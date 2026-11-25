package org.jetbrains.kotlin.fir.java.declarations;

import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0000¨\u0006\u0004"}, d2 = {"javaOrigin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin$Java;", "isFromSource", "", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final FirDeclarationOrigin.Java javaOrigin(boolean z) {
        return z ? FirDeclarationOrigin.Java.Source.INSTANCE : FirDeclarationOrigin.Java.Library.INSTANCE;
    }
}
