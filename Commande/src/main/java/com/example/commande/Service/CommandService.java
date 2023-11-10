package com.example.commande.Service;

import com.example.commande.Entity.Command;
import com.example.commande.Entity.CommandLigne;
import com.example.commande.Exception.ElementNotFoundException;
import com.example.commande.Interfaces.AccountClient;
import com.example.commande.Interfaces.ICommandService;
import com.example.commande.Repository.ICommandLigneRepository;
import com.example.commande.Repository.ICommandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CommandService implements ICommandService {

    ICommandRepository commandRepository;
    ICommandLigneRepository ligneRepository;
    AccountClient accountClient;

    @Override
    public Command addCommand(Command command , Long idA) {
        Long idAccount = accountClient.SelectById(idA).getBody().getId();
        command.setIdA(idAccount);
        return commandRepository.save(command);
    }
    @Override
    public Command addCommand2(Command command ) {
        return  commandRepository.save(command);
    }
    @Override
    public Command modifyCommand(Command commandmodifier, Long idA, long id) {
        Long idAccount = accountClient.SelectById(idA).getBody().getId();

        Command command1 = commandRepository.findById(id).orElse(null);
        command1.setNbPoduct(commandmodifier.getNbPoduct());
        command1.setNotice(commandmodifier.getNotice());
        command1.setDate(commandmodifier.getDate());
        command1.setQuantity_product(commandmodifier.getQuantity_product());
        command1.setTotal_price(commandmodifier.getTotal_price());
        command1.setIdA(idAccount);
        return commandRepository.save(command1);
    }



    @Override
    public List<Command> retrieveLAByAccountId(Long idA) {
        return commandRepository.findCommandByIdA(idA);
    }



    @Override
    public void deleteCommand(Long id) {
        commandRepository.deleteById(id);
    }

    @Override
    public Command getCommandById(Long id) {
        return commandRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Command with id "+ id +" not found : " ));
    }

    @Override
    public Set<Command> getAllCommands() {
        Set<Command> commandList =new HashSet<>();
        commandRepository.findAll().forEach(commandList::add);
        return commandList;
    }

    @Override
    @Transactional
    public Command affectCommandToCommandLine(Command command, List<Long> idCommandLines) {
        commandRepository.save(command);
        command.setDate(LocalDate.now());

        command.setCommandLignes(null);
        for (Long idCommandLine:idCommandLines)
        {
            CommandLigne commandLigne = ligneRepository.findById(idCommandLine).orElseThrow(() -> new ElementNotFoundException("Command Ligne with id "+ idCommandLine +" not found : " ));
            commandLigne.setCommand(command);
            ligneRepository.save(commandLigne);
        }

        if(command.getTotal_price()==null && command.getQuantity_product()==null){
            Long commandId = command.getId();

            commandRepository.save(command);
        }
        return  command;
    }



    @Override
    public Set<CommandLigne> getCommandLigneOfCommand(Long idCom) {
        Command command = commandRepository.findById(idCom).orElseThrow(() -> new ElementNotFoundException("Command with id "+ idCom +" not found : " ));
        return command.getCommandLignes();
    }


}

