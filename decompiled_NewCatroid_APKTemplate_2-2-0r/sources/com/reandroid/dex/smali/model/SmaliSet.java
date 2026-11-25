package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.Smali;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.InstanceIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliSet<T extends Smali> extends Smali {
    private final ArrayCollection<T> body;

    public SmaliSet() {
        ArrayCollection<T> arrayCollection = new ArrayCollection<>();
        this.body = arrayCollection;
        arrayCollection.setMonitor(new SmaliSetMonitor(this));
    }

    public Iterator<T> iterator() {
        return this.body.iterator();
    }

    public Iterator<T> iterator(int i) {
        return this.body.iterator(i);
    }

    public <T2> Iterator<T2> iterator(Class<T2> cls) {
        return (Iterator<T2>) this.body.iterator(cls);
    }

    public <T2> Iterator<T2> iterator(int i, Class<T2> cls) {
        return InstanceIterator.of(this.body.iterator(i), cls);
    }

    public Iterator<T> reversedIterator() {
        return this.body.reversedIterator();
    }

    public Iterator<T> reversedIterator(int i) {
        return this.body.reversedIterator(i);
    }

    public <T2> Iterator<T2> reversedIterator(Class<T2> cls) {
        return InstanceIterator.of(this.body.reversedIterator(), cls);
    }

    public <T2> Iterator<T2> reversedIterator(int i, Class<T2> cls) {
        return InstanceIterator.of(this.body.reversedIterator(i), cls);
    }

    public int size() {
        return this.body.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int indexOf(T t) {
        return this.body.indexOf(t);
    }

    public T get(int i) {
        return this.body.get(i);
    }

    public boolean add(T t) {
        return this.body.add(t);
    }

    public boolean contains(T t) {
        return this.body.contains(t);
    }

    public boolean remove(T t) {
        return this.body.remove(t);
    }

    public T remove(int i) {
        return this.body.remove(i);
    }

    public boolean removeIf(Predicate<? super T> predicate) {
        return this.body.removeIf(predicate);
    }

    public boolean removeInstances(final Class<?> cls) {
        ArrayCollection<T> arrayCollection = this.body;
        cls.getClass();
        return arrayCollection.removeIf(new Predicate() { // from class: com.reandroid.dex.smali.model.SmaliSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((Smali) obj);
            }
        });
    }

    public void clear() {
        Iterator<T> it = this.body.iterator();
        while (it.hasNext()) {
            it.next().setParent(null);
        }
        this.body.clear();
    }

    void onRemoved(T t) {
        if (t != null) {
            t.setParent(null);
        }
    }

    void onAdded(T t) {
        if (t != null) {
            t.setParent(this);
        }
    }

    @Override // com.reandroid.dex.smali.model.Smali, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendAllWithDoubleNewLine(iterator());
    }

    public void parse(SmaliReader smaliReader) throws IOException {
        while (parseNext(smaliReader) != null) {
            smaliReader.skipWhitespacesOrComment();
        }
    }

    public T parseNext(SmaliReader smaliReader) throws IOException {
        if (smaliReader.finished()) {
            return null;
        }
        T t = (T) createNext(smaliReader);
        if (t != null) {
            add(t);
            t.parse(smaliReader);
        }
        return t;
    }

    T createNext(SmaliReader smaliReader) {
        throw new RuntimeException("Method not implemented");
    }

    static class SmaliSetMonitor<T extends Smali> implements ArrayCollection.Monitor<T> {
        private final SmaliSet<T> smaliSet;

        SmaliSetMonitor(SmaliSet<T> smaliSet) {
            this.smaliSet = smaliSet;
        }

        @Override // com.reandroid.utils.collection.ArrayCollection.Monitor
        public void onAdd(int i, T t) {
            this.smaliSet.onAdded(t);
        }

        @Override // com.reandroid.utils.collection.ArrayCollection.Monitor
        public void onRemoved(int i, T t) {
            this.smaliSet.onRemoved(t);
        }
    }
}
