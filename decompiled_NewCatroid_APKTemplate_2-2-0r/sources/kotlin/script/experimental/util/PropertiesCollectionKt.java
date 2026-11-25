package kotlin.script.experimental.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: propertiesCollection.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a#\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"getOrError", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/util/PropertiesCollection;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "(Lkotlin/script/experimental/util/PropertiesCollection;Lkotlin/script/experimental/util/PropertiesCollection$Key;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertiesCollectionKt {
    public static final <T> T getOrError(PropertiesCollection propertiesCollection, PropertiesCollection.Key<T> key) {
        Intrinsics.checkNotNullParameter(propertiesCollection, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        T t = (T) propertiesCollection.get(key);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("Unknown key " + key);
    }
}
