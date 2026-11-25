package com.reandroid.dex.model;

import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.value.ArrayValue;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.utils.collection.ComputeIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class DexValueArray extends DexValue implements Iterable<DexValue> {
    public DexValueArray(Dex dex, ArrayValue arrayValue) {
        super(dex, arrayValue);
    }

    @Override // java.lang.Iterable
    public Iterator<DexValue> iterator() {
        return ComputeIterator.of(getDexValueBlock().iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2776lambda$iterator$0$comreandroiddexmodelDexValueArray((DexValueBlock) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$iterator$0$com-reandroid-dex-model-DexValueArray, reason: not valid java name */
    /* synthetic */ DexValue m2776lambda$iterator$0$comreandroiddexmodelDexValueArray(DexValueBlock dexValueBlock) {
        return DexValue.create(this, dexValueBlock);
    }

    public Iterator<DexValue> clonedIterator() {
        return ComputeIterator.of(getDexValueBlock().clonedIterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m2775lambda$clonedIterator$1$comreandroiddexmodelDexValueArray((DexValueBlock) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clonedIterator$1$com-reandroid-dex-model-DexValueArray, reason: not valid java name */
    /* synthetic */ DexValue m2775lambda$clonedIterator$1$comreandroiddexmodelDexValueArray(DexValueBlock dexValueBlock) {
        return create(this, dexValueBlock);
    }

    public DexValue get(int i) {
        return createValue(getDexValueBlock().get(i));
    }

    public int size() {
        return getDexValueBlock().size();
    }

    public boolean remove(int i) {
        return getDexValueBlock().remove(i);
    }

    public boolean remove(DexValue dexValue) {
        if (dexValue == null) {
            return false;
        }
        return getDexValueBlock().remove(dexValue.getDexValueBlock());
    }

    public boolean removeIf(final Predicate<? super DexValue> predicate) {
        return getDexValueBlock().removeIf(new Predicate() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m2777lambda$removeIf$2$comreandroiddexmodelDexValueArray(predicate, (DexValueBlock) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeIf$2$com-reandroid-dex-model-DexValueArray, reason: not valid java name */
    /* synthetic */ boolean m2777lambda$removeIf$2$comreandroiddexmodelDexValueArray(Predicate predicate, DexValueBlock dexValueBlock) {
        return predicate.test(createValue(dexValueBlock));
    }

    public void clear() {
        getDexValueBlock().clear();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean sort(final Comparator<DexValue> comparator) {
        return getDexValueBlock().sort(new Comparator() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda3
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.m2778lambda$sort$3$comreandroiddexmodelDexValueArray(comparator, (DexValueBlock) obj, (DexValueBlock) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sort$3$com-reandroid-dex-model-DexValueArray, reason: not valid java name */
    /* synthetic */ int m2778lambda$sort$3$comreandroiddexmodelDexValueArray(Comparator comparator, DexValueBlock dexValueBlock, DexValueBlock dexValueBlock2) {
        return comparator.compare(create(this, dexValueBlock), create(this, dexValueBlock2));
    }

    public boolean isFirstType(DexValueType<?> dexValueType) {
        return dexValueType != null && dexValueType == getFirstType();
    }

    public DexValueType<?> getFirstType() {
        DexValueBlock<?> dexValueBlock = getDexValueBlock().get(0);
        if (dexValueBlock != null) {
            return dexValueBlock.getValueType();
        }
        return null;
    }

    public DexValue createNext(DexValueType<?> dexValueType) {
        return DexValue.create(this, getDexValueBlock().createNext(dexValueType));
    }

    public Iterator<String> getStrings() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getString();
            }
        });
    }

    public Iterator<TypeKey> getTypeKeys() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getTypeKey();
            }
        });
    }

    public Iterator<Number> getNumbers() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getNumber();
            }
        });
    }

    public Iterator<Character> getCharacters() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getCharacter();
            }
        });
    }

    public Iterator<Integer> getIntegers() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getInteger();
            }
        });
    }

    public Iterator<Key> getKeys() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getKey();
            }
        });
    }

    public Iterator<DexAnnotation> getAnnotations() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.model.DexValueArray$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexValue) obj).getAnnotation();
            }
        });
    }

    public void add(String str) {
        createNext(DexValueType.STRING).setString(str);
    }

    public void add(Key key) {
        if (key instanceof TypeKey) {
            createNext(DexValueType.TYPE).setKey(key);
        } else if (key instanceof FieldKey) {
            createNext(DexValueType.ENUM).setKey(key);
        } else {
            if (key instanceof MethodKey) {
                createNext(DexValueType.METHOD).setKey(key);
                return;
            }
            throw new RuntimeException("Unimplemented key-type: " + key.getClass());
        }
    }

    public void add(byte b) {
        createNext(DexValueType.BYTE).setByte(b);
    }

    public void add(short s) {
        createNext(DexValueType.SHORT).setShort(s);
    }

    public void add(int i) {
        createNext(DexValueType.INT).setInteger(i);
    }

    public void add(float f) {
        createNext(DexValueType.FLOAT).setFloat(f);
    }

    public void add(long j) {
        createNext(DexValueType.LONG).setLong(j);
    }

    public void add(double d) {
        createNext(DexValueType.DOUBLE).setDouble(d);
    }

    public void add(char c) {
        createNext(DexValueType.CHAR).setCharacter(c);
    }

    @Override // com.reandroid.dex.model.DexValue
    public ArrayValue getDexValueBlock() {
        return (ArrayValue) super.getDexValueBlock();
    }

    DexValue createValue(DexValueBlock<?> dexValueBlock) {
        return DexValue.create(getDeclaring(), dexValueBlock);
    }
}
