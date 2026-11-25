package org.jetbrains.kotlin.incremental.components;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;

/* JADX INFO: compiled from: EnumWhenTracker.kt */
/* JADX INFO: loaded from: classes4.dex */
@DefaultImplementation(impl = DoNothing.class)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0001\u0007J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "", "report", "", "whenExpressionFilePath", "", "enumClassFqName", "DoNothing", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface EnumWhenTracker {
    void report(String whenExpressionFilePath, String enumClassFqName);

    /* JADX INFO: compiled from: EnumWhenTracker.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker$DoNothing;", "Lorg/jetbrains/kotlin/incremental/components/EnumWhenTracker;", "()V", "report", "", "whenExpressionFilePath", "", "enumClassFqName", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DoNothing implements EnumWhenTracker {
        public static final DoNothing INSTANCE = new DoNothing();

        @Override // org.jetbrains.kotlin.incremental.components.EnumWhenTracker
        public void report(String whenExpressionFilePath, String enumClassFqName) {
            Intrinsics.checkNotNullParameter(whenExpressionFilePath, "whenExpressionFilePath");
            Intrinsics.checkNotNullParameter(enumClassFqName, "enumClassFqName");
        }

        private DoNothing() {
        }
    }
}
