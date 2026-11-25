package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: ultraLightMethods.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0004\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001H\n¢\u0006\u0004\b\u0002\u0010\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "invoke", "()Ljava/lang/Object;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightMethodForDescriptor$getAndAddLazy$1<T> extends Lambda implements Function0<T> {
    final /* synthetic */ Function0<T> $initializer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtUltraLightMethodForDescriptor$getAndAddLazy$1(Function0<? extends T> function0) {
        super(0);
        this.$initializer = function0;
    }

    @Override // kotlin.jvm.functions.Function0
    public final T invoke() {
        return this.$initializer.invoke();
    }
}
