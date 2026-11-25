package org.jetbrains.kotlin.ir.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.expressions.IrCall;

/* JADX INFO: compiled from: PartialLinkageSupportForLowerings.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ*\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "", "isEnabled", "", "()Z", "throwLinkageError", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "partialLinkageCase", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "file", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "doNotLog", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PartialLinkageSupportForLowerings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    boolean isEnabled();

    IrCall throwLinkageError(PartialLinkageCase partialLinkageCase, IrElement element, PartialLinkageUtils.File file, boolean doNotLog);

    /* JADX INFO: compiled from: PartialLinkageSupportForLowerings.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static /* synthetic */ IrCall throwLinkageError$default(PartialLinkageSupportForLowerings partialLinkageSupportForLowerings, PartialLinkageCase partialLinkageCase, IrElement irElement, PartialLinkageUtils.File file, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: throwLinkageError");
            }
            if ((i & 8) != 0) {
                z = false;
            }
            return partialLinkageSupportForLowerings.throwLinkageError(partialLinkageCase, irElement, file, z);
        }
    }

    /* JADX INFO: compiled from: PartialLinkageSupportForLowerings.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings$Companion;", "", "()V", "DISABLED", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "getDISABLED", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final PartialLinkageSupportForLowerings DISABLED = new PartialLinkageSupportForLowerings() { // from class: org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings$Companion$DISABLED$1
            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings
            public boolean isEnabled() {
                return false;
            }

            @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings
            public IrCall throwLinkageError(PartialLinkageCase partialLinkageCase, IrElement element, PartialLinkageUtils.File file, boolean doNotLog) {
                Intrinsics.checkNotNullParameter(partialLinkageCase, "partialLinkageCase");
                Intrinsics.checkNotNullParameter(element, "element");
                Intrinsics.checkNotNullParameter(file, "file");
                throw new IllegalStateException("Should not be called".toString());
            }
        };

        private Companion() {
        }

        public final PartialLinkageSupportForLowerings getDISABLED() {
            return DISABLED;
        }
    }
}
