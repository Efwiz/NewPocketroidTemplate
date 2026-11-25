package org.jetbrains.kotlin.ir.backend.js.export;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0001\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute;", "", "()V", "DeprecatedAttribute", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute$DeprecatedAttribute;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExportedAttribute {
    public /* synthetic */ ExportedAttribute(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute$DeprecatedAttribute;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DeprecatedAttribute extends ExportedAttribute {
        private final String message;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DeprecatedAttribute(String message) {
            super(null);
            Intrinsics.checkNotNullParameter(message, "message");
            this.message = message;
        }

        public final String getMessage() {
            return this.message;
        }
    }

    private ExportedAttribute() {
    }
}
