package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.konan.UtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: NativePlatformSpecificCastChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0006H\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/NativePlatformSpecificCastChecker;", "Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;", "()V", "isCastPossible", "", "fromType", "Lorg/jetbrains/kotlin/types/KotlinType;", "toType", "isCastToAForwardDeclaration", "forwardDeclarationType", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativePlatformSpecificCastChecker implements PlatformSpecificCastChecker {
    public static final NativePlatformSpecificCastChecker INSTANCE = new NativePlatformSpecificCastChecker();

    private NativePlatformSpecificCastChecker() {
    }

    @Override // org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker
    public boolean isCastPossible(KotlinType fromType, KotlinType toType) {
        Intrinsics.checkNotNullParameter(fromType, "fromType");
        Intrinsics.checkNotNullParameter(toType, "toType");
        return isCastToAForwardDeclaration(toType);
    }

    private final boolean isCastToAForwardDeclaration(KotlinType forwardDeclarationType) {
        ClassifierDescriptor this$0 = forwardDeclarationType.getConstructor().getThis$0();
        return (this$0 instanceof ClassDescriptor) && UtilsKt.getForwardDeclarationKindOrNull((ClassDescriptor) this$0) != null;
    }
}
