/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vi.utils.DBConnection;

/**
 *
 * @author VI
 */
public class UserDAO {

    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (cn != null) {
            cn.close();
        }
    }

    public boolean createNewAccount(UserDTO user) throws SQLException {
        boolean check = false;
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "insert into Employee (userID,password,name,phone,address,roleID,date,status)\n"
                        + "values (?,?,?,?,?,?,?,?)";
                pst = cn.prepareStatement(sql);
                pst.setString(1, user.getUserID());
                pst.setString(2, user.getPassword());
                pst.setString(3, user.getName());
                pst.setString(4, user.getPhone());
                pst.setString(5, user.getAddress());
                pst.setInt(6, user.getRole());

                Long milis = System.currentTimeMillis();
                //000000123132L 
                Date date = new Date(milis);

                pst.setString(7, date.toString());
                pst.setString(8, "New");
                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean checkLogin(String userID, String password) throws SQLException {
        UserDTO user = null;
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select name,roleID\n"
                        + "from Employee\n"
                        + "where userID=? and password=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public UserDTO checkUser(String userID) throws SQLException {
        UserDTO result = null;
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select [userID],[password],[name],[phone],[address],[date],[roleID],[status]\n"
                        + "from [dbo].[Employee]\n"
                        + "where [userID]= ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String id = rs.getString("userID");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String date = rs.getString("date");
                    int roleID = rs.getInt("roleID");
                    String status = rs.getString("status");
                    UserDAO dao = new UserDAO();
                    result = new UserDTO(id, password, name, phone, address, date, roleID, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;

    }

    public String checkStatusForAccount(String userID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String result = "";
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select e.status\n"
                        + "from Employee e\n"
                        + "where userID=?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, userID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    result = rs.getString("status");
                    return result;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public static void main(String[] args) throws SQLException {
        UserDAO dao = new UserDAO();
      boolean check =  dao.checkLogin("thanhvi", "2411");
        System.out.println(check);
    }
    

}
