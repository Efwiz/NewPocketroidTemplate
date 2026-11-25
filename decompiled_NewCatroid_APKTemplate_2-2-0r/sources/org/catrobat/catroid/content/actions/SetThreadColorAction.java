package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetThreadColorAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetThreadColorAction extends TemporalAction {
    private Formula color;
    private Scope scope;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            String strInterpretString = "0xff0000";
            Formula formula = this.color;
            if (formula != null) {
                strInterpretString = formula.interpretString(this.scope);
            }
            String str = PsiLiteralUtil.HEX_PREFIX + strInterpretString.substring(1, 3);
            String str2 = PsiLiteralUtil.HEX_PREFIX + strInterpretString.substring(3, 5);
            String str3 = PsiLiteralUtil.HEX_PREFIX + strInterpretString.substring(5, 7);
            int iIntValue = Integer.decode(str).intValue();
            int iIntValue2 = Integer.decode(str2).intValue();
            int iIntValue3 = Integer.decode(str3).intValue();
            Color color = new Color();
            Color.argb8888ToColor(color, android.graphics.Color.argb(255, iIntValue, iIntValue2, iIntValue3));
            this.sprite.setEmbroideryThreadColor(color);
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setColor(Formula color) {
        this.color = color;
    }
}
