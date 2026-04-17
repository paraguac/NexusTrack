package com.nexustrack.report.service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.stereotype.Service;

@Service
public class WorkflowConfigParser {

    private final XStream xstream;

    public WorkflowConfigParser() {
        this.xstream = new XStream();
        this.xstream.addPermission(AnyTypePermission.ANY);
        this.xstream.processAnnotations(WorkflowConfig.class);
    }

    public WorkflowConfig parse(String xml) {
        return (WorkflowConfig) xstream.fromXML(xml);
    }

    public String serialize(WorkflowConfig config) {
        return xstream.toXML(config);
    }
}
