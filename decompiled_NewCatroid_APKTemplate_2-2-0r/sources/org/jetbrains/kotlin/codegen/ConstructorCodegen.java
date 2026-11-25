package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ConstructorContext;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class ConstructorCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BindingContext bindingContext;
    private final Type classAsmType;
    private final ClassBodyCodegen classBodyCodegen;
    private final FieldOwnerContext<?> context;
    private final ClassDescriptor descriptor;
    private final FunctionCodegen functionCodegen;
    private final OwnerKind kind;
    private final MemberCodegen<?> memberCodegen;
    private final KtPureClassOrObject myClass;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;
    private final ClassBuilder v;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "context";
                break;
            case 2:
            case 14:
            case 18:
            case 22:
            case 26:
            case 30:
            case 36:
            case 44:
                objArr[0] = "codegen";
                break;
            case 3:
                objArr[0] = "memberCodegen";
                break;
            case 4:
                objArr[0] = "classBodyCodegen";
                break;
            case 5:
                objArr[0] = "state";
                break;
            case 6:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 7:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 8:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 9:
                objArr[0] = "myClass";
                break;
            case 10:
                objArr[0] = "bindingContext";
                break;
            case 11:
            case 15:
            case 17:
            case 21:
            case 27:
            case 37:
                objArr[0] = "constructorDescriptor";
                break;
            case 12:
            case 16:
            case 20:
            case 23:
                objArr[0] = "superClassAsmType";
                break;
            case 13:
            case 24:
            case 25:
            case 29:
            case 35:
                objArr[0] = "iv";
                break;
            case 19:
                objArr[0] = "fieldsInfo";
                break;
            case 28:
                objArr[0] = "delegationConstructorCall";
                break;
            case 31:
                objArr[0] = "delegatingConstructor";
                break;
            case 32:
                objArr[0] = "delegatingCallable";
                break;
            case 33:
                objArr[0] = "delegatingParameters";
                break;
            case 34:
            case 42:
                objArr[0] = "parameters";
                break;
            case 38:
                objArr[0] = "superConstructor";
                break;
            case 39:
                objArr[0] = "superConstructorCall";
                break;
            case 40:
                objArr[0] = "superCallable";
                break;
            case 41:
                objArr[0] = "superParameters";
                break;
            case 43:
            default:
                objArr[0] = "descriptor";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/ConstructorCodegen";
        switch (i) {
            case 11:
            case 12:
                objArr[2] = "generateSecondaryConstructor";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                objArr[2] = "generateDelegatorToConstructorCall";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "generatePrimaryConstructorImpl";
                break;
            case 21:
            case 22:
            case 23:
                objArr[2] = "generateSecondaryConstructorImpl";
                break;
            case 24:
                objArr[2] = "generateClosureInitialization";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "generateDelegationConstructorCall";
                break;
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "generateThisCallImplicitArguments";
                break;
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
                objArr[2] = "generateSuperCallImplicitArguments";
                break;
            case 43:
            case 44:
                objArr[2] = "markLineNumberForConstructor";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static /* synthetic */ ExpressionCodegen lambda$generatePrimaryConstructorImpl$0(ExpressionCodegen expressionCodegen) {
        return expressionCodegen;
    }

    static /* synthetic */ ExpressionCodegen lambda$generatePrimaryConstructorImpl$1(ExpressionCodegen expressionCodegen) {
        return expressionCodegen;
    }

    static /* synthetic */ ExpressionCodegen lambda$generateSecondaryConstructorImpl$2(ExpressionCodegen expressionCodegen) {
        return expressionCodegen;
    }

    public ConstructorCodegen(ClassDescriptor classDescriptor, FieldOwnerContext<?> fieldOwnerContext, FunctionCodegen functionCodegen, MemberCodegen<?> memberCodegen, ClassBodyCodegen classBodyCodegen, GenerationState generationState, OwnerKind ownerKind, ClassBuilder classBuilder, Type type, KtPureClassOrObject ktPureClassOrObject, BindingContext bindingContext) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (fieldOwnerContext == null) {
            $$$reportNull$$$0(1);
        }
        if (functionCodegen == null) {
            $$$reportNull$$$0(2);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(3);
        }
        if (classBodyCodegen == null) {
            $$$reportNull$$$0(4);
        }
        if (generationState == null) {
            $$$reportNull$$$0(5);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(6);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(7);
        }
        if (type == null) {
            $$$reportNull$$$0(8);
        }
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(9);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(10);
        }
        this.descriptor = classDescriptor;
        this.context = fieldOwnerContext;
        this.functionCodegen = functionCodegen;
        this.memberCodegen = memberCodegen;
        this.classBodyCodegen = classBodyCodegen;
        this.state = generationState;
        this.kind = ownerKind;
        this.v = classBuilder;
        this.classAsmType = type;
        this.myClass = ktPureClassOrObject;
        this.bindingContext = bindingContext;
        this.typeMapper = generationState.getTypeMapper();
    }

    public void generatePrimaryConstructor(final DelegationFieldsInfo delegationFieldsInfo, final Type type) {
        final ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
        if (JvmCodegenUtil.isJvmInterface(this.descriptor) || (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = this.descriptor.mo6347getUnsubstitutedPrimaryConstructor()) == null) {
            return;
        }
        ConstructorContext constructorContextIntoConstructor = this.context.intoConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, this.typeMapper);
        final KtPrimaryConstructor primaryConstructor = this.myClass.getPrimaryConstructor();
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin(primaryConstructor != null ? primaryConstructor : this.myClass.getPsiOrParent(), classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor), classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, constructorContextIntoConstructor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.ConstructorCodegen.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "codegen";
                } else {
                    objArr[0] = "signature";
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/ConstructorCodegen$1";
                objArr[2] = "doGenerateBody";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                if (expressionCodegen == null) {
                    $$$reportNull$$$0(0);
                }
                if (jvmMethodSignature == null) {
                    $$$reportNull$$$0(1);
                }
                ConstructorCodegen.this.generatePrimaryConstructorImpl(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, expressionCodegen, delegationFieldsInfo, primaryConstructor, type);
            }
        });
        this.functionCodegen.generateDefaultIfNeeded(constructorContextIntoConstructor, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, this.context.getContextKind() == OwnerKind.ERASED_INLINE_CLASS ? OwnerKind.ERASED_INLINE_CLASS : OwnerKind.IMPLEMENTATION, DefaultParameterValueLoader.DEFAULT, null);
        registerAccessorForHiddenConstructorIfNeeded(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
        new DefaultParameterValueSubstitutor(this.state).generatePrimaryConstructorOverloadsIfNeeded(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, this.v, this.memberCodegen, this.kind, this.myClass);
    }

    private void registerAccessorForHiddenConstructorIfNeeded(ClassConstructorDescriptor classConstructorDescriptor) {
        if (DescriptorAsmUtil.isHiddenConstructor(classConstructorDescriptor)) {
            this.context.getAccessor(classConstructorDescriptor, AccessorKind.NORMAL, null, null);
        }
    }

    public void generateSecondaryConstructor(final ClassConstructorDescriptor classConstructorDescriptor, final Type type) {
        KtSecondaryConstructor ktSecondaryConstructor;
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        if (type == null) {
            $$$reportNull$$$0(12);
        }
        if (DescriptorUtils.canHaveDeclaredConstructors(this.descriptor) && (ktSecondaryConstructor = (KtSecondaryConstructor) DescriptorToSourceUtils.descriptorToDeclaration(classConstructorDescriptor)) != null) {
            ConstructorContext constructorContextIntoConstructor = this.context.intoConstructor(classConstructorDescriptor, this.typeMapper);
            this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin(classConstructorDescriptor), classConstructorDescriptor, constructorContextIntoConstructor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.ConstructorCodegen.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = "codegen";
                    } else {
                        objArr[0] = "signature";
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/ConstructorCodegen$2";
                    objArr[2] = "doGenerateBody";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                    if (expressionCodegen == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (jvmMethodSignature == null) {
                        $$$reportNull$$$0(1);
                    }
                    ConstructorCodegen.this.generateSecondaryConstructorImpl(classConstructorDescriptor, expressionCodegen, type);
                }
            });
            this.functionCodegen.generateDefaultIfNeeded(constructorContextIntoConstructor, classConstructorDescriptor, this.context.getContextKind() == OwnerKind.ERASED_INLINE_CLASS ? OwnerKind.ERASED_INLINE_CLASS : OwnerKind.IMPLEMENTATION, DefaultParameterValueLoader.DEFAULT, null);
            new DefaultParameterValueSubstitutor(this.state).generateOverloadsIfNeeded(ktSecondaryConstructor, classConstructorDescriptor, classConstructorDescriptor, this.kind, this.v, this.memberCodegen);
            registerAccessorForHiddenConstructorIfNeeded(classConstructorDescriptor);
        }
    }

    private void generateDelegatorToConstructorCall(InstructionAdapter instructionAdapter, ExpressionCodegen expressionCodegen, ClassConstructorDescriptor classConstructorDescriptor, ResolvedCall<ConstructorDescriptor> resolvedCall, Type type) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(13);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(14);
        }
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (type == null) {
            $$$reportNull$$$0(16);
        }
        MethodContext methodContext = expressionCodegen.context;
        if (resolvedCall == null) {
            genSimpleSuperCall(instructionAdapter, type);
        } else {
            generateDelegationConstructorCall(instructionAdapter, expressionCodegen, classConstructorDescriptor, resolvedCall);
        }
        ((ConstructorContext) methodContext).setThisInitialized(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generatePrimaryConstructorImpl(ClassConstructorDescriptor classConstructorDescriptor, final ExpressionCodegen expressionCodegen, DelegationFieldsInfo delegationFieldsInfo, KtPrimaryConstructor ktPrimaryConstructor, Type type) {
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(18);
        }
        if (delegationFieldsInfo == null) {
            $$$reportNull$$$0(19);
        }
        if (type == null) {
            $$$reportNull$$$0(20);
        }
        InstructionAdapter instructionAdapter = expressionCodegen.v;
        markLineNumberForConstructor(classConstructorDescriptor, ktPrimaryConstructor, expressionCodegen);
        if (OwnerKind.ERASED_INLINE_CLASS == this.kind) {
            this.memberCodegen.generateInitializers(new Function0() { // from class: org.jetbrains.kotlin.codegen.ConstructorCodegen$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ConstructorCodegen.lambda$generatePrimaryConstructorImpl$0(expressionCodegen);
                }
            });
            Type typeMapType = this.typeMapper.mapType(classConstructorDescriptor.getContainingModule());
            instructionAdapter.load(0, typeMapType);
            instructionAdapter.areturn(typeMapType);
            return;
        }
        generateClosureInitialization(instructionAdapter);
        generateDelegatorToConstructorCall(instructionAdapter, expressionCodegen, classConstructorDescriptor, BindingContextUtils.getDelegationConstructorCall(this.bindingContext, classConstructorDescriptor), type);
        for (KtSuperTypeListEntry ktSuperTypeListEntry : this.myClass.getSuperTypeListEntries()) {
            if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                genCallToDelegatorByExpressionSpecifier(instructionAdapter, expressionCodegen, (KtDelegatedSuperTypeEntry) ktSuperTypeListEntry, delegationFieldsInfo);
            }
        }
        List<ValueParameterDescriptor> valueParameters = classConstructorDescriptor.getValueParameters();
        int i = 0;
        for (KtParameter ktParameter : this.classBodyCodegen.getPrimaryConstructorParameters()) {
            if (ktParameter.hasValOrVar()) {
                ValueParameterDescriptor valueParameterDescriptor = valueParameters.get(i);
                Type typeMapType2 = this.typeMapper.mapType(valueParameterDescriptor);
                instructionAdapter.load(0, this.classAsmType);
                instructionAdapter.load(expressionCodegen.myFrameMap.getIndex(valueParameterDescriptor), typeMapType2);
                instructionAdapter.putfield(this.classAsmType.getInternalName(), this.context.getFieldName((PropertyDescriptor) this.bindingContext.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter), false), typeMapType2.getDescriptor());
            }
            i++;
        }
        if (!DescriptorUtils.isObject(this.descriptor) && !InlineClassesUtilsKt.isInlineClass(this.descriptor)) {
            this.memberCodegen.generateInitializers(new Function0() { // from class: org.jetbrains.kotlin.codegen.ConstructorCodegen$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ConstructorCodegen.lambda$generatePrimaryConstructorImpl$1(expressionCodegen);
                }
            });
        }
        instructionAdapter.visitInsn(177);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void generateSecondaryConstructorImpl(ClassConstructorDescriptor classConstructorDescriptor, final ExpressionCodegen expressionCodegen, Type type) {
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(22);
        }
        if (type == null) {
            $$$reportNull$$$0(23);
        }
        InstructionAdapter instructionAdapter = expressionCodegen.v;
        KtSecondaryConstructor ktSecondaryConstructor = (KtSecondaryConstructor) DescriptorToSourceUtils.descriptorToDeclaration(classConstructorDescriptor);
        markLineNumberForConstructor(classConstructorDescriptor, ktSecondaryConstructor, expressionCodegen);
        ResolvedCall<ConstructorDescriptor> delegationConstructorCall = BindingContextUtils.getDelegationConstructorCall(this.bindingContext, classConstructorDescriptor);
        ConstructorDescriptor constructorDescriptor = delegationConstructorCall == null ? null : (ConstructorDescriptor) delegationConstructorCall.getResultingDescriptor();
        generateDelegatorToConstructorCall(instructionAdapter, expressionCodegen, classConstructorDescriptor, delegationConstructorCall, type);
        if (!isSameClassConstructor(constructorDescriptor)) {
            generateClosureInitialization(instructionAdapter);
            this.memberCodegen.generateInitializers(new Function0() { // from class: org.jetbrains.kotlin.codegen.ConstructorCodegen$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return ConstructorCodegen.lambda$generateSecondaryConstructorImpl$2(expressionCodegen);
                }
            });
        }
        if (ktSecondaryConstructor.hasBody()) {
            expressionCodegen.gen(ktSecondaryConstructor.getBodyExpression(), Type.VOID_TYPE);
        }
        if (OwnerKind.ERASED_INLINE_CLASS == this.kind) {
            instructionAdapter.areturn(this.typeMapper.mapType(classConstructorDescriptor.getContainingModule()));
        } else {
            instructionAdapter.visitInsn(177);
        }
    }

    private void genCallToDelegatorByExpressionSpecifier(InstructionAdapter instructionAdapter, ExpressionCodegen expressionCodegen, KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, DelegationFieldsInfo delegationFieldsInfo) {
        KtExpression delegateExpression = ktDelegatedSuperTypeEntry.getDelegateExpression();
        DelegationFieldsInfo.Field info = delegationFieldsInfo.getInfo(ktDelegatedSuperTypeEntry);
        if (info != null && info.generateField) {
            instructionAdapter.load(0, this.classAsmType);
            info.getStackValue().store(expressionCodegen.gen(delegateExpression), instructionAdapter);
        }
    }

    private boolean isSameClassConstructor(ConstructorDescriptor constructorDescriptor) {
        return constructorDescriptor != null && constructorDescriptor.getContainingModule() == this.descriptor;
    }

    private void generateClosureInitialization(InstructionAdapter instructionAdapter) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(24);
        }
        MutableClosure mutableClosure = this.context.closure;
        if (mutableClosure != null) {
            Iterator<FieldInfo> it = ClosureCodegen.calculateConstructorParameters(this.typeMapper, this.state.getLanguageVersionSettings(), mutableClosure, this.classAsmType).iterator();
            int iGenAssignInstanceFieldFromParam = 1;
            while (it.hasNext()) {
                iGenAssignInstanceFieldFromParam = DescriptorAsmUtil.genAssignInstanceFieldFromParam(it.next(), iGenAssignInstanceFieldFromParam, instructionAdapter);
            }
        }
    }

    private void generateDelegationConstructorCall(InstructionAdapter instructionAdapter, ExpressionCodegen expressionCodegen, ClassConstructorDescriptor classConstructorDescriptor, ResolvedCall<ConstructorDescriptor> resolvedCall) {
        ArgumentGenerator argumentGeneratorGenerateSuperCallImplicitArguments;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(25);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(26);
        }
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(28);
        }
        if (OwnerKind.ERASED_INLINE_CLASS != this.kind) {
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        }
        ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) SamCodegenUtil.resolveSamAdapter(expressionCodegen.getConstructorDescriptor(resolvedCall));
        KotlinTypeMapper typeMapper = this.state.getTypeMapper();
        CallableMethod callableMethodMapToCallableMethod = typeMapper.mapToCallableMethod(constructorDescriptor, false, this.kind);
        CallableMethod callableMethodMapToCallableMethod2 = typeMapper.mapToCallableMethod(classConstructorDescriptor, false, this.kind);
        List<JvmMethodParameterSignature> valueParameters = callableMethodMapToCallableMethod.getValueParameters();
        List<JvmMethodParameterSignature> valueParameters2 = callableMethodMapToCallableMethod2.getValueParameters();
        if (isSameClassConstructor(constructorDescriptor)) {
            argumentGeneratorGenerateSuperCallImplicitArguments = generateThisCallImplicitArguments(instructionAdapter, expressionCodegen, constructorDescriptor, callableMethodMapToCallableMethod, valueParameters, valueParameters2);
        } else {
            argumentGeneratorGenerateSuperCallImplicitArguments = generateSuperCallImplicitArguments(instructionAdapter, expressionCodegen, classConstructorDescriptor, constructorDescriptor, resolvedCall, callableMethodMapToCallableMethod, valueParameters, valueParameters2);
        }
        expressionCodegen.invokeMethodWithArguments(callableMethodMapToCallableMethod, resolvedCall, StackValue.none(), expressionCodegen.defaultCallGenerator, argumentGeneratorGenerateSuperCallImplicitArguments);
    }

    private void genSimpleSuperCall(InstructionAdapter instructionAdapter, Type type) {
        instructionAdapter.load(0, type);
        if (this.descriptor.getClassKind() == ClassKind.ENUM_CLASS || this.descriptor.getClassKind() == ClassKind.ENUM_ENTRY) {
            instructionAdapter.load(1, AsmTypes.JAVA_STRING_TYPE);
            instructionAdapter.load(2, Type.INT_TYPE);
            instructionAdapter.invokespecial(type.getInternalName(), "<init>", "(Ljava/lang/String;I)V", false);
            return;
        }
        instructionAdapter.invokespecial(type.getInternalName(), "<init>", "()V", false);
    }

    private static ArgumentGenerator generateThisCallImplicitArguments(InstructionAdapter instructionAdapter, ExpressionCodegen expressionCodegen, ConstructorDescriptor constructorDescriptor, CallableMethod callableMethod, List<JvmMethodParameterSignature> list, List<JvmMethodParameterSignature> list2) {
        JvmMethodParameterKind kind;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(29);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(30);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        if (callableMethod == null) {
            $$$reportNull$$$0(32);
        }
        if (list == null) {
            $$$reportNull$$$0(33);
        }
        if (list2 == null) {
            $$$reportNull$$$0(34);
        }
        int size = 1;
        for (int i = 0; i < list.size() && (kind = list.get(i).getKind()) != JvmMethodParameterKind.VALUE; i++) {
            if (i >= list2.size() || list2.get(i).getKind() != kind) {
                throw new AssertionError("Constructors of the same class should have the same set of implicit arguments: " + constructorDescriptor);
            }
            JvmMethodParameterSignature jvmMethodParameterSignature = list2.get(i);
            instructionAdapter.load(size, jvmMethodParameterSignature.getAsmType());
            size += jvmMethodParameterSignature.getAsmType().getSize();
        }
        return new CallBasedArgumentGenerator(expressionCodegen, expressionCodegen.defaultCallGenerator, constructorDescriptor.getValueParameters(), callableMethod.getValueParameterTypes());
    }

    private ArgumentGenerator generateSuperCallImplicitArguments(InstructionAdapter instructionAdapter, ExpressionCodegen expressionCodegen, ConstructorDescriptor constructorDescriptor, ConstructorDescriptor constructorDescriptor2, ResolvedCall<ConstructorDescriptor> resolvedCall, CallableMethod callableMethod, List<JvmMethodParameterSignature> list, List<JvmMethodParameterSignature> list2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(35);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(36);
        }
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(37);
        }
        if (constructorDescriptor2 == null) {
            $$$reportNull$$$0(38);
        }
        if (resolvedCall == null) {
            $$$reportNull$$$0(39);
        }
        if (callableMethod == null) {
            $$$reportNull$$$0(40);
        }
        if (list == null) {
            $$$reportNull$$$0(41);
        }
        if (list2 == null) {
            $$$reportNull$$$0(42);
        }
        int i = 0;
        int size = 1;
        for (JvmMethodParameterSignature jvmMethodParameterSignature : list2) {
            if (i >= list.size()) {
                break;
            }
            JvmMethodParameterKind kind = list.get(i).getKind();
            JvmMethodParameterKind kind2 = jvmMethodParameterSignature.getKind();
            Type asmType = jvmMethodParameterSignature.getAsmType();
            if (kind == JvmMethodParameterKind.VALUE && kind2 == JvmMethodParameterKind.SUPER_CALL_PARAM) {
                break;
            }
            if (kind == JvmMethodParameterKind.OUTER) {
                expressionCodegen.generateThisOrOuter((ClassDescriptor) constructorDescriptor2.getContainingModule().getContainingModule(), true, true).put(expressionCodegen.v);
            } else {
                if (kind2 == JvmMethodParameterKind.SUPER_CALL_PARAM || kind2 == JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL) {
                    instructionAdapter.load(size, asmType);
                }
                size += asmType.getSize();
            }
            i++;
            size += asmType.getSize();
        }
        if (DescriptorUtils.isAnonymousObject(this.descriptor)) {
            return new ObjectSuperCallArgumentGenerator(list.subList(i, list.size()), instructionAdapter, constructorDescriptor2.getValueParameters(), expressionCodegen.typeMapper, size, resolvedCall);
        }
        return new CallBasedArgumentGenerator(expressionCodegen, expressionCodegen.defaultCallGenerator, constructorDescriptor2.getValueParameters(), callableMethod.getValueParameterTypes());
    }

    private static void markLineNumberForConstructor(ClassConstructorDescriptor classConstructorDescriptor, KtConstructor ktConstructor, ExpressionCodegen expressionCodegen) {
        if (classConstructorDescriptor == null) {
            $$$reportNull$$$0(43);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(44);
        }
        if (ktConstructor == null) {
            MemberCodegen.markLineNumberForDescriptor(classConstructorDescriptor.getContainingModule(), expressionCodegen.v);
            return;
        }
        if (ktConstructor.hasBody() && (!(ktConstructor instanceof KtSecondaryConstructor) || ((KtSecondaryConstructor) ktConstructor).hasImplicitDelegationCall())) {
            KtBlockExpression bodyExpression = ktConstructor.getBodyExpression();
            List<KtExpression> statements = bodyExpression != null ? bodyExpression.getStatements() : Collections.emptyList();
            if (!statements.isEmpty()) {
                expressionCodegen.markStartLineNumber(statements.iterator().next());
                return;
            }
            if (bodyExpression != null) {
                ktConstructor = bodyExpression;
            }
            expressionCodegen.markStartLineNumber(ktConstructor);
            return;
        }
        expressionCodegen.markStartLineNumber(ktConstructor);
    }
}
