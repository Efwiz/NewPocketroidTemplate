package com.badlogic.gdx.physics.bullet.collision;

import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btDbvtNode extends BulletBase {
    private long swigCPtr;
    private static final btDbvtNode temp = new btDbvtNode(0, false);
    private static btDbvtNode[] argumentInstances = {new btDbvtNode(0, false), new btDbvtNode(0, false), new btDbvtNode(0, false), new btDbvtNode(0, false)};
    private static int argumentIndex = -1;

    protected btDbvtNode(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btDbvtNode(long j, boolean z) {
        this("btDbvtNode", j, z);
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

    public static long getCPtr(btDbvtNode btdbvtnode) {
        if (btdbvtnode == null) {
            return 0L;
        }
        return btdbvtnode.swigCPtr;
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
                CollisionJNI.delete_btDbvtNode(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public static btDbvtNode internalTemp(long j, boolean z) {
        btDbvtNode btdbvtnode = temp;
        btdbvtnode.reset(j, z);
        return btdbvtnode;
    }

    protected static btDbvtNode obtainForArgument(long j, boolean z) {
        btDbvtNode[] btdbvtnodeArr = argumentInstances;
        int i = (argumentIndex + 1) & 3;
        argumentIndex = i;
        btDbvtNode btdbvtnode = btdbvtnodeArr[i];
        btdbvtnode.reset(j, z);
        return btdbvtnode;
    }

    public void setVolume(btDbvtAabbMm btdbvtaabbmm) {
        CollisionJNI.btDbvtNode_volume_set(this.swigCPtr, this, btDbvtAabbMm.getCPtr(btdbvtaabbmm), btdbvtaabbmm);
    }

    public btDbvtAabbMm getVolume() {
        return btDbvtAabbMm.internalTemp(CollisionJNI.btDbvtNode_volume_get(this.swigCPtr, this), false);
    }

    public void setParent(btDbvtNode btdbvtnode) {
        CollisionJNI.btDbvtNode_parent_set(this.swigCPtr, this, getCPtr(btdbvtnode), btdbvtnode);
    }

    public btDbvtNode getParent() {
        return internalTemp(CollisionJNI.btDbvtNode_parent_get(this.swigCPtr, this), false);
    }

    public boolean isleaf() {
        return CollisionJNI.btDbvtNode_isleaf(this.swigCPtr, this);
    }

    public boolean isinternal() {
        return CollisionJNI.btDbvtNode_isinternal(this.swigCPtr, this);
    }

    public void setChilds(SWIGTYPE_p_p_btDbvtNode sWIGTYPE_p_p_btDbvtNode) {
        CollisionJNI.btDbvtNode_childs_set(this.swigCPtr, this, SWIGTYPE_p_p_btDbvtNode.getCPtr(sWIGTYPE_p_p_btDbvtNode));
    }

    public SWIGTYPE_p_p_btDbvtNode getChilds() {
        long jBtDbvtNode_childs_get = CollisionJNI.btDbvtNode_childs_get(this.swigCPtr, this);
        if (jBtDbvtNode_childs_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_btDbvtNode(jBtDbvtNode_childs_get, false);
    }

    public void setData(long j) {
        CollisionJNI.btDbvtNode_data_set(this.swigCPtr, this, j);
    }

    public long getData() {
        return CollisionJNI.btDbvtNode_data_get(this.swigCPtr, this);
    }

    public void setDataAsInt(int i) {
        CollisionJNI.btDbvtNode_dataAsInt_set(this.swigCPtr, this, i);
    }

    public int getDataAsInt() {
        return CollisionJNI.btDbvtNode_dataAsInt_get(this.swigCPtr, this);
    }

    public btDbvtNode getChild(int i) {
        return internalTemp(CollisionJNI.btDbvtNode_getChild(this.swigCPtr, this, i), false);
    }

    public btDbvtNode getChild0() {
        return internalTemp(CollisionJNI.btDbvtNode_getChild0(this.swigCPtr, this), false);
    }

    public btDbvtNode getChild1() {
        return internalTemp(CollisionJNI.btDbvtNode_getChild1(this.swigCPtr, this), false);
    }

    public btBroadphaseProxy getDataAsProxy() {
        return btBroadphaseProxy.internalTemp(CollisionJNI.btDbvtNode_getDataAsProxy(this.swigCPtr, this), false);
    }

    public btCollisionObject getDataAsProxyClientObject() {
        return btCollisionObject.getInstance(CollisionJNI.btDbvtNode_getDataAsProxyClientObject(this.swigCPtr, this), false);
    }

    public btDbvtNode() {
        this(CollisionJNI.new_btDbvtNode(), true);
    }
}
