package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.loginHistory.entity.LoginHistory;
import com.zerobase.fastlms.loginHistory.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final LoginHistoryRepository loginHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userId = authentication.getName();

        Optional<Member> memberOptional = memberRepository.findById(userId);

        if(memberOptional.isPresent()){
            Member member =memberOptional.get();
            member.setLastLoginDt(LocalDateTime.now());
            memberRepository.save(member);

            String userAgent = RequestUtils.getUserAgent(request);
            String clientIp =RequestUtils.getClientIp(request);

            LoginHistory loginHistory  = LoginHistory.builder()
                    .userAgent(userAgent)
                    .ipAddress(clientIp)
                    .userId(userId)
                    .regDt(LocalDateTime.now())
                    .build();

            loginHistoryRepository.save(loginHistory);

            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
