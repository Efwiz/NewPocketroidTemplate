package org.catrobat.catroid.content.actions;

import kotlin.Metadata;
import org.catrobat.catroid.utils.LoopUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LoopAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LoopAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\nH\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0094\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/actions/LoopAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/RepeatAction;", "()V", "currentTime", "", "getCurrentTime", "()F", "setCurrentTime", "(F)V", "isLoopDelay", "", "()Z", "setLoopDelay", "(Z)V", "isLoopDelayNeeded", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class LoopAction extends com.badlogic.gdx.scenes.scene2d.actions.RepeatAction {
    private static final float LOOP_DELAY = 0.02f;
    private float currentTime;
    private boolean isLoopDelay = true;

    /* JADX INFO: renamed from: isLoopDelay, reason: from getter */
    public final boolean getIsLoopDelay() {
        return this.isLoopDelay;
    }

    public final void setLoopDelay(boolean z) {
        this.isLoopDelay = z;
    }

    protected float getCurrentTime() {
        return this.currentTime;
    }

    protected void setCurrentTime(float f) {
        this.currentTime = f;
    }

    protected final boolean isLoopDelayNeeded() {
        return getCurrentTime() < LOOP_DELAY && this.isLoopDelay && !LoopUtil.isAnyStitchRunning();
    }
}
