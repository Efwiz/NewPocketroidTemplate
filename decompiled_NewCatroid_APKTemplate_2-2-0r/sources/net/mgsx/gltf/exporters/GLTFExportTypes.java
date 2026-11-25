package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
class GLTFExportTypes {
    GLTFExportTypes() {
    }

    static float[] rgb(ColorAttribute colorAttribute) {
        if (colorAttribute == null) {
            return null;
        }
        return rgb(colorAttribute.color);
    }

    static float[] rgba(ColorAttribute colorAttribute) {
        if (colorAttribute == null) {
            return null;
        }
        return rgba(colorAttribute.color);
    }

    static float[] rgb(Color color) {
        if (color == null) {
            return null;
        }
        return new float[]{color.r, color.g, color.b};
    }

    static float[] rgba(Color color) {
        if (color == null) {
            return null;
        }
        return new float[]{color.r, color.g, color.b, color.a};
    }

    static float[] rgb(Color color, Color color2) {
        if (color.equals(color2)) {
            return null;
        }
        return rgb(color);
    }

    static float[] toArray(Vector3 vector3) {
        return new float[]{vector3.x, vector3.y, vector3.z};
    }

    static float[] toArray(Quaternion quaternion) {
        return new float[]{quaternion.x, quaternion.y, quaternion.z, quaternion.w};
    }
}
