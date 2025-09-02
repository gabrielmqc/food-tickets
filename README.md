🍽️ Food Tickets

Aplicação para gerenciamento de tickets de alimentação dos funcionários de uma empresa.

🚀 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado na sua máquina:

Docker

Docker Compose

📦 Como rodar a aplicação

Clone este repositório

git clone https://github.com/gabrielmqc/food-tickets.git
cd food-tickets


Suba os containers com o Docker Compose

docker-compose up --build


Esse comando irá:

Criar e iniciar o banco de dados MySQL

Iniciar a API da aplicação

Iniciar a interface web (UI)

Acesse a aplicação

UI (frontend): http://localhost:3000

API (backend): http://localhost:8080

🛠️ Estrutura dos serviços

MySQL → Banco de dados da aplicação

API (Spring Boot) → Responsável pela lógica de negócio

Frontend (React/Thymeleaf) → Interface para interação do usuário

🔧 Comandos úteis

Parar os serviços

docker-compose down


Parar e remover volumes (dados do banco serão apagados)

docker-compose down -v


Reconstruir apenas um serviço específico

docker-compose build <nome-do-servico>
