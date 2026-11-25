package net.mgsx.gltf.scene3d.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class SpotLightEx extends SpotLight {
    public Float range;

    @Override // com.badlogic.gdx.graphics.g3d.environment.SpotLight
    public SpotLight set(SpotLight spotLight) {
        if (spotLight instanceof SpotLightEx) {
            return set(spotLight.color, spotLight.position, spotLight.direction, spotLight.intensity, spotLight.cutoffAngle, spotLight.exponent, ((SpotLightEx) spotLight).range);
        }
        return set(spotLight.color, spotLight.position, spotLight.direction, spotLight.intensity, spotLight.cutoffAngle, spotLight.exponent);
    }

    @Deprecated
    public SpotLightEx set(Color color, Vector3 vector3, Vector3 vector4, float f, float f2, float f3, Float f4) {
        super.set(color, vector3, vector4, f, f2, f3);
        this.range = f4;
        return this;
    }

    public SpotLightEx setRad(Color color, Vector3 vector3, Vector3 vector4, float f, float f2, float f3, Float f4) {
        if (color != null) {
            this.color.set(color);
        }
        if (vector3 != null) {
            this.position.set(vector3);
        }
        if (vector4 != null) {
            this.direction.set(vector4).nor();
        }
        this.intensity = f;
        setConeRad(f2, f3);
        this.range = f4;
        return this;
    }

    public SpotLightEx setDeg(Color color, Vector3 vector3, Vector3 vector4, float f, float f2, float f3, Float f4) {
        return setRad(color, vector3, vector4, f, f2 * 0.017453292f, f3 * 0.017453292f, f4);
    }

    public SpotLightEx setConeRad(float f, float f2) {
        float fCos = (float) Math.cos(f);
        float fMax = 1.0f / Math.max(0.001f, ((float) Math.cos(f2)) - fCos);
        this.cutoffAngle = (-fCos) * fMax;
        this.exponent = fMax;
        return this;
    }

    public SpotLightEx setConeDeg(float f, float f2) {
        return setConeRad(f * 0.017453292f, f2 * 0.017453292f);
    }
}
