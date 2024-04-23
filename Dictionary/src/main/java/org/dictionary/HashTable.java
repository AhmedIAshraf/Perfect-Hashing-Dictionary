package org.dictionary;

import java.util.Random;

public abstract class HashTable <T>{

    protected T[] primaryTable;
    protected int size = 3;
    protected byte[][] hashFunction;
    protected int allocated = 0;
    protected int collisions = 0;
    protected int counterOfRehashing = 0;
    private final Random rand = new Random();

    protected HashTable () {}

    protected void generateHashFunction(boolean linear){
        double numberOfBits;
        if (linear) {
            if (this.size==1) numberOfBits=1;
            else numberOfBits = (Math.log(this.size)/Math.log(2));
        }
        else numberOfBits = (Math.log(this.size*this.size)/Math.log(2));
//System.out.println("Number of bits: " + numberOfBits);
        this.hashFunction = new byte[(int)numberOfBits][50];
        for (int i = 0; i < hashFunction.length ; i++) {
            for (int j = 0; j < 50; j++) {
                hashFunction[i][j] = (byte) rand.nextInt(2);
            }
        }
    }

    protected int getHashIndex(T value){
//        String bin = Integer.toBinaryString(value.hashCode());
//        byte word[] = new byte[50];
//        int j=0;
//        for (int i = bin.length()-1; i >=0 ; i--) { word[j++] = (byte) (bin.charAt(i) == '1' ? 1 : 0); }
//        int index = matrixMultiplication(this.hashFunction,word);
//        return index;
        StringBuilder bin = new StringBuilder();
        byte[] word = new byte[50];
        char[] chars = value.toString().toCharArray();
        for (char aChar : chars)
            bin.append(toBinary(aChar - 'a' + 1));

        int j=0;
        for (int i = bin.length()-1; i >=0 ; i--) { word[j++] = (byte) (bin.charAt(i) == '1' ? 1 : 0); }
        return matrixMultiplication(this.hashFunction,word);
    }

    String toBinary(int n){
        char[] bin = "00000".toCharArray();
        int i=4;
        while(i>=0){
            bin[i--] = (n%2==1) ? '1' : '0';
            n = n/2;
        }
        return new String(bin);
    }

    private int matrixMultiplication(byte[][] a, byte[] b){
        byte[] c = new byte[hashFunction.length];
        int indexInDecimal=0;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < 50; j++) {
                c[i] += (byte) ((a[i][j]*b[j])%2);
            }
            c[i] %= 2;
            indexInDecimal += (int) (c[i]*(Math.pow(2,c.length-1-i)));
        }
        return indexInDecimal;
    }


    abstract boolean insert(T value,boolean linear);
    abstract boolean delete(T value);
    abstract boolean search(T value);
    abstract void batchInsert(T[] items);
    abstract void batchDelete(T[] items);

}