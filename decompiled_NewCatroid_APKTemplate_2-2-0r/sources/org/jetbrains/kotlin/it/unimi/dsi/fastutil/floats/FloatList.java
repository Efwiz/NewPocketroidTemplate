package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatList extends FloatCollection, Comparable<List<? extends Float>>, List<Float> {
    void add(int i, float f);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    boolean add(float f);

    void getElements(int i, float[] fArr, int i2, int i3);

    float getFloat(int i);

    int indexOf(float f);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    FloatListIterator iterator();

    int lastIndexOf(float f);

    @Override // java.util.List
    FloatListIterator listIterator();

    @Override // java.util.List
    FloatListIterator listIterator(int i);

    void removeElements(int i, int i2);

    float removeFloat(int i);

    float set(int i, float f);

    @Override // java.util.List
    FloatList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    default FloatSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractFloatList.IndexBasedSpliterator(this, 0);
        }
        return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(float[] fArr) {
        setElements(0, fArr);
    }

    default void setElements(int i, float[] fArr) {
        setElements(i, fArr, 0, fArr.length);
    }

    default void setElements(int i, float[] fArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        FloatArrays.ensureOffsetLength(fArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        FloatListIterator floatListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            floatListIteratorListIterator.nextFloat();
            floatListIteratorListIterator.set(fArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Float f) {
        add(i, f.floatValue());
    }

    default void replaceAll(FloatUnaryOperator floatUnaryOperator) {
        FloatListIterator floatListIteratorListIterator = listIterator();
        while (floatListIteratorListIterator.hasNext()) {
            floatListIteratorListIterator.set(floatUnaryOperator.apply(floatListIteratorListIterator.nextFloat()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Float> unaryOperator) {
        FloatUnaryOperator floatUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof FloatUnaryOperator) {
            floatUnaryOperator = (FloatUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            floatUnaryOperator = new FloatUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatUnaryOperator
                public final float apply(float f) {
                    return ((Float) unaryOperator.apply(Float.valueOf(f))).floatValue();
                }
            };
        }
        replaceAll(floatUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Float get(int i) {
        return Float.valueOf(getFloat(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Float) obj).floatValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Float) obj).floatValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean add(Float f) {
        return add(f.floatValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Float remove(int i) {
        return Float.valueOf(removeFloat(i));
    }

    @Override // java.util.List
    @Deprecated
    default Float set(int i, Float f) {
        return Float.valueOf(set(i, f.floatValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Float> comparator) {
        sort(FloatComparators.asFloatComparator(comparator));
    }

    default void sort(FloatComparator floatComparator) {
        float[] floatArray = toFloatArray();
        if (floatComparator == null) {
            FloatArrays.stableSort(floatArray);
        } else {
            FloatArrays.stableSort(floatArray, floatComparator);
        }
        setElements(floatArray);
    }
}
