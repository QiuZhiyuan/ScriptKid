package base;

import utils.Utils;

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

    protected abstract E createEntry(K k);

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
        for (int i = 0; i < size; i++) {
            index = i;
            if (Utils.equals(keys[i], k)) {
                e = entries[i];
                break;
            }
        }
        if (e == null) {
            e = createEntry(k);
        }
        if (e != null) {
            moveToFront(k, e, index);
        }
        return e;
    }

    private void moveToFront(K k, E e, int index) {
        keys[0] = k;
        entries[0] = e;
        for (int i = 1; i < index - 1; i++) {
            keys[i + 1] = keys[i];
            entries[i + 1] = entries[i];
        }
    }
}
