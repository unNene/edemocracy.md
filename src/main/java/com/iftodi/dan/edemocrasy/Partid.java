package com.iftodi.dan.edemocrasy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daniftodi on 12/9/15.
 */
public class Partid {
    String name;
    ArrayList<Deputat> deputati;

    String shortTitle;
    String title;
    String url;
    String aboutUrl;
    HashMap<String,Integer> fieldsMap;

    //default required fields fields
    String requiredFields[] = {Deputat.NAME,Deputat.ADDRESS};
    public Partid(String title,String url)
    {
        this.url = url;
        this.title = title;
        fieldsMap = new HashMap<String, Integer>();
        fieldsMap.put(Deputat.NAME, 1);
        fieldsMap.put(Deputat.ADDRESS, 2);
        fieldsMap.put(Deputat.BORN_YEAR,3);
    }
    public Partid(String url)
    {
        this(url,null);
    }
    public Partid()
    {
        this(null,null);
    }
    public ArrayList<Deputat> getDeputati()
    {
        return this.getDeputati(null);
    }
    public ArrayList<Deputat> getDeputati(String[] requiredFields)
    {
        if(requiredFields == null)
            requiredFields = this.requiredFields;
        deputati = new ArrayList<Deputat>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements selection = doc.select(".mps");
        for(Element element : selection)
        {
            Element table = element.getElementsByTag("table").get(0);
            Elements rows = table.getElementsByTag("tr");

            for(Element row : rows)
            {
                int colCount = 0;
                Elements cols = row.getElementsByTag("td");
                Deputat deputat = new Deputat();
                boolean printed = false;
                for(Element col : cols)
                {
                    for(String requiredField : requiredFields)
                    {
                        if(fieldsMap.containsKey(requiredField) && fieldsMap.get(requiredField) == colCount)
                        {
                            if(requiredField.equals("")) continue;
                            Method setter;
                            try {
                                //setterii au primul caracter Uppercase dupa "set"
                                Character firstLetter = requiredField.charAt(0);
                                firstLetter = Character.toUpperCase(firstLetter);

                                setter = deputat.getClass().getMethod("set" + firstLetter + requiredField.substring(1,requiredField.length()), String.class);
                                setter.invoke(deputat, col.text());
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            printed = true;
                        }
                    }
                    colCount++;
                }
                if(printed)
                    deputati.add(deputat);
            }

        }

        return deputati;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeputati(ArrayList<Deputat> deputati) {
        this.deputati = deputati;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAboutUrl() {
        return aboutUrl;
    }

    public void setAboutUrl(String aboutUrl) {
        this.aboutUrl = aboutUrl;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}
