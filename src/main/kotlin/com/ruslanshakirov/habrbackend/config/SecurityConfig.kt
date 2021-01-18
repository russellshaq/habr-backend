package com.ruslanshakirov.habrbackend.config

import com.ruslanshakirov.habrbackend.config.handlers.MyLoginFailureHandler
import com.ruslanshakirov.habrbackend.config.handlers.MyLoginSuccessHandler
import com.ruslanshakirov.habrbackend.config.handlers.MyLogoutSuccessHandler
import com.ruslanshakirov.habrbackend.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
@EnableCaching
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var logoutSuccessHandler: MyLogoutSuccessHandler

    @Autowired
    private lateinit var loginSuccessHandler: MyLoginSuccessHandler

    @Autowired
    private lateinit var loginFailureHandler: MyLoginFailureHandler

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/img/**")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(
                "/auth/signup", "/h2-console/**",
                "/swagger-ui.html/**", "/webjars/springfox-swagger-ui/**",
                "/swagger-resources/**", "/v2/api-docs/**",
                "/posts", "/posts/{id}", "/users/{id}", "/topics"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginProcessingUrl("/auth/signin")
            .usernameParameter("email").permitAll()
            .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
            .and().csrf().disable()
            .logout().logoutUrl("/auth/signout").logoutSuccessHandler(logoutSuccessHandler)
            .and().exceptionHandling().authenticationEntryPoint(Http403ForbiddenEntryPoint())

    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }
}
