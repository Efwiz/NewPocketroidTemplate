package org.catrobat.catroid.sensing;

import android.graphics.Bitmap;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.common.Conversions;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorAtXYDetection.class
  classes14.dex
 */
/* JADX INFO: compiled from: ColorAtXYDetection.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\f\u0018\u0000 /2\u00020\u0001:\u0001/B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\bH\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\"\u0010\u001a\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0002J\b\u0010\u001d\u001a\u00020\rH\u0002J\u0010\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u001fH\u0016J\u0012\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\"H\u0002J\b\u0010&\u001a\u00020\"H\u0002J\b\u0010'\u001a\u00020\"H\u0002J\b\u0010(\u001a\u00020\"H\u0002J\b\u0010)\u001a\u00020\"H\u0002J\u0010\u0010*\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\b\u0010+\u001a\u00020\u000bH\u0016J\u001a\u0010,\u001a\u00020\r2\b\u0010-\u001a\u0004\u0018\u00010$2\b\u0010.\u001a\u0004\u0018\u00010$R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorAtXYDetection;", "Lorg/catrobat/catroid/sensing/ColorDetection;", "scope", "Lorg/catrobat/catroid/content/Scope;", "stageListener", "Lorg/catrobat/catroid/stage/StageListener;", "(Lorg/catrobat/catroid/content/Scope;Lorg/catrobat/catroid/stage/StageListener;)V", "xPosition", "", "yPosition", "adjustBorderCoordinatesToPreventInvalidBitmapAccessForUsability", "", "argbColorToRGBHexString", "", TypedValues.Custom.S_COLOR, "Lcom/badlogic/gdx/graphics/Color;", "convertStageToBitmapCoordinate", "position", "surfaceViewScale", "", "bitmapToScreenRatio", "centerBitmapOffset", "createProjectionMatrix", "Lcom/badlogic/gdx/math/Matrix4;", "project", "Lorg/catrobat/catroid/content/Project;", "getHexColorStringFromBitmapAtPosition", "bitmap", "Landroid/graphics/Bitmap;", "getHexColorStringFromStagePixmap", "getLooksOfRelevantSprites", "", "Lorg/catrobat/catroid/content/Look;", "isParameterInvalid", "", "parameter", "", "isXCoordinateOnRightBorder", "isXPositionOutsideOfScreen", "isYCoordinateOnBottomBorderPortrait", "isYCoordinateOnTopBorderLandscape", "isYPositionOutsideOfScreen", "rgbaColorToRGBHexString", "setBufferParameters", "tryInterpretFunctionColorAtXY", "x", "y", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ColorAtXYDetection extends ColorDetection {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private int xPosition;
    private int yPosition;

    private final int convertStageToBitmapCoordinate(int position, float surfaceViewScale, float bitmapToScreenRatio, int centerBitmapOffset) {
        return (int) (((position / surfaceViewScale) * bitmapToScreenRatio) + centerBitmapOffset);
    }

    @JvmStatic
    public static final void disposeShared() {
        INSTANCE.disposeShared();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sensing/ColorAtXYDetection$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ColorAtXYDetection.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lorg/catrobat/catroid/sensing/ColorAtXYDetection$Companion;", "", "()V", "disposeShared", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public final void disposeShared() {
        }

        private Companion() {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColorAtXYDetection(Scope scope, StageListener stageListener) {
        super(scope, stageListener);
        Intrinsics.checkNotNullParameter(scope, "scope");
    }

    public final String tryInterpretFunctionColorAtXY(Object x, Object y) {
        setBufferParameters();
        Double dConvertArgumentToDouble = Conversions.convertArgumentToDouble(x);
        if (dConvertArgumentToDouble == null) {
            return "NaN";
        }
        double dDoubleValue = dConvertArgumentToDouble.doubleValue();
        Double dConvertArgumentToDouble2 = Conversions.convertArgumentToDouble(y);
        if (dConvertArgumentToDouble2 == null) {
            return "NaN";
        }
        double dDoubleValue2 = dConvertArgumentToDouble2.doubleValue();
        if (Double.isNaN(dDoubleValue) || Double.isNaN(dDoubleValue2) || getStageListener() == null) {
            return "NaN";
        }
        this.xPosition = MathKt.roundToInt(dDoubleValue);
        this.yPosition = MathKt.roundToInt(dDoubleValue2);
        if (StageActivity.getActiveCameraManager() != null && StageActivity.getActiveCameraManager().isCameraActive()) {
            if (isXPositionOutsideOfScreen() || isYPositionOutsideOfScreen()) {
                Color WHITE = Color.WHITE;
                Intrinsics.checkNotNullExpressionValue(WHITE, "WHITE");
                return rgbaColorToRGBHexString(WHITE);
            }
            adjustBorderCoordinatesToPreventInvalidBitmapAccessForUsability();
            callPixelCopyWithSurfaceView(getReceiveBitmapFromPixelCopy());
            return getHexColorStringFromBitmapAtPosition(getCameraBitmap(), this.xPosition, this.yPosition);
        }
        try {
            return getHexColorStringFromStagePixmap();
        } catch (Exception unused) {
            return "NaN";
        }
    }

    private final void adjustBorderCoordinatesToPreventInvalidBitmapAccessForUsability() {
        if (isXCoordinateOnRightBorder()) {
            this.xPosition--;
        }
        if (isYCoordinateOnTopBorderLandscape()) {
            this.yPosition--;
        } else if (isYCoordinateOnBottomBorderPortrait()) {
            this.yPosition++;
        }
    }

    private final boolean isXCoordinateOnRightBorder() {
        return this.xPosition == getVirtualWidth() / 2;
    }

    private final boolean isYCoordinateOnTopBorderLandscape() {
        return ProjectManager.getInstance().isCurrentProjectLandscapeMode() && this.yPosition == getVirtualHeight() / 2;
    }

    private final boolean isYCoordinateOnBottomBorderPortrait() {
        return !ProjectManager.getInstance().isCurrentProjectLandscapeMode() && this.yPosition == (-getVirtualHeight()) / 2;
    }

    private final String getHexColorStringFromStagePixmap() {
        List<Sprite> spritesFromStage;
        Pixmap pixmap;
        StageListener stageListener = getStageListener();
        if (stageListener == null || (spritesFromStage = stageListener.getSpritesFromStage()) == null) {
            return "#000000";
        }
        float f = this.xPosition;
        float f2 = this.yPosition;
        Vector2 vector2 = new Vector2();
        int size = spritesFromStage.size() - 1;
        if (size >= 0) {
            while (true) {
                int i = size - 1;
                Look look = spritesFromStage.get(size).look;
                if (look != null && look.isLookVisible() && look.isVisible()) {
                    Polygon[] currentCollisionPolygon = look.getCurrentCollisionPolygon();
                    Intrinsics.checkNotNull(currentCollisionPolygon);
                    for (Polygon polygon : currentCollisionPolygon) {
                        if (polygon.contains(f, f2)) {
                            LookData lookData = look.getLookData();
                            if (lookData != null && (pixmap = lookData.getPixmap()) != null) {
                                vector2.set(f, f2);
                                look.stageToLocalCoordinates(vector2);
                                int iRoundToInt = MathKt.roundToInt(vector2.x);
                                int height = pixmap.getHeight() - MathKt.roundToInt(vector2.y);
                                if (iRoundToInt < 0 || iRoundToInt >= pixmap.getWidth() || height < 0 || height >= pixmap.getHeight()) {
                                    break;
                                    break;
                                    break;
                                    break;
                                }
                                int pixel = pixmap.getPixel(iRoundToInt, height);
                                if ((pixel & 255) <= 10) {
                                    break;
                                }
                                String string = Integer.toString((pixel >> 24) & 255, CharsKt.checkRadix(16));
                                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                                String strPadStart = StringsKt.padStart(string, 2, ExternalAnnotationProvider.NULLABLE);
                                String string2 = Integer.toString((pixel >> 16) & 255, CharsKt.checkRadix(16));
                                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                                String strPadStart2 = StringsKt.padStart(string2, 2, ExternalAnnotationProvider.NULLABLE);
                                String string3 = Integer.toString((pixel >> 8) & 255, CharsKt.checkRadix(16));
                                Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                                return "#" + strPadStart + strPadStart2 + StringsKt.padStart(string3, 2, ExternalAnnotationProvider.NULLABLE);
                            }
                            break;
                            break;
                        }
                    }
                }
                if (i >= 0) {
                    size = i;
                }
            }
        }
        return "#000000";
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public List<Look> getLooksOfRelevantSprites() {
        StageListener stageListener = getStageListener();
        if (stageListener == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(stageListener.getSpritesFromStage());
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (((Sprite) obj).look.isLookVisible()) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((Sprite) it.next()).look);
        }
        return CollectionsKt.toMutableList((Collection) arrayList4);
    }

    private final String getHexColorStringFromBitmapAtPosition(Bitmap bitmap, int xPosition, int yPosition) {
        int pixel;
        if (bitmap == null) {
            return "NaN";
        }
        float scaleX = StageActivity.getActiveCameraManager().getPreviewView().getSurfaceView().getScaleX();
        float scaleY = StageActivity.getActiveCameraManager().getPreviewView().getSurfaceView().getScaleY();
        int iConvertStageToBitmapCoordinate = convertStageToBitmapCoordinate(yPosition, scaleY, bitmap.getWidth() / getVirtualHeight(), bitmap.getWidth() / 2);
        int iConvertStageToBitmapCoordinate2 = convertStageToBitmapCoordinate(xPosition, scaleX, bitmap.getHeight() / getVirtualWidth(), bitmap.getHeight() / 2);
        int iConvertStageToBitmapCoordinate3 = convertStageToBitmapCoordinate(xPosition, scaleX, bitmap.getWidth() / getVirtualWidth(), bitmap.getWidth() / 2);
        int iConvertStageToBitmapCoordinate4 = convertStageToBitmapCoordinate(-yPosition, scaleY, bitmap.getHeight() / getVirtualHeight(), bitmap.getHeight() / 2);
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            pixel = bitmap.getPixel(iConvertStageToBitmapCoordinate, iConvertStageToBitmapCoordinate2);
        } else {
            pixel = bitmap.getPixel(iConvertStageToBitmapCoordinate3, iConvertStageToBitmapCoordinate4);
        }
        return argbColorToRGBHexString(new Color(pixel));
    }

    private final String rgbaColorToRGBHexString(Color color) {
        String string = color.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String strSubstring = string.substring(0, 6);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return "#" + strSubstring;
    }

    private final String argbColorToRGBHexString(Color color) {
        String string = color.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String strSubstring = string.substring(2, 8);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return "#" + strSubstring;
    }

    private final boolean isXPositionOutsideOfScreen() {
        return this.xPosition > getVirtualWidth() / 2 || this.xPosition < (-getVirtualWidth()) / 2;
    }

    private final boolean isYPositionOutsideOfScreen() {
        return this.yPosition > getVirtualHeight() / 2 || this.yPosition < (-getVirtualHeight()) / 2;
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public void setBufferParameters() {
        setBufferHeight(1);
        setBufferWidth(1);
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public boolean isParameterInvalid(Object parameter) {
        return Conversions.convertArgumentToDouble(parameter) == null;
    }

    @Override // org.catrobat.catroid.sensing.ColorDetection
    public Matrix4 createProjectionMatrix(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        OrthographicCamera orthographicCamera = new OrthographicCamera(getBufferWidth(), getBufferHeight());
        createViewport(project, getBufferWidth(), getBufferHeight(), orthographicCamera).apply();
        orthographicCamera.position.set(this.xPosition, this.yPosition, 0.0f);
        orthographicCamera.rotate(-getLook().getRotation());
        orthographicCamera.update();
        Matrix4 combined = orthographicCamera.combined;
        Intrinsics.checkNotNullExpressionValue(combined, "combined");
        return combined;
    }
}
