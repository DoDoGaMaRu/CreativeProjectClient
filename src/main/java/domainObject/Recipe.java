package domainObject;

import lombok.Builder;
import lombok.Getter;

/**
 * Food
 * 조리 음식 정보
 *
 * rcpSeq			: 일련번호
 * rcpNm			: 메뉴명
 * rcpWay2		    : 조리방법
 * rcpPat2		    : 요리종류
 * hashTag		    : 해쉬태그
 * attFileNoMain	: 이미지경로(소)
 * attFileNoMk	    : 이미지경로(대)
 * rcpPartsDtls     : 재료정보
 * rcpNaTip		    : 저감 조리법 TIP
 * infoNutri		: 영양소
 * manuals		    : 만드는법
 */

@Getter
@Builder
public class Recipe {
    private int rcpSeq;
    private String rcpNm;
    private String rcpWay2;
    private String rcpPat2;
    private String hashTag;
    private String attFileNoMain;
    private String attFileNoMk;
    private String rcpPartsDtls;
    private String rcpNaTip;
    private Nutrient infoNutri;
    private Manual[] manuals;
    private Ingredient[] ingredients;
}
