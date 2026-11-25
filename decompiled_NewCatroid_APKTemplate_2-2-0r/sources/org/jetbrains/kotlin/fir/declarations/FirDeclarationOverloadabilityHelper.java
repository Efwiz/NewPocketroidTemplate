package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: FirDeclarationOverloadabilityHelper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOverloadabilityHelper;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "isOverloadable", "", "a", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirDeclarationOverloadabilityHelper extends FirSessionComponent {
    boolean isOverloadable(FirCallableDeclaration a, FirCallableDeclaration b);
}
