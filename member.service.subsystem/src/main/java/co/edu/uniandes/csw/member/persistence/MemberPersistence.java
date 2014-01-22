
package co.edu.uniandes.csw.member.persistence;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.member.persistence.api.IMemberPersistence;
import javax.ejb.LocalBean;

@Default
@Stateless 
@LocalBean
public class MemberPersistence extends _MemberPersistence  implements IMemberPersistence {

}