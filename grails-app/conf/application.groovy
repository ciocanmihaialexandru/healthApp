// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'healthapp.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'healthapp.UserRole'
grails.plugin.springsecurity.authority.className = 'healthapp.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               				access: ['permitAll']],
	[pattern: '/error',          				access: ['permitAll']],
	[pattern: '/index',          				access: ['permitAll']],
	[pattern: '/index.gsp',      				access: ['permitAll']],
	[pattern: '/shutdown',       				access: ['permitAll']],
	[pattern: '/assets/**',      				access: ['permitAll']],
	[pattern: '/**/js/**',       				access: ['permitAll']],
	[pattern: '/**/css/**',      				access: ['permitAll']],
	[pattern: '/**/images/**',   				access: ['permitAll']],
	[pattern: '/fonts/**',   	 				access: ['permitAll']],
	[pattern: '/templates/**',   				access: ['permitAll']],
	[pattern: '/**/favicon.ico', 				access: ['permitAll']],
	[pattern: '/topic/**', 			            access: ['permitAll']],
	[pattern: '/stomp/**', 						access: ['permitAll']]
]

grails.plugin.springsecurity.rest.login.active  = true
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/login'
grails.plugin.springsecurity.rest.login.failureStatusCode = '401'

grails.plugin.springsecurity.rest.login.useJsonCredentials  = true
grails.plugin.springsecurity.rest.login.usernamePropertyName =  'username'
grails.plugin.springsecurity.rest.login.passwordPropertyName =  'password'

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'

grails.plugin.springsecurity.rest.token.generation.useSecureRandom  = true
grails.plugin.springsecurity.rest.token.generation.useUUID  = false

grails.plugin.springsecurity.rest.token.storage.useGorm = false
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName   = null
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = 'tokenValue'
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName   = 'username'

grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName  = 'username'
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName   = 'roles'

grails.plugin.springsecurity.rest.token.validation.active   = true
grails.plugin.springsecurity.rest.token.validation.headerName   = 'X-Auth-Token'
grails.plugin.springsecurity.rest.token.validation.endpointUrl  = '/api/validate'

grails {
	plugin {
		springsecurity {
			filterChain {
				chainMap = [
						[pattern: '/assets/**',      filters: 'none'],
						[pattern: '/templates/**',   filters: 'none'],
						[pattern: '/**/js/**',       filters: 'none'],
						[pattern: '/**/css/**',      filters: 'none'],
						[pattern: '/**/images/**',   filters: 'none'],
						[pattern: '/**/favicon.ico', filters: 'none'],
						[pattern: '/mobileApi/**',   filters: 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'],
						[pattern: '/api/**',         filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
						[pattern: '/**',             filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
				]
			}

			rest {
				token {
					validation {
						enableAnonymousAccess = true
					}
				}
			}
		}
	}
}
