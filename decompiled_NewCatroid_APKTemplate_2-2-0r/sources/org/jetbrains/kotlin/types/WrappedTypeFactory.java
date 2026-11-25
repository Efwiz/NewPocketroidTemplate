package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: WrappedTypeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016J\u001e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/types/WrappedTypeFactory;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "createDeferredType", "Lorg/jetbrains/kotlin/types/KotlinType;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "computation", "Lkotlin/Function0;", "createRecursionIntolerantDeferredType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class WrappedTypeFactory {
    private final StorageManager storageManager;

    public WrappedTypeFactory(StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.storageManager = storageManager;
    }

    public KotlinType createDeferredType(BindingTrace trace, Function0<? extends KotlinType> computation) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(computation, "computation");
        DeferredType deferredTypeCreate = DeferredType.create(this.storageManager, trace, computation);
        Intrinsics.checkNotNullExpressionValue(deferredTypeCreate, "create(...)");
        return deferredTypeCreate;
    }

    public KotlinType createRecursionIntolerantDeferredType(BindingTrace trace, Function0<? extends KotlinType> computation) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(computation, "computation");
        DeferredType deferredTypeCreateRecursionIntolerant = DeferredType.createRecursionIntolerant(this.storageManager, trace, computation);
        Intrinsics.checkNotNullExpressionValue(deferredTypeCreateRecursionIntolerant, "createRecursionIntolerant(...)");
        return deferredTypeCreateRecursionIntolerant;
    }
}
