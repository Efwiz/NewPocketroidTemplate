package org.eclipse.jdt.internal.compiler.tool;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.WeakHashMap;
import javax.annotation.processing.Processor;
import javax.lang.model.SourceVersion;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems;

/* JADX INFO: loaded from: classes14.dex */
public class EclipseCompiler implements JavaCompiler {
    private static Set<SourceVersion> SupportedSourceVersions = Collections.unmodifiableSet(EnumSet.range(SourceVersion.RELEASE_0, SourceVersion.RELEASE_6));
    public DiagnosticListener<? super JavaFileObject> diagnosticListener;
    WeakHashMap<Thread, EclipseCompilerImpl> threadCache = new WeakHashMap<>();

    public Set<SourceVersion> getSourceVersions() {
        return SupportedSourceVersions;
    }

    public StandardJavaFileManager getStandardFileManager(DiagnosticListener<? super JavaFileObject> diagnosticListener, Locale locale, Charset charset) {
        this.diagnosticListener = diagnosticListener;
        return new EclipseFileManager(locale, charset);
    }

    public JavaCompiler.CompilationTask getTask(Writer writer, JavaFileManager javaFileManager, DiagnosticListener<? super JavaFileObject> diagnosticListener, Iterable<String> iterable, Iterable<String> iterable2, Iterable<? extends JavaFileObject> iterable3) {
        PrintWriter printWriter;
        PrintWriter printWriter2;
        Iterable location;
        if (writer == null) {
            printWriter = new PrintWriter(System.err);
            printWriter2 = new PrintWriter(System.err);
        } else {
            PrintWriter printWriter3 = new PrintWriter(writer);
            PrintWriter printWriter4 = new PrintWriter(writer);
            printWriter = printWriter3;
            printWriter2 = printWriter4;
        }
        Thread threadCurrentThread = Thread.currentThread();
        EclipseCompilerImpl eclipseCompilerImpl = this.threadCache.get(threadCurrentThread);
        int i = 0;
        if (eclipseCompilerImpl == null) {
            this.threadCache.put(threadCurrentThread, new EclipseCompilerImpl(printWriter, printWriter2, false));
        } else {
            eclipseCompilerImpl.initialize(printWriter, printWriter2, false, null, null);
        }
        final EclipseCompilerImpl eclipseCompilerImpl2 = new EclipseCompilerImpl(printWriter, printWriter2, false);
        eclipseCompilerImpl2.compilationUnits = iterable3;
        eclipseCompilerImpl2.diagnosticListener = diagnosticListener;
        if (javaFileManager != null) {
            eclipseCompilerImpl2.fileManager = javaFileManager;
        } else {
            eclipseCompilerImpl2.fileManager = getStandardFileManager(diagnosticListener, null, null);
        }
        eclipseCompilerImpl2.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_6);
        eclipseCompilerImpl2.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_6);
        eclipseCompilerImpl2.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
        ArrayList arrayList = new ArrayList();
        if (iterable != null) {
            Iterator<String> it = iterable.iterator();
            while (it.hasNext()) {
                eclipseCompilerImpl2.fileManager.handleOption(it.next(), it);
            }
            Iterator<String> it2 = iterable.iterator();
            while (it2.hasNext()) {
                arrayList.add(it2.next());
            }
        }
        if (iterable3 != null) {
            Iterator<? extends JavaFileObject> it3 = iterable3.iterator();
            while (it3.hasNext()) {
                URI uri = it3.next().toUri();
                if (!uri.isAbsolute()) {
                    uri = URI.create(StandardFileSystems.FILE_PROTOCOL_PREFIX + uri.toString());
                }
                if (uri.getScheme().equals("file")) {
                    arrayList.add(new File(uri).getAbsolutePath());
                } else {
                    arrayList.add(uri.toString());
                }
            }
        }
        if (iterable2 != null) {
            arrayList.add("-classNames");
            StringBuilder sb = new StringBuilder();
            for (String str : iterable2) {
                if (i != 0) {
                    sb.append(',');
                }
                sb.append(str);
                i++;
            }
            arrayList.add(String.valueOf(sb));
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        eclipseCompilerImpl2.configure(strArr);
        if ((eclipseCompilerImpl2.fileManager instanceof StandardJavaFileManager) && (location = eclipseCompilerImpl2.fileManager.getLocation(StandardLocation.CLASS_OUTPUT)) != null) {
            eclipseCompilerImpl2.setDestinationPath(((File) location.iterator().next()).getAbsolutePath());
        }
        return new JavaCompiler.CompilationTask() { // from class: org.eclipse.jdt.internal.compiler.tool.EclipseCompiler.1
            private boolean hasRun = false;

            /* JADX INFO: renamed from: call, reason: merged with bridge method [inline-methods] */
            public Boolean m6018call() {
                if (this.hasRun) {
                    throw new IllegalStateException("This task has already been run");
                }
                Boolean bool = eclipseCompilerImpl2.call() ? Boolean.TRUE : Boolean.FALSE;
                this.hasRun = true;
                return bool;
            }

            public void setLocale(Locale locale) {
                eclipseCompilerImpl2.setLocale(locale);
            }

            public void setProcessors(Iterable<? extends Processor> iterable4) {
                ArrayList arrayList2 = new ArrayList();
                Iterator<? extends Processor> it4 = iterable4.iterator();
                while (it4.hasNext()) {
                    arrayList2.add(it4.next());
                }
                Processor[] processorArr = new Processor[arrayList2.size()];
                arrayList2.toArray(processorArr);
                eclipseCompilerImpl2.processors = processorArr;
            }
        };
    }

    public int isSupportedOption(String str) {
        return Options.processOptions(str);
    }

    public int run(InputStream inputStream, OutputStream outputStream, OutputStream outputStream2, String... strArr) {
        if (outputStream == null) {
            outputStream = System.out;
        }
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
        if (outputStream2 == null) {
            outputStream2 = System.err;
        }
        return new Main(printWriter, new PrintWriter(new OutputStreamWriter(outputStream2)), true, null, null).compile(strArr) ? 0 : -1;
    }
}
