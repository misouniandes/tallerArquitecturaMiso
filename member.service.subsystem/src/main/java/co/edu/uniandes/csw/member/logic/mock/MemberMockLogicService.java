
package co.edu.uniandes.csw.member.logic.mock;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import co.edu.uniandes.csw.member.logic.api.IMemberLogicService;

@Alternative
@Singleton
public class MemberMockLogicService extends _MemberMockLogicService implements IMemberLogicService {
	
}