package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoSyntaxError.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoExceptions.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoSyntaxError;", "Lorg/catrobat/catroid/utils/lunoscript/LunoException;", CrashHianalyticsData.MESSAGE, "", Constant.LINE, "", "position", "(Ljava/lang/String;II)V", "getLine", "()I", "getPosition", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LunoSyntaxError extends LunoException {
    private final int line;
    private final int position;

    public /* synthetic */ LunoSyntaxError(String str, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i3 & 2) != 0 ? -1 : i, (i3 & 4) != 0 ? -1 : i2);
    }

    public final int getLine() {
        return this.line;
    }

    public final int getPosition() {
        return this.position;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LunoSyntaxError(String message, int i, int i2) {
        super("Syntax Error" + (i != -1 ? " at line " + i + ", pos " + i2 : "") + ": " + message, null, 2, null);
        Intrinsics.checkNotNullParameter(message, "message");
        this.line = i;
        this.position = i2;
    }
}
