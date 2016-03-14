package passwordvault.somesh.com.passwordvault.Authentication;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.realm.Realm;
import io.realm.RealmResults;
import passwordvault.somesh.com.passwordvault.R;

public class Auth extends AppCompatActivity
{
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        realm = Realm.getInstance(this);
        checkIfAccountPresent();
    }

    private void checkIfAccountPresent()
    {
        RealmResults<UserModel> userResults = realm.allObjects(UserModel.class);
        if(userResults.size()>0){
            //TODO:User has an account
            Fragment login = new Login();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerLoginCreate,login,"LoginFragment").commit();
        }
        else{
            //TODO:USer has no account so create one
            Fragment createAccount = new CreateAccount();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerLoginCreate,createAccount,"CreateAccountFragment").commit();
        }
    }
}
