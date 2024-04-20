package org.dictionary;

import java.util.ArrayList;

public class QuadraticSpace<T> extends HashTable<T>{

    public QuadraticSpace() {
        this.primaryTable = (T[]) new Object[this.size*this.size];
        for (int i = 0; i < this.size*this.size ; i++) {this.primaryTable[i] = null;}
        generateHashFunction();
        this.allocated = 0;
    }

    public boolean insert(T value){
        int index=getHashIndex(value);
        if (this.primaryTable[index]==null){
            this.primaryTable[index]=value;
            this.allocated++;
//            if ((double)this.allocated/(double)this.size >= 0.8){
//                System.out.println("Rehashing is Necessary as the Load Factor (n/m) Exceeded 0.8");
//                reHashing(this.size*2);
//            }
            return true;
        }else if (this.primaryTable[index].equals(value)){
            return false; //the element already exists
        }else{
            System.out.println("Rehashing is Necessary as There is a Collision While Inserting '"+value+"'.");
            T[] add = (T[]) new Object[1];
            add[0]=value;
            reHashing(this.size,add);
            return true;
        }
    }

    public boolean delete(T value){
        int index=getHashIndex(value);
        if (this.primaryTable[index]==null||!this.primaryTable[index].equals(value)){
            return false; //The element isn't in the table
        }else{
            this.allocated--;
            this.primaryTable[index] = null;
            return true;
        }
    }

    public boolean search(T value){
        int index=getHashIndex(value);
        if (this.primaryTable[index]==null) return false;
        else return (this.primaryTable[index].equals(value));
    }

    public void batchInsert(T[] items){
        int newLength;
        if (this.primaryTable.length==0) newLength=items.length;
        else newLength = items.length+this.size;
        reHashing(items.length+this.size,items);
    }

    public void batchDelete(T[] items){
        int deletedWordsCounter=0;
        for (T item : items) {
            boolean deleted = this.delete(item);
            if (deleted) {
                deletedWordsCounter++;
            } else {
                System.out.println("The Word '" + item + "' Does Not Exist.");
            }
        }
        System.out.println("Number of Deleted Words = "+deletedWordsCounter+" From "+items.length+" Words Received.");
    }

    private void reHashing(int length,T[] insertedElements){
        boolean collisionsExist = true;
        int numberOfInsertedElements=0;
        ArrayList<String> messages = new ArrayList<String>();
        counterOfRehashing = 0;
        T[] temp;
        while (collisionsExist){
            temp = (T[]) new Object[length*length];
            for (int i = 0; i < length*length; i++){temp[i] = null;}
            allocated = 0;
            counterOfRehashing++;
            messages.clear();
            collisionsExist = false;
            generateHashFunction();
            for (int i = 0; i < this.size; i++){
                if(primaryTable[i]!=null){
                    int index=getHashIndex(this.primaryTable[i]);
                    if (temp[index]==null){
                        temp[index]=this.primaryTable[i];
                        allocated++;
                    }else if (temp[index].equals(this.primaryTable[i])) {
                        messages.add("The Word '" + temp[i] + "' Already Exists.");
                    }else{
                        collisionsExist=true;
                        break;
                    }
                }
            }
            if (!collisionsExist){
                for (int i = 0; i < insertedElements.length; i++){
                    int index=getHashIndex(insertedElements[i]);
                    if (temp[index]==null){
                        temp[index]=insertedElements[i];
                        allocated++;
                        numberOfInsertedElements++;
                    }else if (temp[index].equals(insertedElements[i])) {
                        messages.add("The Word '" + temp[i] + "' Already Exists.");
                    }else{
                        collisionsExist=true;
                        break;
                    }
                }
            }
            if (!collisionsExist){
                for (String message : messages) {
                    System.out.println(message);
                }
                System.out.println("As Rehashing was Necessary, it Took "+counterOfRehashing+" iteration(s) to Rebuild the Hash Table.");
                if(numberOfInsertedElements>1) System.out.println("Number of Inserted Elements = "+numberOfInsertedElements+" From "+insertedElements.length+" Received Words.");
                this.primaryTable = temp;
            }
        }
    }

}
