package org.catrobat.catroid.content.actions;

import android.graphics.PointF;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.plot.Plot;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StartPlotAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: StartPlotAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/content/actions/StartPlotAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "setSprite", "", ConfigConstants.CONFIG_KEY_UPDATE, "delta", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StartPlotAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        Sprite sprite = this.sprite;
        Intrinsics.checkNotNull(sprite);
        if (sprite.plot.getIsPlotting()) {
            return;
        }
        Sprite sprite2 = this.sprite;
        Intrinsics.checkNotNull(sprite2);
        Plot plot = sprite2.plot;
        Sprite sprite3 = this.sprite;
        Intrinsics.checkNotNull(sprite3);
        float xInUserInterfaceDimensionUnit = sprite3.look.getXInUserInterfaceDimensionUnit();
        Sprite sprite4 = this.sprite;
        Intrinsics.checkNotNull(sprite4);
        plot.startNewPlotLine(new PointF(xInUserInterfaceDimensionUnit, sprite4.look.getYInUserInterfaceDimensionUnit()));
        Sprite sprite5 = this.sprite;
        Intrinsics.checkNotNull(sprite5);
        sprite5.plot.resume();
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
