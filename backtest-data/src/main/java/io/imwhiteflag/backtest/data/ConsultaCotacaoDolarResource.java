package io.imwhiteflag.backtest.data;

import io.imwhiteflag.backtest.data.cotacaobcb.CotacaoDolarBcbRestService;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.Instant;

@Path("/cotacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaCotacaoDolarResource {

    @RestClient
    CotacaoDolarBcbRestService cotacaoBcbService;

    @Inject
    ConsultaCotacaoDolarService consultaService;

    @GET
    @Path("/dia")
    @Blocking
    public Response getCotacaoDolarDia(@QueryParam("data") String data) {
        String dataMod = "'" + data + "'";

        var timestamp = Instant.now();
        var response = cotacaoBcbService.getCotacaoDolarDiaria(dataMod, "json");
        consultaService.persistCotacaoResponseAsEntity(response, timestamp);

        return null;
    }

    @GET
    @Path("/periodo")
    public Response getCotacaoDolarPeriodo(@QueryParam("dataInicial") String dataInicial,
                                           @QueryParam("dataFinal") String dataFinal) {
        return null;
    }

    @GET
    @Path("/listar")
    public Response listarTodasCotacaoDolar(@QueryParam("data") String data) {
        return null;
    }
}
