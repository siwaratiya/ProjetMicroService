package tn.esprit.userservice.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.userservice.Entitys.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAccountsByRole(Roles role);
    Optional<Account>   findAccountByUsername( String username   );
    @Query("select ac from  Account ac WHERE ac.username IN :usernames")
    List<Account> findAccountDtosByUsernameIn(String[] usernames);

    Optional<Account> findAccountByCode(String Code);
    Optional<Account> findAccountByCodeAndUsername(String Code,String Username);
    @Query("select case when (count(act) > 0)  then true else false end from Account act  where  ((act.username = :username))")
    boolean isCorrectUserName(@Param("username") String username   );
    @Query("select case when (count(act) > 0)  then true else false end from Account act  where  ((act.code = :code))")
    boolean isCorrectCode(@Param("code") String code   );
    @Query("select act from Account act    where  ((act.email = :email)AND (act.username = :username)) ")
    Optional<Account> findUserByUsernameAndEmail(@Param("username") String username ,@Param("email") String email   );
    @Query("select case when (count(act) > 0)  then true else false end from Account act   where  ((act.email = :email)AND(act.username = :username))")
    boolean isCorrectEmailAndUsername(@Param("username") String username  , @Param("email") String email    );

}
