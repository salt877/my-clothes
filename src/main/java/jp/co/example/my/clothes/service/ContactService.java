package jp.co.example.my.clothes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.my.clothes.domain.Contact;
import jp.co.example.my.clothes.repository.ContactRepository;

/**
 * 問い合わせ情報を受け取るサービスクラス.
 * 
 * @author mizuki.tanimori
 *
 */
@Service
@Transactional
public class ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	/**
	 * 問い合わせ内容をDBに保存します.
	 * 
	 * @param contact 問い合わせ内容
	 */
	public void insertContact(Contact contact) {
		contactRepository.insert(contact);
	}
}
