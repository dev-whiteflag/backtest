# backtest
> backtest é um projeto feito para testar uma stack de backend normalmente usada com microserviços.

## Getting Started
Para começar, é só executar o `docker compose up` dentro da pasta `.docker`. Isso ira provisionar os containers necessários para executar a applicação feita em Quarkus.

### URLs e Credenciais
As credencias de conexão entre aplicações estarão no compose, mas em ambientes não-locais é NECESSÁRIO ser definido nas váraiveis de ambiente e/ou no k8s (ou equivalente na infraestrutura usada).

-  `prometheus`: http://localhost:9090/
-  `grafana`: http://localhost:3000/ _(admin:admin)_
-  `postgres`: http://localhost:5432

### Checklist TODO do Projeto
- [ ] Especificação do projeto
- [ ] Endpoint para consulta da cotação
- [ ] Endpoint para listagem das cotações salvas
- [ ] API documentada no Swagger
- [ ] Monitoração com Prometheus e Grafana exibindo métricas da API, do DB e da infra
- [ ] Tracing (Jaeger)
- [ ] Testes automatizados
- [ ] Docker + compose
- [ ] Frontend em Angular 9

## Especificação do Projeto

### `história-1`: Consultar cotação do Dolar no serviço do BCB
Quando o usuário acessar a interface do sistema e requisitar uma consulta de cotação do dolar, é necessário que o sistema consulte os endpoints do BCB (olinda) e retorne as informações para o usuário em seguida.
O usuário poderá requisitar todos os períodos arquivados no sistema, eles devem ser retornados de forma inteligente para a interface.
Para que a história seja aceita, os critérios abaixo devem ser levados em consideração:
- `critério-1`: O sistema deve consultar sem nenhum problema o `olinda` e retornar as informações pro usuário.
- `critério-2`: O sistema ao consultar deve salvar as informações retonadas pelo `olinda` e salvar no banco de dados, as informações a serem salvas estão anexadas na história (`anexo-1`).
- `critério-3`: O sistema deve verificar se o período consultado pelo usuário já foi consultado anteriormente (ou seja, existe no banco de dados) e se sim, retonar os dados salvos sem consultar o `olinda`.

Os casos de teste abaixo tambem devem ser levados em consideração:
- `caso-1`: A requisição do usuário com sucesso, sem existir dados do periodo no banco.
- `caso-2`: A requisição do usuário com sucesso, existindo os dados no banco.
- `caso-3`: A requisição do usuário com sucesso, com parte do periodo salvo no banco e o resto requisitado no `olinda`.
- `caso-4`: A requisição do usuário sem sucesso por dados insuficientes informados.
- `caso-5`: Deve ser retornados todos os periodos de cotação salvos no banco, utilizando `pagination`.

Anexos da História:
- `anexo-1`: ![UML](https://i.ibb.co/VHCG1JQ/Whats-App-Image-2022-08-28-at-6-04-11-PM.jpg)

### `história-2`: Tela para consulta da cotação do Dolar no sistema
Deverá existir uma tela de fácil acesso para a consulta da cotação do Dolar para que o usuário utilize multiplas vezes e regularmente. A tela deve conter um campo de entrada que, ao ser clicado, ira abrir um componente que o deixará selecionar um período; após seleção, um botão será ativado e ao apertar-lo irá consultar o sistema pelas cotações do periodo selecionado. As informações retornadas pelo sistema irá aparecer em baixo em uma tabela, que utilizará `pagination`.
O sistema tambem deverá deixar o usuário colar datas separadas por virgula como texto, assim dispençando a utilização do componente seletor. Foi feita um wireframe do UX para essa história e pode ser encontrada abaixo:

Anexos da História:
- `anexo-2`: 
![WIREFRAME](https://i.ibb.co/QmDHg4k/Whats-App-Image-2022-08-28-at-7-18-14-PM.jpg)