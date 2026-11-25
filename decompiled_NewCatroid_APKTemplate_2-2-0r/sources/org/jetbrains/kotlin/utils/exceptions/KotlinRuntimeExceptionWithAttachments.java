package org.jetbrains.kotlin.utils.exceptions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;

/* JADX INFO: compiled from: KotlinExceptionWithAttachments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u001b\b\u0016\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/utils/exceptions/KotlinRuntimeExceptionWithAttachments;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "mutableAttachments", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment;", "getMutableAttachments", "()Ljava/util/List;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinRuntimeExceptionWithAttachments extends RuntimeException implements KotlinExceptionWithAttachments {
    private final List<Attachment> mutableAttachments;

    @Override // org.jetbrains.kotlin.utils.exceptions.KotlinExceptionWithAttachments
    public final List<Attachment> getMutableAttachments() {
        return this.mutableAttachments;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinRuntimeExceptionWithAttachments(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
        this.mutableAttachments = new ArrayList();
    }

    public KotlinRuntimeExceptionWithAttachments(String str, Throwable th) {
        super(str, th);
        this.mutableAttachments = new ArrayList();
        KotlinExceptionWithAttachments.INSTANCE.withAttachmentsFrom$util(this, th);
    }
}
