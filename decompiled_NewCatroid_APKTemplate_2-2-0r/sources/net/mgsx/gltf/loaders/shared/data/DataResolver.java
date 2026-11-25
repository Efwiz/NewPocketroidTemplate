package net.mgsx.gltf.loaders.shared.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import net.mgsx.gltf.data.GLTF;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.data.GLTFBufferView;
import net.mgsx.gltf.loaders.exceptions.GLTFUnsupportedException;
import net.mgsx.gltf.loaders.shared.GLTFTypes;

/* JADX INFO: loaded from: classes12.dex */
public class DataResolver {
    private DataFileResolver dataFileResolver;
    private GLTF glModel;

    public DataResolver(GLTF gltf, DataFileResolver dataFileResolver) {
        this.glModel = gltf;
        this.dataFileResolver = dataFileResolver;
    }

    public GLTFAccessor getAccessor(int i) {
        return this.glModel.accessors.get(i);
    }

    public float[] readBufferFloat(int i) {
        GLTFAccessor gLTFAccessor = this.glModel.accessors.get(i);
        AccessorBuffer accessorBuffer = getAccessorBuffer(gLTFAccessor);
        ByteBuffer byteBufferPrepareForReading = accessorBuffer.prepareForReading();
        float[] fArr = new float[GLTFTypes.accessorSize(gLTFAccessor) / 4];
        int iAccessorTypeSize = GLTFTypes.accessorTypeSize(gLTFAccessor);
        int byteStride = accessorBuffer.getByteStride() - (iAccessorTypeSize * 4);
        if (byteStride == 0) {
            byteBufferPrepareForReading.asFloatBuffer().get(fArr);
        } else {
            for (int i2 = 0; i2 < gLTFAccessor.count; i2++) {
                for (int i3 = 0; i3 < iAccessorTypeSize; i3++) {
                    fArr[(i2 * iAccessorTypeSize) + i3] = byteBufferPrepareForReading.getFloat();
                }
                byteBufferPrepareForReading.position(byteBufferPrepareForReading.position() + byteStride);
            }
        }
        return fArr;
    }

    public int[] readBufferUByte(int i) {
        GLTFAccessor gLTFAccessor = this.glModel.accessors.get(i);
        AccessorBuffer accessorBuffer = getAccessorBuffer(gLTFAccessor);
        ByteBuffer byteBufferPrepareForReading = accessorBuffer.prepareForReading();
        int iAccessorSize = GLTFTypes.accessorSize(gLTFAccessor);
        int[] iArr = new int[iAccessorSize];
        int iAccessorTypeSize = GLTFTypes.accessorTypeSize(gLTFAccessor);
        int byteStride = accessorBuffer.getByteStride() - iAccessorTypeSize;
        if (byteStride == 0) {
            for (int i2 = 0; i2 < iAccessorSize; i2++) {
                iArr[i2] = byteBufferPrepareForReading.get() & 255;
            }
        } else {
            for (int i3 = 0; i3 < gLTFAccessor.count; i3++) {
                for (int i4 = 0; i4 < iAccessorTypeSize; i4++) {
                    iArr[(i3 * iAccessorTypeSize) + i4] = byteBufferPrepareForReading.get() & 255;
                }
                byteBufferPrepareForReading.position(byteBufferPrepareForReading.position() + byteStride);
            }
        }
        return iArr;
    }

    public int[] readBufferUShort(int i) {
        GLTFAccessor gLTFAccessor = this.glModel.accessors.get(i);
        AccessorBuffer accessorBuffer = getAccessorBuffer(gLTFAccessor);
        ByteBuffer byteBufferPrepareForReading = accessorBuffer.prepareForReading();
        int iAccessorSize = GLTFTypes.accessorSize(gLTFAccessor) / 2;
        int[] iArr = new int[iAccessorSize];
        int iAccessorTypeSize = GLTFTypes.accessorTypeSize(gLTFAccessor);
        int byteStride = accessorBuffer.getByteStride() - (iAccessorTypeSize * 2);
        if (byteStride == 0) {
            ShortBuffer shortBufferAsShortBuffer = byteBufferPrepareForReading.asShortBuffer();
            for (int i2 = 0; i2 < iAccessorSize; i2++) {
                iArr[i2] = shortBufferAsShortBuffer.get() & 65535;
            }
        } else {
            for (int i3 = 0; i3 < gLTFAccessor.count; i3++) {
                for (int i4 = 0; i4 < iAccessorTypeSize; i4++) {
                    iArr[(i3 * iAccessorTypeSize) + i4] = byteBufferPrepareForReading.getShort() & 65535;
                }
                byteBufferPrepareForReading.position(byteBufferPrepareForReading.position() + byteStride);
            }
        }
        return iArr;
    }

