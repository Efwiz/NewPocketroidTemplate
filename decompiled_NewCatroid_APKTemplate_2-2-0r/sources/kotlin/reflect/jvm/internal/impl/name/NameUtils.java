package kotlin.reflect.jvm.internal.impl.name;

import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;

/* JADX INFO: compiled from: NameUtils.kt */
/* JADX INFO: loaded from: classes12.dex */
public final class NameUtils {
    public static final NameUtils INSTANCE = new NameUtils();
    private static final Regex SANITIZE_AS_JAVA_INVALID_CHARACTERS = new Regex("[^\\p{L}\\p{Digit}]");

    private NameUtils() {
    }

    @JvmStatic
    public static final String sanitizeAsJavaIdentifier(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return SANITIZE_AS_JAVA_INVALID_CHARACTERS.replace(name2, "_");
    }
}
