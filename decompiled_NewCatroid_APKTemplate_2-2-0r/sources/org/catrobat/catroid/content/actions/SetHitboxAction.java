package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetHitboxAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetHitboxAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0004J\u0010\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0010H\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetHitboxAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "lookData", "Lorg/catrobat/catroid/common/LookData;", "getLookData", "()Lorg/catrobat/catroid/common/LookData;", "setLookData", "(Lorg/catrobat/catroid/common/LookData;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "setHitbox", "", "look", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SetHitboxAction extends TemporalAction {
    private LookData lookData;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final LookData getLookData() {
        return this.lookData;
    }

    public final void setLookData(LookData lookData) {
        this.lookData = lookData;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) throws InterruptedException {
        LookData lookData = this.lookData;
        if (lookData != null) {
            setHitbox(lookData);
        }
    }

    public final void setHitbox(LookData look) throws InterruptedException {
        Thread thread;
        Sprite sprite;
        Look look2;
        Intrinsics.checkNotNullParameter(look, "look");
        Scope scope = this.scope;
        if (scope != null && (sprite = scope.getSprite()) != null && (look2 = sprite.look) != null) {
            look2.setLookData2(look);
        }
        CollisionInformation collisionInformation = look.getCollisionInformation();
        if (collisionInformation != null && (thread = collisionInformation.collisionPolygonCalculationThread) != null) {
            thread.join();
        }
        look.setIsWebRequest(false);
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        List<LookData> lookList;
        Sprite sprite2;
        Look look;
        Sprite sprite3;
        Sprite sprite4;
        Scope scope = this.scope;
        LookData lookData = null;
        Look look2 = (scope == null || (sprite4 = scope.getSprite()) == null) ? null : sprite4.look;
        int iIndexOf = -1;
        if (look2 == null || look2.getLookListIndexBeforeLookRequest() <= -1) {
            Scope scope2 = this.scope;
            Look look3 = (scope2 == null || (sprite3 = scope2.getSprite()) == null) ? null : sprite3.look;
            if (look3 == null) {
                return;
            }
            Scope scope3 = this.scope;
            if (scope3 != null && (sprite = scope3.getSprite()) != null && (lookList = sprite.getLookList()) != null) {
                Scope scope4 = this.scope;
                if (scope4 != null && (sprite2 = scope4.getSprite()) != null && (look = sprite2.look) != null) {
                    lookData = look.getLookData();
                }
                iIndexOf = lookList.indexOf(lookData);
            }
            look3.setLookListIndexBeforeLookRequest(iIndexOf);
        }
    }
}
