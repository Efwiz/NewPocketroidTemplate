package com.reandroid.xml.base;

import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.InstanceIterator;
import com.reandroid.xml.base.Node;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public interface NodeTree<T extends Node> extends Node {
    void add(int i, T t);

    boolean add(T t);

    T get(int i);

    Iterator<? extends T> iterator();

    T remove(int i);

    boolean remove(T t);

    int size();

    default <T1 extends Node> int countNodesWithType(Class<T1> cls) {
        return CollectionUtil.count((Iterator<?>) iterator(cls));
    }

    default <T1 extends Node> Iterator<T1> iterator(Class<T1> cls) {
        return iterator(cls, null);
    }

    default <T1 extends Node> Iterator<T1> iterator(Class<T1> cls, Predicate<T1> predicate) {
        return new InstanceIterator(iterator(), cls, predicate);
    }
}
