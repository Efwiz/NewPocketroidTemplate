package org.catrobat.catroid.raptor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/KeyframeComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class KeyframeComponent implements Component {
    public List<KeyframeData> keyframes = new ArrayList();
    public boolean autoStart = false;
    public boolean looping = false;
    public transient boolean isPlaying = false;
    public transient float currentTime = 0.0f;

    public void sortKeyframes() {
        synchronized (this.keyframes) {
            this.keyframes.sort(Comparator.comparingDouble(new ToDoubleFunction() { // from class: org.catrobat.catroid.raptor.KeyframeComponent$$ExternalSyntheticLambda0
                @Override // java.util.function.ToDoubleFunction
                public final double applyAsDouble(Object obj) {
                    return ((KeyframeData) obj).time;
                }
            }));
        }
    }

    public float getDuration() {
        if (this.keyframes.isEmpty()) {
            return 0.0f;
        }
        List<KeyframeData> list = this.keyframes;
        return list.get(list.size() - 1).time;
    }
}
