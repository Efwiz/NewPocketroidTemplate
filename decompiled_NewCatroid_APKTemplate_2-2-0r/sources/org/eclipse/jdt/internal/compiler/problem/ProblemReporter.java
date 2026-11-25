package org.eclipse.jdt.internal.compiler.problem;

import io.netty.util.internal.StringUtil;
import java.io.CharConversionException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.internal.ws.WebSocketProtocol;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayReference;
import org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Assignment;
import org.eclipse.jdt.internal.compiler.ast.BinaryExpression;
import org.eclipse.jdt.internal.compiler.ast.Block;
import org.eclipse.jdt.internal.compiler.ast.BranchStatement;
import org.eclipse.jdt.internal.compiler.ast.CaseStatement;
import org.eclipse.jdt.internal.compiler.ast.CastExpression;
import org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.CompoundAssignment;
import org.eclipse.jdt.internal.compiler.ast.ConditionalExpression;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.EqualExpression;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.ExpressionContext;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldReference;
import org.eclipse.jdt.internal.compiler.ast.FunctionalExpression;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression;
import org.eclipse.jdt.internal.compiler.ast.LabeledStatement;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.Literal;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NameReference;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.ast.NullLiteral;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedSuperReference;
import org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Receiver;
import org.eclipse.jdt.internal.compiler.ast.Reference;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.SingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.Statement;
import org.eclipse.jdt.internal.compiler.ast.SwitchStatement;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.UnaryExpression;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.CaptureBinding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;
import org.eclipse.jdt.internal.compiler.parser.JavadocTagConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.parser.RecoveryScanner;
import org.eclipse.jdt.internal.compiler.parser.Scanner;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class ProblemReporter extends ProblemHandler {
    private static final byte CONSTRUCTOR_ACCESS = 8;
    private static final byte FIELD_ACCESS = 4;
    private static final byte METHOD_ACCESS = 12;
    private Scanner positionScanner;
    public ReferenceContext referenceContext;
    private boolean underScoreIsLambdaParameter;

    private static int getElaborationId(int i, byte b) {
        return (i << 8) | b;
    }

    public static int getIrritant(int i) {
        if (i == 969 || i == 970) {
            return CompilerOptions.PessimisticNullAnalysisForFreeTypeVariables;
        }
        switch (i) {
            case IProblem.JavadocInvalidSeeUrlReference /* -1610612274 */:
            case IProblem.JavadocMissingTagDescription /* -1610612273 */:
            case IProblem.JavadocDuplicateTag /* -1610612272 */:
            case IProblem.JavadocHiddenReference /* -1610612271 */:
            case IProblem.JavadocInvalidMemberTypeQualification /* -1610612270 */:
            case IProblem.JavadocMissingIdentifier /* -1610612269 */:
            case IProblem.JavadocNonStaticTypeFromStaticInvocation /* -1610612268 */:
            case IProblem.JavadocInvalidParamTagTypeParameter /* -1610612267 */:
            case IProblem.JavadocUnexpectedTag /* -1610612266 */:
            case IProblem.JavadocMissingParamName /* -1610612264 */:
            case IProblem.JavadocDuplicateParamName /* -1610612263 */:
            case IProblem.JavadocInvalidParamName /* -1610612262 */:
            case IProblem.JavadocDuplicateReturnTag /* -1610612260 */:
            case IProblem.JavadocMissingThrowsClassName /* -1610612258 */:
            case IProblem.JavadocInvalidThrowsClass /* -1610612257 */:
            case IProblem.JavadocDuplicateThrowsClassName /* -1610612256 */:
            case IProblem.JavadocInvalidThrowsClassName /* -1610612255 */:
            case IProblem.JavadocMissingSeeReference /* -1610612254 */:
            case IProblem.JavadocInvalidSeeReference /* -1610612253 */:
            case IProblem.JavadocInvalidSeeHref /* -1610612252 */:
            case IProblem.JavadocInvalidSeeArgs /* -1610612251 */:
            case IProblem.JavadocInvalidTag /* -1610612249 */:
            case IProblem.JavadocUndefinedField /* -1610612248 */:
            case IProblem.JavadocNotVisibleField /* -1610612247 */:
            case IProblem.JavadocAmbiguousField /* -1610612246 */:
            case IProblem.JavadocUsingDeprecatedField /* -1610612245 */:
            case IProblem.JavadocUndefinedConstructor /* -1610612244 */:
            case IProblem.JavadocNotVisibleConstructor /* -1610612243 */:
            case IProblem.JavadocAmbiguousConstructor /* -1610612242 */:
            case IProblem.JavadocUsingDeprecatedConstructor /* -1610612241 */:
            case IProblem.JavadocUndefinedMethod /* -1610612240 */:
            case IProblem.JavadocNotVisibleMethod /* -1610612239 */:
            case IProblem.JavadocAmbiguousMethod /* -1610612238 */:
            case IProblem.JavadocUsingDeprecatedMethod /* -1610612237 */:
            case IProblem.JavadocNoMessageSendOnBaseType /* -1610612236 */:
            case IProblem.JavadocParameterMismatch /* -1610612235 */:
            case IProblem.JavadocNoMessageSendOnArrayType /* -1610612234 */:
            case IProblem.JavadocUndefinedType /* -1610612233 */:
            case IProblem.JavadocNotVisibleType /* -1610612232 */:
            case IProblem.JavadocAmbiguousType /* -1610612231 */:
            case IProblem.JavadocUsingDeprecatedType /* -1610612230 */:
            case IProblem.JavadocInternalTypeNameProvided /* -1610612229 */:
            case IProblem.JavadocInheritedMethodHidesEnclosingName /* -1610612228 */:
            case IProblem.JavadocInheritedFieldHidesEnclosingName /* -1610612227 */:
            case IProblem.JavadocInheritedNameHidesEnclosingTypeName /* -1610612226 */:
            case IProblem.JavadocAmbiguousMethodReference /* -1610612225 */:
            case IProblem.JavadocUnterminatedInlineTag /* -1610612224 */:
            case IProblem.JavadocMalformedSeeReference /* -1610612223 */:
                return 33554432;
            case IProblem.JavadocMissingParamTag /* -1610612265 */:
            case IProblem.JavadocMissingReturnTag /* -1610612261 */:
            case IProblem.JavadocMissingThrowsTag /* -1610612259 */:
                return 2097152;
            case IProblem.JavadocMissing /* -1610612250 */:
                return 1048576;
            case IProblem.UsingDeprecatedType /* 16777221 */:
            case IProblem.UsingDeprecatedField /* 33554505 */:
            case IProblem.UsingDeprecatedMethod /* 67108967 */:
            case IProblem.OverridingDeprecatedMethod /* 67109276 */:
            case IProblem.UsingDeprecatedConstructor /* 134217861 */:
                return 4;
            case IProblem.TypeHidingType /* 16777249 */:
                return CompilerOptions.TypeHiding;
            case IProblem.MaskedCatch /* 16777381 */:
                return 8;
            case IProblem.DiscouragedReference /* 16777496 */:
                return CompilerOptions.DiscouragedReference;
            case IProblem.ForbiddenReference /* 16777523 */:
                return 536870944;
            case IProblem.UnsafeGenericCast /* 16777761 */:
            case IProblem.UnsafeElementTypeConversion /* 16777801 */:
            case IProblem.UnsafeReturnTypeOverride /* 67109423 */:
            case IProblem.UnsafeGenericArrayForVarargs /* 67109438 */:
            case IProblem.PotentialHeapPollutionFromVararg /* 67109670 */:
                return CompilerOptions.UncheckedTypeOperation;
            case IProblem.AnnotationTypeUsedAsSuperInterface /* 16777842 */:
                return CompilerOptions.AnnotationSuperInterface;
            case IProblem.UnusedTypeParameter /* 16777877 */:
                return CompilerOptions.UnusedTypeParameter;
            case IProblem.RedundantSpecificationOfTypeArguments /* 16778100 */:
                return CompilerOptions.RedundantSpecificationOfTypeArguments;
            case IProblem.RequiredNonNullButProvidedNull /* 16778126 */:
            case IProblem.UninitializedNonNullField /* 33555366 */:
            case IProblem.UninitializedNonNullFieldHintMissingDefault /* 33555367 */:
            case IProblem.IllegalReturnNullityRedefinition /* 67109778 */:
            case IProblem.IllegalRedefinitionToNonNullParameter /* 67109779 */:
            case IProblem.IllegalDefinitionToNonNullParameter /* 67109780 */:
            case IProblem.ParameterLackingNullableAnnotation /* 67109782 */:
            case IProblem.ConflictingNullAnnotations /* 67109803 */:
            case IProblem.ConflictingInheritedNullAnnotations /* 67109804 */:
            case IProblem.ReferenceExpressionParameterNullityMismatch /* 67109821 */:
            case IProblem.ReferenceExpressionReturnNullRedef /* 67109823 */:
            case IProblem.ContradictoryNullAnnotationsInferredFunctionType /* 67109837 */:
            case IProblem.IllegalReturnNullityRedefinitionFreeTypeVariable /* 67109838 */:
            case IProblem.CannotImplementIncompatibleNullness /* 536871833 */:
            case IProblem.ContradictoryNullAnnotations /* 536871841 */:
            case IProblem.RequiredNonNullButProvidedSpecdNullable /* 536871845 */:
            case IProblem.NullityMismatchingTypeAnnotation /* 536871865 */:
            case IProblem.NullityMismatchingTypeAnnotationSuperHint /* 536871866 */:
            case IProblem.NullityMismatchTypeArgument /* 536871876 */:
            case IProblem.ContradictoryNullAnnotationsOnBound /* 536871877 */:
            case IProblem.ContradictoryNullAnnotationsInferred /* 536871878 */:
                return CompilerOptions.NullSpecViolation;
            case IProblem.RequiredNonNullButProvidedPotentialNull /* 16778127 */:
                return CompilerOptions.NullAnnotationInferenceConflict;
            case IProblem.RequiredNonNullButProvidedUnknown /* 16778128 */:
            case IProblem.ReferenceExpressionParameterNullityUnchecked /* 67109822 */:
            case IProblem.ReferenceExpressionReturnNullRedefUnchecked /* 67109824 */:
            case IProblem.NullityUncheckedTypeAnnotationDetail /* 536871867 */:
            case IProblem.NullityUncheckedTypeAnnotationDetailSuperHint /* 536871868 */:
            case IProblem.UnsafeNullnessCast /* 536871879 */:
                return CompilerOptions.NullUncheckedConversion;
            case IProblem.RequiredNonNullButProvidedFreeTypeVariable /* 16778195 */:
                return CompilerOptions.PessimisticNullAnalysisForFreeTypeVariables;
            case IProblem.NonNullTypeVariableFromLegacyMethod /* 16778196 */:
            case IProblem.NonNullMethodTypeVariableFromLegacyMethod /* 16778197 */:
                return CompilerOptions.NonNullTypeVariableFromLegacyInvocation;
            case IProblem.NeedToEmulateFieldReadAccess /* 33554622 */:
            case IProblem.NeedToEmulateFieldWriteAccess /* 33554623 */:
            case IProblem.NeedToEmulateMethodAccess /* 67109056 */:
            case IProblem.NeedToEmulateConstructorAccess /* 67109057 */:
                return 128;
            case IProblem.MissingEnumConstantCase /* 33555193 */:
            case IProblem.MissingEnumConstantCaseDespiteDefault /* 33555200 */:
                return CompilerOptions.MissingEnumConstantCase;
            case IProblem.NullableFieldReference /* 33555356 */:
            case IProblem.NullLocalVariableReference /* 536871363 */:
            case IProblem.NullUnboxing /* 536871373 */:
            case IProblem.NullExpressionReference /* 536871584 */:
                return CompilerOptions.NullReference;
            case IProblem.MethodButWithConstructorName /* 67108974 */:
                return 1;
            case IProblem.OverridingNonVisibleMethod /* 67109274 */:
                return 2;
            case IProblem.IncompatibleReturnTypeForNonInheritedInterfaceMethod /* 67109277 */:
            case IProblem.IncompatibleExceptionInThrowsClauseForNonInheritedInterfaceMethod /* 67109278 */:
                return 16384;
            case IProblem.OverridingMethodWithoutSuperInvocation /* 67109280 */:
                return CompilerOptions.OverridingMethodWithoutSuperInvocation;
            case IProblem.MissingSynchronizedModifierInInheritedMethod /* 67109281 */:
                return 805306368;
            case IProblem.UnusedTypeArgumentsForMethodInvocation /* 67109443 */:
            case IProblem.UnusedTypeArgumentsForConstructorInvocation /* 67109524 */:
                return CompilerOptions.UnusedTypeArguments;
            case IProblem.MissingOverrideAnnotation /* 67109491 */:
            case IProblem.MissingOverrideAnnotationForInterfaceMethodImplementation /* 67109500 */:
                return CompilerOptions.MissingOverrideAnnotation;
            case IProblem.MethodVarargsArgumentNeedCast /* 67109665 */:
            case IProblem.ConstructorVarargsArgumentNeedCast /* 134218530 */:
                return 536870976;
            case IProblem.ParameterLackingNonNullAnnotation /* 67109781 */:
                return CompilerOptions.NonnullParameterAnnotationDropped;
            case IProblem.RedundantNullAnnotation /* 67109786 */:
            case IProblem.RedundantNullDefaultAnnotation /* 536871837 */:
            case IProblem.RedundantNullDefaultAnnotationPackage /* 536871838 */:
            case IProblem.RedundantNullDefaultAnnotationType /* 536871839 */:
            case IProblem.RedundantNullDefaultAnnotationMethod /* 536871840 */:
                return CompilerOptions.RedundantNullAnnotation;
            case IProblem.UnusedImport /* 268435844 */:
                return 1024;
            case IProblem.ParameterAssignment /* 536870971 */:
                return CompilerOptions.ParameterAssignment;
            case IProblem.LocalVariableIsNeverUsed /* 536870973 */:
                return 16;
            case IProblem.ArgumentIsNeverUsed /* 536870974 */:
                return 32;
            case IProblem.ExceptionParameterIsNeverUsed /* 536870997 */:
                return CompilerOptions.UnusedExceptionParameter;
            case IProblem.LocalVariableHidingLocalVariable /* 536871002 */:
            case IProblem.ArgumentHidingLocalVariable /* 536871006 */:
            case IProblem.ArgumentHidingField /* 536871007 */:
            case IProblem.LocalVariableHidingField /* 570425435 */:
                return 65536;
            case IProblem.MissingSerialVersion /* 536871008 */:
                return CompilerOptions.MissingSerialVersion;
            case IProblem.UnusedObjectAllocation /* 536871060 */:
                return CompilerOptions.UnusedObjectAllocation;
            case IProblem.DeadCode /* 536871061 */:
                return CompilerOptions.DeadCode;
            case IProblem.NoImplicitStringConversionForCharArrayExpression /* 536871063 */:
                return 64;
            case IProblem.AssignmentHasNoEffect /* 536871090 */:
                return 8192;
            case IProblem.PossibleAccidentalBooleanAssignment /* 536871091 */:
                return 262144;
            case IProblem.SuperfluousSemicolon /* 536871092 */:
            case IProblem.EmptyControlFlowStatement /* 553648316 */:
                return 524288;
            case IProblem.FinallyMustCompleteNormally /* 536871096 */:
                return 16777216;
            case IProblem.UnusedMethodDeclaredThrownException /* 536871097 */:
            case IProblem.UnusedConstructorDeclaredThrownException /* 536871098 */:
                return 8388608;
            case IProblem.UnnecessaryElse /* 536871101 */:
                return CompilerOptions.UnnecessaryElse;
            case IProblem.FallthroughCase /* 536871106 */:
                return CompilerOptions.FallthroughCase;
            case IProblem.UnusedLabel /* 536871111 */:
                return CompilerOptions.UnusedLabel;
            case IProblem.ComparingIdentical /* 536871123 */:
                return CompilerOptions.ComparingIdentical;
            case IProblem.NonExternalizedStringLiteral /* 536871173 */:
            case IProblem.UnnecessaryNLSTag /* 536871177 */:
                return 256;
            case IProblem.UseAssertAsAnIdentifier /* 536871352 */:
                return 512;
            case IProblem.UseEnumAsAnIdentifier /* 536871353 */:
                return CompilerOptions.EnumUsedAsAnIdentifier;
            case IProblem.Task /* 536871362 */:
                return CompilerOptions.Tasks;
            case IProblem.PotentialNullLocalVariableReference /* 536871364 */:
            case IProblem.PotentialNullUnboxing /* 536871371 */:
            case IProblem.PotentialNullExpressionReference /* 536871585 */:
            case IProblem.PotentialNullMessageSendReference /* 536871831 */:
            case IProblem.ArrayReferencePotentialNullReference /* 536871863 */:
            case IProblem.DereferencingNullableExpression /* 536871864 */:
                return CompilerOptions.PotentialNullReference;
            case IProblem.RedundantNullCheckOnNullLocalVariable /* 536871365 */:
            case IProblem.NullLocalVariableComparisonYieldsFalse /* 536871366 */:
            case IProblem.RedundantLocalVariableNullAssignment /* 536871367 */:
            case IProblem.NullLocalVariableInstanceofYieldsFalse /* 536871368 */:
            case IProblem.RedundantNullCheckOnNonNullLocalVariable /* 536871369 */:
            case IProblem.NonNullLocalVariableComparisonYieldsFalse /* 536871370 */:
            case IProblem.NonNullExpressionComparisonYieldsFalse /* 536871582 */:
            case IProblem.RedundantNullCheckOnNonNullExpression /* 536871583 */:
            case IProblem.RedundantNullCheckOnNonNullMessageSend /* 536871832 */:
            case IProblem.RedundantNullCheckOnSpecdNonNullLocalVariable /* 536871843 */:
            case IProblem.SpecdNonNullLocalVariableComparisonYieldsFalse /* 536871844 */:
            case IProblem.NonNullMessageSendComparisonYieldsFalse /* 536871848 */:
            case IProblem.RedundantNullCheckOnNonNullSpecdField /* 536871849 */:
            case IProblem.NonNullSpecdFieldComparisonYieldsFalse /* 536871850 */:
            case IProblem.RedundantNullCheckOnField /* 536871853 */:
            case IProblem.FieldComparisonYieldsFalse /* 536871854 */:
            case IProblem.RedundantNullCheckAgainstNonNullType /* 536871873 */:
                return CompilerOptions.RedundantNullCheck;
            case IProblem.UndocumentedEmptyBlock /* 536871372 */:
                return 134217728;
            case IProblem.FieldMissingDeprecatedAnnotation /* 536871540 */:
            case IProblem.MethodMissingDeprecatedAnnotation /* 536871541 */:
            case IProblem.TypeMissingDeprecatedAnnotation /* 536871542 */:
                return CompilerOptions.MissingDeprecatedAnnotation;
            case IProblem.UnhandledWarningToken /* 536871543 */:
                return CompilerOptions.UnhandledWarningToken;
            case IProblem.UnusedWarningToken /* 536871547 */:
                return CompilerOptions.UnusedWarningToken;
            case IProblem.BoxingConversion /* 536871632 */:
            case IProblem.UnboxingConversion /* 536871633 */:
                return CompilerOptions.AutoBoxing;
            case IProblem.MissingEnumDefaultCase /* 536871678 */:
            case IProblem.MissingDefaultCase /* 536871679 */:
                return CompilerOptions.MissingDefaultCase;
            case IProblem.PotentiallyUnclosedCloseable /* 536871797 */:
            case IProblem.PotentiallyUnclosedCloseableAtExit /* 536871798 */:
                return CompilerOptions.PotentiallyUnclosedCloseable;
            case IProblem.UnclosedCloseable /* 536871799 */:
            case IProblem.UnclosedCloseableAtExit /* 536871800 */:
                return CompilerOptions.UnclosedCloseable;
            case IProblem.ExplicitlyClosedAutoCloseable /* 536871801 */:
                return CompilerOptions.ExplicitlyClosedAutoCloseable;
            case IProblem.MissingNonNullByDefaultAnnotationOnPackage /* 536871825 */:
            case IProblem.MissingNonNullByDefaultAnnotationOnType /* 536871842 */:
                return CompilerOptions.MissingNonNullByDefaultAnnotation;
            case IProblem.UnusedPrivateType /* 553648135 */:
            case IProblem.UnusedPrivateField /* 570425421 */:
            case IProblem.UnusedPrivateMethod /* 603979894 */:
            case IProblem.UnusedPrivateConstructor /* 603979910 */:
                return 32768;
            case IProblem.IndirectAccessToStaticType /* 553648146 */:
            case IProblem.IndirectAccessToStaticField /* 570425422 */:
            case IProblem.IndirectAccessToStaticMethod /* 603979895 */:
                return 268435456;
            case IProblem.UnnecessaryCast /* 553648309 */:
            case IProblem.UnnecessaryInstanceof /* 553648311 */:
                return 67108864;
            case IProblem.NonStaticAccessToStaticField /* 570425420 */:
            case IProblem.NonStaticAccessToStaticMethod /* 603979893 */:
                return 2048;
            case IProblem.UnqualifiedFieldAccess /* 570425423 */:
                return 4194304;
            case IProblem.FieldHidingLocalVariable /* 570425436 */:
            case IProblem.FieldHidingField /* 570425437 */:
                return 131072;
            case IProblem.MethodCanBeStatic /* 603979897 */:
                return CompilerOptions.MethodCanBeStatic;
            case IProblem.MethodCanBePotentiallyStatic /* 603979898 */:
                return CompilerOptions.MethodCanBePotentiallyStatic;
            default:
                switch (i) {
                    case IProblem.JavadocMissingHashCharacter /* -1610612221 */:
                    case IProblem.JavadocEmptyReturnTag /* -1610612220 */:
                    case IProblem.JavadocInvalidValueReference /* -1610612219 */:
                    case IProblem.JavadocUnexpectedText /* -1610612218 */:
                    case IProblem.JavadocInvalidParamTagName /* -1610612217 */:
                        return 33554432;
                    default:
                        switch (i) {
                            case IProblem.JavadocGenericMethodTypeArgumentMismatch /* -1610611886 */:
                            case IProblem.JavadocNonGenericMethod /* -1610611885 */:
                            case IProblem.JavadocIncorrectArityForParameterizedMethod /* -1610611884 */:
                            case IProblem.JavadocParameterizedMethodArgumentTypeMismatch /* -1610611883 */:
                            case IProblem.JavadocTypeArgumentsForRawGenericMethod /* -1610611882 */:
                            case IProblem.JavadocGenericConstructorTypeArgumentMismatch /* -1610611881 */:
                            case IProblem.JavadocNonGenericConstructor /* -1610611880 */:
                            case IProblem.JavadocIncorrectArityForParameterizedConstructor /* -1610611879 */:
                            case IProblem.JavadocParameterizedConstructorArgumentTypeMismatch /* -1610611878 */:
                            case IProblem.JavadocTypeArgumentsForRawGenericConstructor /* -1610611877 */:
                                return 33554432;
                            default:
                                switch (i) {
                                    case 975:
                                        return CompilerOptions.NullSpecViolation;
                                    case IProblem.UncheckedAccessOfValueOfFreeTypeVariable /* 976 */:
                                    case 977:
                                    case 978:
                                        return CompilerOptions.PessimisticNullAnalysisForFreeTypeVariables;
                                    default:
                                        switch (i) {
                                            case 16777547:
                                                return CompilerOptions.RedundantSuperinterface;
                                            case 16777548:
                                                return CompilerOptions.ShouldImplementHashcode;
                                            default:
                                                switch (i) {
                                                    case IProblem.UnsafeRawConstructorInvocation /* 16777746 */:
                                                    case IProblem.UnsafeRawMethodInvocation /* 16777747 */:
                                                    case IProblem.UnsafeTypeConversion /* 16777748 */:
                                                        return CompilerOptions.UncheckedTypeOperation;
                                                    default:
                                                        switch (i) {
                                                            case IProblem.UnsafeRawFieldAssignment /* 16777752 */:
                                                                return CompilerOptions.UncheckedTypeOperation;
                                                            case IProblem.FinalBoundForTypeVariable /* 16777753 */:
                                                                return CompilerOptions.FinalParameterBound;
                                                            default:
                                                                switch (i) {
                                                                    case IProblem.UnsafeRawGenericConstructorInvocation /* 16777785 */:
                                                                    case IProblem.UnsafeRawGenericMethodInvocation /* 16777786 */:
                                                                        return CompilerOptions.UncheckedTypeOperation;
                                                                    case IProblem.TypeParameterHidingType /* 16777787 */:
                                                                        return CompilerOptions.TypeHiding;
                                                                    case IProblem.RawTypeReference /* 16777788 */:
                                                                        return CompilerOptions.RawTypeReference;
                                                                    default:
                                                                        switch (i) {
                                                                            case IProblem.TypeHidingTypeParameterFromType /* 16777792 */:
                                                                            case IProblem.TypeHidingTypeParameterFromMethod /* 16777793 */:
                                                                                return CompilerOptions.TypeHiding;
                                                                            default:
                                                                                return 0;
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    private boolean isIdentifier(int i) {
        return i == 22;
    }

    private boolean isKeyword(int i) {
        if (i == 17 || i == 114 || i == 67 || i == 68 || i == 111 || i == 112) {
            return true;
        }
        switch (i) {
            case 34:
            case 35:
            case 36:
                return true;
            default:
                switch (i) {
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                        return true;
                    default:
                        switch (i) {
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                                return true;
                            default:
                                switch (i) {
                                    case 71:
                                    case 72:
                                    case 73:
                                    case 74:
                                    case 75:
                                    case 76:
                                    case 77:
                                    case 78:
                                    case 79:
                                    case 80:
                                    case 81:
                                    case 82:
                                        return true;
                                    default:
                                        switch (i) {
                                            case 95:
                                            case 96:
                                            case 97:
                                            case 98:
                                            case 99:
                                            case 100:
                                            case 101:
                                            case 102:
                                            case 103:
                                            case 104:
                                            case 105:
                                            case 106:
                                            case 107:
                                            case 108:
                                            case 109:
                                                return true;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
        }
    }

    private boolean javadocVisibility(int i, int i2) {
        if (i2 < 0) {
            return true;
        }
        int i3 = i2 & 7;
        if (i3 == 0) {
            return i == 0 || i == 2;
        }
        if (i3 != 2) {
            return (i3 == 4 && i == 1) ? false : true;
        }
        return i == 2;
    }

    public ProblemReporter(IErrorHandlingPolicy iErrorHandlingPolicy, CompilerOptions compilerOptions, IProblemFactory iProblemFactory) {
        super(iErrorHandlingPolicy, compilerOptions, iProblemFactory);
    }

    public static int getProblemCategory(int i, int i2) {
        if ((i & 128) == 0) {
            switch (getIrritant(i2)) {
                case 1:
                case 128:
                case 512:
                case 2048:
                case 4194304:
                case 134217728:
                case 268435456:
                case CompilerOptions.FinalParameterBound /* 536870916 */:
                case CompilerOptions.EnumUsedAsAnIdentifier /* 536870928 */:
                case CompilerOptions.AutoBoxing /* 536871168 */:
                case CompilerOptions.AnnotationSuperInterface /* 536871424 */:
                case CompilerOptions.MissingOverrideAnnotation /* 536872960 */:
                case CompilerOptions.MissingDeprecatedAnnotation /* 536879104 */:
                case CompilerOptions.ParameterAssignment /* 537133056 */:
                case CompilerOptions.MethodCanBeStatic /* 1073741840 */:
                case CompilerOptions.MethodCanBePotentiallyStatic /* 1073741856 */:
                case CompilerOptions.ExplicitlyClosedAutoCloseable /* 1073742336 */:
                    return 80;
                case 2:
                case 16384:
                case 65536:
                case 131072:
                case CompilerOptions.TypeHiding /* 536871936 */:
                    return 100;
                case 4:
                    return 110;
                case 8:
                case 64:
                case 8192:
                case 262144:
                case 524288:
                case 16777216:
                case CompilerOptions.MissingSerialVersion /* 536870920 */:
                case 536870976:
                case CompilerOptions.NullReference /* 536871040 */:
                case CompilerOptions.MissingEnumConstantCase /* 536875008 */:
                case CompilerOptions.FallthroughCase /* 537395200 */:
                case CompilerOptions.OverridingMethodWithoutSuperInvocation /* 537919488 */:
                case CompilerOptions.PotentialNullReference /* 538968064 */:
                case CompilerOptions.RedundantNullCheck /* 541065216 */:
                case CompilerOptions.ComparingIdentical /* 671088640 */:
                case 805306368:
                case CompilerOptions.ShouldImplementHashcode /* 1073741825 */:
                case CompilerOptions.DeadCode /* 1073741826 */:
                case CompilerOptions.UnusedObjectAllocation /* 1073741832 */:
                case CompilerOptions.UnclosedCloseable /* 1073741952 */:
                case CompilerOptions.PotentiallyUnclosedCloseable /* 1073742080 */:
                case CompilerOptions.MissingDefaultCase /* 1073774592 */:
                case CompilerOptions.PessimisticNullAnalysisForFreeTypeVariables /* 1074266112 */:
                case CompilerOptions.NonNullTypeVariableFromLegacyInvocation /* 1074790400 */:
                    return 90;
                case 16:
                case 32:
                case 1024:
                case 32768:
                case 8388608:
                case 67108864:
                case CompilerOptions.UnnecessaryElse /* 536870913 */:
                case CompilerOptions.UnhandledWarningToken /* 536903680 */:
                case CompilerOptions.UnusedLabel /* 537001984 */:
                case CompilerOptions.UnusedWarningToken /* 570425344 */:
                case CompilerOptions.RedundantSuperinterface /* 603979776 */:
                case CompilerOptions.RedundantSpecificationOfTypeArguments /* 1073741888 */:
                case CompilerOptions.UnusedTypeParameter /* 1073807360 */:
                case CompilerOptions.UnusedExceptionParameter /* 1074003968 */:
                    return 120;
                case 256:
                    return 140;
                case 4096:
                    return 0;
                case 1048576:
                case 2097152:
                case 33554432:
                case 33554436:
                    return 70;
                case CompilerOptions.UncheckedTypeOperation /* 536870914 */:
                case CompilerOptions.RawTypeReference /* 536936448 */:
                    return 130;
                case 536870944:
                case CompilerOptions.DiscouragedReference /* 536887296 */:
                    return 150;
                case CompilerOptions.NullSpecViolation /* 1073742848 */:
                case CompilerOptions.NullAnnotationInferenceConflict /* 1073743872 */:
                case CompilerOptions.NullUncheckedConversion /* 1073745920 */:
                case CompilerOptions.MissingNonNullByDefaultAnnotation /* 1073758208 */:
                case CompilerOptions.NonnullParameterAnnotationDropped /* 1073872896 */:
                    return 90;
                case CompilerOptions.RedundantNullAnnotation /* 1073750016 */:
                    return 120;
            }
        }
        if (i2 == 16777540 || i2 == 536871612) {
            return 10;
        }
        if ((1073741824 & i2) != 0) {
            return 20;
        }
        if ((268435456 & i2) != 0) {
            return 30;
        }
        if ((16777216 & i2) != 0) {
            return 40;
        }
        return (234881024 & i2) != 0 ? 50 : 60;
    }

    public void abortDueToInternalError(String str) {
        abortDueToInternalError(str, null);
    }

    public void abortDueToInternalError(String str, ASTNode aSTNode) {
        String[] strArr = {str};
        handle(0, strArr, strArr, 159, aSTNode == null ? 0 : aSTNode.sourceStart, aSTNode == null ? 0 : aSTNode.sourceEnd);
    }

    public void abstractMethodCannotBeOverridden(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding) {
        handle(IProblem.AbstractMethodCannotBeOverridden, new String[]{new String(sourceTypeBinding.sourceName()), new String(CharOperation.concat(methodBinding.declaringClass.readableName(), methodBinding.readableName(), '.'))}, new String[]{new String(sourceTypeBinding.sourceName()), new String(CharOperation.concat(methodBinding.declaringClass.shortReadableName(), methodBinding.shortReadableName(), '.'))}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void abstractMethodInAbstractClass(SourceTypeBinding sourceTypeBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        if (sourceTypeBinding.isEnum() && sourceTypeBinding.isLocalType()) {
            String[] strArr = {new String(sourceTypeBinding.scope.enclosingMethodScope().initializedField.sourceField().f617name), new String(abstractMethodDeclaration.selector)};
            handle(IProblem.AbstractMethodInEnum, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        } else {
            String[] strArr2 = {new String(sourceTypeBinding.sourceName()), new String(abstractMethodDeclaration.selector)};
            handle(IProblem.AbstractMethodInAbstractClass, strArr2, strArr2, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        }
    }

    public void abstractMethodInConcreteClass(SourceTypeBinding sourceTypeBinding) {
        if (sourceTypeBinding.isEnum() && sourceTypeBinding.isLocalType()) {
            FieldDeclaration fieldDeclarationSourceField = sourceTypeBinding.scope.enclosingMethodScope().initializedField.sourceField();
            String[] strArr = {new String(fieldDeclarationSourceField.f617name)};
            handle(IProblem.EnumConstantCannotDefineAbstractMethod, strArr, strArr, fieldDeclarationSourceField.sourceStart(), fieldDeclarationSourceField.sourceEnd());
        } else {
            String[] strArr2 = {new String(sourceTypeBinding.sourceName())};
            handle(16777549, strArr2, strArr2, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
        }
    }

    public void abstractMethodMustBeImplemented(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding) {
        if (sourceTypeBinding.isEnum() && sourceTypeBinding.isLocalType()) {
            FieldDeclaration fieldDeclarationSourceField = sourceTypeBinding.scope.enclosingMethodScope().initializedField.sourceField();
            handle(IProblem.EnumConstantMustImplementAbstractMethod, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(fieldDeclarationSourceField.f617name)}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(fieldDeclarationSourceField.f617name)}, fieldDeclarationSourceField.sourceStart(), fieldDeclarationSourceField.sourceEnd());
        } else {
            handle(IProblem.AbstractMethodMustBeImplemented, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), new String(sourceTypeBinding.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), new String(sourceTypeBinding.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
        }
    }

    public void abstractMethodMustBeImplemented(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.AbstractMethodMustBeImplementedOverConcreteMethod, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), new String(sourceTypeBinding.readableName()), new String(methodBinding2.selector), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), new String(sourceTypeBinding.shortReadableName()), new String(methodBinding2.selector), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void abstractMethodNeedingNoBody(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.BodyForAbstractMethod, NoArgument, NoArgument, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd, abstractMethodDeclaration, abstractMethodDeclaration.compilationResult());
    }

    public void alreadyDefinedLabel(char[] cArr, ASTNode aSTNode) {
        String[] strArr = {new String(cArr)};
        handle(IProblem.DuplicateLabel, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void annotationCannotOverrideMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        handle(IProblem.AnnotationCannotOverrideMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding2.declaringClass.readableName()), new String(methodBinding2.selector), typesAsString(methodBinding2, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding2.declaringClass.shortReadableName()), new String(methodBinding2.selector), typesAsString(methodBinding2, true)}, abstractMethodDeclarationSourceMethod.sourceStart, abstractMethodDeclarationSourceMethod.sourceEnd);
    }

    public void annotationCircularity(TypeBinding typeBinding, TypeBinding typeBinding2, TypeReference typeReference) {
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            handle(IProblem.AnnotationCircularitySelfReference, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
        } else {
            handle(IProblem.AnnotationCircularity, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
        }
    }

    public void annotationMembersCannotHaveParameters(AnnotationMethodDeclaration annotationMethodDeclaration) {
        handle(IProblem.AnnotationMembersCannotHaveParameters, NoArgument, NoArgument, annotationMethodDeclaration.sourceStart, annotationMethodDeclaration.sourceEnd);
    }

    public void annotationMembersCannotHaveTypeParameters(AnnotationMethodDeclaration annotationMethodDeclaration) {
        handle(IProblem.AnnotationMembersCannotHaveTypeParameters, NoArgument, NoArgument, annotationMethodDeclaration.sourceStart, annotationMethodDeclaration.sourceEnd);
    }

    public void annotationTypeDeclarationCannotHaveConstructor(ConstructorDeclaration constructorDeclaration) {
        handle(IProblem.AnnotationTypeDeclarationCannotHaveConstructor, NoArgument, NoArgument, constructorDeclaration.sourceStart, constructorDeclaration.sourceEnd);
    }

    public void annotationTypeDeclarationCannotHaveSuperclass(TypeDeclaration typeDeclaration) {
        handle(IProblem.AnnotationTypeDeclarationCannotHaveSuperclass, NoArgument, NoArgument, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void annotationTypeDeclarationCannotHaveSuperinterfaces(TypeDeclaration typeDeclaration) {
        handle(IProblem.AnnotationTypeDeclarationCannotHaveSuperinterfaces, NoArgument, NoArgument, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void annotationTypeUsedAsSuperinterface(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, ReferenceBinding referenceBinding) {
        handle(IProblem.AnnotationTypeUsedAsSuperInterface, new String[]{new String(referenceBinding.readableName()), new String(sourceTypeBinding.sourceName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(sourceTypeBinding.sourceName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void annotationValueMustBeAnnotation(TypeBinding typeBinding, char[] cArr, Expression expression, TypeBinding typeBinding2) {
        String str = new String(cArr);
        handle(IProblem.AnnotationValueMustBeAnnotation, new String[]{new String(typeBinding.readableName()), str, new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), str, new String(typeBinding2.readableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void annotationValueMustBeArrayInitializer(TypeBinding typeBinding, char[] cArr, Expression expression) {
        String str = new String(cArr);
        handle(IProblem.AnnotationValueMustBeArrayInitializer, new String[]{new String(typeBinding.readableName()), str}, new String[]{new String(typeBinding.shortReadableName()), str}, expression.sourceStart, expression.sourceEnd);
    }

    public void annotationValueMustBeClassLiteral(TypeBinding typeBinding, char[] cArr, Expression expression) {
        String str = new String(cArr);
        handle(IProblem.AnnotationValueMustBeClassLiteral, new String[]{new String(typeBinding.readableName()), str}, new String[]{new String(typeBinding.shortReadableName()), str}, expression.sourceStart, expression.sourceEnd);
    }

    public void annotationValueMustBeConstant(TypeBinding typeBinding, char[] cArr, Expression expression, boolean z) {
        String str = new String(cArr);
        if (z) {
            handle(IProblem.AnnotationValueMustBeAnEnumConstant, new String[]{new String(typeBinding.readableName()), str}, new String[]{new String(typeBinding.shortReadableName()), str}, expression.sourceStart, expression.sourceEnd);
        } else {
            handle(IProblem.AnnotationValueMustBeConstant, new String[]{new String(typeBinding.readableName()), str}, new String[]{new String(typeBinding.shortReadableName()), str}, expression.sourceStart, expression.sourceEnd);
        }
    }

    public void anonymousClassCannotExtendFinalClass(TypeReference typeReference, TypeBinding typeBinding) {
        handle(IProblem.AnonymousClassCannotExtendFinalClass, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void argumentTypeCannotBeVoid(ASTNode aSTNode, Argument argument) {
        String[] strArr = {new String(argument.f617name)};
        handle(IProblem.ArgumentTypeCannotBeVoid, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void argumentTypeCannotBeVoidArray(Argument argument) {
        handle(IProblem.CannotAllocateVoidArray, NoArgument, NoArgument, argument.type.sourceStart, argument.type.sourceEnd);
    }

    public void arrayConstantsOnlyInArrayInitializers(int i, int i2) {
        handle(IProblem.ArrayConstantsOnlyInArrayInitializers, NoArgument, NoArgument, i, i2);
    }

    public void assignmentHasNoEffect(AbstractVariableDeclaration abstractVariableDeclaration, char[] cArr) {
        int iComputeSeverity = computeSeverity(IProblem.AssignmentHasNoEffect);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(cArr)};
        handle(IProblem.AssignmentHasNoEffect, strArr, strArr, iComputeSeverity, abstractVariableDeclaration.sourceStart, abstractVariableDeclaration.initialization != null ? abstractVariableDeclaration.initialization.sourceEnd : abstractVariableDeclaration.sourceEnd);
    }

    public void assignmentHasNoEffect(Assignment assignment, char[] cArr) {
        int iComputeSeverity = computeSeverity(IProblem.AssignmentHasNoEffect);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(cArr)};
        handle(IProblem.AssignmentHasNoEffect, strArr, strArr, iComputeSeverity, assignment.sourceStart, assignment.sourceEnd);
    }

    public void attemptToReturnNonVoidExpression(ReturnStatement returnStatement, TypeBinding typeBinding) {
        handle(IProblem.VoidMethodReturnsValue, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, returnStatement.sourceStart, returnStatement.sourceEnd);
    }

    public void attemptToReturnVoidValue(ReturnStatement returnStatement) {
        handle(IProblem.MethodReturnsVoid, NoArgument, NoArgument, returnStatement.sourceStart, returnStatement.sourceEnd);
    }

    public void autoboxing(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (this.options.getSeverity(CompilerOptions.AutoBoxing) == 256) {
            return;
        }
        handle(typeBinding.isBaseType() ? IProblem.BoxingConversion : IProblem.UnboxingConversion, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void boundCannotBeArray(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.BoundCannotBeArray, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void boundMustBeAnInterface(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.BoundMustBeAnInterface, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void bytecodeExceeds64KLimit(MethodBinding methodBinding, int i, int i2) {
        handle(IProblem.BytecodeExceeds64KLimit, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true)}, 159, i, i2);
    }

    public void bytecodeExceeds64KLimit(AbstractMethodDeclaration abstractMethodDeclaration) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        if (abstractMethodDeclaration.isConstructor()) {
            handle(IProblem.BytecodeExceeds64KLimitForConstructor, new String[]{new String(abstractMethodDeclaration.selector), typesAsString(methodBinding, false)}, new String[]{new String(abstractMethodDeclaration.selector), typesAsString(methodBinding, true)}, 159, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        } else {
            bytecodeExceeds64KLimit(methodBinding, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        }
    }

    public void bytecodeExceeds64KLimit(LambdaExpression lambdaExpression) {
        bytecodeExceeds64KLimit(lambdaExpression.binding, lambdaExpression.sourceStart, lambdaExpression.diagnosticsSourceEnd());
    }

    public void bytecodeExceeds64KLimit(TypeDeclaration typeDeclaration) {
        handle(536870976, NoArgument, NoArgument, 159, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void cannotAllocateVoidArray(Expression expression) {
        handle(IProblem.CannotAllocateVoidArray, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void cannotAssignToFinalField(FieldBinding fieldBinding, ASTNode aSTNode) {
        String[] strArr = new String[2];
        strArr[0] = fieldBinding.declaringClass == null ? "array" : new String(fieldBinding.declaringClass.readableName());
        strArr[1] = new String(fieldBinding.readableName());
        String[] strArr2 = new String[2];
        strArr2[0] = fieldBinding.declaringClass != null ? new String(fieldBinding.declaringClass.shortReadableName()) : "array";
        strArr2[1] = new String(fieldBinding.shortReadableName());
        handle(IProblem.FinalFieldAssignment, strArr, strArr2, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void cannotAssignToFinalLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int i;
        if ((localVariableBinding.tagBits & 4096) != 0) {
            i = IProblem.AssignmentToMultiCatchParameter;
        } else {
            i = (localVariableBinding.tagBits & 8192) != 0 ? IProblem.AssignmentToResource : IProblem.NonBlankFinalLocalAssignment;
        }
        int i2 = i;
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(i2, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void cannotAssignToFinalOuterLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(IProblem.FinalOuterLocalAssignment, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void cannotDefineDimensionsAndInitializer(ArrayAllocationExpression arrayAllocationExpression) {
        handle(IProblem.CannotDefineDimensionExpressionsWithInit, NoArgument, NoArgument, arrayAllocationExpression.sourceStart, arrayAllocationExpression.sourceEnd);
    }

    public void cannotDireclyInvokeAbstractMethod(ASTNode aSTNode, MethodBinding methodBinding) {
        handle(IProblem.DirectInvocationOfAbstractMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void cannotExtendEnum(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, TypeBinding typeBinding) {
        String str = new String(sourceTypeBinding.sourceName());
        String str2 = new String(typeBinding.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        if (str3.equals(str)) {
            str3 = str2;
        }
        handle(IProblem.CannotExtendEnum, new String[]{str2, str}, new String[]{str3, str}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void cannotImportPackage(ImportReference importReference) {
        String[] strArr = {CharOperation.toString(importReference.tokens)};
        handleUntagged(IProblem.CannotImportPackage, strArr, strArr, importReference.sourceStart, importReference.sourceEnd);
    }

    public void cannotInstantiate(Expression expression, TypeBinding typeBinding) {
        handle(IProblem.InvalidClassInstantiation, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void cannotInvokeSuperConstructorInEnum(ExplicitConstructorCall explicitConstructorCall, MethodBinding methodBinding) {
        handle(IProblem.CannotInvokeSuperConstructorInEnum, new String[]{new String(methodBinding.declaringClass.sourceName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.sourceName()), typesAsString(methodBinding, true)}, explicitConstructorCall.sourceStart, explicitConstructorCall.sourceEnd);
    }

    public void cannotReadSource(CompilationUnitDeclaration compilationUnitDeclaration, AbortCompilationUnit abortCompilationUnit, boolean z) {
        String str = new String(compilationUnitDeclaration.compilationResult.fileName);
        if (abortCompilationUnit.exception instanceof CharConversionException) {
            String property = abortCompilationUnit.encoding;
            if (property == null) {
                property = System.getProperty(SystemProperties.FILE_ENCODING);
            }
            String[] strArr = {str, property};
            handle(IProblem.InvalidEncoding, strArr, strArr, 0, 0);
            return;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        if (z) {
            abortCompilationUnit.exception.printStackTrace(printWriter);
            System.err.println(stringWriter.toString());
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
        }
        printWriter.print(abortCompilationUnit.exception.getClass().getName());
        printWriter.print(':');
        printWriter.print(abortCompilationUnit.exception.getMessage());
        String[] strArr2 = {str, stringWriter.toString()};
        handle(IProblem.CannotReadSource, strArr2, strArr2, 0, 0);
    }

    public void cannotReferToNonFinalOuterLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(IProblem.OuterLocalMustBeFinal, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void cannotReferToNonEffectivelyFinalOuterLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(IProblem.OuterLocalMustBeEffectivelyFinal, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void cannotReturnInInitializer(ASTNode aSTNode) {
        handle(IProblem.CannotReturnInInitializer, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void cannotThrowNull(ASTNode aSTNode) {
        handle(IProblem.CannotThrowNull, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void cannotThrowType(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.CannotThrowType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalArrayOfUnionType(char[] cArr, TypeReference typeReference) {
        handle(IProblem.IllegalArrayOfUnionType, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void cannotUseQualifiedEnumConstantInCaseLabel(Reference reference, FieldBinding fieldBinding) {
        handle(IProblem.IllegalQualifiedEnumConstantLabel, new String[]{String.valueOf(fieldBinding.declaringClass.readableName()), String.valueOf(fieldBinding.f628name)}, new String[]{String.valueOf(fieldBinding.declaringClass.shortReadableName()), String.valueOf(fieldBinding.f628name)}, reference.sourceStart(), reference.sourceEnd());
    }

    public void cannotUseSuperInCodeSnippet(int i, int i2) {
        handle(IProblem.CannotUseSuperInCodeSnippet, NoArgument, NoArgument, 159, i, i2);
    }

    public void cannotUseSuperInJavaLangObject(ASTNode aSTNode) {
        handle(IProblem.ObjectHasNoSuperclass, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void targetTypeIsNotAFunctionalInterface(FunctionalExpression functionalExpression) {
        handle(IProblem.TargetTypeNotAFunctionalInterface, NoArgument, NoArgument, functionalExpression.sourceStart, functionalExpression.diagnosticsSourceEnd());
    }

    public void illFormedParameterizationOfFunctionalInterface(FunctionalExpression functionalExpression) {
        handle(IProblem.illFormedParameterizationOfFunctionalInterface, NoArgument, NoArgument, functionalExpression.sourceStart, functionalExpression.diagnosticsSourceEnd());
    }

    public void lambdaSignatureMismatched(LambdaExpression lambdaExpression) {
        handle(IProblem.lambdaSignatureMismatched, new String[]{new String(lambdaExpression.descriptor.readableName())}, new String[]{new String(lambdaExpression.descriptor.shortReadableName())}, lambdaExpression.sourceStart, lambdaExpression.diagnosticsSourceEnd());
    }

    public void lambdaParameterTypeMismatched(Argument argument, TypeReference typeReference, TypeBinding typeBinding) {
        String str = new String(argument.f617name);
        handle(typeBinding.isTypeVariable() ? IProblem.IncompatibleLambdaParameterType : IProblem.lambdaParameterTypeMismatched, new String[]{str, new String(typeBinding.readableName())}, new String[]{str, new String(typeBinding.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void lambdaExpressionCannotImplementGenericMethod(LambdaExpression lambdaExpression, MethodBinding methodBinding) {
        String str = new String(methodBinding.selector);
        handle(IProblem.NoGenericLambda, new String[]{str, new String(methodBinding.declaringClass.readableName())}, new String[]{str, new String(methodBinding.declaringClass.shortReadableName())}, lambdaExpression.sourceStart, lambdaExpression.diagnosticsSourceEnd());
    }

    public void caseExpressionMustBeConstant(Expression expression) {
        handle(IProblem.NonConstantExpression, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void classExtendFinalClass(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, TypeBinding typeBinding) {
        String str = new String(sourceTypeBinding.sourceName());
        String str2 = new String(typeBinding.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        if (str3.equals(str)) {
            str3 = str2;
        }
        handle(IProblem.ClassExtendFinalClass, new String[]{str2, str}, new String[]{str3, str}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void codeSnippetMissingClass(String str, int i, int i2) {
        String[] strArr = {str};
        handle(IProblem.CodeSnippetMissingClass, strArr, strArr, 159, i, i2);
    }

    public void codeSnippetMissingMethod(String str, String str2, String str3, int i, int i2) {
        String[] strArr = {str, str2, str3};
        handle(IProblem.CodeSnippetMissingMethod, strArr, strArr, 159, i, i2);
    }

    public void comparingIdenticalExpressions(Expression expression) {
        int iComputeSeverity = computeSeverity(IProblem.ComparingIdentical);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.ComparingIdentical, NoArgument, NoArgument, iComputeSeverity, expression.sourceStart, expression.sourceEnd);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:19:0x0033  */
    /* JADX WARN: Code duplicated, block: B:27:0x0044  */
    /* JADX WARN: Code duplicated, block: B:29:0x004a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:35:0x005e  */
    /* JADX WARN: Code duplicated, block: B:37:0x0063  */
    /* JADX WARN: Code duplicated, block: B:42:0x0071 A[RETURN] */
    /*  JADX ERROR: UnsupportedOperationException in pass: RegionMakerVisitor
        java.lang.UnsupportedOperationException
        	at java.base/java.util.Collections$UnmodifiableCollection.add(Collections.java:1093)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker$1.leaveRegion(SwitchRegionMaker.java:419)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:31)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaksForCase(SwitchRegionMaker.java:399)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.insertBreaks(SwitchRegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.leaveRegion(PostProcessRegions.java:31)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:91)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.PostProcessRegions.process(PostProcessRegions.java:21)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:31)
        */
    @Override // org.eclipse.jdt.internal.compiler.problem.ProblemHandler
    public int computeSeverity(int r4) {
        /*
            Method dump skipped, instruction units count: 262
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.problem.ProblemReporter.computeSeverity(int):int");
    }

    public void conditionalArgumentsIncompatibleTypes(ConditionalExpression conditionalExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        handle(16777232, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, conditionalExpression.sourceStart, conditionalExpression.sourceEnd);
    }

    public void conflictingImport(ImportReference importReference) {
        String[] strArr = {CharOperation.toString(importReference.tokens)};
        handleUntagged(IProblem.ConflictingImport, strArr, strArr, importReference.sourceStart, importReference.sourceEnd);
    }

    public void constantOutOfRange(Literal literal, TypeBinding typeBinding) {
        String[] strArr = {new String(typeBinding.readableName()), new String(literal.source())};
        handle(IProblem.NumericValueOutOfRange, strArr, strArr, literal.sourceStart, literal.sourceEnd);
    }

    public void corruptedSignature(TypeBinding typeBinding, char[] cArr, int i) {
        handle(IProblem.CorruptedSignature, new String[]{new String(typeBinding.readableName()), new String(cArr), String.valueOf(i)}, new String[]{new String(typeBinding.shortReadableName()), new String(cArr), String.valueOf(i)}, 159, 0, 0);
    }

    public void defaultMethodOverridesObjectMethod(MethodBinding methodBinding) {
        int i;
        int i2;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        if (abstractMethodDeclarationSourceMethod != null) {
            int i3 = abstractMethodDeclarationSourceMethod.sourceStart;
            i2 = abstractMethodDeclarationSourceMethod.sourceEnd;
            i = i3;
        } else {
            i = 0;
            i2 = 0;
        }
        handle(IProblem.DefaultMethodOverridesObjectMethod, NoArgument, NoArgument, i, i2);
    }

    public void defaultModifierIllegallySpecified(int i, int i2) {
        handle(IProblem.IllegalDefaultModifierSpecification, NoArgument, NoArgument, i, i2);
    }

    public void deprecatedField(FieldBinding fieldBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.UsingDeprecatedField);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.UsingDeprecatedField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void deprecatedMethod(MethodBinding methodBinding, ASTNode aSTNode) {
        int i;
        boolean zIsConstructor = methodBinding.isConstructor();
        int iComputeSeverity = computeSeverity(zIsConstructor ? IProblem.UsingDeprecatedConstructor : IProblem.UsingDeprecatedMethod);
        if (iComputeSeverity == 256) {
            return;
        }
        if (zIsConstructor) {
            if (aSTNode instanceof AllocationExpression) {
                AllocationExpression allocationExpression = (AllocationExpression) aSTNode;
                if (allocationExpression.enumConstant != null) {
                    int i2 = allocationExpression.enumConstant.sourceStart;
                }
                i = allocationExpression.type.sourceStart;
            } else {
                i = -1;
            }
            handle(IProblem.UsingDeprecatedConstructor, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity, i == -1 ? aSTNode.sourceStart : i, aSTNode.sourceEnd);
            return;
        }
        int i3 = aSTNode instanceof MessageSend ? (int) (((MessageSend) aSTNode).nameSourcePosition >>> 32) : -1;
        handle(IProblem.UsingDeprecatedMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, i3 == -1 ? aSTNode.sourceStart : i3, aSTNode.sourceEnd);
    }

    public void deprecatedType(TypeBinding typeBinding, ASTNode aSTNode) {
        deprecatedType(typeBinding, aSTNode, Integer.MAX_VALUE);
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0029  */
    public void deprecatedType(TypeBinding typeBinding, ASTNode aSTNode, int i) {
        int iComputeSeverity;
        int i2;
        if (aSTNode == null || (iComputeSeverity = computeSeverity(IProblem.UsingDeprecatedType)) == 256) {
            return;
        }
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if (aSTNode instanceof QualifiedTypeReference) {
            QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) aSTNode;
            if (i < Integer.MAX_VALUE) {
                i2 = (int) (qualifiedTypeReference.sourcePositions[i] >> 32);
            } else {
                i2 = -1;
            }
        } else {
            i2 = -1;
        }
        handle(IProblem.UsingDeprecatedType, new String[]{new String(typeBindingLeafComponentType.readableName())}, new String[]{new String(typeBindingLeafComponentType.shortReadableName())}, iComputeSeverity, i2 == -1 ? aSTNode.sourceStart : i2, nodeSourceEnd(null, aSTNode, i));
    }

    public void disallowedTargetForAnnotation(Annotation annotation) {
        handle(IProblem.DisallowedTargetForAnnotation, new String[]{new String(annotation.resolvedType.readableName())}, new String[]{new String(annotation.resolvedType.shortReadableName())}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void explitAnnotationTargetRequired(Annotation annotation) {
        handle(IProblem.ExplicitAnnotationTargetRequired, NoArgument, NoArgument, annotation.sourceStart, annotation.sourceEnd);
    }

    public void polymorphicMethodNotBelow17(ASTNode aSTNode) {
        handle(IProblem.PolymorphicMethodNotBelow17, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void multiCatchNotBelow17(ASTNode aSTNode) {
        handle(IProblem.MultiCatchNotBelow17, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void duplicateAnnotation(Annotation annotation, long j) {
        handle(j >= ClassFileConstants.JDK1_8 ? IProblem.DuplicateAnnotationNotMarkedRepeatable : IProblem.DuplicateAnnotation, new String[]{new String(annotation.resolvedType.readableName())}, new String[]{new String(annotation.resolvedType.shortReadableName())}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void duplicateAnnotationValue(TypeBinding typeBinding, MemberValuePair memberValuePair) {
        String str = new String(memberValuePair.f618name);
        handle(IProblem.DuplicateAnnotationMember, new String[]{str, new String(typeBinding.readableName())}, new String[]{str, new String(typeBinding.shortReadableName())}, memberValuePair.sourceStart, memberValuePair.sourceEnd);
    }

    public void duplicateBounds(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.DuplicateBounds, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void duplicateCase(CaseStatement caseStatement) {
        handle(IProblem.DuplicateCase, NoArgument, NoArgument, caseStatement.sourceStart, caseStatement.sourceEnd);
    }

    public void duplicateDefaultCase(ASTNode aSTNode) {
        handle(IProblem.DuplicateDefaultCase, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void duplicateEnumSpecialMethod(SourceTypeBinding sourceTypeBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.CannotDeclareEnumSpecialMethod, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void duplicateFieldInType(SourceTypeBinding sourceTypeBinding, FieldDeclaration fieldDeclaration) {
        handle(IProblem.DuplicateField, new String[]{new String(sourceTypeBinding.sourceName()), new String(fieldDeclaration.f617name)}, new String[]{new String(sourceTypeBinding.shortReadableName()), new String(fieldDeclaration.f617name)}, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void duplicateImport(ImportReference importReference) {
        String[] strArr = {CharOperation.toString(importReference.tokens)};
        handleUntagged(IProblem.DuplicateImport, strArr, strArr, importReference.sourceStart, importReference.sourceEnd);
    }

    public void duplicateInheritedMethods(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding methodBinding2, boolean z) {
        int i;
        if (TypeBinding.notEquals(methodBinding.declaringClass, methodBinding2.declaringClass)) {
            if (!methodBinding.isDefaultMethod() || !methodBinding2.isDefaultMethod()) {
                i = IProblem.DuplicateInheritedMethods;
            } else if (!z) {
                return;
            } else {
                i = IProblem.DuplicateInheritedDefaultMethods;
            }
            handle(i, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, methodBinding.original().parameters, false), typesAsString(methodBinding2, methodBinding2.original().parameters, false), new String(methodBinding.declaringClass.readableName()), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, methodBinding.original().parameters, true), typesAsString(methodBinding2, methodBinding2.original().parameters, true), new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding2.declaringClass.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
            return;
        }
        handle(IProblem.DuplicateParameterizedMethods, new String[]{new String(methodBinding.selector), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, methodBinding.original().parameters, false), typesAsString(methodBinding2, methodBinding2.original().parameters, false)}, new String[]{new String(methodBinding.selector), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, methodBinding.original().parameters, true), typesAsString(methodBinding2, methodBinding2.original().parameters, true)}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void duplicateInitializationOfBlankFinalField(FieldBinding fieldBinding, Reference reference) {
        String[] strArr = {new String(fieldBinding.readableName())};
        handle(IProblem.DuplicateBlankFinalFieldInitialization, strArr, strArr, nodeSourceStart(fieldBinding, reference), nodeSourceEnd(fieldBinding, reference));
    }

    public void duplicateInitializationOfFinalLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(IProblem.DuplicateFinalLocalInitialization, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void duplicateMethodInType(AbstractMethodDeclaration abstractMethodDeclaration, boolean z, int i) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        if (z) {
            handle(IProblem.DuplicateMethod, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, i, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        } else {
            handle(IProblem.DuplicateMethodErasure, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(abstractMethodDeclaration.selector), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, i, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
        }
    }

    public void duplicateModifierForField(ReferenceBinding referenceBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.DuplicateModifierForField, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void duplicateModifierForMethod(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.DuplicateModifierForMethod, new String[]{new String(referenceBinding.sourceName()), new String(abstractMethodDeclaration.selector)}, new String[]{new String(referenceBinding.shortReadableName()), new String(abstractMethodDeclaration.selector)}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void duplicateModifierForType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(16777517, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void duplicateModifierForVariable(LocalDeclaration localDeclaration, boolean z) {
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(z ? IProblem.DuplicateModifierForArgument : IProblem.DuplicateModifierForVariable, strArr, strArr, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void duplicateNestedType(TypeDeclaration typeDeclaration) {
        String[] strArr = {new String(typeDeclaration.f619name)};
        handle(IProblem.DuplicateNestedType, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void duplicateSuperinterface(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, ReferenceBinding referenceBinding) {
        handle(IProblem.DuplicateSuperInterface, new String[]{new String(referenceBinding.readableName()), new String(sourceTypeBinding.sourceName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(sourceTypeBinding.sourceName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void duplicateTargetInTargetAnnotation(TypeBinding typeBinding, NameReference nameReference) {
        FieldBinding fieldBinding = nameReference.fieldBinding();
        String str = new String(fieldBinding.f628name);
        handle(IProblem.DuplicateTargetInTargetAnnotation, new String[]{str, new String(typeBinding.readableName())}, new String[]{str, new String(typeBinding.shortReadableName())}, nodeSourceStart(fieldBinding, nameReference), nodeSourceEnd(fieldBinding, nameReference));
    }

    public void duplicateTypeParameterInType(TypeParameter typeParameter) {
        handle(IProblem.DuplicateTypeVariable, new String[]{new String(typeParameter.f617name)}, new String[]{new String(typeParameter.f617name)}, typeParameter.sourceStart, typeParameter.sourceEnd);
    }

    public void duplicateTypes(CompilationUnitDeclaration compilationUnitDeclaration, TypeDeclaration typeDeclaration) {
        String[] strArr = {new String(compilationUnitDeclaration.getFileName()), new String(typeDeclaration.f619name)};
        this.referenceContext = typeDeclaration;
        int i = typeDeclaration.sourceEnd;
        if (i <= 0) {
            i = -1;
        }
        handle(IProblem.DuplicateTypes, strArr, strArr, typeDeclaration.sourceStart, i, compilationUnitDeclaration.compilationResult);
    }

    public void emptyControlFlowStatement(int i, int i2) {
        handle(IProblem.EmptyControlFlowStatement, NoArgument, NoArgument, i, i2);
    }

    public void enumAbstractMethodMustBeImplemented(AbstractMethodDeclaration abstractMethodDeclaration) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.EnumAbstractMethodMustBeImplemented, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, abstractMethodDeclaration.sourceStart(), abstractMethodDeclaration.sourceEnd());
    }

    public void enumConstantMustImplementAbstractMethod(AbstractMethodDeclaration abstractMethodDeclaration, FieldDeclaration fieldDeclaration) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.EnumConstantMustImplementAbstractMethod, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(fieldDeclaration.f617name)}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(fieldDeclaration.f617name)}, fieldDeclaration.sourceStart(), fieldDeclaration.sourceEnd());
    }

    public void enumConstantsCannotBeSurroundedByParenthesis(Expression expression) {
        handle(IProblem.EnumConstantsCannotBeSurroundedByParenthesis, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void enumStaticFieldUsedDuringInitialization(FieldBinding fieldBinding, ASTNode aSTNode) {
        handle(IProblem.EnumStaticFieldInInInitializerContext, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void enumSwitchCannotTargetField(Reference reference, FieldBinding fieldBinding) {
        handle(IProblem.EnumSwitchCannotTargetField, new String[]{String.valueOf(fieldBinding.declaringClass.readableName()), String.valueOf(fieldBinding.f628name)}, new String[]{String.valueOf(fieldBinding.declaringClass.shortReadableName()), String.valueOf(fieldBinding.f628name)}, nodeSourceStart(fieldBinding, reference), nodeSourceEnd(fieldBinding, reference));
    }

    public void errorNoMethodFor(MessageSend messageSend, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(typeBinding.isArrayType() ? IProblem.NoMessageSendOnArrayType : IProblem.NoMessageSendOnBaseType, new String[]{new String(typeBinding.readableName()), new String(messageSend.selector), stringBuffer.toString()}, new String[]{new String(typeBinding.shortReadableName()), new String(messageSend.selector), stringBuffer2.toString()}, messageSend.sourceStart, messageSend.sourceEnd);
    }

    public void errorNoMethodFor(Expression expression, TypeBinding typeBinding, char[] cArr, TypeBinding[] typeBindingArr) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(typeBinding.isArrayType() ? IProblem.NoMessageSendOnArrayType : IProblem.NoMessageSendOnBaseType, new String[]{new String(typeBinding.readableName()), new String(cArr), stringBuffer.toString()}, new String[]{new String(typeBinding.shortReadableName()), new String(cArr), stringBuffer2.toString()}, expression.sourceStart, expression.sourceEnd);
    }

    public void errorThisSuperInStatic(ASTNode aSTNode) {
        String[] strArr = new String[1];
        strArr[0] = aSTNode.isSuper() ? PsiKeyword.SUPER : "this";
        handle(IProblem.ThisInStaticContext, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void errorNoSuperInInterface(ASTNode aSTNode) {
        handle(IProblem.NoSuperInInterfaceContext, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void expressionShouldBeAVariable(Expression expression) {
        handle(IProblem.ExpressionShouldBeAVariable, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void fakeReachable(ASTNode aSTNode) {
        int i = aSTNode.sourceStart;
        int i2 = aSTNode.sourceEnd;
        if (aSTNode instanceof LocalDeclaration) {
            LocalDeclaration localDeclaration = (LocalDeclaration) aSTNode;
            i = localDeclaration.declarationSourceStart;
            i2 = localDeclaration.declarationSourceEnd;
        }
        handle(IProblem.DeadCode, NoArgument, NoArgument, i, i2);
    }

    public void fieldHiding(FieldDeclaration fieldDeclaration, Binding binding) {
        ReferenceBinding referenceBinding;
        ReferenceBinding referenceBinding2;
        FieldBinding fieldBinding = fieldDeclaration.binding;
        if (CharOperation.equals(TypeConstants.SERIALVERSIONUID, fieldBinding.f628name) && fieldBinding.isStatic() && fieldBinding.isPrivate() && fieldBinding.isFinal() && TypeBinding.equalsEquals(TypeBinding.LONG, fieldBinding.type) && (referenceBinding2 = fieldBinding.declaringClass) != null && referenceBinding2.findSuperTypeOriginatingFrom(37, false) != null) {
            return;
        }
        if (CharOperation.equals(TypeConstants.SERIALPERSISTENTFIELDS, fieldBinding.f628name) && fieldBinding.isStatic() && fieldBinding.isPrivate() && fieldBinding.isFinal() && fieldBinding.type.dimensions() == 1 && CharOperation.equals(TypeConstants.CharArray_JAVA_IO_OBJECTSTREAMFIELD, fieldBinding.type.leafComponentType().readableName()) && (referenceBinding = fieldBinding.declaringClass) != null && referenceBinding.findSuperTypeOriginatingFrom(37, false) != null) {
            return;
        }
        boolean z = binding instanceof LocalVariableBinding;
        int iComputeSeverity = computeSeverity(z ? IProblem.FieldHidingLocalVariable : IProblem.FieldHidingField);
        if (iComputeSeverity == 256) {
            return;
        }
        if (z) {
            handle(IProblem.FieldHidingLocalVariable, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(binding, fieldDeclaration), nodeSourceEnd(binding, fieldDeclaration));
        } else if (binding instanceof FieldBinding) {
            FieldBinding fieldBinding2 = (FieldBinding) binding;
            handle(IProblem.FieldHidingField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name), new String(fieldBinding2.declaringClass.readableName())}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name), new String(fieldBinding2.declaringClass.shortReadableName())}, iComputeSeverity, nodeSourceStart(fieldBinding2, fieldDeclaration), nodeSourceEnd(fieldBinding2, fieldDeclaration));
        }
    }

    public void fieldsOrThisBeforeConstructorInvocation(ASTNode aSTNode) {
        handle(IProblem.ThisSuperDuringConstructorInvocation, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode instanceof LambdaExpression ? ((LambdaExpression) aSTNode).diagnosticsSourceEnd() : aSTNode.sourceEnd);
    }

    public void finallyMustCompleteNormally(Block block) {
        handle(IProblem.FinallyMustCompleteNormally, NoArgument, NoArgument, block.sourceStart, block.sourceEnd);
    }

    public void finalMethodCannotBeOverridden(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.FinalMethodCannotBeOverridden, new String[]{new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void finalVariableBound(TypeVariableBinding typeVariableBinding, TypeReference typeReference) {
        int iComputeSeverity;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.FinalBoundForTypeVariable)) != 256) {
            handle(IProblem.FinalBoundForTypeVariable, new String[]{new String(typeVariableBinding.sourceName()), new String(typeReference.resolvedType.readableName())}, new String[]{new String(typeVariableBinding.sourceName()), new String(typeReference.resolvedType.shortReadableName())}, iComputeSeverity, typeReference.sourceStart, typeReference.sourceEnd);
        }
    }

    public void forbiddenReference(FieldBinding fieldBinding, ASTNode aSTNode, byte b, String str, int i) {
        int iComputeSeverity = computeSeverity(i);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(i, new String[]{new String(fieldBinding.readableName())}, getElaborationId(IProblem.ForbiddenReference, (byte) (b | 4)), new String[]{str, new String(fieldBinding.shortReadableName()), new String(fieldBinding.declaringClass.shortReadableName())}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void forbiddenReference(MethodBinding methodBinding, ASTNode aSTNode, byte b, String str, int i) {
        int iComputeSeverity = computeSeverity(i);
        if (iComputeSeverity == 256) {
            return;
        }
        if (methodBinding.isConstructor()) {
            handle(i, new String[]{new String(methodBinding.readableName())}, getElaborationId(IProblem.ForbiddenReference, (byte) (b | 8)), new String[]{str, new String(methodBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(i, new String[]{new String(methodBinding.readableName())}, getElaborationId(IProblem.ForbiddenReference, (byte) (b | 12)), new String[]{str, new String(methodBinding.shortReadableName()), new String(methodBinding.declaringClass.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void forbiddenReference(TypeBinding typeBinding, ASTNode aSTNode, byte b, String str, int i) {
        int iComputeSeverity;
        if (aSTNode == null || (iComputeSeverity = computeSeverity(i)) == 256) {
            return;
        }
        handle(i, new String[]{new String(typeBinding.readableName())}, getElaborationId(IProblem.ForbiddenReference, b), new String[]{str, new String(typeBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void forwardReference(Reference reference, int i, FieldBinding fieldBinding) {
        handle(IProblem.ReferenceToForwardField, NoArgument, NoArgument, nodeSourceStart(fieldBinding, reference, i), nodeSourceEnd(fieldBinding, reference, i));
    }

    public void forwardTypeVariableReference(ASTNode aSTNode, TypeVariableBinding typeVariableBinding) {
        handle(IProblem.ReferenceToForwardTypeVariable, new String[]{new String(typeVariableBinding.readableName())}, new String[]{new String(typeVariableBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void genericTypeCannotExtendThrowable(TypeDeclaration typeDeclaration) {
        TypeReference typeReference = typeDeclaration.binding.isAnonymousType() ? typeDeclaration.allocation.type : typeDeclaration.superclass;
        handle(IProblem.GenericTypeCannotExtendThrowable, new String[]{new String(typeDeclaration.binding.readableName())}, new String[]{new String(typeDeclaration.binding.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    private void handle(int i, String[] strArr, int i2, String[] strArr2, int i3, int i4, int i5) {
        ReferenceContext referenceContext = this.referenceContext;
        handle(i, strArr, i2, strArr2, i3, i4, i5, referenceContext, referenceContext == null ? null : referenceContext.compilationResult());
        this.referenceContext = null;
    }

    private void handle(int i, String[] strArr, String[] strArr2, int i2, int i3) {
        ReferenceContext referenceContext = this.referenceContext;
        handle(i, strArr, strArr2, i2, i3, referenceContext, referenceContext == null ? null : referenceContext.compilationResult());
        this.referenceContext = null;
    }

    private void handle(int i, String[] strArr, String[] strArr2, int i2, int i3, CompilationResult compilationResult) {
        handle(i, strArr, strArr2, i2, i3, this.referenceContext, compilationResult);
        this.referenceContext = null;
    }

    private void handle(int i, String[] strArr, String[] strArr2, int i2, int i3, int i4) {
        handle(i, strArr, 0, strArr2, i2, i3, i4);
    }

    protected void handleUntagged(int i, String[] strArr, String[] strArr2, int i2, int i3) {
        boolean z = this.suppressTagging;
        this.suppressTagging = true;
        try {
            handle(i, strArr, strArr2, i2, i3);
        } finally {
            this.suppressTagging = z;
        }
    }

    public void hiddenCatchBlock(ReferenceBinding referenceBinding, ASTNode aSTNode) {
        handle(IProblem.MaskedCatch, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void hierarchyCircularity(SourceTypeBinding sourceTypeBinding, ReferenceBinding referenceBinding, TypeReference typeReference) {
        int iSourceEnd;
        int iSourceStart;
        if (typeReference == null) {
            iSourceStart = sourceTypeBinding.sourceStart();
            iSourceEnd = sourceTypeBinding.sourceEnd();
        } else {
            int i = typeReference.sourceStart;
            iSourceEnd = typeReference.sourceEnd;
            iSourceStart = i;
        }
        if (TypeBinding.equalsEquals(sourceTypeBinding, referenceBinding)) {
            handle(IProblem.HierarchyCircularitySelfReference, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, iSourceStart, iSourceEnd);
        } else {
            handle(IProblem.HierarchyCircularity, new String[]{new String(sourceTypeBinding.readableName()), new String(referenceBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName()), new String(referenceBinding.shortReadableName())}, iSourceStart, iSourceEnd);
        }
    }

    public void hierarchyCircularity(TypeVariableBinding typeVariableBinding, ReferenceBinding referenceBinding, TypeReference typeReference) {
        int i = typeReference.sourceStart;
        int i2 = typeReference.sourceEnd;
        if (TypeBinding.equalsEquals(typeVariableBinding, referenceBinding)) {
            handle(IProblem.HierarchyCircularitySelfReference, new String[]{new String(typeVariableBinding.readableName())}, new String[]{new String(typeVariableBinding.shortReadableName())}, i, i2);
        } else {
            handle(IProblem.HierarchyCircularity, new String[]{new String(typeVariableBinding.readableName()), new String(referenceBinding.readableName())}, new String[]{new String(typeVariableBinding.shortReadableName()), new String(referenceBinding.shortReadableName())}, i, i2);
        }
    }

    public void hierarchyHasProblems(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.HierarchyHasProblems, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalAbstractModifierCombinationForMethod(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(referenceBinding.sourceName()), new String(abstractMethodDeclaration.selector)};
        handle(IProblem.IllegalAbstractModifierCombinationForMethod, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalAbstractModifierCombinationForMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(abstractMethodDeclaration.selector)};
        handle(IProblem.IllegalStrictfpForAbstractInterfaceMethod, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalAccessFromTypeVariable(TypeVariableBinding typeVariableBinding, ASTNode aSTNode) {
        if ((aSTNode.bits & 32768) != 0) {
            javadocInvalidReference(aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            String[] strArr = {new String(typeVariableBinding.sourceName)};
            handle(IProblem.IllegalAccessFromTypeVariable, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void illegalClassLiteralForTypeVariable(TypeVariableBinding typeVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(typeVariableBinding.sourceName)};
        handle(IProblem.IllegalClassLiteralForTypeVariable, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalExtendedDimensions(AnnotationMethodDeclaration annotationMethodDeclaration) {
        handle(IProblem.IllegalExtendedDimensions, NoArgument, NoArgument, annotationMethodDeclaration.sourceStart, annotationMethodDeclaration.sourceEnd);
    }

    public void illegalExtendedDimensions(Argument argument) {
        handle(IProblem.IllegalExtendedDimensionsForVarArgs, NoArgument, NoArgument, argument.sourceStart, argument.sourceEnd);
    }

    public void illegalGenericArray(TypeBinding typeBinding, ASTNode aSTNode) {
        handle(IProblem.IllegalGenericArray, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalInstanceOfGenericType(TypeBinding typeBinding, ASTNode aSTNode) {
        TypeBinding typeBindingErasure = typeBinding.leafComponentType().erasure();
        StringBuffer stringBuffer = new StringBuffer(10);
        if (typeBindingErasure instanceof ReferenceBinding) {
            stringBuffer.append(((ReferenceBinding) typeBindingErasure).qualifiedSourceName());
        } else {
            stringBuffer.append(typeBindingErasure.sourceName());
        }
        int length = typeBindingErasure.typeVariables().length;
        if (length > 0) {
            stringBuffer.append('<');
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(MangleConstant.Q_MARK);
            }
            stringBuffer.append('>');
        }
        int iDimensions = typeBinding.dimensions();
        for (int i2 = 0; i2 < iDimensions; i2++) {
            stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        String string = stringBuffer.toString();
        if (typeBinding.leafComponentType().isTypeVariable()) {
            handle(IProblem.IllegalInstanceofTypeParameter, new String[]{new String(typeBinding.readableName()), string}, new String[]{new String(typeBinding.shortReadableName()), string}, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.IllegalInstanceofParameterizedType, new String[]{new String(typeBinding.readableName()), string}, new String[]{new String(typeBinding.shortReadableName()), string}, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x002c  */
    /* JADX WARN: Code duplicated, block: B:19:? A[RETURN, SYNTHETIC] */
    public void illegalLocalTypeDeclaration(TypeDeclaration typeDeclaration) {
        int i;
        int i2;
        if (isRecoveredName(typeDeclaration.f619name)) {
            return;
        }
        if ((typeDeclaration.modifiers & 16384) != 0) {
            i2 = IProblem.CannotDefineEnumInLocalType;
        } else {
            if ((typeDeclaration.modifiers & 8192) == 0) {
                if ((typeDeclaration.modifiers & 512) != 0) {
                    i2 = IProblem.CannotDefineInterfaceInLocalType;
                } else {
                    i = 0;
                }
                if (i != 0) {
                    String[] strArr = {new String(typeDeclaration.f619name)};
                    handle(i, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
                }
            }
            i2 = IProblem.CannotDefineAnnotationInLocalType;
        }
        i = i2;
        if (i != 0) {
            String[] strArr2 = {new String(typeDeclaration.f619name)};
            handle(i, strArr2, strArr2, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
        }
    }

    public void illegalModifierCombinationFinalAbstractForClass(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierCombinationFinalAbstractForClass, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierCombinationFinalVolatileForField(ReferenceBinding referenceBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.IllegalModifierCombinationFinalVolatileForField, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalModifierCombinationForInterfaceMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(abstractMethodDeclaration.selector)};
        handle(IProblem.IllegalModifierCombinationForInterfaceMethod, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalModifierForAnnotationField(FieldDeclaration fieldDeclaration) {
        String str = new String(fieldDeclaration.f617name);
        handle(IProblem.IllegalModifierForAnnotationField, new String[]{new String(fieldDeclaration.binding.declaringClass.readableName()), str}, new String[]{new String(fieldDeclaration.binding.declaringClass.shortReadableName()), str}, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalModifierForAnnotationMember(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.IllegalModifierForAnnotationMethod, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.readableName()), new String(abstractMethodDeclaration.selector)}, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.shortReadableName()), new String(abstractMethodDeclaration.selector)}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalModifierForAnnotationMemberType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForAnnotationMemberType, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForAnnotationType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForAnnotationType, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForClass(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForClass, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForEnum(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForEnum, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForEnumConstant(ReferenceBinding referenceBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.IllegalModifierForEnumConstant, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalModifierForEnumConstructor(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.IllegalModifierForEnumConstructor, NoArgument, NoArgument, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalModifierForField(ReferenceBinding referenceBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.IllegalModifierForField, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalModifierForInterface(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForInterface, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForInterfaceField(FieldDeclaration fieldDeclaration) {
        String str = new String(fieldDeclaration.f617name);
        handle(IProblem.IllegalModifierForInterfaceField, new String[]{new String(fieldDeclaration.binding.declaringClass.readableName()), str}, new String[]{new String(fieldDeclaration.binding.declaringClass.shortReadableName()), str}, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalModifierForInterfaceMethod(AbstractMethodDeclaration abstractMethodDeclaration, boolean z) {
        handle(z ? IProblem.IllegalModifierForInterfaceMethod18 : IProblem.IllegalModifierForInterfaceMethod, new String[]{new String(abstractMethodDeclaration.selector)}, new String[]{new String(abstractMethodDeclaration.selector)}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalModifierForLocalClass(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForLocalClass, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForMemberClass(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForMemberClass, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForMemberEnum(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForMemberEnum, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForMemberInterface(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalModifierForMemberInterface, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalModifierForMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(abstractMethodDeclaration.isConstructor() ? IProblem.IllegalModifierForConstructor : IProblem.IllegalModifierForMethod, new String[]{new String(abstractMethodDeclaration.selector)}, new String[]{new String(abstractMethodDeclaration.selector)}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalModifierForVariable(LocalDeclaration localDeclaration, boolean z) {
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(z ? IProblem.IllegalModifierForArgument : IProblem.IllegalModifierForVariable, strArr, strArr, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void illegalPrimitiveOrArrayTypeForEnclosingInstance(TypeBinding typeBinding, ASTNode aSTNode) {
        handle(IProblem.IllegalPrimitiveOrArrayTypeForEnclosingInstance, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalQualifiedParameterizedTypeAllocation(TypeReference typeReference, TypeBinding typeBinding) {
        handle(IProblem.IllegalQualifiedParameterizedTypeAllocation, new String[]{new String(typeBinding.readableName()), new String(typeBinding.enclosingType().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding.enclosingType().shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void illegalStaticModifierForMemberType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalStaticModifierForMemberType, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalUsageOfQualifiedTypeReference(QualifiedTypeReference qualifiedTypeReference) {
        StringBuffer stringBuffer = new StringBuffer();
        char[][] cArr = qualifiedTypeReference.tokens;
        for (int i = 0; i < cArr.length; i++) {
            if (i > 0) {
                stringBuffer.append('.');
            }
            stringBuffer.append(cArr[i]);
        }
        String[] strArr = {String.valueOf(stringBuffer)};
        handle(IProblem.IllegalUsageOfQualifiedTypeReference, strArr, strArr, qualifiedTypeReference.sourceStart, qualifiedTypeReference.sourceEnd);
    }

    public void illegalUsageOfWildcard(TypeReference typeReference) {
        handle(IProblem.InvalidUsageOfWildcard, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void illegalVararg(Argument argument, AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {CharOperation.toString(argument.type.getTypeName()), new String(abstractMethodDeclaration.selector)};
        handle(IProblem.IllegalVararg, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void illegalVarargInLambda(Argument argument) {
        String[] strArr = {CharOperation.toString(argument.type.getTypeName())};
        handle(IProblem.IllegalVarargInLambda, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void illegalThisDeclaration(Argument argument) {
        String[] strArr = NoArgument;
        handle(IProblem.IllegalDeclarationOfThisParameter, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void illegalSourceLevelForThis(Argument argument) {
        String[] strArr = NoArgument;
        handle(IProblem.ExplicitThisParameterNotBelow18, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void disallowedThisParameter(Receiver receiver) {
        String[] strArr = NoArgument;
        handle(IProblem.DisallowedExplicitThisParameter, strArr, strArr, receiver.sourceStart, receiver.sourceEnd);
    }

    public void illegalQualifierForExplicitThis(Receiver receiver, TypeBinding typeBinding) {
        String[] strArr = {new String(typeBinding.sourceName())};
        handle(IProblem.IllegalQualifierForExplicitThis, strArr, strArr, receiver.qualifyingName == null ? receiver.sourceStart : receiver.qualifyingName.sourceStart, receiver.sourceEnd);
    }

    public void illegalQualifierForExplicitThis2(Receiver receiver) {
        handle(IProblem.IllegalQualifierForExplicitThis2, NoArgument, NoArgument, receiver.qualifyingName.sourceStart, receiver.sourceEnd);
    }

    public void illegalTypeForExplicitThis(Receiver receiver, TypeBinding typeBinding) {
        handle(IProblem.IllegalTypeForExplicitThis, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, receiver.type.sourceStart, receiver.type.sourceEnd);
    }

    public void illegalThis(Argument argument) {
        String[] strArr = NoArgument;
        handle(IProblem.ExplicitThisParameterNotInLambda, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void defaultMethodsNotBelow18(MethodDeclaration methodDeclaration) {
        handle(IProblem.DefaultMethodNotBelow18, NoArgument, NoArgument, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void interfaceSuperInvocationNotBelow18(QualifiedSuperReference qualifiedSuperReference) {
        handle(IProblem.InterfaceSuperInvocationNotBelow18, NoArgument, NoArgument, qualifiedSuperReference.sourceStart, qualifiedSuperReference.sourceEnd);
    }

    public void staticInterfaceMethodsNotBelow18(MethodDeclaration methodDeclaration) {
        handle(IProblem.StaticInterfaceMethodNotBelow18, NoArgument, NoArgument, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void referenceExpressionsNotBelow18(ReferenceExpression referenceExpression) {
        handle(referenceExpression.isMethodReference() ? IProblem.MethodReferenceNotBelow18 : IProblem.ConstructorReferenceNotBelow18, NoArgument, NoArgument, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void lambdaExpressionsNotBelow18(LambdaExpression lambdaExpression) {
        handle(IProblem.LambdaExpressionNotBelow18, NoArgument, NoArgument, lambdaExpression.sourceStart, lambdaExpression.diagnosticsSourceEnd());
    }

    public void illegalVisibilityModifierCombinationForField(ReferenceBinding referenceBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.IllegalVisibilityModifierCombinationForField, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void illegalVisibilityModifierCombinationForMemberType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalVisibilityModifierCombinationForMemberType, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalVisibilityModifierCombinationForMethod(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(referenceBinding.sourceName()), new String(abstractMethodDeclaration.selector)};
        handle(IProblem.IllegalVisibilityModifierCombinationForMethod, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void illegalVisibilityModifierForInterfaceMemberType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(IProblem.IllegalVisibilityModifierForInterfaceMemberType, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void illegalVoidExpression(ASTNode aSTNode) {
        handle(IProblem.InvalidVoidExpression, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void importProblem(ImportReference importReference, Binding binding) {
        char[][] cArr;
        String[] strArr;
        int i;
        String[] strArr2;
        String[] strArr3;
        String[] strArr4;
        int i2;
        if (binding instanceof FieldBinding) {
            FieldBinding fieldBinding = (FieldBinding) binding;
            int iProblemId = binding.problemId();
            if (iProblemId != 2) {
                if (iProblemId == 3) {
                    strArr3 = new String[]{new String(fieldBinding.readableName())};
                    strArr4 = new String[]{new String(fieldBinding.readableName())};
                    i2 = IProblem.AmbiguousField;
                } else if (iProblemId != 8) {
                    strArr = null;
                    strArr2 = null;
                    i = 33554502;
                } else {
                    strArr3 = new String[]{new String(fieldBinding.declaringClass.leafComponentType().readableName())};
                    strArr4 = new String[]{new String(fieldBinding.declaringClass.leafComponentType().shortReadableName())};
                    i2 = IProblem.NotVisibleType;
                }
                strArr = strArr3;
                i = i2;
                strArr2 = strArr4;
            } else {
                strArr = new String[]{CharOperation.toString(importReference.tokens), new String(fieldBinding.declaringClass.readableName())};
                i = 33554503;
                strArr2 = new String[]{CharOperation.toString(importReference.tokens), new String(fieldBinding.declaringClass.shortReadableName())};
            }
            handleUntagged(i, strArr, strArr2, nodeSourceStart(fieldBinding, importReference), nodeSourceEnd(fieldBinding, importReference));
            return;
        }
        if (binding.problemId() == 1) {
            if (binding instanceof ProblemReferenceBinding) {
                cArr = ((ProblemReferenceBinding) binding).compoundName;
            } else {
                cArr = importReference.tokens;
            }
            String[] strArr5 = {CharOperation.toString(cArr)};
            handleUntagged(IProblem.ImportNotFound, strArr5, strArr5, importReference.sourceStart, (int) importReference.sourcePositions[cArr.length - 1]);
            return;
        }
        if (binding.problemId() == 14) {
            char[][] cArr2 = importReference.tokens;
            String[] strArr6 = {CharOperation.toString(cArr2)};
            handleUntagged(268435847, strArr6, strArr6, importReference.sourceStart, (int) importReference.sourcePositions[cArr2.length - 1]);
            return;
        }
        invalidType(importReference, (TypeBinding) binding);
    }

    public void incompatibleExceptionInThrowsClause(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding methodBinding2, ReferenceBinding referenceBinding) {
        if (TypeBinding.equalsEquals(sourceTypeBinding, methodBinding.declaringClass)) {
            handle((!methodBinding.declaringClass.isInterface() || methodBinding2.isPublic()) ? IProblem.IncompatibleExceptionInThrowsClause : IProblem.IncompatibleExceptionInThrowsClauseForNonInheritedInterfaceMethod, new String[]{new String(referenceBinding.sourceName()), new String(CharOperation.concat(methodBinding2.declaringClass.readableName(), methodBinding2.readableName(), '.'))}, new String[]{new String(referenceBinding.sourceName()), new String(CharOperation.concat(methodBinding2.declaringClass.shortReadableName(), methodBinding2.shortReadableName(), '.'))}, methodBinding.sourceStart(), methodBinding.sourceEnd());
        } else {
            handle(IProblem.IncompatibleExceptionInInheritedMethodThrowsClause, new String[]{new String(referenceBinding.sourceName()), new String(CharOperation.concat(methodBinding.declaringClass.sourceName(), methodBinding.readableName(), '.')), new String(CharOperation.concat(methodBinding2.declaringClass.readableName(), methodBinding2.readableName(), '.'))}, new String[]{new String(referenceBinding.sourceName()), new String(CharOperation.concat(methodBinding.declaringClass.sourceName(), methodBinding.shortReadableName(), '.')), new String(CharOperation.concat(methodBinding2.declaringClass.shortReadableName(), methodBinding2.shortReadableName(), '.'))}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
        }
    }

    public void incompatibleReturnType(MethodBinding methodBinding, MethodBinding methodBinding2) {
        int i;
        int iRetrieveClosingAngleBracketPosition;
        int iSourceEnd;
        int iSourceStart;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(methodBinding2.declaringClass.readableName()).append('.').append(methodBinding2.readableName());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(methodBinding2.declaringClass.shortReadableName()).append('.').append(methodBinding2.shortReadableName());
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        int i2 = (!referenceBinding.isInterface() || methodBinding2.isPublic()) ? IProblem.IncompatibleReturnType : IProblem.IncompatibleReturnTypeForNonInheritedInterfaceMethod;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        if (abstractMethodDeclarationSourceMethod == null) {
            if (referenceBinding instanceof SourceTypeBinding) {
                SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
                iSourceStart = sourceTypeBinding.sourceStart();
                iSourceEnd = sourceTypeBinding.sourceEnd();
            } else {
                iSourceStart = 0;
                iSourceEnd = 0;
            }
        } else {
            if (abstractMethodDeclarationSourceMethod.isConstructor()) {
                i = abstractMethodDeclarationSourceMethod.sourceStart;
                iRetrieveClosingAngleBracketPosition = abstractMethodDeclarationSourceMethod.sourceEnd;
            } else {
                TypeReference typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                i = typeReference.sourceStart;
                if (typeReference instanceof ParameterizedSingleTypeReference) {
                    ParameterizedSingleTypeReference parameterizedSingleTypeReference = (ParameterizedSingleTypeReference) typeReference;
                    TypeReference[] typeReferenceArr = parameterizedSingleTypeReference.typeArguments;
                    if (typeReferenceArr[typeReferenceArr.length - 1].sourceEnd > parameterizedSingleTypeReference.sourceEnd) {
                        iRetrieveClosingAngleBracketPosition = retrieveClosingAngleBracketPosition(parameterizedSingleTypeReference.sourceEnd);
                    } else {
                        iRetrieveClosingAngleBracketPosition = typeReference.sourceEnd;
                    }
                } else if (typeReference instanceof ParameterizedQualifiedTypeReference) {
                    iRetrieveClosingAngleBracketPosition = retrieveClosingAngleBracketPosition(((ParameterizedQualifiedTypeReference) typeReference).sourceEnd);
                } else {
                    iRetrieveClosingAngleBracketPosition = typeReference.sourceEnd;
                }
            }
            iSourceEnd = iRetrieveClosingAngleBracketPosition;
            iSourceStart = i;
        }
        handle(i2, new String[]{stringBuffer.toString()}, new String[]{stringBuffer2.toString()}, iSourceStart, iSourceEnd);
    }

    public void incorrectArityForParameterizedType(ASTNode aSTNode, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        incorrectArityForParameterizedType(aSTNode, typeBinding, typeBindingArr, Integer.MAX_VALUE);
    }

    public void incorrectArityForParameterizedType(ASTNode aSTNode, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i) {
        if (aSTNode == null) {
            handle(IProblem.IncorrectArityForParameterizedType, new String[]{new String(typeBinding.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true)}, 131, 0, 0);
        } else {
            handle(IProblem.IncorrectArityForParameterizedType, new String[]{new String(typeBinding.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true)}, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, i));
        }
    }

    public void diamondNotBelow17(ASTNode aSTNode) {
        diamondNotBelow17(aSTNode, Integer.MAX_VALUE);
    }

    public void diamondNotBelow17(ASTNode aSTNode, int i) {
        if (aSTNode == null) {
            handle(IProblem.DiamondNotBelow17, NoArgument, NoArgument, 131, 0, 0);
        } else {
            handle(IProblem.DiamondNotBelow17, NoArgument, NoArgument, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, i));
        }
    }

    public void incorrectLocationForNonEmptyDimension(ArrayAllocationExpression arrayAllocationExpression, int i) {
        handle(IProblem.IllegalDimension, NoArgument, NoArgument, arrayAllocationExpression.dimensions[i].sourceStart, arrayAllocationExpression.dimensions[i].sourceEnd);
    }

    public void incorrectSwitchType(Expression expression, TypeBinding typeBinding) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
            if (typeBinding.f626id == 11) {
                handle(IProblem.SwitchOnStringsNotBelow17, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
                return;
            } else if (this.options.sourceLevel < ClassFileConstants.JDK1_5 && typeBinding.isEnum()) {
                handle(IProblem.SwitchOnEnumNotBelow15, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
                return;
            } else {
                handle(IProblem.IncorrectSwitchType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
                return;
            }
        }
        handle(IProblem.IncorrectSwitchType17, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void indirectAccessToStaticField(ASTNode aSTNode, FieldBinding fieldBinding) {
        int iComputeSeverity = computeSeverity(IProblem.IndirectAccessToStaticField);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.IndirectAccessToStaticField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void indirectAccessToStaticMethod(ASTNode aSTNode, MethodBinding methodBinding) {
        int iComputeSeverity = computeSeverity(IProblem.IndirectAccessToStaticMethod);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.IndirectAccessToStaticMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void inheritedDefaultMethodConflictsWithOtherInherited(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding methodBinding2) {
        TypeDeclaration typeDeclaration = sourceTypeBinding.scope.referenceContext;
        handle(IProblem.InheritedDefaultMethodConflictsWithOtherInherited, new String[]{String.valueOf(methodBinding.readableName()), String.valueOf(methodBinding.declaringClass.readableName()), String.valueOf(methodBinding2.declaringClass.readableName())}, new String[]{String.valueOf(methodBinding.shortReadableName()), String.valueOf(methodBinding.declaringClass.shortReadableName()), String.valueOf(methodBinding2.declaringClass.shortReadableName())}, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    private void inheritedMethodReducesVisibility(int i, int i2, MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(methodBinding.declaringClass.readableName()).append('.').append(methodBinding.readableName());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(methodBinding.declaringClass.shortReadableName()).append('.').append(methodBinding.shortReadableName());
        handle(IProblem.InheritedMethodReducesVisibility, new String[]{stringBuffer.toString(), new String(methodBindingArr[0].declaringClass.readableName())}, new String[]{stringBuffer2.toString(), new String(methodBindingArr[0].declaringClass.shortReadableName())}, i, i2);
    }

    public void inheritedMethodReducesVisibility(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        inheritedMethodReducesVisibility(sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd(), methodBinding, methodBindingArr);
    }

    public void inheritedMethodReducesVisibility(TypeParameter typeParameter, MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        inheritedMethodReducesVisibility(typeParameter.sourceStart(), typeParameter.sourceEnd(), methodBinding, methodBindingArr);
    }

    public void inheritedMethodsHaveIncompatibleReturnTypes(ASTNode aSTNode, MethodBinding[] methodBindingArr, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            i--;
            if (i >= 0) {
                stringBuffer.append(methodBindingArr[i].declaringClass.readableName()).append('.').append(methodBindingArr[i].readableName());
                stringBuffer2.append(methodBindingArr[i].declaringClass.shortReadableName()).append('.').append(methodBindingArr[i].shortReadableName());
                if (i != 0) {
                    stringBuffer.append(", ");
                    stringBuffer2.append(", ");
                }
            } else {
                handle(IProblem.InheritedIncompatibleReturnType, new String[]{stringBuffer.toString()}, new String[]{stringBuffer2.toString()}, aSTNode.sourceStart, aSTNode.sourceEnd);
                return;
            }
        }
    }

    public void inheritedMethodsHaveIncompatibleReturnTypes(SourceTypeBinding sourceTypeBinding, MethodBinding[] methodBindingArr, int i, boolean[] zArr) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        while (true) {
            i--;
            if (i >= 0) {
                if (!zArr[i]) {
                    stringBuffer.append(methodBindingArr[i].declaringClass.readableName()).append('.').append(methodBindingArr[i].readableName());
                    stringBuffer2.append(methodBindingArr[i].declaringClass.shortReadableName()).append('.').append(methodBindingArr[i].shortReadableName());
                    if (i != 0) {
                        stringBuffer.append(", ");
                        stringBuffer2.append(", ");
                    }
                }
            } else {
                handle(IProblem.InheritedIncompatibleReturnType, new String[]{stringBuffer.toString()}, new String[]{stringBuffer2.toString()}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
                return;
            }
        }
    }

    public void inheritedMethodsHaveNameClash(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.MethodNameClash, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), false), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding2.original(), false), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding2.original(), true), new String(methodBinding2.declaringClass.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void initializerMustCompleteNormally(FieldDeclaration fieldDeclaration) {
        handle(IProblem.InitializerMustCompleteNormally, NoArgument, NoArgument, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void innerTypesCannotDeclareStaticInitializers(ReferenceBinding referenceBinding, Initializer initializer) {
        handle(IProblem.CannotDefineStaticInitializerInLocalType, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, initializer.sourceStart, initializer.sourceStart);
    }

    public void interfaceCannotHaveConstructors(ConstructorDeclaration constructorDeclaration) {
        handle(IProblem.InterfaceCannotHaveConstructors, NoArgument, NoArgument, constructorDeclaration.sourceStart, constructorDeclaration.sourceEnd, constructorDeclaration, constructorDeclaration.compilationResult());
    }

    public void interfaceCannotHaveInitializers(char[] cArr, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(cArr)};
        handle(16777516, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void invalidAnnotationMemberType(MethodDeclaration methodDeclaration) {
        handle(IProblem.InvalidAnnotationMemberType, new String[]{new String(methodDeclaration.binding.returnType.readableName()), new String(methodDeclaration.selector), new String(methodDeclaration.binding.declaringClass.readableName())}, new String[]{new String(methodDeclaration.binding.returnType.shortReadableName()), new String(methodDeclaration.selector), new String(methodDeclaration.binding.declaringClass.shortReadableName())}, methodDeclaration.returnType.sourceStart, methodDeclaration.returnType.sourceEnd);
    }

    public void invalidBreak(ASTNode aSTNode) {
        handle(IProblem.InvalidBreak, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void invalidConstructor(Statement statement, MethodBinding methodBinding) {
        int i;
        MethodBinding methodBindingOriginal;
        ReferenceContext referenceContext = this.referenceContext;
        boolean z = (referenceContext instanceof ConstructorDeclaration) && ((ConstructorDeclaration) referenceContext).isDefaultConstructor();
        boolean z2 = (statement instanceof ExplicitConstructorCall) && ((ExplicitConstructorCall) statement).accessMode == 1;
        int i2 = statement.sourceStart;
        int i3 = statement.sourceEnd;
        if (statement instanceof AllocationExpression) {
            AllocationExpression allocationExpression = (AllocationExpression) statement;
            if (allocationExpression.enumConstant != null) {
                i2 = allocationExpression.enumConstant.sourceStart;
                i3 = allocationExpression.enumConstant.sourceEnd;
            }
        }
        int iProblemId = methodBinding.problemId();
        int i4 = IProblem.UndefinedConstructor;
        if (iProblemId != 1) {
            if (iProblemId != 2) {
                if (iProblemId != 3) {
                    if (iProblemId == 16) {
                        MethodBinding methodBinding2 = ((ProblemMethodBinding) methodBinding).closestMatch;
                        TypeBinding typeBindingLeafComponentType = methodBinding2.parameters[methodBinding2.parameters.length - 1].leafComponentType();
                        handle(IProblem.VarargsElementTypeNotVisibleForConstructor, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName()), new String(typeBindingLeafComponentType.readableName())}, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName()), new String(typeBindingLeafComponentType.shortReadableName())}, i2, i3);
                        return;
                    }
                    if (iProblemId != 23) {
                        if (iProblemId == 25) {
                            contradictoryNullAnnotationsInferred(((ProblemMethodBinding) methodBinding).closestMatch, statement);
                            return;
                        }
                        if (iProblemId != 27) {
                            switch (iProblemId) {
                                case 10:
                                    ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding) methodBinding;
                                    MethodBinding methodBindingOriginal2 = ((ParameterizedGenericMethodBinding) problemMethodBinding.closestMatch).original();
                                    int length = problemMethodBinding.parameters.length;
                                    int i5 = length - 2;
                                    TypeBinding typeBinding = problemMethodBinding.parameters[i5];
                                    TypeVariableBinding typeVariableBinding = (TypeVariableBinding) problemMethodBinding.parameters[length - 1];
                                    TypeBinding[] typeBindingArr = new TypeBinding[i5];
                                    System.arraycopy(problemMethodBinding.parameters, 0, typeBindingArr, 0, i5);
                                    handle(IProblem.GenericConstructorTypeArgumentMismatch, new String[]{new String(methodBindingOriginal2.declaringClass.sourceName()), typesAsString(methodBindingOriginal2, false), new String(methodBindingOriginal2.declaringClass.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.sourceName()), typesAsString(methodBindingOriginal2, true), new String(methodBindingOriginal2.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, i2, i3);
                                    return;
                                case 11:
                                    MethodBinding methodBinding3 = ((ProblemMethodBinding) methodBinding).closestMatch;
                                    if (methodBinding3.typeVariables == Binding.NO_TYPE_VARIABLES) {
                                        handle(IProblem.NonGenericConstructor, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, i2, i3);
                                        return;
                                    } else {
                                        handle(IProblem.IncorrectArityForParameterizedConstructor, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString((TypeBinding[]) methodBinding3.typeVariables, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString((TypeBinding[]) methodBinding3.typeVariables, true), typesAsString(methodBinding, true)}, i2, i3);
                                        return;
                                    }
                                case 12:
                                    MethodBinding methodBinding4 = ((ProblemMethodBinding) methodBinding).closestMatch;
                                    ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) methodBinding4;
                                    handle(IProblem.ParameterizedConstructorArgumentTypeMismatch, new String[]{new String(methodBinding4.declaringClass.sourceName()), typesAsString(methodBinding4, false), new String(methodBinding4.declaringClass.readableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding4.declaringClass.sourceName()), typesAsString(methodBinding4, true), new String(methodBinding4.declaringClass.shortReadableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, true), typesAsString(methodBinding, true)}, i2, i3);
                                    return;
                                case 13:
                                    MethodBinding methodBinding5 = ((ProblemMethodBinding) methodBinding).closestMatch;
                                    handle(IProblem.TypeArgumentsForRawGenericConstructor, new String[]{new String(methodBinding5.declaringClass.sourceName()), typesAsString(methodBinding5, false), new String(methodBinding5.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding5.declaringClass.sourceName()), typesAsString(methodBinding5, true), new String(methodBinding5.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, i2, i3);
                                    return;
                                default:
                                    needImplementation(statement);
                                    break;
                            }
                        }
                    }
                    ProblemMethodBinding problemMethodBinding2 = (ProblemMethodBinding) methodBinding;
                    MethodBinding methodBinding6 = problemMethodBinding2.closestMatch;
                    String[] strArr = new String[2];
                    strArr[0] = String.valueOf(methodBinding6.returnType.readableName());
                    strArr[1] = problemMethodBinding2.returnType != null ? String.valueOf(problemMethodBinding2.returnType.readableName()) : "<unknown>";
                    String[] strArr2 = new String[2];
                    strArr2[0] = String.valueOf(methodBinding6.returnType.shortReadableName());
                    strArr2[1] = problemMethodBinding2.returnType != null ? String.valueOf(problemMethodBinding2.returnType.shortReadableName()) : "<unknown>";
                    handle(IProblem.TypeMismatch, strArr, strArr2, statement.sourceStart, statement.sourceEnd);
                    return;
                }
                if (z) {
                    i = IProblem.AmbiguousConstructorInDefaultConstructor;
                } else {
                    i = z2 ? IProblem.AmbiguousConstructorInImplicitConstructorCall : IProblem.AmbiguousConstructor;
                }
                methodBindingOriginal = methodBinding;
            } else {
                if (z) {
                    i = IProblem.NotVisibleConstructorInDefaultConstructor;
                } else {
                    i = z2 ? IProblem.NotVisibleConstructorInImplicitConstructorCall : IProblem.NotVisibleConstructor;
                }
                ProblemMethodBinding problemMethodBinding3 = (ProblemMethodBinding) methodBinding;
                if (problemMethodBinding3.closestMatch != null) {
                    methodBindingOriginal = problemMethodBinding3.closestMatch.original();
                    i4 = i;
                }
            }
            i4 = i;
            methodBindingOriginal = methodBinding;
        } else {
            ProblemMethodBinding problemMethodBinding4 = (ProblemMethodBinding) methodBinding;
            if (problemMethodBinding4.closestMatch != null && (problemMethodBinding4.closestMatch.tagBits & 128) != 0) {
                missingTypeInConstructor(statement, problemMethodBinding4.closestMatch);
                return;
            }
            if (z) {
                i = IProblem.UndefinedConstructorInDefaultConstructor;
            } else {
                if (z2) {
                    i = IProblem.UndefinedConstructorInImplicitConstructorCall;
                }
                methodBindingOriginal = methodBinding;
            }
            i4 = i;
            methodBindingOriginal = methodBinding;
        }
        handle(i4, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBindingOriginal, true)}, i2, i3);
    }

    public void invalidContinue(ASTNode aSTNode) {
        handle(IProblem.InvalidContinue, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void invalidEnclosingType(Expression expression, TypeBinding typeBinding, ReferenceBinding referenceBinding) {
        if (referenceBinding.isAnonymousType()) {
            referenceBinding = referenceBinding.superclass();
        }
        if (referenceBinding.sourceName == null || referenceBinding.sourceName.length != 0) {
            int iProblemId = typeBinding.problemId();
            int i = IProblem.UndefinedType;
            if (iProblemId != 1) {
                if (iProblemId == 2) {
                    i = IProblem.NotVisibleType;
                } else if (iProblemId == 3) {
                    i = IProblem.AmbiguousType;
                } else if (iProblemId != 4) {
                    needImplementation(expression);
                } else {
                    i = IProblem.InternalTypeNameProvided;
                }
            }
            handle(i, new String[]{String.valueOf(new String(referenceBinding.readableName())) + "." + new String(typeBinding.readableName())}, new String[]{String.valueOf(new String(referenceBinding.shortReadableName())) + "." + new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
        }
    }

    public void invalidExplicitConstructorCall(ASTNode aSTNode) {
        handle(IProblem.InvalidExplicitConstructorCall, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void invalidExpressionAsStatement(Expression expression) {
        handle(IProblem.InvalidExpressionAsStatement, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidField(FieldReference fieldReference, TypeBinding typeBinding) {
        if (isRecoveredName(fieldReference.token)) {
            return;
        }
        FieldBinding fieldBinding = fieldReference.binding;
        int iProblemId = fieldBinding.problemId();
        int i = IProblem.UndefinedField;
        if (iProblemId != 1) {
            if (iProblemId == 2) {
                handle(IProblem.NotVisibleField, new String[]{new String(fieldReference.token), new String(fieldBinding.declaringClass.readableName())}, new String[]{new String(fieldReference.token), new String(fieldBinding.declaringClass.shortReadableName())}, nodeSourceStart(fieldBinding, fieldReference), nodeSourceEnd(fieldBinding, fieldReference));
                return;
            }
            if (iProblemId == 3) {
                i = IProblem.AmbiguousField;
            } else if (iProblemId == 5) {
                i = IProblem.InheritedFieldHidesEnclosingName;
            } else if (iProblemId == 6) {
                i = IProblem.InstanceFieldDuringConstructorInvocation;
            } else if (iProblemId == 7) {
                i = IProblem.NonStaticFieldFromStaticInvocation;
            } else if (iProblemId == 8) {
                handle(IProblem.NotVisibleType, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, fieldReference.receiver.sourceStart, fieldReference.receiver.sourceEnd);
                return;
            } else {
                if (iProblemId == 28) {
                    noSuchEnclosingInstance(fieldReference.actualReceiverType, fieldReference.receiver, false);
                    return;
                }
                needImplementation(fieldReference);
            }
        } else if ((typeBinding.tagBits & 128) != 0) {
            handle(IProblem.UndefinedType, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, fieldReference.receiver.sourceStart, fieldReference.receiver.sourceEnd);
            return;
        }
        int i2 = i;
        String[] strArr = {new String(fieldBinding.readableName())};
        handle(i2, strArr, strArr, nodeSourceStart(fieldBinding, fieldReference), nodeSourceEnd(fieldBinding, fieldReference));
    }

    public void invalidField(NameReference nameReference, FieldBinding fieldBinding) {
        int i;
        if (nameReference instanceof QualifiedNameReference) {
            if (isRecoveredName(((QualifiedNameReference) nameReference).tokens)) {
                return;
            }
        } else if (isRecoveredName(((SingleNameReference) nameReference).token)) {
            return;
        }
        switch (fieldBinding.problemId()) {
            case 1:
                ReferenceBinding referenceBinding = fieldBinding.declaringClass;
                if (referenceBinding != null && (referenceBinding.tagBits & 128) != 0) {
                    handle(IProblem.UndefinedType, new String[]{new String(fieldBinding.declaringClass.readableName())}, new String[]{new String(fieldBinding.declaringClass.shortReadableName())}, nameReference.sourceStart, nameReference.sourceEnd);
                    return;
                } else {
                    String[] strArr = {new String(fieldBinding.readableName())};
                    handle(IProblem.UndefinedField, strArr, strArr, nodeSourceStart(fieldBinding, nameReference), nodeSourceEnd(fieldBinding, nameReference));
                    return;
                }
            case 2:
                char[] cArrLastSegment = CharOperation.lastSegment(fieldBinding.readableName(), '.');
                handle(IProblem.NotVisibleField, new String[]{new String(cArrLastSegment), new String(fieldBinding.declaringClass.readableName())}, new String[]{new String(cArrLastSegment), new String(fieldBinding.declaringClass.shortReadableName())}, nodeSourceStart(fieldBinding, nameReference), nodeSourceEnd(fieldBinding, nameReference));
                return;
            case 3:
                i = IProblem.AmbiguousField;
                break;
            case 4:
            default:
                needImplementation(nameReference);
                i = 33554502;
                break;
            case 5:
                i = IProblem.InheritedFieldHidesEnclosingName;
                break;
            case 6:
                i = IProblem.InstanceFieldDuringConstructorInvocation;
                break;
            case 7:
                i = IProblem.NonStaticFieldFromStaticInvocation;
                break;
            case 8:
                handle(IProblem.NotVisibleType, new String[]{new String(fieldBinding.declaringClass.readableName())}, new String[]{new String(fieldBinding.declaringClass.shortReadableName())}, nameReference.sourceStart, nameReference.sourceEnd);
                return;
        }
        String[] strArr2 = {new String(fieldBinding.readableName())};
        handle(i, strArr2, strArr2, nameReference.sourceStart, nameReference.sourceEnd);
    }

    public void invalidField(QualifiedNameReference qualifiedNameReference, FieldBinding fieldBinding, int i, TypeBinding typeBinding) {
        int i2;
        if (isRecoveredName(qualifiedNameReference.tokens)) {
            return;
        }
        if (typeBinding.isBaseType()) {
            handle(IProblem.NoFieldOnBaseType, new String[]{new String(typeBinding.readableName()), CharOperation.toString(CharOperation.subarray(qualifiedNameReference.tokens, 0, i)), new String(qualifiedNameReference.tokens[i])}, new String[]{new String(typeBinding.sourceName()), CharOperation.toString(CharOperation.subarray(qualifiedNameReference.tokens, 0, i)), new String(qualifiedNameReference.tokens[i])}, qualifiedNameReference.sourceStart, (int) qualifiedNameReference.sourcePositions[i]);
            return;
        }
        switch (fieldBinding.problemId()) {
            case 1:
                if ((typeBinding.tagBits & 128) != 0) {
                    handle(IProblem.UndefinedType, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, qualifiedNameReference.sourceStart, (int) qualifiedNameReference.sourcePositions[i - 1]);
                    return;
                } else {
                    String[] strArr = {new String(qualifiedNameReference.tokens[i])};
                    handle(IProblem.UndefinedField, strArr, strArr, nodeSourceStart(fieldBinding, qualifiedNameReference), nodeSourceEnd(fieldBinding, qualifiedNameReference));
                    return;
                }
            case 2:
                String str = new String(qualifiedNameReference.tokens[i]);
                handle(IProblem.NotVisibleField, new String[]{str, new String(fieldBinding.declaringClass.readableName())}, new String[]{str, new String(fieldBinding.declaringClass.shortReadableName())}, nodeSourceStart(fieldBinding, qualifiedNameReference), nodeSourceEnd(fieldBinding, qualifiedNameReference));
                return;
            case 3:
                i2 = IProblem.AmbiguousField;
                break;
            case 4:
            default:
                needImplementation(qualifiedNameReference);
                i2 = 33554502;
                break;
            case 5:
                i2 = IProblem.InheritedFieldHidesEnclosingName;
                break;
            case 6:
                i2 = IProblem.InstanceFieldDuringConstructorInvocation;
                break;
            case 7:
                i2 = IProblem.NonStaticFieldFromStaticInvocation;
                break;
            case 8:
                handle(IProblem.NotVisibleType, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, qualifiedNameReference.sourceStart, (int) qualifiedNameReference.sourcePositions[i - 1]);
                return;
        }
        String[] strArr2 = {CharOperation.toString(CharOperation.subarray(qualifiedNameReference.tokens, 0, i + 1))};
        handle(i2, strArr2, strArr2, qualifiedNameReference.sourceStart, (int) qualifiedNameReference.sourcePositions[i]);
    }

    public void invalidFileNameForPackageAnnotations(Annotation annotation) {
        handle(IProblem.InvalidFileNameForPackageAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation.sourceEnd);
    }

    public void invalidMethod(MessageSend messageSend, MethodBinding methodBinding, Scope scope) {
        MethodBinding methodBindingOriginal;
        int i;
        MethodBinding methodBindingOriginal2 = methodBinding;
        if (isRecoveredName(messageSend.selector)) {
        }
        switch (methodBinding.problemId()) {
            case 1:
                if ((methodBindingOriginal2.declaringClass.tagBits & 128) != 0) {
                    handle(IProblem.UndefinedType, new String[]{new String(methodBindingOriginal2.declaringClass.readableName())}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName())}, messageSend.receiver.sourceStart, messageSend.receiver.sourceEnd);
                } else {
                    ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding) methodBindingOriginal2;
                    if (problemMethodBinding.closestMatch != null) {
                        MethodBinding methodBinding2 = problemMethodBinding.closestMatch;
                        if ((methodBinding2.tagBits & 128) != 0) {
                            missingTypeInMethod(messageSend, methodBinding2);
                        } else {
                            String strTypesAsString = typesAsString(methodBinding2, false);
                            String strTypesAsString2 = typesAsString(problemMethodBinding.parameters, false);
                            String strTypesAsString3 = typesAsString(methodBinding2, true);
                            String strTypesAsString4 = typesAsString(problemMethodBinding.parameters, true);
                            if (strTypesAsString.equals(strTypesAsString2)) {
                                strTypesAsString = typesAsString(methodBinding2, false, true);
                                strTypesAsString2 = typesAsString(problemMethodBinding.parameters, false, true);
                                strTypesAsString3 = typesAsString(methodBinding2, true, true);
                                strTypesAsString4 = typesAsString(problemMethodBinding.parameters, true, true);
                            }
                            if (strTypesAsString3.equals(strTypesAsString4)) {
                                strTypesAsString3 = strTypesAsString;
                                strTypesAsString4 = strTypesAsString2;
                            }
                            handle(IProblem.ParameterMismatch, new String[]{new String(methodBinding2.declaringClass.readableName()), new String(methodBinding2.selector), strTypesAsString, strTypesAsString2}, new String[]{new String(methodBinding2.declaringClass.shortReadableName()), new String(methodBinding2.selector), strTypesAsString3, strTypesAsString4}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                        }
                    }
                    i = 67108964;
                    methodBindingOriginal = methodBindingOriginal2;
                    handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                }
                break;
            case 2:
                ProblemMethodBinding problemMethodBinding2 = (ProblemMethodBinding) methodBindingOriginal2;
                methodBindingOriginal = problemMethodBinding2.closestMatch != null ? problemMethodBinding2.closestMatch.original() : methodBindingOriginal2;
                i = 67108965;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 3:
                i = IProblem.AmbiguousMethod;
                methodBindingOriginal = methodBindingOriginal2;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 4:
            case 9:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 28:
            default:
                needImplementation(messageSend);
                i = 67108964;
                methodBindingOriginal = methodBindingOriginal2;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 5:
                i = IProblem.InheritedMethodHidesEnclosingName;
                methodBindingOriginal = methodBindingOriginal2;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 6:
                i = IProblem.InstanceMethodDuringConstructorInvocation;
                methodBindingOriginal = methodBindingOriginal2;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 7:
                i = IProblem.StaticMethodRequested;
                methodBindingOriginal = methodBindingOriginal2;
                handle(i, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 8:
                handle(IProblem.NotVisibleType, new String[]{new String(methodBindingOriginal2.declaringClass.readableName())}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName())}, messageSend.receiver.sourceStart, messageSend.receiver.sourceEnd);
                break;
            case 10:
                ProblemMethodBinding problemMethodBinding3 = (ProblemMethodBinding) methodBindingOriginal2;
                MethodBinding methodBindingOriginal3 = ((ParameterizedGenericMethodBinding) problemMethodBinding3.closestMatch).original();
                int length = problemMethodBinding3.parameters.length;
                int i2 = length - 2;
                TypeBinding typeBinding = problemMethodBinding3.parameters[i2];
                TypeVariableBinding typeVariableBinding = (TypeVariableBinding) problemMethodBinding3.parameters[length - 1];
                TypeBinding[] typeBindingArr = new TypeBinding[i2];
                System.arraycopy(problemMethodBinding3.parameters, 0, typeBindingArr, 0, i2);
                handle(IProblem.GenericMethodTypeArgumentMismatch, new String[]{new String(methodBindingOriginal3.selector), typesAsString(methodBindingOriginal3, false), new String(methodBindingOriginal3.declaringClass.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(methodBindingOriginal3.selector), typesAsString(methodBindingOriginal3, true), new String(methodBindingOriginal3.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 11:
                MethodBinding methodBinding3 = ((ProblemMethodBinding) methodBindingOriginal2).closestMatch;
                if (methodBinding3.typeVariables == Binding.NO_TYPE_VARIABLES) {
                    handle(IProblem.NonGenericMethod, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString(methodBindingOriginal2, false)}, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString(methodBindingOriginal2, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                } else {
                    handle(IProblem.IncorrectArityForParameterizedMethod, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString((TypeBinding[]) methodBinding3.typeVariables, false), typesAsString(methodBindingOriginal2, false)}, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString((TypeBinding[]) methodBinding3.typeVariables, true), typesAsString(methodBindingOriginal2, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                }
                break;
            case 12:
                MethodBinding methodBinding4 = ((ProblemMethodBinding) methodBindingOriginal2).closestMatch;
                ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) methodBinding4;
                handle(IProblem.ParameterizedMethodArgumentTypeMismatch, new String[]{new String(methodBinding4.selector), typesAsString(methodBinding4, false), new String(methodBinding4.declaringClass.readableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, false), typesAsString(methodBindingOriginal2, false)}, new String[]{new String(methodBinding4.selector), typesAsString(methodBinding4, true), new String(methodBinding4.declaringClass.shortReadableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, true), typesAsString(methodBindingOriginal2, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 13:
                MethodBinding methodBinding5 = ((ProblemMethodBinding) methodBindingOriginal2).closestMatch;
                handle(IProblem.TypeArgumentsForRawGenericMethod, new String[]{new String(methodBinding5.selector), typesAsString(methodBinding5, false), new String(methodBinding5.declaringClass.readableName()), typesAsString(methodBindingOriginal2, false)}, new String[]{new String(methodBinding5.selector), typesAsString(methodBinding5, true), new String(methodBinding5.declaringClass.shortReadableName()), typesAsString(methodBindingOriginal2, true)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 16:
                ProblemMethodBinding problemMethodBinding4 = (ProblemMethodBinding) methodBindingOriginal2;
                if (problemMethodBinding4.closestMatch != null) {
                    methodBindingOriginal2 = problemMethodBinding4.closestMatch.original();
                }
                TypeBinding typeBindingLeafComponentType = methodBindingOriginal2.parameters[methodBindingOriginal2.parameters.length - 1].leafComponentType();
                handle(IProblem.VarargsElementTypeNotVisible, new String[]{new String(methodBindingOriginal2.selector), typesAsString(methodBindingOriginal2, false), new String(methodBindingOriginal2.declaringClass.readableName()), new String(typeBindingLeafComponentType.readableName())}, new String[]{new String(methodBindingOriginal2.selector), typesAsString(methodBindingOriginal2, true), new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(typeBindingLeafComponentType.shortReadableName())}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 20:
                handle(IProblem.NonStaticOrAlienTypeReceiver, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal2.selector)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal2.selector)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 23:
            case 27:
                ProblemMethodBinding problemMethodBinding5 = (ProblemMethodBinding) methodBindingOriginal2;
                MethodBinding methodBinding6 = problemMethodBinding5.closestMatch;
                if (problemMethodBinding5.returnType == methodBinding6.returnType) {
                    if (messageSend.expressionContext == ExpressionContext.VANILLA_CONTEXT) {
                        String strTypesAsString5 = typesAsString((TypeBinding[]) methodBinding.shallowOriginal().typeVariables, false);
                        handle(IProblem.CannotInferInvocationType, new String[]{strTypesAsString5, String.valueOf(methodBinding6.original().readableName())}, new String[]{strTypesAsString5, String.valueOf(methodBinding6.original().shortReadableName())}, messageSend.sourceStart, messageSend.sourceEnd);
                    }
                } else {
                    TypeBinding typeBindingCapture = methodBinding6.returnType.capture(scope, messageSend.sourceStart, messageSend.sourceEnd);
                    String[] strArr = new String[2];
                    strArr[0] = String.valueOf(typeBindingCapture.readableName());
                    strArr[1] = problemMethodBinding5.returnType != null ? String.valueOf(problemMethodBinding5.returnType.readableName()) : "<unknown>";
                    String[] strArr2 = new String[2];
                    strArr2[0] = String.valueOf(typeBindingCapture.shortReadableName());
                    strArr2[1] = problemMethodBinding5.returnType != null ? String.valueOf(problemMethodBinding5.returnType.shortReadableName()) : "<unknown>";
                    handle(IProblem.TypeMismatch, strArr, strArr2, messageSend.sourceStart, messageSend.sourceEnd);
                }
                break;
            case 24:
                ProblemMethodBinding problemMethodBinding6 = (ProblemMethodBinding) methodBindingOriginal2;
                if (problemMethodBinding6.closestMatch != null) {
                    methodBindingOriginal2 = problemMethodBinding6.closestMatch.original();
                }
                handle(IProblem.ApplicableMethodOverriddenByInapplicable, new String[]{new String(methodBindingOriginal2.selector), typesAsString(methodBindingOriginal2, false), new String(methodBindingOriginal2.declaringClass.readableName())}, new String[]{new String(methodBindingOriginal2.selector), typesAsString(methodBindingOriginal2, true), new String(methodBindingOriginal2.declaringClass.shortReadableName())}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
            case 25:
                contradictoryNullAnnotationsInferred(((ProblemMethodBinding) methodBindingOriginal2).closestMatch, messageSend);
                break;
            case 26:
                break;
            case 29:
                handle(IProblem.InterfaceStaticMethodInvocationNotBelow18, new String[]{new String(methodBindingOriginal2.declaringClass.readableName()), new String(methodBindingOriginal2.selector)}, new String[]{new String(methodBindingOriginal2.declaringClass.shortReadableName()), new String(methodBindingOriginal2.selector)}, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                break;
        }
    }

    public void invalidNullToSynchronize(Expression expression) {
        handle(IProblem.InvalidNullToSynchronized, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidOperator(BinaryExpression binaryExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.InvalidOperator, new String[]{binaryExpression.operatorToString(), String.valueOf(str) + ", " + str2}, new String[]{binaryExpression.operatorToString(), String.valueOf(str3) + ", " + str4}, binaryExpression.sourceStart, binaryExpression.sourceEnd);
    }

    public void invalidOperator(CompoundAssignment compoundAssignment, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.InvalidOperator, new String[]{compoundAssignment.operatorToString(), String.valueOf(str) + ", " + str2}, new String[]{compoundAssignment.operatorToString(), String.valueOf(str3) + ", " + str4}, compoundAssignment.sourceStart, compoundAssignment.sourceEnd);
    }

    public void invalidOperator(UnaryExpression unaryExpression, TypeBinding typeBinding) {
        handle(IProblem.InvalidOperator, new String[]{unaryExpression.operatorToString(), new String(typeBinding.readableName())}, new String[]{unaryExpression.operatorToString(), new String(typeBinding.shortReadableName())}, unaryExpression.sourceStart, unaryExpression.sourceEnd);
    }

    public void invalidParameterizedExceptionType(TypeBinding typeBinding, ASTNode aSTNode) {
        handle(IProblem.InvalidParameterizedExceptionType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void invalidParenthesizedExpression(ASTNode aSTNode) {
        handle(IProblem.InvalidParenthesizedExpression, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    /* JADX WARN: Code duplicated, block: B:98:0x014d  */
    public void invalidType(ASTNode aSTNode, TypeBinding typeBinding) {
        int i;
        long j;
        long j2;
        int i2;
        int length;
        List<TypeBinding> listCollectMissingTypes;
        boolean z = typeBinding instanceof ReferenceBinding;
        if (z) {
            if (isRecoveredName(((ReferenceBinding) typeBinding).compoundName)) {
                return;
            }
        } else if (typeBinding instanceof ArrayBinding) {
            TypeBinding typeBinding2 = ((ArrayBinding) typeBinding).leafComponentType;
            if ((typeBinding2 instanceof ReferenceBinding) && isRecoveredName(((ReferenceBinding) typeBinding2).compoundName)) {
                return;
            }
        }
        if (typeBinding.isParameterizedType() && (listCollectMissingTypes = typeBinding.collectMissingTypes(null)) != null) {
            ReferenceContext referenceContext = this.referenceContext;
            Iterator<TypeBinding> it = listCollectMissingTypes.iterator();
            while (it.hasNext()) {
                try {
                    invalidType(aSTNode, it.next());
                    this.referenceContext = referenceContext;
                } catch (Throwable th) {
                    this.referenceContext = referenceContext;
                    throw th;
                }
            }
            return;
        }
        int iProblemId = typeBinding.problemId();
        int i3 = IProblem.UndefinedType;
        if (iProblemId != 1) {
            if (iProblemId == 2) {
                i3 = IProblem.NotVisibleType;
            } else if (iProblemId == 3) {
                i3 = IProblem.AmbiguousType;
            } else if (iProblemId == 4) {
                i3 = IProblem.InternalTypeNameProvided;
            } else if (iProblemId == 5) {
                i3 = IProblem.InheritedTypeHidesEnclosingName;
            } else if (iProblemId == 7) {
                i3 = IProblem.NonStaticTypeFromStaticInvocation;
            } else if (iProblemId != 9) {
                needImplementation(aSTNode);
            } else {
                i3 = IProblem.IllegalTypeVariableSuperReference;
            }
        }
        int i4 = i3;
        int i5 = aSTNode.sourceEnd;
        if (aSTNode instanceof QualifiedNameReference) {
            QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) aSTNode;
            if (isRecoveredName(qualifiedNameReference.tokens)) {
                return;
            }
            if (qualifiedNameReference.indexOfFirstFieldBinding >= 1) {
                j = qualifiedNameReference.sourcePositions[qualifiedNameReference.indexOfFirstFieldBinding - 1];
                i = (int) j;
                i2 = i;
            } else {
                i2 = i5;
            }
        } else if (aSTNode instanceof ParameterizedQualifiedTypeReference) {
            ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) aSTNode;
            if (isRecoveredName(parameterizedQualifiedTypeReference.tokens)) {
                return;
            }
            if (z) {
                j = parameterizedQualifiedTypeReference.sourcePositions[((ReferenceBinding) typeBinding).compoundName.length - 1];
                i = (int) j;
                i2 = i;
            } else {
                i2 = i5;
            }
        } else {
            if (aSTNode instanceof ArrayQualifiedTypeReference) {
                ArrayQualifiedTypeReference arrayQualifiedTypeReference = (ArrayQualifiedTypeReference) aSTNode;
                if (isRecoveredName(arrayQualifiedTypeReference.tokens)) {
                    return;
                }
                TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
                if (typeBindingLeafComponentType instanceof ReferenceBinding) {
                    j2 = arrayQualifiedTypeReference.sourcePositions[((ReferenceBinding) typeBindingLeafComponentType).compoundName.length - 1];
                } else {
                    long[] jArr = arrayQualifiedTypeReference.sourcePositions;
                    j2 = jArr[jArr.length - 1];
                }
                i = (int) j2;
            } else if (aSTNode instanceof QualifiedTypeReference) {
                QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) aSTNode;
                if (isRecoveredName(qualifiedTypeReference.tokens)) {
                    return;
                }
                if (z) {
                    char[][] cArr = ((ReferenceBinding) typeBinding).compoundName;
                    if (cArr.length <= qualifiedTypeReference.sourcePositions.length) {
                        j = qualifiedTypeReference.sourcePositions[cArr.length - 1];
                        i = (int) j;
                    }
                }
                i2 = i5;
            } else {
                if (aSTNode instanceof ImportReference) {
                    ImportReference importReference = (ImportReference) aSTNode;
                    if (isRecoveredName(importReference.tokens)) {
                        return;
                    }
                    if (z) {
                        j = importReference.sourcePositions[((ReferenceBinding) typeBinding).compoundName.length - 1];
                        i = (int) j;
                    }
                } else if (aSTNode instanceof ArrayTypeReference) {
                    ArrayTypeReference arrayTypeReference = (ArrayTypeReference) aSTNode;
                    if (isRecoveredName(arrayTypeReference.token)) {
                        return;
                    } else {
                        i = arrayTypeReference.originalSourceEnd;
                    }
                }
                i2 = i5;
            }
            i2 = i;
        }
        int length2 = aSTNode.sourceStart;
        if (aSTNode instanceof SingleTypeReference) {
            SingleTypeReference singleTypeReference = (SingleTypeReference) aSTNode;
            if (singleTypeReference.annotations != null) {
                length = (i2 - singleTypeReference.token.length) + 1;
            }
            handle(i4, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, length, i2);
        }
        if (aSTNode instanceof QualifiedTypeReference) {
            QualifiedTypeReference qualifiedTypeReference2 = (QualifiedTypeReference) aSTNode;
            if (qualifiedTypeReference2.annotations != null) {
                length2 = (((int) (4294967295L & qualifiedTypeReference2.sourcePositions[0])) - qualifiedTypeReference2.tokens[0].length) + 1;
            }
        }
        length = length2;
        handle(i4, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, length, i2);
    }

    public void invalidTypeForCollection(Expression expression) {
        handle(IProblem.InvalidTypeForCollection, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidTypeForCollectionTarget14(Expression expression) {
        handle(IProblem.InvalidTypeForCollectionTarget14, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidTypeToSynchronize(Expression expression, TypeBinding typeBinding) {
        handle(IProblem.InvalidTypeToSynchronized, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidTypeVariableAsException(TypeBinding typeBinding, ASTNode aSTNode) {
        handle(IProblem.InvalidTypeVariableExceptionType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void invalidUnaryExpression(Expression expression) {
        handle(IProblem.InvalidUnaryExpression, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void invalidUsageOfAnnotation(Annotation annotation) {
        handle(IProblem.InvalidUsageOfAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation.sourceEnd);
    }

    public void invalidUsageOfAnnotationDeclarations(TypeDeclaration typeDeclaration) {
        handle(IProblem.InvalidUsageOfAnnotationDeclarations, NoArgument, NoArgument, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void invalidUsageOfEnumDeclarations(TypeDeclaration typeDeclaration) {
        handle(IProblem.InvalidUsageOfEnumDeclarations, NoArgument, NoArgument, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void invalidUsageOfForeachStatements(LocalDeclaration localDeclaration, Expression expression) {
        handle(IProblem.InvalidUsageOfForeachStatements, NoArgument, NoArgument, localDeclaration.declarationSourceStart, expression.sourceEnd);
    }

    public void invalidUsageOfStaticImports(ImportReference importReference) {
        handle(IProblem.InvalidUsageOfStaticImports, NoArgument, NoArgument, importReference.declarationSourceStart, importReference.declarationSourceEnd);
    }

    public void invalidUsageOfTypeArguments(TypeReference typeReference, TypeReference typeReference2) {
        handle(IProblem.InvalidUsageOfTypeArguments, NoArgument, NoArgument, typeReference.sourceStart, typeReference2.sourceEnd);
    }

    public void invalidUsageOfTypeParameters(TypeParameter typeParameter, TypeParameter typeParameter2) {
        handle(IProblem.InvalidUsageOfTypeParameters, NoArgument, NoArgument, typeParameter.declarationSourceStart, typeParameter2.declarationSourceEnd);
    }

    public void invalidUsageOfTypeParametersForAnnotationDeclaration(TypeDeclaration typeDeclaration) {
        TypeParameter[] typeParameterArr = typeDeclaration.typeParameters;
        handle(IProblem.InvalidUsageOfTypeParametersForAnnotationDeclaration, NoArgument, NoArgument, typeParameterArr[0].declarationSourceStart, typeParameterArr[typeParameterArr.length - 1].declarationSourceEnd);
    }

    public void invalidUsageOfTypeParametersForEnumDeclaration(TypeDeclaration typeDeclaration) {
        TypeParameter[] typeParameterArr = typeDeclaration.typeParameters;
        handle(IProblem.InvalidUsageOfTypeParametersForEnumDeclaration, NoArgument, NoArgument, typeParameterArr[0].declarationSourceStart, typeParameterArr[typeParameterArr.length - 1].declarationSourceEnd);
    }

    public void invalidUsageOfVarargs(Argument argument) {
        handle(IProblem.InvalidUsageOfVarargs, NoArgument, NoArgument, argument.type.sourceStart, argument.sourceEnd);
    }

    public void invalidUsageOfTypeAnnotations(Annotation annotation) {
        handle(IProblem.InvalidUsageOfTypeAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation.sourceEnd);
    }

    public void toleratedMisplacedTypeAnnotations(Annotation annotation, Annotation annotation2) {
        handle(IProblem.ToleratedMisplacedTypeAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation2.sourceEnd);
    }

    public void misplacedTypeAnnotations(Annotation annotation, Annotation annotation2) {
        handle(IProblem.MisplacedTypeAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation2.sourceEnd);
    }

    public void illegalUsageOfTypeAnnotations(Annotation annotation) {
        handle(IProblem.IllegalUsageOfTypeAnnotations, NoArgument, NoArgument, annotation.sourceStart, annotation.sourceEnd);
    }

    public void illegalTypeAnnotationsInStaticMemberAccess(Annotation annotation, Annotation annotation2) {
        handle(IProblem.IllegalTypeAnnotationsInStaticMemberAccess, NoArgument, NoArgument, annotation.sourceStart, annotation2.sourceEnd);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0030  */
    public void isClassPathCorrect(char[][] cArr, CompilationUnitDeclaration compilationUnitDeclaration, Object obj) {
        int i;
        int i2;
        int iSourceStart;
        int iSourceEnd;
        ReferenceContext referenceContext = this.referenceContext;
        this.referenceContext = compilationUnitDeclaration;
        String[] strArr = {CharOperation.toString(cArr)};
        if (obj == null) {
            i = 0;
            i2 = 0;
        } else {
            if (obj instanceof InvocationSite) {
                InvocationSite invocationSite = (InvocationSite) obj;
                iSourceStart = invocationSite.sourceStart();
                iSourceEnd = invocationSite.sourceEnd();
            } else if (obj instanceof ASTNode) {
                ASTNode aSTNode = (ASTNode) obj;
                iSourceStart = aSTNode.sourceStart();
                iSourceEnd = aSTNode.sourceEnd();
            } else {
                i = 0;
                i2 = 0;
            }
            i = iSourceStart;
            i2 = iSourceEnd;
        }
        try {
            handle(IProblem.IsClassPathCorrect, strArr, strArr, i, i2);
        } finally {
            this.referenceContext = referenceContext;
        }
    }

    private boolean isLiteral(int i) {
        return Scanner.isLiteral(i);
    }

    private boolean isRecoveredName(char[] cArr) {
        return cArr == RecoveryScanner.FAKE_IDENTIFIER;
    }

    private boolean isRecoveredName(char[][] cArr) {
        if (cArr == null) {
            return false;
        }
        for (char[] cArr2 : cArr) {
            if (cArr2 == RecoveryScanner.FAKE_IDENTIFIER) {
                return true;
            }
        }
        return false;
    }

    public void javadocAmbiguousMethodReference(int i, int i2, Binding binding, int i3) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocAmbiguousMethodReference);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            String[] strArr = {new String(binding.readableName())};
            handle(IProblem.JavadocAmbiguousMethodReference, strArr, strArr, iComputeSeverity, i, i2);
        }
    }

    public void javadocDeprecatedField(FieldBinding fieldBinding, ASTNode aSTNode, int i) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocUsingDeprecatedField);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            handle(IProblem.JavadocUsingDeprecatedField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
        }
    }

    public void javadocDeprecatedMethod(MethodBinding methodBinding, ASTNode aSTNode, int i) {
        boolean zIsConstructor = methodBinding.isConstructor();
        int iComputeSeverity = computeSeverity(zIsConstructor ? IProblem.JavadocUsingDeprecatedConstructor : IProblem.JavadocUsingDeprecatedMethod);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            if (zIsConstructor) {
                handle(IProblem.JavadocUsingDeprecatedConstructor, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
            } else {
                handle(IProblem.JavadocUsingDeprecatedMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
            }
        }
    }

    public void javadocDeprecatedType(TypeBinding typeBinding, ASTNode aSTNode, int i) {
        javadocDeprecatedType(typeBinding, aSTNode, i, Integer.MAX_VALUE);
    }

    public void javadocDeprecatedType(TypeBinding typeBinding, ASTNode aSTNode, int i, int i2) {
        int iComputeSeverity;
        if (aSTNode == null || (iComputeSeverity = computeSeverity(IProblem.JavadocUsingDeprecatedType)) == 256 || !javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            return;
        }
        if (typeBinding.isMemberType() && (typeBinding instanceof ReferenceBinding) && !javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, ((ReferenceBinding) typeBinding).modifiers)) {
            handle(IProblem.JavadocHiddenReference, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.JavadocUsingDeprecatedType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, i2));
        }
    }

    public void javadocDuplicatedParamTag(char[] cArr, int i, int i2, int i3) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocDuplicateParamName);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            String[] strArr = {String.valueOf(cArr)};
            handle(IProblem.JavadocDuplicateParamName, strArr, strArr, iComputeSeverity, i, i2);
        }
    }

    public void javadocDuplicatedReturnTag(int i, int i2) {
        handle(IProblem.JavadocDuplicateReturnTag, NoArgument, NoArgument, i, i2);
    }

    public void javadocDuplicatedTag(char[] cArr, int i, int i2) {
        String[] strArr = {new String(cArr)};
        handle(IProblem.JavadocDuplicateTag, strArr, strArr, i, i2);
    }

    public void javadocDuplicatedThrowsClassName(TypeReference typeReference, int i) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocDuplicateThrowsClassName);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            String[] strArr = {String.valueOf(typeReference.resolvedType.sourceName())};
            handle(IProblem.JavadocDuplicateThrowsClassName, strArr, strArr, iComputeSeverity, typeReference.sourceStart, typeReference.sourceEnd);
        }
    }

    public void javadocEmptyReturnTag(int i, int i2, int i3) {
        if (computeSeverity(IProblem.JavadocEmptyReturnTag) != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            String[] strArr = {new String(JavadocTagConstants.TAG_RETURN)};
            handle(IProblem.JavadocEmptyReturnTag, strArr, strArr, i, i2);
        }
    }

    public void javadocErrorNoMethodFor(MessageSend messageSend, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i) {
        int i2 = typeBinding.isArrayType() ? IProblem.JavadocNoMessageSendOnArrayType : IProblem.JavadocNoMessageSendOnBaseType;
        int iComputeSeverity = computeSeverity(i2);
        if (iComputeSeverity == 256) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int length = typeBindingArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (i3 != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i3].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i3].shortReadableName()));
        }
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            handle(i2, new String[]{new String(typeBinding.readableName()), new String(messageSend.selector), stringBuffer.toString()}, new String[]{new String(typeBinding.shortReadableName()), new String(messageSend.selector), stringBuffer2.toString()}, iComputeSeverity, messageSend.sourceStart, messageSend.sourceEnd);
        }
    }

    public void javadocHiddenReference(int i, int i2, Scope scope, int i3) {
        while (scope.parent.kind != 4) {
            if (!javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, scope.getDeclarationModifiers())) {
                return;
            } else {
                scope = scope.parent;
            }
        }
        String[] strArr = {this.options.getVisibilityString(this.options.reportInvalidJavadocTagsVisibility), this.options.getVisibilityString(i3)};
        handle(IProblem.JavadocHiddenReference, strArr, strArr, i, i2);
    }

    /* JADX WARN: Switch 'out' block B:18:0x0047 for B:16:0x0041 already processed. Defaulting to fallback option. */
    public void javadocInvalidConstructor(Statement statement, MethodBinding methodBinding, int i) {
        int i2;
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            int i3 = statement.sourceStart;
            int i4 = statement.sourceEnd;
            if (statement instanceof AllocationExpression) {
                AllocationExpression allocationExpression = (AllocationExpression) statement;
                if (allocationExpression.enumConstant != null) {
                    i3 = allocationExpression.enumConstant.sourceStart;
                    i4 = allocationExpression.enumConstant.sourceEnd;
                }
            }
            int i5 = i3;
            int i6 = i4;
            int iProblemId = methodBinding.problemId();
            if (iProblemId == 1) {
                i2 = -1610612244;
            } else if (iProblemId == 2) {
                i2 = IProblem.JavadocNotVisibleConstructor;
            } else if (iProblemId != 3) {
                switch (iProblemId) {
                    case 10:
                        int iComputeSeverity = computeSeverity(IProblem.JavadocGenericConstructorTypeArgumentMismatch);
                        if (iComputeSeverity == 256) {
                            return;
                        }
                        ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding) methodBinding;
                        MethodBinding methodBindingOriginal = ((ParameterizedGenericMethodBinding) problemMethodBinding.closestMatch).original();
                        int length = problemMethodBinding.parameters.length;
                        int i7 = length - 2;
                        TypeBinding typeBinding = problemMethodBinding.parameters[i7];
                        TypeVariableBinding typeVariableBinding = (TypeVariableBinding) problemMethodBinding.parameters[length - 1];
                        TypeBinding[] typeBindingArr = new TypeBinding[i7];
                        System.arraycopy(problemMethodBinding.parameters, 0, typeBindingArr, 0, i7);
                        handle(IProblem.JavadocGenericConstructorTypeArgumentMismatch, new String[]{new String(methodBindingOriginal.declaringClass.sourceName()), typesAsString(methodBindingOriginal, false), new String(methodBindingOriginal.declaringClass.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(methodBindingOriginal.declaringClass.sourceName()), typesAsString(methodBindingOriginal, true), new String(methodBindingOriginal.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, iComputeSeverity, i5, i6);
                        return;
                    case 11:
                        MethodBinding methodBinding2 = ((ProblemMethodBinding) methodBinding).closestMatch;
                        boolean z = methodBinding2.typeVariables == Binding.NO_TYPE_VARIABLES;
                        int iComputeSeverity2 = computeSeverity(z ? IProblem.JavadocNonGenericConstructor : IProblem.JavadocIncorrectArityForParameterizedConstructor);
                        if (iComputeSeverity2 == 256) {
                            return;
                        }
                        if (z) {
                            handle(IProblem.JavadocNonGenericConstructor, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity2, i5, i6);
                            return;
                        } else {
                            handle(IProblem.JavadocIncorrectArityForParameterizedConstructor, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName()), typesAsString((TypeBinding[]) methodBinding2.typeVariables, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding2.declaringClass.sourceName()), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName()), typesAsString((TypeBinding[]) methodBinding2.typeVariables, true), typesAsString(methodBinding, true)}, iComputeSeverity2, i5, i6);
                            return;
                        }
                    case 12:
                        int iComputeSeverity3 = computeSeverity(IProblem.JavadocParameterizedConstructorArgumentTypeMismatch);
                        if (iComputeSeverity3 == 256) {
                            return;
                        }
                        MethodBinding methodBinding3 = ((ProblemMethodBinding) methodBinding).closestMatch;
                        ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) methodBinding3;
                        handle(IProblem.JavadocParameterizedConstructorArgumentTypeMismatch, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding3.declaringClass.sourceName()), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, true), typesAsString(methodBinding, true)}, iComputeSeverity3, i5, i6);
                        return;
                    case 13:
                        int iComputeSeverity4 = computeSeverity(IProblem.JavadocTypeArgumentsForRawGenericConstructor);
                        if (iComputeSeverity4 == 256) {
                            return;
                        }
                        MethodBinding methodBinding4 = ((ProblemMethodBinding) methodBinding).closestMatch;
                        handle(IProblem.JavadocTypeArgumentsForRawGenericConstructor, new String[]{new String(methodBinding4.declaringClass.sourceName()), typesAsString(methodBinding4, false), new String(methodBinding4.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding4.declaringClass.sourceName()), typesAsString(methodBinding4, true), new String(methodBinding4.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity4, i5, i6);
                        return;
                    default:
                        needImplementation(statement);
                        i2 = -1610612244;
                        break;
                }
            } else {
                i2 = IProblem.JavadocAmbiguousConstructor;
            }
            int iComputeSeverity5 = computeSeverity(i2);
            if (iComputeSeverity5 == 256) {
                return;
            }
            handle(i2, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity5, statement.sourceStart, statement.sourceEnd);
        }
    }

    public void javadocInvalidField(FieldReference fieldReference, Binding binding, TypeBinding typeBinding, int i) {
        int iProblemId = binding.problemId();
        int i2 = IProblem.JavadocUndefinedField;
        if (iProblemId != 1) {
            if (iProblemId == 2) {
                i2 = IProblem.JavadocNotVisibleField;
            } else if (iProblemId != 3) {
                needImplementation(fieldReference);
            } else {
                i2 = IProblem.JavadocAmbiguousField;
            }
        }
        int i3 = i2;
        int iComputeSeverity = computeSeverity(i3);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            String[] strArr = {new String(binding.readableName())};
            handle(i3, strArr, strArr, iComputeSeverity, fieldReference.sourceStart, fieldReference.sourceEnd);
        }
    }

    public void javadocInvalidMemberTypeQualification(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocInvalidMemberTypeQualification, NoArgument, NoArgument, i, i2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:56:0x0359 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:57:0x035a  */
    public void javadocInvalidMethod(MessageSend messageSend, MethodBinding methodBinding, int i) {
        int i2;
        int iComputeSeverity;
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            int iProblemId = methodBinding.problemId();
            if (iProblemId == 1) {
                ProblemMethodBinding problemMethodBinding = (ProblemMethodBinding) methodBinding;
                if (problemMethodBinding.closestMatch != null) {
                    int iComputeSeverity2 = computeSeverity(IProblem.JavadocParameterMismatch);
                    if (iComputeSeverity2 == 256) {
                        return;
                    }
                    String strTypesAsString = typesAsString(problemMethodBinding.closestMatch, false);
                    String strTypesAsString2 = typesAsString(methodBinding, false);
                    String strTypesAsString3 = typesAsString(problemMethodBinding.closestMatch, true);
                    String strTypesAsString4 = typesAsString(methodBinding, true);
                    if (strTypesAsString3.equals(strTypesAsString4)) {
                        strTypesAsString3 = strTypesAsString;
                        strTypesAsString4 = strTypesAsString2;
                    }
                    handle(IProblem.JavadocParameterMismatch, new String[]{new String(problemMethodBinding.closestMatch.declaringClass.readableName()), new String(problemMethodBinding.closestMatch.selector), strTypesAsString, strTypesAsString2}, new String[]{new String(problemMethodBinding.closestMatch.declaringClass.shortReadableName()), new String(problemMethodBinding.closestMatch.selector), strTypesAsString3, strTypesAsString4}, iComputeSeverity2, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                    return;
                }
            } else {
                if (iProblemId == 2) {
                    i2 = IProblem.JavadocNotVisibleMethod;
                } else if (iProblemId != 3) {
                    switch (iProblemId) {
                        case 10:
                            int iComputeSeverity3 = computeSeverity(IProblem.JavadocGenericMethodTypeArgumentMismatch);
                            if (iComputeSeverity3 == 256) {
                                return;
                            }
                            ProblemMethodBinding problemMethodBinding2 = (ProblemMethodBinding) methodBinding;
                            MethodBinding methodBindingOriginal = ((ParameterizedGenericMethodBinding) problemMethodBinding2.closestMatch).original();
                            int length = problemMethodBinding2.parameters.length;
                            int i3 = length - 2;
                            TypeBinding typeBinding = problemMethodBinding2.parameters[i3];
                            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) problemMethodBinding2.parameters[length - 1];
                            TypeBinding[] typeBindingArr = new TypeBinding[i3];
                            System.arraycopy(problemMethodBinding2.parameters, 0, typeBindingArr, 0, i3);
                            handle(IProblem.JavadocGenericMethodTypeArgumentMismatch, new String[]{new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, false), new String(methodBindingOriginal.declaringClass.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(methodBindingOriginal.selector), typesAsString(methodBindingOriginal, true), new String(methodBindingOriginal.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, iComputeSeverity3, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                            return;
                        case 11:
                            MethodBinding methodBinding2 = ((ProblemMethodBinding) methodBinding).closestMatch;
                            boolean z = methodBinding2.typeVariables == Binding.NO_TYPE_VARIABLES;
                            int iComputeSeverity4 = computeSeverity(z ? IProblem.JavadocNonGenericMethod : IProblem.JavadocIncorrectArityForParameterizedMethod);
                            if (iComputeSeverity4 == 256) {
                                return;
                            }
                            if (z) {
                                handle(IProblem.JavadocNonGenericMethod, new String[]{new String(methodBinding2.selector), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding2.selector), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity4, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                                return;
                            } else {
                                handle(IProblem.JavadocIncorrectArityForParameterizedMethod, new String[]{new String(methodBinding2.selector), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName()), typesAsString((TypeBinding[]) methodBinding2.typeVariables, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding2.selector), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName()), typesAsString((TypeBinding[]) methodBinding2.typeVariables, true), typesAsString(methodBinding, true)}, iComputeSeverity4, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                                return;
                            }
                        case 12:
                            int iComputeSeverity5 = computeSeverity(IProblem.JavadocParameterizedMethodArgumentTypeMismatch);
                            if (iComputeSeverity5 == 256) {
                                return;
                            }
                            MethodBinding methodBinding3 = ((ProblemMethodBinding) methodBinding).closestMatch;
                            ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) methodBinding3;
                            handle(IProblem.JavadocParameterizedMethodArgumentTypeMismatch, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, false), new String(methodBinding3.declaringClass.readableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, false), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding3.selector), typesAsString(methodBinding3, true), new String(methodBinding3.declaringClass.shortReadableName()), typesAsString(parameterizedGenericMethodBinding.typeArguments, true), typesAsString(methodBinding, true)}, iComputeSeverity5, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                            return;
                        case 13:
                            int iComputeSeverity6 = computeSeverity(IProblem.JavadocTypeArgumentsForRawGenericMethod);
                            if (iComputeSeverity6 == 256) {
                                return;
                            }
                            MethodBinding methodBinding4 = ((ProblemMethodBinding) methodBinding).closestMatch;
                            handle(IProblem.JavadocTypeArgumentsForRawGenericMethod, new String[]{new String(methodBinding4.selector), typesAsString(methodBinding4, false), new String(methodBinding4.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding4.selector), typesAsString(methodBinding4, true), new String(methodBinding4.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity6, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
                            return;
                        default:
                            needImplementation(messageSend);
                            break;
                    }
                } else {
                    i2 = IProblem.JavadocAmbiguousMethod;
                }
                iComputeSeverity = computeSeverity(i2);
                if (iComputeSeverity == 256) {
                    return;
                }
                handle(i2, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
            }
            i2 = -1610612240;
            iComputeSeverity = computeSeverity(i2);
            if (iComputeSeverity == 256) {
                return;
            }
            handle(i2, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, (int) (messageSend.nameSourcePosition >>> 32), (int) messageSend.nameSourcePosition);
        }
    }

    public void javadocInvalidParamTagName(int i, int i2) {
        handle(IProblem.JavadocInvalidParamTagName, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidParamTypeParameter(int i, int i2) {
        handle(IProblem.JavadocInvalidParamTagTypeParameter, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidReference(int i, int i2) {
        handle(IProblem.JavadocInvalidSeeReference, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidSeeHref(int i, int i2) {
        handle(IProblem.JavadocInvalidSeeHref, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidSeeReferenceArgs(int i, int i2) {
        handle(IProblem.JavadocInvalidSeeArgs, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidSeeUrlReference(int i, int i2) {
        handle(IProblem.JavadocInvalidSeeUrlReference, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidTag(int i, int i2) {
        handle(IProblem.JavadocInvalidTag, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidThrowsClass(int i, int i2) {
        handle(IProblem.JavadocInvalidThrowsClass, NoArgument, NoArgument, i, i2);
    }

    public void javadocInvalidThrowsClassName(TypeReference typeReference, int i) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocInvalidThrowsClassName);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            String[] strArr = {String.valueOf(typeReference.resolvedType.sourceName())};
            handle(IProblem.JavadocInvalidThrowsClassName, strArr, strArr, iComputeSeverity, typeReference.sourceStart, typeReference.sourceEnd);
        }
    }

    public void javadocInvalidType(ASTNode aSTNode, TypeBinding typeBinding, int i) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i)) {
            int iProblemId = typeBinding.problemId();
            int i2 = IProblem.JavadocUndefinedType;
            if (iProblemId != 1) {
                if (iProblemId == 2) {
                    i2 = IProblem.JavadocNotVisibleType;
                } else if (iProblemId == 3) {
                    i2 = IProblem.JavadocAmbiguousType;
                } else if (iProblemId == 4) {
                    i2 = IProblem.JavadocInternalTypeNameProvided;
                } else if (iProblemId == 5) {
                    i2 = IProblem.JavadocInheritedNameHidesEnclosingTypeName;
                } else if (iProblemId != 7) {
                    needImplementation(aSTNode);
                } else {
                    i2 = IProblem.JavadocNonStaticTypeFromStaticInvocation;
                }
            }
            int i3 = i2;
            int iComputeSeverity = computeSeverity(i3);
            if (iComputeSeverity == 256) {
                return;
            }
            handle(i3, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void javadocInvalidValueReference(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocInvalidValueReference, NoArgument, NoArgument, i, i2);
        }
    }

    public void javadocMalformedSeeReference(int i, int i2) {
        handle(IProblem.JavadocMalformedSeeReference, NoArgument, NoArgument, i, i2);
    }

    public void javadocMissing(int i, int i2, int i3) {
        javadocMissing(i, i2, computeSeverity(IProblem.JavadocMissing), i3);
    }

    public void javadocMissing(int i, int i2, int i3, int i4) {
        String strJavadocVisibilityArgument;
        if (i3 == 256) {
            return;
        }
        boolean z = (805306368 & i4) != 0;
        if (this.options.getSeverity(1048576) != 256) {
            if ((!z || this.options.reportMissingJavadocCommentsOverriding) && (strJavadocVisibilityArgument = javadocVisibilityArgument(this.options.reportMissingJavadocCommentsVisibility, i4)) != null) {
                String[] strArr = {strJavadocVisibilityArgument};
                handle(IProblem.JavadocMissing, strArr, strArr, i3, i, i2);
            }
        }
    }

    public void javadocMissingHashCharacter(int i, int i2, String str) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocMissingHashCharacter);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {str};
        handle(IProblem.JavadocMissingHashCharacter, strArr, strArr, iComputeSeverity, i, i2);
    }

    public void javadocMissingIdentifier(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocMissingIdentifier, NoArgument, NoArgument, i, i2);
        }
    }

    public void javadocMissingParamName(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocMissingParamName, NoArgument, NoArgument, i, i2);
        }
    }

    public void javadocMissingParamTag(char[] cArr, int i, int i2, int i3) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocMissingParamTag);
        if (iComputeSeverity == 256) {
            return;
        }
        boolean z = (805306368 & i3) != 0;
        if (this.options.getSeverity(2097152) != 256) {
            if ((!z || this.options.reportMissingJavadocTagsOverriding) && javadocVisibility(this.options.reportMissingJavadocTagsVisibility, i3)) {
                String[] strArr = {String.valueOf(cArr)};
                handle(IProblem.JavadocMissingParamTag, strArr, strArr, iComputeSeverity, i, i2);
            }
        }
    }

    public void javadocMissingReference(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocMissingSeeReference, NoArgument, NoArgument, i, i2);
        }
    }

    public void javadocMissingReturnTag(int i, int i2, int i3) {
        boolean z = (805306368 & i3) != 0;
        if (this.options.getSeverity(2097152) != 256) {
            if ((!z || this.options.reportMissingJavadocTagsOverriding) && javadocVisibility(this.options.reportMissingJavadocTagsVisibility, i3)) {
                handle(IProblem.JavadocMissingReturnTag, NoArgument, NoArgument, i, i2);
            }
        }
    }

    public void javadocMissingTagDescription(char[] cArr, int i, int i2, int i3) {
        if (computeSeverity(IProblem.JavadocMissingTagDescription) != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            String[] strArr = {new String(cArr)};
            handle(IProblem.JavadocEmptyReturnTag, strArr, strArr, i, i2);
        }
    }

    public void javadocMissingTagDescriptionAfterReference(int i, int i2, int i3) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocMissingTagDescription);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocMissingTagDescription, NoArgument, NoArgument, iComputeSeverity, i, i2);
        }
    }

    public void javadocMissingThrowsClassName(int i, int i2, int i3) {
        if (javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            handle(IProblem.JavadocMissingThrowsClassName, NoArgument, NoArgument, i, i2);
        }
    }

    public void javadocMissingThrowsTag(TypeReference typeReference, int i) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocMissingThrowsTag);
        if (iComputeSeverity == 256) {
            return;
        }
        boolean z = (805306368 & i) != 0;
        if (this.options.getSeverity(2097152) != 256) {
            if ((!z || this.options.reportMissingJavadocTagsOverriding) && javadocVisibility(this.options.reportMissingJavadocTagsVisibility, i)) {
                String[] strArr = {String.valueOf(typeReference.resolvedType.sourceName())};
                handle(IProblem.JavadocMissingThrowsTag, strArr, strArr, iComputeSeverity, typeReference.sourceStart, typeReference.sourceEnd);
            }
        }
    }

    public void javadocUndeclaredParamTagName(char[] cArr, int i, int i2, int i3) {
        int iComputeSeverity = computeSeverity(IProblem.JavadocInvalidParamName);
        if (iComputeSeverity != 256 && javadocVisibility(this.options.reportInvalidJavadocTagsVisibility, i3)) {
            String[] strArr = {String.valueOf(cArr)};
            handle(IProblem.JavadocInvalidParamName, strArr, strArr, iComputeSeverity, i, i2);
        }
    }

    public void javadocUnexpectedTag(int i, int i2) {
        handle(IProblem.JavadocUnexpectedTag, NoArgument, NoArgument, i, i2);
    }

    public void javadocUnexpectedText(int i, int i2) {
        handle(IProblem.JavadocUnexpectedText, NoArgument, NoArgument, i, i2);
    }

    public void javadocUnterminatedInlineTag(int i, int i2) {
        handle(IProblem.JavadocUnterminatedInlineTag, NoArgument, NoArgument, i, i2);
    }

    private String javadocVisibilityArgument(int i, int i2) {
        int i3 = i2 & 7;
        if (i3 != 0) {
            if (i3 == 1) {
                return "public";
            }
            if (i3 != 2) {
                if (i3 == 4 && i != 1) {
                    return "protected";
                }
            } else if (i == 2) {
                return "private";
            }
        } else if (i == 0 || i == 2) {
            return "default";
        }
        return null;
    }

    public void localVariableHiding(LocalDeclaration localDeclaration, Binding binding, boolean z) {
        if (binding instanceof LocalVariableBinding) {
            int i = localDeclaration instanceof Argument ? IProblem.ArgumentHidingLocalVariable : IProblem.LocalVariableHidingLocalVariable;
            int iComputeSeverity = computeSeverity(i);
            if (iComputeSeverity == 256) {
                return;
            }
            String[] strArr = {new String(localDeclaration.f617name)};
            handle(i, strArr, strArr, iComputeSeverity, nodeSourceStart(binding, localDeclaration), nodeSourceEnd(binding, localDeclaration));
            return;
        }
        if (binding instanceof FieldBinding) {
            if (!z || this.options.reportSpecialParameterHidingField) {
                int i2 = localDeclaration instanceof Argument ? IProblem.ArgumentHidingField : IProblem.LocalVariableHidingField;
                int iComputeSeverity2 = computeSeverity(i2);
                if (iComputeSeverity2 == 256) {
                    return;
                }
                FieldBinding fieldBinding = (FieldBinding) binding;
                handle(i2, new String[]{new String(localDeclaration.f617name), new String(fieldBinding.declaringClass.readableName())}, new String[]{new String(localDeclaration.f617name), new String(fieldBinding.declaringClass.shortReadableName())}, iComputeSeverity2, localDeclaration.sourceStart, localDeclaration.sourceEnd);
            }
        }
    }

    public void localVariableNonNullComparedToNull(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr;
        int i = IProblem.NonNullLocalVariableComparisonYieldsFalse;
        int iComputeSeverity = computeSeverity(IProblem.NonNullLocalVariableComparisonYieldsFalse);
        if (iComputeSeverity == 256) {
            return;
        }
        if (localVariableBinding.isNonNull()) {
            char[][] cArr = this.options.nonNullAnnotationName;
            strArr = new String[]{new String(localVariableBinding.f628name), new String(cArr[cArr.length - 1])};
            i = IProblem.SpecdNonNullLocalVariableComparisonYieldsFalse;
        } else {
            strArr = new String[]{new String(localVariableBinding.f628name)};
        }
        String[] strArr2 = strArr;
        handle(i, strArr2, strArr2, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void localVariableNullComparedToNonNull(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.NullLocalVariableComparisonYieldsFalse);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.NullLocalVariableComparisonYieldsFalse, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public boolean expressionNonNullComparison(Expression expression, boolean z) {
        String[] strArr;
        int iNodeSourceEnd;
        int iNodeSourceStart;
        int i = 0;
        if (expression.resolvedType != null && (expression.resolvedType.tagBits & TagBits.AnnotationNullMASK) == 72057594037927936L) {
            String[] strArr2 = {String.valueOf(expression.resolvedType.nullAnnotatedReadableName(this.options, true))};
            handle(IProblem.RedundantNullCheckAgainstNonNullType, strArr2, strArr2, nodeSourceStart(expression), nodeSourceEnd(expression));
            return true;
        }
        for (Expression expression2 = expression; !(expression2 instanceof Assignment); expression2 = ((CastExpression) expression2).expression) {
            if (!(expression2 instanceof CastExpression)) {
                if (expression2 instanceof MessageSend) {
                    int i2 = z ? IProblem.NonNullMessageSendComparisonYieldsFalse : IProblem.RedundantNullCheckOnNonNullMessageSend;
                    strArr = new String[]{new String(((MessageSend) expression2).binding.shortReadableName())};
                    int i3 = expression.sourceStart;
                    iNodeSourceEnd = expression.sourceEnd;
                    int i4 = i2;
                    iNodeSourceStart = i3;
                    i = i4;
                } else if ((expression2 instanceof Reference) && !(expression2 instanceof ThisReference) && !(expression2 instanceof ArrayReference)) {
                    FieldBinding fieldBindingLastFieldBinding = ((Reference) expression2).lastFieldBinding();
                    if (fieldBindingLastFieldBinding == null) {
                        return false;
                    }
                    if (fieldBindingLastFieldBinding.isNonNull()) {
                        int i5 = z ? IProblem.NonNullSpecdFieldComparisonYieldsFalse : IProblem.RedundantNullCheckOnNonNullSpecdField;
                        char[][] cArr = this.options.nonNullAnnotationName;
                        i = i5;
                        strArr = new String[]{new String(fieldBindingLastFieldBinding.f628name), new String(cArr[cArr.length - 1])};
                    } else {
                        int i6 = z ? IProblem.FieldComparisonYieldsFalse : IProblem.RedundantNullCheckOnField;
                        strArr = new String[]{String.valueOf(fieldBindingLastFieldBinding.f628name)};
                        i = i6;
                    }
                    iNodeSourceStart = nodeSourceStart(fieldBindingLastFieldBinding, expression);
                    iNodeSourceEnd = nodeSourceEnd(fieldBindingLastFieldBinding, expression);
                } else {
                    if (!(expression2 instanceof AllocationExpression) && !(expression2 instanceof ArrayAllocationExpression) && !(expression2 instanceof ArrayInitializer) && !(expression2 instanceof ClassLiteralAccess) && !(expression2 instanceof ThisReference)) {
                        if ((expression2 instanceof Literal) || (expression2 instanceof ConditionalExpression)) {
                            if (expression2 instanceof NullLiteral) {
                                needImplementation(expression);
                                return false;
                            }
                            if (expression2.resolvedType != null && expression2.resolvedType.isBaseType()) {
                                return false;
                            }
                        } else if (expression2 instanceof BinaryExpression) {
                            if ((expression2.bits & 15) != 11) {
                                return false;
                            }
                        } else {
                            needImplementation(expression2);
                            return false;
                        }
                    }
                    strArr = null;
                    iNodeSourceEnd = 0;
                    iNodeSourceStart = 0;
                }
                if (i == 0) {
                    i = z ? IProblem.NonNullExpressionComparisonYieldsFalse : IProblem.RedundantNullCheckOnNonNullExpression;
                    iNodeSourceStart = expression.sourceStart;
                    iNodeSourceEnd = expression.sourceEnd;
                    strArr = NoArgument;
                }
                String[] strArr3 = strArr;
                handle(i, strArr3, strArr3, iNodeSourceStart, iNodeSourceEnd);
                return true;
            }
        }
        return false;
    }

    public void nullAnnotationUnsupportedLocation(Annotation annotation) {
        handle(IProblem.NullAnnotationUnsupportedLocation, new String[]{String.valueOf(annotation.resolvedType.readableName())}, new String[]{String.valueOf(annotation.resolvedType.shortReadableName())}, ((annotation.recipient instanceof ReferenceBinding) && ((ReferenceBinding) annotation.recipient).isAnnotationType()) ? 0 : 129, annotation.sourceStart, annotation.sourceEnd);
    }

    public void nullAnnotationUnsupportedLocation(TypeReference typeReference) {
        int iRetrieveClosingAngleBracketPosition;
        int i = typeReference.sourceEnd;
        if (typeReference instanceof ParameterizedSingleTypeReference) {
            ParameterizedSingleTypeReference parameterizedSingleTypeReference = (ParameterizedSingleTypeReference) typeReference;
            TypeReference[] typeReferenceArr = parameterizedSingleTypeReference.typeArguments;
            if (typeReferenceArr[typeReferenceArr.length - 1].sourceEnd > parameterizedSingleTypeReference.sourceEnd) {
                iRetrieveClosingAngleBracketPosition = retrieveClosingAngleBracketPosition(parameterizedSingleTypeReference.sourceEnd);
            } else {
                iRetrieveClosingAngleBracketPosition = typeReference.sourceEnd;
            }
        } else if (typeReference instanceof ParameterizedQualifiedTypeReference) {
            iRetrieveClosingAngleBracketPosition = retrieveClosingAngleBracketPosition(((ParameterizedQualifiedTypeReference) typeReference).sourceEnd);
        } else {
            iRetrieveClosingAngleBracketPosition = typeReference.sourceEnd;
        }
        handle(IProblem.NullAnnotationUnsupportedLocationAtType, NoArgument, NoArgument, typeReference.sourceStart, iRetrieveClosingAngleBracketPosition);
    }

    public void localVariableNullInstanceof(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.NullLocalVariableInstanceofYieldsFalse);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.NullLocalVariableInstanceofYieldsFalse, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void localVariableNullReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        if ((aSTNode instanceof Expression) && (((Expression) aSTNode).implicitConversion & 1024) != 0) {
            nullUnboxing(aSTNode, localVariableBinding.type);
            return;
        }
        int iComputeSeverity = computeSeverity(IProblem.NullLocalVariableReference);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.NullLocalVariableReference, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void fieldFreeTypeVariableReference(FieldBinding fieldBinding, long j) {
        char[][] cArr = this.options.nullableAnnotationName;
        String[] strArr = {new String(fieldBinding.type.readableName()), new String(cArr[cArr.length - 1])};
        handle(IProblem.UncheckedAccessOfValueOfFreeTypeVariable, strArr, strArr, (int) (j >>> 32), (int) j);
    }

    public void localVariableFreeTypeVariableReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.UncheckedAccessOfValueOfFreeTypeVariable);
        if (iComputeSeverity == 256) {
            return;
        }
        char[][] cArr = this.options.nullableAnnotationName;
        String[] strArr = {new String(localVariableBinding.type.readableName()), new String(cArr[cArr.length - 1])};
        handle(IProblem.UncheckedAccessOfValueOfFreeTypeVariable, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void methodReturnTypeFreeTypeVariableReference(MethodBinding methodBinding, ASTNode aSTNode) {
        if (computeSeverity(IProblem.UncheckedAccessOfValueOfFreeTypeVariable) == 256) {
            return;
        }
        char[][] cArr = this.options.nullableAnnotationName;
        String[] strArr = {new String(methodBinding.returnType.readableName()), new String(cArr[cArr.length - 1])};
        handle(IProblem.UncheckedAccessOfValueOfFreeTypeVariable, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void localVariablePotentialNullReference(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        if (localVariableBinding.type.isFreeTypeVariable()) {
            localVariableFreeTypeVariableReference(localVariableBinding, aSTNode);
            return;
        }
        boolean z = aSTNode instanceof Expression;
        if (z && (((Expression) aSTNode).implicitConversion & 1024) != 0) {
            potentialNullUnboxing(aSTNode, localVariableBinding.type);
            return;
        }
        if ((localVariableBinding.type.tagBits & 36028797018963968L) != 0 && z) {
            dereferencingNullableExpression((Expression) aSTNode);
            return;
        }
        int iComputeSeverity = computeSeverity(IProblem.PotentialNullLocalVariableReference);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.PotentialNullLocalVariableReference, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void potentialNullUnboxing(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.PotentialNullUnboxing, new String[]{String.valueOf(typeBinding.readableName())}, new String[]{String.valueOf(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void nullUnboxing(ASTNode aSTNode, TypeBinding typeBinding) {
        handle(IProblem.NullUnboxing, new String[]{String.valueOf(typeBinding.readableName())}, new String[]{String.valueOf(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void nullableFieldDereference(FieldBinding fieldBinding, long j) {
        char[][] cArr = this.options.nullableAnnotationName;
        String[] strArr = {new String(fieldBinding.f628name), new String(cArr[cArr.length - 1])};
        handle(IProblem.NullableFieldReference, strArr, strArr, (int) (j >>> 32), (int) j);
    }

    public void localVariableRedundantCheckOnNonNull(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr;
        int i = IProblem.RedundantNullCheckOnNonNullLocalVariable;
        int iComputeSeverity = computeSeverity(IProblem.RedundantNullCheckOnNonNullLocalVariable);
        if (iComputeSeverity == 256) {
            return;
        }
        if (localVariableBinding.isNonNull()) {
            char[][] cArr = this.options.nonNullAnnotationName;
            strArr = new String[]{new String(localVariableBinding.f628name), new String(cArr[cArr.length - 1])};
            i = IProblem.RedundantNullCheckOnSpecdNonNullLocalVariable;
        } else {
            strArr = new String[]{new String(localVariableBinding.f628name)};
        }
        String[] strArr2 = strArr;
        handle(i, strArr2, strArr2, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void localVariableRedundantCheckOnNull(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.RedundantNullCheckOnNullLocalVariable);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.RedundantNullCheckOnNullLocalVariable, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void localVariableRedundantNullAssignment(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity;
        if ((aSTNode.bits & 8) == 0 && (iComputeSeverity = computeSeverity(IProblem.RedundantLocalVariableNullAssignment)) != 256) {
            String[] strArr = {new String(localVariableBinding.f628name)};
            handle(IProblem.RedundantLocalVariableNullAssignment, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
        }
    }

    public void methodMustOverride(AbstractMethodDeclaration abstractMethodDeclaration, long j) {
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(j == ClassFileConstants.JDK1_5 ? IProblem.MethodMustOverride : IProblem.MethodMustOverrideOrImplement, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void methodNameClash(MethodBinding methodBinding, MethodBinding methodBinding2, int i) {
        handle(IProblem.MethodNameClash, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName())}, i, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void methodNameClashHidden(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.MethodNameClashHidden, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, methodBinding.parameters, false), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding2, methodBinding2.parameters, false), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, methodBinding.parameters, true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding2, methodBinding2.parameters, true), new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void methodNeedBody(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.MethodRequiresBody, NoArgument, NoArgument, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void methodNeedingNoBody(MethodDeclaration methodDeclaration) {
        handle((methodDeclaration.modifiers & 256) != 0 ? IProblem.BodyForNativeMethod : IProblem.BodyForAbstractMethod, NoArgument, NoArgument, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void methodWithConstructorName(MethodDeclaration methodDeclaration) {
        handle(IProblem.MethodButWithConstructorName, NoArgument, NoArgument, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void methodCanBeDeclaredStatic(MethodDeclaration methodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.MethodCanBeStatic);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = methodDeclaration.binding;
        handle(IProblem.MethodCanBeStatic, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void methodCanBePotentiallyDeclaredStatic(MethodDeclaration methodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.MethodCanBePotentiallyStatic);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = methodDeclaration.binding;
        handle(IProblem.MethodCanBePotentiallyStatic, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, methodDeclaration.sourceStart, methodDeclaration.sourceEnd);
    }

    public void missingDeprecatedAnnotationForField(FieldDeclaration fieldDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.FieldMissingDeprecatedAnnotation);
        if (iComputeSeverity == 256) {
            return;
        }
        FieldBinding fieldBinding = fieldDeclaration.binding;
        handle(IProblem.FieldMissingDeprecatedAnnotation, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, fieldDeclaration), nodeSourceEnd(fieldBinding, fieldDeclaration));
    }

    public void missingDeprecatedAnnotationForMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.MethodMissingDeprecatedAnnotation);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.MethodMissingDeprecatedAnnotation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, iComputeSeverity, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void missingDeprecatedAnnotationForType(TypeDeclaration typeDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.TypeMissingDeprecatedAnnotation);
        if (iComputeSeverity == 256) {
            return;
        }
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        handle(IProblem.TypeMissingDeprecatedAnnotation, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void notAFunctionalInterface(TypeDeclaration typeDeclaration) {
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        handle(IProblem.InterfaceNotFunctionalInterface, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void missingEnumConstantCase(SwitchStatement switchStatement, FieldBinding fieldBinding) {
        handle(switchStatement.defaultCase == null ? IProblem.MissingEnumConstantCase : IProblem.MissingEnumConstantCaseDespiteDefault, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, switchStatement.expression.sourceStart, switchStatement.expression.sourceEnd);
    }

    public void missingDefaultCase(SwitchStatement switchStatement, boolean z, TypeBinding typeBinding) {
        if (z) {
            handle(IProblem.MissingEnumDefaultCase, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, switchStatement.expression.sourceStart, switchStatement.expression.sourceEnd);
        } else {
            handle(IProblem.MissingDefaultCase, NoArgument, NoArgument, switchStatement.expression.sourceStart, switchStatement.expression.sourceEnd);
        }
    }

    public void missingOverrideAnnotation(AbstractMethodDeclaration abstractMethodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.MissingOverrideAnnotation);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.MissingOverrideAnnotation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, iComputeSeverity, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void missingOverrideAnnotationForInterfaceMethodImplementation(AbstractMethodDeclaration abstractMethodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.MissingOverrideAnnotationForInterfaceMethodImplementation);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        handle(IProblem.MissingOverrideAnnotationForInterfaceMethodImplementation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, iComputeSeverity, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void missingReturnType(AbstractMethodDeclaration abstractMethodDeclaration) {
        handle(IProblem.MissingReturnType, NoArgument, NoArgument, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void missingSemiColon(Expression expression) {
        handle(IProblem.MissingSemiColon, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void missingSerialVersion(TypeDeclaration typeDeclaration) {
        String[] strArr = {new String(typeDeclaration.f619name)};
        handle(IProblem.MissingSerialVersion, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void missingSynchronizedOnInheritedMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.MissingSynchronizedModifierInInheritedMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void missingTypeInConstructor(ASTNode aSTNode, MethodBinding methodBinding) {
        List<TypeBinding> listCollectMissingTypes = methodBinding.collectMissingTypes(null);
        if (listCollectMissingTypes == null) {
            System.err.println("The constructor " + methodBinding + " is wrongly tagged as containing missing types");
            return;
        }
        TypeBinding typeBinding = listCollectMissingTypes.get(0);
        int i = aSTNode.sourceStart;
        int i2 = aSTNode.sourceEnd;
        if (aSTNode instanceof QualifiedAllocationExpression) {
            QualifiedAllocationExpression qualifiedAllocationExpression = (QualifiedAllocationExpression) aSTNode;
            if (qualifiedAllocationExpression.anonymousType != null) {
                i = qualifiedAllocationExpression.anonymousType.sourceStart;
                i2 = qualifiedAllocationExpression.anonymousType.sourceEnd;
            }
        }
        handle(IProblem.MissingTypeInConstructor, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false), new String(typeBinding.readableName())}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true), new String(typeBinding.shortReadableName())}, i, i2);
    }

    public void missingTypeInLambda(LambdaExpression lambdaExpression, MethodBinding methodBinding) {
        int iSourceStart = lambdaExpression.sourceStart();
        int iDiagnosticsSourceEnd = lambdaExpression.diagnosticsSourceEnd();
        List<TypeBinding> listCollectMissingTypes = methodBinding.collectMissingTypes(null);
        if (listCollectMissingTypes == null) {
            System.err.println("The lambda expression " + methodBinding + " is wrongly tagged as containing missing types");
        } else {
            TypeBinding typeBinding = listCollectMissingTypes.get(0);
            handle(IProblem.MissingTypeInLambda, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, iSourceStart, iDiagnosticsSourceEnd);
        }
    }

    public void missingTypeInMethod(ASTNode aSTNode, MethodBinding methodBinding) {
        int i;
        int i2;
        boolean z = aSTNode instanceof MessageSend;
        if (z) {
            MessageSend messageSend = z ? (MessageSend) aSTNode : null;
            i = (int) (messageSend.nameSourcePosition >>> 32);
            i2 = (int) messageSend.nameSourcePosition;
        } else {
            i = aSTNode.sourceStart;
            i2 = aSTNode.sourceEnd;
        }
        int i3 = i2;
        int i4 = i;
        List<TypeBinding> listCollectMissingTypes = methodBinding.collectMissingTypes(null);
        if (listCollectMissingTypes == null) {
            System.err.println("The method " + methodBinding + " is wrongly tagged as containing missing types");
        } else {
            TypeBinding typeBinding = listCollectMissingTypes.get(0);
            handle(IProblem.MissingTypeInMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false), new String(typeBinding.readableName())}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true), new String(typeBinding.shortReadableName())}, i4, i3);
        }
    }

    public void missingValueForAnnotationMember(Annotation annotation, char[] cArr) {
        String str = new String(cArr);
        handle(IProblem.MissingValueForAnnotationMember, new String[]{new String(annotation.resolvedType.readableName()), str}, new String[]{new String(annotation.resolvedType.shortReadableName()), str}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void mustDefineDimensionsOrInitializer(ArrayAllocationExpression arrayAllocationExpression) {
        handle(IProblem.MustDefineEitherDimensionExpressionsOrInitializer, NoArgument, NoArgument, arrayAllocationExpression.sourceStart, arrayAllocationExpression.sourceEnd);
    }

    public void mustUseAStaticMethod(MessageSend messageSend, MethodBinding methodBinding) {
        handle(IProblem.StaticMethodRequested, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, messageSend.sourceStart, messageSend.sourceEnd);
    }

    public void nativeMethodsCannotBeStrictfp(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(referenceBinding.sourceName()), new String(abstractMethodDeclaration.selector)};
        handle(IProblem.NativeMethodsCannotBeStrictfp, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void needImplementation(ASTNode aSTNode) {
        abortDueToInternalError(Messages.abort_missingCode, aSTNode);
    }

    public void needToEmulateFieldAccess(FieldBinding fieldBinding, ASTNode aSTNode, boolean z) {
        int i = z ? IProblem.NeedToEmulateFieldReadAccess : IProblem.NeedToEmulateFieldWriteAccess;
        int iComputeSeverity = computeSeverity(i);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(i, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void needToEmulateMethodAccess(MethodBinding methodBinding, ASTNode aSTNode) {
        if (methodBinding.isConstructor()) {
            int iComputeSeverity = computeSeverity(IProblem.NeedToEmulateConstructorAccess);
            if (iComputeSeverity == 256 || methodBinding.declaringClass.isEnum()) {
                return;
            }
            handle(IProblem.NeedToEmulateConstructorAccess, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
            return;
        }
        int iComputeSeverity2 = computeSeverity(IProblem.NeedToEmulateMethodAccess);
        if (iComputeSeverity2 == 256) {
            return;
        }
        handle(IProblem.NeedToEmulateMethodAccess, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity2, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void noAdditionalBoundAfterTypeVariable(TypeReference typeReference) {
        handle(IProblem.NoAdditionalBoundAfterTypeVariable, new String[]{new String(typeReference.resolvedType.readableName())}, new String[]{new String(typeReference.resolvedType.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    private int nodeSourceEnd(ASTNode aSTNode) {
        FieldBinding fieldBindingLastFieldBinding;
        if ((aSTNode instanceof Reference) && (fieldBindingLastFieldBinding = ((Reference) aSTNode).lastFieldBinding()) != null) {
            return nodeSourceEnd(fieldBindingLastFieldBinding, aSTNode);
        }
        return aSTNode.sourceEnd;
    }

    private int nodeSourceEnd(Binding binding, ASTNode aSTNode) {
        return nodeSourceEnd(binding, aSTNode, 0);
    }

    private int nodeSourceEnd(Binding binding, ASTNode aSTNode, int i) {
        int i2;
        if (aSTNode instanceof ArrayTypeReference) {
            return ((ArrayTypeReference) aSTNode).originalSourceEnd;
        }
        if (aSTNode instanceof QualifiedNameReference) {
            QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) aSTNode;
            int i3 = 0;
            if (qualifiedNameReference.binding == binding) {
                if (i == 0) {
                    return (int) qualifiedNameReference.sourcePositions[qualifiedNameReference.indexOfFirstFieldBinding - 1];
                }
                if (i < qualifiedNameReference.sourcePositions.length) {
                    return (int) qualifiedNameReference.sourcePositions[i];
                }
                return (int) qualifiedNameReference.sourcePositions[0];
            }
            FieldBinding[] fieldBindingArr = qualifiedNameReference.otherBindings;
            if (fieldBindingArr != null) {
                int i4 = qualifiedNameReference.indexOfFirstFieldBinding;
                if (i != 0) {
                    int length = fieldBindingArr.length;
                    while (i3 < length) {
                        if (fieldBindingArr[i3] == binding && (i2 = i3 + i4) == i) {
                            return (int) qualifiedNameReference.sourcePositions[i2];
                        }
                        i3++;
                    }
                } else {
                    int length2 = fieldBindingArr.length;
                    while (i3 < length2) {
                        if (fieldBindingArr[i3] == binding) {
                            return (int) qualifiedNameReference.sourcePositions[i3 + i4];
                        }
                        i3++;
                    }
                }
            }
        } else if (aSTNode instanceof ParameterizedQualifiedTypeReference) {
            ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) aSTNode;
            if (i < parameterizedQualifiedTypeReference.sourcePositions.length) {
                return (int) parameterizedQualifiedTypeReference.sourcePositions[i];
            }
        } else {
            if (aSTNode instanceof ArrayQualifiedTypeReference) {
                ArrayQualifiedTypeReference arrayQualifiedTypeReference = (ArrayQualifiedTypeReference) aSTNode;
                int length3 = arrayQualifiedTypeReference.sourcePositions.length;
                if (i < length3) {
                    return (int) arrayQualifiedTypeReference.sourcePositions[i];
                }
                return (int) arrayQualifiedTypeReference.sourcePositions[length3 - 1];
            }
            if (aSTNode instanceof QualifiedTypeReference) {
                QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) aSTNode;
                if (i < qualifiedTypeReference.sourcePositions.length) {
                    return (int) qualifiedTypeReference.sourcePositions[i];
                }
            }
        }
        return aSTNode.sourceEnd;
    }

    private int nodeSourceStart(ASTNode aSTNode) {
        FieldBinding fieldBindingLastFieldBinding;
        if ((aSTNode instanceof Reference) && (fieldBindingLastFieldBinding = ((Reference) aSTNode).lastFieldBinding()) != null) {
            return nodeSourceStart(fieldBindingLastFieldBinding, aSTNode);
        }
        return aSTNode.sourceStart;
    }

    private int nodeSourceStart(Binding binding, ASTNode aSTNode) {
        return nodeSourceStart(binding, aSTNode, 0);
    }

    private int nodeSourceStart(Binding binding, ASTNode aSTNode, int i) {
        long j;
        long j2;
        int i2;
        if (aSTNode instanceof FieldReference) {
            j = ((FieldReference) aSTNode).nameSourcePosition >> 32;
        } else {
            int i3 = 0;
            if (aSTNode instanceof QualifiedNameReference) {
                QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) aSTNode;
                if (qualifiedNameReference.binding != binding) {
                    FieldBinding[] fieldBindingArr = qualifiedNameReference.otherBindings;
                    if (fieldBindingArr != null) {
                        int i4 = qualifiedNameReference.indexOfFirstFieldBinding;
                        if (i != 0) {
                            int length = fieldBindingArr.length;
                            while (true) {
                                if (i3 < length) {
                                    if (fieldBindingArr[i3] == binding && (i2 = i3 + i4) == i) {
                                        j2 = qualifiedNameReference.sourcePositions[i2];
                                        break;
                                    }
                                    i3++;
                                }
                            }
                        } else {
                            int length2 = fieldBindingArr.length;
                            while (true) {
                                if (i3 < length2) {
                                    if (fieldBindingArr[i3] == binding) {
                                        j2 = qualifiedNameReference.sourcePositions[i3 + i4];
                                        break;
                                    }
                                    i3++;
                                }
                            }
                        }
                    }
                    return aSTNode.sourceStart;
                }
                if (i == 0) {
                    j2 = qualifiedNameReference.sourcePositions[qualifiedNameReference.indexOfFirstFieldBinding - 1];
                } else {
                    j2 = qualifiedNameReference.sourcePositions[i];
                }
                j = j2 >> 32;
            } else {
                if (aSTNode instanceof ParameterizedQualifiedTypeReference) {
                    j = ((ParameterizedQualifiedTypeReference) aSTNode).sourcePositions[0] >>> 32;
                }
                return aSTNode.sourceStart;
            }
        }
        return (int) j;
    }

    public void noMoreAvailableSpaceForArgument(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(localVariableBinding instanceof SyntheticArgumentBinding ? IProblem.TooManySyntheticArgumentSlots : IProblem.TooManyArgumentSlots, strArr, strArr, 159, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void noMoreAvailableSpaceForConstant(TypeDeclaration typeDeclaration) {
        handle(IProblem.TooManyBytesForStringConstant, new String[]{new String(typeDeclaration.binding.readableName())}, new String[]{new String(typeDeclaration.binding.shortReadableName())}, 159, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void noMoreAvailableSpaceForLocal(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        String[] strArr = {new String(localVariableBinding.f628name)};
        handle(IProblem.TooManyLocalVariableSlots, strArr, strArr, 159, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    public void noMoreAvailableSpaceInConstantPool(TypeDeclaration typeDeclaration) {
        handle(IProblem.TooManyConstantsInConstantPool, new String[]{new String(typeDeclaration.binding.readableName())}, new String[]{new String(typeDeclaration.binding.shortReadableName())}, 159, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void nonExternalizedStringLiteral(ASTNode aSTNode) {
        handle(IProblem.NonExternalizedStringLiteral, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void nonGenericTypeCannotBeParameterized(int i, ASTNode aSTNode, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        if (aSTNode == null) {
            handle(IProblem.NonGenericType, new String[]{new String(typeBinding.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true)}, 131, 0, 0);
        } else {
            handle(IProblem.NonGenericType, new String[]{new String(typeBinding.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true)}, nodeSourceStart(null, aSTNode), nodeSourceEnd(null, aSTNode, i));
        }
    }

    public void nonStaticAccessToStaticField(ASTNode aSTNode, FieldBinding fieldBinding) {
        nonStaticAccessToStaticField(aSTNode, fieldBinding, -1);
    }

    public void nonStaticAccessToStaticField(ASTNode aSTNode, FieldBinding fieldBinding, int i) {
        int iComputeSeverity = computeSeverity(IProblem.NonStaticAccessToStaticField);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.NonStaticAccessToStaticField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode, i), nodeSourceEnd(fieldBinding, aSTNode, i));
    }

    public void nonStaticAccessToStaticMethod(ASTNode aSTNode, MethodBinding methodBinding) {
        handle(IProblem.NonStaticAccessToStaticMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void nonStaticContextForEnumMemberType(SourceTypeBinding sourceTypeBinding) {
        String[] strArr = {new String(sourceTypeBinding.sourceName())};
        handle(536870944, strArr, strArr, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0044  */
    public void noSuchEnclosingInstance(TypeBinding typeBinding, ASTNode aSTNode, boolean z) {
        int i;
        if (z) {
            i = IProblem.EnclosingInstanceInConstructorCall;
        } else if ((aSTNode instanceof ExplicitConstructorCall) && ((ExplicitConstructorCall) aSTNode).accessMode == 1) {
            i = IProblem.MissingEnclosingInstanceForConstructorCall;
        } else if (aSTNode instanceof AllocationExpression) {
            AllocationExpression allocationExpression = (AllocationExpression) aSTNode;
            if (allocationExpression.binding.declaringClass.isMemberType() || (allocationExpression.binding.declaringClass.isAnonymousType() && allocationExpression.binding.declaringClass.superclass().isMemberType())) {
                i = IProblem.MissingEnclosingInstance;
            } else {
                i = IProblem.IncorrectEnclosingInstanceReference;
            }
        } else {
            i = IProblem.IncorrectEnclosingInstanceReference;
        }
        handle(i, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode instanceof LambdaExpression ? ((LambdaExpression) aSTNode).diagnosticsSourceEnd() : aSTNode.sourceEnd);
    }

    public void notCompatibleTypesError(EqualExpression equalExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.IncompatibleTypesInEqualityOperator, new String[]{str, str2}, new String[]{str3, str4}, equalExpression.sourceStart, equalExpression.sourceEnd);
    }

    public void notCompatibleTypesError(InstanceOfExpression instanceOfExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(16777232, new String[]{str, str2}, new String[]{str3, str4}, instanceOfExpression.sourceStart, instanceOfExpression.sourceEnd);
    }

    public void notCompatibleTypesErrorInForeach(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.IncompatibleTypesInForeach, new String[]{str, str2}, new String[]{str3, str4}, expression.sourceStart, expression.sourceEnd);
    }

    public void objectCannotBeGeneric(TypeDeclaration typeDeclaration) {
        handle(IProblem.ObjectCannotBeGeneric, NoArgument, NoArgument, typeDeclaration.typeParameters[0].sourceStart, typeDeclaration.typeParameters[typeDeclaration.typeParameters.length - 1].sourceEnd);
    }

    public void objectCannotHaveSuperTypes(SourceTypeBinding sourceTypeBinding) {
        handle(IProblem.ObjectCannotHaveSuperTypes, NoArgument, NoArgument, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void objectMustBeClass(SourceTypeBinding sourceTypeBinding) {
        handle(IProblem.ObjectMustBeClass, NoArgument, NoArgument, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void operatorOnlyValidOnNumericType(CompoundAssignment compoundAssignment, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.TypeMismatch, new String[]{str, str2}, new String[]{str3, str4}, compoundAssignment.sourceStart, compoundAssignment.sourceEnd);
    }

    public void overridesDeprecatedMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.OverridingDeprecatedMethod, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.readableName(), methodBinding.readableName(), '.')), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.shortReadableName(), methodBinding.shortReadableName(), '.')), new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void overridesMethodWithoutSuperInvocation(MethodBinding methodBinding) {
        handle(IProblem.OverridingMethodWithoutSuperInvocation, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.readableName(), methodBinding.readableName(), '.'))}, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.shortReadableName(), methodBinding.shortReadableName(), '.'))}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void overridesPackageDefaultMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.OverridingNonVisibleMethod, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.readableName(), methodBinding.readableName(), '.')), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(CharOperation.concat(methodBinding.declaringClass.shortReadableName(), methodBinding.shortReadableName(), '.')), new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void packageCollidesWithType(CompilationUnitDeclaration compilationUnitDeclaration) {
        String[] strArr = {CharOperation.toString(compilationUnitDeclaration.currentPackage.tokens)};
        handle(IProblem.PackageCollidesWithType, strArr, strArr, compilationUnitDeclaration.currentPackage.sourceStart, compilationUnitDeclaration.currentPackage.sourceEnd);
    }

    public void packageIsNotExpectedPackage(CompilationUnitDeclaration compilationUnitDeclaration) {
        int i;
        boolean z = compilationUnitDeclaration.currentPackage == null;
        String[] strArr = new String[2];
        strArr[0] = CharOperation.toString(compilationUnitDeclaration.compilationResult.compilationUnit.getPackageName());
        strArr[1] = z ? "" : CharOperation.toString(compilationUnitDeclaration.currentPackage.tokens);
        if (compilationUnitDeclaration.sourceEnd <= 0) {
            i = -1;
        } else {
            i = z ? 0 : compilationUnitDeclaration.currentPackage.sourceEnd;
        }
        handle(IProblem.PackageIsNotExpectedPackage, strArr, strArr, z ? 0 : compilationUnitDeclaration.currentPackage.sourceStart, i);
    }

    public void parameterAssignment(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.ParameterAssignment);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(IProblem.ParameterAssignment, strArr, strArr, iComputeSeverity, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    private String parameterBoundAsString(TypeVariableBinding typeVariableBinding, boolean z) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (TypeBinding.equalsEquals(typeVariableBinding.firstBound, typeVariableBinding.superclass)) {
            ReferenceBinding referenceBinding = typeVariableBinding.superclass;
            stringBuffer.append(z ? referenceBinding.shortReadableName() : referenceBinding.readableName());
        }
        int length = typeVariableBinding.superInterfaces.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (i > 0 || TypeBinding.equalsEquals(typeVariableBinding.firstBound, typeVariableBinding.superclass)) {
                    stringBuffer.append(" & ");
                }
                ReferenceBinding[] referenceBindingArr = typeVariableBinding.superInterfaces;
                stringBuffer.append(z ? referenceBindingArr[i].shortReadableName() : referenceBindingArr[i].readableName());
            }
        }
        return stringBuffer.toString();
    }

    public void parameterizedMemberTypeMissingArguments(ASTNode aSTNode, TypeBinding typeBinding, int i) {
        if (aSTNode == null) {
            handle(IProblem.MissingArgumentsForParameterizedMemberType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, 131, 0, 0);
        } else {
            handle(IProblem.MissingArgumentsForParameterizedMemberType, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, i));
        }
    }

    public void parseError(int i, int i2, int i3, char[] cArr, String str, String[] strArr) {
        if (strArr.length == 0) {
            if (isKeyword(i3)) {
                String[] strArr2 = {new String(cArr)};
                handle(IProblem.ParsingErrorOnKeywordNoSuggestion, strArr2, strArr2, i, i2);
                return;
            } else {
                String[] strArr3 = {str};
                handle(IProblem.ParsingErrorNoSuggestion, strArr3, strArr3, i, i2);
                return;
            }
        }
        StringBuffer stringBuffer = new StringBuffer(20);
        int length = strArr.length;
        for (int i4 = 0; i4 < length; i4++) {
            if (i4 > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append('\"');
            stringBuffer.append(strArr[i4]);
            stringBuffer.append('\"');
        }
        if (isKeyword(i3)) {
            String[] strArr4 = {new String(cArr), stringBuffer.toString()};
            handle(IProblem.ParsingErrorOnKeyword, strArr4, strArr4, i, i2);
        } else {
            String[] strArr5 = {(isLiteral(i3) || isIdentifier(i3)) ? new String(cArr) : str, stringBuffer.toString()};
            handle(IProblem.ParsingError, strArr5, strArr5, i, i2);
        }
    }

    public void parseErrorDeleteToken(int i, int i2, int i3, char[] cArr, String str) {
        syntaxError(IProblem.ParsingErrorDeleteToken, i, i2, i3, cArr, str, null);
    }

    public void parseErrorDeleteTokens(int i, int i2) {
        handle(IProblem.ParsingErrorDeleteTokens, NoArgument, NoArgument, i, i2);
    }

    public void parseErrorInsertAfterToken(int i, int i2, int i3, char[] cArr, String str, String str2) {
        syntaxError(IProblem.ParsingErrorInsertTokenAfter, i, i2, i3, cArr, str, str2);
    }

    public void parseErrorInsertBeforeToken(int i, int i2, int i3, char[] cArr, String str, String str2) {
        syntaxError(IProblem.ParsingErrorInsertTokenBefore, i, i2, i3, cArr, str, str2);
    }

    public void parseErrorInsertToComplete(int i, int i2, String str, String str2) {
        String[] strArr = {str, str2};
        handle(IProblem.ParsingErrorInsertToComplete, strArr, strArr, i, i2);
    }

    public void parseErrorInsertToCompletePhrase(int i, int i2, String str) {
        String[] strArr = {str};
        handle(IProblem.ParsingErrorInsertToCompletePhrase, strArr, strArr, i, i2);
    }

    public void parseErrorInsertToCompleteScope(int i, int i2, String str) {
        String[] strArr = {str};
        handle(IProblem.ParsingErrorInsertToCompleteScope, strArr, strArr, i, i2);
    }

    public void parseErrorInvalidToken(int i, int i2, int i3, char[] cArr, String str, String str2) {
        syntaxError(IProblem.ParsingErrorInvalidToken, i, i2, i3, cArr, str, str2);
    }

    public void parseErrorMergeTokens(int i, int i2, String str) {
        String[] strArr = {str};
        handle(IProblem.ParsingErrorMergeTokens, strArr, strArr, i, i2);
    }

    public void parseErrorMisplacedConstruct(int i, int i2) {
        handle(IProblem.ParsingErrorMisplacedConstruct, NoArgument, NoArgument, i, i2);
    }

    public void parseErrorNoSuggestion(int i, int i2, int i3, char[] cArr, String str) {
        syntaxError(IProblem.ParsingErrorNoSuggestion, i, i2, i3, cArr, str, null);
    }

    public void parseErrorNoSuggestionForTokens(int i, int i2) {
        handle(IProblem.ParsingErrorNoSuggestionForTokens, NoArgument, NoArgument, i, i2);
    }

    public void parseErrorReplaceToken(int i, int i2, int i3, char[] cArr, String str, String str2) {
        syntaxError(IProblem.ParsingError, i, i2, i3, cArr, str, str2);
    }

    public void parseErrorReplaceTokens(int i, int i2, String str) {
        String[] strArr = {str};
        handle(IProblem.ParsingErrorReplaceTokens, strArr, strArr, i, i2);
    }

    public void parseErrorUnexpectedEnd(int i, int i2) {
        String[] strArr;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof ConstructorDeclaration) {
            strArr = new String[]{Messages.parser_endOfConstructor};
        } else if (referenceContext instanceof MethodDeclaration) {
            strArr = new String[]{Messages.parser_endOfMethod};
        } else if (referenceContext instanceof TypeDeclaration) {
            strArr = new String[]{Messages.parser_endOfInitializer};
        } else {
            strArr = new String[]{Messages.parser_endOfFile};
        }
        String[] strArr2 = strArr;
        handle(IProblem.ParsingErrorUnexpectedEOF, strArr2, strArr2, i, i2);
    }

    public void possibleAccidentalBooleanAssignment(Assignment assignment) {
        handle(IProblem.PossibleAccidentalBooleanAssignment, NoArgument, NoArgument, assignment.sourceStart, assignment.sourceEnd);
    }

    public void possibleFallThroughCase(CaseStatement caseStatement) {
        handle(IProblem.FallthroughCase, NoArgument, NoArgument, caseStatement.sourceStart, caseStatement.sourceEnd);
    }

    public void publicClassMustMatchFileName(CompilationUnitDeclaration compilationUnitDeclaration, TypeDeclaration typeDeclaration) {
        this.referenceContext = typeDeclaration;
        String[] strArr = {new String(compilationUnitDeclaration.getFileName()), new String(typeDeclaration.f619name)};
        handle(IProblem.PublicClassMustMatchFileName, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd, compilationUnitDeclaration.compilationResult);
    }

    public void rawMemberTypeCannotBeParameterized(ASTNode aSTNode, ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr) {
        if (aSTNode == null) {
            handle(IProblem.RawMemberTypeCannotBeParameterized, new String[]{new String(referenceBinding.readableName()), typesAsString(typeBindingArr, false), new String(referenceBinding.enclosingType().readableName())}, new String[]{new String(referenceBinding.shortReadableName()), typesAsString(typeBindingArr, true), new String(referenceBinding.enclosingType().shortReadableName())}, 131, 0, 0);
        } else {
            handle(IProblem.RawMemberTypeCannotBeParameterized, new String[]{new String(referenceBinding.readableName()), typesAsString(typeBindingArr, false), new String(referenceBinding.enclosingType().readableName())}, new String[]{new String(referenceBinding.shortReadableName()), typesAsString(typeBindingArr, true), new String(referenceBinding.enclosingType().shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void rawTypeReference(ASTNode aSTNode, TypeBinding typeBinding) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_5) {
            return;
        }
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        handle(IProblem.RawTypeReference, new String[]{new String(typeBindingLeafComponentType.readableName()), new String(typeBindingLeafComponentType.erasure().readableName())}, new String[]{new String(typeBindingLeafComponentType.shortReadableName()), new String(typeBindingLeafComponentType.erasure().shortReadableName())}, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, Integer.MAX_VALUE));
    }

    public void recursiveConstructorInvocation(ExplicitConstructorCall explicitConstructorCall) {
        handle(IProblem.RecursiveConstructorInvocation, new String[]{new String(explicitConstructorCall.binding.declaringClass.readableName()), typesAsString(explicitConstructorCall.binding, false)}, new String[]{new String(explicitConstructorCall.binding.declaringClass.shortReadableName()), typesAsString(explicitConstructorCall.binding, true)}, explicitConstructorCall.sourceStart, explicitConstructorCall.sourceEnd);
    }

    public void redefineArgument(Argument argument) {
        String[] strArr = {new String(argument.f617name)};
        handle(IProblem.RedefinedArgument, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void redefineLocal(LocalDeclaration localDeclaration) {
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(IProblem.RedefinedLocal, strArr, strArr, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void redundantSuperInterface(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        int iComputeSeverity = computeSeverity(16777547);
        if (iComputeSeverity != 256) {
            handle(16777547, new String[]{new String(referenceBinding.readableName()), new String(sourceTypeBinding.readableName()), new String(referenceBinding2.readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(sourceTypeBinding.shortReadableName()), new String(referenceBinding2.shortReadableName())}, iComputeSeverity, typeReference.sourceStart, typeReference.sourceEnd);
        }
    }

    public void referenceMustBeArrayTypeAt(TypeBinding typeBinding, ArrayReference arrayReference) {
        handle(IProblem.ArrayReferenceRequired, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, arrayReference.sourceStart, arrayReference.sourceEnd);
    }

    public void repeatedAnnotationWithContainer(Annotation annotation, Annotation annotation2) {
        handle(IProblem.RepeatedAnnotationWithContainerAnnotation, new String[]{new String(annotation.resolvedType.readableName()), new String(annotation2.resolvedType.readableName())}, new String[]{new String(annotation.resolvedType.shortReadableName()), new String(annotation2.resolvedType.shortReadableName())}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void containerAnnotationTypeMustHaveValue(ASTNode aSTNode, ReferenceBinding referenceBinding) {
        handle(IProblem.ContainerAnnotationTypeMustHaveValue, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void containerAnnotationTypeHasWrongValueType(ASTNode aSTNode, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, TypeBinding typeBinding) {
        handle(IProblem.ContainerAnnotationTypeHasWrongValueType, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding2.readableName()), new String(typeBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding2.shortReadableName()), new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void containerAnnotationTypeHasNonDefaultMembers(ASTNode aSTNode, ReferenceBinding referenceBinding, char[] cArr) {
        handle(IProblem.ContainerAnnotationTypeHasNonDefaultMembers, new String[]{new String(referenceBinding.readableName()), new String(cArr)}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr)}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void containerAnnotationTypeHasShorterRetention(ASTNode aSTNode, ReferenceBinding referenceBinding, String str, ReferenceBinding referenceBinding2, String str2) {
        handle(IProblem.ContainerAnnotationTypeHasShorterRetention, new String[]{new String(referenceBinding.readableName()), str, new String(referenceBinding2.readableName()), str2}, new String[]{new String(referenceBinding.shortReadableName()), str, new String(referenceBinding2.shortReadableName()), str2}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void repeatableAnnotationTypeTargetMismatch(ASTNode aSTNode, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, String str) {
        handle(IProblem.RepeatableAnnotationTypeTargetMismatch, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding2.readableName()), str}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding2.shortReadableName()), str}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void repeatableAnnotationTypeIsDocumented(ASTNode aSTNode, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        handle(IProblem.RepeatableAnnotationTypeIsDocumented, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding2.readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding2.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void repeatableAnnotationTypeIsInherited(ASTNode aSTNode, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        handle(IProblem.RepeatableAnnotationTypeIsInherited, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding2.readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding2.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void repeatableAnnotationWithRepeatingContainer(Annotation annotation, ReferenceBinding referenceBinding) {
        handle(IProblem.RepeatableAnnotationWithRepeatingContainerAnnotation, new String[]{new String(annotation.resolvedType.readableName()), new String(referenceBinding.readableName())}, new String[]{new String(annotation.resolvedType.shortReadableName()), new String(referenceBinding.shortReadableName())}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void reset() {
        this.positionScanner = null;
    }

    public void resourceHasToImplementAutoCloseable(TypeBinding typeBinding, TypeReference typeReference) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_7) {
            return;
        }
        handle(IProblem.ResourceHasToImplementAutoCloseable, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    private int retrieveClosingAngleBracketPosition(int i) {
        CompilationResult compilationResult;
        ICompilationUnit compilationUnit;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext == null || (compilationResult = referenceContext.compilationResult()) == null || (compilationUnit = compilationResult.getCompilationUnit()) == null) {
            return i;
        }
        char[] contents = compilationUnit.getContents();
        if (contents.length == 0) {
            return i;
        }
        if (this.positionScanner == null) {
            Scanner scanner = new Scanner(false, false, false, this.options.sourceLevel, this.options.complianceLevel, null, null, false);
            this.positionScanner = scanner;
            scanner.returnOnlyGreater = true;
        }
        this.positionScanner.setSource(contents);
        this.positionScanner.resetTo(i, contents.length);
        int i2 = 0;
        while (true) {
            try {
                int nextToken = this.positionScanner.getNextToken();
                if (nextToken == 60) {
                    return i;
                }
                if (nextToken == 11) {
                    i2++;
                } else if (nextToken == 15) {
                    i2--;
                    if (i2 == 0) {
                        return this.positionScanner.currentPosition - 1;
                    }
                } else if (nextToken == 49) {
                    return i;
                }
            } catch (InvalidInputException unused) {
                return i;
            }
        }
    }

    private int retrieveEndingPositionAfterOpeningParenthesis(int i, int i2, int i3) {
        CompilationResult compilationResult;
        ICompilationUnit compilationUnit;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext == null || (compilationResult = referenceContext.compilationResult()) == null || (compilationUnit = compilationResult.getCompilationUnit()) == null) {
            return i2;
        }
        char[] contents = compilationUnit.getContents();
        if (contents.length == 0) {
            return i2;
        }
        if (this.positionScanner == null) {
            this.positionScanner = new Scanner(false, false, false, this.options.sourceLevel, this.options.complianceLevel, null, null, false);
        }
        this.positionScanner.setSource(contents);
        this.positionScanner.resetTo(i, i2);
        int i4 = i2;
        while (true) {
            try {
                int nextToken = this.positionScanner.getNextToken();
                if (nextToken == 60) {
                    break;
                }
                if (nextToken == 25) {
                    return i4;
                }
                i4 = this.positionScanner.currentPosition - 1;
            } catch (InvalidInputException unused) {
            }
        }
        return i2;
    }

    private int retrieveStartingPositionAfterOpeningParenthesis(int i, int i2, int i3) {
        CompilationResult compilationResult;
        ICompilationUnit compilationUnit;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext == null || (compilationResult = referenceContext.compilationResult()) == null || (compilationUnit = compilationResult.getCompilationUnit()) == null) {
            return i;
        }
        char[] contents = compilationUnit.getContents();
        if (contents.length == 0) {
            return i;
        }
        if (this.positionScanner == null) {
            this.positionScanner = new Scanner(false, false, false, this.options.sourceLevel, this.options.complianceLevel, null, null, false);
        }
        this.positionScanner.setSource(contents);
        this.positionScanner.resetTo(i, i2);
        int i4 = 0;
        while (true) {
            try {
                int nextToken = this.positionScanner.getNextToken();
                if (nextToken == 60) {
                    return i;
                }
                if (nextToken == 24 && (i4 = i4 + 1) == i3) {
                    this.positionScanner.getNextToken();
                    return this.positionScanner.startPosition;
                }
            } catch (InvalidInputException unused) {
                return i;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:50:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:74:0x011d  */
    /* JADX WARN: Code duplicated, block: B:75:0x0124  */
    public void scannerError(Parser parser, String str) {
        int i;
        int i2;
        int i3;
        int i4;
        String[] strArr;
        Scanner scanner = parser.scanner;
        int i5 = scanner.startPosition;
        int i6 = scanner.currentPosition - 1;
        if (str.equals(Scanner.END_OF_SOURCE)) {
            i4 = IProblem.EndOfSource;
        } else if (str.equals(Scanner.INVALID_HEXA)) {
            i4 = IProblem.InvalidHexa;
        } else if (str.equals(Scanner.ILLEGAL_HEXA_LITERAL)) {
            i4 = IProblem.IllegalHexaLiteral;
        } else if (str.equals(Scanner.INVALID_OCTAL)) {
            i4 = IProblem.InvalidOctal;
        } else if (str.equals(Scanner.INVALID_CHARACTER_CONSTANT)) {
            i4 = IProblem.InvalidCharacterConstant;
        } else {
            if (!str.equals(Scanner.INVALID_ESCAPE)) {
                if (str.equals(Scanner.INVALID_UNICODE_ESCAPE)) {
                    char[] cArr = scanner.source;
                    int length = scanner.currentPosition - 1;
                    if (length >= cArr.length) {
                        length = cArr.length - 1;
                    }
                    while (length >= i5 && cArr[length] != '\\') {
                        length--;
                    }
                    i = length;
                    i3 = 1610612992;
                    i2 = i6;
                } else if (str.equals(Scanner.INVALID_LOW_SURROGATE)) {
                    i4 = IProblem.InvalidLowSurrogate;
                } else if (str.equals(Scanner.INVALID_HIGH_SURROGATE)) {
                    char[] cArr2 = scanner.source;
                    int i7 = scanner.startPosition + 1;
                    while (i7 <= i6 && cArr2[i7] != '\\') {
                        i7++;
                    }
                    i6 = i7 - 1;
                    i4 = IProblem.InvalidHighSurrogate;
                } else if (str.equals(Scanner.INVALID_FLOAT)) {
                    i4 = IProblem.InvalidFloat;
                } else if (str.equals(Scanner.UNTERMINATED_STRING)) {
                    i = i5;
                    i2 = i6;
                    i3 = 1610612995;
                } else if (str.equals(Scanner.UNTERMINATED_COMMENT)) {
                    i4 = IProblem.UnterminatedComment;
                } else if (str.equals(Scanner.INVALID_CHAR_IN_STRING)) {
                    i = i5;
                    i2 = i6;
                    i3 = 1610612995;
                } else if (str.equals(Scanner.INVALID_DIGIT)) {
                    i4 = IProblem.InvalidDigit;
                } else if (str.equals(Scanner.INVALID_BINARY)) {
                    i4 = IProblem.InvalidBinary;
                } else if (str.equals(Scanner.BINARY_LITERAL_NOT_BELOW_17)) {
                    i4 = IProblem.BinaryLiteralNotBelow17;
                } else if (str.equals(Scanner.INVALID_UNDERSCORE)) {
                    i4 = IProblem.IllegalUnderscorePosition;
                } else if (str.equals(Scanner.UNDERSCORES_IN_LITERALS_NOT_BELOW_17)) {
                    i4 = IProblem.UnderscoresInLiteralsNotBelow17;
                } else {
                    i = i5;
                    i2 = i6;
                    i3 = 1610612941;
                }
                if (i3 == 1610612941) {
                    strArr = new String[]{str};
                } else {
                    strArr = NoArgument;
                }
                handle(i3, strArr, strArr, i, i2, parser.compilationUnit.compilationResult);
            }
            i4 = IProblem.InvalidEscape;
        }
        i3 = i4;
        i = i5;
        i2 = i6;
        if (i3 == 1610612941) {
            strArr = new String[]{str};
        } else {
            strArr = NoArgument;
        }
        handle(i3, strArr, strArr, i, i2, parser.compilationUnit.compilationResult);
    }

    public void shouldImplementHashcode(SourceTypeBinding sourceTypeBinding) {
        handle(16777548, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void shouldReturn(TypeBinding typeBinding, ASTNode aSTNode) {
        int i = aSTNode.sourceStart;
        int iDiagnosticsSourceEnd = aSTNode.sourceEnd;
        if (aSTNode instanceof LambdaExpression) {
            LambdaExpression lambdaExpression = (LambdaExpression) aSTNode;
            i = lambdaExpression.sourceStart;
            iDiagnosticsSourceEnd = lambdaExpression.diagnosticsSourceEnd();
        }
        handle(methodHasMissingSwitchDefault() ? IProblem.ShouldReturnValueHintMissingDefault : IProblem.ShouldReturnValue, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, i, iDiagnosticsSourceEnd);
    }

    public void signalNoImplicitStringConversionForCharArrayExpression(Expression expression) {
        handle(IProblem.NoImplicitStringConversionForCharArrayExpression, NoArgument, NoArgument, expression.sourceStart, expression.sourceEnd);
    }

    public void staticAndInstanceConflict(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (methodBinding.isStatic()) {
            handle(IProblem.CannotHideAnInstanceMethodWithAStaticMethod, new String[]{new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
        } else {
            handle(IProblem.CannotOverrideAStaticMethodWithAnInstanceMethod, new String[]{new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
        }
    }

    public void staticFieldAccessToNonStaticVariable(ASTNode aSTNode, FieldBinding fieldBinding) {
        String[] strArr = {new String(fieldBinding.readableName())};
        handle(IProblem.NonStaticFieldFromStaticInvocation, strArr, strArr, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void staticInheritedMethodConflicts(SourceTypeBinding sourceTypeBinding, MethodBinding methodBinding, MethodBinding[] methodBindingArr) {
        handle(IProblem.StaticInheritedMethodConflicts, new String[]{new String(methodBinding.readableName()), new String(methodBindingArr[0].declaringClass.readableName())}, new String[]{new String(methodBinding.readableName()), new String(methodBindingArr[0].declaringClass.shortReadableName())}, sourceTypeBinding.sourceStart(), sourceTypeBinding.sourceEnd());
    }

    public void staticMemberOfParameterizedType(ASTNode aSTNode, ReferenceBinding referenceBinding, int i) {
        if (aSTNode == null) {
            handle(IProblem.StaticMemberOfParameterizedType, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding.enclosingType().readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding.enclosingType().shortReadableName())}, 131, 0, 0);
        } else {
            handle(IProblem.StaticMemberOfParameterizedType, new String[]{new String(referenceBinding.readableName()), new String(referenceBinding.enclosingType().readableName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(referenceBinding.enclosingType().shortReadableName())}, aSTNode.sourceStart, nodeSourceEnd(null, aSTNode, i));
        }
    }

    public void stringConstantIsExceedingUtf8Limit(ASTNode aSTNode) {
        handle(IProblem.StringConstantIsExceedingUtf8Limit, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void superclassMustBeAClass(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, ReferenceBinding referenceBinding) {
        handle(IProblem.SuperclassMustBeAClass, new String[]{new String(referenceBinding.readableName()), new String(sourceTypeBinding.sourceName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(sourceTypeBinding.sourceName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void superfluousSemicolon(int i, int i2) {
        handle(IProblem.SuperfluousSemicolon, NoArgument, NoArgument, i, i2);
    }

    public void superinterfaceMustBeAnInterface(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, ReferenceBinding referenceBinding) {
        handle(IProblem.SuperInterfaceMustBeAnInterface, new String[]{new String(referenceBinding.readableName()), new String(sourceTypeBinding.sourceName())}, new String[]{new String(referenceBinding.shortReadableName()), new String(sourceTypeBinding.sourceName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void superinterfacesCollide(TypeBinding typeBinding, ASTNode aSTNode, TypeBinding typeBinding2, TypeBinding typeBinding3) {
        handle(IProblem.SuperInterfacesCollide, new String[]{new String(typeBinding2.readableName()), new String(typeBinding3.readableName()), new String(typeBinding.sourceName())}, new String[]{new String(typeBinding2.shortReadableName()), new String(typeBinding3.shortReadableName()), new String(typeBinding.sourceName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void superTypeCannotUseWildcard(SourceTypeBinding sourceTypeBinding, TypeReference typeReference, TypeBinding typeBinding) {
        String str = new String(sourceTypeBinding.sourceName());
        String str2 = new String(typeBinding.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        if (str3.equals(str)) {
            str3 = str2;
        }
        handle(IProblem.SuperTypeUsingWildcard, new String[]{str2, str}, new String[]{str3, str}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    private void syntaxError(int i, int i2, int i3, int i4, char[] cArr, String str, String str2) {
        String[] strArr;
        if (i4 == 37 && str2 != null && str2.equals(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
            return;
        }
        if (isKeyword(i4) || isLiteral(i4) || isIdentifier(i4)) {
            str = new String(cArr);
        }
        if (str2 != null) {
            strArr = new String[]{str, replaceIfSynthetic(str2)};
        } else {
            strArr = new String[]{str};
        }
        String[] strArr2 = strArr;
        handle(i, strArr2, strArr2, i2, i3);
    }

    private String replaceIfSynthetic(String str) {
        if (str.equals("BeginTypeArguments")) {
            return ".";
        }
        return str.equals("BeginLambda") ? "(" : str;
    }

    public void task(String str, String str2, String str3, int i, int i2) {
        handle(IProblem.Task, new String[]{str, str2, str3}, new String[]{str, str2, str3}, i, i2);
    }

    public void tooManyDimensions(ASTNode aSTNode) {
        handle(IProblem.TooManyArrayDimensions, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void tooManyFields(TypeDeclaration typeDeclaration) {
        handle(IProblem.TooManyFields, new String[]{new String(typeDeclaration.binding.readableName())}, new String[]{new String(typeDeclaration.binding.shortReadableName())}, 159, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void tooManyMethods(TypeDeclaration typeDeclaration) {
        handle(IProblem.TooManyMethods, new String[]{new String(typeDeclaration.binding.readableName())}, new String[]{new String(typeDeclaration.binding.shortReadableName())}, 159, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void tooManyParametersForSyntheticMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        String str;
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        if (methodBinding.isConstructor()) {
            str = new String(methodBinding.declaringClass.sourceName());
        } else {
            str = new String(abstractMethodDeclaration.selector);
        }
        handle(IProblem.TooManyParametersForSyntheticMethod, new String[]{str, typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{str, typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, 145, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void typeCastError(CastExpression castExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        String str = new String(typeBinding.readableName());
        String str2 = new String(typeBinding2.readableName());
        String str3 = new String(typeBinding.shortReadableName());
        String str4 = new String(typeBinding2.shortReadableName());
        if (str3.equals(str4)) {
            str3 = str;
            str4 = str2;
        }
        handle(IProblem.IllegalCast, new String[]{str2, str}, new String[]{str4, str3}, castExpression.sourceStart, castExpression.sourceEnd);
    }

    public void typeCollidesWithEnclosingType(TypeDeclaration typeDeclaration) {
        String[] strArr = {new String(typeDeclaration.f619name)};
        handle(IProblem.HidingEnclosingType, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void typeCollidesWithPackage(CompilationUnitDeclaration compilationUnitDeclaration, TypeDeclaration typeDeclaration) {
        this.referenceContext = typeDeclaration;
        String[] strArr = {new String(compilationUnitDeclaration.getFileName()), new String(typeDeclaration.f619name)};
        handle(IProblem.TypeCollidesWithPackage, strArr, strArr, typeDeclaration.sourceStart, typeDeclaration.sourceEnd, compilationUnitDeclaration.compilationResult);
    }

    public void typeHiding(TypeDeclaration typeDeclaration, TypeBinding typeBinding) {
        int iComputeSeverity = computeSeverity(IProblem.TypeHidingType);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.TypeHidingType, new String[]{new String(typeDeclaration.f619name), new String(typeBinding.shortReadableName())}, new String[]{new String(typeDeclaration.f619name), new String(typeBinding.readableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void typeHiding(TypeDeclaration typeDeclaration, TypeVariableBinding typeVariableBinding) {
        int iComputeSeverity = computeSeverity(IProblem.TypeHidingTypeParameterFromType);
        if (iComputeSeverity == 256) {
            return;
        }
        if (typeVariableBinding.declaringElement instanceof TypeBinding) {
            TypeBinding typeBinding = (TypeBinding) typeVariableBinding.declaringElement;
            handle(IProblem.TypeHidingTypeParameterFromType, new String[]{new String(typeDeclaration.f619name), new String(typeVariableBinding.readableName()), new String(typeBinding.readableName())}, new String[]{new String(typeDeclaration.f619name), new String(typeVariableBinding.shortReadableName()), new String(typeBinding.shortReadableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
        } else {
            MethodBinding methodBinding = (MethodBinding) typeVariableBinding.declaringElement;
            handle(IProblem.TypeHidingTypeParameterFromMethod, new String[]{new String(typeDeclaration.f619name), new String(typeVariableBinding.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName())}, new String[]{new String(typeDeclaration.f619name), new String(typeVariableBinding.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
        }
    }

    public void typeHiding(TypeParameter typeParameter, Binding binding) {
        int iComputeSeverity = computeSeverity(IProblem.TypeParameterHidingType);
        if (iComputeSeverity == 256) {
            return;
        }
        TypeBinding typeBinding = (TypeBinding) binding;
        handle(IProblem.TypeParameterHidingType, new String[]{new String(typeParameter.f617name), new String(typeBinding.readableName())}, new String[]{new String(typeParameter.f617name), new String(typeBinding.shortReadableName())}, iComputeSeverity, typeParameter.sourceStart, typeParameter.sourceEnd);
    }

    public void notAnnotationType(TypeBinding typeBinding, ASTNode aSTNode) {
        handle(IProblem.NotAnnotationType, new String[]{new String(typeBinding.leafComponentType().readableName())}, new String[]{new String(typeBinding.leafComponentType().shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void typeMismatchError(TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode, ASTNode aSTNode2) {
        TypeBinding typeBindingErasure;
        TypeBinding typeBindingErasure2;
        if (this.options.sourceLevel < ClassFileConstants.JDK1_5) {
            typeBindingErasure = typeBinding;
            if (typeBindingErasure instanceof TypeVariableBinding) {
                typeBindingErasure = typeBinding.erasure();
            }
            typeBindingErasure2 = typeBinding2;
            if (typeBindingErasure2 instanceof TypeVariableBinding) {
                typeBindingErasure2 = typeBinding2.erasure();
            }
        } else {
            typeBindingErasure = typeBinding;
            typeBindingErasure2 = typeBinding2;
        }
        if (typeBindingErasure != null && (typeBindingErasure.tagBits & 128) != 0) {
            if (aSTNode instanceof Annotation) {
                return;
            }
            handle(IProblem.UndefinedType, new String[]{new String(typeBindingErasure.leafComponentType().readableName())}, new String[]{new String(typeBindingErasure.leafComponentType().shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
            return;
        }
        if (aSTNode2 != null && (128 & typeBindingErasure2.tagBits) != 0) {
            handle(IProblem.UndefinedType, new String[]{new String(typeBindingErasure2.leafComponentType().readableName())}, new String[]{new String(typeBindingErasure2.leafComponentType().shortReadableName())}, aSTNode2.sourceStart, aSTNode2.sourceEnd);
            return;
        }
        char[] cArrShortReadableName = typeBindingErasure.shortReadableName();
        char[] cArrShortReadableName2 = typeBindingErasure2.shortReadableName();
        char[] cArrNullAnnotatedReadableName = typeBindingErasure.readableName();
        char[] cArrNullAnnotatedReadableName2 = typeBindingErasure2.readableName();
        if (CharOperation.equals(cArrShortReadableName, cArrShortReadableName2)) {
            if (CharOperation.equals(cArrNullAnnotatedReadableName, cArrNullAnnotatedReadableName2)) {
                cArrNullAnnotatedReadableName = typeBindingErasure.nullAnnotatedReadableName(this.options, false);
                cArrNullAnnotatedReadableName2 = typeBindingErasure2.nullAnnotatedReadableName(this.options, false);
                cArrShortReadableName = typeBindingErasure.nullAnnotatedReadableName(this.options, true);
                cArrShortReadableName2 = typeBindingErasure2.nullAnnotatedReadableName(this.options, true);
            } else {
                cArrShortReadableName = cArrNullAnnotatedReadableName;
                cArrShortReadableName2 = cArrNullAnnotatedReadableName2;
            }
        }
        handle(aSTNode2 instanceof ReturnStatement ? IProblem.ReturnTypeMismatch : IProblem.TypeMismatch, new String[]{new String(cArrNullAnnotatedReadableName), new String(cArrNullAnnotatedReadableName2)}, new String[]{new String(cArrShortReadableName), new String(cArrShortReadableName2)}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void typeMismatchError(TypeBinding typeBinding, TypeVariableBinding typeVariableBinding, ReferenceBinding referenceBinding, ASTNode aSTNode) {
        if (aSTNode == null) {
            handle(IProblem.TypeArgumentMismatch, new String[]{new String(typeBinding.readableName()), new String(referenceBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(typeBinding.shortReadableName()), new String(referenceBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, 131, 0, 0);
        } else {
            handle(IProblem.TypeArgumentMismatch, new String[]{new String(typeBinding.readableName()), new String(referenceBinding.readableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, false)}, new String[]{new String(typeBinding.shortReadableName()), new String(referenceBinding.shortReadableName()), new String(typeVariableBinding.sourceName()), parameterBoundAsString(typeVariableBinding, true)}, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    private String typesAsString(MethodBinding methodBinding, boolean z) {
        return typesAsString(methodBinding, methodBinding.parameters, z);
    }

    private String typesAsString(MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z) {
        return typesAsString(methodBinding, typeBindingArr, z, false);
    }

    private String typesAsString(MethodBinding methodBinding, boolean z, boolean z2) {
        return typesAsString(methodBinding, methodBinding.parameters, z, z2);
    }

    private String typesAsString(MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z, boolean z2) {
        if (methodBinding.isPolymorphic()) {
            TypeBinding[] typeBindingArr2 = methodBinding.original().parameters;
            StringBuffer stringBuffer = new StringBuffer(10);
            int length = typeBindingArr2.length;
            int i = 0;
            while (i < length) {
                if (i != 0) {
                    stringBuffer.append(", ");
                }
                TypeBinding typeBindingElementsType = typeBindingArr2[i];
                boolean z3 = i == length + (-1);
                if (z3) {
                    typeBindingElementsType = ((ArrayBinding) typeBindingElementsType).elementsType();
                }
                if (z2) {
                    stringBuffer.append(new String(typeBindingElementsType.nullAnnotatedReadableName(this.options, z)));
                } else {
                    stringBuffer.append(new String(z ? typeBindingElementsType.shortReadableName() : typeBindingElementsType.readableName()));
                }
                if (z3) {
                    stringBuffer.append("...");
                }
                i++;
            }
            return stringBuffer.toString();
        }
        StringBuffer stringBuffer2 = new StringBuffer(10);
        int length2 = typeBindingArr.length;
        int i2 = 0;
        while (i2 < length2) {
            if (i2 != 0) {
                stringBuffer2.append(", ");
            }
            TypeBinding typeBindingElementsType2 = typeBindingArr[i2];
            boolean z4 = methodBinding.isVarargs() && i2 == length2 + (-1);
            if (z4) {
                typeBindingElementsType2 = ((ArrayBinding) typeBindingElementsType2).elementsType();
            }
            if (z2) {
                stringBuffer2.append(new String(typeBindingElementsType2.nullAnnotatedReadableName(this.options, z)));
            } else {
                stringBuffer2.append(new String(z ? typeBindingElementsType2.shortReadableName() : typeBindingElementsType2.readableName()));
            }
            if (z4) {
                stringBuffer2.append("...");
            }
            i2++;
        }
        return stringBuffer2.toString();
    }

    private String typesAsString(TypeBinding[] typeBindingArr, boolean z) {
        return typesAsString(typeBindingArr, z, false);
    }

    private String typesAsString(TypeBinding[] typeBindingArr, boolean z, boolean z2) {
        StringBuffer stringBuffer = new StringBuffer(10);
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            TypeBinding typeBinding = typeBindingArr[i];
            if (z2) {
                stringBuffer.append(new String(typeBinding.nullAnnotatedReadableName(this.options, z)));
            } else {
                stringBuffer.append(new String(z ? typeBinding.shortReadableName() : typeBinding.readableName()));
            }
        }
        return stringBuffer.toString();
    }

    public void undefinedAnnotationValue(TypeBinding typeBinding, MemberValuePair memberValuePair) {
        if (isRecoveredName(memberValuePair.f618name)) {
            return;
        }
        String str = new String(memberValuePair.f618name);
        handle(IProblem.UndefinedAnnotationMember, new String[]{str, new String(typeBinding.readableName())}, new String[]{str, new String(typeBinding.shortReadableName())}, memberValuePair.sourceStart, memberValuePair.sourceEnd);
    }

    public void undefinedLabel(BranchStatement branchStatement) {
        if (isRecoveredName(branchStatement.label)) {
            return;
        }
        String[] strArr = {new String(branchStatement.label)};
        handle(IProblem.UndefinedLabel, strArr, strArr, branchStatement.sourceStart, branchStatement.sourceEnd);
    }

    public void undefinedTypeVariableSignature(char[] cArr, ReferenceBinding referenceBinding) {
        handle(IProblem.UndefinedTypeVariable, new String[]{new String(cArr), new String(referenceBinding.readableName())}, new String[]{new String(cArr), new String(referenceBinding.shortReadableName())}, 131, 0, 0);
    }

    public void undocumentedEmptyBlock(int i, int i2) {
        handle(IProblem.UndocumentedEmptyBlock, NoArgument, NoArgument, i, i2);
    }

    public void unexpectedStaticModifierForField(SourceTypeBinding sourceTypeBinding, FieldDeclaration fieldDeclaration) {
        String[] strArr = {new String(fieldDeclaration.f617name)};
        handle(IProblem.UnexpectedStaticModifierForField, strArr, strArr, fieldDeclaration.sourceStart, fieldDeclaration.sourceEnd);
    }

    public void unexpectedStaticModifierForMethod(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration) {
        String[] strArr = {new String(referenceBinding.sourceName()), new String(abstractMethodDeclaration.selector)};
        handle(IProblem.UnexpectedStaticModifierForMethod, strArr, strArr, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    public void unhandledException(TypeBinding typeBinding, ASTNode aSTNode) {
        int i;
        ReferenceContext referenceContext = this.referenceContext;
        boolean z = (referenceContext instanceof ConstructorDeclaration) && ((ConstructorDeclaration) referenceContext).isDefaultConstructor();
        boolean z2 = (aSTNode instanceof ExplicitConstructorCall) && ((ExplicitConstructorCall) aSTNode).accessMode == 1;
        int i2 = aSTNode.sourceEnd;
        if (aSTNode instanceof LocalDeclaration) {
            i2 = ((LocalDeclaration) aSTNode).declarationEnd;
        }
        int i3 = i2;
        if (z) {
            i = IProblem.UnhandledExceptionInDefaultConstructor;
        } else {
            i = z2 ? IProblem.UndefinedConstructorInImplicitConstructorCall : IProblem.UnhandledException;
        }
        handle(i, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, i3);
    }

    public void unhandledExceptionFromAutoClose(TypeBinding typeBinding, ASTNode aSTNode) {
        LocalVariableBinding localVariableBinding = ((LocalDeclaration) aSTNode).binding;
        if (localVariableBinding != null) {
            handle(IProblem.UnhandledExceptionOnAutoClose, new String[]{new String(typeBinding.readableName()), new String(localVariableBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(localVariableBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void unhandledWarningToken(Expression expression) {
        String[] strArr = {expression.constant.stringValue()};
        handle(IProblem.UnhandledWarningToken, strArr, strArr, expression.sourceStart, expression.sourceEnd);
    }

    public void uninitializedBlankFinalField(FieldBinding fieldBinding, ASTNode aSTNode) {
        String[] strArr = {new String(fieldBinding.readableName())};
        handle(methodHasMissingSwitchDefault() ? IProblem.UninitializedBlankFinalFieldHintMissingDefault : IProblem.UninitializedBlankFinalField, strArr, strArr, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
    }

    public void uninitializedNonNullField(FieldBinding fieldBinding, ASTNode aSTNode) {
        char[][] cArr = this.options.nonNullAnnotationName;
        if (!fieldBinding.isNonNull()) {
            String[] strArr = {new String(fieldBinding.readableName()), new String(fieldBinding.type.readableName()), new String(cArr[cArr.length - 1])};
            handle(methodHasMissingSwitchDefault() ? 978 : 977, strArr, strArr, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
        } else {
            String[] strArr2 = {new String(cArr[cArr.length - 1]), new String(fieldBinding.readableName())};
            handle(methodHasMissingSwitchDefault() ? IProblem.UninitializedNonNullFieldHintMissingDefault : IProblem.UninitializedNonNullField, strArr2, strArr2, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
        }
    }

    public void uninitializedLocalVariable(LocalVariableBinding localVariableBinding, ASTNode aSTNode) {
        localVariableBinding.tagBits |= 256;
        String[] strArr = {new String(localVariableBinding.readableName())};
        handle(methodHasMissingSwitchDefault() ? IProblem.UninitializedLocalVariableHintMissingDefault : IProblem.UninitializedLocalVariable, strArr, strArr, nodeSourceStart(localVariableBinding, aSTNode), nodeSourceEnd(localVariableBinding, aSTNode));
    }

    private boolean methodHasMissingSwitchDefault() {
        MethodScope methodScope;
        Object obj = this.referenceContext;
        if (obj instanceof Block) {
            methodScope = ((Block) obj).scope.methodScope();
        } else {
            methodScope = obj instanceof AbstractMethodDeclaration ? ((AbstractMethodDeclaration) obj).scope : null;
        }
        return methodScope != null && methodScope.hasMissingSwitchDefault;
    }

    public void unmatchedBracket(int i, ReferenceContext referenceContext, CompilationResult compilationResult) {
        handle(IProblem.UnmatchedBracket, NoArgument, NoArgument, i, i, referenceContext, compilationResult);
    }

    public void unnecessaryCast(CastExpression castExpression) {
        int iComputeSeverity;
        if ((castExpression.expression instanceof FunctionalExpression) || (iComputeSeverity = computeSeverity(IProblem.UnnecessaryCast)) == 256) {
            return;
        }
        TypeBinding typeBinding = castExpression.expression.resolvedType;
        handle(IProblem.UnnecessaryCast, new String[]{new String(typeBinding.readableName()), new String(castExpression.type.resolvedType.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(castExpression.type.resolvedType.shortReadableName())}, iComputeSeverity, castExpression.sourceStart, castExpression.sourceEnd);
    }

    public void unnecessaryElse(ASTNode aSTNode) {
        handle(IProblem.UnnecessaryElse, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void unnecessaryEnclosingInstanceSpecification(Expression expression, ReferenceBinding referenceBinding) {
        handle(IProblem.IllegalEnclosingInstanceSpecification, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, expression.sourceStart, expression.sourceEnd);
    }

    public void unnecessaryInstanceof(InstanceOfExpression instanceOfExpression, TypeBinding typeBinding) {
        int iComputeSeverity = computeSeverity(IProblem.UnnecessaryInstanceof);
        if (iComputeSeverity == 256) {
            return;
        }
        TypeBinding typeBinding2 = instanceOfExpression.expression.resolvedType;
        handle(IProblem.UnnecessaryInstanceof, new String[]{new String(typeBinding2.readableName()), new String(typeBinding.readableName())}, new String[]{new String(typeBinding2.shortReadableName()), new String(typeBinding.shortReadableName())}, iComputeSeverity, instanceOfExpression.sourceStart, instanceOfExpression.sourceEnd);
    }

    public void unnecessaryNLSTags(int i, int i2) {
        handle(IProblem.UnnecessaryNLSTag, NoArgument, NoArgument, i, i2);
    }

    public void unnecessaryTypeArgumentsForMethodInvocation(MethodBinding methodBinding, TypeBinding[] typeBindingArr, TypeReference[] typeReferenceArr) {
        String str;
        if (methodBinding.isConstructor()) {
            str = new String(methodBinding.declaringClass.shortReadableName());
        } else {
            str = new String(methodBinding.selector);
        }
        handle(methodBinding.isConstructor() ? IProblem.UnusedTypeArgumentsForConstructorInvocation : IProblem.UnusedTypeArgumentsForMethodInvocation, new String[]{str, typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), typesAsString(typeBindingArr, false)}, new String[]{str, typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true)}, typeReferenceArr[0].sourceStart, typeReferenceArr[typeReferenceArr.length - 1].sourceEnd);
    }

    public void unqualifiedFieldAccess(NameReference nameReference, FieldBinding fieldBinding) {
        int iRetrieveEndingPositionAfterOpeningParenthesis;
        int i;
        int i2;
        int i3 = nameReference.sourceStart;
        int i4 = nameReference.sourceEnd;
        if ((nameReference instanceof SingleNameReference) && (i2 = (nameReference.bits & ASTNode.ParenthesizedMASK) >> 21) != 0) {
            int iRetrieveStartingPositionAfterOpeningParenthesis = retrieveStartingPositionAfterOpeningParenthesis(i3, i4, i2);
            i = iRetrieveStartingPositionAfterOpeningParenthesis;
            iRetrieveEndingPositionAfterOpeningParenthesis = retrieveEndingPositionAfterOpeningParenthesis(iRetrieveStartingPositionAfterOpeningParenthesis, i4, i2);
        } else {
            int iNodeSourceStart = nodeSourceStart(fieldBinding, nameReference);
            int iNodeSourceEnd = nodeSourceEnd(fieldBinding, nameReference);
            iRetrieveEndingPositionAfterOpeningParenthesis = iNodeSourceEnd;
            i = iNodeSourceStart;
        }
        handle(IProblem.UnqualifiedFieldAccess, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, i, iRetrieveEndingPositionAfterOpeningParenthesis);
    }

    public void unreachableCatchBlock(ReferenceBinding referenceBinding, ASTNode aSTNode) {
        handle(IProblem.UnreachableCatch, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void unreachableCode(Statement statement) {
        int i;
        int i2;
        int i3;
        int i4 = statement.sourceStart;
        int i5 = statement.sourceEnd;
        if (statement instanceof LocalDeclaration) {
            LocalDeclaration localDeclaration = (LocalDeclaration) statement;
            i4 = localDeclaration.declarationSourceStart;
            i5 = localDeclaration.declarationSourceEnd;
        } else {
            if ((statement instanceof Expression) && (i = ((Expression) statement).statementEnd) != -1) {
                i2 = i;
                i3 = i4;
            }
            handle(IProblem.CodeCannotBeReached, NoArgument, NoArgument, i3, i2);
        }
        i3 = i4;
        i2 = i5;
        handle(IProblem.CodeCannotBeReached, NoArgument, NoArgument, i3, i2);
    }

    public void unresolvableReference(NameReference nameReference, Binding binding) {
        String[] strArr = {new String(binding.readableName())};
        int iRetrieveEndingPositionAfterOpeningParenthesis = nameReference.sourceEnd;
        int iRetrieveStartingPositionAfterOpeningParenthesis = nameReference.sourceStart;
        if (nameReference instanceof QualifiedNameReference) {
            QualifiedNameReference qualifiedNameReference = (QualifiedNameReference) nameReference;
            if (isRecoveredName(qualifiedNameReference.tokens)) {
                return;
            }
            if (qualifiedNameReference.indexOfFirstFieldBinding >= 1) {
                iRetrieveEndingPositionAfterOpeningParenthesis = (int) qualifiedNameReference.sourcePositions[qualifiedNameReference.indexOfFirstFieldBinding - 1];
            }
        } else {
            SingleNameReference singleNameReference = (SingleNameReference) nameReference;
            if (isRecoveredName(singleNameReference.token)) {
                return;
            }
            int i = (singleNameReference.bits & ASTNode.ParenthesizedMASK) >> 21;
            if (i != 0) {
                iRetrieveStartingPositionAfterOpeningParenthesis = retrieveStartingPositionAfterOpeningParenthesis(iRetrieveStartingPositionAfterOpeningParenthesis, iRetrieveEndingPositionAfterOpeningParenthesis, i);
                iRetrieveEndingPositionAfterOpeningParenthesis = retrieveEndingPositionAfterOpeningParenthesis(iRetrieveStartingPositionAfterOpeningParenthesis, iRetrieveEndingPositionAfterOpeningParenthesis, i);
            }
        }
        handle(((nameReference.bits & 3) == 0 || (nameReference.bits & 4) != 0) ? IProblem.UndefinedName : IProblem.UnresolvedVariable, strArr, strArr, iRetrieveStartingPositionAfterOpeningParenthesis, iRetrieveEndingPositionAfterOpeningParenthesis);
    }

    public void unsafeCast(CastExpression castExpression, Scope scope) {
        int iComputeSeverity;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.UnsafeGenericCast)) != 256) {
            TypeBinding typeBinding = castExpression.expression.resolvedType;
            TypeBinding typeBinding2 = castExpression.resolvedType;
            handle(IProblem.UnsafeGenericCast, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, iComputeSeverity, castExpression.sourceStart, castExpression.sourceEnd);
        }
    }

    public void unsafeNullnessCast(CastExpression castExpression, Scope scope) {
        TypeBinding typeBinding = castExpression.expression.resolvedType;
        TypeBinding typeBinding2 = castExpression.resolvedType;
        handle(IProblem.UnsafeNullnessCast, new String[]{new String(typeBinding.nullAnnotatedReadableName(this.options, false)), new String(typeBinding2.nullAnnotatedReadableName(this.options, false))}, new String[]{new String(typeBinding.nullAnnotatedReadableName(this.options, true)), new String(typeBinding2.nullAnnotatedReadableName(this.options, true))}, castExpression.sourceStart, castExpression.sourceEnd);
    }

    public void unsafeGenericArrayForVarargs(TypeBinding typeBinding, ASTNode aSTNode) {
        int iComputeSeverity = computeSeverity(IProblem.UnsafeGenericArrayForVarargs);
        if (iComputeSeverity == 256) {
            return;
        }
        handle(IProblem.UnsafeGenericArrayForVarargs, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void unsafeRawFieldAssignment(FieldBinding fieldBinding, TypeBinding typeBinding, ASTNode aSTNode) {
        int iComputeSeverity;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.UnsafeRawFieldAssignment)) != 256) {
            handle(IProblem.UnsafeRawFieldAssignment, new String[]{new String(typeBinding.readableName()), new String(fieldBinding.f628name), new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.declaringClass.erasure().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(fieldBinding.f628name), new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.declaringClass.erasure().shortReadableName())}, iComputeSeverity, nodeSourceStart(fieldBinding, aSTNode), nodeSourceEnd(fieldBinding, aSTNode));
        }
    }

    public void unsafeRawGenericMethodInvocation(ASTNode aSTNode, MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_5) {
            return;
        }
        boolean zIsConstructor = methodBinding.isConstructor();
        int iComputeSeverity = computeSeverity(zIsConstructor ? IProblem.UnsafeRawGenericConstructorInvocation : IProblem.UnsafeRawGenericMethodInvocation);
        if (iComputeSeverity == 256) {
            return;
        }
        if (zIsConstructor) {
            handle(IProblem.UnsafeRawGenericConstructorInvocation, new String[]{new String(methodBinding.declaringClass.sourceName()), typesAsString(methodBinding.original(), false), new String(methodBinding.declaringClass.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(methodBinding.declaringClass.sourceName()), typesAsString(methodBinding.original(), true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.UnsafeRawGenericMethodInvocation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), false), new String(methodBinding.declaringClass.readableName()), typesAsString(typeBindingArr, false)}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(typeBindingArr, true)}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void unsafeRawInvocation(ASTNode aSTNode, MethodBinding methodBinding) {
        if (this.options.sourceLevel < ClassFileConstants.JDK1_5) {
            return;
        }
        boolean zIsConstructor = methodBinding.isConstructor();
        int iComputeSeverity = computeSeverity(zIsConstructor ? IProblem.UnsafeRawConstructorInvocation : IProblem.UnsafeRawMethodInvocation);
        if (iComputeSeverity == 256) {
            return;
        }
        if (zIsConstructor) {
            handle(IProblem.UnsafeRawConstructorInvocation, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding.original(), methodBinding.parameters, false), new String(methodBinding.declaringClass.erasure().readableName())}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding.original(), methodBinding.parameters, true), new String(methodBinding.declaringClass.erasure().shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.UnsafeRawMethodInvocation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), methodBinding.parameters, false), new String(methodBinding.declaringClass.readableName()), new String(methodBinding.declaringClass.erasure().readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding.original(), methodBinding.parameters, true), new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.declaringClass.erasure().shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void unsafeReturnTypeOverride(MethodBinding methodBinding, MethodBinding methodBinding2, SourceTypeBinding sourceTypeBinding) {
        int iComputeSeverity;
        int i;
        int iSourceEnd;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.UnsafeReturnTypeOverride)) != 256) {
            int iSourceStart = sourceTypeBinding.sourceStart();
            int iSourceEnd2 = sourceTypeBinding.sourceEnd();
            if (TypeBinding.equalsEquals(methodBinding.declaringClass, sourceTypeBinding)) {
                TypeReference typeReference = ((MethodDeclaration) methodBinding.sourceMethod()).returnType;
                int iSourceStart2 = typeReference.sourceStart();
                iSourceEnd = typeReference.sourceEnd();
                i = iSourceStart2;
            } else {
                i = iSourceStart;
                iSourceEnd = iSourceEnd2;
            }
            handle(IProblem.UnsafeReturnTypeOverride, new String[]{new String(methodBinding.returnType.readableName()), new String(methodBinding.selector), typesAsString(methodBinding.original(), false), new String(methodBinding.declaringClass.readableName()), new String(methodBinding2.returnType.readableName()), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.returnType.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding.original(), true), new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding2.returnType.shortReadableName()), new String(methodBinding2.declaringClass.shortReadableName())}, iComputeSeverity, i, iSourceEnd);
        }
    }

    public void unsafeTypeConversion(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        int iComputeSeverity;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.UnsafeTypeConversion)) != 256) {
            if (this.options.reportUnavoidableGenericTypeProblems || !expression.forcedToBeRaw(this.referenceContext)) {
                handle(IProblem.UnsafeTypeConversion, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName()), new String(typeBinding2.erasure().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName()), new String(typeBinding2.erasure().shortReadableName())}, iComputeSeverity, expression.sourceStart, expression.sourceEnd);
            }
        }
    }

    public void unsafeElementTypeConversion(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        int iComputeSeverity;
        if (this.options.sourceLevel >= ClassFileConstants.JDK1_5 && (iComputeSeverity = computeSeverity(IProblem.UnsafeElementTypeConversion)) != 256) {
            if (this.options.reportUnavoidableGenericTypeProblems || !expression.forcedToBeRaw(this.referenceContext)) {
                handle(IProblem.UnsafeElementTypeConversion, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName()), new String(typeBinding2.erasure().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName()), new String(typeBinding2.erasure().shortReadableName())}, iComputeSeverity, expression.sourceStart, expression.sourceEnd);
            }
        }
    }

    public void unusedArgument(LocalDeclaration localDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.ArgumentIsNeverUsed);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(IProblem.ArgumentIsNeverUsed, strArr, strArr, iComputeSeverity, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void unusedExceptionParameter(LocalDeclaration localDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.ExceptionParameterIsNeverUsed);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(IProblem.ExceptionParameterIsNeverUsed, strArr, strArr, iComputeSeverity, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void unusedDeclaredThrownException(ReferenceBinding referenceBinding, AbstractMethodDeclaration abstractMethodDeclaration, ASTNode aSTNode) {
        boolean zIsConstructor = abstractMethodDeclaration.isConstructor();
        int iComputeSeverity = computeSeverity(zIsConstructor ? IProblem.UnusedConstructorDeclaredThrownException : IProblem.UnusedMethodDeclaredThrownException);
        if (iComputeSeverity == 256) {
            return;
        }
        if (zIsConstructor) {
            handle(IProblem.UnusedConstructorDeclaredThrownException, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.readableName()), typesAsString(abstractMethodDeclaration.binding, false), new String(referenceBinding.readableName())}, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.shortReadableName()), typesAsString(abstractMethodDeclaration.binding, true), new String(referenceBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.UnusedMethodDeclaredThrownException, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.readableName()), new String(abstractMethodDeclaration.selector), typesAsString(abstractMethodDeclaration.binding, false), new String(referenceBinding.readableName())}, new String[]{new String(abstractMethodDeclaration.binding.declaringClass.shortReadableName()), new String(abstractMethodDeclaration.selector), typesAsString(abstractMethodDeclaration.binding, true), new String(referenceBinding.shortReadableName())}, iComputeSeverity, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void unusedImport(ImportReference importReference) {
        int iComputeSeverity = computeSeverity(IProblem.UnusedImport);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {CharOperation.toString(importReference.tokens)};
        handle(IProblem.UnusedImport, strArr, strArr, iComputeSeverity, importReference.sourceStart, importReference.sourceEnd);
    }

    public void unusedLabel(LabeledStatement labeledStatement) {
        int iComputeSeverity = computeSeverity(IProblem.UnusedLabel);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(labeledStatement.label)};
        handle(IProblem.UnusedLabel, strArr, strArr, iComputeSeverity, labeledStatement.sourceStart, labeledStatement.labelEnd);
    }

    public void unusedLocalVariable(LocalDeclaration localDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.LocalVariableIsNeverUsed);
        if (iComputeSeverity == 256) {
            return;
        }
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(IProblem.LocalVariableIsNeverUsed, strArr, strArr, iComputeSeverity, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void unusedObjectAllocation(AllocationExpression allocationExpression) {
        handle(IProblem.UnusedObjectAllocation, NoArgument, NoArgument, allocationExpression.sourceStart, allocationExpression.sourceEnd);
    }

    public void unusedPrivateConstructor(ConstructorDeclaration constructorDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.UnusedPrivateConstructor);
        if (iComputeSeverity == 256 || excludeDueToAnnotation(constructorDeclaration.annotations, IProblem.UnusedPrivateConstructor)) {
            return;
        }
        MethodBinding methodBinding = constructorDeclaration.binding;
        handle(IProblem.UnusedPrivateConstructor, new String[]{new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true)}, iComputeSeverity, constructorDeclaration.sourceStart, constructorDeclaration.sourceEnd);
    }

    public void unusedPrivateField(FieldDeclaration fieldDeclaration) {
        ReferenceBinding referenceBinding;
        ReferenceBinding referenceBinding2;
        int iComputeSeverity = computeSeverity(IProblem.UnusedPrivateField);
        if (iComputeSeverity == 256) {
            return;
        }
        FieldBinding fieldBinding = fieldDeclaration.binding;
        if (CharOperation.equals(TypeConstants.SERIALVERSIONUID, fieldBinding.f628name) && fieldBinding.isStatic() && fieldBinding.isFinal() && TypeBinding.equalsEquals(TypeBinding.LONG, fieldBinding.type) && (referenceBinding2 = fieldBinding.declaringClass) != null && referenceBinding2.findSuperTypeOriginatingFrom(37, false) != null) {
            return;
        }
        if ((CharOperation.equals(TypeConstants.SERIALPERSISTENTFIELDS, fieldBinding.f628name) && fieldBinding.isStatic() && fieldBinding.isFinal() && fieldBinding.type.dimensions() == 1 && CharOperation.equals(TypeConstants.CharArray_JAVA_IO_OBJECTSTREAMFIELD, fieldBinding.type.leafComponentType().readableName()) && (referenceBinding = fieldBinding.declaringClass) != null && referenceBinding.findSuperTypeOriginatingFrom(37, false) != null) || excludeDueToAnnotation(fieldDeclaration.annotations, IProblem.UnusedPrivateField)) {
            return;
        }
        handle(IProblem.UnusedPrivateField, new String[]{new String(fieldBinding.declaringClass.readableName()), new String(fieldBinding.f628name)}, new String[]{new String(fieldBinding.declaringClass.shortReadableName()), new String(fieldBinding.f628name)}, iComputeSeverity, nodeSourceStart(fieldBinding, fieldDeclaration), nodeSourceEnd(fieldBinding, fieldDeclaration));
    }

    public void unusedPrivateMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.UnusedPrivateMethod);
        if (iComputeSeverity == 256) {
            return;
        }
        MethodBinding methodBinding = abstractMethodDeclaration.binding;
        if (!methodBinding.isStatic() && TypeBinding.VOID == methodBinding.returnType && methodBinding.parameters.length == 1 && methodBinding.parameters[0].dimensions() == 0 && CharOperation.equals(methodBinding.selector, TypeConstants.READOBJECT) && CharOperation.equals(TypeConstants.CharArray_JAVA_IO_OBJECTINPUTSTREAM, methodBinding.parameters[0].readableName())) {
            return;
        }
        if (!methodBinding.isStatic() && TypeBinding.VOID == methodBinding.returnType && methodBinding.parameters.length == 1 && methodBinding.parameters[0].dimensions() == 0 && CharOperation.equals(methodBinding.selector, TypeConstants.WRITEOBJECT) && CharOperation.equals(TypeConstants.CharArray_JAVA_IO_OBJECTOUTPUTSTREAM, methodBinding.parameters[0].readableName())) {
            return;
        }
        if (!methodBinding.isStatic() && 1 == methodBinding.returnType.f626id && methodBinding.parameters.length == 0 && CharOperation.equals(methodBinding.selector, TypeConstants.READRESOLVE)) {
            return;
        }
        if ((!methodBinding.isStatic() && 1 == methodBinding.returnType.f626id && methodBinding.parameters.length == 0 && CharOperation.equals(methodBinding.selector, TypeConstants.WRITEREPLACE)) || excludeDueToAnnotation(abstractMethodDeclaration.annotations, IProblem.UnusedPrivateMethod)) {
            return;
        }
        handle(IProblem.UnusedPrivateMethod, new String[]{new String(methodBinding.declaringClass.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false)}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true)}, iComputeSeverity, abstractMethodDeclaration.sourceStart, abstractMethodDeclaration.sourceEnd);
    }

    private boolean excludeDueToAnnotation(Annotation[] annotationArr, int i) {
        int i2;
        if (annotationArr == null || (annotationArr.length) == 0) {
            return false;
        }
        for (Annotation annotation : annotationArr) {
            TypeBinding typeBinding = annotation.resolvedType;
            if (typeBinding != null && (i2 = typeBinding.f626id) != 44 && i2 != 49 && i2 != 60) {
                switch (i2) {
                    case 80:
                    case 81:
                    case 82:
                        if (i != 570425421) {
                            return true;
                        }
                        break;
                        break;
                    default:
                        if (!(typeBinding instanceof ReferenceBinding) || !((ReferenceBinding) typeBinding).hasNullBit(224)) {
                            return true;
                        }
                        break;
                        break;
                }
            }
        }
        return false;
    }

    public void unusedPrivateType(TypeDeclaration typeDeclaration) {
        int iComputeSeverity = computeSeverity(IProblem.UnusedPrivateType);
        if (iComputeSeverity == 256 || excludeDueToAnnotation(typeDeclaration.annotations, IProblem.UnusedPrivateType)) {
            return;
        }
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        handle(IProblem.UnusedPrivateType, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
    }

    public void unusedTypeParameter(TypeParameter typeParameter) {
        if (computeSeverity(IProblem.UnusedTypeParameter) == 256) {
            return;
        }
        String[] strArr = {new String(typeParameter.f617name)};
        handle(IProblem.UnusedTypeParameter, strArr, strArr, typeParameter.sourceStart, typeParameter.sourceEnd);
    }

    public void unusedWarningToken(Expression expression) {
        String[] strArr = {expression.constant.stringValue()};
        handle(IProblem.UnusedWarningToken, strArr, strArr, expression.sourceStart, expression.sourceEnd);
    }

    public void useAssertAsAnIdentifier(int i, int i2) {
        handle(IProblem.UseAssertAsAnIdentifier, NoArgument, NoArgument, i, i2);
    }

    public void useEnumAsAnIdentifier(int i, int i2) {
        handle(IProblem.UseEnumAsAnIdentifier, NoArgument, NoArgument, i, i2);
    }

    public void illegalUseOfUnderscoreAsAnIdentifier(int i, int i2, boolean z) {
        this.underScoreIsLambdaParameter = z;
        try {
            handle(IProblem.IllegalUseOfUnderscoreAsAnIdentifier, NoArgument, NoArgument, i, i2);
        } finally {
            this.underScoreIsLambdaParameter = false;
        }
    }

    public void varargsArgumentNeedCast(MethodBinding methodBinding, TypeBinding typeBinding, InvocationSite invocationSite) {
        int severity = this.options.getSeverity(536870976);
        if (severity == 256) {
            return;
        }
        ArrayBinding arrayBinding = (ArrayBinding) methodBinding.parameters[methodBinding.parameters.length - 1];
        if (methodBinding.isConstructor()) {
            handle(IProblem.ConstructorVarargsArgumentNeedCast, new String[]{new String(typeBinding.readableName()), new String(arrayBinding.readableName()), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding, false), new String(arrayBinding.elementsType().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(arrayBinding.shortReadableName()), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding, true), new String(arrayBinding.elementsType().shortReadableName())}, severity, invocationSite.sourceStart(), invocationSite.sourceEnd());
        } else {
            handle(IProblem.MethodVarargsArgumentNeedCast, new String[]{new String(typeBinding.readableName()), new String(arrayBinding.readableName()), new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), new String(arrayBinding.elementsType().readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(arrayBinding.shortReadableName()), new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), new String(arrayBinding.elementsType().shortReadableName())}, severity, invocationSite.sourceStart(), invocationSite.sourceEnd());
        }
    }

    public void varargsConflict(MethodBinding methodBinding, MethodBinding methodBinding2, SourceTypeBinding sourceTypeBinding) {
        handle(IProblem.VarargsConflict, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(methodBinding.declaringClass.readableName()), typesAsString(methodBinding2, false), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(methodBinding.declaringClass.shortReadableName()), typesAsString(methodBinding2, true), new String(methodBinding2.declaringClass.shortReadableName())}, TypeBinding.equalsEquals(methodBinding.declaringClass, sourceTypeBinding) ? methodBinding.sourceStart() : sourceTypeBinding.sourceStart(), TypeBinding.equalsEquals(methodBinding.declaringClass, sourceTypeBinding) ? methodBinding.sourceEnd() : sourceTypeBinding.sourceEnd());
    }

    public void safeVarargsOnFixedArityMethod(MethodBinding methodBinding) {
        String[] strArr = new String[1];
        strArr[0] = new String(methodBinding.isConstructor() ? methodBinding.declaringClass.shortReadableName() : methodBinding.selector);
        handle(IProblem.SafeVarargsOnFixedArityMethod, strArr, strArr, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void safeVarargsOnNonFinalInstanceMethod(MethodBinding methodBinding) {
        String[] strArr = new String[1];
        strArr[0] = new String(methodBinding.isConstructor() ? methodBinding.declaringClass.shortReadableName() : methodBinding.selector);
        handle(IProblem.SafeVarargsOnNonFinalInstanceMethod, strArr, strArr, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void possibleHeapPollutionFromVararg(AbstractVariableDeclaration abstractVariableDeclaration) {
        String[] strArr = {new String(abstractVariableDeclaration.f617name)};
        handle(IProblem.PotentialHeapPollutionFromVararg, strArr, strArr, abstractVariableDeclaration.sourceStart, abstractVariableDeclaration.sourceEnd);
    }

    public void variableTypeCannotBeVoid(AbstractVariableDeclaration abstractVariableDeclaration) {
        String[] strArr = {new String(abstractVariableDeclaration.f617name)};
        handle(IProblem.VariableTypeCannotBeVoid, strArr, strArr, abstractVariableDeclaration.sourceStart, abstractVariableDeclaration.sourceEnd);
    }

    public void variableTypeCannotBeVoidArray(AbstractVariableDeclaration abstractVariableDeclaration) {
        handle(IProblem.CannotAllocateVoidArray, NoArgument, NoArgument, abstractVariableDeclaration.type.sourceStart, abstractVariableDeclaration.type.sourceEnd);
    }

    public void visibilityConflict(MethodBinding methodBinding, MethodBinding methodBinding2) {
        handle(IProblem.MethodReducesVisibility, new String[]{new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(methodBinding2.declaringClass.shortReadableName())}, methodBinding.sourceStart(), methodBinding.sourceEnd());
    }

    public void wildcardAssignment(TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode) {
        handle(IProblem.WildcardFieldAssignment, new String[]{new String(typeBinding2.readableName()), new String(typeBinding.readableName())}, new String[]{new String(typeBinding2.shortReadableName()), new String(typeBinding.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void wildcardInvocation(ASTNode aSTNode, TypeBinding typeBinding, MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        TypeBinding typeBinding2;
        TypeBinding typeBinding3;
        int length = methodBinding.parameters.length;
        int i = 0;
        while (true) {
            if (i < length) {
                typeBinding3 = methodBinding.parameters[i];
                if (typeBinding3.isWildcard() && ((WildcardBinding) typeBinding3).boundKind != 2) {
                    typeBinding2 = typeBindingArr[i];
                    break;
                }
                i++;
            } else {
                typeBinding2 = null;
                typeBinding3 = null;
                break;
            }
        }
        if (methodBinding.isConstructor()) {
            handle(IProblem.WildcardConstructorInvocation, new String[]{new String(typeBinding.sourceName()), typesAsString(methodBinding, false), new String(typeBinding.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding2.readableName()), new String(typeBinding3.readableName())}, new String[]{new String(typeBinding.sourceName()), typesAsString(methodBinding, true), new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding2.shortReadableName()), new String(typeBinding3.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
        } else {
            handle(IProblem.WildcardMethodInvocation, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, false), new String(typeBinding.readableName()), typesAsString(typeBindingArr, false), new String(typeBinding2.readableName()), new String(typeBinding3.readableName())}, new String[]{new String(methodBinding.selector), typesAsString(methodBinding, true), new String(typeBinding.shortReadableName()), typesAsString(typeBindingArr, true), new String(typeBinding2.shortReadableName()), new String(typeBinding3.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void wrongSequenceOfExceptionTypesError(TypeReference typeReference, TypeBinding typeBinding, TypeBinding typeBinding2) {
        handle(IProblem.InvalidCatchBlockSequence, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void wrongSequenceOfExceptionTypes(TypeReference typeReference, TypeBinding typeBinding, TypeBinding typeBinding2) {
        handle(IProblem.InvalidUnionTypeReferenceSequence, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void autoManagedResourcesNotBelow17(LocalDeclaration[] localDeclarationArr) {
        handle(IProblem.AutoManagedResourceNotBelow17, NoArgument, NoArgument, localDeclarationArr[0].declarationSourceStart, localDeclarationArr[localDeclarationArr.length - 1].declarationSourceEnd);
    }

    public void cannotInferElidedTypes(AllocationExpression allocationExpression) {
        String[] strArr = {allocationExpression.type.toString()};
        handle(IProblem.CannotInferElidedTypes, strArr, strArr, allocationExpression.sourceStart, allocationExpression.sourceEnd);
    }

    public void diamondNotWithExplicitTypeArguments(TypeReference[] typeReferenceArr) {
        handle(IProblem.CannotUseDiamondWithExplicitTypeArguments, NoArgument, NoArgument, typeReferenceArr[0].sourceStart, typeReferenceArr[typeReferenceArr.length - 1].sourceEnd);
    }

    public void rawConstructorReferenceNotWithExplicitTypeArguments(TypeReference[] typeReferenceArr) {
        handle(IProblem.IllegalTypeArgumentsInRawConstructorReference, NoArgument, NoArgument, typeReferenceArr[0].sourceStart, typeReferenceArr[typeReferenceArr.length - 1].sourceEnd);
    }

    public void diamondNotWithAnoymousClasses(TypeReference typeReference) {
        handle(IProblem.CannotUseDiamondWithAnonymousClasses, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void redundantSpecificationOfTypeArguments(ASTNode aSTNode, TypeBinding[] typeBindingArr) {
        int i;
        int iComputeSeverity = computeSeverity(IProblem.RedundantSpecificationOfTypeArguments);
        if (iComputeSeverity != 256) {
            if (aSTNode instanceof QualifiedTypeReference) {
                QualifiedTypeReference qualifiedTypeReference = (QualifiedTypeReference) aSTNode;
                i = (int) (qualifiedTypeReference.sourcePositions[qualifiedTypeReference.sourcePositions.length - 1] >> 32);
            } else {
                i = aSTNode.sourceStart;
            }
            handle(IProblem.RedundantSpecificationOfTypeArguments, new String[]{typesAsString(typeBindingArr, false)}, new String[]{typesAsString(typeBindingArr, true)}, iComputeSeverity, i, aSTNode.sourceEnd);
        }
    }

    public void potentiallyUnclosedCloseable(FakedTrackingVariable fakedTrackingVariable, ASTNode aSTNode) {
        String[] strArr = {fakedTrackingVariable.nameForReporting(aSTNode, this.referenceContext)};
        if (aSTNode == null) {
            handle(IProblem.PotentiallyUnclosedCloseable, strArr, strArr, fakedTrackingVariable.sourceStart, fakedTrackingVariable.sourceEnd);
        } else {
            handle(IProblem.PotentiallyUnclosedCloseableAtExit, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void unclosedCloseable(FakedTrackingVariable fakedTrackingVariable, ASTNode aSTNode) {
        String[] strArr = {String.valueOf(fakedTrackingVariable.f617name)};
        if (aSTNode == null) {
            handle(IProblem.UnclosedCloseable, strArr, strArr, fakedTrackingVariable.sourceStart, fakedTrackingVariable.sourceEnd);
        } else {
            handle(IProblem.UnclosedCloseableAtExit, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
        }
    }

    public void explicitlyClosedAutoCloseable(FakedTrackingVariable fakedTrackingVariable) {
        String[] strArr = {String.valueOf(fakedTrackingVariable.f617name)};
        handle(IProblem.ExplicitlyClosedAutoCloseable, strArr, strArr, fakedTrackingVariable.sourceStart, fakedTrackingVariable.sourceEnd);
    }

    public void nullityMismatch(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2, int i, char[][] cArr) {
        if ((i & 2) != 0) {
            nullityMismatchIsNull(expression, typeBinding2);
            return;
        }
        if ((expression instanceof MessageSend) && (((MessageSend) expression).binding.tagBits & 36028797018963968L) != 0) {
            nullityMismatchSpecdNullable(expression, typeBinding2, this.options.nonNullAnnotationName);
            return;
        }
        if ((i & 16) != 0) {
            VariableBinding variableBindingLocalVariableBinding = expression.localVariableBinding();
            if (variableBindingLocalVariableBinding == null && (expression instanceof Reference)) {
                variableBindingLocalVariableBinding = ((Reference) expression).lastFieldBinding();
            }
            if (variableBindingLocalVariableBinding != null && variableBindingLocalVariableBinding.type.isFreeTypeVariable()) {
                nullityMismatchVariableIsFreeTypeVariable(variableBindingLocalVariableBinding, expression);
                return;
            } else if (variableBindingLocalVariableBinding != null && variableBindingLocalVariableBinding.isNullable()) {
                nullityMismatchSpecdNullable(expression, typeBinding2, cArr);
                return;
            } else {
                nullityMismatchPotentiallyNull(expression, typeBinding2, cArr);
                return;
            }
        }
        if (this.options.usesNullTypeAnnotations()) {
            nullityMismatchingTypeAnnotation(expression, typeBinding, typeBinding2, NullAnnotationMatching.NULL_ANNOTATIONS_UNCHECKED);
        } else {
            nullityMismatchIsUnknown(expression, typeBinding, typeBinding2, cArr);
        }
    }

    public void nullityMismatchIsNull(Expression expression, TypeBinding typeBinding) {
        String[] strArr;
        String[] strArr2;
        boolean zUsesNullTypeAnnotations = this.options.usesNullTypeAnnotations();
        int i = (zUsesNullTypeAnnotations && typeBinding.isTypeVariable() && !typeBinding.hasNullTypeAnnotations()) ? 969 : 16778126;
        if (typeBinding instanceof CaptureBinding) {
            CaptureBinding captureBinding = (CaptureBinding) typeBinding;
            if (captureBinding.wildcard != null) {
                typeBinding = captureBinding.wildcard;
            }
        }
        if (!zUsesNullTypeAnnotations) {
            strArr = new String[]{annotatedTypeName(typeBinding, this.options.nonNullAnnotationName)};
            strArr2 = new String[]{shortAnnotatedTypeName(typeBinding, this.options.nonNullAnnotationName)};
        } else if (i == 969) {
            strArr = new String[]{new String(typeBinding.sourceName())};
            strArr2 = new String[]{new String(typeBinding.sourceName())};
        } else {
            strArr = new String[]{new String(typeBinding.nullAnnotatedReadableName(this.options, false))};
            strArr2 = new String[]{new String(typeBinding.nullAnnotatedReadableName(this.options, true))};
        }
        handle(i, strArr, strArr2, expression.sourceStart, expression.sourceEnd);
    }

    public void nullityMismatchSpecdNullable(Expression expression, TypeBinding typeBinding, char[][] cArr) {
        int i;
        char[][] cArr2 = this.options.nullableAnnotationName;
        String[] strArr = {annotatedTypeName(typeBinding, cArr), String.valueOf(CharOperation.concatWith(cArr2, '.'))};
        String[] strArr2 = {shortAnnotatedTypeName(typeBinding, cArr), String.valueOf(cArr2[cArr2.length - 1])};
        if (expression.resolvedType == null || !expression.resolvedType.hasNullTypeAnnotations()) {
            i = IProblem.RequiredNonNullButProvidedSpecdNullable;
        } else {
            strArr[1] = String.valueOf(expression.resolvedType.nullAnnotatedReadableName(this.options, false));
            strArr2[1] = String.valueOf(expression.resolvedType.nullAnnotatedReadableName(this.options, true));
            i = IProblem.NullityMismatchingTypeAnnotation;
        }
        handle(i, strArr, strArr2, expression.sourceStart, expression.sourceEnd);
    }

    public void nullityMismatchPotentiallyNull(Expression expression, TypeBinding typeBinding, char[][] cArr) {
        char[][] cArr2 = this.options.nullableAnnotationName;
        handle(IProblem.RequiredNonNullButProvidedPotentialNull, new String[]{annotatedTypeName(typeBinding, cArr), String.valueOf(CharOperation.concatWith(cArr2, '.'))}, new String[]{shortAnnotatedTypeName(typeBinding, cArr), String.valueOf(cArr2[cArr2.length - 1])}, expression.sourceStart, expression.sourceEnd);
    }

    public void nullityMismatchIsUnknown(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2, char[][] cArr) {
        handle(IProblem.RequiredNonNullButProvidedUnknown, new String[]{String.valueOf(typeBinding.readableName()), annotatedTypeName(typeBinding2, cArr)}, new String[]{String.valueOf(typeBinding.shortReadableName()), shortAnnotatedTypeName(typeBinding2, cArr)}, expression.sourceStart, expression.sourceEnd);
    }

    private void nullityMismatchIsFreeTypeVariable(TypeBinding typeBinding, int i, int i2) {
        char[][] cArr = this.options.nullableAnnotationName;
        char[][] cArr2 = this.options.nonNullAnnotationName;
        String[] strArr = {new String(cArr2[cArr2.length - 1]), new String(typeBinding.readableName()), new String(cArr[cArr.length - 1])};
        handle(IProblem.RequiredNonNullButProvidedFreeTypeVariable, strArr, strArr, i, i2);
    }

    public void nullityMismatchVariableIsFreeTypeVariable(VariableBinding variableBinding, ASTNode aSTNode) {
        if (computeSeverity(IProblem.RequiredNonNullButProvidedFreeTypeVariable) == 256) {
            return;
        }
        nullityMismatchIsFreeTypeVariable(variableBinding.type, nodeSourceStart(variableBinding, aSTNode), nodeSourceEnd(variableBinding, aSTNode));
    }

    public void illegalRedefinitionToNonNullParameter(Argument argument, ReferenceBinding referenceBinding, char[][] cArr) {
        int i = argument.type.sourceStart;
        if (argument.annotations != null) {
            for (int i2 = 0; i2 < argument.annotations.length; i2++) {
                Annotation annotation = argument.annotations[i2];
                if (annotation.hasNullBit(96)) {
                    i = annotation.sourceStart;
                    break;
                }
            }
        }
        int i3 = i;
        if (cArr == null) {
            handle(IProblem.IllegalDefinitionToNonNullParameter, new String[]{new String(argument.f617name), new String(referenceBinding.readableName())}, new String[]{new String(argument.f617name), new String(referenceBinding.shortReadableName())}, i3, argument.type.sourceEnd);
        } else {
            handle(IProblem.IllegalRedefinitionToNonNullParameter, new String[]{new String(argument.f617name), new String(referenceBinding.readableName()), CharOperation.toString(cArr)}, new String[]{new String(argument.f617name), new String(referenceBinding.shortReadableName()), new String(cArr[cArr.length - 1])}, i3, argument.type.sourceEnd);
        }
    }

    public void parameterLackingNullableAnnotation(Argument argument, ReferenceBinding referenceBinding, char[][] cArr) {
        handle(IProblem.ParameterLackingNullableAnnotation, new String[]{new String(referenceBinding.readableName()), CharOperation.toString(cArr)}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr[cArr.length - 1])}, argument.type.sourceStart, argument.type.sourceEnd);
    }

    public void parameterLackingNonnullAnnotation(Argument argument, ReferenceBinding referenceBinding, char[][] cArr) {
        int i;
        int i2;
        int i3;
        int i4;
        if (argument != null) {
            i3 = argument.type.sourceStart;
            i4 = argument.type.sourceEnd;
        } else {
            ReferenceContext referenceContext = this.referenceContext;
            if (referenceContext instanceof TypeDeclaration) {
                i3 = ((TypeDeclaration) referenceContext).sourceStart;
                i4 = ((TypeDeclaration) this.referenceContext).sourceEnd;
            } else {
                i = 0;
                i2 = 0;
            }
            handle(IProblem.ParameterLackingNonNullAnnotation, new String[]{new String(referenceBinding.readableName()), CharOperation.toString(cArr)}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr[cArr.length - 1])}, i, i2);
        }
        i2 = i4;
        i = i3;
        handle(IProblem.ParameterLackingNonNullAnnotation, new String[]{new String(referenceBinding.readableName()), CharOperation.toString(cArr)}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr[cArr.length - 1])}, i, i2);
    }

    public void illegalParameterRedefinition(Argument argument, ReferenceBinding referenceBinding, TypeBinding typeBinding) {
        int i = argument.type.sourceStart;
        if (argument.annotations != null) {
            for (int i2 = 0; i2 < argument.annotations.length; i2++) {
                Annotation annotation = argument.annotations[i2];
                if (annotation.hasNullBit(96)) {
                    i = annotation.sourceStart;
                    break;
                }
            }
        }
        handle(IProblem.IllegalParameterNullityRedefinition, new String[]{new String(argument.f617name), new String(referenceBinding.readableName()), new String(typeBinding.nullAnnotatedReadableName(this.options, false))}, new String[]{new String(argument.f617name), new String(referenceBinding.shortReadableName()), new String(typeBinding.nullAnnotatedReadableName(this.options, true))}, i, argument.type.sourceEnd);
    }

    public void illegalReturnRedefinition(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, char[][] cArr) {
        int i;
        MethodDeclaration methodDeclaration = (MethodDeclaration) abstractMethodDeclaration;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(methodBinding.declaringClass.readableName()).append('.').append(methodBinding.readableName());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(methodBinding.declaringClass.shortReadableName()).append('.').append(methodBinding.shortReadableName());
        int i2 = methodDeclaration.returnType.sourceStart;
        Annotation annotationFindAnnotation = findAnnotation(methodDeclaration.annotations, 64);
        if (annotationFindAnnotation != null) {
            i2 = annotationFindAnnotation.sourceStart;
        }
        TypeBinding typeBinding = methodBinding.returnType;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        if (this.options.usesNullTypeAnnotations()) {
            if (typeBinding.isTypeVariable() && (typeBinding.tagBits & TagBits.AnnotationNullMASK) == 0) {
                sb.append(typeBinding.readableName());
                sb2.append(typeBinding.shortReadableName());
                i = IProblem.IllegalReturnNullityRedefinitionFreeTypeVariable;
            } else {
                sb.append(typeBinding.nullAnnotatedReadableName(this.options, false));
                sb2.append(typeBinding.nullAnnotatedReadableName(this.options, true));
            }
            handle(i, new String[]{stringBuffer.toString(), sb.toString()}, new String[]{stringBuffer2.toString(), sb2.toString()}, i2, methodDeclaration.returnType.sourceEnd);
        }
        sb.append('@').append(CharOperation.concatWith(cArr, '.'));
        sb.append(StringUtil.SPACE).append(typeBinding.readableName());
        sb2.append('@').append(cArr[cArr.length - 1]);
        sb2.append(StringUtil.SPACE).append(typeBinding.shortReadableName());
        i = IProblem.IllegalReturnNullityRedefinition;
        handle(i, new String[]{stringBuffer.toString(), sb.toString()}, new String[]{stringBuffer2.toString(), sb2.toString()}, i2, methodDeclaration.returnType.sourceEnd);
    }

    public void referenceExpressionArgumentNullityMismatch(ReferenceExpression referenceExpression, TypeBinding typeBinding, TypeBinding typeBinding2, MethodBinding methodBinding, int i, NullAnnotationMatching nullAnnotationMatching) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(methodBinding.declaringClass.readableName()).append('.').append(methodBinding.readableName());
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(methodBinding.declaringClass.shortReadableName()).append('.').append(methodBinding.shortReadableName());
        int i2 = i + 1;
        handle(nullAnnotationMatching.isUnchecked() ? IProblem.ReferenceExpressionParameterNullityUnchecked : IProblem.ReferenceExpressionParameterNullityMismatch, new String[]{String.valueOf(i2), String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false)), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, false)), stringBuffer.toString()}, new String[]{String.valueOf(i2), String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true)), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, true)), stringBuffer2.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void illegalReturnRedefinition(ASTNode aSTNode, MethodBinding methodBinding, boolean z, TypeBinding typeBinding) {
        handle(z ? IProblem.ReferenceExpressionReturnNullRedefUnchecked : IProblem.ReferenceExpressionReturnNullRedef, new String[]{new StringBuffer().append(methodBinding.declaringClass.readableName()).append('.').append(methodBinding.readableName()).toString(), String.valueOf(methodBinding.returnType.nullAnnotatedReadableName(this.options, false)), String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false))}, new String[]{new StringBuffer().append(methodBinding.declaringClass.shortReadableName()).append('.').append(methodBinding.shortReadableName()).toString(), String.valueOf(methodBinding.returnType.nullAnnotatedReadableName(this.options, true)), String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true))}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void messageSendPotentialNullReference(MethodBinding methodBinding, ASTNode aSTNode) {
        String[] strArr = {new String(methodBinding.readableName())};
        handle(IProblem.PotentialNullMessageSendReference, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void messageSendRedundantCheckOnNonNull(MethodBinding methodBinding, ASTNode aSTNode) {
        String[] strArr = {new String(methodBinding.readableName())};
        handle(IProblem.RedundantNullCheckOnNonNullMessageSend, strArr, strArr, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void expressionNullReference(ASTNode aSTNode) {
        handle(IProblem.NullExpressionReference, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void expressionPotentialNullReference(ASTNode aSTNode) {
        handle(IProblem.PotentialNullExpressionReference, NoArgument, NoArgument, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void cannotImplementIncompatibleNullness(MethodBinding methodBinding, MethodBinding methodBinding2, boolean z) {
        int i;
        int i2;
        String str;
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof TypeDeclaration) {
            i = ((TypeDeclaration) referenceContext).sourceStart;
            i2 = ((TypeDeclaration) this.referenceContext).sourceEnd;
        } else {
            i = 0;
            i2 = 0;
        }
        String[] strArr = new String[5];
        String str2 = "";
        if (!z) {
            str = "";
        } else {
            str = String.valueOf(new String(methodBinding.returnType.nullAnnotatedReadableName(this.options, false))) + StringUtil.SPACE;
        }
        strArr[0] = str;
        strArr[1] = new String(methodBinding.selector);
        strArr[2] = typesAsString(methodBinding, false, true);
        strArr[3] = new String(methodBinding.declaringClass.readableName());
        strArr[4] = new String(methodBinding2.declaringClass.readableName());
        String[] strArr2 = new String[5];
        if (z) {
            str2 = String.valueOf(new String(methodBinding.returnType.nullAnnotatedReadableName(this.options, true))) + StringUtil.SPACE;
        }
        strArr2[0] = str2;
        strArr2[1] = new String(methodBinding.selector);
        strArr2[2] = typesAsString(methodBinding, true, true);
        strArr2[3] = new String(methodBinding.declaringClass.shortReadableName());
        strArr2[4] = new String(methodBinding2.declaringClass.shortReadableName());
        handle(IProblem.CannotImplementIncompatibleNullness, strArr, strArr2, i, i2);
    }

    public void nullAnnotationIsRedundant(AbstractMethodDeclaration abstractMethodDeclaration, int i) {
        int i2;
        int i3;
        if (i == -1) {
            MethodDeclaration methodDeclaration = (MethodDeclaration) abstractMethodDeclaration;
            Annotation annotationFindAnnotation = findAnnotation(methodDeclaration.annotations, 32);
            i2 = annotationFindAnnotation != null ? annotationFindAnnotation.sourceStart : methodDeclaration.returnType.sourceStart;
            i3 = methodDeclaration.returnType.sourceEnd;
        } else {
            Argument argument = abstractMethodDeclaration.arguments[i];
            i2 = argument.declarationSourceStart;
            i3 = argument.sourceEnd;
        }
        handle(IProblem.RedundantNullAnnotation, ProblemHandler.NoArgument, ProblemHandler.NoArgument, i2, i3);
    }

    public void nullAnnotationIsRedundant(FieldDeclaration fieldDeclaration) {
        Annotation annotationFindAnnotation = findAnnotation(fieldDeclaration.annotations, 32);
        int i = annotationFindAnnotation != null ? annotationFindAnnotation.sourceStart : fieldDeclaration.type.sourceStart;
        handle(IProblem.RedundantNullAnnotation, ProblemHandler.NoArgument, ProblemHandler.NoArgument, i, fieldDeclaration.type.sourceEnd);
    }

    public void nullDefaultAnnotationIsRedundant(ASTNode aSTNode, Annotation[] annotationArr, Binding binding) {
        String[] strArr;
        String[] strArr2;
        int i;
        Annotation annotationFindAnnotation = findAnnotation(annotationArr, 128);
        int i2 = annotationFindAnnotation != null ? annotationFindAnnotation.sourceStart : aSTNode.sourceStart;
        int i3 = annotationFindAnnotation != null ? annotationFindAnnotation.sourceEnd : aSTNode.sourceStart;
        String[] strArr3 = NoArgument;
        String[] strArr4 = NoArgument;
        if (binding != null) {
            String[] strArr5 = {new String(binding.readableName())};
            strArr2 = new String[]{new String(binding.shortReadableName())};
            strArr = strArr5;
        } else {
            strArr = strArr3;
            strArr2 = strArr4;
        }
        if (binding instanceof PackageBinding) {
            i = IProblem.RedundantNullDefaultAnnotationPackage;
        } else if (binding instanceof ReferenceBinding) {
            i = IProblem.RedundantNullDefaultAnnotationType;
        } else {
            i = binding instanceof MethodBinding ? IProblem.RedundantNullDefaultAnnotationMethod : IProblem.RedundantNullDefaultAnnotation;
        }
        handle(i, strArr, strArr2, i2, i3);
    }

    public void contradictoryNullAnnotations(Annotation annotation) {
        contradictoryNullAnnotations(annotation.sourceStart, annotation.sourceEnd);
    }

    public void contradictoryNullAnnotations(Annotation[] annotationArr) {
        contradictoryNullAnnotations(annotationArr[0].sourceStart, annotationArr[annotationArr.length - 1].sourceEnd);
    }

    public void contradictoryNullAnnotations(int i, int i2) {
        char[][] cArr = this.options.nonNullAnnotationName;
        char[][] cArr2 = this.options.nullableAnnotationName;
        handle(IProblem.ContradictoryNullAnnotations, new String[]{new String(CharOperation.concatWith(cArr, '.')), new String(CharOperation.concatWith(cArr2, '.'))}, new String[]{new String(cArr[cArr.length - 1]), new String(cArr2[cArr2.length - 1])}, i, i2);
    }

    public void contradictoryNullAnnotationsInferred(MethodBinding methodBinding, ASTNode aSTNode) {
        contradictoryNullAnnotationsInferred(methodBinding, aSTNode.sourceStart, aSTNode.sourceEnd, false);
    }

    public void contradictoryNullAnnotationsInferred(MethodBinding methodBinding, int i, int i2, boolean z) {
        char[][] cArr = this.options.nonNullAnnotationName;
        char[][] cArr2 = this.options.nullableAnnotationName;
        handle(z ? IProblem.ContradictoryNullAnnotationsInferredFunctionType : IProblem.ContradictoryNullAnnotationsInferred, new String[]{new String(CharOperation.concatWith(cArr, '.')), new String(CharOperation.concatWith(cArr2, '.')), new String(methodBinding.returnType.nullAnnotatedReadableName(this.options, false)), new String(methodBinding.selector), typesAsString(methodBinding, false, true)}, new String[]{new String(cArr[cArr.length - 1]), new String(cArr2[cArr2.length - 1]), new String(methodBinding.returnType.nullAnnotatedReadableName(this.options, true)), new String(methodBinding.selector), typesAsString(methodBinding, true, true)}, i, i2);
    }

    public void contradictoryNullAnnotationsOnBounds(Annotation annotation, long j) {
        char[][] cArr = j == 72057594037927936L ? this.options.nonNullAnnotationName : this.options.nullableAnnotationName;
        handle(IProblem.ContradictoryNullAnnotationsOnBound, new String[]{new String(CharOperation.concatWith(cArr, '.'))}, new String[]{new String(cArr[cArr.length - 1])}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void conflictingNullAnnotations(MethodBinding methodBinding, ASTNode aSTNode, MethodBinding methodBinding2) {
        char[][] cArr = this.options.nonNullAnnotationName;
        char[][] cArr2 = this.options.nullableAnnotationName;
        handle(IProblem.ConflictingNullAnnotations, new String[]{new String(CharOperation.concatWith(cArr, '.')), new String(CharOperation.concatWith(cArr2, '.')), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(cArr[cArr.length - 1]), new String(cArr2[cArr2.length - 1]), new String(methodBinding2.declaringClass.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void conflictingInheritedNullAnnotations(ASTNode aSTNode, boolean z, MethodBinding methodBinding, boolean z2, MethodBinding methodBinding2) {
        char[][] cArr = z ? this.options.nonNullAnnotationName : this.options.nullableAnnotationName;
        char[][] cArr2 = z2 ? this.options.nonNullAnnotationName : this.options.nullableAnnotationName;
        handle(IProblem.ConflictingInheritedNullAnnotations, new String[]{new String(CharOperation.concatWith(cArr, '.')), new String(methodBinding.declaringClass.readableName()), new String(CharOperation.concatWith(cArr2, '.')), new String(methodBinding2.declaringClass.readableName())}, new String[]{new String(cArr[cArr.length - 1]), new String(methodBinding.declaringClass.shortReadableName()), new String(cArr2[cArr2.length - 1]), new String(methodBinding2.declaringClass.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalAnnotationForBaseType(TypeReference typeReference, Annotation[] annotationArr, long j) {
        char[][] cArr;
        int i = j == 36028797018963968L ? 64 : 32;
        if (j == 72057594037927936L) {
            cArr = this.options.nonNullAnnotationName;
        } else {
            cArr = this.options.nullableAnnotationName;
        }
        String[] strArr = {new String(cArr[cArr.length - 1]), new String(typeReference.getLastToken())};
        Annotation annotationFindAnnotation = findAnnotation(annotationArr, i);
        handle(IProblem.IllegalAnnotationForBaseType, strArr, strArr, annotationFindAnnotation != null ? annotationFindAnnotation.sourceStart : typeReference.sourceStart, annotationFindAnnotation != null ? annotationFindAnnotation.sourceEnd : typeReference.sourceEnd);
    }

    public void illegalAnnotationForBaseType(Annotation annotation, TypeBinding typeBinding) {
        String[] strArr = {new String(annotation.resolvedType.shortReadableName()), new String(typeBinding.readableName())};
        handle(IProblem.IllegalAnnotationForBaseType, strArr, strArr, annotation.sourceStart, annotation.sourceEnd);
    }

    private String annotatedTypeName(TypeBinding typeBinding, char[][] cArr) {
        if ((typeBinding.tagBits & TagBits.AnnotationNullMASK) != 0) {
            return String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false));
        }
        return internalAnnotatedTypeName(CharOperation.concatWith(cArr, '.'), typeBinding.readableName(), 0);
    }

    private String shortAnnotatedTypeName(TypeBinding typeBinding, char[][] cArr) {
        if ((typeBinding.tagBits & TagBits.AnnotationNullMASK) != 0) {
            return String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true));
        }
        return internalAnnotatedTypeName(cArr[cArr.length - 1], typeBinding.shortReadableName(), 0);
    }

    String internalAnnotatedTypeName(char[] cArr, char[] cArr2, int i) {
        char[] cArr3;
        if (i > 0) {
            int length = cArr.length + cArr2.length + 2;
            cArr3 = new char[(i * 2) + length];
            System.arraycopy(cArr2, 0, cArr3, 0, cArr2.length);
            cArr3[cArr2.length] = StringUtil.SPACE;
            cArr3[cArr2.length + 1] = '@';
            System.arraycopy(cArr, 0, cArr3, cArr2.length + 2, cArr.length);
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = length + i2;
                cArr3[i3] = '[';
                cArr3[i3 + 1] = ']';
            }
        } else {
            cArr3 = new char[cArr.length + cArr2.length + 2];
            cArr3[0] = '@';
            System.arraycopy(cArr, 0, cArr3, 1, cArr.length);
            cArr3[cArr.length + 1] = StringUtil.SPACE;
            System.arraycopy(cArr2, 0, cArr3, cArr.length + 2, cArr2.length);
        }
        return String.valueOf(cArr3);
    }

    private Annotation findAnnotation(Annotation[] annotationArr, int i) {
        if (annotationArr == null) {
            return null;
        }
        int length = annotationArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (annotationArr[i2].hasNullBit(i)) {
                return annotationArr[i2];
            }
        }
        return null;
    }

    public void missingNonNullByDefaultAnnotation(TypeDeclaration typeDeclaration) {
        CompilationUnitDeclaration compilationUnitDeclaration = typeDeclaration.getCompilationUnitDeclaration();
        if (compilationUnitDeclaration.currentPackage == null) {
            int iComputeSeverity = computeSeverity(IProblem.MissingNonNullByDefaultAnnotationOnType);
            if (iComputeSeverity == 256) {
                return;
            }
            SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
            handle(IProblem.MissingNonNullByDefaultAnnotationOnType, new String[]{new String(sourceTypeBinding.readableName())}, new String[]{new String(sourceTypeBinding.shortReadableName())}, iComputeSeverity, typeDeclaration.sourceStart, typeDeclaration.sourceEnd);
            return;
        }
        int iComputeSeverity2 = computeSeverity(IProblem.MissingNonNullByDefaultAnnotationOnPackage);
        if (iComputeSeverity2 == 256) {
            return;
        }
        String[] strArr = {CharOperation.toString(compilationUnitDeclaration.currentPackage.tokens)};
        handle(IProblem.MissingNonNullByDefaultAnnotationOnPackage, strArr, strArr, iComputeSeverity2, compilationUnitDeclaration.currentPackage.sourceStart, compilationUnitDeclaration.currentPackage.sourceEnd);
    }

    public void illegalModifiersForElidedType(Argument argument) {
        String[] strArr = {new String(argument.f617name)};
        handle(IProblem.IllegalModifiersForElidedType, strArr, strArr, argument.declarationSourceStart, argument.declarationSourceEnd);
    }

    public void illegalModifiers(int i, int i2) {
        handle(IProblem.IllegalModifiers, NoArgument, NoArgument, i, i2);
    }

    public void arrayReferencePotentialNullReference(ArrayReference arrayReference) {
        handle(IProblem.ArrayReferencePotentialNullReference, NoArgument, NoArgument, arrayReference.sourceStart, arrayReference.sourceEnd);
    }

    public void nullityMismatchingTypeAnnotation(Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2, NullAnnotationMatching nullAnnotationMatching) {
        int i;
        String strSuperTypeHintName;
        String str;
        String str2;
        String str3;
        String[] strArr;
        String[] strArr2;
        if (typeBinding.f626id == 12 || nullAnnotationMatching.nullStatus == 2) {
            nullityMismatchIsNull(expression, typeBinding2);
            return;
        }
        if (nullAnnotationMatching.isPotentiallyNullMismatch() && (typeBinding2.tagBits & 72057594037927936L) != 0 && (typeBinding.tagBits & 36028797018963968L) == 0) {
            if (this.options.pessimisticNullAnalysisForFreeTypeVariablesEnabled && typeBinding.isTypeVariable() && !typeBinding.hasNullTypeAnnotations()) {
                nullityMismatchIsFreeTypeVariable(typeBinding, expression.sourceStart, expression.sourceEnd);
                return;
            } else {
                nullityMismatchPotentiallyNull(expression, typeBinding2, this.options.nonNullAnnotationName);
                return;
            }
        }
        if (nullAnnotationMatching.superTypeHint != null && typeBinding2.isParameterizedType()) {
            int i2 = nullAnnotationMatching.isUnchecked() ? IProblem.NullityUncheckedTypeAnnotationDetailSuperHint : IProblem.NullityMismatchingTypeAnnotationSuperHint;
            String strSuperTypeHintName2 = nullAnnotationMatching.superTypeHintName(this.options, false);
            strSuperTypeHintName = nullAnnotationMatching.superTypeHintName(this.options, true);
            i = i2;
            str = strSuperTypeHintName2;
        } else {
            if (nullAnnotationMatching.isUnchecked()) {
                i = IProblem.NullityUncheckedTypeAnnotationDetail;
            } else {
                i = (!typeBinding2.isTypeVariable() || typeBinding2.hasNullTypeAnnotations()) ? IProblem.NullityMismatchingTypeAnnotation : 970;
            }
            if (i == 970) {
                String[] strArr3 = new String[3];
                new String(typeBinding2.sourceName());
                String[] strArr4 = new String[3];
                new String(typeBinding2.sourceName());
            } else {
                String[] strArr5 = new String[2];
                String[] strArr6 = new String[2];
            }
            strSuperTypeHintName = null;
            str = null;
        }
        if (i == 970) {
            str2 = new String(typeBinding2.sourceName());
            str3 = new String(typeBinding2.sourceName());
        } else {
            str2 = new String(typeBinding2.nullAnnotatedReadableName(this.options, false));
            str3 = new String(typeBinding2.nullAnnotatedReadableName(this.options, true));
        }
        String strValueOf = String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false));
        String strValueOf2 = String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true));
        if (str != null) {
            String[] strArr7 = {str2, strValueOf, str};
            strArr = new String[]{str3, strValueOf2, strSuperTypeHintName};
            strArr2 = strArr7;
        } else {
            strArr = new String[]{str3, strValueOf2};
            strArr2 = new String[]{str2, strValueOf};
        }
        handle(i, strArr2, strArr, expression.sourceStart, expression.sourceEnd);
    }

    public void nullityMismatchTypeArgument(TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode) {
        handle(IProblem.NullityMismatchTypeArgument, new String[]{String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false)), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, false))}, new String[]{String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true)), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, true))}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void cannotRedefineTypeArgumentNullity(TypeBinding typeBinding, Binding binding, ASTNode aSTNode) {
        String[] strArr = new String[2];
        String[] strArr2 = new String[2];
        strArr[0] = String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, false));
        strArr2[0] = String.valueOf(typeBinding.nullAnnotatedReadableName(this.options, true));
        if (binding instanceof MethodBinding) {
            ReferenceBinding referenceBinding = ((MethodBinding) binding).declaringClass;
            strArr[1] = String.valueOf(CharOperation.concat(referenceBinding.readableName(), binding.shortReadableName(), '.'));
            strArr2[1] = String.valueOf(CharOperation.concat(referenceBinding.shortReadableName(), binding.shortReadableName(), '.'));
        } else {
            strArr[1] = String.valueOf(binding.readableName());
            strArr2[1] = String.valueOf(binding.shortReadableName());
        }
        handle(975, strArr, strArr2, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void implicitObjectBoundNoNullDefault(TypeReference typeReference) {
        handle(IProblem.ImplicitObjectBoundNoNullDefault, NoArgument, NoArgument, 0, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void nonNullTypeVariableInUnannotatedBinary(LookupEnvironment lookupEnvironment, MethodBinding methodBinding, Expression expression, int i) {
        TypeBinding typeBinding = methodBinding.original().returnType;
        int iComputeSeverity = computeSeverity(IProblem.NonNullTypeVariableFromLegacyMethod);
        int i2 = (iComputeSeverity & 1281) == 0 ? i : iComputeSeverity;
        if (typeBinding instanceof TypeVariableBinding) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
            ReferenceBinding referenceBinding = methodBinding.declaringClass;
            char[][] cArr = this.options.nonNullAnnotationName;
            String strValueOf = String.valueOf(cArr[cArr.length - 1]);
            if (typeVariableBinding.declaringElement instanceof ReferenceBinding) {
                handle(IProblem.NonNullTypeVariableFromLegacyMethod, new String[]{strValueOf, String.valueOf(referenceBinding.nullAnnotatedReadableName(this.options, false)), String.valueOf(referenceBinding.original().readableName())}, new String[]{strValueOf, String.valueOf(referenceBinding.nullAnnotatedReadableName(this.options, true)), String.valueOf(referenceBinding.original().shortReadableName())}, i2, expression.sourceStart, expression.sourceEnd);
            } else if ((typeVariableBinding.declaringElement instanceof MethodBinding) && (methodBinding instanceof ParameterizedGenericMethodBinding)) {
                TypeBinding typeBinding2 = ((ParameterizedGenericMethodBinding) methodBinding).typeArguments[typeVariableBinding.rank];
                handle(IProblem.NonNullMethodTypeVariableFromLegacyMethod, new String[]{strValueOf, String.valueOf(typeVariableBinding.readableName()), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, false)), String.valueOf(referenceBinding.original().readableName())}, new String[]{strValueOf, String.valueOf(typeVariableBinding.shortReadableName()), String.valueOf(typeBinding2.nullAnnotatedReadableName(this.options, true)), String.valueOf(referenceBinding.original().shortReadableName())}, i2, expression.sourceStart, expression.sourceEnd);
            }
        }
    }

    public void dereferencingNullableExpression(Expression expression) {
        if (expression instanceof MessageSend) {
            MessageSend messageSend = (MessageSend) expression;
            messageSendPotentialNullReference(messageSend.binding, messageSend);
        } else {
            char[][] cArr = this.options.nullableAnnotationName;
            String[] strArr = {String.valueOf(cArr[cArr.length - 1])};
            handle(IProblem.DereferencingNullableExpression, strArr, strArr, nodeSourceStart(expression), nodeSourceEnd(expression));
        }
    }

    public void dereferencingNullableExpression(long j, LookupEnvironment lookupEnvironment) {
        char[][] nullableAnnotationName = lookupEnvironment.getNullableAnnotationName();
        String[] strArr = {String.valueOf(nullableAnnotationName[nullableAnnotationName.length - 1])};
        handle(IProblem.DereferencingNullableExpression, strArr, strArr, (int) (j >>> 32), (int) (j & WebSocketProtocol.PAYLOAD_SHORT_MAX));
    }

    public void onlyReferenceTypesInIntersectionCast(TypeReference typeReference) {
        handle(IProblem.IllegalBasetypeInIntersectionCast, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void illegalArrayTypeInIntersectionCast(TypeReference typeReference) {
        handle(IProblem.IllegalArrayTypeInIntersectionCast, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void intersectionCastNotBelow18(TypeReference[] typeReferenceArr) {
        handle(IProblem.IntersectionCastNotBelow18, NoArgument, NoArgument, typeReferenceArr[0].sourceStart, typeReferenceArr[typeReferenceArr.length - 1].sourceEnd);
    }

    public void duplicateBoundInIntersectionCast(TypeReference typeReference) {
        handle(IProblem.DuplicateBoundInIntersectionCast, NoArgument, NoArgument, typeReference.sourceStart, typeReference.sourceEnd);
    }

    public void lambdaRedeclaresArgument(Argument argument) {
        String[] strArr = {new String(argument.f617name)};
        handle(IProblem.LambdaRedeclaresArgument, strArr, strArr, argument.sourceStart, argument.sourceEnd);
    }

    public void lambdaRedeclaresLocal(LocalDeclaration localDeclaration) {
        String[] strArr = {new String(localDeclaration.f617name)};
        handle(IProblem.LambdaRedeclaresLocal, strArr, strArr, localDeclaration.sourceStart, localDeclaration.sourceEnd);
    }

    public void descriptorHasInvisibleType(FunctionalExpression functionalExpression, ReferenceBinding referenceBinding) {
        handle(99, new String[]{new String(referenceBinding.readableName())}, new String[]{new String(referenceBinding.shortReadableName())}, functionalExpression.sourceStart, functionalExpression.diagnosticsSourceEnd());
    }

    public void methodReferenceSwingsBothWays(ReferenceExpression referenceExpression, MethodBinding methodBinding, MethodBinding methodBinding2) {
        char[] cArr = methodBinding.selector;
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        StringBuffer stringBuffer4 = new StringBuffer();
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        int length2 = typeBindingArr2.length;
        for (int i2 = 0; i2 < length2; i2++) {
            if (i2 != 0) {
                stringBuffer3.append(", ");
                stringBuffer4.append(", ");
            }
            stringBuffer3.append(new String(typeBindingArr2[i2].readableName()));
            stringBuffer4.append(new String(typeBindingArr2[i2].shortReadableName()));
        }
        handle(IProblem.MethodReferenceSwingsBothWays, new String[]{new String(referenceBinding.readableName()), new String(cArr), stringBuffer.toString(), new String(cArr), stringBuffer3.toString()}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr), stringBuffer2.toString(), new String(cArr), stringBuffer4.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void methodMustBeAccessedStatically(ReferenceExpression referenceExpression, MethodBinding methodBinding) {
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        char[] cArr = methodBinding.selector;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(IProblem.StaticMethodShouldBeAccessedStatically, new String[]{new String(referenceBinding.readableName()), new String(cArr), stringBuffer.toString()}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr), stringBuffer2.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void methodMustBeAccessedWithInstance(ReferenceExpression referenceExpression, MethodBinding methodBinding) {
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        char[] cArr = methodBinding.selector;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(IProblem.StaticMethodRequested, new String[]{new String(referenceBinding.readableName()), new String(cArr), stringBuffer.toString()}, new String[]{new String(referenceBinding.shortReadableName()), new String(cArr), stringBuffer2.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void invalidArrayConstructorReference(ReferenceExpression referenceExpression, TypeBinding typeBinding, TypeBinding[] typeBindingArr) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(IProblem.InvalidArrayConstructorReference, new String[]{new String(typeBinding.readableName()), stringBuffer.toString()}, new String[]{new String(typeBinding.shortReadableName()), stringBuffer2.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void constructedArrayIncompatible(ReferenceExpression referenceExpression, TypeBinding typeBinding, TypeBinding typeBinding2) {
        handle(IProblem.ConstructedArrayIncompatible, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void danglingReference(ReferenceExpression referenceExpression, TypeBinding typeBinding, char[] cArr, TypeBinding[] typeBindingArr) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        handle(IProblem.DanglingReference, new String[]{new String(typeBinding.readableName()), new String(cArr), stringBuffer.toString()}, new String[]{new String(typeBinding.shortReadableName()), new String(cArr), stringBuffer2.toString()}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void unhandledException(TypeBinding typeBinding, ReferenceExpression referenceExpression) {
        handle(IProblem.UnhandledException, new String[]{new String(typeBinding.readableName())}, new String[]{new String(typeBinding.shortReadableName())}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void incompatibleReturnType(ReferenceExpression referenceExpression, MethodBinding methodBinding, TypeBinding typeBinding) {
        if (methodBinding.isConstructor()) {
            handle(IProblem.ConstructionTypeMismatch, new String[]{new String(methodBinding.declaringClass.readableName()), new String(typeBinding.readableName())}, new String[]{new String(methodBinding.declaringClass.shortReadableName()), new String(typeBinding.shortReadableName())}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
                stringBuffer2.append(", ");
            }
            stringBuffer.append(new String(typeBindingArr[i].readableName()));
            stringBuffer2.append(new String(typeBindingArr[i].shortReadableName()));
        }
        String str = new String(methodBinding.selector);
        handle(IProblem.IncompatibleMethodReference, new String[]{str, stringBuffer.toString(), new String(methodBinding.declaringClass.readableName()), new String(methodBinding.returnType.readableName()), new String(typeBinding.readableName())}, new String[]{str, stringBuffer2.toString(), new String(methodBinding.declaringClass.shortReadableName()), new String(methodBinding.returnType.shortReadableName()), new String(typeBinding.shortReadableName())}, referenceExpression.sourceStart, referenceExpression.sourceEnd);
    }

    public void illegalSuperAccess(TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode) {
        if (typeBinding2.problemId() == 29) {
            interfaceSuperInvocationNotBelow18((QualifiedSuperReference) aSTNode);
            return;
        }
        if (typeBinding2.problemId() != 21) {
            needImplementation(aSTNode);
        }
        handle(IProblem.SuperAccessCannotBypassDirectSuper, new String[]{String.valueOf(typeBinding.readableName()), String.valueOf(typeBinding2.readableName())}, new String[]{String.valueOf(typeBinding.shortReadableName()), String.valueOf(typeBinding2.shortReadableName())}, aSTNode.sourceStart, aSTNode.sourceEnd);
    }

    public void illegalSuperCallBypassingOverride(InvocationSite invocationSite, MethodBinding methodBinding, ReferenceBinding referenceBinding) {
        handle(IProblem.SuperCallCannotBypassOverride, new String[]{String.valueOf(methodBinding.readableName()), String.valueOf(methodBinding.declaringClass.readableName()), String.valueOf(referenceBinding.readableName())}, new String[]{String.valueOf(methodBinding.shortReadableName()), String.valueOf(methodBinding.declaringClass.shortReadableName()), String.valueOf(referenceBinding.shortReadableName())}, invocationSite.sourceStart(), invocationSite.sourceEnd());
    }

    public void disallowedTargetForContainerAnnotation(Annotation annotation, TypeBinding typeBinding) {
        handle(IProblem.DisallowedTargetForContainerAnnotationType, new String[]{new String(annotation.resolvedType.readableName()), new String(typeBinding.readableName())}, new String[]{new String(annotation.resolvedType.shortReadableName()), new String(typeBinding.shortReadableName())}, annotation.sourceStart, annotation.sourceEnd);
    }

    public void genericInferenceError(String str, InvocationSite invocationSite) {
        genericInferenceProblem(str, invocationSite, 1);
    }

    public void genericInferenceProblem(String str, InvocationSite invocationSite, int i) {
        int iSourceStart;
        int iSourceEnd;
        String[] strArr = {str};
        if (invocationSite != null) {
            iSourceStart = invocationSite.sourceStart();
            iSourceEnd = invocationSite.sourceEnd();
        } else {
            iSourceStart = 0;
            iSourceEnd = 0;
        }
        handle(IProblem.GenericInferenceError, strArr, strArr, i | 512, iSourceStart, iSourceEnd);
    }

    public void uninternedIdentityComparison(EqualExpression equalExpression, TypeBinding typeBinding, TypeBinding typeBinding2, CompilationUnitDeclaration compilationUnitDeclaration) {
        int lineNumber;
        char[] cArrSourceName = typeBinding.sourceName();
        char[] cArrSourceName2 = typeBinding2.sourceName();
        if (CharOperation.equals(cArrSourceName, "VoidTypeBinding".toCharArray()) || CharOperation.equals(cArrSourceName, "NullTypeBinding".toCharArray()) || CharOperation.equals(cArrSourceName, "ProblemReferenceBinding".toCharArray()) || CharOperation.equals(cArrSourceName2, "VoidTypeBinding".toCharArray()) || CharOperation.equals(cArrSourceName2, "NullTypeBinding".toCharArray()) || CharOperation.equals(cArrSourceName2, "ProblemReferenceBinding".toCharArray())) {
            return;
        }
        boolean[] zArr = compilationUnitDeclaration.validIdentityComparisonLines;
        if (zArr != null) {
            int i = equalExpression.left.sourceStart;
            if (i >= 0) {
                int[] lineSeparatorPositions = compilationUnitDeclaration.compilationResult().getLineSeparatorPositions();
                lineNumber = Util.getLineNumber(i, lineSeparatorPositions, 0, lineSeparatorPositions.length - 1);
            } else {
                lineNumber = 0;
            }
            if (lineNumber <= zArr.length && zArr[lineNumber - 1]) {
                return;
            }
        }
        handle(IProblem.UninternedIdentityComparison, new String[]{new String(typeBinding.readableName()), new String(typeBinding2.readableName())}, new String[]{new String(typeBinding.shortReadableName()), new String(typeBinding2.shortReadableName())}, equalExpression.sourceStart, equalExpression.sourceEnd);
    }

    public void invalidTypeArguments(TypeReference[] typeReferenceArr) {
        handle(IProblem.InvalidTypeArguments, NoArgument, NoArgument, typeReferenceArr[0].sourceStart, typeReferenceArr[typeReferenceArr.length - 1].sourceEnd);
    }
}
