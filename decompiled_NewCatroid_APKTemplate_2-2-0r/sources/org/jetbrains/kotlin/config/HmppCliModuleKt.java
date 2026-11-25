package org.jetbrains.kotlin.config;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HmppCliModule.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0001¨\u0006\u0004"}, d2 = {"getModuleNameForSource", "", "Lorg/jetbrains/kotlin/config/HmppCliModuleStructure;", FirebaseAnalytics.Param.SOURCE, "config"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HmppCliModuleKt {
    public static final String getModuleNameForSource(HmppCliModuleStructure hmppCliModuleStructure, String source) {
        Object next;
        Intrinsics.checkNotNullParameter(hmppCliModuleStructure, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        Iterator<T> it = hmppCliModuleStructure.getModules().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((HmppCliModule) next).getSources().contains(source));
        HmppCliModule hmppCliModule = (HmppCliModule) next;
        if (hmppCliModule != null) {
            return hmppCliModule.getName();
        }
        return null;
    }
}
