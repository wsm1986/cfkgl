package com.kgl.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication()
	 * .withUser("a").password("a").roles("ADMIN"); }
	 */

	@Autowired
	private com.kgl.dao.UsuarioDAO usuarioDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDao).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**").antMatchers("/fragments/**").antMatchers("/css/**")
				.antMatchers("/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers("/corretor/**").hasRole("ADMIN").
		antMatchers("/contrato/listar").permitAll().
		antMatchers("/contrato/detalharContr/**").permitAll().
		antMatchers("/contrato/**").hasRole("ADMIN").
		antMatchers("/movimentacao/form").permitAll().
		antMatchers("/movimentacao/**").hasRole("ADMIN").
		antMatchers("/operacao/**").hasRole("ADMIN").
		antMatchers("/produto/**").hasRole("ADMIN").
		antMatchers("/subProduto/**").hasRole("ADMIN").
		antMatchers("/prepararAmbiente").hasRole("ADMIN").
		antMatchers("/static/**").permitAll().
		antMatchers("/url-magica-maluca-/**").permitAll().
		antMatchers("/console/**").permitAll().anyRequest()
			.authenticated().and().formLogin().loginPage("/login").permitAll().and().
			logout().permitAll()
			;
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}