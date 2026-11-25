package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.physics.PhysicsLook;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PointToAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PointToAction extends TemporalAction {
    private Sprite pointedSprite;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.pointedSprite == null || !ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList().contains(this.pointedSprite)) {
            return;
        }
        float xInUserInterfaceDimensionUnit = this.sprite.look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit = this.sprite.look.getYInUserInterfaceDimensionUnit();
        float xInUserInterfaceDimensionUnit2 = this.pointedSprite.look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit2 = this.pointedSprite.look.getYInUserInterfaceDimensionUnit();
        double degrees = 90.0d;
        if (xInUserInterfaceDimensionUnit != xInUserInterfaceDimensionUnit2 || yInUserInterfaceDimensionUnit != yInUserInterfaceDimensionUnit2) {
            if (xInUserInterfaceDimensionUnit == xInUserInterfaceDimensionUnit2) {
                degrees = yInUserInterfaceDimensionUnit < yInUserInterfaceDimensionUnit2 ? 0.0d : 180.0d;
            } else if (yInUserInterfaceDimensionUnit != yInUserInterfaceDimensionUnit2) {
                degrees = 90.0d - Math.toDegrees(Math.atan2(yInUserInterfaceDimensionUnit2 - yInUserInterfaceDimensionUnit, xInUserInterfaceDimensionUnit2 - xInUserInterfaceDimensionUnit));
            } else if (xInUserInterfaceDimensionUnit >= xInUserInterfaceDimensionUnit2) {
                degrees = -90.0d;
            }
        }
        if (this.sprite.look instanceof PhysicsLook) {
            ((PhysicsLook) this.sprite.look).setFlippedByDirection((float) degrees);
        }
        this.sprite.look.setMotionDirectionInUserInterfaceDimensionUnit((float) degrees);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPointedSprite(Sprite pointedSprite) {
        this.pointedSprite = pointedSprite;
    }
}
