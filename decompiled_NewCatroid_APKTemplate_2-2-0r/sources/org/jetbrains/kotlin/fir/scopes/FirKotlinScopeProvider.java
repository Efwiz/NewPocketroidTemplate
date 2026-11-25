package org.jetbrains.kotlin.fir.scopes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirClassAnySynthesizedMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirClassUseSiteMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDelegatedMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNameAwareOnlyCallablesScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirStaticScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;

/* JADX INFO: compiled from: FirKotlinScopeProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0080\u0001\u0012y\b\u0002\u0010\u0003\u001as\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\u0002\u0010\u0011J\"\u0010\u0014\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0016J\"\u0010\u0015\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0016J*\u0010\u0016\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016R\u0082\u0001\u0010\u0003\u001as\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\u000b¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u000e\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirKotlinScopeProvider;", "Lorg/jetbrains/kotlin/fir/scopes/FirScopeProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "declaredMemberScopeDecorator", "Lkotlin/Function5;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lkotlin/ParameterName;", "name", "klass", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "declaredMemberScope", "Lorg/jetbrains/kotlin/fir/FirSession;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "memberRequiredPhase", "(Lkotlin/jvm/functions/Function5;)V", "getDeclaredMemberScopeDecorator", "()Lkotlin/jvm/functions/Function5;", "getNestedClassifierScope", "getStaticMemberScopeForCallables", "getUseSiteMemberScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirKotlinScopeProvider extends FirScopeProvider implements FirSessionComponent {
    private final Function5<FirClass, FirContainingNamesAwareScope, FirSession, ScopeSession, FirResolvePhase, FirContainingNamesAwareScope> declaredMemberScopeDecorator;

    /* JADX INFO: compiled from: FirKotlinScopeProvider.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirKotlinScopeProvider() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final Function5<FirClass, FirContainingNamesAwareScope, FirSession, ScopeSession, FirResolvePhase, FirContainingNamesAwareScope> getDeclaredMemberScopeDecorator() {
        return this.declaredMemberScopeDecorator;
    }

    public /* synthetic */ FirKotlinScopeProvider(AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function5<FirClass, FirContainingNamesAwareScope, FirSession, ScopeSession, FirResolvePhase, FirContainingNamesAwareScope>() { // from class: org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProvider.1
            @Override // kotlin.jvm.functions.Function5
            public final FirContainingNamesAwareScope invoke(FirClass firClass, FirContainingNamesAwareScope declaredMemberScope, FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
                Intrinsics.checkNotNullParameter(firClass, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(declaredMemberScope, "declaredMemberScope");
                Intrinsics.checkNotNullParameter(firSession, "<anonymous parameter 2>");
                Intrinsics.checkNotNullParameter(scopeSession, "<anonymous parameter 3>");
                return declaredMemberScope;
            }
        } : anonymousClass1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirKotlinScopeProvider(Function5<? super FirClass, ? super FirContainingNamesAwareScope, ? super FirSession, ? super ScopeSession, ? super FirResolvePhase, ? extends FirContainingNamesAwareScope> declaredMemberScopeDecorator) {
        Intrinsics.checkNotNullParameter(declaredMemberScopeDecorator, "declaredMemberScopeDecorator");
        this.declaredMemberScopeDecorator = declaredMemberScopeDecorator;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirTypeScope getUseSiteMemberScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession, FirResolvePhase memberRequiredPhase) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (memberRequiredPhase != null) {
            FirLazyDeclarationResolverKt.lazyResolveToPhaseWithCallableMembers(klass, memberRequiredPhase);
        }
        FirClassSymbol<? extends FirClass> symbol = klass.getSymbol();
        ScopeSessionKey<FirClassSymbol<?>, FirTypeScope> use_site = SupertypeUtilsKt.getUSE_SITE();
        HashMap<Object, HashMap<ScopeSessionKey<?, ?>, Object>> mapScopes = scopeSession.scopes();
        HashMap<ScopeSessionKey<?, ?>, Object> map = mapScopes.get(symbol);
        if (map == null) {
            map = new HashMap<>();
            mapScopes.put(symbol, map);
        }
        HashMap<ScopeSessionKey<?, ?>, Object> map2 = map;
        FirClassUseSiteMemberScope firClassUseSiteMemberScope = map2.get(use_site);
        if (firClassUseSiteMemberScope == null) {
            FirClassAnySynthesizedMemberScope firClassAnySynthesizedMemberScopeInvoke = this.declaredMemberScopeDecorator.invoke(klass, FirDeclaredMemberScopeProviderKt.declaredMemberScope(useSiteSession, klass, memberRequiredPhase), useSiteSession, scopeSession, memberRequiredPhase);
            List<FirField> delegateFields = FirDeclarationUtilKt.getDelegateFields(klass);
            if (!delegateFields.isEmpty()) {
                firClassAnySynthesizedMemberScopeInvoke = new FirDelegatedMemberScope(useSiteSession, scopeSession, klass, firClassAnySynthesizedMemberScopeInvoke, delegateFields);
            }
            if (klass instanceof FirRegularClass) {
                FirClass firClass = klass;
                if (!firClass.getStatus().isExpect()) {
                    FirRegularClass firRegularClass = (FirRegularClass) klass;
                    if (firRegularClass.getStatus().isData() || firClass.getStatus().isInline()) {
                        firClassAnySynthesizedMemberScopeInvoke = new FirClassAnySynthesizedMemberScope(useSiteSession, firClassAnySynthesizedMemberScopeInvoke, firRegularClass, scopeSession);
                    }
                }
            }
            FirContainingNamesAwareScope firContainingNamesAwareScope = firClassAnySynthesizedMemberScopeInvoke;
            List listLookupSuperTypes$default = SupertypeUtilsKt.lookupSuperTypes$default(klass, true, false, useSiteSession, true, (SupertypeSupplier) null, 32, (Object) null);
            ArrayList arrayList = new ArrayList();
            Iterator it = listLookupSuperTypes$default.iterator();
            while (it.hasNext()) {
                FirTypeScope firTypeScopeScopeForSupertype = FirKotlinScopeProviderKt.scopeForSupertype((ConeClassLikeType) it.next(), useSiteSession, scopeSession, klass, memberRequiredPhase);
                if (firTypeScopeScopeForSupertype != null) {
                    arrayList.add(firTypeScopeScopeForSupertype);
                }
            }
            firClassUseSiteMemberScope = new FirClassUseSiteMemberScope(klass, useSiteSession, arrayList, firContainingNamesAwareScope);
            map2.put(use_site, firClassUseSiteMemberScope);
        }
        return (FirTypeScope) firClassUseSiteMemberScope;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirContainingNamesAwareScope getStaticMemberScopeForCallables(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (WhenMappings.$EnumSwitchMapping$0[klass.getClassKind().ordinal()] == 1) {
            return new FirNameAwareOnlyCallablesScope(new FirStaticScope(FirDeclaredMemberScopeProviderKt.declaredMemberScope(useSiteSession, klass, (FirResolvePhase) null)));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScopeProvider
    public FirContainingNamesAwareScope getNestedClassifierScope(FirClass klass, FirSession useSiteSession, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return FirDeclaredMemberScopeProviderKt.nestedClassifierScope(useSiteSession, klass);
    }
}
