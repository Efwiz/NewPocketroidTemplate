package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.EmptyScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/EmptyEventBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EmptyEventBrick extends ScriptBrickBaseType {
    private EmptyScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_empty_event;
    }

    public EmptyEventBrick() {
        this(new EmptyScript());
    }

    public EmptyEventBrick(EmptyScript script) {
        script.setScriptBrick(this);
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        EmptyEventBrick emptyEventBrick = (EmptyEventBrick) super.clone();
        EmptyScript emptyScript = (EmptyScript) this.script.clone();
        emptyEventBrick.script = emptyScript;
        emptyScript.setScriptBrick(emptyEventBrick);
        return emptyEventBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        this.view = LayoutInflater.from(context).inflate(getViewResource(), (ViewGroup) null, false);
        this.view.setAlpha(0.8f);
        BrickAdapter.colorAsCommentedOut(((ViewGroup) this.view).getChildAt(1).getBackground());
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }
}
