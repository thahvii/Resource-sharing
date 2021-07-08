/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import vi.utils.DBConnection;

/**
 *
 * @author VI
 */
public class ItemDAO {

    public Connection cn = null;
    public PreparedStatement pst = null;
    public ResultSet rs = null;

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

    public List<ItemDTO> searchItem(String SearchValue) throws SQLException {
        List<ItemDTO> result = new ArrayList<>();
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select itemID,itemName,color,quantity, c.categoryName,i.status\n"
                        + "from Item i inner join Category c on i.categoryID=c.categoryID\n"
                        + "where itemName like ?\n"
                        + "	or categoryName like ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, "%" + SearchValue + "%");
                pst.setString(2, "%" + SearchValue + "%");
                rs = pst.executeQuery();

                while (rs.next()) {
                    String id = rs.getString("itemID");
                    String name = rs.getString("itemName");
                    String color = rs.getString("color");
                    String category = rs.getString("categoryName");
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String status = rs.getString("status");
                    ItemDTO dto = new ItemDTO(id, name, color, category, quantity, status);
                    result.add(dto);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ItemDTO> getAllItem() throws SQLException {
        List<ItemDTO> result = null;
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select itemID, itemName,color,categoryID,quantity,status\n"
                        + "from Item";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("itemID");
                    String name = rs.getString("itemName");
                    String color = rs.getString("color");
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String cateID = rs.getString("categoryID");
                    String status = rs.getString("status");
                    ItemDTO dto = new ItemDTO(id, name, color, cateID, quantity, status);
                    result.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public List<ItemDTO> getAllItemByStatus(String status) throws SQLException {
        List<ItemDTO> result = null;
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "select itemID, itemName,color,categoryID,quantity,status\n"
                        + "from Item \n"
                        + "Where status = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, status);
                rs = pst.executeQuery();
                result = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("itemID");
                    String name = rs.getString("itemName");
                    String color = rs.getString("color");
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    String cateID = rs.getString("categoryID");
                    String Status = rs.getString("status");
                    ItemDTO dto = new ItemDTO(id, name, color, cateID, quantity, Status);
                    result.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean OrderFromUserNormal(String id) throws SQLException, ClassNotFoundException {
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "update Item \n"
                        + "set status = ? \n"
                        + "where itemID = ?";

                pst = cn.prepareStatement(sql);
                pst.setString(1, "Waiting");
                pst.setString(2, id);
                if (pst.executeUpdate() > 0) {
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

    public boolean ActionAccept(String ItemID) throws SQLException, ClassNotFoundException {
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "update Item \n"
                        + "set status = ? \n"
                        + "where itemID = ? ";

                pst = cn.prepareStatement(sql);
                pst.setString(1, "Accept");
                pst.setString(2, ItemID);
                if (pst.executeUpdate() > 0) {
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

    public boolean ChangeStatus(String ItemID) throws SQLException {
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                ItemDTO item = new ItemDTO();
                if (item.getQuantity() > 0) {
                    String sql = "update Item \n"
                            + "set status = ? \n"
                            + "where itemID = ? ";

                    pst = cn.prepareStatement(sql);
                    pst.setString(1, "New");
                    pst.setString(2, ItemID);
                    if (pst.executeUpdate() > 0) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean ActionDelete(String ItemID) throws SQLException, ClassNotFoundException {
        try {
            cn = DBConnection.makeConnection();
            if (cn != null) {
                String sql = "update Item \n"
                        + "set status = ? \n"
                        + "where itemID = ? ";

                pst = cn.prepareStatement(sql);
                pst.setString(1, "Delete");
                pst.setString(2, ItemID);
                if (pst.executeUpdate() > 0) {
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

    public static void main(String[] args) throws SQLException {
        ItemDAO dao = new ItemDAO();
        List<ItemDTO> item = dao.getAllItem();
        for (ItemDTO itemDTO : item) {
            System.out.println(itemDTO.toString());
        }
    }
}
