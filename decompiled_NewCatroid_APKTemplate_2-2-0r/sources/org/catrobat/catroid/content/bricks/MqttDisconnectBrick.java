package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MqttDisconnectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MqttDisconnectBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_mqtt_disconnect;
    }

    public MqttDisconnectBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_mqtt_disconnect_client_id);
    }

    public MqttDisconnectBrick(String clientId) {
        this(new Formula(clientId));
    }

    public MqttDisconnectBrick(Formula clientId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, clientId);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMqttDisconnectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
