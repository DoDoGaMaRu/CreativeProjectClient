package network.protocol;

/**
 * RequestCode
 *
 * code = [L1: 4bit][L2: 4bit]
 * L1 :
 * L2 :
 */

public class RequestCode {
    private static final byte L1 = 4;
    private static final byte L2 = 0;

    // LAYER 1
    public static final byte USER = 1 << L1;
    public static final byte REFRIGERATOR = 2 << L1;
    public static final byte INGREDIENT = 3 << L1;
    public static final byte RECIPE = 4 << L1;

    // LAYER 2
    // USER
    public static final byte REGIST = USER | 1 << L2;
    public static final byte LOGIN = USER | 2 << L2;
    // REFRIGERATOR
    public static final byte PUT_IN = REFRIGERATOR | 1 << L2;
    public static final byte PUT_OUT = REFRIGERATOR | 2 << L2;
    // INGREDIENT
    public static final byte SEARCH = INGREDIENT | 1 << L2;
    // RECIPE
    public static final byte INFO = RECIPE | 1 << L2;
    public static final byte EXPRT_DATE = RECIPE |  2 << L2;
    public static final byte COOKABLE = RECIPE | 3 << L2;
    public static final byte NUTRIENT = RECIPE | 4 << L2;
}
