package anpopo.powerboard.domain.enumeration;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    ID("아이디"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그")
    ;

    private final String value;
}
