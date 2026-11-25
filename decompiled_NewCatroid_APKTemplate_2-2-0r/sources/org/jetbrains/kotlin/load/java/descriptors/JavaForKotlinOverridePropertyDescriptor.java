package org.jetbrains.kotlin.load.java.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;

/* JADX INFO: compiled from: JavaForKotlinOverridePropertyDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/descriptors/JavaForKotlinOverridePropertyDescriptor;", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaPropertyDescriptor;", "ownerDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getterMethod", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "setterMethod", "overriddenProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "getGetterMethod", "()Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getOverriddenProperty", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getSetterMethod", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaForKotlinOverridePropertyDescriptor extends JavaPropertyDescriptor {
    private final SimpleFunctionDescriptor getterMethod;
    private final PropertyDescriptor overriddenProperty;
    private final SimpleFunctionDescriptor setterMethod;

    public final SimpleFunctionDescriptor getGetterMethod() {
        return this.getterMethod;
    }

    public final SimpleFunctionDescriptor getSetterMethod() {
        return this.setterMethod;
    }

    public final PropertyDescriptor getOverriddenProperty() {
        return this.overriddenProperty;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaForKotlinOverridePropertyDescriptor(ClassDescriptor ownerDescriptor, SimpleFunctionDescriptor getterMethod, SimpleFunctionDescriptor simpleFunctionDescriptor, PropertyDescriptor overriddenProperty) {
        super(ownerDescriptor, Annotations.INSTANCE.getEMPTY(), getterMethod.getModality(), getterMethod.getVisibility(), simpleFunctionDescriptor != null, overriddenProperty.getShortName(), getterMethod.getSourceElement(), null, CallableMemberDescriptor.Kind.DECLARATION, false, null);
        Intrinsics.checkNotNullParameter(ownerDescriptor, "ownerDescriptor");
        Intrinsics.checkNotNullParameter(getterMethod, "getterMethod");
        Intrinsics.checkNotNullParameter(overriddenProperty, "overriddenProperty");
        this.getterMethod = getterMethod;
        this.setterMethod = simpleFunctionDescriptor;
        this.overriddenProperty = overriddenProperty;
    }
}
