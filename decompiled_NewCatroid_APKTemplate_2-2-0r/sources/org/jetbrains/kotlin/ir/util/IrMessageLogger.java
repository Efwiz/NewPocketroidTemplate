package org.jetbrains.kotlin.ir.util;

import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrMessageLogger.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \n2\u00020\u0001:\u0004\n\u000b\f\rJ\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "", "report", "", "severity", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Location;", "Companion", "Location", "None", "Severity", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrMessageLogger {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: IrMessageLogger.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", "", "(Ljava/lang/String;I)V", "INFO", "WARNING", MediaError.ERROR_TYPE_ERROR, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Severity {
        INFO,
        WARNING,
        ERROR;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Severity> getEntries() {
            return $ENTRIES;
        }
    }

    static CompilerConfigurationKey<IrMessageLogger> getIR_MESSAGE_LOGGER() {
        return INSTANCE.getIR_MESSAGE_LOGGER();
    }

    void report(Severity severity, String message, Location location);

    /* JADX INFO: compiled from: IrMessageLogger.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Location;", "", "filePath", "", Constant.LINE, "", "column", "(Ljava/lang/String;II)V", "getColumn", "()I", "getFilePath", "()Ljava/lang/String;", "getLine", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Location {
        private final int column;
        private final String filePath;
        private final int line;

        public static /* synthetic */ Location copy$default(Location location, String str, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                str = location.filePath;
            }
            if ((i3 & 2) != 0) {
                i = location.line;
            }
            if ((i3 & 4) != 0) {
                i2 = location.column;
            }
            return location.copy(str, i, i2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFilePath() {
            return this.filePath;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getLine() {
            return this.line;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final int getColumn() {
            return this.column;
        }

        public final Location copy(String filePath, int line, int column) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            return new Location(filePath, line, column);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Location)) {
                return false;
            }
            Location location = (Location) other;
            return Intrinsics.areEqual(this.filePath, location.filePath) && this.line == location.line && this.column == location.column;
        }

        public int hashCode() {
            return (((this.filePath.hashCode() * 31) + Integer.hashCode(this.line)) * 31) + Integer.hashCode(this.column);
        }

        public String toString() {
            return "Location(filePath=" + this.filePath + ", line=" + this.line + ", column=" + this.column + ')';
        }

        public Location(String filePath, int i, int i2) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
            this.filePath = filePath;
            this.line = i;
            this.column = i2;
        }

        public final int getColumn() {
            return this.column;
        }

        public final String getFilePath() {
            return this.filePath;
        }

        public final int getLine() {
            return this.line;
        }
    }

    /* JADX INFO: compiled from: IrMessageLogger.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$None;", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "()V", "report", "", "severity", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Severity;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Location;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class None implements IrMessageLogger {
        public static final None INSTANCE = new None();

        @Override // org.jetbrains.kotlin.ir.util.IrMessageLogger
        public void report(Severity severity, String message, Location location) {
            Intrinsics.checkNotNullParameter(severity, "severity");
            Intrinsics.checkNotNullParameter(message, "message");
        }

        private None() {
        }
    }

    /* JADX INFO: compiled from: IrMessageLogger.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IrMessageLogger$Companion;", "", "()V", "IR_MESSAGE_LOGGER", "Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "getIR_MESSAGE_LOGGER$annotations", "getIR_MESSAGE_LOGGER", "()Lorg/jetbrains/kotlin/config/CompilerConfigurationKey;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final CompilerConfigurationKey<IrMessageLogger> IR_MESSAGE_LOGGER = new CompilerConfigurationKey<>("ir message logger");

        @JvmStatic
        public static /* synthetic */ void getIR_MESSAGE_LOGGER$annotations() {
        }

        private Companion() {
        }

        public final CompilerConfigurationKey<IrMessageLogger> getIR_MESSAGE_LOGGER() {
            return IR_MESSAGE_LOGGER;
        }
    }
}
