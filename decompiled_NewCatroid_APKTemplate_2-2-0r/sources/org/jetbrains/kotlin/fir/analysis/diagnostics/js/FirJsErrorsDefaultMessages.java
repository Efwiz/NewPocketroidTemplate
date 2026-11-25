package org.jetbrains.kotlin.fir.analysis.diagnostics.js;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.fir.analysis.diagnostics.FirDiagnosticRenderers;

/* JADX INFO: compiled from: FirJsErrorsDefaultMessages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/diagnostics/js/FirJsErrorsDefaultMessages;", "Lorg/jetbrains/kotlin/diagnostics/rendering/BaseDiagnosticRendererFactory;", "()V", "MAP", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "getMAP", "()Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "checkers.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJsErrorsDefaultMessages extends BaseDiagnosticRendererFactory {
    public static final FirJsErrorsDefaultMessages INSTANCE = new FirJsErrorsDefaultMessages();
    private static final KtDiagnosticFactoryToRendererMap MAP;

    private FirJsErrorsDefaultMessages() {
    }

    static {
        KtDiagnosticFactoryToRendererMap ktDiagnosticFactoryToRendererMap = new KtDiagnosticFactoryToRendererMap("FIR");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_JS_QUALIFIER(), "Qualifier contains illegal characters");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_MODULE_PROHIBITED_ON_VAR(), "@JsModule and @JsNonModule annotations prohibited for 'var' declarations. Use 'val' instead.");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_MODULE_PROHIBITED_ON_NON_NATIVE(), "@JsModule and @JsNonModule annotations prohibited for non-external declarations.");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNESTED_JS_MODULE_PROHIBITED(), "@JsModule and @JsNonModule can't appear on here since the file is already marked by either @JsModule or @JsNonModule");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getCALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE(), "When accessing module declarations from UMD, they must be marked by both @JsModule and @JsNonModule");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getCALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM(), "Can''t access {0} marked with @JsModule annotation from non-modular project", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getCALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM(), "Can''t access {0} marked with @JsNonModule annotation from modular project", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_MULTIPLE_INHERITANCE(), "Can''t apply multiple inheritance here, since it''s impossible to generate bridge for system function {0}", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getDELEGATION_BY_DYNAMIC(), "Can't delegate to dynamic value");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getPROPERTY_DELEGATION_BY_DYNAMIC(), "Can't apply property delegation by dynamic handler");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getSPREAD_OPERATOR_IN_DYNAMIC_CALL(), "Can't apply spread operator in dynamic call");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_OPERATION_WITH_DYNAMIC(), "Wrong operation with dynamic value: {0}", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getIMPLEMENTING_FUNCTION_INTERFACE(), "Implementing function interface is prohibited in JavaScript");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS(), "Overriding `external` function with optional parameters");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getOVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE(), "Overriding `external` function with optional parameters by declaration from superclass: {0}", FirDiagnosticRenderers.INSTANCE.getSYMBOL());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getCALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION(), "This property can only be used from external declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getRUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION(), "Runtime annotation can't be put on external declaration");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getRUNTIME_ANNOTATION_NOT_SUPPORTED(), "Reflection is not supported in JavaScript target, therefore you won't be able to read this annotation in run-time");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER(), "External class constructor cannot have a property parameter");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_ENUM_ENTRY_WITH_BODY(), "Entry of external enum class can't have body");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_ANONYMOUS_INITIALIZER(), "Anonymous initializer is not allowed in external classes");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_DELEGATION(), "Can't use delegate on external declaration");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_DELEGATED_CONSTRUCTOR_CALL(), "Delegated constructor call in external class is not allowed");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_BODY_OF_EXTERNAL_DECLARATION(), "Wrong body of external declaration. Must be either ' = definedExternally' or { definedExternally }");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_INITIALIZER_OF_EXTERNAL_DECLARATION(), "Wrong initializer of external declaration. Must be ' = definedExternally'");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER(), "Wrong default value for parameter of external function. Must be ' = definedExternally'");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNESTED_EXTERNAL_DECLARATION(), "Non-top-level `external` declaration");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_EXTERNAL_DECLARATION(), "Declaration of such kind ({0}) can''t be external", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNESTED_CLASS_IN_EXTERNAL_INTERFACE(), "Interface can't contain nested classes and objects");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE(), "External type extends non-external type");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getINLINE_EXTERNAL_DECLARATION(), "Inline external declaration");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getINLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING(), "Using value classes as parameter type or return type of external declarations is experimental");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING(), "Using enum classes with an `external` qualifier becomes deprecated and will be an error in future releases");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getINLINE_CLASS_IN_EXTERNAL_DECLARATION(), "Using value classes as parameter type or return type of external declarations is not supported");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION(), "Function types with receiver are prohibited in external declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE(), "Only nullable properties of external interfaces are allowed to be non-abstract");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN(), "Annotation ''{0}'' is allowed only on member functions of declaration annotated as ''kotlin.js.native'' or on toplevel extension functions", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER(), "Native {0}''s first parameter type should be ''kotlin.String'' or subtype of ''kotlin.Number''", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS(), "Native {0}''s parameter can not have default value", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE(), "Native getter's return type should be nullable");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_SETTER_WRONG_RETURN_TYPE(), "Native setter's return type should be 'Unit' or a supertype of the second parameter's type");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNATIVE_INDEXER_WRONG_PARAMETER_COUNT(), "Expected {0} parameters for native {1}", KtDiagnosticRenderers.INSTANCE.getTO_STRING(), CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE(), "Can''t put non-external declarations in file marked with ''{0}'' annotation", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_EXTERNAL_INHERITORS_ONLY(), "External {0} can''t be a parent of non-external {1}", FirDiagnosticRenderers.INSTANCE.getRENDER_CLASS_OR_OBJECT_NAME(), FirDiagnosticRenderers.INSTANCE.getRENDER_CLASS_OR_OBJECT_NAME());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_EXTERNAL_ARGUMENT(), "Expected argument with external type, but type ''{0}'' is non-external", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT(), "Cannot pass external interface {0} for reified type parameter", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY(), "@JsName is prohibited for extension properties");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_BUILTIN_NAME_CLASH(), "JavaScript name generated for this declaration clashes with built-in declaration {0}", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNAME_CONTAINS_ILLEGAL_CHARS(), "Name contains illegal chars that can't appear in JavaScript identifier");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_IS_NOT_ON_ALL_ACCESSORS(), "@JsName should be on all the property accessors");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_NAMED_NATIVE(), "@JsName is prohibited for external declaration with explicit name");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_PROHIBITED_FOR_OVERRIDE(), "@JsName is prohibited for overridden members");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED(), "@JsName annotation is prohibited for primary constructors");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getJS_NAME_ON_ACCESSOR_AND_PROPERTY(), "@JsName can be either on a property or its accessors, not both of them");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getCANNOT_CHECK_FOR_EXTERNAL_INTERFACE(), "Cannot check for external interface: ''{0}''", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getUNCHECKED_CAST_TO_EXTERNAL_INTERFACE(), "Unchecked cast to external interface: ''{0}'' to ''{1}''", FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE(), FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getEXTERNAL_INTERFACE_AS_CLASS_LITERAL(), "Can't refer to external interface from class literal");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNESTED_JS_EXPORT(), "@JsExport is only allowed on files and top-level declarations");
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getWRONG_EXPORTED_DECLARATION(), "Declaration of such kind ({0}) can''t be exported to JS", CommonRenderers.STRING);
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNON_EXPORTABLE_TYPE(), "Exported declaration uses non-exportable {0} type: ''{1}''", CommonRenderers.STRING, FirDiagnosticRenderers.INSTANCE.getRENDER_TYPE());
        ktDiagnosticFactoryToRendererMap.put(FirJsErrors.INSTANCE.getNON_CONSUMABLE_EXPORTED_IDENTIFIER(), "Exported declaration contains non-consumable identifier '0', that can't be represented inside TS definitions and ESM", CommonRenderers.STRING);
        MAP = ktDiagnosticFactoryToRendererMap;
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
    public KtDiagnosticFactoryToRendererMap getMAP() {
        return MAP;
    }
}
