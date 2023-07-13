package personal.bulletinborad;

//@SpringBootTest
//class MailSendAndRedisStoreTest {
//
//    @Autowired
//    private VerificationMailSenderImpl mailSender;
//    @Autowired
//    private VerificationCodeRepositoryImpl repository;
//
//    @Test
//    void 메일보내고_저장하고_찾기() throws MessagingException, UnsupportedEncodingException {
//        String to = "ohchangmin00@gmail.com";
//        String code1 = mailSender.send(to);
//        repository.save(to, code1, 60L);
//        String code2 = repository.findByEmail(to);
//        System.out.println("code2 = " + code2);
//        Assertions.assertThat(code1).isEqualTo(code2);
//    }
//}
