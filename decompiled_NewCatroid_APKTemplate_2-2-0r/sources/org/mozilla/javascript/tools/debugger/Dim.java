package org.mozilla.javascript.tools.debugger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.SecurityUtilities;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableObject;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.Debugger;

/* JADX INFO: loaded from: classes6.dex */
public class Dim {
    public static final int BREAK = 4;
    public static final int EXIT = 5;
    public static final int GO = 3;
    private static final int IPROXY_COMPILE_SCRIPT = 2;
    private static final int IPROXY_DEBUG = 0;
    private static final int IPROXY_EVAL_SCRIPT = 3;
    private static final int IPROXY_LISTEN = 1;
    private static final int IPROXY_OBJECT_IDS = 7;
    private static final int IPROXY_OBJECT_PROPERTY = 6;
    private static final int IPROXY_OBJECT_TO_STRING = 5;
    private static final int IPROXY_STRING_IS_COMPILABLE = 4;
    public static final int STEP_INTO = 1;
    public static final int STEP_OUT = 2;
    public static final int STEP_OVER = 0;
    private boolean breakFlag;
    private boolean breakOnEnter;
    private boolean breakOnExceptions;
    private boolean breakOnReturn;
    private GuiCallback callback;
    private ContextFactory contextFactory;
    private StackFrame evalFrame;
    private String evalRequest;
    private String evalResult;
    private boolean insideInterruptLoop;
    private volatile ContextData interruptedContextData;
    private DimIProxy listener;
    private ScopeProvider scopeProvider;
    private SourceProvider sourceProvider;
    private int frameIndex = -1;
    private Object monitor = new Object();
    private Object eventThreadMonitor = new Object();
    private volatile int returnValue = -1;
    private final Map<String, SourceInfo> urlToSourceInfo = Collections.synchronizedMap(new HashMap());
    private final Map<String, FunctionSource> functionNames = Collections.synchronizedMap(new HashMap());
    private final Map<DebuggableScript, FunctionSource> functionToSource = Collections.synchronizedMap(new HashMap());

    public void setGuiCallback(GuiCallback guiCallback) {
        this.callback = guiCallback;
    }

    public void setBreak() {
        this.breakFlag = true;
    }

    public void setScopeProvider(ScopeProvider scopeProvider) {
        this.scopeProvider = scopeProvider;
    }

    public void setSourceProvider(SourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }

    public void contextSwitch(int i) {
        this.frameIndex = i;
    }

    public void setBreakOnExceptions(boolean z) {
        this.breakOnExceptions = z;
    }

    public void setBreakOnEnter(boolean z) {
        this.breakOnEnter = z;
    }

    public void setBreakOnReturn(boolean z) {
        this.breakOnReturn = z;
    }

    public void attachTo(ContextFactory contextFactory) {
        detach();
        this.contextFactory = contextFactory;
        DimIProxy dimIProxy = new DimIProxy(1);
        this.listener = dimIProxy;
        contextFactory.addListener(dimIProxy);
    }

    public void detach() {
        DimIProxy dimIProxy = this.listener;
        if (dimIProxy != null) {
            this.contextFactory.removeListener(dimIProxy);
            this.contextFactory = null;
            this.listener = null;
        }
    }

