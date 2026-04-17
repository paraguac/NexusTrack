package com.nexustrack.api.config;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;

@Component
public class IntegrationConfig {

    public IntegrationSettings load(String yamlContent) {
        Yaml yaml = new Yaml(new Constructor(IntegrationSettings.class, new LoaderOptions()));
        return yaml.load(yamlContent);
    }

    public static class IntegrationSettings {
        private String webhookUrl;
        private int timeoutSeconds;
        private boolean retryEnabled;
        private int maxRetries;
        private String slackChannel;

        public String getWebhookUrl() { return webhookUrl; }
        public void setWebhookUrl(String webhookUrl) { this.webhookUrl = webhookUrl; }
        public int getTimeoutSeconds() { return timeoutSeconds; }
        public void setTimeoutSeconds(int timeoutSeconds) { this.timeoutSeconds = timeoutSeconds; }
        public boolean isRetryEnabled() { return retryEnabled; }
        public void setRetryEnabled(boolean retryEnabled) { this.retryEnabled = retryEnabled; }
        public int getMaxRetries() { return maxRetries; }
        public void setMaxRetries(int maxRetries) { this.maxRetries = maxRetries; }
        public String getSlackChannel() { return slackChannel; }
        public void setSlackChannel(String slackChannel) { this.slackChannel = slackChannel; }
    }
}
