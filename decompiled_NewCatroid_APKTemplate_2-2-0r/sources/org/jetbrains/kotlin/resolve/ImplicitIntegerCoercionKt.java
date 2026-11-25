package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ImplicitIntegerCoercion.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"hasImplicitIntegerCoercionCapability", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplicitIntegerCoercionKt {
    public static final boolean hasImplicitIntegerCoercionCapability(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return Intrinsics.areEqual(moduleDescriptor.getCapability(ImplicitIntegerCoercion.INSTANCE.getMODULE_CAPABILITY()), (Object) true);
    }
}
