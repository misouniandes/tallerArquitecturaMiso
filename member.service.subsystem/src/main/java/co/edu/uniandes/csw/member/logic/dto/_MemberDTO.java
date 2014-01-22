
package co.edu.uniandes.csw.member.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _MemberDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String docNumber;

	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
 
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	public String getLastName() {
		return lastName;
	}
 
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	public Date getBirthDate() {
		return birthDate;
	}
 
	public void setBirthDate(Date birthdate) {
		this.birthDate = birthdate;
	}
	public String getDocNumber() {
		return docNumber;
	}
 
	public void setDocNumber(String docnumber) {
		this.docNumber = docnumber;
	}
	
}