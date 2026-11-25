package org.jetbrains.kotlin.fir.extensions;

import java.util.Collection;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.extensions.predicate.AbstractPredicate;

/* JADX INFO: compiled from: FirExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\u001a\u0010\u0005\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006\"\u0006\u0012\u0002\b\u00030\u0007H&¢\u0006\u0002\u0010\bJ\u001a\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\tH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationPredicateRegistrar;", "", "()V", "register", "", "predicates", "", "Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;", "([Lorg/jetbrains/kotlin/fir/extensions/predicate/AbstractPredicate;)V", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirDeclarationPredicateRegistrar {
    public abstract void register(Collection<? extends AbstractPredicate<?>> predicates);

    public abstract void register(AbstractPredicate<?>... predicates);
}
