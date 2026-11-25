package org.catrobat.catroid.camera.mlkitdetectors;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.objects.DetectedObject;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/camera/mlkitdetectors/ObjectDetectorOnSuccessListener.class
  classes12.dex
 */
/* JADX INFO: compiled from: ObjectDetector.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/camera/mlkitdetectors/ObjectDetectorOnSuccessListener;", "Lcom/google/android/gms/tasks/OnSuccessListener;", "", "Lcom/google/mlkit/vision/objects/DetectedObject;", "()V", "onSuccess", "", "detectedObjects", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectDetectorOnSuccessListener implements OnSuccessListener<List<DetectedObject>> {
    @Override // com.google.android.gms.tasks.OnSuccessListener
    public void onSuccess(List<DetectedObject> detectedObjects) {
        Intrinsics.checkNotNullParameter(detectedObjects, "detectedObjects");
        ObjectDetectorResults objectDetectorResults = ObjectDetectorResults.INSTANCE;
        List<DetectedObject> list = detectedObjects;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (DetectedObject detectedObject : list) {
            arrayList.add(TuplesKt.to(detectedObject.getTrackingId(), detectedObject));
        }
        objectDetectorResults.setResult(MapsKt.toMap(arrayList));
    }
}
