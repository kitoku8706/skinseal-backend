package com.example.skin_back.user;

import com.example.skin_back.user.entity.UserEntity;
import com.example.skin_back.user.repository.UserRepository;
import com.example.skin_back.user.constant.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        // given
        UserEntity user = UserEntity.builder()
                .username("testuser")
                .password("testpass")
                .role(UserRole.USER)
                .email("testuser@example.com")
                .phoneNumber("010-1234-5678")
                .build();

        // when
        UserEntity savedUser = userRepository.save(user);
        UserEntity foundUser = userRepository.findById(savedUser.getUserId()).orElse(null);

        // then
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("testuser", foundUser.getUsername());
        Assertions.assertEquals("testuser@example.com", foundUser.getEmail());
    }
}