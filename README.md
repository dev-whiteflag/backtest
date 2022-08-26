# backtest
> backtest é um projeto feito para testar uma stack de backend normalmente usada com microserviços.

## Getting Started
Para começar, é só executar o `docker compose up` dentro da pasta `.docker`. Isso ira provisionar os containers necessários para executar a applicação feita em Quarkus.

### URLs e Credenciais
As credencias de conexão entre aplicações estarão no compose, mas em ambientes não-locais é NECESSÁRIO ser definido nas váraiveis de ambiente e/ou no k8s (ou equivalente na infraestrutura usada).
- `prometheus`: http://localhost:9090/
- `grafana`: http://localhost:3000/ _(admin:admin)_
- `postgres`: http://localhost:5432