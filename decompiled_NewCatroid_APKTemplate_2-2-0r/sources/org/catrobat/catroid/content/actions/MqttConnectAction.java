package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.hivemq.client.mqtt.MqttClient;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.NewCatroidMqttManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/MqttConnectAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: MqttConnectAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\b\u0010\u001e\u001a\u00020\u001cH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0006\"\u0004\b\r\u0010\bR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0006\"\u0004\b\u0010\u0010\bR\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/MqttConnectAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "clientId", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getClientId", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setClientId", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "finished", "", "host", "getHost", "setHost", "port", "getPort", "setPort", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "reset", "", "restart", "runAsyncConnect", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MqttConnectAction extends Action {
    private Formula clientId;
    private volatile boolean finished;
    private Formula host;
    private Formula port;
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

    public final Formula getHost() {
        return this.host;
    }

    public final void setHost(Formula formula) {
        this.host = formula;
    }

    public final Formula getPort() {
        return this.port;
    }

    public final void setPort(Formula formula) {
        this.port = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncConnect();
        }
        return this.finished;
    }

    private final void runAsyncConnect() {
        ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.content.actions.MqttConnectAction.runAsyncConnect.1
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
                    Formula clientId = MqttConnectAction.this.getClientId();
                    String strInterpretString = clientId != null ? clientId.interpretString(MqttConnectAction.this.getScope()) : null;
                    if (strInterpretString == null) {
                        strInterpretString = "lobby1";
                    }
                    Formula host = MqttConnectAction.this.getHost();
                    String strInterpretString2 = host != null ? host.interpretString(MqttConnectAction.this.getScope()) : null;
                    if (strInterpretString2 == null) {
                        strInterpretString2 = "broker.emqx.io";
                    }
                    Formula port = MqttConnectAction.this.getPort();
                    Integer numInterpretInteger = port != null ? port.interpretInteger(MqttConnectAction.this.getScope()) : null;
                    int iIntValue = numInterpretInteger == null ? MqttClient.DEFAULT_SERVER_PORT : numInterpretInteger.intValue();
                    if (strInterpretString.length() <= 0 || strInterpretString2.length() <= 0) {
                        MqttConnectAction.this.finished = true;
                        return;
                    }
                    NewCatroidMqttManager newCatroidMqttManager = NewCatroidMqttManager.INSTANCE;
                    final MqttConnectAction mqttConnectAction = MqttConnectAction.this;
                    newCatroidMqttManager.connect(strInterpretString, strInterpretString2, iIntValue, new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.content.actions.MqttConnectAction.runAsyncConnect.1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                            invoke(bool.booleanValue());
                            return Unit.INSTANCE;
                        }

                        public final void invoke(boolean z) {
                            mqttConnectAction.finished = true;
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    MqttConnectAction.this.finished = true;
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
        this.host = null;
        this.port = null;
    }
}
