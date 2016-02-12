package com.olamie.myapplicationtest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 ***/
public class MainActivity extends ActionBarActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean haswhippedCream = whippedCream.isChecked();

        CheckBox whippedChoco = (CheckBox) findViewById(R.id.choco_checkbox);
        boolean hasChoco = whippedChoco.isChecked();

        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();
        //Log.v("MainActivity", "Check value here: " + value);

        int price = calculatePrice(haswhippedCream, hasChoco);
        String priceMessage = createOrderSummary(name, price, haswhippedCream, hasChoco);
        ///This is to display display message method
        // displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "ollyusoft@gmail.com");//
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Course - I do native now" + name);//subject of the email address
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);//body  of the mail
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculate order price
     * q 3
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChoco) {
        int basePrice = 5;

        if(addWhippedCream){
            basePrice = basePrice + 1;
        }
        if(addChoco){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * Summary of all order
     *
     * Comment out order summary that shows within the app space
     */
    private String createOrderSummary(String name, int price, boolean addwhippedCream, boolean addChoco) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addwhippedCream;
        priceMessage += "\nAdd whipped chocolate? " + addChoco;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nAmount due: " + price;
        priceMessage = priceMessage + "\nThank You";
        return priceMessage;
    }

    /****
     * This method increment the quantity need by the customer
     */
    public void increment(View view) {
        //condition to state that value shouldn't be greater than 100
        if(quantity == 100){
            //show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            //exit method early because there is nothing left to do
            return;//return value
        }
        //increase after the if condition
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /***
     * This method decreases the quantity of goods needed by customer
     ***/
    public void decrement(View view) {
        //message
        Toast.makeText(this, "You cannot add more than 100 coffess", Toast.LENGTH_SHORT).show();
        //condition to state that value shouldn't be lesser than 1
        if(quantity ==1){
            return;//return value
        }
        //decrease after the if condition
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.qty_txtv);
        quantityTextView.setText(" " + number);
    }

    private void displayPrice(String amount) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_txtv);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(amount));
    }

    /*

    Comment out display message cos it is not needed again

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_txtv);
        priceTextView.setText("" + message);
    }*/
}