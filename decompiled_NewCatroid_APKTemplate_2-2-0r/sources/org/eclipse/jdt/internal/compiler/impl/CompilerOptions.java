package org.eclipse.jdt.internal.compiler.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class CompilerOptions {
    public static final String ALL_STANDARD_TAGS = "all_standard_tags";
    public static final int AccessEmulation = 128;
    public static final int AccidentalBooleanAssign = 262144;
    public static final int AnnotationSuperInterface = 536871424;
    public static final int AssertUsedAsAnIdentifier = 512;
    public static final int AutoBoxing = 536871168;
    public static final int ComparingIdentical = 671088640;
    public static final String DEFAULT = "default";
    public static final String DISABLED = "disabled";
    public static final String DO_NOT_GENERATE = "do not generate";
    public static final int DeadCode = 1073741826;
    public static final int DiscouragedReference = 536887296;
    public static final String ENABLED = "enabled";
    public static final String ERROR = "error";
    public static final int EmptyStatement = 524288;
    public static final int EnumUsedAsAnIdentifier = 536870928;
    public static final int ExplicitlyClosedAutoCloseable = 1073742336;
    public static final int FallthroughCase = 537395200;
    public static final int FieldHiding = 131072;
    public static final int FinalParameterBound = 536870916;
    public static final int FinallyBlockNotCompleting = 16777216;
    public static final int ForbiddenReference = 536870944;
    public static final String GENERATE = "generate";
    public static final String IGNORE = "ignore";
    public static final String INFO = "info";
    public static final int IncompatibleNonInheritedInterfaceMethod = 16384;
    public static final int IndirectStaticAccess = 268435456;
    public static final int InvalidJavadoc = 33554432;
    public static final int LocalVariableHiding = 65536;
    public static final int MaskedCatchBlock = 8;
    public static final int MethodCanBePotentiallyStatic = 1073741856;
    public static final int MethodCanBeStatic = 1073741840;
    public static final int MethodWithConstructorName = 1;
    public static final int MissingDefaultCase = 1073774592;
    public static final int MissingDeprecatedAnnotation = 536879104;
    public static final int MissingEnumConstantCase = 536875008;
    public static final int MissingJavadocComments = 1048576;
    public static final int MissingJavadocTagDescription = 545259520;
    public static final int MissingJavadocTags = 2097152;
    public static final int MissingNonNullByDefaultAnnotation = 1073758208;
    public static final int MissingOverrideAnnotation = 536872960;
    public static final int MissingSerialVersion = 536870920;
    public static final int MissingSynchronizedModifierInInheritedMethod = 805306368;
    public static final String NO_TAG = "no_tag";
    public static final int NoEffectAssignment = 8192;
    public static final int NoImplicitStringConversion = 64;
    public static final int NonExternalizedString = 256;
    public static final int NonNullTypeVariableFromLegacyInvocation = 1074790400;
    public static final int NonStaticAccessToStatic = 2048;
    public static final int NonnullParameterAnnotationDropped = 1073872896;
    public static final int NullAnnotationInferenceConflict = 1073743872;
    public static final int NullReference = 536871040;
    public static final int NullSpecViolation = 1073742848;
    public static final int NullUncheckedConversion = 1073745920;
    public static final String OPTIMIZE_OUT = "optimize out";
    public static final String OPTION_AnnotationBasedNullAnalysis = "org.eclipse.jdt.core.compiler.annotation.nullanalysis";
    public static final String OPTION_Compliance = "org.eclipse.jdt.core.compiler.compliance";
    public static final String OPTION_DocCommentSupport = "org.eclipse.jdt.core.compiler.doc.comment.support";
    public static final String OPTION_EmulateJavacBug8031744 = "org.eclipse.jdt.core.compiler.emulateJavacBug8031744";
    public static final String OPTION_Encoding = "org.eclipse.jdt.core.encoding";
    public static final String OPTION_FatalOptionalError = "org.eclipse.jdt.core.compiler.problem.fatalOptionalError";
    public static final String OPTION_GenerateClassFiles = "org.eclipse.jdt.core.compiler.generateClassFiles";
    public static final String OPTION_IncludeNullInfoFromAsserts = "org.eclipse.jdt.core.compiler.problem.includeNullInfoFromAsserts";
    public static final String OPTION_InheritNullAnnotations = "org.eclipse.jdt.core.compiler.annotation.inheritNullAnnotations";
    public static final String OPTION_InlineJsr = "org.eclipse.jdt.core.compiler.codegen.inlineJsrBytecode";
    public static final String OPTION_LambdaGenericSignature = "org.eclipse.jdt.core.compiler.codegen.lambda.genericSignature";
    public static final String OPTION_LineNumberAttribute = "org.eclipse.jdt.core.compiler.debug.lineNumber";
    public static final String OPTION_LocalVariableAttribute = "org.eclipse.jdt.core.compiler.debug.localVariable";
    public static final String OPTION_MaxProblemPerUnit = "org.eclipse.jdt.core.compiler.maxProblemPerUnit";
    public static final String OPTION_MethodParametersAttribute = "org.eclipse.jdt.core.compiler.codegen.methodParameters";
    public static final String OPTION_NonNullAnnotationName = "org.eclipse.jdt.core.compiler.annotation.nonnull";
    public static final String OPTION_NonNullAnnotationSecondaryNames = "org.eclipse.jdt.core.compiler.annotation.nonnull.secondary";
    public static final String OPTION_NonNullByDefaultAnnotationName = "org.eclipse.jdt.core.compiler.annotation.nonnullbydefault";
    public static final String OPTION_NonNullByDefaultAnnotationSecondaryNames = "org.eclipse.jdt.core.compiler.annotation.nonnullbydefault.secondary";
    public static final String OPTION_NullableAnnotationName = "org.eclipse.jdt.core.compiler.annotation.nullable";
    public static final String OPTION_NullableAnnotationSecondaryNames = "org.eclipse.jdt.core.compiler.annotation.nullable.secondary";
    public static final String OPTION_PessimisticNullAnalysisForFreeTypeVariables = "org.eclipse.jdt.core.compiler.problem.pessimisticNullAnalysisForFreeTypeVariables";
    public static final String OPTION_PreserveUnusedLocal = "org.eclipse.jdt.core.compiler.codegen.unusedLocal";
    public static final String OPTION_Process_Annotations = "org.eclipse.jdt.core.compiler.processAnnotations";
    public static final String OPTION_ReportAnnotationSuperInterface = "org.eclipse.jdt.core.compiler.problem.annotationSuperInterface";
    public static final String OPTION_ReportAssertIdentifier = "org.eclipse.jdt.core.compiler.problem.assertIdentifier";
    public static final String OPTION_ReportAutoboxing = "org.eclipse.jdt.core.compiler.problem.autoboxing";
    public static final String OPTION_ReportComparingIdentical = "org.eclipse.jdt.core.compiler.problem.comparingIdentical";
    public static final String OPTION_ReportDeadCode = "org.eclipse.jdt.core.compiler.problem.deadCode";
    public static final String OPTION_ReportDeadCodeInTrivialIfStatement = "org.eclipse.jdt.core.compiler.problem.deadCodeInTrivialIfStatement";
    public static final String OPTION_ReportDeprecation = "org.eclipse.jdt.core.compiler.problem.deprecation";
    public static final String OPTION_ReportDeprecationInDeprecatedCode = "org.eclipse.jdt.core.compiler.problem.deprecationInDeprecatedCode";
    public static final String OPTION_ReportDeprecationWhenOverridingDeprecatedMethod = "org.eclipse.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod";
    public static final String OPTION_ReportDiscouragedReference = "org.eclipse.jdt.core.compiler.problem.discouragedReference";
    public static final String OPTION_ReportEmptyStatement = "org.eclipse.jdt.core.compiler.problem.emptyStatement";
    public static final String OPTION_ReportEnumIdentifier = "org.eclipse.jdt.core.compiler.problem.enumIdentifier";
    public static final String OPTION_ReportExplicitlyClosedAutoCloseable = "org.eclipse.jdt.core.compiler.problem.explicitlyClosedAutoCloseable";
    public static final String OPTION_ReportFallthroughCase = "org.eclipse.jdt.core.compiler.problem.fallthroughCase";
    public static final String OPTION_ReportFieldHiding = "org.eclipse.jdt.core.compiler.problem.fieldHiding";
    public static final String OPTION_ReportFinalParameterBound = "org.eclipse.jdt.core.compiler.problem.finalParameterBound";
    public static final String OPTION_ReportFinallyBlockNotCompletingNormally = "org.eclipse.jdt.core.compiler.problem.finallyBlockNotCompletingNormally";
    public static final String OPTION_ReportForbiddenReference = "org.eclipse.jdt.core.compiler.problem.forbiddenReference";
    public static final String OPTION_ReportHiddenCatchBlock = "org.eclipse.jdt.core.compiler.problem.hiddenCatchBlock";
    public static final String OPTION_ReportIncompatibleNonInheritedInterfaceMethod = "org.eclipse.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod";
    public static final String OPTION_ReportIncompleteEnumSwitch = "org.eclipse.jdt.core.compiler.problem.incompleteEnumSwitch";
    public static final String OPTION_ReportIndirectStaticAccess = "org.eclipse.jdt.core.compiler.problem.indirectStaticAccess";
    public static final String OPTION_ReportInvalidJavadoc = "org.eclipse.jdt.core.compiler.problem.invalidJavadoc";
    public static final String OPTION_ReportInvalidJavadocTags = "org.eclipse.jdt.core.compiler.problem.invalidJavadocTags";
    public static final String OPTION_ReportInvalidJavadocTagsDeprecatedRef = "org.eclipse.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef";
    public static final String OPTION_ReportInvalidJavadocTagsNotVisibleRef = "org.eclipse.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef";
    public static final String OPTION_ReportInvalidJavadocTagsVisibility = "org.eclipse.jdt.core.compiler.problem.invalidJavadocTagsVisibility";
    public static final String OPTION_ReportLocalVariableHiding = "org.eclipse.jdt.core.compiler.problem.localVariableHiding";
    public static final String OPTION_ReportMethodCanBePotentiallyStatic = "org.eclipse.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic";
    public static final String OPTION_ReportMethodCanBeStatic = "org.eclipse.jdt.core.compiler.problem.reportMethodCanBeStatic";
    public static final String OPTION_ReportMethodWithConstructorName = "org.eclipse.jdt.core.compiler.problem.methodWithConstructorName";
    public static final String OPTION_ReportMissingDefaultCase = "org.eclipse.jdt.core.compiler.problem.missingDefaultCase";
    public static final String OPTION_ReportMissingDeprecatedAnnotation = "org.eclipse.jdt.core.compiler.problem.missingDeprecatedAnnotation";
    public static final String OPTION_ReportMissingEnumCaseDespiteDefault = "org.eclipse.jdt.core.compiler.problem.missingEnumCaseDespiteDefault";
    public static final String OPTION_ReportMissingHashCodeMethod = "org.eclipse.jdt.core.compiler.problem.missingHashCodeMethod";
    public static final String OPTION_ReportMissingJavadocComments = "org.eclipse.jdt.core.compiler.problem.missingJavadocComments";
    public static final String OPTION_ReportMissingJavadocCommentsOverriding = "org.eclipse.jdt.core.compiler.problem.missingJavadocCommentsOverriding";
    public static final String OPTION_ReportMissingJavadocCommentsVisibility = "org.eclipse.jdt.core.compiler.problem.missingJavadocCommentsVisibility";
    public static final String OPTION_ReportMissingJavadocTagDescription = "org.eclipse.jdt.core.compiler.problem.missingJavadocTagDescription";
    public static final String OPTION_ReportMissingJavadocTags = "org.eclipse.jdt.core.compiler.problem.missingJavadocTags";
    public static final String OPTION_ReportMissingJavadocTagsMethodTypeParameters = "org.eclipse.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters";
    public static final String OPTION_ReportMissingJavadocTagsOverriding = "org.eclipse.jdt.core.compiler.problem.missingJavadocTagsOverriding";
    public static final String OPTION_ReportMissingJavadocTagsVisibility = "org.eclipse.jdt.core.compiler.problem.missingJavadocTagsVisibility";
    public static final String OPTION_ReportMissingNonNullByDefaultAnnotation = "org.eclipse.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation";
    public static final String OPTION_ReportMissingOverrideAnnotation = "org.eclipse.jdt.core.compiler.problem.missingOverrideAnnotation";
    public static final String OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation = "org.eclipse.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation";
    public static final String OPTION_ReportMissingSerialVersion = "org.eclipse.jdt.core.compiler.problem.missingSerialVersion";
    public static final String OPTION_ReportMissingSynchronizedOnInheritedMethod = "org.eclipse.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod";
    public static final String OPTION_ReportNoEffectAssignment = "org.eclipse.jdt.core.compiler.problem.noEffectAssignment";
    public static final String OPTION_ReportNoImplicitStringConversion = "org.eclipse.jdt.core.compiler.problem.noImplicitStringConversion";
    public static final String OPTION_ReportNonExternalizedStringLiteral = "org.eclipse.jdt.core.compiler.problem.nonExternalizedStringLiteral";
    public static final String OPTION_ReportNonNullTypeVariableFromLegacyInvocation = "org.eclipse.jdt.core.compiler.problem.nonnullTypeVariableFromLegacyInvocation";
    public static final String OPTION_ReportNonStaticAccessToStatic = "org.eclipse.jdt.core.compiler.problem.staticAccessReceiver";
    public static final String OPTION_ReportNonnullParameterAnnotationDropped = "org.eclipse.jdt.core.compiler.problem.nonnullParameterAnnotationDropped";
    public static final String OPTION_ReportNullAnnotationInferenceConflict = "org.eclipse.jdt.core.compiler.problem.nullAnnotationInferenceConflict";
    public static final String OPTION_ReportNullReference = "org.eclipse.jdt.core.compiler.problem.nullReference";
    public static final String OPTION_ReportNullSpecViolation = "org.eclipse.jdt.core.compiler.problem.nullSpecViolation";
    public static final String OPTION_ReportNullUncheckedConversion = "org.eclipse.jdt.core.compiler.problem.nullUncheckedConversion";
    public static final String OPTION_ReportOverridingMethodWithoutSuperInvocation = "org.eclipse.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation";
    public static final String OPTION_ReportOverridingPackageDefaultMethod = "org.eclipse.jdt.core.compiler.problem.overridingPackageDefaultMethod";
    public static final String OPTION_ReportParameterAssignment = "org.eclipse.jdt.core.compiler.problem.parameterAssignment";
    public static final String OPTION_ReportPossibleAccidentalBooleanAssignment = "org.eclipse.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment";
    public static final String OPTION_ReportPotentialNullReference = "org.eclipse.jdt.core.compiler.problem.potentialNullReference";
    public static final String OPTION_ReportPotentiallyUnclosedCloseable = "org.eclipse.jdt.core.compiler.problem.potentiallyUnclosedCloseable";
    public static final String OPTION_ReportRawTypeReference = "org.eclipse.jdt.core.compiler.problem.rawTypeReference";
    public static final String OPTION_ReportRedundantNullAnnotation = "org.eclipse.jdt.core.compiler.problem.redundantNullAnnotation";
    public static final String OPTION_ReportRedundantNullCheck = "org.eclipse.jdt.core.compiler.problem.redundantNullCheck";
    public static final String OPTION_ReportRedundantSpecificationOfTypeArguments = "org.eclipse.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments";
    public static final String OPTION_ReportRedundantSuperinterface = "org.eclipse.jdt.core.compiler.problem.redundantSuperinterface";
    public static final String OPTION_ReportSpecialParameterHidingField = "org.eclipse.jdt.core.compiler.problem.specialParameterHidingField";
    public static final String OPTION_ReportSyntheticAccessEmulation = "org.eclipse.jdt.core.compiler.problem.syntheticAccessEmulation";
    public static final String OPTION_ReportTasks = "org.eclipse.jdt.core.compiler.problem.tasks";
    public static final String OPTION_ReportTypeParameterHiding = "org.eclipse.jdt.core.compiler.problem.typeParameterHiding";
    public static final String OPTION_ReportUnavoidableGenericTypeProblems = "org.eclipse.jdt.core.compiler.problem.unavoidableGenericTypeProblems";
    public static final String OPTION_ReportUncheckedTypeOperation = "org.eclipse.jdt.core.compiler.problem.uncheckedTypeOperation";
    public static final String OPTION_ReportUnclosedCloseable = "org.eclipse.jdt.core.compiler.problem.unclosedCloseable";
    public static final String OPTION_ReportUndocumentedEmptyBlock = "org.eclipse.jdt.core.compiler.problem.undocumentedEmptyBlock";
    public static final String OPTION_ReportUnhandledWarningToken = "org.eclipse.jdt.core.compiler.problem.unhandledWarningToken";
    public static final String OPTION_ReportUninternedIdentityComparison = "org.eclipse.jdt.core.compiler.problem.uninternedIdentityComparison";
    public static final String OPTION_ReportUnnecessaryElse = "org.eclipse.jdt.core.compiler.problem.unnecessaryElse";
    public static final String OPTION_ReportUnnecessaryTypeCheck = "org.eclipse.jdt.core.compiler.problem.unnecessaryTypeCheck";
    public static final String OPTION_ReportUnqualifiedFieldAccess = "org.eclipse.jdt.core.compiler.problem.unqualifiedFieldAccess";
    public static final String OPTION_ReportUnusedDeclaredThrownException = "org.eclipse.jdt.core.compiler.problem.unusedDeclaredThrownException";
    public static final String OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = "org.eclipse.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable";
    public static final String OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference = "org.eclipse.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference";
    public static final String OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding = "org.eclipse.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding";
    public static final String OPTION_ReportUnusedExceptionParameter = "org.eclipse.jdt.core.compiler.problem.unusedExceptionParameter";
    public static final String OPTION_ReportUnusedImport = "org.eclipse.jdt.core.compiler.problem.unusedImport";
    public static final String OPTION_ReportUnusedLabel = "org.eclipse.jdt.core.compiler.problem.unusedLabel";
    public static final String OPTION_ReportUnusedLocal = "org.eclipse.jdt.core.compiler.problem.unusedLocal";
    public static final String OPTION_ReportUnusedObjectAllocation = "org.eclipse.jdt.core.compiler.problem.unusedObjectAllocation";
    public static final String OPTION_ReportUnusedParameter = "org.eclipse.jdt.core.compiler.problem.unusedParameter";
    public static final String OPTION_ReportUnusedParameterIncludeDocCommentReference = "org.eclipse.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference";
    public static final String OPTION_ReportUnusedParameterWhenImplementingAbstract = "org.eclipse.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract";
    public static final String OPTION_ReportUnusedParameterWhenOverridingConcrete = "org.eclipse.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete";
    public static final String OPTION_ReportUnusedPrivateMember = "org.eclipse.jdt.core.compiler.problem.unusedPrivateMember";
    public static final String OPTION_ReportUnusedTypeArgumentsForMethodInvocation = "org.eclipse.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation";
    public static final String OPTION_ReportUnusedTypeParameter = "org.eclipse.jdt.core.compiler.problem.unusedTypeParameter";
    public static final String OPTION_ReportUnusedWarningToken = "org.eclipse.jdt.core.compiler.problem.unusedWarningToken";
    public static final String OPTION_ReportVarargsArgumentNeedCast = "org.eclipse.jdt.core.compiler.problem.varargsArgumentNeedCast";
    public static final String OPTION_ShareCommonFinallyBlocks = "org.eclipse.jdt.core.compiler.codegen.shareCommonFinallyBlocks";
    public static final String OPTION_Source = "org.eclipse.jdt.core.compiler.source";
    public static final String OPTION_SourceFileAttribute = "org.eclipse.jdt.core.compiler.debug.sourceFile";
    public static final String OPTION_Store_Annotations = "org.eclipse.jdt.core.compiler.storeAnnotations";
    public static final String OPTION_SuppressOptionalErrors = "org.eclipse.jdt.core.compiler.problem.suppressOptionalErrors";
    public static final String OPTION_SuppressWarnings = "org.eclipse.jdt.core.compiler.problem.suppressWarnings";
    public static final String OPTION_SyntacticNullAnalysisForFields = "org.eclipse.jdt.core.compiler.problem.syntacticNullAnalysisForFields";
    public static final String OPTION_TargetPlatform = "org.eclipse.jdt.core.compiler.codegen.targetPlatform";
    public static final String OPTION_TaskCaseSensitive = "org.eclipse.jdt.core.compiler.taskCaseSensitive";
    public static final String OPTION_TaskPriorities = "org.eclipse.jdt.core.compiler.taskPriorities";
    public static final String OPTION_TaskTags = "org.eclipse.jdt.core.compiler.taskTags";
    public static final int OverriddenPackageDefaultMethod = 2;
    public static final int OverridingMethodWithoutSuperInvocation = 537919488;
    public static final String PRESERVE = "preserve";
    public static final String PRIVATE = "private";
    public static final String PROTECTED = "protected";
    public static final String PUBLIC = "public";
    public static final int ParameterAssignment = 537133056;
    public static final int PessimisticNullAnalysisForFreeTypeVariables = 1074266112;
    public static final int PotentialNullReference = 538968064;
    public static final int PotentiallyUnclosedCloseable = 1073742080;
    public static final String RETURN_TAG = "return_tag";
    public static final int RawTypeReference = 536936448;
    public static final int RedundantNullAnnotation = 1073750016;
    public static final int RedundantNullCheck = 541065216;
    public static final int RedundantSpecificationOfTypeArguments = 1073741888;
    public static final int RedundantSuperinterface = 603979776;
    public static final int ShouldImplementHashcode = 1073741825;
    public static final int Task = 4096;
    public static final int Tasks = 1073741828;
    public static final int TypeHiding = 536871936;
    public static final int UncheckedTypeOperation = 536870914;
    public static final int UnclosedCloseable = 1073741952;
    public static final int UndocumentedEmptyBlock = 134217728;
    public static final int UnhandledWarningToken = 536903680;
    public static final int UnnecessaryElse = 536870913;
    public static final int UnnecessaryTypeCheck = 67108864;
    public static final int UnqualifiedFieldAccess = 4194304;
    public static final int UnusedArgument = 32;
    public static final int UnusedDeclaredThrownException = 8388608;
    public static final int UnusedExceptionParameter = 1074003968;
    public static final int UnusedImport = 1024;
    public static final int UnusedLabel = 537001984;
    public static final int UnusedLocalVariable = 16;
    public static final int UnusedObjectAllocation = 1073741832;
    public static final int UnusedPrivateMember = 32768;
    public static final int UnusedTypeArguments = 553648128;
    public static final int UnusedTypeParameter = 1073807360;
    public static final int UnusedWarningToken = 570425344;
    public static final int UsingDeprecatedAPI = 4;
    public static final String VERSION_1_1 = "1.1";
    public static final String VERSION_1_2 = "1.2";
    public static final String VERSION_1_3 = "1.3";
    public static final String VERSION_1_4 = "1.4";
    public static final String VERSION_1_5 = "1.5";
    public static final String VERSION_1_6 = "1.6";
    public static final String VERSION_1_7 = "1.7";
    public static final String VERSION_1_8 = "1.8";
    public static final String VERSION_1_9 = "1.9";
    public static final String VERSION_CLDC1_1 = "cldc1.1";
    public static final String VERSION_JSR14 = "jsr14";
    public static final int VarargsArgumentNeedCast = 536870976;
    public static final String WARNING = "warning";
    public static boolean tolerateIllegalAmbiguousVarargsInvocation;
    public static boolean useunspecdtypeinferenceperformanceoptimization;
    public boolean analyseResourceLeaks;
    public boolean complainOnUninternedIdentityComparison;
    public long complianceLevel;
    public String defaultEncoding;
    public boolean docCommentSupport;
    public boolean emulateJavacBug8031744;
    public boolean enableSyntacticNullAnalysisForFields;
    protected IrritantSet errorThreshold;
    public boolean generateClassFiles;
    public boolean generateGenericSignatureForLambdaExpressions;
    public boolean ignoreMethodBodies;
    public boolean ignoreSourceFolderWarningOption;
    public boolean includeNullInfoFromAsserts;
    protected IrritantSet infoThreshold;
    public boolean inheritNullAnnotations;
    public boolean inlineJsrBytecode;
    public long intendedDefaultNonNullness;
    public boolean isAnnotationBasedNullAnalysisEnabled;
    public boolean isTaskCaseSensitive;
    public int maxProblemsPerUnit;
    public char[][] nonNullAnnotationName;
    public String[] nonNullAnnotationSecondaryNames;
    public char[][] nonNullByDefaultAnnotationName;
    public String[] nonNullByDefaultAnnotationSecondaryNames;
    public char[][] nullableAnnotationName;
    public String[] nullableAnnotationSecondaryNames;
    public long originalComplianceLevel;
    public long originalSourceLevel;
    public boolean parseLiteralExpressionsAsConstants;
    public boolean performMethodsFullRecovery;
    public boolean performStatementsRecovery;
    public boolean pessimisticNullAnalysisForFreeTypeVariablesEnabled;
    public boolean preserveAllLocalVariables;
    public boolean processAnnotations;
    public int produceDebugAttributes;
    public boolean produceMethodParameters;
    public boolean produceReferenceInfo;
    public boolean reportDeadCodeInTrivialIfStatement;
    public boolean reportDeprecationInsideDeprecatedCode;
    public boolean reportDeprecationWhenOverridingDeprecatedMethod;
    public boolean reportInvalidJavadocTags;
    public boolean reportInvalidJavadocTagsDeprecatedRef;
    public boolean reportInvalidJavadocTagsNotVisibleRef;
    public int reportInvalidJavadocTagsVisibility;
    public boolean reportMissingEnumCaseDespiteDefault;
    public boolean reportMissingJavadocCommentsOverriding;
    public int reportMissingJavadocCommentsVisibility;
    public String reportMissingJavadocTagDescription;
    public boolean reportMissingJavadocTagsMethodTypeParameters;
    public boolean reportMissingJavadocTagsOverriding;
    public int reportMissingJavadocTagsVisibility;
    public boolean reportMissingOverrideAnnotationForInterfaceMethodImplementation;
    public boolean reportSpecialParameterHidingField;
    public boolean reportUnavoidableGenericTypeProblems;
    public boolean reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable;
    public boolean reportUnusedDeclaredThrownExceptionIncludeDocCommentReference;
    public boolean reportUnusedDeclaredThrownExceptionWhenOverriding;
    public boolean reportUnusedParameterIncludeDocCommentReference;
    public boolean reportUnusedParameterWhenImplementingAbstract;
    public boolean reportUnusedParameterWhenOverridingConcrete;
    public boolean shareCommonFinallyBlocks;
    public long sourceLevel;
    public boolean storeAnnotations;
    public boolean suppressOptionalErrors;
    public boolean suppressWarnings;
    public long targetJDK;
    public char[][] taskPriorities;
    public char[][] taskTags;
    public boolean treatOptionalErrorAsFatal;
    public Boolean useNullTypeAnnotations;
    public boolean verbose;
    protected IrritantSet warningThreshold;
    static final char[][] DEFAULT_NULLABLE_ANNOTATION_NAME = CharOperation.splitOn('.', "org.eclipse.jdt.annotation.Nullable".toCharArray());
    static final char[][] DEFAULT_NONNULL_ANNOTATION_NAME = CharOperation.splitOn('.', "org.eclipse.jdt.annotation.NonNull".toCharArray());
    static final char[][] DEFAULT_NONNULLBYDEFAULT_ANNOTATION_NAME = CharOperation.splitOn('.', "org.eclipse.jdt.annotation.NonNullByDefault".toCharArray());
    private static final String[] NO_STRINGS = new String[0];
    public static final String[] warningTokens = {DevModeOverwritingStrategies.ALL, "boxing", "cast", "dep-ann", "deprecation", "fallthrough", PsiKeyword.FINALLY, "hiding", "incomplete-switch", "javadoc", "nls", "null", "rawtypes", "resource", SchemaSymbols.ATTVAL_RESTRICTION, "serial", "static-access", "static-method", PsiKeyword.SUPER, "synthetic-access", "sync-override", "unchecked", "unqualified-field-access", "unused"};

    public CompilerOptions() {
        this(null);
    }

    public CompilerOptions(Map<String, String> map) {
        String[] strArr = NO_STRINGS;
        this.nullableAnnotationSecondaryNames = strArr;
        this.nonNullAnnotationSecondaryNames = strArr;
        this.nonNullByDefaultAnnotationSecondaryNames = strArr;
        String property = System.getProperty("tolerateIllegalAmbiguousVarargsInvocation");
        boolean z = false;
        tolerateIllegalAmbiguousVarargsInvocation = property != null && property.equalsIgnoreCase("true");
        String property2 = System.getProperty("useunspecdtypeinferenceperformanceoptimization");
        if (property2 != null && property2.equalsIgnoreCase("true")) {
            z = true;
        }
        useunspecdtypeinferenceperformanceoptimization = z;
        this.emulateJavacBug8031744 = true;
        this.useNullTypeAnnotations = null;
        resetDefaults();
        if (map != null) {
            set(map);
        }
    }

    public CompilerOptions(Map map, boolean z) {
        this(map);
        this.parseLiteralExpressionsAsConstants = z;
    }

    public static String optionKeyFromIrritant(int i) {
        switch (i) {
            case 1:
                return OPTION_ReportMethodWithConstructorName;
            case 2:
                return OPTION_ReportOverridingPackageDefaultMethod;
            case 4:
            case 33554436:
                return OPTION_ReportDeprecation;
            case 8:
                return OPTION_ReportHiddenCatchBlock;
            case 16:
                return OPTION_ReportUnusedLocal;
            case 32:
                return OPTION_ReportUnusedParameter;
            case 64:
                return OPTION_ReportNoImplicitStringConversion;
            case 128:
                return OPTION_ReportSyntheticAccessEmulation;
            case 256:
                return OPTION_ReportNonExternalizedStringLiteral;
            case 512:
                return OPTION_ReportAssertIdentifier;
            case 1024:
                return OPTION_ReportUnusedImport;
            case 2048:
                return OPTION_ReportNonStaticAccessToStatic;
            case 4096:
                return OPTION_TaskTags;
            case 8192:
                return OPTION_ReportNoEffectAssignment;
            case 16384:
                return OPTION_ReportIncompatibleNonInheritedInterfaceMethod;
            case 32768:
                return OPTION_ReportUnusedPrivateMember;
            case 65536:
                return OPTION_ReportLocalVariableHiding;
            case 131072:
                return OPTION_ReportFieldHiding;
            case 262144:
                return OPTION_ReportPossibleAccidentalBooleanAssignment;
            case 524288:
                return OPTION_ReportEmptyStatement;
            case 1048576:
                return OPTION_ReportMissingJavadocComments;
            case 2097152:
                return OPTION_ReportMissingJavadocTags;
            case 4194304:
                return OPTION_ReportUnqualifiedFieldAccess;
            case 8388608:
                return OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding;
            case 16777216:
                return OPTION_ReportFinallyBlockNotCompletingNormally;
            case 33554432:
                return OPTION_ReportInvalidJavadoc;
            case 67108864:
                return OPTION_ReportUnnecessaryTypeCheck;
            case 134217728:
                return OPTION_ReportUndocumentedEmptyBlock;
            case 268435456:
                return OPTION_ReportIndirectStaticAccess;
            case UnnecessaryElse /* 536870913 */:
                return OPTION_ReportUnnecessaryElse;
            case UncheckedTypeOperation /* 536870914 */:
                return OPTION_ReportUncheckedTypeOperation;
            case FinalParameterBound /* 536870916 */:
                return OPTION_ReportFinalParameterBound;
            case MissingSerialVersion /* 536870920 */:
                return OPTION_ReportMissingSerialVersion;
            case EnumUsedAsAnIdentifier /* 536870928 */:
                return OPTION_ReportEnumIdentifier;
            case 536870944:
                return OPTION_ReportForbiddenReference;
            case 536870976:
                return OPTION_ReportVarargsArgumentNeedCast;
            case NullReference /* 536871040 */:
                return OPTION_ReportNullReference;
            case AutoBoxing /* 536871168 */:
                return OPTION_ReportAutoboxing;
            case AnnotationSuperInterface /* 536871424 */:
                return OPTION_ReportAnnotationSuperInterface;
            case TypeHiding /* 536871936 */:
                return OPTION_ReportTypeParameterHiding;
            case MissingOverrideAnnotation /* 536872960 */:
                return OPTION_ReportMissingOverrideAnnotation;
            case MissingEnumConstantCase /* 536875008 */:
                return OPTION_ReportIncompleteEnumSwitch;
            case MissingDeprecatedAnnotation /* 536879104 */:
                return OPTION_ReportMissingDeprecatedAnnotation;
            case DiscouragedReference /* 536887296 */:
                return OPTION_ReportDiscouragedReference;
            case UnhandledWarningToken /* 536903680 */:
                return OPTION_ReportUnhandledWarningToken;
            case RawTypeReference /* 536936448 */:
                return OPTION_ReportRawTypeReference;
            case UnusedLabel /* 537001984 */:
                return OPTION_ReportUnusedLabel;
            case ParameterAssignment /* 537133056 */:
                return OPTION_ReportParameterAssignment;
            case FallthroughCase /* 537395200 */:
                return OPTION_ReportFallthroughCase;
            case OverridingMethodWithoutSuperInvocation /* 537919488 */:
                return OPTION_ReportOverridingMethodWithoutSuperInvocation;
            case PotentialNullReference /* 538968064 */:
                return OPTION_ReportPotentialNullReference;
            case RedundantNullCheck /* 541065216 */:
                return OPTION_ReportRedundantNullCheck;
            case MissingJavadocTagDescription /* 545259520 */:
                return OPTION_ReportMissingJavadocTagDescription;
            case UnusedTypeArguments /* 553648128 */:
                return OPTION_ReportUnusedTypeArgumentsForMethodInvocation;
            case UnusedWarningToken /* 570425344 */:
                return OPTION_ReportUnusedWarningToken;
            case RedundantSuperinterface /* 603979776 */:
                return OPTION_ReportRedundantSuperinterface;
            case ComparingIdentical /* 671088640 */:
                return OPTION_ReportComparingIdentical;
            case 805306368:
                return OPTION_ReportMissingSynchronizedOnInheritedMethod;
            case ShouldImplementHashcode /* 1073741825 */:
                return OPTION_ReportMissingHashCodeMethod;
            case DeadCode /* 1073741826 */:
                return OPTION_ReportDeadCode;
            case UnusedObjectAllocation /* 1073741832 */:
                return OPTION_ReportUnusedObjectAllocation;
            case MethodCanBeStatic /* 1073741840 */:
                return OPTION_ReportMethodCanBeStatic;
            case MethodCanBePotentiallyStatic /* 1073741856 */:
                return OPTION_ReportMethodCanBePotentiallyStatic;
            case RedundantSpecificationOfTypeArguments /* 1073741888 */:
                return OPTION_ReportRedundantSpecificationOfTypeArguments;
            case UnclosedCloseable /* 1073741952 */:
                return OPTION_ReportUnclosedCloseable;
            case PotentiallyUnclosedCloseable /* 1073742080 */:
                return OPTION_ReportPotentiallyUnclosedCloseable;
            case ExplicitlyClosedAutoCloseable /* 1073742336 */:
                return OPTION_ReportExplicitlyClosedAutoCloseable;
            case NullSpecViolation /* 1073742848 */:
                return OPTION_ReportNullSpecViolation;
            case NullAnnotationInferenceConflict /* 1073743872 */:
                return OPTION_ReportNullAnnotationInferenceConflict;
            case NullUncheckedConversion /* 1073745920 */:
                return OPTION_ReportNullUncheckedConversion;
            case RedundantNullAnnotation /* 1073750016 */:
                return OPTION_ReportRedundantNullAnnotation;
            case MissingNonNullByDefaultAnnotation /* 1073758208 */:
                return OPTION_ReportMissingNonNullByDefaultAnnotation;
            case MissingDefaultCase /* 1073774592 */:
                return OPTION_ReportMissingDefaultCase;
            case UnusedTypeParameter /* 1073807360 */:
                return OPTION_ReportUnusedTypeParameter;
            case NonnullParameterAnnotationDropped /* 1073872896 */:
                return OPTION_ReportNonnullParameterAnnotationDropped;
            case UnusedExceptionParameter /* 1074003968 */:
                return OPTION_ReportUnusedExceptionParameter;
            case PessimisticNullAnalysisForFreeTypeVariables /* 1074266112 */:
                return OPTION_PessimisticNullAnalysisForFreeTypeVariables;
            case NonNullTypeVariableFromLegacyInvocation /* 1074790400 */:
                return OPTION_ReportNonNullTypeVariableFromLegacyInvocation;
            default:
                return null;
        }
    }

    public static String versionFromJdkLevel(long j) {
        switch ((int) (j >> 16)) {
            case 45:
                if (j == ClassFileConstants.JDK1_1) {
                    return VERSION_1_1;
                }
                break;
            case 46:
                if (j == ClassFileConstants.JDK1_2) {
                    return VERSION_1_2;
                }
                break;
            case 47:
                if (j == ClassFileConstants.JDK1_3) {
                    return VERSION_1_3;
                }
                break;
            case 48:
                if (j == ClassFileConstants.JDK1_4) {
                    return VERSION_1_4;
                }
                break;
            case 49:
                if (j == ClassFileConstants.JDK1_5) {
                    return VERSION_1_5;
                }
                break;
            case 50:
                if (j == ClassFileConstants.JDK1_6) {
                    return VERSION_1_6;
                }
                break;
            case 51:
                if (j == ClassFileConstants.JDK1_7) {
                    return VERSION_1_7;
                }
                break;
            case 52:
                if (j == ClassFileConstants.JDK1_8) {
                    return VERSION_1_8;
                }
                break;
            case 53:
                if (j == ClassFileConstants.JDK1_9) {
                    return VERSION_1_9;
                }
                break;
        }
        return Util.EMPTY_STRING;
    }

    public static long versionToJdkLevel(String str) {
        if (str == null || str.length() != 3 || str.charAt(0) != '1' || str.charAt(1) != '.') {
            if (VERSION_JSR14.equals(str)) {
                return ClassFileConstants.JDK1_4;
            }
            if (VERSION_CLDC1_1.equals(str)) {
                return ClassFileConstants.CLDC_1_1;
            }
            return 0L;
        }
        switch (str.charAt(2)) {
            case '1':
                return ClassFileConstants.JDK1_1;
            case '2':
                return ClassFileConstants.JDK1_2;
            case '3':
                return ClassFileConstants.JDK1_3;
            case '4':
                return ClassFileConstants.JDK1_4;
            case '5':
                return ClassFileConstants.JDK1_5;
            case '6':
                return ClassFileConstants.JDK1_6;
            case '7':
                return ClassFileConstants.JDK1_7;
            case '8':
                return ClassFileConstants.JDK1_8;
            case '9':
                return ClassFileConstants.JDK1_9;
            default:
                return 0L;
        }
    }

    public static String[] warningOptionNames() {
        return new String[]{OPTION_ReportAnnotationSuperInterface, OPTION_ReportAssertIdentifier, OPTION_ReportAutoboxing, OPTION_ReportComparingIdentical, OPTION_ReportDeadCode, OPTION_ReportDeadCodeInTrivialIfStatement, OPTION_ReportDeprecation, OPTION_ReportDeprecationInDeprecatedCode, OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, OPTION_ReportDiscouragedReference, OPTION_ReportEmptyStatement, OPTION_ReportEnumIdentifier, OPTION_ReportFallthroughCase, OPTION_ReportFieldHiding, OPTION_ReportFinallyBlockNotCompletingNormally, OPTION_ReportFinalParameterBound, OPTION_ReportForbiddenReference, OPTION_ReportHiddenCatchBlock, OPTION_ReportIncompatibleNonInheritedInterfaceMethod, OPTION_ReportMissingDefaultCase, OPTION_ReportIncompleteEnumSwitch, OPTION_ReportMissingEnumCaseDespiteDefault, OPTION_ReportIndirectStaticAccess, OPTION_ReportInvalidJavadoc, OPTION_ReportInvalidJavadocTags, OPTION_ReportInvalidJavadocTagsDeprecatedRef, OPTION_ReportInvalidJavadocTagsNotVisibleRef, OPTION_ReportInvalidJavadocTagsVisibility, OPTION_ReportLocalVariableHiding, OPTION_ReportMethodCanBePotentiallyStatic, OPTION_ReportMethodCanBeStatic, OPTION_ReportMethodWithConstructorName, OPTION_ReportMissingDeprecatedAnnotation, OPTION_ReportMissingHashCodeMethod, OPTION_ReportMissingJavadocComments, OPTION_ReportMissingJavadocCommentsOverriding, OPTION_ReportMissingJavadocCommentsVisibility, OPTION_ReportMissingJavadocTagDescription, OPTION_ReportMissingJavadocTags, OPTION_ReportMissingJavadocTagsMethodTypeParameters, OPTION_ReportMissingJavadocTagsOverriding, OPTION_ReportMissingJavadocTagsVisibility, OPTION_ReportMissingOverrideAnnotation, OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation, OPTION_ReportMissingSerialVersion, OPTION_ReportMissingSynchronizedOnInheritedMethod, OPTION_ReportNoEffectAssignment, OPTION_ReportNoImplicitStringConversion, OPTION_ReportNonExternalizedStringLiteral, OPTION_ReportNonStaticAccessToStatic, OPTION_ReportNullReference, OPTION_ReportOverridingMethodWithoutSuperInvocation, OPTION_ReportOverridingPackageDefaultMethod, OPTION_ReportParameterAssignment, OPTION_ReportPossibleAccidentalBooleanAssignment, OPTION_ReportPotentialNullReference, OPTION_ReportRawTypeReference, OPTION_ReportRedundantNullCheck, OPTION_ReportRedundantSuperinterface, OPTION_ReportRedundantSpecificationOfTypeArguments, OPTION_ReportSpecialParameterHidingField, OPTION_ReportSyntheticAccessEmulation, OPTION_ReportTasks, OPTION_ReportTypeParameterHiding, OPTION_ReportUnavoidableGenericTypeProblems, OPTION_ReportUncheckedTypeOperation, OPTION_ReportUndocumentedEmptyBlock, OPTION_ReportUnhandledWarningToken, OPTION_ReportUnnecessaryElse, OPTION_ReportUnnecessaryTypeCheck, OPTION_ReportUnqualifiedFieldAccess, OPTION_ReportUnusedDeclaredThrownException, OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable, OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference, OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding, OPTION_ReportUnusedImport, OPTION_ReportUnusedLabel, OPTION_ReportUnusedLocal, OPTION_ReportUnusedObjectAllocation, OPTION_ReportUnusedParameter, OPTION_ReportUnusedExceptionParameter, OPTION_ReportUnusedParameterIncludeDocCommentReference, OPTION_ReportUnusedParameterWhenImplementingAbstract, OPTION_ReportUnusedParameterWhenOverridingConcrete, OPTION_ReportUnusedPrivateMember, OPTION_ReportUnusedTypeArgumentsForMethodInvocation, OPTION_ReportUnusedWarningToken, OPTION_ReportVarargsArgumentNeedCast, OPTION_ReportUnclosedCloseable, OPTION_ReportPotentiallyUnclosedCloseable, OPTION_ReportExplicitlyClosedAutoCloseable, OPTION_AnnotationBasedNullAnalysis, OPTION_NonNullAnnotationName, OPTION_NullableAnnotationName, OPTION_NonNullByDefaultAnnotationName, OPTION_ReportMissingNonNullByDefaultAnnotation, OPTION_ReportNullSpecViolation, OPTION_ReportNullAnnotationInferenceConflict, OPTION_ReportNullUncheckedConversion, OPTION_ReportRedundantNullAnnotation, OPTION_SyntacticNullAnalysisForFields, OPTION_ReportUnusedTypeParameter, OPTION_InheritNullAnnotations, OPTION_ReportNonnullParameterAnnotationDropped};
    }

    public static String warningTokenFromIrritant(int i) {
        switch (i) {
            case 4:
            case 33554436:
                return "deprecation";
            case 8:
            case 65536:
            case 131072:
                return "hiding";
            case 16:
            case 32:
            case 1024:
            case 32768:
            case 8388608:
            case UnusedLabel /* 537001984 */:
            case UnusedTypeArguments /* 553648128 */:
            case RedundantSuperinterface /* 603979776 */:
            case DeadCode /* 1073741826 */:
            case UnusedObjectAllocation /* 1073741832 */:
            case RedundantSpecificationOfTypeArguments /* 1073741888 */:
            case UnusedTypeParameter /* 1073807360 */:
            case UnusedExceptionParameter /* 1074003968 */:
                return "unused";
            case 128:
                return "synthetic-access";
            case 256:
                return "nls";
            case 2048:
            case 268435456:
                return "static-access";
            case 1048576:
            case 2097152:
            case 33554432:
                return "javadoc";
            case 4194304:
                return "unqualified-field-access";
            case 16777216:
                return PsiKeyword.FINALLY;
            case 67108864:
                return "cast";
            case UncheckedTypeOperation /* 536870914 */:
                return "unchecked";
            case MissingSerialVersion /* 536870920 */:
                return "serial";
            case 536870944:
            case DiscouragedReference /* 536887296 */:
                return SchemaSymbols.ATTVAL_RESTRICTION;
            case NullReference /* 536871040 */:
            case PotentialNullReference /* 538968064 */:
            case RedundantNullCheck /* 541065216 */:
            case NullSpecViolation /* 1073742848 */:
            case NullAnnotationInferenceConflict /* 1073743872 */:
            case NullUncheckedConversion /* 1073745920 */:
            case RedundantNullAnnotation /* 1073750016 */:
            case MissingNonNullByDefaultAnnotation /* 1073758208 */:
            case NonnullParameterAnnotationDropped /* 1073872896 */:
            case PessimisticNullAnalysisForFreeTypeVariables /* 1074266112 */:
            case NonNullTypeVariableFromLegacyInvocation /* 1074790400 */:
                return "null";
            case AutoBoxing /* 536871168 */:
                return "boxing";
            case TypeHiding /* 536871936 */:
                return "hiding";
            case MissingEnumConstantCase /* 536875008 */:
            case MissingDefaultCase /* 1073774592 */:
                return "incomplete-switch";
            case MissingDeprecatedAnnotation /* 536879104 */:
                return "dep-ann";
            case RawTypeReference /* 536936448 */:
                return "rawtypes";
            case FallthroughCase /* 537395200 */:
                return "fallthrough";
            case OverridingMethodWithoutSuperInvocation /* 537919488 */:
                return PsiKeyword.SUPER;
            case 805306368:
                return "sync-override";
            case MethodCanBeStatic /* 1073741840 */:
            case MethodCanBePotentiallyStatic /* 1073741856 */:
                return "static-method";
            case UnclosedCloseable /* 1073741952 */:
            case PotentiallyUnclosedCloseable /* 1073742080 */:
            case ExplicitlyClosedAutoCloseable /* 1073742336 */:
                return "resource";
            default:
                return null;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static IrritantSet warningTokenToIrritants(String str) {
        if (str != null && str.length() != 0) {
            switch (str.charAt(0)) {
                case 'a':
                    if (DevModeOverwritingStrategies.ALL.equals(str)) {
                        return IrritantSet.ALL;
                    }
                    break;
                case 'b':
                    if ("boxing".equals(str)) {
                        return IrritantSet.BOXING;
                    }
                    break;
                case 'c':
                    if ("cast".equals(str)) {
                        return IrritantSet.CAST;
                    }
                    break;
                case 'd':
                    if ("deprecation".equals(str)) {
                        return IrritantSet.DEPRECATION;
                    }
                    if ("dep-ann".equals(str)) {
                        return IrritantSet.DEP_ANN;
                    }
                    break;
                case 'f':
                    if ("fallthrough".equals(str)) {
                        return IrritantSet.FALLTHROUGH;
                    }
                    if (PsiKeyword.FINALLY.equals(str)) {
                        return IrritantSet.FINALLY;
                    }
                    break;
                case 'h':
                    if ("hiding".equals(str)) {
                        return IrritantSet.HIDING;
                    }
                    break;
                case 'i':
                    if ("incomplete-switch".equals(str)) {
                        return IrritantSet.INCOMPLETE_SWITCH;
                    }
                    break;
                case 'j':
                    if ("javadoc".equals(str)) {
                        return IrritantSet.JAVADOC;
                    }
                    break;
                case 'n':
                    if ("nls".equals(str)) {
                        return IrritantSet.NLS;
                    }
                    if ("null".equals(str)) {
                        return IrritantSet.NULL;
                    }
                    break;
                case 'r':
                    if ("rawtypes".equals(str)) {
                        return IrritantSet.RAW;
                    }
                    if ("resource".equals(str)) {
                        return IrritantSet.RESOURCE;
                    }
                    if (SchemaSymbols.ATTVAL_RESTRICTION.equals(str)) {
                        return IrritantSet.RESTRICTION;
                    }
                    break;
                case 's':
                    if ("serial".equals(str)) {
                        return IrritantSet.SERIAL;
                    }
                    if ("static-access".equals(str)) {
                        return IrritantSet.STATIC_ACCESS;
                    }
                    if ("static-method".equals(str)) {
                        return IrritantSet.STATIC_METHOD;
                    }
                    if ("synthetic-access".equals(str)) {
                        return IrritantSet.SYNTHETIC_ACCESS;
                    }
                    if (PsiKeyword.SUPER.equals(str)) {
                        return IrritantSet.SUPER;
                    }
                    if ("sync-override".equals(str)) {
                        return IrritantSet.SYNCHRONIZED;
                    }
                    break;
                case 'u':
                    if ("unused".equals(str)) {
                        return IrritantSet.UNUSED;
                    }
                    if ("unchecked".equals(str)) {
                        return IrritantSet.UNCHECKED;
                    }
                    if ("unqualified-field-access".equals(str)) {
                        return IrritantSet.UNQUALIFIED_FIELD_ACCESS;
                    }
                    break;
            }
        }
        return null;
    }

    public Map<String, String> getMap() {
        HashMap map = new HashMap(30);
        int i = this.produceDebugAttributes & 4;
        String str = GENERATE;
        map.put(OPTION_LocalVariableAttribute, i != 0 ? GENERATE : DO_NOT_GENERATE);
        map.put(OPTION_LineNumberAttribute, (this.produceDebugAttributes & 2) != 0 ? GENERATE : DO_NOT_GENERATE);
        map.put(OPTION_SourceFileAttribute, (this.produceDebugAttributes & 1) != 0 ? GENERATE : DO_NOT_GENERATE);
        map.put(OPTION_MethodParametersAttribute, this.produceMethodParameters ? GENERATE : DO_NOT_GENERATE);
        if (!this.generateGenericSignatureForLambdaExpressions) {
            str = DO_NOT_GENERATE;
        }
        map.put(OPTION_LambdaGenericSignature, str);
        map.put(OPTION_PreserveUnusedLocal, this.preserveAllLocalVariables ? "preserve" : OPTIMIZE_OUT);
        boolean z = this.docCommentSupport;
        String str2 = ENABLED;
        map.put(OPTION_DocCommentSupport, z ? ENABLED : DISABLED);
        map.put(OPTION_ReportMethodWithConstructorName, getSeverityString(1));
        map.put(OPTION_ReportOverridingPackageDefaultMethod, getSeverityString(2));
        map.put(OPTION_ReportDeprecation, getSeverityString(4));
        map.put(OPTION_ReportDeprecationInDeprecatedCode, this.reportDeprecationInsideDeprecatedCode ? ENABLED : DISABLED);
        map.put(OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, this.reportDeprecationWhenOverridingDeprecatedMethod ? ENABLED : DISABLED);
        map.put(OPTION_ReportHiddenCatchBlock, getSeverityString(8));
        map.put(OPTION_ReportUnusedLocal, getSeverityString(16));
        map.put(OPTION_ReportUnusedParameter, getSeverityString(32));
        map.put(OPTION_ReportUnusedExceptionParameter, getSeverityString(UnusedExceptionParameter));
        map.put(OPTION_ReportUnusedImport, getSeverityString(1024));
        map.put(OPTION_ReportSyntheticAccessEmulation, getSeverityString(128));
        map.put(OPTION_ReportNoEffectAssignment, getSeverityString(8192));
        map.put(OPTION_ReportNonExternalizedStringLiteral, getSeverityString(256));
        map.put(OPTION_ReportNoImplicitStringConversion, getSeverityString(64));
        map.put(OPTION_ReportNonStaticAccessToStatic, getSeverityString(2048));
        map.put(OPTION_ReportIndirectStaticAccess, getSeverityString(268435456));
        map.put(OPTION_ReportIncompatibleNonInheritedInterfaceMethod, getSeverityString(16384));
        map.put(OPTION_ReportUnusedPrivateMember, getSeverityString(32768));
        map.put(OPTION_ReportLocalVariableHiding, getSeverityString(65536));
        map.put(OPTION_ReportFieldHiding, getSeverityString(131072));
        map.put(OPTION_ReportTypeParameterHiding, getSeverityString(TypeHiding));
        map.put(OPTION_ReportPossibleAccidentalBooleanAssignment, getSeverityString(262144));
        map.put(OPTION_ReportEmptyStatement, getSeverityString(524288));
        map.put(OPTION_ReportAssertIdentifier, getSeverityString(512));
        map.put(OPTION_ReportEnumIdentifier, getSeverityString(EnumUsedAsAnIdentifier));
        map.put(OPTION_ReportUndocumentedEmptyBlock, getSeverityString(134217728));
        map.put(OPTION_ReportUnnecessaryTypeCheck, getSeverityString(67108864));
        map.put(OPTION_ReportUnnecessaryElse, getSeverityString(UnnecessaryElse));
        map.put(OPTION_ReportAutoboxing, getSeverityString(AutoBoxing));
        map.put(OPTION_ReportAnnotationSuperInterface, getSeverityString(AnnotationSuperInterface));
        map.put(OPTION_ReportIncompleteEnumSwitch, getSeverityString(MissingEnumConstantCase));
        map.put(OPTION_ReportMissingEnumCaseDespiteDefault, this.reportMissingEnumCaseDespiteDefault ? ENABLED : DISABLED);
        map.put(OPTION_ReportMissingDefaultCase, getSeverityString(MissingDefaultCase));
        map.put(OPTION_ReportInvalidJavadoc, getSeverityString(33554432));
        map.put(OPTION_ReportInvalidJavadocTagsVisibility, getVisibilityString(this.reportInvalidJavadocTagsVisibility));
        map.put(OPTION_ReportInvalidJavadocTags, this.reportInvalidJavadocTags ? ENABLED : DISABLED);
        map.put(OPTION_ReportInvalidJavadocTagsDeprecatedRef, this.reportInvalidJavadocTagsDeprecatedRef ? ENABLED : DISABLED);
        map.put(OPTION_ReportInvalidJavadocTagsNotVisibleRef, this.reportInvalidJavadocTagsNotVisibleRef ? ENABLED : DISABLED);
        map.put(OPTION_ReportMissingJavadocTags, getSeverityString(2097152));
        map.put(OPTION_ReportMissingJavadocTagsVisibility, getVisibilityString(this.reportMissingJavadocTagsVisibility));
        map.put(OPTION_ReportMissingJavadocTagsOverriding, this.reportMissingJavadocTagsOverriding ? ENABLED : DISABLED);
        map.put(OPTION_ReportMissingJavadocTagsMethodTypeParameters, this.reportMissingJavadocTagsMethodTypeParameters ? ENABLED : DISABLED);
        map.put(OPTION_ReportMissingJavadocComments, getSeverityString(1048576));
        map.put(OPTION_ReportMissingJavadocTagDescription, this.reportMissingJavadocTagDescription);
        map.put(OPTION_ReportMissingJavadocCommentsVisibility, getVisibilityString(this.reportMissingJavadocCommentsVisibility));
        map.put(OPTION_ReportMissingJavadocCommentsOverriding, this.reportMissingJavadocCommentsOverriding ? ENABLED : DISABLED);
        map.put(OPTION_ReportFinallyBlockNotCompletingNormally, getSeverityString(16777216));
        map.put(OPTION_ReportUnusedDeclaredThrownException, getSeverityString(8388608));
        map.put(OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding, this.reportUnusedDeclaredThrownExceptionWhenOverriding ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference, this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable, this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnqualifiedFieldAccess, getSeverityString(4194304));
        map.put(OPTION_ReportUnavoidableGenericTypeProblems, this.reportUnavoidableGenericTypeProblems ? ENABLED : DISABLED);
        map.put(OPTION_ReportUncheckedTypeOperation, getSeverityString(UncheckedTypeOperation));
        map.put(OPTION_ReportRawTypeReference, getSeverityString(RawTypeReference));
        map.put(OPTION_ReportFinalParameterBound, getSeverityString(FinalParameterBound));
        map.put(OPTION_ReportMissingSerialVersion, getSeverityString(MissingSerialVersion));
        map.put(OPTION_ReportForbiddenReference, getSeverityString(536870944));
        map.put(OPTION_ReportDiscouragedReference, getSeverityString(DiscouragedReference));
        map.put(OPTION_ReportVarargsArgumentNeedCast, getSeverityString(536870976));
        map.put(OPTION_ReportMissingOverrideAnnotation, getSeverityString(MissingOverrideAnnotation));
        map.put(OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation, this.reportMissingOverrideAnnotationForInterfaceMethodImplementation ? ENABLED : DISABLED);
        map.put(OPTION_ReportMissingDeprecatedAnnotation, getSeverityString(MissingDeprecatedAnnotation));
        map.put(OPTION_ReportUnusedLabel, getSeverityString(UnusedLabel));
        map.put(OPTION_ReportUnusedTypeArgumentsForMethodInvocation, getSeverityString(UnusedTypeArguments));
        map.put(OPTION_Compliance, versionFromJdkLevel(this.complianceLevel));
        map.put(OPTION_Source, versionFromJdkLevel(this.sourceLevel));
        map.put(OPTION_TargetPlatform, versionFromJdkLevel(this.targetJDK));
        map.put(OPTION_FatalOptionalError, this.treatOptionalErrorAsFatal ? ENABLED : DISABLED);
        String str3 = this.defaultEncoding;
        if (str3 != null) {
            map.put(OPTION_Encoding, str3);
        }
        map.put(OPTION_TaskTags, this.taskTags == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskTags, ',')));
        map.put(OPTION_TaskPriorities, this.taskPriorities == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskPriorities, ',')));
        map.put(OPTION_TaskCaseSensitive, this.isTaskCaseSensitive ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnusedParameterWhenImplementingAbstract, this.reportUnusedParameterWhenImplementingAbstract ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnusedParameterWhenOverridingConcrete, this.reportUnusedParameterWhenOverridingConcrete ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnusedParameterIncludeDocCommentReference, this.reportUnusedParameterIncludeDocCommentReference ? ENABLED : DISABLED);
        map.put(OPTION_ReportSpecialParameterHidingField, this.reportSpecialParameterHidingField ? ENABLED : DISABLED);
        map.put(OPTION_MaxProblemPerUnit, String.valueOf(this.maxProblemsPerUnit));
        map.put(OPTION_InlineJsr, this.inlineJsrBytecode ? ENABLED : DISABLED);
        map.put(OPTION_ShareCommonFinallyBlocks, this.shareCommonFinallyBlocks ? ENABLED : DISABLED);
        map.put(OPTION_ReportNullReference, getSeverityString(NullReference));
        map.put(OPTION_ReportPotentialNullReference, getSeverityString(PotentialNullReference));
        map.put(OPTION_ReportRedundantNullCheck, getSeverityString(RedundantNullCheck));
        map.put(OPTION_SuppressWarnings, this.suppressWarnings ? ENABLED : DISABLED);
        map.put(OPTION_SuppressOptionalErrors, this.suppressOptionalErrors ? ENABLED : DISABLED);
        map.put(OPTION_ReportUnhandledWarningToken, getSeverityString(UnhandledWarningToken));
        map.put(OPTION_ReportUnusedWarningToken, getSeverityString(UnusedWarningToken));
        map.put(OPTION_ReportParameterAssignment, getSeverityString(ParameterAssignment));
        map.put(OPTION_ReportFallthroughCase, getSeverityString(FallthroughCase));
        map.put(OPTION_ReportOverridingMethodWithoutSuperInvocation, getSeverityString(OverridingMethodWithoutSuperInvocation));
        map.put(OPTION_GenerateClassFiles, this.generateClassFiles ? ENABLED : DISABLED);
        map.put(OPTION_Process_Annotations, this.processAnnotations ? ENABLED : DISABLED);
        map.put(OPTION_Store_Annotations, this.storeAnnotations ? ENABLED : DISABLED);
        map.put(OPTION_EmulateJavacBug8031744, this.emulateJavacBug8031744 ? ENABLED : DISABLED);
        map.put(OPTION_ReportRedundantSuperinterface, getSeverityString(RedundantSuperinterface));
        map.put(OPTION_ReportComparingIdentical, getSeverityString(ComparingIdentical));
        map.put(OPTION_ReportMissingSynchronizedOnInheritedMethod, getSeverityString(805306368));
        map.put(OPTION_ReportMissingHashCodeMethod, getSeverityString(ShouldImplementHashcode));
        map.put(OPTION_ReportDeadCode, getSeverityString(DeadCode));
        map.put(OPTION_ReportDeadCodeInTrivialIfStatement, this.reportDeadCodeInTrivialIfStatement ? ENABLED : DISABLED);
        map.put(OPTION_ReportTasks, getSeverityString(Tasks));
        map.put(OPTION_ReportUnusedObjectAllocation, getSeverityString(UnusedObjectAllocation));
        map.put(OPTION_IncludeNullInfoFromAsserts, this.includeNullInfoFromAsserts ? ENABLED : DISABLED);
        map.put(OPTION_ReportMethodCanBeStatic, getSeverityString(MethodCanBeStatic));
        map.put(OPTION_ReportMethodCanBePotentiallyStatic, getSeverityString(MethodCanBePotentiallyStatic));
        map.put(OPTION_ReportRedundantSpecificationOfTypeArguments, getSeverityString(RedundantSpecificationOfTypeArguments));
        map.put(OPTION_ReportUnclosedCloseable, getSeverityString(UnclosedCloseable));
        map.put(OPTION_ReportPotentiallyUnclosedCloseable, getSeverityString(PotentiallyUnclosedCloseable));
        map.put(OPTION_ReportExplicitlyClosedAutoCloseable, getSeverityString(ExplicitlyClosedAutoCloseable));
        map.put(OPTION_AnnotationBasedNullAnalysis, this.isAnnotationBasedNullAnalysisEnabled ? ENABLED : DISABLED);
        map.put(OPTION_ReportNullSpecViolation, getSeverityString(NullSpecViolation));
        map.put(OPTION_ReportNullAnnotationInferenceConflict, getSeverityString(NullAnnotationInferenceConflict));
        map.put(OPTION_ReportNullUncheckedConversion, getSeverityString(NullUncheckedConversion));
        map.put(OPTION_ReportRedundantNullAnnotation, getSeverityString(RedundantNullAnnotation));
        map.put(OPTION_NullableAnnotationName, String.valueOf(CharOperation.concatWith(this.nullableAnnotationName, '.')));
        map.put(OPTION_NonNullAnnotationName, String.valueOf(CharOperation.concatWith(this.nonNullAnnotationName, '.')));
        map.put(OPTION_NonNullByDefaultAnnotationName, String.valueOf(CharOperation.concatWith(this.nonNullByDefaultAnnotationName, '.')));
        map.put(OPTION_NullableAnnotationSecondaryNames, nameListToString(this.nullableAnnotationSecondaryNames));
        map.put(OPTION_NonNullAnnotationSecondaryNames, nameListToString(this.nonNullAnnotationSecondaryNames));
        map.put(OPTION_NonNullByDefaultAnnotationSecondaryNames, nameListToString(this.nonNullByDefaultAnnotationSecondaryNames));
        map.put(OPTION_ReportMissingNonNullByDefaultAnnotation, getSeverityString(MissingNonNullByDefaultAnnotation));
        map.put(OPTION_ReportUnusedTypeParameter, getSeverityString(UnusedTypeParameter));
        map.put(OPTION_SyntacticNullAnalysisForFields, this.enableSyntacticNullAnalysisForFields ? ENABLED : DISABLED);
        map.put(OPTION_InheritNullAnnotations, this.inheritNullAnnotations ? ENABLED : DISABLED);
        map.put(OPTION_ReportNonnullParameterAnnotationDropped, getSeverityString(NonnullParameterAnnotationDropped));
        if (!this.complainOnUninternedIdentityComparison) {
            str2 = DISABLED;
        }
        map.put(OPTION_ReportUninternedIdentityComparison, str2);
        map.put(OPTION_PessimisticNullAnalysisForFreeTypeVariables, getSeverityString(PessimisticNullAnalysisForFreeTypeVariables));
        map.put(OPTION_ReportNonNullTypeVariableFromLegacyInvocation, getSeverityString(NonNullTypeVariableFromLegacyInvocation));
        return map;
    }

    public int getSeverity(int i) {
        if (this.errorThreshold.isSet(i)) {
            return ((i & (-503316480)) != 570425344 && this.treatOptionalErrorAsFatal) ? 161 : 33;
        }
        if (this.warningThreshold.isSet(i)) {
            return 32;
        }
        return this.infoThreshold.isSet(i) ? 1056 : 256;
    }

    public String getSeverityString(int i) {
        if (this.errorThreshold.isSet(i)) {
            return "error";
        }
        if (this.warningThreshold.isSet(i)) {
            return WARNING;
        }
        if (this.infoThreshold.isSet(i)) {
            return INFO;
        }
        return "ignore";
    }

    public String getVisibilityString(int i) {
        int i2 = i & 7;
        if (i2 == 1) {
            return "public";
        }
        if (i2 == 2) {
            return "private";
        }
        if (i2 == 4) {
            return "protected";
        }
        return "default";
    }

    public boolean isAnyEnabled(IrritantSet irritantSet) {
        return this.warningThreshold.isAnySet(irritantSet) || this.errorThreshold.isAnySet(irritantSet) || this.infoThreshold.isAnySet(irritantSet);
    }

    protected void resetDefaults() {
        this.errorThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_ERRORS);
        this.warningThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_WARNINGS);
        this.infoThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_INFOS);
        this.produceDebugAttributes = 3;
        this.originalComplianceLevel = ClassFileConstants.JDK1_4;
        this.complianceLevel = ClassFileConstants.JDK1_4;
        this.originalSourceLevel = ClassFileConstants.JDK1_3;
        this.sourceLevel = ClassFileConstants.JDK1_3;
        this.targetJDK = ClassFileConstants.JDK1_2;
        this.defaultEncoding = null;
        this.verbose = Compiler.DEBUG;
        this.produceReferenceInfo = false;
        this.preserveAllLocalVariables = false;
        this.produceMethodParameters = false;
        this.parseLiteralExpressionsAsConstants = true;
        this.maxProblemsPerUnit = 100;
        this.taskTags = null;
        this.taskPriorities = null;
        this.isTaskCaseSensitive = true;
        this.reportDeprecationInsideDeprecatedCode = false;
        this.reportDeprecationWhenOverridingDeprecatedMethod = false;
        this.reportUnusedParameterWhenImplementingAbstract = false;
        this.reportUnusedParameterWhenOverridingConcrete = false;
        this.reportUnusedParameterIncludeDocCommentReference = true;
        this.reportUnusedDeclaredThrownExceptionWhenOverriding = false;
        this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = true;
        this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = true;
        this.reportSpecialParameterHidingField = false;
        this.reportUnavoidableGenericTypeProblems = true;
        this.reportInvalidJavadocTagsVisibility = 1;
        this.reportInvalidJavadocTags = false;
        this.reportInvalidJavadocTagsDeprecatedRef = false;
        this.reportInvalidJavadocTagsNotVisibleRef = false;
        this.reportMissingJavadocTagDescription = RETURN_TAG;
        this.reportMissingJavadocTagsVisibility = 1;
        this.reportMissingJavadocTagsOverriding = false;
        this.reportMissingJavadocTagsMethodTypeParameters = false;
        this.reportMissingJavadocCommentsVisibility = 1;
        this.reportMissingJavadocCommentsOverriding = false;
        this.inlineJsrBytecode = false;
        this.shareCommonFinallyBlocks = false;
        this.docCommentSupport = false;
        this.suppressWarnings = true;
        this.suppressOptionalErrors = false;
        this.treatOptionalErrorAsFatal = false;
        this.performMethodsFullRecovery = true;
        this.performStatementsRecovery = true;
        this.storeAnnotations = false;
        this.generateClassFiles = true;
        this.processAnnotations = false;
        this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = true;
        this.reportDeadCodeInTrivialIfStatement = false;
        this.ignoreMethodBodies = false;
        this.ignoreSourceFolderWarningOption = false;
        this.includeNullInfoFromAsserts = false;
        this.isAnnotationBasedNullAnalysisEnabled = false;
        this.nullableAnnotationName = DEFAULT_NULLABLE_ANNOTATION_NAME;
        this.nonNullAnnotationName = DEFAULT_NONNULL_ANNOTATION_NAME;
        this.nonNullByDefaultAnnotationName = DEFAULT_NONNULLBYDEFAULT_ANNOTATION_NAME;
        this.intendedDefaultNonNullness = 0L;
        this.enableSyntacticNullAnalysisForFields = false;
        this.inheritNullAnnotations = false;
        this.analyseResourceLeaks = true;
        this.reportMissingEnumCaseDespiteDefault = false;
        this.complainOnUninternedIdentityComparison = false;
    }

    public void set(Map<String, String> map) {
        String str;
        String str2 = map.get(OPTION_LocalVariableAttribute);
        if (str2 != null) {
            if (GENERATE.equals(str2)) {
                this.produceDebugAttributes |= 4;
            } else if (DO_NOT_GENERATE.equals(str2)) {
                this.produceDebugAttributes &= -5;
            }
        }
        String str3 = map.get(OPTION_LineNumberAttribute);
        if (str3 != null) {
            if (GENERATE.equals(str3)) {
                this.produceDebugAttributes |= 2;
            } else if (DO_NOT_GENERATE.equals(str3)) {
                this.produceDebugAttributes &= -3;
            }
        }
        String str4 = map.get(OPTION_SourceFileAttribute);
        if (str4 != null) {
            if (GENERATE.equals(str4)) {
                this.produceDebugAttributes |= 1;
            } else if (DO_NOT_GENERATE.equals(str4)) {
                this.produceDebugAttributes &= -2;
            }
        }
        String str5 = map.get(OPTION_PreserveUnusedLocal);
        if (str5 != null) {
            if ("preserve".equals(str5)) {
                this.preserveAllLocalVariables = true;
            } else if (OPTIMIZE_OUT.equals(str5)) {
                this.preserveAllLocalVariables = false;
            }
        }
        String str6 = map.get(OPTION_ReportDeprecationInDeprecatedCode);
        if (str6 != null) {
            if (ENABLED.equals(str6)) {
                this.reportDeprecationInsideDeprecatedCode = true;
            } else if (DISABLED.equals(str6)) {
                this.reportDeprecationInsideDeprecatedCode = false;
            }
        }
        String str7 = map.get(OPTION_ReportDeprecationWhenOverridingDeprecatedMethod);
        if (str7 != null) {
            if (ENABLED.equals(str7)) {
                this.reportDeprecationWhenOverridingDeprecatedMethod = true;
            } else if (DISABLED.equals(str7)) {
                this.reportDeprecationWhenOverridingDeprecatedMethod = false;
            }
        }
        String str8 = map.get(OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding);
        if (str8 != null) {
            if (ENABLED.equals(str8)) {
                this.reportUnusedDeclaredThrownExceptionWhenOverriding = true;
            } else if (DISABLED.equals(str8)) {
                this.reportUnusedDeclaredThrownExceptionWhenOverriding = false;
            }
        }
        String str9 = map.get(OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference);
        if (str9 != null) {
            if (ENABLED.equals(str9)) {
                this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = true;
            } else if (DISABLED.equals(str9)) {
                this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = false;
            }
        }
        String str10 = map.get(OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable);
        if (str10 != null) {
            if (ENABLED.equals(str10)) {
                this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = true;
            } else if (DISABLED.equals(str10)) {
                this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = false;
            }
        }
        String str11 = map.get(OPTION_Compliance);
        if (str11 != null) {
            long jVersionToJdkLevel = versionToJdkLevel(str11);
            if (jVersionToJdkLevel != 0) {
                this.originalComplianceLevel = jVersionToJdkLevel;
                this.complianceLevel = jVersionToJdkLevel;
            }
        }
        String str12 = map.get(OPTION_Source);
        if (str12 != null) {
            long jVersionToJdkLevel2 = versionToJdkLevel(str12);
            if (jVersionToJdkLevel2 != 0) {
                this.originalSourceLevel = jVersionToJdkLevel2;
                this.sourceLevel = jVersionToJdkLevel2;
            }
        }
        String str13 = map.get(OPTION_TargetPlatform);
        if (str13 != null) {
            long jVersionToJdkLevel3 = versionToJdkLevel(str13);
            if (jVersionToJdkLevel3 != 0) {
                this.targetJDK = jVersionToJdkLevel3;
            }
            if (this.targetJDK >= ClassFileConstants.JDK1_5) {
                this.inlineJsrBytecode = true;
            }
        }
        String str14 = map.get(OPTION_Encoding);
        if (str14 != null) {
            this.defaultEncoding = null;
            if (str14.length() > 0) {
                try {
                    new InputStreamReader(new ByteArrayInputStream(new byte[0]), str14);
                    this.defaultEncoding = str14;
                } catch (UnsupportedEncodingException unused) {
                }
            }
        }
        String str15 = map.get(OPTION_ReportUnusedParameterWhenImplementingAbstract);
        if (str15 != null) {
            if (ENABLED.equals(str15)) {
                this.reportUnusedParameterWhenImplementingAbstract = true;
            } else if (DISABLED.equals(str15)) {
                this.reportUnusedParameterWhenImplementingAbstract = false;
            }
        }
        String str16 = map.get(OPTION_ReportUnusedParameterWhenOverridingConcrete);
        if (str16 != null) {
            if (ENABLED.equals(str16)) {
                this.reportUnusedParameterWhenOverridingConcrete = true;
            } else if (DISABLED.equals(str16)) {
                this.reportUnusedParameterWhenOverridingConcrete = false;
            }
        }
        String str17 = map.get(OPTION_ReportUnusedParameterIncludeDocCommentReference);
        if (str17 != null) {
            if (ENABLED.equals(str17)) {
                this.reportUnusedParameterIncludeDocCommentReference = true;
            } else if (DISABLED.equals(str17)) {
                this.reportUnusedParameterIncludeDocCommentReference = false;
            }
        }
        String str18 = map.get(OPTION_ReportSpecialParameterHidingField);
        if (str18 != null) {
            if (ENABLED.equals(str18)) {
                this.reportSpecialParameterHidingField = true;
            } else if (DISABLED.equals(str18)) {
                this.reportSpecialParameterHidingField = false;
            }
        }
        String str19 = map.get(OPTION_ReportUnavoidableGenericTypeProblems);
        if (str19 != null) {
            if (ENABLED.equals(str19)) {
                this.reportUnavoidableGenericTypeProblems = true;
            } else if (DISABLED.equals(str19)) {
                this.reportUnavoidableGenericTypeProblems = false;
            }
        }
        String str20 = map.get(OPTION_ReportDeadCodeInTrivialIfStatement);
        if (str20 != null) {
            if (ENABLED.equals(str20)) {
                this.reportDeadCodeInTrivialIfStatement = true;
            } else if (DISABLED.equals(str20)) {
                this.reportDeadCodeInTrivialIfStatement = false;
            }
        }
        String str21 = map.get(OPTION_MaxProblemPerUnit);
        if (str21 != null) {
            try {
                int i = Integer.parseInt(str21);
                if (i >= 0) {
                    this.maxProblemsPerUnit = i;
                }
            } catch (NumberFormatException unused2) {
            }
        }
        String str22 = map.get(OPTION_TaskTags);
        if (str22 != null) {
            if (str22.length() == 0) {
                this.taskTags = null;
            } else {
                this.taskTags = CharOperation.splitAndTrimOn(',', str22.toCharArray());
            }
        }
        String str23 = map.get(OPTION_TaskPriorities);
        if (str23 != null) {
            if (str23.length() == 0) {
                this.taskPriorities = null;
            } else {
                this.taskPriorities = CharOperation.splitAndTrimOn(',', str23.toCharArray());
            }
        }
        String str24 = map.get(OPTION_TaskCaseSensitive);
        if (str24 != null) {
            if (ENABLED.equals(str24)) {
                this.isTaskCaseSensitive = true;
            } else if (DISABLED.equals(str24)) {
                this.isTaskCaseSensitive = false;
            }
        }
        String str25 = map.get(OPTION_InlineJsr);
        if (str25 != null && this.targetJDK < ClassFileConstants.JDK1_5) {
            if (ENABLED.equals(str25)) {
                this.inlineJsrBytecode = true;
            } else if (DISABLED.equals(str25)) {
                this.inlineJsrBytecode = false;
            }
        }
        String str26 = map.get(OPTION_ShareCommonFinallyBlocks);
        if (str26 != null) {
            if (ENABLED.equals(str26)) {
                this.shareCommonFinallyBlocks = true;
            } else if (DISABLED.equals(str26)) {
                this.shareCommonFinallyBlocks = false;
            }
        }
        String str27 = map.get(OPTION_MethodParametersAttribute);
        if (str27 == null) {
            str = DO_NOT_GENERATE;
        } else if (GENERATE.equals(str27)) {
            this.produceMethodParameters = true;
            str = DO_NOT_GENERATE;
        } else {
            str = DO_NOT_GENERATE;
            if (str.equals(str27)) {
                this.produceMethodParameters = false;
            }
        }
        String str28 = map.get(OPTION_LambdaGenericSignature);
        if (str28 != null) {
            if (GENERATE.equals(str28)) {
                this.generateGenericSignatureForLambdaExpressions = true;
            } else if (str.equals(str28)) {
                this.generateGenericSignatureForLambdaExpressions = false;
            }
        }
        String str29 = map.get(OPTION_SuppressWarnings);
        if (str29 != null) {
            if (ENABLED.equals(str29)) {
                this.suppressWarnings = true;
            } else if (DISABLED.equals(str29)) {
                this.suppressWarnings = false;
            }
        }
        String str30 = map.get(OPTION_SuppressOptionalErrors);
        if (str30 != null) {
            if (ENABLED.equals(str30)) {
                this.suppressOptionalErrors = true;
            } else if (DISABLED.equals(str30)) {
                this.suppressOptionalErrors = false;
            }
        }
        String str31 = map.get(OPTION_FatalOptionalError);
        if (str31 != null) {
            if (ENABLED.equals(str31)) {
                this.treatOptionalErrorAsFatal = true;
            } else if (DISABLED.equals(str31)) {
                this.treatOptionalErrorAsFatal = false;
            }
        }
        String str32 = map.get(OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation);
        if (str32 != null) {
            if (ENABLED.equals(str32)) {
                this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = true;
            } else if (DISABLED.equals(str32)) {
                this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = false;
            }
        }
        String str33 = map.get(OPTION_IncludeNullInfoFromAsserts);
        if (str33 != null) {
            if (ENABLED.equals(str33)) {
                this.includeNullInfoFromAsserts = true;
            } else if (DISABLED.equals(str33)) {
                this.includeNullInfoFromAsserts = false;
            }
        }
        String str34 = map.get(OPTION_ReportMethodWithConstructorName);
        if (str34 != null) {
            updateSeverity(1, str34);
        }
        String str35 = map.get(OPTION_ReportOverridingPackageDefaultMethod);
        if (str35 != null) {
            updateSeverity(2, str35);
        }
        String str36 = map.get(OPTION_ReportDeprecation);
        if (str36 != null) {
            updateSeverity(4, str36);
        }
        String str37 = map.get(OPTION_ReportHiddenCatchBlock);
        if (str37 != null) {
            updateSeverity(8, str37);
        }
        String str38 = map.get(OPTION_ReportUnusedLocal);
        if (str38 != null) {
            updateSeverity(16, str38);
        }
        String str39 = map.get(OPTION_ReportUnusedParameter);
        if (str39 != null) {
            updateSeverity(32, str39);
        }
        String str40 = map.get(OPTION_ReportUnusedExceptionParameter);
        if (str40 != null) {
            updateSeverity(UnusedExceptionParameter, str40);
        }
        String str41 = map.get(OPTION_ReportUnusedImport);
        if (str41 != null) {
            updateSeverity(1024, str41);
        }
        String str42 = map.get(OPTION_ReportUnusedPrivateMember);
        if (str42 != null) {
            updateSeverity(32768, str42);
        }
        String str43 = map.get(OPTION_ReportUnusedDeclaredThrownException);
        if (str43 != null) {
            updateSeverity(8388608, str43);
        }
        String str44 = map.get(OPTION_ReportNoImplicitStringConversion);
        if (str44 != null) {
            updateSeverity(64, str44);
        }
        String str45 = map.get(OPTION_ReportSyntheticAccessEmulation);
        if (str45 != null) {
            updateSeverity(128, str45);
        }
        String str46 = map.get(OPTION_ReportLocalVariableHiding);
        if (str46 != null) {
            updateSeverity(65536, str46);
        }
        String str47 = map.get(OPTION_ReportFieldHiding);
        if (str47 != null) {
            updateSeverity(131072, str47);
        }
        String str48 = map.get(OPTION_ReportTypeParameterHiding);
        if (str48 != null) {
            updateSeverity(TypeHiding, str48);
        }
        String str49 = map.get(OPTION_ReportPossibleAccidentalBooleanAssignment);
        if (str49 != null) {
            updateSeverity(262144, str49);
        }
        String str50 = map.get(OPTION_ReportEmptyStatement);
        if (str50 != null) {
            updateSeverity(524288, str50);
        }
        String str51 = map.get(OPTION_ReportNonExternalizedStringLiteral);
        if (str51 != null) {
            updateSeverity(256, str51);
        }
        String str52 = map.get(OPTION_ReportAssertIdentifier);
        if (str52 != null) {
            updateSeverity(512, str52);
        }
        String str53 = map.get(OPTION_ReportEnumIdentifier);
        if (str53 != null) {
            updateSeverity(EnumUsedAsAnIdentifier, str53);
        }
        String str54 = map.get(OPTION_ReportNonStaticAccessToStatic);
        if (str54 != null) {
            updateSeverity(2048, str54);
        }
        String str55 = map.get(OPTION_ReportIndirectStaticAccess);
        if (str55 != null) {
            updateSeverity(268435456, str55);
        }
        String str56 = map.get(OPTION_ReportIncompatibleNonInheritedInterfaceMethod);
        if (str56 != null) {
            updateSeverity(16384, str56);
        }
        String str57 = map.get(OPTION_ReportUndocumentedEmptyBlock);
        if (str57 != null) {
            updateSeverity(134217728, str57);
        }
        String str58 = map.get(OPTION_ReportUnnecessaryTypeCheck);
        if (str58 != null) {
            updateSeverity(67108864, str58);
        }
        String str59 = map.get(OPTION_ReportUnnecessaryElse);
        if (str59 != null) {
            updateSeverity(UnnecessaryElse, str59);
        }
        String str60 = map.get(OPTION_ReportFinallyBlockNotCompletingNormally);
        if (str60 != null) {
            updateSeverity(16777216, str60);
        }
        String str61 = map.get(OPTION_ReportUnqualifiedFieldAccess);
        if (str61 != null) {
            updateSeverity(4194304, str61);
        }
        String str62 = map.get(OPTION_ReportNoEffectAssignment);
        if (str62 != null) {
            updateSeverity(8192, str62);
        }
        String str63 = map.get(OPTION_ReportUncheckedTypeOperation);
        if (str63 != null) {
            updateSeverity(UncheckedTypeOperation, str63);
        }
        String str64 = map.get(OPTION_ReportRawTypeReference);
        if (str64 != null) {
            updateSeverity(RawTypeReference, str64);
        }
        String str65 = map.get(OPTION_ReportFinalParameterBound);
        if (str65 != null) {
            updateSeverity(FinalParameterBound, str65);
        }
        String str66 = map.get(OPTION_ReportMissingSerialVersion);
        if (str66 != null) {
            updateSeverity(MissingSerialVersion, str66);
        }
        String str67 = map.get(OPTION_ReportForbiddenReference);
        if (str67 != null) {
            updateSeverity(536870944, str67);
        }
        String str68 = map.get(OPTION_ReportDiscouragedReference);
        if (str68 != null) {
            updateSeverity(DiscouragedReference, str68);
        }
        String str69 = map.get(OPTION_ReportVarargsArgumentNeedCast);
        if (str69 != null) {
            updateSeverity(536870976, str69);
        }
        String str70 = map.get(OPTION_ReportNullReference);
        if (str70 != null) {
            updateSeverity(NullReference, str70);
        }
        String str71 = map.get(OPTION_ReportPotentialNullReference);
        if (str71 != null) {
            updateSeverity(PotentialNullReference, str71);
        }
        String str72 = map.get(OPTION_ReportRedundantNullCheck);
        if (str72 != null) {
            updateSeverity(RedundantNullCheck, str72);
        }
        String str73 = map.get(OPTION_ReportAutoboxing);
        if (str73 != null) {
            updateSeverity(AutoBoxing, str73);
        }
        String str74 = map.get(OPTION_ReportAnnotationSuperInterface);
        if (str74 != null) {
            updateSeverity(AnnotationSuperInterface, str74);
        }
        String str75 = map.get(OPTION_ReportMissingOverrideAnnotation);
        if (str75 != null) {
            updateSeverity(MissingOverrideAnnotation, str75);
        }
        String str76 = map.get(OPTION_ReportMissingDeprecatedAnnotation);
        if (str76 != null) {
            updateSeverity(MissingDeprecatedAnnotation, str76);
        }
        String str77 = map.get(OPTION_ReportIncompleteEnumSwitch);
        if (str77 != null) {
            updateSeverity(MissingEnumConstantCase, str77);
        }
        String str78 = map.get(OPTION_ReportMissingEnumCaseDespiteDefault);
        if (str78 != null) {
            if (ENABLED.equals(str78)) {
                this.reportMissingEnumCaseDespiteDefault = true;
            } else if (DISABLED.equals(str78)) {
                this.reportMissingEnumCaseDespiteDefault = false;
            }
        }
        String str79 = map.get(OPTION_ReportMissingDefaultCase);
        if (str79 != null) {
            updateSeverity(MissingDefaultCase, str79);
        }
        String str80 = map.get(OPTION_ReportUnhandledWarningToken);
        if (str80 != null) {
            updateSeverity(UnhandledWarningToken, str80);
        }
        String str81 = map.get(OPTION_ReportUnusedWarningToken);
        if (str81 != null) {
            updateSeverity(UnusedWarningToken, str81);
        }
        String str82 = map.get(OPTION_ReportUnusedLabel);
        if (str82 != null) {
            updateSeverity(UnusedLabel, str82);
        }
        String str83 = map.get(OPTION_ReportParameterAssignment);
        if (str83 != null) {
            updateSeverity(ParameterAssignment, str83);
        }
        String str84 = map.get(OPTION_ReportFallthroughCase);
        if (str84 != null) {
            updateSeverity(FallthroughCase, str84);
        }
        String str85 = map.get(OPTION_ReportOverridingMethodWithoutSuperInvocation);
        if (str85 != null) {
            updateSeverity(OverridingMethodWithoutSuperInvocation, str85);
        }
        String str86 = map.get(OPTION_ReportUnusedTypeArgumentsForMethodInvocation);
        if (str86 != null) {
            updateSeverity(UnusedTypeArguments, str86);
        }
        String str87 = map.get(OPTION_ReportRedundantSuperinterface);
        if (str87 != null) {
            updateSeverity(RedundantSuperinterface, str87);
        }
        String str88 = map.get(OPTION_ReportComparingIdentical);
        if (str88 != null) {
            updateSeverity(ComparingIdentical, str88);
        }
        String str89 = map.get(OPTION_ReportMissingSynchronizedOnInheritedMethod);
        if (str89 != null) {
            updateSeverity(805306368, str89);
        }
        String str90 = map.get(OPTION_ReportMissingHashCodeMethod);
        if (str90 != null) {
            updateSeverity(ShouldImplementHashcode, str90);
        }
        String str91 = map.get(OPTION_ReportDeadCode);
        if (str91 != null) {
            updateSeverity(DeadCode, str91);
        }
        String str92 = map.get(OPTION_ReportTasks);
        if (str92 != null) {
            updateSeverity(Tasks, str92);
        }
        String str93 = map.get(OPTION_ReportUnusedObjectAllocation);
        if (str93 != null) {
            updateSeverity(UnusedObjectAllocation, str93);
        }
        String str94 = map.get(OPTION_ReportMethodCanBeStatic);
        if (str94 != null) {
            updateSeverity(MethodCanBeStatic, str94);
        }
        String str95 = map.get(OPTION_ReportMethodCanBePotentiallyStatic);
        if (str95 != null) {
            updateSeverity(MethodCanBePotentiallyStatic, str95);
        }
        String str96 = map.get(OPTION_ReportRedundantSpecificationOfTypeArguments);
        if (str96 != null) {
            updateSeverity(RedundantSpecificationOfTypeArguments, str96);
        }
        String str97 = map.get(OPTION_ReportUnclosedCloseable);
        if (str97 != null) {
            updateSeverity(UnclosedCloseable, str97);
        }
        String str98 = map.get(OPTION_ReportPotentiallyUnclosedCloseable);
        if (str98 != null) {
            updateSeverity(PotentiallyUnclosedCloseable, str98);
        }
        String str99 = map.get(OPTION_ReportExplicitlyClosedAutoCloseable);
        if (str99 != null) {
            updateSeverity(ExplicitlyClosedAutoCloseable, str99);
        }
        String str100 = map.get(OPTION_ReportUnusedTypeParameter);
        if (str100 != null) {
            updateSeverity(UnusedTypeParameter, str100);
        }
        if (getSeverity(UnclosedCloseable) == 256 && getSeverity(PotentiallyUnclosedCloseable) == 256 && getSeverity(ExplicitlyClosedAutoCloseable) == 256) {
            this.analyseResourceLeaks = false;
        } else {
            this.analyseResourceLeaks = true;
        }
        String str101 = map.get(OPTION_AnnotationBasedNullAnalysis);
        if (str101 != null) {
            this.isAnnotationBasedNullAnalysisEnabled = ENABLED.equals(str101);
        }
        if (this.isAnnotationBasedNullAnalysisEnabled) {
            this.storeAnnotations = true;
            String str102 = map.get(OPTION_ReportNullSpecViolation);
            if (str102 != null) {
                if ("error".equals(str102)) {
                    this.errorThreshold.set(NullSpecViolation);
                    this.warningThreshold.clear(NullSpecViolation);
                } else if (WARNING.equals(str102)) {
                    this.errorThreshold.clear(NullSpecViolation);
                    this.warningThreshold.set(NullSpecViolation);
                }
            }
            String str103 = map.get(OPTION_ReportNullAnnotationInferenceConflict);
            if (str103 != null) {
                updateSeverity(NullAnnotationInferenceConflict, str103);
            }
            String str104 = map.get(OPTION_ReportNullUncheckedConversion);
            if (str104 != null) {
                updateSeverity(NullUncheckedConversion, str104);
            }
            String str105 = map.get(OPTION_ReportRedundantNullAnnotation);
            if (str105 != null) {
                updateSeverity(RedundantNullAnnotation, str105);
            }
            String str106 = map.get(OPTION_NullableAnnotationName);
            if (str106 != null) {
                this.nullableAnnotationName = CharOperation.splitAndTrimOn('.', str106.toCharArray());
            }
            String str107 = map.get(OPTION_NonNullAnnotationName);
            if (str107 != null) {
                this.nonNullAnnotationName = CharOperation.splitAndTrimOn('.', str107.toCharArray());
            }
            String str108 = map.get(OPTION_NonNullByDefaultAnnotationName);
            if (str108 != null) {
                this.nonNullByDefaultAnnotationName = CharOperation.splitAndTrimOn('.', str108.toCharArray());
            }
            String str109 = map.get(OPTION_NullableAnnotationSecondaryNames);
            if (str109 != null) {
                this.nullableAnnotationSecondaryNames = stringToNameList(str109);
            }
            String str110 = map.get(OPTION_NonNullAnnotationSecondaryNames);
            if (str110 != null) {
                this.nonNullAnnotationSecondaryNames = stringToNameList(str110);
            }
            String str111 = map.get(OPTION_NonNullByDefaultAnnotationSecondaryNames);
            if (str111 != null) {
                this.nonNullByDefaultAnnotationSecondaryNames = stringToNameList(str111);
            }
            String str112 = map.get(OPTION_ReportMissingNonNullByDefaultAnnotation);
            if (str112 != null) {
                updateSeverity(MissingNonNullByDefaultAnnotation, str112);
            }
            String str113 = map.get(OPTION_SyntacticNullAnalysisForFields);
            if (str113 != null) {
                this.enableSyntacticNullAnalysisForFields = ENABLED.equals(str113);
            }
            String str114 = map.get(OPTION_InheritNullAnnotations);
            if (str114 != null) {
                this.inheritNullAnnotations = ENABLED.equals(str114);
            }
            String str115 = map.get(OPTION_ReportNonnullParameterAnnotationDropped);
            if (str115 != null) {
                updateSeverity(NonnullParameterAnnotationDropped, str115);
            }
            String str116 = map.get(OPTION_PessimisticNullAnalysisForFreeTypeVariables);
            if (str116 != null) {
                updateSeverity(PessimisticNullAnalysisForFreeTypeVariables, str116);
            }
            if (getSeverity(PessimisticNullAnalysisForFreeTypeVariables) == 256) {
                this.pessimisticNullAnalysisForFreeTypeVariablesEnabled = false;
            } else {
                this.pessimisticNullAnalysisForFreeTypeVariablesEnabled = true;
            }
            String str117 = map.get(OPTION_ReportNonNullTypeVariableFromLegacyInvocation);
            if (str117 != null) {
                updateSeverity(NonNullTypeVariableFromLegacyInvocation, str117);
            }
        }
        String str118 = map.get(OPTION_DocCommentSupport);
        if (str118 != null) {
            if (ENABLED.equals(str118)) {
                this.docCommentSupport = true;
            } else if (DISABLED.equals(str118)) {
                this.docCommentSupport = false;
            }
        }
        String str119 = map.get(OPTION_ReportInvalidJavadoc);
        if (str119 != null) {
            updateSeverity(33554432, str119);
        }
        String str120 = map.get(OPTION_ReportInvalidJavadocTagsVisibility);
        if (str120 != null) {
            if ("public".equals(str120)) {
                this.reportInvalidJavadocTagsVisibility = 1;
            } else if ("protected".equals(str120)) {
                this.reportInvalidJavadocTagsVisibility = 4;
            } else if ("default".equals(str120)) {
                this.reportInvalidJavadocTagsVisibility = 0;
            } else if ("private".equals(str120)) {
                this.reportInvalidJavadocTagsVisibility = 2;
            }
        }
        String str121 = map.get(OPTION_ReportInvalidJavadocTags);
        if (str121 != null) {
            if (ENABLED.equals(str121)) {
                this.reportInvalidJavadocTags = true;
            } else if (DISABLED.equals(str121)) {
                this.reportInvalidJavadocTags = false;
            }
        }
        String str122 = map.get(OPTION_ReportInvalidJavadocTagsDeprecatedRef);
        if (str122 != null) {
            if (ENABLED.equals(str122)) {
                this.reportInvalidJavadocTagsDeprecatedRef = true;
            } else if (DISABLED.equals(str122)) {
                this.reportInvalidJavadocTagsDeprecatedRef = false;
            }
        }
        String str123 = map.get(OPTION_ReportInvalidJavadocTagsNotVisibleRef);
        if (str123 != null) {
            if (ENABLED.equals(str123)) {
                this.reportInvalidJavadocTagsNotVisibleRef = true;
            } else if (DISABLED.equals(str123)) {
                this.reportInvalidJavadocTagsNotVisibleRef = false;
            }
        }
        String str124 = map.get(OPTION_ReportMissingJavadocTags);
        if (str124 != null) {
            updateSeverity(2097152, str124);
        }
        String str125 = map.get(OPTION_ReportMissingJavadocTagsVisibility);
        if (str125 != null) {
            if ("public".equals(str125)) {
                this.reportMissingJavadocTagsVisibility = 1;
            } else if ("protected".equals(str125)) {
                this.reportMissingJavadocTagsVisibility = 4;
            } else if ("default".equals(str125)) {
                this.reportMissingJavadocTagsVisibility = 0;
            } else if ("private".equals(str125)) {
                this.reportMissingJavadocTagsVisibility = 2;
            }
        }
        String str126 = map.get(OPTION_ReportMissingJavadocTagsOverriding);
        if (str126 != null) {
            if (ENABLED.equals(str126)) {
                this.reportMissingJavadocTagsOverriding = true;
            } else if (DISABLED.equals(str126)) {
                this.reportMissingJavadocTagsOverriding = false;
            }
        }
        String str127 = map.get(OPTION_ReportMissingJavadocTagsMethodTypeParameters);
        if (str127 != null) {
            if (ENABLED.equals(str127)) {
                this.reportMissingJavadocTagsMethodTypeParameters = true;
            } else if (DISABLED.equals(str127)) {
                this.reportMissingJavadocTagsMethodTypeParameters = false;
            }
        }
        String str128 = map.get(OPTION_ReportMissingJavadocComments);
        if (str128 != null) {
            updateSeverity(1048576, str128);
        }
        String str129 = map.get(OPTION_ReportMissingJavadocTagDescription);
        if (str129 != null) {
            this.reportMissingJavadocTagDescription = str129;
        }
        String str130 = map.get(OPTION_ReportMissingJavadocCommentsVisibility);
        if (str130 != null) {
            if ("public".equals(str130)) {
                this.reportMissingJavadocCommentsVisibility = 1;
            } else if ("protected".equals(str130)) {
                this.reportMissingJavadocCommentsVisibility = 4;
            } else if ("default".equals(str130)) {
                this.reportMissingJavadocCommentsVisibility = 0;
            } else if ("private".equals(str130)) {
                this.reportMissingJavadocCommentsVisibility = 2;
            }
        }
        String str131 = map.get(OPTION_ReportMissingJavadocCommentsOverriding);
        if (str131 != null) {
            if (ENABLED.equals(str131)) {
                this.reportMissingJavadocCommentsOverriding = true;
            } else if (DISABLED.equals(str131)) {
                this.reportMissingJavadocCommentsOverriding = false;
            }
        }
        String str132 = map.get(OPTION_GenerateClassFiles);
        if (str132 != null) {
            if (ENABLED.equals(str132)) {
                this.generateClassFiles = true;
            } else if (DISABLED.equals(str132)) {
                this.generateClassFiles = false;
            }
        }
        String str133 = map.get(OPTION_Process_Annotations);
        if (str133 != null) {
            if (ENABLED.equals(str133)) {
                this.processAnnotations = true;
                this.storeAnnotations = true;
            } else if (DISABLED.equals(str133)) {
                this.processAnnotations = false;
                if (!this.isAnnotationBasedNullAnalysisEnabled) {
                    this.storeAnnotations = false;
                }
            }
        }
        String str134 = map.get(OPTION_Store_Annotations);
        if (str134 != null) {
            if (ENABLED.equals(str134)) {
                this.storeAnnotations = true;
            } else if (DISABLED.equals(str134) && !this.isAnnotationBasedNullAnalysisEnabled && !this.processAnnotations) {
                this.storeAnnotations = false;
            }
        }
        String str135 = map.get(OPTION_EmulateJavacBug8031744);
        if (str135 != null) {
            if (ENABLED.equals(str135)) {
                this.emulateJavacBug8031744 = true;
            } else if (DISABLED.equals(str135)) {
                this.emulateJavacBug8031744 = false;
            }
        }
        String str136 = map.get(OPTION_ReportUninternedIdentityComparison);
        if (str136 != null) {
            if (ENABLED.equals(str136)) {
                this.complainOnUninternedIdentityComparison = true;
            } else if (DISABLED.equals(str136)) {
                this.complainOnUninternedIdentityComparison = false;
            }
        }
    }

    private String[] stringToNameList(String str) {
        String[] strArrSplit = str.split(",");
        if (strArrSplit == null) {
            return NO_STRINGS;
        }
        for (int i = 0; i < strArrSplit.length; i++) {
            strArrSplit[i] = strArrSplit[i].trim();
        }
        return strArrSplit;
    }

    String nameListToString(String[] strArr) {
        if (strArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(strArr[i]);
        }
        return sb.toString();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("CompilerOptions:\n\t- local variables debug attributes: ");
        stringBuffer.append((this.produceDebugAttributes & 4) != 0 ? "ON" : " OFF");
        stringBuffer.append("\n\t- line number debug attributes: ").append((this.produceDebugAttributes & 2) != 0 ? "ON" : " OFF");
        stringBuffer.append("\n\t- source debug attributes: ").append((this.produceDebugAttributes & 1) != 0 ? "ON" : " OFF");
        StringBuffer stringBufferAppend = stringBuffer.append("\n\t- MethodParameters attributes: ");
        boolean z = this.produceMethodParameters;
        String str = GENERATE;
        stringBufferAppend.append(z ? GENERATE : DO_NOT_GENERATE);
        StringBuffer stringBufferAppend2 = stringBuffer.append("\n\t- Generic signature for lambda expressions: ");
        if (!this.generateGenericSignatureForLambdaExpressions) {
            str = DO_NOT_GENERATE;
        }
        stringBufferAppend2.append(str);
        stringBuffer.append("\n\t- preserve all local variables: ").append(this.preserveAllLocalVariables ? "ON" : " OFF");
        stringBuffer.append("\n\t- method with constructor name: ").append(getSeverityString(1));
        stringBuffer.append("\n\t- overridden package default method: ").append(getSeverityString(2));
        stringBuffer.append("\n\t- deprecation: ").append(getSeverityString(4));
        stringBuffer.append("\n\t- masked catch block: ").append(getSeverityString(8));
        stringBuffer.append("\n\t- unused local variable: ").append(getSeverityString(16));
        stringBuffer.append("\n\t- unused parameter: ").append(getSeverityString(32));
        stringBuffer.append("\n\t- unused exception parameter: ").append(getSeverityString(UnusedExceptionParameter));
        stringBuffer.append("\n\t- unused import: ").append(getSeverityString(1024));
        stringBuffer.append("\n\t- synthetic access emulation: ").append(getSeverityString(128));
        stringBuffer.append("\n\t- assignment with no effect: ").append(getSeverityString(8192));
        stringBuffer.append("\n\t- non externalized string: ").append(getSeverityString(256));
        stringBuffer.append("\n\t- static access receiver: ").append(getSeverityString(2048));
        stringBuffer.append("\n\t- indirect static access: ").append(getSeverityString(268435456));
        stringBuffer.append("\n\t- incompatible non inherited interface method: ").append(getSeverityString(16384));
        stringBuffer.append("\n\t- unused private member: ").append(getSeverityString(32768));
        stringBuffer.append("\n\t- local variable hiding another variable: ").append(getSeverityString(65536));
        stringBuffer.append("\n\t- field hiding another variable: ").append(getSeverityString(131072));
        stringBuffer.append("\n\t- type hiding another type: ").append(getSeverityString(TypeHiding));
        stringBuffer.append("\n\t- possible accidental boolean assignment: ").append(getSeverityString(262144));
        stringBuffer.append("\n\t- superfluous semicolon: ").append(getSeverityString(524288));
        stringBuffer.append("\n\t- uncommented empty block: ").append(getSeverityString(134217728));
        stringBuffer.append("\n\t- unnecessary type check: ").append(getSeverityString(67108864));
        stringBuffer.append("\n\t- javadoc comment support: ").append(this.docCommentSupport ? "ON" : " OFF");
        stringBuffer.append("\n\t\t+ invalid javadoc: ").append(getSeverityString(33554432));
        StringBuffer stringBufferAppend3 = stringBuffer.append("\n\t\t+ report invalid javadoc tags: ");
        boolean z2 = this.reportInvalidJavadocTags;
        String str2 = ENABLED;
        stringBufferAppend3.append(z2 ? ENABLED : DISABLED);
        stringBuffer.append("\n\t\t\t* deprecated references: ").append(this.reportInvalidJavadocTagsDeprecatedRef ? ENABLED : DISABLED);
        stringBuffer.append("\n\t\t\t* not visible references: ").append(this.reportInvalidJavadocTagsNotVisibleRef ? ENABLED : DISABLED);
        stringBuffer.append("\n\t\t+ visibility level to report invalid javadoc tags: ").append(getVisibilityString(this.reportInvalidJavadocTagsVisibility));
        stringBuffer.append("\n\t\t+ missing javadoc tags: ").append(getSeverityString(2097152));
        stringBuffer.append("\n\t\t+ visibility level to report missing javadoc tags: ").append(getVisibilityString(this.reportMissingJavadocTagsVisibility));
        stringBuffer.append("\n\t\t+ report missing javadoc tags for method type parameters: ").append(this.reportMissingJavadocTagsMethodTypeParameters ? ENABLED : DISABLED);
        stringBuffer.append("\n\t\t+ report missing javadoc tags in overriding methods: ").append(this.reportMissingJavadocTagsOverriding ? ENABLED : DISABLED);
        stringBuffer.append("\n\t\t+ missing javadoc comments: ").append(getSeverityString(1048576));
        stringBuffer.append("\n\t\t+ report missing tag description option: ").append(this.reportMissingJavadocTagDescription);
        stringBuffer.append("\n\t\t+ visibility level to report missing javadoc comments: ").append(getVisibilityString(this.reportMissingJavadocCommentsVisibility));
        stringBuffer.append("\n\t\t+ report missing javadoc comments in overriding methods: ").append(this.reportMissingJavadocCommentsOverriding ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- finally block not completing normally: ").append(getSeverityString(16777216));
        stringBuffer.append("\n\t- report unused declared thrown exception: ").append(getSeverityString(8388608));
        stringBuffer.append("\n\t- report unused declared thrown exception when overriding: ").append(this.reportUnusedDeclaredThrownExceptionWhenOverriding ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unused declared thrown exception include doc comment reference: ").append(this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unused declared thrown exception exempt exception and throwable: ").append(this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- unnecessary else: ").append(getSeverityString(UnnecessaryElse));
        stringBuffer.append("\n\t- JDK compliance level: " + versionFromJdkLevel(this.complianceLevel));
        stringBuffer.append("\n\t- JDK source level: " + versionFromJdkLevel(this.sourceLevel));
        stringBuffer.append("\n\t- JDK target level: " + versionFromJdkLevel(this.targetJDK));
        stringBuffer.append("\n\t- verbose : ").append(this.verbose ? "ON" : "OFF");
        stringBuffer.append("\n\t- produce reference info : ").append(this.produceReferenceInfo ? "ON" : "OFF");
        stringBuffer.append("\n\t- parse literal expressions as constants : ").append(this.parseLiteralExpressionsAsConstants ? "ON" : "OFF");
        StringBuffer stringBufferAppend4 = stringBuffer.append("\n\t- encoding : ");
        String str3 = this.defaultEncoding;
        if (str3 == null) {
            str3 = "<default>";
        }
        stringBufferAppend4.append(str3);
        stringBuffer.append("\n\t- task tags: ").append(this.taskTags == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskTags, ',')));
        stringBuffer.append("\n\t- task priorities : ").append(this.taskPriorities == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskPriorities, ',')));
        stringBuffer.append("\n\t- report deprecation inside deprecated code : ").append(this.reportDeprecationInsideDeprecatedCode ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report deprecation when overriding deprecated method : ").append(this.reportDeprecationWhenOverridingDeprecatedMethod ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unused parameter when implementing abstract method : ").append(this.reportUnusedParameterWhenImplementingAbstract ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unused parameter when overriding concrete method : ").append(this.reportUnusedParameterWhenOverridingConcrete ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unused parameter include doc comment reference : ").append(this.reportUnusedParameterIncludeDocCommentReference ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report constructor/setter parameter hiding existing field : ").append(this.reportSpecialParameterHidingField ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- inline JSR bytecode : ").append(this.inlineJsrBytecode ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- share common finally blocks : ").append(this.shareCommonFinallyBlocks ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- report unavoidable generic type problems : ").append(this.reportUnavoidableGenericTypeProblems ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- unsafe type operation: ").append(getSeverityString(UncheckedTypeOperation));
        stringBuffer.append("\n\t- unsafe raw type: ").append(getSeverityString(RawTypeReference));
        stringBuffer.append("\n\t- final bound for type parameter: ").append(getSeverityString(FinalParameterBound));
        stringBuffer.append("\n\t- missing serialVersionUID: ").append(getSeverityString(MissingSerialVersion));
        stringBuffer.append("\n\t- varargs argument need cast: ").append(getSeverityString(536870976));
        stringBuffer.append("\n\t- forbidden reference to type with access restriction: ").append(getSeverityString(536870944));
        stringBuffer.append("\n\t- discouraged reference to type with access restriction: ").append(getSeverityString(DiscouragedReference));
        stringBuffer.append("\n\t- null reference: ").append(getSeverityString(NullReference));
        stringBuffer.append("\n\t- potential null reference: ").append(getSeverityString(PotentialNullReference));
        stringBuffer.append("\n\t- redundant null check: ").append(getSeverityString(RedundantNullCheck));
        stringBuffer.append("\n\t- autoboxing: ").append(getSeverityString(AutoBoxing));
        stringBuffer.append("\n\t- annotation super interface: ").append(getSeverityString(AnnotationSuperInterface));
        stringBuffer.append("\n\t- missing @Override annotation: ").append(getSeverityString(MissingOverrideAnnotation));
        stringBuffer.append("\n\t- missing @Override annotation for interface method implementation: ").append(this.reportMissingOverrideAnnotationForInterfaceMethodImplementation ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- missing @Deprecated annotation: ").append(getSeverityString(MissingDeprecatedAnnotation));
        stringBuffer.append("\n\t- incomplete enum switch: ").append(getSeverityString(MissingEnumConstantCase));
        stringBuffer.append("\n\t- raise null related warnings for variables tainted in assert statements: ").append(this.includeNullInfoFromAsserts ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- suppress warnings: ").append(this.suppressWarnings ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- suppress optional errors: ").append(this.suppressOptionalErrors ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- unhandled warning token: ").append(getSeverityString(UnhandledWarningToken));
        stringBuffer.append("\n\t- unused warning token: ").append(getSeverityString(UnusedWarningToken));
        stringBuffer.append("\n\t- unused label: ").append(getSeverityString(UnusedLabel));
        stringBuffer.append("\n\t- treat optional error as fatal: ").append(this.treatOptionalErrorAsFatal ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- parameter assignment: ").append(getSeverityString(ParameterAssignment));
        stringBuffer.append("\n\t- generate class files: ").append(this.generateClassFiles ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- process annotations: ").append(this.processAnnotations ? ENABLED : DISABLED);
        stringBuffer.append("\n\t- unused type arguments for method/constructor invocation: ").append(getSeverityString(UnusedTypeArguments));
        stringBuffer.append("\n\t- redundant superinterface: ").append(getSeverityString(RedundantSuperinterface));
        stringBuffer.append("\n\t- comparing identical expr: ").append(getSeverityString(ComparingIdentical));
        stringBuffer.append("\n\t- missing synchronized on inherited method: ").append(getSeverityString(805306368));
        stringBuffer.append("\n\t- should implement hashCode() method: ").append(getSeverityString(ShouldImplementHashcode));
        stringBuffer.append("\n\t- dead code: ").append(getSeverityString(DeadCode));
        StringBuffer stringBufferAppend5 = stringBuffer.append("\n\t- dead code in trivial if statement: ");
        if (!this.reportDeadCodeInTrivialIfStatement) {
            str2 = DISABLED;
        }
        stringBufferAppend5.append(str2);
        stringBuffer.append("\n\t- tasks severity: ").append(getSeverityString(Tasks));
        stringBuffer.append("\n\t- unused object allocation: ").append(getSeverityString(UnusedObjectAllocation));
        stringBuffer.append("\n\t- method can be static: ").append(getSeverityString(MethodCanBeStatic));
        stringBuffer.append("\n\t- method can be potentially static: ").append(getSeverityString(MethodCanBePotentiallyStatic));
        stringBuffer.append("\n\t- redundant specification of type arguments: ").append(getSeverityString(RedundantSpecificationOfTypeArguments));
        stringBuffer.append("\n\t- resource is not closed: ").append(getSeverityString(UnclosedCloseable));
        stringBuffer.append("\n\t- resource may not be closed: ").append(getSeverityString(PotentiallyUnclosedCloseable));
        stringBuffer.append("\n\t- resource should be handled by try-with-resources: ").append(getSeverityString(ExplicitlyClosedAutoCloseable));
        stringBuffer.append("\n\t- Unused Type Parameter: ").append(getSeverityString(UnusedTypeParameter));
        stringBuffer.append("\n\t- pessimistic null analysis for free type variables: ").append(getSeverityString(PessimisticNullAnalysisForFreeTypeVariables));
        stringBuffer.append("\n\t- report unsafe nonnull return from legacy method: ").append(getSeverityString(NonNullTypeVariableFromLegacyInvocation));
        return stringBuffer.toString();
    }

    protected void updateSeverity(int i, Object obj) {
        if ("error".equals(obj)) {
            this.errorThreshold.set(i);
            this.warningThreshold.clear(i);
            this.infoThreshold.clear(i);
            return;
        }
        if (WARNING.equals(obj)) {
            this.errorThreshold.clear(i);
            this.warningThreshold.set(i);
            this.infoThreshold.clear(i);
        } else if (INFO.equals(obj)) {
            this.errorThreshold.clear(i);
            this.warningThreshold.clear(i);
            this.infoThreshold.set(i);
        } else if ("ignore".equals(obj)) {
            this.errorThreshold.clear(i);
            this.warningThreshold.clear(i);
            this.infoThreshold.clear(i);
        }
    }

    public boolean usesNullTypeAnnotations() {
        Boolean bool = this.useNullTypeAnnotations;
        if (bool != null) {
            return bool.booleanValue();
        }
        return this.isAnnotationBasedNullAnalysisEnabled && this.sourceLevel >= ClassFileConstants.JDK1_8;
    }
}
