package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class PBRMatrixAttribute extends Attribute {
    public final Matrix4 matrix;
    public static final String EnvRotationAlias = "envRotation";
    public static final long EnvRotation = register(EnvRotationAlias);

    public static PBRMatrixAttribute createEnvRotation(float f) {
        return new PBRMatrixAttribute(EnvRotation).set(f);
    }

    public static PBRMatrixAttribute createEnvRotation(Matrix4 matrix4) {
        return new PBRMatrixAttribute(EnvRotation).set(matrix4);
    }

    public PBRMatrixAttribute(long j) {
        super(j);
        this.matrix = new Matrix4();
    }

    private PBRMatrixAttribute set(Matrix4 matrix4) {
        this.matrix.set(matrix4);
        return this;
    }

    public PBRMatrixAttribute set(float f) {
        this.matrix.setToRotation(Vector3.Y, f);
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRMatrixAttribute(this.type).set(this.matrix);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return (int) (this.type - attribute.type);
    }
}
