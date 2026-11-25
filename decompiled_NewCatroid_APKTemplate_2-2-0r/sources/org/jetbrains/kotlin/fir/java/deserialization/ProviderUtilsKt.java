package org.jetbrains.kotlin.fir.java.deserialization;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: compiled from: ProviderUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"KotlinBuiltins", "", "", "getKotlinBuiltins", "()Ljava/util/Set;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProviderUtilsKt {
    private static final Set<String> KotlinBuiltins = SetsKt.setOf((Object[]) new String[]{"kotlin/ArrayIntrinsicsKt", "kotlin/internal/ProgressionUtilKt"});

    public static final Set<String> getKotlinBuiltins() {
        return KotlinBuiltins;
    }
}
