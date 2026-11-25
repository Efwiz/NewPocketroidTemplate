package com.badlogic.gdx.physics.bullet.softbody;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectFloatData;

/* JADX INFO: loaded from: classes8.dex */
public class btSoftBodyFloatData extends BulletBase {
    private long swigCPtr;

    protected btSoftBodyFloatData(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btSoftBodyFloatData(long j, boolean z) {
        this(SoftbodyConstants.btSoftBodyDataName, j, z);
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

    public static long getCPtr(btSoftBodyFloatData btsoftbodyfloatdata) {
        if (btsoftbodyfloatdata == null) {
            return 0L;
        }
        return btsoftbodyfloatdata.swigCPtr;
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
                SoftbodyJNI.delete_btSoftBodyFloatData(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    public void setCollisionObjectData(btCollisionObjectFloatData btcollisionobjectfloatdata) {
        SoftbodyJNI.btSoftBodyFloatData_collisionObjectData_set(this.swigCPtr, this, btCollisionObjectFloatData.getCPtr(btcollisionobjectfloatdata), btcollisionobjectfloatdata);
    }

    public btCollisionObjectFloatData getCollisionObjectData() {
        long jBtSoftBodyFloatData_collisionObjectData_get = SoftbodyJNI.btSoftBodyFloatData_collisionObjectData_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_collisionObjectData_get == 0) {
            return null;
        }
        return new btCollisionObjectFloatData(jBtSoftBodyFloatData_collisionObjectData_get, false);
    }

    public void setPose(SoftBodyPoseData softBodyPoseData) {
        SoftbodyJNI.btSoftBodyFloatData_pose_set(this.swigCPtr, this, SoftBodyPoseData.getCPtr(softBodyPoseData), softBodyPoseData);
    }

    public SoftBodyPoseData getPose() {
        long jBtSoftBodyFloatData_pose_get = SoftbodyJNI.btSoftBodyFloatData_pose_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_pose_get == 0) {
            return null;
        }
        return new SoftBodyPoseData(jBtSoftBodyFloatData_pose_get, false);
    }

    public void setMaterials(SWIGTYPE_p_p_SoftBodyMaterialData sWIGTYPE_p_p_SoftBodyMaterialData) {
        SoftbodyJNI.btSoftBodyFloatData_materials_set(this.swigCPtr, this, SWIGTYPE_p_p_SoftBodyMaterialData.getCPtr(sWIGTYPE_p_p_SoftBodyMaterialData));
    }

    public SWIGTYPE_p_p_SoftBodyMaterialData getMaterials() {
        long jBtSoftBodyFloatData_materials_get = SoftbodyJNI.btSoftBodyFloatData_materials_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_materials_get == 0) {
            return null;
        }
        return new SWIGTYPE_p_p_SoftBodyMaterialData(jBtSoftBodyFloatData_materials_get, false);
    }

    public void setNodes(SoftBodyNodeData softBodyNodeData) {
        SoftbodyJNI.btSoftBodyFloatData_nodes_set(this.swigCPtr, this, SoftBodyNodeData.getCPtr(softBodyNodeData), softBodyNodeData);
    }

    public SoftBodyNodeData getNodes() {
        long jBtSoftBodyFloatData_nodes_get = SoftbodyJNI.btSoftBodyFloatData_nodes_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_nodes_get == 0) {
            return null;
        }
        return new SoftBodyNodeData(jBtSoftBodyFloatData_nodes_get, false);
    }

    public void setLinks(SoftBodyLinkData softBodyLinkData) {
        SoftbodyJNI.btSoftBodyFloatData_links_set(this.swigCPtr, this, SoftBodyLinkData.getCPtr(softBodyLinkData), softBodyLinkData);
    }

