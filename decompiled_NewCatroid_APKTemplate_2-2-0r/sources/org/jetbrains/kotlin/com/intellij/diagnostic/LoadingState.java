package org.jetbrains.kotlin.com.intellij.diagnostic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes3.dex */
public enum LoadingState {
    BOOTSTRAP("bootstrap"),
    LAF_INITIALIZED("LaF is initialized"),
    COMPONENTS_REGISTERED("app component registered"),
    CONFIGURATION_STORE_INITIALIZED("app store initialized"),
    COMPONENTS_LOADED("app component loaded"),
    APP_STARTED("app started"),
    PROJECT_OPENED("project opened");

    private static boolean CHECK_LOADING_PHASE;
    public static BiConsumer<String, Throwable> errorHandler;
    private static Set<ThrowableWrapper> stackTraces;
    final String displayName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "displayName";
        } else {
            objArr[0] = "currentState";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/diagnostic/LoadingState";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "logStateError";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    LoadingState(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.displayName = str;
    }

    public void checkOccurred() {
        if (CHECK_LOADING_PHASE) {
            LoadingState loadingState = StartUpMeasurer.currentState.get();
            if (loadingState.compareTo(this) >= 0 || isKnownViolator()) {
                return;
            }
            logStateError(loadingState);
        }
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0027 A[DONT_GENERATE] */
    /* JADX WARN: Code duplicated, block: B:14:0x0029 A[Catch: all -> 0x005f, TRY_ENTER, TryCatch #0 {, blocks: (B:6:0x0006, B:7:0x0009, B:9:0x0012, B:10:0x0019, B:14:0x0029, B:16:0x002d), top: B:22:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x002d A[Catch: all -> 0x005f, TRY_LEAVE, TryCatch #0 {, blocks: (B:6:0x0006, B:7:0x0009, B:9:0x0012, B:10:0x0019, B:14:0x0029, B:16:0x002d), top: B:22:0x0006 }] */
    /* JADX WARN: Code duplicated, block: B:9:0x0012 A[Catch: all -> 0x005f, TryCatch #0 {, blocks: (B:6:0x0006, B:7:0x0009, B:9:0x0012, B:10:0x0019, B:14:0x0029, B:16:0x002d), top: B:22:0x0006 }] */
    /* JADX WARN: Instruction removed from duplicated block: B:12:0x0027, please report this as an issue */
    private synchronized void logStateError(LoadingState loadingState) {
        Throwable th;
        BiConsumer<String, Throwable> biConsumer;
        if (loadingState == null) {
            $$$reportNull$$$0(1);
            th = new Throwable();
            if (stackTraces == null) {
                stackTraces = new HashSet();
            }
            if (stackTraces.add(new ThrowableWrapper(th))) {
                biConsumer = errorHandler;
                if (biConsumer != null) {
                    biConsumer.accept("Should be called at least in the state " + this + ", the current state is: " + loadingState + "\nCurrent violators count: " + stackTraces.size() + "\n\n", th);
                }
                return;
            }
            return;
        }
        th = new Throwable();
        if (stackTraces == null) {
            stackTraces = new HashSet();
        }
        if (stackTraces.add(new ThrowableWrapper(th))) {
            return;
        }
        biConsumer = errorHandler;
        if (biConsumer != null) {
            biConsumer.accept("Should be called at least in the state " + this + ", the current state is: " + loadingState + "\nCurrent violators count: " + stackTraces.size() + "\n\n", th);
        }
        return;
        throw th;
    }

    private static final class ThrowableWrapper {
        final Throwable throwable;

        private ThrowableWrapper(Throwable th) {
            this.throwable = th;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ThrowableWrapper)) {
                return false;
            }
            Throwable th = ((ThrowableWrapper) obj).throwable;
            Throwable th2 = this.throwable;
            return th2 == th || fingerprint(th2).equals(fingerprint(th));
        }

        public int hashCode() {
            return fingerprint(this.throwable).hashCode();
        }

        private static String fingerprint(Throwable th) {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                sb.append(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName());
            }
            return sb.toString();
        }
    }

    private static boolean isKnownViolator() {
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            String className = stackTraceElement.getClassName();
            if (className.contains("org.jetbrains.kotlin.com.intellij.util.indexing.IndexInfrastructure") || className.contains("org.jetbrains.kotlin.com.intellij.psi.impl.search.IndexPatternSearcher") || className.contains("com.jetbrains.performancePlugin.ProjectLoaded")) {
                return true;
            }
        }
        return false;
    }

    public boolean isOccurred() {
        return StartUpMeasurer.currentState.get().compareTo(this) >= 0;
    }
}
