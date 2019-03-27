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

import java.util.jar.JarException;


// TODO: describe the collision resolution scheme you have chosen
//     OPEN ADDRESSING: quadratic Collision handling
//     if a node is not found or array spot is full then we add exponent^2 to the hashIndex and try for the next spot
//    it will repeat this process until a open space is found, after each process exponent is increase by 1
//     if the hashIndex is equal or greater to the size of the table then it is set too 0 so that it loops
//
// TODO: explain your hashing algorithm here
//          I hashed by first usding the defualt hashCode method of th object
//          then % by the size of the table to find the hashIndex for teh node

/**
 * hashTable data structure made to store nodes
 * @param <K> key to be used by the node
 * @param <V> value to be used by the node
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
//TODO: add absolute value for all of the hashCodes, and fix all the negative numbers

    private int numKeys, capacity;
    private double loadFactorThreshold, loadFactor;
    private HashNode<K, V>[] hashArray;


    /**
     * creates default hashTable size 11, loacFacotrThreshold = 0.75
     */
    public HashTable() {
        hashArray = new HashNode[11];
        capacity = 11;
        numKeys = 0;
        loadFactor = 0;
        loadFactorThreshold = .75;


    }

    /**
     * creates hashTable to desired size and loadFactorThreshold
     * @param initialCapacity size of array
     * @param loadFactorThreshold point of doubling
     */
    public HashTable(int initialCapacity, double loadFactorThreshold) {
        hashArray = new HashNode[initialCapacity];
        capacity = initialCapacity;
        numKeys = 0;
        loadFactor = 0;
        this.loadFactorThreshold = loadFactorThreshold;

    }



    /**
     *
     * @return loadFactorThreshold
     */
    @Override
    public double getLoadFactorThreshold() {
        return loadFactorThreshold;
    }

    /**
     *finds then sets loadFactor
     * @return loadFactor
     */
    @Override
    public double getLoadFactor() {
        loadFactor = (numKeys * 1.0) / capacity;
        return loadFactor;
    }

    /**
     *
     * @return capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * OPEN ADDRESSING: quadratic Collision handling
     * if a node is not found or array spot is full then we add exponent^2 to the hashIndex and try for the next spot
     * it will repeat this process until a open space is found, after each process exponent is increase by 1
     * if the hashIndex is equal or greater to the size of the table then it is set too 0 so that it loops
     * @return 2
     */
    @Override
    public int getCollisionResolution() {
        return 2;
    }

    /**
     * finds the hashcode of key then % by capacity to find hashIndex
     * if full then use linear collision handling
     * finds open slot then creates new node and inserts it in the array and increase size by one
     * if loadFactorThreshold is breached by insertion then @increaseCapacity() is called, resizing the array
     * @param key key of node
     * @param value value of node
     * @throws IllegalNullKeyException if key is now throw
     * @throws DuplicateKeyException if key is duplicate throw
     */
    @Override
    public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }

        int hashIndex = Math.abs(key.hashCode()) % capacity;
        int exponent = 1;

        while (true) {
            if (hashArray[hashIndex] == null) {
                hashArray[hashIndex] = new HashNode<K, V>(key, value);
                numKeys++;
                if (this.getLoadFactor() >= this.getLoadFactorThreshold()){
                    this.increaseCapacity();
                }
                return;
            } else if (hashArray[hashIndex].isRemoved() == true) {
                hashArray[hashIndex] = new HashNode<K, V>(key, value);
                numKeys++;
                if (this.getLoadFactor() >= this.getLoadFactorThreshold()){
                    this.increaseCapacity();
                }
                return;
            } else if (hashArray[hashIndex].getKey() == key) {
                throw new DuplicateKeyException();
            } else {
                hashIndex = hashIndex + exponent * exponent;
                exponent++;
                if (hashIndex >= capacity) {
                    hashIndex = 0;
                }
            }
        }


    }

    /**
     * sets hashArray to new empty array that is 2x + 1 the last size, then rehashes all nodes
     * from the previous array to the new one
     * @throws IllegalNullKeyException from insert
     * @throws DuplicateKeyException from insert
     */
    private void increaseCapacity() throws IllegalNullKeyException, DuplicateKeyException{
        HashNode<K, V>[] tempHashArray = hashArray;
        hashArray = new HashNode[hashArray.length * 2 + 1];
        numKeys = 0;
        capacity = hashArray.length;

        for (HashNode<K,V> node : tempHashArray) {
            if (node != null) {
                if (node.isRemoved() == false) {
                    this.insert(node.getKey(), node.getValue());
                }
            }
        }
    }

    /**
     * hashes key then finds hashIndex of key, then tries to find node
     * if node isn't in initial spot then uses linear probing to find it
     * if node isn't found after one loop of probing then returns false
     * when node is found it's removed value is set too true, which means it will be skipped
     * and removed when array size is increased
     * @param key of node to be removed
     * @return true if node is removed otherwise false
     * @throws IllegalNullKeyException if key is null
     */
    @Override
    public boolean remove(K key) throws IllegalNullKeyException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }

        int exponent = 1;
        int hashIndex = Math.abs(key.hashCode()) % capacity;
        int startingIndex = hashIndex;

        do {
            if (hashArray[hashIndex] == null) {
                return false;

            } else if (hashArray[hashIndex].isRemoved() == true) {

            } else if (hashArray[hashIndex].getKey().equals(key)) {
                hashArray[hashIndex].setRemoved(true);
                numKeys--;
                return true;
            }


            hashIndex = hashIndex + exponent * exponent;
            exponent++;
            if (hashIndex >= capacity) {
                hashIndex = 0;
            }

        } while (startingIndex != hashIndex);

        return false;


    }

    /**
     * hashes key then finds hashIndex of key, then tries to find node
     * if node isn't in initial spot then uses linear probing to find it
     * if it makes a full loop without finding the node then KeyNotFoundException is thrown
     * when node is found then returns value of said node
     * @param key key of node to get value of
     * @return value of the node
     * @throws IllegalNullKeyException if key is null
     * @throws KeyNotFoundException if node isn't found
     */
    @Override
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }
        int exponent = 1;
        boolean looped = false;
        int hashIndex = Math.abs(key.hashCode()) % capacity;
        int stratingIndex = hashIndex;

        do {
            if (hashArray[hashIndex] == null) {
                throw new KeyNotFoundException();
            } else if (hashArray[hashIndex].isRemoved() == true) {

            } else if (hashArray[hashIndex].getKey().equals(key)) {
                return hashArray[hashIndex].getValue();
            }


            hashIndex = hashIndex + exponent * exponent;
            exponent++;
            if (hashIndex >= capacity) {
                hashIndex = 0;
                looped = true;
            }

        } while (stratingIndex <= hashIndex || !looped);

        throw new KeyNotFoundException();

    }

    /**
     *
     * @return numKeys
     */
    @Override
    public int numKeys() {
        return numKeys;
    }


    /**
     * node class to be used by the hash table
     * @param <K> key of a node
     * @param <V> value of a node
     */
    private class HashNode<K, V> {
        private K key;
        private V value;
        private boolean removed;

        /**
         * @return isRemoved
         */
        private boolean isRemoved() {
            return removed;
        }

        /**
         * sets removed
         * @param removed boolean value ofr removed to be set too
         */
        private void setRemoved(boolean removed) {
            this.removed = removed;
        }

        /**
         * constructor to make a HashNode object
         * @param key key of the node
         * @param value value of the node
         */
        private HashNode(K key, V value) {
            this.key = key;
            this.value = value;
            removed = false;
        }

        /**
         *
         * @return key
         */
        private K getKey() {
            return key;
        }


        /**
         *
         * @return value
         */
        private V getValue() {
            return value;
        }


    }
}
