package org.catrobat.catroid.raptor.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import org.catrobat.catroid.raptor.ParticleSystem3DComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/MeshParticleBatcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MeshParticleBatcher implements Disposable {
    private static final int FLOATS_PER_VERT = 7;
    private static final int MAX_VERTS_PER_PARTICLE = 128;
    private short[] batchedIndices;
    private Mesh batchedMesh;
    private float[] batchedVertices;
    private int maxParticles;
    private ShaderProgram shader;
    private int srcIdxCount;
    private short[] srcIndices;
    private float[] srcNormals;
    private float[] srcPositions;
    private float[] srcUVs;
    private int srcVertCount;
    private boolean glInitialized = false;
    private final Vector3 tmpV = new Vector3();
    private final Quaternion tmpQ = new Quaternion();
    private final Matrix4 tmpMat = new Matrix4();
    private int lastVertOffset = 0;
    private int lastIdxOffset = 0;
    private int lastParticleCount = 0;

    public MeshParticleBatcher(int maxParticles) {
        this.maxParticles = maxParticles;
    }

    public void loadPrimitive(ParticleSystem3DComponent.MeshType type) {
        Model modelCreateSphere;
        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material();
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$MeshType[type.ordinal()];
        if (i == 1) {
            modelCreateSphere = modelBuilder.createSphere(1.0f, 1.0f, 1.0f, 6, 6, material, 25L);
        } else if (i == 2) {
            modelCreateSphere = modelBuilder.createCylinder(1.0f, 1.0f, 1.0f, 8, material, 25L);
        } else {
            modelCreateSphere = modelBuilder.createBox(1.0f, 1.0f, 1.0f, material, 25L);
        }
        extractMeshData(modelCreateSphere);
        modelCreateSphere.dispose();
        invalidateGL();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/MeshParticleBatcher$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.raptor.particles.MeshParticleBatcher$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$MeshType;

        static {
            int[] iArr = new int[ParticleSystem3DComponent.MeshType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$MeshType = iArr;
            try {
                iArr[ParticleSystem3DComponent.MeshType.SPHERE_LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$MeshType[ParticleSystem3DComponent.MeshType.CYLINDER_LOW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$MeshType[ParticleSystem3DComponent.MeshType.CUBE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void loadFromModel(Model model) {
        if (model == null) {
            loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
        } else {
            extractMeshData(model);
            invalidateGL();
        }
    }

    private void invalidateGL() {
        Mesh mesh = this.batchedMesh;
        if (mesh != null) {
            mesh.dispose();
            this.batchedMesh = null;
        }
        this.batchedVertices = null;
        this.batchedIndices = null;
        this.glInitialized = false;
    }

    private void extractMeshData(Model model) {
        short[] sArr;
        MeshPart meshPartFindFirstMeshPart = findFirstMeshPart(model.nodes);
        Mesh mesh = meshPartFindFirstMeshPart != null ? meshPartFindFirstMeshPart.mesh : null;
        int i = 0;
        if (mesh == null && model.meshParts != null && model.meshParts.size > 0) {
            meshPartFindFirstMeshPart = model.meshParts.get(0);
            mesh = meshPartFindFirstMeshPart.mesh;
        }
        if (mesh == null && model.meshes != null && model.meshes.size > 0) {
            mesh = model.meshes.get(0);
            meshPartFindFirstMeshPart = new MeshPart();
            meshPartFindFirstMeshPart.mesh = mesh;
            meshPartFindFirstMeshPart.offset = 0;
            meshPartFindFirstMeshPart.size = mesh.getNumIndices();
            meshPartFindFirstMeshPart.primitiveType = 4;
        }
        if (mesh == null) {
            Gdx.f82app.error("MeshBatcher", "No mesh found in model, falling back to cube");
            loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
            return;
        }
        Gdx.f82app.log("MeshBatcher", "Found mesh: " + mesh.getNumVertices() + " verts, " + mesh.getNumIndices() + " indices");
        int vertexSize = mesh.getVertexSize() / 4;
        float[] fArr = new float[mesh.getNumVertices() * vertexSize];
        mesh.getVertices(fArr);
        int numIndices = mesh.getNumIndices();
        if (numIndices > 0) {
            sArr = new short[numIndices];
            mesh.getIndices(sArr);
        } else {
            numIndices = mesh.getNumVertices();
            sArr = new short[numIndices];
            for (int i2 = 0; i2 < numIndices; i2++) {
                sArr[i2] = (short) i2;
            }
        }
        VertexAttribute vertexAttribute = mesh.getVertexAttribute(1);
        VertexAttribute vertexAttribute2 = mesh.getVertexAttribute(8);
        VertexAttribute vertexAttribute3 = mesh.getVertexAttribute(16);
        int i3 = vertexAttribute != null ? vertexAttribute.offset / 4 : -1;
        int i4 = vertexAttribute2 != null ? vertexAttribute2.offset / 4 : -1;
        int i5 = vertexAttribute3 != null ? vertexAttribute3.offset / 4 : -1;
        if (i3 < 0) {
            Gdx.f82app.error("MeshBatcher", "Mesh has no position attribute!");
            loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
            return;
        }
        this.srcVertCount = Math.min(mesh.getNumVertices(), 128);
        int i6 = (meshPartFindFirstMeshPart == null || meshPartFindFirstMeshPart.offset < 0) ? 0 : meshPartFindFirstMeshPart.offset;
        int i7 = (meshPartFindFirstMeshPart == null || meshPartFindFirstMeshPart.size <= 0) ? numIndices : meshPartFindFirstMeshPart.size;
        if (i6 + i7 > numIndices) {
            i7 = numIndices - i6;
        }
        this.srcIdxCount = i7;
        int i8 = this.srcVertCount;
        this.srcPositions = new float[i8 * 3];
        this.srcNormals = new float[i8 * 3];
        this.srcUVs = new float[i8 * 2];
        this.srcIndices = new short[i7];
        float f = -3.4028235E38f;
        float f2 = -3.4028235E38f;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        float f6 = -3.4028235E38f;
        while (i < this.srcVertCount) {
            int i9 = i * vertexSize;
            int i10 = i9 + i3;
            float f7 = fArr[i10];
            float f8 = fArr[i10 + 1];
            float f9 = fArr[i10 + 2];
            int i11 = vertexSize;
            float[] fArr2 = this.srcPositions;
            int i12 = i * 3;
            fArr2[i12] = f7;
            int i13 = i12 + 1;
            fArr2[i13] = f8;
            int i14 = i12 + 2;
            fArr2[i14] = f9;
            if (f7 < f3) {
                f3 = f7;
            }
            if (f7 > f) {
                f = f7;
            }
            if (f8 < f4) {
                f4 = f8;
            }
            if (f8 > f6) {
                f6 = f8;
            }
            if (f9 < f5) {
                f5 = f9;
            }
            if (f9 > f2) {
                f2 = f9;
            }
            if (i4 >= 0) {
                float[] fArr3 = this.srcNormals;
                int i15 = i9 + i4;
                fArr3[i12] = fArr[i15];
                fArr3[i13] = fArr[i15 + 1];
                fArr3[i14] = fArr[i15 + 2];
            } else {
                this.srcNormals[i13] = 1.0f;
            }
            if (i5 >= 0) {
                float[] fArr4 = this.srcUVs;
                int i16 = i * 2;
                int i17 = i9 + i5;
                fArr4[i16] = fArr[i17];
                fArr4[i16 + 1] = fArr[i17 + 1];
            }
            i++;
            vertexSize = i11;
        }
        float fMax = Math.max(f - f3, Math.max(f6 - f4, f2 - f5));
        if (fMax > 0.001f) {
            float f10 = 1.0f / fMax;
            float f11 = (f3 + f) * 0.5f;
            float f12 = (f4 + f6) * 0.5f;
            float f13 = (f5 + f2) * 0.5f;
            for (int i18 = 0; i18 < this.srcVertCount; i18++) {
                float[] fArr5 = this.srcPositions;
                int i19 = i18 * 3;
                fArr5[i19] = (fArr5[i19] - f11) * f10;
                int i20 = i19 + 1;
                fArr5[i20] = (fArr5[i20] - f12) * f10;
                int i21 = i19 + 2;
                fArr5[i21] = (fArr5[i21] - f13) * f10;
            }
        }
        for (int i22 = 0; i22 < this.srcIdxCount; i22++) {
            this.srcIndices[i22] = (short) Math.min(sArr[i6 + i22] & 65535, this.srcVertCount - 1);
        }
        Gdx.f82app.log("MeshBatcher", "Extracted: " + this.srcVertCount + " verts, " + this.srcIdxCount + " indices, normalized to unit size");
    }

    private MeshPart findFirstMeshPart(Iterable<Node> nodes) {
        MeshPart meshPartFindFirstMeshPart;
        for (Node node : nodes) {
            if (node.parts != null && node.parts.size > 0) {
                Array.ArrayIterator<NodePart> it = node.parts.iterator();
                while (it.hasNext()) {
                    NodePart next = it.next();
                    if (next.meshPart != null && next.meshPart.mesh != null && next.meshPart.mesh.getNumVertices() > 0) {
                        return next.meshPart;
                    }
                }
            }
            if (node.hasChildren() && (meshPartFindFirstMeshPart = findFirstMeshPart(node.getChildren())) != null) {
                return meshPartFindFirstMeshPart;
            }
        }
        return null;
    }

    public void ensureGLReady() {
        if (this.glInitialized) {
            return;
        }
        if (this.srcPositions == null) {
            loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
        }
        int i = this.maxParticles;
        int i2 = this.srcVertCount;
        int i3 = i * i2;
        int i4 = this.srcIdxCount;
        int i5 = i * i4;
        if (i3 > 65000) {
            int i6 = 65000 / i2;
            this.maxParticles = i6;
            i3 = i6 * i2;
            i5 = i6 * i4;
        }
        this.batchedVertices = new float[i3 * 7];
        this.batchedIndices = new short[i5];
        this.batchedMesh = new Mesh(false, i3, i5, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
        buildShader();
        this.glInitialized = true;
    }

    private void buildShader() {
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec3 a_position;\nattribute vec3 a_normal;\nattribute vec4 a_color;\nuniform mat4 u_projViewTrans;\nuniform vec3 u_lightDir;\nuniform vec3 u_ambientColor;\nvarying vec4 v_color;\nvoid main() {\n    float NdotL = max(dot(a_normal, -u_lightDir), 0.0);\n    vec3 diffuse = a_color.rgb * (u_ambientColor + vec3(NdotL));\n    v_color = vec4(diffuse, a_color.a);\n    gl_Position = u_projViewTrans * vec4(a_position, 1.0);\n}\n", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec4 v_color;\nvoid main() {\n    gl_FragColor = v_color;\n    if (gl_FragColor.a < 0.01) discard;\n}\n");
        this.shader = shaderProgram;
        if (shaderProgram.isCompiled()) {
            return;
        }
        Gdx.f82app.error("MeshBatcherShader", this.shader.getLog());
        this.shader = null;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0048  */
    public void batch(int count, float[] posX, float[] posY, float[] posZ, float[] size, float[] rotX, float[] rotY, float[] rotZ, float[] colorR, float[] colorG, float[] colorB, float[] colorA, float[] velX, float[] velY, float[] velZ, boolean alignToVelocity) {
        if (!this.glInitialized || this.srcPositions == null) {
            return;
        }
        int iMin = Math.min(count, this.maxParticles);
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < iMin; i3++) {
            float f = size[i3];
            if (alignToVelocity) {
                float f2 = velX[i3];
                if (f2 != 0.0f || velY[i3] != 0.0f || velZ[i3] != 0.0f) {
                    this.tmpV.set(f2, velY[i3], velZ[i3]).nor();
                    this.tmpQ.setFromCross(Vector3.Z, this.tmpV);
                } else {
                    this.tmpQ.setEulerAngles(rotY[i3], rotX[i3], rotZ[i3]);
                }
            } else {
                this.tmpQ.setEulerAngles(rotY[i3], rotX[i3], rotZ[i3]);
            }
            float f3 = posX[i3];
            float f4 = posY[i3];
            float f5 = posZ[i3];
            float floatBits = Color.toFloatBits(colorR[i3], colorG[i3], colorB[i3], colorA[i3]);
            float f6 = this.tmpQ.x;
            float f7 = this.tmpQ.y;
            float f8 = this.tmpQ.z;
            float f9 = this.tmpQ.w;
            float f10 = f6 * f6;
            float f11 = f7 * f7;
            float f12 = f8 * f8;
            float f13 = f6 * f7;
            float f14 = f6 * f8;
            float f15 = f7 * f8;
            float f16 = f6 * f9;
            float f17 = f7 * f9;
            float f18 = f9 * f8;
            float f19 = 1.0f - ((f11 + f12) * 2.0f);
            float f20 = (f13 - f18) * 2.0f;
            float f21 = (f14 + f17) * 2.0f;
            float f22 = (f13 + f18) * 2.0f;
            float f23 = 1.0f - ((f10 + f12) * 2.0f);
            float f24 = (f15 - f16) * 2.0f;
            float f25 = (f14 - f17) * 2.0f;
            float f26 = (f15 + f16) * 2.0f;
            float f27 = 1.0f - ((f10 + f11) * 2.0f);
            int i4 = this.srcVertCount * i3;
            for (int i5 = 0; i5 < this.srcVertCount; i5++) {
                float[] fArr = this.srcPositions;
                int i6 = i5 * 3;
                float f28 = fArr[i6] * f;
                int i7 = i6 + 1;
                float f29 = fArr[i7] * f;
                int i8 = i6 + 2;
                float f30 = fArr[i8] * f;
                float f31 = (f19 * f28) + (f20 * f29) + (f21 * f30) + f3;
                float f32 = (f22 * f28) + (f23 * f29) + (f24 * f30) + f4;
                float f33 = (f28 * f25) + (f29 * f26) + (f30 * f27) + f5;
                float[] fArr2 = this.srcNormals;
                float f34 = fArr2[i6];
                float f35 = fArr2[i7];
                float f36 = fArr2[i8];
                float f37 = (f19 * f34) + (f20 * f35) + (f21 * f36);
                float f38 = (f22 * f34) + (f23 * f35) + (f24 * f36);
                float f39 = (f34 * f25) + (f35 * f26) + (f36 * f27);
                float[] fArr3 = this.batchedVertices;
                fArr3[i] = f31;
                fArr3[i + 1] = f32;
                fArr3[i + 2] = f33;
                fArr3[i + 3] = f37;
                fArr3[i + 4] = f38;
                int i9 = i + 6;
                fArr3[i + 5] = f39;
                i += 7;
                fArr3[i9] = floatBits;
            }
            int i10 = 0;
            while (i10 < this.srcIdxCount) {
                this.batchedIndices[i2] = (short) (this.srcIndices[i10] + i4);
                i10++;
                i2++;
            }
        }
        this.lastVertOffset = i;
        this.lastIdxOffset = i2;
        this.lastParticleCount = iMin;
    }

    public void render(Camera camera, Vector3 lightDir, Color ambientColor, boolean additive) {
        if (!this.glInitialized || this.shader == null || this.lastParticleCount == 0) {
            return;
        }
        this.batchedMesh.setVertices(this.batchedVertices, 0, this.lastVertOffset);
        this.batchedMesh.setIndices(this.batchedIndices, 0, this.lastIdxOffset);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        if (additive) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
            Gdx.gl.glDepthMask(false);
        } else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glDepthMask(true);
        }
        this.shader.begin();
        this.shader.setUniformMatrix("u_projViewTrans", camera.combined);
        this.shader.setUniformf("u_lightDir", lightDir.x, lightDir.y, lightDir.z);
        this.shader.setUniformf("u_ambientColor", ambientColor.r, ambientColor.g, ambientColor.b);
        this.batchedMesh.render(this.shader, 4, 0, this.lastIdxOffset);
        this.shader.end();
        if (additive) {
            Gdx.gl.glDepthMask(true);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Mesh mesh = this.batchedMesh;
        if (mesh != null) {
            mesh.dispose();
            this.batchedMesh = null;
        }
        ShaderProgram shaderProgram = this.shader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
            this.shader = null;
        }
        this.glInitialized = false;
    }
}
