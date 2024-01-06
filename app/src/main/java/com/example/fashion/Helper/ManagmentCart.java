package com.example.fashion.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.fashion.Domain.CartProduct;
import com.example.fashion.R;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertProduct(CartProduct item){
        ArrayList<CartProduct> listPop = getListCart();
        boolean existAlready=false;

        int n=0;
        for (int i=0;i<listPop.size();i++){
            if(listPop.get(i).getId().equals(item.getId())){
                existAlready = true;
                n=i;
                break;
            }
        }
        if(existAlready)
            listPop.get(n).setQty(item.getQty());
        else
            listPop.add(item);

        tinyDB.putListObject("CartList",listPop);
        Toast.makeText(context, R.string.add_to_cart_text, Toast.LENGTH_SHORT).show();

    }

    public ArrayList<CartProduct> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberItems(ArrayList<CartProduct> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if (listItem.get(position).getQty()==1) {
            listItem.remove(position);
        } else {
            listItem.get(position).setQty(listItem.get(position).getQty() - 1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public void PlusNumberItem(ArrayList<CartProduct> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setQty(listItem.get(position).getQty() +1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public Double getTotalFee(){
        ArrayList<CartProduct> listItem=getListCart();
    double fee=0;
    for (int i=0; i< listItem.size();i++) {

        fee = fee + (listItem.get(i).getPrice() * listItem.get(i).getQty());
    }
        return fee;
    }

    public void commit(){

    }
}
