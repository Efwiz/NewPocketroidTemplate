package org.catrobat.catroid.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import org.catrobat.catroid.raptor.ColliderShapeData;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.KeyframeData;
import org.catrobat.catroid.raptor.PhysicsComponent;
import org.catrobat.catroid.raptor.SceneManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Gizmo.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Gizmo {
    private final EditorActivity activity;
    private final Camera camera;
    private ModelInstance gizmoRotateX;
    private ModelInstance gizmoRotateY;
    private ModelInstance gizmoRotateZ;
    private ModelInstance gizmoScaleBoxX;
    private ModelInstance gizmoScaleBoxY;
    private ModelInstance gizmoScaleBoxZ;
    private ModelInstance gizmoScaleLineX;
    private ModelInstance gizmoScaleLineY;
    private ModelInstance gizmoScaleLineZ;
    private ModelInstance gizmoTranslateX;
    private ModelInstance gizmoTranslateY;
    private ModelInstance gizmoTranslateZ;
    private final SceneManager sceneManager;
    private final BoundingBox boxX = new BoundingBox();
    private final BoundingBox boxY = new BoundingBox();
    private final BoundingBox boxZ = new BoundingBox();
    private EditorTool currentTool = EditorTool.TRANSLATE;
    private Axis selectedAxis = Axis.NONE;
    private GameObject selectedObject = null;
    private ColliderShapeData selectedCollider = null;
    private KeyframeData selectedKeyframe = null;
    private final Vector3 intersectionPoint = new Vector3();
    private final Plane dragPlane = new Plane();
    private final Vector3 dragStartPoint = new Vector3();
    private final Vector3 dragCurrentPoint = new Vector3();
    private final Quaternion lastObjectRotation = new Quaternion();
    private final Vector3 dragStartPos = new Vector3();
    private final Quaternion dragStartRot = new Quaternion();
    private final Vector3 dragStartScale = new Vector3();
    private boolean isTransforming = false;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Gizmo$Axis.class
      classes14.dex
     */
    private enum Axis {
        NONE,
        X,
        Y,
        Z
    }

    public Gizmo(EditorActivity activity, SceneManager sceneManager, Camera camera) {
        this.activity = activity;
        this.sceneManager = sceneManager;
        this.camera = camera;
        createModels();
    }

    private void createModels() {
        ModelBuilder modelBuilder = new ModelBuilder();
        Model modelCreateArrow = modelBuilder.createArrow(0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.25f, 0.1f, 10, 4, new Material(ColorAttribute.createDiffuse(Color.RED)), 9L);
        Model modelCreateArrow2 = modelBuilder.createArrow(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.25f, 0.1f, 10, 4, new Material(ColorAttribute.createDiffuse(Color.GREEN)), 9L);
        Model modelCreateArrow3 = modelBuilder.createArrow(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 0.1f, 10, 4, new Material(ColorAttribute.createDiffuse(Color.BLUE)), 9L);
        this.gizmoTranslateX = new ModelInstance(modelCreateArrow);
        this.gizmoTranslateY = new ModelInstance(modelCreateArrow2);
        this.gizmoTranslateZ = new ModelInstance(modelCreateArrow3);
        this.gizmoRotateX = new ModelInstance(modelBuilder.createCylinder(2.0f, 0.04f, 2.0f, 32, new Material(ColorAttribute.createDiffuse(Color.RED)), 9L));
        this.gizmoRotateY = new ModelInstance(modelBuilder.createCylinder(2.0f, 0.04f, 2.0f, 32, new Material(ColorAttribute.createDiffuse(Color.GREEN)), 9L));
        this.gizmoRotateZ = new ModelInstance(modelBuilder.createCylinder(2.0f, 0.04f, 2.0f, 32, new Material(ColorAttribute.createDiffuse(Color.BLUE)), 9L));
        Model modelCreateBox = modelBuilder.createBox(0.2f, 0.2f, 0.2f, new Material(), 9L);
        ModelInstance modelInstance = new ModelInstance(modelCreateBox);
        this.gizmoScaleBoxX = modelInstance;
        modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));
        ModelInstance modelInstance2 = new ModelInstance(modelCreateBox);
        this.gizmoScaleBoxY = modelInstance2;
        modelInstance2.materials.get(0).set(ColorAttribute.createDiffuse(Color.GREEN));
        ModelInstance modelInstance3 = new ModelInstance(modelCreateBox);
        this.gizmoScaleBoxZ = modelInstance3;
        modelInstance3.materials.get(0).set(ColorAttribute.createDiffuse(Color.BLUE));
        Model modelCreateCylinder = modelBuilder.createCylinder(0.05f, 1.0f, 0.05f, 8, new Material(ColorAttribute.createDiffuse(Color.WHITE)), 9L);
        ModelInstance modelInstance4 = new ModelInstance(modelCreateCylinder);
        this.gizmoScaleLineX = modelInstance4;
        modelInstance4.materials.get(0).set(ColorAttribute.createDiffuse(Color.RED));
        ModelInstance modelInstance5 = new ModelInstance(modelCreateCylinder);
        this.gizmoScaleLineY = modelInstance5;
        modelInstance5.materials.get(0).set(ColorAttribute.createDiffuse(Color.GREEN));
        ModelInstance modelInstance6 = new ModelInstance(modelCreateCylinder);
        this.gizmoScaleLineZ = modelInstance6;
        modelInstance6.materials.get(0).set(ColorAttribute.createDiffuse(Color.BLUE));
    }

    public void setSelectedObject(GameObject go) {
        this.selectedObject = go;
        this.selectedCollider = null;
        this.selectedKeyframe = null;
    }

    public void setSelected(GameObject go, ColliderShapeData collider) {
        this.selectedObject = go;
        this.selectedCollider = collider;
        this.selectedKeyframe = null;
    }

    public void setSelectedKeyframe(GameObject owner, KeyframeData frame) {
        this.selectedObject = owner;
        this.selectedCollider = null;
        this.selectedKeyframe = frame;
    }

    public KeyframeData getSelectedKeyframe() {
        return this.selectedKeyframe;
    }

    public void setCurrentTool(EditorTool tool) {
        this.currentTool = tool;
    }

    public boolean isDragging() {
        return this.selectedAxis != Axis.NONE;
    }

    private Vector3 getGizmoPosition() {
        KeyframeData keyframeData = this.selectedKeyframe;
        if (keyframeData != null) {
            return keyframeData.position;
        }
        GameObject gameObject = this.selectedObject;
        if (gameObject == null) {
            return Vector3.Zero;
        }
        Vector3 translation = gameObject.transform.worldTransform.getTranslation(new Vector3());
        ColliderShapeData colliderShapeData = this.selectedCollider;
        if (colliderShapeData == null) {
            return translation;
        }
        return translation.add(this.selectedObject.transform.worldTransform.getRotation(new Quaternion()).transform(new Vector3(colliderShapeData.centerOffset)));
    }

    public void render(ModelBatch batch) {
        if (this.selectedObject == null || this.currentTool == EditorTool.HAND) {
            return;
        }
        Vector3 gizmoPosition = getGizmoPosition();
        Gdx.gl.glClear(256);
        batch.begin(this.camera);
        float fDst = this.camera.position.dst(gizmoPosition) * 0.15f;
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$editor$EditorTool[this.currentTool.ordinal()];
        if (i == 1) {
            renderAxis(batch, this.gizmoTranslateX, gizmoPosition, fDst);
            renderAxis(batch, this.gizmoTranslateY, gizmoPosition, fDst);
            renderAxis(batch, this.gizmoTranslateZ, gizmoPosition, fDst);
        } else if (i == 2) {
            float f = fDst * 0.6f;
            this.gizmoRotateX.transform.setToTranslation(gizmoPosition).scl(f).rotate(Vector3.Z, 90.0f);
            this.gizmoRotateY.transform.setToTranslation(gizmoPosition).scl(f);
            this.gizmoRotateZ.transform.setToTranslation(gizmoPosition).scl(f).rotate(Vector3.X, 90.0f);
            batch.render(this.gizmoRotateX);
            batch.render(this.gizmoRotateY);
            batch.render(this.gizmoRotateZ);
        } else if (i == 3) {
            this.gizmoScaleLineX.transform.setToTranslation(gizmoPosition).scl(fDst).rotate(Vector3.Z, -90.0f).translate(0.0f, 0.5f, 0.0f);
            this.gizmoScaleLineY.transform.setToTranslation(gizmoPosition).scl(fDst).translate(0.0f, 0.5f, 0.0f);
            this.gizmoScaleLineZ.transform.setToTranslation(gizmoPosition).scl(fDst).rotate(Vector3.X, 90.0f).translate(0.0f, 0.5f, 0.0f);
            batch.render(this.gizmoScaleLineX);
            batch.render(this.gizmoScaleLineY);
            batch.render(this.gizmoScaleLineZ);
            this.gizmoScaleBoxX.transform.setToTranslation(gizmoPosition).translate(fDst, 0.0f, 0.0f).scl(fDst);
            this.gizmoScaleBoxY.transform.setToTranslation(gizmoPosition).translate(0.0f, fDst, 0.0f).scl(fDst);
            this.gizmoScaleBoxZ.transform.setToTranslation(gizmoPosition).translate(0.0f, 0.0f, fDst).scl(fDst);
            batch.render(this.gizmoScaleBoxX);
            batch.render(this.gizmoScaleBoxY);
            batch.render(this.gizmoScaleBoxZ);
        }
        batch.end();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Gizmo$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.Gizmo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$editor$EditorTool;

        static {
            int[] iArr = new int[EditorTool.values().length];
            $SwitchMap$org$catrobat$catroid$editor$EditorTool = iArr;
            try {
                iArr[EditorTool.TRANSLATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$editor$EditorTool[EditorTool.ROTATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$editor$EditorTool[EditorTool.SCALE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void renderAxis(ModelBatch batch, ModelInstance instance, Vector3 position, float scale) {
        instance.transform.setToTranslation(position).scl(scale);
        batch.render(instance);
    }

    public void touchDragged(Ray pickRay) {
        GameObject gameObjectFindGameObject;
        if (this.selectedAxis == Axis.NONE || this.selectedObject == null || !Intersector.intersectRayPlane(pickRay, this.dragPlane, this.dragCurrentPoint)) {
            return;
        }
        Vector3 vector3Sub = this.dragCurrentPoint.cpy().sub(this.dragStartPoint);
        Vector3 vector3 = new Vector3();
        if (this.selectedAxis == Axis.X) {
            vector3.set(1.0f, 0.0f, 0.0f);
        }
        if (this.selectedAxis == Axis.Y) {
            vector3.set(0.0f, 1.0f, 0.0f);
        }
        if (this.selectedAxis == Axis.Z) {
            vector3.set(0.0f, 0.0f, 1.0f);
        }
        if (this.selectedCollider != null) {
            if (((PhysicsComponent) this.selectedObject.getComponent(PhysicsComponent.class)) == null) {
                return;
            }
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$editor$EditorTool[this.currentTool.ordinal()];
            if (i == 1) {
                Vector3 vector3Scl = vector3.cpy().scl(vector3Sub.dot(vector3));
                this.selectedObject.transform.worldTransform.getRotation(new Quaternion()).conjugate().transform(vector3Scl);
                this.selectedCollider.centerOffset.add(vector3Scl);
            } else if (i == 3) {
                Vector3 vector3Scl2 = vector3.cpy().scl(vector3Sub.dot(vector3) * 0.5f);
                if (this.selectedCollider.type == ColliderShapeData.ShapeType.SPHERE || this.selectedCollider.type == ColliderShapeData.ShapeType.CAPSULE) {
                    float fLen = vector3Scl2.len() * Math.signum(vector3Scl2.dot(vector3));
                    ColliderShapeData colliderShapeData = this.selectedCollider;
                    colliderShapeData.radius = Math.max(0.01f, colliderShapeData.radius + fLen);
                    if (this.selectedCollider.type == ColliderShapeData.ShapeType.CAPSULE) {
                        this.selectedCollider.size.y = Math.max(0.01f, this.selectedCollider.size.y + (fLen * 2.0f));
                    }
                } else {
                    this.selectedCollider.size.add(vector3Scl2);
                    this.selectedCollider.size.x = Math.max(0.01f, this.selectedCollider.size.x);
                    this.selectedCollider.size.y = Math.max(0.01f, this.selectedCollider.size.y);
                    this.selectedCollider.size.z = Math.max(0.01f, this.selectedCollider.size.z);
                }
            }
        } else if (this.selectedKeyframe != null) {
            int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$editor$EditorTool[this.currentTool.ordinal()];
            if (i2 == 1) {
                this.selectedKeyframe.position.add(vector3.cpy().scl(vector3Sub.dot(vector3)));
            } else if (i2 == 2) {
                Vector3 vector3Sub2 = this.dragCurrentPoint.cpy().sub(getGizmoPosition());
                Vector3 vector3Sub3 = this.dragStartPoint.cpy().sub(getGizmoPosition());
                Vector3 vector3Sub4 = vector3Sub3.cpy().sub(vector3.cpy().scl(vector3Sub3.dot(vector3)));
                Vector3 vector3Sub5 = vector3Sub2.cpy().sub(vector3.cpy().scl(vector3Sub2.dot(vector3)));
                vector3Sub4.nor();
                vector3Sub5.nor();
                float degrees = (float) Math.toDegrees(Math.acos(vector3Sub4.dot(vector3Sub5)));
                if (!Float.isNaN(degrees) && degrees >= 0.01f) {
                    this.selectedKeyframe.rotation.mulLeft(new Quaternion(vector3, degrees * Math.signum(vector3Sub4.crs(vector3Sub5).dot(vector3))));
                }
            } else if (i2 == 3) {
                this.selectedKeyframe.scale.add(vector3.cpy().scl(vector3Sub.dot(vector3) * 0.1f));
            }
        } else {
            int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$editor$EditorTool[this.currentTool.ordinal()];
            if (i3 == 1) {
                Vector3 vector3Scl3 = vector3.cpy().scl(vector3Sub.dot(vector3));
                if (this.selectedObject.parentId != null && (gameObjectFindGameObject = this.sceneManager.findGameObject(this.selectedObject.parentId)) != null) {
                    gameObjectFindGameObject.transform.worldTransform.getRotation(new Quaternion()).conjugate().transform(vector3Scl3);
                    Vector3 scale = gameObjectFindGameObject.transform.worldTransform.getScale(new Vector3());
                    if (scale.x != 0.0f) {
                        vector3Scl3.x /= scale.x;
                    }
                    if (scale.y != 0.0f) {
                        vector3Scl3.y /= scale.y;
                    }
                    if (scale.z != 0.0f) {
                        vector3Scl3.z /= scale.z;
                    }
                }
                this.selectedObject.transform.position.add(vector3Scl3);
            } else if (i3 == 2) {
                Vector3 vector3Sub6 = this.dragCurrentPoint.cpy().sub(getGizmoPosition());
                Vector3 vector3Sub7 = this.dragStartPoint.cpy().sub(getGizmoPosition());
                Vector3 vector3Sub8 = vector3Sub7.cpy().sub(vector3.cpy().scl(vector3Sub7.dot(vector3)));
                Vector3 vector3Sub9 = vector3Sub6.cpy().sub(vector3.cpy().scl(vector3Sub6.dot(vector3)));
                vector3Sub8.nor();
                vector3Sub9.nor();
                float degrees2 = (float) Math.toDegrees(Math.acos(vector3Sub8.dot(vector3Sub9)));
                if (!Float.isNaN(degrees2) && degrees2 >= 0.01f) {
                    this.sceneManager.rotate(this.selectedObject, new Quaternion(vector3, degrees2 * Math.signum(vector3Sub8.crs(vector3Sub9).dot(vector3))));
                }
            } else if (i3 == 3) {
                this.selectedObject.transform.scale.add(vector3.cpy().scl(vector3Sub.dot(vector3) * 0.1f));
            }
        }
        this.dragStartPoint.set(this.dragCurrentPoint);
    }

    public boolean touchDown(Ray pickRay) {
        ModelInstance modelInstance;
        ModelInstance modelInstance2;
        ModelInstance modelInstance3;
        if (this.selectedObject != null && this.currentTool != EditorTool.HAND) {
            this.selectedAxis = Axis.NONE;
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$editor$EditorTool[this.currentTool.ordinal()];
            if (i == 1) {
                modelInstance = this.gizmoTranslateX;
                modelInstance2 = this.gizmoTranslateY;
                modelInstance3 = this.gizmoTranslateZ;
            } else if (i == 2) {
                modelInstance = this.gizmoRotateX;
                modelInstance2 = this.gizmoRotateY;
                modelInstance3 = this.gizmoRotateZ;
            } else {
                if (i != 3) {
                    return false;
                }
                modelInstance = this.gizmoScaleBoxX;
                modelInstance2 = this.gizmoScaleBoxY;
                modelInstance3 = this.gizmoScaleBoxZ;
            }
            if (this.currentTool == EditorTool.ROTATE) {
                this.lastObjectRotation.set(this.selectedObject.transform.rotation);
            }
            this.dragStartPos.set(this.selectedObject.transform.position);
            this.dragStartRot.set(this.selectedObject.transform.rotation);
            this.dragStartScale.set(this.selectedObject.transform.scale);
            this.isTransforming = true;
            modelInstance.calculateBoundingBox(this.boxX).mul(modelInstance.transform);
            modelInstance2.calculateBoundingBox(this.boxY).mul(modelInstance2.transform);
            modelInstance3.calculateBoundingBox(this.boxZ).mul(modelInstance3.transform);
            float fIntersect = intersect(pickRay, this.boxX);
            if (fIntersect < Float.MAX_VALUE) {
                this.selectedAxis = Axis.X;
            } else {
                fIntersect = Float.MAX_VALUE;
            }
            float fIntersect2 = intersect(pickRay, this.boxY);
            if (fIntersect2 < fIntersect) {
                this.selectedAxis = Axis.Y;
                fIntersect = fIntersect2;
            }
            if (intersect(pickRay, this.boxZ) < fIntersect) {
                this.selectedAxis = Axis.Z;
            }
            if (this.selectedAxis != Axis.NONE) {
                setupDragPlane(getGizmoPosition());
                Intersector.intersectRayPlane(pickRay, this.dragPlane, this.dragStartPoint);
                if (this.currentTool == EditorTool.ROTATE) {
                    this.lastObjectRotation.set(this.selectedObject.transform.rotation);
                }
                return true;
            }
        }
        return false;
    }

    private float intersect(Ray ray, BoundingBox box) {
        if (Intersector.intersectRayBounds(ray, box, this.intersectionPoint)) {
            return ray.origin.dst2(this.intersectionPoint);
        }
        return Float.MAX_VALUE;
    }

    private void setupDragPlane(Vector3 origin) {
        this.dragPlane.set(origin, new Vector3(this.camera.direction).scl(-1.0f));
    }

    public void touchUp() {
        PhysicsComponent physicsComponent;
        GameObject gameObject;
        GameObject gameObject2;
        if (this.isTransforming && (gameObject2 = this.selectedObject) != null) {
            this.isTransforming = false;
            if (!this.dragStartPos.epsilonEquals(gameObject2.transform.position, 0.001f) || !this.dragStartRot.equals(this.selectedObject.transform.rotation) || !this.dragStartScale.epsilonEquals(this.selectedObject.transform.scale, 0.001f)) {
                this.activity.getUndoManager().pushCommand(new Commands.TransformCommand(this.sceneManager, this.selectedObject, this.dragStartPos, this.dragStartRot, this.dragStartScale));
            }
        }
        if (this.currentTool == EditorTool.ROTATE && (gameObject = this.selectedObject) != null) {
            this.lastObjectRotation.set(gameObject.transform.rotation);
        }
        if (this.selectedObject != null && this.selectedAxis != Axis.NONE) {
            if (this.selectedCollider != null && (physicsComponent = (PhysicsComponent) this.selectedObject.getComponent(PhysicsComponent.class)) != null) {
                this.sceneManager.setPhysicsComponent(this.selectedObject, physicsComponent);
            }
            this.activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.Gizmo$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5069lambda$touchUp$0$orgcatrobatcatroideditorGizmo();
                }
            });
        }
        this.selectedAxis = Axis.NONE;
    }

    /* JADX INFO: renamed from: lambda$touchUp$0$org-catrobat-catroid-editor-Gizmo, reason: not valid java name */
    /* synthetic */ void m5069lambda$touchUp$0$orgcatrobatcatroideditorGizmo() {
        if (this.activity.getInspectorManager() != null) {
            this.activity.getInspectorManager().m5171x5f6795b(this.selectedObject);
        }
    }

    public GameObject getSelectedObject() {
        return this.selectedObject;
    }
}
