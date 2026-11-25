package org.jetbrains.kotlin.fir.symbols.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.mpp.ClassifierSymbolMarker;

/* JADX INFO: compiled from: FirClassifierSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H&\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "Lorg/jetbrains/kotlin/mpp/ClassifierSymbolMarker;", "()V", "toLookupTag", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirClassifierSymbol<E extends FirDeclaration> extends FirBasedSymbol<E> implements ClassifierSymbolMarker {
    public /* synthetic */ FirClassifierSymbol(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ConeClassifierLookupTag toLookupTag();

    private FirClassifierSymbol() {
    }
}
