package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import androidx.core.view.ViewCompat;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.List;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/ShowTextActor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowTextActor extends Actor {
    private int alignment;
    private ShowTextUtils.AndroidStringProvider androidStringProvider;
    private Texture cachedTexture;
    private String colorStr;
    private float drawX;
    private float drawY;
    private boolean isText;
    private String rawText;
    private Sprite sprite;
    private float textSize;
    private Typeface typeface;
    private String variableNameToCompare;
    private UserVariable variableToShow;
    private float xPosition;
    private float yPosition;
    private String lastRenderedText = null;
    private float lastTextSize = -1.0f;
    private String lastColor = null;
    private Typeface lastTypeface = null;
    private boolean needsTextureUpdate = true;
    private boolean drawOnlyInBuffer = false;

    public ShowTextActor(Boolean isText, UserVariable userVariable, String rawText, float xPosition, float yPosition, float relativeSize, String color, Sprite sprite, int alignment, ShowTextUtils.AndroidStringProvider androidStringProvider) {
        this.isText = isText.booleanValue();
        this.variableToShow = userVariable;
        this.variableNameToCompare = userVariable != null ? userVariable.getName() : rawText;
        this.rawText = rawText;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.textSize = relativeSize * 45.0f;
        this.colorStr = color;
        this.sprite = sprite;
        this.alignment = alignment;
        this.androidStringProvider = androidStringProvider;
    }

    public String getVariableNameToCompare() {
        return this.variableNameToCompare;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setFont(Typeface typeface) {
        if (this.typeface != typeface) {
            this.typeface = typeface;
            this.needsTextureUpdate = true;
        }
    }

    public void updateProperties(String newText, float x, float y, float relativeSize, String newColor, Typeface newFont) {
        this.xPosition = x;
        this.yPosition = y;
        float f = relativeSize * 45.0f;
        if (this.textSize == f && this.colorStr.equals(newColor) && this.typeface == newFont && (!this.isText || this.rawText.equals(newText))) {
            return;
        }
        this.rawText = newText;
        this.textSize = f;
        this.colorStr = newColor;
        this.typeface = newFont;
        this.needsTextureUpdate = true;
    }

    private String getCurrentTextValue() {
        if (this.isText) {
            return this.rawText;
        }
        UserVariable userVariable = this.variableToShow;
        if (userVariable != null && userVariable.isDummy()) {
            return CatroidApplication.getAppContext().getString(R.string.no_variable_selected);
        }
        ProjectManager projectManager = ProjectManager.getInstance();
        UserVariable userVariableFindVariable = findVariable(projectManager.getCurrentProject().getUserVariables());
        if (userVariableFindVariable == null) {
            userVariableFindVariable = findVariable(projectManager.getCurrentProject().getMultiplayerVariables());
        }
        if (userVariableFindVariable == null) {
            userVariableFindVariable = findVariable(this.sprite.getUserVariables());
        }
        if (userVariableFindVariable == null || !userVariableFindVariable.getVisible()) {
            return null;
        }
        Object value = userVariableFindVariable.getValue();
        if (value instanceof Boolean) {
            return this.androidStringProvider.getTrueOrFalse((Boolean) value);
        }
        String string = value.toString();
        return ShowTextUtils.isNumberAndInteger(string) ? ShowTextUtils.getStringAsInteger(string) : string;
    }

    private UserVariable findVariable(List<UserVariable> list) {
        if (list == null) {
            return null;
        }
        for (UserVariable userVariable : list) {
            if (userVariable.getName().equals(this.variableNameToCompare)) {
                return userVariable;
            }
        }
        return null;
    }

    public boolean isDrawOnlyInBuffer() {
        return this.drawOnlyInBuffer;
    }

    public void setDrawOnlyInBuffer(boolean drawOnlyInBuffer) {
        this.drawOnlyInBuffer = drawOnlyInBuffer;
        this.needsTextureUpdate = true;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        String currentTextValue;
        if ((this.drawOnlyInBuffer && !RenderTextureManager.INSTANCE.isRenderingToBuffer()) || (currentTextValue = getCurrentTextValue()) == null || currentTextValue.isEmpty()) {
            return;
        }
        if (this.needsTextureUpdate || !currentTextValue.equals(this.lastRenderedText)) {
            generateTexture(currentTextValue);
            this.lastRenderedText = currentTextValue;
            this.needsTextureUpdate = false;
        }
        if (this.cachedTexture != null) {
            batch.setColor(1.0f, 1.0f, 1.0f, parentAlpha);
            Texture texture = this.cachedTexture;
            float f = this.xPosition;
            float f2 = this.drawX;
            float f3 = this.yPosition;
            float f4 = this.drawY;
            batch.draw(texture, f + f2, f3 + f4, -f2, -f4, texture.getWidth(), this.cachedTexture.getHeight(), 1.0f, 1.0f, getRotation(), 0, 0, this.cachedTexture.getWidth(), this.cachedTexture.getHeight(), false, false);
        }
    }

    private void generateTexture(String textToDraw) {
        Texture texture = this.cachedTexture;
        if (texture != null) {
            texture.dispose();
            this.cachedTexture = null;
        }
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(ShowTextUtils.sanitizeTextSize(this.textSize));
        Typeface typeface = this.typeface;
        if (typeface != null) {
            paint.setTypeface(typeface);
        }
        if (ShowTextUtils.isValidColorString(this.colorStr)) {
            int[] iArrCalculateColorRGBs = ShowTextUtils.calculateColorRGBs(this.colorStr.toUpperCase(Locale.getDefault()));
            paint.setColor(iArrCalculateColorRGBs[2] | (-16777216) | (iArrCalculateColorRGBs[0] << 16) | (iArrCalculateColorRGBs[1] << 8));
        } else {
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        }
        String[] strArrSplit = textToDraw.split("\n");
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f = fontMetrics.descent - fontMetrics.ascent;
        float length = strArrSplit.length * f;
        float fMax = 0.0f;
        for (String str : strArrSplit) {
            fMax = Math.max(fMax, paint.measureText(str));
        }
        if (fMax <= 0.0f || length <= 0.0f) {
            return;
        }
        int iCeil = (int) Math.ceil(fMax);
        int iCeil2 = (int) Math.ceil(length);
        if (iCeil > 2048) {
            iCeil = 2048;
        }
        if (iCeil2 > 2048) {
            iCeil2 = 2048;
        }
        try {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iCeil, iCeil2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            float f2 = -fontMetrics.ascent;
            for (String str2 : strArrSplit) {
                float fMeasureText = paint.measureText(str2);
                int i = this.alignment;
                canvas.drawText(str2, i == 1 ? (iCeil - fMeasureText) / 2.0f : i == 2 ? iCeil - fMeasureText : 0.0f, f2, paint);
                f2 += f;
            }
            Texture texture2 = new Texture(bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), Pixmap.Format.RGBA8888);
            this.cachedTexture = texture2;
            GLES20.glBindTexture(3553, texture2.getTextureObjectHandle());
            GLUtils.texImage2D(3553, 0, bitmapCreateBitmap, 0);
            GLES20.glBindTexture(3553, 0);
            bitmapCreateBitmap.recycle();
            this.drawX = 0.0f;
            int i2 = this.alignment;
            if (i2 == 1) {
                this.drawX = (-iCeil) / 2.0f;
            } else if (i2 == 2) {
                this.drawX = -iCeil;
            }
            this.drawY = (-iCeil2) / 2.0f;
        } catch (Throwable th) {
            Log.e("ShowTextActor", "Failed to generate text texture safely: size=" + iCeil + "x" + iCeil2, th);
            Texture texture3 = this.cachedTexture;
            if (texture3 != null) {
                try {
                    texture3.dispose();
                } catch (Exception unused) {
                }
                this.cachedTexture = null;
            }
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public boolean remove() {
        Texture texture = this.cachedTexture;
        if (texture != null) {
            texture.dispose();
            this.cachedTexture = null;
        }
        return super.remove();
    }
}
