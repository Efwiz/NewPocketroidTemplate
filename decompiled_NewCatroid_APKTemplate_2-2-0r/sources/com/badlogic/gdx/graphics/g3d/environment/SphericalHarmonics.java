package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/environment/SphericalHarmonics.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class SphericalHarmonics {
    private static final float[] coeff = {0.282095f, 0.488603f, 0.488603f, 0.488603f, 1.092548f, 1.092548f, 1.092548f, 0.315392f, 0.546274f};
    public final float[] data;

    private static final float clamp(float f) {
        float f2 = 0.0f;
        if (f >= 0.0f) {
            f2 = 1.0f;
            if (f <= 1.0f) {
                return f;
            }
        }
        return f2;
    }

    public SphericalHarmonics() {
        this.data = new float[27];
    }

    public SphericalHarmonics(float[] fArr) {
        if (fArr.length != 27) {
            throw new GdxRuntimeException("Incorrect array size");
        }
        this.data = (float[]) fArr.clone();
    }

    public SphericalHarmonics set(float[] fArr) {
        int i = 0;
        while (true) {
            float[] fArr2 = this.data;
            if (i >= fArr2.length) {
                return this;
            }
            fArr2[i] = fArr[i];
            i++;
        }
    }

    public SphericalHarmonics set(AmbientCubemap ambientCubemap) {
        return set(ambientCubemap.data);
    }

    public SphericalHarmonics set(Color color) {
        return set(color.r, color.g, color.b);
    }

    public SphericalHarmonics set(float f, float f2, float f3) {
        int i = 0;
        while (true) {
            float[] fArr = this.data;
            if (i >= fArr.length) {
                return this;
            }
            fArr[i] = f;
            int i2 = i + 2;
            fArr[i + 1] = f2;
            i += 3;
            fArr[i2] = f3;
        }
    }
}
