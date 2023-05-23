package domainObject;

import lombok.Getter;
import lombok.Setter;

/**
 * updater.Manual
 * 조리 메뉴얼
 *
 * img      : 조리 이미지
 * text     : 조리 설명
 */

@Getter
@Setter
public class Manual {
    private String img;
    private String text;
}