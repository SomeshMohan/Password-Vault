package passwordvault.somesh.com.passwordvault.Authentication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.realm.Realm;
import io.realm.RealmResults;
import passwordvault.somesh.com.passwordvault.HomeScreen;
import passwordvault.somesh.com.passwordvault.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private EditText userId;
    private EditText password;
    private Button login;
    private Button forgotPassword;
    private Realm realm;
    private UserModel user;


    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        bindUI(view);
        bindListeners();
        return view;
    }

    private void bindUI(View view)
    {
        userId = (EditText) view.findViewById(R.id.userId_EditText);
        password = (EditText) view.findViewById(R.id.password_EditText);
        login = (Button) view.findViewById(R.id.login_Button);
        forgotPassword = (Button) view.findViewById(R.id.forgotPassword_Button);
    }

    private void bindListeners()
    {
        login.setOnClickListener(new ClickListener());
        forgotPassword.setOnClickListener((new ClickListener()));
    }

    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.login_Button:
                    realm = Realm.getInstance(getActivity());
                    boolean isCredentialsCorrect = checkCredentials(userId.getText().toString(),password.getText().toString());
                    if(isCredentialsCorrect) {
                        Fragment prev = getFragmentManager().findFragmentByTag("LoginFragment");
                        if (prev != null) {
                            getFragmentManager().beginTransaction().remove(prev).commit();
                        }
                        Intent homeScreenIntent = new Intent(getActivity(), HomeScreen.class);
                        startActivity(homeScreenIntent);
                    }
                    else{
                        Toast.makeText(getActivity(),"Invalid Credentials",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.forgotPassword_Button:
                    Fragment prev = getFragmentManager().findFragmentByTag("LoginFragment");
                    if(prev!=null){
                        getFragmentManager().beginTransaction().remove(prev).commit();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.containerLoginCreate,new ForgotPassword(),"ForgotPasswordFragment").commit();
                    break;

            }
        }
    }

    private boolean checkCredentials(String userId,String password)
    {
        RealmResults<UserModel> credentialsFromDatabase;
        String passwordHash = createHash(password);
        if(passwordHash!=null){
            realm.beginTransaction();
            user = realm.createObject(UserModel.class);
            credentialsFromDatabase = realm.where(UserModel.class).equalTo("userId", userId).findAll();
            realm.commitTransaction();
            if(credentialsFromDatabase.size()>0){
                if(credentialsFromDatabase.get(0).getPassword().equals(passwordHash)){
                    return true;
                }
                else
                    return false;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    private String createHash(String password)
    {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(password.getBytes());
            byte messageDigest[] = digester.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }
            return MD5Hash.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

}
