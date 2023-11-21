package homemate.controller.user;

import homemate.config.jwt.service.JwtService;
import homemate.dto.building.BuildingDto;
import homemate.dto.user.UserDto;
import homemate.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;




    /**
     * 회원가입 시 주소에 jwt 반환
     */
    @GetMapping("/login/oauth2/code/user/sign-up")
    public RedirectView redirectHandler(@RequestParam("accessToken") String token) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://ceprj.gachon.ac.kr:60006/login/oauth2/code/user/sign-up?accessToken=" + token);
        return redirectView;
    }

    /**
     * 로그인 시 반환되는 페이지
     */
    @GetMapping("/signin")
    public RedirectView redirectHandler(@RequestParam("accessToken") String token, @RequestParam("userId") String userId) {
        RedirectView redirectView = new RedirectView();

        String url = "http://ceprj.gachon.ac.kr:60006/signin";

        String paramAccessToken = "accessToken=" + token;
        String paramUserId = "userId=" + userId;

        url += "?" + paramAccessToken + "&" + paramUserId;
        redirectView.setUrl(url);
        return redirectView;
    }


    /**
     * 회원가입 시 최초 닉네임 설정 api
     * @param request
     * @param userRequestDto
     * @return
     */
    @PostMapping("/user/sign-up")
    public ResponseEntity<?> joinUser(HttpServletRequest request, @RequestBody UserDto.UserRequestDto userRequestDto){
        log.info("회원가입 api 실행");
        try{
            // Header에서 Bearer부분을 제외한 jwt 추출
            String authorizationHeader = request.getHeader("Authorization");


            // 추출한 jwt에서 이메일값 추출
            Optional<String> email = jwtService.extractEmail(authorizationHeader.substring(7));

            // 이메일 값이 null이 아니면 회원가입 진행
            if(email.isPresent()){
                String value = email.get();
                Long userId = userService.addJoinUserInfo(value, userRequestDto.getNickName());
                return ResponseEntity.ok().body("userId=" + userId);
            } else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("empty email");
            }

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("sign-up error");
        }
    }


    /**
     * logout -> 시큐리티에서 처리
     */
    @GetMapping("/")
    public RedirectView redirectHandler() {
        log.info("로그아웃 시작");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://ceprj.gachon.ac.kr:60006");
        return redirectView;
    }





    /**
     *
     * update -> 닉네임만 설정 및 수정 가능 -> 온보딩
     */
    @PatchMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestParam("userId") Long userId, @RequestBody UserDto.UserPatchDto userPatchDto) {
        return ResponseEntity.ok().body(userService.updateUser(userId, userPatchDto));
    }

    /**
     * 사용자 탈퇴 기능
     */
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Deleted UserId: " + userId);
    }


    @GetMapping("/getUser")
    public ResponseEntity<?> getUser (@RequestParam("userId") Long userId) {

        UserDto.UserResponseDto user = userService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }





}
