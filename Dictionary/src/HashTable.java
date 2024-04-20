import java.util.Random;

public class HashTable <T> {

    protected T[] primaryTable;
    protected int size = 10;
    protected byte[][] hashFunction;
    protected int allocated = 0;
    protected int counterOfRehashing = 0;
    private final Random rand = new Random();

    protected HashTable () {}

    public HashTable<T> createTable (int type){
        this.primaryTable = (T[]) new Object[this.size*this.size];
        for (int i = 0; i < this.size*this.size ; i++) {this.primaryTable[i] = null;}
        generateHashFunction();
        this.allocated = 0;
        if(type==1) return new LinearSpace<T>();
        else return new QuadraticSpace<T>();
    }

    protected void generateHashFunction(){
        this.hashFunction = new byte[this.size][32];
        for (int i = 0; i < this.size ; i++) {
            for (int j = 0; j < 32; j++) {
                hashFunction[i][j] = (byte) rand.nextInt(2);
            }
        }
    }

    protected int getHashIndex(T value){
        int index=0;
        String bin = Integer.toBinaryString(value.hashCode());
        byte word[] = new byte[32];
        for (int i = 0; i < bin.length(); i++) { word[i] = (byte) (bin.indexOf(i) == '0' ? 0 : 1); }
        byte[] indexInBin = matrixMultiplication(this.hashFunction,word);
        Integer.parseInt(new String(indexInBin),2);
        return index;
    }

    private byte[] matrixMultiplication(byte[][] a, byte[] b){
        byte[] c = new byte[this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < 32; j++) {
                c[i] += (byte) ((a[i][j]*b[j])%2);
            }
            c[i] %= 2;
        }
        return c;
    }

}