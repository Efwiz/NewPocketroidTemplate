package org.catrobat.catroid.content.actions.conditional;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.eventids.BounceOffEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/conditional/IfOnEdgeBounceAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class IfOnEdgeBounceAction extends TemporalAction {
    private Sprite sprite;

    private boolean isLookingDown(float direction) {
        return direction > 90.0f || direction < -90.0f;
    }

    private boolean isLookingLeft(float direction) {
        return direction > -180.0f && direction < 0.0f;
    }

    private boolean isLookingRight(float direction) {
        return direction > 0.0f && direction < 180.0f;
    }

    private boolean isLookingUp(float direction) {
        return direction > -90.0f && direction < 90.0f;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        float widthInUserInterfaceDimensionUnit = this.sprite.look.getWidthInUserInterfaceDimensionUnit();
        float heightInUserInterfaceDimensionUnit = this.sprite.look.getHeightInUserInterfaceDimensionUnit();
        float xInUserInterfaceDimensionUnit = this.sprite.look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit = this.sprite.look.getYInUserInterfaceDimensionUnit();
        int virtualScreenWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenWidth() / 2;
        int virtualScreenHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenHeight() / 2;
        float motionDirectionInUserInterfaceDimensionUnit = this.sprite.look.getMotionDirectionInUserInterfaceDimensionUnit();
        float f = widthInUserInterfaceDimensionUnit / 2.0f;
        float f2 = (-virtualScreenWidth) + f;
        if (xInUserInterfaceDimensionUnit < f2) {
            if (isLookingLeft(motionDirectionInUserInterfaceDimensionUnit)) {
                motionDirectionInUserInterfaceDimensionUnit = -motionDirectionInUserInterfaceDimensionUnit;
                fireBounceEvent();
            }
            xInUserInterfaceDimensionUnit = f2;
        } else {
            float f3 = virtualScreenWidth - f;
            if (xInUserInterfaceDimensionUnit > f3) {
                if (isLookingRight(motionDirectionInUserInterfaceDimensionUnit)) {
                    fireBounceEvent();
                    motionDirectionInUserInterfaceDimensionUnit = -motionDirectionInUserInterfaceDimensionUnit;
                }
                xInUserInterfaceDimensionUnit = f3;
            }
        }
        float f4 = heightInUserInterfaceDimensionUnit / 2.0f;
        float f5 = (-virtualScreenHeight) + f4;
        if (yInUserInterfaceDimensionUnit < f5) {
            if (isLookingDown(motionDirectionInUserInterfaceDimensionUnit)) {
                motionDirectionInUserInterfaceDimensionUnit = 180.0f - motionDirectionInUserInterfaceDimensionUnit;
                fireBounceEvent();
            }
        } else {
            f5 = virtualScreenHeight - f4;
            if (yInUserInterfaceDimensionUnit > f5) {
                if (isLookingUp(motionDirectionInUserInterfaceDimensionUnit)) {
                    fireBounceEvent();
                    motionDirectionInUserInterfaceDimensionUnit = 180.0f - motionDirectionInUserInterfaceDimensionUnit;
                }
            }
            this.sprite.look.setMotionDirectionInUserInterfaceDimensionUnit(motionDirectionInUserInterfaceDimensionUnit);
            this.sprite.look.setPositionInUserInterfaceDimensionUnit(xInUserInterfaceDimensionUnit, yInUserInterfaceDimensionUnit);
        }
        yInUserInterfaceDimensionUnit = f5;
        this.sprite.look.setMotionDirectionInUserInterfaceDimensionUnit(motionDirectionInUserInterfaceDimensionUnit);
        this.sprite.look.setPositionInUserInterfaceDimensionUnit(xInUserInterfaceDimensionUnit, yInUserInterfaceDimensionUnit);
    }

    private void fireBounceEvent() {
        this.sprite.look.fire(new EventWrapper(new BounceOffEventId(this.sprite, null), false));
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
