package io.imwhiteflag.backtest.quotation.service;

import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBRestResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "olinda.quotation")
public interface DollarQuotationBCBRestService {
    @GET
    @Path("/CotacaoDolarDia(dataCotacao=@dataCotacao)")
    DollarQuotationBCBRestResponse getDayDollarQuotation(@QueryParam("@dataCotacao") String quotationDate,
                                                         @QueryParam("$format") String responseFormat);

    @GET
    @Path("/CotacaoDolarPeriodo(dataInicial=@dataInicial,dataFinalCotacao=@dataFinalCotacao)")
    DollarQuotationBCBRestResponse getPeriodDollarQuotation(@QueryParam("@dataInicial") String quotationInitialDate,
                                                            @QueryParam("@dataFinalCotacao") String quotationFinalDate,
                                                            @QueryParam("$format") String responseFormat,
                                                            @QueryParam("$top") Integer max,
                                                            @QueryParam("$skip") Integer first);
}
