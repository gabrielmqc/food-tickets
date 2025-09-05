🍽️ Food Tickets

Aplicação para gerenciamento de tickets de alimentação dos funcionários de uma empresa.

🚀 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado na sua máquina:

  Docker

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
  
  Frontend (React) → Interface para interação do usuário

Sobre a Arquitetura escolhida:
  Para a API escolhi seguir os principios da Clean Arch, focando em um código desacoplado e separado em camadas, acredito que essa arquitetura seja muito poderosa para construir sistemas duradouros e de facil manutenção, e em conjunto a uma linguagem que já é orientada a Objetos como é o caso do Java ou c#
  se mostra ainda mais funcional.
  Junto da arquitetura também apliquei alguns conceitos da Orientação a Objetos no meu dominio e application, utlizando de classes abstratas e interfaces genéricas para criar um ambiente de facil expansão, e também encapsulando a lógica de negócio dentro do meu dominio, indepente de frameworks

