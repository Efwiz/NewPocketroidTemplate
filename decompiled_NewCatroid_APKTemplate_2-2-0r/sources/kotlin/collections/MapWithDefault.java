package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/collections/MapWithDefault.class
  classes11.dex
 */
/* JADX INFO: compiled from: MapWithDefault.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0007\bb\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0006\b\u0001\u0010\u0002 \u00012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003J\u0015\u0010\u0007\u001a\u00028\u00012\u0006\u0010\b\u001a\u00028\u0000H&¢\u0006\u0002\u0010\tR\u001e\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lkotlin/collections/MapWithDefault;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "map", "getMap", "()Ljava/util/Map;", "getOrImplicitDefault", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/lang/Object;", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
interface MapWithDefault<K, V> extends Map<K, V>, KMappedMarker {
    Map<K, V> getMap();

    V getOrImplicitDefault(K key);
}
