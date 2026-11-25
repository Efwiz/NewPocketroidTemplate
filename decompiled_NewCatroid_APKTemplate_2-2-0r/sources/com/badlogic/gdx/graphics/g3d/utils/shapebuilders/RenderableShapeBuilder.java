package com.badlogic.gdx.graphics.g3d.utils.shapebuilders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FlushablePool;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/shapebuilders/RenderableShapeBuilder.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RenderableShapeBuilder extends BaseShapeBuilder {
    private static final int FLOAT_BYTES = 4;
    private static short[] indices;
    private static float[] vertices;
    private static final RenderablePool renderablesPool = new RenderablePool();
    private static final Array<Renderable> renderables = new Array<>();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/utils/shapebuilders/RenderableShapeBuilder$RenderablePool.class
      classes8.dex
     */
    private static class RenderablePool extends FlushablePool<Renderable> {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.badlogic.gdx.utils.Pool
        public Renderable newObject() {
            return new Renderable();
        }

        @Override // com.badlogic.gdx.utils.FlushablePool, com.badlogic.gdx.utils.Pool
        public Renderable obtain() {
            Renderable renderable = (Renderable) super.obtain();
            renderable.environment = null;
            renderable.material = null;
            renderable.meshPart.set("", null, 0, 0, 0);
            renderable.shader = null;
            renderable.userData = null;
            return renderable;
        }
    }

    public static void buildNormals(MeshPartBuilder meshPartBuilder, RenderableProvider renderableProvider, float f) {
        buildNormals(meshPartBuilder, renderableProvider, f, tmpColor0.set(0.0f, 0.0f, 1.0f, 1.0f), tmpColor1.set(1.0f, 0.0f, 0.0f, 1.0f), tmpColor2.set(0.0f, 1.0f, 0.0f, 1.0f));
    }

    public static void buildNormals(MeshPartBuilder meshPartBuilder, RenderableProvider renderableProvider, float f, Color color, Color color2, Color color3) {
        Array<Renderable> array = renderables;
        renderableProvider.getRenderables(array, renderablesPool);
        Array.ArrayIterator<Renderable> it = array.iterator();
        while (it.hasNext()) {
            buildNormals(meshPartBuilder, it.next(), f, color, color2, color3);
        }
        renderablesPool.flush();
        renderables.clear();
    }

    public static void buildNormals(MeshPartBuilder meshPartBuilder, Renderable renderable, float f, Color color, Color color2, Color color3) {
        int iMinVerticeInIndices;
        int iMaxVerticeInIndices;
        Mesh mesh = renderable.meshPart.mesh;
        int i = mesh.getVertexAttribute(1) != null ? mesh.getVertexAttribute(1).offset / 4 : -1;
        int i2 = mesh.getVertexAttribute(8) != null ? mesh.getVertexAttribute(8).offset / 4 : -1;
        int i3 = mesh.getVertexAttribute(128) != null ? mesh.getVertexAttribute(128).offset / 4 : -1;
        int i4 = mesh.getVertexAttribute(256) != null ? mesh.getVertexAttribute(256).offset / 4 : -1;
        int vertexSize = mesh.getVertexSize() / 4;
        if (mesh.getNumIndices() > 0) {
            ensureIndicesCapacity(mesh.getNumIndices());
            mesh.getIndices(renderable.meshPart.offset, renderable.meshPart.size, indices, 0);
            iMinVerticeInIndices = minVerticeInIndices();
            iMaxVerticeInIndices = maxVerticeInIndices() - iMinVerticeInIndices;
        } else {
            iMinVerticeInIndices = renderable.meshPart.offset;
            iMaxVerticeInIndices = renderable.meshPart.size;
        }
        int i5 = iMaxVerticeInIndices * vertexSize;
        ensureVerticesCapacity(i5);
        mesh.getVertices(iMinVerticeInIndices * vertexSize, i5, vertices, 0);
        while (iMinVerticeInIndices < iMaxVerticeInIndices) {
            int i6 = iMinVerticeInIndices * vertexSize;
            Vector3 vector3 = tmpV0;
            float[] fArr = vertices;
            int i7 = i6 + i;
            vector3.set(fArr[i7], fArr[i7 + 1], fArr[i7 + 2]);
            int i8 = -1;
            if (i2 != -1) {
                Vector3 vector4 = tmpV1;
                float[] fArr2 = vertices;
                int i9 = i6 + i2;
                vector4.set(fArr2[i9], fArr2[i9 + 1], fArr2[i9 + 2]);
                tmpV2.set(tmpV0).add(tmpV1.scl(f));
                i8 = -1;
            }
            if (i3 != i8) {
                Vector3 vector5 = tmpV3;
                float[] fArr3 = vertices;
                int i10 = i6 + i3;
                vector5.set(fArr3[i10], fArr3[i10 + 1], fArr3[i10 + 2]);
                tmpV4.set(tmpV0).add(tmpV3.scl(f));
                i8 = -1;
            }
            if (i4 != i8) {
                Vector3 vector6 = tmpV5;
                float[] fArr4 = vertices;
                int i11 = i6 + i4;
                vector6.set(fArr4[i11], fArr4[i11 + 1], fArr4[i11 + 2]);
                tmpV6.set(tmpV0).add(tmpV5.scl(f));
            }
            tmpV0.mul(renderable.worldTransform);
            tmpV2.mul(renderable.worldTransform);
            tmpV4.mul(renderable.worldTransform);
            tmpV6.mul(renderable.worldTransform);
            if (i2 != -1) {
                meshPartBuilder.setColor(color);
                meshPartBuilder.line(tmpV0, tmpV2);
            }
            if (i3 != -1) {
                meshPartBuilder.setColor(color2);
                meshPartBuilder.line(tmpV0, tmpV4);
            }
            if (i4 != -1) {
                meshPartBuilder.setColor(color3);
                meshPartBuilder.line(tmpV0, tmpV6);
            }
            iMinVerticeInIndices++;
        }
    }

    private static void ensureVerticesCapacity(int i) {
        float[] fArr = vertices;
        if (fArr == null || fArr.length < i) {
            vertices = new float[i];
        }
    }

    private static void ensureIndicesCapacity(int i) {
        short[] sArr = indices;
        if (sArr == null || sArr.length < i) {
            indices = new short[i];
        }
    }

    private static short minVerticeInIndices() {
        short s = ShortCompanionObject.MAX_VALUE;
        int i = 0;
        while (true) {
            short[] sArr = indices;
            if (i >= sArr.length) {
                return s;
            }
            short s2 = sArr[i];
            if (s2 < s) {
                s = s2;
            }
            i++;
        }
    }

    private static short maxVerticeInIndices() {
        short s = ShortCompanionObject.MIN_VALUE;
        int i = 0;
        while (true) {
            short[] sArr = indices;
            if (i >= sArr.length) {
                return s;
            }
            short s2 = sArr[i];
            if (s2 > s) {
                s = s2;
            }
            i++;
        }
    }
}
