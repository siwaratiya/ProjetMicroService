package com.example.commande.Service;

import com.example.commande.Entity.CommandLigne;
import com.example.commande.Exception.ElementNotFoundException;
import com.example.commande.Interfaces.ICommandLigneService;
import com.example.commande.Repository.ICommandLigneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
@AllArgsConstructor
public class CommandLigneService implements ICommandLigneService {

    ICommandLigneRepository ligneRepository;




    @Override
    public CommandLigne AddLigneAndAssign(CommandLigne ligne) {
      return  ligneRepository.save(ligne);

    }

    @Override
    public CommandLigne GetCommandById(Long id) {
        return ligneRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Command with id "+ id +" not found : " ));
    }

    @Override
    public Set<CommandLigne> getAllOrdersLine() {
        Set<CommandLigne> ligneList = new HashSet<>();
        ligneRepository.findAll().forEach(ligneList::add);
        return ligneList;
    }

    @Override
    public void deleteOrderLine(Long id) {
        ligneRepository.deleteById(id);
    }

    @Override
    public Double getSumQuantity(Long id) {
        return null;
    }


}