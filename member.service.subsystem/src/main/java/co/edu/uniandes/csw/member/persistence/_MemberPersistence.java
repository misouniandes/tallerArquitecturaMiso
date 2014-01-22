
package co.edu.uniandes.csw.member.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import co.edu.uniandes.csw.member.logic.dto.MemberDTO;
import co.edu.uniandes.csw.member.persistence.api._IMemberPersistence;
import co.edu.uniandes.csw.member.persistence.converter.MemberConverter;
import co.edu.uniandes.csw.member.persistence.entity.MemberEntity;

public abstract class _MemberPersistence implements _IMemberPersistence {

	@PersistenceContext(unitName="MemberPU")
	protected EntityManager entityManager;
	
	public MemberDTO createMember(MemberDTO member) {
		MemberEntity entity=MemberConverter.persistenceDTO2Entity(member);
		entityManager.persist(entity);
		return MemberConverter.entity2PersistenceDTO(entity);
	}

	@SuppressWarnings("unchecked")
	public List<MemberDTO> getMembers() {
		Query q = entityManager.createQuery("select u from MemberEntity u");
		return MemberConverter.entity2PersistenceDTOList(q.getResultList());
	}

	public MemberDTO getMember(Long id) {
		return MemberConverter.entity2PersistenceDTO(entityManager.find(MemberEntity.class, id));
	}

	public void deleteMember(Long id) {
		MemberEntity entity=entityManager.find(MemberEntity.class, id);
		entityManager.remove(entity);
	}

	public void updateMember(MemberDTO detail) {
		MemberEntity entity=entityManager.merge(MemberConverter.persistenceDTO2Entity(detail));
		MemberConverter.entity2PersistenceDTO(entity);
	}

}