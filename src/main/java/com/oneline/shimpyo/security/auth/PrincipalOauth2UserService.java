package com.oneline.shimpyo.security.auth;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.CustomBCryptPasswordEncoder;
import com.oneline.shimpyo.security.provider.GoogleUserInfo;
import com.oneline.shimpyo.security.provider.NaverUserInfo;
import com.oneline.shimpyo.security.provider.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.oneline.shimpyo.domain.member.MemberRole.*;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomBCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("입장");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            // 네이버는 response키를 가진 Map 형태 안에 attribute를 가지고 있기 때문에 아래와 같이 작성함
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider();    // google
        String providerId = oAuth2UserInfo.getProviderId(); // google의 sub(기본키)
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId;  // google_기본키
        String password = bCryptPasswordEncoder.encode("oAuth 비밀번호");

        Member user = memberRepository.findByEmail(username);

        if(user == null) {
            log.info("OAuth 로그인이 최초입니다.");
            user = user.builder()
                    .email(username)
                    .password(password)
                    .email(email)
                    .role(CLIENT)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberRepository.save(user);
        } else {
            log.info("이미 로그인 되있습니다.");
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
