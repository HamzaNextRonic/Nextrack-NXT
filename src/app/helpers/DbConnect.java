package app.helpers;


//import org.sqlite.mc.SQLiteMCChacha20Config;
//import org.sqlite.mc.SQLiteMCConfig;

//import org.sqlite.SQLiteConfig;
//import org.sqlite.mc.SQLiteMCChacha20Config;
//import org.sqlite.mc.SQLiteMCConfig;
//import org.sqlite.mc.SQLiteMCWxAES256Config;



import org.sqlite.SQLiteConfig;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {




    public static Connection getConnection(){
       try {
            Class.forName("org.sqlite.JDBC");

            Class.forName("org.sqlite.JDBC");//, new SQLiteMCWxAES256Config().withKey("douyryNext").toProperties()
            Connection conn = DriverManager.getConnection("jdbc:sqlite:nextrack.db");
//           final SQLiteConnectionPoolDataSource dataSource = new SQLiteConnectionPoolDataSource();
//           dataSource.setUrl("jdbc:sqlite:nextrack.db");
//           dataSource.getConfig().setHexKeyMode(SQLiteConfig.HexKeyMode.valueOf("123456"));
//
//            conn=dataSource.getConnection();
           return conn;
       }catch (Exception e){
        return  null;
       }
    }
}
