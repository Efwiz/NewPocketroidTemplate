package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.codegen.ClosureGenerationStrategy;
import org.jetbrains.kotlin.codegen.DefaultParameterValueLoader;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.codegen.FunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.FunctionReferenceGenerationStrategy;
import org.jetbrains.kotlin.codegen.InlineClassesCodegenUtilKt;
import org.jetbrains.kotlin.codegen.InnerClassConsumer;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.JvmKotlinType;
import org.jetbrains.kotlin.codegen.MemberCodegen;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.PropertyReferenceCodegen;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.InlineLambdaContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.PackageContext;
import org.jetbrains.kotlin.codegen.context.ScriptContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.LocationInfo;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DescriptorWithContainerSource;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: PsiSourceCompilerForInline.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000À\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 T2\u00020\u0001:\u0002TUB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J2\u00100\u001a\u0002012\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u0002092\b\u0010:\u001a\u0004\u0018\u000109H\u0016J\u0018\u0010;\u001a\u00020-2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J:\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020/2\b\u0010<\u001a\u0004\u0018\u00010HH\u0002J\u0016\u0010I\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u0001090JH\u0016J\b\u0010K\u001a\u00020LH\u0002J\b\u0010M\u001a\u00020\rH\u0016J\u0010\u0010N\u001a\u00020\r2\u0006\u0010O\u001a\u00020\u0015H\u0016J\b\u0010P\u001a\u00020QH\u0002J\u0010\u0010R\u001a\u0002012\u0006\u0010S\u001a\u00020\u0015H\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0014\u001a\u00020\u00158VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u001b¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u000fR\u0014\u0010#\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u000fR\u0014\u0010$\u001a\u00020%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiSourceCompilerForInline;", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "callElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "additionalInnerClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "callDefault", "", "getCallDefault$backend", "()Z", "setCallDefault$backend", "(Z)V", "getCallElement", "()Lorg/jetbrains/kotlin/psi/KtElement;", "callElementText", "", "getCallElementText", "()Ljava/lang/String;", "callElementText$delegate", "Lkotlin/Lazy;", "context", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "getContext", "()Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "inlineCallSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "getInlineCallSiteInfo", "()Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "isCallInsideSameModuleAsCallee", "isFinallyMarkerRequired", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getSourceMapper", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compileInlineFunction", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "generateFinallyBlocks", "", "finallyNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "curFinallyDepth", "", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "afterReturnLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "target", "generateLambdaBody", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/ExpressionLambda;", "reifiedTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "generateMethodBody", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "adapter", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "descriptor", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "jvmMethodSignature", "Lorg/jetbrains/kotlin/codegen/inline/PsiExpressionLambda;", "getContextLabels", "", "getDirectMemberAndCallableFromObject", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "hasFinallyBlocks", "isSuspendLambdaCapturedByOuterObjectOrLambda", "name", "mapDefault", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "reportSuspensionPointInsideMonitor", "stackTraceElement", "Companion", "FakeMemberCodegen", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceCompilerForInline implements SourceCompilerForInline {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<ClassDescriptor> additionalInnerClasses;
    private boolean callDefault;
    private final KtElement callElement;

    /* JADX INFO: renamed from: callElementText$delegate, reason: from kotlin metadata */
    private final Lazy callElementText;
    private final ExpressionCodegen codegen;
    private final MethodContext context;
    private final FunctionDescriptor functionDescriptor;

    public PsiSourceCompilerForInline(ExpressionCodegen codegen, KtElement callElement, FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(callElement, "callElement");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        this.codegen = codegen;
        this.callElement = callElement;
        this.functionDescriptor = functionDescriptor;
        ArrayList arrayList = new ArrayList();
        this.additionalInnerClasses = arrayList;
        Companion companion = INSTANCE;
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        FunctionDescriptor functionDescriptor3 = functionDescriptor;
        GenerationState state = codegen.getState();
        Intrinsics.checkNotNullExpressionValue(state, "getState(...)");
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor);
        PsiFile containingFile = psiElementDescriptorToDeclaration != null ? psiElementDescriptorToDeclaration.getContainingFile() : null;
        CodegenContext<?> context = companion.getContext(functionDescriptor2, functionDescriptor3, state, containingFile instanceof KtFile ? (KtFile) containingFile : null, arrayList);
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.context.MethodContext");
        this.context = (MethodContext) context;
        this.callElementText = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.codegen.inline.PsiSourceCompilerForInline$callElementText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return this.this$0.getCallElement().getText();
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public KtElement getCallElement() {
        return this.callElement;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public GenerationState getState() {
        GenerationState state = this.codegen.getState();
        Intrinsics.checkNotNullExpressionValue(state, "getState(...)");
        return state;
    }

    public final MethodContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public String getCallElementText() {
        Object value = this.callElementText.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (String) value;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public InlineCallSiteInfo getInlineCallSiteInfo() {
        VirtualFile virtualFile;
        MethodContext context = this.codegen.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        MemberCodegen<?> parentCodegen = this.codegen.getParentCodegen();
        Intrinsics.checkNotNullExpressionValue(parentCodegen, "getParentCodegen(...)");
        while (context instanceof InlineLambdaContext) {
            CodegenContext parentContext = context.getParentContext();
            Intrinsics.checkNotNullExpressionValue(parentContext, "getParentContext(...)");
            boolean z = parentContext instanceof ClosureContext;
            boolean z2 = parentContext.getParentContext() instanceof MethodContext;
            CodegenContext parentContext2 = parentContext.getParentContext();
            Intrinsics.checkNotNull(parentContext2, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.context.MethodContext");
            context = (MethodContext) parentContext2;
            boolean z3 = parentCodegen instanceof FakeMemberCodegen;
            Intrinsics.checkNotNull(parentCodegen, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.PsiSourceCompilerForInline.FakeMemberCodegen");
            parentCodegen = ((FakeMemberCodegen) parentCodegen).getDelegate();
        }
        KotlinTypeMapper typeMapper = this.codegen.getState().getTypeMapper();
        FunctionDescriptor functionDescriptor = context.getFunctionDescriptor();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        OwnerKind contextKind = context.getContextKind();
        Intrinsics.checkNotNullExpressionValue(contextKind, "getContextKind(...)");
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric = typeMapper.mapSignatureSkipGeneric(functionDescriptor, contextKind);
        String className = parentCodegen.getClassName();
        Intrinsics.checkNotNullExpressionValue(className, "getClassName(...)");
        Method asmMethod = jvmMethodSignatureMapSignatureSkipGeneric.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        FunctionDescriptor functionDescriptor2 = context.getFunctionDescriptor();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor2, "getFunctionDescriptor(...)");
        DescriptorVisibility inlineCallSiteVisibility = PsiSourceCompilerForInlineKt.getInlineCallSiteVisibility(functionDescriptor2);
        PsiFile containingFile = getCallElement().getContainingFile();
        File file = (containingFile == null || (virtualFile = containingFile.getVirtualFile()) == null) ? null : new File(virtualFile.getPath());
        Integer lineNumberForElement = CodegenUtil.getLineNumberForElement(getCallElement(), false);
        return new InlineCallSiteInfo(className, asmMethod, inlineCallSiteVisibility, file, lineNumberForElement != null ? lineNumberForElement.intValue() : 0);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SourceMapper getSourceMapper() {
        SourceMapper orCreateSourceMapper = this.codegen.getParentCodegen().getOrCreateSourceMapper();
        Intrinsics.checkNotNullExpressionValue(orCreateSourceMapper, "getOrCreateSourceMapper(...)");
        return orCreateSourceMapper;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SMAPAndMethodNode generateLambdaBody(ExpressionLambda lambdaInfo, ReifiedTypeParametersUsages reifiedTypeParameters) {
        ClosureContext closureContextIntoClosure;
        Intrinsics.checkNotNullParameter(lambdaInfo, "lambdaInfo");
        Intrinsics.checkNotNullParameter(reifiedTypeParameters, "reifiedTypeParameters");
        if (!(lambdaInfo instanceof PsiExpressionLambda)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        PsiExpressionLambda psiExpressionLambda = (PsiExpressionLambda) lambdaInfo;
        FunctionDescriptor invokeMethodDescriptor = psiExpressionLambda.getInvokeMethodDescriptor();
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default = KotlinTypeMapper.mapSignatureSkipGeneric$default(getState().getTypeMapper(), invokeMethodDescriptor, null, 2, null);
        Method asmMethod = jvmMethodSignatureMapSignatureSkipGeneric$default.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        MethodNode methodNode = new MethodNode(589824, DescriptorAsmUtil.getMethodAsmFlags(invokeMethodDescriptor, OwnerKind.IMPLEMENTATION, getState()), asmMethod.getName(), asmMethod.getDescriptor(), null, null);
        MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
        if (psiExpressionLambda.isPropertyReference()) {
            closureContextIntoClosure = this.codegen.getContext().intoAnonymousClass(psiExpressionLambda.getClassDescriptor(), this.codegen, OwnerKind.IMPLEMENTATION);
        } else if (invokeMethodDescriptor.isSuspend()) {
            closureContextIntoClosure = this.codegen.getContext().intoCoroutineClosure(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(invokeMethodDescriptor, getState()), invokeMethodDescriptor, this.codegen, getState().getTypeMapper());
        } else {
            closureContextIntoClosure = this.codegen.getContext().intoClosure(invokeMethodDescriptor, this.codegen, getState().getTypeMapper());
        }
        Intrinsics.checkNotNull(closureContextIntoClosure);
        MethodContext methodContextIntoInlinedLambda = closureContextIntoClosure.intoInlinedLambda(invokeMethodDescriptor, psiExpressionLambda.getIsCrossInline(), psiExpressionLambda.isPropertyReference());
        Intrinsics.checkNotNullExpressionValue(methodContextIntoInlinedLambda, "intoInlinedLambda(...)");
        SMAP smapGenerateMethodBody = generateMethodBody(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc, invokeMethodDescriptor, methodContextIntoInlinedLambda, psiExpressionLambda.getFunctionWithBodyOrCallableReference(), jvmMethodSignatureMapSignatureSkipGeneric$default, psiExpressionLambda);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
        return new SMAPAndMethodNode(methodNode, smapGenerateMethodBody);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00e6  */
    private final SMAP generateMethodBody(MethodVisitor adapter, FunctionDescriptor descriptor, MethodContext context, KtExpression expression, JvmMethodSignature jvmMethodSignature, PsiExpressionLambda lambdaInfo) {
        String internalName;
        FunctionGenerationStrategy.FunctionDefault functionDefault;
        boolean z = lambdaInfo != null;
        MemberCodegen<?> parentCodegen = this.codegen.getParentCodegen();
        Intrinsics.checkNotNullExpressionValue(parentCodegen, "getParentCodegen(...)");
        KtExpression ktExpression = expression;
        CodegenContext parentContext = context.getParentContext();
        Intrinsics.checkNotNull(parentContext, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.context.FieldOwnerContext<*>");
        FieldOwnerContext fieldOwnerContext = (FieldOwnerContext) parentContext;
        if (z) {
            internalName = this.codegen.getParentCodegen().getClassName();
        } else {
            internalName = getState().getTypeMapper().mapImplementationOwner(descriptor).getInternalName();
        }
        String str = internalName;
        Intrinsics.checkNotNull(str);
        FakeMemberCodegen fakeMemberCodegen = new FakeMemberCodegen(parentCodegen, ktExpression, fieldOwnerContext, str, z ? CollectionsKt.emptyList() : this.additionalInnerClasses, z);
        if (expression instanceof KtCallableReferenceExpression) {
            KtSimpleNameExpression callableReference = ((KtCallableReferenceExpression) expression).getCallableReference();
            Intrinsics.checkNotNullExpressionValue(callableReference, "getCallableReference(...)");
            ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(callableReference, getState().getBindingContext());
            ReceiverValue boundCallableReferenceReceiver = JvmCodegenUtil.getBoundCallableReferenceReceiver(resolvedCallWithAssert);
            KotlinType type = boundCallableReferenceReceiver != null ? boundCallableReferenceReceiver.getType() : null;
            Type typeMapType$default = type != null ? KotlinTypeMapper.mapType$default(getState().getTypeMapper(), type, null, null, 6, null) : null;
            JvmKotlinType jvmKotlinType = typeMapType$default != null ? new JvmKotlinType(typeMapType$default, type) : null;
            if (z) {
                Intrinsics.checkNotNull(lambdaInfo);
                if (lambdaInfo.isPropertyReference()) {
                    Type typeMapClass = getState().getTypeMapper().mapClass(lambdaInfo.getClassDescriptor());
                    PropertyReferenceInfo propertyReferenceInfo = lambdaInfo.getPropertyReferenceInfo();
                    Intrinsics.checkNotNull(propertyReferenceInfo);
                    functionDefault = new PropertyReferenceCodegen.PropertyReferenceGenerationStrategy(true, propertyReferenceInfo.getGetFunction(), propertyReferenceInfo.getTarget(), typeMapClass, jvmKotlinType, lambdaInfo.getFunctionWithBodyOrCallableReference(), getState(), true);
                } else {
                    functionDefault = new FunctionReferenceGenerationStrategy(getState(), descriptor, resolvedCallWithAssert, jvmKotlinType, null, true);
                }
            } else {
                functionDefault = new FunctionReferenceGenerationStrategy(getState(), descriptor, resolvedCallWithAssert, jvmKotlinType, null, true);
            }
        } else if (expression instanceof KtFunctionLiteral) {
            GenerationState state = getState();
            Intrinsics.checkNotNull(expression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDeclarationWithBody");
            functionDefault = new ClosureGenerationStrategy(state, (KtDeclarationWithBody) expression);
        } else {
            GenerationState state2 = getState();
            Intrinsics.checkNotNull(expression, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDeclarationWithBody");
            functionDefault = new FunctionGenerationStrategy.FunctionDefault(state2, (KtDeclarationWithBody) expression);
        }
        FunctionCodegen.generateMethodBody(adapter, descriptor, context, jvmMethodSignature, functionDefault, fakeMemberCodegen, getState().getJvmDefaultMode());
        if (z) {
            this.codegen.propagateChildReifiedTypeParametersUsages(fakeMemberCodegen.getReifiedTypeParametersUsages());
        }
        return new SMAP(fakeMemberCodegen.getOrCreateSourceMapper().getResultMappings());
    }

    /* JADX INFO: compiled from: PsiSourceCompilerForInline.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BC\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0014J\b\u0010\u0018\u001a\u00020\u0013H\u0014J\b\u0010\u0019\u001a\u00020\u0013H\u0014J\b\u0010\u001a\u001a\u00020\tH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiSourceCompilerForInline$FakeMemberCodegen;", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "Lorg/jetbrains/kotlin/psi/KtPureElement;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "declaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "codegenContext", "Lorg/jetbrains/kotlin/codegen/context/FieldOwnerContext;", "className", "", "parentAsInnerClasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isInlineLambdaCodegen", "", "(Lorg/jetbrains/kotlin/codegen/MemberCodegen;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/context/FieldOwnerContext;Ljava/lang/String;Ljava/util/List;Z)V", "getDelegate", "()Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "addParentsToInnerClassesIfNeeded", "", "innerClasses", "", "generateAssertField", "generateBody", "generateDeclaration", "generateKotlinMetadataAnnotation", "getClassName", "getInlineNameGenerator", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class FakeMemberCodegen extends MemberCodegen<KtPureElement> {
        private final String className;
        private final MemberCodegen<?> delegate;
        private final boolean isInlineLambdaCodegen;
        private final List<ClassDescriptor> parentAsInnerClasses;

        public final MemberCodegen<?> getDelegate() {
            return this.delegate;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public FakeMemberCodegen(MemberCodegen<?> delegate, KtElement declaration, FieldOwnerContext<?> codegenContext, String className, List<? extends ClassDescriptor> parentAsInnerClasses, boolean z) {
            super(delegate, declaration, codegenContext);
            Intrinsics.checkNotNullParameter(delegate, "delegate");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(codegenContext, "codegenContext");
            Intrinsics.checkNotNullParameter(className, "className");
            Intrinsics.checkNotNullParameter(parentAsInnerClasses, "parentAsInnerClasses");
            this.delegate = delegate;
            this.className = className;
            this.parentAsInnerClasses = parentAsInnerClasses;
            this.isInlineLambdaCodegen = z;
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        /* JADX INFO: renamed from: generateDeclaration */
        protected void mo6420generateDeclaration() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        /* JADX INFO: renamed from: generateBody */
        protected void mo6419generateBody() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
        protected void mo6421generateKotlinMetadataAnnotation() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        public NameGenerator getInlineNameGenerator() {
            NameGenerator inlineNameGenerator = this.delegate.getInlineNameGenerator();
            Intrinsics.checkNotNullExpressionValue(inlineNameGenerator, "getInlineNameGenerator(...)");
            return inlineNameGenerator;
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        public String getClassName() {
            return this.className;
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        protected void addParentsToInnerClassesIfNeeded(Collection<ClassDescriptor> innerClasses) {
            Intrinsics.checkNotNullParameter(innerClasses, "innerClasses");
            if (this.isInlineLambdaCodegen) {
                super.addParentsToInnerClassesIfNeeded(innerClasses);
            } else {
                innerClasses.addAll(this.parentAsInnerClasses);
            }
        }

        @Override // org.jetbrains.kotlin.codegen.MemberCodegen
        public void generateAssertField() {
            this.delegate.generateAssertField();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final CallableMemberDescriptor getDirectMemberAndCallableFromObject() {
        CallableMemberDescriptor callableFromObject;
        CallableMemberDescriptor directMember = JvmCodegenUtil.getDirectMember(this.functionDescriptor);
        Intrinsics.checkNotNullExpressionValue(directMember, "getDirectMember(...)");
        ImportedFromObjectCallableDescriptor importedFromObjectCallableDescriptor = directMember instanceof ImportedFromObjectCallableDescriptor ? (ImportedFromObjectCallableDescriptor) directMember : null;
        return (importedFromObjectCallableDescriptor == null || (callableFromObject = importedFromObjectCallableDescriptor.getCallableFromObject()) == null) ? directMember : callableFromObject;
    }

    /* JADX INFO: renamed from: getCallDefault$backend, reason: from getter */
    public final boolean getCallDefault() {
        return this.callDefault;
    }

    public final void setCallDefault$backend(boolean z) {
        this.callDefault = z;
    }

    private final Method mapDefault() {
        KotlinTypeMapper typeMapper = getState().getTypeMapper();
        FunctionDescriptor functionDescriptor = this.functionDescriptor;
        OwnerKind contextKind = this.context.getContextKind();
        Intrinsics.checkNotNullExpressionValue(contextKind, "getContextKind(...)");
        Method methodMapDefaultMethod = typeMapper.mapDefaultMethod(functionDescriptor, contextKind);
        String name2 = methodMapDefaultMethod.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        if (!StringsKt.contains$default((CharSequence) name2, (CharSequence) com.beust.jcommander.Parameters.DEFAULT_OPTION_PREFIXES, false, 2, (Object) null) || getState().getConfiguration().getBoolean(JVMConfigurationKeys.USE_OLD_INLINE_CLASSES_MANGLING_SCHEME) || !Intrinsics.areEqual((Object) InlineClassesCodegenUtilKt.classFileContainsMethod(this.functionDescriptor, getState(), methodMapDefaultMethod), (Object) false)) {
            return methodMapDefaultMethod;
        }
        getState().getTypeMapper().setUseOldManglingRulesForFunctionAcceptingInlineClass(true);
        KotlinTypeMapper typeMapper2 = getState().getTypeMapper();
        FunctionDescriptor functionDescriptor2 = this.functionDescriptor;
        OwnerKind contextKind2 = this.context.getContextKind();
        Intrinsics.checkNotNullExpressionValue(contextKind2, "getContextKind(...)");
        Method methodMapDefaultMethod2 = typeMapper2.mapDefaultMethod(functionDescriptor2, contextKind2);
        getState().getTypeMapper().setUseOldManglingRulesForFunctionAcceptingInlineClass(false);
        return methodMapDefaultMethod2;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public SMAPAndMethodNode compileInlineFunction(JvmMethodSignature jvmSignature) {
        Method asmMethod;
        SMAP smapGenerateMethodBody;
        LocationInfo location;
        Intrinsics.checkNotNullParameter(jvmSignature, "jvmSignature");
        FunctionDescriptor functionDescriptor = this.functionDescriptor;
        Method asmMethod2 = jvmSignature.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod2, "getAsmMethod(...)");
        TypeSystemCommonBackendContext typeSystem = this.codegen.getTypeSystem();
        Intrinsics.checkNotNullExpressionValue(typeSystem, "<get-typeSystem>(...)");
        SMAPAndMethodNode sMAPAndMethodNodeGenerateInlineIntrinsic = InlineIntrinsicsKt.generateInlineIntrinsic(functionDescriptor, asmMethod2, typeSystem);
        if (sMAPAndMethodNodeGenerateInlineIntrinsic != null) {
            return sMAPAndMethodNodeGenerateInlineIntrinsic;
        }
        if (this.callDefault) {
            asmMethod = mapDefault();
        } else {
            asmMethod = jvmSignature.getAsmMethod();
            Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        }
        Method method = asmMethod;
        if (!Intrinsics.areEqual(method.getName(), this.functionDescriptor.getShortName().asString()) && (location = new KotlinLookupLocation(getCallElement()).getLocation()) != null) {
            GenerationState state = getState();
            FqName fqNameSafe = DescriptorUtils.getFqNameSafe(this.functionDescriptor.getContainingModule());
            Intrinsics.checkNotNullExpressionValue(fqNameSafe, "getFqNameSafe(...)");
            String name2 = method.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            SourceCompilerForInlineKt.trackLookup(state, fqNameSafe, name2, location);
        }
        CallableMemberDescriptor directMemberAndCallableFromObject = getDirectMemberAndCallableFromObject();
        if (directMemberAndCallableFromObject instanceof DescriptorWithContainerSource) {
            return SourceCompilerForInlineKt.loadCompiledInlineFunction(KotlinTypeMapper.INSTANCE.getContainingClassesForDeserializedCallable((DescriptorWithContainerSource) directMemberAndCallableFromObject).getImplClassId(), method, this.functionDescriptor.isSuspend(), InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(this.functionDescriptor), getState());
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(this.functionDescriptor);
        KtDeclarationWithBody ktDeclarationWithBody = psiElementDescriptorToDeclaration instanceof KtDeclarationWithBody ? (KtDeclarationWithBody) psiElementDescriptorToDeclaration : null;
        if (ktDeclarationWithBody == null) {
            throw new IllegalStateException("Couldn't find declaration for function " + this.functionDescriptor);
        }
        MethodNode methodNode = new MethodNode(589824, DescriptorAsmUtil.getMethodAsmFlags(this.functionDescriptor, this.context.getContextKind(), getState()) | (this.callDefault ? 8 : 0), method.getName(), method.getDescriptor(), null, null);
        MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
        if (this.callDefault) {
            Type typeMapImplementationOwner = getState().getTypeMapper().mapImplementationOwner(this.functionDescriptor);
            MemberCodegen<?> parentCodegen = this.codegen.getParentCodegen();
            Intrinsics.checkNotNullExpressionValue(parentCodegen, "getParentCodegen(...)");
            CodegenContext parentContext = this.context.getParentContext();
            Intrinsics.checkNotNull(parentContext, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.context.FieldOwnerContext<*>");
            String internalName = typeMapImplementationOwner.getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            FakeMemberCodegen fakeMemberCodegen = new FakeMemberCodegen(parentCodegen, ktDeclarationWithBody, (FieldOwnerContext) parentContext, internalName, this.additionalInnerClasses, false);
            if (!(ktDeclarationWithBody instanceof KtNamedFunction)) {
                throw new IllegalStateException("Property accessors with default parameters not supported " + this.functionDescriptor);
            }
            FunctionCodegen.generateDefaultImplBody(this.context, this.functionDescriptor, maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc, DefaultParameterValueLoader.DEFAULT, (KtNamedFunction) ktDeclarationWithBody, fakeMemberCodegen, method);
            smapGenerateMethodBody = new SMAP(fakeMemberCodegen.getOrCreateSourceMapper().getResultMappings());
        } else {
            smapGenerateMethodBody = generateMethodBody(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc, this.functionDescriptor, this.context, ktDeclarationWithBody, jvmSignature, null);
        }
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitEnd();
        return new SMAPAndMethodNode(methodNode, smapGenerateMethodBody);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean hasFinallyBlocks() {
        return this.codegen.hasFinallyBlocks();
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public void generateFinallyBlocks(MethodNode finallyNode, int curFinallyDepth, Type returnType, Label afterReturnLabel, Label target) {
        Intrinsics.checkNotNullParameter(finallyNode, "finallyNode");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(afterReturnLabel, "afterReturnLabel");
        ExpressionCodegen expressionCodegen = new ExpressionCodegen(finallyNode, this.codegen.getFrameMap(), this.codegen.getReturnType(), this.codegen.getContext(), this.codegen.getState(), this.codegen.getParentCodegen());
        expressionCodegen.addBlockStackElementsForNonLocalReturns(this.codegen.getBlockStackElements(), curFinallyDepth);
        expressionCodegen.generateFinallyBlocksIfNeeded(returnType, null, afterReturnLabel);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isCallInsideSameModuleAsCallee() {
        return JvmCodegenUtil.isCallInsideSameModuleAsDeclared(this.functionDescriptor, this.codegen.getContext(), this.codegen.getState().getOutDirectory());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isFinallyMarkerRequired() {
        MethodContext context = this.codegen.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        return InlineCodegenUtilsKt.isFinallyMarkerRequired(context);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public boolean isSuspendLambdaCapturedByOuterObjectOrLambda(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        FunctionDescriptor functionDescriptor = this.codegen.getContext().getFunctionDescriptor();
        Intrinsics.checkNotNullExpressionValue(functionDescriptor, "getFunctionDescriptor(...)");
        FunctionDescriptor containingDeclaration = functionDescriptor;
        while (!(containingDeclaration instanceof ClassDescriptor)) {
            containingDeclaration = containingDeclaration.getContainingModule();
            if (containingDeclaration == null) {
                return false;
            }
        }
        ClassDescriptor capturedOuterClassDescriptor = (ClassDescriptor) containingDeclaration;
        while (capturedOuterClassDescriptor != null) {
            MutableClosure mutableClosure = (MutableClosure) getState().getBindingContext().get(CodegenBinding.CLOSURE, capturedOuterClassDescriptor);
            if (mutableClosure == null) {
                return false;
            }
            Map<DeclarationDescriptor, EnclosedValueDescriptor> captureVariables = mutableClosure.getCaptureVariables();
            Intrinsics.checkNotNullExpressionValue(captureVariables, "getCaptureVariables(...)");
            for (Map.Entry<DeclarationDescriptor, EnclosedValueDescriptor> entry : captureVariables.entrySet()) {
                DeclarationDescriptor key = entry.getKey();
                EnclosedValueDescriptor value = entry.getValue();
                if ((key instanceof ValueParameterDescriptor) && Intrinsics.areEqual(value.getFieldName(), name2)) {
                    KotlinType type = ((ValueParameterDescriptor) key).getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    return FunctionTypesKt.isSuspendFunctionTypeOrSubtype(type);
                }
            }
            capturedOuterClassDescriptor = mutableClosure.getCapturedOuterClassDescriptor();
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x002a  */
    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public Map<String, Label> getContextLabels() {
        CallableMemberDescriptor contextDescriptor;
        MethodContext context = this.codegen.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        CodegenContext parentContext = context.getParentContext();
        Intrinsics.checkNotNullExpressionValue(parentContext, "getParentContext(...)");
        if (parentContext instanceof ClosureContext) {
            ClosureContext closureContext = (ClosureContext) parentContext;
            if (closureContext.getOriginalSuspendLambdaDescriptor() != null) {
                FunctionDescriptor originalSuspendLambdaDescriptor = closureContext.getOriginalSuspendLambdaDescriptor();
                Intrinsics.checkNotNull(originalSuspendLambdaDescriptor);
                contextDescriptor = originalSuspendLambdaDescriptor;
            } else {
                contextDescriptor = context.getContextDescriptor();
            }
        } else {
            contextDescriptor = context.getContextDescriptor();
        }
        Intrinsics.checkNotNull(contextDescriptor);
        CallableMemberDescriptor callableMemberDescriptor = contextDescriptor;
        Set<String> declarationLabels = PsiSourceCompilerForInlineKt.getDeclarationLabels(DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor), callableMemberDescriptor);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(declarationLabels, 10)), 16));
        for (Object obj : declarationLabels) {
            linkedHashMap.put(obj, null);
        }
        return linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.SourceCompilerForInline
    public void reportSuspensionPointInsideMonitor(String stackTraceElement) {
        Intrinsics.checkNotNullParameter(stackTraceElement, "stackTraceElement");
        CoroutineCodegenKt.reportSuspensionPointInsideMonitor(getCallElement(), getState(), stackTraceElement);
    }

    /* JADX INFO: compiled from: PsiSourceCompilerForInline.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiSourceCompilerForInline$Companion;", "", "()V", "getContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "innerDescriptor", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "sourceFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "additionalInners", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CodegenContext<?> getContext(DeclarationDescriptor descriptor, DeclarationDescriptor innerDescriptor, GenerationState state, KtFile sourceFile, List<ClassDescriptor> additionalInners) {
            OwnerKind ownerKind;
            ClassContext classContextIntoClass;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(innerDescriptor, "innerDescriptor");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(additionalInners, "additionalInners");
            if (descriptor instanceof PackageFragmentDescriptor) {
                return new PackageContext((PackageFragmentDescriptor) descriptor, state.getRootContext(), null, sourceFile);
            }
            DeclarationDescriptor containingDeclaration = descriptor.getContainingModule();
            if (containingDeclaration == null) {
                throw new IllegalStateException(("No container for descriptor: " + descriptor).toString());
            }
            CodegenContext<?> context = getContext(containingDeclaration, descriptor, state, sourceFile, additionalInners);
            if (descriptor instanceof ScriptDescriptor) {
                List<ScriptDescriptor> earlierScriptsForReplInterpreter = state.getScriptSpecific().getEarlierScriptsForReplInterpreter();
                ScriptDescriptor scriptDescriptor = (ScriptDescriptor) descriptor;
                if (earlierScriptsForReplInterpreter == null) {
                    earlierScriptsForReplInterpreter = CollectionsKt.emptyList();
                }
                ScriptContext scriptContextIntoScript = context.intoScript(scriptDescriptor, earlierScriptsForReplInterpreter, (ClassDescriptor) descriptor, state.getTypeMapper());
                Intrinsics.checkNotNull(scriptContextIntoScript);
                return scriptContextIntoScript;
            }
            if (descriptor instanceof ClassDescriptor) {
                if (DescriptorUtils.isInterface(descriptor) && !(innerDescriptor instanceof ClassDescriptor) && !JvmAnnotationUtilKt.isCallableMemberCompiledToJvmDefault(innerDescriptor, state.getJvmDefaultMode())) {
                    ownerKind = OwnerKind.DEFAULT_IMPLS;
                } else {
                    ownerKind = OwnerKind.IMPLEMENTATION;
                }
                ClassDescriptor classDescriptor = (ClassDescriptor) descriptor;
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(additionalInners, InnerClassConsumer.INSTANCE.classForInnerClassRecord(classDescriptor, ownerKind == OwnerKind.DEFAULT_IMPLS));
                if (InlineClassesUtilsKt.isInlineClass(descriptor)) {
                    classContextIntoClass = context.intoClass(classDescriptor, OwnerKind.IMPLEMENTATION, state).intoClass(classDescriptor, OwnerKind.ERASED_INLINE_CLASS, state);
                } else {
                    classContextIntoClass = context.intoClass(classDescriptor, ownerKind, state);
                }
                Intrinsics.checkNotNull(classContextIntoClass);
                return classContextIntoClass;
            }
            if (descriptor instanceof FunctionDescriptor) {
                MethodContext methodContextIntoFunction = context.intoFunction((FunctionDescriptor) descriptor);
                Intrinsics.checkNotNull(methodContextIntoFunction);
                return methodContextIntoFunction;
            }
            throw new IllegalStateException("Couldn't build context for " + descriptor);
        }
    }
}
