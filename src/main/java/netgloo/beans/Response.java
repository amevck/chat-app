package netgloo.beans;

/**
 * Created by Dharshan on 8/22/2017.
 */
public class Response {
    private String message;
    private int code;
    private String description;
    private int userId;
    private  boolean isSuccess;
  



	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSucces) {
		this.isSuccess = isSucces;
	}


}
