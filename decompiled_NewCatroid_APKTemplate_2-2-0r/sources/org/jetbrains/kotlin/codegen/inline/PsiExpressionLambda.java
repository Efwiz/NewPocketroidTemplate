package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.PropertyReferenceCodegen;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: PsiInlineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020!¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u001c\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u000eR\u0016\u0010'\u001a\u0004\u0018\u00010%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b(\u0010)R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010*R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010*R\u0011\u0010+\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b+\u0010*R\u0014\u0010,\u001a\u00020-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0013\u00100\u001a\u0004\u0018\u000101¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\"\u00104\u001a\u0010\u0012\u0004\u0012\u000206\u0012\u0006\u0012\u0004\u0018\u00010705X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiExpressionLambda;", "Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isCrossInline", "", "isBoundCallableReference", "(Lorg/jetbrains/kotlin/psi/KtExpression;Lorg/jetbrains/kotlin/codegen/state/GenerationState;ZZ)V", "capturedVars", "", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "getCapturedVars", "()Ljava/util/List;", "capturedVars$delegate", "Lkotlin/Lazy;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/CalculatedClosure;", "getClosure", "()Lorg/jetbrains/kotlin/codegen/binding/CalculatedClosure;", "functionWithBodyOrCallableReference", "getFunctionWithBodyOrCallableReference", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "invokeMethod", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "getInvokeMethod", "()Lorg/jetbrains/org/objectweb/asm/commons/Method;", "invokeMethodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getInvokeMethodDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "invokeMethodParameters", "Lorg/jetbrains/kotlin/types/KotlinType;", "getInvokeMethodParameters", "invokeMethodReturnType", "getInvokeMethodReturnType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "()Z", "isPropertyReference", "lambdaClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "getLambdaClassType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "propertyReferenceInfo", "Lorg/jetbrains/kotlin/codegen/inline/PropertyReferenceInfo;", "getPropertyReferenceInfo", "()Lorg/jetbrains/kotlin/codegen/inline/PropertyReferenceInfo;", "returnLabels", "", "", "Lorg/jetbrains/org/objectweb/asm/Label;", "getReturnLabels", "()Ljava/util/Map;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiExpressionLambda extends ExpressionLambda {

    /* JADX INFO: renamed from: capturedVars$delegate, reason: from kotlin metadata */
    private final Lazy capturedVars;
    private final ClassDescriptor classDescriptor;
    private final CalculatedClosure closure;
    private final KtExpression functionWithBodyOrCallableReference;
    private final Method invokeMethod;
    private final FunctionDescriptor invokeMethodDescriptor;
    private final boolean isBoundCallableReference;
    private final boolean isCrossInline;
    private final Type lambdaClassType;
    private final PropertyReferenceInfo propertyReferenceInfo;
    private final Map<String, Label> returnLabels;
    private final GenerationState state;

    /* JADX INFO: renamed from: isCrossInline, reason: from getter */
    public final boolean getIsCrossInline() {
        return this.isCrossInline;
    }

    /* JADX INFO: renamed from: isBoundCallableReference, reason: from getter */
    public final boolean getIsBoundCallableReference() {
        return this.isBoundCallableReference;
    }

    public PsiExpressionLambda(KtExpression expression, GenerationState state, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.isCrossInline = z;
        this.isBoundCallableReference = z2;
        KtLambdaExpression ktLambdaExpression = expression instanceof KtLambdaExpression ? (KtLambdaExpression) expression : null;
        KtFunctionLiteral functionLiteral = ktLambdaExpression != null ? ktLambdaExpression.getFunctionLiteral() : null;
        KtFunctionLiteral ktFunctionLiteral = functionLiteral == null ? expression : functionLiteral;
        this.functionWithBodyOrCallableReference = ktFunctionLiteral;
        BindingContext bindingContext = state.getBindingContext();
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) bindingContext.get(BindingContext.FUNCTION, ktFunctionLiteral);
        if (simpleFunctionDescriptor == null && (expression instanceof KtCallableReferenceExpression)) {
            Object obj = bindingContext.get(BindingContext.VARIABLE, ktFunctionLiteral);
            VariableDescriptorWithAccessors variableDescriptorWithAccessors = obj instanceof VariableDescriptorWithAccessors ? (VariableDescriptorWithAccessors) obj : null;
            if (variableDescriptorWithAccessors == null) {
                throw new AssertionError("Reference expression not resolved to variable descriptor with accessors: " + expression.getText());
            }
            ClassDescriptor classDescriptor = (ClassDescriptor) bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, variableDescriptorWithAccessors);
            if (classDescriptor == null) {
                throw new IllegalStateException("Class for callable not found: " + variableDescriptorWithAccessors + '\n' + ((KtCallableReferenceExpression) expression).getText());
            }
            this.classDescriptor = classDescriptor;
            this.lambdaClassType = state.getTypeMapper().mapClass(classDescriptor);
            SimpleFunctionDescriptor simpleFunctionDescriptorFindGetFunction = PropertyReferenceCodegen.INSTANCE.findGetFunction(variableDescriptorWithAccessors);
            this.invokeMethodDescriptor = PropertyReferenceCodegen.INSTANCE.createFakeOpenDescriptor(simpleFunctionDescriptorFindGetFunction, classDescriptor);
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) expression).getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            CallableDescriptor resultingDescriptor = CallUtilKt.getResolvedCallWithAssert(callableReference, bindingContext).getResultingDescriptor();
            Intrinsics.checkNotNull(resultingDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptor");
            this.propertyReferenceInfo = new PropertyReferenceInfo((VariableDescriptor) resultingDescriptor, simpleFunctionDescriptorFindGetFunction);
        } else {
            this.propertyReferenceInfo = null;
            if (simpleFunctionDescriptor != null) {
                SimpleFunctionDescriptor simpleFunctionDescriptor2 = simpleFunctionDescriptor;
                this.invokeMethodDescriptor = simpleFunctionDescriptor2;
                ClassDescriptor classDescriptor2 = (ClassDescriptor) bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, simpleFunctionDescriptor2);
                if (classDescriptor2 == null) {
                    throw new IllegalStateException("Class for invoke method not found: " + simpleFunctionDescriptor2 + '\n' + expression.getText());
                }
                this.classDescriptor = classDescriptor2;
                Type typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(bindingContext, simpleFunctionDescriptor2);
                Intrinsics.checkNotNullExpressionValue(typeAsmTypeForAnonymousClass, "asmTypeForAnonymousClass(...)");
                this.lambdaClassType = typeAsmTypeForAnonymousClass;
            } else {
                throw new AssertionError("Function is not resolved to descriptor: " + expression.getText());
            }
        }
        MutableClosure mutableClosure = (MutableClosure) bindingContext.get(CodegenBinding.CLOSURE, this.classDescriptor);
        if (mutableClosure == null) {
            throw new AssertionError("null closure for lambda " + expression.getText());
        }
        this.closure = mutableClosure;
        Set<String> declarationLabels = PsiSourceCompilerForInlineKt.getDeclarationLabels(expression, this.invokeMethodDescriptor);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(declarationLabels, 10)), 16));
        for (Object obj2 : declarationLabels) {
            linkedHashMap.put(obj2, null);
        }
        this.returnLabels = linkedHashMap;
        this.invokeMethod = this.state.getTypeMapper().mapAsmMethod(this.invokeMethodDescriptor);
        this.capturedVars = LazyKt.lazy(new Function0<ArrayList<CapturedParamDesc>>() { // from class: org.jetbrains.kotlin.codegen.inline.PsiExpressionLambda$capturedVars$2
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:20:0x00dd  */
            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<CapturedParamDesc> invoke() {
                boolean z3;
                ArrayList<CapturedParamDesc> arrayList = new ArrayList<>();
                PsiExpressionLambda psiExpressionLambda = this.this$0;
                ClassDescriptor capturedOuterClassDescriptor = psiExpressionLambda.getClosure().getCapturedOuterClassDescriptor();
                if (capturedOuterClassDescriptor != null) {
                    KotlinTypeMapper typeMapper = psiExpressionLambda.state.getTypeMapper();
                    SimpleType defaultType = capturedOuterClassDescriptor.getDefaultType();
                    Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                    arrayList.add(LambdaInfo.INSTANCE.capturedParamDesc(psiExpressionLambda, AsmUtil.CAPTURED_THIS_FIELD, KotlinTypeMapper.mapType$default(typeMapper, defaultType, null, null, 6, null), false));
                }
                KotlinType capturedReceiverFromOuterContext = psiExpressionLambda.getClosure().getCapturedReceiverFromOuterContext();
                if (capturedReceiverFromOuterContext != null) {
                    String capturedReceiverFieldName = psiExpressionLambda.getClosure().getCapturedReceiverFieldName(psiExpressionLambda.state.getTypeMapper().getBindingContext(), psiExpressionLambda.state.getLanguageVersionSettings());
                    Intrinsics.checkNotNullExpressionValue(capturedReceiverFieldName, "getCapturedReceiverFieldName(...)");
                    arrayList.add(LambdaInfo.INSTANCE.capturedParamDesc(psiExpressionLambda, capturedReceiverFieldName, psiExpressionLambda.getIsBoundCallableReference() ? psiExpressionLambda.state.getTypeMapper().mapType(capturedReceiverFromOuterContext, null, TypeMappingMode.GENERIC_ARGUMENT) : KotlinTypeMapper.mapType$default(psiExpressionLambda.state.getTypeMapper(), capturedReceiverFromOuterContext, null, null, 6, null), false));
                }
                Map<DeclarationDescriptor, EnclosedValueDescriptor> captureVariables = psiExpressionLambda.getClosure().getCaptureVariables();
                Intrinsics.checkNotNullExpressionValue(captureVariables, "getCaptureVariables(...)");
                for (Map.Entry<DeclarationDescriptor, EnclosedValueDescriptor> entry : captureVariables.entrySet()) {
                    DeclarationDescriptor key = entry.getKey();
                    EnclosedValueDescriptor value = entry.getValue();
                    if (key instanceof ValueParameterDescriptor) {
                        KotlinType type = ((ValueParameterDescriptor) key).getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        if (FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type)) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                    } else {
                        z3 = false;
                    }
                    String fieldName = value.getFieldName();
                    Intrinsics.checkNotNullExpressionValue(fieldName, "getFieldName(...)");
                    Type type2 = value.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    arrayList.add(LambdaInfo.INSTANCE.capturedParamDesc(psiExpressionLambda, fieldName, type2, z3));
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Type getLambdaClassType() {
        return this.lambdaClassType;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Method getInvokeMethod() {
        return this.invokeMethod;
    }

    public final FunctionDescriptor getInvokeMethodDescriptor() {
        return this.invokeMethodDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<KotlinType> getInvokeMethodParameters() {
        FunctionDescriptor orCreateJvmSuspendFunctionView;
        if (this.invokeMethodDescriptor.isSuspend()) {
            orCreateJvmSuspendFunctionView = CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(this.invokeMethodDescriptor, this.state);
        } else {
            orCreateJvmSuspendFunctionView = this.invokeMethodDescriptor;
        }
        List explicitParameters = PsiInlineCodegenKt.getExplicitParameters(orCreateJvmSuspendFunctionView);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10));
        Iterator it = explicitParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((ParameterDescriptor) it.next()).getReturnType());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public KotlinType getInvokeMethodReturnType() {
        return this.invokeMethodDescriptor.getReturnType();
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    public final PropertyReferenceInfo getPropertyReferenceInfo() {
        return this.propertyReferenceInfo;
    }

    public final KtExpression getFunctionWithBodyOrCallableReference() {
        return this.functionWithBodyOrCallableReference;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public Map<String, Label> getReturnLabels() {
        return this.returnLabels;
    }

    public final CalculatedClosure getClosure() {
        return this.closure;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.LambdaInfo
    public List<CapturedParamDesc> getCapturedVars() {
        return (List) this.capturedVars.getValue();
    }

    public final boolean isPropertyReference() {
        return this.propertyReferenceInfo != null;
    }
}
