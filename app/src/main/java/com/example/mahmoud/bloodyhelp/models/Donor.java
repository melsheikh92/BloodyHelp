
package com.example.mahmoud.bloodyhelp.models;

import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class Donor implements Parcelable
{

    private Object createdAt;
    private Integer id;
    private Integer cityId;
    private String description;
    private String email;
    private String name;
    private String phone;
    private Integer typeId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<Donor> CREATOR = new Creator<Donor>() {

        public Donor createFromParcel(Parcel in) {
            Donor instance = new Donor();
            instance.createdAt = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.cityId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.description = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.typeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
            return instance;
        }

        public Donor[] newArray(int size) {
            return (new Donor[size]);
        }

    }
    ;
    /**
     * No args constructor for use in serialization
     * 
     */
    public Donor() {
    }

    /**
     * 
     * @param id
     * @param phone
     * @param email
     * @param cityId
     * @param description
     * @param createdAt
     * @param name
     * @param typeId
     */
    public Donor(Object createdAt, Integer id, Integer cityId, String description, String email, String name, String phone, Integer typeId) {
        super();
        this.createdAt = createdAt;
        this.id = id;
        this.cityId = cityId;
        this.description = description;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.typeId = typeId;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(createdAt);
        dest.writeValue(id);
        dest.writeValue(cityId);
        dest.writeValue(description);
        dest.writeValue(email);
        dest.writeValue(name);
        dest.writeValue(phone);
        dest.writeValue(typeId);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
