package kotlin;

import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/HashCodeKt.class
  classes11.dex
 */
/* JADX INFO: compiled from: HashCode.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0000\n\u0000\u001a\u000f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b¨\u0006\u0003"}, d2 = {"hashCode", "", "", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HashCodeKt {
    private static final int hashCode(Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }
}
