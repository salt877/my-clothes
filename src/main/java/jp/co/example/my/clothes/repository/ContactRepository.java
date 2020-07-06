package jp.co.example.my.clothes.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.Contact;

/**
 * 問い合わせ情報を扱うリポジトリです.
 * 
 * @author mizuki.tanimori
 *
 */
@Repository
public class ContactRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Contactオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Contact>CONTACT_ROW_MAPPER =(rs,i) ->{
		Contact contact = new Contact();
		contact.setId(rs.getInt("id"));
		contact.setName(rs.getString("name"));
		contact.setEmail(rs.getString("email"));
		contact.setContent(rs.getString("content"));
		return contact;
	};
	
	/**
	 * 問い合わせ情報をDBに保存します。
	 * 
	 * @param contact 問い合わせ内容
	 */
	public void insert(Contact contact) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(contact);
		String insertSql = "INSERT INTO contacts(name,email,content) VALUES(:name, :email, :content)";
		
		template.update(insertSql, param);
	}
	
}
