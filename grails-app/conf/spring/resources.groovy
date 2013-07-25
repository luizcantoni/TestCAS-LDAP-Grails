import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils;
import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;

import example.PrepopulateUserDetailsService;

// Place your Spring DSL code here
beans = {
	def config = SpringSecurityUtils.securityConfig
	SpringSecurityUtils.loadSecondaryConfig 'DefaultLdapSecurityConfig'
	config = SpringSecurityUtils.securityConfig

	initialDirContextFactory(org.springframework.security.ldap.DefaultSpringSecurityContextSource,
	   config.ldap.context.server){
		userDn = config.ldap.context.managerDn
		password = config.ldap.context.managerPassword
		anonymousReadOnly = config.ldap.context.anonymousReadOnly
	}

	ldapUserSearch(org.springframework.security.ldap.search.FilterBasedLdapUserSearch,
	   config.ldap.search.base,
	   config.ldap.search.filter,
		initialDirContextFactory){
	}

	ldapAuthoritiesPopulator(org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator,
		initialDirContextFactory,
	   config.ldap.authorities.groupSearchBase){
		  groupRoleAttribute = config.ldap.authorities.groupRoleAttribute
		  groupSearchFilter = config.ldap.authorities.groupSearchFilter
		  searchSubtree = config.ldap.authorities.searchSubtree
		  convertToUpperCase = config.ldap.mapper.convertToUpperCase
		  ignorePartialResultException = config.ldap.authorities.ignorePartialResultException
	}

	ldapUserDetailsMapper(InetOrgPersonContextMapper)
	   
	ldapUserDetailsService(org.springframework.security.ldap.userdetails.LdapUserDetailsService,
		ldapUserSearch,
		ldapAuthoritiesPopulator){
		userDetailsMapper = ref('ldapUserDetailsMapper')
	}
	
	userDetailsService(PrepopulateUserDetailsService) {
		ldapUserDetailsService=ref('ldapUserDetailsService')
		grailsApplication = ref('grailsApplication')
	}
	
}
