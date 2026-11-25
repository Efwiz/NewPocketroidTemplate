package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import java.util.Iterator;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;

/* JADX INFO: loaded from: classes12.dex */
public class MaterialConverter {
    public static void makeCompatible(Scene scene) {
        makeCompatible(scene.modelInstance.materials);
    }

    public static void makeCompatible(Iterable<Material> iterable) {
        Iterator<Material> it = iterable.iterator();
        while (it.hasNext()) {
            makeCompatible(it.next());
        }
    }

    public static void makeCompatible(Material material) {
        PBRColorAttribute pBRColorAttribute = (PBRColorAttribute) material.get(PBRColorAttribute.class, PBRColorAttribute.BaseColorFactor);
        Color color = pBRColorAttribute != null ? pBRColorAttribute.color : Color.WHITE;
        material.set(ColorAttribute.createDiffuse(color));
        material.set(ColorAttribute.createSpecular(color));
        PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) material.get(PBRFloatAttribute.class, PBRFloatAttribute.Roughness);
        float f = (1.0f - (pBRFloatAttribute != null ? pBRFloatAttribute.value : 1.0f)) * 10.0f;
        if (f > 0.0f) {
            material.set(FloatAttribute.createShininess(f * f));
        }
    }
}
