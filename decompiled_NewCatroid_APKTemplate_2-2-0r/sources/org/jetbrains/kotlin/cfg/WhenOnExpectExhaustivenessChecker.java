package org.jetbrains.kotlin.cfg;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;

/* JADX INFO: compiled from: WhenChecker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cfg/WhenOnExpectExhaustivenessChecker;", "", "()V", "getMissingCase", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "subjectDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WhenOnExpectExhaustivenessChecker {
    public static final WhenOnExpectExhaustivenessChecker INSTANCE = new WhenOnExpectExhaustivenessChecker();

    /* JADX INFO: compiled from: WhenChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private WhenOnExpectExhaustivenessChecker() {
    }

    public final WhenMissingCase getMissingCase(ClassDescriptor subjectDescriptor) {
        boolean z = false;
        if (subjectDescriptor != null && subjectDescriptor.isExpect()) {
            z = true;
        }
        if (!z) {
            return null;
        }
        Intrinsics.checkNotNull(subjectDescriptor);
        int i = WhenMappings.$EnumSwitchMapping$0[subjectDescriptor.getKind().ordinal()];
        if (i == 1) {
            return WhenMissingCase.ConditionTypeIsExpect.SealedClass.INSTANCE;
        }
        if (i == 2) {
            return WhenMissingCase.ConditionTypeIsExpect.SealedInterface.INSTANCE;
        }
        if (i == 3) {
            return WhenMissingCase.ConditionTypeIsExpect.Enum.INSTANCE;
        }
        return WhenMissingCase.Unknown.INSTANCE;
    }
}
