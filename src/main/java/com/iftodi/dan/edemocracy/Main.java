package com.iftodi.dan.edemocracy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daniftodi on 12/9/15.
 */
public class Main {

    static String url = "http://www.e-democracy.md/elections/parliamentary/2014/opponents/";
    public static void main(String [] args)
    {
        //2014 elections URL

        Elections e2014 = new Elections(url);

        Partid pldm = e2014.getPartidByShortTitle("PLDM");

        if(pldm != null)
        {
            System.out.println(pldm.getTitle());

            for(Deputat deputat : pldm.getDeputati())
            {
                System.out.println(deputat.getName());
            }
        }
        else
        {
            System.out.println("Partid not found");
        }

        saveFile("deputati2.txt");

    }
    public static void saveFile(String fileName)
    {
        ArrayList<Partid> parties;
        HashMap<String,Integer> fieldsMap = new HashMap<String,Integer>();
        ArrayList<Deputat> deputati;

        Elections e2014 = new Elections(url);
        parties = e2014.getParties();

        String requiredFields[] = {Deputat.NAME,Deputat.ADDRESS};

        try {
            PrintWriter file = new PrintWriter(new File(fileName));

            for(Partid partid : parties)
            {
                deputati = partid.getDeputati(requiredFields);
                file.println(partid.getShortTitle());
                for(Deputat deputat : deputati)
                {
                    file.print(deputat.getName()+ ",");
                    //System.out.print(deputat.getName()+ ",");
                }
                file.println();
                file.println();
            }

            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
