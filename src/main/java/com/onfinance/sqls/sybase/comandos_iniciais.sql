/* 
 * Comandos iniciais a serem executados logo após a criação do banco de dados
 */
/**
 * Author:  Vanderlei Fiorentin
 * Created: 25 de jun. de 2021
 */

INSERT INTO PERIODIC_ACTIONS VALUES(1,'LanctoFixoPeriodicAction',300000,216000,NOW(),1,4);
INSERT INTO PERIODIC_ACTIONS VALUES(2,'ExcluirFaturasZeradasPeriodicAction',300000,216000,NOW(),1,4);
INSERT INTO PERIODIC_ACTIONS VALUES(3,'AvisoVencimentoFaturasPeriodicAction',86400000,216000,NOW(),1,4);
INSERT INTO PERIODIC_ACTIONS VALUES(4,'AvisoFaturasVencidasPeriodicAction',86400000,216000,NOW(),1,4);
INSERT INTO PERIODIC_ACTIONS VALUES(5,'UploadDocumentosPagamentoPeriodicAction',300000,216000,NOW(),1,4);
INSERT INTO PERIODIC_ACTIONS VALUES(6,'UploadDocumentosLanctoPeriodicAction',300000,216000,NOW(),1,4);

INSERT INTO USUARIOS VALUES(1,'Administrador','adm@gmail.com','admin','e73b6814e73fb0c249a2b27f628b4a1e',1,1);
INSERT INTO PERFIS_USUARIOS VALUES(1,1,'00000000000','1900-01-01',NULL,1,1);

INSERT INTO ROLES VALUES(1,'Admin','Role de administrador');
INSERT INTO ROLES VALUES(2,'Visualizacao','Permite apenas visualizar as listagens');
INSERT INTO ROLES VALUES(3,'Convidado','Role de convidado');
INSERT INTO ROLES VALUES(4,'Gerenciar Acessos','Permite cadastrar roles e gerenciar suas permissões');
INSERT INTO ROLES VALUES(5,'Gerente Financeiro','Permite gerenciar lançamentos e baixar faturas');
INSERT INTO ROLES VALUES(6,'Gerente Bancario','Permite gerenciar os dados bancários');
INSERT INTO ROLES VALUES(7,'Dashboard','Permite visualizar os gráficos do dashboard');

