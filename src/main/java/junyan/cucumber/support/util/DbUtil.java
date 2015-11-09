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
        List queryForString = new QueryUtil(conn).executeQuery(sql);
        if (queryForString.size()-1 < index)
            throw new InterfaceException("所请求的index超出sql查询结果的条数...");
        JsonElement jsonElement = new JsonParser().parse("{}");
        Map map = (Map)queryForString.get(index);
        for (String string:list.split(",")){
            jsonElement.getAsJsonObject().add(string, toJsonPrimitive(map.get(string).toString()));
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Gson().toJson(jsonElement);
    }
}
