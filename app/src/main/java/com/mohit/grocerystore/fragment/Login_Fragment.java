package com.mohit.grocerystore.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mohit.grocerystore.R;
import com.mohit.grocerystore.activity.MainActivity;
import com.mohit.grocerystore.model.User;
import com.mohit.grocerystore.util.CustomToast;
import com.mohit.grocerystore.util.Utils;
import com.mohit.grocerystore.util.localstorage.LocalStorage;

public class Login_Fragment extends Fragment implements OnClickListener {
    public static View view;
    private static EditText mobile, password;
    private static Button LoginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static Animation shakeAnimation;
    public static FragmentManager fragmentManager;
    private static LinearLayout loginLayout;
    ProgressDialog progressDialog;
    LocalStorage localStorage;
    String userString;
    User user;
    Gson gson;

    public Login_Fragment(){
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }


    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        mobile = view.findViewById(R.id.login_mobile);
        password = view.findViewById(R.id.login_password);
        LoginButton = view.findViewById(R.id.loginBtn);
        forgotPassword = view.findViewById(R.id.forgot_password);
        signUp = view.findViewById(R.id.createAccount);
        show_hide_password = view.findViewById(R.id.show_hide_password);
        loginLayout = view.findViewById(R.id.login_layout);
        progressDialog = new ProgressDialog(getContext());
        localStorage = new LocalStorage(getContext());
        String userData =localStorage.getUserLogin();
        Gson gson = new Gson();
        userData = localStorage.getUserLogin();
        user = gson.fromJson(userData, User.class);
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        @SuppressLint("ResourceType")XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try{
            ColorStateList cls = ColorStateList.createFromXml(getResources(), xrp);
        } catch (Exception e) {
        }

    }

    private void setListeners() {
        LoginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton Button, boolean isChecked) {
                if(isChecked){
                    show_hide_password.setText("Hide Password");
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    show_hide_password.setText("Show Password");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new ForgotPassword_Fragment(), Utils.ForgotPassword_Fragment).commit();
                break;

            case R.id.createAccount:
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;

        }
    }

    private void checkValidation() {
        final String getMobileNumber = mobile.getText().toString();
        final String getPassword = password.getText().toString();

        if (getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Enter Both Credentials.");
            vibrate(200);
        }
        else{
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            isUser();
            progressDialog.dismiss();
        }

    }

    private void isUser() {
        final String userMobile = mobile.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://grocery-store-59251.firebaseio.com/").getReference("user");
        Query checkUser = reference.orderByChild("mobile").equalTo(userMobile);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String dbPassword = snapshot.child(userMobile).child("password").getValue(String.class);
                    if(dbPassword.equals(Password)){
                        new CustomToast().Show_Toast(getActivity(), view, "Login Success");
                        String name = snapshot.child(userMobile).child("name").getValue().toString();
                        String email = snapshot.child(userMobile).child("email").getValue().toString();
                        String mobile = snapshot.child(userMobile).child("mobile").getValue().toString();
                        String password = snapshot.child(userMobile).child("password").getValue().toString();
                        user = new User(name, email, mobile, password);
                        gson = new Gson();
                        String userString = gson.toJson(user);
                        localStorage = new LocalStorage(getContext());
                        localStorage.createUserLoginSession(userString);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    }
                    else{
                        new CustomToast().Show_Toast(getActivity(), view, "Invalid Credentials. Try Again ");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void vibrate(int duration) {
        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(duration);
    }
}
