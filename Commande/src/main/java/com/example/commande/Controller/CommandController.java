package com.example.commande.Controller;


import com.example.commande.Entity.Command;
import com.example.commande.Interfaces.ICommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Command Management")
@AllArgsConstructor
@RestController
@RequestMapping("/command")
public class CommandController {
    ICommandService commandService;



    @Operation(description = "Add new Command")
    @PostMapping("add/{idA}")
    public Command addCommand(@RequestBody Command command,@PathVariable("idA") long idA){
        return commandService.addCommand(command ,idA);
    }
    @Operation(description = "Add new Command")
    @PostMapping("add2")
    public Command addCommand(@RequestBody Command command){
        return commandService.addCommand2(command);}

    @Operation(description = "Modify Command")
    @PutMapping("modify/{idA}/{id}")
    public Command modifyCommand(@RequestBody Command command ,@PathVariable("idA") long idA, @PathVariable("id") long id) {
        return commandService.modifyCommand(command,idA,id);
    }




    @Operation(description = "Retreive Command by Id")
    @GetMapping("getcomandebyidA/{idA}")
    public List<Command> retrieveLAByAccountId(@PathVariable("idA")Long idA){
        return commandService.retrieveLAByAccountId(idA);
    }
    @Operation(description = "Delete Command")
    @DeleteMapping("delete/{id}")
    public void deleteCommand(@PathVariable("id") Long id){
        commandService.deleteCommand(id);
    }

    @Operation(description = "Retreive Command by Id")
    @GetMapping("getcommande/{id}")
    public Command getCommandById(@PathVariable("id") Long id){
        return commandService.getCommandById(id);
    }

    @Operation(description = "Retreive all Commands")
    @GetMapping("getAllCommands")
    public Set<Command> getAllCommand(){
        return commandService.getAllCommands();
    }
    @PostMapping("AddCommandAndAssignToCommandLigne/{id}")
    public Command addCommandAndAffectProducts(@RequestBody Command command, @PathVariable("id") List<Long> idCommandLignes){
        return commandService.affectCommandToCommandLine(command,idCommandLignes);
    }







}
