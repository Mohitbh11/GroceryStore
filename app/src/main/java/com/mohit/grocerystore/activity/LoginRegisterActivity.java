package com.mohit.grocerystore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.view.View.OnClickListener;
import com.google.firebase.storage.internal.Util;
import com.mohit.grocerystore.R;
import com.mohit.grocerystore.fragment.Login_Fragment;
import com.mohit.grocerystore.util.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginRegisterActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        getSupportActionBar().hide();
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }

        findViewById(R.id.close_activity).setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );

    }
    public void replaceLoginFragment(){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Utils.Login_Fragment).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);

        if(SignUp_Fragment != null)
            replaceLoginFragment();
        else if(ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}
