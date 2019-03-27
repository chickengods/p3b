/////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:   p3
// Files: HashTable.java HashTableTest.java
//
// Author:    Donelson Graham Berger
// Email:      dgberger2@wisc.edu
// Lecturer's Name: Deppler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:       none
// Online Sources:  none

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
 
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;


/**
 * this class test the methods in HashTable to see if they work
 */
public class HashTableTest{


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
    
    /** 
     * Tests that a HashTable returns an integer code
     * indicating which collision resolution strategy 
     * is used.
     * REFER TO HashTableADT for valid collision scheme codes.
     */
    @Test
    public void test000_collision_scheme() {
        HashTableADT htIntegerKey = new HashTable<Integer,String>();
        int scheme = htIntegerKey.getCollisionResolution();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }
        
    /** IMPLEMENTED AS EXAMPLE FOR YOU
     * Tests that insert(null,null) throws IllegalNullKeyException
     */
    @Test
    public void test001_IllegalNullKey() {
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            htIntegerKey.insert(null, null);
            fail("should not be able to insert null key");
        } 
        catch (IllegalNullKeyException e) { /* expected */ } 
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }

    /**
     * test if the array could expand correctly when loadFactorThreshold is pasted
     */
    @Test
    public void test002_increases_size(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }

        }
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }

    /**
     * sees if error is thrown when duplicate key is entered
     */
    @Test
    public void test003_duplicate_key_insert(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            htIntegerKey.insert(44, "boi");
        }
        catch (DuplicateKeyException e) { /* expected */ }
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }

    /**
     * tests if the remove method works
     */
    @Test
    public void test004_remove(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            if (htIntegerKey.remove(44) == false){
                fail("remove should've returned true");
            }
        }
        catch (Exception e) {
            fail("insert null key should not throw exception "+e.getClass().getName());
        }
    }

    /**
     * test if the remove method works if it can't find a key
     */
    @Test
    public void test004_remove_fail(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            if (htIntegerKey.remove(20202) == true){
                fail("remove should've returned false");
            }
        }
        catch (Exception e) {
            fail("there shouldn't have been an exception");
        }
    }

    /**
     * test if the get method returns the right value
     */
    @Test
    public void test005_get(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            htIntegerKey.insert(124, "right");

            if (!htIntegerKey.get(124).equals("right")){
                fail("the value should have been 'right'");
            }
        }
        catch (Exception e) {
            fail("there shouldn't have been an exception");
        }
    }

    /**
     * tests if the numKeys stays correct after insertions and removals
     */
    @Test
    public void test006_numKeys_stays_correct(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            if (htIntegerKey.numKeys() != 50){
                fail("numKeys should have been 50");
            }
            for (int i = 0;  i < 40; i++){
                htIntegerKey.remove(i);
            }
            if (htIntegerKey.numKeys() != 10){
                fail("numKeys should have ben 10");
            }
        }
        catch (Exception e) {
            fail("there shouldn't have been an exception");
        }
    }

    /**
     * test if get() method throws right Exception when it can't find the node
     */
    @Test
    public void test007_get_key_not_found(){
        try {
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i < 50; i++){
                htIntegerKey.insert(i, "boy");
            }
            htIntegerKey.insert(124, "right");

            htIntegerKey.get(324).equals("right");
            fail("should have thrown KeyNotFoundException");

        }
        catch (KeyNotFoundException e){

        }
        catch (Exception e) {
            fail("wrong exception thrown");
        }
    }

    /**
     * test if something breaks after mass insert and mass removal
     */
    @Test
    public  void test008_mass_insert_and_removal_numKeys_check(){
        try{
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i< 10000; i++){
                htIntegerKey.insert(i,"wooo");
            }
            if (htIntegerKey.numKeys() != 10000){
                fail("numKeys should be equal to 10000");
            }
            for (int i = 10000; i > 0; i--) {
                htIntegerKey.remove(i);
            }
            if(htIntegerKey.numKeys() != 1) {
                fail("numKeys should be equal to 1");
            }
            }catch (Exception e){
            fail("shouldn't have thrown an error");
        }
    }

    /**
     * test if right error is thrown if get can't find a node
     */
    @Test
    public void test009_get_key_not_found(){
        try{
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = 0; i< 120; i++){
                htIntegerKey.insert(i,"wooo");
            }
            htIntegerKey.get(100000);
        }
        catch (KeyNotFoundException e){

        }
        catch (Exception e){
            fail("shouldn't have thrown an error");
        }
    }

    @Test
    public void test010_insert_remove_many(){
        try{
            HashTableADT htIntegerKey = new HashTable<Integer,String>();
            for (int i = -3245; i<-234598 ; i--){
                htIntegerKey.insert(i,"wooo");
            }
            for (int i = -5555; i < -23479; i--) {
                htIntegerKey.remove(i);
            }

        }
        catch (Exception e){
            fail("there shouldn't have been an Exeception");
        }
    }


    

    
}
