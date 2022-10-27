package com.muhammadazeem.redcare;

import java.util.HashMap;
import java.util.Map;

public class DonorType {

    public String name;
    public String bloodGroup;
    public String address;
    public String contact;
    public String gender;


    public DonorType() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public DonorType(String name, String group,
                     String address, String contact, String gender) {
        this.name = name;
        this.bloodGroup = group;
        this.address = address;
        this.contact = contact;
        this.gender = gender;
    }

    public HashMap<String, String> getMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("name", name);
        result.put("bloodGroup", bloodGroup);
        result.put("contact", contact);
        result.put("address", address);
        result.put("gender", gender);

        return result;
    }


}
