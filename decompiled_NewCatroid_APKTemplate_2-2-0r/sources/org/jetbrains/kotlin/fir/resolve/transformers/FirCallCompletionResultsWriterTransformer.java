package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.MutableOrEmptyList;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirErrorFunction;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.InlineStatus;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirPropertyAccessExpressionImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedCallableReference;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedCallableReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.FirSamResolver;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceAdaptation;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolvedCallArgument;
import org.jetbrains.kotlin.fir.resolve.dfa.FirDataFlowAnalyzer;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirAnonymousFunctionReturnExpressionInfo;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeConstraintSystemHasContradiction;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInapplicableCandidateError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConePropertyAsOperator;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterInQualifiedAccess;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom;
import org.jetbrains.kotlin.fir.resolve.inference.ResolvedLambdaAtom;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirArrayOfCallTransformer;
import org.jetbrains.kotlin.fir.scopes.impl.ConvertibleIntegerOperators;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorTypeImpl;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralTypeImplKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeApproximator;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirStarProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersection;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ú\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0004\u008a\u0001\u008b\u0001BO\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\"\u001a\u00020#H\u0002J\u001e\u0010$\u001a\b\u0012\u0004\u0012\u00020%0 2\u0006\u0010&\u001a\u00020'2\u0006\u0010\"\u001a\u00020#H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010!2\u0006\u0010)\u001a\u00020!H\u0002J\u0010\u0010*\u001a\u00020!2\u0006\u0010)\u001a\u00020!H\u0002J'\u0010+\u001a\u0002H,\"\b\b\u0000\u0010,*\u00020'2\u0006\u0010-\u001a\u0002H,2\u0006\u0010.\u001a\u00020/H\u0002¢\u0006\u0002\u00100J\u001a\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00106\u001a\u0002022\u0006\u00107\u001a\u0002082\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u00109\u001a\u0002022\u0006\u0010:\u001a\u00020;2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010<\u001a\u0002022\u0006\u0010=\u001a\u00020>2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010?\u001a\u0002022\u0006\u0010@\u001a\u00020A2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010B\u001a\u0002022\u0006\u0010C\u001a\u00020D2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010E\u001a\u0002022\u0006\u0010F\u001a\u00020G2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J&\u0010H\u001a\u000202\"\u0004\b\u0000\u0010,2\f\u0010I\u001a\b\u0012\u0004\u0012\u0002H,0J2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010K\u001a\u0002022\u0006\u0010L\u001a\u00020M2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010N\u001a\u0002022\u0006\u0010O\u001a\u00020P2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010Q\u001a\u0002022\u0006\u0010R\u001a\u00020S2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010T\u001a\u0002022\u0006\u0010U\u001a\u00020V2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u0010\u0010W\u001a\u0002022\u0006\u00107\u001a\u000208H\u0002J\u001a\u0010X\u001a\u0002022\u0006\u0010Y\u001a\u00020Z2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010[\u001a\u0002022\u0006\u0010\\\u001a\u00020]2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010^\u001a\u0002022\u0006\u0010-\u001a\u00020'2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010_\u001a\u0002022\u0006\u0010`\u001a\u00020a2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010b\u001a\u0002022\u0006\u0010c\u001a\u00020d2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010e\u001a\u0002022\u0006\u0010f\u001a\u00020g2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J0\u0010h\u001a\u000202\"\u000e\b\u0000\u0010i\u0018\u0001*\u00020j*\u00020k2\u0006\u0010l\u001a\u0002Hi2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0082\b¢\u0006\u0002\u0010mJ0\u0010n\u001a\u00020o\"\u000e\b\u0000\u0010i\u0018\u0001*\u00020j*\u00020k2\u0006\u0010l\u001a\u0002Hi2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0082\b¢\u0006\u0002\u0010pJ\u001a\u0010q\u001a\u0002022\u0006\u0010r\u001a\u00020s2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010t\u001a\u0002022\u0006\u0010u\u001a\u00020v2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010w\u001a\u0002022\u0006\u0010x\u001a\u00020y2\b\u00105\u001a\u0004\u0018\u00010\u0002H\u0016J\"\u0010z\u001a\u0002H,\"\u0004\b\u0000\u0010,2\f\u0010@\u001a\b\u0012\u0004\u0012\u0002H,0{H\u0082\b¢\u0006\u0002\u0010|J\u000e\u0010}\u001a\u0004\u0018\u00010\u0002*\u00020#H\u0002J\f\u0010~\u001a\u00020o*\u00020#H\u0002J\f\u0010\u007f\u001a\u00020\u0019*\u00020/H\u0002J\r\u0010\u0080\u0001\u001a\u00020!*\u00020!H\u0002J/\u0010\u0081\u0001\u001a\u0002Hi\"\b\b\u0000\u0010i*\u00020k*\u0002Hi2\u0006\u0010.\u001a\u00020/2\b\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002¢\u0006\u0003\u0010\u0084\u0001J\u0016\u0010\u0085\u0001\u001a\u00020!*\u00030\u0086\u00012\u0006\u0010\"\u001a\u00020#H\u0002J\u0017\u0010\u0087\u0001\u001a\u00030\u0083\u0001*\u00030\u0083\u00012\u0006\u0010\"\u001a\u00020#H\u0002J\u000e\u0010\u0088\u0001\u001a\u00030\u0089\u0001*\u00020/H\u0002R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u001c\u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030\u001d8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001e¨\u0006\u008c\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "finalSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "typeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "typeApproximator", "Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;", "dataFlowAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;", "integerOperatorApproximator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;", "samResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "context", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "mode", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer$Mode;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/types/ConeTypeApproximator;Lorg/jetbrains/kotlin/fir/resolve/dfa/FirDataFlowAnalyzer;Lorg/jetbrains/kotlin/fir/resolve/transformers/IntegerLiteralAndOperatorApproximationTransformer;Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer$Mode;)V", "arrayOfCallTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirArrayOfCallTransformer;", "enableArrayOfCallTransformation", "", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "isArrayConstructorWithLambda", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Z", "computeTypeArgumentTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "computeTypeArguments", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "access", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "finallySubstituteOrNull", ModuleXmlParser.TYPE, "finallySubstituteOrSelf", "prepareQualifiedTransform", ExifInterface.GPS_DIRECTION_TRUE, "qualifiedAccessExpression", "calleeReference", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;)Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "transformAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "data", "transformAnonymousFunction", "anonymousFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "transformAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "transformArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "transformCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "transformConstExpression", "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "transformDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "transformElvisExpression", "elvisExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirElvisExpression;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "transformFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "transformImplicitTypeRefInAnonymousFunction", "transformIntegerLiteralOperatorCall", "integerLiteralOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirIntegerLiteralOperatorCall;", "transformPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "transformQualifiedAccessExpression", "transformReturnExpression", "returnExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "transformSafeCallExpression", "safeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "transformSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "transformSyntheticCall", "D", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "syntheticCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "transformSyntheticCallChildren", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/resolve/transformers/ExpectedArgumentType;)V", "transformTryExpression", "tryExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirTryExpression;", "transformVarargArgumentsExpression", "varargArgumentsExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "transformWhenExpression", "whenExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWhenExpression;", "withFirArrayOfCallTransformer", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "createArgumentsMapping", "handleVarargs", "hasAdditionalResolutionErrors", "removeExactAttribute", "replaceTypeRefWithSubstituted", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "(Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "substitute", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "substituteTypeRef", "toResolvedReference", "Lorg/jetbrains/kotlin/fir/references/FirNamedReference;", "Mode", "TypeUpdaterForDelegateArguments", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallCompletionResultsWriterTransformer extends FirAbstractTreeTransformer<ExpectedArgumentType> {
    private final FirArrayOfCallTransformer arrayOfCallTransformer;
    private final BodyResolveContext context;
    private final FirDataFlowAnalyzer dataFlowAnalyzer;
    private boolean enableArrayOfCallTransformation;
    private final ConeSubstitutor finalSubstitutor;
    private final IntegerLiteralAndOperatorApproximationTransformer integerOperatorApproximator;
    private final Mode mode;
    private final FirSamResolver samResolver;
    private final FirSession session;
    private final ConeTypeApproximator typeApproximator;
    private final ReturnTypeCalculator typeCalculator;

    /* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer$Mode;", "", "(Ljava/lang/String;I)V", "Normal", "DelegatedPropertyCompletion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Mode {
        Normal,
        DelegatedPropertyCompletion;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Mode> getEntries() {
            return $ENTRIES;
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public FirSession getSession() {
        return this.session;
    }

    public /* synthetic */ FirCallCompletionResultsWriterTransformer(FirSession firSession, ConeSubstitutor coneSubstitutor, ReturnTypeCalculator returnTypeCalculator, ConeTypeApproximator coneTypeApproximator, FirDataFlowAnalyzer firDataFlowAnalyzer, IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer, FirSamResolver firSamResolver, BodyResolveContext bodyResolveContext, Mode mode, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, coneSubstitutor, returnTypeCalculator, coneTypeApproximator, firDataFlowAnalyzer, integerLiteralAndOperatorApproximationTransformer, firSamResolver, bodyResolveContext, (i & 256) != 0 ? Mode.Normal : mode);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirCallCompletionResultsWriterTransformer(FirSession session, ConeSubstitutor finalSubstitutor, ReturnTypeCalculator typeCalculator, ConeTypeApproximator typeApproximator, FirDataFlowAnalyzer dataFlowAnalyzer, IntegerLiteralAndOperatorApproximationTransformer integerOperatorApproximator, FirSamResolver samResolver, BodyResolveContext context, Mode mode) {
        super(FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(finalSubstitutor, "finalSubstitutor");
        Intrinsics.checkNotNullParameter(typeCalculator, "typeCalculator");
        Intrinsics.checkNotNullParameter(typeApproximator, "typeApproximator");
        Intrinsics.checkNotNullParameter(dataFlowAnalyzer, "dataFlowAnalyzer");
        Intrinsics.checkNotNullParameter(integerOperatorApproximator, "integerOperatorApproximator");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.session = session;
        this.finalSubstitutor = finalSubstitutor;
        this.typeCalculator = typeCalculator;
        this.typeApproximator = typeApproximator;
        this.dataFlowAnalyzer = dataFlowAnalyzer;
        this.integerOperatorApproximator = integerOperatorApproximator;
        this.samResolver = samResolver;
        this.context = context;
        this.mode = mode;
        this.arrayOfCallTransformer = new FirArrayOfCallTransformer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConeKotlinType finallySubstituteOrNull(ConeKotlinType type) {
        ConeKotlinType coneKotlinTypeSubstituteOrNull = this.finalSubstitutor.substituteOrNull(type);
        if (coneKotlinTypeSubstituteOrNull == null && (type instanceof ConeIntegerLiteralType)) {
            return ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType$default(type, null, 1, null);
        }
        if (coneKotlinTypeSubstituteOrNull != null) {
            return ConeIntegerLiteralTypeImplKt.approximateIntegerLiteralType$default(coneKotlinTypeSubstituteOrNull, null, 1, null);
        }
        return null;
    }

    private final ConeKotlinType finallySubstituteOrSelf(ConeKotlinType type) {
        ConeKotlinType coneKotlinTypeFinallySubstituteOrNull = finallySubstituteOrNull(type);
        return coneKotlinTypeFinallySubstituteOrNull == null ? type : coneKotlinTypeFinallySubstituteOrNull;
    }

    private final <T extends FirQualifiedAccessExpression> T prepareQualifiedTransform(T qualifiedAccessExpression, FirNamedReferenceWithCandidate calleeReference) {
        FirErrorTypeRef firErrorTypeRefMo6809build;
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef;
        Candidate candidate = calleeReference.getCandidate();
        FirDeclaration fir = candidate.getSymbol().getFir();
        List<FirTypeProjection> listComputeTypeArguments = computeTypeArguments(qualifiedAccessExpression, candidate);
        boolean z = fir instanceof FirCallableDeclaration;
        if (z) {
            FirResolvedTypeRef firResolvedTypeRefTryCalculateReturnType = this.typeCalculator.tryCalculateReturnType((FirCallableDeclaration) fir);
            if (!(firResolvedTypeRefTryCalculateReturnType instanceof FirErrorTypeRef)) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                KtSourceElement source = firResolvedTypeRefTryCalculateReturnType.getSource();
                firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null);
                CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firResolvedTypeRefTryCalculateReturnType.getAnnotations());
                firResolvedTypeRefBuilder.setType(firResolvedTypeRefTryCalculateReturnType.getType());
                firErrorTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                KtSourceElement source2 = firResolvedTypeRefTryCalculateReturnType.getSource();
                firErrorTypeRefBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null);
                firErrorTypeRefBuilder.setType(firResolvedTypeRefTryCalculateReturnType.getType());
                firErrorTypeRefBuilder.setDiagnostic(((FirErrorTypeRef) firResolvedTypeRefTryCalculateReturnType).getDiagnostic());
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            }
        } else {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
            KtSourceElement source3 = calleeReference.getSource();
            firErrorTypeRefBuilder2.setSource(source3 != null ? KtSourceElementKt.fakeElement$default(source3, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null);
            firErrorTypeRefBuilder2.setDiagnostic(fir instanceof FirTypeParameter ? new ConeTypeParameterInQualifiedAccess(((FirTypeParameter) fir).getSymbol()) : new ConeSimpleDiagnostic("Callee reference to candidate without return type: " + UtilsKt.render(fir), null, 2, null));
            firErrorTypeRefMo6809build = firErrorTypeRefBuilder2.mo6809build();
        }
        FirElement firElementDispatchReceiverExpression = candidate.dispatchReceiverExpression();
        FirElement firElementChosenExtensionReceiverExpression = candidate.chosenExtensionReceiverExpression();
        if (!FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperator(fir)) {
            FirCallableDeclaration firCallableDeclaration = z ? (FirCallableDeclaration) fir : null;
            ConeSimpleKotlinType dispatchReceiverType = firCallableDeclaration != null ? firCallableDeclaration.getDispatchReceiverType() : null;
            FirCallableDeclaration firCallableDeclaration2 = z ? (FirCallableDeclaration) fir : null;
            ConeKotlinType coneType = (firCallableDeclaration2 == null || (receiverParameter = firCallableDeclaration2.getReceiverParameter()) == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
            firElementDispatchReceiverExpression = FirTransformerUtilKt.transformSingle(firElementDispatchReceiverExpression, this.integerOperatorApproximator, dispatchReceiverType);
            firElementChosenExtensionReceiverExpression = FirTransformerUtilKt.transformSingle(firElementChosenExtensionReceiverExpression, this.integerOperatorApproximator, coneType);
        }
        T t = qualifiedAccessExpression instanceof FirQualifiedAccessExpression ? qualifiedAccessExpression : null;
        if (t != null) {
            t.replaceCalleeReference(toResolvedReference(calleeReference));
            t.replaceDispatchReceiver((FirExpression) firElementDispatchReceiverExpression);
            t.replaceExtensionReceiver((FirExpression) firElementChosenExtensionReceiverExpression);
        }
        qualifiedAccessExpression.replaceContextReceiverArguments(candidate.contextReceiverArguments());
        if ((qualifiedAccessExpression instanceof FirPropertyAccessExpressionImpl) && calleeReference.getCandidate().getCurrentApplicability() == CandidateApplicability.K2_PROPERTY_AS_OPERATOR) {
            FirBasedSymbol<?> symbol = calleeReference.getCandidate().getSymbol();
            Intrinsics.checkNotNull(symbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
            ConePropertyAsOperator conePropertyAsOperator = new ConePropertyAsOperator((FirPropertySymbol) symbol);
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            FirPropertyAccessExpressionImpl firPropertyAccessExpressionImpl = (FirPropertyAccessExpressionImpl) qualifiedAccessExpression;
            listCreateListBuilder.addAll(MutableOrEmptyList.m6756boximpl(firPropertyAccessExpressionImpl.m6992getNonFatalDiagnostics5e3fPpI()));
            listCreateListBuilder.add(conePropertyAsOperator);
            firPropertyAccessExpressionImpl.replaceNonFatalDiagnostics(CollectionsKt.build(listCreateListBuilder));
        }
        FirResolvedTypeRef firResolvedTypeRef = firErrorTypeRefMo6809build;
        qualifiedAccessExpression.replaceTypeRef(firResolvedTypeRef);
        if (!(fir instanceof FirErrorFunction)) {
            qualifiedAccessExpression.replaceTypeArguments(listComputeTypeArguments);
        }
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef, qualifiedAccessExpression.getSource(), this.context.getFile().getSource());
        }
        return qualifiedAccessExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        FirReference calleeReference = qualifiedAccessExpression.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            if (this.mode == Mode.DelegatedPropertyCompletion) {
                FirTransformerUtilKt.transformSingle(qualifiedAccessExpression, new TypeUpdaterForDelegateArguments(), null);
            }
            return qualifiedAccessExpression;
        }
        FirQualifiedAccessExpression firQualifiedAccessExpressionPrepareQualifiedTransform = prepareQualifiedTransform(qualifiedAccessExpression, firNamedReferenceWithCandidate);
        FirTypeRef typeRef = firQualifiedAccessExpressionPrepareQualifiedTransform.getTypeRef();
        Intrinsics.checkNotNull(typeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
        FirResolvedTypeRef firResolvedTypeRefSubstituteTypeRef = substituteTypeRef((FirResolvedTypeRef) typeRef, firNamedReferenceWithCandidate.getCandidate());
        PhaseUtilsKt.ensureResolvedTypeDeclaration$default(firResolvedTypeRefSubstituteTypeRef, getSession(), (FirResolvePhase) null, 2, (Object) null);
        firQualifiedAccessExpressionPrepareQualifiedTransform.replaceTypeRef(firResolvedTypeRefSubstituteTypeRef);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefSubstituteTypeRef, qualifiedAccessExpression.getSource(), this.context.getFile().getSource());
        }
        if (this.mode == Mode.DelegatedPropertyCompletion) {
            firQualifiedAccessExpressionPrepareQualifiedTransform.transformExplicitReceiver(new TypeUpdaterForDelegateArguments(), null);
        }
        return firQualifiedAccessExpressionPrepareQualifiedTransform;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        return transformQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunctionCall(FirFunctionCall functionCall, ExpectedArgumentType data) {
        FirAnonymousFunction anonymousFunction;
        InlineStatus inlineStatus;
        FirSimpleFunction firSimpleFunction;
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        FirNamedReference calleeReference = functionCall.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            return functionCall;
        }
        FirFunctionCall firFunctionCall = (FirFunctionCall) prepareQualifiedTransform(functionCall, firNamedReferenceWithCandidate);
        FirTypeRef typeRef = firFunctionCall.getTypeRef();
        Intrinsics.checkNotNull(typeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
        Candidate candidate = firNamedReferenceWithCandidate.getCandidate();
        FirResolvedTypeRef firResolvedTypeRefSubstituteTypeRef = substituteTypeRef((FirResolvedTypeRef) typeRef, candidate);
        if (firNamedReferenceWithCandidate.isError()) {
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
            if (argumentMapping != null) {
                firFunctionCall.replaceArgumentList(FirArgumentUtilKt.buildArgumentListForErrorCall(firFunctionCall.getArgumentList(), argumentMapping));
            }
        } else {
            handleVarargs(candidate);
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping2 = candidate.getArgumentMapping();
            if (argumentMapping2 != null) {
                FirResolvedArgumentList firResolvedArgumentListBuildResolvedArgumentList = FirArgumentUtilKt.buildResolvedArgumentList(argumentMapping2, functionCall.getArgumentList().getSource());
                FirBasedSymbol<?> symbol = candidate.getSymbol();
                FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
                boolean z = true;
                if ((firNamedFunctionSymbol == null || (firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir()) == null || !firSimpleFunction.getStatus().isInline()) && !isArrayConstructorWithLambda(symbol)) {
                    z = false;
                }
                for (Map.Entry<FirExpression, FirValueParameter> entry : firResolvedArgumentListBuildResolvedArgumentList.getMapping().entrySet()) {
                    FirExpression key = entry.getKey();
                    FirValueParameter value = entry.getValue();
                    FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(key);
                    FirAnonymousFunctionExpression firAnonymousFunctionExpression = firExpressionUnwrapArgument instanceof FirAnonymousFunctionExpression ? (FirAnonymousFunctionExpression) firExpressionUnwrapArgument : null;
                    if (firAnonymousFunctionExpression != null && (anonymousFunction = firAnonymousFunctionExpression.getAnonymousFunction()) != null) {
                        if (value.getIsCrossinline() && z) {
                            inlineStatus = InlineStatus.CrossInline;
                        } else if (!value.getIsNoinline() && z) {
                            inlineStatus = InlineStatus.Inline;
                        } else {
                            inlineStatus = InlineStatus.NoInline;
                        }
                        anonymousFunction.replaceInlineStatus(inlineStatus);
                    }
                }
                firFunctionCall.replaceArgumentList(firResolvedArgumentListBuildResolvedArgumentList);
            }
        }
        firFunctionCall.getArgumentList().transformArguments(this, !firNamedReferenceWithCandidate.isError() ? createArgumentsMapping(candidate) : null);
        firFunctionCall.replaceTypeRef(firResolvedTypeRefSubstituteTypeRef);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefSubstituteTypeRef, functionCall.getSource(), this.context.getFile().getSource());
        }
        if (this.mode == Mode.DelegatedPropertyCompletion) {
            TypeUpdaterForDelegateArguments typeUpdaterForDelegateArguments = new TypeUpdaterForDelegateArguments();
            firFunctionCall.getArgumentList().transformArguments(typeUpdaterForDelegateArguments, null);
            firFunctionCall.transformExplicitReceiver((FirTransformer<? super Object>) typeUpdaterForDelegateArguments, (Object) null);
        }
        if (this.enableArrayOfCallTransformation) {
            return this.arrayOfCallTransformer.transformFunctionCall(firFunctionCall, getSession());
        }
        return firFunctionCall;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isArrayConstructorWithLambda(FirBasedSymbol<?> firBasedSymbol) {
        FirConstructor firConstructor;
        FirConstructorSymbol firConstructorSymbol = firBasedSymbol instanceof FirConstructorSymbol ? (FirConstructorSymbol) firBasedSymbol : null;
        if (firConstructorSymbol == null || (firConstructor = (FirConstructor) firConstructorSymbol.getFir()) == null || firConstructor.getValueParameters().size() != 2) {
            return false;
        }
        return ArrayUtilsKt.isArrayOrPrimitiveArray(FirTypeUtilsKt.getConeType(firConstructor.getReturnTypeRef()));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        FirReference calleeReference = annotationCall.getCalleeReference();
        LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap = null;
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            return annotationCall;
        }
        annotationCall.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        Candidate candidate = firNamedReferenceWithCandidate.getCandidate();
        ExpectedArgumentType expectedArgumentTypeCreateArgumentsMapping = !firNamedReferenceWithCandidate.isError() ? createArgumentsMapping(candidate) : null;
        this.enableArrayOfCallTransformation = true;
        try {
            annotationCall.getArgumentList().transformArguments(this, expectedArgumentTypeCreateArgumentsMapping);
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
            if (argumentMapping != null) {
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(argumentMapping.size());
                LinkedHashMap<FirExpression, FirValueParameter> argumentMapping2 = candidate.getArgumentMapping();
                if (argumentMapping2 != null) {
                    int i = 0;
                    for (Object obj : argumentMapping2.entrySet()) {
                        linkedHashMap2.put(annotationCall.getArgumentList().getArguments().get(i), ((Map.Entry) obj).getValue());
                        i++;
                    }
                    linkedHashMap = linkedHashMap2;
                }
            }
            candidate.setArgumentMapping(linkedHashMap);
            Unit unit = Unit.INSTANCE;
            this.enableArrayOfCallTransformation = false;
            if (firNamedReferenceWithCandidate.isError()) {
                LinkedHashMap<FirExpression, FirValueParameter> argumentMapping3 = candidate.getArgumentMapping();
                if (argumentMapping3 != null) {
                    annotationCall.replaceArgumentList(FirArgumentUtilKt.buildArgumentListForErrorCall(annotationCall.getArgumentList(), argumentMapping3));
                }
            } else {
                handleVarargs(candidate);
                LinkedHashMap<FirExpression, FirValueParameter> argumentMapping4 = candidate.getArgumentMapping();
                if (argumentMapping4 != null) {
                    annotationCall.replaceArgumentList(FirArgumentUtilKt.buildResolvedArgumentList(argumentMapping4, annotationCall.getArgumentList().getSource()));
                }
            }
            return annotationCall;
        } catch (Throwable th) {
            this.enableArrayOfCallTransformation = false;
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        return transformAnnotationCall((FirAnnotationCall) errorAnnotationCall, data);
    }

    private final void handleVarargs(Candidate candidate) {
        Collection<FirValueParameter> collectionValues;
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
        FirValueParameter firValueParameter = null;
        Object obj = null;
        firValueParameter = null;
        if (argumentMapping != null && (collectionValues = argumentMapping.values()) != null) {
            for (Object obj2 : collectionValues) {
                if (((FirValueParameter) obj2).getIsVararg()) {
                    obj = obj2;
                    break;
                }
            }
            firValueParameter = (FirValueParameter) obj;
        }
        if (firValueParameter != null) {
            candidate.setArgumentMapping(BodyResolveUtilsKt.remapArgumentsWithVararg(firValueParameter, substitute(firValueParameter.getReturnTypeRef(), candidate), argumentMapping));
        }
    }

    private final <D extends FirExpression> D replaceTypeRefWithSubstituted(D d, FirNamedReferenceWithCandidate firNamedReferenceWithCandidate, FirResolvedTypeRef firResolvedTypeRef) {
        FirResolvedTypeRef firResolvedTypeRefSubstituteTypeRef = substituteTypeRef(firResolvedTypeRef, firNamedReferenceWithCandidate.getCandidate());
        d.replaceTypeRef(firResolvedTypeRefSubstituteTypeRef);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefSubstituteTypeRef, d.getSource(), this.context.getFile().getSource());
        }
        return d;
    }

    private final FirResolvedTypeRef substituteTypeRef(FirResolvedTypeRef firResolvedTypeRef, Candidate candidate) {
        ConeKotlinType coneKotlinTypeSubstituteOrSelf = candidate.getSubstitutor().substituteOrSelf(firResolvedTypeRef.getType());
        ConeKotlinType coneKotlinTypeFinallySubstituteOrNull = finallySubstituteOrNull(coneKotlinTypeSubstituteOrSelf);
        ConeTypeApproximator coneTypeApproximator = this.typeApproximator;
        if (coneKotlinTypeFinallySubstituteOrNull != null) {
            coneKotlinTypeSubstituteOrSelf = coneKotlinTypeFinallySubstituteOrNull;
        }
        ConeKotlinType coneKotlinTypeApproximateToSuperType = coneTypeApproximator.approximateToSuperType(coneKotlinTypeSubstituteOrSelf, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        if (coneKotlinTypeApproximateToSuperType != null) {
            coneKotlinTypeFinallySubstituteOrNull = coneKotlinTypeApproximateToSuperType;
        }
        return TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRef, coneKotlinTypeFinallySubstituteOrNull != null ? removeExactAttribute(coneKotlinTypeFinallySubstituteOrNull) : null, null, 2, null);
    }

    private final ConeKotlinType removeExactAttribute(ConeKotlinType coneKotlinType) {
        return coneKotlinType.getAttributes().contains(CompilerConeAttributes.Exact.INSTANCE) ? TypeUtilsKt.withAttributes(coneKotlinType, coneKotlinType.getAttributes().remove(CompilerConeAttributes.Exact.INSTANCE)) : coneKotlinType;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSafeCallExpression(FirSafeCallExpression safeCallExpression, ExpectedArgumentType data) {
        ConeKotlinType expectedType;
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        safeCallExpression.transformSelector(this, (data == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, safeCallExpression)) == null) ? null : FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType));
        ResolveUtilsKt.propagateTypeFromQualifiedAccessAfterNullCheck(safeCallExpression, getSession(), this.context.getFile());
        return safeCallExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, ExpectedArgumentType data) {
        Map<FirValueParameter, ResolvedCallArgument> mapEmptyMap;
        FirResolvedCallableReference firResolvedCallableReferenceBuild;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        FirNamedReference calleeReference = callableReferenceAccess.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            return callableReferenceAccess;
        }
        Candidate candidate = firNamedReferenceWithCandidate.getCandidate();
        List<FirTypeProjection> listComputeTypeArguments = computeTypeArguments(callableReferenceAccess, candidate);
        FirTypeRef typeRef = callableReferenceAccess.getTypeRef();
        Intrinsics.checkNotNull(typeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
        FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) typeRef;
        FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRef, finallySubstituteOrSelf(firNamedReferenceWithCandidate.getCandidate().getSubstitutor().substituteOrSelf(firResolvedTypeRef.getType())), null, 2, null);
        callableReferenceAccess.replaceTypeRef(firResolvedTypeRefWithReplacedConeType$default);
        callableReferenceAccess.replaceTypeArguments(listComputeTypeArguments);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            KtSourceElement source = firResolvedTypeRef.getSource();
            if (source == null) {
                source = callableReferenceAccess.getSource();
            }
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefWithReplacedConeType$default, source, this.context.getFile().getSource());
        }
        if (firNamedReferenceWithCandidate instanceof FirErrorReferenceWithCandidate) {
            firResolvedCallableReferenceBuild = ResolveUtilsKt.toErrorReference(firNamedReferenceWithCandidate, ((FirErrorReferenceWithCandidate) firNamedReferenceWithCandidate).getDiagnostic());
        } else {
            FirResolvedCallableReferenceBuilder firResolvedCallableReferenceBuilder = new FirResolvedCallableReferenceBuilder();
            firResolvedCallableReferenceBuilder.setSource(firNamedReferenceWithCandidate.getSource());
            firResolvedCallableReferenceBuilder.setName(firNamedReferenceWithCandidate.getName());
            firResolvedCallableReferenceBuilder.setResolvedSymbol(firNamedReferenceWithCandidate.getCandidateSymbol());
            firResolvedCallableReferenceBuilder.getInferredTypeArguments().addAll(computeTypeArgumentTypes(firNamedReferenceWithCandidate.getCandidate()));
            CallableReferenceAdaptation callableReferenceAdaptation = candidate.getCallableReferenceAdaptation();
            if (callableReferenceAdaptation == null || (mapEmptyMap = callableReferenceAdaptation.getMappedArguments()) == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            firResolvedCallableReferenceBuilder.setMappedArguments(mapEmptyMap);
            firResolvedCallableReferenceBuild = firResolvedCallableReferenceBuilder.build();
        }
        callableReferenceAccess.replaceCalleeReference(firResolvedCallableReferenceBuild);
        callableReferenceAccess.replaceDispatchReceiver(candidate.dispatchReceiverExpression());
        callableReferenceAccess.replaceExtensionReceiver(candidate.chosenExtensionReceiverExpression());
        return callableReferenceAccess;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSmartCastExpression(FirSmartCastExpression smartCastExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        return smartCastExpression.transformOriginalExpression(this, data);
    }

    /* JADX INFO: compiled from: FirCallCompletionResultsWriterTransformer.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J)\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer$TypeUpdaterForDelegateArguments;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer;)V", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformPropertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "transformQualifiedAccessExpression", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class TypeUpdaterForDelegateArguments extends FirTransformer<Object> {
        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public <E extends FirElement> E transformElement(E element, Object data) {
            Intrinsics.checkNotNullParameter(element, "element");
            return element;
        }

        public TypeUpdaterForDelegateArguments() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, Object data) {
            Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
            ConeKotlinType coneKotlinTypeFinallySubstituteOrNull = FirCallCompletionResultsWriterTransformer.this.finallySubstituteOrNull(FirTypeUtilsKt.getConeType(qualifiedAccessExpression.getTypeRef()));
            if (coneKotlinTypeFinallySubstituteOrNull == null) {
                return qualifiedAccessExpression;
            }
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(qualifiedAccessExpression.getTypeRef(), coneKotlinTypeFinallySubstituteOrNull, null, 2, null);
            qualifiedAccessExpression.replaceTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(FirCallCompletionResultsWriterTransformer.this.getSession());
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefResolvedTypeFromPrototype$default, qualifiedAccessExpression.getSource(), FirCallCompletionResultsWriterTransformer.this.context.getFile().getSource());
            }
            return qualifiedAccessExpression;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, Object data) {
            Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
            return transformQualifiedAccessExpression(propertyAccessExpression, data);
        }
    }

    private final ConeKotlinType substitute(FirTypeRef firTypeRef, Candidate candidate) {
        return finallySubstituteOrSelf(candidate.getSubstitutor().substituteOrSelf(FirTypeUtilsKt.getConeType(firTypeRef)));
    }

    private final ExpectedArgumentType createArgumentsMapping(Candidate candidate) {
        Map mapEmptyMap;
        ConeIntegerConstantOperatorTypeImpl coneIntegerConstantOperatorTypeImplVarargElementType;
        List<PostponedResolvedAtom> postponedAtoms = candidate.getPostponedAtoms();
        ArrayList arrayList = new ArrayList();
        for (Object obj : postponedAtoms) {
            if (obj instanceof ResolvedLambdaAtom) {
                arrayList.add(obj);
            }
        }
        ArrayList<ResolvedLambdaAtom> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (ResolvedLambdaAtom resolvedLambdaAtom : arrayList2) {
            Pair pair = new Pair(resolvedLambdaAtom.getReference(), finallySubstituteOrSelf(candidate.getSubstitutor().substituteOrSelf(resolvedLambdaAtom.getReturnType())));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        boolean zIsWrappedIntegerOperator = FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperator(candidate.getSymbol());
        LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
        if (argumentMapping != null) {
            LinkedHashMap<FirExpression, FirValueParameter> linkedHashMap2 = argumentMapping;
            ArrayList arrayList3 = new ArrayList(linkedHashMap2.size());
            for (Map.Entry<FirExpression, FirValueParameter> entry : linkedHashMap2.entrySet()) {
                FirExpression key = entry.getKey();
                FirValueParameter value = entry.getValue();
                if (zIsWrappedIntegerOperator) {
                    coneIntegerConstantOperatorTypeImplVarargElementType = new ConeIntegerConstantOperatorTypeImpl(FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperatorForUnsignedType(candidate.getSymbol()) && ConvertibleIntegerOperators.INSTANCE.getBinaryOperatorsWithSignedArgument().contains(candidate.getCallInfo().getName()), ConeNullability.NOT_NULL);
                } else {
                    coneIntegerConstantOperatorTypeImplVarargElementType = value.getIsVararg() ? ArrayUtilsKt.varargElementType(substitute(value.getReturnTypeRef(), candidate)) : substitute(value.getReturnTypeRef(), candidate);
                }
                FirElement firElementUnwrapArgument = FirExpressionUtilKt.unwrapArgument(key);
                if (firElementUnwrapArgument instanceof FirAnonymousFunctionExpression) {
                    firElementUnwrapArgument = ((FirAnonymousFunctionExpression) firElementUnwrapArgument).getAnonymousFunction();
                }
                arrayList3.add(TuplesKt.to(firElementUnwrapArgument, coneIntegerConstantOperatorTypeImplVarargElementType));
            }
            mapEmptyMap = MapsKt.toMap(arrayList3);
        } else {
            mapEmptyMap = null;
        }
        if (linkedHashMap.isEmpty() && (mapEmptyMap == null || mapEmptyMap.isEmpty())) {
            return null;
        }
        if (mapEmptyMap == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        return new ExpectedArgumentType.ArgumentsMap(mapEmptyMap, linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        FirReference calleeReference = delegatedConstructorCall.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            return delegatedConstructorCall;
        }
        Candidate candidate = firNamedReferenceWithCandidate.getCandidate();
        if (firNamedReferenceWithCandidate.isError()) {
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
            if (argumentMapping != null) {
                delegatedConstructorCall.replaceArgumentList(FirArgumentUtilKt.buildArgumentListForErrorCall(delegatedConstructorCall.getArgumentList(), argumentMapping));
            }
        } else {
            handleVarargs(candidate);
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping2 = candidate.getArgumentMapping();
            if (argumentMapping2 != null) {
                delegatedConstructorCall.replaceArgumentList(FirArgumentUtilKt.buildResolvedArgumentList(argumentMapping2, delegatedConstructorCall.getArgumentList().getSource()));
            }
        }
        delegatedConstructorCall.getArgumentList().transformArguments(this, firNamedReferenceWithCandidate.isError() ? null : createArgumentsMapping(candidate));
        delegatedConstructorCall.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        return delegatedConstructorCall;
    }

    private final List<FirTypeProjection> computeTypeArguments(FirQualifiedAccessExpression access, Candidate candidate) {
        FirTypeProjection firTypeProjectionBuild;
        List<ConeKotlinType> listComputeTypeArgumentTypes = computeTypeArgumentTypes(candidate);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listComputeTypeArgumentTypes, 10));
        int i = 0;
        for (Object obj : listComputeTypeArgumentTypes) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ConeKotlinType coneKotlinType = (ConeKotlinType) obj;
            FirTypeProjection firTypeProjection = (FirTypeProjection) CollectionsKt.getOrNull(access.getTypeArguments(), i);
            if (firTypeProjection instanceof FirTypeProjectionWithVariance) {
                FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
                FirTypeRef typeRef = firTypeProjectionWithVariance.getTypeRef();
                Intrinsics.checkNotNull(typeRef, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirResolvedTypeRef");
                FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) typeRef;
                FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
                firTypeProjectionWithVarianceBuilder.setSource(firTypeProjectionWithVariance.getSource());
                boolean z = firResolvedTypeRef.getType() instanceof ConeErrorType;
                FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = firResolvedTypeRef;
                if (!z) {
                    firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRefWithReplacedConeType$default, coneKotlinType, null, 2, null);
                }
                firTypeProjectionWithVarianceBuilder.setTypeRef(firResolvedTypeRefWithReplacedConeType$default);
                firTypeProjectionWithVarianceBuilder.setVariance(firTypeProjectionWithVariance.getVariance());
                firTypeProjectionBuild = firTypeProjectionWithVarianceBuilder.build();
            } else if (firTypeProjection instanceof FirStarProjection) {
                FirStarProjectionBuilder firStarProjectionBuilder = new FirStarProjectionBuilder();
                firStarProjectionBuilder.setSource(((FirStarProjection) firTypeProjection).getSource());
                firTypeProjectionBuild = firStarProjectionBuilder.build();
            } else {
                FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder2 = new FirTypeProjectionWithVarianceBuilder();
                firTypeProjectionWithVarianceBuilder2.setSource(firTypeProjection != null ? firTypeProjection.getSource() : null);
                firTypeProjectionWithVarianceBuilder2.setTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinType, null, null, 3, null));
                firTypeProjectionWithVarianceBuilder2.setVariance(Variance.INVARIANT);
                firTypeProjectionBuild = firTypeProjectionWithVarianceBuilder2.build();
            }
            arrayList.add(firTypeProjectionBuild);
            i = i2;
        }
        ArrayList arrayList2 = arrayList;
        return arrayList2.size() < access.getTypeArguments().size() ? CollectionsKt.plus((Collection) arrayList2, (Iterable) access.getTypeArguments().subList(arrayList2.size(), access.getTypeArguments().size())) : arrayList2;
    }

    private final List<ConeKotlinType> computeTypeArgumentTypes(Candidate candidate) {
        FirDeclaration fir = candidate.getSymbol().getFir();
        FirCallableDeclaration firCallableDeclaration = fir instanceof FirCallableDeclaration ? (FirCallableDeclaration) fir : null;
        if (firCallableDeclaration == null) {
            return CollectionsKt.emptyList();
        }
        List<FirTypeParameterRef> typeParameters = firCallableDeclaration.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            ConeKotlinType coneKotlinTypeFinallySubstituteOrSelf = finallySubstituteOrSelf(candidate.getSubstitutor().substituteOrSelf(new ConeTypeParameterTypeImpl(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag(), false, null, 4, null)));
            ConeKotlinType coneKotlinTypeApproximateToSuperType = this.typeApproximator.approximateToSuperType(coneKotlinTypeFinallySubstituteOrSelf, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.TypeArgumentApproximation.INSTANCE);
            if (coneKotlinTypeApproximateToSuperType != null) {
                coneKotlinTypeFinallySubstituteOrSelf = coneKotlinTypeApproximateToSuperType;
            }
            arrayList.add(coneKotlinTypeFinallySubstituteOrSelf);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        return anonymousFunctionExpression.transformAnonymousFunction(this, data);
    }

    /* JADX WARN: Code duplicated, block: B:34:0x0085  */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunction(FirAnonymousFunction anonymousFunction, ExpectedArgumentType data) {
        ConeSimpleKotlinType expectedType;
        ConeKotlinType type;
        ConeKotlinType coneKotlinTypeCommonSuperTypeOrNull;
        Map<FirAnonymousFunction, ConeKotlinType> lambdasReturnTypes;
        FunctionTypeKind functionTypeKind;
        FirTypeRef typeRef;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Collection<FirAnonymousFunctionReturnExpressionInfo> collectionReturnExpressionsOfAnonymousFunctionOrNull = this.dataFlowAnalyzer.returnExpressionsOfAnonymousFunctionOrNull(anonymousFunction);
        if (collectionReturnExpressionsOfAnonymousFunctionOrNull == null) {
            return transformImplicitTypeRefInAnonymousFunction(anonymousFunction);
        }
        Collection<FirAnonymousFunctionReturnExpressionInfo> collection = collectionReturnExpressionsOfAnonymousFunctionOrNull;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirAnonymousFunctionReturnExpressionInfo) it.next()).getExpression());
        }
        ArrayList arrayList2 = arrayList;
        FunctionTypeKind functionTypeKind2 = null;
        if (data == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, anonymousFunction)) == null) {
            expectedType = null;
        } else if (!FunctionalTypeUtilsKt.isSomeFunctionType(expectedType, getSession())) {
            ConeKotlinType functionTypeForPossibleSamType = this.samResolver.getFunctionTypeForPossibleSamType(expectedType);
            expectedType = functionTypeForPossibleSamType != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(functionTypeForPossibleSamType) : null;
        }
        boolean z = anonymousFunction.getTypeRef() instanceof FirImplicitTypeRef;
        FirReceiverParameter receiverParameter = anonymousFunction.getReceiverParameter();
        if (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) {
            type = null;
        } else {
            FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
            type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
        }
        ConeKotlinType coneKotlinTypeFinallySubstituteOrNull = type != null ? finallySubstituteOrNull(type) : null;
        boolean z2 = true;
        if (coneKotlinTypeFinallySubstituteOrNull != null) {
            receiverParameter.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(receiverParameter.getTypeRef(), coneKotlinTypeFinallySubstituteOrNull, null, 2, null));
            z = true;
        }
        FirTypeRef returnTypeRef = anonymousFunction.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef2 = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
        if (!(type2 instanceof ConeKotlinType)) {
            type2 = null;
        }
        if (type2 == null || (coneKotlinTypeCommonSuperTypeOrNull = finallySubstituteOrSelf(type2)) == null) {
            ConeKotlinType coneKotlinTypeReturnType = expectedType != null ? FunctionalTypeUtilsKt.returnType(expectedType, getSession()) : null;
            ConeClassLikeType coneClassLikeType = coneKotlinTypeReturnType instanceof ConeClassLikeType ? (ConeClassLikeType) coneKotlinTypeReturnType : null;
            if (coneClassLikeType != null) {
                coneKotlinTypeCommonSuperTypeOrNull = coneClassLikeType;
            } else {
                ExpectedArgumentType.ArgumentsMap argumentsMap = data instanceof ExpectedArgumentType.ArgumentsMap ? (ExpectedArgumentType.ArgumentsMap) data : null;
                coneKotlinTypeCommonSuperTypeOrNull = (argumentsMap == null || (lambdasReturnTypes = argumentsMap.getLambdasReturnTypes()) == null) ? null : lambdasReturnTypes.get(anonymousFunction);
            }
        }
        ExpectedArgumentType expectedType2 = coneKotlinTypeCommonSuperTypeOrNull != null ? FirCallCompletionResultsWriterTransformerKt.toExpectedType(coneKotlinTypeCommonSuperTypeOrNull) : null;
        FirAnonymousFunction firAnonymousFunction = (FirAnonymousFunction) transformElement(anonymousFunction, expectedType2);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            FirTransformerUtilKt.transformSingle((FirExpression) it2.next(), this, expectedType2);
        }
        if (coneKotlinTypeCommonSuperTypeOrNull == null) {
            ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
            ArrayList arrayList3 = arrayList2;
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
            Iterator it3 = arrayList3.iterator();
            while (it3.hasNext()) {
                arrayList4.add(FirTypeUtilsKt.getConeType(((FirExpression) it3.next()).getTypeRef()));
            }
            coneKotlinTypeCommonSuperTypeOrNull = TypeUtilsKt.commonSuperTypeOrNull(typeContext, arrayList4);
            if (coneKotlinTypeCommonSuperTypeOrNull == null) {
                coneKotlinTypeCommonSuperTypeOrNull = getSession().getBuiltinTypes().getUnitType().getType();
            }
        }
        if (Intrinsics.areEqual(type2, coneKotlinTypeCommonSuperTypeOrNull)) {
            z2 = z;
        } else {
            firAnonymousFunction.replaceReturnTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firAnonymousFunction.getReturnTypeRef(), coneKotlinTypeCommonSuperTypeOrNull, null, 2, null));
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firAnonymousFunction.getReturnTypeRef(), firAnonymousFunction.getSource(), this.context.getFile().getSource());
            }
        }
        if (z2) {
            if (expectedType == null || (functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(expectedType, getSession())) == null) {
                FirTypeRef typeRef2 = firAnonymousFunction.getTypeRef();
                FirResolvedTypeRef firResolvedTypeRef3 = typeRef2 instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef2 : null;
                ConeKotlinType type3 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
                if (!(type3 instanceof ConeClassLikeType)) {
                    type3 = null;
                }
                ConeClassLikeType coneClassLikeType2 = (ConeClassLikeType) type3;
                if (coneClassLikeType2 != null) {
                    functionTypeKind2 = FunctionalTypeUtilsKt.functionTypeKind(coneClassLikeType2, getSession());
                }
            } else {
                functionTypeKind2 = functionTypeKind;
            }
            firAnonymousFunction.replaceTypeRef(ResolveUtilsKt.constructFunctionTypeRef(firAnonymousFunction, getSession(), functionTypeKind2));
            FirLookupTrackerComponent lookupTracker2 = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker2 != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker2, firAnonymousFunction.getTypeRef(), firAnonymousFunction.getSource(), this.context.getFile().getSource());
            }
        }
        ResolveUtilsKt.addReturnToLastStatementIfNeeded(firAnonymousFunction, getSession());
        return firAnonymousFunction;
    }

    private final FirStatement transformImplicitTypeRefInAnonymousFunction(FirAnonymousFunction anonymousFunction) {
        FirDefaultTransformer<Object> firDefaultTransformer = new FirDefaultTransformer<Object>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirCallCompletionResultsWriterTransformer$transformImplicitTypeRefInAnonymousFunction$implicitTypeTransformer$1
            @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
            public <E extends FirElement> E transformElement(E element, Object data) {
                Intrinsics.checkNotNullParameter(element, "element");
                E e = (E) element.transformChildren(this, data);
                Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.transformers.FirCallCompletionResultsWriterTransformer.transformImplicitTypeRefInAnonymousFunction.<no name provided>.transformElement");
                return e;
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
            public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, Object data) {
                Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(implicitTypeRef.getSource());
                firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Cannot infer type w/o CFG", DiagnosticKind.InferenceError));
                return firErrorTypeRefBuilder.mo6809build();
            }
        };
        anonymousFunction.transformReturnTypeRef((FirTransformer<? super Object>) firDefaultTransformer, (Object) null);
        anonymousFunction.transformValueParameters((FirTransformer<? super Object>) firDefaultTransformer, (Object) null);
        anonymousFunction.transformBody((FirTransformer<? super Object>) firDefaultTransformer, (Object) null);
        return anonymousFunction;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformReturnExpression(FirReturnExpression returnExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
        FirFunction firFunction = (FirFunction) returnExpression.getTarget().getLabeledElement();
        if (firFunction instanceof FirAnonymousFunction) {
            return returnExpression;
        }
        FirTypeRef returnTypeRef = firFunction.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        return super.transformReturnExpression(returnExpression, type != null ? FirCallCompletionResultsWriterTransformerKt.toExpectedType(type) : null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, ExpectedArgumentType data) {
        ConeKotlinType expectedType;
        Intrinsics.checkNotNullParameter(block, "block");
        FirBlock firBlock = block;
        FirTypeRef typeRef = firBlock.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type != null) {
            FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firBlock.getTypeRef(), finallySubstituteOrNull(type), null, 2, null);
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRefWithReplacedConeType$default;
            FirResolvedTypeRef firResolvedTypeRef3 = firResolvedTypeRef2 instanceof FirResolvedTypeRef ? firResolvedTypeRef2 : null;
            ConeKotlinType type2 = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
            if (!(type2 instanceof ConeIntegerLiteralType)) {
                type2 = null;
            }
            ConeIntegerLiteralType coneIntegerLiteralType = (ConeIntegerLiteralType) type2;
            if (coneIntegerLiteralType != null) {
                firResolvedTypeRefWithReplacedConeType$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firResolvedTypeRef2, coneIntegerLiteralType.getApproximatedType((data == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, block)) == null) ? null : TypeExpansionUtilsKt.fullyExpandedType(expectedType, getSession())), null, 2, null);
            }
            FirResolvedTypeRef firResolvedTypeRef4 = firResolvedTypeRefWithReplacedConeType$default;
            block.replaceTypeRef(firResolvedTypeRef4);
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef4, block.getSource(), this.context.getFile().getSource());
            }
        }
        transformElement(block, data);
        if (firBlock.getTypeRef() instanceof FirErrorTypeRef) {
            BodyResolveUtilsKt.writeResultType(block, getSession());
        }
        return block;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <T> FirStatement transformConstExpression(FirConstExpression<T> constExpression, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        if (Intrinsics.areEqual(data, ExpectedArgumentType.NoApproximation.INSTANCE)) {
            return constExpression;
        }
        ConeKotlinType expectedType = data != null ? FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, constExpression) : null;
        if (expectedType instanceof ConeIntegerConstantOperatorType) {
            return constExpression;
        }
        return (FirStatement) FirTransformerUtilKt.transformSingle(constExpression, this.integerOperatorApproximator, expectedType);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformIntegerLiteralOperatorCall(FirIntegerLiteralOperatorCall integerLiteralOperatorCall, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(integerLiteralOperatorCall, "integerLiteralOperatorCall");
        if (Intrinsics.areEqual(data, ExpectedArgumentType.NoApproximation.INSTANCE)) {
            return integerLiteralOperatorCall;
        }
        ConeKotlinType expectedType = data != null ? FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, integerLiteralOperatorCall) : null;
        if (expectedType instanceof ConeIntegerConstantOperatorType) {
            return integerLiteralOperatorCall;
        }
        return (FirStatement) FirTransformerUtilKt.transformSingle(integerLiteralOperatorCall, this.integerOperatorApproximator, expectedType);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x008b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x008d  */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformArrayLiteral(FirArrayLiteral arrayLiteral, ExpectedArgumentType data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        if (!(arrayLiteral.getTypeRef() instanceof FirImplicitTypeRef)) {
            return arrayLiteral;
        }
        ConeKotlinType expectedType = data != null ? FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, arrayLiteral) : null;
        ConeClassLikeType coneClassLikeTypeArrayElementType = expectedType != null ? ArrayUtilsKt.arrayElementType(expectedType) : null;
        arrayLiteral.transformChildren(this, coneClassLikeTypeArrayElementType != null ? FirCallCompletionResultsWriterTransformerKt.toExpectedType(coneClassLikeTypeArrayElementType) : null);
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        List<FirExpression> arguments = arrayLiteral.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType(((FirExpression) it.next()).getTypeRef()));
        }
        ConeKotlinType coneKotlinTypeCommonSuperTypeOrNull = TypeUtilsKt.commonSuperTypeOrNull(typeContext, arrayList);
        if (coneKotlinTypeCommonSuperTypeOrNull != null) {
            ConeKotlinType coneKotlinTypeApproximateToSuperType = this.typeApproximator.approximateToSuperType(coneKotlinTypeCommonSuperTypeOrNull, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
            if (coneKotlinTypeApproximateToSuperType != null) {
                coneKotlinTypeCommonSuperTypeOrNull = coneKotlinTypeApproximateToSuperType;
            }
            if (coneKotlinTypeCommonSuperTypeOrNull != null) {
                coneClassLikeTypeArrayElementType = coneKotlinTypeCommonSuperTypeOrNull;
            } else if (coneClassLikeTypeArrayElementType == null) {
                coneClassLikeTypeArrayElementType = getSession().getBuiltinTypes().getNullableAnyType().getType();
            }
        } else if (coneClassLikeTypeArrayElementType == null) {
            coneClassLikeTypeArrayElementType = getSession().getBuiltinTypes().getNullableAnyType().getType();
        }
        arrayLiteral.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(arrayLiteral.getTypeRef(), ArrayUtilsKt.createArrayType$default(coneClassLikeTypeArrayElementType, false, expectedType != null && ConeBuiltinTypeUtilsKt.isPrimitiveArray(expectedType), 1, null), null, 2, null));
        return arrayLiteral;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformVarargArgumentsExpression(FirVarargArgumentsExpression varargArgumentsExpression, ExpectedArgumentType data) {
        ConeKotlinType expectedType;
        Intrinsics.checkNotNullParameter(varargArgumentsExpression, "varargArgumentsExpression");
        varargArgumentsExpression.transformChildren(this, (data == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, varargArgumentsExpression)) == null) ? null : new ExpectedArgumentType.ExpectedType(expectedType));
        return varargArgumentsExpression;
    }

    private final boolean hasAdditionalResolutionErrors(FirNamedReferenceWithCandidate firNamedReferenceWithCandidate) {
        List<ConstraintSystemError> errors = firNamedReferenceWithCandidate.getCandidate().getSystem().getErrors();
        if ((errors instanceof Collection) && errors.isEmpty()) {
            return false;
        }
        Iterator<T> it = errors.iterator();
        while (it.hasNext()) {
            if (((ConstraintSystemError) it.next()) instanceof InferredEmptyIntersection) {
                return true;
            }
        }
        return false;
    }

    private final FirNamedReference toResolvedReference(FirNamedReferenceWithCandidate firNamedReferenceWithCandidate) {
        ConeConstraintSystemHasContradiction coneConstraintSystemHasContradiction;
        if (firNamedReferenceWithCandidate instanceof FirErrorReferenceWithCandidate) {
            coneConstraintSystemHasContradiction = ((FirErrorReferenceWithCandidate) firNamedReferenceWithCandidate).getDiagnostic();
        } else if (!CandidateApplicabilityKt.isSuccess(firNamedReferenceWithCandidate.getCandidate().getCurrentApplicability())) {
            coneConstraintSystemHasContradiction = new ConeInapplicableCandidateError(firNamedReferenceWithCandidate.getCandidate().getCurrentApplicability(), firNamedReferenceWithCandidate.getCandidate());
        } else if (!firNamedReferenceWithCandidate.getCandidate().isSuccessful()) {
            if (!firNamedReferenceWithCandidate.getCandidate().getSystem().getHasContradiction()) {
                throw new IllegalArgumentException("Candidate is not successful, but system has no contradiction".toString());
            }
            coneConstraintSystemHasContradiction = new ConeConstraintSystemHasContradiction(firNamedReferenceWithCandidate.getCandidate());
        } else {
            coneConstraintSystemHasContradiction = hasAdditionalResolutionErrors(firNamedReferenceWithCandidate) ? new ConeConstraintSystemHasContradiction(firNamedReferenceWithCandidate.getCandidate()) : null;
        }
        if (coneConstraintSystemHasContradiction != null) {
            return ResolveUtilsKt.toErrorReference(firNamedReferenceWithCandidate, coneConstraintSystemHasContradiction);
        }
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setSource(firNamedReferenceWithCandidate.getSource());
        firResolvedNamedReferenceBuilder.setName(firNamedReferenceWithCandidate.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firNamedReferenceWithCandidate.getCandidateSymbol());
        return firResolvedNamedReferenceBuilder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWhenExpression(FirWhenExpression whenExpression, ExpectedArgumentType data) {
        ExpectedArgumentType expectedType;
        ConeKotlinType expectedType2;
        ExpectedArgumentType expectedType3;
        ConeKotlinType expectedType4;
        Candidate candidate;
        FirBasedSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
        FirWhenExpression firWhenExpression = whenExpression;
        FirWhenExpression firWhenExpression2 = firWhenExpression;
        FirReference calleeReference = firWhenExpression2.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        FirDeclaration fir = (firNamedReferenceWithCandidate == null || (candidate = firNamedReferenceWithCandidate.getCandidate()) == null || (symbol = candidate.getSymbol()) == null) ? null : symbol.getFir();
        FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
        if (firNamedReferenceWithCandidate == null || firSimpleFunction == null) {
            if (data == null || (expectedType2 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firWhenExpression)) == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType2)) == null) {
                expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firWhenExpression.getTypeRef()));
            }
            if (firWhenExpression instanceof FirTryExpression) {
                FirTryExpression firTryExpression = (FirTryExpression) firWhenExpression;
                FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer = this;
                firTryExpression.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformTryBlock(firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformCatches(firCallCompletionResultsWriterTransformer, expectedType);
            } else {
                firWhenExpression2.transformChildren(this, expectedType);
            }
            return firWhenExpression;
        }
        replaceTypeRefWithSubstituted(firWhenExpression, firNamedReferenceWithCandidate, this.typeCalculator.tryCalculateReturnType(firSimpleFunction));
        if (data == null || (expectedType4 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firWhenExpression)) == null || (expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType4)) == null) {
            expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firWhenExpression.getTypeRef()));
        }
        if (firWhenExpression instanceof FirTryExpression) {
            FirTryExpression firTryExpression2 = (FirTryExpression) firWhenExpression;
            FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer2 = this;
            firTryExpression2.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformTryBlock(firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformCatches(firCallCompletionResultsWriterTransformer2, expectedType3);
        } else {
            firWhenExpression2.transformChildren(this, expectedType3);
        }
        firWhenExpression2.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        return firWhenExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTryExpression(FirTryExpression tryExpression, ExpectedArgumentType data) {
        ExpectedArgumentType expectedType;
        ConeKotlinType expectedType2;
        ExpectedArgumentType expectedType3;
        ConeKotlinType expectedType4;
        Candidate candidate;
        FirBasedSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(tryExpression, "tryExpression");
        FirTryExpression firTryExpression = tryExpression;
        FirTryExpression firTryExpression2 = firTryExpression;
        FirReference calleeReference = firTryExpression2.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        FirDeclaration fir = (firNamedReferenceWithCandidate == null || (candidate = firNamedReferenceWithCandidate.getCandidate()) == null || (symbol = candidate.getSymbol()) == null) ? null : symbol.getFir();
        FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
        if (firNamedReferenceWithCandidate == null || firSimpleFunction == null) {
            if (data == null || (expectedType2 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firTryExpression)) == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType2)) == null) {
                expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firTryExpression.getTypeRef()));
            }
            FirTryExpression firTryExpression3 = firTryExpression;
            FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer = this;
            firTryExpression3.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer, expectedType);
            firTryExpression3.transformTryBlock(firCallCompletionResultsWriterTransformer, expectedType);
            firTryExpression3.transformCatches(firCallCompletionResultsWriterTransformer, expectedType);
            return firTryExpression;
        }
        replaceTypeRefWithSubstituted(firTryExpression, firNamedReferenceWithCandidate, this.typeCalculator.tryCalculateReturnType(firSimpleFunction));
        if (data == null || (expectedType4 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firTryExpression)) == null || (expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType4)) == null) {
            expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firTryExpression.getTypeRef()));
        }
        FirTryExpression firTryExpression4 = firTryExpression;
        FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer2 = this;
        firTryExpression4.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer2, expectedType3);
        firTryExpression4.transformTryBlock(firCallCompletionResultsWriterTransformer2, expectedType3);
        firTryExpression4.transformCatches(firCallCompletionResultsWriterTransformer2, expectedType3);
        firTryExpression2.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        return firTryExpression;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, ExpectedArgumentType data) {
        ExpectedArgumentType expectedType;
        ConeKotlinType expectedType2;
        ExpectedArgumentType expectedType3;
        ConeKotlinType expectedType4;
        Candidate candidate;
        FirBasedSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        FirCheckNotNullCall firCheckNotNullCall = checkNotNullCall;
        FirCheckNotNullCall firCheckNotNullCall2 = firCheckNotNullCall;
        FirReference calleeReference = firCheckNotNullCall2.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        FirDeclaration fir = (firNamedReferenceWithCandidate == null || (candidate = firNamedReferenceWithCandidate.getCandidate()) == null || (symbol = candidate.getSymbol()) == null) ? null : symbol.getFir();
        FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
        if (firNamedReferenceWithCandidate == null || firSimpleFunction == null) {
            if (data == null || (expectedType2 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firCheckNotNullCall)) == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType2)) == null) {
                expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firCheckNotNullCall.getTypeRef()));
            }
            if (firCheckNotNullCall instanceof FirTryExpression) {
                FirTryExpression firTryExpression = (FirTryExpression) firCheckNotNullCall;
                FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer = this;
                firTryExpression.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformTryBlock(firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformCatches(firCallCompletionResultsWriterTransformer, expectedType);
            } else {
                firCheckNotNullCall2.transformChildren(this, expectedType);
            }
            return firCheckNotNullCall;
        }
        replaceTypeRefWithSubstituted(firCheckNotNullCall, firNamedReferenceWithCandidate, this.typeCalculator.tryCalculateReturnType(firSimpleFunction));
        if (data == null || (expectedType4 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firCheckNotNullCall)) == null || (expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType4)) == null) {
            expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firCheckNotNullCall.getTypeRef()));
        }
        if (firCheckNotNullCall instanceof FirTryExpression) {
            FirTryExpression firTryExpression2 = (FirTryExpression) firCheckNotNullCall;
            FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer2 = this;
            firTryExpression2.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformTryBlock(firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformCatches(firCallCompletionResultsWriterTransformer2, expectedType3);
        } else {
            firCheckNotNullCall2.transformChildren(this, expectedType3);
        }
        firCheckNotNullCall2.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        return firCheckNotNullCall;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformElvisExpression(FirElvisExpression elvisExpression, ExpectedArgumentType data) {
        ExpectedArgumentType expectedType;
        ConeKotlinType expectedType2;
        ExpectedArgumentType expectedType3;
        ConeKotlinType expectedType4;
        Candidate candidate;
        FirBasedSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(elvisExpression, "elvisExpression");
        FirElvisExpression firElvisExpression = elvisExpression;
        FirElvisExpression firElvisExpression2 = firElvisExpression;
        FirReference calleeReference = firElvisExpression2.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        FirDeclaration fir = (firNamedReferenceWithCandidate == null || (candidate = firNamedReferenceWithCandidate.getCandidate()) == null || (symbol = candidate.getSymbol()) == null) ? null : symbol.getFir();
        FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
        if (firNamedReferenceWithCandidate == null || firSimpleFunction == null) {
            if (data == null || (expectedType2 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firElvisExpression)) == null || (expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType2)) == null) {
                expectedType = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firElvisExpression.getTypeRef()));
            }
            if (firElvisExpression instanceof FirTryExpression) {
                FirTryExpression firTryExpression = (FirTryExpression) firElvisExpression;
                FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer = this;
                firTryExpression.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformTryBlock(firCallCompletionResultsWriterTransformer, expectedType);
                firTryExpression.transformCatches(firCallCompletionResultsWriterTransformer, expectedType);
            } else {
                firElvisExpression2.transformChildren(this, expectedType);
            }
            return firElvisExpression;
        }
        replaceTypeRefWithSubstituted(firElvisExpression, firNamedReferenceWithCandidate, this.typeCalculator.tryCalculateReturnType(firSimpleFunction));
        if (data == null || (expectedType4 = FirCallCompletionResultsWriterTransformerKt.getExpectedType(data, firElvisExpression)) == null || (expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(expectedType4)) == null) {
            expectedType3 = FirCallCompletionResultsWriterTransformerKt.toExpectedType(FirTypeUtilsKt.getConeType(firElvisExpression.getTypeRef()));
        }
        if (firElvisExpression instanceof FirTryExpression) {
            FirTryExpression firTryExpression2 = (FirTryExpression) firElvisExpression;
            FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformer2 = this;
            firTryExpression2.transformCalleeReference((FirTransformer<? super ExpectedArgumentType>) firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformTryBlock(firCallCompletionResultsWriterTransformer2, expectedType3);
            firTryExpression2.transformCatches(firCallCompletionResultsWriterTransformer2, expectedType3);
        } else {
            firElvisExpression2.transformChildren(this, expectedType3);
        }
        firElvisExpression2.replaceCalleeReference(toResolvedReference(firNamedReferenceWithCandidate));
        return firElvisExpression;
    }
}
