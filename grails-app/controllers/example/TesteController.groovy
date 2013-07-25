package example

import grails.converters.JSON
import grails.plugins.springsecurity.Secured;

class TesteController {
	def springSecurityService

	@Secured(['ROLE_USER'])
    def index() { 
		//render User.list()

        def user = User.get(springSecurityService.principal.id)
		render user as JSON //user.username + " - " + user.displayName + " - " + user.email
	}
}
