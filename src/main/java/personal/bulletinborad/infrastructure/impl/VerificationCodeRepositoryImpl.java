package personal.bulletinborad.infrastructure;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String email, String code, Long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(email, code, expireDuration);
    }

    @Override
    public String findByEmail(String email) {
        return redisTemplate.opsForValue().get(email);
    }
}
