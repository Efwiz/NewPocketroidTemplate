package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JavaDownloadDepBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JavaDownloadDepBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;
    private boolean recursive;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.java_download_log_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_java_download_dep;
    }

    public JavaDownloadDepBrick() {
        this.recursive = true;
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_java_download_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_java_download_repos_field);
    }

    public JavaDownloadDepBrick(Formula libraryId, Formula repositories, UserVariable resultVariable, boolean recursive) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, libraryId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, repositories);
        this.userVariable = resultVariable;
        this.recursive = recursive;
    }

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula, org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        CheckBox checkBox = (CheckBox) this.view.findViewById(R.id.brick_java_download_recursive_checkbox);
        if (checkBox != null) {
            checkBox.setChecked(this.recursive);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.content.bricks.JavaDownloadDepBrick$$ExternalSyntheticLambda0
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    this.f$0.m4922x518b221c(compoundButton, z);
                }
            });
        }
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-JavaDownloadDepBrick, reason: not valid java name */
    /* synthetic */ void m4922x518b221c(CompoundButton compoundButton, boolean z) {
        this.recursive = z;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDownloadDependencyAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), this.userVariable, this.recursive));
    }
}
