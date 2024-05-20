package com.demo.tiny_url.controller;

import com.demo.tiny_url.model.CreateShortUrlRequest;
import com.demo.tiny_url.model.CreateShortUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface Controller {
    @Operation(summary = "Redirects to the url pointed to by id", description = "Fetches long url pointed by id and redirects to it.", tags={ "miniurl" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "redirect to original url"),

            @ApiResponse(responseCode = "400", description = "client side error - validation failed"),

            @ApiResponse(responseCode = "404", description = "not found"),

            @ApiResponse(responseCode = "429", description = "ratelimit exceeded"),

            @ApiResponse(responseCode = "500", description = "server side error") })
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    ResponseEntity<Void> resolveUrl(@Parameter(in = ParameterIn.PATH, description = "ID of the miniURL", required=true, schema=@Schema()) @PathVariable("id") String id);

    @Operation(summary = "Creates a shorter URL.", description = "Idempotently creates the shorter URL.(Multiple calls just update expires_at)", tags={ "miniurl" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created Successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateShortUrlResponse.class))),

            @ApiResponse(responseCode = "400", description = "client side error - validation failed"),

            @ApiResponse(responseCode = "429", description = "ratelimit exceeded."),

            @ApiResponse(responseCode = "500", description = "server side error.") })
    @RequestMapping(value = "/create",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PUT)
    ResponseEntity<CreateShortUrlResponse> createShortURl(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody CreateShortUrlRequest body
    );
}
