package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ArgumentAndDeclIndex;
import org.jetbrains.kotlin.codegen.CallGenerator;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.JvmKotlinType;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.ValueKind;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: InlineCodegenForDefaultBody.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ.\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J*\u0010\u001b\u001a\u00020\u00142\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0016J\b\u0010$\u001a\u00020\u0014H\u0016J \u0010%\u001a\u00020\u00142\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020#H\u0016J\b\u0010+\u001a\u00020\u0014H\u0016J(\u0010,\u001a\u00020\u00142\u0006\u0010 \u001a\u00020!2\u0006\u0010-\u001a\u00020'2\u0006\u0010.\u001a\u00020/2\u0006\u0010\"\u001a\u00020#H\u0016J$\u00100\u001a\u00020\u00142\f\u00101\u001a\b\u0012\u0004\u0012\u000203022\f\u00104\u001a\b\u0012\u0004\u0012\u00020)02H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u00065"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineCodegenForDefaultBody;", "Lorg/jetbrains/kotlin/codegen/CallGenerator;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "sourceCompilerForInline", "Lorg/jetbrains/kotlin/codegen/inline/PsiSourceCompilerForInline;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/codegen/inline/PsiSourceCompilerForInline;)V", "methodStartLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "genCallInner", "", "callableMethod", "Lorg/jetbrains/kotlin/codegen/Callable;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "callDefault", "", "genValueAndPut", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "parameterType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "parameterIndex", "", "processHiddenParameters", "putCapturedValueOnStack", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "valueType", "Lorg/jetbrains/org/objectweb/asm/Type;", "paramIndex", "putHiddenParamsIntoLocals", "putValueIfNeeded", "value", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/ValueKind;", "reorderArgumentsIfNeeded", "actualArgsWithDeclIndex", "", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", "valueParameterTypes", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineCodegenForDefaultBody implements CallGenerator {
    private final FunctionDescriptor function;
    private final JvmMethodSignature jvmSignature;
    private final Label methodStartLabel;
    private final PsiSourceCompilerForInline sourceCompilerForInline;
    private final SourceMapper sourceMapper;
    private final GenerationState state;

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putValueIfNeeded(JvmKotlinType parameterType, StackValue value, ValueKind kind, int parameterIndex) {
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(kind, "kind");
    }

    public InlineCodegenForDefaultBody(FunctionDescriptor function, ExpressionCodegen codegen, GenerationState state, JvmMethodSignature jvmSignature, PsiSourceCompilerForInline sourceCompilerForInline) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(jvmSignature, "jvmSignature");
        Intrinsics.checkNotNullParameter(sourceCompilerForInline, "sourceCompilerForInline");
        this.function = function;
        this.state = state;
        this.jvmSignature = jvmSignature;
        this.sourceCompilerForInline = sourceCompilerForInline;
        SourceMapper orCreateSourceMapper = codegen.getParentCodegen().getOrCreateSourceMapper();
        Intrinsics.checkNotNullExpressionValue(orCreateSourceMapper, "getOrCreateSourceMapper(...)");
        this.sourceMapper = orCreateSourceMapper;
        Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
        this.methodStartLabel = labelLinkedLabel;
        InlineUtil.isInline(function);
        codegen.v.visitLabel(labelLinkedLabel);
    }

    public final GenerationState getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void genCallInner(Callable callableMethod, ResolvedCall<?> resolvedCall, boolean callDefault, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        SMAPAndMethodNode sMAPAndMethodNodeCompileInlineFunction = this.sourceCompilerForInline.compileInlineFunction(this.jvmSignature);
        MethodNode node = sMAPAndMethodNodeCompileInlineFunction.getNode();
        SourceMapCopier sourceMapCopier = new SourceMapCopier(this.sourceMapper, sMAPAndMethodNodeCompileInlineFunction.getClassSMAP(), null, 4, null);
        int argumentsAndReturnSizes = (Type.getArgumentsAndReturnSizes(this.jvmSignature.getAsmMethod().getDescriptor()) >>> 2) - (callableMethod.isStaticCall() ? 1 : 0);
        InlineCodegenUtilsKt.preprocessSuspendMarkers(node, true, false);
        node.accept(new MethodBodyVisitor(argumentsAndReturnSizes, this, sourceMapCopier, codegen.getVisitor()) { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenForDefaultBody.genCallInner.2
            final /* synthetic */ int $argsSize;
            final /* synthetic */ SourceMapCopier $childSourceMapper;
            final /* synthetic */ InlineCodegenForDefaultBody this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(instructionAdapter);
                Intrinsics.checkNotNull(instructionAdapter);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLocalVariable(String name2, String desc, String signature, Label start, Label end, int index) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                Intrinsics.checkNotNullParameter(start, "start");
                Intrinsics.checkNotNullParameter(end, "end");
                if (index < this.$argsSize) {
                    start = this.this$0.methodStartLabel;
                }
                super.visitLocalVariable(name2, desc, signature, start, end, index);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLineNumber(int line, Label start) {
                Intrinsics.checkNotNullParameter(start, "start");
                super.visitLineNumber(this.$childSourceMapper.mapLineNumber(line), start);
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void genValueAndPut(ValueParameterDescriptor valueParameterDescriptor, KtExpression argumentExpression, JvmKotlinType parameterType, int parameterIndex) {
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        throw new UnsupportedOperationException("Shouldn't be called");
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putCapturedValueOnStack(StackValue stackValue, Type valueType, int paramIndex) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        throw new UnsupportedOperationException("Shouldn't be called");
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void processHiddenParameters() {
        throw new UnsupportedOperationException("Shouldn't be called");
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putHiddenParamsIntoLocals() {
        throw new UnsupportedOperationException("Shouldn't be called");
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> actualArgsWithDeclIndex, List<Type> valueParameterTypes) {
        Intrinsics.checkNotNullParameter(actualArgsWithDeclIndex, "actualArgsWithDeclIndex");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
        throw new UnsupportedOperationException("Shouldn't be called");
    }
}
