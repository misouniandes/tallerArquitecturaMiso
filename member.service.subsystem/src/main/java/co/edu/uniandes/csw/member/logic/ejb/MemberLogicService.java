
package co.edu.uniandes.csw.member.logic.ejb;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.inject.Inject;

import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.member.logic.api.IMemberLogicService;

@Default
@Stateless
@LocalBean
public class MemberLogicService extends _MemberLogicService implements IMemberLogicService {

}