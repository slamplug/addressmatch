package com.firstutility.poc.addressmatch;

import com.firstutility.poc.addressmatch.domain.Address;
import com.firstutility.poc.addressmatch.service.AddressMatchService;
import com.firstutility.poc.addressmatch.service.AddressWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootAddressMatchApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(SpringBootAddressMatchApplication.class);

    @Autowired
    private AddressWebService addressWebService;

    @Autowired
    private AddressMatchService addressMatchService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("run start");

        final String fileName = Paths.get(".").toAbsolutePath().normalize().toString() +
                "/src/main/resources/one-shot-failed-adresses.txt";
        logger.info("file :" + fileName);

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                String[] addressParts = line.split(",");
                Address requestAddress = new Address() {{
                    setAddressLine1(addressParts[0]);
                    setAddressLine2(addressParts[1]);
                    setAddressLine3(addressParts[2]);
                    setAddressLine4(addressParts[3]);
                    setAddressLine5(addressParts[4]);
                    setPostcode(addressParts[5]);
                }};
                try {
                    addressMatchService.getMatchedAddress(requestAddress,
                            addressWebService.getAddressesForPostCode(requestAddress.getPostcode()).getBody());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void matchAddress(final String addressLine) {
        String[] addressParts = addressLine.split(",");
        Address requestAddress = new Address() {{
            setAddressLine1(addressParts[0]);
            setAddressLine2(addressParts[1]);
            setAddressLine3(addressParts[2]);
            setAddressLine4(addressParts[3]);
            setAddressLine5(addressParts[4]);
            setPostcode(addressParts[5]);
        }};

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootAddressMatchApplication.class, args);
    }

}
