package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.GeneratedDeclarationKey;

/* JADX INFO: compiled from: FirDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "getOrigin", "(Lorg/jetbrains/kotlin/GeneratedDeclarationKey;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationOriginKt {
    public static final FirDeclarationOrigin getOrigin(GeneratedDeclarationKey generatedDeclarationKey) {
        Intrinsics.checkNotNullParameter(generatedDeclarationKey, "<this>");
        return new FirDeclarationOrigin.Plugin(generatedDeclarationKey);
    }
}
