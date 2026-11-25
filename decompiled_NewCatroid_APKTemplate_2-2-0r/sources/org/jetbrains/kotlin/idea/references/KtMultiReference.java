package org.jetbrains.kotlin.idea.references;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtElement;

/* JADX INFO: compiled from: KtReference.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtMultiReference;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/idea/references/AbstractKtReference;", "expression", "(Lorg/jetbrains/kotlin/psi/KtElement;)V", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtMultiReference<T extends KtElement> extends AbstractKtReference<T> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtMultiReference(T expression) {
        super(expression);
        Intrinsics.checkNotNullParameter(expression, "expression");
    }
}
