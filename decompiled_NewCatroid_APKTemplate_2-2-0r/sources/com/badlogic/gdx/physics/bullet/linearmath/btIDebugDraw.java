package com.badlogic.gdx.physics.bullet.linearmath;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.BulletBase;

/* JADX INFO: loaded from: classes8.dex */
public class btIDebugDraw extends BulletBase {
    private long swigCPtr;

    public static final class DebugDrawModes {
        public static final int DBG_DisableBulletLCP = 512;
        public static final int DBG_DrawAabb = 2;
        public static final int DBG_DrawConstraintLimits = 4096;
        public static final int DBG_DrawConstraints = 2048;
        public static final int DBG_DrawContactPoints = 8;
        public static final int DBG_DrawFeaturesText = 4;
        public static final int DBG_DrawFrames = 32768;
        public static final int DBG_DrawNormals = 16384;
        public static final int DBG_DrawText = 64;
        public static final int DBG_DrawWireframe = 1;
        public static final int DBG_EnableCCD = 1024;
        public static final int DBG_EnableSatComparison = 256;
        public static final int DBG_FastWireframe = 8192;
        public static final int DBG_MAX_DEBUG_DRAW_MODE = 32769;
        public static final int DBG_NoDeactivation = 16;
        public static final int DBG_NoDebug = 0;
        public static final int DBG_NoHelpText = 32;
        public static final int DBG_ProfileTimings = 128;
    }

    protected btIDebugDraw(String str, long j, boolean z) {
        super(str, j, z);
        this.swigCPtr = j;
    }

