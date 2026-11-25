package org.jetbrains.kotlin.fir.extensions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;
import org.jetbrains.kotlin.fir.extensions.predicate.LookupPredicate;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;

/* JADX INFO: compiled from: FirPredicateBasedProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u001c\u0010\u0007\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000bH&J\u001a\u0010\f\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b2\u0006\u0010\r\u001a\u00020\u000eH&J \u0010\u000f\u001a\u00020\u00042\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\b2\u0006\u0010\n\u001a\u00020\u000bJ$\u0010\u000f\u001a\u00020\u00042\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\b2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\tJ\u001c\u0010\u000f\u001a\u00020\u00042\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\n\u001a\u00020\u000bH&J\u001e\u0010\u000f\u001a\u00020\u00042\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00112\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\tJ\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0015H\u0017¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirPredicateBasedProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "fileHasPluginAnnotations", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getOwnersOfDeclaration", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getSymbolsByPredicate", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/LookupPredicate;", "matches", "predicates", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "registerAnnotatedDeclaration", "", "owners", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirPredicateBasedProvider implements FirSessionComponent {
    public abstract boolean fileHasPluginAnnotations(FirFile file);

    public abstract List<FirBasedSymbol<?>> getOwnersOfDeclaration(FirDeclaration declaration);

    public abstract List<FirBasedSymbol<?>> getSymbolsByPredicate(LookupPredicate predicate);

    public abstract boolean matches(AbstractPredicate<?> predicate, FirDeclaration declaration);

    @FirExtensionApiInternals
    public void registerAnnotatedDeclaration(FirDeclaration declaration, PersistentList<? extends FirDeclaration> owners) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(owners, "owners");
    }

    public final boolean matches(AbstractPredicate<?> predicate, FirBasedSymbol<?> declaration) {
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return matches(predicate, declaration.getFir());
    }

    public final boolean matches(List<? extends AbstractPredicate<?>> predicates, FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(predicates, "predicates");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        List<? extends AbstractPredicate<?>> list = predicates;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (matches((AbstractPredicate<?>) it.next(), declaration)) {
                return true;
            }
        }
        return false;
    }

    public final boolean matches(List<? extends AbstractPredicate<?>> predicates, FirBasedSymbol<?> declaration) {
        Intrinsics.checkNotNullParameter(predicates, "predicates");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return matches(predicates, declaration.getFir());
    }
}
