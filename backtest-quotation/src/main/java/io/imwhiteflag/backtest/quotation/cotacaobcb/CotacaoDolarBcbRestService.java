package io.imwhiteflag.backtest.quotation.cotacaobcb;

import io.imwhiteflag.backtest.quotation.cotacaobcb.models.CotacaoDolarBcbRestResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "olinda.cotacao")
public interface CotacaoDolarBcbRestService {
    @GET
    @Path("/CotacaoDolarDia(dataCotacao=@dataCotacao)")
    CotacaoDolarBcbRestResponse getCotacaoDolarDiaria(@QueryParam("@dataCotacao") String exchangeDate,
                                                      @QueryParam("$format") String responseFormat);

    @GET
    @Path("/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)")
    CotacaoDolarBcbRestResponse getCotacaoDolarPeriodo(@QueryParam("@dataInicial") String exchangeInitialDate,
                                                       @QueryParam("@dataFinalCotacao") String exchangeFinalDate,
                                                       @QueryParam("$format") String responseFormat);

}
