package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: ArrayMapOwner.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0002*\b\b\u0002\u0010\u0004*\u0002H\u00032\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u00052\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00030\u0006B!\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00018\u0002¢\u0006\u0002\u0010\rJ.\u0010\u0011\u001a\u00028\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00072\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0096\u0002¢\u0006\u0002\u0010\u0015R\u0015\u0010\f\u001a\u0004\u0018\u00018\u0002¢\u0006\n\n\u0002\u0010\u0010\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "K", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner$AbstractArrayMapAccessor;", "Lkotlin/properties/ReadOnlyProperty;", "Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;", "keyQualifiedName", "", "id", "", "default", "(Ljava/lang/String;ILjava/lang/Object;)V", "getDefault", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/util/AbstractArrayMapOwner;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayMapAccessor<K, V, T extends V> extends AbstractArrayMapOwner.AbstractArrayMapAccessor<K, V, T> implements ReadOnlyProperty<AbstractArrayMapOwner<K, V>, V> {
    private final T default;
    private final String keyQualifiedName;

    public /* synthetic */ ArrayMapAccessor(String str, int i, Object obj, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, (i2 & 4) != 0 ? null : obj);
    }

    @Override // kotlin.properties.ReadOnlyProperty
    public /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
        return getValue((AbstractArrayMapOwner) obj, (KProperty<?>) kProperty);
    }

    public final T getDefault() {
        return this.default;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ArrayMapAccessor(String keyQualifiedName, int i, T t) {
        super(i);
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        this.keyQualifiedName = keyQualifiedName;
        this.default = t;
    }

    public T getValue(AbstractArrayMapOwner<K, V> thisRef, KProperty<?> property) {
        Intrinsics.checkNotNullParameter(thisRef, "thisRef");
        Intrinsics.checkNotNullParameter(property, "property");
        T tExtractValue = extractValue(thisRef);
        if (tExtractValue == null && (tExtractValue = this.default) == null) {
            throw new IllegalStateException(("No '" + this.keyQualifiedName + "'(" + getId() + ") in array owner: " + thisRef).toString());
        }
        return tExtractValue;
    }
}
