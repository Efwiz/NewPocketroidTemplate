package org.catrobat.catroid.utils.lunoscript;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LunoDelayAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LunoDelayAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LunoDelayAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "durationInSeconds", "", "(F)V", "getDurationInSeconds", "()F", "passedTime", "act", "", "delta", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LunoDelayAction extends Action {
    private final float durationInSeconds;
    private float passedTime;

    public LunoDelayAction(float f) {
        this.durationInSeconds = f;
    }

    public final float getDurationInSeconds() {
        return this.durationInSeconds;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        float f = this.passedTime + delta;
        this.passedTime = f;
        return f >= this.durationInSeconds;
    }
}
