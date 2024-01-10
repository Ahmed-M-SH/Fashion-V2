package com.example.fashion.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.example.fashion.Activity.CartActivity;
import com.example.fashion.Activity.PaymentActivity;
import com.example.fashion.Domain.CartProduct;
import com.example.fashion.Domain.City;
import com.example.fashion.Domain.Currency;
import com.example.fashion.Domain.MakeOreder;
import com.example.fashion.Domain.OrderItem;
import com.example.fashion.Domain.PaymentDetail;
import com.example.fashion.Domain.PaymentType;
import com.example.fashion.Helper.RetrofitClient;
import com.example.fashion.R;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {
        private static final int IMAGE_PICK_REQUEST = 1;
        private ImageView imageView;
        private Button downloadButton;
        private int cityId,currencyId,payment_id;
        private Uri imageUri;

        private String imageUrl = "http://example.com/image.jpg";
        private PaymentDetail paymentDetail;
       private AppCompatSpinner spinnerCity ;
    private AppCompatSpinner spinnerCurrency;
    private RadioGroup payment_group;
    EditText phone_number1,address_payment,phone_number2;
    Button send_btn_final_payment2;
    private View view;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_payment, container, false);

            initView(view);
            sendPaymentDetailsRequest();

            // Load the image using a library like Picasso or Glide
            Glide.with(this).load(imageUrl).into(imageView);

            return view;
        }

        public void initView(View view) {

            phone_number1 = view.findViewById(R.id.phone_number1);
            phone_number2 = view.findViewById(R.id.phone_number2);
            address_payment = view.findViewById(R.id.address_payment);
            send_btn_final_payment2= view.findViewById(R.id.send_btn_final_payment2);
            spinnerCity = view.findViewById(R.id.spinner_city);
            spinnerCurrency = view.findViewById(R.id.spinner_currency);
            imageView = view.findViewById(R.id.imageView);
            downloadButton = view.findViewById(R.id.downloadButton);
            payment_group = view.findViewById(R.id.paynent_group);
            downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownloadDialog(imageUrl);
                }
            });

            send_btn_final_payment2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeOrderPayment();
                }
            });
            spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Retrieve the selected City object
                    City selectedCity = (City) parentView.getItemAtPosition(position);

                    // Access the properties of the selected City
                    cityId = selectedCity.getId();
                    String cityName = selectedCity.getName();

                    // Do something with the selected City
                    // Example: Log the selected City information
//                    Log.d("Spinner", "Selected City ID: " + cityId + ", Name: " + cityName);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing if nothing is selected
                }
            });

            spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // Retrieve the selected Currency object
                    Currency selectedCurrency = (Currency) parentView.getItemAtPosition(position);

                    // Access the properties of the selected Currency
                    currencyId = selectedCurrency.getId();
                    String currencyName = selectedCurrency.getCurrencyName();

                    // Do something with the selected Currency
                    // Example: Log the selected Currency information
