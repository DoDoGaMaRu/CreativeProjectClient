package network.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte type;
    private byte code;
    private JSONObject cookie;
    private JSONObject body;

    @NoArgsConstructor
    public static class RequestBuilder{
        private byte type;
        private byte code;
        private JSONObject cookie;
        private JSONObject body;

        public Request build() {
            return new Request(type, code, cookie, body);
        }

        public RequestBuilder type(byte type) {
            this.type = type;
            return this;
        }
        public RequestBuilder code(byte code) {
            this.code = code;
            return this;
        }
        public RequestBuilder cookie(JSONObject cookie) {
            this.cookie = cookie;
            return this;
        }
        public RequestBuilder body(JSONObject body) {
            this.body = body;
            return this;
        }
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }
}