package io.imwhiteflag.backtest.data;

import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/exchangeRate")
public class DollarExchangeRateResource {

    @RestClient
    DollarExchangeRateBcbRestService exchangeRateService;

    // 
}
