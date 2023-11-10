package tn.esprit.hrservice.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.hrservice.Dto.Leave_AuthorizationDto;
import tn.esprit.hrservice.Entity.Leave_Authorization;
import tn.esprit.hrservice.Entity.State_LA;
import tn.esprit.hrservice.Entity.Type_LA;
import tn.esprit.hrservice.Exception.WrongPeriodException;
import tn.esprit.hrservice.Interface.AccountClient;
import tn.esprit.hrservice.Interface.ILeave_AuthService;
import tn.esprit.hrservice.Mapper.Leave_AuthorizationMapper;
import tn.esprit.hrservice.Repository.Leave_AuthorizationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class Leave_AuthService implements ILeave_AuthService {

    Leave_AuthorizationRepository leave_authorizationRepository;

    AccountClient accountClient;
    Leave_AuthorizationMapper leaveAuthorizationMapper;

    @Override
    public Leave_AuthorizationDto addAndAssignLAToAccount(Leave_Authorization la, Long idA) {
        Long idAccount = accountClient.SelectById(idA).getBody().getId();
        if(la.getType_la().equals(Type_LA.Leave)){
            if(la.getEnd_date().before(la.getStart_date())){
                throw new WrongPeriodException("End Date must be >= Start Date");
            }
        }
        Leave_Authorization leaveAuthorization = leave_authorizationRepository.save(la);
        leaveAuthorization.setIdA(idAccount);
        leaveAuthorization.setState_la(State_LA.Pending);
        leave_authorizationRepository.save(leaveAuthorization);

        return leaveAuthorizationMapper.mapLeaveAuthToDto(leaveAuthorization);
    }

    @Override
    public Leave_AuthorizationDto updateLeaveAuth(Leave_AuthorizationDto la, Long idA) {
        Leave_Authorization leaveAuth = leaveAuthorizationMapper.mapLeaveAuthToEntity(la);
        Long idAccount = accountClient.SelectById(idA).getBody().getId();
        if(leaveAuth.getEnd_date().before(leaveAuth.getStart_date())){
            throw new WrongPeriodException("End Date must be >= Start Date");
        }else {
            leaveAuth.setIdA(idAccount);
            Leave_Authorization leaveAuthorization = leave_authorizationRepository.save(leaveAuth);
            return leaveAuthorizationMapper.mapLeaveAuthToDto(leaveAuthorization);
        }
    }

    @Override
    public List<Leave_AuthorizationDto> retrieveLAByAccountId(Long idA) {
        List<Leave_Authorization> leaveAuthorizations = leave_authorizationRepository.findLeave_AuthorizationsByIdA(idA);
        return leaveAuthorizations.stream().map(leaveAuthorizationMapper::mapLeaveAuthToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteLeaveAuth(Long idLA) {
        leave_authorizationRepository.deleteById(idLA);
    }

    @Override
    public List<Leave_AuthorizationDto> retrieveAllLeaveAuths() {
        List<Leave_Authorization> leaveAuthorizations =  leave_authorizationRepository.findAll();
        return leaveAuthorizations.stream().map(leaveAuthorizationMapper::mapLeaveAuthToDto).collect(Collectors.toList());
    }

    @Override
    public Leave_AuthorizationDto retrieveLeaveAuthById(Long idLA) {
        Leave_Authorization leaveAuthorization = leave_authorizationRepository.findById(idLA).orElse(null);
        return leaveAuthorizationMapper.mapLeaveAuthToDto(leaveAuthorization);
    }

}
