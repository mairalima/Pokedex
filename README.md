<div align="center">
   
   ![Variation=Logotype 02](https://github.com/colussicode/FinTrack/assets/54089435/c5b55d7c-2b31-450e-8dc5-86473e9566d1)

</div>

Pokedex - Equipe 10 - HackSprint 2025 - Devspace 
==========================
Evento de tecnologia promovido pelo Devspace.

# Sobre o Projeto
A proposta do app é apresentar uma lista de Pokémons e permitir que o usuário visualize detalhes completos de cada um deles. Além disso, o aplicativo conta com suporte offline e segue boas práticas de desenvolvimento.

# :camera_flash: Screenshots
<!-- You can add more screenshots here if you like -->
<img src="https://github.com/user-attachments/assets/5e9d3ac4-7ccc-4162-a9e9-68f64f2aa637" width=200/> <img src="https://github.com/user-attachments/assets/c05b26bb-967e-4483-8e8a-0fe5bdcd7df2" width=200/>

# Design
O design do aplicativo foi inspirado em uma interface limpa e objetiva, apresentando:
- Tela inicial: Exibe um grid de Pokémons com imagem e nome.
- Tela de detalhes: Mostra informações específicas e atributos do Pokémon selecionado.

# Funcionalidades
- Exibir lista de Pokémons com imagem e nome.
- Visualizar detalhes de cada Pokémon ao clicar na lista.
- Mostrar mensagens de erro e estado vazio quando aplicável.
- Offline Mode: Utiliza dados armazenados localmente para acesso sem internet.
- Arquitetura MVVM para melhor organização e manutenção do código.
- Integração com a API da Pokedex para buscar os dados.

# Tecnologias e Ferramentas Utilizadas
O projeto utiliza tecnologias do desenvolvimento Android para garantir um desempenho eficiente e uma experiência fluida:
- Linguagem: Kotlin;
- Layout: ConstraintLayout;
- Lista:
   - RecyclerView;
   - ListAdapter
   - DiffUtils
- Gerenciamento de Layout: GridLayoutManager / LinearLayoutManager;
- Navegação: Explicit Intent para navegação entre telas;
- Retrofit para consumo da API;
- Gson para conversão de dados JSON; 
- Room para armazenamento local (Offline Mode);
- Glide para carregamento de imagens

# Organização e Metodologia
O projeto foi dividido em etapas seguindo práticas ágeis durante a HackSprint, com uso de Planning, Dailies e Retrospectivas, simulando o ambiente real de desenvolvimento de software.

# HackSprint
Evento promovido pelo Devspace, com duração de duas semanas, focado em:
- Aprendizado colaborativo.
- Prática intensa de desenvolvimento.
- Simulação de um ambiente profissional.

# Contribuidores
- André Oliveira Brito Souza Vita
- Talita Evancely
- Maíra Lima
- Eunice
- Henrique Nunes

# Como Rodar o Projeto
- Clone o repositório:
- Abra o projeto no Android Studio
- Compile e execute no emulador ou dispositivo físico

# License

Now in Android is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
