package com.mohit.grocerystore.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mohit.grocerystore.R;
import com.mohit.grocerystore.activity.LoginRegisterActivity;
import com.mohit.grocerystore.activity.MainActivity;
import com.mohit.grocerystore.model.User;
import com.mohit.grocerystore.util.CustomToast;
import com.mohit.grocerystore.util.Utils;
import com.mohit.grocerystore.util.localstorage.LocalStorage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private  static View view;
    private static EditText fullname, emailId, mobilenumber, password;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    User user;


    public SignUp_Fragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        firebaseDatabase = FirebaseDatabase.getInstance("https://grocery-store-59251.firebaseio.com/");
        mDatabase = firebaseDatabase.getReference();
        initViews();
        setListeners();
        return view;


    }

    private void initViews() {
        fullname = view.findViewById(R.id.fullName);
        emailId = view.findViewById(R.id.userEmaiilId);
        mobilenumber = view.findViewById(R.id.mobilNumber);
        password = view.findViewById(R.id.password);
        signUpButton = view.findViewById(R.id.signupBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
        progressDialog = new ProgressDialog(getContext());

       @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try{
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        }catch (Exception e){

        }

    }

    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupBtn:
                checkValidation();
                break;
            case R.id.already_user:
                new LoginRegisterActivity().replaceLoginFragment();
                break;
        }
    }

    private void checkValidation() {
        String getFullName = fullname.getText().toString();
        String getEmailId = emailId.getText().toString();
        String getMobileNumber = mobilenumber.getText().toString();
        String getPassword = password.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);
        if(getFullName.length() == 0){
            fullname.setError("Enter Your Name");
            fullname.requestFocus();
        }
        else if(getEmailId.length() == 0){
            emailId.setError("Enter Your EmailId");
            emailId.requestFocus();
        }
        else if(!m.find()){
            emailId.setError("Enter Correct Email");
            emailId.requestFocus();
        }
        else if(getMobileNumber.length() == 0){
            mobilenumber.setError("Enter Mobile Number");
            mobilenumber.requestFocus();
        }
        else if (getPassword.length() == 0){
            password.setError("Enter Password");
            password.requestFocus();
        }
        else if (getPassword.length() < 6){
            password.setError("Password must have 6 Characters");
            password.requestFocus();
        }
        else if (!terms_conditions.isChecked()){
            new CustomToast().Show_Toast(getActivity(), view, "Accept terms & Conditions");
        }

        else{
            User user = new User(getFullName, getEmailId, getMobileNumber, getPassword);
            mDatabase.child("user").child(getMobileNumber).setValue(user);

            //LocalStorage localStorage = new LocalStorage(getContext());
            progressDialog.setMessage("Registering Data...");
            progressDialog.show();
            Handler hand = new Handler();
            hand.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                    getActivity().finish();
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            }, 5000);
        }

    }

}
