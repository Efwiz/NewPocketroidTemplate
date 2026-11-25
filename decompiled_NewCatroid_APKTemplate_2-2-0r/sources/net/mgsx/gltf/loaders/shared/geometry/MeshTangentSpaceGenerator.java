package net.mgsx.gltf.loaders.shared.geometry;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Vector3;
import java.util.Iterator;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class MeshTangentSpaceGenerator {
    public static void computeTangentSpace(Mesh mesh, Material material, boolean z, boolean z2) {
        VertexAttribute vertexAttribute;
        if (mesh.getNumIndices() == 0) {
            throw new IllegalArgumentException("non indexed mesh not implemented");
        }
        float[] fArr = new float[(mesh.getNumVertices() * mesh.getVertexAttributes().vertexSize) / 4];
        short[] sArr = new short[mesh.getNumIndices()];
        mesh.getVertices(fArr);
        mesh.getIndices(sArr);
        PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) material.get(PBRTextureAttribute.class, PBRTextureAttribute.NormalTexture);
        if (pBRTextureAttribute == null) {
            throw new IllegalArgumentException("normal map not found in material");
        }
        VertexAttributes vertexAttributes = mesh.getVertexAttributes();
        Iterator<VertexAttribute> it = vertexAttributes.iterator();
        VertexAttribute next = null;
        loop0: while (true) {
            vertexAttribute = next;
            while (true) {
                if (!it.hasNext()) {
                    break loop0;
                }
                next = it.next();
                if (next.usage != 16 || next.unit != pBRTextureAttribute.uvIndex) {
                }
            }
        }
        if (vertexAttribute == null) {
            throw new IllegalArgumentException("texture coordinates not found");
        }
        computeTangentSpace(fArr, sArr, vertexAttributes, z, z2, vertexAttribute);
        mesh.setVertices(fArr);
        mesh.setIndices(sArr);
    }

    public static void computeTangentSpace(float[] fArr, short[] sArr, VertexAttributes vertexAttributes, boolean z, boolean z2, VertexAttribute vertexAttribute) {
        if (z) {
            computeNormals(fArr, sArr, vertexAttributes);
        }
        if (z2) {
            computeTangents(fArr, sArr, vertexAttributes, vertexAttribute);
        }
    }

    private static void computeNormals(float[] fArr, short[] sArr, VertexAttributes vertexAttributes) {
        short[] sArr2 = sArr;
        int offset = vertexAttributes.getOffset(1);
        int offset2 = vertexAttributes.getOffset(8);
        int i = vertexAttributes.vertexSize / 4;
        Vector3 vector3 = new Vector3();
        Vector3 vector4 = new Vector3();
        int i2 = 0;
        if (sArr2 != null) {
            int length = sArr2.length;
            while (i2 < length) {
                int i3 = (sArr2[i2] & 65535) * i;
                int i4 = i3 + offset;
                float f = fArr[i4];
                float f2 = fArr[i4 + 1];
                float f3 = fArr[i4 + 2];
                int i5 = i2 + 2;
                int i6 = (sArr2[i2 + 1] & 65535) * i;
                int i7 = i6 + offset;
                float f4 = fArr[i7];
                int i8 = length;
                float f5 = fArr[i7 + 1];
                float f6 = fArr[i7 + 2];
                int i9 = i2 + 3;
                int i10 = (sArr2[i5] & 65535) * i;
                int i11 = i10 + offset;
                float f7 = fArr[i11];
                float f8 = fArr[i11 + 1];
                int i12 = offset;
                float f9 = fArr[i11 + 2];
                vector3.set(f4, f5, f6).sub(f, f2, f3);
                vector4.set(f7, f8, f9).sub(f, f2, f3);
                Vector3 vector3Nor = vector3.crs(vector4).nor();
                int i13 = i3 + offset2;
                fArr[i13] = vector3Nor.x;
                fArr[i13 + 1] = vector3Nor.y;
                fArr[i13 + 2] = vector3Nor.z;
                int i14 = i6 + offset2;
                fArr[i14] = vector3Nor.x;
                fArr[i14 + 1] = vector3Nor.y;
                fArr[i14 + 2] = vector3Nor.z;
                int i15 = i10 + offset2;
                fArr[i15] = vector3Nor.x;
                fArr[i15 + 1] = vector3Nor.y;
                fArr[i15 + 2] = vector3Nor.z;
                sArr2 = sArr;
                i2 = i9;
                length = i8;
                offset = i12;
            }
            return;
        }
        int length2 = fArr.length / i;
        while (i2 < length2) {
            int i16 = i2 * i;
            int i17 = i16 + offset;
            float f10 = fArr[i17];
            float f11 = fArr[i17 + 1];
            float f12 = fArr[i17 + 2];
            int i18 = i2 + 2;
            int i19 = (i2 + 1) * i;
            int i20 = i19 + offset;
            float f13 = fArr[i20];
            float f14 = fArr[i20 + 1];
            float f15 = fArr[i20 + 2];
            int i21 = i2 + 3;
            int i22 = i18 * i;
            int i23 = i22 + offset;
            int i24 = length2;
            float f16 = fArr[i23];
            int i25 = i;
            float f17 = fArr[i23 + 1];
            float f18 = fArr[i23 + 2];
            vector3.set(f13, f14, f15).sub(f10, f11, f12);
            vector4.set(f16, f17, f18).sub(f10, f11, f12);
            Vector3 vector3Nor2 = vector3.crs(vector4).nor();
            int i26 = i16 + offset2;
            fArr[i26] = vector3Nor2.x;
            fArr[i26 + 1] = vector3Nor2.y;
            fArr[i26 + 2] = vector3Nor2.z;
            int i27 = i19 + offset2;
            fArr[i27] = vector3Nor2.x;
            fArr[i27 + 1] = vector3Nor2.y;
            fArr[i27 + 2] = vector3Nor2.z;
            int i28 = i22 + offset2;
            fArr[i28] = vector3Nor2.x;
            fArr[i28 + 1] = vector3Nor2.y;
            fArr[i28 + 2] = vector3Nor2.z;
            length2 = i24;
            i = i25;
            i2 = i21;
        }
    }

    private static void computeTangents(float[] fArr, short[] sArr, VertexAttributes vertexAttributes, VertexAttribute vertexAttribute) {
        short[] sArr2 = sArr;
        int offset = vertexAttributes.getOffset(1);
        int offset2 = vertexAttributes.getOffset(8);
        int offset3 = vertexAttributes.getOffset(128);
        int i = vertexAttribute.offset / 4;
        int i2 = vertexAttributes.vertexSize / 4;
        int length = fArr.length / i2;
        Vector3 vector3 = new Vector3();
        Vector3 vector4 = new Vector3();
        Vector3[] vector3Arr = new Vector3[sArr2.length];
        Vector3[] vector3Arr2 = new Vector3[sArr2.length];
        for (int i3 = 0; i3 < sArr2.length; i3++) {
            vector3Arr[i3] = new Vector3();
            vector3Arr2[i3] = new Vector3();
        }
        int length2 = sArr2.length;
        int i4 = 0;
        while (i4 < length2) {
            int i5 = sArr2[i4] & 65535;
            int i6 = i5 * i2;
            int i7 = i6 + offset;
            float f = fArr[i7];
            float f2 = fArr[i7 + 1];
            float f3 = fArr[i7 + 2];
            int i8 = i4 + 2;
            int i9 = sArr2[i4 + 1] & 65535;
            int i10 = i9 * i2;
            int i11 = i10 + offset;
            float f4 = fArr[i11];
            float f5 = fArr[i11 + 1];
            float f6 = fArr[i11 + 2];
            i4 += 3;
            int i12 = sArr2[i8] & 65535;
            int i13 = i12 * i2;
            int i14 = i13 + offset;
            float f7 = fArr[i14];
            float f8 = fArr[i14 + 1];
            float f9 = fArr[i14 + 2];
            int i15 = i6 + i;
            float f10 = fArr[i15];
            float f11 = 1.0f - fArr[i15 + 1];
            int i16 = i10 + i;
            float f12 = fArr[i16];
            float f13 = 1.0f - fArr[i16 + 1];
            int i17 = i13 + i;
            float f14 = f4 - f;
            float f15 = f7 - f;
            float f16 = f5 - f2;
            float f17 = f8 - f2;
            float f18 = f6 - f3;
            float f19 = f9 - f3;
            float f20 = f12 - f10;
            float f21 = fArr[i17] - f10;
            float f22 = f13 - f11;
            float f23 = (1.0f - fArr[i17 + 1]) - f11;
            float f24 = 1.0f / ((f20 * f23) - (f21 * f22));
            vector3.set(((f23 * f14) - (f22 * f15)) * f24, ((f23 * f16) - (f22 * f17)) * f24, ((f23 * f18) - (f22 * f19)) * f24);
            vector4.set(((f15 * f20) - (f14 * f21)) * f24, ((f17 * f20) - (f16 * f21)) * f24, ((f20 * f19) - (f21 * f18)) * f24);
            vector3Arr[i5].add(vector3);
            vector3Arr2[i5].add(vector4);
            vector3Arr[i9].add(vector3);
            vector3Arr2[i9].add(vector4);
            vector3Arr[i12].add(vector3);
            vector3Arr2[i12].add(vector4);
            sArr2 = sArr;
        }
        Vector3 vector5 = new Vector3();
        Vector3 vector6 = new Vector3();
        Vector3 vector7 = new Vector3();
        for (int i18 = 0; i18 < length; i18++) {
            int i19 = i18 * i2;
            int i20 = i19 + offset2;
            vector6.set(fArr[i20], fArr[i20 + 1], fArr[i20 + 2]);
            Vector3 vector8 = vector3Arr[i18];
            vector5.set(vector8).mulAdd(vector6, -vector6.dot(vector8)).nor();
            Vector3 vector9 = vector3Arr2[i18];
            vector7.set(vector6).crs(vector5);
            float f25 = vector7.dot(vector9) < 0.0f ? -1.0f : 1.0f;
            int i21 = i19 + offset3;
            fArr[i21] = vector5.x;
            fArr[i21 + 1] = vector5.y;
            fArr[i21 + 2] = vector5.z;
            fArr[i21 + 3] = f25;
        }
    }
}
