package org.catrobat.catroid.raptor;

import com.badlogic.gdx.utils.Array;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SceneData.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneData {
    public Array<GameObject> gameObjects = new Array<>();
    public float skyR = 0.1f;
    public float skyG = 0.2f;
    public float skyB = 0.3f;
    public String skyboxPath = null;
    public float shadowSize = 100.0f;
    public float shadowResolution = 2048.0f;
    public float ambientIntensity = 1.0f;
    public boolean useCSM = false;
    public float csmSplitFactor = 4.0f;
    public ThreeDManager.SceneSettings renderSettings = new ThreeDManager.SceneSettings();
}
