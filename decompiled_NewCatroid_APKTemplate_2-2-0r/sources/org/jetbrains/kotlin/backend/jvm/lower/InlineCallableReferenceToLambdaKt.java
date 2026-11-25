package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: InlineCallableReferenceToLambda.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\r\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"0\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u00020\n*\u0004\u0018\u00010\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\f¨\u0006\u0010"}, d2 = {"STUB_FOR_INLINING", "", "inlineCallableReferenceToLambdaPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getInlineCallableReferenceToLambdaPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "isInlinable", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)Z", "replaceOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "origin", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineCallableReferenceToLambdaKt {
    public static final String STUB_FOR_INLINING = "stub_for_inlining";
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> inlineCallableReferenceToLambdaPhase = PhaseBuildersKt.makeIrFilePhase(InlineCallableReferenceToLambdaKt$inlineCallableReferenceToLambdaPhase$1.INSTANCE, "InlineCallableReferenceToLambdaPhase", "Transform callable reference to inline lambdas, mark inline lambdas for later passes", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getInlineCallableReferenceToLambdaPhase() {
        return inlineCallableReferenceToLambdaPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isInlinable(IrStatementOrigin irStatementOrigin) {
        return IrUtilsKt.isLambda(irStatementOrigin) || Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.ADAPTED_FUNCTION_REFERENCE.INSTANCE) || Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.SUSPEND_CONVERSION.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrFunctionReference replaceOrigin(IrFunctionReference irFunctionReference, IrStatementOrigin irStatementOrigin) {
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(irFunctionReference.getStartOffset(), irFunctionReference.getEndOffset(), irFunctionReference.getType(), irFunctionReference.getSymbol(), irFunctionReference.getTypeArgumentsCount(), irFunctionReference.getValueArgumentsCount(), irFunctionReference.getReflectionTarget(), irStatementOrigin);
        IrDeclarationsKt.copyAttributes(irFunctionReferenceImpl, irFunctionReference);
        IrUtilsKt.copyTypeAndValueArgumentsFrom$default(irFunctionReferenceImpl, irFunctionReference, false, false, 6, null);
        return irFunctionReferenceImpl;
    }
}
