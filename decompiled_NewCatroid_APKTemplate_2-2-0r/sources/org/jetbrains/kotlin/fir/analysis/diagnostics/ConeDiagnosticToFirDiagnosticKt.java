package org.jetbrains.kotlin.fir.analysis.diagnostics;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.diagnostics.KtDiagnostic;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory0;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory4;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticReportHelpersKt;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters1;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters2;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters3;
import org.jetbrains.kotlin.diagnostics.KtDiagnosticWithParameters4;
import org.jetbrains.kotlin.diagnostics.KtSimpleDiagnostic;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirSourceUtilsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.builder.FirSyntaxErrors;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousFunctionTypeKinds;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousSuper;
import org.jetbrains.kotlin.fir.diagnostics.ConeCannotInferTypeParameterType;
import org.jetbrains.kotlin.fir.diagnostics.ConeCannotInferValueParameterType;
import org.jetbrains.kotlin.fir.diagnostics.ConeDestructuringDeclarationsOnTopLevel;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeIntermediateDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeNotAnnotationContainer;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeStubDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeTypeVariableTypeIsNotInferred;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnderscoreIsReserved;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnderscoreUsageWithoutBackticks;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnexpectedTypeArgumentsError;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnsupportedClassLiteralsWithEmptyLhs;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.AmbiguousValuesForContextReceiverParameter;
import org.jetbrains.kotlin.fir.resolve.calls.ArgumentPassedTwice;
import org.jetbrains.kotlin.fir.resolve.calls.ArgumentTypeMismatch;
import org.jetbrains.kotlin.fir.resolve.calls.DslScopeViolation;
import org.jetbrains.kotlin.fir.resolve.calls.ErrorTypeInArguments;
import org.jetbrains.kotlin.fir.resolve.calls.InferenceError;
import org.jetbrains.kotlin.fir.resolve.calls.InferredEmptyIntersectionDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.InfixCallOfNonInfixFunction;
import org.jetbrains.kotlin.fir.resolve.calls.ManyLambdaExpressionArguments;
import org.jetbrains.kotlin.fir.resolve.calls.MultipleContextReceiversApplicableForExtensionReceivers;
import org.jetbrains.kotlin.fir.resolve.calls.NameForAmbiguousParameter;
import org.jetbrains.kotlin.fir.resolve.calls.NameNotFound;
import org.jetbrains.kotlin.fir.resolve.calls.NamedArgumentNotAllowed;
import org.jetbrains.kotlin.fir.resolve.calls.NoApplicableValueForContextReceiver;
import org.jetbrains.kotlin.fir.resolve.calls.NoValueForParameter;
import org.jetbrains.kotlin.fir.resolve.calls.NonVarargSpread;
import org.jetbrains.kotlin.fir.resolve.calls.NullForNotNullType;
import org.jetbrains.kotlin.fir.resolve.calls.OperatorCallOfNonOperatorFunction;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.TooManyArguments;
import org.jetbrains.kotlin.fir.resolve.calls.TypeVariableTypeRemovingSubstitutorKt;
import org.jetbrains.kotlin.fir.resolve.calls.UnsafeCall;
import org.jetbrains.kotlin.fir.resolve.calls.UnstableSmartCast;
import org.jetbrains.kotlin.fir.resolve.calls.UnsuccessfulCallableReferenceAtom;
import org.jetbrains.kotlin.fir.resolve.calls.UnsupportedContextualDeclarationCall;
import org.jetbrains.kotlin.fir.resolve.calls.VarargArgumentOutsideParentheses;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguousAlteredAssign;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguouslyResolvedAnnotationArgument;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguouslyResolvedAnnotationFromPlugin;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeConstraintSystemHasContradiction;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeContractDescriptionError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeForbiddenIntersection;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeFunctionCallExpectedError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeFunctionExpectedError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeHiddenCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeIllegalAnnotationError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeImportFromSingleton;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableWrongReceiver;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInstanceAccessBeforeSuperCall;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeLocalVariableNoTypeOrInitializer;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeNoCompanionObject;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeNoConstructorError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeNoTypeArgumentsOnRhsError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeOperatorAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeOuterClassArgumentsRequired;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeResolutionToClassifierError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterInQualifiedAccess;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterSupertype;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedParentInImport;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedReferenceError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedSymbolError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedTypeQualifierError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnsupported;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVariableExpectedError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVisibilityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeWrongNumberOfTypeArgumentsError;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeVariableForLambdaReturnType;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeExpectedTypeConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeLambdaArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstrainingTypeIsError;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.FixVariableConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersectionError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.model.NoSuccessfulFork;
import org.jetbrains.kotlin.resolve.calls.inference.model.NotEnoughInformationForTypeParameter;
import org.jetbrains.kotlin.resolve.calls.inference.model.OnlyInputTypesDiagnostic;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: coneDiagnosticToFirDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a2\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0002\u001a2\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001c2\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0002\u001a,\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\rH\u0002\u001aH\u0010 \u001a\u0004\u0018\u00010\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00060\"2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0002\u001a\u0016\u0010'\u001a\u00020(*\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\rH\u0002\u001a5\u0010'\u001a\b\u0012\u0004\u0012\u0002H,0+\"\u0004\b\u0000\u0010,*\b\u0012\u0004\u0012\u0002H,0-2\b\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010.\u001a\u0002H,H\u0002¢\u0006\u0002\u0010/\u001aO\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H100\"\u0004\b\u0000\u0010,\"\u0004\b\u0001\u00101*\u000e\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H1022\b\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010.\u001a\u0002H,2\u0006\u00103\u001a\u0002H1H\u0002¢\u0006\u0002\u00104\u001ai\u0010'\u001a\u0014\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H605\"\u0004\b\u0000\u0010,\"\u0004\b\u0001\u00101\"\u0004\b\u0002\u00106*\u0014\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H6072\b\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010.\u001a\u0002H,2\u0006\u00103\u001a\u0002H12\u0006\u00108\u001a\u0002H6H\u0002¢\u0006\u0002\u00109\u001a\u0083\u0001\u0010'\u001a\u001a\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H6\u0012\u0004\u0012\u0002H;0:\"\u0004\b\u0000\u0010,\"\u0004\b\u0001\u00101\"\u0004\b\u0002\u00106\"\u0004\b\u0003\u0010;*\u001a\u0012\u0004\u0012\u0002H,\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H6\u0012\u0004\u0012\u0002H;0<2\b\u0010*\u001a\u0004\u0018\u00010\r2\u0006\u0010.\u001a\u0002H,2\u0006\u00103\u001a\u0002H12\u0006\u00108\u001a\u0002H62\u0006\u0010=\u001a\u0002H;H\u0002¢\u0006\u0002\u0010>\u001a\u0016\u0010?\u001a\u00020)*\u00020@2\b\u0010\u0019\u001a\u0004\u0018\u00010\rH\u0002\u001a2\u0010A\u001a\u0004\u0018\u00010\u0014*\u00020B2\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\r2\u0006\u0010C\u001a\u00020D2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\u001a,\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013*\u00020F2\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010G\u001a\u0004\u0018\u00010\r\u001a\u001a\u0010H\u001a\u0004\u0018\u00010\u0014*\u0006\u0012\u0002\b\u00030I2\b\u0010\u0019\u001a\u0004\u0018\u00010\r\u001a\"\u0010J\u001a\u0004\u0018\u00010\u0014*\u00020F2\b\u0010\u0019\u001a\u0004\u0018\u00010\r2\b\u0010G\u001a\u0004\u0018\u00010\rH\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0018\u0010\n\u001a\u00020\u0006*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006K"}, d2 = {"canBeIgnored", "", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "getCanBeIgnored", "(Lorg/jetbrains/kotlin/KtFakeSourceElementKind;)Z", "lowerConeType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;", "getLowerConeType", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintError;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "upperConeType", "getUpperConeType", "findInferredEmptyIntersectionNarrowedSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "typeVariable", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "mapInapplicableCandidateError", "", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "diagnostic", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeInapplicableCandidateError;", FirebaseAnalytics.Param.SOURCE, "qualifiedAccessSource", "mapSystemHasContradictionError", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeConstraintSystemHasContradiction;", "mapUnsafeCallError", "rootCause", "Lorg/jetbrains/kotlin/fir/resolve/calls/UnsafeCall;", "reportInferredIntoEmptyIntersection", "incompatibleTypes", "", "causingTypes", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;", "isError", "createOn", "Lorg/jetbrains/kotlin/diagnostics/KtSimpleDiagnostic;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "A", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "a", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters1;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3;", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "c", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters3;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "d", "(Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticWithParameters4;", "getFactory", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeSimpleDiagnostic;", "toDiagnostic", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "toFirDiagnostics", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "callOrAssignmentSource", "toInvisibleReferenceDiagnostic", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "toKtDiagnostic", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeDiagnosticToFirDiagnosticKt {

    /* JADX INFO: compiled from: coneDiagnosticToFirDiagnostic.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[DiagnosticKind.values().length];
            try {
                iArr2[DiagnosticKind.ReturnNotAllowed.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[DiagnosticKind.NotAFunctionLabel.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[DiagnosticKind.UnresolvedLabel.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[DiagnosticKind.NoThis.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[DiagnosticKind.IllegalConstExpression.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[DiagnosticKind.IllegalUnderscore.ordinal()] = 6;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[DiagnosticKind.DeserializationError.ordinal()] = 7;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[DiagnosticKind.InferenceError.ordinal()] = 8;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[DiagnosticKind.RecursionInImplicitTypes.ordinal()] = 9;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[DiagnosticKind.Java.ordinal()] = 10;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr2[DiagnosticKind.SuperNotAllowed.ordinal()] = 11;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr2[DiagnosticKind.ExpressionExpected.ordinal()] = 12;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr2[DiagnosticKind.JumpOutsideLoop.ordinal()] = 13;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr2[DiagnosticKind.NotLoopLabel.ordinal()] = 14;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr2[DiagnosticKind.VariableExpected.ordinal()] = 15;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr2[DiagnosticKind.ValueParameterWithNoTypeAnnotation.ordinal()] = 16;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr2[DiagnosticKind.CannotInferParameterType.ordinal()] = 17;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr2[DiagnosticKind.IllegalProjectionUsage.ordinal()] = 18;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr2[DiagnosticKind.MissingStdlibClass.ordinal()] = 19;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr2[DiagnosticKind.IntLiteralOutOfRange.ordinal()] = 20;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr2[DiagnosticKind.FloatLiteralOutOfRange.ordinal()] = 21;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr2[DiagnosticKind.WrongLongSuffix.ordinal()] = 22;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr2[DiagnosticKind.UnsignedNumbersAreNotPresent.ordinal()] = 23;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr2[DiagnosticKind.IncorrectCharacterLiteral.ordinal()] = 24;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr2[DiagnosticKind.EmptyCharacterLiteral.ordinal()] = 25;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr2[DiagnosticKind.TooManyCharactersInCharacterLiteral.ordinal()] = 26;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr2[DiagnosticKind.IllegalEscape.ordinal()] = 27;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr2[DiagnosticKind.RecursiveTypealiasExpansion.ordinal()] = 28;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr2[DiagnosticKind.LoopInSupertype.ordinal()] = 29;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr2[DiagnosticKind.IllegalSelector.ordinal()] = 30;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr2[DiagnosticKind.NoReceiverAllowed.ordinal()] = 31;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr2[DiagnosticKind.IsEnumEntry.ordinal()] = 32;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr2[DiagnosticKind.EnumEntryAsType.ordinal()] = 33;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr2[DiagnosticKind.NotASupertype.ordinal()] = 34;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr2[DiagnosticKind.SuperNotAvailable.ordinal()] = 35;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr2[DiagnosticKind.AnnotationNotAllowed.ordinal()] = 36;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr2[DiagnosticKind.UnresolvedSupertype.ordinal()] = 37;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr2[DiagnosticKind.UnresolvedExpandedType.ordinal()] = 38;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr2[DiagnosticKind.Other.ordinal()] = 39;
            } catch (NoSuchFieldError unused41) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private static final KtDiagnostic toKtDiagnostic(ConeDiagnostic coneDiagnostic, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        Object next;
        Object next2;
        Name name2;
        KtDiagnosticWithParameters1 ktDiagnosticWithParameters1CreateOn;
        if (coneDiagnostic instanceof ConeUnresolvedReferenceError) {
            KtDiagnosticFactory1<String> unresolved_reference = FirErrors.INSTANCE.getUNRESOLVED_REFERENCE();
            String strAsString = ((ConeUnresolvedReferenceError) coneDiagnostic).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return createOn(unresolved_reference, ktSourceElement, strAsString);
        }
        if (coneDiagnostic instanceof ConeUnresolvedSymbolError) {
            KtDiagnosticFactory1<String> unresolved_reference2 = FirErrors.INSTANCE.getUNRESOLVED_REFERENCE();
            String strAsString2 = ((ConeUnresolvedSymbolError) coneDiagnostic).getClassId().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return createOn(unresolved_reference2, ktSourceElement, strAsString2);
        }
        if (coneDiagnostic instanceof ConeUnresolvedNameError) {
            KtDiagnosticFactory1<String> unresolved_reference3 = FirErrors.INSTANCE.getUNRESOLVED_REFERENCE();
            String strAsString3 = ((ConeUnresolvedNameError) coneDiagnostic).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
            return createOn(unresolved_reference3, ktSourceElement, strAsString3);
        }
        if (coneDiagnostic instanceof ConeUnresolvedTypeQualifierError) {
            return createOn(FirErrors.INSTANCE.getUNRESOLVED_REFERENCE(), ktSourceElement, ((ConeUnresolvedTypeQualifierError) coneDiagnostic).getQualifier());
        }
        if (coneDiagnostic instanceof ConeFunctionCallExpectedError) {
            KtDiagnosticFactory2<String, Boolean> function_call_expected = FirErrors.INSTANCE.getFUNCTION_CALL_EXPECTED();
            ConeFunctionCallExpectedError coneFunctionCallExpectedError = (ConeFunctionCallExpectedError) coneDiagnostic;
            String strAsString4 = coneFunctionCallExpectedError.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
            return createOn(function_call_expected, ktSourceElement, strAsString4, Boolean.valueOf(coneFunctionCallExpectedError.getHasValueParameters()));
        }
        if (coneDiagnostic instanceof ConeFunctionExpectedError) {
            ConeFunctionExpectedError coneFunctionExpectedError = (ConeFunctionExpectedError) coneDiagnostic;
            return createOn(FirErrors.INSTANCE.getFUNCTION_EXPECTED(), ktSourceElement, coneFunctionExpectedError.getExpression(), coneFunctionExpectedError.getType());
        }
        if (coneDiagnostic instanceof ConeNoConstructorError) {
            KtDiagnosticFactory0 no_constructor = FirErrors.INSTANCE.getNO_CONSTRUCTOR();
            if (ktSourceElement2 != null) {
                ktSourceElement = ktSourceElement2;
            }
            return createOn(no_constructor, ktSourceElement);
        }
        if (coneDiagnostic instanceof ConeResolutionToClassifierError) {
            ConeResolutionToClassifierError coneResolutionToClassifierError = (ConeResolutionToClassifierError) coneDiagnostic;
            int i = WhenMappings.$EnumSwitchMapping$0[coneResolutionToClassifierError.getCandidateSymbol().getClassKind().ordinal()];
            if (i == 1) {
                ktDiagnosticWithParameters1CreateOn = createOn(FirErrors.INSTANCE.getINTERFACE_AS_FUNCTION(), ktSourceElement, coneResolutionToClassifierError.getCandidateSymbol());
            } else if (i != 2) {
                ktDiagnosticWithParameters1CreateOn = createOn(FirErrors.INSTANCE.getRESOLUTION_TO_CLASSIFIER(), ktSourceElement, coneResolutionToClassifierError.getCandidateSymbol());
            } else if (coneResolutionToClassifierError.getCandidateSymbol().getRawStatus().isInner()) {
                ktDiagnosticWithParameters1CreateOn = createOn(FirErrors.INSTANCE.getINNER_CLASS_CONSTRUCTOR_NO_RECEIVER(), ktSourceElement, coneResolutionToClassifierError.getCandidateSymbol());
            } else if (coneResolutionToClassifierError.getCandidateSymbol().getRawStatus().isExpect()) {
                ktDiagnosticWithParameters1CreateOn = createOn(FirErrors.INSTANCE.getEXPECT_CLASS_AS_FUNCTION(), ktSourceElement, coneResolutionToClassifierError.getCandidateSymbol());
            } else {
                ktDiagnosticWithParameters1CreateOn = createOn(FirErrors.INSTANCE.getRESOLUTION_TO_CLASSIFIER(), ktSourceElement, coneResolutionToClassifierError.getCandidateSymbol());
            }
            return ktDiagnosticWithParameters1CreateOn;
        }
        Pair pair = null;
        UnstableSmartCast unstableSmartCast = null;
        KtSimpleDiagnostic ktSimpleDiagnosticCreateOn = null;
        if (coneDiagnostic instanceof ConeHiddenCandidateError) {
            KtDiagnosticFactory1<String> unresolved_reference4 = FirErrors.INSTANCE.getUNRESOLVED_REFERENCE();
            FirBasedSymbol<?> candidateSymbol = ((ConeHiddenCandidateError) coneDiagnostic).getCandidateSymbol();
            FirCallableSymbol firCallableSymbol = candidateSymbol instanceof FirCallableSymbol ? (FirCallableSymbol) candidateSymbol : null;
            if (firCallableSymbol == null || (name2 = firCallableSymbol.getName()) == null) {
                name2 = SpecialNames.NO_NAME_PROVIDED;
            }
            String strAsString5 = name2.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString5, "asString(...)");
            return createOn(unresolved_reference4, ktSourceElement, strAsString5);
        }
        if (coneDiagnostic instanceof ConeVisibilityError) {
            return toInvisibleReferenceDiagnostic(((ConeVisibilityError) coneDiagnostic).getSymbol(), ktSourceElement);
        }
        if (coneDiagnostic instanceof ConeInapplicableWrongReceiver) {
            return createOn(FirErrors.INSTANCE.getUNRESOLVED_REFERENCE_WRONG_RECEIVER(), ktSourceElement, ((ConeInapplicableWrongReceiver) coneDiagnostic).getCandidateSymbols());
        }
        if (coneDiagnostic instanceof ConeNoCompanionObject) {
            KtDiagnosticFactory1<FirClassLikeSymbol<?>> no_companion_object = FirErrors.INSTANCE.getNO_COMPANION_OBJECT();
            FirBasedSymbol<?> candidateSymbol2 = ((ConeNoCompanionObject) coneDiagnostic).getCandidateSymbol();
            Intrinsics.checkNotNull(candidateSymbol2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol<*>");
            return createOn(no_companion_object, ktSourceElement, (FirClassLikeSymbol) candidateSymbol2);
        }
        if (coneDiagnostic instanceof ConeAmbiguityError) {
            ConeAmbiguityError coneAmbiguityError = (ConeAmbiguityError) coneDiagnostic;
            if (!CandidateApplicabilityKt.isSuccess(coneAmbiguityError.getApplicability())) {
                if (coneAmbiguityError.getApplicability() == CandidateApplicability.UNSAFE_CALL) {
                    for (AbstractCandidate abstractCandidate : coneAmbiguityError.getCandidates()) {
                        Iterator<T> it = abstractCandidate.getDiagnostics().iterator();
                        do {
                            if (!it.hasNext()) {
                                next2 = null;
                                break;
                            }
                            next2 = it.next();
                        } while (!(next2 instanceof UnsafeCall));
                        UnsafeCall unsafeCall = (UnsafeCall) next2;
                        Pair pair2 = unsafeCall != null ? TuplesKt.to(unsafeCall, abstractCandidate) : null;
                        if (pair2 != null) {
                            pair = pair2;
                            break;
                        }
                    }
                    if (pair == null) {
                        throw new NoSuchElementException("No element of the collection was transformed to a non-null value.");
                    }
                    return mapUnsafeCallError((AbstractCandidate) pair.component2(), (UnsafeCall) pair.component1(), ktSourceElement, ktSourceElement2);
                }
                if (coneAmbiguityError.getApplicability() == CandidateApplicability.UNSTABLE_SMARTCAST) {
                    Iterator<T> it2 = coneAmbiguityError.getCandidates().iterator();
                    while (it2.hasNext()) {
                        Iterator<T> it3 = ((AbstractCandidate) it2.next()).getDiagnostics().iterator();
                        do {
                            if (!it3.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it3.next();
                        } while (!(next instanceof UnstableSmartCast));
                        UnstableSmartCast unstableSmartCast2 = (UnstableSmartCast) next;
                        if (unstableSmartCast2 != null) {
                            unstableSmartCast = unstableSmartCast2;
                            break;
                        }
                    }
                    if (unstableSmartCast == null) {
                        throw new NoSuchElementException("No element of the collection was transformed to a non-null value.");
                    }
                    return createOn(FirErrors.INSTANCE.getSMARTCAST_IMPOSSIBLE(), unstableSmartCast.getArgument().getSource(), unstableSmartCast.getTargetType(), unstableSmartCast.getArgument(), unstableSmartCast.getArgument().getSmartcastStability().getDescription(), Boolean.valueOf(unstableSmartCast.getIsCastToNotNull()));
                }
                KtDiagnosticFactory1<Collection<FirBasedSymbol<?>>> none_applicable = FirErrors.INSTANCE.getNONE_APPLICABLE();
                Collection<AbstractCandidate> candidates = coneAmbiguityError.getCandidates();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates, 10));
                Iterator<T> it4 = candidates.iterator();
                while (it4.hasNext()) {
                    arrayList.add(((AbstractCandidate) it4.next()).getSymbol());
                }
                return createOn(none_applicable, ktSourceElement, arrayList);
            }
            KtDiagnosticFactory1<Collection<FirBasedSymbol<?>>> overload_resolution_ambiguity = FirErrors.INSTANCE.getOVERLOAD_RESOLUTION_AMBIGUITY();
            Collection<AbstractCandidate> candidates2 = coneAmbiguityError.getCandidates();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(candidates2, 10));
            Iterator<T> it5 = candidates2.iterator();
            while (it5.hasNext()) {
                arrayList2.add(((AbstractCandidate) it5.next()).getSymbol());
            }
            return createOn(overload_resolution_ambiguity, ktSourceElement, arrayList2);
        }
        if (coneDiagnostic instanceof ConeOperatorAmbiguityError) {
            return createOn(FirErrors.INSTANCE.getASSIGN_OPERATOR_AMBIGUITY(), ktSourceElement, ((ConeOperatorAmbiguityError) coneDiagnostic).getCandidateSymbols());
        }
        if (coneDiagnostic instanceof ConeVariableExpectedError) {
            return createOn(FirErrors.INSTANCE.getVARIABLE_EXPECTED(), ktSourceElement);
        }
        if (coneDiagnostic instanceof ConeUnexpectedTypeArgumentsError) {
            KtDiagnosticFactory0 type_arguments_not_allowed = FirErrors.INSTANCE.getTYPE_ARGUMENTS_NOT_ALLOWED();
            KtSourceElement source = ((ConeUnexpectedTypeArgumentsError) coneDiagnostic).getSource();
            if (source != null) {
                ktSourceElement = source;
            }
            return createOn(type_arguments_not_allowed, ktSourceElement);
        }
        if (coneDiagnostic instanceof ConeIllegalAnnotationError) {
            KtDiagnosticFactory1<String> not_an_annotation_class = FirErrors.INSTANCE.getNOT_AN_ANNOTATION_CLASS();
            String strAsString6 = ((ConeIllegalAnnotationError) coneDiagnostic).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString6, "asString(...)");
            return createOn(not_an_annotation_class, ktSourceElement, strAsString6);
        }
        if (coneDiagnostic instanceof ConeWrongNumberOfTypeArgumentsError) {
            ConeWrongNumberOfTypeArgumentsError coneWrongNumberOfTypeArgumentsError = (ConeWrongNumberOfTypeArgumentsError) coneDiagnostic;
            return createOn(FirErrors.INSTANCE.getWRONG_NUMBER_OF_TYPE_ARGUMENTS(), coneWrongNumberOfTypeArgumentsError.getSource(), Integer.valueOf(coneWrongNumberOfTypeArgumentsError.getDesiredCount()), coneWrongNumberOfTypeArgumentsError.getSymbol());
        }
        if (coneDiagnostic instanceof ConeOuterClassArgumentsRequired) {
            KtDiagnosticFactory1<FirClassLikeSymbol<?>> outer_class_arguments_required = FirErrors.INSTANCE.getOUTER_CLASS_ARGUMENTS_REQUIRED();
            if (ktSourceElement2 != null) {
                ktSourceElement = ktSourceElement2;
            }
            return createOn(outer_class_arguments_required, ktSourceElement, ((ConeOuterClassArgumentsRequired) coneDiagnostic).getSymbol());
        }
        if (coneDiagnostic instanceof ConeNoTypeArgumentsOnRhsError) {
            KtDiagnosticFactory2<Integer, FirClassLikeSymbol<?>> no_type_arguments_on_rhs = FirErrors.INSTANCE.getNO_TYPE_ARGUMENTS_ON_RHS();
            if (ktSourceElement2 != null) {
                ktSourceElement = ktSourceElement2;
            }
            ConeNoTypeArgumentsOnRhsError coneNoTypeArgumentsOnRhsError = (ConeNoTypeArgumentsOnRhsError) coneDiagnostic;
            return createOn(no_type_arguments_on_rhs, ktSourceElement, Integer.valueOf(coneNoTypeArgumentsOnRhsError.getDesiredCount()), coneNoTypeArgumentsOnRhsError.getSymbol());
        }
        if (coneDiagnostic instanceof ConeSyntaxDiagnostic) {
            KtDiagnosticFactory1<String> syntax = FirSyntaxErrors.INSTANCE.getSYNTAX();
            if (ktSourceElement2 != null) {
                ktSourceElement = ktSourceElement2;
            }
            return createOn(syntax, ktSourceElement, coneDiagnostic.getReason());
        }
        if (coneDiagnostic instanceof ConeSimpleDiagnostic) {
            KtSourceElementKind kind = ktSourceElement != null ? ktSourceElement.getKind() : null;
            KtFakeSourceElementKind ktFakeSourceElementKind = kind instanceof KtFakeSourceElementKind ? (KtFakeSourceElementKind) kind : null;
            if (ktFakeSourceElementKind == null || !getCanBeIgnored(ktFakeSourceElementKind)) {
                KtDiagnosticFactory0 factory = getFactory((ConeSimpleDiagnostic) coneDiagnostic, ktSourceElement);
                if (ktSourceElement2 != null) {
                    ktSourceElement = ktSourceElement2;
                }
                ktSimpleDiagnosticCreateOn = createOn(factory, ktSourceElement);
            }
            return ktSimpleDiagnosticCreateOn;
        }
        if (!(coneDiagnostic instanceof ConeDestructuringDeclarationsOnTopLevel)) {
            if (!(coneDiagnostic instanceof ConeCannotInferTypeParameterType) && !(coneDiagnostic instanceof ConeCannotInferValueParameterType)) {
                if (coneDiagnostic instanceof ConeTypeVariableTypeIsNotInferred) {
                    KtDiagnosticFactory0 inference_error = FirErrors.INSTANCE.getINFERENCE_ERROR();
                    if (ktSourceElement2 != null) {
                        ktSourceElement = ktSourceElement2;
                    }
                    return createOn(inference_error, ktSourceElement);
                }
                if (coneDiagnostic instanceof ConeInstanceAccessBeforeSuperCall) {
                    return createOn(FirErrors.INSTANCE.getINSTANCE_ACCESS_BEFORE_SUPER_CALL(), ktSourceElement, ((ConeInstanceAccessBeforeSuperCall) coneDiagnostic).getTarget());
                }
                if (!(coneDiagnostic instanceof ConeStubDiagnostic) && !(coneDiagnostic instanceof ConeIntermediateDiagnostic)) {
                    if (coneDiagnostic instanceof ConeContractDescriptionError) {
                        return createOn(FirErrors.INSTANCE.getERROR_IN_CONTRACT_DESCRIPTION(), ktSourceElement, coneDiagnostic.getReason());
                    }
                    if (coneDiagnostic instanceof ConeTypeParameterSupertype) {
                        return createOn(FirErrors.INSTANCE.getSUPERTYPE_NOT_A_CLASS_OR_INTERFACE(), ktSourceElement, coneDiagnostic.getReason());
                    }
                    if (!(coneDiagnostic instanceof ConeTypeParameterInQualifiedAccess) && !(coneDiagnostic instanceof ConeNotAnnotationContainer)) {
                        if (coneDiagnostic instanceof ConeImportFromSingleton) {
                            return createOn(FirErrors.INSTANCE.getCANNOT_ALL_UNDER_IMPORT_FROM_SINGLETON(), ktSourceElement, ((ConeImportFromSingleton) coneDiagnostic).getName());
                        }
                        if (coneDiagnostic instanceof ConeUnsupported) {
                            KtDiagnosticFactory1<String> unsupported = FirErrors.INSTANCE.getUNSUPPORTED();
                            KtSourceElement source2 = ((ConeUnsupported) coneDiagnostic).getSource();
                            if (source2 != null) {
                                ktSourceElement = source2;
                            }
                            return createOn(unsupported, ktSourceElement, coneDiagnostic.getReason());
                        }
                        if (coneDiagnostic instanceof ConeLocalVariableNoTypeOrInitializer) {
                            return DeclarationUtilsKt.isLocalMember(((ConeLocalVariableNoTypeOrInitializer) coneDiagnostic).getVariable()) ? createOn(FirErrors.INSTANCE.getVARIABLE_WITH_NO_TYPE_NO_INITIALIZER(), ktSourceElement) : null;
                        }
                        if (!(coneDiagnostic instanceof ConeForbiddenIntersection)) {
                            if (coneDiagnostic instanceof ConeUnderscoreIsReserved) {
                                return createOn(FirErrors.INSTANCE.getUNDERSCORE_IS_RESERVED(), ((ConeUnderscoreIsReserved) coneDiagnostic).getSource());
                            }
                            if (coneDiagnostic instanceof ConeUnderscoreUsageWithoutBackticks) {
                                return createOn(FirErrors.INSTANCE.getUNDERSCORE_USAGE_WITHOUT_BACKTICKS(), ((ConeUnderscoreUsageWithoutBackticks) coneDiagnostic).getSource());
                            }
                            if (coneDiagnostic instanceof ConeAmbiguousSuper) {
                                return createOn(FirErrors.INSTANCE.getAMBIGUOUS_SUPER(), ktSourceElement, ((ConeAmbiguousSuper) coneDiagnostic).getCandidateTypes());
                            }
                            if (!(coneDiagnostic instanceof ConeUnresolvedParentInImport)) {
                                if (coneDiagnostic instanceof ConeAmbiguousAlteredAssign) {
                                    return createOn(FirErrors.INSTANCE.getAMBIGUOUS_ALTERED_ASSIGN(), ktSourceElement, ((ConeAmbiguousAlteredAssign) coneDiagnostic).getAltererNames());
                                }
                                if (coneDiagnostic instanceof ConeAmbiguouslyResolvedAnnotationFromPlugin) {
                                    ConeAmbiguouslyResolvedAnnotationFromPlugin coneAmbiguouslyResolvedAnnotationFromPlugin = (ConeAmbiguouslyResolvedAnnotationFromPlugin) coneDiagnostic;
                                    return createOn(FirErrors.INSTANCE.getPLUGIN_ANNOTATION_AMBIGUITY(), ktSourceElement, coneAmbiguouslyResolvedAnnotationFromPlugin.getTypeFromCompilerPhase(), coneAmbiguouslyResolvedAnnotationFromPlugin.getTypeFromTypesPhase());
                                }
                                if (coneDiagnostic instanceof ConeAmbiguouslyResolvedAnnotationArgument) {
                                    ConeAmbiguouslyResolvedAnnotationArgument coneAmbiguouslyResolvedAnnotationArgument = (ConeAmbiguouslyResolvedAnnotationArgument) coneDiagnostic;
                                    return createOn(FirErrors.INSTANCE.getAMBIGUOUS_ANNOTATION_ARGUMENT(), ktSourceElement, CollectionsKt.listOfNotNull((Object[]) new FirBasedSymbol[]{coneAmbiguouslyResolvedAnnotationArgument.getSymbolFromCompilerPhase(), coneAmbiguouslyResolvedAnnotationArgument.getSymbolFromAnnotationArgumentsPhase()}));
                                }
                                if (coneDiagnostic instanceof ConeAmbiguousFunctionTypeKinds) {
                                    return createOn(FirErrors.INSTANCE.getAMBIGUOUS_FUNCTION_TYPE_KIND(), ktSourceElement, ((ConeAmbiguousFunctionTypeKinds) coneDiagnostic).getKinds());
                                }
                                if (coneDiagnostic instanceof ConeUnsupportedClassLiteralsWithEmptyLhs) {
                                    return createOn(FirErrors.INSTANCE.getUNSUPPORTED_CLASS_LITERALS_WITH_EMPTY_LHS(), ktSourceElement);
                                }
                                throw new IllegalArgumentException("Unsupported diagnostic type: " + coneDiagnostic.getClass());
                            }
                        }
                    }
                }
            }
            return createOn(FirErrors.INSTANCE.getCANNOT_INFER_PARAMETER_TYPE(), ktSourceElement);
        }
        return null;
    }

    private static final boolean getCanBeIgnored(KtFakeSourceElementKind ktFakeSourceElementKind) {
        return (Intrinsics.areEqual(ktFakeSourceElementKind, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE) || Intrinsics.areEqual(ktFakeSourceElementKind, KtFakeSourceElementKind.ImplicitReturnTypeOfLambdaValueParameter.INSTANCE)) ? false : true;
    }

    public static final KtDiagnostic toInvisibleReferenceDiagnostic(FirBasedSymbol<?> firBasedSymbol, KtSourceElement ktSourceElement) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        if (firBasedSymbol instanceof FirCallableSymbol) {
            FirCallableSymbol firCallableSymbol = (FirCallableSymbol) firBasedSymbol;
            return createOn(FirErrors.INSTANCE.getINVISIBLE_REFERENCE(), ktSourceElement, firBasedSymbol, firCallableSymbol.getResolvedStatus().getVisibility(), firCallableSymbol.getCallableId().getClassId());
        }
        if (firBasedSymbol instanceof FirClassLikeSymbol) {
            FirClassLikeSymbol firClassLikeSymbol = (FirClassLikeSymbol) firBasedSymbol;
            return createOn(FirErrors.INSTANCE.getINVISIBLE_REFERENCE(), ktSourceElement, firBasedSymbol, firClassLikeSymbol.getResolvedStatus().getVisibility(), firClassLikeSymbol.getClassId().getOuterClassId());
        }
        AddToStdlibKt.shouldNotBeCalled("Unexpected receiver " + firBasedSymbol.getClass());
        throw null;
    }

    public static final List<KtDiagnostic> toFirDiagnostics(ConeDiagnostic coneDiagnostic, FirSession session, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        Intrinsics.checkNotNullParameter(coneDiagnostic, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (coneDiagnostic instanceof ConeInapplicableCandidateError) {
            return mapInapplicableCandidateError(session, (ConeInapplicableCandidateError) coneDiagnostic, ktSourceElement, ktSourceElement2);
        }
        return coneDiagnostic instanceof ConeConstraintSystemHasContradiction ? mapSystemHasContradictionError(session, (ConeConstraintSystemHasContradiction) coneDiagnostic, ktSourceElement, ktSourceElement2) : CollectionsKt.listOfNotNull(toKtDiagnostic(coneDiagnostic, ktSourceElement, ktSourceElement2));
    }

    /* JADX WARN: Code duplicated, block: B:26:0x0072  */
    /* JADX WARN: Code duplicated, block: B:35:0x0093  */
    /* JADX WARN: Code duplicated, block: B:36:0x0098  */
    /* JADX WARN: Code duplicated, block: B:39:0x00a1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:40:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:41:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:42:0x00ba  */
    /* JADX WARN: Code duplicated, block: B:44:0x00bd  */
    /* JADX WARN: Code duplicated, block: B:47:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f7  */
    private static final KtDiagnostic mapUnsafeCallError(AbstractCandidate abstractCandidate, UnsafeCall unsafeCall, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        IElementType elementType;
        KtSourceElement child$default;
        KtSourceElement ktSourceElement3 = ktSourceElement;
        if (abstractCandidate.getCallInfo().isImplicitInvoke()) {
            return createOn(FirErrors.INSTANCE.getUNSAFE_IMPLICIT_INVOKE_CALL(), ktSourceElement3, unsafeCall.getActualType());
        }
        FirBasedSymbol<?> symbol = abstractCandidate.getSymbol();
        Object child$default2 = null;
        FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
        Name name2 = firNamedFunctionSymbol != null ? firNamedFunctionSymbol.getName() : null;
        FirExpression explicitReceiver = abstractCandidate.getCallInfo().getExplicitReceiver();
        FirExpression firExpression = (FirExpression) CollectionsKt.singleOrNull((List) abstractCandidate.getCallInfo().getArgumentList().getArguments());
        if (explicitReceiver != null && firExpression != null) {
            if (!Intrinsics.areEqual(ktSourceElement3 != null ? ktSourceElement.getElementType() : null, KtNodeTypes.OPERATION_REFERENCE)) {
                if (Intrinsics.areEqual(ktSourceElement3 != null ? ktSourceElement.getElementType() : null, KtNodeTypes.BINARY_EXPRESSION)) {
                    if (firNamedFunctionSymbol != null) {
                        if (ktSourceElement3 != null) {
                            elementType = ktSourceElement.getElementType();
                        } else {
                            elementType = null;
                        }
                        if (!Intrinsics.areEqual(elementType, KtNodeTypes.BINARY_EXPRESSION)) {
                            child$default = ktSourceElement3;
                        } else if (ktSourceElement3 != null) {
                            IElementType OPERATION_REFERENCE = KtNodeTypes.OPERATION_REFERENCE;
                            Intrinsics.checkNotNullExpressionValue(OPERATION_REFERENCE, "OPERATION_REFERENCE");
                            child$default = FirSourceUtilsKt.getChild$default(ktSourceElement, OPERATION_REFERENCE, 0, 0, false, 14, (Object) null);
                        } else {
                            child$default = null;
                        }
                        if (child$default != null) {
                            KtToken IDENTIFIER = KtTokens.IDENTIFIER;
                            Intrinsics.checkNotNullExpressionValue(IDENTIFIER, "IDENTIFIER");
                            child$default2 = FirSourceUtilsKt.getChild$default(child$default, (IElementType) IDENTIFIER, 0, 0, false, 14, (Object) null);
                        }
                        if (child$default2 != null) {
                            KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_infix_call = FirErrors.INSTANCE.getUNSAFE_INFIX_CALL();
                            ConeKotlinType actualType = unsafeCall.getActualType();
                            Intrinsics.checkNotNull(name2);
                            String strAsString = name2.asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            return createOn(unsafe_infix_call, ktSourceElement, actualType, explicitReceiver, strAsString, firExpression);
                        }
                        KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_operator_call = FirErrors.INSTANCE.getUNSAFE_OPERATOR_CALL();
                        ConeKotlinType actualType2 = unsafeCall.getActualType();
                        Intrinsics.checkNotNull(name2);
                        String strAsString2 = name2.asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        return createOn(unsafe_operator_call, ktSourceElement, actualType2, explicitReceiver, strAsString2, firExpression);
                    }
                    if (ktSourceElement3 != null) {
                        elementType = ktSourceElement.getElementType();
                    } else {
                        elementType = null;
                    }
                    if (!Intrinsics.areEqual(elementType, KtNodeTypes.BINARY_EXPRESSION)) {
                        child$default = ktSourceElement3;
                    } else if (ktSourceElement3 != null) {
                        IElementType OPERATION_REFERENCE2 = KtNodeTypes.OPERATION_REFERENCE;
                        Intrinsics.checkNotNullExpressionValue(OPERATION_REFERENCE2, "OPERATION_REFERENCE");
                        child$default = FirSourceUtilsKt.getChild$default(ktSourceElement, OPERATION_REFERENCE2, 0, 0, false, 14, (Object) null);
                    } else {
                        child$default = null;
                    }
                    if (child$default != null) {
                        KtToken IDENTIFIER2 = KtTokens.IDENTIFIER;
                        Intrinsics.checkNotNullExpressionValue(IDENTIFIER2, "IDENTIFIER");
                        child$default2 = FirSourceUtilsKt.getChild$default(child$default, (IElementType) IDENTIFIER2, 0, 0, false, 14, (Object) null);
                    }
                    if (child$default2 != null) {
                        KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_infix_call2 = FirErrors.INSTANCE.getUNSAFE_INFIX_CALL();
                        ConeKotlinType actualType3 = unsafeCall.getActualType();
                        Intrinsics.checkNotNull(name2);
                        String strAsString3 = name2.asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                        return createOn(unsafe_infix_call2, ktSourceElement, actualType3, explicitReceiver, strAsString3, firExpression);
                    }
                    KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_operator_call2 = FirErrors.INSTANCE.getUNSAFE_OPERATOR_CALL();
                    ConeKotlinType actualType4 = unsafeCall.getActualType();
                    Intrinsics.checkNotNull(name2);
                    String strAsString4 = name2.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                    return createOn(unsafe_operator_call2, ktSourceElement, actualType4, explicitReceiver, strAsString4, firExpression);
                }
            } else if ((firNamedFunctionSymbol != null && firNamedFunctionSymbol.getResolvedStatus().isOperator()) || (firNamedFunctionSymbol != null && firNamedFunctionSymbol.getResolvedStatus().isInfix())) {
                if (ktSourceElement3 != null) {
                    elementType = ktSourceElement.getElementType();
                } else {
                    elementType = null;
                }
                if (!Intrinsics.areEqual(elementType, KtNodeTypes.BINARY_EXPRESSION)) {
                    child$default = ktSourceElement3;
                } else if (ktSourceElement3 != null) {
                    IElementType OPERATION_REFERENCE3 = KtNodeTypes.OPERATION_REFERENCE;
                    Intrinsics.checkNotNullExpressionValue(OPERATION_REFERENCE3, "OPERATION_REFERENCE");
                    child$default = FirSourceUtilsKt.getChild$default(ktSourceElement, OPERATION_REFERENCE3, 0, 0, false, 14, (Object) null);
                } else {
                    child$default = null;
                }
                if (child$default != null) {
                    KtToken IDENTIFIER3 = KtTokens.IDENTIFIER;
                    Intrinsics.checkNotNullExpressionValue(IDENTIFIER3, "IDENTIFIER");
                    child$default2 = FirSourceUtilsKt.getChild$default(child$default, (IElementType) IDENTIFIER3, 0, 0, false, 14, (Object) null);
                }
                if (child$default2 != null) {
                    KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_infix_call3 = FirErrors.INSTANCE.getUNSAFE_INFIX_CALL();
                    ConeKotlinType actualType5 = unsafeCall.getActualType();
                    Intrinsics.checkNotNull(name2);
                    String strAsString5 = name2.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString5, "asString(...)");
                    return createOn(unsafe_infix_call3, ktSourceElement, actualType5, explicitReceiver, strAsString5, firExpression);
                }
                KtDiagnosticFactory4<ConeKotlinType, FirExpression, String, FirExpression> unsafe_operator_call3 = FirErrors.INSTANCE.getUNSAFE_OPERATOR_CALL();
                ConeKotlinType actualType6 = unsafeCall.getActualType();
                Intrinsics.checkNotNull(name2);
                String strAsString6 = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString6, "asString(...)");
                return createOn(unsafe_operator_call3, ktSourceElement, actualType6, explicitReceiver, strAsString6, firExpression);
            }
        }
        if (Intrinsics.areEqual(ktSourceElement3 != null ? ktSourceElement.getKind() : null, KtFakeSourceElementKind.ArrayAccessNameReference.INSTANCE)) {
            return createOn(FirErrors.INSTANCE.getUNSAFE_CALL(), ktSourceElement3, unsafeCall.getActualType(), explicitReceiver);
        }
        KtDiagnosticFactory2<ConeKotlinType, FirExpression> unsafe_call = FirErrors.INSTANCE.getUNSAFE_CALL();
        if (ktSourceElement2 != null) {
            ktSourceElement3 = ktSourceElement2;
        }
        return createOn(unsafe_call, ktSourceElement3, unsafeCall.getActualType(), explicitReceiver);
    }

    private static final List<KtDiagnostic> mapInapplicableCandidateError(FirSession firSession, ConeInapplicableCandidateError coneInapplicableCandidateError, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        KtDiagnostic ktDiagnosticReportInferredIntoEmptyIntersection;
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
        KtDiagnostic ktDiagnosticCreateOn = createOn(FirErrors.INSTANCE.getINAPPLICABLE_CANDIDATE(), ktSourceElement, coneInapplicableCandidateError.getCandidate().getSymbol());
        List<ResolutionDiagnostic> diagnostics = coneInapplicableCandidateError.getCandidate().getDiagnostics();
        ArrayList<ResolutionDiagnostic> arrayList = new ArrayList();
        for (Object obj : diagnostics) {
            if (((ResolutionDiagnostic) obj).getApplicability() == coneInapplicableCandidateError.getApplicability()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (ResolutionDiagnostic resolutionDiagnostic : arrayList) {
            if (resolutionDiagnostic instanceof VarargArgumentOutsideParentheses) {
                KtDiagnosticFactory0 vararg_outside_parentheses = FirErrors.INSTANCE.getVARARG_OUTSIDE_PARENTHESES();
                KtSourceElement source = ((VarargArgumentOutsideParentheses) resolutionDiagnostic).getArgument().getSource();
                if (source == null) {
                    source = ktSourceElement2;
                }
                ktDiagnosticReportInferredIntoEmptyIntersection = createOn(vararg_outside_parentheses, source);
            } else if (resolutionDiagnostic instanceof NamedArgumentNotAllowed) {
                NamedArgumentNotAllowed namedArgumentNotAllowed = (NamedArgumentNotAllowed) resolutionDiagnostic;
                ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getNAMED_ARGUMENTS_NOT_ALLOWED(), namedArgumentNotAllowed.getArgument().getSource(), namedArgumentNotAllowed.getForbiddenNamedArgumentsTarget());
            } else if (resolutionDiagnostic instanceof ArgumentTypeMismatch) {
                KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean> argument_type_mismatch = FirErrors.INSTANCE.getARGUMENT_TYPE_MISMATCH();
                ArgumentTypeMismatch argumentTypeMismatch = (ArgumentTypeMismatch) resolutionDiagnostic;
                KtSourceElement source2 = argumentTypeMismatch.getArgument().getSource();
                if (source2 == null) {
                    source2 = ktSourceElement;
                }
                ConeInferenceContext coneInferenceContext = typeContext;
                ktDiagnosticReportInferredIntoEmptyIntersection = createOn(argument_type_mismatch, source2, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(argumentTypeMismatch.getExpectedType(), coneInferenceContext), TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(argumentTypeMismatch.getActualType(), coneInferenceContext), Boolean.valueOf(argumentTypeMismatch.getIsMismatchDueToNullability()));
            } else {
                KtSourceElement child$default = null;
                if ((resolutionDiagnostic instanceof ErrorTypeInArguments) || (resolutionDiagnostic instanceof UnsuccessfulCallableReferenceAtom)) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = null;
                } else if (resolutionDiagnostic instanceof MultipleContextReceiversApplicableForExtensionReceivers) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getAMBIGUOUS_CALL_WITH_IMPLICIT_CONTEXT_RECEIVER(), ktSourceElement2 == null ? ktSourceElement : ktSourceElement2);
                } else if (resolutionDiagnostic instanceof NoApplicableValueForContextReceiver) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getNO_CONTEXT_RECEIVER(), ktSourceElement2 == null ? ktSourceElement : ktSourceElement2, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(((NoApplicableValueForContextReceiver) resolutionDiagnostic).getExpectedContextReceiverType(), typeContext));
                } else if (resolutionDiagnostic instanceof UnsupportedContextualDeclarationCall) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getUNSUPPORTED_CONTEXTUAL_DECLARATION_CALL(), ktSourceElement);
                } else if (resolutionDiagnostic instanceof AmbiguousValuesForContextReceiverParameter) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getMULTIPLE_ARGUMENTS_APPLICABLE_FOR_CONTEXT_RECEIVER(), ktSourceElement2 == null ? ktSourceElement : ktSourceElement2, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(((AmbiguousValuesForContextReceiverParameter) resolutionDiagnostic).getExpectedContextReceiverType(), typeContext));
                } else if (resolutionDiagnostic instanceof NullForNotNullType) {
                    KtDiagnosticFactory1<ConeKotlinType> null_for_nonnull_type = FirErrors.INSTANCE.getNULL_FOR_NONNULL_TYPE();
                    NullForNotNullType nullForNotNullType = (NullForNotNullType) resolutionDiagnostic;
                    KtSourceElement source3 = nullForNotNullType.getArgument().getSource();
                    if (source3 == null) {
                        source3 = ktSourceElement;
                    }
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(null_for_nonnull_type, source3, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(nullForNotNullType.getExpectedType(), typeContext));
                } else if (resolutionDiagnostic instanceof NonVarargSpread) {
                    KtDiagnosticFactory0 non_vararg_spread = FirErrors.INSTANCE.getNON_VARARG_SPREAD();
                    KtSourceElement source4 = ((NonVarargSpread) resolutionDiagnostic).getArgument().getSource();
                    if (source4 != null) {
                        KtSingleValueToken MUL = KtTokens.MUL;
                        Intrinsics.checkNotNullExpressionValue(MUL, "MUL");
                        child$default = FirSourceUtilsKt.getChild$default(source4, (IElementType) MUL, 0, 1, false, 10, (Object) null);
                    }
                    Intrinsics.checkNotNull(child$default);
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(non_vararg_spread, child$default);
                } else if (resolutionDiagnostic instanceof ArgumentPassedTwice) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getARGUMENT_PASSED_TWICE(), ((ArgumentPassedTwice) resolutionDiagnostic).getArgument().getSource());
                } else if (resolutionDiagnostic instanceof TooManyArguments) {
                    KtDiagnosticFactory1<FirCallableSymbol<?>> too_many_arguments = FirErrors.INSTANCE.getTOO_MANY_ARGUMENTS();
                    TooManyArguments tooManyArguments = (TooManyArguments) resolutionDiagnostic;
                    KtSourceElement source5 = tooManyArguments.getArgument().getSource();
                    if (source5 == null) {
                        source5 = ktSourceElement;
                    }
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(too_many_arguments, source5, tooManyArguments.getFunction().getSymbol());
                } else if (resolutionDiagnostic instanceof NoValueForParameter) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getNO_VALUE_FOR_PARAMETER(), ktSourceElement2 == null ? ktSourceElement : ktSourceElement2, ((NoValueForParameter) resolutionDiagnostic).getValueParameter().getSymbol());
                } else if (resolutionDiagnostic instanceof NameNotFound) {
                    KtDiagnosticFactory1<String> named_parameter_not_found = FirErrors.INSTANCE.getNAMED_PARAMETER_NOT_FOUND();
                    NameNotFound nameNotFound = (NameNotFound) resolutionDiagnostic;
                    KtSourceElement source6 = nameNotFound.getArgument().getSource();
                    if (source6 == null) {
                        source6 = ktSourceElement;
                    }
                    String strAsString = nameNotFound.getArgument().getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(named_parameter_not_found, source6, strAsString);
                } else if (resolutionDiagnostic instanceof NameForAmbiguousParameter) {
                    KtDiagnosticFactory0 name_for_ambiguous_parameter = FirErrors.INSTANCE.getNAME_FOR_AMBIGUOUS_PARAMETER();
                    KtSourceElement source7 = ((NameForAmbiguousParameter) resolutionDiagnostic).getArgument().getSource();
                    if (source7 == null) {
                        source7 = ktSourceElement;
                    }
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(name_for_ambiguous_parameter, source7);
                } else if (resolutionDiagnostic instanceof UnsafeCall) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = mapUnsafeCallError(coneInapplicableCandidateError.getCandidate(), (UnsafeCall) resolutionDiagnostic, ktSourceElement, ktSourceElement2);
                } else if (resolutionDiagnostic instanceof ManyLambdaExpressionArguments) {
                    KtDiagnosticFactory0 many_lambda_expression_arguments = FirErrors.INSTANCE.getMANY_LAMBDA_EXPRESSION_ARGUMENTS();
                    KtSourceElement source8 = ((ManyLambdaExpressionArguments) resolutionDiagnostic).getArgument().getSource();
                    if (source8 == null) {
                        source8 = ktSourceElement;
                    }
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(many_lambda_expression_arguments, source8);
                } else if (resolutionDiagnostic instanceof InfixCallOfNonInfixFunction) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getINFIX_MODIFIER_REQUIRED(), ktSourceElement, ((InfixCallOfNonInfixFunction) resolutionDiagnostic).getFunction());
                } else if (resolutionDiagnostic instanceof OperatorCallOfNonOperatorFunction) {
                    KtDiagnosticFactory2<FirNamedFunctionSymbol, String> operator_modifier_required = FirErrors.INSTANCE.getOPERATOR_MODIFIER_REQUIRED();
                    OperatorCallOfNonOperatorFunction operatorCallOfNonOperatorFunction = (OperatorCallOfNonOperatorFunction) resolutionDiagnostic;
                    FirNamedFunctionSymbol function = operatorCallOfNonOperatorFunction.getFunction();
                    String strAsString2 = operatorCallOfNonOperatorFunction.getFunction().getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(operator_modifier_required, ktSourceElement, function, strAsString2);
                } else if (resolutionDiagnostic instanceof UnstableSmartCast) {
                    UnstableSmartCast unstableSmartCast = (UnstableSmartCast) resolutionDiagnostic;
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getSMARTCAST_IMPOSSIBLE(), unstableSmartCast.getArgument().getSource(), unstableSmartCast.getTargetType(), unstableSmartCast.getArgument(), unstableSmartCast.getArgument().getSmartcastStability().getDescription(), Boolean.valueOf(unstableSmartCast.getIsCastToNotNull()));
                } else if (resolutionDiagnostic instanceof DslScopeViolation) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = createOn(FirErrors.INSTANCE.getDSL_SCOPE_VIOLATION(), ktSourceElement, ((DslScopeViolation) resolutionDiagnostic).getCalleeSymbol());
                } else if (resolutionDiagnostic instanceof InferenceError) {
                    ktDiagnosticReportInferredIntoEmptyIntersection = toDiagnostic(((InferenceError) resolutionDiagnostic).getConstraintError(), ktSourceElement, ktSourceElement2, TypeComponentsKt.getTypeContext(firSession), coneInapplicableCandidateError.getCandidate());
                } else if (resolutionDiagnostic instanceof InferredEmptyIntersectionDiagnostic) {
                    InferredEmptyIntersectionDiagnostic inferredEmptyIntersectionDiagnostic = (InferredEmptyIntersectionDiagnostic) resolutionDiagnostic;
                    ktDiagnosticReportInferredIntoEmptyIntersection = reportInferredIntoEmptyIntersection(ktSourceElement, inferredEmptyIntersectionDiagnostic.getTypeVariable(), inferredEmptyIntersectionDiagnostic.getIncompatibleTypes(), inferredEmptyIntersectionDiagnostic.getCausingTypes(), inferredEmptyIntersectionDiagnostic.getKind(), inferredEmptyIntersectionDiagnostic.getIsError());
                } else {
                    ktDiagnosticReportInferredIntoEmptyIntersection = ktDiagnosticCreateOn;
                }
            }
            if (ktDiagnosticReportInferredIntoEmptyIntersection != null) {
                arrayList2.add(ktDiagnosticReportInferredIntoEmptyIntersection);
            }
        }
        List<KtDiagnostic> listDistinct = CollectionsKt.distinct(arrayList2);
        if (listDistinct.size() <= 1) {
            return listDistinct;
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listDistinct) {
            if (!Intrinsics.areEqual((KtDiagnostic) obj2, ktDiagnosticCreateOn)) {
                arrayList3.add(obj2);
            }
        }
        return arrayList3;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x0101  */
    /* JADX WARN: Code duplicated, block: B:39:0x0109  */
    /* JADX WARN: Code duplicated, block: B:40:0x010b  */
    private static final List<KtDiagnostic> mapSystemHasContradictionError(FirSession firSession, ConeConstraintSystemHasContradiction coneConstraintSystemHasContradiction, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2) {
        KtDiagnosticWithParameters1 ktDiagnosticWithParameters1CreateOn;
        String str;
        KtSourceElement ktSourceElement3;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<ConstraintSystemError> it = coneConstraintSystemHasContradiction.getCandidate().getErrors().iterator();
        while (it.hasNext()) {
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(listCreateListBuilder, toDiagnostic(it.next(), ktSourceElement, ktSourceElement2, TypeComponentsKt.getTypeContext(firSession), coneConstraintSystemHasContradiction.getCandidate()));
        }
        List<KtDiagnostic> listBuild = CollectionsKt.build(listCreateListBuilder);
        if (listBuild.isEmpty()) {
            Iterator<T> it2 = coneConstraintSystemHasContradiction.getCandidate().getErrors().iterator();
            do {
                ktDiagnosticWithParameters1CreateOn = null;
                if (!it2.hasNext()) {
                    break;
                }
                ConstraintSystemError constraintSystemError = (ConstraintSystemError) it2.next();
                boolean z = constraintSystemError instanceof NewConstraintError;
                if (z) {
                    NewConstraintError newConstraintError = (NewConstraintError) constraintSystemError;
                    str = "NewConstraintError at " + newConstraintError.getPosition() + ": " + newConstraintError.getLowerType() + " <!: " + newConstraintError.getUpperType();
                } else if (!(constraintSystemError instanceof ConstrainingTypeIsError) && !(constraintSystemError instanceof NotEnoughInformationForTypeParameter)) {
                    str = "Inference error: " + Reflection.getOrCreateKotlinClass(constraintSystemError.getClass()).getSimpleName();
                }
                if (z && (((NewConstraintError) constraintSystemError).getPosition().getFrom() instanceof FixVariableConstraintPosition)) {
                    List<ConstraintSystemError> errors = coneConstraintSystemHasContradiction.getCandidate().getErrors();
                    if ((errors instanceof Collection) && errors.isEmpty()) {
                        KtDiagnosticFactory1<String> new_inference_error = FirErrors.INSTANCE.getNEW_INFERENCE_ERROR();
                        if (ktSourceElement2 == null) {
                            ktSourceElement3 = ktSourceElement;
                        } else {
                            ktSourceElement3 = ktSourceElement2;
                        }
                        ktDiagnosticWithParameters1CreateOn = createOn(new_inference_error, ktSourceElement3, str);
                    } else {
                        Iterator<T> it3 = errors.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                ConstraintSystemError constraintSystemError2 = (ConstraintSystemError) it3.next();
                                if (!(constraintSystemError2 instanceof NewConstraintError) || (((NewConstraintError) constraintSystemError2).getPosition().getFrom() instanceof FixVariableConstraintPosition)) {
                                }
                            } else {
                                KtDiagnosticFactory1<String> new_inference_error2 = FirErrors.INSTANCE.getNEW_INFERENCE_ERROR();
                                if (ktSourceElement2 == null) {
                                    ktSourceElement3 = ktSourceElement;
                                } else {
                                    ktSourceElement3 = ktSourceElement2;
                                }
                                ktDiagnosticWithParameters1CreateOn = createOn(new_inference_error2, ktSourceElement3, str);
                            }
                        }
                    }
                } else {
                    KtDiagnosticFactory1<String> new_inference_error3 = FirErrors.INSTANCE.getNEW_INFERENCE_ERROR();
                    if (ktSourceElement2 == null) {
                        ktSourceElement3 = ktSourceElement;
                    } else {
                        ktSourceElement3 = ktSourceElement2;
                    }
                    ktDiagnosticWithParameters1CreateOn = createOn(new_inference_error3, ktSourceElement3, str);
                }
            } while (ktDiagnosticWithParameters1CreateOn == null);
            listBuild = CollectionsKt.listOfNotNull(ktDiagnosticWithParameters1CreateOn);
        }
        return listBuild;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final KtDiagnostic toDiagnostic(ConstraintSystemError constraintSystemError, KtSourceElement ktSourceElement, KtSourceElement ktSourceElement2, ConeTypeContext coneTypeContext, AbstractCandidate abstractCandidate) {
        String strAsString;
        FirAnonymousFunction lambda;
        ConeKotlinType coneKotlinTypeWithNullability$default;
        KtDiagnosticWithParameters3 ktDiagnosticWithParameters3CreateOn = null;
        if (constraintSystemError instanceof NewConstraintError) {
            NewConstraintError newConstraintError = (NewConstraintError) constraintSystemError;
            ConstraintPosition from = newConstraintError.getPosition().getFrom();
            if (from instanceof ConeArgumentConstraintPosition) {
                lambda = ((ConeArgumentConstraintPosition) from).getArgument();
            } else {
                lambda = from instanceof ConeLambdaArgumentConstraintPosition ? ((ConeLambdaArgumentConstraintPosition) from).getLambda() : null;
            }
            boolean zIsTypeMismatchDueToNullability = InferenceUtilsKt.isTypeMismatchDueToNullability(coneTypeContext, getLowerConeType(newConstraintError), getUpperConeType(newConstraintError));
            if (lambda != null) {
                KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean> argument_type_mismatch = FirErrors.INSTANCE.getARGUMENT_TYPE_MISMATCH();
                KtSourceElement source = lambda.getSource();
                if (source != null) {
                    ktSourceElement = source;
                }
                return createOn(argument_type_mismatch, ktSourceElement, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(getLowerConeType(newConstraintError), coneTypeContext), TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(getUpperConeType(newConstraintError), coneTypeContext), Boolean.valueOf(zIsTypeMismatchDueToNullability));
            }
            if (from instanceof ConeExpectedTypeConstraintPosition) {
                if (!ConeBuiltinTypeUtilsKt.isNullableNothing(getLowerConeType(newConstraintError))) {
                    coneKotlinTypeWithNullability$default = getLowerConeType(newConstraintError);
                } else {
                    coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(getUpperConeType(newConstraintError), ConeNullability.NULLABLE, coneTypeContext, null, false, 12, null);
                }
                KtDiagnosticFactory3<ConeKotlinType, ConeKotlinType, Boolean> type_mismatch = FirErrors.INSTANCE.getTYPE_MISMATCH();
                if (ktSourceElement2 != null) {
                    ktSourceElement = ktSourceElement2;
                }
                ktDiagnosticWithParameters3CreateOn = createOn(type_mismatch, ktSourceElement, TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(getUpperConeType(newConstraintError), coneTypeContext), TypeVariableTypeRemovingSubstitutorKt.removeTypeVariableTypes(coneKotlinTypeWithNullability$default, coneTypeContext), Boolean.valueOf(zIsTypeMismatchDueToNullability));
            }
            return ktDiagnosticWithParameters3CreateOn;
        }
        if (constraintSystemError instanceof NotEnoughInformationForTypeParameter) {
            List<ConstraintSystemError> errors = abstractCandidate.getErrors();
            if (!(errors instanceof Collection) || !errors.isEmpty()) {
                for (ConstraintSystemError constraintSystemError2 : errors) {
                    if (((constraintSystemError2 instanceof ConstrainingTypeIsError) && Intrinsics.areEqual(((ConstrainingTypeIsError) constraintSystemError2).getTypeVariable(), ((NotEnoughInformationForTypeParameter) constraintSystemError).getTypeVariable())) || (constraintSystemError2 instanceof NewConstraintError)) {
                        return null;
                    }
                }
            }
            TypeVariableMarker typeVariable = ((NotEnoughInformationForTypeParameter) constraintSystemError).getTypeVariable();
            if (typeVariable instanceof ConeTypeParameterBasedTypeVariable) {
                strAsString = ((ConeTypeParameterBasedTypeVariable) typeVariable).getTypeParameterSymbol().getName().asString();
            } else {
                if (!(typeVariable instanceof ConeTypeVariableForLambdaReturnType)) {
                    throw new IllegalStateException(("Unsupported type variable: " + typeVariable).toString());
                }
                strAsString = "return type of lambda";
            }
            Intrinsics.checkNotNull(strAsString);
            return createOn(FirErrors.INSTANCE.getNEW_INFERENCE_NO_INFORMATION_FOR_PARAMETER(), ktSourceElement, strAsString);
        }
        if (constraintSystemError instanceof NoSuccessfulFork) {
            return createOn(FirErrors.INSTANCE.getINFERENCE_UNSUCCESSFUL_FORK(), ktSourceElement, ((NoSuccessfulFork) constraintSystemError).getPosition().getInitialConstraint().asStringWithoutPosition());
        }
        if (constraintSystemError instanceof InferredEmptyIntersection) {
            InferredEmptyIntersection inferredEmptyIntersection = (InferredEmptyIntersection) constraintSystemError;
            TypeVariableMarker typeVariable2 = inferredEmptyIntersection.getTypeVariable();
            Intrinsics.checkNotNull(typeVariable2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
            ConeTypeVariable coneTypeVariable = (ConeTypeVariable) typeVariable2;
            KtSourceElement ktSourceElementFindInferredEmptyIntersectionNarrowedSource = findInferredEmptyIntersectionNarrowedSource(coneTypeVariable, abstractCandidate);
            KtSourceElement ktSourceElement3 = ktSourceElementFindInferredEmptyIntersectionNarrowedSource == null ? ktSourceElement : ktSourceElementFindInferredEmptyIntersectionNarrowedSource;
            List<KotlinTypeMarker> incompatibleTypes = inferredEmptyIntersection.getIncompatibleTypes();
            Intrinsics.checkNotNull(incompatibleTypes, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.fir.types.ConeKotlinType>");
            List<KotlinTypeMarker> list = incompatibleTypes;
            List<KotlinTypeMarker> causingTypes = inferredEmptyIntersection.getCausingTypes();
            Intrinsics.checkNotNull(causingTypes, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.fir.types.ConeKotlinType>");
            return reportInferredIntoEmptyIntersection(ktSourceElement3, coneTypeVariable, list, causingTypes, inferredEmptyIntersection.getKind(), constraintSystemError instanceof InferredEmptyIntersectionError);
        }
        if (!(constraintSystemError instanceof OnlyInputTypesDiagnostic)) {
            return null;
        }
        KtDiagnosticFactory1<FirTypeParameterSymbol> type_inference_only_input_types_error = FirErrors.INSTANCE.getTYPE_INFERENCE_ONLY_INPUT_TYPES_ERROR();
        TypeVariableMarker typeVariable3 = ((OnlyInputTypesDiagnostic) constraintSystemError).getTypeVariable();
        Intrinsics.checkNotNull(typeVariable3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable");
        return createOn(type_inference_only_input_types_error, ktSourceElement, ((ConeTypeParameterBasedTypeVariable) typeVariable3).getTypeParameterSymbol());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final KtSourceElement findInferredEmptyIntersectionNarrowedSource(final ConeTypeVariable coneTypeVariable, AbstractCandidate abstractCandidate) {
        if (!(coneTypeVariable instanceof ConeTypeParameterBasedTypeVariable)) {
            return null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        abstractCandidate.getCallInfo().getCallSite().accept(new FirVisitorVoid() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.ConeDiagnosticToFirDiagnosticKt.findInferredEmptyIntersectionNarrowedSource.1
            /* JADX WARN: Type inference failed for: r0v9, types: [T, org.jetbrains.kotlin.KtSourceElement] */
            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6787visitElement(FirElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (objectRef.element != null) {
                    return;
                }
                if (element instanceof FirQualifiedAccessExpression) {
                    FirQualifiedAccessExpression firQualifiedAccessExpression = (FirQualifiedAccessExpression) element;
                    FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(firQualifiedAccessExpression.getCalleeReference(), false, 1, null);
                    if (resolvedCallableSymbol$default != null && resolvedCallableSymbol$default.getTypeParameterSymbols().contains(((ConeTypeParameterBasedTypeVariable) coneTypeVariable).getTypeParameterSymbol())) {
                        objectRef.element = firQualifiedAccessExpression.getCalleeReference().getSource();
                        return;
                    }
                }
                element.acceptChildren(this);
            }
        }, null);
        return (KtSourceElement) objectRef.element;
    }

    private static final KtDiagnostic reportInferredIntoEmptyIntersection(KtSourceElement ktSourceElement, ConeTypeVariable coneTypeVariable, Collection<? extends ConeKotlinType> collection, Collection<? extends ConeKotlinType> collection2, EmptyIntersectionTypeKind emptyIntersectionTypeKind, boolean z) {
        String string;
        KtDiagnosticFactory4<String, Collection<ConeKotlinType>, String, String> inferred_type_variable_into_possible_empty_intersection;
        Name name2;
        TypeParameterMarker originalTypeParameter = coneTypeVariable.getTypeConstructor().getOriginalTypeParameter();
        ConeTypeParameterLookupTag coneTypeParameterLookupTag = originalTypeParameter instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter : null;
        if (coneTypeParameterLookupTag == null || (name2 = coneTypeParameterLookupTag.getName()) == null || (string = name2.asString()) == null) {
            string = coneTypeVariable.toString();
        }
        String str = string;
        Intrinsics.checkNotNull(str);
        String str2 = Intrinsics.areEqual(collection, collection2) ? "" : ": " + CollectionsKt.joinToString$default(collection2, null, null, null, 0, null, null, 63, null);
        if (!emptyIntersectionTypeKind.getIsDefinitelyEmpty()) {
            inferred_type_variable_into_possible_empty_intersection = FirErrors.INSTANCE.getINFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION();
        } else if (z) {
            inferred_type_variable_into_possible_empty_intersection = (KtDiagnosticFactory4) FirErrors.INSTANCE.getINFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION().getErrorFactory();
        } else {
            inferred_type_variable_into_possible_empty_intersection = (KtDiagnosticFactory4) FirErrors.INSTANCE.getINFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION().getWarningFactory();
        }
        return createOn(inferred_type_variable_into_possible_empty_intersection, ktSourceElement, str, collection, emptyIntersectionTypeKind.getDescription(), str2);
    }

    private static final ConeKotlinType getLowerConeType(NewConstraintError newConstraintError) {
        KotlinTypeMarker lowerType = newConstraintError.getLowerType();
        Intrinsics.checkNotNull(lowerType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) lowerType;
    }

    private static final ConeKotlinType getUpperConeType(NewConstraintError newConstraintError) {
        KotlinTypeMarker upperType = newConstraintError.getUpperType();
        Intrinsics.checkNotNull(upperType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return (ConeKotlinType) upperType;
    }

    private static final KtDiagnosticFactory0 getFactory(ConeSimpleDiagnostic coneSimpleDiagnostic, KtSourceElement ktSourceElement) {
        switch (WhenMappings.$EnumSwitchMapping$1[coneSimpleDiagnostic.getKind().ordinal()]) {
            case 1:
                return FirErrors.INSTANCE.getRETURN_NOT_ALLOWED();
            case 2:
                return FirErrors.INSTANCE.getNOT_A_FUNCTION_LABEL();
            case 3:
                return FirErrors.INSTANCE.getUNRESOLVED_LABEL();
            case 4:
                return FirErrors.INSTANCE.getNO_THIS();
            case 5:
                return FirErrors.INSTANCE.getILLEGAL_CONST_EXPRESSION();
            case 6:
                return FirErrors.INSTANCE.getILLEGAL_UNDERSCORE();
            case 7:
                return FirErrors.INSTANCE.getDESERIALIZATION_ERROR();
            case 8:
                return FirErrors.INSTANCE.getINFERENCE_ERROR();
            case 9:
                return FirErrors.INSTANCE.getRECURSION_IN_IMPLICIT_TYPES();
            case 10:
                return FirErrors.INSTANCE.getERROR_FROM_JAVA_RESOLUTION();
            case 11:
                return FirErrors.INSTANCE.getSUPER_IS_NOT_AN_EXPRESSION();
            case 12:
                IElementType elementType = ktSourceElement != null ? ktSourceElement.getElementType() : null;
                if (Intrinsics.areEqual(elementType, KtNodeTypes.BINARY_EXPRESSION)) {
                    return FirErrors.INSTANCE.getASSIGNMENT_IN_EXPRESSION_CONTEXT();
                }
                if (Intrinsics.areEqual(elementType, KtNodeTypes.FUN)) {
                    return FirErrors.INSTANCE.getANONYMOUS_FUNCTION_WITH_NAME();
                }
                if (Intrinsics.areEqual(elementType, KtNodeTypes.WHEN_CONDITION_IN_RANGE) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.WHEN_CONDITION_IS_PATTERN) ? true : Intrinsics.areEqual(elementType, KtNodeTypes.WHEN_CONDITION_EXPRESSION)) {
                    return FirErrors.INSTANCE.getEXPECTED_CONDITION();
                }
                return FirErrors.INSTANCE.getEXPRESSION_EXPECTED();
            case 13:
                return FirErrors.INSTANCE.getBREAK_OR_CONTINUE_OUTSIDE_A_LOOP();
            case 14:
                return FirErrors.INSTANCE.getNOT_A_LOOP_LABEL();
            case 15:
                return FirErrors.INSTANCE.getVARIABLE_EXPECTED();
            case 16:
                return FirErrors.INSTANCE.getVALUE_PARAMETER_WITH_NO_TYPE_ANNOTATION();
            case 17:
                return FirErrors.INSTANCE.getCANNOT_INFER_PARAMETER_TYPE();
            case 18:
                return FirErrors.INSTANCE.getILLEGAL_PROJECTION_USAGE();
            case 19:
                return FirErrors.INSTANCE.getMISSING_STDLIB_CLASS();
            case 20:
                return FirErrors.INSTANCE.getINT_LITERAL_OUT_OF_RANGE();
            case 21:
                return FirErrors.INSTANCE.getFLOAT_LITERAL_OUT_OF_RANGE();
            case 22:
                return FirErrors.INSTANCE.getWRONG_LONG_SUFFIX();
            case 23:
                return FirErrors.INSTANCE.getUNSIGNED_LITERAL_WITHOUT_DECLARATIONS_ON_CLASSPATH();
            case 24:
                return FirErrors.INSTANCE.getINCORRECT_CHARACTER_LITERAL();
            case 25:
                return FirErrors.INSTANCE.getEMPTY_CHARACTER_LITERAL();
            case 26:
                return FirErrors.INSTANCE.getTOO_MANY_CHARACTERS_IN_CHARACTER_LITERAL();
            case 27:
                return FirErrors.INSTANCE.getILLEGAL_ESCAPE();
            case 28:
                return FirErrors.INSTANCE.getRECURSIVE_TYPEALIAS_EXPANSION();
            case 29:
                return FirErrors.INSTANCE.getCYCLIC_INHERITANCE_HIERARCHY();
            case 30:
                return FirErrors.INSTANCE.getILLEGAL_SELECTOR();
            case 31:
                return FirErrors.INSTANCE.getNO_RECEIVER_ALLOWED();
            case 32:
                return FirErrors.INSTANCE.getIS_ENUM_ENTRY();
            case 33:
                return FirErrors.INSTANCE.getENUM_ENTRY_AS_TYPE();
            case 34:
                return FirErrors.INSTANCE.getNOT_A_SUPERTYPE();
            case 35:
                return FirErrors.INSTANCE.getSUPER_NOT_AVAILABLE();
            case 36:
                return FirErrors.INSTANCE.getANNOTATION_IN_WHERE_CLAUSE_ERROR();
            case 37:
            case 38:
            case 39:
                return FirErrors.INSTANCE.getOTHER_ERROR();
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private static final KtSimpleDiagnostic createOn(KtDiagnosticFactory0 ktDiagnosticFactory0, KtSourceElement ktSourceElement) {
        return ktDiagnosticFactory0.on(KtDiagnosticReportHelpersKt.requireNotNull(ktSourceElement), null);
    }

    private static final <A> KtDiagnosticWithParameters1<A> createOn(KtDiagnosticFactory1<A> ktDiagnosticFactory1, KtSourceElement ktSourceElement, A a) {
        return ktDiagnosticFactory1.on(KtDiagnosticReportHelpersKt.requireNotNull(ktSourceElement), a, null);
    }

    private static final <A, B> KtDiagnosticWithParameters2<A, B> createOn(KtDiagnosticFactory2<A, B> ktDiagnosticFactory2, KtSourceElement ktSourceElement, A a, B b) {
        return ktDiagnosticFactory2.on(KtDiagnosticReportHelpersKt.requireNotNull(ktSourceElement), a, b, null);
    }

    private static final <A, B, C> KtDiagnosticWithParameters3<A, B, C> createOn(KtDiagnosticFactory3<A, B, C> ktDiagnosticFactory3, KtSourceElement ktSourceElement, A a, B b, C c) {
        return ktDiagnosticFactory3.on(KtDiagnosticReportHelpersKt.requireNotNull(ktSourceElement), a, b, c, null);
    }

    private static final <A, B, C, D> KtDiagnosticWithParameters4<A, B, C, D> createOn(KtDiagnosticFactory4<A, B, C, D> ktDiagnosticFactory4, KtSourceElement ktSourceElement, A a, B b, C c, D d) {
        return ktDiagnosticFactory4.on(KtDiagnosticReportHelpersKt.requireNotNull(ktSourceElement), a, b, c, d, null);
    }
}
