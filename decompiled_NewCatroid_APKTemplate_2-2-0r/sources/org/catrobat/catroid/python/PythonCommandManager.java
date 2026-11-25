package org.catrobat.catroid.python;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.utils.git.TokenManager;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonCommandManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: PythonCommandManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b \u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0019\u001a\u00020\t2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\"\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\t2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u001d0 J\u0016\u0010!\u001a\u00020\u001d2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010#\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010$\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010%\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010&\u001a\u00020\u001dH\u0002J\b\u0010'\u001a\u00020\u001dH\u0002J\u0016\u0010(\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010)\u001a\u00020\u001dH\u0002J\b\u0010*\u001a\u00020\u001dH\u0002J\b\u0010+\u001a\u00020\u001dH\u0002J\u0016\u0010,\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010-\u001a\u00020\u001dH\u0002J\u0010\u0010.\u001a\u00020\u001d2\u0006\u0010/\u001a\u00020\tH\u0002J\n\u00100\u001a\u0004\u0018\u000101H\u0002J\u0010\u00102\u001a\u00020\u000b2\u0006\u00103\u001a\u00020\tH\u0002J\n\u00104\u001a\u0004\u0018\u000105H\u0002J\u0016\u00106\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u00107\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u00108\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u00109\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0010\u0010:\u001a\u00020\u001d2\u0006\u0010;\u001a\u00020\tH\u0002J\u0016\u0010<\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010=\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010>\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010?\u001a\u00020\u001dH\u0002J\u0016\u0010@\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010A\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010B\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010C\u001a\u00020\u001dH\u0002J\u0016\u0010D\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010E\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010F\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010G\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010H\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010I\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u001e\u0010J\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001b2\u0006\u0010;\u001a\u00020\tH\u0002J\b\u0010K\u001a\u00020\u001dH\u0002J\u0016\u0010L\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010M\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010N\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\u0016\u0010O\u001a\u00020\u001d2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\u001bH\u0002J\b\u0010P\u001a\u00020\u001dH\u0002J\u0010\u0010Q\u001a\u00020\u001d2\u0006\u0010R\u001a\u00020\tH\u0002J\u0010\u0010S\u001a\u00020\u001d2\u0006\u0010R\u001a\u00020\tH\u0002J\u000e\u0010T\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\tR\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006U"}, d2 = {"Lorg/catrobat/catroid/python/PythonCommandManager;", "", "pythonEngine", "Lorg/catrobat/catroid/python/PythonEngine;", "project", "Lorg/catrobat/catroid/content/Project;", "(Lorg/catrobat/catroid/python/PythonEngine;Lorg/catrobat/catroid/content/Project;)V", "aliases", "", "", "currentWorkingDirectory", "Ljava/io/File;", "getCurrentWorkingDirectory", "()Ljava/io/File;", "setCurrentWorkingDirectory", "(Ljava/io/File;)V", "defaultLibsPath", "mainThreadHandler", "Landroid/os/Handler;", "outputListener", "Lorg/catrobat/catroid/python/CommandOutputListener;", "getOutputListener", "()Lorg/catrobat/catroid/python/CommandOutputListener;", "setOutputListener", "(Lorg/catrobat/catroid/python/CommandOutputListener;)V", "buildPipScript", "args", "", "executeCommandForResult", "", "commandLine", "onResult", "Lkotlin/Function1;", "executeCommandSequence", "commands", "executeGitAdd", "executeGitClone", "executeGitCommit", "executeGitInit", "executeGitLog", "executeGitLogin", "executeGitLogout", "executeGitPull", "executeGitPush", "executeGitRemote", "executeGitStatus", "executePythonScript", "script", "getCredentialsProvider", "Lorg/eclipse/jgit/transport/UsernamePasswordCredentialsProvider;", "getFile", "path", "getGitInstance", "Lorg/eclipse/jgit/api/Git;", "handleAlias", "handleCat", "handleCd", "handleCp", "handleEcho", "fullCommand", "handleFind", "handleGit", "handleGrep", "handleHelp", "handleHexdump", "handleLogcat", "handleLs", "handleMemoryInfo", "handleMkdir", "handleMlCommands", "handleMv", "handleNano", "handlePipCommand", "handleRm", "handleSh", "handleSysInfo", "handleTouch", "handleUnzip", "handleVmCommands", "handleWget", "printComplete", "printError", "text", "printOutput", "processCommand", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PythonCommandManager {
    private final Map<String, String> aliases;
    private File currentWorkingDirectory;
    private final String defaultLibsPath;
    private final Handler mainThreadHandler;
    private CommandOutputListener outputListener;
    private final Project project;
    private final PythonEngine pythonEngine;

    public PythonCommandManager(PythonEngine pythonEngine, Project project) {
        Intrinsics.checkNotNullParameter(pythonEngine, "pythonEngine");
        Intrinsics.checkNotNullParameter(project, "project");
        this.pythonEngine = pythonEngine;
        this.project = project;
        String absolutePath = project.getFilesDir().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        this.defaultLibsPath = absolutePath;
        File filesDir = project.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        this.currentWorkingDirectory = filesDir;
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
        this.aliases = new LinkedHashMap();
    }

    public final CommandOutputListener getOutputListener() {
        return this.outputListener;
    }

    public final void setOutputListener(CommandOutputListener commandOutputListener) {
        this.outputListener = commandOutputListener;
    }

    public final File getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    public final void setCurrentWorkingDirectory(File file) {
        Intrinsics.checkNotNullParameter(file, "<set-?>");
        this.currentWorkingDirectory = file;
    }

    public final void executeCommandForResult(String commandLine, final Function1<? super String, Unit> onResult) throws IOException {
        Intrinsics.checkNotNullParameter(commandLine, "commandLine");
        Intrinsics.checkNotNullParameter(onResult, "onResult");
        String string = StringsKt.trim((CharSequence) commandLine).toString();
        if (string.length() == 0) {
            this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.executeCommandForResult$lambda$0(onResult);
                }
            });
            return;
        }
        StringBuilder sb = new StringBuilder();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        booleanRef.element = true;
        this.outputListener = new AnonymousClass2(booleanRef, string, sb, this, onResult);
        processCommand(string);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeCommandForResult$lambda$0(Function1 onResult) {
        Intrinsics.checkNotNullParameter(onResult, "$onResult");
        onResult.invoke("");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonCommandManager$executeCommandForResult$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.python.PythonCommandManager$executeCommandForResult$2, reason: invalid class name */
    /* JADX INFO: compiled from: PythonCommandManager.kt */
    @Metadata(d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"org/catrobat/catroid/python/PythonCommandManager$executeCommandForResult$2", "Lorg/catrobat/catroid/python/CommandOutputListener;", "onComplete", "", "onOpenEditor", "file", "Ljava/io/File;", "onOutput", "output", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass2 implements CommandOutputListener {
        final /* synthetic */ Ref.BooleanRef $isFirstOutput;
        final /* synthetic */ Function1<String, Unit> $onResult;
        final /* synthetic */ StringBuilder $outputBuilder;
        final /* synthetic */ String $trimmedCommand;
        final /* synthetic */ PythonCommandManager this$0;

        /* JADX WARN: Multi-variable type inference failed */
        AnonymousClass2(Ref.BooleanRef booleanRef, String str, StringBuilder sb, PythonCommandManager pythonCommandManager, Function1<? super String, Unit> function1) {
            this.$isFirstOutput = booleanRef;
            this.$trimmedCommand = str;
            this.$outputBuilder = sb;
            this.this$0 = pythonCommandManager;
            this.$onResult = function1;
        }

        @Override // org.catrobat.catroid.python.CommandOutputListener
        public void onOutput(String output) {
            Intrinsics.checkNotNullParameter(output, "output");
            if (this.$isFirstOutput.element) {
                if (Intrinsics.areEqual(output, "\n> " + this.$trimmedCommand + "\n")) {
                    this.$isFirstOutput.element = false;
                    return;
                }
            }
            this.$outputBuilder.append(output);
        }

        @Override // org.catrobat.catroid.python.CommandOutputListener
        public void onComplete() {
            Handler handler = this.this$0.mainThreadHandler;
            final Function1<String, Unit> function1 = this.$onResult;
            final StringBuilder sb = this.$outputBuilder;
            handler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$executeCommandForResult$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.AnonymousClass2.onComplete$lambda$0(function1, sb);
                }
            });
            this.this$0.setOutputListener(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onComplete$lambda$0(Function1 onResult, StringBuilder outputBuilder) {
            Intrinsics.checkNotNullParameter(onResult, "$onResult");
            Intrinsics.checkNotNullParameter(outputBuilder, "$outputBuilder");
            String string = outputBuilder.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            onResult.invoke(StringsKt.trim((CharSequence) string).toString());
        }

        @Override // org.catrobat.catroid.python.CommandOutputListener
        public void onOpenEditor(File file) {
            Intrinsics.checkNotNullParameter(file, "file");
            Handler handler = this.this$0.mainThreadHandler;
            final Function1<String, Unit> function1 = this.$onResult;
            handler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$executeCommandForResult$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.AnonymousClass2.onOpenEditor$lambda$1(function1);
                }
            });
            this.this$0.setOutputListener(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onOpenEditor$lambda$1(Function1 onResult) {
            Intrinsics.checkNotNullParameter(onResult, "$onResult");
            onResult.invoke("Error: Interactive editor (nano) is not supported in block execution.");
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:176:0x0321  */
    /* JADX WARN: Code duplicated, block: B:177:0x032f  */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x024b, code lost:
    
        if (r3.equals("dir") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0265, code lost:
    
        if (r3.equals("vm") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0269, code lost:
    
        handleVmCommands(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0275, code lost:
    
        if (r3.equals(com.huawei.hms.ml.language.common.utils.LanguageCodeUtil.VI) == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0279, code lost:
    
        handleNano(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0285, code lost:
    
        if (r3.equals("sh") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x029e, code lost:
    
        if (r3.equals(com.huawei.hms.ml.language.common.utils.LanguageCodeUtil.PT) == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x02b7, code lost:
    
        if (r3.equals("ml") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02bb, code lost:
    
        handleMlCommands(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02c6, code lost:
    
        if (r3.equals("ls") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02c9, code lost:
    
        handleLs(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x02ec, code lost:
    
        if (r3.equals("./") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02ef, code lost:
    
        handleSh(r2, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x02f9, code lost:
    
        if (r3.equals(net.bytebuddy.description.type.TypeDescription.Generic.OfWildcardType.SYMBOL) == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x02fc, code lost:
    
        handleHelp();
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0314, code lost:
    
        if (r3.equals("sysinfo") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0317, code lost:
    
        handleSysInfo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f3, code lost:
    
        if (r3.equals("pocketensor") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00fd, code lost:
    
        if (r3.equals("neofetch") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0107, code lost:
    
        if (r3.equals("hexdump") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0122, code lost:
    
        if (r3.equals("uname") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0171, code lost:
    
        if (r3.equals("wget") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x017b, code lost:
    
        if (r3.equals("qemu") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0185, code lost:
    
        if (r3.equals("nano") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x018f, code lost:
    
        if (r3.equals("help") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01a8, code lost:
    
        if (r3.equals("free") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01c1, code lost:
    
        if (r3.equals("edit") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01da, code lost:
    
        if (r3.equals("curl") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01de, code lost:
    
        handleWget(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ea, code lost:
    
        if (r3.equals("xxd") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01ee, code lost:
    
        handleHexdump(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x01fa, code lost:
    
        if (r3.equals("top") == false) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x01fe, code lost:
    
        handleMemoryInfo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0209, code lost:
    
        if (r3.equals("run") == false) goto L174;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void processCommand(java.lang.String r20) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 986
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.python.PythonCommandManager.processCommand(java.lang.String):void");
    }

    private final void handleHelp() {
        printOutput("=== Terminal ===\n\n FILE SYSTEM:\n  ls, dir     - List directory contents\n  cd          - Change the current directory\n  pwd         - Print working directory\n  mkdir       - Create a directory\n  rm          - Remove files/directories (use -r for recursive)\n  cp, mv      - Copy or move files\n  touch       - Create an empty file\n  cat         - Print file content\n  echo        - Print text or write to file (>, >>)\n  find        - Find files by name\n  unzip       - Extract zip archives\n\n TEXT & SYSTEM:\n  nano, vi    - Open text editor\n  grep        - Search text in a file\n  xxd         - View file in HEX format\n  sh, ./      - Execute a shell script\n  alias       - Create aliases (e.g., alias ll=\"ls -l\")\n  wget, curl  - Download files\n  logcat      - View internal app logs\n  sysinfo     - Display device information\n  free        - Display memory usage\n  clear       - Clear python environment & terminal\n\n ML, VM & PYTHON:\n  pip         - Install Python packages\n\n GIT:\n  git         - Version control (init, clone, status, add, commit, log, pull, push, remote)\n\n NOTE: Any unknown command will be evaluated as a Python script");
    }

    private final UsernamePasswordCredentialsProvider getCredentialsProvider() {
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        String token = tokenManager.getToken(appContext);
        String str = token;
        if (str == null || str.length() == 0) {
            return null;
        }
        return new UsernamePasswordCredentialsProvider(token, "");
    }

    private final Git getGitInstance() {
        try {
            FileRepositoryBuilder fileRepositoryBuilderFindGitDir = new FileRepositoryBuilder().findGitDir(this.currentWorkingDirectory);
            if (fileRepositoryBuilderFindGitDir.getGitDir() == null) {
                return null;
            }
            return new Git(fileRepositoryBuilderFindGitDir.setMustExist(true).build());
        } catch (Exception unused) {
            return null;
        }
    }

    private final void handleGit(List<String> args) {
        if (args.isEmpty()) {
            printError("usage: git <command> [<args>]\nCommands: init, clone, status, add, commit, log, pull, push, remote");
            return;
        }
        final String lowerCase = args.get(0).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        final List listDrop = CollectionsKt.drop(args, 1);
        new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.handleGit$lambda$1(lowerCase, this, listDrop);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void handleGit$lambda$1(String gitCommand, PythonCommandManager this$0, List gitArgs) {
        String message;
        Intrinsics.checkNotNullParameter(gitCommand, "$gitCommand");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(gitArgs, "$gitArgs");
        try {
            switch (gitCommand.hashCode()) {
                case -1354815177:
                    if (!gitCommand.equals("commit")) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitCommit(gitArgs);
                    }
                    break;
                case -1097329270:
                    if (!gitCommand.equals("logout")) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitLogout();
                    }
                    break;
                case -934610874:
                    if (!gitCommand.equals("remote")) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitRemote(gitArgs);
                    }
                    break;
                case -892481550:
                    if (!gitCommand.equals(NotificationCompat.CATEGORY_STATUS)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitStatus();
                    }
                    break;
                case 96417:
                    if (!gitCommand.equals(ProductAction.ACTION_ADD)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitAdd(gitArgs);
                    }
                    break;
                case 107332:
                    if (!gitCommand.equals(K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitLog();
                    }
                    break;
                case 3237136:
                    if (!gitCommand.equals(ConfigConstants.CONFIG_INIT_SECTION)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitInit();
                    }
                    break;
                case 3452485:
                    if (!gitCommand.equals(ConfigConstants.CONFIG_PULL_SECTION)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitPull();
                    }
                    break;
                case 3452698:
                    if (!gitCommand.equals("push")) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitPush();
                    }
                    break;
                case 94756189:
                    if (!gitCommand.equals("clone")) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitClone(gitArgs);
                    }
                    break;
                case 103149417:
                    if (!gitCommand.equals(FirebaseAnalytics.Event.LOGIN)) {
                        this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    } else {
                        this$0.executeGitLogin(gitArgs);
                    }
                    break;
                default:
                    this$0.printError("git: '" + gitCommand + "' is not a supported git command.");
                    break;
            }
        } catch (Exception e) {
            if ((e instanceof TransportException) && (message = e.getMessage()) != null && StringsKt.contains((CharSequence) message, (CharSequence) "not authorized", true)) {
                this$0.printError("Git Authentication failed.\nTip: You need to log in. Use: git login <your_github_token>");
            } else {
                this$0.printError("git " + gitCommand + " error: " + e.getMessage());
            }
        }
    }

    private final void executeGitLogin(List<String> args) {
        if (args.isEmpty()) {
            TokenManager tokenManager = TokenManager.INSTANCE;
            Context appContext = CatroidApplication.getAppContext();
            Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
            String token = tokenManager.getToken(appContext);
            String str = token;
            if (str != null && str.length() != 0) {
                printOutput("You are currently logged in. (Token: " + StringsKt.take(token, 4) + "..." + StringsKt.takeLast(token, 4) + Constant.AFTER_QUTO);
                return;
            }
            printError("Usage: git login <github_personal_access_token>\nYou can generate it at https://github.com/settings/tokens");
            return;
        }
        String str2 = args.get(0);
        TokenManager tokenManager2 = TokenManager.INSTANCE;
        Context appContext2 = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext2, "getAppContext(...)");
        tokenManager2.saveToken(appContext2, str2);
        printOutput("Successfully logged in! Token saved securely in EncryptedSharedPreferences.");
    }

    private final void executeGitLogout() {
        TokenManager tokenManager = TokenManager.INSTANCE;
        Context appContext = CatroidApplication.getAppContext();
        Intrinsics.checkNotNullExpressionValue(appContext, "getAppContext(...)");
        tokenManager.clearToken(appContext);
        printOutput("Logged out successfully. Token cleared.");
    }

    private final void executeGitPull() throws Exception {
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository");
            return;
        }
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.executeGitPull$lambda$2(this.f$0);
            }
        });
        Git git = gitInstance;
        try {
            PullCommand pullCommandPull = git.pull();
            UsernamePasswordCredentialsProvider credentialsProvider = getCredentialsProvider();
            if (credentialsProvider != null) {
                pullCommandPull.setCredentialsProvider(credentialsProvider);
            }
            if (pullCommandPull.call().isSuccessful()) {
                printOutput("Successfully pulled from remote.");
            } else {
                printOutput("Pull completed. (Could be already up-to-date or have conflicts)");
            }
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeGitPull$lambda$2(PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("Pulling from remote...\n");
        }
    }

    private final void executeGitPush() throws Exception {
        String string;
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository");
            return;
        }
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.executeGitPush$lambda$5(this.f$0);
            }
        });
        Git git = gitInstance;
        try {
            PushCommand pushCommandPush = git.push();
            UsernamePasswordCredentialsProvider credentialsProvider = getCredentialsProvider();
            if (credentialsProvider != null) {
                pushCommandPush.setCredentialsProvider(credentialsProvider);
            }
            Iterable<PushResult> iterableCall = pushCommandPush.call();
            StringBuilder sb = new StringBuilder();
            Iterator<PushResult> it = iterableCall.iterator();
            while (it.hasNext()) {
                for (RemoteRefUpdate remoteRefUpdate : it.next().getRemoteUpdates()) {
                    sb.append(remoteRefUpdate.getRemoteName() + " : " + remoteRefUpdate.getStatus() + "\n");
                }
            }
            if (sb.length() == 0) {
                string = "Everything up-to-date";
            } else {
                String string2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                string = StringsKt.trim((CharSequence) string2).toString();
            }
            printOutput(string);
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeGitPush$lambda$5(PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("Pushing to remote...\n");
        }
    }

    private final void executeGitRemote(List<String> args) throws Exception {
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository");
            return;
        }
        Git git = gitInstance;
        try {
            Git git2 = git;
            if (!args.isEmpty() && !Intrinsics.areEqual(args.get(0), "-v")) {
                if (args.size() == 3 && Intrinsics.areEqual(args.get(0), "set-url") && Intrinsics.areEqual(args.get(1), "origin")) {
                    StoredConfig config = git2.getRepository().getConfig();
                    config.setString("remote", "origin", "url", args.get(2));
                    config.save();
                    printOutput("Origin URL updated.");
                } else {
                    printError("Usage: git remote -v OR git remote set-url origin <URL>");
                }
                Unit unit = Unit.INSTANCE;
                AutoCloseableKt.closeFinally(git, null);
                return;
            }
            String string = git2.getRepository().getConfig().getString("remote", "origin", "url");
            if (string == null) {
                string = "No origin set";
            } else {
                Intrinsics.checkNotNull(string);
            }
            printOutput("origin\t" + string);
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    private final void executeGitInit() throws Exception {
        if (new File(this.currentWorkingDirectory, ".git").exists()) {
            printOutput("Git repository already exists in " + this.currentWorkingDirectory.getAbsolutePath());
            return;
        }
        Git gitCall = Git.init().setDirectory(this.currentWorkingDirectory).call();
        try {
            Git git = gitCall;
            printOutput("Initialized empty Git repository in " + new File(this.currentWorkingDirectory, ".git").getAbsolutePath());
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(gitCall, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(gitCall, th);
                throw th2;
            }
        }
    }

    private final void executeGitClone(List<String> args) throws Exception {
        if (args.isEmpty()) {
            printError("fatal: You must specify a repository to clone.");
            return;
        }
        String str = args.get(0);
        final String strRemoveSuffix = (String) CollectionsKt.getOrNull(args, 1);
        if (strRemoveSuffix == null) {
            strRemoveSuffix = StringsKt.removeSuffix(StringsKt.substringAfterLast$default(str, "/", (String) null, 2, (Object) null), (CharSequence) ".git");
        }
        File file = new File(this.currentWorkingDirectory, strRemoveSuffix);
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.executeGitClone$lambda$10(this.f$0, strRemoveSuffix);
            }
        });
        CloneCommand directory = Git.cloneRepository().setURI(str).setDirectory(file);
        UsernamePasswordCredentialsProvider credentialsProvider = getCredentialsProvider();
        if (credentialsProvider != null) {
            directory.setCredentialsProvider(credentialsProvider);
        }
        Git gitCall = directory.call();
        try {
            Git git = gitCall;
            printOutput("Successfully cloned to " + file.getAbsolutePath());
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(gitCall, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(gitCall, th);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeGitClone$lambda$10(PythonCommandManager this$0, String folderName) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(folderName, "$folderName");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("Cloning into '" + folderName + "'...\n");
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0088 A[Catch: all -> 0x021a, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:20:0x00a0 A[Catch: all -> 0x021a, LOOP:3: B:18:0x009a->B:20:0x00a0, LOOP_END, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:24:0x00d4 A[Catch: all -> 0x021a, LOOP:4: B:22:0x00ce->B:24:0x00d4, LOOP_END, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0106 A[Catch: all -> 0x021a, LOOP:5: B:26:0x0100->B:28:0x0106, LOOP_END, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0148 A[Catch: all -> 0x021a, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x0160 A[Catch: all -> 0x021a, LOOP:1: B:36:0x015a->B:38:0x0160, LOOP_END, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    /* JADX WARN: Code duplicated, block: B:42:0x0192 A[Catch: all -> 0x021a, LOOP:2: B:40:0x018c->B:42:0x0192, LOOP_END, TryCatch #0 {all -> 0x021a, blocks: (B:7:0x0016, B:9:0x004a, B:50:0x01fc, B:10:0x0051, B:13:0x006a, B:15:0x0079, B:30:0x0128, B:33:0x0139, B:44:0x01b4, B:46:0x01c3, B:47:0x01d5, B:49:0x01db, B:35:0x0148, B:36:0x015a, B:38:0x0160, B:39:0x017f, B:40:0x018c, B:42:0x0192, B:43:0x01b1, B:17:0x0088, B:18:0x009a, B:20:0x00a0, B:21:0x00c1, B:22:0x00ce, B:24:0x00d4, B:25:0x00f3, B:26:0x0100, B:28:0x0106, B:29:0x0125), top: B:58:0x0016 }] */
    private final void executeGitStatus() throws Exception {
        Iterator<T> it;
        Iterator<T> it2;
        Iterator<T> it3;
        Iterator<T> it4;
        Iterator<T> it5;
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository (or any of the parent directories): .git");
            return;
        }
        Git git = gitInstance;
        try {
            Git git2 = git;
            Status statusCall = git2.status().call();
            StringBuilder sb = new StringBuilder();
            sb.append("On branch " + git2.getRepository().getBranch() + "\n\n");
            if (statusCall.isClean()) {
                sb.append("nothing to commit, working tree clean\n");
            } else {
                Set<String> added = statusCall.getAdded();
                Intrinsics.checkNotNullExpressionValue(added, "getAdded(...)");
                if (added.isEmpty()) {
                    Set<String> changed = statusCall.getChanged();
                    Intrinsics.checkNotNullExpressionValue(changed, "getChanged(...)");
                    if (changed.isEmpty()) {
                        Set<String> removed = statusCall.getRemoved();
                        Intrinsics.checkNotNullExpressionValue(removed, "getRemoved(...)");
                        if (!removed.isEmpty()) {
                            sb.append("Changes to be committed:\n");
                            Set<String> added2 = statusCall.getAdded();
                            Intrinsics.checkNotNullExpressionValue(added2, "getAdded(...)");
                            it = added2.iterator();
                            while (it.hasNext()) {
                                sb.append("  (new file)  " + ((String) it.next()) + "\n");
                            }
                            Set<String> changed2 = statusCall.getChanged();
                            Intrinsics.checkNotNullExpressionValue(changed2, "getChanged(...)");
                            it2 = changed2.iterator();
                            while (it2.hasNext()) {
                                sb.append("  (modified)  " + ((String) it2.next()) + "\n");
                            }
                            Set<String> removed2 = statusCall.getRemoved();
                            Intrinsics.checkNotNullExpressionValue(removed2, "getRemoved(...)");
                            it3 = removed2.iterator();
                            while (it3.hasNext()) {
                                sb.append("  (deleted)   " + ((String) it3.next()) + "\n");
                            }
                            sb.append("\n");
                        }
                    } else {
                        sb.append("Changes to be committed:\n");
                        Set<String> added3 = statusCall.getAdded();
                        Intrinsics.checkNotNullExpressionValue(added3, "getAdded(...)");
                        it = added3.iterator();
                        while (it.hasNext()) {
                            sb.append("  (new file)  " + ((String) it.next()) + "\n");
                        }
                        Set<String> changed3 = statusCall.getChanged();
                        Intrinsics.checkNotNullExpressionValue(changed3, "getChanged(...)");
                        it2 = changed3.iterator();
                        while (it2.hasNext()) {
                            sb.append("  (modified)  " + ((String) it2.next()) + "\n");
                        }
                        Set<String> removed3 = statusCall.getRemoved();
                        Intrinsics.checkNotNullExpressionValue(removed3, "getRemoved(...)");
                        it3 = removed3.iterator();
                        while (it3.hasNext()) {
                            sb.append("  (deleted)   " + ((String) it3.next()) + "\n");
                        }
                        sb.append("\n");
                    }
                } else {
                    sb.append("Changes to be committed:\n");
                    Set<String> added4 = statusCall.getAdded();
                    Intrinsics.checkNotNullExpressionValue(added4, "getAdded(...)");
                    it = added4.iterator();
                    while (it.hasNext()) {
                        sb.append("  (new file)  " + ((String) it.next()) + "\n");
                    }
                    Set<String> changed4 = statusCall.getChanged();
                    Intrinsics.checkNotNullExpressionValue(changed4, "getChanged(...)");
                    it2 = changed4.iterator();
                    while (it2.hasNext()) {
                        sb.append("  (modified)  " + ((String) it2.next()) + "\n");
                    }
                    Set<String> removed4 = statusCall.getRemoved();
                    Intrinsics.checkNotNullExpressionValue(removed4, "getRemoved(...)");
                    it3 = removed4.iterator();
                    while (it3.hasNext()) {
                        sb.append("  (deleted)   " + ((String) it3.next()) + "\n");
                    }
                    sb.append("\n");
                }
                Set<String> modified = statusCall.getModified();
                Intrinsics.checkNotNullExpressionValue(modified, "getModified(...)");
                if (modified.isEmpty()) {
                    Set<String> missing = statusCall.getMissing();
                    Intrinsics.checkNotNullExpressionValue(missing, "getMissing(...)");
                    if (!missing.isEmpty()) {
                        sb.append("Changes not staged for commit:\n");
                        Set<String> modified2 = statusCall.getModified();
                        Intrinsics.checkNotNullExpressionValue(modified2, "getModified(...)");
                        it4 = modified2.iterator();
                        while (it4.hasNext()) {
                            sb.append("  (modified)  " + ((String) it4.next()) + "\n");
                        }
                        Set<String> missing2 = statusCall.getMissing();
                        Intrinsics.checkNotNullExpressionValue(missing2, "getMissing(...)");
                        it5 = missing2.iterator();
                        while (it5.hasNext()) {
                            sb.append("  (deleted)   " + ((String) it5.next()) + "\n");
                        }
                        sb.append("\n");
                    }
                } else {
                    sb.append("Changes not staged for commit:\n");
                    Set<String> modified3 = statusCall.getModified();
                    Intrinsics.checkNotNullExpressionValue(modified3, "getModified(...)");
                    it4 = modified3.iterator();
                    while (it4.hasNext()) {
                        sb.append("  (modified)  " + ((String) it4.next()) + "\n");
                    }
                    Set<String> missing3 = statusCall.getMissing();
                    Intrinsics.checkNotNullExpressionValue(missing3, "getMissing(...)");
                    it5 = missing3.iterator();
                    while (it5.hasNext()) {
                        sb.append("  (deleted)   " + ((String) it5.next()) + "\n");
                    }
                    sb.append("\n");
                }
                Set<String> untracked = statusCall.getUntracked();
                Intrinsics.checkNotNullExpressionValue(untracked, "getUntracked(...)");
                if (!untracked.isEmpty()) {
                    sb.append("Untracked files:\n");
                    Set<String> untracked2 = statusCall.getUntracked();
                    Intrinsics.checkNotNullExpressionValue(untracked2, "getUntracked(...)");
                    Iterator<T> it6 = untracked2.iterator();
                    while (it6.hasNext()) {
                        sb.append("  " + ((String) it6.next()) + "\n");
                    }
                }
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            printOutput(StringsKt.trimEnd((CharSequence) string).toString());
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    private final void executeGitAdd(List<String> args) throws Exception {
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository: .git");
            return;
        }
        if (args.isEmpty()) {
            printError("Nothing specified, nothing added.");
            return;
        }
        Git git = gitInstance;
        try {
            AddCommand addCommandAdd = git.add();
            Iterator<String> it = args.iterator();
            while (it.hasNext()) {
                addCommandAdd.addFilepattern(it.next());
            }
            addCommandAdd.call();
            printComplete();
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    private final void executeGitCommit(List<String> args) throws Exception {
        int i;
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository: .git");
            return;
        }
        int iIndexOf = args.indexOf("-m");
        if (iIndexOf == -1 || (i = iIndexOf + 1) >= args.size()) {
            printError("error: message is required. Use: git commit -m \"message\"");
            return;
        }
        String strRemoveSurrounding = StringsKt.removeSurrounding(StringsKt.removeSurrounding(CollectionsKt.joinToString$default(CollectionsKt.drop(args, i), " ", null, null, 0, null, null, 62, null), (CharSequence) "\""), (CharSequence) FormatHelper.QUOTE);
        Git git = gitInstance;
        try {
            Git git2 = git;
            RevCommit revCommitCall = git2.commit().setMessage(strRemoveSurrounding).setAuthor(new PersonIdent("CatroidUser", "user@catroid.org")).call();
            String branch = git2.getRepository().getBranch();
            String name2 = revCommitCall.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            printOutput("[" + branch + " " + StringsKt.take(name2, 7) + "] " + strRemoveSurrounding);
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    private final void executeGitLog() throws Exception {
        Git gitInstance = getGitInstance();
        if (gitInstance == null) {
            printError("fatal: not a git repository: .git");
            return;
        }
        Git git = gitInstance;
        try {
            Iterable<RevCommit> iterableCall = git.log().setMaxCount(10).call();
            StringBuilder sb = new StringBuilder();
            for (RevCommit revCommit : iterableCall) {
                sb.append("commit " + revCommit.getName() + "\n");
                sb.append("Author: " + revCommit.getAuthorIdent().getName() + " <" + revCommit.getAuthorIdent().getEmailAddress() + ">\n");
                sb.append("Date:   " + revCommit.getAuthorIdent().getWhen() + "\n\n");
                sb.append("    " + revCommit.getFullMessage() + "\n\n");
            }
            if (sb.length() == 0) {
                printOutput("No commits yet.");
            } else {
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                printOutput(StringsKt.trimEnd((CharSequence) string).toString());
            }
            Unit unit = Unit.INSTANCE;
            AutoCloseableKt.closeFinally(git, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(git, th);
                throw th2;
            }
        }
    }

    private final void handleSh(List<String> args, String fullCommand) {
        String string = StringsKt.startsWith$default(fullCommand, "./", false, 2, (Object) null) ? StringsKt.trim((CharSequence) StringsKt.removePrefix(fullCommand, (CharSequence) "./")).toString() : (String) CollectionsKt.firstOrNull((List) args);
        String str = string;
        if (str == null || str.length() == 0) {
            printError("Usage: sh <script.sh> or ./<script.sh>");
            return;
        }
        File file = getFile(string);
        if (!file.exists() || file.isDirectory()) {
            printError("sh: " + string + ": No such file");
            return;
        }
        try {
            List lines$default = FilesKt.readLines$default(file, null, 1, null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(lines$default, 10));
            Iterator it = lines$default.iterator();
            while (it.hasNext()) {
                arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                String str2 = (String) obj;
                if (str2.length() > 0 && !StringsKt.startsWith$default(str2, "#", false, 2, (Object) null)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (arrayList3.isEmpty()) {
                printComplete();
            } else {
                executeCommandSequence(arrayList3);
            }
        } catch (Exception e) {
            printError("sh: Failed to read script - " + e.getMessage());
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [org.catrobat.catroid.python.PythonCommandManager$executeCommandSequence$sequenceListener$1] */
    private final void executeCommandSequence(final List<String> commands) {
        final Ref.IntRef intRef = new Ref.IntRef();
        final CommandOutputListener commandOutputListener = this.outputListener;
        ?? r2 = new CommandOutputListener() { // from class: org.catrobat.catroid.python.PythonCommandManager$executeCommandSequence$sequenceListener$1
            @Override // org.catrobat.catroid.python.CommandOutputListener
            public void onOutput(String output) {
                Intrinsics.checkNotNullParameter(output, "output");
                CommandOutputListener commandOutputListener2 = commandOutputListener;
                if (commandOutputListener2 != null) {
                    commandOutputListener2.onOutput(output);
                }
            }

            @Override // org.catrobat.catroid.python.CommandOutputListener
            public void onOpenEditor(File file) {
                Intrinsics.checkNotNullParameter(file, "file");
                CommandOutputListener commandOutputListener2 = commandOutputListener;
                if (commandOutputListener2 != null) {
                    commandOutputListener2.onOpenEditor(file);
                }
            }

            @Override // org.catrobat.catroid.python.CommandOutputListener
            public void onComplete() {
                intRef.element++;
                if (intRef.element < commands.size()) {
                    processNext();
                    return;
                }
                this.setOutputListener(commandOutputListener);
                CommandOutputListener commandOutputListener2 = commandOutputListener;
                if (commandOutputListener2 != null) {
                    commandOutputListener2.onComplete();
                }
            }

            public final void processNext() {
                this.processCommand(commands.get(intRef.element));
            }
        };
        this.outputListener = (CommandOutputListener) r2;
        r2.processNext();
    }

    private final void handleAlias(List<String> args) {
        if (args.isEmpty()) {
            Map<String, String> map = this.aliases;
            ArrayList arrayList = new ArrayList(map.size());
            for (Map.Entry<String, String> entry : map.entrySet()) {
                arrayList.add(((Object) entry.getKey()) + "='" + ((Object) entry.getValue()) + FormatHelper.QUOTE);
            }
            String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, "\n", null, null, 0, null, null, 62, null);
            if (strJoinToString$default.length() == 0) {
                strJoinToString$default = "No aliases defined.";
            }
            printOutput(strJoinToString$default);
            return;
        }
        String strJoinToString$default2 = CollectionsKt.joinToString$default(args, " ", null, null, 0, null, null, 62, null);
        if (!StringsKt.contains$default((CharSequence) strJoinToString$default2, (CharSequence) ContainerUtils.KEY_VALUE_DELIMITER, false, 2, (Object) null)) {
            printError("Usage: alias name=\"command\"");
            return;
        }
        List listSplit$default = StringsKt.split$default((CharSequence) strJoinToString$default2, new String[]{ContainerUtils.KEY_VALUE_DELIMITER}, false, 2, 2, (Object) null);
        this.aliases.put(StringsKt.trim((CharSequence) listSplit$default.get(0)).toString(), StringsKt.removeSurrounding(StringsKt.removeSurrounding(StringsKt.trim((CharSequence) listSplit$default.get(1)).toString(), (CharSequence) "\""), (CharSequence) FormatHelper.QUOTE));
        printComplete();
    }

    private final void handleLogcat(List<String> args) {
        Integer intOrNull;
        String str = (String) CollectionsKt.firstOrNull((List) args);
        final int iIntValue = (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) ? 50 : intOrNull.intValue();
        final int iMyPid = Process.myPid();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.handleLogcat$lambda$27(iMyPid, this, iIntValue);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleLogcat$lambda$27(int i, PythonCommandManager this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            InputStream inputStream = Runtime.getRuntime().exec("logcat -d -v threadtime --pid=" + i).getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
            Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            BufferedReader bufferedReader2 = bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, 8192);
            try {
                String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.takeLast(SequencesKt.toList(TextStreamsKt.lineSequence(bufferedReader2)), i2), "\n", null, null, 0, null, null, 62, null);
                CloseableKt.closeFinally(bufferedReader2, null);
                this$0.printOutput("--- Last " + i2 + " logcat lines ---\n" + strJoinToString$default);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader2, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            this$0.printError("logcat failed: " + e.getMessage());
        }
    }

    private final void handleHexdump(List<String> args) {
        if (args.isEmpty()) {
            printError("Usage: hexdump <file>");
            return;
        }
        final File file = getFile(args.get(0));
        if (!file.exists() || file.isDirectory()) {
            printError("hexdump: " + ((Object) args.get(0)) + ": No such file");
        } else {
            new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.handleHexdump$lambda$30(file, this);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleHexdump$lambda$30(File file, PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(file, "$file");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            byte[] bytes = FilesKt.readBytes(file);
            int iMin = Math.min(bytes.length, 256);
            StringBuilder sb = new StringBuilder();
            sb.append("File: " + file.getName() + " (showing first " + iMin + " bytes)\n");
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, iMin), 16);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step > 0 && first <= last) || (step < 0 && last <= first)) {
                while (true) {
                    byte[] bArrSliceArray = ArraysKt.sliceArray(bytes, RangesKt.until(first, Math.min(first + 16, iMin)));
                    StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                    String str = String.format("%08X  ", Arrays.copyOf(new Object[]{Integer.valueOf(first)}, 1));
                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                    sb.append(str);
                    for (byte b : bArrSliceArray) {
                        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                        String str2 = String.format("%02X ", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                        sb.append(str2);
                    }
                    for (int length = bArrSliceArray.length; length < 16; length++) {
                        sb.append("   ");
                    }
                    sb.append(" |");
                    for (byte b2 : bArrSliceArray) {
                        char c = (char) b2;
                        if (' ' > c || c >= 127) {
                            c = '.';
                        }
                        sb.append(c);
                    }
                    sb.append("|\n");
                    if (first == last) {
                        break;
                    } else {
                        first += step;
                    }
                }
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            this$0.printOutput(string);
        } catch (Exception e) {
            this$0.printError("hexdump failed: " + e.getMessage());
        }
    }

    private final File getFile(String path) {
        return StringsKt.startsWith$default(path, "/", false, 2, (Object) null) ? new File(path) : new File(this.currentWorkingDirectory, path);
    }

    private final void handleCd(List<String> args) throws IOException {
        String absolutePath = (String) CollectionsKt.firstOrNull((List) args);
        if (absolutePath == null) {
            absolutePath = this.project.getFilesDir().getAbsolutePath();
        }
        Intrinsics.checkNotNull(absolutePath);
        File file = getFile(absolutePath);
        if (file.exists() && file.isDirectory()) {
            File canonicalFile = file.getCanonicalFile();
            Intrinsics.checkNotNullExpressionValue(canonicalFile, "getCanonicalFile(...)");
            this.currentWorkingDirectory = canonicalFile;
            printComplete();
            return;
        }
        printError("cd: " + absolutePath + ": No such file or directory");
    }

    private final void handleLs(List<String> args) {
        File file = !args.isEmpty() ? getFile(args.get(0)) : this.currentWorkingDirectory;
        if (!file.exists() || !file.isDirectory()) {
            printError("ls: cannot access '" + CollectionsKt.firstOrNull((List) args) + "': No such file or directory");
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        List listSortedWith = fileArrListFiles != null ? ArraysKt.sortedWith(fileArrListFiles, new Comparator() { // from class: org.catrobat.catroid.python.PythonCommandManager$handleLs$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((File) t).getName(), ((File) t2).getName());
            }
        }) : null;
        List list = listSortedWith;
        if (list == null || list.isEmpty()) {
            printOutput("");
        } else {
            printOutput(CollectionsKt.joinToString$default(listSortedWith, "\n", null, null, 0, null, new Function1<File, CharSequence>() { // from class: org.catrobat.catroid.python.PythonCommandManager$handleLs$output$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(File file2) {
                    boolean zIsDirectory = file2.isDirectory();
                    String name2 = file2.getName();
                    if (zIsDirectory) {
                        name2 = name2 + "/";
                    } else {
                        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    }
                    return name2;
                }
            }, 30, null));
        }
    }

    private final void handleMkdir(List<String> args) {
        if (args.isEmpty()) {
            printError("mkdir: missing operand");
            return;
        }
        File file = getFile(args.get(0));
        if (file.mkdirs()) {
            printOutput("Created directory: " + file.getName());
        } else {
            printError("mkdir: cannot create directory '" + ((Object) args.get(0)) + "': File exists or permission denied");
        }
    }

    private final void handleRm(List<String> args) {
        if (args.isEmpty()) {
            printError("rm: missing operand");
            return;
        }
        boolean z = args.contains("-r") || args.contains("-rf");
        String str = (String) CollectionsKt.last((List) args);
        File file = getFile(str);
        if (!file.exists()) {
            printError("rm: cannot remove '" + str + "': No such file or directory");
            return;
        }
        if (file.isDirectory() && !z) {
            printError("rm: cannot remove '" + str + "': Is a directory (use -r)");
        } else if (FilesKt.deleteRecursively(file)) {
            printOutput("Removed '" + str + FormatHelper.QUOTE);
        } else {
            printError("rm: failed to remove '" + str + FormatHelper.QUOTE);
        }
    }

    private final void handleCp(List<String> args) {
        if (args.size() < 2) {
            printError("cp: missing file operand");
            return;
        }
        File file = getFile(args.get(0));
        File file2 = getFile(args.get(1));
        if (!file.exists()) {
            printError("cp: cannot stat '" + ((Object) args.get(0)) + "': No such file");
            return;
        }
        try {
            FilesKt.copyRecursively$default(file, file2, true, null, 4, null);
            printOutput("Copied '" + ((Object) args.get(0)) + "' to '" + ((Object) args.get(1)) + FormatHelper.QUOTE);
        } catch (Exception e) {
            printError("cp: " + e.getMessage());
        }
    }

    private final void handleMv(List<String> args) {
        if (args.size() < 2) {
            printError("mv: missing file operand");
            return;
        }
        File file = getFile(args.get(0));
        File file2 = getFile(args.get(1));
        if (!file.exists()) {
            printError("mv: cannot stat '" + ((Object) args.get(0)) + "': No such file");
        } else {
            if (!file.renameTo(file2)) {
                printError("mv: failed to move file");
                return;
            }
            printOutput("Moved '" + ((Object) args.get(0)) + "' to '" + ((Object) args.get(1)) + FormatHelper.QUOTE);
        }
    }

    private final void handleTouch(List<String> args) throws IOException {
        if (args.isEmpty()) {
            printError("touch: missing operand");
            return;
        }
        File file = getFile(args.get(0));
        if (file.exists()) {
            file.setLastModified(System.currentTimeMillis());
        } else {
            file.createNewFile();
        }
        printComplete();
    }

    private final void handleCat(List<String> args) {
        if (args.isEmpty()) {
            printError("cat: missing operand");
            return;
        }
        File file = getFile(args.get(0));
        if (!file.exists()) {
            printError("cat: " + ((Object) args.get(0)) + ": No such file");
        } else {
            if (file.isDirectory()) {
                printError("cat: " + ((Object) args.get(0)) + ": Is a directory");
                return;
            }
            try {
                printOutput(FilesKt.readText$default(file, null, 1, null));
            } catch (Exception e) {
                printError("cat: " + e.getMessage());
            }
        }
    }

    private final void handleEcho(String fullCommand) {
        String string = StringsKt.trim((CharSequence) StringsKt.removePrefix(fullCommand, (CharSequence) "echo")).toString();
        try {
            if (!StringsKt.contains$default((CharSequence) string, (CharSequence) ">>", false, 2, (Object) null)) {
                if (StringsKt.contains$default((CharSequence) string, (CharSequence) ">", false, 2, (Object) null)) {
                    List listSplit$default = StringsKt.split$default((CharSequence) string, new String[]{">"}, false, 2, 2, (Object) null);
                    FilesKt.writeText$default(getFile(StringsKt.trim((CharSequence) listSplit$default.get(1)).toString()), StringsKt.removeSurrounding(StringsKt.removeSurrounding(StringsKt.trim((CharSequence) listSplit$default.get(0)).toString(), (CharSequence) "\""), (CharSequence) FormatHelper.QUOTE) + "\n", null, 2, null);
                    printComplete();
                } else {
                    printOutput(StringsKt.removeSurrounding(StringsKt.removeSurrounding(string, (CharSequence) "\""), (CharSequence) FormatHelper.QUOTE));
                }
            } else {
                List listSplit$default2 = StringsKt.split$default((CharSequence) string, new String[]{">>"}, false, 2, 2, (Object) null);
                FilesKt.appendText$default(getFile(StringsKt.trim((CharSequence) listSplit$default2.get(1)).toString()), StringsKt.removeSurrounding(StringsKt.removeSurrounding(StringsKt.trim((CharSequence) listSplit$default2.get(0)).toString(), (CharSequence) "\""), (CharSequence) FormatHelper.QUOTE) + "\n", null, 2, null);
                printComplete();
            }
        } catch (Exception e) {
            printError("echo: " + e.getMessage());
        }
    }

    private final void handleNano(List<String> args) {
        if (args.isEmpty()) {
            printError("nano: missing filename");
            return;
        }
        final File file = getFile(args.get(0));
        if (!file.isDirectory()) {
            this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.handleNano$lambda$32(this.f$0, file);
                }
            });
        } else {
            printError("nano: " + ((Object) args.get(0)) + " is a directory");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleNano$lambda$32(PythonCommandManager this$0, File file) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(file, "$file");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOpenEditor(file);
        }
    }

    private final void handleWget(List<String> args) {
        if (args.isEmpty()) {
            printError("wget: missing URL");
            return;
        }
        final String str = args.get(0);
        final String strSubstringAfterLast = (String) CollectionsKt.getOrNull(args, 1);
        if (strSubstringAfterLast == null) {
            strSubstringAfterLast = StringsKt.substringAfterLast(str, "/", "downloaded_file");
        }
        final File file = getFile(strSubstringAfterLast);
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.handleWget$lambda$33(this.f$0, str, strSubstringAfterLast);
            }
        });
        new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.handleWget$lambda$35(str, file, this);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleWget$lambda$33(PythonCommandManager this$0, String urlString, String fileName) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(urlString, "$urlString");
        Intrinsics.checkNotNullParameter(fileName, "$fileName");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("Downloading " + urlString + " to " + fileName + "...\n");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleWget$lambda$35(String urlString, File destFile, PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(urlString, "$urlString");
        Intrinsics.checkNotNullParameter(destFile, "$destFile");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            URLConnection uRLConnectionOpenConnection = new URL(urlString).openConnection();
            uRLConnectionOpenConnection.connect();
            InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            try {
                Intrinsics.checkNotNull(inputStream);
                ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                CloseableKt.closeFinally(fileOutputStream, null);
                this$0.printOutput("Saved: " + destFile.getAbsolutePath());
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            this$0.printError("wget failed: " + e.getMessage());
        }
    }

    private final void handleGrep(List<String> args) {
        if (args.size() < 2) {
            printError("Usage: grep <pattern> <file>");
            return;
        }
        String str = args.get(0);
        File file = getFile(args.get(1));
        if (!file.exists() || file.isDirectory()) {
            printError("grep: " + ((Object) args.get(1)) + ": No such file");
            return;
        }
        try {
            final Regex regex = new Regex(str, RegexOption.IGNORE_CASE);
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                String strJoinToString$default = SequencesKt.joinToString$default(SequencesKt.filter(TextStreamsKt.lineSequence(bufferedReader), new Function1<String, Boolean>() { // from class: org.catrobat.catroid.python.PythonCommandManager$handleGrep$matchedLines$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(regex.containsMatchIn(it));
                    }
                }), "\n", null, null, 0, null, null, 62, null);
                CloseableKt.closeFinally(bufferedReader, null);
                if (strJoinToString$default.length() > 0) {
                    printOutput(strJoinToString$default);
                } else {
                    printComplete();
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            printError("grep error: " + e.getMessage());
        }
    }

    private final void handleFind(List<String> args) {
        final String str = (String) CollectionsKt.firstOrNull((List) args);
        if (str == null) {
            printError("Usage: find <name_part>");
            return;
        }
        try {
            String strJoinToString$default = SequencesKt.joinToString$default(SequencesKt.filter(FilesKt.walkTopDown(this.currentWorkingDirectory), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.python.PythonCommandManager$handleFind$foundFiles$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String name2 = it.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    return Boolean.valueOf(StringsKt.contains((CharSequence) name2, (CharSequence) str, true));
                }
            }), "\n", null, null, 0, null, new Function1<File, CharSequence>() { // from class: org.catrobat.catroid.python.PythonCommandManager$handleFind$foundFiles$2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(File it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String absolutePath = it.getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                    String absolutePath2 = this.this$0.project.getFilesDir().getAbsolutePath();
                    Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
                    String strRemovePrefix = StringsKt.removePrefix(absolutePath, (CharSequence) absolutePath2);
                    if (strRemovePrefix.length() == 0) {
                        strRemovePrefix = "/";
                    }
                    return strRemovePrefix;
                }
            }, 30, null);
            if (strJoinToString$default.length() > 0) {
                printOutput(strJoinToString$default);
            } else {
                printOutput("No files found matching '" + str + FormatHelper.QUOTE);
            }
        } catch (Exception e) {
            printError("find error: " + e.getMessage());
        }
    }

    private final void handleUnzip(List<String> args) {
        if (args.isEmpty()) {
            printError("Usage: unzip <file.zip>");
            return;
        }
        final File file = getFile(args.get(0));
        if (file.exists()) {
            new Thread(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.handleUnzip$lambda$40(file, this);
                }
            }).start();
        } else {
            printError("unzip: cannot find " + ((Object) args.get(0)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [T, java.util.zip.ZipEntry] */
    /* JADX WARN: Type inference failed for: r2v7, types: [T, java.util.zip.ZipEntry] */
    public static final void handleUnzip$lambda$40(File zipFile, final PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(zipFile, "$zipFile");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            try {
                ZipInputStream zipInputStream2 = zipInputStream;
                final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                objectRef.element = zipInputStream2.getNextEntry();
                while (objectRef.element != 0) {
                    File file = new File(this$0.currentWorkingDirectory, ((ZipEntry) objectRef.element).getName());
                    if (((ZipEntry) objectRef.element).isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        try {
                            ByteStreamsKt.copyTo$default(zipInputStream2, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                    }
                    this$0.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            PythonCommandManager.handleUnzip$lambda$40$lambda$39$lambda$38(this.f$0, objectRef);
                        }
                    });
                    objectRef.element = zipInputStream2.getNextEntry();
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(zipInputStream, null);
                this$0.printOutput("Archive extracted successfully.");
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(zipInputStream, th3);
                    throw th4;
                }
            }
        } catch (Exception e) {
            this$0.printError("unzip error: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void handleUnzip$lambda$40$lambda$39$lambda$38(PythonCommandManager this$0, Ref.ObjectRef entry) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(entry, "$entry");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("  inflating: " + ((ZipEntry) entry.element).getName() + "\n");
        }
    }

    private final void handleSysInfo() {
        String str = Build.VERSION.RELEASE;
        String str2 = Build.MANUFACTURER + " " + Build.MODEL;
        String[] SUPPORTED_ABIS = Build.SUPPORTED_ABIS;
        Intrinsics.checkNotNullExpressionValue(SUPPORTED_ABIS, "SUPPORTED_ABIS");
        printOutput(StringsKt.trimMargin$default("\n            |    /\\_/\\    OS: Android " + str + "\n            |   ( o.o )   Device: " + str2 + "\n            |    > ^ <    Arch: " + ArraysKt.joinToString$default(SUPPORTED_ABIS, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) + "\n            |             Engine: NewCatroid\n        ", null, 1, null));
    }

    private final void handleMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long j = 1048576;
        long j2 = runtime.totalMemory() / j;
        long jFreeMemory = runtime.freeMemory() / j;
        printOutput(StringsKt.trimMargin$default("\n            |              total        used        free      max\n            |Mem(MB):       " + j2 + "          " + (j2 - jFreeMemory) + "          " + jFreeMemory + "        " + (runtime.maxMemory() / j) + "\n        ", null, 1, null));
    }

    private final void handleMlCommands(List<String> args) {
        if (args.isEmpty()) {
            printOutput("ML Engine Commands: ml info, ml mode <train/eval>");
            return;
        }
        String str = args.get(0);
        if (Intrinsics.areEqual(str, "mode")) {
            printOutput("ML Training mode set to: " + Intrinsics.areEqual(CollectionsKt.getOrNull(args, 1), "train"));
        } else if (Intrinsics.areEqual(str, CompilerOptions.INFO)) {
            printOutput("Pocketensor Engine Active. Use Python to interact with tensors.");
        } else {
            printError("Unknown ml command");
        }
    }

    private final void handleVmCommands(List<String> args) {
        if (args.isEmpty()) {
            printOutput("VM Commands: vm stop <name>");
            return;
        }
        if (Intrinsics.areEqual(args.get(0), "stop")) {
            String str = (String) CollectionsKt.getOrNull(args, 1);
            if (str == null) {
                printError("Missing VM name");
                return;
            } else {
                printOutput("Send STOP signal to VM: " + str);
                return;
            }
        }
        printError("Unknown vm command");
    }

    private final void printOutput(final String text) {
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.printOutput$lambda$41(text, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printOutput$lambda$41(String text, PythonCommandManager this$0) {
        CommandOutputListener commandOutputListener;
        Intrinsics.checkNotNullParameter(text, "$text");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (text.length() > 0 && (commandOutputListener = this$0.outputListener) != null) {
            commandOutputListener.onOutput(text + "\n");
        }
        CommandOutputListener commandOutputListener2 = this$0.outputListener;
        if (commandOutputListener2 != null) {
            commandOutputListener2.onComplete();
        }
    }

    private final void printError(final String text) {
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.printError$lambda$42(this.f$0, text);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printError$lambda$42(PythonCommandManager this$0, String text) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(text, "$text");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onOutput("Error: " + text + "\n");
        }
        CommandOutputListener commandOutputListener2 = this$0.outputListener;
        if (commandOutputListener2 != null) {
            commandOutputListener2.onComplete();
        }
    }

    private final void printComplete() {
        this.mainThreadHandler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                PythonCommandManager.printComplete$lambda$43(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void printComplete$lambda$43(PythonCommandManager this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        CommandOutputListener commandOutputListener = this$0.outputListener;
        if (commandOutputListener != null) {
            commandOutputListener.onComplete();
        }
    }

    private final void handlePipCommand(List<String> args) {
        String strBuildPipScript = buildPipScript(args);
        if (StringsKt.startsWith$default(strBuildPipScript, "Error:", false, 2, (Object) null)) {
            printError(StringsKt.removePrefix(strBuildPipScript, (CharSequence) "Error: "));
        } else {
            executePythonScript(strBuildPipScript);
        }
    }

    private final String buildPipScript(List<String> args) {
        String strTrimIndent;
        boolean zContains = args.contains("--files");
        ArrayList arrayList = new ArrayList();
        for (Object obj : args) {
            if (!Intrinsics.areEqual((String) obj, "--files")) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        String str = (String) CollectionsKt.firstOrNull((List) arrayList2);
        if (arrayList2.size() <= 1) {
            return "Error: No package name specified.";
        }
        String str2 = (String) arrayList2.get(1);
        if (!Intrinsics.areEqual(str, "install") && !Intrinsics.areEqual(str, "download")) {
            return "Error: use 'pip install <package> [--files]'.";
        }
        String absolutePath = this.currentWorkingDirectory.getAbsolutePath();
        if (zContains) {
            strTrimIndent = StringsKt.trimIndent("\n    import os, sys, io\n    \n    target_dir = '" + absolutePath + "'\n    files_before = set(os.listdir(target_dir))\n    \n    original_stdout = sys.stdout\n    original_stderr = sys.stderr\n    sys.stdout = io.StringIO()\n    sys.stderr = io.StringIO()\n    \n    try:\n        from pip._internal.cli.main import main as pip_main\n        cli_args = [\n            'download', '--only-binary=:all:', '-d', target_dir,\n            '--find-links', '" + this.defaultLibsPath + "', '--index-url', 'https://chaquo.com/pypi-13.1/',\n            '--extra-index-url', 'https://pypi.org/simple', '" + str2 + "'\n        ]\n        result = pip_main(cli_args)\n    finally:\n        sys.stdout = original_stdout\n        sys.stderr = original_stderr\n        \n    if result == 0:\n        files_after = set(os.listdir(target_dir))\n        new_files = sorted(list(files_after - files_before))\n        if new_files:\n            print('\\\\n'.join(new_files))\n\n    ");
        } else {
            strTrimIndent = StringsKt.trimIndent("\n    import os, sys, io\n    from pip._internal.cli.main import main as pip_main\n\n    stdout_capture = io.StringIO()\n    stderr_capture = io.StringIO()\n    \n    original_stdout = sys.stdout\n    original_stderr = sys.stderr\n    \n    sys.stdout = stdout_capture\n    sys.stderr = stderr_capture\n    \n    result = -1\n    try:\n        cli_args = [\n            'download', '--only-binary=:all:', '-d', '" + absolutePath + "',\n            '--find-links', '" + this.defaultLibsPath + "', '--index-url', 'https://chaquo.com/pypi-13.1/',\n            '--extra-index-url', 'https://pypi.org/simple', '" + str2 + "'\n        ]\n        \n        result = pip_main(cli_args)\n        \n    finally:\n        sys.stdout = original_stdout\n        sys.stderr = original_stderr\n\n    pip_stdout = stdout_capture.getvalue()\n    pip_stderr = stderr_capture.getvalue()\n\n    if pip_stdout:\n        print(pip_stdout, end='')\n        \n    if pip_stderr:\n        print(pip_stderr, file=sys.stderr, end='')\n\n    if result == 0:\n        print(f\"\\n--- Pip finished successfully (Code: {result}) ---\")\n    else:\n        print(f\"\\n--- Pip finished with an error (Code: {result}) ---\")\n        \n    ");
        }
        Intrinsics.checkNotNull(absolutePath);
        return StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(strTrimIndent, "$targetDir", absolutePath, false, 4, (Object) null), "$defaultLibsPath", this.defaultLibsPath, false, 4, (Object) null), "$packageName", str2, false, 4, (Object) null);
    }

    private final void executePythonScript(String script) {
        String absolutePath = this.currentWorkingDirectory.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        this.pythonEngine.runScriptAsync(StringsKt.trimIndent("\nimport os, sys\ntry:\n    os.chdir('" + StringsKt.replace$default(absolutePath, "\\", "\\\\", false, 4, (Object) null) + "')\n" + StringsKt.prependIndent(script, "    ") + "\nexcept Exception as e:\n    import traceback\n    traceback.print_exc(file=sys.stdout)\n    "), new AnonymousClass1());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/python/PythonCommandManager$executePythonScript$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.python.PythonCommandManager$executePythonScript$1, reason: invalid class name */
    /* JADX INFO: compiled from: PythonCommandManager.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "output", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function1<String, Unit> {
        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(String str) {
            invoke2(str);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(final String output) {
            Intrinsics.checkNotNullParameter(output, "output");
            Handler handler = PythonCommandManager.this.mainThreadHandler;
            final PythonCommandManager pythonCommandManager = PythonCommandManager.this;
            handler.post(new Runnable() { // from class: org.catrobat.catroid.python.PythonCommandManager$executePythonScript$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PythonCommandManager.AnonymousClass1.invoke$lambda$0(pythonCommandManager, output);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(PythonCommandManager this$0, String output) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(output, "$output");
            CommandOutputListener outputListener = this$0.getOutputListener();
            if (outputListener != null) {
                outputListener.onOutput(output);
            }
            CommandOutputListener outputListener2 = this$0.getOutputListener();
            if (outputListener2 != null) {
                outputListener2.onComplete();
            }
        }
    }
}
