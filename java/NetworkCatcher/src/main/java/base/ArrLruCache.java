package base;

import utils.Utils;

import java.util.Arrays;

public abstract class ArrLruCache<K, E> {

    private E[] entries = null;

    private K[] keys = null;

    private final int size;

    public ArrLruCache(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    protected abstract E createEntry(K key);

    protected abstract void recycleEntry(K key, E entry);

    public E fromKey(K k) {
        if (k == null) {
            return null;
        }
        E e = null;
        if (entries == null || keys == null) {
            Object[] objs1 = new Object[size];
            entries = (E[]) objs1;
            Object[] objs2 = new Object[size];
            keys = (K[]) objs2;
        }
        int index = 0;
        while (index < size) {
            if (Utils.equals(keys[index], k)) {
                e = entries[index];
            }
            index++;
            if (e != null) {
                break;
            }
        }
        K recycleK = null;
        E recycleE = null;
        if (e == null) {
            e = createEntry(k);
            recycleK = keys[size - 1];
            recycleE = entries[size - 1];
        }
        moveToFront(k, e, index);
        if (recycleE != null && recycleK != null) {
            recycleEntry(recycleK, recycleE);
        }
        return e;
    }

    private void moveToFront(K k, E e, int index) {
        for (int i = index - 1; i > 0; i--) {
            keys[i] = keys[i - 1];
            entries[i] = entries[i - 1];
        }
        keys[0] = k;
        entries[0] = e;
    }

    @Override
    public String toString() {
        return "ArrLruCache{" +
                "entries=" + Arrays.toString(entries) +
                ", keys=" + Arrays.toString(keys) +
                ", size=" + size +
                '}';
    }
}
