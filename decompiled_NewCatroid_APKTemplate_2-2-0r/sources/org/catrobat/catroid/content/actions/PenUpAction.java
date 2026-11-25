package org.catrobat.catroid.content.actions;

import android.graphics.PointF;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PenUpAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PenUpAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        if (this.sprite.penConfiguration.isPenDown()) {
            this.sprite.penConfiguration.setPenDown(false);
            this.sprite.penConfiguration.addPosition(new PointF(this.sprite.look.getXInUserInterfaceDimensionUnit(), this.sprite.look.getYInUserInterfaceDimensionUnit()));
            this.sprite.penConfiguration.incrementQueuesToFinish();
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
