package org.catrobat.catroid.physics.content;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.GlideToPhysicsAction;
import org.catrobat.catroid.content.actions.IfOnEdgeBouncePhysicsAction;
import org.catrobat.catroid.content.actions.SetBounceFactorAction;
import org.catrobat.catroid.content.actions.SetFrictionAction;
import org.catrobat.catroid.content.actions.SetGravityAction;
import org.catrobat.catroid.content.actions.SetMassAction;
import org.catrobat.catroid.content.actions.SetPhysicsObjectTypeAction;
import org.catrobat.catroid.content.actions.SetVelocityAction;
import org.catrobat.catroid.content.actions.TurnLeftSpeedAction;
import org.catrobat.catroid.content.actions.TurnRightSpeedAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.physics.PhysicsLook;
import org.catrobat.catroid.physics.PhysicsObject;
import org.catrobat.catroid.physics.PhysicsWorld;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/physics/content/ActionPhysicsFactory.class
  classes14.dex
 */
/* JADX INFO: compiled from: ActionPhysicsFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\"\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\u0016J\"\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0015\u001a\u0004\u0018\u00010\u000eH\u0016J,\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000eH\u0016J\"\u0010\u0019\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001a\u001a\u0004\u0018\u00010\u000eH\u0016J\u0018\u0010\u001b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J,\u0010\u001e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u000eH\u0016J\"\u0010!\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\"\u001a\u0004\u0018\u00010\u000eH\u0016J\"\u0010#\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\"\u001a\u0004\u0018\u00010\u000eH\u0016J\u0010\u0010$\u001a\u00020%2\u0006\u0010\t\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006&"}, d2 = {"Lorg/catrobat/catroid/physics/content/ActionPhysicsFactory;", "Lorg/catrobat/catroid/content/ActionFactory;", "()V", "physicsWorld", "Lorg/catrobat/catroid/physics/PhysicsWorld;", "getPhysicsWorld", "()Lorg/catrobat/catroid/physics/PhysicsWorld;", "createGlideToAction", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lcom/badlogic/gdx/scenes/scene2d/actions/SequenceAction;", "x", "Lorg/catrobat/catroid/formulaeditor/Formula;", "y", "duration", "createIfOnEdgeBounceAction", "createSetBounceFactorAction", "bounceFactor", "createSetFrictionAction", "friction", "createSetGravityAction", "gravityX", "gravityY", "createSetMassAction", "mass", "createSetPhysicsObjectTypeAction", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/physics/PhysicsObject$Type;", "createSetVelocityAction", "velocityX", "velocityY", "createTurnLeftSpeedAction", "speed", "createTurnRightSpeedAction", "getPhysicsObject", "Lorg/catrobat/catroid/physics/PhysicsObject;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ActionPhysicsFactory extends ActionFactory {
    private final PhysicsWorld getPhysicsWorld() {
        PhysicsWorld physicsWorld = ProjectManager.getInstance().getCurrentlyPlayingScene().getPhysicsWorld();
        Intrinsics.checkNotNullExpressionValue(physicsWorld, "getPhysicsWorld(...)");
        return physicsWorld;
    }

    private final PhysicsObject getPhysicsObject(Sprite sprite) {
        PhysicsObject physicsObject = getPhysicsWorld().getPhysicsObject(sprite);
        Intrinsics.checkNotNullExpressionValue(physicsObject, "getPhysicsObject(...)");
        return physicsObject;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createIfOnEdgeBounceAction(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        IfOnEdgeBouncePhysicsAction ifOnEdgeBouncePhysicsAction = (IfOnEdgeBouncePhysicsAction) Actions.action(IfOnEdgeBouncePhysicsAction.class);
        ifOnEdgeBouncePhysicsAction.setSprite(sprite);
        ifOnEdgeBouncePhysicsAction.setPhysicsWorld(getPhysicsWorld());
        Intrinsics.checkNotNull(ifOnEdgeBouncePhysicsAction);
        return ifOnEdgeBouncePhysicsAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createGlideToAction(Sprite sprite, SequenceAction sequence, Formula x, Formula y, Formula duration) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        GlideToPhysicsAction glideToPhysicsAction = (GlideToPhysicsAction) Actions.action(GlideToPhysicsAction.class);
        glideToPhysicsAction.setPosition(x, y);
        glideToPhysicsAction.setDuration(duration);
        glideToPhysicsAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        Look look = sprite.look;
        Intrinsics.checkNotNull(look, "null cannot be cast to non-null type org.catrobat.catroid.physics.PhysicsLook");
        glideToPhysicsAction.setPhysicsLook((PhysicsLook) look);
        Intrinsics.checkNotNull(glideToPhysicsAction);
        return glideToPhysicsAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetBounceFactorAction(Sprite sprite, SequenceAction sequence, Formula bounceFactor) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        SetBounceFactorAction setBounceFactorAction = (SetBounceFactorAction) Actions.action(SetBounceFactorAction.class);
        setBounceFactorAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setBounceFactorAction.setPhysicsObject(getPhysicsObject(sprite));
        setBounceFactorAction.setBounceFactor(bounceFactor);
        Intrinsics.checkNotNull(setBounceFactorAction);
        return setBounceFactorAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetFrictionAction(Sprite sprite, SequenceAction sequence, Formula friction) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        SetFrictionAction setFrictionAction = (SetFrictionAction) Actions.action(SetFrictionAction.class);
        setFrictionAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setFrictionAction.setPhysicsObject(getPhysicsObject(sprite));
        setFrictionAction.setFriction(friction);
        Intrinsics.checkNotNull(setFrictionAction);
        return setFrictionAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetGravityAction(Sprite sprite, SequenceAction sequence, Formula gravityX, Formula gravityY) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        SetGravityAction setGravityAction = (SetGravityAction) Actions.action(SetGravityAction.class);
        setGravityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setGravityAction.setPhysicsWorld(getPhysicsWorld());
        setGravityAction.setGravity(gravityX, gravityY);
        Intrinsics.checkNotNull(setGravityAction);
        return setGravityAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetMassAction(Sprite sprite, SequenceAction sequence, Formula mass) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        SetMassAction setMassAction = (SetMassAction) Actions.action(SetMassAction.class);
        setMassAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setMassAction.setPhysicsObject(getPhysicsObject(sprite));
        setMassAction.setMass(mass);
        Intrinsics.checkNotNull(setMassAction);
        return setMassAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetPhysicsObjectTypeAction(Sprite sprite, PhysicsObject.Type type) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(type, "type");
        SetPhysicsObjectTypeAction setPhysicsObjectTypeAction = (SetPhysicsObjectTypeAction) Actions.action(SetPhysicsObjectTypeAction.class);
        setPhysicsObjectTypeAction.setPhysicsObject(getPhysicsObject(sprite));
        setPhysicsObjectTypeAction.setType(type);
        Intrinsics.checkNotNull(setPhysicsObjectTypeAction);
        return setPhysicsObjectTypeAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createSetVelocityAction(Sprite sprite, SequenceAction sequence, Formula velocityX, Formula velocityY) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        SetVelocityAction setVelocityAction = (SetVelocityAction) Actions.action(SetVelocityAction.class);
        setVelocityAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        setVelocityAction.setPhysicsObject(getPhysicsObject(sprite));
        setVelocityAction.setVelocity(velocityX, velocityY);
        Intrinsics.checkNotNull(setVelocityAction);
        return setVelocityAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createTurnLeftSpeedAction(Sprite sprite, SequenceAction sequence, Formula speed) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        TurnLeftSpeedAction turnLeftSpeedAction = (TurnLeftSpeedAction) Actions.action(TurnLeftSpeedAction.class);
        turnLeftSpeedAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        turnLeftSpeedAction.setPhysicsObject(getPhysicsObject(sprite));
        turnLeftSpeedAction.setSpeed(speed);
        Intrinsics.checkNotNull(turnLeftSpeedAction);
        return turnLeftSpeedAction;
    }

    @Override // org.catrobat.catroid.content.ActionFactory
    public Action createTurnRightSpeedAction(Sprite sprite, SequenceAction sequence, Formula speed) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        TurnRightSpeedAction turnRightSpeedAction = (TurnRightSpeedAction) Actions.action(TurnRightSpeedAction.class);
        turnRightSpeedAction.setScope(new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, sequence));
        turnRightSpeedAction.setPhysicsObject(getPhysicsObject(sprite));
        turnRightSpeedAction.setSpeed(speed);
        Intrinsics.checkNotNull(turnRightSpeedAction);
        return turnRightSpeedAction;
    }
}
