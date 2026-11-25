package org.catrobat.catroid.physics;

import java.util.Iterator;
import java.util.LinkedList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsObjectStateHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhysicsObjectStateHandler {
    private PhysicsLook physicsLook;
    private PhysicsObject physicsObject;
    private PhysicsObjectStateCondition positionCondition;
    private PhysicsObjectStateCondition transparencyCondition;
    private PhysicsObjectStateCondition visibleCondition;
    private LinkedList<PhysicsObjectStateCondition> hangupConditions = new LinkedList<>();
    private LinkedList<PhysicsObjectStateCondition> nonCollidingConditions = new LinkedList<>();
    private LinkedList<PhysicsObjectStateCondition> fixConditions = new LinkedList<>();
    private boolean glideToIsActive = false;
    private boolean hangedUp = false;
    private boolean fixed = false;
    private boolean nonColliding = false;
    private PhysicsObjectStateCondition glideToCondition = new PhysicsObjectStateCondition() { // from class: org.catrobat.catroid.physics.PhysicsObjectStateHandler.4
        @Override // org.catrobat.catroid.physics.PhysicsObjectStateHandler.PhysicsObjectStateCondition
        public boolean isTrue() {
            return PhysicsObjectStateHandler.this.glideToIsActive;
        }
    };

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/PhysicsObjectStateHandler$PhysicsObjectStateCondition.class
      classes14.dex
     */
    private interface PhysicsObjectStateCondition {
        boolean isTrue();
    }

    PhysicsObjectStateHandler(final PhysicsLook physicsLook, final PhysicsObject physicsObject) {
        this.physicsLook = physicsLook;
        this.physicsObject = physicsObject;
        this.positionCondition = new PhysicsObjectStateCondition() { // from class: org.catrobat.catroid.physics.PhysicsObjectStateHandler.1
            @Override // org.catrobat.catroid.physics.PhysicsObjectStateHandler.PhysicsObjectStateCondition
            public boolean isTrue() {
                return isOutsideActiveArea();
            }

            private boolean isOutsideActiveArea() {
                return isXOutsideActiveArea() || isYOutsideActiveArea();
            }

            private boolean isXOutsideActiveArea() {
                return Math.abs(PhysicsWorldConverter.convertBox2dToNormalCoordinate(physicsObject.getMassCenter().x)) - physicsObject.getCircumference() > PhysicsWorld.activeArea.x / 2.0f;
            }

            private boolean isYOutsideActiveArea() {
                return Math.abs(PhysicsWorldConverter.convertBox2dToNormalCoordinate(physicsObject.getMassCenter().y)) - physicsObject.getCircumference() > PhysicsWorld.activeArea.y / 2.0f;
            }
        };
        this.visibleCondition = new PhysicsObjectStateCondition() { // from class: org.catrobat.catroid.physics.PhysicsObjectStateHandler.2
            @Override // org.catrobat.catroid.physics.PhysicsObjectStateHandler.PhysicsObjectStateCondition
            public boolean isTrue() {
                return !physicsLook.isLookVisible();
            }
        };
        this.transparencyCondition = new PhysicsObjectStateCondition() { // from class: org.catrobat.catroid.physics.PhysicsObjectStateHandler.3
            @Override // org.catrobat.catroid.physics.PhysicsObjectStateHandler.PhysicsObjectStateCondition
            public boolean isTrue() {
                return ((double) physicsLook.getAlpha()) == 0.0d;
            }
        };
        this.hangupConditions.add(this.transparencyCondition);
        this.hangupConditions.add(this.positionCondition);
        this.hangupConditions.add(this.visibleCondition);
        this.hangupConditions.add(this.glideToCondition);
        this.nonCollidingConditions.add(this.transparencyCondition);
        this.nonCollidingConditions.add(this.positionCondition);
        this.nonCollidingConditions.add(this.visibleCondition);
        this.fixConditions.add(this.glideToCondition);
    }

    boolean checkHangup(boolean record) {
        boolean z;
        Iterator<PhysicsObjectStateCondition> it = this.hangupConditions.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (it.next().isTrue()) {
                z = true;
                break;
            }
        }
        boolean z2 = this.hangedUp;
        boolean z3 = z2 && !z;
        boolean z4 = !z2 && z;
        if (z3) {
            this.physicsObject.deactivateHangup(record);
        } else if (z4) {
            this.physicsObject.activateHangup();
        }
        this.hangedUp = z;
        return z;
    }

    private boolean checkNonColliding(boolean record) {
        boolean z;
        Iterator<PhysicsObjectStateCondition> it = this.nonCollidingConditions.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (it.next().isTrue()) {
                z = true;
                break;
            }
        }
        boolean z2 = this.nonColliding;
        boolean z3 = z2 && !z;
        boolean z4 = !z2 && z;
        if (z3) {
            this.physicsObject.deactivateNonColliding(record, false);
        } else if (z4) {
            this.physicsObject.activateNonColliding(false);
        }
        this.nonColliding = z;
        return z;
    }

    private boolean checkFixed(boolean record) {
        boolean z;
        Iterator<PhysicsObjectStateCondition> it = this.fixConditions.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (it.next().isTrue()) {
                z = true;
                break;
            }
        }
        boolean z2 = this.fixed;
        boolean z3 = z2 && !z;
        boolean z4 = !z2 && z;
        if (z3) {
            this.physicsObject.deactivateFixed(record);
        } else if (z4) {
            this.physicsObject.activateFixed();
        }
        this.fixed = z;
        return z;
    }

    public void update(boolean record) {
        checkHangup(record);
        checkNonColliding(record);
        checkFixed(record);
        updateRotation();
    }

    public void updateRotation() {
        this.physicsObject.setFixedRotation(this.physicsLook.getRotationMode() != 1);
    }

    public void activateGlideTo() {
        if (this.glideToIsActive) {
            return;
        }
        this.glideToIsActive = true;
        this.physicsLook.updatePhysicsObjectState(true);
    }

    public void deactivateGlideTo() {
        this.glideToIsActive = false;
        this.physicsLook.updatePhysicsObjectState(true);
    }

    public boolean isHangedUp() {
        return this.hangedUp;
    }

    public void setNonColliding(boolean nonColliding) {
        if (this.nonColliding != nonColliding) {
            this.nonColliding = nonColliding;
            update(true);
        }
    }
}
