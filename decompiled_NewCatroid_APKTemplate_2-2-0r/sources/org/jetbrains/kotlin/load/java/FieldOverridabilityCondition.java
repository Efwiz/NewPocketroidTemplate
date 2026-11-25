package org.jetbrains.kotlin.load.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition;

/* JADX INFO: compiled from: FieldOverridabilityCondition.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/FieldOverridabilityCondition;", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition;", "()V", "getContract", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Contract;", "isOverridable", "Lorg/jetbrains/kotlin/resolve/ExternalOverridabilityCondition$Result;", "superDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "subDescriptor", "subClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FieldOverridabilityCondition implements ExternalOverridabilityCondition {
    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Result isOverridable(CallableDescriptor superDescriptor, CallableDescriptor subDescriptor, ClassDescriptor subClassDescriptor) {
        Intrinsics.checkNotNullParameter(superDescriptor, "superDescriptor");
        Intrinsics.checkNotNullParameter(subDescriptor, "subDescriptor");
        if (!(subDescriptor instanceof PropertyDescriptor) || !(superDescriptor instanceof PropertyDescriptor)) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) subDescriptor;
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) superDescriptor;
        if (!Intrinsics.areEqual(propertyDescriptor.getName(), propertyDescriptor2.getName())) {
            return ExternalOverridabilityCondition.Result.UNKNOWN;
        }
        if (JavaDescriptorUtilKt.isJavaField(propertyDescriptor) && JavaDescriptorUtilKt.isJavaField(propertyDescriptor2)) {
            return ExternalOverridabilityCondition.Result.OVERRIDABLE;
        }
        if (JavaDescriptorUtilKt.isJavaField(propertyDescriptor) || JavaDescriptorUtilKt.isJavaField(propertyDescriptor2)) {
            return ExternalOverridabilityCondition.Result.INCOMPATIBLE;
        }
        return ExternalOverridabilityCondition.Result.UNKNOWN;
    }

    @Override // org.jetbrains.kotlin.resolve.ExternalOverridabilityCondition
    public ExternalOverridabilityCondition.Contract getContract() {
        return ExternalOverridabilityCondition.Contract.BOTH;
    }
}
