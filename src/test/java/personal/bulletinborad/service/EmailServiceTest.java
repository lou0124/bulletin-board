package personal.bulletinborad.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {

    @Autowired EmailService emailService;

    @Test
    void 메일_보내기() throws Exception {
        String ePw = emailService.sendSimpleMessage("ohchangmin00@gmail.com");
        System.out.println("ePw = " + ePw);
    }
}