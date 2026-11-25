package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ArgumentAndDeclIndex;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CallGenerator;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.JvmKotlinType;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.ValueKind;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.org.objectweb.asm.Type;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: PsiInlineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001NBS\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J.\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010&2\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0004\u001a\u00020\u0002H\u0016J*\u0010)\u001a\u00020\"2\b\u0010*\u001a\u0004\u0018\u00010+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020 H\u0016J\b\u00101\u001a\u00020\"H\u0014J\u0010\u00102\u001a\u00020(2\u0006\u00103\u001a\u00020+H\u0002J\u0010\u00104\u001a\u00020(2\u0006\u00103\u001a\u00020+H\u0002J\b\u00105\u001a\u00020(H\u0014J\b\u00106\u001a\u00020\"H\u0016J \u00107\u001a\u00020\"2\u0006\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00112\u0006\u0010;\u001a\u00020 H\u0016J\u001a\u0010<\u001a\u00020\"2\u0006\u0010=\u001a\u00020\u00172\b\u0010>\u001a\u0004\u0018\u000109H\u0002J\b\u0010?\u001a\u00020\"H\u0016J(\u0010@\u001a\u00020\"2\u0006\u0010.\u001a\u00020/2\u0006\u0010A\u001a\u0002092\u0006\u0010B\u001a\u00020C2\u0006\u00100\u001a\u00020 H\u0016J\u0016\u0010D\u001a\u00020(2\f\u0010%\u001a\b\u0012\u0002\b\u0003\u0018\u00010&H\u0002J \u0010E\u001a\u00020\"2\u0006\u0010F\u001a\u00020-2\u0006\u0010G\u001a\u00020\u00112\u0006\u0010H\u001a\u00020+H\u0002J$\u0010I\u001a\u00020\"2\f\u0010J\u001a\b\u0012\u0004\u0012\u00020L0K2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00110KH\u0016R\"\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00170\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u001d\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020 0\u001e0\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiInlineCodegen;", "Lorg/jetbrains/kotlin/codegen/inline/InlineCodegen;", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "Lorg/jetbrains/kotlin/codegen/CallGenerator;", "codegen", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "typeParameterMappings", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "Lorg/jetbrains/kotlin/types/KotlinType;", "sourceCompiler", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "methodOwner", "Lorg/jetbrains/org/objectweb/asm/Type;", "actualDispatchReceiver", "reportErrorsOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/psi/KtElement;)V", "<set-?>", "Lorg/jetbrains/kotlin/codegen/inline/PsiExpressionLambda;", "activeLambda", "getActiveLambda", "()Lorg/jetbrains/kotlin/codegen/inline/PsiExpressionLambda;", "closuresToGenerate", "", "hiddenParameters", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "", "genCallInner", "", "callableMethod", "Lorg/jetbrains/kotlin/codegen/Callable;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "callDefault", "", "genValueAndPut", "valueParameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "argumentExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "parameterType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "parameterIndex", "generateAssertField", "isCallSiteIsSuspend", "descriptor", "isInlineSuspendParameter", "isInlinedToInlineFunInKotlinRuntime", "processHiddenParameters", "putCapturedValueOnStack", "stackValue", "Lorg/jetbrains/kotlin/codegen/StackValue;", "valueType", "paramIndex", "putClosureParametersOnStack", ES6Iterator.NEXT_METHOD, "receiverValue", "putHiddenParamsIntoLocals", "putValueIfNeeded", "value", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/ValueKind;", "registerLineNumberAfterwards", "rememberClosure", "expression", ModuleXmlParser.TYPE, "parameter", "reorderArgumentsIfNeeded", "actualArgsWithDeclIndex", "", "Lorg/jetbrains/kotlin/codegen/ArgumentAndDeclIndex;", "valueParameterTypes", "PsiInlineFunctionSource", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiInlineCodegen extends InlineCodegen<ExpressionCodegen> implements CallGenerator {
    private PsiExpressionLambda activeLambda;
    private final Type actualDispatchReceiver;
    private final List<PsiExpressionLambda> closuresToGenerate;
    private final FunctionDescriptor functionDescriptor;
    private final List<Pair<ParameterInfo, Integer>> hiddenParameters;
    private final Type methodOwner;

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void reorderArgumentsIfNeeded(List<ArgumentAndDeclIndex> actualArgsWithDeclIndex, List<Type> valueParameterTypes) {
        Intrinsics.checkNotNullParameter(actualArgsWithDeclIndex, "actualArgsWithDeclIndex");
        Intrinsics.checkNotNullParameter(valueParameterTypes, "valueParameterTypes");
    }

    public PsiInlineCodegen(ExpressionCodegen codegen, GenerationState state, FunctionDescriptor functionDescriptor, JvmMethodSignature signature, TypeParameterMappings<KotlinType> typeParameterMappings, SourceCompilerForInline sourceCompiler, Type methodOwner, Type actualDispatchReceiver, KtElement reportErrorsOn) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(typeParameterMappings, "typeParameterMappings");
        Intrinsics.checkNotNullParameter(sourceCompiler, "sourceCompiler");
        Intrinsics.checkNotNullParameter(methodOwner, "methodOwner");
        Intrinsics.checkNotNullParameter(actualDispatchReceiver, "actualDispatchReceiver");
        Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
        PsiInlineIntrinsicsSupport psiInlineIntrinsicsSupport = new PsiInlineIntrinsicsSupport(state, reportErrorsOn);
        TypeSystemCommonBackendContext typeSystem = codegen.getTypeSystem();
        Intrinsics.checkNotNullExpressionValue(typeSystem, "<get-typeSystem>(...)");
        super(codegen, state, signature, typeParameterMappings, sourceCompiler, new ReifiedTypeInliner(typeParameterMappings, psiInlineIntrinsicsSupport, typeSystem, state.getLanguageVersionSettings(), state.getConfig().getUnifiedNullChecks()));
        this.functionDescriptor = functionDescriptor;
        this.methodOwner = methodOwner;
        this.actualDispatchReceiver = actualDispatchReceiver;
        this.hiddenParameters = new ArrayList();
        this.closuresToGenerate = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InlineCodegen
    protected void generateAssertField() {
        getCodegen().getParentCodegen().generateAssertField();
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void genCallInner(Callable callableMethod, ResolvedCall<?> resolvedCall, boolean callDefault, ExpressionCodegen codegen) {
        Call call;
        Intrinsics.checkNotNullParameter(callableMethod, "callableMethod");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        SourceCompilerForInline sourceCompiler = getSourceCompiler();
        Intrinsics.checkNotNull(sourceCompiler, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.PsiSourceCompilerForInline");
        ((PsiSourceCompilerForInline) sourceCompiler).setCallDefault$backend(callDefault);
        this.hiddenParameters.isEmpty();
        KtElement callElement = (resolvedCall == null || (call = resolvedCall.get$fakeCall()) == null) ? null : call.getCallElement();
        if (!getState().getGlobalInlineContext().enterIntoInlining(this.functionDescriptor, callElement != null ? new PsiInlineFunctionSource(callElement) : null, new Function2<GlobalInlineContext.InlineFunctionSource, CallableDescriptor, Unit>() { // from class: org.jetbrains.kotlin.codegen.inline.PsiInlineCodegen.genCallInner.2
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
                PsiInlineCodegen.this.getState().getDiagnostics().report(Errors.INLINE_CALL_CYCLE.on(((PsiInlineFunctionSource) reportOn).getPsi(), callee));
            }
        })) {
            String text = callElement != null ? callElement.getText() : null;
            if (text == null) {
                text = "<no source>";
            }
            generateStub(text, codegen);
            return;
        }
        try {
            for (PsiExpressionLambda psiExpressionLambda : this.closuresToGenerate) {
                psiExpressionLambda.generateLambdaBody(getSourceCompiler());
                if (!psiExpressionLambda.getIsBoundCallableReference()) {
                    putClosureParametersOnStack(psiExpressionLambda, null);
                }
            }
            performInline(registerLineNumberAfterwards(resolvedCall), InlineOnlyKt.isInlineOnly(this.functionDescriptor));
        } finally {
            getState().getGlobalInlineContext().exitFromInlining();
        }
    }

    private final boolean registerLineNumberAfterwards(ResolvedCall<?> resolvedCall) {
        Call call;
        KtElement callElement;
        KtExpression condition;
        if (resolvedCall != null && (call = resolvedCall.get$fakeCall()) != null && (callElement = call.getCallElement()) != null) {
            KtElement ktElement = callElement;
            KtIfExpression ktIfExpression = (KtIfExpression) PsiTreeUtil.getParentOfType(ktElement, KtIfExpression.class, true);
            if (ktIfExpression != null && (condition = ktIfExpression.getCondition()) != null) {
                return PsiUtilsKt.isAncestor(condition, ktElement, false);
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void processHiddenParameters() {
        SourceCompilerForInline sourceCompiler = getSourceCompiler();
        Intrinsics.checkNotNull(sourceCompiler, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.PsiSourceCompilerForInline");
        if (!DescriptorAsmUtil.isStaticMethod(((PsiSourceCompilerForInline) sourceCompiler).getContext().getContextKind(), this.functionDescriptor)) {
            this.hiddenParameters.add(TuplesKt.to(getInvocationParamBuilder().addNextParameter(this.methodOwner, false, this.actualDispatchReceiver), Integer.valueOf(getCodegen().getFrameMap().enterTemp(this.methodOwner))));
        }
        for (JvmMethodParameterSignature jvmMethodParameterSignature : getJvmSignature().getValueParameters()) {
            if (jvmMethodParameterSignature.getKind() == JvmMethodParameterKind.VALUE) {
                return;
            }
            List<Pair<ParameterInfo, Integer>> list = this.hiddenParameters;
            ParametersBuilder invocationParamBuilder = getInvocationParamBuilder();
            Type asmType = jvmMethodParameterSignature.getAsmType();
            Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
            ParameterInfo parameterInfoAddNextParameter$default = ParametersBuilder.addNextParameter$default(invocationParamBuilder, asmType, false, null, 4, null);
            FrameMap frameMap = getCodegen().getFrameMap();
            Type asmType2 = jvmMethodParameterSignature.getAsmType();
            Intrinsics.checkNotNullExpressionValue(asmType2, "getAsmType(...)");
            list.add(TuplesKt.to(parameterInfoAddNextParameter$default, Integer.valueOf(frameMap.enterTemp(asmType2))));
        }
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putHiddenParamsIntoLocals() {
        int size = this.hiddenParameters.size() - 1;
        if (size >= 0) {
            while (true) {
                int i = size - 1;
                Pair<ParameterInfo, Integer> pair = this.hiddenParameters.get(size);
                ParameterInfo parameterInfoComponent1 = pair.component1();
                StackValue.local(pair.component2().intValue(), parameterInfoComponent1.getType()).store(StackValue.onStack(parameterInfoComponent1.getTypeOnStack()), getCodegen().getVisitor());
                if (i < 0) {
                    break;
                } else {
                    size = i;
                }
            }
        }
        this.hiddenParameters.clear();
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void genValueAndPut(ValueParameterDescriptor valueParameterDescriptor, KtExpression argumentExpression, JvmKotlinType parameterType, int parameterIndex) {
        ValueKind valueKind;
        KotlinType kotlinType;
        Intrinsics.checkNotNullParameter(argumentExpression, "argumentExpression");
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        if (valueParameterDescriptor == null) {
            throw new IllegalArgumentException(("Parameter descriptor can only be null in case a @PolymorphicSignature function is called, which cannot be declared in Kotlin and thus be inline: " + getCodegen()).toString());
        }
        if (InlineUtil.isInlineParameter(valueParameterDescriptor) && InlineUtil.isInlinableParameterExpression(KtPsiUtil.deparenthesize(argumentExpression))) {
            rememberClosure(argumentExpression, parameterType.getType(), valueParameterDescriptor);
            return;
        }
        StackValue stackValueGen = getCodegen().gen(argumentExpression);
        if (isCallSiteIsSuspend(valueParameterDescriptor) && (kotlinType = parameterType.getKotlinType()) != null && FunctionTypesKt.isSuspendFunctionTypeOrSubtype(kotlinType)) {
            valueKind = ValueKind.READ_OF_INLINE_LAMBDA_FOR_INLINE_SUSPEND_PARAMETER;
        } else {
            valueKind = isInlineSuspendParameter(valueParameterDescriptor) ? ValueKind.READ_OF_OBJECT_FOR_INLINE_SUSPEND_PARAMETER : ValueKind.GENERAL;
        }
        Intrinsics.checkNotNull(stackValueGen);
        putValueIfNeeded(parameterType, stackValueGen, valueKind, parameterIndex);
    }

    private final boolean isInlineSuspendParameter(ValueParameterDescriptor descriptor) {
        if (this.functionDescriptor.isInline() && !descriptor.getIsNoinline()) {
            KotlinType type = descriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            if (FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isCallSiteIsSuspend(ValueParameterDescriptor descriptor) {
        return Intrinsics.areEqual(getState().getBindingContext().get(CodegenBinding.CALL_SITE_IS_SUSPEND_FOR_CROSSINLINE_LAMBDA, descriptor), (Object) true);
    }

    private final void rememberClosure(KtExpression expression, Type type, ValueParameterDescriptor parameter) {
        ReceiverValue boundCallableReferenceReceiver;
        Type typeBoxType;
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression);
        InlineUtil.isInlinableParameterExpression(ktExpressionDeparenthesize);
        if (ktExpressionDeparenthesize instanceof KtCallableReferenceExpression) {
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) ktExpressionDeparenthesize).getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            boundCallableReferenceReceiver = JvmCodegenUtil.getBoundCallableReferenceReceiver(CallUtilKt.getResolvedCallWithAssert(callableReference, getState().getBindingContext()));
        } else {
            boundCallableReferenceReceiver = null;
        }
        Intrinsics.checkNotNull(ktExpressionDeparenthesize);
        PsiExpressionLambda psiExpressionLambda = new PsiExpressionLambda(ktExpressionDeparenthesize, getState(), parameter.getIsCrossinline(), boundCallableReferenceReceiver != null);
        rememberClosure(type, parameter.getIndex(), psiExpressionLambda);
        this.closuresToGenerate.add(psiExpressionLambda);
        if (boundCallableReferenceReceiver != null) {
            StackValue stackValueGenerateReceiverValue = getCodegen().generateReceiverValue(boundCallableReferenceReceiver, false);
            Intrinsics.checkNotNullExpressionValue(stackValueGenerateReceiverValue, "generateReceiverValue(...)");
            KotlinType kotlinType = stackValueGenerateReceiverValue.kotlinType;
            if (kotlinType != null) {
                typeBoxType = DescriptorAsmUtil.boxType(stackValueGenerateReceiverValue.type, kotlinType, getState().getTypeMapper());
            } else {
                typeBoxType = AsmUtil.boxType(stackValueGenerateReceiverValue.type);
            }
            Intrinsics.checkNotNull(typeBoxType);
            putClosureParametersOnStack(psiExpressionLambda, StackValue.coercion(stackValueGenerateReceiverValue, typeBoxType, kotlinType));
        }
    }

    public final PsiExpressionLambda getActiveLambda() {
        return this.activeLambda;
    }

    private final void putClosureParametersOnStack(PsiExpressionLambda next, StackValue receiverValue) {
        this.activeLambda = next;
        getCodegen().pushClosureOnStack(next.getClassDescriptor(), true, this, receiverValue);
        this.activeLambda = null;
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putValueIfNeeded(JvmKotlinType parameterType, StackValue value, ValueKind kind, int parameterIndex) {
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(kind, "kind");
        putArgumentToLocalVal(parameterType, value, parameterIndex, kind);
    }

    @Override // org.jetbrains.kotlin.codegen.CallGenerator
    public void putCapturedValueOnStack(StackValue stackValue, Type valueType, int paramIndex) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        PsiExpressionLambda psiExpressionLambda = this.activeLambda;
        Intrinsics.checkNotNull(psiExpressionLambda);
        putCapturedToLocalVal(stackValue, psiExpressionLambda.getCapturedVars().get(paramIndex), stackValue.kotlinType);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.InlineCodegen
    protected boolean isInlinedToInlineFunInKotlinRuntime() {
        PackageFragmentDescriptor packageFragmentDescriptor;
        ExpressionCodegen codegen = getCodegen();
        ExpressionCodegen expressionCodegen = codegen instanceof ExpressionCodegen ? codegen : null;
        if (expressionCodegen == null) {
            return false;
        }
        FunctionDescriptor functionDescriptor = expressionCodegen.context.getFunctionDescriptor();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        if (!functionDescriptor.isInline() || (packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(functionDescriptor, PackageFragmentDescriptor.class)) == null) {
            return false;
        }
        String strAsString = packageFragmentDescriptor.getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return StringsKt.startsWith$default(strAsString, "kotlin.", false, 2, (Object) null);
    }

    /* JADX INFO: compiled from: PsiInlineCodegen.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiInlineCodegen$PsiInlineFunctionSource;", "Lorg/jetbrains/kotlin/codegen/inline/GlobalInlineContext$InlineFunctionSource;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Lcom/intellij/psi/PsiElement;)V", "getPsi", "()Lcom/intellij/psi/PsiElement;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PsiInlineFunctionSource extends GlobalInlineContext.InlineFunctionSource {
        private final PsiElement psi;

        public PsiInlineFunctionSource(PsiElement psi) {
            Intrinsics.checkNotNullParameter(psi, "psi");
            this.psi = psi;
        }

        public final PsiElement getPsi() {
            return this.psi;
        }
    }
}
