/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.request;

/**
 *
 * @author VI
 */
public class RequestDTO {

    private String requestID;
    private String status;
    private String userID;
    private String itemID;

    public RequestDTO() {
    }

    public RequestDTO(String requestID, String status, String userID, String itemID) {
        this.requestID = requestID;
        this.status = status;
        this.userID = userID;
        this.itemID = itemID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

}
