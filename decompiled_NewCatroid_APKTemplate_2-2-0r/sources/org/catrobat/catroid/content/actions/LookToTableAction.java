package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.TableManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LookToTableAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookToTableAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/LookToTableAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "a_table", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getA_table", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setA_table", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "b_table", "getB_table", "setB_table", "g_table", "getG_table", "setG_table", "r_table", "getR_table", "setR_table", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LookToTableAction extends TemporalAction {
    private Formula a_table;
    private Formula b_table;
    private Formula g_table;
    private Formula r_table;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getR_table() {
        return this.r_table;
    }

    public final void setR_table(Formula formula) {
        this.r_table = formula;
    }

    public final Formula getG_table() {
        return this.g_table;
    }

    public final void setG_table(Formula formula) {
        this.g_table = formula;
    }

    public final Formula getB_table() {
        return this.b_table;
    }

    public final void setB_table(Formula formula) {
        this.b_table = formula;
    }

    public final Formula getA_table() {
        return this.a_table;
    }

    public final void setA_table(Formula formula) {
        this.a_table = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Scope scope = this.scope;
        if (scope != null) {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(scope.getSprite().look.getLookData().getFile().getAbsolutePath());
            Intrinsics.checkNotNullExpressionValue(bitmapDecodeFile, "decodeFile(...)");
            int width = bitmapDecodeFile.getWidth();
            int height = bitmapDecodeFile.getHeight();
            Formula formula = this.r_table;
            String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "rTable";
            } else {
                Intrinsics.checkNotNull(strInterpretString);
            }
            Formula formula2 = this.g_table;
            String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
            if (strInterpretString2 == null) {
                strInterpretString2 = "gTable";
            } else {
                Intrinsics.checkNotNull(strInterpretString2);
            }
            Formula formula3 = this.b_table;
            String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
            if (strInterpretString3 == null) {
                strInterpretString3 = "bTable";
            } else {
                Intrinsics.checkNotNull(strInterpretString3);
            }
            Formula formula4 = this.a_table;
            String strInterpretString4 = formula4 != null ? formula4.interpretString(this.scope) : null;
            if (strInterpretString4 == null) {
                strInterpretString4 = "aTable";
            } else {
                Intrinsics.checkNotNull(strInterpretString4);
            }
            TableManager.INSTANCE.deleteTable(strInterpretString);
            TableManager.INSTANCE.deleteTable(strInterpretString2);
            TableManager.INSTANCE.deleteTable(strInterpretString3);
            TableManager.INSTANCE.deleteTable(strInterpretString4);
            TableManager.INSTANCE.createTable(strInterpretString, width, height);
            TableManager.INSTANCE.createTable(strInterpretString2, width, height);
            TableManager.INSTANCE.createTable(strInterpretString3, width, height);
            TableManager.INSTANCE.createTable(strInterpretString4, width, height);
            for (int i = 0; i < width; i++) {
                int i2 = 0;
                while (i2 < height) {
                    int pixel = bitmapDecodeFile.getPixel(i, i2);
                    int iRed = Color.red(pixel);
                    int iGreen = Color.green(pixel);
                    int iBlue = Color.blue(pixel);
                    int iAlpha = Color.alpha(pixel);
                    int i3 = i + 1;
                    i2++;
                    TableManager.INSTANCE.insertElement(strInterpretString, iRed, i3, i2);
                    TableManager.INSTANCE.insertElement(strInterpretString2, iGreen, i3, i2);
                    TableManager.INSTANCE.insertElement(strInterpretString3, iBlue, i3, i2);
                    TableManager.INSTANCE.insertElement(strInterpretString4, iAlpha, i3, i2);
                }
            }
        }
    }
}
