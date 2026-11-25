package net.mgsx.gltf.loaders.shared.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.mgsx.gltf.data.data.GLTFAccessor;
import net.mgsx.gltf.data.data.GLTFBufferView;
import net.mgsx.gltf.loaders.shared.GLTFTypes;

/* JADX INFO: loaded from: classes12.dex */
public class AccessorBuffer {
    private final GLTFAccessor accessor;
    private final GLTFBufferView bufferView;
    private int byteOffset;
    private ByteBuffer data;

    AccessorBuffer(GLTFAccessor gLTFAccessor, GLTFBufferView gLTFBufferView, ByteBuffer byteBuffer) {
        this.accessor = gLTFAccessor;
        this.bufferView = gLTFBufferView;
        this.data = byteBuffer;
        if (gLTFBufferView != null) {
            this.byteOffset = gLTFBufferView.byteOffset + gLTFAccessor.byteOffset;
        } else {
            this.byteOffset = 0;
        }
    }

    public int getByteStride() {
        GLTFBufferView gLTFBufferView = this.bufferView;
        if (gLTFBufferView != null && gLTFBufferView.byteStride != null) {
            return this.bufferView.byteStride.intValue();
        }
        return GLTFTypes.accessorStrideSize(this.accessor);
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public void prepareForWriting() {
        if (this.bufferView != null) {
            prepareForReading();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.data.remaining());
            byteBufferAllocate.put(this.data);
            byteBufferAllocate.order(this.data.order());
            byteBufferAllocate.flip();
            this.data = byteBufferAllocate;
            this.byteOffset = 0;
        }
    }

    public ByteBuffer prepareForReading() {
        this.data.position(getByteOffset());
        return this.data;
    }

    public ByteBuffer getData() {
        return this.data;
    }

    public static AccessorBuffer fromBufferView(GLTFAccessor gLTFAccessor, GLTFBufferView gLTFBufferView, DataFileResolver dataFileResolver) {
        AccessorBuffer accessorBuffer = new AccessorBuffer(gLTFAccessor, gLTFBufferView, dataFileResolver.getBuffer(gLTFBufferView.buffer.intValue()));
        accessorBuffer.data.position(accessorBuffer.getByteOffset());
        return accessorBuffer;
    }

    public static AccessorBuffer fromZeros(GLTFAccessor gLTFAccessor) {
        return new AccessorBuffer(gLTFAccessor, null, ByteBuffer.allocate(GLTFTypes.accessorSize(gLTFAccessor)).order(ByteOrder.LITTLE_ENDIAN));
    }
}
