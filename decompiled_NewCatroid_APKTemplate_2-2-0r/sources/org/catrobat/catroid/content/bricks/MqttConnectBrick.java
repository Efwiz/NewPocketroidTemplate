package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MqttConnectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MqttConnectBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_mqtt_connect;
    }

    public MqttConnectBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_mqtt_connect_id);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_mqtt_connect_host);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_mqtt_connect_port);
    }

    public MqttConnectBrick(String clientId, String host, int port) {
        this(new Formula(clientId), new Formula(host), new Formula(Integer.valueOf(port)));
    }

    public MqttConnectBrick(Formula clientId, Formula host, Formula port) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, clientId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, host);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, port);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMqttConnectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3)));
    }
}
