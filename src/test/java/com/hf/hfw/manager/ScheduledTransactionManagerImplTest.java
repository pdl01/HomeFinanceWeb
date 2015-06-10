/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import com.hf.hfw.dao.ScheduledTransactionDAO;
import com.hf.homefinanceshared.Account;
import com.hf.homefinanceshared.ScheduledTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pldorrell
 */
public class ScheduledTransactionManagerImplTest {
    
    public ScheduledTransactionManagerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    private ScheduledTransaction setUpScheduledTxn(){
        ScheduledTransaction txn = new ScheduledTransaction();
        txn.setId("xxx");
        txn.setTxnAmount(12.54);
        txn.setPayee("test xxx");
        return txn;
    }
    private void printDateList(List<String> dates) {
        for (String date: dates) {
            System.out.println(date);
        }
    }
    /**
     * Test of getRecurringDates method, of class ScheduledTransactionManagerImpl.
     */
    @Test
    public void testGetRecurringDatesDailyWithEndDate() {
        System.out.println("testGetRecurringDatesDailyWithEndDate");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_DAILY);
        txn.setBeginDate("2015-01-01");
        txn.setEndDate("2015-01-17");
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        assertTrue(result.get(0).equals("2015-01-01"));
        assertTrue(result.get(result.size()-1).equals("2015-01-17"));
        //this.printDateList(result);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGetRecurringDatesWeeklyWithEndDate() {
        System.out.println("testGetRecurringDatesWeeklyWithEndDate");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_WEEKLY);
        txn.setBeginDate("2015-01-01");
        txn.setEndDate("2015-01-17");
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        assertTrue(result.get(0).equals("2015-01-01"));
        assertTrue(result.get(result.size()-1).substring(0,7).equals("2015-01"));

        //this.printDateList(result);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testGetRecurringDatesMonthlyWithEndDate() {
        System.out.println("testGetRecurringDatesMonthlyWithEndDate");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_MONTHLY);
        txn.setBeginDate("2015-01-01");
        txn.setEndDate("2015-01-17");
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        //this.printDateList(result);
        assertTrue(result.get(0).equals("2015-01-01"));
        //assertTrue(result.get(result.size()-1).equals("2015-01-17"));        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    @Test
    public void testGetRecurringDatesMonthlyWithOccurrences() {
        System.out.println("testGetRecurringDatesMonthlyWithOccurrences");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_MONTHLY);
        txn.setBeginDate("2015-01-01");
        txn.setNumberOfOccurrences(10);
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        //this.printDateList(result);
        assertTrue(result.get(0).equals("2015-01-01"));
        assertTrue(result.size() == 10);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    @Test
    public void testGetRecurringDatesDailyWithOccurrences() {
        System.out.println("testGetRecurringDatesDailyWithOccurrences");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_DAILY);
        txn.setBeginDate("2015-01-01");
        txn.setNumberOfOccurrences(10);
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        //this.printDateList(result);
        assertTrue(result.get(0).equals("2015-01-01"));
        assertTrue(result.size() == 10);        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    @Test
    public void testGetRecurringDatesWeeklyWithOccurrences() {
        System.out.println("testGetRecurringDatesWeeklyWithOccurrences");
        ScheduledTransaction txn = this.setUpScheduledTxn();
        txn.setFrequency(ScheduledTransaction.FREQUENCY_WEEKLY);
        txn.setBeginDate("2015-12-01");
        txn.setNumberOfOccurrences(10);
        ScheduledTransactionManagerImpl instance = new ScheduledTransactionManagerImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getRecurringDates(txn);
        assertTrue(result.get(0).equals("2015-12-01"));
        assertTrue(result.size() == 10);
        //this.printDateList(result);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
