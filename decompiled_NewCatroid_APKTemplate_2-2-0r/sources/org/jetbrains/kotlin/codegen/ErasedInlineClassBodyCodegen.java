package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ErasedInlineClassBodyCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\b\u0010\u0011\u001a\u00020\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0012H\u0014J\b\u0010\u0014\u001a\u00020\u0012H\u0014J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\b\u0010\u0016\u001a\u00020\u0012H\u0002J\b\u0010\u0017\u001a\u00020\u0012H\u0014J\b\u0010\u0018\u001a\u00020\u0012H\u0002J\b\u0010\u0019\u001a\u00020\u0012H\u0014J\b\u0010\u001a\u001a\u00020\u0012H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ErasedInlineClassBodyCodegen;", "Lorg/jetbrains/kotlin/codegen/ClassBodyCodegen;", "aClass", "Lorg/jetbrains/kotlin/psi/KtClass;", "context", "Lorg/jetbrains/kotlin/codegen/context/ClassContext;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "(Lorg/jetbrains/kotlin/psi/KtClass;Lorg/jetbrains/kotlin/codegen/context/ClassContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/MemberCodegen;)V", "classAsmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "constructorCodegen", "Lorg/jetbrains/kotlin/codegen/ConstructorCodegen;", ES6Iterator.DONE_PROPERTY, "", "generateConstructors", "generateDeclaration", "generateFunctionsFromAny", "generateJvmInlineAnnotation", "generateKotlinMetadataAnnotation", "generateSpecializedEqualsStub", "generateSyntheticPartsAfterBody", "generateUnboxMethod", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErasedInlineClassBodyCodegen extends ClassBodyCodegen {
    private final Type classAsmType;
    private final ConstructorCodegen constructorCodegen;

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void done() {
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public ErasedInlineClassBodyCodegen(KtClass aClass, ClassContext context, ClassBuilder v, GenerationState state, MemberCodegen<?> memberCodegen) {
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(state, "state");
        KtClass ktClass = aClass;
        super(ktClass, context, v, state, memberCodegen);
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        Type typeMapClass = kotlinTypeMapper.mapClass(descriptor);
        this.classAsmType = typeMapClass;
        this.constructorCodegen = new ConstructorCodegen(this.descriptor, context, this.functionCodegen, this, this, state, this.kind, v, typeMapClass, ktClass, this.bindingContext);
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen
    protected void generateConstructors() {
        this.constructorCodegen.generatePrimaryConstructor(new DelegationFieldsInfo(this.classAsmType, this.descriptor, this.state, this.bindingContext), AsmTypes.OBJECT_TYPE);
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        Iterator<ClassConstructorDescriptor> it = DescriptorUtilsKt.getSecondaryConstructors(descriptor).iterator();
        while (it.hasNext()) {
            this.constructorCodegen.generateSecondaryConstructor(it.next(), AsmTypes.OBJECT_TYPE);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
        super.generateSyntheticPartsAfterBody();
        generateDelegatesToDefaultImpl();
        generateUnboxMethod();
        generateFunctionsFromAny();
        generateSpecializedEqualsStub();
        generateJvmInlineAnnotation();
    }

    private final void generateJvmInlineAnnotation() {
        if (this.descriptor.isInline()) {
            this.v.newAnnotation(CodegenUtilKt.topLevelClassAsmType(InlineClassesUtilsKt.getJVM_INLINE_ANNOTATION_FQ_NAME()).getDescriptor(), true).visitEnd();
        }
    }

    private final void generateFunctionsFromAny() {
        KtPureClassOrObject ktPureClassOrObject = this.myClass;
        Intrinsics.checkNotNull(ktPureClassOrObject, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        new FunctionsFromAnyGeneratorImpl((KtClassOrObject) ktPureClassOrObject, this.bindingContext, this.descriptor, this.classAsmType, this.context, this.v, this.state).generate();
    }

    private final void generateUnboxMethod() {
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        SimpleFunctionDescriptor simpleFunctionDescriptorCreateBoxFunctionDescriptor = InlineClassDescriptorResolver.createBoxFunctionDescriptor(descriptor);
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, simpleFunctionDescriptorCreateBoxFunctionDescriptor), simpleFunctionDescriptorCreateBoxFunctionDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.ErasedInlineClassBodyCodegen.generateUnboxMethod.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public JvmMethodGenericSignature mapMethodSignature(FunctionDescriptor functionDescriptor, KotlinTypeMapper typeMapper, OwnerKind contextKind, boolean hasSpecialBridge) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
                Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                Intrinsics.checkNotNullParameter(contextKind, "contextKind");
                return typeMapper.mapSignatureForBoxMethodOfInlineClass(functionDescriptor);
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                InstructionAdapter instructionAdapter = codegen.v;
                Type returnType = signature.getReturnType();
                Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
                List<JvmMethodParameterSignature> valueParameters = signature.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                Type asmType = ((JvmMethodParameterSignature) CollectionsKt.single((List) valueParameters)).getAsmType();
                Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
                KotlinTypeMapper typeMapper = ErasedInlineClassBodyCodegen.this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = ErasedInlineClassBodyCodegen.this.descriptor.mo6347getUnsubstitutedPrimaryConstructor();
                Intrinsics.checkNotNull(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
                CallableMethod callableMethodMapToCallableMethod$default = KotlinTypeMapper.mapToCallableMethod$default(typeMapper, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, false, null, null, 12, null);
                instructionAdapter.anew(returnType);
                instructionAdapter.dup();
                instructionAdapter.load(0, asmType);
                Intrinsics.checkNotNull(instructionAdapter);
                callableMethodMapToCallableMethod$default.genInvokeInstruction(instructionAdapter);
                instructionAdapter.areturn(returnType);
            }
        });
    }

    private final void generateSpecializedEqualsStub() {
        InlineClassDescriptorResolver inlineClassDescriptorResolver = InlineClassDescriptorResolver.INSTANCE;
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        final SimpleFunctionDescriptor simpleFunctionDescriptorCreateSpecializedEqualsDescriptor = inlineClassDescriptorResolver.createSpecializedEqualsDescriptor(descriptor);
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(null, simpleFunctionDescriptorCreateSpecializedEqualsDescriptor), simpleFunctionDescriptorCreateSpecializedEqualsDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.ErasedInlineClassBodyCodegen.generateSpecializedEqualsStub.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public boolean skipNotNullAssertionsForParameters() {
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public JvmMethodGenericSignature mapMethodSignature(FunctionDescriptor functionDescriptor, KotlinTypeMapper typeMapper, OwnerKind contextKind, boolean hasSpecialBridge) {
                Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
                Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
                Intrinsics.checkNotNullParameter(contextKind, "contextKind");
                return typeMapper.mapSignatureForSpecializedEqualsOfInlineClass(functionDescriptor);
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                FrameMap frameMap = codegen.getFrameMap();
                ValueParameterDescriptor valueParameterDescriptor = simpleFunctionDescriptorCreateSpecializedEqualsDescriptor.getValueParameters().get(0);
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor, "get(...)");
                int index = frameMap.getIndex(valueParameterDescriptor);
                FrameMap frameMap2 = codegen.getFrameMap();
                ValueParameterDescriptor valueParameterDescriptor2 = simpleFunctionDescriptorCreateSpecializedEqualsDescriptor.getValueParameters().get(1);
                Intrinsics.checkNotNullExpressionValue(valueParameterDescriptor2, "get(...)");
                int index2 = frameMap2.getIndex(valueParameterDescriptor2);
                Type asmType = signature.getValueParameters().get(0).getAsmType();
                Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
                StackValue.Local local = StackValue.local(index, asmType);
                Intrinsics.checkNotNullExpressionValue(local, "local(...)");
                StackValue.Local local2 = StackValue.local(index2, asmType);
                Intrinsics.checkNotNullExpressionValue(local2, "local(...)");
                DescriptorAsmUtil.genTotalOrderEqualsForExpressionOnStack(local, local2, asmType).put(Type.BOOLEAN_TYPE, codegen.v);
                codegen.v.areturn(Type.BOOLEAN_TYPE);
            }
        });
    }
}
