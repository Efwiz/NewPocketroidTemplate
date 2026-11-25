package org.catrobat.catroid.content.actions;

import android.content.Context;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.text.StringsKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ide.DependencyManager;
import org.catrobat.catroid.ide.DownloadStatus;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/DownloadDependencyAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: DownloadDependencyAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020 H\u0016J\b\u0010\"\u001a\u00020 H\u0002J\u0010\u0010#\u001a\u00020 2\b\u0010$\u001a\u0004\u0018\u00010\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\b\"\u0004\b\u0012\u0010\nR\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/DownloadDependencyAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "finished", "", "libraryId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getLibraryId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setLibraryId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "recursive", "getRecursive", "()Z", "setRecursive", "(Z)V", "repositories", "getRepositories", "setRepositories", "resultVariable", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runAsyncDownload", "setResultVariable", "variable", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DownloadDependencyAction extends Action {
    private volatile boolean finished;
    private Formula libraryId;
    private boolean recursive = true;
    private Formula repositories;
    private UserVariable resultVariable;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getLibraryId() {
        return this.libraryId;
    }

    public final void setLibraryId(Formula formula) {
        this.libraryId = formula;
    }

    public final Formula getRepositories() {
        return this.repositories;
    }

    public final void setRepositories(Formula formula) {
        this.repositories = formula;
    }

    public final boolean getRecursive() {
        return this.recursive;
    }

    public final void setRecursive(boolean z) {
        this.recursive = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncDownload();
        }
        return this.finished;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/DownloadDependencyAction$runAsyncDownload$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.content.actions.DownloadDependencyAction$runAsyncDownload$1, reason: invalid class name */
    /* JADX INFO: compiled from: DownloadDependencyAction.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class AnonymousClass1 extends Lambda implements Function0<Unit> {
        AnonymousClass1() {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() {
            ArrayList arrayListEmptyList;
            Project project;
            Context appContext = CatroidApplication.getAppContext();
            try {
                try {
                    Scope scope = DownloadDependencyAction.this.getScope();
                    File filesDir = (scope == null || (project = scope.getProject()) == null) ? null : project.getFilesDir();
                    if (filesDir != null) {
                        Formula libraryId = DownloadDependencyAction.this.getLibraryId();
                        String strInterpretString = libraryId != null ? libraryId.interpretString(DownloadDependencyAction.this.getScope()) : null;
                        String str = "";
                        String str2 = strInterpretString == null ? "" : strInterpretString;
                        Formula repositories = DownloadDependencyAction.this.getRepositories();
                        String strInterpretString2 = repositories != null ? repositories.interpretString(DownloadDependencyAction.this.getScope()) : null;
                        if (strInterpretString2 != null) {
                            str = strInterpretString2;
                        }
                        if (StringsKt.trim((CharSequence) str2).toString().length() != 0) {
                            List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{","}, false, 0, 6, (Object) null);
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
                            Iterator it = listSplit$default.iterator();
                            while (it.hasNext()) {
                                arrayList.add(StringsKt.trim((CharSequence) it.next()).toString());
                            }
                            ArrayList arrayList2 = new ArrayList();
                            for (Object obj : arrayList) {
                                if (((String) obj).length() > 0) {
                                    arrayList2.add(obj);
                                }
                            }
                            ArrayList arrayList3 = arrayList2;
                            DependencyManager dependencyManager = DependencyManager.INSTANCE;
                            Intrinsics.checkNotNull(appContext);
                            String absolutePath = filesDir.getAbsolutePath();
                            Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                            if (dependencyManager.downloadLibraryRecursive(appContext, absolutePath, str2, arrayList3, DownloadDependencyAction.this.getRecursive(), new Function2<String, DownloadStatus, Unit>() { // from class: org.catrobat.catroid.content.actions.DownloadDependencyAction$runAsyncDownload$1$success$1
                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                public final void invoke2(String str3, DownloadStatus downloadStatus) {
                                    Intrinsics.checkNotNullParameter(str3, "<anonymous parameter 0>");
                                    Intrinsics.checkNotNullParameter(downloadStatus, "<anonymous parameter 1>");
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public /* bridge */ /* synthetic */ Unit invoke(String str3, DownloadStatus downloadStatus) {
                                    invoke2(str3, downloadStatus);
                                    return Unit.INSTANCE;
                                }
                            })) {
                                File[] fileArrListFiles = new File(filesDir, "libs").listFiles();
                                if (fileArrListFiles != null) {
                                    ArrayList arrayList4 = new ArrayList();
                                    for (File file : fileArrListFiles) {
                                        if (file.isFile()) {
                                            Intrinsics.checkNotNull(file);
                                            if (Intrinsics.areEqual(FilesKt.getExtension(file), "jar")) {
                                                arrayList4.add(file);
                                            }
                                        }
                                    }
                                    ArrayList arrayList5 = arrayList4;
                                    ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
                                    Iterator it2 = arrayList5.iterator();
                                    while (it2.hasNext()) {
                                        arrayList6.add(((File) it2.next()).getName());
                                    }
                                    arrayListEmptyList = arrayList6;
                                } else {
                                    arrayListEmptyList = CollectionsKt.emptyList();
                                }
                                final String strJoinToString$default = CollectionsKt.joinToString$default(arrayListEmptyList, ",", null, null, 0, null, null, 62, null);
                                Application application = Gdx.f82app;
                                final DownloadDependencyAction downloadDependencyAction = DownloadDependencyAction.this;
                                application.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.DownloadDependencyAction$runAsyncDownload$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DownloadDependencyAction.AnonymousClass1.invoke$lambda$4(downloadDependencyAction, strJoinToString$default);
                                    }
                                });
                            } else {
                                Application application2 = Gdx.f82app;
                                final DownloadDependencyAction downloadDependencyAction2 = DownloadDependencyAction.this;
                                application2.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.DownloadDependencyAction$runAsyncDownload$1$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        DownloadDependencyAction.AnonymousClass1.invoke$lambda$5(downloadDependencyAction2);
                                    }
                                });
                            }
                            DownloadDependencyAction.this.finished = true;
                            return;
                        }
                        DownloadDependencyAction.this.finished = true;
                    }
                    DownloadDependencyAction.this.finished = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    Application application3 = Gdx.f82app;
                    final DownloadDependencyAction downloadDependencyAction3 = DownloadDependencyAction.this;
                    application3.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.DownloadDependencyAction$runAsyncDownload$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DownloadDependencyAction.AnonymousClass1.invoke$lambda$6(downloadDependencyAction3, e);
                        }
                    });
                }
            } catch (Throwable th) {
                DownloadDependencyAction.this.finished = true;
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$4(DownloadDependencyAction this$0, String resultListStr) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(resultListStr, "$resultListStr");
            UserVariable userVariable = this$0.resultVariable;
            if (userVariable == null) {
                return;
            }
            userVariable.setValue(resultListStr);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$5(DownloadDependencyAction this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            UserVariable userVariable = this$0.resultVariable;
            if (userVariable == null) {
                return;
            }
            userVariable.setValue("ERROR_DOWNLOAD_FAILED");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$6(DownloadDependencyAction this$0, Exception e) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(e, "$e");
            UserVariable userVariable = this$0.resultVariable;
            if (userVariable == null) {
                return;
            }
            userVariable.setValue("ERROR: " + e.getLocalizedMessage());
        }
    }

    private final void runAsyncDownload() {
        ThreadsKt.thread$default(true, false, null, null, 0, new AnonymousClass1(), 30, null);
    }

    public final void setResultVariable(UserVariable variable) {
        this.resultVariable = variable;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.libraryId = null;
        this.repositories = null;
        this.resultVariable = null;
        this.recursive = true;
    }
}
