package app.model;


import app.helpers.DbConnect;
import app.model.prevalent.Prevalent;

import java.sql.*;

public class LoginModel {

    public Connection connection;
    //public HomeController homeController;

    public LoginModel() {
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:nextrack.db","ANextronic","douyryNext" );
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        connection = DbConnect.getConnection();
//        try {
//            connection = SQLiteMCChacha20Config.getDefault().withKey("Key").createConnection("jdbc:sqlite:nextrack.db");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        if (connection == null) {
            System.out.println("no connection");
            System.exit(1);
        }

    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public boolean isLogin(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int resultSet1 = 0;
        User userData = new User();

        String query = "select * from user where username = ? and password = ?";


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                userData.setId(resultSet.getInt("id"));
                userData.setUsername(resultSet.getString("username"));
                userData.setPassword(resultSet.getString("password"));
                userData.setStatus((resultSet.getString("status")));
                userData.setType(Integer.parseInt(resultSet.getString("type")));
                userData.setLastCctn(resultSet.getString("lastConnection"));
                // if(resultSet.getString("status").equals("1"))userData.setStatus("connecter");
                // query = "update user set status = "+1+" where id = "+resultSet.getInt("id")+"";
                // resultSet1 = preparedStatement.executeUpdate();
                userData.setStatus("connecté");
                Prevalent.currentOnlineUser = userData;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //always executed
            preparedStatement.close();
            resultSet.close();
        }
    }
}
