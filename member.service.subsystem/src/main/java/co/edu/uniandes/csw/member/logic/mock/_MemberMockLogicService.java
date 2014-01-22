
package co.edu.uniandes.csw.member.logic.mock;
import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.member.logic.dto.MemberDTO;
import co.edu.uniandes.csw.member.logic.api._IMemberLogicService;

public abstract class _MemberMockLogicService implements _IMemberLogicService {

	private Long id= new Long(1);
	protected List<MemberDTO> data=new ArrayList<MemberDTO>();

	public MemberDTO createMember(MemberDTO member){
		id++;
		member.setId(id);
		return member;
    }

	public List<MemberDTO> getMembers(){
		return data; 
	}

	public MemberDTO getMember(Long id){
		for(MemberDTO d:data){
			if(d.getId().equals(id)){
				return d;
			}
		}
		return null;
	}

	public void deleteMember(Long id){
	    MemberDTO delete=null;
		for(MemberDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
		} 
	}

	public void updateMember(MemberDTO member){
	    MemberDTO delete=null;
		for(MemberDTO d:data){
			if(d.getId().equals(id)){
				delete=d;
			}
		}
		if(delete!=null){
			data.remove(delete);
			data.add(member);
		} 
	}	
}