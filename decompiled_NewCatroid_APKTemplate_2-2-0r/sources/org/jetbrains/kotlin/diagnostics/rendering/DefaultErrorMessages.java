package org.jetbrains.kotlin.diagnostics.rendering;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.RenderedDiagnostic;
import org.jetbrains.kotlin.diagnostics.TypeMismatchDueToTypeProjectionsData;
import org.jetbrains.kotlin.diagnostics.UnboundDiagnostic;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.resolve.VarianceConflictDiagnosticData;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultErrorMessages {
    private static final DiagnosticFactoryToRendererMap MAP;
    private static final List<DiagnosticFactoryToRendererMap> RENDERER_MAPS;
    private static final List<String> RENDERER_PLATFORM_EXTENSIONS;

    public interface Extension {
        DiagnosticFactoryToRendererMap getMap();
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages";
        } else {
            objArr[0] = "diagnostic";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "render";
        } else {
            objArr[1] = "org/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages";
        }
        if (i != 1 && i != 2) {
            if (i != 3) {
                objArr[2] = "render";
            } else {
                objArr[2] = "getRendererForDiagnostic";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static {
        DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = new DiagnosticFactoryToRendererMap("Default");
        MAP = diagnosticFactoryToRendererMap;
        List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{"org.jetbrains.kotlin.resolve.jvm.diagnostics.DefaultErrorMessagesJvm", "org.jetbrains.kotlin.js.resolve.diagnostics.DefaultErrorMessagesJs", "org.jetbrains.kotlin.resolve.konan.diagnostics.DefaultErrorMessagesNative", "org.jetbrains.kotlin.wasm.resolve.diagnostics.DefaultErrorMessagesWasm"});
        RENDERER_PLATFORM_EXTENSIONS = listListOf;
        ArrayList arrayList = new ArrayList(listListOf.size() + 1);
        arrayList.add(diagnosticFactoryToRendererMap);
        Iterator<String> it = listListOf.iterator();
        while (it.hasNext()) {
            try {
                Class<?> cls = Class.forName(it.next());
                if (!Extension.class.isAssignableFrom(cls)) {
                    throw new IllegalStateException(cls.getName() + " is not a " + Extension.class.getName());
                }
                arrayList.add(((Extension) cls.newInstance()).getMap());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        RENDERER_MAPS = Collections.unmodifiableList(arrayList);
        DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap2 = MAP;
        diagnosticFactoryToRendererMap2.put(Errors.UNRESOLVED_REFERENCE, "Unresolved reference: {0}", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.INVISIBLE_REFERENCE, "Cannot access ''{0}'': it is {1} in {2}", Renderers.NAME, Renderers.VISIBILITY, Renderers.NAME_OF_CONTAINING_DECLARATION_OR_FILE);
        diagnosticFactoryToRendererMap2.put(Errors.INVISIBLE_MEMBER, "Cannot access ''{0}'': it is {1} in {2}", Renderers.NAME, Renderers.VISIBILITY, Renderers.NAME_OF_CONTAINING_DECLARATION_OR_FILE);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_ACCESS_BY_SHORT_NAME, "Access to this type by short name is deprecated, and soon is going to be removed. Please, add explicit qualifier or import", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_ACCESS_TO_ENUM_COMPANION_PROPERTY, "Ambiguous access to companion''s property ''{0}'' in enum is deprecated. Please, add explicit Companion qualifier to the class name", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_ACCESS_TO_ENUM_ENTRY_COMPANION_PROPERTY, "Ambiguous access to companion''s property 'entries' in enum is deprecated. Please, add explicit Companion qualifier to the class name");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_ACCESS_TO_ENTRY_PROPERTY_FROM_ENUM, "Ambiguous access to property 'entries' from enum is deprecated. Please, add explicit qualifier to the call");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_ACCESS_TO_ENUM_ENTRY_PROPERTY_AS_REFERENCE, "Ambiguous access to property 'entries' is deprecated. Please, specify type of the referenced expression explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_DECLARATION_OF_ENUM_ENTRY, "Conflicting declarations: enum entry 'entries' and the property 'Enum.entries' (KT-48872). Please, rename the enum entry declaration");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_RESOLVE_WITH_AMBIGUOUS_ENUM_ENTRY, "Ambiguous access to property ''{0}'' is deprecated because similar enum entry ''{1}'' is available. Please add explicit named import or use fully qualified name", Renderers.FQ_NAME, Renderers.FQ_NAME);
        diagnosticFactoryToRendererMap2.put(Errors.PROTECTED_CONSTRUCTOR_NOT_IN_SUPER_CALL, "Protected constructor ''{0}'' from other classes can only be used in super-call", Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_PROPERTY_TYPE, "''{0}'' property exposes its ''{2}'' type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_PROPERTY_TYPE_IN_CONSTRUCTOR, "''{0}'' property exposes its ''{2}'' type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_FUNCTION_RETURN_TYPE, "''{0}'' function exposes its ''{2}'' return type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_PARAMETER_TYPE, "''{0}'' function exposes its ''{2}'' parameter type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_RECEIVER_TYPE, "''{0}'' member exposes its ''{2}'' receiver type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_TYPE_PARAMETER_BOUND, "''{0}'' generic exposes its ''{2}'' parameter bound type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_SUPER_CLASS, "''{0}'' subclass exposes its ''{2}'' supertype{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_SUPER_INTERFACE, "''{0}'' sub-interface exposes its ''{2}'' supertype{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_TYPEALIAS_EXPANDED_TYPE, "''{0}'' typealias exposes ''{2}'' in expanded type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPOSED_FROM_PRIVATE_IN_FILE, "Deprecation: ''{0}'' element should not expose ''{2}'' type{1}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_SHADOWED_BY_MEMBER, "Extension is shadowed by a member: {0}", Renderers.COMPACT_WITH_MODIFIERS);
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_FUNCTION_SHADOWED_BY_INNER_CLASS_CONSTRUCTOR, "Extension function is shadowed by an inner class constructor: {0}", Renderers.COMPACT_WITH_MODIFIERS);
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_FUNCTION_SHADOWED_BY_MEMBER_PROPERTY_WITH_INVOKE, "Extension function is shadowed by a member property ''{0}'' with {1}", Renderers.NAME, Renderers.COMPACT_WITH_MODIFIERS);
        diagnosticFactoryToRendererMap2.put(Errors.INACCESSIBLE_TYPE, "Type {0} is inaccessible in this context due to: {1}", Renderers.RENDER_TYPE, CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS));
        diagnosticFactoryToRendererMap2.put(Errors.REDECLARATION, "Conflicting declarations: {0}", CommonRenderers.commaSeparated(Renderers.COMPACT_WITH_MODIFIERS));
        diagnosticFactoryToRendererMap2.put(Errors.PACKAGE_OR_CLASSIFIER_REDECLARATION, "Redeclaration: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DUPLICATE_PARAMETER_NAME_IN_FUNCTION_TYPE, "Duplicate parameter name in function type");
        diagnosticFactoryToRendererMap2.put(Errors.NAME_SHADOWING, "Name shadowed: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.ACCESSOR_PARAMETER_NAME_SHADOWING, "Accessor parameter name 'field' is shadowed by backing field variable");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH, "Type mismatch: inferred type is {1} but {0} was expected", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_WARNING, "Type mismatch: inferred type is {1} but {0} was expected", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_DUE_TO_EQUALS_LAMBDA_IN_FUN, "Inferred type is a function type, but a non-function type {0} was expected. Use either ''= ...'' or '''{ ... }'', but not both.", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_DUE_TO_TYPE_PROJECTIONS, "Type mismatch: inferred type is {1} but {0} was expected. Projected type {2} restricts use of {3}", new MultiRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda12
            @Override // org.jetbrains.kotlin.diagnostics.rendering.MultiRenderer
            public final String[] render(Object obj) {
                return DefaultErrorMessages.lambda$static$1((TypeMismatchDueToTypeProjectionsData) obj);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.MEMBER_PROJECTED_OUT, "Out-projected type ''{1}'' prohibits the use of ''{0}''", Renderers.FQ_NAMES_IN_TYPES, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.INCOMPATIBLE_MODIFIERS, "Modifier ''{0}'' is incompatible with ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_MODIFIER_PAIR, "Modifier ''{0}'' is deprecated in presence of ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REPEATED_MODIFIER, "Repeated ''{0}''", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_MODIFIER_TARGET, "Modifier ''{0}'' is not applicable to ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_MODIFIER_FOR_TARGET, "Modifier ''{0}'' is deprecated for ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_MODIFIER, "Modifier ''{0}'' is deprecated, use ''{1}'' instead", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_MODIFIER_FOR_TARGET, "Modifier ''{0}'' is redundant for ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NO_EXPLICIT_VISIBILITY_IN_API_MODE, "Visibility must be specified in explicit API mode");
        diagnosticFactoryToRendererMap2.put(Errors.NO_EXPLICIT_RETURN_TYPE_IN_API_MODE, "Return type must be specified in explicit API mode");
        diagnosticFactoryToRendererMap2.put(Errors.NO_EXPLICIT_VISIBILITY_IN_API_MODE_WARNING, "Visibility must be specified in explicit API mode");
        diagnosticFactoryToRendererMap2.put(Errors.NO_EXPLICIT_RETURN_TYPE_IN_API_MODE_WARNING, "Return type must be specified in explicit API mode");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_MODIFIER_CONTAINING_DECLARATION, "Modifier ''{0}'' is not applicable inside ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_MODIFIER_CONTAINING_DECLARATION, "Modifier ''{0}'' is deprecated inside ''{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_INLINE_PARAMETER_MODIFIER, "Modifier ''{0}'' is allowed only for function parameters of an inline function", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INLINE_SUSPEND_FUNCTION_TYPE_UNSUPPORTED, "Suspend inline lambda parameters of non-suspend function type are not supported. Add 'noinline' or 'crossinline' modifier.");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE, "Redundant 'suspend' modifier: lambda parameters of suspend function type uses existing continuation.");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_ANNOTATION_TARGET, "This annotation is not applicable to target ''{0}''", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET, "This annotation is not applicable to target ''{0}'' and use site target ''@{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET_ON_TYPE, "Use of this annotation with target ''type'' and use site target ''@{0}'' is deprecated", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REPEATED_ANNOTATION, "This annotation is not repeatable");
        diagnosticFactoryToRendererMap2.put(Errors.REPEATED_ANNOTATION_WARNING, "This annotation is not repeatable");
        diagnosticFactoryToRendererMap2.put(Errors.NON_SOURCE_ANNOTATION_ON_INLINED_LAMBDA_EXPRESSION, "The lambda expression here is an inlined argument so this annotation cannot be stored anywhere");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_EXTENSION_FUNCTION_TYPE, "ExtensionFunctionType is forbidden on a function type without parameters or on a non-function type");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_EXTENSION_FUNCTION_TYPE_WARNING, "ExtensionFunctionType makes no sense on a non-function type. It will be an error in a future release. See https://youtrack.jetbrains.com/issue/KT-43527");
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_TARGET_ON_PROPERTY, "''@{0}:'' annotations could be applied only to property declarations", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_TARGET_ON_PROPERTY_WARNING, "''@{0}:'' annotations could be applied only to property declarations. It will be an error in a future release. See https://youtrack.jetbrains.com/issue/KT-15470", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_TARGET_PROPERTY_IMMUTABLE, "''@{0}:'' annotations could be applied only to mutable properties", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_TARGET_PROPERTY_HAS_NO_DELEGATE, "'@delegate:' annotations could be applied only to delegated properties");
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_TARGET_PROPERTY_HAS_NO_BACKING_FIELD, "'@field:' annotations could be applied only to properties with backing fields");
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_PARAM_TARGET, "'@param:' annotations could be applied only to primary constructor parameters");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_ANNOTATION_TARGET, "Redundant annotation target ''{0}''", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_FILE_TARGET, "'@file:' annotations can only be applied before package declaration");
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_KOTLIN_VERSION_STRING_VALUE, "Invalid @{0} annotation value (should be ''major.minor'' or ''major.minor.patch'')", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NEWER_VERSION_IN_SINCE_KOTLIN, "The version is greater than the specified API version {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_USAGE, "{1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_USAGE_ERROR, "{1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_USAGE_FUTURE_ERROR, "{1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_OVERRIDE, "{1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_OVERRIDE_ERROR, "{1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_IS_NOT_ENABLED, "This annotation should be used with the compiler argument '-opt-in=kotlin.RequiresOptIn'");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION, "This class can only be used as an annotation");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_CAN_ONLY_BE_USED_AS_ANNOTATION_OR_ARGUMENT_IN_OPT_IN, "This class can only be used as an annotation or as an argument to @OptIn");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_WITHOUT_ARGUMENTS, "@OptIn without any arguments has no effect");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_ARGUMENT_IS_NOT_MARKER, "Annotation ''{0}'' is not an opt-in requirement marker, therefore its usage in @OptIn is ignored", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_WITH_WRONG_TARGET, "Opt-in requirement marker annotation cannot be used on the following code elements: {0}. Please remove these targets", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_WITH_WRONG_RETENTION, "Opt-in requirement marker annotation cannot be used with SOURCE retention. Please replace retention with BINARY");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_ON_WRONG_TARGET, "Opt-in requirement marker annotation cannot be used on {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_ON_OVERRIDE, "Opt-in requirement marker annotation on override requires the same marker on base declaration");
        diagnosticFactoryToRendererMap2.put(Errors.OPT_IN_MARKER_ON_OVERRIDE_WARNING, "Opt-in requirement marker annotation on override makes no sense without the same marker on base declaration");
        diagnosticFactoryToRendererMap2.put(Errors.SUBCLASS_OPT_IN_INAPPLICABLE, "@SubclassOptInRequired is inapplicable on {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPERIMENTAL_UNSIGNED_LITERALS, "{0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPERIMENTAL_UNSIGNED_LITERALS_ERROR, "{0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NON_PARENTHESIZED_ANNOTATIONS_ON_FUNCTIONAL_TYPES, "Non-parenthesized annotations on function types without receiver aren't yet supported (see KT-31734 for details)");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_IN_WHERE_CLAUSE_WARNING, "Type parameter annotations will not be allowed inside where clauses in future releases. You should probably move annotations to the type parameter declaration");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_MODIFIER, "Modifier ''{0}'' is redundant because ''{1}'' is present", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_OPEN_IN_INTERFACE, "Modifier 'open' is redundant for abstract interface members");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_MODIFIER_IN_GETTER, "Visibility modifiers are redundant in getter");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETERS_IN_ENUM, "Enum class cannot have type parameters");
        diagnosticFactoryToRendererMap2.put(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM, "Type checking has run into a recursive problem. Easiest workaround: specify types of your declarations explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM_IN_AUGMENTED_ASSIGNMENT, "Type checking has run into a recursive problem. Easiest workaround: specify types of your declarations explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_NOT_ALLOWED, "'return' is not allowed here");
        diagnosticFactoryToRendererMap2.put(Errors.PROJECTION_IN_IMMEDIATE_ARGUMENT_TO_SUPERTYPE, "Projections are not allowed for immediate arguments of a supertype");
        diagnosticFactoryToRendererMap2.put(Errors.LABEL_NAME_CLASH, "There is more than one label with such a name in this scope");
        diagnosticFactoryToRendererMap2.put(Errors.LABEL_RESOLVE_WILL_CHANGE, "This label is now resolved to ''{0}'' but soon it will be resolved to the closest ''{1}''. Please consider introducing or changing explicit label name", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPRESSION_EXPECTED_PACKAGE_FOUND, "Expression expected, but a package name found");
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_ALL_UNDER_IMPORT_FROM_SINGLETON, "Cannot import-on-demand from object ''{0}''", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_BE_IMPORTED, "Cannot import ''{0}'', functions and properties can be imported only from packages or objects", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.PACKAGE_CANNOT_BE_IMPORTED, "Packages cannot be imported");
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_IMPORT, "Conflicting import, imported name ''{0}'' is ambiguous", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPERATOR_RENAMED_ON_IMPORT, "Operator renamed to a different operator on import");
        diagnosticFactoryToRendererMap2.put(Errors.PLATFORM_CLASS_MAPPED_TO_KOTLIN, "This class shouldn''t be used in Kotlin. Use {0} instead.", Renderers.CLASSES_OR_SEPARATED);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_INFER_PARAMETER_TYPE, "Cannot infer a type for this parameter. Please specify it explicitly.");
        diagnosticFactoryToRendererMap2.put(Errors.MIXING_NAMED_AND_POSITIONED_ARGUMENTS, "Mixing named and positioned arguments is not allowed");
        diagnosticFactoryToRendererMap2.put(Errors.ARGUMENT_PASSED_TWICE, "An argument is already passed for this parameter");
        diagnosticFactoryToRendererMap2.put(Errors.NAMED_PARAMETER_NOT_FOUND, "Cannot find a parameter with this name: {0}", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.NAMED_ARGUMENTS_NOT_ALLOWED, "Named arguments are not allowed for {0}", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$2((Errors.BadNamedArgumentsTarget) obj, renderingContext);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.VARARG_OUTSIDE_PARENTHESES, "Passing value as a vararg is only allowed inside a parenthesized argument list");
        diagnosticFactoryToRendererMap2.put(Errors.NON_VARARG_SPREAD, "The spread operator (*foo) may only be applied in a vararg position. See https://youtrack.jetbrains.com/issue/KT-48162 for more details.");
        diagnosticFactoryToRendererMap2.put(Errors.SPREAD_OF_NULLABLE, "The spread operator (*foo) may not be applied to an argument of nullable type");
        diagnosticFactoryToRendererMap2.put(Errors.SPREAD_OF_LAMBDA_OR_CALLABLE_REFERENCE, "The spread operator (*foo) cannot be applied to lambda argument or callable reference");
        diagnosticFactoryToRendererMap2.put(Errors.MANY_LAMBDA_EXPRESSION_ARGUMENTS, "Only one lambda expression is allowed outside a parenthesized argument list");
        diagnosticFactoryToRendererMap2.put(Errors.UNEXPECTED_TRAILING_LAMBDA_ON_A_NEW_LINE, "Expression is treated as a trailing lambda argument; consider separating it from call with semicolon");
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_WITH_NO_TYPE_NO_INITIALIZER, "This property must either have a type annotation, be initialized or be delegated");
        diagnosticFactoryToRendererMap2.put(Errors.VARIABLE_WITH_NO_TYPE_NO_INITIALIZER, "This variable must either have a type annotation or be initialized");
        diagnosticFactoryToRendererMap2.put(Errors.INITIALIZER_REQUIRED_FOR_DESTRUCTURING_DECLARATION, "Initializer required for destructuring declaration");
        diagnosticFactoryToRendererMap2.put(Errors.COMPONENT_FUNCTION_MISSING, "Destructuring declaration initializer of type {1} must have a ''{0}()'' function", Renderers.TO_STRING, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.COMPONENT_FUNCTION_ON_NULLABLE, "Not nullable value required to call ''{0}()'' function of destructuring declaration initializer", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.COMPONENT_FUNCTION_AMBIGUITY, "Function ''{0}()'' is ambiguous for this expression: {1}", Renderers.TO_STRING, Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.COMPONENT_FUNCTION_RETURN_TYPE_MISMATCH, "''{0}()'' function returns ''{1}'', but ''{2}'' is expected", Renderers.TO_STRING, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_PROPERTY_IN_PRIMARY_CONSTRUCTOR_PARAMETERS, "Property in primary constructor cannot be declared abstract");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_PROPERTY_WITH_INITIALIZER, "Property with initializer cannot be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_PROPERTY_WITH_GETTER, "Property with getter implementation cannot be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_PROPERTY_WITH_SETTER, "Property with setter implementation cannot be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_DELEGATED_PROPERTY, "Delegated property cannot be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.ACCESSOR_FOR_DELEGATED_PROPERTY, "Delegated property cannot have accessors with non-default implementations");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATED_PROPERTY_IN_INTERFACE, "Delegated properties are not allowed in interfaces");
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_LATEINIT_MODIFIER, "''lateinit'' modifier {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LITERAL, "This declaration can only be called on a property literal (e.g. 'Foo::bar')");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT, "This declaration can only be called on a reference to a lateinit property");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION, "This declaration can not be used inside an inline function");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY, "Backing field of ''{0}'' is not accessible at this point", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LITERAL_WARNING, "This declaration can only be called on a property literal (e.g. 'Foo::bar'). This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_LATEINIT_WARNING, "This declaration can only be called on a reference to a lateinit property. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_IN_INLINE_FUNCTION_WARNING, "This declaration can not be used inside an inline function. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_INTRINSIC_CALL_ON_NON_ACCESSIBLE_PROPERTY_WARNING, "Backing field of ''{0}'' is not accessible at this point. This warning will become an error in future releases.", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.GETTER_VISIBILITY_DIFFERS_FROM_PROPERTY_VISIBILITY, "Getter visibility must be the same as property visibility");
        diagnosticFactoryToRendererMap2.put(Errors.SETTER_VISIBILITY_INCONSISTENT_WITH_PROPERTY_VISIBILITY, "Setter visibility must be the same or less permissive than property visibility");
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_SETTER_FOR_ABSTRACT_PROPERTY, "Private setters are not allowed for abstract properties");
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_SETTER_FOR_OPEN_PROPERTY, "Private setters are not allowed for open properties");
        diagnosticFactoryToRendererMap2.put(Errors.BACKING_FIELD_IN_INTERFACE, "Property in an interface cannot have a backing field");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED, "Property must be initialized");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_WARNING, "Property must be initialized. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_BE_FINAL, "Property must be initialized or be final");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_BE_FINAL_WARNING, "Property must be initialized or be final. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_BE_ABSTRACT, "Property must be initialized or be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_BE_ABSTRACT_WARNING, "Property must be initialized or be abstract. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT, "Property must be initialized, be final, or be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.MUST_BE_INITIALIZED_OR_FINAL_OR_ABSTRACT_WARNING, "Property must be initialized, be final, or be abstract. This warning will become an error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_PROPERTY_MUST_HAVE_ACCESSORS_OR_BE_ABSTRACT, "Extension property must have accessors or be abstract");
        diagnosticFactoryToRendererMap2.put(Errors.UNNECESSARY_LATEINIT, "Lateinit is unnecessary: definitely initialized in constructors");
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_INITIALIZER_IN_INTERFACE, "Property initializers are not allowed in interfaces");
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_PROPERTY_IN_INTERFACE, "Abstract property in an interface cannot be private");
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_PROPERTY_WITH_BACKING_FIELD, "Extension property cannot be initialized because it has no backing field");
        diagnosticFactoryToRendererMap2.put(Errors.CONTEXT_RECEIVERS_WITH_BACKING_FIELD, "Property with context receivers cannot be initialized because it has no backing field");
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_INITIALIZER_NO_BACKING_FIELD, "Initializer is not allowed here because this property has no backing field");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_PROPERTY_IN_NON_ABSTRACT_CLASS, "Abstract property ''{0}'' in non-abstract class ''{1}''", CommonRenderers.STRING, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_FUNCTION_IN_NON_ABSTRACT_CLASS, "Abstract function ''{0}'' in non-abstract class ''{1}''", CommonRenderers.STRING, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_FUNCTION_WITH_BODY, "A function ''{0}'' with body cannot be abstract", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NON_ABSTRACT_FUNCTION_WITH_NO_BODY, "Function ''{0}'' without a body must be abstract", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_FUNCTION_WITH_NO_BODY, "Function ''{0}'' without body cannot be private", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NON_MEMBER_FUNCTION_NO_BODY, "Function ''{0}'' must have a body", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.FUNCTION_DECLARATION_WITH_NO_NAME, "Function declaration must have a name");
        diagnosticFactoryToRendererMap2.put(Errors.ANONYMOUS_FUNCTION_WITH_NAME, "Anonymous functions with names are prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.NON_FINAL_MEMBER_IN_FINAL_CLASS, "'open' has no effect in a final class");
        diagnosticFactoryToRendererMap2.put(Errors.NON_FINAL_MEMBER_IN_OBJECT, "'open' has no effect in an object");
        diagnosticFactoryToRendererMap2.put(Errors.ANONYMOUS_FUNCTION_PARAMETER_WITH_DEFAULT_VALUE, "An anonymous function is not allowed to specify default values for its parameters");
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_VARARG_ON_PARAMETER, "Vararg on this parameter is useless");
        diagnosticFactoryToRendererMap2.put(Errors.MULTIPLE_VARARG_PARAMETERS, "Multiple vararg-parameters are prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.FORBIDDEN_VARARG_PARAMETER_TYPE, "Forbidden vararg parameter type: {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.CHANGING_ARGUMENTS_EXECUTION_ORDER_FOR_NAMED_VARARGS, "Arguments execution order is going to be changed in a future release. The expression for named vararg argument will be executed in the order in which it was listed, not at the end. See KT-17691 for more details.");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_DECLARATION_WITH_BODY, "Expected declaration must not have a body");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_CLASS_CONSTRUCTOR_DELEGATION_CALL, "Explicit delegation call for constructor of an expected class is not allowed");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_CLASS_CONSTRUCTOR_PROPERTY_PARAMETER, "Expected class constructor cannot have a property parameter");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_ENUM_CONSTRUCTOR, "Expected enum class cannot have a constructor");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_ENUM_ENTRY_WITH_BODY, "Expected enum entry cannot have a body");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_PROPERTY_INITIALIZER, "Expected property cannot have an initializer");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_DELEGATED_PROPERTY, "Expected property cannot be delegated");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_LATEINIT_PROPERTY, "Expected property cannot be lateinit");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_INITIALIZED_IN_EXPECTED_CLASS, "Expected classes cannot initialize supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_PRIVATE_DECLARATION, "Expected declaration cannot be private");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_EXTERNAL_DECLARATION, "Expected declaration cannot be external");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_TAILREC_FUNCTION, "Expected function cannot have 'tailrec' modifier");
        diagnosticFactoryToRendererMap2.put(Errors.IMPLEMENTATION_BY_DELEGATION_IN_EXPECT_CLASS, "Implementation by delegation in expected classes is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_NOT_TO_CLASS, "Right-hand side of actual type alias should be a class, not another type alias");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_TO_CLASS_WITH_DECLARATION_SITE_VARIANCE, "Aliased class should not have type parameters with declaration-site variance");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_WITH_USE_SITE_VARIANCE, "Right-hand side of actual type alias cannot contain use-site variance or star projections");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_WITH_COMPLEX_SUBSTITUTION, "Type arguments in the right-hand side of actual type alias should be its type parameters in the same order, e.g. 'actual typealias Foo<A, B> = Bar<A, B>'");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_TO_NULLABLE_TYPE, "Right-hand side of actual type alias cannot be a nullable type");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPE_ALIAS_TO_NOTHING, "Right-hand side of actual type alias cannot be of type kotlin.Nothing");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS, "Actual function cannot have default argument values, they should be declared in the expected function");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_ANNOTATION_CONFLICTING_DEFAULT_ARGUMENT_VALUE, "Parameter ''{0}'' has conflicting values in the expected and actual annotation", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DEFAULT_ARGUMENTS_IN_EXPECT_WITH_ACTUAL_TYPEALIAS, "Default argument values inside expect declaration ''{0}'' are not allowed if it is actualized via typealias. Possible fix is to remove default argument values in members:{1}", Renderers.NAME, Renderers.DESCRIPTORS_ON_NEWLINE_WITH_INDENT);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_FUNCTION_SOURCE_WITH_DEFAULT_ARGUMENTS_NOT_FOUND, "Expected function source is not found, therefore it's impossible to generate default argument values declared there. Please add the corresponding file to compilation sources");
        diagnosticFactoryToRendererMap2.put(Errors.NO_ACTUAL_FOR_EXPECT, "Expected {0} has no actual declaration in module {1}{2}", Renderers.DECLARATION_NAME_WITH_KIND, Renderers.MODULE_WITH_PLATFORM, adaptGenerics1(PlatformIncompatibilityDiagnosticRenderer.TEXT));
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_JVM_ACTUALIZATION, "Expected {0} is implicitly actualized by Java declaration in module {1}. Please migrate to explicit ''actual typealias''. See: https://youtrack.jetbrains.com/issue/KT-58545", Renderers.DECLARATION_NAME_WITH_KIND, Renderers.MODULE_WITH_PLATFORM);
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_WITHOUT_EXPECT, "{0} has no corresponding expected declaration{1}", Renderers.CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM, adaptGenerics1(PlatformIncompatibilityDiagnosticRenderer.TEXT));
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_ACTUALS, "{0} has several compatible actual declarations in modules {1}", Renderers.CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM, CommonRenderers.commaSeparated(Renderers.MODULE));
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_EXPECTS, "{0} has several compatible expect declarations in modules {1}", Renderers.CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM, CommonRenderers.commaSeparated(Renderers.MODULE));
        diagnosticFactoryToRendererMap2.put(Errors.NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS, "Actual class ''{0}'' has no corresponding members for expected class members:{1}", Renderers.NAME, adaptGenerics2(IncompatibleExpectedActualClassScopesRenderer.TEXT));
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_MISSING, "Declaration must be marked with 'actual'");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE, "{0}: expect and corresponding actual are declared in the same module, which will be prohibited in Kotlin 2.0. See https://youtrack.jetbrains.com/issue/KT-55177", Renderers.CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM);
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING, "{0}: actual class and its non-final expect class must declare exactly the same non-private members. The following non-private members in actual class are mismatched:{1}\nThis error happens because the expect class ''{2}'' is non-final. This warning will become an error in future releases.\nAlso see https://youtrack.jetbrains.com/issue/KT-22841 for more details", Renderers.CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM, ExpectActualScopeDiffsRenderer.TEXT, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNKNOWN_PROBLEM_DURING_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_TYPE_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.MODALITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.VISIBILITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.PARAMETER_NAME_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_KIND_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.LATEINIT_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.VARARG_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.SETTER_VISIBILITY_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_NAMES_CHANGED_IN_NON_FINAL_EXPECT_CLASSIFIER_ACTUALIZATION_WARNING, "{0}. This warning will become an error in future releases. Also see https://youtrack.jetbrains.com/issue/KT-22841 for more details", ExpectActualScopeDiffRenderer.INSTANCE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING, "'expect'/'actual' classes (including interfaces, objects, annotations, enums, and 'actual' typealiases) are in Beta. You can use -Xexpect-actual-classes flag to suppress this warning. Also see: https://youtrack.jetbrains.com/issue/KT-61573");
        diagnosticFactoryToRendererMap2.put(Errors.OPTIONAL_EXPECTATION_NOT_ON_EXPECTED, "'@OptionalExpectation' can only be used on an expected annotation class");
        diagnosticFactoryToRendererMap2.put(Errors.OPTIONAL_DECLARATION_OUTSIDE_OF_ANNOTATION_ENTRY, "Declaration annotated with '@OptionalExpectation' can only be used inside an annotation entry");
        diagnosticFactoryToRendererMap2.put(Errors.OPTIONAL_DECLARATION_USAGE_IN_NON_COMMON_SOURCE, "Declaration annotated with '@OptionalExpectation' can only be used in common module sources");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_MULTIPLATFORM_COMPILATION, "'expect' and 'actual' declarations can be used only in multiplatform projects. Learn more about Kotlin Multiplatform: https://kotl.in/multiplatform-setup");
        diagnosticFactoryToRendererMap2.put(Errors.EXPECT_ACTUAL_OPT_IN_ANNOTATION, "Opt-in annotations are prohibited to be `expect` or `actual`. Instead, declare annotation once in common sources.");
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_TYPEALIAS_TO_SPECIAL_ANNOTATION, "`actual typealias` to annotation which affects code compilation can lead to incorrect behavior. Instead, use ''{0}'' annotation directly.", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT, "{2}.\nAll annotations from expect `{0}` must be present with the same arguments on actual `{1}`, otherwise they might behave incorrectly.", ExpectActualAnnotationIncompatibilityDiagnosticRenderers.DESCRIPTOR_RENDERER, ExpectActualAnnotationIncompatibilityDiagnosticRenderers.DESCRIPTOR_RENDERER, ExpectActualAnnotationIncompatibilityDiagnosticRenderers.INCOMPATIBILITY);
        diagnosticFactoryToRendererMap2.put(Errors.PROJECTION_ON_NON_CLASS_TYPE_ARGUMENT, "Projections are not allowed on type arguments of functions and properties");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_NOT_INITIALIZED, "This type has a constructor, and thus must be initialized here");
        diagnosticFactoryToRendererMap2.put(Errors.NOTHING_TO_OVERRIDE, "''{0}'' overrides nothing", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.VIRTUAL_MEMBER_HIDDEN, "''{0}'' hides member of supertype ''{2}'' and needs ''override'' modifier", Renderers.NAME, Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_OVERRIDE_CONFLICT, "Function ''{0}'' generated for the data class conflicts with member of supertype ''{1}''", Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_OVERRIDE_DEFAULT_VALUES, "Function ''{0}'' generated for the data class has default values for parameters, and conflicts with member of supertype ''{1}''", Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_OVERRIDE_INVISIBLE_MEMBER, "''{0}'' has no access to ''{1}'', so it cannot override it", Renderers.FQ_NAMES_IN_TYPES, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_INFER_VISIBILITY, "Cannot infer visibility for ''{0}''. Please specify it explicitly", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.ENUM_ENTRY_SHOULD_BE_INITIALIZED, "Enum has no default constructor, use 'entry(parameters)'");
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_VARIABLE, "Variable ''{0}'' must be initialized", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_PARAMETER, "Parameter ''{0}'' is uninitialized here", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_PARAMETER_WARNING, "Parameter ''{0}'' is uninitialized here. This warning will be an error in future releases", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_ENUM_ENTRY, "Enum entry ''{0}'' is uninitialized here", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_ENUM_COMPANION, "Companion object of enum class ''{0}'' is uninitialized here", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_VARIABLE, "Variable ''{0}'' is never used", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNINITIALIZED_ENUM_COMPANION_WARNING, "Companion object of enum class ''{0}'' is uninitialized here. This warning will become an error in future releases. See https://youtrack.jetbrains.com/issue/KT-49110 for details", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_PARAMETER, "Parameter ''{0}'' is never used", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_ANONYMOUS_PARAMETER, "Parameter ''{0}'' is never used, could be renamed to _", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_DESTRUCTURED_PARAMETER_ENTRY, "Destructured parameter ''{0}'' is never used", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE, "Variable ''{0}'' is assigned but never accessed", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.VARIABLE_WITH_REDUNDANT_INITIALIZER, "Variable ''{0}'' initializer is redundant", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_VALUE, "The value ''{0}'' assigned to ''{1}'' is never used", Renderers.ELEMENT_TEXT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_CHANGED_VALUE, "The value changed at ''{0}'' is never used", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_EXPRESSION, "The expression is unused");
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_LAMBDA_EXPRESSION, "The lambda expression is unused. If you mean a block, you can use 'run { ... }'");
        diagnosticFactoryToRendererMap2.put(Errors.VAL_REASSIGNMENT, "Val cannot be reassigned", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.VAL_REASSIGNMENT_VIA_BACKING_FIELD, "Reassignment of read-only property via backing field", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CAPTURED_VAL_INITIALIZATION, "Captured values initialization is forbidden due to possible reassignment", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CAPTURED_MEMBER_VAL_INITIALIZATION, "Captured member values initialization is forbidden due to possible reassignment", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.SETTER_PROJECTED_OUT, "Setter for ''{0}'' is removed by type projection", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.INVISIBLE_SETTER, "Cannot assign to ''{0}'': the setter is {1} in {2}", Renderers.NAME, Renderers.VISIBILITY, Renderers.NAME_OF_CONTAINING_DECLARATION_OR_FILE);
        diagnosticFactoryToRendererMap2.put(Errors.INVISIBLE_SETTER_FROM_DERIVED, "Cannot assign to ''{0}'': the setter is {1} in {2}. This warning will be an error soon. See https://youtrack.jetbrains.com/issue/KT-56662 for details", Renderers.NAME, Renderers.VISIBILITY, Renderers.NAME_OF_CONTAINING_DECLARATION_OR_FILE);
        diagnosticFactoryToRendererMap2.put(Errors.INITIALIZATION_BEFORE_DECLARATION, "Variable cannot be initialized before declaration", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.VARIABLE_EXPECTED, "Variable expected");
        diagnosticFactoryToRendererMap2.put(Errors.VAL_OR_VAR_ON_LOOP_PARAMETER, "''{0}'' on loop parameter is not allowed", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.VAL_OR_VAR_ON_FUN_PARAMETER, "''{0}'' on function parameter is not allowed", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.VAL_OR_VAR_ON_CATCH_PARAMETER, "''{0}'' on catch parameter is not allowed", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.VAL_OR_VAR_ON_SECONDARY_CONSTRUCTOR_PARAMETER, "''{0}'' on secondary constructor parameter is not allowed", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UNREACHABLE_CODE, "Unreachable code", CommonRenderers.EMPTY, CommonRenderers.EMPTY);
        diagnosticFactoryToRendererMap2.put(Errors.MANY_COMPANION_OBJECTS, "Only one companion object is allowed per class");
        diagnosticFactoryToRendererMap2.put(Errors.SELF_CALL_IN_NESTED_OBJECT_CONSTRUCTOR, "Self references to members of containing class are prohibited in constructor of nested object");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATION, "''{0}'' is deprecated. {1}", Renderers.DEPRECATION_RENDERER, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATION_ERROR, "Using ''{0}'' is an error. {1}", Renderers.DEPRECATION_RENDERER, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPEALIAS_EXPANSION_DEPRECATION, "''{0}'' uses ''{1}'', which is deprecated. {2}", Renderers.DEPRECATION_RENDERER, Renderers.DEPRECATION_RENDERER, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPEALIAS_EXPANSION_DEPRECATION_ERROR, "''{0}'' uses ''{1}'', which is an error. {2}", Renderers.DEPRECATION_RENDERER, Renderers.DEPRECATION_RENDERER, CommonRenderers.STRING);
        DiagnosticParameterRenderer diagnosticParameterRenderer = new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$3((Pair) obj, renderingContext);
            }
        };
        diagnosticFactoryToRendererMap2.put(Errors.VERSION_REQUIREMENT_DEPRECATION, "''{0}''{1} should not be used in Kotlin {2}", Renderers.DEPRECATION_RENDERER, new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$4((VersionRequirement.Version) obj, renderingContext);
            }
        }, diagnosticParameterRenderer);
        diagnosticFactoryToRendererMap2.put(Errors.VERSION_REQUIREMENT_DEPRECATION_ERROR, "''{0}''{1} cannot be used in Kotlin {2}", Renderers.DEPRECATION_RENDERER, new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$5((VersionRequirement.Version) obj, renderingContext);
            }
        }, diagnosticParameterRenderer);
        diagnosticFactoryToRendererMap2.put(Errors.OVERRIDE_DEPRECATION, "This declaration overrides deprecated member but not marked as deprecated itself. {0}Please add @Deprecated annotation or suppress. See https://youtrack.jetbrains.com/issue/KT-47902 for details", Renderers.TO_STRING, CommonRenderers.EMPTY, CommonRenderers.EMPTY);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SINCE_KOTLIN_WITHOUT_DEPRECATED, "DeprecatedSinceKotlin annotation can be used only together with Deprecated annotation");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SINCE_KOTLIN_WITH_DEPRECATED_LEVEL, "DeprecatedSinceKotlin annotation can be used only with unspecified deprecation level of Deprecated annotation");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SINCE_KOTLIN_WITH_UNORDERED_VERSIONS, "Values of DeprecatedSinceKotlin annotation should be ordered so 'warningSince' <= 'errorSince' <= 'hiddenSince' if specified");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SINCE_KOTLIN_WITHOUT_ARGUMENTS, "DeprecatedSinceKotlin annotation should have at least one argument");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SINCE_KOTLIN_OUTSIDE_KOTLIN_SUBPACKAGE, "DeprecatedSinceKotlin annotation cannot be used outside 'kotlin' subpackages");
        diagnosticFactoryToRendererMap2.put(Errors.API_NOT_AVAILABLE, "This declaration is only available since Kotlin {0} and cannot be used with the specified API version {1}", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_DEPENDENCY_CLASS, "Cannot access class ''{0}''. Check your module classpath for missing or conflicting dependencies", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_DEPENDENCY_SUPERCLASS, "Cannot access ''{0}'' which is a supertype of ''{1}''. Check your module classpath for missing or conflicting dependencies", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_BUILT_IN_DECLARATION, "Cannot access built-in declaration ''{0}''. Ensure that you have a dependency on the Kotlin standard library", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_SCRIPT_BASE_CLASS, "Cannot access script base class ''{0}''. Check your module classpath for missing or conflicting dependencies", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_SCRIPT_STANDARD_TEMPLATE, "No script runtime was found in the classpath: class ''{0}'' not found. Please add kotlin-script-runtime.jar to the module dependencies.", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_SCRIPT_RECEIVER_CLASS, "Cannot access implicit script receiver class ''{0}''. Check your module classpath for missing or conflicting dependencies", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_IMPORTED_SCRIPT_FILE, "Cannot find imported script file ''{0}''. Check your script imports", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_IMPORTED_SCRIPT_PSI, "Imported script file ''{0}'' is not loaded. Check your script imports", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_SCRIPT_PROVIDED_PROPERTY_CLASS, "Cannot access script provided property class ''{0}''. Check your module classpath for missing or conflicting dependencies", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.PRE_RELEASE_CLASS, "{0} is compiled by a pre-release version of Kotlin and cannot be loaded by this version of the compiler", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.IR_WITH_UNSTABLE_ABI_COMPILED_CLASS, "{0} is compiled by an unstable version of the Kotlin compiler and cannot be loaded by this compiler", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.FIR_COMPILED_CLASS, "{0} is compiled by the new Kotlin compiler frontend and cannot be loaded by the old compiler", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INCOMPATIBLE_CLASS, "{0} was compiled with an incompatible version of Kotlin. {1}", Renderers.TO_STRING, new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$6((IncompatibleVersionErrorData) obj, renderingContext);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_OBJECT_NOT_ALLOWED, "Named object ''{0}'' is a singleton and cannot be local. Try to use anonymous object instead", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_INTERFACE_NOT_ALLOWED, "''{0}'' is an interface so it cannot be local. Try to use anonymous object or abstract class instead", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETERS_IN_OBJECT, "Type parameters are not allowed for objects");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETERS_IN_ANONYMOUS_OBJECT, "Type parameters for anonymous objects are deprecated");
        diagnosticFactoryToRendererMap2.put(Errors.ENUM_CLASS_CONSTRUCTOR_CALL, "Enum types cannot be instantiated");
        diagnosticFactoryToRendererMap2.put(Errors.SEALED_CLASS_CONSTRUCTOR_CALL, "Sealed types cannot be instantiated");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATION_IN_INTERFACE, "Interfaces cannot use delegation");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATION_NOT_TO_INTERFACE, "Only interfaces can be delegated to");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE, "Delegated member ''{0}'' hides supertype override: {1}. Please specify proper override explicitly", Renderers.COMPACT, CommonRenderers.commaSeparated(Renderers.SHORT_NAMES_IN_TYPES));
        diagnosticFactoryToRendererMap2.put(Errors.NO_CONSTRUCTOR, "This class does not have a constructor");
        diagnosticFactoryToRendererMap2.put(Errors.NO_CONSTRUCTOR_WARNING, "This class does not have a constructor. This warning will be an error in future releases");
        diagnosticFactoryToRendererMap2.put(Errors.RESOLUTION_TO_CLASSIFIER, "{2}", Renderers.NAME, Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_CLASS, "Not a class");
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_ESCAPE_SEQUENCE, "Illegal escape sequence");
        diagnosticFactoryToRendererMap2.put(Errors.UNSIGNED_LITERAL_WITHOUT_DECLARATIONS_ON_CLASSPATH, "Type of the constant expression cannot be resolved. Please make sure you have the required dependencies for unsigned types in the classpath");
        diagnosticFactoryToRendererMap2.put(Errors.SIGNED_CONSTANT_CONVERTED_TO_UNSIGNED, "Conversion of signed constants to unsigned ones is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.INTEGER_OPERATOR_RESOLVE_WILL_CHANGE, "This expression will be resolved to {0} in future releases. Please add explicit conversion call", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NON_TRIVIAL_BOOLEAN_CONSTANT, "Compiler won''t reduce this expression to {0} in future. Please replace it with a boolean literal. See https://youtrack.jetbrains.com/issue/KT-39883 for details", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.RESERVED_SYNTAX_IN_CALLABLE_REFERENCE_LHS, "Left-hand side of callable reference matches expression syntax reserved for future releases");
        diagnosticFactoryToRendererMap2.put(Errors.PARENTHESIZED_COMPANION_LHS_DEPRECATION, "Access to companion object through parenthesized class name is deprecated. Please, add explicit Companion qualifier.");
        diagnosticFactoryToRendererMap2.put(Errors.INCORRECT_CALLABLE_REFERENCE_RESOLUTION_FOR_COMPANION_LHS, "Callable reference to the companion's member is incorrectly resolved as unbound. Please, add explicit Companion qualifier to the left-hand-side. See https://youtrack.jetbrains.com/issue/KT-54316 for details");
        diagnosticFactoryToRendererMap2.put(Errors.RESOLUTION_TO_PRIVATE_CONSTRUCTOR_OF_SEALED_CLASS, "The private constructor of a sealed class will become inaccessible here in future. See https://youtrack.jetbrains.com/issue/KT-44866 for details");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_WARNING_FOR_INCORRECT_CAPTURE_APPROXIMATION, "Type mismatch: inferred type is {1} but {0} was expected. This warning will be an error soon. See https://youtrack.jetbrains.com/issue/KT-49404 for details", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.RECEIVER_TYPE_MISMATCH_WARNING_FOR_INCORRECT_CAPTURE_APPROXIMATION, "Extension receiver type mismatch: inferred type is {1} but {0} was expected. This warning will be an error soon. See https://youtrack.jetbrains.com/issue/KT-49404 for details", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.RECEIVER_TYPE_MISMATCH, "Constraint error in receiver type argument: inferred type is {1} but {0} was expected", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_IN_CONSTRAINT, "Type mismatch in constraint system: actual type is {1} but {0} was expected. Constraint position is {2}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_EXTENSION_PROPERTY, "Local extension properties are not allowed");
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_VARIABLE_WITH_GETTER, "Local variables are not allowed to have getters");
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_VARIABLE_WITH_SETTER, "Local variables are not allowed to have setters");
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_VARIABLE_WITH_TYPE_PARAMETERS_WARNING, "Type parameters for local variables are deprecated");
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_VARIABLE_WITH_TYPE_PARAMETERS, "Local variables are not allowed to have type parameters");
        diagnosticFactoryToRendererMap2.put(Errors.VAL_WITH_SETTER, "A 'val'-property cannot have a setter");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_IDENTITY_EQUALS, "Identity equality for arguments of types {0} and {1} is deprecated", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_BOXING_IN_IDENTITY_EQUALS, "Identity equality for arguments of types {0} and {1} can be unstable because of implicit boxing", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.FORBIDDEN_IDENTITY_EQUALS, "Identity equality for arguments of types {0} and {1} is forbidden", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.FORBIDDEN_SYNCHRONIZED_BY_VALUE_CLASSES_OR_PRIMITIVES, "Synchronizing by {0} is forbidden", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_BINARY_MOD, "Deprecated convention for ''{0}''. Use ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.FORBIDDEN_BINARY_MOD, "Convention for ''{0}'' is forbidden. Use ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_BINARY_MOD_AS_REM, "''%'' is resolved to deprecated ''{0}'' operator. Replace with ''.{0}'' or add operator ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.FORBIDDEN_BINARY_MOD_AS_REM, "''%'' is resolved to forbidden ''{0}'' operator. Replace with ''.{0}'' or add operator ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NO_GET_METHOD, "No get method providing array access");
        diagnosticFactoryToRendererMap2.put(Errors.NO_SET_METHOD, "No set method providing array access");
        diagnosticFactoryToRendererMap2.put(Errors.INC_DEC_SHOULD_NOT_RETURN_UNIT, "Functions inc(), dec() shouldn't return Unit to be used by operators ++, --");
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNMENT_OPERATOR_SHOULD_RETURN_UNIT, "Function ''{0}'' should return Unit to be used by corresponding operator ''{1}''", Renderers.NAME, Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGN_OPERATOR_AMBIGUITY, "Assignment operators ambiguity: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.EQUALS_MISSING, "No method 'equals(Any?): Boolean' available");
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNMENT_IN_EXPRESSION_CONTEXT, "Assignments are not expressions, and only expressions are allowed in this context");
        diagnosticFactoryToRendererMap2.put(Errors.SUPER_IS_NOT_AN_EXPRESSION, "''{0}'' is not an expression, it can only be used on the left-hand side of a dot (''.'')", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.SUPER_CANT_BE_EXTENSION_RECEIVER, "''{0}'' is not an expression, it can not be used as a receiver for extension functions", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DECLARATION_IN_ILLEGAL_CONTEXT, "Declarations are not allowed in this position");
        diagnosticFactoryToRendererMap2.put(Errors.SETTER_PARAMETER_WITH_DEFAULT_VALUE, "Setter parameters cannot have default values");
        diagnosticFactoryToRendererMap2.put(Errors.NO_THIS, "'this' is not defined in this context");
        diagnosticFactoryToRendererMap2.put(Errors.SUPER_NOT_AVAILABLE, "No supertypes are accessible in this context");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERCLASS_NOT_ACCESSIBLE_FROM_INTERFACE, "Superclass is not accessible from interface");
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_SUPER, "Many supertypes available, please specify the one you mean in angle brackets, e.g. 'super<Foo>'");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_SUPER_CALL, "Abstract member cannot be accessed directly");
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_SUPER_CALL_WARNING, "Abstract fake override member access is deprecated. See https://youtrack.jetbrains.com/issue/KT-49017");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_SUPERTYPE, "Not an immediate supertype");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_ARGUMENTS_REDUNDANT_IN_SUPER_QUALIFIER, "Type arguments do not need to be specified in a 'super' qualifier");
        diagnosticFactoryToRendererMap2.put(Errors.QUALIFIED_SUPERTYPE_EXTENDED_BY_OTHER_SUPERTYPE, "Explicitly qualified supertype is extended by another supertype ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_CAST, "No cast needed");
        diagnosticFactoryToRendererMap2.put(Errors.CAST_NEVER_SUCCEEDS, "This cast can never succeed");
        diagnosticFactoryToRendererMap2.put(Errors.DYNAMIC_NOT_ALLOWED, "Dynamic types are not allowed in this position");
        diagnosticFactoryToRendererMap2.put(Errors.IS_ENUM_ENTRY, "'is' over enum entry is not allowed, use comparison instead");
        diagnosticFactoryToRendererMap2.put(Errors.ENUM_ENTRY_AS_TYPE, "Use of enum entry names as types is not allowed, use enum type instead");
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_NULLABLE_CHECK, "Non-null type is checked for instance of nullable type");
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_IS_CHECK, "Check for instance is always ''{0}''", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_SETTER_PARAMETER_TYPE, "Setter parameter type must be equal to the type of the property, i.e. ''{0}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_USES_EXTENSION_PROPERTY_TYPE_PARAMETER, "It''s forbidden using extension property type parameter ''{0}'' in delegate. See https://youtrack.jetbrains.com/issue/KT-24643", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_GETTER_RETURN_TYPE, "Getter return type must be equal to the type of the property, i.e. ''{0}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_SETTER_RETURN_TYPE, "Setter return type must be Unit");
        diagnosticFactoryToRendererMap2.put(Errors.NO_COMPANION_OBJECT, "Classifier ''{0}'' does not have a companion object, and thus must be initialized here", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_IS_NOT_AN_EXPRESSION, "Type parameter ''{0}'' is not an expression", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_ON_LHS_OF_DOT, "Type parameter ''{0}'' cannot have or inherit a companion object, so it cannot be on the left hand side of dot", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NESTED_CLASS_ACCESSED_VIA_INSTANCE_REFERENCE, "Nested {0} accessed via instance reference", Renderers.RENDER_CLASS_OR_OBJECT_NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NESTED_CLASS_SHOULD_BE_QUALIFIED, "Nested {0} should be qualified as ''{1}''", Renderers.RENDER_CLASS_OR_OBJECT_NAME, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INACCESSIBLE_OUTER_CLASS_EXPRESSION, "Expression is inaccessible from a nested class ''{0}''", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NESTED_CLASS_NOT_ALLOWED, "{0} is not allowed here", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NESTED_CLASS_DEPRECATED, "{0} is deprecated here", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.HAS_NEXT_MISSING, "hasNext() cannot be called on iterator() of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.HAS_NEXT_FUNCTION_AMBIGUITY, "hasNext() is ambiguous for iterator() of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.HAS_NEXT_FUNCTION_NONE_APPLICABLE, "None of the hasNext() functions is applicable for iterator() of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.HAS_NEXT_FUNCTION_TYPE_MISMATCH, "The ''iterator().hasNext()'' function of the loop range must return Boolean, but returns {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NEXT_MISSING, "''next()'' cannot be called on ''iterator()'' of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NEXT_AMBIGUITY, "''next()'' is ambiguous for ''iterator()'' of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NEXT_NONE_APPLICABLE, "None of the ''next()'' functions is applicable for ''iterator()'' of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ITERATOR_MISSING, "For-loop range must have an 'iterator()' method");
        diagnosticFactoryToRendererMap2.put(Errors.ITERATOR_ON_NULLABLE, "Not nullable value required to call an 'iterator()' method on for-loop range");
        diagnosticFactoryToRendererMap2.put(Errors.ITERATOR_AMBIGUITY, "Method ''iterator()'' is ambiguous for this expression: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_SPECIAL_FUNCTION_MISSING, "Type ''{1}'' has no method ''{0}'' and thus it cannot serve as a {2}", CommonRenderers.STRING, Renderers.RENDER_TYPE, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_SPECIAL_FUNCTION_AMBIGUITY, "Overload resolution ambiguity on method ''{0}'': {1}", CommonRenderers.STRING, Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_SPECIAL_FUNCTION_NONE_APPLICABLE, "Property delegate must have a ''{0}'' method. None of the following functions is suitable: {1}", CommonRenderers.STRING, Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_SPECIAL_FUNCTION_RETURN_TYPE_MISMATCH, "The ''{0}'' function of property delegate is expected to return ''{1}'', but returns ''{2}''", CommonRenderers.STRING, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATE_PD_METHOD_NONE_APPLICABLE, "''{0}'' method may be missing. None of the following functions will be called: {1}", CommonRenderers.STRING, Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.COMPARE_TO_TYPE_MISMATCH, "''compareTo()'' must return Int, but returns {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UNDERSCORE_IS_RESERVED, "Names _, __, ___, ..., are reserved in Kotlin");
        diagnosticFactoryToRendererMap2.put(Errors.UNDERSCORE_USAGE_WITHOUT_BACKTICKS, "Names _, __, ___, ... can be used only in back-ticks (`_`, `__`, `___`, ...)");
        diagnosticFactoryToRendererMap2.put(Errors.RESOLVED_TO_UNDERSCORE_NAMED_CATCH_PARAMETER, "Referencing to an underscore-named parameter is deprecated. It will be an error in a future release.");
        diagnosticFactoryToRendererMap2.put(Errors.YIELD_IS_RESERVED, "{0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INVALID_CHARACTERS, "Name {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_OPERATOR_MODIFIER, "''operator'' modifier is inapplicable on this function: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_INFIX_MODIFIER, "''infix'' modifier is inapplicable on this function: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.OPERATOR_MODIFIER_REQUIRED, "''operator'' modifier is required on ''{0}'' in ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INFIX_MODIFIER_REQUIRED, "''infix'' modifier is required on ''{0}'' in ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_AS_OPERATOR, "Properties cannot be used in operator conventions: ''{0}'' in ''{1}''", Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INAPPLICABLE_MODIFIER, "''{0}'' modifier is inapplicable. The reason is that {1}", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DSL_SCOPE_VIOLATION, "''{0}'' can''t be called in this context by implicit receiver. Use the explicit one if necessary", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.DSL_SCOPE_VIOLATION_WARNING, "''{0}'' shouldn't be called in this context by implicit receiver, it will become an error soon. Use the explicit one if necessary", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_EXTENSION_OPERATOR_WITH_SAFE_CALL_RECEIVER, "Semantics of such combination of safe call and operator will change in next compiler version. Namely, the right part of the safe call will not be evaluated if receiver is null");
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_IN_FUNCTION_WITH_EXPRESSION_BODY, "Returns are not allowed for functions with expression body. Use block body in '{...}'");
        diagnosticFactoryToRendererMap2.put(Errors.NO_RETURN_IN_FUNCTION_WITH_BLOCK_BODY, "A 'return' expression required in a function with a block body ('{...}')");
        diagnosticFactoryToRendererMap2.put(Errors.NO_RETURN_IN_FUNCTION_WITH_BLOCK_BODY_MIGRATION, "A 'return' expression required in a function with a block body ('{...}'). If you got this error after the compiler update, then it's most likely due to a fix of a bug introduced in 1.3.0 (see KT-28061 for details)");
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_TYPE_MISMATCH, "This function must return a value of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_TYPE_MISMATCH, "Expected a value of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNMENT_TYPE_MISMATCH, "Expected a value of type {0}. Assignment operation is not an expression, so it does not return any value", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_PARAMETER_TYPE_MISMATCH, "Expected parameter of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_PARAMETER_TYPE_MISMATCH_WARNING, "Expected parameter of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_PARAMETERS_NUMBER_MISMATCH, "Expected {0,choice,0#no parameters|1#one parameter of type|1<{0,number,integer} parameters of types} {1}", (DiagnosticParameterRenderer) null, Renderers.RENDER_COLLECTION_OF_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_CAST_TO_ANY, "Conditional branch result of type {0} is implicitly cast to {1}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPRESSION_EXPECTED, "{0} is not an expression, and only expressions are allowed here", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$7((KtExpression) obj, renderingContext);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_VIOLATED, "Type argument is not within its bounds: should be subtype of ''{0}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_VIOLATED_WARNING, "Type argument is not within its bounds: ''{1}'' should be subtype of ''{0}''. This warning will become an error in K2", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.FINAL_UPPER_BOUND, "''{0}'' is a final type, and thus a value of the type parameter is predetermined", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_IS_EXTENSION_FUNCTION_TYPE, "Extension function type can not be used as an upper bound");
        diagnosticFactoryToRendererMap2.put(Errors.ONLY_ONE_CLASS_BOUND_ALLOWED, "Only one of the upper bounds can be a class");
        diagnosticFactoryToRendererMap2.put(Errors.BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER, "Type parameter cannot have any other bounds if it's bounded by another type parameter");
        diagnosticFactoryToRendererMap2.put(Errors.REPEATED_BOUND, "Type parameter already has this bound");
        diagnosticFactoryToRendererMap2.put(Errors.DYNAMIC_UPPER_BOUND, "Dynamic type can not be used as an upper bound");
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_ELVIS, "Elvis operator (?:) always returns the left operand of non-nullable type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.USELESS_ELVIS_RIGHT_IS_NULL, "Right operand of elvis operator (?:) is useless if it is null");
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_UPPER_BOUNDS, "Upper bounds of {0} have empty intersection", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.TOPLEVEL_TYPEALIASES_ONLY, "Nested and local type aliases are not supported");
        diagnosticFactoryToRendererMap2.put(Errors.RECURSIVE_TYPEALIAS_EXPANSION, "Recursive type alias in expansion: {0}", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION, "Type argument resulting from type alias expansion is not within required bounds for ''{2}'': should be subtype of ''{0}'', substituted type is ''{1}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION_WARNING, "Type argument resulting from type alias expansion is not within required bounds for ''{2}'': should be subtype of ''{0}'', substituted type is ''{1}''. This warning will become an error since 1.8. See https://youtrack.jetbrains.com/issue/KT-29168", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_PROJECTION_IN_TYPEALIAS_EXPANSION, "Conflicting projection in type alias expansion in intermediate type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPEALIAS_SHOULD_EXPAND_TO_CLASS, "Type alias expands to {0}, which is not a class, an interface, or an object", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPEALIAS_EXPANDED_TO_MALFORMED_TYPE, "Type alias expanded to malformed type {0}: {1}", Renderers.RENDER_TYPE, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UNUSED_TYPEALIAS_PARAMETER, "Type alias parameter {0} is not used in the expanded type {1} and does not affect type checking", Renderers.NAME, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPANDED_TYPE_CANNOT_BE_CONSTRUCTED, "Expanded type {0} contains non-invariant projections in top-level arguments and cannot be constructed", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPANDED_TYPE_CANNOT_BE_INHERITED, "Expanded type {0} contains non-invariant projections in top-level arguments and cannot be inherited from", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SYNTAX_WITH_DEFINITELY_NOT_NULL, "Applying '!!' to the whole as/is expression without parentheses is deprecated. Please, put parentheses explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.MODIFIER_LIST_NOT_ALLOWED, "Modifiers and annotations are not allowed here, because there are other modifiers or annotations outside of parenthesis");
        diagnosticFactoryToRendererMap2.put(Errors.PROGRESSIONS_CHANGING_RESOLVE, "Progressions and ranges types will start implementing Collection interface soon. This call will resolve to another declaration: {0}. See https://youtrack.jetbrains.com/issue/KT-49276 for more details. Please specify a progression type of argument explicitly through explicit cast to resolve to a proper declaration", Renderers.COMPACT);
        diagnosticFactoryToRendererMap2.put(Errors.TOO_MANY_ARGUMENTS, "Too many arguments for {0}", Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.CONSTANT_EXPECTED_TYPE_MISMATCH, "The {0} literal does not conform to the expected type {1}", CommonRenderers.STRING, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.DIVISION_BY_ZERO, "Division by zero");
        diagnosticFactoryToRendererMap2.put(Errors.INTEGER_OVERFLOW, "This operation has led to an overflow");
        diagnosticFactoryToRendererMap2.put(Errors.INT_LITERAL_OUT_OF_RANGE, "The value is out of range");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_LONG_SUFFIX, "Use 'L' instead of 'l'");
        diagnosticFactoryToRendererMap2.put(Errors.FLOAT_LITERAL_OUT_OF_RANGE, "The value is out of range");
        diagnosticFactoryToRendererMap2.put(Errors.FLOAT_LITERAL_CONFORMS_INFINITY, "Floating point number conforms to infinity");
        diagnosticFactoryToRendererMap2.put(Errors.FLOAT_LITERAL_CONFORMS_ZERO, "Floating point number conforms to zero");
        diagnosticFactoryToRendererMap2.put(Errors.INCORRECT_CHARACTER_LITERAL, "Incorrect character literal");
        diagnosticFactoryToRendererMap2.put(Errors.EMPTY_CHARACTER_LITERAL, "Empty character literal");
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_UNDERSCORE, "Illegal underscore");
        diagnosticFactoryToRendererMap2.put(Errors.TOO_MANY_CHARACTERS_IN_CHARACTER_LITERAL, "Too many characters in a character literal ''{0}''", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_ESCAPE, "Illegal escape: ''{0}''", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.NULL_FOR_NONNULL_TYPE, "Null can not be a value of a non-null type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NULL_FOR_NONNULL_TYPE_WARNING, "Null can not be a value of a non-null type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ELSE_MISPLACED_IN_WHEN, "'else' entry must be the last one in a when-expression");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_ELSE_IN_WHEN, "'when' is exhaustive so 'else' is redundant here");
        diagnosticFactoryToRendererMap2.put(Errors.COMMA_IN_WHEN_CONDITION_WITHOUT_ARGUMENT, "Deprecated syntax. Use '||' instead of commas in when-condition for 'when' without argument");
        diagnosticFactoryToRendererMap2.put(Errors.DUPLICATE_LABEL_IN_WHEN, "Duplicate label in when");
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_DECLARATION_IN_WHEN_SUBJECT, "Illegal variable declaration in ''when'' subject: {0}. Should be a simple val with an initializer", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NO_ELSE_IN_WHEN, "''when'' expression must be exhaustive, add necessary {0}", Renderers.RENDER_WHEN_MISSING_CASES);
        diagnosticFactoryToRendererMap2.put(Errors.NON_EXHAUSTIVE_WHEN, "''when'' expression on enum is recommended to be exhaustive, add {0}", Renderers.RENDER_WHEN_MISSING_CASES);
        diagnosticFactoryToRendererMap2.put(Errors.NO_ELSE_IN_WHEN_WARNING, "''when'' expression must be exhaustive, add necessary {0}. See https://youtrack.jetbrains.com/issue/KT-44705 for details", Renderers.RENDER_WHEN_MISSING_CASES);
        diagnosticFactoryToRendererMap2.put(Errors.NON_EXHAUSTIVE_WHEN_STATEMENT, "Non exhaustive ''when'' statements on {0} will be prohibited in 1.7, add {1}", CommonRenderers.STRING, Renderers.RENDER_WHEN_MISSING_CASES);
        diagnosticFactoryToRendererMap2.put(Errors.NON_EXHAUSTIVE_WHEN_ON_SEALED_CLASS, "''when'' expression on sealed classes is recommended to be exhaustive, add {0}", Renderers.RENDER_WHEN_MISSING_CASES);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECT_TYPE_IN_WHEN_WITHOUT_ELSE, "''when'' with expect {0} as subject can not be exhaustive without else branch", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_IN_RANGE, "Type mismatch: incompatible types of range and element checked in it");
        diagnosticFactoryToRendererMap2.put(Errors.CYCLIC_INHERITANCE_HIERARCHY, "There's a cycle in the inheritance hierarchy for this type");
        diagnosticFactoryToRendererMap2.put(Errors.CYCLIC_GENERIC_UPPER_BOUND, "Type parameter has cyclic upper bounds");
        diagnosticFactoryToRendererMap2.put(Errors.CYCLIC_SCOPES_WITH_COMPANION, "There's a cycle in scopes for that type. Most probably, there's a companion object that inherits some nested class (see KT-21515).\nSuch code is currently unstable, and its behavior may change in future releases");
        diagnosticFactoryToRendererMap2.put(Errors.MANY_CLASSES_IN_SUPERTYPE_LIST, "Only one class may appear in a supertype list");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_NOT_A_CLASS_OR_INTERFACE, "Only classes and interfaces may serve as supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_IS_EXTENSION_FUNCTION_TYPE, "Extension function type is not allowed as supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_IS_SUSPEND_EXTENSION_FUNCTION_TYPE, "Suspend extension function type is not allowed as supertypes. Such code will become error in future releases.");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_INITIALIZED_IN_INTERFACE, "Interfaces cannot initialize supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_IS_SUSPEND_FUNCTION_TYPE, "Suspend function type is allowed as a supertype only since version 1.6");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_IS_KSUSPEND_FUNCTION_TYPE, "KSuspendFunctionN interfaces are not allowed as supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.MIXING_SUSPEND_AND_NON_SUSPEND_SUPERTYPES, "Mixing suspend and non-suspend supertypes is not allowed");
        diagnosticFactoryToRendererMap2.put(Errors.CLASS_IN_SUPERTYPE_FOR_ENUM, "Enum class cannot inherit from classes");
        diagnosticFactoryToRendererMap2.put(Errors.CONSTRUCTOR_IN_INTERFACE, "An interface may not have a constructor");
        diagnosticFactoryToRendererMap2.put(Errors.METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE, "An interface may not implement a method of 'Any'");
        diagnosticFactoryToRendererMap2.put(Errors.INTERFACE_WITH_SUPERCLASS, "An interface cannot inherit from a class");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_APPEARS_TWICE, "A supertype appears twice");
        diagnosticFactoryToRendererMap2.put(Errors.FINAL_SUPERTYPE, "This type is final, so it cannot be inherited from");
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_CANNOT_HAVE_CLASS_SUPERTYPES, "Data class inheritance from other classes is forbidden");
        diagnosticFactoryToRendererMap2.put(Errors.SEALED_SUPERTYPE, "This type is sealed, so it can be inherited by only its own nested classes or objects");
        diagnosticFactoryToRendererMap2.put(Errors.SEALED_SUPERTYPE_IN_LOCAL_CLASS, "{0} cannot extend a sealed {1}", CommonRenderers.STRING, CommonRenderers.CLASS_KIND);
        diagnosticFactoryToRendererMap2.put(Errors.SEALED_INHERITOR_IN_DIFFERENT_PACKAGE, "Inheritor of sealed class or interface declared in package {0} but it must be in package {1} where base class is declared", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.SEALED_INHERITOR_IN_DIFFERENT_MODULE, "Inheritance of sealed classes or interfaces from different module is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.CLASS_INHERITS_JAVA_SEALED_CLASS, "Inheritance of java sealed classes is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.SINGLETON_IN_SUPERTYPE, "Cannot inherit from a singleton");
        diagnosticFactoryToRendererMap2.put(Errors.CLASS_CANNOT_BE_EXTENDED_DIRECTLY, "Class {0} cannot be extended directly", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CYCLIC_CONSTRUCTOR_DELEGATION_CALL, "There's a cycle in the delegation calls chain");
        diagnosticFactoryToRendererMap2.put(Errors.CONSTRUCTOR_IN_OBJECT, "Constructors are not allowed for objects");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPE_INITIALIZED_WITHOUT_PRIMARY_CONSTRUCTOR, "Supertype initialization is impossible without primary constructor");
        diagnosticFactoryToRendererMap2.put(Errors.PRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED, "Primary constructor call expected");
        diagnosticFactoryToRendererMap2.put(Errors.PRIMARY_CONSTRUCTOR_DELEGATION_CALL_EXPECTED_IN_ENUM, "Primary constructor call expected. It's going to be an error in 1.5.");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATION_SUPER_CALL_IN_ENUM_CONSTRUCTOR, "Call to super is not allowed in enum constructor");
        diagnosticFactoryToRendererMap2.put(Errors.PRIMARY_CONSTRUCTOR_REQUIRED_FOR_DATA_CLASS, "Primary constructor required for data class");
        diagnosticFactoryToRendererMap2.put(Errors.EXPLICIT_DELEGATION_CALL_REQUIRED, "Explicit 'this' or 'super' call is required. There is no constructor in superclass that can be called without arguments");
        diagnosticFactoryToRendererMap2.put(Errors.INSTANCE_ACCESS_BEFORE_SUPER_CALL, "Cannot access ''{0}'' before the instance has been initialized", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_SELECTOR, "The expression cannot be a selector (occur after a dot)");
        diagnosticFactoryToRendererMap2.put(Errors.NO_TAIL_CALLS_FOUND, "A function is marked as tail-recursive but no tail calls are found");
        diagnosticFactoryToRendererMap2.put(Errors.TAILREC_ON_VIRTUAL_MEMBER, "Tailrec is not allowed on open members");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_PARAMETER_WITH_NO_TYPE_ANNOTATION, "A type annotation is required on a value parameter");
        diagnosticFactoryToRendererMap2.put(Errors.BREAK_OR_CONTINUE_OUTSIDE_A_LOOP, "'break' and 'continue' are only allowed inside a loop");
        diagnosticFactoryToRendererMap2.put(Errors.BREAK_OR_CONTINUE_IN_WHEN, "'break' and 'continue' are not allowed in 'when' statements. Consider using labels to continue/break from the outer loop");
        diagnosticFactoryToRendererMap2.put(Errors.BREAK_OR_CONTINUE_JUMPS_ACROSS_FUNCTION_BOUNDARY, "'break' or 'continue' jumps across a function or a class boundary");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_LOOP_LABEL, "The label ''{0}'' does not denote a loop", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_FUNCTION_LABEL, "Target label does not denote a function");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_A_FUNCTION_LABEL_WARNING, "Target label does not denote a function");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_LABEL_WARNING, "Label is redundant, because it can not be referenced in either 'break', 'continue', or 'return' expression");
        diagnosticFactoryToRendererMap2.put(Errors.ANONYMOUS_INITIALIZER_IN_INTERFACE, "Anonymous initializers are not allowed in interfaces");
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_SUPERTYPE, "A supertype cannot be nullable");
        diagnosticFactoryToRendererMap2.put(Errors.DYNAMIC_SUPERTYPE, "A supertype cannot be dynamic");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_NULLABLE, "Redundant '?'");
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_ON_DEFINITELY_NOT_NULLABLE, "'!!' type cannot be marked as nullable");
        diagnosticFactoryToRendererMap2.put(Errors.INCORRECT_LEFT_COMPONENT_OF_INTERSECTION, "Intersection types are only supported for definitely non-nullable types: left part should be a type parameter with nullable bounds");
        diagnosticFactoryToRendererMap2.put(Errors.INCORRECT_RIGHT_COMPONENT_OF_INTERSECTION, "Intersection types are only supported for definitely non-nullable types: right part should be non-nullable Any");
        diagnosticFactoryToRendererMap2.put(Errors.UNSAFE_CALL, "Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UNSAFE_IMPLICIT_INVOKE_CALL, "Reference has a nullable type ''{0}'', use explicit ''?.invoke()'' to make a function-like call instead", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_LABEL, "Ambiguous label");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED, "Unsupported [{0}]", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_WARNING, "Unsupported [{0}]. This warning will be an error in future releases", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NEW_INFERENCE_ERROR, "New inference error [{0}]", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NEW_INFERENCE_DIAGNOSTIC, "New inference [{0}]", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NEW_INFERENCE_UNKNOWN_ERROR, "Unknown error in new inference with applicability ''{0}'' and target ''{1}'', please report to https://youtrack.jetbrains.com/newIssue?project=KT", Renderers.TO_STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NON_APPLICABLE_CALL_FOR_BUILDER_INFERENCE, "Non-applicable call for builder inference");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_FEATURE, "{0}", new LanguageFeatureMessageRenderer(LanguageFeatureMessageRenderer.Type.UNSUPPORTED));
        diagnosticFactoryToRendererMap2.put(Errors.EXPERIMENTAL_FEATURE_WARNING, "{0}", new LanguageFeatureMessageRenderer(LanguageFeatureMessageRenderer.Type.WARNING));
        diagnosticFactoryToRendererMap2.put(Errors.EXPLICIT_BACKING_FIELDS_UNSUPPORTED, "Explicit backing field declarations are not supported in FE 1.0");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_INHERITANCE_FROM_JAVA_MEMBER_REFERENCING_KOTLIN_FUNCTION, "Inheritance of a Java member referencing 'kotlin.jvm.functions.FunctionN': {0} is unsupported", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_SEALED_WHEN, "'sealed' in front of 'when' is reserved");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_SEALED_FUN_INTERFACE, "'sealed fun interface' is unsupported");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_SUSPEND_TEST, "'suspend' functions annotated with @kotlin.test.Test are unsupported");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_REFERENCES_TO_VARIABLES_AND_PARAMETERS, "References to variables and parameters are unsupported");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_CLASS_LITERALS_WITH_EMPTY_LHS, "Class literals with empty left hand side are unsupported");
        diagnosticFactoryToRendererMap2.put(Errors.EXCEPTION_FROM_ANALYZER, "Internal Error occurred while analyzing this expression:\n{0}", CommonRenderers.THROWABLE);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_STDLIB, "{0}. Ensure you have the standard Kotlin library in dependencies", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UNNECESSARY_SAFE_CALL, "Unnecessary safe call on a non-null receiver of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.SAFE_CALL_WILL_CHANGE_NULLABILITY, "Safe call on a non-null receiver will have nullable type in future releases\n  Right now safe call on non nullable receiver has not null type: `\"hello\"?.length` has type Int\n  In future releases all safe calls will have nullable type: `\"hello\"?.length` will have type Int?");
        diagnosticFactoryToRendererMap2.put(Errors.UNEXPECTED_SAFE_CALL, "Safe-call is not allowed here");
        diagnosticFactoryToRendererMap2.put(Errors.UNNECESSARY_NOT_NULL_ASSERTION, "Unnecessary non-null assertion (!!) on a non-null receiver of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NOT_NULL_ASSERTION_ON_LAMBDA_EXPRESSION, "Non-null assertion (!!) is called on a lambda expression");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_NULL_ASSERTION_ON_CALLABLE_REFERENCE, "Non-null assertion (!!) is called on a callable reference expression");
        diagnosticFactoryToRendererMap2.put(Errors.NAME_IN_CONSTRAINT_IS_NOT_A_TYPE_PARAMETER, "{0} does not refer to a type parameter of {1}", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((KtTypeConstraint) obj).getSubjectTypeParameterName().getReferencedName();
            }
        }, Renderers.DECLARATION_NAME);
        diagnosticFactoryToRendererMap2.put(Errors.SMARTCAST_IMPOSSIBLE, "Smart cast to ''{0}'' is impossible, because ''{1}'' is a {2}", Renderers.RENDER_TYPE, CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_SMARTCAST, "Smart cast to ''{0}'' is deprecated, because ''{1}'' is a {2}", Renderers.RENDER_TYPE, CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.ALWAYS_NULL, "The result of the expression is always null");
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_CONSTRUCTOR_KEYWORD, "Use 'constructor' keyword after modifiers of primary constructor");
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_CONSTRUCTOR_BRACKETS, "Constructor requires brackets");
        diagnosticFactoryToRendererMap2.put(Errors.NON_PRIVATE_CONSTRUCTOR_IN_ENUM, "Constructor must be private in enum class");
        diagnosticFactoryToRendererMap2.put(Errors.NON_PRIVATE_CONSTRUCTOR_IN_SEALED, "Constructor must be private in sealed class");
        diagnosticFactoryToRendererMap2.put(Errors.NON_PRIVATE_OR_PROTECTED_CONSTRUCTOR_IN_SEALED, "Constructor must be private or protected in sealed class");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_NOT_TOP_LEVEL, "Value classes cannot be local or inner");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_NOT_FINAL, "Value classes can be only final");
        diagnosticFactoryToRendererMap2.put(Errors.ABSENCE_OF_PRIMARY_CONSTRUCTOR_FOR_VALUE_CLASS, "Primary constructor is required for value class");
        diagnosticFactoryToRendererMap2.put(Errors.INLINE_CLASS_CONSTRUCTOR_WRONG_PARAMETERS_SIZE, "Inline class must have exactly one primary constructor parameter");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_EMPTY_CONSTRUCTOR, "Value class must have at least one primary constructor parameter");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CONSTRUCTOR_NOT_FINAL_READ_ONLY_PARAMETER, "Value class primary constructor must only have final read-only (val) property parameters");
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_WITH_BACKING_FIELD_INSIDE_VALUE_CLASS, "Value class cannot have properties with backing fields");
        diagnosticFactoryToRendererMap2.put(Errors.DELEGATED_PROPERTY_INSIDE_VALUE_CLASS, "Value class cannot have delegated properties");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_HAS_INAPPLICABLE_PARAMETER_TYPE, "Value class cannot have value parameter of type ''{0}''", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CANNOT_IMPLEMENT_INTERFACE_BY_DELEGATION, "Value class cannot implement an interface by delegation if expression is not a parameter");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CANNOT_EXTEND_CLASSES, "Value class cannot extend classes");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CANNOT_BE_RECURSIVE, "Value class cannot be recursive");
        diagnosticFactoryToRendererMap2.put(Errors.MULTI_FIELD_VALUE_CLASS_PRIMARY_CONSTRUCTOR_DEFAULT_PARAMETER, "Default parameters are not supported in the primary constructor of a multi-field value class");
        diagnosticFactoryToRendererMap2.put(Errors.RESERVED_MEMBER_INSIDE_VALUE_CLASS, "Member with the name ''{0}'' is reserved for future releases", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_ARGUMENT_ON_TYPED_VALUE_CLASS_EQUALS, "Type arguments for typed value class equals must be only star projections");
        diagnosticFactoryToRendererMap2.put(Errors.INNER_CLASS_INSIDE_VALUE_CLASS, "Value class cannot have inner classes");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CANNOT_BE_CLONEABLE, "Value class cannot be Cloneable");
        diagnosticFactoryToRendererMap2.put(Errors.INLINE_CLASS_DEPRECATED, "'inline' modifier is deprecated. Use 'value' instead");
        diagnosticFactoryToRendererMap2.put(Errors.VALUE_CLASS_CANNOT_HAVE_CONTEXT_RECEIVERS, "Value classes cannot have context receivers");
        diagnosticFactoryToRendererMap2.put(Errors.INEFFICIENT_EQUALS_OVERRIDING_IN_VALUE_CLASS, "Overriding ''equals'' from ''Any'' in value class without operator ''equals(other: {0}): Boolean'' leads to boxing on every equality comparison", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ON_ILLEGAL_MULTI_FIELD_VALUE_CLASS_TYPED_TARGET, "Annotations on {0} of multi-field value class type are not supported", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.RESULT_CLASS_IN_RETURN_TYPE, "'kotlin.Result' cannot be used as a return type");
        diagnosticFactoryToRendererMap2.put(Errors.RESULT_CLASS_WITH_NULLABLE_OPERATOR, "Expression of type ''kotlin.Result'' cannot be used as a left operand of ''{0}''", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_WRONG_COUNT_OF_ABSTRACT_MEMBERS, "Fun interfaces must have exactly one abstract method");
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_CANNOT_HAVE_ABSTRACT_PROPERTIES, "Fun interfaces cannot have abstract properties");
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_ABSTRACT_METHOD_WITH_TYPE_PARAMETERS, "Single abstract member cannot declare type parameters");
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_ABSTRACT_METHOD_WITH_DEFAULT_VALUE, "Single abstract member cannot declare default values");
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_CONSTRUCTOR_REFERENCE, "Functional interface constructor references are prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.FUN_INTERFACE_WITH_SUSPEND_FUNCTION, "'suspend' modifier is not allowed on a single abstract member");
        diagnosticFactoryToRendererMap2.put(Errors.VARIANCE_ON_TYPE_PARAMETER_NOT_ALLOWED, "Variance annotations are only allowed for type parameters of classes and interfaces");
        diagnosticFactoryToRendererMap2.put(Errors.BOUND_ON_TYPE_ALIAS_PARAMETER_NOT_ALLOWED, "Bounds are not allowed on type alias parameters");
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_TYPE_PARAMETER_SYNTAX, "Type parameters must be placed before the name of the function");
        diagnosticFactoryToRendererMap2.put(Errors.MISPLACED_TYPE_PARAMETER_CONSTRAINTS, "If a type parameter has multiple constraints, they all need to be placed in the 'where' clause");
        MultiRenderer multiRenderer = new MultiRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.diagnostics.rendering.MultiRenderer
            public final String[] render(Object obj) {
                return DefaultErrorMessages.lambda$static$9((VarianceConflictDiagnosticData) obj);
            }
        };
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_VARIANCE_CONFLICT, "Type parameter {0} is declared as ''{1}'' but occurs in ''{2}'' position in type {3}", multiRenderer);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_VARIANCE_CONFLICT_IN_EXPANDED_TYPE, "Type parameter {0} is declared as ''{1}'' but occurs in ''{2}'' position in abbreviated type {3}", multiRenderer);
        diagnosticFactoryToRendererMap2.put(Errors.FINITE_BOUNDS_VIOLATION, "This type parameter violates the Finite Bound Restriction");
        diagnosticFactoryToRendererMap2.put(Errors.FINITE_BOUNDS_VIOLATION_IN_JAVA, "Violation of Finite Bound Restriction for {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.EXPANSIVE_INHERITANCE, "This type parameter violates the Non-Expansive Inheritance Restriction");
        diagnosticFactoryToRendererMap2.put(Errors.EXPANSIVE_INHERITANCE_IN_JAVA, "Violation of Non-Expansive Inheritance Restriction for {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_PROJECTION, "Projection is redundant: the corresponding type parameter of {0} has the same variance", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_PROJECTION, "Projection is conflicting with variance of the corresponding type parameter of {0}. Remove the projection or replace it with ''*''", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_ARGUMENTS_FOR_OUTER_CLASS_WHEN_NESTED_REFERENCED, "Type arguments for outer class are redundant when nested class is referenced");
        diagnosticFactoryToRendererMap2.put(Errors.REIFIED_TYPE_IN_CATCH_CLAUSE, "Reified type is forbidden for catch parameter");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_IN_CATCH_CLAUSE, "Type parameter is forbidden for catch parameter");
        diagnosticFactoryToRendererMap2.put(Errors.GENERIC_THROWABLE_SUBCLASS, "Subclass of 'Throwable' may not have type parameters");
        diagnosticFactoryToRendererMap2.put(Errors.INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS, "Inner class of generic class extending 'Throwable' is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS_WARNING, "Inner class of generic class extending 'Throwable' is deprecated");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_MISMATCH_IN_FOR_LOOP, "The loop iterates over values of type {0} but the parameter is declared to be {1}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.INCOMPATIBLE_TYPES, "Incompatible types: {0} and {1}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_NOTHING_RETURN_TYPE, "'Nothing' return type needs to be specified explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_NOTHING_PROPERTY_TYPE, "'Nothing' property type needs to be specified explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.ABBREVIATED_NOTHING_RETURN_TYPE, "'Nothing' return type can't be specified with type alias");
        diagnosticFactoryToRendererMap2.put(Errors.ABBREVIATED_NOTHING_PROPERTY_TYPE, "'Nothing' property type can't be specified with type alias");
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_INTERSECTION_TYPE, "Inferred type {0} is an intersection, please specify the required type explicitly", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EXPECTED_CONDITION, "Expected condition of type Boolean");
        diagnosticFactoryToRendererMap2.put(Errors.DYNAMIC_RECEIVER_NOT_ALLOWED, "Dynamic receiver is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_CHECK_FOR_ERASED, "Cannot check for instance of erased type: {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UNCHECKED_CAST, "Unchecked cast: {0} to {1}", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.INCONSISTENT_TYPE_PARAMETER_VALUES, "Type parameter {0} of ''{1}'' has inconsistent values: {2}", Renderers.NAME, Renderers.NAME, Renderers.RENDER_COLLECTION_OF_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.INCONSISTENT_TYPE_PARAMETER_BOUNDS, "Type parameter {0} of ''{1}'' has inconsistent bounds: {2}", Renderers.NAME, Renderers.NAME, Renderers.RENDER_COLLECTION_OF_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.EQUALITY_NOT_APPLICABLE, "Operator ''{0}'' cannot be applied to ''{1}'' and ''{2}''", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda9
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((KtSimpleNameExpression) obj).getReferencedName();
            }
        }, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.EQUALITY_NOT_APPLICABLE_WARNING, "Operator ''{0}'' cannot be applied to ''{1}'' and ''{2}''. It will became an error in future releases.", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda13
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((KtSimpleNameExpression) obj).getReferencedName();
            }
        }, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.INCOMPATIBLE_ENUM_COMPARISON, "Comparison of incompatible enums ''{0}'' and ''{1}'' is always unsuccessful", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.INCOMPATIBLE_ENUM_COMPARISON_ERROR, "Comparison of incompatible enums ''{0}'' and ''{1}'' is always unsuccessful", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.SENSELESS_COMPARISON, "Condition ''{0}'' is always ''{1}''", Renderers.ELEMENT_TEXT, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.SENSELESS_NULL_IN_WHEN, "Expression under 'when' is never equal to null");
        diagnosticFactoryToRendererMap2.put(Errors.INVALID_IF_AS_EXPRESSION, "'if' must have both main and 'else' branches if used as an expression");
        diagnosticFactoryToRendererMap2.put(Errors.CONFUSING_BRANCH_CONDITION, "The logical expressions may be understood ambiguously in when with subject branches. Please wrap it with parentheses");
        diagnosticFactoryToRendererMap2.put(Errors.INVALID_IF_AS_EXPRESSION_WARNING, "'if' must have both main and 'else' branches if used as an expression. See https://youtrack.jetbrains.com/issue/KT-44705 for details");
        diagnosticFactoryToRendererMap2.put(Errors.OVERRIDING_FINAL_MEMBER, "''{0}'' in ''{1}'' is final and cannot be overridden", Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_WEAKEN_ACCESS_PRIVILEGE, "Cannot weaken access privilege ''{0}'' for ''{1}'' in ''{2}''", Renderers.VISIBILITY, Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_CHANGE_ACCESS_PRIVILEGE, "Cannot change access privilege ''{0}'' for ''{1}'' in ''{2}''", Renderers.VISIBILITY, Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_TYPE_MISMATCH_ON_OVERRIDE, "Return type of ''{0}'' is not a subtype of the return type of the overridden member ''{1}''", Renderers.NAME, Renderers.FQ_NAMES_IN_TYPES_ANNOTATIONS_WHITELIST);
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_TYPE_MISMATCH_ON_INHERITANCE, "''{0}'' clashes with ''{1}'': return types are incompatible", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_TYPE_MISMATCH_ON_OVERRIDE, "Type of ''{0}'' is not a subtype of the overridden property ''{1}''", Renderers.NAME, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.VAR_TYPE_MISMATCH_ON_OVERRIDE, "Type of ''{0}'' doesn''t match the type of the overridden var-property ''{1}''", Renderers.NAME, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_TYPE_MISMATCH_ON_INHERITANCE, "''{0}'' clashes with ''{1}'': property types are incompatible", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.VAR_TYPE_MISMATCH_ON_INHERITANCE, "''{0}'' clashes with ''{1}'': property types do not match", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.OVERRIDING_FINAL_MEMBER_BY_DELEGATION, "''{0}'' implicitly overrides a final member ''{1}'' by delegation", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.VAR_OVERRIDDEN_BY_VAL_BY_DELEGATION, "Val-property ''{0}'' implicitly overrides a var-property ''{1}'' by delegation", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_TYPE_MISMATCH_BY_DELEGATION, "Type of ''{0}'' is not a subtype of overridden by delegation ''{1}''", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PROPERTY_TYPE_MISMATCH_BY_DELEGATION, "Type of property ''{0}'' is not a subtype of overridden by delegation ''{1}''", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.VAR_OVERRIDDEN_BY_VAL, "Var-property {0} cannot be overridden by val-property {1}", Renderers.FQ_NAMES_IN_TYPES, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_INHERITED_MEMBERS, "{0} inherits conflicting members: {1}", Renderers.NAME, CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES));
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_INHERITED_MEMBERS_WARNING, "{0} inherits conflicting members: {1}; This warning will became error in future releases. See https://youtrack.jetbrains.com/issue/KT-51194", Renderers.NAME, CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES));
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_MEMBER_NOT_IMPLEMENTED, "{0} is not abstract and does not implement abstract member {1}", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED, "{0} is not abstract and does not implement abstract base class member {1}", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.ABSTRACT_CLASS_MEMBER_NOT_IMPLEMENTED_WARNING, "Deprecated: {0} is not abstract and does not implement abstract base class member {1}. See https://youtrack.jetbrains.com/issue/KT-45508", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.MANY_IMPL_MEMBER_NOT_IMPLEMENTED, "{0} must override {1} because it inherits many implementations of it", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED, "{0} must override {1} because it inherits multiple interface methods of it", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.MANY_INTERFACES_MEMBER_NOT_IMPLEMENTED_WARNING, "Deprecated: {0} must override {1} because it inherits multiple interface methods of it", Renderers.RENDER_CLASS_OR_OBJECT, Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.INVISIBLE_ABSTRACT_MEMBER_FROM_SUPER, "{0} inherits invisible abstract members: {1}", Renderers.NAME, CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES));
        diagnosticFactoryToRendererMap2.put(Errors.CONFLICTING_OVERLOADS, "Conflicting overloads: {0}", CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES));
        diagnosticFactoryToRendererMap2.put(Errors.FUNCTION_EXPECTED, "Expression ''{0}''{1} cannot be invoked as a function. The function ''" + OperatorNameConventions.INVOKE.asString() + "()'' is not found", Renderers.ELEMENT_TEXT, new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda14
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$12((KotlinType) obj, renderingContext);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.FUNCTION_CALL_EXPECTED, "Function invocation ''{0}({1})'' expected", Renderers.ELEMENT_TEXT, new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda15
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessages.lambda$static$13((Boolean) obj, renderingContext);
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.NON_TAIL_RECURSIVE_CALL, "Recursive call is not a tail call");
        diagnosticFactoryToRendererMap2.put(Errors.TAIL_RECURSION_IN_TRY_IS_NOT_SUPPORTED, "Tail recursion optimization inside try/catch/finally is not supported");
        diagnosticFactoryToRendererMap2.put(Errors.RESULT_TYPE_MISMATCH, "{0} must return {1} but returns {2}", CommonRenderers.STRING, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.UNSAFE_INFIX_CALL, "Infix call corresponds to a dot-qualified call ''{0}.{1}({2})'' which is not allowed on a nullable receiver ''{0}''. Use ''?.''-qualified call instead", Renderers.ELEMENT_TEXT, CommonRenderers.STRING, Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.UNSAFE_OPERATOR_CALL, "Operator call corresponds to a dot-qualified call ''{0}.{1}({2})'' which is not allowed on a nullable receiver ''{0}''.", Renderers.ELEMENT_TEXT, CommonRenderers.STRING, Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.OVERLOAD_RESOLUTION_AMBIGUITY, "Overload resolution ambiguity: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.OVERLOAD_RESOLUTION_AMBIGUITY_BECAUSE_OF_STUB_TYPES, "No type argument for type parameter(s) `{1}` of the `{0}` builder specified. Cannot choose which overloaded function `{2}` to call. To disambiguate this call, either use an explicit type cast for a parameter or a receiver (see specific errors on them) or specify the type `{1}` explicitly.", CommonRenderers.STRING, CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.STUB_TYPE_IN_ARGUMENT_CAUSES_AMBIGUITY, "The type of an argument hasn''t been inferred yet. To disambiguate this call, explicitly cast it to `{0}` if you want the builder''s type parameter(s) `{1}` to be inferred to `{2}`.", Renderers.RENDER_TYPE, CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.STUB_TYPE_IN_RECEIVER_CAUSES_AMBIGUITY, "The type of a receiver hasn''t been inferred yet. To disambiguate this call, explicitly cast it to `{0}` if you want the builder''s type parameter(s) `{1}` to be inferred to `{2}`.", Renderers.RENDER_TYPE, CommonRenderers.STRING, CommonRenderers.STRING, (DiagnosticParameterRenderer) null);
        diagnosticFactoryToRendererMap2.put(Errors.BUILDER_INFERENCE_MULTI_LAMBDA_RESTRICTION, "Unstable inference behaviour with multiple lambdas. Please either specify the type argument for generic parameter `{0}` of `{1}` explicitly", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.BUILDER_INFERENCE_STUB_RECEIVER, "The type of a receiver hasn''t been inferred yet. Please specify type argument for generic parameter `{0}` of `{1}` explicitly", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.UPPER_BOUND_VIOLATION_IN_CONSTRAINT, "Upper bound violation for generic parameter `{0}` of `{1}`: {3} is not a subtype of {2}", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NONE_APPLICABLE, "None of the following functions can be called with the arguments supplied: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.CANNOT_COMPLETE_RESOLVE, "Cannot choose among the following candidates without completing type inference: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.UNRESOLVED_REFERENCE_WRONG_RECEIVER, "Unresolved reference. None of the following candidates is applicable because of receiver type mismatch: {0}", Renderers.AMBIGUOUS_CALLS);
        diagnosticFactoryToRendererMap2.put(Errors.CALLABLE_REFERENCE_RESOLUTION_AMBIGUITY, "Callable reference resolution ambiguity: {0}", Renderers.AMBIGUOUS_CALLABLE_REFERENCES);
        diagnosticFactoryToRendererMap2.put(Errors.NO_VALUE_FOR_PARAMETER, "No value passed for parameter ''{0}''", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_RECEIVER, "A receiver of type {0} is required", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.NO_RECEIVER_ALLOWED, "No receiver can be passed to this function or property");
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_FUNCTION, "Assigning single elements to varargs in named form is forbidden", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_FUNCTION, "Redundant spread (*) operator");
        diagnosticFactoryToRendererMap2.put(Errors.ASSIGNING_SINGLE_ELEMENT_TO_VARARG_IN_NAMED_FORM_ANNOTATION, "Assigning single elements to varargs in named form is forbidden");
        diagnosticFactoryToRendererMap2.put(Errors.REDUNDANT_SPREAD_OPERATOR_IN_NAMED_FORM_IN_ANNOTATION, "Redundant spread (*) operator");
        diagnosticFactoryToRendererMap2.put(Errors.CREATING_AN_INSTANCE_OF_ABSTRACT_CLASS, "Cannot create an instance of an abstract class");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS, "Type inference failed: {0}", Renderers.TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS_RENDERER);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_CANNOT_CAPTURE_TYPES, "Type inference failed: {0}", Renderers.TYPE_INFERENCE_CANNOT_CAPTURE_TYPES_RENDERER);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER, "Type inference failed: {0}", Renderers.TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER_RENDERER);
        diagnosticFactoryToRendererMap2.put(Errors.NEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER, "Not enough information to infer type variable {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INFERRED_INTO_DECLARED_UPPER_BOUNDS, "Type parameter for a type argument {0} can''t be inferred into declared upper bounds. Please provide any use-site type information. It will become an error in future releases.", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.COULD_BE_INFERRED_ONLY_WITH_UNRESTRICTED_BUILDER_INFERENCE, "Builder inference lambda contains inapplicable calls so {0} can''t be inferred. It could be resolved only with unrestricted builder inference. Please use -Xunrestricted-builder-inference compiler flag to enable it.", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR, "Type inference failed: {0}", Renderers.TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR_RENDERER);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_INCORPORATION_ERROR, "Type inference failed. Please try to specify type arguments explicitly.");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_ONLY_INPUT_TYPES, "Type inference failed. The value of the type parameter {0} should be mentioned in input types (argument types, receiver type or expected type). Try to specify it explicitly.", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION, "Type argument for a type parameter {0} can''t be inferred because it has incompatible upper bounds: {1} ({2}{3})", Renderers.TO_STRING, Renderers.RENDER_COLLECTION_OF_TYPES, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION, "Type argument for a type parameter {0} has possible incompatible upper bounds: {1} ({2}{3})", Renderers.TO_STRING, Renderers.RENDER_COLLECTION_OF_TYPES, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_UPPER_BOUND_VIOLATED, "{0}", Renderers.TYPE_INFERENCE_UPPER_BOUND_VIOLATED_RENDERER);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_EXPECTED_TYPE_MISMATCH, "Type inference failed. Expected type mismatch: inferred type is {1} but {0} was expected", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_CANDIDATE_WITH_SAM_AND_VARARG, "Please use spread operator to pass an array as vararg. It will be an error in 1.5.");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_POSTPONED_VARIABLE_IN_RECEIVER_TYPE, "Postponed type variable (type variable of the builder inference) can't be used in the receiver type. Use a member function instead of extension one or specify type arguments of a function which uses the builder inference, explicitly.");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_INFERENCE_FAILED_ON_SPECIAL_CONSTRUCT, "Type inference for control flow expression failed. Please specify its type explicitly.");
        diagnosticFactoryToRendererMap2.put(Errors.WRONG_NUMBER_OF_TYPE_ARGUMENTS, "{0,choice,0#No type arguments|1#One type argument|1<{0,number,integer} type arguments} expected for {1}", (DiagnosticParameterRenderer) null, Renderers.COMPACT_WITHOUT_SUPERTYPES);
        diagnosticFactoryToRendererMap2.put(Errors.OUTER_CLASS_ARGUMENTS_REQUIRED, "Type arguments should be specified for an outer {0}. Use full class name to specify them", Renderers.RENDER_CLASS_OR_OBJECT_NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NO_TYPE_ARGUMENTS_ON_RHS, "{0,choice,0#No type arguments|1#One type argument|1<{0,number,integer} type arguments} expected. Use ''{1}'' if you don''t want to pass type arguments", (DiagnosticParameterRenderer) null, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_ARGUMENTS_NOT_ALLOWED, "Type arguments are not allowed {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_AS_REIFIED, "Cannot use ''{0}'' as reified type parameter. Use a class instead.", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DEFINITELY_NON_NULLABLE_AS_REIFIED, "Cannot use definitely-non-nullable type as reified type argument");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_AS_REIFIED_ARRAY, "Cannot use ''{0}'' as reified type parameter, since the array type parameter is not reified.", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.REIFIED_TYPE_PARAMETER_NO_INLINE, "Only type parameters of inline functions can be reified");
        diagnosticFactoryToRendererMap2.put(Errors.REIFIED_TYPE_FORBIDDEN_SUBSTITUTION, "Cannot use ''{0}'' as reified type parameter", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.REIFIED_TYPE_UNSAFE_SUBSTITUTION, "It may be not safe to use ''{0}'' as an argument for a reified type parameter. Use a non-generic type or * if possible", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETERS_NOT_ALLOWED, "Type parameters are not allowed here");
        diagnosticFactoryToRendererMap2.put(Errors.CANDIDATE_CHOSEN_USING_OVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION, "Candidate was chosen only by @OverloadResolutionByLambdaReturnType annotation");
        diagnosticFactoryToRendererMap2.put(Errors.COMPATIBILITY_WARNING, "Candidate resolution will be changed soon, please use fully qualified name to invoke the following closer candidate explicitly ''{0}''", Renderers.COMPATIBILITY_CANDIDATE);
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_PARAMETER_OF_PROPERTY_NOT_USED_IN_RECEIVER, "Type parameter of a property must be used in its receiver type");
        diagnosticFactoryToRendererMap2.put(Errors.SUPERTYPES_FOR_ANNOTATION_CLASS, "Annotation class cannot have supertypes");
        diagnosticFactoryToRendererMap2.put(Errors.MISSING_VAL_ON_ANNOTATION_PARAMETER, "'val' keyword is missing on annotation parameter");
        diagnosticFactoryToRendererMap2.put(Errors.VAR_ANNOTATION_PARAMETER, "An annotation parameter cannot be 'var'");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_CLASS_CONSTRUCTOR_CALL, "Annotation class cannot be instantiated");
        diagnosticFactoryToRendererMap2.put(Errors.NOT_AN_ANNOTATION_CLASS, "''{0}'' is not an annotation class", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_CLASS_MEMBER, "Members are not allowed in annotation class");
        diagnosticFactoryToRendererMap2.put(Errors.INVALID_TYPE_OF_ANNOTATION_MEMBER, "Invalid type of annotation member");
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_TYPE_OF_ANNOTATION_MEMBER, "An annotation parameter cannot be nullable");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_MUST_BE_CONST, "An annotation argument must be a compile-time constant");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_MUST_BE_ENUM_CONST, "An enum annotation argument must be a enum constant");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_MUST_BE_KCLASS_LITERAL, "An annotation argument must be a class literal (T::class)");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_KCLASS_LITERAL_OF_TYPE_PARAMETER, "Type parameter in a class literal is not allowed in an annotation argument");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_KCLASS_LITERAL_OF_TYPE_PARAMETER_ERROR, "Type parameter in a class literal is deprecated in an annotation argument");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_PARAMETER_DEFAULT_VALUE_MUST_BE_CONSTANT, "Default value of annotation parameter must be a compile-time constant");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATIONS_ON_BLOCK_LEVEL_EXPRESSION_ON_THE_SAME_LINE, "Annotations on block-level expressions are being parsed differently depending on presence of a new line after them. Use new line if whole block-level expression must be annotated or wrap annotated expression in parentheses");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_USED_AS_ANNOTATION_ARGUMENT, "An annotation can't be used as the annotations argument");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ARGUMENT_IS_NON_CONST, "An annotation argument must be a compile-time constant");
        diagnosticFactoryToRendererMap2.put(Errors.CYCLE_IN_ANNOTATION_PARAMETER, "Type of this parameter is cyclic");
        diagnosticFactoryToRendererMap2.put(Errors.RESTRICTED_RETENTION_FOR_EXPRESSION_ANNOTATION, "Expression annotations with retention other than SOURCE are prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.LOCAL_ANNOTATION_CLASS, "Annotation class cannot be local");
        diagnosticFactoryToRendererMap2.put(Errors.ANNOTATION_ON_SUPERCLASS, "Annotations on superclass are meaningless");
        diagnosticFactoryToRendererMap2.put(Errors.CONST_VAL_NOT_TOP_LEVEL_OR_OBJECT, "Const 'val' are only allowed on top level, in named objects, or in companion objects");
        diagnosticFactoryToRendererMap2.put(Errors.CONST_VAL_WITH_DELEGATE, "Const 'val' should not have a delegate");
        diagnosticFactoryToRendererMap2.put(Errors.CONST_VAL_WITH_GETTER, "Const 'val' should not have a getter");
        diagnosticFactoryToRendererMap2.put(Errors.TYPE_CANT_BE_USED_FOR_CONST_VAL, "Const ''val'' has type ''{0}''. Only primitives and String are allowed", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.CONST_VAL_WITHOUT_INITIALIZER, "Const 'val' should have an initializer");
        diagnosticFactoryToRendererMap2.put(Errors.CONST_VAL_WITH_NON_CONST_INITIALIZER, "Const 'val' initializer should be a constant value");
        diagnosticFactoryToRendererMap2.put(Errors.NON_CONST_VAL_USED_IN_CONSTANT_EXPRESSION, "Only 'const val' can be used in constant expressions");
        diagnosticFactoryToRendererMap2.put(Errors.DEFAULT_VALUE_NOT_ALLOWED_IN_OVERRIDE, "An overriding function is not allowed to specify default values for its parameters");
        diagnosticFactoryToRendererMap2.put(Errors.MULTIPLE_DEFAULTS_INHERITED_FROM_SUPERTYPES, "More than one overridden descriptor declares a default value for ''{0}''. As the compiler can not make sure these values agree, this is not allowed.", Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.MULTIPLE_DEFAULTS_INHERITED_FROM_SUPERTYPES_WHEN_NO_EXPLICIT_OVERRIDE, "More than one overridden descriptor declares a default value for ''{0}''. As the compiler can not make sure these values agree, this is not allowed.", Renderers.FQ_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PARAMETER_NAME_CHANGED_ON_OVERRIDE, "The corresponding parameter in the supertype ''{0}'' is named ''{1}''. This may cause problems when calling this function with named arguments.", Renderers.NAME, Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES, "Names of the parameter #{1} conflict in the following members of supertypes: ''{0}''. This may cause problems when calling this function with named arguments.", CommonRenderers.commaSeparated(Renderers.FQ_NAMES_IN_TYPES), Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NAME_FOR_AMBIGUOUS_PARAMETER, "Named argument is not allowed for a parameter with an ambiguous name");
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_WITHOUT_PARAMETERS, "Data class must have at least one primary constructor parameter");
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_VARARG_PARAMETER, "Primary constructor vararg parameters are forbidden for data classes");
        diagnosticFactoryToRendererMap2.put(Errors.DATA_CLASS_NOT_PROPERTY_PARAMETER, "Data class primary constructor must only have property (val / var) parameters");
        diagnosticFactoryToRendererMap2.put(Errors.DATA_OBJECT_CUSTOM_EQUALS_OR_HASH_CODE, "Data object cannot have a custom implementation of 'equals' or 'hashCode'");
        diagnosticFactoryToRendererMap2.put(Errors.CATCH_PARAMETER_WITH_DEFAULT_VALUE, "Catch clause parameter may not have a default value");
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_ANONYMOUS_TYPE_INFERRED, "Right-hand side has anonymous type. Please specify type explicitly", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.APPROXIMATED_LOCAL_TYPE_WILL_BECOME_NULLABLE, "Declaration return type inferred as ''{0}'' instead of ''{0}?''. This will be fixed in Kotlin 1.9. Please specify the type explicitly to avoid future errors or unexpected changes in behavior. See https://youtrack.jetbrains.com/issue/KT-53982 for details.", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.APPROXIMATED_LOCAL_TYPE_WILL_BECOME_FLEXIBLE, "Declaration return type inferred as ''{0}'' but it may be nullable. This will be fixed in Kotlin 1.9. Please specify the type explicitly to avoid unexpected changes in behavior. See https://youtrack.jetbrains.com/issue/KT-53982 for details.", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.KCLASS_WITH_NULLABLE_TYPE_PARAMETER_IN_SIGNATURE, "Declaration has an inconsistent return type. Please add upper bound Any for type parameter ''{0}'' or specify return type explicitly", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.EXTENSION_IN_CLASS_REFERENCE_NOT_ALLOWED, "''{0}'' is a member and an extension at the same time. References to such elements are not allowed", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.CALLABLE_REFERENCE_LHS_NOT_A_CLASS, "Left-hand side of a callable reference cannot be a type parameter");
        diagnosticFactoryToRendererMap2.put(Errors.CALLABLE_REFERENCE_TO_ANNOTATION_CONSTRUCTOR, "Annotation class cannot be instantiated");
        diagnosticFactoryToRendererMap2.put(Errors.CLASS_LITERAL_LHS_NOT_A_CLASS, "Only classes are allowed on the left hand side of a class literal");
        diagnosticFactoryToRendererMap2.put(Errors.ARRAY_CLASS_LITERAL_REQUIRES_ARGUMENT, "Array class literal requires a type argument, please specify one in angle brackets");
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_TYPE_IN_CLASS_LITERAL_LHS, "Type in a class literal must not be nullable");
        diagnosticFactoryToRendererMap2.put(Errors.EXPRESSION_OF_NULLABLE_TYPE_IN_CLASS_LITERAL_LHS, "Expression in a class literal has a nullable type ''{0}'', use !! to make the type non-nullable", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap2.put(Errors.ADAPTED_CALLABLE_REFERENCE_AGAINST_REFLECTION_TYPE, "Adapted callable reference cannot be resolved against reflective types");
        diagnosticFactoryToRendererMap2.put(Errors.NON_PUBLIC_CALL_FROM_PUBLIC_INLINE, "Public-API inline function cannot access non-public-API ''{0}''", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.DEPRECATED_IMPLICIT_NON_PUBLIC_API_ACCESS, "Deprecated implicit access of non-public-API from public-API inline function");
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_CLASS_MEMBER_FROM_INLINE, "Non-private inline function cannot access members of private classes: ''{0}''", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_CLASS_MEMBER_FROM_INLINE_WARNING, "Non-private inline function cannot access members of private classes: ''{0}''. This warning will become an error in 2.0", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.NOT_YET_SUPPORTED_IN_INLINE, "{0} are not yet supported in inline functions", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap2.put(Errors.DECLARATION_CANT_BE_INLINED, "'inline' modifier is not allowed on virtual members. Only private or final members can be inlined");
        diagnosticFactoryToRendererMap2.put(Errors.DECLARATION_CANT_BE_INLINED_WARNING, "'inline' modifier is not allowed on virtual enum members. Only private or final members can be inlined. This warning will become an error in K2");
        diagnosticFactoryToRendererMap2.put(Errors.OVERRIDE_BY_INLINE, "Override by an inline function");
        diagnosticFactoryToRendererMap2.put(Errors.REIFIED_TYPE_PARAMETER_IN_OVERRIDE, "Override by a function with reified type parameter");
        diagnosticFactoryToRendererMap2.put(Errors.NOTHING_TO_INLINE, "Expected performance impact from inlining is insignificant. Inlining works best for functions with parameters of functional types");
        diagnosticFactoryToRendererMap2.put(Errors.USAGE_IS_NOT_INLINABLE, "Illegal usage of inline-parameter ''{0}'' in ''{1}''. Add ''noinline'' modifier to the parameter declaration", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.USAGE_IS_NOT_INLINABLE_WARNING, "Deprecated usage of inline-parameter ''{0}'' in ''{1}''. Add ''noinline'' modifier to the parameter declaration. See https://youtrack.jetbrains.com/issue/KT-52502", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.NULLABLE_INLINE_PARAMETER, "Inline-parameter ''{0}'' of ''{1}'' must not be nullable. Add ''noinline'' modifier to the parameter declaration or make its type not nullable", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.RECURSION_IN_INLINE, "Inline function ''{1}'' cannot be recursive", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.INLINE_PROPERTY_WITH_BACKING_FIELD, "Inline property cannot have backing field");
        diagnosticFactoryToRendererMap2.put(Errors.NON_INTERNAL_PUBLISHED_API, "@PublishedApi annotation is only applicable for internal declaration");
        diagnosticFactoryToRendererMap2.put(Errors.PROTECTED_CALL_FROM_PUBLIC_INLINE, "Protected function call from public-API inline function is prohibited", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.PROTECTED_CONSTRUCTOR_CALL_FROM_PUBLIC_INLINE, "Protected constructor call from public-API inline function is deprecated", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.SUPER_CALL_FROM_PUBLIC_INLINE, "Accessing super members from public-API inline function is deprecated", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.INVALID_DEFAULT_FUNCTIONAL_PARAMETER_FOR_INLINE, "Invalid default value for inline parameter: ''{0}''. Only lambdas, anonymous functions, and callable references are supported", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.NOT_SUPPORTED_INLINE_PARAMETER_IN_INLINE_PARAMETER_DEFAULT_VALUE, "Usage of inline parameter ''{0}'' in default value for another inline parameter is not supported", Renderers.ELEMENT_TEXT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap2.put(Errors.PRIVATE_INLINE_FUNCTIONS_RETURNING_ANONYMOUS_OBJECTS, "Return type of the private inline function can't be anonymous. It will be approximated to Any in a future release. See KT-33917 for more details");
        diagnosticFactoryToRendererMap2.put(Errors.NON_LOCAL_RETURN_NOT_ALLOWED, "Can''t inline ''{0}'' here: it may contain non-local returns. Add ''crossinline'' modifier to parameter declaration ''{0}''", Renderers.ELEMENT_TEXT);
        diagnosticFactoryToRendererMap2.put(Errors.INLINE_CALL_CYCLE, "The ''{0}'' invocation is a part of inline cycle", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.NON_LOCAL_RETURN_IN_DISABLED_INLINE, "Non-local returns are not allowed with inlining disabled");
        diagnosticFactoryToRendererMap2.put(Errors.NON_LOCAL_SUSPENSION_POINT, "Suspension functions can be called only within coroutine body");
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_SUSPEND_FUNCTION_CALL, "Suspend function ''{0}'' should be called only from a coroutine or another suspend function", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_SUSPEND_PROPERTY_ACCESS, "Suspend property ''{0}'' should be accessed only from a coroutine or suspend function", Renderers.NAME);
        diagnosticFactoryToRendererMap2.put(Errors.ILLEGAL_RESTRICTED_SUSPENDING_FUNCTION_CALL, "Restricted suspending functions can only invoke member or extension suspending functions on their restricted coroutine scope");
        diagnosticFactoryToRendererMap2.put(Errors.NON_MODIFIER_FORM_FOR_BUILT_IN_SUSPEND, "''suspend'' function can only be called in a form of modifier of a lambda: suspend { ... }");
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION, "Returning type parameter has been inferred to Nothing implicitly. Please specify type arguments explicitly to hide this warning. Nothing can produce an exception at runtime.");
        diagnosticFactoryToRendererMap2.put(Errors.IMPLICIT_NOTHING_TYPE_ARGUMENT_AGAINST_NOT_NOTHING_EXPECTED_TYPE, "Returning type parameter has been inferred to Nothing implicitly because Nothing is more specific than specified expected type. Please specify type arguments explicitly in accordance with expected type to hide this warning. Nothing can produce an exception at runtime. See KT-36776 for more details.");
        diagnosticFactoryToRendererMap2.put(Errors.RETURN_FOR_BUILT_IN_SUSPEND, "Using implicit label for this lambda is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.MODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND, "Calls having a form of ''suspend {}'' are deprecated because ''suspend'' in the context will have a meaning of a modifier. Surround the lambda with parentheses: ''suspend({ ... })''");
        diagnosticFactoryToRendererMap2.put(Errors.MODIFIER_FORM_FOR_NON_BUILT_IN_SUSPEND_FUN, "Calls having a form of ''suspend fun'' are deprecated because ''suspend'' in the context will have a meaning of a modifier. Surround the argument of the call with parens: ''suspend(fun() { ... })''. See https://youtrack.jetbrains.com/issue/KT-49264");
        diagnosticFactoryToRendererMap2.put(Errors.PLUGIN_ERROR, "{0}", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda16
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((RenderedDiagnostic) obj).getText();
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.PLUGIN_WARNING, "{0}", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda17
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((RenderedDiagnostic) obj).getText();
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.PLUGIN_INFO, "{0}", new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda18
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return ((RenderedDiagnostic) obj).getText();
            }
        });
        diagnosticFactoryToRendererMap2.put(Errors.ERROR_IN_CONTRACT_DESCRIPTION, "Error in contract description: {0}", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.CONTRACT_NOT_ALLOWED, "{0}", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.NO_CONTEXT_RECEIVER, "No required context receiver found: {0}", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.MULTIPLE_ARGUMENTS_APPLICABLE_FOR_CONTEXT_RECEIVER, "Multiple arguments applicable for context receiver: {0}", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap2.put(Errors.AMBIGUOUS_CALL_WITH_IMPLICIT_CONTEXT_RECEIVER, "With implicit context receiver, call is ambiguous. Specify the receiver explicitly");
        diagnosticFactoryToRendererMap2.put(Errors.UNSUPPORTED_CONTEXTUAL_DECLARATION_CALL, "To use contextual declarations, specify the `-Xcontext-receivers` compiler option");
        diagnosticFactoryToRendererMap2.put(Errors.SUBTYPING_BETWEEN_CONTEXT_RECEIVERS, "Subtyping relation between context receivers is prohibited");
        diagnosticFactoryToRendererMap2.put(Errors.VOLATILE_ON_VALUE, "'@Volatile' annotation cannot be used on immutable properties");
        diagnosticFactoryToRendererMap2.put(Errors.VOLATILE_ON_DELEGATE, "'@Volatile' annotation cannot be used on delegated properties");
        diagnosticFactoryToRendererMap2.setImmutable();
        for (Field field : Errors.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    Object obj = field.get(null);
                    if ((obj instanceof DiagnosticFactory) && MAP.get((DiagnosticFactory) obj) == null) {
                        throw new IllegalStateException("No default diagnostic renderer is provided for " + ((DiagnosticFactory) obj).getName());
                    }
                } catch (IllegalAccessException e2) {
                    throw new IllegalStateException(e2);
                }
            }
        }
    }

    public static String render(UnboundDiagnostic unboundDiagnostic) {
        if (unboundDiagnostic == null) {
            $$$reportNull$$$0(0);
        }
        DiagnosticRenderer rendererForDiagnostic = getRendererForDiagnostic(unboundDiagnostic);
        if (rendererForDiagnostic != null) {
            String strRender = rendererForDiagnostic.render(unboundDiagnostic);
            if (strRender == null) {
                $$$reportNull$$$0(1);
            }
            return strRender;
        }
        String str = unboundDiagnostic.toString() + " (error: could not render message)";
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    public static DiagnosticRenderer getRendererForDiagnostic(final UnboundDiagnostic unboundDiagnostic) {
        if (unboundDiagnostic == null) {
            $$$reportNull$$$0(3);
        }
        DiagnosticRenderer diagnosticRenderer = (DiagnosticRenderer) AddToStdlibKt.firstNotNullResult(RENDERER_MAPS, new Function1() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ((DiagnosticFactoryToRendererMap) obj).get(unboundDiagnostic.getFactory());
            }
        });
        return diagnosticRenderer != null ? diagnosticRenderer : unboundDiagnostic.getFactory().getDefaultRenderer();
    }

    static /* synthetic */ String[] lambda$static$1(TypeMismatchDueToTypeProjectionsData typeMismatchDueToTypeProjectionsData) {
        RenderingContext renderingContextOf = RenderingContext.of(typeMismatchDueToTypeProjectionsData.getExpectedType(), typeMismatchDueToTypeProjectionsData.getExpressionType(), typeMismatchDueToTypeProjectionsData.getReceiverType(), typeMismatchDueToTypeProjectionsData.getCallableDescriptor());
        return new String[]{Renderers.RENDER_TYPE.render(typeMismatchDueToTypeProjectionsData.getExpectedType(), renderingContextOf), Renderers.RENDER_TYPE.render(typeMismatchDueToTypeProjectionsData.getExpressionType(), renderingContextOf), Renderers.RENDER_TYPE.render(typeMismatchDueToTypeProjectionsData.getReceiverType(), renderingContextOf), Renderers.FQ_NAMES_IN_TYPES.render((DeclarationDescriptor) typeMismatchDueToTypeProjectionsData.getCallableDescriptor(), renderingContextOf)};
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget;

        static {
            int[] iArr = new int[Errors.BadNamedArgumentsTarget.values().length];
            $SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget = iArr;
            try {
                iArr[Errors.BadNamedArgumentsTarget.NON_KOTLIN_FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget[Errors.BadNamedArgumentsTarget.INTEROP_FUNCTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget[Errors.BadNamedArgumentsTarget.INVOKE_ON_FUNCTION_TYPE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget[Errors.BadNamedArgumentsTarget.EXPECTED_CLASS_MEMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static /* synthetic */ String lambda$static$2(Errors.BadNamedArgumentsTarget badNamedArgumentsTarget, RenderingContext renderingContext) {
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$diagnostics$Errors$BadNamedArgumentsTarget[badNamedArgumentsTarget.ordinal()];
        if (i == 1) {
            return "non-Kotlin functions";
        }
        if (i == 2) {
            return "interop functions with ambiguous parameter names";
        }
        if (i == 3) {
            return "function types";
        }
        if (i == 4) {
            return "members of expected classes";
        }
        throw new AssertionError(badNamedArgumentsTarget);
    }

    static /* synthetic */ String lambda$static$3(Pair pair, RenderingContext renderingContext) {
        String str = (String) pair.getSecond();
        return ((String) pair.getFirst()) + (str != null ? ". " + str : "");
    }

    static /* synthetic */ String lambda$static$4(VersionRequirement.Version version, RenderingContext renderingContext) {
        return version.equals(VersionRequirement.Version.INFINITY) ? "" : " is only supported since Kotlin " + version.asString() + " and";
    }

    static /* synthetic */ String lambda$static$5(VersionRequirement.Version version, RenderingContext renderingContext) {
        return version.equals(VersionRequirement.Version.INFINITY) ? "" : " is only available since Kotlin " + version.asString() + " and";
    }

    static /* synthetic */ String lambda$static$6(IncompatibleVersionErrorData incompatibleVersionErrorData, RenderingContext renderingContext) {
        return "The actual metadata version is " + incompatibleVersionErrorData.getActualVersion() + ", but the compiler version " + incompatibleVersionErrorData.getCompilerVersion() + (incompatibleVersionErrorData.getLanguageVersion().equals(incompatibleVersionErrorData.getCompilerVersion()) ? "" : " [with language version " + incompatibleVersionErrorData.getLanguageVersion() + "]") + " can read versions up to " + incompatibleVersionErrorData.getExpectedVersion() + ".\nThe class is loaded from " + FileUtil.toSystemIndependentName(incompatibleVersionErrorData.getFilePath());
    }

    static /* synthetic */ String lambda$static$7(KtExpression ktExpression, RenderingContext renderingContext) {
        String string = ktExpression.toString();
        return string.substring(0, 1) + string.substring(1).toLowerCase();
    }

    static /* synthetic */ String[] lambda$static$9(VarianceConflictDiagnosticData varianceConflictDiagnosticData) {
        RenderingContext renderingContextOf = RenderingContext.of(varianceConflictDiagnosticData.getTypeParameter(), varianceConflictDiagnosticData.getTypeParameter().getVariance(), varianceConflictDiagnosticData.getOccurrencePosition(), varianceConflictDiagnosticData.getContainingType());
        return new String[]{Renderers.NAME.render(varianceConflictDiagnosticData.getTypeParameter(), renderingContextOf), CommonRenderers.RENDER_POSITION_VARIANCE.render(varianceConflictDiagnosticData.getTypeParameter().getVariance(), renderingContextOf), CommonRenderers.RENDER_POSITION_VARIANCE.render(varianceConflictDiagnosticData.getOccurrencePosition(), renderingContextOf), Renderers.RENDER_TYPE.render(varianceConflictDiagnosticData.getContainingType(), renderingContextOf)};
    }

    static /* synthetic */ String lambda$static$12(KotlinType kotlinType, RenderingContext renderingContext) {
        return KotlinTypeKt.isError(kotlinType) ? "" : " of type '" + Renderers.RENDER_TYPE.render(kotlinType, renderingContext) + FormatHelper.QUOTE;
    }

    static /* synthetic */ String lambda$static$13(Boolean bool, RenderingContext renderingContext) {
        return bool.booleanValue() ? "..." : "";
    }

    public static DiagnosticParameterRenderer<Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, Collection<MemberDescriptor>>> adaptGenerics1(final DiagnosticParameterRenderer<Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>> diagnosticParameterRenderer) {
        return new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return diagnosticParameterRenderer.render((Map) obj, renderingContext);
            }
        };
    }

    public static DiagnosticParameterRenderer<List<Pair<MemberDescriptor, Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, Collection<MemberDescriptor>>>>> adaptGenerics2(final DiagnosticParameterRenderer<List<? extends Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>>>> diagnosticParameterRenderer) {
        return new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages$$ExternalSyntheticLambda11
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return diagnosticParameterRenderer.render((List) obj, renderingContext);
            }
        };
    }

    private DefaultErrorMessages() {
    }
}
