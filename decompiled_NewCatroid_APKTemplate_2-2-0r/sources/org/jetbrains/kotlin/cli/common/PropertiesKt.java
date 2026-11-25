package org.jetbrains.kotlin.cli.common;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Properties.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005\"\u0011\u0010\u0000\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0002¨\u0006\u0006"}, d2 = {"isWindows", "", "()Z", "toBooleanLenient", "", "(Ljava/lang/String;)Ljava/lang/Boolean;", "cli-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertiesKt {
    public static final boolean isWindows() {
        String value = CompilerSystemProperties.OS_NAME.getValue();
        Intrinsics.checkNotNull(value);
        String lowerCase = value.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return StringsKt.startsWith$default(lowerCase, "windows", false, 2, (Object) null);
    }

    public static final Boolean toBooleanLenient(String str) {
        String lowerCase;
        if (str != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        } else {
            lowerCase = null;
        }
        if (lowerCase == null) {
            return false;
        }
        if (CollectionsKt.contains(CollectionsKt.listOf((Object[]) new String[]{"", "yes", "true", "on", "y"}), lowerCase)) {
            return true;
        }
        return CollectionsKt.contains(CollectionsKt.listOf((Object[]) new String[]{"no", "false", "off", "n"}), lowerCase) ? false : null;
    }
}
