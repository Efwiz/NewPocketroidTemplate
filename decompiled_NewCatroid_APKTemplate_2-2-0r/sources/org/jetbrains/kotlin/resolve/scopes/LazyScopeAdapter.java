package org.jetbrains.kotlin.resolve.scopes;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: LazyScopeAdapter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u00068TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/LazyScopeAdapter;", "Lorg/jetbrains/kotlin/resolve/scopes/AbstractScopeAdapter;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "getScope", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lkotlin/jvm/functions/Function0;)V", "lazyScope", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "workerScope", "getWorkerScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyScopeAdapter extends AbstractScopeAdapter {
    private final NotNullLazyValue<MemberScope> lazyScope;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public LazyScopeAdapter(Function0<? extends MemberScope> getScope) {
        this(null, getScope, 1, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(getScope, "getScope");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ LazyScopeAdapter(StorageManager NO_LOCKS, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
            Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
        }
        this(NO_LOCKS, function0);
    }

    public LazyScopeAdapter(StorageManager storageManager, final Function0<? extends MemberScope> getScope) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(getScope, "getScope");
        this.lazyScope = storageManager.createLazyValue(new Function0<MemberScope>() { // from class: org.jetbrains.kotlin.resolve.scopes.LazyScopeAdapter$lazyScope$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MemberScope invoke() {
                MemberScope memberScopeInvoke = getScope.invoke();
                return memberScopeInvoke instanceof AbstractScopeAdapter ? ((AbstractScopeAdapter) memberScopeInvoke).getActualScope() : memberScopeInvoke;
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.AbstractScopeAdapter
    protected MemberScope getWorkerScope() {
        return this.lazyScope.invoke();
    }
}
