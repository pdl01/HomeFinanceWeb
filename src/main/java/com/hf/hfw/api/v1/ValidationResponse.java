/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.api.v1;

import java.util.List;

/**
 *
 * @author pldorrell
 */
public class ValidationResponse {
    private Object validatedObject;
    private boolean valid;
    private List<String> messages;

    public Object getValidatedObject() {
        return validatedObject;
    }

    public void setValidatedObject(Object validatedObject) {
        this.validatedObject = validatedObject;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
}
