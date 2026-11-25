package org.catrobat.catroid.formulaeditor.function;

import android.os.Handler;
import android.os.Looper;
import java.util.Map;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.formulaeditor.Functions;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.TextBlockUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/function/TextBlockFunctionProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TextBlockFunctionProvider implements FunctionProvider {
    @Override // org.catrobat.catroid.formulaeditor.function.FunctionProvider
    public void addFunctionsToMap(Map<Functions, FormulaFunction> formulaFunctions) {
        formulaFunctions.put(Functions.TEXT_BLOCK_X, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TextBlockFunctionProvider$$ExternalSyntheticLambda0
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionTextBlockX(d));
            }
        }));
        formulaFunctions.put(Functions.TEXT_BLOCK_Y, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TextBlockFunctionProvider$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionTextBlockY(d));
            }
        }));
        formulaFunctions.put(Functions.TEXT_BLOCK_SIZE, new UnaryFunction(new UnaryFunctionAction() { // from class: org.catrobat.catroid.formulaeditor.function.TextBlockFunctionProvider$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.formulaeditor.function.UnaryFunctionAction
            public final Object execute(double d) {
                return Double.valueOf(this.f$0.interpretFunctionTextBlockSize(d));
            }
        }));
    }

    public void checkTextDetectionEnabled() {
        final CameraManager activeCameraManager = StageActivity.getActiveCameraManager();
        if (activeCameraManager == null || activeCameraManager.getDetectionOn()) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: org.catrobat.catroid.formulaeditor.function.TextBlockFunctionProvider$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                activeCameraManager.startDetection();
            }
        });
    }

    public String interpretFunctionTextBlock(double argument) {
        checkTextDetectionEnabled();
        return TextBlockUtil.INSTANCE.getTextBlock((int) argument);
    }

    public String interpretFunctionTextBlockLanguage(double argument) {
        checkTextDetectionEnabled();
        return TextBlockUtil.INSTANCE.getTextBlockLanguage((int) argument);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionTextBlockX(double argument) {
        checkTextDetectionEnabled();
        return TextBlockUtil.INSTANCE.getCenterCoordinates((int) argument).x;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionTextBlockY(double argument) {
        checkTextDetectionEnabled();
        return TextBlockUtil.INSTANCE.getCenterCoordinates((int) argument).y;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double interpretFunctionTextBlockSize(double argument) {
        checkTextDetectionEnabled();
        return TextBlockUtil.INSTANCE.getSize((int) argument);
    }
}
