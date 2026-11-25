package org.jetbrains.kotlin.fir.analysis.diagnostics.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers;

/* JADX INFO: compiled from: FirJvmErrorsDefaultMessages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/jvm/FirJvmErrorsDefaultMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmErrorsDefaultMessages extends BaseDiagnosticRendererFactory {
    public static final FirJvmErrorsDefaultMessages INSTANCE = new FirJvmErrorsDefaultMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private FirJvmErrorsDefaultMessages() {
    }

    static {
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("FIR");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getCONFLICTING_JVM_DECLARATIONS(), "Platform declaration clash");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJAVA_TYPE_MISMATCH(), "Java type mismatch: expected ''{0}'' but found ''{1}''. Use explicit cast", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE(), FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getUPPER_BOUND_CANNOT_BE_ARRAY(), "Upper bound of a type parameter cannot be an array");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSTRICTFP_ON_CLASS(), "'@Strictfp' annotation on classes is unsupported yet");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_ABSTRACT(), "'@Synchronized' annotation cannot be used on abstract functions");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_INLINE(), "'@Synchronized' annotation has no effect on inline functions");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSYNCHRONIZED_ON_SUSPEND(), "@Synchronized annotation is not applicable to suspend functions and lambdas");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSYNCHRONIZED_IN_INTERFACE(), "'@Synchronized' annotation cannot be used on interface members");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_WITHOUT_DEFAULT_ARGUMENTS(), "'@JvmOverloads' annotation has no effect for methods without default arguments");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_ABSTRACT(), "'@JvmOverloads' annotation cannot be used on abstract methods");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_INTERFACE(), "'@JvmOverloads' annotation cannot be used on interface methods");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_PRIVATE(), "'@JvmOverloads' annotation has no effect on private declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_LOCAL(), "'@JvmOverloads' annotation cannot be used on local declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR(), "'@JvmOverloads' annotation cannot be used on constructors of annotation classes");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getDEPRECATED_JAVA_ANNOTATION(), "This annotation is deprecated in Kotlin. Use ''@{0}'' instead", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getPOSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION(), "Only named arguments are available for Java annotations");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_CANNOT_BE_EMPTY(), "'@JvmPackageName' annotation value cannot be empty");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_MUST_BE_VALID_NAME(), "'@JvmPackageName' annotation value must be a valid dot-qualified name of a package");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES(), "'@JvmPackageName' annotation is not supported for files with class declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSUPER_CALL_WITH_DEFAULT_PARAMETERS(), "Super-calls with default arguments are not allowed. Please specify all arguments of ''super.{0}'' explicitly", KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getLOCAL_JVM_RECORD(), "Local @JvmRecord classes are not allowed");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getNON_FINAL_JVM_RECORD(), "@JvmRecord class should be final");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getENUM_JVM_RECORD(), "@JvmRecord class should not be an enum");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS(), "Primary constructor with parameters is required for @JvmRecord class");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_RECORD_NOT_VAL_PARAMETER(), "Constructor parameter of @JvmRecord class should be a val");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_RECORD_NOT_LAST_VARARG_PARAMETER(), "Only the last constructor parameter of @JvmRecord may be a vararg");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_RECORD_EXTENDS_CLASS(), "Record cannot inherit a class", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getINNER_JVM_RECORD(), "@JvmRecord class should not be inner");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getFIELD_IN_JVM_RECORD(), "It's not allowed to have non-constructor properties with backing field in @JvmRecord class");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getDELEGATION_BY_IN_JVM_RECORD(), "Delegation is not allowed for @JvmRecord classes");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getNON_DATA_CLASS_JVM_RECORD(), "Only data classes are allowed to be marked as @JvmRecord");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getILLEGAL_JAVA_LANG_RECORD_SUPERTYPE(), "Classes cannot have explicit 'java.lang.Record' supertype");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getOVERRIDE_CANNOT_BE_STATIC(), "Override member cannot be '@JvmStatic' in object");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION(), "Only members in named objects and companion objects of classes can be annotated with '@JvmStatic'");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_STATIC_NOT_IN_OBJECT_OR_COMPANION(), "Only members in named objects and companion objects can be annotated with '@JvmStatic'");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_STATIC_ON_NON_PUBLIC_MEMBER(), "Only public members in interface companion objects can be annotated with '@JvmStatic'");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_STATIC_ON_CONST_OR_JVM_FIELD(), "'@JvmStatic' annotation is useless for const or '@JvmField' properties");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_STATIC_ON_EXTERNAL_IN_INTERFACE(), "'@JvmStatic' annotation cannot be used on 'external' members of interface companions");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_NAME(), "'@JvmName' annotation is not applicable to this declaration");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getILLEGAL_JVM_NAME(), "Illegal JVM name");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getFUNCTION_DELEGATE_MEMBER_NAME_CLASH(), "Spread operator is prohibited for arguments to signature-polymorphic calls");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getVALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION(), "Value classes without @JvmInline annotation are not supported yet");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_INLINE_WITHOUT_VALUE_CLASS(), "@JvmInline annotation is only applicable to value classes");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_DEFAULT_IN_DECLARATION(), "Usage of ''@{0}'' is only allowed with -Xjvm-default option", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION(), "Usage of '@JvmDefaultWithCompatibility' is only allowed with '-Xjvm-default=all' option");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE(), "'@JvmDefaultWithCompatibility' annotation is only allowed on interfaces");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_IN_INTERFACE(), "Members of interfaces can not be external");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT(), "External declaration can not be abstract");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_HAVE_BODY(), "External declaration can not have a body");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getEXTERNAL_DECLARATION_CANNOT_BE_INLINED(), "Inline functions can not be external");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_FIELD(), "{0}", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getINAPPLICABLE_JVM_FIELD_WARNING(), "{0}. This warning will become an error in further releases", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJVM_SYNTHETIC_ON_DELEGATE(), "'@JvmSynthetic' annotation cannot be used on delegated properties");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getNON_SOURCE_REPEATED_ANNOTATION(), "Repeatable annotations with non-SOURCE retention are only supported starting from Kotlin 1.6");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATED_ANNOTATION_WITH_CONTAINER(), "Repeated annotation ''@{0}'' cannot be used on a declaration which is annotated with its container annotation ''@{1}''", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getINTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER(), "Interfaces can call JVM-default members via super only within JVM-default members. Please use '-Xjvm-default=all/all-compatibility' modes for such calls");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC(), "Using protected members which are not @JvmStatic in the superclass companion is unsupported yet");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY(), "Container annotation ''{0}'' must have a property ''value'' of type ''Array<{1}>''.", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER(), "Container annotation ''{0}'' does not have a default value for ''{1}''.", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_HAS_SHORTER_RETENTION(), "Container annotation ''{0}'' has shorter retention (''{1}'') than the repeatable annotation ''{2}'' (''{3}'').", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET(), "Target set of container annotation ''{0}'' must be a subset of the target set of contained annotation ''{1}''.", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER(), "Repeatable annotation cannot have a nested class named 'Container'. This name is reserved for auto-generated container class");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_CRITICAL_SECTION(), "The ''{0}'' suspension point is inside a critical section", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getCONCURRENT_HASH_MAP_CONTAINS_OPERATOR(), "Method 'contains' from ConcurrentHashMap may have unexpected semantics: it calls 'containsValue' instead of 'containsKey'. Use explicit form of the call to 'containsKey'/'containsValue'/'contains' or cast the value to kotlin.collections.Map instead. See https://youtrack.jetbrains.com/issue/KT-18053 for more details");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getSPREAD_ON_SIGNATURE_POLYMORPHIC_CALL(), "Spread operator is prohibited for arguments to signature-polymorphic calls");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getJAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE(), "Java SAM interface constructor references are prohibited");
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getREDUNDANT_REPEATABLE_ANNOTATION(), "Please, remove the ''{0}'' annotation, as ''{1}'' is already enough", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), KtDiagnosticRenderers.INSTANCE.getTO_STRING());
        ktDiagnosticFactoryToRendererMap.put(FirJvmErrors.INSTANCE.getNO_REFLECTION_IN_CLASS_PATH(), "Call uses reflection API which is not found in compilation classpath. Make sure you have kotlin-reflect.jar in the classpath");
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
