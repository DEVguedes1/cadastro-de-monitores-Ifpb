# 🎓 SISMON - Sistema de Gestão de Monitoria

![Java](https://img.shields.io/badge/Java-21%2B-orange) ![License](https://img.shields.io/badge/License-MIT-blue) ![Status](https://img.shields.io/badge/Status-Finalizado-green)

> Sistema desktop completo para gerenciamento de processos seletivos de monitoria do curso de ADS (IFPB).

![Logo do Sistema](src/images/logo.png)
*(Dica: Coloque a imagem da logo dentro da pasta `src/images` do projeto e nomeie como `logo.png` para aparecer aqui)*

---

## 📄 Sobre o Projeto

O **SISMON** foi desenvolvido para automatizar e organizar a seleção de monitores. Ele elimina o uso de planilhas manuais, permitindo que coordenadores lancem editais e alunos se inscrevam de forma autônoma. O sistema calcula automaticamente a classificação baseada na **Nota da Disciplina** e no **CRE** do aluno.

---

## ✨ Funcionalidades

### 👨‍🏫 Módulo do Coordenador
- [x] **Gestão de Editais:** Criar, Editar, Clonar e Excluir editais.
- [x] **Configuração de Disciplinas:** Definir vagas, pesos (Nota vs CRE) e docentes.
- [x] **Ranking Automático:** O sistema calcula a nota final e ordena os aprovados.
- [x] **Relatórios em PDF:** Geração de lista oficial de resultados com um clique.
- [x] **Notificação via E-mail:** Envia avisos automáticos para os candidatos.
- [x] **Backup na Nuvem:** Integração com Google Drive/Dropbox (via exportação de arquivo) e Backup por E-mail.

### 👨‍🎓 Módulo do Aluno
- [x] **Inscrição Simplificada:** Visualização de editais abertos e candidatura rápida.
- [x] **Acompanhamento:** Status em tempo real (Concorrendo, Aprovado, Lista de Espera).
- [x] **Histórico:** Visualização de todas as monitorias passadas.
- [x] **Gestão de Perfil:** Edição de dados cadastrais e senha.

---

## 🛠️ Tecnologias Utilizadas

O projeto foi construído seguindo o padrão de arquitetura **MVC (Model-View-Controller)**.

* **Linguagem:** [Java 21](https://www.oracle.com/java/)
* **Interface Gráfica:** Java Swing (com Design System personalizado "Flat Design").
* **Persistência de Dados:** [XStream](https://x-stream.github.io/) (Banco de dados em XML).
* **Geração de Relatórios:** [iText PDF](https://itextpdf.com/).
* **Envio de E-mails:** [Jakarta Mail](https://eclipse-ee4j.github.io/mail/) + Angus Activation.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Uma IDE Java (Eclipse recomendado ou IntelliJ/VSCode).

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/DEVguedes1/cadastro-de-monitores-Ifpb.git](https://github.com/DEVguedes1/cadastro-de-monitores-Ifpb.git)
    ```

2.  **Importe no Eclipse:**
    * Abra o Eclipse.
    * Vá em `File` > `Open Projects from File System`.
    * Selecione a pasta do projeto clonado.

3.  **Configuração das Bibliotecas:**
    * O projeto utiliza bibliotecas externas (`xstream.jar`, `itextpdf.jar`, `jakarta.mail.jar`).
    * Certifique-se de que elas estão na pasta `lib` e adicionadas ao **Build Path** do projeto.

4.  **Execute a Aplicação:**
    * Localize a classe `src/program/Main.java`.
    * Clique com o botão direito > `Run As` > `Java Application`.

---

## 📸 Screenshots

*(Espaço reservado para prints das telas do sistema - Login, Dashboard, Ranking)*

---

## 👥 Equipe de Desenvolvimento

Este projeto foi desenvolvido como requisito avaliativo do curso de Análise e Desenvolvimento de Sistemas.

* **Nicolas Guedes** - *Desenvolvedor Front-end & UX*
* **[Nome do Aluno 2]** - *Arquiteto de Software & Backend*
* **[Nome do Aluno 3]** - *Analista de Regras de Negócio & QA*

---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---
Feito com ❤️ por **Equipe Sismon**.