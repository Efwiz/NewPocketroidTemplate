package org.catrobat.catroid.common;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NewCatroidBroadcastManager.class
  classes12.dex
 */
/* JADX INFO: compiled from: NewCatroidBroadcastManager.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/common/NewCatroidBroadcastManager;", "", "()V", "lastParams", "Ljava/util/concurrent/ConcurrentHashMap;", "", "getParams", "signal", "setParams", "", "params", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCatroidBroadcastManager {
    public static final NewCatroidBroadcastManager INSTANCE = new NewCatroidBroadcastManager();
    private static final ConcurrentHashMap<String, String> lastParams = new ConcurrentHashMap<>();

    private NewCatroidBroadcastManager() {
    }

    public final void setParams(String signal, String params) {
        Intrinsics.checkNotNullParameter(signal, "signal");
        Intrinsics.checkNotNullParameter(params, "params");
        lastParams.put(signal, params);
    }

    public final String getParams(String signal) {
        Intrinsics.checkNotNullParameter(signal, "signal");
        String str = lastParams.get(signal);
        return str == null ? "" : str;
    }
}
