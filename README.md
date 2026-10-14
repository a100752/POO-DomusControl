# DomusControl — Smart Home Management System

Sistema de gestão e automação residencial inteligente desenvolvido em **Java**, focado em conceitos avançados de **Programação Orientada a Objetos (POO)**, robustez arquitetural e persistência de dados.

O projeto foi desenvolvido no âmbito da Licenciatura em Engenharia Informática da **Universidade do Minho**.

---

## 🌟 Visão Geral

O **DomusControl** é uma plataforma que permite modelar, controlar e automatizar dispositivos inteligentes distribuídos por habitações e divisões, suportando múltiplos utilizadores com diferentes níveis de permissão e regras de automação avançadas.

### Principais Funcionalidades

- **Gestão Hierárquica de Espaços**: Modelação de casas, divisões e catálogo de dispositivos (lâmpadas, termostatos, colunas de som inteligentes, etc.).
- **Perfis de Utilizador e Controlo de Acesso**: Suporte a múltiplos utilizadores com papéis bem definidos (ex: Administrador, Morador, Convidado) e permissões por habitação.
- **Motor de Automações & Cenários**:
  - Execução de ações condicionadas por estados de sensores ou eventos.
  - Escalonamento temporal de rotinas (agendamentos diários, semanais ou pontuais).
  - Cenários pré-configurados (ex: "Sair de Casa", "Modo Noite", "Cinema").
- **Persistência de Estado**:
  - Serialização binária em ficheiros `.dat` para salvaguarda de sessão entre execuções.
  - Parser e bootstrapping declarativo a partir de ficheiros de texto estruturados (`.txt`).
- **Tratamento de Exceções**: Sistema robusto de exceções personalizadas para validação de integridade do domínio.

---

## 🏗️ Arquitetura e Padrões de Desenho

A aplicação adota o princípio de separação de responsabilidades (SoC):

- `model`: Núcleo de domínio e lógica de negócio.
  - `casa`: Agregação de divisões e associação de dispositivos.
  - `dispositivo`: Hierarquia polimórfica de periféricos e respetivos estados operacionais.
  - `utilizador`: Modelos de perfil, autenticação e privilégios.
  - `automacao`: Modelação de cenários, ações e escalonamentos.
- `ui`: Camada de apresentação textual interativa (menus modulares, controladores de fluxo e visualização de dados).
- `exceptions`: Domínio de exceções específicas (e.g. `CasaAlreadyExistsException`, `DispositivoDoesNotExistException`).
- `estado`: Gestão de carregamento e salvaguarda do estado do sistema.

---

## 🚀 Como Compilar e Executar

### Pré-requisitos
- **Java Development Kit (JDK) 17** ou superior instalado.

### Compilação
A partir da raiz do projeto:

```bash
mkdir -p bin
javac -d bin -sourcepath src $(find src -name "*.java")
```

### Execução
Para iniciar a aplicação:

```bash
java -cp bin Main
```

*(O sistema pode carregar opcionalmente o estado padrão fornecido em `estado83.dat` ou `estado8_3.txt`)*.

---

## 📄 Documentação Técnica

Para uma análise detalhada da especificação técnica, diagramas de classes e justificações de desenho arquitetural, consulte o relatório oficial:
- [Relatório Técnico DomusControl (PDF)](Relatorio_DomusControl.pdf)
