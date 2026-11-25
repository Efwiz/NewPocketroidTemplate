package org.jetbrains.kotlin.fir.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.Comparator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Comparisons.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "a", JvmProtoBufUtil.PLATFORM_TYPE_ID, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirElementSerializer$collectDeclarations$$inlined$sortedBy$1<T> implements Comparator {
    final /* synthetic */ Map $indexByDeclaration$inlined;

    public FirElementSerializer$collectDeclarations$$inlined$sortedBy$1(Map map) {
        this.$indexByDeclaration$inlined = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Comparator
    public final int compare(T t, T t2) {
        Integer num = (Integer) this.$indexByDeclaration$inlined.get((FirCallableDeclaration) t);
        Integer numValueOf = Integer.valueOf(num != null ? num.intValue() : Integer.MAX_VALUE);
        Integer num2 = (Integer) this.$indexByDeclaration$inlined.get((FirCallableDeclaration) t2);
        return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(num2 != null ? num2.intValue() : Integer.MAX_VALUE));
    }
}