    public SoftBodyLinkData getLinks() {
        long jBtSoftBodyFloatData_links_get = SoftbodyJNI.btSoftBodyFloatData_links_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_links_get == 0) {
            return null;
        }
        return new SoftBodyLinkData(jBtSoftBodyFloatData_links_get, false);
    }

    public void setFaces(SoftBodyFaceData softBodyFaceData) {
        SoftbodyJNI.btSoftBodyFloatData_faces_set(this.swigCPtr, this, SoftBodyFaceData.getCPtr(softBodyFaceData), softBodyFaceData);
    }

    public SoftBodyFaceData getFaces() {
        long jBtSoftBodyFloatData_faces_get = SoftbodyJNI.btSoftBodyFloatData_faces_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_faces_get == 0) {
            return null;
        }
        return new SoftBodyFaceData(jBtSoftBodyFloatData_faces_get, false);
    }

    public void setTetrahedra(SoftBodyTetraData softBodyTetraData) {
        SoftbodyJNI.btSoftBodyFloatData_tetrahedra_set(this.swigCPtr, this, SoftBodyTetraData.getCPtr(softBodyTetraData), softBodyTetraData);
    }

    public SoftBodyTetraData getTetrahedra() {
        long jBtSoftBodyFloatData_tetrahedra_get = SoftbodyJNI.btSoftBodyFloatData_tetrahedra_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_tetrahedra_get == 0) {
            return null;
        }
        return new SoftBodyTetraData(jBtSoftBodyFloatData_tetrahedra_get, false);
    }

    public void setAnchors(SoftRigidAnchorData softRigidAnchorData) {
        SoftbodyJNI.btSoftBodyFloatData_anchors_set(this.swigCPtr, this, SoftRigidAnchorData.getCPtr(softRigidAnchorData), softRigidAnchorData);
    }

    public SoftRigidAnchorData getAnchors() {
        long jBtSoftBodyFloatData_anchors_get = SoftbodyJNI.btSoftBodyFloatData_anchors_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_anchors_get == 0) {
            return null;
        }
        return new SoftRigidAnchorData(jBtSoftBodyFloatData_anchors_get, false);
    }

    public void setClusters(SoftBodyClusterData softBodyClusterData) {
        SoftbodyJNI.btSoftBodyFloatData_clusters_set(this.swigCPtr, this, SoftBodyClusterData.getCPtr(softBodyClusterData), softBodyClusterData);
    }

    public SoftBodyClusterData getClusters() {
        long jBtSoftBodyFloatData_clusters_get = SoftbodyJNI.btSoftBodyFloatData_clusters_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_clusters_get == 0) {
            return null;
        }
        return new SoftBodyClusterData(jBtSoftBodyFloatData_clusters_get, false);
    }

    public void setJoints(btSoftBodyJointData btsoftbodyjointdata) {
        SoftbodyJNI.btSoftBodyFloatData_joints_set(this.swigCPtr, this, btSoftBodyJointData.getCPtr(btsoftbodyjointdata), btsoftbodyjointdata);
    }

    public btSoftBodyJointData getJoints() {
        long jBtSoftBodyFloatData_joints_get = SoftbodyJNI.btSoftBodyFloatData_joints_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_joints_get == 0) {
            return null;
        }
        return new btSoftBodyJointData(jBtSoftBodyFloatData_joints_get, false);
    }

    public void setNumMaterials(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numMaterials_set(this.swigCPtr, this, i);
    }

    public int getNumMaterials() {
        return SoftbodyJNI.btSoftBodyFloatData_numMaterials_get(this.swigCPtr, this);
    }

    public void setNumNodes(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numNodes_set(this.swigCPtr, this, i);
    }

    public int getNumNodes() {
        return SoftbodyJNI.btSoftBodyFloatData_numNodes_get(this.swigCPtr, this);
    }

    public void setNumLinks(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numLinks_set(this.swigCPtr, this, i);
    }

    public int getNumLinks() {
        return SoftbodyJNI.btSoftBodyFloatData_numLinks_get(this.swigCPtr, this);
    }

    public void setNumFaces(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numFaces_set(this.swigCPtr, this, i);
    }

    public int getNumFaces() {
        return SoftbodyJNI.btSoftBodyFloatData_numFaces_get(this.swigCPtr, this);
    }

    public void setNumTetrahedra(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numTetrahedra_set(this.swigCPtr, this, i);
    }

    public int getNumTetrahedra() {
        return SoftbodyJNI.btSoftBodyFloatData_numTetrahedra_get(this.swigCPtr, this);
    }

    public void setNumAnchors(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numAnchors_set(this.swigCPtr, this, i);
    }

    public int getNumAnchors() {
        return SoftbodyJNI.btSoftBodyFloatData_numAnchors_get(this.swigCPtr, this);
    }

    public void setNumClusters(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numClusters_set(this.swigCPtr, this, i);
    }

    public int getNumClusters() {
        return SoftbodyJNI.btSoftBodyFloatData_numClusters_get(this.swigCPtr, this);
    }

    public void setNumJoints(int i) {
        SoftbodyJNI.btSoftBodyFloatData_numJoints_set(this.swigCPtr, this, i);
    }

    public int getNumJoints() {
        return SoftbodyJNI.btSoftBodyFloatData_numJoints_get(this.swigCPtr, this);
    }

    public void setConfig(SoftBodyConfigData softBodyConfigData) {
        SoftbodyJNI.btSoftBodyFloatData_config_set(this.swigCPtr, this, SoftBodyConfigData.getCPtr(softBodyConfigData), softBodyConfigData);
    }

    public SoftBodyConfigData getConfig() {
        long jBtSoftBodyFloatData_config_get = SoftbodyJNI.btSoftBodyFloatData_config_get(this.swigCPtr, this);
        if (jBtSoftBodyFloatData_config_get == 0) {
            return null;
        }
        return new SoftBodyConfigData(jBtSoftBodyFloatData_config_get, false);
    }

    public btSoftBodyFloatData() {
        this(SoftbodyJNI.new_btSoftBodyFloatData(), true);
    }
}
