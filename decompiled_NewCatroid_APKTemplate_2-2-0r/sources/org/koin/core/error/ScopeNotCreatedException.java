package org.koin.core.error;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/core/error/ScopeNotCreatedException.class
  classes6.dex
 */
/* JADX INFO: compiled from: ScopeNotCreatedException.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/koin/core/error/ScopeNotCreatedException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", NotificationCompat.CATEGORY_MESSAGE, "", "(Ljava/lang/String;)V", "koin-core"}, k = 1, mv = {1, 1, 16})
public final class ScopeNotCreatedException extends Exception {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScopeNotCreatedException(String msg) {
        super(msg);
        Intrinsics.checkParameterIsNotNull(msg, "msg");
    }
}
