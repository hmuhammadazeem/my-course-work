package com.muhammadazeem.redcare;

import java.util.HashMap;
import java.util.Map;

public class BloodType {

    public String bloodGroup;
    public String dateOfEntry;
    public String status;



    public BloodType() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public BloodType(String bloodGroup, String dateOfEntry) {
        this.bloodGroup = bloodGroup;
        this.dateOfEntry = dateOfEntry;
        this.status = "pending";
    }

    public HashMap<String, String> getMap() {
        HashMap<String, String> result = new HashMap<>();
        result.put("bloodGroup", bloodGroup);
        result.put("dateOfEntry", dateOfEntry);
        result.put("status", status);
        return result;
    }


}
