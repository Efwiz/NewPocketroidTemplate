package org.catrobat.catroid.raptor;

import java.util.ArrayList;
import java.util.List;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/PostProcessingComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PostProcessingComponent implements Component {
    public List<PostProcessingData> effects;
    public boolean isActive = true;
    public float qualityScale = 0.25f;

    public PostProcessingComponent() {
        ArrayList arrayList = new ArrayList();
        this.effects = arrayList;
        arrayList.add(new PostProcessingData.Bloom());
        this.effects.add(new PostProcessingData.Vignette());
        this.effects.add(new PostProcessingData.Levels());
        this.effects.add(new PostProcessingData.Fxaa());
    }

    public boolean hasEffect(Class<? extends PostProcessingData> effectClass) {
        List<PostProcessingData> list = this.effects;
        if (list == null) {
            return false;
        }
        for (PostProcessingData postProcessingData : list) {
            if (effectClass.isInstance(postProcessingData) && postProcessingData.isEnabled) {
                return true;
            }
        }
        return false;
    }
}
