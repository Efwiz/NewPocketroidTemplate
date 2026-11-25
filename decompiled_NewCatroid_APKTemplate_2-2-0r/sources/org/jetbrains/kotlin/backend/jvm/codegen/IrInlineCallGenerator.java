package org.jetbrains.kotlin.backend.jvm.codegen;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrCallableMethod;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.inline.GlobalInlineContext;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReporterWithContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;

/* JADX INFO: compiled from: IrInlineCallGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0010J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J(\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineCallGenerator;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrCallGenerator;", "genCall", "", "callableMethod", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrCallableMethod;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "isInsideIfCondition", "", "genCycleStub", "text", "", "genInlineCall", "IrInlineFunctionSource", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrInlineCallGenerator extends IrCallGenerator {
    void genInlineCall(IrCallableMethod callableMethod, ExpressionCodegen codegen, IrFunctionAccessExpression expression, boolean isInsideIfCondition);

    @Override // org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator
    default void genCall(IrCallableMethod callableMethod, final ExpressionCodegen codegen, IrFunctionAccessExpression expression, boolean isInsideIfCondition) {
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (!codegen.getState().getGlobalInlineContext().enterIntoInlining(IrBasedDescriptorsKt.toIrBasedDescriptor(JvmIrUtilsKt.suspendFunctionOriginal(expression.getSymbol().getOwner())), new IrInlineFunctionSource(expression), new Function2<GlobalInlineContext.InlineFunctionSource, CallableDescriptor, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.IrInlineCallGenerator.genCall.1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(GlobalInlineContext.InlineFunctionSource inlineFunctionSource, CallableDescriptor callableDescriptor) {
                invoke2(inlineFunctionSource, callableDescriptor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(GlobalInlineContext.InlineFunctionSource reportOn, CallableDescriptor callee) {
                Intrinsics.checkNotNullParameter(reportOn, "reportOn");
                Intrinsics.checkNotNullParameter(callee, "callee");
                KtDiagnosticReporterWithContext.DiagnosticContextImpl diagnosticContextImplAt = codegen.getContext().getKtDiagnosticReporter().at(((IrInlineFunctionSource) reportOn).getIr(), JvmIrUtilsKt.getFileParent(codegen.getIrFunction()));
                KtDiagnosticFactory1<Name> inline_call_cycle = JvmBackendErrors.INSTANCE.getINLINE_CALL_CYCLE();
                Name name2 = callee.getShortName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                diagnosticContextImplAt.report(inline_call_cycle, name2);
            }
        })) {
            PsiElement psiElement = JvmIrUtilsKt.getPsiElement(expression);
            String text = psiElement != null ? psiElement.getText() : null;
            if (text == null) {
                text = "<no source>";
            }
            genCycleStub(text, codegen);
            return;
        }
        try {
            genInlineCall(callableMethod, codegen, expression, isInsideIfCondition);
        } finally {
            codegen.getState().getGlobalInlineContext().exitFromInlining();
        }
    }

    default void genCycleStub(String text, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        AsmUtil.genThrow(codegen.getVisitor(), "java/lang/UnsupportedOperationException", "Call is a part of inline call cycle: " + text);
    }

    /* JADX INFO: compiled from: IrInlineCallGenerator.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrInlineCallGenerator$IrInlineFunctionSource;", "Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext$InlineFunctionSource;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/IrElement;", "(Lorg/jetbrains/kotlin/ir/IrElement;)V", "getIr", "()Lorg/jetbrains/kotlin/ir/IrElement;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IrInlineFunctionSource extends GlobalInlineContext.InlineFunctionSource {
        private final IrElement ir;

        public IrInlineFunctionSource(IrElement ir) {
            Intrinsics.checkNotNullParameter(ir, "ir");
            this.ir = ir;
        }

        public final IrElement getIr() {
            return this.ir;
        }
    }
}
