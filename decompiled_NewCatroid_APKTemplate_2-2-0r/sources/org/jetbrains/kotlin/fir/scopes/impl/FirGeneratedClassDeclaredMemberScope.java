package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirGeneratedScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J.\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u001c\u0010\u0010\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0012\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u000e0\u0011H\u0016J\u001c\u0010\u0014\u001a\u00020\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u000e0\u0015H\u0016J$\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u000e0\u0015H\u0016J(\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u0016\u0010\u0010\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u000e0\u0015H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassDeclaredMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "storage", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$CallableStorage;", "nestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$CallableStorage;Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;)V", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirGeneratedClassDeclaredMemberScope extends FirClassDeclaredMemberScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirNestedClassifierScope nestedClassifierScope;
    private final FirGeneratedMemberDeclarationsStorage.CallableStorage storage;

    public /* synthetic */ FirGeneratedClassDeclaredMemberScope(ClassId classId, FirGeneratedMemberDeclarationsStorage.CallableStorage callableStorage, FirNestedClassifierScope firNestedClassifierScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(classId, callableStorage, firNestedClassifierScope);
    }

    /* JADX INFO: compiled from: FirGeneratedScopes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassDeclaredMemberScope$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassDeclaredMemberScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "regularDeclaredScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;", "scopeForGeneratedClass", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final FirGeneratedClassDeclaredMemberScope create(FirSession useSiteSession, FirClassSymbol<?> classSymbol, FirClassDeclaredMemberScope regularDeclaredScope, boolean scopeForGeneratedClass) {
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            FirGeneratedMemberDeclarationsStorage.CallableStorage callableStorage$providers = FirGeneratedScopesKt.getGeneratedDeclarationsStorage(classSymbol.getModuleData().getSession()).getCallableStorage$providers(classSymbol, regularDeclaredScope, scopeForGeneratedClass);
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (callableStorage$providers == null) {
                return null;
            }
            return new FirGeneratedClassDeclaredMemberScope(classSymbol.getClassId(), callableStorage$providers, scopeForGeneratedClass ? FirDeclaredMemberScopeProviderKt.nestedClassifierScope(useSiteSession, (FirClass) classSymbol.getFir()) : null, defaultConstructorMarker);
        }
    }

    private FirGeneratedClassDeclaredMemberScope(ClassId classId, FirGeneratedMemberDeclarationsStorage.CallableStorage callableStorage, FirNestedClassifierScope firNestedClassifierScope) {
        super(classId);
        this.storage = callableStorage;
        this.nestedClassifierScope = firNestedClassifierScope;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.storage.getAllCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        Set<Name> classifierNames;
        FirNestedClassifierScope firNestedClassifierScope = this.nestedClassifierScope;
        return (firNestedClassifierScope == null || (classifierNames = firNestedClassifierScope.getClassifierNames()) == null) ? SetsKt.emptySet() : classifierNames;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirNestedClassifierScope firNestedClassifierScope = this.nestedClassifierScope;
        if (firNestedClassifierScope != null) {
            firNestedClassifierScope.processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            Iterator it = ((List) this.storage.getFunctionCache().getValue(name2, null)).iterator();
            while (it.hasNext()) {
                processor.invoke((FirNamedFunctionSymbol) it.next());
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (getCallableNames().contains(name2)) {
            Iterator it = ((List) this.storage.getPropertyCache().getValue(name2, null)).iterator();
            while (it.hasNext()) {
                processor.invoke((FirPropertySymbol) it.next());
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<FirConstructorSymbol> it = this.storage.getConstructorCache().getValue().iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }
}
