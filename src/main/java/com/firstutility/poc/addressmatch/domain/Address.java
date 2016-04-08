package com.firstutility.poc.addressmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String postcode;
    private List<String> parts = new ArrayList<>();

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        addParts(this.addressLine1);
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        addParts(this.addressLine2);
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        addParts(this.addressLine3);
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
        addParts(this.addressLine4);
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
        addParts(this.addressLine5);
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
        addPart(StringUtils.trimAllWhitespace(this.postcode));
    }

    private void addParts(final String toadd) {
        if (toadd != null && !toadd.equals("-")) {
            for (String part : splitStringBySpace(toadd)) {
                addPart(part);
            }
        }
    }

    private void addPart(final String toadd) {
        if (!parts.contains(toadd.toUpperCase())) {
            parts.add(toadd.toUpperCase());
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", addressLine4='" + addressLine4 + '\'' +
                ", addressLine5='" + addressLine5 + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }


    public String getAlphaSortedString() {
        Collections.sort(parts, ALPHABETICAL_ORDER);
        return String.join(" ", parts);
    }

    private String[] splitStringBySpace(final String in) {
        return in.split(" ");
    }

    private static Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
        public int compare(String str1, String str2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
            if (res == 0) {
                res = str1.compareTo(str2);
            }
            return res;
        }
    };
}
