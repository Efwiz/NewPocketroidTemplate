package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.content.bricks.brickspinner.NewOption;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SceneTransitionBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneTransitionBrick extends BrickBaseType implements BrickSpinner.OnItemSelectedListener<Scene> {
    private static final long serialVersionUID = 1;
    private String sceneForTransition;
    private transient BrickSpinner<Scene> spinner;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_scene_transition;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onEditOptionSelected(Integer spinnerId) {
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onStringOptionSelected(Integer spinnerId, String string) {
    }

    public SceneTransitionBrick() {
        this.sceneForTransition = "";
    }

    public SceneTransitionBrick(String scene) {
        this.sceneForTransition = scene;
    }

    public String getSceneForTransition() {
        return this.sceneForTransition;
    }

    public void setSceneForTransition(String sceneForTransition) {
        this.sceneForTransition = sceneForTransition;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        SceneTransitionBrick sceneTransitionBrick = (SceneTransitionBrick) super.clone();
        sceneTransitionBrick.spinner = null;
        return sceneTransitionBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(final Context context) {
        super.getView(context);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new NewOption(context.getString(R.string.new_option)));
        arrayList.addAll(ProjectManager.getInstance().getCurrentProject().getSceneList());
        arrayList.remove(ProjectManager.getInstance().getCurrentlyEditedScene());
        BrickSpinner<Scene> brickSpinner = new BrickSpinner<>(Integer.valueOf(R.id.brick_scene_transition_spinner), this.view, arrayList);
        this.spinner = brickSpinner;
        brickSpinner.setOnItemSelectedListener(this);
        this.spinner.setSelection(this.sceneForTransition);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onNewOptionSelected(Integer spinnerId) {
        final AppCompatActivity activityFromView = UiUtils.getActivityFromView(this.view);
        if (activityFromView == null) {
            return;
        }
        final Project currentProject = ProjectManager.getInstance().getCurrentProject();
        List<Scene> sceneList = currentProject.getSceneList();
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(activityFromView.getResources().getString(R.string.default_scene_name), sceneList);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(activityFromView);
        builder.setHint(activityFromView.getString(R.string.scene_name_label)).setText(uniqueNameInNameables).setTextWatcher(new DuplicateInputTextWatcher(sceneList)).setPositiveButton(activityFromView.getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.SceneTransitionBrick$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m4957x17cb1d8a(activityFromView, currentProject, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.new_scene_dialog).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.content.bricks.SceneTransitionBrick$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m4958xa4b834a9(dialogInterface, i);
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.catrobat.catroid.content.bricks.SceneTransitionBrick$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                this.f$0.m4959x31a54bc8(dialogInterface);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$onNewOptionSelected$0$org-catrobat-catroid-content-bricks-SceneTransitionBrick, reason: not valid java name */
    /* synthetic */ void m4957x17cb1d8a(AppCompatActivity appCompatActivity, Project project, DialogInterface dialogInterface, String str) {
        Scene sceneNewSceneWithBackgroundSprite = SceneController.newSceneWithBackgroundSprite(str, appCompatActivity.getString(R.string.background), project);
        project.addScene(sceneNewSceneWithBackgroundSprite);
        this.spinner.add(sceneNewSceneWithBackgroundSprite);
        this.spinner.setSelection(sceneNewSceneWithBackgroundSprite);
        notifyDataSetChanged(appCompatActivity);
    }

    /* JADX INFO: renamed from: lambda$onNewOptionSelected$1$org-catrobat-catroid-content-bricks-SceneTransitionBrick, reason: not valid java name */
    /* synthetic */ void m4958xa4b834a9(DialogInterface dialogInterface, int i) {
        this.spinner.setSelection(this.sceneForTransition);
    }

    /* JADX INFO: renamed from: lambda$onNewOptionSelected$2$org-catrobat-catroid-content-bricks-SceneTransitionBrick, reason: not valid java name */
    /* synthetic */ void m4959x31a54bc8(DialogInterface dialogInterface) {
        this.spinner.setSelection(this.sceneForTransition);
    }

    @Override // org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner.OnItemSelectedListener
    public void onItemSelected(Integer spinnerId, Scene item) {
        this.sceneForTransition = item != null ? item.getName() : null;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSceneTransitionAction(this.sceneForTransition, sprite));
    }
}