    public btIDebugDraw(long j, boolean z) {
        this("btIDebugDraw", j, z);
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

    public static long getCPtr(btIDebugDraw btidebugdraw) {
        if (btidebugdraw == null) {
            return 0L;
        }
        return btidebugdraw.swigCPtr;
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
                LinearMathJNI.delete_btIDebugDraw(this.swigCPtr);
            }
            this.swigCPtr = 0L;
        }
        super.delete();
    }

    protected void swigDirectorDisconnect() {
        this.swigCMemOwn = false;
        delete();
    }

    public void swigReleaseOwnership() {
        this.swigCMemOwn = false;
        LinearMathJNI.btIDebugDraw_change_ownership(this, this.swigCPtr, false);
    }

    public void swigTakeOwnership() {
        this.swigCMemOwn = true;
        LinearMathJNI.btIDebugDraw_change_ownership(this, this.swigCPtr, true);
    }

    public static class DefaultColors extends BulletBase {
        private long swigCPtr;

        protected DefaultColors(String str, long j, boolean z) {
            super(str, j, z);
            this.swigCPtr = j;
        }

        public DefaultColors(long j, boolean z) {
            this("DefaultColors", j, z);
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

        public static long getCPtr(DefaultColors defaultColors) {
            if (defaultColors == null) {
                return 0L;
            }
            return defaultColors.swigCPtr;
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
                    LinearMathJNI.delete_btIDebugDraw_DefaultColors(this.swigCPtr);
                }
                this.swigCPtr = 0L;
            }
            super.delete();
        }

        public void setActiveObject(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_activeObject_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getActiveObject() {
            long jBtIDebugDraw_DefaultColors_activeObject_get = LinearMathJNI.btIDebugDraw_DefaultColors_activeObject_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_activeObject_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_activeObject_get, false);
        }

        public void setDeactivatedObject(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_deactivatedObject_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDeactivatedObject() {
            long jBtIDebugDraw_DefaultColors_deactivatedObject_get = LinearMathJNI.btIDebugDraw_DefaultColors_deactivatedObject_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_deactivatedObject_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_deactivatedObject_get, false);
        }

        public void setWantsDeactivationObject(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_wantsDeactivationObject_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getWantsDeactivationObject() {
            long jBtIDebugDraw_DefaultColors_wantsDeactivationObject_get = LinearMathJNI.btIDebugDraw_DefaultColors_wantsDeactivationObject_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_wantsDeactivationObject_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_wantsDeactivationObject_get, false);
        }

        public void setDisabledDeactivationObject(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_disabledDeactivationObject_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDisabledDeactivationObject() {
            long jBtIDebugDraw_DefaultColors_disabledDeactivationObject_get = LinearMathJNI.btIDebugDraw_DefaultColors_disabledDeactivationObject_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_disabledDeactivationObject_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_disabledDeactivationObject_get, false);
        }

        public void setDisabledSimulationObject(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_disabledSimulationObject_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getDisabledSimulationObject() {
            long jBtIDebugDraw_DefaultColors_disabledSimulationObject_get = LinearMathJNI.btIDebugDraw_DefaultColors_disabledSimulationObject_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_disabledSimulationObject_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_disabledSimulationObject_get, false);
        }

        public void setAabb(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_aabb_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getAabb() {
            long jBtIDebugDraw_DefaultColors_aabb_get = LinearMathJNI.btIDebugDraw_DefaultColors_aabb_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_aabb_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_aabb_get, false);
        }

        public void setContactPoint(btVector3 btvector3) {
            LinearMathJNI.btIDebugDraw_DefaultColors_contactPoint_set(this.swigCPtr, this, btVector3.getCPtr(btvector3), btvector3);
        }

        public btVector3 getContactPoint() {
            long jBtIDebugDraw_DefaultColors_contactPoint_get = LinearMathJNI.btIDebugDraw_DefaultColors_contactPoint_get(this.swigCPtr, this);
            if (jBtIDebugDraw_DefaultColors_contactPoint_get == 0) {
                return null;
            }
            return new btVector3(jBtIDebugDraw_DefaultColors_contactPoint_get, false);
        }

        public DefaultColors() {
            this(LinearMathJNI.new_btIDebugDraw_DefaultColors(), true);
        }
    }

    public DefaultColors getDefaultColors() {
        return new DefaultColors(getClass() == btIDebugDraw.class ? LinearMathJNI.btIDebugDraw_getDefaultColors(this.swigCPtr, this) : LinearMathJNI.btIDebugDraw_getDefaultColorsSwigExplicitbtIDebugDraw(this.swigCPtr, this), true);
    }

    public void setDefaultColors(DefaultColors defaultColors) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_setDefaultColors(this.swigCPtr, this, DefaultColors.getCPtr(defaultColors), defaultColors);
        } else {
            LinearMathJNI.btIDebugDraw_setDefaultColorsSwigExplicitbtIDebugDraw(this.swigCPtr, this, DefaultColors.getCPtr(defaultColors), defaultColors);
        }
    }

    public void drawLine(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        LinearMathJNI.btIDebugDraw_drawLine__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5);
    }

    public void drawLine(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawLine__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6);
        } else {
            LinearMathJNI.btIDebugDraw_drawLineSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6);
        }
    }

    public void drawSphere(float f, Matrix4 matrix4, Vector3 vector3) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawSphere__SWIG_0(this.swigCPtr, this, f, matrix4, vector3);
        } else {
            LinearMathJNI.btIDebugDraw_drawSphereSwigExplicitbtIDebugDraw__SWIG_0(this.swigCPtr, this, f, matrix4, vector3);
        }
    }

    public void drawSphere(Vector3 vector3, float f, Vector3 vector4) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawSphere__SWIG_1(this.swigCPtr, this, vector3, f, vector4);
        } else {
            LinearMathJNI.btIDebugDraw_drawSphereSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, f, vector4);
        }
    }

    public void drawTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, Vector3 vector7, Vector3 vector8, Vector3 vector9, float f) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawTriangle__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, vector6, vector7, vector8, vector9, f);
        } else {
            LinearMathJNI.btIDebugDraw_drawTriangleSwigExplicitbtIDebugDraw__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, vector6, vector7, vector8, vector9, f);
        }
    }

    public void drawTriangle(Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawTriangle__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6, f);
        } else {
            LinearMathJNI.btIDebugDraw_drawTriangleSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, vector6, f);
        }
    }

    public void drawContactPoint(Vector3 vector3, Vector3 vector4, float f, int i, Vector3 vector5) {
        LinearMathJNI.btIDebugDraw_drawContactPoint(this.swigCPtr, this, vector3, vector4, f, i, vector5);
    }

    public void reportErrorWarning(String str) {
        LinearMathJNI.btIDebugDraw_reportErrorWarning(this.swigCPtr, this, str);
    }

    public void draw3dText(Vector3 vector3, String str) {
        LinearMathJNI.btIDebugDraw_draw3dText(this.swigCPtr, this, vector3, str);
    }

    public void setDebugMode(int i) {
        LinearMathJNI.btIDebugDraw_setDebugMode(this.swigCPtr, this, i);
    }

    public int getDebugMode() {
        return LinearMathJNI.btIDebugDraw_getDebugMode(this.swigCPtr, this);
    }

    public void drawAabb(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawAabb(this.swigCPtr, this, vector3, vector4, vector5);
        } else {
            LinearMathJNI.btIDebugDraw_drawAabbSwigExplicitbtIDebugDraw(this.swigCPtr, this, vector3, vector4, vector5);
        }
    }

    public void drawTransform(Matrix4 matrix4, float f) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawTransform(this.swigCPtr, this, matrix4, f);
        } else {
            LinearMathJNI.btIDebugDraw_drawTransformSwigExplicitbtIDebugDraw(this.swigCPtr, this, matrix4, f);
        }
    }

    public void drawArc(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z, float f5) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawArc__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, vector6, z, f5);
        } else {
            LinearMathJNI.btIDebugDraw_drawArcSwigExplicitbtIDebugDraw__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, vector6, z, f5);
        }
    }

    public void drawArc(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, Vector3 vector6, boolean z) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawArc__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, vector6, z);
        } else {
            LinearMathJNI.btIDebugDraw_drawArcSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, vector6, z);
        }
    }

    public void drawSpherePatch(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6, boolean z) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawSpherePatch__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6, z);
        } else {
            LinearMathJNI.btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6, z);
        }
    }

    public void drawSpherePatch(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6, float f6) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawSpherePatch__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6);
        } else {
            LinearMathJNI.btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6, f6);
        }
    }

    public void drawSpherePatch(Vector3 vector3, Vector3 vector4, Vector3 vector5, float f, float f2, float f3, float f4, float f5, Vector3 vector6) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawSpherePatch__SWIG_2(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6);
        } else {
            LinearMathJNI.btIDebugDraw_drawSpherePatchSwigExplicitbtIDebugDraw__SWIG_2(this.swigCPtr, this, vector3, vector4, vector5, f, f2, f3, f4, f5, vector6);
        }
    }

    public void drawBox(Vector3 vector3, Vector3 vector4, Vector3 vector5) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawBox__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5);
        } else {
            LinearMathJNI.btIDebugDraw_drawBoxSwigExplicitbtIDebugDraw__SWIG_0(this.swigCPtr, this, vector3, vector4, vector5);
        }
    }

    public void drawBox(Vector3 vector3, Vector3 vector4, Matrix4 matrix4, Vector3 vector5) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawBox__SWIG_1(this.swigCPtr, this, vector3, vector4, matrix4, vector5);
        } else {
            LinearMathJNI.btIDebugDraw_drawBoxSwigExplicitbtIDebugDraw__SWIG_1(this.swigCPtr, this, vector3, vector4, matrix4, vector5);
        }
    }

    public void drawCapsule(float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawCapsule(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        } else {
            LinearMathJNI.btIDebugDraw_drawCapsuleSwigExplicitbtIDebugDraw(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        }
    }

    public void drawCylinder(float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawCylinder(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        } else {
            LinearMathJNI.btIDebugDraw_drawCylinderSwigExplicitbtIDebugDraw(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        }
    }

    public void drawCone(float f, float f2, int i, Matrix4 matrix4, Vector3 vector3) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawCone(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        } else {
            LinearMathJNI.btIDebugDraw_drawConeSwigExplicitbtIDebugDraw(this.swigCPtr, this, f, f2, i, matrix4, vector3);
        }
    }

    public void drawPlane(Vector3 vector3, float f, Matrix4 matrix4, Vector3 vector4) {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_drawPlane(this.swigCPtr, this, vector3, f, matrix4, vector4);
        } else {
            LinearMathJNI.btIDebugDraw_drawPlaneSwigExplicitbtIDebugDraw(this.swigCPtr, this, vector3, f, matrix4, vector4);
        }
    }

    public void clearLines() {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_clearLines(this.swigCPtr, this);
        } else {
            LinearMathJNI.btIDebugDraw_clearLinesSwigExplicitbtIDebugDraw(this.swigCPtr, this);
        }
    }

    public void flushLines() {
        if (getClass() == btIDebugDraw.class) {
            LinearMathJNI.btIDebugDraw_flushLines(this.swigCPtr, this);
        } else {
            LinearMathJNI.btIDebugDraw_flushLinesSwigExplicitbtIDebugDraw(this.swigCPtr, this);
        }
    }

    public btIDebugDraw() {
        this(LinearMathJNI.new_btIDebugDraw(), true);
        LinearMathJNI.btIDebugDraw_director_connect(this, this.swigCPtr, this.swigCMemOwn, true);
    }
}
