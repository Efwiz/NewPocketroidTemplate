package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: deprecations.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"EmptyDeprecationsPerUseSite", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "getEmptyDeprecationsPerUseSite", "()Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeprecationsKt {
    private static final DeprecationsPerUseSite EmptyDeprecationsPerUseSite = new DeprecationsPerUseSite(null, null);

    public static final DeprecationsPerUseSite getEmptyDeprecationsPerUseSite() {
        return EmptyDeprecationsPerUseSite;
    }
}
