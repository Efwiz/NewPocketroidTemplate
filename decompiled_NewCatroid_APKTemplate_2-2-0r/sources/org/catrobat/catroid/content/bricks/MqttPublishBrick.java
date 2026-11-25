package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MqttPublishBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MqttPublishBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_mqtt_publish;
    }

    public MqttPublishBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_mqtt_publish_client_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_mqtt_publish_room_id);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_mqtt_publish_salt);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_mqtt_publish_message);
    }

    public MqttPublishBrick(String clientId, String roomId, String salt, String message) {
        this(new Formula(clientId), new Formula(roomId), new Formula(salt), new Formula(message));
    }

    public MqttPublishBrick(Formula clientId, Formula roomId, Formula salt, Formula message) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, clientId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, roomId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, salt);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, message);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMqttPublishAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}
