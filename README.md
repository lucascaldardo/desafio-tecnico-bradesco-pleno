# 🏦 Desafio Técnico Bradesco - Desenvolvedor Pleno

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Data_Access-lightgrey?style=for-the-badge)
![Swing](https://img.shields.io/badge/Java_Swing-Desktop_UI-blue?style=for-the-badge)

Aplicação robusta desenvolvida em Java para o desafio técnico de Desenvolvedor Pleno. O sistema orquestra e executa a replicação de dados entre múltiplos bancos de dados PostgreSQL, atuando como um motor de sincronização customizado (ETL / Change Data Capture) que transfere registros da origem para o destino com base em regras dinâmicas.

## 🎯 Sobre o Projeto

Em vez de depender de ferramentas de terceiros pesadas, esta aplicação utiliza um banco de dados de "Controle" para gerenciar credenciais, rotas e tabelas. O grande diferencial tecnológico é o seu motor assíncrono que processa filas de replicação (CDC), identificando automaticamente operações de Insert, Update e Delete na origem e refletindo-as no destino com alta fidelidade.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Interface Gráfica:** Java Swing (JDesktopPane, JInternalFrame)
* **Banco de Dados:** PostgreSQL
* **Acesso a Dados:** JDBC Puro (Java Database Connectivity)
* **Arquitetura:** MVC e Padrão DAO

## 🧠 Arquitetura e Decisões de Engenharia

Para atender aos requisitos de uma vaga Pleno, garantindo que a replicação ocorra de forma contínua e escalável, foram aplicadas as seguintes práticas:

* **Multithreading e Background Workers:** O motor de replicação (`ReplicacaoExecutar`) roda em uma Thread separada. Isso permite que a sincronização ocorra em loop assíncrono enquanto a interface gráfica permanece 100% responsiva.
* **Processamento em Lote (Batch Processing):** Utilização de `PreparedStatement.addBatch()` e `executeBatch()`, garantindo alta performance na transferência de grandes volumes de dados.
* **Change Data Capture (CDC) Customizado:** Leitura inteligente de uma tabela de fila (`replication_queue`) na origem para capturar eventos reais de modificação. O sistema aplica lógicas de `Upsert` dinâmico para resolver conflitos de chave primária no destino.
* **Reflexão de Metadados:** Uso de `ResultSetMetaData` para mapear colunas dinamicamente em tempo de execução. A ferramenta consegue construir instruções SQL (`INSERT`, `UPDATE`) on-the-fly, independentemente da estrutura da tabela.
* **Design Pattern DAO:** Isolamento total da lógica de acesso a dados (SQL) em classes específicas (Data Access Objects), mantendo as views e serviços limpos e com responsabilidade única.

## 🛠️ Como Executar o Projeto Localmente

**Pré-requisitos:** Java 17 instalado e instâncias do PostgreSQL rodando localmente.

1. Clone o repositório:
   git clone https://github.com/lucascaldardo/desafio-tecnico-bradesco-pleno.git

2. Prepare o Banco de Controle:
   Crie um banco chamado `controle` no PostgreSQL local (senha: 123) e certifique-se de que as tabelas de parametrização existem.

3. Compile e execute a aplicação via IDE ou terminal.

## 📖 Documentação dos Módulos

O sistema possui uma interface MDI (Multiple Document Interface) com os seguintes módulos:

| Módulo | Funcionalidade | Descrição |
|---|---|---|
| `VIEW` | `Processos` | Cadastro e gestão dos fluxos gerais de replicação. |
| `VIEW` | `Direções` | Configuração das credenciais e rotas (Origem ➔ Destino). |
| `VIEW` | `Processos x Tabelas` | Mapeamento dinâmico de tabelas e filtros SQL customizados. |
| `ENGINE`| `Motor CDC` | Thread invisível que lê a fila de log e aplica as mudanças no destino. |

### Exemplo de Uso: Execução Automática (Modo Serviço)
O sistema pode ser iniciado sem a intervenção do usuário, ideal para rodar em servidores. Basta passar o argumento via linha de comando:
java -jar replicador.jar service=yes

### Exemplo de Comportamento: Fila de Replicação (CDC)
O motor lê o log na origem e processa os dados com a seguinte lógica de operação (`operation`):
{
"table_name": "clientes",
"row_id": 1054,
"operation": "U",  // O sistema fará um UPDATE ou INSERT (Upsert) no destino
"processed_at": null
}

## 📄 Interface Visual (GUI)
A aplicação conta com validações nativas em tela, caixas de diálogo modais e bloqueio inteligente de botões e campos para evitar erros de integridade referencial durante o cadastro das regras.
