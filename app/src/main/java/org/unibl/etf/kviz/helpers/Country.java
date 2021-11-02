package org.unibl.etf.kviz.helpers;

public class Country {
    public String countryName;
    public String capital;
    String city1;
    String city2;
    String city3;

    public Country(String countryName, String capital, String city1, String city2, String city3){
        setCities(countryName, capital,city1,city2,city3);
    }
    public void setCities(String countryName, String capital, String city1, String city2, String city3 ){
        this.countryName=countryName;
        this.capital=capital;
        this.city1=city1;
        this.city2=city2;
        this.city3=city3;
    }
}
