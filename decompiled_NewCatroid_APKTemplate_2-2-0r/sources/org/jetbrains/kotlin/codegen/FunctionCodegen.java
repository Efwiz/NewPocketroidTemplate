package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.beust.jcommander.Parameters;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.backend.common.bridges.Bridge;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.CodegenContextUtil;
import org.jetbrains.kotlin.codegen.context.ConstructorContext;
import org.jetbrains.kotlin.codegen.context.DefaultImplsClassContext;
import org.jetbrains.kotlin.codegen.context.InlineLambdaContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassFacadeContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.SuspendFunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.TypeMapperUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModalityUtilsKt;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.annotations.ThrowUtilKt;
import org.jetbrains.kotlin.resolve.calls.util.UnderscoreUtilKt;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.util.TraceMethodVisitor;

/* JADX INFO: loaded from: classes3.dex */
public class FunctionCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BindingContext bindingContext;
    private final MemberCodegen<?> memberCodegen;
    private final CodegenContext owner;
    public final GenerationState state;
    private final KotlinTypeMapper typeMapper;
    private final ClassBuilder v;

    /* JADX WARN: Code duplicated, block: B:77:0x0161  */
    /* JADX WARN: Code duplicated, block: B:8:0x0012  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 23 && i != 110 && i != 134) {
            switch (i) {
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 23 && i != 110 && i != 134) {
            switch (i) {
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 2:
            case 55:
            case 77:
            case 83:
            case 131:
                objArr[0] = "state";
                break;
            case 3:
            case 54:
                objArr[0] = "memberCodegen";
                break;
            case 4:
            case 108:
            case 111:
                objArr[0] = "function";
                break;
            case 5:
            case 11:
            case 14:
            case 15:
            case 25:
            case 29:
            case 33:
            case 38:
            case 45:
            case 50:
            case 56:
            case 60:
            case 68:
            case 73:
            case 80:
            case 119:
            case 123:
            case 147:
            case 148:
                objArr[0] = "functionDescriptor";
                break;
            case 6:
                objArr[0] = "delegateFunctionDescriptor";
                break;
            case 7:
            case 10:
            case 20:
            case 24:
            case 28:
            case 32:
            case 37:
            case 44:
                objArr[0] = "origin";
                break;
            case 8:
            case 105:
            case 106:
            case 107:
            case 129:
            case 138:
            case 139:
            case 144:
                objArr[0] = "descriptor";
                break;
            case 9:
            case 13:
            case 40:
            case 47:
            case 63:
                objArr[0] = "strategy";
                break;
            case 12:
            case 39:
            case 46:
            case 122:
                objArr[0] = "methodContext";
                break;
            case 16:
            case 43:
            case 85:
            case 88:
            case 97:
            case 100:
                objArr[0] = "asmMethod";
                break;
            case 17:
            case 42:
            case 49:
            case 52:
                objArr[0] = "jvmSignature";
                break;
            case 18:
            case 35:
            case 41:
            case 48:
            case 51:
            case 59:
            case 66:
            case 71:
            case 78:
            case 84:
            case 87:
            case 91:
            case 96:
            case 99:
            case 104:
            case 124:
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                break;
            case 19:
                objArr[0] = "additionalVisibleAnnotations";
                break;
            case 21:
                objArr[0] = "name";
                break;
            case 22:
                objArr[0] = "desc";
                break;
            case 23:
            case 110:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 134:
                objArr[0] = "org/jetbrains/kotlin/codegen/FunctionCodegen";
                break;
            case 26:
            case 30:
                objArr[0] = "contextKind";
                break;
            case 27:
            case 31:
                objArr[0] = "containingDeclaration";
                break;
            case 34:
                objArr[0] = "inlineClass";
                break;
            case 36:
            case 58:
            case 109:
            case 146:
                objArr[0] = "typeMapper";
                break;
            case 53:
            case 133:
                objArr[0] = "valueParameters";
                break;
            case 57:
            case 61:
            case 69:
            case 86:
                objArr[0] = "context";
                break;
            case 62:
            case 132:
            case 136:
                objArr[0] = "signature";
                break;
            case 64:
            case 126:
                objArr[0] = "parentCodegen";
                break;
            case 65:
            case 70:
                objArr[0] = "jvmDefaultMode";
                break;
            case 67:
                objArr[0] = "frameMap";
                break;
            case 72:
            case 79:
                objArr[0] = "jvmMethodSignature";
                break;
            case 74:
            case 81:
                objArr[0] = "methodBegin";
                break;
            case 75:
            case 82:
                objArr[0] = "methodEnd";
                break;
            case 76:
                objArr[0] = "ownerKind";
                break;
            case 89:
            case 98:
            case 101:
                objArr[0] = "classToDelegateTo";
                break;
            case 90:
            case 102:
            case 145:
                objArr[0] = "returnType";
                break;
            case 92:
                objArr[0] = "erasedStaticAsmMethod";
                break;
            case 93:
                objArr[0] = "fieldOwnerType";
                break;
            case 94:
                objArr[0] = "fieldName";
                break;
            case 95:
                objArr[0] = "fieldType";
                break;
            case 103:
                objArr[0] = "method";
                break;
            case 112:
                objArr[0] = "languageVersionSettings";
                break;
            case 118:
            default:
                objArr[0] = "owner";
                break;
            case 120:
            case 153:
                objArr[0] = Namer.METADATA_CLASS_KIND;
                break;
            case 121:
            case 125:
                objArr[0] = "loadStrategy";
                break;
            case 127:
            case 130:
                objArr[0] = "defaultMethod";
                break;
            case 128:
            case 143:
                objArr[0] = "iv";
                break;
            case 135:
                objArr[0] = "ownerType";
                break;
            case 137:
                objArr[0] = "callGenerator";
                break;
            case 140:
                objArr[0] = "bridge";
                break;
            case 141:
                objArr[0] = "delegateTo";
                break;
            case 142:
                objArr[0] = "bridgeReturnType";
                break;
            case 149:
            case 150:
                objArr[0] = "delegateFunction";
                break;
            case 151:
                objArr[0] = "declarationOrigin";
                break;
            case 152:
                objArr[0] = "memberDescriptor";
                break;
        }
        if (i == 23) {
            objArr[1] = "newMethod";
        } else if (i == 110) {
            objArr[1] = "getThrownExceptions";
        } else if (i != 134) {
            switch (i) {
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                    objArr[1] = "getThrownExceptions";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/codegen/FunctionCodegen";
                    break;
            }
        } else {
            objArr[1] = "createFrameMap";
        }
        switch (i) {
            case 4:
                objArr[2] = "gen";
                break;
            case 5:
            case 6:
                objArr[2] = "generateOverloadsWithDefaultValues";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                objArr[2] = "generateMethod";
                break;
            case 14:
                objArr[2] = "recordMethodForFunctionIfAppropriate";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "generateMethodAnnotationsIfRequired";
                break;
            case 20:
            case 21:
            case 22:
                objArr[2] = "newMethod";
                break;
            case 23:
            case 110:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 134:
                break;
            case 24:
            case 25:
            case 26:
            case 27:
                objArr[2] = "shouldGenerateMethodInsideInlineClass";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "canDelegateMethodBodyToInlineClass";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "generateMethodInsideInlineClassWrapper";
                break;
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[2] = "generateOpenMethodInSuspendClass";
                break;
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
                objArr[2] = "generateMethodBody";
                break;
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                objArr[2] = "generateParameterAnnotations";
                break;
            case 56:
            case 57:
            case 58:
                objArr[2] = "getThisTypeForFunction";
                break;
            case 66:
            case 67:
                objArr[2] = "newFakeTempIndex";
                break;
            case 68:
            case 69:
            case 70:
                objArr[2] = "isCompatibilityStubInDefaultImpls";
                break;
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
                objArr[2] = "generateLocalVariableTable";
                break;
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
                objArr[2] = "generateLocalVariablesForParameters";
                break;
            case 84:
            case 85:
            case 86:
                objArr[2] = "generateFacadeDelegateMethodBody";
                break;
            case 87:
            case 88:
            case 89:
            case 90:
                objArr[2] = "generateDelegateToMethodBody";
                break;
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
                objArr[2] = "generateDelegateToStaticErasedVersion";
                break;
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
                objArr[2] = "generateDelegateToStaticMethodBody";
                break;
            case 103:
                objArr[2] = "endVisit";
                break;
            case 104:
                objArr[2] = "renderByteCodeIfAvailable";
                break;
            case 105:
                objArr[2] = "hasSpecialBridgeMethod";
                break;
            case 106:
                objArr[2] = "generateBridges";
                break;
            case 107:
                objArr[2] = "isThereOverriddenInKotlinClass";
                break;
            case 108:
            case 109:
            case 111:
            case 112:
                objArr[2] = "getThrownExceptions";
                break;
            case 118:
            case 119:
            case 120:
            case 121:
                objArr[2] = "generateDefaultIfNeeded";
                break;
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
                objArr[2] = "generateDefaultImplBody";
                break;
            case 128:
            case 129:
            case 130:
                objArr[2] = "genDefaultSuperCallCheckIfNeeded";
                break;
            case 131:
            case 132:
            case 133:
                objArr[2] = "createFrameMap";
                break;
            case 135:
            case 136:
            case 137:
                objArr[2] = "loadExplicitArgumentsOnStack";
                break;
            case 138:
                objArr[2] = "isDefaultNeeded";
                break;
            case 139:
            case 140:
            case 141:
            case 142:
                objArr[2] = "generateBridge";
                break;
            case 143:
            case 144:
            case 145:
            case 146:
                objArr[2] = "generateTypeCheckBarrierIfNeeded";
                break;
            case 147:
                objArr[2] = "genSamDelegate";
                break;
            case 148:
            case 149:
            case 150:
            case 151:
                objArr[2] = "genDelegate";
                break;
            case 152:
            case 153:
                objArr[2] = "processInterfaceMethod";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 23 && i != 110 && i != 134) {
            switch (i) {
                case 113:
                case 114:
                case 115:
                case 116:
                case 117:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public FunctionCodegen(CodegenContext codegenContext, ClassBuilder classBuilder, GenerationState generationState, MemberCodegen<?> memberCodegen) {
        if (codegenContext == null) {
            $$$reportNull$$$0(0);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(1);
        }
        if (generationState == null) {
            $$$reportNull$$$0(2);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(3);
        }
        this.owner = codegenContext;
        this.v = classBuilder;
        this.state = generationState;
        this.typeMapper = generationState.getTypeMapper();
        this.bindingContext = generationState.getBindingContext();
        this.memberCodegen = memberCodegen;
    }

    public void gen(KtNamedFunction ktNamedFunction) {
        FunctionGenerationStrategy functionDefault;
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(4);
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) this.bindingContext.get(BindingContext.FUNCTION, ktNamedFunction);
        if (this.bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, simpleFunctionDescriptor) != null) {
            simpleFunctionDescriptor = (SimpleFunctionDescriptor) this.bindingContext.get(CodegenBinding.SUSPEND_FUNCTION_TO_JVM_VIEW, simpleFunctionDescriptor);
        }
        if (simpleFunctionDescriptor == null) {
            throw ExceptionLogger.logDescriptorNotFound("No descriptor for function " + ktNamedFunction.getName(), ktNamedFunction);
        }
        if (this.owner.getContextKind() != OwnerKind.DEFAULT_IMPLS || ktNamedFunction.hasBody()) {
            if (simpleFunctionDescriptor.isSuspend()) {
                functionDefault = new SuspendFunctionGenerationStrategy(this.state, (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(simpleFunctionDescriptor), ktNamedFunction, this.v.getThisName(), this);
            } else {
                functionDefault = new FunctionGenerationStrategy.FunctionDefault(this.state, ktNamedFunction);
            }
            generateMethod(JvmDeclarationOriginKt.OtherOrigin(ktNamedFunction, simpleFunctionDescriptor), simpleFunctionDescriptor, functionDefault);
        }
        generateDefaultIfNeeded(this.owner.intoFunction(simpleFunctionDescriptor, true), simpleFunctionDescriptor, this.owner.getContextKind(), DefaultParameterValueLoader.DEFAULT, ktNamedFunction);
        generateOverloadsWithDefaultValues(ktNamedFunction, simpleFunctionDescriptor, simpleFunctionDescriptor);
    }

    public void generateOverloadsWithDefaultValues(KtNamedFunction ktNamedFunction, FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(5);
        }
        if (functionDescriptor2 == null) {
            $$$reportNull$$$0(6);
        }
        new DefaultParameterValueSubstitutor(this.state).generateOverloadsIfNeeded(ktNamedFunction, functionDescriptor, functionDescriptor2, this.owner.getContextKind(), this.v, this.memberCodegen);
    }

    public void generateMethod(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, FunctionGenerationStrategy functionGenerationStrategy) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(7);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(9);
        }
        if (CoroutineCodegenUtilKt.isSuspendFunctionNotSuspensionView(functionDescriptor)) {
            generateMethod(jvmDeclarationOrigin, CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(functionDescriptor, this.state), functionGenerationStrategy);
        } else {
            generateMethod(jvmDeclarationOrigin, functionDescriptor, this.owner.intoFunction(functionDescriptor), functionGenerationStrategy);
        }
    }

    public void generateMethod(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, MethodContext methodContext, FunctionGenerationStrategy functionGenerationStrategy) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(10);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(12);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(13);
        }
        OwnerKind contextKind = TypeMapperUtilsKt.isInlineClassConstructorAccessor(functionDescriptor) ? OwnerKind.ERASED_INLINE_CLASS : methodContext.getContextKind();
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        if ((!DescriptorUtils.isInterface(containingDeclaration) || processInterfaceMethod(functionDescriptor, contextKind, false, false, this.state.getJvmDefaultMode())) && shouldGenerateMethodInsideInlineClass(jvmDeclarationOrigin, functionDescriptor, contextKind, containingDeclaration)) {
            JvmMethodGenericSignature jvmMethodGenericSignatureMapMethodSignature = functionGenerationStrategy.mapMethodSignature(functionDescriptor, this.typeMapper, contextKind, hasSpecialBridgeMethod(functionDescriptor));
            Method asmMethod = jvmMethodGenericSignatureMapMethodSignature.getAsmMethod();
            int methodAsmFlags = DescriptorAsmUtil.getMethodAsmFlags(functionDescriptor, contextKind, this.state);
            if (jvmDeclarationOrigin.getOriginKind() == JvmDeclarationOriginKind.SAM_DELEGATION) {
                methodAsmFlags |= 4096;
            }
            boolean zIsCompatibilityStubInDefaultImpls = isCompatibilityStubInDefaultImpls(functionDescriptor, methodContext, this.state.getJvmDefaultMode());
            if (zIsCompatibilityStubInDefaultImpls) {
                methodAsmFlags |= 131072;
            }
            int i = methodAsmFlags;
            if (functionDescriptor.isExternal() && (this.owner instanceof MultifileClassFacadeContext)) {
                return;
            }
            MethodVisitor methodVisitorWrapMethodVisitor = functionGenerationStrategy.wrapMethodVisitor(newMethod(jvmDeclarationOrigin, i, asmMethod.getName(), asmMethod.getDescriptor(), functionGenerationStrategy.skipGenericSignature() ? null : jvmMethodGenericSignatureMapMethodSignature.getGenericsSignature(), getThrownExceptions(functionDescriptor, this.typeMapper)), i, asmMethod.getName(), asmMethod.getDescriptor());
            recordMethodForFunctionIfAppropriate(functionDescriptor, asmMethod);
            generateMethodAnnotationsIfRequired(functionDescriptor, asmMethod, jvmMethodGenericSignatureMapMethodSignature, methodVisitorWrapMethodVisitor, zIsCompatibilityStubInDefaultImpls ? Collections.singletonList(CodegenUtilKt.JAVA_LANG_DEPRECATED) : Collections.emptyList(), ((i & 2) == 0 && (i & 4096) == 0 && !InlineClassDescriptorResolver.isSpecializedEqualsMethod(functionDescriptor)) ? false : true);
            GenerateJava8ParameterNamesKt.generateParameterNames(functionDescriptor, methodVisitorWrapMethodVisitor, jvmMethodGenericSignatureMapMethodSignature, this.state, (i & 4096) != 0);
            if (contextKind != OwnerKind.ERASED_INLINE_CLASS) {
                generateBridges(functionDescriptor);
            }
            boolean zIsJvmStaticInCompanionObject = CodegenUtilKt.isJvmStaticInCompanionObject(functionDescriptor);
            if (zIsJvmStaticInCompanionObject) {
                ImplementationBodyCodegen implementationBodyCodegen = (ImplementationBodyCodegen) this.memberCodegen.getParentCodegen();
                implementationBodyCodegen.addAdditionalTask(new JvmStaticInCompanionObjectGenerator(functionDescriptor, jvmDeclarationOrigin, this.state, implementationBodyCodegen));
            }
            CodegenUtilKt.generateBridgeForMainFunctionIfNecessary(this.state, this.v, functionDescriptor, jvmMethodGenericSignatureMapMethodSignature, jvmDeclarationOrigin, methodContext.getParentContext());
            if (functionDescriptor.isSuspend() && functionDescriptor.getModality() != Modality.ABSTRACT && ModalityUtilsKt.isOverridable(functionDescriptor) && !DescriptorUtils.isInterface(containingDeclaration) && !(containingDeclaration instanceof PackageFragmentDescriptor) && jvmDeclarationOrigin.getOriginKind() != JvmDeclarationOriginKind.CLASS_MEMBER_DELEGATION_TO_DEFAULT_IMPL) {
                generateOpenMethodInSuspendClass(jvmDeclarationOrigin, functionDescriptor, methodContext, functionGenerationStrategy, methodVisitorWrapMethodVisitor, jvmMethodGenericSignatureMapMethodSignature, asmMethod, i, zIsJvmStaticInCompanionObject);
            } else if (canDelegateMethodBodyToInlineClass(jvmDeclarationOrigin, functionDescriptor, contextKind, containingDeclaration)) {
                generateMethodInsideInlineClassWrapper(jvmDeclarationOrigin, functionDescriptor, (ClassDescriptor) containingDeclaration, methodVisitorWrapMethodVisitor, this.typeMapper);
            } else {
                generateMethodBody(jvmDeclarationOrigin, functionDescriptor, methodContext, functionGenerationStrategy, methodVisitorWrapMethodVisitor, jvmMethodGenericSignatureMapMethodSignature, zIsJvmStaticInCompanionObject);
            }
        }
    }

    private void recordMethodForFunctionIfAppropriate(FunctionDescriptor functionDescriptor, Method method) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (functionDescriptor instanceof AccessorForConstructorDescriptor) {
            ClassConstructorDescriptor calleeDescriptor = ((AccessorForConstructorDescriptor) functionDescriptor).getCalleeDescriptor();
            if (InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters(calleeDescriptor)) {
                functionDescriptor = calleeDescriptor;
            }
        } else if (InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters(functionDescriptor)) {
            return;
        }
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) CodegenUtilKt.unwrapFrontendVersion(functionDescriptor);
        if (CodegenContextUtil.isImplementationOwner(this.owner, functionDescriptor2)) {
            this.v.getSerializationBindings().put(JvmSerializationBindings.METHOD_FOR_FUNCTION, functionDescriptor2, method);
        }
    }

    private void generateMethodAnnotationsIfRequired(FunctionDescriptor functionDescriptor, Method method, JvmMethodGenericSignature jvmMethodGenericSignature, MethodVisitor methodVisitor, List<String> list, boolean z) {
        FunctionDescriptor calleeDescriptor;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (method == null) {
            $$$reportNull$$$0(16);
        }
        if (jvmMethodGenericSignature == null) {
            $$$reportNull$$$0(17);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(18);
        }
        if (list == null) {
            $$$reportNull$$$0(19);
        }
        if (!InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters(functionDescriptor)) {
            calleeDescriptor = functionDescriptor;
        } else if (!(functionDescriptor instanceof AccessorForConstructorDescriptor)) {
            return;
        } else {
            calleeDescriptor = ((AccessorForConstructorDescriptor) functionDescriptor).getCalleeDescriptor();
        }
        AnnotationCodegen.forMethod(methodVisitor, this.memberCodegen, this.state, z).genAnnotations(calleeDescriptor, method.getReturnType(), functionDescriptor.getReturnType(), null, list);
        generateParameterAnnotations(calleeDescriptor, methodVisitor, jvmMethodGenericSignature, calleeDescriptor.getValueParameters(), this.memberCodegen, this.state, z);
    }

    public MethodVisitor newMethod(JvmDeclarationOrigin jvmDeclarationOrigin, int i, String str, String str2, String str3, String[] strArr) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(20);
        }
        if (str == null) {
            $$$reportNull$$$0(21);
        }
        if (str2 == null) {
            $$$reportNull$$$0(22);
        }
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(jvmDeclarationOrigin, i, str, str2, str3, strArr);
        if (methodVisitorNewMethod == null) {
            $$$reportNull$$$0(23);
        }
        return methodVisitorNewMethod;
    }

    private static boolean shouldGenerateMethodInsideInlineClass(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, OwnerKind ownerKind, DeclarationDescriptor declarationDescriptor) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(24);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(26);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return (canDelegateMethodBodyToInlineClass(jvmDeclarationOrigin, functionDescriptor, ownerKind, declarationDescriptor) && functionDescriptor.getOverriddenDescriptors().isEmpty() && !CodegenUtilKt.isJvmStaticInInlineClass(functionDescriptor)) ? false : true;
    }

    private static boolean canDelegateMethodBodyToInlineClass(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, OwnerKind ownerKind, DeclarationDescriptor declarationDescriptor) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(28);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(30);
        }
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        if (ownerKind == OwnerKind.ERASED_INLINE_CLASS || jvmDeclarationOrigin.getOriginKind() == JvmDeclarationOriginKind.UNBOX_METHOD_OF_INLINE_CLASS || CodegenUtilKt.isJvmStaticInInlineClass(functionDescriptor)) {
            return false;
        }
        if ((functionDescriptor instanceof PropertyAccessorDescriptor) && InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(((PropertyAccessorDescriptor) functionDescriptor).getCorrespondingProperty())) {
            return false;
        }
        return InlineClassesUtilsKt.isInlineClass(declarationDescriptor) && (!(functionDescriptor instanceof ConstructorDescriptor) && !KotlinTypeMapper.isAccessor(functionDescriptor));
    }

    public static void generateMethodInsideInlineClassWrapper(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, ClassDescriptor classDescriptor, MethodVisitor methodVisitor, KotlinTypeMapper kotlinTypeMapper) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(32);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(33);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(34);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(35);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(36);
        }
        methodVisitor.visitCode();
        Type typeMapClass = kotlinTypeMapper.mapClass(classDescriptor);
        Method methodMapAsmMethod = kotlinTypeMapper.mapAsmMethod(functionDescriptor.getOriginal(), OwnerKind.ERASED_INLINE_CLASS);
        InlineClassRepresentation<SimpleType> inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(classDescriptor);
        generateDelegateToStaticErasedVersion(methodVisitor, methodMapAsmMethod, typeMapClass, inlineClassRepresentation.getUnderlyingPropertyName().asString(), kotlinTypeMapper.mapType((KotlinType) inlineClassRepresentation.getUnderlyingType()));
        endVisit(methodVisitor, (String) null, jvmDeclarationOrigin.getElement());
    }

    private void generateOpenMethodInSuspendClass(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, MethodContext methodContext, FunctionGenerationStrategy functionGenerationStrategy, MethodVisitor methodVisitor, JvmMethodSignature jvmMethodSignature, Method method, int i, boolean z) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(37);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(38);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(39);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(40);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(41);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(42);
        }
        if (method == null) {
            $$$reportNull$$$0(43);
        }
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        int size = 1;
        for (Type type : method.getArgumentTypes()) {
            methodVisitor.visitVarInsn(type.getOpcode(21), size);
            size += type.getSize();
        }
        Method implForOpenMethod = CoroutineCodegenUtilKt.getImplForOpenMethod(method, this.v.getThisName());
        JvmMethodGenericSignature jvmMethodGenericSignature = new JvmMethodGenericSignature(implForOpenMethod, jvmMethodSignature.getValueParameters(), null);
        methodVisitor.visitMethodInsn(184, this.v.getThisName(), implForOpenMethod.getName(), implForOpenMethod.getDescriptor(), false);
        methodVisitor.visitInsn(176);
        methodVisitor.visitEnd();
        int i2 = (i | 4104) & (~DescriptorAsmUtil.getVisibilityAccessFlag(functionDescriptor));
        generateMethodBody(jvmDeclarationOrigin, functionDescriptor, methodContext, functionGenerationStrategy, functionGenerationStrategy.wrapMethodVisitor(this.v.newMethod(jvmDeclarationOrigin, i2, implForOpenMethod.getName(), implForOpenMethod.getDescriptor(), null, getThrownExceptions(functionDescriptor, this.typeMapper)), i2, implForOpenMethod.getName(), implForOpenMethod.getDescriptor()), jvmMethodGenericSignature, z);
    }

    private void generateMethodBody(JvmDeclarationOrigin jvmDeclarationOrigin, FunctionDescriptor functionDescriptor, MethodContext methodContext, FunctionGenerationStrategy functionGenerationStrategy, MethodVisitor methodVisitor, JvmMethodSignature jvmMethodSignature, boolean z) {
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(44);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(45);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(46);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(47);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(48);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(49);
        }
        OwnerKind contextKind = methodContext.getContextKind();
        if (!this.state.getClassBuilderMode().generateBodies || DescriptorAsmUtil.isAbstractMethod(functionDescriptor, contextKind, this.state.getJvmDefaultMode())) {
            generateLocalVariableTable(methodVisitor, jvmMethodSignature, functionDescriptor, getThisTypeForFunction(functionDescriptor, methodContext, this.typeMapper), new Label(), new Label(), contextKind, this.state, 0);
            methodVisitor.visitEnd();
            return;
        }
        if (!functionDescriptor.isExternal()) {
            generateMethodBody(methodVisitor, functionDescriptor, methodContext, jvmMethodSignature, functionGenerationStrategy, this.memberCodegen, this.state.getJvmDefaultMode());
        } else if (z) {
            methodVisitor.visitCode();
            FunctionDescriptor functionDescriptorCreateStaticFunctionDescriptor = JvmStaticInCompanionObjectGenerator.createStaticFunctionDescriptor(functionDescriptor);
            generateDelegateToStaticMethodBody(false, methodVisitor, this.typeMapper.mapAsmMethod((FunctionDescriptor) this.memberCodegen.getContext().accessibleDescriptor(functionDescriptorCreateStaticFunctionDescriptor, null)), this.typeMapper.mapClass((ClassifierDescriptor) functionDescriptorCreateStaticFunctionDescriptor.getContainingDeclaration()).getInternalName(), false);
        }
        endVisit(methodVisitor, (String) null, jvmDeclarationOrigin.getElement());
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0076  */
    public static void generateParameterAnnotations(FunctionDescriptor functionDescriptor, MethodVisitor methodVisitor, JvmMethodSignature jvmMethodSignature, List<ValueParameterDescriptor> list, MemberCodegen<?> memberCodegen, GenerationState generationState, boolean z) {
        ValueParameterDescriptor dispatchReceiverParameter;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(50);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(51);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(52);
        }
        if (list == null) {
            $$$reportNull$$$0(53);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(54);
        }
        if (generationState == null) {
            $$$reportNull$$$0(55);
        }
        if (KotlinTypeMapper.isAccessor(functionDescriptor)) {
            return;
        }
        Iterator<ValueParameterDescriptor> it = list.iterator();
        List<JvmMethodParameterSignature> valueParameters = jvmMethodSignature.getValueParameters();
        int iCount = CollectionsKt.count(valueParameters, new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((JvmMethodParameterSignature) obj).getKind().isSkippedInGenericSignature());
            }
        });
        Asm7UtilKt.visitAnnotableParameterCount(methodVisitor, valueParameters.size() - iCount);
        boolean z2 = OwnerKind.DEFAULT_IMPLS == memberCodegen.context.getContextKind();
        for (int i = 0; i < valueParameters.size(); i++) {
            JvmMethodParameterSignature jvmMethodParameterSignature = valueParameters.get(i);
            JvmMethodParameterKind kind = jvmMethodParameterSignature.getKind();
            if (!kind.isSkippedInGenericSignature()) {
                if (kind == JvmMethodParameterKind.VALUE) {
                    dispatchReceiverParameter = it.next();
                } else if (kind == JvmMethodParameterKind.RECEIVER) {
                    dispatchReceiverParameter = JvmCodegenUtil.getDirectMember(functionDescriptor).getExtensionReceiverParameter();
                } else {
                    dispatchReceiverParameter = (kind == JvmMethodParameterKind.THIS && z2) ? JvmCodegenUtil.getDirectMember(functionDescriptor).mo6753getDispatchReceiverParameter() : null;
                }
                if (dispatchReceiverParameter != null) {
                    AnnotationCodegen.forParameter(i - iCount, methodVisitor, memberCodegen, generationState, z).genAnnotations(dispatchReceiverParameter, jvmMethodParameterSignature.getAsmType(), dispatchReceiverParameter.getReturnType(), functionDescriptor, Collections.emptyList());
                }
            }
        }
    }

    private static Type getThisTypeForFunction(FunctionDescriptor functionDescriptor, MethodContext methodContext, KotlinTypeMapper kotlinTypeMapper) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(56);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(57);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(58);
        }
        ReceiverParameterDescriptor dispatchReceiverParameter = functionDescriptor.mo6753getDispatchReceiverParameter();
        if (functionDescriptor instanceof ConstructorDescriptor) {
            return kotlinTypeMapper.mapTypeAsDeclaration(functionDescriptor);
        }
        if (dispatchReceiverParameter != null) {
            return kotlinTypeMapper.mapTypeAsDeclaration(dispatchReceiverParameter.getType());
        }
        if (ExpressionTypingUtils.isFunctionLiteral(functionDescriptor) || ExpressionTypingUtils.isLocalFunction(functionDescriptor) || ExpressionTypingUtils.isFunctionExpression(functionDescriptor)) {
            return kotlinTypeMapper.mapClass(methodContext.getThisDescriptor());
        }
        return null;
    }

    public static void generateMethodBody(MethodVisitor methodVisitor, FunctionDescriptor functionDescriptor, MethodContext methodContext, JvmMethodSignature jvmMethodSignature, FunctionGenerationStrategy functionGenerationStrategy, MemberCodegen<?> memberCodegen, JvmDefaultMode jvmDefaultMode) {
        int i;
        int i2;
        Label label;
        Label label2;
        String strAsString;
        ValueParameterDescriptor inlineArgumentDescriptor;
        FunctionDescriptor orCreateJvmSuspendFunctionView = functionDescriptor;
        if (methodVisitor == null) {
            $$$reportNull$$$0(59);
        }
        if (orCreateJvmSuspendFunctionView == null) {
            $$$reportNull$$$0(60);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(61);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(62);
        }
        if (functionGenerationStrategy == null) {
            $$$reportNull$$$0(63);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(64);
        }
        if (jvmDefaultMode == null) {
            $$$reportNull$$$0(65);
        }
        methodVisitor.visitCode();
        Label label3 = new Label();
        methodVisitor.visitLabel(label3);
        KotlinTypeMapper kotlinTypeMapper = memberCodegen.typeMapper;
        kotlinTypeMapper.getClass();
        if (BuiltinSpecialBridgesUtil.shouldHaveTypeSafeBarrier(orCreateJvmSuspendFunctionView, new FunctionCodegen$$ExternalSyntheticLambda3(kotlinTypeMapper))) {
            generateTypeCheckBarrierIfNeeded(new InstructionAdapter(methodVisitor), orCreateJvmSuspendFunctionView, jvmMethodSignature.getReturnType(), null, kotlinTypeMapper);
        }
        if (methodContext.getParentContext() instanceof MultifileClassFacadeContext) {
            generateFacadeDelegateMethodBody(methodVisitor, jvmMethodSignature.getAsmMethod(), (MultifileClassFacadeContext) methodContext.getParentContext());
            i2 = -1;
            label2 = null;
            label = new Label();
            i = -1;
        } else if (isCompatibilityStubInDefaultImpls(orCreateJvmSuspendFunctionView, methodContext, jvmDefaultMode)) {
            FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) ((DefaultImplsClassContext) methodContext.getParentContext()).getInterfaceContext().getAccessorForJvmDefaultCompatibility(orCreateJvmSuspendFunctionView);
            DescriptorAsmUtil.getMethodAsmFlags(orCreateJvmSuspendFunctionView, OwnerKind.DEFAULT_IMPLS, methodContext.getState());
            CallableMethod callableMethodMapToCallableMethod = kotlinTypeMapper.mapToCallableMethod(functionDescriptor2, false);
            i = -1;
            generateDelegateToStaticMethodBody(true, methodVisitor, callableMethodMapToCallableMethod.getAsmMethod(), callableMethodMapToCallableMethod.getOwner().getInternalName(), true, jvmMethodSignature.getReturnType());
            label = new Label();
            i2 = -1;
            label2 = null;
        } else {
            i = -1;
            FrameMap frameMapCreateFrameMap = createFrameMap(memberCodegen.state, jvmMethodSignature, functionDescriptor.getExtensionReceiverParameter(), functionDescriptor.getValueParameters(), DescriptorAsmUtil.isStaticMethod(methodContext.getContextKind(), orCreateJvmSuspendFunctionView));
            if (functionDescriptor.isSuspend() && CoroutineCodegenUtilKt.isSuspendFunctionNotSuspensionView(functionDescriptor)) {
                FunctionDescriptor orCreateJvmSuspendFunctionView2 = CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(orCreateJvmSuspendFunctionView, memberCodegen.state);
                ValueParameterDescriptor valueParameterDescriptor = orCreateJvmSuspendFunctionView2.getValueParameters().get(orCreateJvmSuspendFunctionView2.getValueParameters().size() - 1);
                frameMapCreateFrameMap.enter(valueParameterDescriptor, kotlinTypeMapper.mapType(valueParameterDescriptor));
            }
            int iNewFakeTempIndex = (methodContext.isInlineMethodContext() || (methodContext instanceof InlineLambdaContext)) ? newFakeTempIndex(methodVisitor, frameMapCreateFrameMap) : -1;
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodContext.setMethodStartLabel(label4);
            if (!functionGenerationStrategy.skipNotNullAssertionsForParameters()) {
                DescriptorAsmUtil.genNotNullAssertionsForParameters(new InstructionAdapter(methodVisitor), memberCodegen.state, orCreateJvmSuspendFunctionView, frameMapCreateFrameMap);
            }
            Label label5 = new Label();
            methodContext.setMethodEndLabel(label5);
            functionGenerationStrategy.generateBody(methodVisitor, frameMapCreateFrameMap, jvmMethodSignature, methodContext, memberCodegen);
            i2 = iNewFakeTempIndex;
            label = label5;
            label2 = label4;
        }
        methodVisitor.visitLabel(label);
        Type thisTypeForFunction = getThisTypeForFunction(orCreateJvmSuspendFunctionView, methodContext, kotlinTypeMapper);
        if ((orCreateJvmSuspendFunctionView instanceof AnonymousFunctionDescriptor) && functionDescriptor.isSuspend()) {
            orCreateJvmSuspendFunctionView = CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(orCreateJvmSuspendFunctionView, memberCodegen.state);
        }
        int i3 = i2;
        Label label6 = label;
        FunctionDescriptor functionDescriptor3 = orCreateJvmSuspendFunctionView;
        generateLocalVariableTable(methodVisitor, jvmMethodSignature, orCreateJvmSuspendFunctionView, thisTypeForFunction, label3, label, methodContext.getContextKind(), memberCodegen.state, i2 < 0 ? 0 : 1);
        if (methodContext.isInlineMethodContext() && i3 != i) {
            methodVisitor.visitLocalVariable(JvmAbi.LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION + kotlinTypeMapper.mapAsmMethod(functionDescriptor3).getName(), Type.INT_TYPE.getDescriptor(), null, label2, label6, i3);
            return;
        }
        if (!(methodContext instanceof InlineLambdaContext) || thisTypeForFunction == null || i3 == i) {
            return;
        }
        String internalName = thisTypeForFunction.getInternalName();
        String strSubstringAfterLast = StringsKt.substringAfterLast(internalName, '/', internalName);
        T t = memberCodegen.element;
        if ((t instanceof KtFunction) && (inlineArgumentDescriptor = InlineUtil.getInlineArgumentDescriptor((KtFunction) t, memberCodegen.bindingContext)) != null) {
            strAsString = inlineArgumentDescriptor.getContainingDeclaration().getShortName().asString();
        } else {
            strAsString = "unknown";
        }
        methodVisitor.visitLocalVariable("$i$a$-" + strAsString + Parameters.DEFAULT_OPTION_PREFIXES + strSubstringAfterLast, Type.INT_TYPE.getDescriptor(), null, label2, label6, i3);
    }

    private static int newFakeTempIndex(MethodVisitor methodVisitor, FrameMap frameMap) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(66);
        }
        if (frameMap == null) {
            $$$reportNull$$$0(67);
        }
        int iEnterTemp = frameMap.enterTemp(Type.INT_TYPE);
        methodVisitor.visitLdcInsn(0);
        methodVisitor.visitVarInsn(54, iEnterTemp);
        return iEnterTemp;
    }

    private static boolean isCompatibilityStubInDefaultImpls(FunctionDescriptor functionDescriptor, MethodContext methodContext, JvmDefaultMode jvmDefaultMode) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(68);
        }
        if (methodContext == null) {
            $$$reportNull$$$0(69);
        }
        if (jvmDefaultMode == null) {
            $$$reportNull$$$0(70);
        }
        return OwnerKind.DEFAULT_IMPLS == methodContext.getContextKind() && isCompiledInCompatibilityMode(jvmDefaultMode, functionDescriptor) && JvmAnnotationUtilKt.checkIsImplementationCompiledToJvmDefault(functionDescriptor, jvmDefaultMode);
    }

    private static boolean isCompiledInCompatibilityMode(JvmDefaultMode jvmDefaultMode, CallableMemberDescriptor callableMemberDescriptor) {
        return jvmDefaultMode.isCompatibility() || (jvmDefaultMode == JvmDefaultMode.ALL_INCOMPATIBLE && JvmAnnotationUtilKt.hasJvmDefaultWithCompatibilityAnnotation(callableMemberDescriptor.getContainingDeclaration()));
    }

    private static void generateLocalVariableTable(MethodVisitor methodVisitor, JvmMethodSignature jvmMethodSignature, FunctionDescriptor functionDescriptor, Type type, Label label, Label label2, OwnerKind ownerKind, GenerationState generationState, int i) {
        FunctionDescriptor functionDescriptor2;
        if (methodVisitor == null) {
            $$$reportNull$$$0(71);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(72);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(73);
        }
        if (label == null) {
            $$$reportNull$$$0(74);
        }
        if (label2 == null) {
            $$$reportNull$$$0(75);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(76);
        }
        if (generationState == null) {
            $$$reportNull$$$0(77);
        }
        if (functionDescriptor.isSuspend() && !(functionDescriptor instanceof AnonymousFunctionDescriptor) && (functionDescriptor2 = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(functionDescriptor)) != functionDescriptor) {
            generateLocalVariableTable(methodVisitor, new JvmMethodSignature(jvmMethodSignature.getAsmMethod(), jvmMethodSignature.getValueParameters().subList(0, jvmMethodSignature.getValueParameters().size() - 1)), functionDescriptor2, type, label, label2, ownerKind, generationState, i);
        } else {
            generateLocalVariablesForParameters(methodVisitor, jvmMethodSignature, functionDescriptor, type, label, label2, functionDescriptor.getValueParameters(), DescriptorAsmUtil.isStaticMethod(ownerKind, functionDescriptor), generationState);
        }
    }

    public static void generateLocalVariablesForParameters(MethodVisitor methodVisitor, JvmMethodSignature jvmMethodSignature, FunctionDescriptor functionDescriptor, Type type, Label label, Label label2, Collection<ValueParameterDescriptor> collection, boolean z, GenerationState generationState) {
        boolean z2;
        String nameForReceiverParameter;
        if (methodVisitor == null) {
            $$$reportNull$$$0(78);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(79);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(80);
        }
        if (label == null) {
            $$$reportNull$$$0(81);
        }
        if (label2 == null) {
            $$$reportNull$$$0(82);
        }
        if (generationState == null) {
            $$$reportNull$$$0(83);
        }
        Iterator<ValueParameterDescriptor> it = collection.iterator();
        List<JvmMethodParameterSignature> valueParameters = jvmMethodSignature.getValueParameters();
        int i = 0;
        if (!z) {
            if (type != null) {
                methodVisitor.visitLocalVariable("this", type.getDescriptor(), null, label, label2, 0);
            }
            i = 1;
        }
        KotlinTypeMapper typeMapper = generationState.getTypeMapper();
        int i2 = 0;
        int size = i;
        boolean z3 = true;
        while (i2 < valueParameters.size()) {
            JvmMethodParameterSignature jvmMethodParameterSignature = valueParameters.get(i2);
            JvmMethodParameterKind kind = jvmMethodParameterSignature.getKind();
            int i3 = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind[kind.ordinal()];
            if (i3 != 1) {
                if (i3 == 2 || i3 == 3) {
                    nameForReceiverParameter = DescriptorAsmUtil.getNameForReceiverParameter(functionDescriptor, typeMapper.getBindingContext(), generationState.getLanguageVersionSettings());
                } else if (i3 == 4) {
                    nameForReceiverParameter = AsmUtil.CAPTURED_THIS_FIELD;
                } else if (i3 == 5) {
                    nameForReceiverParameter = z3 ? "$enum$name" : "$enum$ordinal";
                    z3 = !z3;
                } else {
                    String lowerCase = kind.name().toLowerCase();
                    nameForReceiverParameter = (needIndexForVar(kind) ? new StringBuilder("$").append(lowerCase).append("$").append(i2) : new StringBuilder("$").append(lowerCase)).toString();
                }
                z2 = z3;
            } else {
                ValueParameterDescriptor next = it.next();
                String nameForDestructuredParameterOrNull = VariableAsmNameManglingUtils.getNameForDestructuredParameterOrNull(next);
                if (nameForDestructuredParameterOrNull == null) {
                    nameForDestructuredParameterOrNull = computeParameterName(i2, next);
                }
                z2 = z3;
                nameForReceiverParameter = nameForDestructuredParameterOrNull;
            }
            Type asmType = jvmMethodParameterSignature.getAsmType();
            methodVisitor.visitLocalVariable(nameForReceiverParameter, asmType.getDescriptor(), null, label, label2, size);
            size += asmType.getSize();
            i2++;
            z3 = z2;
        }
    }

    private static String computeParameterName(int i, ValueParameterDescriptor valueParameterDescriptor) {
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
        if ((psiElementDescriptorToDeclaration instanceof KtParameter) && UnderscoreUtilKt.isSingleUnderscore((KtParameter) psiElementDescriptorToDeclaration)) {
            return "$noName_" + i;
        }
        return valueParameterDescriptor.getShortName().asString();
    }

    public static void generateFacadeDelegateMethodBody(MethodVisitor methodVisitor, Method method, MultifileClassFacadeContext multifileClassFacadeContext) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(84);
        }
        if (method == null) {
            $$$reportNull$$$0(85);
        }
        if (multifileClassFacadeContext == null) {
            $$$reportNull$$$0(86);
        }
        generateDelegateToStaticMethodBody(true, methodVisitor, method, multifileClassFacadeContext.getFilePartType().getInternalName(), false);
    }

    private static void generateDelegateToMethodBody(int i, MethodVisitor methodVisitor, Method method, String str, int i2, boolean z, Type type) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(87);
        }
        if (method == null) {
            $$$reportNull$$$0(88);
        }
        if (str == null) {
            $$$reportNull$$$0(89);
        }
        if (type == null) {
            $$$reportNull$$$0(90);
        }
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
        Type[] argumentTypes = method.getArgumentTypes();
        Label label = new Label();
        instructionAdapter.visitLabel(label);
        instructionAdapter.visitLineNumber(1, label);
        if (i == -1) {
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
            i = 1;
        }
        for (Type type2 : argumentTypes) {
            instructionAdapter.load(i, type2);
            i += type2.getSize();
        }
        instructionAdapter.visitMethodInsn(i2, str, method.getName(), method.getDescriptor(), z);
        StackValue.onStack(method.getReturnType()).coerceTo(type, null, instructionAdapter);
        instructionAdapter.areturn(type);
    }

    private static void generateDelegateToStaticErasedVersion(MethodVisitor methodVisitor, Method method, Type type, String str, Type type2) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(91);
        }
        if (method == null) {
            $$$reportNull$$$0(92);
        }
        if (type == null) {
            $$$reportNull$$$0(93);
        }
        if (str == null) {
            $$$reportNull$$$0(94);
        }
        if (type2 == null) {
            $$$reportNull$$$0(95);
        }
        String internalName = type.getInternalName();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
        Type[] argumentTypes = method.getArgumentTypes();
        Label label = new Label();
        instructionAdapter.visitLabel(label);
        instructionAdapter.visitLineNumber(1, label);
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        instructionAdapter.visitFieldInsn(180, internalName, str, type2.getDescriptor());
        int size = 1;
        for (int i = 1; i < argumentTypes.length; i++) {
            Type type3 = argumentTypes[i];
            instructionAdapter.load(size, type3);
            size += type3.getSize();
        }
        instructionAdapter.invokestatic(internalName, method.getName(), method.getDescriptor(), false);
        instructionAdapter.areturn(method.getReturnType());
    }

    private static void generateDelegateToStaticMethodBody(boolean z, MethodVisitor methodVisitor, Method method, String str, boolean z2) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(96);
        }
        if (method == null) {
            $$$reportNull$$$0(97);
        }
        if (str == null) {
            $$$reportNull$$$0(98);
        }
        generateDelegateToStaticMethodBody(z, methodVisitor, method, str, z2, method.getReturnType());
    }

    private static void generateDelegateToStaticMethodBody(boolean z, MethodVisitor methodVisitor, Method method, String str, boolean z2, Type type) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(99);
        }
        if (method == null) {
            $$$reportNull$$$0(100);
        }
        if (str == null) {
            $$$reportNull$$$0(101);
        }
        if (type == null) {
            $$$reportNull$$$0(102);
        }
        generateDelegateToMethodBody(!z ? 1 : 0, methodVisitor, method, str, 184, z2, type);
    }

    private static boolean needIndexForVar(JvmMethodParameterKind jvmMethodParameterKind) {
        return jvmMethodParameterKind == JvmMethodParameterKind.CAPTURED_LOCAL_VARIABLE || jvmMethodParameterKind == JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL || jvmMethodParameterKind == JvmMethodParameterKind.SUPER_CALL_PARAM;
    }

    public static void endVisit(MethodVisitor methodVisitor, String str) {
        endVisit(methodVisitor, str, (PsiElement) null);
    }

    public static void endVisit(MethodVisitor methodVisitor, String str, KtPureElement ktPureElement) {
        endVisit(methodVisitor, str, (PsiElement) (ktPureElement == null ? null : ktPureElement.getPsiOrParent()));
    }

    public static void endVisit(MethodVisitor methodVisitor, String str, KtElement ktElement) {
        if (ktElement == null) {
            $$$reportNull$$$0(103);
        }
        endVisit(methodVisitor, str, (PsiElement) ktElement);
    }

    public static void endVisit(MethodVisitor methodVisitor, String str, PsiElement psiElement) {
        try {
            methodVisitor.visitMaxs(-1, -1);
            methodVisitor.visitEnd();
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            String strRenderByteCodeIfAvailable = renderByteCodeIfAvailable(methodVisitor);
            StringBuilder sbAppend = new StringBuilder("wrong bytecode generated").append(str != null ? " for " + str : "").append("\n");
            if (strRenderByteCodeIfAvailable == null) {
                strRenderByteCodeIfAvailable = "<no bytecode>";
            }
            throw new CompilationException(sbAppend.append(strRenderByteCodeIfAvailable).toString(), th, psiElement);
        }
    }

    public static String renderByteCodeIfAvailable(MethodVisitor methodVisitor) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(104);
        }
        if (methodVisitor instanceof TransformationMethodVisitor) {
            methodVisitor = ((TransformationMethodVisitor) methodVisitor).getTraceMethodVisitorIfPossible();
        }
        if (!(methodVisitor instanceof TraceMethodVisitor)) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ((TraceMethodVisitor) methodVisitor).p.print(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private boolean hasSpecialBridgeMethod(FunctionDescriptor functionDescriptor) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(105);
        }
        if (SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor) == null) {
            return false;
        }
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        kotlinTypeMapper.getClass();
        return !BuiltinSpecialBridgesUtil.generateBridgesForBuiltinSpecial(functionDescriptor, new FunctionCodegen$$ExternalSyntheticLambda3(kotlinTypeMapper), this.state).isEmpty();
    }

    public void generateBridges(FunctionDescriptor functionDescriptor) {
        PsiElement sourceFromDescriptor;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(106);
        }
        if ((functionDescriptor instanceof ConstructorDescriptor) || this.owner.getContextKind() == OwnerKind.DEFAULT_IMPLS || JvmBridgesImplKt.isAbstractOnJvmIgnoringActualModality(functionDescriptor, this.state.getJvmDefaultMode()) || DescriptorUtils.isMethodOfAny(functionDescriptor)) {
            return;
        }
        if (SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor) == null) {
            KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
            kotlinTypeMapper.getClass();
            Set<Bridge<Method, DescriptorBasedFunctionHandleForJvm>> setGenerateBridgesForFunctionDescriptorForJvm = JvmBridgesImplKt.generateBridgesForFunctionDescriptorForJvm(functionDescriptor, new FunctionCodegen$$ExternalSyntheticLambda3(kotlinTypeMapper), this.state);
            if (setGenerateBridgesForFunctionDescriptorForJvm.isEmpty()) {
                return;
            }
            sourceFromDescriptor = functionDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION ? DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor) : null;
            boolean z = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(functionDescriptor) != null;
            for (Bridge<Method, DescriptorBasedFunctionHandleForJvm> bridge : setGenerateBridgesForFunctionDescriptorForJvm) {
                generateBridge(sourceFromDescriptor, functionDescriptor, bridge.getFrom(), bridge.getTo(), getBridgeReturnType(bridge), z, false);
            }
            return;
        }
        KotlinTypeMapper kotlinTypeMapper2 = this.typeMapper;
        kotlinTypeMapper2.getClass();
        Set<BridgeForBuiltinSpecial> setGenerateBridgesForBuiltinSpecial = BuiltinSpecialBridgesUtil.generateBridgesForBuiltinSpecial(functionDescriptor, new FunctionCodegen$$ExternalSyntheticLambda3(kotlinTypeMapper2), this.state);
        if (!setGenerateBridgesForBuiltinSpecial.isEmpty()) {
            sourceFromDescriptor = functionDescriptor.getKind() == CallableMemberDescriptor.Kind.DECLARATION ? DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor) : null;
            for (BridgeForBuiltinSpecial bridgeForBuiltinSpecial : setGenerateBridgesForBuiltinSpecial) {
                generateBridge(sourceFromDescriptor, functionDescriptor, (Method) bridgeForBuiltinSpecial.getFrom(), (Method) bridgeForBuiltinSpecial.getTo(), functionDescriptor.getReturnType(), bridgeForBuiltinSpecial.getIsSpecial(), bridgeForBuiltinSpecial.getIsDelegateToSuper());
            }
        }
        if (functionDescriptor.getKind().isReal() || !DescriptorAsmUtil.isAbstractMethod(functionDescriptor, OwnerKind.IMPLEMENTATION, this.state.getJvmDefaultMode())) {
            return;
        }
        CallableMemberDescriptor overriddenBuiltinReflectingJvmDescriptor = SpecialBuiltinMembers.getOverriddenBuiltinReflectingJvmDescriptor(functionDescriptor);
        if (isThereOverriddenInKotlinClass(functionDescriptor)) {
            return;
        }
        Method methodMapAsmMethod = this.typeMapper.mapAsmMethod(functionDescriptor);
        this.v.newMethod(JvmDeclarationOriginKt.AugmentedBuiltInApi(overriddenBuiltinReflectingJvmDescriptor), DescriptorAsmUtil.getVisibilityAccessFlag(functionDescriptor) | 1024, methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), null, null);
    }

    private KotlinType getBridgeReturnType(Bridge<Method, DescriptorBasedFunctionHandleForJvm> bridge) {
        Iterator it = bridge.getOriginalFunctions().iterator();
        if (it.hasNext()) {
            return ((DescriptorBasedFunctionHandleForJvm) it.next()).getDescriptor().getReturnType();
        }
        if (this.state.getClassBuilderMode().mightBeIncorrectCode) {
            return this.state.getModule().getBuiltIns().getNullableAnyType();
        }
        if (bridge.getOriginalFunctions().isEmpty()) {
            throw new AssertionError("No overridden functions for the bridge method '" + bridge.getTo() + FormatHelper.QUOTE);
        }
        throw new AssertionError("No return type for the bridge method '" + bridge.getTo() + "' found in the following overridden functions:\n" + ((String) bridge.getOriginalFunctions().stream().map(new Function() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DescriptorBasedFunctionHandleForJvm) obj).getDescriptor().toString();
            }
        }).collect(Collectors.joining("\n"))));
    }

    public static boolean isThereOverriddenInKotlinClass(CallableMemberDescriptor callableMemberDescriptor) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(107);
        }
        return CollectionsKt.any(DescriptorUtils.getAllOverriddenDescriptors(callableMemberDescriptor), new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) obj;
                return Boolean.valueOf(!(callableMemberDescriptor2.getContainingDeclaration() instanceof JavaClassDescriptor) && DescriptorUtils.isClass(callableMemberDescriptor2.getContainingDeclaration()));
            }
        });
    }

    public static String[] getThrownExceptions(FunctionDescriptor functionDescriptor, final KotlinTypeMapper kotlinTypeMapper) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(108);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(109);
        }
        String[] stringArray = ArrayUtil.toStringArray(CollectionsKt.map(getThrownExceptions(functionDescriptor, kotlinTypeMapper.getLanguageVersionSettings()), new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return kotlinTypeMapper.mapClass((ClassDescriptor) obj).getInternalName();
            }
        }));
        if (stringArray == null) {
            $$$reportNull$$$0(110);
        }
        return stringArray;
    }

    public static List<ClassDescriptor> getThrownExceptions(final FunctionDescriptor functionDescriptor, LanguageVersionSettings languageVersionSettings) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(111);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(112);
        }
        if (functionDescriptor.getKind() == CallableMemberDescriptor.Kind.DELEGATION && languageVersionSettings.supportsFeature(LanguageFeature.DoNotGenerateThrowsForDelegatedKotlinMembers)) {
            List<ClassDescriptor> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(113);
            }
            return listEmptyList;
        }
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = functionDescriptor.getAnnotations().mo6752findAnnotation(ThrowUtilKt.JVM_THROWS_ANNOTATION_FQ_NAME);
        if (annotationDescriptorMo6752findAnnotation == null) {
            List<ClassDescriptor> listEmptyList2 = Collections.emptyList();
            if (listEmptyList2 == null) {
                $$$reportNull$$$0(114);
            }
            return listEmptyList2;
        }
        Collection<ConstantValue<?>> collectionValues = annotationDescriptorMo6752findAnnotation.getAllValueArguments().values();
        if (collectionValues.isEmpty()) {
            List<ClassDescriptor> listEmptyList3 = Collections.emptyList();
            if (listEmptyList3 == null) {
                $$$reportNull$$$0(115);
            }
            return listEmptyList3;
        }
        ConstantValue<?> next = collectionValues.iterator().next();
        if (next instanceof ArrayValue) {
            List<ClassDescriptor> listMapNotNull = CollectionsKt.mapNotNull(((ArrayValue) next).getValue(), new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return FunctionCodegen.lambda$getThrownExceptions$4(functionDescriptor, (ConstantValue) obj);
                }
            });
            if (listMapNotNull == null) {
                $$$reportNull$$$0(117);
            }
            return listMapNotNull;
        }
        List<ClassDescriptor> listEmptyList4 = Collections.emptyList();
        if (listEmptyList4 == null) {
            $$$reportNull$$$0(116);
        }
        return listEmptyList4;
    }

    static /* synthetic */ ClassDescriptor lambda$getThrownExceptions$4(FunctionDescriptor functionDescriptor, ConstantValue constantValue) {
        if (constantValue instanceof KClassValue) {
            return DescriptorUtils.getClassDescriptorForType(((KClassValue) constantValue).getArgumentType(DescriptorUtilsKt.getModule(functionDescriptor)));
        }
        return null;
    }

    void generateDefaultIfNeeded(MethodContext methodContext, FunctionDescriptor functionDescriptor, OwnerKind ownerKind, DefaultParameterValueLoader defaultParameterValueLoader, KtNamedFunction ktNamedFunction) {
        if (methodContext == null) {
            $$$reportNull$$$0(118);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(119);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(120);
        }
        if (defaultParameterValueLoader == null) {
            $$$reportNull$$$0(121);
        }
        DeclarationDescriptor containingDeclaration = methodContext.getContextDescriptor().getContainingDeclaration();
        int i = 0;
        if (!DescriptorUtils.isInterface(containingDeclaration) || processInterfaceMethod(functionDescriptor, ownerKind, true, false, this.state.getJvmDefaultMode())) {
            if ((!InlineClassesUtilsKt.isInlineClass(containingDeclaration) || ownerKind == OwnerKind.ERASED_INLINE_CLASS) && isDefaultNeeded(functionDescriptor, ktNamedFunction)) {
                if ((DescriptorUtils.isInterface(functionDescriptor.getContainingDeclaration()) && ownerKind != OwnerKind.DEFAULT_IMPLS) || (!DescriptorVisibilities.isPrivate(functionDescriptor.getVisibility()) && !InlineOnlyKt.isInlineOnlyPrivateInBytecode(functionDescriptor))) {
                    i = 1;
                }
                int deprecatedAccessFlag = DescriptorAsmUtil.getDeprecatedAccessFlag(functionDescriptor) | i;
                int i2 = deprecatedAccessFlag | 4096;
                if (!(functionDescriptor instanceof ConstructorDescriptor) || InlineClassesUtilsKt.isInlineClass(functionDescriptor.getContainingDeclaration())) {
                    i2 = deprecatedAccessFlag | 4104;
                }
                Method methodMapDefaultMethod = this.typeMapper.mapDefaultMethod(functionDescriptor, ownerKind);
                MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.Synthetic(ktNamedFunction, functionDescriptor), i2, methodMapDefaultMethod.getName(), methodMapDefaultMethod.getDescriptor(), null, getThrownExceptions(functionDescriptor, this.typeMapper));
                if (!this.state.getClassBuilderMode().generateBodies) {
                    if (this.owner instanceof MultifileClassFacadeContext) {
                        endVisit(methodVisitorNewMethod, "default method delegation", DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor));
                        return;
                    } else {
                        endVisit(methodVisitorNewMethod, "default method", DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor));
                        return;
                    }
                }
                if (this.owner instanceof MultifileClassFacadeContext) {
                    methodVisitorNewMethod.visitCode();
                    generateFacadeDelegateMethodBody(methodVisitorNewMethod, methodMapDefaultMethod, (MultifileClassFacadeContext) this.owner);
                    endVisit(methodVisitorNewMethod, "default method delegation", DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor));
                } else if (isCompatibilityStubInDefaultImpls(functionDescriptor, methodContext, this.state.getJvmDefaultMode())) {
                    methodVisitorNewMethod.visitCode();
                    generateDelegateToStaticMethodBody(true, methodVisitorNewMethod, this.typeMapper.mapDefaultMethod(functionDescriptor, OwnerKind.IMPLEMENTATION), this.typeMapper.mapOwner(functionDescriptor).getInternalName(), true);
                    endVisit(methodVisitorNewMethod, "default method delegation to interface one", DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor));
                } else {
                    methodVisitorNewMethod.visitCode();
                    generateDefaultImplBody(methodContext, functionDescriptor, methodVisitorNewMethod, defaultParameterValueLoader, ktNamedFunction, this.memberCodegen, methodMapDefaultMethod);
                    endVisit(methodVisitorNewMethod, "default method", DescriptorToSourceUtils.getSourceFromDescriptor(functionDescriptor));
                }
            }
        }
    }

    public static void generateDefaultImplBody(MethodContext methodContext, FunctionDescriptor functionDescriptor, MethodVisitor methodVisitor, final DefaultParameterValueLoader defaultParameterValueLoader, KtNamedFunction ktNamedFunction, MemberCodegen<?> memberCodegen, Method method) {
        List<ValueParameterDescriptor> functionParametersForDefaultValueGeneration;
        if (methodContext == null) {
            $$$reportNull$$$0(122);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(123);
        }
        if (methodVisitor == null) {
            $$$reportNull$$$0(124);
        }
        if (defaultParameterValueLoader == null) {
            $$$reportNull$$$0(125);
        }
        if (memberCodegen == null) {
            $$$reportNull$$$0(126);
        }
        if (method == null) {
            $$$reportNull$$$0(127);
        }
        if (methodContext instanceof ConstructorContext) {
            ((ConstructorContext) methodContext).setThisInitialized(false);
        }
        GenerationState generationState = memberCodegen.state;
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignatureWithGeneric = generationState.getTypeMapper().mapSignatureWithGeneric(functionDescriptor, methodContext.getContextKind());
        if (functionDescriptor.isSuspend()) {
            functionParametersForDefaultValueGeneration = CollectionsKt.plus((Collection<? extends Object>) CodegenUtil.getFunctionParametersForDefaultValueGeneration((FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(functionDescriptor), null), CollectionsKt.last((List) functionDescriptor.getValueParameters()));
        } else {
            functionParametersForDefaultValueGeneration = CodegenUtil.getFunctionParametersForDefaultValueGeneration(functionDescriptor, null);
        }
        List<ValueParameterDescriptor> list = functionParametersForDefaultValueGeneration;
        boolean zIsStaticMethod = DescriptorAsmUtil.isStaticMethod(methodContext.getContextKind(), functionDescriptor);
        FrameMap frameMapCreateFrameMap = createFrameMap(generationState, jvmMethodGenericSignatureMapSignatureWithGeneric, functionDescriptor.getExtensionReceiverParameter(), list, zIsStaticMethod);
        FrameMap frameMap = frameMapCreateFrameMap;
        final ExpressionCodegen expressionCodegen = new ExpressionCodegen(methodVisitor, frameMapCreateFrameMap, jvmMethodGenericSignatureMapSignatureWithGeneric.getReturnType(), methodContext, generationState, memberCodegen);
        CallGenerator orCreateCallGeneratorForDefaultImplBody = expressionCodegen.getOrCreateCallGeneratorForDefaultImplBody(functionDescriptor, ktNamedFunction);
        final InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
        genDefaultSuperCallCheckIfNeeded(instructionAdapter, functionDescriptor, method);
        List<JvmMethodParameterSignature> valueParameters = jvmMethodGenericSignatureMapSignatureWithGeneric.getValueParameters();
        int i = 0;
        while (i < valueParameters.size() && valueParameters.get(i).getKind() != JvmMethodParameterKind.VALUE) {
            i++;
        }
        int iEnterTemp = frameMap.enterTemp(Type.INT_TYPE);
        for (int i2 = 1; i2 < list.size(); i2++) {
            if (i2 % 32 == 0) {
                frameMap.enterTemp(Type.INT_TYPE);
            }
        }
        frameMap.enterTemp(AsmTypes.OBJECT_TYPE);
        int i3 = 0;
        while (i3 < list.size()) {
            int i4 = (i3 / 32) + iEnterTemp;
            final ValueParameterDescriptor valueParameterDescriptor = list.get(i3);
            final Type asmType = valueParameters.get(i + i3).getAsmType();
            final int index = frameMap.getIndex(valueParameterDescriptor);
            if (valueParameterDescriptor.declaresDefaultValue()) {
                instructionAdapter.load(i4, Type.INT_TYPE);
                instructionAdapter.iconst(1 << (i3 % 32));
                instructionAdapter.and(Type.INT_TYPE);
                Label label = new Label();
                instructionAdapter.ifeq(label);
                CallableDescriptor containingDeclaration = valueParameterDescriptor.getContainingDeclaration();
                expressionCodegen.runWithShouldMarkLineNumbers(((containingDeclaration instanceof MemberDescriptor) && ((MemberDescriptor) containingDeclaration).get$isExpect()) ? false : true, new Supplier() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda4
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return FunctionCodegen.lambda$generateDefaultImplBody$5(index, asmType, valueParameterDescriptor, defaultParameterValueLoader, expressionCodegen, instructionAdapter);
                    }
                });
                instructionAdapter.mark(label);
            }
            i3++;
            iEnterTemp = iEnterTemp;
            list = list;
            jvmMethodGenericSignatureMapSignatureWithGeneric = jvmMethodGenericSignatureMapSignatureWithGeneric;
            generationState = generationState;
            frameMap = frameMap;
        }
        List<ValueParameterDescriptor> list2 = list;
        JvmMethodGenericSignature jvmMethodGenericSignature = jvmMethodGenericSignatureMapSignatureWithGeneric;
        GenerationState generationState2 = generationState;
        FrameMap frameMap2 = frameMap;
        loadExplicitArgumentsOnStack(AsmTypes.OBJECT_TYPE, zIsStaticMethod, jvmMethodGenericSignature, orCreateCallGeneratorForDefaultImplBody);
        for (int i5 = 0; i5 < list2.size(); i5++) {
            ValueParameterDescriptor valueParameterDescriptor2 = list2.get(i5);
            Type asmType2 = valueParameters.get(i + i5).getAsmType();
            orCreateCallGeneratorForDefaultImplBody.putValueIfNeeded(new JvmKotlinType(asmType2, null), StackValue.local(frameMap2.getIndex(valueParameterDescriptor2), asmType2));
        }
        orCreateCallGeneratorForDefaultImplBody.genCall(generationState2.getTypeMapper().mapToCallableMethod(functionDescriptor, false, methodContext.getContextKind()), null, false, expressionCodegen);
        instructionAdapter.areturn(jvmMethodGenericSignature.getReturnType());
    }

    static /* synthetic */ Object lambda$generateDefaultImplBody$5(int i, Type type, ValueParameterDescriptor valueParameterDescriptor, DefaultParameterValueLoader defaultParameterValueLoader, ExpressionCodegen expressionCodegen, InstructionAdapter instructionAdapter) {
        StackValue.local(i, type, valueParameterDescriptor.getType()).store(defaultParameterValueLoader.genValue(valueParameterDescriptor, expressionCodegen), instructionAdapter);
        return null;
    }

    private static void genDefaultSuperCallCheckIfNeeded(InstructionAdapter instructionAdapter, FunctionDescriptor functionDescriptor, Method method) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(128);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(129);
        }
        if (method == null) {
            $$$reportNull$$$0(130);
        }
        if (functionDescriptor instanceof ConstructorDescriptor) {
            return;
        }
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        if ((containingDeclaration instanceof ClassDescriptor) && ((ClassDescriptor) containingDeclaration).getModality() != Modality.FINAL) {
            Label label = new Label();
            instructionAdapter.load((Type.getArgumentsAndReturnSizes(method.getDescriptor()) >> 2) - 2, AsmTypes.OBJECT_TYPE);
            instructionAdapter.ifnull(label);
            AsmUtil.genThrow(instructionAdapter, "java/lang/UnsupportedOperationException", "Super calls with default arguments not supported in this target, function: " + functionDescriptor.getShortName().asString());
            instructionAdapter.visitLabel(label);
        }
    }

    private static FrameMap createFrameMap(GenerationState generationState, JvmMethodSignature jvmMethodSignature, ReceiverParameterDescriptor receiverParameterDescriptor, List<ValueParameterDescriptor> list, boolean z) {
        if (generationState == null) {
            $$$reportNull$$$0(131);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(132);
        }
        if (list == null) {
            $$$reportNull$$$0(133);
        }
        FrameMapWithExpectActualSupport frameMapWithExpectActualSupport = new FrameMapWithExpectActualSupport(generationState.getModule());
        if (!z) {
            frameMapWithExpectActualSupport.enterTemp(AsmTypes.OBJECT_TYPE);
        }
        for (JvmMethodParameterSignature jvmMethodParameterSignature : jvmMethodSignature.getValueParameters()) {
            if (jvmMethodParameterSignature.getKind() == JvmMethodParameterKind.RECEIVER || jvmMethodParameterSignature.getKind() == JvmMethodParameterKind.CONTEXT_RECEIVER) {
                if (receiverParameterDescriptor != null) {
                    frameMapWithExpectActualSupport.enter(receiverParameterDescriptor, generationState.getTypeMapper().mapType(receiverParameterDescriptor));
                } else {
                    frameMapWithExpectActualSupport.enterTemp(jvmMethodParameterSignature.getAsmType());
                }
            } else if (jvmMethodParameterSignature.getKind() != JvmMethodParameterKind.VALUE) {
                frameMapWithExpectActualSupport.enterTemp(jvmMethodParameterSignature.getAsmType());
            }
        }
        for (ValueParameterDescriptor valueParameterDescriptor : list) {
            frameMapWithExpectActualSupport.enter(valueParameterDescriptor, generationState.getTypeMapper().mapType(valueParameterDescriptor));
        }
        return frameMapWithExpectActualSupport;
    }

    private static void loadExplicitArgumentsOnStack(Type type, boolean z, JvmMethodSignature jvmMethodSignature, CallGenerator callGenerator) {
        if (type == null) {
            $$$reportNull$$$0(135);
        }
        if (jvmMethodSignature == null) {
            $$$reportNull$$$0(136);
        }
        if (callGenerator == null) {
            $$$reportNull$$$0(137);
        }
        int size = 0;
        if (!z) {
            callGenerator.putValueIfNeeded(new JvmKotlinType(type, null), StackValue.local(0, type));
            size = type.getSize();
        }
        for (JvmMethodParameterSignature jvmMethodParameterSignature : jvmMethodSignature.getValueParameters()) {
            if (jvmMethodParameterSignature.getKind() != JvmMethodParameterKind.VALUE) {
                Type asmType = jvmMethodParameterSignature.getAsmType();
                callGenerator.putValueIfNeeded(new JvmKotlinType(asmType, null), StackValue.local(size, asmType));
                size += asmType.getSize();
            }
        }
    }

    private boolean isDefaultNeeded(FunctionDescriptor functionDescriptor, KtNamedFunction ktNamedFunction) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(138);
        }
        if (functionDescriptor.isSuspend()) {
            functionDescriptor = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(functionDescriptor);
        }
        return CollectionsKt.any(CodegenUtil.getFunctionParametersForDefaultValueGeneration(functionDescriptor, this.state.getDiagnostics()), new Function1() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(((ValueParameterDescriptor) obj).declaresDefaultValue());
            }
        });
    }

    private void generateBridge(PsiElement psiElement, FunctionDescriptor functionDescriptor, Method method, Method method2, KotlinType kotlinType, boolean z, boolean z2) {
        StackValue stackValueLocal;
        if (functionDescriptor == null) {
            $$$reportNull$$$0(139);
        }
        if (method == null) {
            $$$reportNull$$$0(140);
        }
        if (method2 == null) {
            $$$reportNull$$$0(141);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(142);
        }
        int i = 1;
        char c = 0;
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.Bridge(functionDescriptor, psiElement), (!(z || z2) ? 4096 : 0) | 65 | (z ? 16 : 0), method.getName(), method.getDescriptor(), z ? this.typeMapper.mapSignatureWithGeneric(functionDescriptor, OwnerKind.IMPLEMENTATION).getGenericsSignature() : null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            Type[] argumentTypes = method.getArgumentTypes();
            Type[] argumentTypes2 = method2.getArgumentTypes();
            ArrayList arrayList = new ArrayList(argumentTypes2.length);
            if (functionDescriptor.getExtensionReceiverParameter() != null) {
                arrayList.add(functionDescriptor.getExtensionReceiverParameter());
            }
            arrayList.addAll(functionDescriptor.getValueParameters());
            boolean z3 = arrayList.size() == argumentTypes2.length;
            boolean zIsOverrideOfBigArityFunctionInvoke = JvmCodegenUtil.isOverrideOfBigArityFunctionInvoke(functionDescriptor);
            if (zIsOverrideOfBigArityFunctionInvoke) {
                DescriptorAsmUtil.generateVarargInvokeArityAssert(instructionAdapter, argumentTypes2.length);
            }
            MemberCodegen.markLineNumberForDescriptor(this.owner.getThisDescriptor(), instructionAdapter);
            if (method2.getArgumentTypes().length > 0 && z) {
                generateTypeCheckBarrierIfNeeded(instructionAdapter, functionDescriptor, method.getReturnType(), method2.getArgumentTypes(), this.typeMapper);
            }
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
            int size = 1;
            int i2 = 0;
            while (i2 < argumentTypes2.length) {
                KotlinType type = z3 ? ((ParameterDescriptor) arrayList.get(i2)).getType() : null;
                if (zIsOverrideOfBigArityFunctionInvoke) {
                    stackValueLocal = StackValue.arrayElement(AsmTypes.OBJECT_TYPE, null, StackValue.local(i, argumentTypes[c]), StackValue.constant(i2));
                } else {
                    stackValueLocal = StackValue.local(size, argumentTypes[i2], type);
                    size += argumentTypes[i2].getSize();
                }
                stackValueLocal.put(argumentTypes2[i2], type, instructionAdapter);
                i2++;
                arrayList = arrayList;
                z3 = z3;
                i = 1;
                c = 0;
            }
            if (z2) {
                instructionAdapter.invokespecial(this.typeMapper.mapClass(DescriptorUtils.getSuperClassDescriptor((ClassDescriptor) functionDescriptor.getContainingDeclaration())).getInternalName(), method2.getName(), method2.getDescriptor(), false);
            } else if (DescriptorUtils.isInterface(functionDescriptor.getContainingDeclaration()) && JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptor, this.state.getJvmDefaultMode())) {
                instructionAdapter.invokeinterface(this.v.getThisName(), method2.getName(), method2.getDescriptor());
            } else {
                instructionAdapter.invokevirtual(this.v.getThisName(), method2.getName(), method2.getDescriptor(), false);
            }
            StackValue.coerce(method2.getReturnType(), functionDescriptor.getReturnType(), method.getReturnType(), kotlinType, instructionAdapter);
            instructionAdapter.areturn(method.getReturnType());
            endVisit(methodVisitorNewMethod, "bridge method", psiElement);
        }
    }

    private static void generateTypeCheckBarrierIfNeeded(InstructionAdapter instructionAdapter, FunctionDescriptor functionDescriptor, Type type, Type[] typeArr, KotlinTypeMapper kotlinTypeMapper) {
        Type typeBoxType;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(143);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(144);
        }
        if (type == null) {
            $$$reportNull$$$0(145);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(146);
        }
        SpecialGenericSignatures.TypeSafeBarrierDescription defaultValueForOverriddenBuiltinFunction = BuiltinMethodsWithSpecialGenericSignature.getDefaultValueForOverriddenBuiltinFunction(functionDescriptor);
        if (defaultValueForOverriddenBuiltinFunction == null) {
            return;
        }
        BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(functionDescriptor);
        Label label = new Label();
        for (int i = 0; i < functionDescriptor.getValueParameters().size(); i++) {
            if (defaultValueForOverriddenBuiltinFunction.checkParameter(i)) {
                boolean z = typeArr == null || AsmTypes.OBJECT_TYPE.equals(typeArr[i]);
                KotlinType type2 = functionDescriptor.getValueParameters().get(i).getType();
                if (!z || !TypeUtils.isNullableType(type2)) {
                    instructionAdapter.load(i + 1, AsmTypes.OBJECT_TYPE);
                    if (z) {
                        instructionAdapter.ifnull(label);
                    } else {
                        if (InlineClassesUtilsKt.isInlineClassType(type2)) {
                            typeBoxType = kotlinTypeMapper.mapTypeAsDeclaration(type2);
                        } else {
                            typeBoxType = AsmUtil.boxType(typeArr[i]);
                        }
                        CodegenUtilKt.generateIsCheck(instructionAdapter, type2, typeBoxType);
                        instructionAdapter.ifeq(label);
                    }
                }
            }
        }
        Label label2 = new Label();
        instructionAdapter.goTo(label2);
        instructionAdapter.visitLabel(label);
        if (defaultValueForOverriddenBuiltinFunction.equals(SpecialGenericSignatures.TypeSafeBarrierDescription.MAP_GET_OR_DEFAULT)) {
            instructionAdapter.load(2, type);
        } else {
            StackValue.constant(defaultValueForOverriddenBuiltinFunction.getDefaultValue(), type).put(instructionAdapter);
        }
        instructionAdapter.areturn(type);
        instructionAdapter.visitLabel(label2);
    }

    public void genSamDelegate(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, StackValue stackValue) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(147);
        }
        genDelegate(functionDescriptor, functionDescriptor2.getOriginal(), JvmDeclarationOriginKt.SamDelegation(functionDescriptor), (ClassDescriptor) functionDescriptor2.getContainingDeclaration(), stackValue, true);
    }

    public void genDelegate(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, StackValue stackValue) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(148);
        }
        genDelegate(functionDescriptor, functionDescriptor2.getOriginal(), (ClassDescriptor) functionDescriptor2.getContainingDeclaration(), stackValue);
    }

    public void genDelegate(FunctionDescriptor functionDescriptor, FunctionDescriptor functionDescriptor2, ClassDescriptor classDescriptor, StackValue stackValue) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(149);
        }
        genDelegate(functionDescriptor, functionDescriptor2, JvmDeclarationOriginKt.Delegation(DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor2), functionDescriptor), classDescriptor, stackValue, false);
    }

    private void genDelegate(final FunctionDescriptor functionDescriptor, final FunctionDescriptor functionDescriptor2, JvmDeclarationOrigin jvmDeclarationOrigin, final ClassDescriptor classDescriptor, final StackValue stackValue, final boolean z) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(150);
        }
        if (jvmDeclarationOrigin == null) {
            $$$reportNull$$$0(151);
        }
        generateMethod(jvmDeclarationOrigin, functionDescriptor, new FunctionGenerationStrategy() { // from class: org.jetbrains.kotlin.codegen.FunctionCodegen.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i == 1) {
                    objArr[0] = "frameMap";
                } else if (i == 2) {
                    objArr[0] = "signature";
                } else if (i == 3) {
                    objArr[0] = "context";
                } else if (i == 4) {
                    objArr[0] = "parentCodegen";
                } else if (i != 5) {
                    objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                } else {
                    objArr[0] = "functionDescriptor";
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/FunctionCodegen$1";
                if (i != 5) {
                    objArr[2] = "generateBody";
                } else {
                    objArr[2] = "getKotlinTypesForJvmParameters";
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public boolean skipNotNullAssertionsForParameters() {
                return false;
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public void generateBody(MethodVisitor methodVisitor, FrameMap frameMap, JvmMethodSignature jvmMethodSignature, MethodContext methodContext, MemberCodegen<?> memberCodegen) {
                if (methodVisitor == null) {
                    $$$reportNull$$$0(0);
                }
                int size = 1;
                if (frameMap == null) {
                    $$$reportNull$$$0(1);
                }
                if (jvmMethodSignature == null) {
                    $$$reportNull$$$0(2);
                }
                if (methodContext == null) {
                    $$$reportNull$$$0(3);
                }
                if (memberCodegen == null) {
                    $$$reportNull$$$0(4);
                }
                Method methodMapAsmMethod = FunctionCodegen.this.typeMapper.mapAsmMethod(functionDescriptor);
                Type[] argumentTypes = methodMapAsmMethod.getArgumentTypes();
                List<KotlinType> kotlinTypesForJvmParameters = getKotlinTypesForJvmParameters(functionDescriptor);
                Method asmMethod = FunctionCodegen.this.typeMapper.mapToCallableMethod(functionDescriptor2, false).getAsmMethod();
                Type[] argumentTypes2 = asmMethod.getArgumentTypes();
                List<KotlinType> kotlinTypesForJvmParameters2 = getKotlinTypesForJvmParameters(functionDescriptor2);
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
                instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
                stackValue.put(instructionAdapter);
                boolean zIsInlineClass = InlineClassesUtilsKt.isInlineClass(classDescriptor);
                for (int i = 0; i < argumentTypes.length; i++) {
                    Type type = argumentTypes[i];
                    StackValue.local(size, type, kotlinTypesForJvmParameters.get(i)).put(argumentTypes2[i + (zIsInlineClass ? 1 : 0)], kotlinTypesForJvmParameters2.get(i), instructionAdapter);
                    size += type.getSize();
                }
                String internalName = FunctionCodegen.this.typeMapper.mapClass(classDescriptor).getInternalName();
                if (classDescriptor.getKind() == ClassKind.INTERFACE) {
                    instructionAdapter.invokeinterface(internalName, asmMethod.getName(), asmMethod.getDescriptor());
                } else if (InlineClassesUtilsKt.isInlineClass(classDescriptor)) {
                    instructionAdapter.invokestatic(internalName, asmMethod.getName(), asmMethod.getDescriptor(), false);
                } else {
                    instructionAdapter.invokevirtual(internalName, asmMethod.getName(), asmMethod.getDescriptor(), false);
                }
                DescriptorAsmUtil.genNotNullAssertions(FunctionCodegen.this.state, StackValue.onStack(asmMethod.getReturnType(), functionDescriptor2.getReturnType()), RuntimeAssertionInfo.create(functionDescriptor.getReturnType(), functionDescriptor2.getReturnType(), new RuntimeAssertionInfo.DataFlowExtras.OnlyMessage(functionDescriptor2.getShortName() + "(...)"))).put(methodMapAsmMethod.getReturnType(), functionDescriptor.getReturnType(), instructionAdapter);
                instructionAdapter.areturn(methodMapAsmMethod.getReturnType());
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public boolean skipGenericSignature() {
                return z;
            }

            private List<KotlinType> getKotlinTypesForJvmParameters(FunctionDescriptor functionDescriptor3) {
                if (functionDescriptor3 == null) {
                    $$$reportNull$$$0(5);
                }
                ArrayList arrayList = new ArrayList();
                ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor3.getExtensionReceiverParameter();
                if (extensionReceiverParameter != null) {
                    arrayList.add(extensionReceiverParameter.getType());
                }
                Iterator<ValueParameterDescriptor> it = functionDescriptor3.getValueParameters().iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getType());
                }
                if (functionDescriptor3.isSuspend()) {
                    arrayList.add(null);
                }
                return arrayList;
            }
        });
    }

    public static boolean processInterfaceMethod(CallableMemberDescriptor callableMemberDescriptor, OwnerKind ownerKind, boolean z, boolean z2, JvmDefaultMode jvmDefaultMode) {
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(152);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(153);
        }
        DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingDeclaration();
        if (JvmAnnotationUtilKt.checkIsImplementationCompiledToJvmDefault(callableMemberDescriptor, jvmDefaultMode)) {
            boolean zIsCompiledInCompatibilityMode = isCompiledInCompatibilityMode(jvmDefaultMode, callableMemberDescriptor);
            boolean z3 = z2 && (zIsCompiledInCompatibilityMode || jvmDefaultMode == JvmDefaultMode.ENABLE);
            if (ownerKind == OwnerKind.DEFAULT_IMPLS || z3) {
                if (ownerKind != OwnerKind.DEFAULT_IMPLS) {
                    return false;
                }
                if ((!z3 && (!zIsCompiledInCompatibilityMode || JvmAnnotationUtilKt.hasJvmDefaultNoCompatibilityAnnotation(containingDeclaration))) || DescriptorVisibilities.isPrivate(callableMemberDescriptor.getVisibility())) {
                    return false;
                }
            }
            return true;
        }
        int i = AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$codegen$OwnerKind[ownerKind.ordinal()];
        if (i != 1) {
            return (i != 2 || DescriptorVisibilities.isPrivate(callableMemberDescriptor.getVisibility()) || z || z2) ? false : true;
        }
        return true;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.FunctionCodegen$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$codegen$OwnerKind;
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind;

        static {
            int[] iArr = new int[OwnerKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$codegen$OwnerKind = iArr;
            try {
                iArr[OwnerKind.DEFAULT_IMPLS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$codegen$OwnerKind[OwnerKind.IMPLEMENTATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[JvmMethodParameterKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind = iArr2;
            try {
                iArr2[JvmMethodParameterKind.VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind[JvmMethodParameterKind.RECEIVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind[JvmMethodParameterKind.CONTEXT_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind[JvmMethodParameterKind.OUTER.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$resolve$jvm$jvmSignature$JvmMethodParameterKind[JvmMethodParameterKind.ENUM_NAME_OR_ORDINAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public CalculatedClosure getClosure() {
        return this.owner.closure;
    }
}
