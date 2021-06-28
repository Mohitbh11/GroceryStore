package com.mohit.grocerystore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.mohit.grocerystore.R;
import com.mohit.grocerystore.model.User;
import com.mohit.grocerystore.util.localstorage.LocalStorage;


public class ProfileFragment extends Fragment {
    User user;
    public ProfileFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LocalStorage localStorage = new LocalStorage(getContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        user = gson.fromJson(userString, User.class);

        String Name = user.getName();
        String Email = user.getEmail();
        String Mobile = user.getMobile();
        TextView userName = (TextView) view.findViewById(R.id.profile_name);
        TextView userEmail = (TextView) view.findViewById(R.id.profile_email);
        TextView userMobile = (TextView) view.findViewById(R.id.profile_number);
        userName.setText(Name);
        userEmail.setText(Email);
        userMobile.setText(Mobile);
        getActivity().setTitle("Profile");
    }
}
