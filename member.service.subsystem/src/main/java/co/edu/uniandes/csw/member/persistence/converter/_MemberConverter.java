
package co.edu.uniandes.csw.member.persistence.converter;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniandes.csw.member.logic.dto.MemberDTO;
import co.edu.uniandes.csw.member.persistence.entity.MemberEntity;

public abstract class _MemberConverter {


	public static MemberDTO entity2PersistenceDTO(MemberEntity entity){
		if (entity != null) {
			MemberDTO dto = new MemberDTO();
				dto.setId(entity.getId());
				dto.setFirstName(entity.getFirstName());
				dto.setLastName(entity.getLastName());
				dto.setBirthDate(entity.getBirthDate());
				dto.setDocNumber(entity.getDocNumber());
			return dto;
		}else{
			return null;
		}
	}
	
	public static MemberEntity persistenceDTO2Entity(MemberDTO dto){
		if(dto!=null){
			MemberEntity entity=new MemberEntity();
			entity.setId(dto.getId());
			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setBirthDate(dto.getBirthDate());
			entity.setDocNumber(dto.getDocNumber());
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<MemberDTO> entity2PersistenceDTOList(List<MemberEntity> entities){
		List<MemberDTO> dtos=new ArrayList<MemberDTO>();
		for(MemberEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<MemberEntity> persistenceDTO2EntityList(List<MemberDTO> dtos){
		List<MemberEntity> entities=new ArrayList<MemberEntity>();
		for(MemberDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}