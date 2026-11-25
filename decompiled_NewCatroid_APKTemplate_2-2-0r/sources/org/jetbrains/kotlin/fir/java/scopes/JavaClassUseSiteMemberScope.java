package org.jetbrains.kotlin.fir.java.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertySetter;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyBuilderKt;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.java.FirSyntheticPropertiesStorageKt;
import org.jetbrains.kotlin.fir.java.JavaTypeConversionKt;
import org.jetbrains.kotlin.fir.java.JavaTypeParameterStack;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaClass;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethod;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethodBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaMethodKt;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameterBuilder;
import org.jetbrains.kotlin.fir.java.declarations.FirJavaValueParameterKt;
import org.jetbrains.kotlin.fir.java.symbols.FirJavaOverriddenSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirOverrideCheckerKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.scopes.impl.AbstractFirOverrideScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext;
import org.jetbrains.kotlin.fir.scopes.jvm.SignatureUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRef;
import org.jetbrains.kotlin.load.java.BuiltinSpecialProperties;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.PropertiesConventionUtilKt;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;

/* JADX INFO: compiled from: JavaClassUseSiteMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u001f\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001a\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\u0006\u0010)\u001a\u00020\u0018H\u0014J\u001a\u0010*\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030+0'2\u0006\u0010)\u001a\u00020\u0018H\u0014J*\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020(2\b\u0010.\u001a\u0004\u0018\u00010(2\u0006\u0010\"\u001a\u00020#2\u0006\u0010/\u001a\u00020\rH\u0002JZ\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00182\u0006\u00103\u001a\u00020(2\b\u00104\u001a\u0004\u0018\u00010(2\u0006\u00105\u001a\u00020\u00182\f\u00106\u001a\b\u0012\u0004\u0012\u00020(0\u001b2\f\u00107\u001a\b\u0012\u0004\u0012\u00020(082\u0012\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u001b0:H\u0002JX\u0010;\u001a\u00020\r2\u0012\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0=0\u00072\u0006\u00105\u001a\u00020\u00182\f\u0010>\u001a\b\u0012\u0004\u0012\u00020(0'2\f\u00107\u001a\b\u0012\u0004\u0012\u00020(082\f\u00106\u001a\b\u0012\u0004\u0012\u00020(0\u001b2\b\u00104\u001a\u0004\u0018\u00010(H\u0002JV\u0010?\u001a\u0002012\u0006\u0010@\u001a\u00020\u00182\f\u0010>\u001a\b\u0012\u0004\u0012\u00020(0'2(\u0010A\u001a$\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00070\u001a0\u0007j\b\u0012\u0004\u0012\u00020(`B2\f\u00107\u001a\b\u0012\u0004\u0012\u00020(08H\u0002J\u001d\u0010C\u001a\u0004\u0018\u00010\u00192\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0000¢\u0006\u0002\bEJ\b\u0010F\u001a\u00020GH\u0016J\"\u0010H\u001a\u00020G*\u00020I2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010G2\b\b\u0002\u0010K\u001a\u00020\rH\u0002J\"\u0010L\u001a\u00020G*\u00020#2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010G2\b\b\u0002\u0010K\u001a\u00020\rH\u0002J\"\u0010M\u001a\u00020G*\u00020#2\n\b\u0002\u0010J\u001a\u0004\u0018\u00010G2\b\b\u0002\u0010K\u001a\u00020\rH\u0002J\u001e\u0010N\u001a\u0004\u0018\u00010\u0019*\u00020\u001c2\u0006\u0010O\u001a\u00020P2\u0006\u0010/\u001a\u00020\rH\u0002J\u000e\u0010Q\u001a\u0004\u0018\u00010%*\u00020(H\u0002J\f\u0010R\u001a\u00020\r*\u00020(H\u0002J\f\u0010S\u001a\u00020\r*\u00020(H\u0002J\u001e\u0010T\u001a\u0004\u0018\u00010(*\u00020\u001c2\u0006\u0010U\u001a\u00020G2\u0006\u0010O\u001a\u00020PH\u0002J\u0016\u0010V\u001a\u0004\u0018\u00010(*\u00020\u001c2\u0006\u0010O\u001a\u00020PH\u0002J\u0016\u0010W\u001a\u0004\u0018\u00010(*\u00020\u001c2\u0006\u0010O\u001a\u00020PH\u0002J\u000e\u0010X\u001a\u0004\u0018\u00010\u0018*\u00020\u001cH\u0002J\f\u0010Y\u001a\u00020\r*\u00020(H\u0002J$\u0010Z\u001a\u00020\r*\u00020[2\u0006\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\\\u001a\b\u0012\u0004\u0012\u00020[0]H\u0002J\u0014\u0010^\u001a\u00020\r*\u00020(2\u0006\u0010_\u001a\u00020(H\u0002J\f\u0010`\u001a\u00020\r*\u00020%H\u0002J\u0014\u0010a\u001a\u00020\r*\u00020\u001c2\u0006\u0010b\u001a\u00020(H\u0002J\f\u0010c\u001a\u00020\r*\u00020(H\u0014J\f\u0010d\u001a\u00020e*\u00020fH\u0002J\f\u0010g\u001a\u00020\r*\u00020(H\u0002J\u0016\u0010h\u001a\u0004\u0018\u00010[*\u00020i2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0012\u001a\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R8\u0010\u0016\u001a,\u0012\u0004\u0012\u00020\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u0019\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\u001b0\u001a0\u0017j\u0002`\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006j"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirUseSiteMemberScope;", "klass", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "superTypeScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "(Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaClass;Lorg/jetbrains/kotlin/fir/FirSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;)V", "canUseSpecialGetters", "", "getCanUseSpecialGetters", "()Z", "canUseSpecialGetters$delegate", "Lkotlin/Lazy;", "javaOverrideChecker", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverrideChecker;", "getJavaOverrideChecker", "()Lorg/jetbrains/kotlin/fir/java/scopes/JavaOverrideChecker;", "syntheticPropertyCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/java/SyntheticPropertiesCache;", "typeParameterStack", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "chooseModalityForAccessor", "Lorg/jetbrains/kotlin/descriptors/Modality;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getter", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "collectFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "name", "collectProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "generateSyntheticPropertySymbol", "getterSymbol", "setterSymbol", "takeModalityFromGetter", "processOverridesForFunctionsWithDifferentJvmName", "", "jvmName", "someSymbolWithNaturalNameFromSuperType", "explicitlyDeclaredFunctionWithNaturalName", "naturalName", "resultOfIntersectionWithNaturalName", FirebaseAnalytics.Param.DESTINATION, "", "resultsOfIntersectionToSaveInCache", "", "processOverridesForFunctionsWithErasedValueParameter", "overriddenMembers", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "explicitlyDeclaredFunctionsWithNaturalName", "processSpecialFunctions", "requestedName", "functionsFromSupertypesWithRequestedName", "Lorg/jetbrains/kotlin/fir/scopes/impl/MembersByScope;", "syntheticPropertyFromOverride", "overriddenProperty", "syntheticPropertyFromOverride$java", "toString", "", "computeJvmDescriptor", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "customName", "includeReturnType", "computeJvmDescriptorForGetter", "computeJvmDescriptorForSetter", "createOverridePropertyIfExists", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "createSuspendView", "doesOverrideRenamedBuiltins", "doesOverrideSuspendFunction", "findGetterByName", "getterName", "findGetterOverride", "findSetterOverride", "getBuiltinSpecialPropertyGetterName", "hasErasedParameters", "hasKotlinSuper", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visited", "", "hasSameJvmDescriptor", "builtinWithErasedParameters", "isAcceptableAsAccessorOverride", "isOverriddenInClassBy", "functionSymbol", "isVisibleInCurrentClass", "probablyJavaTypeRefToConeType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters", "toFir", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassUseSiteMemberScope extends AbstractFirUseSiteMemberScope {

    /* JADX INFO: renamed from: canUseSpecialGetters$delegate, reason: from kotlin metadata */
    private final Lazy canUseSpecialGetters;
    private final FirCache<Name, FirSyntheticPropertySymbol, Pair<JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> syntheticPropertyCache;
    private final JavaTypeParameterStack typeParameterStack;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaClassUseSiteMemberScope(final FirJavaClass klass, final FirSession session, List<? extends FirTypeScope> superTypeScopes, FirContainingNamesAwareScope declaredMemberScope) {
        super(FirDeclarationUtilKt.getClassId(klass), session, new JavaOverrideChecker(session, klass.getJavaTypeParameterStack(), superTypeScopes, true), superTypeScopes, ScopeUtilsKt.defaultType(klass), declaredMemberScope);
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(superTypeScopes, "superTypeScopes");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        this.typeParameterStack = klass.getJavaTypeParameterStack();
        this.canUseSpecialGetters = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope$canUseSpecialGetters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                return Boolean.valueOf(!JavaClassUseSiteMemberScope.hasKotlinSuper$default(this.this$0, klass, session, null, 2, null));
            }
        });
        this.syntheticPropertyCache = (FirCache) FirSyntheticPropertiesStorageKt.getSyntheticPropertiesStorage(session).getCacheByOwner().getValue(klass, null);
    }

    private final boolean getCanUseSpecialGetters() {
        return ((Boolean) this.canUseSpecialGetters.getValue()).booleanValue();
    }

    private final JavaOverrideChecker getJavaOverrideChecker() {
        FirOverrideChecker overrideChecker = getOverrideChecker();
        Intrinsics.checkNotNull(overrideChecker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.java.scopes.JavaOverrideChecker");
        return (JavaOverrideChecker) overrideChecker;
    }

    private final FirSyntheticPropertySymbol generateSyntheticPropertySymbol(final FirNamedFunctionSymbol getterSymbol, final FirNamedFunctionSymbol setterSymbol, final FirProperty property, final boolean takeModalityFromGetter) {
        return FirSyntheticPropertyBuilderKt.buildSyntheticProperty(new Function1<FirSyntheticPropertyBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope.generateSyntheticPropertySymbol.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirSyntheticPropertyBuilder firSyntheticPropertyBuilder) {
                invoke2(firSyntheticPropertyBuilder);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirSyntheticPropertyBuilder buildSyntheticProperty) {
                Modality modalityChooseModalityForAccessor;
                Intrinsics.checkNotNullParameter(buildSyntheticProperty, "$this$buildSyntheticProperty");
                buildSyntheticProperty.setModuleData(FirModuleDataKt.getModuleData(JavaClassUseSiteMemberScope.this.getSession()));
                buildSyntheticProperty.setName(property.getName());
                buildSyntheticProperty.setSymbol(new FirJavaOverriddenSyntheticPropertySymbol(new CallableId(getterSymbol.getCallableId().getPackageName(), getterSymbol.getCallableId().getClassName(), property.getName(), null, 8, null), getterSymbol.getCallableId()));
                buildSyntheticProperty.setDelegateGetter((FirSimpleFunction) getterSymbol.getFir());
                FirNamedFunctionSymbol firNamedFunctionSymbol = setterSymbol;
                buildSyntheticProperty.setDelegateSetter(firNamedFunctionSymbol != null ? (FirSimpleFunction) firNamedFunctionSymbol.getFir() : null);
                FirDeclarationStatus status = ((FirSimpleFunction) getterSymbol.getFir()).getStatus();
                if (!takeModalityFromGetter) {
                    modalityChooseModalityForAccessor = JavaClassUseSiteMemberScope.this.chooseModalityForAccessor(property, buildSyntheticProperty.getDelegateGetter());
                } else {
                    modalityChooseModalityForAccessor = buildSyntheticProperty.getDelegateGetter().getStatus().getModality();
                    if (modalityChooseModalityForAccessor == null) {
                        modalityChooseModalityForAccessor = property.getStatus().getModality();
                    }
                }
                buildSyntheticProperty.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : modalityChooseModalityForAccessor, (2097147 & 4) != 0 ? status.isExpect() : false, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
                buildSyntheticProperty.setDeprecationsProvider(DeprecationUtilsKt.getDeprecationsProviderFromAccessors(JavaClassUseSiteMemberScope.this.getSession(), buildSyntheticProperty.getDelegateGetter(), buildSyntheticProperty.getDelegateSetter()));
            }
        }).getSymbol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Modality chooseModalityForAccessor(FirProperty property, FirSimpleFunction getter) {
        Modality modality = property.getStatus().getModality();
        Modality modality2 = getter.getStatus().getModality();
        if (modality == null) {
            return modality2;
        }
        return modality2 == null ? modality : (Modality) ComparisonsKt.minOf(modality, modality2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope
    protected Collection<FirVariableSymbol<?>> collectProperties(Name name2) {
        FirVariableSymbol firVariableSymbol;
        Intrinsics.checkNotNullParameter(name2, "name");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        final LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        getDeclaredMemberScope().processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope.collectProperties.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol2) {
                invoke2(firVariableSymbol2);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> variableSymbol) {
                Intrinsics.checkNotNullParameter(variableSymbol, "variableSymbol");
                if (variableSymbol.getRawStatus().isStatic()) {
                    return;
                }
                linkedHashSet.add(variableSymbol);
                linkedHashSet2.add(((FirVariable) variableSymbol.getFir()).getName());
                linkedHashSet3.add(variableSymbol);
            }
        });
        FirTypeIntersectionScopeContext supertypeScopeContext = getSupertypeScopeContext();
        List<FirTypeScope> scopes = supertypeScopeContext.getScopes();
        ArrayList arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            final ArrayList arrayList2 = new ArrayList();
            firTypeScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope$collectProperties$$inlined$collectIntersectionResultsForCallables$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol2) {
                    invoke(firVariableSymbol2);
                    return Unit.INSTANCE;
                }

                public final void invoke(FirVariableSymbol<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirConstructorSymbol) {
                        return;
                    }
                    arrayList2.add(it);
                }
            });
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        List listConvertGroupedCallablesToIntersectionResults = supertypeScopeContext.convertGroupedCallablesToIntersectionResults(arrayList);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj : listConvertGroupedCallablesToIntersectionResults) {
            FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection = (FirTypeIntersectionScopeContext.ResultOfIntersection) obj;
            if ((resultOfIntersection instanceof FirTypeIntersectionScopeContext.ResultOfIntersection.SingleMember) && (resultOfIntersection.getChosenSymbol() instanceof FirFieldSymbol)) {
                arrayList3.add(obj);
            } else {
                arrayList4.add(obj);
            }
        }
        Pair pair2 = new Pair(arrayList3, arrayList4);
        List list = (List) pair2.component1();
        List list2 = (List) pair2.component2();
        list.size();
        list2.size();
        FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection2 = (FirTypeIntersectionScopeContext.ResultOfIntersection) CollectionsKt.firstOrNull(list);
        if (resultOfIntersection2 != null && (firVariableSymbol = (FirVariableSymbol) resultOfIntersection2.getChosenSymbol()) != null) {
            if (!(firVariableSymbol instanceof FirFieldSymbol)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (!linkedHashSet2.contains(firVariableSymbol.getName())) {
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet3, firVariableSymbol);
            }
        }
        FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection3 = (FirTypeIntersectionScopeContext.ResultOfIntersection) CollectionsKt.firstOrNull(list2);
        if (resultOfIntersection3 == null) {
            return linkedHashSet3;
        }
        FirSyntheticPropertySymbol value = this.syntheticPropertyCache.getValue(name2, TuplesKt.to(this, resultOfIntersection3));
        FirSyntheticPropertySymbol firSyntheticPropertySymbol = value != null ? value : (FirPropertySymbol) resultOfIntersection3.getChosenSymbol();
        getDirectOverriddenProperties().put(firSyntheticPropertySymbol, CollectionsKt.listOf(resultOfIntersection3));
        Iterator it = resultOfIntersection3.getOverriddenMembers().iterator();
        while (it.hasNext()) {
            getOverrideByBase().put(((MemberWithBaseScope) it.next()).getMember(), value);
        }
        LinkedHashSet linkedHashSet4 = linkedHashSet3;
        linkedHashSet4.add(firSyntheticPropertySymbol);
        return linkedHashSet4;
    }

    public final FirSyntheticPropertySymbol syntheticPropertyFromOverride$java(FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol> overriddenProperty) {
        FirSyntheticPropertySymbol firSyntheticPropertySymbol;
        Intrinsics.checkNotNullParameter(overriddenProperty, "overriddenProperty");
        Iterator it = overriddenProperty.getOverriddenMembers().iterator();
        do {
            firSyntheticPropertySymbol = null;
            if (!it.hasNext()) {
                break;
            }
            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) ((MemberWithBaseScope) it.next()).component1();
            FirSyntheticPropertySymbol firSyntheticPropertySymbolCreateOverridePropertyIfExists = createOverridePropertyIfExists(firPropertySymbol, getDeclaredMemberScope(), true);
            if (firSyntheticPropertySymbolCreateOverridePropertyIfExists == null) {
                Iterator<T> it2 = getSuperTypeScopes().iterator();
                while (it2.hasNext()) {
                    FirSyntheticPropertySymbol firSyntheticPropertySymbolCreateOverridePropertyIfExists2 = createOverridePropertyIfExists(firPropertySymbol, (FirTypeScope) it2.next(), false);
                    if (firSyntheticPropertySymbolCreateOverridePropertyIfExists2 != null) {
                        firSyntheticPropertySymbol = firSyntheticPropertySymbolCreateOverridePropertyIfExists2;
                        break;
                    }
                }
            } else {
                firSyntheticPropertySymbol = firSyntheticPropertySymbolCreateOverridePropertyIfExists;
            }
        } while (firSyntheticPropertySymbol == null);
        return firSyntheticPropertySymbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirSyntheticPropertySymbol createOverridePropertyIfExists(FirPropertySymbol firPropertySymbol, FirScope firScope, boolean z) {
        FirNamedFunctionSymbol firNamedFunctionSymbolFindSetterOverride;
        FirNamedFunctionSymbol firNamedFunctionSymbolFindGetterOverride = findGetterOverride(firPropertySymbol, firScope);
        if (firNamedFunctionSymbolFindGetterOverride == null) {
            return null;
        }
        if (((FirProperty) firPropertySymbol.getFir()).getIsVar()) {
            firNamedFunctionSymbolFindSetterOverride = findSetterOverride(firPropertySymbol, firScope);
            if (firNamedFunctionSymbolFindSetterOverride == null) {
                return null;
            }
        } else {
            firNamedFunctionSymbolFindSetterOverride = null;
        }
        if (firNamedFunctionSymbolFindSetterOverride == null || ((FirMemberDeclaration) firNamedFunctionSymbolFindSetterOverride.getFir()).getStatus().getModality() == ((FirMemberDeclaration) firNamedFunctionSymbolFindGetterOverride.getFir()).getStatus().getModality()) {
            return generateSyntheticPropertySymbol(firNamedFunctionSymbolFindGetterOverride, firNamedFunctionSymbolFindSetterOverride, (FirProperty) firPropertySymbol.getFir(), z);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirNamedFunctionSymbol findGetterOverride(FirPropertySymbol firPropertySymbol, FirScope firScope) {
        String strAsString;
        Name builtinSpecialPropertyGetterName = getCanUseSpecialGetters() ? getBuiltinSpecialPropertyGetterName(firPropertySymbol) : null;
        if (builtinSpecialPropertyGetterName == null || (strAsString = builtinSpecialPropertyGetterName.asString()) == null) {
            String strAsString2 = ((FirProperty) firPropertySymbol.getFir()).getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            strAsString = JvmAbi.getterName(strAsString2);
        }
        Intrinsics.checkNotNull(strAsString);
        return findGetterByName(firPropertySymbol, strAsString, firScope);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0051  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirNamedFunctionSymbol findGetterByName(FirPropertySymbol firPropertySymbol, String str, FirScope firScope) {
        FirTypeRef returnTypeRef = ((FirProperty) firPropertySymbol.getFir()).getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        Name nameIdentifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (FirNamedFunctionSymbol firNamedFunctionSymbol : FirScopeKt.getFunctions(firScope, nameIdentifier)) {
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir();
            if (firSimpleFunction.getValueParameters().isEmpty()) {
                ConeKotlinType coneKotlinTypeProbablyFlexible$default = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(firSimpleFunction.getReturnTypeRef(), getSession(), this.typeParameterStack, null, 4, null);
                if (!isAcceptableAsAccessorOverride(firSimpleFunction) || (type != null && !AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(getSession()), (KotlinTypeMarker) coneKotlinTypeProbablyFlexible$default, (KotlinTypeMarker) type, false, 8, (Object) null))) {
                    firNamedFunctionSymbol = null;
                }
            } else {
                firNamedFunctionSymbol = null;
            }
            if (firNamedFunctionSymbol != null) {
                return firNamedFunctionSymbol;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x006a  */
    /* JADX WARN: Multi-variable type inference failed */
    private final FirNamedFunctionSymbol findSetterOverride(FirPropertySymbol firPropertySymbol, FirScope firScope) {
        FirTypeRef returnTypeRef = ((FirProperty) firPropertySymbol.getFir()).getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        if (type == null) {
            return null;
        }
        String strAsString = ((FirProperty) firPropertySymbol.getFir()).getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Name nameIdentifier = Name.identifier(JvmAbi.setterName(strAsString));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        for (FirNamedFunctionSymbol firNamedFunctionSymbol : FirScopeKt.getFunctions(firScope, nameIdentifier)) {
            FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir();
            if (firSimpleFunction.getValueParameters().size() == 1 && ConeBuiltinTypeUtilsKt.isUnit(JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(firSimpleFunction.getReturnTypeRef(), getSession(), this.typeParameterStack, null, 4, null))) {
                ConeKotlinType coneKotlinTypeProbablyFlexible$default = JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(((FirValueParameter) CollectionsKt.single((List) firSimpleFunction.getValueParameters())).getReturnTypeRef(), getSession(), this.typeParameterStack, null, 4, null);
                if (!isAcceptableAsAccessorOverride(firSimpleFunction) || !AbstractTypeChecker.equalTypes$default(AbstractTypeChecker.INSTANCE, TypeComponentsKt.getTypeContext(getSession()), coneKotlinTypeProbablyFlexible$default, type, false, 8, null)) {
                    firNamedFunctionSymbol = null;
                }
            } else {
                firNamedFunctionSymbol = null;
            }
            if (firNamedFunctionSymbol != null) {
                return firNamedFunctionSymbol;
            }
        }
        return null;
    }

    private final boolean isAcceptableAsAccessorOverride(FirSimpleFunction firSimpleFunction) {
        return DeclarationUtilsKt.isJavaOrEnhancement(firSimpleFunction) || firSimpleFunction.getTypeParameters().isEmpty();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Name getBuiltinSpecialPropertyGetterName(FirPropertySymbol firPropertySymbol) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        FirScopeKt.processOverriddenPropertiesAndSelf(getSuperTypeScopes(), firPropertySymbol, new Function1<FirPropertySymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope.getBuiltinSpecialPropertyGetterName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v4, types: [T, org.jetbrains.kotlin.name.Name] */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirPropertySymbol overridden) {
                ClassId classId;
                FqName fqNameAsSingleFqName;
                Intrinsics.checkNotNullParameter(overridden, "overridden");
                ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag((FirCallableDeclaration) overridden.getFir());
                Name name2 = BuiltinSpecialProperties.INSTANCE.getPROPERTY_FQ_NAME_TO_JVM_GETTER_NAME_MAP().get((coneClassLikeLookupTagContainingClassLookupTag == null || (classId = coneClassLikeLookupTagContainingClassLookupTag.getClassId()) == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null) ? null : fqNameAsSingleFqName.child(((FirProperty) overridden.getFir()).getName()));
                if (name2 != 0) {
                    objectRef.element = name2;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
        return (Name) objectRef.element;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope
    protected boolean isVisibleInCurrentClass(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        List<Name> propertyNamesCandidatesByAccessorName = PropertiesConventionUtilKt.getPropertyNamesCandidatesByAccessorName(firNamedFunctionSymbol.getName());
        if (!(propertyNamesCandidatesByAccessorName instanceof Collection) || !propertyNamesCandidatesByAccessorName.isEmpty()) {
            Iterator<T> it = propertyNamesCandidatesByAccessorName.iterator();
            while (it.hasNext()) {
                List<FirVariableSymbol<?>> properties = FirScopeKt.getProperties(this, (Name) it.next());
                if (!(properties instanceof Collection) || !properties.isEmpty()) {
                    Iterator<T> it2 = properties.iterator();
                    while (it2.hasNext()) {
                        FirVariableSymbol firVariableSymbol = (FirVariableSymbol) it2.next();
                        if (!(firVariableSymbol instanceof FirPropertySymbol) ? false : isOverriddenInClassBy((FirPropertySymbol) firVariableSymbol, firNamedFunctionSymbol)) {
                            return false;
                        }
                    }
                }
            }
        }
        return (doesOverrideRenamedBuiltins(firNamedFunctionSymbol) || shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(firNamedFunctionSymbol) || doesOverrideSuspendFunction(firNamedFunctionSymbol)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean doesOverrideRenamedBuiltins(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Name builtinFunctionNamesByJvmName = SpecialGenericSignatures.INSTANCE.getBuiltinFunctionNamesByJvmName(firNamedFunctionSymbol.getName());
        if (builtinFunctionNamesByJvmName == null) {
            return false;
        }
        List<FirTypeScope> scopes = getSupertypeScopeContext().getScopes();
        ArrayList<Pair> arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            final ArrayList arrayList2 = new ArrayList();
            firTypeScope.processFunctionsByName(builtinFunctionNamesByJvmName, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope$doesOverrideRenamedBuiltins$$inlined$collectMembersGroupedByScope$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol2) {
                    invoke(firNamedFunctionSymbol2);
                    return Unit.INSTANCE;
                }

                public final void invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirConstructorSymbol) {
                        return;
                    }
                    arrayList2.add(it);
                }
            });
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Pair pair2 : arrayList) {
            FirTypeScope firTypeScope2 = (FirTypeScope) pair2.component1();
            List list = (List) pair2.component2();
            ArrayList arrayList4 = new ArrayList();
            for (Object obj : list) {
                if (JavaScopeUtilsKt.doesOverrideBuiltinWithDifferentJvmName((FirNamedFunctionSymbol) obj, firTypeScope2, getSession())) {
                    arrayList4.add(obj);
                }
            }
            CollectionsKt.addAll(arrayList3, arrayList4);
        }
        ArrayList arrayList5 = arrayList3;
        if (arrayList5.isEmpty()) {
            return false;
        }
        ArrayList arrayList6 = arrayList5;
        if ((arrayList6 instanceof Collection) && arrayList6.isEmpty()) {
            return false;
        }
        Iterator it = arrayList6.iterator();
        while (it.hasNext()) {
            if (getOverrideChecker().isOverriddenFunction((FirSimpleFunction) firNamedFunctionSymbol.getFir(), (FirSimpleFunction) ((FirNamedFunctionSymbol) it.next()).getFir())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        if (!SpecialGenericSignatures.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(firNamedFunctionSymbol.getName())) {
            return false;
        }
        FirTypeIntersectionScopeContext supertypeScopeContext = getSupertypeScopeContext();
        Name name2 = firNamedFunctionSymbol.getName();
        List<FirTypeScope> scopes = supertypeScopeContext.getScopes();
        ArrayList<Pair> arrayList = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            final ArrayList arrayList2 = new ArrayList();
            firTypeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope$shouldBeVisibleAsOverrideOfBuiltInWithErasedValueParameters$$inlined$collectMembersGroupedByScope$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol2) {
                    invoke(firNamedFunctionSymbol2);
                    return Unit.INSTANCE;
                }

                public final void invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirConstructorSymbol) {
                        return;
                    }
                    arrayList2.add(it);
                }
            });
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            Pair pair = arrayList2 != null ? TuplesKt.to(firTypeScope, arrayList2) : null;
            if (pair != null) {
                arrayList.add(pair);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (Pair pair2 : arrayList) {
            FirTypeScope firTypeScope2 = (FirTypeScope) pair2.component1();
            List list = (List) pair2.component2();
            ArrayList arrayList4 = new ArrayList();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                FirNamedFunctionSymbol overriddenBuiltinFunctionWithErasedValueParametersInJava = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((FirNamedFunctionSymbol) it.next(), firTypeScope2);
                if (overriddenBuiltinFunctionWithErasedValueParametersInJava != null) {
                    arrayList4.add(overriddenBuiltinFunctionWithErasedValueParametersInJava);
                }
            }
            CollectionsKt.addAll(arrayList3, arrayList4);
        }
        String strComputeJvmDescriptor$default = computeJvmDescriptor$default(this, (FirFunction) firNamedFunctionSymbol.getFir(), null, false, 3, null);
        ArrayList arrayList5 = arrayList3;
        if ((arrayList5 instanceof Collection) && arrayList5.isEmpty()) {
            return false;
        }
        Iterator it2 = arrayList5.iterator();
        while (it2.hasNext()) {
            if (Intrinsics.areEqual(computeJvmDescriptor$default(this, (FirFunction) ((FirNamedFunctionSymbol) it2.next()).getFir(), null, false, 3, null), strComputeJvmDescriptor$default) && hasErasedParameters(firNamedFunctionSymbol)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean doesOverrideSuspendFunction(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        FirSimpleFunction firSimpleFunctionCreateSuspendView = createSuspendView(firNamedFunctionSymbol);
        if (firSimpleFunctionCreateSuspendView == null) {
            return false;
        }
        List<FirTypeScope> superTypeScopes = getSuperTypeScopes();
        if ((superTypeScopes instanceof Collection) && superTypeScopes.isEmpty()) {
            return false;
        }
        Iterator<T> it = superTypeScopes.iterator();
        while (it.hasNext()) {
            List<FirNamedFunctionSymbol> functions = FirScopeKt.getFunctions((FirTypeScope) it.next(), firNamedFunctionSymbol.getName());
            if (!(functions instanceof Collection) || !functions.isEmpty()) {
                for (FirNamedFunctionSymbol firNamedFunctionSymbol2 : functions) {
                    if (firNamedFunctionSymbol2.getRawStatus().isSuspend() && getOverrideChecker().isOverriddenFunction(firSimpleFunctionCreateSuspendView, (FirSimpleFunction) firNamedFunctionSymbol2.getFir())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirSimpleFunction createSuspendView(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        FirValueParameter firValueParameter = (FirValueParameter) CollectionsKt.lastOrNull((List) ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters());
        if (firValueParameter == null) {
            return null;
        }
        FirTypeRef returnTypeRef = firValueParameter.getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = type != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(type) : null;
        ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        if (coneClassLikeType == null || !Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId().asSingleFqName(), StandardNames.CONTINUATION_INTERFACE_FQ_NAME)) {
            return null;
        }
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir();
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        firSimpleFunctionBuilder.setSource(firSimpleFunction.getSource());
        firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firSimpleFunction));
        firSimpleFunctionBuilder.setModuleData(firSimpleFunction.getModuleData());
        firSimpleFunctionBuilder.setOrigin(firSimpleFunction.getOrigin());
        firSimpleFunctionBuilder.setAttributes(firSimpleFunction.getAttributes().copy());
        firSimpleFunctionBuilder.setStatus(firSimpleFunction.getStatus());
        firSimpleFunctionBuilder.setReturnTypeRef(firSimpleFunction.getReturnTypeRef());
        firSimpleFunctionBuilder.setReceiverParameter(firSimpleFunction.getReceiverParameter());
        firSimpleFunctionBuilder.setDeprecationsProvider(firSimpleFunction.getDeprecationsProvider());
        firSimpleFunctionBuilder.setContainerSource(firSimpleFunction.getContainerSource());
        firSimpleFunctionBuilder.setDispatchReceiverType(firSimpleFunction.getDispatchReceiverType());
        firSimpleFunctionBuilder.getContextReceivers().addAll(firSimpleFunction.getContextReceivers());
        firSimpleFunctionBuilder.getValueParameters().addAll(firSimpleFunction.getValueParameters());
        firSimpleFunctionBuilder.setBody(firSimpleFunction.getBody());
        firSimpleFunctionBuilder.setContractDescription(firSimpleFunction.getContractDescription());
        firSimpleFunctionBuilder.setName(firSimpleFunction.getName());
        firSimpleFunctionBuilder.setSymbol(firSimpleFunction.getSymbol());
        firSimpleFunctionBuilder.getAnnotations().addAll(firSimpleFunction.getAnnotations());
        firSimpleFunctionBuilder.getTypeParameters().addAll(firSimpleFunction.getTypeParameters());
        firSimpleFunctionBuilder.getValueParameters().clear();
        firSimpleFunctionBuilder.getValueParameters().addAll(CollectionsKt.dropLast(((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters(), 1));
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        ConeKotlinType type2 = ConeTypeProjectionKt.getType(coneClassLikeType.getTypeArguments()[0]);
        if (type2 == null) {
            return null;
        }
        firResolvedTypeRefBuilder.setType(type2);
        firSimpleFunctionBuilder.setReturnTypeRef(firResolvedTypeRefBuilder.mo6809build());
        FirDeclarationStatus status = firSimpleFunctionBuilder.getStatus();
        Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl");
        ((FirDeclarationStatusImpl) status).setSuspend(true);
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(firNamedFunctionSymbol.getCallableId()));
        return firSimpleFunctionBuilder.mo6809build();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope
    protected Collection<FirNamedFunctionSymbol> collectFunctions(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        ArrayList arrayList = new ArrayList();
        collectDeclaredFunctions(name2, arrayList);
        ArrayList arrayList2 = arrayList;
        Set<? extends FirNamedFunctionSymbol> set = CollectionsKt.toSet(arrayList2);
        List<FirTypeScope> scopes = getSupertypeScopeContext().getScopes();
        ArrayList arrayList3 = new ArrayList();
        for (FirTypeScope firTypeScope : scopes) {
            final ArrayList arrayList4 = new ArrayList();
            firTypeScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope$collectFunctions$$inlined$collectMembersGroupedByScope$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                    invoke(firNamedFunctionSymbol);
                    return Unit.INSTANCE;
                }

                public final void invoke(FirNamedFunctionSymbol it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it instanceof FirConstructorSymbol) {
                        return;
                    }
                    arrayList4.add(it);
                }
            });
            if (arrayList4.isEmpty()) {
                arrayList4 = null;
            }
            Pair pair = arrayList4 != null ? TuplesKt.to(firTypeScope, arrayList4) : null;
            if (pair != null) {
                arrayList3.add(pair);
            }
        }
        ArrayList arrayList5 = arrayList3;
        if (!SpecialGenericSignatures.INSTANCE.getSameAsRenamedInJvmBuiltin(name2) && !SpecialGenericSignatures.INSTANCE.getSameAsBuiltinMethodWithErasedValueParameters(name2)) {
            ArrayList arrayList6 = arrayList5;
            if (!(arrayList6 instanceof Collection) || !arrayList6.isEmpty()) {
                Iterator it = arrayList6.iterator();
                while (it.hasNext()) {
                    Iterable iterable = (Iterable) ((Pair) it.next()).getSecond();
                    if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                        Iterator it2 = iterable.iterator();
                        while (it2.hasNext()) {
                            if (((FirNamedFunctionSymbol) it2.next()).getRawStatus().isSuspend()) {
                            }
                        }
                    }
                }
            }
            super.collectFunctionsFromSupertypes(name2, arrayList, set);
            return arrayList;
        }
        processSpecialFunctions(name2, set, arrayList5, arrayList);
        return CollectionsKt.toSet(arrayList2);
    }

    private final void processSpecialFunctions(Name requestedName, Collection<? extends FirNamedFunctionSymbol> explicitlyDeclaredFunctionsWithNaturalName, List<? extends Pair<? extends FirTypeScope, ? extends List<? extends FirNamedFunctionSymbol>>> functionsFromSupertypesWithRequestedName, Collection<FirNamedFunctionSymbol> destination) {
        Object next;
        Name jvmMethodNameIfSpecial;
        ArrayList arrayList = new ArrayList();
        for (FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol> resultOfIntersection : getSupertypeScopeContext().convertGroupedCallablesToIntersectionResults(functionsFromSupertypesWithRequestedName)) {
            FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) extractSomeSymbolFromSuperType(resultOfIntersection);
            Iterator<T> it = explicitlyDeclaredFunctionsWithNaturalName.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!FirOverrideCheckerKt.isOverriddenFunction(getOverrideChecker(), (FirNamedFunctionSymbol) next, firNamedFunctionSymbol));
            FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) next;
            Iterator it2 = resultOfIntersection.getOverriddenMembers().iterator();
            do {
                if (!it2.hasNext()) {
                    jvmMethodNameIfSpecial = null;
                    break;
                } else {
                    MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) it2.next();
                    jvmMethodNameIfSpecial = JavaScopeUtilsKt.getJvmMethodNameIfSpecial(memberWithBaseScope.getMember(), memberWithBaseScope.getBaseScope(), getSession());
                }
            } while (jvmMethodNameIfSpecial == null);
            if (jvmMethodNameIfSpecial != null) {
                processOverridesForFunctionsWithDifferentJvmName(jvmMethodNameIfSpecial, firNamedFunctionSymbol, firNamedFunctionSymbol2, requestedName, resultOfIntersection, destination, arrayList);
            } else if (!processOverridesForFunctionsWithErasedValueParameter(resultOfIntersection.getOverriddenMembers(), requestedName, explicitlyDeclaredFunctionsWithNaturalName, destination, resultOfIntersection, firNamedFunctionSymbol2)) {
                if (firNamedFunctionSymbol2 == null) {
                    FirNamedFunctionSymbol firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) resultOfIntersection.getChosenSymbol();
                    if (isVisibleInCurrentClass(firNamedFunctionSymbol3)) {
                        destination.add(firNamedFunctionSymbol3);
                        arrayList.add(resultOfIntersection);
                    }
                } else {
                    destination.add(firNamedFunctionSymbol2);
                    getDirectOverriddenFunctions().put(firNamedFunctionSymbol2, CollectionsKt.listOf(resultOfIntersection));
                    Iterator it3 = resultOfIntersection.getOverriddenMembers().iterator();
                    while (it3.hasNext()) {
                        getOverrideByBase().put(((MemberWithBaseScope) it3.next()).getMember(), firNamedFunctionSymbol2);
                    }
                }
            }
        }
        getFunctionsFromSupertypes().put(requestedName, arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean processOverridesForFunctionsWithErasedValueParameter(List<? extends MemberWithBaseScope<? extends FirNamedFunctionSymbol>> overriddenMembers, Name naturalName, Collection<? extends FirNamedFunctionSymbol> explicitlyDeclaredFunctionsWithNaturalName, Collection<FirNamedFunctionSymbol> destination, FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol> resultOfIntersectionWithNaturalName, FirNamedFunctionSymbol explicitlyDeclaredFunctionWithNaturalName) {
        Object obj;
        Object next;
        FirNamedFunctionSymbol firNamedFunctionSymbol;
        Iterator<T> it = overriddenMembers.iterator();
        do {
            obj = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (BuiltinMethodsWithSpecialGenericSignature.INSTANCE.getOverriddenBuiltinFunctionWithErasedValueParametersInJava((MemberWithBaseScope) next) == null);
        MemberWithBaseScope memberWithBaseScope = (MemberWithBaseScope) next;
        if (memberWithBaseScope == null || (firNamedFunctionSymbol = (FirNamedFunctionSymbol) memberWithBaseScope.getMember()) == null) {
            return false;
        }
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firNamedFunctionSymbol.getFir();
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) ((ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null);
        if (firSimpleFunction == null) {
            firSimpleFunction = (FirSimpleFunction) firNamedFunctionSymbol.getFir();
        }
        FirCallableDeclaration initialSignatureAttr = ClassMembersKt.getInitialSignatureAttr(firSimpleFunction);
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = initialSignatureAttr != null ? initialSignatureAttr.getSymbol() : null;
        FirNamedFunctionSymbol symbol2 = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
        if (symbol2 == null) {
            symbol2 = firSimpleFunction.getSymbol();
        }
        for (Object obj2 : FirScopeKt.getFunctions(getDeclaredMemberScope(), naturalName)) {
            FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) obj2;
            if (hasSameJvmDescriptor(firNamedFunctionSymbol2, symbol2) && hasErasedParameters(firNamedFunctionSymbol2) && getJavaOverrideChecker().doesReturnTypesHaveSameKind((FirSimpleFunction) symbol2.getFir(), (FirSimpleFunction) firNamedFunctionSymbol2.getFir())) {
                obj = obj2;
                break;
            }
        }
        FirNamedFunctionSymbol firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) obj;
        if (firNamedFunctionSymbol3 == null) {
            return false;
        }
        D fir = firNamedFunctionSymbol3.getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.java.declarations.FirJavaMethod");
        FirJavaMethod firJavaMethod = (FirJavaMethod) fir;
        FirJavaMethodBuilder firJavaMethodBuilder = new FirJavaMethodBuilder();
        firJavaMethodBuilder.setSource(firJavaMethod.getSource());
        firJavaMethodBuilder.setModuleData(firJavaMethod.getModuleData());
        firJavaMethodBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firJavaMethod));
        firJavaMethodBuilder.setAttributes(firJavaMethod.getAttributes().copy());
        firJavaMethodBuilder.setReturnTypeRef(firJavaMethod.getReturnTypeRef());
        firJavaMethodBuilder.getValueParameters().addAll(firJavaMethod.getValueParameters());
        firJavaMethodBuilder.setBody(firJavaMethod.getBody());
        firJavaMethodBuilder.setStatus(firJavaMethod.getStatus());
        firJavaMethodBuilder.setDispatchReceiverType(firJavaMethod.getDispatchReceiverType());
        firJavaMethodBuilder.setName(firJavaMethod.getName());
        firJavaMethodBuilder.setSymbol(firJavaMethod.getSymbol());
        firJavaMethodBuilder.setFromSource(firJavaMethod.getOrigin().getFromSource());
        firJavaMethodBuilder.getAnnotations().addAll(firJavaMethod.getAnnotations());
        firJavaMethodBuilder.getTypeParameters().addAll(firJavaMethod.getTypeParameters());
        firJavaMethodBuilder.setAnnotationBuilder(new FirJavaMethodKt.AnonymousClass2(firJavaMethod.getAnnotations()));
        firJavaMethodBuilder.setName(naturalName);
        firJavaMethodBuilder.setSymbol(new FirNamedFunctionSymbol(firNamedFunctionSymbol3.getCallableId()));
        firJavaMethodBuilder.getValueParameters().clear();
        List<Pair> listZip = CollectionsKt.zip(((FirSimpleFunction) firNamedFunctionSymbol3.getFir()).getValueParameters(), ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters());
        List<FirValueParameter> valueParameters = firJavaMethodBuilder.getValueParameters();
        for (Pair pair : listZip) {
            FirValueParameter firValueParameter = (FirValueParameter) pair.component1();
            FirValueParameter firValueParameter2 = (FirValueParameter) pair.component2();
            FirJavaValueParameterBuilder firJavaValueParameterBuilder = new FirJavaValueParameterBuilder();
            firJavaValueParameterBuilder.setSource(firValueParameter.getSource());
            firJavaValueParameterBuilder.setModuleData(firValueParameter.getModuleData());
            firJavaValueParameterBuilder.setAttributes(firValueParameter.getAttributes().copy());
            firJavaValueParameterBuilder.setFromSource(firValueParameter.getOrigin().getFromSource());
            firJavaValueParameterBuilder.setReturnTypeRef(firValueParameter.getReturnTypeRef());
            firJavaValueParameterBuilder.setName(firValueParameter.getName());
            firJavaValueParameterBuilder.setAnnotationBuilder(new FirJavaValueParameterKt.AnonymousClass2(firValueParameter.getAnnotations()));
            firJavaValueParameterBuilder.setDefaultValue(firValueParameter.getDefaultValue());
            firJavaValueParameterBuilder.setContainingFunctionSymbol(firValueParameter.getContainingFunctionSymbol());
            firJavaValueParameterBuilder.setVararg(firValueParameter.getIsVararg());
            firJavaValueParameterBuilder.setReturnTypeRef(firValueParameter2.getReturnTypeRef());
            valueParameters.add(firJavaValueParameterBuilder.build());
        }
        FirJavaMethod firJavaMethodMo6809build = firJavaMethodBuilder.mo6809build();
        ClassMembersKt.setInitialSignatureAttr(firJavaMethodMo6809build, (FirCallableDeclaration) firNamedFunctionSymbol3.getFir());
        FirNamedFunctionSymbol symbol3 = firJavaMethodMo6809build.getSymbol();
        Collection<? extends FirNamedFunctionSymbol> collection = explicitlyDeclaredFunctionsWithNaturalName;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator<T> it2 = collection.iterator();
            while (it2.hasNext()) {
                if (FirOverrideCheckerKt.isOverriddenFunction(getOverrideChecker(), symbol3, (FirNamedFunctionSymbol) it2.next())) {
                    return false;
                }
            }
        }
        destination.add(symbol3);
        getDirectOverriddenFunctions().put(symbol3, CollectionsKt.listOf(resultOfIntersectionWithNaturalName));
        Iterator<? extends MemberWithBaseScope<? extends FirNamedFunctionSymbol>> it3 = overriddenMembers.iterator();
        while (it3.hasNext()) {
            getOverrideByBase().put(it3.next().getMember(), symbol3);
        }
        if (explicitlyDeclaredFunctionWithNaturalName == null) {
            return true;
        }
        destination.add(explicitlyDeclaredFunctionWithNaturalName);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean hasSameJvmDescriptor(FirNamedFunctionSymbol firNamedFunctionSymbol, FirNamedFunctionSymbol firNamedFunctionSymbol2) {
        return Intrinsics.areEqual(computeJvmDescriptor$default(this, (FirFunction) firNamedFunctionSymbol.getFir(), null, false, 1, null), computeJvmDescriptor$default(this, (FirFunction) firNamedFunctionSymbol2.getFir(), null, false, 1, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void processOverridesForFunctionsWithDifferentJvmName(Name jvmName, FirNamedFunctionSymbol someSymbolWithNaturalNameFromSuperType, FirNamedFunctionSymbol explicitlyDeclaredFunctionWithNaturalName, Name naturalName, FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol> resultOfIntersectionWithNaturalName, Collection<FirNamedFunctionSymbol> destination, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> resultsOfIntersectionToSaveInCache) {
        ArrayList arrayList;
        Object next;
        Object next2;
        FirNamedFunctionSymbol symbol;
        List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> listConvertGroupedCallablesToIntersectionResults;
        List overriddenMembers;
        Name name2 = naturalName;
        Iterable<MemberWithBaseScope> overriddenMembers2 = resultOfIntersectionWithNaturalName.getOverriddenMembers();
        Iterator<T> it = FirScopeKt.getFunctions(getDeclaredMemberScope(), jvmName).iterator();
        do {
            arrayList = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!FirOverrideCheckerKt.isOverriddenFunction(getOverrideChecker(), (FirNamedFunctionSymbol) next, someSymbolWithNaturalNameFromSuperType));
        FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) next;
        Iterator<T> it2 = getSupertypeScopeContext().collectFunctions(jvmName).iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
        } while (!AbstractFirOverrideScopeKt.similarFunctionsOrBothProperties(getOverrideChecker(), (FirCallableSymbol<?>) extractSomeSymbolFromSuperType((FirTypeIntersectionScopeContext.ResultOfIntersection) next2), someSymbolWithNaturalNameFromSuperType));
        FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection = (FirTypeIntersectionScopeContext.ResultOfIntersection) next2;
        if (explicitlyDeclaredFunctionWithNaturalName != null) {
            symbol = explicitlyDeclaredFunctionWithNaturalName;
        } else if (firNamedFunctionSymbol != null) {
            D fir = firNamedFunctionSymbol.getFir();
            Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.java.declarations.FirJavaMethod");
            FirJavaMethod firJavaMethod = (FirJavaMethod) fir;
            FirJavaMethodBuilder firJavaMethodBuilder = new FirJavaMethodBuilder();
            FirJavaMethod firJavaMethod2 = firJavaMethod;
            firJavaMethodBuilder.setSource(firJavaMethod2.getSource());
            firJavaMethodBuilder.setModuleData(firJavaMethod2.getModuleData());
            firJavaMethodBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firJavaMethod2));
            firJavaMethodBuilder.setAttributes(firJavaMethod2.getAttributes().copy());
            firJavaMethodBuilder.setReturnTypeRef(firJavaMethod2.getReturnTypeRef());
            firJavaMethodBuilder.getValueParameters().addAll(firJavaMethod2.getValueParameters());
            firJavaMethodBuilder.setBody(firJavaMethod2.getBody());
            firJavaMethodBuilder.setStatus(firJavaMethod2.getStatus());
            firJavaMethodBuilder.setDispatchReceiverType(firJavaMethod2.getDispatchReceiverType());
            firJavaMethodBuilder.setName(firJavaMethod2.getName());
            firJavaMethodBuilder.setSymbol(firJavaMethod2.getSymbol());
            firJavaMethodBuilder.setFromSource(firJavaMethod2.getOrigin().getFromSource());
            firJavaMethodBuilder.getAnnotations().addAll(firJavaMethod2.getAnnotations());
            firJavaMethodBuilder.getTypeParameters().addAll(firJavaMethod2.getTypeParameters());
            firJavaMethodBuilder.setAnnotationBuilder(new FirJavaMethodKt.AnonymousClass2(firJavaMethod2.getAnnotations()));
            firJavaMethodBuilder.setName(name2);
            firJavaMethodBuilder.setSymbol(new FirNamedFunctionSymbol(CallableId.copy$default(firNamedFunctionSymbol.getCallableId(), null, null, naturalName, null, 11, null)));
            FirDeclarationStatus status = firJavaMethod.getStatus();
            firJavaMethodBuilder.setStatus(UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : null, (2097147 & 4) != 0 ? status.isExpect() : false, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : true, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false));
            FirJavaMethod firJavaMethodMo6809build = firJavaMethodBuilder.mo6809build();
            ClassMembersKt.setInitialSignatureAttr(firJavaMethodMo6809build, firJavaMethod);
            symbol = firJavaMethodMo6809build.getSymbol();
        } else {
            symbol = null;
        }
        if (resultOfIntersection != null && (overriddenMembers = resultOfIntersection.getOverriddenMembers()) != null) {
            List<MemberWithBaseScope> list = overriddenMembers;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (MemberWithBaseScope memberWithBaseScope : list) {
                FirSimpleFunction firSimpleFunction = (FirSimpleFunction) ((FirNamedFunctionSymbol) memberWithBaseScope.getMember()).getFir();
                FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
                firSimpleFunctionBuilder.setSource(firSimpleFunction.getSource());
                firSimpleFunctionBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firSimpleFunction));
                firSimpleFunctionBuilder.setModuleData(firSimpleFunction.getModuleData());
                firSimpleFunctionBuilder.setOrigin(firSimpleFunction.getOrigin());
                firSimpleFunctionBuilder.setAttributes(firSimpleFunction.getAttributes().copy());
                firSimpleFunctionBuilder.setStatus(firSimpleFunction.getStatus());
                firSimpleFunctionBuilder.setReturnTypeRef(firSimpleFunction.getReturnTypeRef());
                firSimpleFunctionBuilder.setReceiverParameter(firSimpleFunction.getReceiverParameter());
                firSimpleFunctionBuilder.setDeprecationsProvider(firSimpleFunction.getDeprecationsProvider());
                firSimpleFunctionBuilder.setContainerSource(firSimpleFunction.getContainerSource());
                firSimpleFunctionBuilder.setDispatchReceiverType(firSimpleFunction.getDispatchReceiverType());
                firSimpleFunctionBuilder.getContextReceivers().addAll(firSimpleFunction.getContextReceivers());
                firSimpleFunctionBuilder.getValueParameters().addAll(firSimpleFunction.getValueParameters());
                firSimpleFunctionBuilder.setBody(firSimpleFunction.getBody());
                firSimpleFunctionBuilder.setContractDescription(firSimpleFunction.getContractDescription());
                firSimpleFunctionBuilder.setName(firSimpleFunction.getName());
                firSimpleFunctionBuilder.setSymbol(firSimpleFunction.getSymbol());
                firSimpleFunctionBuilder.getAnnotations().addAll(firSimpleFunction.getAnnotations());
                firSimpleFunctionBuilder.getTypeParameters().addAll(firSimpleFunction.getTypeParameters());
                firSimpleFunctionBuilder.setName(name2);
                firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(CallableId.copy$default(((FirNamedFunctionSymbol) memberWithBaseScope.getMember()).getCallableId(), null, null, naturalName, null, 11, null)));
                firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.RenamedForOverride.INSTANCE);
                FirSimpleFunction firSimpleFunctionMo6809build = firSimpleFunctionBuilder.mo6809build();
                ClassMembersKt.setInitialSignatureAttr(firSimpleFunctionMo6809build, (FirCallableDeclaration) ((FirNamedFunctionSymbol) memberWithBaseScope.getMember()).getFir());
                arrayList2.add(TuplesKt.to(memberWithBaseScope.getBaseScope(), CollectionsKt.listOf(firSimpleFunctionMo6809build.getSymbol())));
                name2 = naturalName;
            }
            arrayList = arrayList2;
        }
        if (arrayList == null) {
            listConvertGroupedCallablesToIntersectionResults = CollectionsKt.listOf(resultOfIntersectionWithNaturalName);
        } else {
            List listCreateListBuilder = CollectionsKt.createListBuilder();
            for (MemberWithBaseScope memberWithBaseScope2 : overriddenMembers2) {
                listCreateListBuilder.add(TuplesKt.to(memberWithBaseScope2.getBaseScope(), CollectionsKt.listOf(memberWithBaseScope2.getMember())));
            }
            listCreateListBuilder.addAll(arrayList);
            listConvertGroupedCallablesToIntersectionResults = getSupertypeScopeContext().convertGroupedCallablesToIntersectionResults(CollectionsKt.build(listCreateListBuilder));
        }
        if (symbol != null) {
            destination.add(symbol);
            getDirectOverriddenFunctions().put(symbol, listConvertGroupedCallablesToIntersectionResults);
            Iterator<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> it3 = listConvertGroupedCallablesToIntersectionResults.iterator();
            while (it3.hasNext()) {
                Iterator it4 = it3.next().getOverriddenMembers().iterator();
                while (it4.hasNext()) {
                    getOverrideByBase().put(((MemberWithBaseScope) it4.next()).getMember(), symbol);
                }
            }
            return;
        }
        Iterator<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> it5 = listConvertGroupedCallablesToIntersectionResults.iterator();
        while (it5.hasNext()) {
            destination.add(it5.next().getChosenSymbol());
        }
        CollectionsKt.addAll(resultsOfIntersectionToSaveInCache, listConvertGroupedCallablesToIntersectionResults);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isOverriddenInClassBy(FirPropertySymbol firPropertySymbol, FirNamedFunctionSymbol firNamedFunctionSymbol) {
        ArrayList arrayListListOf;
        boolean z;
        Pair pair;
        FirSimpleFunction delegate;
        FirSimpleFunction delegate2;
        if (Intrinsics.areEqual(firPropertySymbol.getRawStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
            return false;
        }
        FirProperty firProperty = (FirProperty) firPropertySymbol.getFir();
        if (firProperty instanceof FirSyntheticProperty) {
            FirSyntheticProperty firSyntheticProperty = (FirSyntheticProperty) firProperty;
            if (!Intrinsics.areEqual(firSyntheticProperty.getGetter().getDelegate().getSymbol(), firNamedFunctionSymbol)) {
                FirSyntheticPropertyAccessor setter = firSyntheticProperty.getSetter();
                if (!Intrinsics.areEqual((setter == null || (delegate2 = setter.getDelegate()) == null) ? null : delegate2.getSymbol(), firNamedFunctionSymbol)) {
                    String strComputeJvmDescriptor$default = computeJvmDescriptor$default(this, firSyntheticProperty.getGetter().getDelegate(), null, false, 1, null);
                    FirSyntheticPropertyAccessor setter2 = firSyntheticProperty.getSetter();
                    arrayListListOf = CollectionsKt.listOf(TuplesKt.to(strComputeJvmDescriptor$default, (setter2 == null || (delegate = setter2.getDelegate()) == null) ? null : computeJvmDescriptor$default(this, delegate, null, false, 1, null)));
                }
            }
            return true;
        }
        List<Name> listListOfNotNull = CollectionsKt.listOfNotNull(JavaScopeUtilsKt.getJvmMethodNameIfSpecial(firPropertySymbol, this, getSession()));
        if (listListOfNotNull.isEmpty()) {
            listListOfNotNull = null;
        }
        if (listListOfNotNull == null) {
            listListOfNotNull = PropertiesConventionUtilKt.possibleGetMethodNames(firProperty.getName());
        }
        List<Name> list = listListOfNotNull;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Name name2 : list) {
            arrayList.add(TuplesKt.to(computeJvmDescriptorForGetter(firProperty, name2.getIdentifier(), false), firPropertySymbol.isVar() ? computeJvmDescriptorForSetter(firProperty, PropertiesConventionUtilKt.setMethodName(name2).getIdentifier(), false) : null));
        }
        arrayListListOf = arrayList;
        Iterable iterable = arrayListListOf;
        String strComputeJvmDescriptor$default2 = computeJvmDescriptor$default(this, (FirFunction) firNamedFunctionSymbol.getFir(), null, false, 1, null);
        Iterable iterable2 = iterable;
        boolean z2 = iterable2 instanceof Collection;
        if (z2 && ((Collection) iterable2).isEmpty()) {
            z = false;
            break;
        }
        Iterator it = iterable2.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (Intrinsics.areEqual(strComputeJvmDescriptor$default2, (String) ((Pair) it.next()).component1())) {
                if (TypeUtilsKt.isSubtypeOf$default(probablyJavaTypeRefToConeType(((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getReturnTypeRef()), probablyJavaTypeRefToConeType(((FirProperty) firPropertySymbol.getFir()).getReturnTypeRef()), getSession(), false, 4, null)) {
                    z = true;
                    break;
                }
            }
        }
        if (z && firPropertySymbol.isVal()) {
            return true;
        }
        if (!z2 || !((Collection) iterable2).isEmpty()) {
            Iterator it2 = iterable2.iterator();
            while (it2.hasNext()) {
                if (Intrinsics.areEqual(strComputeJvmDescriptor$default2, (String) ((Pair) it2.next()).component2())) {
                    if (z) {
                        pair = TuplesKt.to(firNamedFunctionSymbol, findSetterOverride(firPropertySymbol, this));
                    } else if (!z) {
                        pair = TuplesKt.to(findGetterOverride(firPropertySymbol, this), firNamedFunctionSymbol);
                    } else {
                        throw new NoWhenBranchMatchedException();
                    }
                    FirNamedFunctionSymbol firNamedFunctionSymbol2 = (FirNamedFunctionSymbol) pair.component1();
                    FirNamedFunctionSymbol firNamedFunctionSymbol3 = (FirNamedFunctionSymbol) pair.component2();
                    return (firNamedFunctionSymbol2 != null ? firNamedFunctionSymbol2.getResolvedStatus().getModality() : null) == (firNamedFunctionSymbol3 != null ? firNamedFunctionSymbol3.getResolvedStatus().getModality() : null);
                }
            }
        }
        return false;
    }

    private final ConeKotlinType probablyJavaTypeRefToConeType(FirTypeRef firTypeRef) {
        return firTypeRef instanceof FirJavaTypeRef ? JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(firTypeRef, getSession(), this.typeParameterStack, null, 4, null) : FirTypeUtilsKt.getConeType(firTypeRef);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean hasErasedParameters(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        ConeSimpleKotlinType coneSimpleKotlinTypeUpperBoundIfFlexible = ConeTypeUtilsKt.upperBoundIfFlexible(JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(((FirValueParameter) CollectionsKt.first((List) ((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getValueParameters())).getReturnTypeRef(), getSession(), this.typeParameterStack, null, 4, null));
        if (!(coneSimpleKotlinTypeUpperBoundIfFlexible instanceof ConeClassLikeType)) {
            return false;
        }
        if (SpecialGenericSignatures.INSTANCE.getERASED_COLLECTION_PARAMETER_NAMES().contains(((FirSimpleFunction) firNamedFunctionSymbol.getFir()).getName().asString())) {
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneSimpleKotlinTypeUpperBoundIfFlexible;
            if (!Intrinsics.areEqual(coneClassLikeType.getLookupTag().getClassId(), StandardClassIds.INSTANCE.getCollection())) {
                throw new IllegalArgumentException(("Unexpected type: " + coneClassLikeType.getLookupTag().getClassId()).toString());
            }
            return ArraysKt.singleOrNull(coneSimpleKotlinTypeUpperBoundIfFlexible.getTypeArguments()) instanceof ConeStarProjection;
        }
        return Intrinsics.areEqual(ConeTypeUtilsKt.getClassId(coneSimpleKotlinTypeUpperBoundIfFlexible), StandardClassIds.INSTANCE.getAny());
    }

    private final String computeJvmDescriptorForGetter(FirProperty firProperty, String str, boolean z) {
        String strComputeJvmDescriptor;
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter != null && (strComputeJvmDescriptor = computeJvmDescriptor(getter, str, z)) != null) {
            return strComputeJvmDescriptor;
        }
        FirModuleData moduleData = firProperty.getModuleData();
        FirDeclarationOrigin origin = firProperty.getOrigin();
        FirTypeRef returnTypeRef = firProperty.getReturnTypeRef();
        Visibility visibility = firProperty.getStatus().getVisibility();
        FirPropertySymbol symbol = firProperty.getSymbol();
        Modality modality = firProperty.getStatus().getModality();
        if (modality == null) {
            modality = Modality.FINAL;
        }
        return computeJvmDescriptor(new FirDefaultPropertyGetter(null, moduleData, origin, returnTypeRef, visibility, symbol, modality, null, null, null, 896, null), str, z);
    }

    private final String computeJvmDescriptorForSetter(FirProperty firProperty, String str, boolean z) {
        String strComputeJvmDescriptor;
        FirPropertyAccessor setter = firProperty.getSetter();
        if (setter != null && (strComputeJvmDescriptor = computeJvmDescriptor(setter, str, z)) != null) {
            return strComputeJvmDescriptor;
        }
        FirModuleData moduleData = firProperty.getModuleData();
        FirDeclarationOrigin origin = firProperty.getOrigin();
        FirTypeRef returnTypeRef = firProperty.getReturnTypeRef();
        Visibility visibility = firProperty.getStatus().getVisibility();
        FirPropertySymbol symbol = firProperty.getSymbol();
        Modality modality = firProperty.getStatus().getModality();
        if (modality == null) {
            modality = Modality.FINAL;
        }
        return computeJvmDescriptor(new FirDefaultPropertySetter(null, moduleData, origin, returnTypeRef, visibility, symbol, modality, null, null, null, null, 1920, null), str, z);
    }

    static /* synthetic */ String computeJvmDescriptor$default(JavaClassUseSiteMemberScope javaClassUseSiteMemberScope, FirFunction firFunction, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        if ((i & 2) != 0) {
            z = false;
        }
        return javaClassUseSiteMemberScope.computeJvmDescriptor(firFunction, str, z);
    }

    private final String computeJvmDescriptor(FirFunction firFunction, String str, boolean z) {
        return SignatureUtilsKt.computeJvmDescriptor(firFunction, str, z, new Function1<FirTypeRef, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope.computeJvmDescriptor.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(FirTypeRef it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return JavaTypeConversionKt.toConeKotlinTypeProbablyFlexible$default(it, JavaClassUseSiteMemberScope.this.getSession(), JavaClassUseSiteMemberScope.this.typeParameterStack, null, 4, null);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ boolean hasKotlinSuper$default(JavaClassUseSiteMemberScope javaClassUseSiteMemberScope, FirRegularClass firRegularClass, FirSession firSession, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new LinkedHashSet();
        }
        return javaClassUseSiteMemberScope.hasKotlinSuper(firRegularClass, firSession, set);
    }

    private final boolean hasKotlinSuper(FirRegularClass firRegularClass, FirSession firSession, Set<FirRegularClass> set) {
        if (!set.add(firRegularClass)) {
            return false;
        }
        if (firRegularClass instanceof FirJavaClass) {
            List<ConeClassLikeType> superConeTypes = FirDeclarationUtilKt.getSuperConeTypes(firRegularClass);
            if ((superConeTypes instanceof Collection) && superConeTypes.isEmpty()) {
                return false;
            }
            Iterator<T> it = superConeTypes.iterator();
            while (it.hasNext()) {
                FirRegularClass fir = toFir((ConeClassLikeType) it.next(), firSession);
                if (fir == null || !hasKotlinSuper(fir, firSession, set)) {
                }
            }
            return false;
        }
        if (firRegularClass.getClassKind() == ClassKind.INTERFACE || Intrinsics.areEqual(firRegularClass.getOrigin(), FirDeclarationOrigin.BuiltIns.INSTANCE)) {
            return false;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirRegularClass toFir(ConeClassLikeType coneClassLikeType, FirSession firSession) {
        FirClassLikeSymbol<?> symbol = TypeUtilsKt.toSymbol(coneClassLikeType, firSession);
        if (symbol instanceof FirRegularClassSymbol) {
            return (FirRegularClass) ((FirRegularClassSymbol) symbol).getFir();
        }
        return null;
    }

    public String toString() {
        return "Java use site scope of " + getClassId();
    }
}
