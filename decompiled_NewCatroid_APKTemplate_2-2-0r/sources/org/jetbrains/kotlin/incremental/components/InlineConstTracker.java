package org.jetbrains.kotlin.incremental.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InlineConstTracker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001:\u0001\tJ(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "", "report", "", "filePath", "", "owner", "name", "constType", "DoNothing", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InlineConstTracker {
    void report(String filePath, String owner, String name2, String constType);

    /* JADX INFO: compiled from: InlineConstTracker.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker$DoNothing;", "Lorg/jetbrains/kotlin/incremental/components/InlineConstTracker;", "()V", "report", "", "filePath", "", "owner", "name", "constType", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DoNothing implements InlineConstTracker {
        public static final DoNothing INSTANCE = new DoNothing();

        @Override // org.jetbrains.kotlin.incremental.components.InlineConstTracker
        public void report(String filePath, String owner, String name2, String constType) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(constType, "constType");
        }

        private DoNothing() {
        }
    }
}
