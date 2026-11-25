package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoRuntimeError.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoExceptions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoRuntimeError;", "Lorg/catrobat/catroid/utils/lunoscript/LunoException;", CrashHianalyticsData.MESSAGE, "", Constant.LINE, "", "cause", "", "(Ljava/lang/String;ILjava/lang/Throwable;)V", "getLine", "()I", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LunoRuntimeError extends LunoException {
    private final int line;

    public /* synthetic */ LunoRuntimeError(String str, int i, Throwable th, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? -1 : i, (i2 & 4) != 0 ? null : th);
    }

    public final int getLine() {
        return this.line;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LunoRuntimeError(String message, int i, Throwable th) {
        super("Runtime Error" + (i != -1 ? " at line " + i : "") + ": " + message, th);
        Intrinsics.checkNotNullParameter(message, "message");
        this.line = i;
    }
}
