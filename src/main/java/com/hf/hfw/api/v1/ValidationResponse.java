
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
