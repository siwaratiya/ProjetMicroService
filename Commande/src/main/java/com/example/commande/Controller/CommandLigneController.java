package com.example.commande.Controller;

import com.example.commande.Entity.CommandLigne;
import com.example.commande.Interfaces.ICommandLigneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("commandLigne")
@AllArgsConstructor
public class CommandLigneController {
    ICommandLigneService ligneService;

    @PostMapping("addLigneAndProduct/{id}")
    public CommandLigne AddLigneAndAssign(@RequestBody CommandLigne ligne ){
        return ligneService.AddLigneAndAssign(ligne);
    }

    @Operation(description = "Delete adress")
    @DeleteMapping("delete/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        ligneService.deleteOrderLine(id);
    }

    @Operation(description = "Retreive Command Ligne by id")
    @GetMapping("getCommLigne/{id}")
    public CommandLigne getAdressById(@PathVariable("id") Long id){
        return ligneService.GetCommandById(id);
    }

    @Operation(description = "Retreive all Command Lignes")
    @GetMapping("getAllCommLignes")
    public Set<CommandLigne> getAllAdress(){
        return ligneService.getAllOrdersLine();
    }

    @GetMapping("getQuantity/{id}")
    public Double getSumQuantity(@PathVariable("id") Long id){
        return ligneService.getSumQuantity(id);
    }


}

