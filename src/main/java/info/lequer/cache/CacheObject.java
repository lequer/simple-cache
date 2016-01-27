/*
 * 
 * 
 * 
 */
package info.lequer.cache;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author 
 * @param <VALUE_CLASS>
 */
public class CacheObject<VALUE_CLASS extends Object> implements Serializable {

    public transient Long lastAccessed = System.currentTimeMillis();
    public VALUE_CLASS value;

    protected CacheObject(VALUE_CLASS value) {
        this.value = value;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeLong(lastAccessed);
        out.writeObject(value);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        lastAccessed = in.readLong();
        value = (VALUE_CLASS) in.readObject();
    }

    @Override
    public String toString() {
        return value.toString(); //value;
    }
    
    public long getLastAccessed(){
     return lastAccessed;   
    }

}
