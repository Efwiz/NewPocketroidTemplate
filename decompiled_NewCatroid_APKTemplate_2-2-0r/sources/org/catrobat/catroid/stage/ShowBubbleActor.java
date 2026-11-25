package org.catrobat.catroid.stage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.netty.util.internal.StringUtil;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.sensing.CollisionInformation;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/ShowBubbleActor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ShowBubbleActor extends Actor {
    ArrayList<String> bubbleValue;
    private boolean drawRight = true;
    private Image image;
    private Image imageLeft;
    private Image imageRight;
    private Pixmap pixmapLeft;
    private Pixmap pixmapRight;
    private Sprite sprite;
    private Texture textureLeft;
    private Texture textureRight;
    private int type;

    public ShowBubbleActor(String text, Sprite sprite, int type) {
        this.bubbleValue = formatStringForBubbleBricks(text);
        this.sprite = sprite;
        this.type = type;
        init();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Actor
    public void draw(Batch batch, float parentAlpha) {
        switchLogic();
        getImageForDraw().draw(batch, parentAlpha);
    }

    public void close() {
        this.pixmapRight.dispose();
        this.textureRight.dispose();
        this.pixmapLeft.dispose();
        this.textureLeft.dispose();
    }

    private void init() {
        this.pixmapRight = drawBubbleOnCanvas(this.bubbleValue, true);
        this.textureRight = new Texture(this.pixmapRight);
        this.imageRight = new Image(this.textureRight);
        this.pixmapLeft = drawBubbleOnCanvas(this.bubbleValue, false);
        this.textureLeft = new Texture(this.pixmapLeft);
        this.imageLeft = new Image(this.textureLeft);
        this.image = this.imageRight;
    }

    private Image getImageForDraw() {
        LookData lookData = this.sprite.look.getLookData();
        if (lookData != null) {
            CollisionInformation collisionInformation = lookData.getCollisionInformation();
            if (collisionInformation.getLeftBubblePos() == null || collisionInformation.getRightBubblePos() == null) {
                collisionInformation.calculateBubblePositions();
            }
            Pair<Integer, Integer> rightBubblePos = collisionInformation.getRightBubblePos();
            Pair<Integer, Integer> leftBubblePos = collisionInformation.getLeftBubblePos();
            this.imageLeft.setX(calculateLeftImageX(leftBubblePos.first));
            this.imageLeft.setY(calculateImageY(leftBubblePos.second));
            this.imageRight.setX(calculateRightImageX(rightBubblePos.first));
            this.imageRight.setY(calculateImageY(rightBubblePos.second));
        } else {
            Look look = this.sprite.look;
            if (this.drawRight) {
                this.image.setX(look.getXInUserInterfaceDimensionUnit() + (look.getWidthInUserInterfaceDimensionUnit() / 2.0f));
            } else {
                this.image.setX((look.getXInUserInterfaceDimensionUnit() - (look.getWidthInUserInterfaceDimensionUnit() / 2.0f)) - this.image.getWidth());
            }
            this.image.setY(look.getYInUserInterfaceDimensionUnit() + (look.getHeightInUserInterfaceDimensionUnit() / 2.0f));
        }
        return this.image;
    }

    public float calculateRightImageX(Integer bubblePosX) {
        return this.sprite.look.getXInUserInterfaceDimensionUnit() + (bubblePosX.intValue() * this.sprite.look.getScaleX());
    }

    public float calculateLeftImageX(Integer bubblePosX) {
        return this.sprite.look.getXInUserInterfaceDimensionUnit() - (this.image.getWidth() - (bubblePosX.intValue() * this.sprite.look.getScaleX()));
    }

    public float calculateImageY(Integer bubblePosY) {
        return this.sprite.look.getYInUserInterfaceDimensionUnit() + (bubblePosY.intValue() * this.sprite.look.getScaleY());
    }

    private void switchLogic() {
        if (this.drawRight && !drawRight()) {
            this.drawRight = false;
            this.image = this.imageLeft;
        }
        if (this.drawRight || drawLeft()) {
            return;
        }
        this.drawRight = true;
        this.image = this.imageRight;
    }

    private boolean drawRight() {
        return this.imageRight.getX() + this.imageRight.getWidth() < ((float) (ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth / 2));
    }

    private boolean drawLeft() {
        return this.imageLeft.getX() > ((float) (-(ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth / 2)));
    }

    private Pixmap drawBubbleOnCanvas(ArrayList<String> lines, boolean right) {
        Paint paint = new Paint();
        paint.setTextSize(30.0f);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        ArrayList arrayList = new ArrayList();
        Rect rect = new Rect();
        int iHeight = 30;
        int iWidth = 0;
        for (String str : lines) {
            paint.getTextBounds(str, 0, str.length(), rect);
            iHeight = iHeight + 3 + rect.height();
            arrayList.add(Float.valueOf(paint.measureText(str)));
            if (iWidth < rect.width()) {
                iWidth = rect.width();
            }
        }
        int i = iWidth + 55;
        if (i < 148) {
            i = 148;
        }
        float size = (iHeight - 30) / lines.size();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, iHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        float f = i;
        RectF rectF = new RectF(0.0f, 0.0f, f, iHeight);
        canvas.drawRoundRect(rectF, 20.0f, 20.0f, paint);
        float f2 = 5;
        RectF rectF2 = new RectF(rectF.left + f2, rectF.top + f2, rectF.right - f2, rectF.bottom - f2);
        paint.setColor(-1);
        canvas.drawRoundRect(rectF2, 15.0f, 15.0f, paint);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            arrayList.set(i2, Float.valueOf((f - ((Float) arrayList.get(i2)).floatValue()) / 2.0f));
        }
        Iterator<String> it = lines.iterator();
        float f3 = 40.0f;
        int i3 = 0;
        while (it.hasNext()) {
            canvas.drawText(it.next(), ((Float) arrayList.get(i3)).floatValue(), f3, paint);
            f3 += size;
            i3++;
        }
        return getFinalBubble(i, iHeight, bitmapCreateBitmap, right);
    }

    private Pixmap getFinalBubble(int width, int height, Bitmap bitmap, boolean right) {
        Paint paint = new Paint();
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height + 40, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        if (this.type == 0) {
            canvas.drawPath(getSayTrianglePath(bitmapCreateBitmap.getHeight(), bitmapCreateBitmap.getWidth(), right), paint);
            paint.setColor(-1);
            canvas.drawPath(getSayTrianglePathSmaller(bitmapCreateBitmap.getHeight(), bitmapCreateBitmap.getWidth(), right), paint);
        } else {
            canvas.drawBitmap(getThinkBubbles(right), right ? 0 : bitmapCreateBitmap.getWidth() - 80, bitmap.getHeight(), (Paint) null);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapCreateBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return new Pixmap(byteArray, 0, byteArray.length);
    }

    private Path getSayTrianglePath(int bitmapHeight, int bitmapWidth, boolean right) {
        if (right) {
            bitmapWidth = 0;
        }
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        float f = bitmapWidth;
        float f2 = bitmapHeight;
        path.moveTo(f, f2);
        float f3 = bitmapHeight - 40;
        path.lineTo(Math.abs(bitmapWidth - 28), f3);
        path.lineTo(Math.abs(bitmapWidth - 118), f3);
        path.lineTo(f, f2);
        path.close();
        return path;
    }

    private Path getSayTrianglePathSmaller(int bitmapHeight, int bitmapWidth, boolean right) {
        if (right) {
            bitmapWidth = 0;
        }
        Path path = new Path();
        int i = bitmapWidth - 12;
        float f = bitmapHeight - 9;
        path.moveTo(Math.abs(i), f);
        float f2 = bitmapHeight - 45;
        path.lineTo(Math.abs(bitmapWidth - 37), f2);
        path.lineTo(Math.abs(bitmapWidth - 116), f2);
        path.lineTo(Math.abs(i), f);
        path.close();
        return path;
    }

    private Bitmap getThinkBubbles(boolean right) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(70, 40, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        int width = right ? bitmapCreateBitmap.getWidth() : 0;
        for (int i = 0; i <= 3; i++) {
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            int i2 = (width - (i * 15)) - 10;
            float f = i * 10;
            canvas.drawCircle(Math.abs(i2), f, 10 - i, paint);
            paint.setColor(-1);
            canvas.drawCircle(Math.abs(i2), f, 7 - i, paint);
        }
        return bitmapCreateBitmap;
    }

    public static ArrayList<String> formatStringForBubbleBricks(String text) {
        String strTrim = text.trim();
        ArrayList arrayList = new ArrayList();
        Iterator it = Arrays.asList(strTrim.split(" |((?<=\\n)|(?=\\n))")).iterator();
        while (it.hasNext()) {
            arrayList.addAll(splitLongWordIntoSubStrings((String) it.next(), 16));
        }
        return concatWordsIntoLines(arrayList, 16);
    }

    private static ArrayList<String> splitLongWordIntoSubStrings(String word, int maxLength) {
        ArrayList<String> arrayList = new ArrayList<>();
        while (word.length() > maxLength) {
            arrayList.add(word.substring(0, maxLength));
            word = word.substring(maxLength);
        }
        arrayList.add(word);
        return arrayList;
    }

    private static ArrayList<String> concatWordsIntoLines(ArrayList<String> words, int maxLineLength) {
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder sb = new StringBuilder(words.get(0));
        for (String str : words.subList(1, words.size())) {
            if (str.equals("\n")) {
                arrayList.add(sb.toString());
                sb = new StringBuilder();
            } else if (sb.length() + str.length() < maxLineLength) {
                sb.append(StringUtil.SPACE).append(str);
            } else {
                arrayList.add(sb.toString());
                sb = new StringBuilder(str);
            }
        }
        arrayList.add(sb.toString());
        return arrayList;
    }
}
