package personal.bulletinborad.infrastructure;

public interface VerificationCodeRepository {

    void save(String email, String code, Long duration);
    String findByEmail(String email);
}
