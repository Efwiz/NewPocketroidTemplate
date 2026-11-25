package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AdapterViewOnItemSelectedListenerImpl;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.physics.PhysicsObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetPhysicsObjectTypeBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPhysicsObjectTypeBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private PhysicsObject.Type type;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_physics_set_physics_object_type;
    }

    public SetPhysicsObjectTypeBrick() {
        this.type = PhysicsObject.Type.NONE;
    }

    public SetPhysicsObjectTypeBrick(PhysicsObject.Type type) {
        PhysicsObject.Type type2 = PhysicsObject.Type.NONE;
        this.type = type;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 3);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_set_physics_object_type_spinner);
        spinner.setAdapter((SpinnerAdapter) createAdapter(context));
        spinner.setSelection(this.type.ordinal());
        spinner.setOnItemSelectedListener(new AdapterViewOnItemSelectedListenerImpl(new Function1() { // from class: org.catrobat.catroid.content.bricks.SetPhysicsObjectTypeBrick$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m4963xe954e8a3((Integer) obj);
            }
        }));
        return this.view;
    }

    /* JADX INFO: renamed from: lambda$getView$0$org-catrobat-catroid-content-bricks-SetPhysicsObjectTypeBrick, reason: not valid java name */
    /* synthetic */ Unit m4963xe954e8a3(Integer num) {
        if (num.intValue() < PhysicsObject.Type.values().length) {
            this.type = PhysicsObject.Type.values()[num.intValue()];
        }
        return Unit.INSTANCE;
    }

    private ArrayAdapter<String> createAdapter(Context context) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (String str : context.getResources().getStringArray(R.array.physics_object_types)) {
            arrayAdapter.add(str);
        }
        return arrayAdapter;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPhysicsObjectTypeAction(sprite, this.type));
    }

    public PhysicsObject.Type getType() {
        return this.type;
    }
}
