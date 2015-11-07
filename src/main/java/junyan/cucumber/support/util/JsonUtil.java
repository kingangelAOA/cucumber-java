package junyan.cucumber.support.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.path.json.JsonPath;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import junyan.cucumber.support.exceptions.InterfaceException;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingangeltot on 15/9/30.
 */
public class JsonUtil extends Common {
    private Gson gson;
    public JsonUtil(){
        gson = new Gson();
    }

    /**
     * map转换成string,用spacer来分隔
     * @param map
     * @param spacer
     * if spacer = &
     * @return a=b&b=c
     */
    public static String toString(Map<String, String> map, String spacer){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(spacer);
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * string转换成map, 分隔符是spacer
     * @param input
     * @param spacer
     * if input = "a=b&c=d"
     * @return {a=b,c=d}
     */
    public static Map<String, String> toMap(String input, String spacer) {
        Map<String, String> map = new HashMap<String, String>();

        String[] nameValuePairs = input.split(spacer);
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                        nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return map;
    }



    /**
     *
     * @param type
     * @return reqeuestBody的类型
     * @throws InterfaceException
     */
    private static MediaType setContentType(String type) throws InterfaceException {
        if (type.equals("JSON"))
            return MediaType.parse("application/interface_data; charset=utf-8");
        else if (type.equals("FORM"))
            return MediaType.parse("multipart/form-data");
        else if(type.equals("URL_ENCODE_FORM"))
            return MediaType.parse("application/x-www-form-urlencoded");
        else
            throw new InterfaceException("contentType:"+type+",此类型暂时不支持.....");
    }

    /**
     * 设置requestBody,支持JSON,FORM,URL_ENCODE_FORM
     * @param json
     * @param contentType
     * @return RequestBody
     * @throws InterfaceException
     */
    public static RequestBody setBodyByType(String json, String contentType) throws InterfaceException {
        FormEncodingBuilder formEncodingBuilder =  new FormEncodingBuilder();
        if (contentType.equals("FORM")){
            for(Map.Entry<String, String> entry : toMap(toElement(json)).entrySet()){
                formEncodingBuilder.add(entry.getKey(), entry.getValue().toString());
            }
            return formEncodingBuilder.build();
        }else if (contentType.equals("URL_ENCODE_FORM")){
            for(Map.Entry<String, String> entry : toMap(toElement(json)).entrySet()){
                formEncodingBuilder.addEncoded(entry.getKey(), entry.getValue().toString());
            }
            return formEncodingBuilder.build();
        }else if (contentType.equals("JSON")) {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        } else
            throw new InterfaceException("不支持此类型的body类型: " + contentType + "; 只支持 FORM, URL_ENCODE_FORM, JSON");
    }
    /**
     * 判断此字符串是否是数据类型的字符串
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * map转 json字符串
     * @param map
     * @return
     */
    public String toJson(Map map){
        return gson.toJson(map);
    }

    /**
     * response的headers转map
     * @param map
     * @return
     */
    public static Map toMap(Map<String, List<String>> map){
        Map newMap = new HashMap<>();
        for (String key : map.keySet()){
            List<String> list = map.get(key);
            if (list.size() == 1)
                newMap.put(key, list.get(0));
            else
                newMap.put(key, list);
        }
        return newMap;
    }

    /**
     * jsonElement 对象转json字符串
     * @param jsonElement
     * @return
     */
    public String toJson(JsonElement jsonElement){
        return gson.toJson(jsonElement);
    }

    /**
     * JsonElement对象转map对象
     * @param jsonElement
     * @return
     */
    public static Map<String, String> toMap(JsonElement jsonElement){
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return new Gson().fromJson(jsonElement, type);
    }

    /**
     * 转jsonElement对象
     * @param json
     * @return
     */
    public JsonElement readerToJson(Reader json){
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(json);
    }


    public Object getDataFromJson(JsonElement jsonElement, Iterator<JsonElement> iterator){
        while (iterator.hasNext()){
            Object i =  iterator.next();
            System.out.println(i);
        }
        return null;
    }


    /**
     * 原始数据转JsonPrimitive对象
     * @param primitive
     * @return
     * @throws InterfaceException
     */
    public JsonPrimitive getJsonPrimitiveType(Object primitive) throws InterfaceException {
        if (primitive instanceof Boolean)
            return new JsonPrimitive((Boolean) primitive);
        else if (primitive instanceof Number)
            return new JsonPrimitive((Number) primitive);
        else if (primitive instanceof String)
            return new JsonPrimitive(primitive.toString());
        else
            throw new InterfaceException("jsonPrimitive转换类型不存在.......");
    }

    /**
     * 获取JsonPrimitive对象的数据真实对象
     * @param primitive
     * @return
     * @throws InterfaceException
     */
    public Object getJsonPrimitiveType(JsonPrimitive primitive) throws InterfaceException {
        if (primitive.isBoolean())
            return primitive.getAsBoolean();
        else if (primitive.isNumber())
            return primitive.getAsNumber();
        else if (primitive.isString())
            return primitive.getAsString();
        else
            throw new InterfaceException("jsonPrimitive转换类型不存在.......");
    }

    /**
     * 从JsonElement中获取数据根据数组索引
     * @param indexStr
     * @param jsonElement
     * @return
     * @throws InterfaceException
     */
    public JsonElement getDataByIndex(String indexStr, JsonElement jsonElement) throws InterfaceException {
        JsonPath jsonPath = new JsonPath(jsonElement.toString());
        puts(indexStr);
        return jsonPath.get(indexStr);
    }


    /**
     * 字符串是否包含 "${}" 或者 ${}
     * @param target
     * @return
     */


    /**
     * 替换数据根据"${}"或者${}
     * @param target
     * @param sources
     *
     * example:
     * target = select * from users where id = ${user_id} and name = "${user_name}"
     * sources = {"user_id":1111111, "user_name":"hehe"}
     * @return select * from users where id = 1111111 and name = "hehe"
     *
     * or {
     *      "aa" = "${xxxxx}"
     *    }
     * @throws InterfaceException
     */

    /**
     *
     * @param reader
     * @return
     * @throws IOException
     */
    public String toSting(Reader reader) throws IOException {
        char[] arr = new char[8 * 1024];
        StringBuilder buffer = new StringBuilder();
        int numCharsRead;
        while ((numCharsRead = reader.read(arr, 0, arr.length)) != -1) {
            buffer.append(arr, 0, numCharsRead);
        }
        reader.close();
        return buffer.toString();
    }

    /**
     *
     * @param target
     * @param array
     * @param value
     * @return
     * @throws InterfaceException
     */
    public JsonElement setValue(JsonElement target, JsonArray array, JsonPrimitive value) throws InterfaceException {
        if (array.size() > 1){
            JsonElement element = array.get(0);
            array.remove(0);
            if (!element.isJsonPrimitive())
                throw new InterfaceException("数组索引支持 string, int");
            JsonPrimitive jsonPrimitive = element.getAsJsonPrimitive();
            if (jsonPrimitive.isNumber() || jsonPrimitive.isString()) {
                if (jsonPrimitive.isNumber()) {
                    if (target.getAsJsonArray().size() - 1 < jsonPrimitive.getAsInt())
                        throw new InterfaceException(target.getAsJsonArray()+" << "+jsonPrimitive.getAsInt()+" : 数组越界");
                    setValue(target.getAsJsonArray().get(jsonPrimitive.getAsInt()), array, value);
                } else {
                    if (!target.getAsJsonObject().has(jsonPrimitive.getAsString()))
                        throw new InterfaceException(target.getAsJsonObject()+" << "+jsonPrimitive.getAsString()+" : 没有此key");
                    if (target.getAsJsonObject().get(jsonPrimitive.getAsString()).isJsonPrimitive() ||
                            target.getAsJsonObject().get(jsonPrimitive.getAsString()).isJsonNull())
                        throw new InterfaceException(target.getAsJsonObject().get(jsonPrimitive.getAsString())+" << 必须是json对象");
                    setValue(target.getAsJsonObject().get(jsonPrimitive.getAsString()), array, value);
                }
            }else
                throw new InterfaceException("数组索引支持 string, int");
        } else {
            if (!target.isJsonObject())
                throw new InterfaceException("array最后一位必须是string");
            target.getAsJsonObject().add(array.get(0).getAsString(), value);
        }
        return target;
    }

    /**
     * json字符串转JsonElement对象
     * @param json
     * @return
     */
    public static JsonElement toElement(String json){
        return new JsonParser().parse(json);
    }

    /**
     * 字符串转JsonPrimitive
     * @param string
     * @return
     */
    public JsonPrimitive toJsonPrimitive(String string) {
        return new JsonPrimitive(string);
    }

    /**
     * 数字转JsonPrimitive
     * @param number
     * @return
     */
    public JsonPrimitive toJsonPrimitive(Number number) {
        return new JsonPrimitive(number);
    }

    /**
     * boolean转JsonPrimitive
     * @param hehe
     * @return
     */
    public JsonPrimitive toJsonPrimitive(Boolean hehe) {
        return new JsonPrimitive(hehe);
    }

    /**
     * newData更新到target对象中
     * @param target
     * @param newData
     * @return
     */
    public static JsonElement update(JsonObject target, JsonObject newData){
        for (Map.Entry<String, JsonElement> map : newData.entrySet()) {
            target.add(map.getKey(), map.getValue());
        }
        return target;
    }

    /**
     *
     * @param reader
     * @return
     */
    public static JsonElement toElement(Reader reader){
        return new JsonParser().parse(reader);
    }
}