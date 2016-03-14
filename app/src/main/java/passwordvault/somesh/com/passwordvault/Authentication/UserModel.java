package passwordvault.somesh.com.passwordvault.Authentication;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by root on 14/3/16.
 */
public class UserModel extends RealmObject
{
    private String          userId;
    private String          password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
