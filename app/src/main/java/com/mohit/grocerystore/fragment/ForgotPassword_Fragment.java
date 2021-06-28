package com.mohit.grocerystore.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mohit.grocerystore.R;
import com.mohit.grocerystore.activity.LoginRegisterActivity;
import com.mohit.grocerystore.util.CustomToast;
import com.mohit.grocerystore.util.Utils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener {
    public  static View view;
    private static EditText emailId;
    private TextView submit, back;

    public ForgotPassword_Fragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        emailId = view.findViewById(R.id.registered_email);
        submit = view.findViewById(R.id.forgot_button);
        back = view.findViewById(R.id.backToLoginBtn);
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try{
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            back.setTextColor(csl);
            submit.setTextColor(csl);
        } catch (Exception e) {
        }


    }

    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backToLoginBtn:
                new LoginRegisterActivity().replaceLoginFragment();
                break;
            case R.id.forgot_button:
                submitButtonTask();
                break;
        }
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);
        if(getEmailId.equals("") || getEmailId.length() == 0)
            new CustomToast().Show_Toast(getActivity(), view, "Enter Your Email");
        else if(!m.find())
            new CustomToast().Show_Toast(getActivity(), view, "Your Email is Invalid");
        else
            Toast.makeText(getActivity(), "Get Forgot Password", Toast.LENGTH_LONG).show();
    }
}
