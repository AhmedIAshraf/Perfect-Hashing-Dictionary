package org.dictionary;

import java.util.ArrayList;

public class LinearSpace<T> extends HashTable<T>{

    private ArrayList<QuadraticSpace<T>> primary_table;

    public LinearSpace() {
        this.primary_table = new ArrayList<QuadraticSpace<T>>(this.size);
        for(int i = 0; i < this.size; ++i) this.primary_table.add(new QuadraticSpace<>(true));
        generateHashFunction(true);
    }

    @Override
    public boolean insert(T value ,boolean linear) {
        int index = getHashIndex(value);
        boolean inserted = this.primary_table.get(index).insert(value,linear);
        if(inserted) {
            this.allocated++;
            return true;
        }
        return false;
    }

    @Override
    boolean delete(T value) {
        int index = getHashIndex(value);
        if (this.primary_table.get(index) == null)
            return false;
        else{
            this.primary_table.get(index).delete(value);
            return true;
        }
    }

    @Override
    boolean search(T value) {
        int index = getHashIndex(value);
        if (this.primary_table.get(index) == null)
            return false;
        else{
            this.allocated--;
            return this.primary_table.get(index).search(value);
        }
    }

    @Override
    void batchInsert(T[] items) {
        int additionalLength=0;
        for (T item : items) {
            if (!search(item)) additionalLength++;
        }
        int newLength = additionalLength;
        if (newLength == 0) {
            System.out.println("No New Words Can be Added, No Need for Rehashing.");
            return;
        }
        if (this.allocated!=0) newLength += (int)this.size;
        T[] temp = (T[]) new Object[newLength];
        int k = 0;
        for (int i = 0; i < this.size; ++i) {
            if(this.primary_table.get(i) != null) {
                int length = this.primary_table.get(i).primaryTable.length;
                for (int j = 0; j < length; ++j) {
                    if (this.primary_table.get(i).primaryTable[j] == null) continue;
                    else temp[k++] = this.primary_table.get(i).primaryTable[j];
                }
            }
        }
        for (T item : items) {
            if(item == null) continue;
            temp[k++] = item;
        }
        reHashing(newLength ,temp);
    }

    @Override
    void batchDelete(T[] items) {
        int deletedCount = 0;
        for(T item : items) {
            boolean deleted = delete(item);
            if(deleted) deletedCount++;
            else{
                System.out.println("The Word '" + item + "' Does Not Exist.");
            }
        }
        System.out.println("Number of Deleted Words = " + deletedCount + " From "+items.length+" Words Received.");
    }

    private void reHashing (int length ,T[] insertedElements) {
        this.size = length;
        this.primary_table = new ArrayList<QuadraticSpace<T>>(this.size);
        for(int i = 0; i < this.size; ++i) this.primary_table.add(new QuadraticSpace<>(true));
        this.allocated = 0;
        generateHashFunction(true);
        for (T item : insertedElements) {
            if(item == null) continue;
            insert(item,true);
        }
        test();
    }

    void test(){
        for(int i = 0; i < this.size; ++i) {
            System.out.println("***********"+" table "+i+ " ***********");
            for (int j = 0; j < this.primary_table.get(i).primaryTable.length; ++j) {
                if (this.primary_table.get(i).primaryTable[j] == null) {
                    System.out.println("=>index "+ j + " : _");
                }
                else{
                    System.out.println("=>index "+ j + " : " + this.primary_table.get(i).primaryTable[j]);
                }
            }
        }
    }

}