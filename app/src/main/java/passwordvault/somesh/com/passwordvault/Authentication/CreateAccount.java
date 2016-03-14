package passwordvault.somesh.com.passwordvault.Authentication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Patterns;
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
import passwordvault.somesh.com.passwordvault.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccount extends Fragment
{
    private EditText userId;
    private EditText password;
    private EditText confirmPassword;
    private Button createAccount;
    private Realm realm;
    private UserModel user;

    public CreateAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        bindUI(view);
        bindListeners();
        return view;
    }

    private void bindListeners()
    {
        createAccount.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
          switch (v.getId())
          {
              case R.id.createAccount_Button:
                  String passwordHash = null;
                  boolean isCredentialsValid = validateCredentials();
                  if(isCredentialsValid)
                  {
                      //TODO:Credentials are valid.Create a hash of password and store it in the db.
                      //TODO:User Id shall not be converted to hash because it will be required to send email alerts
                      passwordHash = createHash(password.getText().toString());
                  }
                  if(passwordHash!=null) {
                      realm = Realm.getInstance(getActivity());
                      realm.beginTransaction();

                      user = realm.createObject(UserModel.class);
                      user.setUserId(userId.getText().toString());
                      user.setPassword(passwordHash);

                      realm.commitTransaction();
                      RealmResults<UserModel> realmResults = realm.allObjects(UserModel.class);
                      if(realmResults.size()>0){
                          //TODO:Goto Login page
                          Fragment prev = getFragmentManager().findFragmentByTag("CreateAccountFragment");
                          if(prev!=null){
                              getFragmentManager().beginTransaction().remove(prev).commit();
                          }
                          getFragmentManager().beginTransaction().replace(R.id.containerLoginCreate, new Login(),"LoginFragment").commit();
                      }
                      else{
                          Toast.makeText(getActivity(),"Unable to add to storage!Please try again",Toast.LENGTH_SHORT).show();
                      }
                  }
                  else{
                      Toast.makeText(getActivity(),"Something went wrong!Please try again",Toast.LENGTH_SHORT).show();
                  }
                  break;
          }
        }
    }

    private String createHash(String password)
    {
        try{
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(password.getBytes());
            byte messageDigest[] = digester.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

            return  MD5Hash.toString();

        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    private void bindUI(View view)
    {
        userId = (EditText)view.findViewById(R.id.userId_EditText);
        password = (EditText)view.findViewById(R.id.password_EditText);
        confirmPassword = (EditText)view.findViewById(R.id.confirmPassword_EditText);
        createAccount = (Button)view.findViewById(R.id.createAccount_Button);
    }

    private boolean validateCredentials()
    {
        if(userId.getText().toString()==null) {
            userId.setError("Please enter userId");
            return false;
        }
        else
        {
            if(password.getText().toString()==null || confirmPassword.getText().toString()==null){
                password.setError("Please enter password");
                return false;
            }
            else{
                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    confirmPassword.setText("Passwords do not match");
                    return false;
                }
                else{
                    if(!Patterns.EMAIL_ADDRESS.matcher(userId.getText().toString()).matches())
                    {
                        userId.setError("Email address invalid");
                        return false;
                    }
                    else
                        return true;
                }
            }
    }
    }

}
