package com.mohit.grocerystore.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.mohit.grocerystore.R;
import com.mohit.grocerystore.activity.BaseActivity;
import com.mohit.grocerystore.activity.CartActivity;
import com.mohit.grocerystore.activity.MainActivity;
import com.mohit.grocerystore.adapter.CheckoutCartAdapter;
import com.mohit.grocerystore.model.Cart;
import com.mohit.grocerystore.model.Order;
import com.mohit.grocerystore.model.OrderConfirm;
import com.mohit.grocerystore.model.User;
import com.mohit.grocerystore.util.localstorage.LocalStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ConfirmFragment extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    LocalStorage localStorage;
    List<Cart> cartList = new ArrayList<>();
    Gson gson;
    RecyclerView recyclerView;
    CheckoutCartAdapter adapter;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    TextView back, placeOrder;
    TextView total, shipping, totalAmount;
    Double _total, _shipping, _totalAmount;
    ProgressDialog progressDialog;
    List<Order> orderList = new ArrayList<>();
    String orderNo;
    String id;

    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        localStorage = new LocalStorage(getContext());
        recyclerView = view.findViewById(R.id.cart_rv);
        totalAmount = view.findViewById(R.id.total_amount);
        total = view.findViewById(R.id.total);
        shipping = view.findViewById(R.id.shipping_amount);
        back = view.findViewById(R.id.back);
        placeOrder = view.findViewById(R.id.place_order);
        progressDialog = new ProgressDialog(getContext());
        gson = new Gson();
        orderList = ((BaseActivity) getActivity()).getOrderList();
        Random rnd = new Random();
        orderNo = "Order #" + (100000 + rnd.nextInt(900000));
        setUpCartRecyclerview();
        if (orderList.isEmpty()) {
            id = "1";
        } else {
            id = String.valueOf(orderList.size() + 1);
        }


        _total = ((BaseActivity) getActivity()).getTotalPrice();
        _shipping = 0.0;
        _totalAmount = _total + _shipping;
        total.setText(_total + "");
        shipping.setText(_shipping + "");
        totalAmount.setText(_totalAmount + "");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please Wait....");
                progressDialog.show();
                closeProgress();

            }
        });


        return view;
    }

    private void closeProgress() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();

            }
        }, 3000); // 5000 milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        Order order = new Order(id, orderNo, currentDateandTime, "Rs. " + _totalAmount, "Confirm");
        orderList.add(order);
        firebaseDatabase = FirebaseDatabase.getInstance("https://grocery-store-59251.firebaseio.com/");
        mDatabase = firebaseDatabase.getReference();
        LocalStorage localStorage = new LocalStorage(getContext());
        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        userString = localStorage.getUserLogin();
        User user = gson.fromJson(userString, User.class);
        String Name = user.getName();
        OrderConfirm confirm = new OrderConfirm(Name, id,orderNo, currentDateandTime, _totalAmount);
        mDatabase.child("Order").child(id).setValue(confirm);
        String orderString = gson.toJson(orderList);
        localStorage.setOrder(orderString);
        localStorage.deleteCart();

        showCustomDialog();
    }

    private void showCustomDialog() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        // Include dialog.xml file
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        // Set dialog title

        dialog.show();
    }

    private void setUpCartRecyclerview() {

        cartList = new ArrayList<>();
        cartList = ((BaseActivity) getContext()).getCartList();


        recyclerView.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewlayoutManager);
        adapter = new CheckoutCartAdapter(cartList, getContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Confirm");
    }


}
