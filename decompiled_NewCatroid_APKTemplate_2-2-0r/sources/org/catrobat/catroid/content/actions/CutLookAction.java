package org.catrobat.catroid.content.actions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.ErrorLog;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CutLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CutLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001bJ\u0010\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#H\u0014J\b\u0010$\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/actions/CutLookAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "hasExecuted", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "x1", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getX1", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setX1", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "x2", "getX2", "setX2", "y1", "getY1", "setY1", "y2", "getY2", "setY2", "createlook", "Lorg/catrobat/catroid/common/LookData;", "file", "Ljava/io/File;", "setLook", "", "look", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "updateLookListIndex", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CutLookAction extends TemporalAction {
    private boolean hasExecuted;
    private Scope scope;
    private Formula x1;
    private Formula x2;
    private Formula y1;
    private Formula y2;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getX1() {
        return this.x1;
    }

    public final void setX1(Formula formula) {
        this.x1 = formula;
    }

    public final Formula getY1() {
        return this.y1;
    }

    public final void setY1(Formula formula) {
        this.y1 = formula;
    }

    public final Formula getX2() {
        return this.x2;
    }

    public final void setX2(Formula formula) {
        this.x2 = formula;
    }

    public final Formula getY2() {
        return this.y2;
    }

    public final void setY2(Formula formula) {
        this.y2 = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Look look;
        if (this.hasExecuted) {
            return;
        }
        this.hasExecuted = true;
        Scope scope = this.scope;
        if (scope == null) {
            return;
        }
        Formula formula = this.x1;
        Integer numInterpretInteger = formula != null ? formula.interpretInteger(scope) : null;
        int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula2 = this.y1;
        Integer numInterpretInteger2 = formula2 != null ? formula2.interpretInteger(scope) : null;
        int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula3 = this.x2;
        Integer numInterpretInteger3 = formula3 != null ? formula3.interpretInteger(scope) : null;
        int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula4 = this.y2;
        Integer numInterpretInteger4 = formula4 != null ? formula4.interpretInteger(scope) : null;
        int iIntValue4 = numInterpretInteger4 == null ? 0 : numInterpretInteger4.intValue();
        Sprite sprite = scope.getSprite();
        LookData lookData = (sprite == null || (look = sprite.look) == null) ? null : look.getLookData();
        if (lookData == null) {
            return;
        }
        File file = lookData.getFile();
        Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
        try {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmapDecodeFile == null) {
                return;
            }
            int width = bitmapDecodeFile.getWidth();
            int height = bitmapDecodeFile.getHeight();
            int iCoerceIn = RangesKt.coerceIn(iIntValue, 0, width);
            int iCoerceIn2 = RangesKt.coerceIn(iIntValue2, 0, height);
            int iCoerceIn3 = RangesKt.coerceIn(iIntValue3, 0, width);
            int iCoerceIn4 = RangesKt.coerceIn(iIntValue4, 0, height);
            int iMin = Math.min(iCoerceIn, iCoerceIn3);
            int iMin2 = Math.min(iCoerceIn2, iCoerceIn4);
            int iAbs = Math.abs(iCoerceIn3 - iCoerceIn);
            int iAbs2 = Math.abs(iCoerceIn4 - iCoerceIn2);
            if (iAbs > 0 && iAbs2 > 0) {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeFile, iMin, iMin2, iAbs, iAbs2);
                Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
                Context appContext = CatroidApplication.getAppContext();
                File fileCreateTempFile = File.createTempFile("cropped_look_", Constants.DEFAULT_IMAGE_EXTENSION, appContext != null ? appContext.getCacheDir() : null);
                FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
                try {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    Intrinsics.checkNotNull(fileCreateTempFile);
                    setLook(createlook(fileCreateTempFile));
                    bitmapCreateBitmap.recycle();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            }
            bitmapDecodeFile.recycle();
        } catch (Exception e) {
            ErrorLog.INSTANCE.log(e.getMessage());
            e.printStackTrace();
        }
    }

    public final LookData createlook(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        LookData lookData = new LookData(file.getName(), file);
        lookData.getCollisionInformation().calculate();
        return lookData;
    }

    public final void setLook(LookData look) {
        Sprite sprite;
        Intrinsics.checkNotNullParameter(look, "look");
        updateLookListIndex();
        Scope scope = this.scope;
        Look look2 = (scope == null || (sprite = scope.getSprite()) == null) ? null : sprite.look;
        if (look2 != null) {
            look2.setLookData(look);
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
