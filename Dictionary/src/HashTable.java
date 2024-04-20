public class HashTable <T> {

    protected T[] primaryTable;
    protected int size=10;
    protected double loadFactor;
    protected byte[][] hashFunction;
    protected int allocated=0;
    protected int numberOfRehashing=0;
    private HashTable<T> tableConstructed;

    protected HashTable(){};
    public HashTable (int type) {
        this.primaryTable = (T[]) new Object[this.size*this.size];
        for (int i = 0; i < this.size*this.size ; i++) {this.primaryTable[i] = null;}
        this.hashFunction = new byte[this.size][32];
        //Randomize the hash function
        this.allocated = 0;
        this.numberOfRehashing=0;

        if(type==1) tableConstructed= new LinearSpace<T>();
        else if (type==2) tableConstructed= new QuadraticSpace<T>();
    }

    protected void generateHashFunction(){

    }

    protected void reHashing(){
        //don't forget the rehashing counter
    }

    protected int getHashIndex(T value){
        int index=0;
        //Do the mapping using the hash function
        return index;
    }

}
