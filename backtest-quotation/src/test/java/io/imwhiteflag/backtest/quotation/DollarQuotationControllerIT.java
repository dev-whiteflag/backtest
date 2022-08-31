package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DollarQuotation;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBItem;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBRestResponse;
import io.imwhiteflag.backtest.quotation.service.DollarQuotationBCBRestService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DollarQuotationControllerIT {

    private static final String CONTROLLER_PATH = "/quotation";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private final DateTimeFormatter bcbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @InjectMock
    @RestClient
    DollarQuotationBCBRestService bcbRestService;

    private final List<DollarQuotation> testDollarQuotations = new ArrayList<>();

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
    public void testGetDateDollarQuotationSavedThenReturn200() {
        // Calling API getDateDollarQuotation endpoint
        var quotation = testDollarQuotations.get(0);
        var date = formatter.format(quotation.getQuotationDate());

        // NOTE: when BigDecimal is used on a h2 database, it loses precision even if is specified.
        given().when()
                .queryParam("date", date)
                .get(CONTROLLER_PATH + "/date")
                .then().statusCode(200)
                .body("quotationDateHour", is(quotation.getQuotationDateHour().toString()))
                .body("buyPrice", is(Matchers.equalTo(quotation.getBuyPrice().floatValue())))
                .body("sellPrice", is(Matchers.equalTo(quotation.getSellPrice().floatValue())));
    }

    @Test
    public void testGetDateDollarQuotationThenReturn400() {
        // Calling API getDateDollarQuotation endpoint
        given().when()
                .queryParam("date", "")
                .get(CONTROLLER_PATH + "/date")
                .then().statusCode(400);
    }

    @Test
    public void testlistAllSavedDollarQuotation() {

    }

    @BeforeEach
    @Transactional
    public void createTestingDataOnDatabase() {
        testDollarQuotations.add(DollarQuotationTestFactory.createNewDollarQuotation());
        testDollarQuotations.add(DollarQuotationTestFactory.createNewDollarQuotation());
    }
}
