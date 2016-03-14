package passwordvault.somesh.com.passwordvault.Authentication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import passwordvault.somesh.com.passwordvault.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotPassword extends Fragment {

    private EditText userId;
    private Button reset;
    private Realm realm;


    public ForgotPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        bindUI(view);
        bindListeners();
        return view;
    }

    private void bindUI(View view)
    {
        userId = (EditText) view.findViewById(R.id.userId_EditText);
        reset = (Button) view.findViewById(R.id.resetPassword_Button);
    }
    private void bindListeners()
    {
        reset.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.resetPassword_Button:
                    boolean isUserIdPresent = validateUserId(userId.getText().toString());
                    if(isUserIdPresent)
                    {
                        //TODO:Send a reset mail to the user email Id
                    }
                    else{
                        Toast.makeText(getActivity(),"User Id cannot be found",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private boolean validateUserId(String userId)
    {
        realm = Realm.getInstance(getActivity());
        realm.beginTransaction();
        RealmResults<UserModel> credentialResult = realm.where(UserModel.class).equalTo("userId",userId).findAll();
        realm.commitTransaction();
        if(credentialResult.size()>0){
            return true;
        }
        else{
            return false;
        }
    }

}
