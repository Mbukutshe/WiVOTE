package com.example.mczondi.wivote;
/**
 * Created by Wiseman on 2016-05-19.
 */
public class Salons {
    private String salonName;
    private double number;

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public Salons(String salonName, double number)
    {
        this.setSalonName(salonName);
        this.setNumber(number);
    }
}
