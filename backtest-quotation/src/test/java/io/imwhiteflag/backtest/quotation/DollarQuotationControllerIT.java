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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class DollarQuotationControllerIT {

    private static final String CONTROLLER_PATH = "/quotation";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    @InjectMock
    @RestClient
    DollarQuotationBCBRestService bcbRestService;

    private final List<DollarQuotation> testDollarQuotations = new ArrayList<>();

    @Test
    public void testGetDateDollarQuotationMockThenReturn200() {
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
                .body("quotationDateHour", is(quotation.getQuotationDateHour().toString())) // TODO: format using iso
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
    public void testGetPeriodDollarQuotationMockAndSavedThenReturn200() {
        // Mocking getPeriodDollarQuotation and getDayDollarQuotation
        var periodResponseItemList = List.of(DollarQuotationTestFactory.fromBCBToDollarQuotation(
                DollarQuotationTestFactory.createNewDollarQuotationWithoutPersist("08-26-2022")), DollarQuotationTestFactory.fromBCBToDollarQuotation(
                DollarQuotationTestFactory.createNewDollarQuotationWithoutPersist("08-27-2022")));
        var dayResponseItem = List.of(DollarQuotationTestFactory.fromBCBToDollarQuotation(
                DollarQuotationTestFactory.createNewDollarQuotationWithoutPersist("08-24-2022")));

        Mockito.when(bcbRestService.getDayDollarQuotation("'08-24-2022'", "json")).thenReturn(
                new DollarQuotationBCBRestResponse(dayResponseItem));
        Mockito.when(bcbRestService.getPeriodDollarQuotation("'08-26-2022'", "'08-27-2022'", "json",100,
                0)).thenReturn(new DollarQuotationBCBRestResponse(periodResponseItemList));

        // Building query params
        Map<String, Object> params = new HashMap<>();
        params.put("initialDate", "08-24-2022");
        params.put("finalDate", "08-27-2022");
        params.put("skip", 0);
        params.put("max", 100);

        // Calling API getPeriodDollarQuotation endpoint
        given().when()
                .queryParams(params)
                .get(CONTROLLER_PATH + "/period").peek()
                .then().statusCode(200)
                .body("quotations.size()", is(4)); // TODO: look into why there's ELEVEN
    }

    @Test
    public void testGetPeriodDollarQuotationSavedThenReturn200() {
        // Building query params
        Map<String, Object> params = new HashMap<>();
        params.put("initialDate", "asdas");
        params.put("finalDate", "sadas");
        params.put("skip", 0);
        params.put("max", 1);

        // Calling API getPeriodDollarQuotation endpoint
        given().when()
                .queryParams(params)
                .get(CONTROLLER_PATH + "/period")
                .then().statusCode(400);
    }

    @Test
    public void testGetPeriodDollarQuotationThenReturn400() {
        // Building query params
        Map<String, Object> params = new HashMap<>();
        params.put("initialDate", "asdas");
        params.put("finalDate", "sadas");
        params.put("skip", 0);
        params.put("max", 1);

        // Calling API getPeriodDollarQuotation endpoint
        given().when()
                .queryParams(params)
                .get(CONTROLLER_PATH + "/period")
                .then().statusCode(400);
    }

    @Test
    public void testlistAllSavedDollarQuotationThenReturn200() {
        // Calling API listAllSavedDollarQuotation endpoint
        given().when()
                .queryParam("pageIndex", "0")
                .queryParam("itemsPerPage", "10")
                .get(CONTROLLER_PATH + "/listAll")
                .then().statusCode(200)
                .body("quotations.size()", is(3));
    }

    @Test
    public void testlistAllSavedDollarQuotationThenReturn400() {
        // Calling API listAllSavedDollarQuotation endpoint
        // This test a hard-coded limit for items per page, this should be a configuration item but will do for now.
        given().when()
                .queryParam("pageIndex", "0")
                .queryParam("itemsPerPage", "101")
                .get(CONTROLLER_PATH + "/listAll")
                .then().statusCode(400);
    }

    @BeforeEach
    @Transactional
    public void createTestingDataOnDatabase() {
        testDollarQuotations.add(DollarQuotationTestFactory.createNewDollarQuotation());
        testDollarQuotations.add(DollarQuotationTestFactory.createNewDollarQuotation());
        testDollarQuotations.add(DollarQuotationTestFactory.createNewDollarQuotation("08-25-2022"));
    }
}
