package com.tty.twsearch.pojo;

import java.util.Arrays;
import java.util.Date;
import org.springframework.stereotype.Component;


/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:35
 */
@Component
public class TwitterData {

    private Date twDate;
    private String twString;
    private String[] splitedString;

    public Date getTwDate() {
        return twDate;
    }

    public void setTwDate(Date twDate) {
        this.twDate = twDate;
    }

    public String getTwString() {
        return twString;
    }

    public void setTwString(String twString) {
        this.twString = twString;
    }

    public String[] getSplitedString() {
        return splitedString;
    }

    public void setSplitedString() {
        this.splitedString = twString.split(" ");
    }

    @Override
    public String toString() {
        return "TwitterData{" +
                "twDate=" + twDate +
                ", twString='" + twString + '\'' +
                ", splitedString=" + Arrays.toString(splitedString) +
                '}';
    }
}
