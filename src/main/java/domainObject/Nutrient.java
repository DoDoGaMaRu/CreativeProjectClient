package domainObject;

import lombok.Builder;
import lombok.Getter;

/**
 * updater.Nutrient
 * 영양소 정보
 *
 * infoWgt		    : 중량(1인분)
 * infoEng          : 열량
 * infoCar		    : 탄수화물
 * infoPro		    : 단백질
 * infoFat		    : 지방
 * infoNa			: 나트륨
 */

@Getter
@Builder
public class Nutrient {
    private double infoWgt;
    private double infoEng;
    private double infoCar;
    private double infoPro;
    private double infoFat;
    private double infoNa;
}
