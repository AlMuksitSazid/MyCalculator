package com.mycalculator;

public class Model {
    String first, second, sum;

    public Model(String first, String second, String sum) {
        this.first = first;
        this.second = second;
        this.sum = sum;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Model(String email, String password) {
    }
}