INSERT INTO PERMISSOES VALUES(1,'Acesso Listar Usuarios','Acesso as informações de usuários');
INSERT INTO PERMISSOES VALUES(2,'Acesso Listar Empresas','Acesso as informações de empresas');
INSERT INTO PERMISSOES VALUES(3,'Acesso Listar Produtos','Acesso as informações de produtos');
INSERT INTO PERMISSOES VALUES(4,'Acesso Listar Bancos','Acesso as informações de bancos');
INSERT INTO PERMISSOES VALUES(5,'Acesso Listar Agencias','Acesso as informações de agencias');
INSERT INTO PERMISSOES VALUES(6,'Acesso Listar Contas','Acesso as informações de contas');
INSERT INTO PERMISSOES VALUES(7,'Acesso Listar Cartoes','Acesso as informações de cartoes');
INSERT INTO PERMISSOES VALUES(8,'Acesso Listar Bandeiras','Acesso as informações de bandeiras');
INSERT INTO PERMISSOES VALUES(9,'Acesso Listar Categorias','Acesso as informações de categorias');
INSERT INTO PERMISSOES VALUES(10,'Acesso Listar Unidades','Acesso as informações de unidades');
INSERT INTO PERMISSOES VALUES(11,'Acesso Listar Eventos','Acesso as informações de eventos');
INSERT INTO PERMISSOES VALUES(12,'Acesso Dashboard','Acesso as informações do dashboard');
INSERT INTO PERMISSOES VALUES(13,'Acesso Grafico Despesas/Receitas','Acesso as informações do Grafico Despesas/Receitas');
INSERT INTO PERMISSOES VALUES(14,'Acesso Grafico Projecao','Acesso as informações do Grafico Projecao');
INSERT INTO PERMISSOES VALUES(15,'Acesso Cadastrar Usuarios','Permite cadastro de usuários');
INSERT INTO PERMISSOES VALUES(16,'Acesso Cadastrar Empresas','Permite cadastro de empresas');
INSERT INTO PERMISSOES VALUES(17,'Acesso Cadastrar Produtos','Permite cadastro de produtos');
INSERT INTO PERMISSOES VALUES(18,'Acesso Cadastrar Bancos','Permite cadastro de bancos');
INSERT INTO PERMISSOES VALUES(19,'Acesso Cadastrar Agencias','Permite cadastro de agencias');
INSERT INTO PERMISSOES VALUES(20,'Acesso Cadastrar Contas','Permite cadastro de contas');
INSERT INTO PERMISSOES VALUES(21,'Acesso Cadastrar Cartoes','Permite cadastro de cartoes');
INSERT INTO PERMISSOES VALUES(22,'Acesso Cadastrar Bandeiras','Permite cadastro de bandeiras');
INSERT INTO PERMISSOES VALUES(23,'Acesso Cadastrar Categorias','Permite cadastro de categorias');
INSERT INTO PERMISSOES VALUES(24,'Acesso Cadastrar Unidades','Permite cadastro de unidades');
INSERT INTO PERMISSOES VALUES(25,'Acesso Cadastrar Eventos','Permite cadastro de eventos');
INSERT INTO PERMISSOES VALUES(26,'Acesso Atualizar Usuarios','Permite atualização de usuários');
INSERT INTO PERMISSOES VALUES(27,'Acesso Atualizar Empresas','Permite atualização de empresas');
INSERT INTO PERMISSOES VALUES(28,'Acesso Atualizar Produtos','Permite atualização de produtos');
INSERT INTO PERMISSOES VALUES(29,'Acesso Atualizar Bancos','Permite atualização de bancos');
INSERT INTO PERMISSOES VALUES(30,'Acesso Atualizar Agencias','Permite atualização de agencias');
INSERT INTO PERMISSOES VALUES(31,'Acesso Atualizar Contas','Permite atualização de contas');
INSERT INTO PERMISSOES VALUES(32,'Acesso Atualizar Cartoes','Permite atualização de cartoes');
INSERT INTO PERMISSOES VALUES(33,'Acesso Atualizar Bandeiras','Permite atualização de bandeiras');
INSERT INTO PERMISSOES VALUES(34,'Acesso Atualizar Categorias','Permite atualização de categorias');
INSERT INTO PERMISSOES VALUES(35,'Acesso Atualizar Unidades','Permite atualização de unidades');
INSERT INTO PERMISSOES VALUES(36,'Acesso Atualizar Eventos','Permite atualização de eventos');
INSERT INTO PERMISSOES VALUES(37,'Acesso Remover Usuarios','Permite remoção de usuários');
INSERT INTO PERMISSOES VALUES(38,'Acesso Remover Empresas','Permite remoção de empresas');
INSERT INTO PERMISSOES VALUES(39,'Acesso Remover Produtos','Permite remoção de produtos');
INSERT INTO PERMISSOES VALUES(40,'Acesso Remover Bancos','Permite remoção de bancos');
INSERT INTO PERMISSOES VALUES(41,'Acesso Remover Agencias','Permite remoção de agencias');
INSERT INTO PERMISSOES VALUES(42,'Acesso Remover Contas','Permite remoção de contas');
INSERT INTO PERMISSOES VALUES(43,'Acesso Remover Cartoes','Permite remoção de cartoes');
INSERT INTO PERMISSOES VALUES(44,'Acesso Remover Bandeiras','Permite remoção de bandeiras');
INSERT INTO PERMISSOES VALUES(45,'Acesso Remover Categorias','Permite remoção de categorias');
INSERT INTO PERMISSOES VALUES(46,'Acesso Remover Unidades','Permite remoção de unidades');
INSERT INTO PERMISSOES VALUES(47,'Acesso Remover Eventos','Permite remoção de eventos');
INSERT INTO PERMISSOES VALUES(48,'Acesso Listar Lancamentos','Permite listar lançamentos');
INSERT INTO PERMISSOES VALUES(49,'Acesso Cadastrar Lancamentos','Permite cadastrar lançamentos');
INSERT INTO PERMISSOES VALUES(50,'Acesso Atualizar Lancamentos','Permite atualizar lançamentos');
INSERT INTO PERMISSOES VALUES(51,'Acesso Remover Lancamentos','Permite remover lançamentos');
INSERT INTO PERMISSOES VALUES(52,'Acesso Listar Faturas','Permite listar faturas');
INSERT INTO PERMISSOES VALUES(53,'Acesso Cadastrar Faturas','Permite cadastrar faturas');
INSERT INTO PERMISSOES VALUES(54,'Acesso Atualizar Faturas','Permite atualizar faturas');
INSERT INTO PERMISSOES VALUES(55,'Acesso Remover Faturas','Permite remover faturas');
INSERT INTO PERMISSOES VALUES(56,'Acesso Listar Extratos Bancarios','Permite listar extratos bancarios');
INSERT INTO PERMISSOES VALUES(57,'Acesso Listar Parcelas Lancamentos','Permite listar parcelas lancamentos');
INSERT INTO PERMISSOES VALUES(58,'Acesso Renegociar Parcelas','Permite renegociar parcelas');
INSERT INTO PERMISSOES VALUES(59,'Acesso Consultar Fatura','Permite consultar fatura');
INSERT INTO PERMISSOES VALUES(60,'Acesso Realizar Pagamentos','Permite realizar pagamentos');
INSERT INTO PERMISSOES VALUES(61,'Acesso Desvincular Parcelas','Permite desvincular parcelas das faturas');
INSERT INTO PERMISSOES VALUES(62,'Acesso Listar Eventos Lanctos Entrada','Permite listar eventos lanctos entrada');
INSERT INTO PERMISSOES VALUES(63,'Acesso Listar Saidas Lanctos Entrada','Permite listar saidas lanctos entrada');
INSERT INTO PERMISSOES VALUES(64,'Acesso Vincular Parcelas','Permite vincular parcelas à faturas');
INSERT INTO PERMISSOES VALUES(65,'Acesso Listar Perfil Usuarios','Acesso as informações de perfil dos usuários');
INSERT INTO PERMISSOES VALUES(66,'Acesso Cadastrar Perfil Usuarios','Acesso a cadastrar perfil dos usuários');
INSERT INTO PERMISSOES VALUES(67,'Acesso Atualizar Perfil Usuarios','Acesso a atualizar perfil dos usuários');
INSERT INTO PERMISSOES VALUES(68,'Acesso Remover Perfil Usuarios','Acesso a remover perfil dos usuários');
INSERT INTO PERMISSOES VALUES(69,'Acesso Listar Roles','Acesso as informações de roles');
INSERT INTO PERMISSOES VALUES(70,'Acesso Cadastrar Roles','Permite cadastrar roles');
INSERT INTO PERMISSOES VALUES(71,'Acesso Atualizar Roles','Permite atualizar roles');
INSERT INTO PERMISSOES VALUES(72,'Acesso Remover Roles','Permite remover roles');
INSERT INTO PERMISSOES VALUES(73,'Acesso Listar Permissoes','Acesso as informações de permissões');
INSERT INTO PERMISSOES VALUES(74,'Acesso Cadastrar Permissoes','Permite cadastrar permissões');
INSERT INTO PERMISSOES VALUES(75,'Acesso Atualizar Permissoes','Permite atualizar permissões');
INSERT INTO PERMISSOES VALUES(76,'Acesso Remover Permissoes','Permite remover permissões');

