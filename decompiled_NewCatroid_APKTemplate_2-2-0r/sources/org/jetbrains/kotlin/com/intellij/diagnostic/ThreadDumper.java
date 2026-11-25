package org.jetbrains.kotlin.com.intellij.diagnostic;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes3.dex */
public final class ThreadDumper {
    private static final Comparator<ThreadInfo> THREAD_INFO_COMPARATOR = Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Boolean.valueOf(ThreadDumper.isEDT(((ThreadInfo) obj).getThreadName()));
        }
    }).thenComparing(new Function() { // from class: org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Boolean.valueOf(((ThreadInfo) obj).getThreadState() == Thread.State.RUNNABLE);
        }
    }).thenComparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper$$ExternalSyntheticLambda2
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return ThreadDumper.lambda$static$2((ThreadInfo) obj);
        }
    }).reversed();

    /* JADX WARN: Code duplicated, block: B:31:0x0061  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 6 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 6 || i == 11) ? 2 : 3];
        if (i != 1 && i != 3) {
            switch (i) {
                case 5:
                    objArr[0] = "threadMXBean";
                    break;
                case 6:
                case 11:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/ThreadDumper";
                    break;
                case 7:
                case 12:
                case 14:
                    objArr[0] = CompilerOptions.INFO;
                    break;
                case 8:
                case 13:
                case 15:
                case 18:
                case 20:
                    objArr[0] = "f";
                    break;
                case 9:
                    objArr[0] = "threadInfo";
                    break;
                case 10:
                    objArr[0] = ConfigConstants.CONFIG_KEY_THREADS;
                    break;
                case 16:
                case 19:
                case 21:
                    objArr[0] = "stackTraceElements";
                    break;
                case 17:
                    objArr[0] = "thread";
                    break;
                case 22:
                    objArr[0] = "fullThreadDump";
                    break;
                case 23:
                    objArr[0] = "exceptionType";
                    break;
                case 24:
                    objArr[0] = "state";
                    break;
                default:
                    objArr[0] = "threadInfos";
                    break;
            }
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/diagnostic/ThreadDumper";
        }
        if (i == 1) {
            objArr[1] = "dumpThreadsToString";
        } else if (i == 3) {
            objArr[1] = "dumpEdtStackTrace";
        } else if (i == 6) {
            objArr[1] = "getThreadInfos";
        } else if (i != 11) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/ThreadDumper";
        } else {
            objArr[1] = "sort";
        }
        switch (i) {
            case 1:
            case 3:
            case 6:
            case 11:
                break;
            case 2:
                objArr[2] = "dumpEdtStackTrace";
                break;
            case 4:
                objArr[2] = "getThreadDumpInfo";
                break;
            case 5:
                objArr[2] = "getThreadInfos";
                break;
            case 7:
                objArr[2] = "isEDT";
                break;
            case 8:
            case 9:
                objArr[2] = "dumpThreadInfos";
                break;
            case 10:
                objArr[2] = "sort";
                break;
            case 12:
            case 13:
                objArr[2] = "dumpThreadInfo";
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "dumpCallStack";
                break;
            case 20:
            case 21:
                objArr[2] = "printStackTrace";
                break;
            case 22:
            case 23:
                objArr[2] = "getEdtStackForCrash";
                break;
            case 24:
                objArr[2] = "getReadableState";
                break;
            default:
                objArr[2] = "dumpThreadsToString";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 6 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ int lambda$static$2(ThreadInfo threadInfo) {
        return threadInfo.getStackTrace().length;
    }

    public static String dumpThreadsToString() {
        return dumpThreadsToString(getThreadInfos());
    }

    public static String dumpThreadsToString(ThreadInfo[] threadInfoArr) {
        if (threadInfoArr == null) {
            $$$reportNull$$$0(0);
        }
        StringWriter stringWriter = new StringWriter();
        dumpThreadInfos(threadInfoArr, stringWriter);
        String string = stringWriter.toString();
        if (string == null) {
            $$$reportNull$$$0(1);
        }
        return string;
    }

    public static ThreadInfo[] getThreadInfos() {
        return getThreadInfos(ManagementFactory.getThreadMXBean(), true);
    }

    public static ThreadInfo[] getThreadInfos(ThreadMXBean threadMXBean, boolean z) {
        ThreadInfo[] threadInfo;
        if (threadMXBean == null) {
            $$$reportNull$$$0(5);
        }
        try {
            threadInfo = threadMXBean.dumpAllThreads(false, false);
        } catch (Exception unused) {
            threadInfo = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE);
        }
        if (z) {
            sort(threadInfo);
        }
        if (threadInfo == null) {
            $$$reportNull$$$0(6);
        }
        return threadInfo;
    }

    public static boolean isEDT(ThreadInfo threadInfo) {
        if (threadInfo == null) {
            $$$reportNull$$$0(7);
        }
        return isEDT(threadInfo.getThreadName());
    }

    public static boolean isEDT(String str) {
        return str != null && str.startsWith("AWT-EventQueue");
    }

    private static StackTraceElement[] dumpThreadInfos(ThreadInfo[] threadInfoArr, Writer writer) {
        if (writer == null) {
            $$$reportNull$$$0(8);
        }
        if (threadInfoArr == null) {
            $$$reportNull$$$0(9);
        }
        StackTraceElement[] stackTrace = null;
        for (ThreadInfo threadInfo : threadInfoArr) {
            if (threadInfo != null) {
                if (isEDT(threadInfo)) {
                    stackTrace = threadInfo.getStackTrace();
                }
                dumpThreadInfo(threadInfo, writer);
            }
        }
        return stackTrace;
    }

    public static ThreadInfo[] sort(ThreadInfo[] threadInfoArr) {
        if (threadInfoArr == null) {
            $$$reportNull$$$0(10);
        }
        Arrays.sort(threadInfoArr, THREAD_INFO_COMPARATOR);
        if (threadInfoArr == null) {
            $$$reportNull$$$0(11);
        }
        return threadInfoArr;
    }

    private static void dumpThreadInfo(ThreadInfo threadInfo, Writer writer) {
        if (threadInfo == null) {
            $$$reportNull$$$0(12);
        }
        if (writer == null) {
            $$$reportNull$$$0(13);
        }
        dumpCallStack(threadInfo, writer, threadInfo.getStackTrace());
    }

    private static void dumpCallStack(ThreadInfo threadInfo, Writer writer, StackTraceElement[] stackTraceElementArr) {
        if (threadInfo == null) {
            $$$reportNull$$$0(14);
        }
        if (writer == null) {
            $$$reportNull$$$0(15);
        }
        if (stackTraceElementArr == null) {
            $$$reportNull$$$0(16);
        }
        try {
            StringBuilder sbAppend = new StringBuilder("\"").append(threadInfo.getThreadName()).append("\" prio=0 tid=0x0 nid=0x0 ");
            sbAppend.append(getReadableState(threadInfo.getThreadState())).append("\n     java.lang.Thread.State: ");
            sbAppend.append(threadInfo.getThreadState()).append("\n");
            if (threadInfo.getLockName() != null) {
                sbAppend.append(" on ").append(threadInfo.getLockName());
            }
            if (threadInfo.getLockOwnerName() != null) {
                sbAppend.append(" owned by \"").append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
            }
            if (threadInfo.isSuspended()) {
                sbAppend.append(" (suspended)");
            }
            if (threadInfo.isInNative()) {
                sbAppend.append(" (in native)");
            }
            writer.write(((Object) sbAppend) + "\n");
            printStackTrace(writer, stackTraceElementArr);
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dumpCallStack(Thread thread, Writer writer, StackTraceElement[] stackTraceElementArr) {
        if (thread == null) {
            $$$reportNull$$$0(17);
        }
        if (writer == null) {
            $$$reportNull$$$0(18);
        }
        if (stackTraceElementArr == null) {
            $$$reportNull$$$0(19);
        }
        try {
            StringBuilder sbAppend = new StringBuilder("\"").append(thread.getName()).append("\" prio=0 tid=0x0 nid=0x0 ");
            sbAppend.append(getReadableState(thread.getState())).append("\n     java.lang.Thread.State: ");
            sbAppend.append(thread.getState()).append("\n");
            writer.write(((Object) sbAppend) + "\n");
            printStackTrace(writer, stackTraceElementArr);
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printStackTrace(Writer writer, StackTraceElement[] stackTraceElementArr) {
        if (writer == null) {
            $$$reportNull$$$0(20);
        }
        if (stackTraceElementArr == null) {
            $$$reportNull$$$0(21);
        }
        try {
            for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                writer.write("\tat " + stackTraceElement + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.diagnostic.ThreadDumper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$lang$Thread$State;

        static {
            int[] iArr = new int[Thread.State.values().length];
            $SwitchMap$java$lang$Thread$State = iArr;
            try {
                iArr[Thread.State.BLOCKED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$lang$Thread$State[Thread.State.TIMED_WAITING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$lang$Thread$State[Thread.State.WAITING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$lang$Thread$State[Thread.State.RUNNABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$lang$Thread$State[Thread.State.NEW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$lang$Thread$State[Thread.State.TERMINATED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static String getReadableState(Thread.State state) {
        if (state == null) {
            $$$reportNull$$$0(24);
        }
        switch (AnonymousClass1.$SwitchMap$java$lang$Thread$State[state.ordinal()]) {
            case 1:
                return "blocked";
            case 2:
            case 3:
                return "waiting on condition";
            case 4:
                return "runnable";
            case 5:
                return PsiKeyword.NEW;
            case 6:
                return "terminated";
            default:
                return null;
        }
    }
}
