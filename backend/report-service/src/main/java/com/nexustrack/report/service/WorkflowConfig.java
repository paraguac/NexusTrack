package com.nexustrack.report.service;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("workflow")
public class WorkflowConfig {

    private String name;
    private String schedule;
    private boolean enabled;

    @XStreamImplicit(itemFieldName = "step")
    private List<String> steps;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSchedule() { return schedule; }
    public void setSchedule(String schedule) { this.schedule = schedule; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }
}
