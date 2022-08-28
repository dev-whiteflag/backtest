package io.imwhiteflag.backtest.data;

import io.imwhiteflag.backtest.data.models.DollarExchangeRateResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
@Path("olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata") // WIP: get this from a configuration in properties
public interface DollarExchangeRateBcbRestService {
    @GET
    @Path("/CotacaoDolarDia(dataCotacao=@dataCotacao)")
    DollarExchangeRateResponse getDailyExchangeRate(@QueryParam("@dataCotacao") String exchangeDate,
                                                    @QueryParam("$format") String responseFormat);

    @GET
    @Path("/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)")
    DollarExchangeRateResponse getPeriodExchangeRate(@QueryParam("@dataInicial") String exchangeInitialDate,
                                                     @QueryParam("@dataFinalCotacao") String exchangeFinalDate,
                                                     @QueryParam("$format") String responseFormat);

}
