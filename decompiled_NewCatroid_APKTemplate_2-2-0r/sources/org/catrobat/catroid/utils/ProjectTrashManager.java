package org.catrobat.catroid.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/ProjectTrashManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectTrashManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\fH\u0007J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\fH\u0007J&\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J \u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0018\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0016\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/utils/ProjectTrashManager;", "", "()V", "DELETED_SUFFIX", "", "TAG", "TRASH_DIR_NAME", "cleanExpiredTrash", "", "context", "Landroid/content/Context;", "getProjectsDirectory", "Ljava/io/File;", "getTrashDirectory", "moveToTrash", "", ProjectUploadActivityKt.PROJECT_DIR, "restoreFromTrash", "trashProjectDir", "showDeleteMultipleProjectsDialog", "projectDirs", "", "onFinished", "Ljava/lang/Runnable;", "showDeleteProjectDialog", "showTrashBinDialog", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectTrashManager {
    private static final String DELETED_SUFFIX = "_deleted_";
    public static final ProjectTrashManager INSTANCE = new ProjectTrashManager();
    private static final String TAG = "ProjectTrashManager";
    private static final String TRASH_DIR_NAME = "projects_trash";

    private ProjectTrashManager() {
    }

    @JvmStatic
    public static final File getTrashDirectory(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, TRASH_DIR_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    @JvmStatic
    public static final File getProjectsDirectory(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File DEFAULT_ROOT_DIRECTORY = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_ROOT_DIRECTORY, "DEFAULT_ROOT_DIRECTORY");
        return DEFAULT_ROOT_DIRECTORY;
    }

    @JvmStatic
    public static final boolean moveToTrash(Context context, File projectDir) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        try {
            File file = new File(getTrashDirectory(context), projectDir.getName() + DELETED_SUFFIX + System.currentTimeMillis());
            boolean zRenameTo = projectDir.renameTo(file);
            if (zRenameTo) {
                return zRenameTo;
            }
            Log.w(TAG, "renameTo failed, using manual copy-and-delete fallback");
            StorageOperations.copyDir(projectDir, file);
            StorageOperations.deleteDir(projectDir);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to move project to trash: " + projectDir.getName(), e);
            return false;
        }
    }

    @JvmStatic
    public static final boolean restoreFromTrash(Context context, File trashProjectDir) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(trashProjectDir, "trashProjectDir");
        try {
            String name2 = trashProjectDir.getName();
            Intrinsics.checkNotNull(name2);
            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) name2, DELETED_SUFFIX, 0, false, 6, (Object) null);
            if (iLastIndexOf$default == -1) {
                return false;
            }
            String strSubstring = name2.substring(0, iLastIndexOf$default);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            File projectsDirectory = getProjectsDirectory(context);
            File file = new File(projectsDirectory, strSubstring);
            boolean z = true;
            String str = strSubstring;
            int i = 1;
            while (file.exists()) {
                str = strSubstring + " (" + i + Constant.AFTER_QUTO;
                file = new File(projectsDirectory, str);
                i++;
            }
            boolean zRenameTo = trashProjectDir.renameTo(file);
            if (zRenameTo) {
                z = zRenameTo;
            } else {
                Log.w(TAG, "renameTo failed during restore, using manual copy-and-delete fallback");
                StorageOperations.copyDir(trashProjectDir, file);
                StorageOperations.deleteDir(trashProjectDir);
            }
            if (z) {
                File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
                if (file2.exists()) {
                    try {
                        FilesKt.writeText(file2, new Regex("<projectName>.*?</projectName>").replace(FilesKt.readText(file2, Charsets.UTF_8), "<projectName>" + str + "</projectName>"), Charsets.UTF_8);
                        Log.i(TAG, "Successfully updated internal project name in XML to: " + str);
                    } catch (Exception e) {
                        Log.e(TAG, "Failed to update internal project name in XML", e);
                    }
                }
            }
            return z;
        } catch (Exception e2) {
            Log.e(TAG, "Failed to restore project from trash: " + trashProjectDir.getName(), e2);
            return false;
        }
    }

    @JvmStatic
    public static final void cleanExpiredTrash(final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        new Thread(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ProjectTrashManager.cleanExpiredTrash$lambda$0(context);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void cleanExpiredTrash$lambda$0(Context context) {
        Integer intOrNull;
        Intrinsics.checkNotNullParameter(context, "$context");
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(context).getString("pref_trash_retention_days", "14");
            long jIntValue = ((long) (((string == null || (intOrNull = StringsKt.toIntOrNull(string)) == null) ? 14 : intOrNull.intValue()) * 86400)) * 1000;
            long jCurrentTimeMillis = System.currentTimeMillis();
            File[] fileArrListFiles = getTrashDirectory(context).listFiles();
            if (fileArrListFiles == null) {
                return;
            }
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    String name2 = file.getName();
                    Intrinsics.checkNotNull(name2);
                    int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) name2, DELETED_SUFFIX, 0, false, 6, (Object) null);
                    if (iLastIndexOf$default != -1) {
                        try {
                            String strSubstring = name2.substring(iLastIndexOf$default + 9);
                            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                            if (jCurrentTimeMillis - Long.parseLong(strSubstring) > jIntValue) {
                                StorageOperations.deleteDir(file);
                                Log.i(TAG, "Permanently deleted expired trash project: " + name2);
                            }
                        } catch (Exception unused) {
                            StorageOperations.deleteDir(file);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error cleaning expired trash", e);
        }
    }

    @JvmStatic
    public static final void showDeleteProjectDialog(final Context context, final File projectDir, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectDir, "projectDir");
        Intrinsics.checkNotNullParameter(onFinished, "onFinished");
        float f = context.getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        int i = (int) (20 * f);
        linearLayout.setPadding(i, i, i, 0);
        final CheckBox checkBox = new CheckBox(context);
        checkBox.setText(context.getString(R.string.delete_permanently));
        checkBox.setChecked(false);
        linearLayout.addView(checkBox);
        new AlertDialog.Builder(context).setTitle(context.getString(R.string.delete_project_title, projectDir.getName())).setMessage(context.getString(R.string.delete_project_message, projectDir.getName())).setView(linearLayout).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                ProjectTrashManager.showDeleteProjectDialog$lambda$6(checkBox, context, projectDir, onFinished, dialogInterface, i2);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteProjectDialog$lambda$6(CheckBox deletePermanentlyCheckBox, Context context, final File projectDir, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(deletePermanentlyCheckBox, "$deletePermanentlyCheckBox");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(projectDir, "$projectDir");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        if (deletePermanentlyCheckBox.isChecked()) {
            new Thread(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showDeleteProjectDialog$lambda$6$lambda$4(projectDir, onFinished);
                }
            }).start();
        } else if (moveToTrash(context, projectDir)) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showDeleteProjectDialog$lambda$6$lambda$5(onFinished);
                }
            });
        }
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteProjectDialog$lambda$6$lambda$4(File projectDir, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(projectDir, "$projectDir");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        try {
            StorageOperations.deleteDir(projectDir);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showDeleteProjectDialog$lambda$6$lambda$4$lambda$3(onFinished);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "Failed to delete project permanently", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteProjectDialog$lambda$6$lambda$4$lambda$3(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteProjectDialog$lambda$6$lambda$5(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }

    @JvmStatic
    public static final void showTrashBinDialog(final Context context, final Runnable onFinished) {
        final ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(onFinished, "onFinished");
        File[] fileArrListFiles = getTrashDirectory(context).listFiles();
        if (fileArrListFiles == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    String name2 = file.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    if (StringsKt.contains$default((CharSequence) name2, (CharSequence) DELETED_SUFFIX, false, 2, (Object) null)) {
                        arrayList.add(file);
                    }
                }
            }
            arrayListEmptyList = arrayList;
        }
        if (arrayListEmptyList.isEmpty()) {
            new AlertDialog.Builder(context).setTitle(R.string.trash_bin_title).setMessage(R.string.trash_bin_empty).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            return;
        }
        List list = arrayListEmptyList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String name3 = ((File) it.next()).getName();
            Intrinsics.checkNotNull(name3);
            int iLastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) name3, DELETED_SUFFIX, 0, false, 6, (Object) null);
            if (iLastIndexOf$default != -1) {
                name3 = name3.substring(0, iLastIndexOf$default);
                Intrinsics.checkNotNullExpressionValue(name3, "substring(...)");
            }
            arrayList2.add(name3);
        }
        final String[] strArr = (String[]) arrayList2.toArray(new String[0]);
        new AlertDialog.Builder(context).setTitle(R.string.trash_bin_title).setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda16
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectTrashManager.showTrashBinDialog$lambda$15(arrayListEmptyList, strArr, context, onFinished, dialogInterface, i);
            }
        }).setNeutralButton(R.string.trash_bin_empty_all, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectTrashManager.showTrashBinDialog$lambda$20(context, arrayListEmptyList, onFinished, dialogInterface, i);
            }
        }).setPositiveButton(R.string.close, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15(List trashedFiles, String[] displayNames, final Context context, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(trashedFiles, "$trashedFiles");
        Intrinsics.checkNotNullParameter(displayNames, "$displayNames");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        final File file = (File) trashedFiles.get(i);
        new AlertDialog.Builder(context).setTitle(displayNames[i]).setMessage(R.string.trash_bin_item_action).setPositiveButton(R.string.trash_bin_restore, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface2, int i2) {
                ProjectTrashManager.showTrashBinDialog$lambda$15$lambda$11(context, file, onFinished, dialogInterface2, i2);
            }
        }).setNegativeButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface2, int i2) {
                ProjectTrashManager.showTrashBinDialog$lambda$15$lambda$14(file, onFinished, dialogInterface2, i2);
            }
        }).setNeutralButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15$lambda$11(Context context, File file, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        Intrinsics.checkNotNull(file);
        restoreFromTrash(context, file);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ProjectTrashManager.showTrashBinDialog$lambda$15$lambda$11$lambda$10(onFinished);
            }
        });
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15$lambda$11$lambda$10(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15$lambda$14(final File file, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        new Thread(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                ProjectTrashManager.showTrashBinDialog$lambda$15$lambda$14$lambda$13(file, onFinished);
            }
        }).start();
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15$lambda$14$lambda$13(File file, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        try {
            StorageOperations.deleteDir(file);
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showTrashBinDialog$lambda$15$lambda$14$lambda$13$lambda$12(onFinished);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "Failed to delete trashed file", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$15$lambda$14$lambda$13$lambda$12(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$20(Context context, final List trashedFiles, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(trashedFiles, "$trashedFiles");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        new AlertDialog.Builder(context).setTitle(R.string.trash_bin_empty_all_confirm_title).setMessage(R.string.trash_bin_empty_all_confirm_message).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda12
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface2, int i2) {
                ProjectTrashManager.showTrashBinDialog$lambda$20$lambda$19(trashedFiles, onFinished, dialogInterface2, i2);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$20$lambda$19(final List trashedFiles, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(trashedFiles, "$trashedFiles");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        new Thread(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                ProjectTrashManager.showTrashBinDialog$lambda$20$lambda$19$lambda$18(trashedFiles, onFinished);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$20$lambda$19$lambda$18(List trashedFiles, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(trashedFiles, "$trashedFiles");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        try {
            Iterator it = trashedFiles.iterator();
            while (it.hasNext()) {
                StorageOperations.deleteDir((File) it.next());
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showTrashBinDialog$lambda$20$lambda$19$lambda$18$lambda$17(onFinished);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "Failed to empty trash", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTrashBinDialog$lambda$20$lambda$19$lambda$18$lambda$17(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }

    @JvmStatic
    public static final void showDeleteMultipleProjectsDialog(final Context context, final List<? extends File> projectDirs, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectDirs, "projectDirs");
        Intrinsics.checkNotNullParameter(onFinished, "onFinished");
        float f = context.getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        int i = (int) (20 * f);
        linearLayout.setPadding(i, i, i, 0);
        final CheckBox checkBox = new CheckBox(context);
        checkBox.setText(context.getString(R.string.delete_permanently));
        checkBox.setChecked(false);
        linearLayout.addView(checkBox);
        int size = projectDirs.size();
        new AlertDialog.Builder(context).setTitle(context.getResources().getQuantityString(R.plurals.delete_projects, size, Integer.valueOf(size))).setMessage(context.getString(R.string.delete_project_multiple_message, Integer.valueOf(size))).setView(linearLayout).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                ProjectTrashManager.showDeleteMultipleProjectsDialog$lambda$25(projectDirs, checkBox, context, onFinished, dialogInterface, i2);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteMultipleProjectsDialog$lambda$25(final List projectDirs, final CheckBox deletePermanentlyCheckBox, final Context context, final Runnable onFinished, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(projectDirs, "$projectDirs");
        Intrinsics.checkNotNullParameter(deletePermanentlyCheckBox, "$deletePermanentlyCheckBox");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        new Thread(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ProjectTrashManager.showDeleteMultipleProjectsDialog$lambda$25$lambda$24(projectDirs, deletePermanentlyCheckBox, context, onFinished);
            }
        }).start();
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteMultipleProjectsDialog$lambda$25$lambda$24(List projectDirs, CheckBox deletePermanentlyCheckBox, Context context, final Runnable onFinished) {
        Intrinsics.checkNotNullParameter(projectDirs, "$projectDirs");
        Intrinsics.checkNotNullParameter(deletePermanentlyCheckBox, "$deletePermanentlyCheckBox");
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        try {
            Iterator it = projectDirs.iterator();
            while (it.hasNext()) {
                File file = (File) it.next();
                if (deletePermanentlyCheckBox.isChecked()) {
                    StorageOperations.deleteDir(file);
                } else {
                    moveToTrash(context, file);
                }
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.utils.ProjectTrashManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectTrashManager.showDeleteMultipleProjectsDialog$lambda$25$lambda$24$lambda$23(onFinished);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Failed to delete multiple projects", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteMultipleProjectsDialog$lambda$25$lambda$24$lambda$23(Runnable onFinished) {
        Intrinsics.checkNotNullParameter(onFinished, "$onFinished");
        onFinished.run();
    }
}
