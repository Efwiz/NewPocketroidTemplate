package org.jetbrains.kotlin.fir.builder;

import androidx.exifinterface.media.ExifInterface;
import java.lang.Throwable;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0005\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, JvmProtoBufUtil.PLATFORM_TYPE_ID, "compute", "()Ljava/lang/Object;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class PsiRawFirBuilder$runOnStubs$1<T, E extends Throwable> implements ThrowableComputable {
    final /* synthetic */ Function0<T> $body;

    /* JADX WARN: Multi-variable type inference failed */
    public PsiRawFirBuilder$runOnStubs$1(Function0<? extends T> function0) {
        this.$body = function0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
    public final T compute() {
        return this.$body.invoke();
    }
}
