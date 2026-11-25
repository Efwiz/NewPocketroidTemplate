package org.jetbrains.kotlin.backend.common.linkage.issues;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrDeserializationExceptions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrDeserializationException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrDisallowedErrorNode;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrSymbolTypeMismatchException;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrDeserializationException extends Exception {
    public /* synthetic */ IrDeserializationException(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private IrDeserializationException(String str) {
        super(str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        Intrinsics.checkNotNull(message);
        return message;
    }
}
