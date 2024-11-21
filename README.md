# TáLigado: GreenOn 🌿⚡

O **GreenOn** é uma solução da plataforma **TáLigado**, focada no gerenciamento de consumo de energia ⚡ e emissões de CO2 🌍 pelas **empresas**. Ele permite monitorar e controlar o uso de energia nas filiais 🏢, identificar padrões e promover práticas sustentáveis 🌱, ajudando a reduzir custos 💸 e impactos ambientais. O objetivo é tornar as empresas mais sustentáveis, otimizar recursos e minimizar as emissões de carbono 🌍.

## ⚙️ Funcionalidades

- **Monitoramento de Consumo de Energia ⚡**  
  Acompanhe em tempo real o consumo de energia elétrica nas suas filiais, com dados detalhados para cada unidade 🔌.

- **Gestão de Emissões de Gás Carbônico 🌿**  
  Monitore as emissões de CO2 associadas ao consumo de energia e tome ações para reduzir o impacto ambiental 🌎.

- **Cadastro de Empresas 🏢**  
  As empresas podem se cadastrar na plataforma para gerenciar suas filiais, monitorando o consumo e as emissões de todas as unidades de forma centralizada.

- **Relatórios e Análises Detalhadas 📊**  
  Obtenha relatórios completos sobre o consumo de energia e as emissões de gases em cada filial, ajudando a identificar padrões e áreas de melhoria 📉.

## 📚 Estrutura do Banco de Dados

A aplicação utiliza um modelo de banco de dados relacional para gerenciar as informações dos branch office, devices e suas interações. Abaixo está o diagrama de Entidade e Relacionamento com as tabelas principais da estrutura do banco de dados:

```mermaid
erDiagram
    DEVICE {
        NUMBER id PK
        VARCHAR2 nome
        VARCHAR2 imagemurl
        FLOAT potencia_nominal
        FLOAT tempo_uso
        FLOAT fator_emissao
        VARCHAR2 departamento
    }

    BRANCH_OFFICE {
        NUMBER id PK
        VARCHAR2 nome
        VARCHAR2 endereco
        VARCHAR2 telefone
        VARCHAR2 segmento
        FLOAT consumo_energia
        FLOAT emissoes_carbono
    }

    ROLES {
        NUMBER id PK
        VARCHAR2 role_name
    }

    USER_ENTERPRISE {
        NUMBER id PK
        VARCHAR2 nome
        VARCHAR2 username
        VARCHAR2 password
        VARCHAR2 email
        VARCHAR2 cnpj
        VARCHAR2 img_perfil
    }

    BRANCH_OFFICE_DEVICE {
        NUMBER branch_office_id FK
        NUMBER device_id FK
    }

    USER_ROLES {
        NUMBER id_user FK
        NUMBER id_role FK
    }

  BRANCH_OFFICE ||--o{ BRANCH_OFFICE_DEVICE : "possui"
    DEVICE ||--o{ BRANCH_OFFICE_DEVICE : "usa"
    USER_ENTERPRISE ||--o{ USER_ROLES : "tem"
    ROLES ||--o{ USER_ROLES : "atribui"

```
---

## 🧪 Testes Unitários

A aplicação **GreenOn** inclui uma suíte de testes unitários que garante a qualidade e o funcionamento adequado dos serviços. Foram implementados testes para as classes, `UserEnterprise`, `BranchOffice`, `Device`, `UserEnterprise`, `BranchOfficeService` e `DeviceService` utilizando **JUnit** e **Mockito**. 

No total, 42 testes foram executados com sucesso, confirmando que as funcionalidades principais estão funcionando conforme o esperado.

