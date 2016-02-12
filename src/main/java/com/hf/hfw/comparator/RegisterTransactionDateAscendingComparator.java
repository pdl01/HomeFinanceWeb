/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.comparator;

import com.hf.homefinanceshared.RegisterTransaction;
import java.util.Comparator;

/**
 *
 * @author pldorrell
 */
public class RegisterTransactionDateAscendingComparator implements Comparator<RegisterTransaction> {

    @Override
    public int compare(RegisterTransaction o1, RegisterTransaction o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }
        return o1.getTxnDate().compareTo(o2.getTxnDate());
    }
    
}
