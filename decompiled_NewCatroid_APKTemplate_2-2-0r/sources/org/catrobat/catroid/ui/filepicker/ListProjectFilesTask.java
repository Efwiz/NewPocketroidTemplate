package org.catrobat.catroid.ui.filepicker;

import android.os.AsyncTask;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/ListProjectFilesTask.class
  classes14.dex
 */
/* JADX INFO: compiled from: ListProjectFilesTask.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u0000 \u00112\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00040\u0001:\u0002\u0011\u0012B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J+\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\u0016\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\f\"\u0004\u0018\u00010\u0002H\u0014¢\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004H\u0014R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask;", "Landroid/os/AsyncTask;", "Ljava/io/File;", "Ljava/lang/Void;", "", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask$OnListProjectFilesListener;", "(Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask$OnListProjectFilesListener;)V", "weakListenerReference", "Ljava/lang/ref/WeakReference;", "doInBackground", "params", "", "([Ljava/io/File;)Ljava/util/List;", "onPostExecute", "", "files", "Companion", "OnListProjectFilesListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListProjectFilesTask extends AsyncTask<File, Void, List<? extends File>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final WeakReference<OnListProjectFilesListener> weakListenerReference;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/ListProjectFilesTask$OnListProjectFilesListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ListProjectFilesTask.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask$OnListProjectFilesListener;", "", "onListProjectFilesComplete", "", "files", "", "Ljava/io/File;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OnListProjectFilesListener {
        void onListProjectFilesComplete(List<? extends File> files);
    }

    @JvmStatic
    public static final List<File> task(File... fileArr) {
        return INSTANCE.task(fileArr);
    }

    public ListProjectFilesTask(OnListProjectFilesListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.weakListenerReference = new WeakReference<>(listener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public List<File> doInBackground(File... params) {
        Intrinsics.checkNotNullParameter(params, "params");
        return INSTANCE.task((File[]) Arrays.copyOf(params, params.length));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(List<? extends File> files) {
        Intrinsics.checkNotNullParameter(files, "files");
        OnListProjectFilesListener onListProjectFilesListener = this.weakListenerReference.get();
        if (onListProjectFilesListener != null) {
            onListProjectFilesListener.onListProjectFilesComplete(files);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/ListProjectFilesTask$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ListProjectFilesTask.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0002J\u0016\u0010\t\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u0002J'\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\r\"\u00020\u0006H\u0007¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask$Companion;", "", "()V", "findProjectFiles", "", "dir", "Ljava/io/File;", "projectFiles", "", "getAllProjectsFromPocketCodeFolder", "task", "", "startDir", "", "([Ljava/io/File;)Ljava/util/List;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final List<File> task(File... startDir) {
            Intrinsics.checkNotNullParameter(startDir, "startDir");
            ArrayList arrayList = new ArrayList();
            for (File file : startDir) {
                findProjectFiles(file, arrayList);
            }
            getAllProjectsFromPocketCodeFolder(arrayList);
            return arrayList;
        }

        private final void findProjectFiles(File dir, List<File> projectFiles) {
            File[] fileArrListFiles;
            if (!dir.canRead() || dir.listFiles() == null || (fileArrListFiles = dir.listFiles()) == null) {
                return;
            }
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    Companion companion = ListProjectFilesTask.INSTANCE;
                    Intrinsics.checkNotNull(file);
                    companion.findProjectFiles(file, projectFiles);
                }
                String name2 = file.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                if (StringsKt.endsWith$default(name2, Constants.CATROBAT_EXTENSION, false, 2, (Object) null)) {
                    Intrinsics.checkNotNull(file);
                    projectFiles.add(file);
                }
            }
        }

        private final void getAllProjectsFromPocketCodeFolder(List<File> projectFiles) {
            File[] fileArrListFiles;
            if (FlavoredConstants.EXTERNAL_STORAGE_ROOT_DIRECTORY.listFiles() == null || (fileArrListFiles = FlavoredConstants.EXTERNAL_STORAGE_ROOT_DIRECTORY.listFiles()) == null) {
                return;
            }
            for (File file : fileArrListFiles) {
                if (!Intrinsics.areEqual(file.getName(), Constants.BACKPACK_DIRECTORY_NAME) && !Intrinsics.areEqual(file.getName(), Constants.TMP_DIRECTORY_NAME) && file.isDirectory() && new File(file, Constants.CODE_XML_FILE_NAME).exists()) {
                    Intrinsics.checkNotNull(file);
                    projectFiles.add(file);
                }
            }
        }
    }
}
