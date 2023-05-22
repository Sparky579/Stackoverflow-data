package com.example.datafetcher.service;

import com.example.datafetcher.model.APIs;
import com.example.datafetcher.repository.APIsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Service
public class APIService {

    static APIsRepository apisRepository;

    @Autowired
    public APIService(APIsRepository apisRepository0) {
        if (apisRepository == null) {
            if (apisRepository0 == null)
                throw new RuntimeException("apisRepository0 is null");
            apisRepository = apisRepository0;
        }
    }

    public void logAPI(String apiName) {
        APIs api = apisRepository.findByName(apiName);
        if (api == null) {
            api = new APIs(apiName);
        }
        api.incrementCount();
        apisRepository.save(api);
    }

    private static final Pattern API_PATTERN = Pattern.compile("(?<![a-zA-Z\\.])java\\.\\w+(?:\\.\\w+)*(?![a-zA-Z\\.\\/=])");

    public void extractAndCountAPIs(String jsonString) {
        Matcher matcher = API_PATTERN.matcher(jsonString);
        while (matcher.find()) {
            String apiName = matcher.group();
            logAPI(apiName);
        }
    }

    public Map<String, Integer> getAPIsCountMap() {
        List<APIs> apisList = apisRepository.findAll();
        Map<String, Integer> apisCountMap = new HashMap<>();

        for (APIs api : apisList) {
            apisCountMap.put(api.getName(), api.getCount());
        }
        return apisCountMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    }
}
