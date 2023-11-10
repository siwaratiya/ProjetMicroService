package tn.esprit.hrservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.hrservice.Entity.Leave_Authorization;

import java.util.Date;
import java.util.List;

@Repository
public interface Leave_AuthorizationRepository extends JpaRepository<Leave_Authorization, Long> {

    List<Leave_Authorization> findLeave_AuthorizationsByIdA(Long idA);
}
