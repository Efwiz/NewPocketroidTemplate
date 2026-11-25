package org.catrobat.catroid.ide;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.dex.DexFormat;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.R8;
import com.android.tools.r8.R8Command;
import dalvik.system.DexClassLoader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ScriptExecutor.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScriptExecutor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J2\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00122\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e0\u001aJ\u0018\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\bH\u0002J \u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\bH\u0002J\u0018\u0010#\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010$\u001a\u00020%H\u0002J\u0010\u0010&\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J0\u0010'\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010)\u001a\u00020\b2\b\b\u0002\u0010$\u001a\u00020%JP\u0010*\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0,2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\b0,2\u0006\u0010.\u001a\u00020\b2\b\b\u0002\u0010/\u001a\u00020%2\b\b\u0002\u00100\u001a\u0002012\u0006\u0010$\u001a\u00020%H\u0002JF\u00102\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u00103\u001a\u0002042\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e0\u001a2\u0012\u00106\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000e0\u001aJ\u000e\u00107\u001a\u00020\u000e2\u0006\u00108\u001a\u00020\u0012R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u00069"}, d2 = {"Lorg/catrobat/catroid/ide/ScriptExecutor;", "", "()V", "bootClasspath", "Ljava/nio/file/Path;", "inputWriter", "Ljava/io/PipedOutputStream;", "lastCompiledDex", "Ljava/io/File;", "getLastCompiledDex", "()Ljava/io/File;", "setLastCompiledDex", "(Ljava/io/File;)V", "addFileToJar", "", "jarFile", "fileToAdd", "entryName", "", "compileForApk", "Lorg/catrobat/catroid/ide/CompilationResult;", "context", "Landroid/content/Context;", Constants.EXTRA_PROJECT_PATH, "packageName", "onStatus", "Lkotlin/Function1;", "createJarFromClasses", "classesDir", "outputJar", "extractNativeLib", "name", "input", "Ljava/io/InputStream;", ModuleXmlParser.OUTPUT_DIR, "getBootJar", "targetApi", "", "getLambdaStubs", "processLibrary", "originLib", "nativeDir", "runD8", "inputFiles", "", "libraries", "outputFolder", "minApi", "isRelease", "", "runScript", "layoutContainer", "Landroid/widget/FrameLayout;", "onPrint", "onError", "sendInput", "text", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptExecutor {
    public static final ScriptExecutor INSTANCE = new ScriptExecutor();
    private static Path bootClasspath;
    private static PipedOutputStream inputWriter;
    private static File lastCompiledDex;

    private ScriptExecutor() {
    }

    public final File getLastCompiledDex() {
        return lastCompiledDex;
    }

    public final void setLastCompiledDex(File file) {
        lastCompiledDex = file;
    }

    public final void sendInput(String text) {
        Intrinsics.checkNotNullParameter(text, "text");
        try {
            PipedOutputStream pipedOutputStream = inputWriter;
            if (pipedOutputStream != null) {
                byte[] bytes = (text + "\n").getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                pipedOutputStream.write(bytes);
            }
            PipedOutputStream pipedOutputStream2 = inputWriter;
            if (pipedOutputStream2 != null) {
                pipedOutputStream2.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final void runD8(Context context, List<? extends File> inputFiles, List<? extends File> libraries, File outputFolder, int minApi, boolean isRelease, int targetApi) throws CompilationFailedException {
        CompilationMode compilationMode;
        File bootJar = getBootJar(context, targetApi);
        if (isRelease) {
            compilationMode = CompilationMode.RELEASE;
        } else {
            compilationMode = CompilationMode.DEBUG;
        }
        D8Command.Builder builder = (D8Command.Builder) D8Command.builder().setMode(compilationMode).setMinApiLevel(minApi).setIntermediate(false).setOutput(outputFolder.toPath(), OutputMode.DexIndexed).addLibraryFiles(bootJar.toPath());
        for (File file : inputFiles) {
            if (file.exists()) {
                builder.addProgramFiles(file.toPath());
            }
        }
        for (File file2 : libraries) {
            if (file2.exists()) {
                builder.addClasspathFiles(file2.toPath());
            }
        }
        D8.run((D8Command) builder.build());
    }

    public final void runScript(final Context context, final String projectPath, final FrameLayout layoutContainer, final Function1<? super String, Unit> onPrint, final Function1<? super String, Unit> onError) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(layoutContainer, "layoutContainer");
        Intrinsics.checkNotNullParameter(onPrint, "onPrint");
        Intrinsics.checkNotNullParameter(onError, "onError");
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ide.ScriptExecutor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                ScriptExecutor.runScript$lambda$6(projectPath, context, onPrint, layoutContainer, onError);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v21, types: [org.catrobat.catroid.ide.ScriptExecutor] */
    /* JADX WARN: Type inference failed for: r22v0 */
    /* JADX WARN: Type inference failed for: r22v1, types: [java.io.PrintStream] */
    /* JADX WARN: Type inference failed for: r22v3 */
    /* JADX WARN: Type inference failed for: r22v4 */
    /* JADX WARN: Type inference failed for: r22v5 */
    /* JADX WARN: Type inference failed for: r22v6 */
    /* JADX WARN: Type inference failed for: r22v7 */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v5, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public static final void runScript$lambda$6(String projectPath, Context context, final Function1 onPrint, final FrameLayout layoutContainer, Function1 onError) throws Throwable {
        String str;
        ?? r22;
        PrintStream printStream;
        File file;
        ArrayList arrayList;
        File file2;
        Object next;
        File file3;
        File[] fileArrListFiles;
        Intrinsics.checkNotNullParameter(projectPath, "$projectPath");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(onPrint, "$onPrint");
        Intrinsics.checkNotNullParameter(layoutContainer, "$layoutContainer");
        Intrinsics.checkNotNullParameter(onError, "$onError");
        try {
            System.gc();
            File file4 = new File(projectPath);
            File file5 = new File(file4, "src");
            List<File> libraries = DependencyManager.INSTANCE.getLibraries(projectPath);
            File codeCacheDir = context.getCodeCacheDir();
            long jCurrentTimeMillis = System.currentTimeMillis();
            File file6 = new File(codeCacheDir, "classes_" + jCurrentTimeMillis);
            File file7 = new File(codeCacheDir, "optimized_" + jCurrentTimeMillis);
            File file8 = new File(file4, "libs_processed");
            File file9 = new File(codeCacheDir, "native_libs_" + jCurrentTimeMillis);
            file6.mkdirs();
            file7.mkdirs();
            file9.mkdirs();
            if (!file8.exists()) {
                file8.mkdirs();
            }
            PrintStream printStream2 = new PrintStream(new OutputStream() { // from class: org.catrobat.catroid.ide.ScriptExecutor$runScript$1$logStream$1
                private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                @Override // java.io.OutputStream
                public void write(int b) throws UnsupportedEncodingException {
                    this.buffer.write(b);
                    if (b == 10) {
                        flushLine();
                    }
                }

                @Override // java.io.OutputStream
                public void write(byte[] b, int off, int len) throws UnsupportedEncodingException {
                    Intrinsics.checkNotNullParameter(b, "b");
                    this.buffer.write(b, off, len);
                    if (len <= 0 || b[(off + len) - 1] != 10) {
                        return;
                    }
                    flushLine();
                }

                private final void flushLine() throws UnsupportedEncodingException {
                    String string = this.buffer.toString("UTF-8");
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    String string2 = StringsKt.trimEnd((CharSequence) string).toString();
                    if (string2.length() > 0) {
                        onPrint.invoke(string2);
                    }
                    this.buffer.reset();
                }
            }, true, "UTF-8");
            PipedInputStream pipedInputStream = new PipedInputStream();
            inputWriter = new PipedOutputStream(pipedInputStream);
            PrintStream printStream3 = System.out;
            PrintStream printStream4 = System.err;
            InputStream inputStream = System.in;
            System.setOut(printStream2);
            System.setErr(printStream2);
            System.setIn(pipedInputStream);
            try {
                ProjectConfig projectConfigLoadConfig = ProjectManager.INSTANCE.loadConfig(projectPath);
                ArrayList arrayList2 = new ArrayList();
                if (!libraries.isEmpty()) {
                    try {
                        onPrint.invoke("> Processing libraries...");
                        ?? r3 = file8;
                        for (File file10 : libraries) {
                            try {
                                file = file9;
                                r22 = r3;
                                arrayList = arrayList2;
                                printStream = printStream4;
                                file2 = file6;
                                try {
                                    try {
                                        File fileProcessLibrary = INSTANCE.processLibrary(context, file10, r3, file, projectConfigLoadConfig.getTargetSdk());
                                        if (fileProcessLibrary.exists()) {
                                            arrayList.add(fileProcessLibrary.getAbsolutePath());
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        r22 = printStream3;
                                        System.setOut(r22);
                                        System.setErr(printStream);
                                        System.setIn(inputStream);
                                        str = null;
                                        try {
                                            inputWriter = null;
                                            throw th;
                                        } catch (Exception e) {
                                            e = e;
                                            Exception cause = e instanceof InvocationTargetException ? e.getCause() : e;
                                            onError.invoke("Runtime Error: " + (cause != null ? cause.getMessage() : str));
                                            if (cause != null) {
                                                cause.printStackTrace();
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    onPrint.invoke("! Error lib " + file10.getName() + ": " + e.getMessage());
                                }
                            } catch (Exception e3) {
                                e = e3;
                                file = file9;
                                r22 = r3;
                                arrayList = arrayList2;
                                printStream = printStream4;
                                file2 = file6;
                            }
                            file9 = file;
                            arrayList2 = arrayList;
                            file6 = file2;
                            r3 = r22;
                            printStream4 = printStream;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        printStream = printStream4;
                        r22 = printStream3;
                        System.setOut(r22);
                        System.setErr(printStream);
                        System.setIn(inputStream);
                        str = null;
                        inputWriter = null;
                        throw th;
                    }
                }
                File file11 = file9;
                ArrayList arrayList3 = arrayList2;
                printStream = printStream4;
                File file12 = file6;
                File file13 = new File(file4, "jniLibs");
                if (file13.exists()) {
                    onPrint.invoke("> Loading native libs...");
                    String[] strArr = Build.SUPPORTED_ABIS;
                    Intrinsics.checkNotNull(strArr);
                    int length = strArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            file3 = null;
                            break;
                        }
                        file3 = new File(file13, strArr[i]);
                        if (file3.exists()) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (file3 != null && (fileArrListFiles = file3.listFiles()) != null) {
                        ArrayList<File> arrayList4 = new ArrayList();
                        for (File file14 : fileArrListFiles) {
                            Intrinsics.checkNotNull(file14);
                            if (Intrinsics.areEqual(FilesKt.getExtension(file14), "so")) {
                                arrayList4.add(file14);
                            }
                        }
                        for (File file15 : arrayList4) {
                            Intrinsics.checkNotNull(file15);
                            File file16 = file11;
                            FilesKt.copyTo$default(file15, new File(file16, file15.getName()), true, 0, 4, null);
                            file11 = file16;
                        }
                    }
                }
                File file17 = file11;
                onPrint.invoke("> Compiling Java...");
                ScriptExecutor scriptExecutor = INSTANCE;
                Map<String, byte[]> mapCompileDirectory = AndroidECJ.INSTANCE.compileDirectory(file5, libraries, scriptExecutor.getBootJar(context, projectConfigLoadConfig.getTargetSdk()), scriptExecutor.getLambdaStubs(context));
                try {
                    if (mapCompileDirectory.isEmpty()) {
                        throw new RuntimeException("No compiled classes found.");
                    }
                    for (Map.Entry<String, byte[]> entry : mapCompileDirectory.entrySet()) {
                        String key = entry.getKey();
                        byte[] value = entry.getValue();
                        File file18 = new File(file12, key);
                        File parentFile = file18.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FilesKt.writeBytes(file18, value);
                    }
                    onPrint.invoke("> Linking...");
                    File file19 = new File(file7, "dex_temp");
                    file19.mkdirs();
                    List<? extends File> list = SequencesKt.toList(SequencesKt.filter(FilesKt.walkTopDown(file12), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.ide.ScriptExecutor$runScript$1$classFiles$1
                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(File it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return Boolean.valueOf(Intrinsics.areEqual(FilesKt.getExtension(it), "class"));
                        }
                    }));
                    ScriptExecutor scriptExecutor2 = INSTANCE;
                    scriptExecutor2.runD8(context, list, libraries, file19, 26, false, projectConfigLoadConfig.getTargetSdk());
                    File file20 = new File(file19, DexFormat.DEX_IN_JAR_NAME);
                    File file21 = new File(file7, "app_run.jar");
                    scriptExecutor2.createJarFromClasses(file12, file21);
                    scriptExecutor2.addFileToJar(file21, file20, DexFormat.DEX_IN_JAR_NAME);
                    file21.setReadOnly();
                    arrayList3.add(0, file21.getAbsolutePath());
                    onPrint.invoke("> Loading...");
                    String pathSeparator = File.pathSeparator;
                    Intrinsics.checkNotNullExpressionValue(pathSeparator, "pathSeparator");
                    DexClassLoader dexClassLoader = new DexClassLoader(CollectionsKt.joinToString$default(arrayList3, pathSeparator, null, null, 0, null, null, 62, null), file7.getAbsolutePath(), file17.getAbsolutePath(), context.getClassLoader());
                    Iterator it = mapCompileDirectory.keySet().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!StringsKt.endsWith$default((String) next, "Main.class", false, 2, (Object) null));
                    String str2 = (String) next;
                    if (str2 == null) {
                        throw new RuntimeException("Class 'Main' not found.");
                    }
                    Class clsLoadClass = dexClassLoader.loadClass(StringsKt.replace$default(StringsKt.replace$default(StringsKt.removeSuffix(str2, (CharSequence) ".class"), File.separatorChar, '.', false, 4, (Object) null), '/', '.', false, 4, (Object) null));
                    final Object objNewInstance = clsLoadClass.newInstance();
                    for (Pair pair : CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(new Class[]{Context.class, ViewGroup.class}, new Object[]{context, layoutContainer}), TuplesKt.to(new Class[]{Context.class}, new Context[]{context}), TuplesKt.to(new Class[0], new Object[0])})) {
                        Class[] clsArr = (Class[]) pair.component1();
                        final Object[] objArr = (Object[]) pair.component2();
                        try {
                            final Method method = clsLoadClass.getMethod("onStart", (Class[]) Arrays.copyOf(clsArr, clsArr.length));
                            if (ArraysKt.contains(clsArr, ViewGroup.class)) {
                                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.ide.ScriptExecutor$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ScriptExecutor.runScript$lambda$6$lambda$5(layoutContainer, method, objNewInstance, objArr, onPrint);
                                    }
                                });
                            } else {
                                method.invoke(objNewInstance, Arrays.copyOf(objArr, objArr.length));
                            }
                            System.setOut(printStream3);
                            System.setErr(printStream);
                            System.setIn(inputStream);
                            inputWriter = null;
                            return;
                        } catch (NoSuchMethodException unused) {
                        }
                    }
                    throw new RuntimeException("Method 'onStart' not found.");
                } catch (Throwable th3) {
                    th = th3;
                    System.setOut(r22);
                    System.setErr(printStream);
                    System.setIn(inputStream);
                    str = null;
                    inputWriter = null;
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                r22 = printStream3;
                printStream = printStream4;
            }
        } catch (Exception e4) {
            e = e4;
            str = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void runScript$lambda$6$lambda$5(FrameLayout layoutContainer, Method method, Object obj, Object[] args, Function1 onPrint) {
        Intrinsics.checkNotNullParameter(layoutContainer, "$layoutContainer");
        Intrinsics.checkNotNullParameter(args, "$args");
        Intrinsics.checkNotNullParameter(onPrint, "$onPrint");
        layoutContainer.removeAllViews();
        try {
            method.invoke(obj, Arrays.copyOf(args, args.length));
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            onPrint.invoke("UI Error: " + (cause != null ? cause.getMessage() : null));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final CompilationResult compileForApk(Context context, String projectPath, String packageName, Function1<? super String, Unit> onStatus) {
        String str;
        int i;
        String str2;
        ArrayList arrayList;
        int i2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(onStatus, "onStatus");
        File file = new File(new File(projectPath), "src");
        File codeCacheDir = context.getCodeCacheDir();
        List<File> libraries = DependencyManager.INSTANCE.getLibraries(projectPath);
        ProjectConfig projectConfigLoadConfig = ProjectManager.INSTANCE.loadConfig(projectPath);
        File lambdaStubs = getLambdaStubs(context);
        File bootJar = getBootJar(context, projectConfigLoadConfig.getTargetSdk());
        File file2 = new File(codeCacheDir, "apk_build_" + System.currentTimeMillis());
        File file3 = new File(file2, "classes");
        file3.mkdirs();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        try {
            ArrayList arrayList4 = arrayList2;
            if (projectConfigLoadConfig.isProtected()) {
                onStatus.invoke("Защита: Подготовка...");
                File file4 = new File(file2, "src_temp");
                FilesKt.copyRecursively$default(file, file4, true, null, 4, null);
                File file5 = new File(file4, "game/Main.java");
                if (file5.exists()) {
                    String strReplace = new Regex("\\bMain::").replace(new Regex("\\bMain\\.").replace(StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(FilesKt.readText$default(file5, null, 1, null), "class Main", "class SecretMain", false, 4, (Object) null), "public Main(", "public SecretMain(", false, 4, (Object) null), "Main.this", "SecretMain.this", false, 4, (Object) null), "SecretMain."), "SecretMain::");
                    file5.delete();
                    FilesKt.writeText$default(new File(file4, "game/SecretMain.java"), strReplace, null, 2, null);
                }
                file = file4;
            }
            onStatus.invoke("Компиляция...");
            Map<String, byte[]> mapCompileDirectory = AndroidECJ.INSTANCE.compileDirectory(file, libraries, bootJar, lambdaStubs);
            if (mapCompileDirectory.isEmpty()) {
                throw new RuntimeException("Нет скомпилированных классов");
            }
            for (Map.Entry<String, byte[]> entry : mapCompileDirectory.entrySet()) {
                String key = entry.getKey();
                byte[] value = entry.getValue();
                File file6 = new File(file3, key);
                File parentFile = file6.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FilesKt.writeBytes(file6, value);
            }
            onStatus.invoke("Генерация DEX (D8)...");
            File file7 = new File(file2, "user_dex");
            file7.mkdirs();
            List<? extends File> list = SequencesKt.toList(SequencesKt.filter(FilesKt.walkTopDown(file3), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.ide.ScriptExecutor$compileForApk$classFiles$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(Intrinsics.areEqual(FilesKt.getExtension(it), "class"));
                }
            }));
            if (projectConfigLoadConfig.isObfuscated()) {
                onStatus.invoke("Обфускация (R8)...");
                File file8 = new File(projectPath, "proguard-rules.pro");
                if (file8.exists()) {
                    i2 = 2;
                } else {
                    projectConfigLoadConfig.isProtected();
                    i2 = 2;
                    FilesKt.writeText$default(file8, "# Правила ProGuard для R8.\n\n-keep public class game.Main {\n    public <init>();\n\n    public void onStart(...);\n}\n\n-keep public class game.SecretMain {\n    public <init>();\n\n    public void onStart(...);\n}", null, 2, null);
                    onStatus.invoke("Создан proguard-rules.pro");
                }
                R8Command.Builder minApiLevel = R8Command.builder().setMode(CompilationMode.RELEASE).setMinApiLevel(21);
                List<? extends File> list2 = list;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    arrayList5.add(((File) it.next()).toPath());
                }
                R8Command.Builder builder = (R8Command.Builder) ((R8Command.Builder) minApiLevel.addProgramFiles(arrayList5)).addLibraryFiles(bootJar.toPath());
                List<File> list3 = libraries;
                ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator<T> it2 = list3.iterator();
                while (it2.hasNext()) {
                    arrayList6.add(((File) it2.next()).toPath());
                }
                R8.run((R8Command) ((R8Command.Builder) builder.addClasspathFiles(arrayList6)).setOutput(file7.toPath(), OutputMode.DexIndexed).addProguardConfigurationFiles(file8.toPath()).build());
                str = "classes";
                i = i2;
            } else {
                onStatus.invoke("Генерация DEX (D8)...");
                str = "classes";
                i = 2;
                runD8(context, list, libraries, file7, 21, true, projectConfigLoadConfig.getTargetSdk());
            }
            if (!new File(file7, DexFormat.DEX_IN_JAR_NAME).exists()) {
                throw new RuntimeException("D8/R8 failed to produce classes.dex");
            }
            if (!libraries.isEmpty()) {
                onStatus.invoke("Обработка библиотек...");
                for (File file9 : libraries) {
                    try {
                        if (file9.length() >= 100) {
                            File file10 = new File(context.getCodeCacheDir(), "lib_d8_" + String.valueOf(file9.getName().hashCode()) + ".dex");
                            if (!file10.exists()) {
                                File file11 = new File(file2, "temp_lib_" + i);
                                file11.mkdirs();
                                runD8(context, CollectionsKt.listOf(file9), CollectionsKt.emptyList(), file11, 21, true, projectConfigLoadConfig.getTargetSdk());
                                FilesKt.copyTo$default(new File(file11, DexFormat.DEX_IN_JAR_NAME), file10, false, 0, 6, null);
                                FilesKt.deleteRecursively(file11);
                            }
                            str2 = str;
                            try {
                                File file12 = new File(file2, str2 + i + ".dex");
                                FilesKt.copyTo$default(file10, file12, true, 0, 4, null);
                                arrayList = arrayList4;
                                try {
                                    arrayList.add(file12);
                                    i++;
                                } catch (Exception unused) {
                                }
                            } catch (Exception unused2) {
                                arrayList = arrayList4;
                            }
                            arrayList4 = arrayList;
                            str = str2;
                        } else {
                            str2 = str;
                        }
                    } catch (Exception unused3) {
                    }
                    arrayList = arrayList4;
                    arrayList4 = arrayList;
                    str = str2;
                }
            }
            return new CompilationResult(arrayList4, arrayList3, null, 4, null);
        } catch (Exception e) {
            e.printStackTrace();
            onStatus.invoke("Ошибка: " + e.getMessage());
            return new CompilationResult(CollectionsKt.emptyList(), CollectionsKt.emptyList(), e);
        }
    }

    public static /* synthetic */ File processLibrary$default(ScriptExecutor scriptExecutor, Context context, File file, File file2, File file3, int i, int i2, Object obj) {
        if ((i2 & 16) != 0) {
            i = 33;
        }
        return scriptExecutor.processLibrary(context, file, file2, file3, i);
    }

    public final File processLibrary(Context context, File originLib, File outputDir, File nativeDir, int targetApi) throws CompilationFailedException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(originLib, "originLib");
        Intrinsics.checkNotNullParameter(outputDir, "outputDir");
        Intrinsics.checkNotNullParameter(nativeDir, "nativeDir");
        try {
            ZipFile zipFile = new ZipFile(originLib);
            try {
                ZipFile zipFile2 = zipFile;
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    String name2 = zipEntryNextElement.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (StringsKt.endsWith$default(name2, ".so", false, 2, (Object) null)) {
                        ScriptExecutor scriptExecutor = INSTANCE;
                        String name3 = zipEntryNextElement.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        InputStream inputStream = zipFile2.getInputStream(zipEntryNextElement);
                        Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                        scriptExecutor.extractNativeLib(name3, inputStream, nativeDir);
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipFile, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(zipFile, th);
                    throw th2;
                }
            }
        } catch (Exception unused) {
        }
        File file = new File(outputDir, originLib.getName());
        if (file.exists()) {
            return file;
        }
        File file2 = new File(outputDir, "dex_" + originLib.getName().hashCode());
        file2.mkdirs();
        runD8(context, CollectionsKt.listOf(originLib), CollectionsKt.emptyList(), file2, 26, false, targetApi);
        FilesKt.copyTo$default(originLib, file, true, 0, 4, null);
        addFileToJar(file, new File(file2, DexFormat.DEX_IN_JAR_NAME), DexFormat.DEX_IN_JAR_NAME);
        FilesKt.deleteRecursively(file2);
        file.setReadOnly();
        return file;
    }

    private final void extractNativeLib(String name2, InputStream input, File outputDir) {
        String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS, "SUPPORTED_ABIS");
        List list = ArraysKt.toList(SUPPORTED_ABIS);
        for (String str : CollectionsKt.listOf((Object[]) new String[]{"arm64-v8a", "armeabi-v7a", "x86", "x86_64"})) {
            if (StringsKt.contains$default((CharSequence) name2, (CharSequence) str, false, 2, (Object) null) && !list.contains(str)) {
                return;
            }
        }
        File file = new File(outputDir, new File(name2).getName());
        if (!file.exists() || file.length() <= 0) {
            InputStream inputStream = input;
            try {
                InputStream inputStream2 = inputStream;
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(inputStream, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(inputStream, th3);
                    throw th4;
                }
            }
        }
    }

    private final void addFileToJar(File jarFile, File fileToAdd, String entryName) {
        File file = new File(jarFile.getParent(), jarFile.getName() + ".tmp");
        if (fileToAdd.exists()) {
            ZipFile zipFile = new ZipFile(jarFile);
            try {
                ZipFile zipFile2 = zipFile;
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
                try {
                    ZipOutputStream zipOutputStream2 = zipOutputStream;
                    Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries();
                    while (enumerationEntries.hasMoreElements()) {
                        ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                        if (!Intrinsics.areEqual(zipEntryNextElement.getName(), entryName)) {
                            zipOutputStream2.putNextEntry(new ZipEntry(zipEntryNextElement.getName()));
                            InputStream inputStream = zipFile2.getInputStream(zipEntryNextElement);
                            Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                            ByteStreamsKt.copyTo$default(inputStream, zipOutputStream2, 0, 2, null);
                            zipOutputStream2.closeEntry();
                        }
                    }
                    zipOutputStream2.putNextEntry(new ZipEntry(entryName));
                    ByteStreamsKt.copyTo$default(new FileInputStream(fileToAdd), zipOutputStream2, 0, 2, null);
                    zipOutputStream2.closeEntry();
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(zipOutputStream, null);
                    Unit unit2 = Unit.INSTANCE;
                    CloseableKt.closeFinally(zipFile, null);
                    if (jarFile.exists()) {
                        jarFile.delete();
                    }
                    file.renameTo(jarFile);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(zipOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(zipFile, th3);
                    throw th4;
                }
            }
        }
    }

    private final void createJarFromClasses(File classesDir, File outputJar) {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputJar));
        try {
            ZipOutputStream zipOutputStream2 = zipOutputStream;
            for (File file : FilesKt.walkTopDown(classesDir)) {
                if (file.isFile()) {
                    zipOutputStream2.putNextEntry(new ZipEntry(FilesKt.toRelativeString(file, classesDir)));
                    ByteStreamsKt.copyTo$default(new FileInputStream(file), zipOutputStream2, 0, 2, null);
                    zipOutputStream2.closeEntry();
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(zipOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(zipOutputStream, th);
                throw th2;
            }
        }
    }

    private final File getBootJar(Context context, int targetApi) {
        File androidJar = IdeSettings.INSTANCE.getAndroidJar(context, targetApi);
        if (androidJar.exists()) {
            return androidJar;
        }
        throw new RuntimeException("Android SDK (android.jar) не найден! Скачайте API " + targetApi + " в настройках.");
    }

    private final File getLambdaStubs(Context context) {
        File file = new File(context.getFilesDir(), "core-lambda-stubs.jar");
        if (!file.exists()) {
            try {
                InputStream inputStreamOpen = context.getAssets().open("core-lambda-stubs.jar");
                try {
                    InputStream inputStream = inputStreamOpen;
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
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
                } catch (Throwable th3) {
                    try {
                        throw th3;
                    } catch (Throwable th4) {
                        CloseableKt.closeFinally(inputStreamOpen, th3);
                        throw th4;
                    }
                }
            } catch (IOException unused) {
                throw new RuntimeException("Ошибка: core-lambda-stubs.jar не найден в assets!");
            }
        }
        return file;
    }
}
