package network.protocol;

/**
 * ResponseState
 *
 * state = [state type: 4bit][state detail: 4bit]
 *
 */

public class ResponseState {
    private static final byte TYPE = 4;
    private static final byte DETAIL = 0;

    public static final byte INFORMATION = 1 << TYPE;
    public static final byte SUCCESS = 2 << TYPE;
    public static final byte REDIRECTION = 3 << TYPE;
    public static final byte CLIENT_ERROR = 4 << TYPE;
    public static final byte SERVER_ERROR = 5 << TYPE;

    // detail 은 필요시 정의
}
