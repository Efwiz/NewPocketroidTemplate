package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.physics.PhysicalCollision;
import org.catrobat.catroid.physics.PhysicsBoundaryBox;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: IfOnEdgeBouncePhysicsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u0000 A2\u00020\u0001:\u0002ABB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0004H\u0002J\b\u0010\u0018\u001a\u00020\u0004H\u0002J\b\u0010\u0019\u001a\u00020\u0004H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0004H\u0002J\b\u0010\u001f\u001a\u00020\u0004H\u0002J\b\u0010 \u001a\u00020\u0004H\u0002J\b\u0010!\u001a\u00020\u0004H\u0002J\b\u0010\"\u001a\u00020\u0004H\u0002J\b\u0010#\u001a\u00020\u0004H\u0002J\b\u0010$\u001a\u00020\u0004H\u0002J\b\u0010%\u001a\u00020\u0004H\u0002J\b\u0010&\u001a\u00020\u0004H\u0002J\u0010\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)H\u0002J*\u0010+\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010/\u001a\u000200H\u0002J\b\u00101\u001a\u00020-H\u0002J\u0010\u00102\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u00103\u001a\u00020-H\u0002J\b\u00104\u001a\u00020-H\u0002J\b\u00105\u001a\u00020-H\u0002J\b\u00106\u001a\u00020-H\u0002J\b\u00107\u001a\u00020-H\u0002J\u0010\u00108\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u00109\u001a\u00020-H\u0002J\u0010\u0010:\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010;\u001a\u00020-H\u0002J\u0010\u0010<\u001a\u00020-2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J(\u0010=\u001a\u00020\u001b2\u0006\u0010>\u001a\u00020\u00042\u0006\u0010.\u001a\u00020-2\u0006\u0010,\u001a\u00020-2\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010?\u001a\u00020\u001b2\u0006\u0010@\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "boundaryBoxCenterX", "", "boundaryBoxCenterY", "boundaryBoxHeight", "boundaryBoxWidth", "physicsWorld", "Lorg/catrobat/catroid/physics/PhysicsWorld;", "getPhysicsWorld", "()Lorg/catrobat/catroid/physics/PhysicsWorld;", "setPhysicsWorld", "(Lorg/catrobat/catroid/physics/PhysicsWorld;)V", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "virtualScreenWidth", "", "vsHeight", "calculateBottomCollisionAreaInnerBorder", "calculateBottomCollisionAreaOuterBorder", "calculateBottomCollisionOffset", "calculateBoundaryBoxDimensions", "", "physicsObject", "Lorg/catrobat/catroid/physics/PhysicsObject;", "calculateLeftCollisionAreaInnerBorder", "calculateLeftCollisionAreaOuterBorder", "calculateLeftCollisionOffset", "calculateRightCollisionAreaInnerBorder", "calculateRightCollisionAreaOuterBorder", "calculateRightCollisionOffset", "calculateTopCollisionAreaInnerBorder", "calculateTopCollisionAreaOuterBorder", "calculateTopCollisionOffset", "changeDirectionOnStepsTaken", "side", "Lorg/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction$Side;", "changeDirectionOnVelocityOrGravity", "checkBounceActivation", "correctGravityPresent", "", "isVelocityHighEnoughToCollideAfterRepositioning", "boundaryBoxIdentifier", "Lorg/catrobat/catroid/physics/PhysicsBoundaryBox$BoundaryBoxIdentifier;", "isBottomGravityPresent", "isBottomVelocityHighEnoughToCollideAfterRepositioning", "isBoundaryBoxCenterInBottomCollisionArea", "isBoundaryBoxCenterInLeftCollisionArea", "isBoundaryBoxCenterInRightCollisionArea", "isBoundaryBoxCenterInTopCollisionArea", "isLeftGravityPresent", "isLeftVelocityHighEnoughToCollideAfterRepositioning", "isRightGravityPresent", "isRightVelocityHighEnoughToCollideAfterRepositioning", "isTopGravityPresent", "isTopVelocityHighEnoughToCollideAfterRepositioning", "performRepositioning", "boundaryBoxLookOffset", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "Companion", "Side", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IfOnEdgeBouncePhysicsAction extends TemporalAction {
    private static final float COLLISION_OVERLAP_RANGE_FACTOR = 0.9f;
    private static final float OPPOSITE_DIRECTION = 180.0f;
    public static final float THRESHOLD_VELOCITY_TO_ACTIVATE_BOUNCE = 10.0f;
    private float boundaryBoxCenterX;
    private float boundaryBoxCenterY;
    private float boundaryBoxHeight;
    private float boundaryBoxWidth;
    public PhysicsWorld physicsWorld;
    public Sprite sprite;
    private final int virtualScreenWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth;
    private final int vsHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenHeight();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction$Side.class
      classes14.dex
     */
    /* JADX INFO: compiled from: IfOnEdgeBouncePhysicsAction.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction$Side;", "", "(Ljava/lang/String;I)V", "TOP", "BOTTOM", "LEFT", "RIGHT", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Side {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Side> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/IfOnEdgeBouncePhysicsAction$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: IfOnEdgeBouncePhysicsAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Side.values().length];
            try {
                iArr[Side.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Side.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Side.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Side.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final Sprite getSprite() {
        Sprite sprite = this.sprite;
        if (sprite != null) {
            return sprite;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sprite");
        return null;
    }

    public final void setSprite(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "<set-?>");
        this.sprite = sprite;
    }

    public final PhysicsWorld getPhysicsWorld() {
        PhysicsWorld physicsWorld = this.physicsWorld;
        if (physicsWorld != null) {
            return physicsWorld;
        }
        Intrinsics.throwUninitializedPropertyAccessException("physicsWorld");
        return null;
    }

    public final void setPhysicsWorld(PhysicsWorld physicsWorld) {
        Intrinsics.checkNotNullParameter(physicsWorld, "<set-?>");
        this.physicsWorld = physicsWorld;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        PhysicsObject physicsObject = getPhysicsWorld().getPhysicsObject(getSprite());
        Intrinsics.checkNotNull(physicsObject);
        calculateBoundaryBoxDimensions(physicsObject);
        if (isBoundaryBoxCenterInLeftCollisionArea()) {
            performRepositioning(calculateLeftCollisionOffset(), isLeftVelocityHighEnoughToCollideAfterRepositioning(physicsObject), isLeftGravityPresent(), Side.LEFT);
        } else if (isBoundaryBoxCenterInRightCollisionArea()) {
            performRepositioning(-calculateRightCollisionOffset(), isRightVelocityHighEnoughToCollideAfterRepositioning(physicsObject), isRightGravityPresent(), Side.RIGHT);
        }
        if (isBoundaryBoxCenterInBottomCollisionArea()) {
            performRepositioning(calculateBottomCollisionOffset(), isBottomVelocityHighEnoughToCollideAfterRepositioning(physicsObject), isBottomGravityPresent(), Side.BOTTOM);
        } else if (isBoundaryBoxCenterInTopCollisionArea()) {
            performRepositioning(-calculateTopCollisionOffset(), isTopVelocityHighEnoughToCollideAfterRepositioning(physicsObject), isTopGravityPresent(), Side.TOP);
        }
        getSprite().movedByStepsBrick = false;
    }

    private final void performRepositioning(float boundaryBoxLookOffset, boolean isVelocityHighEnoughToCollideAfterRepositioning, boolean correctGravityPresent, Side side) {
        if (side == Side.LEFT || side == Side.RIGHT) {
            getSprite().look.changeXInUserInterfaceDimensionUnit(boundaryBoxLookOffset);
            if (getSprite().movedByStepsBrick) {
                changeDirectionOnStepsTaken(side);
            } else {
                changeDirectionOnVelocityOrGravity(side);
            }
            checkBounceActivation(correctGravityPresent, isVelocityHighEnoughToCollideAfterRepositioning, getSprite(), PhysicsBoundaryBox.BoundaryBoxIdentifier.BBI_VERTICAL);
            return;
        }
        getSprite().look.changeYInUserInterfaceDimensionUnit(boundaryBoxLookOffset);
        if (getSprite().movedByStepsBrick) {
            changeDirectionOnStepsTaken(side);
        } else {
            changeDirectionOnVelocityOrGravity(side);
        }
        checkBounceActivation(correctGravityPresent, isVelocityHighEnoughToCollideAfterRepositioning, getSprite(), PhysicsBoundaryBox.BoundaryBoxIdentifier.BBI_HORIZONTAL);
    }

    private final void changeDirectionOnStepsTaken(Side side) {
        PhysicsObject physicsObject = getPhysicsWorld().getPhysicsObject(getSprite());
        float motionDirectionInUserInterfaceDimensionUnit = getSprite().look.getMotionDirectionInUserInterfaceDimensionUnit();
        if (side == Side.LEFT || side == Side.RIGHT) {
            getSprite().look.setMotionDirectionInUserInterfaceDimensionUnit(-motionDirectionInUserInterfaceDimensionUnit);
            Intrinsics.checkNotNull(physicsObject);
            calculateBoundaryBoxDimensions(physicsObject);
            Look look = getSprite().look;
            Intrinsics.checkNotNull(look, "null cannot be cast to non-null type org.catrobat.catroid.physics.PhysicsLook");
            ((PhysicsLook) look).updateFlippedByAction();
        } else if (side == Side.TOP || side == Side.BOTTOM) {
            getSprite().look.setMotionDirectionInUserInterfaceDimensionUnit(OPPOSITE_DIRECTION - motionDirectionInUserInterfaceDimensionUnit);
            Intrinsics.checkNotNull(physicsObject);
            calculateBoundaryBoxDimensions(physicsObject);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[side.ordinal()];
        if (i == 1) {
            getSprite().look.changeXInUserInterfaceDimensionUnit(calculateLeftCollisionOffset());
        } else if (i == 2) {
            getSprite().look.changeXInUserInterfaceDimensionUnit(-calculateRightCollisionOffset());
        } else if (i == 3) {
            getSprite().look.changeYInUserInterfaceDimensionUnit(-calculateTopCollisionOffset());
        } else if (i == 4) {
            getSprite().look.changeYInUserInterfaceDimensionUnit(calculateBottomCollisionOffset());
        } else {
            throw new IllegalArgumentException("invalid side");
        }
        PhysicalCollision.fireBounceOffEvent(getSprite(), null);
    }

    private final void changeDirectionOnVelocityOrGravity(Side side) {
        PhysicsObject physicsObject = getPhysicsWorld().getPhysicsObject(getSprite());
        float motionDirectionInUserInterfaceDimensionUnit = getSprite().look.getMotionDirectionInUserInterfaceDimensionUnit();
        if ((side == Side.LEFT || side == Side.RIGHT) && getSprite().look.getRotationMode() != 1) {
            getSprite().look.setMotionDirectionInUserInterfaceDimensionUnit(-motionDirectionInUserInterfaceDimensionUnit);
            Intrinsics.checkNotNull(physicsObject);
            calculateBoundaryBoxDimensions(physicsObject);
            Look look = getSprite().look;
            Intrinsics.checkNotNull(look, "null cannot be cast to non-null type org.catrobat.catroid.physics.PhysicsLook");
            ((PhysicsLook) look).updateFlippedByAction();
            return;
        }
        if ((side == Side.TOP || side == Side.BOTTOM) && getSprite().look.getRotationMode() != 1) {
            getSprite().look.setMotionDirectionInUserInterfaceDimensionUnit(OPPOSITE_DIRECTION - motionDirectionInUserInterfaceDimensionUnit);
            Intrinsics.checkNotNull(physicsObject);
            calculateBoundaryBoxDimensions(physicsObject);
        }
    }

    private final void checkBounceActivation(boolean correctGravityPresent, boolean isVelocityHighEnoughToCollideAfterRepositioning, Sprite sprite, PhysicsBoundaryBox.BoundaryBoxIdentifier boundaryBoxIdentifier) {
        if (isVelocityHighEnoughToCollideAfterRepositioning || correctGravityPresent) {
            getPhysicsWorld().setBounceOnce(sprite, boundaryBoxIdentifier);
        }
    }

    private final void calculateBoundaryBoxDimensions(PhysicsObject physicsObject) {
        Vector2 vector2 = new Vector2();
        Vector2 vector3 = new Vector2();
        physicsObject.getBoundaryBox(vector2, vector3);
        this.boundaryBoxWidth = vector3.x - vector2.x;
        this.boundaryBoxHeight = vector3.y - vector2.y;
        float f = 2;
        this.boundaryBoxCenterX = vector2.x + (this.boundaryBoxWidth / f);
        this.boundaryBoxCenterY = vector2.y + (this.boundaryBoxHeight / f);
    }

    private final boolean isBoundaryBoxCenterInLeftCollisionArea() {
        float fCalculateLeftCollisionAreaOuterBorder = calculateLeftCollisionAreaOuterBorder();
        float f = this.boundaryBoxCenterX;
        return fCalculateLeftCollisionAreaOuterBorder < f && f < calculateLeftCollisionAreaInnerBorder();
    }

    private final boolean isBoundaryBoxCenterInRightCollisionArea() {
        float fCalculateRightCollisionAreaOuterBorder = calculateRightCollisionAreaOuterBorder();
        float f = this.boundaryBoxCenterX;
        return fCalculateRightCollisionAreaOuterBorder > f && f > calculateRightCollisionAreaInnerBorder();
    }

    private final boolean isBoundaryBoxCenterInBottomCollisionArea() {
        float fCalculateBottomCollisionAreaOuterBorder = calculateBottomCollisionAreaOuterBorder();
        float f = this.boundaryBoxCenterY;
        return fCalculateBottomCollisionAreaOuterBorder < f && f < calculateBottomCollisionAreaInnerBorder();
    }

    private final boolean isBoundaryBoxCenterInTopCollisionArea() {
        float fCalculateTopCollisionAreaOuterBorder = calculateTopCollisionAreaOuterBorder();
        float f = this.boundaryBoxCenterY;
        return fCalculateTopCollisionAreaOuterBorder > f && f > calculateTopCollisionAreaInnerBorder();
    }

    private final float calculateLeftCollisionAreaInnerBorder() {
        return ((-this.virtualScreenWidth) / 2.0f) + (this.boundaryBoxWidth / 2.0f);
    }

    private final float calculateLeftCollisionAreaOuterBorder() {
        return calculateLeftCollisionAreaInnerBorder() + ((-this.boundaryBoxWidth) * COLLISION_OVERLAP_RANGE_FACTOR);
    }

    private final float calculateRightCollisionAreaInnerBorder() {
        return (this.virtualScreenWidth / 2.0f) - (this.boundaryBoxWidth / 2.0f);
    }

    private final float calculateRightCollisionAreaOuterBorder() {
        return calculateRightCollisionAreaInnerBorder() + (this.boundaryBoxWidth * COLLISION_OVERLAP_RANGE_FACTOR);
    }

    private final float calculateBottomCollisionAreaInnerBorder() {
        return ((-this.vsHeight) / 2.0f) + (this.boundaryBoxHeight / 2.0f);
    }

    private final float calculateBottomCollisionAreaOuterBorder() {
        return calculateBottomCollisionAreaInnerBorder() + ((-this.boundaryBoxHeight) * COLLISION_OVERLAP_RANGE_FACTOR);
    }

    private final float calculateTopCollisionAreaInnerBorder() {
        return (this.vsHeight / 2.0f) - (this.boundaryBoxHeight / 2.0f);
    }

    private final float calculateTopCollisionAreaOuterBorder() {
        return calculateTopCollisionAreaInnerBorder() + (this.boundaryBoxHeight * COLLISION_OVERLAP_RANGE_FACTOR);
    }

    private final boolean isLeftVelocityHighEnoughToCollideAfterRepositioning(PhysicsObject physicsObject) {
        return physicsObject.getVelocity().x <= -10.0f;
    }

    private final boolean isRightVelocityHighEnoughToCollideAfterRepositioning(PhysicsObject physicsObject) {
        return physicsObject.getVelocity().x >= 10.0f;
    }

    private final boolean isBottomVelocityHighEnoughToCollideAfterRepositioning(PhysicsObject physicsObject) {
        return physicsObject.getVelocity().y <= -10.0f;
    }

    private final boolean isTopVelocityHighEnoughToCollideAfterRepositioning(PhysicsObject physicsObject) {
        return physicsObject.getVelocity().y >= 10.0f;
    }

    private final boolean isLeftGravityPresent() {
        return getPhysicsWorld().getGravity().x < 0.0f;
    }

    private final boolean isRightGravityPresent() {
        return getPhysicsWorld().getGravity().x > 0.0f;
    }

    private final boolean isBottomGravityPresent() {
        return getPhysicsWorld().getGravity().y < 0.0f;
    }

    private final boolean isTopGravityPresent() {
        return getPhysicsWorld().getGravity().y > 0.0f;
    }

    private final float calculateLeftCollisionOffset() {
        return Math.abs((this.boundaryBoxCenterX - (this.boundaryBoxWidth / 2.0f)) + (this.virtualScreenWidth / 2.0f));
    }

    private final float calculateRightCollisionOffset() {
        return Math.abs((this.boundaryBoxCenterX + (this.boundaryBoxWidth / 2.0f)) - (this.virtualScreenWidth / 2.0f));
    }

    private final float calculateBottomCollisionOffset() {
        return Math.abs((this.boundaryBoxCenterY - (this.boundaryBoxHeight / 2.0f)) + (this.vsHeight / 2.0f));
    }

    private final float calculateTopCollisionOffset() {
        return Math.abs((this.boundaryBoxCenterY + (this.boundaryBoxHeight / 2.0f)) - (this.vsHeight / 2.0f));
    }
}
