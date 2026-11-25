package org.jetbrains.kotlin.fir.resolve.providers;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirScriptSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH&J\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H&J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0014\u0010\u0017\u001a\u00020\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u0016H&J\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u00102\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u0016H&J\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u001b2\u0006\u0010\r\u001a\u00020\u000eH&J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH&J\u0012\u0010 \u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u001dH&R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "isPhasedFirAllowed", "", "()Z", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getSymbolProvider", "()Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "getClassNamesInPackage", "", "Lorg/jetbrains/kotlin/name/Name;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFirCallableContainerFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getFirClassifierByFqName", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getFirClassifierContainerFile", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getFirClassifierContainerFileIfAny", "getFirFilesByPackage", "", "getFirScriptByFilePath", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirScriptSymbol;", "path", "", "getFirScriptContainerFile", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirProvider implements FirSessionComponent {
    public abstract Set<Name> getClassNamesInPackage(FqName fqName);

    public abstract FirFile getFirCallableContainerFile(FirCallableSymbol<?> symbol);

    public abstract FirClassLikeDeclaration getFirClassifierByFqName(ClassId classId);

    public abstract FirFile getFirClassifierContainerFile(ClassId fqName);

    public abstract FirFile getFirClassifierContainerFileIfAny(ClassId fqName);

    public abstract List<FirFile> getFirFilesByPackage(FqName fqName);

    public abstract FirScriptSymbol getFirScriptByFilePath(String path);

    public abstract FirFile getFirScriptContainerFile(FirScriptSymbol symbol);

    public abstract FirSymbolProvider getSymbolProvider();

    public boolean isPhasedFirAllowed() {
        return false;
    }

    public FirFile getFirClassifierContainerFile(FirClassLikeSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return getFirClassifierContainerFile(symbol.getClassId());
    }

    public FirFile getFirClassifierContainerFileIfAny(FirClassLikeSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return getFirClassifierContainerFileIfAny(symbol.getClassId());
    }
}
