// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
		   
	debug 'org.springframework.security'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'example.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'example.UserRole'
grails.plugins.springsecurity.authority.className = 'example.Role'

// Define the authentication providers
grails.plugins.springsecurity.providerNames = ['casAuthenticationProvider']
grails.plugins.springsecurity.cas.loginUri = '/login'
grails.plugins.springsecurity.cas.serviceUrl = 'http://localhost:8080/SpringLDAPGrails/j_spring_cas_security_check'
grails.plugins.springsecurity.cas.serverUrlPrefix = 'https://mycompany.com:8443/cas'
grails.plugins.springsecurity.cas.proxyCallbackUrl = 'http://localhost:8080/SpringLDAPGrails/secure/receptor'
grails.plugins.springsecurity.cas.proxyReceptorUrl = '/secure/receptor'
grails.plugins.springsecurity.logout.afterLogoutUrl = 'https://mycompany.com:8443/cas/logout?service=http://localhost:8080/SpringLDAPGrails/'

grails.plugins.springsecurity.ldap.context.server = 'ldap://192.168.10.245:389/'
grails.plugins.springsecurity.ldap.context.managerDn = 'user@mycompany.com'
grails.plugins.springsecurity.ldap.context.managerPassword = 'password'
grails.plugins.springsecurity.ldap.search.attributesToReturn = ['mail', 'displayName'] // extra attributes you want returned; see below for custom classes that access this data
grails.plugins.springsecurity.ldap.mapper.userDetailsClass= 'inetOrgPerson'// 'org.springframework.security.ldap.userdetails.InetOrgPerson'
grails.plugins.springsecurity.ldap.context.anonymousReadOnly=false
grails.plugins.springsecurity.ldap.authorities.retrieveDatabaseRoles = true
grails.plugins.springsecurity.ldap.authorities.ignorePartialResultException = true // typically needed for Active Directory
grails.plugins.springsecurity.ldap.search.base = 'ou=MYCOMPANY,dc=MYCOMPANY,dc=COM,dc=BR'
grails.plugins.springsecurity.ldap.search.filter="sAMAccountName={0}" // for Active Directory you need this
grails.plugins.springsecurity.ldap.search.searchSubtree = true
grails.plugins.springsecurity.ldap.auth.hideUserNotFoundExceptions = false
//grails.plugins.springsecurity.providerNames = ['ldapAuthProvider', 'anonymousAuthenticationProvider'] // specify this when you want to skip attempting to load from db and only use LDAP


//grails.plugins.springsecurity.rejectIfNoRule = true

grails.plugins.springsecurity.controllerAnnotations.staticRules = [
	'/**': ['ROLE_USER']
 ]

