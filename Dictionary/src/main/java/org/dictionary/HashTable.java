package org.dictionary;

import java.util.Random;

public abstract class HashTable <T>{

    protected T[] primaryTable;
    protected int size = 8;
    protected byte[][] hashFunction;
    protected int allocated = 0;
    protected int counterOfRehashing = 0;
    private final Random rand = new Random();

    protected HashTable () {}

    protected void generateHashFunction(){
        this.hashFunction = new byte[(int)Math.log(this.size)][32];
        for (int i = 0; i < hashFunction.length ; i++) {
            for (int j = 0; j < 32; j++) {
                hashFunction[i][j] = (byte) rand.nextInt(2);
            }
        }
    }

    protected int getHashIndex(T value){
        String bin = Integer.toBinaryString(value.hashCode());
        byte word[] = new byte[32];
        for (int i = 0; i < bin.length(); i++) { word[i] = (byte) (bin.charAt(i) == '1' ? 1 : 0); }
        int index = matrixMultiplication(this.hashFunction,word);
        return index;
    }

    private int matrixMultiplication(byte[][] a, byte[] b){
        byte[] c = new byte[hashFunction.length];
        int indexInDecimal=0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < 32; j++) {
                c[i] += (byte) ((a[i][j]*b[j])%2);
            }
            c[i] %= 2;
            indexInDecimal += c[i]*(Math.pow(2,i));
        }
        return indexInDecimal;
    }


    abstract boolean insert(T value);
    abstract boolean delete(T value);
    abstract boolean search(T value);
    abstract void batchInsert(T[] items);
    abstract void batchDelete(T[] items);

}