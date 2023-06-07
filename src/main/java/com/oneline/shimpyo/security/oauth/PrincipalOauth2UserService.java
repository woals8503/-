package com.oneline.shimpyo.security.oauth;

import com.oneline.shimpyo.domain.member.Member;
import com.oneline.shimpyo.domain.member.MemberGrade;
import com.oneline.shimpyo.domain.member.MemberRole;
import com.oneline.shimpyo.security.auth.PrincipalDetails;
import com.oneline.shimpyo.repository.MemberRepository;
import com.oneline.shimpyo.security.provider.GoogleUserInfo;
import com.oneline.shimpyo.security.provider.NaverUserInfo;
import com.oneline.shimpyo.security.provider.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomBCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    // 구글로 부터 받은 userRequest 데이터에 대한 후처리 함수
    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider();    // google
        String providerId = oAuth2UserInfo.getProviderId(); // google의 sub(기본키)
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId;  // google_기본키
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        Member member = memberRepository.findByEmail(email);

        if(member == null) {
            System.out.println("OAuth 로그인이 최초입니다. 회원가입을 진행하여주세요.");

            MemberGrade memberGrade = new MemberGrade("브론즈", 2);
            em.persist(memberGrade);

            member = member.builder()
                    .email(email)
                    .password(password)
                    .phoneNumber("전화번호를 등록해주세요.")
                    .point(0)
                    .nickname("닉네임을 등록해주세요.")
                    .provider(provider)
                    .providerId(providerId)
                    .memberGrade(memberGrade)
                    .role(MemberRole.valueOf(role))
                    .build();
            memberRepository.save(member);

            /** 여기서 추가 회원가입 페이지로 이동해야함. **/
        } else {
            System.out.println("이미 로그인 되있습니다.");
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
