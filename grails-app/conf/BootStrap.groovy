class BootStrap {

    def init = { servletContext ->
		new example.Role(authority: "ROLE_INVESTIGATOR").save()
		new example.Role(authority: "ROLE_USER").save()
    }
    def destroy = {
    }
}
