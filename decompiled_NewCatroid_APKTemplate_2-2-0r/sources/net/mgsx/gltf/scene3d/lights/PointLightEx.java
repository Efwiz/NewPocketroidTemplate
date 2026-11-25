package net.mgsx.gltf.scene3d.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class PointLightEx extends PointLight {
    public Float range;

    @Override // com.badlogic.gdx.graphics.g3d.environment.PointLight
    public PointLight set(PointLight pointLight) {
        if (pointLight instanceof PointLightEx) {
            return set(pointLight.color, pointLight.position, pointLight.intensity, ((PointLightEx) pointLight).range);
        }
        return set(pointLight.color, pointLight.position, pointLight.intensity);
    }

    public PointLightEx set(Color color, Vector3 vector3, float f, Float f2) {
        super.set(color, vector3, f);
        this.range = f2;
        return this;
    }
}
