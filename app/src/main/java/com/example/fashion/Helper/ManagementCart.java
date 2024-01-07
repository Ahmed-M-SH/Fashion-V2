package com.example.fashion.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.CartService;
import com.example.fashion.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertProduct(CartProduct item){
        List<CartProduct> listPop = getListCart();
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

    public List<CartProduct> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumberItems(List<CartProduct> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        List<CartProduct> removedProducts = tinyDB.getListObject("removedProducts");
        List<CartProduct> updatedProducts = tinyDB.getListObject("updatedProducts");
        if (position >= 0 && position < listItem.size()) { // Check if position is within bounds
            if (listItem.get(position).getQty() == 1) {
                removedProducts.add(listItem.get(position));
                listItem.remove(position);
                changeNumberItemsListener.itemRemoved(position);
            } else {
                listItem.get(position).setQty(listItem.get(position).getQty() - 1);
                updatedProducts.add(listItem.get(position));
                changeNumberItemsListener.change();
            }

            tinyDB.putListObject("CartList", listItem);
            tinyDB.putListObject("removedProducts", removedProducts);
            tinyDB.putListObject("updatedProducts", updatedProducts);
        }
    }
    public void PlusNumberItem(List<CartProduct> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setQty(listItem.get(position).getQty() +1);
        List<CartProduct> updatedProducts = tinyDB.getListObject("updatedProducts");
        updatedProducts.add(listItem.get(position));
        tinyDB.putListObject("CartList",listItem);
        tinyDB.putListObject("updatedProducts",updatedProducts);
        changeNumberItemsListener.change();
    }
    public Double getTotalFee(){
        List<CartProduct> listItem=getListCart();
    double fee=0;
    for (int i=0; i< listItem.size();i++) {

        fee = fee + (listItem.get(i).getPrice() * listItem.get(i).getQty());
    }
        return fee;
    }

    public void commit(){
//        boolean isAuthent= tinyDB.getBoolean("isAuthent");
//        if (isAuthent) {
//            String userAuth = tinyDB.getString("userAuth");
            String userAuth = "token 4ff24a3114344bc978419193eacdbca8316a82c8";
            CartService cartServiceUpdate =new CartService();
            List<CartProduct> removedItem = tinyDB.getListObject("removedProducts");
            List<CartProduct> updatedItem = tinyDB.getListObject("updatedProducts");
            if (updatedItem.size() > 0) {
                cartServiceUpdate.setCartItem(updatedItem);
                Call<CartService> call = RetrofitClient
                        .getInstance()
                        .getServerDetail()
                        .postCartAddList(userAuth, cartServiceUpdate);
                call.enqueue(new Callback<CartService>() {
                    @Override
                    public void onResponse(Call<CartService> call, Response<CartService> response) {
                        if (response.isSuccessful()) {
                        }
                        else if (response.code() == 500) {
                            Toast.makeText(context,"Error updating Cart",Toast.LENGTH_LONG).show();
                        }
                        else if (response.code()== 400){
                            Toast.makeText(context,"known Error in updating Cart:"+response.errorBody(),Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(context,"known Error in updating Cart",Toast.LENGTH_LONG).show();

                        }

                    }
                    @Override
                    public void onFailure(Call<CartService> call, Throwable t) {
                        Toast.makeText(context,"Chack your internet connection Error on Update cart",Toast.LENGTH_LONG).show();
                    }
                });
            }
            if (removedItem.size() > 0) {
                cartServiceUpdate.setCartItem(removedItem);
                Call<CartService> call = RetrofitClient
                        .getInstance()
                        .getServerDetail()
                        .postCartDeleteList(userAuth, cartServiceUpdate);
                call.enqueue(new Callback<CartService>() {
                    @Override
                    public void onResponse(Call<CartService> call, Response<CartService> response) {
                        if (response.isSuccessful()) {
                        } else if (response.code() == 500) {
                            Toast.makeText(context, "Error Delete Cart", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "known Error in Delete Cart", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<CartService> call, Throwable t) {
                        Toast.makeText(context, "Chack your internet connection Error on Delete cart", Toast.LENGTH_LONG).show();

                    }
                });
            }
            tinyDB.remove("removedProducts");
            tinyDB.remove("updatedProducts");
//        }
    }
}
