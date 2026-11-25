package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptorKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.DeclarationUtilKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClassBodyCodegen extends MemberCodegen<KtPureClassOrObject> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final ClassDescriptor descriptor;
    public final OwnerKind kind;
    public final KtPureClassOrObject myClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "context";
                break;
            case 2:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 3:
            case 10:
                objArr[0] = "state";
                break;
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/codegen/ClassBodyCodegen";
                break;
            case 6:
                objArr[0] = "interfaceFun";
                break;
            case 7:
                objArr[0] = "inheritedFun";
                break;
            case 8:
                objArr[0] = "receiverType";
                break;
            case 9:
                objArr[0] = "functionCodegen";
                break;
            default:
                objArr[0] = "myClass";
                break;
        }
        if (i == 4 || i == 5) {
            objArr[1] = "getPrimaryConstructorParameters";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/ClassBodyCodegen";
        }
        switch (i) {
            case 4:
            case 5:
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "generateDelegationToDefaultImpl";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected void generateConstructors() {
    }

    protected void generateDefaultImplsIfNeeded() {
    }

    protected void generateErasedInlineClassIfNeeded() {
    }

    protected void generateUnboxMethodForInlineClass() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected ClassBodyCodegen(KtPureClassOrObject ktPureClassOrObject, ClassContext classContext, ClassBuilder classBuilder, GenerationState generationState, MemberCodegen<?> memberCodegen) {
        super(generationState, memberCodegen, classContext, ktPureClassOrObject, classBuilder);
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(0);
        }
        if (classContext == null) {
            $$$reportNull$$$0(1);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(2);
        }
        if (generationState == null) {
            $$$reportNull$$$0(3);
        }
        this.myClass = ktPureClassOrObject;
        this.kind = classContext.getContextKind();
        this.descriptor = SyntheticClassOrObjectDescriptorKt.findClassDescriptor(ktPureClassOrObject, this.bindingContext);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        ArrayList arrayList = new ArrayList();
        if (this.kind != OwnerKind.DEFAULT_IMPLS && this.kind != OwnerKind.ERASED_INLINE_CLASS) {
            for (KtDeclaration ktDeclaration : this.myClass.getDeclarations()) {
                if (shouldProcessFirst(ktDeclaration)) {
                    if (ktDeclaration instanceof KtObjectDeclaration) {
                        KtObjectDeclaration ktObjectDeclaration = (KtObjectDeclaration) ktDeclaration;
                        if (ktObjectDeclaration.isCompanion()) {
                            arrayList.add(ktObjectDeclaration);
                            CodegenUtilKt.populateCompanionBackingFieldNamesToOuterContextIfNeeded(ktObjectDeclaration, this.context, this.state);
                        }
                    }
                    generateDeclaration(ktDeclaration);
                }
            }
        }
        for (KtDeclaration ktDeclaration2 : this.myClass.getDeclarations()) {
            if (!shouldProcessFirst(ktDeclaration2)) {
                generateDeclaration(ktDeclaration2);
            }
        }
        boolean zShouldGenerateNonClassMembers = shouldGenerateNonClassMembers();
        if (zShouldGenerateNonClassMembers) {
            generatePrimaryConstructorProperties();
            generateConstructors();
            generateDefaultImplsIfNeeded();
            generateErasedInlineClassIfNeeded();
            generateUnboxMethodForInlineClass();
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            genClassOrObject((KtObjectDeclaration) it.next());
        }
        if (this.kind != OwnerKind.DEFAULT_IMPLS) {
            for (DeclarationDescriptor declarationDescriptor : this.descriptor.getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.CLASSIFIERS, MemberScope.INSTANCE.getALL_NAME_FILTER())) {
                if (declarationDescriptor instanceof SyntheticClassOrObjectDescriptor) {
                    genSyntheticClassOrObject((SyntheticClassOrObjectDescriptor) declarationDescriptor);
                }
            }
        }
        if (zShouldGenerateNonClassMembers) {
            generateBridges();
        }
    }

    private void generateBridges() {
        if (DescriptorUtils.isInterface(this.descriptor)) {
            return;
        }
        for (DeclarationDescriptor declarationDescriptor : DescriptorUtils.getAllDescriptors(this.descriptor.getDefaultType().getMemberScope())) {
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                if (!callableMemberDescriptor.getKind().isReal() && DeclarationUtilKt.findInterfaceImplementation(callableMemberDescriptor) == null) {
                    if (callableMemberDescriptor instanceof FunctionDescriptor) {
                        this.functionCodegen.generateBridges((FunctionDescriptor) callableMemberDescriptor);
                    } else if (callableMemberDescriptor instanceof PropertyDescriptor) {
                        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableMemberDescriptor;
                        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                        if (getter != null) {
                            this.functionCodegen.generateBridges(getter);
                        }
                        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
                        if (setter != null) {
                            this.functionCodegen.generateBridges(setter);
                        }
                    }
                }
            }
        }
    }

    private boolean shouldGenerateNonClassMembers() {
        return !(this.myClass instanceof KtClassOrObject) || this.state.getGenerateDeclaredClassFilter().shouldGenerateClassMembers((KtClassOrObject) this.myClass);
    }

    private static boolean shouldProcessFirst(KtDeclaration ktDeclaration) {
        return ((ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtNamedFunction)) ? false : true;
    }

    protected void generateDeclaration(KtDeclaration ktDeclaration) {
        if ((ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtNamedFunction) || (ktDeclaration instanceof KtTypeAlias)) {
            if (shouldGenerateNonClassMembers()) {
                genSimpleMember(ktDeclaration);
            }
        } else if (ktDeclaration instanceof KtClassOrObject) {
            if (!(ktDeclaration instanceof KtEnumEntry) || CodegenBinding.enumEntryNeedSubclass(this.bindingContext, (KtEnumEntry) ktDeclaration)) {
                genClassOrObject((KtClassOrObject) ktDeclaration);
            }
        }
    }

    private void generatePrimaryConstructorProperties() {
        PropertyDescriptor propertyDescriptor;
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.firstOrNull(this.descriptor.getConstructors());
        if (classConstructorDescriptor == null) {
            return;
        }
        boolean z = this.descriptor.getClassKind() == ClassKind.ANNOTATION_CLASS;
        FunctionDescriptor functionDescriptorFindExpectedFunctionForActual = (z && classConstructorDescriptor.getIsActual()) ? CodegenUtil.findExpectedFunctionForActual(classConstructorDescriptor) : null;
        for (KtParameter ktParameter : getPrimaryConstructorParameters()) {
            if (ktParameter.hasValOrVar() && (propertyDescriptor = (PropertyDescriptor) this.bindingContext.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter)) != null) {
                if (z) {
                    this.propertyCodegen.generateConstructorPropertyAsMethodForAnnotationClass(ktParameter, propertyDescriptor, functionDescriptorFindExpectedFunctionForActual);
                } else {
                    this.propertyCodegen.generatePrimaryConstructorProperty(propertyDescriptor);
                }
            }
        }
    }

    public List<KtParameter> getPrimaryConstructorParameters() {
        KtPureClassOrObject ktPureClassOrObject = this.myClass;
        if (ktPureClassOrObject instanceof KtClass) {
            List<KtParameter> primaryConstructorParameters = ktPureClassOrObject.getPrimaryConstructorParameters();
            if (primaryConstructorParameters == null) {
                $$$reportNull$$$0(4);
            }
            return primaryConstructorParameters;
        }
        List<KtParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(5);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected ClassDescriptor classForInnerClassRecord() {
        return InnerClassConsumer.INSTANCE.classForInnerClassRecord(this.descriptor, false);
    }

    protected void generateDelegatesToDefaultImpl() {
        if (JvmCodegenUtil.isJvmInterface(this.descriptor)) {
            return;
        }
        boolean z = InlineClassesUtilsKt.isInlineClass(this.descriptor) && this.kind == OwnerKind.ERASED_INLINE_CLASS;
        JvmKotlinType jvmKotlinType = new JvmKotlinType(this.typeMapper.mapType(this.descriptor), this.descriptor.getDefaultType());
        for (Map.Entry<FunctionDescriptor, FunctionDescriptor> entry : CodegenUtil.getNonPrivateTraitMethods(this.descriptor).entrySet()) {
            generateDelegationToDefaultImpl(entry.getKey(), entry.getValue(), jvmKotlinType, this.functionCodegen, this.state, z);
        }
    }

    public static void generateDelegationToDefaultImpl(final FunctionDescriptor functionDescriptor, final FunctionDescriptor functionDescriptor2, final JvmKotlinType jvmKotlinType, FunctionCodegen functionCodegen, GenerationState generationState, final boolean z) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(7);
        }
        if (jvmKotlinType == null) {
            $$$reportNull$$$0(8);
        }
        if (functionCodegen == null) {
            $$$reportNull$$$0(9);
        }
        if (generationState == null) {
            $$$reportNull$$$0(10);
        }
        CallableMemberDescriptor callableMemberDescriptorFindImplementationFromInterface = functionDescriptor.getKind().isReal() ? functionDescriptor : DeclarationUtilKt.findImplementationFromInterface(functionDescriptor);
        if (CodegenUtilKt.isDefinitelyNotDefaultImplsMethod(callableMemberDescriptorFindImplementationFromInterface) || JvmAnnotationUtilKt.isCallableMemberCompiledToJvmDefault(callableMemberDescriptorFindImplementationFromInterface, generationState.getJvmDefaultMode())) {
            return;
        }
        final KotlinTypeMapper typeMapper = generationState.getTypeMapper();
        functionCodegen.generateMethod(new JvmDeclarationOrigin(JvmDeclarationOriginKind.CLASS_MEMBER_DELEGATION_TO_DEFAULT_IMPL, DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor), functionDescriptor, null), functionDescriptor2, new FunctionGenerationStrategy.CodegenBased(generationState) { // from class: org.jetbrains.kotlin.codegen.ClassBodyCodegen.1
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1 || i == 3) {
                    objArr[0] = "signature";
                } else if (i != 4) {
                    objArr[0] = "codegen";
                } else {
                    objArr[0] = "defaultImplsMethod";
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/ClassBodyCodegen$1";
                if (i == 2 || i == 3 || i == 4) {
                    objArr[2] = "putArgumentsOnStack";
                } else {
                    objArr[2] = "doGenerateBody";
                }
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
                DeclarationDescriptor containingModule = functionDescriptor.getContainingModule();
                if (DescriptorUtils.isInterface(containingModule)) {
                    PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor2.getContainingModule());
                    if (psiElementDescriptorToDeclaration instanceof KtDeclaration) {
                        expressionCodegen.markLineNumber(psiElementDescriptorToDeclaration, false);
                    }
                    ClassDescriptor classDescriptor = (ClassDescriptor) containingModule;
                    Type typeMapDefaultImpls = typeMapper.mapDefaultImpls(classDescriptor);
                    FunctionDescriptor original = functionDescriptor.getOriginal();
                    Method methodMapAsmMethod = typeMapper.mapAsmMethod(original, OwnerKind.DEFAULT_IMPLS);
                    putArgumentsOnStack(expressionCodegen, jvmMethodSignature, methodMapAsmMethod);
                    InstructionAdapter instructionAdapter = expressionCodegen.v;
                    if (KotlinBuiltIns.isCloneable(classDescriptor) && methodMapAsmMethod.getName().equals("clone")) {
                        instructionAdapter.invokespecial(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "clone", "()Ljava/lang/Object;", false);
                    } else {
                        instructionAdapter.invokestatic(typeMapDefaultImpls.getInternalName(), methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), false);
                    }
                    Type returnType = jvmMethodSignature.getReturnType();
                    StackValue.onStack(methodMapAsmMethod.getReturnType(), original.getReturnType()).put(returnType, instructionAdapter);
                    instructionAdapter.areturn(returnType);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v5, types: [int] */
            /* JADX WARN: Type inference failed for: r0v7 */
            /* JADX WARN: Type inference failed for: r0v8 */
            private void putArgumentsOnStack(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature, Method method) {
                if (expressionCodegen == null) {
                    $$$reportNull$$$0(2);
                }
                if (jvmMethodSignature == null) {
                    $$$reportNull$$$0(3);
                }
                if (method == null) {
                    $$$reportNull$$$0(4);
                }
                InstructionAdapter instructionAdapter = expressionCodegen.v;
                Type[] argumentTypes = jvmMethodSignature.getAsmMethod().getArgumentTypes();
                Type[] argumentTypes2 = method.getArgumentTypes();
                StackValue.local(0, jvmKotlinType.getType(), jvmKotlinType.getKotlinType()).put(AsmTypes.OBJECT_TYPE, ((ClassDescriptor) functionDescriptor2.getContainingModule()).getDefaultType(), instructionAdapter);
                boolean z2 = z;
                int size = jvmKotlinType.getType().getSize();
                List<ParameterDescriptor> parameters = getParameters(functionDescriptor2);
                List<ParameterDescriptor> parameters2 = getParameters(functionDescriptor);
                Iterator<ParameterDescriptor> it = parameters.iterator();
                Iterator<ParameterDescriptor> it2 = parameters2.iterator();
                int i = 1;
                for (?? r0 = z2; r0 < argumentTypes.length; r0++) {
                    Type type = argumentTypes[r0];
                    Type type2 = argumentTypes2[i];
                    KotlinType type3 = null;
                    KotlinType type4 = it.hasNext() ? it.next().getType() : null;
                    if (it2.hasNext()) {
                        type3 = it2.next().getType();
                    }
                    StackValue.local(size, type, type4).put(type2, type3, instructionAdapter);
                    size += type.getSize();
                    i++;
                }
            }

            private List<ParameterDescriptor> getParameters(FunctionDescriptor functionDescriptor3) {
                ArrayList arrayList = new ArrayList(functionDescriptor3.getValueParameters().size() + 1);
                ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor3.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    arrayList.add(extensionReceiverParameter);
                }
                arrayList.addAll(functionDescriptor3.getValueParameters());
                return arrayList;
            }
        });
    }
}
