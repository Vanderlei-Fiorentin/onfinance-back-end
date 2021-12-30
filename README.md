# onfinance-back-end
Backend gerenciador financeiro

# Apresentação:
Esse projeto é um gerenciador financeiro com o intuito de gerenciar as finanças pessoais. O mesmo roda em conjunto com os demais projetos abaixo:
1- onfinance-front-end -> Consome o projeto onfinance-back-end.
2- email-service -> Utilizado pelo projeto onfinance-back-end para envio de e-mails, redefinição de senha, etc.
3- gerenciador-arquivos -> Utilizado pelo projeto onfinance-back-end para armazenar os arquivos de lançamentos, pagamentos, etc.

# Funcionalidades:
1- Lançamentos a pagar e receber fixos, à vista e parcelados;
2- Lançamentos no débito e crédito;
3- Aviso de lançamentos vencidos e à vencer (Necessário configurar o projeto email-service);
4- Redefinição de senha dos usuários (Necessário configurar o projeto email-service);
5- Armazenamento de arquivos localmente e na nuvem (Necessário configurar o projeto gerenciador-arquivos);
6- Acompanhamento extrato bancário;
7- Visualização de gráficos de receitas e despesas;
8- Opção de renegociação de lançamentos;
9- Controle de acesso aos recursos por roles e permissões;

# Criação do banco de dados do projeto com o PostgresSQL:
1- Criar um banco de dados PostgresSQL;
2- Executar os comandos do arquivo "tabelas.sql" presente no package "com.onfinance.sqls.postgressql";
3- Criar as funções presentes no package "com.onfinance.sqls.postgressql" na seguinte ordem:
3.1- "days.sql";
3.2- "months.sql";
3.3- "years.sql";
3.4- "fun_gerar_vencimento.sql";
4- Criar as triggers presentes no package "com.onfinance.sqls.postgressql";
5- Executar os comandos do arquivo "comandos_iniciais.sql" presente no package "com.onfinance.sqls.postgressql";

# Criação do banco de dados do projeto com o Sybase 16:
1- Criar um banco de dados Sybase 16;
2- Executar os comandos do arquivo "tabelas.sql" presente no package "com.onfinance.sqls.sybase";
3- Executar o comando do arquivo "fun_gerar_vencimento.sql" presente no package "com.onfinance.sqls.sybase";
4- Criar as triggers presentes no package "com.onfinance.sqls.sybase";
5- Executar os comandos do arquivo "comandos_iniciais.sql" presente no package "com.onfinance.sqls.sybase";

# Dados do usuário admin criado no banco de dados:
-- Usuário: admin
-- Senha: admin
