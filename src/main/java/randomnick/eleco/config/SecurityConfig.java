package randomnick.eleco.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import randomnick.eleco.service.impl.MyUserDetailsService;
import randomnick.eleco.component.JwtAuthenticationTokenFilter;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index","/user/login","/user/regist",
                        "/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**",
                        "/swagger-resources/configuration/ui","/swagge‌​r-ui.html","/user/logout",
                        "/checked/**","/comment/**","/good/**","/user/**","/picture/**","/comment/**","/collect/**","/post/**",
                        "/good/findSellingGood","/image/**")
                //"/checked/**","/comment/**","/good/**","/user/**","/picture/**","/comment/**","/collect/**",   用于swagger测试（没有token）
                .permitAll()
                .antMatchers("/user/admin").hasAuthority("admin")
                .anyRequest().authenticated()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //添加过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        http
//                .authorizeRequests()
//                //访问"/"和"/home"路径的请求都允许
//                .antMatchers("/", "/index","/login","/register",
//                        "/v2/api-docs", "/configuration/ui", "/swagger-resources",
//                        "/configuration/security", "/swagger-ui.html", "/webjars/**",
//                        "/swagger-resources/configuration/ui","/swagge‌​r-ui.html")
//                .permitAll()
//                //而其他的请求都需要认证
//                .anyRequest()
//                .authenticated()
//                .and()
//                //修改Spring Security默认的登陆界面
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
    public UserDetailsService myUserService(){
        return new MyUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
