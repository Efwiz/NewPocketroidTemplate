package net.mgsx.gltf.exporters;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Iterator;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.geometry.GLTFMorphTarget;
import net.mgsx.gltf.data.geometry.GLTFPrimitive;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;

/* JADX INFO: loaded from: classes12.dex */
class GLTFMeshExporter {
    private final GLTFExporter base;
    private ObjectMap<Mesh, GLTFPrimitive> layouts = new ObjectMap<>();

    public GLTFMeshExporter(GLTFExporter gLTFExporter) {
        this.base = gLTFExporter;
    }

    GLTFPrimitive exportMeshPart(MeshPart meshPart) {
        Mesh mesh = meshPart.mesh;
        GLTFPrimitive gLTFPrimitive = new GLTFPrimitive();
        gLTFPrimitive.attributes = new ObjectMap<>();
        gLTFPrimitive.mode = mapPrimitiveMode(meshPart.primitiveType);
        GLTFPrimitive gLTFPrimitive2 = this.layouts.get(mesh);
        if (gLTFPrimitive2 != null) {
            copyLayout(gLTFPrimitive, gLTFPrimitive2);
        } else {
            this.layouts.put(mesh, gLTFPrimitive);
            exportMesh(gLTFPrimitive, mesh);
        }
        if (mesh.getNumIndices() > 0) {
            ShortBuffer shortBufferBeginShorts = this.base.binManager.beginShorts(meshPart.size);
            ShortBuffer indicesBuffer = mesh.getIndicesBuffer();
            if (meshPart.offset == 0 && meshPart.size == mesh.getNumIndices()) {
                shortBufferBeginShorts.put(mesh.getIndicesBuffer());
            } else {
                short[] sArr = new short[meshPart.size];
                indicesBuffer.position(meshPart.offset);
                indicesBuffer.get(sArr);
                shortBufferBeginShorts.put(sArr);
            }
            indicesBuffer.rewind();
            GLTFAccessor gLTFAccessorObtainAccessor = this.base.obtainAccessor();
            gLTFAccessorObtainAccessor.type = GLTFTypes.TYPE_SCALAR;
            gLTFAccessorObtainAccessor.componentType = 5123;
            gLTFAccessorObtainAccessor.count = meshPart.size;
            gLTFAccessorObtainAccessor.bufferView = Integer.valueOf(this.base.binManager.end());
            gLTFPrimitive.indices = Integer.valueOf(this.base.root.accessors.size - 1);
        }
        return gLTFPrimitive;
    }

