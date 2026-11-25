package org.catrobat.catroid.editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorCameraController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EditorCameraController implements GestureDetector.GestureListener {
    public Camera camera;
    public boolean enabled = true;
    public boolean isPcMode = false;
    public float rotateSpeed = 0.2f;
    public float baseMoveSpeed = 5.0f;
    public float maxMoveSpeed = 100.0f;
    public float acceleration = 30.0f;
    private float currentMoveSpeed = 5.0f;
    public boolean isAccelerating = false;
    public final Vector3 velocity = new Vector3();
    private final Vector3 tmp = new Vector3();

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean fling(float vX, float vY, int b) {
        return false;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean pinch(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
        return false;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public void pinchStop() {
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    public EditorCameraController(Camera camera) {
        this.camera = camera;
    }

    public void update(float delta) {
        if (this.enabled) {
            if (this.isPcMode) {
                handlePcInputs();
            }
            if (this.isAccelerating) {
                this.currentMoveSpeed = Math.min(this.currentMoveSpeed + (this.acceleration * delta), this.maxMoveSpeed);
            } else {
                this.currentMoveSpeed = Math.max(this.currentMoveSpeed - ((this.acceleration * delta) * 2.0f), this.baseMoveSpeed);
            }
            if (this.velocity.isZero() && this.isPcMode) {
                this.camera.update();
                return;
            }
            float f = this.currentMoveSpeed * delta;
            if (this.velocity.z != 0.0f) {
                this.tmp.set(this.camera.direction).nor().scl(this.velocity.z * f);
                this.camera.position.add(this.tmp);
            }
            if (this.velocity.x != 0.0f) {
                this.tmp.set(this.camera.direction).crs(this.camera.up).nor().scl(this.velocity.x * f);
                this.camera.position.add(this.tmp);
            }
            if (this.velocity.y != 0.0f) {
                this.tmp.set(this.camera.up).nor().scl(f * this.velocity.y);
                this.camera.position.add(this.tmp);
            }
            this.camera.update();
        }
    }

    private void handlePcInputs() {
        this.velocity.setZero();
        this.isAccelerating = Gdx.input.isKeyPressed(59);
        if (Gdx.input.isKeyPressed(51)) {
            this.velocity.z = 1.0f;
        }
        if (Gdx.input.isKeyPressed(47)) {
            this.velocity.z = -1.0f;
        }
        if (Gdx.input.isKeyPressed(29)) {
            this.velocity.x = -1.0f;
        }
        if (Gdx.input.isKeyPressed(32)) {
            this.velocity.x = 1.0f;
        }
        if (Gdx.input.isKeyPressed(33)) {
            this.velocity.y = 1.0f;
        }
        if (Gdx.input.isKeyPressed(45)) {
            this.velocity.y = -1.0f;
        }
        if (Gdx.input.isButtonPressed(1) || Gdx.input.isButtonPressed(0)) {
            float f = (-Gdx.input.getDeltaX()) * this.rotateSpeed;
            float f2 = (-Gdx.input.getDeltaY()) * this.rotateSpeed;
            if (f != 0.0f) {
                this.camera.rotate(Vector3.Y, f);
            }
            if (f2 != 0.0f) {
                this.tmp.set(this.camera.direction).crs(this.camera.up).nor();
                this.camera.rotate(this.tmp, f2);
                return;
            }
            return;
        }
        Gdx.input.getDeltaX();
        Gdx.input.getDeltaY();
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!this.enabled || this.isPcMode) {
            return false;
        }
        this.camera.rotate(Vector3.Y, (-deltaX) * this.rotateSpeed);
        this.tmp.set(this.camera.direction).crs(this.camera.up).nor();
        this.camera.rotate(this.tmp, (-deltaY) * this.rotateSpeed);
        this.camera.update();
        return true;
    }

    @Override // com.badlogic.gdx.input.GestureDetector.GestureListener
    public boolean touchDown(float x, float y, int pointer, int button) {
        return this.enabled;
    }
}