    public void dispose() {
        detach();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FunctionSource getFunctionSource(DebuggableScript debuggableScript) {
        String strLoadSource;
        FunctionSource functionSource = functionSource(debuggableScript);
        if (functionSource != null) {
            return functionSource;
        }
        String normalizedUrl = getNormalizedUrl(debuggableScript);
        if (sourceInfo(normalizedUrl) != null || debuggableScript.isGeneratedScript() || (strLoadSource = loadSource(normalizedUrl)) == null) {
            return functionSource;
        }
        DebuggableScript debuggableScript2 = debuggableScript;
        while (true) {
            DebuggableScript parent = debuggableScript2.getParent();
            if (parent == null) {
                registerTopScript(debuggableScript2, strLoadSource);
                return functionSource(debuggableScript);
            }
            debuggableScript2 = parent;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0047 A[Catch: SecurityException -> 0x0059, IOException -> 0x00ad, TryCatch #1 {SecurityException -> 0x0059, blocks: (B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:15:0x0047, B:17:0x0052), top: B:38:0x001c }] */
    /* JADX WARN: Code duplicated, block: B:17:0x0052 A[Catch: SecurityException -> 0x0059, IOException -> 0x00ad, TRY_LEAVE, TryCatch #1 {SecurityException -> 0x0059, blocks: (B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:15:0x0047, B:17:0x0052), top: B:38:0x001c }] */
    /* JADX WARN: Code duplicated, block: B:19:0x0059 A[Catch: IOException -> 0x00ad, PHI: r0 r1 r2 r4 r9
      0x0059: PHI (r0v2 java.lang.String) = (r0v0 ??), (r0v8 ??) binds: [B:16:0x0050, B:35:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r1v2 java.lang.String) = (r1v0 java.lang.String), (r1v4 java.lang.String) binds: [B:16:0x0050, B:35:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r2v3 java.lang.String) = (r2v0 java.lang.String), (r2v6 java.lang.String) binds: [B:16:0x0050, B:35:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r4v3 java.lang.String) = (r4v0 java.lang.String), (r4v4 java.lang.String) binds: [B:16:0x0050, B:35:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x0059: PHI (r9v7 java.lang.String) = (r9v1 java.lang.String), (r9v11 java.lang.String) binds: [B:16:0x0050, B:35:0x0059] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #2 {IOException -> 0x00ad, blocks: (B:6:0x0016, B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:28:0x00a4, B:30:0x00a9, B:31:0x00ac, B:15:0x0047, B:17:0x0052, B:26:0x0092, B:19:0x0059, B:21:0x0061, B:22:0x006f, B:24:0x0077, B:25:0x0085, B:27:0x009b), top: B:39:0x0016, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0061 A[Catch: IOException -> 0x00ad, TryCatch #2 {IOException -> 0x00ad, blocks: (B:6:0x0016, B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:28:0x00a4, B:30:0x00a9, B:31:0x00ac, B:15:0x0047, B:17:0x0052, B:26:0x0092, B:19:0x0059, B:21:0x0061, B:22:0x006f, B:24:0x0077, B:25:0x0085, B:27:0x009b), top: B:39:0x0016, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:22:0x006f A[Catch: IOException -> 0x00ad, TryCatch #2 {IOException -> 0x00ad, blocks: (B:6:0x0016, B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:28:0x00a4, B:30:0x00a9, B:31:0x00ac, B:15:0x0047, B:17:0x0052, B:26:0x0092, B:19:0x0059, B:21:0x0061, B:22:0x006f, B:24:0x0077, B:25:0x0085, B:27:0x009b), top: B:39:0x0016, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x0077 A[Catch: IOException -> 0x00ad, TryCatch #2 {IOException -> 0x00ad, blocks: (B:6:0x0016, B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:28:0x00a4, B:30:0x00a9, B:31:0x00ac, B:15:0x0047, B:17:0x0052, B:26:0x0092, B:19:0x0059, B:21:0x0061, B:22:0x006f, B:24:0x0077, B:25:0x0085, B:27:0x009b), top: B:39:0x0016, inners: #0 }] */
    /* JADX WARN: Code duplicated, block: B:25:0x0085 A[Catch: IOException -> 0x00ad, TryCatch #2 {IOException -> 0x00ad, blocks: (B:6:0x0016, B:8:0x001c, B:10:0x0024, B:12:0x002c, B:14:0x0041, B:28:0x00a4, B:30:0x00a9, B:31:0x00ac, B:15:0x0047, B:17:0x0052, B:26:0x0092, B:19:0x0059, B:21:0x0061, B:22:0x006f, B:24:0x0077, B:25:0x0085, B:27:0x009b), top: B:39:0x0016, inners: #0 }] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.InputStreamReader, java.io.Reader] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0061 -> B:26:0x0092). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:24:0x0077 -> B:26:0x0092). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0085 -> B:26:0x0092). Please report as a decompilation issue!!! */
    private String loadSource(String str) {
        File file;
        InputStream fileInputStream;
        String systemProperty;
        boolean zStartsWith;
        String inputStreamReader = "http://127.0.0.1";
        String str2 = "http://";
        String str3 = "http:";
        int iIndexOf = str.indexOf(35);
        if (iIndexOf >= 0) {
            str = str.substring(0, iIndexOf);
        }
        String reader = null;
        try {
            ?? r1 = str2;
            ?? r2 = str3;
            if (str.indexOf(58) < 0) {
                try {
                    if (str.startsWith("~/") && (systemProperty = SecurityUtilities.getSystemProperty("user.home")) != null) {
                        File file2 = new File(new File(systemProperty), str.substring(2));
                        if (file2.exists()) {
                            fileInputStream = new FileInputStream(file2);
                            str2 = str2;
                            str3 = str3;
                        } else {
                            file = new File(str);
                            if (file.exists()) {
                                fileInputStream = new FileInputStream(file);
                                str2 = str2;
                                str3 = str3;
                            } else {
                                if (str.startsWith("//")) {
                                    str = str3 + str;
                                    r1 = str2;
                                    r2 = str3;
                                } else {
                                    zStartsWith = str.startsWith("/");
                                    if (zStartsWith) {
                                        StringBuilder sb = new StringBuilder(inputStreamReader);
                                        str = sb.append(str).toString();
                                        r1 = sb;
                                        r2 = zStartsWith;
                                    } else {
                                        str = str2 + str;
                                        r1 = str2;
                                        r2 = zStartsWith;
                                    }
                                }
                                fileInputStream = new URL(str).openStream();
                                str2 = r1;
                                str3 = r2;
                            }
                        }
                    } else {
                        file = new File(str);
                        if (file.exists()) {
                            fileInputStream = new FileInputStream(file);
                            str2 = str2;
                            str3 = str3;
                        } else {
                            if (str.startsWith("//")) {
                                str = str3 + str;
                                r1 = str2;
                                r2 = str3;
                            } else {
                                zStartsWith = str.startsWith("/");
                                if (zStartsWith) {
                                    StringBuilder sb2 = new StringBuilder(inputStreamReader);
                                    str = sb2.append(str).toString();
                                    r1 = sb2;
                                    r2 = zStartsWith;
                                } else {
                                    str = str2 + str;
                                    r1 = str2;
                                    r2 = zStartsWith;
                                }
                            }
                            fileInputStream = new URL(str).openStream();
                            str2 = r1;
                            str3 = r2;
                        }
                    }
                } catch (SecurityException unused) {
                }
            } else {
                fileInputStream = new URL(str).openStream();
                str2 = r1;
                str3 = r2;
            }
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
                reader = Kit.readReader(inputStreamReader);
            } finally {
                fileInputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Failed to load source from " + str + ": " + e);
        }
        return reader;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerTopScript(DebuggableScript debuggableScript, String str) {
        int i;
        String source;
        if (!debuggableScript.isTopLevel()) {
            throw new IllegalArgumentException();
        }
        String normalizedUrl = getNormalizedUrl(debuggableScript);
        DebuggableScript[] allFunctions = getAllFunctions(debuggableScript);
        SourceProvider sourceProvider = this.sourceProvider;
        if (sourceProvider != null && (source = sourceProvider.getSource(debuggableScript)) != null) {
            str = source;
        }
        SourceInfo sourceInfo = new SourceInfo(str, allFunctions, normalizedUrl);
        synchronized (this.urlToSourceInfo) {
            SourceInfo sourceInfo2 = this.urlToSourceInfo.get(normalizedUrl);
            if (sourceInfo2 != null) {
                sourceInfo.copyBreakpointsFrom(sourceInfo2);
            }
            this.urlToSourceInfo.put(normalizedUrl, sourceInfo);
            for (int i2 = 0; i2 != sourceInfo.functionSourcesTop(); i2++) {
                FunctionSource functionSource = sourceInfo.functionSource(i2);
                String strName = functionSource.name();
                if (strName.length() != 0) {
                    this.functionNames.put(strName, functionSource);
                }
            }
        }
        synchronized (this.functionToSource) {
            for (i = 0; i != allFunctions.length; i++) {
                this.functionToSource.put(allFunctions[i], sourceInfo.functionSource(i));
            }
        }
        this.callback.updateSourceText(sourceInfo);
    }

    private FunctionSource functionSource(DebuggableScript debuggableScript) {
        return this.functionToSource.get(debuggableScript);
    }

    public String[] functionNames() {
        String[] strArr;
        synchronized (this.urlToSourceInfo) {
            strArr = (String[]) this.functionNames.keySet().toArray(new String[this.functionNames.size()]);
        }
        return strArr;
    }

    public FunctionSource functionSourceByName(String str) {
        return this.functionNames.get(str);
    }

    public SourceInfo sourceInfo(String str) {
        return this.urlToSourceInfo.get(str);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003e  */
    private String getNormalizedUrl(DebuggableScript debuggableScript) {
        String str;
        String sourceName = debuggableScript.getSourceName();
        if (sourceName == null) {
            return "<stdin>";
        }
        int length = sourceName.length();
        StringBuilder sb = null;
        int i = 0;
        while (true) {
            int iIndexOf = sourceName.indexOf(35, i);
            if (iIndexOf < 0) {
                break;
            }
            int i2 = iIndexOf + 1;
            int i3 = i2;
            while (i3 != length) {
                char cCharAt = sourceName.charAt(i3);
                if ('0' > cCharAt || cCharAt > '9') {
                    break;
                }
                i3++;
            }
            if (i3 != i2) {
                str = "(eval)";
                if ("(eval)".regionMatches(0, sourceName, i3, 6)) {
                    i = i3 + 6;
                } else {
                    str = null;
                }
            } else {
                str = null;
            }
            if (str == null) {
                break;
            }
            if (sb == null) {
                sb = new StringBuilder();
                sb.append(sourceName.substring(0, iIndexOf));
            }
            sb.append(str);
        }
        if (sb == null) {
            return sourceName;
        }
        if (i != length) {
            sb.append(sourceName.substring(i));
        }
        return sb.toString();
    }

    private static DebuggableScript[] getAllFunctions(DebuggableScript debuggableScript) {
        ObjArray objArray = new ObjArray();
        collectFunctions_r(debuggableScript, objArray);
        DebuggableScript[] debuggableScriptArr = new DebuggableScript[objArray.size()];
        objArray.toArray(debuggableScriptArr);
        return debuggableScriptArr;
    }

    private static void collectFunctions_r(DebuggableScript debuggableScript, ObjArray objArray) {
        objArray.add(debuggableScript);
        for (int i = 0; i != debuggableScript.getFunctionCount(); i++) {
            collectFunctions_r(debuggableScript.getFunction(i), objArray);
        }
    }

    public void clearAllBreakpoints() {
        Iterator<SourceInfo> it = this.urlToSourceInfo.values().iterator();
        while (it.hasNext()) {
            it.next().removeAllBreakpoints();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBreakpointHit(StackFrame stackFrame, Context context) {
        this.breakFlag = false;
        interrupted(context, stackFrame, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleExceptionThrown(Context context, Throwable th, StackFrame stackFrame) {
        if (this.breakOnExceptions) {
            ContextData contextData = stackFrame.contextData();
            if (contextData.lastProcessedException != th) {
                interrupted(context, stackFrame, th);
                contextData.lastProcessedException = th;
            }
        }
    }

    public ContextData currentContextData() {
        return this.interruptedContextData;
    }

    public void setReturnValue(int i) {
        synchronized (this.monitor) {
            this.returnValue = i;
            this.monitor.notify();
        }
    }

    public void go() {
        synchronized (this.monitor) {
            this.returnValue = 3;
            this.monitor.notifyAll();
        }
    }

    public String eval(String str) {
        ContextData contextDataCurrentContextData;
        String str2 = "undefined";
        if (str == null || (contextDataCurrentContextData = currentContextData()) == null || this.frameIndex >= contextDataCurrentContextData.frameCount()) {
            return "undefined";
        }
        StackFrame frame = contextDataCurrentContextData.getFrame(this.frameIndex);
        if (contextDataCurrentContextData.eventThreadFlag) {
            return do_eval(Context.getCurrentContext(), frame, str);
        }
        synchronized (this.monitor) {
            if (this.insideInterruptLoop) {
                this.evalRequest = str;
                this.evalFrame = frame;
                this.monitor.notify();
                do {
                    try {
                        this.monitor.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                } while (this.evalRequest != null);
                str2 = this.evalResult;
            }
        }
        return str2;
    }

    public void compileScript(String str, String str2) {
        DimIProxy dimIProxy = new DimIProxy(2);
        dimIProxy.url = str;
        dimIProxy.text = str2;
        dimIProxy.withContext();
    }

    public void evalScript(String str, String str2) {
        DimIProxy dimIProxy = new DimIProxy(3);
        dimIProxy.url = str;
        dimIProxy.text = str2;
        dimIProxy.withContext();
    }

    public String objectToString(Object obj) {
        DimIProxy dimIProxy = new DimIProxy(5);
        dimIProxy.object = obj;
        dimIProxy.withContext();
        return dimIProxy.stringResult;
    }

    public boolean stringIsCompilableUnit(String str) {
        DimIProxy dimIProxy = new DimIProxy(4);
        dimIProxy.text = str;
        dimIProxy.withContext();
        return dimIProxy.booleanResult;
    }

    public Object getObjectProperty(Object obj, Object obj2) {
        DimIProxy dimIProxy = new DimIProxy(6);
        dimIProxy.object = obj;
        dimIProxy.f1070id = obj2;
        dimIProxy.withContext();
        return dimIProxy.objectResult;
    }

    public Object[] getObjectIds(Object obj) {
        DimIProxy dimIProxy = new DimIProxy(7);
        dimIProxy.object = obj;
        dimIProxy.withContext();
        return dimIProxy.objectArrayResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object getObjectPropertyImpl(Context context, Object obj, Object obj2) {
        Scriptable scriptable = (Scriptable) obj;
        if (obj2 instanceof String) {
            String str = (String) obj2;
            if (str.equals("this")) {
                return scriptable;
            }
            if (str.equals("__proto__")) {
                return scriptable.getPrototype();
            }
            if (str.equals("__parent__")) {
                return scriptable.getParentScope();
            }
            Object property = ScriptableObject.getProperty(scriptable, str);
            return property == ScriptableObject.NOT_FOUND ? Undefined.instance : property;
        }
        Object property2 = ScriptableObject.getProperty(scriptable, ((Integer) obj2).intValue());
        return property2 == ScriptableObject.NOT_FOUND ? Undefined.instance : property2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object[] getObjectIdsImpl(Context context, Object obj) {
        Object[] ids;
        if (!(obj instanceof Scriptable) || obj == Undefined.instance) {
            return Context.emptyArgs;
        }
        Scriptable scriptable = (Scriptable) obj;
        if (scriptable instanceof DebuggableObject) {
            ids = ((DebuggableObject) scriptable).getAllIds();
        } else {
            ids = scriptable.getIds();
        }
        Scriptable prototype = scriptable.getPrototype();
        Scriptable parentScope = scriptable.getParentScope();
        char c = 1;
        int i = prototype != null ? 1 : 0;
        if (parentScope != null) {
            i++;
        }
        if (i == 0) {
            return ids;
        }
        Object[] objArr = new Object[ids.length + i];
        System.arraycopy(ids, 0, objArr, i, ids.length);
        if (prototype != null) {
            objArr[0] = "__proto__";
        } else {
            c = 0;
        }
        if (parentScope != null) {
            objArr[c] = "__parent__";
        }
        return objArr;
    }

    private void interrupted(Context context, StackFrame stackFrame, Throwable th) {
        int i;
        ContextData contextData = stackFrame.contextData();
        boolean zIsGuiEventThread = this.callback.isGuiEventThread();
        contextData.eventThreadFlag = zIsGuiEventThread;
        synchronized (this.eventThreadMonitor) {
            if (zIsGuiEventThread) {
                if (this.interruptedContextData != null) {
                    return;
                }
            } else {
                while (this.interruptedContextData != null) {
                    try {
                        this.eventThreadMonitor.wait();
                    } catch (InterruptedException unused) {
                        return;
                    }
                }
            }
            this.interruptedContextData = contextData;
            if (this.interruptedContextData == null) {
                Kit.codeBug();
            }
            try {
                this.frameIndex = contextData.frameCount() - 1;
                String string = Thread.currentThread().toString();
                String string2 = th == null ? null : th.toString();
                if (!zIsGuiEventThread) {
                    synchronized (this.monitor) {
                        if (this.insideInterruptLoop) {
                            Kit.codeBug();
                        }
                        this.insideInterruptLoop = true;
                        this.evalRequest = null;
                        this.returnValue = -1;
                        this.callback.enterInterrupt(stackFrame, string, string2);
                        while (true) {
                            try {
                                try {
                                    this.monitor.wait();
                                    String str = this.evalRequest;
                                    if (str != null) {
                                        this.evalResult = null;
                                        try {
                                            this.evalResult = do_eval(context, this.evalFrame, str);
                                            this.evalRequest = null;
                                            this.evalFrame = null;
                                            this.monitor.notify();
                                        } catch (Throwable th2) {
                                            this.evalRequest = null;
                                            this.evalFrame = null;
                                            this.monitor.notify();
                                            throw th2;
                                        }
                                    } else if (this.returnValue != -1) {
                                        break;
                                    }
                                } catch (InterruptedException unused2) {
                                    Thread.currentThread().interrupt();
                                    i = -1;
                                }
                            } catch (Throwable th3) {
                                this.insideInterruptLoop = false;
                                throw th3;
                            }
                        }
                        i = this.returnValue;
                        this.insideInterruptLoop = false;
                    }
                } else {
                    this.returnValue = -1;
                    this.callback.enterInterrupt(stackFrame, string, string2);
                    while (this.returnValue == -1) {
                        try {
                            this.callback.dispatchNextGuiEvent();
                        } catch (InterruptedException unused3) {
                        }
                    }
                    i = this.returnValue;
                }
                if (i == 0) {
                    contextData.breakNextLine = true;
                    contextData.stopAtFrameDepth = contextData.frameCount();
                } else if (i != 1) {
                    if (i == 2 && contextData.frameCount() > 1) {
                        contextData.breakNextLine = true;
                        contextData.stopAtFrameDepth = contextData.frameCount() - 1;
                    }
                } else {
                    contextData.breakNextLine = true;
                    contextData.stopAtFrameDepth = -1;
                }
                synchronized (this.eventThreadMonitor) {
                    this.interruptedContextData = null;
                    this.eventThreadMonitor.notifyAll();
                }
            } catch (Throwable th4) {
                synchronized (this.eventThreadMonitor) {
                    this.interruptedContextData = null;
                    this.eventThreadMonitor.notifyAll();
                    throw th4;
                }
            }
        }
    }

    private static String do_eval(Context context, StackFrame stackFrame, String str) {
        String message = "";
        Debugger debugger = context.getDebugger();
        Object debuggerContextData = context.getDebuggerContextData();
        int optimizationLevel = context.getOptimizationLevel();
        context.setDebugger(null, null);
        context.setOptimizationLevel(-1);
        context.setGeneratingDebug(false);
        try {
            try {
                Object objCall = ((Callable) context.compileString(str, "", 0, null)).call(context, stackFrame.scope, stackFrame.thisObj, ScriptRuntime.emptyArgs);
                if (objCall != Undefined.instance) {
                    message = ScriptRuntime.toString(objCall);
                }
            } catch (Exception e) {
                message = e.getMessage();
            }
            context.setGeneratingDebug(true);
            context.setOptimizationLevel(optimizationLevel);
            context.setDebugger(debugger, debuggerContextData);
            return message == null ? "null" : message;
        } catch (Throwable th) {
            context.setGeneratingDebug(true);
            context.setOptimizationLevel(optimizationLevel);
            context.setDebugger(debugger, debuggerContextData);
            throw th;
        }
    }

    private static class DimIProxy implements ContextAction, ContextFactory.Listener, Debugger {
        private boolean booleanResult;
        private Dim dim;

        /* JADX INFO: renamed from: id, reason: collision with root package name */
        private Object f1070id;
        private Object object;
        private Object[] objectArrayResult;
        private Object objectResult;
        private String stringResult;
        private String text;
        private int type;
        private String url;

        private DimIProxy(Dim dim, int i) {
            this.dim = dim;
            this.type = i;
        }

        @Override // org.mozilla.javascript.ContextAction
        public Object run(Context context) {
            switch (this.type) {
                case 2:
                    context.compileString(this.text, this.url, 1, null);
                    return null;
                case 3:
                    Scriptable scope = this.dim.scopeProvider != null ? this.dim.scopeProvider.getScope() : null;
                    if (scope == null) {
                        scope = new ImporterTopLevel(context);
                    }
                    context.evaluateString(scope, this.text, this.url, 1, null);
                    return null;
                case 4:
                    this.booleanResult = context.stringIsCompilableUnit(this.text);
                    return null;
                case 5:
                    if (this.object == Undefined.instance) {
                        this.stringResult = "undefined";
                    } else {
                        Object obj = this.object;
                        if (obj == null) {
                            this.stringResult = "null";
                        } else if (obj instanceof NativeCall) {
                            this.stringResult = "[object Call]";
                        } else {
                            this.stringResult = Context.toString(obj);
                        }
                    }
                    return null;
                case 6:
                    this.objectResult = this.dim.getObjectPropertyImpl(context, this.object, this.f1070id);
                    return null;
                case 7:
                    this.objectArrayResult = this.dim.getObjectIdsImpl(context, this.object);
                    return null;
                default:
                    throw Kit.codeBug();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void withContext() {
            this.dim.contextFactory.call(this);
        }

        @Override // org.mozilla.javascript.ContextFactory.Listener
        public void contextCreated(Context context) {
            if (this.type != 1) {
                Kit.codeBug();
            }
            context.setDebugger(new DimIProxy(this.dim, 0), new ContextData());
            context.setGeneratingDebug(true);
            context.setOptimizationLevel(-1);
        }

        @Override // org.mozilla.javascript.ContextFactory.Listener
        public void contextReleased(Context context) {
            if (this.type != 1) {
                Kit.codeBug();
            }
        }

        @Override // org.mozilla.javascript.debug.Debugger
        public DebugFrame getFrame(Context context, DebuggableScript debuggableScript) {
            if (this.type != 0) {
                Kit.codeBug();
            }
            FunctionSource functionSource = this.dim.getFunctionSource(debuggableScript);
            if (functionSource == null) {
                return null;
            }
            return new StackFrame(context, this.dim, functionSource);
        }

        @Override // org.mozilla.javascript.debug.Debugger
        public void handleCompilationDone(Context context, DebuggableScript debuggableScript, String str) {
            if (this.type != 0) {
                Kit.codeBug();
            }
            if (debuggableScript.isTopLevel()) {
                this.dim.registerTopScript(debuggableScript, str);
            }
        }
    }

    public static class ContextData {
        private boolean breakNextLine;
        private boolean eventThreadFlag;
        private Throwable lastProcessedException;
        private ObjArray frameStack = new ObjArray();
        private int stopAtFrameDepth = -1;

        public static ContextData get(Context context) {
            return (ContextData) context.getDebuggerContextData();
        }

        public int frameCount() {
            return this.frameStack.size();
        }

        public StackFrame getFrame(int i) {
            return (StackFrame) this.frameStack.get((this.frameStack.size() - i) - 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void pushFrame(StackFrame stackFrame) {
            this.frameStack.push(stackFrame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void popFrame() {
            this.frameStack.pop();
        }
    }

    public static class StackFrame implements DebugFrame {
        private boolean[] breakpoints;
        private ContextData contextData;
        private Dim dim;
        private FunctionSource fsource;
        private int lineNumber;
        private Scriptable scope;
        private Scriptable thisObj;

        private StackFrame(Context context, Dim dim, FunctionSource functionSource) {
            this.dim = dim;
            this.contextData = ContextData.get(context);
            this.fsource = functionSource;
            this.breakpoints = functionSource.sourceInfo().breakpoints;
            this.lineNumber = functionSource.firstLine();
        }

        @Override // org.mozilla.javascript.debug.DebugFrame
        public void onEnter(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
            this.contextData.pushFrame(this);
            this.scope = scriptable;
            this.thisObj = scriptable2;
            if (this.dim.breakOnEnter) {
                this.dim.handleBreakpointHit(this, context);
            }
        }

        @Override // org.mozilla.javascript.debug.DebugFrame
        public void onLineChange(Context context, int i) {
            this.lineNumber = i;
            if (!this.breakpoints[i] && !this.dim.breakFlag) {
                boolean z = this.contextData.breakNextLine;
                if (z && this.contextData.stopAtFrameDepth >= 0) {
                    z = this.contextData.frameCount() <= this.contextData.stopAtFrameDepth;
                }
                if (!z) {
                    return;
                }
                this.contextData.stopAtFrameDepth = -1;
                this.contextData.breakNextLine = false;
            }
            this.dim.handleBreakpointHit(this, context);
        }

        @Override // org.mozilla.javascript.debug.DebugFrame
        public void onExceptionThrown(Context context, Throwable th) {
            this.dim.handleExceptionThrown(context, th, this);
        }

        @Override // org.mozilla.javascript.debug.DebugFrame
        public void onExit(Context context, boolean z, Object obj) {
            if (this.dim.breakOnReturn && !z) {
                this.dim.handleBreakpointHit(this, context);
            }
            this.contextData.popFrame();
        }

        @Override // org.mozilla.javascript.debug.DebugFrame
        public void onDebuggerStatement(Context context) {
            this.dim.handleBreakpointHit(this, context);
        }

        public SourceInfo sourceInfo() {
            return this.fsource.sourceInfo();
        }

        public ContextData contextData() {
            return this.contextData;
        }

        public Object scope() {
            return this.scope;
        }

        public Object thisObj() {
            return this.thisObj;
        }

        public String getUrl() {
            return this.fsource.sourceInfo().url();
        }

        public int getLineNumber() {
            return this.lineNumber;
        }

        public String getFunctionName() {
            return this.fsource.name();
        }
    }

    public static class FunctionSource {
        private int firstLine;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private String f1071name;
        private SourceInfo sourceInfo;

        private FunctionSource(SourceInfo sourceInfo, int i, String str) {
            if (str == null) {
                throw new IllegalArgumentException();
            }
            this.sourceInfo = sourceInfo;
            this.firstLine = i;
            this.f1071name = str;
        }

        public SourceInfo sourceInfo() {
            return this.sourceInfo;
        }

        public int firstLine() {
            return this.firstLine;
        }

        public String name() {
            return this.f1071name;
        }
    }

    public static class SourceInfo {
        private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
        private boolean[] breakableLines;
        private boolean[] breakpoints;
        private FunctionSource[] functionSources;
        private String source;
        private String url;

        private SourceInfo(String str, DebuggableScript[] debuggableScriptArr, String str2) {
            this.source = str;
            this.url = str2;
            int length = debuggableScriptArr.length;
            int[][] iArr = new int[length][];
            for (int i = 0; i != length; i++) {
                iArr[i] = debuggableScriptArr[i].getLineNumbers();
            }
            int[] iArr2 = new int[length];
            int i2 = 0;
            int i3 = 0;
            int i4 = -1;
            while (true) {
                if (i2 == length) {
                    break;
                }
                int[] iArr3 = iArr[i2];
                if (iArr3 == null || iArr3.length == 0) {
                    iArr2[i2] = -1;
                } else {
                    int i5 = iArr3[0];
                    int i6 = i5;
                    for (int i7 = 1; i7 != iArr3.length; i7++) {
                        int i8 = iArr3[i7];
                        if (i8 < i5) {
                            i5 = i8;
                        } else if (i8 > i6) {
                            i6 = i8;
                        }
                    }
                    iArr2[i2] = i5;
                    if (i3 > i4) {
                        i3 = i5;
                    } else {
                        i3 = i5 < i3 ? i5 : i3;
                        if (i6 > i4) {
                        }
                    }
                    i4 = i6;
                }
                i2++;
            }
            if (i3 > i4) {
                boolean[] zArr = EMPTY_BOOLEAN_ARRAY;
                this.breakableLines = zArr;
                this.breakpoints = zArr;
            } else {
                if (i3 < 0) {
                    throw new IllegalStateException(String.valueOf(i3));
                }
                int i9 = i4 + 1;
                this.breakableLines = new boolean[i9];
                this.breakpoints = new boolean[i9];
                for (int i10 = 0; i10 != length; i10++) {
                    int[] iArr4 = iArr[i10];
                    if (iArr4 != null && iArr4.length != 0) {
                        for (int i11 = 0; i11 != iArr4.length; i11++) {
                            this.breakableLines[iArr4[i11]] = true;
                        }
                    }
                }
            }
            this.functionSources = new FunctionSource[length];
            for (int i12 = 0; i12 != length; i12++) {
                String functionName = debuggableScriptArr[i12].getFunctionName();
                if (functionName == null) {
                    functionName = "";
                }
                this.functionSources[i12] = new FunctionSource(this, iArr2[i12], functionName);
            }
        }

        public String source() {
            return this.source;
        }

        public String url() {
            return this.url;
        }

        public int functionSourcesTop() {
            return this.functionSources.length;
        }

        public FunctionSource functionSource(int i) {
            return this.functionSources[i];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copyBreakpointsFrom(SourceInfo sourceInfo) {
            int length = sourceInfo.breakpoints.length;
            boolean[] zArr = this.breakpoints;
            if (length > zArr.length) {
                length = zArr.length;
            }
            for (int i = 0; i != length; i++) {
                if (sourceInfo.breakpoints[i]) {
                    this.breakpoints[i] = true;
                }
            }
        }

        public boolean breakableLine(int i) {
            boolean[] zArr = this.breakableLines;
            return i < zArr.length && zArr[i];
        }

        public boolean breakpoint(int i) {
            if (!breakableLine(i)) {
                throw new IllegalArgumentException(String.valueOf(i));
            }
            boolean[] zArr = this.breakpoints;
            return i < zArr.length && zArr[i];
        }

        public boolean breakpoint(int i, boolean z) {
            boolean z2;
            if (!breakableLine(i)) {
                throw new IllegalArgumentException(String.valueOf(i));
            }
            synchronized (this.breakpoints) {
                boolean[] zArr = this.breakpoints;
                if (zArr[i] != z) {
                    zArr[i] = z;
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
            return z2;
        }

        public void removeAllBreakpoints() {
            synchronized (this.breakpoints) {
                int i = 0;
                while (true) {
                    boolean[] zArr = this.breakpoints;
                    if (i != zArr.length) {
                        zArr[i] = false;
                        i++;
                    }
                }
            }
        }
    }
}
