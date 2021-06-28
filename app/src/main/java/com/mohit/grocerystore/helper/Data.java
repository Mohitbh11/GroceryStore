package com.mohit.grocerystore.helper;

import com.mohit.grocerystore.model.Category;
import com.mohit.grocerystore.model.Offer;
import com.mohit.grocerystore.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Data {

    List<Product> productList = new ArrayList<>();
    List<Product> VegeList =  new ArrayList<>();

    public List<Product> getProductList() {
        Product product = new Product("1",  "Fortune Chakki Fresh Atta", "", "10 Kg", "Rs.", "320", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/516L7l3%2BltL._SX425_.jpg");
        productList.add(product);
        product = new Product("2",  "Dabur Anmol Coconut Oil", "Gold 100 % Pure", "600ml", "Rs.", "160", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/71GuUUAnuJL._SL1500_.jpg");
        productList.add(product);
        product = new Product("3",  "NIVEA Whitening Smooth Skin", "Deodorant Roll-on", "190g", "Rs.", "129", "25% OFF", "https://images-na.ssl-images-amazon.com/images/I/714Qp4ljpBL._SL1500_.jpg");
        productList.add(product);
        product = new Product("4",  "NIVEA Lemon & Oil Shower Gel", " Care Oil Pearls And Revitalizing Scent Of Lemon", "250ml", "Rs.", "135", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/51gmJPaQfLL._SL1000_.jpg");
        productList.add(product);
        product = new Product("5",  "Santoor Sandal & Turmeric Soap", "Haldi or Chanadan ke Gun ", "4 Pack", "Rs.", "130", "20% OFF", "https://images-na.ssl-images-amazon.com/images/I/815B98uwHuL._SL1500_.jpg");
        productList.add(product);
        product = new Product("6",  "Colgate Plax Antibacterial Mouthwash", "24/7 Fresh Breath", "500 Ml", "Rs.", "196", "15% OFF", "https://images-na.ssl-images-amazon.com/images/I/613%2BhiXzFLL._SL1000_.jpg");
        productList.add(product);
        product = new Product("7", "Tata Salt", "", "1 Kg", "Rs.", "20", "", "https://images-na.ssl-images-amazon.com/images/I/61n1hm72dlL._SL1000_.jpg");
        productList.add(product);
        product = new Product("8", "Saffola Gold", "", "1 Ltr", "Rs.", "120", "", "https://images-na.ssl-images-amazon.com/images/I/71PA6oc4PXL._SL1500_.jpg");
        productList.add(product);
        product = new Product("9", "Dark Fantasy", "", "600 gm", "Rs.", "80", "", "https://images-na.ssl-images-amazon.com/images/I/71yR4SYp3iL._SL1500_.jpg");
        productList.add(product);
        product = new Product("10", "Maggi", "", "560 gm", "Rs.", "75", "", "https://images-na.ssl-images-amazon.com/images/I/81tiRzUBKEL._SL1500_.jpg");
        productList.add(product);
        return productList;
    }

    public List<Product> getVegetableList() {
        Product product = new Product("1",  "Bhindi", "", "1 Kg", "Rs.", "30", "5% OFF", "https://m.media-amazon.com/images/I/417on5F5IKL._AC_SS350_.jpg");
        VegeList.add(product);
        product = new Product("2",  "Aaloo", "", "1 Kg", "Rs.", "40", "5% OFF", "https://images-na.ssl-images-amazon.com/images/I/61yXL70-RaL._SL1500_.jpg");
        VegeList.add(product);
        return VegeList;
    }





}
