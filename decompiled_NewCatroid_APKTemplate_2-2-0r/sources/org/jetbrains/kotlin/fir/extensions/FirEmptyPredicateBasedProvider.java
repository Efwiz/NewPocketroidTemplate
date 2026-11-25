package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.LookupPredicate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;

/* JADX INFO: compiled from: FirPredicateBasedProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\u0007\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001a\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u001c\u0010\u000f\u001a\u00020\u00042\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirEmptyPredicateBasedProvider;", "Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProvider;", "()V", "fileHasPluginAnnotations", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getOwnersOfDeclaration", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getSymbolsByPredicate", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate;", "matches", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEmptyPredicateBasedProvider extends FirPredicateBasedProvider {
    public static final FirEmptyPredicateBasedProvider INSTANCE = new FirEmptyPredicateBasedProvider();

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public boolean fileHasPluginAnnotations(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public List<FirBasedSymbol<?>> getOwnersOfDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public boolean matches(AbstractPredicate<?> predicate, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return false;
    }

    private FirEmptyPredicateBasedProvider() {
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProvider
    public List<FirBasedSymbol<?>> getSymbolsByPredicate(LookupPredicate predicate) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        return CollectionsKt.emptyList();
    }
}
