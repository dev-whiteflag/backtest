package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBItem;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBRestResponse;
import io.imwhiteflag.backtest.quotation.service.DollarQuotationBCBRestService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class DollarQuotationControllerIT {

    private static final String CONTROLLER_PATH = "/quotation";

    @InjectMock
    @RestClient
    DollarQuotationBCBRestService bcbRestService;

    @Test
    public void testGetDateDollarQuotationNotSavedThenReturn200() {
        // Mocking getDayDollarQuotation
        var buyPrice = BigDecimal.valueOf(5.17670);
        var sellPrice = BigDecimal.valueOf(5.17730);
        var item = new DollarQuotationBCBItem(buyPrice, sellPrice, "2022-08-18 13:09:55.325");
        var response = new DollarQuotationBCBRestResponse(List.of(item));
        Mockito.when(bcbRestService.getDayDollarQuotation("'08-18-2022'", "json")).thenReturn(response);

        // Calling API getDateDollarQuotation endpoint
        given().when()
                .queryParam("date", "08-18-2022")
                .get(CONTROLLER_PATH + "/date")
          .then().statusCode(200)
                .body("quotationDateHour", is("2022-08-18T13:09:55.325"))
                .body("buyPrice", is(Matchers.equalTo(5.17670f)))
                .body("sellPrice", is(Matchers.equalTo(5.17730f)));
    }

    @Test
    public void testGetDateDollarQuotationThenReturn400() {
        // Calling API getDateDollarQuotation endpoint
        given().when()
                .queryParam("date", "")
                .get(CONTROLLER_PATH + "/date")
                .then().statusCode(400);
    }
}
