package kotlin.collections.jdk8;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/collections/jdk8/CollectionsJDK8Kt.class
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-jdk8-1.6.21.jar:kotlin/collections/jdk8/CollectionsJDK8Kt.class
  classes11.dex
 */
/* JADX INFO: compiled from: Collections.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0010%\n\u0002\b\u0003\u001aA\u0010\u0000\u001a\u0002H\u0001\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0003\"\u0004\b\u0001\u0010\u0001*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00010\u00042\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u0006\u001a\u0002H\u0001H\u0087\b¢\u0006\u0002\u0010\u0007\u001aH\u0010\b\u001a\u00020\t\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b\u0003\"\t\b\u0001\u0010\u0001¢\u0006\u0002\b\u0003*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0006\b\u0001\u0012\u0002H\u00010\n2\u0006\u0010\u0005\u001a\u0002H\u00022\u0006\u0010\u000b\u001a\u0002H\u0001H\u0087\b¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"getOrDefault", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "Lkotlin/internal/OnlyInputTypes;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "defaultValue", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "", "", "value", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)Z", PathUtil.KOTLIN_JAVA_RUNTIME_JDK8_NAME}, k = 2, mv = {1, 9, 0}, pn = "kotlin.collections", xi = 48)
public final class CollectionsJDK8Kt {
    /* JADX WARN: Multi-variable type inference failed */
    private static final <K, V> V getOrDefault(Map<? extends K, ? extends V> map, K k, V v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        return map.getOrDefault(k, v);
    }

    private static final <K, V> boolean remove(Map<? extends K, ? extends V> map, K k, V v) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        return TypeIntrinsics.asMutableMap(map).remove(k, v);
    }
}
