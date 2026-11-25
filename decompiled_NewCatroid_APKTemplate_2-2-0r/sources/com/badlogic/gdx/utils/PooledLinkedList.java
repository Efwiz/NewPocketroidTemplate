package com.badlogic.gdx.utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PooledLinkedList.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class PooledLinkedList<T> {
    private Item<T> curr;
    private Item<T> head;
    private Item<T> iter;
    private final Pool<Item<T>> pool;
    private int size = 0;
    private Item<T> tail;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/utils/PooledLinkedList$Item.class
      classes8.dex
     */
    static final class Item<T> {
        public Item<T> next;
        public T payload;
        public Item<T> prev;

        Item() {
        }
    }

    public PooledLinkedList(int i) {
        this.pool = new Pool<Item<T>>(16, i) { // from class: com.badlogic.gdx.utils.PooledLinkedList.1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.badlogic.gdx.utils.Pool
            public Item<T> newObject() {
                return new Item<>();
            }
        };
    }

    public void add(T t) {
        Item<T> itemObtain = this.pool.obtain();
        itemObtain.payload = t;
        itemObtain.next = null;
        itemObtain.prev = null;
        if (this.head == null) {
            this.head = itemObtain;
            this.tail = itemObtain;
            this.size++;
        } else {
            itemObtain.prev = this.tail;
            this.tail.next = itemObtain;
            this.tail = itemObtain;
            this.size++;
        }
    }

    public void addFirst(T t) {
        Item<T> itemObtain = this.pool.obtain();
        itemObtain.payload = t;
        itemObtain.next = this.head;
        itemObtain.prev = null;
        Item<T> item = this.head;
        if (item != null) {
            item.prev = itemObtain;
        } else {
            this.tail = itemObtain;
        }
        this.head = itemObtain;
        this.size++;
    }

    public int size() {
        return this.size;
    }

    public void iter() {
        this.iter = this.head;
    }

    public void iterReverse() {
        this.iter = this.tail;
    }

    public T next() {
        Item<T> item = this.iter;
        if (item == null) {
            return null;
        }
        T t = item.payload;
        Item<T> item2 = this.iter;
        this.curr = item2;
        this.iter = item2.next;
        return t;
    }

    public T previous() {
        Item<T> item = this.iter;
        if (item == null) {
            return null;
        }
        T t = item.payload;
        Item<T> item2 = this.iter;
        this.curr = item2;
        this.iter = item2.prev;
        return t;
    }

    public void remove() {
        Item<T> item = this.curr;
        if (item == null) {
            return;
        }
        this.size--;
        Item<T> item2 = item.next;
        Item<T> item3 = this.curr.prev;
        this.pool.free(this.curr);
        this.curr = null;
        if (this.size == 0) {
            this.head = null;
            this.tail = null;
        } else if (item == this.head) {
            item2.prev = null;
            this.head = item2;
        } else if (item == this.tail) {
            item3.next = null;
            this.tail = item3;
        } else {
            item3.next = item2;
            item2.prev = item3;
        }
    }

    public T removeLast() {
        Item<T> item = this.tail;
        if (item == null) {
            return null;
        }
        T t = item.payload;
        this.size--;
        Item<T> item2 = this.tail.prev;
        this.pool.free(this.tail);
        if (this.size == 0) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail = item2;
            item2.next = null;
        }
        return t;
    }

    public void clear() {
        iter();
        while (next() != null) {
            remove();
        }
    }
}
