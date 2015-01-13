
package com.hf.hfw.accounts.events;

import com.hf.homefinanceshared.Budget;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author pldorrell
 */
public class BudgetEvent extends ApplicationEvent {
    private BudgetEvent.BudgetEventType type;
    private Budget budget;
    
    public enum BudgetEventType {
        ADDED(1),MODIFIED(2),DELETING(3),DELETED(4);
        private int code;
	private BudgetEventType(int _code) {
            this.code = _code;
	}
	public int getCode() {
            return code;
	}
    }
    
    public BudgetEvent(Object _source,Budget _budget,BudgetEvent.BudgetEventType _type) {
	super(_source);
        this.budget = _budget;
        this.type = _type;
    }
}
