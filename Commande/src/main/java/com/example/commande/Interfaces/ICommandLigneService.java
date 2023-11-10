package com.example.commande.Interfaces;

import com.example.commande.Entity.CommandLigne;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface ICommandLigneService {


    CommandLigne AddLigneAndAssign(CommandLigne ligne);

    CommandLigne GetCommandById(Long id);

    Set<CommandLigne> getAllOrdersLine();

    void deleteOrderLine(Long id);

    Double getSumQuantity(Long id);


}
