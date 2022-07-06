package admin;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author quavario@gmail.com
 * @date 2020/3/5 16:21
 */
@SpringBootTest
class TestMail {
    public static void main(String[] args) {
        String hashpw = BCrypt.hashpw("Support_FmGF6jY9", "$2a$10$jNHBa2paqlvPJG8UyLjwbe54XRrJnKzBnpx4lccYDTgMy9OFB9uY2");
        System.out.println(hashpw);
    }
}
