package org.jetbrains.kotlin.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.backend.common.SamType;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class ClosureCodegen extends MemberCodegen<KtElement> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected final Type asmType;
    private final ClassDescriptor classDescriptor;
    protected final CalculatedClosure closure;
    private Method constructor;
    protected final FunctionDescriptor funDescriptor;
    private final ResolvedCall<FunctionDescriptor> functionReferenceCall;
    private final FunctionDescriptor functionReferenceTarget;
    private final boolean isAdaptedFunctionReference;
    private final boolean isLegacyFunctionReference;
    private final boolean isOptimizedFunctionReference;
    private final SamType samType;
    private final boolean shouldHaveBoundReferenceReceiver;
    private final FunctionGenerationStrategy strategy;
    protected Type superClassAsmType;
    private final KotlinType superClassType;
    private final List<KotlinType> superInterfaceTypes;
    protected final int visibilityFlag;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 7 || i == 12 || i == 19 || i == 21) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 7 || i == 12 || i == 19 || i == 21) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
                objArr[0] = "context";
                break;
            case 3:
                objArr[0] = "strategy";
                break;
            case 4:
                objArr[0] = "parentCodegen";
                break;
            case 5:
                objArr[0] = "classBuilder";
                break;
            case 6:
                objArr[0] = "codegen";
                break;
            case 7:
            case 12:
            case 19:
            case 21:
                objArr[0] = "org/jetbrains/kotlin/codegen/ClosureCodegen";
                break;
            case 8:
                objArr[0] = "bridge";
                break;
            case 9:
                objArr[0] = "bridgeParameterKotlinTypes";
                break;
            case 10:
                objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
                break;
            case 11:
                objArr[0] = "descriptor";
                break;
            case 13:
                objArr[0] = "call";
                break;
            case 14:
                objArr[0] = "anonymousAdapterFunction";
                break;
            case 15:
                objArr[0] = "typeMapper";
                break;
            case 16:
                objArr[0] = "languageVersionSettings";
                break;
            case 17:
                objArr[0] = "closure";
                break;
            case 18:
                objArr[0] = "ownerType";
                break;
            case 20:
                objArr[0] = "function";
                break;
            default:
                objArr[0] = "state";
                break;
        }
        if (i == 7) {
            objArr[1] = "putInstanceOnStack";
        } else if (i == 12) {
            objArr[1] = "generateConstructor";
        } else if (i == 19) {
            objArr[1] = "calculateConstructorParameters";
        } else if (i != 21) {
            objArr[1] = "org/jetbrains/kotlin/codegen/ClosureCodegen";
        } else {
            objArr[1] = "getErasedInvokeFunction";
        }
        switch (i) {
            case 6:
                objArr[2] = "putInstanceOnStack";
                break;
            case 7:
            case 12:
            case 19:
            case 21:
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "generateBridge";
                break;
            case 11:
                objArr[2] = "generateFunctionReferenceMethods";
                break;
            case 13:
            case 14:
                objArr[2] = "calculateFunctionReferenceFlags";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "calculateConstructorParameters";
                break;
            case 20:
                objArr[2] = "getErasedInvokeFunction";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 7 && i != 12 && i != 19 && i != 21) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClosureCodegen(GenerationState generationState, KtElement ktElement, SamType samType, ClosureContext closureContext, ResolvedCall<FunctionDescriptor> resolvedCall, FunctionGenerationStrategy functionGenerationStrategy, MemberCodegen<?> memberCodegen, ClassBuilder classBuilder) {
        super(generationState, memberCodegen, closureContext, ktElement, classBuilder);
        boolean z = false;
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        if (ktElement == null) {
            $$$reportNull$$$0(1);
        }
        if (closureContext == null) {
            $$$reportNull$$$0(2);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(3);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(4);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(5);
        }
        FunctionDescriptor functionDescriptor = closureContext.getFunctionDescriptor();
        this.funDescriptor = functionDescriptor;
        ClassDescriptor classDescriptor = (ClassDescriptor) closureContext.getContextDescriptor();
        this.classDescriptor = classDescriptor;
        this.samType = samType;
        this.functionReferenceCall = resolvedCall;
        KotlinType kotlinType = null;
        this.functionReferenceTarget = resolvedCall != null ? (FunctionDescriptor) resolvedCall.getResultingDescriptor() : null;
        this.strategy = functionGenerationStrategy;
        if (samType == null) {
            this.superInterfaceTypes = new ArrayList();
            for (KotlinType kotlinType2 : classDescriptor.getTypeConstructor().mo7438getSupertypes()) {
                if (DescriptorUtils.isInterface(kotlinType2.getConstructor().getThis$0())) {
                    this.superInterfaceTypes.add(kotlinType2);
                } else {
                    kotlinType = kotlinType2;
                }
            }
            this.superClassType = kotlinType;
        } else {
            this.superInterfaceTypes = Collections.singletonList(samType.getType());
            this.superClassType = DescriptorUtilsKt.getBuiltIns(functionDescriptor).getAnyType();
        }
        CalculatedClosure calculatedClosure = (CalculatedClosure) this.bindingContext.get(CodegenBinding.CLOSURE, this.classDescriptor);
        this.closure = calculatedClosure;
        this.shouldHaveBoundReferenceReceiver = CallableReferenceUtilKt.isForBoundCallableReference(calculatedClosure);
        ClassifierDescriptor this$0 = this.superClassType.getConstructor().getThis$0();
        this.isLegacyFunctionReference = this.functionReferenceTarget != null && this$0 == generationState.getJvmRuntimeTypes().getFunctionReference();
        this.isOptimizedFunctionReference = this.functionReferenceTarget != null && this$0 == generationState.getJvmRuntimeTypes().getFunctionReferenceImpl();
        if (this.functionReferenceTarget != null && this$0 == generationState.getJvmRuntimeTypes().getAdaptedFunctionReference()) {
            z = true;
        }
        this.isAdaptedFunctionReference = z;
        this.asmType = this.typeMapper.mapClass(this.classDescriptor);
        this.visibilityFlag = DescriptorAsmUtil.getVisibilityAccessFlagForClass(this.classDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        Type typeMapSupertype;
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.CLASS);
        if (this.samType != null) {
            this.typeMapper.writeFormalTypeParameters(this.samType.getType().getConstructor().getParameters(), bothSignatureWriter);
        }
        bothSignatureWriter.writeSuperclass();
        this.superClassAsmType = this.typeMapper.mapSupertype(this.superClassType, bothSignatureWriter);
        bothSignatureWriter.writeSuperclassEnd();
        String[] strArr = new String[this.superInterfaceTypes.size()];
        for (int i = 0; i < this.superInterfaceTypes.size(); i++) {
            KotlinType kotlinType = this.superInterfaceTypes.get(i);
            bothSignatureWriter.writeInterface();
            if (this.samType != null && kotlinType.getConstructor() == this.samType.getType().getConstructor()) {
                typeMapSupertype = this.typeMapper.mapSupertype(kotlinType, null);
                bothSignatureWriter.writeAsmType(typeMapSupertype);
            } else {
                typeMapSupertype = this.typeMapper.mapSupertype(kotlinType, bothSignatureWriter);
            }
            strArr[i] = typeMapSupertype.getInternalName();
            bothSignatureWriter.writeInterfaceEnd();
        }
        this.v.defineClass((PsiElement) this.element, this.state.getConfig().getClassFileVersion(), DescriptorAsmUtil.getSyntheticAccessFlagForLambdaClass(this.classDescriptor) | this.visibilityFlag | 48, this.asmType.getInternalName(), bothSignatureWriter.makeJavaGenericSignature(), this.superClassAsmType.getInternalName(), strArr);
        initDefaultSourceMappingIfNeeded();
        this.v.visitSource(((KtElement) this.element).getContainingFile().get$name(), null);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected ClassDescriptor classForInnerClassRecord() {
        if (JvmCodegenUtil.isArgumentWhichWillBeInlined(this.bindingContext, this.funDescriptor)) {
            return null;
        }
        return this.classDescriptor;
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        generateBridges();
        generateClosureBody();
        SamType samType = this.samType;
        if (samType != null) {
            SamWrapperCodegen.generateDelegatesToDefaultImpl(this.asmType, this.classDescriptor, samType.getClassDescriptor(), this.functionCodegen, this.state);
        }
        this.constructor = generateConstructor();
        if (JvmCodegenUtil.isConst(this.closure)) {
            Type type = this.asmType;
            generateConstInstance(type, type);
        }
        DescriptorAsmUtil.genClosureFields(this.closure, this.v, this.typeMapper, this.state.getLanguageVersionSettings());
    }

    protected void generateClosureBody() {
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin((PsiElement) this.element, this.funDescriptor), this.funDescriptor, this.strategy);
        if (this.isLegacyFunctionReference) {
            generateFunctionReferenceMethods(this.functionReferenceTarget);
        }
        this.functionCodegen.generateDefaultIfNeeded(this.context.intoFunction(this.funDescriptor), this.funDescriptor, this.context.getContextKind(), DefaultParameterValueLoader.DEFAULT, null);
    }

    protected void generateBridges() {
        FunctionDescriptor originalAbstractMethod;
        SamType samType = this.samType;
        if (samType == null) {
            originalAbstractMethod = getErasedInvokeFunction(this.funDescriptor);
        } else {
            originalAbstractMethod = samType.getOriginalAbstractMethod();
        }
        generateBridge(this.typeMapper.mapAsmMethod(originalAbstractMethod), CollectionsKt.map(originalAbstractMethod.getValueParameters(), new Function1() { // from class: org.jetbrains.kotlin.codegen.ClosureCodegen$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((ValueParameterDescriptor) obj).getType();
            }
        }), originalAbstractMethod.getReturnType(), this.typeMapper.mapAsmMethod(this.funDescriptor), this.funDescriptor.getReturnType(), JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke(originalAbstractMethod));
        if (this.samType != null) {
            generateBridgesForSAM(originalAbstractMethod, this.funDescriptor, this.functionCodegen);
        }
    }

    static void generateBridgesForSAM(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, FunctionCodegen functionCodegen) {
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(functionDescriptor2.getContainingModule(), functionDescriptor2.getAnnotations(), functionDescriptor.getShortName(), CallableMemberDescriptor.Kind.DECLARATION, functionDescriptor2.getSourceElement());
        simpleFunctionDescriptorImplCreate.initialize(functionDescriptor.getExtensionReceiverParameter(), functionDescriptor.mo6753getDispatchReceiverParameter(), functionDescriptor.getContextReceiverParameters(), (List<? extends TypeParameterDescriptor>) functionDescriptor.getTypeParameters(), functionDescriptor.getValueParameters(), functionDescriptor.getReturnType(), Modality.OPEN, functionDescriptor.getVisibility());
        simpleFunctionDescriptorImplCreate.setSuspend(functionDescriptor2.isSuspend());
        DescriptorUtilsKt.setSingleOverridden(simpleFunctionDescriptorImplCreate, functionDescriptor);
        functionCodegen.generateBridges(simpleFunctionDescriptorImplCreate);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) CodegenUtilKt.unwrapFrontendVersion(this.funDescriptor);
        Method method = (Method) this.v.getSerializationBindings().get(JvmSerializationBindings.METHOD_FOR_FUNCTION, functionDescriptor);
        FunctionDescriptor functionDescriptorCreateFreeFakeLambdaDescriptor = FakeDescriptorsForReferencesKt.createFreeFakeLambdaDescriptor(functionDescriptor, this.state.getTypeApproximator());
        this.v.getSerializationBindings().put(JvmSerializationBindings.METHOD_FOR_FUNCTION, functionDescriptorCreateFreeFakeLambdaDescriptor, method);
        final DescriptorSerializer descriptorSerializerCreateForLambda = DescriptorSerializer.createForLambda(new JvmSerializerExtension(this.v.getSerializationBindings(), this.state), this.state.getLanguageVersionSettings());
        ProtoBuf.Function.Builder builderFunctionProto = descriptorSerializerCreateForLambda.functionProto(functionDescriptorCreateFreeFakeLambdaDescriptor);
        if (builderFunctionProto == null) {
            return;
        }
        final ProtoBuf.Function functionBuild = builderFunctionProto.build();
        WriteAnnotationUtilKt.writeKotlinMetadata(this.v, this.state, KotlinClassHeader.Kind.SYNTHETIC_CLASS, InlineUtil.isInPublicInlineScope(functionDescriptor), 0, new Function1() { // from class: org.jetbrains.kotlin.codegen.ClosureCodegen$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ClosureCodegen.lambda$generateKotlinMetadataAnnotation$0(descriptorSerializerCreateForLambda, functionBuild, (AnnotationVisitor) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$generateKotlinMetadataAnnotation$0(DescriptorSerializer descriptorSerializer, ProtoBuf.Function function, AnnotationVisitor annotationVisitor) {
        DescriptorAsmUtil.writeAnnotationData(annotationVisitor, descriptorSerializer, function);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void done() {
        writeOuterClassAndEnclosingMethod();
        super.done();
    }

    public StackValue putInstanceOnStack(final ExpressionCodegen expressionCodegen, final StackValue stackValue) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(6);
        }
        StackValue stackValueOperation = StackValue.operation(this.functionReferenceTarget != null ? AsmTypes.K_FUNCTION : this.asmType, new Function1() { // from class: org.jetbrains.kotlin.codegen.ClosureCodegen$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6378x2f5172c9(expressionCodegen, stackValue, (InstructionAdapter) obj);
            }
        });
        if (stackValueOperation == null) {
            $$$reportNull$$$0(7);
        }
        return stackValueOperation;
    }

    /* JADX INFO: renamed from: lambda$putInstanceOnStack$1$org-jetbrains-kotlin-codegen-ClosureCodegen, reason: not valid java name */
    /* synthetic */ Unit m6378x2f5172c9(ExpressionCodegen expressionCodegen, StackValue stackValue, InstructionAdapter instructionAdapter) {
        if (JvmCodegenUtil.isConst(this.closure)) {
            instructionAdapter.getstatic(this.asmType.getInternalName(), JvmAbi.INSTANCE_FIELD, this.asmType.getDescriptor());
        } else {
            instructionAdapter.anew(this.asmType);
            instructionAdapter.dup();
            expressionCodegen.pushClosureOnStack(this.classDescriptor, true, expressionCodegen.defaultCallGenerator, stackValue);
            instructionAdapter.invokespecial(this.asmType.getInternalName(), "<init>", this.constructor.getDescriptor(), false);
        }
        return Unit.INSTANCE;
    }

    private void generateBridge(Method method, List<KotlinType> list, KotlinType kotlinType, Method method2, KotlinType kotlinType2, boolean z) {
        StackValue stackValueArrayElement;
        if (method == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        if (method2 == null) {
            $$$reportNull$$$0(10);
        }
        if (method.equals(method2)) {
            return;
        }
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin((PsiElement) this.element, this.funDescriptor), 4161, method.getName(), method.getDescriptor(), null, ArrayUtil.EMPTY_STRING_ARRAY);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            MemberCodegen.markLineNumberForDescriptor((ClassDescriptor) DescriptorUtils.getParentOfType(this.funDescriptor, ClassDescriptor.class), instructionAdapter);
            Type[] argumentTypes = method.getArgumentTypes();
            if (z) {
                DescriptorAsmUtil.generateVarargInvokeArityAssert(instructionAdapter, method2.getArgumentTypes().length);
            }
            int i = 0;
            instructionAdapter.load(0, this.asmType);
            List listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(this.funDescriptor.getExtensionReceiverParameter()), (Iterable) this.funDescriptor.getValueParameters());
            int i2 = 1;
            int i3 = 0;
            int size = 1;
            while (i3 < listPlus.size()) {
                ParameterDescriptor parameterDescriptor = (ParameterDescriptor) listPlus.get(i3);
                KotlinType type = parameterDescriptor.getType();
                if (z) {
                    stackValueArrayElement = StackValue.arrayElement(AsmTypes.OBJECT_TYPE, null, StackValue.local(i2, argumentTypes[i], list.get(i)), StackValue.constant(i3));
                } else {
                    Type type2 = argumentTypes[i3];
                    StackValue.Local local = StackValue.local(size, type2, list.get(i3));
                    size += type2.getSize();
                    stackValueArrayElement = local;
                }
                if (InlineClassesCodegenUtilKt.isInlineClassWithUnderlyingTypeAnyOrAnyN(type) && this.functionReferenceCall == null) {
                    type = (KotlinType) DescriptorUtilsKt.getInlineClassRepresentation(TypeUtils.getClassDescriptor(type)).getUnderlyingType();
                }
                stackValueArrayElement.put(this.typeMapper.mapType(parameterDescriptor), type, instructionAdapter);
                i3++;
                i = 0;
                i2 = 1;
            }
            instructionAdapter.invokevirtual(this.asmType.getInternalName(), method2.getName(), method2.getDescriptor(), false);
            StackValue.onStack(method2.getReturnType(), kotlinType2).put(method.getReturnType(), kotlinType, instructionAdapter);
            instructionAdapter.areturn(method.getReturnType());
            FunctionCodegen.endVisit(methodVisitorNewMethod, "bridge", (KtElement) this.element);
        }
    }

    private void generateFunctionReferenceMethods(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        boolean z = this.state.getClassBuilderMode().generateBodies;
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 17, "getOwner", Type.getMethodDescriptor(AsmTypes.K_DECLARATION_CONTAINER_TYPE, new Type[0]), null, null);
        if (z) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            CallableReferenceUtilKt.generateCallableReferenceDeclarationContainer(instructionAdapter, functionDescriptor, this.state);
            instructionAdapter.areturn(AsmTypes.K_DECLARATION_CONTAINER_TYPE);
            FunctionCodegen.endVisit((MethodVisitor) instructionAdapter, "function reference getOwner", (KtElement) this.element);
        }
        MethodVisitor methodVisitorNewMethod2 = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 17, "getName", Type.getMethodDescriptor(AsmTypes.JAVA_STRING_TYPE, new Type[0]), null, null);
        if (z) {
            methodVisitorNewMethod2.visitCode();
            InstructionAdapter instructionAdapter2 = new InstructionAdapter(methodVisitorNewMethod2);
            instructionAdapter2.aconst(functionDescriptor.getShortName().asString());
            instructionAdapter2.areturn(AsmTypes.JAVA_STRING_TYPE);
            FunctionCodegen.endVisit((MethodVisitor) instructionAdapter2, "function reference getName", (KtElement) this.element);
        }
        MethodVisitor methodVisitorNewMethod3 = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 17, "getSignature", Type.getMethodDescriptor(AsmTypes.JAVA_STRING_TYPE, new Type[0]), null, null);
        if (z) {
            methodVisitorNewMethod3.visitCode();
            InstructionAdapter instructionAdapter3 = new InstructionAdapter(methodVisitorNewMethod3);
            CallableReferenceUtilKt.generateFunctionReferenceSignature(instructionAdapter3, functionDescriptor, this.state);
            instructionAdapter3.areturn(AsmTypes.JAVA_STRING_TYPE);
            FunctionCodegen.endVisit((MethodVisitor) instructionAdapter3, "function reference getSignature", (KtElement) this.element);
        }
    }

    protected Method generateConstructor() {
        Type fieldType;
        int iIntValue;
        KotlinType fieldKotlinType;
        List<FieldInfo> listCalculateConstructorParameters = calculateConstructorParameters(this.typeMapper, this.state.getLanguageVersionSettings(), this.closure, this.asmType);
        Method method = new Method("<init>", Type.VOID_TYPE, fieldListToTypeArray(listCalculateConstructorParameters));
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin((PsiElement) this.element, this.funDescriptor), this.visibilityFlag, "<init>", method.getDescriptor(), null, ArrayUtil.EMPTY_STRING_ARRAY);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            Pair<Integer, FieldInfo> pairGenerateClosureFieldsInitializationFromParameters = CallableReferenceUtilKt.generateClosureFieldsInitializationFromParameters(instructionAdapter, this.closure, listCalculateConstructorParameters);
            boolean z = this.shouldHaveBoundReferenceReceiver;
            if (z && pairGenerateClosureFieldsInitializationFromParameters == null) {
                throw new AssertionError("No bound reference receiver in constructor parameters: " + listCalculateConstructorParameters);
            }
            if (z) {
                iIntValue = pairGenerateClosureFieldsInitializationFromParameters.getFirst().intValue();
                fieldType = pairGenerateClosureFieldsInitializationFromParameters.getSecond().getFieldType();
                fieldKotlinType = pairGenerateClosureFieldsInitializationFromParameters.getSecond().getFieldKotlinType();
            } else {
                fieldType = null;
                iIntValue = -1;
                fieldKotlinType = null;
            }
            instructionAdapter.load(0, this.superClassAsmType);
            ArrayList arrayList = new ArrayList();
            if (this.superClassAsmType.equals(AsmTypes.LAMBDA) || this.functionReferenceTarget != null || CoroutineCodegenUtilKt.isCoroutineSuperClass(this.superClassAsmType.getInternalName())) {
                instructionAdapter.iconst(CodegenUtilKt.getArity(this.funDescriptor));
                arrayList.add(Type.INT_TYPE);
                if (this.shouldHaveBoundReferenceReceiver) {
                    CallableReferenceUtilKt.loadBoundReferenceReceiverParameter(instructionAdapter, iIntValue, fieldType, fieldKotlinType);
                    arrayList.add(AsmTypes.OBJECT_TYPE);
                }
                if (this.isOptimizedFunctionReference || this.isAdaptedFunctionReference) {
                    CallableReferenceUtilKt.generateCallableReferenceDeclarationContainerClass(instructionAdapter, this.functionReferenceTarget, this.state);
                    instructionAdapter.aconst(this.functionReferenceTarget.getShortName().asString());
                    CallableReferenceUtilKt.generateFunctionReferenceSignature(instructionAdapter, this.functionReferenceTarget, this.state);
                    instructionAdapter.aconst(Integer.valueOf(CallableReferenceUtilKt.getCallableReferenceTopLevelFlag(this.functionReferenceTarget) + (calculateFunctionReferenceFlags(this.functionReferenceCall, this.funDescriptor) << 1)));
                    arrayList.add(AsmTypes.JAVA_CLASS_TYPE);
                    arrayList.add(AsmTypes.JAVA_STRING_TYPE);
                    arrayList.add(AsmTypes.JAVA_STRING_TYPE);
                    arrayList.add(Type.INT_TYPE);
                }
            }
            instructionAdapter.invokespecial(this.superClassAsmType.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, (Type[]) arrayList.toArray(new Type[0])), false);
            instructionAdapter.visitInsn(177);
            FunctionCodegen.endVisit((MethodVisitor) instructionAdapter, JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, (KtElement) this.element);
        }
        return method;
    }

    private static int calculateFunctionReferenceFlags(ResolvedCall<?> resolvedCall, FunctionDescriptor functionDescriptor) {
        int i;
        int i2;
        if (resolvedCall == null) {
            $$$reportNull$$$0(13);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) resolvedCall.getResultingDescriptor();
        int i3 = (resolvedCall.getDispatchReceiver() instanceof TransientReceiver ? 1 : 0) + (resolvedCall.getExtensionReceiver() instanceof TransientReceiver ? 1 : 0);
        int i4 = i3;
        while (true) {
            if (i4 < functionDescriptor.getValueParameters().size() && (i2 = i4 - i3) < functionDescriptor2.getValueParameters().size()) {
                ValueParameterDescriptor valueParameterDescriptor = functionDescriptor2.getValueParameters().get(i2);
                ValueParameterDescriptor valueParameterDescriptor2 = functionDescriptor.getValueParameters().get(i4);
                if (valueParameterDescriptor.getVarargElementType() != null && !valueParameterDescriptor.getType().equals(valueParameterDescriptor2.getType())) {
                    i = 1;
                    break;
                }
                i4++;
            } else {
                i = 0;
                break;
            }
        }
        return i + (!functionDescriptor2.isSuspend() && functionDescriptor.isSuspend() ? 2 : 0) + (((!KotlinBuiltIns.isUnit(functionDescriptor.getReturnType()) || KotlinBuiltIns.isUnit(functionDescriptor2.getReturnType())) ? 0 : 1) << 2);
    }

    public static List<FieldInfo> calculateConstructorParameters(KotlinTypeMapper kotlinTypeMapper, LanguageVersionSettings languageVersionSettings, CalculatedClosure calculatedClosure, Type type) {
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(15);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(16);
        }
        if (calculatedClosure == null) {
            $$$reportNull$$$0(17);
        }
        if (type == null) {
            $$$reportNull$$$0(18);
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        ClassDescriptor capturedOuterClassDescriptor = calculatedClosure.getCapturedOuterClassDescriptor();
        if (capturedOuterClassDescriptor != null) {
            SimpleType defaultType = capturedOuterClassDescriptor.getDefaultType();
            arrayListNewArrayList.add(FieldInfo.createForHiddenField(type, kotlinTypeMapper.mapType(defaultType), defaultType, AsmUtil.CAPTURED_THIS_FIELD));
        }
        KotlinType capturedReceiverFromOuterContext = calculatedClosure.getCapturedReceiverFromOuterContext();
        if (capturedReceiverFromOuterContext != null) {
            arrayListNewArrayList.add(FieldInfo.createForHiddenField(type, kotlinTypeMapper.mapType(capturedReceiverFromOuterContext), capturedReceiverFromOuterContext, calculatedClosure.getCapturedReceiverFieldName(kotlinTypeMapper.getBindingContext(), languageVersionSettings)));
        }
        for (EnclosedValueDescriptor enclosedValueDescriptor : calculatedClosure.getCaptureVariables().values()) {
            DeclarationDescriptor descriptor = enclosedValueDescriptor.getDescriptor();
            if (((descriptor instanceof VariableDescriptor) && !(descriptor instanceof PropertyDescriptor)) || ExpressionTypingUtils.isLocalFunction(descriptor)) {
                arrayListNewArrayList.add(FieldInfo.createForHiddenField(type, enclosedValueDescriptor.getType(), enclosedValueDescriptor.getKotlinType(), enclosedValueDescriptor.getFieldName()));
            } else {
                boolean z = descriptor instanceof FunctionDescriptor;
            }
        }
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(19);
        }
        return arrayListNewArrayList;
    }

    private static Type[] fieldListToTypeArray(List<FieldInfo> list) {
        int size = list.size();
        Type[] typeArr = new Type[size];
        for (int i = 0; i != size; i++) {
            typeArr[i] = list.get(i).getFieldType();
        }
        return typeArr;
    }

    public static FunctionDescriptor getErasedInvokeFunction(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        SimpleFunctionDescriptor next = DescriptorUtilsKt.getBuiltIns(functionDescriptor).getFunction(functionDescriptor.getValueParameters().size() + (functionDescriptor.getExtensionReceiverParameter() != null ? 1 : 0)).getDefaultType().getMemberScope().getContributedFunctions(OperatorNameConventions.INVOKE, NoLookupLocation.FROM_BACKEND).iterator().next();
        if (next == null) {
            $$$reportNull$$$0(21);
        }
        return next;
    }

    public boolean isCallableReference() {
        return this.functionReferenceTarget != null;
    }
}
