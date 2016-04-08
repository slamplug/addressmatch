package com.firstutility.poc.addressmatch.service;

import com.firstutility.poc.addressmatch.domain.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AddressWebService {

    private Logger logger = LoggerFactory.getLogger(AddressWebService.class);

    @Autowired
    private RestTemplate apiKeyRestTemplate;

    public ResponseEntity<Address[]> getAddressesForPostCode(final String postCode) throws URISyntaxException {
        final String trimmedPostCode = StringUtils.trimAllWhitespace(postCode);
        logger.info("fetching addresses for postCode: " + trimmedPostCode);

        return apiKeyRestTemplate.getForEntity(getUrl(postCode), Address[].class);
    }

    private URI getUrl(final String postCode) throws URISyntaxException {
        return new URI("https://www.secure.first-utility.com/address-web/address/v2?postcode=" +
                postCode.replaceAll(" ", "").toUpperCase());
    }
}
