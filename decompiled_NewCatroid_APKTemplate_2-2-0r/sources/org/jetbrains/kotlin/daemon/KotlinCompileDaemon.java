package org.jetbrains.kotlin.daemon;

import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.daemon.common.DaemonJVMOptions;
import org.jetbrains.kotlin.daemon.common.DaemonOptions;
import org.jetbrains.kotlin.daemon.common.DaemonParamsKt;
import org.jetbrains.kotlin.daemon.common.NetworkUtilsKt;

/* JADX INFO: compiled from: KotlinCompileDaemon.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014J\u001b\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0007¢\u0006\u0002\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/daemon/KotlinCompileDaemon;", "Lorg/jetbrains/kotlin/daemon/KotlinCompileDaemonBase;", "()V", "getCompileServiceAndPort", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/daemon/CompileServiceImpl;", "", "compilerSelector", "Lorg/jetbrains/kotlin/daemon/CompilerSelector;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "daemonOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonOptions;", "daemonJVMOptions", "Lorg/jetbrains/kotlin/daemon/common/DaemonJVMOptions;", "timer", "Ljava/util/Timer;", "main", "", "args", "", "", "([Ljava/lang/String;)V", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCompileDaemon extends KotlinCompileDaemonBase {
    public static final KotlinCompileDaemon INSTANCE = new KotlinCompileDaemon();

    private KotlinCompileDaemon() {
    }

    @JvmStatic
    public static final void main(String[] args) {
        Intrinsics.checkNotNullParameter(args, "args");
        INSTANCE.mainImpl(args);
    }

    @Override // org.jetbrains.kotlin.daemon.KotlinCompileDaemonBase
    protected Pair<CompileServiceImpl, Integer> getCompileServiceAndPort(CompilerSelector compilerSelector, CompilerId compilerId, final DaemonOptions daemonOptions, DaemonJVMOptions daemonJVMOptions, final Timer timer) {
        Intrinsics.checkNotNullParameter(compilerSelector, "compilerSelector");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(daemonOptions, "daemonOptions");
        Intrinsics.checkNotNullParameter(daemonJVMOptions, "daemonJVMOptions");
        Intrinsics.checkNotNullParameter(timer, "timer");
        Pair<Registry, Integer> pairFindPortAndCreateRegistry = NetworkUtilsKt.findPortAndCreateRegistry(10, DaemonParamsKt.COMPILE_DAEMON_PORTS_RANGE_START, 18000);
        Registry registryComponent1 = pairFindPortAndCreateRegistry.component1();
        int iIntValue = pairFindPortAndCreateRegistry.component2().intValue();
        return new Pair<>(new CompileServiceImpl(registryComponent1, compilerSelector, compilerId, daemonOptions, daemonJVMOptions, iIntValue, timer, new Function0<Unit>() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemon$getCompileServiceAndPort$1$compilerService$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                if (daemonOptions.getForceShutdownTimeoutMilliseconds() != 0) {
                    Timer timer2 = timer;
                    long forceShutdownTimeoutMilliseconds = daemonOptions.getForceShutdownTimeoutMilliseconds();
                    final KotlinCompileDaemon kotlinCompileDaemon = this;
                    timer2.schedule(new TimerTask() { // from class: org.jetbrains.kotlin.daemon.KotlinCompileDaemon$getCompileServiceAndPort$1$compilerService$1$invoke$$inlined$schedule$1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() {
                            cancel();
                            kotlinCompileDaemon.getLog().info("force JVM shutdown");
                            System.exit(0);
                            throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
                        }
                    }, forceShutdownTimeoutMilliseconds);
                    return;
                }
                timer.cancel();
            }
        }), Integer.valueOf(iIntValue));
    }
}
