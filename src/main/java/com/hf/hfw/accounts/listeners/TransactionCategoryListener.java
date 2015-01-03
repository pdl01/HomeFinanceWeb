/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.accounts.listeners;

import com.hf.hfw.accounts.events.TransactionEvent;
import com.hf.hfw.manager.CategoryCacheManagerImpl;
import com.hf.homefinanceshared.CategorySplit;
import com.hf.homefinanceshared.RegisterTransaction;
import org.springframework.context.ApplicationListener;

/**
 *
 * @author pldorrell
 */
public class TransactionCategoryListener  implements ApplicationListener<TransactionEvent>  {

    public CategoryCacheManagerImpl getCategoryCacheManager() {
        return categoryCacheManager;
    }

    public void setCategoryCacheManager(CategoryCacheManagerImpl categoryCacheManager) {
        this.categoryCacheManager = categoryCacheManager;
    }
    private CategoryCacheManagerImpl categoryCacheManager;

    @Override
    public void onApplicationEvent(TransactionEvent e) {
        if (e.getType().getCode() == TransactionEvent.TransactionEventType.ADDED.getCode() || 
                e.getType().getCode() == TransactionEvent.TransactionEventType.MODIFIED.getCode()) {
        RegisterTransaction txn = e.getRegisterTransaction();
        if (txn.getCategorySplits() != null) {
            for (CategorySplit category: txn.getCategorySplits()) {
                this.categoryCacheManager.add(category.getCategory());
            }
        }    
        }
        
    }
    
}
