package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ArrayMapOwner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00030\u0004:\u0001 B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\u000e\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000f\u001a\u00020\u0010H\u0086\u0002¢\u0006\u0002\u0010\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u0016H\u0086\u0002J\u001d\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0001H$¢\u0006\u0002\u0010\u001cJ%\u0010\u0017\u001a\u00020\u00182\u000e\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u001e2\u0006\u0010\u001b\u001a\u00028\u0001H\u0004¢\u0006\u0002\u0010\u001fR\u0018\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X¤\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000bX¤\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "arrayMap", "Lorg/jetbrains/kotlin/util/ArrayMap;", "getArrayMap", "()Lorg/jetbrains/kotlin/util/ArrayMap;", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", "get", "index", "", "(I)Ljava/lang/Object;", "isEmpty", "", "isNotEmpty", "iterator", "", "registerComponent", "", "keyQualifiedName", "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "tClass", "Lkotlin/reflect/KClass;", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)V", "AbstractArrayMapAccessor", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractArrayMapOwner<K, V> implements Iterable<V>, KMappedMarker {
    protected abstract ArrayMap<V> getArrayMap();

    protected abstract TypeRegistry<K, V> getTypeRegistry();

    protected abstract void registerComponent(String keyQualifiedName, V value);

    /* JADX INFO: compiled from: ArrayMapOwner.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0002\u0010\u0001*\u00020\u0002*\b\b\u0003\u0010\u0003*\u00020\u0002*\b\b\u0004\u0010\u0004*\u0002H\u00032\u00020\u0002B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J#\u0010\n\u001a\u0004\u0018\u00018\u00042\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\fH\u0004¢\u0006\u0002\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner$AbstractArrayMapAccessor;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.GPS_DIRECTION_TRUE, "id", "", "(I)V", "getId", "()I", "extractValue", "thisRef", "Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;", "(Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AbstractArrayMapAccessor<K, V, T extends V> {
        private final int id;

        public AbstractArrayMapAccessor(int i) {
            this.id = i;
        }

        protected final int getId() {
            return this.id;
        }

        protected final T extractValue(AbstractArrayMapOwner<K, V> thisRef) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            return thisRef.getArrayMap().get(this.id);
        }
    }

    protected final void registerComponent(KClass<? extends K> tClass, V value) {
        Intrinsics.checkNotNullParameter(tClass, "tClass");
        Intrinsics.checkNotNullParameter(value, "value");
        String qualifiedName = tClass.getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        registerComponent(qualifiedName, value);
    }

    @Override // java.lang.Iterable
    public final Iterator<V> iterator() {
        return getArrayMap().iterator();
    }

    public final boolean isEmpty() {
        return getArrayMap().getSize() == 0;
    }

    public final boolean isNotEmpty() {
        return getArrayMap().getSize() != 0;
    }

    public final V get(int index) {
        return getArrayMap().get(index);
    }
}
