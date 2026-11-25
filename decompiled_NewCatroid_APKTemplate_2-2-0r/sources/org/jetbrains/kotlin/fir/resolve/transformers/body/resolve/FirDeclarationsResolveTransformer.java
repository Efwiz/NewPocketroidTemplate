package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.contracts.impl.FirEmptyContractDescription;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirPropertyBodyResolveState;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.builder.FirAnonymousFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirArgumentList;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirErrorExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.FirMultiDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirWrappedDelegateExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.references.FirNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedErrorReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionModeKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.dfa.FirControlFlowGraphReferenceImpl;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraph;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirAnonymousFunctionReturnExpressionInfo;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeLocalVariableNoTypeOrInitializer;
import org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession;
import org.jetbrains.kotlin.fir.resolve.inference.FirStubTypeTransformer;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.ResolvedLambdaAtom;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirCallCompletionResultsWriterTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver;
import org.jetbrains.kotlin.fir.resolve.transformers.TransformUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirContractResolveTransformerAdapterKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitTypeRefImplWithoutSource;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.calls.inference.model.MutableVariableWithConstraints;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: FirDeclarationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0004\u008e\u0001\u008f\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0004J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0004J$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001e2\u0006\u0010\"\u001a\u00020#H\u0002J \u0010$\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\b\u0010%\u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#H\u0002J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010'\u001a\u00020(2\u0006\u0010\"\u001a\u00020#H\u0002J\u0010\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u001a2\u0006\u00100\u001a\u00020\rH\u0002J\u0018\u00101\u001a\u00020\u001a2\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020.H\u0002J\u0018\u00105\u001a\u0002062\u0006\u00107\u001a\u00020#2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\"\u00108\u001a\u00020#2\u0006\u00107\u001a\u00020#2\b\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J \u0010;\u001a\u00020#2\u0006\u00107\u001a\u00020#2\u0006\u0010<\u001a\u00020:2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010@\u001a\u0002062\u0006\u0010A\u001a\u00020B2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010C\u001a\u0002062\u0006\u0010D\u001a\u00020E2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020G2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010I\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010J\u001a\u00020K2\u0006\u0010\u001b\u001a\u00020K2\u0006\u0010\u0014\u001a\u00020\u0015H\u0004J\u0018\u0010L\u001a\u00020M2\u0006\u0010N\u001a\u00020M2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020P2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020S2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010U\u001a\u0002062\u0006\u0010V\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u001f\u0010W\u001a\u0002HX\"\b\b\u0000\u0010X*\u00020\t2\u0006\u0010V\u001a\u0002HXH\u0002¢\u0006\u0002\u0010YJ\u0010\u0010Z\u001a\u00020.2\u0006\u00100\u001a\u00020.H\u0002J\u0018\u0010[\u001a\u0002062\u0006\u0010\\\u001a\u00020]2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010^\u001a\u00020.2\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010_\u001a\u0002062\u0006\u0010`\u001a\u0002032\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010a\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.2\u0006\u0010b\u001a\u00020cH\u0002J\u0018\u0010d\u001a\u0002062\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010h\u001a\u00020i2\u0006\u0010j\u001a\u00020i2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010k\u001a\u00020l2\u0006\u0010m\u001a\u00020l2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010n\u001a\u0002062\u0006\u0010o\u001a\u00020\u001f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0018\u0010p\u001a\u0002062\u0006\u0010q\u001a\u00020r2\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\"\u0010s\u001a\u0002Ht\"\u0004\b\u0000\u0010t2\f\u0010u\u001a\b\u0012\u0004\u0012\u0002Ht0vH\u0082\b¢\u0006\u0002\u0010wJ\u001e\u0010x\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\f\u0010y\u001a\b\u0012\u0004\u0012\u00020\u00170vH\u0016J\u001c\u0010z\u001a\u00020f2\u0006\u0010g\u001a\u00020f2\f\u0010y\u001a\b\u0012\u0004\u0012\u00020f0vJ\u0016\u0010{\u001a\u00020|*\u00020#2\b\u0010}\u001a\u0004\u0018\u00010|H\u0002J\u0014\u0010,\u001a\u00020\u001a*\u00020~2\u0006\u0010-\u001a\u00020.H\u0002J\u0015\u0010\u007f\u001a\u00020\u001a*\u00020.2\u0007\u0010\u0080\u0001\u001a\u00020\bH\u0002J(\u0010\u0081\u0001\u001a\u00020M*\u00020K2\f\b\u0002\u0010\u0082\u0001\u001a\u0005\u0018\u00010\u0083\u00012\u000b\b\u0002\u0010\u0084\u0001\u001a\u0004\u0018\u00010.H\u0002J\r\u0010\u0085\u0001\u001a\u00020|*\u00020:H\u0002J\u0019\u0010\u0086\u0001\u001a\u00020\u001a*\u00020.2\n\b\u0002\u0010\u0087\u0001\u001a\u00030\u0088\u0001H\u0002J!\u0010\u0089\u0001\u001a\u00020\u001a*\u0002032\u0007\u0010\u008a\u0001\u001a\u00020:2\t\b\u0002\u0010\u008b\u0001\u001a\u00020\bH\u0002J\u000e\u0010\u008c\u0001\u001a\u00030\u008d\u0001*\u00020KH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\u00020\b*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\u00020\b*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0090\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirPartialBodyResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "statusResolver", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirStatusResolver;", "bodyResolved", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getBodyResolved", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Z", "initializerResolved", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "getInitializerResolved", "(Lorg/jetbrains/kotlin/fir/declarations/FirVariable;)Z", "isLocal", "doTransformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "doTransformRegularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "regularClass", "doTransformTypeParameters", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "obtainValueParametersFromExpectedParameterTypes", "", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "expectedTypeParameterTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "lambda", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "obtainValueParametersFromExpectedType", "expectedType", "obtainValueParametersFromResolvedLambdaAtom", "resolvedLambdaAtom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "prepareSignatureForBodyResolve", "callableMember", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "replacePropertyReferenceTypeInDelegateAccessors", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "storeVariableReturnType", "variable", "transformAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "owner", "transformAnonymousFunction", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "anonymousFunction", "transformAnonymousFunctionBody", "expectedReturnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformAnonymousFunctionWithExpectedType", "expectedTypeRef", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "transformAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "transformBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "transformCodeFragment", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "codeFragment", "transformConstructor", "transformDeclarationContent", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformFunction", "function", "transformFunctionWithGivenSignature", "F", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "transformLocalVariable", "transformMultiDelegatedConstructorCall", "multiDelegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirMultiDelegatedConstructorCall;", "transformProperty", "transformPropertyAccessor", "propertyAccessor", "transformPropertyAccessorsWithDelegate", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "transformRegularClass", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformValueParameter", "valueParameter", "transformWrappedDelegateExpression", "wrappedDelegateExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirWrappedDelegateExpression;", "withFirArrayOfCallTransformer", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withRegularClass", "action", "withScript", "computeReturnTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "expected", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "resolveSetter", "mayResolveSetterBody", "resolveStatus", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "containingProperty", "toExpectedTypeRef", "transformAccessors", "setterResolutionMode", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer$SetterResolutionMode;", "transformTypeWithPropertyType", "propertyTypeRef", "forceUpdateForNonImplicitTypes", "visibilityForApproximation", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "ImplicitToErrorTypeTransformer", "SetterResolutionMode", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirDeclarationsResolveTransformer extends FirPartialBodyResolveTransformer {
    private final FirStatusResolver statusResolver;

    /* JADX INFO: compiled from: FirDeclarationsResolveTransformer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer$SetterResolutionMode;", "", "(Ljava/lang/String;I)V", "FULLY_RESOLVE", "ONLY_IMPLICIT_PARAMETER_TYPE", "SKIP", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum SetterResolutionMode {
        FULLY_RESOLVE,
        ONLY_IMPLICIT_PARAMETER_TYPE,
        SKIP;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<SetterResolutionMode> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDeclarationsResolveTransformer(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        this.statusResolver = new FirStatusResolver(getSession(), getComponents().getScopeSession());
    }

    private final Visibility visibilityForApproximation(FirDeclaration firDeclaration) {
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        return TypeUtilsKt.visibilityForApproximation(firDeclaration, (FirDeclaration) CollectionsKt.getOrNull(firDeclarationsResolveTransformer.getTransformer().getContext().getContainers(), firDeclarationsResolveTransformer.getTransformer().getContext().getContainers().size() - 2));
    }

    protected final FirDeclaration transformDeclarationContent(FirDeclaration declaration, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        FirResolveContextCollector firResolveContextCollector = getTransformer().getFirResolveContextCollector();
        if (firResolveContextCollector != null) {
            firResolveContextCollector.addDeclarationContext(declaration, getTransformer().getContext());
        }
        return getTransformer().transformDeclarationContent(declaration, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclarationStatus transformDeclarationStatus(FirDeclarationStatus declarationStatus, ResolutionMode data) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        Intrinsics.checkNotNullParameter(data, "data");
        ResolutionMode.WithStatus withStatus = data instanceof ResolutionMode.WithStatus ? (ResolutionMode.WithStatus) data : null;
        return (withStatus == null || (status = withStatus.getStatus()) == null) ? declarationStatus : status;
    }

    private final void prepareSignatureForBodyResolve(FirCallableDeclaration callableMember) {
        callableMember.transformReturnTypeRef(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        callableMember.transformReceiverParameter(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        if (callableMember instanceof FirFunction) {
            for (FirValueParameter firValueParameter : ((FirFunction) callableMember).getValueParameters()) {
                firValueParameter.transformReturnTypeRef((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                TransformUtilsKt.transformVarargTypeToArrayType(firValueParameter);
            }
        }
    }

    protected final void doTransformTypeParameters(FirMemberDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Iterator<FirTypeParameterRef> it = declaration.getTypeParameters().iterator();
        while (it.hasNext()) {
            it.next().transformChildren(getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirEnumEntry transformEnumEntry(FirEnumEntry enumEntry, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(data, "data");
        if (getImplicitTypeOnly() || getInitializerResolved(enumEntry)) {
            return enumEntry;
        }
        BodyResolveContext context = getTransformer().getContext();
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.ENUM_ENTRY;
        FirTowerDataMode towerDataMode = context.getTowerDataMode();
        try {
            context.setTowerDataMode(firTowerDataMode);
            FirElement firElementTransformChildren = enumEntry.transformChildren(this, data);
            Intrinsics.checkNotNull(firElementTransformChildren, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirEnumEntry");
            return (FirEnumEntry) firElementTransformChildren;
        } finally {
            context.setTowerDataMode(towerDataMode);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v16, types: [org.jetbrains.kotlin.fir.declarations.FirTowerDataContext] */
    /* JADX WARN: Type inference failed for: r11v17 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v2, types: [org.jetbrains.kotlin.fir.declarations.FirTowerDataContext] */
    /* JADX WARN: Type inference failed for: r11v23 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r11v9 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v13, types: [org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher] */
    /* JADX WARN: Type inference failed for: r16v2, types: [org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext] */
    /* JADX WARN: Type inference failed for: r1v10, types: [org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13, types: [org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v40 */
    /* JADX WARN: Type inference failed for: r1v42, types: [org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r20v0, types: [java.lang.Object, org.jetbrains.kotlin.fir.resolve.ResolutionMode] */
    /* JADX WARN: Type inference failed for: r20v1 */
    /* JADX WARN: Type inference failed for: r20v3 */
    /* JADX WARN: Type inference failed for: r6v2, types: [org.jetbrains.kotlin.fir.declarations.FirTowerDataContext] */
    /* JADX WARN: Type inference failed for: r6v3, types: [org.jetbrains.kotlin.fir.declarations.FirTowerDataContext] */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(FirProperty property, ResolutionMode data) {
        int i;
        ?? r11;
        ?? r1;
        boolean z;
        FirPropertyBodyResolveState firPropertyBodyResolveState;
        ?? transformer;
        int i2;
        FirPropertyBodyResolveState firPropertyBodyResolveState2;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (property instanceof FirSyntheticProperty) {
                throw new IllegalArgumentException("Synthetic properties should not be processed by body transformers".toString());
            }
            FirAbstractBodyResolveTransformerDispatcher transformer2 = null;
            if (property.getIsLocal()) {
                prepareSignatureForBodyResolve(property);
                property.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, property, null, null, 3, null)));
                FirPropertyAccessor getter = property.getGetter();
                if (getter != null) {
                    getter.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, getter, null, property, 1, null)));
                }
                FirPropertyAccessor setter = property.getSetter();
                if (setter != null) {
                    setter.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, setter, null, property, 1, null)));
                }
                FirBackingField backingField = property.getBackingField();
                if (backingField != null) {
                    backingField.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, backingField, null, property, 1, null)));
                }
                BodyResolveContext context = getTransformer().getContext();
                if (property.getTypeParameters().isEmpty()) {
                    context.getContainers().add(property);
                    try {
                        doTransformTypeParameters(property);
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        context.getContainers().removeLast();
                    }
                } else {
                    FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(property);
                    FirTowerDataContext towerDataContext = context.getTowerDataContext();
                    try {
                        context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                        context.getContainers().add(property);
                        try {
                            doTransformTypeParameters(property);
                            Unit unit2 = Unit.INSTANCE;
                            context.getContainers().removeLast();
                            context.replaceTowerDataContext(towerDataContext);
                        } catch (Throwable th) {
                            context.getContainers().removeLast();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        context.replaceTowerDataContext(towerDataContext);
                        throw th2;
                    }
                }
                return transformLocalVariable(property);
            }
            FirTypeRef returnTypeRef = property.getReturnTypeRef();
            FirPropertyBodyResolveState bodyResolveState = property.getBodyResolveState();
            if (bodyResolveState == FirPropertyBodyResolveState.EVERYTHING_RESOLVED) {
                return property;
            }
            boolean z2 = property.getBackingField() != null;
            if (!(returnTypeRef instanceof FirImplicitTypeRef) && getImplicitTypeOnly() && !z2) {
                return property;
            }
            property.transformReceiverParameter((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            doTransformTypeParameters(property);
            boolean implicitTypeOnly = getImplicitTypeOnly();
            FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
            boolean implicitTypeOnly2 = firDeclarationsResolveTransformer.getImplicitTypeOnly();
            if (implicitTypeOnly2) {
                firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(false);
            }
            try {
                boolean z3 = bodyResolveState.compareTo(FirPropertyBodyResolveState.INITIALIZER_RESOLVED) >= 0;
                if (!z3) {
                    getComponents().getDataFlowAnalyzer().enterProperty(property);
                }
                BodyResolveContext context2 = getTransformer().getContext();
                if (property.getTypeParameters().isEmpty()) {
                    context2.getContainers().add(property);
                    try {
                        BodyResolveContext context3 = getTransformer().getContext();
                        FirTowerDataContext towerDataContext2 = context3.getTowerDataContext();
                        try {
                            try {
                                FirLocalScope primaryConstructorPureParametersScope = context3.getPrimaryConstructorPureParametersScope();
                                if (primaryConstructorPureParametersScope != null) {
                                    context3.addLocalScope(primaryConstructorPureParametersScope);
                                    Unit unit3 = Unit.INSTANCE;
                                    Unit unit4 = Unit.INSTANCE;
                                }
                                if (!z3) {
                                    ResolutionMode resolutionModeWithExpectedType$default = ResolutionModeKt.withExpectedType$default(returnTypeRef, false, 2, (Object) null);
                                    FirProperty firPropertyTransformReturnTypeRef = property.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), resolutionModeWithExpectedType$default);
                                    transformer = getTransformer();
                                    firPropertyTransformReturnTypeRef.transformInitializer((FirTransformer<? super ResolutionMode>) transformer, resolutionModeWithExpectedType$default).transformTypeParameters((FirTransformer<? super ResolutionMode>) getTransformer(), resolutionModeWithExpectedType$default).replaceBodyResolveState(FirPropertyBodyResolveState.INITIALIZER_RESOLVED);
                                }
                                if (property.getInitializer() != null) {
                                    storeVariableReturnType(property);
                                }
                                if (!z3) {
                                    property.transformOtherChildren((FirTransformer<? super ??>) getTransformer(), data);
                                }
                                boolean z4 = DeclarationAttributesKt.getHasExplicitBackingField(property) || (property.getReturnTypeRef() instanceof FirResolvedTypeRef);
                                if (z3 || !z4) {
                                    i2 = 2;
                                    z = false;
                                } else {
                                    i2 = 2;
                                    property.transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(property.getReturnTypeRef(), false, 2, (Object) null));
                                    z = true;
                                }
                                Unit unit5 = Unit.INSTANCE;
                                context3.replaceTowerDataContext(towerDataContext2);
                                FirExpression delegate = property.getDelegate();
                                if (delegate != null) {
                                    transformPropertyAccessorsWithDelegate(property, delegate);
                                    if (property.getDelegateFieldSymbol() != null) {
                                        replacePropertyReferenceTypeInDelegateAccessors(property);
                                    }
                                    property.replaceBodyResolveState(FirPropertyBodyResolveState.EVERYTHING_RESOLVED);
                                    Unit unit6 = Unit.INSTANCE;
                                    i = i2;
                                    context2 = context2;
                                } else {
                                    boolean z5 = !implicitTypeOnly || ((property.getGetter() == null || (property.getGetter() instanceof FirDefaultPropertyAccessor)) && (property.getSetter() == null || (property.getSetter() instanceof FirDefaultPropertyAccessor)));
                                    FirTypeRef returnTypeRef2 = property.getReturnTypeRef();
                                    boolean z6 = returnTypeRef2 instanceof FirResolvedTypeRef;
                                    if (z5 || !z6) {
                                        i = i2;
                                        context2 = context2;
                                        transformAccessors(property, z5 ? SetterResolutionMode.FULLY_RESOLVE : SetterResolutionMode.ONLY_IMPLICIT_PARAMETER_TYPE);
                                        if (z5) {
                                            firPropertyBodyResolveState2 = FirPropertyBodyResolveState.EVERYTHING_RESOLVED;
                                        } else {
                                            firPropertyBodyResolveState2 = FirPropertyBodyResolveState.INITIALIZER_AND_GETTER_RESOLVED;
                                        }
                                        property.replaceBodyResolveState(firPropertyBodyResolveState2);
                                        Unit unit7 = Unit.INSTANCE;
                                    } else {
                                        FirPropertyAccessor getter2 = property.getGetter();
                                        if (getter2 != null) {
                                            transformTypeWithPropertyType$default(this, getter2, returnTypeRef2, false, 2, null);
                                            Unit unit8 = Unit.INSTANCE;
                                        }
                                        FirPropertyAccessor setter2 = property.getSetter();
                                        if (setter2 != null) {
                                            transformTypeWithPropertyType$default(this, setter2, returnTypeRef2, false, 2, null);
                                            Unit unit9 = Unit.INSTANCE;
                                        }
                                        FirPropertyAccessor setter3 = property.getSetter();
                                        if (setter3 != null) {
                                            i = 2;
                                            setter3.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default((ConeKotlinType) getSession().getBuiltinTypes().getUnitType().getType(), false, 2, (Object) null));
                                        } else {
                                            i = 2;
                                        }
                                    }
                                }
                                context2.getContainers().removeLast();
                            } catch (Throwable th3) {
                                context3.replaceTowerDataContext(towerDataContext2);
                                throw th3;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            transformer.getContainers().removeLast();
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        transformer = context2;
                    }
                } else {
                    i = 2;
                    FirMemberTypeParameterScope firMemberTypeParameterScope2 = new FirMemberTypeParameterScope(property);
                    FirTowerDataContext towerDataContext3 = context2.getTowerDataContext();
                    try {
                        BodyResolveContext bodyResolveContext = context2;
                        try {
                            bodyResolveContext.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope2, false));
                            bodyResolveContext.getContainers().add(property);
                            try {
                                BodyResolveContext context4 = getTransformer().getContext();
                                FirTowerDataContext towerDataContext4 = context4.getTowerDataContext();
                                try {
                                    FirLocalScope primaryConstructorPureParametersScope2 = context4.getPrimaryConstructorPureParametersScope();
                                    if (primaryConstructorPureParametersScope2 != null) {
                                        context4.addLocalScope(primaryConstructorPureParametersScope2);
                                        Unit unit10 = Unit.INSTANCE;
                                        Unit unit11 = Unit.INSTANCE;
                                    }
                                    if (!z3) {
                                        ResolutionMode resolutionModeWithExpectedType$default2 = ResolutionModeKt.withExpectedType$default(returnTypeRef, false, 2, (Object) null);
                                        FirProperty firPropertyTransformInitializer = property.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), resolutionModeWithExpectedType$default2).transformInitializer((FirTransformer<? super ResolutionMode>) getTransformer(), resolutionModeWithExpectedType$default2);
                                        transformer2 = getTransformer();
                                        firPropertyTransformInitializer.transformTypeParameters((FirTransformer<? super ResolutionMode>) transformer2, resolutionModeWithExpectedType$default2).replaceBodyResolveState(FirPropertyBodyResolveState.INITIALIZER_RESOLVED);
                                    }
                                    if (property.getInitializer() != null) {
                                        storeVariableReturnType(property);
                                    }
                                    if (!z3) {
                                        property.transformOtherChildren((FirTransformer<? super ??>) getTransformer(), data);
                                    }
                                    boolean z7 = DeclarationAttributesKt.getHasExplicitBackingField(property) || (property.getReturnTypeRef() instanceof FirResolvedTypeRef);
                                    if (z3 || !z7) {
                                        z = false;
                                        r11 = transformer2;
                                    } else {
                                        r11 = 0;
                                        property.transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(property.getReturnTypeRef(), false, 2, (Object) null));
                                        z = true;
                                    }
                                    Unit unit12 = Unit.INSTANCE;
                                    context4.replaceTowerDataContext(towerDataContext4);
                                    FirExpression delegate2 = property.getDelegate();
                                    if (delegate2 != null) {
                                        transformPropertyAccessorsWithDelegate(property, delegate2);
                                        if (property.getDelegateFieldSymbol() != null) {
                                            replacePropertyReferenceTypeInDelegateAccessors(property);
                                        }
                                        property.replaceBodyResolveState(FirPropertyBodyResolveState.EVERYTHING_RESOLVED);
                                        Unit unit13 = Unit.INSTANCE;
                                        bodyResolveContext = bodyResolveContext;
                                        r11 = towerDataContext3;
                                    } else {
                                        boolean z8 = !implicitTypeOnly || ((property.getGetter() == null || (property.getGetter() instanceof FirDefaultPropertyAccessor)) && (property.getSetter() == null || (property.getSetter() instanceof FirDefaultPropertyAccessor)));
                                        FirTypeRef returnTypeRef3 = property.getReturnTypeRef();
                                        boolean z9 = returnTypeRef3 instanceof FirResolvedTypeRef;
                                        try {
                                            if (z8 || !z9) {
                                                bodyResolveContext = bodyResolveContext;
                                                r11 = towerDataContext3;
                                                transformAccessors(property, z8 ? SetterResolutionMode.FULLY_RESOLVE : SetterResolutionMode.ONLY_IMPLICIT_PARAMETER_TYPE);
                                                if (z8) {
                                                    firPropertyBodyResolveState = FirPropertyBodyResolveState.EVERYTHING_RESOLVED;
                                                } else {
                                                    firPropertyBodyResolveState = FirPropertyBodyResolveState.INITIALIZER_AND_GETTER_RESOLVED;
                                                }
                                                property.replaceBodyResolveState(firPropertyBodyResolveState);
                                                Unit unit14 = Unit.INSTANCE;
                                            } else {
                                                try {
                                                    FirPropertyAccessor getter3 = property.getGetter();
                                                    if (getter3 != null) {
                                                        transformTypeWithPropertyType$default(this, getter3, returnTypeRef3, false, 2, null);
                                                        Unit unit15 = Unit.INSTANCE;
                                                    }
                                                    FirPropertyAccessor setter4 = property.getSetter();
                                                    if (setter4 != null) {
                                                        transformTypeWithPropertyType$default(this, setter4, returnTypeRef3, false, 2, null);
                                                        Unit unit16 = Unit.INSTANCE;
                                                    }
                                                    FirPropertyAccessor setter5 = property.getSetter();
                                                    r11 = towerDataContext3;
                                                    if (setter5 != null) {
                                                        setter5.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default((ConeKotlinType) getSession().getBuiltinTypes().getUnitType().getType(), false, 2, (Object) null));
                                                        r11 = towerDataContext3;
                                                    }
                                                } catch (Throwable th6) {
                                                    th = th6;
                                                    data = bodyResolveContext;
                                                    r11 = towerDataContext3;
                                                    r1 = data;
                                                    try {
                                                        r1.getContainers().removeLast();
                                                        throw th;
                                                    } catch (Throwable th7) {
                                                        th = th7;
                                                        r1.replaceTowerDataContext(r11);
                                                        throw th;
                                                    }
                                                }
                                            }
                                        } catch (Throwable th8) {
                                            th = th8;
                                        }
                                    }
                                    try {
                                        bodyResolveContext.getContainers().removeLast();
                                        bodyResolveContext.replaceTowerDataContext(r11);
                                    } catch (Throwable th9) {
                                        th = th9;
                                        r1 = bodyResolveContext;
                                        r1.replaceTowerDataContext(r11);
                                        throw th;
                                    }
                                } catch (Throwable th10) {
                                    r1 = bodyResolveContext;
                                    r11 = towerDataContext3;
                                    try {
                                        context4.replaceTowerDataContext(towerDataContext4);
                                        throw th10;
                                    } catch (Throwable th11) {
                                        th = th11;
                                        r1.getContainers().removeLast();
                                        throw th;
                                    }
                                }
                            } catch (Throwable th12) {
                                th = th12;
                                r1 = bodyResolveContext;
                                r11 = towerDataContext3;
                            }
                        } catch (Throwable th13) {
                            th = th13;
                            r1 = bodyResolveContext;
                            r11 = towerDataContext3;
                        }
                    } catch (Throwable th14) {
                        th = th14;
                        r11 = towerDataContext3;
                        r1 = context2;
                    }
                }
                if (!z3) {
                    if (!z) {
                        property.transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(property.getReturnTypeRef(), false, i, (Object) null));
                    }
                    ControlFlowGraph controlFlowGraphExitProperty = getComponents().getDataFlowAnalyzer().exitProperty(property);
                    if (controlFlowGraphExitProperty != null) {
                        property.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(controlFlowGraphExitProperty, null, i, null));
                        Unit unit17 = Unit.INSTANCE;
                        Unit unit18 = Unit.INSTANCE;
                    }
                }
                if (implicitTypeOnly2) {
                    firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(true);
                }
                return property;
            } catch (Throwable th15) {
                if (implicitTypeOnly2) {
                    firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(true);
                }
                throw th15;
            }
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(property, th);
            throw null;
        } catch (Throwable th16) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(property, th16);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirField transformField(FirField field, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirTypeRef returnTypeRef = field.getReturnTypeRef();
            if (getImplicitTypeOnly() || getInitializerResolved(field)) {
                return field;
            }
            getComponents().getDataFlowAnalyzer().enterField(field);
            FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
            boolean implicitTypeOnly = firDeclarationsResolveTransformer.getImplicitTypeOnly();
            if (implicitTypeOnly) {
                firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(false);
            }
            try {
                BodyResolveContext context = getTransformer().getContext();
                FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
                FirTowerDataMode towerDataMode = context.getTowerDataMode();
                try {
                    context.setTowerDataMode(firTowerDataMode);
                    context.getContainers().add(field);
                    try {
                        FirTowerDataContext towerDataContext = context.getTowerDataContext();
                        try {
                            FirLocalScope primaryConstructorAllParametersScope = context.getPrimaryConstructorAllParametersScope();
                            if (primaryConstructorAllParametersScope != null) {
                                context.addLocalScope(primaryConstructorAllParametersScope);
                            }
                            field.transformChildren(getTransformer(), ResolutionModeKt.withExpectedType$default(returnTypeRef, false, 2, (Object) null));
                            context.replaceTowerDataContext(towerDataContext);
                            context.getContainers().removeLast();
                            context.setTowerDataMode(towerDataMode);
                            if (field.getInitializer() != null) {
                                storeVariableReturnType(field);
                            }
                            ControlFlowGraph controlFlowGraphExitField = getComponents().getDataFlowAnalyzer().exitField(field);
                            if (controlFlowGraphExitField != null) {
                                field.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(controlFlowGraphExitField, null, 2, null));
                            }
                            if (implicitTypeOnly) {
                                firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(true);
                            }
                            return field;
                        } catch (Throwable th) {
                            context.replaceTowerDataContext(towerDataContext);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        context.getContainers().removeLast();
                        throw th2;
                    }
                } catch (Throwable th3) {
                    context.setTowerDataMode(towerDataMode);
                    throw th3;
                }
            } catch (Throwable th4) {
                if (implicitTypeOnly) {
                    firDeclarationsResolveTransformer.setImplicitTypeOnly$resolve(true);
                }
                throw th4;
            }
        } catch (Throwable th5) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(field, th5);
            throw null;
        }
    }

    private final void replacePropertyReferenceTypeInDelegateAccessors(FirFunctionCall firFunctionCall, FirProperty firProperty) {
        ConeClassLikeType coneType;
        FirTypeRef typeRef;
        Set<FirExpression> setKeySet;
        List list;
        FirArgumentList argumentList = firFunctionCall.getArgumentList();
        LinkedHashMap<FirExpression, FirValueParameter> mapping = argumentList instanceof FirResolvedArgumentList ? ((FirResolvedArgumentList) argumentList).getMapping() : null;
        FirExpression firExpression = (mapping == null || (setKeySet = mapping.keySet()) == null || (list = CollectionsKt.toList(setKeySet)) == null) ? null : (FirExpression) CollectionsKt.getOrNull(list, 1);
        FirCallableReferenceAccess firCallableReferenceAccess = firExpression instanceof FirCallableReferenceAccess ? (FirCallableReferenceAccess) firExpression : null;
        if (firCallableReferenceAccess == null) {
            return;
        }
        FirTypeRef typeRef2 = firCallableReferenceAccess.getTypeRef();
        if ((typeRef2 instanceof FirResolvedTypeRef) && (firProperty.getReturnTypeRef() instanceof FirResolvedTypeRef)) {
            FirResolvedTypeRef firResolvedTypeRef = (FirResolvedTypeRef) typeRef2;
            ConeKotlinType type = firResolvedTypeRef.getType();
            Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            ConeTypeProjection[] typeArguments = ((ConeClassLikeType) type).getTypeArguments();
            FirReceiverParameter receiverParameter = firProperty.getReceiverParameter();
            ConeKotlinType coneType2 = (receiverParameter == null || (typeRef = receiverParameter.getTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(typeRef);
            FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
            FirRegularClass containingClass = firDeclarationsResolveTransformer.getTransformer().getContext().getContainingClass();
            ConeClassLikeType coneClassLikeTypeConstructStarProjectedType$default = containingClass != null ? TypeConstructionUtilsKt.constructStarProjectedType$default(containingClass.getSymbol(), containingClass.getTypeParameters().size(), false, 2, null) : null;
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(typeRef2.getSource());
            firResolvedTypeRefBuilder.getAnnotations().addAll(typeRef2.getAnnotations());
            ConeKotlinType type2 = firResolvedTypeRef.getType();
            Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
            ConeClassLikeLookupTag lookupTag = ((ConeClassLikeType) type2).getLookupTag();
            ArrayList arrayList = new ArrayList(typeArguments.length);
            int length = typeArguments.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                ConeKotlinType coneKotlinType = typeArguments[i];
                int i3 = i2 + 1;
                if (i2 == ArraysKt.getLastIndex(typeArguments)) {
                    coneType = FirTypeUtilsKt.getConeType(firProperty.getReturnTypeRef());
                } else {
                    coneType = (i2 != 0 || coneType2 == null) ? coneClassLikeTypeConstructStarProjectedType$default : coneType2;
                }
                if (coneType != null) {
                    coneKotlinType = coneType;
                }
                arrayList.add(coneKotlinType);
                i++;
                i2 = i3;
            }
            firResolvedTypeRefBuilder.setType(TypeConstructionUtilsKt.constructClassType$default(lookupTag, (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]), false, null, 4, null));
            FirResolvedTypeRef firResolvedTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker != null) {
                FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRefMo6809build;
                KtSourceElement source = firCallableReferenceAccess.getSource();
                if (source == null) {
                    source = firFunctionCall.getSource();
                }
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef2, source, firDeclarationsResolveTransformer.getTransformer().getComponents().getFile().getSource());
            }
            firCallableReferenceAccess.replaceTypeRef(firResolvedTypeRefMo6809build);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void replacePropertyReferenceTypeInDelegateAccessors(FirProperty property) {
        FirBlock body;
        List<FirStatement> statements;
        FirBlock body2;
        List<FirStatement> statements2;
        FirPropertyAccessor getter = property.getGetter();
        FirStatement firStatement = (getter == null || (body2 = getter.getBody()) == null || (statements2 = body2.getStatements()) == null) ? null : (FirStatement) CollectionsKt.singleOrNull((List) statements2);
        FirReturnExpression firReturnExpression = firStatement instanceof FirReturnExpression ? (FirReturnExpression) firStatement : null;
        if (firReturnExpression != null) {
            FirExpression result = firReturnExpression.getResult();
            FirFunctionCall firFunctionCall = result instanceof FirFunctionCall ? (FirFunctionCall) result : null;
            if (firFunctionCall != null) {
                replacePropertyReferenceTypeInDelegateAccessors(firFunctionCall, property);
            }
        }
        FirPropertyAccessor setter = property.getSetter();
        FirStatement firStatement2 = (setter == null || (body = setter.getBody()) == null || (statements = body.getStatements()) == null) ? null : (FirStatement) CollectionsKt.singleOrNull((List) statements);
        FirFunctionCall firFunctionCall2 = firStatement2 instanceof FirFunctionCall ? (FirFunctionCall) firStatement2 : null;
        if (firFunctionCall2 != null) {
            replacePropertyReferenceTypeInDelegateAccessors(firFunctionCall2, property);
        }
        FirExpression delegate = property.getDelegate();
        FirFunctionCall firFunctionCall3 = delegate instanceof FirFunctionCall ? (FirFunctionCall) delegate : null;
        if (firFunctionCall3 != null) {
            replacePropertyReferenceTypeInDelegateAccessors(firFunctionCall3, property);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void transformPropertyAccessorsWithDelegate(FirProperty property, FirExpression delegate) {
        boolean z = property.getReturnTypeRef() instanceof FirImplicitTypeRef;
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        BodyResolveContext context = firDeclarationsResolveTransformer.getTransformer().getContext();
        ResolutionContext resolutionContext = firDeclarationsResolveTransformer.getTransformer().getResolutionContext();
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = this;
        FirDelegatedPropertyInferenceSession firDelegatedPropertyInferenceSession = new FirDelegatedPropertyInferenceSession(property, resolutionContext, firDeclarationsResolveTransformer2.getComponents().getCallCompleter().createPostponedArgumentsAnalyzer(resolutionContext));
        FirInferenceSession inferenceSession = context.getInferenceSession();
        context.setInferenceSession(firDelegatedPropertyInferenceSession);
        try {
            getComponents().getDataFlowAnalyzer().enterDelegateExpression();
            if (property.getIsLocal()) {
                property.transformDelegate((FirTransformer<? super ResolutionMode.ContextDependent.Delegate>) getTransformer(), ResolutionMode.ContextDependent.Delegate.INSTANCE);
            } else {
                BodyResolveContext context2 = getTransformer().getContext();
                FirTowerDataContext towerDataContext = context2.getTowerDataContext();
                try {
                    FirLocalScope primaryConstructorPureParametersScope = context2.getPrimaryConstructorPureParametersScope();
                    if (primaryConstructorPureParametersScope != null) {
                        context2.addLocalScope(primaryConstructorPureParametersScope);
                    }
                    property.transformDelegate((FirTransformer<? super ResolutionMode.ContextDependent.Delegate>) getTransformer(), ResolutionMode.ContextDependent.Delegate.INSTANCE);
                    context2.replaceTowerDataContext(towerDataContext);
                } catch (Throwable th) {
                    context2.replaceTowerDataContext(towerDataContext);
                    throw th;
                }
            }
            transformAccessors(property, z ? SetterResolutionMode.SKIP : SetterResolutionMode.FULLY_RESOLVE);
            List<FirResolvable> listCompleteCandidates = firDelegatedPropertyInferenceSession.completeCandidates();
            ConeSubstitutor coneSubstitutorCreateFinalSubstitutor = firDelegatedPropertyInferenceSession.createFinalSubstitutor();
            FirTransformerUtilKt.transformSingle(property, new FirStubTypeTransformer(coneSubstitutorCreateFinalSubstitutor), null);
            property.replaceReturnTypeRef(DeclarationApproximationUtilsKt.approximateDeclarationType$default(property.getReturnTypeRef(), getSession(), visibilityForApproximation(property), property.getIsLocal(), false, false, 24, null));
            FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformerCreateCompletionResultsWriter = getComponents().getCallCompleter().createCompletionResultsWriter(coneSubstitutorCreateFinalSubstitutor, FirCallCompletionResultsWriterTransformer.Mode.DelegatedPropertyCompletion);
            Iterator<T> it = listCompleteCandidates.iterator();
            while (it.hasNext()) {
                FirTransformerUtilKt.transformSingle((FirResolvable) it.next(), firCallCompletionResultsWriterTransformerCreateCompletionResultsWriter, null);
            }
            Unit unit = Unit.INSTANCE;
            context.setInferenceSession(inferenceSession);
            if (z) {
                resolveSetter(property, true);
            }
            firDeclarationsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitDelegateExpression(delegate);
        } catch (Throwable th2) {
            context.setInferenceSession(inferenceSession);
            throw th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessor(FirPropertyAccessor propertyAccessor, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(data, "data");
        transformProperty((FirProperty) propertyAccessor.getPropertySymbol().getFir(), data);
        return propertyAccessor;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformWrappedDelegateExpression(FirWrappedDelegateExpression wrappedDelegateExpression, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(wrappedDelegateExpression, "wrappedDelegateExpression");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        FirExpression firExpression = (FirExpression) FirTransformerUtilKt.transformSingle(FirTransformerUtilKt.transformSingle(wrappedDelegateExpression.getExpression(), getTransformer(), ResolutionMode.ContextDependent.Delegate.INSTANCE), firDeclarationsResolveTransformer.getTransformer().getComponents().getIntegerLiteralAndOperatorApproximationTransformer(), null);
        if (firExpression instanceof FirResolvable) {
            FirReference calleeReference = ((FirResolvable) firExpression).getCalleeReference();
            if (calleeReference instanceof FirNamedReferenceWithCandidate) {
                NewConstraintSystemImpl system = ((FirNamedReferenceWithCandidate) calleeReference).getCandidate().getSystem();
                Iterator<Map.Entry<TypeConstructorMarker, MutableVariableWithConstraints>> it = system.getNotFixedTypeVariables().entrySet().iterator();
                while (it.hasNext()) {
                    system.markPostponedVariable(it.next().getValue().getTypeVariable());
                }
                ConeSubstitutor coneSubstitutorCreateTypeSubstitutorByTypeConstructor = SubstitutorsKt.createTypeSubstitutorByTypeConstructor(firDeclarationsResolveTransformer.getTransformer().getContext().getInferenceSession().createSyntheticStubTypes(system), TypeComponentsKt.getTypeContext(getSession()), true);
                FirTypeRef typeRef = firExpression.getTypeRef();
                firExpression.replaceTypeRef(TypeUtilsKt.withReplacedConeType$default(typeRef, coneSubstitutorCreateTypeSubstitutorByTypeConstructor.substituteOrNull(FirTypeUtilsKt.getConeType(typeRef)), null, 2, null));
            }
        }
        FirExpression delegateProvider = wrappedDelegateExpression.getDelegateProvider();
        Intrinsics.checkNotNull(delegateProvider, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirFunctionCall");
        FirFunctionCall firFunctionCall = (FirFunctionCall) delegateProvider;
        firFunctionCall.replaceExplicitReceiver(firExpression);
        FirExpressionsResolveTransformer expressionsTransformer = getTransformer().getExpressionsTransformer();
        if (expressionsTransformer != null) {
            expressionsTransformer.transformFunctionCallInternal$resolve(firFunctionCall, ResolutionMode.ContextIndependent.INSTANCE, true);
        }
        FirFunctionCall firFunctionCall2 = firFunctionCall;
        Candidate candidate = CandidateFactoryKt.candidate(firFunctionCall2);
        if (candidate != null && candidate.isSuccessful()) {
            NewConstraintSystemImpl system2 = candidate.getSystem();
            Iterator<Map.Entry<TypeConstructorMarker, MutableVariableWithConstraints>> it2 = system2.getNotFixedTypeVariables().entrySet().iterator();
            while (it2.hasNext()) {
                system2.markPostponedVariable(it2.next().getValue().getTypeVariable());
            }
            firFunctionCall.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firFunctionCall.getTypeRef(), SubstitutorsKt.createTypeSubstitutorByTypeConstructor(firDeclarationsResolveTransformer.getTransformer().getContext().getInferenceSession().createSyntheticStubTypes(system2), TypeComponentsKt.getTypeContext(getSession()), true).substituteOrSelf(candidate.getSubstitutor().substituteOrSelf(ResolveUtilsKt.typeFromCallee(firDeclarationsResolveTransformer.getTransformer().getComponents(), firFunctionCall2).getType())), null, 2, null));
            return firFunctionCall;
        }
        FirNamedReference calleeReference2 = firFunctionCall.getCalleeReference();
        if ((calleeReference2 instanceof FirResolvedNamedReference) && !(calleeReference2 instanceof FirResolvedErrorReference)) {
            return firFunctionCall;
        }
        return firExpression;
    }

    private final FirProperty transformLocalVariable(FirProperty variable) {
        FirSession session = getSession();
        try {
            variable.getIsLocal();
            FirExpression delegate = variable.getDelegate();
            boolean z = !(variable.getReturnTypeRef() instanceof FirImplicitTypeRef);
            if (delegate != null) {
                transformPropertyAccessorsWithDelegate(variable, delegate);
                if (variable.getDelegateFieldSymbol() != null) {
                    replacePropertyReferenceTypeInDelegateAccessors(variable);
                }
                variable.transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(variable.getReturnTypeRef(), false, 2, (Object) null));
            } else {
                ResolutionMode resolutionModeWithExpectedType$default = ResolutionModeKt.withExpectedType$default(variable.getReturnTypeRef(), false, 2, (Object) null);
                if (variable.getInitializer() != null) {
                    variable.transformInitializer((FirTransformer<? super ResolutionMode>) getTransformer(), resolutionModeWithExpectedType$default);
                    storeVariableReturnType(variable);
                }
                variable.transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(variable.getReturnTypeRef(), false, 2, (Object) null));
                transformAccessors$default(this, variable, null, 1, null);
            }
            variable.transformOtherChildren((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            getTransformer().getContext().storeVariable(variable, getSession());
            getComponents().getDataFlowAnalyzer().exitLocalVariableDeclaration(variable, z);
            return variable;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(variable, th);
            throw null;
        }
    }

    static /* synthetic */ void transformAccessors$default(FirDeclarationsResolveTransformer firDeclarationsResolveTransformer, FirProperty firProperty, SetterResolutionMode setterResolutionMode, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: transformAccessors");
        }
        if ((i & 1) != 0) {
            setterResolutionMode = SetterResolutionMode.FULLY_RESOLVE;
        }
        firDeclarationsResolveTransformer.transformAccessors(firProperty, setterResolutionMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void transformAccessors(FirProperty firProperty, SetterResolutionMode setterResolutionMode) {
        FirPropertyAccessor getter;
        if (firProperty.getBodyResolveState().compareTo(FirPropertyBodyResolveState.INITIALIZER_AND_GETTER_RESOLVED) < 0 && (getter = firProperty.getGetter()) != null) {
            transformAccessor(getter, firProperty);
        }
        if (firProperty.getReturnTypeRef() instanceof FirImplicitTypeRef) {
            storeVariableReturnType(firProperty);
            FirPropertyAccessor getter2 = firProperty.getGetter();
            if (getter2 != null) {
                transformTypeWithPropertyType(getter2, firProperty.getReturnTypeRef(), true);
            }
        }
        if (setterResolutionMode != SetterResolutionMode.SKIP) {
            resolveSetter(firProperty, setterResolutionMode == SetterResolutionMode.FULLY_RESOLVE);
        }
    }

    private final void resolveSetter(FirProperty firProperty, boolean z) {
        FirPropertyAccessor setter = firProperty.getSetter();
        if (setter != null) {
            transformTypeWithPropertyType$default(this, setter, firProperty.getReturnTypeRef(), false, 2, null);
            if (z) {
                transformAccessor(setter, firProperty);
            }
        }
    }

    static /* synthetic */ void transformTypeWithPropertyType$default(FirDeclarationsResolveTransformer firDeclarationsResolveTransformer, FirPropertyAccessor firPropertyAccessor, FirTypeRef firTypeRef, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: transformTypeWithPropertyType");
        }
        if ((i & 2) != 0) {
            z = false;
        }
        firDeclarationsResolveTransformer.transformTypeWithPropertyType(firPropertyAccessor, firTypeRef, z);
    }

    private final void transformTypeWithPropertyType(FirPropertyAccessor firPropertyAccessor, FirTypeRef firTypeRef, boolean z) {
        FirValueParameter firValueParameter;
        if (firPropertyAccessor.getIsGetter()) {
            if ((firPropertyAccessor.getReturnTypeRef() instanceof FirImplicitTypeRef) || z) {
                firPropertyAccessor.replaceReturnTypeRef(UtilsKt.copyWithNewSource(firTypeRef, firPropertyAccessor.getReturnTypeRef().getSource()));
                return;
            }
            return;
        }
        if (firPropertyAccessor.isSetter() && (firValueParameter = (FirValueParameter) CollectionsKt.firstOrNull((List) firPropertyAccessor.getValueParameters())) != null && (firValueParameter.getReturnTypeRef() instanceof FirImplicitTypeRef)) {
            firValueParameter.replaceReturnTypeRef(UtilsKt.copyWithNewSource(firTypeRef, firPropertyAccessor.getReturnTypeRef().getSource()));
        }
    }

    private final void transformAccessor(final FirPropertyAccessor accessor, final FirProperty owner) {
        FirSession session = getSession();
        try {
            BodyResolveContext.withPropertyAccessor$default(getTransformer().getContext(), owner, accessor, getTransformer().getComponents(), false, new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$transformAccessor$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    FirTypeRef returnTypeRef = owner.getReturnTypeRef();
                    if ((accessor.getReturnTypeRef() instanceof FirImplicitTypeRef) && !(returnTypeRef instanceof FirImplicitTypeRef)) {
                        accessor.replaceReturnTypeRef(returnTypeRef);
                    }
                    FirPropertyAccessor firPropertyAccessor = accessor;
                    if (!(firPropertyAccessor instanceof FirDefaultPropertyAccessor) && firPropertyAccessor.getBody() != null) {
                        this.transformFunctionWithGivenSignature(accessor);
                    } else {
                        this.transformFunction((FirFunction) accessor, (ResolutionMode) ResolutionMode.ContextIndependent.INSTANCE);
                    }
                }
            }, 8, null);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(accessor, th);
            throw null;
        }
    }

    static /* synthetic */ FirDeclarationStatus resolveStatus$default(FirDeclarationsResolveTransformer firDeclarationsResolveTransformer, FirDeclaration firDeclaration, FirClass firClass, FirProperty firProperty, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resolveStatus");
        }
        if ((i & 1) != 0) {
            firClass = null;
        }
        if ((i & 2) != 0) {
            firProperty = null;
        }
        return firDeclarationsResolveTransformer.resolveStatus(firDeclaration, firClass, firProperty);
    }

    private final FirDeclarationStatus resolveStatus(FirDeclaration firDeclaration, FirClass firClass, FirProperty firProperty) {
        return this.statusResolver.resolveStatus(firDeclaration, firClass instanceof FirRegularClass ? (FirRegularClass) firClass : null, firProperty, getTransformer().getContext().getContainerIfAny() != null && firClass == null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            BodyResolveContext context = getTransformer().getContext();
            context.getContainingClassDeclarations().add(regularClass);
            try {
                if (regularClass.getSymbol().getClassId().isLocal() && !getTransformer().getContext().getTargetedLocalClasses().contains(regularClass)) {
                    return LocalClassesResolutionKt.runAllPhasesForLocalClass(regularClass, getTransformer(), getTransformer().getComponents(), data, getTransformer().getFirResolveContextCollector());
                }
                doTransformTypeParameters(regularClass);
                return doTransformRegularClass(regularClass, data);
            } finally {
                context.getContainingClassDeclarations().removeLast();
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(regularClass, th);
            throw null;
        }
    }

    public final FirScript withScript(FirScript script, final Function0<? extends FirScript> action) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(action, "action");
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().enterScript(script);
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = this;
        FirScript firScript = (FirScript) firDeclarationsResolveTransformer2.getTransformer().getContext().withScopesForScript(script, firDeclarationsResolveTransformer2.getTransformer().getComponents(), new Function0<FirScript>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$withScript$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirScript invoke() {
                return action.invoke();
            }
        });
        firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().exitScript();
        return firScript;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirScript transformScript(final FirScript script, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(data, "data");
        return withScript(script, new Function0<FirScript>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer.transformScript.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirScript invoke() {
                FirDeclaration firDeclarationTransformDeclarationContent = FirDeclarationsResolveTransformer.this.transformDeclarationContent(script, data);
                Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirScript");
                return (FirScript) firDeclarationTransformDeclarationContent;
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirCodeFragment transformCodeFragment(final FirCodeFragment codeFragment, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(codeFragment, "codeFragment");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().enterCodeFragment(codeFragment);
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = this;
        firDeclarationsResolveTransformer2.getTransformer().getContext().withScopesForCodeFragment(codeFragment, firDeclarationsResolveTransformer2.getTransformer().getComponents(), new Function0<FirStatement>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer.transformCodeFragment.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirStatement invoke() {
                return FirDeclarationsResolveTransformer.this.transformBlock(codeFragment.getBlock(), data);
            }
        });
        firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().exitCodeFragment();
        return codeFragment;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (typeAlias.getSymbol().getClassId().isLocal() && !getTransformer().getContext().getTargetedLocalClasses().contains(typeAlias)) {
                return (FirTypeAlias) LocalClassesResolutionKt.runAllPhasesForLocalClass(typeAlias, getTransformer(), getTransformer().getComponents(), data, getTransformer().getFirResolveContextCollector());
            }
            doTransformTypeParameters(typeAlias);
            typeAlias.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            FirResolveContextCollector firResolveContextCollector = getTransformer().getFirResolveContextCollector();
            if (firResolveContextCollector != null) {
                firResolveContextCollector.addDeclarationContext(typeAlias, getTransformer().getContext());
            }
            typeAlias.transformExpandedTypeRef(getTransformer(), data);
            return typeAlias;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(typeAlias, th);
            throw null;
        }
    }

    protected final FirRegularClass doTransformRegularClass(final FirRegularClass regularClass, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        return withRegularClass(regularClass, new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer.doTransformRegularClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirRegularClass invoke() {
                FirDeclaration firDeclarationTransformDeclarationContent = FirDeclarationsResolveTransformer.this.transformDeclarationContent(regularClass, data);
                Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirRegularClass");
                return (FirRegularClass) firDeclarationTransformDeclarationContent;
            }
        });
    }

    public FirRegularClass withRegularClass(FirRegularClass regularClass, final Function0<? extends FirRegularClass> action) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(action, "action");
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().enterClass(regularClass, getTransformer().getPreserveCFGForClasses());
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = this;
        FirRegularClass firRegularClass = (FirRegularClass) firDeclarationsResolveTransformer2.getTransformer().getContext().withRegularClass(regularClass, firDeclarationsResolveTransformer2.getTransformer().getComponents(), new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$withRegularClass$result$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirRegularClass invoke() {
                return action.invoke();
            }
        });
        ControlFlowGraph controlFlowGraphExitClass = firDeclarationsResolveTransformer.getComponents().getDataFlowAnalyzer().exitClass();
        if (controlFlowGraphExitClass != null) {
            firRegularClass.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(controlFlowGraphExitClass, null, 2, null));
        }
        return firRegularClass;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObject(final FirAnonymousObject anonymousObject, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (!getTransformer().getContext().getTargetedLocalClasses().contains(anonymousObject)) {
                return LocalClassesResolutionKt.runAllPhasesForLocalClass(anonymousObject, getTransformer(), getTransformer().getComponents(), data, getTransformer().getFirResolveContextCollector());
            }
            if (anonymousObject.getControlFlowGraphReference() != null) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            getComponents().getDataFlowAnalyzer().enterClass(anonymousObject, !getImplicitTypeOnly());
            final BodyResolveContext context = getTransformer().getContext();
            FirAnonymousObject firAnonymousObject = (FirAnonymousObject) context.withScopesForClass(anonymousObject, getTransformer().getComponents(), new Function0<FirAnonymousObject>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$transformAnonymousObject$lambda$35$$inlined$withAnonymousObject$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirAnonymousObject invoke() {
                    BodyResolveContext bodyResolveContext = context;
                    bodyResolveContext.getContainers().add(anonymousObject);
                    try {
                        FirDeclaration firDeclarationTransformDeclarationContent = this.transformDeclarationContent(anonymousObject, data);
                        Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirAnonymousObject");
                        return (FirAnonymousObject) firDeclarationTransformDeclarationContent;
                    } finally {
                        bodyResolveContext.getContainers().removeLast();
                    }
                }
            });
            ControlFlowGraph controlFlowGraphExitClass = getComponents().getDataFlowAnalyzer().exitClass();
            if (controlFlowGraphExitClass != null) {
                firAnonymousObject.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(controlFlowGraphExitClass, null, 2, null));
            }
            return firAnonymousObject;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousObject, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(final FirSimpleFunction simpleFunction, ResolutionMode data) {
        FirSimpleFunction firSimpleFunction;
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (getBodyResolved(simpleFunction)) {
                return simpleFunction;
            }
            if (!(simpleFunction.getReturnTypeRef() instanceof FirImplicitTypeRef) && getImplicitTypeOnly()) {
                return simpleFunction;
            }
            FirDeclaration containerIfAny = getTransformer().getContext().getContainerIfAny();
            BodyResolveContext context = getTransformer().getContext();
            FirSession session2 = getSession();
            if (!(context.getContainerIfAny() instanceof FirClass)) {
                context.storeFunction(simpleFunction, session2);
            }
            if (simpleFunction.getTypeParameters().isEmpty()) {
                context.getContainers().add(simpleFunction);
                try {
                    doTransformTypeParameters(simpleFunction);
                    if (containerIfAny != null && !(containerIfAny instanceof FirClass)) {
                        prepareSignatureForBodyResolve(simpleFunction);
                        simpleFunction.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, simpleFunction, null, null, 3, null)));
                        if (!Intrinsics.areEqual(simpleFunction.getContractDescription(), FirEmptyContractDescription.INSTANCE)) {
                            FirContractResolveTransformerAdapterKt.runContractResolveForFunction(simpleFunction, getSession(), getComponents().getScopeSession(), getTransformer().getContext());
                        }
                    }
                    firSimpleFunction = (FirSimpleFunction) getTransformer().getContext().forFunctionBody(simpleFunction, getTransformer().getComponents(), new Function0<FirSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$transformSimpleFunction$1$1$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public final FirSimpleFunction invoke() {
                            FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this.this$0;
                            FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = firDeclarationsResolveTransformer;
                            FirSimpleFunction firSimpleFunction2 = simpleFunction;
                            boolean implicitTypeOnly = firDeclarationsResolveTransformer2.getImplicitTypeOnly();
                            if (implicitTypeOnly) {
                                firDeclarationsResolveTransformer2.setImplicitTypeOnly$resolve(false);
                            }
                            try {
                                return (FirSimpleFunction) firDeclarationsResolveTransformer.transformFunctionWithGivenSignature(firSimpleFunction2);
                            } finally {
                                if (implicitTypeOnly) {
                                    firDeclarationsResolveTransformer2.setImplicitTypeOnly$resolve(true);
                                }
                            }
                        }
                    });
                } finally {
                    context.getContainers().removeLast();
                }
            } else {
                FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(simpleFunction);
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                    context.getContainers().add(simpleFunction);
                    try {
                        doTransformTypeParameters(simpleFunction);
                        if (containerIfAny != null && !(containerIfAny instanceof FirClass)) {
                            prepareSignatureForBodyResolve(simpleFunction);
                            simpleFunction.transformStatus((FirTransformer<? super ResolutionMode>) this, ResolutionModeKt.mode(resolveStatus$default(this, simpleFunction, null, null, 3, null)));
                            if (!Intrinsics.areEqual(simpleFunction.getContractDescription(), FirEmptyContractDescription.INSTANCE)) {
                                FirContractResolveTransformerAdapterKt.runContractResolveForFunction(simpleFunction, getSession(), getComponents().getScopeSession(), getTransformer().getContext());
                            }
                        }
                        firSimpleFunction = (FirSimpleFunction) getTransformer().getContext().forFunctionBody(simpleFunction, getTransformer().getComponents(), new Function0<FirSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer$transformSimpleFunction$1$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final FirSimpleFunction invoke() {
                                FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this.this$0;
                                FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = firDeclarationsResolveTransformer;
                                FirSimpleFunction firSimpleFunction2 = simpleFunction;
                                boolean implicitTypeOnly = firDeclarationsResolveTransformer2.getImplicitTypeOnly();
                                if (implicitTypeOnly) {
                                    firDeclarationsResolveTransformer2.setImplicitTypeOnly$resolve(false);
                                }
                                try {
                                    return (FirSimpleFunction) firDeclarationsResolveTransformer.transformFunctionWithGivenSignature(firSimpleFunction2);
                                } finally {
                                    if (implicitTypeOnly) {
                                        firDeclarationsResolveTransformer2.setImplicitTypeOnly$resolve(true);
                                    }
                                }
                            }
                        });
                        context.getContainers().removeLast();
                        context.replaceTowerDataContext(towerDataContext);
                    } catch (Throwable th) {
                        context.getContainers().removeLast();
                        throw th;
                    }
                } catch (Throwable th2) {
                    context.replaceTowerDataContext(towerDataContext);
                    throw th2;
                }
            }
            return firSimpleFunction;
        } catch (Throwable th3) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(simpleFunction, th3);
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <F extends FirFunction> F transformFunctionWithGivenSignature(F function) {
        FirErrorTypeRef firErrorTypeRefMo6809build;
        List<FirStatement> statements;
        FirStatement firStatementTransformFunction = transformFunction((FirFunction) function, (ResolutionMode) ResolutionMode.ContextIndependent.INSTANCE);
        Intrinsics.checkNotNull(firStatementTransformFunction, "null cannot be cast to non-null type F of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer.transformFunctionWithGivenSignature");
        F f = (F) firStatementTransformFunction;
        FirBlock body = f.getBody();
        if (f.getReturnTypeRef() instanceof FirImplicitTypeRef) {
            FirSimpleFunction firSimpleFunction = function instanceof FirSimpleFunction ? (FirSimpleFunction) function : null;
            FirStatement firStatement = (body == null || (statements = body.getStatements()) == null) ? null : (FirStatement) CollectionsKt.singleOrNull((List) statements);
            FirReturnExpression firReturnExpression = firStatement instanceof FirReturnExpression ? (FirReturnExpression) firStatement : null;
            FirExpression result = firReturnExpression != null ? firReturnExpression.getResult() : null;
            if ((result != null ? result.getTypeRef() : null) instanceof FirResolvedTypeRef) {
                firErrorTypeRefMo6809build = UtilsKt.copyWithNewSource(DeclarationApproximationUtilsKt.approximateDeclarationType$default(result.getTypeRef(), getSession(), firSimpleFunction != null ? visibilityForApproximation(firSimpleFunction) : null, firSimpleFunction != null && Intrinsics.areEqual(firSimpleFunction.getStatus().getVisibility(), Visibilities.Local.INSTANCE), firSimpleFunction != null && firSimpleFunction.getStatus().isInline(), false, 16, null), f.getReturnTypeRef().getSource());
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(f.getReturnTypeRef().getSource());
                firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("empty body", DiagnosticKind.Other));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            }
            f.transformReturnTypeRef(getTransformer(), ResolutionModeKt.withExpectedType$default(firErrorTypeRefMo6809build, false, 2, (Object) null));
        }
        return f;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunction(FirFunction function, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (getBodyResolved(function)) {
                return function;
            }
            getComponents().getDataFlowAnalyzer().enterFunction(function);
            FirAnnotationContainer firAnnotationContainerTransformDeclarationContent = transformDeclarationContent(function, data);
            Intrinsics.checkNotNull(firAnnotationContainerTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirFunction");
            FirFunction firFunction = (FirFunction) firAnnotationContainerTransformDeclarationContent;
            firFunction.replaceControlFlowGraphReference(getComponents().getDataFlowAnalyzer().exitFunction(firFunction));
            Intrinsics.checkNotNull(firAnnotationContainerTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
            return (FirStatement) firAnnotationContainerTransformDeclarationContent;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(function, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (getImplicitTypeOnly()) {
                return constructor;
            }
            FirDeclaration containerIfAny = getTransformer().getContext().getContainerIfAny();
            FirRegularClass firRegularClass = containerIfAny instanceof FirRegularClass ? (FirRegularClass) containerIfAny : null;
            if (constructor.getIsPrimary()) {
                if ((firRegularClass != null ? firRegularClass.getClassKind() : null) == ClassKind.ANNOTATION_CLASS) {
                    FirExpressionsResolveTransformer expressionsTransformer = getTransformer().getExpressionsTransformer();
                    if (expressionsTransformer != null) {
                        expressionsTransformer.setEnableArrayOfCallTransformation(true);
                    }
                    try {
                        return doTransformConstructor(constructor, data);
                    } finally {
                        FirExpressionsResolveTransformer expressionsTransformer2 = getTransformer().getExpressionsTransformer();
                        if (expressionsTransformer2 != null) {
                            expressionsTransformer2.setEnableArrayOfCallTransformation(false);
                        }
                    }
                }
            }
            return doTransformConstructor(constructor, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(constructor, th);
            throw null;
        }
    }

    private final FirConstructor doTransformConstructor(final FirConstructor constructor, final ResolutionMode data) {
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        FirDeclaration containerIfAny = firDeclarationsResolveTransformer.getTransformer().getContext().getContainerIfAny();
        FirRegularClass firRegularClass = containerIfAny instanceof FirRegularClass ? (FirRegularClass) containerIfAny : null;
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = this;
        FirConstructor firConstructor = constructor;
        firDeclarationsResolveTransformer2.getComponents().getDataFlowAnalyzer().enterFunction(firConstructor);
        BodyResolveContext context = firDeclarationsResolveTransformer.getTransformer().getContext();
        context.getContainers().add(constructor);
        try {
            constructor.transformTypeParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            BodyResolveContext context2 = getTransformer().getContext();
            FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = getTransformer().getComponents();
            FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
            FirTowerDataMode towerDataMode = context2.getTowerDataMode();
            try {
                context2.setTowerDataMode(firTowerDataMode);
                if (constructor.getIsPrimary()) {
                    FirLocalScope primaryConstructorAllParametersScope = context2.getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope == null) {
                        constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                    } else {
                        FirTowerDataContext towerDataContext = context2.getTowerDataContext();
                        try {
                            context2.addLocalScope(primaryConstructorAllParametersScope);
                            FirConstructor firConstructorTransformValueParameters = constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                            context2.replaceTowerDataContext(towerDataContext);
                            if (firConstructorTransformValueParameters == null) {
                                constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                            }
                        } catch (Throwable th) {
                            context2.replaceTowerDataContext(towerDataContext);
                            throw th;
                        }
                    }
                } else {
                    context2.addInaccessibleImplicitReceiverValue(firRegularClass, components);
                    FirTowerDataContext towerDataContext2 = context2.getTowerDataContext();
                    try {
                        context2.addLocalScope(context2.buildSecondaryConstructorParametersScope(constructor, components.getSession()));
                        Iterator<T> it = constructor.getValueParameters().iterator();
                        while (it.hasNext()) {
                            context2.storeVariable((FirValueParameter) it.next(), components.getSession());
                        }
                        constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                        context2.replaceTowerDataContext(towerDataContext2);
                    } catch (Throwable th2) {
                        context2.replaceTowerDataContext(towerDataContext2);
                        throw th2;
                    }
                }
                context2.setTowerDataMode(towerDataMode);
                constructor.transformDelegatedConstructor(getTransformer(), data);
                context.getContainers().removeLast();
                constructor.replaceControlFlowGraphReference(firDeclarationsResolveTransformer2.getComponents().getDataFlowAnalyzer().exitFunction(firConstructor));
                return constructor;
            } catch (Throwable th3) {
                context2.setTowerDataMode(towerDataMode);
                throw th3;
            }
        } catch (Throwable th4) {
            context.getContainers().removeLast();
            throw th4;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformMultiDelegatedConstructorCall(FirMultiDelegatedConstructorCall multiDelegatedConstructorCall, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(multiDelegatedConstructorCall, "multiDelegatedConstructorCall");
        Intrinsics.checkNotNullParameter(data, "data");
        multiDelegatedConstructorCall.transformChildren(this, data);
        return super.transformMultiDelegatedConstructorCall(multiDelegatedConstructorCall, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            if (getImplicitTypeOnly()) {
                return anonymousInitializer;
            }
            getComponents().getDataFlowAnalyzer().enterInitBlock(anonymousInitializer);
            BodyResolveContext context = getTransformer().getContext();
            FirSession session2 = getSession();
            FirTowerDataContext towerDataContext = context.getTowerDataContext();
            try {
                FirLocalScope primaryConstructorPureParametersScope = context.getPrimaryConstructorPureParametersScope();
                if (primaryConstructorPureParametersScope != null) {
                    context.addLocalScope(primaryConstructorPureParametersScope);
                }
                context.addLocalScope(new FirLocalScope(session2));
                context.getContainers().add(anonymousInitializer);
                try {
                    FirDeclaration firDeclarationTransformDeclarationContent = transformDeclarationContent(anonymousInitializer, ResolutionMode.ContextIndependent.INSTANCE);
                    Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer");
                    FirAnonymousInitializer firAnonymousInitializer = (FirAnonymousInitializer) firDeclarationTransformDeclarationContent;
                    firAnonymousInitializer.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(getComponents().getDataFlowAnalyzer().exitInitBlock(), null, 2, null));
                    context.getContainers().removeLast();
                    context.replaceTowerDataContext(towerDataContext);
                    return firAnonymousInitializer;
                } catch (Throwable th) {
                    context.getContainers().removeLast();
                    throw th;
                }
            } catch (Throwable th2) {
                context.replaceTowerDataContext(towerDataContext);
                throw th2;
            }
        } catch (Throwable th3) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousInitializer, th3);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            getComponents().getDataFlowAnalyzer().enterValueParameter(valueParameter);
            BodyResolveContext context = getTransformer().getContext();
            FirSession session2 = getSession();
            if (!valueParameter.getName().isSpecial() || !Intrinsics.areEqual(valueParameter.getName(), SpecialNames.UNDERSCORE_FOR_UNUSED_VAR)) {
                context.storeVariable(valueParameter, session2);
            }
            context.getContainers().add(valueParameter);
            try {
                FirDeclaration firDeclarationTransformDeclarationContent = transformDeclarationContent(valueParameter, ResolutionModeKt.withExpectedType$default(valueParameter.getReturnTypeRef(), false, 2, (Object) null));
                Intrinsics.checkNotNull(firDeclarationTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirValueParameter");
                FirValueParameter firValueParameter = (FirValueParameter) firDeclarationTransformDeclarationContent;
                context.getContainers().removeLast();
                ControlFlowGraph controlFlowGraphExitValueParameter = getComponents().getDataFlowAnalyzer().exitValueParameter(firValueParameter);
                if (controlFlowGraphExitValueParameter != null) {
                    firValueParameter.replaceControlFlowGraphReference(new FirControlFlowGraphReferenceImpl(controlFlowGraphExitValueParameter, null, 2, null));
                }
                return firValueParameter;
            } catch (Throwable th) {
                context.getContainers().removeLast();
                throw th;
            }
        } catch (Throwable th2) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(valueParameter, th2);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousFunction(FirAnonymousFunction anonymousFunction, ResolutionMode data) {
        FirAnonymousFunction firAnonymousFunctionTransformAnonymousFunctionWithExpectedType;
        FirResolvedTypeRef returnTypeRef;
        Intrinsics.checkNotNullParameter(anonymousFunction, "anonymousFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            anonymousFunction.transformAnnotations((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
            if (!(data instanceof ResolutionMode.LambdaResolution)) {
                anonymousFunction.transformReturnTypeRef((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                anonymousFunction.transformReceiverParameter((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                Iterator<T> it = anonymousFunction.getValueParameters().iterator();
                while (it.hasNext()) {
                    ((FirValueParameter) it.next()).transformReturnTypeRef((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
                }
            }
            if (!Intrinsics.areEqual(anonymousFunction.getContractDescription(), FirEmptyContractDescription.INSTANCE)) {
                FirContractResolveTransformerAdapterKt.runContractResolveForFunction(anonymousFunction, getSession(), getComponents().getScopeSession(), getTransformer().getContext());
            }
            if (data instanceof ResolutionMode.ContextDependent) {
                getTransformer().getContext().storeContextForAnonymousFunction(anonymousFunction);
                return anonymousFunction;
            }
            if (data instanceof ResolutionMode.LambdaResolution) {
                FirResolvedTypeRef expectedReturnTypeRef = ((ResolutionMode.LambdaResolution) data).getExpectedReturnTypeRef();
                if (expectedReturnTypeRef != null) {
                    returnTypeRef = expectedReturnTypeRef;
                } else {
                    returnTypeRef = anonymousFunction.getReturnTypeRef();
                    if (returnTypeRef instanceof FirImplicitTypeRef) {
                        returnTypeRef = null;
                    }
                }
                firAnonymousFunctionTransformAnonymousFunctionWithExpectedType = transformAnonymousFunctionBody(anonymousFunction, returnTypeRef, data);
            } else if (data instanceof ResolutionMode.WithExpectedType) {
                firAnonymousFunctionTransformAnonymousFunctionWithExpectedType = transformAnonymousFunctionWithExpectedType(anonymousFunction, ((ResolutionMode.WithExpectedType) data).getExpectedTypeRef(), data);
            } else {
                boolean z = true;
                if (!(data instanceof ResolutionMode.ContextIndependent ? true : data instanceof ResolutionMode.AssignmentLValue)) {
                    z = data instanceof ResolutionMode.ReceiverResolution;
                }
                if (z) {
                    firAnonymousFunctionTransformAnonymousFunctionWithExpectedType = transformAnonymousFunctionWithExpectedType(anonymousFunction, FirImplicitTypeRefImplWithoutSource.INSTANCE, data);
                } else {
                    if (data instanceof ResolutionMode.WithStatus) {
                        throw new AssertionError("Should not be here in WithStatus/WithExpectedTypeFromCast mode");
                    }
                    throw new NoWhenBranchMatchedException();
                }
            }
            return firAnonymousFunctionTransformAnonymousFunctionWithExpectedType;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(anonymousFunction, th);
            throw null;
        }
    }

    private final FirAnonymousFunction transformAnonymousFunctionBody(final FirAnonymousFunction anonymousFunction, final FirTypeRef expectedReturnTypeRef, ResolutionMode data) {
        FirTypeRef typeRef = anonymousFunction.getTypeRef();
        FirDeclarationsResolveTransformer firDeclarationsResolveTransformer = this;
        FirAnonymousFunction firAnonymousFunction = (FirAnonymousFunction) firDeclarationsResolveTransformer.getTransformer().getContext().withAnonymousFunction(anonymousFunction, firDeclarationsResolveTransformer.getTransformer().getComponents(), data, new Function0<FirAnonymousFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer.transformAnonymousFunctionBody.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirAnonymousFunction invoke() {
                ResolutionMode.ContextDependent.Companion companionWithExpectedType$default;
                FirDeclarationsResolveTransformer firDeclarationsResolveTransformer2 = FirDeclarationsResolveTransformer.this;
                FirDeclarationsResolveTransformer firDeclarationsResolveTransformer3 = firDeclarationsResolveTransformer2;
                FirAnonymousFunction firAnonymousFunction2 = anonymousFunction;
                FirTypeRef firTypeRef = expectedReturnTypeRef;
                boolean implicitTypeOnly = firDeclarationsResolveTransformer3.getImplicitTypeOnly();
                if (implicitTypeOnly) {
                    firDeclarationsResolveTransformer3.setImplicitTypeOnly$resolve(false);
                }
                try {
                    FirAnonymousFunction firAnonymousFunction3 = firAnonymousFunction2;
                    if (firTypeRef == null || (companionWithExpectedType$default = ResolutionModeKt.withExpectedType$default(firTypeRef, false, 2, (Object) null)) == null) {
                        companionWithExpectedType$default = ResolutionMode.ContextDependent.INSTANCE;
                    }
                    FirStatement firStatementTransformFunction = firDeclarationsResolveTransformer2.transformFunction((FirFunction) firAnonymousFunction3, companionWithExpectedType$default);
                    Intrinsics.checkNotNull(firStatementTransformFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction");
                    return (FirAnonymousFunction) firStatementTransformFunction;
                } finally {
                    if (implicitTypeOnly) {
                        firDeclarationsResolveTransformer3.setImplicitTypeOnly$resolve(true);
                    }
                }
            }
        });
        firAnonymousFunction.replaceTypeRef(typeRef);
        return firAnonymousFunction;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0136  */
    /* JADX WARN: Code duplicated, block: B:39:0x0167  */
    private final FirAnonymousFunction transformAnonymousFunctionWithExpectedType(FirAnonymousFunction anonymousFunction, FirTypeRef expectedTypeRef, ResolutionMode data) {
        List<FirValueParameter> listObtainValueParametersFromExpectedType;
        ConeKotlinType receiver;
        FirReceiverParameter receiverParameter;
        ConeKotlinType returnType;
        List<ConeKotlinType> contextReceivers;
        boolean z = expectedTypeRef instanceof FirResolvedTypeRef;
        FirResolvedTypeRef firResolvedTypeRef = z ? (FirResolvedTypeRef) expectedTypeRef : null;
        ResolvedLambdaAtom resolvedLambdaAtomExtractLambdaInfoFromFunctionType = firResolvedTypeRef != null ? InferenceUtilsKt.extractLambdaInfoFromFunctionType(firResolvedTypeRef.getType(), anonymousFunction, null, getTransformer().getComponents(), null, true) : null;
        if (resolvedLambdaAtomExtractLambdaInfoFromFunctionType != null) {
            listObtainValueParametersFromExpectedType = obtainValueParametersFromResolvedLambdaAtom(resolvedLambdaAtomExtractLambdaInfoFromFunctionType, anonymousFunction);
        } else {
            FirResolvedTypeRef firResolvedTypeRef2 = z ? (FirResolvedTypeRef) expectedTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            listObtainValueParametersFromExpectedType = obtainValueParametersFromExpectedType(type, anonymousFunction);
        }
        FirAnonymousFunctionBuilder firAnonymousFunctionBuilder = new FirAnonymousFunctionBuilder();
        firAnonymousFunctionBuilder.setSource(anonymousFunction.getSource());
        firAnonymousFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(anonymousFunction));
        firAnonymousFunctionBuilder.getAnnotations().addAll(anonymousFunction.getAnnotations());
        firAnonymousFunctionBuilder.setModuleData(anonymousFunction.getModuleData());
        firAnonymousFunctionBuilder.setOrigin(anonymousFunction.getOrigin());
        firAnonymousFunctionBuilder.setAttributes(anonymousFunction.getAttributes().copy());
        firAnonymousFunctionBuilder.setStatus(anonymousFunction.getStatus());
        firAnonymousFunctionBuilder.setReturnTypeRef(anonymousFunction.getReturnTypeRef());
        firAnonymousFunctionBuilder.setReceiverParameter(anonymousFunction.getReceiverParameter());
        firAnonymousFunctionBuilder.setDeprecationsProvider(anonymousFunction.getDeprecationsProvider());
        firAnonymousFunctionBuilder.setContainerSource(anonymousFunction.getContainerSource());
        firAnonymousFunctionBuilder.setDispatchReceiverType(anonymousFunction.getDispatchReceiverType());
        firAnonymousFunctionBuilder.getContextReceivers().addAll(anonymousFunction.getContextReceivers());
        firAnonymousFunctionBuilder.setControlFlowGraphReference(anonymousFunction.getControlFlowGraphReference());
        firAnonymousFunctionBuilder.getValueParameters().addAll(anonymousFunction.getValueParameters());
        firAnonymousFunctionBuilder.setBody(anonymousFunction.getBody());
        firAnonymousFunctionBuilder.setContractDescription(anonymousFunction.getContractDescription());
        firAnonymousFunctionBuilder.setSymbol(anonymousFunction.getSymbol());
        firAnonymousFunctionBuilder.setLabel(anonymousFunction.getLabel());
        firAnonymousFunctionBuilder.setInvocationKind(anonymousFunction.getInvocationKind());
        firAnonymousFunctionBuilder.setInlineStatus(anonymousFunction.getInlineStatus());
        firAnonymousFunctionBuilder.setLambda(anonymousFunction.getIsLambda());
        firAnonymousFunctionBuilder.setHasExplicitParameterList(anonymousFunction.getHasExplicitParameterList());
        firAnonymousFunctionBuilder.getTypeParameters().addAll(anonymousFunction.getTypeParameters());
        firAnonymousFunctionBuilder.setTypeRef(anonymousFunction.getTypeRef());
        FirReceiverParameter receiverParameter2 = anonymousFunction.getReceiverParameter();
        if (receiverParameter2 != null) {
            if (!Boolean.valueOf(!(receiverParameter2.getTypeRef() instanceof FirImplicitTypeRef)).booleanValue()) {
                receiverParameter2 = null;
            }
            if (receiverParameter2 == null) {
                if (resolvedLambdaAtomExtractLambdaInfoFromFunctionType != null) {
                    receiverParameter2 = null;
                } else {
                    receiverParameter2 = null;
                }
            }
        } else if (resolvedLambdaAtomExtractLambdaInfoFromFunctionType != null || (receiver = resolvedLambdaAtomExtractLambdaInfoFromFunctionType.getReceiver()) == null) {
            receiverParameter2 = null;
        } else {
            if (!Boolean.valueOf(!resolvedLambdaAtomExtractLambdaInfoFromFunctionType.getCoerceFirstParameterToExtensionReceiver()).booleanValue()) {
                receiver = null;
            }
            if (receiver == null || (receiverParameter = anonymousFunction.getReceiverParameter()) == null) {
                receiverParameter2 = null;
            } else {
                receiverParameter.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(receiverParameter.getTypeRef(), receiver, null, 2, null));
                receiverParameter2 = receiverParameter;
            }
        }
        firAnonymousFunctionBuilder.setReceiverParameter(receiverParameter2);
        firAnonymousFunctionBuilder.getContextReceivers().clear();
        List<FirContextReceiver> contextReceivers2 = firAnonymousFunctionBuilder.getContextReceivers();
        List<FirContextReceiver> contextReceivers3 = anonymousFunction.getContextReceivers();
        if (!Boolean.valueOf(!contextReceivers3.isEmpty()).booleanValue()) {
            contextReceivers3 = null;
        }
        if (contextReceivers3 == null) {
            if (resolvedLambdaAtomExtractLambdaInfoFromFunctionType == null || (contextReceivers = resolvedLambdaAtomExtractLambdaInfoFromFunctionType.getContextReceivers()) == null) {
                contextReceivers3 = null;
            } else {
                List<ConeKotlinType> list = contextReceivers;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (ConeKotlinType coneKotlinType : list) {
                    FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
                    FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                    firResolvedTypeRefBuilder.setType(coneKotlinType);
                    firContextReceiverBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
                    arrayList.add(firContextReceiverBuilder.build());
                }
                contextReceivers3 = arrayList;
            }
            if (contextReceivers3 == null) {
                contextReceivers3 = CollectionsKt.emptyList();
            }
        }
        contextReceivers2.addAll(contextReceivers3);
        firAnonymousFunctionBuilder.getValueParameters().clear();
        firAnonymousFunctionBuilder.getValueParameters().addAll(listObtainValueParametersFromExpectedType);
        FirAnonymousFunction firAnonymousFunctionTransformValueParameters = firAnonymousFunctionBuilder.mo6809build().transformValueParameters((FirTransformer<? super Object>) ImplicitToErrorTypeTransformer.INSTANCE, (Object) null);
        FirTypeRef returnTypeRef = firAnonymousFunctionTransformValueParameters.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef3 = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = firResolvedTypeRef3 == null ? (resolvedLambdaAtomExtractLambdaInfoFromFunctionType == null || (returnType = resolvedLambdaAtomExtractLambdaInfoFromFunctionType.getReturnType()) == null) ? null : CopyUtilsKt.resolvedTypeFromPrototype$default(firAnonymousFunctionTransformValueParameters.getReturnTypeRef(), returnType, null, 2, null) : firResolvedTypeRef3;
        FirAnonymousFunction firAnonymousFunctionTransformAnonymousFunctionBody = transformAnonymousFunctionBody(firAnonymousFunctionTransformValueParameters, firResolvedTypeRefResolvedTypeFromPrototype$default != null ? firResolvedTypeRefResolvedTypeFromPrototype$default : getTransformer().getComponents().getNoExpectedType(), data);
        if (firResolvedTypeRef3 == null) {
            firAnonymousFunctionTransformAnonymousFunctionBody.replaceReturnTypeRef(computeReturnTypeRef(firAnonymousFunctionTransformAnonymousFunctionBody, firResolvedTypeRefResolvedTypeFromPrototype$default));
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firAnonymousFunctionTransformAnonymousFunctionBody.getReturnTypeRef(), firAnonymousFunctionTransformAnonymousFunctionBody.getSource(), getTransformer().getContext().getFile().getSource());
            }
        }
        firAnonymousFunctionTransformAnonymousFunctionBody.replaceTypeRef(ResolveUtilsKt.constructFunctionTypeRef(firAnonymousFunctionTransformAnonymousFunctionBody, getSession(), resolvedLambdaAtomExtractLambdaInfoFromFunctionType != null ? resolvedLambdaAtomExtractLambdaInfoFromFunctionType.getExpectedFunctionTypeKind() : null));
        FirLookupTrackerComponent lookupTracker2 = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker2 != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker2, firAnonymousFunctionTransformAnonymousFunctionBody.getTypeRef(), firAnonymousFunctionTransformAnonymousFunctionBody.getSource(), getTransformer().getContext().getFile().getSource());
        }
        ResolveUtilsKt.addReturnToLastStatementIfNeeded(firAnonymousFunctionTransformAnonymousFunctionBody, getSession());
        return firAnonymousFunctionTransformAnonymousFunctionBody;
    }

    private final FirResolvedTypeRef computeReturnTypeRef(FirAnonymousFunction firAnonymousFunction, FirResolvedTypeRef firResolvedTypeRef) {
        FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default;
        ConeKotlinType type;
        Collection<FirAnonymousFunctionReturnExpressionInfo> collectionReturnExpressionsOfAnonymousFunction = getComponents().getDataFlowAnalyzer().returnExpressionsOfAnonymousFunction(firAnonymousFunction);
        if (firAnonymousFunction.getIsLambda() && firResolvedTypeRef != null && (type = firResolvedTypeRef.getType()) != null && ConeBuiltinTypeUtilsKt.isUnit(type)) {
            Collection<FirAnonymousFunctionReturnExpressionInfo> collection = collectionReturnExpressionsOfAnonymousFunction;
            if (!(collection instanceof Collection) || !collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    if (((FirAnonymousFunctionReturnExpressionInfo) it.next()).isExplicit()) {
                    }
                }
            }
            return firResolvedTypeRef;
        }
        Collection<FirAnonymousFunctionReturnExpressionInfo> collection2 = collectionReturnExpressionsOfAnonymousFunction;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            arrayList.add(((FirAnonymousFunctionReturnExpressionInfo) it2.next()).getExpression());
        }
        if (ResolveUtilsKt.shouldReturnUnit(firAnonymousFunction, arrayList)) {
            return getSession().getBuiltinTypes().getUnitType();
        }
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getSession());
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it3 = collection2.iterator();
        while (it3.hasNext()) {
            arrayList2.add(FirTypeUtilsKt.getConeType(((FirAnonymousFunctionReturnExpressionInfo) it3.next()).getExpression().getTypeRef()));
        }
        ConeKotlinType coneKotlinTypeCommonSuperTypeOrNull = TypeUtilsKt.commonSuperTypeOrNull(typeContext, arrayList2);
        return (coneKotlinTypeCommonSuperTypeOrNull == null || (firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firAnonymousFunction.getReturnTypeRef(), coneKotlinTypeCommonSuperTypeOrNull, null, 2, null)) == null) ? getSession().getBuiltinTypes().getUnitType() : firResolvedTypeRefResolvedTypeFromPrototype$default;
    }

    private final List<FirValueParameter> obtainValueParametersFromResolvedLambdaAtom(ResolvedLambdaAtom resolvedLambdaAtom, FirAnonymousFunction lambda) {
        List<ConeKotlinType> parameters;
        ConeKotlinType coneKotlinType = (ConeKotlinType) CollectionsKt.singleOrNull((List) resolvedLambdaAtom.getParameters());
        if (lambda.getValueParameters().isEmpty() && coneKotlinType != null) {
            Name name2 = StandardNames.IMPLICIT_LAMBDA_PARAMETER_NAME;
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
            KtSourceElement source = lambda.getSource();
            firValueParameterBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ItLambdaParameter.INSTANCE, 0, 0, 6, null) : null);
            firValueParameterBuilder.setContainingFunctionSymbol(resolvedLambdaAtom.getReference().getSymbol());
            firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
            firValueParameterBuilder.setReturnTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinType, null, null, 3, null));
            firValueParameterBuilder.setName(name2);
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name2));
            firValueParameterBuilder.setCrossinline(false);
            firValueParameterBuilder.setNoinline(false);
            firValueParameterBuilder.setVararg(false);
            return CollectionsKt.listOf(firValueParameterBuilder.mo6809build());
        }
        if (resolvedLambdaAtom.getCoerceFirstParameterToExtensionReceiver()) {
            ConeKotlinType receiver = resolvedLambdaAtom.getReceiver();
            if (receiver == null) {
                throw new IllegalStateException("Coercion to an extension function type, but no receiver found".toString());
            }
            parameters = CollectionsKt.plus((Collection) CollectionsKt.listOf(receiver), (Iterable) resolvedLambdaAtom.getParameters());
        } else {
            parameters = resolvedLambdaAtom.getParameters();
        }
        return obtainValueParametersFromExpectedParameterTypes(parameters, lambda);
    }

    private final List<FirValueParameter> obtainValueParametersFromExpectedType(ConeKotlinType expectedType, FirAnonymousFunction lambda) {
        if (expectedType == null) {
            return lambda.getValueParameters();
        }
        if (!FunctionalTypeUtilsKt.isNonReflectFunctionType(expectedType, getSession())) {
            return lambda.getValueParameters();
        }
        ConeTypeProjection[] typeArguments = expectedType.getTypeArguments();
        ArrayList arrayList = new ArrayList();
        for (ConeTypeProjection coneTypeProjection : typeArguments) {
            TypeArgumentListMarker type = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type == null) {
                type = getSession().getBuiltinTypes().getNullableAnyType().getType();
            }
            arrayList.add(type);
        }
        ArrayList arrayList2 = arrayList;
        CollectionsKt.removeLastOrNull(arrayList2);
        if (CompilerConeAttributesKt.isExtensionFunctionType(expectedType)) {
            CollectionsKt.removeFirstOrNull(arrayList2);
        }
        return obtainValueParametersFromExpectedParameterTypes(arrayList2, lambda);
    }

    private final List<FirValueParameter> obtainValueParametersFromExpectedParameterTypes(List<? extends ConeKotlinType> expectedTypeParameterTypes, FirAnonymousFunction lambda) {
        List<FirValueParameter> valueParameters = lambda.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            FirValueParameter firValueParameter = (FirValueParameter) obj;
            if (!(firValueParameter.getReturnTypeRef() instanceof FirResolvedTypeRef)) {
                FirTypeRef returnTypeRef = firValueParameter.getReturnTypeRef();
                ConeKotlinType coneKotlinType = expectedTypeParameterTypes.get(i);
                KtSourceElement source = firValueParameter.getSource();
                firValueParameter.replaceReturnTypeRef(CopyUtilsKt.resolvedTypeFromPrototype(returnTypeRef, coneKotlinType, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitReturnTypeOfLambdaValueParameter.INSTANCE, 0, 0, 6, null) : null));
            }
            arrayList.add(firValueParameter);
            i = i2;
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBackingField(FirBackingField backingField, ResolutionMode data) {
        ResolutionMode withExpectedType;
        FirExpression firExpressionUnwrapSmartcastExpression;
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        Intrinsics.checkNotNullParameter(data, "data");
        FirSession session = getSession();
        try {
            FirTypeRef expectedType = getExpectedType(data);
            if (backingField.getReturnTypeRef() instanceof FirResolvedTypeRef) {
                withExpectedType = ResolutionModeKt.withExpectedType$default(backingField.getReturnTypeRef(), false, 2, (Object) null);
            } else if (expectedType instanceof FirResolvedTypeRef) {
                withExpectedType = new ResolutionMode.WithExpectedType((FirResolvedTypeRef) expectedType, false, false, false, false, false, 46, null);
            } else if (expectedType != null) {
                withExpectedType = ResolutionMode.ContextIndependent.INSTANCE;
            } else {
                withExpectedType = ResolutionMode.ContextDependent.INSTANCE;
            }
            backingField.transformInitializer((FirTransformer<? super ResolutionMode>) getTransformer(), withExpectedType);
            backingField.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            if (!(backingField.getReturnTypeRef() instanceof FirErrorTypeRef) && !(backingField.getReturnTypeRef() instanceof FirResolvedTypeRef)) {
                if (!(backingField instanceof FirDefaultPropertyBackingField)) {
                    FirExpression initializer = backingField.getInitializer();
                    expectedType = (initializer == null || (firExpressionUnwrapSmartcastExpression = FirExpressionUtilKt.unwrapSmartcastExpression(initializer)) == null) ? null : firExpressionUnwrapSmartcastExpression.getTypeRef();
                }
                if (expectedType == null) {
                    FirAbstractBodyResolveTransformerDispatcher transformer = getTransformer();
                    FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                    firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic("Cannot infer variable type without an initializer", DiagnosticKind.InferenceError));
                    Unit unit = Unit.INSTANCE;
                    return backingField.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) transformer, ResolutionModeKt.withExpectedType$default((FirTypeRef) firErrorTypeRefBuilder.mo6809build(), false, 2, (Object) null));
                }
                return backingField.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(DeclarationApproximationUtilsKt.approximateDeclarationType$default(toExpectedTypeRef(expectedType), getSession(), visibilityForApproximation(backingField), false, false, false, 24, null), false, 2, (Object) null));
            }
            return backingField;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(backingField, th);
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void storeVariableReturnType(FirVariable variable) {
        FirTypeRef returnTypeRef;
        FirPropertyAccessor getter;
        FirErrorTypeRef firErrorTypeRefMo6809build;
        FirPropertyAccessor getter2;
        FirExpression initializer = variable.getInitializer();
        if (variable.getReturnTypeRef() instanceof FirImplicitTypeRef) {
            if (initializer == null) {
                returnTypeRef = (variable.getGetter() == null || (variable.getGetter() instanceof FirDefaultPropertyAccessor) || (getter = variable.getGetter()) == null) ? null : getter.getReturnTypeRef();
            } else {
                returnTypeRef = FirExpressionUtilKt.unwrapSmartcastExpression(initializer).getTypeRef();
            }
            FirAbstractBodyResolveTransformerDispatcher transformer = getTransformer();
            if (returnTypeRef == null || (firErrorTypeRefMo6809build = DeclarationApproximationUtilsKt.approximateDeclarationType$default(toExpectedTypeRef(returnTypeRef), getSession(), visibilityForApproximation(variable), isLocal(variable), false, false, 24, null)) == null) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setDiagnostic(new ConeLocalVariableNoTypeOrInitializer(variable));
                firErrorTypeRefBuilder.setSource(variable.getSource());
                Unit unit = Unit.INSTANCE;
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            }
            variable.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) transformer, ResolutionModeKt.withExpectedType$default(firErrorTypeRefMo6809build, false, 2, (Object) null));
            FirPropertyAccessor getter3 = variable.getGetter();
            if (!((getter3 != null ? getter3.getReturnTypeRef() : null) instanceof FirImplicitTypeRef) || (getter2 = variable.getGetter()) == null) {
                return;
            }
            getter2.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), ResolutionModeKt.withExpectedType$default(variable.getReturnTypeRef(), false, 2, (Object) null));
        }
    }

    private final boolean isLocal(FirVariable firVariable) {
        if (firVariable instanceof FirProperty) {
            return ((FirProperty) firVariable).getIsLocal();
        }
        return firVariable instanceof FirValueParameter;
    }

    private final FirResolvedTypeRef toExpectedTypeRef(FirTypeRef firTypeRef) {
        KtSourceElement ktSourceElementFakeElement$default;
        if (!(firTypeRef instanceof FirImplicitTypeRef)) {
            if (firTypeRef instanceof FirErrorTypeRef) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setDiagnostic(((FirErrorTypeRef) firTypeRef).getDiagnostic());
                KtSourceElement source = firTypeRef.getSource();
                if (source != null && (ktSourceElementFakeElement$default = KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null)) != null) {
                    firErrorTypeRefBuilder.setSource(ktSourceElementFakeElement$default);
                }
                return firErrorTypeRefBuilder.mo6809build();
            }
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(FirTypeUtilsKt.getConeType(firTypeRef));
            firResolvedTypeRefBuilder.getAnnotations().addAll(firTypeRef.getAnnotations());
            return firResolvedTypeRefBuilder.mo6809build();
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder2.setDiagnostic(new ConeSimpleDiagnostic("No result type for initializer", DiagnosticKind.InferenceError));
        return firErrorTypeRefBuilder2.mo6809build();
    }

    /* JADX INFO: compiled from: FirDeclarationsResolveTransformer.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J)\u0010\u0004\u001a\u0002H\u0005\"\b\b\u0000\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer$ImplicitToErrorTypeTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "()V", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformValueParameter", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ImplicitToErrorTypeTransformer extends FirTransformer<Object> {
        public static final ImplicitToErrorTypeTransformer INSTANCE = new ImplicitToErrorTypeTransformer();

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public <E extends FirElement> E transformElement(E element, Object data) {
            Intrinsics.checkNotNullParameter(element, "element");
            return element;
        }

        private ImplicitToErrorTypeTransformer() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformValueParameter(FirValueParameter valueParameter, Object data) {
            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
            FirSession session = valueParameter.getModuleData().getSession();
            try {
                if (valueParameter.getReturnTypeRef() instanceof FirImplicitTypeRef) {
                    FirTypeRef returnTypeRef = valueParameter.getReturnTypeRef();
                    ConeErrorType coneErrorType = new ConeErrorType(new ConeSimpleDiagnostic("No type for parameter", DiagnosticKind.ValueParameterWithNoTypeAnnotation), false, null, null, 14, null);
                    KtSourceElement source = valueParameter.getSource();
                    valueParameter.replaceReturnTypeRef(CopyUtilsKt.resolvedTypeFromPrototype(returnTypeRef, coneErrorType, source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ImplicitReturnTypeOfLambdaValueParameter.INSTANCE, 0, 0, 6, null) : null));
                }
                return valueParameter;
            } catch (Throwable th) {
                UtilsKt.getExceptionHandler(session).handleExceptionOnElementAnalysis(valueParameter, th);
                throw null;
            }
        }
    }

    private final boolean getInitializerResolved(FirVariable firVariable) {
        FirExpression initializer = firVariable.getInitializer();
        return (initializer == null || !(initializer.getTypeRef() instanceof FirResolvedTypeRef) || (initializer instanceof FirErrorExpression)) ? false : true;
    }

    protected final boolean getBodyResolved(FirFunction firFunction) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        if (!(firFunction.getBody() instanceof FirLazyBlock)) {
            FirBlock body = firFunction.getBody();
            if ((body != null ? body.getTypeRef() : null) instanceof FirResolvedTypeRef) {
                return true;
            }
        }
        return false;
    }
}
