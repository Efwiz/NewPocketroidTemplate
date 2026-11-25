package org.catrobat.catroid.utils;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.text.Text;
import com.huawei.hms.mlsdk.langdetect.MLLangDetectorFactory;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetector;
import com.huawei.hms.mlsdk.langdetect.local.MLLocalLangDetectorSetting;
import com.huawei.hms.mlsdk.text.MLText;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.camera.VisualDetectionHandler;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/TextBlockUtil.class
  classes14.dex
 */
/* JADX INFO: compiled from: TextBlockUtil.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0004J\u000e\u0010#\u001a\u00020$2\u0006\u0010\"\u001a\u00020\u0004J\u000e\u0010%\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u0004J\u000e\u0010&\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u0004J$\u0010'\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004J$\u0010.\u001a\u00020(2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020/0*2\u0006\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u001e0\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/catrobat/catroid/utils/TextBlockUtil;", "", "()V", "MAX_TEXT_SIZE", "", "TRUSTED_THRESHOLD", "", "imageHeight", "imageWidth", "languageDetectorFactoryHuawei", "Lcom/huawei/hms/mlsdk/langdetect/MLLangDetectorFactory;", "languageDetectorSettingHuawei", "Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetectorSetting;", "getLanguageDetectorSettingHuawei", "()Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetectorSetting;", "setLanguageDetectorSettingHuawei", "(Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetectorSetting;)V", "languageIdentifierGoogle", "Lcom/google/mlkit/nl/languageid/LanguageIdentifier;", "languageIdentifierHuawei", "Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetector;", "getLanguageIdentifierHuawei", "()Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetector;", "setLanguageIdentifierHuawei", "(Lcom/huawei/hms/mlsdk/langdetect/local/MLLocalLangDetector;)V", "textBlockBoundingBoxes", "", "Landroid/graphics/Rect;", "textBlockLanguages", "", "", "textBlocks", "getCenterCoordinates", "Landroid/graphics/Point;", "index", "getSize", "", "getTextBlock", "getTextBlockLanguage", "setTextBlocksGoogle", "", "text", "", "Lcom/google/mlkit/vision/text/Text$TextBlock;", "width", "height", "setTextBlocksHuawei", "Lcom/huawei/hms/mlsdk/text/MLText$Block;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TextBlockUtil {
    private static final int MAX_TEXT_SIZE = 100;
    private static final float TRUSTED_THRESHOLD = 0.01f;
    private static int imageHeight;
    private static int imageWidth;
    private static MLLangDetectorFactory languageDetectorFactoryHuawei;
    private static MLLocalLangDetectorSetting languageDetectorSettingHuawei;
    private static LanguageIdentifier languageIdentifierGoogle;
    private static MLLocalLangDetector languageIdentifierHuawei;
    public static final TextBlockUtil INSTANCE = new TextBlockUtil();
    private static List<String> textBlocks = new ArrayList();
    private static List<Rect> textBlockBoundingBoxes = new ArrayList();
    private static Map<Integer, String> textBlockLanguages = new LinkedHashMap();

    private TextBlockUtil() {
    }

    static {
        LanguageIdentifier client = LanguageIdentification.getClient();
        Intrinsics.checkNotNullExpressionValue(client, "getClient(...)");
        languageIdentifierGoogle = client;
        MLLangDetectorFactory mLLangDetectorFactory = MLLangDetectorFactory.getInstance();
        Intrinsics.checkNotNullExpressionValue(mLLangDetectorFactory, "getInstance(...)");
        languageDetectorFactoryHuawei = mLLangDetectorFactory;
        MLLocalLangDetectorSetting mLLocalLangDetectorSettingCreate = new MLLocalLangDetectorSetting.Factory().setTrustedThreshold(0.01f).create();
        Intrinsics.checkNotNullExpressionValue(mLLocalLangDetectorSettingCreate, "create(...)");
        languageDetectorSettingHuawei = mLLocalLangDetectorSettingCreate;
        MLLocalLangDetector localLangDetector = languageDetectorFactoryHuawei.getLocalLangDetector(mLLocalLangDetectorSettingCreate);
        Intrinsics.checkNotNullExpressionValue(localLangDetector, "getLocalLangDetector(...)");
        languageIdentifierHuawei = localLangDetector;
    }

    public final MLLocalLangDetectorSetting getLanguageDetectorSettingHuawei() {
        return languageDetectorSettingHuawei;
    }

    public final void setLanguageDetectorSettingHuawei(MLLocalLangDetectorSetting mLLocalLangDetectorSetting) {
        Intrinsics.checkNotNullParameter(mLLocalLangDetectorSetting, "<set-?>");
        languageDetectorSettingHuawei = mLLocalLangDetectorSetting;
    }

    public final MLLocalLangDetector getLanguageIdentifierHuawei() {
        return languageIdentifierHuawei;
    }

    public final void setLanguageIdentifierHuawei(MLLocalLangDetector mLLocalLangDetector) {
        Intrinsics.checkNotNullParameter(mLLocalLangDetector, "<set-?>");
        languageIdentifierHuawei = mLLocalLangDetector;
    }

    public final void setTextBlocksGoogle(List<? extends Text.TextBlock> text, int width, int height) {
        Intrinsics.checkNotNullParameter(text, "text");
        imageWidth = width;
        imageHeight = height;
        textBlockLanguages.clear();
        textBlockBoundingBoxes.clear();
        final int i = 0;
        for (Object obj : text) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Text.TextBlock textBlock = (Text.TextBlock) obj;
            String text2 = textBlock.getText();
            List<String> list = textBlocks;
            Intrinsics.checkNotNull(text2);
            list.add(i, text2);
            Rect boundingBox = textBlock.getBoundingBox();
            if (boundingBox != null) {
                List<Rect> list2 = textBlockBoundingBoxes;
                Intrinsics.checkNotNull(boundingBox);
                list2.add(i, boundingBox);
            }
            Task<String> taskIdentifyLanguage = languageIdentifierGoogle.identifyLanguage(textBlock.getText());
            final Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.catrobat.catroid.utils.TextBlockUtil$setTextBlocksGoogle$1$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String str) {
                    Map map = TextBlockUtil.textBlockLanguages;
                    Integer numValueOf = Integer.valueOf(i);
                    Intrinsics.checkNotNull(str);
                    map.put(numValueOf, str);
                }
            };
            taskIdentifyLanguage.addOnSuccessListener(new OnSuccessListener() { // from class: org.catrobat.catroid.utils.TextBlockUtil$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj2) {
                    TextBlockUtil.setTextBlocksGoogle$lambda$3$lambda$2(function1, obj2);
                }
            });
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setTextBlocksGoogle$lambda$3$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    public final void setTextBlocksHuawei(List<? extends MLText.Block> text, int width, int height) {
        Intrinsics.checkNotNullParameter(text, "text");
        imageWidth = width;
        imageHeight = height;
        textBlockLanguages.clear();
        textBlockBoundingBoxes.clear();
        final int i = 0;
        for (Object obj : text) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MLText.Block block = (MLText.Block) obj;
            String stringValue = block.getStringValue();
            if (stringValue != null) {
                Intrinsics.checkNotNull(stringValue);
                textBlocks.add(i, stringValue);
            }
            Rect border = block.getBorder();
            if (border != null) {
                Intrinsics.checkNotNull(border);
                textBlockBoundingBoxes.add(i, border);
            }
            languageIdentifierHuawei.firstBestDetect(block.getStringValue()).addOnSuccessListener(new com.huawei.hmf.tasks.OnSuccessListener() { // from class: org.catrobat.catroid.utils.TextBlockUtil$$ExternalSyntheticLambda1
                @Override // com.huawei.hmf.tasks.OnSuccessListener
                public final void onSuccess(Object obj2) {
                    TextBlockUtil.setTextBlocksHuawei$lambda$7$lambda$6(i, (String) obj2);
                }
            });
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setTextBlocksHuawei$lambda$7$lambda$6(int i, String str) {
        Integer numValueOf = Integer.valueOf(i);
        Map<Integer, String> map = textBlockLanguages;
        Intrinsics.checkNotNull(str);
        map.put(numValueOf, str);
    }

    public final String getTextBlock(int index) {
        String str = (String) CollectionsKt.getOrNull(textBlocks, index - 1);
        return str == null ? "0" : str;
    }

    public final String getTextBlockLanguage(int index) {
        String str = textBlockLanguages.get(Integer.valueOf(index - 1));
        return str == null ? "0" : str;
    }

    public final Point getCenterCoordinates(int index) {
        Rect rect = (Rect) CollectionsKt.getOrNull(textBlockBoundingBoxes, index - 1);
        if (rect == null) {
            return new Point(0, 0);
        }
        CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager == null) {
            return new Point(0, 0);
        }
        boolean zIsCameraFacingFront = activeCameraManager.isCameraFacingFront();
        double d = ((double) imageWidth) / ((double) imageHeight);
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            double dExactCenterX = ((double) rect.exactCenterX()) / ((double) imageWidth);
            if (zIsCameraFacingFront) {
                dExactCenterX = ((double) 1) - dExactCenterX;
            }
            return VisualDetectionHandler.INSTANCE.coordinatesFromRelativePosition(dExactCenterX, ScreenValues.currentScreenResolution.getWidth(), ((double) 1) - (((double) rect.exactCenterY()) / ((double) imageHeight)), ((double) ScreenValues.currentScreenResolution.getWidth()) / d);
        }
        double dExactCenterX2 = ((double) rect.exactCenterX()) / ((double) imageHeight);
        if (zIsCameraFacingFront) {
            dExactCenterX2 = ((double) 1) - dExactCenterX2;
        }
        return VisualDetectionHandler.INSTANCE.coordinatesFromRelativePosition(dExactCenterX2, ((double) ScreenValues.currentScreenResolution.getHeight()) / d, ((double) 1) - (((double) rect.exactCenterY()) / ((double) imageWidth)), ScreenValues.currentScreenResolution.getHeight());
    }

    public final double getSize(int index) {
        Rect rect = (Rect) CollectionsKt.getOrNull(textBlockBoundingBoxes, index - 1);
        if (rect == null) {
            return 0.0d;
        }
        float fWidth = rect.width() / imageWidth;
        if (fWidth > 1.0f) {
            fWidth = 1.0f;
        }
        return MathKt.roundToInt(100 * fWidth);
    }
}
