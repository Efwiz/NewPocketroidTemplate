package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.synthetic.FunctionInterfaceConstructorDescriptor;

/* JADX INFO: compiled from: SamConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u0003\u001a\u00020\u0004H&¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/synthetic/FunctionInterfaceConstructorDescriptor;", "getSingleAbstractMethod", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SamConstructorDescriptor extends SimpleFunctionDescriptor, FunctionInterfaceConstructorDescriptor {
    CallableMemberDescriptor getSingleAbstractMethod();
}
