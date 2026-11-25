package org.jetbrains.kotlin.fir.extensions;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderInternals;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSwitchableExtensionDeclarationsSymbolProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000  2\u00020\u0001:\u0001 B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\fH\u0007J\u0016\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J*\u0010\u0015\u001a\u00020\f2\u0010\u0010\u0016\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u00172\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0017J&\u0010\u001c\u001a\u00020\f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00172\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0017J&\u0010\u001e\u001a\u00020\f2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00172\u0006\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSwitchableExtensionDeclarationsSymbolProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionDeclarationsSymbolProvider;", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionDeclarationsSymbolProvider;)V", CompilerOptions.DISABLED, "", "symbolNamesProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "getSymbolNamesProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolNamesProvider;", "disable", "", CommonCompilerArguments.ENABLE, "getClassLikeSymbolByClassId", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPackage", "Lorg/jetbrains/kotlin/name/FqName;", "fqName", "getTopLevelCallableSymbolsTo", FirebaseAnalytics.Param.DESTINATION, "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "packageFqName", "name", "Lorg/jetbrains/kotlin/name/Name;", "getTopLevelFunctionSymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getTopLevelPropertySymbolsTo", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Companion", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSwitchableExtensionDeclarationsSymbolProvider extends FirSymbolProvider {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final FirExtensionDeclarationsSymbolProvider delegate;
    private boolean disabled;
    private final FirSymbolNamesProvider symbolNamesProvider;

    public /* synthetic */ FirSwitchableExtensionDeclarationsSymbolProvider(FirExtensionDeclarationsSymbolProvider firExtensionDeclarationsSymbolProvider, DefaultConstructorMarker defaultConstructorMarker) {
        this(firExtensionDeclarationsSymbolProvider);
    }

    /* JADX INFO: compiled from: FirSwitchableExtensionDeclarationsSymbolProvider.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirSwitchableExtensionDeclarationsSymbolProvider$Companion;", "", "()V", "createIfNeeded", "Lorg/jetbrains/kotlin/fir/extensions/FirSwitchableExtensionDeclarationsSymbolProvider;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirSwitchableExtensionDeclarationsSymbolProvider createIfNeeded(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            FirExtensionDeclarationsSymbolProvider firExtensionDeclarationsSymbolProviderCreateIfNeeded = FirExtensionDeclarationsSymbolProvider.INSTANCE.createIfNeeded(session);
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (firExtensionDeclarationsSymbolProviderCreateIfNeeded != null) {
                return new FirSwitchableExtensionDeclarationsSymbolProvider(firExtensionDeclarationsSymbolProviderCreateIfNeeded, defaultConstructorMarker);
            }
            return null;
        }
    }

    private FirSwitchableExtensionDeclarationsSymbolProvider(FirExtensionDeclarationsSymbolProvider firExtensionDeclarationsSymbolProvider) {
        super(firExtensionDeclarationsSymbolProvider.getSession());
        this.delegate = firExtensionDeclarationsSymbolProvider;
        this.symbolNamesProvider = new FirSymbolNamesProvider() { // from class: org.jetbrains.kotlin.fir.extensions.FirSwitchableExtensionDeclarationsSymbolProvider$symbolNamesProvider$1
            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getPackageNamesWithTopLevelCallables() {
                if (this.this$0.disabled) {
                    return null;
                }
                return this.this$0.delegate.getSymbolNamesProvider().getPackageNamesWithTopLevelCallables();
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<String> getTopLevelClassifierNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                if (this.this$0.disabled) {
                    return null;
                }
                return this.this$0.delegate.getSymbolNamesProvider().getTopLevelClassifierNamesInPackage(packageFqName);
            }

            @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolNamesProvider
            public Set<Name> getTopLevelCallableNamesInPackage(FqName packageFqName) {
                Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
                if (this.this$0.disabled) {
                    return null;
                }
                return this.this$0.delegate.getSymbolNamesProvider().getTopLevelCallableNamesInPackage(packageFqName);
            }
        };
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirSymbolNamesProvider getSymbolNamesProvider() {
        return this.symbolNamesProvider;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FirClassLikeSymbol<?> getClassLikeSymbolByClassId(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        if (this.disabled) {
            return null;
        }
        return this.delegate.getClassLikeSymbolByClassId(classId);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelCallableSymbolsTo(List<FirCallableSymbol<?>> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.disabled) {
            return;
        }
        this.delegate.getTopLevelCallableSymbolsTo(destination, packageFqName, name2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelFunctionSymbolsTo(List<FirNamedFunctionSymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.disabled) {
            return;
        }
        this.delegate.getTopLevelFunctionSymbolsTo(destination, packageFqName, name2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    @FirSymbolProviderInternals
    public void getTopLevelPropertySymbolsTo(List<FirPropertySymbol> destination, FqName packageFqName, Name name2) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.disabled) {
            return;
        }
        this.delegate.getTopLevelPropertySymbolsTo(destination, packageFqName, name2);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
    public FqName getPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (this.disabled) {
            return null;
        }
        return this.delegate.getPackage(fqName);
    }

    @FirSymbolProviderInternals
    public final void disable() {
        this.disabled = true;
    }

    @FirSymbolProviderInternals
    public final void enable() {
        this.disabled = false;
    }
}
