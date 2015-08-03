/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hf.hfw.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DailyBalanceDateHelperTest {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public DailyBalanceDateHelperTest() {
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

    /**
     * Test of getDatesToBuildFor method, of class DailyBalanceDateHelper.
     */
    @Test
    public void testGetDatesToBuildFor() {
        System.out.println("getDatesToBuildFor");
        //Calendar cal  = Calendar.getInstance();
        
        Date startDate =null;
        try {
            startDate = sdf.parse("2015-01-01");
        } catch (ParseException ex) {
            Logger.getLogger(DailyBalanceDateHelperTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date endDate = new Date();
        DailyBalanceDateHelper instance = new DailyBalanceDateHelper();
        //Set<Date> expResult = null;
        Set<Date> result = instance.getDatesToBuildFor(startDate, endDate);
        for (Date date:result) {
            System.out.println(sdf.format(date));
        }
        
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getEndDate method, of class DailyBalanceDateHelper.
     */
    @Test
    public void testGetEndDate() {
        System.out.println("getEndDate");
        Date startDate = null;
        Date referenceDate = null;
        DailyBalanceDateHelper instance = new DailyBalanceDateHelper();
        Date expResult = null;
        Date result = instance.getEndDate(startDate, referenceDate);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