INSERT INTO RECURSOS VALUES(1,1,'api/usuarios');
INSERT INTO RECURSOS VALUES(2,1,'api/empresas');
INSERT INTO RECURSOS VALUES(3,1,'api/produtos');
INSERT INTO RECURSOS VALUES(4,1,'api/bancos');
INSERT INTO RECURSOS VALUES(5,1,'api/agencias');
INSERT INTO RECURSOS VALUES(6,1,'api/contas');
INSERT INTO RECURSOS VALUES(7,1,'api/cartoes');
INSERT INTO RECURSOS VALUES(8,1,'api/bandeiras');
INSERT INTO RECURSOS VALUES(9,1,'api/categorias');
INSERT INTO RECURSOS VALUES(10,1,'api/unidades');
INSERT INTO RECURSOS VALUES(11,1,'api/eventos');
INSERT INTO RECURSOS VALUES(12,1,'api/dashboard');
INSERT INTO RECURSOS VALUES(13,1,'api/dashboard/grafico-despesas-receitas');
INSERT INTO RECURSOS VALUES(14,1,'api/dashboard/grafico-projecao');
INSERT INTO RECURSOS VALUES(15,2,'api/usuarios');
INSERT INTO RECURSOS VALUES(16,2,'api/empresas');
INSERT INTO RECURSOS VALUES(17,2,'api/produtos');
INSERT INTO RECURSOS VALUES(18,2,'api/bancos');
INSERT INTO RECURSOS VALUES(19,2,'api/agencias');
INSERT INTO RECURSOS VALUES(20,2,'api/contas');
INSERT INTO RECURSOS VALUES(21,2,'api/cartoes');
INSERT INTO RECURSOS VALUES(22,2,'api/bandeiras');
INSERT INTO RECURSOS VALUES(23,2,'api/categorias');
INSERT INTO RECURSOS VALUES(24,2,'api/unidades');
INSERT INTO RECURSOS VALUES(25,2,'api/eventos');
INSERT INTO RECURSOS VALUES(26,3,'api/usuarios');
INSERT INTO RECURSOS VALUES(27,3,'api/empresas');
INSERT INTO RECURSOS VALUES(28,3,'api/produtos');
INSERT INTO RECURSOS VALUES(29,3,'api/bancos');
INSERT INTO RECURSOS VALUES(30,3,'api/agencias');
INSERT INTO RECURSOS VALUES(31,3,'api/contas');
INSERT INTO RECURSOS VALUES(32,3,'api/cartoes');
INSERT INTO RECURSOS VALUES(33,3,'api/bandeiras');
INSERT INTO RECURSOS VALUES(34,3,'api/categorias');
INSERT INTO RECURSOS VALUES(35,3,'api/unidades');
INSERT INTO RECURSOS VALUES(36,3,'api/eventos');
INSERT INTO RECURSOS VALUES(37,4,'api/usuarios');
INSERT INTO RECURSOS VALUES(38,4,'api/empresas');
INSERT INTO RECURSOS VALUES(39,4,'api/produtos');
INSERT INTO RECURSOS VALUES(40,4,'api/bancos');
INSERT INTO RECURSOS VALUES(41,4,'api/agencias');
INSERT INTO RECURSOS VALUES(42,4,'api/contas');
INSERT INTO RECURSOS VALUES(43,4,'api/cartoes');
INSERT INTO RECURSOS VALUES(44,4,'api/bandeiras');
INSERT INTO RECURSOS VALUES(45,4,'api/categorias');
INSERT INTO RECURSOS VALUES(46,4,'api/unidades');
INSERT INTO RECURSOS VALUES(47,4,'api/eventos');
INSERT INTO RECURSOS VALUES(48,1,'api/lanctos-contabeis');
INSERT INTO RECURSOS VALUES(49,2,'api/lanctos-contabeis');
INSERT INTO RECURSOS VALUES(50,3,'api/lanctos-contabeis');
INSERT INTO RECURSOS VALUES(51,4,'api/lanctos-contabeis');
INSERT INTO RECURSOS VALUES(52,1,'api/faturas');
INSERT INTO RECURSOS VALUES(53,2,'api/faturas');
INSERT INTO RECURSOS VALUES(54,3,'api/faturas');
INSERT INTO RECURSOS VALUES(55,4,'api/faturas');
INSERT INTO RECURSOS VALUES(56,1,'api/extratos-bancarios');
INSERT INTO RECURSOS VALUES(57,1,'api/parcelas-lancto-contabil');
INSERT INTO RECURSOS VALUES(58,2,'api/renegociar-parcelas');
INSERT INTO RECURSOS VALUES(59,1,'api/pagamentos');
INSERT INTO RECURSOS VALUES(60,2,'api/pagamentos');
INSERT INTO RECURSOS VALUES(61,2,'api/parcelas-lancto-contabil/desvincular');
INSERT INTO RECURSOS VALUES(62,1,'api/eventos-lancto-entrada');
INSERT INTO RECURSOS VALUES(63,1,'api/saidas-lancto-entrada');
INSERT INTO RECURSOS VALUES(64,2,'api/parcelas-lancto-contabil/vincular');
INSERT INTO RECURSOS VALUES(65,1,'api/perfil-usuario');
INSERT INTO RECURSOS VALUES(66,2,'api/perfil-usuario');
INSERT INTO RECURSOS VALUES(67,3,'api/perfil-usuario');
INSERT INTO RECURSOS VALUES(68,4,'api/perfil-usuario');
INSERT INTO RECURSOS VALUES(69,1,'api/roles');
INSERT INTO RECURSOS VALUES(70,2,'api/roles');
INSERT INTO RECURSOS VALUES(71,3,'api/roles');
INSERT INTO RECURSOS VALUES(72,4,'api/roles');
INSERT INTO RECURSOS VALUES(73,1,'api/permissoes');
INSERT INTO RECURSOS VALUES(74,2,'api/permissoes');
INSERT INTO RECURSOS VALUES(75,3,'api/permissoes');
INSERT INTO RECURSOS VALUES(76,4,'api/permissoes');

