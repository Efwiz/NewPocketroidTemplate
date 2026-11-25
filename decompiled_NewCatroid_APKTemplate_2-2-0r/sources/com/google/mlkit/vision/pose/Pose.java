package com.google.mlkit.vision.pose;

import android.graphics.PointF;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzaq;
import com.google.android.gms.internal.mlkit_vision_pose_common.zzar;
import com.google.mlkit.vision.mediapipe.pose.PoseHolder;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class Pose {
    private final zzar<PoseLandmark> zza;

    public Pose(PoseHolder poseHolder) {
        PoseHolder.PoseLandmarkHolder next;
        int index;
        Preconditions.checkNotNull(poseHolder);
        zzaq zzaqVar = new zzaq();
        Iterator<PoseHolder.PoseLandmarkHolder> it = poseHolder.getPoseLandmarkHolders().iterator();
        while (it.hasNext() && (index = (next = it.next()).getIndex()) < 33) {
            zzaqVar.zza(new PoseLandmark(index, new PointF(next.getX(), next.getY()), next.getInFrameLikelihood()));
        }
        this.zza = zzaqVar.zza();
    }

    public PoseLandmark getPoseLandmark(int i) {
        if (this.zza.isEmpty()) {
            return null;
        }
        return this.zza.get(i);
    }

    public List<PoseLandmark> getAllPoseLandmarks() {
        return this.zza;
    }
}
