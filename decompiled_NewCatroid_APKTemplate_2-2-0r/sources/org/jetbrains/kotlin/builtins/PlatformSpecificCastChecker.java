package org.jetbrains.kotlin.builtins;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: PlatformSpecificCastChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0001\u0007J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;", "", "isCastPossible", "", "fromType", "Lorg/jetbrains/kotlin/types/KotlinType;", "toType", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PlatformSpecificCastChecker {

    /* JADX INFO: compiled from: PlatformSpecificCastChecker.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker$Default;", "Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;", "()V", "isCastPossible", "", "fromType", "Lorg/jetbrains/kotlin/types/KotlinType;", "toType", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements PlatformSpecificCastChecker {
        @Override // org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker
        public boolean isCastPossible(KotlinType fromType, KotlinType toType) {
            Intrinsics.checkNotNullParameter(fromType, "fromType");
            Intrinsics.checkNotNullParameter(toType, "toType");
            return false;
        }
    }

    boolean isCastPossible(KotlinType fromType, KotlinType toType);
}
