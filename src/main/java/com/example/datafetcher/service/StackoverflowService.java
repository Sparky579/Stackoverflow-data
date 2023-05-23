package com.example.datafetcher.service;
import com.example.datafetcher.model.*;
import com.example.datafetcher.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
import java.util.Objects;
import java.util.zip.GZIPInputStream;

@Service
public class StackoverflowService {

    private static final String STACKOVERFLOW_API_URL = "https://api.stackexchange.com/2.2/questions?order=desc&sort=activity&tagged=java&site=stackoverflow";

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AnswerResponseRepository answerResponseRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionResponseRepository questionResponseRepository;
    @Autowired
    private APIsRepository apisRepository;


    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    String toAnswerURL(String tag, String sort, String order, String site) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site;
    }

    String toAnswerURL(String tag, String sort, String order, String site, int page, int pageSize) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site + "&page=" + page + "&pagesize=" + pageSize;
    }

    String toQuestionURL(String tag, String sort, String order, String site) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site;
    }
    String toQuestionURL(String tag, String sort, String order, String site, int page, int pageSize) {
        return "https://api.stackexchange.com/2.2/questions?order=" + order + "&sort=" + sort + "&tagged=" + tag + "&site=" + site + "&page=" + page + "&pagesize=" + pageSize + "&filter=withbody";
    }

    String toQuestionAnswerURL(String questionID, String sort, String order, String site, int page, int pageSize) {
        return "https://api.stackexchange.com/2.2/questions/" + questionID + "/answers?order=" + order + "&sort=" + sort + "&site=" + site + "&page=" + page + "&pagesize=" + pageSize;
    }
    String toQuestionCommentURL(String questionID, String sort, String order, String site, int page, int pageSize) {
        return "https://api.stackexchange.com/2.2/questions/" + questionID + "/comments?order=" + order + "&sort=" + sort + "&site=" + site + "&page=" + page + "&pagesize=" + pageSize;
    }

    public String fetchJavaAnswersURLJson(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Encoding", "gzip");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.hasBody()) {
            try {
                String result = new String(response.getBody(), StandardCharsets.UTF_8);
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



    public String fetchJavaAnswers() {
        return fetchJavaAnswersURLJson(toAnswerURL("java", "activity", "desc", "stackoverflow"));
    }

    public String fetchJavaAnswersByTag(String tag) {//add page=?
        return fetchJavaAnswersURLJson(toAnswerURL("java;"+tag, "activity", "desc", "stackoverflow"));
    }



    public void autoFetchAnswer(int num, int pageSize) {
        for (int i = 1; i <= num; i++) {
            String answerJson = fetchJavaAnswersURLJson(toAnswerURL("java", "activity", "desc", "stackoverflow", i, pageSize));
            Tools.saveAnswerResponse(answerJson, answerRepository, answerResponseRepository, ownerRepository);
        }
    }

    public void autoFetchQuestion(int num, int pageSize) {
        for (int i = 1; i <= num; i++) {
            String questionJson = fetchJavaAnswersURLJson(toQuestionURL("java", "activity", "desc", "stackoverflow", i+10, pageSize));
            APIService apiService = new APIService(apisRepository);
            APIService.apisRepository = apisRepository;
            apiService.extractAndCountAPIs(questionJson);
            try
            {
                Tools.saveQuestionResponse(questionJson, questionRepository, questionResponseRepository, ownerRepository);
            }catch (Exception e){
//                System.out.println("error");
            }
            QuestionResponse questionResponse = Tools.parseJson(questionJson, QuestionResponse.class);
            for (Question question : questionResponse.getItems()) {
                if (question.isIs_answered()) {
                    autoFetchAnswerByQuestionID(question.getQuestion_id());
                    autoFetchCommentByQuestionID(question.getQuestion_id());
                }
            }
        }
    }

    public void autoFetchAnswerByQuestionID(int id) {
        int i = 0;
        while (true) {
            i++;
            String answerJson = fetchJavaAnswersURLJson(toQuestionAnswerURL(String.valueOf(id), "activity", "desc", "stackoverflow", i, 50));
            AnswerResponse answerResponse = Tools.parseJson(answerJson, AnswerResponse.class);
            Tools.saveAnswerResponse(answerJson, answerRepository, answerResponseRepository, ownerRepository);
            if (!answerResponse.isHas_more()) break;
        }
    }

    public void autoFetchCommentByQuestionID(int id) {
        int i = 0;
        while (true) {
            i++;
            String commentJson = fetchJavaAnswersURLJson(toQuestionCommentURL(String.valueOf(id), "creation", "desc", "stackoverflow", i, 50));
            CommentResponse answerResponse = Tools.parseJson(commentJson, CommentResponse.class);
            Tools.setCommentResponse(commentJson, commentRepository, ownerRepository, id);
            if (!answerResponse.isHas_more()) break;
        }
    }



    public void saveJavaQuestions(AnswerResponse data) {
        answerRepository.saveAll(data.getItems());
        answerResponseRepository.save(data);
    }

    @Deprecated
    public void saveDataFromJson(String json,
                                 Class<? extends Response> responseClass,
                                 Class<? extends EntityWithOwner> itemClass,
                                 JpaRepository<? extends EntityWithOwner, Long> itemRepository) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 解析JSON数据
            Response response = objectMapper.readValue(json, responseClass);

            // 获取所有的item
            List<? extends EntityWithOwner> items = response.getItems();

            // 遍历每个item并获取owner对象
            for (EntityWithOwner item : items) {
                Owner owner = item.getOwner();
                //if not exist, save owner
                if (ownerRepository.findById((long) owner.getUserId()).isEmpty()) {
                    ownerRepository.save(owner);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}