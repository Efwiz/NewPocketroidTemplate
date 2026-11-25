package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NameTables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0007B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope;", "", "()V", "isReserved", "", "name", "", "EmptyScope", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NameScope {
    public abstract boolean isReserved(String name2);

    /* JADX INFO: compiled from: NameTables.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope$EmptyScope;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope;", "()V", "isReserved", "", "name", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EmptyScope extends NameScope {
        public static final EmptyScope INSTANCE = new EmptyScope();

        @Override // org.jetbrains.kotlin.ir.backend.js.utils.NameScope
        public boolean isReserved(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return false;
        }

        private EmptyScope() {
        }
    }
}
