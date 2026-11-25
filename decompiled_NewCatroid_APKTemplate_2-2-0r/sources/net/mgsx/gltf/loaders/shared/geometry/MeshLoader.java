package net.mgsx.gltf.loaders.shared.geometry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.geometry.GLTFMesh;
import net.mgsx.gltf.data.geometry.GLTFPrimitive;
import net.mgsx.gltf.loaders.blender.BlenderShapeKeys;
import net.mgsx.gltf.loaders.exceptions.GLTFIllegalException;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.loaders.shared.GLTFLoaderBase;
import net.mgsx.gltf.loaders.shared.GLTFTypes;
import net.mgsx.gltf.loaders.shared.data.AccessorBuffer;
import net.mgsx.gltf.loaders.shared.data.DataResolver;
import net.mgsx.gltf.loaders.shared.material.MaterialLoader;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.model.NodePartPlus;
import net.mgsx.gltf.scene3d.model.NodePlus;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class MeshLoader {
    private ObjectMap<GLTFMesh, Array<NodePart>> meshMap = new ObjectMap<>();
    private final Array<Mesh> meshes = new Array<>();

    /* JADX WARN: Code duplicated, block: B:136:0x03a9  */
    /* JADX WARN: Code duplicated, block: B:319:0x03ac A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public void load(Node node, GLTFMesh gLTFMesh, DataResolver dataResolver, MaterialLoader materialLoader) {
        Material defaultMaterial;
        Array<NodePart> array;
        String str;
        Material material;
        String str2;
        int i;
        NodePlus nodePlus;
        Array array2;
        Material material2;
        VertexAttribute vertexAttribute;
        boolean z;
        boolean z2;
        GLTFMesh gLTFMesh2;
        DataResolver dataResolver2;
        Array<NodePart> array3;
        NodePlus nodePlus2;
        int i2;
        Array array4;
        Array array5;
        ObjectSet objectSet;
        String str3;
        String str4;
        boolean z3;
        MeshLoader meshLoader = this;
        GLTFMesh gLTFMesh3 = gLTFMesh;
        DataResolver dataResolver3 = dataResolver;
        NodePlus nodePlus3 = (NodePlus) node;
        nodePlus3.morphTargetNames = BlenderShapeKeys.parse(gLTFMesh);
        Array<NodePart> array6 = meshLoader.meshMap.get(gLTFMesh3);
        if (array6 == null) {
            Array<NodePart> array7 = new Array<>();
            Array.ArrayIterator<GLTFPrimitive> it = gLTFMesh3.primitives.iterator();
            while (it.hasNext()) {
                GLTFPrimitive next = it.next();
                int iMapPrimitiveMode = GLTFTypes.mapPrimitiveMode(next.mode);
                if (next.material != null) {
                    defaultMaterial = materialLoader.get(next.material.intValue());
                } else {
                    defaultMaterial = materialLoader.getDefaultMaterial();
                }
                Material material3 = defaultMaterial;
                Array array8 = new Array();
                Array array9 = new Array();
                ObjectSet objectSet2 = new ObjectSet();
                Array array10 = new Array();
                Array array11 = new Array();
                ObjectMap.Entries<String, Integer> it2 = next.attributes.iterator();
                boolean z4 = false;
                boolean z5 = false;
                while (true) {
                    boolean zHasNext = it2.hasNext();
                    array = array7;
                    str = "POSITION";
                    material = material3;
                    str2 = GLTFLoaderBase.TAG;
                    i = iMapPrimitiveMode;
                    nodePlus = nodePlus3;
                    if (zHasNext) {
                        ObjectMap.Entry next2 = it2.next();
                        ObjectMap.Entries<String, Integer> entries = it2;
                        String str5 = (String) next2.key;
                        int iIntValue = ((Integer) next2.value).intValue();
                        GLTFPrimitive gLTFPrimitive = next;
                        GLTFAccessor accessor = dataResolver3.getAccessor(iIntValue);
                        if (str5.equals("POSITION")) {
                            if (!GLTFTypes.TYPE_VEC3.equals(accessor.type) || accessor.componentType != 5126) {
                                throw new GLTFIllegalException("illegal position attribute format");
                            }
                            array8.add(VertexAttribute.Position());
                        } else {
                            if (str5.equals("NORMAL")) {
                                if (!GLTFTypes.TYPE_VEC3.equals(accessor.type) || accessor.componentType != 5126) {
                                    throw new GLTFIllegalException("illegal normal attribute format");
                                }
                                array8.add(VertexAttribute.Normal());
                                z3 = true;
                                z4 = true;
                            } else if (str5.equals("TANGENT")) {
                                if (!GLTFTypes.TYPE_VEC4.equals(accessor.type) || accessor.componentType != 5126) {
                                    throw new GLTFIllegalException("illegal tangent attribute format");
                                }
                                array8.add(new VertexAttribute(128, 4, ShaderProgram.TANGENT_ATTRIBUTE));
                                z3 = true;
                                z5 = true;
                            } else if (str5.startsWith("TEXCOORD_")) {
                                if (!GLTFTypes.TYPE_VEC2.equals(accessor.type)) {
                                    throw new GLTFIllegalException("illegal texture coordinate attribute type : " + accessor.type);
                                }
                                if (accessor.componentType == 5121) {
                                    throw new GLTFUnsupportedException("unsigned byte texture coordinate attribute not supported");
                                }
                                if (accessor.componentType == 5123) {
                                    throw new GLTFUnsupportedException("unsigned short texture coordinate attribute not supported");
                                }
                                if (accessor.componentType != 5126) {
                                    throw new GLTFIllegalException("illegal texture coordinate component type : " + accessor.componentType);
                                }
                                array8.add(VertexAttribute.TexCoords(meshLoader.parseAttributeUnit(str5)));
                            } else if (str5.startsWith("COLOR_")) {
                                int attributeUnit = meshLoader.parseAttributeUnit(str5);
                                String str6 = ShaderProgram.COLOR_ATTRIBUTE;
                                if (attributeUnit > 0) {
                                    str6 = ShaderProgram.COLOR_ATTRIBUTE + attributeUnit;
                                }
                                String str7 = str6;
                                if (GLTFTypes.TYPE_VEC4.equals(accessor.type)) {
                                    if (5126 == accessor.componentType) {
                                        array8.add(new VertexAttribute(2, 4, 5126, false, str7));
                                    } else if (5123 == accessor.componentType) {
                                        array8.add(new VertexAttribute(2, 4, 5123, true, str7));
                                    } else if (5121 == accessor.componentType) {
                                        array8.add(new VertexAttribute(2, 4, 5121, true, str7));
                                    } else {
                                        throw new GLTFIllegalException("illegal color attribute component type: " + accessor.type);
                                    }
                                } else if (GLTFTypes.TYPE_VEC3.equals(accessor.type)) {
                                    if (5126 == accessor.componentType) {
                                        array8.add(new VertexAttribute(2, 3, 5126, false, str7));
                                    } else if (5123 == accessor.componentType) {
                                        VertexAttribute vertexAttribute2 = new VertexAttribute(2, 4, 5123, true, str7);
                                        objectSet2.add(vertexAttribute2);
                                        array8.add(vertexAttribute2);
                                    } else if (5121 == accessor.componentType) {
                                        VertexAttribute vertexAttribute3 = new VertexAttribute(2, 4, 5121, true, str7);
                                        objectSet2.add(vertexAttribute3);
                                        array8.add(vertexAttribute3);
                                    } else {
                                        throw new GLTFIllegalException("illegal color attribute component type: " + accessor.type);
                                    }
                                } else {
                                    throw new GLTFIllegalException("illegal color attribute type: " + accessor.type);
                                }
                            } else {
                                if (str5.startsWith("WEIGHTS_")) {
                                    if (!GLTFTypes.TYPE_VEC4.equals(accessor.type)) {
                                        throw new GLTFIllegalException("illegal weight attribute type: " + accessor.type);
                                    }
                                    int attributeUnit2 = meshLoader.parseAttributeUnit(str5);
                                    if (attributeUnit2 >= array11.size) {
                                        array11.setSize(attributeUnit2 + 1);
                                    }
                                    if (accessor.componentType == 5126) {
                                        array11.set(attributeUnit2, dataResolver3.readBufferFloat(iIntValue));
                                    } else if (accessor.componentType == 5123) {
                                        array11.set(attributeUnit2, dataResolver3.readBufferUShortAsFloat(iIntValue));
                                    } else if (accessor.componentType == 5121) {
                                        array11.set(attributeUnit2, dataResolver3.readBufferUByteAsFloat(iIntValue));
                                    } else {
                                        throw new GLTFIllegalException("illegal weight attribute type: " + accessor.componentType);
                                    }
                                } else if (str5.startsWith("JOINTS_")) {
                                    if (!GLTFTypes.TYPE_VEC4.equals(accessor.type)) {
                                        throw new GLTFIllegalException("illegal joints attribute type: " + accessor.type);
                                    }
                                    int attributeUnit3 = meshLoader.parseAttributeUnit(str5);
                                    if (attributeUnit3 >= array10.size) {
                                        array10.setSize(attributeUnit3 + 1);
                                    }
                                    if (accessor.componentType == 5121) {
                                        array10.set(attributeUnit3, dataResolver3.readBufferUByte(iIntValue));
                                    } else if (accessor.componentType == 5123) {
                                        array10.set(attributeUnit3, dataResolver3.readBufferUShort(iIntValue));
                                    } else {
                                        throw new GLTFIllegalException("illegal type for joints: " + accessor.componentType);
                                    }
                                } else if (str5.startsWith("_")) {
                                    Gdx.f82app.error(GLTFLoaderBase.TAG, "skip unsupported custom attribute: " + str5);
                                } else {
                                    throw new GLTFIllegalException("illegal attribute type " + str5);
                                }
                                z3 = false;
                            }
                            if (z3) {
                                array9.add(accessor);
                            }
                            it2 = entries;
                            array7 = array;
                            material3 = material;
                            iMapPrimitiveMode = i;
                            nodePlus3 = nodePlus;
                            next = gLTFPrimitive;
                        }
                        z3 = true;
                        if (z3) {
                            array9.add(accessor);
                        }
                        it2 = entries;
                        array7 = array;
                        material3 = material;
                        iMapPrimitiveMode = i;
                        nodePlus3 = nodePlus;
                        next = gLTFPrimitive;
                    }
                }
                if (next.targets != null) {
                    nodePlus.weights = new WeightVector(next.targets.size);
                    int i3 = 0;
                    while (i3 < next.targets.size) {
                        ObjectMap.Entries<String, Integer> it3 = next.targets.get(i3).iterator();
                        while (it3.hasNext()) {
                            ObjectMap.Entries<String, Integer> entries2 = it3;
                            ObjectMap.Entry next3 = it3.next();
                            String str8 = (String) next3.key;
                            GLTFAccessor accessor2 = dataResolver3.getAccessor(((Integer) next3.value).intValue());
                            array9.add(accessor2);
                            if (str8.equals(str)) {
                                if (!GLTFTypes.TYPE_VEC3.equals(accessor2.type) || accessor2.componentType != 5126) {
                                    throw new GLTFIllegalException("illegal morph target position attribute format");
                                }
                                str4 = str;
                                str3 = str2;
                                array8.add(new VertexAttribute(512, 3, ShaderProgram.POSITION_ATTRIBUTE + i3, i3));
                            } else {
                                str3 = str2;
                                str4 = str;
                                if (str8.equals("NORMAL")) {
                                    if (!GLTFTypes.TYPE_VEC3.equals(accessor2.type) || accessor2.componentType != 5126) {
                                        throw new GLTFIllegalException("illegal morph target normal attribute format");
                                    }
                                    array8.add(new VertexAttribute(1024, 3, ShaderProgram.NORMAL_ATTRIBUTE + i3, i3));
                                } else if (str8.equals("TANGENT")) {
                                    if (!GLTFTypes.TYPE_VEC3.equals(accessor2.type) || accessor2.componentType != 5126) {
                                        throw new GLTFIllegalException("illegal morph target tangent attribute format");
                                    }
                                    array8.add(new VertexAttribute(2048, 3, ShaderProgram.TANGENT_ATTRIBUTE + i3, i3));
                                } else {
                                    throw new GLTFIllegalException("illegal morph target attribute type " + str8);
                                }
                                str = str4;
                                it3 = entries2;
                                str2 = str3;
                            }
                            str = str4;
                            it3 = entries2;
                            str2 = str3;
                        }
                        i3++;
                        str2 = str2;
                    }
                }
                String str9 = str2;
                int i4 = array10.size * 4;
                Array array12 = new Array();
                for (int i5 = 0; i5 < i4; i5++) {
                    VertexAttribute vertexAttributeBoneWeight = VertexAttribute.BoneWeight(i5);
                    array8.add(vertexAttributeBoneWeight);
                    array12.add(vertexAttributeBoneWeight);
                }
                int i6 = i;
                if (i6 == 4) {
                    if (z4) {
                        z = false;
                    } else {
                        array8.add(VertexAttribute.Normal());
                        array9.add(null);
                        z = true;
                    }
                    if (z5) {
                        array2 = array12;
                        material2 = material;
                    } else {
                        array2 = array12;
                        material2 = material;
                        PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) material2.get(PBRTextureAttribute.class, PBRTextureAttribute.NormalTexture);
                        if (pBRTextureAttribute != null) {
                            array8.add(new VertexAttribute(128, 4, ShaderProgram.TANGENT_ATTRIBUTE));
                            VertexAttribute vertexAttribute4 = null;
                            array9.add(null);
                            Array.ArrayIterator it4 = array8.iterator();
                            while (it4.hasNext()) {
                                VertexAttribute vertexAttribute5 = (VertexAttribute) it4.next();
                                Array.ArrayIterator arrayIterator = it4;
                                if (vertexAttribute5.usage == 16 && vertexAttribute5.unit == pBRTextureAttribute.uvIndex) {
                                    vertexAttribute4 = vertexAttribute5;
                                }
                                it4 = arrayIterator;
                            }
                            if (vertexAttribute4 == null) {
                                throw new GLTFIllegalException("UVs not found for normal map");
                            }
                            vertexAttribute = vertexAttribute4;
                            z2 = true;
                        }
                    }
                    vertexAttribute = null;
                    z2 = false;
                } else {
                    array2 = array12;
                    material2 = material;
                    vertexAttribute = null;
                    z = false;
                    z2 = false;
                }
                VertexAttributes vertexAttributes = new VertexAttributes((VertexAttribute[]) array8.toArray(VertexAttribute.class));
                int i7 = vertexAttributes.vertexSize / 4;
                int i8 = ((GLTFAccessor) array9.first()).count;
                VertexAttribute vertexAttribute6 = vertexAttribute;
                float[] fArr = new float[i8 * i7];
                boolean z6 = z2;
                int i9 = 0;
                while (i9 < i4) {
                    int i10 = i4;
                    Array array13 = array2;
                    VertexAttribute vertexAttribute7 = (VertexAttribute) array13.get(i9);
                    boolean z7 = z;
                    int i11 = 0;
                    while (i11 < i8) {
                        int i12 = i11 * i7;
                        VertexAttributes vertexAttributes2 = vertexAttributes;
                        int i13 = i9 / 4;
                        int i14 = (i11 * 4) + (i9 % 4);
                        fArr[i12 + (vertexAttribute7.offset / 4)] = ((int[]) array10.get(i13))[i14];
                        fArr[i12 + (vertexAttribute7.offset / 4) + 1] = ((float[]) array11.get(i13))[i14];
                        i11++;
                        material2 = material2;
                        array10 = array10;
                        vertexAttributes = vertexAttributes2;
                    }
                    i9++;
                    z = z7;
                    i4 = i10;
                    array2 = array13;
                }
                VertexAttributes vertexAttributes3 = vertexAttributes;
                Material material4 = material2;
                boolean z8 = z;
                int i15 = 0;
                while (i15 < array9.size) {
                    GLTFAccessor gLTFAccessor = (GLTFAccessor) array9.get(i15);
                    VertexAttribute vertexAttribute8 = (VertexAttribute) array8.get(i15);
                    if (gLTFAccessor == null) {
                        array4 = array8;
                        array5 = array9;
                        objectSet = objectSet2;
                    } else {
                        AccessorBuffer accessorBuffer = dataResolver3.getAccessorBuffer(gLTFAccessor);
                        ByteBuffer byteBufferPrepareForReading = accessorBuffer.prepareForReading();
                        int byteStride = accessorBuffer.getByteStride();
                        int i16 = byteStride / 4;
                        int sizeInBytes = vertexAttribute8.getSizeInBytes() / 4;
                        if (objectSet2.contains(vertexAttribute8)) {
                            if (vertexAttribute8.type == 5123) {
                                ShortBuffer shortBufferAsShortBuffer = byteBufferPrepareForReading.asShortBuffer();
                                for (int i17 = 0; i17 < gLTFAccessor.count; i17++) {
                                    shortBufferAsShortBuffer.position(i17 * 3);
                                    int i18 = (i17 * i7) + (vertexAttribute8.offset / 4);
                                    int i19 = shortBufferAsShortBuffer.get() & 65535;
                                    int i20 = shortBufferAsShortBuffer.get() & 65535;
                                    int i21 = shortBufferAsShortBuffer.get() & 65535;
                                    fArr[i18] = Float.intBitsToFloat((i19 << 16) | i20);
                                    fArr[i18 + 1] = Float.intBitsToFloat((i21 << 16) | 65535);
                                }
                                array4 = array8;
                                array5 = array9;
                                objectSet = objectSet2;
                            } else {
                                if (vertexAttribute8.type == 5121) {
                                    int i22 = 0;
                                    while (i22 < gLTFAccessor.count) {
                                        byteBufferPrepareForReading.position(i22 * 3);
                                        fArr[(i22 * i7) + (vertexAttribute8.offset / 4)] = Float.intBitsToFloat(((byteBufferPrepareForReading.get() & 255) << 16) | ((byteBufferPrepareForReading.get() & 255) << 24) | ((byteBufferPrepareForReading.get() & 255) << 8) | 255);
                                        i22++;
                                        array8 = array8;
                                        array9 = array9;
                                    }
                                }
                                array4 = array8;
                                array5 = array9;
                            }
                        } else {
                            array4 = array8;
                            array5 = array9;
                            if (byteStride % 4 != 0) {
                                int i23 = 0;
                                while (i23 < gLTFAccessor.count) {
                                    int i24 = (i23 * i7) + (vertexAttribute8.offset / 4);
                                    int i25 = i23 * byteStride;
                                    ObjectSet objectSet3 = objectSet2;
                                    int i26 = 0;
                                    while (i26 < sizeInBytes) {
                                        fArr[i24 + i26] = byteBufferPrepareForReading.getFloat(i25 + (i26 * 4));
                                        i26++;
                                        i24 = i24;
                                    }
                                    i23++;
                                    objectSet2 = objectSet3;
                                }
                            } else {
                                objectSet = objectSet2;
                                FloatBuffer floatBufferAsFloatBuffer = byteBufferPrepareForReading.asFloatBuffer();
                                for (int i27 = 0; i27 < gLTFAccessor.count; i27++) {
                                    floatBufferAsFloatBuffer.position(i27 * i16);
                                    floatBufferAsFloatBuffer.get(fArr, (i27 * i7) + (vertexAttribute8.offset / 4), sizeInBytes);
                                }
                            }
                        }
                        objectSet = objectSet2;
                    }
                    i15++;
                    array8 = array4;
                    array9 = array5;
                    objectSet2 = objectSet;
                }
                if (next.indices != null) {
                    GLTFAccessor accessor3 = dataResolver3.getAccessor(next.indices.intValue());
                    if (!accessor3.type.equals(GLTFTypes.TYPE_SCALAR)) {
                        throw new GLTFIllegalException("indices accessor must be SCALAR but was " + accessor3.type);
                    }
                    int i28 = accessor3.count;
                    switch (accessor3.componentType) {
                        case 5121:
                            gLTFMesh2 = gLTFMesh;
                            dataResolver2 = dataResolver3;
                            array3 = array;
                            nodePlus2 = nodePlus;
                            short[] sArr = new short[i28];
                            ByteBuffer bufferByte = dataResolver2.getBufferByte(accessor3);
                            for (int i29 = 0; i29 < i28; i29++) {
                                sArr[i29] = (short) (bufferByte.get() & 255);
                            }
                            generateParts(node, array3, material4, gLTFMesh2.f408name, fArr, i8, sArr, vertexAttributes3, i6, z8, z6, vertexAttribute6);
                            break;
                        case 5122:
                        case 5123:
                            array3 = array;
                            nodePlus2 = nodePlus;
                            short[] sArr2 = new short[i28];
                            dataResolver2 = dataResolver;
                            dataResolver2.getBufferShort(accessor3).get(sArr2);
                            gLTFMesh2 = gLTFMesh;
                            generateParts(node, array3, material4, gLTFMesh2.f408name, fArr, i8, sArr2, vertexAttributes3, i6, z8, z6, vertexAttribute6);
                            break;
                        case GL20.GL_INT /* 5124 */:
                        default:
                            throw new GLTFIllegalException("illegal componentType " + accessor3.componentType);
                        case 5125:
                            String str10 = str9;
                            Gdx.f82app.error(str10, "integer indices partially supported, mesh will be split");
                            Gdx.f82app.error(str10, "splitting mesh: " + i8 + " vertices, " + i28 + " indices.");
                            if (i6 == 4) {
                                i2 = 3;
                            } else {
                                if (i6 != 1) {
                                    throw new GLTFUnsupportedException("integer indices only supported for triangles or lines");
                                }
                                i2 = 2;
                            }
                            int[] iArr = new int[i28];
                            dataResolver3.getBufferInt(accessor3).get(iArr);
                            Array array14 = new Array();
                            Array array15 = new Array();
                            MeshSpliter.split(array14, array15, fArr, vertexAttributes3, iArr, i2);
                            VertexAttributes vertexAttributes4 = vertexAttributes3;
                            int i30 = vertexAttributes4.vertexSize / 4;
                            int i31 = array15.size;
                            int i32 = 0;
                            int i33 = 0;
                            int i34 = 0;
                            while (i34 < i31) {
                                float[] fArr2 = (float[]) array14.get(i34);
                                short[] sArr3 = (short[]) array15.get(i34);
                                int length = fArr2.length / i30;
                                int length2 = i33 + sArr3.length;
                                Gdx.f82app.error(str10, "generate mesh: " + length + " vertices, " + sArr3.length + " indices.");
                                int i35 = i6;
                                generateParts(node, array, material4, gLTFMesh.f408name, fArr2, length, sArr3, vertexAttributes4, i35, z8, z6, vertexAttribute6);
                                i34++;
                                i32 += length;
                                i33 = length2;
                                array14 = array14;
                                nodePlus = nodePlus;
                                i31 = i31;
                                i6 = i35;
                                array15 = array15;
                                str10 = str10;
                                array = array;
                                vertexAttributes4 = vertexAttributes4;
                            }
                            array3 = array;
                            nodePlus2 = nodePlus;
                            Gdx.f82app.error(str10, "mesh split: " + array3.size + " meshes generated: " + i32 + " vertices, " + i33 + " indices.");
                            gLTFMesh2 = gLTFMesh;
                            dataResolver2 = dataResolver;
                            break;
                    }
                } else {
                    gLTFMesh2 = gLTFMesh;
                    dataResolver2 = dataResolver3;
                    array3 = array;
                    nodePlus2 = nodePlus;
                    generateParts(node, array3, material4, gLTFMesh2.f408name, fArr, i8, null, vertexAttributes3, i6, z8, z6, vertexAttribute6);
                }
                dataResolver3 = dataResolver2;
                array7 = array3;
                nodePlus3 = nodePlus2;
                meshLoader = this;
                gLTFMesh3 = gLTFMesh2;
            }
            GLTFMesh gLTFMesh4 = gLTFMesh3;
            Array<NodePart> array16 = array7;
            meshLoader.meshMap.put(gLTFMesh4, array16);
            array6 = array16;
        }
        node.parts.addAll(array6);
    }

    private void generateParts(Node node, Array<NodePart> array, Material material, String str, float[] fArr, int i, short[] sArr, VertexAttributes vertexAttributes, int i2, boolean z, boolean z2, VertexAttribute vertexAttribute) {
        if (fArr.length == 0 || (sArr != null && sArr.length == 0)) {
            return;
        }
        if (z || z2) {
            if (z && z2) {
                Gdx.f82app.log(GLTFLoaderBase.TAG, "compute normals and tangents for primitive " + str);
            } else if (z2) {
                Gdx.f82app.log(GLTFLoaderBase.TAG, "compute tangents for primitive " + str);
            } else {
                Gdx.f82app.log(GLTFLoaderBase.TAG, "compute normals for primitive " + str);
            }
            MeshTangentSpaceGenerator.computeTangentSpace(fArr, sArr, vertexAttributes, z, z2, vertexAttribute);
        }
        Mesh mesh = new Mesh(true, i, sArr == null ? 0 : sArr.length, vertexAttributes);
        this.meshes.add(mesh);
        mesh.setVertices(fArr);
        if (sArr != null) {
            mesh.setIndices(sArr);
        }
        MeshPart meshPart = new MeshPart(str, mesh, 0, sArr == null ? i : sArr.length, i2);
        NodePartPlus nodePartPlus = new NodePartPlus();
        nodePartPlus.morphTargets = ((NodePlus) node).weights;
        nodePartPlus.meshPart = meshPart;
        nodePartPlus.material = material;
        array.add(nodePartPlus);
    }

    private int parseAttributeUnit(String str) {
        try {
            return Integer.parseInt(str.substring(str.lastIndexOf(95) + 1));
        } catch (NumberFormatException unused) {
            throw new GLTFIllegalException("illegal attribute name " + str);
        }
    }

    public Array<? extends Mesh> getMeshes() {
        return this.meshes;
    }
}
