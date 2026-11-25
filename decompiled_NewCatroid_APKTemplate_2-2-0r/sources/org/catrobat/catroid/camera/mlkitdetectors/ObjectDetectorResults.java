package org.catrobat.catroid.camera.mlkitdetectors;

import com.google.mlkit.vision.objects.DetectedObject;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/ObjectDetectorResults.class
  classes12.dex
 */
/* JADX INFO: compiled from: ObjectDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002RB\u0010\u0007\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u0014\u0010\u0003\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u00048F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/ObjectDetectorResults;", "", "()V", "<set-?>", "", "", "Lcom/google/mlkit/vision/objects/DetectedObject;", "result", "getResult", "()Ljava/util/Map;", "setResult", "(Ljava/util/Map;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectDetectorResults {
    public static final ObjectDetectorResults INSTANCE = new ObjectDetectorResults();
    private static Map<Integer, ? extends DetectedObject> result = new HashMap();

    private ObjectDetectorResults() {
    }

    public final synchronized Map<Integer, DetectedObject> getResult() {
        return result;
    }

    public final synchronized void setResult(Map<Integer, ? extends DetectedObject> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        result = map;
    }
}
