package org.catrobat.catroid.web;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebConnectionException.class
  classes14.dex
 */
/* JADX INFO: compiled from: WebConnectionException.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \n2\u00060\u0001j\u0002`\u0002:\u0001\nB\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/web/WebConnectionException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "statusCode", "", CrashHianalyticsData.MESSAGE, "", "(ILjava/lang/String;)V", "getStatusCode", "()I", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WebConnectionException extends Exception {
    public static final int ERROR_EMPTY_PROJECT_DATA = 1003;
    public static final int ERROR_JSON = 1001;
    public static final int ERROR_NETWORK = 1002;
    private static final long serialVersionUID = 1;
    private final int statusCode;

    public final int getStatusCode() {
        return this.statusCode;
    }

    public /* synthetic */ WebConnectionException(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? "Unknown Error, no exception message given." : str);
    }

    public WebConnectionException(int i, String str) {
        super(str);
        this.statusCode = i;
    }
}
