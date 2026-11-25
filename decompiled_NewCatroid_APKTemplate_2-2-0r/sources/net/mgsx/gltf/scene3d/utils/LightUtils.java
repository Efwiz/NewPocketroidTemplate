package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;

/* JADX INFO: loaded from: classes12.dex */
public class LightUtils {

    public static class LightsInfo {
        public int dirLights = 0;
        public int pointLights = 0;
        public int spotLights = 0;
        public int miscLights = 0;

        public void reset() {
            this.dirLights = 0;
            this.pointLights = 0;
            this.spotLights = 0;
            this.miscLights = 0;
        }
    }

    public static LightsInfo getLightsInfo(LightsInfo lightsInfo, Environment environment) {
        lightsInfo.reset();
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) environment.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        if (directionalLightsAttribute != null) {
            lightsInfo.dirLights = directionalLightsAttribute.lights.size;
        }
        PointLightsAttribute pointLightsAttribute = (PointLightsAttribute) environment.get(PointLightsAttribute.class, PointLightsAttribute.Type);
        if (pointLightsAttribute != null) {
            lightsInfo.pointLights = pointLightsAttribute.lights.size;
        }
        SpotLightsAttribute spotLightsAttribute = (SpotLightsAttribute) environment.get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
        if (spotLightsAttribute != null) {
            lightsInfo.spotLights = spotLightsAttribute.lights.size;
        }
        return lightsInfo;
    }

    public static LightsInfo getLightsInfo(LightsInfo lightsInfo, Iterable<BaseLight> iterable) {
        lightsInfo.reset();
        for (BaseLight baseLight : iterable) {
            if (baseLight instanceof DirectionalLight) {
                lightsInfo.dirLights++;
            } else if (baseLight instanceof PointLight) {
                lightsInfo.pointLights++;
            } else if (baseLight instanceof SpotLight) {
                lightsInfo.spotLights++;
            } else {
                lightsInfo.miscLights++;
            }
        }
        return lightsInfo;
    }
}
