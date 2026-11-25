package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: AttributeArrayOwner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004B\u0007\b\u0016¢\u0006\u0002\u0010\u0005B\u0015\b\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00028\u0001H\u0004¢\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\r2\u000e\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0014H\u0004R*\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007@BX\u0084\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/util/AttributeArrayOwner;", "K", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;", "()V", "arrayMap", "Lorg/jetbrains/kotlin/util/ArrayMap;", "(Lorg/jetbrains/kotlin/util/ArrayMap;)V", "<set-?>", "getArrayMap", "()Lorg/jetbrains/kotlin/util/ArrayMap;", "registerComponent", "", "keyQualifiedName", "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "removeComponent", "tClass", "Lkotlin/reflect/KClass;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AttributeArrayOwner<K, T> extends AbstractArrayMapOwner<K, T> {
    private ArrayMap<T> arrayMap;

    protected AttributeArrayOwner(ArrayMap<T> arrayMap) {
        Intrinsics.checkNotNullParameter(arrayMap, "arrayMap");
        this.arrayMap = arrayMap;
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected final ArrayMap<T> getArrayMap() {
        return this.arrayMap;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public AttributeArrayOwner() {
        EmptyArrayMap emptyArrayMap = EmptyArrayMap.INSTANCE;
        Intrinsics.checkNotNull(emptyArrayMap, "null cannot be cast to non-null type org.jetbrains.kotlin.util.ArrayMap<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
        this(emptyArrayMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected final void registerComponent(String keyQualifiedName, T value) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        Intrinsics.checkNotNullParameter(value, "value");
        int id2 = getTypeRegistry().getId(keyQualifiedName);
        int size = this.arrayMap.getSize();
        if (size == 0) {
            this.arrayMap = new OneElementArrayMap(value, id2);
            return;
        }
        if (size == 1) {
            ArrayMap<T> arrayMap = this.arrayMap;
            Intrinsics.checkNotNull(arrayMap, "null cannot be cast to non-null type org.jetbrains.kotlin.util.OneElementArrayMap<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
            OneElementArrayMap oneElementArrayMap = (OneElementArrayMap) arrayMap;
            if (oneElementArrayMap.getIndex() == id2) {
                this.arrayMap = new OneElementArrayMap(value, id2);
                return;
            } else {
                ArrayMapImpl arrayMapImpl = new ArrayMapImpl();
                this.arrayMap = arrayMapImpl;
                arrayMapImpl.set(oneElementArrayMap.getIndex(), oneElementArrayMap.getValue());
            }
        }
        this.arrayMap.set(id2, value);
    }

    protected final void removeComponent(KClass<? extends K> tClass) {
        Intrinsics.checkNotNullParameter(tClass, "tClass");
        int id2 = getTypeRegistry().getId(tClass);
        if (this.arrayMap.get(id2) == null) {
            return;
        }
        if (this.arrayMap.getSize() == 1) {
            EmptyArrayMap emptyArrayMap = EmptyArrayMap.INSTANCE;
            Intrinsics.checkNotNull(emptyArrayMap, "null cannot be cast to non-null type org.jetbrains.kotlin.util.ArrayMap<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
            this.arrayMap = emptyArrayMap;
            return;
        }
        ArrayMap<T> arrayMap = this.arrayMap;
        Intrinsics.checkNotNull(arrayMap, "null cannot be cast to non-null type org.jetbrains.kotlin.util.ArrayMapImpl<T of org.jetbrains.kotlin.util.AttributeArrayOwner>");
        ArrayMapImpl arrayMapImpl = (ArrayMapImpl) arrayMap;
        arrayMapImpl.remove(id2);
        if (arrayMapImpl.getSize() == 1) {
            ArrayMapImpl.Entry entry = (ArrayMapImpl.Entry) CollectionsKt.first((List) arrayMapImpl.entries());
            this.arrayMap = new OneElementArrayMap(entry.component2(), entry.getKey());
        }
    }
}
