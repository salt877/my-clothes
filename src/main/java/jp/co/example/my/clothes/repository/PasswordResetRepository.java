package jp.co.example.my.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.example.my.clothes.domain.PasswordReset;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {

}
