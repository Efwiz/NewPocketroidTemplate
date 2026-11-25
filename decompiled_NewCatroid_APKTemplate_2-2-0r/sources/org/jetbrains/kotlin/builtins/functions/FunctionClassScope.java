package org.jetbrains.kotlin.builtins.functions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.resolve.scopes.GivenFunctionsMemberScope;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: FunctionClassScope.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0014¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/builtins/functions/FunctionClassScope;", "Lorg/jetbrains/kotlin/resolve/scopes/GivenFunctionsMemberScope;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "containingClass", "Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/builtins/functions/FunctionClassDescriptor;)V", "computeDeclaredFunctions", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionClassScope extends GivenFunctionsMemberScope {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionClassScope(StorageManager storageManager, FunctionClassDescriptor containingClass) {
        super(storageManager, containingClass);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.GivenFunctionsMemberScope
    protected List<FunctionDescriptor> computeDeclaredFunctions() {
        ClassDescriptor containingClass = getContainingClass();
        Intrinsics.checkNotNull(containingClass, "null cannot be cast to non-null type org.jetbrains.kotlin.builtins.functions.FunctionClassDescriptor");
        FunctionTypeKind functionTypeKind = ((FunctionClassDescriptor) containingClass).getFunctionTypeKind();
        if (Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE)) {
            return CollectionsKt.listOf(FunctionInvokeDescriptor.INSTANCE.create((FunctionClassDescriptor) getContainingClass(), false));
        }
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE) ? CollectionsKt.listOf(FunctionInvokeDescriptor.INSTANCE.create((FunctionClassDescriptor) getContainingClass(), true)) : CollectionsKt.emptyList();
    }
}
