package org.catrobat.catroid.python;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.utils.NativeLibraryManager;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonEngine.class
  classes14.dex
 */
/* JADX INFO: compiled from: PythonEngine.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0002,-B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0015J\u0018\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u0015J\u0010\u0010\u001b\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u0006H\u0007J\t\u0010\u001d\u001a\u00020\u0015H\u0082 J\t\u0010\u001e\u001a\u00020\u0015H\u0082 J\u001c\u0010\u001f\u001a\u00020\u00152\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060!H\u0082 ¢\u0006\u0002\u0010\"J\u0011\u0010#\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0006H\u0082 J5\u0010%\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u00062%\b\u0002\u0010&\u001a\u001f\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b(\u0012\b\b)\u0012\u0004\b\b(*\u0012\u0004\u0012\u00020\u0015\u0018\u00010'J\u0006\u0010+\u001a\u00020\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/catrobat/catroid/python/PythonEngine;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "cleanSlateScript", "", "isInitialized", "", "()Z", "setInitialized", "(Z)V", "isSupportedArchitecture", "loadedNativeLibs", "", "taskQueue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lorg/catrobat/catroid/python/PythonEngine$PythonTask;", "workerThread", "Ljava/lang/Thread;", "clearEnvironment", "", "copyAssets", "assetPath", "destDir", "Ljava/io/File;", "initialize", "loadNativeModule", "filePath", "nativeFinalizePython", "nativeForceStopPythonScript", "nativeInitPython", "modulePaths", "", "([Ljava/lang/String;)V", "nativeRunScript", "script", "runScriptAsync", "onComplete", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "output", "shutdown", "PythonTask", "PythonTask2", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PythonEngine {
    private final String cleanSlateScript;
    private final Context context;
    private boolean isInitialized;
    private boolean isSupportedArchitecture;
    private final Set<String> loadedNativeLibs;
    private final ConcurrentLinkedQueue<PythonTask> taskQueue;
    private Thread workerThread;

    private final native void nativeFinalizePython();

    private final native void nativeForceStopPythonScript();

    private final native void nativeInitPython(String[] modulePaths);

    private final native String nativeRunScript(String script);

    public PythonEngine(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.taskQueue = new ConcurrentLinkedQueue<>();
        this.loadedNativeLibs = new LinkedHashSet();
        Log.d("PythonEngine", "Initializing engine...");
        String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS, "SUPPORTED_ABIS");
        if (ArraysKt.contains(SUPPORTED_ABIS, "arm64-v8a")) {
            this.isSupportedArchitecture = true;
            Log.d("PythonEngine", "Architecture is supported (arm64-v8a). Loading native libraries...");
            String[] SUPPORTED_ABIS2 = Build.SUPPORTED_ABIS;
            Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS2, "SUPPORTED_ABIS");
            boolean zContains = ArraysKt.contains(SUPPORTED_ABIS2, "arm64-v8a");
            this.isSupportedArchitecture = zContains;
            if (!zContains || !NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.PYTHON)) {
                Log.e("PythonEngine", "Python feature is not available. Either unsupported architecture or missing libraries.");
                this.isSupportedArchitecture = false;
            }
        } else {
            this.isSupportedArchitecture = false;
            String[] SUPPORTED_ABIS3 = Build.SUPPORTED_ABIS;
            Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS3, "SUPPORTED_ABIS");
            Log.w("PythonEngine", "!!! WARNING: Unsupported CPU architecture found: " + ArraysKt.joinToString$default(SUPPORTED_ABIS3, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null) + ". Python functionality will be disabled. !!!");
        }
        this.cleanSlateScript = "import sys\nif '_initial_modules' not in globals():\n    _initial_modules = set(sys.modules.keys())\n    _initial_path = list(sys.path)\n    _initial_globals = set(globals().keys())\nelse:\n    modules_to_delete = [name for name in sys.modules if name not in _initial_modules]\n    for mod_name in modules_to_delete:\n        try: del sys.modules[mod_name]\n        except KeyError: pass\n    \n    globals_to_delete = [name for name in globals() if name not in _initial_globals]\n    for var_name in globals_to_delete:\n        try: del globals()[var_name]\n        except KeyError: pass\n    \n    sys.path[:] = _initial_path\nprint(\"--- Python Environment has been reset ---\")";
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonEngine$PythonTask2.class
      classes14.dex
     */
    /* JADX INFO: compiled from: PythonEngine.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/python/PythonEngine$PythonTask2;", "", "script", "", "variableName", "(Ljava/lang/String;Ljava/lang/String;)V", "getScript", "()Ljava/lang/String;", "getVariableName", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class PythonTask2 {
        private final String script;
        private final String variableName;

        public static /* synthetic */ PythonTask2 copy$default(PythonTask2 pythonTask2, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = pythonTask2.script;
            }
            if ((i & 2) != 0) {
                str2 = pythonTask2.variableName;
            }
            return pythonTask2.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getScript() {
            return this.script;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getVariableName() {
            return this.variableName;
        }

        public final PythonTask2 copy(String script, String variableName) {
            Intrinsics.checkNotNullParameter(script, "script");
            return new PythonTask2(script, variableName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PythonTask2)) {
                return false;
            }
            PythonTask2 pythonTask2 = (PythonTask2) other;
            return Intrinsics.areEqual(this.script, pythonTask2.script) && Intrinsics.areEqual(this.variableName, pythonTask2.variableName);
        }

        public int hashCode() {
            int iHashCode = this.script.hashCode() * 31;
            String str = this.variableName;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            return "PythonTask2(script=" + this.script + ", variableName=" + this.variableName + Constant.AFTER_QUTO;
        }

        public PythonTask2(String script, String str) {
            Intrinsics.checkNotNullParameter(script, "script");
            this.script = script;
            this.variableName = str;
        }

        public /* synthetic */ PythonTask2(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : str2);
        }

        public final String getScript() {
            return this.script;
        }

        public final String getVariableName() {
            return this.variableName;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonEngine$PythonTask.class
      classes14.dex
     */
    /* JADX INFO: compiled from: PythonEngine.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B4\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012%\b\u0002\u0010\u0004\u001a\u001f\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005¢\u0006\u0002\u0010\nJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J&\u0010\u0010\u001a\u001f\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005HÆ\u0003J:\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032%\b\u0002\u0010\u0004\u001a\u001f\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R.\u0010\u0004\u001a\u001f\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/python/PythonEngine$PythonTask;", "", "script", "", "onComplete", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "output", "", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getOnComplete", "()Lkotlin/jvm/functions/Function1;", "getScript", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class PythonTask {
        private final Function1<String, Unit> onComplete;
        private final String script;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ PythonTask copy$default(PythonTask pythonTask, String str, Function1 function1, int i, Object obj) {
            if ((i & 1) != 0) {
                str = pythonTask.script;
            }
            if ((i & 2) != 0) {
                function1 = pythonTask.onComplete;
            }
            return pythonTask.copy(str, function1);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getScript() {
            return this.script;
        }

        public final Function1<String, Unit> component2() {
            return this.onComplete;
        }

        public final PythonTask copy(String script, Function1<? super String, Unit> onComplete) {
            Intrinsics.checkNotNullParameter(script, "script");
            return new PythonTask(script, onComplete);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PythonTask)) {
                return false;
            }
            PythonTask pythonTask = (PythonTask) other;
            return Intrinsics.areEqual(this.script, pythonTask.script) && Intrinsics.areEqual(this.onComplete, pythonTask.onComplete);
        }

        public int hashCode() {
            int iHashCode = this.script.hashCode() * 31;
            Function1<String, Unit> function1 = this.onComplete;
            return iHashCode + (function1 == null ? 0 : function1.hashCode());
        }

        public String toString() {
            return "PythonTask(script=" + this.script + ", onComplete=" + this.onComplete + Constant.AFTER_QUTO;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public PythonTask(String script, Function1<? super String, Unit> function1) {
            Intrinsics.checkNotNullParameter(script, "script");
            this.script = script;
            this.onComplete = function1;
        }

        public /* synthetic */ PythonTask(String str, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : function1);
        }

        public final String getScript() {
            return this.script;
        }

        public final Function1<String, Unit> getOnComplete() {
            return this.onComplete;
        }
    }

    /* JADX INFO: renamed from: isInitialized, reason: from getter */
    public final boolean getIsInitialized() {
        return this.isInitialized;
    }

    public final void setInitialized(boolean z) {
        this.isInitialized = z;
    }

    public final void initialize() {
        if (!this.isSupportedArchitecture || this.isInitialized) {
            return;
        }
        Thread thread = new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonEngine$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PythonEngine.initialize$lambda$0(this.f$0);
            }
        });
        thread.setName("PythonWorkerThread");
        thread.start();
        this.workerThread = thread;
        this.isInitialized = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initialize$lambda$0(PythonEngine this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.i("PythonWorker", "Worker thread started.");
        File file = new File(this$0.context.getFilesDir(), "python3.12");
        if (!file.exists()) {
            this$0.copyAssets("python3.12", file);
        }
        File file2 = new File(this$0.context.getFilesDir(), "default_pylibs");
        this$0.copyAssets("default_pylibs", file2);
        this$0.nativeInitPython(new String[]{file.getAbsolutePath(), file2.getAbsolutePath(), new File(file, "lib-dynload").getAbsolutePath()});
        Log.i("PythonWorker", "Python interpreter initialized in worker thread.");
        this$0.nativeRunScript(this$0.cleanSlateScript);
        Log.i("PythonWorker", "Initial Python state recorded.");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                try {
                    PythonTask pythonTaskPoll = this$0.taskQueue.poll();
                    if (pythonTaskPoll != null) {
                        String strNativeRunScript = this$0.nativeRunScript(pythonTaskPoll.getScript());
                        Function1<String, Unit> onComplete = pythonTaskPoll.getOnComplete();
                        if (onComplete != null) {
                            onComplete.invoke(strNativeRunScript);
                        }
                    } else {
                        Thread.sleep(50L);
                    }
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            } catch (Throwable th) {
                this$0.nativeFinalizePython();
                Log.i("PythonWorker", "Python interpreter finalized. Worker thread shutting down.");
                throw th;
            }
        }
        this$0.nativeFinalizePython();
        Log.i("PythonWorker", "Python interpreter finalized. Worker thread shutting down.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void runScriptAsync$default(PythonEngine pythonEngine, String str, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            function1 = null;
        }
        pythonEngine.runScriptAsync(str, function1);
    }

    public final void runScriptAsync(String script, Function1<? super String, Unit> onComplete) {
        Intrinsics.checkNotNullParameter(script, "script");
        if (this.isSupportedArchitecture) {
            if (!this.isInitialized) {
                Log.w("PythonEngine", "Python was not initialized. Initializing now...");
                initialize();
            }
            this.taskQueue.add(new PythonTask(script, onComplete));
        }
    }

    public final void clearEnvironment() {
        if (this.isInitialized) {
            Log.i("PythonEngine", "Clearing environment: stopping script and queueing reset task...");
            nativeForceStopPythonScript();
            runScriptAsync$default(this, this.cleanSlateScript, null, 2, null);
        }
    }

    public final void shutdown() {
        if (!this.isInitialized || this.workerThread == null) {
            return;
        }
        Log.i("PythonEngine", "Shutdown sequence initiated...");
        Thread thread = this.workerThread;
        if (thread != null) {
            thread.interrupt();
        }
        try {
            Thread thread2 = this.workerThread;
            if (thread2 != null) {
                thread2.join(1000L);
            }
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
        this.workerThread = null;
        this.isInitialized = false;
        Log.i("PythonEngine", "Python Engine has been shut down.");
    }

    private final void copyAssets(String assetPath, File destDir) {
        InputStream inputStreamOpen;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        try {
            AssetManager assets = this.context.getAssets();
            String[] list = assets.list(assetPath);
            if (list != null && list.length != 0) {
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                Iterator it = ArrayIteratorKt.iterator(list);
                while (it.hasNext()) {
                    String str = (String) it.next();
                    String str2 = assetPath.length() == 0 ? str : assetPath + "/" + str;
                    File file = new File(destDir, str);
                    String[] list2 = assets.list(str2);
                    try {
                        try {
                            if (list2 != null) {
                                if (!(list2.length == 0)) {
                                    file.mkdirs();
                                    Intrinsics.checkNotNull(str2);
                                    copyAssets(str2, file);
                                }
                            }
                            Intrinsics.checkNotNull(inputStream);
                            ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                            CloseableKt.closeFinally(inputStreamOpen, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                        inputStream = inputStreamOpen;
                        fileOutputStream = new FileOutputStream(file);
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(inputStreamOpen, th3);
                            throw th4;
                        }
                    }
                    inputStreamOpen = assets.open(str2);
                }
                return;
            }
            if (destDir.exists()) {
                return;
            }
            destDir.mkdirs();
        } catch (Exception e) {
            Log.e("PythonEngine", "FATAL ERROR in copyAssets for path: " + assetPath, e);
        }
    }

    public final boolean loadNativeModule(String filePath) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        if (!this.isSupportedArchitecture) {
            Log.w("PythonEngine", "Cannot load native module on unsupported architecture.");
            return false;
        }
        if (this.loadedNativeLibs.add(filePath)) {
            try {
                Log.d("PythonEngine", "Dynamically loading native module: " + filePath);
                System.load(filePath);
                return true;
            } catch (UnsatisfiedLinkError e) {
                Log.e("PythonEngine", "Failed to load native module: " + filePath, e);
                this.loadedNativeLibs.remove(filePath);
                return false;
            }
        }
        Log.d("PythonEngine", "Native module already loaded: " + filePath);
        return true;
    }
}
