package com.iftodi.dan.edemocrasy;
/**
 * Created by daniftodi on 12/9/15.
 */
public class Deputat {
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String BORN_YEAR = "bornYear";
    public static final String DESCRIPTION = "description";
    String name;
    String address;
    String bornYear;
    String description;
    public Deputat()
    {
        this(null,null,null,null);
    }
    public Deputat(String name, String address,String bornYear,String description)
    {
        this.name = name;
        this.address = address;
        this.bornYear = bornYear;
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBornYear() {
        return bornYear;
    }

    public void setBornYear(String bornYear) {
        this.bornYear = bornYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
