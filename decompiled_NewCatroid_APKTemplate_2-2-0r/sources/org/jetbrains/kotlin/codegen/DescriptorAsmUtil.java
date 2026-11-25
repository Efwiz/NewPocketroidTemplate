package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.badlogic.gdx.graphics.GL20;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.intrinsics.HashCode;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.resolve.jvm.RuntimeAssertionInfo;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.synthetic.SyntheticJavaPropertyDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class DescriptorAsmUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Code duplicated, block: B:13:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 18 && i != 19 && i != 42 && i != 43 && i != 48 && i != 50 && i != 63 && i != 64) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 18 && i != 19 && i != 42 && i != 43 && i != 48 && i != 50 && i != 63 && i != 64) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
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
            case 4:
            case 7:
                objArr[0] = "bindingContext";
                break;
            case 2:
            case 5:
            case 8:
            case 33:
                objArr[0] = "languageVersionSettings";
                break;
            case 3:
            case 6:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 30:
            case 53:
            default:
                objArr[0] = "descriptor";
                break;
            case 9:
                objArr[0] = "prefix";
                break;
            case 10:
                objArr[0] = "defaultName";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 42:
            case 43:
            case 48:
            case 50:
            case 63:
            case 64:
                objArr[0] = "org/jetbrains/kotlin/codegen/DescriptorAsmUtil";
                break;
            case 15:
            case 35:
            case 77:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 16:
                objArr[0] = "kotlinType";
                break;
            case 17:
            case 78:
                objArr[0] = "typeMapper";
                break;
            case 20:
                objArr[0] = "functionDescriptor";
                break;
            case 21:
            case 52:
            case 56:
            case 61:
                objArr[0] = "state";
                break;
            case 22:
                objArr[0] = "deprecationResolver";
                break;
            case 28:
            case 29:
                objArr[0] = "innerClass";
                break;
            case 31:
                objArr[0] = "memberDescriptor";
                break;
            case 32:
                objArr[0] = "closure";
                break;
            case 34:
                objArr[0] = "generator";
                break;
            case 36:
            case 62:
                objArr[0] = "stackValue";
                break;
            case 37:
                objArr[0] = "receiver";
                break;
            case 38:
                objArr[0] = "receiverType";
                break;
            case 39:
            case 47:
            case 49:
                objArr[0] = "opToken";
                break;
            case 40:
            case 44:
                objArr[0] = "left";
                break;
            case 41:
            case 45:
                objArr[0] = "right";
                break;
            case 46:
                objArr[0] = "asmType";
                break;
            case 51:
            case 55:
            case 76:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 54:
            case 57:
                objArr[0] = "frameMap";
                break;
            case 58:
            case 65:
                objArr[0] = "parameter";
                break;
            case 59:
                objArr[0] = "name";
                break;
            case 60:
                objArr[0] = "containingDeclaration";
                break;
            case 66:
            case 67:
            case 68:
            case 69:
                objArr[0] = "propertyDescriptor";
                break;
            case 70:
            case 73:
                objArr[0] = "av";
                break;
            case 71:
                objArr[0] = "serializer";
                break;
            case 72:
            case 74:
                objArr[0] = CrashHianalyticsData.MESSAGE;
                break;
            case 75:
                objArr[0] = "stringTable";
                break;
            case 79:
                objArr[0] = "context";
                break;
        }
        if (i == 18 || i == 19) {
            objArr[1] = "boxType";
        } else if (i == 42 || i == 43) {
            objArr[1] = "genEqualsForExpressionsOnStack";
        } else if (i == 48) {
            objArr[1] = "genEqualsBoxedOnStack";
        } else if (i == 50) {
            objArr[1] = "genAreEqualCall";
        } else if (i != 63 && i != 64) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    objArr[1] = "getLabeledThisNameForReceiver";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/codegen/DescriptorAsmUtil";
                    break;
            }
        } else {
            objArr[1] = "genNotNullAssertions";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
                objArr[2] = "getNameForReceiverParameter";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[2] = "getLabeledThisNameForReceiver";
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 18:
            case 19:
            case 42:
            case 43:
            case 48:
            case 50:
            case 63:
            case 64:
                break;
            case 15:
            case 16:
            case 17:
                objArr[2] = "boxType";
                break;
            case 20:
                objArr[2] = "isInlineClassWrapperConstructor";
                break;
            case 21:
            case 22:
                objArr[2] = "getCommonCallableFlags";
                break;
            case 23:
            case 24:
                objArr[2] = "getVisibilityAccessFlag";
                break;
            case 25:
                objArr[2] = "getVisibilityAccessFlagForClass";
                break;
            case 26:
                objArr[2] = "getVisibilityAccessFlagForAnonymous";
                break;
            case 27:
                objArr[2] = "getSyntheticAccessFlagForLambdaClass";
                break;
            case 28:
                objArr[2] = "calculateInnerClassAccessFlags";
                break;
            case 29:
                objArr[2] = "innerAccessFlagsForModalityAndKind";
                break;
            case 30:
                objArr[2] = "getDeprecatedAccessFlag";
                break;
            case 31:
                objArr[2] = "specialCaseVisibility";
                break;
            case 32:
            case 33:
                objArr[2] = "genClosureFields";
                break;
            case 34:
            case 35:
            case 36:
                objArr[2] = "genInvokeAppendMethod";
                break;
            case 37:
            case 38:
                objArr[2] = "genToString";
                break;
            case 39:
            case 40:
            case 41:
                objArr[2] = "genEqualsForExpressionsOnStack";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "genTotalOrderEqualsForExpressionOnStack";
                break;
            case 47:
                objArr[2] = "genEqualsBoxedOnStack";
                break;
            case 49:
                objArr[2] = "genAreEqualCall";
                break;
            case 51:
            case 52:
            case 53:
            case 54:
                objArr[2] = "genNotNullAssertionsForParameters";
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
                objArr[2] = "genParamAssertion";
                break;
            case 61:
            case 62:
                objArr[2] = "genNotNullAssertions";
                break;
            case 65:
                objArr[2] = "getIndexOfParameterInVarargInvokeArray";
                break;
            case 66:
                objArr[2] = "isInstancePropertyWithStaticBackingField";
                break;
            case 67:
                objArr[2] = "getVisibilityForBackingField";
                break;
            case 68:
                objArr[2] = "descriptorForVisibility";
                break;
            case 69:
                objArr[2] = "isPropertyWithBackingFieldCopyInOuterClass";
                break;
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
                objArr[2] = "writeAnnotationData";
                break;
            case 76:
            case 77:
            case 78:
                objArr[2] = "putJavaLangClassInstance";
                break;
            case 79:
            case 80:
                objArr[2] = "getReceiverIndex";
                break;
            default:
                objArr[2] = "getNameForCapturedReceiverField";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 18 && i != 19 && i != 42 && i != 43 && i != 48 && i != 50 && i != 63 && i != 64) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    private DescriptorAsmUtil() {
    }

    public static String getNameForCapturedReceiverField(CallableDescriptor callableDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(1);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(2);
        }
        return getLabeledThisNameForReceiver(callableDescriptor, bindingContext, languageVersionSettings, "this_", AsmUtil.CAPTURED_RECEIVER_FIELD);
    }

    public static String getNameForReceiverParameter(CallableDescriptor callableDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(3);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(4);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(5);
        }
        return getLabeledThisNameForReceiver(callableDescriptor, bindingContext, languageVersionSettings, AsmUtil.LABELED_THIS_PARAMETER, AsmUtil.RECEIVER_PARAMETER_NAME);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x005e  */
    private static String getLabeledThisNameForReceiver(CallableDescriptor callableDescriptor, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, String str, String str2) {
        Name name2;
        if (callableDescriptor == null) {
            $$$reportNull$$$0(6);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(7);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(8);
        }
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        if (!languageVersionSettings.supportsFeature(LanguageFeature.NewCapturedReceiverFieldNamingConvention)) {
            if (str2 == null) {
                $$$reportNull$$$0(11);
            }
            return str2;
        }
        if (callableDescriptor instanceof FunctionDescriptor) {
            String str3 = (String) bindingContext.get(CodegenBinding.CALL_LABEL_FOR_LAMBDA_ARGUMENT, (FunctionDescriptor) callableDescriptor);
            if (str3 != null) {
                String labeledThisName = AsmUtil.getLabeledThisName(str3, str, str2);
                if (labeledThisName == null) {
                    $$$reportNull$$$0(12);
                }
                return labeledThisName;
            }
            if (callableDescriptor instanceof VariableAccessorDescriptor) {
                name2 = ((VariableAccessorDescriptor) callableDescriptor).getCorrespondingVariable().getShortName();
            } else {
                name2 = null;
            }
        } else {
            name2 = null;
        }
        if (name2 == null) {
            name2 = callableDescriptor.getShortName();
        }
        if (name2.isSpecial()) {
            if (str2 == null) {
                $$$reportNull$$$0(13);
            }
            return str2;
        }
        String labeledThisName2 = AsmUtil.getLabeledThisName(name2.asString(), str, str2);
        if (labeledThisName2 == null) {
            $$$reportNull$$$0(14);
        }
        return labeledThisName2;
    }

    public static Type boxType(Type type, KotlinType kotlinType, KotlinTypeMapper kotlinTypeMapper) {
        if (type == null) {
            $$$reportNull$$$0(15);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(16);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(17);
        }
        if (InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            Type typeMapTypeAsDeclaration = kotlinTypeMapper.mapTypeAsDeclaration(kotlinType);
            if (typeMapTypeAsDeclaration == null) {
                $$$reportNull$$$0(18);
            }
            return typeMapTypeAsDeclaration;
        }
        Type typeBoxPrimitiveType = AsmUtil.boxPrimitiveType(type);
        if (typeBoxPrimitiveType != null) {
            type = typeBoxPrimitiveType;
        }
        if (type == null) {
            $$$reportNull$$$0(19);
        }
        return type;
    }

    public static boolean isAbstractMethod(FunctionDescriptor functionDescriptor, OwnerKind ownerKind, JvmDefaultMode jvmDefaultMode) {
        return (functionDescriptor.getModality() == Modality.ABSTRACT || (JvmCodegenUtil.isJvmInterface(functionDescriptor.getContainingDeclaration()) && !JvmAnnotationUtilKt.isCompiledToJvmDefault(functionDescriptor, jvmDefaultMode))) && !isStaticMethod(ownerKind, functionDescriptor);
    }

    public static boolean isStaticMethod(OwnerKind ownerKind, CallableMemberDescriptor callableMemberDescriptor) {
        return isStaticKind(ownerKind) || KotlinTypeMapper.isStaticAccessor(callableMemberDescriptor) || CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(callableMemberDescriptor);
    }

    public static boolean isStaticKind(OwnerKind ownerKind) {
        return ownerKind == OwnerKind.PACKAGE || ownerKind == OwnerKind.DEFAULT_IMPLS || ownerKind == OwnerKind.ERASED_INLINE_CLASS;
    }

    public static int getMethodAsmFlags(FunctionDescriptor functionDescriptor, OwnerKind ownerKind, GenerationState generationState) {
        return getMethodAsmFlags(functionDescriptor, ownerKind, generationState.getDeprecationProvider(), generationState.getJvmDefaultMode());
    }

    public static int getMethodAsmFlags(FunctionDescriptor functionDescriptor, OwnerKind ownerKind, DeprecationResolver deprecationResolver, JvmDefaultMode jvmDefaultMode) {
        int commonCallableFlags = getCommonCallableFlags(functionDescriptor, ownerKind, deprecationResolver);
        Iterator<AnnotationCodegen.JvmFlagAnnotation> it = AnnotationCodegen.METHOD_FLAGS.iterator();
        while (it.hasNext()) {
            commonCallableFlags |= it.next().getJvmFlag(functionDescriptor.getOriginal());
        }
        if (functionDescriptor.getOriginal().isExternal()) {
            commonCallableFlags |= 256;
        }
        if (CodegenUtilKt.isJvmStaticInCompanionObject(functionDescriptor)) {
            commonCallableFlags &= -257;
        }
        if (functionDescriptor.getModality() == Modality.FINAL && !(functionDescriptor instanceof ConstructorDescriptor) && !JvmCodegenUtil.isJvmInterface(functionDescriptor.getContainingDeclaration())) {
            commonCallableFlags |= 16;
        }
        if (isStaticMethod(ownerKind, functionDescriptor)) {
            commonCallableFlags |= 8;
        }
        if (isAbstractMethod(functionDescriptor, ownerKind, jvmDefaultMode)) {
            commonCallableFlags |= 1024;
        }
        return (KotlinTypeMapper.isAccessor(functionDescriptor) || JvmAnnotationUtilKt.hasJvmSyntheticAnnotation(functionDescriptor) || isInlineClassWrapperConstructor(functionDescriptor, ownerKind) || InlineClassDescriptorResolver.isSynthesizedBoxMethod(functionDescriptor) || InlineClassDescriptorResolver.isSynthesizedUnboxMethod(functionDescriptor)) ? commonCallableFlags | 4096 : commonCallableFlags;
    }

    private static boolean isInlineClassWrapperConstructor(FunctionDescriptor functionDescriptor, OwnerKind ownerKind) {
        if (functionDescriptor == null) {
            $$$reportNull$$$0(20);
        }
        return (functionDescriptor instanceof ConstructorDescriptor) && InlineClassesUtilsKt.isInlineClass(((ConstructorDescriptor) functionDescriptor).getConstructedClass()) && ownerKind == OwnerKind.IMPLEMENTATION;
    }

    public static int getCommonCallableFlags(FunctionDescriptor functionDescriptor, GenerationState generationState) {
        if (generationState == null) {
            $$$reportNull$$$0(21);
        }
        return getCommonCallableFlags(functionDescriptor, null, generationState.getDeprecationProvider());
    }

    private static int getCommonCallableFlags(FunctionDescriptor functionDescriptor, OwnerKind ownerKind, DeprecationResolver deprecationResolver) {
        if (deprecationResolver == null) {
            $$$reportNull$$$0(22);
        }
        int visibilityAccessFlag = getVisibilityAccessFlag(functionDescriptor, ownerKind) | getVarargsFlag(functionDescriptor) | getDeprecatedAccessFlag(functionDescriptor);
        return (deprecationResolver.isDeprecatedHidden(functionDescriptor) || InlineOnlyKt.isInlineWithReified(functionDescriptor)) ? visibilityAccessFlag | 4096 : visibilityAccessFlag;
    }

    public static int getVisibilityAccessFlag(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(23);
        }
        return getVisibilityAccessFlag(memberDescriptor, null);
    }

    private static int getVisibilityAccessFlag(MemberDescriptor memberDescriptor, OwnerKind ownerKind) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        Integer numSpecialCaseVisibility = specialCaseVisibility(memberDescriptor, ownerKind);
        if (numSpecialCaseVisibility != null) {
            return numSpecialCaseVisibility.intValue();
        }
        DescriptorVisibility visibility = memberDescriptor.getVisibility();
        Integer visibilityAccessFlag = getVisibilityAccessFlag(visibility);
        if (visibilityAccessFlag == null) {
            throw new IllegalStateException(visibility + " is not a valid visibility in backend for " + DescriptorRenderer.DEBUG_TEXT.render(memberDescriptor));
        }
        return visibilityAccessFlag.intValue();
    }

    public static Integer getVisibilityAccessFlag(DescriptorVisibility descriptorVisibility) {
        return AsmUtil.getVisibilityAccessFlag(descriptorVisibility.getDelegate());
    }

    public static int getVisibilityAccessFlagForClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(25);
        }
        if (classDescriptor instanceof SyntheticClassDescriptorForLambda) {
            return getVisibilityAccessFlagForAnonymous(classDescriptor);
        }
        if (classDescriptor.getKind() == ClassKind.ENUM_ENTRY) {
            return 0;
        }
        return (classDescriptor.getVisibility() == DescriptorVisibilities.PUBLIC || classDescriptor.getVisibility() == DescriptorVisibilities.PROTECTED || classDescriptor.getVisibility() == DescriptorVisibilities.LOCAL || classDescriptor.getVisibility() == DescriptorVisibilities.INTERNAL) ? 1 : 0;
    }

    private static int getVisibilityAccessFlagForAnonymous(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        return InlineUtil.isInlineOrContainingInline(classDescriptor.getContainingDeclaration()) ? 1 : 0;
    }

    public static int getSyntheticAccessFlagForLambdaClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(27);
        }
        return ((classDescriptor instanceof SyntheticClassDescriptorForLambda) && ((SyntheticClassDescriptorForLambda) classDescriptor).isCallableReference()) ? 4096 : 0;
    }

    public static int calculateInnerClassAccessFlags(ClassDescriptor classDescriptor) {
        int visibilityAccessFlag;
        if (classDescriptor == null) {
            $$$reportNull$$$0(28);
        }
        if (classDescriptor instanceof SyntheticClassDescriptorForLambda) {
            visibilityAccessFlag = getVisibilityAccessFlagForAnonymous(classDescriptor);
        } else {
            visibilityAccessFlag = classDescriptor.getVisibility() == DescriptorVisibilities.LOCAL ? 1 : getVisibilityAccessFlag(classDescriptor);
        }
        return (classDescriptor.getIsInner() ? 0 : 8) | visibilityAccessFlag | getSyntheticAccessFlagForLambdaClass(classDescriptor) | innerAccessFlagsForModalityAndKind(classDescriptor);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$descriptors$ClassKind;

        static {
            int[] iArr = new int[ClassKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$descriptors$ClassKind = iArr;
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$ClassKind[ClassKind.ENUM_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$descriptors$ClassKind[ClassKind.ANNOTATION_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static int innerAccessFlagsForModalityAndKind(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(29);
        }
        int i = AnonymousClass3.$SwitchMap$org$jetbrains$kotlin$descriptors$ClassKind[classDescriptor.getKind().ordinal()];
        if (i == 1) {
            return 1536;
        }
        if (i == 2) {
            return 16400;
        }
        if (i == 3) {
            return GL20.GL_NEAREST;
        }
        Modality modality = classDescriptor.getModality();
        if (modality == Modality.FINAL) {
            return 16;
        }
        return (modality == Modality.ABSTRACT || modality == Modality.SEALED) ? 1024 : 0;
    }

    public static int getDeprecatedAccessFlag(MemberDescriptor memberDescriptor) {
        if (memberDescriptor == null) {
            $$$reportNull$$$0(30);
        }
        if (!(memberDescriptor instanceof PropertyAccessorDescriptor)) {
            return KotlinBuiltIns.isDeprecated(memberDescriptor) ? 131072 : 0;
        }
        if (KotlinBuiltIns.isDeprecated(memberDescriptor)) {
            return 131072;
        }
        return getDeprecatedAccessFlag(((PropertyAccessorDescriptor) memberDescriptor).getCorrespondingProperty());
    }

    private static int getVarargsFlag(FunctionDescriptor functionDescriptor) {
        return (functionDescriptor.getValueParameters().isEmpty() || functionDescriptor.getValueParameters().get(functionDescriptor.getValueParameters().size() + (-1)).getVarargElementType() == null) ? 0 : 128;
    }

    private static Integer specialCaseVisibility(MemberDescriptor memberDescriptor, OwnerKind ownerKind) {
        FunctionDescriptor setMethod;
        if (memberDescriptor == null) {
            $$$reportNull$$$0(31);
        }
        DeclarationDescriptor containingDeclaration = memberDescriptor.getContainingDeclaration();
        DescriptorVisibility visibility = memberDescriptor.getVisibility();
        if (JvmCodegenUtil.isNonIntrinsicPrivateCompanionObjectInInterface(memberDescriptor)) {
            return 1;
        }
        if ((memberDescriptor instanceof FunctionDescriptor) && isInlineClassWrapperConstructor((FunctionDescriptor) memberDescriptor, ownerKind)) {
            return 2;
        }
        if (ownerKind != OwnerKind.ERASED_INLINE_CLASS && (memberDescriptor instanceof ConstructorDescriptor) && !(memberDescriptor instanceof AccessorForConstructorDescriptor) && InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters((ConstructorDescriptor) memberDescriptor)) {
            return 2;
        }
        boolean z = memberDescriptor instanceof ConstructorDescriptor;
        if ((!z || (memberDescriptor instanceof AccessorForConstructorDescriptor) || !DescriptorUtils.isSealedClass(((ConstructorDescriptor) memberDescriptor).getConstructedClass()) || memberDescriptor.getVisibility() == DescriptorVisibilities.PUBLIC) && !InlineOnlyKt.isInlineOnlyPrivateInBytecode(memberDescriptor)) {
            if (visibility == DescriptorVisibilities.LOCAL && (memberDescriptor instanceof CallableMemberDescriptor)) {
                return 1;
            }
            if (DescriptorUtils.isEnumEntry(memberDescriptor)) {
                return 0;
            }
            if (CodegenUtilKt.isToArrayFromCollection(memberDescriptor)) {
                return 1;
            }
            if (z && DescriptorUtils.isAnonymousObject(memberDescriptor.getContainingDeclaration())) {
                return Integer.valueOf(getVisibilityAccessFlagForAnonymous((ClassDescriptor) memberDescriptor.getContainingDeclaration()));
            }
            if (memberDescriptor instanceof SyntheticJavaPropertyDescriptor) {
                return Integer.valueOf(getVisibilityAccessFlag(((SyntheticJavaPropertyDescriptor) memberDescriptor).getGetMethod()));
            }
            if (memberDescriptor instanceof PropertyAccessorDescriptor) {
                PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) memberDescriptor).getCorrespondingProperty();
                if (correspondingProperty instanceof SyntheticJavaPropertyDescriptor) {
                    if (memberDescriptor == correspondingProperty.getGetter()) {
                        setMethod = ((SyntheticJavaPropertyDescriptor) correspondingProperty).getGetMethod();
                    } else {
                        setMethod = ((SyntheticJavaPropertyDescriptor) correspondingProperty).getSetMethod();
                    }
                    return Integer.valueOf(getVisibilityAccessFlag(setMethod));
                }
            }
            if ((memberDescriptor instanceof CallableDescriptor) && visibility == DescriptorVisibilities.PROTECTED) {
                Iterator it = DescriptorUtils.getAllOverriddenDescriptors((CallableDescriptor) memberDescriptor).iterator();
                while (it.hasNext()) {
                    if (JvmCodegenUtil.isJvmInterface(((CallableDescriptor) it.next()).getContainingDeclaration())) {
                        return 1;
                    }
                }
            }
            if (!DescriptorVisibilities.isPrivate(visibility)) {
                return null;
            }
            if (memberDescriptor instanceof AccessorForCompanionObjectInstanceFieldDescriptor) {
                return 0;
            }
            return (z && DescriptorUtils.isEnumEntry(containingDeclaration)) ? 0 : null;
        }
        return 2;
    }

    public static void genClosureFields(CalculatedClosure calculatedClosure, ClassBuilder classBuilder, KotlinTypeMapper kotlinTypeMapper, LanguageVersionSettings languageVersionSettings) {
        if (calculatedClosure == null) {
            $$$reportNull$$$0(32);
        }
        if (languageVersionSettings == null) {
            $$$reportNull$$$0(33);
        }
        ArrayList arrayList = new ArrayList();
        ClassDescriptor capturedOuterClassDescriptor = calculatedClosure.getCapturedOuterClassDescriptor();
        if (capturedOuterClassDescriptor != null) {
            arrayList.add(Pair.create(AsmUtil.CAPTURED_THIS_FIELD, kotlinTypeMapper.mapType(capturedOuterClassDescriptor)));
        }
        KotlinType capturedReceiverFromOuterContext = calculatedClosure.getCapturedReceiverFromOuterContext();
        if (capturedReceiverFromOuterContext != null && !CallableReferenceUtilKt.isForCallableReference(calculatedClosure)) {
            arrayList.add(Pair.create(calculatedClosure.getCapturedReceiverFieldName(kotlinTypeMapper.getBindingContext(), languageVersionSettings), kotlinTypeMapper.mapType(capturedReceiverFromOuterContext)));
        }
        arrayList.addAll(calculatedClosure.getRecordedFields());
        genClosureFields(arrayList, classBuilder);
    }

    public static void genClosureFields(List<Pair<String, Type>> list, ClassBuilder classBuilder) {
        for (Pair<String, Type> pair : list) {
            classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 4112, pair.first, pair.second.getDescriptor(), null, null);
        }
    }

    public static int genAssignInstanceFieldFromParam(FieldInfo fieldInfo, int i, InstructionAdapter instructionAdapter) {
        return genAssignInstanceFieldFromParam(fieldInfo, i, instructionAdapter, 0, false);
    }

    public static int genAssignInstanceFieldFromParam(FieldInfo fieldInfo, int i, InstructionAdapter instructionAdapter, int i2, boolean z) {
        Type fieldType = fieldInfo.getFieldType();
        KotlinType fieldKotlinType = fieldInfo.getFieldKotlinType();
        SimpleType nullableAnyType = fieldKotlinType != null ? fieldKotlinType.getConstructor().getBuiltIns().getNullableAnyType() : null;
        instructionAdapter.load(i2, fieldInfo.getOwnerType());
        if (z) {
            instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
            StackValue.coerce(AsmTypes.OBJECT_TYPE, nullableAnyType, fieldType, fieldKotlinType, instructionAdapter);
        } else {
            instructionAdapter.load(i, fieldType);
        }
        instructionAdapter.visitFieldInsn(181, fieldInfo.getOwnerInternalName(), fieldInfo.getFieldName(), fieldType.getDescriptor());
        return i + fieldType.getSize();
    }

    public static void genInvokeAppendMethod(StringConcatGenerator stringConcatGenerator, Type type, KotlinType kotlinType, KotlinTypeMapper kotlinTypeMapper, StackValue stackValue) {
        if (stringConcatGenerator == null) {
            $$$reportNull$$$0(34);
        }
        if (type == null) {
            $$$reportNull$$$0(35);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(36);
        }
        CallableMethod specializedToStringCallableMethodOrNull = getSpecializedToStringCallableMethodOrNull(kotlinType, kotlinTypeMapper);
        if (specializedToStringCallableMethodOrNull != null) {
            stackValue.put(type, kotlinType, stringConcatGenerator.getMv());
            specializedToStringCallableMethodOrNull.genInvokeInstruction(stringConcatGenerator.getMv());
            stringConcatGenerator.invokeAppend(AsmTypes.JAVA_STRING_TYPE);
        } else {
            if (kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
                SimpleType nullableAnyType = kotlinType.getConstructor().getBuiltIns().getNullableAnyType();
                stackValue.put(type, kotlinType, stringConcatGenerator.getMv());
                StackValue.coerce(type, kotlinType, AsmTypes.OBJECT_TYPE, nullableAnyType, stringConcatGenerator.getMv());
                stringConcatGenerator.invokeAppend(AsmTypes.OBJECT_TYPE);
                return;
            }
            stringConcatGenerator.putValueOrProcessConstant(stackValue, type, kotlinType);
        }
    }

    public static StackValue genToString(final StackValue stackValue, final Type type, final KotlinType kotlinType, final KotlinTypeMapper kotlinTypeMapper) {
        if (stackValue == null) {
            $$$reportNull$$$0(37);
        }
        if (type == null) {
            $$$reportNull$$$0(38);
        }
        return StackValue.operation(AsmTypes.JAVA_STRING_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DescriptorAsmUtil.lambda$genToString$0(kotlinType, kotlinTypeMapper, stackValue, type, (InstructionAdapter) obj);
            }
        });
    }

    static /* synthetic */ Unit lambda$genToString$0(KotlinType kotlinType, KotlinTypeMapper kotlinTypeMapper, StackValue stackValue, Type type, InstructionAdapter instructionAdapter) {
        Type typeStringValueOfType;
        SimpleType nullableAnyType;
        CallableMethod specializedToStringCallableMethodOrNull = getSpecializedToStringCallableMethodOrNull(kotlinType, kotlinTypeMapper);
        if (specializedToStringCallableMethodOrNull != null) {
            stackValue.put(type, kotlinType, instructionAdapter);
            specializedToStringCallableMethodOrNull.genInvokeInstruction(instructionAdapter);
            return null;
        }
        if (kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            typeStringValueOfType = AsmTypes.OBJECT_TYPE;
            nullableAnyType = kotlinType.getConstructor().getBuiltIns().getNullableAnyType();
        } else {
            typeStringValueOfType = AsmUtil.stringValueOfType(type);
            nullableAnyType = null;
        }
        stackValue.put(typeStringValueOfType, nullableAnyType, instructionAdapter);
        instructionAdapter.invokestatic("java/lang/String", "valueOf", "(" + typeStringValueOfType.getDescriptor() + ")Ljava/lang/String;", false);
        return null;
    }

    private static CallableMethod getSpecializedToStringCallableMethodOrNull(KotlinType kotlinType, KotlinTypeMapper kotlinTypeMapper) {
        if (kotlinTypeMapper == null || kotlinType == null || !InlineClassesUtilsKt.isInlineClassType(kotlinType) || kotlinType.getIsMarkedNullable()) {
            return null;
        }
        final ClassDescriptor classDescriptor = (ClassDescriptor) kotlinType.getConstructor().getThis$0();
        return kotlinTypeMapper.mapToCallableMethod(classDescriptor.getUnsubstitutedMemberScope().getContributedFunctions(Name.identifier("toString"), NoLookupLocation.FROM_BACKEND).stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DescriptorAsmUtil.lambda$getSpecializedToStringCallableMethodOrNull$1((SimpleFunctionDescriptor) obj);
            }
        }).findFirst().orElseThrow(new Supplier() { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return DescriptorAsmUtil.lambda$getSpecializedToStringCallableMethodOrNull$2(classDescriptor);
            }
        }), false, OwnerKind.ERASED_INLINE_CLASS);
    }

    static /* synthetic */ boolean lambda$getSpecializedToStringCallableMethodOrNull$1(SimpleFunctionDescriptor simpleFunctionDescriptor) {
        return simpleFunctionDescriptor.getValueParameters().size() == 0 && KotlinBuiltIns.isString(simpleFunctionDescriptor.getReturnType()) && simpleFunctionDescriptor.mo6753getDispatchReceiverParameter() != null && simpleFunctionDescriptor.getExtensionReceiverParameter() == null;
    }

    static /* synthetic */ AssertionError lambda$getSpecializedToStringCallableMethodOrNull$2(ClassDescriptor classDescriptor) {
        return new AssertionError("'toString' not found in member scope of " + classDescriptor);
    }

    public static void genHashCode(MethodVisitor methodVisitor, InstructionAdapter instructionAdapter, Type type, JvmTarget jvmTarget) {
        if (type.getSort() == 9) {
            Type typeCorrectElementType = AsmUtil.correctElementType(type);
            if (typeCorrectElementType.getSort() == 10 || typeCorrectElementType.getSort() == 9) {
                instructionAdapter.invokestatic("java/util/Arrays", "hashCode", "([Ljava/lang/Object;)I", false);
                return;
            } else {
                instructionAdapter.invokestatic("java/util/Arrays", "hashCode", "(" + type.getDescriptor() + ")I", false);
                return;
            }
        }
        if (type.getSort() == 10) {
            instructionAdapter.invokevirtual(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "hashCode", "()I", false);
        } else {
            HashCode.INSTANCE.invokeHashCode(instructionAdapter, type);
        }
    }

    public static StackValue genEqualsForExpressionsOnStack(final IElementType iElementType, final StackValue stackValue, final StackValue stackValue2) {
        if (iElementType == null) {
            $$$reportNull$$$0(39);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(40);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(41);
        }
        Type type = stackValue.type;
        Type type2 = stackValue2.type;
        if (AsmUtil.isPrimitive(type) && type == type2) {
            StackValue stackValueCmp = StackValue.cmp(iElementType, type, stackValue, stackValue2);
            if (stackValueCmp == null) {
                $$$reportNull$$$0(42);
            }
            return stackValueCmp;
        }
        StackValue stackValueOperation = StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DescriptorAsmUtil.lambda$genEqualsForExpressionsOnStack$3(stackValue, stackValue2, iElementType, (InstructionAdapter) obj);
            }
        });
        if (stackValueOperation == null) {
            $$$reportNull$$$0(43);
        }
        return stackValueOperation;
    }

    static /* synthetic */ Unit lambda$genEqualsForExpressionsOnStack$3(StackValue stackValue, StackValue stackValue2, IElementType iElementType, InstructionAdapter instructionAdapter) {
        stackValue.put(AsmTypes.OBJECT_TYPE, stackValue.kotlinType, instructionAdapter);
        stackValue2.put(AsmTypes.OBJECT_TYPE, stackValue2.kotlinType, instructionAdapter);
        return genAreEqualCall(instructionAdapter, iElementType);
    }

    public static BranchedValue genTotalOrderEqualsForExpressionOnStack(final StackValue stackValue, final StackValue stackValue2, final Type type) {
        if (stackValue == null) {
            $$$reportNull$$$0(44);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(45);
        }
        if (type == null) {
            $$$reportNull$$$0(46);
        }
        return new BranchedValue(stackValue, stackValue2, type, 153) { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                if (i != 1) {
                    objArr[0] = "jumpLabel";
                } else {
                    objArr[0] = "iv";
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/DescriptorAsmUtil$1";
                objArr[2] = "condJump";
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.BranchedValue
            public void condJump(Label label, InstructionAdapter instructionAdapter, boolean z) {
                if (label == null) {
                    $$$reportNull$$$0(0);
                }
                if (instructionAdapter == null) {
                    $$$reportNull$$$0(1);
                }
                if (type.getSort() == 6) {
                    stackValue.put(type, this.kotlinType, instructionAdapter);
                    stackValue2.put(type, this.kotlinType, instructionAdapter);
                    instructionAdapter.invokestatic("java/lang/Float", "compare", "(FF)I", false);
                    instructionAdapter.visitJumpInsn(patchOpcode(z ? 154 : 153, instructionAdapter), label);
                    return;
                }
                if (type.getSort() == 8) {
                    stackValue.put(type, this.kotlinType, instructionAdapter);
                    stackValue2.put(type, this.kotlinType, instructionAdapter);
                    instructionAdapter.invokestatic("java/lang/Double", "compare", "(DD)I", false);
                    instructionAdapter.visitJumpInsn(patchOpcode(z ? 154 : 153, instructionAdapter), label);
                    return;
                }
                BranchedValue.INSTANCE.condJump(DescriptorAsmUtil.genEqualsForExpressionsOnStack(KtTokens.EQEQ, stackValue, stackValue2), label, z, instructionAdapter);
            }
        };
    }

    public static StackValue genEqualsBoxedOnStack(final IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(47);
        }
        StackValue stackValueOperation = StackValue.operation(Type.BOOLEAN_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DescriptorAsmUtil.genAreEqualCall((InstructionAdapter) obj, iElementType);
            }
        });
        if (stackValueOperation == null) {
            $$$reportNull$$$0(48);
        }
        return stackValueOperation;
    }

    public static void genAreEqualCall(InstructionAdapter instructionAdapter) {
        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "areEqual", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unit genAreEqualCall(InstructionAdapter instructionAdapter, IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(49);
        }
        genAreEqualCall(instructionAdapter);
        if (iElementType == KtTokens.EXCLEQ || iElementType == KtTokens.EXCLEQEQEQ) {
            AsmUtil.genInvertBoolean(instructionAdapter);
        }
        Unit unit = Unit.INSTANCE;
        if (unit == null) {
            $$$reportNull$$$0(50);
        }
        return unit;
    }

    public static void genIEEE754EqualForNullableTypesCall(InstructionAdapter instructionAdapter, Type type, Type type2) {
        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "areEqual", "(" + type.getDescriptor() + type2.getDescriptor() + ")Z", false);
    }

    public static void genIncrement(Type type, int i, InstructionAdapter instructionAdapter) {
        Type typeNumberFunctionOperandType = AsmUtil.numberFunctionOperandType(type);
        AsmUtil.numConst(i, typeNumberFunctionOperandType, instructionAdapter);
        instructionAdapter.add(typeNumberFunctionOperandType);
        StackValue.coerce(typeNumberFunctionOperandType, type, instructionAdapter);
    }

    static void genNotNullAssertionsForParameters(InstructionAdapter instructionAdapter, GenerationState generationState, FunctionDescriptor functionDescriptor, FrameMap frameMap) {
        ReceiverParameterDescriptor extensionReceiverParameter;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(51);
        }
        if (generationState == null) {
            $$$reportNull$$$0(52);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(53);
        }
        if (frameMap == null) {
            $$$reportNull$$$0(54);
        }
        if (generationState.getConfig().getIsParamAssertionsDisabled() || functionDescriptor.isSuspend()) {
            return;
        }
        if (getVisibilityAccessFlag(functionDescriptor) == 2) {
            if (generationState.getConfig().getIsReceiverAssertionsDisabled() || !functionDescriptor.isOperator() || (extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter()) == null) {
                return;
            }
            genParamAssertion(instructionAdapter, generationState, frameMap, extensionReceiverParameter, getNameForReceiverParameter(functionDescriptor, generationState.getBindingContext(), generationState.getLanguageVersionSettings()), functionDescriptor);
            return;
        }
        ReceiverParameterDescriptor extensionReceiverParameter2 = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter2 != null) {
            genParamAssertion(instructionAdapter, generationState, frameMap, extensionReceiverParameter2, getNameForReceiverParameter(functionDescriptor, generationState.getBindingContext(), generationState.getLanguageVersionSettings()), functionDescriptor);
        }
        for (ValueParameterDescriptor valueParameterDescriptor : functionDescriptor.getValueParameters()) {
            genParamAssertion(instructionAdapter, generationState, frameMap, valueParameterDescriptor, valueParameterDescriptor.getShortName().asString(), functionDescriptor);
        }
    }

    private static void genParamAssertion(InstructionAdapter instructionAdapter, GenerationState generationState, FrameMap frameMap, ParameterDescriptor parameterDescriptor, String str, FunctionDescriptor functionDescriptor) {
        StackValue stackValueArrayElement;
        if (instructionAdapter == null) {
            $$$reportNull$$$0(55);
        }
        if (generationState == null) {
            $$$reportNull$$$0(56);
        }
        if (frameMap == null) {
            $$$reportNull$$$0(57);
        }
        if (parameterDescriptor == null) {
            $$$reportNull$$$0(58);
        }
        if (str == null) {
            $$$reportNull$$$0(59);
        }
        if (functionDescriptor == null) {
            $$$reportNull$$$0(60);
        }
        KotlinType type = parameterDescriptor.getType();
        if (TypeUtils.isNullableType(type) || InlineClassesUtilsKt.isNullableUnderlyingType(type)) {
            return;
        }
        Type typeMapType = generationState.getTypeMapper().mapType(type);
        if (typeMapType.getSort() == 10 || typeMapType.getSort() == 9) {
            if (JvmCodegenUtil.isDeclarationOfBigArityFunctionInvoke(functionDescriptor) || JvmCodegenUtil.isDeclarationOfBigArityCreateCoroutineMethod(functionDescriptor)) {
                stackValueArrayElement = StackValue.arrayElement(AsmTypes.OBJECT_TYPE, null, StackValue.local(1, AsmUtil.getArrayType(AsmTypes.OBJECT_TYPE)), StackValue.constant(getIndexOfParameterInVarargInvokeArray(parameterDescriptor)));
            } else {
                stackValueArrayElement = StackValue.local(frameMap.getIndex(parameterDescriptor), typeMapType);
            }
            stackValueArrayElement.put(typeMapType, instructionAdapter);
            instructionAdapter.visitLdcInsn(str);
            instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", generationState.getConfig().getUnifiedNullChecks() ? "checkNotNullParameter" : "checkParameterIsNotNull", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
        }
    }

    public static StackValue genNotNullAssertions(final GenerationState generationState, final StackValue stackValue, final RuntimeAssertionInfo runtimeAssertionInfo) {
        if (generationState == null) {
            $$$reportNull$$$0(61);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(62);
        }
        if (generationState.getConfig().getIsCallAssertionsDisabled()) {
            if (stackValue == null) {
                $$$reportNull$$$0(63);
            }
            return stackValue;
        }
        if (runtimeAssertionInfo != null && runtimeAssertionInfo.getNeedNotNullAssertion()) {
            return new StackValue(stackValue.type, stackValue.kotlinType) { // from class: org.jetbrains.kotlin.codegen.DescriptorAsmUtil.2
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = ModuleXmlParser.TYPE;
                    } else {
                        objArr[0] = RegisterSpec.PREFIX;
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/DescriptorAsmUtil$2";
                    objArr[2] = "putSelector";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.StackValue
                public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
                    if (type == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (instructionAdapter == null) {
                        $$$reportNull$$$0(1);
                    }
                    Type type2 = stackValue.type;
                    KotlinType kotlinType2 = stackValue.kotlinType;
                    stackValue.put(type2, kotlinType2, instructionAdapter);
                    if (type2.getSort() == 10 || type2.getSort() == 9) {
                        instructionAdapter.dup();
                        instructionAdapter.visitLdcInsn(runtimeAssertionInfo.getMessage());
                        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", generationState.getConfig().getUnifiedNullChecks() ? "checkNotNullExpressionValue" : "checkExpressionValueIsNotNull", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
                    }
                    StackValue.coerce(type2, kotlinType2, type, kotlinType, instructionAdapter);
                }
            };
        }
        if (stackValue == null) {
            $$$reportNull$$$0(64);
        }
        return stackValue;
    }

    private static int getIndexOfParameterInVarargInvokeArray(ParameterDescriptor parameterDescriptor) {
        if (parameterDescriptor == null) {
            $$$reportNull$$$0(65);
        }
        if (parameterDescriptor instanceof ReceiverParameterDescriptor) {
            return 0;
        }
        return (((CallableDescriptor) parameterDescriptor.getContainingDeclaration()).getExtensionReceiverParameter() != null ? 1 : 0) + ((ValueParameterDescriptor) parameterDescriptor).getIndex();
    }

    public static void generateVarargInvokeArityAssert(InstructionAdapter instructionAdapter, int i) {
        Label label = new Label();
        instructionAdapter.load(1, AsmUtil.getArrayType(AsmTypes.OBJECT_TYPE));
        instructionAdapter.arraylength();
        instructionAdapter.iconst(i);
        instructionAdapter.ificmpeq(label);
        instructionAdapter.visitLdcInsn("Vararg argument must contain " + i + " elements.");
        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "throwIllegalArgument", "(Ljava/lang/String;)V", false);
        instructionAdapter.visitLabel(label);
    }

    public static boolean isInstancePropertyWithStaticBackingField(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(66);
        }
        return propertyDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE && DescriptorUtils.isObject(propertyDescriptor.getContainingDeclaration());
    }

    public static int getVisibilityForBackingField(PropertyDescriptor propertyDescriptor, boolean z) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(67);
        }
        boolean z2 = propertyDescriptor.getExtensionReceiverParameter() != null;
        if (z || z2) {
            return 2;
        }
        if (propertyDescriptor.isLateInit() || JvmCodegenUtil.isConstOrHasJvmFieldAnnotation(propertyDescriptor)) {
            return getVisibilityAccessFlag(descriptorForVisibility(propertyDescriptor));
        }
        return 2;
    }

    private static MemberDescriptor descriptorForVisibility(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(68);
        }
        return (propertyDescriptor.isVar() && propertyDescriptor.getSetter() != null) ? propertyDescriptor.getSetter() : propertyDescriptor;
    }

    public static boolean isPropertyWithBackingFieldCopyInOuterClass(PropertyDescriptor propertyDescriptor) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(69);
        }
        DeclarationDescriptor containingDeclaration = propertyDescriptor.getContainingDeclaration();
        return propertyDescriptor.isConst() && DescriptorUtils.isCompanionObject(containingDeclaration) && JvmCodegenUtil.isJvmInterface(containingDeclaration.getContainingDeclaration()) && getVisibilityForBackingField(propertyDescriptor, false) == 1;
    }

    public static void writeAnnotationData(AnnotationVisitor annotationVisitor, DescriptorSerializer descriptorSerializer, MessageLite messageLite) {
        if (annotationVisitor == null) {
            $$$reportNull$$$0(70);
        }
        if (descriptorSerializer == null) {
            $$$reportNull$$$0(71);
        }
        if (messageLite == null) {
            $$$reportNull$$$0(72);
        }
        writeAnnotationData(annotationVisitor, messageLite, (JvmStringTable) descriptorSerializer.getStringTable());
    }

    public static void writeAnnotationData(AnnotationVisitor annotationVisitor, MessageLite messageLite, JvmStringTable jvmStringTable) {
        if (annotationVisitor == null) {
            $$$reportNull$$$0(73);
        }
        if (messageLite == null) {
            $$$reportNull$$$0(74);
        }
        if (jvmStringTable == null) {
            $$$reportNull$$$0(75);
        }
        AsmUtil.writeAnnotationData(annotationVisitor, JvmProtoBufUtil.writeData(messageLite, jvmStringTable), ArrayUtil.toStringArray(jvmStringTable.getStrings()));
    }

    public static void putJavaLangClassInstance(InstructionAdapter instructionAdapter, Type type, KotlinType kotlinType, KotlinTypeMapper kotlinTypeMapper) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(76);
        }
        if (type == null) {
            $$$reportNull$$$0(77);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(78);
        }
        if (kotlinType != null && InlineClassesUtilsKt.isInlineClassType(kotlinType)) {
            instructionAdapter.aconst(boxType(type, kotlinType, kotlinTypeMapper));
        } else if (AsmUtil.isPrimitive(type)) {
            instructionAdapter.getstatic(AsmUtil.boxType(type).getInternalName(), "TYPE", "Ljava/lang/Class;");
        } else {
            instructionAdapter.aconst(type);
        }
    }

    public static int getReceiverIndex(CodegenContext codegenContext, CallableMemberDescriptor callableMemberDescriptor) {
        if (codegenContext == null) {
            $$$reportNull$$$0(79);
        }
        if (callableMemberDescriptor == null) {
            $$$reportNull$$$0(80);
        }
        OwnerKind contextKind = codegenContext.getContextKind();
        return (contextKind == OwnerKind.DEFAULT_IMPLS || !isStaticMethod(contextKind, callableMemberDescriptor)) ? 1 : 0;
    }

    public static boolean isHiddenConstructor(FunctionDescriptor functionDescriptor) {
        if (!(functionDescriptor instanceof ClassConstructorDescriptor)) {
            return false;
        }
        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) functionDescriptor;
        if (InlineClassManglingRulesKt.shouldHideConstructorDueToValueClassTypeValueParameters(functionDescriptor)) {
            return true;
        }
        return DescriptorUtils.isSealedClass(classConstructorDescriptor.getConstructedClass()) && classConstructorDescriptor.getVisibility() != DescriptorVisibilities.PUBLIC;
    }
}
