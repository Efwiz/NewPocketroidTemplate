package com.reandroid.graph;

import com.reandroid.dex.model.DexClass;
import com.reandroid.dex.model.DexClassRepository;
import com.reandroid.utils.collection.FilterIterator;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public abstract class BaseDexClassProcessor extends GraphTask {
    private final DexClassRepository classRepository;

    public BaseDexClassProcessor(DexClassRepository dexClassRepository) {
        this.classRepository = dexClassRepository;
    }

    public Iterator<DexClass> getDexClasses(Predicate<? super DexClass> predicate) {
        return FilterIterator.of(getClassRepository().getDexClasses(), predicate);
    }

    public DexClassRepository getClassRepository() {
        return this.classRepository;
    }
}
