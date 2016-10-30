package lam.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.util.Xml;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.R.attr.data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class FoodActivity extends AppCompatActivity
{
    static final int REQ_CODE = 1;
    static String filename_ = "foodActivity1.xml";

    private List<TextView> textViewList_ = new ArrayList<TextView>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.food_content);
        getAllTextViews();
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

            String xmlText = writeXml(name, price);

            writeToFile(xmlText);
            //readSimpleFile(filename_);

            readXML();
        }
    }

    private void readSimpleFile(String filename)
    {
        try {
            FileInputStream fileIn=openFileInput(filename);
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String text)
    {
        try {
            // Create a new output file stream
            FileOutputStream fileos = openFileOutput(filename_, Context.MODE_PRIVATE);
            fileos.write(text.getBytes());
            fileos.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
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


    private String writeXml(String name, String price){

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "foodNames");
            //serializer.attribute("", "number", String.valueOf(messages.size()));
            //for (Message msg: messages){
                serializer.startTag("", "foodName");
                //serializer.attribute("", "date", msg.getDate());
                serializer.startTag("", "price");
                serializer.text(price);
                serializer.endTag("", "price");
                /*serializer.startTag("", "url");
                //serializer.text(msg.getLink().toExternalForm());
                serializer.endTag("", "url");
                serializer.startTag("", "body");
                //serializer.text(msg.getDescription());
                serializer.endTag("", "body");*/
                serializer.endTag("", "foodName");
            //}
            serializer.endTag("", "foodNames");
            serializer.endDocument();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readXML()
    {
        //xmlParser xml = new xmlParser(filename_);
        parseXml();
    }

/*
    private StringBuffer readInternalData()
    {
        File file = new File(getFilesDir(), filename_);
        String filename = filename_;
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput (filename);
            InputStreamReader isr = new InputStreamReader (fIn);
            BufferedReader buffreader = new BufferedReader (isr);

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace();
        }

        return datax;
    }*/
public void parseXml(){

    try {
        File inputFile = new File(filename_);

        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :"
                + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("foodNames");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :"
                    + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println("Student roll no : "
                        + eElement.getAttribute("number"));
                System.out.println("foodName : "
                        + eElement
                        .getElementsByTagName("foodName")
                        .item(0)
                        .getTextContent());
                System.out.println("price : "
                        + eElement
                        .getElementsByTagName("price")
                        .item(0)
                        .getTextContent());
                    /*System.out.println("Nick Name : "
                            + eElement
                            .getElementsByTagName("nickname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Marks : "
                            + eElement
                            .getElementsByTagName("marks")
                            .item(0)
                            .getTextContent());*/
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
