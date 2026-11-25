package org.catrobat.catroid.raptor;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import org.catrobat.catroid.content.EasingFunctions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/KeyframeData.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class KeyframeData {
    public EasingFunctions.EasingType easingToNext;
    public Vector3 position;
    public Quaternion rotation;
    public Vector3 scale;
    public float time;

    public KeyframeData() {
        this.time = 0.0f;
        this.position = new Vector3();
        this.rotation = new Quaternion();
        this.scale = new Vector3(1.0f, 1.0f, 1.0f);
        this.easingToNext = EasingFunctions.EasingType.LINEAR;
    }

    public KeyframeData(KeyframeData other) {
        this.time = 0.0f;
        this.position = new Vector3();
        this.rotation = new Quaternion();
        this.scale = new Vector3(1.0f, 1.0f, 1.0f);
        this.easingToNext = EasingFunctions.EasingType.LINEAR;
        this.time = other.time;
        this.position.set(other.position);
        this.rotation.set(other.rotation);
        this.scale.set(other.scale);
        this.easingToNext = other.easingToNext;
    }
}
