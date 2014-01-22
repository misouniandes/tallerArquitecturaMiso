
package co.edu.uniandes.csw.member.logic.api;

import java.util.List; 

import co.edu.uniandes.csw.member.logic.dto.MemberDTO;

public interface _IMemberLogicService {

	public MemberDTO createMember(MemberDTO detail);
	public List<MemberDTO> getMembers();
	public MemberDTO getMember(Long id);
	public void deleteMember(Long id);
	public void updateMember(MemberDTO detail);
	
}