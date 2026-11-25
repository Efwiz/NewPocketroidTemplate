package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AsynchronousAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AsynchronousAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\u0004H&J\b\u0010\u000b\u001a\u00020\tH\u0016R\u0012\u0010\u0003\u001a\u00020\u00048\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/content/actions/AsynchronousAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "initialized", "", "act", "delta", "", "initialize", "", "isFinished", "restart", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AsynchronousAction extends Action {
    protected boolean initialized;

    public abstract void initialize();

    public abstract boolean isFinished();

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.initialized) {
            initialize();
            this.initialized = true;
        }
        return isFinished();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.initialized = false;
    }
}
