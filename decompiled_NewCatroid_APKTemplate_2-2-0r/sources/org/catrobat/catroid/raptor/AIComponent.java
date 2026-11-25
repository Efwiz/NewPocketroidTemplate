package org.catrobat.catroid.raptor;

import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/AIComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AIComponent implements Component {
    public Mode mode = Mode.OFF;
    public String targetId = "";
    public Vector3 targetPos = new Vector3();
    public float speed = 5.0f;
    public float turnSpeed = 5.0f;
    public float stopDistance = 1.5f;
    public boolean avoidObstacles = true;
    public float avoidanceRange = 3.0f;
    public boolean groundOnly = true;
    public float detectionRange = 4.0f;
    public float stepHeight = 0.7f;
    public float climbSpeed = 4.0f;
    public final Vector3 currentVelocity = new Vector3();
    public final Vector3 lastPosition = new Vector3();
    public float stuckTimer = 0.0f;
    public float randomWanderTimer = 0.0f;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/AIComponent$Mode.class
      classes14.dex
     */
    public enum Mode {
        OFF,
        FOLLOW,
        MOVE_TO,
        FLEE
    }
}
