package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Color;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/MaterialComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MaterialComponent implements Component {
    public Color baseColor = new Color(Color.WHITE);
    public String baseColorTexturePath = null;
    public float metallic = 0.0f;
    public float roughness = 1.0f;
    public String metallicRoughnessTexturePath = null;
    public String normalTexturePath = null;
    public String occlusionTexturePath = null;
    public Color emissiveColor = new Color(Color.BLACK);
    public float emissiveIntensity = 0.0f;
    public String emissiveTexturePath = null;
    public float uvScaleX = 1.0f;
    public float uvScaleY = 1.0f;
}
