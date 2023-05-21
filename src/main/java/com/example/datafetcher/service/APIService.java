package com.example.datafetcher.service;

import com.example.datafetcher.model.APIs;
import com.example.datafetcher.repository.APIsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class APIService {

    static APIsRepository apisRepository;

    public APIService() {
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

        return apisCountMap;
    }
}
