package personal.bulletinborad.exception;

public abstract class ExceptionMessage {
    public static final String EXIST_LOGIN_ID = "이미 존재하는 아이디 입니다.";
    public static final String EXIST_EMAIL = "해당 이메일로 아이디가 존재합니다.";
    public static final String EXIST_NICKNAME = "이미 존재하는 닉네임 입니다.";
    public static final String NOT_EXIST_NICKNAME = "해당 아이디가 존재하지 않습니다.";
    public static final String NOT_VERIFY = "인증되지 않은 사용자 입니다.";
}
