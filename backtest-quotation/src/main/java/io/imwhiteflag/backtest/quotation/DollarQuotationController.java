package io.imwhiteflag.backtest.quotation;

import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/quotation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DollarQuotationController {

    @Inject
    DollarQuotationService quotationService;

    @GET
    @Blocking
    @Path("/day")
    @Operation(summary = "Get Dollar Quotation for a Day", description = "Return dollar quotation for a specific day.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema()))
    @Parameter(name = "Day", in = ParameterIn.QUERY, description = "Quotation day for Querying")
    public Response getDayDollarQuotation(@QueryParam("day") String day) {
//
//        StringUtils.addQuotesToString(date);
//
//        var timestamp = Instant.now();
//        var response = cotacaoBcbService.getCotacaoDolarDiaria(dataMod, "json");
//        consultaService.persistCotacaoResponseAsEntity(response, timestamp);
//
        return null;
    }

    @GET
    @Blocking
    @Path("/period")
    @Operation(summary = "Get Dollar Quotation for a Period", description = "Return dollar quotation for a specific period.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema()))
    @Parameter(name = "initialDate", in = ParameterIn.QUERY, description = "Initial quotation day for Querying")
    @Parameter(name = "finalDate", in = ParameterIn.QUERY, description = "Final quotation day for Querying")
    public Response getPeriodDollarQuotation(@QueryParam("initialDate") String initialDate, @QueryParam("finalDate") String finalDate) {
        return null;
    }

    @GET
    @Blocking
    @Path("/listAll")
    @Operation(summary = "Get Dollar Quotation for all saved days",
            description = "Return dollar quotation for all days saved in our database.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema()))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema()))
    public Response listAllSavedDollarQuotation() {
        return null;
    }
}
