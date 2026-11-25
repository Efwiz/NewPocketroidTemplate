package com.badlogic.gdx.graphics.g3d.utils.shapebuilders;

import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/shapebuilders/CapsuleShapeBuilder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class CapsuleShapeBuilder extends BaseShapeBuilder {
    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i) {
        float f3 = 2.0f * f;
        if (f2 < f3) {
            throw new GdxRuntimeException("Height must be at least twice the radius");
        }
        float f4 = f2 - f3;
        CylinderShapeBuilder.build(meshPartBuilder, f3, f4, f3, i, 0.0f, 360.0f, false);
        SphereShapeBuilder.build(meshPartBuilder, matTmp1.setToTranslation(0.0f, 0.5f * f4, 0.0f), f3, f3, f3, i, i, 0.0f, 360.0f, 0.0f, 90.0f);
        SphereShapeBuilder.build(meshPartBuilder, matTmp1.setToTranslation(0.0f, f4 * (-0.5f), 0.0f), f3, f3, f3, i, i, 0.0f, 360.0f, 90.0f, 180.0f);
    }
}
