package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import java.util.List;
import java.util.stream.Collectors;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.diagnostics.rendering.RenderingContext;
import org.jetbrains.kotlin.resolve.MemberComparator;
import org.jetbrains.kotlin.utils.StringsKt;

/* JADX INFO: loaded from: classes6.dex */
public class DefaultErrorMessagesJvm implements DefaultErrorMessages.Extension {
    private static final DiagnosticParameterRenderer<ConflictingJvmDeclarationsData> CONFLICTING_JVM_DECLARATIONS_DATA;
    private static final DiagnosticFactoryToRendererMap MAP;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/resolve/jvm/diagnostics/DefaultErrorMessagesJvm", "getMap"));
    }

    static {
        DiagnosticParameterRenderer<ConflictingJvmDeclarationsData> diagnosticParameterRenderer = new DiagnosticParameterRenderer() { // from class: org.jetbrains.kotlin.resolve.jvm.diagnostics.DefaultErrorMessagesJvm$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public final String render(Object obj, RenderingContext renderingContext) {
                return DefaultErrorMessagesJvm.lambda$static$1((ConflictingJvmDeclarationsData) obj, renderingContext);
            }
        };
        CONFLICTING_JVM_DECLARATIONS_DATA = diagnosticParameterRenderer;
        DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = new DiagnosticFactoryToRendererMap("JVM");
        MAP = diagnosticFactoryToRendererMap;
        diagnosticFactoryToRendererMap.put(ErrorsJvm.CONFLICTING_JVM_DECLARATIONS, "Platform declaration clash: {0}", diagnosticParameterRenderer);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ACCIDENTAL_OVERRIDE, "Accidental override: {0}", diagnosticParameterRenderer);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.CONFLICTING_INHERITED_JVM_DECLARATIONS, "Inherited platform declarations clash: {0}", diagnosticParameterRenderer);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_STATIC_NOT_IN_OBJECT_OR_CLASS_COMPANION, "Only members in named objects and companion objects of classes can be annotated with '@JvmStatic'");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_STATIC_NOT_IN_OBJECT_OR_COMPANION, "Only members in named objects and companion objects can be annotated with '@JvmStatic'");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_STATIC_ON_NON_PUBLIC_MEMBER, "Only public members in interface companion objects can be annotated with '@JvmStatic'");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_STATIC_ON_CONST_OR_JVM_FIELD, "'@JvmStatic' annotation is useless for const or '@JvmField' properties");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_STATIC_ON_EXTERNAL_IN_INTERFACE, "'@JvmStatic' annotation cannot be used on 'external' members of interface companions");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERRIDE_CANNOT_BE_STATIC, "Override member cannot be '@JvmStatic' in object");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS, "'@JvmOverloads' annotation has no effect for methods without default arguments");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_ABSTRACT, "'@JvmOverloads' annotation cannot be used on abstract methods");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_INTERFACE, "'@JvmOverloads' annotation cannot be used on interface methods");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_PRIVATE, "'@JvmOverloads' annotation has no effect on private declarations");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_LOCAL, "'@JvmOverloads' annotation cannot be used on local declarations");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_ANNOTATION_CLASS_CONSTRUCTOR, "'@JvmOverloads' annotation cannot be used on constructors of annotation classes");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_ANNOTATION_HIDDEN_CONSTRUCTOR, "'@JvmOverloads' annotation cannot be used on constructors hidden by value class rules");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.OVERLOADS_ANNOTATION_MANGLED_FUNCTION, "'@JvmOverloads' annotation cannot be used on functions mangled by value class rules");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INAPPLICABLE_JVM_NAME, "'@JvmName' annotation is not applicable to this declaration");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ILLEGAL_JVM_NAME, "Illegal JVM name");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNCHRONIZED_ON_ABSTRACT, "'@Synchronized' annotation cannot be used on abstract functions");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNCHRONIZED_ON_INLINE, "'@Synchronized' annotation has no effect on inline functions");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNCHRONIZED_ON_VALUE_CLASS, "'@Synchronized' annotation has no effect on value classes");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNCHRONIZED_IN_INTERFACE, "'@Synchronized' annotation cannot be used on interface members");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_BE_ABSTRACT, "External declaration can not be abstract");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_HAVE_BODY, "External declaration can not have a body");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXTERNAL_DECLARATION_IN_INTERFACE, "Members of interfaces can not be external");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXTERNAL_DECLARATION_CANNOT_BE_INLINED, "Inline functions can not be external");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.POSITIONED_VALUE_ARGUMENT_FOR_JAVA_ANNOTATION, "Only named arguments are available for Java annotations");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.DEPRECATED_JAVA_ANNOTATION, "This annotation is deprecated in Kotlin. Use ''@{0}'' instead", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NON_SOURCE_REPEATED_ANNOTATION, "Repeatable annotations with non-SOURCE retention are only supported starting from Kotlin 1.6");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATED_ANNOTATION_WITH_CONTAINER, "Repeated annotation ''@{0}'' cannot be used on a declaration which is annotated with its container annotation ''@{1}''", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ANNOTATION_IS_NOT_APPLICABLE_TO_MULTIFILE_CLASSES, "Annotation ''@{0}'' is not applicable to the multi-file classes", Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATABLE_CONTAINER_MUST_HAVE_VALUE_ARRAY, "Container annotation ''{0}'' must have a property ''value'' of type ''Array<{1}>''. This code will be prohibited in Kotlin 1.6", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATABLE_CONTAINER_HAS_NON_DEFAULT_PARAMETER, "Container annotation ''{0}'' does not have a default value for ''{1}''. This code will be prohibited in Kotlin 1.6", Renderers.TO_STRING, Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATABLE_CONTAINER_HAS_SHORTER_RETENTION, "Container annotation ''{0}'' has shorter retention (''{1}'') than the repeatable annotation ''{2}'' (''{3}''). This code will be prohibited in Kotlin 1.6", Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATABLE_CONTAINER_TARGET_SET_NOT_A_SUBSET, "Target set of container annotation ''{0}'' must be a subset of the target set of contained annotation ''{1}''. This code will be prohibited in Kotlin 1.6", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REPEATABLE_ANNOTATION_HAS_NESTED_CLASS_NAMED_CONTAINER, "Repeatable annotation cannot have a nested class named 'Container'. This name is reserved for auto-generated container class");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_PACKAGE_NAME_CANNOT_BE_EMPTY, "'@JvmPackageName' annotation value cannot be empty");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_PACKAGE_NAME_MUST_BE_VALID_NAME, "'@JvmPackageName' annotation value must be a valid dot-qualified name of a package");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_PACKAGE_NAME_NOT_SUPPORTED_IN_FILES_WITH_CLASSES, "'@JvmPackageName' annotation is not supported for files with class declarations");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.STATE_IN_MULTIFILE_CLASS, "Non-const property with backing field or delegate is not allowed in a multi-file class if -Xmultifile-parts-inherit is enabled");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NOT_ALL_MULTIFILE_CLASS_PARTS_ARE_JVM_SYNTHETIC, "All of multi-file class parts should be annotated with @JvmSynthetic if at least one of them is");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NO_REFLECTION_IN_CLASS_PATH, "Call uses reflection API which is not found in compilation classpath. Make sure you have kotlin-reflect.jar in the classpath");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INTERFACE_CANT_CALL_DEFAULT_METHOD_VIA_SUPER, "Interfaces can call JVM-default members via super only within JVM-default members. Please use '-Xjvm-default=all/all-compatibility' modes for such calls");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SUBCLASS_CANT_CALL_COMPANION_PROTECTED_NON_STATIC, "Using protected members which are not @JvmStatic in the superclass companion is unsupported yet");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS, "Type mismatch: inferred type is {1} but {0} was expected", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.UPPER_BOUND_VIOLATED_BASED_ON_JAVA_ANNOTATIONS, "Type argument is not within its bounds: should be subtype of ''{0}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.UPPER_BOUND_VIOLATED_IN_TYPEALIAS_EXPANSION_BASED_ON_JAVA_ANNOTATIONS, "Type argument resulting from type alias expansion is not within required bounds for ''{2}'': should be subtype of ''{0}'', substituted type is ''{1}''", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE, Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NULLABLE_TYPE_PARAMETER_AGAINST_NOT_NULL_TYPE_PARAMETER, "Type parameter ''{0}'' has nullable upper bounds while non-nullable version is expected. This warning will become an error soon. See https://youtrack.jetbrains.com/issue/KT-36770 for details", Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS, "Unsafe use of a nullable receiver of type {0}", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.WRONG_NULLABILITY_FOR_JAVA_OVERRIDE, "Override ''{0}'' has incorrect nullability in its signature comparing with overridden ''{1}''This warning will become an error soon. See https://youtrack.jetbrains.com/issue/KT-36770 for details", Renderers.COMPACT, Renderers.COMPACT);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.WRONG_TYPE_PARAMETER_NULLABILITY_FOR_JAVA_OVERRIDE, "Type parameter ''{0}'' has nullable upper bound, so override has incorrect signature comparing with a base member with NotNull annotation. Please add a non-nullable upper bound (e.g. Any) to the type parameter. See https://kotlinlang.org/docs/generics.html#upper-bounds and https://youtrack.jetbrains.com/issue/KT-36770 for more details. This warning will become an error soon.", Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR, "An accessor will not be generated for ''{0}'', so the annotation will not be written to the class file", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.WHEN_ENUM_CAN_BE_NULL_IN_JAVA, "Enum argument can be null in Java, but exhaustive when contains no null branch");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_CLASS_ON_COMPANION, "The resulting type of this ''javaClass'' call is {0} and not {1}. Please use the more clear ''::class.java'' syntax to avoid confusion", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_TYPE_MISMATCH, "Java type mismatch expected {1} but found {0}. Use explicit cast", Renderers.RENDER_TYPE, Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.DUPLICATE_CLASS_NAMES, "Duplicate JVM class name ''{0}'' generated from: {1}", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.UPPER_BOUND_CANNOT_BE_ARRAY, "Upper bound of a type parameter cannot be an array");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INAPPLICABLE_JVM_FIELD, "{0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INAPPLICABLE_JVM_FIELD_WARNING, "{0}. This warning will become an error in further releases", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_SYNTHETIC_ON_DELEGATE, "'@JvmSynthetic' annotation cannot be used on delegated properties");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNTHETIC_SETTER_PROJECTED_OUT, "Use of setter for ''{0}'' is unsafe due to the type projection in receiver. Workaround: use explicit cast on receiver. See https://youtrack.jetbrains.com/issue/KT-54309", Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.STRICTFP_ON_CLASS, "'@Strictfp' annotation on classes is unsupported yet");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SUPER_CALL_WITH_DEFAULT_PARAMETERS, "Super-calls with default arguments are not allowed. Please specify all arguments of ''super.{0}'' explicitly", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.TARGET6_INTERFACE_INHERITANCE, "Compiling ''{0}'' to JVM 1.8, but its superinterface ''{1}'' was compiled for JVM 1.6. Method implementation inheritance is restricted for such cases. Please make explicit overrides (abstract or concrete) for the following non-abstract members of ''{1}'': {2}", Renderers.NAME, Renderers.NAME, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INLINE_FROM_HIGHER_PLATFORM, "Cannot inline bytecode built with {0} into bytecode that is being built with {1}. Please specify proper ''-jvm-target'' option", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INLINE_FROM_HIGHER_PLATFORM_WARNING, "Cannot inline bytecode built with {0} into bytecode that is being built with {1}. Please specify proper ''-jvm-target'' option", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_MODULE_DOES_NOT_DEPEND_ON_MODULE, "Symbol is declared in module ''{0}'' which current module does not depend on", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_MODULE_DOES_NOT_READ_UNNAMED_MODULE, "Symbol is declared in unnamed module which is not read by current module");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE, "Symbol is declared in module ''{0}'' which does not export package ''{1}''", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.API_VERSION_IS_AT_LEAST_ARGUMENT_SHOULD_BE_CONSTANT, "'apiVersionIsAtLeast' argument should be a constant expression");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ASSIGNMENT_TO_ARRAY_LOOP_VARIABLE, "Assignment to a for-in-array loop range variable. Behavior may change in Kotlin 1.3. See https://youtrack.jetbrains.com/issue/KT-21354 for more details");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_DEFAULT_IN_DECLARATION, "Usage of ''@{0}'' is only allowed with -Xjvm-default option", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_DEFAULT_WITH_COMPATIBILITY_IN_DECLARATION, "Usage of '@JvmDefaultWithCompatibility' is only allowed with '-Xjvm-default=all' option");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_DEFAULT_WITH_COMPATIBILITY_NOT_ON_INTERFACE, "'@JvmDefaultWithCompatibility' annotation is only allowed on interfaces");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXPLICIT_METADATA_IS_DISALLOWED, "Explicit @Metadata is disallowed");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SUSPENSION_POINT_INSIDE_MONITOR, "A suspension point at {0} is inside a critical section", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SUSPENSION_POINT_INSIDE_CRITICAL_SECTION, "The ''{0}'' suspension point is inside a critical section", Renderers.NAME);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.LOCAL_JVM_RECORD, "Local @JvmRecord classes are not allowed");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NON_FINAL_JVM_RECORD, "@JvmRecord class should be final");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ENUM_JVM_RECORD, "@JvmRecord class should not be an enum");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORD_WITHOUT_PRIMARY_CONSTRUCTOR_PARAMETERS, "Primary constructor with parameters is required for @JvmRecord class");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORD_NOT_VAL_PARAMETER, "Constructor parameter of @JvmRecord class should be a val");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORD_NOT_LAST_VARARG_PARAMETER, "Only the last constructor parameter of @JvmRecord may be a vararg");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORD_EXTENDS_CLASS, "Record cannot inherit a class", Renderers.RENDER_TYPE);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORD_REQUIRES_JDK15, "Using @JvmRecords requires at least JDK 15");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.INNER_JVM_RECORD, "@JvmRecord class should not be inner");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.FIELD_IN_JVM_RECORD, "It's not allowed to have non-constructor properties with backing field in @JvmRecord class");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.DELEGATION_BY_IN_JVM_RECORD, "Delegation is not allowed for @JvmRecord classes");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.NON_DATA_CLASS_JVM_RECORD, "Only data classes are allowed to be marked as @JvmRecord");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ILLEGAL_JAVA_LANG_RECORD_SUPERTYPE, "Classes cannot have explicit 'java.lang.Record' supertype");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_RECORDS_ILLEGAL_BYTECODE_TARGET, "Using @JvmRecord is only allowed with -jvm-target 16 or later (or -jvm-target 15 with the -Xjvm-enable-preview flag enabled)");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.CONCURRENT_HASH_MAP_CONTAINS_OPERATOR, "Method 'contains' from ConcurrentHashMap may have unexpected semantics: it calls 'containsValue' instead of 'containsKey'. Use explicit form of the call to 'containsKey'/'containsValue'/'contains' or cast the value to kotlin.collections.Map instead. See https://youtrack.jetbrains.com/issue/KT-18053 for more details");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.TAILREC_WITH_DEFAULTS, "Computation order of non-constant default arguments in tail-recursive functions will change in 1.4. See KT-31540 for more details");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SPREAD_ON_SIGNATURE_POLYMORPHIC_CALL, "Spread operator is prohibited for arguments to signature-polymorphic calls");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.FUNCTION_DELEGATE_MEMBER_NAME_CLASH, "Functional interface member cannot have this name on JVM because it clashes with an internal member getFunctionDelegate");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXPLICIT_OVERRIDE_REQUIRED_IN_COMPATIBILITY_MODE, "Compatibility mode requires to explicitly override ''{1}'' with specialization ''{0}'', or annotate the class with @JvmDefaultWithoutCompatibility. Please refer to KT-39603 for details", Renderers.COMPACT, Renderers.SHORT_NAMES_IN_TYPES);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.EXPLICIT_OVERRIDE_REQUIRED_IN_MIXED_MODE, "Explicit override is required for ''{0}'' in the ''-Xjvm-default={2}'' mode. Otherwise, implicit class override ''{1}'' (compiled in the old -Xjvm-default mode) is not fully overridden and would be incorrectly called at runtime", Renderers.SHORT_NAMES_IN_TYPES, Renderers.SHORT_NAMES_IN_TYPES, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.DANGEROUS_CHARACTERS, "Name contains characters which can cause problems on Windows: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.VALUE_CLASS_WITHOUT_JVM_INLINE_ANNOTATION, "Value classes without @JvmInline annotation are not supported yet");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JVM_INLINE_WITHOUT_VALUE_CLASS, "@JvmInline annotation is only applicable to value classes");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.SYNCHRONIZED_ON_SUSPEND, "@Synchronized annotation is not applicable to suspend functions and lambdas");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.TYPEOF_SUSPEND_TYPE, "Suspend functional types are not supported in typeOf");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND, "Non-reified type parameters with recursive bounds are not supported yet: {0}", CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.JAVA_SAM_INTERFACE_CONSTRUCTOR_REFERENCE, "Java SAM interface constructor references are prohibited");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.ENUM_DECLARING_CLASS_DEPRECATED, "Enum.declaringClass is deprecated, use declaringJavaClass instead or cast receiver to java.lang.Enum explicitly");
        diagnosticFactoryToRendererMap.put(ErrorsJvm.REDUNDANT_REPEATABLE_ANNOTATION, "Please, remove the ''{0}'' annotation, as ''{1}'' is already enough", Renderers.TO_STRING, Renderers.TO_STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.BASE_CLASS_FIELD_SHADOWS_DERIVED_CLASS_PROPERTY, "Now field from base class {0} shadows the property with custom getter from derived class {1}. This behavior will be changed soon in favor of the property. Please use explicit cast to {0} if you wish to preserve current behavior. See https://youtrack.jetbrains.com/issue/KT-55017 for details", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.BASE_CLASS_FIELD_MAY_SHADOW_DERIVED_CLASS_PROPERTY, "Field from base class {0} may shadow the open property from derived class {1}. This behavior will be changed soon in favor of the property. Please use explicit cast to {0} if you wish to preserve current behavior. See https://youtrack.jetbrains.com/issue/KT-55017 for details", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.BACKING_FIELD_ACCESSED_DUE_TO_PROPERTY_FIELD_CONFLICT, "Property backing field in derived class {1} is accessed instead of the property itself. This happens because of field with the same name in the base class {0}. This behavior will be changed soon in favor of the property. Please use explicit cast to {0} if you wish to preserve current behavior. See https://youtrack.jetbrains.com/issue/KT-55017 for details", CommonRenderers.STRING, CommonRenderers.STRING);
        diagnosticFactoryToRendererMap.put(ErrorsJvm.BASE_CLASS_FIELD_WITH_DIFFERENT_SIGNATURE_THAN_DERIVED_CLASS_PROPERTY, "Now field from base class {0} shadows the property with different type from derived class {1}. This behavior will be changed soon in favor of the property. Please use explicit cast to {0} if you wish to preserve current behavior. See https://youtrack.jetbrains.com/issue/KT-55017 for details", CommonRenderers.STRING, CommonRenderers.STRING);
    }

    static /* synthetic */ String lambda$static$1(ConflictingJvmDeclarationsData conflictingJvmDeclarationsData, RenderingContext renderingContext) {
        List list = (List) conflictingJvmDeclarationsData.getSignatureDescriptors().stream().sorted(MemberComparator.INSTANCE).collect(Collectors.toList());
        final RenderingContext.Impl impl = new RenderingContext.Impl(list);
        return "The following declarations have the same JVM signature (" + conflictingJvmDeclarationsData.getSignature().getName() + conflictingJvmDeclarationsData.getSignature().getDesc() + "):\n" + StringsKt.join(CollectionsKt.map(list, new Function1() { // from class: org.jetbrains.kotlin.resolve.jvm.diagnostics.DefaultErrorMessagesJvm$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DefaultErrorMessagesJvm.lambda$null$0(impl, (DeclarationDescriptor) obj);
            }
        }), "\n");
    }

    static /* synthetic */ String lambda$null$0(RenderingContext renderingContext, DeclarationDescriptor declarationDescriptor) {
        return "    " + Renderers.WITHOUT_MODIFIERS.render(declarationDescriptor, renderingContext);
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages.Extension
    public DiagnosticFactoryToRendererMap getMap() {
        DiagnosticFactoryToRendererMap diagnosticFactoryToRendererMap = MAP;
        if (diagnosticFactoryToRendererMap == null) {
            $$$reportNull$$$0(0);
        }
        return diagnosticFactoryToRendererMap;
    }
}
