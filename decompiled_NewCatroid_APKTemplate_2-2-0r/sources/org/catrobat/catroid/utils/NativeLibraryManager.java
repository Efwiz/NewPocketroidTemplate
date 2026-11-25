package org.catrobat.catroid.utils;

import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NativeLibraryManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeLibraryManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/utils/NativeLibraryManager;", "", "()V", "TAG", "", "featureStatus", "", "Lorg/catrobat/catroid/utils/NativeLibraryManager$Feature;", "", "initialize", "", "isLoaded", ConfigConstants.CONFIG_FEATURE_SECTION, "Feature", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeLibraryManager {
    private static final String TAG = "NativeLibManager";
    public static final NativeLibraryManager INSTANCE = new NativeLibraryManager();
    private static final Map<Feature, Boolean> featureStatus = new LinkedHashMap();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NativeLibraryManager$Feature.class
      classes14.dex
     */
    /* JADX INFO: compiled from: NativeLibraryManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/utils/NativeLibraryManager$Feature;", "", "(Ljava/lang/String;I)V", "CORE", "PYTHON", "VNC", "ONNX", "TORCH", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Feature {
        CORE,
        PYTHON,
        VNC,
        ONNX,
        TORCH;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Feature> getEntries() {
            return $ENTRIES;
        }
    }

    private NativeLibraryManager() {
    }

    public final void initialize() {
        boolean z;
        boolean z2;
        boolean z3;
        Log.i(TAG, "Initializing native libraries...");
        Map<Feature, Boolean> map = featureStatus;
        Feature feature = Feature.PYTHON;
        boolean z4 = true;
        try {
            System.loadLibrary("crypto");
            System.loadLibrary("ssl");
            System.loadLibrary(CompressorStreamFactory.Z);
            System.loadLibrary("expat");
            System.loadLibrary("openblas");
            System.loadLibrary("jpeg");
            System.loadLibrary("png");
            System.loadLibrary("python3.12");
            Log.d(TAG, "All Python libraries loaded.");
            z = true;
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "A Python-related library failed to load. Python feature disabled.", e);
            z = false;
        }
        map.put(feature, Boolean.valueOf(z));
        Map<Feature, Boolean> map2 = featureStatus;
        Feature feature2 = Feature.TORCH;
        try {
            System.loadLibrary("MNN");
            Log.d(TAG, "LibTorch Loaded.");
            z2 = true;
        } catch (UnsatisfiedLinkError unused) {
            Log.e(TAG, "LibTorch not loaded (error)");
            z2 = false;
        }
        map2.put(feature2, Boolean.valueOf(z2));
        Map<Feature, Boolean> map3 = featureStatus;
        Feature feature3 = Feature.CORE;
        try {
            System.loadLibrary(Constants.FLAVOR_POCKET_CODE);
            Log.d(TAG, "'catroid' loaded.");
            z3 = true;
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "Failed to load 'catroid' library!", e2);
            z3 = false;
        }
        map3.put(feature3, Boolean.valueOf(z3));
        Map<Feature, Boolean> map4 = featureStatus;
        Feature feature4 = Feature.VNC;
        try {
            System.loadLibrary("native-vnc");
            Log.d(TAG, "'native-vnc' loaded.");
        } catch (UnsatisfiedLinkError e3) {
            Log.e(TAG, "Failed to load 'native-vnc' library!", e3);
            z4 = false;
        }
        map4.put(feature4, Boolean.valueOf(z4));
        featureStatus.put(Feature.ONNX, Boolean.valueOf(isLoaded(Feature.CORE)));
    }

    public final boolean isLoaded(Feature feature) {
        Intrinsics.checkNotNullParameter(feature, "feature");
        Boolean bool = featureStatus.get(feature);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }
}
