package lam.myapplication;

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

public class Food_new_item_activity  extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_new_item);


        EditText editTextName = (EditText) findViewById(R.id.food_name_input);
        editTextName.addTextChangedListener(new GenericTextWatcher(editTextName));

        EditText editTextPrice = (EditText) findViewById(R.id.food_price_input);
        editTextPrice.addTextChangedListener(new GenericTextWatcher(editTextPrice));

        TextView textViewName = (TextView) findViewById(R.id.food_name);
        //textViewName.addTextChangedListener(new GenericTextWatcher(editTextName));

        TextView textViewPrice = (TextView) findViewById(R.id.food_price);
        //textViewPrice.addTextChangedListener(new GenericTextWatcher(editTextPrice));
    }


    private class GenericTextWatcher implements TextWatcher {

        private View view;
        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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

        public void afterTextChanged(Editable editable) {
        }
    }


    public void confirm_btn_click(View v)
    {

    }

    public void cancel_btn_click(View v)
    {

    }
}
