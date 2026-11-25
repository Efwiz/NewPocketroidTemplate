package org.eclipse.jdt.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.taskdefs.compilers.DefaultCompilerAdapter;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.util.JavaEnvUtils;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.antadapter.AntAdapterMessages;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class JDTCompilerAdapter extends DefaultCompilerAdapter {
    Map customDefaultOptions;
    String logFileName;
    private static final char[] SEPARATOR_CHARS = {'/', '\\'};
    private static final char[] ADAPTER_PREFIX = "#ADAPTER#".toCharArray();
    private static final char[] ADAPTER_ENCODING = "ENCODING#".toCharArray();
    private static final char[] ADAPTER_ACCESS = "ACCESS#".toCharArray();
    private static String compilerClass = "org.eclipse.jdt.internal.compiler.batch.Main";
    private Map fileEncodings = null;
    private Map dirEncodings = null;
    private List accessRules = null;

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.tools.ant.BuildException */
    public boolean execute() throws Throwable {
        this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.usingJDTCompiler"), 3);
        Commandline commandline = setupJavacCommand();
        try {
            Class<?> cls = Class.forName(compilerClass);
            boolean zBooleanValue = ((Boolean) cls.getMethod("compile", String[].class).invoke(cls.getConstructor(PrintWriter.class, PrintWriter.class, Boolean.TYPE, Map.class).newInstance(new PrintWriter(System.out), new PrintWriter(System.err), Boolean.TRUE, this.customDefaultOptions), commandline.getArguments())).booleanValue();
            if (!zBooleanValue && this.logFileName != null) {
                this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.error.compilationFailed", this.logFileName));
            }
            return zBooleanValue;
        } catch (ClassNotFoundException unused) {
            throw new BuildException(AntAdapterMessages.getString("ant.jdtadapter.error.cannotFindJDTCompiler"));
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    protected Commandline setupJavacCommand() throws Throwable {
        Method method;
        Path path;
        Method method2;
        int length;
        int i;
        Commandline commandline = new Commandline();
        this.customDefaultOptions = new CompilerOptions().getMap();
        String[] strArrProcessCompilerArguments = processCompilerArguments(Javac.class);
        commandline.createArgument().setValue("-noExit");
        if (this.bootclasspath != null) {
            commandline.createArgument().setValue("-bootclasspath");
            if (this.bootclasspath.size() != 0) {
                commandline.createArgument().setPath(this.bootclasspath);
            } else {
                commandline.createArgument().setValue(Util.EMPTY_STRING);
            }
        }
        if (this.extdirs != null) {
            commandline.createArgument().setValue("-extdirs");
            commandline.createArgument().setPath(this.extdirs);
        }
        Path path2 = new Path(this.project);
        path2.append(getCompileClasspath());
        commandline.createArgument().setValue("-classpath");
        createClasspathArgument(commandline, path2);
        String str = null;
        try {
            method = Javac.class.getMethod("getSourcepath", null);
        } catch (NoSuchMethodException unused) {
            method = null;
        }
        if (method != null) {
            try {
                path = (Path) method.invoke(this.attributes, null);
            } catch (IllegalAccessException | InvocationTargetException unused2) {
                path = null;
            }
        } else {
            path = null;
        }
        if (path == null) {
            path = this.src;
        }
        commandline.createArgument().setValue("-sourcepath");
        createClasspathArgument(commandline, path);
        String str2 = JavaEnvUtils.getJavaVersion().equals(CompilerOptions.VERSION_1_1) ? "-J-" : "-J-X";
        if (this.memoryInitialSize != null) {
            if (!this.attributes.isForkedJavac()) {
                this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.ignoringMemoryInitialSize"), 1);
            } else {
                commandline.createArgument().setValue(String.valueOf(str2) + LanguageCodeUtil.MS + this.memoryInitialSize);
            }
        }
        if (this.memoryMaximumSize != null) {
            if (!this.attributes.isForkedJavac()) {
                this.attributes.log(AntAdapterMessages.getString("ant.jdtadapter.info.ignoringMemoryMaximumSize"), 1);
            } else {
                commandline.createArgument().setValue(String.valueOf(str2) + "mx" + this.memoryMaximumSize);
            }
        }
        if (this.debug) {
            try {
                method2 = Javac.class.getMethod("getDebugLevel", null);
            } catch (NoSuchMethodException unused3) {
                method2 = null;
            }
            if (method2 != null) {
                try {
                    str = (String) method2.invoke(this.attributes, null);
                } catch (IllegalAccessException | InvocationTargetException unused4) {
                }
            }
            if (str == null) {
                this.customDefaultOptions.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
                this.customDefaultOptions.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
                this.customDefaultOptions.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);
            } else {
                this.customDefaultOptions.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.DO_NOT_GENERATE);
                this.customDefaultOptions.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.DO_NOT_GENERATE);
                this.customDefaultOptions.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.DO_NOT_GENERATE);
                if (str.length() != 0) {
                    if (str.indexOf("vars") != -1) {
                        this.customDefaultOptions.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
                    }
                    if (str.indexOf("lines") != -1) {
                        this.customDefaultOptions.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
                    }
                    if (str.indexOf(FirebaseAnalytics.Param.SOURCE) != -1) {
                        this.customDefaultOptions.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);
                    }
                }
            }
        } else {
            this.customDefaultOptions.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.DO_NOT_GENERATE);
            this.customDefaultOptions.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.DO_NOT_GENERATE);
            this.customDefaultOptions.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.DO_NOT_GENERATE);
        }
        if (this.attributes.getNowarn()) {
            for (Object obj : this.customDefaultOptions.entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if ((entry.getKey() instanceof String) && (entry.getValue() instanceof String) && ((String) entry.getValue()).equals(CompilerOptions.WARNING)) {
                    this.customDefaultOptions.put(entry.getKey(), "ignore");
                }
            }
            this.customDefaultOptions.put(CompilerOptions.OPTION_TaskTags, Util.EMPTY_STRING);
            if (this.deprecation) {
                this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.WARNING);
                this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationInDeprecatedCode, CompilerOptions.ENABLED);
                this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, CompilerOptions.ENABLED);
            }
        } else if (this.deprecation) {
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.WARNING);
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationInDeprecatedCode, CompilerOptions.ENABLED);
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, CompilerOptions.ENABLED);
        } else {
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecation, "ignore");
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationInDeprecatedCode, CompilerOptions.DISABLED);
            this.customDefaultOptions.put(CompilerOptions.OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, CompilerOptions.DISABLED);
        }
        if (this.destDir != null) {
            commandline.createArgument().setValue("-d");
            commandline.createArgument().setFile(this.destDir.getAbsoluteFile());
        }
        if (this.verbose) {
            commandline.createArgument().setValue("-verbose");
        }
        if (!this.attributes.getFailonerror()) {
            commandline.createArgument().setValue("-proceedOnError");
        }
        if (this.target != null) {
            this.customDefaultOptions.put(CompilerOptions.OPTION_TargetPlatform, this.target);
        }
        String source = this.attributes.getSource();
        if (source != null) {
            this.customDefaultOptions.put(CompilerOptions.OPTION_Source, source);
        }
        if (strArrProcessCompilerArguments != null && (length = strArrProcessCompilerArguments.length) != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                String str3 = strArrProcessCompilerArguments[i2];
                if (this.logFileName == null && "-log".equals(str3) && (i = i2 + 1) < length) {
                    this.logFileName = strArrProcessCompilerArguments[i];
                }
                commandline.createArgument().setValue(str3);
            }
        }
        if (this.encoding != null) {
            commandline.createArgument().setValue("-encoding");
            commandline.createArgument().setValue(this.encoding);
        }
        logAndAddFilesToCompile(commandline);
        return commandline;
    }

    private String[] processCompilerArguments(Class cls) throws Throwable {
        Method method;
        String[] strArr = null;
        try {
            method = cls.getMethod("getCurrentCompilerArgs", null);
        } catch (NoSuchMethodException unused) {
            method = null;
        }
        if (method != null) {
            try {
                strArr = (String[]) method.invoke(this.attributes, null);
            } catch (IllegalAccessException | InvocationTargetException unused2) {
            }
        }
        if (strArr != null) {
            checkCompilerArgs(strArr);
        }
        return strArr;
    }

    private void checkCompilerArgs(String[] strArr) throws Throwable {
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].charAt(0) == '@') {
                try {
                    char[] fileCharContent = Util.getFileCharContent(new File(strArr[i].substring(1)), null);
                    int length = ADAPTER_PREFIX.length;
                    int i2 = 0;
                    while (true) {
                        int iIndexOf = CharOperation.indexOf(ADAPTER_PREFIX, fileCharContent, true, i2);
                        if (iIndexOf <= -1) {
                            break;
                        }
                        int i3 = iIndexOf + length;
                        int iIndexOf2 = CharOperation.indexOf('\n', fileCharContent, i3);
                        int length2 = iIndexOf2 == -1 ? fileCharContent.length : iIndexOf2;
                        while (CharOperation.isWhitespace(fileCharContent[length2])) {
                            length2--;
                        }
                        char[] cArr = ADAPTER_ENCODING;
                        if (CharOperation.equals(cArr, fileCharContent, i3, cArr.length + i3)) {
                            CharOperation.replace(fileCharContent, SEPARATOR_CHARS, File.separatorChar, i3, length2 + 1);
                            int length3 = i3 + cArr.length;
                            int iLastIndexOf = CharOperation.lastIndexOf('[', fileCharContent, length3, length2);
                            if (length3 < iLastIndexOf && iLastIndexOf < length2) {
                                boolean zEquals = CharOperation.equals(SuffixConstants.SUFFIX_java, fileCharContent, iLastIndexOf - 5, iLastIndexOf, false);
                                String strValueOf = String.valueOf(fileCharContent, length3, iLastIndexOf - length3);
                                String strValueOf2 = String.valueOf(fileCharContent, iLastIndexOf, (length2 - iLastIndexOf) + 1);
                                if (zEquals) {
                                    if (this.fileEncodings == null) {
                                        this.fileEncodings = new HashMap();
                                    }
                                    this.fileEncodings.put(strValueOf, strValueOf2);
                                } else {
                                    if (this.dirEncodings == null) {
                                        this.dirEncodings = new HashMap();
                                    }
                                    this.dirEncodings.put(strValueOf, strValueOf2);
                                }
                            }
                        } else {
                            char[] cArr2 = ADAPTER_ACCESS;
                            if (CharOperation.equals(cArr2, fileCharContent, i3, cArr2.length + i3)) {
                                int length4 = i3 + cArr2.length;
                                int iIndexOf3 = CharOperation.indexOf('[', fileCharContent, length4, length2);
                                CharOperation.replace(fileCharContent, SEPARATOR_CHARS, File.separatorChar, length4, iIndexOf3);
                                if (length4 < iIndexOf3 && iIndexOf3 < length2) {
                                    String strValueOf3 = String.valueOf(fileCharContent, length4, iIndexOf3 - length4);
                                    String strValueOf4 = String.valueOf(fileCharContent, iIndexOf3, (length2 - iIndexOf3) + 1);
                                    if (this.accessRules == null) {
                                        this.accessRules = new ArrayList();
                                    }
                                    this.accessRules.add(strValueOf3);
                                    this.accessRules.add(strValueOf4);
                                }
                            }
                        }
                        i2 = length2;
                    }
                } catch (IOException unused) {
                }
            }
        }
    }

    private void createClasspathArgument(Commandline commandline, Path path) {
        int i;
        int i2;
        Commandline.Argument argumentCreateArgument = commandline.createArgument();
        String[] list = path.list();
        if (list.length == 0) {
            argumentCreateArgument.setValue(Util.EMPTY_STRING);
            return;
        }
        List list2 = this.accessRules;
        if (list2 == null) {
            argumentCreateArgument.setPath(path);
            return;
        }
        int size = list2.size();
        String[] strArr = (String[]) this.accessRules.toArray(new String[size]);
        StringBuffer stringBuffer = new StringBuffer();
        int length = list.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (i4 > 0) {
                stringBuffer.append(File.pathSeparatorChar);
            }
            String str = list[i4];
            stringBuffer.append(str);
            int i5 = i3;
            while (true) {
                if (i5 < size) {
                    String str2 = strArr[i5];
                    if (str.endsWith(str2)) {
                        stringBuffer.append(strArr[i5 + 1]);
                        i = i5 + 2;
                    } else if (str2.endsWith(File.separator)) {
                        int length2 = str2.length();
                        i2 = i5;
                        if (str.regionMatches(false, (str.length() - length2) + 1, str2, 0, length2 - 1)) {
                            stringBuffer.append(strArr[i2 + 1]);
                            i = i2 + 2;
                        } else {
                            i5 = i2 + 2;
                        }
                    } else {
                        i2 = i5;
                        if (str.endsWith(File.separator)) {
                            int length3 = str2.length();
                            if (str.regionMatches(false, (str.length() - length3) - 1, str2, 0, length3)) {
                                stringBuffer.append(strArr[i2 + 1]);
                                i = i2 + 2;
                            }
                        } else {
                            continue;
                        }
                        i5 = i2 + 2;
                    }
                    i3 = i;
                }
            }
        }
        argumentCreateArgument.setValue(stringBuffer.toString());
    }

    protected void logAndAddFilesToCompile(Commandline commandline) {
        String[] strArr;
        int size;
        String[] strArr2;
        int size2;
        boolean z;
        this.attributes.log("Compilation " + commandline.describeArguments(), 3);
        StringBuffer stringBuffer = new StringBuffer("File");
        if (this.compileList.length != 1) {
            stringBuffer.append("s");
        }
        stringBuffer.append(" to be compiled:");
        stringBuffer.append(lSep);
        Map map = this.fileEncodings;
        if (map != null) {
            size = map.size();
            strArr = new String[size];
            this.fileEncodings.keySet().toArray(strArr);
        } else {
            strArr = null;
            size = 0;
        }
        Map map2 = this.dirEncodings;
        if (map2 != null) {
            size2 = map2.size();
            strArr2 = new String[size2];
            this.dirEncodings.keySet().toArray(strArr2);
            Arrays.sort(strArr2, new Comparator() { // from class: org.eclipse.jdt.core.JDTCompilerAdapter.1
                @Override // java.util.Comparator
                public int compare(Object obj, Object obj2) {
                    return ((String) obj2).length() - ((String) obj).length();
                }
            });
        } else {
            strArr2 = null;
            size2 = 0;
        }
        for (int i = 0; i < this.compileList.length; i++) {
            String absolutePath = this.compileList[i].getAbsolutePath();
            if (strArr == null) {
                z = false;
                break;
            }
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    z = false;
                    break;
                }
                if (absolutePath.endsWith(strArr[i2])) {
                    absolutePath = String.valueOf(absolutePath) + ((String) this.fileEncodings.get(strArr[i2]));
                    if (i2 < size - 1) {
                        System.arraycopy(strArr, i2 + 1, strArr, i2, (size - i2) - 1);
                    }
                    size--;
                    strArr[size] = null;
                    z = true;
                    break;
                }
                i2++;
            }
            if (!z && strArr2 != null) {
                for (int i3 = 0; i3 < size2; i3++) {
                    if (absolutePath.lastIndexOf(strArr2[i3]) != -1) {
                        absolutePath = String.valueOf(absolutePath) + ((String) this.dirEncodings.get(strArr2[i3]));
                        break;
                    }
                }
            }
            commandline.createArgument().setValue(absolutePath);
            stringBuffer.append("    " + absolutePath + lSep);
        }
        this.attributes.log(stringBuffer.toString(), 3);
    }
}