    public float[] readBufferUShortAsFloat(int i) {
        int[] bufferUShort = readBufferUShort(i);
        float[] fArr = new float[bufferUShort.length];
        for (int i2 = 0; i2 < bufferUShort.length; i2++) {
            fArr[i2] = bufferUShort[i2] / 65535.0f;
        }
        return fArr;
    }

    public float[] readBufferUByteAsFloat(int i) {
        int[] bufferUByte = readBufferUByte(i);
        float[] fArr = new float[bufferUByte.length];
        for (int i2 = 0; i2 < bufferUByte.length; i2++) {
            fArr[i2] = bufferUByte[i2] / 255.0f;
        }
        return fArr;
    }

    public FloatBuffer getBufferFloat(int i) {
        return getBufferFloat(this.glModel.accessors.get(i));
    }

    public GLTFBufferView getBufferView(int i) {
        return this.glModel.bufferViews.get(i);
    }

    public FloatBuffer getBufferFloat(GLTFAccessor gLTFAccessor) {
        return getBufferByte(gLTFAccessor).asFloatBuffer();
    }

    public IntBuffer getBufferInt(GLTFAccessor gLTFAccessor) {
        return getBufferByte(gLTFAccessor).asIntBuffer();
    }

    public ShortBuffer getBufferShort(GLTFAccessor gLTFAccessor) {
        return getBufferByte(gLTFAccessor).asShortBuffer();
    }

    public ByteBuffer getBufferByte(GLTFAccessor gLTFAccessor) {
        return getAccessorBuffer(gLTFAccessor).prepareForReading();
    }

    public AccessorBuffer getAccessorBuffer(GLTFAccessor gLTFAccessor) {
        AccessorBuffer accessorBufferFromZeros;
        if (gLTFAccessor.bufferView != null) {
            accessorBufferFromZeros = AccessorBuffer.fromBufferView(gLTFAccessor, this.glModel.bufferViews.get(gLTFAccessor.bufferView.intValue()), this.dataFileResolver);
        } else {
            accessorBufferFromZeros = AccessorBuffer.fromZeros(gLTFAccessor);
        }
        if (gLTFAccessor.sparse != null) {
            accessorBufferFromZeros.prepareForWriting();
            patchSparseValues(gLTFAccessor, accessorBufferFromZeros);
        }
        accessorBufferFromZeros.prepareForReading();
        return accessorBufferFromZeros;
    }

    private void patchSparseValues(GLTFAccessor gLTFAccessor, AccessorBuffer accessorBuffer) {
        int i;
        GLTFBufferView bufferView = getBufferView(gLTFAccessor.sparse.indices.bufferView);
        ByteBuffer byteBufferOrder = this.dataFileResolver.getBuffer(bufferView.buffer.intValue()).asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.position(gLTFAccessor.sparse.indices.byteOffset + bufferView.byteOffset);
        GLTFBufferView bufferView2 = getBufferView(gLTFAccessor.sparse.values.bufferView);
        ByteBuffer byteBufferOrder2 = this.dataFileResolver.getBuffer(bufferView2.buffer.intValue()).asReadOnlyBuffer().order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder2.position(gLTFAccessor.sparse.values.byteOffset + bufferView2.byteOffset);
        byte[] bArr = new byte[GLTFTypes.accessorStrideSize(gLTFAccessor)];
        for (int i2 = 0; i2 < gLTFAccessor.sparse.count; i2++) {
            int i3 = gLTFAccessor.sparse.indices.componentType;
            if (i3 == 5121) {
                i = byteBufferOrder.get() & 255;
            } else if (i3 == 5123) {
                i = byteBufferOrder.getShort() & 65535;
            } else if (i3 == 5125) {
                long j = ((long) byteBufferOrder.getInt()) & 4294967295L;
                if (j > 2147483647L) {
                    throw new GLTFUnsupportedException("very large indices can not be parsed");
                }
                i = (int) j;
            } else {
                throw new GLTFUnsupportedException("unsupported indices type");
            }
            byteBufferOrder2.get(bArr);
            ByteBuffer data = accessorBuffer.getData();
            data.position(accessorBuffer.getByteOffset() + (i * accessorBuffer.getByteStride()));
            data.put(bArr);
        }
    }

    public ByteBuffer getBufferByte(GLTFBufferView gLTFBufferView) {
        ByteBuffer buffer = this.dataFileResolver.getBuffer(gLTFBufferView.buffer.intValue());
        buffer.position(gLTFBufferView.byteOffset);
        return buffer;
    }
}
