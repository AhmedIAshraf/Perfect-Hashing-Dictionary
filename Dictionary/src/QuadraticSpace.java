public class QuadraticSpace<T> extends HashTable{

    public boolean insert(T value){
        int index=getHashIndex(value);
        if (this.primaryTable[index]==null){
            this.primaryTable[index]=value;
            this.allocated++;
            if ((double)this.allocated/(double)this.size >= 0.8){
                System.out.println("Rehashing is Necessary as the Load Factor (n/m) Exceeded 0.8");
                reHashing();
            }
            return true;
        }else if (this.primaryTable[index].equals(value)){
            return false; //the element already exists
        }else{
            System.out.println("Rehashing is Necessary as There is a Collision While Inserting '"+value+"'.");
            reHashing();
            return true;
        }
    }

    public boolean delete(T value){
        int index=getHashIndex(value);
        if (this.primaryTable[index]==null){
            return false; //The element isn't in the table
        }else{
            this.allocated--;
            this.primaryTable[index] = "";
            return true;
        }
    }

    public boolean search(T value){
        int index=getHashIndex(value);
        return (this.primaryTable[index].equals(value));
    }

    public void batchInsert(T[] items){

    }

    public void batchDelete(T[] items){

    }


}
