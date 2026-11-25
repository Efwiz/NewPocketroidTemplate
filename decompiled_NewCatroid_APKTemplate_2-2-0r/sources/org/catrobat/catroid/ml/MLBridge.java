package org.catrobat.catroid.ml;

import android.util.Log;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ml/MLBridge.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MLBridge {
    public static native int nativeArgMax(String name2);

    public static native void nativeBackward(String lossVar);

    public static native void nativeCreateRandomTensor(String name2, int[] shape, boolean trainable);

    public static native void nativeCreateTensor(String name2, int[] shape, float val, boolean trainable);

    public static native String nativeGetShape(String name2);

    public static native float[] nativeGetTensor(String name2);

    public static native String nativeGetTensorAsString(String name2);

    public static native float nativeGetTensorValueByIndex(String name2, int index);

    public static native int nativeGetTotalSize(String name2);

    public static native float nativeGetValueND(String name2, String indices);

    public static native void nativeLayerLinear(String layerName, String input, String output, int inFeatures, int outFeatures);

    public static native boolean nativeLoadModel(String path);

    public static native void nativeOp(String res, String a, String b, String op);

    public static native void nativeReshape(String name2, int[] shape);

    public static native void nativeSaveModel(String path);

    public static native void nativeSetTensor(String name2, String data);

    public static native void nativeSetTensorByIndex(String name2, int index, float value);

    public static native void nativeSetTrainingMode(boolean isTrainMode);

    public static native void nativeStep(float lr);

    public static native void nativeStepAdam(float lr);

    public static void test() {
        nativeCreateTensor("x", new int[]{1, 1}, 2.0f, false);
        nativeCreateTensor("w", new int[]{1, 1}, 0.5f, true);
        nativeCreateTensor("target", new int[]{1, 1}, 10.0f, false);
        for (int i = 0; i < 50; i++) {
            nativeOp("y", "x", "w", "mul");
            nativeOp("diff", "target", "y", "sub");
            nativeOp("loss", "diff", "diff", "mul");
            nativeBackward("loss");
            nativeStep(0.01f);
            if (i % 10 == 0) {
                Log.d("ML", "Step " + i + ": Weight = " + nativeGetTensor("w")[0] + ", Loss = " + nativeGetTensor("loss")[0]);
            }
        }
    }
}
