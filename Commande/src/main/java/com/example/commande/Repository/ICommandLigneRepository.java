package com.example.commande.Repository;

import com.example.commande.Entity.CommandLigne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommandLigneRepository extends JpaRepository<CommandLigne,Long> {

}

