package org.jetbrains.kotlin.fir.resolve.providers.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirLibrarySessionProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0016\u0010\f\u001a\u0004\u0018\u00010\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0013H\u0016J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\r2\u0006\u0010\n\u001a\u00020\u0013H\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\r0\u00172\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirLibrarySessionProvider;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "(Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;)V", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getClassNamesInPackage", "", "Lorg/jetbrains/kotlin/name/Name;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFirCallableContainerFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getFirClassifierByFqName", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getFirClassifierContainerFile", "getFirClassifierContainerFileIfAny", "getFirFilesByPackage", "", "getFirScriptByFilePath", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "path", "", "getFirScriptContainerFile", "shouldNotBeCalled", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLibrarySessionProvider extends FirProvider {
    private final FirSymbolProvider symbolProvider;

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirCallableContainerFile(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirClassifierContainerFileIfAny(ClassId fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirScriptSymbol getFirScriptByFilePath(String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirScriptContainerFile(FirScriptSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirSymbolProvider getSymbolProvider() {
        return this.symbolProvider;
    }

    public FirLibrarySessionProvider(FirSymbolProvider symbolProvider) {
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        this.symbolProvider = symbolProvider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirClassLikeDeclaration getFirClassifierByFqName(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        FirClassLikeSymbol<?> classLikeSymbolByClassId = getSymbolProvider().getClassLikeSymbolByClassId(classId);
        if (classLikeSymbolByClassId != null) {
            return (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public FirFile getFirClassifierContainerFile(ClassId fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public List<FirFile> getFirFilesByPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.providers.FirProvider
    public Set<Name> getClassNamesInPackage(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        shouldNotBeCalled();
        throw null;
    }

    private final Void shouldNotBeCalled() {
        throw new IllegalStateException("Should not be called for FirLibrarySessionProvider".toString());
    }
}
