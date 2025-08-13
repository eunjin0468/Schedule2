package org.example.schedule.Filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter implements Filter {

    private static final List<String> EXCLUDE_URLS = List.of("/users/signup", "/users/signin");

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();

        // 회원가입, 로그인 요청은 인증 체크 제외
        if (EXCLUDE_URLS.contains(path)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);

        // 세션 없거나 userId 없으면 401 Unauthorized 반환
        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        // 정상적인 요청은 다음 필터/컨트롤러로 진행
        chain.doFilter(req, res);
    }
}
