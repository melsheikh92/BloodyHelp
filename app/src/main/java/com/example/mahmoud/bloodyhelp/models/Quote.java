
package com.example.mahmoud.bloodyhelp.models;

import java.util.HashMap;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

public class Quote implements Parcelable {

    private String quote;
    private String author;
    private String cat;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<Quote> CREATOR = new Creator<Quote>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Quote createFromParcel(Parcel in) {
            Quote instance = new Quote();
            instance.quote = ((String) in.readValue((String.class.getClassLoader())));
            instance.author = ((String) in.readValue((String.class.getClassLoader())));
            instance.cat = ((String) in.readValue((String.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Quote[] newArray(int size) {
            return (new Quote[size]);
        }

    };

    /**
     * No args constructor for use in serialization
     */
    public Quote() {
    }

    public Quote(String quote, String author, String cat) {
        super();
        this.quote = quote;
        this.author = author;
        this.cat = cat;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quote);
        dest.writeValue(author);
        dest.writeValue(cat);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
