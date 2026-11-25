package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;

/* JADX INFO: compiled from: ClosureGenerationStrategy.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ClosureGenerationStrategy;", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy$FunctionDefault;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/psi/KtDeclarationWithBody;", "doGenerateBody", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClosureGenerationStrategy extends FunctionGenerationStrategy.FunctionDefault {
    private final KtDeclarationWithBody declaration;

    public final KtDeclarationWithBody getDeclaration() {
        return this.declaration;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClosureGenerationStrategy(GenerationState state, KtDeclarationWithBody declaration) {
        super(state, declaration);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        this.declaration = declaration;
    }

    @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.FunctionDefault, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
    public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(signature, "signature");
        List<ValueParameterDescriptor> valueParameters = codegen.context.getFunctionDescriptor().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        CodegenUtilKt.initializeVariablesForDestructuredLambdaParameters(codegen, valueParameters, codegen.context.getMethodEndLabel());
        KtDeclarationWithBody ktDeclarationWithBody = this.declaration;
        if (ktDeclarationWithBody instanceof KtFunctionLiteral) {
            CodegenUtilKt.recordCallLabelForLambdaArgument((KtFunctionLiteral) ktDeclarationWithBody, this.state.getBindingTrace());
        }
        super.doGenerateBody(codegen, signature);
    }
}
