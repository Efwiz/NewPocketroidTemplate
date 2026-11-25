package com.badlogic.gdx.graphics.g3d.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMeshPart;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNode;
import com.badlogic.gdx.graphics.g3d.model.data.ModelNodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.eclipse.jgit.transport.WalkEncryption;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/loader/ObjLoader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class ObjLoader extends ModelLoader<ObjLoaderParameters> {
    public static boolean logWarning = false;
    final Array<Group> groups;
    final FloatArray norms;
    final FloatArray uvs;
    final FloatArray verts;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/loader/ObjLoader$ObjLoaderParameters.class
      classes8.dex
     */
    public static class ObjLoaderParameters extends ModelLoader.ModelParameters {
        public boolean flipV;

        public ObjLoaderParameters() {
        }

        public ObjLoaderParameters(boolean z) {
            this.flipV = z;
        }
    }

    public ObjLoader() {
        this(null);
    }

    public ObjLoader(FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.verts = new FloatArray(300);
        this.norms = new FloatArray(300);
        this.uvs = new FloatArray(200);
        this.groups = new Array<>(10);
    }

    public Model loadModel(FileHandle fileHandle, boolean z) {
        return loadModel(fileHandle, new ObjLoaderParameters(z));
    }

    @Override // com.badlogic.gdx.assets.loaders.ModelLoader
    public ModelData loadModelData(FileHandle fileHandle, ObjLoaderParameters objLoaderParameters) {
        return loadModelData(fileHandle, objLoaderParameters != null && objLoaderParameters.flipV);
    }

    protected ModelData loadModelData(FileHandle fileHandle, boolean z) {
        int i;
        int i2;
        int i3;
        char cCharAt;
        if (logWarning) {
            Gdx.f82app.error("ObjLoader", "Wavefront (OBJ) is not fully supported, consult the documentation for more information");
        }
        MtlLoader mtlLoader = new MtlLoader();
        Group group = new Group("default");
        this.groups.add(group);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileHandle.read()), 4096);
        while (true) {
            try {
                String line = bufferedReader.readLine();
                i = 3;
                i2 = 2;
                if (line == null) {
                    break;
                }
                String[] strArrSplit = line.split(WalkEncryption.Vals.REGEX_WS);
                if (strArrSplit.length < 1) {
                    break;
                }
                if (strArrSplit[0].length() != 0 && (cCharAt = strArrSplit[0].toLowerCase().charAt(0)) != '#') {
                    if (cCharAt == 'v') {
                        if (strArrSplit[0].length() == 1) {
                            this.verts.add(Float.parseFloat(strArrSplit[1]));
                            this.verts.add(Float.parseFloat(strArrSplit[2]));
                            this.verts.add(Float.parseFloat(strArrSplit[3]));
                        } else if (strArrSplit[0].charAt(1) == 'n') {
                            this.norms.add(Float.parseFloat(strArrSplit[1]));
                            this.norms.add(Float.parseFloat(strArrSplit[2]));
                            this.norms.add(Float.parseFloat(strArrSplit[3]));
                        } else if (strArrSplit[0].charAt(1) == 't') {
                            this.uvs.add(Float.parseFloat(strArrSplit[1]));
                            this.uvs.add(z ? 1.0f - Float.parseFloat(strArrSplit[2]) : Float.parseFloat(strArrSplit[2]));
                        }
                    } else if (cCharAt == 'f') {
                        Array<Integer> array = group.faces;
                        for (int i4 = 1; i4 < strArrSplit.length - 2; i4++) {
                            String[] strArrSplit2 = strArrSplit[1].split("/");
                            array.add(Integer.valueOf(getIndex(strArrSplit2[0], this.verts.size)));
                            if (strArrSplit2.length > 2) {
                                if (i4 == 1) {
                                    group.hasNorms = true;
                                }
                                array.add(Integer.valueOf(getIndex(strArrSplit2[2], this.norms.size)));
                            }
                            if (strArrSplit2.length > 1 && strArrSplit2[1].length() > 0) {
                                if (i4 == 1) {
                                    group.hasUVs = true;
                                }
                                array.add(Integer.valueOf(getIndex(strArrSplit2[1], this.uvs.size)));
                            }
                            String[] strArrSplit3 = strArrSplit[i4 + 1].split("/");
                            array.add(Integer.valueOf(getIndex(strArrSplit3[0], this.verts.size)));
                            if (strArrSplit3.length > 2) {
                                array.add(Integer.valueOf(getIndex(strArrSplit3[2], this.norms.size)));
                            }
                            if (strArrSplit3.length > 1 && strArrSplit3[1].length() > 0) {
                                array.add(Integer.valueOf(getIndex(strArrSplit3[1], this.uvs.size)));
                            }
                            String[] strArrSplit4 = strArrSplit[i4 + 2].split("/");
                            array.add(Integer.valueOf(getIndex(strArrSplit4[0], this.verts.size)));
                            if (strArrSplit4.length > 2) {
                                array.add(Integer.valueOf(getIndex(strArrSplit4[2], this.norms.size)));
                            }
                            if (strArrSplit4.length > 1 && strArrSplit4[1].length() > 0) {
                                array.add(Integer.valueOf(getIndex(strArrSplit4[1], this.uvs.size)));
                            }
                            group.numFaces++;
                        }
                    } else if (cCharAt == 'o' || cCharAt == 'g') {
                        if (strArrSplit.length > 1) {
                            group = setActiveGroup(strArrSplit[1]);
                        } else {
                            group = setActiveGroup("default");
                        }
                    } else if (strArrSplit[0].equals("mtllib")) {
                        mtlLoader.load(fileHandle.parent().child(strArrSplit[1]));
                    } else if (strArrSplit[0].equals("usemtl")) {
                        if (strArrSplit.length == 1) {
                            group.materialName = "default";
                        } else {
                            group.materialName = strArrSplit[1].replace('.', '_');
                        }
                    }
                }
            } catch (IOException unused) {
                return null;
            }
        }
        bufferedReader.close();
        int i5 = 0;
        while (i5 < this.groups.size) {
            if (this.groups.get(i5).numFaces < 1) {
                this.groups.removeIndex(i5);
                i5--;
            }
            i5++;
        }
        if (this.groups.size < 1) {
            return null;
        }
        int i6 = this.groups.size;
        ModelData modelData = new ModelData();
        int i7 = 0;
        int i8 = 0;
        while (i7 < i6) {
            Group group2 = this.groups.get(i7);
            Array<Integer> array2 = group2.faces;
            int i9 = array2.size;
            int i10 = group2.numFaces;
            boolean z2 = group2.hasNorms;
            boolean z3 = group2.hasUVs;
            int i11 = i10 * i;
            float[] fArr = new float[i11 * ((z2 ? i : 0) + 3 + (z3 ? i2 : 0))];
            int i12 = 0;
            int i13 = 0;
            while (i12 < i9) {
                int i14 = i12 + 1;
                int i15 = i6;
                int iIntValue = array2.get(i12).intValue() * 3;
                int i16 = i9;
                int i17 = i7;
                fArr[i13] = this.verts.get(iIntValue);
                MtlLoader mtlLoader2 = mtlLoader;
                fArr[i13 + 1] = this.verts.get(iIntValue + 1);
                int i18 = i13 + 3;
                fArr[i13 + 2] = this.verts.get(iIntValue + 2);
                if (z2) {
                    int iIntValue2 = array2.get(i14).intValue() * 3;
                    fArr[i18] = this.norms.get(iIntValue2);
                    fArr[i13 + 4] = this.norms.get(iIntValue2 + 1);
                    fArr[i13 + 5] = this.norms.get(iIntValue2 + 2);
                    i18 = i13 + 6;
                    i14 = i12 + 2;
                }
                if (z3) {
                    int iIntValue3 = array2.get(i14).intValue() * 2;
                    fArr[i18] = this.uvs.get(iIntValue3);
                    fArr[i18 + 1] = this.uvs.get(iIntValue3 + 1);
                    i12 = i14 + 1;
                    i13 = i18 + 2;
                } else {
                    i13 = i18;
                    i12 = i14;
                }
                i6 = i15;
                i9 = i16;
                i7 = i17;
                mtlLoader = mtlLoader2;
            }
            int i19 = i6;
            MtlLoader mtlLoader3 = mtlLoader;
            int i20 = i7;
            if (i11 >= 32767) {
                i11 = 0;
            }
            short[] sArr = new short[i11];
            if (i11 > 0) {
                for (int i21 = 0; i21 < i11; i21++) {
                    sArr[i21] = (short) i21;
                }
            }
            Array array3 = new Array();
            array3.add(new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE));
            if (z2) {
                array3.add(new VertexAttribute(8, 3, ShaderProgram.NORMAL_ATTRIBUTE));
            }
            if (z3) {
                i3 = 2;
                array3.add(new VertexAttribute(16, 2, "a_texCoord0"));
            } else {
                i3 = 2;
            }
            i8++;
            String string = Integer.toString(i8);
            String str = "default".equals(group2.f101name) ? "node" + string : group2.f101name;
            String str2 = "default".equals(group2.f101name) ? "mesh" + string : group2.f101name;
            String str3 = "default".equals(group2.f101name) ? DependencyDownloader.TMP_SUFFIX + string : group2.f101name;
            ModelNode modelNode = new ModelNode();
            modelNode.f110id = str;
            modelNode.meshId = str2;
            modelNode.scale = new Vector3(1.0f, 1.0f, 1.0f);
            modelNode.translation = new Vector3();
            modelNode.rotation = new Quaternion();
            ModelNodePart modelNodePart = new ModelNodePart();
            modelNodePart.meshPartId = str3;
            modelNodePart.materialId = group2.materialName;
            modelNode.parts = new ModelNodePart[]{modelNodePart};
            ModelMeshPart modelMeshPart = new ModelMeshPart();
            modelMeshPart.f109id = str3;
            modelMeshPart.indices = sArr;
            modelMeshPart.primitiveType = 4;
            ModelMesh modelMesh = new ModelMesh();
            modelMesh.f108id = str2;
            modelMesh.attributes = (VertexAttribute[]) array3.toArray(VertexAttribute.class);
            modelMesh.vertices = fArr;
            modelMesh.parts = new ModelMeshPart[]{modelMeshPart};
            modelData.nodes.add(modelNode);
            modelData.meshes.add(modelMesh);
            modelData.materials.add(mtlLoader3.getMaterial(group2.materialName));
            mtlLoader = mtlLoader3;
            i2 = i3;
            i7 = i20 + 1;
            i = 3;
            i6 = i19;
        }
        if (this.verts.size > 0) {
            this.verts.clear();
        }
        if (this.norms.size > 0) {
            this.norms.clear();
        }
        if (this.uvs.size > 0) {
            this.uvs.clear();
        }
        if (this.groups.size > 0) {
            this.groups.clear();
        }
        return modelData;
    }

    private Group setActiveGroup(String str) {
        Array.ArrayIterator<Group> it = this.groups.iterator();
        while (it.hasNext()) {
            Group next = it.next();
            if (next.f101name.equals(str)) {
                return next;
            }
        }
        Group group = new Group(str);
        this.groups.add(group);
        return group;
    }

    private int getIndex(String str, int i) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int i2 = Integer.parseInt(str);
        return i2 < 0 ? i + i2 : i2 - 1;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/loader/ObjLoader$Group.class
      classes8.dex
     */
    private static class Group {
        boolean hasNorms;
        boolean hasUVs;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f101name;
        Array<Integer> faces = new Array<>(200);
        int numFaces = 0;
        Material mat = new Material("");
        String materialName = "default";

        Group(String str) {
            this.f101name = str;
        }
    }
}
