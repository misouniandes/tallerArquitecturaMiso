
package co.edu.uniandes.csw.member.logic.ejb;
import java.util.List;
import javax.inject.Inject;

import co.edu.uniandes.csw.member.logic.dto.MemberDTO;
import co.edu.uniandes.csw.member.logic.api._IMemberLogicService;
import co.edu.uniandes.csw.member.persistence.api.IMemberPersistence;

public abstract class _MemberLogicService implements _IMemberLogicService {

	@Inject
	protected IMemberPersistence persistance;

	public MemberDTO createMember(MemberDTO member){
		return persistance.createMember( member); 
    }

	public List<MemberDTO> getMembers(){
		return persistance.getMembers(); 
	}

	public MemberDTO getMember(Long id){
		return persistance.getMember(id); 
	}

	public void deleteMember(Long id){
	    persistance.deleteMember(id); 
	}

	public void updateMember(MemberDTO member){
	    persistance.updateMember(member); 
	}	
}