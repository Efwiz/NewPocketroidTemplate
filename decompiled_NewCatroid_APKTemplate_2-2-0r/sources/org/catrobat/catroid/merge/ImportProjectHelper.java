package org.catrobat.catroid.merge;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BroadcastMessageContainer;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/merge/ImportProjectHelper.class
  classes14.dex
 */
/* JADX INFO: compiled from: ImportProjectHelper.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0010J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\fJ\u0006\u0010\u0014\u001a\u00020\u0015J\u0010\u0010\u0016\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0017\u001a\u00020\u0003J\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003J\"\u0010\u001a\u001a\u00020\u000e2\u001a\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u001cj\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\u001dR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/merge/ImportProjectHelper;", "", "lookFileName", "", "currentScene", "Lorg/catrobat/catroid/content/Scene;", "context", "Landroid/app/Activity;", "(Ljava/lang/String;Lorg/catrobat/catroid/content/Scene;Landroid/app/Activity;)V", "newProject", "Lorg/catrobat/catroid/content/Project;", "spriteToAdd", "Lorg/catrobat/catroid/content/Sprite;", "addGlobalsToProject", "", "globalList", "", "globalsToAdd", "addObjectDataToNewSprite", "spriteToAddTo", "checkForConflicts", "", "getNewProject", "resolvedName", "getProject", "getSpriteToAddName", "rejectImportDialog", "conflicts", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportProjectHelper {
    private Activity context;
    private Scene currentScene;
    private String lookFileName;
    private Project newProject;
    private Sprite spriteToAdd;

    public ImportProjectHelper(String lookFileName, Scene currentScene, Activity context) {
        Intrinsics.checkNotNullParameter(lookFileName, "lookFileName");
        Intrinsics.checkNotNullParameter(currentScene, "currentScene");
        Intrinsics.checkNotNullParameter(context, "context");
        this.lookFileName = lookFileName;
        this.context = context;
        this.currentScene = currentScene;
        String sanitizedFileName = StorageOperations.getSanitizedFileName(lookFileName);
        Intrinsics.checkNotNull(sanitizedFileName);
        Project project = getProject(sanitizedFileName);
        Scene defaultScene = project != null ? project.getDefaultScene() : null;
        if (project != null) {
            Intrinsics.checkNotNull(defaultScene);
            if (defaultScene.getSpriteList().size() >= 2) {
                this.newProject = project;
                this.spriteToAdd = defaultScene.getSpriteList().get(1);
                return;
            }
        }
        rejectImportDialog(null);
    }

    public final String getSpriteToAddName() {
        Sprite sprite = this.spriteToAdd;
        if (sprite != null) {
            return sprite.getName();
        }
        return null;
    }

    public final Sprite addObjectDataToNewSprite(Sprite spriteToAddTo) {
        Sprite sprite;
        Project project;
        BroadcastMessageContainer broadcastMessageContainer;
        Scene scene;
        Project project2;
        List<UserVariable> userVariables;
        Project project3;
        Scene scene2;
        Project project4;
        List<UserList> userLists;
        Project project5;
        if (spriteToAddTo == null) {
            sprite = new Sprite(this.spriteToAdd, this.currentScene);
        } else {
            spriteToAddTo.mergeSprites(this.spriteToAdd, this.currentScene);
            sprite = null;
        }
        Project project6 = this.newProject;
        if (project6 != null) {
            for (UserList userList : project6.getUserLists()) {
                Scene scene3 = this.currentScene;
                List<UserList> userLists2 = (scene3 == null || (project5 = scene3.getProject()) == null) ? null : project5.getUserLists();
                Intrinsics.checkNotNull(userLists2);
                if (!userLists2.contains(userList) && (scene2 = this.currentScene) != null && (project4 = scene2.getProject()) != null && (userLists = project4.getUserLists()) != null) {
                    userLists.add(userList);
                }
            }
        }
        Project project7 = this.newProject;
        if (project7 != null) {
            for (UserVariable userVariable : project7.getUserVariables()) {
                Scene scene4 = this.currentScene;
                List<UserVariable> userVariables2 = (scene4 == null || (project3 = scene4.getProject()) == null) ? null : project3.getUserVariables();
                Intrinsics.checkNotNull(userVariables2);
                if (!userVariables2.contains(userVariable) && (scene = this.currentScene) != null && (project2 = scene.getProject()) != null && (userVariables = project2.getUserVariables()) != null) {
                    userVariables.add(userVariable);
                }
            }
        }
        Project project8 = this.newProject;
        Intrinsics.checkNotNull(project8);
        List<UserList> userLists3 = project8.getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists3, "getUserLists(...)");
        Scene scene5 = this.currentScene;
        Intrinsics.checkNotNull(scene5);
        List<UserList> userLists4 = scene5.getProject().getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists4, "getUserLists(...)");
        addGlobalsToProject(userLists3, userLists4);
        Project project9 = this.newProject;
        Intrinsics.checkNotNull(project9);
        List<UserVariable> userVariables3 = project9.getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables3, "getUserVariables(...)");
        Scene scene6 = this.currentScene;
        Intrinsics.checkNotNull(scene6);
        List<UserVariable> userVariables4 = scene6.getProject().getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables4, "getUserVariables(...)");
        addGlobalsToProject(userVariables3, userVariables4);
        Project project10 = this.newProject;
        Intrinsics.checkNotNull(project10);
        List<String> broadcastMessages = project10.getBroadcastMessageContainer().getBroadcastMessages();
        Intrinsics.checkNotNullExpressionValue(broadcastMessages, "getBroadcastMessages(...)");
        Scene scene7 = this.currentScene;
        Intrinsics.checkNotNull(scene7);
        List<String> broadcastMessages2 = scene7.getProject().getBroadcastMessageContainer().getBroadcastMessages();
        Intrinsics.checkNotNullExpressionValue(broadcastMessages2, "getBroadcastMessages(...)");
        addGlobalsToProject(broadcastMessages, broadcastMessages2);
        Scene scene8 = this.currentScene;
        if (scene8 != null && (project = scene8.getProject()) != null && (broadcastMessageContainer = project.getBroadcastMessageContainer()) != null) {
            broadcastMessageContainer.update();
        }
        return sprite;
    }

    public final void addGlobalsToProject(List<? extends Object> globalList, List<? extends Object> globalsToAdd) {
        Intrinsics.checkNotNullParameter(globalList, "globalList");
        Intrinsics.checkNotNullParameter(globalsToAdd, "globalsToAdd");
        for (Object obj : globalsToAdd) {
            if (!globalList.contains(obj)) {
                CollectionsKt.plus((Collection<? extends Object>) globalList, obj);
            }
        }
    }

    public final boolean checkForConflicts() {
        Project project;
        List<Scene> sceneList;
        Project project2;
        Project project3;
        ArrayList<String> arrayList = new ArrayList<>();
        if (this.newProject == null || this.spriteToAdd == null) {
            return false;
        }
        Scene scene = this.currentScene;
        List<UserList> userLists = (scene == null || (project3 = scene.getProject()) == null) ? null : project3.getUserLists();
        Sprite sprite = this.spriteToAdd;
        ArrayList<Object> arrayListCheckForVariablesConflicts = ProjectManager.checkForVariablesConflicts(userLists, sprite != null ? sprite.getUserLists() : null);
        Intrinsics.checkNotNullExpressionValue(arrayListCheckForVariablesConflicts, "checkForVariablesConflicts(...)");
        for (Object obj : arrayListCheckForVariablesConflicts) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserList");
            arrayList.add(((UserList) obj).getName());
        }
        Scene scene2 = this.currentScene;
        List<UserVariable> userVariables = (scene2 == null || (project2 = scene2.getProject()) == null) ? null : project2.getUserVariables();
        Sprite sprite2 = this.spriteToAdd;
        ArrayList<Object> arrayListCheckForVariablesConflicts2 = ProjectManager.checkForVariablesConflicts(userVariables, sprite2 != null ? sprite2.getUserVariables() : null);
        Intrinsics.checkNotNullExpressionValue(arrayListCheckForVariablesConflicts2, "checkForVariablesConflicts(...)");
        for (Object obj2 : arrayListCheckForVariablesConflicts2) {
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
            arrayList.add(((UserVariable) obj2).getName());
        }
        Scene scene3 = this.currentScene;
        if (scene3 != null && (project = scene3.getProject()) != null && (sceneList = project.getSceneList()) != null) {
            Iterator<T> it = sceneList.iterator();
            while (it.hasNext()) {
                List<Sprite> spriteList = ((Scene) it.next()).getSpriteList();
                if (spriteList != null) {
                    Intrinsics.checkNotNull(spriteList);
                    for (Sprite sprite3 : spriteList) {
                        Project project4 = this.newProject;
                        ArrayList<Object> arrayListCheckForVariablesConflicts3 = ProjectManager.checkForVariablesConflicts(project4 != null ? project4.getUserLists() : null, sprite3.getUserLists());
                        Intrinsics.checkNotNullExpressionValue(arrayListCheckForVariablesConflicts3, "checkForVariablesConflicts(...)");
                        for (Object obj3 : arrayListCheckForVariablesConflicts3) {
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserList");
                            arrayList.add(((UserList) obj3).getName());
                        }
                        Project project5 = this.newProject;
                        ArrayList<Object> arrayListCheckForVariablesConflicts4 = ProjectManager.checkForVariablesConflicts(project5 != null ? project5.getUserVariables() : null, sprite3.getUserVariables());
                        Intrinsics.checkNotNullExpressionValue(arrayListCheckForVariablesConflicts4, "checkForVariablesConflicts(...)");
                        for (Object obj4 : arrayListCheckForVariablesConflicts4) {
                            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type org.catrobat.catroid.formulaeditor.UserVariable");
                            arrayList.add(((UserVariable) obj4).getName());
                        }
                    }
                }
            }
        }
        if (arrayList.size() <= 0) {
            return true;
        }
        rejectImportDialog(arrayList);
        return false;
    }

    public final Project getProject(String resolvedName) {
        Intrinsics.checkNotNullParameter(resolvedName, "resolvedName");
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, resolvedName);
        if (file.exists() && file.isDirectory()) {
            return XstreamSerializer.getInstance().loadProject(file, this.context);
        }
        return getNewProject(resolvedName);
    }

    public final Project getNewProject(String resolvedName) {
        Intrinsics.checkNotNullParameter(resolvedName, "resolvedName");
        try {
            File file = new File(Constants.MEDIA_LIBRARY_CACHE_DIRECTORY, resolvedName);
            new ZipArchiver().unzip(new File(Constants.MEDIA_LIBRARY_CACHE_DIRECTORY, this.lookFileName), file);
            return XstreamSerializer.getInstance().loadProject(file, this.context);
        } catch (FileNotFoundException e) {
            Log.e(ImportProjectHelperKt.TAG, Log.getStackTraceString(e));
            rejectImportDialog(null);
            return null;
        } catch (IOException e2) {
            Log.e(ImportProjectHelperKt.TAG, Log.getStackTraceString(e2));
            rejectImportDialog(null);
            return null;
        }
    }

    public final void rejectImportDialog(final ArrayList<String> conflicts) {
        if (conflicts == null) {
            ToastUtil.showError(this.context, R.string.reject_import);
        } else {
            final View viewInflate = View.inflate(this.context, R.layout.dialog_import_rejected, null);
            this.context.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.merge.ImportProjectHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImportProjectHelper.rejectImportDialog$lambda$9(this.f$0, viewInflate, conflicts);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rejectImportDialog$lambda$9(ImportProjectHelper this$0, View view, ArrayList arrayList) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AlertDialog alertDialogCreate = new AlertDialog.Builder(new ContextThemeWrapper(this$0.context, 2131952355)).setTitle(R.string.warning).setView(view).setPositiveButton(this$0.context.getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.merge.ImportProjectHelper$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ImportProjectHelper.rejectImportDialog$lambda$9$lambda$8(dialogInterface, i);
            }
        }).setCancelable(false).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
        TextView textView = (TextView) alertDialogCreate.findViewById(R.id.conflicting_variables);
        int iMin = Math.min(arrayList.size(), 3);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        for (IndexedValue indexedValue : CollectionsKt.take(CollectionsKt.withIndex(arrayList), iMin)) {
            int length = spannableStringBuilder.length();
            if (indexedValue.getIndex() < iMin - 1) {
                spannableStringBuilder.append((CharSequence) (indexedValue.getValue() + System.lineSeparator()));
            } else {
                spannableStringBuilder.append((CharSequence) indexedValue.getValue());
            }
            spannableStringBuilder.setSpan(new BulletSpan(15), length, spannableStringBuilder.length(), 0);
        }
        if (textView == null) {
            return;
        }
        textView.setText(spannableStringBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void rejectImportDialog$lambda$9$lambda$8(DialogInterface dialog, int i) {
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        dialog.cancel();
    }
}
