package org.jetbrains.kotlin.fir.resolve.transformers.mpp;

import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.annotation.AnnotationRetention;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContext;
import org.jetbrains.kotlin.fir.FirExpectActualMatchingContextFactory;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.FirRetentionAnnotationHelpersKt;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.FirSamResolver;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ScopesKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionWithoutNameSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ExpectActualUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.mpp.CallableSymbolMarker;
import org.jetbrains.kotlin.mpp.ClassLikeSymbolMarker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.FunctionSymbolMarker;
import org.jetbrains.kotlin.mpp.PropertySymbolMarker;
import org.jetbrains.kotlin.mpp.RegularClassSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.mpp.TypeParameterSymbolMarker;
import org.jetbrains.kotlin.mpp.ValueParameterSymbolMarker;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualCollectionArgumentsCompatibilityCheckStrategy;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.checkers.OptInNames;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.ObsoleteTypeKind;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentListMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: FirExpectActualMatchingContextImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b0\n\u0002\u0010(\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u00012\u00020\u0002:\u0004·\u0002¸\u0002B\u0017\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u001b\u0010\u0082\u0001\u001a\u00020\t2\u0007\u0010\u0083\u0001\u001a\u00020\u001b2\u0007\u0010\u0084\u0001\u001a\u00020\u001bH\u0016J\u001d\u0010\u0085\u0001\u001a\u00020\t2\b\u0010\u0086\u0001\u001a\u00030\u0087\u00012\b\u0010\u0088\u0001\u001a\u00030\u0087\u0001H\u0002J%\u0010\u0085\u0001\u001a\u00020\t2\u0007\u0010\u0089\u0001\u001a\u00020\u00162\u0007\u0010\u008a\u0001\u001a\u00020\u00162\b\u0010\u008b\u0001\u001a\u00030\u008c\u0001H\u0016J\u001f\u0010\u008d\u0001\u001a\u00020\t2\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u001b2\t\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u001bH\u0016J\u001e\u0010\u008e\u0001\u001a\u00020\t2\b\u0010\u008f\u0001\u001a\u00030\u0090\u00012\b\u0010\u0091\u0001\u001a\u00030\u0090\u0001H\u0096\u0001J\u001d\u0010\u0092\u0001\u001a\u00020\t2\b\u0010\u0093\u0001\u001a\u00030\u0094\u00012\b\u0010\u0095\u0001\u001a\u00030\u0094\u0001H\u0002J\u001f\u0010\u0096\u0001\u001a\u0004\u0018\u0001032\u0007\u0010\u0097\u0001\u001a\u0002032\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H\u0096\u0001J\u0015\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u001b2\u0007\u0010\u0097\u0001\u001a\u00020\u001bH\u0096\u0001J\u000b\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0096\u0001J4\u0010\u009d\u0001\u001a\u00030\u009c\u00012\r\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00152\r\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00152\n\u0010 \u0001\u001a\u0005\u0018\u00010\u009c\u0001H\u0016J\n\u0010¡\u0001\u001a\u00030¢\u0001H\u0002J'\u0010£\u0001\u001a\u00030¤\u00012\u0011\u0010¥\u0001\u001a\f\u0012\u0007\b\u0000\u0012\u00030§\u00010¦\u00012\b\u0010¨\u0001\u001a\u00030©\u0001H\u0002J\u001c\u0010ª\u0001\u001a\u00020\t2\u0007\u0010«\u0001\u001a\u0002032\u0007\u0010¬\u0001\u001a\u000203H\u0096\u0001J\u0019\u0010\u00ad\u0001\u001a\u00020\u001b2\r\u0010®\u0001\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015H\u0096\u0001J\u0019\u0010\u00ad\u0001\u001a\u0002032\r\u0010®\u0001\u001a\b\u0012\u0004\u0012\u0002030\u0015H\u0096\u0001J\u0014\u0010¯\u0001\u001a\u00030°\u00012\u0007\u0010\u0097\u0001\u001a\u000203H\u0096\u0001J\"\u0010±\u0001\u001a\u00030\u009c\u00012\u0015\u0010²\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0090\u0001\u0012\u0004\u0012\u00020\u001b0³\u0001H\u0096\u0001J\u0014\u0010´\u0001\u001a\t\u0012\u0004\u0012\u00020!0µ\u0001*\u00020gH\u0016J\u000f\u0010¶\u0001\u001a\u00030·\u0001*\u00020\u001bH\u0096\u0001J\u000f\u0010¸\u0001\u001a\u00030¹\u0001*\u000203H\u0096\u0001J\u0011\u0010º\u0001\u001a\u0005\u0018\u00010»\u0001*\u000203H\u0096\u0001J\u0011\u0010¼\u0001\u001a\u0005\u0018\u00010½\u0001*\u000203H\u0096\u0001J\u0012\u0010¾\u0001\u001a\u0005\u0018\u00010¿\u0001*\u00030À\u0001H\u0096\u0001J\u0011\u0010Á\u0001\u001a\u0005\u0018\u00010À\u0001*\u00020\u001bH\u0096\u0001J\u0010\u0010Â\u0001\u001a\u0004\u0018\u000103*\u00020\u001bH\u0096\u0001J\u0012\u0010Ã\u0001\u001a\u0007\u0012\u0002\b\u00030Ä\u0001*\u00020!H\u0002J\u0012\u0010Ã\u0001\u001a\u0007\u0012\u0002\b\u00030Å\u0001*\u00020]H\u0002J\u0012\u0010Ã\u0001\u001a\u0007\u0012\u0002\b\u00030Æ\u0001*\u00020\u0017H\u0002J\u0012\u0010Ã\u0001\u001a\u0007\u0012\u0002\b\u00030Ç\u0001*\u00020gH\u0002J\u000e\u0010Ã\u0001\u001a\u00030È\u0001*\u00020FH\u0002J\u000e\u0010Ã\u0001\u001a\u00030É\u0001*\u00020&H\u0002J\u000e\u0010Ã\u0001\u001a\u00030Ê\u0001*\u00020)H\u0002J\u000e\u0010Ã\u0001\u001a\u00030Ë\u0001*\u00020\u001cH\u0002J\u000e\u0010Ã\u0001\u001a\u00030Ì\u0001*\u00020:H\u0002J\u000f\u0010Í\u0001\u001a\u00030Î\u0001*\u00020\u001bH\u0096\u0001J\u0010\u0010Ï\u0001\u001a\u00030\u0099\u0001*\u00030»\u0001H\u0096\u0001J!\u0010Ð\u0001\u001a\r\u0012\t\u0012\u0007\u0012\u0002\b\u00030Æ\u00010\u0015*\u00020&2\u0007\u0010Ñ\u0001\u001a\u00020\tH\u0016J\u0013\u0010Ò\u0001\u001a\b\u0012\u0004\u0012\u00020`0\u0015*\u00020&H\u0016J\u000f\u0010Ó\u0001\u001a\u0004\u0018\u00010&*\u00020)H\u0016J \u0010Ô\u0001\u001a\n\u0012\u0004\u0012\u000203\u0018\u00010\u0015*\u0002032\b\u0010Õ\u0001\u001a\u00030\u0090\u0001H\u0096\u0001J\u001a\u0010Ö\u0001\u001a\u00030Î\u0001*\u00030¹\u00012\b\u0010×\u0001\u001a\u00030·\u0001H\u0096\u0003J\u0019\u0010Ø\u0001\u001a\u00030Î\u0001*\u00020\u001b2\b\u0010×\u0001\u001a\u00030·\u0001H\u0096\u0001J\u001b\u0010Ù\u0001\u001a\u0005\u0018\u00010Î\u0001*\u0002032\b\u0010×\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0015\u0010Ú\u0001\u001a\t\u0012\u0005\u0012\u00030Î\u00010\u0015*\u00020\u001bH\u0096\u0001J\u0015\u0010Û\u0001\u001a\t\u0012\u0005\u0012\u00030Ü\u00010\u0015*\u00020\u001bH\u0096\u0001J+\u0010Ý\u0001\u001a\n\u0012\u0005\u0012\u00030§\u00010Þ\u0001*\u0007\u0012\u0002\b\u00030ß\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0007\u0010à\u0001\u001a\u00020\u0004H\u0016J\u0015\u0010á\u0001\u001a\t\u0012\u0005\u0012\u00030Ü\u00010\u0015*\u00020\u001bH\u0096\u0001J!\u0010â\u0001\u001a\r\u0012\t\u0012\u0007\u0012\u0002\b\u00030Ä\u00010\u0015*\u00020&2\u0007\u0010ã\u0001\u001a\u00020`H\u0016J/\u0010ä\u0001\u001a\u00030¤\u0001*\u00030©\u00012\u0015\u0010¥\u0001\u001a\u0010\u0012\u000b\b\u0000\u0012\u0007\u0012\u0002\b\u00030Ä\u00010¦\u00012\u0007\u0010ã\u0001\u001a\u00020`H\u0002J\u0019\u0010å\u0001\u001a\u00020n*\u00030\u0090\u00012\b\u0010×\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0015\u0010æ\u0001\u001a\b\u0012\u0004\u0012\u00020n0\u0015*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ç\u0001\u001a\u00020\u001b*\u00030Î\u0001H\u0096\u0001J\u000f\u0010è\u0001\u001a\u00030\u0090\u0001*\u00020nH\u0096\u0001J\u0011\u0010é\u0001\u001a\u0004\u0018\u00010n*\u00030\u0090\u0001H\u0096\u0001J\u0018\u0010ê\u0001\u001a\u00020\u001b*\u00020n2\b\u0010×\u0001\u001a\u00030·\u0001H\u0096\u0001J\u0014\u0010ë\u0001\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015*\u00020nH\u0096\u0001J\u000f\u0010{\u001a\u00030ì\u0001*\u00030Î\u0001H\u0096\u0001J\u000e\u0010{\u001a\u00030ì\u0001*\u00020nH\u0096\u0001J\u000e\u0010í\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010î\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u001c\u0010ï\u0001\u001a\u00020\t*\u00020n2\f\b\u0002\u0010ð\u0001\u001a\u0005\u0018\u00010\u0090\u0001H\u0096\u0001J\r\u0010ñ\u0001\u001a\u00020\t*\u00020!H\u0016J\u000f\u0010ò\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ó\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ô\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010õ\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010ö\u0001\u001a\u00020\t*\u000203H\u0096\u0001J\u000f\u0010÷\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ø\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010ù\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ú\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000f\u0010û\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010ü\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010ý\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000f\u0010ý\u0001\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010þ\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010ÿ\u0001\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000f\u0010\u0080\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010\u0081\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u0082\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000f\u0010\u0083\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010\u0084\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010\u0085\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010\u0086\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u0087\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0087\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000e\u0010\u0088\u0002\u001a\u00020\t*\u00020\u001bH\u0097\u0001J\r\u0010\u0089\u0002\u001a\u00020\t*\u00020&H\u0016J\u000e\u0010\u008a\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000f\u0010\u008b\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u008c\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u008d\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u008e\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000f\u0010\u008f\u0002\u001a\u00020\t*\u00030»\u0001H\u0096\u0001J\u000e\u0010\u0090\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000f\u0010\u0091\u0002\u001a\u00020\t*\u00030»\u0001H\u0096\u0001J\u000e\u0010\u0092\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0093\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u0094\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000f\u0010\u0095\u0002\u001a\u00020\t*\u00030Î\u0001H\u0096\u0001J\u000e\u0010\u0096\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000e\u0010\u0097\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000e\u0010\u0098\u0002\u001a\u00020\t*\u000203H\u0096\u0001J\u000f\u0010\u0099\u0002\u001a\u00020\t*\u00030\u0090\u0001H\u0096\u0001J\u000e\u0010\u009a\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u000e\u0010\u009b\u0002\u001a\u00020\t*\u00020\u001bH\u0096\u0001J\u0017\u0010\u009c\u0002\u001a\n\u0012\u0005\u0012\u00030Î\u00010\u009d\u0002*\u00030¹\u0001H\u0096\u0003J\u000f\u0010\u009e\u0002\u001a\u000203*\u00030À\u0001H\u0096\u0001J\u000e\u0010\u009f\u0002\u001a\u000203*\u00020\u001bH\u0096\u0001J\u0011\u0010 \u0002\u001a\u0004\u0018\u00010\u001b*\u00030»\u0001H\u0096\u0001J\u000e\u0010¡\u0002\u001a\u00020\u001b*\u00020\u001bH\u0096\u0001J\u000e\u0010¢\u0002\u001a\u000203*\u000203H\u0096\u0001J\u000f\u0010£\u0002\u001a\u000203*\u00030½\u0001H\u0096\u0001J\u000e\u0010¤\u0002\u001a\u000203*\u000203H\u0096\u0001J\u0010\u0010¥\u0002\u001a\u00030·\u0001*\u00030\u0090\u0001H\u0096\u0001J\u0015\u0010¦\u0002\u001a\t\u0012\u0004\u0012\u00020\u001b0Þ\u0001*\u000203H\u0096\u0001J\u0010\u0010§\u0002\u001a\u00030Î\u0001*\u00030¨\u0002H\u0096\u0001J\u0019\u0010©\u0002\u001a\u00030Î\u0001*\u00030Î\u00012\u0007\u0010ª\u0002\u001a\u00020\u001bH\u0096\u0001J\u0018\u0010«\u0002\u001a\u00020\u001b*\u00030\u009c\u00012\u0007\u0010\u0097\u0001\u001a\u00020\u001bH\u0096\u0001J\u0016\u0010¬\u0002\u001a\u00020\t*\u00020!2\u0007\u0010\u00ad\u0002\u001a\u00020&H\u0016J\u0010\u0010®\u0002\u001a\u00030·\u0001*\u00030¹\u0001H\u0096\u0001J\u0016\u0010¯\u0002\u001a\t\u0012\u0004\u0012\u00020\u001b0Þ\u0001*\u00030\u0090\u0001H\u0096\u0001J\u0010\u0010°\u0002\u001a\u00030¨\u0002*\u00030»\u0001H\u0096\u0001J\u000f\u0010°\u0002\u001a\u00030\u0090\u0001*\u00020\u001bH\u0096\u0001J\u000f\u0010°\u0002\u001a\u00030\u0090\u0001*\u000203H\u0096\u0001J\u0010\u0010±\u0002\u001a\u00030\u0090\u0001*\u00030\u0090\u0001H\u0096\u0001J\u000f\u0010²\u0002\u001a\u000203*\u00030À\u0001H\u0096\u0001J\u000f\u0010³\u0002\u001a\u00030·\u0001*\u00020nH\u0096\u0001J\u000e\u0010´\u0002\u001a\u000203*\u00020\u001bH\u0096\u0001J\u0017\u0010µ\u0002\u001a\u00020\u001b*\u00020\u001b2\u0007\u0010¶\u0002\u001a\u00020\tH\u0096\u0001J\u0017\u0010µ\u0002\u001a\u000203*\u0002032\u0007\u0010¶\u0002\u001a\u00020\tH\u0096\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000bR\u0014\u0010\u0012\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000bR\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\u001f\u001a\u00020 *\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\u00020%*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0018\u0010$\u001a\u00020%*\u00020)8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b'\u0010*R\u0018\u0010+\u001a\u00020,*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0018\u0010/\u001a\u00020\u001b*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u001a\u00102\u001a\u0004\u0018\u000103*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u001a\u00106\u001a\u0004\u0018\u00010\u001b*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0018\u00109\u001a\u00020\t*\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b;\u0010<R\u0018\u0010=\u001a\u00020\t*\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?R\u0018\u0010@\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0018\u0010C\u001a\u00020\t*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bC\u0010DR\u0018\u0010E\u001a\u00020\t*\u00020F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bE\u0010GR\u0018\u0010H\u001a\u00020\t*\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bH\u0010<R\u0018\u0010I\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bI\u0010BR\u0018\u0010J\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010BR\u0018\u0010K\u001a\u00020\t*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bK\u0010DR\u0018\u0010L\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bL\u0010BR\u0018\u0010M\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010BR\u0018\u0010M\u001a\u00020\t*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010DR\u0018\u0010N\u001a\u00020\t*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010DR\u0018\u0010O\u001a\u00020\t*\u00020F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bO\u0010GR\u0018\u0010P\u001a\u00020\t*\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bP\u0010<R\u0018\u0010Q\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010BR\u0018\u0010R\u001a\u00020\t*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bR\u0010SR\u0018\u0010T\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bT\u0010BR\u0018\u0010U\u001a\u00020\t*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bU\u0010BR\u0018\u0010V\u001a\u00020\t*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bV\u0010DR\u0018\u0010W\u001a\u00020\t*\u00020F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bW\u0010GR\u0018\u0010X\u001a\u00020\t*\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bX\u0010<R\u001a\u0010Y\u001a\u0004\u0018\u00010Z*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b[\u0010\\R\u001a\u0010Y\u001a\u0004\u0018\u00010Z*\u00020]8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b[\u0010^R\u0018\u0010_\u001a\u00020`*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010bR\u0018\u0010_\u001a\u00020`*\u00020:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\ba\u0010cR\u0018\u0010d\u001a\u00020\u001b*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\be\u00108R\u001a\u0010f\u001a\u0004\u0018\u00010g*\u00020F8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bh\u0010iR\u001e\u0010j\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0015*\u00020&8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bk\u0010lR\u0018\u0010m\u001a\u0004\u0018\u00010n*\u00020oX\u0096\u0005¢\u0006\u0006\u001a\u0004\bp\u0010qR\u001e\u0010r\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015*\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bs\u0010tR\u001e\u0010r\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0015*\u00020]8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bs\u0010uR\u001e\u0010v\u001a\b\u0012\u0004\u0012\u00020:0\u0015*\u00020g8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bw\u0010xR\u0018\u0010y\u001a\u00020z*\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b{\u0010|R\u0019\u0010}\u001a\u00020~*\u00020!8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u007f\u0010\u0080\u0001R\u0019\u0010}\u001a\u00020~*\u00020]8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b\u007f\u0010\u0081\u0001¨\u0006¹\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatchingContextImpl;", "Lorg/jetbrains/kotlin/fir/FirExpectActualMatchingContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "actualSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "allowClassActualizationWithWiderVisibility", "", "getAllowClassActualizationWithWiderVisibility", "()Z", "allowTransitiveSupertypesActualization", "getAllowTransitiveSupertypesActualization", "enumConstructorsAreAlwaysCompatible", "getEnumConstructorsAreAlwaysCompatible", "shouldCheckAbsenceOfDefaultParamsInActual", "getShouldCheckAbsenceOfDefaultParamsInActual", "shouldCheckReturnTypesOfCallables", "getShouldCheckReturnTypesOfCallables", "annotations", "", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;", "getAnnotations", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Ljava/util/List;", "bounds", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;", "getBounds", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Ljava/util/List;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;", "getCallableId", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/name/CallableId;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;", "getClassId", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;)Lorg/jetbrains/kotlin/name/ClassId;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "getClassKind", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "defaultType", "getDefaultType", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "getDispatchReceiverType", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "extensionReceiverType", "getExtensionReceiverType", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "hasDefaultValue", "Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;", "getHasDefaultValue", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Z", "hasSourceAnnotationsErased", "getHasSourceAnnotationsErased", "(Lorg/jetbrains/kotlin/mpp/DeclarationSymbolMarker;)Z", "hasStableParameterNames", "getHasStableParameterNames", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Z", "isCompanion", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Z", "isConst", "Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Z", "isCrossinline", "isExpect", "isExternal", "isFun", "isInfix", "isInline", "isInner", "isLateinit", "isNoinline", "isOperator", "isReified", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Z", "isSuspend", "isTailrec", "isValue", "isVar", "isVararg", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Modality;", "parameterName", "Lorg/jetbrains/kotlin/name/Name;", "getParameterName", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/mpp/ValueParameterSymbolMarker;)Lorg/jetbrains/kotlin/name/Name;", "returnType", "getReturnType", "setter", "Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "getSetter", "(Lorg/jetbrains/kotlin/mpp/PropertySymbolMarker;)Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;", "superTypes", "getSuperTypes", "(Lorg/jetbrains/kotlin/mpp/RegularClassSymbolMarker;)Ljava/util/List;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "typeParameters", "getTypeParameters", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Ljava/util/List;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Ljava/util/List;", "valueParameters", "getValueParameters", "(Lorg/jetbrains/kotlin/mpp/FunctionSymbolMarker;)Ljava/util/List;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "getVariance", "(Lorg/jetbrains/kotlin/mpp/TypeParameterSymbolMarker;)Lorg/jetbrains/kotlin/types/Variance;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lorg/jetbrains/kotlin/mpp/CallableSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/mpp/ClassLikeSymbolMarker;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "actualTypeIsSubtypeOfExpectType", "expectType", "actualType", "areAnnotationArgumentsEqual", "expression1", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "expression2", "expectAnnotation", "actualAnnotation", "collectionArgumentsCompatibilityCheckStrategy", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualCollectionArgumentsCompatibilityCheckStrategy;", "areCompatibleExpectActualTypes", "areEqualTypeConstructors", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "areFirAnnotationsEqual", "annotation1", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "annotation2", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createExpectActualTypeParameterSubstitutor", "expectTypeParameters", "actualTypeParameters", "parentSubstitutor", "createTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "getConstructorsTo", "", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "identicalArguments", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "intersectTypes", SpecTypePair.NAME_types, "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "allOverriddenDeclarationsRecursive", "Lkotlin/sequences/Sequence;", "argumentsCount", "", "asArgumentList", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "asTypeArgument", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "captureStatus", "collectAllMembers", "isActualDeclaration", "collectEnumEntryNames", "expandToRegularClass", "fastCorrespondingSupertypes", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "get", "index", "getArgument", "getArgumentOrNull", "getArguments", "getAttributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "getConstructors", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "session", "getCustomAttributes", "getMembersForExpectClass", "name", "getMembersTo", "getParameter", "getParameters", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUpperBound", "getUpperBounds", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "hasCustomAttributes", "hasFlexibleNullability", "hasRecursiveBounds", "selfConstructor", "isAnnotationConstructor", "isAnonymous", "isAnyConstructor", "isCapturedDynamic", "isCapturedType", "isClassType", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDefinitelyClassTypeConstructor", "isDefinitelyNotNullType", "isDenotable", "isDynamic", "isError", "isFlexible", "isFlexibleNothing", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralType", "isIntegerLiteralTypeConstructor", "isInterface", "isIntersection", "isLocalType", "isMarkedNullable", "isNotNullTypeParameter", "isNotSamInterface", "isNothing", "isNothingConstructor", "isNullableAny", "isNullableNothing", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isSimpleType", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUninferredParameter", "iterator", "", "lowerBound", "lowerBoundIfFlexible", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "originalIfDefinitelyNotNullable", "parametersCount", "possibleIntegerTypes", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceType", "newType", "safeSubstitute", "shouldSkipMatching", "containingExpectClass", "size", "supertypes", "typeConstructor", "unwrapStubTypeVariableConstructor", "upperBound", "upperBoundCount", "upperBoundIfFlexible", "withNullability", "nullable", "AnnotationCallInfoImpl", "Factory", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExpectActualMatchingContextImpl implements FirExpectActualMatchingContext, TypeSystemContext {
    private final /* synthetic */ ConeInferenceContext $$delegate_0;
    private final FirSession actualSession;
    private final ScopeSession scopeSession;

    public /* synthetic */ FirExpectActualMatchingContextImpl(FirSession firSession, ScopeSession scopeSession, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2) {
        Intrinsics.checkNotNullParameter(c1, "c1");
        Intrinsics.checkNotNullParameter(c2, "c2");
        return this.$$delegate_0.areEqualTypeConstructors(c1, c2);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int argumentsCount(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.argumentsCount(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asArgumentList(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asCapturedType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.asDefinitelyNotNullType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.asDynamicType(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asFlexibleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.asTypeArgument(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(status, "status");
        return this.$$delegate_0.captureFromArguments(type, status);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker captureFromExpression(KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.captureFromExpression(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.captureStatus(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker createEmptySubstitutor() {
        return this.$$delegate_0.createEmptySubstitutor();
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker constructor) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.$$delegate_0.fastCorrespondingSupertypes(simpleTypeMarker, constructor);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.get(typeArgumentListMarker, i);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getAllowClassActualizationWithWiderVisibility() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getAllowTransitiveSupertypesActualization() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArgument(kotlinTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.getArgumentOrNull(simpleTypeMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getArguments(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.getCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getEnumConstructorsAreAlwaysCompatible() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameter(typeConstructorMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<TypeParameterMarker> getParameters(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getParameters(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckAbsenceOfDefaultParamsInActual() {
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getShouldCheckReturnTypesOfCallables() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getType(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker getTypeConstructor(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getTypeConstructor(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeVariableTypeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameter(typeVariableTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.getTypeParameterClassifier(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBound(typeParameterMarker, i);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getUpperBounds(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.getVariance(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeVariance getVariance(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.getVariance(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasCustomAttributes(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.hasFlexibleNullability(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.hasRecursiveBounds(typeParameterMarker, typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemOptimizationContext
    public boolean identicalArguments(SimpleTypeMarker a, SimpleTypeMarker b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return this.$$delegate_0.identicalArguments(a, b);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes(types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    public SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types) {
        Intrinsics.checkNotNullParameter(types, "types");
        return this.$$delegate_0.intersectTypes((List<? extends KotlinTypeMarker>) types);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnonymous(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnonymous(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isAnyConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCapturedType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isCapturedType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isClassType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isCommonFinalClassConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyClassTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDefinitelyClassTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDefinitelyNotNullType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDenotable(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isDenotable(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isDynamic(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isError(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isError(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isError(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isFlexibleNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isFlexibleNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerConstantOperatorTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralConstantTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isInterface(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isInterface(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isIntersection(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isIntersection(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isLocalType(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isLocalType(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isMarkedNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    @ObsoleteTypeKind
    public boolean isNotNullTypeParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNotNullTypeParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isNothingConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableAny(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableAny(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableNothing(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableNothing(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isNullableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isNullableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isOldCapturedType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isPrimitiveType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.isProjectionNotNull(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isRawType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isRawType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSimpleType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isSimpleType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isSingleClassifierType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStarProjection(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return this.$$delegate_0.isStarProjection(typeArgumentMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubType(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubType(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForBuilderInference(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.isStubTypeForVariableInSubtyping(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.isTypeParameterTypeConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isTypeVariableType(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.isUninferredParameter(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Iterator<TypeArgumentMarker> iterator(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.iterator(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.lowerBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.lowerBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.lowerType(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
        Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "<this>");
        return this.$$delegate_0.original(definitelyNotNullTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker originalIfDefinitelyNotNullable(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.originalIfDefinitelyNotNullable(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int parametersCount(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.parametersCount(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.possibleIntegerTypes(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeConstructorMarker, "<this>");
        return this.$$delegate_0.projection(capturedTypeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker newType) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        return this.$$delegate_0.replaceType(typeArgumentMarker, newType);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSubstitutorMarker, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.safeSubstitute(typeSubstitutorMarker, type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentListMarker, "<this>");
        return this.$$delegate_0.size(typeArgumentListMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.$$delegate_0.substitutionSupertypePolicy(type);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.supertypes(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(capturedTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.typeConstructor(simpleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        return this.$$delegate_0.typeSubstitutorByTypeConstructor(map);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        return this.$$delegate_0.unwrapStubTypeVariableConstructor(typeConstructorMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker) {
        Intrinsics.checkNotNullParameter(flexibleTypeMarker, "<this>");
        return this.$$delegate_0.upperBound(flexibleTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public int upperBoundCount(TypeParameterMarker typeParameterMarker) {
        Intrinsics.checkNotNullParameter(typeParameterMarker, "<this>");
        return this.$$delegate_0.upperBoundCount(typeParameterMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.upperBoundIfFlexible(kotlinTypeMarker);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    public SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "<this>");
        return this.$$delegate_0.withNullability(simpleTypeMarker, z);
    }

    private FirExpectActualMatchingContextImpl(FirSession firSession, ScopeSession scopeSession) {
        this.actualSession = firSession;
        this.scopeSession = scopeSession;
        this.$$delegate_0 = TypeComponentsKt.getTypeContext(firSession);
    }

    private final FirBasedSymbol<?> asSymbol(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNull(declarationSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>");
        return (FirBasedSymbol) declarationSymbolMarker;
    }

    private final FirCallableSymbol<?> asSymbol(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNull(callableSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
        return (FirCallableSymbol) callableSymbolMarker;
    }

    private final FirFunctionSymbol<?> asSymbol(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNull(functionSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol<*>");
        return (FirFunctionSymbol) functionSymbolMarker;
    }

    private final FirPropertySymbol asSymbol(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNull(propertySymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
        return (FirPropertySymbol) propertySymbolMarker;
    }

    private final FirValueParameterSymbol asSymbol(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNull(valueParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol");
        return (FirValueParameterSymbol) valueParameterSymbolMarker;
    }

    private final FirTypeParameterSymbol asSymbol(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNull(typeParameterSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol");
        return (FirTypeParameterSymbol) typeParameterSymbolMarker;
    }

    private final FirClassLikeSymbol<?> asSymbol(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNull(classLikeSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol<*>");
        return (FirClassLikeSymbol) classLikeSymbolMarker;
    }

    private final FirRegularClassSymbol asSymbol(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNull(regularClassSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol");
        return (FirRegularClassSymbol) regularClassSymbolMarker;
    }

    private final FirTypeAliasSymbol asSymbol(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNull(typeAliasSymbolMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol");
        return (FirTypeAliasSymbol) typeAliasSymbolMarker;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getClassId();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassId getClassId(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        return asSymbol(typeAliasSymbolMarker).getClassId();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public CallableId getCallableId(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getCallableId();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asSymbol(typeParameterSymbolMarker).getName();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Name getParameterName(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asSymbol(valueParameterSymbolMarker).getName();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public RegularClassSymbolMarker expandToRegularClass(TypeAliasSymbolMarker typeAliasSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeAliasSymbolMarker, "<this>");
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(TypeExpansionUtilsKt.fullyExpandedType(FirTypeUtilsKt.getConeType(asSymbol(typeAliasSymbolMarker).getResolvedExpandedTypeRef()), this.actualSession), this.actualSession);
        return symbol instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public ClassKind getClassKind(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getClassKind();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCompanion(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getResolvedStatus().isCompanion();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInner(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getResolvedStatus().isInner();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getResolvedStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isValue(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getResolvedStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isFun(RegularClassSymbolMarker regularClassSymbolMarker) {
        FirResolvedDeclarationStatus resolvedStatus;
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        FirRegularClassSymbol firRegularClassSymbolAsSymbol = asSymbol(regularClassSymbolMarker);
        if (firRegularClassSymbolAsSymbol.getOrigin() instanceof FirDeclarationOrigin.Java) {
            firRegularClassSymbolAsSymbol = null;
        }
        if (firRegularClassSymbolAsSymbol == null || (resolvedStatus = firRegularClassSymbolAsSymbol.getResolvedStatus()) == null) {
            return false;
        }
        return resolvedStatus.isFun();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        return asSymbol(classLikeSymbolMarker).getTypeParameterSymbols();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        return asSymbol(classLikeSymbolMarker).getResolvedStatus().getModality();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(ClassLikeSymbolMarker classLikeSymbolMarker) {
        Intrinsics.checkNotNullParameter(classLikeSymbolMarker, "<this>");
        return asSymbol(classLikeSymbolMarker).getResolvedStatus().getVisibility();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Modality getModality(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().getModality();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Visibility getVisibility(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().getVisibility();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExpect(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isExpect();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInline(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isInline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isSuspend(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isSuspend();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isExternal(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isExternal();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isInfix(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isInfix();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isOperator(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isOperator();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isTailrec(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedStatus().isTailRec();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVar(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asSymbol(propertySymbolMarker).isVar();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isLateinit(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asSymbol(propertySymbolMarker).getResolvedStatus().isLateInit();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isConst(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asSymbol(propertySymbolMarker).getResolvedStatus().isConst();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public FunctionSymbolMarker getSetter(PropertySymbolMarker propertySymbolMarker) {
        Intrinsics.checkNotNullParameter(propertySymbolMarker, "<this>");
        return asSymbol(propertySymbolMarker).getSetterSymbol();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getSuperTypes(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return asSymbol(regularClassSymbolMarker).getResolvedSuperTypes();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getDefaultType(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return ScopeUtilsKt.defaultType(asSymbol(regularClassSymbolMarker));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<FirBasedSymbol<?>> collectAllMembers(RegularClassSymbolMarker regularClassSymbolMarker, boolean z) {
        FirSession session;
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        final FirRegularClassSymbol firRegularClassSymbolAsSymbol = asSymbol(regularClassSymbolMarker);
        if (z) {
            session = this.actualSession;
        } else if (!z) {
            session = firRegularClassSymbolAsSymbol.getModuleData().getSession();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(ScopeUtilsKt.defaultType(firRegularClassSymbolAsSymbol), session, this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        if (firTypeScopeScope == null) {
            return CollectionsKt.emptyList();
        }
        final ArrayList arrayList = new ArrayList();
        Iterator<Name> it = firTypeScopeScope.getCallableNames().iterator();
        while (it.hasNext()) {
            getMembersTo(firTypeScopeScope, arrayList, it.next());
        }
        for (Name name2 : firTypeScopeScope.getClassifierNames()) {
            final Function1<FirClassifierSymbol<?>, Unit> function1 = new Function1<FirClassifierSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl$collectAllMembers$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol) {
                    invoke2(firClassifierSymbol);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    if ((it2 instanceof FirRegularClassSymbol) && Intrinsics.areEqual(((FirRegularClassSymbol) it2).getClassId().getParentClassId(), firRegularClassSymbolAsSymbol.getClassId())) {
                        arrayList.add(it2);
                    }
                }
            };
            firTypeScopeScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl$collectAllMembers$lambda$1$$inlined$processClassifiersByName$1
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor coneSubstitutor) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    Intrinsics.checkNotNullParameter(coneSubstitutor, "<anonymous parameter 1>");
                    function1.invoke(symbol);
                }
            });
        }
        getConstructorsTo(arrayList, firTypeScopeScope);
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.FirExpectActualMatchingContext, org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<FirCallableSymbol<?>> getMembersForExpectClass(RegularClassSymbolMarker regularClassSymbolMarker, Name name2) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        FirRegularClassSymbol firRegularClassSymbolAsSymbol = asSymbol(regularClassSymbolMarker);
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(ScopeUtilsKt.defaultType(firRegularClassSymbolAsSymbol), firRegularClassSymbolAsSymbol.getModuleData().getSession(), this.scopeSession, FakeOverrideTypeCalculator.DoNothing.INSTANCE, FirResolvePhase.STATUS);
        if (firTypeScopeScope == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        getMembersTo(firTypeScopeScope, arrayList, name2);
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.FirExpectActualMatchingContext
    public Collection<FirConstructorSymbol> getConstructors(FirClassSymbol<?> firClassSymbol, ScopeSession scopeSession, FirSession session) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(session, "session");
        ArrayList arrayList = new ArrayList();
        getConstructorsTo(arrayList, FirKotlinScopeProviderKt.unsubstitutedScope(firClassSymbol, session, scopeSession, false, FirResolvePhase.STATUS));
        return arrayList;
    }

    private final void getConstructorsTo(List<? super FirConstructorSymbol> destination, FirTypeScope scope) {
        Iterator<T> it = FirScopeKt.getDeclaredConstructors(scope).iterator();
        while (it.hasNext()) {
            destination.add((FirConstructorSymbol) it.next());
        }
    }

    private final void getMembersTo(FirTypeScope firTypeScope, final List<? super FirCallableSymbol<?>> list, Name name2) {
        firTypeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl.getMembersTo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                list.add(it);
            }
        });
        firTypeScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl.getMembersTo.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                list.add(it);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<Name> collectEnumEntryNames(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        Collection<FirEnumEntry> collectionCollectEnumEntries = DeclarationUtilsKt.collectEnumEntries((FirClass) asSymbol(regularClassSymbolMarker).getFir());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionCollectEnumEntries, 10));
        Iterator<T> it = collectionCollectEnumEntries.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirEnumEntry) it.next()).getName());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public SimpleTypeMarker getDispatchReceiverType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getDispatchReceiverType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getExtensionReceiverType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        FirResolvedTypeRef resolvedReceiverTypeRef = asSymbol(callableSymbolMarker).getResolvedReceiverTypeRef();
        return resolvedReceiverTypeRef != null ? FirTypeUtilsKt.getConeType(resolvedReceiverTypeRef) : null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public KotlinTypeMarker getReturnType(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getResolvedReturnType().getType();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<TypeParameterSymbolMarker> getTypeParameters(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getTypeParameterSymbols();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Sequence<CallableSymbolMarker> allOverriddenDeclarationsRecursive(FunctionSymbolMarker functionSymbolMarker) {
        FirRegularClassSymbol firRegularClassSymbol;
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        FirFunctionSymbol<?> firFunctionSymbolAsSymbol = asSymbol(functionSymbolMarker);
        if (firFunctionSymbolAsSymbol instanceof FirConstructorSymbol ? true : firFunctionSymbolAsSymbol instanceof FirFunctionWithoutNameSymbol) {
            return SequencesKt.sequenceOf(functionSymbolMarker);
        }
        if (firFunctionSymbolAsSymbol instanceof FirNamedFunctionSymbol) {
            FirSession session = firFunctionSymbolAsSymbol.getModuleData().getSession();
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firFunctionSymbolAsSymbol);
            return (coneClassLikeLookupTagContainingClassLookupTag == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, session)) == null) ? SequencesKt.sequenceOf(firFunctionSymbolAsSymbol) : SequencesKt.filter(SequencesKt.plus(SequencesKt.sequenceOf(firFunctionSymbolAsSymbol), CollectionsKt.asSequence(ScopesKt.overriddenFunctions((FirNamedFunctionSymbol) firFunctionSymbolAsSymbol, firRegularClassSymbol, session, this.scopeSession))), new Function1<FirFunctionSymbol<?>, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl.allOverriddenDeclarationsRecursive.1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(FirFunctionSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf((ClassMembersKt.isSubstitutionOrIntersectionOverride(it) || Intrinsics.areEqual(it.getOrigin(), FirDeclarationOrigin.Delegated.INSTANCE)) ? false : true);
                }
            });
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ValueParameterSymbolMarker> getValueParameters(FunctionSymbolMarker functionSymbolMarker) {
        Intrinsics.checkNotNullParameter(functionSymbolMarker, "<this>");
        return asSymbol(functionSymbolMarker).getValueParameterSymbols();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isVararg(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asSymbol(valueParameterSymbolMarker).isVararg();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNoinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asSymbol(valueParameterSymbolMarker).isNoinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isCrossinline(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asSymbol(valueParameterSymbolMarker).isCrossinline();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasDefaultValue(ValueParameterSymbolMarker valueParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(valueParameterSymbolMarker, "<this>");
        return asSymbol(valueParameterSymbolMarker).getHasDefaultValue();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isAnnotationConstructor(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        FirCallableSymbol<?> firCallableSymbolAsSymbol = asSymbol(callableSymbolMarker);
        return DeclarationUtilsKt.isAnnotationConstructor(firCallableSymbolAsSymbol, firCallableSymbolAsSymbol.getModuleData().getSession());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<KotlinTypeMarker> getBounds(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        List<FirResolvedTypeRef> resolvedBounds = asSymbol(typeParameterSymbolMarker).getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public Variance getVariance(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asSymbol(typeParameterSymbolMarker).getVariance();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isReified(TypeParameterSymbolMarker typeParameterSymbolMarker) {
        Intrinsics.checkNotNullParameter(typeParameterSymbolMarker, "<this>");
        return asSymbol(typeParameterSymbolMarker).isReified();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areCompatibleExpectActualTypes(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        if (expectType == null) {
            return actualType == null;
        }
        if (actualType == null) {
            return false;
        }
        return AbstractTypeChecker.INSTANCE.equalTypes(createTypeCheckerState(), expectType, actualType);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean actualTypeIsSubtypeOfExpectType(KotlinTypeMarker expectType, KotlinTypeMarker actualType) {
        Intrinsics.checkNotNullParameter(expectType, "expectType");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, createTypeCheckerState(), actualType, expectType, false, 8, (Object) null);
    }

    private final TypeCheckerState createTypeCheckerState() {
        return TypeComponentsKt.getTypeContext(this.actualSession).newTypeCheckerState(true, false);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean isNotSamInterface(RegularClassSymbolMarker regularClassSymbolMarker) {
        Intrinsics.checkNotNullParameter(regularClassSymbolMarker, "<this>");
        return !new FirSamResolver(this.actualSession, this.scopeSession, null, 4, null).isSamType(ScopeUtilsKt.defaultType(asSymbol(regularClassSymbolMarker)));
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean shouldSkipMatching(CallableSymbolMarker callableSymbolMarker, RegularClassSymbolMarker containingExpectClass) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        Intrinsics.checkNotNullParameter(containingExpectClass, "containingExpectClass");
        FirCallableSymbol<?> firCallableSymbolAsSymbol = asSymbol(callableSymbolMarker);
        FirRegularClassSymbol firRegularClassSymbolAsSymbol = asSymbol(containingExpectClass);
        if (!(firCallableSymbolAsSymbol instanceof FirConstructorSymbol)) {
            ConeSimpleKotlinType dispatchReceiverType = firCallableSymbolAsSymbol.getDispatchReceiverType();
            if (!Intrinsics.areEqual(dispatchReceiverType != null ? ConeTypeUtilsKt.getClassId(dispatchReceiverType) : null, firRegularClassSymbolAsSymbol.getClassId())) {
                return true;
            }
        }
        return ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableSymbolAsSymbol) || !isExpect(firCallableSymbolAsSymbol);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasStableParameterNames(CallableSymbolMarker callableSymbolMarker) {
        Intrinsics.checkNotNullParameter(callableSymbolMarker, "<this>");
        return asSymbol(callableSymbolMarker).getRawStatus().getHasStableParameterNames();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public List<ExpectActualMatchingContext.AnnotationCallInfo> getAnnotations(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        List<FirAnnotation> resolvedAnnotationsWithArguments = asSymbol(declarationSymbolMarker).getResolvedAnnotationsWithArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedAnnotationsWithArguments, 10));
        Iterator<T> it = resolvedAnnotationsWithArguments.iterator();
        while (it.hasNext()) {
            arrayList.add(new AnnotationCallInfoImpl(this, (FirAnnotation) it.next()));
        }
        return arrayList;
    }

    private static final FirAnnotation areAnnotationArgumentsEqual$getFirAnnotation(ExpectActualMatchingContext.AnnotationCallInfo annotationCallInfo) {
        Intrinsics.checkNotNull(annotationCallInfo, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.transformers.mpp.FirExpectActualMatchingContextImpl.AnnotationCallInfoImpl");
        return ((AnnotationCallInfoImpl) annotationCallInfo).getAnnotation();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean areAnnotationArgumentsEqual(ExpectActualMatchingContext.AnnotationCallInfo expectAnnotation, ExpectActualMatchingContext.AnnotationCallInfo actualAnnotation, ExpectActualCollectionArgumentsCompatibilityCheckStrategy collectionArgumentsCompatibilityCheckStrategy) {
        Intrinsics.checkNotNullParameter(expectAnnotation, "expectAnnotation");
        Intrinsics.checkNotNullParameter(actualAnnotation, "actualAnnotation");
        Intrinsics.checkNotNullParameter(collectionArgumentsCompatibilityCheckStrategy, "collectionArgumentsCompatibilityCheckStrategy");
        return areFirAnnotationsEqual(areAnnotationArgumentsEqual$getFirAnnotation(expectAnnotation), areAnnotationArgumentsEqual$getFirAnnotation(actualAnnotation));
    }

    private final boolean areFirAnnotationsEqual(FirAnnotation annotation1, FirAnnotation annotation2) {
        if (!areCompatibleExpectActualTypes(FirTypeUtilsKt.getConeType(annotation1.getTypeRef()), FirTypeUtilsKt.getConeType(annotation2.getTypeRef()))) {
            return false;
        }
        Map<Name, FirExpression> mapping = annotation1.getArgumentMapping().getMapping();
        Map<Name, FirExpression> mapping2 = annotation2.getArgumentMapping().getMapping();
        if (mapping.size() != mapping2.size()) {
            return false;
        }
        if (!mapping.isEmpty()) {
            for (Map.Entry<Name, FirExpression> entry : mapping.entrySet()) {
                Name key = entry.getKey();
                FirExpression value = entry.getValue();
                FirExpression firExpression = mapping2.get(key);
                if (firExpression == null || !areAnnotationArgumentsEqual(value, firExpression)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean areAnnotationArgumentsEqual(FirExpression expression1, FirExpression expression2) {
        if ((expression1 instanceof FirConstExpression) && (expression2 instanceof FirConstExpression)) {
            return Intrinsics.areEqual(((FirConstExpression) expression1).getValue(), ((FirConstExpression) expression2).getValue());
        }
        return true;
    }

    /* JADX INFO: compiled from: FirExpectActualMatchingContextImpl.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatchingContextImpl$AnnotationCallInfoImpl;", "Lorg/jetbrains/kotlin/resolve/calls/mpp/ExpectActualMatchingContext$AnnotationCallInfo;", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatchingContextImpl;Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)V", "getAnnotation", "()Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "annotationSymbol", "getAnnotationSymbol", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "isOptIn", "", "()Z", "isRetentionSource", "getAnnotationClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class AnnotationCallInfoImpl implements ExpectActualMatchingContext.AnnotationCallInfo {
        private final FirAnnotation annotation;
        private final FirAnnotation annotationSymbol;
        final /* synthetic */ FirExpectActualMatchingContextImpl this$0;

        public AnnotationCallInfoImpl(FirExpectActualMatchingContextImpl firExpectActualMatchingContextImpl, FirAnnotation annotation) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            this.this$0 = firExpectActualMatchingContextImpl;
            this.annotation = annotation;
            this.annotationSymbol = annotation;
        }

        public final FirAnnotation getAnnotation() {
            return this.annotation;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public FirAnnotation getAnnotationSymbol() {
            return this.annotationSymbol;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public ClassId getClassId() {
            return FirAnnotationUtilsKt.toAnnotationClassId(this.annotation, this.this$0.actualSession);
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isRetentionSource() {
            FirRegularClassSymbol annotationClass = getAnnotationClass();
            return (annotationClass != null ? FirRetentionAnnotationHelpersKt.getRetention(annotationClass, this.this$0.actualSession) : null) == AnnotationRetention.SOURCE;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext.AnnotationCallInfo
        public boolean isOptIn() {
            FirRegularClassSymbol annotationClass = getAnnotationClass();
            if (annotationClass != null) {
                return FirAnnotationUtilsKt.hasAnnotation(annotationClass, OptInNames.INSTANCE.getREQUIRES_OPT_IN_CLASS_ID(), this.this$0.actualSession);
            }
            return false;
        }

        private final FirRegularClassSymbol getAnnotationClass() {
            return TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(this.annotation.getAnnotationTypeRef()), this.this$0.actualSession);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public boolean getHasSourceAnnotationsErased(DeclarationSymbolMarker declarationSymbolMarker) {
        Intrinsics.checkNotNullParameter(declarationSymbolMarker, "<this>");
        FirBasedSymbol<?> firBasedSymbolAsSymbol = asSymbol(declarationSymbolMarker);
        return firBasedSymbolAsSymbol.getSource() == null && !(firBasedSymbolAsSymbol.getOrigin() instanceof FirDeclarationOrigin.Plugin);
    }

    /* JADX INFO: compiled from: FirExpectActualMatchingContextImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatchingContextImpl$Factory;", "Lorg/jetbrains/kotlin/fir/FirExpectActualMatchingContextFactory;", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/resolve/transformers/mpp/FirExpectActualMatchingContextImpl;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Factory implements FirExpectActualMatchingContextFactory {
        public static final Factory INSTANCE = new Factory();

        private Factory() {
        }

        @Override // org.jetbrains.kotlin.fir.FirExpectActualMatchingContextFactory
        public FirExpectActualMatchingContextImpl create(FirSession session, ScopeSession scopeSession) {
            Intrinsics.checkNotNullParameter(session, "session");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            return new FirExpectActualMatchingContextImpl(session, scopeSession, null);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext
    public TypeSubstitutorMarker createExpectActualTypeParameterSubstitutor(List<? extends TypeParameterSymbolMarker> expectTypeParameters, List<? extends TypeParameterSymbolMarker> actualTypeParameters, TypeSubstitutorMarker parentSubstitutor) {
        Intrinsics.checkNotNullParameter(expectTypeParameters, "expectTypeParameters");
        Intrinsics.checkNotNullParameter(actualTypeParameters, "actualTypeParameters");
        for (TypeParameterSymbolMarker typeParameterSymbolMarker : expectTypeParameters) {
            if (typeParameterSymbolMarker == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol");
            }
        }
        for (TypeParameterSymbolMarker typeParameterSymbolMarker2 : actualTypeParameters) {
            if (typeParameterSymbolMarker2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol");
            }
        }
        return ExpectActualUtilsKt.createExpectActualTypeParameterSubstitutor(expectTypeParameters, actualTypeParameters, this.actualSession, (ConeSubstitutor) parentSubstitutor);
    }
}
