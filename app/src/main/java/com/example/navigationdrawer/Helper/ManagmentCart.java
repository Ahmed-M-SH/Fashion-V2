package com.example.navigationdrawer.Helper;

import android.content.Context;

import com.example.fashion.Domain.PopularDomain;
import com.example.fashion.Domain.ProductDetail;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(ProductDetail item){
//        ArrayList<ProductDetail> listPop = getListCart();
//        boolean existAlready=false;
//
//        int n=0;
//        for (int i=0;i<listPop.size();i++){
//            if(listPop.get(i).getId().equals(item.getId())){
//                existAlready = true;
//                n=i;
//                break;
//            }
//        }
////        if(existAlready)
//////            listPop.get(n).setNumberinCart(item.getNumberinCart());
////        else
////            listPop.add(item);
//
//        tinyDB.putListObject("CartList",listPop);
//        Toast.makeText(context, R.string.add_to_cart_text, Toast.LENGTH_SHORT).show();

    }

    public ArrayList<PopularDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberItems(ArrayList<PopularDomain> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if (listItem.get(position).getNumberinCart()==1) {
            listItem.remove(position);
        } else {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() - 1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public void PlusNumberItem(ArrayList<PopularDomain> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart() +1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public Double getTotalFee(){
        ArrayList<PopularDomain> listItem=getListCart();
    double fee=0;
    for (int i=0; i< listItem.size();i++) {

        fee = fee + (listItem.get(i).getPrice() * listItem.get(i).getNumberinCart());

    }
        return fee;
    }
}
