package br.com.alexandria.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Responsável por gerar o backup (dump) do banco de dados MySQL usando o
 * utilitário de linha de comando "mysqldump".
 *
 * Esta classe concentra toda a lógica que antes estava misturada dentro do
 * Menu (UI):
 *  - localizar o executável do mysqldump (mesmo quando ele não está no PATH,
 *    que era a causa do erro "Cannot run program mysqldump... CreateProcess
 *    error=2");
 *  - montar o comando de forma segura, sem depender de um único texto que o
 *    Java precisa "adivinhar" como separar em argumentos;
 *  - executar o processo e ler sua saída sem travar;
 *  - lidar com usuários que não são administradores do banco: se o usuário
 *    logado não tiver privilégio para ler procedures/functions (--routines)
 *    ou metadados de tablespace, o backup tenta automaticamente uma versão
 *    mais simples em vez de falhar por completo;
 *  - devolver um resultado simples (sucesso/erro), deixando o Menu livre
 *    para decidir apenas COMO mostrar isso ao usuário (JOptionPane, etc.).
 */
public class BackupService {

    private static final String HOST = "localhost";
    private static final String PORTA = "3306";
    private static final String BANCO = "alexandria";

    /** Resultado de uma tentativa de backup. */
    public static class ResultadoBackup {
        public final boolean sucesso;
        public final String mensagem;
        public final String caminhoArquivo;

        private ResultadoBackup(boolean sucesso, String mensagem, String caminhoArquivo) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
            this.caminhoArquivo = caminhoArquivo;
        }

        static ResultadoBackup ok(String caminho, String observacao) {
            String mensagem = "Backup realizado com sucesso!" + observacao;
            return new ResultadoBackup(true, mensagem, caminho);
        }

        static ResultadoBackup erro(String mensagem) {
            return new ResultadoBackup(false, mensagem, null);
        }
    }

    /** Resultado interno de uma única execução do mysqldump (um "tiro"). */
    private static class ResultadoExecucao {
        final boolean sucesso;
        final String mensagemErro;

        ResultadoExecucao(boolean sucesso, String mensagemErro) {
            this.sucesso = sucesso;
            this.mensagemErro = mensagemErro;
        }
    }

    /**
     * Executa o backup do banco "alexandria" usando as credenciais informadas
     * (idealmente as mesmas do usuário que já fez login no sistema).
     *
     * Caso o usuário não tenha privilégios administrativos, o backup completo
     * (com procedures, functions e triggers) pode não ser possível. Nesse
     * caso, esta classe tenta automaticamente versões mais simples do backup,
     * em vez de simplesmente falhar, e avisa o que ficou de fora.
     *
     * @param usuario usuário do MySQL
     * @param senha   senha do usuário do MySQL
     * @return um ResultadoBackup indicando sucesso ou o motivo da falha
     */
    public static ResultadoBackup realizarBackup(String usuario, String senha) {
        String mysqldumpPath;
        try {
            mysqldumpPath = localizarMysqldump();
        } catch (FileNotFoundException e) {
            return ResultadoBackup.erro(e.getMessage());
        }

        String pastaDestino = System.getProperty("user.home") + File.separator + "Documents";
        File pasta = new File(pastaDestino);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String caminhoBackup = pastaDestino + File.separator + "backup_alexandria_" + timestamp + ".sql";

        // Do backup mais completo para o mais simples. "--no-tablespaces" vai
        // em todas as tentativas porque resolve, de graça, o aviso de que o
        // usuário precisaria do privilégio PROCESS (o mysqldump não precisa
        // dessa informação para um backup normal de banco de aplicação).
        List<List<String>> tentativas = List.of(
                List.of("--no-tablespaces", "--routines", "--triggers"),
                List.of("--no-tablespaces", "--triggers"),
                List.of("--no-tablespaces")
        );

        String ultimoErro = null;

        for (int i = 0; i < tentativas.size(); i++) {
            ResultadoExecucao execucao = executarMysqldump(
                    mysqldumpPath, usuario, senha, tentativas.get(i), caminhoBackup);

            if (execucao.sucesso) {
                String observacao = switch (i) {
                    case 1 -> "\n\nAtenção: as procedures/functions não foram incluídas no backup " +
                            "porque o usuário \"" + usuario + "\" não tem privilégio para lê-las.\n" +
                            "Se precisar delas no backup, peça a um administrador do banco para " +
                            "conceder o privilégio SHOW_ROUTINE a esse usuário.";
                    case 2 -> "\n\nAtenção: apenas as tabelas e os dados foram incluídos no backup.\n" +
                            "Triggers e procedures/functions foram omitidos por falta de privilégio " +
                            "do usuário \"" + usuario + "\".";
                    default -> "";
                };
                return ResultadoBackup.ok(caminhoBackup, observacao);
            }

            ultimoErro = execucao.mensagemErro;

            boolean falhaDeLogin = ultimoErro != null &&
                    ultimoErro.toLowerCase().contains("access denied for user");

            if (falhaDeLogin) {
                // Senha/usuário errados: tentar de novo com menos recursos não vai ajudar.
                break;
            }

            boolean faltaDePrivilegioEmObjetoEspecifico = ultimoErro != null && (
                    ultimoErro.toLowerCase().contains("insufficient privileges") ||
                    ultimoErro.toLowerCase().contains("process privilege") ||
                    ultimoErro.toLowerCase().contains("show_routine")
            );

            if (!faltaDePrivilegioEmObjetoEspecifico) {
                // Erro de outra natureza (ex: conexão recusada) — não adianta insistir.
                break;
            }
            // Caso contrário, cai para a próxima tentativa (mais simples) do loop.
        }

        return ResultadoBackup.erro(
                "Não foi possível concluir o backup.\nDetalhe: " + ultimoErro);
    }

    /**
     * Executa uma única tentativa de mysqldump com os argumentos extras
     * informados, gravando o resultado em caminhoBackup.
     */
    private static ResultadoExecucao executarMysqldump(String mysqldumpPath, String usuario, String senha,
                                                         List<String> argumentosExtras, String caminhoBackup) {
        // Usamos uma LISTA de argumentos (e não uma única String) porque é assim
        // que o ProcessBuilder evita erros de interpretação do comando pelo
        // Windows/Java (o antigo Runtime.getRuntime().exec(String) quebra a
        // string em espaços "no olho", o que causa problemas com senhas ou
        // caminhos que contenham espaço).
        List<String> comando = new ArrayList<>();
        comando.add(mysqldumpPath);
        comando.add("-h" + HOST);
        comando.add("-P" + PORTA);
        comando.add("-u" + usuario);
        comando.add("-p" + senha); // sem espaço entre -p e a senha
        comando.addAll(argumentosExtras);
        comando.add(BANCO);

        ProcessBuilder builder = new ProcessBuilder(comando);

        try {
            Process processo = builder.start();

            // O mysqldump escreve o dump no stdout e possíveis avisos/erros no
            // stderr. Se lermos os dois em sequência (primeiro todo o stdout,
            // depois o stderr), o processo pode travar caso o buffer do stderr
            // encha antes de começarmos a lê-lo. Por isso lemos o stderr em uma
            // thread separada, ao mesmo tempo em que gravamos o stdout no arquivo.
            StringBuilder erros = new StringBuilder();
            Thread leitorDeErros = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(processo.getErrorStream()))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        erros.append(linha).append(System.lineSeparator());
                    }
                } catch (IOException ignored) {
                    // Se o processo já morreu, o stream fecha e cai aqui; não é um erro real.
                }
            });
            leitorDeErros.start();

            try (InputStream saidaPadrao = processo.getInputStream();
                 OutputStream arquivoSaida = new FileOutputStream(caminhoBackup)) {
                saidaPadrao.transferTo(arquivoSaida);
            }

            int codigoSaida = processo.waitFor();
            leitorDeErros.join();

            if (codigoSaida == 0) {
                return new ResultadoExecucao(true, null);
            } else {
                new File(caminhoBackup).delete(); // remove arquivo incompleto/corrompido
                String detalhe = erros.toString().trim();
                String mensagem = "O mysqldump retornou um erro (código " + codigoSaida + ").\n" +
                        (detalhe.isBlank()
                                ? "Verifique se o usuário e a senha têm permissão para exportar o banco."
                                : "Detalhe: " + detalhe);
                return new ResultadoExecucao(false, mensagem);
            }

        } catch (IOException e) {
            return new ResultadoExecucao(false, "Não foi possível executar o mysqldump: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new ResultadoExecucao(false, "O processo de backup foi interrompido.");
        }
    }

    /**
     * Tenta localizar o executável do mysqldump, nesta ordem:
     * 1) variável de ambiente MYSQLDUMP_PATH (caminho completo do executável);
     * 2) instalações padrão do MySQL/XAMPP/WAMP no Windows;
     * 3) instalações padrão em Linux/Mac;
     * 4) apenas "mysqldump", assumindo que está no PATH do sistema.
     *
     * Isso resolve o erro original: "Cannot run program mysqldump...
     * CreateProcess error=2, The system cannot find the file specified",
     * que acontece quando o Java procura o programa apenas no PATH e ele
     * não está lá (situação muito comum em instalações do MySQL no Windows).
     */
    private static String localizarMysqldump() throws FileNotFoundException {
        String variavelAmbiente = System.getenv("MYSQLDUMP_PATH");
        if (variavelAmbiente != null && !variavelAmbiente.isBlank() && new File(variavelAmbiente).isFile()) {
            return variavelAmbiente;
        }

        boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
        String nomeExecutavel = windows ? "mysqldump.exe" : "mysqldump";

        List<String> candidatos = new ArrayList<>();
        if (windows) {
            String[] versoesComuns = {
                    "MySQL Server 9.0", "MySQL Server 8.4", "MySQL Server 8.0", "MySQL Server 5.7"
            };
            for (String versao : versoesComuns) {
                candidatos.add("C:\\Program Files\\MySQL\\" + versao + "\\bin\\" + nomeExecutavel);
                candidatos.add("C:\\Program Files (x86)\\MySQL\\" + versao + "\\bin\\" + nomeExecutavel);
            }
            candidatos.add("C:\\xampp\\mysql\\bin\\" + nomeExecutavel);
            candidatos.add("C:\\wamp64\\bin\\mysql\\mysql8.0.31\\bin\\" + nomeExecutavel);
        } else {
            candidatos.add("/usr/bin/" + nomeExecutavel);
            candidatos.add("/usr/local/bin/" + nomeExecutavel);
            candidatos.add("/usr/local/mysql/bin/" + nomeExecutavel);
            candidatos.add("/opt/homebrew/bin/" + nomeExecutavel); // Mac com Apple Silicon
            candidatos.add("/opt/homebrew/opt/mysql-client/bin/" + nomeExecutavel);
        }

        for (String caminho : candidatos) {
            if (new File(caminho).isFile()) {
                return caminho;
            }
        }

        if (estaNoPath(nomeExecutavel)) {
            return nomeExecutavel;
        }

        throw new FileNotFoundException(
                "Não foi possível encontrar o programa \"" + nomeExecutavel + "\".\n\n" +
                "Isso geralmente acontece quando o MySQL não está instalado nesta máquina\n" +
                "ou a pasta \"bin\" do MySQL não foi adicionada à variável de ambiente PATH.\n\n" +
                "Soluções:\n" +
                "1) Adicione a pasta bin do MySQL ao PATH do Windows\n" +
                "   (ex: C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin); ou\n" +
                "2) Defina uma variável de ambiente chamada MYSQLDUMP_PATH apontando\n" +
                "   para o caminho completo do mysqldump.exe nesta máquina."
        );
    }

    private static boolean estaNoPath(String nomeExecutavel) {
        String path = System.getenv("PATH");
        if (path == null) {
            return false;
        }
        for (String pasta : path.split(File.pathSeparator)) {
            if (new File(pasta, nomeExecutavel).isFile()) {
                return true;
            }
        }
        return false;
    }
}
