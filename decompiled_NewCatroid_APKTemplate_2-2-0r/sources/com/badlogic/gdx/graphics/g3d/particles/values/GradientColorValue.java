package com.badlogic.gdx.graphics.g3d.particles.values;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/particles/values/GradientColorValue.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class GradientColorValue extends ParticleValue {
    private static float[] temp = new float[3];
    private float[] colors = {1.0f, 1.0f, 1.0f};
    public float[] timeline = {0.0f};

    public float[] getTimeline() {
        return this.timeline;
    }

    public void setTimeline(float[] fArr) {
        this.timeline = fArr;
    }

    public float[] getColors() {
        return this.colors;
    }

    public void setColors(float[] fArr) {
        this.colors = fArr;
    }

    public float[] getColor(float f) {
        getColor(f, temp, 0);
        return temp;
    }

    public void getColor(float f, float[] fArr, int i) {
        int i2;
        float[] fArr2 = this.timeline;
        int length = fArr2.length;
        int i3 = 0;
        int i4 = 1;
        while (true) {
            int i5 = i4;
            i2 = i3;
            i3 = i5;
            if (i3 >= length) {
                i3 = -1;
                break;
            } else if (fArr2[i3] > f) {
                break;
            } else {
                i4 = i3 + 1;
            }
        }
        float f2 = fArr2[i2];
        int i6 = i2 * 3;
        float[] fArr3 = this.colors;
        float f3 = fArr3[i6];
        float f4 = fArr3[i6 + 1];
        float f5 = fArr3[i6 + 2];
        if (i3 == -1) {
            fArr[i] = f3;
            fArr[i + 1] = f4;
            fArr[i + 2] = f5;
        } else {
            float f6 = (f - f2) / (fArr2[i3] - f2);
            int i7 = i3 * 3;
            fArr[i] = f3 + ((fArr3[i7] - f3) * f6);
            fArr[i + 1] = f4 + ((fArr3[i7 + 1] - f4) * f6);
            fArr[i + 2] = f5 + ((fArr3[i7 + 2] - f5) * f6);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void write(Json json) {
        super.write(json);
        json.writeValue("colors", this.colors);
        json.writeValue("timeline", this.timeline);
    }

    @Override // com.badlogic.gdx.graphics.g3d.particles.values.ParticleValue, com.badlogic.gdx.utils.Json.Serializable
    public void read(Json json, JsonValue jsonValue) {
        super.read(json, jsonValue);
        this.colors = (float[]) json.readValue("colors", float[].class, jsonValue);
        this.timeline = (float[]) json.readValue("timeline", float[].class, jsonValue);
    }

    public void load(GradientColorValue gradientColorValue) {
        super.load((ParticleValue) gradientColorValue);
        float[] fArr = new float[gradientColorValue.colors.length];
        this.colors = fArr;
        System.arraycopy(gradientColorValue.colors, 0, fArr, 0, fArr.length);
        float[] fArr2 = new float[gradientColorValue.timeline.length];
        this.timeline = fArr2;
        System.arraycopy(gradientColorValue.timeline, 0, fArr2, 0, fArr2.length);
    }
}
