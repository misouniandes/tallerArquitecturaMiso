package co.edu.uniandes.csw.member.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.member.logic.api.IMemberLogicService;
import co.edu.uniandes.csw.member.logic.dto.MemberDTO;


public abstract class _MemberService {

	@Inject
	protected IMemberLogicService memberLogicService;
	
	@POST
	public MemberDTO createMember(MemberDTO member){
		return memberLogicService.createMember(member);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteMember(@PathParam("id") Long id){
		memberLogicService.deleteMember(id);
	}
	
	@GET
	public List<MemberDTO> getMembers(){
		return memberLogicService.getMembers();
	}
	
	@GET
	@Path("{id}")
	public MemberDTO getMember(@PathParam("id") Long id){
		return memberLogicService.getMember(id);
	}
	
	@PUT
    @Path("{id}")
	public void updateMember(@PathParam("id") Long id, MemberDTO member){
		memberLogicService.updateMember(member);
	}
	
}