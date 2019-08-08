package techcourse.myblog.presentation.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests extends ControllerTests {
    private static final String NAME = "zino";
    private static final String PASSWORD = "zinozino";
    private static final String EMAIL = "zino@gmail.com";

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        registerUser(NAME, EMAIL, PASSWORD);
    }

    @Test
    void 회원가입_POST() {
        webTestClient.post()
                .uri("/users")
                .body(fromFormData("name", NAME + "a")
                        .with("email", EMAIL + "a")
                        .with("password", PASSWORD + "a"))
                .exchange()
                .expectStatus().isFound();
        countUser();
    }

    @Test
    void 로그인_테스트() {
        webTestClient.post()
                .uri("/login")
                .body(fromFormData("email", EMAIL)
                        .with("password", PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();
    }

    @Test
    void 로그아웃_테스트() {
        webTestClient.get()
                .uri("/logout")
                .header("Cookie", logInAndGetSessionId(EMAIL, PASSWORD))
                .exchange()
                .expectStatus()
                .isFound();
    }

    @Test
    void myPage_조회_테스트() {
        webTestClient.get()
                .uri("/mypage")
                .header("Cookie", logInAndGetSessionId(EMAIL, PASSWORD))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void myPage_수정_페이지_테스트() {
        webTestClient.get()
                .uri("/mypage/edit")
                .header("Cookie", logInAndGetSessionId(EMAIL, PASSWORD))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void myPage_수정_테스트() {
        webTestClient.put()
                .uri("/mypage/edit")
                .body(fromFormData("email", "zino@gmail.com")
                        .with("password", "zino123!@#")
                        .with("name", "zinozino"))
                .header("Cookie", logInAndGetSessionId(EMAIL, PASSWORD))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", "http://localhost:"+portNo+"/mypage/edit");
    }

    @Test
    void myPage_수정_에러_테스트() {
        webTestClient.put()
                .uri("/mypage/edit")
                .body(fromFormData("email", "zino@gmail.com")
                        .with("password", "zi")
                        .with("name", "zinozino"))
                .header("Cookie", logInAndGetSessionId(EMAIL, PASSWORD))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("location", "http://localhost:"+portNo+"/");
    }

    @AfterEach
    void tearDown() {
        String sessionId = logInAndGetSessionId(EMAIL, PASSWORD);
        deleteUser(sessionId);
    }
}