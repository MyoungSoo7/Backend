package homemate.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import homemate.constant.Status;
import homemate.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity extends TimeStamp {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private UserEntity user;

    private String title; //제목

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content; //내용

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer complain; // 게시글 신고 횟수 (최대 10회)

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CommentEntity> comments = new ArrayList<>();




}

