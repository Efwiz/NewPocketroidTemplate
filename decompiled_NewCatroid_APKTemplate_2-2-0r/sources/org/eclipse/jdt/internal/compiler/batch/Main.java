package org.eclipse.jdt.internal.compiler.batch;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.apkeditor.Options;
import io.netty.util.internal.StringUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.SystemProperties;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.AbstractAnnotationProcessorManager;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.Compiler;
import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.eclipse.jdt.internal.compiler.IProblemFactory;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRule;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.CompilerStats;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;
import org.eclipse.jdt.internal.compiler.util.GenericXMLWriter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfInt;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes14.dex */
public class Main implements ProblemSeverities, SuffixConstants {
    private static final String ANNOTATION_SOURCE_CLASSPATH = "CLASSPATH";
    public static final int DEFAULT_SIZE_CLASSPATH = 4;
    public static final String NONE = "none";
    public static final int TIMING_DETAILED = 2;
    public static final int TIMING_DISABLED = 0;
    public static final int TIMING_ENABLED = 1;
    public static final String bundleName = "org.eclipse.jdt.internal.compiler.batch.messages";
    protected List<String> annotationPaths;
    protected boolean annotationsFromClasspath;
    public Compiler batchCompiler;
    public ResourceBundle bundle;
    protected FileSystem.Classpath[] checkedClasspaths;
    public String[] classNames;
    public Locale compilerLocale;
    public CompilerOptions compilerOptions;
    public CompilerStats[] compilerStats;
    public int currentRepetition;
    public String destinationPath;
    public String[] destinationPaths;
    private boolean didSpecifySource;
    private boolean didSpecifyTarget;
    boolean enableJavadocOn;
    public String[] encodings;
    private PrintWriter err;
    private String[] expandedCommandLine;
    public int exportedClassFilesCounter;
    protected ArrayList extraProblems;
    public String[] filenames;
    public int globalErrorsCount;
    public int globalProblemsCount;
    public int globalTasksCount;
    public int globalWarningsCount;
    public char[][] ignoreOptionalProblemsFromFolders;
    private File javaHomeCache;
    private boolean javaHomeChecked;
    public long lineCount0;
    public String log;
    public Logger logger;
    public int maxProblems;
    public int maxRepetition;
    public Map<String, String> options;
    protected PrintWriter out;
    public ArrayList pendingErrors;
    private boolean primaryNullAnnotationsSeen;
    public boolean proceed;
    public boolean proceedOnError;
    public boolean produceRefInfo;
    public CompilationProgress progress;
    public boolean showProgress;
    public long startTime;
    public boolean systemExitWhenFinished;
    public int timing;
    public boolean verbose;
    boolean warnAllJavadocOn;
    boolean warnJavadocOn;

    public static class Logger {
        private static final String CLASS = "class";
        private static final String CLASSPATH = "classpath";
        private static final String CLASSPATHS = "classpaths";
        private static final String CLASSPATH_FILE = "FILE";
        private static final String CLASSPATH_FOLDER = "FOLDER";
        private static final String CLASSPATH_ID = "id";
        private static final String CLASSPATH_JAR = "JAR";
        private static final String CLASS_FILE = "classfile";
        private static final String COMMAND_LINE_ARGUMENT = "argument";
        private static final String COMMAND_LINE_ARGUMENTS = "command_line";
        private static final String COMPILER = "compiler";
        private static final String COMPILER_COPYRIGHT = "copyright";
        private static final String COMPILER_NAME = "name";
        private static final String COMPILER_VERSION = "version";
        public static final int EMACS = 2;
        private static final String ERROR = "ERROR";
        private static final String ERROR_TAG = "error";
        private static final String EXCEPTION = "exception";
        private static final String EXTRA_PROBLEMS = "extra_problems";
        private static final String EXTRA_PROBLEM_TAG = "extra_problem";
        private static final HashtableOfInt FIELD_TABLE = new HashtableOfInt();
        private static final String ID = "id";
        private static final String KEY = "key";
        private static final String MESSAGE = "message";
        private static final String NUMBER_OF_CLASSFILES = "number_of_classfiles";
        private static final String NUMBER_OF_ERRORS = "errors";
        private static final String NUMBER_OF_LINES = "number_of_lines";
        private static final String NUMBER_OF_PROBLEMS = "problems";
        private static final String NUMBER_OF_TASKS = "tasks";
        private static final String NUMBER_OF_WARNINGS = "warnings";
        private static final String OPTION = "option";
        private static final String OPTIONS = "options";
        private static final String OUTPUT = "output";
        private static final String PACKAGE = "package";
        private static final String PATH = "path";
        private static final String PROBLEMS = "problems";
        private static final String PROBLEM_ARGUMENT = "argument";
        private static final String PROBLEM_ARGUMENTS = "arguments";
        private static final String PROBLEM_ARGUMENT_VALUE = "value";
        private static final String PROBLEM_CATEGORY_ID = "categoryID";
        private static final String PROBLEM_ID = "problemID";
        private static final String PROBLEM_LINE = "line";
        private static final String PROBLEM_MESSAGE = "message";
        private static final String PROBLEM_OPTION_KEY = "optionKey";
        private static final String PROBLEM_SEVERITY = "severity";
        private static final String PROBLEM_SOURCE_END = "charEnd";
        private static final String PROBLEM_SOURCE_START = "charStart";
        private static final String PROBLEM_SUMMARY = "problem_summary";
        private static final String PROBLEM_TAG = "problem";
        private static final String SOURCE = "source";
        private static final String SOURCES = "sources";
        private static final String SOURCE_CONTEXT = "source_context";
        private static final String SOURCE_END = "sourceEnd";
        private static final String SOURCE_START = "sourceStart";
        private static final String STATS = "stats";
        private static final String TASK = "task";
        private static final String TASKS = "tasks";
        private static final String TIME = "time";
        private static final String VALUE = "value";
        private static final String WARNING = "WARNING";
        private static final String WARNING_TAG = "warning";
        public static final int XML = 1;
        private static final String XML_DTD_DECLARATION = "<!DOCTYPE compiler PUBLIC \"-//Eclipse.org//DTD Eclipse JDT 3.2.004 Compiler//EN\" \"http://www.eclipse.org/jdt/core/compiler_32_004.dtd\">";
        private PrintWriter err;
        private PrintWriter log;
        private Main main;
        private PrintWriter out;
        private HashMap parameters = new HashMap();
        int tagBits;

        static {
            try {
                for (Field field : IProblem.class.getFields()) {
                    if (field.getType().equals(Integer.TYPE)) {
                        int iIntValue = ((Integer) field.get(null)).intValue() & 16777215;
                        if (iIntValue == 0) {
                            iIntValue = Integer.MAX_VALUE;
                        }
                        FIELD_TABLE.put(iIntValue, field.getName());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            } catch (SecurityException e3) {
                e3.printStackTrace();
            }
        }

        public Logger(Main main, PrintWriter printWriter, PrintWriter printWriter2) {
            this.out = printWriter;
            this.err = printWriter2;
            this.main = main;
        }

        public String buildFileName(String str, String str2) {
            char c = File.separatorChar;
            String str3 = File.separator;
            String strReplace = str.replace('/', c);
            StringBuffer stringBuffer = new StringBuffer(strReplace);
            if (!strReplace.endsWith(str3)) {
                stringBuffer.append(str3);
            }
            StringTokenizer stringTokenizer = new StringTokenizer(str2, str3);
            String strNextToken = stringTokenizer.nextToken();
            while (stringTokenizer.hasMoreTokens()) {
                stringBuffer.append(strNextToken).append(str3);
                strNextToken = stringTokenizer.nextToken();
            }
            return stringBuffer.append(strNextToken).toString();
        }

        public void close() {
            if (this.log != null) {
                if ((this.tagBits & 1) != 0) {
                    endTag(COMPILER);
                    flush();
                }
                this.log.close();
            }
        }

        public void compiling() {
            printlnOut(this.main.bind("progress.compiling"));
        }

        private void endLoggingExtraProblems() {
            endTag(EXTRA_PROBLEMS);
        }

        private void endLoggingProblems() {
            endTag("problems");
        }

        public void endLoggingSource() {
            if ((this.tagBits & 1) != 0) {
                endTag("source");
            }
        }

        public void endLoggingSources() {
            if ((this.tagBits & 1) != 0) {
                endTag("sources");
            }
        }

        public void endLoggingTasks() {
            if ((this.tagBits & 1) != 0) {
                endTag("tasks");
            }
        }

        private void endTag(String str) {
            PrintWriter printWriter = this.log;
            if (printWriter != null) {
                ((GenericXMLWriter) printWriter).endTag(str, true, true);
            }
        }

        private String errorReportSource(CategorizedProblem categorizedProblem, char[] cArr, int i) throws Throwable {
            int length;
            char c;
            char c2;
            int sourceStart = categorizedProblem.getSourceStart();
            int sourceEnd = categorizedProblem.getSourceEnd();
            if (cArr == null && categorizedProblem.getOriginatingFileName() != null) {
                try {
                    cArr = Util.getFileCharContent(new File(new String(categorizedProblem.getOriginatingFileName())), null);
                } catch (IOException unused) {
                }
            }
            if (sourceStart > sourceEnd || ((sourceStart < 0 && sourceEnd < 0) || cArr == null || (length = cArr.length) == 0)) {
                return Messages.problem_noSourceInformation;
            }
            StringBuffer stringBuffer = new StringBuffer();
            if ((i & 2) == 0) {
                stringBuffer.append(StringUtil.SPACE).append(Messages.bind(Messages.problem_atLine, String.valueOf(categorizedProblem.getSourceLineNumber())));
                stringBuffer.append(Util.LINE_SEPARATOR);
            }
            stringBuffer.append('\t');
            int i2 = sourceStart >= length ? length - 1 : sourceStart;
            while (i2 > 0 && (c2 = cArr[i2 - 1]) != '\n' && c2 != '\r') {
                i2--;
            }
            int i3 = sourceEnd >= length ? length - 1 : sourceEnd;
            while (true) {
                int i4 = i3 + 1;
                if (i4 >= length || (c = cArr[i4]) == '\r' || c == '\n') {
                    break;
                }
                i3 = i4;
            }
            while (true) {
                char c3 = cArr[i2];
                if (c3 != ' ' && c3 != '\t') {
                    break;
                }
                i2++;
            }
            stringBuffer.append(cArr, i2, (i3 - i2) + 1);
            stringBuffer.append(Util.LINE_SEPARATOR).append("\t");
            while (i2 < sourceStart) {
                stringBuffer.append(cArr[i2] == '\t' ? '\t' : ' ');
                i2++;
            }
            while (true) {
                if (sourceStart <= (sourceEnd >= length ? length - 1 : sourceEnd)) {
                    stringBuffer.append(Util.C_EXCEPTION_START);
                    sourceStart++;
                } else {
                    return stringBuffer.toString();
                }
            }
        }

        private void extractContext(CategorizedProblem categorizedProblem, char[] cArr) throws Throwable {
            int length;
            char c;
            char c2;
            int sourceStart = categorizedProblem.getSourceStart();
            int sourceEnd = categorizedProblem.getSourceEnd();
            if (cArr == null && categorizedProblem.getOriginatingFileName() != null) {
                try {
                    cArr = Util.getFileCharContent(new File(new String(categorizedProblem.getOriginatingFileName())), null);
                } catch (IOException unused) {
                }
            }
            if (sourceStart > sourceEnd || ((sourceStart < 0 && sourceEnd < 0) || cArr == null || (length = cArr.length) <= 0 || sourceEnd > length)) {
                this.parameters.put("value", Messages.problem_noSourceInformation);
                this.parameters.put(SOURCE_START, ServerAuthenticationConstants.TOKEN_CODE_INVALID);
                this.parameters.put(SOURCE_END, ServerAuthenticationConstants.TOKEN_CODE_INVALID);
                printTag(SOURCE_CONTEXT, this.parameters, true, true);
                return;
            }
            int i = sourceStart >= length ? length - 1 : sourceStart;
            while (i > 0 && (c2 = cArr[i - 1]) != '\n' && c2 != '\r') {
                i--;
            }
            int i2 = sourceEnd >= length ? length - 1 : sourceEnd;
            while (true) {
                int i3 = i2 + 1;
                if (i3 >= length || (c = cArr[i3]) == '\r' || c == '\n') {
                    break;
                } else {
                    i2 = i3;
                }
            }
            while (true) {
                char c3 = cArr[i];
                if (c3 != ' ' && c3 != '\t') {
                    break;
                } else {
                    i++;
                }
            }
            while (true) {
                char c4 = cArr[i2];
                if (c4 != ' ' && c4 != '\t') {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(cArr, i, (i2 - i) + 1);
                    this.parameters.put("value", String.valueOf(stringBuffer));
                    this.parameters.put(SOURCE_START, Integer.toString(sourceStart - i));
                    this.parameters.put(SOURCE_END, Integer.toString(sourceEnd - i));
                    printTag(SOURCE_CONTEXT, this.parameters, true, true);
                    return;
                }
                i2--;
            }
        }

        public void flush() {
            this.out.flush();
            this.err.flush();
            PrintWriter printWriter = this.log;
            if (printWriter != null) {
                printWriter.flush();
            }
        }

        private String getFieldName(int i) {
            int i2 = i & 16777215;
            if (i2 == 0) {
                i2 = Integer.MAX_VALUE;
            }
            return (String) FIELD_TABLE.get(i2);
        }

        private String getProblemOptionKey(int i) {
            return CompilerOptions.optionKeyFromIrritant(ProblemReporter.getIrritant(i));
        }

        public void logAverage() {
            Logger logger = this;
            Arrays.sort(logger.main.compilerStats);
            boolean z = false;
            long j = logger.main.compilerStats[0].lineCount;
            int i = logger.main.maxRepetition;
            int i2 = i - 1;
            long jElapsedTime = 0;
            long j2 = 0;
            long j3 = 0;
            long j4 = 0;
            long j5 = 0;
            int i3 = 1;
            while (i3 < i2) {
                long j6 = j;
                CompilerStats compilerStats = logger.main.compilerStats[i3];
                jElapsedTime += compilerStats.elapsedTime();
                j2 += compilerStats.parseTime;
                j3 += compilerStats.resolveTime;
                j4 += compilerStats.analyzeTime;
                j5 += compilerStats.generateTime;
                i3++;
                logger = this;
                j = j6;
                i = i;
                i2 = i2;
                z = z;
            }
            long j7 = j;
            long j8 = i - 2;
            long j9 = jElapsedTime / j8;
            long j10 = j2 / j8;
            long j11 = j3 / j8;
            long j12 = j4 / j8;
            long j13 = j5 / j8;
            double d = j9;
            logger.printlnOut(logger.main.bind("compile.averageTime", new String[]{String.valueOf(j7), String.valueOf(j9), String.valueOf(((double) ((int) ((j7 * 10000.0d) / d))) / 10.0d)}));
            if ((logger.main.timing & 2) != 0) {
                logger.printlnOut(logger.main.bind("compile.detailedTime", new String[]{String.valueOf(j10), String.valueOf(((double) ((int) ((j10 * 1000.0d) / d))) / 10.0d), String.valueOf(j11), String.valueOf(((double) ((int) ((j11 * 1000.0d) / d))) / 10.0d), String.valueOf(j12), String.valueOf(((double) ((int) ((j12 * 1000.0d) / d))) / 10.0d), String.valueOf(j13), String.valueOf(((double) ((int) ((j13 * 1000.0d) / d))) / 10.0d)}));
            }
        }

        public void logClassFile(boolean z, String str, String str2) {
            String strBuildFileName;
            if ((this.tagBits & 1) != 0) {
                if (z) {
                    strBuildFileName = buildFileName(str, str2);
                } else {
                    char c = File.separatorChar;
                    String str3 = File.separator;
                    str = str.replace('/', c);
                    int iLastIndexOf = str2.lastIndexOf(c);
                    if (iLastIndexOf == -1) {
                        if (str.endsWith(str3)) {
                            strBuildFileName = String.valueOf(str) + str2;
                        } else {
                            strBuildFileName = String.valueOf(str) + str3 + str2;
                        }
                    } else {
                        int length = str2.length();
                        if (str.endsWith(str3)) {
                            strBuildFileName = String.valueOf(str) + str2.substring(iLastIndexOf + 1, length);
                        } else {
                            strBuildFileName = String.valueOf(str) + str3 + str2.substring(iLastIndexOf + 1, length);
                        }
                    }
                }
                try {
                    this.parameters.put("path", new File(strBuildFileName).getCanonicalPath());
                    printTag(CLASS_FILE, this.parameters, true, true);
                } catch (IOException e) {
                    logNoClassFileCreated(str, str2, e);
                }
            }
        }

        public void logClasspath(FileSystem.Classpath[] classpathArr) {
            String str;
            if (classpathArr == null || (this.tagBits & 1) == 0 || (classpathArr.length) == 0) {
                return;
            }
            printTag(CLASSPATHS, null, true, false);
            for (FileSystem.Classpath classpath : classpathArr) {
                String path = classpath.getPath();
                this.parameters.put("path", path);
                File file = new File(path);
                if (file.isFile()) {
                    if (Util.isPotentialZipArchive(path)) {
                        str = CLASSPATH_JAR;
                    } else {
                        str = CLASSPATH_FILE;
                    }
                } else {
                    str = file.isDirectory() ? CLASSPATH_FOLDER : null;
                }
                if (str != null) {
                    this.parameters.put("id", str);
                    printTag("classpath", this.parameters, true, true);
                }
            }
            endTag(CLASSPATHS);
        }

        public void logCommandLineArguments(String[] strArr) {
            if (strArr == null || (this.tagBits & 1) == 0 || (strArr.length) == 0) {
                return;
            }
            printTag(COMMAND_LINE_ARGUMENTS, null, true, false);
            for (String str : strArr) {
                this.parameters.put("value", str);
                printTag("argument", this.parameters, true, true);
            }
            endTag(COMMAND_LINE_ARGUMENTS);
        }

        public void logException(Exception exc) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exc.printStackTrace(printWriter);
            printWriter.flush();
            printWriter.close();
            String string = stringWriter.toString();
            if ((this.tagBits & 1) != 0) {
                LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(string));
                StringBuffer stringBuffer = new StringBuffer();
                String message = exc.getMessage();
                if (message != null) {
                    stringBuffer.append(message).append(Util.LINE_SEPARATOR);
                }
                int i = 0;
                while (true) {
                    try {
                        String line = lineNumberReader.readLine();
                        if (line == null || i >= 4) {
                            break;
                            break;
                        } else {
                            stringBuffer.append(line).append(Util.LINE_SEPARATOR);
                            i++;
                        }
                    } catch (IOException unused) {
                    }
                }
                lineNumberReader.close();
                this.parameters.put(CrashHianalyticsData.MESSAGE, stringBuffer.toString());
                this.parameters.put("class", exc.getClass());
                printTag("exception", this.parameters, true, true);
            }
            String message2 = exc.getMessage();
            if (message2 == null) {
                printlnErr(string);
            } else {
                printlnErr(message2);
            }
        }

        private void logExtraProblem(CategorizedProblem categorizedProblem, int i, int i2) {
            String strBind;
            Main main;
            String str;
            char[] originatingFileName = categorizedProblem.getOriginatingFileName();
            if (originatingFileName == null) {
                if (categorizedProblem.isError()) {
                    printErr(this.main.bind("requestor.extraerror", Integer.toString(i2)));
                } else {
                    printErr(this.main.bind("requestor.extrawarning", Integer.toString(i2)));
                }
                printErr(" ");
                printlnErr(categorizedProblem.getMessage());
                return;
            }
            String str2 = new String(originatingFileName);
            if ((this.tagBits & 2) != 0) {
                StringBuilder sbAppend = new StringBuilder(String.valueOf(str2)).append(":").append(categorizedProblem.getSourceLineNumber()).append(": ");
                if (categorizedProblem.isError()) {
                    main = this.main;
                    str = "output.emacs.error";
                } else {
                    main = this.main;
                    str = "output.emacs.warning";
                }
                printlnErr(sbAppend.append(main.bind(str)).append(": ").append(categorizedProblem.getMessage()).toString());
                printlnErr(errorReportSource(categorizedProblem, null, this.tagBits));
                return;
            }
            if (i == 0) {
                printlnErr("----------");
            }
            if (categorizedProblem.isError()) {
                strBind = this.main.bind("requestor.error", Integer.toString(i2), new String(str2));
            } else {
                strBind = this.main.bind("requestor.warning", Integer.toString(i2), new String(str2));
            }
            printErr(strBind);
            printlnErr(errorReportSource(categorizedProblem, null, 0));
            printlnErr(categorizedProblem.getMessage());
            printlnErr("----------");
        }

        public void loggingExtraProblems(Main main) throws Throwable {
            ArrayList arrayList = main.extraProblems;
            int size = arrayList.size();
            if (size != 0) {
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    CategorizedProblem categorizedProblem = (CategorizedProblem) arrayList.get(i4);
                    if (categorizedProblem != null) {
                        main.globalProblemsCount++;
                        logExtraProblem(categorizedProblem, i, main.globalProblemsCount);
                        i++;
                        if (categorizedProblem.isError()) {
                            i2++;
                            main.globalErrorsCount++;
                        } else if (categorizedProblem.isWarning()) {
                            main.globalWarningsCount++;
                            i3++;
                        }
                    }
                }
                if ((this.tagBits & 1) == 0 || i2 + i3 == 0) {
                    return;
                }
                startLoggingExtraProblems(size);
                for (int i5 = 0; i5 < size; i5++) {
                    CategorizedProblem categorizedProblem2 = (CategorizedProblem) arrayList.get(i5);
                    if (categorizedProblem2 != null && categorizedProblem2.getID() != 536871362) {
                        logXmlExtraProblem(categorizedProblem2, i, main.globalProblemsCount);
                    }
                }
                endLoggingExtraProblems();
            }
        }

