package lam.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity
{
    static final int REQ_CODE = 1;

    private List<TextView> textViewList_ = new ArrayList<TextView>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.food_content);
        getAllTextViews();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                String foodName = intent.getStringExtra("foodName");
                String foodPrice = intent.getStringExtra("foodPrice");
                super.onActivityResult(requestCode, resultCode, intent);
                addNewElementTextViews(foodName, foodPrice);
            }
            if (resultCode == RESULT_CANCELED)
            {
                // Write your code if there's no result
            }
        }
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        String foodName = intent.getStringExtra("foodName");
        String foodPrice = intent.getStringExtra("foodPrice");
        addNewElementTextViews(foodName, foodPrice);
    }

    public void addItem_btnOnClick(View v)
    {
        Intent intent = new Intent(this, Food_new_item_activity.class);
        this.startActivity(intent);
    }

    private void getAllTextViews()
    {
        ViewGroup layout = (ViewGroup) findViewById(R.id.food_content_textField);
        for (int i = 0; i < layout.getChildCount(); i++)
        {
            View childView = layout.getChildAt(i);
            if(childView instanceof TextView)
            {
                textViewList_.add((TextView)childView);
            }
        }
    }

    private void addNewElementTextViews(String name, String price)
    {
        if(name != null)
        {
            RelativeLayout layout = (RelativeLayout)findViewById(R.id.food_content_textField);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            TextView tv1 = new TextView(this);
            tv1.setId(View.generateViewId());
            tv1.setText(name);
            if(!textViewList_.isEmpty())
            {
                try
                {
                    params1.addRule(RelativeLayout.BELOW, getLastTextViewList().getId());
                }
                catch (Exception e)
                {
                    System.console().printf(e.getMessage());
                }
            }

            TextView tv2 = new TextView(this);
            tv2.setId(View.generateViewId());
            tv2.setText(". -->" + price);
            params2.addRule(RelativeLayout.RIGHT_OF, tv1.getId());
            params2.addRule(RelativeLayout.ALIGN_BOTTOM, tv1.getId());

            layout.addView(tv1, params1);
            layout.addView(tv2, params2);

            int id1 = tv1.getId();
            int id2 = tv2.getId();

            textViewList_.add((TextView)layout.findViewById(id1));
            textViewList_.add((TextView)layout.findViewById(id2));
        }
    }

    private TextView getLastTextViewList()
    {
        // Get the odd textview (containing the food name)
        int size = textViewList_.size() - 2;
        if(size < 0)
        {
            return null;
        }
        else
        {
            return textViewList_.get(size);
        }
    }
}
