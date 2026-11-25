package org.jetbrains.kotlin.fir.scopes;

import java.util.Set;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirContainingNamesAwareScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J\u000e\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "()V", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirContainingNamesAwareScope extends FirScope {
    public abstract Set<Name> getCallableNames();

    public abstract Set<Name> getClassifierNames();
}
