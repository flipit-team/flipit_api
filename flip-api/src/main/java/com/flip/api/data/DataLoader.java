package com.flip.api.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.flip.data.entity.Country;
import com.flip.data.entity.State;
import com.flip.data.repository.CountryRepository;
import com.flip.data.repository.StateRepository;
import com.github.fge.jackson.JsonLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Charles on 21/01/2023
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("--------------- Initializing Data --------------");
        if (countryRepository.count() == 0L) {
            loadCountriesAndStates();
        }
    }

    @Transactional
    void loadCountriesAndStates() throws IOException {
        System.out.println("--------------- Populating Country Data --------------");
        URL resource = getClass().getClassLoader().getResource("countries.json");
        if (resource != null) {
            JsonNode countries = JsonLoader.fromURL(resource).get("countries");
            for (JsonNode countryNode : countries) {
                Country country = new Country();
                country.setName(countryNode.get("country").asText());
                country.setAlpha2code(countryNode.get("alpha2Code").asText());
                country.setAlpha3code(countryNode.get("alpha3Code").asText());
                country.setNumberCode(countryNode.get("numberCode").asText());

                JsonNode states = countryNode.get("states");
                Set<State> stateSet = new HashSet<>();
                for (JsonNode stateNode : states) {
                    State state = new State();
                    state.setCountry(country);
                    state.setName(stateNode.asText());
                    stateSet.add(state);
                }
                countryRepository.save(country);
                stateRepository.saveAll(stateSet);
            }
        }
        System.out.println("--------------- Country Data Populated!! --------------");
    }
}
