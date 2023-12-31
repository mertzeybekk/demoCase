# Spring-Boot-Book-Shop
Spring Boot Token based Authentication with Spring Security &amp; JWT


								-Spring Security-
-----------------------------------------------------------------------

– WebSecurityConfigurerAdapter is the crux of our security implementation. 
It provides HttpSecurity configurations to configure cors, csrf, session management, rules for protected resources. 
We can also extend and customize the default configuration that contains the elements below.

– UserDetailsService interface has a method to load User by username and returns a UserDetails object 
that Spring Security can use for authentication and validation.

– UserDetails contains necessary information (such as: username, password, authorities) to build an Authentication object.

– UsernamePasswordAuthenticationToken gets {username, password} from login Request, 
AuthenticationManager will use it to authenticate a login account.

– AuthenticationManager has a DaoAuthenticationProvider (with help of UserDetailsService & PasswordEncoder) to validate 
UsernamePasswordAuthenticationToken object. If successful, 
AuthenticationManager returns a fully populated Authentication object (including granted authorities).

– OncePerRequestFilter makes a single execution for each request to our API. 
It provides a doFilterInternal() method that we will implement parsing & validating JWT, 
loading User details (using UserDetailsService), checking Authorizaion (using UsernamePasswordAuthenticationToken).

– AuthenticationEntryPoint handles AuthenticationException, will catch authentication error.

– JwtAuthTokenFilter (extends OncePerRequestFilter) pre-processes HTTP request, from Token, 
create Authentication and populate it to SecurityContext.

– JwtProvider validates, parses token String or generates token String from UserDetails.

– SecurityContext is established by calling SecurityContextHolder.getContext().setAuthentication(…​) with returned 
authentication object above.

- Repository contains UserRepository & RoleRepository to work with Database, will be imported into Controller.

- Controller receives and handles request after it was filtered by OncePerRequestFilter.

– AuthController handles signup/login requests

– TestController has accessing protected resource methods with role based validations.
=======================================================================================================================

security: we configure Spring Security & implement pre-defined Security Objects here.
				
				WebSecurityConfig extends WebSecurityConfigurerAdapter
				UserDetailsServiceImpl implements UserDetailsService
				UserDetailsImpl implements UserDetails
				AuthEntryPointJwt implements AuthenticationEntryPoint
				AuthTokenFilter extends OncePerRequestFilter
				JwtUtils provides methods for generating, parsing, validating JWT
----------------------------------------------------------------------------------------------------------------------

controllers handle signup/login requests & authorized requests.

AuthController: @PostMapping(‘/signin’), @PostMapping(‘/signup’)
TestController: @GetMapping(‘/api/test/all’), @GetMapping(‘/api/test/[role]’)
----------------------------------------------------------------------------------------------------------------------
repository has intefaces that extend Spring Data JPA JpaRepository to interact with Database.

UserRepository extends JpaRepository<User, Long>
RoleRepository extends JpaRepository<Role, Long>
----------------------------------------------------------------------------------------------------------------------
models defines two main models for Authentication (User) & Authorization (Role). They have many-to-many relationship.

User: id, username, email, password, roles
Role: id, name
-----------------------------------------------------------------------------------------------------------------------
payload defines classes for Request and Response objects

We also have application.properties for configuring 
			Spring Datasource, Spring Data JPA and App properties (such as JWT Secret string or Token expiration time).
----------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------


1. Create the models (UserRole, Role, User)

2. Implement Repositories (UserRepository, RoleRepository)

3. Configure Spring Security
		-	@EnableGlobalMethodSecurity provides AOP security on methods. 
			It enables @PreAuthorize, @PostAuthorize, it also supports JSR-250.
		
4. Create UserDetailsImpl & UserDetailsServiceImpl class and implements UserDetails &UserDetailsService (In-Built)
		
		* If the authentication process is successful, we can get User’s information such as 
			username, password, authorities from an Authentication object.
	
		* UserDetailsService interface that has only one method:
				public interface UserDetailsService {
 				   UserDetails loadUserByUsername(..)	}

5. Filter the Requests, create AuthTokenFilter class that extends OncePerRequestFilter and 
	override doFilterInternal() method.
	
		* What we do inside doFilterInternal():
			– get JWT from the Authorization header (by removing Bearer prefix)
			– if the request has JWT, validate it, parse username from it
			– from username, get UserDetails to create an Authentication object
			– set the current UserDetails in SecurityContext using setAuthentication(authentication) method.

		* After this, everytime you want to get UserDetails, just use SecurityContext like this:
			UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					// userDetails.getUsername()
					// userDetails.getPassword()
					// userDetails.getAuthorities()


6. Create JWT Utility class
	
		*This class has 3 funtions:
			- generate a JWT from username, date, expiration, secret
			- get username from JWT
			- validate a JWT
			- add jwtSecret and jwtExpirationMs properties in application.properties file.

7. Handle Authentication Exception
	* create AuthEntryPointJwt class that implements AuthenticationEntryPoint interface. 
		- Then we override the commence() method. 
		- This method will be triggerd anytime unauthenticated User requests a secured HTTP resource 
		  and an AuthenticationException is thrown.


8. Define payloads for Spring RestController
		
		– Requests:
			LoginRequest: { username, password }
			SignupRequest: { username, email, password }
			
		– Responses:
			JwtResponse: { token, type, id, username, email, roles }
			MessageResponse: { message }


9. Create Spring RestAPIs Controllers

		– /signup
			check existing username/email
			create new User (with ROLE_USER if not specifying role)
			save User to database using UserRepository

		– /signin	
			authenticate { username, pasword }
			update SecurityContext using Authentication object
			generate JWT
			get UserDetails from Authentication object
			response contains JWT and UserDetails data



