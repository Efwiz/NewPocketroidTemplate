package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirGenerationKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousSuper;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeStubDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSyntaxDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousObjectExpression;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirAssignmentOperatorStatement;
import org.jetbrains.kotlin.fir.expressions.FirAugmentedArraySetCall;
import org.jetbrains.kotlin.fir.expressions.FirBinaryLogicExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirCheckedSafeCallSubject;
import org.jetbrains.kotlin.fir.expressions.FirComparisonExpression;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirDesugaredAssignmentValueReferenceExpression;
import org.jetbrains.kotlin.fir.expressions.FirEmptyArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirEqualityOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCallOrigin;
import org.jetbrains.kotlin.fir.expressions.FirGetClassCall;
import org.jetbrains.kotlin.fir.expressions.FirIncrementDecrementExpression;
import org.jetbrains.kotlin.fir.expressions.FirIntegerLiteralOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirOperationNameConventions;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirResolvedReifiedParameterReference;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirStringConcatenationCall;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.FirTypeOperatorCall;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.FirVariableAssignment;
import org.jetbrains.kotlin.fir.expressions.FirWrappedExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirBlockBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirDesugaredAssignmentValueReferenceExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirFunctionCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirIntegerLiteralOperatorCallBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirVarargArgumentsExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirVariableAssignmentBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirAnnotationArgumentMappingImplKt;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.extensions.FirAssignExpressionAltererExtension;
import org.jetbrains.kotlin.fir.extensions.FirAssignExpressionAltererExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExpressionResolutionExtension;
import org.jetbrains.kotlin.fir.extensions.FirExpressionResolutionExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.references.FirDelegateFieldReference;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.FirThisReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitSuperReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirSimpleNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionModeKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitExtensionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.InaccessibleImplicitReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.SuperCallsKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguousAlteredAssign;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeInstanceAccessBeforeSuperCall;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeNoTypeArgumentsOnRhsError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeOperatorAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedNameError;
import org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter;
import org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.FirStubInferenceSession;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSpecificTypeResolverTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.IntegerLiteralAndOperatorApproximationTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.InvocationKindTransformerKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirIntegerConstantOperatorScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeIntegerConstantOperatorTypeImpl;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantTypeImpl;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.TransformData;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.ConstantValueKind;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ò\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001:\u0006¯\u0001°\u0001±\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00020&H\u0002J\u0012\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010)\u001a\u00020*H\u0002J \u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020,2\u0006\u00100\u001a\u000201H\u0002J\"\u00102\u001a\u0002H3\"\u0004\b\u0000\u001032\f\u00104\u001a\b\u0012\u0004\u0012\u0002H305H\u0082\b¢\u0006\u0002\u00106J \u00107\u001a\u00020&2\u0006\u00108\u001a\u00020,2\u0006\u00109\u001a\u00020\u00152\u0006\u0010:\u001a\u00020;H\u0014J\u0010\u0010<\u001a\u00020\u00152\u0006\u0010=\u001a\u00020>H\u0014J\u001d\u0010?\u001a\u00020!2\u0006\u0010@\u001a\u00020,2\u0006\u0010A\u001a\u00020\u0015H\u0000¢\u0006\u0002\bBJ\u0018\u0010C\u001a\u00020&2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010H\u001a\u00020&2\u0006\u0010I\u001a\u00020J2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010K\u001a\u00020&2\u0006\u0010L\u001a\u00020M2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010N\u001a\u00020&2\u0006\u0010O\u001a\u00020P2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010Q\u001a\u00020&2\u0006\u0010R\u001a\u00020S2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010T\u001a\u00020&2\u0006\u0010U\u001a\u00020V2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010W\u001a\u00020&2\u0006\u0010X\u001a\u00020Y2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010Z\u001a\u00020&2\u0006\u0010[\u001a\u00020\\2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010]\u001a\u00020&2\u0006\u00104\u001a\u00020^2\u0006\u0010F\u001a\u00020GH\u0016J\u001d\u0010_\u001a\u00020!2\u0006\u00104\u001a\u00020^2\u0006\u0010F\u001a\u00020GH\u0000¢\u0006\u0002\b`J\u0018\u0010a\u001a\u00020&2\u0006\u0010b\u001a\u00020c2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010d\u001a\u00020&2\u0006\u0010e\u001a\u00020f2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010g\u001a\u00020&2\u0006\u0010h\u001a\u00020i2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010j\u001a\u00020&2\u0006\u0010k\u001a\u00020l2\u0006\u0010F\u001a\u00020GH\u0016J$\u0010m\u001a\u00020&\"\u0004\b\u0000\u001032\f\u0010n\u001a\b\u0012\u0004\u0012\u0002H30o2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010p\u001a\u00020&2\u0006\u0010q\u001a\u00020\u001e2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010r\u001a\u00020&2\u0006\u0010s\u001a\u00020t2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010u\u001a\u00020&2\u0006\u0010v\u001a\u00020w2\u0006\u0010F\u001a\u00020GH\u0016J\u0018\u0010x\u001a\u00020&2\u0006\u0010y\u001a\u00020z2\u0006\u0010F\u001a\u00020GH\u0016J\u001d\u0010{\u001a\u0002H|\"\b\b\u0000\u0010|*\u00020,2\u0006\u00108\u001a\u0002H|¢\u0006\u0002\u0010}J\u0019\u0010~\u001a\u00020&2\u0007\u0010\u007f\u001a\u00030\u0080\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u0019\u0010\u0081\u0001\u001a\u00020&2\u0006\u0010\"\u001a\u00020#2\u0006\u0010F\u001a\u00020GH\u0016J(\u0010\u0082\u0001\u001a\u00020&2\u0006\u0010\"\u001a\u00020#2\u0006\u0010F\u001a\u00020G2\u0007\u0010\u0083\u0001\u001a\u00020\u0015H\u0000¢\u0006\u0003\b\u0084\u0001J\u0019\u0010\u0085\u0001\u001a\u00020&2\u0006\u0010=\u001a\u00020>2\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u0086\u0001\u001a\u00020&2\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u0089\u0001\u001a\u00020&2\b\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u0019\u0010\u008c\u0001\u001a\u00020&2\u0006\u00108\u001a\u00020,2\u0006\u0010F\u001a\u00020GH\u0016J\u001f\u0010\u008c\u0001\u001a\u00020&2\u0006\u00108\u001a\u00020,2\u0006\u0010F\u001a\u00020G2\u0006\u00109\u001a\u00020\u0015J\u001a\u0010\u008d\u0001\u001a\u00020&2\u0007\u0010\u008e\u0001\u001a\u00020\u000f2\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u008f\u0001\u001a\u00020&2\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u0092\u0001\u001a\u00020&2\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\u0006\u0010F\u001a\u00020GH\u0016J\"\u0010\u0095\u0001\u001a\u00020,2\u0006\u00100\u001a\u0002012\u0006\u0010/\u001a\u00020,2\t\u0010\u0096\u0001\u001a\u0004\u0018\u00010,J\u001b\u0010\u0097\u0001\u001a\u00020&2\b\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u009a\u0001\u001a\u00020&2\b\u0010\u009b\u0001\u001a\u00030\u009c\u00012\u0006\u0010F\u001a\u00020GH\u0016J\u001b\u0010\u009d\u0001\u001a\u00020&2\b\u0010\u009e\u0001\u001a\u00030\u009f\u00012\u0006\u0010F\u001a\u00020GH\u0016J)\u0010 \u0001\u001a\u00070¡\u0001R\u00020\u00002\u0006\u0010X\u001a\u00020Y2\u0007\u0010¢\u0001\u001a\u00020#2\b\u0010£\u0001\u001a\u00030\u0080\u0001H\u0002J-\u0010¤\u0001\u001a\u0002H3\"\u0004\b\u0000\u001032\u0007\u0010\u008e\u0001\u001a\u00020\u000f2\f\u00104\u001a\b\u0012\u0004\u0012\u0002H305H\u0082\b¢\u0006\u0003\u0010¥\u0001J&\u0010¦\u0001\u001a\u0002H3\"\u0004\b\u0000\u001032\f\u00104\u001a\b\u0012\u0004\u0012\u0002H305H\u0084\bø\u0001\u0000¢\u0006\u0002\u00106J\r\u0010§\u0001\u001a\u00020\u0015*\u00020,H\u0014J\u0016\u0010¨\u0001\u001a\u00020#*\u00020#2\u0007\u0010©\u0001\u001a\u00020GH\u0002J\u000f\u0010ª\u0001\u001a\u00030\u009c\u0001*\u00030\u009c\u0001H\u0002J!\u0010«\u0001\u001a\u00020**\u00020*2\b\u0010¬\u0001\u001a\u00030\u0080\u00012\b\u0010\u00ad\u0001\u001a\u00030®\u0001H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\n\u001a\u00020\u000b8Â\u0002X\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u001d\u001a\u00020\u0015*\u00020\u001e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001f\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006²\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirPartialBodyResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "arrayOfCallTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirArrayOfCallTransformer;", "assignAltererExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirAssignExpressionAltererExtension;", "builtinTypes", "Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "getBuiltinTypes", "()Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "containingSafeCallExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "getContainingSafeCallExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;", "setContainingSafeCallExpression", "(Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;)V", "enableArrayOfCallTransformation", "", "getEnableArrayOfCallTransformation", "()Z", "setEnableArrayOfCallTransformation", "(Z)V", "expressionResolutionExtensions", "Lorg/jetbrains/kotlin/fir/extensions/FirExpressionResolutionExtension;", "resolvingAugmentedAssignment", "isCallToDelegatedConstructorWithoutArguments", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;)Z", "addReceiversFromExtensions", "", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "collectStatementContext", "statement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "extractSuperTypeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "markSuperReferenceError", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "superNotAvailableDiagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "superReferenceContainer", "superReference", "Lorg/jetbrains/kotlin/fir/references/FirSuperReference;", "resolveCandidateForAssignmentOperatorCall", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "resolveQualifiedAccessAndSelectCandidate", "qualifiedAccessExpression", "isUsedAsReceiver", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "shouldComputeTypeOfGetClassCallWithNotQualifierInLhs", "getClassCall", "Lorg/jetbrains/kotlin/fir/expressions/FirGetClassCall;", "storeTypeFromCallee", "access", "isLhsOfAssignment", "storeTypeFromCallee$resolve", "transformAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformAnonymousFunctionExpression", "anonymousFunctionExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousFunctionExpression;", "transformAnonymousObjectExpression", "anonymousObjectExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirAnonymousObjectExpression;", "transformArrayLiteral", "arrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", "transformAssignmentOperatorStatement", "assignmentOperatorStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirAssignmentOperatorStatement;", "transformAugmentedArraySetCall", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "transformBinaryLogicExpression", "binaryLogicExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirBinaryLogicExpression;", "transformBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformBlockInCurrentScope", "transformBlockInCurrentScope$resolve", "transformCallableReferenceAccess", "callableReferenceAccess", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "transformCheckNotNullCall", "checkNotNullCall", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckNotNullCall;", "transformCheckedSafeCallSubject", "checkedSafeCallSubject", "Lorg/jetbrains/kotlin/fir/expressions/FirCheckedSafeCallSubject;", "transformComparisonExpression", "comparisonExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirComparisonExpression;", "transformConstExpression", "constExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "transformDelegatedConstructorCall", "delegatedConstructorCall", "transformDesugaredAssignmentValueReferenceExpression", "desugaredAssignmentValueReferenceExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirDesugaredAssignmentValueReferenceExpression;", "transformEqualityOperatorCall", "equalityOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirEqualityOperatorCall;", "transformErrorAnnotationCall", "errorAnnotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorAnnotationCall;", "transformExplicitReceiver", "Q", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;)Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "transformExpression", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "transformFunctionCall", "transformFunctionCallInternal", "provideDelegate", "transformFunctionCallInternal$resolve", "transformGetClassCall", "transformIncrementDecrementExpression", "incrementDecrementExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirIncrementDecrementExpression;", "transformPropertyAccessExpression", "propertyAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirPropertyAccessExpression;", "transformQualifiedAccessExpression", "transformSafeCallExpression", "safeCallExpression", "transformSmartCastExpression", "smartCastExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirSmartCastExpression;", "transformStringConcatenationCall", "stringConcatenationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStringConcatenationCall;", "transformSuperReceiver", "containingCall", "transformThisReceiverExpression", "thisReceiverExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "transformTypeOperatorCall", "typeOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirTypeOperatorCall;", "transformVariableAssignment", "variableAssignment", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "tryResolveAugmentedArraySetCallAsSetGetBlock", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer$AugmentedArraySetAsGetSetCallDesugaringInfo;", "lhsGetCall", "transformedRhs", "withContainingSafeCallExpression", "(Lorg/jetbrains/kotlin/fir/expressions/FirSafeCallExpression;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFirArrayOfCallTransformer", "isAcceptableResolvedQualifiedAccess", "transformToIntegerOperatorCallOrApproximateItIfNeeded", "resolutionMode", "transformTypeOperatorCallChildren", "withTypeArgumentsForBareType", "argument", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "AugmentedArraySetAsGetSetCallDesugaringInfo", "GeneratorOfPlusAssignCalls", "InferenceSessionForAssignmentOperatorCall", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirExpressionsResolveTransformer extends FirPartialBodyResolveTransformer {
    private final FirArrayOfCallTransformer arrayOfCallTransformer;
    private final List<FirAssignExpressionAltererExtension> assignAltererExtensions;
    private FirSafeCallExpression containingSafeCallExpression;
    private boolean enableArrayOfCallTransformation;
    private final List<FirExpressionResolutionExtension> expressionResolutionExtensions;
    private boolean resolvingAugmentedAssignment;

    /* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FirOperation.values().length];
            try {
                iArr[FirOperation.IS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FirOperation.NOT_IS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FirOperation.AS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FirOperation.SAFE_AS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected boolean isAcceptableResolvedQualifiedAccess(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        return true;
    }

    protected boolean shouldComputeTypeOfGetClassCallWithNotQualifierInLhs(FirGetClassCall getClassCall) {
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        return true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExpressionsResolveTransformer(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.arrayOfCallTransformer = new FirArrayOfCallTransformer();
        List<FirExpressionResolutionExtension> expressionResolutionExtensions = FirExpressionResolutionExtensionKt.getExpressionResolutionExtensions(FirExtensionServiceKt.getExtensionService(getSession()));
        this.expressionResolutionExtensions = expressionResolutionExtensions.isEmpty() ? null : expressionResolutionExtensions;
        List<FirAssignExpressionAltererExtension> assignAltererExtensions = FirAssignExpressionAltererExtensionKt.getAssignAltererExtensions(FirExtensionServiceKt.getExtensionService(getSession()));
        this.assignAltererExtensions = assignAltererExtensions.isEmpty() ? null : assignAltererExtensions;
        getTransformer().getComponents().getCallResolver().initTransformer(this);
    }

    public final boolean getEnableArrayOfCallTransformation() {
        return this.enableArrayOfCallTransformation;
    }

    public final void setEnableArrayOfCallTransformation(boolean z) {
        this.enableArrayOfCallTransformation = z;
    }

    public final FirSafeCallExpression getContainingSafeCallExpression() {
        return this.containingSafeCallExpression;
    }

    public final void setContainingSafeCallExpression(FirSafeCallExpression firSafeCallExpression) {
        this.containingSafeCallExpression = firSafeCallExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSmartCastExpression(FirSmartCastExpression smartCastExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(smartCastExpression, "smartCastExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return smartCastExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            return transformQualifiedAccessExpression(qualifiedAccessExpression, data, false);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(qualifiedAccessExpression, th);
            throw null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0122  */
    /* JADX WARN: Code duplicated, block: B:71:0x01ae  */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirStatement transformQualifiedAccessExpression(FirQualifiedAccessExpression qualifiedAccessExpression, ResolutionMode data, boolean isUsedAsReceiver) {
        FirQualifiedAccessExpression firQualifiedAccessExpressionTransformSuperReceiver;
        FirStatement selector;
        FirErrorTypeRef firErrorTypeRefMo6809build;
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        if ((qualifiedAccessExpression.getTypeRef() instanceof FirResolvedTypeRef) && !(qualifiedAccessExpression.getCalleeReference() instanceof FirSimpleNamedReference)) {
            return qualifiedAccessExpression;
        }
        qualifiedAccessExpression.transformAnnotations((FirTransformer<? super ResolutionMode>) this, data);
        qualifiedAccessExpression.transformTypeArguments(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        FirReference calleeReference = qualifiedAccessExpression.getCalleeReference();
        if (calleeReference instanceof FirThisReference) {
            FirThisReference firThisReference = (FirThisReference) calleeReference;
            String labelName = firThisReference.getLabelName();
            ImplicitReceiverValue<?> implicitReceiverValue = getComponents().getImplicitReceiverStack().get(labelName);
            if (implicitReceiverValue != null) {
                firThisReference.replaceBoundSymbol(implicitReceiverValue.getBoundSymbol());
                if (implicitReceiverValue instanceof ContextReceiverValue) {
                    firThisReference.replaceContextReceiverNumber(implicitReceiverValue.getContextReceiverNumber());
                }
            }
            ConeKotlinType originalType = implicitReceiverValue != null ? implicitReceiverValue.getOriginalType() : null;
            FirQualifiedAccessExpression firQualifiedAccessExpression = qualifiedAccessExpression;
            if (implicitReceiverValue instanceof InaccessibleImplicitReceiverValue) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(qualifiedAccessExpression.getSource());
                firErrorTypeRefBuilder.setDiagnostic(new ConeInstanceAccessBeforeSuperCall("<this>"));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            } else if (originalType != null) {
                KtSourceElement source = calleeReference.getSource();
                firErrorTypeRefMo6809build = TypeUtilsKt.toFirResolvedTypeRef$default(originalType, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null, null, 2, null);
            } else if (labelName != null) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder2.setSource(qualifiedAccessExpression.getSource());
                firErrorTypeRefBuilder2.setDiagnostic(new ConeSimpleDiagnostic("Unresolved this@" + labelName, DiagnosticKind.UnresolvedLabel));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder2.mo6809build();
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder3 = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder3.setSource(qualifiedAccessExpression.getSource());
                firErrorTypeRefBuilder3.setDiagnostic(new ConeSimpleDiagnostic("'this' is not defined in this context", DiagnosticKind.NoThis));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder3.mo6809build();
            }
            firQualifiedAccessExpression.replaceTypeRef(firErrorTypeRefMo6809build);
            firQualifiedAccessExpressionTransformSuperReceiver = qualifiedAccessExpression;
        } else if (calleeReference instanceof FirSuperReference) {
            FirSuperReference firSuperReference = (FirSuperReference) calleeReference;
            FirSafeCallExpression firSafeCallExpression = this.containingSafeCallExpression;
            if (firSafeCallExpression == null) {
                selector = null;
            } else {
                if (!Intrinsics.areEqual(qualifiedAccessExpression, firSafeCallExpression.getReceiver())) {
                    firSafeCallExpression = null;
                }
                if (firSafeCallExpression != null) {
                    selector = firSafeCallExpression.getSelector();
                } else {
                    selector = null;
                }
            }
            firQualifiedAccessExpressionTransformSuperReceiver = transformSuperReceiver(firSuperReference, qualifiedAccessExpression, selector instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) selector : null);
        } else if (!(calleeReference instanceof FirDelegateFieldReference)) {
            if (calleeReference instanceof FirResolvedNamedReference ? true : calleeReference instanceof FirErrorNamedReference) {
                if (!(qualifiedAccessExpression.getTypeRef() instanceof FirResolvedTypeRef)) {
                    storeTypeFromCallee$resolve(qualifiedAccessExpression, false);
                }
                firQualifiedAccessExpressionTransformSuperReceiver = qualifiedAccessExpression;
            } else {
                FirStatement firStatementResolveQualifiedAccessAndSelectCandidate = resolveQualifiedAccessAndSelectCandidate(qualifiedAccessExpression, isUsedAsReceiver, data instanceof ResolutionMode.AssignmentLValue ? ((ResolutionMode.AssignmentLValue) data).getVariableAssignment() : qualifiedAccessExpression);
                if (firStatementResolveQualifiedAccessAndSelectCandidate instanceof FirQualifiedAccessExpression) {
                    FirResolvable firResolvable = (FirResolvable) firStatementResolveQualifiedAccessAndSelectCandidate;
                    if (CandidateFactoryKt.candidate(firResolvable) == null) {
                        firQualifiedAccessExpressionTransformSuperReceiver = firStatementResolveQualifiedAccessAndSelectCandidate;
                    } else {
                        if (!isAcceptableResolvedQualifiedAccess((FirQualifiedAccessExpression) firStatementResolveQualifiedAccessAndSelectCandidate)) {
                            return qualifiedAccessExpression;
                        }
                        firQualifiedAccessExpressionTransformSuperReceiver = (FirStatement) getComponents().getCallCompleter().completeCall(firResolvable, data);
                    }
                } else {
                    firQualifiedAccessExpressionTransformSuperReceiver = firStatementResolveQualifiedAccessAndSelectCandidate;
                }
            }
        } else {
            FirExpression delegate = ((FirProperty) ((FirDelegateFieldReference) calleeReference).getResolvedSymbol().getFir()).getDelegate();
            Intrinsics.checkNotNull(delegate);
            qualifiedAccessExpression.replaceTypeRef(delegate.getTypeRef());
            firQualifiedAccessExpressionTransformSuperReceiver = qualifiedAccessExpression;
        }
        if (!(data instanceof ResolutionMode.AssignmentLValue)) {
            if (firQualifiedAccessExpressionTransformSuperReceiver instanceof FirQualifiedAccessExpression) {
                FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
                FirQualifiedAccessExpression firQualifiedAccessExpression2 = (FirQualifiedAccessExpression) firQualifiedAccessExpressionTransformSuperReceiver;
                firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().exitQualifiedAccessExpression(firQualifiedAccessExpression2);
                firQualifiedAccessExpressionTransformSuperReceiver = ResolveUtilsKt.transformQualifiedAccessUsingSmartcastInfo(getTransformer().getComponents(), firQualifiedAccessExpression2);
                if (firQualifiedAccessExpressionTransformSuperReceiver instanceof FirSmartCastExpression) {
                    firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().exitSmartCastExpression((FirSmartCastExpression) firQualifiedAccessExpressionTransformSuperReceiver);
                }
            } else if (firQualifiedAccessExpressionTransformSuperReceiver instanceof FirResolvedQualifier) {
                getComponents().getDataFlowAnalyzer().exitResolvedQualifierNode((FirResolvedQualifier) firQualifiedAccessExpressionTransformSuperReceiver);
            }
        }
        return firQualifiedAccessExpressionTransformSuperReceiver;
    }

    public final <Q extends FirQualifiedAccessExpression> Q transformExplicitReceiver(Q qualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        FirExpression explicitReceiver = qualifiedAccessExpression.getExplicitReceiver();
        FirQualifiedAccessExpression firQualifiedAccessExpression = explicitReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver : null;
        if (firQualifiedAccessExpression != null) {
            FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
            FirSuperReference firSuperReference = calleeReference instanceof FirSuperReference ? (FirSuperReference) calleeReference : null;
            if (firSuperReference != null) {
                transformSuperReceiver(firSuperReference, firQualifiedAccessExpression, qualifiedAccessExpression);
                return qualifiedAccessExpression;
            }
        }
        if (firQualifiedAccessExpression instanceof FirPropertyAccessExpression) {
            FirStatement firStatementTransformQualifiedAccessExpression = transformQualifiedAccessExpression(firQualifiedAccessExpression, ResolutionMode.ReceiverResolution.INSTANCE, true);
            Intrinsics.checkNotNull(firStatementTransformQualifiedAccessExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
            qualifiedAccessExpression.replaceExplicitReceiver((FirExpression) firStatementTransformQualifiedAccessExpression);
            return qualifiedAccessExpression;
        }
        Q q = (Q) qualifiedAccessExpression.transformExplicitReceiver(getTransformer(), ResolutionMode.ReceiverResolution.INSTANCE);
        Intrinsics.checkNotNull(q, "null cannot be cast to non-null type Q of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer.transformExplicitReceiver");
        return q;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessExpression(FirPropertyAccessExpression propertyAccessExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(propertyAccessExpression, "propertyAccessExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformQualifiedAccessExpression((FirQualifiedAccessExpression) propertyAccessExpression, data);
    }

    protected FirStatement resolveQualifiedAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccessExpression, boolean isUsedAsReceiver, FirElement callSite) {
        Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
        Intrinsics.checkNotNullParameter(callSite, "callSite");
        return getComponents().getCallResolver().resolveVariableAccessAndSelectCandidate(qualifiedAccessExpression, isUsedAsReceiver, callSite);
    }

    /* JADX WARN: Code duplicated, block: B:76:0x0158  */
    /* JADX WARN: Code duplicated, block: B:78:0x015e  */
    /* JADX WARN: Multi-variable type inference failed */
    public final FirQualifiedAccessExpression transformSuperReceiver(FirSuperReference superReference, FirQualifiedAccessExpression superReferenceContainer, FirQualifiedAccessExpression containingCall) {
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        FirErrorTypeRef firErrorTypeRefMo6809build;
        FirErrorTypeRef firErrorTypeRefMo6809build2;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        Object next;
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default2;
        FirClassSymbol<?> boundSymbol;
        FirClass firClass;
        FirExpression receiverExpression;
        Intrinsics.checkNotNullParameter(superReference, "superReference");
        Intrinsics.checkNotNullParameter(superReferenceContainer, "superReferenceContainer");
        String labelName = superReference.getLabelName();
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
        ImplicitDispatchReceiverValue implicitDispatchReceiverValueLastDispatchReceiver = firExpressionsResolveTransformer.getComponents().getImplicitReceiverStack().lastDispatchReceiver();
        ConeClassLikeType coneClassLikeType = null;
        if (labelName != null && implicitDispatchReceiverValueLastDispatchReceiver != null) {
            ImplicitReceiverValue<?> implicitReceiverValue = firExpressionsResolveTransformer.getComponents().getImplicitReceiverStack().get(labelName);
            implicitDispatchReceiverValueLastDispatchReceiver = implicitReceiverValue instanceof ImplicitDispatchReceiverValue ? (ImplicitDispatchReceiverValue) implicitReceiverValue : null;
            if (implicitDispatchReceiverValueLastDispatchReceiver == null) {
                return markSuperReferenceError(new ConeSimpleDiagnostic("Unresolved label", DiagnosticKind.UnresolvedLabel), superReferenceContainer, superReference);
            }
        }
        if (implicitDispatchReceiverValueLastDispatchReceiver != null && (receiverExpression = implicitDispatchReceiverValueLastDispatchReceiver.getReceiverExpression()) != null) {
            superReferenceContainer.replaceDispatchReceiver(receiverExpression);
        }
        List<FirTypeRef> superTypeRefs = (implicitDispatchReceiverValueLastDispatchReceiver == null || (boundSymbol = implicitDispatchReceiverValueLastDispatchReceiver.getBoundSymbol()) == null || (firClass = (FirClass) boundSymbol.getFir()) == null) ? null : firClass.getSuperTypeRefs();
        FirTypeRef superTypeRef = superReference.getSuperTypeRef();
        if (containingCall == null) {
            return markSuperReferenceError(new ConeSimpleDiagnostic("Super not allowed", DiagnosticKind.SuperNotAllowed), superReferenceContainer, superReference);
        }
        if (implicitDispatchReceiverValueLastDispatchReceiver == null || superTypeRefs == null || superTypeRefs.isEmpty()) {
            if (CollectionsKt.lastOrNull(firExpressionsResolveTransformer.getComponents().getImplicitReceiverStack()) instanceof InaccessibleImplicitReceiverValue) {
                coneSimpleDiagnostic = new ConeInstanceAccessBeforeSuperCall("<super>");
            } else {
                coneSimpleDiagnostic = new ConeSimpleDiagnostic("Super not available", DiagnosticKind.SuperNotAvailable);
            }
            return markSuperReferenceError(coneSimpleDiagnostic, superReferenceContainer, superReference);
        }
        if (!(superTypeRef instanceof FirResolvedTypeRef)) {
            if (!(superTypeRef instanceof FirImplicitTypeRef)) {
                FirSpecificTypeResolverTransformer typeResolverTransformer = getTransformer().getComponents().getTypeResolverTransformer();
                boolean areBareTypesAllowed = typeResolverTransformer.getAreBareTypesAllowed();
                typeResolverTransformer.setAreBareTypesAllowed(true);
                try {
                    superReference.transformChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                    typeResolverTransformer.setAreBareTypesAllowed(areBareTypesAllowed);
                    ConeKotlinType coneType = FirTypeUtilsKt.getConeType(superReference.getSuperTypeRef());
                    ConeClassLikeType coneClassLikeType2 = coneType instanceof ConeClassLikeType ? (ConeClassLikeType) coneType : null;
                    if (coneClassLikeType2 != null && (coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType2, getSession(), null, 2, null)) != null) {
                        ClassId classId = coneClassLikeTypeFullyExpandedType$default.getLookupTag().getClassId();
                        Iterator<T> it = superTypeRefs.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (!Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType((FirTypeRef) next), getSession())), classId));
                        FirTypeRef firTypeRef = (FirTypeRef) next;
                        if (firTypeRef != null) {
                            FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
                            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                            if (!(type instanceof ConeClassLikeType)) {
                                type = null;
                            }
                            ConeClassLikeType coneClassLikeType3 = (ConeClassLikeType) type;
                            if (coneClassLikeType3 != null && (coneClassLikeTypeFullyExpandedType$default2 = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType3, getSession(), null, 2, null)) != null) {
                                if (coneClassLikeTypeFullyExpandedType$default.getTypeArguments().length == 0) {
                                    if (!(coneClassLikeTypeFullyExpandedType$default2.getTypeArguments().length == 0)) {
                                        coneClassLikeType = coneClassLikeTypeFullyExpandedType$default2;
                                    } else if (Intrinsics.areEqual(coneClassLikeTypeFullyExpandedType$default, coneClassLikeTypeFullyExpandedType$default2)) {
                                        coneClassLikeType = coneClassLikeTypeFullyExpandedType$default2;
                                    }
                                } else if (Intrinsics.areEqual(coneClassLikeTypeFullyExpandedType$default, coneClassLikeTypeFullyExpandedType$default2)) {
                                    coneClassLikeType = coneClassLikeTypeFullyExpandedType$default2;
                                }
                            }
                        }
                    }
                    if (coneClassLikeType == null || (firErrorTypeRefMo6809build2 = TypeUtilsKt.toFirResolvedTypeRef(coneClassLikeType, superTypeRef.getSource(), superTypeRef)) == null) {
                        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                        firErrorTypeRefBuilder.setSource(superTypeRef.getSource());
                        firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Not a super type", DiagnosticKind.NotASupertype));
                        firErrorTypeRefMo6809build2 = firErrorTypeRefBuilder.mo6809build();
                    }
                    FirTypeRef firTypeRef2 = firErrorTypeRefMo6809build2;
                    superReferenceContainer.replaceTypeRef(UtilsKt.copyWithNewSourceKind(firTypeRef2, KtFakeSourceElementKind.SuperCallExplicitType.INSTANCE));
                    superReference.replaceSuperTypeRef(firTypeRef2);
                } catch (Throwable th) {
                    typeResolverTransformer.setAreBareTypesAllowed(areBareTypesAllowed);
                    throw th;
                }
            } else {
                List<ConeKotlinType> listFindTypesForSuperCandidates = SuperCallsKt.findTypesForSuperCandidates(getTransformer().getComponents(), superTypeRefs, containingCall);
                int size = listFindTypesForSuperCandidates.size();
                if (size == 0) {
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder2.setSource(superReferenceContainer.getSource());
                    firErrorTypeRefBuilder2.setDiagnostic(new ConeStubDiagnostic(new ConeSimpleDiagnostic("Unresolved super method", DiagnosticKind.Other)));
                    firErrorTypeRefMo6809build = firErrorTypeRefBuilder2.mo6809build();
                } else if (size == 1) {
                    ConeKotlinType coneKotlinType = (ConeKotlinType) CollectionsKt.single((List) listFindTypesForSuperCandidates);
                    KtSourceElement source = superReferenceContainer.getSource();
                    firErrorTypeRefMo6809build = TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinType, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.SuperCallImplicitType.INSTANCE, 0, 0, 6, null) : null, null, 2, null);
                } else {
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder3 = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder3.setSource(superReferenceContainer.getSource());
                    firErrorTypeRefBuilder3.setDiagnostic(new ConeAmbiguousSuper(listFindTypesForSuperCandidates));
                    firErrorTypeRefMo6809build = firErrorTypeRefBuilder3.mo6809build();
                }
                FirTypeRef firTypeRef3 = firErrorTypeRefMo6809build;
                superReferenceContainer.replaceTypeRef(UtilsKt.copyWithNewSourceKind(firTypeRef3, KtFakeSourceElementKind.SuperCallExplicitType.INSTANCE));
                superReference.replaceSuperTypeRef(firTypeRef3);
            }
        } else {
            superReferenceContainer.replaceTypeRef(UtilsKt.copyWithNewSourceKind(superTypeRef, KtFakeSourceElementKind.SuperCallExplicitType.INSTANCE));
        }
        return superReferenceContainer;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSafeCallExpression(FirSafeCallExpression safeCallExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(safeCallExpression, "safeCallExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirSafeCallExpression firSafeCallExpression = this.containingSafeCallExpression;
            try {
                this.containingSafeCallExpression = safeCallExpression;
                safeCallExpression.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) this, ResolutionMode.ContextIndependent.INSTANCE);
                safeCallExpression.transformReceiver(this, ResolutionMode.ContextIndependent.INSTANCE);
                FirExpression receiver = safeCallExpression.getReceiver();
                getComponents().getDataFlowAnalyzer().enterSafeCallAfterNullCheck(safeCallExpression);
                ResolveUtilsKt.propagateTypeFromOriginalReceiver((FirCheckedSafeCallSubject) safeCallExpression.getCheckedSubjectRef().getValue(), receiver, getTransformer().getComponents().getSession(), getTransformer().getComponents().getFile());
                safeCallExpression.transformSelector(this, data);
                ResolveUtilsKt.propagateTypeFromQualifiedAccessAfterNullCheck(safeCallExpression, getSession(), getTransformer().getContext().getFile());
                getComponents().getDataFlowAnalyzer().exitSafeCall(safeCallExpression);
                return safeCallExpression;
            } finally {
                this.containingSafeCallExpression = firSafeCallExpression;
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(safeCallExpression, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckedSafeCallSubject(FirCheckedSafeCallSubject checkedSafeCallSubject, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(checkedSafeCallSubject, "checkedSafeCallSubject");
        Intrinsics.checkNotNullParameter(data, "data");
        return checkedSafeCallSubject;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunctionCall(FirFunctionCall functionCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformFunctionCallInternal$resolve(functionCall, data, false);
    }

    public final FirStatement transformFunctionCallInternal$resolve(FirFunctionCall functionCall, ResolutionMode data, boolean provideDelegate) {
        FirFunctionCall firFunctionCall;
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirNamedReference calleeReference = functionCall.getCalleeReference();
            if (((calleeReference instanceof FirResolvedNamedReference) || (calleeReference instanceof FirErrorNamedReference)) && (functionCall.getTypeRef() instanceof FirImplicitTypeRef)) {
                storeTypeFromCallee$resolve(functionCall, false);
            }
            if (calleeReference instanceof FirNamedReferenceWithCandidate) {
                return functionCall;
            }
            if (!(calleeReference instanceof FirSimpleNamedReference)) {
                if (!(calleeReference instanceof FirResolvedNamedReference)) {
                    functionCall.transformChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                }
                return functionCall;
            }
            functionCall.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            InvocationKindTransformerKt.replaceLambdaArgumentInvocationKinds(functionCall, getSession());
            functionCall.transformTypeArguments((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            if (this.resolvingAugmentedAssignment) {
                firFunctionCall = functionCall;
            } else {
                getComponents().getDataFlowAnalyzer().enterCallArguments(functionCall, functionCall.getArgumentList().getArguments());
                firFunctionCall = provideDelegate ? functionCall : (FirFunctionCall) transformExplicitReceiver(functionCall);
                firFunctionCall.replaceArgumentList((FirArgumentList) firFunctionCall.getArgumentList().transform(this, ResolutionMode.ContextDependent.INSTANCE));
                getComponents().getDataFlowAnalyzer().exitCallArguments();
            }
            FirFunctionCall firFunctionCallTransformToIntegerOperatorCallOrApproximateItIfNeeded = transformToIntegerOperatorCallOrApproximateItIfNeeded((FirFunctionCall) getComponents().getCallCompleter().completeCall(getComponents().getCallResolver().resolveCallAndSelectCandidate(firFunctionCall), data), data);
            if (!this.resolvingAugmentedAssignment) {
                getComponents().getDataFlowAnalyzer().exitFunctionCall(firFunctionCallTransformToIntegerOperatorCallOrApproximateItIfNeeded, data.getForceFullCompletion());
            }
            addReceiversFromExtensions(firFunctionCallTransformToIntegerOperatorCallOrApproximateItIfNeeded);
            if (this.enableArrayOfCallTransformation) {
                return this.arrayOfCallTransformer.transformFunctionCall(firFunctionCallTransformToIntegerOperatorCallOrApproximateItIfNeeded, getSession());
            }
            return firFunctionCallTransformToIntegerOperatorCallOrApproximateItIfNeeded;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(functionCall, th);
            throw null;
        }
    }

    private final void addReceiversFromExtensions(FirFunctionCall functionCall) {
        List<FirExpressionResolutionExtension> list = this.expressionResolutionExtensions;
        if (list == null) {
            return;
        }
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
        FirDeclaration containerIfAny = firExpressionsResolveTransformer.getTransformer().getContext().getContainerIfAny();
        FirBasedSymbol<? extends FirDeclaration> symbol = containerIfAny != null ? containerIfAny.getSymbol() : null;
        FirCallableSymbol firCallableSymbol = symbol instanceof FirCallableSymbol ? (FirCallableSymbol) symbol : null;
        if (firCallableSymbol == null) {
            return;
        }
        Iterator<FirExpressionResolutionExtension> it = list.iterator();
        while (it.hasNext()) {
            Iterator<ConeKotlinType> it2 = it.next().addNewImplicitReceivers(functionCall).iterator();
            while (it2.hasNext()) {
                BodyResolveContext.addReceiver$default(firExpressionsResolveTransformer.getTransformer().getContext(), null, new ImplicitExtensionReceiverValue(firCallableSymbol, it2.next(), getSession(), getComponents().getScopeSession(), false, 16, null), null, 4, null);
            }
        }
    }

    private final FirFunctionCall transformToIntegerOperatorCallOrApproximateItIfNeeded(FirFunctionCall firFunctionCall, ResolutionMode resolutionMode) {
        FirExpression firExpression;
        if (!ResolveUtilsKt.isIntegerLiteralOrOperatorCall(firFunctionCall.getExplicitReceiver())) {
            return firFunctionCall;
        }
        boolean z = false;
        ConeKotlinType coneKotlinType = null;
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null) {
            return firFunctionCall;
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol = resolvedNamedFunctionSymbol$default;
        if (!FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperator(firNamedFunctionSymbol)) {
            return firFunctionCall;
        }
        List<FirExpression> arguments = firFunctionCall.getArgumentList().getArguments();
        int size = arguments.size();
        if (size == 0) {
            firExpression = null;
        } else {
            if (size != 1) {
                return firFunctionCall;
            }
            firExpression = (FirExpression) CollectionsKt.first((List) arguments);
        }
        if (firExpression != null) {
            ResolveUtilsKt.isIntegerLiteralOrOperatorCall(firExpression);
        }
        ConeIntegerConstantOperatorTypeImpl coneIntegerConstantOperatorTypeImpl = new ConeIntegerConstantOperatorTypeImpl(FirIntegerConstantOperatorScopeKt.isWrappedIntegerOperatorForUnsignedType(firNamedFunctionSymbol), ConeNullability.NOT_NULL);
        if (!(resolutionMode instanceof ResolutionMode.ReceiverResolution) && (!(resolutionMode instanceof ResolutionMode.ContextDependent) || (resolutionMode instanceof ResolutionMode.ContextDependent.Delegate))) {
            z = true;
        }
        FirIntegerLiteralOperatorCallBuilder firIntegerLiteralOperatorCallBuilder = new FirIntegerLiteralOperatorCallBuilder();
        firIntegerLiteralOperatorCallBuilder.setSource(firFunctionCall.getSource());
        firIntegerLiteralOperatorCallBuilder.setTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firFunctionCall.getTypeRef(), coneIntegerConstantOperatorTypeImpl, null, 2, null));
        firIntegerLiteralOperatorCallBuilder.getAnnotations().addAll(firFunctionCall.getAnnotations());
        firIntegerLiteralOperatorCallBuilder.getTypeArguments().addAll(firFunctionCall.getTypeArguments());
        firIntegerLiteralOperatorCallBuilder.setCalleeReference(firFunctionCall.getCalleeReference());
        firIntegerLiteralOperatorCallBuilder.setOrigin(firFunctionCall.getOrigin());
        firIntegerLiteralOperatorCallBuilder.setArgumentList(firFunctionCall.getArgumentList());
        firIntegerLiteralOperatorCallBuilder.setExplicitReceiver(firFunctionCall.getExplicitReceiver());
        firIntegerLiteralOperatorCallBuilder.setDispatchReceiver(firFunctionCall.getDispatchReceiver());
        firIntegerLiteralOperatorCallBuilder.setExtensionReceiver(firFunctionCall.getExtensionReceiver());
        FirIntegerLiteralOperatorCall firIntegerLiteralOperatorCallMo6809build = firIntegerLiteralOperatorCallBuilder.mo6809build();
        if (z) {
            FirIntegerLiteralOperatorCall firIntegerLiteralOperatorCall = firIntegerLiteralOperatorCallMo6809build;
            IntegerLiteralAndOperatorApproximationTransformer integerLiteralAndOperatorApproximationTransformer = getTransformer().getComponents().getIntegerLiteralAndOperatorApproximationTransformer();
            FirTypeRef expectedType = getExpectedType(resolutionMode);
            if (expectedType != null) {
                FirResolvedTypeRef firResolvedTypeRef = expectedType instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expectedType : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (type instanceof ConeKotlinType) {
                    coneKotlinType = type;
                }
            }
            return (FirFunctionCall) FirTransformerUtilKt.transformSingle(firIntegerLiteralOperatorCall, integerLiteralAndOperatorApproximationTransformer, coneKotlinType);
        }
        return firIntegerLiteralOperatorCallMo6809build;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        BodyResolveContext context = getTransformer().getContext();
        FirSession session = getSession();
        FirTowerDataContext towerDataContext = context.getTowerDataContext();
        try {
            context.addLocalScope(new FirLocalScope(session));
            transformBlockInCurrentScope$resolve(block, data);
            Unit unit = Unit.INSTANCE;
            return block;
        } finally {
            context.replaceTowerDataContext(towerDataContext);
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0072  */
    public final void transformBlockInCurrentScope$resolve(final FirBlock block, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(block, "block");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
        firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().enterBlock(block);
        final int size = block.getStatements().size();
        FirExpressionUtilKt.transformStatementsIndexed(block, getTransformer(), new Function1<Integer, TransformData<? extends ResolutionMode>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer$transformBlockInCurrentScope$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ TransformData<? extends ResolutionMode> invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final TransformData<ResolutionMode> invoke(int i) {
                ResolutionMode.ContextIndependent contextIndependentCopy$default;
                if (i == size - 1) {
                    contextIndependentCopy$default = data;
                    if (contextIndependentCopy$default instanceof ResolutionMode.WithExpectedType) {
                        contextIndependentCopy$default = ResolutionMode.WithExpectedType.copy$default((ResolutionMode.WithExpectedType) contextIndependentCopy$default, true, false, 2, null);
                    }
                } else {
                    contextIndependentCopy$default = ResolutionMode.ContextIndependent.INSTANCE;
                }
                this.collectStatementContext(block.getStatements().get(i));
                return new TransformData.Data(contextIndependentCopy$default);
            }
        });
        block.transformOtherChildren(getTransformer(), data);
        if (!(data instanceof ResolutionMode.WithExpectedType)) {
            BodyResolveUtilsKt.writeResultType(block, getSession());
        } else {
            ResolutionMode.WithExpectedType withExpectedType = (ResolutionMode.WithExpectedType) data;
            FirResolvedTypeRef expectedTypeRef = withExpectedType.getExpectedTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = expectedTypeRef instanceof FirResolvedTypeRef ? expectedTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
            if (coneKotlinType == null || !CallableReferenceResolutionKt.isUnitOrFlexibleUnit(coneKotlinType)) {
                BodyResolveUtilsKt.writeResultType(block, getSession());
            } else {
                block.replaceTypeRef(withExpectedType.getExpectedTypeRef());
            }
        }
        firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().exitBlock(block);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void collectStatementContext(FirStatement statement) {
        FirResolveContextCollector firResolveContextCollector = getTransformer().getFirResolveContextCollector();
        if (firResolveContextCollector != null) {
            firResolveContextCollector.addStatementContext(statement, getTransformer().getContext());
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformThisReceiverExpression(FirThisReceiverExpression thisReceiverExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(thisReceiverExpression, "thisReceiverExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformQualifiedAccessExpression((FirQualifiedAccessExpression) thisReceiverExpression, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformComparisonExpression(FirComparisonExpression comparisonExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(comparisonExpression, "comparisonExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirElement firElementTransformChildren = comparisonExpression.transformChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirComparisonExpression");
            FirComparisonExpression firComparisonExpression = (FirComparisonExpression) firElementTransformChildren;
            firComparisonExpression.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(comparisonExpression.getTypeRef(), getSession().getBuiltinTypes().getBooleanType().getType(), null, 2, null));
            getComponents().getDataFlowAnalyzer().exitComparisonExpressionCall(firComparisonExpression);
            return firComparisonExpression;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(comparisonExpression, th);
            throw null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:40:0x012c  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAssignmentOperatorStatement(FirAssignmentOperatorStatement assignmentOperatorStatement, ResolutionMode data) {
        boolean z;
        Intrinsics.checkNotNullParameter(assignmentOperatorStatement, "assignmentOperatorStatement");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirOperation operation = assignmentOperatorStatement.getOperation();
            if (operation == FirOperation.ASSIGN) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            assignmentOperatorStatement.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            getComponents().getDataFlowAnalyzer().enterCallArguments(assignmentOperatorStatement, CollectionsKt.listOf(assignmentOperatorStatement.getRightArgument()));
            FirExpression firExpression = (FirExpression) FirTransformerUtilKt.transformSingle(assignmentOperatorStatement.getLeftArgument(), getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            FirExpression firExpression2 = (FirExpression) FirTransformerUtilKt.transformSingle(assignmentOperatorStatement.getRightArgument(), getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
            getComponents().getDataFlowAnalyzer().exitCallArguments();
            FirAssignmentOperatorStatement firAssignmentOperatorStatement = assignmentOperatorStatement;
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(assignmentOperatorStatement);
            GeneratorOfPlusAssignCalls generatorOfPlusAssignCalls = new GeneratorOfPlusAssignCalls(firAssignmentOperatorStatement, calleeReference != null ? calleeReference.getSource() : null, operation, firExpression, firExpression2);
            FirFunctionCall firFunctionCallCreateAssignOperatorCall = generatorOfPlusAssignCalls.createAssignOperatorCall();
            boolean z2 = true;
            this.resolvingAugmentedAssignment = true;
            try {
                BodyResolveContext context = getTransformer().getContext();
                InferenceSessionForAssignmentOperatorCall inferenceSessionForAssignmentOperatorCall = InferenceSessionForAssignmentOperatorCall.INSTANCE;
                FirInferenceSession inferenceSession = context.getInferenceSession();
                context.setInferenceSession(inferenceSessionForAssignmentOperatorCall);
                try {
                    FirFunctionCall firFunctionCall = (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallCreateAssignOperatorCall, this, ResolutionMode.ContextDependent.INSTANCE);
                    context.setInferenceSession(inferenceSession);
                    this.resolvingAugmentedAssignment = false;
                    FirNamedReference calleeReference2 = firFunctionCall.getCalleeReference();
                    FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference2 instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference2 : null;
                    boolean z3 = (firNamedReferenceWithCandidate == null || firNamedReferenceWithCandidate.isError()) ? false : true;
                    FirFunctionCall firFunctionCallCreateSimpleOperatorCall = generatorOfPlusAssignCalls.createSimpleOperatorCall();
                    this.resolvingAugmentedAssignment = true;
                    try {
                        BodyResolveContext context2 = getTransformer().getContext();
                        InferenceSessionForAssignmentOperatorCall inferenceSessionForAssignmentOperatorCall2 = InferenceSessionForAssignmentOperatorCall.INSTANCE;
                        FirInferenceSession inferenceSession2 = context2.getInferenceSession();
                        context2.setInferenceSession(inferenceSessionForAssignmentOperatorCall2);
                        try {
                            FirFunctionCall firFunctionCall2 = (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallCreateSimpleOperatorCall, this, ResolutionMode.ContextDependent.INSTANCE);
                            context2.setInferenceSession(inferenceSession2);
                            this.resolvingAugmentedAssignment = false;
                            FirNamedReference calleeReference3 = firFunctionCall2.getCalleeReference();
                            FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2 = calleeReference3 instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference3 : null;
                            boolean z4 = (firNamedReferenceWithCandidate2 == null || firNamedReferenceWithCandidate2.isError()) ? false : true;
                            if (z4) {
                                Intrinsics.checkNotNull(firNamedReferenceWithCandidate2);
                                if (transformAssignmentOperatorStatement$lambda$35$operatorReturnTypeMatches(firFunctionCall2, this, firExpression, firNamedReferenceWithCandidate2.getCandidate())) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            } else {
                                z = false;
                            }
                            FirReference reference = FirExpressionUtilKt.toReference(firExpression);
                            FirVariableSymbol resolvedVariableSymbol$default = reference != null ? FirReferenceUtilsKt.toResolvedVariableSymbol$default(reference, false, 1, null) : null;
                            FirVariable firVariable = resolvedVariableSymbol$default != null ? (FirVariable) resolvedVariableSymbol$default.getFir() : null;
                            if (firVariable == null || !firVariable.getIsVar()) {
                                z2 = false;
                            }
                            if (z3 && !z2) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseAssign(this, firFunctionCall);
                            }
                            if (!z3 && !z4) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseResolved(firNamedReferenceWithCandidate, firNamedReferenceWithCandidate2, this, firFunctionCall, firFunctionCall2, firVariable, firExpression, assignmentOperatorStatement);
                            }
                            if (!z3 && z4) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseOperator(this, firFunctionCall2, firVariable, firExpression, assignmentOperatorStatement);
                            }
                            if (z3 && !z4) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseAssign(this, firFunctionCall);
                            }
                            if (FirTypeUtilsKt.getConeType(firExpression.getTypeRef()) instanceof ConeDynamicType) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseAssign(this, firFunctionCall);
                            }
                            if (!z) {
                                return transformAssignmentOperatorStatement$lambda$35$chooseAssign(this, firFunctionCall);
                            }
                            return transformAssignmentOperatorStatement$lambda$35$reportAmbiguity(firNamedReferenceWithCandidate2, firNamedReferenceWithCandidate, assignmentOperatorStatement);
                        } catch (Throwable th) {
                            context2.setInferenceSession(inferenceSession2);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        this.resolvingAugmentedAssignment = false;
                        throw th2;
                    }
                } catch (Throwable th3) {
                    context.setInferenceSession(inferenceSession);
                    throw th3;
                }
            } catch (Throwable th4) {
                this.resolvingAugmentedAssignment = false;
                throw th4;
            }
        } catch (Throwable th5) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(assignmentOperatorStatement, th5);
            throw null;
        }
    }

    private static final boolean transformAssignmentOperatorStatement$lambda$35$operatorReturnTypeMatches(FirFunctionCall firFunctionCall, FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirExpression firExpression, Candidate candidate) {
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firFunctionCall.getTypeRef());
        TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default = InferenceUtilsKt.buildAbstractResultingSubstitutor$default(candidate.getSystem().currentStorage(), candidate.getSystem().getTypeSystemContext(), false, 2, null);
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(firExpressionsResolveTransformer.getSession()), (KotlinTypeMarker) ((ConeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default).substituteOrSelf(coneType), (KotlinTypeMarker) FirTypeUtilsKt.getConeType(firExpression.getTypeRef()), false, 8, (Object) null);
    }

    private static final FirStatement transformAssignmentOperatorStatement$lambda$35$chooseAssign(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
        firExpressionsResolveTransformer2.getComponents().getCallCompleter().completeCall(firFunctionCall, ResolutionMode.ContextIndependent.INSTANCE);
        firExpressionsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitFunctionCall(firFunctionCall, true);
        return firFunctionCall;
    }

    private static final FirStatement transformAssignmentOperatorStatement$lambda$35$chooseOperator(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, FirVariable firVariable, FirExpression firExpression, FirAssignmentOperatorStatement firAssignmentOperatorStatement) {
        ResolutionMode.ContextIndependent withExpectedType;
        FirVariableAssignment firVariableAssignmentMo6809build;
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
        FirCallCompleter callCompleter = firExpressionsResolveTransformer2.getComponents().getCallCompleter();
        FirFunctionCall firFunctionCall2 = firFunctionCall;
        FirTypeRef returnTypeRef = firVariable != null ? firVariable.getReturnTypeRef() : null;
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        if (firResolvedTypeRef != null) {
            withExpectedType = new ResolutionMode.WithExpectedType(firResolvedTypeRef, false, true, false, false, false, 58, null);
        } else {
            withExpectedType = ResolutionMode.ContextIndependent.INSTANCE;
        }
        callCompleter.completeCall(firFunctionCall2, withExpectedType);
        firExpressionsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitFunctionCall(firFunctionCall, true);
        KtSourceElement source = firExpression.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null;
        FirExpression firExpressionUnwrapSmartcastExpression = FirExpressionUtilKt.unwrapSmartcastExpression(firExpression);
        FirDesugaredAssignmentValueReferenceExpressionBuilder firDesugaredAssignmentValueReferenceExpressionBuilder = new FirDesugaredAssignmentValueReferenceExpressionBuilder();
        FirExpressionRef<FirExpression> firExpressionRef = new FirExpressionRef<>();
        firExpressionRef.bind(firExpressionUnwrapSmartcastExpression);
        firDesugaredAssignmentValueReferenceExpressionBuilder.setExpressionRef(firExpressionRef);
        firDesugaredAssignmentValueReferenceExpressionBuilder.setSource(ktSourceElementFakeElement$default);
        FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpressionMo6809build = firDesugaredAssignmentValueReferenceExpressionBuilder.mo6809build();
        FirVariableAssignmentBuilder firVariableAssignmentBuilder = new FirVariableAssignmentBuilder();
        firVariableAssignmentBuilder.setSource(firAssignmentOperatorStatement.getSource());
        firVariableAssignmentBuilder.setLValue(firDesugaredAssignmentValueReferenceExpressionMo6809build);
        firVariableAssignmentBuilder.setRValue(firFunctionCall);
        CollectionsKt.addAll(firVariableAssignmentBuilder.getAnnotations(), firAssignmentOperatorStatement.getAnnotations());
        FirVariableAssignment firVariableAssignmentMo6809build2 = firVariableAssignmentBuilder.mo6809build();
        FirProperty firPropertyGenerateExplicitReceiverTemporaryVariable = FirGenerationKt.generateExplicitReceiverTemporaryVariable(firExpressionsResolveTransformer.getSession(), firExpressionUnwrapSmartcastExpression, ktSourceElementFakeElement$default);
        if (firPropertyGenerateExplicitReceiverTemporaryVariable == null) {
            firVariableAssignmentMo6809build = firVariableAssignmentMo6809build2;
        } else {
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            KtSourceElement source2 = firAssignmentOperatorStatement.getSource();
            firBlockBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null);
            CollectionsKt.addAll(firBlockBuilder.getAnnotations(), firAssignmentOperatorStatement.getAnnotations());
            firBlockBuilder.getStatements().add(firPropertyGenerateExplicitReceiverTemporaryVariable);
            firBlockBuilder.getStatements().add(firVariableAssignmentMo6809build2);
            firVariableAssignmentMo6809build = firBlockBuilder.mo6809build();
        }
        return (FirStatement) firVariableAssignmentMo6809build.transform(firExpressionsResolveTransformer.getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
    }

    private static final FirStatement transformAssignmentOperatorStatement$lambda$35$chooseResolved(FirNamedReferenceWithCandidate firNamedReferenceWithCandidate, FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2, FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, FirFunctionCall firFunctionCall2, FirVariable firVariable, FirExpression firExpression, FirAssignmentOperatorStatement firAssignmentOperatorStatement) {
        FirErrorReferenceWithCandidate firErrorReferenceWithCandidate = firNamedReferenceWithCandidate instanceof FirErrorReferenceWithCandidate ? (FirErrorReferenceWithCandidate) firNamedReferenceWithCandidate : null;
        boolean z = (firErrorReferenceWithCandidate != null ? firErrorReferenceWithCandidate.getDiagnostic() : null) instanceof ConeUnresolvedNameError;
        FirErrorReferenceWithCandidate firErrorReferenceWithCandidate2 = firNamedReferenceWithCandidate2 instanceof FirErrorReferenceWithCandidate ? (FirErrorReferenceWithCandidate) firNamedReferenceWithCandidate2 : null;
        boolean z2 = (firErrorReferenceWithCandidate2 != null ? firErrorReferenceWithCandidate2.getDiagnostic() : null) instanceof ConeUnresolvedNameError;
        if (!z) {
            return transformAssignmentOperatorStatement$lambda$35$chooseAssign(firExpressionsResolveTransformer, firFunctionCall);
        }
        if (!z2) {
            return transformAssignmentOperatorStatement$lambda$35$chooseOperator(firExpressionsResolveTransformer, firFunctionCall2, firVariable, firExpression, firAssignmentOperatorStatement);
        }
        return transformAssignmentOperatorStatement$lambda$35$chooseAssign(firExpressionsResolveTransformer, firFunctionCall);
    }

    private static final FirStatement transformAssignmentOperatorStatement$lambda$35$reportAmbiguity(FirNamedReferenceWithCandidate firNamedReferenceWithCandidate, FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2, FirAssignmentOperatorStatement firAssignmentOperatorStatement) {
        Candidate candidate = firNamedReferenceWithCandidate != null ? firNamedReferenceWithCandidate.getCandidate() : null;
        if (candidate == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        Candidate candidate2 = firNamedReferenceWithCandidate2 != null ? firNamedReferenceWithCandidate2.getCandidate() : null;
        if (candidate2 == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(firAssignmentOperatorStatement.getSource());
        firErrorExpressionBuilder.setDiagnostic(new ConeOperatorAmbiguityError(CollectionsKt.listOf((Object[]) new Candidate[]{candidate, candidate2})));
        return firErrorExpressionBuilder.mo6809build();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformIncrementDecrementExpression(FirIncrementDecrementExpression incrementDecrementExpression, ResolutionMode data) {
        FirErrorExpression firErrorExpressionMo6809build;
        FirExpression expression;
        Intrinsics.checkNotNullParameter(incrementDecrementExpression, "incrementDecrementExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        incrementDecrementExpression.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        FirExpression firExpression = (FirExpression) FirTransformerUtilKt.transformSingle(incrementDecrementExpression.getExpression(), getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        boolean z = firExpression instanceof FirSafeCallExpression;
        if (z) {
            FirStatement selector = ((FirSafeCallExpression) firExpression).getSelector();
            firErrorExpressionMo6809build = selector instanceof FirExpression ? (FirExpression) selector : null;
            if (firErrorExpressionMo6809build == null) {
                FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                firErrorExpressionBuilder.setSource(firExpression.getSource());
                firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("Safe call selector expected to be an expression here"));
                firErrorExpressionMo6809build = firErrorExpressionBuilder.mo6809build();
            }
        } else {
            firErrorExpressionMo6809build = firExpression;
        }
        KtSourceElement source = incrementDecrementExpression.getSource();
        KtSourceElement ktSourceElementFakeElement$default = source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null;
        FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
        firBlockBuilder.setSource(ktSourceElementFakeElement$default);
        CollectionsKt.addAll(firBlockBuilder.getAnnotations(), incrementDecrementExpression.getAnnotations());
        FirProperty firPropertyGenerateExplicitReceiverTemporaryVariable = FirGenerationKt.generateExplicitReceiverTemporaryVariable(getSession(), firErrorExpressionMo6809build, ktSourceElementFakeElement$default);
        if (firPropertyGenerateExplicitReceiverTemporaryVariable != null) {
            firBlockBuilder.getStatements().add(firPropertyGenerateExplicitReceiverTemporaryVariable);
        }
        if (incrementDecrementExpression.getIsPrefix()) {
            firBlockBuilder.getStatements().add(transformIncrementDecrementExpression$buildAndResolveVariableAssignment(this, ktSourceElementFakeElement$default, firErrorExpressionMo6809build, transformIncrementDecrementExpression$buildAndResolveOperatorCall(this, incrementDecrementExpression, firErrorExpressionMo6809build)));
            List<FirStatement> statements = firBlockBuilder.getStatements();
            FirDesugaredAssignmentValueReferenceExpressionBuilder firDesugaredAssignmentValueReferenceExpressionBuilder = new FirDesugaredAssignmentValueReferenceExpressionBuilder();
            FirErrorExpression firErrorExpression = firErrorExpressionMo6809build instanceof FirErrorExpression ? (FirErrorExpression) firErrorExpressionMo6809build : null;
            if (firErrorExpression == null || (expression = firErrorExpression.getExpression()) == null) {
                expression = firErrorExpressionMo6809build;
            }
            KtSourceElement source2 = expression.getSource();
            firDesugaredAssignmentValueReferenceExpressionBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null);
            FirExpressionRef<FirExpression> firExpressionRef = new FirExpressionRef<>();
            firExpressionRef.bind(FirExpressionUtilKt.unwrapSmartcastExpression(firErrorExpressionMo6809build));
            firDesugaredAssignmentValueReferenceExpressionBuilder.setExpressionRef(firExpressionRef);
            FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpressionMo6809build = firDesugaredAssignmentValueReferenceExpressionBuilder.mo6809build();
            firDesugaredAssignmentValueReferenceExpressionMo6809build.transform(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            statements.add(ResolveUtilsKt.transformDesugaredAssignmentValueUsingSmartcastInfo(getTransformer().getComponents(), firDesugaredAssignmentValueReferenceExpressionMo6809build));
        } else {
            FirProperty firPropertyTransformIncrementDecrementExpression$generateTemporaryVariable = transformIncrementDecrementExpression$generateTemporaryVariable(this, ktSourceElementFakeElement$default, SpecialNames.UNARY, firErrorExpressionMo6809build);
            firBlockBuilder.getStatements().add(firPropertyTransformIncrementDecrementExpression$generateTemporaryVariable);
            FirProperty firProperty = firPropertyTransformIncrementDecrementExpression$generateTemporaryVariable;
            firBlockBuilder.getStatements().add(transformIncrementDecrementExpression$buildAndResolveVariableAssignment(this, ktSourceElementFakeElement$default, firErrorExpressionMo6809build, transformIncrementDecrementExpression$buildAndResolveOperatorCall(this, incrementDecrementExpression, FirGenerationKt.toQualifiedAccess$default(firProperty, null, null, 3, null))));
            firBlockBuilder.getStatements().add(FirGenerationKt.toQualifiedAccess$default(firProperty, null, null, 3, null));
        }
        FirBlock firBlockMo6809build = firBlockBuilder.mo6809build();
        Object objLast = CollectionsKt.last((List<? extends Object>) firBlockMo6809build.getStatements());
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
        firBlockMo6809build.replaceTypeRef(UtilsKt.copyWithNewSource(((FirExpression) objLast).getTypeRef(), null));
        if (z) {
            ((FirSafeCallExpression) firExpression).replaceSelector(firBlockMo6809build);
            return firExpression;
        }
        return firBlockMo6809build;
    }

    private static final FirProperty transformIncrementDecrementExpression$generateTemporaryVariable(FirExpressionsResolveTransformer firExpressionsResolveTransformer, KtSourceElement ktSourceElement, Name name2, FirExpression firExpression) {
        return FirGenerationKt.generateTemporaryVariable$default(FirModuleDataKt.getModuleData(firExpressionsResolveTransformer.getSession()), ktSourceElement, name2, firExpression, UtilsKt.copyWithNewSource(firExpression.getTypeRef(), ktSourceElement), null, 32, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformEqualityOperatorCall(FirEqualityOperatorCall equalityOperatorCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(equalityOperatorCall, "equalityOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            List<FirExpression> arguments = equalityOperatorCall.getArgumentList().getArguments();
            if (arguments.size() != 2) {
                throw new IllegalArgumentException(("Unexpected number of arguments in equality call: " + arguments.size()).toString());
            }
            equalityOperatorCall.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE).replaceArgumentList(FirArgumentUtilKt.buildBinaryArgumentList((FirExpression) arguments.get(0).transform(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE), (FirExpression) arguments.get(1).transform(getTransformer(), ResolutionModeKt.withExpectedType$default((FirTypeRef) getSession().getBuiltinTypes().getNullableAnyType(), false, 2, (Object) null))));
            equalityOperatorCall.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(equalityOperatorCall.getTypeRef(), getSession().getBuiltinTypes().getBooleanType().getType(), null, 2, null));
            getComponents().getDataFlowAnalyzer().exitEqualityOperatorCall(equalityOperatorCall);
            return equalityOperatorCall;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(equalityOperatorCall, th);
            throw null;
        }
    }

    /* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\u00020\u0004\"\f\b\u0000\u0010\u0005*\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u0002H\u0005H\u0016¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer$InferenceSessionForAssignmentOperatorCall;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirStubInferenceSession;", "()V", "shouldRunCompletion", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "call", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;)Z", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InferenceSessionForAssignmentOperatorCall extends FirStubInferenceSession {
        public static final InferenceSessionForAssignmentOperatorCall INSTANCE = new InferenceSessionForAssignmentOperatorCall();

        @Override // org.jetbrains.kotlin.fir.resolve.inference.FirStubInferenceSession, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
        public <T extends FirStatement & FirResolvable> boolean shouldRunCompletion(T call) {
            Intrinsics.checkNotNullParameter(call, "call");
            return false;
        }

        private InferenceSessionForAssignmentOperatorCall() {
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeOperatorCall(FirTypeOperatorCall typeOperatorCall, ResolutionMode data) {
        FirTypeOperatorCall firTypeOperatorCallTransformConversionTypeRef;
        Intrinsics.checkNotNullParameter(typeOperatorCall, "typeOperatorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSpecificTypeResolverTransformer typeResolverTransformer = getTransformer().getComponents().getTypeResolverTransformer();
        boolean areBareTypesAllowed = typeResolverTransformer.getAreBareTypesAllowed();
        typeResolverTransformer.setAreBareTypesAllowed(true);
        try {
            if (typeOperatorCall.getOperation() == FirOperation.IS || typeOperatorCall.getOperation() == FirOperation.NOT_IS) {
                FirSpecificTypeResolverTransformer typeResolverTransformer2 = getTransformer().getComponents().getTypeResolverTransformer();
                boolean isOperandOfIsOperator = typeResolverTransformer2.getIsOperandOfIsOperator();
                typeResolverTransformer2.setOperandOfIsOperator(true);
                try {
                    FirTypeOperatorCall firTypeOperatorCallTransformConversionTypeRef2 = typeOperatorCall.transformConversionTypeRef(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                    typeResolverTransformer2.setOperandOfIsOperator(isOperandOfIsOperator);
                    firTypeOperatorCallTransformConversionTypeRef = firTypeOperatorCallTransformConversionTypeRef2;
                } catch (Throwable th) {
                    typeResolverTransformer2.setOperandOfIsOperator(isOperandOfIsOperator);
                    throw th;
                }
            } else {
                firTypeOperatorCallTransformConversionTypeRef = typeOperatorCall.transformConversionTypeRef(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            }
            typeResolverTransformer.setAreBareTypesAllowed(areBareTypesAllowed);
            final FirTypeOperatorCall firTypeOperatorCallTransformTypeOperatorCallChildren = transformTypeOperatorCallChildren(firTypeOperatorCallTransformConversionTypeRef);
            final FirTypeRef firTypeRefWithTypeArgumentsForBareType = withTypeArgumentsForBareType(firTypeOperatorCallTransformTypeOperatorCallChildren.getConversionTypeRef(), (FirExpression) CollectionsKt.first((List) firTypeOperatorCallTransformTypeOperatorCallChildren.getArgumentList().getArguments()), typeOperatorCall.getOperation());
            firTypeOperatorCallTransformTypeOperatorCallChildren.transformChildren(new FirDefaultTransformer<Object>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer.transformTypeOperatorCall.1
                @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
                public <E extends FirElement> E transformElement(E element, Object data2) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    return element;
                }

                @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
                public FirTypeRef transformTypeRef(FirTypeRef typeRef, Object data2) {
                    Intrinsics.checkNotNullParameter(typeRef, "typeRef");
                    return typeRef == firTypeOperatorCallTransformTypeOperatorCallChildren.getConversionTypeRef() ? firTypeRefWithTypeArgumentsForBareType : typeRef;
                }
            }, null);
            int i = WhenMappings.$EnumSwitchMapping$0[firTypeOperatorCallTransformTypeOperatorCallChildren.getOperation().ordinal()];
            if (i == 1 || i == 2) {
                firTypeOperatorCallTransformTypeOperatorCallChildren.replaceTypeRef(getSession().getBuiltinTypes().getBooleanType());
            } else if (i == 3) {
                FirTypeOperatorCall firTypeOperatorCall = firTypeOperatorCallTransformTypeOperatorCallChildren;
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                KtSourceElement source = firTypeRefWithTypeArgumentsForBareType.getSource();
                firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null);
                firResolvedTypeRefBuilder.setType(FirTypeUtilsKt.getConeType(firTypeRefWithTypeArgumentsForBareType));
                CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), firTypeRefWithTypeArgumentsForBareType.getAnnotations());
                firTypeOperatorCall.replaceTypeRef(firResolvedTypeRefBuilder.mo6809build());
            } else if (i == 4) {
                firTypeOperatorCallTransformTypeOperatorCallChildren.replaceTypeRef(TypeUtilsKt.withReplacedConeType$default(firTypeRefWithTypeArgumentsForBareType, TypeUtilsKt.withNullability$default(FirTypeUtilsKt.getConeType(firTypeRefWithTypeArgumentsForBareType), ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(getSession()), null, false, 12, null), null, 2, null));
            } else {
                throw new IllegalStateException(("Unknown type operator: " + firTypeOperatorCallTransformTypeOperatorCallChildren.getOperation()).toString());
            }
            getComponents().getDataFlowAnalyzer().exitTypeOperatorCall(firTypeOperatorCallTransformTypeOperatorCallChildren);
            return firTypeOperatorCallTransformTypeOperatorCallChildren;
        } catch (Throwable th2) {
            typeResolverTransformer.setAreBareTypesAllowed(areBareTypesAllowed);
            throw th2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0091  */
    /* JADX WARN: Code duplicated, block: B:52:0x00b3  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirTypeOperatorCall transformTypeOperatorCallChildren(FirTypeOperatorCall firTypeOperatorCall) {
        ConeKotlinType coneKotlinTypeWithNullability$default;
        List<FirTypeParameterRef> typeParameters;
        if (firTypeOperatorCall.getOperation() == FirOperation.AS || firTypeOperatorCall.getOperation() == FirOperation.SAFE_AS) {
            FirExpression firExpression = (FirExpression) CollectionsKt.singleOrNull((List) firTypeOperatorCall.getArgumentList().getArguments());
            if (firExpression == null) {
                throw new IllegalStateException(("Not a single argument: " + UtilsKt.render(firTypeOperatorCall)).toString());
            }
            if ((firExpression instanceof FirFunctionCall) || ((firExpression instanceof FirSafeCallExpression) && (((FirSafeCallExpression) firExpression).getSelector() instanceof FirFunctionCall))) {
                FirTypeRef conversionTypeRef = firTypeOperatorCall.getConversionTypeRef();
                FirResolvedTypeRef firResolvedTypeRef = conversionTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) conversionTypeRef : null;
                ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                if (!(type instanceof ConeKotlinType)) {
                    type = null;
                }
                if (type == null) {
                    coneKotlinTypeWithNullability$default = null;
                } else {
                    if (type instanceof ConeClassLikeType) {
                        if (type.getTypeArguments().length == 0) {
                            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) type).getLookupTag(), getSession());
                            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                            FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
                            if (firClassLikeDeclaration2 == null || (typeParameters = firClassLikeDeclaration2.getTypeParameters()) == null || !typeParameters.isEmpty()) {
                                coneKotlinTypeWithNullability$default = null;
                            } else {
                                coneKotlinTypeWithNullability$default = type;
                            }
                        } else {
                            coneKotlinTypeWithNullability$default = type;
                        }
                    } else {
                        coneKotlinTypeWithNullability$default = type;
                    }
                    if (coneKotlinTypeWithNullability$default == null) {
                        coneKotlinTypeWithNullability$default = null;
                    } else if (firTypeOperatorCall.getOperation() == FirOperation.SAFE_AS) {
                        coneKotlinTypeWithNullability$default = TypeUtilsKt.withNullability$default(coneKotlinTypeWithNullability$default, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(getSession()), null, false, 12, null);
                    }
                }
                if (coneKotlinTypeWithNullability$default != null) {
                    return firTypeOperatorCall.transformOtherChildren(getTransformer(), new ResolutionMode.WithExpectedType(TypeUtilsKt.withReplacedConeType$default(firTypeOperatorCall.getConversionTypeRef(), coneKotlinTypeWithNullability$default, null, 2, null), false, false, true, false, false, 54, null));
                }
            }
        }
        return firTypeOperatorCall.transformOtherChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCheckNotNullCall(FirCheckNotNullCall checkNotNullCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(checkNotNullCall, "checkNotNullCall");
        Intrinsics.checkNotNullParameter(data, "data");
        if ((checkNotNullCall.getCalleeReference() instanceof FirResolvedNamedReference) && !(checkNotNullCall.getTypeRef() instanceof FirImplicitTypeRef)) {
            return checkNotNullCall;
        }
        FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
        firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().enterCheckNotNullCall();
        checkNotNullCall.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE).replaceArgumentList((FirArgumentList) checkNotNullCall.getArgumentList().transform(getTransformer(), ResolutionMode.ContextDependent.INSTANCE));
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = this;
        FirCheckNotNullCall firCheckNotNullCall = (FirCheckNotNullCall) firExpressionsResolveTransformer.getComponents().getCallCompleter().completeCall(firExpressionsResolveTransformer2.getTransformer().getComponents().getSyntheticCallGenerator().generateCalleeForCheckNotNullCall(checkNotNullCall, firExpressionsResolveTransformer2.getTransformer().getResolutionContext()), data);
        firExpressionsResolveTransformer.getComponents().getDataFlowAnalyzer().exitCheckNotNullCall(firCheckNotNullCall, data.getForceFullCompletion());
        return firCheckNotNullCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBinaryLogicExpression(FirBinaryLogicExpression binaryLogicExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(binaryLogicExpression, "binaryLogicExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(binaryLogicExpression.getTypeRef(), getSession().getBuiltinTypes().getBooleanType().getType(), null, 2, null);
            getComponents().getDataFlowAnalyzer().enterBinaryLogicExpression(binaryLogicExpression);
            FirBinaryLogicExpression firBinaryLogicExpressionTransformLeftOperand = binaryLogicExpression.transformLeftOperand(this, new ResolutionMode.WithExpectedType(firResolvedTypeRefResolvedTypeFromPrototype$default, false, false, false, false, false, 62, null));
            getComponents().getDataFlowAnalyzer().exitLeftBinaryLogicExpressionArgument(firBinaryLogicExpressionTransformLeftOperand);
            collectStatementContext(firBinaryLogicExpressionTransformLeftOperand.getRightOperand());
            FirBinaryLogicExpression firBinaryLogicExpressionTransformRightOperand = firBinaryLogicExpressionTransformLeftOperand.transformRightOperand(this, new ResolutionMode.WithExpectedType(firResolvedTypeRefResolvedTypeFromPrototype$default, false, false, false, false, false, 62, null));
            getComponents().getDataFlowAnalyzer().exitBinaryLogicExpression(firBinaryLogicExpressionTransformRightOperand);
            FirBinaryLogicExpression firBinaryLogicExpressionTransformOtherChildren = firBinaryLogicExpressionTransformRightOperand.transformOtherChildren(getTransformer(), new ResolutionMode.WithExpectedType(firResolvedTypeRefResolvedTypeFromPrototype$default, false, false, false, false, false, 62, null));
            firBinaryLogicExpressionTransformOtherChildren.replaceTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
            return firBinaryLogicExpressionTransformOtherChildren;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(binaryLogicExpression, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDesugaredAssignmentValueReferenceExpression(FirDesugaredAssignmentValueReferenceExpression desugaredAssignmentValueReferenceExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(desugaredAssignmentValueReferenceExpression, "desugaredAssignmentValueReferenceExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirExpression value = desugaredAssignmentValueReferenceExpression.getExpressionRef().getValue();
        if (value instanceof FirQualifiedAccessExpression) {
            FirResolvedTypeRef firResolvedTypeRefTypeFromCallee = ResolveUtilsKt.typeFromCallee(getTransformer().getComponents(), (FirResolvable) value);
            desugaredAssignmentValueReferenceExpression.replaceTypeRef(TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRefTypeFromCallee, TypeComponentsKt.getTypeApproximator(getSession()).approximateToSubType(firResolvedTypeRefTypeFromCallee.getType(), (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE), null, 2, null));
        } else {
            desugaredAssignmentValueReferenceExpression.replaceTypeRef(UtilsKt.copyWithNewSourceKind(value.getTypeRef(), KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE));
        }
        return desugaredAssignmentValueReferenceExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformVariableAssignment(FirVariableAssignment variableAssignment, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(variableAssignment, "variableAssignment");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            variableAssignment.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            variableAssignment.transformLValue(getTransformer(), new ResolutionMode.AssignmentLValue(variableAssignment));
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(variableAssignment);
            List<FirAssignExpressionAltererExtension> list = this.assignAltererExtensions;
            if (list != null && (calleeReference instanceof FirResolvedNamedReference)) {
                ArrayList arrayList = new ArrayList();
                for (FirAssignExpressionAltererExtension firAssignExpressionAltererExtension : list) {
                    FirStatement firStatementTransformVariableAssignment = firAssignExpressionAltererExtension.transformVariableAssignment(variableAssignment);
                    Pair pair = firStatementTransformVariableAssignment != null ? TuplesKt.to(firStatementTransformVariableAssignment, firAssignExpressionAltererExtension) : null;
                    if (pair != null) {
                        arrayList.add(pair);
                    }
                }
                ArrayList arrayList2 = arrayList;
                int size = arrayList2.size();
                if (size != 0) {
                    if (size == 1) {
                        return (FirStatement) ((FirStatement) ((Pair) CollectionsKt.first((List) arrayList2)).getFirst()).transform(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                    }
                    ArrayList arrayList3 = arrayList2;
                    ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                    Iterator it = arrayList3.iterator();
                    while (it.hasNext()) {
                        arrayList4.add(Reflection.getOrCreateKotlinClass(((Pair) it.next()).getSecond().getClass()).getQualifiedName());
                    }
                    ArrayList arrayList5 = arrayList4;
                    FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
                    firErrorExpressionBuilder.setExpression(variableAssignment.getLValue());
                    KtSourceElement source = variableAssignment.getLValue().getSource();
                    firErrorExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.AssignmentLValueError.INSTANCE, 0, 0, 6, null) : null);
                    firErrorExpressionBuilder.setDiagnostic(new ConeAmbiguousAlteredAssign(arrayList5));
                    variableAssignment.replaceLValue(firErrorExpressionBuilder.mo6809build());
                }
            }
            FirVariableAssignment firVariableAssignmentTransformRValue = variableAssignment.transformRValue(getTransformer(), ResolutionModeKt.withExpectedType(variableAssignment.getLValue().getTypeRef(), true));
            FirVariableAssignment firVariableAssignment = firVariableAssignmentTransformRValue instanceof FirVariableAssignment ? firVariableAssignmentTransformRValue : null;
            if (firVariableAssignment != null) {
                getComponents().getDataFlowAnalyzer().exitVariableAssignment(firVariableAssignment);
            }
            return firVariableAssignmentTransformRValue;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(variableAssignment, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformCallableReferenceAccess(FirCallableReferenceAccess callableReferenceAccess, ResolutionMode data) {
        FirExpression firExpression;
        FirCallableReferenceAccess firCallableReferenceAccessResolveCallableReferenceWithSyntheticOuterCall;
        Intrinsics.checkNotNullParameter(callableReferenceAccess, "callableReferenceAccess");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (callableReferenceAccess.getCalleeReference() instanceof FirResolvedNamedReference) {
                return callableReferenceAccess;
            }
            callableReferenceAccess.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            FirExpression explicitReceiver = callableReferenceAccess.getExplicitReceiver();
            if (explicitReceiver instanceof FirPropertyAccessExpression) {
                FirStatement firStatementTransformQualifiedAccessExpression = transformQualifiedAccessExpression((FirQualifiedAccessExpression) explicitReceiver, ResolutionMode.ContextIndependent.INSTANCE, true);
                Intrinsics.checkNotNull(firStatementTransformQualifiedAccessExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                firExpression = (FirExpression) firStatementTransformQualifiedAccessExpression;
            } else {
                firExpression = explicitReceiver != null ? (FirExpression) FirTransformerUtilKt.transformSingle(explicitReceiver, this, ResolutionMode.ContextIndependent.INSTANCE) : null;
            }
            if ((firExpression instanceof FirResolvedQualifier) && callableReferenceAccess.getHasQuestionMarkAtLHS()) {
                ((FirResolvedQualifier) firExpression).replaceIsNullableLHSForCallableReference(true);
            }
            if (firExpression != null) {
                callableReferenceAccess.replaceExplicitReceiver(firExpression);
            }
            if ((data instanceof ResolutionMode.ContextDependent) && !(data instanceof ResolutionMode.ContextDependent.Delegate)) {
                getTransformer().getContext().storeCallableReferenceContext(callableReferenceAccess);
                firCallableReferenceAccessResolveCallableReferenceWithSyntheticOuterCall = callableReferenceAccess;
            } else {
                firCallableReferenceAccessResolveCallableReferenceWithSyntheticOuterCall = getTransformer().getComponents().getSyntheticCallGenerator().resolveCallableReferenceWithSyntheticOuterCall(callableReferenceAccess, getExpectedType(data), getTransformer().getResolutionContext());
            }
            getComponents().getDataFlowAnalyzer().exitCallableReference(firCallableReferenceAccessResolveCallableReferenceWithSyntheticOuterCall);
            return firCallableReferenceAccessResolveCallableReferenceWithSyntheticOuterCall;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(callableReferenceAccess, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformGetClassCall(FirGetClassCall getClassCall, ResolutionMode data) {
        ResolutionMode.ContextIndependent contextIndependentWithExpectedType$default;
        final FirExpression firExpression;
        ConeKotlinTypeProjectionOut coneKotlinTypeProjectionOut;
        ConeTypeProjection[] coneTypeProjectionArr;
        List<FirTypeParameterRef> typeParameters;
        ConeClassLikeType coneType;
        Intrinsics.checkNotNullParameter(getClassCall, "getClassCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            getClassCall.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            FirExpression argument = getClassCall.getArgument();
            if (argument instanceof FirConstExpression) {
                contextIndependentWithExpectedType$default = ResolutionModeKt.withExpectedType$default((FirTypeRef) CopyUtilsKt.resolvedTypeFromPrototype$default(argument.getTypeRef(), BodyResolveUtilsKt.expectedConeType(((FirConstExpression) argument).getKind(), getSession()), null, 2, null), false, 2, (Object) null);
            } else {
                contextIndependentWithExpectedType$default = ResolutionMode.ContextIndependent.INSTANCE;
            }
            FirExpressionsResolveTransformer firExpressionsResolveTransformer = this;
            FirExpression argument2 = getClassCall.getArgument();
            if (argument2 instanceof FirPropertyAccessExpression) {
                FirStatement firStatementTransformQualifiedAccessExpression = transformQualifiedAccessExpression((FirQualifiedAccessExpression) argument2, contextIndependentWithExpectedType$default, true);
                Intrinsics.checkNotNull(firStatementTransformQualifiedAccessExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirExpression");
                firExpression = (FirExpression) firStatementTransformQualifiedAccessExpression;
            } else {
                firExpression = (FirExpression) argument2.transform(this, contextIndependentWithExpectedType$default);
            }
            getClassCall.getArgumentList().transformArguments(new FirTransformer() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer$transformGetClassCall$1$transformedGetClassCall$1$1
                @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
                public <E extends FirElement> E transformElement(E element, Void data2) {
                    Intrinsics.checkNotNullParameter(element, "element");
                    FirExpression firExpression2 = firExpression;
                    Intrinsics.checkNotNull(firExpression2, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer.transformGetClassCall$lambda$77$lambda$72.<no name provided>.transformElement");
                    return firExpression2;
                }
            }, null);
            FirExpression argument3 = getClassCall.getArgument();
            if (argument3 instanceof FirResolvedQualifier) {
                ((FirResolvedQualifier) argument3).replaceResolvedToCompanionObject(false);
                FirClassLikeSymbol<?> symbol = ((FirResolvedQualifier) argument3).getSymbol();
                if (!((FirResolvedQualifier) argument3).getTypeArguments().isEmpty()) {
                    List<FirTypeProjection> typeArguments = ((FirResolvedQualifier) argument3).getTypeArguments();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeArguments, 10));
                    Iterator<T> it = typeArguments.iterator();
                    while (it.hasNext()) {
                        arrayList.add(FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) it.next()));
                    }
                    coneTypeProjectionArr = (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]);
                } else {
                    FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
                    FirClassLikeDeclaration firClassLikeDeclaration2 = firClassLikeDeclaration instanceof FirTypeParameterRefsOwner ? firClassLikeDeclaration : null;
                    int size = (firClassLikeDeclaration2 == null || (typeParameters = firClassLikeDeclaration2.getTypeParameters()) == null) ? 0 : typeParameters.size();
                    ConeTypeProjection[] coneTypeProjectionArr2 = new ConeTypeProjection[size];
                    for (int i = 0; i < size; i++) {
                        coneTypeProjectionArr2[i] = ConeStarProjection.INSTANCE;
                    }
                    coneTypeProjectionArr = coneTypeProjectionArr2;
                }
                ConeClassLikeType coneClassLikeTypeConstructType$default = symbol != null ? TypeConstructionUtilsKt.constructType$default((FirClassLikeSymbol) symbol, coneTypeProjectionArr, false, (ConeAttributes) null, 4, (Object) null) : null;
                if (coneClassLikeTypeConstructType$default == null) {
                    coneType = FirTypeUtilsKt.getConeType(argument3.getTypeRef());
                } else {
                    FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                    firResolvedTypeRefBuilder.setType(coneClassLikeTypeConstructType$default);
                    FirResolvedTypeRef firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
                    FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
                    if (lookupTracker != null) {
                        FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefMo6809build, getClassCall.getSource(), getTransformer().getComponents().getFile().getSource());
                    }
                    argument3.replaceTypeRef(firResolvedTypeRefMo6809build);
                    coneType = coneClassLikeTypeConstructType$default;
                }
                coneKotlinTypeProjectionOut = coneType;
            } else if (argument3 instanceof FirResolvedReifiedParameterReference) {
                coneKotlinTypeProjectionOut = TypeConstructionUtilsKt.constructType$default((FirClassifierSymbol) ((FirResolvedReifiedParameterReference) argument3).getSymbol(), new ConeTypeProjection[0], false, (ConeAttributes) null, 4, (Object) null);
            } else {
                if (!shouldComputeTypeOfGetClassCallWithNotQualifierInLhs(getClassCall)) {
                    return getClassCall;
                }
                FirTypeRef typeRef = argument3.getTypeRef();
                if (typeRef instanceof FirErrorTypeRef) {
                    coneKotlinTypeProjectionOut = FirTypeUtilsKt.getConeType(typeRef);
                } else {
                    coneKotlinTypeProjectionOut = new ConeKotlinTypeProjectionOut(FirTypeUtilsKt.getConeType(typeRef));
                }
            }
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder2 = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder2.setType(TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getKClass(), new ConeKotlinTypeProjection[]{coneKotlinTypeProjectionOut}, false, null, 4, null));
            getClassCall.replaceTypeRef(firResolvedTypeRefBuilder2.mo6809build());
            getComponents().getDataFlowAnalyzer().exitGetClassCall(getClassCall);
            return getClassCall;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(getClassCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <T> FirStatement transformConstExpression(FirConstExpression<T> constExpression, ResolutionMode data) {
        ConeSimpleKotlinType coneSimpleKotlinTypeExpectedConeType;
        Intrinsics.checkNotNullParameter(constExpression, "constExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        constExpression.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        ConstantValueKind<T> kind = constExpression.getKind();
        if (Intrinsics.areEqual(kind, ConstantValueKind.IntegerLiteral.INSTANCE) ? true : Intrinsics.areEqual(kind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE)) {
            ConeIntegerLiteralConstantTypeImpl.Companion companion = ConeIntegerLiteralConstantTypeImpl.INSTANCE;
            Object value = constExpression.getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Long");
            ConeClassLikeType coneClassLikeTypeCreate$default = ConeIntegerLiteralConstantTypeImpl.Companion.create$default(companion, ((Long) value).longValue(), Intrinsics.areEqual(kind, ConstantValueKind.UnsignedIntegerLiteral.INSTANCE), new Function1<ConeClassLikeType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer$transformConstExpression$type$expressionType$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(ConeClassLikeType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(LookupTagUtilsKt.toSymbol(it.getLookupTag(), this.this$0.getSession()) != null);
                }
            }, null, 8, null);
            FirTypeRef expectedType = getExpectedType(data);
            if (!(coneClassLikeTypeCreate$default instanceof ConeErrorType)) {
                if (coneClassLikeTypeCreate$default instanceof ConeClassLikeType) {
                    ConstantValueKind<?> constKind = FirTypeUtilsKt.toConstKind((ConeClassLikeType) coneClassLikeTypeCreate$default);
                    Intrinsics.checkNotNull(constKind, "null cannot be cast to non-null type org.jetbrains.kotlin.types.ConstantValueKind<T of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer.transformConstExpression>");
                    constExpression.replaceKind(constKind);
                } else if (data instanceof ResolutionMode.ReceiverResolution) {
                    if (!(coneClassLikeTypeCreate$default instanceof ConeIntegerLiteralConstantTypeImpl)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    coneClassLikeTypeCreate$default = new ConeIntegerConstantOperatorTypeImpl(((ConeIntegerLiteralConstantTypeImpl) coneClassLikeTypeCreate$default).getIsUnsigned(), ConeNullability.NOT_NULL);
                } else if (expectedType != null) {
                    if (!(coneClassLikeTypeCreate$default instanceof ConeIntegerLiteralConstantTypeImpl)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    FirResolvedTypeRef firResolvedTypeRef = expectedType instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expectedType : null;
                    ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    if (!(type instanceof ConeKotlinType)) {
                        type = null;
                    }
                    ConeClassLikeType approximatedType = ((ConeIntegerLiteralConstantTypeImpl) coneClassLikeTypeCreate$default).getApproximatedType(type != null ? TypeExpansionUtilsKt.fullyExpandedType(type, getSession()) : null);
                    ConstantValueKind<?> constKind2 = FirTypeUtilsKt.toConstKind(approximatedType);
                    Intrinsics.checkNotNull(constKind2, "null cannot be cast to non-null type org.jetbrains.kotlin.types.ConstantValueKind<T of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer.transformConstExpression>");
                    constExpression.replaceKind(constKind2);
                    coneClassLikeTypeCreate$default = approximatedType;
                }
            }
            coneSimpleKotlinTypeExpectedConeType = coneClassLikeTypeCreate$default;
        } else {
            coneSimpleKotlinTypeExpectedConeType = BodyResolveUtilsKt.expectedConeType(kind, getSession());
        }
        getComponents().getDataFlowAnalyzer().exitConstExpression(constExpression);
        FirConstExpression<T> firConstExpression = constExpression;
        firConstExpression.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firConstExpression.getTypeRef(), coneSimpleKotlinTypeExpectedConeType, null, 2, null));
        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(firConstExpression.getTypeRef());
        if (!(coneType instanceof ConeErrorType)) {
            return constExpression;
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setExpression(firConstExpression);
        firErrorExpressionBuilder.setDiagnostic(((ConeErrorType) coneType).getDiagnostic());
        firErrorExpressionBuilder.setSource(constExpression.getSource());
        return firErrorExpressionBuilder.mo6809build();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        Intrinsics.checkNotNullParameter(data, "data");
        if (FirAnnotationUtilsKt.getResolved(annotation)) {
            return annotation;
        }
        annotation.transformAnnotationTypeRef(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        return annotation;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (FirAnnotationUtilsKt.getResolved(annotationCall)) {
                return annotationCall;
            }
            annotationCall.transformAnnotationTypeRef((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types);
            BodyResolveContext context = getTransformer().getContext();
            if (context.getContainerIfAny() instanceof FirRegularClass) {
                FirTowerDataMode firTowerDataMode = FirTowerDataMode.CLASS_HEADER_ANNOTATIONS;
                FirTowerDataMode towerDataMode = context.getTowerDataMode();
                try {
                    context.setTowerDataMode(firTowerDataMode);
                    setEnableArrayOfCallTransformation(true);
                    try {
                        getComponents().getDataFlowAnalyzer().enterAnnotation();
                        FirAnnotationCall firAnnotationCallResolveAnnotationCall = getComponents().getCallResolver().resolveAnnotationCall(annotationCall);
                        getComponents().getDataFlowAnalyzer().exitAnnotation();
                        if (firAnnotationCallResolveAnnotationCall == null) {
                            FirAnnotationCall firAnnotationCall = annotationCall;
                            setEnableArrayOfCallTransformation(false);
                            context.setTowerDataMode(towerDataMode);
                            return firAnnotationCall;
                        }
                        getComponents().getCallCompleter().completeCall(firAnnotationCallResolveAnnotationCall, ResolutionMode.ContextIndependent.INSTANCE);
                        FirArgumentList argumentList = firAnnotationCallResolveAnnotationCall.getArgumentList();
                        Intrinsics.checkNotNull(argumentList, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList");
                        annotationCall.replaceArgumentMapping(FirAnnotationArgumentMappingImplKt.toAnnotationArgumentMapping((FirResolvedArgumentList) argumentList));
                        setEnableArrayOfCallTransformation(false);
                        context.setTowerDataMode(towerDataMode);
                    } catch (Throwable th) {
                        setEnableArrayOfCallTransformation(false);
                        throw th;
                    }
                } catch (Throwable th2) {
                    context.setTowerDataMode(towerDataMode);
                    throw th2;
                }
            } else {
                setEnableArrayOfCallTransformation(true);
                try {
                    getComponents().getDataFlowAnalyzer().enterAnnotation();
                    FirAnnotationCall firAnnotationCallResolveAnnotationCall2 = getComponents().getCallResolver().resolveAnnotationCall(annotationCall);
                    getComponents().getDataFlowAnalyzer().exitAnnotation();
                    if (firAnnotationCallResolveAnnotationCall2 == null) {
                        return annotationCall;
                    }
                    getComponents().getCallCompleter().completeCall(firAnnotationCallResolveAnnotationCall2, ResolutionMode.ContextIndependent.INSTANCE);
                    FirArgumentList argumentList2 = firAnnotationCallResolveAnnotationCall2.getArgumentList();
                    Intrinsics.checkNotNull(argumentList2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList");
                    annotationCall.replaceArgumentMapping(FirAnnotationArgumentMappingImplKt.toAnnotationArgumentMapping((FirResolvedArgumentList) argumentList2));
                } finally {
                    setEnableArrayOfCallTransformation(false);
                }
            }
            return annotationCall;
        } catch (Throwable th3) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(annotationCall, th3);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformErrorAnnotationCall(FirErrorAnnotationCall errorAnnotationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(errorAnnotationCall, "errorAnnotationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        return transformAnnotationCall((FirAnnotationCall) errorAnnotationCall, data);
    }

    protected final <T> T withFirArrayOfCallTransformer(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        setEnableArrayOfCallTransformation(true);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setEnableArrayOfCallTransformation(false);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:97:0x021a  */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ResolutionMode data) {
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default;
        ConeClassLikeLookupTag lookupTag;
        FirClassLikeSymbol<?> symbol;
        Object next;
        boolean z;
        FirRegularClass firRegularClassExtractSuperTypeDeclaration;
        Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (getTransformer().getImplicitTypeOnly()) {
                return delegatedConstructorCall;
            }
            FirReference calleeReference = delegatedConstructorCall.getCalleeReference();
            if (calleeReference instanceof FirResolvedNamedReference ? true : calleeReference instanceof FirErrorNamedReference) {
                return delegatedConstructorCall;
            }
            ArrayDeque<FirDeclaration> containers = getTransformer().getComponents().getContext().getContainers();
            FirDeclaration firDeclaration = containers.get(CollectionsKt.getLastIndex(containers) - 1);
            Intrinsics.checkNotNull(firDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirClass");
            FirClass firClass = (FirClass) firDeclaration;
            FirDeclaration firDeclarationLast = containers.last();
            Intrinsics.checkNotNull(firDeclarationLast, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirConstructor");
            FirConstructor firConstructor = (FirConstructor) firDeclarationLast;
            if (delegatedConstructorCall.isSuper() && (delegatedConstructorCall.getConstructedTypeRef() instanceof FirImplicitTypeRef)) {
                Iterator<T> it = firClass.getSuperTypeRefs().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    FirTypeRef firTypeRef = (FirTypeRef) next;
                    z = false;
                    if ((firTypeRef instanceof FirResolvedTypeRef) && (firRegularClassExtractSuperTypeDeclaration = extractSuperTypeDeclaration(firTypeRef)) != null) {
                        boolean z2 = firRegularClassExtractSuperTypeDeclaration.getStatus().isExternal() && isCallToDelegatedConstructorWithoutArguments(delegatedConstructorCall);
                        if (firRegularClassExtractSuperTypeDeclaration.getClassKind() == ClassKind.CLASS && !z2) {
                            z = true;
                        }
                    }
                } while (!z);
                FirImplicitBuiltinTypeRef anyType = (FirResolvedTypeRef) next;
                if (anyType == null) {
                    anyType = getSession().getBuiltinTypes().getAnyType();
                }
                delegatedConstructorCall.replaceConstructedTypeRef(anyType);
                FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
                firExplicitSuperReferenceBuilder.setSource(delegatedConstructorCall.getCalleeReference().getSource());
                firExplicitSuperReferenceBuilder.setSuperTypeRef(anyType);
                delegatedConstructorCall.replaceCalleeReference(firExplicitSuperReferenceBuilder.build());
            }
            getComponents().getDataFlowAnalyzer().enterCallArguments(delegatedConstructorCall, delegatedConstructorCall.getArgumentList().getArguments());
            ImplicitDispatchReceiverValue implicitDispatchReceiverValueLastDispatchReceiver = getComponents().getImplicitReceiverStack().lastDispatchReceiver();
            BodyResolveContext context = getTransformer().getContext();
            FirRegularClass firRegularClass = firClass instanceof FirRegularClass ? (FirRegularClass) firClass : null;
            FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = getTransformer().getComponents();
            FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
            FirTowerDataMode towerDataMode = context.getTowerDataMode();
            try {
                context.setTowerDataMode(firTowerDataMode);
                if (firConstructor.getIsPrimary()) {
                    FirLocalScope primaryConstructorAllParametersScope = context.getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope == null) {
                        delegatedConstructorCall.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
                    } else {
                        FirTowerDataContext towerDataContext = context.getTowerDataContext();
                        try {
                            context.addLocalScope(primaryConstructorAllParametersScope);
                            FirElement firElementTransformChildren = delegatedConstructorCall.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
                            context.replaceTowerDataContext(towerDataContext);
                            if (firElementTransformChildren == null) {
                                delegatedConstructorCall.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
                            }
                        } catch (Throwable th) {
                            context.replaceTowerDataContext(towerDataContext);
                            throw th;
                        }
                    }
                } else {
                    context.addInaccessibleImplicitReceiverValue(firRegularClass, components);
                    FirTowerDataContext towerDataContext2 = context.getTowerDataContext();
                    try {
                        context.addLocalScope(context.buildSecondaryConstructorParametersScope(firConstructor, components.getSession()));
                        Iterator<T> it2 = firConstructor.getValueParameters().iterator();
                        while (it2.hasNext()) {
                            context.storeVariable((FirValueParameter) it2.next(), components.getSession());
                        }
                        delegatedConstructorCall.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
                        context.replaceTowerDataContext(towerDataContext2);
                    } catch (Throwable th2) {
                        context.replaceTowerDataContext(towerDataContext2);
                        throw th2;
                    }
                }
                context.setTowerDataMode(towerDataMode);
                getComponents().getDataFlowAnalyzer().exitCallArguments();
                FirReference calleeReference2 = delegatedConstructorCall.getCalleeReference();
                if (calleeReference2 instanceof FirThisReference) {
                    ConeKotlinType type = implicitDispatchReceiverValueLastDispatchReceiver != null ? implicitDispatchReceiverValueLastDispatchReceiver.getType() : null;
                    if (type instanceof ConeClassLikeType) {
                        coneClassLikeTypeFullyExpandedType$default = (ConeClassLikeType) type;
                    } else {
                        coneClassLikeTypeFullyExpandedType$default = null;
                    }
                } else if (calleeReference2 instanceof FirSuperReference) {
                    FirTypeRef superTypeRef = ((FirSuperReference) calleeReference2).getSuperTypeRef();
                    FirResolvedTypeRef firResolvedTypeRef = superTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) superTypeRef : null;
                    ConeKotlinType type2 = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    if (!(type2 instanceof ConeClassLikeType)) {
                        type2 = null;
                    }
                    ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type2;
                    if (coneClassLikeType == null) {
                        coneClassLikeTypeFullyExpandedType$default = null;
                    } else {
                        if (coneClassLikeType instanceof ConeErrorType) {
                            coneClassLikeType = null;
                        }
                        if (coneClassLikeType != null) {
                            coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, getSession(), null, 2, null);
                        } else {
                            coneClassLikeTypeFullyExpandedType$default = null;
                        }
                    }
                } else {
                    coneClassLikeTypeFullyExpandedType$default = null;
                }
                FirDelegatedConstructorCall firDelegatedConstructorCallResolveDelegatingConstructorCall = getComponents().getCallResolver().resolveDelegatingConstructorCall(delegatedConstructorCall, coneClassLikeTypeFullyExpandedType$default, firClass.getSymbol().getLookupTag());
                if ((calleeReference2 instanceof FirThisReference) && ((FirThisReference) calleeReference2).getBoundSymbol() == null) {
                    FirTypeRef typeRef = firDelegatedConstructorCallResolveDelegatingConstructorCall.getDispatchReceiver().getTypeRef();
                    FirResolvedTypeRef firResolvedTypeRef2 = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
                    ConeKotlinType type3 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
                    if (!(type3 instanceof ConeClassLikeType)) {
                        type3 = null;
                    }
                    ConeClassLikeType coneClassLikeType2 = (ConeClassLikeType) type3;
                    if (coneClassLikeType2 != null && (lookupTag = coneClassLikeType2.getLookupTag()) != null && (symbol = LookupTagUtilsKt.toSymbol(lookupTag, getSession())) != null) {
                        ((FirThisReference) calleeReference2).replaceBoundSymbol(symbol);
                    }
                }
                FirDelegatedConstructorCall firDelegatedConstructorCall = (FirDelegatedConstructorCall) getComponents().getCallCompleter().completeCall(firDelegatedConstructorCallResolveDelegatingConstructorCall, ResolutionMode.ContextIndependent.INSTANCE);
                getComponents().getDataFlowAnalyzer().exitDelegatedConstructorCall(firDelegatedConstructorCall, data.getForceFullCompletion());
                return firDelegatedConstructorCall;
            } catch (Throwable th3) {
                context.setTowerDataMode(towerDataMode);
                throw th3;
            }
        } catch (Throwable th4) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(delegatedConstructorCall, th4);
            throw null;
        }
    }

    private final boolean isCallToDelegatedConstructorWithoutArguments(FirDelegatedConstructorCall firDelegatedConstructorCall) {
        KtSourceElement source = firDelegatedConstructorCall.getSource();
        return Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE);
    }

    private final FirRegularClass extractSuperTypeDeclaration(FirTypeRef typeRef) {
        if (!(typeRef instanceof FirResolvedTypeRef)) {
            return null;
        }
        FirClassLikeDeclaration firClassLikeDeclarationFirClassLike = DeclarationUtilsKt.firClassLike(typeRef, getSession());
        if (firClassLikeDeclarationFirClassLike instanceof FirRegularClass) {
            return (FirRegularClass) firClassLikeDeclarationFirClassLike;
        }
        if (firClassLikeDeclarationFirClassLike instanceof FirTypeAlias) {
            return extractSuperTypeDeclaration(((FirTypeAlias) firClassLikeDeclarationFirClassLike).getExpandedTypeRef());
        }
        return null;
    }

    /* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0015\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer$GeneratorOfPlusAssignCalls;", "", "baseElement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "referenceSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "lhs", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "rhs", "(Lorg/jetbrains/kotlin/fir/expressions/FirStatement;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirOperation;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)V", "getBaseElement", "()Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "getLhs", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "getReferenceSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getRhs", "createAssignOperatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "createFunctionCall", "name", "Lorg/jetbrains/kotlin/name/Name;", "createSimpleOperatorCall", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class GeneratorOfPlusAssignCalls {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final FirStatement baseElement;
        private final FirExpression lhs;
        private final FirOperation operation;
        private final KtSourceElement referenceSource;
        private final FirExpression rhs;

        public GeneratorOfPlusAssignCalls(FirStatement baseElement, KtSourceElement ktSourceElement, FirOperation operation, FirExpression lhs, FirExpression rhs) {
            Intrinsics.checkNotNullParameter(baseElement, "baseElement");
            Intrinsics.checkNotNullParameter(operation, "operation");
            Intrinsics.checkNotNullParameter(lhs, "lhs");
            Intrinsics.checkNotNullParameter(rhs, "rhs");
            this.baseElement = baseElement;
            this.referenceSource = ktSourceElement;
            this.operation = operation;
            this.lhs = lhs;
            this.rhs = rhs;
        }

        public final FirStatement getBaseElement() {
            return this.baseElement;
        }

        public final KtSourceElement getReferenceSource() {
            return this.referenceSource;
        }

        public final FirOperation getOperation() {
            return this.operation;
        }

        public final FirExpression getLhs() {
            return this.lhs;
        }

        public final FirExpression getRhs() {
            return this.rhs;
        }

        /* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JC\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000b0\r\"\u00020\u000b¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer$GeneratorOfPlusAssignCalls$Companion;", "", "()V", "createFunctionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "referenceSource", "receiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "arguments", "", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;[Lorg/jetbrains/kotlin/fir/expressions/FirExpression;)Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final FirFunctionCall createFunctionCall(Name name2, KtSourceElement source, KtSourceElement referenceSource, FirExpression receiver, FirExpression... arguments) {
                FirEmptyArgumentList firEmptyArgumentListBuildUnaryArgumentList;
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(receiver, "receiver");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
                firFunctionCallBuilder.setSource(source);
                firFunctionCallBuilder.setExplicitReceiver(receiver);
                int length = arguments.length;
                if (length == 0) {
                    firEmptyArgumentListBuildUnaryArgumentList = FirEmptyArgumentList.INSTANCE;
                } else if (length == 1) {
                    firEmptyArgumentListBuildUnaryArgumentList = FirArgumentUtilKt.buildUnaryArgumentList((FirExpression) ArraysKt.first(arguments));
                } else {
                    FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
                    CollectionsKt.addAll(firArgumentListBuilder.getArguments(), arguments);
                    firEmptyArgumentListBuildUnaryArgumentList = firArgumentListBuilder.build();
                }
                firFunctionCallBuilder.setArgumentList(firEmptyArgumentListBuildUnaryArgumentList);
                FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
                if (referenceSource != null) {
                    source = referenceSource;
                }
                firSimpleNamedReferenceBuilder.setSource(source);
                firSimpleNamedReferenceBuilder.setName(name2);
                firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
                firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
                return firFunctionCallBuilder.mo6809build();
            }
        }

        private final FirFunctionCall createFunctionCall(Name name2) {
            Companion companion = INSTANCE;
            KtSourceElement source = this.baseElement.getSource();
            return companion.createFunctionCall(name2, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null, this.referenceSource, this.lhs, this.rhs);
        }

        public final FirFunctionCall createAssignOperatorCall() {
            return createFunctionCall((Name) MapsKt.getValue(FirOperationNameConventions.INSTANCE.getASSIGNMENTS(), this.operation));
        }

        public final FirFunctionCall createSimpleOperatorCall() {
            return createFunctionCall((Name) MapsKt.getValue(FirOperationNameConventions.INSTANCE.getASSIGNMENTS_TO_SIMPLE_OPERATOR(), this.operation));
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAugmentedArraySetCall(FirAugmentedArraySetCall augmentedArraySetCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirOperation operation = augmentedArraySetCall.getOperation();
            FirOperation.INSTANCE.getASSIGNMENTS().contains(operation);
            FirOperation firOperation = FirOperation.ASSIGN;
            augmentedArraySetCall.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            getComponents().getDataFlowAnalyzer().enterCallArguments(augmentedArraySetCall, CollectionsKt.listOf(augmentedArraySetCall.getRhs()));
            FirFunctionCall firFunctionCall = (FirFunctionCall) FirTransformerUtilKt.transformSingle(augmentedArraySetCall.getLhsGetCall(), getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            FirExpression firExpression = (FirExpression) FirTransformerUtilKt.transformSingle(augmentedArraySetCall.getRhs(), getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
            getComponents().getDataFlowAnalyzer().exitCallArguments();
            FirFunctionCall firFunctionCallCreateAssignOperatorCall = new GeneratorOfPlusAssignCalls(augmentedArraySetCall, augmentedArraySetCall.getCalleeReference().getSource(), operation, firFunctionCall, firExpression).createAssignOperatorCall();
            boolean z = true;
            this.resolvingAugmentedAssignment = true;
            try {
                BodyResolveContext context = getTransformer().getContext();
                InferenceSessionForAssignmentOperatorCall inferenceSessionForAssignmentOperatorCall = InferenceSessionForAssignmentOperatorCall.INSTANCE;
                FirInferenceSession inferenceSession = context.getInferenceSession();
                context.setInferenceSession(inferenceSessionForAssignmentOperatorCall);
                try {
                    FirFunctionCall firFunctionCall2 = (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallCreateAssignOperatorCall, this, ResolutionMode.ContextDependent.INSTANCE);
                    context.setInferenceSession(inferenceSession);
                    this.resolvingAugmentedAssignment = false;
                    FirNamedReference calleeReference = firFunctionCall2.getCalleeReference();
                    FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
                    boolean z2 = (firNamedReferenceWithCandidate == null || firNamedReferenceWithCandidate.isError()) ? false : true;
                    FirBasedSymbol resolvedBaseSymbol$default = FirReferenceUtilsKt.toResolvedBaseSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null);
                    if (Intrinsics.areEqual(resolvedBaseSymbol$default != null ? resolvedBaseSymbol$default.getOrigin() : null, FirDeclarationOrigin.DynamicScope.INSTANCE)) {
                        return transformAugmentedArraySetCall$lambda$94$chooseAssign$90(this, firFunctionCall2);
                    }
                    AugmentedArraySetAsGetSetCallDesugaringInfo augmentedArraySetAsGetSetCallDesugaringInfoTryResolveAugmentedArraySetCallAsSetGetBlock = tryResolveAugmentedArraySetCallAsSetGetBlock(augmentedArraySetCall, firFunctionCall, firExpression);
                    FirNamedReference calleeReference2 = augmentedArraySetAsGetSetCallDesugaringInfoTryResolveAugmentedArraySetCallAsSetGetBlock.getOperatorCall().getCalleeReference();
                    FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2 = calleeReference2 instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference2 : null;
                    boolean z3 = (firNamedReferenceWithCandidate2 == null || firNamedReferenceWithCandidate2.isError()) ? false : true;
                    if (z2 && !z3) {
                        return transformAugmentedArraySetCall$lambda$94$chooseAssign$90(this, firFunctionCall2);
                    }
                    FirFunctionCall setCall = augmentedArraySetAsGetSetCallDesugaringInfoTryResolveAugmentedArraySetCallAsSetGetBlock.getSetCall();
                    FirNamedReference calleeReference3 = setCall.getCalleeReference();
                    FirNamedReferenceWithCandidate firNamedReferenceWithCandidate3 = calleeReference3 instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference3 : null;
                    if (firNamedReferenceWithCandidate3 == null || firNamedReferenceWithCandidate3.isError()) {
                        z = false;
                    }
                    if (z2 && z) {
                        return transformAugmentedArraySetCall$lambda$94$reportAmbiguity$93(this, firFunctionCall2, augmentedArraySetCall, firNamedReferenceWithCandidate, firNamedReferenceWithCandidate3);
                    }
                    if (z2) {
                        return transformAugmentedArraySetCall$lambda$94$chooseAssign$90(this, firFunctionCall2);
                    }
                    if (z) {
                        return transformAugmentedArraySetCall$lambda$94$chooseSetOperator(this, setCall, augmentedArraySetAsGetSetCallDesugaringInfoTryResolveAugmentedArraySetCallAsSetGetBlock);
                    }
                    return transformAugmentedArraySetCall$lambda$94$reportUnresolvedReference(operation, this, firFunctionCall2, augmentedArraySetCall);
                } catch (Throwable th) {
                    context.setInferenceSession(inferenceSession);
                    throw th;
                }
            } catch (Throwable th2) {
                this.resolvingAugmentedAssignment = false;
                throw th2;
            }
        } catch (Throwable th3) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(augmentedArraySetCall, th3);
            throw null;
        }
    }

    private static final FirFunctionCall transformAugmentedArraySetCall$lambda$94$chooseAssign$90(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
        firExpressionsResolveTransformer2.getComponents().getCallCompleter().completeCall(firFunctionCall, ResolutionMode.ContextIndependent.INSTANCE);
        firExpressionsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitFunctionCall(firFunctionCall, true);
        return firFunctionCall;
    }

    private static final FirStatement transformAugmentedArraySetCall$lambda$94$chooseSetOperator(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, AugmentedArraySetAsGetSetCallDesugaringInfo augmentedArraySetAsGetSetCallDesugaringInfo) {
        FirExpressionsResolveTransformer firExpressionsResolveTransformer2 = firExpressionsResolveTransformer;
        firExpressionsResolveTransformer2.getComponents().getCallCompleter().completeCall(firFunctionCall, ResolutionMode.ContextIndependent.INSTANCE);
        firExpressionsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitFunctionCall(firFunctionCall, true);
        return augmentedArraySetAsGetSetCallDesugaringInfo.toBlock();
    }

    private static final FirStatement transformAugmentedArraySetCall$lambda$94$reportError(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, FirAugmentedArraySetCall firAugmentedArraySetCall, ConeDiagnostic coneDiagnostic) {
        FirFunctionCall firFunctionCallTransformAugmentedArraySetCall$lambda$94$chooseAssign$90 = transformAugmentedArraySetCall$lambda$94$chooseAssign$90(firExpressionsResolveTransformer, firFunctionCall);
        FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
        firErrorNamedReferenceBuilder.setSource(firAugmentedArraySetCall.getSource());
        firErrorNamedReferenceBuilder.setDiagnostic(coneDiagnostic);
        firFunctionCallTransformAugmentedArraySetCall$lambda$94$chooseAssign$90.replaceCalleeReference((FirNamedReference) firErrorNamedReferenceBuilder.build());
        return firFunctionCallTransformAugmentedArraySetCall$lambda$94$chooseAssign$90;
    }

    private static final FirStatement transformAugmentedArraySetCall$lambda$94$reportAmbiguity$93(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, FirAugmentedArraySetCall firAugmentedArraySetCall, FirNamedReferenceWithCandidate firNamedReferenceWithCandidate, FirNamedReferenceWithCandidate firNamedReferenceWithCandidate2) {
        Candidate candidate = firNamedReferenceWithCandidate != null ? firNamedReferenceWithCandidate.getCandidate() : null;
        Candidate candidate2 = firNamedReferenceWithCandidate2 != null ? firNamedReferenceWithCandidate2.getCandidate() : null;
        if (candidate == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        if (candidate2 == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        return transformAugmentedArraySetCall$lambda$94$reportError(firExpressionsResolveTransformer, firFunctionCall, firAugmentedArraySetCall, new ConeOperatorAmbiguityError(CollectionsKt.listOf((Object[]) new Candidate[]{candidate, candidate2})));
    }

    private static final FirStatement transformAugmentedArraySetCall$lambda$94$reportUnresolvedReference(FirOperation firOperation, FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirFunctionCall firFunctionCall, FirAugmentedArraySetCall firAugmentedArraySetCall) {
        Name nameIdentifier = Name.identifier(firOperation.getOperator());
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return transformAugmentedArraySetCall$lambda$94$reportError(firExpressionsResolveTransformer, firFunctionCall, firAugmentedArraySetCall, new ConeUnresolvedNameError(nameIdentifier));
    }

    /* JADX INFO: compiled from: FirExpressionsResolveTransformer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u0015\u001a\u00020\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer$AugmentedArraySetAsGetSetCallDesugaringInfo;", "", "augmentedArraySetCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "arrayVariable", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "indexVariables", "", "operatorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "setCall", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/util/List;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)V", "getArrayVariable", "()Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getAugmentedArraySetCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirAugmentedArraySetCall;", "getIndexVariables", "()Ljava/util/List;", "getOperatorCall", "()Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "getSetCall", "toBlock", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AugmentedArraySetAsGetSetCallDesugaringInfo {
        private final FirProperty arrayVariable;
        private final FirAugmentedArraySetCall augmentedArraySetCall;
        private final List<FirProperty> indexVariables;
        private final FirFunctionCall operatorCall;
        private final FirFunctionCall setCall;
        final /* synthetic */ FirExpressionsResolveTransformer this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public AugmentedArraySetAsGetSetCallDesugaringInfo(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirAugmentedArraySetCall augmentedArraySetCall, FirProperty arrayVariable, List<? extends FirProperty> indexVariables, FirFunctionCall operatorCall, FirFunctionCall setCall) {
            Intrinsics.checkNotNullParameter(augmentedArraySetCall, "augmentedArraySetCall");
            Intrinsics.checkNotNullParameter(arrayVariable, "arrayVariable");
            Intrinsics.checkNotNullParameter(indexVariables, "indexVariables");
            Intrinsics.checkNotNullParameter(operatorCall, "operatorCall");
            Intrinsics.checkNotNullParameter(setCall, "setCall");
            this.this$0 = firExpressionsResolveTransformer;
            this.augmentedArraySetCall = augmentedArraySetCall;
            this.arrayVariable = arrayVariable;
            this.indexVariables = indexVariables;
            this.operatorCall = operatorCall;
            this.setCall = setCall;
        }

        public final FirAugmentedArraySetCall getAugmentedArraySetCall() {
            return this.augmentedArraySetCall;
        }

        public final FirProperty getArrayVariable() {
            return this.arrayVariable;
        }

        public final List<FirProperty> getIndexVariables() {
            return this.indexVariables;
        }

        public final FirFunctionCall getOperatorCall() {
            return this.operatorCall;
        }

        public final FirFunctionCall getSetCall() {
            return this.setCall;
        }

        public final FirBlock toBlock() {
            FirBlockBuilder firBlockBuilder = new FirBlockBuilder();
            CollectionsKt.addAll(firBlockBuilder.getAnnotations(), this.augmentedArraySetCall.getAnnotations());
            firBlockBuilder.getStatements().add(this.arrayVariable);
            CollectionsKt.addAll(firBlockBuilder.getStatements(), this.indexVariables);
            firBlockBuilder.getStatements().add(this.setCall);
            FirBlock firBlockMo6809build = firBlockBuilder.mo6809build();
            FirExpressionsResolveTransformer firExpressionsResolveTransformer = this.this$0;
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(firExpressionsResolveTransformer.getSession().getBuiltinTypes().getUnitType().getType());
            firBlockMo6809build.replaceTypeRef(firResolvedTypeRefBuilder.mo6809build());
            return firBlockMo6809build;
        }
    }

    private final AugmentedArraySetAsGetSetCallDesugaringInfo tryResolveAugmentedArraySetCallAsSetGetBlock(FirAugmentedArraySetCall augmentedArraySetCall, FirFunctionCall lhsGetCall, FirExpression transformedRhs) {
        FirVarargArgumentsExpression firVarargArgumentsExpressionMo6809build;
        int size;
        List<FirExpression> listListOf;
        KtSourceElement source;
        FirModuleData moduleData = FirModuleDataKt.getModuleData(getSession());
        FirExpression explicitReceiver = lhsGetCall.getExplicitReceiver();
        KtSourceElement ktSourceElementFakeElement$default = (explicitReceiver == null || (source = explicitReceiver.getSource()) == null) ? null : KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null);
        Name name2 = SpecialNames.ARRAY;
        FirErrorExpression explicitReceiver2 = lhsGetCall.getExplicitReceiver();
        if (explicitReceiver2 == null) {
            FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
            KtSourceElement source2 = augmentedArraySetCall.getSource();
            firErrorExpressionBuilder.setSource(source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null);
            firErrorExpressionBuilder.setDiagnostic(new ConeSyntaxDiagnostic("No receiver for array access"));
            Unit unit = Unit.INSTANCE;
            explicitReceiver2 = firErrorExpressionBuilder.mo6809build();
        }
        FirProperty firPropertyGenerateTemporaryVariable$default = FirGenerationKt.generateTemporaryVariable$default(moduleData, ktSourceElementFakeElement$default, name2, explicitReceiver2, null, null, 48, null);
        List<FirExpression> arguments = lhsGetCall.getArgumentList().getArguments();
        ArrayList arrayList = new ArrayList();
        for (FirExpression firExpression : arguments) {
            if (firExpression instanceof FirVarargArgumentsExpression) {
                listListOf = ((FirVarargArgumentsExpression) firExpression).getArguments();
            } else {
                listListOf = CollectionsKt.listOf(firExpression);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        int i = 0;
        for (Object obj : arrayList2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirExpression firExpression2 = (FirExpression) obj;
            FirModuleData moduleData2 = FirModuleDataKt.getModuleData(getSession());
            KtSourceElement source3 = firExpression2.getSource();
            arrayList3.add(FirGenerationKt.generateTemporaryVariable$default(moduleData2, source3 != null ? KtSourceElementKt.fakeElement$default(source3, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null, SpecialNames.subscribeOperatorIndex(i), firExpression2, firExpression2.getTypeRef(), null, 32, null));
            i = i2;
        }
        ArrayList arrayList4 = arrayList3;
        FirTransformerUtilKt.transformSingle(firPropertyGenerateTemporaryVariable$default, getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        ArrayList arrayList5 = arrayList4;
        Iterator it = arrayList5.iterator();
        while (it.hasNext()) {
            FirTransformerUtilKt.transformSingle((FirProperty) it.next(), getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        }
        FirQualifiedAccessExpression qualifiedAccess$default = FirGenerationKt.toQualifiedAccess$default(firPropertyGenerateTemporaryVariable$default, null, null, 3, null);
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            arrayList6.add(FirGenerationKt.toQualifiedAccess$default((FirProperty) it2.next(), null, null, 3, null));
        }
        ArrayList arrayList7 = arrayList6;
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        KtSourceElement arrayAccessSource = augmentedArraySetCall.getArrayAccessSource();
        firFunctionCallBuilder.setSource(arrayAccessSource != null ? KtSourceElementKt.fakeElement$default(arrayAccessSource, KtFakeSourceElementKind.DesugaredCompoundAssignment.INSTANCE, 0, 0, 6, null) : null);
        FirQualifiedAccessExpression firQualifiedAccessExpression = qualifiedAccess$default;
        firFunctionCallBuilder.setExplicitReceiver(firQualifiedAccessExpression);
        if (Intrinsics.areEqual(lhsGetCall.getExplicitReceiver(), lhsGetCall.getDispatchReceiver())) {
            firFunctionCallBuilder.setDispatchReceiver(firQualifiedAccessExpression);
            firFunctionCallBuilder.setExtensionReceiver(lhsGetCall.getExtensionReceiver());
        } else {
            firFunctionCallBuilder.setExtensionReceiver(firQualifiedAccessExpression);
            firFunctionCallBuilder.setDispatchReceiver(lhsGetCall.getDispatchReceiver());
        }
        firFunctionCallBuilder.setCalleeReference(lhsGetCall.getCalleeReference());
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        int i3 = 0;
        for (FirExpression firExpression3 : lhsGetCall.getArgumentList().getArguments()) {
            List<FirExpression> arguments2 = firArgumentListBuilder.getArguments();
            if (!(firExpression3 instanceof FirVarargArgumentsExpression)) {
                int i4 = i3 + 1;
                firVarargArgumentsExpressionMo6809build = (FirExpression) arrayList7.get(i3);
                size = i4;
            } else {
                FirVarargArgumentsExpressionBuilder firVarargArgumentsExpressionBuilder = new FirVarargArgumentsExpressionBuilder();
                FirVarargArgumentsExpression firVarargArgumentsExpression = (FirVarargArgumentsExpression) firExpression3;
                size = firVarargArgumentsExpression.getArguments().size() + i3;
                CollectionsKt.addAll(firVarargArgumentsExpressionBuilder.getArguments(), arrayList7.subList(i3, size));
                firVarargArgumentsExpressionBuilder.setSource(firExpression3.getSource());
                firVarargArgumentsExpressionBuilder.setTypeRef(firExpression3.getTypeRef());
                firVarargArgumentsExpressionBuilder.setVarargElementType(firVarargArgumentsExpression.getVarargElementType());
                firVarargArgumentsExpressionMo6809build = firVarargArgumentsExpressionBuilder.mo6809build();
            }
            arguments2.add(firVarargArgumentsExpressionMo6809build);
            i3 = size;
        }
        firFunctionCallBuilder.setArgumentList(firArgumentListBuilder.build());
        firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
        firFunctionCallBuilder.setTypeRef(lhsGetCall.getTypeRef());
        FirFunctionCall firFunctionCallCreateSimpleOperatorCall = new GeneratorOfPlusAssignCalls(augmentedArraySetCall, augmentedArraySetCall.getCalleeReference().getSource(), augmentedArraySetCall.getOperation(), firFunctionCallBuilder.mo6809build(), transformedRhs).createSimpleOperatorCall();
        this.resolvingAugmentedAssignment = true;
        try {
            BodyResolveContext context = getTransformer().getContext();
            InferenceSessionForAssignmentOperatorCall inferenceSessionForAssignmentOperatorCall = InferenceSessionForAssignmentOperatorCall.INSTANCE;
            FirInferenceSession inferenceSession = context.getInferenceSession();
            context.setInferenceSession(inferenceSessionForAssignmentOperatorCall);
            try {
                FirFunctionCall firFunctionCall = (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallCreateSimpleOperatorCall, this, ResolutionMode.ContextDependent.INSTANCE);
                context.setInferenceSession(inferenceSession);
                this.resolvingAugmentedAssignment = false;
                GeneratorOfPlusAssignCalls.Companion companion = GeneratorOfPlusAssignCalls.INSTANCE;
                Name name3 = OperatorNameConventions.SET;
                KtSourceElement source4 = augmentedArraySetCall.getSource();
                KtSourceElement source5 = augmentedArraySetCall.getCalleeReference().getSource();
                SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                spreadBuilder.addSpread(arrayList7.toArray(new FirQualifiedAccessExpression[0]));
                spreadBuilder.add(firFunctionCall);
                FirFunctionCall firFunctionCallCreateFunctionCall = companion.createFunctionCall(name3, source4, source5, firQualifiedAccessExpression, (FirExpression[]) spreadBuilder.toArray(new FirExpression[spreadBuilder.size()]));
                this.resolvingAugmentedAssignment = true;
                try {
                    BodyResolveContext context2 = getTransformer().getContext();
                    InferenceSessionForAssignmentOperatorCall inferenceSessionForAssignmentOperatorCall2 = InferenceSessionForAssignmentOperatorCall.INSTANCE;
                    FirInferenceSession inferenceSession2 = context2.getInferenceSession();
                    context2.setInferenceSession(inferenceSessionForAssignmentOperatorCall2);
                    try {
                        FirFunctionCall firFunctionCall2 = (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallCreateFunctionCall, this, ResolutionMode.ContextDependent.INSTANCE);
                        context2.setInferenceSession(inferenceSession2);
                        this.resolvingAugmentedAssignment = false;
                        return new AugmentedArraySetAsGetSetCallDesugaringInfo(this, augmentedArraySetCall, firPropertyGenerateTemporaryVariable$default, arrayList4, firFunctionCall, firFunctionCall2);
                    } catch (Throwable th) {
                        context2.setInferenceSession(inferenceSession2);
                        throw th;
                    }
                } catch (Throwable th2) {
                    this.resolvingAugmentedAssignment = false;
                    throw th2;
                }
            } catch (Throwable th3) {
                context.setInferenceSession(inferenceSession);
                throw th3;
            }
        } catch (Throwable th4) {
            this.resolvingAugmentedAssignment = false;
            throw th4;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformArrayLiteral(FirArrayLiteral arrayLiteral, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(arrayLiteral, "arrayLiteral");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (data instanceof ResolutionMode.ContextDependent.Companion) {
                arrayLiteral.transformChildren(getTransformer(), data);
                return arrayLiteral;
            }
            if (data instanceof ResolutionMode.WithExpectedType) {
                arrayLiteral.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
                FirFunctionCall firFunctionCallGenerateSyntheticArrayOfCall = getTransformer().getComponents().getSyntheticCallGenerator().generateSyntheticArrayOfCall(arrayLiteral, ((ResolutionMode.WithExpectedType) data).getExpectedTypeRef(), getTransformer().getResolutionContext());
                getComponents().getCallCompleter().completeCall(firFunctionCallGenerateSyntheticArrayOfCall, data);
                return this.arrayOfCallTransformer.transformFunctionCall(firFunctionCallGenerateSyntheticArrayOfCall, getSession());
            }
            FirFunctionCall firFunctionCallGenerateSyntheticIdCall = getTransformer().getComponents().getSyntheticCallGenerator().generateSyntheticIdCall(arrayLiteral, getTransformer().getResolutionContext());
            arrayLiteral.transformChildren(getTransformer(), ResolutionMode.ContextDependent.INSTANCE);
            getComponents().getCallCompleter().completeCall(firFunctionCallGenerateSyntheticIdCall, data);
            return arrayLiteral;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(arrayLiteral, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformStringConcatenationCall(FirStringConcatenationCall stringConcatenationCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(stringConcatenationCall, "stringConcatenationCall");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            getComponents().getDataFlowAnalyzer().enterStringConcatenationCall();
            stringConcatenationCall.transformChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            getComponents().getDataFlowAnalyzer().exitStringConcatenationCall(stringConcatenationCall);
            return stringConcatenationCall;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(stringConcatenationCall, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObjectExpression(FirAnonymousObjectExpression anonymousObjectExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(anonymousObjectExpression, "anonymousObjectExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        anonymousObjectExpression.transformAnonymousObject(getTransformer(), data);
        if (!(anonymousObjectExpression.getTypeRef() instanceof FirResolvedTypeRef)) {
            FirAnonymousObjectExpression firAnonymousObjectExpression = anonymousObjectExpression;
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            KtSourceElement source = anonymousObjectExpression.getSource();
            firResolvedTypeRefBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null);
            firResolvedTypeRefBuilder.setType(ScopeUtilsKt.defaultType(anonymousObjectExpression.getAnonymousObject()));
            firAnonymousObjectExpression.replaceTypeRef(firResolvedTypeRefBuilder.mo6809build());
        }
        getComponents().getDataFlowAnalyzer().exitAnonymousObjectExpression(anonymousObjectExpression);
        return anonymousObjectExpression;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunctionExpression(FirAnonymousFunctionExpression anonymousFunctionExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(anonymousFunctionExpression, "anonymousFunctionExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        getComponents().getDataFlowAnalyzer().enterAnonymousFunctionExpression(anonymousFunctionExpression);
        return anonymousFunctionExpression.transformAnonymousFunction(getTransformer(), data);
    }

    public final void storeTypeFromCallee$resolve(FirQualifiedAccessExpression access, boolean isLhsOfAssignment) {
        ConeKotlinType coneKotlinTypeApproximateToSuperType;
        Intrinsics.checkNotNullParameter(access, "access");
        FirResolvedTypeRef firResolvedTypeRefTypeFromCallee = ResolveUtilsKt.typeFromCallee(getTransformer().getComponents(), access);
        FirQualifiedAccessExpression firQualifiedAccessExpression = access;
        FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefTypeFromCallee;
        if (isLhsOfAssignment) {
            coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(getSession()).approximateToSubType(firResolvedTypeRefTypeFromCallee.getType(), (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        } else {
            coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(getSession()).approximateToSuperType(firResolvedTypeRefTypeFromCallee.getType(), (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        }
        firQualifiedAccessExpression.replaceTypeRef(TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRef, coneKotlinTypeApproximateToSuperType, null, 2, null));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformExpression(FirExpression expression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if ((expression.getTypeRef() instanceof FirImplicitTypeRef) && !(expression instanceof FirWrappedExpression)) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setSource(expression.getSource());
            firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Type calculating for " + Reflection.getOrCreateKotlinClass(expression.getClass()) + " is not supported", DiagnosticKind.InferenceError));
            expression.replaceTypeRef(firErrorTypeRefBuilder.mo6809build());
        }
        FirElement firElementTransformChildren = expression.transformChildren(getTransformer(), data);
        Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        return (FirStatement) firElementTransformChildren;
    }

    private final FirQualifiedAccessExpression markSuperReferenceError(ConeDiagnostic superNotAvailableDiagnostic, FirQualifiedAccessExpression superReferenceContainer, FirSuperReference superReference) {
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setDiagnostic(superNotAvailableDiagnostic);
        FirErrorTypeRef firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
        superReferenceContainer.replaceTypeRef(firErrorTypeRefMo6809build);
        superReference.replaceSuperTypeRef(firErrorTypeRefMo6809build);
        FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
        KtSourceElement source = superReferenceContainer.getSource();
        firErrorNamedReferenceBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ReferenceInAtomicQualifiedAccess.INSTANCE, 0, 0, 6, null) : null);
        firErrorNamedReferenceBuilder.setDiagnostic(superNotAvailableDiagnostic);
        superReferenceContainer.replaceCalleeReference(firErrorNamedReferenceBuilder.build());
        return superReferenceContainer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirTypeRef withTypeArgumentsForBareType(FirTypeRef firTypeRef, FirExpression firExpression, FirOperation firOperation) {
        FirClassLikeSymbol<?> symbol;
        FirClassLikeDeclaration firClassLikeDeclaration;
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null) {
            return firTypeRef;
        }
        if (!(coneClassLikeType.getTypeArguments().length == 0) || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), getSession())) == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol.getFir()) == null || firClassLikeDeclaration.getTypeParameters().isEmpty()) {
            return firTypeRef;
        }
        FirTypeRef typeRef = FirExpressionUtilKt.unwrapSmartcastExpression(firExpression).getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef2 = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type2 = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
        if (!(type2 instanceof ConeKotlinType)) {
            type2 = null;
        }
        if (type2 == null) {
            return firTypeRef;
        }
        ConeClassLikeType coneClassLikeTypeComputeRepresentativeTypeForBareType = BareTypesKt.computeRepresentativeTypeForBareType(getTransformer().getComponents(), coneClassLikeType, type2);
        if (coneClassLikeTypeComputeRepresentativeTypeForBareType == null) {
            if (firClassLikeDeclaration.getSymbol().getClassId().isLocal() && (firOperation == FirOperation.AS || firOperation == FirOperation.SAFE_AS)) {
                coneClassLikeTypeComputeRepresentativeTypeForBareType = ScopeUtilsKt.defaultType((FirClass) firClassLikeDeclaration);
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(firTypeRef.getSource());
                firErrorTypeRefBuilder.setDiagnostic(new ConeNoTypeArgumentsOnRhsError(firClassLikeDeclaration.getTypeParameters().size(), firClassLikeDeclaration.getSymbol()));
                return firErrorTypeRefBuilder.mo6809build();
            }
        }
        return coneClassLikeTypeComputeRepresentativeTypeForBareType.getTypeArguments().length == 0 ? firTypeRef : TypeUtilsKt.withReplacedConeType$default(firTypeRef, coneClassLikeTypeComputeRepresentativeTypeForBareType, null, 2, null);
    }

    private static final FirFunctionCall transformIncrementDecrementExpression$buildAndResolveOperatorCall(FirExpressionsResolveTransformer firExpressionsResolveTransformer, FirIncrementDecrementExpression firIncrementDecrementExpression, FirExpression firExpression) {
        FirFunctionCallBuilder firFunctionCallBuilder = new FirFunctionCallBuilder();
        firFunctionCallBuilder.setSource(firIncrementDecrementExpression.getOperationSource());
        firFunctionCallBuilder.setExplicitReceiver(firExpression);
        FirSimpleNamedReferenceBuilder firSimpleNamedReferenceBuilder = new FirSimpleNamedReferenceBuilder();
        KtFakeSourceElementKind ktFakeSourceElementKind = firIncrementDecrementExpression.getIsPrefix() ? KtFakeSourceElementKind.DesugaredPrefixNameReference.INSTANCE : KtFakeSourceElementKind.DesugaredPostfixNameReference.INSTANCE;
        KtSourceElement operationSource = firIncrementDecrementExpression.getOperationSource();
        firSimpleNamedReferenceBuilder.setSource(operationSource != null ? KtSourceElementKt.fakeElement$default(operationSource, ktFakeSourceElementKind, 0, 0, 6, null) : null);
        firSimpleNamedReferenceBuilder.setName(firIncrementDecrementExpression.getOperationName());
        firFunctionCallBuilder.setCalleeReference(firSimpleNamedReferenceBuilder.build());
        firFunctionCallBuilder.setOrigin(FirFunctionCallOrigin.Operator);
        return (FirFunctionCall) FirTransformerUtilKt.transformSingle(firFunctionCallBuilder.mo6809build(), firExpressionsResolveTransformer.getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
    }

    private static final FirVariableAssignment transformIncrementDecrementExpression$buildAndResolveVariableAssignment(FirExpressionsResolveTransformer firExpressionsResolveTransformer, KtSourceElement ktSourceElement, FirExpression firExpression, FirExpression firExpression2) {
        FirExpression expression;
        FirVariableAssignmentBuilder firVariableAssignmentBuilder = new FirVariableAssignmentBuilder();
        firVariableAssignmentBuilder.setSource(ktSourceElement);
        FirDesugaredAssignmentValueReferenceExpressionBuilder firDesugaredAssignmentValueReferenceExpressionBuilder = new FirDesugaredAssignmentValueReferenceExpressionBuilder();
        FirErrorExpression firErrorExpression = firExpression instanceof FirErrorExpression ? (FirErrorExpression) firExpression : null;
        if (firErrorExpression == null || (expression = firErrorExpression.getExpression()) == null) {
            expression = firExpression;
        }
        KtSourceElement source = expression.getSource();
        firDesugaredAssignmentValueReferenceExpressionBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.DesugaredIncrementOrDecrement.INSTANCE, 0, 0, 6, null) : null);
        FirExpressionRef<FirExpression> firExpressionRef = new FirExpressionRef<>();
        firExpressionRef.bind(FirExpressionUtilKt.unwrapSmartcastExpression(firExpression));
        firDesugaredAssignmentValueReferenceExpressionBuilder.setExpressionRef(firExpressionRef);
        firVariableAssignmentBuilder.setLValue(firDesugaredAssignmentValueReferenceExpressionBuilder.mo6809build());
        firVariableAssignmentBuilder.setRValue(firExpression2);
        return (FirVariableAssignment) FirTransformerUtilKt.transformSingle(firVariableAssignmentBuilder.mo6809build(), firExpressionsResolveTransformer.getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
    }
}
