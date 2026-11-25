package net.mgsx.gltf.data.extensions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.data.GLTFObject;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;

/* JADX INFO: loaded from: classes12.dex */
public abstract class KHRLightsPunctual {
    public static final String EXT = "KHR_lights_punctual";

    public static class GLTFLight extends GLTFObject {
        public static final String TYPE_DIRECTIONAL = "directional";
        public static final String TYPE_POINT = "point";
        public static final String TYPE_SPOT = "spot";
        public Float range;
        public GLTFSpotLight spot;
        public String type;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public String f409name = "";
        public float[] color = {1.0f, 1.0f, 1.0f};
        public float intensity = 1.0f;
    }

    public static class GLTFLightNode {
        public Integer light;
    }

    public static class GLTFLights {
        public Array<GLTFLight> lights;
    }

    public static class GLTFSpotLight {
        public float innerConeAngle = 0.0f;
        public float outerConeAngle = 0.7853982f;
    }

    public static BaseLight map(GLTFLight gLTFLight) {
        if (GLTFLight.TYPE_DIRECTIONAL.equals(gLTFLight.type)) {
            DirectionalLightEx directionalLightEx = new DirectionalLightEx();
            directionalLightEx.baseColor.set(GLTFTypes.mapColor(gLTFLight.color, Color.WHITE));
            directionalLightEx.intensity = gLTFLight.intensity;
            return directionalLightEx;
        }
        if (GLTFLight.TYPE_POINT.equals(gLTFLight.type)) {
            PointLightEx pointLightEx = new PointLightEx();
            pointLightEx.color.set(GLTFTypes.mapColor(gLTFLight.color, Color.WHITE));
            pointLightEx.intensity = gLTFLight.intensity / 10.0f;
            pointLightEx.range = gLTFLight.range;
            return pointLightEx;
        }
        if (GLTFLight.TYPE_SPOT.equals(gLTFLight.type)) {
            SpotLightEx spotLightEx = new SpotLightEx();
            if (gLTFLight.spot == null) {
                throw new GLTFIllegalException("spot property required for spot light type");
            }
            spotLightEx.color.set(GLTFTypes.mapColor(gLTFLight.color, Color.WHITE));
            spotLightEx.intensity = gLTFLight.intensity / 10.0f;
            spotLightEx.range = gLTFLight.range;
            spotLightEx.setConeRad(gLTFLight.spot.outerConeAngle, gLTFLight.spot.innerConeAngle);
            return spotLightEx;
        }
        throw new GLTFIllegalException("unsupported light type " + gLTFLight.type);
    }
}
