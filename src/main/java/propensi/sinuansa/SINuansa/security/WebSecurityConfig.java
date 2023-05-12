package propensi.sinuansa.SINuansa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.csrf().disable().requestCache().disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/login", "/info","/dummy","/validate-ticket").permitAll()
                .antMatchers("/user").hasAnyAuthority("MANAJER", "ADMIN")
                .antMatchers("/user/addmanajer").hasAnyAuthority("MANAJER", "ADMIN")
                .antMatchers("/user/addbarista").hasAnyAuthority("MANAJER", "ADMIN")
                .antMatchers("/user/addstafinv").hasAnyAuthority("MANAJER", "ADMIN")
                .antMatchers("/user/addstafpabrik").hasAuthority("ADMIN")
                .antMatchers("/user/update/{id}").hasAuthority("ADMIN")
                .antMatchers("/menu").hasAnyAuthority("ADMIN", "MANAJER", "BARISTA", "StaffInventory")
                .antMatchers("/menu/add").hasAnyAuthority("ADMIN", "MANAJER")
                .antMatchers("/menu/hide").hasAnyAuthority("ADMIN", "MANAJER")
                .antMatchers("/menu/show").hasAnyAuthority("ADMIN", "MANAJER")
                .antMatchers("/menu/update/{id}").hasAnyAuthority("ADMIN", "MANAJER", "BARISTA")
                .antMatchers("/inventory/add").hasAnyAuthority("ADMIN", "MANAJER", "StaffInventory")
                .antMatchers("/inventory/update").hasAnyAuthority("ADMIN", "MANAJER", "StaffInventory")
                .antMatchers("/inventory/delete/{id}").hasAnyAuthority("ADMIN", "MANAJER", "StaffInventory")
                .antMatchers("/inventory/viewall").hasAnyAuthority("ADMIN", "MANAJER", "StaffInventory")
                .antMatchers("/pesananCustomer/add").hasAnyAuthority("ADMIN", "BARISTA")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    public BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    //Sementara buat superuser, need to be discussed later.
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("superuser")
//                .password(encoder.encode("sinuansa"))
//                .roles("ADMIN");
//    }

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

}
