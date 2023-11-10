package com.example.commande.Mappers;


import com.example.commande.Dto.CommandDto;
import com.example.commande.Entity.Command;
import com.example.commande.Interfaces.AccountClient;

public class CommandMapper {
    static AccountClient accountClient;
    public static CommandDto mapToDo(Command command){
        CommandDto commandDto=CommandDto.builder()
                .id(command.getId())
                //.date(command.getDate())
                .account_id(command.getIdA())
                .accountDto(accountClient.SelectById(command.getIdA()).getBody())
                .notice(command.getNotice())
                .build();
        return commandDto;
    }
    public static Command mapToEntity(CommandDto commandDto){
        Command command=Command.builder()
                .id(commandDto.getId())
                //.date(commandDto.getDate())
                .notice(commandDto.getNotice())
                .idA(commandDto.getAccount_id())
                .build();

        return command;
    }
}
