package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.ChangePasswordTokenDTO;
import com.SpringIsComing.injagang.DTO.FindDTO;
import com.SpringIsComing.injagang.DTO.LoginDTO;
import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.DTO.*;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Friend;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Service.*;
import com.SpringIsComing.injagang.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final EssayService essayService;
    private final InterviewService interviewService;
    private final FriendService friendService;
    private final AuthTokenService authTokenService;
    private final AlarmService alarmService;


    /**
     * 마이페이지
     */
    @GetMapping("/mypage/{nickname}")
    public String myPage(@SessionAttribute("loginSession") String nickname, @PathVariable("nickname") String curNickname
                            ,Model model) {


        Member loginMember = memberService.findByNickname(nickname);
        Member targetMember = memberService.findByNickname(curNickname);

        log.info("curname = {}", curNickname);
        log.info("target = {}",loginMember);

        List<Friend> loginFriends = friendService.findFriends(loginMember);
        List<Friend> targetFriends = friendService.findFriends(targetMember);


        int friendState = 0;

        if (!nickname.equals(curNickname)) {
            boolean toRequest = friendService.existRequest(loginMember.getId(), targetMember.getId());
            boolean fromRequest = friendService.existRequest(targetMember.getId(), loginMember.getId());

            if (!toRequest && !fromRequest) {
                friendState = 2;
            }

            if (toRequest) {
                friendState = 3;
            }

            if (fromRequest) {
                friendState = 4;
            }

        }

        for (Friend friend : loginFriends) {
            log.info("김건부={}",friend.getNickname());
            if (friend.getNickname().equals(targetMember.getNickname())) {
                friendState = 1;
                break;
            }
        }


        model.addAttribute("essayList",essayService.findEssays(targetMember).stream()
                                    .map(e -> essayService.toMypageEssayDTO(e)).collect(toList()));
        model.addAttribute("interviewList",interviewService.findInterviews(targetMember).stream()
                .map(i -> interviewService.toMypageInterViewDTO(i)).collect(toList()));

        model.addAttribute("friendList", targetFriends.stream()
                .map(f -> friendService.toMypageDTO(f)).collect(toList()));

        log.info("깐부={}",friendState);
        model.addAttribute("friendState", friendState);
        model.addAttribute("nickname", curNickname);
        model.addAttribute("loginNickname", nickname);

        return "mypage/mypage";
    }

    @GetMapping("/mypage/update")
    public String updateStart(@SessionAttribute(value = "loginSession", required = false) String nickname,Model model){

        if (nickname == null) {
            return "redirect:/login";
        }

        Member loginMember = memberService.findByNickname(nickname);
        UpdateDTO updateDTO = memberService.toUpdateDTO(loginMember);

        model.addAttribute("member", updateDTO);

        return "mypage/updateInformation";

    }

    @PostMapping("/mypage/update")
    public String updateStart(@Valid @ModelAttribute("member") UpdateDTO updateDTO,BindingResult bindingResult,
                              @SessionAttribute(value = "loginSession", required = false) String nickname,
                              RedirectAttributes redirectAttributes, HttpServletRequest request) {

        boolean sameNickname = false;

        if (nickname.equals(updateDTO.getNickname())) {
            sameNickname = true;
        }

        HttpSession session = request.getSession(false);

//        if (session == null) {
//            return "redirect:/login";
//        }

        if (bindingResult.hasErrors()) {
            log.info("개새끼");

            return "mypage/updateInformation";
        }

        log.info("updateDTO = {}",updateDTO);
        Member loginMember = memberService.findByNickname(nickname);
        if (!memberService.passwordCheck(loginMember, updateDTO.getCurPassword())) {
            bindingResult.rejectValue("curPassword","curPasswordCheck","현재 비밀번호가 틀립니다.");
            log.info("개새끼2");

            return "mypage/updateInformation";

        }

        if(!updateDTO.getNewPassword().equals(updateDTO.getPasswordCheck())){
            bindingResult.rejectValue("newPassword","passwordCheck","비밀번호가 다릅니다.");
            log.info("개새끼3");

            return "mypage/updateInformation";
        }


        if (memberService.nicknameDuplicateCheck(updateDTO.getNickname()) && !sameNickname) {
            bindingResult.rejectValue("nickname","duplicateNickname","닉네임이 중복됩니다.");

            log.info("개새끼4");

            return "mypage/updateInformation";

        }

        if (!sameNickname) {
            memberService.changeNickname(nickname, updateDTO.getNickname());
            session.removeAttribute(SessionConst.LOGIN_SESSION);
            session.setAttribute(SessionConst.LOGIN_SESSION, updateDTO.getNickname());

        }
        memberService.changePassword(updateDTO.getNickname(),updateDTO.getNewPassword());

        redirectAttributes.addAttribute("nickname", updateDTO.getNickname());

        return "redirect:/mypage/{nickname}";

    }



    /**
     * 마이페이지에서 수정 버튼 눌렀을때
     * */
    @GetMapping("/mypage/update/{memberId}")
    public void memberUpdateStart() {

    }

    /**
     * 회원 정보 수정 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/mypage/update/{memberId}")
    public void memberUpdateEnd() {
        //입력 확인 후 객체 변경
    }

    /**
     * login 화면
     **/
    @GetMapping("/login")
    public String loginStart(Member member) {

        return "mypage/login";

    }

    /**
     * login 화면에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/login")
    public String loginEnd(@Valid @ModelAttribute("member")LoginDTO loginDTO, BindingResult bindingResult
                            , HttpServletRequest request, RedirectAttributes redirectAttributes
                           ) {
        //입력 확인 후 마이페이지로 이동

        if (bindingResult.hasErrors()) {
            return "mypage/login";
        }

        Member loginMember = memberService.login(loginDTO.getLoginId(), loginDTO.getPassword());

        if(loginMember == null){
            bindingResult.reject("loginFail","아이디나 비밀번호를 다시 확인해주세요");
            return "mypage/login";
        }

        if (!loginMember.isAuth()) {
            bindingResult.reject("notAuth","인증되지 않은 계정입니다.");
            return "mypage/login";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_SESSION, loginMember.getNickname());
        redirectAttributes.addAttribute("nickname", loginMember.getNickname());


        return "redirect:/mypage/{nickname}";

    }

    /**
     * 회원가입 화면
     * */
    @GetMapping("/register")
    public String registerStart(@ModelAttribute("member")RegisterDTO registerDTO) {

        return "mypage/reg";
    }

    /**
     * 회원가입에서 확인 버튼 눌렀을때
     * */
    @PostMapping("/register")
    public String registerEnd(@Valid @ModelAttribute("member")RegisterDTO registerDTO, BindingResult bindingResult
                                ,HttpServletRequest request
    ) {
        //입력 확인 후 객체 추가

        if (bindingResult.hasErrors()) {
            return "mypage/reg";
        }

        if(!registerDTO.getPassword().equals(registerDTO.getPasswordCheck())){
            bindingResult.rejectValue("password","passwordCheck","비밀번호가 다릅니다.");
            return "mypage/reg";
        }

        if (!memberService.loginDuplicateCheck(registerDTO.getLoginId())) {
            bindingResult.rejectValue("loginId","duplicateLoginId","아이디가 중복됩니다.");
            return "mypage/reg";
        }

        if (memberService.nicknameDuplicateCheck(registerDTO.getNickname())) {
            bindingResult.rejectValue("nickname","duplicateNickname","닉네임이 중복됩니다.");
            return "mypage/reg";
        }

        if (memberService.emailDuplicateCheck(registerDTO.getEmail())) {
            bindingResult.rejectValue("email","duplicateEmail","이메일이 중복됩니다.");
            return "mypage/reg";
        }

        Long savedId = memberService.save(registerDTO);

        authTokenService.createEmailAuthToken(registerDTO.getLoginId(), registerDTO.getEmail());

//        HttpSession session = request.getSession();
//
//        session.setAttribute(SessionConst.LOGIN_SESSION, savedId);

        return "redirect:/auth/complete";

    }


    @GetMapping("/find")
    public String findStart(@ModelAttribute("findEmail") FindDTO findDTO){

        return "mypage/findIdAndPassword";
    }

    @PostMapping("/find")
    public String findEnd(@Valid @ModelAttribute("findEmail") FindDTO findDTO, BindingResult bindingResult) {

        Member find = memberService.findByEmail(findDTO.getEmail());


        if (find == null) {
            bindingResult.reject("notFindEmail","등록된 이메일이 아닙니다.");
            return "mypage/findIdAndPassword";

        }

        authTokenService.findEmailAuthToken(find.getLoginId(), find.getEmail());

        return "redirect:/auth/complete";

    }

    @GetMapping("/change/password")
    public String changePasswordStart(@Valid @RequestParam String token,
                                      @ModelAttribute("passwords") ChangePasswordTokenDTO changePasswordTokenDTO){

        return "mypage/changePasswordToken";

    }

    @PostMapping("/change/password")
    public String changePasswordEnd(@Valid @ModelAttribute("passwords") ChangePasswordTokenDTO changePasswordTokenDTO
                                    , BindingResult bindingResult, @RequestParam String token) {


        if (bindingResult.hasErrors()) {
            return "mypage/changePasswordToken";
        }

        if (!changePasswordTokenDTO.getPassword().equals(changePasswordTokenDTO.getPasswordCheck())) {
            bindingResult.rejectValue("password","passwordCheck","비밀번호가 다릅니다.");
            return "mypage/changePasswordToken";
        }

        Member findMember = memberService.confirmEmailForPassword(token);
        memberService.changePassword(findMember.getLoginId(), changePasswordTokenDTO.getPassword());

        return "redirect:/login";

    }

    @GetMapping("/auth/complete")
    public String authComplete(){

        return "mypage/mail";
    }


    /*
    @PostConstruct
    public void init(){
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setEmail("abcdefg123@naver.com");//자신의 이메일을 넣어서 테스트해보세욤
        registerDTO.setName("김수만");
        registerDTO.setLoginId("dkandkdlel");
        registerDTO.setNickname("smkim");
        registerDTO.setPassword("test");
        registerDTO.setPasswordCheck("test");

        memberService.save(registerDTO);
    }
    */
}