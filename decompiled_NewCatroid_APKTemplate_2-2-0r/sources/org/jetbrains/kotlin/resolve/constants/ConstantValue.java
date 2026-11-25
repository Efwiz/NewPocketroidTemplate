package org.jetbrains.kotlin.resolve.constants;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: constantValues.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0000¢\u0006\u0002\u0010\u0004J5\u0010\b\u001a\u0002H\t\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\n0\f2\u0006\u0010\r\u001a\u0002H\nH&¢\u0006\u0002\u0010\u000eJ\n\u0010\u000f\u001a\u0004\u0018\u00010\u0002H\u0016J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00028\u0000X\u0096\u0004¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", ExifInterface.GPS_DIRECTION_TRUE, "", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationArgumentVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "boxedValue", Namer.EQUALS_METHOD_NAME, "", "other", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstantValue<T> {
    private final T value;

    public abstract <R, D> R accept(AnnotationArgumentVisitor<R, D> visitor, D data);

    public abstract KotlinType getType(ModuleDescriptor module);

    public ConstantValue(T t) {
        this.value = t;
    }

    public T getValue() {
        return this.value;
    }

    public boolean equals(Object other) {
        if (this != other) {
            T value = getValue();
            ConstantValue constantValue = other instanceof ConstantValue ? (ConstantValue) other : null;
            if (!Intrinsics.areEqual(value, constantValue != null ? constantValue.getValue() : null)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        T value = getValue();
        if (value != null) {
            return value.hashCode();
        }
        return 0;
    }

    public String toString() {
        return String.valueOf(getValue());
    }

    public Object boxedValue() {
        return getValue();
    }
}
