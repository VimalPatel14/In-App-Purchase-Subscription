package com.vimal.inapppurchase.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.vimal.inapppurchase.R;
import com.vimal.inapppurchase.helpers.IAPHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubscriptionActivity extends AppCompatActivity implements IAPHelper.IAPHelperListener {

    TextView message;
    Button inapp;
    private String TAG = MainActivity.class.getSimpleName();
    IAPHelper iapHelper;
    List<SkuDetails> skuDetailsHashMap = new ArrayList<>();
    final String TEST = "android.test.purchased"; //This id can be used for testing purpose
    final String weekly = "Plan_Product_weekly";
    final String monthly = "Plan_Product_monthly";
    final String yearly = "Plan_Product_yearly";
    private List<String> skuList = Arrays.asList(TEST, weekly, monthly, yearly);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        iapHelper = new IAPHelper(this, SubscriptionActivity.this, skuList, BillingClient.SkuType.SUBS);

        message = findViewById(R.id.message);
        inapp = findViewById(R.id.inapp);

        inapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launch(weekly);
            }
        });
        Log.e("vml", iapHelper.getIsLyPro(SubscriptionActivity.this) + " IsLyPro");

    }

    @Override
    public void onPurchasehistoryResponse(List<Purchase> purchasedItems) {
        Log.e("vml", purchasedItems + " purchasedItems");
        if (purchasedItems != null && purchasedItems.size() > 0) {
            for (Purchase purchase : purchasedItems) {
                //Update UI and backend according to purchased items if required
                // Like in this project I am updating UI for purchased items
                String sku = purchase.getSku();
                switch (sku) {
                    case TEST:
                        if (TEST.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                            Log.e("vml", purchase.isAcknowledged() + " isAcknowledged");
//                            Log.e("vml", purchase.getPackageName() + " getPackageName");
//                            Log.e("vml", purchase.getPurchaseTime() + " getPurchaseTime");
//                            Log.e("vml", purchase.isAutoRenewing() + " isAutoRenewing");
//                            Log.e("vml", purchase.getPurchaseToken() + " getPurchaseToken");
//                            Log.e("vml", purchase.getDeveloperPayload() + " getDeveloperPayload");
//                            Log.e("vml", purchase.getOriginalJson() + " json");
                            message.setText("Your Subscription is  " + purchase.getPackageName());
                            message.setVisibility(View.VISIBLE);
                            inapp.setVisibility(View.GONE);

                            iapHelper.setIsLyPro(SubscriptionActivity.this, "true");

                        } else if (TEST.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
                            message.setText("Your Subscription is Pending");
                            message.setVisibility(View.VISIBLE);
                            inapp.setVisibility(View.GONE);
                        }
                        //if purchase is unknown
                        else if (TEST.equals(purchase.getSku()) && purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                            message.setVisibility(View.VISIBLE);
                            inapp.setVisibility(View.GONE);
                            message.setText("Your Subscription Status : Not Purchased");
                        }
                        break;
                    case weekly:
                        break;
                    case monthly:
                        break;
                    case yearly:
                        break;
                }
            }
            if (iapHelper.getIsLyPro(SubscriptionActivity.this).equals("true")) {
                message.setVisibility(View.VISIBLE);
                inapp.setVisibility(View.GONE);
            }
        } else {
            message.setVisibility(View.VISIBLE);
            inapp.setVisibility(View.VISIBLE);
        }
    }

    private void launch(String sku) {
        if (!skuDetailsHashMap.isEmpty())
            for (SkuDetails skuDetailsv : skuDetailsHashMap) {
                if (skuDetailsv.getSku().equals(sku)) {
                    Log.e("vml", skuDetailsv.getSku() + " " + skuDetailsv.getPrice());
                    iapHelper.launchBillingFLow(skuDetailsv);
                }
            }
    }


    @Override
    public void onSkuListResponse(List<SkuDetails> skuDetails) {
        skuDetailsHashMap = skuDetails;
        Log.e("vml", skuDetailsHashMap + " skuDetailsHashMap");
        for (SkuDetails skuDetailsv : skuDetails) {
            String sku = skuDetailsv.getSku();
            String price = skuDetailsv.getPrice();
            Log.e("vml", sku + " sku");
            Log.e("vml", price + " price");
            if (sku.equals(weekly)) {
                message.setText("\n " + skuDetailsv.getTitle() + "\n " + skuDetailsv.getPrice());
            } else if (sku.equals(monthly)) {
                message.setText("\n " + skuDetailsv.getTitle() + "\n " + skuDetailsv.getPrice());
            } else if (sku.equals(yearly)) {
                message.setText("\n " + skuDetailsv.getTitle() + "\n " + skuDetailsv.getPrice());
            }

        }
    }

    @Override
    public void onPurchaseCompleted(Purchase purchase) {
        Toast.makeText(getApplicationContext(), "Purchase Successful", Toast.LENGTH_SHORT).show();
        Log.e("vml", purchase + " onPurchaseCompleted");
        updatePurchase(purchase);
    }

    private void updatePurchase(Purchase purchase) {
        String sku = purchase.getSku();
        Log.e("vml", sku + " updatePurchase");
        switch (sku) {
            case TEST:
                message.setText("Your Subscription is  " + purchase.getPurchaseToken());
                this.recreate();
                break;
            case weekly:
                message.setText("Your Subscription is  " + purchase.getPurchaseToken());
                this.recreate();
                break;
            case monthly:
                message.setText("Your Subscription is  " + purchase.getPurchaseToken());
                this.recreate();
                break;
            case yearly:
                message.setText("Your Subscription is  " + purchase.getPurchaseToken());
                this.recreate();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iapHelper != null)
            iapHelper.endConnection();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}