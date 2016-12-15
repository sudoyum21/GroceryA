package lam.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Lam on 10/14/2016.
 */


public class Food_new_item_activity  extends AppCompatActivity
{

    static final int REQ_CODE = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_new_item);

        EditText editTextName = (EditText) findViewById(R.id.food_name_input);
        editTextName.addTextChangedListener(new GenericTextWatcher(editTextName));

        EditText editTextPrice = (EditText) findViewById(R.id.food_price_input);
        editTextPrice.addTextChangedListener(new GenericTextWatcher(editTextPrice));

        TextView textViewName = (TextView) findViewById(R.id.food_name);

        TextView textViewPrice = (TextView) findViewById(R.id.food_price);
    }


    private class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            switch(view.getId()){
                case R.id.food_name_input:
                    TextView textViewName = (TextView) findViewById(R.id.food_name);
                    textViewName.setText(charSequence.toString());
                    break;
                case R.id.food_price_input:
                    TextView textViewPrice = (TextView) findViewById(R.id.food_price);
                    textViewPrice.setText(charSequence.toString());
                    break;
            }
        }

        public void afterTextChanged(Editable editable)
        {
        }
    }


    public void confirm_btn_click(View v)
    {
        TextView textViewName = (TextView) findViewById(R.id.food_name);
        String foodName = textViewName.getText().toString();
        TextView textViewPrice = (TextView) findViewById(R.id.food_price);
        String foodPrice = textViewPrice.getText().toString();

        Intent intent = new Intent(Food_new_item_activity.this, FoodActivity.class);
        intent.putExtra("foodName", foodName);
        intent.putExtra("foodPrice", foodPrice);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, intent);
        startActivityForResult(intent, REQ_CODE);
        this.finish();
    }

    public void cancel_btn_click(View v)
    {

    }
}
