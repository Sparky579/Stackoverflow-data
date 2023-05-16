package com.example.datafetcher.service;
import com.example.datafetcher.model.Owner;
import com.example.datafetcher.model.StackoverflowData;
import com.example.datafetcher.model.StackoverflowResponse;
import com.example.datafetcher.repository.OwnerRepository;
import com.example.datafetcher.repository.StackoverflowDataRepository;
import com.example.datafetcher.repository.StackoverflowResponseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class StackoverflowService {

    private static final String STACKOVERFLOW_API_URL = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged=java&site=stackoverflow";

    @Autowired
    private StackoverflowDataRepository stackoverflowDataRepository;
    @Autowired
    private StackoverflowResponseRepository stackoverflowResponseRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    String toURL(String tag, String sort, String order, String site) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site;
    }

    String toURL(String tag, String sort, String order, String site, int page, int pageSize) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site + "&page=" + page + "&pagesize=" + pageSize;
    }

    public String fetchJavaQuestionURL(String url, boolean save) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Encoding", "gzip");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.hasBody()) {
            try {
                String result = new String(response.getBody(), StandardCharsets.UTF_8);
                if (save) saveDataFromJson(result);
                return result;
            } catch (Exception e) {
                throw new RuntimeException("Error processing data", e);
            }
        } else {
            throw new RuntimeException("No data received from StackOverflow API");
        }
    }

    public String HTTPContent(String content) {
        try {
            GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
            BufferedReader bf = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
            StringBuilder outStr = new StringBuilder();
            String line;
            while ((line = bf.readLine()) != null) {
                outStr.append(line);
            }
            return outStr.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error processing data", e);
        }
    }



    public String fetchJavaQuestions() {
        return fetchJavaQuestionURL(toURL("java", "activity", "desc", "stackoverflow"), false);
    }

    public String fetchQuestionsByTag(String tag) {//add page=?
        return fetchJavaQuestionURL(toURL("java;"+tag, "activity", "desc", "stackoverflow"), false);
    }

    public void autoFetch(int num) {
        for (int i = 1; i <= num; i++) {
            fetchJavaQuestionURL(toURL("java", "activity", "desc", "stackoverflow", i, 50), true);
        }
    }


    public void saveJavaQuestions(StackoverflowResponse data) {
        stackoverflowResponseRepository.save(data);
    }

    public void saveDataFromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 解析JSON数据
            StackoverflowResponse response = objectMapper.readValue(json, StackoverflowResponse.class);

            // 获取所有的item
            List<StackoverflowData> items = response.getItems();

            // 遍历每个item并获取owner对象
            for (StackoverflowData item : items) {
                Owner owner = item.getOwner();
                ownerRepository.save(owner);
                stackoverflowDataRepository.save(item);
            }
            stackoverflowResponseRepository.save(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
