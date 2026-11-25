package org.jetbrains.kotlin.codegen;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionInvokeDescriptor;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.FacadePartWithSourceFile;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.RootContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorPsiUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaPropertyDescriptor;
import org.jetbrains.kotlin.load.kotlin.ModuleVisibilityUtilsKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCodeFragment;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.checkers.PolymorphicSignatureCallChecker;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.resolve.source.PsiSourceElement;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: loaded from: classes3.dex */
public class JvmCodegenUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 15 || i == 21 || i == 18 || i == 19 || i == 34 || i == 35) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 15 || i == 21 || i == 18 || i == 19 || i == 34 || i == 35) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "jvmDefaultMode";
                break;
            case 2:
                objArr[0] = "closure";
                break;
            case 3:
            case 5:
            case 13:
            case 14:
            case 17:
            case 22:
            case 24:
            case 31:
            default:
                objArr[0] = "descriptor";
                break;
            case 4:
            case 7:
            case 12:
                objArr[0] = "context";
                break;
            case 6:
                objArr[0] = "declarationDescriptor";
                break;
            case 8:
                objArr[0] = "propertyDescriptor";
                break;
            case 9:
                objArr[0] = "companionObjectDescriptor";
                break;
            case 10:
                objArr[0] = "property";
                break;
            case 11:
                objArr[0] = "contextBeforeInline";
                break;
            case 15:
            case 18:
            case 19:
            case 21:
            case 34:
            case 35:
                objArr[0] = "org/jetbrains/kotlin/codegen/JvmCodegenUtil";
                break;
            case 16:
            case 23:
            case 30:
                objArr[0] = "bindingContext";
                break;
            case 20:
                objArr[0] = "moduleName";
                break;
            case 25:
                objArr[0] = "resolvedCall";
                break;
            case 26:
            case 27:
                objArr[0] = "companionObject";
                break;
            case 28:
                objArr[0] = "specifier";
                break;
            case 29:
            case 37:
                objArr[0] = "state";
                break;
            case 32:
                objArr[0] = "name";
                break;
            case 33:
                objArr[0] = "languageVersionSettings";
                break;
            case 36:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        if (i == 15) {
            objArr[1] = "getDirectMember";
        } else if (i == 21) {
            objArr[1] = "getMappingFileName";
        } else if (i == 18 || i == 19) {
            objArr[1] = "getModuleName";
        } else if (i == 34 || i == 35) {
            objArr[1] = "sanitizeNameIfNeeded";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/JvmCodegenUtil";
        }
        switch (i) {
            case 2:
                objArr[2] = "isConst";
                break;
            case 3:
            case 4:
                objArr[2] = "isCallInsideSameClassAsFieldRepresentingProperty";
                break;
            case 5:
                objArr[2] = "isWithinSameFile";
                break;
            case 6:
            case 7:
                objArr[2] = "isCallInsideSameModuleAsDeclared";
                break;
            case 8:
                objArr[2] = "isConstOrHasJvmFieldAnnotation";
                break;
            case 9:
                objArr[2] = "getCompanionObjectAccessorName";
                break;
            case 10:
            case 11:
                objArr[2] = "couldUseDirectAccessToProperty";
                break;
            case 12:
                objArr[2] = "isDebuggerContext";
                break;
            case 13:
                objArr[2] = "getDispatchReceiverParameterForConstructorCall";
                break;
            case 14:
                objArr[2] = "getDirectMember";
                break;
            case 15:
            case 18:
            case 19:
            case 21:
            case 34:
            case 35:
                break;
            case 16:
            case 17:
                objArr[2] = "isArgumentWhichWillBeInlined";
                break;
            case 20:
                objArr[2] = "getMappingFileName";
                break;
            case 22:
            case 23:
                objArr[2] = "getPropertyDelegateType";
                break;
            case 24:
                objArr[2] = "isDelegatedLocalVariable";
                break;
            case 25:
                objArr[2] = "getBoundCallableReferenceReceiver";
                break;
            case 26:
                objArr[2] = "isCompanionObjectInInterfaceNotIntrinsic";
                break;
            case 27:
                objArr[2] = "isNonIntrinsicPrivateCompanionObjectInInterface";
                break;
            case 28:
            case 29:
            case 30:
                objArr[2] = "getSuperClass";
                break;
            case 31:
                objArr[2] = "isPolymorphicSignature";
                break;
            case 32:
            case 33:
                objArr[2] = "sanitizeNameIfNeeded";
                break;
            case 36:
            case 37:
                objArr[2] = "typeHasSpecializedInlineClassEquality";
                break;
            default:
                objArr[2] = "isNonDefaultInterfaceMember";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 15 && i != 21 && i != 18 && i != 19 && i != 34 && i != 35) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private JvmCodegenUtil() {
    }

    public static boolean isNonDefaultInterfaceMember(CallableMemberDescriptor callableMemberDescriptor, JvmDefaultMode jvmDefaultMode) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (jvmDefaultMode == null) {
            $$$reportNull$$$0(1);
        }
        if (!isJvmInterface(callableMemberDescriptor.getContainingDeclaration())) {
            return false;
        }
        if (callableMemberDescriptor instanceof JavaCallableMemberDescriptor) {
            return callableMemberDescriptor.getModality() == Modality.ABSTRACT;
        }
        return !JvmAnnotationUtilKt.isCompiledToJvmDefault(callableMemberDescriptor, jvmDefaultMode);
    }

    public static boolean isJvmInterface(DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            return false;
        }
        ClassKind kind = ((ClassDescriptor) declarationDescriptor).getKind();
        return kind == ClassKind.INTERFACE || kind == ClassKind.ANNOTATION_CLASS;
    }

    public static boolean isJvmInterface(KotlinType kotlinType) {
        return isJvmInterface(kotlinType.getConstructor().getThis$0());
    }

    public static boolean isConst(CalculatedClosure calculatedClosure) {
        if (calculatedClosure == null) {
            $$$reportNull$$$0(2);
        }
        return calculatedClosure.getCapturedOuterClassDescriptor() == null && calculatedClosure.getCapturedReceiverFromOuterContext() == null && calculatedClosure.getCaptureVariables().isEmpty() && !calculatedClosure.isSuspend();
    }

    private static boolean isCallInsideSameClassAsFieldRepresentingProperty(PropertyDescriptor propertyDescriptor, CodegenContext codegenContext) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(4);
        }
        boolean z = propertyDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE;
        boolean z2 = propertyDescriptor.getKind() == CallableMemberDescriptor.Kind.DELEGATION;
        DeclarationDescriptor original = propertyDescriptor.getContainingDeclaration().getOriginal();
        if (DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(propertyDescriptor)) {
            original = original.getContainingDeclaration();
        }
        return (z || z2 || ((!codegenContext.hasThisDescriptor() || original != codegenContext.getThisDescriptor()) && (!(codegenContext.getParentContext() instanceof FacadePartWithSourceFile) || !isWithinSameFile(((FacadePartWithSourceFile) codegenContext.getParentContext()).getSourceFile(), propertyDescriptor))) || codegenContext.getContextKind() == OwnerKind.DEFAULT_IMPLS) ? false : true;
    }

    private static boolean isWithinSameFile(KtFile ktFile, CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (!(callableMemberDescriptor.getContainingDeclaration().getOriginal() instanceof PackageFragmentDescriptor)) {
            return false;
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor);
        return (ktFile == null || ktFile == SourceFile.NO_SOURCE_FILE || (psiElementDescriptorToDeclaration != null ? psiElementDescriptorToDeclaration.getContainingFile() : null) != ktFile) ? false : true;
    }

    public static boolean isCallInsideSameModuleAsDeclared(CallableMemberDescriptor callableMemberDescriptor, CodegenContext codegenContext, File file) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(7);
        }
        if (codegenContext instanceof RootContext) {
            return true;
        }
        DeclarationDescriptor contextDescriptor = codegenContext.getContextDescriptor();
        CallableMemberDescriptor directMember = getDirectMember(callableMemberDescriptor);
        if (directMember instanceof DeserializedCallableMemberDescriptor) {
            return ModuleVisibilityUtilsKt.isContainedByCompiledPartOfOurModule(directMember, file);
        }
        return DescriptorUtils.areInSameModule(directMember, contextDescriptor);
    }

    public static boolean isConstOrHasJvmFieldAnnotation(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        return propertyDescriptor.isConst() || JvmAnnotationUtilKt.hasJvmFieldAnnotation(propertyDescriptor);
    }

    public static String getCompanionObjectAccessorName(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        return "access$" + classDescriptor.getShortName();
    }

    public static boolean couldUseDirectAccessToProperty(PropertyDescriptor propertyDescriptor, boolean z, boolean z2, MethodContext methodContext, boolean z3) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(11);
        }
        if (z3 && propertyDescriptor.isConst()) {
            return true;
        }
        if (KotlinTypeMapper.isAccessor(propertyDescriptor)) {
            return false;
        }
        CodegenContext firstCrossInlineOrNonInlineContext = methodContext.getFirstCrossInlineOrNonInlineContext();
        if (firstCrossInlineOrNonInlineContext.isInlineMethodContext()) {
            return false;
        }
        if (!isCallInsideSameClassAsFieldRepresentingProperty(propertyDescriptor, firstCrossInlineOrNonInlineContext)) {
            DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
            if (((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getKind() == ClassKind.ANNOTATION_CLASS) || !isDebuggerContext(firstCrossInlineOrNonInlineContext)) {
                return false;
            }
            for (PropertyAccessorDescriptor propertyAccessorDescriptor : propertyDescriptor.getAccessors()) {
                if (!propertyAccessorDescriptor.isDefault() && z == (propertyAccessorDescriptor instanceof PropertyGetterDescriptor)) {
                    return false;
                }
            }
            if (!propertyDescriptor.getOverriddenDescriptors().isEmpty()) {
                return false;
            }
        }
        if (z2 || propertyDescriptor.getExtensionReceiverParameter() != null) {
            return false;
        }
        PropertyAccessorDescriptor getter = z ? propertyDescriptor.getGetter() : propertyDescriptor.getSetter();
        if (getter == null) {
            return true;
        }
        if (DescriptorPsiUtilsKt.hasBody(getter)) {
            return false;
        }
        return DescriptorVisibilities.isPrivate(getter.getVisibilityImpl()) || getter.getModality() == Modality.FINAL;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0026  */
    public static boolean isDebuggerContext(CodegenContext codegenContext) {
        PsiFile containingFile;
        PsiElement psi;
        if (codegenContext == null) {
            $$$reportNull$$$0(12);
        }
        DeclarationDescriptor contextDescriptor = codegenContext.getContextDescriptor();
        if (contextDescriptor instanceof DeclarationDescriptorWithSource) {
            SourceElement source = ((DeclarationDescriptorWithSource) contextDescriptor).getSourceElement();
            if (!(source instanceof PsiSourceElement) || (psi = ((PsiSourceElement) source).getPsi()) == null) {
                containingFile = null;
            } else {
                containingFile = psi.getContainingFile();
            }
        } else {
            containingFile = null;
        }
        return containingFile instanceof KtCodeFragment;
    }

    public static ClassDescriptor getDispatchReceiverParameterForConstructorCall(ConstructorDescriptor constructorDescriptor, CalculatedClosure calculatedClosure) {
        if (constructorDescriptor == null) {
            $$$reportNull$$$0(13);
        }
        if (calculatedClosure != null) {
            return calculatedClosure.getCapturedOuterClassDescriptor();
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = constructorDescriptor.mo6753getDispatchReceiverParameter();
        if (dispatchReceiverParameter == null) {
            return null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) dispatchReceiverParameter.getContainingDeclaration();
        if (classDescriptor.getKind().isSingleton()) {
            return null;
        }
        return classDescriptor;
    }

    public static CallableMemberDescriptor getDirectMember(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        CallableMemberDescriptor directMember = DescriptorUtils.getDirectMember(callableMemberDescriptor);
        if (directMember == null) {
            $$$reportNull$$$0(15);
        }
        return directMember;
    }

    public static boolean isArgumentWhichWillBeInlined(BindingContext bindingContext, DeclarationDescriptor declarationDescriptor) {
        if (bindingContext == null) {
            $$$reportNull$$$0(16);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(declarationDescriptor);
        return InlineUtil.canBeInlineArgument(psiElementDescriptorToDeclaration) && InlineUtil.isInlinedArgument((KtFunction) psiElementDescriptorToDeclaration, bindingContext, false);
    }

    public static String getModuleName(ModuleDescriptor moduleDescriptor) {
        Name stableName = moduleDescriptor.getStableName();
        if (stableName == null) {
            String strRemoveSurrounding = StringsKt.removeSurrounding(moduleDescriptor.getShortName().asString(), (CharSequence) "<", (CharSequence) ">");
            if (strRemoveSurrounding == null) {
                $$$reportNull$$$0(18);
            }
            return strRemoveSurrounding;
        }
        String strRemoveSurrounding2 = StringsKt.removeSurrounding(stableName.asString(), (CharSequence) "<", (CharSequence) ">");
        if (strRemoveSurrounding2 == null) {
            $$$reportNull$$$0(19);
        }
        return strRemoveSurrounding2;
    }

    public static String getMappingFileName(String str) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        String str2 = "META-INF/" + str + ".kotlin_module";
        if (str2 == null) {
            $$$reportNull$$$0(21);
        }
        return str2;
    }

    public static boolean isInlinedJavaConstProperty(VariableDescriptor variableDescriptor) {
        return (variableDescriptor instanceof JavaPropertyDescriptor) && variableDescriptor.isConst();
    }

    public static KotlinType getPropertyDelegateType(VariableDescriptorWithAccessors variableDescriptorWithAccessors, BindingContext bindingContext) {
        ResolvedCall resolvedCall;
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(22);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(23);
        }
        VariableAccessorDescriptor getter = variableDescriptorWithAccessors.getGetter();
        if (getter != null && (resolvedCall = (ResolvedCall) bindingContext.get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, getter)) != null) {
            ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
            if (receiverValueMo7305getExtensionReceiver != null) {
                return receiverValueMo7305getExtensionReceiver.getType();
            }
            ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
            if (receiverValueMo7304getDispatchReceiver != null) {
                return receiverValueMo7304getDispatchReceiver.getType();
            }
        }
        return null;
    }

    public static boolean isDelegatedLocalVariable(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        return (declarationDescriptor instanceof LocalVariableDescriptor) && ((LocalVariableDescriptor) declarationDescriptor).getIsDelegated();
    }

    public static ReceiverValue getBoundCallableReferenceReceiver(ResolvedCall<?> resolvedCall) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(25);
        }
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor.getExtensionReceiverParameter() == null && resultingDescriptor.mo6753getDispatchReceiverParameter() == null) {
            return null;
        }
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.getExtensionReceiver();
        if (receiverValueMo7304getDispatchReceiver == null) {
            receiverValueMo7304getDispatchReceiver = receiverValueMo7305getExtensionReceiver;
        }
        if (receiverValueMo7304getDispatchReceiver instanceof TransientReceiver) {
            return null;
        }
        return receiverValueMo7304getDispatchReceiver;
    }

    public static boolean isCompanionObjectInInterfaceNotIntrinsic(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        return DescriptorUtils.isCompanionObject(declarationDescriptor) && isJvmInterface(declarationDescriptor.getContainingDeclaration()) && !DescriptorsJvmAbiUtil.isMappedIntrinsicCompanionObject((ClassDescriptor) declarationDescriptor);
    }

    public static boolean isNonIntrinsicPrivateCompanionObjectInInterface(DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        if (declarationDescriptorWithVisibility == null) {
            $$$reportNull$$$0(27);
        }
        return isCompanionObjectInInterfaceNotIntrinsic(declarationDescriptorWithVisibility) && DescriptorVisibilities.isPrivate(declarationDescriptorWithVisibility.getVisibilityImpl());
    }

    public static boolean isDeclarationOfBigArityFunctionInvoke(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof FunctionInvokeDescriptor) && ((FunctionInvokeDescriptor) declarationDescriptor).hasBigArity();
    }

    public static boolean isDeclarationOfBigArityCreateCoroutineMethod(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof SimpleFunctionDescriptor) && declarationDescriptor.getShortName().asString().equals(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME) && ((SimpleFunctionDescriptor) declarationDescriptor).getValueParameters().size() >= 22 && (declarationDescriptor.getContainingDeclaration() instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) declarationDescriptor.getContainingDeclaration()).isSuspend();
    }

    public static boolean isOverrideOfBigArityFunctionInvoke(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof FunctionDescriptor) && declarationDescriptor.getShortName().equals(OperatorNameConventions.INVOKE) && CollectionsKt.any(DescriptorUtils.getAllOverriddenDeclarations((FunctionDescriptor) declarationDescriptor), new Function1() { // from class: org.jetbrains.kotlin.codegen.JvmCodegenUtil$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke((FunctionDescriptor) obj));
            }
        });
    }

    public static ClassDescriptor getSuperClass(KtSuperTypeListEntry ktSuperTypeListEntry, GenerationState generationState, BindingContext bindingContext) {
        if (ktSuperTypeListEntry == null) {
            $$$reportNull$$$0(28);
        }
        if (generationState == null) {
            $$$reportNull$$$0(29);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(30);
        }
        return CodegenUtil.getSuperClassBySuperTypeListEntry(ktSuperTypeListEntry, bindingContext);
    }

    public static boolean isPolymorphicSignature(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        return functionDescriptor.getAnnotations().hasAnnotation(PolymorphicSignatureCallChecker.polymorphicSignatureFqName);
    }

    public static String sanitizeNameIfNeeded(String str, LanguageVersionSettings languageVersionSettings) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(33);
        }
        if (!((Boolean) languageVersionSettings.getFlag(JvmAnalysisFlags.getSanitizeParentheses())).booleanValue()) {
            if (str == null) {
                $$$reportNull$$$0(35);
            }
            return str;
        }
        String strReplace = str.replace("(", "$_").replace(Constant.AFTER_QUTO, "$_");
        if (strReplace == null) {
            $$$reportNull$$$0(34);
        }
        return strReplace;
    }

    public static boolean typeHasSpecializedInlineClassEquality(KotlinType kotlinType, GenerationState generationState) {
        if (kotlinType == null) {
            $$$reportNull$$$0(36);
        }
        if (generationState == null) {
            $$$reportNull$$$0(37);
        }
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        if (!(classifierDescriptorMo7434getDeclarationDescriptor instanceof DeserializedClassDescriptor)) {
            return true;
        }
        DeserializedClassDescriptor deserializedClassDescriptor = (DeserializedClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
        if (DescriptorUtils.getFqNameSafe(deserializedClassDescriptor).equals(StandardNames.RESULT_FQ_NAME)) {
            return generationState.getLanguageVersionSettings().getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) >= 0;
        }
        return deserializedClassDescriptor.getMetadataVersion().isAtLeast(1, 1, 16);
    }

    public static boolean isInSamePackage(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
        PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(declarationDescriptor, PackageFragmentDescriptor.class, false);
        PackageFragmentDescriptor packageFragmentDescriptor2 = (PackageFragmentDescriptor) DescriptorUtils.getParentOfType(declarationDescriptor2, PackageFragmentDescriptor.class, false);
        return (packageFragmentDescriptor == null || packageFragmentDescriptor2 == null || !packageFragmentDescriptor.getFqName().equals(packageFragmentDescriptor2.getFqName())) ? false : true;
    }

    public static String dumpContextHierarchy(CodegenContext codegenContext) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (codegenContext != null) {
            sb.append(i).append(": ").append(codegenContext).append('\n');
            codegenContext = codegenContext.getParentContext();
            i++;
        }
        return sb.toString();
    }
}
