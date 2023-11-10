package tn.esprit.hrservice.Interface;



import org.springframework.stereotype.Service;
import tn.esprit.hrservice.Dto.Leave_AuthorizationDto;
import tn.esprit.hrservice.Entity.Leave_Authorization;

import java.util.Date;
import java.util.List;
@Service
public interface ILeave_AuthService {

    public Leave_AuthorizationDto addAndAssignLAToAccount(Leave_Authorization la, Long idA);
    public Leave_AuthorizationDto updateLeaveAuth(Leave_AuthorizationDto la, Long idA);
    public List<Leave_AuthorizationDto> retrieveLAByAccountId(Long idA);
    public void deleteLeaveAuth(Long idLA);
    public List<Leave_AuthorizationDto> retrieveAllLeaveAuths();
    public Leave_AuthorizationDto retrieveLeaveAuthById(Long idLA);

}
