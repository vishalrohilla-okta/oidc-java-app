package com.okta.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.sample.dto.RegisterDTO;
import com.okta.sample.model.RegisterModel;
import com.okta.sample.model.RegisterResult;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Value("#{ @environment['okta.api.token'] }")
    protected String oktaApiToken;

    @Value("#{ @environment['okta.api.url'] }")
    protected String oktaApiUrl;

    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);

    private ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    @Override
    public RegisterResult register(RegisterModel registerModel) {

        RegisterResult result = new RegisterResult();
        result.setStatus(RegisterResult.Status.SUCCESS);

        RegisterDTO registerDTO = new RegisterDTO(registerModel);

        StringEntity input = null;
        String json = null;
        try {
            json = mapper.writeValueAsString(registerDTO);
            input = new StringEntity(json);
        } catch (UnsupportedEncodingException|JsonProcessingException e) {
            log.error("Failed to encode StringEntity: {} - {}", json, e.getMessage(), e);
            result.setStatus(RegisterResult.Status.FAIL);
            result.setMessage("Failed to serialize register form data.");
            return result;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost  = new HttpPost(oktaApiUrl + "/api/v1/users?activate=true");

        httpPost.setEntity(input);
        httpPost.addHeader("content-type", "application/json");
        httpPost.addHeader("Authorization", "SSWS " + oktaApiToken);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() != 200) {
                JsonNode node = mapper.readTree(response.getEntity().getContent());
                String message = node.path("errorCauses").path(0).path("errorSummary").asText();
                log.error("Failed to register: {} ", message);
                result.setStatus(RegisterResult.Status.FAIL);
                result.setMessage(message);
            }
        } catch (IOException e) {
            log.error("Failed to register: {} ", e.getMessage(), e);
            result.setStatus(RegisterResult.Status.FAIL);
            result.setMessage(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error("Failed to close http client: {} ", e.getMessage(), e);
            }
        }

        return result;
    }
}
