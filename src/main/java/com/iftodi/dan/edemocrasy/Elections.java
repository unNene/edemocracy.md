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
import java.util.Map;

/**
 * Created by daniftodi on 12/12/15.
 */
public class Elections {
    ArrayList<Partid> parties;

    String url;
    HashMap<String,String> mapRequiredLinks;
    public Elections(String url)
    {
        this.url = url;
        mapRequiredLinks = new HashMap<>();
        mapRequiredLinks.put("url","lista candidaţilor");
        mapRequiredLinks.put("aboutUrl","despre partid");
    }
    public ArrayList<Partid> getParties()
    {
        parties = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements selection = doc.select(".table");

        for(Element item : selection )
        {
            Elements rows = item.getElementsByTag("tr");

            for(Element row : rows)
            {
                Partid partid = new Partid();
                Elements cols = row.getElementsByTag("td");
                int colCount = 0;
                for(Element col : cols)
                {
                    if(colCount == 2) //pe pozitia 2 e denumirea partidului
                    {
                        String titles[] = col.html().split("<br />");
                        String title = titles[0].trim();

                        Document htmlPartid = Jsoup.parse(title);

                        Elements shortNameList = htmlPartid.getElementsByTag("span");
                        Element shortName = shortNameList.first();

                        String shortNameString = shortName.text();
                        String partidTitle = htmlPartid.text().replace(shortNameString,"");
                        partid.setTitle(partidTitle);
                        partid.setShortTitle(shortNameString.replaceAll("\\)|\\(",""));

                    }


                    //sub items ( denumirea si linkurile sunt in aceeasi celula separate prin <br />
                    Elements links = col.getElementsByTag("a");
                    int linksCount;
                    for(Element link : links)
                    {
                        for(Map.Entry<String,String> requiredLink : mapRequiredLinks.entrySet())
                        {
                            if(requiredLink.getValue().equals(link.text()))
                            {
                                Method setter;
                                try {
                                    //setterii au primul caracter Uppercase dupa "set"
                                    Character firstLetter = requiredLink.getKey().charAt(0);
                                    firstLetter = Character.toUpperCase(firstLetter);

                                    setter = partid.getClass().getDeclaredMethod("set" + firstLetter +
                                            requiredLink.getKey().substring(1,requiredLink.getKey().length()),String.class);
                                    setter.invoke(partid,this.url+link.attr("href"));

                                } catch (NoSuchMethodException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    colCount++;
                }
                parties.add(partid);
            }

        }

        return parties;
    }
    public Partid getPartidByShortTitle(String shortTitle)
    {
        if(parties == null) parties = getParties();
        for(Partid partid : parties)
        {
            if(partid.getShortTitle().equals(shortTitle))
            {
                return partid;
            }
        }

        return null;
    }
}
