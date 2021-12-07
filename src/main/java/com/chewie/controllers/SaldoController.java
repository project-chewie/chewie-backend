package com.chewie.controllers;

import com.chewie.domain.Saldo;
import com.chewie.domain.User;
import com.chewie.services.SaldoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaldoController {

    @Autowired
    SaldoService saldoService;


    @Operation(summary = "This is to find all available saldo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of a saldo",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Saldo not found"/*, no booking possible"*/,
                    content = @Content)
    })
    @GetMapping("/saldos")
    public List<Saldo> getSaldos() {return saldoService.findAllSaldos();}


    @Operation(summary = "This is to find a specific saldo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Details of all saldo",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Saldo not found"/*, no booking possible"*/,
                    content = @Content)
    })
    @GetMapping("/saldo/{id}")
    public Saldo findSaldo(@PathVariable(name = "id")Long user_id) {
        return saldoService.findSaldoById(user_id);
    }
}
