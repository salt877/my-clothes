package jp.co.example.my.clothes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ログイン認証用設定.
 * 
 * @author katsuya.fujishima
 *
 */
@Configuration // 設定用のクラス
@EnableWebSecurity // Spring Securityのウェブ用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService memberDetailsService;

	/**
	 * このメソッドをオーバーライドすることで、 特定のリクエストに対して「セキュリティ設定」を 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/dist/**", "/img/**", "/lib/**");
	}

	/**
	 * このメソッドをオーバーライドすることで、認可の設定やログイン/ログアウトに関する設定ができる.
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests() // 認可に関する設定
				.antMatchers("/**").permitAll() // 認証が必要
				.anyRequest().authenticated(); // 全てのユーザに許可

	}

	/**
	 * 「認証」に関する設定. 認証ユーザを取得する「UserDetailsService」の設定や
	 * パスワード照合時に使う「PasswordEncoder」の設定
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	/**
	 * 
	 * bcryptアルゴリズムでハッシュ化する実装を返します. これを指定することでパスワードハッシュ化やマッチ確認する際に
	 * 
	 * @Autowired private PasswordEncoder passwordEncoder; と記載するとDIされるようになります。
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
