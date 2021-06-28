package com.mohit.grocerystore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mohit.grocerystore.R;
import com.mohit.grocerystore.activity.ProductActivity;
import com.mohit.grocerystore.activity.ProductVegetable;
import com.mohit.grocerystore.util.CustomToast;

public class HomeFragment extends Fragment  {

    public HomeFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout cooking = (RelativeLayout) view.findViewById(R.id.cooking);
        RelativeLayout vegetable = (RelativeLayout) view.findViewById(R.id.vegetable);

        cooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomToast().Show_Toast(getActivity(), view, "Cooking Essential is Clicked");
                startActivity(new Intent(getContext(), ProductActivity.class));

            }
        });

        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomToast().Show_Toast(getActivity(), view, "Vegetable is Clicked");
                startActivity(new Intent(getContext(), ProductVegetable.class));
            }
        });

    }
}
