package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CallableMethod;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FakeDescriptorsForReferencesKt;
import org.jetbrains.kotlin.codegen.FieldInfo;
import org.jetbrains.kotlin.codegen.FunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.WriteAnnotationUtilKt;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u001bH\u0014J\b\u0010\u001d\u001a\u00020\u001bH\u0002J\b\u0010\u001e\u001a\u00020\u001bH\u0014R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00168TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForNamedFunction;", "Lorg/jetbrains/kotlin/codegen/coroutines/AbstractCoroutineCodegen;", "outerExpressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "closureContext", "Lorg/jetbrains/kotlin/codegen/context/ClosureContext;", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "originalSuspendFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/context/ClosureContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "inlineClassToBoxInInvokeSuspend", "Lorg/jetbrains/kotlin/types/KotlinType;", "labelFieldStackValue", "Lorg/jetbrains/kotlin/codegen/StackValue$Field;", "getLabelFieldStackValue", "()Lorg/jetbrains/kotlin/codegen/StackValue$Field;", "labelFieldStackValue$delegate", "Lkotlin/Lazy;", "passArityToSuperClass", "", "getPassArityToSuperClass", "()Z", "suspendFunctionJvmView", "generateBridges", "", "generateClosureBody", "generateInvokeSuspend", "generateKotlinMetadataAnnotation", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCodegenForNamedFunction extends AbstractCoroutineCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinType inlineClassToBoxInInvokeSuspend;

    /* JADX INFO: renamed from: labelFieldStackValue$delegate, reason: from kotlin metadata */
    private final Lazy labelFieldStackValue;
    private final FunctionDescriptor suspendFunctionJvmView;

    public /* synthetic */ CoroutineCodegenForNamedFunction(ExpressionCodegen expressionCodegen, KtElement ktElement, ClosureContext closureContext, ClassBuilder classBuilder, FunctionDescriptor functionDescriptor, DefaultConstructorMarker defaultConstructorMarker) {
        this(expressionCodegen, ktElement, closureContext, classBuilder, functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen
    protected void generateBridges() {
    }

    @Override // org.jetbrains.kotlin.codegen.coroutines.AbstractCoroutineCodegen
    protected boolean getPassArityToSuperClass() {
        return false;
    }

    private CoroutineCodegenForNamedFunction(ExpressionCodegen expressionCodegen, KtElement ktElement, ClosureContext closureContext, ClassBuilder classBuilder, FunctionDescriptor functionDescriptor) {
        super(expressionCodegen, ktElement, closureContext, classBuilder, null, 16, null);
        this.labelFieldStackValue = LazyKt.lazy(new Function0<StackValue.Field>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForNamedFunction$labelFieldStackValue$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final StackValue.Field invoke() {
                return StackValue.field(FieldInfo.createForHiddenField(Type.getObjectType(this.this$0.v.getThisName()), Type.INT_TYPE, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME), StackValue.LOCAL_0);
            }
        });
        Object obj = this.bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, functionDescriptor);
        Intrinsics.checkNotNull(obj);
        this.suspendFunctionJvmView = (FunctionDescriptor) obj;
        this.inlineClassToBoxInInvokeSuspend = CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(functionDescriptor, this.state.getTypeMapper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final StackValue.Field getLabelFieldStackValue() {
        return (StackValue.Field) this.labelFieldStackValue.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen
    protected void generateClosureBody() {
        generateInvokeSuspend();
        this.v.newField(JvmDeclarationOrigin.NO_ORIGIN, 4096, "result", AsmTypes.OBJECT_TYPE.getDescriptor(), null, null);
    }

    private final void generateInvokeSuspend() {
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin$default((PsiElement) this.element, null, 2, null), getMethodToImplement(), new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForNamedFunction.generateInvokeSuspend.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                Type typeMapType;
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                StackValue.field(AsmTypes.OBJECT_TYPE, Type.getObjectType(CoroutineCodegenForNamedFunction.this.v.getThisName()), "result", false, StackValue.LOCAL_0).store(StackValue.local(1, AsmTypes.OBJECT_TYPE), codegen.v);
                StackValue.Field labelFieldStackValue = CoroutineCodegenForNamedFunction.this.getLabelFieldStackValue();
                Type type = Type.INT_TYPE;
                final CoroutineCodegenForNamedFunction coroutineCodegenForNamedFunction = CoroutineCodegenForNamedFunction.this;
                labelFieldStackValue.store(StackValue.operation(type, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForNamedFunction$generateInvokeSuspend$1$doGenerateBody$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                        invoke2(instructionAdapter);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(InstructionAdapter instructionAdapter) {
                        coroutineCodegenForNamedFunction.getLabelFieldStackValue().put(Type.INT_TYPE, instructionAdapter);
                        instructionAdapter.iconst(Integer.MIN_VALUE);
                        instructionAdapter.or(Type.INT_TYPE);
                    }
                }), codegen.v);
                ClassDescriptor capturedOuterClassDescriptor = CoroutineCodegenForNamedFunction.this.closure.getCapturedOuterClassDescriptor();
                if (capturedOuterClassDescriptor != null) {
                    KotlinTypeMapper typeMapper = CoroutineCodegenForNamedFunction.this.typeMapper;
                    Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                    typeMapType = typeMapper.mapType(capturedOuterClassDescriptor);
                } else {
                    typeMapType = null;
                }
                if (typeMapType != null) {
                    StackValue.field(typeMapType, Type.getObjectType(CoroutineCodegenForNamedFunction.this.v.getThisName()), AsmUtil.CAPTURED_THIS_FIELD, false, StackValue.LOCAL_0).put(typeMapType, codegen.v);
                }
                boolean zIsInterface = DescriptorUtils.isInterface(CoroutineCodegenForNamedFunction.this.suspendFunctionJvmView.getContainingModule());
                KotlinTypeMapper typeMapper2 = CoroutineCodegenForNamedFunction.this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper2, "typeMapper");
                CallableMemberDescriptor callableMemberDescriptorAccessibleDescriptor = CoroutineCodegenForNamedFunction.this.context.accessibleDescriptor((CallableMemberDescriptor) CodegenUtilKt.unwrapFrontendVersion(CoroutineCodegenForNamedFunction.this.suspendFunctionJvmView), null);
                Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorAccessibleDescriptor, "accessibleDescriptor(...)");
                CallableMethod callableMethodMapToCallableMethod$default = KotlinTypeMapper.mapToCallableMethod$default(typeMapper2, (FunctionDescriptor) callableMemberDescriptorAccessibleDescriptor, zIsInterface, null, null, 12, null);
                KotlinTypeMapper typeMapper3 = CoroutineCodegenForNamedFunction.this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper3, "typeMapper");
                Type[] argumentTypes = KotlinTypeMapper.mapToCallableMethod$default(typeMapper3, (FunctionDescriptor) CodegenUtilKt.unwrapFrontendVersion(CoroutineCodegenForNamedFunction.this.suspendFunctionJvmView), zIsInterface, null, null, 12, null).getAsmMethod().getArgumentTypes();
                Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
                Iterator it = ArraysKt.dropLast(argumentTypes, 1).iterator();
                while (it.hasNext()) {
                    AsmUtil.pushDefaultValueOnStack((Type) it.next(), codegen.v);
                }
                codegen.v.load(0, AsmTypes.OBJECT_TYPE);
                if (ModalityUtilsKt.isOverridable(CoroutineCodegenForNamedFunction.this.suspendFunctionJvmView) && !zIsInterface && typeMapType != null) {
                    String internalName = typeMapType.getInternalName();
                    Method asmMethod = callableMethodMapToCallableMethod$default.getAsmMethod();
                    Intrinsics.checkNotNull(internalName);
                    Method implForOpenMethod = CoroutineCodegenUtilKt.getImplForOpenMethod(asmMethod, internalName);
                    codegen.v.invokestatic(internalName, implForOpenMethod.getName(), implForOpenMethod.getDescriptor(), false);
                } else {
                    InstructionAdapter v = codegen.v;
                    Intrinsics.checkNotNullExpressionValue(v, "v");
                    callableMethodMapToCallableMethod$default.genInvokeInstruction(v);
                }
                if (CoroutineCodegenForNamedFunction.this.inlineClassToBoxInInvokeSuspend != null) {
                    InstructionAdapter instructionAdapter = codegen.v;
                    CoroutineCodegenForNamedFunction coroutineCodegenForNamedFunction2 = CoroutineCodegenForNamedFunction.this;
                    Intrinsics.checkNotNull(instructionAdapter);
                    CoroutineCodegenUtilKt.generateCoroutineSuspendedCheck(instructionAdapter);
                    Type type2 = AsmTypes.OBJECT_TYPE;
                    KotlinTypeMapper typeMapper4 = coroutineCodegenForNamedFunction2.typeMapper;
                    Intrinsics.checkNotNullExpressionValue(typeMapper4, "typeMapper");
                    StackValue.coerce(type2, KotlinTypeMapper.mapType$default(typeMapper4, coroutineCodegenForNamedFunction2.inlineClassToBoxInInvokeSuspend, null, null, 6, null), instructionAdapter);
                    StackValue.boxInlineClass(coroutineCodegenForNamedFunction2.inlineClassToBoxInInvokeSuspend, instructionAdapter, coroutineCodegenForNamedFunction2.typeMapper);
                }
                codegen.v.visitInsn(176);
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen, org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        boolean zIsInPublicInlineScope = InlineUtil.isInPublicInlineScope(getClassDescriptor());
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        WriteAnnotationUtilKt.writeKotlinMetadata(v, state, KotlinClassHeader.Kind.SYNTHETIC_CLASS, zIsInPublicInlineScope, 0, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForNamedFunction.generateKotlinMetadataAnnotation.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor av) {
                Intrinsics.checkNotNullParameter(av, "av");
                DescriptorSerializer.Companion companion = DescriptorSerializer.INSTANCE;
                JvmSerializationBindings serializationBindings = CoroutineCodegenForNamedFunction.this.v.getSerializationBindings();
                Intrinsics.checkNotNullExpressionValue(serializationBindings, "getSerializationBindings(...)");
                GenerationState state2 = CoroutineCodegenForNamedFunction.this.state;
                Intrinsics.checkNotNullExpressionValue(state2, "state");
                DescriptorSerializer descriptorSerializerCreateForLambda = companion.createForLambda(new JvmSerializerExtension(serializationBindings, state2, null, 4, null), CoroutineCodegenForNamedFunction.this.state.getLanguageVersionSettings());
                ProtoBuf.Function.Builder builderFunctionProto = descriptorSerializerCreateForLambda.functionProto(FakeDescriptorsForReferencesKt.createFreeFakeLambdaDescriptor(CoroutineCodegenForNamedFunction.this.suspendFunctionJvmView, CoroutineCodegenForNamedFunction.this.state.getTypeApproximator()));
                ProtoBuf.Function functionBuild = builderFunctionProto != null ? builderFunctionProto.build() : null;
                if (functionBuild == null) {
                    return;
                }
                DescriptorAsmUtil.writeAnnotationData(av, descriptorSerializerCreateForLambda, functionBuild);
            }
        });
    }

    /* JADX INFO: compiled from: CoroutineCodegen.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForNamedFunction$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForNamedFunction;", "cv", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "expressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "originalSuspendDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtFunction;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CoroutineCodegenForNamedFunction create(ClassBuilder cv, ExpressionCodegen expressionCodegen, FunctionDescriptor originalSuspendDescriptor, KtFunction declaration) {
            Intrinsics.checkNotNullParameter(cv, "cv");
            Intrinsics.checkNotNullParameter(expressionCodegen, "expressionCodegen");
            Intrinsics.checkNotNullParameter(originalSuspendDescriptor, "originalSuspendDescriptor");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            BindingContext bindingContext = expressionCodegen.getState().getBindingContext();
            Object obj = bindingContext.get(CodegenBinding.CLOSURE, bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, originalSuspendDescriptor));
            if (obj != null) {
                Intrinsics.checkNotNullExpressionValue(obj, "sure(...)");
                MutableClosure mutableClosure = (MutableClosure) obj;
                Object obj2 = bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, originalSuspendDescriptor);
                if (obj2 != null) {
                    Intrinsics.checkNotNullExpressionValue(obj2, "sure(...)");
                    if (((FunctionDescriptor) obj2).mo6753getDispatchReceiverParameter() != null) {
                        mutableClosure.setNeedsCaptureOuterClass();
                    }
                    KtFunction ktFunction = declaration;
                    ClosureContext closureContextIntoClosure = expressionCodegen.context.intoClosure(originalSuspendDescriptor, expressionCodegen, expressionCodegen.getState().getTypeMapper());
                    Intrinsics.checkNotNullExpressionValue(closureContextIntoClosure, "intoClosure(...)");
                    return new CoroutineCodegenForNamedFunction(expressionCodegen, ktFunction, closureContextIntoClosure, cv, originalSuspendDescriptor, null);
                }
                throw new AssertionError("There must be a jvm view defined for " + originalSuspendDescriptor);
            }
            throw new AssertionError("There must be a closure defined for " + originalSuspendDescriptor);
        }
    }
}
