package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: syntheticAccessorUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\f\u0010\b\u001a\u00020\u0001*\u00020\u0003H\u0002\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0005¨\u0006\n"}, d2 = {"getAccessorNameSuffix", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "superCallDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "getJvmName", "syntheticAccessorToSuperSuffix", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticAccessorUtilKt {
    private static final String getJvmName(CallableMemberDescriptor callableMemberDescriptor) {
        String jvmName = DescriptorUtils.getJvmName(callableMemberDescriptor);
        if (jvmName == null) {
            jvmName = callableMemberDescriptor.getShortName().asString();
        }
        Intrinsics.checkNotNull(jvmName);
        return jvmName;
    }

    public static final String getAccessorNameSuffix(CallableMemberDescriptor descriptor, ClassDescriptor classDescriptor, AccessorKind accessorKind) {
        String jvmName;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(accessorKind, "accessorKind");
        if (accessorKind == AccessorKind.JVM_DEFAULT_COMPATIBILITY) {
            return getJvmName(descriptor) + '$' + AccessorKind.JVM_DEFAULT_COMPATIBILITY.getSuffix();
        }
        if (descriptor instanceof ConstructorDescriptor) {
            return "will be ignored";
        }
        if (descriptor instanceof SimpleFunctionDescriptor) {
            jvmName = getJvmName(descriptor);
        } else if (descriptor instanceof PropertyDescriptor) {
            jvmName = getJvmName(descriptor) + '$' + accessorKind.getSuffix();
        } else {
            throw new UnsupportedOperationException("Do not know how to create accessor for descriptor " + descriptor);
        }
        return classDescriptor == null ? jvmName : jvmName + "$s" + syntheticAccessorToSuperSuffix(classDescriptor);
    }

    public static final String syntheticAccessorToSuperSuffix(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return String.valueOf(classDescriptor.getShortName().asString().hashCode());
    }
}