//                    Log.d("Spinner", "Selected Currency ID: " + currencyId + ", Name: " + currencyName);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });
        }
        private void showDownloadDialog(String imageUrl) {
            // Check if the permission is granted
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // If not, request the permission
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_REQUEST);
        }
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == IMAGE_PICK_REQUEST && resultCode == PaymentActivity.RESULT_OK) {
                if (data != null) {
                    imageUri = data.getData();
                    if (imageUri != null) {
                        Glide.with(this).load(imageUri).into(imageView);
                    }
                }
            }
        }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String filePath = cursor.getString(column_index);
            cursor.close();
            return filePath;
        }
        return null;
    }
        public  void sendPaymentDetailsRequest(){
            String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
            if (isAdded() && getContext() != null) {
                Call<PaymentDetail> call = RetrofitClient.getInstance().getServerDetail().getPaymentDetail(auth);
                call.enqueue(new Callback<PaymentDetail>() {
                    @Override
                    public void onResponse(Call<PaymentDetail> call, Response<PaymentDetail> response) {
                        if (isAdded() && getContext() != null && !getActivity().isFinishing()) {
                            paymentDetail = response.body();
                            if (response.isSuccessful()){
                                for (PaymentType payment_type : paymentDetail.getPaymentType()) {
                                    RadioButton button = new RadioButton(requireContext());
                                    button.setText(payment_type.getName());
                                    button.setTag(payment_type.getId());
                                    payment_group.addView(button);
                                }
                                ArrayAdapter<City> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, paymentDetail.getCity());
                                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCity.setAdapter(adapter1);
                                ArrayAdapter<Currency> adapter2 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, paymentDetail.getCurrency());
                                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCurrency.setAdapter(adapter2);
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<PaymentDetail> call, Throwable t) {
                    }
                });
            }
        }

        public void makeOrderPayment(){
        RadioButton payButton = view.findViewById(payment_group.getCheckedRadioButtonId());
        payment_id = (int) payButton.getTag();
            String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
            MakeOreder order= new MakeOreder();
            order.setCity(cityId);
            order.setCurrency(currencyId);
            order.setPaymentType(payment_id);
            if (phone_number1.getText().toString().isEmpty()){
                phone_number1.setError("هذا الحقل ضروري");
                return;
            }
            if (phone_number2.getText().toString().isEmpty()){
                order.setCustomerPhone2(" ");
            }
            if (address_payment.getText().toString().isEmpty()){
                address_payment.setError("هذا الحقل ضروري");
                return;
            }
            order.setCustomerPhone(phone_number1.getText().toString());
            order.setAddress(address_payment.getText().toString());
            order.setCustomerPhone2(phone_number2.getText().toString());

            List<CartProduct> orderItems = (List<CartProduct>) requireActivity().getIntent().getSerializableExtra("productItem");
//            List<OrderItem> formattedOrderItems = new ArrayList<>();

//            for (CartProduct cartProduct : orderItems) {
//                OrderItem orderItem = new OrderItem();
//                orderItem.setProduct(cartProduct.getId());  // Set the correct field for product ID
//                orderItem.setQty(cartProduct.getQty());     // Set the correct field for quantity
//                formattedOrderItems.add(orderItem);
//            }
            order.setOrderItems(orderItems);

//            if (imageUri !=null){
//
//                // Convert the List<CartProduct> to a JSON string
//// Convert the List<CartProduct> to a JSON string
//                Gson gson = new Gson();
//                String orderItemsJson = gson.toJson(order.getOrderItems());
//
//// Create a RequestBody for the order items JSON
//                RequestBody orderItemsRequestBody = RequestBody.create(MediaType.parse("application/json"), orderItemsJson);
//
//// Create a MultipartBody.Part for the image file
//
//
//// Create other RequestBody instances for non-file data
//// Create other RequestBody instances for non-file data
//// Create other RequestBody instances for non-file data
//                RequestBody cityRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(order.getCity()));
//                RequestBody currencyRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(order.getCurrency()));
//                RequestBody paymentTypeRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(order.getPaymentType()));
//                RequestBody customerPhoneRequestBody = RequestBody.create(MediaType.parse("text/plain"), order.getCustomerPhone());
//                RequestBody addressRequestBody = RequestBody.create(MediaType.parse("text/plain"), order.getAddress());
//                RequestBody customerPhone2RequestBody = RequestBody.create(MediaType.parse("text/plain"), order.getCustomerPhone2());
//
//// Make the API call
//                Call<MakeOreder> call = RetrofitClient.getInstance().getServerDetail()
//                        .postMakeOrderWithImage(auth, cityRequestBody, currencyRequestBody, paymentTypeRequestBody,
//                                customerPhoneRequestBody, addressRequestBody, customerPhone2RequestBody, orderItemsRequestBody);
//
//                call.enqueue(new Callback<MakeOreder>() {
//                    @Override
//                    public void onResponse(Call<MakeOreder> call, Response<MakeOreder> response) {
//                        if (response.code() == 400) {
//                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
//                            alertDialogBuilder.setTitle("Error");  // Set the title of the dialog
//                            alertDialogBuilder.setMessage(response.errorBody().toString());  // Set the message of the dialog
//
//                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Positive button clicked
//                                    dialog.dismiss();  // Dismiss the dialog if needed
//                                    getActivity().finish();
//                                }
//                            });
//                            AlertDialog alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();
//
//                        }
//                        if (response.isSuccessful()&& response.code() == 201) {
//                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
//                            alertDialogBuilder.setTitle("تم بنجاح");  // Set the title of the dialog
//                            alertDialogBuilder.setMessage("شكرا لتعاملك معنا سيتم التواصل معك في اقرب وقت");  // Set the message of the dialog
//
//                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Positive button clicked
//                                    dialog.dismiss();  // Dismiss the dialog if needed
////                                    getActivity().finish();
//                                }
//                            });
//                            AlertDialog alertDialog = alertDialogBuilder.create();
//                            alertDialog.show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MakeOreder> call, Throwable t) {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
//                        alertDialogBuilder.setTitle("خطاء في الطلب");  // Set the title of the dialog
//                        alertDialogBuilder.setMessage(t.getMessage()+"حدث خطاء غير متوقع يرجاء اعادة المحاولة");  // Set the message of the dialog
//                        Log.i("Error on send Make order", t.getMessage());
//
//                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // Positive button clicked
//                                dialog.dismiss();  // Dismiss the dialog if needed
//                            }
//                        });
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        alertDialog.show();
//                    }
//                });
//            }
//            else {
            Call<MakeOreder> call = RetrofitClient.getInstance().getServerDetail().postMakeOrder(auth, order);
            call.enqueue(new Callback<MakeOreder>() {
                @Override
                public void onResponse(Call<MakeOreder> call, Response<MakeOreder> response) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());

                    if (response.isSuccessful()&& response.code() == 201) {
                        if (imageUri != null) {
                            sendOrderProfit(response.body().getId());
                        }
                        alertDialogBuilder.setTitle("تم بنجاح");  // Set the title of the dialog
                        alertDialogBuilder.setMessage("شكرا لتعاملك معنا سيتم التواصل معك في اقرب وقت");  // Set the message of the dialog
                    }
                    else {
                        alertDialogBuilder.setTitle("حدث خطاء في ارسال البيانات");  // Set the title of the dialog
                        alertDialogBuilder.setMessage("حدث خطاء غير متوقع في ارسال البيانات الرجاء المحاولة لاحقاً");  // Set the message of the dialog
                    }
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Positive button clicked
                            dialog.dismiss();  // Dismiss the dialog if needed
                            getActivity().finish();

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                @Override
                public void onFailure(Call<MakeOreder> call, Throwable t) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireContext());
                    alertDialogBuilder.setTitle("خطاء في الطلب");  // Set the title of the dialog
                    alertDialogBuilder.setMessage("حدث خطاء غير متوقع يرجاء اعادة المحاولة");  // Set the message of the dialog

                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Positive button clicked
                            dialog.dismiss();  // Dismiss the dialog if needed
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });
//            }
        }
private boolean success;
        public boolean sendOrderProfit(int orderId){
            String auth ="token 4ff24a3114344bc978419193eacdbca8316a82c8";
            File imageFile = new File(getRealPathFromURI(imageUri));
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData(
                    "proof_of_payment_image",
                    imageFile.getName(),
                    RequestBody.create(MediaType.parse("image/jpeg"), imageFile)
            );
            Call<MakeOreder> call= RetrofitClient.getInstance().getServerDetail().updateOrderImage(auth,orderId,imagePart);

             success =false;
            call.enqueue(new Callback<MakeOreder>() {
                @Override
                public void onResponse(Call<MakeOreder> call, Response<MakeOreder> response) {
                    if (response.code() == 200)
                        success = true;
                }
                @Override
                public void onFailure(Call<MakeOreder> call, Throwable t) {
                    success = false;
                }
            });
            return success;
        }
    }