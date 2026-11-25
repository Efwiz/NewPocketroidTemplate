package org.koin.ext;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/ext/StringExtKt.class
  classes6.dex
 */
/* JADX INFO: compiled from: StringExt.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"quoted", "", "koin-core"}, k = 2, mv = {1, 1, 16})
public final class StringExtKt {
    public static final String quoted(String quoted) {
        Intrinsics.checkParameterIsNotNull(quoted, "$this$quoted");
        return StringsKt.replace$default(quoted, "\"", "", false, 4, (Object) null);
    }
}
