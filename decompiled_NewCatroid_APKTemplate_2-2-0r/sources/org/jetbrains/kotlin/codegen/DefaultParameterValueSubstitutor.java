package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.components.ArgumentsUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: DefaultParameterValueSubstitutor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 %2\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JF\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J<\u0010\u0016\u001a\u00020\u00172\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fJ2\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001cJ\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\"H\u0002J\u0018\u0010#\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\f\u0010$\u001a\u00020\u0015*\u00020\nH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultParameterValueSubstitutor;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "generateOverloadWithSubstitutedParameters", "", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "delegateFunctionDescriptor", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "memberCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "methodElement", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "contextKind", "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "substituteCount", "", "generateOverloadsIfNeeded", "", "generatePrimaryConstructorOverloadsIfNeeded", "constructorDescriptor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "getRemainingParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "hasSecondaryConstructorsWithNoParameters", "klass", "Lorg/jetbrains/kotlin/psi/KtClass;", "isEmptyConstructorNeeded", "countDefaultParameters", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultParameterValueSubstitutor {
    private static final String ANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final GenerationState state;

    /* JADX INFO: compiled from: DefaultParameterValueSubstitutor.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/DefaultParameterValueSubstitutor$Companion;", "", "()V", "ANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS", "", "getANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS", "()Ljava/lang/String;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS() {
            return DefaultParameterValueSubstitutor.ANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS;
        }
    }

    public DefaultParameterValueSubstitutor(GenerationState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
    }

    public final GenerationState getState() {
        return this.state;
    }

    static {
        String descriptor = Type.getObjectType("synthetic/kotlin/jvm/GeneratedByJvmOverloads").getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
        ANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS = descriptor;
    }

    public final void generatePrimaryConstructorOverloadsIfNeeded(ConstructorDescriptor constructorDescriptor, ClassBuilder classBuilder, MemberCodegen<?> memberCodegen, OwnerKind contextKind, KtPureClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(constructorDescriptor, "constructorDescriptor");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(memberCodegen, "memberCodegen");
        Intrinsics.checkNotNullParameter(contextKind, "contextKind");
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        KtPrimaryConstructor primaryConstructor = classOrObject.getPrimaryConstructor();
        KtPrimaryConstructor ktPrimaryConstructor = primaryConstructor == null ? classOrObject : primaryConstructor;
        ConstructorDescriptor constructorDescriptor2 = constructorDescriptor;
        if (generateOverloadsIfNeeded(ktPrimaryConstructor, constructorDescriptor2, constructorDescriptor2, contextKind, classBuilder, memberCodegen) || !isEmptyConstructorNeeded(constructorDescriptor, classOrObject)) {
            return;
        }
        generateOverloadWithSubstitutedParameters(constructorDescriptor2, constructorDescriptor2, classBuilder, memberCodegen, ktPrimaryConstructor, contextKind, countDefaultParameters(constructorDescriptor2));
    }

    public final boolean generateOverloadsIfNeeded(KtPureElement methodElement, FunctionDescriptor functionDescriptor, FunctionDescriptor delegateFunctionDescriptor, OwnerKind contextKind, ClassBuilder classBuilder, MemberCodegen<?> memberCodegen) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(delegateFunctionDescriptor, "delegateFunctionDescriptor");
        Intrinsics.checkNotNullParameter(contextKind, "contextKind");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(memberCodegen, "memberCodegen");
        if (JvmAnnotationUtilKt.findJvmOverloadsAnnotation(functionDescriptor) == null) {
            return false;
        }
        int iCountDefaultParameters = countDefaultParameters(functionDescriptor);
        if (1 <= iCountDefaultParameters) {
            int i = 1;
            while (true) {
                int i2 = i;
                generateOverloadWithSubstitutedParameters(functionDescriptor, delegateFunctionDescriptor, classBuilder, memberCodegen, methodElement, contextKind, i2);
                if (i2 == iCountDefaultParameters) {
                    break;
                }
                i = i2 + 1;
            }
        }
        return true;
    }

    private final int countDefaultParameters(FunctionDescriptor functionDescriptor) {
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        int i = 0;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (ValueParameterDescriptor valueParameterDescriptor : list) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                if (ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:88:0x0301  */
    private final void generateOverloadWithSubstitutedParameters(FunctionDescriptor functionDescriptor, FunctionDescriptor delegateFunctionDescriptor, ClassBuilder classBuilder, MemberCodegen<?> memberCodegen, KtPureElement methodElement, OwnerKind contextKind, int substituteCount) {
        MethodVisitor methodVisitor;
        KotlinType type;
        KotlinTypeMapper typeMapper = this.state.getTypeMapper();
        boolean zIsStaticMethod = DescriptorAsmUtil.isStaticMethod(contextKind, functionDescriptor);
        int commonCallableFlags = DescriptorAsmUtil.getCommonCallableFlags(functionDescriptor, this.state) & (-129);
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        List<ValueParameterDescriptor> remainingParameters = getRemainingParameters(original, substituteCount);
        List<ValueParameterDescriptor> list = remainingParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (true) {
            KtParameter ktParameter = null;
            if (!it.hasNext()) {
                break;
            }
            PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration((ValueParameterDescriptor) it.next());
            if (psiElementDescriptorToDeclaration instanceof KtParameter) {
                ktParameter = (KtParameter) psiElementDescriptorToDeclaration;
            }
            arrayList.add(ktParameter);
        }
        ArrayList arrayList2 = arrayList;
        int i = commonCallableFlags | (zIsStaticMethod ? 8 : 0) | ((!((functionDescriptor.getModality() == Modality.FINAL || this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.GenerateJvmOverloadsAsFinal)) && !JvmCodegenUtil.isJvmInterface(functionDescriptor.getContainingModule())) || (functionDescriptor instanceof ConstructorDescriptor)) ? 0 : 16);
        ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.lastOrNull((List) remainingParameters);
        int i2 = i | ((valueParameterDescriptor != null ? valueParameterDescriptor.getVarargElementType() : null) != null ? 128 : 0);
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignatureWithCustomParameters = typeMapper.mapSignatureWithCustomParameters(functionDescriptor, contextKind, remainingParameters, false);
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(new JvmDeclarationOrigin(JvmDeclarationOriginKind.JVM_OVERLOADS, methodElement != null ? methodElement.getPsiOrParent() : null, functionDescriptor, arrayList2), i2, jvmMethodGenericSignatureMapSignatureWithCustomParameters.getAsmMethod().getName(), jvmMethodGenericSignatureMapSignatureWithCustomParameters.getAsmMethod().getDescriptor(), jvmMethodGenericSignatureMapSignatureWithCustomParameters.getGenericsSignature(), FunctionCodegen.getThrownExceptions(functionDescriptor, typeMapper));
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        boolean z = ((i2 & 2) == 0 && (i2 & 4096) == 0) ? false : true;
        AnnotationCodegen.forMethod(methodVisitorNewMethod, memberCodegen, this.state, z).genAnnotations(functionDescriptor, jvmMethodGenericSignatureMapSignatureWithCustomParameters.getReturnType(), functionDescriptor.getReturnType());
        if (Intrinsics.areEqual(this.state.getClassBuilderMode(), ClassBuilderMode.KAPT3)) {
            methodVisitorNewMethod.visitAnnotation(ANNOTATION_TYPE_DESCRIPTOR_FOR_JVM_OVERLOADS_GENERATED_METHODS, false);
        }
        JvmMethodGenericSignature jvmMethodGenericSignature = jvmMethodGenericSignatureMapSignatureWithCustomParameters;
        FunctionCodegen.generateParameterAnnotations(functionDescriptor, methodVisitorNewMethod, jvmMethodGenericSignature, remainingParameters, memberCodegen, this.state, z);
        if (!this.state.getClassBuilderMode().generateBodies) {
            FunctionCodegen.generateLocalVariablesForParameters(methodVisitorNewMethod, jvmMethodGenericSignature, functionDescriptor, null, new Label(), new Label(), remainingParameters, zIsStaticMethod, this.state);
            methodVisitorNewMethod.visitEnd();
            return;
        }
        FrameMap frameMap = new FrameMap();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
        methodVisitorNewMethod.visitCode();
        Label label = new Label();
        methodVisitorNewMethod.visitLabel(label);
        List<ValueParameterDescriptor> list2 = remainingParameters;
        KotlinTypeMapper kotlinTypeMapper = typeMapper;
        Type owner = KotlinTypeMapper.mapToCallableMethod$default(typeMapper, delegateFunctionDescriptor, false, null, null, 12, null).getOwner();
        if (!zIsStaticMethod) {
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            instructionAdapter.load(frameMap.enterTemp(OBJECT_TYPE), owner);
            if (functionDescriptor instanceof ConstructorDescriptor) {
                ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) functionDescriptor;
                methodVisitor = methodVisitorNewMethod;
                ClassDescriptor dispatchReceiverParameterForConstructorCall = JvmCodegenUtil.getDispatchReceiverParameterForConstructorCall(constructorDescriptor, (MutableClosure) this.state.getBindingContext().get(CodegenBinding.CLOSURE, constructorDescriptor.getConstructedClass()));
                if (dispatchReceiverParameterForConstructorCall != null) {
                    Type OBJECT_TYPE2 = AsmTypes.OBJECT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE2, "OBJECT_TYPE");
                    instructionAdapter.load(frameMap.enterTemp(OBJECT_TYPE2), kotlinTypeMapper.mapType(dispatchReceiverParameterForConstructorCall));
                }
            } else {
                methodVisitor = methodVisitorNewMethod;
            }
        } else {
            methodVisitor = methodVisitorNewMethod;
            DeclarationDescriptor containingDeclaration = delegateFunctionDescriptor.getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (containingDeclaration instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) containingDeclaration;
                if (classDescriptor.getIsCompanionObject()) {
                    StackValue.Field fieldSingleton = StackValue.singleton(classDescriptor, kotlinTypeMapper);
                    Intrinsics.checkNotNullExpressionValue(fieldSingleton, "singleton(...)");
                    fieldSingleton.put(fieldSingleton.type, fieldSingleton.kotlinType, instructionAdapter);
                }
            }
        }
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType returnType = extensionReceiverParameter.getReturnType();
            Type typeMapType = kotlinTypeMapper.mapType(extensionReceiverParameter);
            StackValue.local(frameMap.enter(extensionReceiverParameter, typeMapType), typeMapType, returnType).put(typeMapType, returnType, instructionAdapter);
        }
        for (ValueParameterDescriptor valueParameterDescriptor2 : list2) {
            frameMap.enter(valueParameterDescriptor2, kotlinTypeMapper.mapType(valueParameterDescriptor2));
        }
        DefaultCallArgs defaultCallArgs = new DefaultCallArgs(functionDescriptor.getValueParameters().size());
        Iterator<ValueParameterDescriptor> it2 = functionDescriptor.getValueParameters().iterator();
        while (it2.hasNext()) {
            ValueParameterDescriptor next = it2.next();
            KotlinType type2 = next.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            List<ValueParameterDescriptor> list3 = list2;
            KotlinTypeMapper kotlinTypeMapper2 = kotlinTypeMapper;
            MethodVisitor methodVisitor2 = methodVisitor;
            Label label2 = label;
            Iterator<ValueParameterDescriptor> it3 = it2;
            InstructionAdapter instructionAdapter2 = instructionAdapter;
            Type typeMapType$default = KotlinTypeMapper.mapType$default(kotlinTypeMapper, type2, null, null, 6, null);
            if (list3.contains(next)) {
                Intrinsics.checkNotNull(next);
                StackValue.local(frameMap.getIndex(next), typeMapType$default, type2).put(typeMapType$default, type2, instructionAdapter2);
            } else {
                AsmUtil.pushDefaultValueOnStack(typeMapType$default, instructionAdapter2);
                defaultCallArgs.mark(next.getIndex());
            }
            list2 = list3;
            instructionAdapter = instructionAdapter2;
            label = label2;
            kotlinTypeMapper = kotlinTypeMapper2;
            it2 = it3;
            methodVisitor = methodVisitor2;
        }
        MethodVisitor methodVisitor3 = methodVisitor;
        KotlinTypeMapper kotlinTypeMapper3 = kotlinTypeMapper;
        Label label3 = label;
        InstructionAdapter instructionAdapter3 = instructionAdapter;
        List<ValueParameterDescriptor> list4 = list2;
        Iterator<Integer> it4 = defaultCallArgs.toInts().iterator();
        while (it4.hasNext()) {
            instructionAdapter3.iconst(it4.next().intValue());
        }
        instructionAdapter3.aconst(null);
        Method methodMapDefaultMethod = kotlinTypeMapper3.mapDefaultMethod(delegateFunctionDescriptor, contextKind);
        if (functionDescriptor instanceof ConstructorDescriptor) {
            ClassifierDescriptorWithTypeParameters containingDeclaration2 = ((ConstructorDescriptor) functionDescriptor).getContainingModule();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            if (!InlineClassesUtilsKt.isInlineClass(containingDeclaration2)) {
                instructionAdapter3.invokespecial(owner.getInternalName(), methodMapDefaultMethod.getName(), methodMapDefaultMethod.getDescriptor(), false);
            } else {
                instructionAdapter3.invokestatic(owner.getInternalName(), methodMapDefaultMethod.getName(), methodMapDefaultMethod.getDescriptor(), false);
            }
        } else {
            instructionAdapter3.invokestatic(owner.getInternalName(), methodMapDefaultMethod.getName(), methodMapDefaultMethod.getDescriptor(), false);
        }
        instructionAdapter3.areturn(jvmMethodGenericSignatureMapSignatureWithCustomParameters.getReturnType());
        Label label4 = new Label();
        methodVisitor3.visitLabel(label4);
        ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
        FunctionCodegen.generateLocalVariablesForParameters(methodVisitor3, jvmMethodGenericSignature, functionDescriptor, (dispatchReceiverParameter == null || (type = dispatchReceiverParameter.getType()) == null) ? null : CodegenUtilKt.asmType(type, kotlinTypeMapper3), label3, label4, list4, zIsStaticMethod, this.state);
        FunctionCodegen.endVisit(methodVisitor3, (String) null, methodElement);
    }

    private final List<ValueParameterDescriptor> getRemainingParameters(FunctionDescriptor functionDescriptor, int substituteCount) {
        int iCountDefaultParameters = countDefaultParameters(functionDescriptor) - substituteCount;
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
            Intrinsics.checkNotNull(valueParameterDescriptor);
            if (!ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor) || (iCountDefaultParameters = iCountDefaultParameters + (-1)) >= 0) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    private final boolean isEmptyConstructorNeeded(ConstructorDescriptor constructorDescriptor, KtPureClassOrObject classOrObject) {
        ClassDescriptor constructedClass = constructorDescriptor.getConstructedClass();
        Intrinsics.checkNotNullExpressionValue(constructedClass, "getConstructedClass(...)");
        if (constructedClass.getClassKind() != ClassKind.CLASS || classOrObject.isLocal()) {
            return false;
        }
        ClassDescriptor classDescriptor = constructedClass;
        if (InlineClassesUtilsKt.isInlineClass(classDescriptor) || InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters(constructorDescriptor) || DescriptorUtils.isSealedClass(classDescriptor) || CodegenBinding.canHaveOuter(this.state.getBindingContext(), constructedClass) || DescriptorVisibilities.isPrivate(constructorDescriptor.getVisibility()) || constructorDescriptor.getValueParameters().isEmpty()) {
            return false;
        }
        if ((classOrObject instanceof KtClass) && hasSecondaryConstructorsWithNoParameters((KtClass) classOrObject)) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = constructorDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (ValueParameterDescriptor valueParameterDescriptor : list) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                if (!ArgumentsUtilsKt.hasDefaultValue(valueParameterDescriptor)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean hasSecondaryConstructorsWithNoParameters(KtClass klass) {
        List<KtSecondaryConstructor> secondaryConstructors = klass.getSecondaryConstructors();
        if ((secondaryConstructors instanceof Collection) && secondaryConstructors.isEmpty()) {
            return false;
        }
        Iterator<T> it = secondaryConstructors.iterator();
        while (it.hasNext()) {
            if (((KtSecondaryConstructor) it.next()).getValueParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
