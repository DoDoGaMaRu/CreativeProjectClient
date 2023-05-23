package domainObject;

import lombok.Builder;
import lombok.Getter;

/**
 * updater.Ingredient
 * 재료 정보 컨테이너
 *
 * name     : 재료 이름
 * unit     : 재료 단위명
 * amount   : 양
 */

@Getter
@Builder
public class Ingredient {
    private String name;
    private String unit;
    private double amount;
}
