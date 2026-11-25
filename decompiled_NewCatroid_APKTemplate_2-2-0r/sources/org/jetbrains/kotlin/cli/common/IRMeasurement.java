package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\r\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/IRMeasurement;", "Lorg/jetbrains/kotlin/cli/common/PerformanceMeasurement;", "lines", "", "milliseconds", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/cli/common/IRMeasurement$Kind;", "(Ljava/lang/Integer;JLorg/jetbrains/kotlin/cli/common/IRMeasurement$Kind;)V", "getKind", "()Lorg/jetbrains/kotlin/cli/common/IRMeasurement$Kind;", "getLines", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMilliseconds", "()J", "render", "", Namer.CLASS_KIND_ENUM, "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IRMeasurement implements PerformanceMeasurement {
    private final Kind kind;
    private final Integer lines;
    private final long milliseconds;

    /* JADX INFO: compiled from: performanceMeasurements.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/IRMeasurement$Kind;", "", "(Ljava/lang/String;I)V", "TRANSLATION", "LOWERING", "GENERATION", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        TRANSLATION,
        LOWERING,
        GENERATION;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }
    }

    public IRMeasurement(Integer num, long j, Kind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.lines = num;
        this.milliseconds = j;
        this.kind = kind;
    }

    public final Kind getKind() {
        return this.kind;
    }

    public final Integer getLines() {
        return this.lines;
    }

    public final long getMilliseconds() {
        return this.milliseconds;
    }

    @Override // org.jetbrains.kotlin.cli.common.PerformanceMeasurement
    /* JADX INFO: renamed from: render */
    public String getCounterReport() {
        return PerformanceMeasurementsKt.formatMeasurement("IR " + this.kind, this.milliseconds, this.lines);
    }
}
