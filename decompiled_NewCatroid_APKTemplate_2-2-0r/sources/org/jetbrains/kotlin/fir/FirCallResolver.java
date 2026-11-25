package org.jetbrains.kotlin.fir;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeStubDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirImplicitInvokeCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirImplicitInvokeCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedReifiedParameterReferenceBuilder;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.resolve.DoubleColonLHS;
import org.jetbrains.kotlin.fir.resolve.ErrorCandidateUtilsKt;
import org.jetbrains.kotlin.fir.resolve.FirRegularTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.CallKind;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateCollector;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactory;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolver;
import org.jetbrains.kotlin.fir.resolve.calls.ConeCallConflictResolverKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirArgumentsToParametersMapperKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionDiagnostic;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionStageRunner;
import org.jetbrains.kotlin.fir.resolve.calls.Unsupported;
import org.jetbrains.kotlin.fir.resolve.calls.tower.FirTowerResolver;
import org.jetbrains.kotlin.fir.resolve.calls.tower.TowerResolveManager;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeDiagnosticWithSymbol;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeHiddenCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeIllegalAnnotationError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedReferenceError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnsupported;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponentsKt;
import org.jetbrains.kotlin.fir.resolve.inference.ResolvedCallableReferenceAtom;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirStarProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemOperation;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemTransaction;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirCallResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Â\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001tB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J6\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001a2\b\b\u0002\u0010\"\u001a\u00020#J^\u0010$\u001a\u00020%2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\n\b\u0002\u0010&\u001a\u0004\u0018\u00010'2\b\b\u0002\u0010(\u001a\u00020)2\u000e\b\u0002\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001a2\b\b\u0002\u0010\"\u001a\u00020#2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010+2\b\b\u0002\u0010,\u001a\u00020-H\u0002J.\u0010.\u001a\u00020\u00142\u0006\u0010/\u001a\u0002002\b\u00101\u001a\u0004\u0018\u0001022\b\u00103\u001a\u0004\u0018\u0001042\b\u00105\u001a\u0004\u0018\u000106H\u0002J,\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002Jj\u0010;\u001a\u0002082\u0006\u0010<\u001a\u00020=2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010>\u001a\b\u0012\u0004\u0012\u00020:0?2\u0006\u0010@\u001a\u00020A2\n\b\u0002\u0010B\u001a\u0004\u0018\u00010C2\b\b\u0002\u0010D\u001a\u00020E2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010'2\u0010\b\u0002\u0010G\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010?H\u0002J\u0012\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020KH\u0002J\u000e\u0010L\u001a\u00020M2\u0006\u0010\u000f\u001a\u00020\u0010J:\u0010N\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0P\u0012\u0006\u0012\u0004\u0018\u00010A0O2\u0006\u0010*\u001a\u00020+2\n\b\u0002\u0010B\u001a\u0004\u0018\u00010C2\b\b\u0002\u0010\"\u001a\u00020#H\u0002J\u0010\u0010Q\u001a\u0004\u0018\u00010R2\u0006\u0010S\u001a\u00020RJ\u000e\u0010T\u001a\u00020U2\u0006\u0010V\u001a\u00020UJ\"\u0010W\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020E0O2\u0006\u0010X\u001a\u0002062\u0006\u0010Y\u001a\u00020ZJ \u0010[\u001a\u00020\\2\u0006\u0010]\u001a\u00020\\2\b\u0010^\u001a\u0004\u0018\u00010_2\u0006\u0010`\u001a\u00020aJ\u0016\u0010b\u001a\u00020c2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010d\u001a\u00020EJ\u001e\u0010e\u001a\u00020c2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010d\u001a\u00020E2\u0006\u0010,\u001a\u00020-J<\u0010f\u001a\u00020c2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010d\u001a\u00020E2\b\b\u0002\u0010,\u001a\u00020-2\u0018\u0010g\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020:0?\u0012\u0004\u0012\u00020E0hH\u0002J\u001c\u0010i\u001a\u00020%2\u0006\u0010\u0013\u001a\u00020\u00142\n\u0010j\u001a\u0006\u0012\u0002\b\u00030kH\u0002J(\u0010l\u001a\u00020\\2\u0006\u0010m\u001a\u00020\\2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010n\u001a\u00020+2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010o\u001a\u00020\u00142\u0006\u0010S\u001a\u00020R2\u0006\u0010<\u001a\u00020pH\u0002J\f\u0010q\u001a\u00020r*\u00020sH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000¨\u0006u²\u0006\n\u0010v\u001a\u00020%X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirCallResolver;", "", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "towerResolver", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolver;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/FirTowerResolver;)V", "conflictResolver", "Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "getConflictResolver", "()Lorg/jetbrains/kotlin/fir/resolve/calls/ConeCallConflictResolver;", "overloadByLambdaReturnTypeResolver", "Lorg/jetbrains/kotlin/fir/FirOverloadByLambdaReturnTypeResolver;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "buildReferenceWithErrorCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirErrorReferenceWithCandidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "collectAllCandidates", "", "Lorg/jetbrains/kotlin/fir/OverloadCandidate;", "qualifiedAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "containingDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "collectCandidates", "Lorg/jetbrains/kotlin/fir/FirCallResolver$ResolutionResult;", "forceCallKind", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallKind;", "origin", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCallOrigin;", "collector", "Lorg/jetbrains/kotlin/fir/resolve/calls/CandidateCollector;", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "createCallableReferencesInfoForLHS", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "lhs", "Lorg/jetbrains/kotlin/fir/resolve/DoubleColonLHS;", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "outerConstraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "createErrorReferenceForSingleCandidate", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "createResolvedNamedReference", "reference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "candidates", "", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "createResolvedReferenceWithoutCandidateForLocalVariables", "", "expectedCallKind", "expectedCandidates", "getConstructorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "annotationClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "initTransformer", "", "reduceCandidates", "Lkotlin/Pair;", "", "resolveAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "annotation", "resolveCallAndSelectCandidate", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "functionCall", "resolveCallableReference", "constraintSystemBuilder", "resolvedCallableReferenceAtom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedCallableReferenceAtom;", "resolveDelegatingConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "delegatedConstructorCall", "constructedType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "derivedClassLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "resolveOnlyEnumOrQualifierAccessAndSelectCandidate", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "isUsedAsReceiver", "resolveVariableAccessAndSelectCandidate", "resolveVariableAccessAndSelectCandidateImpl", "acceptCandidates", "Lkotlin/Function1;", "runResolutionForGivenSymbol", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "selectDelegatingConstructorCall", "call", "result", "toCallInfo", "Lorg/jetbrains/kotlin/fir/references/impl/FirSimpleNamedReference;", "toFirTypeProjection", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "ResolutionResult", "resolve", "basicResult"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallResolver {
    private final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components;
    private final ConeCallConflictResolver conflictResolver;
    private final FirOverloadByLambdaReturnTypeResolver overloadByLambdaReturnTypeResolver;
    private final FirSession session;
    private final FirTowerResolver towerResolver;
    private FirExpressionsResolveTransformer transformer;

    /* JADX INFO: compiled from: FirCallResolver.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProjectionKind.values().length];
            try {
                iArr[ProjectionKind.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProjectionKind.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProjectionKind.INVARIANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProjectionKind.STAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirCallResolver(FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components, FirTowerResolver towerResolver) {
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(towerResolver, "towerResolver");
        this.components = components;
        this.towerResolver = towerResolver;
        FirSession session = components.getSession();
        this.session = session;
        this.overloadByLambdaReturnTypeResolver = new FirOverloadByLambdaReturnTypeResolver(components);
        this.conflictResolver = ConeCallConflictResolverKt.getCallConflictResolverFactory(session).create(TypeSpecificityComparator.NONE.INSTANCE, InferenceComponentsKt.getInferenceComponents(session), components);
    }

    public /* synthetic */ FirCallResolver(FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents, FirTowerResolver firTowerResolver, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 2) != 0) {
            firTowerResolver = new FirTowerResolver(bodyResolveTransformerComponents, bodyResolveTransformerComponents.getResolutionStageRunner(), null, 4, null);
        }
        this(bodyResolveTransformerComponents, firTowerResolver);
    }

    public final void initTransformer(FirExpressionsResolveTransformer transformer) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.transformer = transformer;
    }

    public final ConeCallConflictResolver getConflictResolver() {
        return this.conflictResolver;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0059  */
    public final FirFunctionCall resolveCallAndSelectCandidate(FirFunctionCall functionCall) {
        Collection<Candidate> candidates;
        Candidate candidate;
        FirImplicitInvokeCall functionCall2 = functionCall;
        Intrinsics.checkNotNullParameter(functionCall2, "functionCall");
        Name name2 = functionCall.getCalleeReference().getName();
        FirFunctionCall firFunctionCall = functionCall2;
        ResolutionResult resolutionResultCollectCandidates$default = collectCandidates$default(this, firFunctionCall, name2, null, functionCall.getOrigin(), null, null, null, null, 244, null);
        if (resolutionResultCollectCandidates$default.getCandidates().isEmpty()) {
            ResolutionResult resolutionResultCollectCandidates$default2 = collectCandidates$default(this, firFunctionCall, name2, CallKind.VariableAccess.INSTANCE, functionCall.getOrigin(), null, null, null, null, 240, null);
            if (resolutionResultCollectCandidates$default2.getCandidates().isEmpty()) {
                candidates = null;
            } else {
                candidates = resolutionResultCollectCandidates$default2.getCandidates();
            }
        } else {
            candidates = null;
        }
        FirNamedReference firNamedReferenceCreateResolvedNamedReference$default = createResolvedNamedReference$default(this, functionCall.getCalleeReference(), name2, resolutionResultCollectCandidates$default.getInfo(), resolutionResultCollectCandidates$default.getCandidates(), resolutionResultCollectCandidates$default.getApplicability(), functionCall.getExplicitReceiver(), false, candidates != null ? CallKind.VariableAccess.INSTANCE : null, candidates, 64, null);
        functionCall2.replaceCalleeReference(firNamedReferenceCreateResolvedNamedReference$default);
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = firNamedReferenceCreateResolvedNamedReference$default instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) firNamedReferenceCreateResolvedNamedReference$default : null;
        Candidate candidate2 = firNamedReferenceWithCandidate != null ? firNamedReferenceWithCandidate.getCandidate() : null;
        FirExpression explicitReceiver = functionCall.getExplicitReceiver();
        if (candidate2 != null && (explicitReceiver instanceof FirResolvedQualifier)) {
            ((FirResolvedQualifier) explicitReceiver).replaceResolvedToCompanionObject(candidate2.getIsFromCompanionObjectTypeScope());
        }
        if (candidate2 != null && !Intrinsics.areEqual(candidate2.getCallInfo(), resolutionResultCollectCandidates$default.getInfo())) {
            FirReference calleeReference = explicitReceiver != null ? FirExpressionUtilKt.getCalleeReference(explicitReceiver) : null;
            FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2 = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
            if (firNamedReferenceWithCandidate2 != null && (candidate = firNamedReferenceWithCandidate2.getCandidate()) != null) {
                candidate.updateSourcesOfReceivers();
            }
            candidate2.updateSourcesOfReceivers();
            FirImplicitInvokeCallBuilder firImplicitInvokeCallBuilder = new FirImplicitInvokeCallBuilder();
            firImplicitInvokeCallBuilder.setSource(functionCall.getSource());
            firImplicitInvokeCallBuilder.getAnnotations().addAll(functionCall.getAnnotations());
            firImplicitInvokeCallBuilder.getTypeArguments().addAll(functionCall.getTypeArguments());
            firImplicitInvokeCallBuilder.setExplicitReceiver(functionCall.getExplicitReceiver());
            firImplicitInvokeCallBuilder.setDispatchReceiver(functionCall.getDispatchReceiver());
            firImplicitInvokeCallBuilder.setExtensionReceiver(functionCall.getExtensionReceiver());
            firImplicitInvokeCallBuilder.setArgumentList(functionCall.getArgumentList());
            firImplicitInvokeCallBuilder.setCalleeReference(functionCall.getCalleeReference());
            firImplicitInvokeCallBuilder.setExplicitReceiver(candidate2.getCallInfo().getExplicitReceiver());
            firImplicitInvokeCallBuilder.setDispatchReceiver(candidate2.dispatchReceiverExpression());
            firImplicitInvokeCallBuilder.setExtensionReceiver(candidate2.chosenExtensionReceiverExpression());
            firImplicitInvokeCallBuilder.setArgumentList(candidate2.getCallInfo().getArgumentList());
            firImplicitInvokeCallBuilder.getContextReceiverArguments().addAll(candidate2.contextReceiverArguments());
            functionCall2 = firImplicitInvokeCallBuilder.mo6809build();
        } else if (candidate2 != null) {
            candidate2.updateSourcesOfReceivers();
        }
        FirResolvedTypeRef firResolvedTypeRefTypeFromCallee = ResolveUtilsKt.typeFromCallee(this.components, functionCall2);
        if (firResolvedTypeRefTypeFromCallee.getType() instanceof ConeErrorType) {
            functionCall2.replaceTypeRef(firResolvedTypeRefTypeFromCallee);
        }
        return functionCall2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirCallResolver.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirCallResolver$ResolutionResult;", "", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "candidates", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;Ljava/util/Collection;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getCandidates", "()Ljava/util/Collection;", "getInfo", "()Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class ResolutionResult {
        private final CandidateApplicability applicability;
        private final Collection<Candidate> candidates;
        private final CallInfo info;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ResolutionResult copy$default(ResolutionResult resolutionResult, CallInfo callInfo, CandidateApplicability candidateApplicability, Collection collection, int i, Object obj) {
            if ((i & 1) != 0) {
                callInfo = resolutionResult.info;
            }
            if ((i & 2) != 0) {
                candidateApplicability = resolutionResult.applicability;
            }
            if ((i & 4) != 0) {
                collection = resolutionResult.candidates;
            }
            return resolutionResult.copy(callInfo, candidateApplicability, collection);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final CallInfo getInfo() {
            return this.info;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final CandidateApplicability getApplicability() {
            return this.applicability;
        }

        public final Collection<Candidate> component3() {
            return this.candidates;
        }

        public final ResolutionResult copy(CallInfo info, CandidateApplicability applicability, Collection<Candidate> candidates) {
            Intrinsics.checkNotNullParameter(info, "info");
            Intrinsics.checkNotNullParameter(applicability, "applicability");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            return new ResolutionResult(info, applicability, candidates);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ResolutionResult)) {
                return false;
            }
            ResolutionResult resolutionResult = (ResolutionResult) other;
            return Intrinsics.areEqual(this.info, resolutionResult.info) && this.applicability == resolutionResult.applicability && Intrinsics.areEqual(this.candidates, resolutionResult.candidates);
        }

        public int hashCode() {
            return (((this.info.hashCode() * 31) + this.applicability.hashCode()) * 31) + this.candidates.hashCode();
        }

        public String toString() {
            return "ResolutionResult(info=" + this.info + ", applicability=" + this.applicability + ", candidates=" + this.candidates + ')';
        }

        public ResolutionResult(CallInfo info, CandidateApplicability applicability, Collection<Candidate> candidates) {
            Intrinsics.checkNotNullParameter(info, "info");
            Intrinsics.checkNotNullParameter(applicability, "applicability");
            Intrinsics.checkNotNullParameter(candidates, "candidates");
            this.info = info;
            this.applicability = applicability;
            this.candidates = candidates;
        }

        public final CandidateApplicability getApplicability() {
            return this.applicability;
        }

        public final Collection<Candidate> getCandidates() {
            return this.candidates;
        }

        public final CallInfo getInfo() {
            return this.info;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List collectAllCandidates$default(FirCallResolver firCallResolver, FirQualifiedAccessExpression firQualifiedAccessExpression, Name name2, List list, ResolutionContext resolutionContext, int i, Object obj) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = null;
        if ((i & 4) != 0) {
            FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firCallResolver.transformer;
            if (firExpressionsResolveTransformer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer2 = null;
            }
            list = firExpressionsResolveTransformer2.getTransformer().getComponents().getContainingDeclarations();
        }
        if ((i & 8) != 0) {
            FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = firCallResolver.transformer;
            if (firExpressionsResolveTransformer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
            } else {
                firExpressionsResolveTransformer = firExpressionsResolveTransformer3;
            }
            resolutionContext = firExpressionsResolveTransformer.getTransformer().getResolutionContext();
        }
        return firCallResolver.collectAllCandidates(firQualifiedAccessExpression, name2, list, resolutionContext);
    }

    public final List<OverloadCandidate> collectAllCandidates(FirQualifiedAccessExpression qualifiedAccess, Name name2, List<? extends FirDeclaration> containingDeclarations, ResolutionContext resolutionContext) {
        FirFunctionCallOrigin origin;
        Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents = this.components;
        AllCandidatesCollector allCandidatesCollector = new AllCandidatesCollector(bodyResolveTransformerComponents, bodyResolveTransformerComponents.getResolutionStageRunner());
        FirFunctionCall firFunctionCall = qualifiedAccess instanceof FirFunctionCall ? (FirFunctionCall) qualifiedAccess : null;
        if (firFunctionCall == null || (origin = firFunctionCall.getOrigin()) == null) {
            origin = FirFunctionCallOrigin.Regular;
        }
        ResolutionResult resolutionResultCollectCandidates$default = collectCandidates$default(this, qualifiedAccess, name2, null, origin, containingDeclarations, resolutionContext, allCandidatesCollector, null, 128, null);
        Collection<Candidate> allCandidates = allCandidatesCollector.getAllCandidates();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(allCandidates, 10));
        for (Candidate candidate : allCandidates) {
            arrayList.add(new OverloadCandidate(candidate, resolutionResultCollectCandidates$default.getCandidates().contains(candidate)));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ ResolutionResult collectCandidates$default(FirCallResolver firCallResolver, FirQualifiedAccessExpression firQualifiedAccessExpression, Name name2, CallKind callKind, FirFunctionCallOrigin firFunctionCallOrigin, List list, ResolutionContext resolutionContext, CandidateCollector candidateCollector, FirElement firElement, int i, Object obj) {
        List containingDeclarations;
        ResolutionContext resolutionContext2;
        CallKind callKind2 = (i & 4) != 0 ? null : callKind;
        FirFunctionCallOrigin firFunctionCallOrigin2 = (i & 8) != 0 ? FirFunctionCallOrigin.Regular : firFunctionCallOrigin;
        if ((i & 16) != 0) {
            FirExpressionsResolveTransformer firExpressionsResolveTransformer = firCallResolver.transformer;
            if (firExpressionsResolveTransformer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer = null;
            }
            containingDeclarations = firExpressionsResolveTransformer.getTransformer().getComponents().getContainingDeclarations();
        } else {
            containingDeclarations = list;
        }
        if ((i & 32) != 0) {
            FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firCallResolver.transformer;
            if (firExpressionsResolveTransformer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer2 = null;
            }
            resolutionContext2 = firExpressionsResolveTransformer2.getTransformer().getResolutionContext();
        } else {
            resolutionContext2 = resolutionContext;
        }
        return firCallResolver.collectCandidates(firQualifiedAccessExpression, name2, callKind2, firFunctionCallOrigin2, containingDeclarations, resolutionContext2, (i & 64) != 0 ? null : candidateCollector, (i & 128) != 0 ? firQualifiedAccessExpression : firElement);
    }

    private final ResolutionResult collectCandidates(FirQualifiedAccessExpression qualifiedAccess, Name name2, CallKind forceCallKind, FirFunctionCallOrigin origin, List<? extends FirDeclaration> containingDeclarations, ResolutionContext resolutionContext, CandidateCollector collector, FirElement callSite) {
        FirEmptyArgumentList argumentList;
        CallKind callKind;
        FirExpression explicitReceiver = qualifiedAccess.getExplicitReceiver();
        boolean z = qualifiedAccess instanceof FirFunctionCall;
        FirFunctionCall firFunctionCall = z ? (FirFunctionCall) qualifiedAccess : null;
        if (firFunctionCall == null || (argumentList = firFunctionCall.getArgumentList()) == null) {
            argumentList = FirEmptyArgumentList.INSTANCE;
        }
        FirArgumentList firArgumentList = argumentList;
        FirFunctionCall firFunctionCall2 = z ? (FirFunctionCall) qualifiedAccess : null;
        List<FirTypeProjection> typeArguments = firFunctionCall2 != null ? firFunctionCall2.getTypeArguments() : null;
        if (typeArguments == null) {
            typeArguments = CollectionsKt.emptyList();
        }
        List<FirTypeProjection> list = typeArguments;
        if (forceCallKind == null) {
            callKind = z ? CallKind.Function.INSTANCE : CallKind.VariableAccess.INSTANCE;
        } else {
            callKind = forceCallKind;
        }
        CallInfo callInfo = new CallInfo(callSite, callKind, name2, explicitReceiver, firArgumentList, qualifiedAccess instanceof FirImplicitInvokeCall, list, this.session, this.components.getFile(), containingDeclarations, null, null, null, null, origin, 15360, null);
        this.towerResolver.reset();
        CandidateCollector candidateCollectorRunResolver = this.towerResolver.runResolver(callInfo, resolutionContext, collector);
        Pair<Set<Candidate>, CandidateApplicability> pairReduceCandidates = reduceCandidates(candidateCollectorRunResolver, explicitReceiver, resolutionContext);
        Set<Candidate> setComponent1 = pairReduceCandidates.component1();
        CandidateApplicability candidateApplicabilityComponent2 = pairReduceCandidates.component2();
        Set<Candidate> setReduceCandidates = this.overloadByLambdaReturnTypeResolver.reduceCandidates(qualifiedAccess, setComponent1, setComponent1);
        if (candidateApplicabilityComponent2 == null) {
            candidateApplicabilityComponent2 = candidateCollectorRunResolver.getCurrentApplicability();
        }
        return new ResolutionResult(callInfo, candidateApplicabilityComponent2, setReduceCandidates);
    }

    static /* synthetic */ Pair reduceCandidates$default(FirCallResolver firCallResolver, CandidateCollector candidateCollector, FirExpression firExpression, ResolutionContext resolutionContext, int i, Object obj) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = null;
        if ((i & 2) != 0) {
            firExpression = null;
        }
        if ((i & 4) != 0) {
            FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firCallResolver.transformer;
            if (firExpressionsResolveTransformer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
            } else {
                firExpressionsResolveTransformer = firExpressionsResolveTransformer2;
            }
            resolutionContext = firExpressionsResolveTransformer.getTransformer().getResolutionContext();
        }
        return firCallResolver.reduceCandidates(candidateCollector, firExpression, resolutionContext);
    }

    private static final Set<Candidate> reduceCandidates$chooseMostSpecific(FirExpression firExpression, FirCallResolver firCallResolver, List<Candidate> list) {
        FirQualifiedAccessExpression firQualifiedAccessExpression = firExpression instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) firExpression : null;
        return firCallResolver.conflictResolver.chooseMaximallySpecificCandidates(list, (firQualifiedAccessExpression != null ? firQualifiedAccessExpression.getCalleeReference() : null) instanceof FirSuperReference);
    }

    private final Pair<Set<Candidate>, CandidateApplicability> reduceCandidates(CandidateCollector collector, FirExpression explicitReceiver, ResolutionContext resolutionContext) {
        List<Candidate> listBestCandidates = collector.bestCandidates();
        if (CandidateApplicabilityKt.isSuccess(collector.getCurrentApplicability())) {
            return TuplesKt.to(reduceCandidates$chooseMostSpecific(explicitReceiver, this, listBestCandidates), null);
        }
        if (listBestCandidates.size() <= 1) {
            return TuplesKt.to(CollectionsKt.toSet(listBestCandidates), null);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listBestCandidates) {
            Candidate candidate = (Candidate) obj;
            ErrorCandidateUtilsKt.fullyProcessCandidate(this.components.getResolutionStageRunner(), candidate, resolutionContext);
            Iterator<T> it = candidate.getDiagnostics().iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            CandidateApplicability applicability = ((ResolutionDiagnostic) it.next()).getApplicability();
            while (it.hasNext()) {
                CandidateApplicability applicability2 = ((ResolutionDiagnostic) it.next()).getApplicability();
                if (applicability.compareTo(applicability2) > 0) {
                    applicability = applicability2;
                }
            }
            CandidateApplicability candidateApplicability = applicability;
            Object obj2 = linkedHashMap.get(candidateApplicability);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(candidateApplicability, obj2);
            }
            ((List) obj2).add(obj);
        }
        Iterator it2 = linkedHashMap.entrySet().iterator();
        if (!it2.hasNext()) {
            throw new NoSuchElementException();
        }
        Object next = it2.next();
        if (it2.hasNext()) {
            CandidateApplicability candidateApplicability2 = (CandidateApplicability) ((Map.Entry) next).getKey();
            do {
                Object next2 = it2.next();
                CandidateApplicability candidateApplicability3 = (CandidateApplicability) ((Map.Entry) next2).getKey();
                if (candidateApplicability2.compareTo(candidateApplicability3) < 0) {
                    next = next2;
                    candidateApplicability2 = candidateApplicability3;
                }
            } while (it2.hasNext());
        }
        Map.Entry entry = (Map.Entry) next;
        return TuplesKt.to(reduceCandidates$chooseMostSpecific(explicitReceiver, this, (List) entry.getValue()), entry.getKey());
    }

    public final FirStatement resolveVariableAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccess, boolean isUsedAsReceiver, FirElement callSite) {
        Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
        Intrinsics.checkNotNullParameter(callSite, "callSite");
        return resolveVariableAccessAndSelectCandidateImpl(qualifiedAccess, isUsedAsReceiver, callSite, new Function1<Collection<? extends Candidate>, Boolean>() { // from class: org.jetbrains.kotlin.fir.FirCallResolver.resolveVariableAccessAndSelectCandidate.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Collection<Candidate> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return true;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Collection<? extends Candidate> collection) {
                return invoke2((Collection<Candidate>) collection);
            }
        });
    }

    public final FirStatement resolveOnlyEnumOrQualifierAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccess, boolean isUsedAsReceiver) {
        Intrinsics.checkNotNullParameter(qualifiedAccess, "qualifiedAccess");
        return resolveVariableAccessAndSelectCandidateImpl$default(this, qualifiedAccess, isUsedAsReceiver, null, new Function1<Collection<? extends Candidate>, Boolean>() { // from class: org.jetbrains.kotlin.fir.FirCallResolver.resolveOnlyEnumOrQualifierAccessAndSelectCandidate.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Collection<? extends Candidate> collection) {
                return invoke2((Collection<Candidate>) collection);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Boolean invoke2(Collection<Candidate> candidates) {
                FirBasedSymbol<?> symbol;
                Intrinsics.checkNotNullParameter(candidates, "candidates");
                Candidate candidate = (Candidate) CollectionsKt.singleOrNull(candidates);
                if (candidate == null || (symbol = candidate.getSymbol()) == null) {
                    return false;
                }
                return Boolean.valueOf((symbol instanceof FirEnumEntrySymbol) || (symbol instanceof FirRegularClassSymbol));
            }
        }, 4, null);
    }

    static /* synthetic */ FirStatement resolveVariableAccessAndSelectCandidateImpl$default(FirCallResolver firCallResolver, FirQualifiedAccessExpression firQualifiedAccessExpression, boolean z, FirElement firElement, Function1 function1, int i, Object obj) {
        if ((i & 4) != 0) {
            firElement = firQualifiedAccessExpression;
        }
        return firCallResolver.resolveVariableAccessAndSelectCandidateImpl(firQualifiedAccessExpression, z, firElement, function1);
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01cf  */
    /* JADX WARN: Code duplicated, block: B:106:0x01de  */
    /* JADX WARN: Code duplicated, block: B:110:0x01e7  */
    /* JADX WARN: Code duplicated, block: B:112:0x0210  */
    /* JADX WARN: Code duplicated, block: B:114:0x0214  */
    /* JADX WARN: Code duplicated, block: B:116:0x0222  */
    /* JADX WARN: Code duplicated, block: B:120:0x024b  */
    /* JADX WARN: Code duplicated, block: B:123:0x0271  */
    /* JADX WARN: Code duplicated, block: B:124:0x0279  */
    /* JADX WARN: Code duplicated, block: B:130:0x01dc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:131:0x01de A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:132:? A[LOOP:1: B:100:0x01c9->B:132:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:63:0x012e  */
    /* JADX WARN: Code duplicated, block: B:65:0x0132  */
    /* JADX WARN: Code duplicated, block: B:67:0x0147  */
    /* JADX WARN: Code duplicated, block: B:68:0x014a  */
    /* JADX WARN: Code duplicated, block: B:71:0x0164  */
    /* JADX WARN: Code duplicated, block: B:72:0x016c  */
    /* JADX WARN: Code duplicated, block: B:74:0x0170  */
    /* JADX WARN: Code duplicated, block: B:75:0x0178  */
    /* JADX WARN: Code duplicated, block: B:78:0x017d  */
    /* JADX WARN: Code duplicated, block: B:80:0x0186  */
    /* JADX WARN: Code duplicated, block: B:82:0x018a  */
    /* JADX WARN: Code duplicated, block: B:83:0x0192  */
    /* JADX WARN: Code duplicated, block: B:85:0x0196  */
    /* JADX WARN: Code duplicated, block: B:86:0x019e  */
    /* JADX WARN: Code duplicated, block: B:89:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:90:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:92:0x01ad  */
    /* JADX WARN: Code duplicated, block: B:94:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:96:0x01bb  */
    /* JADX WARN: Code duplicated, block: B:99:0x01c5  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirStatement resolveVariableAccessAndSelectCandidateImpl(FirQualifiedAccessExpression qualifiedAccess, boolean isUsedAsReceiver, final FirElement callSite, Function1<? super Collection<Candidate>, Boolean> acceptCandidates) {
        List<ConeDiagnostic> list;
        FirQualifiedAccessExpression firQualifiedAccessExpression;
        boolean z;
        Collection<Candidate> candidates;
        CallKind.Function function;
        FirNamedReference firNamedReferenceCreateResolvedNamedReference$default;
        FirBasedSymbol<?> candidateSymbol;
        ConeDiagnostic coneDiagnostic;
        ConeDiagnostic diagnostic;
        FirExpression explicitReceiver;
        FirResolvedQualifier firResolvedQualifier;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2;
        FirTypeParameterSymbol firTypeParameterSymbol;
        boolean z2;
        Collection<Candidate> collection;
        Iterator<T> it;
        FirResolvedQualifier firResolvedQualifierResolveRootPartOfQualifier;
        FirReference calleeReference = qualifiedAccess.getCalleeReference();
        final FirSimpleNamedReference firSimpleNamedReference = calleeReference instanceof FirSimpleNamedReference ? (FirSimpleNamedReference) calleeReference : null;
        if (firSimpleNamedReference == null) {
            return qualifiedAccess;
        }
        FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = this.transformer;
        if (firExpressionsResolveTransformer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer3 = null;
        }
        final FirQualifiedAccessExpression firQualifiedAccessExpressionTransformExplicitReceiver = firExpressionsResolveTransformer3.transformExplicitReceiver(qualifiedAccess);
        FirPropertyAccessExpression firPropertyAccessExpression = firQualifiedAccessExpressionTransformExplicitReceiver instanceof FirPropertyAccessExpression ? (FirPropertyAccessExpression) firQualifiedAccessExpressionTransformExplicitReceiver : null;
        List<ConeDiagnostic> nonFatalDiagnostics = firPropertyAccessExpression != null ? firPropertyAccessExpression.getNonFatalDiagnostics() : null;
        Lazy lazy = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<ResolutionResult>() { // from class: org.jetbrains.kotlin.fir.FirCallResolver$resolveVariableAccessAndSelectCandidateImpl$basicResult$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirCallResolver.ResolutionResult invoke() {
                return FirCallResolver.collectCandidates$default(this.this$0, firQualifiedAccessExpressionTransformExplicitReceiver, firSimpleNamedReference.getName(), null, null, null, null, null, callSite, 124, null);
            }
        });
        if (isUsedAsReceiver || !CandidateApplicabilityKt.isSuccess(resolveVariableAccessAndSelectCandidateImpl$lambda$6(lazy).getApplicability())) {
            FirExpression explicitReceiver2 = firQualifiedAccessExpressionTransformExplicitReceiver.getExplicitReceiver();
            FirResolvedQualifier firResolvedQualifier2 = explicitReceiver2 instanceof FirResolvedQualifier ? (FirResolvedQualifier) explicitReceiver2 : null;
            if (firResolvedQualifier2 != null) {
                FirResolvedQualifier firResolvedQualifierContinueQualifier = QualifiedNameResolutionKt.continueQualifier(firResolvedQualifier2, firSimpleNamedReference, firQualifiedAccessExpressionTransformExplicitReceiver.getSource(), firQualifiedAccessExpressionTransformExplicitReceiver.getTypeArguments(), nonFatalDiagnostics, this.session, this.components);
                if (firResolvedQualifierContinueQualifier != null) {
                    return firResolvedQualifierContinueQualifier;
                }
            }
        }
        ResolutionResult resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6 = resolveVariableAccessAndSelectCandidateImpl$lambda$6(lazy);
        if (firQualifiedAccessExpressionTransformExplicitReceiver.getExplicitReceiver() == null) {
            if (!CandidateApplicabilityKt.isSuccess(resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getApplicability())) {
                firResolvedQualifierResolveRootPartOfQualifier = QualifiedNameResolutionKt.resolveRootPartOfQualifier(this.components, firSimpleNamedReference, firQualifiedAccessExpressionTransformExplicitReceiver.getSource(), firQualifiedAccessExpressionTransformExplicitReceiver.getTypeArguments(), nonFatalDiagnostics);
                if (firResolvedQualifierResolveRootPartOfQualifier != null) {
                    return firResolvedQualifierResolveRootPartOfQualifier;
                }
            } else if (isUsedAsReceiver) {
                Collection<Candidate> candidates2 = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getCandidates();
                if ((candidates2 instanceof Collection) && candidates2.isEmpty()) {
                    firResolvedQualifierResolveRootPartOfQualifier = QualifiedNameResolutionKt.resolveRootPartOfQualifier(this.components, firSimpleNamedReference, firQualifiedAccessExpressionTransformExplicitReceiver.getSource(), firQualifiedAccessExpressionTransformExplicitReceiver.getTypeArguments(), nonFatalDiagnostics);
                    if (firResolvedQualifierResolveRootPartOfQualifier != null) {
                        return firResolvedQualifierResolveRootPartOfQualifier;
                    }
                } else {
                    Iterator<T> it2 = candidates2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (!(((Candidate) it2.next()).getSymbol() instanceof FirClassLikeSymbol)) {
                            }
                        } else {
                            firResolvedQualifierResolveRootPartOfQualifier = QualifiedNameResolutionKt.resolveRootPartOfQualifier(this.components, firSimpleNamedReference, firQualifiedAccessExpressionTransformExplicitReceiver.getSource(), firQualifiedAccessExpressionTransformExplicitReceiver.getTypeArguments(), nonFatalDiagnostics);
                            if (firResolvedQualifierResolveRootPartOfQualifier != null) {
                                return firResolvedQualifierResolveRootPartOfQualifier;
                            }
                        }
                    }
                }
            }
        }
        if (resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getCandidates().isEmpty() && !(firQualifiedAccessExpressionTransformExplicitReceiver instanceof FirFunctionCall)) {
            list = nonFatalDiagnostics;
            firQualifiedAccessExpression = firQualifiedAccessExpressionTransformExplicitReceiver;
            ResolutionResult resolutionResultCollectCandidates$default = collectCandidates$default(this, firQualifiedAccessExpressionTransformExplicitReceiver, firSimpleNamedReference.getName(), CallKind.Function.INSTANCE, null, null, null, null, null, 248, null);
            if (!resolutionResultCollectCandidates$default.getCandidates().isEmpty()) {
                resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6 = resolutionResultCollectCandidates$default;
                z = true;
            }
            candidates = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getCandidates();
            if (!acceptCandidates.invoke(candidates).booleanValue()) {
                return firQualifiedAccessExpression;
            }
            FirSimpleNamedReference firSimpleNamedReference2 = firSimpleNamedReference;
            Name name2 = firSimpleNamedReference.getName();
            CallInfo info = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getInfo();
            CandidateApplicability applicability = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getApplicability();
            FirExpression explicitReceiver3 = firQualifiedAccessExpression.getExplicitReceiver();
            if (z) {
                function = CallKind.Function.INSTANCE;
            } else {
                function = null;
            }
            firNamedReferenceCreateResolvedNamedReference$default = createResolvedNamedReference$default(this, firSimpleNamedReference2, name2, info, candidates, applicability, explicitReceiver3, false, function, null, 320, null);
            if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirResolvedNamedReference) {
                candidateSymbol = ((FirResolvedNamedReference) firNamedReferenceCreateResolvedNamedReference$default).getResolvedSymbol();
            } else if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirNamedReferenceWithCandidate) {
                candidateSymbol = ((FirNamedReferenceWithCandidate) firNamedReferenceCreateResolvedNamedReference$default).getCandidateSymbol();
            } else {
                candidateSymbol = null;
            }
            if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirErrorReferenceWithCandidate) {
                diagnostic = ((FirErrorReferenceWithCandidate) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
            } else {
                if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirResolvedErrorReference) {
                    if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirErrorNamedReference) {
                        diagnostic = ((FirErrorNamedReference) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
                    } else {
                        coneDiagnostic = null;
                    }
                    explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
                    if (explicitReceiver instanceof FirResolvedQualifier) {
                        firResolvedQualifier = (FirResolvedQualifier) explicitReceiver;
                    } else {
                        firResolvedQualifier = null;
                    }
                    if (firResolvedQualifier != null) {
                        if (candidates.isEmpty()) {
                            z2 = false;
                        } else {
                            collection = candidates;
                            if ((collection instanceof Collection) || !collection.isEmpty()) {
                                it = collection.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                            z2 = false;
                                        }
                                    }
                                }
                            }
                            z2 = true;
                        }
                        firResolvedQualifier.replaceResolvedToCompanionObject(z2);
                    }
                    if (candidateSymbol instanceof FirClassLikeSymbol) {
                        FirClassLikeSymbol firClassLikeSymbol = (FirClassLikeSymbol) candidateSymbol;
                        return ResolveUtilsKt.buildResolvedQualifierForClass(this.components, firClassLikeSymbol, firQualifiedAccessExpression.getSource(), firQualifiedAccessExpression.getTypeArguments(), coneDiagnostic, QualifiedNameResolutionKt.extractNonFatalDiagnostics(firNamedReferenceCreateResolvedNamedReference$default.getSource(), firQualifiedAccessExpression.getExplicitReceiver(), firClassLikeSymbol, list, this.session), firQualifiedAccessExpression.getAnnotations());
                    }
                    if (candidateSymbol instanceof FirTypeParameterSymbol) {
                        firTypeParameterSymbol = (FirTypeParameterSymbol) candidateSymbol;
                        if (((FirTypeParameter) firTypeParameterSymbol.getFir()).getIsReified()) {
                            FirResolvedReifiedParameterReferenceBuilder firResolvedReifiedParameterReferenceBuilder = new FirResolvedReifiedParameterReferenceBuilder();
                            firResolvedReifiedParameterReferenceBuilder.setSource(firNamedReferenceCreateResolvedNamedReference$default.getSource());
                            firResolvedReifiedParameterReferenceBuilder.setSymbol(firTypeParameterSymbol);
                            firResolvedReifiedParameterReferenceBuilder.setTypeRef(ResolveUtilsKt.typeForReifiedParameterReference(firResolvedReifiedParameterReferenceBuilder));
                            return firResolvedReifiedParameterReferenceBuilder.mo6809build();
                        }
                    }
                    firQualifiedAccessExpression.replaceCalleeReference(firNamedReferenceCreateResolvedNamedReference$default);
                    if (candidates.size() == 1) {
                        Candidate candidate = (Candidate) CollectionsKt.single(candidates);
                        candidate.updateSourcesOfReceivers();
                        firQualifiedAccessExpression.replaceDispatchReceiver(candidate.dispatchReceiverExpression());
                        firQualifiedAccessExpression.replaceExtensionReceiver(candidate.chosenExtensionReceiverExpression());
                        firQualifiedAccessExpression.replaceContextReceiverArguments(candidate.contextReceiverArguments());
                    }
                    firExpressionsResolveTransformer = this.transformer;
                    if (firExpressionsResolveTransformer == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("transformer");
                        firExpressionsResolveTransformer2 = null;
                    } else {
                        firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
                    }
                    firExpressionsResolveTransformer2.storeTypeFromCallee$resolve(firQualifiedAccessExpression, callSite instanceof FirVariableAssignment);
                    return firQualifiedAccessExpression;
                }
                diagnostic = ((FirResolvedErrorReference) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
            }
            coneDiagnostic = diagnostic;
            explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
            if (explicitReceiver instanceof FirResolvedQualifier) {
                firResolvedQualifier = (FirResolvedQualifier) explicitReceiver;
            } else {
                firResolvedQualifier = null;
            }
            if (firResolvedQualifier != null) {
                if (candidates.isEmpty()) {
                    collection = candidates;
                    if (collection instanceof Collection) {
                        it = collection.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                    z2 = false;
                                }
                            }
                        }
                    } else {
                        it = collection.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                    z2 = false;
                                }
                            }
                        }
                    }
                    z2 = true;
                } else {
                    z2 = false;
                }
                firResolvedQualifier.replaceResolvedToCompanionObject(z2);
            }
            if (candidateSymbol instanceof FirClassLikeSymbol) {
                FirClassLikeSymbol firClassLikeSymbol2 = (FirClassLikeSymbol) candidateSymbol;
                return ResolveUtilsKt.buildResolvedQualifierForClass(this.components, firClassLikeSymbol2, firQualifiedAccessExpression.getSource(), firQualifiedAccessExpression.getTypeArguments(), coneDiagnostic, QualifiedNameResolutionKt.extractNonFatalDiagnostics(firNamedReferenceCreateResolvedNamedReference$default.getSource(), firQualifiedAccessExpression.getExplicitReceiver(), firClassLikeSymbol2, list, this.session), firQualifiedAccessExpression.getAnnotations());
            }
            if (candidateSymbol instanceof FirTypeParameterSymbol) {
                firTypeParameterSymbol = (FirTypeParameterSymbol) candidateSymbol;
                if (((FirTypeParameter) firTypeParameterSymbol.getFir()).getIsReified()) {
                    FirResolvedReifiedParameterReferenceBuilder firResolvedReifiedParameterReferenceBuilder2 = new FirResolvedReifiedParameterReferenceBuilder();
                    firResolvedReifiedParameterReferenceBuilder2.setSource(firNamedReferenceCreateResolvedNamedReference$default.getSource());
                    firResolvedReifiedParameterReferenceBuilder2.setSymbol(firTypeParameterSymbol);
                    firResolvedReifiedParameterReferenceBuilder2.setTypeRef(ResolveUtilsKt.typeForReifiedParameterReference(firResolvedReifiedParameterReferenceBuilder2));
                    return firResolvedReifiedParameterReferenceBuilder2.mo6809build();
                }
            }
            firQualifiedAccessExpression.replaceCalleeReference(firNamedReferenceCreateResolvedNamedReference$default);
            if (candidates.size() == 1) {
                Candidate candidate2 = (Candidate) CollectionsKt.single(candidates);
                candidate2.updateSourcesOfReceivers();
                firQualifiedAccessExpression.replaceDispatchReceiver(candidate2.dispatchReceiverExpression());
                firQualifiedAccessExpression.replaceExtensionReceiver(candidate2.chosenExtensionReceiverExpression());
                firQualifiedAccessExpression.replaceContextReceiverArguments(candidate2.contextReceiverArguments());
            }
            firExpressionsResolveTransformer = this.transformer;
            if (firExpressionsResolveTransformer == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer2 = null;
            } else {
                firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
            }
            firExpressionsResolveTransformer2.storeTypeFromCallee$resolve(firQualifiedAccessExpression, callSite instanceof FirVariableAssignment);
            return firQualifiedAccessExpression;
        }
        list = nonFatalDiagnostics;
        firQualifiedAccessExpression = firQualifiedAccessExpressionTransformExplicitReceiver;
        z = false;
        candidates = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getCandidates();
        if (!acceptCandidates.invoke(candidates).booleanValue()) {
            return firQualifiedAccessExpression;
        }
        FirSimpleNamedReference firSimpleNamedReference3 = firSimpleNamedReference;
        Name name3 = firSimpleNamedReference.getName();
        CallInfo info2 = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getInfo();
        CandidateApplicability applicability2 = resolutionResultResolveVariableAccessAndSelectCandidateImpl$lambda$6.getApplicability();
        FirExpression explicitReceiver4 = firQualifiedAccessExpression.getExplicitReceiver();
        if (z) {
            function = CallKind.Function.INSTANCE;
        } else {
            function = null;
        }
        firNamedReferenceCreateResolvedNamedReference$default = createResolvedNamedReference$default(this, firSimpleNamedReference3, name3, info2, candidates, applicability2, explicitReceiver4, false, function, null, 320, null);
        if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirResolvedNamedReference) {
            candidateSymbol = ((FirResolvedNamedReference) firNamedReferenceCreateResolvedNamedReference$default).getResolvedSymbol();
        } else if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirNamedReferenceWithCandidate) {
            candidateSymbol = ((FirNamedReferenceWithCandidate) firNamedReferenceCreateResolvedNamedReference$default).getCandidateSymbol();
        } else {
            candidateSymbol = null;
        }
        if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirErrorReferenceWithCandidate) {
            diagnostic = ((FirErrorReferenceWithCandidate) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
        } else {
            if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirResolvedErrorReference) {
                if (firNamedReferenceCreateResolvedNamedReference$default instanceof FirErrorNamedReference) {
                    diagnostic = ((FirErrorNamedReference) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
                } else {
                    coneDiagnostic = null;
                }
                explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
                if (explicitReceiver instanceof FirResolvedQualifier) {
                    firResolvedQualifier = (FirResolvedQualifier) explicitReceiver;
                } else {
                    firResolvedQualifier = null;
                }
                if (firResolvedQualifier != null) {
                    if (candidates.isEmpty()) {
                        collection = candidates;
                        if (collection instanceof Collection) {
                            it = collection.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                        z2 = false;
                                    }
                                }
                            }
                        } else {
                            it = collection.iterator();
                            while (true) {
                                if (it.hasNext()) {
                                    if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                        z2 = false;
                                    }
                                }
                            }
                        }
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    firResolvedQualifier.replaceResolvedToCompanionObject(z2);
                }
                if (candidateSymbol instanceof FirClassLikeSymbol) {
                    FirClassLikeSymbol firClassLikeSymbol3 = (FirClassLikeSymbol) candidateSymbol;
                    return ResolveUtilsKt.buildResolvedQualifierForClass(this.components, firClassLikeSymbol3, firQualifiedAccessExpression.getSource(), firQualifiedAccessExpression.getTypeArguments(), coneDiagnostic, QualifiedNameResolutionKt.extractNonFatalDiagnostics(firNamedReferenceCreateResolvedNamedReference$default.getSource(), firQualifiedAccessExpression.getExplicitReceiver(), firClassLikeSymbol3, list, this.session), firQualifiedAccessExpression.getAnnotations());
                }
                if (candidateSymbol instanceof FirTypeParameterSymbol) {
                    firTypeParameterSymbol = (FirTypeParameterSymbol) candidateSymbol;
                    if (((FirTypeParameter) firTypeParameterSymbol.getFir()).getIsReified()) {
                        FirResolvedReifiedParameterReferenceBuilder firResolvedReifiedParameterReferenceBuilder3 = new FirResolvedReifiedParameterReferenceBuilder();
                        firResolvedReifiedParameterReferenceBuilder3.setSource(firNamedReferenceCreateResolvedNamedReference$default.getSource());
                        firResolvedReifiedParameterReferenceBuilder3.setSymbol(firTypeParameterSymbol);
                        firResolvedReifiedParameterReferenceBuilder3.setTypeRef(ResolveUtilsKt.typeForReifiedParameterReference(firResolvedReifiedParameterReferenceBuilder3));
                        return firResolvedReifiedParameterReferenceBuilder3.mo6809build();
                    }
                }
                firQualifiedAccessExpression.replaceCalleeReference(firNamedReferenceCreateResolvedNamedReference$default);
                if (candidates.size() == 1) {
                    Candidate candidate3 = (Candidate) CollectionsKt.single(candidates);
                    candidate3.updateSourcesOfReceivers();
                    firQualifiedAccessExpression.replaceDispatchReceiver(candidate3.dispatchReceiverExpression());
                    firQualifiedAccessExpression.replaceExtensionReceiver(candidate3.chosenExtensionReceiverExpression());
                    firQualifiedAccessExpression.replaceContextReceiverArguments(candidate3.contextReceiverArguments());
                }
                firExpressionsResolveTransformer = this.transformer;
                if (firExpressionsResolveTransformer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transformer");
                    firExpressionsResolveTransformer2 = null;
                } else {
                    firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
                }
                firExpressionsResolveTransformer2.storeTypeFromCallee$resolve(firQualifiedAccessExpression, callSite instanceof FirVariableAssignment);
                return firQualifiedAccessExpression;
            }
            diagnostic = ((FirResolvedErrorReference) firNamedReferenceCreateResolvedNamedReference$default).getDiagnostic();
        }
        coneDiagnostic = diagnostic;
        explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
        if (explicitReceiver instanceof FirResolvedQualifier) {
            firResolvedQualifier = (FirResolvedQualifier) explicitReceiver;
        } else {
            firResolvedQualifier = null;
        }
        if (firResolvedQualifier != null) {
            if (candidates.isEmpty()) {
                collection = candidates;
                if (collection instanceof Collection) {
                    it = collection.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                z2 = false;
                            }
                        }
                    }
                } else {
                    it = collection.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                z2 = false;
                            }
                        }
                    }
                }
                z2 = true;
            } else {
                z2 = false;
            }
            firResolvedQualifier.replaceResolvedToCompanionObject(z2);
        }
        if (candidateSymbol instanceof FirClassLikeSymbol) {
            FirClassLikeSymbol firClassLikeSymbol4 = (FirClassLikeSymbol) candidateSymbol;
            return ResolveUtilsKt.buildResolvedQualifierForClass(this.components, firClassLikeSymbol4, firQualifiedAccessExpression.getSource(), firQualifiedAccessExpression.getTypeArguments(), coneDiagnostic, QualifiedNameResolutionKt.extractNonFatalDiagnostics(firNamedReferenceCreateResolvedNamedReference$default.getSource(), firQualifiedAccessExpression.getExplicitReceiver(), firClassLikeSymbol4, list, this.session), firQualifiedAccessExpression.getAnnotations());
        }
        if (candidateSymbol instanceof FirTypeParameterSymbol) {
            firTypeParameterSymbol = (FirTypeParameterSymbol) candidateSymbol;
            if (((FirTypeParameter) firTypeParameterSymbol.getFir()).getIsReified()) {
                FirResolvedReifiedParameterReferenceBuilder firResolvedReifiedParameterReferenceBuilder4 = new FirResolvedReifiedParameterReferenceBuilder();
                firResolvedReifiedParameterReferenceBuilder4.setSource(firNamedReferenceCreateResolvedNamedReference$default.getSource());
                firResolvedReifiedParameterReferenceBuilder4.setSymbol(firTypeParameterSymbol);
                firResolvedReifiedParameterReferenceBuilder4.setTypeRef(ResolveUtilsKt.typeForReifiedParameterReference(firResolvedReifiedParameterReferenceBuilder4));
                return firResolvedReifiedParameterReferenceBuilder4.mo6809build();
            }
        }
        firQualifiedAccessExpression.replaceCalleeReference(firNamedReferenceCreateResolvedNamedReference$default);
        if (candidates.size() == 1) {
            Candidate candidate4 = (Candidate) CollectionsKt.single(candidates);
            candidate4.updateSourcesOfReceivers();
            firQualifiedAccessExpression.replaceDispatchReceiver(candidate4.dispatchReceiverExpression());
            firQualifiedAccessExpression.replaceExtensionReceiver(candidate4.chosenExtensionReceiverExpression());
            firQualifiedAccessExpression.replaceContextReceiverArguments(candidate4.contextReceiverArguments());
        }
        firExpressionsResolveTransformer = this.transformer;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer2 = null;
        } else {
            firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
        }
        firExpressionsResolveTransformer2.storeTypeFromCallee$resolve(firQualifiedAccessExpression, callSite instanceof FirVariableAssignment);
        return firQualifiedAccessExpression;
    }

    private static final ResolutionResult resolveVariableAccessAndSelectCandidateImpl$lambda$6(Lazy<ResolutionResult> lazy) {
        return lazy.getValue();
    }

    public final Pair<CandidateApplicability, Boolean> resolveCallableReference(ConstraintSystemBuilder constraintSystemBuilder, ResolvedCallableReferenceAtom resolvedCallableReferenceAtom) {
        CandidateCollector candidateCollectorRunResolver;
        ConeUnresolvedReferenceError coneUnresolvedReferenceError;
        String message;
        List<ResolutionDiagnostic> diagnostics;
        boolean z;
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(resolvedCallableReferenceAtom, "resolvedCallableReferenceAtom");
        FirCallableReferenceAccess reference = resolvedCallableReferenceAtom.getReference();
        DoubleColonLHS lhs = resolvedCallableReferenceAtom.getLhs();
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = constraintSystemBuilder.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ConeSubstitutor coneSubstitutor = (ConeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor;
        ConeKotlinType expectedType = resolvedCallableReferenceAtom.getExpectedType();
        CallInfo callInfoCreateCallableReferencesInfoForLHS = createCallableReferencesInfoForLHS(reference, lhs, expectedType != null ? coneSubstitutor.substituteOrSelf(expectedType) : null, constraintSystemBuilder);
        FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents = this.components;
        CandidateCollector candidateCollector = new CandidateCollector(bodyResolveTransformerComponents, bodyResolveTransformerComponents.getResolutionStageRunner());
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer = null;
        }
        BodyResolveContext context = firExpressionsResolveTransformer.getTransformer().getContext();
        FirTowerDataContext callableReferenceContext = context.getSpecialTowerDataContexts().getCallableReferenceContext(reference);
        if (callableReferenceContext == null) {
            FirTowerResolver firTowerResolver = this.towerResolver;
            FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = this.transformer;
            if (firExpressionsResolveTransformer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer2 = null;
            }
            candidateCollectorRunResolver = firTowerResolver.runResolver(callInfoCreateCallableReferencesInfoForLHS, firExpressionsResolveTransformer2.getTransformer().getResolutionContext(), candidateCollector, new TowerResolveManager(candidateCollector));
        } else {
            FirTowerDataMode towerDataMode = context.getTowerDataMode();
            try {
                FirRegularTowerDataContexts firRegularTowerDataContextsReplaceAndSetActiveRegularContext = context.getRegularTowerDataContexts().replaceAndSetActiveRegularContext(callableReferenceContext);
                FirRegularTowerDataContexts regularTowerDataContexts = context.getRegularTowerDataContexts();
                context.setRegularTowerDataContexts(firRegularTowerDataContextsReplaceAndSetActiveRegularContext);
                try {
                    FirTowerResolver firTowerResolver2 = this.towerResolver;
                    FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = this.transformer;
                    if (firExpressionsResolveTransformer3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("transformer");
                        firExpressionsResolveTransformer3 = null;
                    }
                    candidateCollectorRunResolver = firTowerResolver2.runResolver(callInfoCreateCallableReferencesInfoForLHS, firExpressionsResolveTransformer3.getTransformer().getResolutionContext(), candidateCollector, new TowerResolveManager(candidateCollector));
                    context.setRegularTowerDataContexts(regularTowerDataContexts);
                    context.setTowerDataMode(towerDataMode);
                } catch (Throwable th) {
                    context.setRegularTowerDataContexts(regularTowerDataContexts);
                    throw th;
                }
            } catch (Throwable th2) {
                context.setTowerDataMode(towerDataMode);
                throw th2;
            }
        }
        CandidateCollector candidateCollector2 = candidateCollectorRunResolver;
        boolean zIsSuccess = CandidateApplicabilityKt.isSuccess(candidateCollector2.getCurrentApplicability());
        Pair pairReduceCandidates$default = reduceCandidates$default(this, candidateCollector2, reference.getExplicitReceiver(), null, 4, null);
        Set set = (Set) pairReduceCandidates$default.component1();
        CandidateApplicability currentApplicability = (CandidateApplicability) pairReduceCandidates$default.component2();
        if (currentApplicability == null) {
            currentApplicability = candidateCollector2.getCurrentApplicability();
        }
        CandidateApplicability candidateApplicability = currentApplicability;
        FirExpression explicitReceiver = reference.getExplicitReceiver();
        FirResolvedQualifier firResolvedQualifier = explicitReceiver instanceof FirResolvedQualifier ? (FirResolvedQualifier) explicitReceiver : null;
        if (firResolvedQualifier != null) {
            if (set.isEmpty()) {
                z = false;
            } else {
                Set set2 = set;
                if (!(set2 instanceof Collection) || !set2.isEmpty()) {
                    Iterator it = set2.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!((Candidate) it.next()).getIsFromCompanionObjectTypeScope()) {
                                z = false;
                            }
                        }
                    }
                }
                z = true;
            }
            firResolvedQualifier.replaceResolvedToCompanionObject(z);
        }
        resolvedCallableReferenceAtom.setHasBeenResolvedOnce(true);
        if (!zIsSuccess) {
            if (candidateApplicability == CandidateApplicability.K2_UNSUPPORTED) {
                Candidate candidate = (Candidate) CollectionsKt.firstOrNull(set);
                ResolutionDiagnostic resolutionDiagnostic = (candidate == null || (diagnostics = candidate.getDiagnostics()) == null) ? null : (ResolutionDiagnostic) CollectionsKt.firstOrNull((List) diagnostics);
                Unsupported unsupported = resolutionDiagnostic instanceof Unsupported ? (Unsupported) resolutionDiagnostic : null;
                if (unsupported == null || (message = unsupported.getMessage()) == null) {
                    message = "";
                }
                coneUnresolvedReferenceError = new ConeUnsupported(message, unsupported != null ? unsupported.getSource() : null);
            } else {
                coneUnresolvedReferenceError = new ConeUnresolvedReferenceError(callInfoCreateCallableReferencesInfoForLHS.getName());
            }
            resolvedCallableReferenceAtom.setResultingReference(buildReferenceWithErrorCandidate(callInfoCreateCallableReferencesInfoForLHS, coneUnresolvedReferenceError, reference.getSource()));
            return TuplesKt.to(candidateApplicability, false);
        }
        if (set.size() > 1) {
            if (resolvedCallableReferenceAtom.getHasBeenPostponed()) {
                resolvedCallableReferenceAtom.setResultingReference(buildReferenceWithErrorCandidate(callInfoCreateCallableReferencesInfoForLHS, new ConeAmbiguityError(callInfoCreateCallableReferencesInfoForLHS.getName(), candidateApplicability, set), reference.getSource()));
                return TuplesKt.to(candidateApplicability, false);
            }
            resolvedCallableReferenceAtom.setHasBeenPostponed(true);
            return TuplesKt.to(candidateApplicability, true);
        }
        Candidate candidate2 = (Candidate) CollectionsKt.single(set);
        candidate2.updateSourcesOfReceivers();
        ConstraintSystemTransaction constraintSystemTransactionPrepareTransaction = constraintSystemBuilder.prepareTransaction();
        Function1<ConstraintSystemOperation, Unit> outerConstraintBuilderEffect = candidate2.getOuterConstraintBuilderEffect();
        Intrinsics.checkNotNull(outerConstraintBuilderEffect);
        outerConstraintBuilderEffect.invoke(constraintSystemBuilder);
        constraintSystemTransactionPrepareTransaction.closeTransaction();
        resolvedCallableReferenceAtom.setResultingReference(createResolvedNamedReference$default(this, reference.getCalleeReference(), callInfoCreateCallableReferencesInfoForLHS.getName(), callInfoCreateCallableReferencesInfoForLHS, set, candidateApplicability, null, false, null, null, 416, null));
        resolvedCallableReferenceAtom.setResultingTypeForCallableReference(candidate2.getResultingTypeForCallableReference());
        return TuplesKt.to(candidateApplicability, true);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00ad  */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirDelegatedConstructorCall resolveDelegatingConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ConeClassLikeType constructedType, ConeClassLikeLookupTag derivedClassLookupTag) {
        List listEmptyList;
        ConeTypeProjection[] typeArguments;
        List<FirTypeParameterRef> typeParameters;
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        Intrinsics.checkNotNullParameter(derivedClassLookupTag, "derivedClassLookupTag");
        Name name2 = SpecialNames.INIT;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = null;
        FirClassLikeSymbol<?> symbol = (constructedType == null || (lookupTag = constructedType.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, this.components.getSession());
        if (constructedType != null && (typeArguments = constructedType.getTypeArguments()) != null) {
            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
            FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            int i = 0;
            if (firRegularClass != null && (typeParameters = firRegularClass.getTypeParameters()) != null) {
                List<FirTypeParameterRef> list = typeParameters;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        if ((((FirTypeParameterRef) it.next()) instanceof FirTypeParameter) && (i = i + 1) < 0) {
                            CollectionsKt.throwCountOverflow();
                        }
                    }
                }
            }
            List listTake = ArraysKt.take(typeArguments, i);
            if (listTake == null) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                List list2 = listTake;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    arrayList.add(toFirTypeProjection((ConeTypeProjection) it2.next()));
                }
                listEmptyList = arrayList;
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        CallInfo callInfo = new CallInfo(delegatedConstructorCall, CallKind.DelegatingConstructorCall.INSTANCE, name2, null, delegatedConstructorCall.getArgumentList(), false, listEmptyList, this.session, this.components.getFile(), this.components.getContainingDeclarations(), null, null, null, null, null, 31744, null);
        this.towerResolver.reset();
        if (constructedType == null) {
            ConeSimpleDiagnostic coneSimpleDiagnostic = new ConeSimpleDiagnostic("Erroneous delegated constructor call", DiagnosticKind.UnresolvedSupertype);
            KtSourceElement source = delegatedConstructorCall.getCalleeReference().getSource();
            FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = this.transformer;
            if (firExpressionsResolveTransformer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
            } else {
                firExpressionsResolveTransformer = firExpressionsResolveTransformer2;
            }
            delegatedConstructorCall.replaceCalleeReference(ErrorCandidateUtilsKt.createErrorReferenceWithErrorCandidate(callInfo, coneSimpleDiagnostic, source, firExpressionsResolveTransformer.getTransformer().getResolutionContext(), this.components.getResolutionStageRunner()));
            return delegatedConstructorCall;
        }
        FirTowerResolver firTowerResolver = this.towerResolver;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = this.transformer;
        if (firExpressionsResolveTransformer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
        } else {
            firExpressionsResolveTransformer = firExpressionsResolveTransformer3;
        }
        return selectDelegatingConstructorCall(delegatedConstructorCall, name2, firTowerResolver.runResolverForDelegatingConstructor(callInfo, constructedType, derivedClassLookupTag, firExpressionsResolveTransformer.getTransformer().getResolutionContext()), callInfo);
    }

    private final FirTypeProjection toFirTypeProjection(ConeTypeProjection coneTypeProjection) {
        ConeKotlinType type;
        Variance variance;
        boolean z = coneTypeProjection instanceof ConeStarProjection;
        if (!z) {
            if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
                type = ((ConeKotlinTypeProjectionIn) coneTypeProjection).getType();
            } else if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
                type = ((ConeKotlinTypeProjectionOut) coneTypeProjection).getType();
            } else {
                if (z) {
                    throw new IllegalStateException();
                }
                Intrinsics.checkNotNull(coneTypeProjection, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                type = (ConeKotlinType) coneTypeProjection;
            }
            FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(type);
            firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
            int i = WhenMappings.$EnumSwitchMapping$0[coneTypeProjection.getKind().ordinal()];
            if (i == 1) {
                variance = Variance.IN_VARIANCE;
            } else if (i == 2) {
                variance = Variance.OUT_VARIANCE;
            } else {
                if (i != 3) {
                    if (i != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                    throw new IllegalStateException();
                }
                variance = Variance.INVARIANT;
            }
            firTypeProjectionWithVarianceBuilder.setVariance(variance);
            return firTypeProjectionWithVarianceBuilder.build();
        }
        return new FirStarProjectionBuilder().build();
    }

    /* JADX WARN: Code duplicated, block: B:43:0x0113  */
    /* JADX WARN: Code duplicated, block: B:45:0x011b  */
    /* JADX WARN: Code duplicated, block: B:46:0x011f  */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirAnnotationCall resolveAnnotationCall(FirAnnotationCall annotation) {
        ConeStubDiagnostic coneStubDiagnostic;
        FirErrorReferenceWithCandidate firErrorReferenceWithCandidateBuildReferenceWithErrorCandidate;
        Object obj;
        ResolutionResult resolutionResult;
        ResolutionMode.ContextDependent.Companion withExpectedType;
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        FirReference calleeReference = annotation.getCalleeReference();
        Object obj2 = null;
        FirSimpleNamedReference firSimpleNamedReference = calleeReference instanceof FirSimpleNamedReference ? (FirSimpleNamedReference) calleeReference : null;
        if (firSimpleNamedReference == null) {
            return null;
        }
        FirRegularClassSymbol correspondingClassSymbolOrNull = ResolveUtilsKt.getCorrespondingClassSymbolOrNull(annotation, this.session);
        if (correspondingClassSymbolOrNull != null && ((FirRegularClass) correspondingClassSymbolOrNull.getFir()).getClassKind() == ClassKind.ANNOTATION_CLASS) {
            FirConstructorSymbol constructorSymbol = getConstructorSymbol(correspondingClassSymbolOrNull);
            if (constructorSymbol != null) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(constructorSymbol, FirResolvePhase.TYPES);
            }
            if (constructorSymbol != null) {
                FirAnnotationCall firAnnotationCall = annotation;
                if (!firAnnotationCall.getArgumentList().getArguments().isEmpty()) {
                    FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
                    if (firExpressionsResolveTransformer == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("transformer");
                        firExpressionsResolveTransformer = null;
                    }
                    LinkedHashMap<FirExpression, FirValueParameter> argumentToParameterMapping = FirArgumentsToParametersMapperKt.mapArguments(firExpressionsResolveTransformer.getTransformer().getResolutionContext().getBodyResolveComponents(), firAnnotationCall.getArgumentList().getArguments(), (FirFunction) constructorSymbol.getFir(), null, false).toArgumentToParameterMapping();
                    FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                    firArgumentListBuilder.setSource(annotation.getArgumentList().getSource());
                    List<FirExpression> arguments = firAnnotationCall.getArgumentList().getArguments();
                    List<FirExpression> arguments2 = firArgumentListBuilder.getArguments();
                    for (FirExpression firExpression : arguments) {
                        if (FirExpressionUtilKt.unwrapArgument(firExpression) instanceof FirArrayLiteral) {
                            FirValueParameter firValueParameter = argumentToParameterMapping.get(firExpression);
                            FirTypeRef returnTypeRef = firValueParameter != null ? firValueParameter.getReturnTypeRef() : null;
                            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
                            if (firResolvedTypeRef != null) {
                                withExpectedType = new ResolutionMode.WithExpectedType(firResolvedTypeRef, false, true, false, false, false, 26, null);
                            } else {
                                withExpectedType = ResolutionMode.ContextDependent.INSTANCE;
                            }
                        } else {
                            withExpectedType = ResolutionMode.ContextDependent.INSTANCE;
                        }
                        FirExpression firExpression2 = firExpression;
                        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = this.transformer;
                        if (firExpressionsResolveTransformer2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("transformer");
                            firExpressionsResolveTransformer2 = null;
                        }
                        arguments2.add((FirExpression) FirTransformerUtilKt.transformSingle(firExpression2, firExpressionsResolveTransformer2, withExpectedType));
                    }
                    annotation.replaceArgumentList(firArgumentListBuilder.build());
                } else {
                    FirArgumentList argumentList = annotation.getArgumentList();
                    obj = this.transformer;
                    if (obj == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("transformer");
                    } else {
                        obj2 = obj;
                    }
                    annotation.replaceArgumentList((FirArgumentList) argumentList.transform((FirTransformer) obj2, ResolutionMode.ContextDependent.INSTANCE));
                }
            } else {
                FirArgumentList argumentList2 = annotation.getArgumentList();
                obj = this.transformer;
                if (obj == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("transformer");
                } else {
                    obj2 = obj;
                }
                annotation.replaceArgumentList((FirArgumentList) argumentList2.transform((FirTransformer) obj2, ResolutionMode.ContextDependent.INSTANCE));
            }
            CallInfo callInfo = toCallInfo(annotation, firSimpleNamedReference);
            if (constructorSymbol == null || (resolutionResult = runResolutionForGivenSymbol(callInfo, constructorSymbol)) == null) {
                resolutionResult = new ResolutionResult(callInfo, CandidateApplicability.HIDDEN, CollectionsKt.emptyList());
            }
            firErrorReferenceWithCandidateBuildReferenceWithErrorCandidate = createResolvedNamedReference$default(this, firSimpleNamedReference, firSimpleNamedReference.getName(), callInfo, resolutionResult.getCandidates(), resolutionResult.getApplicability(), null, false, null, null, XKeySym.XK_Racute, null);
        } else {
            FirArgumentList argumentList3 = annotation.getArgumentList();
            FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = this.transformer;
            if (firExpressionsResolveTransformer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("transformer");
                firExpressionsResolveTransformer3 = null;
            }
            annotation.replaceArgumentList((FirArgumentList) argumentList3.transform(firExpressionsResolveTransformer3, ResolutionMode.ContextDependent.INSTANCE));
            CallInfo callInfo2 = toCallInfo(annotation, firSimpleNamedReference);
            if (correspondingClassSymbolOrNull != null) {
                coneStubDiagnostic = new ConeIllegalAnnotationError(firSimpleNamedReference.getName());
            } else {
                ConeKotlinType coneType = FirTypeUtilsKt.getConeType(annotation.getTypeRef());
                ConeErrorType coneErrorType = coneType instanceof ConeErrorType ? (ConeErrorType) coneType : null;
                Object diagnostic = coneErrorType != null ? coneErrorType.getDiagnostic() : null;
                obj2 = diagnostic instanceof ConeDiagnosticWithSymbol ? (ConeDiagnosticWithSymbol) diagnostic : null;
                coneStubDiagnostic = new ConeStubDiagnostic(obj2 != null ? (ConeDiagnostic) obj2 : new ConeUnresolvedNameError(firSimpleNamedReference.getName()));
            }
            firErrorReferenceWithCandidateBuildReferenceWithErrorCandidate = buildReferenceWithErrorCandidate(callInfo2, coneStubDiagnostic, firSimpleNamedReference.getSource());
        }
        annotation.replaceCalleeReference(firErrorReferenceWithCandidateBuildReferenceWithErrorCandidate);
        return annotation;
    }

    private final CallInfo toCallInfo(FirAnnotationCall annotation, FirSimpleNamedReference reference) {
        return new CallInfo(annotation, CallKind.Function.INSTANCE, reference.getName(), null, annotation.getArgumentList(), false, annotation.getTypeArguments(), this.session, this.components.getFile(), this.components.getContainingDeclarations(), null, null, null, null, null, 31744, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirConstructorSymbol getConstructorSymbol(FirRegularClassSymbol annotationClassSymbol) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) annotationClassSymbol.getFir(), this.session, this.components.getScopeSession(), false, (FirResolvePhase) null).processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.FirCallResolver.getConstructorSymbol.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (((FirConstructor) it.getFir()).getIsPrimary() && objectRef.element == null) {
                    objectRef.element = it;
                }
            }
        });
        return (FirConstructorSymbol) objectRef.element;
    }

    private final ResolutionResult runResolutionForGivenSymbol(CallInfo callInfo, FirBasedSymbol<?> symbol) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = null;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer = null;
        }
        Candidate candidateCreateCandidate = new CandidateFactory(firExpressionsResolveTransformer.getTransformer().getResolutionContext(), callInfo).createCandidate(callInfo, symbol, ExplicitReceiverKind.NO_EXPLICIT_RECEIVER, null, (240 & 16) != 0 ? null : null, (240 & 32) != 0 ? CollectionsKt.emptyList() : null, (240 & 64) != 0 ? false : false, (240 & 128) != 0 ? false : false);
        ResolutionStageRunner resolutionStageRunner = this.components.getResolutionStageRunner();
        FirExpressionsResolveTransformer firExpressionsResolveTransformer3 = this.transformer;
        if (firExpressionsResolveTransformer3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
        } else {
            firExpressionsResolveTransformer2 = firExpressionsResolveTransformer3;
        }
        return new ResolutionResult(callInfo, ResolutionStageRunner.processCandidate$default(resolutionStageRunner, candidateCreateCandidate, firExpressionsResolveTransformer2.getTransformer().getResolutionContext(), false, 4, null), CollectionsKt.listOf(candidateCreateCandidate));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirDelegatedConstructorCall selectDelegatingConstructorCall(FirDelegatedConstructorCall call, Name name2, CandidateCollector result, CallInfo callInfo) {
        Pair pairReduceCandidates$default = reduceCandidates$default(this, result, null, null, 6, null);
        Set set = (Set) pairReduceCandidates$default.component1();
        CandidateApplicability currentApplicability = (CandidateApplicability) pairReduceCandidates$default.component2();
        FirReference calleeReference = call.getCalleeReference();
        Set set2 = set;
        if (currentApplicability == null) {
            currentApplicability = result.getCurrentApplicability();
        }
        call.replaceCalleeReference(createResolvedNamedReference$default(this, calleeReference, name2, callInfo, set2, currentApplicability, null, false, null, null, 480, null));
        Candidate candidate = (Candidate) CollectionsKt.singleOrNull(set);
        if (candidate != null) {
            candidate.updateSourcesOfReceivers();
        }
        if (candidate != null) {
            FirBasedSymbol<?> symbol = candidate.getSymbol();
            if ((symbol instanceof FirConstructorSymbol) && ((FirMemberDeclaration) ((FirConstructorSymbol) symbol).getFir()).getStatus().isInner()) {
                call.replaceDispatchReceiver(candidate.dispatchReceiverExpression());
            }
            call.replaceContextReceiverArguments(candidate.contextReceiverArguments());
        }
        return call;
    }

    private final CallInfo createCallableReferencesInfoForLHS(FirCallableReferenceAccess callableReferenceAccess, DoubleColonLHS lhs, ConeKotlinType expectedType, ConstraintSystemBuilder outerConstraintSystemBuilder) {
        FirCallableReferenceAccess firCallableReferenceAccess = callableReferenceAccess;
        CallKind.CallableReference callableReference = CallKind.CallableReference.INSTANCE;
        Name name2 = callableReferenceAccess.getCalleeReference().getName();
        FirExpression explicitReceiver = callableReferenceAccess.getExplicitReceiver();
        FirEmptyArgumentList firEmptyArgumentList = FirEmptyArgumentList.INSTANCE;
        List listEmptyList = CollectionsKt.emptyList();
        FirSession firSession = this.session;
        FirFile file = this.components.getFile();
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer = null;
        }
        return new CallInfo(firCallableReferenceAccess, callableReference, name2, explicitReceiver, firEmptyArgumentList, false, listEmptyList, firSession, file, firExpressionsResolveTransformer.getTransformer().getComponents().getContainingDeclarations(), null, expectedType, outerConstraintSystemBuilder, lhs, null, 16384, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ FirNamedReference createResolvedNamedReference$default(FirCallResolver firCallResolver, FirReference firReference, Name name2, CallInfo callInfo, Collection collection, CandidateApplicability candidateApplicability, FirExpression firExpression, boolean z, CallKind callKind, Collection collection2, int i, Object obj) {
        return firCallResolver.createResolvedNamedReference(firReference, name2, callInfo, collection, candidateApplicability, (i & 32) != 0 ? null : firExpression, (i & 64) != 0 ? true : z, (i & 128) != 0 ? null : callKind, (i & 256) != 0 ? null : collection2);
    }

    /* JADX WARN: Code duplicated, block: B:85:0x0168  */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0250, code lost:
    
        if (((org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration) r7).getTypeParameters().isEmpty() != false) goto L133;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final org.jetbrains.kotlin.fir.references.FirNamedReference createResolvedNamedReference(org.jetbrains.kotlin.fir.references.FirReference r4, org.jetbrains.kotlin.name.Name r5, org.jetbrains.kotlin.fir.resolve.calls.CallInfo r6, java.util.Collection<org.jetbrains.kotlin.fir.resolve.calls.Candidate> r7, org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability r8, org.jetbrains.kotlin.fir.expressions.FirExpression r9, boolean r10, org.jetbrains.kotlin.fir.resolve.calls.CallKind r11, java.util.Collection<org.jetbrains.kotlin.fir.resolve.calls.Candidate> r12) {
        /*
            Method dump skipped, instruction units count: 623
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.fir.FirCallResolver.createResolvedNamedReference(org.jetbrains.kotlin.fir.references.FirReference, org.jetbrains.kotlin.name.Name, org.jetbrains.kotlin.fir.resolve.calls.CallInfo, java.util.Collection, org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability, org.jetbrains.kotlin.fir.expressions.FirExpression, boolean, org.jetbrains.kotlin.fir.resolve.calls.CallKind, java.util.Collection):org.jetbrains.kotlin.fir.references.FirNamedReference");
    }

    private static final boolean createResolvedNamedReference$isValueParametersNotEmpty(Candidate candidate) {
        List<FirValueParameter> valueParameters;
        FirDeclaration fir = candidate.getSymbol().getFir();
        FirFunction firFunction = fir instanceof FirFunction ? (FirFunction) fir : null;
        return (firFunction == null || (valueParameters = firFunction.getValueParameters()) == null || valueParameters.size() <= 0) ? false : true;
    }

    private final FirNamedReference createErrorReferenceForSingleCandidate(Candidate candidate, ConeDiagnostic diagnostic, CallInfo callInfo, KtSourceElement source) {
        if (candidate == null) {
            return buildReferenceWithErrorCandidate(callInfo, diagnostic, source);
        }
        if (diagnostic instanceof ConeUnresolvedError ? true : diagnostic instanceof ConeHiddenCandidateError) {
            return buildReferenceWithErrorCandidate(callInfo, diagnostic, source);
        }
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer = null;
        }
        return ErrorCandidateUtilsKt.createErrorReferenceWithExistingCandidate(candidate, diagnostic, source, firExpressionsResolveTransformer.getTransformer().getResolutionContext(), this.components.getResolutionStageRunner());
    }

    private final FirErrorReferenceWithCandidate buildReferenceWithErrorCandidate(CallInfo callInfo, ConeDiagnostic diagnostic, KtSourceElement source) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.transformer;
        if (firExpressionsResolveTransformer == null) {
            Intrinsics.throwUninitializedPropertyAccessException("transformer");
            firExpressionsResolveTransformer = null;
        }
        return ErrorCandidateUtilsKt.createErrorReferenceWithErrorCandidate(callInfo, diagnostic, source, firExpressionsResolveTransformer.getTransformer().getResolutionContext(), this.components.getResolutionStageRunner());
    }
}
