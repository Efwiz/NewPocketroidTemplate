package org.jetbrains.kotlin.fir.scopes.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AbstractFirUseSiteMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u001e\u00108\u001a\u0002092\u0006\u0010:\u001a\u00020\u00122\f\u0010;\u001a\b\u0012\u0004\u0012\u00020 0<H\u0004J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020 0+2\u0006\u0010:\u001a\u00020\u0012H\u0014J,\u0010>\u001a\u0002092\u0006\u0010:\u001a\u00020\u00122\f\u0010;\u001a\b\u0012\u0004\u0012\u00020 0<2\f\u0010?\u001a\b\u0012\u0004\u0012\u00020 0\u0011H\u0004J\u001a\u0010@\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030/0+2\u0006\u0010:\u001a\u00020\u0012H$J\u001c\u0010A\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\t2\u0006\u0010B\u001a\u00020 H\u0002J\u000e\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\u000e\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016J\u001c\u0010E\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\t2\u0006\u0010:\u001a\u00020\u0012H\u0002J.\u0010F\u001a\u0002092\u0006\u0010:\u001a\u00020\u00122\u001c\u0010G\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030I\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u0002090HH\u0016J\u001c\u0010K\u001a\u0002092\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u0002090LH\u0016J*\u0010N\u001a\u00020O2\u0006\u0010P\u001a\u00020 2\u0018\u0010G\u001a\u0014\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020O0HH\u0016J´\u0001\u0010Q\u001a\u00020O\"\f\b\u0000\u0010R*\u0006\u0012\u0002\b\u00030S2\u001e\u0010T\u001a\u001a\u0012\u0004\u0012\u0002HR\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HR0!0\t0U2\u001e\u0010V\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HR0!0\t0U2\u0006\u0010W\u001a\u0002HR2\u0018\u0010G\u001a\u0014\u0012\u0004\u0012\u0002HR\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020O0H25\u0010X\u001a1\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u0002HR\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002HR\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020O0H\u0012\u0004\u0012\u00020O0Y¢\u0006\u0002\bZH\u0002¢\u0006\u0002\u0010[J*\u0010\\\u001a\u00020O2\u0006\u0010]\u001a\u00020%2\u0018\u0010G\u001a\u0014\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020O0HH\u0016J\"\u0010^\u001a\u0002092\u0006\u0010:\u001a\u00020\u00122\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u0002090LJ&\u0010_\u001a\u0002092\u0006\u0010:\u001a\u00020\u00122\u0016\u0010G\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030/\u0012\u0004\u0012\u0002090LJ%\u0010`\u001a\u0002HR\"\f\b\u0000\u0010R*\u0006\u0012\u0002\b\u00030S*\b\u0012\u0004\u0012\u0002HR0!H\u0004¢\u0006\u0002\u0010aJ\f\u0010b\u001a\u00020c*\u00020 H$R!\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R!\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0016\u001a\u0004\b\u001a\u0010\u0014R\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR,\u0010\u001e\u001a\u001a\u0012\u0004\u0012\u00020 \u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\t0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R,\u0010$\u001a\u001a\u0012\u0004\u0012\u00020%\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0!0\t0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010#R&\u0010'\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\t0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010#R \u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0+0\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010,\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0!0\t0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010#R$\u0010.\u001a\u0018\u0012\u0004\u0012\u00020\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030/0+0\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R,\u00100\u001a\u001a\u0012\u0004\u0012\u00020\u0012\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0!0\t0\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010#R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0014\u00104\u001a\u000205X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirOverrideScope;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "superTypeScopes", "", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "dispatchReceiverType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;)V", "callableNamesCached", "", "Lorg/jetbrains/kotlin/name/Name;", "getCallableNamesCached", "()Ljava/util/Set;", "callableNamesCached$delegate", "Lkotlin/Lazy;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "classifierNamesCached", "getClassifierNamesCached", "classifierNamesCached$delegate", "getDeclaredMemberScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "directOverriddenFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "getDirectOverriddenFunctions", "()Ljava/util/Map;", "directOverriddenProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "getDirectOverriddenProperties", "fieldsFromSupertypes", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFieldSymbol;", "getFieldsFromSupertypes", "functions", "", "functionsFromSupertypes", "getFunctionsFromSupertypes", "properties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "propertiesFromSupertypes", "getPropertiesFromSupertypes", "getSuperTypeScopes", "()Ljava/util/List;", "supertypeScopeContext", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "getSupertypeScopeContext", "()Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext;", "collectDeclaredFunctions", "", "name", FirebaseAnalytics.Param.DESTINATION, "", "collectFunctions", "collectFunctionsFromSupertypes", "explicitlyDeclaredFunctions", "collectProperties", "computeDirectOverriddenForDeclaredFunction", "declaredFunctionSymbol", "getCallableNames", "getClassifierNames", "getFunctionsFromSupertypesByName", "processClassifiersByNameWithSubstitution", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "processDirectOverriddenMembersWithBaseScopeImpl", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "directOverriddenMap", "", "callablesFromSupertypes", "callableSymbol", "processDirectOverriddenCallables", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "(Ljava/util/Map;Ljava/util/Map;Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "processFunctionsByName", "processPropertiesByName", "extractSomeSymbolFromSuperType", "(Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "isVisibleInCurrentClass", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirUseSiteMemberScope extends AbstractFirOverrideScope {

    /* JADX INFO: renamed from: callableNamesCached$delegate, reason: from kotlin metadata */
    private final Lazy callableNamesCached;
    private final ClassId classId;

    /* JADX INFO: renamed from: classifierNamesCached$delegate, reason: from kotlin metadata */
    private final Lazy classifierNamesCached;
    private final FirContainingNamesAwareScope declaredMemberScope;
    private final Map<FirNamedFunctionSymbol, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>>> directOverriddenFunctions;
    private final Map<FirPropertySymbol, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> directOverriddenProperties;
    private final Map<Name, List<FirFieldSymbol>> fieldsFromSupertypes;
    private final Map<Name, Collection<FirNamedFunctionSymbol>> functions;
    private final Map<Name, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>>> functionsFromSupertypes;
    private final Map<Name, Collection<FirVariableSymbol<?>>> properties;
    private final Map<Name, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> propertiesFromSupertypes;
    private final List<FirTypeScope> superTypeScopes;
    private final FirTypeIntersectionScopeContext supertypeScopeContext;

    protected abstract Collection<FirVariableSymbol<?>> collectProperties(Name name2);

    protected abstract boolean isVisibleInCurrentClass(FirNamedFunctionSymbol firNamedFunctionSymbol);

    public final ClassId getClassId() {
        return this.classId;
    }

    protected final List<FirTypeScope> getSuperTypeScopes() {
        return this.superTypeScopes;
    }

    protected final FirContainingNamesAwareScope getDeclaredMemberScope() {
        return this.declaredMemberScope;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AbstractFirUseSiteMemberScope(ClassId classId, FirSession session, FirOverrideChecker overrideChecker, List<? extends FirTypeScope> superTypeScopes, ConeSimpleKotlinType dispatchReceiverType, FirContainingNamesAwareScope declaredMemberScope) {
        super(session, overrideChecker);
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(overrideChecker, "overrideChecker");
        Intrinsics.checkNotNullParameter(superTypeScopes, "superTypeScopes");
        Intrinsics.checkNotNullParameter(dispatchReceiverType, "dispatchReceiverType");
        Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
        this.classId = classId;
        this.superTypeScopes = superTypeScopes;
        this.declaredMemberScope = declaredMemberScope;
        this.supertypeScopeContext = new FirTypeIntersectionScopeContext(session, overrideChecker, superTypeScopes, dispatchReceiverType, true);
        this.functions = new HashMap();
        this.properties = new HashMap();
        this.directOverriddenFunctions = new HashMap();
        this.directOverriddenProperties = new HashMap();
        this.functionsFromSupertypes = new LinkedHashMap();
        this.propertiesFromSupertypes = new LinkedHashMap();
        this.fieldsFromSupertypes = new LinkedHashMap();
        this.callableNamesCached = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope$callableNamesCached$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                AbstractFirUseSiteMemberScope abstractFirUseSiteMemberScope = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                setCreateSetBuilder.addAll(abstractFirUseSiteMemberScope.getDeclaredMemberScope().getCallableNames());
                Iterator<T> it = abstractFirUseSiteMemberScope.getSuperTypeScopes().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(setCreateSetBuilder, ((FirTypeScope) it.next()).getCallableNames());
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
        this.classifierNamesCached = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Set<? extends Name>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope$classifierNamesCached$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<? extends Name> invoke() {
                AbstractFirUseSiteMemberScope abstractFirUseSiteMemberScope = this.this$0;
                Set setCreateSetBuilder = SetsKt.createSetBuilder();
                setCreateSetBuilder.addAll(abstractFirUseSiteMemberScope.getDeclaredMemberScope().getClassifierNames());
                Iterator<T> it = abstractFirUseSiteMemberScope.getSuperTypeScopes().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(setCreateSetBuilder, ((FirTypeScope) it.next()).getClassifierNames());
                }
                return SetsKt.build(setCreateSetBuilder);
            }
        });
    }

    protected final FirTypeIntersectionScopeContext getSupertypeScopeContext() {
        return this.supertypeScopeContext;
    }

    protected final Map<FirNamedFunctionSymbol, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>>> getDirectOverriddenFunctions() {
        return this.directOverriddenFunctions;
    }

    protected final Map<FirPropertySymbol, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> getDirectOverriddenProperties() {
        return this.directOverriddenProperties;
    }

    protected final Map<Name, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>>> getFunctionsFromSupertypes() {
        return this.functionsFromSupertypes;
    }

    protected final Map<Name, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> getPropertiesFromSupertypes() {
        return this.propertiesFromSupertypes;
    }

    protected final Map<Name, List<FirFieldSymbol>> getFieldsFromSupertypes() {
        return this.fieldsFromSupertypes;
    }

    private final Set<Name> getCallableNamesCached() {
        return (Set) this.callableNamesCached.getValue();
    }

    private final Set<Name> getClassifierNamesCached() {
        return (Set) this.classifierNamesCached.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public final void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            Map<Name, Collection<FirNamedFunctionSymbol>> map = this.functions;
            Collection<FirNamedFunctionSymbol> collectionCollectFunctions = map.get(name2);
            if (collectionCollectFunctions == null) {
                collectionCollectFunctions = collectFunctions(name2);
                map.put(name2, collectionCollectFunctions);
            }
            Iterator<T> it = collectionCollectFunctions.iterator();
            while (it.hasNext()) {
                processor.invoke((FirNamedFunctionSymbol) it.next());
            }
        }
    }

    protected Collection<FirNamedFunctionSymbol> collectFunctions(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        ArrayList arrayList = new ArrayList();
        collectDeclaredFunctions(name2, arrayList);
        collectFunctionsFromSupertypes(name2, arrayList, CollectionsKt.toSet(arrayList));
        return arrayList;
    }

    protected final void collectDeclaredFunctions(Name name2, final List<FirNamedFunctionSymbol> destination) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(destination, "destination");
        this.declaredMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope.collectDeclaredFunctions.1
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
            public final void invoke2(FirNamedFunctionSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                if (!symbol.getRawStatus().isStatic() && AbstractFirUseSiteMemberScope.this.isVisibleInCurrentClass(symbol)) {
                    AbstractFirUseSiteMemberScope.this.getDirectOverriddenFunctions().put(symbol, AbstractFirUseSiteMemberScope.this.computeDirectOverriddenForDeclaredFunction(symbol));
                    destination.add(symbol);
                }
            }
        });
    }

    protected final void collectFunctionsFromSupertypes(Name name2, List<FirNamedFunctionSymbol> destination, Set<? extends FirNamedFunctionSymbol> explicitlyDeclaredFunctions) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(explicitlyDeclaredFunctions, "explicitlyDeclaredFunctions");
        for (FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol> resultOfIntersection : getFunctionsFromSupertypesByName(name2)) {
            FirNamedFunctionSymbol firNamedFunctionSymbol = (FirNamedFunctionSymbol) extractSomeSymbolFromSuperType(resultOfIntersection);
            if (isVisibleInCurrentClass(firNamedFunctionSymbol) && getOverridden(firNamedFunctionSymbol, explicitlyDeclaredFunctions) == null) {
                destination.add(resultOfIntersection.getChosenSymbol());
            }
        }
    }

    private final List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> getFunctionsFromSupertypesByName(Name name2) {
        Map<Name, List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>>> map = this.functionsFromSupertypes;
        List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> listConvertGroupedCallablesToIntersectionResults = map.get(name2);
        if (listConvertGroupedCallablesToIntersectionResults == null) {
            FirTypeIntersectionScopeContext firTypeIntersectionScopeContext = this.supertypeScopeContext;
            List<FirTypeScope> scopes = firTypeIntersectionScopeContext.getScopes();
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
            listConvertGroupedCallablesToIntersectionResults = firTypeIntersectionScopeContext.convertGroupedCallablesToIntersectionResults(arrayList);
            map.put(name2, listConvertGroupedCallablesToIntersectionResults);
        }
        return listConvertGroupedCallablesToIntersectionResults;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public final void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            Map<Name, Collection<FirVariableSymbol<?>>> map = this.properties;
            Collection<FirVariableSymbol<?>> collectionCollectProperties = map.get(name2);
            if (collectionCollectProperties == null) {
                collectionCollectProperties = collectProperties(name2);
                map.put(name2, collectionCollectProperties);
            }
            Iterator<T> it = collectionCollectProperties.iterator();
            while (it.hasNext()) {
                processor.invoke((FirVariableSymbol) it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol>> computeDirectOverriddenForDeclaredFunction(FirNamedFunctionSymbol declaredFunctionSymbol) {
        ArrayList arrayList = new ArrayList();
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) declaredFunctionSymbol.getFir();
        for (FirTypeIntersectionScopeContext.ResultOfIntersection<FirNamedFunctionSymbol> resultOfIntersection : getFunctionsFromSupertypesByName(declaredFunctionSymbol.getName())) {
            if (getOverrideChecker().isOverriddenFunction(firSimpleFunction, (FirSimpleFunction) ((FirNamedFunctionSymbol) extractSomeSymbolFromSuperType(resultOfIntersection)).getFir())) {
                arrayList.add(resultOfIntersection);
            }
        }
        return arrayList;
    }

    protected final <D extends FirCallableSymbol<?>> D extractSomeSymbolFromSuperType(FirTypeIntersectionScopeContext.ResultOfIntersection<D> resultOfIntersection) {
        Intrinsics.checkNotNullParameter(resultOfIntersection, "<this>");
        if (FirTypeIntersectionScopeContextKt.isIntersectionOverride(resultOfIntersection)) {
            return (D) ((FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial) resultOfIntersection).getKeySymbol();
        }
        return (D) resultOfIntersection.getChosenSymbol();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return processDirectOverriddenMembersWithBaseScopeImpl(this.directOverriddenFunctions, this.functionsFromSupertypes, functionSymbol, processor, C45981.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope$processDirectOverriddenFunctionsWithBaseScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractFirUseSiteMemberScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45981 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45981 INSTANCE = new C45981();

        C45981() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenFunctionsWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenFunctionsWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirNamedFunctionSymbol p1, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenFunctionsWithBaseScope(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return processDirectOverriddenMembersWithBaseScopeImpl(this.directOverriddenProperties, this.propertiesFromSupertypes, propertySymbol, processor, C45991.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope$processDirectOverriddenPropertiesWithBaseScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: AbstractFirUseSiteMemberScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C45991 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C45991 INSTANCE = new C45991();

        C45991() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenPropertiesWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenPropertiesWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenPropertiesWithBaseScope(p1, p2);
        }
    }

    private final <D extends FirCallableSymbol<?>> ProcessorAction processDirectOverriddenMembersWithBaseScopeImpl(Map<D, ? extends List<? extends FirTypeIntersectionScopeContext.ResultOfIntersection<D>>> directOverriddenMap, Map<Name, ? extends List<? extends FirTypeIntersectionScopeContext.ResultOfIntersection<D>>> callablesFromSupertypes, D callableSymbol, Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction> processor, Function3<? super FirTypeScope, ? super D, ? super Function2<? super D, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverriddenCallables) {
        Object next;
        ProcessorAction processorActionInvoke;
        List<? extends FirTypeIntersectionScopeContext.ResultOfIntersection<D>> list = directOverriddenMap.get(callableSymbol);
        if (list == null) {
            List<? extends FirTypeIntersectionScopeContext.ResultOfIntersection<D>> list2 = callablesFromSupertypes.get(callableSymbol.getName());
            if (list2 != null) {
                Iterator<T> it = list2.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!Intrinsics.areEqual(((FirTypeIntersectionScopeContext.ResultOfIntersection) next).getChosenSymbol(), callableSymbol));
                FirTypeIntersectionScopeContext.ResultOfIntersection resultOfIntersection = (FirTypeIntersectionScopeContext.ResultOfIntersection) next;
                if (resultOfIntersection != null) {
                    if (FirTypeIntersectionScopeContextKt.isIntersectionOverride(resultOfIntersection)) {
                        for (MemberWithBaseScope<D> memberWithBaseScope : resultOfIntersection.getOverriddenMembers()) {
                            if (processor.invoke(memberWithBaseScope.component1(), memberWithBaseScope.getBaseScope()).not()) {
                                return ProcessorAction.STOP;
                            }
                        }
                        return ProcessorAction.NONE;
                    }
                    FirTypeScope containingScope = resultOfIntersection.getContainingScope();
                    return (containingScope == null || (processorActionInvoke = processDirectOverriddenCallables.invoke(containingScope, callableSymbol, processor)) == null) ? ProcessorAction.NONE : processorActionInvoke;
                }
            }
            return ProcessorAction.NONE;
        }
        Iterator<? extends FirTypeIntersectionScopeContext.ResultOfIntersection<D>> it2 = list.iterator();
        while (it2.hasNext()) {
            for (MemberWithBaseScope<D> memberWithBaseScope2 : it2.next().getOverriddenMembers()) {
                if (processor.invoke(memberWithBaseScope2.component1(), memberWithBaseScope2.getBaseScope()).not()) {
                    return ProcessorAction.STOP;
                }
            }
        }
        return ProcessorAction.NONE;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, final Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getClassifierNames().contains(name2)) {
            final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
            this.declaredMemberScope.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.AbstractFirUseSiteMemberScope.processClassifiersByNameWithSubstitution.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> classifier, ConeSubstitutor substitutor) {
                    Intrinsics.checkNotNullParameter(classifier, "classifier");
                    Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                    booleanRef.element = true;
                    processor.invoke(classifier, substitutor);
                }
            });
            if (booleanRef.element) {
                return;
            }
            this.supertypeScopeContext.processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.declaredMemberScope.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return getCallableNamesCached();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return getClassifierNamesCached();
    }
}