-- Vincula as permissões existentes na role de administrador
INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 1
  FROM PERMISSOES;

-- Vincula as permissões aos recursos
INSERT INTO PERMISSOES_RECURSO
SELECT ID_PERMISSAO, ID_PERMISSAO
  FROM PERMISSOES
ORDER BY 1 ASC;

-- Insere apenas as permissões de visualização na role de visualização
INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 2
  FROM PERMISSOES_RECURSO PR 
       INNER JOIN RECURSOS R ON R.ID_RECURSO = PR.ID_RECURSO
 WHERE R.ACAO = 1;

-- Insere permissões nas demais roles
INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 3
  FROM PERMISSOES
 WHERE ID_PERMISSAO IN(1,2,3,4,5,8,9,10,11);

INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 4
  FROM PERMISSOES
 WHERE ID_PERMISSAO IN(1,15,26,37,69,70,71,72,73,74,75,76);

INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 5
  FROM PERMISSOES
 WHERE ID_PERMISSAO IN(2,3,9,10,11,16,17,23,24,25,27,28,34,35,36,38,39,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64);

INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 6
  FROM PERMISSOES
 WHERE ID_PERMISSAO IN(1,2,4,5,6,7,8,18,19,20,21,22,29,30,31,32,33,40,41,42,43,44);

INSERT INTO PERMISSOES_ROLES
SELECT ID_PERMISSAO, 7
  FROM PERMISSOES
 WHERE ID_PERMISSAO IN(12,13,14);

-- Vincula o usuário administrador na role de administrador
INSERT INTO USUARIOS_ROLES VALUES(1,1);