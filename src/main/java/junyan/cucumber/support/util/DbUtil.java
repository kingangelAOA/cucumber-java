package junyan.cucumber.support.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mysql.management.util.QueryUtil;
import junyan.cucumber.support.exceptions.InterfaceException;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kingangelTOT on 15/10/3.
 */
public class DbUtil extends JsonUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private Connection conn;
    public DbUtil(String env){
        initDB(env);
    }

    public void initDB(String env){
        Map<String, Map<String, String>> map = Common.toMapByYaml("/src/main/java/config/db.yaml");
        Map<String, String> connectConf = map.get(env);
        String userName = connectConf.get("username");
        String password = connectConf.get("password");
        try {
            Class.forName(DRIVER);
            String dbName = connectConf.get("database");
            String url = "jdbc:mysql://"+connectConf.get("host")+":" + connectConf.get("port") + "/" + dbName
                    + "?" + "createDatabaseIfNotExist=true";
            conn = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据sql语句获取数据
     * @param sql
     * @param index sql结果的行数数组索引下标
     * @param list 要去哦获取的字段数组 example:[name, age]
     * @return {"name":"hehe","age":18}
     * @throws InterfaceException
     */
    public String getDataBySql(String sql, int index, String list) throws InterfaceException {
        Iterator<JsonElement> iterator = toElement(list).getAsJsonArray().iterator();
        List queryForString = new QueryUtil(conn).executeQuery(sql);
        if (queryForString.size()-1 < index)
            throw new InterfaceException("所请求的index超出sql查询结果的条数...");
        JsonElement jsonElement = new JsonParser().parse("{}");
        Map map = (Map)queryForString.get(index);
        while (iterator.hasNext()){
            JsonElement element =  iterator.next();
            Object elementType = getJsonPrimitiveType(element.getAsJsonPrimitive());
            if (elementType instanceof String) {
                jsonElement.getAsJsonObject().add(element.getAsString(), toJsonPrimitive(map.get(element.getAsString()).toString()));
            } else {
                throw new InterfaceException("sql字段索引只能是字符串.....");
            }
        }
        return new Gson().toJson(jsonElement);
    }

//    public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, YamlException, SQLException, InterfaceException {
//        DbUtil mysql = new DbUtil("debug");
//        String target = "select * from users where id = {user_id} and status = '{bbb}'";
//        String interface_data = "{\"user_id\":1, \"bbb\":\"life\"}";
//        Map sources = mysql.jsonToMap(interface_data);
//        String str = mysql.regularBrace(target, sources);
//
//        Map map = mysql.getDataBySql(str, 0, "[\"name\", \"platform\"]");
//        JsonUtil util = new JsonUtil();
//        puts(util.mapToJson(map));
//    }
}
