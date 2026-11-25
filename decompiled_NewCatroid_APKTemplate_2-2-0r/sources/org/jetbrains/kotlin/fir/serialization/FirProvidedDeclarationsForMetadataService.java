package org.jetbrains.kotlin.fir.serialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationsForMetadataProviderExtension;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationsForMetadataProviderExtensionKt;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00122\u00020\u0001:\u0002\u0012\u0013B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH&J\"\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH&J&\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH&J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH&¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "getProvidedCallables", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getProvidedConstructors", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "getProvidedNestedClassifiers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getProvidedTopLevelDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "Companion", "Empty", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirProvidedDeclarationsForMetadataService implements FirSessionComponent {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public abstract List<FirCallableDeclaration> getProvidedCallables(FirClassSymbol<?> owner, ScopeSession scopeSession);

    public abstract List<FirConstructor> getProvidedConstructors(FirClassSymbol<?> owner, ScopeSession scopeSession);

    public abstract List<FirClassLikeSymbol<?>> getProvidedNestedClassifiers(FirClassSymbol<?> owner, ScopeSession scopeSession);

    public abstract List<FirDeclaration> getProvidedTopLevelDeclarations(FqName packageFqName, ScopeSession scopeSession);

    /* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FirProvidedDeclarationsForMetadataService create(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            List<FirDeclarationsForMetadataProviderExtension> declarationForMetadataProviders = FirDeclarationsForMetadataProviderExtensionKt.getDeclarationForMetadataProviders(FirExtensionServiceKt.getExtensionService(session));
            return declarationForMetadataProviders.isEmpty() ? Empty.INSTANCE : new FirProvidedDeclarationsForMetadataServiceImpl(session, declarationForMetadataProviders);
        }
    }

    /* JADX INFO: compiled from: FirProvidedDeclarationsForMetadataService.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\"\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J&\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\u00042\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService$Empty;", "Lorg/jetbrains/kotlin/fir/serialization/FirProvidedDeclarationsForMetadataService;", "()V", "getProvidedCallables", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getProvidedConstructors", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "getProvidedNestedClassifiers", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getProvidedTopLevelDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Empty extends FirProvidedDeclarationsForMetadataService {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
        public List<FirDeclaration> getProvidedTopLevelDeclarations(FqName packageFqName, ScopeSession scopeSession) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
        public List<FirConstructor> getProvidedConstructors(FirClassSymbol<?> owner, ScopeSession scopeSession) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
        public List<FirCallableDeclaration> getProvidedCallables(FirClassSymbol<?> owner, ScopeSession scopeSession) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.serialization.FirProvidedDeclarationsForMetadataService
        public List<FirClassLikeSymbol<?>> getProvidedNestedClassifiers(FirClassSymbol<?> owner, ScopeSession scopeSession) {
            Intrinsics.checkNotNullParameter(owner, "owner");
            Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
            return CollectionsKt.emptyList();
        }
    }
}
