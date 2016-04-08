package com.firstutility.poc.addressmatch.service;

import com.firstutility.poc.addressmatch.domain.Address;
import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressMatchService {

    private Logger logger = LoggerFactory.getLogger(AddressMatchService.class);

    public Address getMatchedAddress(final Address requestAddress, final Address[] addresses) {
        logger.info("get best match addeess from " +
                addresses.length + " possibles");

        Address bestMatchAddress = null;
        float bestMatchScore = 0;

        logger.info("REQUEST --> " + requestAddress.toString());

        for (Address address : addresses) {
            // if exact match on postCode and addressLine1 use that
            if (address.getAddressLine1().equalsIgnoreCase(requestAddress.getAddressLine1())) {
                logger.info("MATCH (EXACT) ---> " + address.toString());
                return address;
            }

            // get
            final float matchScore = calculateApacheLuceneLevensteinDistance(
                    requestAddress.getAlphaSortedString(),
                    address.getAlphaSortedString());
            if (matchScore > bestMatchScore) {
                bestMatchScore = matchScore;
                bestMatchAddress = address;
            }
        }

        logger.info("MATCH (NEAR) ---> " + bestMatchAddress.toString());
        return bestMatchAddress;
    }

    private static float calculateApacheLuceneLevensteinDistance(String s1, String s2) {
        return Math.abs(new LuceneLevenshteinDistance().getDistance(s1.toLowerCase(), s2.toLowerCase())) * 100;
    }
}
