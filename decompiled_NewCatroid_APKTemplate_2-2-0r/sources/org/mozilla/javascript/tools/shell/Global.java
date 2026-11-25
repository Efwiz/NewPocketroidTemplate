package org.mozilla.javascript.tools.shell;

import androidx.core.app.NotificationCompat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jgit.lib.ConfigConstants;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Synchronizer;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.Wrapper;
import org.mozilla.javascript.commonjs.module.Require;
import org.mozilla.javascript.commonjs.module.RequireBuilder;
import org.mozilla.javascript.commonjs.module.provider.SoftCachingModuleScriptProvider;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;
import org.mozilla.javascript.tools.ToolErrorReporter;

/* JADX INFO: loaded from: classes6.dex */
public class Global extends ImporterTopLevel {
    static final long serialVersionUID = 4029130780977538005L;
    boolean attemptedJLineLoad;
    private ShellConsole console;
    private HashMap<String, String> doctestCanonicalizations;
    private PrintStream errStream;
    NativeArray history;
    private InputStream inStream;
    boolean initialized;
    private PrintStream outStream;
    private QuitAction quitAction;
    private boolean sealedStdLib = false;
    private String[] prompts = {"js> ", "  > "};

    public Global() {
    }

    public Global(Context context) {
        init(context);
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void initQuitAction(QuitAction quitAction) {
        if (quitAction == null) {
            throw new IllegalArgumentException("quitAction is null");
        }
        if (this.quitAction != null) {
            throw new IllegalArgumentException("The method is once-call.");
        }
        this.quitAction = quitAction;
    }

    public void init(ContextFactory contextFactory) {
        contextFactory.call(new ContextAction() { // from class: org.mozilla.javascript.tools.shell.Global$$ExternalSyntheticLambda0
            @Override // org.mozilla.javascript.ContextAction
            public final Object run(Context context) {
                return this.f$0.m7541lambda$init$0$orgmozillajavascripttoolsshellGlobal(context);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$init$0$org-mozilla-javascript-tools-shell-Global, reason: not valid java name */
    /* synthetic */ Object m7541lambda$init$0$orgmozillajavascripttoolsshellGlobal(Context context) {
        init(context);
        return null;
    }

    public void init(Context context) {
        initStandardObjects(context, this.sealedStdLib);
        defineFunctionProperties(new String[]{"defineClass", "deserialize", "doctest", ConfigConstants.CONFIG_GC_SECTION, "help", "load", "loadClass", "print", "quit", "readline", "readFile", "readUrl", "runCommand", "seal", "serialize", "spawn", "sync", "toint32", "version", "write"}, Global.class, 2);
        Environment.defineClass(this);
        defineProperty("environment", new Environment(this), 2);
        NativeArray nativeArray = (NativeArray) context.newArray(this, 0);
        this.history = nativeArray;
        defineProperty("history", nativeArray, 2);
        this.initialized = true;
    }

    public Require installRequire(Context context, List<String> list, boolean z) {
        RequireBuilder requireBuilder = new RequireBuilder();
        requireBuilder.setSandboxed(z);
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (String str : list) {
                try {
                    URI uri = new URI(str);
                    if (!uri.isAbsolute()) {
                        uri = new File(str).toURI().resolve("");
                    }
                    if (!uri.toString().endsWith("/")) {
                        uri = new URI(uri + "/");
                    }
                    arrayList.add(uri);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        requireBuilder.setModuleScriptProvider(new SoftCachingModuleScriptProvider(new UrlModuleSourceProvider(arrayList, null)));
        Require requireCreateRequire = requireBuilder.createRequire(context, this);
        requireCreateRequire.install(this);
        return requireCreateRequire;
    }

    public static void help(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        getInstance(function).getOut().println(ToolErrorReporter.getMessage("msg.help"));
    }

    public static void gc(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        System.gc();
    }

    public static Object print(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, true);
    }

    public static Object write(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        return doPrint(objArr, function, false);
    }

    private static Object doPrint(Object[] objArr, Function function, boolean z) {
        PrintStream out = getInstance(function).getOut();
        for (int i = 0; i < objArr.length; i++) {
            if (i > 0) {
                out.print(" ");
            }
            out.print(Context.toString(objArr[i]));
        }
        if (z) {
            out.println();
        }
        return Context.getUndefinedValue();
    }

    public static void quit(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Global global = getInstance(function);
        if (global.quitAction != null) {
            global.quitAction.quit(context, objArr.length != 0 ? ScriptRuntime.toInt32(objArr[0]) : 0);
        }
    }

    public static double version(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length > 0) {
            context.setLanguageVersion((int) Context.toNumber(objArr[0]));
        }
        return context.getLanguageVersion();
    }

    public static void load(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        for (Object obj : objArr) {
            String string = Context.toString(obj);
            try {
                Main.processFile(context, scriptable, string);
            } catch (IOException e) {
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.couldnt.read.source", string, e.getMessage()));
            } catch (VirtualMachineError e2) {
                e2.printStackTrace();
                throw Context.reportRuntimeError(ToolErrorReporter.getMessage("msg.uncaughtJSException", e2.toString()));
            }
        }
    }

    public static void defineClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> cls = getClass(objArr);
        if (!Scriptable.class.isAssignableFrom(cls)) {
            throw reportRuntimeError("msg.must.implement.Scriptable");
        }
        ScriptableObject.defineClass(scriptable, cls);
    }

    public static void loadClass(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IllegalAccessException, InstantiationException {
        Class<?> cls = getClass(objArr);
        if (!Script.class.isAssignableFrom(cls)) {
            throw reportRuntimeError("msg.must.implement.Script");
        }
        ((Script) cls.newInstance()).exec(context, scriptable);
    }

    private static Class<?> getClass(Object[] objArr) {
        if (objArr.length == 0) {
            throw reportRuntimeError("msg.expected.string.arg");
        }
        Object obj = objArr[0];
        if (obj instanceof Wrapper) {
            Object objUnwrap = ((Wrapper) obj).unwrap();
            if (objUnwrap instanceof Class) {
                return (Class) objUnwrap;
            }
        }
        String string = Context.toString(objArr[0]);
        try {
            return Class.forName(string);
        } catch (ClassNotFoundException unused) {
            throw reportRuntimeError("msg.class.not.found", string);
        }
    }

    public static void serialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length < 2) {
            throw Context.reportRuntimeError("Expected an object to serialize and a filename to write the serialization to");
        }
        Object obj = objArr[0];
        ScriptableOutputStream scriptableOutputStream = new ScriptableOutputStream(new FileOutputStream(Context.toString(objArr[1])), ScriptableObject.getTopLevelScope(scriptable));
        scriptableOutputStream.writeObject(obj);
        scriptableOutputStream.close();
    }

    public static Object deserialize(Context context, Scriptable scriptable, Object[] objArr, Function function) throws ClassNotFoundException, IOException {
        if (objArr.length < 1) {
            throw Context.reportRuntimeError("Expected a filename to read the serialization from");
        }
        FileInputStream fileInputStream = new FileInputStream(Context.toString(objArr[0]));
        Scriptable topLevelScope = ScriptableObject.getTopLevelScope(scriptable);
        ScriptableInputStream scriptableInputStream = new ScriptableInputStream(fileInputStream, topLevelScope);
        Object object = scriptableInputStream.readObject();
        scriptableInputStream.close();
        return Context.toObject(object, topLevelScope);
    }

    public String[] getPrompts(Context context) {
        if (ScriptableObject.hasProperty(this, "prompts")) {
            Object property = ScriptableObject.getProperty(this, "prompts");
            if (property instanceof Scriptable) {
                Scriptable scriptable = (Scriptable) property;
                if (ScriptableObject.hasProperty(scriptable, 0) && ScriptableObject.hasProperty(scriptable, 1)) {
                    Object property2 = ScriptableObject.getProperty(scriptable, 0);
                    if (property2 instanceof Function) {
                        property2 = ((Function) property2).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[0] = Context.toString(property2);
                    Object property3 = ScriptableObject.getProperty(scriptable, 1);
                    if (property3 instanceof Function) {
                        property3 = ((Function) property3).call(context, this, scriptable, new Object[0]);
                    }
                    this.prompts[1] = Context.toString(property3);
                }
            }
        }
        return this.prompts;
    }

    public static Object doctest(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length == 0) {
            return Boolean.FALSE;
        }
        String string = Context.toString(objArr[0]);
        Global global = getInstance(function);
        return Integer.valueOf(global.runDoctest(context, global, string, null, 0));
    }

    public int runDoctest(Context context, Scriptable scriptable, String str, String str2, int i) throws Throwable {
        PrintStream printStream;
        ErrorReporter errorReporter;
        ByteArrayOutputStream byteArrayOutputStream;
        PrintStream printStream2;
        String str3;
        StringBuilder sb;
        int i2;
        StringBuilder sb2;
        String[] strArr;
        StringBuilder sb3;
        String string;
        this.doctestCanonicalizations = new HashMap<>();
        String[] strArrSplit = str.split("\r\n?|\n");
        boolean z = false;
        String strTrim = this.prompts[0].trim();
        String strTrim2 = this.prompts[1].trim();
        int i3 = 0;
        while (i3 < strArrSplit.length && !strArrSplit[i3].trim().startsWith(strTrim)) {
            i3++;
        }
        int i4 = 0;
        while (i3 < strArrSplit.length) {
            StringBuilder sb4 = new StringBuilder(strArrSplit[i3].trim().substring(strTrim.length()));
            sb4.append('\n');
            while (true) {
                i3++;
                if (i3 >= strArrSplit.length || !strArrSplit[i3].trim().startsWith(strTrim2)) {
                    break;
                }
                sb4.append(strArrSplit[i3].trim().substring(strTrim2.length()));
                sb4.append('\n');
            }
            StringBuilder sb5 = new StringBuilder();
            int i5 = i3;
            while (i5 < strArrSplit.length && !strArrSplit[i5].trim().startsWith(strTrim)) {
                sb5.append(strArrSplit[i5]).append('\n');
                i5++;
            }
            PrintStream out = getOut();
            PrintStream err = getErr();
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            setOut(new PrintStream(byteArrayOutputStream2));
            setErr(new PrintStream(byteArrayOutputStream3));
            ErrorReporter errorReporter2 = context.getErrorReporter();
            context.setErrorReporter(new ToolErrorReporter(z, getErr()));
            int i6 = i4 + 1;
            try {
                String string2 = sb4.toString();
                printStream2 = err;
                i2 = i5;
                sb = sb5;
                byteArrayOutputStream = byteArrayOutputStream3;
                strArr = strArrSplit;
                errorReporter = errorReporter2;
                str3 = strTrim;
                sb2 = sb4;
                try {
                    Object objEvaluateString = context.evaluateString(scriptable, string2, "doctest input", 1, null);
                    if (objEvaluateString == Context.getUndefinedValue() || ((objEvaluateString instanceof Function) && string2.trim().startsWith("function"))) {
                        string = "";
                    } else {
                        string = Context.toString(objEvaluateString);
                    }
                    setOut(out);
                    setErr(printStream2);
                    context.setErrorReporter(errorReporter);
                    sb3 = new StringBuilder().append(string);
                } catch (RhinoException e) {
                    e = e;
                    printStream = out;
                    try {
                        ToolErrorReporter.reportException(context.getErrorReporter(), e);
                        setOut(printStream);
                        setErr(printStream2);
                        context.setErrorReporter(errorReporter);
                        sb3 = new StringBuilder("");
                    } catch (Throwable th) {
                        th = th;
                        setOut(printStream);
                        setErr(printStream2);
                        context.setErrorReporter(errorReporter);
                        String str4 = "" + byteArrayOutputStream.toString() + byteArrayOutputStream2.toString();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = out;
                    setOut(printStream);
                    setErr(printStream2);
                    context.setErrorReporter(errorReporter);
                    String str5 = "" + byteArrayOutputStream.toString() + byteArrayOutputStream2.toString();
                    throw th;
                }
            } catch (RhinoException e2) {
                e = e2;
                printStream = out;
                str3 = strTrim;
                sb = sb5;
                byteArrayOutputStream = byteArrayOutputStream3;
                printStream2 = err;
                i2 = i5;
                sb2 = sb4;
                strArr = strArrSplit;
                errorReporter = errorReporter2;
            } catch (Throwable th3) {
                th = th3;
                printStream = out;
                errorReporter = errorReporter2;
                byteArrayOutputStream = byteArrayOutputStream3;
                printStream2 = err;
            }
            String string3 = sb3.append(byteArrayOutputStream.toString()).append(byteArrayOutputStream2.toString()).toString();
            if (!doctestOutputMatches(sb.toString(), string3)) {
                String str6 = "doctest failure running:\n" + ((Object) sb2) + "expected: " + ((Object) sb) + "actual: " + string3 + "\n";
                if (str2 != null) {
                    throw Context.reportRuntimeError(str6, str2, (i + i2) - 1, null, 0);
                }
                throw Context.reportRuntimeError(str6);
            }
            strArrSplit = strArr;
            i3 = i2;
            i4 = i6;
            strTrim = str3;
            z = false;
        }
        return i4;
    }

    private boolean doctestOutputMatches(String str, String str2) {
        String strTrim = str.trim();
        String strReplace = str2.trim().replace("\r\n", "\n");
        if (strTrim.equals(strReplace)) {
            return true;
        }
        for (Map.Entry<String, String> entry : this.doctestCanonicalizations.entrySet()) {
            strTrim = strTrim.replace(entry.getKey(), entry.getValue());
        }
        if (strTrim.equals(strReplace)) {
            return true;
        }
        Pattern patternCompile = Pattern.compile("@[0-9a-fA-F]+");
        Matcher matcher = patternCompile.matcher(strTrim);
        Matcher matcher2 = patternCompile.matcher(strReplace);
        while (matcher.find() && matcher2.find() && matcher2.start() == matcher.start()) {
            int iStart = matcher.start();
            if (!strTrim.substring(0, iStart).equals(strReplace.substring(0, iStart))) {
                return false;
            }
            String strGroup = matcher.group();
            String strGroup2 = matcher2.group();
            String str3 = this.doctestCanonicalizations.get(strGroup);
            if (str3 == null) {
                this.doctestCanonicalizations.put(strGroup, strGroup2);
                strTrim = strTrim.replace(strGroup, strGroup2);
            } else if (!strGroup2.equals(str3)) {
                return false;
            }
            if (strTrim.equals(strReplace)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001f  */
    public static Object spawn(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Runner runner;
        Object[] elements;
        Scriptable parentScope = function.getParentScope();
        if (objArr.length != 0 && (objArr[0] instanceof Function)) {
            if (objArr.length > 1) {
                Object obj = objArr[1];
                if (obj instanceof Scriptable) {
                    elements = context.getElements((Scriptable) obj);
                } else {
                    elements = null;
                }
            } else {
                elements = null;
            }
            if (elements == null) {
                elements = ScriptRuntime.emptyArgs;
            }
            runner = new Runner(parentScope, (Function) objArr[0], elements);
        } else if (objArr.length != 0 && (objArr[0] instanceof Script)) {
            runner = new Runner(parentScope, (Script) objArr[0]);
        } else {
            throw reportRuntimeError("msg.spawn.args");
        }
        runner.factory = context.getFactory();
        Thread thread = new Thread(runner);
        thread.start();
        return thread;
    }

    public static Object sync(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        if (objArr.length >= 1 && objArr.length <= 2 && (objArr[0] instanceof Function)) {
            return new Synchronizer((Function) objArr[0], (objArr.length != 2 || objArr[1] == Undefined.instance) ? null : objArr[1]);
        }
        throw reportRuntimeError("msg.sync.args");
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00d3  */
    /* JADX WARN: Code duplicated, block: B:50:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:51:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:55:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:56:0x00ff  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1, types: [java.io.ByteArrayOutputStream] */
    public static Object runCommand(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        Object[] elements;
        Object obj;
        Object obj2;
        OutputStream out;
        String[] strArr;
        File file;
        InputStream inputStream;
        Scriptable scriptable2;
        OutputStream err;
        ?? r15;
        ?? r17;
        String[] strArr2;
        OutputStream outputStream;
        Object property;
        OutputStream outputStream2;
        Object property2;
        Object property3;
        String str;
        int length = objArr.length;
        if (length == 0 || (length == 1 && (objArr[0] instanceof Scriptable))) {
            throw reportRuntimeError("msg.runCommand.bad.args");
        }
        Object obj3 = objArr[length - 1];
        if (obj3 instanceof Scriptable) {
            Scriptable scriptable3 = (Scriptable) obj3;
            length--;
            Object property4 = ScriptableObject.getProperty(scriptable3, "env");
            if (property4 == Scriptable.NOT_FOUND) {
                strArr2 = null;
            } else if (property4 == null) {
                strArr2 = new String[0];
            } else {
                if (!(property4 instanceof Scriptable)) {
                    throw reportRuntimeError("msg.runCommand.bad.env");
                }
                Scriptable scriptable4 = (Scriptable) property4;
                Object[] propertyIds = ScriptableObject.getPropertyIds(scriptable4);
                String[] strArr3 = new String[propertyIds.length];
                for (int i = 0; i != propertyIds.length; i++) {
                    Object obj4 = propertyIds[i];
                    if (obj4 instanceof String) {
                        str = (String) obj4;
                        property3 = ScriptableObject.getProperty(scriptable4, str);
                    } else {
                        int iIntValue = ((Number) obj4).intValue();
                        String string = Integer.toString(iIntValue);
                        property3 = ScriptableObject.getProperty(scriptable4, iIntValue);
                        str = string;
                    }
                    if (property3 == ScriptableObject.NOT_FOUND) {
                        property3 = Undefined.instance;
                    }
                    strArr3[i] = str + '=' + ScriptRuntime.toString(property3);
                }
                strArr2 = strArr3;
            }
            Object property5 = ScriptableObject.getProperty(scriptable3, "dir");
            File file2 = property5 != Scriptable.NOT_FOUND ? new File(ScriptRuntime.toString(property5)) : null;
            Object property6 = ScriptableObject.getProperty(scriptable3, "input");
            InputStream inputStream2 = property6 != Scriptable.NOT_FOUND ? toInputStream(property6) : null;
            Object property7 = ScriptableObject.getProperty(scriptable3, "output");
            if (property7 != Scriptable.NOT_FOUND) {
                out = toOutputStream(property7);
                if (out == null) {
                    out = new ByteArrayOutputStream();
                    outputStream = out;
                }
                property = ScriptableObject.getProperty(scriptable3, NotificationCompat.CATEGORY_ERROR);
                if (property != Scriptable.NOT_FOUND) {
                    err = toOutputStream(property);
                    if (err == null) {
                        err = new ByteArrayOutputStream();
                        outputStream2 = err;
                    }
                    property2 = ScriptableObject.getProperty(scriptable3, "args");
                    if (property2 != Scriptable.NOT_FOUND) {
                        elements = context.getElements(Context.toObject(property2, getTopLevelScope(scriptable)));
                    } else {
                        elements = null;
                    }
                    inputStream = inputStream2;
                    scriptable2 = scriptable3;
                    obj = property7;
                    file = file2;
                    strArr = strArr2;
                    obj2 = property;
                    r15 = outputStream2;
                    r17 = outputStream;
                } else {
                    err = null;
                }
                outputStream2 = null;
                property2 = ScriptableObject.getProperty(scriptable3, "args");
                if (property2 != Scriptable.NOT_FOUND) {
                    elements = context.getElements(Context.toObject(property2, getTopLevelScope(scriptable)));
                } else {
                    elements = null;
                }
                inputStream = inputStream2;
                scriptable2 = scriptable3;
                obj = property7;
                file = file2;
                strArr = strArr2;
                obj2 = property;
                r15 = outputStream2;
                r17 = outputStream;
            } else {
                out = null;
            }
            outputStream = null;
            property = ScriptableObject.getProperty(scriptable3, NotificationCompat.CATEGORY_ERROR);
            if (property != Scriptable.NOT_FOUND) {
                err = toOutputStream(property);
                if (err == null) {
                    err = new ByteArrayOutputStream();
                    outputStream2 = err;
                }
                property2 = ScriptableObject.getProperty(scriptable3, "args");
                if (property2 != Scriptable.NOT_FOUND) {
                    elements = context.getElements(Context.toObject(property2, getTopLevelScope(scriptable)));
                } else {
                    elements = null;
                }
                inputStream = inputStream2;
                scriptable2 = scriptable3;
                obj = property7;
                file = file2;
                strArr = strArr2;
                obj2 = property;
                r15 = outputStream2;
                r17 = outputStream;
            } else {
                err = null;
            }
            outputStream2 = null;
            property2 = ScriptableObject.getProperty(scriptable3, "args");
            if (property2 != Scriptable.NOT_FOUND) {
                elements = context.getElements(Context.toObject(property2, getTopLevelScope(scriptable)));
            } else {
                elements = null;
            }
            inputStream = inputStream2;
            scriptable2 = scriptable3;
            obj = property7;
            file = file2;
            strArr = strArr2;
            obj2 = property;
            r15 = outputStream2;
            r17 = outputStream;
        } else {
            elements = null;
            obj = null;
            obj2 = null;
            out = null;
            strArr = null;
            file = null;
            inputStream = null;
            scriptable2 = null;
            err = null;
            r15 = 0;
            r17 = 0;
        }
        Global global = getInstance(function);
        if (out == null) {
            out = global.getOut();
        }
        OutputStream outputStream3 = out;
        if (err == null) {
            err = global.getErr();
        }
        String[] strArr4 = new String[elements == null ? length : elements.length + length];
        for (int i2 = 0; i2 != length; i2++) {
            strArr4[i2] = ScriptRuntime.toString(objArr[i2]);
        }
        if (elements != null) {
            for (int i3 = 0; i3 != elements.length; i3++) {
                strArr4[length + i3] = ScriptRuntime.toString(elements[i3]);
            }
        }
        Scriptable scriptable5 = scriptable2;
        int iRunProcess = runProcess(strArr4, strArr, file, inputStream, outputStream3, err);
        if (r17 != 0) {
            ScriptableObject.putProperty(scriptable5, "output", ScriptRuntime.toString(obj) + r17.toString());
        }
        if (r15 != 0) {
            ScriptableObject.putProperty(scriptable5, NotificationCompat.CATEGORY_ERROR, ScriptRuntime.toString(obj2) + r15.toString());
        }
        return Integer.valueOf(iRunProcess);
    }

    public static void seal(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            if (!(obj instanceof ScriptableObject) || obj == Undefined.instance) {
                if (!(obj instanceof Scriptable) || obj == Undefined.instance) {
                    throw reportRuntimeError("msg.shell.seal.not.object");
                }
                throw reportRuntimeError("msg.shell.seal.not.scriptable");
            }
        }
        for (int i2 = 0; i2 != objArr.length; i2++) {
            ((ScriptableObject) objArr[i2]).sealObject();
        }
    }

    public static Object readFile(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length == 0) {
            throw reportRuntimeError("msg.shell.readFile.bad.args");
        }
        return readUrl(ScriptRuntime.toString(objArr[0]), objArr.length >= 2 ? ScriptRuntime.toString(objArr[1]) : null, true);
    }

    public static Object readUrl(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        if (objArr.length == 0) {
            throw reportRuntimeError("msg.shell.readUrl.bad.args");
        }
        return readUrl(ScriptRuntime.toString(objArr[0]), objArr.length >= 2 ? ScriptRuntime.toString(objArr[1]) : null, false);
    }

    public static Object toint32(Context context, Scriptable scriptable, Object[] objArr, Function function) {
        Object obj = objArr.length != 0 ? objArr[0] : Undefined.instance;
        return obj instanceof Integer ? obj : ScriptRuntime.wrapInt(ScriptRuntime.toInt32(obj));
    }

    private boolean loadJLine(Charset charset) {
        if (!this.attemptedJLineLoad) {
            this.attemptedJLineLoad = true;
            this.console = ShellConsole.getConsole(this, charset);
        }
        return this.console != null;
    }

    public ShellConsole getConsole(Charset charset) {
        if (!loadJLine(charset)) {
            this.console = ShellConsole.getConsole(getIn(), getErr(), charset);
        }
        return this.console;
    }

    public InputStream getIn() {
        if (this.inStream == null && !this.attemptedJLineLoad && loadJLine(Charset.defaultCharset())) {
            this.inStream = this.console.getIn();
        }
        InputStream inputStream = this.inStream;
        return inputStream == null ? System.in : inputStream;
    }

    public void setIn(InputStream inputStream) {
        this.inStream = inputStream;
    }

    public PrintStream getOut() {
        PrintStream printStream = this.outStream;
        return printStream == null ? System.out : printStream;
    }

    public void setOut(PrintStream printStream) {
        this.outStream = printStream;
    }

    public PrintStream getErr() {
        PrintStream printStream = this.errStream;
        return printStream == null ? System.err : printStream;
    }

    public void setErr(PrintStream printStream) {
        this.errStream = printStream;
    }

    public void setSealedStdLib(boolean z) {
        this.sealedStdLib = z;
    }

    private static Global getInstance(Function function) {
        Scriptable parentScope = function.getParentScope();
        if (!(parentScope instanceof Global)) {
            throw reportRuntimeError("msg.bad.shell.function.scope", String.valueOf(parentScope));
        }
        return (Global) parentScope;
    }

    private static int runProcess(String[] strArr, String[] strArr2, File file, InputStream inputStream, OutputStream outputStream, OutputStream outputStream2) throws IOException {
        Process processExec;
        PipeThread pipeThread;
        PipeThread pipeThread2;
        PipeThread pipeThread3 = null;
        if (strArr2 == null) {
            processExec = Runtime.getRuntime().exec(strArr, (String[]) null, file);
        } else {
            processExec = Runtime.getRuntime().exec(strArr, strArr2, file);
        }
        try {
            if (inputStream != null) {
                pipeThread = new PipeThread(false, inputStream, processExec.getOutputStream());
                pipeThread.start();
            } else {
                processExec.getOutputStream().close();
                pipeThread = null;
            }
            if (outputStream != null) {
                pipeThread2 = new PipeThread(true, processExec.getInputStream(), outputStream);
                pipeThread2.start();
            } else {
                processExec.getInputStream().close();
                pipeThread2 = null;
            }
            if (outputStream2 != null) {
                pipeThread3 = new PipeThread(true, processExec.getErrorStream(), outputStream2);
                pipeThread3.start();
            } else {
                processExec.getErrorStream().close();
            }
            while (true) {
                try {
                    processExec.waitFor();
                    if (pipeThread2 != null) {
                        pipeThread2.join();
                    }
                    if (pipeThread != null) {
                        pipeThread.join();
                    }
                    if (pipeThread3 == null) {
                        break;
                    }
                    pipeThread3.join();
                    break;
                } catch (InterruptedException unused) {
                }
            }
            int iExitValue = processExec.exitValue();
            processExec.destroy();
            return iExitValue;
        } catch (Throwable th) {
            processExec.destroy();
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0025 A[Catch: IOException -> 0x002c, TRY_ENTER, TryCatch #3 {IOException -> 0x002c, blocks: (B:17:0x0025, B:18:0x0029), top: B:36:0x0023 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x0029 A[Catch: IOException -> 0x002c, TRY_LEAVE, TryCatch #3 {IOException -> 0x002c, blocks: (B:17:0x0025, B:18:0x0029), top: B:36:0x0023 }] */
    static void pipe(boolean z, InputStream inputStream, OutputStream outputStream) throws IOException {
        int i;
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                if (!z) {
                    i = inputStream.read(bArr, 0, 4096);
                } else {
                    i = inputStream.read(bArr, 0, 4096);
                }
                if (i < 0) {
                    break;
                }
                if (z) {
                    outputStream.write(bArr, 0, i);
                    outputStream.flush();
                } else {
                    try {
                        outputStream.write(bArr, 0, i);
                        outputStream.flush();
                    } catch (IOException unused) {
                        try {
                            if (z) {
                                inputStream.close();
                            } else {
                                outputStream.close();
                            }
                        } catch (IOException unused2) {
                            return;
                        }
                    }
                }
            }
            if (z) {
                inputStream.close();
            } else {
                outputStream.close();
            }
        } catch (Throwable th) {
            try {
                if (z) {
                    inputStream.close();
                } else {
                    outputStream.close();
                }
            } catch (IOException unused3) {
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:16:0x0040  */
    private static InputStream toInputStream(Object obj) throws IOException {
        String string;
        InputStream inputStream = null;
        if (obj instanceof Wrapper) {
            Object objUnwrap = ((Wrapper) obj).unwrap();
            if (objUnwrap instanceof InputStream) {
                inputStream = (InputStream) objUnwrap;
                string = null;
            } else if (objUnwrap instanceof byte[]) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[]) objUnwrap);
                string = null;
                inputStream = byteArrayInputStream;
            } else if (objUnwrap instanceof Reader) {
                string = readReader((Reader) objUnwrap);
            } else if (objUnwrap instanceof char[]) {
                string = new String((char[]) objUnwrap);
            } else {
                string = null;
            }
        } else {
            string = null;
        }
        if (inputStream != null) {
            return inputStream;
        }
        if (string == null) {
            string = ScriptRuntime.toString(obj);
        }
        return new ByteArrayInputStream(string.getBytes());
    }

    private static OutputStream toOutputStream(Object obj) {
        if (obj instanceof Wrapper) {
            Object objUnwrap = ((Wrapper) obj).unwrap();
            if (objUnwrap instanceof OutputStream) {
                return (OutputStream) objUnwrap;
            }
        }
        return null;
    }

    private static String readUrl(String str, String str2, boolean z) throws IOException {
        int contentLength;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        String contentType;
        InputStream inputStream2 = null;
        try {
            if (!z) {
                URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
                inputStream = uRLConnectionOpenConnection.getInputStream();
                contentLength = uRLConnectionOpenConnection.getContentLength();
                if (contentLength <= 0) {
                    contentLength = 1024;
                }
                if (str2 == null && (contentType = uRLConnectionOpenConnection.getContentType()) != null) {
                    str2 = getCharCodingFromType(contentType);
                }
            } else {
                File file = new File(str);
                if (!file.exists()) {
                    throw new FileNotFoundException("File not found: " + str);
                }
                if (!file.canRead()) {
                    throw new IOException("Cannot read file: " + str);
                }
                long length = file.length();
                int i = (int) length;
                if (i != length) {
                    throw new IOException("Too big file size: " + length);
                }
                if (i == 0) {
                    return "";
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                contentLength = i;
                inputStream = fileInputStream;
            }
            if (str2 == null) {
                inputStreamReader = new InputStreamReader(inputStream);
            } else {
                inputStreamReader = new InputStreamReader(inputStream, str2);
            }
            String reader = readReader(inputStreamReader, contentLength);
            if (inputStream != null) {
                inputStream.close();
            }
            return reader;
        } catch (Throwable th) {
            if (0 != 0) {
                inputStream2.close();
            }
            throw th;
        }
    }

    public static Object readline(Context context, Scriptable scriptable, Object[] objArr, Function function) throws IOException {
        Global global = getInstance(function);
        if (objArr.length > 0) {
            return global.console.readLine(Context.toString(objArr[0]));
        }
        return global.console.readLine();
    }

    private static String getCharCodingFromType(String str) {
        int iIndexOf = str.indexOf(59);
        if (iIndexOf < 0) {
            return null;
        }
        int length = str.length();
        do {
            iIndexOf++;
            if (iIndexOf == length) {
                break;
            }
        } while (str.charAt(iIndexOf) <= ' ');
        if (!"charset".regionMatches(true, 0, str, iIndexOf, "charset".length())) {
            return null;
        }
        int length2 = iIndexOf + "charset".length();
        while (length2 != length && str.charAt(length2) <= ' ') {
            length2++;
        }
        if (length2 == length || str.charAt(length2) != '=') {
            return null;
        }
        do {
            length2++;
            if (length2 == length) {
                break;
            }
        } while (str.charAt(length2) <= ' ');
        if (length2 == length) {
            return null;
        }
        while (str.charAt(length - 1) <= ' ') {
            length--;
        }
        return str.substring(length2, length);
    }

    private static String readReader(Reader reader) throws IOException {
        return readReader(reader, 4096);
    }

    private static String readReader(Reader reader, int i) throws IOException {
        char[] cArr = new char[i];
        int i2 = 0;
        while (true) {
            int i3 = reader.read(cArr, i2, cArr.length - i2);
            if (i3 >= 0) {
                i2 += i3;
                if (i2 == cArr.length) {
                    char[] cArr2 = new char[cArr.length * 2];
                    System.arraycopy(cArr, 0, cArr2, 0, i2);
                    cArr = cArr2;
                }
            } else {
                return new String(cArr, 0, i2);
            }
        }
    }

    static RuntimeException reportRuntimeError(String str) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str));
    }

    static RuntimeException reportRuntimeError(String str, String str2) {
        return Context.reportRuntimeError(ToolErrorReporter.getMessage(str, str2));
    }
}
