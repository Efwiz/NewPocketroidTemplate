package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtClassOrObject;

/* JADX INFO: compiled from: ultraLightUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0006\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0004\b\u0000\u0010\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, JvmProtoBufUtil.PLATFORM_TYPE_ID, "compute", "()Ljava/lang/Object;", "org/jetbrains/kotlin/asJava/classes/UltraLightUtilsKt$runReadAction$1"}, k = 3, mv = {1, 9, 0}, xi = 176)
public final class UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1<T> implements Computable {
    final /* synthetic */ KtClassOrObject $this_safeIsLocal$inlined;

    public UltraLightUtilsKt$safeIsLocal$$inlined$runReadAction$1(KtClassOrObject ktClassOrObject) {
        this.$this_safeIsLocal$inlined = ktClassOrObject;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
    public final T compute() {
        return (T) Boolean.valueOf(this.$this_safeIsLocal$inlined.isLocal());
    }
}
