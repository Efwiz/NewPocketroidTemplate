package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.DynamicLookData;
import org.catrobat.catroid.content.RenderTexture;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ApplyBufferLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ApplyBufferLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/content/actions/ApplyBufferLookAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApplyBufferLookAction extends TemporalAction {
    private Formula nameFormula;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNameFormula() {
        return this.nameFormula;
    }

    public final void setNameFormula(Formula formula) {
        this.nameFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope;
        final Sprite sprite;
        RenderTexture renderTexture;
        Formula formula = this.nameFormula;
        final String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null || (scope = this.scope) == null || (sprite = scope.getSprite()) == null) {
            return;
        }
        LookData lookData = sprite.look.getLookData();
        if (((lookData instanceof DynamicLookData) && Intrinsics.areEqual(((DynamicLookData) lookData).getBufferName(), strInterpretString)) || (renderTexture = RenderTextureManager.INSTANCE.getRenderTextures().get(strInterpretString)) == null) {
            return;
        }
        final TextureRegion textureRegion = renderTexture.getTextureRegion();
        final int width = renderTexture.getWidth();
        final int height = renderTexture.getHeight();
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.actions.ApplyBufferLookAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ApplyBufferLookAction.update$lambda$0(strInterpretString, textureRegion, width, height, sprite);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void update$lambda$0(String name2, TextureRegion region, int i, int i2, Sprite sprite) {
        Intrinsics.checkNotNullParameter(name2, "$name");
        Intrinsics.checkNotNullParameter(region, "$region");
        Intrinsics.checkNotNullParameter(sprite, "$sprite");
        sprite.look.setLookData(new DynamicLookData(name2, region, i, i2));
    }
}
