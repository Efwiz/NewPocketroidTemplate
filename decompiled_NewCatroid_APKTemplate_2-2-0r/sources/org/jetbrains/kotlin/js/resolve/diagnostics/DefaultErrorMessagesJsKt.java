package org.jetbrains.kotlin.js.resolve.diagnostics;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.js.JavaScript;

/* JADX INFO: compiled from: DefaultErrorMessagesJs.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\"\u001b\u0010\u0000\u001a\u00020\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0006"}, d2 = {"DIAGNOSTIC_FACTORY_TO_RENDERER", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "getDIAGNOSTIC_FACTORY_TO_RENDERER", "()Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "DIAGNOSTIC_FACTORY_TO_RENDERER$delegate", "Lkotlin/Lazy;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DefaultErrorMessagesJsKt {
    private static final Lazy DIAGNOSTIC_FACTORY_TO_RENDERER$delegate = LazyKt.lazy(new Function0<DiagnosticFactoryToRendererMap>() { // from class: org.jetbrains.kotlin.js.resolve.diagnostics.DefaultErrorMessagesJsKt$DIAGNOSTIC_FACTORY_TO_RENDERER$2
        @Override // kotlin.jvm.functions.Function0
        public final DiagnosticFactoryToRendererMap invoke() {
            DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = new DiagnosticFactoryToRendererMap(JavaScript.NAME);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_ANNOTATIONS_ALLOWED_ONLY_ON_MEMBER_OR_EXTENSION_FUN, "Annotation ''{0}'' is allowed only on member functions of declaration annotated as ''kotlin.js.native'' or on toplevel extension functions", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_INDEXER_KEY_SHOULD_BE_STRING_OR_NUMBER, "Native {0}''s first parameter type should be ''kotlin.String'' or subtype of ''kotlin.Number''", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_INDEXER_CAN_NOT_HAVE_DEFAULT_ARGUMENTS, "Native {0}''s parameter can not have default value", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_GETTER_RETURN_TYPE_SHOULD_BE_NULLABLE, "Native getter's return type should be nullable");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_SETTER_WRONG_RETURN_TYPE, "Native setter's return type should be 'Unit' or a supertype of the second parameter's type");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NATIVE_INDEXER_WRONG_PARAMETER_COUNT, "Expected {0} parameters for native {1}", Renderers.TO_STRING, CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JSCODE_ERROR, "JavaScript: {0}", JsCallDataTextRenderer.INSTANCE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JSCODE_WARNING, "JavaScript: {0}", JsCallDataTextRenderer.INSTANCE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JSCODE_ARGUMENT_SHOULD_BE_CONSTANT, "Argument must be string constant");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NOT_SUPPORTED, "Cannot translate (not supported yet): ''{0}''", RenderFirstLineOfElementText.INSTANCE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JSCODE_NO_JAVASCRIPT_PRODUCED, "Argument must be non-empty JavaScript code");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NESTED_EXTERNAL_DECLARATION, "Non top-level `external` declaration");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_EXTERNAL_DECLARATION, "Declaration of such kind ({0}) can''t be external", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTENSION_FUNCTION_IN_EXTERNAL_DECLARATION, "Function types with receiver are prohibited in external declarations");
            diagnosticFactoryToRendererMap.put(ErrorsJs.INLINE_CLASS_IN_EXTERNAL_DECLARATION, "Using value classes as parameter type or return type of external declarations is not supported");
            diagnosticFactoryToRendererMap.put(ErrorsJs.INLINE_CLASS_IN_EXTERNAL_DECLARATION_WARNING, "Using value classes as parameter type or return type of external declarations is experimental");
            diagnosticFactoryToRendererMap.put(ErrorsJs.ENUM_CLASS_IN_EXTERNAL_DECLARATION_WARNING, "Using enum classes with an `external` qualifier becomes deprecated and will be an error in future releases");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_CLASH, "JavaScript name ({0}) generated for this declaration clashes with another declaration: {1}", CommonRenderers.STRING, Renderers.COMPACT);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_FAKE_NAME_CLASH, "JavaScript name {0} is generated for different inherited members: {1} and {2}", CommonRenderers.STRING, Renderers.COMPACT, Renderers.COMPACT);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_BUILTIN_NAME_CLASH, "JavaScript name generated for this declaration clashes with built-in declaration {1}", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_ON_PRIMARY_CONSTRUCTOR_PROHIBITED, "@JsName annotation is prohibited for primary constructors");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_ON_ACCESSOR_AND_PROPERTY, "@JsName can be either on a property or its accessors, not both of them");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_IS_NOT_ON_ALL_ACCESSORS, "@JsName should be on all of the property accessors");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_PROHIBITED_FOR_OVERRIDE, "@JsName is prohibited for overridden members");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_PROHIBITED_FOR_EXTENSION_PROPERTY, "@JsName is prohibited for extension properties");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_NAME_PROHIBITED_FOR_NAMED_NATIVE, "@JsName is prohibited for external declaration with explicit name");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NAME_CONTAINS_ILLEGAL_CHARS, "Name contains illegal chars that can't appear in JavaScript identifier");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_MODULE_PROHIBITED_ON_VAR, "@JsModule and @JsNonModule annotations prohibited for 'var' declarations. Use 'val' instead.");
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_MODULE_PROHIBITED_ON_NON_NATIVE, "@JsModule and @JsNonModule annotations prohibited for non-external declarations.");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NESTED_JS_MODULE_PROHIBITED, "@JsModule and @JsNonModule can't appear on here since the file is already marked by either @JsModule or @JsNonModule");
            diagnosticFactoryToRendererMap.put(ErrorsJs.CALL_TO_JS_MODULE_WITHOUT_MODULE_SYSTEM, "Can''t access {0} marked with @JsModule annotation from non-modular project", Renderers.DECLARATION_NAME_WITH_KIND);
            diagnosticFactoryToRendererMap.put(ErrorsJs.CALL_TO_JS_NON_MODULE_WITH_MODULE_SYSTEM, "Can''t access {0} marked with @JsNonModule annotation from modular project", Renderers.DECLARATION_NAME_WITH_KIND);
            diagnosticFactoryToRendererMap.put(ErrorsJs.CALL_FROM_UMD_MUST_BE_JS_MODULE_AND_JS_NON_MODULE, "When accessing module declarations from UMD, they must be marked by both @JsModule and @JsNonModule");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NON_EXTERNAL_DECLARATION_IN_INAPPROPRIATE_FILE, "Can''t put non-external declarations in file marked with {0} annotation", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_JS_QUALIFIER, "Qualifier contains illegal characters");
            diagnosticFactoryToRendererMap.put(ErrorsJs.CANNOT_CHECK_FOR_EXTERNAL_INTERFACE, "Cannot check for external interface: {0}", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.UNCHECKED_CAST_TO_EXTERNAL_INTERFACE, "Unchecked cast to external interface: {0} to {1}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_INTERFACE_AS_REIFIED_TYPE_ARGUMENT, "Cannot pass external interface {0} for reified type parameter", Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_INTERFACE_AS_CLASS_LITERAL, "Can't refer to external interface from class literal");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE, "External type extends non-external type");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_OPERATION_WITH_DYNAMIC, "Wrong operation with dynamic value: {0}", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.SPREAD_OPERATOR_IN_DYNAMIC_CALL, "Can't apply spread operator in dynamic call");
            diagnosticFactoryToRendererMap.put(ErrorsJs.DELEGATION_BY_DYNAMIC, "Can't delegate to dynamic value");
            diagnosticFactoryToRendererMap.put(ErrorsJs.PROPERTY_DELEGATION_BY_DYNAMIC, "Can't apply property delegation by dynamic handler");
            diagnosticFactoryToRendererMap.put(ErrorsJs.RUNTIME_ANNOTATION_ON_EXTERNAL_DECLARATION, "Runtime annotation can't be put on external declaration");
            diagnosticFactoryToRendererMap.put(ErrorsJs.RUNTIME_ANNOTATION_NOT_SUPPORTED, "Reflection is not supported in JavaScript target, therefore you won't be able to read this annotation in run-time");
            diagnosticFactoryToRendererMap.put(ErrorsJs.OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS, "Overriding `external` function with optional parameters");
            diagnosticFactoryToRendererMap.put(ErrorsJs.OVERRIDING_EXTERNAL_FUN_WITH_OPTIONAL_PARAMS_WITH_FAKE, "Overriding `external` function with optional parameters by declaration from superclass: {0}", Renderers.COMPACT);
            diagnosticFactoryToRendererMap.put(ErrorsJs.IMPLEMENTING_FUNCTION_INTERFACE, "Implementing function interface is prohibited in JavaScript");
            diagnosticFactoryToRendererMap.put(ErrorsJs.INLINE_EXTERNAL_DECLARATION, "Inline external declaration");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE, "Only nullable properties of external interfaces are allowed to be non-abstract");
            diagnosticFactoryToRendererMap.put(ErrorsJs.NESTED_CLASS_IN_EXTERNAL_INTERFACE, "Interface can't contain nested classes and objects");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_BODY_OF_EXTERNAL_DECLARATION, "Wrong body of external declaration. Must be either ' = definedExternally' or { definedExternally }");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_INITIALIZER_OF_EXTERNAL_DECLARATION, "Wrong initializer of external declaration. Must be ' = definedExternally'");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_DEFAULT_VALUE_FOR_EXTERNAL_FUN_PARAMETER, "Wrong default value for parameter of external function. Must be ' = definedExternally'");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_DELEGATED_CONSTRUCTOR_CALL, "Delegated constructor call in external class is not allowed");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_DELEGATION, "Can't use delegate on external declaration");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_ANONYMOUS_INITIALIZER, "Anonymous initializer is not allowed in external classes");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_ENUM_ENTRY_WITH_BODY, "Entry of external enum class can't have body");
            diagnosticFactoryToRendererMap.put(ErrorsJs.EXTERNAL_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER, "External class constructor cannot have a property parameter");
            diagnosticFactoryToRendererMap.put(ErrorsJs.CALL_TO_DEFINED_EXTERNALLY_FROM_NON_EXTERNAL_DECLARATION, "This property can only be used from external declarations");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_MULTIPLE_INHERITANCE, "Can''t apply multiple inheritance here, since it''s impossible to generate bridge for system function {0}", Renderers.DECLARATION_NAME_WITH_KIND);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NESTED_JS_EXPORT, "@JsExport is only allowed on files and top-level declarations");
            diagnosticFactoryToRendererMap.put(ErrorsJs.WRONG_EXPORTED_DECLARATION, "Declaration of such kind ({0}) can''t be exported to JS", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NON_EXPORTABLE_TYPE, "Exported declaration uses non-exportable {0} type: {1}", CommonRenderers.STRING, Renderers.RENDER_TYPE);
            diagnosticFactoryToRendererMap.put(ErrorsJs.NON_CONSUMABLE_EXPORTED_IDENTIFIER, "Exported declaration contains non-consumable identifier '0', that can't be represented inside TS definitions and ESM", CommonRenderers.STRING);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_EXTERNAL_INHERITORS_ONLY, "External {0} can''t be a parent of non-external {1}", Renderers.DECLARATION_NAME_WITH_KIND, Renderers.DECLARATION_NAME_WITH_KIND);
            diagnosticFactoryToRendererMap.put(ErrorsJs.JS_EXTERNAL_ARGUMENT, "Expected argument with external type, but type {0} is non-external", Renderers.RENDER_TYPE);
            return diagnosticFactoryToRendererMap;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final DiagnosticFactoryToRendererMap getDIAGNOSTIC_FACTORY_TO_RENDERER() {
        return (DiagnosticFactoryToRendererMap) DIAGNOSTIC_FACTORY_TO_RENDERER$delegate.getValue();
    }
}
