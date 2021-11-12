package org.unibl.etf.kviz.helpers;

public class Country {
    public String countryName;
    public String capital;
    public String city1;
    public String city2;
    public String city3;
    public String capitalCOA;
    public String domain;

    public Country(String countryName, String capital, String city1, String city2, String city3,String capitalCOA, String domain){
        setCities(countryName, capital,city1,city2,city3, capitalCOA, domain);
    }
    public void setCities(String countryName, String capital, String city1, String city2, String city3, String capitalCOA, String domain){
        this.countryName=countryName;
        this.capital=capital;
        this.city1=city1;
        this.city2=city2;
        this.city3=city3;
        this.capitalCOA=capitalCOA;
        this.domain=domain;
    }
}
