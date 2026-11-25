package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirClassLikeSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"ANONYMOUS_CLASS_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getANONYMOUS_CLASS_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirClassLikeSymbolKt {
    private static final ClassId ANONYMOUS_CLASS_ID = new ClassId(FqName.ROOT, FqName.topLevel(SpecialNames.ANONYMOUS), true);

    public static final ClassId getANONYMOUS_CLASS_ID() {
        return ANONYMOUS_CLASS_ID;
    }
}
