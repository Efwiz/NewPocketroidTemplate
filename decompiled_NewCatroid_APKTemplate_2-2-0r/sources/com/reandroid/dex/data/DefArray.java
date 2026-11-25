package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.EditableItem;
import com.reandroid.dex.common.IdUsageIterator;
import com.reandroid.dex.data.Def;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DefArray<T extends Def<?>> extends BlockList<T> implements Iterable<T>, EditableItem, SmaliFormat, IdUsageIterator {
    private final IntegerReference itemCount;
    private ClassId mClassId;

    void sortAnnotations() {
    }

    public DefArray(IntegerReference integerReference, Creator<T> creator) {
        super(creator);
        this.itemCount = integerReference;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void onPreRemove(T t) {
        AnnotationsDirectory uniqueAnnotationsDirectory = getUniqueAnnotationsDirectory();
        if (uniqueAnnotationsDirectory != null) {
            uniqueAnnotationsDirectory.remove(t);
        }
        resetIndex();
        super.onPreRemove(t);
    }

    @Override // com.reandroid.arsc.container.BlockList
    public final boolean sort(Comparator<? super T> comparator) {
        if (!needsSort(comparator)) {
            sortAnnotations();
            return false;
        }
        onPreSort();
        boolean zSort = super.sort(comparator);
        onPostSort();
        return zSort;
    }

    void onPreSort() {
        ClassId classId = getClassId();
        if (classId != null) {
            classId.getUniqueAnnotationsDirectory();
        }
        linkAnnotation();
    }

    void onPostSort() {
        resetIndex();
        sortAnnotations();
    }

    public T getOrCreate(Key key) {
        T t = (T) get(key);
        if (t != null) {
            return t;
        }
        T t2 = (T) createNext();
        t2.setKey(key);
        return t2;
    }

    public T get(Key key) {
        for (T t : this) {
            if (key.equals(t.getKey())) {
                return t;
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public T createNext() {
        T t = (T) super.createNext();
        updateCount();
        return t;
    }

    private ClassId searchClassId() {
        ClassId classId = this.mClassId;
        if (classId != null) {
            return classId;
        }
        Iterator<T> it = iterator();
        return it.hasNext() ? it.next().getClassId() : classId;
    }

    public ClassId getClassId() {
        return this.mClassId;
    }

    public void setClassId(ClassId classId) {
        if (this.mClassId == classId) {
            return;
        }
        this.mClassId = classId;
        if (classId != null) {
            linkAnnotation();
        }
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        super.onPreRefresh();
        linkAnnotation();
        if (sort(CompareUtil.getComparatorUnchecked())) {
            return;
        }
        resetIndex();
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        updateCount();
    }

    private void updateCount() {
        this.itemCount.set(getCount());
    }

    public ClassData getClassData() {
        return (ClassData) getParent();
    }

    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setSize(this.itemCount.get());
        super.readChildes(blockReader);
    }

    private void linkAnnotation() {
        AnnotationsDirectory annotationsDirectory;
        if (getCount() == 0 || (annotationsDirectory = getAnnotationsDirectory()) == null) {
            return;
        }
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            annotationsDirectory.link(it.next());
        }
    }

    AnnotationsDirectory getAnnotationsDirectory() {
        ClassId classIdSearchClassId = searchClassId();
        if (classIdSearchClassId == null) {
            return null;
        }
        return classIdSearchClassId.getAnnotationsDirectory();
    }

    AnnotationsDirectory getUniqueAnnotationsDirectory() {
        ClassId classIdSearchClassId = searchClassId();
        if (classIdSearchClassId == null) {
            return null;
        }
        return classIdSearchClassId.getUniqueAnnotationsDirectory();
    }

    private void resetIndex() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().resetIndex();
        }
    }

    public void replaceKeys(Key key, Key key2) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().editInternal(block);
        }
    }

    @Override // com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return new IterableIterator<Def<?>, IdItem>(iterator()) { // from class: com.reandroid.dex.data.DefArray.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(Def<?> def) {
                return def.usedIds();
            }
        };
    }

    public void merge(DefArray<?> defArray) {
        int count = defArray.getCount();
        setSize(count);
        for (int i = 0; i < count; i++) {
            get(i).merge((Def) defArray.get(i));
        }
        this.itemCount.set(count);
        linkAnnotation();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        Iterator<T> it = getChildes().iterator();
        while (it.hasNext()) {
            it.next().append(smaliWriter);
            smaliWriter.newLine();
        }
    }
}
