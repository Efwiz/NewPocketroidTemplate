package org.jetbrains.kotlin.konan.util;

import io.netty.util.internal.StringUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.konan.target.KonanTarget;

/* JADX INFO: compiled from: Substitution.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a\"\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001¨\u0006\n"}, d2 = {"defaultTargetSubstitutions", "", "", "target", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "substitute", "", "properties", "Ljava/util/Properties;", "substitutions", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class SubstitutionKt {
    public static final Map<String, String> defaultTargetSubstitutions(KonanTarget target) {
        Intrinsics.checkNotNullParameter(target, "target");
        return MapsKt.mapOf(TuplesKt.to("target", target.getVisibleName()), TuplesKt.to("arch", VisibleNamedKt.getVisibleName(target.getArchitecture())), TuplesKt.to("family", VisibleNamedKt.getVisibleName(target.getFamily())));
    }

    public static final void substitute(Properties properties, Map<String, String> substitutions) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(substitutions, "substitutions");
        for (String key : properties.stringPropertyNames()) {
            Iterator<String> it = substitutions.values().iterator();
            while (it.hasNext()) {
                String str = "." + it.next();
                Intrinsics.checkNotNullExpressionValue(key, "key");
                if (StringsKt.endsWith$default(key, str, false, 2, (Object) null)) {
                    String strRemoveSuffix = StringsKt.removeSuffix(key, (CharSequence) str);
                    String property = properties.getProperty(strRemoveSuffix, "");
                    String property2 = properties.getProperty(key, "");
                    if (!Intrinsics.areEqual(property, "")) {
                        property2 = property + StringUtil.SPACE + property2;
                    }
                    properties.setProperty(strRemoveSuffix, property2);
                }
            }
        }
    }
}
