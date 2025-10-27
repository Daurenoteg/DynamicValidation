package com.example.guarantebasic.validator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "validation")
public class ValidationProperties {
    private Map<String, Boolean> controls;

    public Map<String, Boolean> getControls() {
        return controls;
    }

    public void setControls(Map<String, Boolean> controls) {
        this.controls = controls;
    }

    public boolean isEnabled(String controlName) {
        return controls.getOrDefault(controlName, false);
    }
}