        public void logUnavaibleAPT(String str) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, this.main.bind("configure.unavailableAPT", str));
                printTag("error", this.parameters, true, true);
            }
            printlnErr(this.main.bind("configure.unavailableAPT", str));
        }

        public void logIncorrectVMVersionForAnnotationProcessing() {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, this.main.bind("configure.incorrectVMVersionforAPT"));
                printTag("error", this.parameters, true, true);
            }
            printlnErr(this.main.bind("configure.incorrectVMVersionforAPT"));
        }

        public void logNoClassFileCreated(String str, String str2, IOException iOException) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, this.main.bind("output.noClassFileCreated", new String[]{str, str2, iOException.getMessage()}));
                printTag("error", this.parameters, true, true);
            }
            printlnErr(this.main.bind("output.noClassFileCreated", new String[]{str, str2, iOException.getMessage()}));
        }

        public void logNumberOfClassFilesGenerated(int i) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put("value", Integer.valueOf(i));
                printTag(NUMBER_OF_CLASSFILES, this.parameters, true, true);
            }
            if (i == 1) {
                printlnOut(this.main.bind("compile.oneClassFileGenerated"));
            } else {
                printlnOut(this.main.bind("compile.severalClassFilesGenerated", String.valueOf(i)));
            }
        }

        public void logOptions(Map<String, String> map) {
            if ((this.tagBits & 1) != 0) {
                printTag(OPTIONS, null, true, false);
                Set<Map.Entry<String, String>> setEntrySet = map.entrySet();
                Map.Entry[] entryArr = (Map.Entry[]) setEntrySet.toArray(new Map.Entry[setEntrySet.size()]);
                Arrays.sort(entryArr, new Comparator<Map.Entry<String, String>>() { // from class: org.eclipse.jdt.internal.compiler.batch.Main.Logger.1
                    @Override // java.util.Comparator
                    public int compare(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                        return entry.getKey().compareTo(entry2.getKey());
                    }
                });
                for (Map.Entry entry : entryArr) {
                    this.parameters.put("key", (String) entry.getKey());
                    this.parameters.put("value", entry.getValue());
                    printTag(OPTION, this.parameters, true, true);
                }
                endTag(OPTIONS);
            }
        }

        public void logPendingError(String str) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, str);
                printTag("error", this.parameters, true, true);
            }
            printlnErr(str);
        }

        public void logWarning(String str) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, str);
                printTag("warning", this.parameters, true, true);
            }
            printlnOut(str);
        }

        private void logProblem(CategorizedProblem categorizedProblem, int i, int i2, char[] cArr) throws Throwable {
            String strBind;
            Main main;
            String str;
            if ((this.tagBits & 2) != 0) {
                StringBuilder sbAppend = new StringBuilder(String.valueOf(new String(categorizedProblem.getOriginatingFileName()))).append(":").append(categorizedProblem.getSourceLineNumber()).append(": ");
                if (categorizedProblem.isError()) {
                    main = this.main;
                    str = "output.emacs.error";
                } else {
                    main = this.main;
                    str = "output.emacs.warning";
                }
                printlnErr(sbAppend.append(main.bind(str)).append(": ").append(categorizedProblem.getMessage()).toString());
                String strErrorReportSource = errorReportSource(categorizedProblem, cArr, this.tagBits);
                if (strErrorReportSource.length() != 0) {
                    printlnErr(strErrorReportSource);
                    return;
                }
                return;
            }
            if (i == 0) {
                printlnErr("----------");
            }
            if (categorizedProblem.isError()) {
                strBind = this.main.bind("requestor.error", Integer.toString(i2), new String(categorizedProblem.getOriginatingFileName()));
            } else {
                strBind = this.main.bind("requestor.warning", Integer.toString(i2), new String(categorizedProblem.getOriginatingFileName()));
            }
            printErr(strBind);
            try {
                printlnErr(errorReportSource(categorizedProblem, cArr, 0));
                printlnErr(categorizedProblem.getMessage());
            } catch (Exception unused) {
                printlnErr(this.main.bind("requestor.notRetrieveErrorMessage", categorizedProblem.toString()));
            }
            printlnErr("----------");
        }

        public int logProblems(CategorizedProblem[] categorizedProblemArr, char[] cArr, Main main) throws Throwable {
            if (categorizedProblemArr.length == 0) {
                return 0;
            }
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            for (CategorizedProblem categorizedProblem : categorizedProblemArr) {
                if (categorizedProblem != null) {
                    main.globalProblemsCount++;
                    logProblem(categorizedProblem, i, main.globalProblemsCount, cArr);
                    i++;
                    if (categorizedProblem.isError()) {
                        i4++;
                        i2++;
                        main.globalErrorsCount++;
                    } else if (categorizedProblem.getID() == 536871362) {
                        main.globalTasksCount++;
                        i5++;
                    } else {
                        main.globalWarningsCount++;
                        i3++;
                    }
                }
            }
            if ((this.tagBits & 1) != 0) {
                if (i2 + i3 != 0) {
                    startLoggingProblems(i2, i3);
                    for (CategorizedProblem categorizedProblem2 : categorizedProblemArr) {
                        if (categorizedProblem2 != null && categorizedProblem2.getID() != 536871362) {
                            logXmlProblem(categorizedProblem2, cArr);
                        }
                    }
                    endLoggingProblems();
                }
                if (i5 != 0) {
                    startLoggingTasks(i5);
                    for (CategorizedProblem categorizedProblem3 : categorizedProblemArr) {
                        if (categorizedProblem3 != null && categorizedProblem3.getID() == 536871362) {
                            logXmlTask(categorizedProblem3, cArr);
                        }
                    }
                    endLoggingTasks();
                }
            }
            return i4;
        }

        public void logProblemsSummary(int i, int i2, int i3, int i4) {
            String strBind;
            String strBind2;
            if ((this.tagBits & 1) != 0) {
                this.parameters.put("problems", Integer.valueOf(i));
                this.parameters.put("errors", Integer.valueOf(i2));
                this.parameters.put("warnings", Integer.valueOf(i3));
                this.parameters.put("tasks", Integer.valueOf(i4));
                printTag(PROBLEM_SUMMARY, this.parameters, true, true);
            }
            if (i == 1) {
                if (i2 == 1) {
                    strBind2 = this.main.bind("compile.oneError");
                } else {
                    strBind2 = this.main.bind("compile.oneWarning");
                }
                printErr(this.main.bind("compile.oneProblem", strBind2));
            } else {
                String strBind3 = null;
                if (i2 <= 0) {
                    strBind = null;
                } else if (i2 == 1) {
                    strBind = this.main.bind("compile.oneError");
                } else {
                    strBind = this.main.bind("compile.severalErrors", String.valueOf(i2));
                }
                int i5 = i3 + i4;
                if (i5 > 0) {
                    if (i5 == 1) {
                        strBind3 = this.main.bind("compile.oneWarning");
                    } else {
                        strBind3 = this.main.bind("compile.severalWarnings", String.valueOf(i5));
                    }
                }
                if (strBind != null && strBind3 != null) {
                    printErr(this.main.bind("compile.severalProblemsErrorsAndWarnings", new String[]{String.valueOf(i), strBind, strBind3}));
                } else if (strBind == null) {
                    printErr(this.main.bind("compile.severalProblemsErrorsOrWarnings", String.valueOf(i), strBind3));
                } else {
                    printErr(this.main.bind("compile.severalProblemsErrorsOrWarnings", String.valueOf(i), strBind));
                }
            }
            if ((this.tagBits & 1) == 0) {
                printlnErr();
            }
        }

        public void logProgress() {
            printOut('.');
        }

        public void logRepetition(int i, int i2) {
            printlnOut(this.main.bind("compile.repetition", String.valueOf(i + 1), String.valueOf(i2)));
        }

        public void logTiming(CompilerStats compilerStats) {
            long jElapsedTime = compilerStats.elapsedTime();
            long j = compilerStats.lineCount;
            if ((this.tagBits & 1) != 0) {
                this.parameters.put("value", Long.valueOf(jElapsedTime));
                printTag("time", this.parameters, true, true);
                this.parameters.put("value", Long.valueOf(j));
                printTag(NUMBER_OF_LINES, this.parameters, true, true);
            }
            if (j != 0) {
                printlnOut(this.main.bind("compile.instantTime", new String[]{String.valueOf(j), String.valueOf(jElapsedTime), String.valueOf(((double) ((int) ((j * 10000.0d) / jElapsedTime))) / 10.0d)}));
            } else {
                printlnOut(this.main.bind("compile.totalTime", new String[]{String.valueOf(jElapsedTime)}));
            }
            if ((this.main.timing & 2) != 0) {
                double d = jElapsedTime;
                printlnOut(this.main.bind("compile.detailedTime", new String[]{String.valueOf(compilerStats.parseTime), String.valueOf(((double) ((int) ((compilerStats.parseTime * 1000.0d) / d))) / 10.0d), String.valueOf(compilerStats.resolveTime), String.valueOf(((double) ((int) ((compilerStats.resolveTime * 1000.0d) / d))) / 10.0d), String.valueOf(compilerStats.analyzeTime), String.valueOf(((double) ((int) ((compilerStats.analyzeTime * 1000.0d) / d))) / 10.0d), String.valueOf(compilerStats.generateTime), String.valueOf(((double) ((int) ((compilerStats.generateTime * 1000.0d) / d))) / 10.0d)}));
            }
        }

        public void logUsage(String str) {
            printlnOut(str);
        }

        public void logVersion(boolean z) {
            if (this.log == null || (this.tagBits & 1) != 0) {
                if (z) {
                    Main main = this.main;
                    this.out.println(main.bind("misc.version", new String[]{main.bind("compiler.name"), this.main.bind("compiler.version"), this.main.bind("compiler.copyright")}));
                    this.out.flush();
                    return;
                }
                return;
            }
            Main main2 = this.main;
            String strBind = main2.bind("misc.version", new String[]{main2.bind("compiler.name"), this.main.bind("compiler.version"), this.main.bind("compiler.copyright")});
            this.log.println("# " + strBind);
            if (z) {
                this.out.println(strBind);
                this.out.flush();
            }
        }

        public void logWrongJDK() {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put(CrashHianalyticsData.MESSAGE, this.main.bind("configure.requiresJDK1.2orAbove"));
                printTag("ERROR", this.parameters, true, true);
            }
            printlnErr(this.main.bind("configure.requiresJDK1.2orAbove"));
        }

        private void logXmlExtraProblem(CategorizedProblem categorizedProblem, int i, int i2) throws Throwable {
            int sourceStart = categorizedProblem.getSourceStart();
            int sourceEnd = categorizedProblem.getSourceEnd();
            this.parameters.put(PROBLEM_SEVERITY, categorizedProblem.isError() ? "ERROR" : WARNING);
            this.parameters.put("line", Integer.valueOf(categorizedProblem.getSourceLineNumber()));
            this.parameters.put(PROBLEM_SOURCE_START, Integer.valueOf(sourceStart));
            this.parameters.put(PROBLEM_SOURCE_END, Integer.valueOf(sourceEnd));
            printTag(EXTRA_PROBLEM_TAG, this.parameters, true, false);
            this.parameters.put("value", categorizedProblem.getMessage());
            printTag(CrashHianalyticsData.MESSAGE, this.parameters, true, true);
            extractContext(categorizedProblem, null);
            endTag(EXTRA_PROBLEM_TAG);
        }

        private void logXmlProblem(CategorizedProblem categorizedProblem, char[] cArr) throws Throwable {
            int sourceStart = categorizedProblem.getSourceStart();
            int sourceEnd = categorizedProblem.getSourceEnd();
            int id2 = categorizedProblem.getID();
            this.parameters.put("id", getFieldName(id2));
            this.parameters.put(PROBLEM_ID, Integer.valueOf(id2));
            boolean zIsError = categorizedProblem.isError();
            this.parameters.put(PROBLEM_SEVERITY, zIsError ? "ERROR" : WARNING);
            this.parameters.put("line", Integer.valueOf(categorizedProblem.getSourceLineNumber()));
            this.parameters.put(PROBLEM_SOURCE_START, Integer.valueOf(sourceStart));
            this.parameters.put(PROBLEM_SOURCE_END, Integer.valueOf(sourceEnd));
            String problemOptionKey = getProblemOptionKey(id2);
            if (problemOptionKey != null) {
                this.parameters.put(PROBLEM_OPTION_KEY, problemOptionKey);
            }
            this.parameters.put(PROBLEM_CATEGORY_ID, Integer.valueOf(ProblemReporter.getProblemCategory(zIsError ? 1 : 0, id2)));
            printTag(PROBLEM_TAG, this.parameters, true, false);
            this.parameters.put("value", categorizedProblem.getMessage());
            printTag(CrashHianalyticsData.MESSAGE, this.parameters, true, true);
            extractContext(categorizedProblem, cArr);
            String[] arguments = categorizedProblem.getArguments();
            if (arguments.length != 0) {
                printTag(PROBLEM_ARGUMENTS, null, true, false);
                for (String str : arguments) {
                    this.parameters.put("value", str);
                    printTag("argument", this.parameters, true, true);
                }
                endTag(PROBLEM_ARGUMENTS);
            }
            endTag(PROBLEM_TAG);
        }

        private void logXmlTask(CategorizedProblem categorizedProblem, char[] cArr) throws Throwable {
            this.parameters.put("line", Integer.valueOf(categorizedProblem.getSourceLineNumber()));
            this.parameters.put(PROBLEM_SOURCE_START, Integer.valueOf(categorizedProblem.getSourceStart()));
            this.parameters.put(PROBLEM_SOURCE_END, Integer.valueOf(categorizedProblem.getSourceEnd()));
            String problemOptionKey = getProblemOptionKey(categorizedProblem.getID());
            if (problemOptionKey != null) {
                this.parameters.put(PROBLEM_OPTION_KEY, problemOptionKey);
            }
            printTag(TASK, this.parameters, true, false);
            this.parameters.put("value", categorizedProblem.getMessage());
            printTag(CrashHianalyticsData.MESSAGE, this.parameters, true, true);
            extractContext(categorizedProblem, cArr);
            endTag(TASK);
        }

        private void printErr(String str) {
            PrintWriter printWriter;
            this.err.print(str);
            if ((this.tagBits & 1) != 0 || (printWriter = this.log) == null) {
                return;
            }
            printWriter.print(str);
        }

        private void printlnErr() {
            PrintWriter printWriter;
            this.err.println();
            if ((this.tagBits & 1) != 0 || (printWriter = this.log) == null) {
                return;
            }
            printWriter.println();
        }

        private void printlnErr(String str) {
            PrintWriter printWriter;
            this.err.println(str);
            if ((this.tagBits & 1) != 0 || (printWriter = this.log) == null) {
                return;
            }
            printWriter.println(str);
        }

        private void printlnOut(String str) {
            PrintWriter printWriter;
            this.out.println(str);
            if ((this.tagBits & 1) != 0 || (printWriter = this.log) == null) {
                return;
            }
            printWriter.println(str);
        }

        public void printNewLine() {
            this.out.println();
        }

        private void printOut(char c) {
            this.out.print(c);
        }

        public void printStats() {
            boolean z = (this.main.timing & 1) != 0;
            if ((this.tagBits & 1) != 0) {
                printTag(STATS, null, true, false);
            }
            if (z) {
                CompilerStats compilerStats = this.main.batchCompiler.stats;
                compilerStats.startTime = this.main.startTime;
                compilerStats.endTime = System.currentTimeMillis();
                logTiming(compilerStats);
            }
            if (this.main.globalProblemsCount > 0) {
                logProblemsSummary(this.main.globalProblemsCount, this.main.globalErrorsCount, this.main.globalWarningsCount, this.main.globalTasksCount);
            }
            if (this.main.exportedClassFilesCounter != 0 && (this.main.showProgress || z || this.main.verbose)) {
                logNumberOfClassFilesGenerated(this.main.exportedClassFilesCounter);
            }
            if ((this.tagBits & 1) != 0) {
                endTag(STATS);
            }
        }

        private void printTag(String str, HashMap map, boolean z, boolean z2) {
            PrintWriter printWriter = this.log;
            if (printWriter != null) {
                ((GenericXMLWriter) printWriter).printTag(str, this.parameters, true, z, z2);
            }
            this.parameters.clear();
        }

        public void setEmacs() {
            this.tagBits |= 2;
        }

        public void setLog(String str) {
            Date date = new Date();
            DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(3, 1, Locale.getDefault());
            try {
                int iLastIndexOf = str.lastIndexOf(46);
                if (iLastIndexOf != -1) {
                    if (str.substring(iLastIndexOf).toLowerCase().equals(".xml")) {
                        GenericXMLWriter genericXMLWriter = new GenericXMLWriter((Writer) new OutputStreamWriter(new FileOutputStream(str, false), "UTF-8"), Util.LINE_SEPARATOR, true);
                        this.log = genericXMLWriter;
                        this.tagBits |= 1;
                        genericXMLWriter.println("<!-- " + dateTimeInstance.format(date) + " -->");
                        this.log.println(XML_DTD_DECLARATION);
                        this.parameters.put("name", this.main.bind("compiler.name"));
                        this.parameters.put("version", this.main.bind("compiler.version"));
                        this.parameters.put(COMPILER_COPYRIGHT, this.main.bind("compiler.copyright"));
                        printTag(COMPILER, this.parameters, true, false);
                        return;
                    }
                    PrintWriter printWriter = new PrintWriter(new FileOutputStream(str, false));
                    this.log = printWriter;
                    printWriter.println("# " + dateTimeInstance.format(date));
                    return;
                }
                PrintWriter printWriter2 = new PrintWriter(new FileOutputStream(str, false));
                this.log = printWriter2;
                printWriter2.println("# " + dateTimeInstance.format(date));
            } catch (FileNotFoundException unused) {
                throw new IllegalArgumentException(this.main.bind("configure.cannotOpenLog", str));
            } catch (UnsupportedEncodingException unused2) {
                throw new IllegalArgumentException(this.main.bind("configure.cannotOpenLogInvalidEncoding", str));
            }
        }

        private void startLoggingExtraProblems(int i) {
            this.parameters.put("problems", Integer.valueOf(i));
            printTag(EXTRA_PROBLEMS, this.parameters, true, false);
        }

        private void startLoggingProblems(int i, int i2) {
            this.parameters.put("problems", Integer.valueOf(i + i2));
            this.parameters.put("errors", Integer.valueOf(i));
            this.parameters.put("warnings", Integer.valueOf(i2));
            printTag("problems", this.parameters, true, false);
        }

        public void startLoggingSource(CompilationResult compilationResult) {
            if ((this.tagBits & 1) != 0) {
                ICompilationUnit iCompilationUnit = compilationResult.compilationUnit;
                if (iCompilationUnit != null) {
                    char[] fileName = iCompilationUnit.getFileName();
                    File file = new File(new String(fileName));
                    if (fileName != null) {
                        this.parameters.put("path", file.getAbsolutePath());
                    }
                    char[][] cArr = compilationResult.packageName;
                    if (cArr != null) {
                        this.parameters.put("package", new String(CharOperation.concatWith(cArr, File.separatorChar)));
                    }
                    String str = ((CompilationUnit) iCompilationUnit).destinationPath;
                    if (str == null) {
                        str = this.main.destinationPath;
                    }
                    if (str != null && str != "none") {
                        if (File.separatorChar == '/') {
                            this.parameters.put(OUTPUT, str);
                        } else {
                            this.parameters.put(OUTPUT, str.replace('/', File.separatorChar));
                        }
                    }
                }
                printTag("source", this.parameters, true, false);
            }
        }

        public void startLoggingSources() {
            if ((this.tagBits & 1) != 0) {
                printTag("sources", null, true, false);
            }
        }

        public void startLoggingTasks(int i) {
            if ((this.tagBits & 1) != 0) {
                this.parameters.put("tasks", Integer.valueOf(i));
                printTag("tasks", this.parameters, true, false);
            }
        }
    }

    public static class ResourceBundleFactory {
        private static HashMap Cache = new HashMap();

        public static synchronized ResourceBundle getBundle(Locale locale) {
            ResourceBundle bundle;
            bundle = (ResourceBundle) Cache.get(locale);
            if (bundle == null) {
                bundle = ResourceBundle.getBundle(Main.bundleName, locale);
                Cache.put(locale, bundle);
            }
            return bundle;
        }
    }

    public static boolean compile(String str) {
        return new Main(new PrintWriter(System.out), new PrintWriter(System.err), false, null, null).compile(tokenize(str));
    }

    public static boolean compile(String str, PrintWriter printWriter, PrintWriter printWriter2) {
        return new Main(printWriter, printWriter2, false, null, null).compile(tokenize(str));
    }

    public static boolean compile(String[] strArr, PrintWriter printWriter, PrintWriter printWriter2, CompilationProgress compilationProgress) {
        return new Main(printWriter, printWriter2, false, null, compilationProgress).compile(strArr);
    }

    public static File[][] getLibrariesFiles(File[] fileArr) {
        FilenameFilter filenameFilter = new FilenameFilter() { // from class: org.eclipse.jdt.internal.compiler.batch.Main.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                return Util.isPotentialZipArchive(str);
            }
        };
        int length = fileArr.length;
        File[][] fileArr2 = new File[length][];
        for (int i = 0; i < length; i++) {
            File file = fileArr[i];
            if (file.exists() && file.isDirectory()) {
                fileArr2[i] = file.listFiles(filenameFilter);
            }
        }
        return fileArr2;
    }

    public static void main(String[] strArr) {
        new Main(new PrintWriter(System.out), new PrintWriter(System.err), true, null, null).compile(strArr);
    }

    public static String[] tokenize(String str) {
        String[] strArr = new String[10];
        StringTokenizer stringTokenizer = new StringTokenizer(str, " \"", true);
        String str2 = Util.EMPTY_STRING;
        int i = 0;
        boolean z = false;
        while (true) {
            boolean z2 = true;
            while (true) {
                if (stringTokenizer.hasMoreTokens()) {
                    String strNextToken = stringTokenizer.nextToken();
                    if (strNextToken.equals(" ")) {
                        if (z) {
                            int i2 = i - 1;
                            strArr[i2] = String.valueOf(strArr[i2]) + strNextToken;
                        }
                    } else if (strNextToken.equals("\"")) {
                        if (!z && z2) {
                            if (i == strArr.length) {
                                String[] strArr2 = new String[i * 2];
                                System.arraycopy(strArr, 0, strArr2, 0, i);
                                strArr = strArr2;
                            }
                            strArr[i] = Util.EMPTY_STRING;
                            i++;
                        }
                        z = !z;
                    } else if (z) {
                        int i3 = i - 1;
                        strArr[i3] = String.valueOf(strArr[i3]) + strNextToken;
                    } else if (strNextToken.length() > 0 && !z2) {
                        int i4 = i - 1;
                        strArr[i4] = String.valueOf(strArr[i4]) + strNextToken;
                    } else {
                        if (i == strArr.length) {
                            String[] strArr3 = new String[i * 2];
                            System.arraycopy(strArr, 0, strArr3, 0, i);
                            strArr = strArr3;
                        }
                        String strTrim = strNextToken.trim();
                        if (strTrim.length() != 0) {
                            strArr[i] = strTrim;
                            i++;
                        }
                    }
                    z2 = false;
                } else {
                    String[] strArr4 = new String[i];
                    System.arraycopy(strArr, 0, strArr4, 0, i);
                    return strArr4;
                }
            }
        }
    }

    public Main(PrintWriter printWriter, PrintWriter printWriter2, boolean z) {
        this(printWriter, printWriter2, z, null, null);
    }

    public Main(PrintWriter printWriter, PrintWriter printWriter2, boolean z, Map map) {
        this(printWriter, printWriter2, z, map, null);
    }

    public Main(PrintWriter printWriter, PrintWriter printWriter2, boolean z, Map<String, String> map, CompilationProgress compilationProgress) {
        this.javaHomeChecked = false;
        this.primaryNullAnnotationsSeen = false;
        this.proceed = true;
        this.proceedOnError = false;
        this.produceRefInfo = false;
        this.showProgress = false;
        this.systemExitWhenFinished = true;
        this.timing = 0;
        this.verbose = false;
        initialize(printWriter, printWriter2, z, map, compilationProgress);
        relocalize();
    }

    public void addExtraProblems(CategorizedProblem categorizedProblem) {
        if (this.extraProblems == null) {
            this.extraProblems = new ArrayList();
        }
        this.extraProblems.add(categorizedProblem);
    }

    protected void addNewEntry(ArrayList arrayList, String str, ArrayList arrayList2, String str2, String str3, boolean z, boolean z2) {
        AccessRuleSet accessRuleSet;
        int i;
        if (arrayList2.size() != 0) {
            AccessRule[] accessRuleArr = new AccessRule[arrayList2.size()];
            Iterator it = arrayList2.iterator();
            int i2 = 0;
            boolean z3 = true;
            while (it.hasNext()) {
                String str4 = (String) it.next();
                char cCharAt = str4.charAt(0);
                String strSubstring = str4.substring(1);
                if (strSubstring.length() > 0) {
                    if (cCharAt == '+') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), 0);
                    } else if (cCharAt == '-') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.ForbiddenReference);
                    } else if (cCharAt == '?') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.ForbiddenReference, true);
                    } else if (cCharAt == '~') {
                        i = i2 + 1;
                        accessRuleArr[i2] = new AccessRule(strSubstring.toCharArray(), IProblem.DiscouragedReference);
                    }
                    i2 = i;
                }
                z3 = false;
            }
            if (z3) {
                accessRuleSet = new AccessRuleSet(accessRuleArr, (byte) 0, str);
            } else {
                if (str.length() != 0) {
                    addPendingErrors(bind("configure.incorrectClasspath", str));
                    return;
                }
                return;
            }
        } else {
            accessRuleSet = null;
        }
        String str5 = "none".equals(str3) ? "none" : str3;
        if (z2 && str5 != null && Util.isPotentialZipArchive(str)) {
            throw new IllegalArgumentException(bind("configure.unexpectedDestinationPathEntryFile", str));
        }
        FileSystem.Classpath classpath = FileSystem.getClasspath(str, str2, z, accessRuleSet, str5, this.options);
        if (classpath != null) {
            arrayList.add(classpath);
        } else if (str.length() != 0) {
            addPendingErrors(bind("configure.incorrectClasspath", str));
        }
    }

    void addPendingErrors(String str) {
        if (this.pendingErrors == null) {
            this.pendingErrors = new ArrayList();
        }
        this.pendingErrors.add(str);
    }

    public String bind(String str) {
        return bind(str, (String[]) null);
    }

    public String bind(String str, String str2) {
        return bind(str, new String[]{str2});
    }

    public String bind(String str, String str2, String str3) {
        return bind(str, new String[]{str2, str3});
    }

    public String bind(String str, String[] strArr) {
        if (str == null) {
            return "No message available";
        }
        try {
            return MessageFormat.format(this.bundle.getString(str), strArr);
        } catch (MissingResourceException unused) {
            return "Missing message: " + str + " in: org.eclipse.jdt.internal.compiler.batch.messages";
        }
    }

    private boolean checkVMVersion(long j) {
        int iIndexOf;
        String property = System.getProperty(SystemProperties.JAVA_CLASS_VERSION);
        if (property == null || (iIndexOf = property.indexOf(46)) == -1) {
            return false;
        }
        try {
            switch (Integer.parseInt(property.substring(0, iIndexOf))) {
                case 45:
                    return ClassFileConstants.JDK1_1 >= j;
                case 46:
                    return ClassFileConstants.JDK1_2 >= j;
                case 47:
                    return ClassFileConstants.JDK1_3 >= j;
                case 48:
                    return ClassFileConstants.JDK1_4 >= j;
                case 49:
                    return ClassFileConstants.JDK1_5 >= j;
                case 50:
                    return ClassFileConstants.JDK1_6 >= j;
                case 51:
                    return ClassFileConstants.JDK1_7 >= j;
                case 52:
                    return ClassFileConstants.JDK1_8 >= j;
                default:
                    return false;
            }
        } catch (NumberFormatException unused) {
        }
    }

    public boolean compile(String[] strArr) {
        CompilationProgress compilationProgress;
        try {
            try {
                configure(strArr);
                CompilationProgress compilationProgress2 = this.progress;
                if (compilationProgress2 != null) {
                    String[] strArr2 = this.filenames;
                    compilationProgress2.begin(strArr2 == null ? 0 : strArr2.length * this.maxRepetition);
                }
                if (this.proceed) {
                    if (this.showProgress) {
                        this.logger.compiling();
                    }
                    this.currentRepetition = 0;
                    while (true) {
                        int i = this.currentRepetition;
                        int i2 = this.maxRepetition;
                        if (i >= i2) {
                            break;
                        }
                        this.globalProblemsCount = 0;
                        this.globalErrorsCount = 0;
                        this.globalWarningsCount = 0;
                        this.globalTasksCount = 0;
                        this.exportedClassFilesCounter = 0;
                        if (i2 > 1) {
                            this.logger.flush();
                            this.logger.logRepetition(this.currentRepetition, this.maxRepetition);
                        }
                        performCompilation();
                        this.currentRepetition++;
                    }
                    if (this.compilerStats != null) {
                        this.logger.logAverage();
                    }
                    if (this.showProgress) {
                        this.logger.printNewLine();
                    }
                }
                if (this.systemExitWhenFinished) {
                    this.logger.flush();
                    this.logger.close();
                    System.exit(this.globalErrorsCount > 0 ? -1 : 0);
                }
                this.logger.flush();
                this.logger.close();
                CompilationProgress compilationProgress3 = this.progress;
                if (compilationProgress3 != null) {
                    compilationProgress3.done();
                }
                return this.globalErrorsCount == 0 && ((compilationProgress = this.progress) == null || !compilationProgress.isCanceled());
            } catch (IllegalArgumentException e) {
                this.logger.logException(e);
                if (this.systemExitWhenFinished) {
                    this.logger.flush();
                    this.logger.close();
                    System.exit(-1);
                }
                this.logger.flush();
                this.logger.close();
                CompilationProgress compilationProgress4 = this.progress;
                if (compilationProgress4 != null) {
                    compilationProgress4.done();
                }
                return false;
            } catch (RuntimeException e2) {
                this.logger.logException(e2);
                if (this.systemExitWhenFinished) {
                    this.logger.flush();
                    this.logger.close();
                    System.exit(-1);
                }
                this.logger.flush();
                this.logger.close();
                CompilationProgress compilationProgress5 = this.progress;
                if (compilationProgress5 != null) {
                    compilationProgress5.done();
                }
                return false;
            }
        } catch (Throwable th) {
            this.logger.flush();
            this.logger.close();
            CompilationProgress compilationProgress6 = this.progress;
            if (compilationProgress6 != null) {
                compilationProgress6.done();
            }
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:351:0x07b4  */
    /* JADX WARN: Code duplicated, block: B:352:0x07c3  */
    /* JADX WARN: Code duplicated, block: B:354:0x07c6  */
    /* JADX WARN: Code duplicated, block: B:357:0x080e  */
    /* JADX WARN: Code duplicated, block: B:361:0x081a  */
    /* JADX WARN: Code duplicated, block: B:365:0x082d  */
    /* JADX WARN: Code duplicated, block: B:369:0x083a  */
    /* JADX WARN: Code duplicated, block: B:372:0x084d  */
    /* JADX WARN: Code duplicated, block: B:376:0x0859  */
    /* JADX WARN: Code duplicated, block: B:379:0x0868  */
    /* JADX WARN: Code duplicated, block: B:385:0x0899  */
    /* JADX WARN: Code duplicated, block: B:388:0x08a5  */
    /* JADX WARN: Code duplicated, block: B:392:0x08d1  */
    /* JADX WARN: Code duplicated, block: B:395:0x08dd  */
    /* JADX WARN: Code duplicated, block: B:398:0x08ed  */
    /* JADX WARN: Code duplicated, block: B:586:0x0ce5  */
    /* JADX WARN: Code duplicated, block: B:594:0x0d07  */
    /* JADX WARN: Code duplicated, block: B:596:0x0d16  */
    /* JADX WARN: Code duplicated, block: B:599:0x0d1b  */
    /* JADX WARN: Code duplicated, block: B:600:0x0d22  */
    /* JADX WARN: Code duplicated, block: B:711:0x0f77  */
    /* JADX WARN: Code duplicated, block: B:719:0x0fb1  */
    /* JADX WARN: Code duplicated, block: B:721:0x0fb7  */
    /* JADX WARN: Code duplicated, block: B:730:0x1003  */
    /* JADX WARN: Code duplicated, block: B:732:0x1011  */
    /* JADX WARN: Code duplicated, block: B:735:0x1017  */
    /* JADX WARN: Code duplicated, block: B:738:0x103e A[LOOP:9: B:736:0x103a->B:738:0x103e, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:739:0x104b  */
    /* JADX WARN: Code duplicated, block: B:743:0x107a A[LOOP:10: B:740:0x1058->B:743:0x107a, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:806:0x1085 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:810:0x0821 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:811:0x0841 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:812:0x085c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:813:0x08a3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:814:0x08db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:854:0x0cfb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:860:0x0f99 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:861:0x08c7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:862:0x08e3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:889:0x0ff4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:890:0x0ff1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:891:0x07b0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:892:0x0870 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:901:0x0fc7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:902:0x0816 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:903:0x0836 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:904:0x0855 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:905:0x0896 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:917:0x088e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:943:0x0d28 A[SYNTHETIC] */
    public void configure(String[] strArr) throws Throwable {
        boolean z;
        String str;
        int i;
        int i2;
        boolean z2;
        ArrayList arrayList;
        String str2;
        String[] strArr2;
        boolean z3;
        int i3;
        boolean z4;
        StringTokenizer stringTokenizer;
        boolean z5;
        int i4;
        String strNextToken;
        char cCharAt;
        boolean z6;
        String strSubstring;
        File file;
        String[] strArrFind;
        String[] strArr3;
        char c;
        int length;
        int i5;
        int length2;
        int i6;
        HashSet hashSet;
        ArrayList arrayList2;
        String[] strArr4;
        String[] strArr5 = strArr;
        if (strArr5 == null || strArr5.length == 0) {
            printUsage();
            return;
        }
        ArrayList arrayList3 = new ArrayList(4);
        ArrayList arrayList4 = new ArrayList(4);
        ArrayList arrayList5 = new ArrayList(4);
        this.annotationPaths = null;
        this.annotationsFromClasspath = false;
        int length3 = strArr5.length;
        this.maxRepetition = 0;
        String str3 = Util.EMPTY_STRING;
        int i7 = 0;
        while (true) {
            if (i7 >= length3) {
                z = false;
                break;
            } else {
                if (strArr5[i7].startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                    z = true;
                    break;
                }
                i7++;
            }
        }
        if (z) {
            String[] strArr6 = new String[length3];
            int i8 = 0;
            int i9 = 0;
            while (i8 < length3) {
                String strTrim = strArr5[i8].trim();
                if (strTrim.startsWith(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT)) {
                    try {
                        LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(new String(Util.getFileCharContent(new File(strTrim.substring(1)), null))));
                        StringBuffer stringBuffer = new StringBuffer();
                        while (true) {
                            String line = lineNumberReader.readLine();
                            if (line == null) {
                                break;
                            }
                            String strTrim2 = line.trim();
                            if (!strTrim2.startsWith("#")) {
                                stringBuffer.append(strTrim2).append(" ");
                            }
                        }
                        strArr4 = tokenize(stringBuffer.toString());
                    } catch (IOException unused) {
                        throw new IllegalArgumentException(bind("configure.invalidexpansionargumentname", strTrim));
                    }
                } else {
                    strArr4 = null;
                }
                if (strArr4 != null) {
                    int length4 = strArr6.length;
                    int length5 = strArr4.length;
                    String[] strArr7 = new String[(length4 + length5) - 1];
                    System.arraycopy(strArr6, 0, strArr7, 0, i9);
                    System.arraycopy(strArr4, 0, strArr7, i9, length5);
                    i9 += length5;
                    strArr6 = strArr7;
                } else {
                    strArr6[i9] = strTrim;
                    i9++;
                }
                i8++;
                str3 = str3;
            }
            str = str3;
            strArr5 = strArr6;
        } else {
            str = str3;
            int i10 = 0;
            while (i10 < length3) {
                strArr5[i10] = strArr5[i10].trim();
                i10++;
                arrayList3 = arrayList3;
            }
        }
        int length6 = strArr5.length;
        this.expandedCommandLine = strArr5;
        int iProcessPaths = -1;
        String strSubstring2 = str;
        String str4 = null;
        String str5 = null;
        ArrayList arrayList6 = null;
        ArrayList arrayList7 = null;
        String strSubstring3 = null;
        int i11 = 0;
        boolean z7 = false;
        HashSet hashSet2 = null;
        boolean z8 = false;
        int i12 = 0;
        String strSubstring4 = null;
        boolean z9 = false;
        String str6 = null;
        boolean z10 = false;
        boolean z11 = false;
        while (true) {
            char c2 = 0;
            while (true) {
                hashSet2 = hashSet2;
                iProcessPaths++;
                int i13 = iProcessPaths;
                strSubstring3 = strSubstring3;
                arrayList7 = arrayList7;
                if (iProcessPaths >= length6) {
                    if (this.enableJavadocOn) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                    } else if (this.warnJavadocOn || this.warnAllJavadocOn) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportUnusedParameterIncludeDocCommentReference, CompilerOptions.DISABLED);
                        this.options.put(CompilerOptions.OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference, CompilerOptions.DISABLED);
                    }
                    if (this.warnJavadocOn) {
                        this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTags, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsDeprecatedRef, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsNotVisibleRef, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportMissingJavadocTagsVisibility, "private");
                    }
                    if (z9 || (i11 == 0 && i12 == 0)) {
                        if (str6 == null) {
                            printUsage();
                        } else {
                            printUsage(str6);
                        }
                        this.proceed = false;
                        return;
                    }
                    String str7 = this.log;
                    if (str7 != null) {
                        this.logger.setLog(str7);
                    } else {
                        this.showProgress = false;
                    }
                    this.logger.logVersion(z7);
                    validateOptions(z8);
                    if (!z10 && CompilerOptions.versionToJdkLevel(this.options.get(CompilerOptions.OPTION_Compliance)) >= ClassFileConstants.JDK1_6) {
                        this.options.put(CompilerOptions.OPTION_Process_Annotations, CompilerOptions.ENABLED);
                    }
                    this.logger.logCommandLineArguments(strArr5);
                    this.logger.logOptions(this.options);
                    if (this.maxRepetition == 0) {
                        i = 1;
                        this.maxRepetition = 1;
                    } else {
                        i = 1;
                    }
                    int i14 = this.maxRepetition;
                    if (i14 >= 3 && (this.timing & i) != 0) {
                        this.compilerStats = new CompilerStats[i14];
                    }
                    if (i11 != 0) {
                        String[] strArr8 = this.filenames;
                        String[] strArr9 = new String[i11];
                        this.filenames = strArr9;
                        i2 = 0;
                        System.arraycopy(strArr8, 0, strArr9, 0, i11);
                    } else {
                        i2 = 0;
                    }
                    if (i12 != 0) {
                        String[] strArr10 = this.classNames;
                        String[] strArr11 = new String[i12];
                        this.classNames = strArr11;
                        System.arraycopy(strArr10, i2, strArr11, i2, i12);
                    }
                    setPaths(arrayList3, str5, arrayList4, arrayList5, arrayList6, arrayList7, strSubstring3);
                    if (hashSet2 != null && hashSet2.size() > 1) {
                        this.logger.logWarning(bind("configure.multipleencodings", this.options.get(CompilerOptions.OPTION_Encoding), getAllEncodings(hashSet2)));
                    }
                    ArrayList arrayList8 = this.pendingErrors;
                    if (arrayList8 != null) {
                        Iterator it = arrayList8.iterator();
                        while (it.hasNext()) {
                            this.logger.logPendingError((String) it.next());
                        }
                        this.pendingErrors = null;
                        return;
                    }
                    return;
                }
                str6 = str6;
                if (strSubstring3 != null) {
                    throw new IllegalArgumentException(bind("configure.unexpectedCustomEncoding", strSubstring2, strSubstring3));
                }
                arrayList4 = arrayList4;
                strSubstring2 = strArr5[iProcessPaths];
                z7 = z7;
                i12 = i12;
                String str8 = CompilerOptions.VERSION_1_7;
                arrayList6 = arrayList6;
                str5 = str5;
                switch (c2) {
                    case 0:
                        arrayList3 = arrayList3;
                        z2 = z8;
                        if (strSubstring2.startsWith("-nowarn")) {
                            int length7 = strSubstring2.length();
                            if (length7 == 7) {
                                disableAll(0);
                            } else {
                                if (length7 == 8) {
                                    throw new IllegalArgumentException(bind("configure.invalidNowarnOption", strSubstring2));
                                }
                                int iIndexOf = strSubstring2.indexOf(91) + 1;
                                int iLastIndexOf = strSubstring2.lastIndexOf(93);
                                if (iIndexOf <= 8 || iLastIndexOf == -1 || iIndexOf > iLastIndexOf || iLastIndexOf < strSubstring2.length() - 1) {
                                    throw new IllegalArgumentException(bind("configure.invalidNowarnOption", strSubstring2));
                                }
                                String strSubstring5 = strSubstring2.substring(iIndexOf, iLastIndexOf);
                                if (strSubstring5.length() > 0) {
                                    char[][] cArrDecodeIgnoreOptionalProblemsFromFolders = decodeIgnoreOptionalProblemsFromFolders(strSubstring5);
                                    char[][] cArr = this.ignoreOptionalProblemsFromFolders;
                                    if (cArr != null) {
                                        char[][] cArr2 = new char[cArr.length + cArrDecodeIgnoreOptionalProblemsFromFolders.length][];
                                        System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                                        System.arraycopy(cArrDecodeIgnoreOptionalProblemsFromFolders, 0, cArr2, this.ignoreOptionalProblemsFromFolders.length, cArrDecodeIgnoreOptionalProblemsFromFolders.length);
                                        this.ignoreOptionalProblemsFromFolders = cArr2;
                                    } else {
                                        this.ignoreOptionalProblemsFromFolders = cArrDecodeIgnoreOptionalProblemsFromFolders;
                                    }
                                } else {
                                    throw new IllegalArgumentException(bind("configure.invalidNowarnOption", strSubstring2));
                                }
                            }
                            strSubstring2 = strSubstring2;
                            iProcessPaths = iProcessPaths;
                            str6 = str6;
                            hashSet2 = hashSet2;
                            strSubstring3 = strSubstring3;
                            arrayList7 = arrayList7;
                            arrayList4 = arrayList4;
                            z7 = z7;
                            i12 = i12;
                            arrayList6 = arrayList6;
                            str5 = str5;
                            arrayList3 = arrayList3;
                            z8 = z2;
                        } else {
                            if (strSubstring2.startsWith("[")) {
                                throw new IllegalArgumentException(bind("configure.unexpectedBracket", strSubstring2));
                            }
                            if (strSubstring2.endsWith("]")) {
                                int iIndexOf2 = strSubstring2.indexOf(91);
                                int i15 = iIndexOf2 + 1;
                                arrayList = arrayList5;
                                if (i15 <= 1) {
                                    throw new IllegalArgumentException(bind("configure.unexpectedBracket", strSubstring2));
                                }
                                int length8 = strSubstring2.length();
                                str2 = CompilerOptions.VERSION_1_5;
                                int i16 = length8 - 1;
                                if (i15 >= 1) {
                                    if (i15 < i16) {
                                        strSubstring3 = strSubstring2.substring(i15, i16);
                                        try {
                                            new InputStreamReader(new ByteArrayInputStream(new byte[0]), strSubstring3);
                                        } catch (UnsupportedEncodingException unused2) {
                                            throw new IllegalArgumentException(bind("configure.unsupportedEncoding", strSubstring3));
                                        }
                                    } else {
                                        strSubstring3 = strSubstring3;
                                    }
                                    strSubstring2 = strSubstring2.substring(0, iIndexOf2);
                                    break;
                                }
                                if (strSubstring2.endsWith(SuffixConstants.SUFFIX_STRING_java)) {
                                    strArr2 = this.filenames;
                                    if (strArr2 == null) {
                                        int i17 = length6 - iProcessPaths;
                                        this.filenames = new String[i17];
                                        this.encodings = new String[i17];
                                        this.destinationPaths = new String[i17];
                                    } else if (i11 == strArr2.length) {
                                        int length9 = strArr2.length;
                                        int i18 = (length9 + length6) - iProcessPaths;
                                        String[] strArr12 = new String[i18];
                                        this.filenames = strArr12;
                                        System.arraycopy(strArr2, 0, strArr12, 0, length9);
                                        String[] strArr13 = this.encodings;
                                        String[] strArr14 = new String[i18];
                                        this.encodings = strArr14;
                                        System.arraycopy(strArr13, 0, strArr14, 0, length9);
                                        String[] strArr15 = this.destinationPaths;
                                        String[] strArr16 = new String[i18];
                                        this.destinationPaths = strArr16;
                                        System.arraycopy(strArr15, 0, strArr16, 0, length9);
                                    }
                                    this.filenames[i11] = strSubstring2;
                                    this.encodings[i11] = strSubstring3;
                                    strSubstring2 = strSubstring2;
                                    i11++;
                                    strSubstring3 = null;
                                    arrayList3 = arrayList3;
                                    z8 = z2;
                                    arrayList5 = arrayList;
                                    c2 = 0;
                                    str6 = str6;
                                } else {
                                    if (strSubstring2.equals("-log")) {
                                        if (this.log == null) {
                                            throw new IllegalArgumentException(bind("configure.duplicateLog", strSubstring2));
                                        }
                                        c2 = 5;
                                    } else {
                                        int i19 = 6;
                                        if (strSubstring2.equals("-repeat")) {
                                            if (this.maxRepetition <= 0) {
                                                throw new IllegalArgumentException(bind("configure.duplicateRepeat", strSubstring2));
                                            }
                                            strSubstring2 = strSubstring2;
                                            strSubstring3 = strSubstring3;
                                            c2 = 6;
                                        } else if (strSubstring2.equals("-maxProblems")) {
                                            if (this.maxProblems <= 0) {
                                                throw new IllegalArgumentException(bind("configure.duplicateMaxProblems", strSubstring2));
                                            }
                                            c2 = 11;
                                        } else if (strSubstring2.equals("-source")) {
                                            strSubstring2 = strSubstring2;
                                            strSubstring3 = strSubstring3;
                                            iProcessPaths = iProcessPaths;
                                            str6 = str6;
                                            hashSet2 = hashSet2;
                                            arrayList7 = arrayList7;
                                            arrayList4 = arrayList4;
                                            z7 = z7;
                                            i12 = i12;
                                            arrayList6 = arrayList6;
                                            str5 = str5;
                                            arrayList3 = arrayList3;
                                            z8 = z2;
                                            arrayList5 = arrayList;
                                            c2 = 7;
                                        } else if (strSubstring2.equals("-encoding")) {
                                            c2 = '\b';
                                        } else {
                                            if (strSubstring2.equals("-1.3")) {
                                                if (!z2) {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                                this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_3);
                                            } else if (strSubstring2.equals("-1.4")) {
                                                if (!z2) {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                                this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_4);
                                            } else if (!strSubstring2.equals("-1.5") || strSubstring2.equals("-5") || strSubstring2.equals("-5.0")) {
                                                z3 = true;
                                                if (!z2) {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                                this.options.put(CompilerOptions.OPTION_Compliance, str2);
                                                z8 = z3;
                                                arrayList5 = arrayList;
                                            } else if (strSubstring2.equals("-1.6") || strSubstring2.equals("-6") || strSubstring2.equals("-6.0")) {
                                                z3 = true;
                                                if (z2) {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                                this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_6);
                                                z8 = z3;
                                                arrayList5 = arrayList;
                                            } else if (strSubstring2.equals("-1.7") || strSubstring2.equals("-7") || strSubstring2.equals("-7.0")) {
                                                z3 = true;
                                                if (z2) {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                                this.options.put(CompilerOptions.OPTION_Compliance, str8);
                                                z8 = z3;
                                                arrayList5 = arrayList;
                                            } else if (strSubstring2.equals("-1.8") || strSubstring2.equals("-8") || strSubstring2.equals("-8.0")) {
                                                z3 = true;
                                                if (!z2) {
                                                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_8);
                                                    z8 = z3;
                                                    arrayList5 = arrayList;
                                                } else {
                                                    throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                                }
                                            } else if (strSubstring2.equals("-d")) {
                                                if (this.destinationPath != null) {
                                                    StringBuffer stringBuffer2 = new StringBuffer();
                                                    stringBuffer2.append(strSubstring2);
                                                    int i20 = i13 + 2;
                                                    if (i20 < length6) {
                                                        stringBuffer2.append(StringUtil.SPACE);
                                                        stringBuffer2.append(strArr5[i20]);
                                                    }
                                                    throw new IllegalArgumentException(bind("configure.duplicateOutputPath", stringBuffer2.toString()));
                                                }
                                                strSubstring2 = strSubstring2;
                                                strSubstring3 = strSubstring3;
                                                iProcessPaths = iProcessPaths;
                                                str6 = str6;
                                                hashSet2 = hashSet2;
                                                arrayList7 = arrayList7;
                                                arrayList4 = arrayList4;
                                                z7 = z7;
                                                i12 = i12;
                                                arrayList6 = arrayList6;
                                                str5 = str5;
                                                arrayList3 = arrayList3;
                                                z8 = z2;
                                                arrayList5 = arrayList;
                                                c2 = 3;
                                            } else if (strSubstring2.equals("-classpath") || strSubstring2.equals("-cp")) {
                                                strSubstring2 = strSubstring2;
                                                strSubstring3 = strSubstring3;
                                                c2 = 1;
                                            } else if (strSubstring2.equals("-bootclasspath")) {
                                                if (arrayList3.size() > 0) {
                                                    StringBuffer stringBuffer3 = new StringBuffer();
                                                    stringBuffer3.append(strSubstring2);
                                                    int i21 = i13 + 2;
                                                    if (i21 < length6) {
                                                        stringBuffer3.append(StringUtil.SPACE);
                                                        stringBuffer3.append(strArr5[i21]);
                                                    }
                                                    throw new IllegalArgumentException(bind("configure.duplicateBootClasspath", stringBuffer3.toString()));
                                                }
                                                c2 = '\t';
                                            } else if (strSubstring2.equals("-sourcepath")) {
                                                if (str5 != null) {
                                                    StringBuffer stringBuffer4 = new StringBuffer();
                                                    stringBuffer4.append(strSubstring2);
                                                    int i22 = i13 + 2;
                                                    if (i22 < length6) {
                                                        stringBuffer4.append(StringUtil.SPACE);
                                                        stringBuffer4.append(strArr5[i22]);
                                                    }
                                                    throw new IllegalArgumentException(bind("configure.duplicateSourcepath", stringBuffer4.toString()));
                                                }
                                                c2 = '\r';
                                            } else if (strSubstring2.equals("-extdirs")) {
                                                if (arrayList6 != null) {
                                                    StringBuffer stringBuffer5 = new StringBuffer();
                                                    stringBuffer5.append(strSubstring2);
                                                    int i23 = i13 + 2;
                                                    if (i23 < length6) {
                                                        stringBuffer5.append(StringUtil.SPACE);
                                                        stringBuffer5.append(strArr5[i23]);
                                                    }
                                                    throw new IllegalArgumentException(bind("configure.duplicateExtDirs", stringBuffer5.toString()));
                                                }
                                                c2 = '\f';
                                            } else if (!strSubstring2.equals("-endorseddirs")) {
                                                if (strSubstring2.equals("-progress")) {
                                                    this.showProgress = true;
                                                } else if (strSubstring2.startsWith("-proceedOnError")) {
                                                    if (strSubstring2.length() <= 15) {
                                                        this.options.put(CompilerOptions.OPTION_FatalOptionalError, CompilerOptions.DISABLED);
                                                    } else if (strSubstring2.equals("-proceedOnError:Fatal")) {
                                                        this.options.put(CompilerOptions.OPTION_FatalOptionalError, CompilerOptions.ENABLED);
                                                    } else {
                                                        throw new IllegalArgumentException(bind("configure.invalidWarningConfiguration", strSubstring2));
                                                    }
                                                    this.proceedOnError = true;
                                                } else if (strSubstring2.equals("-time")) {
                                                    this.timing = 1;
                                                } else if (strSubstring2.equals("-time:detail")) {
                                                    this.timing = 3;
                                                } else {
                                                    if (strSubstring2.equals("-version") || strSubstring2.equals("-v")) {
                                                        this.logger.logVersion(true);
                                                        this.proceed = false;
                                                        return;
                                                    }
                                                    if (strSubstring2.equals("-showversion")) {
                                                        strSubstring2 = strSubstring2;
                                                        strSubstring3 = strSubstring3;
                                                        iProcessPaths = iProcessPaths;
                                                        str6 = str6;
                                                        hashSet2 = hashSet2;
                                                        arrayList7 = arrayList7;
                                                        arrayList4 = arrayList4;
                                                        i12 = i12;
                                                        arrayList6 = arrayList6;
                                                        str5 = str5;
                                                        arrayList3 = arrayList3;
                                                        z8 = z2;
                                                        arrayList5 = arrayList;
                                                        z7 = true;
                                                    } else if ("-deprecation".equals(strSubstring2)) {
                                                        this.options.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.WARNING);
                                                        strSubstring2 = strSubstring2;
                                                        strSubstring3 = strSubstring3;
                                                        iProcessPaths = iProcessPaths;
                                                        str6 = str6;
                                                        hashSet2 = hashSet2;
                                                        arrayList7 = arrayList7;
                                                        arrayList4 = arrayList4;
                                                        z7 = z7;
                                                        i12 = i12;
                                                        arrayList6 = arrayList6;
                                                        str5 = str5;
                                                        arrayList3 = arrayList3;
                                                        z8 = z2;
                                                        arrayList5 = arrayList;
                                                        z11 = true;
                                                    } else if (strSubstring2.equals("-help") || strSubstring2.equals("-?")) {
                                                        strSubstring2 = strSubstring2;
                                                        strSubstring3 = strSubstring3;
                                                        iProcessPaths = iProcessPaths;
                                                        str6 = str6;
                                                        hashSet2 = hashSet2;
                                                        arrayList7 = arrayList7;
                                                        arrayList4 = arrayList4;
                                                        z7 = z7;
                                                        i12 = i12;
                                                        arrayList6 = arrayList6;
                                                        str5 = str5;
                                                        arrayList3 = arrayList3;
                                                        z8 = z2;
                                                        arrayList5 = arrayList;
                                                        z9 = true;
                                                    } else if (strSubstring2.equals("-help:warn") || strSubstring2.equals("-?:warn")) {
                                                        strSubstring2 = strSubstring2;
                                                        str6 = "misc.usage.warn";
                                                        strSubstring3 = strSubstring3;
                                                        iProcessPaths = iProcessPaths;
                                                        hashSet2 = hashSet2;
                                                        arrayList7 = arrayList7;
                                                        arrayList4 = arrayList4;
                                                        z7 = z7;
                                                        i12 = i12;
                                                        arrayList6 = arrayList6;
                                                        str5 = str5;
                                                        arrayList3 = arrayList3;
                                                        z8 = z2;
                                                        arrayList5 = arrayList;
                                                        z9 = true;
                                                    } else if (strSubstring2.equals("-noExit")) {
                                                        this.systemExitWhenFinished = false;
                                                    } else if (strSubstring2.equals("-verbose")) {
                                                        this.verbose = true;
                                                    } else if (strSubstring2.equals("-referenceInfo")) {
                                                        this.produceRefInfo = true;
                                                    } else if (strSubstring2.equals("-inlineJSR")) {
                                                        this.options.put(CompilerOptions.OPTION_InlineJsr, CompilerOptions.ENABLED);
                                                    } else if (strSubstring2.equals("-parameters")) {
                                                        this.options.put(CompilerOptions.OPTION_MethodParametersAttribute, CompilerOptions.GENERATE);
                                                    } else if (strSubstring2.equals("-genericsignature")) {
                                                        this.options.put(CompilerOptions.OPTION_LambdaGenericSignature, CompilerOptions.GENERATE);
                                                    } else if (strSubstring2.startsWith("-g")) {
                                                        int length10 = strSubstring2.length();
                                                        if (length10 == 2) {
                                                            this.options.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
                                                            this.options.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
                                                            this.options.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);
                                                        } else if (length10 > 3) {
                                                            this.options.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.DO_NOT_GENERATE);
                                                            this.options.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.DO_NOT_GENERATE);
                                                            this.options.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.DO_NOT_GENERATE);
                                                            if (length10 != 7 || !strSubstring2.equals("-g:none")) {
                                                                StringTokenizer stringTokenizer2 = new StringTokenizer(strSubstring2.substring(3, strSubstring2.length()), ",");
                                                                while (stringTokenizer2.hasMoreTokens()) {
                                                                    String strNextToken2 = stringTokenizer2.nextToken();
                                                                    if (strNextToken2.equals("vars")) {
                                                                        this.options.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
                                                                    } else if (strNextToken2.equals("lines")) {
                                                                        this.options.put(CompilerOptions.OPTION_LineNumberAttribute, CompilerOptions.GENERATE);
                                                                    } else if (strNextToken2.equals(FirebaseAnalytics.Param.SOURCE)) {
                                                                        this.options.put(CompilerOptions.OPTION_SourceFileAttribute, CompilerOptions.GENERATE);
                                                                    } else {
                                                                        throw new IllegalArgumentException(bind("configure.invalidDebugOption", strSubstring2));
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            throw new IllegalArgumentException(bind("configure.invalidDebugOption", strSubstring2));
                                                        }
                                                    } else if (strSubstring2.startsWith("-warn")) {
                                                        int length11 = strSubstring2.length();
                                                        if (length11 == 10 && strSubstring2.equals("-warn:none")) {
                                                            disableAll(0);
                                                        } else {
                                                            if (length11 <= 6) {
                                                                throw new IllegalArgumentException(bind("configure.invalidWarningConfiguration", strSubstring2));
                                                            }
                                                            char cCharAt2 = strSubstring2.charAt(6);
                                                            if (cCharAt2 != '+') {
                                                                if (cCharAt2 != '-') {
                                                                    disableAll(0);
                                                                    i3 = 6;
                                                                } else {
                                                                    i3 = 7;
                                                                    z4 = false;
                                                                }
                                                                stringTokenizer = new StringTokenizer(strSubstring2.substring(i3, strSubstring2.length()), ",");
                                                                if (z11) {
                                                                    this.options.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.WARNING);
                                                                }
                                                                z5 = z4;
                                                                i4 = 0;
                                                                while (stringTokenizer.hasMoreTokens()) {
                                                                    strNextToken = stringTokenizer.nextToken();
                                                                    i4++;
                                                                    cCharAt = strNextToken.charAt(0);
                                                                    if (cCharAt != '+') {
                                                                        strNextToken = strNextToken.substring(1);
                                                                        z5 = true;
                                                                    } else if (cCharAt != '-') {
                                                                        strNextToken = strNextToken.substring(1);
                                                                        z5 = false;
                                                                    }
                                                                    handleWarningToken(strNextToken, z5);
                                                                }
                                                                if (i4 == 0) {
                                                                    throw new IllegalArgumentException(bind("configure.invalidWarningOption", strSubstring2));
                                                                }
                                                            } else {
                                                                i3 = 7;
                                                            }
                                                            z4 = true;
                                                            stringTokenizer = new StringTokenizer(strSubstring2.substring(i3, strSubstring2.length()), ",");
                                                            if (z11) {
                                                                this.options.put(CompilerOptions.OPTION_ReportDeprecation, CompilerOptions.WARNING);
                                                            }
                                                            z5 = z4;
                                                            i4 = 0;
                                                            while (stringTokenizer.hasMoreTokens()) {
                                                                strNextToken = stringTokenizer.nextToken();
                                                                i4++;
                                                                cCharAt = strNextToken.charAt(0);
                                                                if (cCharAt != '+') {
                                                                    strNextToken = strNextToken.substring(1);
                                                                    z5 = true;
                                                                } else if (cCharAt != '-') {
                                                                    strNextToken = strNextToken.substring(1);
                                                                    z5 = false;
                                                                }
                                                                handleWarningToken(strNextToken, z5);
                                                            }
                                                            if (i4 == 0) {
                                                                throw new IllegalArgumentException(bind("configure.invalidWarningOption", strSubstring2));
                                                            }
                                                        }
                                                    } else if (strSubstring2.startsWith("-err")) {
                                                        if (strSubstring2.length() <= 5) {
                                                            throw new IllegalArgumentException(bind("configure.invalidErrorConfiguration", strSubstring2));
                                                        }
                                                        char cCharAt3 = strSubstring2.charAt(5);
                                                        if (cCharAt3 == '+') {
                                                            z6 = true;
                                                        } else if (cCharAt3 != '-') {
                                                            disableAll(1);
                                                            i19 = 5;
                                                            z6 = true;
                                                        } else {
                                                            z6 = false;
                                                        }
                                                        StringTokenizer stringTokenizer3 = new StringTokenizer(strSubstring2.substring(i19, strSubstring2.length()), ",");
                                                        boolean z12 = z6;
                                                        int i24 = 0;
                                                        while (stringTokenizer3.hasMoreTokens()) {
                                                            String strNextToken3 = stringTokenizer3.nextToken();
                                                            i24++;
                                                            char cCharAt4 = strNextToken3.charAt(0);
                                                            if (cCharAt4 == '+') {
                                                                strNextToken3 = strNextToken3.substring(1);
                                                                z12 = true;
                                                            } else if (cCharAt4 == '-') {
                                                                strNextToken3 = strNextToken3.substring(1);
                                                                z12 = false;
                                                            }
                                                            handleErrorToken(strNextToken3, z12);
                                                        }
                                                        if (i24 == 0) {
                                                            throw new IllegalArgumentException(bind("configure.invalidErrorOption", strSubstring2));
                                                        }
                                                    } else if (strSubstring2.equals("-target")) {
                                                        strSubstring2 = strSubstring2;
                                                        strSubstring3 = strSubstring3;
                                                        iProcessPaths = iProcessPaths;
                                                        str6 = str6;
                                                        hashSet2 = hashSet2;
                                                        arrayList7 = arrayList7;
                                                        arrayList4 = arrayList4;
                                                        z7 = z7;
                                                        i12 = i12;
                                                        arrayList6 = arrayList6;
                                                        str5 = str5;
                                                        arrayList3 = arrayList3;
                                                        z8 = z2;
                                                        arrayList5 = arrayList;
                                                        c2 = 4;
                                                    } else if (strSubstring2.equals("-preserveAllLocals")) {
                                                        this.options.put(CompilerOptions.OPTION_PreserveUnusedLocal, "preserve");
                                                    } else if (strSubstring2.equals("-enableJavadoc")) {
                                                        this.enableJavadocOn = true;
                                                    } else if (strSubstring2.equals("-Xemacs")) {
                                                        this.logger.setEmacs();
                                                    } else if (!strSubstring2.startsWith("-A")) {
                                                        if (strSubstring2.equals("-processorpath")) {
                                                            c2 = 17;
                                                        } else if (strSubstring2.equals("-processor")) {
                                                            c2 = 18;
                                                        } else if (strSubstring2.equals("-proc:only")) {
                                                            this.options.put(CompilerOptions.OPTION_GenerateClassFiles, CompilerOptions.DISABLED);
                                                        } else if (strSubstring2.equals("-proc:none")) {
                                                            this.options.put(CompilerOptions.OPTION_Process_Annotations, CompilerOptions.DISABLED);
                                                            strSubstring2 = strSubstring2;
                                                            strSubstring3 = strSubstring3;
                                                            iProcessPaths = iProcessPaths;
                                                            str6 = str6;
                                                            hashSet2 = hashSet2;
                                                            arrayList7 = arrayList7;
                                                            arrayList4 = arrayList4;
                                                            z7 = z7;
                                                            i12 = i12;
                                                            arrayList6 = arrayList6;
                                                            str5 = str5;
                                                            arrayList3 = arrayList3;
                                                            z8 = z2;
                                                            arrayList5 = arrayList;
                                                            z10 = true;
                                                        } else if (strSubstring2.equals("-s")) {
                                                            c2 = 19;
                                                        } else if (!strSubstring2.equals("-XprintProcessorInfo") && !strSubstring2.equals("-XprintRounds") && !strSubstring2.startsWith("-X") && !strSubstring2.startsWith("-J") && !strSubstring2.equals("-O")) {
                                                            if (strSubstring2.equals("-classNames")) {
                                                                c2 = 20;
                                                            } else if (strSubstring2.equals("-properties")) {
                                                                c2 = 21;
                                                            } else if (strSubstring2.equals("-missingNullDefault")) {
                                                                this.options.put(CompilerOptions.OPTION_ReportMissingNonNullByDefaultAnnotation, CompilerOptions.WARNING);
                                                            } else if (strSubstring2.equals("-annotationpath")) {
                                                                c2 = 22;
                                                            } else {
                                                                strSubstring2 = strSubstring2;
                                                                strSubstring3 = strSubstring3;
                                                                strSubstring = str4;
                                                                if (strSubstring == null) {
                                                                    if (File.separatorChar != '/') {
                                                                        strSubstring2 = strSubstring2.replace('/', File.separatorChar);
                                                                    }
                                                                    if (strSubstring2.endsWith("[-d")) {
                                                                        strSubstring4 = strSubstring2.substring(0, strSubstring2.length() - 3);
                                                                        c2 = 16;
                                                                        str4 = strSubstring;
                                                                        strSubstring2 = strSubstring2;
                                                                    } else {
                                                                        strSubstring2 = strSubstring2;
                                                                    }
                                                                } else {
                                                                    strSubstring2 = strSubstring2;
                                                                    strSubstring2 = strSubstring4;
                                                                }
                                                                file = new File(strSubstring2);
                                                                if (!file.isDirectory()) {
                                                                    throw new IllegalArgumentException(bind("configure.unrecognizedOption", strSubstring2));
                                                                }
                                                                strArrFind = FileFinder.find(file, SuffixConstants.SUFFIX_STRING_JAVA);
                                                                if ("none".equals(strSubstring)) {
                                                                    strSubstring = "none";
                                                                }
                                                                strArr3 = this.filenames;
                                                                if (strArr3 != null) {
                                                                    length2 = strArrFind.length;
                                                                    int i25 = length2 + i11;
                                                                    String[] strArr17 = new String[i25];
                                                                    this.filenames = strArr17;
                                                                    c = 0;
                                                                    System.arraycopy(strArr3, 0, strArr17, 0, i11);
                                                                    String[] strArr18 = this.encodings;
                                                                    String[] strArr19 = new String[i25];
                                                                    this.encodings = strArr19;
                                                                    System.arraycopy(strArr18, 0, strArr19, 0, i11);
                                                                    String[] strArr20 = this.destinationPaths;
                                                                    String[] strArr21 = new String[i25];
                                                                    this.destinationPaths = strArr21;
                                                                    System.arraycopy(strArr20, 0, strArr21, 0, i11);
                                                                    System.arraycopy(strArrFind, 0, this.filenames, i11, length2);
                                                                    for (i6 = 0; i6 < length2; i6++) {
                                                                        int i26 = i11 + i6;
                                                                        this.encodings[i26] = strSubstring3;
                                                                        this.destinationPaths[i26] = strSubstring;
                                                                    }
                                                                    i11 = i25;
                                                                } else {
                                                                    c = 0;
                                                                    this.filenames = strArrFind;
                                                                    length = strArrFind.length;
                                                                    this.encodings = new String[length];
                                                                    this.destinationPaths = new String[length];
                                                                    for (i5 = 0; i5 < length; i5++) {
                                                                        this.encodings[i5] = strSubstring3;
                                                                        this.destinationPaths[i5] = strSubstring;
                                                                    }
                                                                    i11 = length;
                                                                }
                                                                c2 = c;
                                                                str4 = null;
                                                                strSubstring3 = null;
                                                                strSubstring4 = null;
                                                                arrayList3 = arrayList3;
                                                                z8 = z2;
                                                                arrayList5 = arrayList;
                                                                str6 = str6;
                                                            }
                                                        }
                                                    }
                                                }
                                                z8 = z2;
                                                arrayList5 = arrayList;
                                            } else {
                                                if (arrayList7 != null) {
                                                    StringBuffer stringBuffer6 = new StringBuffer();
                                                    stringBuffer6.append(strSubstring2);
                                                    int i27 = i13 + 2;
                                                    if (i27 < length6) {
                                                        stringBuffer6.append(StringUtil.SPACE);
                                                        stringBuffer6.append(strArr5[i27]);
                                                    }
                                                    throw new IllegalArgumentException(bind("configure.duplicateEndorsedDirs", stringBuffer6.toString()));
                                                }
                                                c2 = 15;
                                            }
                                            strSubstring2 = strSubstring2;
                                            strSubstring3 = strSubstring3;
                                            iProcessPaths = iProcessPaths;
                                            str6 = str6;
                                            hashSet2 = hashSet2;
                                            arrayList7 = arrayList7;
                                            arrayList4 = arrayList4;
                                            z7 = z7;
                                            i12 = i12;
                                            arrayList6 = arrayList6;
                                            str5 = str5;
                                            arrayList3 = arrayList3;
                                            arrayList5 = arrayList;
                                            z8 = true;
                                        }
                                        iProcessPaths = iProcessPaths;
                                        str6 = str6;
                                        hashSet2 = hashSet2;
                                        arrayList7 = arrayList7;
                                        arrayList4 = arrayList4;
                                        z7 = z7;
                                        i12 = i12;
                                        arrayList6 = arrayList6;
                                        str5 = str5;
                                        arrayList3 = arrayList3;
                                        z8 = z2;
                                        arrayList5 = arrayList;
                                    }
                                    strSubstring2 = strSubstring2;
                                    strSubstring3 = strSubstring3;
                                    iProcessPaths = iProcessPaths;
                                    str6 = str6;
                                    hashSet2 = hashSet2;
                                    arrayList7 = arrayList7;
                                    arrayList4 = arrayList4;
                                    z7 = z7;
                                    i12 = i12;
                                    arrayList6 = arrayList6;
                                    str5 = str5;
                                    arrayList3 = arrayList3;
                                    z8 = z2;
                                    arrayList5 = arrayList;
                                }
                            } else {
                                arrayList = arrayList5;
                                str2 = CompilerOptions.VERSION_1_5;
                            }
                            str8 = CompilerOptions.VERSION_1_7;
                            strSubstring2 = strSubstring2;
                            strSubstring3 = strSubstring3;
                            if (strSubstring2.endsWith(SuffixConstants.SUFFIX_STRING_java)) {
                                strArr2 = this.filenames;
                                if (strArr2 == null) {
                                    int i110 = length6 - iProcessPaths;
                                    this.filenames = new String[i110];
                                    this.encodings = new String[i110];
                                    this.destinationPaths = new String[i110];
                                } else if (i11 == strArr2.length) {
                                    int length12 = strArr2.length;
                                    int i111 = (length12 + length6) - iProcessPaths;
                                    String[] strArr110 = new String[i111];
                                    this.filenames = strArr110;
                                    System.arraycopy(strArr2, 0, strArr110, 0, length12);
                                    String[] strArr111 = this.encodings;
                                    String[] strArr112 = new String[i111];
                                    this.encodings = strArr112;
                                    System.arraycopy(strArr111, 0, strArr112, 0, length12);
                                    String[] strArr113 = this.destinationPaths;
                                    String[] strArr114 = new String[i111];
                                    this.destinationPaths = strArr114;
                                    System.arraycopy(strArr113, 0, strArr114, 0, length12);
                                }
                                this.filenames[i11] = strSubstring2;
                                this.encodings[i11] = strSubstring3;
                                strSubstring2 = strSubstring2;
                                i11++;
                                strSubstring3 = null;
                                arrayList3 = arrayList3;
                                z8 = z2;
                                arrayList5 = arrayList;
                                c2 = 0;
                                str6 = str6;
                            } else {
                                if (strSubstring2.equals("-log")) {
                                    if (this.log == null) {
                                        throw new IllegalArgumentException(bind("configure.duplicateLog", strSubstring2));
                                    }
                                    c2 = 5;
                                } else {
                                    int i112 = 6;
                                    if (strSubstring2.equals("-repeat")) {
                                        if (this.maxRepetition <= 0) {
                                            throw new IllegalArgumentException(bind("configure.duplicateRepeat", strSubstring2));
                                        }
                                        strSubstring2 = strSubstring2;
                                        strSubstring3 = strSubstring3;
                                        c2 = 6;
                                    } else if (strSubstring2.equals("-maxProblems")) {
                                        if (this.maxProblems <= 0) {
                                            throw new IllegalArgumentException(bind("configure.duplicateMaxProblems", strSubstring2));
                                        }
                                        c2 = 11;
                                    } else if (strSubstring2.equals("-source")) {
                                        strSubstring2 = strSubstring2;
                                        strSubstring3 = strSubstring3;
                                        iProcessPaths = iProcessPaths;
                                        str6 = str6;
                                        hashSet2 = hashSet2;
                                        arrayList7 = arrayList7;
                                        arrayList4 = arrayList4;
                                        z7 = z7;
                                        i12 = i12;
                                        arrayList6 = arrayList6;
                                        str5 = str5;
                                        arrayList3 = arrayList3;
                                        z8 = z2;
                                        arrayList5 = arrayList;
                                        c2 = 7;
                                    } else if (strSubstring2.equals("-encoding")) {
                                        c2 = '\b';
                                    } else {
                                        if (strSubstring2.equals("-1.3")) {
                                            if (!z2) {
                                                throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                            }
                                            this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_3);
                                        } else if (strSubstring2.equals("-1.4")) {
                                            if (!z2) {
                                                throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                            }
                                            this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_4);
                                        } else {
                                            if (strSubstring2.equals("-1.5")) {
                                            }
                                            z3 = true;
                                            if (!z2) {
                                                throw new IllegalArgumentException(bind("configure.duplicateCompliance", strSubstring2));
                                            }
                                            this.options.put(CompilerOptions.OPTION_Compliance, str2);
                                            z8 = z3;
                                            arrayList5 = arrayList;
                                        }
                                        strSubstring2 = strSubstring2;
                                        strSubstring3 = strSubstring3;
                                        iProcessPaths = iProcessPaths;
                                        str6 = str6;
                                        hashSet2 = hashSet2;
                                        arrayList7 = arrayList7;
                                        arrayList4 = arrayList4;
                                        z7 = z7;
                                        i12 = i12;
                                        arrayList6 = arrayList6;
                                        str5 = str5;
                                        arrayList3 = arrayList3;
                                        arrayList5 = arrayList;
                                        z8 = true;
                                    }
                                    iProcessPaths = iProcessPaths;
                                    str6 = str6;
                                    hashSet2 = hashSet2;
                                    arrayList7 = arrayList7;
                                    arrayList4 = arrayList4;
                                    z7 = z7;
                                    i12 = i12;
                                    arrayList6 = arrayList6;
                                    str5 = str5;
                                    arrayList3 = arrayList3;
                                    z8 = z2;
                                    arrayList5 = arrayList;
                                }
                                strSubstring2 = strSubstring2;
                                strSubstring3 = strSubstring3;
                                iProcessPaths = iProcessPaths;
                                str6 = str6;
                                hashSet2 = hashSet2;
                                arrayList7 = arrayList7;
                                arrayList4 = arrayList4;
                                z7 = z7;
                                i12 = i12;
                                arrayList6 = arrayList6;
                                str5 = str5;
                                arrayList3 = arrayList3;
                                z8 = z2;
                                arrayList5 = arrayList;
                            }
                        }
                        break;
                    case 1:
                        arrayList2 = arrayList3;
                        iProcessPaths += processPaths(strArr5, iProcessPaths, strSubstring2, arrayList5);
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        arrayList3 = arrayList2;
                        break;
                    case 2:
                    case '\n':
                    case 14:
                    default:
                        arrayList3 = arrayList3;
                        arrayList = arrayList5;
                        z2 = z8;
                        strSubstring = str4;
                        strSubstring3 = strSubstring3;
                        if (strSubstring == null) {
                            if (File.separatorChar != '/') {
                                strSubstring2 = strSubstring2.replace('/', File.separatorChar);
                            }
                            if (strSubstring2.endsWith("[-d")) {
                                strSubstring4 = strSubstring2.substring(0, strSubstring2.length() - 3);
                                c2 = 16;
                                str4 = strSubstring;
                                strSubstring2 = strSubstring2;
                                iProcessPaths = iProcessPaths;
                                str6 = str6;
                                hashSet2 = hashSet2;
                                arrayList7 = arrayList7;
                                arrayList4 = arrayList4;
                                z7 = z7;
                                i12 = i12;
                                arrayList6 = arrayList6;
                                str5 = str5;
                                arrayList3 = arrayList3;
                                z8 = z2;
                                arrayList5 = arrayList;
                            } else {
                                strSubstring2 = strSubstring2;
                            }
                        } else {
                            strSubstring2 = strSubstring2;
                            strSubstring2 = strSubstring4;
                        }
                        file = new File(strSubstring2);
                        if (!file.isDirectory()) {
                            throw new IllegalArgumentException(bind("configure.unrecognizedOption", strSubstring2));
                        }
                        strArrFind = FileFinder.find(file, SuffixConstants.SUFFIX_STRING_JAVA);
                        if ("none".equals(strSubstring)) {
                            strSubstring = "none";
                        }
                        strArr3 = this.filenames;
                        if (strArr3 != null) {
                            length2 = strArrFind.length;
                            int i28 = length2 + i11;
                            String[] strArr115 = new String[i28];
                            this.filenames = strArr115;
                            c = 0;
                            System.arraycopy(strArr3, 0, strArr115, 0, i11);
                            String[] strArr116 = this.encodings;
                            String[] strArr117 = new String[i28];
                            this.encodings = strArr117;
                            System.arraycopy(strArr116, 0, strArr117, 0, i11);
                            String[] strArr22 = this.destinationPaths;
                            String[] strArr23 = new String[i28];
                            this.destinationPaths = strArr23;
                            System.arraycopy(strArr22, 0, strArr23, 0, i11);
                            System.arraycopy(strArrFind, 0, this.filenames, i11, length2);
                            while (i6 < length2) {
                                int i29 = i11 + i6;
                                this.encodings[i29] = strSubstring3;
                                this.destinationPaths[i29] = strSubstring;
                            }
                            i11 = i28;
                        } else {
                            c = 0;
                            this.filenames = strArrFind;
                            length = strArrFind.length;
                            this.encodings = new String[length];
                            this.destinationPaths = new String[length];
                            while (i5 < length) {
                                this.encodings[i5] = strSubstring3;
                                this.destinationPaths[i5] = strSubstring;
                            }
                            i11 = length;
                        }
                        c2 = c;
                        str4 = null;
                        strSubstring3 = null;
                        strSubstring4 = null;
                        arrayList3 = arrayList3;
                        z8 = z2;
                        arrayList5 = arrayList;
                        str6 = str6;
                        break;
                        break;
                    case 3:
                        arrayList3 = arrayList3;
                        z2 = z8;
                        setDestinationPath(strSubstring2.equals("none") ? "none" : strSubstring2);
                        strSubstring2 = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        arrayList3 = arrayList3;
                        z8 = z2;
                        break;
                    case 4:
                        arrayList3 = arrayList3;
                        if (this.didSpecifyTarget) {
                            throw new IllegalArgumentException(bind("configure.duplicateTarget", strSubstring2));
                        }
                        this.didSpecifyTarget = true;
                        z2 = z8;
                        if (strSubstring2.equals(CompilerOptions.VERSION_1_1)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_1);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_2)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_2);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_3)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_3);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_4)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_5) || strSubstring2.equals("5") || strSubstring2.equals("5.0")) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_5);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_6) || strSubstring2.equals("6") || strSubstring2.equals("6.0")) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_7) || strSubstring2.equals("7") || strSubstring2.equals("7.0")) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_7);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_8) || strSubstring2.equals("8") || strSubstring2.equals("8.0")) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_8);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_JSR14)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_JSR14);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_CLDC1_1)) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_CLDC1_1);
                            this.options.put(CompilerOptions.OPTION_InlineJsr, CompilerOptions.ENABLED);
                        } else {
                            throw new IllegalArgumentException(bind("configure.targetJDK", strSubstring2));
                        }
                        strSubstring2 = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        arrayList3 = arrayList3;
                        z8 = z2;
                        break;
                        break;
                    case 5:
                        this.log = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        arrayList7 = arrayList7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                    case 6:
                        arrayList2 = arrayList3;
                        try {
                            int i30 = Integer.parseInt(strSubstring2);
                            this.maxRepetition = i30;
                            if (i30 <= 0) {
                                throw new IllegalArgumentException(bind("configure.repetition", strSubstring2));
                            }
                            iProcessPaths = iProcessPaths;
                            str6 = str6;
                            hashSet2 = hashSet2;
                            strSubstring3 = strSubstring3;
                            arrayList7 = arrayList7;
                            arrayList4 = arrayList4;
                            z7 = z7;
                            i12 = i12;
                            arrayList6 = arrayList6;
                            str5 = str5;
                            arrayList3 = arrayList2;
                        } catch (NumberFormatException unused3) {
                            throw new IllegalArgumentException(bind("configure.repetition", strSubstring2));
                        }
                        break;
                    case 7:
                        if (this.didSpecifySource) {
                            throw new IllegalArgumentException(bind("configure.duplicateSource", strSubstring2));
                        }
                        this.didSpecifySource = true;
                        arrayList2 = arrayList3;
                        if (strSubstring2.equals(CompilerOptions.VERSION_1_3)) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_3);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_4)) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_4);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_5) || strSubstring2.equals("5") || strSubstring2.equals("5.0")) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_5);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_6) || strSubstring2.equals("6") || strSubstring2.equals("6.0")) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_6);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_7) || strSubstring2.equals("7") || strSubstring2.equals("7.0")) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_7);
                        } else if (strSubstring2.equals(CompilerOptions.VERSION_1_8) || strSubstring2.equals("8") || strSubstring2.equals("8.0")) {
                            this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_8);
                        } else {
                            throw new IllegalArgumentException(bind("configure.source", strSubstring2));
                        }
                        iProcessPaths = iProcessPaths;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        arrayList3 = arrayList2;
                        break;
                        break;
                    case '\b':
                        if (hashSet2 != null) {
                            hashSet = hashSet2;
                            if (!hashSet.contains(strSubstring2)) {
                                if (hashSet.size() > 1) {
                                    this.logger.logWarning(bind("configure.differentencodings", strSubstring2, getAllEncodings(hashSet)));
                                } else {
                                    this.logger.logWarning(bind("configure.differentencoding", strSubstring2, getAllEncodings(hashSet)));
                                }
                            }
                        } else {
                            hashSet = new HashSet();
                        }
                        try {
                            new InputStreamReader(new ByteArrayInputStream(new byte[0]), strSubstring2);
                            hashSet.add(strSubstring2);
                            this.options.put(CompilerOptions.OPTION_Encoding, strSubstring2);
                            strSubstring2 = strSubstring2;
                            iProcessPaths = iProcessPaths;
                            str6 = str6;
                            strSubstring3 = strSubstring3;
                            arrayList7 = arrayList7;
                            arrayList4 = arrayList4;
                            z7 = z7;
                            i12 = i12;
                            arrayList6 = arrayList6;
                            c2 = 0;
                            hashSet2 = hashSet;
                            str5 = str5;
                        } catch (UnsupportedEncodingException unused4) {
                            throw new IllegalArgumentException(bind("configure.unsupportedEncoding", strSubstring2));
                        }
                        break;
                    case '\t':
                        iProcessPaths += processPaths(strArr5, iProcessPaths, strSubstring2, arrayList3);
                        arrayList7 = arrayList7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                    case 11:
                        try {
                            int i31 = Integer.parseInt(strSubstring2);
                            this.maxProblems = i31;
                            if (i31 <= 0) {
                                throw new IllegalArgumentException(bind("configure.maxProblems", strSubstring2));
                            }
                            this.options.put(CompilerOptions.OPTION_MaxProblemPerUnit, strSubstring2);
                            iProcessPaths = iProcessPaths;
                            arrayList7 = arrayList7;
                            i12 = i12;
                            arrayList6 = arrayList6;
                            str5 = str5;
                        } catch (NumberFormatException unused5) {
                            throw new IllegalArgumentException(bind("configure.maxProblems", strSubstring2));
                        }
                        break;
                    case '\f':
                        if (strSubstring2.indexOf("[-d") != -1) {
                            throw new IllegalArgumentException(bind("configure.unexpectedDestinationPathEntry", "-extdir"));
                        }
                        StringTokenizer stringTokenizer4 = new StringTokenizer(strSubstring2, File.pathSeparator, false);
                        arrayList6 = new ArrayList(4);
                        while (stringTokenizer4.hasMoreTokens()) {
                            arrayList6.add(stringTokenizer4.nextToken());
                        }
                        strSubstring2 = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        str5 = str5;
                        break;
                        break;
                    case '\r':
                        String[] strArr24 = new String[1];
                        int iProcessPaths2 = processPaths(strArr5, iProcessPaths, strSubstring2, strArr24) + iProcessPaths;
                        String str9 = strArr24[0];
                        strSubstring2 = strSubstring2;
                        c2 = 0;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList4 = arrayList4;
                        z7 = z7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        iProcessPaths = iProcessPaths2;
                        str5 = str9;
                        break;
                    case 15:
                        if (strSubstring2.indexOf("[-d") != -1) {
                            throw new IllegalArgumentException(bind("configure.unexpectedDestinationPathEntry", "-endorseddirs"));
                        }
                        StringTokenizer stringTokenizer5 = new StringTokenizer(strSubstring2, File.pathSeparator, false);
                        arrayList7 = new ArrayList(4);
                        while (stringTokenizer5.hasMoreTokens()) {
                            arrayList7.add(stringTokenizer5.nextToken());
                        }
                        strSubstring2 = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                        break;
                    case 16:
                        if (strSubstring2.endsWith("]")) {
                            arrayList3 = arrayList3;
                            arrayList = arrayList5;
                            strSubstring = strSubstring2.substring(0, strSubstring2.length() - 1);
                            z2 = z8;
                            strSubstring3 = strSubstring3;
                            if (strSubstring == null) {
                                if (File.separatorChar != '/') {
                                    strSubstring2 = strSubstring2.replace('/', File.separatorChar);
                                }
                                if (strSubstring2.endsWith("[-d")) {
                                    strSubstring4 = strSubstring2.substring(0, strSubstring2.length() - 3);
                                    c2 = 16;
                                    str4 = strSubstring;
                                    strSubstring2 = strSubstring2;
                                    iProcessPaths = iProcessPaths;
                                    str6 = str6;
                                    hashSet2 = hashSet2;
                                    arrayList7 = arrayList7;
                                    arrayList4 = arrayList4;
                                    z7 = z7;
                                    i12 = i12;
                                    arrayList6 = arrayList6;
                                    str5 = str5;
                                    arrayList3 = arrayList3;
                                    z8 = z2;
                                    arrayList5 = arrayList;
                                } else {
                                    strSubstring2 = strSubstring2;
                                }
                            } else {
                                strSubstring2 = strSubstring2;
                                strSubstring2 = strSubstring4;
                            }
                            file = new File(strSubstring2);
                            if (!file.isDirectory()) {
                                throw new IllegalArgumentException(bind("configure.unrecognizedOption", strSubstring2));
                            }
                            strArrFind = FileFinder.find(file, SuffixConstants.SUFFIX_STRING_JAVA);
                            if ("none".equals(strSubstring)) {
                                strSubstring = "none";
                            }
                            strArr3 = this.filenames;
                            if (strArr3 != null) {
                                length2 = strArrFind.length;
                                int i210 = length2 + i11;
                                String[] strArr118 = new String[i210];
                                this.filenames = strArr118;
                                c = 0;
                                System.arraycopy(strArr3, 0, strArr118, 0, i11);
                                String[] strArr119 = this.encodings;
                                String[] strArr1110 = new String[i210];
                                this.encodings = strArr1110;
                                System.arraycopy(strArr119, 0, strArr1110, 0, i11);
                                String[] strArr25 = this.destinationPaths;
                                String[] strArr26 = new String[i210];
                                this.destinationPaths = strArr26;
                                System.arraycopy(strArr25, 0, strArr26, 0, i11);
                                System.arraycopy(strArrFind, 0, this.filenames, i11, length2);
                                while (i6 < length2) {
                                    int i211 = i11 + i6;
                                    this.encodings[i211] = strSubstring3;
                                    this.destinationPaths[i211] = strSubstring;
                                }
                                i11 = i210;
                            } else {
                                c = 0;
                                this.filenames = strArrFind;
                                length = strArrFind.length;
                                this.encodings = new String[length];
                                this.destinationPaths = new String[length];
                                while (i5 < length) {
                                    this.encodings[i5] = strSubstring3;
                                    this.destinationPaths[i5] = strSubstring;
                                }
                                i11 = length;
                            }
                            c2 = c;
                            str4 = null;
                            strSubstring3 = null;
                            strSubstring4 = null;
                            arrayList3 = arrayList3;
                            z8 = z2;
                            arrayList5 = arrayList;
                            str6 = str6;
                        } else {
                            throw new IllegalArgumentException(bind("configure.incorrectDestinationPathEntry", "[-d " + strSubstring2));
                        }
                        break;
                    case 17:
                    case 18:
                    case 19:
                        iProcessPaths = iProcessPaths;
                        arrayList7 = arrayList7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                    case 20:
                        StringTokenizer stringTokenizer6 = new StringTokenizer(strSubstring2, ",");
                        if (this.classNames == null) {
                            this.classNames = new String[4];
                        }
                        i12 = i12;
                        while (stringTokenizer6.hasMoreTokens()) {
                            String[] strArr27 = this.classNames;
                            if (strArr27.length == i12) {
                                String[] strArr28 = new String[i12 * 2];
                                this.classNames = strArr28;
                                System.arraycopy(strArr27, 0, strArr28, 0, i12);
                            }
                            this.classNames[i12] = stringTokenizer6.nextToken();
                            i12++;
                        }
                        strSubstring2 = strSubstring2;
                        iProcessPaths = iProcessPaths;
                        str6 = str6;
                        hashSet2 = hashSet2;
                        strSubstring3 = strSubstring3;
                        arrayList7 = arrayList7;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                    case 21:
                        initializeWarnings(strSubstring2);
                        iProcessPaths = iProcessPaths;
                        arrayList7 = arrayList7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                    case 22:
                        if (strSubstring2.isEmpty() || strSubstring2.charAt(0) == '-') {
                            throw new IllegalArgumentException(bind("configure.missingAnnotationPath", strSubstring2));
                        }
                        if (ANNOTATION_SOURCE_CLASSPATH.equals(strSubstring2)) {
                            this.annotationsFromClasspath = true;
                        } else {
                            if (this.annotationPaths == null) {
                                this.annotationPaths = new ArrayList();
                            }
                            StringTokenizer stringTokenizer7 = new StringTokenizer(strSubstring2, File.pathSeparator);
                            while (stringTokenizer7.hasMoreTokens()) {
                                this.annotationPaths.add(stringTokenizer7.nextToken());
                            }
                        }
                        iProcessPaths = iProcessPaths;
                        arrayList7 = arrayList7;
                        i12 = i12;
                        arrayList6 = arrayList6;
                        str5 = str5;
                        break;
                        break;
                }
            }
        }
    }

    private static char[][] decodeIgnoreOptionalProblemsFromFolders(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, File.pathSeparator);
        char[][] cArr = new char[stringTokenizer.countTokens()][];
        int i = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            File file = new File(strNextToken);
            if (file.exists()) {
                int i2 = i + 1;
                try {
                    cArr[i] = file.getCanonicalPath().toCharArray();
                    i = i2;
                } catch (IOException unused) {
                    i += 2;
                    cArr[i2] = strNextToken.toCharArray();
                }
            } else {
                cArr[i] = strNextToken.toCharArray();
                i++;
            }
        }
        return cArr;
    }

    private static String getAllEncodings(Set set) {
        int size = set.size();
        String[] strArr = new String[size];
        set.toArray(strArr);
        Arrays.sort(strArr);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(strArr[i]);
        }
        return String.valueOf(stringBuffer);
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Not initialized variable reg: 1, insn: 0x00a8: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:36:0x00a8 */
    private void initializeWarnings(String str) throws Throwable {
        IOException e;
        BufferedInputStream bufferedInputStream;
        if (!new File(str).exists()) {
            throw new IllegalArgumentException(bind("configure.missingwarningspropertiesfile", str));
        }
        BufferedInputStream bufferedInputStream2 = null;
        try {
            try {
                BufferedInputStream bufferedInputStream3 = new BufferedInputStream(new FileInputStream(str));
                try {
                    Properties properties = new Properties();
                    properties.load(bufferedInputStream3);
                    try {
                        bufferedInputStream3.close();
                    } catch (IOException unused) {
                    }
                    for (Map.Entry entry : properties.entrySet()) {
                        String string = entry.getKey().toString();
                        if (string.startsWith("org.eclipse.jdt.core.compiler.")) {
                            this.options.put(string, entry.getValue().toString());
                        }
                    }
                    if (!properties.containsKey(CompilerOptions.OPTION_LocalVariableAttribute)) {
                        this.options.put(CompilerOptions.OPTION_LocalVariableAttribute, CompilerOptions.GENERATE);
                    }
                    if (!properties.containsKey(CompilerOptions.OPTION_PreserveUnusedLocal)) {
                        this.options.put(CompilerOptions.OPTION_PreserveUnusedLocal, "preserve");
                    }
                    if (!properties.containsKey(CompilerOptions.OPTION_DocCommentSupport)) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                    }
                    if (properties.containsKey(CompilerOptions.OPTION_ReportForbiddenReference)) {
                        return;
                    }
                    this.options.put(CompilerOptions.OPTION_ReportForbiddenReference, "error");
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    throw new IllegalArgumentException(bind("configure.ioexceptionwarningspropertiesfile", str));
                }
            } catch (Throwable th) {
                th = th;
                bufferedInputStream2 = bufferedInputStream;
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedInputStream2 != null) {
                bufferedInputStream2.close();
            }
            throw th;
        }
    }

    protected void enableAll(int i) {
        String str;
        if (i != 0) {
            str = i != 1 ? null : "error";
        } else {
            str = CompilerOptions.WARNING;
        }
        for (Map.Entry entry : (Map.Entry[]) this.options.entrySet().toArray(new Map.Entry[this.options.size()])) {
            if (((String) entry.getValue()).equals("ignore")) {
                this.options.put((String) entry.getKey(), str);
            }
        }
        this.options.put(CompilerOptions.OPTION_TaskTags, Util.EMPTY_STRING);
    }

    protected void disableAll(int i) {
        String str;
        if (i != 0) {
            str = i != 1 ? null : "error";
        } else {
            str = CompilerOptions.WARNING;
        }
        for (Object obj : this.options.entrySet().toArray()) {
            Map.Entry entry = (Map.Entry) obj;
            if ((entry.getKey() instanceof String) && (entry.getValue() instanceof String) && ((String) entry.getValue()).equals(str)) {
                this.options.put((String) entry.getKey(), "ignore");
            }
        }
    }

    public String extractDestinationPathFromSourceFile(CompilationResult compilationResult) {
        char[] fileName;
        int iLastIndexOf;
        ICompilationUnit iCompilationUnit = compilationResult.compilationUnit;
        if (iCompilationUnit != null && (iLastIndexOf = CharOperation.lastIndexOf(File.separatorChar, (fileName = iCompilationUnit.getFileName()))) != -1) {
            String str = new String(fileName, 0, iLastIndexOf);
            File file = new File(str);
            if (file.exists() && file.isDirectory()) {
                return str;
            }
        }
        return System.getProperty("user.dir");
    }

    public ICompilerRequestor getBatchRequestor() {
        return new BatchCompilerRequestor(this);
    }

    public CompilationUnit[] getCompilationUnits() {
        String canonicalPath;
        int length = this.filenames.length;
        CompilationUnit[] compilationUnitArr = new CompilationUnit[length];
        HashtableOfObject hashtableOfObject = new HashtableOfObject(length);
        String str = this.options.get(CompilerOptions.OPTION_Encoding);
        if (Util.EMPTY_STRING.equals(str)) {
            str = null;
        }
        for (int i = 0; i < length; i++) {
            char[] charArray = this.filenames[i].toCharArray();
            if (hashtableOfObject.get(charArray) != null) {
                throw new IllegalArgumentException(bind("unit.more", this.filenames[i]));
            }
            hashtableOfObject.put(charArray, charArray);
            File file = new File(this.filenames[i]);
            if (!file.exists()) {
                throw new IllegalArgumentException(bind("unit.missing", this.filenames[i]));
            }
            String str2 = this.encodings[i];
            String str3 = str2 == null ? str : str2;
            try {
                canonicalPath = file.getCanonicalPath();
            } catch (IOException unused) {
                canonicalPath = this.filenames[i];
            }
            String str4 = canonicalPath;
            compilationUnitArr[i] = new CompilationUnit(null, str4, str3, this.destinationPaths[i], shouldIgnoreOptionalProblems(this.ignoreOptionalProblemsFromFolders, str4.toCharArray()));
        }
        return compilationUnitArr;
    }

    public IErrorHandlingPolicy getHandlingPolicy() {
        return new IErrorHandlingPolicy() { // from class: org.eclipse.jdt.internal.compiler.batch.Main.2
            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean ignoreAllErrors() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean stopOnFirstError() {
                return false;
            }

            @Override // org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy
            public boolean proceedOnErrors() {
                return Main.this.proceedOnError;
            }
        };
    }

    public File getJavaHome() {
        if (!this.javaHomeChecked) {
            this.javaHomeChecked = true;
            this.javaHomeCache = Util.getJavaHome();
        }
        return this.javaHomeCache;
    }

    public FileSystem getLibraryAccess() {
        return new FileSystem(this.checkedClasspaths, this.filenames, this.annotationsFromClasspath && CompilerOptions.ENABLED.equals(this.options.get(CompilerOptions.OPTION_AnnotationBasedNullAnalysis)));
    }

    public IProblemFactory getProblemFactory() {
        return new DefaultProblemFactory(this.compilerLocale);
    }

    protected ArrayList handleBootclasspath(ArrayList arrayList, String str) {
        int size;
        if (arrayList != null && (size = arrayList.size()) != 0) {
            String[] strArr = new String[size];
            arrayList.toArray(strArr);
            arrayList.clear();
            for (int i = 0; i < size; i++) {
                processPathEntries(4, arrayList, strArr[i], str, false, true);
            }
        } else {
            arrayList = new ArrayList(4);
            try {
                Util.collectRunningVMBootclasspath(arrayList);
            } catch (IllegalStateException unused) {
                this.logger.logWrongJDK();
                this.proceed = false;
                return null;
            }
        }
        return arrayList;
    }

    protected ArrayList handleClasspath(ArrayList arrayList, String str) {
        int size;
        if (arrayList != null && (size = arrayList.size()) != 0) {
            String[] strArr = new String[size];
            arrayList.toArray(strArr);
            arrayList.clear();
            for (int i = 0; i < size; i++) {
                processPathEntries(4, arrayList, strArr[i], str, false, true);
            }
        } else {
            arrayList = new ArrayList(4);
            String property = System.getProperty(SystemProperties.JAVA_CLASS_PATH);
            if (property == null || property.length() == 0) {
                addPendingErrors(bind("configure.noClasspath"));
                FileSystem.Classpath classpath = FileSystem.getClasspath(System.getProperty("user.dir"), str, null, this.options);
                if (classpath != null) {
                    arrayList.add(classpath);
                }
            } else {
                StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
                while (stringTokenizer.hasMoreTokens()) {
                    String strNextToken = stringTokenizer.nextToken();
                    FileSystem.Classpath classpath2 = FileSystem.getClasspath(strNextToken, str, null, this.options);
                    if (classpath2 != null) {
                        arrayList.add(classpath2);
                    } else if (strNextToken.length() != 0) {
                        addPendingErrors(bind("configure.incorrectClasspath", strNextToken));
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList();
        HashMap map = new HashMap();
        FileSystem.ClasspathSectionProblemReporter classpathSectionProblemReporter = new FileSystem.ClasspathSectionProblemReporter() { // from class: org.eclipse.jdt.internal.compiler.batch.Main.3
            @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.ClasspathSectionProblemReporter
            public void invalidClasspathSection(String str2) {
                Main main = Main.this;
                main.addPendingErrors(main.bind("configure.invalidClasspathSection", str2));
            }

            @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.ClasspathSectionProblemReporter
            public void multipleClasspathSections(String str2) {
                Main main = Main.this;
                main.addPendingErrors(main.bind("configure.multipleClasspathSections", str2));
            }
        };
        while (!arrayList.isEmpty()) {
            FileSystem.Classpath classpath3 = (FileSystem.Classpath) arrayList.remove(0);
            String path = classpath3.getPath();
            if (map.get(path) == null) {
                map.put(path, classpath3);
                arrayList2.add(classpath3);
                List listFetchLinkedJars = classpath3.fetchLinkedJars(classpathSectionProblemReporter);
                if (listFetchLinkedJars != null) {
                    arrayList.addAll(0, listFetchLinkedJars);
                }
            }
        }
        return arrayList2;
    }

    protected ArrayList handleEndorseddirs(ArrayList arrayList) {
        File javaHome = getJavaHome();
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList(4);
            String property = System.getProperty(SystemProperties.JAVA_ENDORSED_DIRS);
            if (property != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList2.add(stringTokenizer.nextToken());
                }
            } else if (javaHome != null) {
                arrayList2.add(String.valueOf(javaHome.getAbsolutePath()) + "/lib/endorsed");
            }
            arrayList = arrayList2;
        }
        if (arrayList.size() != 0) {
            int size = arrayList.size();
            File[] fileArr = new File[size];
            for (int i = 0; i < size; i++) {
                fileArr[i] = new File((String) arrayList.get(i));
            }
            arrayList.clear();
            File[][] librariesFiles = getLibrariesFiles(fileArr);
            if (librariesFiles != null) {
                int length = librariesFiles.length;
                for (int i2 = 0; i2 < length; i2++) {
                    File[] fileArr2 = librariesFiles[i2];
                    if (fileArr2 != null) {
                        for (File file : fileArr2) {
                            FileSystem.Classpath classpath = FileSystem.getClasspath(file.getAbsolutePath(), null, null, this.options);
                            if (classpath != null) {
                                arrayList.add(classpath);
                            }
                        }
                    } else if (fileArr[i2].isFile()) {
                        addPendingErrors(bind("configure.incorrectEndorsedDirsEntry", fileArr[i2].getAbsolutePath()));
                    }
                }
            }
        }
        return arrayList;
    }

    protected ArrayList handleExtdirs(ArrayList arrayList) {
        File javaHome = getJavaHome();
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList(4);
            String property = System.getProperty(SystemProperties.JAVA_EXT_DIRS);
            if (property == null) {
                arrayList2.add(String.valueOf(javaHome.getAbsolutePath()) + "/lib/ext");
            } else {
                StringTokenizer stringTokenizer = new StringTokenizer(property, File.pathSeparator);
                while (stringTokenizer.hasMoreTokens()) {
                    arrayList2.add(stringTokenizer.nextToken());
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList.size() != 0) {
            int size = arrayList.size();
            File[] fileArr = new File[size];
            for (int i = 0; i < size; i++) {
                fileArr[i] = new File((String) arrayList.get(i));
            }
            arrayList.clear();
            File[][] librariesFiles = getLibrariesFiles(fileArr);
            if (librariesFiles != null) {
                int length = librariesFiles.length;
                for (int i2 = 0; i2 < length; i2++) {
                    File[] fileArr2 = librariesFiles[i2];
                    if (fileArr2 != null) {
                        for (File file : fileArr2) {
                            FileSystem.Classpath classpath = FileSystem.getClasspath(file.getAbsolutePath(), null, null, this.options);
                            if (classpath != null) {
                                arrayList.add(classpath);
                            }
                        }
                    } else if (fileArr[i2].isFile()) {
                        addPendingErrors(bind("configure.incorrectExtDirsEntry", fileArr[i2].getAbsolutePath()));
                    }
                }
            }
        }
        return arrayList;
    }

    protected void handleWarningToken(String str, boolean z) {
        handleErrorOrWarningToken(str, z, 0);
    }

    protected void handleErrorToken(String str, boolean z) {
        handleErrorOrWarningToken(str, z, 1);
    }

    private void setSeverity(String str, int i, boolean z) {
        if (z) {
            if (i == 0) {
                this.options.put(str, CompilerOptions.WARNING);
                return;
            } else if (i == 1) {
                this.options.put(str, "error");
                return;
            } else {
                this.options.put(str, "ignore");
                return;
            }
        }
        if (i == 0) {
            if (CompilerOptions.WARNING.equals(this.options.get(str))) {
                this.options.put(str, "ignore");
            }
        } else {
            if (i == 1) {
                if ("error".equals(this.options.get(str))) {
                    this.options.put(str, "ignore");
                    return;
                }
                return;
            }
            this.options.put(str, "ignore");
        }
    }

    private void handleErrorOrWarningToken(String str, boolean z, int i) {
        if (str.length() == 0) {
            return;
        }
        char cCharAt = str.charAt(0);
        String strBind = null;
        String str2 = CompilerOptions.ENABLED;
        String str3 = CompilerOptions.DISABLED;
        switch (cCharAt) {
            case 'a':
                if (str.equals("allDeprecation")) {
                    setSeverity(CompilerOptions.OPTION_ReportDeprecation, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportDeprecationInDeprecatedCode, z ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
                    Map<String, String> map = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map.put(CompilerOptions.OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, str2);
                    return;
                }
                if (str.equals("allJavadoc")) {
                    this.warnJavadocOn = z;
                    this.warnAllJavadocOn = z;
                    setSeverity(CompilerOptions.OPTION_ReportInvalidJavadoc, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocTags, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocComments, i, z);
                    return;
                }
                if (str.equals("assertIdentifier")) {
                    setSeverity(CompilerOptions.OPTION_ReportAssertIdentifier, i, z);
                    return;
                }
                if (str.equals("allDeadCode")) {
                    setSeverity(CompilerOptions.OPTION_ReportDeadCode, i, z);
                    Map<String, String> map2 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map2.put(CompilerOptions.OPTION_ReportDeadCodeInTrivialIfStatement, str2);
                    return;
                }
                if (str.equals("allOver-ann")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingOverrideAnnotation, i, z);
                    Map<String, String> map3 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map3.put(CompilerOptions.OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation, str2);
                    return;
                }
                if (str.equals("all-static-method")) {
                    setSeverity(CompilerOptions.OPTION_ReportMethodCanBeStatic, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportMethodCanBePotentiallyStatic, i, z);
                    return;
                } else if (str.equals(DevModeOverwritingStrategies.ALL)) {
                    if (z) {
                        enableAll(i);
                        return;
                    } else {
                        disableAll(i);
                        return;
                    }
                }
                break;
            case 'b':
                if (str.equals("boxing")) {
                    setSeverity(CompilerOptions.OPTION_ReportAutoboxing, i, z);
                    return;
                }
                break;
            case 'c':
                if (str.equals("constructorName")) {
                    setSeverity(CompilerOptions.OPTION_ReportMethodWithConstructorName, i, z);
                    return;
                }
                if (str.equals("conditionAssign")) {
                    setSeverity(CompilerOptions.OPTION_ReportPossibleAccidentalBooleanAssignment, i, z);
                    return;
                } else if (str.equals("compareIdentical")) {
                    setSeverity(CompilerOptions.OPTION_ReportComparingIdentical, i, z);
                    return;
                } else if (str.equals("charConcat")) {
                    setSeverity(CompilerOptions.OPTION_ReportNoImplicitStringConversion, i, z);
                    return;
                }
                break;
            case 'd':
                if (str.equals("deprecation")) {
                    setSeverity(CompilerOptions.OPTION_ReportDeprecation, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportDeprecationInDeprecatedCode, CompilerOptions.DISABLED);
                    this.options.put(CompilerOptions.OPTION_ReportDeprecationWhenOverridingDeprecatedMethod, CompilerOptions.DISABLED);
                    return;
                } else if (str.equals("dep-ann")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingDeprecatedAnnotation, i, z);
                    return;
                } else if (str.equals("discouraged")) {
                    setSeverity(CompilerOptions.OPTION_ReportDiscouragedReference, i, z);
                    return;
                } else if (str.equals("deadCode")) {
                    setSeverity(CompilerOptions.OPTION_ReportDeadCode, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportDeadCodeInTrivialIfStatement, CompilerOptions.DISABLED);
                    return;
                }
                break;
            case 'e':
                if (str.equals("enumSwitch")) {
                    setSeverity(CompilerOptions.OPTION_ReportIncompleteEnumSwitch, i, z);
                    return;
                }
                if (str.equals("enumSwitchPedantic")) {
                    if (z) {
                        if (i != 0) {
                            if (i == 1) {
                                setSeverity(CompilerOptions.OPTION_ReportIncompleteEnumSwitch, i, z);
                            }
                        } else if ("ignore".equals(this.options.get(CompilerOptions.OPTION_ReportIncompleteEnumSwitch))) {
                            setSeverity(CompilerOptions.OPTION_ReportIncompleteEnumSwitch, i, z);
                        }
                    }
                    Map<String, String> map4 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map4.put(CompilerOptions.OPTION_ReportMissingEnumCaseDespiteDefault, str2);
                    return;
                }
                if (str.equals("emptyBlock")) {
                    setSeverity(CompilerOptions.OPTION_ReportUndocumentedEmptyBlock, i, z);
                    return;
                } else if (str.equals("enumIdentifier")) {
                    setSeverity(CompilerOptions.OPTION_ReportEnumIdentifier, i, z);
                    return;
                }
                break;
            case 'f':
                if (str.equals("fieldHiding")) {
                    setSeverity(CompilerOptions.OPTION_ReportFieldHiding, i, z);
                    return;
                }
                if (str.equals("finalBound")) {
                    setSeverity(CompilerOptions.OPTION_ReportFinalParameterBound, i, z);
                    return;
                }
                if (str.equals(PsiKeyword.FINALLY)) {
                    setSeverity(CompilerOptions.OPTION_ReportFinallyBlockNotCompletingNormally, i, z);
                    return;
                } else if (str.equals("forbidden")) {
                    setSeverity(CompilerOptions.OPTION_ReportForbiddenReference, i, z);
                    return;
                } else if (str.equals("fallthrough")) {
                    setSeverity(CompilerOptions.OPTION_ReportFallthroughCase, i, z);
                    return;
                }
                break;
            case 'h':
                if (str.equals("hiding")) {
                    setSeverity(CompilerOptions.OPTION_ReportHiddenCatchBlock, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportLocalVariableHiding, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportFieldHiding, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportTypeParameterHiding, i, z);
                    return;
                }
                if (str.equals("hashCode")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingHashCodeMethod, i, z);
                    return;
                }
                break;
            case 'i':
                if (str.equals("indirectStatic")) {
                    setSeverity(CompilerOptions.OPTION_ReportIndirectStaticAccess, i, z);
                    return;
                }
                if (str.equals("inheritNullAnnot")) {
                    Map<String, String> map5 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map5.put(CompilerOptions.OPTION_InheritNullAnnotations, str2);
                    return;
                }
                if (str.equals("intfNonInherited") || str.equals("interfaceNonInherited")) {
                    setSeverity(CompilerOptions.OPTION_ReportIncompatibleNonInheritedInterfaceMethod, i, z);
                    return;
                }
                if (str.equals("intfAnnotation")) {
                    setSeverity(CompilerOptions.OPTION_ReportAnnotationSuperInterface, i, z);
                    return;
                }
                if (str.equals("intfRedundant")) {
                    setSeverity(CompilerOptions.OPTION_ReportRedundantSuperinterface, i, z);
                    return;
                }
                if (str.equals("includeAssertNull")) {
                    Map<String, String> map6 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map6.put(CompilerOptions.OPTION_IncludeNullInfoFromAsserts, str2);
                    return;
                }
                if (str.equals("invalidJavadoc")) {
                    setSeverity(CompilerOptions.OPTION_ReportInvalidJavadoc, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTags, z ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
                    this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsDeprecatedRef, z ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
                    Map<String, String> map7 = this.options;
                    if (z) {
                        str3 = CompilerOptions.ENABLED;
                    }
                    map7.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsNotVisibleRef, str3);
                    if (z) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsVisibility, "private");
                        return;
                    }
                    return;
                }
                if (str.equals("invalidJavadocTag")) {
                    Map<String, String> map8 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map8.put(CompilerOptions.OPTION_ReportInvalidJavadocTags, str2);
                    return;
                }
                if (str.equals("invalidJavadocTagDep")) {
                    Map<String, String> map9 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map9.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsDeprecatedRef, str2);
                    return;
                }
                if (str.equals("invalidJavadocTagNotVisible")) {
                    Map<String, String> map10 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map10.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsNotVisibleRef, str2);
                    return;
                }
                if (str.startsWith("invalidJavadocTagVisibility")) {
                    int iIndexOf = str.indexOf(40);
                    int iIndexOf2 = str.indexOf(41);
                    if (z && iIndexOf >= 0 && iIndexOf2 >= 0 && iIndexOf < iIndexOf2) {
                        strBind = str.substring(iIndexOf + 1, iIndexOf2).trim();
                    }
                    String str4 = strBind;
                    if ((str4 != null && str4.equals("public")) || str4.equals("private") || str4.equals("protected") || str4.equals("default")) {
                        this.options.put(CompilerOptions.OPTION_ReportInvalidJavadocTagsVisibility, str4);
                        return;
                    }
                    throw new IllegalArgumentException(bind("configure.invalidJavadocTagVisibility", str));
                }
                break;
            case 'j':
                if (str.equals("javadoc")) {
                    this.warnJavadocOn = z;
                    setSeverity(CompilerOptions.OPTION_ReportInvalidJavadoc, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocTags, i, z);
                    return;
                }
                break;
            case 'l':
                if (str.equals("localHiding")) {
                    setSeverity(CompilerOptions.OPTION_ReportLocalVariableHiding, i, z);
                    return;
                }
                break;
            case 'm':
                if (str.equals("maskedCatchBlock") || str.equals("maskedCatchBlocks")) {
                    setSeverity(CompilerOptions.OPTION_ReportHiddenCatchBlock, i, z);
                    return;
                }
                if (str.equals("missingJavadocTags")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocTags, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportMissingJavadocTagsOverriding, z ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
                    Map<String, String> map11 = this.options;
                    if (z) {
                        str3 = CompilerOptions.ENABLED;
                    }
                    map11.put(CompilerOptions.OPTION_ReportMissingJavadocTagsMethodTypeParameters, str3);
                    if (z) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportMissingJavadocTagsVisibility, "private");
                        return;
                    }
                    return;
                }
                if (str.equals("missingJavadocTagsOverriding")) {
                    Map<String, String> map12 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map12.put(CompilerOptions.OPTION_ReportMissingJavadocTagsOverriding, str2);
                    return;
                }
                if (str.equals("missingJavadocTagsMethod")) {
                    Map<String, String> map13 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map13.put(CompilerOptions.OPTION_ReportMissingJavadocTagsMethodTypeParameters, str2);
                    return;
                }
                if (str.startsWith("missingJavadocTagsVisibility")) {
                    int iIndexOf3 = str.indexOf(40);
                    int iIndexOf4 = str.indexOf(41);
                    if (z && iIndexOf3 >= 0 && iIndexOf4 >= 0 && iIndexOf3 < iIndexOf4) {
                        strBind = str.substring(iIndexOf3 + 1, iIndexOf4).trim();
                    }
                    String str5 = strBind;
                    if ((str5 != null && str5.equals("public")) || str5.equals("private") || str5.equals("protected") || str5.equals("default")) {
                        this.options.put(CompilerOptions.OPTION_ReportMissingJavadocTagsVisibility, str5);
                        return;
                    }
                    throw new IllegalArgumentException(bind("configure.missingJavadocTagsVisibility", str));
                }
                if (str.equals("missingJavadocComments")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocComments, i, z);
                    Map<String, String> map14 = this.options;
                    if (z) {
                        str3 = CompilerOptions.ENABLED;
                    }
                    map14.put(CompilerOptions.OPTION_ReportMissingJavadocCommentsOverriding, str3);
                    if (z) {
                        this.options.put(CompilerOptions.OPTION_DocCommentSupport, CompilerOptions.ENABLED);
                        this.options.put(CompilerOptions.OPTION_ReportMissingJavadocCommentsVisibility, "private");
                        return;
                    }
                    return;
                }
                if (str.equals("missingJavadocCommentsOverriding")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingJavadocComments, i, z);
                    Map<String, String> map15 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map15.put(CompilerOptions.OPTION_ReportMissingJavadocCommentsOverriding, str2);
                    return;
                }
                if (str.startsWith("missingJavadocCommentsVisibility")) {
                    int iIndexOf5 = str.indexOf(40);
                    int iIndexOf6 = str.indexOf(41);
                    if (z && iIndexOf5 >= 0 && iIndexOf6 >= 0 && iIndexOf5 < iIndexOf6) {
                        strBind = str.substring(iIndexOf5 + 1, iIndexOf6).trim();
                    }
                    String str6 = strBind;
                    if ((str6 != null && str6.equals("public")) || str6.equals("private") || str6.equals("protected") || str6.equals("default")) {
                        this.options.put(CompilerOptions.OPTION_ReportMissingJavadocCommentsVisibility, str6);
                        return;
                    }
                    throw new IllegalArgumentException(bind("configure.missingJavadocCommentsVisibility", str));
                }
                break;
            case 'n':
                if (str.equals("nls")) {
                    setSeverity(CompilerOptions.OPTION_ReportNonExternalizedStringLiteral, i, z);
                    return;
                }
                if (str.equals("noEffectAssign")) {
                    setSeverity(CompilerOptions.OPTION_ReportNoEffectAssignment, i, z);
                    return;
                }
                if (str.equals("noImplicitStringConversion")) {
                    setSeverity(CompilerOptions.OPTION_ReportNoImplicitStringConversion, i, z);
                    return;
                }
                if (str.equals("null")) {
                    setSeverity(CompilerOptions.OPTION_ReportNullReference, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportPotentialNullReference, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportRedundantNullCheck, i, z);
                    return;
                }
                if (str.equals("nullDereference")) {
                    setSeverity(CompilerOptions.OPTION_ReportNullReference, i, z);
                    if (z) {
                        return;
                    }
                    setSeverity(CompilerOptions.OPTION_ReportPotentialNullReference, 256, z);
                    setSeverity(CompilerOptions.OPTION_ReportRedundantNullCheck, 256, z);
                    return;
                }
                if (str.equals("nullAnnotConflict")) {
                    setSeverity(CompilerOptions.OPTION_ReportNullAnnotationInferenceConflict, i, z);
                    return;
                }
                if (str.equals("nullAnnotRedundant")) {
                    setSeverity(CompilerOptions.OPTION_ReportRedundantNullAnnotation, i, z);
                    return;
                }
                if (str.startsWith("nullAnnot")) {
                    String str7 = Util.EMPTY_STRING;
                    int iIndexOf7 = str.indexOf(40);
                    int iIndexOf8 = str.indexOf(41);
                    if (z && iIndexOf7 >= 0 && iIndexOf8 >= 0 && iIndexOf7 < iIndexOf8) {
                        boolean z2 = this.primaryNullAnnotationsSeen;
                        String strTrim = str.substring(iIndexOf7 + 1, iIndexOf8).trim();
                        int iIndexOf9 = strTrim.indexOf(124);
                        if (iIndexOf9 == -1) {
                            throw new IllegalArgumentException(bind("configure.invalidNullAnnot", str));
                        }
                        String strTrim2 = strTrim.substring(0, iIndexOf9).trim();
                        if (!z2 && strTrim2.length() == 0) {
                            throw new IllegalArgumentException(bind("configure.invalidNullAnnot", str));
                        }
                        int i2 = iIndexOf9 + 1;
                        int iIndexOf10 = strTrim.indexOf(124, i2);
                        if (iIndexOf10 == -1) {
                            throw new IllegalArgumentException(bind("configure.invalidNullAnnot", str));
                        }
                        String strTrim3 = strTrim.substring(i2, iIndexOf10).trim();
                        if (!z2 && strTrim3.length() == 0) {
                            throw new IllegalArgumentException(bind("configure.invalidNullAnnot", str));
                        }
                        String strTrim4 = strTrim.substring(iIndexOf10 + 1).trim();
                        if (!z2 && strTrim4.length() == 0) {
                            throw new IllegalArgumentException(bind("configure.invalidNullAnnot", str));
                        }
                        if (!z2) {
                            this.primaryNullAnnotationsSeen = true;
                            this.options.put(CompilerOptions.OPTION_NullableAnnotationName, strTrim2);
                            this.options.put(CompilerOptions.OPTION_NonNullAnnotationName, strTrim3);
                            this.options.put(CompilerOptions.OPTION_NonNullByDefaultAnnotationName, strTrim4);
                        } else {
                            if (strTrim2.length() > 0) {
                                String str8 = this.options.get(CompilerOptions.OPTION_NullableAnnotationSecondaryNames);
                                if (!str8.isEmpty()) {
                                    strTrim2 = String.valueOf(str8) + ',' + strTrim2;
                                }
                                this.options.put(CompilerOptions.OPTION_NullableAnnotationSecondaryNames, strTrim2);
                            }
                            if (strTrim3.length() > 0) {
                                String str9 = this.options.get(CompilerOptions.OPTION_NonNullAnnotationSecondaryNames);
                                if (!str9.isEmpty()) {
                                    strTrim3 = String.valueOf(str9) + ',' + strTrim3;
                                }
                                this.options.put(CompilerOptions.OPTION_NonNullAnnotationSecondaryNames, strTrim3);
                            }
                            if (strTrim4.length() > 0) {
                                String str10 = this.options.get(CompilerOptions.OPTION_NonNullByDefaultAnnotationSecondaryNames);
                                if (!str10.isEmpty()) {
                                    strTrim4 = String.valueOf(str10) + ',' + strTrim4;
                                }
                                this.options.put(CompilerOptions.OPTION_NonNullByDefaultAnnotationSecondaryNames, strTrim4);
                            }
                        }
                    }
                    Map<String, String> map16 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map16.put(CompilerOptions.OPTION_AnnotationBasedNullAnalysis, str2);
                    setSeverity(CompilerOptions.OPTION_ReportNullSpecViolation, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportNullAnnotationInferenceConflict, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportNullUncheckedConversion, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportRedundantNullAnnotation, i, z);
                    return;
                }
                if (str.equals("nullUncheckedConversion")) {
                    setSeverity(CompilerOptions.OPTION_ReportNullUncheckedConversion, i, z);
                    return;
                } else if (str.equals("nonnullNotRepeated")) {
                    setSeverity(CompilerOptions.OPTION_ReportNonnullParameterAnnotationDropped, i, z);
                    return;
                }
                break;
            case 'o':
                if (str.equals("over-sync")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingSynchronizedOnInheritedMethod, i, z);
                    return;
                } else if (str.equals("over-ann")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingOverrideAnnotation, i, z);
                    this.options.put(CompilerOptions.OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation, CompilerOptions.DISABLED);
                    return;
                }
            case 'p':
                if (str.equals("pkgDefaultMethod") || str.equals("packageDefaultMethod")) {
                    setSeverity(CompilerOptions.OPTION_ReportOverridingPackageDefaultMethod, i, z);
                    return;
                } else if (str.equals("paramAssign")) {
                    setSeverity(CompilerOptions.OPTION_ReportParameterAssignment, i, z);
                    return;
                }
            case 'r':
                if (str.equals(Options.TYPE_RAW)) {
                    setSeverity(CompilerOptions.OPTION_ReportRawTypeReference, i, z);
                    return;
                }
                if (str.equals("redundantSuperinterface")) {
                    setSeverity(CompilerOptions.OPTION_ReportRedundantSuperinterface, i, z);
                    return;
                } else if (str.equals("resource")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnclosedCloseable, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportPotentiallyUnclosedCloseable, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportExplicitlyClosedAutoCloseable, i, z);
                    return;
                }
                break;
            case 's':
                if (str.equals("specialParamHiding")) {
                    Map<String, String> map17 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map17.put(CompilerOptions.OPTION_ReportSpecialParameterHidingField, str2);
                    return;
                }
                if (str.equals("syntheticAccess") || str.equals("synthetic-access")) {
                    setSeverity(CompilerOptions.OPTION_ReportSyntheticAccessEmulation, i, z);
                    return;
                }
                if (str.equals("staticReceiver")) {
                    setSeverity(CompilerOptions.OPTION_ReportNonStaticAccessToStatic, i, z);
                    return;
                }
                if (str.equals("syncOverride")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingSynchronizedOnInheritedMethod, i, z);
                    return;
                }
                if (str.equals("semicolon")) {
                    setSeverity(CompilerOptions.OPTION_ReportEmptyStatement, i, z);
                    return;
                }
                if (str.equals("serial")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingSerialVersion, i, z);
                    return;
                }
                if (str.equals("suppress")) {
                    if (i == 0) {
                        Map<String, String> map18 = this.options;
                        if (!z) {
                            str2 = CompilerOptions.DISABLED;
                        }
                        map18.put(CompilerOptions.OPTION_SuppressWarnings, str2);
                        this.options.put(CompilerOptions.OPTION_SuppressOptionalErrors, CompilerOptions.DISABLED);
                        return;
                    }
                    if (i != 1) {
                        return;
                    }
                    this.options.put(CompilerOptions.OPTION_SuppressWarnings, z ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
                    Map<String, String> map19 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map19.put(CompilerOptions.OPTION_SuppressOptionalErrors, str2);
                    return;
                }
                if (str.equals("static-access")) {
                    setSeverity(CompilerOptions.OPTION_ReportNonStaticAccessToStatic, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportIndirectStaticAccess, i, z);
                    return;
                }
                if (str.equals(PsiKeyword.SUPER)) {
                    setSeverity(CompilerOptions.OPTION_ReportOverridingMethodWithoutSuperInvocation, i, z);
                    return;
                }
                if (str.equals("static-method")) {
                    setSeverity(CompilerOptions.OPTION_ReportMethodCanBeStatic, i, z);
                    return;
                }
                if (str.equals("switchDefault")) {
                    setSeverity(CompilerOptions.OPTION_ReportMissingDefaultCase, i, z);
                    return;
                } else if (str.equals("syntacticAnalysis")) {
                    Map<String, String> map20 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map20.put(CompilerOptions.OPTION_SyntacticNullAnalysisForFields, str2);
                    return;
                }
                break;
            case 't':
                if (str.startsWith("tasks")) {
                    String strReplace = Util.EMPTY_STRING;
                    int iIndexOf11 = str.indexOf(40);
                    int iIndexOf12 = str.indexOf(41);
                    if (iIndexOf11 >= 0 && iIndexOf12 >= 0 && iIndexOf11 < iIndexOf12) {
                        strReplace = str.substring(iIndexOf11 + 1, iIndexOf12).trim().replace('|', ',');
                    }
                    if (strReplace.length() == 0) {
                        throw new IllegalArgumentException(bind("configure.invalidTaskTag", str));
                    }
                    Map<String, String> map21 = this.options;
                    if (!z) {
                        strReplace = Util.EMPTY_STRING;
                    }
                    map21.put(CompilerOptions.OPTION_TaskTags, strReplace);
                    setSeverity(CompilerOptions.OPTION_ReportTasks, i, z);
                    return;
                }
                if (str.equals("typeHiding")) {
                    setSeverity(CompilerOptions.OPTION_ReportTypeParameterHiding, i, z);
                    return;
                }
                break;
            case 'u':
                if (str.equals("unusedLocal") || str.equals("unusedLocals")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedLocal, i, z);
                    return;
                }
                if (str.equals("unusedArgument") || str.equals("unusedArguments")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedParameter, i, z);
                    return;
                }
                if (str.equals("unusedExceptionParam")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedExceptionParameter, i, z);
                    return;
                }
                if (str.equals("unusedImport") || str.equals("unusedImports")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedImport, i, z);
                    return;
                }
                if (str.equals("unusedAllocation")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedObjectAllocation, i, z);
                    return;
                }
                if (str.equals("unusedPrivate")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedPrivateMember, i, z);
                    return;
                }
                if (str.equals("unusedLabel")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedLabel, i, z);
                    return;
                }
                if (str.equals("uselessTypeCheck")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnnecessaryTypeCheck, i, z);
                    return;
                }
                if (str.equals("unchecked") || str.equals("unsafe")) {
                    setSeverity(CompilerOptions.OPTION_ReportUncheckedTypeOperation, i, z);
                    return;
                }
                if (str.equals("unnecessaryElse")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnnecessaryElse, i, z);
                    return;
                }
                if (str.equals("unusedThrown")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedDeclaredThrownException, i, z);
                    return;
                }
                if (str.equals("unusedThrownWhenOverriding")) {
                    Map<String, String> map22 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map22.put(CompilerOptions.OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding, str2);
                    return;
                }
                if (str.equals("unusedThrownIncludeDocComment")) {
                    Map<String, String> map23 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map23.put(CompilerOptions.OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference, str2);
                    return;
                }
                if (str.equals("unusedThrownExemptExceptionThrowable")) {
                    Map<String, String> map24 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map24.put(CompilerOptions.OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable, str2);
                    return;
                }
                if (str.equals("unqualifiedField") || str.equals("unqualified-field-access")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnqualifiedFieldAccess, i, z);
                    return;
                }
                if (str.equals("unused")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedLocal, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedParameter, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedImport, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedPrivateMember, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedDeclaredThrownException, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedLabel, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedTypeArgumentsForMethodInvocation, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportRedundantSpecificationOfTypeArguments, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedTypeParameter, i, z);
                    return;
                }
                if (str.equals("unusedParam")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedParameter, i, z);
                    return;
                }
                if (str.equals("unusedTypeParameter")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedTypeParameter, i, z);
                    return;
                }
                if (str.equals("unusedParamIncludeDoc")) {
                    Map<String, String> map25 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map25.put(CompilerOptions.OPTION_ReportUnusedParameterIncludeDocCommentReference, str2);
                    return;
                }
                if (str.equals("unusedParamOverriding")) {
                    Map<String, String> map26 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map26.put(CompilerOptions.OPTION_ReportUnusedParameterWhenOverridingConcrete, str2);
                    return;
                }
                if (str.equals("unusedParamImplementing")) {
                    Map<String, String> map27 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map27.put(CompilerOptions.OPTION_ReportUnusedParameterWhenImplementingAbstract, str2);
                    return;
                }
                if (str.equals("unusedTypeArgs")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnusedTypeArgumentsForMethodInvocation, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportRedundantSpecificationOfTypeArguments, i, z);
                    return;
                } else if (str.equals("unavoidableGenericProblems")) {
                    Map<String, String> map28 = this.options;
                    if (!z) {
                        str2 = CompilerOptions.DISABLED;
                    }
                    map28.put(CompilerOptions.OPTION_ReportUnavoidableGenericTypeProblems, str2);
                    return;
                }
                break;
            case 'v':
                if (str.equals("varargsCast")) {
                    setSeverity(CompilerOptions.OPTION_ReportVarargsArgumentNeedCast, i, z);
                    return;
                }
                break;
            case 'w':
                if (str.equals("warningToken")) {
                    setSeverity(CompilerOptions.OPTION_ReportUnhandledWarningToken, i, z);
                    setSeverity(CompilerOptions.OPTION_ReportUnusedWarningToken, i, z);
                    return;
                }
                break;
        }
        if (i == 0) {
            strBind = bind("configure.invalidWarning", str);
        } else if (i == 1) {
            strBind = bind("configure.invalidError", str);
        }
        addPendingErrors(strBind);
    }

    protected void initialize(PrintWriter printWriter, PrintWriter printWriter2, boolean z) {
        initialize(printWriter, printWriter2, z, null, null);
    }

    protected void initialize(PrintWriter printWriter, PrintWriter printWriter2, boolean z, Map map) {
        initialize(printWriter, printWriter2, z, map, null);
    }

    protected void initialize(PrintWriter printWriter, PrintWriter printWriter2, boolean z, Map<String, String> map, CompilationProgress compilationProgress) {
        this.logger = new Logger(this, printWriter, printWriter2);
        this.proceed = true;
        this.out = printWriter;
        this.err = printWriter2;
        this.systemExitWhenFinished = z;
        this.options = new CompilerOptions().getMap();
        this.ignoreOptionalProblemsFromFolders = null;
        this.progress = compilationProgress;
        if (map != null) {
            this.didSpecifySource = map.get(CompilerOptions.OPTION_Source) != null;
            this.didSpecifyTarget = map.get(CompilerOptions.OPTION_TargetPlatform) != null;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.options.put(entry.getKey(), entry.getValue());
            }
        } else {
            this.didSpecifySource = false;
            this.didSpecifyTarget = false;
        }
        this.classNames = null;
    }

    protected void initializeAnnotationProcessorManager() {
        try {
            AbstractAnnotationProcessorManager abstractAnnotationProcessorManager = (AbstractAnnotationProcessorManager) Class.forName("org.eclipse.jdt.internal.compiler.apt.dispatch.BatchAnnotationProcessorManager").newInstance();
            abstractAnnotationProcessorManager.configure(this, this.expandedCommandLine);
            abstractAnnotationProcessorManager.setErr(this.err);
            abstractAnnotationProcessorManager.setOut(this.out);
            this.batchCompiler.annotationProcessorManager = abstractAnnotationProcessorManager;
        } catch (ClassNotFoundException | InstantiationException unused) {
            this.logger.logUnavaibleAPT("org.eclipse.jdt.internal.compiler.apt.dispatch.BatchAnnotationProcessorManager");
            throw new AbortCompilation();
        } catch (IllegalAccessException unused2) {
            throw new AbortCompilation();
        } catch (UnsupportedClassVersionError unused3) {
            this.logger.logIncorrectVMVersionForAnnotationProcessing();
        }
    }

    private static boolean isParentOf(char[] cArr, char[] cArr2) {
        if (cArr.length >= cArr2.length) {
            return false;
        }
        if (cArr2[cArr.length] != '\\' && cArr2[cArr.length] != '/') {
            return false;
        }
        for (int length = cArr.length - 1; length >= 0; length--) {
            if (cArr[length] != cArr2[length]) {
                return false;
            }
        }
        return true;
    }

    public void outputClassFiles(CompilationResult compilationResult) {
        String strExtractDestinationPathFromSourceFile;
        boolean z;
        if (compilationResult != null) {
            if (!compilationResult.hasErrors() || this.proceedOnError) {
                ClassFile[] classFiles = compilationResult.getClassFiles();
                CompilationUnit compilationUnit = (CompilationUnit) compilationResult.compilationUnit;
                if (compilationUnit.destinationPath == null) {
                    strExtractDestinationPathFromSourceFile = this.destinationPath;
                    if (strExtractDestinationPathFromSourceFile == null) {
                        strExtractDestinationPathFromSourceFile = extractDestinationPathFromSourceFile(compilationResult);
                    } else if (strExtractDestinationPathFromSourceFile != "none") {
                        z = true;
                    } else {
                        strExtractDestinationPathFromSourceFile = null;
                    }
                    z = false;
                } else {
                    if (compilationUnit.destinationPath != "none") {
                        strExtractDestinationPathFromSourceFile = compilationUnit.destinationPath;
                        z = true;
                    }
                    strExtractDestinationPathFromSourceFile = null;
                    z = false;
                }
                if (strExtractDestinationPathFromSourceFile != null) {
                    for (ClassFile classFile : classFiles) {
                        char[] cArrFileName = classFile.fileName();
                        int length = cArrFileName.length;
                        char[] cArr = new char[length + 6];
                        System.arraycopy(cArrFileName, 0, cArr, 0, length);
                        System.arraycopy(SuffixConstants.SUFFIX_class, 0, cArr, length, 6);
                        CharOperation.replace(cArr, '/', File.separatorChar);
                        String str = new String(cArr);
                        try {
                            if (this.compilerOptions.verbose) {
                                this.out.println(Messages.bind(Messages.compilation_write, (Object[]) new String[]{String.valueOf(this.exportedClassFilesCounter + 1), str}));
                            }
                            Util.writeToDisk(z, strExtractDestinationPathFromSourceFile, str, classFile);
                            this.logger.logClassFile(z, strExtractDestinationPathFromSourceFile, str);
                            this.exportedClassFilesCounter++;
                        } catch (IOException e) {
                            this.logger.logNoClassFileCreated(strExtractDestinationPathFromSourceFile, str, e);
                        }
                    }
                    this.batchCompiler.lookupEnvironment.releaseClassFiles(classFiles);
                }
            }
        }
    }

    public void performCompilation() throws Throwable {
        this.startTime = System.currentTimeMillis();
        FileSystem libraryAccess = getLibraryAccess();
        CompilerOptions compilerOptions = new CompilerOptions(this.options);
        this.compilerOptions = compilerOptions;
        boolean z = false;
        compilerOptions.performMethodsFullRecovery = false;
        this.compilerOptions.performStatementsRecovery = false;
        Compiler compiler = new Compiler(libraryAccess, getHandlingPolicy(), this.compilerOptions, getBatchRequestor(), getProblemFactory(), this.out, this.progress);
        this.batchCompiler = compiler;
        compiler.remainingIterations = this.maxRepetition - this.currentRepetition;
        String property = System.getProperty("jdt.compiler.useSingleThread");
        Compiler compiler2 = this.batchCompiler;
        if (property != null && property.equals("true")) {
            z = true;
        }
        compiler2.useSingleThread = z;
        if (this.compilerOptions.complianceLevel >= ClassFileConstants.JDK1_6 && this.compilerOptions.processAnnotations) {
            if (checkVMVersion(ClassFileConstants.JDK1_6)) {
                initializeAnnotationProcessorManager();
                if (this.classNames != null) {
                    Compiler compiler3 = this.batchCompiler;
                    compiler3.setBinaryTypes(processClassNames(compiler3.lookupEnvironment));
                }
            } else {
                this.logger.logIncorrectVMVersionForAnnotationProcessing();
            }
        }
        this.compilerOptions.verbose = this.verbose;
        this.compilerOptions.produceReferenceInfo = this.produceRefInfo;
        try {
            this.logger.startLoggingSources();
            this.batchCompiler.compile(getCompilationUnits());
            this.logger.endLoggingSources();
            if (this.extraProblems != null) {
                loggingExtraProblems();
                this.extraProblems = null;
            }
            CompilerStats[] compilerStatsArr = this.compilerStats;
            if (compilerStatsArr != null) {
                compilerStatsArr[this.currentRepetition] = this.batchCompiler.stats;
            }
            this.logger.printStats();
            libraryAccess.cleanup();
        } catch (Throwable th) {
            this.logger.endLoggingSources();
            throw th;
        }
    }

    protected void loggingExtraProblems() throws Throwable {
        this.logger.loggingExtraProblems(this);
    }

    public void printUsage() {
        printUsage("misc.usage");
    }

    private void printUsage(String str) {
        this.logger.logUsage(bind(str, new String[]{System.getProperty(SystemProperties.PATH_SEPARATOR), bind("compiler.name"), bind("compiler.version"), bind("compiler.copyright")}));
        this.logger.flush();
    }

    private ReferenceBinding[] processClassNames(LookupEnvironment lookupEnvironment) {
        char[][] cArrSplitOn;
        int length = this.classNames.length;
        ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length];
        for (int i = 0; i < length; i++) {
            String str = this.classNames[i];
            if (str.indexOf(46) != -1) {
                cArrSplitOn = CharOperation.splitOn('.', str.toCharArray());
            } else {
                cArrSplitOn = new char[][]{str.toCharArray()};
            }
            ReferenceBinding type = lookupEnvironment.getType(cArrSplitOn);
            if (type != null && type.isValidBinding()) {
                if (type.isBinaryBinding()) {
                    referenceBindingArr[i] = type;
                }
            } else {
                throw new IllegalArgumentException(bind("configure.invalidClassName", str));
            }
        }
        return referenceBindingArr;
    }

    public void processPathEntries(int i, ArrayList arrayList, String str, String str2, boolean z, boolean z2) {
        char c;
        char c2;
        int i2;
        char c3;
        ArrayList arrayList2 = new ArrayList(i);
        StringTokenizer stringTokenizer = new StringTokenizer(str, String.valueOf(File.pathSeparator) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, true);
        ArrayList arrayList3 = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList3.add(stringTokenizer.nextToken());
        }
        int size = arrayList3.size();
        int i3 = 0;
        String str3 = null;
        String strTrim = null;
        int i4 = -1;
        char c4 = 0;
        while (i3 < size && c4 != 'c') {
            int i5 = i3 + 1;
            String str4 = (String) arrayList3.get(i3);
            if (str4.equals(File.pathSeparator)) {
                if (c4 == 0) {
                    i3 = i5;
                    c = 11;
                } else {
                    if (c4 == 1 || c4 == 2) {
                        c3 = 3;
                    } else {
                        c3 = 3;
                        if (c4 != 3) {
                            if (c4 == 6) {
                                i3 = i5;
                                c = 11;
                                c4 = 4;
                            } else {
                                if (c4 == 7) {
                                    throw new IllegalArgumentException(bind("configure.incorrectDestinationPathEntry", str));
                                }
                                if (c4 != '\b') {
                                    if (c4 != '\n') {
                                        if (c4 != 11) {
                                            i3 = i5;
                                            c = 11;
                                            c4 = 'c';
                                        } else {
                                            i3 = i4 + 1;
                                            c = 11;
                                            c4 = 5;
                                        }
                                    }
                                }
                                c = 11;
                            }
                        }
                        i3 = i5;
                        c = 11;
                    }
                    addNewEntry(arrayList, str3, arrayList2, str2, strTrim, z, z2);
                    arrayList2.clear();
                    i3 = i5;
                    c4 = 3;
                    c = 11;
                }
                c2 = 5;
            } else if (str4.equals("[")) {
                if (c4 == 0) {
                    c = 11;
                    str3 = "";
                } else if (c4 == 1) {
                    c = 11;
                } else if (c4 == 2) {
                    c = 11;
                    c4 = '\t';
                    i3 = i5;
                    c2 = 5;
                } else if (c4 != '\b') {
                    c = 11;
                    if (c4 == 11) {
                        i3 = i4;
                    }
                    i3 = i5;
                    c4 = 'c';
                    c2 = 5;
                } else {
                    c = 11;
                    i3 = i5;
                    c4 = 5;
                    c2 = 5;
                }
                i4 = i3;
                i3 = i5;
                c4 = '\n';
                c2 = 5;
            } else {
                c = 11;
                if (str4.equals("]")) {
                    if (c4 != 6) {
                        if (c4 != 7) {
                            if (c4 == '\n') {
                                c4 = 11;
                            }
                            i3 = i5;
                            c4 = 'c';
                        } else {
                            i3 = i5;
                            c4 = '\b';
                        }
                        c2 = 5;
                    } else {
                        c4 = 2;
                    }
                    i3 = i5;
                    c2 = 5;
                } else {
                    if (c4 == 0 || c4 == 3) {
                        c2 = 5;
                        i3 = i5;
                        str3 = str4;
                    } else {
                        if (c4 != 4) {
                            c2 = 5;
                            if (c4 == 5) {
                                i2 = i5;
                                if (str4.startsWith("-d ")) {
                                    if (strTrim != null) {
                                        throw new IllegalArgumentException(bind("configure.duplicateDestinationPathEntry", str));
                                    }
                                    strTrim = str4.substring(3).trim();
                                    i3 = i2;
                                }
                            } else {
                                switch (c4) {
                                    case '\t':
                                        i3 = i5;
                                        if (str4.startsWith("-d ")) {
                                            strTrim = str4.substring(3).trim();
                                        } else {
                                            c4 = 'c';
                                        }
                                        break;
                                    case '\n':
                                        i3 = i5;
                                        break;
                                    case 11:
                                        i3 = i5;
                                        for (int i6 = i4; i6 < i3; i6++) {
                                            str3 = String.valueOf(str3) + ((String) arrayList3.get(i6));
                                        }
                                        break;
                                    default:
                                        i3 = i5;
                                        c4 = 'c';
                                        break;
                                }
                            }
                            c4 = 7;
                        } else {
                            i2 = i5;
                            c2 = 5;
                        }
                        if (strTrim != null) {
                            throw new IllegalArgumentException(bind("configure.accessRuleAfterDestinationPath", str));
                        }
                        arrayList2.add(str4);
                        int i7 = i2;
                        c4 = 6;
                        i3 = i7;
                    }
                    c4 = 1;
                }
            }
            if (c4 == c && i3 == size) {
                i3 = i4 + 1;
                c4 = c2;
            }
        }
        if (c4 != 1 && c4 != 2) {
            if (c4 == 3) {
                return;
            }
            if (c4 != '\b') {
                if (str.length() != 0) {
                    addPendingErrors(bind("configure.incorrectClasspath", str));
                    return;
                }
                return;
            }
        }
        addNewEntry(arrayList, str3, arrayList2, str2, strTrim, z, z2);
    }

    private int processPaths(String[] strArr, int i, String str, ArrayList arrayList) {
        int length = str.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '[') {
                i2++;
            } else if (cCharAt == ']') {
                i2--;
            }
        }
        if (i2 == 0) {
            arrayList.add(str);
            return 0;
        }
        if (i2 > 1) {
            throw new IllegalArgumentException(bind("configure.unexpectedBracket", str));
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        int i4 = i;
        while (i4 < strArr.length) {
            i4++;
            String str2 = strArr[i4];
            int length2 = str2.length();
            for (int i5 = 0; i5 < length2; i5++) {
                char cCharAt2 = str2.charAt(i5);
                if (cCharAt2 == '[') {
                    if (i2 > 1) {
                        throw new IllegalArgumentException(bind("configure.unexpectedBracket", str2));
                    }
                    i2++;
                } else if (cCharAt2 == ']') {
                    i2--;
                }
            }
            if (i2 == 0) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(str2);
                arrayList.add(stringBuffer.toString());
                return i4 - i;
            }
            if (i2 < 0) {
                throw new IllegalArgumentException(bind("configure.unexpectedBracket", str2));
            }
            stringBuffer.append(StringUtil.SPACE);
            stringBuffer.append(str2);
        }
        throw new IllegalArgumentException(bind("configure.unexpectedBracket", str));
    }

    private int processPaths(String[] strArr, int i, String str, String[] strArr2) {
        int length = str.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '[') {
                i2++;
            } else if (cCharAt == ']') {
                i2--;
            }
        }
        if (i2 == 0) {
            strArr2[0] = str;
            return 0;
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        int i4 = i;
        while (true) {
            i4++;
            if (i4 >= strArr.length) {
                throw new IllegalArgumentException(bind("configure.unexpectedBracket", str));
            }
            String str2 = strArr[i4];
            int length2 = str2.length();
            for (int i5 = 0; i5 < length2; i5++) {
                char cCharAt2 = str2.charAt(i5);
                if (cCharAt2 == '[') {
                    if (i2 > 1) {
                        throw new IllegalArgumentException(bind("configure.unexpectedBracket", str));
                    }
                    i2++;
                } else if (cCharAt2 == ']') {
                    i2--;
                }
            }
            if (i2 == 0) {
                stringBuffer.append(StringUtil.SPACE);
                stringBuffer.append(str2);
                strArr2[0] = stringBuffer.toString();
                return i4 - i;
            }
            if (i2 < 0) {
                throw new IllegalArgumentException(bind("configure.unexpectedBracket", str));
            }
            stringBuffer.append(StringUtil.SPACE);
            stringBuffer.append(str2);
        }
    }

    public void relocalize() {
        relocalize(Locale.getDefault());
    }

    private void relocalize(Locale locale) {
        this.compilerLocale = locale;
        try {
            this.bundle = ResourceBundleFactory.getBundle(locale);
        } catch (MissingResourceException e) {
            System.out.println("Missing resource : " + bundleName.replace('.', '/') + ".properties for locale " + locale);
            throw e;
        }
    }

    public void setDestinationPath(String str) {
        this.destinationPath = str;
    }

    public void setLocale(Locale locale) {
        relocalize(locale);
    }

    protected void setPaths(ArrayList arrayList, String str, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, String str2) {
        ArrayList arrayListHandleBootclasspath = handleBootclasspath(arrayList, str2);
        ArrayList arrayListHandleClasspath = handleClasspath(arrayList3, str2);
        if (str != null) {
            processPathEntries(4, arrayList2, str, str2, true, false);
        }
        ArrayList arrayListHandleExtdirs = handleExtdirs(arrayList4);
        ArrayList arrayListHandleEndorseddirs = handleEndorseddirs(arrayList5);
        arrayListHandleBootclasspath.addAll(0, arrayListHandleEndorseddirs);
        arrayListHandleBootclasspath.addAll(arrayListHandleExtdirs);
        arrayListHandleBootclasspath.addAll(arrayList2);
        arrayListHandleBootclasspath.addAll(arrayListHandleClasspath);
        ArrayList arrayListNormalize = FileSystem.ClasspathNormalizer.normalize(arrayListHandleBootclasspath);
        FileSystem.Classpath[] classpathArr = new FileSystem.Classpath[arrayListNormalize.size()];
        this.checkedClasspaths = classpathArr;
        arrayListNormalize.toArray(classpathArr);
        this.logger.logClasspath(this.checkedClasspaths);
        if (this.annotationPaths == null || !CompilerOptions.ENABLED.equals(this.options.get(CompilerOptions.OPTION_AnnotationBasedNullAnalysis))) {
            return;
        }
        for (FileSystem.Classpath classpath : this.checkedClasspaths) {
            if (classpath instanceof ClasspathJar) {
                ((ClasspathJar) classpath).annotationPaths = this.annotationPaths;
            }
        }
    }

    private static boolean shouldIgnoreOptionalProblems(char[][] cArr, char[] cArr2) {
        if (cArr == null || cArr2 == null) {
            return false;
        }
        for (char[] cArr3 : cArr) {
            if (isParentOf(cArr3, cArr2)) {
                return true;
            }
        }
        return false;
    }

    protected void validateOptions(boolean z) {
        if (z) {
            String str = this.options.get(CompilerOptions.OPTION_Compliance);
            if (CompilerOptions.VERSION_1_3.equals(str)) {
                if (!this.didSpecifySource) {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_3);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_1);
                }
            } else if (CompilerOptions.VERSION_1_4.equals(str)) {
                if (!this.didSpecifySource) {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_3);
                    if (!this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_2);
                    }
                } else {
                    String str2 = this.options.get(CompilerOptions.OPTION_Source);
                    if (CompilerOptions.VERSION_1_3.equals(str2)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_2);
                        }
                    } else if (CompilerOptions.VERSION_1_4.equals(str2) && !this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                    }
                }
            } else if (CompilerOptions.VERSION_1_5.equals(str)) {
                if (this.didSpecifySource) {
                    String str3 = this.options.get(CompilerOptions.OPTION_Source);
                    if (CompilerOptions.VERSION_1_3.equals(str3) || CompilerOptions.VERSION_1_4.equals(str3)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                        }
                    } else if (CompilerOptions.VERSION_1_5.equals(str3) && !this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_5);
                    }
                } else {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_5);
                    if (!this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_5);
                    }
                }
            } else if (CompilerOptions.VERSION_1_6.equals(str)) {
                if (this.didSpecifySource) {
                    String str4 = this.options.get(CompilerOptions.OPTION_Source);
                    if (CompilerOptions.VERSION_1_3.equals(str4) || CompilerOptions.VERSION_1_4.equals(str4)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                        }
                    } else if ((CompilerOptions.VERSION_1_5.equals(str4) || CompilerOptions.VERSION_1_6.equals(str4)) && !this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                    }
                } else {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_6);
                    if (!this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                    }
                }
            } else if (CompilerOptions.VERSION_1_7.equals(str)) {
                if (this.didSpecifySource) {
                    String str5 = this.options.get(CompilerOptions.OPTION_Source);
                    if (CompilerOptions.VERSION_1_3.equals(str5) || CompilerOptions.VERSION_1_4.equals(str5)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                        }
                    } else if (CompilerOptions.VERSION_1_5.equals(str5) || CompilerOptions.VERSION_1_6.equals(str5)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                        }
                    } else if (CompilerOptions.VERSION_1_7.equals(str5) && !this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_7);
                    }
                } else {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_7);
                    if (!this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_7);
                    }
                }
            } else if (CompilerOptions.VERSION_1_8.equals(str)) {
                if (!this.didSpecifySource) {
                    this.options.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_8);
                    if (!this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_8);
                    }
                } else {
                    String str6 = this.options.get(CompilerOptions.OPTION_Source);
                    if (CompilerOptions.VERSION_1_3.equals(str6) || CompilerOptions.VERSION_1_4.equals(str6)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                        }
                    } else if (CompilerOptions.VERSION_1_5.equals(str6) || CompilerOptions.VERSION_1_6.equals(str6)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                        }
                    } else if (CompilerOptions.VERSION_1_7.equals(str6)) {
                        if (!this.didSpecifyTarget) {
                            this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_7);
                        }
                    } else if (CompilerOptions.VERSION_1_8.equals(str6) && !this.didSpecifyTarget) {
                        this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_8);
                    }
                }
            }
        } else if (this.didSpecifySource) {
            String str7 = this.options.get(CompilerOptions.OPTION_Source);
            if (CompilerOptions.VERSION_1_4.equals(str7)) {
                if (!z) {
                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_4);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_4);
                }
            } else if (CompilerOptions.VERSION_1_5.equals(str7)) {
                if (!z) {
                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_5);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_5);
                }
            } else if (CompilerOptions.VERSION_1_6.equals(str7)) {
                if (!z) {
                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_6);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_6);
                }
            } else if (CompilerOptions.VERSION_1_7.equals(str7)) {
                if (!z) {
                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_7);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_7);
                }
            } else if (CompilerOptions.VERSION_1_8.equals(str7)) {
                if (!z) {
                    this.options.put(CompilerOptions.OPTION_Compliance, CompilerOptions.VERSION_1_8);
                }
                if (!this.didSpecifyTarget) {
                    this.options.put(CompilerOptions.OPTION_TargetPlatform, CompilerOptions.VERSION_1_8);
                }
            }
        }
        String str8 = this.options.get(CompilerOptions.OPTION_Source);
        String str9 = this.options.get(CompilerOptions.OPTION_Compliance);
        if (str8.equals(CompilerOptions.VERSION_1_8) && CompilerOptions.versionToJdkLevel(str9) < ClassFileConstants.JDK1_8) {
            throw new IllegalArgumentException(bind("configure.incompatibleComplianceForSource", this.options.get(CompilerOptions.OPTION_Compliance), CompilerOptions.VERSION_1_8));
        }
        if (str8.equals(CompilerOptions.VERSION_1_7) && CompilerOptions.versionToJdkLevel(str9) < ClassFileConstants.JDK1_7) {
            throw new IllegalArgumentException(bind("configure.incompatibleComplianceForSource", this.options.get(CompilerOptions.OPTION_Compliance), CompilerOptions.VERSION_1_7));
        }
        if (str8.equals(CompilerOptions.VERSION_1_6) && CompilerOptions.versionToJdkLevel(str9) < ClassFileConstants.JDK1_6) {
            throw new IllegalArgumentException(bind("configure.incompatibleComplianceForSource", this.options.get(CompilerOptions.OPTION_Compliance), CompilerOptions.VERSION_1_6));
        }
        if (str8.equals(CompilerOptions.VERSION_1_5) && CompilerOptions.versionToJdkLevel(str9) < ClassFileConstants.JDK1_5) {
            throw new IllegalArgumentException(bind("configure.incompatibleComplianceForSource", this.options.get(CompilerOptions.OPTION_Compliance), CompilerOptions.VERSION_1_5));
        }
        if (str8.equals(CompilerOptions.VERSION_1_4) && CompilerOptions.versionToJdkLevel(str9) < ClassFileConstants.JDK1_4) {
            throw new IllegalArgumentException(bind("configure.incompatibleComplianceForSource", this.options.get(CompilerOptions.OPTION_Compliance), CompilerOptions.VERSION_1_4));
        }
        if (this.didSpecifyTarget) {
            String str10 = this.options.get(CompilerOptions.OPTION_TargetPlatform);
            if (CompilerOptions.VERSION_JSR14.equals(str10)) {
                if (CompilerOptions.versionToJdkLevel(str8) < ClassFileConstants.JDK1_5) {
                    throw new IllegalArgumentException(bind("configure.incompatibleTargetForGenericSource", str10, str8));
                }
                return;
            }
            if (CompilerOptions.VERSION_CLDC1_1.equals(str10)) {
                if (this.didSpecifySource && CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_4) {
                    throw new IllegalArgumentException(bind("configure.incompatibleSourceForCldcTarget", str10, str8));
                }
                if (CompilerOptions.versionToJdkLevel(str9) >= ClassFileConstants.JDK1_5) {
                    throw new IllegalArgumentException(bind("configure.incompatibleComplianceForCldcTarget", str10, str8));
                }
                return;
            }
            if (CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_8 && CompilerOptions.versionToJdkLevel(str10) < ClassFileConstants.JDK1_8) {
                throw new IllegalArgumentException(bind("configure.incompatibleTargetForSource", str10, CompilerOptions.VERSION_1_8));
            }
            if (CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_7 && CompilerOptions.versionToJdkLevel(str10) < ClassFileConstants.JDK1_7) {
                throw new IllegalArgumentException(bind("configure.incompatibleTargetForSource", str10, CompilerOptions.VERSION_1_7));
            }
            if (CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_6 && CompilerOptions.versionToJdkLevel(str10) < ClassFileConstants.JDK1_6) {
                throw new IllegalArgumentException(bind("configure.incompatibleTargetForSource", str10, CompilerOptions.VERSION_1_6));
            }
            if (CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_5 && CompilerOptions.versionToJdkLevel(str10) < ClassFileConstants.JDK1_5) {
                throw new IllegalArgumentException(bind("configure.incompatibleTargetForSource", str10, CompilerOptions.VERSION_1_5));
            }
            if (CompilerOptions.versionToJdkLevel(str8) >= ClassFileConstants.JDK1_4 && CompilerOptions.versionToJdkLevel(str10) < ClassFileConstants.JDK1_4) {
                throw new IllegalArgumentException(bind("configure.incompatibleTargetForSource", str10, CompilerOptions.VERSION_1_4));
            }
            if (CompilerOptions.versionToJdkLevel(str9) < CompilerOptions.versionToJdkLevel(str10)) {
                throw new IllegalArgumentException(bind("configure.incompatibleComplianceForTarget", this.options.get(CompilerOptions.OPTION_Compliance), str10));
            }
        }
    }
}
