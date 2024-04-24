package org.dictionary;
import java.util.ArrayList;
import java.util.Arrays;

public class QuadraticSpace<T> extends HashTable<T>{

    public QuadraticSpace(boolean linear) {
        if (linear) this.size=1;
        this.primaryTable = (T[]) new Object[this.size*this.size];
        for (int i = 0; i < this.size ; i++) {this.primaryTable[i] = null;}
        generateHashFunction(false);
        this.allocated = 0;
    }

    public boolean insert(T value,boolean linear){

        int index=getHashIndex(value);
        if (this.primaryTable[index]!=null&&this.primaryTable[index].equals(value)) return false; //the element already exists

        this.allocated++;
        T[] add = (T[]) new Object[1];
        add[0] = value;
        if(linear) {
            reHashing(allocated,add);
            return true;
        }
//System.out.println("word is : "+value+" with Index = "+index);
        if ((double)this.allocated/(double)primaryTable.length >= 0.75){
            System.out.println("Rehashing is Necessary as the Load Factor (n/m) Exceeded 0.75");
            reHashing(allocated,add);
        }else if (this.primaryTable[index]==null){
          this.primaryTable[index]=value;
        }else if(!this.primaryTable[index].equals(value)) {
            this.collisions++;
            System.out.println("Rehashing is Necessary as There is a Collision While Inserting '" + value + "'.");
            reHashing(allocated, add);
        }
        return true;
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
        int additionalLength=0;
        for (T item : items)
            if (!search(item)) additionalLength++;

        int newLength=additionalLength;
        if (newLength==0){
            System.out.println("No New Words Can be Added, No Need for Rehashing.");
            return;
        }
        if (this.allocated!=0) newLength+=allocated;
        reHashing(newLength,items);
        System.out.println("Number Of Collisions = "+this.collisions);
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
        this.size = length;
        boolean collisionsExist = true;
        int numberOfInsertedElements=0;
        counterOfRehashing = 0;
        ArrayList<String> messages = new ArrayList<String>();
        T[] temp;
        while (collisionsExist){
            temp = (T[]) new Object[length*length];
            Arrays.fill(temp, null);
            allocated = 0;
            numberOfInsertedElements=0;
            counterOfRehashing++;
//System.out.println("Rehashing Number "+counterOfRehashing);
            messages.clear();
            collisionsExist = false;
            generateHashFunction(false);
            for (int i = 0; i < primaryTable.length; i++){
                if(primaryTable[i]!=null){
                    int index=getHashIndex(this.primaryTable[i]);
//System.out.println("word is : "+primaryTable[i]+" with Index = "+index);
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
                for (T insertedElement : insertedElements) {
                    if (insertedElement == null) {
                        continue;
                    }
                    int index = getHashIndex(insertedElement);
//System.out.println("word is : "+insertedElements[i]+" with Index = "+index);
                    if (temp[index] == null) {
                        temp[index] = insertedElement;
                        allocated++;
                        numberOfInsertedElements++;
                    } else if (temp[index].equals(insertedElement)) {
                        messages.add("The Word '" + insertedElement + "' Already Exists.");
                    } else {
                        collisionsExist = true;
                        break;
                    }
                }
            }
            if (!collisionsExist){
                for (String message : messages) {
                    System.out.println(message);
                }
                System.out.println("As Rehashing was Necessary, it Took "+counterOfRehashing+" iteration(s) to (Re)build the Hash Table.");
                if(numberOfInsertedElements>1) System.out.println("Number of Inserted Elements = "+numberOfInsertedElements+" From "+insertedElements.length+" Received Words.");
                System.out.println("Length = "+length+" Table Length = "+length*length);
                this.primaryTable = temp;
            }
        }
    }



}