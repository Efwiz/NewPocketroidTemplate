package org.jetbrains.kotlin.cli.common.profiling;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: compiled from: ProfilingCompilerPerformanceManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\b\u0010\u0017\u001a\u00020\u0015H\u0016J\u0018\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0002J\b\u0010\u001e\u001a\u00020\u0015H\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/cli/common/profiling/ProfilingCompilerPerformanceManager;", "Lorg/jetbrains/kotlin/cli/common/CommonCompilerPerformanceManager;", "profilerPath", "", "command", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "(Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)V", AppMeasurementSdk.ConditionalUserProperty.ACTIVE, "", "getCommand", "()Ljava/lang/String;", "formatter", "Ljava/text/SimpleDateFormat;", "getOutputDir", "()Ljava/io/File;", "profiler", "Lorg/jetbrains/kotlin/cli/common/profiling/AsyncProfilerReflected;", "runDate", "Ljava/util/Date;", "dumpProfile", "", "postfix", "notifyCompilationFinished", "notifyRepeat", "total", "", "number", "restartProfiling", "startProfiling", "stopProfiling", "Companion", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProfilingCompilerPerformanceManager extends CommonCompilerPerformanceManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean active;
    private final String command;
    private final SimpleDateFormat formatter;
    private final File outputDir;
    private final AsyncProfilerReflected profiler;
    private final Date runDate;

    public final String getCommand() {
        return this.command;
    }

    public final File getOutputDir() {
        return this.outputDir;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProfilingCompilerPerformanceManager(String profilerPath, String command, File outputDir) {
        super("Profiling");
        Intrinsics.checkNotNullParameter(profilerPath, "profilerPath");
        Intrinsics.checkNotNullParameter(command, "command");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        this.command = command;
        this.outputDir = outputDir;
        this.profiler = AsyncProfilerHelper.INSTANCE.getInstance(profilerPath);
        this.runDate = new Date();
        this.formatter = new SimpleDateFormat("yyyy-MM-dd__HH-mm");
        startProfiling();
    }

    private final void startProfiling() {
        this.profiler.execute(this.command);
        this.active = true;
    }

    private final void stopProfiling() {
        if (this.active) {
            this.profiler.stop();
        }
        this.active = false;
    }

    private final void restartProfiling() {
        stopProfiling();
        startProfiling();
    }

    private final void dumpProfile(String postfix) {
        this.outputDir.mkdirs();
        File fileResolve = FilesKt.resolve(this.outputDir, "snapshot-" + this.formatter.format(this.runDate) + '-' + postfix + ".collapsed");
        String strExecute = this.profiler.execute("collapsed");
        FileOutputStream fileOutputStream = new FileOutputStream(fileResolve);
        try {
            FileOutputStream fileOutputStream2 = fileOutputStream;
            Iterator<String> it = StringsKt.chunkedSequence(strExecute, 1048576).iterator();
            while (it.hasNext()) {
                byte[] bytes = it.next().getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                fileOutputStream2.write(bytes);
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
            this.active = false;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager
    public void notifyRepeat(int total, int number) {
        dumpProfile("repeat" + number);
        restartProfiling();
    }

    @Override // org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager
    public void notifyCompilationFinished() {
        dumpProfile("final");
        stopProfiling();
    }

    /* JADX INFO: compiled from: ProfilingCompilerPerformanceManager.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/profiling/ProfilingCompilerPerformanceManager$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/cli/common/profiling/ProfilingCompilerPerformanceManager;", "profileCompilerArgument", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ProfilingCompilerPerformanceManager create(String profileCompilerArgument) {
            Intrinsics.checkNotNullParameter(profileCompilerArgument, "profileCompilerArgument");
            List listSplit$default = StringsKt.split$default((CharSequence) profileCompilerArgument, new String[]{":"}, false, 3, 2, (Object) null);
            return new ProfilingCompilerPerformanceManager((String) listSplit$default.get(0), (String) listSplit$default.get(1), new File((String) listSplit$default.get(2)));
        }
    }
}
