package team.safe.escape.common.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    @Test
    @DisplayName("이메일 형식이 잘못되었는지를 확인합니다.")
    void isInvalidEmail_shouldReturnTrueForWrongFormat() {
        // given
        String wrongEmail = "test";

        // when
        boolean result = EmailValidator.isInvalidEmail(wrongEmail);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("이메일 형식이 올바른지를 확인합니다.")
    void isValidEmail_shouldReturnTrueForRightEmail() {
        // given
        String rightEmail = "psy020207@naver.com";

        // when
        boolean result = EmailValidator.isValidEmail(rightEmail);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("null 이메일은 잘못된 이메일로 처리됩니다.")
    void nullEmail_shouldBeInvalid() {
        // given
        String nullEmail = null;

        // when
        boolean result = EmailValidator.isInvalidEmail(nullEmail);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("빈 문자열 이메일은 잘못된 이메일로 처리됩니다.")
    void emptyEmail_shouldBeInvalid() {
        // given
        String emptyEmail = "";

        // when
        boolean result = EmailValidator.isInvalidEmail(emptyEmail);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("공백이 포함된 이메일은 잘못된 이메일로 처리됩니다.")
    void emailWithSpaces_shouldBeInvalid() {
        // given
        String emailWithSpaces = " test@example.com ";

        // when
        boolean result = EmailValidator.isInvalidEmail(emailWithSpaces);

        // then
        assertTrue(result);
    }
}