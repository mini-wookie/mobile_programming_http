package com.example.xmlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

    }

    public void btnMethod(View v) {
        NetworkThread thread = new NetworkThread();
        thread.start();
    }

    class NetworkThread extends Thread {
        @Override
        public void run() {
            try {
                String site = "http://192.168.35.100:8080/BasicServer/xml.jsp";
                URL url = new URL(site);
                URLConnection conn = url.openConnection();

                InputStream is = conn.getInputStream();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);

                Element root = doc.getDocumentElement();

                NodeList item_list = root.getElementsByTagName("item");

                for (int i = 0; i < item_list.getLength(); i++) {
                    Element item_tag = (Element) item_list.item(i);
                    NodeList data1_list = item_tag.getElementsByTagName("data1");
                    NodeList data2_list = item_tag.getElementsByTagName("data2");
                    NodeList data3_list = item_tag.getElementsByTagName("data3");

                    Element data1_tag = (Element) data1_list.item(0);
                    Element data2_tag = (Element) data2_list.item(0);
                    Element data3_tag = (Element) data3_list.item(0);

                    String data1 = data1_tag.getTextContent();
                    String data2 = data2_tag.getTextContent();
                    String data3 = data3_tag.getTextContent();

                    final int a1 = Integer.parseInt(data1);
                    final double a3 = Double.parseDouble(data3);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.append("학번 : " + a1 + "\n");
                            textView.append("이름 : " + data2 + "\n");
                            textView.append("학점 : " + a3 + "\n\n");
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}