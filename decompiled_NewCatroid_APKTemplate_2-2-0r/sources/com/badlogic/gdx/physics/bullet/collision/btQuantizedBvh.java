package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.btSerializer;
import java.nio.IntBuffer;

/* JADX INFO: loaded from: classes8.dex */
public class btQuantizedBvh extends BulletBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private long swigCPtr;

    public static final class btTraversalMode {
        public static final int TRAVERSAL_RECURSIVE = 2;
        public static final int TRAVERSAL_STACKLESS = 0;
        public static final int TRAVERSAL_STACKLESS_CACHE_FRIENDLY = 1;
    }

    protected btQuantizedBvh(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btQuantizedBvh(long j, boolean z) {
        this("btQuantizedBvh", j, z);
        construct();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void reset(long j, boolean z) {
        if (!this.destroyed) {
            destroy();
        }
        this.swigCPtr = j;
        super.reset(j, z);
    }

    public static long getCPtr(btQuantizedBvh btquantizedbvh) {
        if (btquantizedbvh == null) {
            return 0L;
        }
        return btquantizedbvh.swigCPtr;
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected void finalize() throws Throwable {
        if (!this.destroyed) {
            destroy();
        }
        super.finalize();
    }

    @Override // com.badlogic.gdx.physics.bullet.BulletBase
    protected synchronized void delete() {
        if (this.swigCPtr != 0) {
            if (this.swigCMemOwn) {
                this.swigCMemOwn = false;
                CollisionJNI.delete_btQuantizedBvh(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public long operatorNew(long j) {
        return CollisionJNI.btQuantizedBvh_operatorNew__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDelete(long j) {
        CollisionJNI.btQuantizedBvh_operatorDelete__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNew(long j, long j2) {
        return CollisionJNI.btQuantizedBvh_operatorNew__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDelete(long j, long j2) {
        CollisionJNI.btQuantizedBvh_operatorDelete__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public long operatorNewArray(long j) {
        return CollisionJNI.btQuantizedBvh_operatorNewArray__SWIG_0(this.swigCPtr, this, j);
    }

    public void operatorDeleteArray(long j) {
        CollisionJNI.btQuantizedBvh_operatorDeleteArray__SWIG_0(this.swigCPtr, this, j);
    }

    public long operatorNewArray(long j, long j2) {
        return CollisionJNI.btQuantizedBvh_operatorNewArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public void operatorDeleteArray(long j, long j2) {
        CollisionJNI.btQuantizedBvh_operatorDeleteArray__SWIG_1(this.swigCPtr, this, j, j2);
    }

    public btQuantizedBvh() {
        this(CollisionJNI.new_btQuantizedBvh(), true);
    }

    public void setQuantizationValues(Vector3 vector3, Vector3 vector4, float f) {
        CollisionJNI.btQuantizedBvh_setQuantizationValues__SWIG_0(this.swigCPtr, this, vector3, vector4, f);
    }

    public void setQuantizationValues(Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btQuantizedBvh_setQuantizationValues__SWIG_1(this.swigCPtr, this, vector3, vector4);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t getLeafNodeArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t(CollisionJNI.btQuantizedBvh_getLeafNodeArray(this.swigCPtr, this), false);
    }

    public void buildInternal() {
        CollisionJNI.btQuantizedBvh_buildInternal(this.swigCPtr, this);
    }

    public void reportAabbOverlappingNodex(btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btQuantizedBvh_reportAabbOverlappingNodex(this.swigCPtr, this, btNodeOverlapCallback.getCPtr(btnodeoverlapcallback), btnodeoverlapcallback, vector3, vector4);
    }

    public void reportRayOverlappingNodex(btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4) {
        CollisionJNI.btQuantizedBvh_reportRayOverlappingNodex(this.swigCPtr, this, btNodeOverlapCallback.getCPtr(btnodeoverlapcallback), btnodeoverlapcallback, vector3, vector4);
    }

    public void reportBoxCastOverlappingNodex(btNodeOverlapCallback btnodeoverlapcallback, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        CollisionJNI.btQuantizedBvh_reportBoxCastOverlappingNodex(this.swigCPtr, this, btNodeOverlapCallback.getCPtr(btnodeoverlapcallback), btnodeoverlapcallback, vector3, vector4, vector5, vector6);
    }

    public void quantize(IntBuffer intBuffer, Vector3 vector3, int i) {
        CollisionJNI.btQuantizedBvh_quantize(this.swigCPtr, this, intBuffer, vector3, i);
    }

    public void quantizeWithClamp(IntBuffer intBuffer, Vector3 vector3, int i) {
        CollisionJNI.btQuantizedBvh_quantizeWithClamp(this.swigCPtr, this, intBuffer, vector3, i);
    }

    public Vector3 unQuantize(IntBuffer intBuffer) {
        return CollisionJNI.btQuantizedBvh_unQuantize(this.swigCPtr, this, intBuffer);
    }

    public void setTraversalMode(int i) {
        CollisionJNI.btQuantizedBvh_setTraversalMode(this.swigCPtr, this, i);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t getQuantizedNodeArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btQuantizedBvhNode_t(CollisionJNI.btQuantizedBvh_getQuantizedNodeArray(this.swigCPtr, this), false);
    }

    public SWIGTYPE_p_btAlignedObjectArrayT_btBvhSubtreeInfo_t getSubtreeInfoArray() {
        return new SWIGTYPE_p_btAlignedObjectArrayT_btBvhSubtreeInfo_t(CollisionJNI.btQuantizedBvh_getSubtreeInfoArray(this.swigCPtr, this), false);
    }

    public long calculateSerializeBufferSize() {
        return CollisionJNI.btQuantizedBvh_calculateSerializeBufferSize(this.swigCPtr, this);
    }

    public boolean serialize(long j, long j2, boolean z) {
        return CollisionJNI.btQuantizedBvh_serialize__SWIG_0(this.swigCPtr, this, j, j2, z);
    }

    public static btQuantizedBvh deSerializeInPlace(long j, long j2, boolean z) {
        long jBtQuantizedBvh_deSerializeInPlace = CollisionJNI.btQuantizedBvh_deSerializeInPlace(j, j2, z);
        if (jBtQuantizedBvh_deSerializeInPlace == 0) {
            return null;
        }
        return new btQuantizedBvh(jBtQuantizedBvh_deSerializeInPlace, false);
    }

    public static long getAlignmentSerializationPadding() {
        return CollisionJNI.btQuantizedBvh_getAlignmentSerializationPadding();
    }

    public int calculateSerializeBufferSizeNew() {
        return CollisionJNI.btQuantizedBvh_calculateSerializeBufferSizeNew(this.swigCPtr, this);
    }

    public String serialize(long j, btSerializer btserializer) {
        return CollisionJNI.btQuantizedBvh_serialize__SWIG_1(this.swigCPtr, this, j, btSerializer.getCPtr(btserializer), btserializer);
    }

    public void deSerializeFloat(btQuantizedBvhFloatData btquantizedbvhfloatdata) {
        CollisionJNI.btQuantizedBvh_deSerializeFloat(this.swigCPtr, this, btQuantizedBvhFloatData.getCPtr(btquantizedbvhfloatdata), btquantizedbvhfloatdata);
    }

    public void deSerializeDouble(btQuantizedBvhDoubleData btquantizedbvhdoubledata) {
        CollisionJNI.btQuantizedBvh_deSerializeDouble(this.swigCPtr, this, btQuantizedBvhDoubleData.getCPtr(btquantizedbvhdoubledata), btquantizedbvhdoubledata);
    }

    public boolean isQuantized() {
        return CollisionJNI.btQuantizedBvh_isQuantized(this.swigCPtr, this);
    }
}
