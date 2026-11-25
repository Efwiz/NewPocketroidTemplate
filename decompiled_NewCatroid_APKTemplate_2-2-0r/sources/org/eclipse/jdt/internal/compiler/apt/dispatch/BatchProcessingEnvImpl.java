package org.eclipse.jdt.internal.compiler.apt.dispatch;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.tools.JavaFileManager;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.apt.util.EclipseFileManager;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;

/* JADX INFO: loaded from: classes14.dex */
public class BatchProcessingEnvImpl extends BaseProcessingEnvImpl {
    protected final Main _compilerOwner;
    protected final BaseAnnotationProcessorManager _dispatchManager;
    protected final JavaFileManager _fileManager;

    public BatchProcessingEnvImpl(BaseAnnotationProcessorManager baseAnnotationProcessorManager, Main main, String[] strArr) {
        Class<?> cls;
        Field field;
        JavaFileManager javaFileManager;
        this._compilerOwner = main;
        this._compiler = main.batchCompiler;
        this._dispatchManager = baseAnnotationProcessorManager;
        try {
            cls = Class.forName("org.eclipse.jdt.internal.compiler.tool.EclipseCompilerImpl");
        } catch (ClassNotFoundException unused) {
            cls = null;
        }
        if (cls != null) {
            try {
                field = cls.getField("fileManager");
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException unused2) {
                field = null;
            }
        } else {
            field = null;
        }
        if (field != null) {
            try {
                javaFileManager = (JavaFileManager) field.get(main);
            } catch (IllegalAccessException | IllegalArgumentException unused3) {
                javaFileManager = null;
            }
        } else {
            javaFileManager = null;
        }
        if (javaFileManager != null) {
            this._fileManager = javaFileManager;
        } else {
            String str = main.options.get(CompilerOptions.OPTION_Encoding);
            EclipseFileManager eclipseFileManager = new EclipseFileManager(main.compilerLocale, str != null ? Charset.forName(str) : null);
            ArrayList arrayList = new ArrayList();
            for (String str2 : strArr) {
                arrayList.add(str2);
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                eclipseFileManager.handleOption((String) it.next(), it);
            }
            this._fileManager = eclipseFileManager;
        }
        this._processorOptions = Collections.unmodifiableMap(parseProcessorOptions(strArr));
        this._filer = new BatchFilerImpl(this._dispatchManager, this);
        this._messager = new BatchMessagerImpl(this, this._compilerOwner);
    }

    private Map<String, String> parseProcessorOptions(String[] strArr) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : strArr) {
            if (str.startsWith("-A")) {
                int iIndexOf = str.indexOf(61);
                if (iIndexOf == 2) {
                    throw new AbortCompilation((CompilationResult) null, new IllegalArgumentException("-A option must have a key before the equals sign"));
                }
                if (iIndexOf == str.length() - 1) {
                    linkedHashMap.put(str.substring(2, iIndexOf), null);
                } else if (iIndexOf == -1) {
                    linkedHashMap.put(str.substring(2), null);
                } else {
                    linkedHashMap.put(str.substring(2, iIndexOf), str.substring(iIndexOf + 1));
                }
            }
        }
        return linkedHashMap;
    }

    public JavaFileManager getFileManager() {
        return this._fileManager;
    }

    public Locale getLocale() {
        return this._compilerOwner.compilerLocale;
    }
}
