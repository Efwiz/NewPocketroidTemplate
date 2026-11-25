package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.declarations.UnresolvedDeprecationProvider;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.builder.FirResolvedQualifierBuilder;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProviderImplKt;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProviderKt;
import org.jetbrains.kotlin.fir.extensions.FirRegisteredPluginAnnotationsKt;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.references.impl.FirSimpleNamedReference;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSpecificTypeResolverTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.ScopeClassDeclaration;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirAbstractImportingScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirPlaceholderProjection;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirStarProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirPlaceholderProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirStarProjectionBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;
import org.jetbrains.kotlin.fir.types.impl.FirQualifierPartImpl;
import org.jetbrains.kotlin.fir.types.impl.FirTypeArgumentListImpl;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: AbstractFirSpecificAnnotationResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000º\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 ¤\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\b£\u0001¤\u0001¥\u0001¦\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\u0016\u0010?\u001a\u00020@2\u000e\u0010A\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\"J\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020\u000b0\"2\u0006\u0010C\u001a\u00020\u000bJ\u0010\u0010D\u001a\u00020@2\u0006\u0010E\u001a\u00020FH\u0002J\u000e\u0010D\u001a\u00020@2\u0006\u0010G\u001a\u00020HJ\u000e\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020JJ0\u0010L\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020O2\u000e\b\u0002\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0\n2\u000e\b\u0002\u0010R\u001a\b\u0012\u0004\u0012\u00020Q0\nJ\u0010\u0010S\u001a\u00020@2\u0006\u0010T\u001a\u00020UH\u0002J0\u0010V\u001a\u00020@2\u0006\u0010W\u001a\u00020X2\f\u0010Y\u001a\b\u0012\u0004\u0012\u00020@0Z2\f\u0010[\u001a\b\u0012\u0004\u0012\u00020@0ZH\u0086\bø\u0001\u0000J\u000e\u0010\\\u001a\u0002072\u0006\u0010]\u001a\u00020JJ\u0010\u0010^\u001a\u0002072\u0006\u0010_\u001a\u00020\u000bH&J\u001a\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020Q2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010d\u001a\u00020a2\u0006\u0010e\u001a\u00020f2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u0016\u0010d\u001a\u00020@2\u0006\u0010e\u001a\u00020f2\u0006\u0010]\u001a\u00020JJ\u001a\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020h2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010j\u001a\u00020a2\u0006\u0010k\u001a\u00020l2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J-\u0010m\u001a\u00020a\"\f\b\u0000\u0010n*\u00020F*\u00020a2\u0006\u0010E\u001a\u0002Hn2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0002¢\u0006\u0002\u0010oJ-\u0010Y\u001a\u0002Hp\"\u0004\b\u0000\u0010p2\u0006\u0010C\u001a\u00020\u000b2\f\u0010q\u001a\b\u0012\u0004\u0012\u0002Hp0ZH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010rJ\u001a\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020t2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010v\u001a\u00020\u000b2\u0006\u0010_\u001a\u00020\u000b2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J)\u0010w\u001a\u0002Hx\"\b\b\u0000\u0010x*\u00020y2\u0006\u0010z\u001a\u0002Hx2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010{J\u001a\u0010|\u001a\u00020a2\u0006\u0010}\u001a\u00020~2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010\u007f\u001a\u00020a2\b\u0010\u0080\u0001\u001a\u00030\u0081\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001e\u0010\u0082\u0001\u001a\u00030\u0083\u00012\b\u0010\u0084\u0001\u001a\u00030\u0083\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010\u0085\u0001\u001a\u00020a2\u0006\u0010N\u001a\u00020O2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u0086\u0001\u001a\u00020a2\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001b\u0010\u0089\u0001\u001a\u00020a2\u0006\u0010W\u001a\u00020X2\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001e\u0010\u008a\u0001\u001a\u00030\u008b\u00012\b\u0010\u008c\u0001\u001a\u00030\u008b\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001e\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\u0010\u008f\u0001\u001a\u00030\u008e\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0007\u0010]\u001a\u00030\u0091\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J\u001d\u0010\u0092\u0001\u001a\u00020a2\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\b\u0010c\u001a\u0004\u0018\u00010\u0002H\u0016J2\u0010\u0095\u0001\u001a\u0002Hn\"\u0004\b\u0000\u0010n2\b\u0010\u0084\u0001\u001a\u00030\u0083\u00012\r\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u0002Hn0ZH\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0097\u0001J\u001f\u0010\u0098\u0001\u001a\u00020@2\b\u0010\u0084\u0001\u001a\u00030\u0083\u00012\f\u0010q\u001a\b\u0012\u0004\u0012\u00020@0ZJ2\u0010\u0099\u0001\u001a\u0002Hn\"\u0004\b\u0000\u0010n2\b\u0010\u0084\u0001\u001a\u00030\u0083\u00012\r\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u0002Hn0ZH\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0097\u0001J#\u0010\u009a\u0001\u001a\u00020@2\u0006\u0010W\u001a\u00020X2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020@0ZH\u0086\bø\u0001\u0000J\u000f\u0010\u009b\u0001\u001a\u00030\u009c\u0001*\u00030\u009c\u0001H\u0002J\u000f\u0010\u009b\u0001\u001a\u00030\u009d\u0001*\u00030\u009d\u0001H\u0002J)\u0010\u009e\u0001\u001a\u000207*\u00030\u009f\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0011\u0010 \u0001\u001a\f\u0012\b\u0012\u00060\u000fj\u0002`\u00100\u000eH\u0002J\r\u0010¡\u0001\u001a\u00020@*\u00020OH\u0002J\r\u0010¢\u0001\u001a\u000207*\u00020UH\u0002R\u0018\u0010\r\u001a\f\u0012\b\u0012\u00060\u000fj\u0002`\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0011\u001a\u00060\u0012R\u00020\u00008\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0014\u001a\u0004\b\u001b\u0010\u001cR\u001c\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u0014\u001a\u0004\b\u001e\u0010\u001fR\u0018\u0010 \u001a\f\u0012\b\u0012\u00060\u000fj\u0002`\u00100\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\"8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b#\u0010\u0014\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b*\u0010\u0014\u001a\u0004\b+\u0010,R \u0010-\u001a\b\u0012\u0004\u0012\u00020.0\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001c\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b3\u0010\u0014\u001a\u0004\b4\u00105R\u0014\u00106\u001a\u0002078TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u001c\u0010:\u001a\u00020;8\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b<\u0010\u0014\u001a\u0004\b=\u0010>\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006§\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;Ljava/util/List;)V", "annotationsFromPlugins", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/fir/extensions/AnnotationFqn;", "argumentsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformerDispatcher;", "getArgumentsTransformer$annotations", "()V", "getArgumentsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformerDispatcher;", "classDeclarationsStack", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getClassDeclarationsStack$annotations", "getClassDeclarationsStack", "()Lkotlin/collections/ArrayDeque;", "getComputationSession$annotations", "getComputationSession", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "metaAnnotationsFromPlugins", "owners", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "getOwners$annotations", "getOwners", "()Lkotlinx/collections/immutable/PersistentList;", "setOwners", "(Lkotlinx/collections/immutable/PersistentList;)V", "predicateBasedProvider", "Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProvider;", "getScopeSession$annotations", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scopes", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "getScopes", "()Ljava/util/List;", "setScopes", "(Ljava/util/List;)V", "getSession$annotations", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "shouldRecordIntoPredicateBasedProvider", "", "getShouldRecordIntoPredicateBasedProvider", "()Z", "typeResolverTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "getTypeResolverTransformer$annotations", "getTypeResolverTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "afterTransformingChildren", "", "state", "beforeTransformingChildren", "parentDeclaration", "calculateDeprecations", "callableDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "classLikeDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "createDeepCopyOfTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "original", "extractBackingFieldAnnotationsFromProperty", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$AnnotationsPosition;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "propertyAnnotations", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "backingFieldAnnotations", "resolveAnnotationsOnAnnotationIfNeeded", "annotationTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "resolveRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformChildren", "Lkotlin/Function0;", "afterChildrenTransform", "shouldRunAnnotationResolve", "typeRef", "shouldTransformDeclaration", "declaration", "transformAnnotation", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "annotation", "data", "transformAnnotationCall", "annotationCall", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotationCall;", "transformAnnotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "annotationContainer", "transformBackingField", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "transformCallableDeclarationForDeprecations", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Ljava/lang/Void;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "R", "action", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformDeclaration", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Void;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "transformField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformProperty", "transformPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "transformRegularClass", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "withFile", "f", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFileAndFileScopes", "withFileScopes", "withRegularClass", "createDeepCopy", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjection;", "markedWithMetaAnnotation", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "metaAnnotations", "moveJavaDeprecatedAnnotationToBackingField", "requiredToSave", "AnnotationsPosition", "Companion", "FirEnumAnnotationArgumentsTransformer", "FirEnumAnnotationArgumentsTransformerDispatcher", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirSpecificAnnotationResolveTransformer extends FirDefaultTransformer {
    private static final Set<Name> REQUIRED_ANNOTATION_NAMES;
    private final Set<FqName> annotationsFromPlugins;
    private final FirEnumAnnotationArgumentsTransformerDispatcher argumentsTransformer;
    private final ArrayDeque<FirClass> classDeclarationsStack;
    private final CompilerRequiredAnnotationsComputationSession computationSession;
    private final Set<FqName> metaAnnotationsFromPlugins;
    private PersistentList<? extends FirDeclaration> owners;
    private final FirPredicateBasedProvider predicateBasedProvider;
    private final ScopeSession scopeSession;
    public List<? extends FirScope> scopes;
    private final FirSession session;
    private final FirSpecificTypeResolverTransformer typeResolverTransformer;

    @PrivateForInline
    public static /* synthetic */ void getArgumentsTransformer$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getClassDeclarationsStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getComputationSession$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getOwners$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getScopeSession$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getSession$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getTypeResolverTransformer$annotations() {
    }

    protected boolean getShouldRecordIntoPredicateBasedProvider() {
        return true;
    }

    public abstract boolean shouldTransformDeclaration(FirDeclaration declaration);

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Void data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final CompilerRequiredAnnotationsComputationSession getComputationSession() {
        return this.computationSession;
    }

    public /* synthetic */ AbstractFirSpecificAnnotationResolveTransformer(FirSession firSession, ScopeSession scopeSession, CompilerRequiredAnnotationsComputationSession compilerRequiredAnnotationsComputationSession, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, compilerRequiredAnnotationsComputationSession, (i & 8) != 0 ? CollectionsKt.emptyList() : list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AbstractFirSpecificAnnotationResolveTransformer(FirSession session, ScopeSession scopeSession, CompilerRequiredAnnotationsComputationSession computationSession, List<? extends FirDeclaration> containingDeclarations) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        this.session = session;
        this.scopeSession = scopeSession;
        this.computationSession = computationSession;
        this.predicateBasedProvider = FirPredicateBasedProviderKt.getPredicateBasedProvider(session);
        this.annotationsFromPlugins = FirRegisteredPluginAnnotationsKt.getRegisteredPluginAnnotations(session).getAnnotations();
        this.metaAnnotationsFromPlugins = FirRegisteredPluginAnnotationsKt.getRegisteredPluginAnnotations(session).getMetaAnnotations();
        this.typeResolverTransformer = new FirSpecificTypeResolverTransformer(session, false, false, null, 8, null);
        this.argumentsTransformer = new FirEnumAnnotationArgumentsTransformerDispatcher();
        this.owners = ExtensionsKt.persistentListOf();
        ArrayDeque<FirClass> arrayDeque = new ArrayDeque<>();
        for (FirDeclaration firDeclaration : containingDeclarations) {
            if (firDeclaration instanceof FirClass) {
                arrayDeque.add(firDeclaration);
            }
        }
        this.classDeclarationsStack = arrayDeque;
    }

    static {
        Set<ClassId> required_annotations = CompilerRequiredAnnotationsHelper.INSTANCE.getREQUIRED_ANNOTATIONS();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = required_annotations.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((ClassId) it.next()).getShortClassName());
        }
        REQUIRED_ANNOTATION_NAMES = linkedHashSet;
    }

    /* JADX INFO: compiled from: AbstractFirSpecificAnnotationResolveTransformer.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformerDispatcher;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;)V", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FirEnumAnnotationArgumentsTransformerDispatcher extends FirAbstractBodyResolveTransformerDispatcher {
        private final FirDeclarationsResolveTransformer declarationsTransformer;
        private final FirExpressionsResolveTransformer expressionsTransformer;

        public FirEnumAnnotationArgumentsTransformerDispatcher() {
            super(AbstractFirSpecificAnnotationResolveTransformer.this.getSession(), FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS, false, AbstractFirSpecificAnnotationResolveTransformer.this.getScopeSession(), null, null, null, 112, null);
            this.expressionsTransformer = new FirEnumAnnotationArgumentsTransformer(AbstractFirSpecificAnnotationResolveTransformer.this, this);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
        public FirExpressionsResolveTransformer getExpressionsTransformer() {
            return this.expressionsTransformer;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
        public FirDeclarationsResolveTransformer getDeclarationsTransformer() {
            return this.declarationsTransformer;
        }
    }

    /* JADX INFO: compiled from: AbstractFirSpecificAnnotationResolveTransformer.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0018\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\f\u0010\u0012\u001a\u00020\u0013*\u00020\bH\u0002J\u001c\u0010\u0014\u001a\u00020\u0013*\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirExpressionsResolveTransformerForAnnotations;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "resolveQualifiedAccessAndSelectCandidate", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "qualifiedAccessExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "isUsedAsReceiver", "", "callSite", "Lorg/jetbrains/kotlin/fir/FirElement;", "transformFunctionCall", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "resolveFromImportScope", "", "updateCallee", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/impl/FirSimpleNamedReference;", "calleeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class FirEnumAnnotationArgumentsTransformer extends AbstractFirExpressionsResolveTransformerForAnnotations {
        final /* synthetic */ AbstractFirSpecificAnnotationResolveTransformer this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirEnumAnnotationArgumentsTransformer(AbstractFirSpecificAnnotationResolveTransformer abstractFirSpecificAnnotationResolveTransformer, FirAbstractBodyResolveTransformerDispatcher transformer) {
            super(transformer);
            Intrinsics.checkNotNullParameter(transformer, "transformer");
            this.this$0 = abstractFirSpecificAnnotationResolveTransformer;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirExpressionsResolveTransformerForAnnotations, org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformFunctionCall(FirFunctionCall functionCall, ResolutionMode data) {
            Intrinsics.checkNotNullParameter(functionCall, "functionCall");
            Intrinsics.checkNotNullParameter(data, "data");
            functionCall.transformChildren(getTransformer(), data);
            return super.transformFunctionCall(functionCall, data);
        }

        @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirExpressionsResolveTransformerForAnnotations, org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer
        protected FirStatement resolveQualifiedAccessAndSelectCandidate(FirQualifiedAccessExpression qualifiedAccessExpression, boolean isUsedAsReceiver, FirElement callSite) {
            Intrinsics.checkNotNullParameter(qualifiedAccessExpression, "qualifiedAccessExpression");
            Intrinsics.checkNotNullParameter(callSite, "callSite");
            resolveFromImportScope(qualifiedAccessExpression);
            return qualifiedAccessExpression;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final void resolveFromImportScope(FirQualifiedAccessExpression firQualifiedAccessExpression) {
            FirVariableSymbol firVariableSymbol;
            FirEnumEntrySymbol firEnumEntrySymbol;
            FirClassSymbol<?> firClassSymbol;
            Object next;
            FirEnumEntrySymbol firEnumEntrySymbolEnhance;
            FirReference calleeReference = firQualifiedAccessExpression.getCalleeReference();
            FirSimpleNamedReference firSimpleNamedReference = calleeReference instanceof FirSimpleNamedReference ? (FirSimpleNamedReference) calleeReference : null;
            if (firSimpleNamedReference == null) {
                return;
            }
            FirExpression explicitReceiver = firQualifiedAccessExpression.getExplicitReceiver();
            FirQualifiedAccessExpression firQualifiedAccessExpression2 = explicitReceiver instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) explicitReceiver : null;
            if (firQualifiedAccessExpression2 != null) {
                FirReference calleeReference2 = firQualifiedAccessExpression2.getCalleeReference();
                FirSimpleNamedReference firSimpleNamedReference2 = calleeReference2 instanceof FirSimpleNamedReference ? (FirSimpleNamedReference) calleeReference2 : null;
                if (firSimpleNamedReference2 == null) {
                    return;
                }
                Name name2 = firSimpleNamedReference2.getName();
                if (name2.isSpecial()) {
                    name2 = null;
                }
                if (name2 == null) {
                    return;
                }
                List<FirScope> scopes = this.this$0.getScopes();
                ArrayList arrayList = new ArrayList();
                for (Object obj : scopes) {
                    if (obj instanceof FirAbstractImportingScope) {
                        arrayList.add(obj);
                    }
                }
                Iterator it = arrayList.iterator();
                do {
                    if (!it.hasNext()) {
                        firClassSymbol = null;
                        break;
                    } else {
                        FirClassifierSymbol<?> singleClassifier = FirScopeKt.getSingleClassifier((FirAbstractImportingScope) it.next(), name2);
                        firClassSymbol = singleClassifier instanceof FirClassSymbol ? (FirClassSymbol) singleClassifier : null;
                    }
                } while (firClassSymbol == null);
                if (firClassSymbol == null) {
                    return;
                }
                List list = SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.generateSequence(firQualifiedAccessExpression2.getExplicitReceiver(), new Function1<FirExpression, FirExpression>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformer$resolveFromImportScope$segments$1
                    @Override // kotlin.jvm.functions.Function1
                    public final FirExpression invoke(FirExpression it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        FirQualifiedAccessExpression firQualifiedAccessExpression3 = it2 instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) it2 : null;
                        if (firQualifiedAccessExpression3 != null) {
                            return firQualifiedAccessExpression3.getExplicitReceiver();
                        }
                        return null;
                    }
                }), new Function1<FirExpression, String>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer$FirEnumAnnotationArgumentsTransformer$resolveFromImportScope$segments$2
                    @Override // kotlin.jvm.functions.Function1
                    public final String invoke(FirExpression it2) {
                        Name name3;
                        Intrinsics.checkNotNullParameter(it2, "it");
                        FirReference calleeReference3 = FirExpressionUtilKt.getCalleeReference(it2);
                        FirSimpleNamedReference firSimpleNamedReference3 = calleeReference3 instanceof FirSimpleNamedReference ? (FirSimpleNamedReference) calleeReference3 : null;
                        if (firSimpleNamedReference3 == null || (name3 = firSimpleNamedReference3.getName()) == null) {
                            return null;
                        }
                        return name3.getIdentifier();
                    }
                }));
                List list2 = list;
                if (list2.isEmpty() || Intrinsics.areEqual(FqName.fromSegments(CollectionsKt.asReversed(list)), firClassSymbol.getClassId().getPackageFqName())) {
                    FirResolvedQualifierBuilder firResolvedQualifierBuilder = new FirResolvedQualifierBuilder();
                    firResolvedQualifierBuilder.setSource(firQualifiedAccessExpression2.getSource());
                    FqName packageFqName = firClassSymbol.getClassId().getPackageFqName();
                    Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                    firResolvedQualifierBuilder.setPackageFqName(packageFqName);
                    firResolvedQualifierBuilder.setRelativeClassFqName(firClassSymbol.getClassId().getRelativeClassName());
                    firResolvedQualifierBuilder.setTypeRef(new FirImplicitUnitTypeRef(firQualifiedAccessExpression2.getTypeRef().getSource()));
                    firResolvedQualifierBuilder.setSymbol(firClassSymbol);
                    firResolvedQualifierBuilder.setFullyQualified(!list2.isEmpty());
                    FirResolvedQualifier firResolvedQualifierMo6809build = firResolvedQualifierBuilder.mo6809build();
                    Iterator<T> it2 = ((FirClass) firClassSymbol.getFir()).getDeclarations().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it2.next();
                        FirDeclaration firDeclaration = (FirDeclaration) next;
                        if ((firDeclaration instanceof FirEnumEntry) && Intrinsics.areEqual(((FirEnumEntry) firDeclaration).getName(), firSimpleNamedReference.getName())) {
                            break;
                        }
                    }
                    FirDeclaration firDeclaration2 = (FirDeclaration) next;
                    FirBasedSymbol<? extends FirDeclaration> symbol = firDeclaration2 != null ? firDeclaration2.getSymbol() : null;
                    firEnumEntrySymbol = symbol instanceof FirEnumEntrySymbol ? (FirEnumEntrySymbol) symbol : null;
                    if (firEnumEntrySymbol == null) {
                        return;
                    }
                    CompilerRequiredAnnotationEnhancementProvider compilerRequiredAnnotationEnhancementProvider = CompilerRequiredAnnotationEnhancementProviderKt.getCompilerRequiredAnnotationEnhancementProvider(getSession());
                    if (compilerRequiredAnnotationEnhancementProvider != null && (firEnumEntrySymbolEnhance = compilerRequiredAnnotationEnhancementProvider.enhance(firClassSymbol, firEnumEntrySymbol, getSession())) != null) {
                        firEnumEntrySymbol = firEnumEntrySymbolEnhance;
                    }
                    updateCallee(firQualifiedAccessExpression, firSimpleNamedReference, firEnumEntrySymbol);
                    FirResolvedQualifier firResolvedQualifier = firResolvedQualifierMo6809build;
                    firQualifiedAccessExpression.replaceExplicitReceiver(firResolvedQualifier);
                    firQualifiedAccessExpression.replaceDispatchReceiver(firResolvedQualifier);
                    return;
                }
                return;
            }
            Iterator<T> it3 = this.this$0.getScopes().iterator();
            do {
                if (!it3.hasNext()) {
                    firVariableSymbol = null;
                    break;
                }
                firVariableSymbol = (FirVariableSymbol) CollectionsKt.firstOrNull((List) FirScopeKt.getProperties((FirScope) it3.next(), firSimpleNamedReference.getName()));
            } while (firVariableSymbol == null);
            firEnumEntrySymbol = firVariableSymbol instanceof FirEnumEntrySymbol ? (FirEnumEntrySymbol) firVariableSymbol : null;
            if (firEnumEntrySymbol == null) {
                return;
            }
            updateCallee(firQualifiedAccessExpression, firSimpleNamedReference, firEnumEntrySymbol);
        }

        private final void updateCallee(FirQualifiedAccessExpression firQualifiedAccessExpression, FirSimpleNamedReference firSimpleNamedReference, FirEnumEntrySymbol firEnumEntrySymbol) {
            String strAsString;
            ClassId classId;
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
            if (lookupTracker != null) {
                Name name2 = firSimpleNamedReference.getName();
                ConeSimpleKotlinType dispatchReceiverType = firEnumEntrySymbol.getDispatchReceiverType();
                if (dispatchReceiverType == null || (classId = ConeTypeUtilsKt.getClassId(dispatchReceiverType)) == null || (strAsString = classId.asFqNameString()) == null) {
                    strAsString = firEnumEntrySymbol.getCallableId().getPackageName().asString();
                }
                Intrinsics.checkNotNull(strAsString);
                lookupTracker.recordLookup(name2, strAsString, firQualifiedAccessExpression.getSource(), getTransformer().getContext().getFile().getSource());
            }
            FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
            firResolvedNamedReferenceBuilder.setSource(firSimpleNamedReference.getSource());
            firResolvedNamedReferenceBuilder.setName(firSimpleNamedReference.getName());
            firResolvedNamedReferenceBuilder.setResolvedSymbol(firEnumEntrySymbol);
            firQualifiedAccessExpression.replaceCalleeReference(firResolvedNamedReferenceBuilder.build());
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firEnumEntrySymbol);
            if (coneClassLikeLookupTagContainingClassLookupTag != null) {
                firQualifiedAccessExpression.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(firQualifiedAccessExpression.getTypeRef(), new ConeClassLikeTypeImpl(coneClassLikeLookupTagContainingClassLookupTag, new ConeTypeProjection[0], false, null, 8, null), null, 2, null));
            }
        }
    }

    public final FirSpecificTypeResolverTransformer getTypeResolverTransformer() {
        return this.typeResolverTransformer;
    }

    public final FirEnumAnnotationArgumentsTransformerDispatcher getArgumentsTransformer() {
        return this.argumentsTransformer;
    }

    public final PersistentList<FirDeclaration> getOwners() {
        return this.owners;
    }

    public final void setOwners(PersistentList<? extends FirDeclaration> persistentList) {
        Intrinsics.checkNotNullParameter(persistentList, "<set-?>");
        this.owners = persistentList;
    }

    public final ArrayDeque<FirClass> getClassDeclarationsStack() {
        return this.classDeclarationsStack;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotationCall(FirAnnotationCall annotationCall, Void data) {
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        FirTypeRef annotationTypeRef = annotationCall.getAnnotationTypeRef();
        if (!(annotationTypeRef instanceof FirUserTypeRef)) {
            return annotationCall;
        }
        FirUserTypeRef firUserTypeRef = (FirUserTypeRef) annotationTypeRef;
        if (!shouldRunAnnotationResolve(firUserTypeRef)) {
            return annotationCall;
        }
        transformAnnotationCall(annotationCall, firUserTypeRef);
        return annotationCall;
    }

    public final void transformAnnotationCall(FirAnnotationCall annotationCall, FirUserTypeRef typeRef) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(annotationCall, "annotationCall");
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        FirTypeRef firTypeRefTransformUserTypeRef = this.typeResolverTransformer.transformUserTypeRef(createDeepCopyOfTypeRef(typeRef), new ScopeClassDeclaration(CollectionsKt.asReversed(getScopes()), this.classDeclarationsStack, null, null, 12, null));
        ClassId classId = null;
        FirResolvedTypeRef firResolvedTypeRef = firTypeRefTransformUserTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRefTransformUserTypeRef : null;
        if (firResolvedTypeRef == null) {
            return;
        }
        resolveAnnotationsOnAnnotationIfNeeded(firResolvedTypeRef);
        if (requiredToSave(firResolvedTypeRef)) {
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef;
            annotationCall.replaceAnnotationTypeRef(firResolvedTypeRef2);
            annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.CompilerRequiredAnnotations);
            Set<ClassId> rEQUIRED_ANNOTATIONS_WITH_ARGUMENTS$resolve = CompilerRequiredAnnotationsHelper.INSTANCE.getREQUIRED_ANNOTATIONS_WITH_ARGUMENTS$resolve();
            FirResolvedTypeRef firResolvedTypeRef3 = firResolvedTypeRef2 instanceof FirResolvedTypeRef ? firResolvedTypeRef2 : null;
            ConeKotlinType type = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
            if (!(type instanceof ConeClassLikeType)) {
                type = null;
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                classId = lookupTag.getClassId();
            }
            if (CollectionsKt.contains(rEQUIRED_ANNOTATIONS_WITH_ARGUMENTS$resolve, classId)) {
                this.argumentsTransformer.transformAnnotation((FirAnnotation) annotationCall, (ResolutionMode) ResolutionMode.ContextDependent.INSTANCE);
            }
        }
    }

    private final void resolveAnnotationsOnAnnotationIfNeeded(FirResolvedTypeRef annotationTypeRef) {
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(annotationTypeRef), this.session);
        if (regularClassSymbol == null) {
            return;
        }
        this.computationSession.resolveAnnotationsOnAnnotationIfNeeded(regularClassSymbol, this.scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnnotation(FirAnnotation annotation, Void data) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        throw new IllegalStateException("Should not be there".toString());
    }

    public final boolean shouldRunAnnotationResolve(FirUserTypeRef typeRef) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Name name2 = ((FirQualifierPart) CollectionsKt.last((List) typeRef.getQualifier())).getName();
        if (!this.metaAnnotationsFromPlugins.isEmpty() || REQUIRED_ANNOTATION_NAMES.contains(name2)) {
            return true;
        }
        Set<FqName> set = this.annotationsFromPlugins;
        if (!(set instanceof Collection) || !set.isEmpty()) {
            Iterator<T> it = set.iterator();
            while (it.hasNext()) {
                if (Intrinsics.areEqual(((FqName) it.next()).shortName(), name2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean requiredToSave(FirResolvedTypeRef firResolvedTypeRef) {
        ClassId classId = ConeTypeUtilsKt.getClassId(firResolvedTypeRef.getType());
        if (classId == null) {
            return false;
        }
        if (CompilerRequiredAnnotationsHelper.INSTANCE.getREQUIRED_ANNOTATIONS().contains(classId) || this.annotationsFromPlugins.contains(classId.asSingleFqName())) {
            return true;
        }
        if (this.metaAnnotationsFromPlugins.isEmpty()) {
            return false;
        }
        return markedWithMetaAnnotation(firResolvedTypeRef.getType(), this.session, this.metaAnnotationsFromPlugins);
    }

    private final boolean markedWithMetaAnnotation(ConeKotlinType coneKotlinType, FirSession firSession, Set<FqName> set) {
        return FirPredicateBasedProviderImplKt.markedWithMetaAnnotationImpl(TypeUtilsKt.toRegularClassSymbol(coneKotlinType, firSession), firSession, set, true, new LinkedHashSet(), new Function1<FirRegularClassSymbol, List<? extends FirAnnotation>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer.markedWithMetaAnnotation.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<FirAnnotation> invoke(FirRegularClassSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                AbstractFirSpecificAnnotationResolveTransformer.this.getComputationSession().resolveAnnotationsOnAnnotationIfNeeded(it, AbstractFirSpecificAnnotationResolveTransformer.this.getScopeSession());
                return it.getAnnotations();
            }
        });
    }

    public final void withRegularClass(FirRegularClass regularClass, Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(action, "action");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(regularClass);
        try {
            action.invoke();
            Unit unit = Unit.INSTANCE;
        } finally {
            InlineMarker.finallyStart(1);
            classDeclarationsStack.removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, Void data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        FirTypeAlias firTypeAlias = typeAlias;
        if (!shouldTransformDeclaration(firTypeAlias)) {
            return typeAlias;
        }
        this.computationSession.recordThatAnnotationsAreResolved(typeAlias);
        FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) firTypeAlias, data);
        calculateDeprecations(typeAlias);
        Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeAlias");
        return (FirTypeAlias) firDeclarationTransformDeclaration;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclaration transformDeclaration(FirDeclaration declaration, Void data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirAnnotationContainer firAnnotationContainerTransformAnnotationContainer = transformAnnotationContainer((FirAnnotationContainer) declaration, data);
        Intrinsics.checkNotNull(firAnnotationContainerTransformAnnotationContainer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirDeclaration");
        FirDeclaration firDeclaration = (FirDeclaration) firAnnotationContainerTransformAnnotationContainer;
        if (getShouldRecordIntoPredicateBasedProvider()) {
            this.predicateBasedProvider.registerAnnotatedDeclaration(declaration, this.owners);
        }
        return firDeclaration;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(final FirFile file, final Void data) {
        Intrinsics.checkNotNullParameter(file, "file");
        if (!shouldTransformDeclaration(file)) {
            return file;
        }
        withFileAndFileScopes(file, new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer.transformFile.1
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
                file.transformDeclarations(this, data);
            }
        });
        return file;
    }

    public final <T> T withFile(FirFile file, Function0<? extends T> f) throws Throwable {
        int i;
        FirTowerDataContext firTowerDataContext;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(f, "f");
        FirSpecificTypeResolverTransformer typeResolverTransformer = getTypeResolverTransformer();
        FirFile firFile = typeResolverTransformer.currentFile;
        typeResolverTransformer.currentFile = file;
        try {
            BodyResolveContext context = getArgumentsTransformer().getContext();
            FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = getArgumentsTransformer().getComponents();
            context.clear();
            context.setFile(file);
            List<FirScope> fileImportsScope = context.getFileImportsScope();
            int size = fileImportsScope.size();
            int i2 = 0;
            try {
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    firTowerDataContext = towerDataContext;
                    try {
                        List listCreateImportingScopes$default = ImportingScopesKt.createImportingScopes$default(file, components.getSession(), components.getScopeSession(), false, 8, null);
                        CollectionsKt.addAll(context.getFileImportsScope(), listCreateImportingScopes$default);
                        List list = listCreateImportingScopes$default;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(ImplicitReceiverUtilsKt.asTowerDataElement((FirScope) it.next(), false));
                        }
                        context.addNonLocalTowerDataElements(arrayList);
                        try {
                            T tInvoke = f.invoke();
                            InlineMarker.finallyStart(2);
                            context.replaceTowerDataContext(firTowerDataContext);
                            InlineMarker.finallyEnd(2);
                            InlineMarker.finallyStart(3);
                            int size2 = fileImportsScope.size() - size;
                            while (i2 < size2) {
                                fileImportsScope.remove(fileImportsScope.size() - 1);
                                i2++;
                            }
                            InlineMarker.finallyEnd(3);
                            InlineMarker.finallyStart(2);
                            typeResolverTransformer.currentFile = firFile;
                            InlineMarker.finallyEnd(2);
                            return tInvoke;
                        } catch (Throwable th) {
                            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
                            throw null;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        InlineMarker.finallyStart(1);
                        context.replaceTowerDataContext(firTowerDataContext);
                        InlineMarker.finallyEnd(1);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    firTowerDataContext = towerDataContext;
                }
            } catch (Throwable th4) {
                i = 1;
                InlineMarker.finallyStart(1);
                int size3 = fileImportsScope.size() - size;
                while (i2 < size3) {
                    try {
                        fileImportsScope.remove(fileImportsScope.size() - 1);
                        i2++;
                    } catch (Throwable th5) {
                        th = th5;
                        InlineMarker.finallyStart(i);
                        typeResolverTransformer.currentFile = firFile;
                        InlineMarker.finallyEnd(i);
                        throw th;
                    }
                }
                InlineMarker.finallyEnd(1);
                throw th4;
            }
        } catch (Throwable th6) {
            th = th6;
            i = 1;
        }
    }

    public final void calculateDeprecations(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        if (Intrinsics.areEqual(classLikeDeclaration.getDeprecationsProvider(), UnresolvedDeprecationProvider.INSTANCE)) {
            classLikeDeclaration.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(classLikeDeclaration, this.session));
        }
    }

    private final void calculateDeprecations(FirCallableDeclaration callableDeclaration) {
        if (Intrinsics.areEqual(callableDeclaration.getDeprecationsProvider(), UnresolvedDeprecationProvider.INSTANCE)) {
            callableDeclaration.replaceDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProvider(callableDeclaration, this.session));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <T extends FirCallableDeclaration & FirStatement> FirStatement transformCallableDeclarationForDeprecations(T callableDeclaration, Void data) {
        FirDeclaration firDeclaration = (FirDeclaration) callableDeclaration;
        if (!shouldTransformDeclaration(firDeclaration)) {
            return callableDeclaration;
        }
        this.computationSession.recordThatAnnotationsAreResolved(callableDeclaration);
        FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration(firDeclaration, data);
        calculateDeprecations(callableDeclaration);
        Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        return (FirStatement) firAnnotationContainerTransformDeclaration;
    }

    public final List<FirScope> getScopes() {
        List list = this.scopes;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scopes");
        return null;
    }

    public final void setScopes(List<? extends FirScope> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.scopes = list;
    }

    public final <T> T withFileScopes(FirFile file, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(f, "f");
        setScopes(ImportingScopesKt.createImportingScopes(file, getSession(), getScopeSession(), getComputationSession().getUseCacheForImportScope()));
        return f.invoke();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBackingField(FirBackingField backingField, Void data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        return transformCallableDeclarationForDeprecations(backingField, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessor(FirPropertyAccessor propertyAccessor, Void data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        return transformCallableDeclarationForDeprecations(propertyAccessor, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformProperty(FirProperty property, Void data) {
        Intrinsics.checkNotNullParameter(property, "property");
        FirProperty firProperty = property;
        if (!shouldTransformDeclaration(firProperty)) {
            return property;
        }
        this.computationSession.recordThatAnnotationsAreResolved(property);
        FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration((FirDeclaration) firProperty, data);
        moveJavaDeprecatedAnnotationToBackingField(property);
        PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(firProperty);
        try {
            property.transformSetter((FirTransformer<? super Void>) this, data);
            property.transformGetter((FirTransformer<? super Void>) this, data);
            property.transformBackingField((FirTransformer<? super Void>) this, data);
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            calculateDeprecations(property);
            Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
            return (FirStatement) firAnnotationContainerTransformDeclaration;
        } catch (Throwable th) {
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            throw th;
        }
    }

    private final void moveJavaDeprecatedAnnotationToBackingField(FirProperty firProperty) {
        AnnotationsPosition annotationsPositionExtractBackingFieldAnnotationsFromProperty$default = extractBackingFieldAnnotationsFromProperty$default(this, firProperty, null, null, 6, null);
        if (annotationsPositionExtractBackingFieldAnnotationsFromProperty$default == null) {
            return;
        }
        firProperty.replaceAnnotations(annotationsPositionExtractBackingFieldAnnotationsFromProperty$default.getPropertyAnnotations());
        FirBackingField backingField = firProperty.getBackingField();
        if (backingField != null) {
            backingField.replaceAnnotations(annotationsPositionExtractBackingFieldAnnotationsFromProperty$default.getBackingFieldAnnotations());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AnnotationsPosition extractBackingFieldAnnotationsFromProperty$default(AbstractFirSpecificAnnotationResolveTransformer abstractFirSpecificAnnotationResolveTransformer, FirProperty firProperty, List list, List list2, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: extractBackingFieldAnnotationsFromProperty");
        }
        if ((i & 2) != 0) {
            list = firProperty.getAnnotations();
        }
        if ((i & 4) != 0) {
            FirBackingField backingField = firProperty.getBackingField();
            list2 = backingField != null ? backingField.getAnnotations() : null;
            if (list2 == null) {
                list2 = CollectionsKt.emptyList();
            }
        }
        return abstractFirSpecificAnnotationResolveTransformer.extractBackingFieldAnnotationsFromProperty(firProperty, list, list2);
    }

    public final AnnotationsPosition extractBackingFieldAnnotationsFromProperty(FirProperty property, List<? extends FirAnnotation> propertyAnnotations, List<? extends FirAnnotation> backingFieldAnnotations) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(propertyAnnotations, "propertyAnnotations");
        Intrinsics.checkNotNullParameter(backingFieldAnnotations, "backingFieldAnnotations");
        if (propertyAnnotations.isEmpty() || property.getBackingField() == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : propertyAnnotations) {
            if (Intrinsics.areEqual(FirAnnotationUtilsKt.toAnnotationClassIdSafe((FirAnnotation) obj, this.session), StandardClassIds.Annotations.Java.INSTANCE.getDeprecated())) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        if (list.isEmpty()) {
            return null;
        }
        return new AnnotationsPosition(CollectionsKt.plus((Collection) backingFieldAnnotations, (Iterable) list), list2);
    }

    /* JADX INFO: compiled from: AbstractFirSpecificAnnotationResolveTransformer.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer$AnnotationsPosition;", "", "backingFieldAnnotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "propertyAnnotations", "(Ljava/util/List;Ljava/util/List;)V", "getBackingFieldAnnotations", "()Ljava/util/List;", "getPropertyAnnotations", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnnotationsPosition {
        private final List<FirAnnotation> backingFieldAnnotations;
        private final List<FirAnnotation> propertyAnnotations;

        /* JADX WARN: Multi-variable type inference failed */
        public AnnotationsPosition(List<? extends FirAnnotation> backingFieldAnnotations, List<? extends FirAnnotation> propertyAnnotations) {
            Intrinsics.checkNotNullParameter(backingFieldAnnotations, "backingFieldAnnotations");
            Intrinsics.checkNotNullParameter(propertyAnnotations, "propertyAnnotations");
            this.backingFieldAnnotations = backingFieldAnnotations;
            this.propertyAnnotations = propertyAnnotations;
        }

        public final List<FirAnnotation> getBackingFieldAnnotations() {
            return this.backingFieldAnnotations;
        }

        public final List<FirAnnotation> getPropertyAnnotations() {
            return this.propertyAnnotations;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(FirSimpleFunction simpleFunction, Void data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        FirSimpleFunction firSimpleFunction = simpleFunction;
        if (!shouldTransformDeclaration(firSimpleFunction)) {
            return simpleFunction;
        }
        this.computationSession.recordThatAnnotationsAreResolved(simpleFunction);
        FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) firSimpleFunction, data);
        PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(firSimpleFunction);
        try {
            simpleFunction.transformValueParameters((FirTransformer<? super Void>) this, data);
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            calculateDeprecations(simpleFunction);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirSimpleFunction");
            return (FirSimpleFunction) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, Void data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirConstructor firConstructor = constructor;
        if (!shouldTransformDeclaration(firConstructor)) {
            return constructor;
        }
        this.computationSession.recordThatAnnotationsAreResolved(constructor);
        FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) firConstructor, data);
        PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(firConstructor);
        try {
            constructor.transformValueParameters((FirTransformer<? super Void>) this, data);
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            calculateDeprecations(constructor);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirConstructor");
            return (FirConstructor) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformEnumEntry(FirEnumEntry enumEntry, Void data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        return transformCallableDeclarationForDeprecations(enumEntry, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformField(FirField field, Void data) {
        Intrinsics.checkNotNullParameter(field, "field");
        return transformCallableDeclarationForDeprecations(field, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, Void data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return transformCallableDeclarationForDeprecations(valueParameter, data);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformTypeRef(FirTypeRef typeRef, Void data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        FirAnnotationContainer firAnnotationContainerTransformAnnotationContainer = transformAnnotationContainer((FirAnnotationContainer) typeRef, data);
        Intrinsics.checkNotNull(firAnnotationContainerTransformAnnotationContainer, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.FirTypeRef");
        return (FirTypeRef) firAnnotationContainerTransformAnnotationContainer;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirAnnotationContainer transformAnnotationContainer(FirAnnotationContainer annotationContainer, Void data) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        return annotationContainer.transformAnnotations(this, data);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public final PersistentList<FirDeclaration> beforeTransformingChildren(FirDeclaration parentDeclaration) {
        Intrinsics.checkNotNullParameter(parentDeclaration, "parentDeclaration");
        PersistentList<? extends FirDeclaration> persistentList = this.owners;
        this.owners = persistentList.add(parentDeclaration);
        return persistentList;
    }

    public final void afterTransformingChildren(PersistentList<? extends FirDeclaration> state) {
        if (state == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        this.owners = state;
    }

    public final <R> R transformChildren(FirDeclaration parentDeclaration, Function0<? extends R> action) {
        Intrinsics.checkNotNullParameter(parentDeclaration, "parentDeclaration");
        Intrinsics.checkNotNullParameter(action, "action");
        PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(parentDeclaration);
        try {
            return action.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            afterTransformingChildren(persistentListBeforeTransformingChildren);
            InlineMarker.finallyEnd(1);
        }
    }

    private final FirQualifierPart createDeepCopy(FirQualifierPart firQualifierPart) {
        FirTypeArgumentListImpl firTypeArgumentListImpl = new FirTypeArgumentListImpl(firQualifierPart.getTypeArgumentList().getSource());
        List<FirTypeProjection> typeArguments = firQualifierPart.getTypeArgumentList().getTypeArguments();
        List<FirTypeProjection> typeArguments2 = firTypeArgumentListImpl.getTypeArguments();
        Iterator<T> it = typeArguments.iterator();
        while (it.hasNext()) {
            typeArguments2.add(createDeepCopy((FirTypeProjection) it.next()));
        }
        return new FirQualifierPartImpl(firQualifierPart.getSource(), firQualifierPart.getName(), firTypeArgumentListImpl);
    }

    private final FirTypeProjection createDeepCopy(FirTypeProjection firTypeProjection) {
        if (!(firTypeProjection instanceof FirTypeProjectionWithVariance)) {
            if (!(firTypeProjection instanceof FirStarProjection)) {
                if (!(firTypeProjection instanceof FirPlaceholderProjection)) {
                    AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
                    throw null;
                }
                FirPlaceholderProjectionBuilder firPlaceholderProjectionBuilder = new FirPlaceholderProjectionBuilder();
                firPlaceholderProjectionBuilder.setSource(firTypeProjection.getSource());
                return firPlaceholderProjectionBuilder.build();
            }
            FirStarProjectionBuilder firStarProjectionBuilder = new FirStarProjectionBuilder();
            firStarProjectionBuilder.setSource(firTypeProjection.getSource());
            return firStarProjectionBuilder.build();
        }
        FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
        firTypeProjectionWithVarianceBuilder.setSource(firTypeProjection.getSource());
        FirTypeProjectionWithVariance firTypeProjectionWithVariance = (FirTypeProjectionWithVariance) firTypeProjection;
        FirUserTypeRef typeRef = firTypeProjectionWithVariance.getTypeRef();
        if (typeRef instanceof FirUserTypeRef) {
            typeRef = createDeepCopyOfTypeRef((FirUserTypeRef) typeRef);
        }
        firTypeProjectionWithVarianceBuilder.setTypeRef(typeRef);
        firTypeProjectionWithVarianceBuilder.setVariance(firTypeProjectionWithVariance.getVariance());
        return firTypeProjectionWithVarianceBuilder.build();
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, Void data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(regularClass);
        try {
            if (shouldTransformDeclaration(regularClass)) {
                getComputationSession().recordThatAnnotationsAreResolved(regularClass);
                transformDeclaration((FirDeclaration) regularClass, (Void) null);
                PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(regularClass);
                try {
                    regularClass.transformDeclarations((FirTransformer<? super Void>) this, data);
                    Unit unit = Unit.INSTANCE;
                    afterTransformingChildren(persistentListBeforeTransformingChildren);
                    calculateDeprecations(regularClass);
                    Unit unit2 = Unit.INSTANCE;
                } catch (Throwable th) {
                    afterTransformingChildren(persistentListBeforeTransformingChildren);
                    throw th;
                }
            }
            classDeclarationsStack.removeLast();
            return regularClass;
        } catch (Throwable th2) {
            classDeclarationsStack.removeLast();
            throw th2;
        }
    }

    public final void resolveRegularClass(FirRegularClass regularClass, Function0<Unit> transformChildren, Function0<Unit> afterChildrenTransform) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(transformChildren, "transformChildren");
        Intrinsics.checkNotNullParameter(afterChildrenTransform, "afterChildrenTransform");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(regularClass);
        try {
            if (shouldTransformDeclaration(regularClass)) {
                getComputationSession().recordThatAnnotationsAreResolved(regularClass);
                transformDeclaration((FirDeclaration) regularClass, (Void) null);
                PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(regularClass);
                try {
                    transformChildren.invoke();
                    Unit unit = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    afterTransformingChildren(persistentListBeforeTransformingChildren);
                    InlineMarker.finallyEnd(1);
                    afterChildrenTransform.invoke();
                    Unit unit2 = Unit.INSTANCE;
                    InlineMarker.finallyStart(1);
                    classDeclarationsStack.removeLast();
                    return;
                } finally {
                    InlineMarker.finallyStart(1);
                    afterTransformingChildren(persistentListBeforeTransformingChildren);
                    InlineMarker.finallyEnd(1);
                }
            }
            InlineMarker.finallyStart(2);
            classDeclarationsStack.removeLast();
            InlineMarker.finallyEnd(2);
        } catch (Throwable th) {
            InlineMarker.finallyStart(1);
            classDeclarationsStack.removeLast();
            InlineMarker.finallyEnd(1);
            throw th;
        }
    }

    public final void withFileAndFileScopes(FirFile file, Function0<Unit> action) {
        FirTowerDataContext firTowerDataContext;
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(action, "action");
        FirSpecificTypeResolverTransformer typeResolverTransformer = getTypeResolverTransformer();
        FirFile firFile = typeResolverTransformer.currentFile;
        typeResolverTransformer.currentFile = file;
        try {
            BodyResolveContext context = getArgumentsTransformer().getContext();
            FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = getArgumentsTransformer().getComponents();
            context.clear();
            context.setFile(file);
            List<FirScope> fileImportsScope = context.getFileImportsScope();
            int size = fileImportsScope.size();
            int i = 0;
            try {
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    firTowerDataContext = towerDataContext;
                    try {
                        List listCreateImportingScopes$default = ImportingScopesKt.createImportingScopes$default(file, components.getSession(), components.getScopeSession(), false, 8, null);
                        CollectionsKt.addAll(context.getFileImportsScope(), listCreateImportingScopes$default);
                        List list = listCreateImportingScopes$default;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(ImplicitReceiverUtilsKt.asTowerDataElement((FirScope) it.next(), false));
                        }
                        context.addNonLocalTowerDataElements(arrayList);
                        try {
                            setScopes(ImportingScopesKt.createImportingScopes(file, getSession(), getScopeSession(), getComputationSession().getUseCacheForImportScope()));
                            PersistentList<FirDeclaration> persistentListBeforeTransformingChildren = beforeTransformingChildren(file);
                            try {
                                action.invoke();
                                Unit unit = Unit.INSTANCE;
                                afterTransformingChildren(persistentListBeforeTransformingChildren);
                                Unit unit2 = Unit.INSTANCE;
                                context.replaceTowerDataContext(firTowerDataContext);
                                int size2 = fileImportsScope.size() - size;
                                while (i < size2) {
                                    fileImportsScope.remove(fileImportsScope.size() - 1);
                                    i++;
                                }
                                typeResolverTransformer.currentFile = firFile;
                            } catch (Throwable th) {
                                try {
                                    afterTransformingChildren(persistentListBeforeTransformingChildren);
                                    throw th;
                                } catch (Throwable th2) {
                                    th = th2;
                                    try {
                                        UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
                                        throw null;
                                    } catch (Throwable th3) {
                                        th = th3;
                                        context.replaceTowerDataContext(firTowerDataContext);
                                        throw th;
                                    }
                                }
                            }
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        firTowerDataContext = firTowerDataContext;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    firTowerDataContext = towerDataContext;
                }
            } catch (Throwable th7) {
                int size3 = fileImportsScope.size() - size;
                while (i < size3) {
                    fileImportsScope.remove(fileImportsScope.size() - 1);
                    i++;
                }
                throw th7;
            }
        } catch (Throwable th8) {
            typeResolverTransformer.currentFile = firFile;
            throw th8;
        }
    }

    public final FirUserTypeRef createDeepCopyOfTypeRef(FirUserTypeRef original) {
        Intrinsics.checkNotNullParameter(original, "original");
        FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
        firUserTypeRefBuilder.setSource(original.getSource());
        firUserTypeRefBuilder.setMarkedNullable(original.getIsMarkedNullable());
        firUserTypeRefBuilder.getAnnotations().addAll(original.getAnnotations());
        List<FirQualifierPart> qualifier = original.getQualifier();
        List<FirQualifierPart> qualifier2 = firUserTypeRefBuilder.getQualifier();
        Iterator<T> it = qualifier.iterator();
        while (it.hasNext()) {
            qualifier2.add(createDeepCopy((FirQualifierPart) it.next()));
        }
        return firUserTypeRefBuilder.mo6809build();
    }
}
