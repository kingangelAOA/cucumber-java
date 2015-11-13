package junyan.cucumber.support.models;

import com.google.gson.JsonElement;
import com.squareup.okhttp.Response;
import junyan.cucumber.support.util.Common;

/**
 * Created by kingangelTOT on 15/11/7.
 */
public class RequestData {
    private String url = "";
    private String method ="" ;
    private String headers = "{}";
    private String body = "";
    private String interfaceName = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}
