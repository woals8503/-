package com.oneline.shimpyo.security.oAuth;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.CustomBCryptPasswordEncoder;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.security.oAuth.provider.GoogleUserInfo;
import com.oneline.shimpyo.security.oAuth.provider.NaverUserInfo;
import com.oneline.shimpyo.security.oAuth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

import static com.oneline.shimpyo.domain.member.GradeName.*;
import static com.oneline.shimpyo.domain.member.MemberRole.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final CustomBCryptPasswordEncoder bCryptPasswordEncoder;

    private final EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    // 코드를 보내서 엑세스토큰으로 소셜쪽에 요청해서 유저 정보를 받아온 상태
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String social = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if(social.equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(social.equals("naver")) {
            log.info("네이버 로그인 요청");
            // 네이버는 response키를 가진 Map 형태 안에 attribute를 가지고 있기 때문에 아래와 같이 작성함
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }

        String username = oAuth2UserInfo.getEmail();

        Member member = memberRepository.findByEmail(username);

        if(member == null) {
            log.info("OAuth 최초 로그인");
            String provider = oAuth2UserInfo.getProvider();
            String providerId = oAuth2UserInfo.getProviderId();

            MemberGrade memberGrade = new MemberGrade(SILVER, 3);
            em.persist(memberGrade);
            em.flush();

            // join
            member = member.builder()
                    .email(username)
                    .password("쉼표")
                    .point(0)
                    .nickname(oAuth2UserInfo.getName())
                    .provider(provider)
                    .providerId(providerId)
                    .memberGrade(memberGrade)
                    .role(CLIENT).build();

            memberRepository.save(member);
        }
        else {
            log.info("이미 로그인 되있습니다.");
        }

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
