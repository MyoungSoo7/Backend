package homemate;
import homemate.constant.*;
import homemate.domain.admin.AdminEntity;
import homemate.domain.building.BuildingEntity;
import homemate.domain.user.ArticleEntity;
import homemate.domain.user.CommentEntity;
import homemate.domain.user.UserEntity;
import homemate.repository.admin.AdminRepository;
import homemate.repository.building.BuildingRepository;
import homemate.repository.user.ArticleRepository;
import homemate.repository.user.CommentRepository;
import homemate.repository.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init {

    //init.

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final BuildingRepository buildingRepository;

    @PostConstruct
    private void initFirst(){
        initAdmins();
        initUsers();
        initBuildings();
        initArticles();
        initComments();
    }

    @Transactional
    public void initAdmins() {

        AdminEntity admin = new AdminEntity();
        admin.setAdminName("admin");
        admin.setPassword("homematePassword");
        admin.setStatus(Status.ACTIVE);
        adminRepository.save(admin);


    }

    @Transactional
    public void initUsers() {
        for (int i = 0; i < 5; i++) {
            UserEntity user = new UserEntity();
            user.setUserName("user" + i);
            user.setNickName("userNickname" + i);
            user.setPassword("userPs" + i);
            user.setEmail("user" + i + "@example.com");
            user.setStatus(Status.ACTIVE);
            user.setSocialType(SocialType.KAKAO);
            user.setSocialId("userSocialId" + i);
            user.setRefreshToken("userRefreshToken" + i);
            userRepository.save(user);
        }
    }



    @Transactional
    public void initBuildings() {
        for (int i = 0; i < 5; i++) {
            BuildingEntity building = new BuildingEntity();
            building.setBuildingName("가천건물" + i);
            building.setDistrict(District.Gyeonggi);
            building.setAddress("성남시 수정구 - " + i);
            building.setContent("건물" + i + "입니다.");
            building.setFloor(i + "층");
            building.setCost("월세 5" + i );
            building.setMoveInDate("2024-01-0" + i);
            building.setCheckDuplex("복층 없음");
            building.setDirection("남동");
            building.setNumberOfRoom("방" + i + "개");
            building.setNumberOfParking("주차 가능 " + i + "대");
            building.setRealterName("가천중개사" + i);
            building.setRealterNumber("010-0000-000" + i);
            building.setBuildingField(BuildingField.APARTMENT);
            building.setStatus(Status.ACTIVE);
            building.setTransactioonType(TransactionType.MONTHLY_RENT);
            buildingRepository.save(building);
        }
    }

    @Transactional
    public void initArticles() {
        List<UserEntity> user = userRepository.findAll();
        for (int i = 0; i < 5; i++) {
            ArticleEntity article = new ArticleEntity();
            article.setUser(user.get(i));
            article.setTitle("게시글" + i);
            article.setContent("내용" + i);
            article.setStatus(Status.ACTIVE);
            article.setComplain(0);
            articleRepository.save(article);
        }
    }


    @Transactional
    public void initComments(){
        List<UserEntity> user = userRepository.findAll();
        List<ArticleEntity> article = articleRepository.findAll();
        for (int i = 0; i < 5; i++) {
            CommentEntity comment = new CommentEntity();
            comment.setUser(user.get(i));
            comment.setArticle(article.get(i));
            comment.setContent("댓글" + i);
            comment.setStatus(Status.ACTIVE);
            comment.setComplain(i);
            commentRepository.save(comment);
        }

    }

}