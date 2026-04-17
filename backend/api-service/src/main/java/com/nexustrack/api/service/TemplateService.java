package com.nexustrack.api.service;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    public String render(String template, Map<String, String> vars) {
        StringSubstitutor substitutor = new StringSubstitutor(vars);
        return substitutor.replace(template);
    }

    public String renderProjectEvent(String name, String eventType, String actor) {
        Map<String, String> vars = new HashMap<>();
        vars.put("name", name);
        vars.put("eventType", eventType);
        vars.put("actor", actor);
        return render("Project '${name}' was ${eventType} by ${actor}", vars);
    }

    public String renderWebhookPayload(String projectName, String callbackUrl) {
        Map<String, String> vars = new HashMap<>();
        vars.put("projectName", projectName);
        vars.put("callbackUrl", callbackUrl);
        return render("{\"project\": \"${projectName}\", \"callback\": \"${callbackUrl}\"}", vars);
    }
}
