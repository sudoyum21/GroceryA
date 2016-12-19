package lam.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
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
import android.view.KeyEvent;
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
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;

public class FoodActivity extends AppCompatActivity
{
    static final int REQ_CODE = 1;
    static private String filename_ = "foodActivity1";

    private List<TextView> textViewList_ = new ArrayList<TextView>();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.food_content);

        String categoryText = intent.getStringExtra("category");

        filename_ += categoryText + ".txt";

        readXML();

        getAllTextViews();
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        deleteAllTextView();
        String foodName = intent.getStringExtra("foodName");
        String foodPrice = intent.getStringExtra("foodPrice");
        String categoryText = intent.getStringExtra("category");
        if(!filename_.contains(categoryText)) {
            filename_ += categoryText + ".txt";
        }
        readXML();
        getAllTextViews();
        addNewElementTextViews(foodName, foodPrice, true);
    }

    public void addItem_btnOnClick(View v)
    {
        Intent intent = new Intent(this, Food_new_item_activity.class);
        this.startActivity(intent);
    }

    public void clearData_btnonClick(View v)
    {
        deleteXML(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent(FoodActivity.this, MainActivity.class);
        moveTaskToBack(true);
        startActivity(intent);
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

    private void deleteAllTextView()
    {
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.food_content_textField);
        layout.removeAllViews();
    }

    private void addNewElementTextViews(String name, String price, boolean writingToFile)
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

            if(writingToFile) {
                writeToFile(this, name, price);
                readSimpleFile(filename_);
            }
            //
            //readInternalData();

            //readXML();
        }
    }

    private String readSimpleFile(String filename)
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
            return s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteXML(Context context) {
        String filePath = context.getFilesDir().getPath().toString() + filename_;
        File f = new File(filePath);
        if(f.exists()) {
            f.delete();
            Toast.makeText(getBaseContext(), "Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeToFile(Context context, String name, String price)
    {
        try {
                //File f = new File(filename_);
                String text = null;
                String filePath = context.getFilesDir().getPath().toString() + filename_;
                File f = new File(filePath);
                if(!f.exists()) {
                    f.createNewFile();
                    text = writeXmlFirstTime(name, price);
                }
                else
                {
                    text = writeXmlElement(name, price);
                }
                // Create a new output file stream
                FileOutputStream fileos = openFileOutput(filename_, Context.MODE_PRIVATE);
                if(!(text == null)) {
                    try {
                        fileos.write(text.getBytes());
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    Log.w("[WARNING]:","writing new xml element returns null");
                }
                fileos.close();
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String writeXmlElement(String name, String price) {
        String text = null;

        FileInputStream inputFile = null;
        try {
            inputFile = openFileInput(filename_);
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("foodNames");
            int sizeOfNlist = nList.getLength()-1;
            Element foodNamesEle = (Element)doc.getElementsByTagName("foodNames").item(sizeOfNlist);

            Element ele = doc.createElement("foodName");
            ele.setTextContent(name);
            ele.setAttribute("price", price);

            foodNamesEle.appendChild(ele);
            text = convertDocumentToString(doc);
            System.out.println(text);
            inputFile.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private String writeXmlFirstTime(String name, String price){

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        String text = null;
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "foodNames");
            serializer.startTag("", "foodName");
            serializer.attribute("","price",price);
            serializer.text(name);
            serializer.endTag("", "foodName");
            serializer.endTag("", "foodNames");
            serializer.endDocument();
            text = writer.toString();
            serializer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return text;
    }

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
    }

    private void readXML(){

        try {
            Log.d("debug xml", readInternalData().toString());
            FileInputStream inputFile = openFileInput(filename_);
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
                    int sizeOfElementParent = eElement.getElementsByTagName("foodName").getLength();
                    for(int i = 0; i < sizeOfElementParent; ++i) {
                        String foodName = eElement.getChildNodes().item(i).getTextContent();
                        String priceValue = eElement.getChildNodes().item(i).getAttributes().getNamedItem("price").getTextContent();
                        System.out.println("Foodname name is : " + foodName);
                        System.out.println("price is : " + priceValue);
                        addNewElementTextViews(foodName, priceValue, false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertDocumentToString(Document doc) {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            // below code to remove XML declaration
            // transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
    }
}
