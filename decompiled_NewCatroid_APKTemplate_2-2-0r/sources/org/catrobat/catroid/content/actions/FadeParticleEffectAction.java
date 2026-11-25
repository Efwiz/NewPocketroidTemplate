package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/FadeParticleEffectAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: FadeParticleEffectAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u000b\u001a\u00020\t2\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/actions/FadeParticleEffectAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "backgroundSprite", "Lorg/catrobat/catroid/content/Sprite;", "fadeIn", "", "sprite", "setBackgroundSprite", "", "setFadeIn", "setSprite", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FadeParticleEffectAction extends TemporalAction {
    private Sprite backgroundSprite;
    private boolean fadeIn;
    private Sprite sprite;

    public final void setFadeIn(boolean fadeIn) {
        this.fadeIn = fadeIn;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final void setBackgroundSprite(Sprite backgroundSprite) {
        this.backgroundSprite = backgroundSprite;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        List<LookData> lookList;
        Sprite sprite = this.sprite;
        Look look = sprite != null ? sprite.look : null;
        if (look != null) {
            look.hasParticleEffect = this.fadeIn;
        }
        Sprite sprite2 = this.backgroundSprite;
        if (sprite2 == null || (lookList = sprite2.getLookList()) == null || lookList.size() != 0) {
            return;
        }
        Sprite sprite3 = this.sprite;
        Look look2 = sprite3 != null ? sprite3.look : null;
        if (look2 == null) {
            return;
        }
        look2.isAdditive = false;
    }
}
