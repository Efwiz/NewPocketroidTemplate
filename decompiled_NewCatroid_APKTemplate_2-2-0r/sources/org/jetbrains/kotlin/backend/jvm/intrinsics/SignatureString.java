package org.jetbrains.kotlin.backend.jvm.intrinsics;

import com.android.dx.rop.code.RegisterSpec;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: SignatureString.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0000¢\u0006\u0002\b\u000bJ \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/SignatureString;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "generateSignatureString", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "generateSignatureString$backend_jvm_codegen", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignatureString extends IntrinsicMethod {
    public static final SignatureString INSTANCE = new SignatureString();

    private SignatureString() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrStatement");
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.generateSequence(valueArgument, new Function1<IrStatement, IrStatement>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.SignatureString$invoke$argument$1
            @Override // kotlin.jvm.functions.Function1
            public final IrStatement invoke(IrStatement it) {
                List<IrStatement> statements;
                Intrinsics.checkNotNullParameter(it, "it");
                IrBlock irBlock = it instanceof IrBlock ? (IrBlock) it : null;
                if (irBlock == null || (statements = irBlock.getStatements()) == null) {
                    return null;
                }
                return (IrStatement) CollectionsKt.lastOrNull((List) statements);
            }
        }), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.SignatureString$invoke$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrFunctionReference);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        generateSignatureString$backend_jvm_codegen(codegen.getMv(), ((IrFunctionReference) SequencesKt.single(sequenceFilter)).getSymbol().getOwner(), codegen.getClassCodegen());
        Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
        Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
        return new MaterialValue(codegen, JAVA_STRING_TYPE, codegen.getContext().getIrBuiltIns().getStringType());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Object, org.jetbrains.kotlin.ir.declarations.IrFunction] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.Object, org.jetbrains.kotlin.ir.declarations.IrFunction] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v2, types: [org.jetbrains.kotlin.ir.declarations.IrFunction] */
    /* JADX WARN: Type inference failed for: r5v1, types: [org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper] */
    public final void generateSignatureString$backend_jvm_codegen(InstructionAdapter v, IrFunction function, ClassCodegen codegen) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        boolean z = function instanceof IrSimpleFunction;
        ?? r4 = function;
        if (z) {
            r4 = (IrFunction) CollectionsKt.first(IrFakeOverrideUtilsKt.collectRealOverrides$default((IrOverridableDeclaration) function, null, null, 3, null));
        }
        boolean zIsSuspend = AdditionalIrUtilsKt.isSuspend(r4);
        ?? r5 = r4;
        if (zIsSuspend && (irSimpleFunction = codegen.getContext().getSuspendFunctionOriginalToView().get(r4)) != null) {
            r5 = r4;
            r5 = irSimpleFunction;
        }
        r5 = r4;
        Method methodMapAsmMethod = codegen.getMethodSignatureMapper().mapAsmMethod(r5);
        v.aconst(methodMapAsmMethod.getName() + methodMapAsmMethod.getDescriptor());
    }
}
