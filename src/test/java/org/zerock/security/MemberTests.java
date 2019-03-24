package org.zerock.security;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {

    @Setter(onMethod_ = @Autowired)
    private PasswordEncoder passwordEncoder;

    @Setter(onMethod_ = @Autowired)
    private DataSource dataSource;

    @Test
    public void testInsertMember() {
        String sql = "insert into tbl_member(userid, userpw, username) values (?, ?, ?)";

        for (int i = 0; i < 100; i++) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(2, passwordEncoder.encode("pw" + i));

                if (i < 80) {
                    preparedStatement.setString(1, "user" + i);
                    preparedStatement.setString(3, "일반사용자" + i);
                } else if (i < 90) {
                    preparedStatement.setString(1, "manager" + i);
                    preparedStatement.setString(3, "운영자" + i);
                } else {
                    preparedStatement.setString(1, "admin" + i);
                    preparedStatement.setString(3, "관리자" + i);
                }

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // end for
    }

    @Test
    public void testInsertAuth() {
        String sql = "insert into tbl_member_auth(userid, auth) values (?, ?)";

        for (int i = 0; i < 100; i++) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                if (i < 80) {
                    preparedStatement.setString(1, "user" + i);
                    preparedStatement.setString(2, "ROLE_USER");
                } else if (i < 90) {
                    preparedStatement.setString(1, "manager" + i);
                    preparedStatement.setString(2, "ROLE_MEMBER");
                } else {
                    preparedStatement.setString(1, "admin" + i);
                    preparedStatement.setString(2, "ROLE_ADMIN");
                }

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // end for
    }

}
