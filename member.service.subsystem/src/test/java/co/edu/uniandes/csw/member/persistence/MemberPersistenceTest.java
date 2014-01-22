
package co.edu.uniandes.csw.member.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.member.logic.dto.MemberDTO;
import co.edu.uniandes.csw.member.persistence.api.IMemberPersistence;
import co.edu.uniandes.csw.member.persistence.entity.MemberEntity;

@RunWith(Arquillian.class)
public class MemberPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(MemberPersistence.class.getPackage())
				.addPackage(MemberEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IMemberPersistence memberPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from MemberEntity").executeUpdate();
	}

	private List<MemberEntity> data=new ArrayList<MemberEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			MemberEntity entity=new MemberEntity();
			entity.setFirstName(generateRandom(String.class));
			entity.setLastName(generateRandom(String.class));
			entity.setBirthDate(generateRandom(Date.class));
			entity.setDocNumber(generateRandom(String.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createMemberTest(){
		MemberDTO dto=new MemberDTO();
		dto.setFirstName(generateRandom(String.class));
		dto.setLastName(generateRandom(String.class));
		dto.setBirthDate(generateRandom(Date.class));
		dto.setDocNumber(generateRandom(String.class));
		
		
		MemberDTO result=memberPersistence.createMember(dto);
		
		Assert.assertNotNull(result);
		
		MemberEntity entity=em.find(MemberEntity.class, result.getId());
		
		Assert.assertEquals(dto.getFirstName(), entity.getFirstName());	
		Assert.assertEquals(dto.getLastName(), entity.getLastName());	
		Assert.assertEquals(dto.getBirthDate(), entity.getBirthDate());	
		Assert.assertEquals(dto.getDocNumber(), entity.getDocNumber());	
	}
	
	@Test
	public void getMembersTest(){
		List<MemberDTO> list=memberPersistence.getMembers();
		Assert.assertEquals(list.size(), data.size());
        for(MemberDTO dto:list){
        	boolean found=false;
            for(MemberEntity entity:data){
            	if(dto.getId()==entity.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getMemberTest(){
		MemberEntity entity=data.get(0);
		MemberDTO dto=memberPersistence.getMember(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getFirstName(), dto.getFirstName());
		Assert.assertEquals(entity.getLastName(), dto.getLastName());
		Assert.assertEquals(entity.getBirthDate(), dto.getBirthDate());
		Assert.assertEquals(entity.getDocNumber(), dto.getDocNumber());
        
	}
	
	@Test
	public void deleteMemberTest(){
		MemberEntity entity=data.get(0);
		memberPersistence.deleteMember(entity.getId());
        MemberEntity deleted=em.find(MemberEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateMemberTest(){
		MemberEntity entity=data.get(0);
		
		MemberDTO dto=new MemberDTO();
		dto.setId(entity.getId());
		dto.setFirstName(generateRandom(String.class));
		dto.setLastName(generateRandom(String.class));
		dto.setBirthDate(generateRandom(Date.class));
		dto.setDocNumber(generateRandom(String.class));
		
		
		memberPersistence.updateMember(dto);
		
		
		MemberEntity resp=em.find(MemberEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getFirstName(), resp.getFirstName());	
		Assert.assertEquals(dto.getLastName(), resp.getLastName());	
		Assert.assertEquals(dto.getBirthDate(), resp.getBirthDate());	
		Assert.assertEquals(dto.getDocNumber(), resp.getDocNumber());	
	}
	
	public <T> T generateRandom(Class<T> objectClass){
		Random r=new Random();
		if(objectClass.isInstance(String.class)){
			String s="";
			for(int i=0;i<10;i++){
				char c=(char)(r.nextInt()/('Z'-'A')+'A');
				s=s+c;
			}
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Integer.class)){
			Integer s=r.nextInt();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(Long.class)){
			Long s=r.nextLong();
			return objectClass.cast(s);
		}else if(objectClass.isInstance(java.util.Date.class)){
			java.util.Calendar c=java.util.Calendar.getInstance();
			c.set(java.util.Calendar.MONTH, r.nextInt()/12);
			c.set(java.util.Calendar.DAY_OF_MONTH,r.nextInt()/30);
			c.setLenient(false);
			return objectClass.cast(c.getTime());
		} 
		return null;
	}
	
}