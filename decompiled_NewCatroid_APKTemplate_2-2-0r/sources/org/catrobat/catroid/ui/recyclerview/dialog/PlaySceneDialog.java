package org.catrobat.catroid.ui.recyclerview.dialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/PlaySceneDialog.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class PlaySceneDialog extends AlertDialog {
    private PlaySceneDialog(Context context) {
        super(context);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/PlaySceneDialog$Builder.class
      classes14.dex
     */
    public static class Builder extends AlertDialog.Builder {
        public Builder(Context context) {
            super(context);
            final ProjectManager projectManager = ProjectManager.getInstance();
            final Scene currentlyEditedScene = projectManager.getCurrentlyEditedScene();
            final Scene defaultScene = projectManager.getCurrentProject().getDefaultScene();
            String[] strArr = {String.format(context.getString(R.string.play_scene_dialog_default), defaultScene.getName()), String.format(context.getString(R.string.play_scene_dialog_current), currentlyEditedScene.getName())};
            setTitle(R.string.play_scene_dialog_title);
            projectManager.setCurrentlyPlayingScene(defaultScene);
            projectManager.setStartScene(defaultScene);
            setSingleChoiceItems(strArr, 0, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.dialog.PlaySceneDialog.Builder.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        projectManager.setCurrentlyPlayingScene(defaultScene);
                        projectManager.setStartScene(defaultScene);
                    } else {
                        if (which != 1) {
                            return;
                        }
                        projectManager.setCurrentlyPlayingScene(currentlyEditedScene);
                        projectManager.setStartScene(currentlyEditedScene);
                    }
                }
            });
        }
    }
}