![Terminal com comando `mvn clean test`](https://github.com/user-attachments/assets/bb2914bf-b746-43b0-8c5f-85de1af731db)

---
## 🔑 Instruções de Acesso

> [!WARNING]  
> **💡 Observação:** Verifique as permissões atribuídas a cada tipo de usuário.


### 👑 ADMIN padrão

- **Usuário padrão (ADMIN)**:

```plaintext
Nome: Admin TáLigado
Email: admin@taligado.com
Username: admin_taligado
Imagem de Perfil: https://abrir.link/eiEEK
Senha: fiap123 (criptografada no banco)
CNPJ: 12.345.678/0001-90
Função: ADMIN
```

> [!IMPORTANT]  
> **⚠️ Importante**: O administrador tem permissões completas para gerenciar todos os aspectos do sistema, incluindo o gerenciamento de usuários e dispositivos.


🧑‍💻 **Acesso para Usuário Padrão (USER)**

- **Usuário padrão (USER)** pode criar usuários comuns, como:

```plaintext
Nome: FIAP
Email: devops@fiap.com
Username: fiap@devops
Imagem de Perfil: https://abrir.link/EgrNu
Senha: fiap2024 (criptografada no banco)
CNPJ: 22.000.678/0001-90
Função: USER
```

> [!NOTE]
> 📝 **Atenção:** As senhas são **criptografadas** no banco de dados utilizando o algoritmo **bcrypt**. A senha fornecida acima é a original, mas será armazenada de forma segura no banco de dados.


---

## CRUD - Exemplos 📋

### 1. Criar ➕

#### *Dispositivo* ⚡

Para criar um novo Dispositivo, você deve enviar um formulário com as seguintes informações:

```plaintext
Nome: Monitor
Departamento: TI
Potência Nominal: 50.0 (em watts)
Tempo de Uso: 1000.0 (em horas)
Imagem URL: https://abrir.link/yLVET
Fator de Emissão: 1 (em kgCO2/kWh)
```

#### *Filial* 🏢

Para criar uma nova filial, você pode usar o seguinte formulário:

```plaintext
Nome: Filial São Paulo
Endereço: Rua das Indústrias, 123
Telefone: 912345678
Segmento: INDUSTRIA 
Dispositivos: Monitor
```

### 2. Ler 📖

#### *Dispositivo* 🔍

Para **ler** os detalhes de um dispositivo específico, aperte em 'Detalhes'.

#### *Filiais* 📅

Para **ler** os detalhes de uma filial específica, aperte em 'Detalhes'.

### 3. Atualizar ✏️

#### *Dispositivo* ✨

Para **atualizar** as informações de um dispositivo existente, você pode enviar o seguinte formulário:

```plaintext
Nome: Monitor LG
Departamento: ADMINISTRATIVO
Potência Nominal: 35.0 (em watts)
Tempo de Uso: 1500.0 (em horas)
Imagem URL: https://abrir.link/yLVET
Fator de Emissão: 1 (em kgCO2/kWh)
```

#### *Filial* 🏢

Para **atualizar** uma filial existente, você pode usar o seguinte formulário:

```plaintext
Nome: Filial São Paulo
Endereço: Rua dos Comercios, 123
Telefone: 1234-5678
Segmento: COMERCIO
Dispositivos: Monitor, Lâmpada LED
```

### 4. Excluir 🗑️

#### *Dispositivo* 🛑

Para **excluir** um dispositivo, aperte em 'Excluir'.

#### *Filiais* 🏠

Para **excluir** uma filial, aperte em 'Excluir'.
---

## 🫂 Integrantes

Aqui estão os membros do grupo que participaram durante desenvolvimento desta GS.

* **RM 552258 - Laís Alves da Silva Cruz**
  - Turma: 2TDSPH

* **RM 552267 - Bianca Leticia Román Caldeira**
  - Turma: 2TDSPH

* **RM 552252 – Charlene Aparecida Estevam Mendes Fialho**
  - Turma: 2TDSPH

* **RM 97916 – Fabricio Torres Antonio**
  - Turma: 2TDSPH

* **RM 99675 – Lucca Raphael Pereira dos Santos**
  - Turma: 2TDSPZ
    
<table>
  <tr>
      <td align="center">
      <a href="https://github.com/laiscrz">
        <img src="https://avatars.githubusercontent.com/u/133046134?v=4" width="100px;" alt="Lais Alves's photo on GitHub"/><br>
        <sub>
          <b>Lais Alves</b>
        </sub>
      </a>
      </td>
      <td align="center">
      <a href="https://github.com/biancaroman">
        <img src="https://avatars.githubusercontent.com/u/128830935?v=4" width="100px;" border-radius='50%' alt="Bianca Román's photo on GitHub"/><br>
        <sub>
          <b>Bianca Román</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/charlenefialho">
        <img src="https://avatars.githubusercontent.com/u/94643076?v=4" width="100px;" border-radius='50%' alt="Charlene Aparecida's photo on GitHub"/><br>
        <sub>
          <b>Charlene Aparecida</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Fabs0602">
        <img src="https://avatars.githubusercontent.com/u/111320639?v=4" width="100px;" border-radius='50%' alt="Fabricio Torres's photo on GitHub"/><br>
        <sub>
          <b>Fabricio Torres</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/LuccaRaphael">
        <img src="https://avatars.githubusercontent.com/u/127765063?v=4" width="100px;" border-radius='50%' alt="Lucca Raphael's photo on GitHub"/><br>
        <sub>
          <b>Lucca Raphael</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

---

> Esse guia detalha o processo de configuração do CI/CD, desde a criação do projeto no Azure até a configuração do pipeline de build e release, facilitando a automação do deploy e a entrega contínua da aplicação **GreenOn**. ✨🚀
