package com.nexustrack.api.config;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;

@Component
public class IntegrationConfig {

    // Integration settings payloads are small config documents, so cap YAML size at 1MB.
    private static final int YAML_CODE_POINT_LIMIT = 1_048_576;
    // Keep alias expansion bounded to limit resource amplification during parsing.
    private static final int YAML_MAX_ALIASES = 50;

    public IntegrationSettings load(String yamlContent) {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setCodePointLimit(YAML_CODE_POINT_LIMIT);
        loaderOptions.setMaxAliasesForCollections(YAML_MAX_ALIASES);
        Yaml yaml = new Yaml(new Constructor(IntegrationSettings.class, loaderOptions));
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
