package net.mgsx.gltf.scene3d.lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class DirectionalLightEx extends DirectionalLight {
    public final Color baseColor = new Color(Color.WHITE);
    public float intensity = 1.0f;

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public DirectionalLight set(DirectionalLight directionalLight) {
        if (directionalLight instanceof DirectionalLightEx) {
            DirectionalLightEx directionalLightEx = (DirectionalLightEx) directionalLight;
            return set(directionalLightEx.baseColor, directionalLight.direction, directionalLightEx.intensity);
        }
        return set(directionalLight.color, directionalLight.direction, 1.0f);
    }

    public DirectionalLightEx set(Color color, Vector3 vector3, float f) {
        this.intensity = f;
        this.baseColor.set(color);
        this.direction.set(vector3);
        updateColor();
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public DirectionalLightEx set(Color color, Vector3 vector3) {
        if (color != null) {
            this.baseColor.set(color);
        }
        if (vector3 != null) {
            this.direction.set(vector3).nor();
        }
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public DirectionalLightEx set(float f, float f2, float f3, Vector3 vector3) {
        this.baseColor.set(f, f2, f3, 1.0f);
        if (vector3 != null) {
            this.direction.set(vector3).nor();
        }
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public DirectionalLightEx set(Color color, float f, float f2, float f3) {
        if (color != null) {
            this.baseColor.set(color);
        }
        this.direction.set(f, f2, f3).nor();
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public DirectionalLightEx set(float f, float f2, float f3, float f4, float f5, float f6) {
        this.baseColor.set(f, f2, f3, 1.0f).clamp();
        this.direction.set(f4, f5, f6).nor();
        return this;
    }

    public void updateColor() {
        this.color.r = this.baseColor.r * this.intensity;
        this.color.g = this.baseColor.g * this.intensity;
        this.color.b = this.baseColor.b * this.intensity;
    }

    @Override // com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
    public boolean equals(DirectionalLight directionalLight) {
        if (directionalLight instanceof DirectionalLightEx) {
            return equals((DirectionalLightEx) directionalLight);
        }
        return false;
    }

    public boolean equals(DirectionalLightEx directionalLightEx) {
        return directionalLightEx != null && (directionalLightEx == this || (this.baseColor.equals(directionalLightEx.baseColor) && Float.compare(this.intensity, directionalLightEx.intensity) == 0 && this.direction.equals(directionalLightEx.direction)));
    }
}
