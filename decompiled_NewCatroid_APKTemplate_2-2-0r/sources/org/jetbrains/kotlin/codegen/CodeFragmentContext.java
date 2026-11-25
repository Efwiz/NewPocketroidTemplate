package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.context.LocalLookup;
import org.jetbrains.kotlin.codegen.context.ScriptLikeContext;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: CodeFragmentCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000?\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\f\b\u0002\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001a\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001a\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u0005H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodeFragmentContext;", "Lorg/jetbrains/kotlin/codegen/context/ScriptLikeContext;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "contextDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "calculatedInfo", "Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;", "(Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/codegen/context/CodegenContext;Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;)V", "localLookup", "org/jetbrains/kotlin/codegen/CodeFragmentContext$localLookup$1", "Lorg/jetbrains/kotlin/codegen/CodeFragmentContext$localLookup$1;", "captureVariable", "Lorg/jetbrains/kotlin/codegen/StackValue;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/MutableClosure;", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getOuterReceiverExpression", "prefix", "thisOrOuterClass", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CodeFragmentContext extends ScriptLikeContext {
    private final CalculatedCodeFragmentCodegenInfo calculatedInfo;
    private final CodeFragmentContext$localLookup$1 localLookup;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.jetbrains.kotlin.codegen.CodeFragmentContext$localLookup$1] */
    public CodeFragmentContext(KotlinTypeMapper typeMapper, ClassDescriptor contextDescriptor, CodegenContext<?> codegenContext, CalculatedCodeFragmentCodegenInfo calculatedInfo) {
        super(typeMapper, contextDescriptor, codegenContext);
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(contextDescriptor, "contextDescriptor");
        Intrinsics.checkNotNullParameter(calculatedInfo, "calculatedInfo");
        this.calculatedInfo = calculatedInfo;
        this.localLookup = new LocalLookup() { // from class: org.jetbrains.kotlin.codegen.CodeFragmentContext$localLookup$1
            @Override // org.jetbrains.kotlin.codegen.context.LocalLookup
            public boolean isLocal(DeclarationDescriptor descriptor) {
                List<CalculatedCodeFragmentCodegenInfo.CalculatedParameter> parameters = this.this$0.calculatedInfo.getParameters();
                if ((parameters instanceof Collection) && parameters.isEmpty()) {
                    return false;
                }
                for (CalculatedCodeFragmentCodegenInfo.CalculatedParameter calculatedParameter : parameters) {
                    if (Intrinsics.areEqual(descriptor, calculatedParameter.getTargetDescriptor()) || Intrinsics.areEqual(descriptor, calculatedParameter.getParameterDescriptor())) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override // org.jetbrains.kotlin.codegen.context.ScriptLikeContext
    public StackValue getOuterReceiverExpression(StackValue prefix, ClassDescriptor thisOrOuterClass) {
        Intrinsics.checkNotNullParameter(thisOrOuterClass, "thisOrOuterClass");
        CalculatedCodeFragmentCodegenInfo.CalculatedParameter calculatedParameterFindParameter = this.calculatedInfo.findParameter(thisOrOuterClass);
        if (calculatedParameterFindParameter == null) {
            throw new IllegalStateException("Can not generate outer receiver value for " + thisOrOuterClass);
        }
        return calculatedParameterFindParameter.getStackValue();
    }

    @Override // org.jetbrains.kotlin.codegen.context.ScriptLikeContext
    public StackValue captureVariable(MutableClosure closure, DeclarationDescriptor target) {
        Intrinsics.checkNotNullParameter(closure, "closure");
        Intrinsics.checkNotNullParameter(target, "target");
        CalculatedCodeFragmentCodegenInfo.CalculatedParameter calculatedParameterFindParameter = this.calculatedInfo.findParameter(target);
        if (calculatedParameterFindParameter == null) {
            return null;
        }
        ValueParameterDescriptor parameterDescriptor = calculatedParameterFindParameter.getParameterDescriptor();
        EnclosedValueDescriptor enclosedValueDescriptor = closure.getCaptureVariables().get(parameterDescriptor);
        if (enclosedValueDescriptor == null) {
            KotlinTypeMapper typeMapper = getTypeMapper();
            ClassDescriptor closureClass = closure.getClosureClass();
            Intrinsics.checkNotNullExpressionValue(closureClass, "getClosureClass(...)");
            return LocalLookup.LocalLookupCase.VAR.innerValue(parameterDescriptor, this.localLookup, getState(), closure, typeMapper.mapType(closureClass));
        }
        return enclosedValueDescriptor.getInnerValue();
    }
}
