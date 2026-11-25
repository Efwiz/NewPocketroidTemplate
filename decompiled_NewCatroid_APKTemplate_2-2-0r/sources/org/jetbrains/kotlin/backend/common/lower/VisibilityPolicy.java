package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;

/* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \r2\u00020\u0001:\u0001\rJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "", "forCapturedField", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "value", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "forClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "inInlineFunctionScope", "", "forConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Companion", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface VisibilityPolicy {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    default DescriptorVisibility forClass(IrClass declaration, boolean inInlineFunctionScope) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return declaration.getVisibility();
    }

    default DescriptorVisibility forConstructor(IrConstructor declaration, boolean inInlineFunctionScope) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        return PRIVATE;
    }

    default DescriptorVisibility forCapturedField(IrValueSymbol value) {
        Intrinsics.checkNotNullParameter(value, "value");
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        return PRIVATE;
    }

    /* JADX INFO: compiled from: LocalDeclarationsLowering.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy$Companion;", "", "()V", "DEFAULT", "Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "getDEFAULT", "()Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final VisibilityPolicy DEFAULT = new VisibilityPolicy() { // from class: org.jetbrains.kotlin.backend.common.lower.VisibilityPolicy$Companion$DEFAULT$1
        };

        private Companion() {
        }

        public final VisibilityPolicy getDEFAULT() {
            return DEFAULT;
        }
    }
}
