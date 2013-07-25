package example
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.plugins.springsecurity.GormUserDetailsService
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.InetOrgPerson
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;

import example.Role
import example.UserRole
import example.User

/**
* Prepopulates the database with the user details from LDAP directory and assigns a default Role to the user
* @author Philip Wu
*
*/
class PrepopulateUserDetailsService extends GormUserDetailsService {

   Logger logger = Logger.getLogger(getClass())
   
   LdapUserDetailsService ldapUserDetailsService
	   
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
	   return loadUserByUsername(username, true)
   }

   @Override
   public UserDetails loadUserByUsername(String username, boolean loadRoles)
		   throws UsernameNotFoundException, DataAccessException {
			   
			   
	   UserDetails userDetails = ldapUserDetailsService.loadUserByUsername(username)
	   
	   if (userDetails instanceof InetOrgPerson) {
		   
		   InetOrgPerson inetOrgPerson = (InetOrgPerson) userDetails
		   logger.info("mail="+inetOrgPerson.getMail())
		  
		   User user = User.findByUsername(username)
		   if (user == null) {
			   
			   User.withTransaction {
			   
				   // Create new user and save to the database
				   user = new User()
				   user.username = username
				   user.email = inetOrgPerson.getMail()
				   user.displayName = inetOrgPerson.getDisplayName()
				   user.enabled = true
				   user.password = "123" //doesn't matter
				   user.save(flush: true, failOnError: true)
				   
				   Role clientRole
				   
				   if (user.username == "usertest") {
					   clientRole = Role.findByAuthority("ROLE_INVESTIGATOR")

					   UserRole userRole = new UserRole()
					   userRole.user = user
					   userRole.role = clientRole
					   userRole.save()
					   
					   clientRole = Role.findByAuthority("ROLE_USER")
					   userRole = new UserRole()
					   userRole.user = user
					   userRole.role = clientRole
					   userRole.save()

					   logger.info("user saved to database")
					   				   
				   }
				   else {
					    clientRole = Role.findByAuthority("ROLE_USER")
						// Assign the default role of client
						UserRole userRole = new UserRole()
						userRole.user = user
						userRole.role = clientRole
						userRole.save()
						logger.info("user saved to database")
				   }
							   
			   }
		   }
							   
	   }
	   
	   logger.info("ldap user details: "+userDetails)
	   
	   // Load user details from database
	   return super.loadUserByUsername(username, loadRoles)
   }

}
