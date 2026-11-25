package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageCase;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageLogger;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.linkage.partial.PartiallyLinkedDeclarationOrigin;

/* JADX INFO: compiled from: PartialLinkageSupportForLoweringsImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0010\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J(\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\rH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLoweringsImpl;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "logger", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageLogger;)V", "<set-?>", "", "errorMessagesRendered", "getErrorMessagesRendered", "()I", "isEnabled", "", "()Z", "throwExpressionsGenerated", "getThrowExpressionsGenerated", "renderAndLogLinkageError", "", "partialLinkageCase", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "file", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$File;", "renderLinkageError", "throwLinkageError", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "doNotLog", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageSupportForLoweringsImpl implements PartialLinkageSupportForLowerings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final IrBuiltIns builtIns;
    private int errorMessagesRendered;
    private final PartialLinkageLogger logger;
    private int throwExpressionsGenerated;

    @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings
    public boolean isEnabled() {
        return true;
    }

    public PartialLinkageSupportForLoweringsImpl(IrBuiltIns builtIns, PartialLinkageLogger logger) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.builtIns = builtIns;
        this.logger = logger;
    }

    public final int getThrowExpressionsGenerated() {
        return this.throwExpressionsGenerated;
    }

    public final int getErrorMessagesRendered() {
        return this.errorMessagesRendered;
    }

    @Override // org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings
    public IrCall throwLinkageError(PartialLinkageCase partialLinkageCase, IrElement element, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file, boolean doNotLog) {
        String strRenderAndLogLinkageError;
        Intrinsics.checkNotNullParameter(partialLinkageCase, "partialLinkageCase");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(file, "file");
        if (doNotLog) {
            strRenderAndLogLinkageError = renderLinkageError(partialLinkageCase);
        } else {
            strRenderAndLogLinkageError = renderAndLogLinkageError(partialLinkageCase, element, file);
        }
        this.throwExpressionsGenerated++;
        IrCallImpl irCallImpl = new IrCallImpl(element.getStartOffset(), element.getEndOffset(), this.builtIns.getNothingType(), this.builtIns.getLinkageErrorSymbol(), 0, 1, IrStatementOrigin.PARTIAL_LINKAGE_RUNTIME_ERROR.INSTANCE, null, 128, null);
        irCallImpl.putValueArgument(0, IrConstImpl.INSTANCE.string(irCallImpl.getStartOffset(), irCallImpl.getEndOffset(), this.builtIns.getStringType(), strRenderAndLogLinkageError));
        return irCallImpl;
    }

    public final String renderAndLogLinkageError(PartialLinkageCase partialLinkageCase, IrElement element, org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageUtils.File file) {
        Intrinsics.checkNotNullParameter(partialLinkageCase, "partialLinkageCase");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(file, "file");
        String strRenderLinkageError = renderLinkageError(partialLinkageCase);
        this.logger.log(strRenderLinkageError, file.computeLocationForOffset(INSTANCE.startOffsetOfFirstDenotableIrElement(element)));
        return strRenderLinkageError;
    }

    private final String renderLinkageError(PartialLinkageCase partialLinkageCase) {
        this.errorMessagesRendered++;
        return PartialLinkageErrorMessagesKt.renderLinkageError(partialLinkageCase);
    }

    /* JADX INFO: compiled from: PartialLinkageSupportForLoweringsImpl.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0082\u0010¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageSupportForLoweringsImpl$Companion;", "", "()V", "startOffsetOfFirstDenotableIrElement", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int startOffsetOfFirstDenotableIrElement(IrElement irElement) {
            while (!(irElement instanceof IrPackageFragment)) {
                if (!(irElement instanceof IrDeclaration)) {
                    return irElement.getStartOffset();
                }
                IrDeclaration irDeclaration = (IrDeclaration) irElement;
                if (irDeclaration.getOrigin() instanceof PartiallyLinkedDeclarationOrigin) {
                    irElement = irDeclaration.getParent();
                } else {
                    return irElement.getStartOffset();
                }
            }
            return -1;
        }
    }
}
