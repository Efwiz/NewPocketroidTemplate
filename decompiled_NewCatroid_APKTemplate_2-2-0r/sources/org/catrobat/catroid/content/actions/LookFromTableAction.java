package org.catrobat.catroid.content.actions;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.TableManager;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/LookFromTableAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: LookFromTableAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0019H\u0002J\u0010\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!H\u0014J\b\u0010\"\u001a\u00020\u001dH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/LookFromTableAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "a_table", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getA_table", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setA_table", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "b_table", "getB_table", "setB_table", "g_table", "getG_table", "setG_table", "r_table", "getR_table", "setR_table", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "saveBitmapToTempFile", "Ljava/io/File;", "bitmap", "Landroid/graphics/Bitmap;", "setLookFromFile", "", "file", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookFromTableAction extends TemporalAction {
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
        Scope scope;
        Integer numValueOf;
        Integer numValueOf2;
        if (percent >= 1.0f && (scope = this.scope) != null) {
            Formula formula = this.r_table;
            String strInterpretString = formula != null ? formula.interpretString(scope) : null;
            if (strInterpretString == null) {
                strInterpretString = "rTable";
            }
            Formula formula2 = this.g_table;
            String strInterpretString2 = formula2 != null ? formula2.interpretString(scope) : null;
            if (strInterpretString2 == null) {
                strInterpretString2 = "gTable";
            }
            Formula formula3 = this.b_table;
            String strInterpretString3 = formula3 != null ? formula3.interpretString(scope) : null;
            if (strInterpretString3 == null) {
                strInterpretString3 = "bTable";
            }
            Formula formula4 = this.a_table;
            String strInterpretString4 = formula4 != null ? formula4.interpretString(scope) : null;
            if (strInterpretString4 == null) {
                strInterpretString4 = "aTable";
            }
            Map<Pair<Integer, Integer>, Object> table = TableManager.INSTANCE.getTable(strInterpretString);
            if (table == null) {
                return;
            }
            Map<Pair<Integer, Integer>, Object> table2 = TableManager.INSTANCE.getTable(strInterpretString2);
            Map<Pair<Integer, Integer>, Object> table3 = TableManager.INSTANCE.getTable(strInterpretString3);
            Map<Pair<Integer, Integer>, Object> table4 = TableManager.INSTANCE.getTable(strInterpretString4);
            Iterator<T> it = table.keySet().iterator();
            if (it.hasNext()) {
                numValueOf = Integer.valueOf(((Number) ((Pair) it.next()).getFirst()).intValue());
                while (it.hasNext()) {
                    Integer numValueOf3 = Integer.valueOf(((Number) ((Pair) it.next()).getFirst()).intValue());
                    if (numValueOf.compareTo(numValueOf3) < 0) {
                        numValueOf = numValueOf3;
                    }
                }
            } else {
                numValueOf = null;
            }
            Integer num = numValueOf;
            int iIntValue = num != null ? num.intValue() : 0;
            Iterator<T> it2 = table.keySet().iterator();
            if (it2.hasNext()) {
                numValueOf2 = Integer.valueOf(((Number) ((Pair) it2.next()).getSecond()).intValue());
                while (it2.hasNext()) {
                    Integer numValueOf4 = Integer.valueOf(((Number) ((Pair) it2.next()).getSecond()).intValue());
                    if (numValueOf2.compareTo(numValueOf4) < 0) {
                        numValueOf2 = numValueOf4;
                    }
                }
            } else {
                numValueOf2 = null;
            }
            Integer num2 = numValueOf2;
            int iIntValue2 = num2 != null ? num2.intValue() : 0;
            if (iIntValue == 0 || iIntValue2 == 0) {
                return;
            }
            int[] iArr = new int[iIntValue * iIntValue2];
            if (1 <= iIntValue2) {
                int i = 1;
                while (true) {
                    if (1 <= iIntValue) {
                        int i2 = 1;
                        while (true) {
                            Pair pair = new Pair(Integer.valueOf(i2), Integer.valueOf(i));
                            Object obj = table.get(pair);
                            Number number = obj instanceof Number ? (Number) obj : null;
                            int iIntValue3 = number != null ? number.intValue() : 0;
                            Object obj2 = table2 != null ? table2.get(pair) : null;
                            Number number2 = obj2 instanceof Number ? (Number) obj2 : null;
                            int iIntValue4 = number2 != null ? number2.intValue() : 0;
                            Object obj3 = table3 != null ? table3.get(pair) : null;
                            Number number3 = obj3 instanceof Number ? (Number) obj3 : null;
                            int iIntValue5 = number3 != null ? number3.intValue() : 0;
                            Object obj4 = table4 != null ? table4.get(pair) : null;
                            Number number4 = obj4 instanceof Number ? (Number) obj4 : null;
                            iArr[((i - 1) * iIntValue) + (i2 - 1)] = Color.argb(number4 != null ? number4.intValue() : 255, iIntValue3, iIntValue4, iIntValue5);
                            if (i2 == iIntValue) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                    if (i == iIntValue2) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iIntValue, iIntValue2, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
            bitmapCreateBitmap.setPixels(iArr, 0, iIntValue, 0, 0, iIntValue, iIntValue2);
            setLookFromFile(saveBitmapToTempFile(bitmapCreateBitmap));
            bitmapCreateBitmap.recycle();
        }
    }

    private final File saveBitmapToTempFile(Bitmap bitmap) {
        File file = new File(CatroidApplication.getAppContext().getCacheDir(), "generated_" + System.nanoTime() + Constants.DEFAULT_IMAGE_EXTENSION);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                CloseableKt.closeFinally(fileOutputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private final void setLookFromFile(File file) {
        Sprite sprite;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null) {
            return;
        }
        LookData lookData = new LookData(file.getName(), file);
        lookData.getCollisionInformation().calculate();
        updateLookListIndex();
        sprite.look.setLookData(lookData);
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        List<LookData> lookList;
        Sprite sprite2;
        Look look;
        Sprite sprite3;
        Scope scope = this.scope;
        LookData lookData = null;
        Look look2 = (scope == null || (sprite3 = scope.getSprite()) == null) ? null : sprite3.look;
        if (look2 != null) {
            int iIndexOf = -1;
            if (look2.getLookListIndexBeforeLookRequest() <= -1) {
                Scope scope2 = this.scope;
                if (scope2 != null && (sprite = scope2.getSprite()) != null && (lookList = sprite.getLookList()) != null) {
                    Scope scope3 = this.scope;
                    if (scope3 != null && (sprite2 = scope3.getSprite()) != null && (look = sprite2.look) != null) {
                        lookData = look.getLookData();
                    }
                    iIndexOf = lookList.indexOf(lookData);
                }
                look2.setLookListIndexBeforeLookRequest(iIndexOf);
            }
        }
    }
}
