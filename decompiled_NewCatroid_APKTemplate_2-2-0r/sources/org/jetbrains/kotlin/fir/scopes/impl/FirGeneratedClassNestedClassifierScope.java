package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirGeneratedScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassNestedClassifierScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "storage", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$ClassifierStorage;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedMemberDeclarationsStorage$ClassifierStorage;)V", "getClassifierNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getNestedClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "name", "isEmpty", "", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirGeneratedClassNestedClassifierScope extends FirNestedClassifierScope {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirGeneratedMemberDeclarationsStorage.ClassifierStorage storage;

    public /* synthetic */ FirGeneratedClassNestedClassifierScope(FirSession firSession, FirClass firClass, FirGeneratedMemberDeclarationsStorage.ClassifierStorage classifierStorage, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firClass, classifierStorage);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    public boolean isEmpty() {
        return false;
    }

    /* JADX INFO: compiled from: FirGeneratedScopes.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\b\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassNestedClassifierScope$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/scopes/impl/FirGeneratedClassNestedClassifierScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "regularNestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final FirGeneratedClassNestedClassifierScope create(FirSession useSiteSession, FirClassSymbol<?> classSymbol, FirNestedClassifierScope regularNestedClassifierScope) {
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            FirGeneratedMemberDeclarationsStorage.ClassifierStorage classifierStorage$providers = FirGeneratedScopesKt.getGeneratedDeclarationsStorage(classSymbol.getModuleData().getSession()).getClassifierStorage$providers(classSymbol, regularNestedClassifierScope);
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (classifierStorage$providers == null) {
                return null;
            }
            return new FirGeneratedClassNestedClassifierScope(useSiteSession, (FirClass) classSymbol.getFir(), classifierStorage$providers, defaultConstructorMarker);
        }
    }

    private FirGeneratedClassNestedClassifierScope(FirSession firSession, FirClass firClass, FirGeneratedMemberDeclarationsStorage.ClassifierStorage classifierStorage) {
        super(firClass, firSession);
        this.storage = classifierStorage;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope
    public FirRegularClassSymbol getNestedClassSymbol(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return (FirRegularClassSymbol) this.storage.getClassifiersCache().getValue(name2, null);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.storage.getAllClassifierNames();
    }
}
