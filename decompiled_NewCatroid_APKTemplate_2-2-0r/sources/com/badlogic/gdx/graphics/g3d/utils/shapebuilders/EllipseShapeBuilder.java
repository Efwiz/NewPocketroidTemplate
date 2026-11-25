package com.badlogic.gdx.graphics.g3d.utils.shapebuilders;

import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/shapebuilders/EllipseShapeBuilder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class EllipseShapeBuilder extends BaseShapeBuilder {
    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, float f2, float f3, float f4, float f5, float f6, float f7) {
        build(meshPartBuilder, f, i, f2, f3, f4, f5, f6, f7, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, Vector3 vector3, Vector3 vector4) {
        build(meshPartBuilder, f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        build(meshPartBuilder, f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, vector5.x, vector5.y, vector5.z, vector6.x, vector6.y, vector6.z);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13) {
        build(meshPartBuilder, f, i, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = f * 2.0f;
        build(meshPartBuilder, f10, f10, i, f2, f3, f4, f5, f6, f7, f8, f9);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, Vector3 vector3, Vector3 vector4, float f2, float f3) {
        build(meshPartBuilder, f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, f2, f3);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f2, float f3) {
        build(meshPartBuilder, f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, vector5.x, vector5.y, vector5.z, vector6.x, vector6.y, vector6.z, f2, f3);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, int i, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15) {
        float f16 = f * 2.0f;
        build(meshPartBuilder, f16, f16, 0.0f, 0.0f, i, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, float f3, float f4, float f5, float f6, float f7, float f8) {
        build(meshPartBuilder, f, f2, i, f3, f4, f5, f6, f7, f8, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, Vector3 vector3, Vector3 vector4) {
        build(meshPartBuilder, f, f2, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        build(meshPartBuilder, f, f2, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, vector5.x, vector5.y, vector5.z, vector6.x, vector6.y, vector6.z);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14) {
        build(meshPartBuilder, f, f2, i, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        build(meshPartBuilder, f, f2, 0.0f, 0.0f, i, f3, f4, f5, f6, f7, f8, f9, f10);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, Vector3 vector3, Vector3 vector4, float f3, float f4) {
        build(meshPartBuilder, f, f2, 0.0f, 0.0f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, f3, f4);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f3, float f4) {
        build(meshPartBuilder, f, f2, 0.0f, 0.0f, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, vector5.x, vector5.y, vector5.z, vector6.x, vector6.y, vector6.z, f3, f4);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, int i, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16) {
        build(meshPartBuilder, f, f2, 0.0f, 0.0f, i, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, float f3, float f4, int i, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12) {
        tmpV1.set(f8, f9, f10).crs(0.0f, 0.0f, 1.0f);
        tmpV2.set(f8, f9, f10).crs(0.0f, 1.0f, 0.0f);
        if (tmpV2.len2() > tmpV1.len2()) {
            tmpV1.set(tmpV2);
        }
        tmpV2.set(tmpV1.nor()).crs(f8, f9, f10).nor();
        build(meshPartBuilder, f, f2, f3, f4, i, f5, f6, f7, f8, f9, f10, tmpV1.x, tmpV1.y, tmpV1.z, tmpV2.x, tmpV2.y, tmpV2.z, f11, f12);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, float f3, float f4, int i, float f5, float f6, float f7, float f8, float f9, float f10) {
        build(meshPartBuilder, f, f2, f3, f4, i, f5, f6, f7, f8, f9, f10, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, float f3, float f4, int i, Vector3 vector3, Vector3 vector4) {
        build(meshPartBuilder, f, f2, f3, f4, i, vector3.x, vector3.y, vector3.z, vector4.x, vector4.y, vector4.z, 0.0f, 360.0f);
    }

    public static void build(MeshPartBuilder meshPartBuilder, float f, float f2, float f3, float f4, int i, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16, float f17, float f18) {
        Vector3 vector3;
        short s;
        short s2;
        char c;
        short s3;
        int i2 = i;
        float f19 = f5;
        if (f3 <= 0.0f || f4 <= 0.0f) {
            meshPartBuilder.ensureVertices(i2 + 2);
            meshPartBuilder.ensureTriangleIndices(i2);
        } else if (f3 == f && f4 == f2) {
            int i3 = i2 + 1;
            meshPartBuilder.ensureVertices(i3);
            meshPartBuilder.ensureIndices(i3);
            if (meshPartBuilder.getPrimitiveType() != 1) {
                throw new GdxRuntimeException("Incorrect primitive type : expect GL_LINES because innerWidth == width && innerHeight == height");
            }
        } else {
            int i4 = i2 + 1;
            meshPartBuilder.ensureVertices(i4 * 2);
            meshPartBuilder.ensureRectangleIndices(i4);
        }
        float f20 = f17 * 0.017453292f;
        float f21 = ((f18 - f17) * 0.017453292f) / i2;
        Vector3 vector3Scl = tmpV1.set(f11, f12, f13).scl(f * 0.5f);
        Vector3 vector3Scl2 = tmpV2.set(f14, f15, f16).scl(f2 * 0.5f);
        Vector3 vector3Scl3 = tmpV3.set(f11, f12, f13).scl(f3 * 0.5f);
        Vector3 vector3Scl4 = tmpV4.set(f14, f15, f16).scl(f4 * 0.5f);
        MeshPartBuilder.VertexInfo vertexInfo = vertTmp3.set(null, null, null, null);
        vertexInfo.hasNormal = true;
        vertexInfo.hasPosition = true;
        vertexInfo.hasUV = true;
        vertexInfo.uv.set(0.5f, 0.5f);
        vertexInfo.position.set(f19, f6, f7);
        vertexInfo.normal.set(f8, f9, f10);
        MeshPartBuilder.VertexInfo vertexInfo2 = vertTmp4.set(null, null, null, null);
        vertexInfo2.hasNormal = true;
        vertexInfo2.hasPosition = true;
        vertexInfo2.hasUV = true;
        vertexInfo2.uv.set(0.5f, 0.5f);
        vertexInfo2.position.set(f19, f6, f7);
        vertexInfo2.normal.set(f8, f9, f10);
        short sVertex = meshPartBuilder.vertex(vertexInfo2);
        float f22 = (f3 / f) * 0.5f;
        float f23 = (f4 / f2) * 0.5f;
        int i5 = 0;
        short s4 = 0;
        short s5 = 0;
        short s6 = 0;
        while (i5 <= i2) {
            float f24 = f20 + (i5 * f21);
            float fCos = MathUtils.cos(f24);
            float fSin = MathUtils.sin(f24);
            short s7 = sVertex;
            float f25 = f23;
            float f26 = f22;
            Vector3 vector4 = vector3Scl4;
            vertexInfo2.position.set(f19, f6, f7).add((vector3Scl.x * fCos) + (vector3Scl2.x * fSin), (vector3Scl.y * fCos) + (vector3Scl2.y * fSin), (vector3Scl.z * fCos) + (vector3Scl2.z * fSin));
            vertexInfo2.uv.set((fCos * 0.5f) + 0.5f, (fSin * 0.5f) + 0.5f);
            short sVertex2 = meshPartBuilder.vertex(vertexInfo2);
            if (f3 <= 0.0f || f4 <= 0.0f) {
                vector3 = vector4;
                s = s5;
                s2 = s6;
                c = 0;
                s3 = s7;
                if (i5 != 0) {
                    meshPartBuilder.triangle(sVertex2, s4, s3);
                }
            } else {
                if (f3 == f && f4 == f2) {
                    if (i5 != 0) {
                        meshPartBuilder.line(sVertex2, s4);
                    }
                    s3 = s7;
                    vector3 = vector4;
                    s = s5;
                    s2 = s6;
                    c = 0;
                } else {
                    vector3 = vector4;
                    vertexInfo.position.set(f19, f6, f7).add((vector3Scl3.x * fCos) + (vector3.x * fSin), (vector3Scl3.y * fCos) + (vector3.y * fSin), (vector3Scl3.z * fCos) + (vector3.z * fSin));
                    c = 0;
                    vertexInfo.uv.set((f26 * fCos) + 0.5f, (f25 * fSin) + 0.5f);
                    short sVertex3 = meshPartBuilder.vertex(vertexInfo);
                    if (i5 != 0) {
                        meshPartBuilder.rect(sVertex3, sVertex2, s6, s5);
                    }
                    s6 = sVertex2;
                    s5 = sVertex3;
                    s3 = s7;
                }
                i5++;
                f22 = f26;
                s4 = sVertex2;
                vector3Scl4 = vector3;
                i2 = i;
                f23 = f25;
                sVertex = s3;
                f19 = f5;
            }
            s5 = s;
            s6 = s2;
            i5++;
            f22 = f26;
            s4 = sVertex2;
            vector3Scl4 = vector3;
            i2 = i;
            f23 = f25;
            sVertex = s3;
            f19 = f5;
        }
    }
}
