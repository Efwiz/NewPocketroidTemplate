package org.jetbrains.kotlin.daemon.common;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DaemonParams.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f¢\u0006\u0002\u0010\u000eJ\t\u0010,\u001a\u00020\u0003HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\t\u0010.\u001a\u00020\u0007HÆ\u0003J\t\u0010/\u001a\u00020\u0007HÆ\u0003J\t\u00100\u001a\u00020\u0005HÆ\u0003J\t\u00101\u001a\u00020\u0005HÆ\u0003J\t\u00102\u001a\u00020\fHÆ\u0003J\t\u00103\u001a\u00020\fHÆ\u0003JY\u00104\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fHÆ\u0001J\u0013\u00105\u001a\u00020\f2\b\u00106\u001a\u0004\u0018\u000107HÖ\u0003J\t\u00108\u001a\u00020\u0007HÖ\u0001J\t\u00109\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0014\"\u0004\b\u001a\u0010\u0016R&\u0010\u001b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u001d0\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010\r\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0014\"\u0004\b)\u0010\u0016R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010!\"\u0004\b+\u0010#¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "Lorg/jetbrains/kotlin/daemon/common/OptionsGroup;", "runFilesPath", "", "autoshutdownMemoryThreshold", "", "autoshutdownIdleSeconds", "", "autoshutdownUnusedSeconds", "shutdownDelayMilliseconds", "forceShutdownTimeoutMilliseconds", "verbose", "", "reportPerf", "(Ljava/lang/String;JIIJJZZ)V", "getAutoshutdownIdleSeconds", "()I", "setAutoshutdownIdleSeconds", "(I)V", "getAutoshutdownMemoryThreshold", "()J", "setAutoshutdownMemoryThreshold", "(J)V", "getAutoshutdownUnusedSeconds", "setAutoshutdownUnusedSeconds", "getForceShutdownTimeoutMilliseconds", "setForceShutdownTimeoutMilliseconds", "mappers", "", "Lorg/jetbrains/kotlin/daemon/common/PropMapper;", "getMappers", "()Ljava/util/List;", "getReportPerf", "()Z", "setReportPerf", "(Z)V", "getRunFilesPath", "()Ljava/lang/String;", "setRunFilesPath", "(Ljava/lang/String;)V", "getShutdownDelayMilliseconds", "setShutdownDelayMilliseconds", "getVerbose", "setVerbose", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toString", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DaemonOptions implements OptionsGroup {
    private int autoshutdownIdleSeconds;
    private long autoshutdownMemoryThreshold;
    private int autoshutdownUnusedSeconds;
    private long forceShutdownTimeoutMilliseconds;
    private boolean reportPerf;
    private String runFilesPath;
    private long shutdownDelayMilliseconds;
    private boolean verbose;

    public DaemonOptions() {
        this(null, 0L, 0, 0, 0L, 0L, false, false, 255, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getRunFilesPath() {
        return this.runFilesPath;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getAutoshutdownMemoryThreshold() {
        return this.autoshutdownMemoryThreshold;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getAutoshutdownIdleSeconds() {
        return this.autoshutdownIdleSeconds;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getAutoshutdownUnusedSeconds() {
        return this.autoshutdownUnusedSeconds;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getShutdownDelayMilliseconds() {
        return this.shutdownDelayMilliseconds;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getForceShutdownTimeoutMilliseconds() {
        return this.forceShutdownTimeoutMilliseconds;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getVerbose() {
        return this.verbose;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getReportPerf() {
        return this.reportPerf;
    }

    public final DaemonOptions copy(String runFilesPath, long autoshutdownMemoryThreshold, int autoshutdownIdleSeconds, int autoshutdownUnusedSeconds, long shutdownDelayMilliseconds, long forceShutdownTimeoutMilliseconds, boolean verbose, boolean reportPerf) {
        Intrinsics.checkNotNullParameter(runFilesPath, "runFilesPath");
        return new DaemonOptions(runFilesPath, autoshutdownMemoryThreshold, autoshutdownIdleSeconds, autoshutdownUnusedSeconds, shutdownDelayMilliseconds, forceShutdownTimeoutMilliseconds, verbose, reportPerf);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DaemonOptions)) {
            return false;
        }
        DaemonOptions daemonOptions = (DaemonOptions) other;
        return Intrinsics.areEqual(this.runFilesPath, daemonOptions.runFilesPath) && this.autoshutdownMemoryThreshold == daemonOptions.autoshutdownMemoryThreshold && this.autoshutdownIdleSeconds == daemonOptions.autoshutdownIdleSeconds && this.autoshutdownUnusedSeconds == daemonOptions.autoshutdownUnusedSeconds && this.shutdownDelayMilliseconds == daemonOptions.shutdownDelayMilliseconds && this.forceShutdownTimeoutMilliseconds == daemonOptions.forceShutdownTimeoutMilliseconds && this.verbose == daemonOptions.verbose && this.reportPerf == daemonOptions.reportPerf;
    }

    public int hashCode() {
        return (((((((((((((this.runFilesPath.hashCode() * 31) + Long.hashCode(this.autoshutdownMemoryThreshold)) * 31) + Integer.hashCode(this.autoshutdownIdleSeconds)) * 31) + Integer.hashCode(this.autoshutdownUnusedSeconds)) * 31) + Long.hashCode(this.shutdownDelayMilliseconds)) * 31) + Long.hashCode(this.forceShutdownTimeoutMilliseconds)) * 31) + Boolean.hashCode(this.verbose)) * 31) + Boolean.hashCode(this.reportPerf);
    }

    public String toString() {
        return "DaemonOptions(runFilesPath=" + this.runFilesPath + ", autoshutdownMemoryThreshold=" + this.autoshutdownMemoryThreshold + ", autoshutdownIdleSeconds=" + this.autoshutdownIdleSeconds + ", autoshutdownUnusedSeconds=" + this.autoshutdownUnusedSeconds + ", shutdownDelayMilliseconds=" + this.shutdownDelayMilliseconds + ", forceShutdownTimeoutMilliseconds=" + this.forceShutdownTimeoutMilliseconds + ", verbose=" + this.verbose + ", reportPerf=" + this.reportPerf + ')';
    }

    public DaemonOptions(String runFilesPath, long j, int i, int i2, long j2, long j3, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(runFilesPath, "runFilesPath");
        this.runFilesPath = runFilesPath;
        this.autoshutdownMemoryThreshold = j;
        this.autoshutdownIdleSeconds = i;
        this.autoshutdownUnusedSeconds = i2;
        this.shutdownDelayMilliseconds = j2;
        this.forceShutdownTimeoutMilliseconds = j3;
        this.verbose = z;
        this.reportPerf = z2;
    }

    public /* synthetic */ DaemonOptions(String str, long j, int i, int i2, long j2, long j3, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? DaemonParamsKt.getCOMPILE_DAEMON_DEFAULT_RUN_DIR_PATH() : str, (i3 & 2) != 0 ? 0L : j, (i3 & 4) != 0 ? DaemonParamsKt.COMPILE_DAEMON_DEFAULT_IDLE_TIMEOUT_S : i, (i3 & 8) != 0 ? 60 : i2, (i3 & 16) != 0 ? 1000L : j2, (i3 & 32) != 0 ? 10000L : j3, (i3 & 64) != 0 ? false : z, (i3 & 128) == 0 ? z2 : false);
    }

    public final String getRunFilesPath() {
        return this.runFilesPath;
    }

    public final void setRunFilesPath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.runFilesPath = str;
    }

    public final long getAutoshutdownMemoryThreshold() {
        return this.autoshutdownMemoryThreshold;
    }

    public final void setAutoshutdownMemoryThreshold(long j) {
        this.autoshutdownMemoryThreshold = j;
    }

    public final int getAutoshutdownIdleSeconds() {
        return this.autoshutdownIdleSeconds;
    }

    public final void setAutoshutdownIdleSeconds(int i) {
        this.autoshutdownIdleSeconds = i;
    }

    public final int getAutoshutdownUnusedSeconds() {
        return this.autoshutdownUnusedSeconds;
    }

    public final void setAutoshutdownUnusedSeconds(int i) {
        this.autoshutdownUnusedSeconds = i;
    }

    public final long getShutdownDelayMilliseconds() {
        return this.shutdownDelayMilliseconds;
    }

    public final void setShutdownDelayMilliseconds(long j) {
        this.shutdownDelayMilliseconds = j;
    }

    public final long getForceShutdownTimeoutMilliseconds() {
        return this.forceShutdownTimeoutMilliseconds;
    }

    public final void setForceShutdownTimeoutMilliseconds(long j) {
        this.forceShutdownTimeoutMilliseconds = j;
    }

    public final boolean getVerbose() {
        return this.verbose;
    }

    public final void setVerbose(boolean z) {
        this.verbose = z;
    }

    public final boolean getReportPerf() {
        return this.reportPerf;
    }

    public final void setReportPerf(boolean z) {
        this.reportPerf = z;
    }

    @Override // org.jetbrains.kotlin.daemon.common.OptionsGroup
    public List<PropMapper<?, ?, ?>> getMappers() {
        List list = null;
        DefaultConstructorMarker defaultConstructorMarker = null;
        int i = 4;
        return CollectionsKt.listOf((Object[]) new PropMapper[]{new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$1
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getRunFilesPath()Ljava/lang/String;";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return ((DaemonOptions) obj).getRunFilesPath();
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "runFilesPath";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setRunFilesPath((String) obj2);
            }
        }, null, DaemonOptions$mappers$2.INSTANCE, null, null, null, 116, null), new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$3
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAutoshutdownMemoryThreshold()J";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Long.valueOf(((DaemonOptions) obj).getAutoshutdownMemoryThreshold());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "autoshutdownMemoryThreshold";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setAutoshutdownMemoryThreshold(((Number) obj2).longValue());
            }
        }, null, DaemonOptions$mappers$4.INSTANCE, null, new Function1<Long, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$5
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Long l) {
                return invoke(l.longValue());
            }

            public final Boolean invoke(long j) {
                return Boolean.valueOf(j == 0);
            }
        }, ContainerUtils.KEY_VALUE_DELIMITER, 20, null), new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$6
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAutoshutdownIdleSeconds()I";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Integer.valueOf(((DaemonOptions) obj).getAutoshutdownIdleSeconds());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "autoshutdownIdleSeconds";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setAutoshutdownIdleSeconds(((Number) obj2).intValue());
            }
        }, null, DaemonOptions$mappers$7.INSTANCE, null, new Function1<Integer, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$8
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Boolean invoke(int i2) {
                return Boolean.valueOf(i2 == 0);
            }
        }, ContainerUtils.KEY_VALUE_DELIMITER, 20, null), new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$9
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getAutoshutdownUnusedSeconds()I";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Integer.valueOf(((DaemonOptions) obj).getAutoshutdownUnusedSeconds());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "autoshutdownUnusedSeconds";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setAutoshutdownUnusedSeconds(((Number) obj2).intValue());
            }
        }, null, DaemonOptions$mappers$10.INSTANCE, null, new Function1<Integer, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$11
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Boolean invoke(int i2) {
                return Boolean.valueOf(i2 == 60);
            }
        }, ContainerUtils.KEY_VALUE_DELIMITER, 20, null), new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$12
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getShutdownDelayMilliseconds()J";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Long.valueOf(((DaemonOptions) obj).getShutdownDelayMilliseconds());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "shutdownDelayMilliseconds";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setShutdownDelayMilliseconds(((Number) obj2).longValue());
            }
        }, null, DaemonOptions$mappers$13.INSTANCE, null, new Function1<Long, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$14
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Long l) {
                return invoke(l.longValue());
            }

            public final Boolean invoke(long j) {
                return Boolean.valueOf(j == 1000);
            }
        }, ContainerUtils.KEY_VALUE_DELIMITER, 20, null), new PropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$15
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getForceShutdownTimeoutMilliseconds()J";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Long.valueOf(((DaemonOptions) obj).getForceShutdownTimeoutMilliseconds());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "forceShutdownTimeoutMilliseconds";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setForceShutdownTimeoutMilliseconds(((Number) obj2).longValue());
            }
        }, null, DaemonOptions$mappers$16.INSTANCE, null, new Function1<Long, Boolean>() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$17
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Boolean invoke(Long l) {
                return invoke(l.longValue());
            }

            public final Boolean invoke(long j) {
                return Boolean.valueOf(j == 10000);
            }
        }, ContainerUtils.KEY_VALUE_DELIMITER, 20, null), new BoolPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$18
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getVerbose()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((DaemonOptions) obj).getVerbose());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "verbose";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setVerbose(((Boolean) obj2).booleanValue());
            }
        }, list, i, defaultConstructorMarker), new BoolPropMapper(this, new MutablePropertyReference1() { // from class: org.jetbrains.kotlin.daemon.common.DaemonOptions$mappers$19
            @Override // kotlin.jvm.internal.CallableReference
            public String getSignature() {
                return "getReportPerf()Z";
            }

            @Override // kotlin.reflect.KProperty1
            public Object get(Object obj) {
                return Boolean.valueOf(((DaemonOptions) obj).getReportPerf());
            }

            @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
            public String getName() {
                return "reportPerf";
            }

            @Override // kotlin.jvm.internal.CallableReference
            public KDeclarationContainer getOwner() {
                return Reflection.getOrCreateKotlinClass(DaemonOptions.class);
            }

            @Override // kotlin.reflect.KMutableProperty1
            public void set(Object obj, Object obj2) {
                ((DaemonOptions) obj).setReportPerf(((Boolean) obj2).booleanValue());
            }
        }, list, i, defaultConstructorMarker)});
    }
}
