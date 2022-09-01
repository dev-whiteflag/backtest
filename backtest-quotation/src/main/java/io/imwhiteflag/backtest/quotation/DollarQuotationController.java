package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DollarQuotationResponse;
import io.imwhiteflag.backtest.quotation.service.DollarQuotationService;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
    @Path("/date")
    @Transactional
    @Operation(summary = "Get Dollar Quotation for a Date", description = "Return dollar quotation for a specific date.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = DollarQuotationResponse.class)))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ServerErrorException.class)))
    @Parameter(name = "Date", in = ParameterIn.QUERY, description = "Quotation date for Querying")
    public Response getDateDollarQuotation(@QueryParam("date") String date) {
        if (!BacktestQuotationUtils.validateString(date)) throw new BadRequestException("Date field is null or empty.");
        if (!BacktestQuotationUtils.validateDateFormat(date)) throw new BadRequestException("Date field is invalid.");

        var quotation = quotationService.getDollarQuotationFromBCB(date);
        var response = DollarQuotationResponse.fromEntity(quotation);

        return Response.ok(response).build();
    }

    @GET
    @Blocking
    @Path("/period")
    @Operation(summary = "Get Dollar Quotation for a Period", description = "Return dollar quotation for a specific period.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = DollarQuotationResponse.class)))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ServerErrorException.class)))
    @Parameter(name = "initialDate", in = ParameterIn.QUERY, description = "Initial quotation day for Querying")
    @Parameter(name = "finalDate", in = ParameterIn.QUERY, description = "Final quotation day for Querying")
    @Parameter(name = "skip", in = ParameterIn.QUERY, description = "Number of quotation to skip")
    @Parameter(name = "max", in = ParameterIn.QUERY, description = "Max quotations to return")
    public Response getPeriodDollarQuotation(@QueryParam("initialDate") String initialDate, @QueryParam("finalDate") String finalDate,
                                             @QueryParam("skip") Integer skip, @QueryParam("max") Integer max) {
        if (!BacktestQuotationUtils.validateString(initialDate) || !BacktestQuotationUtils.validateString(finalDate))
            throw new BadRequestException("Initial or Final Date field is null or empty.");

        if (!BacktestQuotationUtils.validateDateFormat(initialDate) || !BacktestQuotationUtils.validateDateFormat(finalDate))
            throw new BadRequestException("Initial or Final Date field is invalid.");

        if (!BacktestQuotationUtils.validatePaginationParams(skip, max)) throw new BadRequestException("Invalid pagination parameters.");

        var quotations = quotationService.getDollarQuotationFromBCB(initialDate, finalDate, skip, max);
        var response = DollarQuotationResponse.fromEntity(quotations);

        return Response.ok(response).build();
    }

    @GET
    @Blocking
    @Path("/listAll")
    @Operation(summary = "Get Dollar Quotation for all saved days",
            description = "Return dollar quotation for all days saved in our database.")
    @APIResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = DollarQuotationResponse.class)))
    @APIResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = BadRequestException.class)))
    @APIResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ServerErrorException.class)))
    @Parameter(name = "pageIndex", in = ParameterIn.QUERY, description = "Page index for Querying")
    @Parameter(name = "itemsPerPage", in = ParameterIn.QUERY, description = "Items per Page for Querying")
    public Response listAllSavedDollarQuotation(@QueryParam("pageIndex") Integer pageIndex,
                                                @QueryParam("itemsPerPage") Integer itemsPerPage) {
        if (!BacktestQuotationUtils.validatePaginationParams(pageIndex, itemsPerPage))
            throw new BadRequestException("Invalid pagination parameters.");

        var saved = quotationService.getAllSavedDollarQuotation(pageIndex, itemsPerPage);
        var response = DollarQuotationResponse.fromEntity(saved);

        return Response.ok(response).build();
    }
}
