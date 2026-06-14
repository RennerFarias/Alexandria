-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: alexandria
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `emprestimos`
--

DROP TABLE IF EXISTS `emprestimos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emprestimos` (
  `id_emprestimo` int NOT NULL AUTO_INCREMENT,
  `id_usuario_fk` int DEFAULT NULL,
  `id_livro_fk` int DEFAULT NULL,
  `data_saida` datetime DEFAULT CURRENT_TIMESTAMP,
  `data_prevista` date DEFAULT NULL,
  `data_devolucao` datetime DEFAULT NULL,
  PRIMARY KEY (`id_emprestimo`),
  KEY `idx_emprestimos_usuario` (`id_usuario_fk`),
  KEY `idx_emprestimos_livro` (`id_livro_fk`),
  CONSTRAINT `id_livro_fk` FOREIGN KEY (`id_livro_fk`) REFERENCES `livros` (`id_livro`),
  CONSTRAINT `usuario_fk` FOREIGN KEY (`id_usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emprestimos`
--

LOCK TABLES `emprestimos` WRITE;
/*!40000 ALTER TABLE `emprestimos` DISABLE KEYS */;
INSERT INTO `emprestimos` VALUES (1,1,1,'2026-06-14 20:14:56','2026-06-21',NULL),(2,1,1,'2026-06-14 20:15:44','2026-06-21',NULL);
/*!40000 ALTER TABLE `emprestimos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enderecos`
--

DROP TABLE IF EXISTS `enderecos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enderecos` (
  `id_endereço` int NOT NULL AUTO_INCREMENT,
  `logradouro` varchar(100) DEFAULT NULL,
  `bairro` varchar(100) DEFAULT NULL,
  `cidade` varchar(100) DEFAULT NULL,
  `uf` varchar(100) DEFAULT NULL,
  `id_usuario_fk` int DEFAULT NULL,
  PRIMARY KEY (`id_endereço`),
  KEY `id_usuario_fk` (`id_usuario_fk`),
  CONSTRAINT `id_usuario_fk` FOREIGN KEY (`id_usuario_fk`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enderecos`
--

LOCK TABLES `enderecos` WRITE;
/*!40000 ALTER TABLE `enderecos` DISABLE KEYS */;
INSERT INTO `enderecos` VALUES (1,'Rua A','Centro','Campina Grande','PB',1);
/*!40000 ALTER TABLE `enderecos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livros`
--

DROP TABLE IF EXISTS `livros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livros` (
  `id_livro` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) DEFAULT NULL,
  `autor` varchar(100) DEFAULT NULL,
  `isbn` varchar(100) DEFAULT NULL,
  `preco_custo` decimal(10,2) DEFAULT NULL,
  `quantidade_estoque` int DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_livro`),
  UNIQUE KEY `isbn` (`isbn`),
  KEY `idx_livros_titulo` (`titulo`),
  KEY `idx_livros_autor` (`autor`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livros`
--

LOCK TABLES `livros` WRITE;
/*!40000 ALTER TABLE `livros` DISABLE KEYS */;
INSERT INTO `livros` VALUES (1,'Dom Casmurro','Machado de Assis','978000000001',20.00,3,'DISPONIVEL');
/*!40000 ALTER TABLE `livros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_auditoria`
--

DROP TABLE IF EXISTS `log_auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log_auditoria` (
  `id_log` int NOT NULL AUTO_INCREMENT,
  `tabela_afetada` varchar(30) DEFAULT NULL,
  `acao` varchar(100) DEFAULT NULL,
  `usuario_responsavel` varchar(100) DEFAULT NULL,
  `dados_antigos` text,
  `data_hora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_log`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_auditoria`
--

LOCK TABLES `log_auditoria` WRITE;
/*!40000 ALTER TABLE `log_auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multas`
--

DROP TABLE IF EXISTS `multas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multas` (
  `id_multa` int NOT NULL AUTO_INCREMENT,
  `id_emprestimo_fk` int DEFAULT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `pago` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id_multa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multas`
--

LOCK TABLES `multas` WRITE;
/*!40000 ALTER TABLE `multas` DISABLE KEYS */;
/*!40000 ALTER TABLE `multas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `cpf` char(11) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `senha` varchar(30) DEFAULT NULL,
  `tipo` enum('ALUNO','GERENTE','BIBLIOTECARIO','ESTAGIARIO') DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `cpf` (`cpf`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Jose','11111111111','jose@email.com','123456','ALUNO');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `vw_acervo_publico`
--

DROP TABLE IF EXISTS `vw_acervo_publico`;
/*!50001 DROP VIEW IF EXISTS `vw_acervo_publico`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_acervo_publico` AS SELECT 
 1 AS `id_livro`,
 1 AS `titulo`,
 1 AS `autor`,
 1 AS `quantidade_estoque`,
 1 AS `status`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_dashboard_financeiro`
--

DROP TABLE IF EXISTS `vw_dashboard_financeiro`;
/*!50001 DROP VIEW IF EXISTS `vw_dashboard_financeiro`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_dashboard_financeiro` AS SELECT 
 1 AS `total_multas`,
 1 AS `valor_total_multas`,
 1 AS `multas_pagas`,
 1 AS `multas_pendentes`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_emprestimos_ativos`
--

DROP TABLE IF EXISTS `vw_emprestimos_ativos`;
/*!50001 DROP VIEW IF EXISTS `vw_emprestimos_ativos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_emprestimos_ativos` AS SELECT 
 1 AS `id_emprestimo`,
 1 AS `usuario`,
 1 AS `livro`,
 1 AS `data_saida`,
 1 AS `data_prevista`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_livros_atrasados`
--

DROP TABLE IF EXISTS `vw_livros_atrasados`;
/*!50001 DROP VIEW IF EXISTS `vw_livros_atrasados`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_livros_atrasados` AS SELECT 
 1 AS `id_emprestimo`,
 1 AS `usuario`,
 1 AS `titulo`,
 1 AS `data_prevista`,
 1 AS `dias_atraso`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_livros_disponiveis`
--

DROP TABLE IF EXISTS `vw_livros_disponiveis`;
/*!50001 DROP VIEW IF EXISTS `vw_livros_disponiveis`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_livros_disponiveis` AS SELECT 
 1 AS `id_livro`,
 1 AS `titulo`,
 1 AS `autor`,
 1 AS `quantidade_estoque`,
 1 AS `status`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vw_ranking_leitura`
--

DROP TABLE IF EXISTS `vw_ranking_leitura`;
/*!50001 DROP VIEW IF EXISTS `vw_ranking_leitura`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_ranking_leitura` AS SELECT 
 1 AS `id_usuario`,
 1 AS `nome`,
 1 AS `total_emprestimos`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `vw_acervo_publico`
--

/*!50001 DROP VIEW IF EXISTS `vw_acervo_publico`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_acervo_publico` AS select `livros`.`id_livro` AS `id_livro`,`livros`.`titulo` AS `titulo`,`livros`.`autor` AS `autor`,`livros`.`quantidade_estoque` AS `quantidade_estoque`,`livros`.`status` AS `status` from `livros` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_dashboard_financeiro`
--

/*!50001 DROP VIEW IF EXISTS `vw_dashboard_financeiro`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_dashboard_financeiro` AS select count(`multas`.`id_multa`) AS `total_multas`,sum(`multas`.`valor`) AS `valor_total_multas`,sum((case when (`multas`.`pago` = 1) then `multas`.`valor` else 0 end)) AS `multas_pagas`,sum((case when (`multas`.`pago` = 0) then `multas`.`valor` else 0 end)) AS `multas_pendentes` from `multas` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_emprestimos_ativos`
--

/*!50001 DROP VIEW IF EXISTS `vw_emprestimos_ativos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_emprestimos_ativos` AS select `e`.`id_emprestimo` AS `id_emprestimo`,`u`.`nome` AS `usuario`,`l`.`titulo` AS `livro`,`e`.`data_saida` AS `data_saida`,`e`.`data_prevista` AS `data_prevista` from ((`emprestimos` `e` join `usuarios` `u` on((`e`.`id_usuario_fk` = `u`.`id_usuario`))) join `livros` `l` on((`e`.`id_livro_fk` = `l`.`id_livro`))) where (`e`.`data_devolucao` is null) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_livros_atrasados`
--

/*!50001 DROP VIEW IF EXISTS `vw_livros_atrasados`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_livros_atrasados` AS select `e`.`id_emprestimo` AS `id_emprestimo`,`u`.`nome` AS `usuario`,`l`.`titulo` AS `titulo`,`e`.`data_prevista` AS `data_prevista`,(to_days(curdate()) - to_days(`e`.`data_prevista`)) AS `dias_atraso` from ((`emprestimos` `e` join `usuarios` `u` on((`e`.`id_usuario_fk` = `u`.`id_usuario`))) join `livros` `l` on((`e`.`id_livro_fk` = `l`.`id_livro`))) where ((`e`.`data_devolucao` is null) and (`e`.`data_prevista` < curdate())) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_livros_disponiveis`
--

/*!50001 DROP VIEW IF EXISTS `vw_livros_disponiveis`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_livros_disponiveis` AS select `livros`.`id_livro` AS `id_livro`,`livros`.`titulo` AS `titulo`,`livros`.`autor` AS `autor`,`livros`.`quantidade_estoque` AS `quantidade_estoque`,`livros`.`status` AS `status` from `livros` where (`livros`.`quantidade_estoque` > 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vw_ranking_leitura`
--

/*!50001 DROP VIEW IF EXISTS `vw_ranking_leitura`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_ranking_leitura` AS select `u`.`id_usuario` AS `id_usuario`,`u`.`nome` AS `nome`,count(`e`.`id_emprestimo`) AS `total_emprestimos` from (`usuarios` `u` left join `emprestimos` `e` on((`u`.`id_usuario` = `e`.`id_usuario_fk`))) group by `u`.`id_usuario`,`u`.`nome` order by `total_emprestimos` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-14 20:37:55
