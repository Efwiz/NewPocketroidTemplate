package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Color;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/FogComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FogComponent implements Component {
    public boolean isEnabled = true;
    public FogType type = FogType.EXPONENTIAL_SQUARED;
    public Color color = new Color(0.5f, 0.6f, 0.7f, 1.0f);
    public float density = 0.02f;
    public float startDistance = 20.0f;
    public float endDistance = 100.0f;
    public float heightFalloff = 0.1f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/FogComponent$FogType.class
      classes14.dex
     */
    public enum FogType {
        NONE,
        LINEAR,
        EXPONENTIAL,
        EXPONENTIAL_SQUARED
    }
}
