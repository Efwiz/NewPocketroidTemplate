package org.jetbrains.kotlin.js.translate.context;

import com.android.dx.rop.code.RegisterSpec;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsObjectScope;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.backend.ast.metadata.SpecialFunction;
import org.jetbrains.kotlin.js.backend.ast.metadata.TypeCheck;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.naming.NameSuggestion;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.ArrayFIF;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: loaded from: classes5.dex */
public final class Namer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ANOTHER_THIS_PARAMETER_NAME = "$this";
    public static final String CALLEE_NAME = "$fun";
    public static final String CALL_FUNCTION = "call";
    public static final String CLASS_KIND_CLASS = "CLASS";
    public static final String CLASS_KIND_ENUM = "Kind";
    public static final String CLASS_KIND_INTERFACE = "INTERFACE";
    public static final String CLASS_KIND_OBJECT = "OBJECT";
    public static final String COMPARE_TO_METHOD_NAME = "compareTo_11rb$";
    public static final String CREATE_CONTRAVARIANT_KTYPE_PROJECTION = "createContravariantKTypeProjection";
    public static final String CREATE_COVARIANT_KTYPE_PROJECTION = "createCovariantKTypeProjection";
    public static final String CREATE_DYNAMIC_KTYPE = "createDynamicKType";
    public static final String CREATE_INVARIANT_KTYPE_PROJECTION = "createInvariantKTypeProjection";
    public static final String CREATE_KTYPE = "createKType";
    public static final String CREATE_KTYPE_PARAMETER = "createKTypeParameter";
    public static final String CREATE_REIFIED_KTYPE_PARAMETER = "getReifiedTypeParameterKType";
    public static final String DEFAULT_PARAMETER_IMPLEMENTOR_SUFFIX = "$default";
    public static final String DEFINE_INLINE_FUNCTION = "defineInlineFunction";
    public static final String ENUM_NAME_FIELD = "name$";
    public static final String ENUM_ORDINAL_FIELD = "ordinal$";
    public static final String EQUALS_METHOD_NAME = "equals";
    public static final String FUNCTION_CALLABLE_REF = "getCallableRef";
    public static final String GET_KCLASS = "getKClass";
    public static final String GET_KCLASS_FROM_EXPRESSION = "getKClassFromExpression";
    public static final String GET_START_KTYPE_PROJECTION = "getStarKTypeProjection";
    public static final String IMPORTS_FOR_INLINE_PROPERTY = "$$importsForInline$$";
    public static final String IS_CHAR = "isChar";
    public static final String IS_NUMBER = "isNumber";
    private static final JsNameRef JS_OBJECT;
    private static final JsNameRef JS_OBJECT_CREATE_FUNCTION;
    public static final String KOTLIN_NAME = "Kotlin";
    public static final String LOCAL_MODULE_PREFIX = "$module$";
    public static final String LONG_FROM_INT = "fromInt";
    public static final String LONG_FROM_NUMBER = "fromNumber";
    public static final String LONG_MAX_VALUE = "MAX_VALUE";
    public static final String LONG_MIN_VALUE = "MIN_VALUE";
    public static final String LONG_NEG_ONE = "NEG_ONE";
    public static final String LONG_ONE = "ONE";
    public static final String LONG_TO_NUMBER = "toNumber";
    public static final String LONG_ZERO = "ZERO";
    public static final String MARK_KTYPE_NULLABLE = "markKTypeNullable";
    public static final String METADATA = "$metadata$";
    public static final String METADATA_CLASS_KIND = "kind";
    public static final String METADATA_SIMPLE_NAME = "simpleName";
    public static final String METADATA_SUPERTYPES = "interfaces";
    public static final String NULL_CHECK_INTRINSIC_NAME = "ensureNotNull";
    public static final String OBJECT_INSTANCE_FUNCTION_SUFFIX = "_getInstance";
    public static final String OBJECT_INSTANCE_VAR_SUFFIX = "_instance";
    public static final String OUTER_FIELD_NAME = "$outer";
    public static final String PRIMITIVE_COMPARE_TO = "primitiveCompareTo";
    public static final String PROPERTY_CALLABLE_REF = "getPropertyCallableRef";
    public static final String SAM_FIELD_NAME = "function$";
    public static final String THROW_CLASS_CAST_EXCEPTION_FUN_NAME = "throwCCE";
    public static final String THROW_ILLEGAL_ARGUMENT_EXCEPTION_FUN_NAME = "throwIAE";
    public static final String THROW_UNINITIALIZED_PROPERTY_ACCESS_EXCEPTION = "throwUPAE";
    public static final String UINT_FROM_INT = "toUInt";
    private final JsExpression callGetProperty;
    private final JsExpression callSetProperty;
    private final JsObjectScope kotlinScope;
    public static final String KOTLIN_LOWER_NAME = "Kotlin".toLowerCase();
    public static final JsNameRef IS_ARRAY_FUN_REF = new JsNameRef("isArray", "Array");

    /* JADX WARN: Code duplicated, block: B:18:0x0038 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 4 && i != 6 && i != 8 && i != 11 && i != 13 && i != 17 && i != 19 && i != 29 && i != 35 && i != 42 && i != 44) {
            switch (i) {
                default:
                    switch (i) {
                        case 38:
                        case 39:
                        case 40:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 25:
                case 26:
                case 27:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 4 && i != 6 && i != 8 && i != 11 && i != 13 && i != 17 && i != 19 && i != 29 && i != 35 && i != 42 && i != 44) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 38:
                        case 39:
                        case 40:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "config";
                break;
            case 2:
                objArr[0] = "bindingContext";
                break;
            case 3:
            case 4:
            case 6:
            case 8:
            case 11:
            case 13:
            case 17:
            case 19:
            case 25:
            case 26:
            case 27:
            case 29:
            case 35:
            case 38:
            case 39:
            case 40:
            case 42:
            case 44:
                objArr[0] = "org/jetbrains/kotlin/js/translate/context/Namer";
                break;
            case 5:
            case 7:
                objArr[0] = "functionExpression";
                break;
            case 9:
                objArr[0] = "referenceToClass";
                break;
            case 10:
                objArr[0] = "ref";
                break;
            case 12:
                objArr[0] = "descriptor";
                break;
            case 14:
            case 15:
                objArr[0] = "rootScope";
                break;
            case 16:
            case 18:
            case 45:
                objArr[0] = "name";
                break;
            case 20:
            case 21:
            case 28:
            case 37:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 22:
                objArr[0] = "callable";
                break;
            case 23:
                objArr[0] = "a";
                break;
            case 24:
                objArr[0] = JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME;
                break;
            case 30:
            case 32:
                objArr[0] = "functionName";
                break;
            case 31:
            case 34:
                objArr[0] = "metadata";
                break;
            case 33:
                objArr[0] = "arguments";
                break;
            case 36:
                objArr[0] = "instance";
                break;
            case 41:
                objArr[0] = "specialFunction";
                break;
            case 43:
                objArr[0] = "id";
                break;
            default:
                objArr[0] = "functionDescriptor";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getFunctionTag";
        } else if (i == 6) {
            objArr[1] = "getFunctionCallRef";
        } else if (i == 8) {
            objArr[1] = "getFunctionApplyRef";
        } else if (i == 11) {
            objArr[1] = "getCapturedVarAccessor";
        } else if (i == 13) {
            objArr[1] = "isInstanceSuggestedName";
        } else if (i == 17) {
            objArr[1] = IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME;
        } else if (i == 19) {
            objArr[1] = "kotlinObject";
        } else if (i == 29) {
            objArr[1] = "isPrimitiveArray";
        } else if (i == 35) {
            objArr[1] = "invokeFunctionAndSetTypeCheckMetadata";
        } else if (i == 42) {
            objArr[1] = "createSpecialFunction";
        } else if (i != 44) {
            switch (i) {
                case 25:
                    objArr[1] = "isComparable";
                    break;
                case 26:
                    objArr[1] = "isCharSequence";
                    break;
                case 27:
                    objArr[1] = "isArray";
                    break;
                default:
                    switch (i) {
                        case 38:
                            objArr[1] = "isInstanceOf";
                            break;
                        case 39:
                            objArr[1] = "getCallGetProperty";
                            break;
                        case 40:
                            objArr[1] = "getCallSetProperty";
                            break;
                        default:
                            objArr[1] = "org/jetbrains/kotlin/js/translate/context/Namer";
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "suggestedModuleName";
        }
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 11:
            case 13:
            case 17:
            case 19:
            case 25:
            case 26:
            case 27:
            case 29:
            case 35:
            case 38:
            case 39:
            case 40:
            case 42:
            case 44:
                break;
            case 5:
                objArr[2] = "getFunctionCallRef";
                break;
            case 7:
                objArr[2] = "getFunctionApplyRef";
                break;
            case 9:
                objArr[2] = "createObjectWithPrototypeFrom";
                break;
            case 10:
                objArr[2] = "getCapturedVarAccessor";
                break;
            case 12:
                objArr[2] = "isInstanceSuggestedName";
                break;
            case 14:
                objArr[2] = TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME;
                break;
            case 15:
                objArr[2] = "<init>";
                break;
            case 16:
            case 18:
                objArr[2] = IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME;
                break;
            case 20:
                objArr[2] = "isTypeOf";
                break;
            case 21:
            case 36:
            case 37:
                objArr[2] = "isInstanceOf";
                break;
            case 22:
                objArr[2] = "orNull";
                break;
            case 23:
            case 24:
                objArr[2] = "andPredicate";
                break;
            case 28:
                objArr[2] = "isPrimitiveArray";
                break;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                objArr[2] = "invokeFunctionAndSetTypeCheckMetadata";
                break;
            case 41:
                objArr[2] = "createSpecialFunction";
                break;
            case 43:
                objArr[2] = "suggestedModuleName";
                break;
            case 45:
                objArr[2] = "requiresEscaping";
                break;
            default:
                objArr[2] = "getFunctionTag";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 6 && i != 8 && i != 11 && i != 13 && i != 17 && i != 19 && i != 29 && i != 35 && i != 42 && i != 44) {
            switch (i) {
                case 25:
                case 26:
                case 27:
                    break;
                default:
                    switch (i) {
                        case 38:
                        case 39:
                        case 40:
                            break;
                        default:
                            throw new IllegalArgumentException(str2);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    static {
        JsNameRef jsNameRef = new JsNameRef(CommonClassNames.JAVA_LANG_OBJECT_SHORT);
        JS_OBJECT = jsNameRef;
        JS_OBJECT_CREATE_FUNCTION = new JsNameRef(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, jsNameRef);
    }

    public static String getFunctionTag(CallableDescriptor callableDescriptor, JsConfig jsConfig, BindingContext bindingContext) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (jsConfig == null) {
            $$$reportNull$$$0(1);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(2);
        }
        String tag = ArrayFIF.INSTANCE.getTag(callableDescriptor, jsConfig);
        if (tag != null) {
            if (tag == null) {
                $$$reportNull$$$0(3);
            }
            return tag;
        }
        CallableDescriptor callableDescriptor2 = (CallableDescriptor) JsDescriptorUtils.findRealInlineDeclaration(callableDescriptor);
        String moduleName = JsDescriptorUtils.getModuleName(callableDescriptor2);
        FqNameUnsafe fqNameUnsafeParent = DescriptorUtils.getFqName(callableDescriptor2).parent();
        String strJoin = StringUtil.join((Collection<String>) Arrays.asList(moduleName, !fqNameUnsafeParent.isRoot() ? fqNameUnsafeParent.asString() : null, new NameSuggestion().suggest(callableDescriptor2, bindingContext).getNames().get(0)), ".");
        if (strJoin == null) {
            $$$reportNull$$$0(4);
        }
        return strJoin;
    }

    public static String getReceiverParameterName() {
        return AsmUtil.RECEIVER_PARAMETER_NAME;
    }

    public static String getRootPackageName() {
        return "_";
    }

    public static String getPrototypeName() {
        return "prototype";
    }

    public static String getDelegatePrefix() {
        return JvmAbi.DELEGATED_PROPERTY_NAME_SUFFIX;
    }

    public static JsNameRef getFunctionCallRef(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(5);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn("call", jsExpression);
        MetadataProperties.setJsCall(jsNameRefPureFqn, true);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(6);
        }
        return jsNameRefPureFqn;
    }

    public static JsNameRef getFunctionApplyRef(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(7);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn("apply", jsExpression);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(8);
        }
        return jsNameRefPureFqn;
    }

    public static JsInvocation createObjectWithPrototypeFrom(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(9);
        }
        return new JsInvocation(JS_OBJECT_CREATE_FUNCTION.deepCopy(), JsAstUtils.prototypeOf(jsExpression));
    }

    public static JsNameRef getCapturedVarAccessor(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(10);
        }
        JsNameRef jsNameRef = new JsNameRef(RegisterSpec.PREFIX, jsExpression);
        MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.DEPENDS_ON_STATE);
        return jsNameRef;
    }

    public static String isInstanceSuggestedName(TypeParameterDescriptor typeParameterDescriptor) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        String str = LanguageCodeUtil.IS + NameSuggestion.sanitizeName(typeParameterDescriptor.getShortName().getIdentifier());
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        return str;
    }

    public static Namer newInstance(JsScope jsScope) {
        if (jsScope == null) {
            $$$reportNull$$$0(14);
        }
        return new Namer(jsScope);
    }

    private Namer(JsScope jsScope) {
        if (jsScope == null) {
            $$$reportNull$$$0(15);
        }
        this.kotlinScope = new JsObjectScope(jsScope, "Kotlin standard object");
        this.callGetProperty = kotlin("callGetter");
        this.callSetProperty = kotlin("callSetter");
    }

    public static JsNameRef kotlin(JsName jsName) {
        if (jsName == null) {
            $$$reportNull$$$0(16);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(jsName, kotlinObject());
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(17);
        }
        return jsNameRefPureFqn;
    }

    public JsNameRef kotlin(String str) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        return kotlin(this.kotlinScope.declareName(str));
    }

    public static JsNameRef kotlinObject() {
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn("Kotlin", (JsExpression) null);
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(19);
        }
        return jsNameRefPureFqn;
    }

    public JsExpression isTypeOf(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(20);
        }
        return invokeFunctionAndSetTypeCheckMetadata("isTypeOf", jsExpression, TypeCheck.TYPEOF);
    }

    public JsExpression isInstanceOf(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(21);
        }
        return invokeFunctionAndSetTypeCheckMetadata("isInstanceOf", jsExpression, TypeCheck.INSTANCEOF);
    }

    public JsExpression orNull(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(22);
        }
        return invokeFunctionAndSetTypeCheckMetadata("orNull", jsExpression, TypeCheck.OR_NULL);
    }

    public JsExpression andPredicate(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(23);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(24);
        }
        return invokeFunctionAndSetTypeCheckMetadata("andPredicate", Arrays.asList(jsExpression, jsExpression2), TypeCheck.AND_PREDICATE);
    }

    public JsExpression isComparable() {
        JsNameRef jsNameRefKotlin = kotlin("isComparable");
        if (jsNameRefKotlin == null) {
            $$$reportNull$$$0(25);
        }
        return jsNameRefKotlin;
    }

    public JsExpression isCharSequence() {
        JsNameRef jsNameRefKotlin = kotlin("isCharSequence");
        if (jsNameRefKotlin == null) {
            $$$reportNull$$$0(26);
        }
        return jsNameRefKotlin;
    }

    public JsExpression isArray() {
        JsNameRef jsNameRefKotlin = kotlin("isArray");
        if (jsNameRefKotlin == null) {
            $$$reportNull$$$0(27);
        }
        return jsNameRefKotlin;
    }

    public JsExpression isPrimitiveArray(PrimitiveType primitiveType) {
        if (primitiveType == null) {
            $$$reportNull$$$0(28);
        }
        JsNameRef jsNameRefKotlin = kotlin(LanguageCodeUtil.IS + primitiveType.getArrayTypeName().asString());
        if (jsNameRefKotlin == null) {
            $$$reportNull$$$0(29);
        }
        return jsNameRefKotlin;
    }

    private JsExpression invokeFunctionAndSetTypeCheckMetadata(String str, JsExpression jsExpression, TypeCheck typeCheck) {
        if (str == null) {
            $$$reportNull$$$0(30);
        }
        if (typeCheck == null) {
            $$$reportNull$$$0(31);
        }
        return invokeFunctionAndSetTypeCheckMetadata(str, jsExpression != null ? Collections.singletonList(jsExpression) : Collections.emptyList(), typeCheck);
    }

    private JsExpression invokeFunctionAndSetTypeCheckMetadata(String str, List<JsExpression> list, TypeCheck typeCheck) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (list == null) {
            $$$reportNull$$$0(33);
        }
        if (typeCheck == null) {
            $$$reportNull$$$0(34);
        }
        JsInvocation jsInvocation = new JsInvocation(kotlin(str), new JsExpression[0]);
        jsInvocation.getArguments().addAll(list);
        MetadataProperties.setTypeCheck(jsInvocation, typeCheck);
        MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
        return jsInvocation;
    }

    public static JsExpression isInstanceOf(JsExpression jsExpression, JsExpression jsExpression2) {
        if (jsExpression == null) {
            $$$reportNull$$$0(36);
        }
        if (jsExpression2 == null) {
            $$$reportNull$$$0(37);
        }
        JsInvocation jsInvocation = new JsInvocation(new JsNameRef("isType", "Kotlin"), jsExpression, jsExpression2);
        MetadataProperties.setSideEffects(jsInvocation, SideEffectKind.PURE);
        return jsInvocation;
    }

    public static JsExpression getUndefinedExpression() {
        return new JsPrefixOperation(JsUnaryOperator.VOID, new JsIntLiteral(0));
    }

    public JsExpression getCallGetProperty() {
        JsExpression jsExpressionDeepCopy = this.callGetProperty.deepCopy();
        if (jsExpressionDeepCopy == null) {
            $$$reportNull$$$0(39);
        }
        return jsExpressionDeepCopy;
    }

    public JsExpression getCallSetProperty() {
        JsExpression jsExpressionDeepCopy = this.callSetProperty.deepCopy();
        if (jsExpressionDeepCopy == null) {
            $$$reportNull$$$0(40);
        }
        return jsExpressionDeepCopy;
    }

    public static JsNameRef kotlinLong() {
        return JsAstUtils.pureFqn("Long", kotlinObject());
    }

    public static JsExpression createSpecialFunction(SpecialFunction specialFunction) {
        if (specialFunction == null) {
            $$$reportNull$$$0(41);
        }
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(specialFunction.getSuggestedName(), kotlinObject());
        if (jsNameRefPureFqn == null) {
            $$$reportNull$$$0(42);
        }
        return jsNameRefPureFqn;
    }

    public static String suggestedModuleName(String str) {
        if (str == null) {
            $$$reportNull$$$0(43);
        }
        if (str.isEmpty()) {
            return "_";
        }
        StringBuilder sb = new StringBuilder(str.length());
        char cCharAt = str.charAt(0);
        if (Character.isJavaIdentifierStart(cCharAt)) {
            sb.append(cCharAt);
        } else {
            sb.append('_');
            if (Character.isJavaIdentifierPart(cCharAt)) {
                sb.append(cCharAt);
            }
        }
        for (int i = 1; i < str.length(); i++) {
            char cCharAt2 = str.charAt(i);
            if (!Character.isJavaIdentifierPart(cCharAt2)) {
                cCharAt2 = '_';
            }
            sb.append(cCharAt2);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(44);
        }
        return string;
    }

    public static JsNameRef imul() {
        return JsAstUtils.pureFqn("imul", kotlinObject());
    }

    public static boolean requiresEscaping(String str) {
        if (str == null) {
            $$$reportNull$$$0(45);
        }
        if (str.isEmpty() || !Character.isJavaIdentifierStart(str.charAt(0))) {
            return true;
        }
        for (int i = 1; i < str.length(); i++) {
            if (!Character.isJavaIdentifierPart(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
