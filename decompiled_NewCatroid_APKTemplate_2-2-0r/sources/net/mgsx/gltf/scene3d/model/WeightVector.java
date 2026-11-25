package net.mgsx.gltf.scene3d.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes12.dex */
public class WeightVector {
    public int count;
    public float[] values;

    public WeightVector() {
        this(0, 8);
    }

    public WeightVector(int i) {
        this(i, 8);
    }

    public WeightVector(int i, int i2) {
        this.count = i;
        this.values = new float[Math.max(i, i2)];
    }

    public WeightVector set(WeightVector weightVector) {
        int i = weightVector.count;
        if (i > this.values.length) {
            this.values = new float[i];
        }
        this.count = i;
        int i2 = 0;
        while (true) {
            float[] fArr = weightVector.values;
            if (i2 >= fArr.length) {
                return this;
            }
            this.values[i2] = fArr[i2];
            i2++;
        }
    }

    public void lerp(WeightVector weightVector, float f) {
        if (this.count != weightVector.count) {
            throw new GdxRuntimeException("WeightVector count mismatch");
        }
        for (int i = 0; i < this.count; i++) {
            float[] fArr = this.values;
            fArr[i] = MathUtils.lerp(fArr[i], weightVector.values[i], f);
        }
    }

    public String toString() {
        String str = "WeightVector(";
        for (int i = 0; i < this.count; i++) {
            if (i > 0) {
                str = str + ", ";
            }
            str = str + this.values[i];
        }
        return str + Constant.AFTER_QUTO;
    }

    public WeightVector set() {
        this.count = 0;
        return this;
    }

    public WeightVector cpy() {
        return new WeightVector(this.count, this.values.length).set(this);
    }

    public float get(int i) {
        return this.values[i];
    }

    public WeightVector scl(float f) {
        for (int i = 0; i < this.count; i++) {
            float[] fArr = this.values;
            fArr[i] = fArr[i] * f;
        }
        return this;
    }

    public WeightVector mulAdd(WeightVector weightVector, float f) {
        for (int i = 0; i < this.count; i++) {
            float[] fArr = this.values;
            fArr[i] = fArr[i] + (weightVector.values[i] * f);
        }
        return this;
    }
}
