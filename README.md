# backtest
> backtest é um projeto feito para testar uma stack de backend normalmente usada com microserviços.

## Getting Started

Para executar o projeto todo, precisamos gerar os containers para o `quotation` e o `app`.

- Para o `quotation`, primeiro entre na pasta raiz do serviço e execute o `mvn package`; Logo em seguida, crie a imagem utilizando o comando `docker build -f ./src/main/docker/Dockerfile.jvm -t imwhiteflag/quotation:1.0.0 .`. Após ser executado, essa imagem estará salva localmente e será utilizada no compose.

- Para o `app`, primeiro entre na pasta raiz da aplicação e execute o `npm run build --prod`; Logo em seguida crie a imagem do mesmo jeito que anteriormente, utilizando o `docker build -f ./Dockerfile -t imwhiteflag/quotation-app:0.1.0 .`.

Agora que as imagens faltando foram criadas, precisa entrar na pasta `./docker` e nela executar o `docker compose up, esse comando ira criar todos os containers necessários para a aplicação.

### Módulos do Projeto

- `backtest-quotation`: Microserviço construido em Quarkus `latest` para consulta e retorno da cotação do dolar.
- `backtest-app`: Interface construida utilizando Angular 9, Material-UI para demonstração visual do `backtest-quotation`.

### URLs e Credenciais
As credencias de conexão entre aplicações estarão no compose, mas em ambientes não-locais é NECESSÁRIO ser definido nas váraiveis de ambiente e/ou no k8s (ou equivalente na infraestrutura usada).

-  `quotation`: http://localhost:8080/
-  `prometheus`: http://localhost:9090/
-  `grafana`: http://localhost:3000/ _(admin:admin)_
-  `postgres`: http://localhost:5432
-  `jaeger-ui`: http://localhost:16686

### Checklist TODO do Projeto
- [x] Especificação do projeto
- [x] Endpoint para consulta da cotação
- [x] Endpoint para listagem das cotações salvas
- [x] API documentada no Swagger
- [x] Monitoração com Prometheus e Grafana exibindo métricas da API [x], do DB [x] e da infra [ ]
- [x] Tracing (Jaeger)
- [x] Testes automatizados
- [x] Docker + compose
- [ ] Frontend em Angular 9

## Especificação do Projeto

### `história-1`: Consultar cotação do Dolar no serviço do BCB
Quando o usuário acessar a interface do sistema e requisitar uma consulta de cotação do dolar, é necessário que o sistema consulte os endpoints do BCB (olinda) e retorne as informações para o usuário em seguida.
O usuário poderá requisitar todos os períodos arquivados no sistema, eles devem ser retornados de forma inteligente para a interface.
Para que a história seja aceita, os critérios abaixo devem ser levados em consideração:

Anexos da História:
- `anexo-1`: ![UML](https://i.ibb.co/VHCG1JQ/Whats-App-Image-2022-08-28-at-6-04-11-PM.jpg)

### `história-2`: Tela para consulta da cotação do Dolar no sistema
Deverá existir uma tela de fácil acesso para a consulta da cotação do Dolar para que o usuário utilize multiplas vezes e regularmente. A tela deve conter um campo de entrada que, ao ser clicado, ira abrir um componente que o deixará selecionar um período; após seleção, um botão será ativado e ao apertar-lo irá consultar o sistema pelas cotações do periodo selecionado. As informações retornadas pelo sistema irá aparecer em baixo em uma tabela, que utilizará `pagination`.
O sistema tambem deverá deixar o usuário colar datas separadas por virgula como texto, assim dispençando a utilização do componente seletor. Foi feita um wireframe do UX para essa história e pode ser encontrada abaixo:

Anexos da História:
- `anexo-2`: 
![WIREFRAME](https://i.ibb.co/QmDHg4k/Whats-App-Image-2022-08-28-at-7-18-14-PM.jpg)

### Critérios de Aprovação
- `critério-1`: O sistema deve consultar sem nenhum problema o `olinda` e retornar as informações pro usuário; caso as informações existem no banco de dados, não consultar no `olinda`.
- `critério-2`: O sistema ao consultar deve salvar as informações retonadas pelo `olinda` e salvar no banco de dados, as informações a serem salvas estão anexadas na `história-1` (`anexo-1`).
- `critério-3`: O sistema deve retornar páginado todas as informações salvas que foram retornadas do `olinda`; todas as informações devem ser mostradas na interface.

### Casos de Teste
- `caso-1`: A requisição do usuário com sucesso, sem existir dados do periodo no banco.
- `caso-2`: A requisição do usuário com sucesso, existindo os dados no banco.
- `caso-3`: A requisição do usuário com sucesso, com parte do periodo salvo no banco e o resto requisitado no `olinda`.
- `caso-4`: A requisição do usuário sem sucesso por dados insuficientes informados.
- `caso-5`: Deve ser retornados todos os periodos de cotação salvos no banco, utilizando `pagination`.