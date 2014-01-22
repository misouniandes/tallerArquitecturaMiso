
package co.edu.uniandes.csw.member.logic.ejb;

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
import co.edu.uniandes.csw.member.logic.api.IMemberLogicService;
import co.edu.uniandes.csw.member.persistence.MemberPersistence;
import co.edu.uniandes.csw.member.persistence.api.IMemberPersistence;
import co.edu.uniandes.csw.member.persistence.entity.MemberEntity;

@RunWith(Arquillian.class)
public class MemberLogicServiceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(MemberLogicService.class.getPackage())
				.addPackage(MemberPersistence.class.getPackage())
				.addPackage(MemberEntity.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IMemberLogicService memberLogicService;
	
	@Inject
	private IMemberPersistence memberPersistence;	

	@Before
	public void configTest() {
		try {
			clearData();
			insertData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		List<MemberDTO> dtos=memberPersistence.getMembers();
		for(MemberDTO dto:dtos){
			memberPersistence.deleteMember(dto.getId());
		}
	}

	private List<MemberDTO> data=new ArrayList<MemberDTO>();

	private void insertData() {
		for(int i=0;i<3;i++){
			MemberDTO pdto=new MemberDTO();
			pdto.setFirstName(generateRandom(String.class));
			pdto.setLastName(generateRandom(String.class));
			pdto.setBirthDate(generateRandom(Date.class));
			pdto.setDocNumber(generateRandom(String.class));
			pdto=memberPersistence.createMember(pdto);
			data.add(pdto);
		}
	}
	
	@Test
	public void createMemberTest(){
		MemberDTO ldto=new MemberDTO();
		ldto.setFirstName(generateRandom(String.class));
		ldto.setLastName(generateRandom(String.class));
		ldto.setBirthDate(generateRandom(Date.class));
		ldto.setDocNumber(generateRandom(String.class));
		
		
		MemberDTO result=memberLogicService.createMember(ldto);
		
		Assert.assertNotNull(result);
		
		MemberDTO pdto=memberPersistence.getMember(result.getId());
		
		Assert.assertEquals(ldto.getFirstName(), pdto.getFirstName());	
		Assert.assertEquals(ldto.getLastName(), pdto.getLastName());	
		Assert.assertEquals(ldto.getBirthDate(), pdto.getBirthDate());	
		Assert.assertEquals(ldto.getDocNumber(), pdto.getDocNumber());	
	}
	
	@Test
	public void getMembersTest(){
		List<MemberDTO> list=memberLogicService.getMembers();
		Assert.assertEquals(list.size(), data.size());
        for(MemberDTO ldto:list){
        	boolean found=false;
            for(MemberDTO pdto:data){
            	if(ldto.getId()==pdto.getId()){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getMemberTest(){
		MemberDTO pdto=data.get(0);
		MemberDTO ldto=memberLogicService.getMember(pdto.getId());
        Assert.assertNotNull(ldto);
		Assert.assertEquals(pdto.getFirstName(), ldto.getFirstName());
		Assert.assertEquals(pdto.getLastName(), ldto.getLastName());
		Assert.assertEquals(pdto.getBirthDate(), ldto.getBirthDate());
		Assert.assertEquals(pdto.getDocNumber(), ldto.getDocNumber());
        
	}
	
	@Test
	public void deleteMemberTest(){
		MemberDTO pdto=data.get(0);
		memberLogicService.deleteMember(pdto.getId());
        MemberDTO deleted=memberPersistence.getMember(pdto.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateMemberTest(){
		MemberDTO pdto=data.get(0);
		
		MemberDTO ldto=new MemberDTO();
		ldto.setId(pdto.getId());
		ldto.setFirstName(generateRandom(String.class));
		ldto.setLastName(generateRandom(String.class));
		ldto.setBirthDate(generateRandom(Date.class));
		ldto.setDocNumber(generateRandom(String.class));
		
		
		memberLogicService.updateMember(ldto);
		
		
		MemberDTO resp=memberPersistence.getMember(pdto.getId());
		
		Assert.assertEquals(ldto.getFirstName(), resp.getFirstName());	
		Assert.assertEquals(ldto.getLastName(), resp.getLastName());	
		Assert.assertEquals(ldto.getBirthDate(), resp.getBirthDate());	
		Assert.assertEquals(ldto.getDocNumber(), resp.getDocNumber());	
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