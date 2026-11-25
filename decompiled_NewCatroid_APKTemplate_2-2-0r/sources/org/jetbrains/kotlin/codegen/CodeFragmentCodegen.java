package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: CodeFragmentCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B7\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0012H\u0014J\b\u0010\u0017\u001a\u00020\u0012H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0014R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegen;", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "codeFragment", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo;", "calculatedInfo", "Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;", "classContext", "Lorg/jetbrains/kotlin/codegen/CodeFragmentContext;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/psi/KtCodeFragment;Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo;Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;Lorg/jetbrains/kotlin/codegen/CodeFragmentContext;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "methodDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "genConstructor", "", "genMethod", "methodContext", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "generateBody", "generateDeclaration", "generateKotlinMetadataAnnotation", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeFragmentCodegen extends MemberCodegen<KtCodeFragment> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Key<CodeFragmentCodegenInfo> INFO_USERDATA_KEY;
    private final CalculatedCodeFragmentCodegenInfo calculatedInfo;
    private final CodeFragmentContext classContext;
    private final KtCodeFragment codeFragment;
    private final CodeFragmentCodegenInfo info;
    private final FunctionDescriptor methodDescriptor;

    public /* synthetic */ CodeFragmentCodegen(KtCodeFragment ktCodeFragment, CodeFragmentCodegenInfo codeFragmentCodegenInfo, CalculatedCodeFragmentCodegenInfo calculatedCodeFragmentCodegenInfo, CodeFragmentContext codeFragmentContext, GenerationState generationState, ClassBuilder classBuilder, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktCodeFragment, codeFragmentCodegenInfo, calculatedCodeFragmentCodegenInfo, codeFragmentContext, generationState, classBuilder);
    }

    @JvmStatic
    public static final CodeFragmentCodegen createCodegen(KtCodeFragment ktCodeFragment, GenerationState generationState, CodegenContext<?> codegenContext) {
        return INSTANCE.createCodegen(ktCodeFragment, generationState, codegenContext);
    }

    @JvmStatic
    public static final CodeFragmentCodegenInfo getCodeFragmentInfo(KtCodeFragment ktCodeFragment) {
        return INSTANCE.getCodeFragmentInfo(ktCodeFragment);
    }

    private CodeFragmentCodegen(KtCodeFragment ktCodeFragment, CodeFragmentCodegenInfo codeFragmentCodegenInfo, CalculatedCodeFragmentCodegenInfo calculatedCodeFragmentCodegenInfo, CodeFragmentContext codeFragmentContext, GenerationState generationState, ClassBuilder classBuilder) {
        super(generationState, null, codeFragmentContext, ktCodeFragment, classBuilder);
        this.codeFragment = ktCodeFragment;
        this.info = codeFragmentCodegenInfo;
        this.calculatedInfo = calculatedCodeFragmentCodegenInfo;
        this.classContext = codeFragmentContext;
        this.methodDescriptor = codeFragmentCodegenInfo.getMethodDescriptor();
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        this.v.defineClass(this.codeFragment, this.state.getConfig().getClassFileVersion(), 33, this.info.getClassType().getInternalName(), null, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, new String[0]);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        genConstructor();
        final FunctionDescriptor functionDescriptor = this.methodDescriptor;
        final OwnerKind contextKind = this.classContext.getContextKind();
        final CodeFragmentContext codeFragmentContext = this.classContext;
        genMethod(new MethodContext(functionDescriptor, contextKind, codeFragmentContext) { // from class: org.jetbrains.kotlin.codegen.CodeFragmentCodegen$generateBody$methodContext$1
            @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
            public <D extends CallableMemberDescriptor> D getAccessorForSuperCallIfNeeded(D descriptor, ClassDescriptor superCallTarget, GenerationState state) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                Intrinsics.checkNotNullParameter(state, "state");
                return descriptor;
            }

            {
                CodeFragmentContext codeFragmentContext2 = codeFragmentContext;
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(v, state, false);
    }

    private final void genConstructor() {
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 1, "<init>", "()V", null, null);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            instructionAdapter.load(0, this.info.getClassType());
            instructionAdapter.invokespecial(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "<init>", "()V", false);
            instructionAdapter.areturn(Type.VOID_TYPE);
        }
        methodVisitorNewMethod.visitMaxs(-1, -1);
        methodVisitorNewMethod.visitEnd();
    }

    /* JADX WARN: Type inference failed for: r12v1, types: [org.jetbrains.kotlin.codegen.CodeFragmentCodegen$genMethod$codegen$1] */
    private final void genMethod(final MethodContext methodContext) {
        final Type returnAsmType = this.calculatedInfo.getReturnAsmType();
        List<CalculatedCodeFragmentCodegenInfo.CalculatedParameter> parameters = this.calculatedInfo.getParameters();
        List<CalculatedCodeFragmentCodegenInfo.CalculatedParameter> list = parameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((CalculatedCodeFragmentCodegenInfo.CalculatedParameter) it.next()).getAsmType());
        }
        Type[] typeArr = (Type[]) arrayList.toArray(new Type[0]);
        final MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin(this.codeFragment, methodContext.getFunctionDescriptor()), 9, this.methodDescriptor.getShortName().asString(), Type.getMethodDescriptor(returnAsmType, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), null, null);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            final FrameMap frameMap = new FrameMap();
            for (CalculatedCodeFragmentCodegenInfo.CalculatedParameter calculatedParameter : list) {
                frameMap.enter(calculatedParameter.getTargetDescriptor(), calculatedParameter.getAsmType());
            }
            final GenerationState generationState = this.state;
            ?? r12 = new ExpressionCodegen(methodVisitorNewMethod, frameMap, returnAsmType, methodContext, this, generationState) { // from class: org.jetbrains.kotlin.codegen.CodeFragmentCodegen$genMethod$codegen$1
                final /* synthetic */ CodeFragmentCodegen this$0;

                {
                    this.this$0 = this;
                    CodeFragmentCodegen codeFragmentCodegen = this;
                }

                @Override // org.jetbrains.kotlin.codegen.ExpressionCodegen
                public StackValue findCapturedValue(DeclarationDescriptor descriptor) {
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    CalculatedCodeFragmentCodegenInfo.CalculatedParameter calculatedParameterFindParameter = this.this$0.calculatedInfo.findParameter(descriptor);
                    return calculatedParameterFindParameter == null ? super.findCapturedValue(descriptor) : calculatedParameterFindParameter.getStackValue();
                }

                @Override // org.jetbrains.kotlin.codegen.ExpressionCodegen
                public StackValue generateThisOrOuter(ClassDescriptor calleeContainingClass, boolean isSuper) {
                    Intrinsics.checkNotNullParameter(calleeContainingClass, "calleeContainingClass");
                    StackValue stackValueFindCapturedValue = findCapturedValue(calleeContainingClass);
                    if (stackValueFindCapturedValue != null) {
                        return stackValueFindCapturedValue;
                    }
                    StackValue stackValueGenerateThisOrOuter = super.generateThisOrOuter(calleeContainingClass, isSuper);
                    Intrinsics.checkNotNullExpressionValue(stackValueGenerateThisOrOuter, "generateThisOrOuter(...)");
                    return stackValueGenerateThisOrOuter;
                }

                @Override // org.jetbrains.kotlin.codegen.ExpressionCodegen
                public StackValue generateExtensionReceiver(CallableDescriptor descriptor) {
                    StackValue stackValueFindCapturedValue;
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
                    if (extensionReceiverParameter != null && (stackValueFindCapturedValue = findCapturedValue(extensionReceiverParameter)) != null) {
                        return stackValueFindCapturedValue;
                    }
                    StackValue stackValueGenerateExtensionReceiver = super.generateExtensionReceiver(descriptor);
                    Intrinsics.checkNotNullExpressionValue(stackValueGenerateExtensionReceiver, "generateExtensionReceiver(...)");
                    return stackValueGenerateExtensionReceiver;
                }

                @Override // org.jetbrains.kotlin.codegen.ExpressionCodegen
                public StackValue generateNonIntrinsicSimpleNameExpression(KtSimpleNameExpression expression, StackValue receiver, DeclarationDescriptor descriptor, ResolvedCall<?> resolvedCall, boolean isSyntheticField) {
                    StackValue stackValueFindCapturedValue;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    Intrinsics.checkNotNullParameter(receiver, "receiver");
                    Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                    CallableDescriptor resultingDescriptor = resolvedCall != null ? resolvedCall.getResultingDescriptor() : null;
                    if (resultingDescriptor != null && (stackValueFindCapturedValue = findCapturedValue(resultingDescriptor)) != null) {
                        return stackValueFindCapturedValue;
                    }
                    StackValue stackValueGenerateNonIntrinsicSimpleNameExpression = super.generateNonIntrinsicSimpleNameExpression(expression, receiver, descriptor, resolvedCall, isSyntheticField);
                    Intrinsics.checkNotNullExpressionValue(stackValueGenerateNonIntrinsicSimpleNameExpression, "generateNonIntrinsicSimpleNameExpression(...)");
                    return stackValueGenerateNonIntrinsicSimpleNameExpression;
                }

                @Override // org.jetbrains.kotlin.codegen.ExpressionCodegen, org.jetbrains.kotlin.psi.KtVisitor
                public StackValue visitThisExpression(KtThisExpression expression, StackValue receiver) {
                    StackValue stackValueFindCapturedValue;
                    Intrinsics.checkNotNullParameter(expression, "expression");
                    KtReferenceExpression instanceReference = expression.getInstanceReference();
                    Intrinsics.checkNotNullExpressionValue(instanceReference, "getInstanceReference(...)");
                    DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) getBindingContext().get(BindingContext.REFERENCE_TARGET, instanceReference);
                    if (declarationDescriptor != null && (stackValueFindCapturedValue = findCapturedValue(declarationDescriptor)) != null) {
                        return stackValueFindCapturedValue;
                    }
                    StackValue stackValueVisitThisExpression = super.visitThisExpression(expression, receiver);
                    Intrinsics.checkNotNullExpressionValue(stackValueVisitThisExpression, "visitThisExpression(...)");
                    return stackValueVisitThisExpression;
                }
            };
            r12.gen(this.codeFragment.getContentElement(), returnAsmType);
            r12.v.areturn(returnAsmType);
            Iterator it2 = CollectionsKt.asReversed(parameters).iterator();
            while (it2.hasNext()) {
                frameMap.leave(((CalculatedCodeFragmentCodegenInfo.CalculatedParameter) it2.next()).getTargetDescriptor());
            }
        }
        methodVisitorNewMethod.visitMaxs(-1, -1);
        methodVisitorNewMethod.visitEnd();
    }

    /* JADX INFO: compiled from: CodeFragmentCodegen.kt */
    @Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0005R!\u0010\u0003\u001a\u0015\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004¢\u0006\u0002\b\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegen$Companion;", "", "()V", "INFO_USERDATA_KEY", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "calculateInfo", "Lorg/jetbrains/kotlin/codegen/CalculatedCodeFragmentCodegenInfo;", CompilerOptions.INFO, "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "clearCodeFragmentInfo", "", "codeFragment", "Lorg/jetbrains/kotlin/psi/KtCodeFragment;", "createCodegen", "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegen;", "declaration", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "getCodeFragmentInfo", "getSharedTypeIfApplicable", "Lorg/jetbrains/org/objectweb/asm/Type;", "parameter", "Lorg/jetbrains/kotlin/codegen/CodeFragmentCodegenInfo$IParameter;", "setCodeFragmentInfo", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final CodeFragmentCodegenInfo getCodeFragmentInfo(KtCodeFragment codeFragment) {
            Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
            CodeFragmentCodegenInfo codeFragmentCodegenInfo = (CodeFragmentCodegenInfo) codeFragment.getUserData(CodeFragmentCodegen.INFO_USERDATA_KEY);
            if (codeFragmentCodegenInfo != null) {
                return codeFragmentCodegenInfo;
            }
            throw new IllegalStateException("Codegen info user data is not set".toString());
        }

        private Companion() {
        }

        public final void setCodeFragmentInfo(KtCodeFragment codeFragment, CodeFragmentCodegenInfo info) {
            Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
            Intrinsics.checkNotNullParameter(info, "info");
            codeFragment.putUserData(CodeFragmentCodegen.INFO_USERDATA_KEY, info);
        }

        public final void clearCodeFragmentInfo(KtCodeFragment codeFragment) {
            Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
            codeFragment.putUserData(CodeFragmentCodegen.INFO_USERDATA_KEY, null);
        }

        @JvmStatic
        public final CodeFragmentCodegen createCodegen(KtCodeFragment declaration, GenerationState state, CodegenContext<?> parentContext) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(parentContext, "parentContext");
            CodeFragmentCodegenInfo codeFragmentInfo = getCodeFragmentInfo(declaration);
            ClassDescriptor classDescriptor = codeFragmentInfo.getClassDescriptor();
            ClassBuilder classBuilderNewVisitor = state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(declaration, classDescriptor), codeFragmentInfo.getClassType(), declaration.getContainingFile());
            Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
            CalculatedCodeFragmentCodegenInfo calculatedCodeFragmentCodegenInfoCalculateInfo = calculateInfo(codeFragmentInfo, state.getTypeMapper());
            return new CodeFragmentCodegen(declaration, codeFragmentInfo, calculatedCodeFragmentCodegenInfoCalculateInfo, new CodeFragmentContext(state.getTypeMapper(), classDescriptor, parentContext, calculatedCodeFragmentCodegenInfoCalculateInfo), state, classBuilderNewVisitor, null);
        }

        private final CalculatedCodeFragmentCodegenInfo calculateInfo(CodeFragmentCodegenInfo info, KotlinTypeMapper typeMapper) {
            Type type;
            StackValue stackValueShared;
            JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default = KotlinTypeMapper.mapSignatureSkipGeneric$default(typeMapper, info.getMethodDescriptor(), null, 2, null);
            if (info.getParameters().size() != jvmMethodSignatureMapSignatureSkipGeneric$default.getValueParameters().size()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (info.getParameters().size() != info.getMethodDescriptor().getValueParameters().size()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            ArrayList arrayList = new ArrayList();
            int size = info.getParameters().size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                CodeFragmentCodegenInfo.IParameter iParameter = info.getParameters().get(i2);
                JvmMethodParameterSignature jvmMethodParameterSignature = jvmMethodSignatureMapSignatureSkipGeneric$default.getValueParameters().get(i2);
                ValueParameterDescriptor valueParameterDescriptor = info.getMethodDescriptor().getValueParameters().get(i2);
                Type sharedTypeIfApplicable = getSharedTypeIfApplicable(iParameter, typeMapper);
                if (sharedTypeIfApplicable != null) {
                    stackValueShared = StackValue.shared(i, KotlinTypeMapper.mapType$default(typeMapper, iParameter.getTargetType(), null, null, 6, null));
                    Intrinsics.checkNotNullExpressionValue(stackValueShared, "shared(...)");
                    type = sharedTypeIfApplicable;
                } else {
                    Type asmType = jvmMethodParameterSignature.getAsmType();
                    Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
                    StackValue.Local local = StackValue.local(i, asmType);
                    Intrinsics.checkNotNullExpressionValue(local, "local(...)");
                    StackValue.Local local2 = local;
                    type = asmType;
                    stackValueShared = local2;
                }
                Intrinsics.checkNotNull(valueParameterDescriptor);
                arrayList.add(new CalculatedCodeFragmentCodegenInfo.CalculatedParameter(iParameter, type, stackValueShared, valueParameterDescriptor));
                i += (Intrinsics.areEqual(type, Type.DOUBLE_TYPE) || Intrinsics.areEqual(type, Type.LONG_TYPE)) ? 2 : 1;
            }
            Type returnType = jvmMethodSignatureMapSignatureSkipGeneric$default.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            return new CalculatedCodeFragmentCodegenInfo(arrayList, returnType);
        }

        public final Type getSharedTypeIfApplicable(CodeFragmentCodegenInfo.IParameter parameter, KotlinTypeMapper typeMapper) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
            DeclarationDescriptor targetDescriptor = parameter.getTargetDescriptor();
            if (!(targetDescriptor instanceof LocalVariableDescriptor)) {
                return null;
            }
            Type sharedVarType = typeMapper.getSharedVarType(targetDescriptor);
            if (sharedVarType != null || !parameter.isLValue()) {
                return sharedVarType;
            }
            KotlinType type = ((LocalVariableDescriptor) targetDescriptor).getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return StackValue.sharedTypeForType(KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null));
        }
    }

    static {
        Key<CodeFragmentCodegenInfo> keyCreate = Key.create("CODE_FRAGMENT_CODEGEN_INFO");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        INFO_USERDATA_KEY = keyCreate;
    }
}
