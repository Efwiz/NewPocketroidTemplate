package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/LightComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LightComponent implements Component {
    public LightType type = LightType.POINT;
    public Color color = new Color(Color.WHITE);
    public float intensity = 100.0f;
    public Vector3 direction = new Vector3(0.0f, -1.0f, 0.0f);
    public float cutoffAngle = 30.0f;
    public float exponent = 1.0f;
    public float range = -1.0f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/LightComponent$LightType.class
      classes14.dex
     */
    public enum LightType {
        DIRECTIONAL,
        POINT,
        SPOT
    }
}
