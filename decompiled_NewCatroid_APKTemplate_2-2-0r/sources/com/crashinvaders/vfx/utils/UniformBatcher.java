package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/* JADX INFO: loaded from: classes8.dex */
public class UniformBatcher implements Pool.Poolable {
    private ShaderProgram program = null;
    private boolean activateShader = false;

    @Override // com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        this.program = null;
        this.activateShader = false;
    }

    public UniformBatcher begin(ShaderProgram shaderProgram, boolean z) {
        this.program = shaderProgram;
        this.activateShader = z;
        if (z) {
            shaderProgram.begin();
        }
        return this;
    }

    public void end() {
        if (this.activateShader) {
            this.program.end();
        }
    }

    public UniformBatcher set(String str, float f) {
        this.program.setUniformf(str, f);
        return this;
    }

    public UniformBatcher set(String str, int i) {
        this.program.setUniformi(str, i);
        return this;
    }

    public UniformBatcher set(String str, Vector2 vector2) {
        this.program.setUniformf(str, vector2);
        return this;
    }

    public UniformBatcher set(String str, Vector3 vector3) {
        this.program.setUniformf(str, vector3);
        return this;
    }

    public UniformBatcher set(String str, Matrix3 matrix3) {
        this.program.setUniformMatrix(str, matrix3);
        return this;
    }

    public UniformBatcher set(String str, Matrix4 matrix4) {
        this.program.setUniformMatrix(str, matrix4);
        return this;
    }

    public UniformBatcher set(String str, int i, float[] fArr, int i2, int i3) {
        if (i == 1) {
            this.program.setUniform1fv(str, fArr, i2, i3);
        } else if (i == 2) {
            this.program.setUniform2fv(str, fArr, i2, i3);
        } else if (i == 3) {
            this.program.setUniform3fv(str, fArr, i2, i3);
        } else if (i == 4) {
            this.program.setUniform4fv(str, fArr, i2, i3);
        } else {
            throw new IllegalArgumentException("elementSize has illegal value: " + i + ". Possible values are 1..4");
        }
        return this;
    }
}
