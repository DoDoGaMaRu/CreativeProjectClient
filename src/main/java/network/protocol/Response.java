package network.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte status;
    private JSONObject body;

    @NoArgsConstructor
    public static class ResponseBuilder{
        private byte status;
        private JSONObject body;

        public Response build() {
            return new Response(status, body);
        }

        public ResponseBuilder status(byte status) {
            this.status = status;
            return this;
        }
        public ResponseBuilder body(JSONObject body) {
            this.body = body;
            return this;
        }
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }
}
