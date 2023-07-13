package personal.bulletinborad.mock;

import personal.bulletinborad.infrastructure.VerificationCodeRepository;

import java.util.HashMap;
import java.util.Map;

public class FakeVerificationCodeRepository implements VerificationCodeRepository {

    private Map<String, String> storage = new HashMap<>();

    @Override
    public void save(String email, String code, Long duration) {
        storage.put(email, code);
    }

    @Override
    public String findByEmail(String email) {
        return storage.get(email);
    }
}
