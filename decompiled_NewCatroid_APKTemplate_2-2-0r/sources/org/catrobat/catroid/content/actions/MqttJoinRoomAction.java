package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.NewCatroidMqttManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MqttJoinRoomAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MqttJoinRoomAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\b\u0010\u001e\u001a\u00020\u001cH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/MqttJoinRoomAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "clientId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getClientId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setClientId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "roomId", "getRoomId", "setRoomId", "salt", "getSalt", "setSalt", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runAsyncJoin", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MqttJoinRoomAction extends Action {
    private Formula clientId;
    private volatile boolean finished;
    private Formula roomId;
    private Formula salt;
    private Scope scope;
    private boolean started;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getClientId() {
        return this.clientId;
    }

    public final void setClientId(Formula formula) {
        this.clientId = formula;
    }

    public final Formula getRoomId() {
        return this.roomId;
    }

    public final void setRoomId(Formula formula) {
        this.roomId = formula;
    }

    public final Formula getSalt() {
        return this.salt;
    }

    public final void setSalt(Formula formula) {
        this.salt = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncJoin();
        }
        return this.finished;
    }

    private final void runAsyncJoin() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.MqttJoinRoomAction.runAsyncJoin.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                try {
                    Formula clientId = MqttJoinRoomAction.this.getClientId();
                    String strInterpretString = clientId != null ? clientId.interpretString(MqttJoinRoomAction.this.getScope()) : null;
                    if (strInterpretString == null) {
                        strInterpretString = "lobby1";
                    }
                    Formula roomId = MqttJoinRoomAction.this.getRoomId();
                    String strInterpretString2 = roomId != null ? roomId.interpretString(MqttJoinRoomAction.this.getScope()) : null;
                    if (strInterpretString2 == null) {
                        strInterpretString2 = "room1";
                    }
                    Formula salt = MqttJoinRoomAction.this.getSalt();
                    String strInterpretString3 = salt != null ? salt.interpretString(MqttJoinRoomAction.this.getScope()) : null;
                    if (strInterpretString3 == null) {
                        strInterpretString3 = "my_project";
                    }
                    if (strInterpretString.length() <= 0 || strInterpretString2.length() <= 0) {
                        MqttJoinRoomAction.this.finished = true;
                        return;
                    }
                    NewCatroidMqttManager newCatroidMqttManager = NewCatroidMqttManager.INSTANCE;
                    final MqttJoinRoomAction mqttJoinRoomAction = MqttJoinRoomAction.this;
                    newCatroidMqttManager.joinRoom(strInterpretString, strInterpretString2, strInterpretString3, new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.content.actions.MqttJoinRoomAction.runAsyncJoin.1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean z) {
                            mqttJoinRoomAction.finished = true;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    MqttJoinRoomAction.this.finished = true;
                }
            }
        }, 30, null);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
        this.scope = null;
        this.clientId = null;
        this.roomId = null;
        this.salt = null;
    }
}
