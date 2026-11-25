package org.jetbrains.kotlin.ir.backend.js.export;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExportModelGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "", "()V", "Allowed", "Implicit", "NotNeeded", "Prohibited", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Allowed;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Implicit;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$NotNeeded;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Prohibited;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Exportability {
    public /* synthetic */ Exportability(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ExportModelGenerator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Allowed;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Allowed extends Exportability {
        public static final Allowed INSTANCE = new Allowed();

        private Allowed() {
            super(null);
        }
    }

    private Exportability() {
    }

    /* JADX INFO: compiled from: ExportModelGenerator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$NotNeeded;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotNeeded extends Exportability {
        public static final NotNeeded INSTANCE = new NotNeeded();

        private NotNeeded() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ExportModelGenerator.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Implicit;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Implicit extends Exportability {
        public static final Implicit INSTANCE = new Implicit();

        private Implicit() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ExportModelGenerator.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability$Prohibited;", "Lorg/jetbrains/kotlin/ir/backend/js/export/Exportability;", "reason", "", "(Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Prohibited extends Exportability {
        private final String reason;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Prohibited(String reason) {
            super(null);
            Intrinsics.checkNotNullParameter(reason, "reason");
            this.reason = reason;
        }

        public final String getReason() {
            return this.reason;
        }
    }
}
