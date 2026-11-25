package org.jetbrains.kotlin.fir.scopes.impl;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirOverrideService;
import org.jetbrains.kotlin.fir.scopes.FirOverrideServiceKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverrideFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverrideFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirIntersectionOverridePropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001aB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ,\u0010\u001c\u001a\u0004\u0018\u00010\u001d\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0012\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110 H\u0002J*\u0010!\u001a\u00020\"\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0012\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110 H\u0002J&\u0010$\u001a\u0018\u0012\u0014\u0012\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0012\u0004\u0012\u00020'0%0\u00072\u0006\u0010(\u001a\u00020)H\u0002J\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020,0+0\u00072\u0006\u0010(\u001a\u00020)J_\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0+0\u0007\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010(\u001a\u00020)2/\u0010.\u001a+\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020)\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u00020201\u0012\u0004\u0012\u0002020/¢\u0006\u0002\b3H\u0086\bø\u0001\u0000Ju\u00104\u001a$\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00070%0\u0007j\b\u0012\u0004\u0012\u0002H\u001e`5\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010(\u001a\u00020)2/\u0010.\u001a+\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020)\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u00020201\u0012\u0004\u0012\u0002020/¢\u0006\u0002\b3H\u0086\bø\u0001\u0000J\u0090\u0001\u00106\u001a\u000202\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0006\u00107\u001a\u0002H\u001e2\u0006\u00108\u001a\u00020\b2\u0012\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110:2\u0018\u0010;\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u001e0%0<25\u0010=\u001a1\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u001e\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020?0>\u0012\u0004\u0012\u00020?0/¢\u0006\u0002\b3H\u0002¢\u0006\u0002\u0010@J@\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0+0\u0007\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u001e\u0010B\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00070%0\u0007JF\u0010C\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u0011\"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0012\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110\u00072\u0012\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110\u0007J>\u0010E\u001a\u00020F2\u0010\u0010D\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\u0010\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\b\u0010H\u001a\u0004\u0018\u00010\u001d2\u0006\u0010I\u001a\u00020\"H\u0002J>\u0010J\u001a\u00020,2\u0010\u0010D\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\u0010\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\b\u0010H\u001a\u0004\u0018\u00010\u001d2\u0006\u0010I\u001a\u00020\"H\u0002J>\u0010K\u001a\u00020L2\u0010\u0010D\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\u0010\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\b\u0010H\u001a\u0004\u0018\u00010\u001d2\u0006\u0010I\u001a\u00020\"H\u0002J£\u0001\u0010M\u001a\u0002HN\"\u0010\b\u0000\u0010N\u0018\u0001*\b\u0012\u0004\u0012\u0002HP0O\"\b\b\u0001\u0010P*\u00020Q2\u0010\u0010D\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\u0010\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 2\"\u0010R\u001a\u001e\u0012\u0004\u0012\u00020S\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 \u0012\u0004\u0012\u0002HN0>2/\u0010T\u001a+\u0012\u0004\u0012\u0002HN\u0012\u0004\u0012\u0002HP\u0012\u0015\u0012\u0013\u0018\u00010U¢\u0006\f\bV\u0012\b\b(\u0012\u0004\b\b(W\u0012\u0004\u0012\u0002HP0/H\u0082\b¢\u0006\u0002\u0010XJ\u001c\u0010Y\u001a\u0004\u0018\u00010U2\u0010\u0010G\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100 H\u0002J,\u0010Z\u001a\u0002022\u0006\u0010(\u001a\u00020)2\u001c\u0010[\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u0002020>Jx\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u001e0\u00110 \"\f\b\u0000\u0010\u001e*\u0006\u0012\u0002\b\u00030\u00102\u0006\u00107\u001a\u0002H\u001e2\u0006\u00108\u001a\u00020\b2?\u0010=\u001a;\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u0002H\u001e\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020?0>\u0012\u0004\u0012\u00020?0/j\b\u0012\u0004\u0012\u0002H\u001e`]¢\u0006\u0002\b3H\u0002¢\u0006\u0002\u0010^J,\u0010_\u001a\b\u0012\u0004\u0012\u0002HN0\u0011\"\f\b\u0000\u0010N*\u0006\u0012\u0002\b\u00030\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HN0\u00110 H\u0002J2\u0010`\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HN0\u00110\u0007\"\f\b\u0000\u0010N*\u0006\u0012\u0002\b\u00030\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HN0\u00110\u0007H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R5\u0010\u000e\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\u0011\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "scopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "forClassUseSiteScope", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Z)V", "intersectionOverrides", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial;", "getIntersectionOverrides", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "isReceiverClassExpect", "overrideService", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideService;", "getScopes", "()Ljava/util/List;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "chooseIntersectionOverrideModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "D", "extractedOverridden", "", "chooseIntersectionVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "extractedOverrides", "collectClassifiers", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "collectFunctions", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "collectIntersectionResultsForCallables", "processCallables", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "collectMembersGroupedByScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/MembersByScope;", "collectRealOverridden", NativeSymbol.TYPE_NAME, "scope", "result", "", "visited", "", "processDirectOverridden", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Ljava/util/Collection;Ljava/util/Set;Lkotlin/jvm/functions/Function3;)V", "convertGroupedCallablesToIntersectionResults", "membersByScope", "createIntersectionOverride", "mostSpecific", "createIntersectionOverrideField", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "overrides", "newModality", "newVisibility", "createIntersectionOverrideFunction", "createIntersectionOverrideProperty", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "createIntersectionOverrideVariable", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "createIntersectionOverrideSymbol", "Lorg/jetbrains/kotlin/name/CallableId;", "createCopy", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lkotlin/ParameterName;", "returnType", "(Ljava/util/Collection;Ljava/util/Collection;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "intersectReturnTypes", "processClassifiersByNameWithSubstitution", "processor", "realOverridden", "Lorg/jetbrains/kotlin/fir/scopes/ProcessOverriddenWithBaseScope;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;Lkotlin/jvm/functions/Function3;)Ljava/util/Collection;", "maxByVisibility", "nonSubsumed", "ResultOfIntersection", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeIntersectionScopeContext {
    private final ConeSimpleKotlinType dispatchReceiverType;
    private final boolean forClassUseSiteScope;
    private final FirCache<FirCallableSymbol<?>, MemberWithBaseScope<FirCallableSymbol<?>>, ResultOfIntersection.NonTrivial<?>> intersectionOverrides;
    private final boolean isReceiverClassExpect;
    private final FirOverrideChecker overrideChecker;
    private final FirOverrideService overrideService;
    private final List<FirTypeScope> scopes;
    private final FirSession session;

    /* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Modality.values().length];
            try {
                iArr[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Modality.SEALED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Modality.OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Modality.ABSTRACT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirTypeIntersectionScopeContext(FirSession session, FirOverrideChecker overrideChecker, List<? extends FirTypeScope> scopes, ConeSimpleKotlinType dispatchReceiverType, boolean z) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(overrideChecker, "overrideChecker");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        Intrinsics.checkNotNullParameter(dispatchReceiverType, "dispatchReceiverType");
        this.session = session;
        this.overrideChecker = overrideChecker;
        this.scopes = scopes;
        this.dispatchReceiverType = dispatchReceiverType;
        this.forClassUseSiteScope = z;
        this.overrideService = FirOverrideServiceKt.getOverrideService(session);
        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(dispatchReceiverType, session);
        boolean z2 = false;
        if (regularClassSymbol != null && regularClassSymbol.getRawStatus().isExpect()) {
            z2 = true;
        }
        this.isReceiverClassExpect = z2;
        this.intersectionOverrides = (FirCache) FirTypeIntersectionScopeContextKt.getIntersectionOverrideStorage(session).getCacheByScope().getValue(dispatchReceiverType, null);
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final List<FirTypeScope> getScopes() {
        return this.scopes;
    }

    public final FirCache<FirCallableSymbol<?>, MemberWithBaseScope<FirCallableSymbol<?>>, ResultOfIntersection.NonTrivial<?>> getIntersectionOverrides() {
        return this.intersectionOverrides;
    }

    /* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\f\b\u0000\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003:\u0002\u0011\u0012B%\b\u0004\u0012\u0012\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0012\u0010\n\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0001\u0002\u0013\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "", "overriddenMembers", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "chosenSymbol", "getChosenSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getContainingScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "getOverriddenMembers", "()Ljava/util/List;", "NonTrivial", "SingleMember", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$SingleMember;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ResultOfIntersection<D extends FirCallableSymbol<?>> {
        private final FirTypeScope containingScope;
        private final List<MemberWithBaseScope<D>> overriddenMembers;

        public /* synthetic */ ResultOfIntersection(List list, FirTypeScope firTypeScope, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, firTypeScope);
        }

        public abstract D getChosenSymbol();

        /* JADX WARN: Multi-variable type inference failed */
        private ResultOfIntersection(List<? extends MemberWithBaseScope<? extends D>> list, FirTypeScope firTypeScope) {
            this.overriddenMembers = list;
            this.containingScope = firTypeScope;
        }

        public final List<MemberWithBaseScope<D>> getOverriddenMembers() {
            return this.overriddenMembers;
        }

        public final FirTypeScope getContainingScope() {
            return this.containingScope;
        }

        /* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000*\f\b\u0001\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001d\b\u0016\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007B+\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00060\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fR\u0016\u0010\u0004\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$SingleMember;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "chosenSymbol", "overriddenMember", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;)V", "overriddenMembers", "", "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "getChosenSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SingleMember<D extends FirCallableSymbol<?>> extends ResultOfIntersection<D> {
            private final D chosenSymbol;

            @Override // org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.ResultOfIntersection
            public D getChosenSymbol() {
                return this.chosenSymbol;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SingleMember(D chosenSymbol, List<? extends MemberWithBaseScope<? extends D>> overriddenMembers, FirTypeScope firTypeScope) {
                super(overriddenMembers, firTypeScope, null);
                Intrinsics.checkNotNullParameter(chosenSymbol, "chosenSymbol");
                Intrinsics.checkNotNullParameter(overriddenMembers, "overriddenMembers");
                this.chosenSymbol = chosenSymbol;
            }

            /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
            public SingleMember(D chosenSymbol, MemberWithBaseScope<? extends D> overriddenMember) {
                this(chosenSymbol, CollectionsKt.listOf(overriddenMember), overriddenMember.getBaseScope());
                Intrinsics.checkNotNullParameter(chosenSymbol, "chosenSymbol");
                Intrinsics.checkNotNullParameter(overriddenMember, "overriddenMember");
            }
        }

        /* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000*\f\b\u0001\u0010\u0001*\u0006\u0012\u0002\b\u00030\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B?\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b0\u0007\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fR\u001b\u0010\r\u001a\u00028\u00018VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00028\u00018F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000fR\u001d\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial;", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "context", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "mostSpecific", "", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "overriddenMembers", "containingScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "(Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)V", "chosenSymbol", "getChosenSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "chosenSymbol$delegate", "Lkotlin/Lazy;", "getContext", "()Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "keySymbol", "getKeySymbol", "getMostSpecific", "()Ljava/util/List;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NonTrivial<D extends FirCallableSymbol<?>> extends ResultOfIntersection<D> {

            /* JADX INFO: renamed from: chosenSymbol$delegate, reason: from kotlin metadata */
            private final Lazy chosenSymbol;
            private final FirTypeIntersectionScopeContext context;
            private final List<MemberWithBaseScope<D>> mostSpecific;

            public final FirTypeIntersectionScopeContext getContext() {
                return this.context;
            }

            public final List<MemberWithBaseScope<D>> getMostSpecific() {
                return this.mostSpecific;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public NonTrivial(FirTypeIntersectionScopeContext context, List<? extends MemberWithBaseScope<? extends D>> mostSpecific, List<? extends MemberWithBaseScope<? extends D>> overriddenMembers, FirTypeScope firTypeScope) {
                super(overriddenMembers, firTypeScope, null);
                Intrinsics.checkNotNullParameter(context, "context");
                Intrinsics.checkNotNullParameter(mostSpecific, "mostSpecific");
                Intrinsics.checkNotNullParameter(overriddenMembers, "overriddenMembers");
                this.context = context;
                this.mostSpecific = mostSpecific;
                this.chosenSymbol = LazyKt.lazy(new Function0<D>(this) { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial$chosenSymbol$2
                    final /* synthetic */ FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial<D> this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                        this.this$0 = this;
                    }

                    /* JADX WARN: Incorrect return type in method signature: ()TD; */
                    @Override // kotlin.jvm.functions.Function0
                    public final FirCallableSymbol invoke() {
                        FirCallableSymbol member = this.this$0.getContext().getIntersectionOverrides().getValue(this.this$0.getKeySymbol(), this.this$0).getMember();
                        Intrinsics.checkNotNull(member, "null cannot be cast to non-null type D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial");
                        return member;
                    }
                });
            }

            @Override // org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.ResultOfIntersection
            public D getChosenSymbol() {
                return (D) this.chosenSymbol.getValue();
            }

            public final D getKeySymbol() {
                return (D) ((MemberWithBaseScope) CollectionsKt.first((List) this.mostSpecific)).getMember();
            }
        }
    }

    public final void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        for (Pair<FirClassifierSymbol<?>, ConeSubstitutor> pair : collectClassifiers(name2)) {
            processor.invoke(pair.component1(), pair.component2());
        }
    }

    private final List<Pair<FirClassifierSymbol<?>, ConeSubstitutor>> collectClassifiers(Name name2) {
        final HashSet hashSet = new HashSet();
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        Iterator<FirTypeScope> it = this.scopes.iterator();
        while (it.hasNext()) {
            it.next().processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.collectClassifiers.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor substitution) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    Intrinsics.checkNotNullParameter(substitution, "substitution");
                    if (hashSet.contains(symbol)) {
                        return;
                    }
                    arrayList.add(symbol);
                    arrayList2.add(TuplesKt.to(symbol, substitution));
                }
            });
            CollectionsKt.addAll(hashSet, arrayList);
            arrayList.clear();
        }
        return arrayList2;
    }

    public final <D extends FirCallableSymbol<?>> List<Pair<FirTypeScope, List<D>>> collectMembersGroupedByScope(Name name2, Function3<? super FirScope, ? super Name, ? super Function1<? super D, Unit>, Unit> processCallables) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processCallables, "processCallables");
        List<FirTypeScope> scopes = getScopes();
        ArrayList arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            ArrayList arrayList2 = new ArrayList();
            processCallables.invoke(firTypeScope, name2, new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList2));
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <D extends FirCallableSymbol<?>> List<ResultOfIntersection<D>> convertGroupedCallablesToIntersectionResults(List<? extends Pair<? extends FirTypeScope, ? extends List<? extends D>>> membersByScope) {
        Intrinsics.checkNotNullParameter(membersByScope, "membersByScope");
        if (membersByScope.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        Pair pair = (Pair) CollectionsKt.singleOrNull((List) membersByScope);
        if (pair != null) {
            FirTypeScope firTypeScope = (FirTypeScope) pair.component1();
            List<FirCallableSymbol> list = (List) pair.component2();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (FirCallableSymbol firCallableSymbol : list) {
                arrayList.add(new ResultOfIntersection.SingleMember(firCallableSymbol, new MemberWithBaseScope(firCallableSymbol, firTypeScope)));
            }
            return arrayList;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<T> it = membersByScope.iterator();
        while (it.hasNext()) {
            Pair pair2 = (Pair) it.next();
            FirTypeScope firTypeScope2 = (FirTypeScope) pair2.component1();
            List list2 = (List) pair2.component2();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                arrayList2.add(new MemberWithBaseScope((FirCallableSymbol) it2.next(), firTypeScope2));
            }
            CollectionsKt.addAll(linkedHashSet, arrayList2);
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        ArrayList arrayList3 = new ArrayList();
        while (linkedHashSet2.size() > 1) {
            LinkedHashSet linkedHashSet3 = linkedHashSet2;
            List<? extends MemberWithBaseScope<? extends D>> listExtractBothWaysOverridable = this.overrideService.extractBothWaysOverridable(maxByVisibility(linkedHashSet3), linkedHashSet3, this.overrideChecker);
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : listExtractBothWaysOverridable) {
                if (!Visibilities.INSTANCE.isPrivate(((FirMemberDeclaration) ((MemberWithBaseScope) obj).getMember().getFir()).getStatus().getVisibility())) {
                    arrayList4.add(obj);
                }
            }
            ArrayList arrayList5 = arrayList4;
            if (!arrayList5.isEmpty()) {
                listExtractBothWaysOverridable = arrayList5;
            }
            List<? extends MemberWithBaseScope<? extends D>> list3 = listExtractBothWaysOverridable;
            List<? extends MemberWithBaseScope<? extends D>> listNonSubsumed = this.forClassUseSiteScope ? nonSubsumed(list3) : list3;
            List<MemberWithBaseScope<D>> listSelectMostSpecificMembers = this.overrideService.selectMostSpecificMembers(listNonSubsumed, ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault());
            if (this.forClassUseSiteScope) {
                if (listNonSubsumed.size() > 1) {
                    LinkedHashSet linkedHashSet4 = new LinkedHashSet();
                    Iterator<T> it3 = listNonSubsumed.iterator();
                    while (it3.hasNext()) {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) ((MemberWithBaseScope) it3.next()).getMember().getFir();
                        while (true) {
                            FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                            if (originalForSubstitutionOverrideAttr == null) {
                                break;
                            }
                            firCallableDeclaration = originalForSubstitutionOverrideAttr;
                        }
                        linkedHashSet4.add(firCallableDeclaration.getSymbol());
                    }
                    if (linkedHashSet4.size() > 1) {
                        arrayList3.add(new ResultOfIntersection.NonTrivial(this, listSelectMostSpecificMembers, listNonSubsumed, null));
                    }
                }
                MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) CollectionsKt.first((List) listSelectMostSpecificMembers);
                arrayList3.add(new ResultOfIntersection.SingleMember(memberWithBaseScope.component1(), list3, memberWithBaseScope.getBaseScope()));
            } else if (listSelectMostSpecificMembers.size() > 1) {
                arrayList3.add(new ResultOfIntersection.NonTrivial(this, listSelectMostSpecificMembers, listNonSubsumed, null));
            } else {
                MemberWithBaseScope memberWithBaseScope2 = (MemberWithBaseScope) CollectionsKt.first((List) listSelectMostSpecificMembers);
                arrayList3.add(new ResultOfIntersection.SingleMember(memberWithBaseScope2.component1(), list3, memberWithBaseScope2.getBaseScope()));
            }
        }
        if (!linkedHashSet2.isEmpty()) {
            LinkedHashSet linkedHashSet5 = linkedHashSet2;
            MemberWithBaseScope memberWithBaseScope3 = (MemberWithBaseScope) CollectionsKt.single(linkedHashSet5);
            arrayList3.add(new ResultOfIntersection.SingleMember(memberWithBaseScope3.component1(), CollectionsKt.toList(linkedHashSet5), memberWithBaseScope3.getBaseScope()));
        }
        return arrayList3;
    }

    public final <D extends FirCallableSymbol<?>> MemberWithBaseScope<FirCallableSymbol<?>> createIntersectionOverride(List<? extends MemberWithBaseScope<? extends D>> mostSpecific, List<? extends MemberWithBaseScope<? extends D>> extractedOverrides) {
        FirFieldSymbol firFieldSymbolCreateIntersectionOverrideField;
        Intrinsics.checkNotNullParameter(mostSpecific, "mostSpecific");
        Intrinsics.checkNotNullParameter(extractedOverrides, "extractedOverrides");
        List<? extends MemberWithBaseScope<? extends D>> list = extractedOverrides;
        Modality modalityChooseIntersectionOverrideModality = chooseIntersectionOverrideModality(list);
        Visibility visibilityChooseIntersectionVisibility = chooseIntersectionVisibility(list);
        List<? extends MemberWithBaseScope<? extends D>> list2 = mostSpecific;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((MemberWithBaseScope) it.next()).getMember());
        }
        ArrayList arrayList2 = arrayList;
        List<? extends MemberWithBaseScope<? extends D>> list3 = extractedOverrides;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        Iterator<T> it2 = list3.iterator();
        while (it2.hasNext()) {
            arrayList3.add(((MemberWithBaseScope) it2.next()).getMember());
        }
        ArrayList arrayList4 = arrayList3;
        MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) CollectionsKt.first((List) mostSpecific);
        FirCallableSymbol member = memberWithBaseScope.getMember();
        if (member instanceof FirNamedFunctionSymbol) {
            firFieldSymbolCreateIntersectionOverrideField = createIntersectionOverrideFunction(arrayList2, arrayList4, modalityChooseIntersectionOverrideModality, visibilityChooseIntersectionVisibility);
        } else if (member instanceof FirPropertySymbol) {
            firFieldSymbolCreateIntersectionOverrideField = createIntersectionOverrideProperty(arrayList2, arrayList4, modalityChooseIntersectionOverrideModality, visibilityChooseIntersectionVisibility);
        } else if (member instanceof FirFieldSymbol) {
            if (this.forClassUseSiteScope) {
                throw new IllegalStateException(("Can not create intersection override in class scope for field " + memberWithBaseScope.getMember()).toString());
            }
            firFieldSymbolCreateIntersectionOverrideField = createIntersectionOverrideField(arrayList2, arrayList4, modalityChooseIntersectionOverrideModality, visibilityChooseIntersectionVisibility);
        } else {
            throw new IllegalStateException(("Unsupported symbol type for creating intersection overrides: " + memberWithBaseScope.getMember()).toString());
        }
        return FirTypeIntersectionScopeContextKt.withScope(firFieldSymbolCreateIntersectionOverrideField, memberWithBaseScope.getBaseScope());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <S extends FirCallableSymbol<?>> List<MemberWithBaseScope<S>> nonSubsumed(List<? extends MemberWithBaseScope<? extends S>> list) {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (MemberWithBaseScope<? extends S> memberWithBaseScope : list) {
            FirCallableSymbol firCallableSymbolComponent1 = memberWithBaseScope.component1();
            FirTypeScope baseScope = memberWithBaseScope.getBaseScope();
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbolComponent1.getFir();
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                if (originalForSubstitutionOverrideAttr == null) {
                    break;
                }
                firCallableDeclaration = originalForSubstitutionOverrideAttr;
            }
            final FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
            if (symbol != null) {
                Function1<FirCallableSymbol<?>, ProcessorAction> function1 = new Function1<FirCallableSymbol<?>, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext$nonSubsumed$addIfDifferent$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final ProcessorAction invoke(FirCallableSymbol<?> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) it.getFir();
                        while (true) {
                            FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
                            if (originalForSubstitutionOverrideAttr2 == null) {
                                break;
                            }
                            firCallableDeclaration2 = originalForSubstitutionOverrideAttr2;
                        }
                        FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration2.getSymbol();
                        if (symbol2 != null) {
                            if (!Intrinsics.areEqual(symbol2, symbol)) {
                                linkedHashSet.add(symbol2);
                            }
                            return ProcessorAction.NEXT;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
                    }
                };
                if (firCallableSymbolComponent1 instanceof FirNamedFunctionSymbol) {
                    FirTypeScopeKt.processOverriddenFunctions(baseScope, (FirNamedFunctionSymbol) firCallableSymbolComponent1, function1);
                } else if (firCallableSymbolComponent1 instanceof FirPropertySymbol) {
                    FirTypeScopeKt.processOverriddenProperties(baseScope, (FirPropertySymbol) firCallableSymbolComponent1, function1);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) ((MemberWithBaseScope) obj).getMember().getFir();
            while (true) {
                FirCallableDeclaration originalForSubstitutionOverrideAttr2 = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration2) || (firCallableDeclaration2.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration2) : null;
                if (originalForSubstitutionOverrideAttr2 == null) {
                    break;
                }
                firCallableDeclaration2 = originalForSubstitutionOverrideAttr2;
            }
            FirCallableSymbol<? extends FirCallableDeclaration> symbol2 = firCallableDeclaration2.getSymbol();
            if (symbol2 != null) {
                if (!linkedHashSet.contains(symbol2)) {
                    arrayList.add(obj);
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol<*>");
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <S extends FirCallableSymbol<?>> MemberWithBaseScope<S> maxByVisibility(Collection<? extends MemberWithBaseScope<? extends S>> collection) {
        Integer numCompare;
        MemberWithBaseScope<S> memberWithBaseScope = null;
        for (MemberWithBaseScope<? extends S> memberWithBaseScope2 : collection) {
            if (memberWithBaseScope == null || ((numCompare = Visibilities.INSTANCE.compare(((FirCallableDeclaration) memberWithBaseScope.getMember().getFir()).getStatus().getVisibility(), ((FirCallableDeclaration) memberWithBaseScope2.getMember().getFir()).getStatus().getVisibility())) != null && numCompare.intValue() < 0)) {
                memberWithBaseScope = memberWithBaseScope2;
            }
        }
        Intrinsics.checkNotNull(memberWithBaseScope);
        return memberWithBaseScope;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends FirCallableSymbol<?>> Modality chooseIntersectionOverrideModality(Collection<? extends MemberWithBaseScope<? extends D>> extractedOverridden) {
        Function3 function3;
        Iterator<? extends MemberWithBaseScope<? extends D>> it = extractedOverridden.iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            D fir = it.next().component1().getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
            Modality modality = ((FirMemberDeclaration) fir).getStatus().getModality();
            int i = modality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
            if (i == 1) {
                return Modality.FINAL;
            }
            if (i == 2) {
                return null;
            }
            if (i == 3) {
                z2 = true;
            } else if (i == 4) {
                z = true;
            }
        }
        if (z && !z2) {
            return Modality.ABSTRACT;
        }
        if (!z && z2) {
            return Modality.OPEN;
        }
        Collection<? extends MemberWithBaseScope<? extends D>> collection = extractedOverridden;
        FirCallableSymbol member = ((MemberWithBaseScope) CollectionsKt.first(collection)).getMember();
        if (member instanceof FirNamedFunctionSymbol) {
            FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$1 firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$1 = FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$1.INSTANCE;
            Intrinsics.checkNotNull(firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$1, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.Function3<org.jetbrains.kotlin.fir.scopes.FirTypeScope, D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality, kotlin.Function2<D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality, org.jetbrains.kotlin.fir.scopes.FirTypeScope, org.jetbrains.kotlin.fir.scopes.ProcessorAction>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>{ org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.ProcessOverriddenWithBaseScope<D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality> }");
            function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$1, 3);
        } else {
            if (!(member instanceof FirPropertySymbol)) {
                throw new IllegalStateException(("Unexpected callable kind: " + ((MemberWithBaseScope) CollectionsKt.first(collection)).getMember()).toString());
            }
            FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2 firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2 = FirTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2.INSTANCE;
            Intrinsics.checkNotNull(firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2, "null cannot be cast to non-null type @[ExtensionFunctionType] kotlin.Function3<org.jetbrains.kotlin.fir.scopes.FirTypeScope, D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality, kotlin.Function2<D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality, org.jetbrains.kotlin.fir.scopes.FirTypeScope, org.jetbrains.kotlin.fir.scopes.ProcessorAction>, org.jetbrains.kotlin.fir.scopes.ProcessorAction>{ org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt.ProcessOverriddenWithBaseScope<D of org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.chooseIntersectionOverrideModality> }");
            function3 = (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(firTypeIntersectionScopeContext$chooseIntersectionOverrideModality$processDirectOverridden$2, 3);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<T> it2 = collection.iterator();
        while (it2.hasNext()) {
            MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) it2.next();
            CollectionsKt.addAll(arrayList, realOverridden(memberWithBaseScope.getMember(), memberWithBaseScope.getBaseScope(), function3));
        }
        Iterator it3 = FirOverrideUtilsKt.filterOutOverridden(arrayList, function3).iterator();
        if (!it3.hasNext()) {
            throw new NoSuchElementException();
        }
        D fir2 = ((MemberWithBaseScope) it3.next()).getMember().getFir();
        Intrinsics.checkNotNull(fir2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
        Modality modality2 = ((FirMemberDeclaration) fir2).getStatus().getModality();
        if (modality2 == null) {
            modality2 = Modality.ABSTRACT;
        }
        Modality modality3 = modality2;
        while (it3.hasNext()) {
            D fir3 = ((MemberWithBaseScope) it3.next()).getMember().getFir();
            Intrinsics.checkNotNull(fir3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
            Modality modality4 = ((FirMemberDeclaration) fir3).getStatus().getModality();
            if (modality4 == null) {
                modality4 = Modality.ABSTRACT;
            }
            Modality modality5 = modality4;
            if (modality3.compareTo(modality5) > 0) {
                modality3 = modality5;
            }
        }
        return modality3;
    }

    private final <D extends FirCallableSymbol<?>> Collection<MemberWithBaseScope<D>> realOverridden(D symbol, FirTypeScope scope, Function3<? super FirTypeScope, ? super D, ? super Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverridden) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectRealOverridden(symbol, scope, linkedHashSet, new LinkedHashSet(), processDirectOverridden);
        return linkedHashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <D extends FirCallableSymbol<?>> void collectRealOverridden(D symbol, FirTypeScope scope, final Collection<MemberWithBaseScope<D>> result, final Set<Pair<FirTypeScope, D>> visited, final Function3<? super FirTypeScope, ? super D, ? super Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverridden) {
        if (visited.add(TuplesKt.to(scope, symbol))) {
            if (!((FirCallableDeclaration) symbol.getFir()).getOrigin().getFromSupertypes()) {
                result.add(new MemberWithBaseScope<>(symbol, scope));
            } else {
                processDirectOverridden.invoke(scope, symbol, new Function2<D, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext.collectRealOverridden.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    /* JADX WARN: Incorrect types in method signature: (TD;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
                    @Override // kotlin.jvm.functions.Function2
                    public final ProcessorAction invoke(FirCallableSymbol overridden, FirTypeScope baseScope) {
                        Intrinsics.checkNotNullParameter(overridden, "overridden");
                        Intrinsics.checkNotNullParameter(baseScope, "baseScope");
                        FirTypeIntersectionScopeContext.this.collectRealOverridden(overridden, baseScope, result, visited, processDirectOverridden);
                        return ProcessorAction.NEXT;
                    }
                });
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <D extends FirCallableSymbol<?>> Visibility chooseIntersectionVisibility(Collection<? extends MemberWithBaseScope<? extends D>> extractedOverrides) {
        Visibilities.Private r0 = Visibilities.Private.INSTANCE;
        Iterator<? extends MemberWithBaseScope<? extends D>> it = extractedOverrides.iterator();
        while (it.hasNext()) {
            D fir = it.next().component1().getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
            Visibility visibility = ((FirMemberDeclaration) fir).getStatus().getVisibility();
            Integer numCompare = Visibilities.INSTANCE.compare(visibility, r0);
            if (numCompare == null) {
                return Visibilities.INSTANCE.getDEFAULT_VISIBILITY();
            }
            if (numCompare.intValue() > 0) {
                r0 = visibility;
            }
        }
        return r0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirNamedFunctionSymbol createIntersectionOverrideFunction(Collection<? extends FirCallableSymbol<?>> mostSpecific, Collection<? extends FirCallableSymbol<?>> overrides, Modality newModality, Visibility newVisibility) {
        Object objFirst = CollectionsKt.first(mostSpecific);
        Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol");
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) ((FirNamedFunctionSymbol) objFirst).getFir();
        ClassId classId = ConeTypeUtilsKt.getClassId(this.dispatchReceiverType);
        if (classId == null) {
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firSimpleFunction);
            classId = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null;
            Intrinsics.checkNotNull(classId);
        }
        FirIntersectionOverrideFunctionSymbol firIntersectionOverrideFunctionSymbol = new FirIntersectionOverrideFunctionSymbol(new CallableId(classId, firSimpleFunction.getName()), overrides);
        FirIntersectionOverrideFunctionSymbol firIntersectionOverrideFunctionSymbol2 = firIntersectionOverrideFunctionSymbol;
        ClassMembersKt.setOriginalForIntersectionOverrideAttr(FirFakeOverrideGenerator.createCopyForFirFunction$default(FirFakeOverrideGenerator.INSTANCE, firIntersectionOverrideFunctionSymbol2, firSimpleFunction, null, this.session, FirDeclarationOrigin.IntersectionOverride.INSTANCE, this.isReceiverClassExpect || firSimpleFunction.getStatus().isExpect(), this.dispatchReceiverType, null, null, null, null, this.forClassUseSiteScope ? null : intersectReturnTypes(mostSpecific), newModality, newVisibility, null, 18304, null), firSimpleFunction);
        return firIntersectionOverrideFunctionSymbol2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeKotlinType intersectReturnTypes(Collection<? extends FirCallableSymbol<?>> overrides) {
        Collection<? extends FirCallableSymbol<?>> collection = overrides;
        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) CollectionsKt.first(collection);
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collection.iterator();
        while (true) {
            ConeKotlinType coneKotlinTypeSubstituteOrSelf = null;
            if (!it.hasNext()) {
                break;
            }
            FirCallableSymbol firCallableSymbol2 = (FirCallableSymbol) it.next();
            FirTypeRef returnTypeRef = ((FirCallableDeclaration) firCallableSymbol2.getFir()).getReturnTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            if (!(type instanceof ConeKotlinType)) {
                type = null;
            }
            if (Intrinsics.areEqual(firCallableSymbol2, firCallableSymbol)) {
                coneKotlinTypeSubstituteOrSelf = type;
            } else {
                ConeSubstitutor coneSubstitutorBuildSubstitutorForOverridesCheck = FirAbstractOverrideCheckerKt.buildSubstitutorForOverridesCheck((FirCallableDeclaration) firCallableSymbol2.getFir(), (FirCallableDeclaration) firCallableSymbol.getFir(), this.session);
                if (coneSubstitutorBuildSubstitutorForOverridesCheck != null && type != null) {
                    coneKotlinTypeSubstituteOrSelf = coneSubstitutorBuildSubstitutorForOverridesCheck.substituteOrSelf(type);
                }
            }
            if (coneKotlinTypeSubstituteOrSelf != null) {
                arrayList.add(coneKotlinTypeSubstituteOrSelf);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty()) {
            return null;
        }
        return (ConeKotlinType) TypeComponentsKt.getTypeContext(this.session).intersectTypes((List<? extends KotlinTypeMarker>) arrayList2);
    }

    public final List<ResultOfIntersection<FirNamedFunctionSymbol>> collectFunctions(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        List<FirTypeScope> scopes = getScopes();
        ArrayList arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            ArrayList arrayList2 = new ArrayList();
            firTypeScope.processFunctionsByName(name2, new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList2));
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return convertGroupedCallablesToIntersectionResults(arrayList);
    }

    public final <D extends FirCallableSymbol<?>> List<ResultOfIntersection<D>> collectIntersectionResultsForCallables(Name name2, Function3<? super FirScope, ? super Name, ? super Function1<? super D, Unit>, Unit> processCallables) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processCallables, "processCallables");
        List<FirTypeScope> scopes = getScopes();
        ArrayList arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            ArrayList arrayList2 = new ArrayList();
            processCallables.invoke(firTypeScope, name2, new FirTypeIntersectionScopeContext$collectMembersGroupedByScope$1$1(arrayList2));
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        return convertGroupedCallablesToIntersectionResults(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirPropertySymbol createIntersectionOverrideProperty(Collection<? extends FirCallableSymbol<?>> mostSpecific, Collection<? extends FirCallableSymbol<?>> overrides, Modality newModality, Visibility newVisibility) {
        boolean z;
        Collection<? extends FirCallableSymbol<?>> collection = mostSpecific;
        Object objFirst = CollectionsKt.first(collection);
        if (objFirst == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol");
        }
        FirVariable firVariable = (FirVariable) ((FirPropertySymbol) objFirst).getFir();
        ClassId classId = ConeTypeUtilsKt.getClassId(this.dispatchReceiverType);
        if (classId == null) {
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firVariable);
            classId = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null;
            Intrinsics.checkNotNull(classId);
        }
        FirIntersectionOverridePropertySymbol firIntersectionOverridePropertySymbol = new FirIntersectionOverridePropertySymbol(new CallableId(classId, firVariable.getName()), overrides);
        if (this.forClassUseSiteScope) {
            z = false;
        } else {
            if (!(collection instanceof Collection) || !collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
                        Intrinsics.checkNotNull(firCallableSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol<*>");
                        if (((FirVariable) ((FirVariableSymbol) firCallableSymbol).getFir()).getIsVar()) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        }
        FirProperty firProperty = (FirProperty) firVariable;
        FirIntersectionOverridePropertySymbol firIntersectionOverridePropertySymbol2 = firIntersectionOverridePropertySymbol;
        ClassMembersKt.setOriginalForIntersectionOverrideAttr(FirFakeOverrideGenerator.createCopyForFirProperty$default(FirFakeOverrideGenerator.INSTANCE, firIntersectionOverridePropertySymbol2, firProperty, null, this.session, FirDeclarationOrigin.IntersectionOverride.INSTANCE, this.isReceiverClassExpect || firProperty.getStatus().isExpect(), this.dispatchReceiverType, null, null, null, z ? intersectReturnTypes(mostSpecific) : null, newModality, newVisibility, null, 9088, null), firVariable);
        return firIntersectionOverridePropertySymbol2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirFieldSymbol createIntersectionOverrideField(Collection<? extends FirCallableSymbol<?>> mostSpecific, Collection<? extends FirCallableSymbol<?>> overrides, Modality newModality, Visibility newVisibility) {
        boolean z;
        Collection<? extends FirCallableSymbol<?>> collection = mostSpecific;
        Object objFirst = CollectionsKt.first(collection);
        if (objFirst == null) {
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol");
        }
        FirVariable firVariable = (FirVariable) ((FirFieldSymbol) objFirst).getFir();
        ClassId classId = ConeTypeUtilsKt.getClassId(this.dispatchReceiverType);
        if (classId == null) {
            ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firVariable);
            classId = coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null;
            Intrinsics.checkNotNull(classId);
        }
        FirIntersectionOverrideFieldSymbol firIntersectionOverrideFieldSymbol = new FirIntersectionOverrideFieldSymbol(new CallableId(classId, firVariable.getName()), overrides);
        if (this.forClassUseSiteScope) {
            z = false;
        } else {
            if (!(collection instanceof Collection) || !collection.isEmpty()) {
                Iterator<T> it = collection.iterator();
                while (true) {
                    if (it.hasNext()) {
                        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) it.next();
                        Intrinsics.checkNotNull(firCallableSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol<*>");
                        if (((FirVariable) ((FirVariableSymbol) firCallableSymbol).getFir()).getIsVar()) {
                            z = false;
                        }
                    }
                }
            }
            z = true;
        }
        FirField firField = (FirField) firVariable;
        FirIntersectionOverrideFieldSymbol firIntersectionOverrideFieldSymbol2 = firIntersectionOverrideFieldSymbol;
        ClassMembersKt.setOriginalForIntersectionOverrideAttr(FirFakeOverrideGenerator.INSTANCE.createCopyForFirField(firIntersectionOverrideFieldSymbol2, firField, null, this.session, FirDeclarationOrigin.IntersectionOverride.INSTANCE, (4480 & 32) != 0 ? firField.getStatus().isExpect() : this.isReceiverClassExpect || firField.getStatus().isExpect(), this.dispatchReceiverType, (4480 & 128) != 0 ? null : null, (4480 & 256) != 0 ? null : null, (4480 & 512) != 0 ? null : z ? intersectReturnTypes(mostSpecific) : null, (4480 & 1024) != 0 ? null : newModality, (4480 & 2048) != 0 ? null : newVisibility, (4480 & 4096) != 0 ? null : null), firVariable);
        return firIntersectionOverrideFieldSymbol2;
    }
}