    private void copyLayout(GLTFPrimitive gLTFPrimitive, GLTFPrimitive gLTFPrimitive2) {
        gLTFPrimitive.attributes.putAll(gLTFPrimitive2.attributes);
        if (gLTFPrimitive2.targets != null) {
            gLTFPrimitive.targets = new Array<>();
            gLTFPrimitive.targets.addAll(gLTFPrimitive2.targets);
        }
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:47:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:49:0x00fe  */
    /* JADX WARN: Code duplicated, block: B:51:0x0102  */
    /* JADX WARN: Code duplicated, block: B:54:0x0111 A[LOOP:1: B:52:0x0109->B:54:0x0111, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:56:0x0138  */
    /* JADX WARN: Code duplicated, block: B:59:0x015e  */
    /* JADX WARN: Code duplicated, block: B:61:0x016a A[LOOP:3: B:60:0x0168->B:61:0x016a, LOOP_END] */
    private void exportMesh(GLTFPrimitive gLTFPrimitive, Mesh mesh) {
        String str;
        boolean z;
        String str2;
        GLTFAccessor gLTFAccessorObtainAccessor;
        VertexAttribute vertexAttribute;
        GLTFAccessor gLTFAccessor;
        int i;
        int iAccessorStrideSize;
        FloatBuffer floatBufferBeginFloats;
        int i2;
        int i3;
        FloatBuffer verticesBuffer = mesh.getVerticesBuffer();
        Array array = new Array();
        Array array2 = new Array();
        int i4 = mesh.getVertexAttributes().vertexSize / 4;
        int numVertices = mesh.getNumVertices();
        Iterator<VertexAttribute> it = mesh.getVertexAttributes().iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            int i5 = 5123;
            i5 = 5126;
            String str3 = GLTFTypes.TYPE_VEC4;
            boolean z2 = true;
            if (zHasNext) {
                VertexAttribute next = it.next();
                if (next.usage == 1) {
                    i5 = 5126;
                    str = "POSITION";
                    str3 = GLTFTypes.TYPE_VEC3;
                } else {
                    if (next.usage == 8) {
                        i5 = 5126;
                        str3 = GLTFTypes.TYPE_VEC3;
                        str = "NORMAL";
                    } else if (next.usage == 128) {
                        i5 = 5126;
                        str = "TANGENT";
                    } else {
                        if (next.usage == 2) {
                            str2 = "COLOR_" + next.unit;
                            if (next.type != 5126) {
                                if (next.type != 5123) {
                                    i5 = 5121;
                                    if (next.type != 5121) {
                                        throw new GLTFUnsupportedException("color attribute format not supported");
                                    }
                                }
                            }
                            str = str2;
                        } else if (next.usage == 16) {
                            str2 = "TEXCOORD_" + next.unit;
                            str3 = GLTFTypes.TYPE_VEC2;
                        } else {
                            if (next.usage == 512) {
                                str = "POSITION";
                                str3 = GLTFTypes.TYPE_VEC3;
                            } else if (next.usage == 1024) {
                                str3 = GLTFTypes.TYPE_VEC3;
                                str = "NORMAL";
                            } else if (next.usage == 2048) {
                                str = "TANGENT";
                            } else if (next.usage == 64) {
                                while (next.unit >= array.size) {
                                    array.add(null);
                                }
                                while (next.unit >= array2.size) {
                                    array2.add(null);
                                }
                                FloatBuffer floatBufferAllocate = FloatBuffer.allocate(numVertices);
                                array.set(next.unit, floatBufferAllocate);
                                ShortBuffer shortBufferAllocate = ShortBuffer.allocate(numVertices);
                                array2.set(next.unit, shortBufferAllocate);
                                for (int i6 = 0; i6 < numVertices; i6++) {
                                    verticesBuffer.position((i6 * i4) + (next.offset / 4));
                                    short s = (short) (((int) verticesBuffer.get()) & 65535);
                                    floatBufferAllocate.put(verticesBuffer.get());
                                    shortBufferAllocate.put(s);
                                }
                            } else {
                                throw new GLTFUnsupportedException("unsupported vertex attribute " + next.alias);
                            }
                            z2 = false;
                            z = true;
                            gLTFAccessorObtainAccessor = this.base.obtainAccessor();
                            gLTFAccessorObtainAccessor.type = str3;
                            gLTFAccessorObtainAccessor.componentType = i5;
                            gLTFAccessorObtainAccessor.count = numVertices;
                            if (z2) {
                                gLTFAccessor = gLTFAccessorObtainAccessor;
                                vertexAttribute = next;
                                computeBounds(gLTFAccessorObtainAccessor, verticesBuffer, next, numVertices, i4);
                            } else {
                                vertexAttribute = next;
                                gLTFAccessor = gLTFAccessorObtainAccessor;
                            }
                            if (z) {
                                if (gLTFPrimitive.targets == null) {
                                    gLTFPrimitive.targets = new Array<>();
                                }
                                while (gLTFPrimitive.targets.size <= vertexAttribute.unit) {
                                    gLTFPrimitive.targets.add(new GLTFMorphTarget());
                                }
                                gLTFPrimitive.targets.get(vertexAttribute.unit).put(str, Integer.valueOf(this.base.root.accessors.size - 1));
                            } else {
                                gLTFPrimitive.attributes.put(str, Integer.valueOf(this.base.root.accessors.size - 1));
                            }
                            i = 4;
                            iAccessorStrideSize = GLTFTypes.accessorStrideSize(gLTFAccessor) / 4;
                            floatBufferBeginFloats = this.base.binManager.beginFloats(numVertices * iAccessorStrideSize);
                            i2 = 0;
                            while (i2 < numVertices) {
                                verticesBuffer.position((i2 * i4) + (vertexAttribute.offset / i));
                                for (i3 = 0; i3 < iAccessorStrideSize; i3++) {
                                    floatBufferBeginFloats.put(verticesBuffer.get());
                                }
                                i2++;
                                i = 4;
                            }
                            gLTFAccessor.bufferView = Integer.valueOf(this.base.binManager.end());
                        }
                        i5 = 5126;
                        str = str2;
                    }
                    z2 = false;
                }
                z = false;
                gLTFAccessorObtainAccessor = this.base.obtainAccessor();
                gLTFAccessorObtainAccessor.type = str3;
                gLTFAccessorObtainAccessor.componentType = i5;
                gLTFAccessorObtainAccessor.count = numVertices;
                if (z2) {
                    gLTFAccessor = gLTFAccessorObtainAccessor;
                    vertexAttribute = next;
                    computeBounds(gLTFAccessorObtainAccessor, verticesBuffer, next, numVertices, i4);
                } else {
                    vertexAttribute = next;
                    gLTFAccessor = gLTFAccessorObtainAccessor;
                }
                if (z) {
                    if (gLTFPrimitive.targets == null) {
                        gLTFPrimitive.targets = new Array<>();
                    }
                    while (gLTFPrimitive.targets.size <= vertexAttribute.unit) {
                        gLTFPrimitive.targets.add(new GLTFMorphTarget());
                    }
                    gLTFPrimitive.targets.get(vertexAttribute.unit).put(str, Integer.valueOf(this.base.root.accessors.size - 1));
                } else {
                    gLTFPrimitive.attributes.put(str, Integer.valueOf(this.base.root.accessors.size - 1));
                }
                i = 4;
                iAccessorStrideSize = GLTFTypes.accessorStrideSize(gLTFAccessor) / 4;
                floatBufferBeginFloats = this.base.binManager.beginFloats(numVertices * iAccessorStrideSize);
                i2 = 0;
                while (i2 < numVertices) {
                    verticesBuffer.position((i2 * i4) + (vertexAttribute.offset / i));
                    while (i3 < iAccessorStrideSize) {
                        floatBufferBeginFloats.put(verticesBuffer.get());
                    }
                    i2++;
                    i = 4;
                }
                gLTFAccessor.bufferView = Integer.valueOf(this.base.binManager.end());
            } else {
                if (array.size > 0) {
                    int i7 = array.size <= 4 ? 1 : 2;
                    for (int i8 = 0; i8 < i7; i8++) {
                        GLTFAccessor gLTFAccessorObtainAccessor2 = this.base.obtainAccessor();
                        FloatBuffer floatBufferBeginFloats2 = this.base.binManager.beginFloats(numVertices * 4);
                        for (int i9 = 0; i9 < numVertices; i9++) {
                            for (int i10 = 0; i10 < 4; i10++) {
                                FloatBuffer floatBuffer = (FloatBuffer) array.get((i8 * 4) + i10);
                                if (floatBuffer != null) {
                                    floatBufferBeginFloats2.put(floatBuffer.get(i9));
                                } else {
                                    floatBufferBeginFloats2.put(0.0f);
                                }
                            }
                        }
                        gLTFAccessorObtainAccessor2.type = GLTFTypes.TYPE_VEC4;
                        gLTFAccessorObtainAccessor2.componentType = 5126;
                        gLTFAccessorObtainAccessor2.count = numVertices;
                        gLTFAccessorObtainAccessor2.bufferView = Integer.valueOf(this.base.binManager.end());
                        gLTFPrimitive.attributes.put("WEIGHTS_" + i8, Integer.valueOf(this.base.root.accessors.size - 1));
                    }
                    for (int i11 = 0; i11 < i7; i11++) {
                        GLTFAccessor gLTFAccessorObtainAccessor3 = this.base.obtainAccessor();
                        ShortBuffer shortBufferBeginShorts = this.base.binManager.beginShorts(numVertices * 4);
                        for (int i12 = 0; i12 < numVertices; i12++) {
                            for (int i13 = 0; i13 < 4; i13++) {
                                ShortBuffer shortBuffer = (ShortBuffer) array2.get((i11 * 4) + i13);
                                if (shortBuffer != null) {
                                    shortBufferBeginShorts.put(shortBuffer.get(i12));
                                } else {
                                    shortBufferBeginShorts.put((short) 0);
                                }
                            }
                        }
                        gLTFAccessorObtainAccessor3.type = GLTFTypes.TYPE_VEC4;
                        gLTFAccessorObtainAccessor3.componentType = 5123;
                        gLTFAccessorObtainAccessor3.count = numVertices;
                        gLTFAccessorObtainAccessor3.bufferView = Integer.valueOf(this.base.binManager.end());
                        gLTFPrimitive.attributes.put("JOINTS_" + i11, Integer.valueOf(this.base.root.accessors.size - 1));
                    }
                    return;
                }
                return;
            }
        }
    }

    private void computeBounds(GLTFAccessor gLTFAccessor, FloatBuffer floatBuffer, VertexAttribute vertexAttribute, int i, int i2) {
        gLTFAccessor.min = new float[vertexAttribute.numComponents];
        gLTFAccessor.max = new float[vertexAttribute.numComponents];
        int i3 = vertexAttribute.offset / 4;
        for (int i4 = 0; i4 < i; i4++) {
            for (int i5 = 0; i5 < vertexAttribute.numComponents; i5++) {
                float f = floatBuffer.get((i4 * i2) + i3 + i5);
                if (i4 == 0) {
                    gLTFAccessor.min[i5] = f;
                    gLTFAccessor.max[i5] = f;
                } else {
                    gLTFAccessor.min[i5] = Math.min(gLTFAccessor.min[i5], f);
                    gLTFAccessor.max[i5] = Math.max(gLTFAccessor.max[i5], f);
                }
            }
        }
    }

    public static Integer mapPrimitiveMode(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return null;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new GLTFUnsupportedException("unsupported primitive type " + i);
        }
    }
}
