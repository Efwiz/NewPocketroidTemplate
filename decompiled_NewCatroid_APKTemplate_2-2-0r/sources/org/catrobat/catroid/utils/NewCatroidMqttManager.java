package org.catrobat.catroid.utils;

import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenMqttMessageScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/NewCatroidMqttManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: NewCatroidMqttManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\t0\u000fJ\u000e\u0010\u0011\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u0006\u0010\u0012\u001a\u00020\tJ\u000e\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005J\u0018\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J2\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\t0\u000fJ&\u0010\u0019\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u0005J\u0010\u0010\u001b\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\u0005H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/utils/NewCatroidMqttManager;", "", "()V", "clients", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/hivemq/client/mqtt/mqtt3/Mqtt3AsyncClient;", "latestMessages", MqttConnectHandler.NAME, "", "id", "host", "port", "", "onComplete", "Lkotlin/Function1;", "", MqttDisconnectHandler.NAME, "disconnectAll", "getLatestMessage", "roomId", "getSaltedTopic", "salt", "joinRoom", "clientId", "publishMessage", CrashHianalyticsData.MESSAGE, "triggerMqttEvent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCatroidMqttManager {
    public static final NewCatroidMqttManager INSTANCE = new NewCatroidMqttManager();
    private static final ConcurrentHashMap<String, Mqtt3AsyncClient> clients = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> latestMessages = new ConcurrentHashMap<>();

    private NewCatroidMqttManager() {
    }

    public final void connect(final String id2, String host, int port, final Function1<? super Boolean, Unit> onComplete) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        Mqtt3AsyncClient mqtt3AsyncClient = clients.get(id2);
        if (mqtt3AsyncClient != null && mqtt3AsyncClient.getState().isConnected()) {
            onComplete.invoke(true);
            return;
        }
        try {
            Mqtt3ClientBuilder mqtt3ClientBuilderUseMqttVersion3 = MqttClient.builder().useMqttVersion3();
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            final Mqtt3AsyncClient mqtt3AsyncClientBuildAsync = mqtt3ClientBuilderUseMqttVersion3.identifier("newcatroid_" + StringsKt.take(string, 8)).mo2674serverHost(host).mo2676serverPort(port).buildAsync();
            Intrinsics.checkNotNullExpressionValue(mqtt3AsyncClientBuildAsync, "buildAsync(...)");
            CompletableFuture<Mqtt3ConnAck> completableFutureSend = mqtt3AsyncClientBuildAsync.connectWith().send();
            final Function2<Mqtt3ConnAck, Throwable, Unit> function2 = new Function2<Mqtt3ConnAck, Throwable, Unit>() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager.connect.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(Mqtt3ConnAck mqtt3ConnAck, Throwable th) {
                    invoke2(mqtt3ConnAck, th);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Mqtt3ConnAck mqtt3ConnAck, Throwable th) {
                    if (th == null) {
                        NewCatroidMqttManager.clients.put(id2, mqtt3AsyncClientBuildAsync);
                        onComplete.invoke(true);
                    } else {
                        th.printStackTrace();
                        onComplete.invoke(false);
                    }
                }
            };
            completableFutureSend.whenComplete(new BiConsumer() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    NewCatroidMqttManager.connect$lambda$0(function2, obj, obj2);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            onComplete.invoke(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void connect$lambda$0(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    public final void joinRoom(String clientId, final String roomId, String salt, final Function1<? super Boolean, Unit> onComplete) {
        Intrinsics.checkNotNullParameter(clientId, "clientId");
        Intrinsics.checkNotNullParameter(roomId, "roomId");
        Intrinsics.checkNotNullParameter(salt, "salt");
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        Mqtt3AsyncClient mqtt3AsyncClient = clients.get(clientId);
        if (mqtt3AsyncClient == null) {
            onComplete.invoke(false);
            return;
        }
        if (!mqtt3AsyncClient.getState().isConnected()) {
            onComplete.invoke(false);
            return;
        }
        CompletableFuture<Mqtt3SubAck> completableFutureSend = ((Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Start.Complete) mqtt3AsyncClient.subscribeWith().topicFilter(getSaltedTopic(roomId, salt))).callback(new Consumer() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NewCatroidMqttManager.joinRoom$lambda$1(roomId, (Mqtt3Publish) obj);
            }
        }).send();
        final Function2<Mqtt3SubAck, Throwable, Unit> function2 = new Function2<Mqtt3SubAck, Throwable, Unit>() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager.joinRoom.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Mqtt3SubAck mqtt3SubAck, Throwable th) {
                invoke2(mqtt3SubAck, th);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Mqtt3SubAck mqtt3SubAck, Throwable th) {
                if (th != null) {
                    th.printStackTrace();
                    onComplete.invoke(false);
                } else {
                    onComplete.invoke(true);
                }
            }
        };
        completableFutureSend.whenComplete(new BiConsumer() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                NewCatroidMqttManager.joinRoom$lambda$2(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void joinRoom$lambda$1(String roomId, Mqtt3Publish mqtt3Publish) {
        Intrinsics.checkNotNullParameter(roomId, "$roomId");
        byte[] payloadAsBytes = mqtt3Publish.getPayloadAsBytes();
        Intrinsics.checkNotNullExpressionValue(payloadAsBytes, "getPayloadAsBytes(...)");
        latestMessages.put(roomId, new String(payloadAsBytes, Charsets.UTF_8));
        INSTANCE.triggerMqttEvent(roomId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void joinRoom$lambda$2(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj, obj2);
    }

    public final void publishMessage(String clientId, String roomId, String salt, String message) {
        Intrinsics.checkNotNullParameter(clientId, "clientId");
        Intrinsics.checkNotNullParameter(roomId, "roomId");
        Intrinsics.checkNotNullParameter(salt, "salt");
        Intrinsics.checkNotNullParameter(message, "message");
        Mqtt3AsyncClient mqtt3AsyncClient = clients.get(clientId);
        if (mqtt3AsyncClient != null && mqtt3AsyncClient.getState().isConnected()) {
            Mqtt3PublishBuilder.Send.Complete complete = mqtt3AsyncClient.publishWith().topic(getSaltedTopic(roomId, salt));
            byte[] bytes = message.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            complete.payload(bytes).send();
        }
    }

    public final String getLatestMessage(String roomId) {
        Intrinsics.checkNotNullParameter(roomId, "roomId");
        String str = latestMessages.get(roomId);
        return str == null ? "" : str;
    }

    private final String getSaltedTopic(String roomId, String salt) {
        String strReplace$default = StringsKt.replace$default(StringsKt.trim((CharSequence) salt).toString(), " ", "_", false, 4, (Object) null);
        if (strReplace$default.length() == 0) {
            strReplace$default = "public";
        }
        return "newcatroid/" + strReplace$default + "/rooms/" + roomId;
    }

    private final void triggerMqttEvent(final String roomId) {
        final Project currentProject;
        StageActivity stageActivity;
        final StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (currentProject = ProjectManager.getInstance().getCurrentProject()) == null || (stageActivity = StageActivity.activeStageActivity.get()) == null) {
            return;
        }
        stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.utils.NewCatroidMqttManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NewCatroidMqttManager.triggerMqttEvent$lambda$4(activeStageListener, currentProject, roomId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerMqttEvent$lambda$4(StageListener stageListener, Project project, String roomId) {
        Formula formula;
        Intrinsics.checkNotNullParameter(stageListener, "$stageListener");
        Intrinsics.checkNotNullParameter(project, "$project");
        Intrinsics.checkNotNullParameter(roomId, "$roomId");
        try {
            for (Sprite sprite : stageListener.getSpritesFromStage()) {
                for (Script script : sprite.getScriptList()) {
                    if ((script instanceof WhenMqttMessageScript) && (formula = ((WhenMqttMessageScript) script).getFormulaMap().get(Brick.BrickField.VALUE_1)) != null) {
                        Intrinsics.checkNotNull(sprite);
                        if (Intrinsics.areEqual(formula.interpretString(new Scope(project, sprite, null)), roomId)) {
                            sprite.look.addAction(sprite.createSequenceAction(script));
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void disconnect(String id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        ConcurrentHashMap<String, Mqtt3AsyncClient> concurrentHashMap = clients;
        Mqtt3AsyncClient mqtt3AsyncClient = concurrentHashMap.get(id2);
        if (mqtt3AsyncClient != null && mqtt3AsyncClient.getState().isConnected()) {
            mqtt3AsyncClient.disconnect();
        }
        concurrentHashMap.remove(id2);
    }

    public final void disconnectAll() {
        Iterator<Map.Entry<String, Mqtt3AsyncClient>> it = clients.entrySet().iterator();
        while (it.hasNext()) {
            Mqtt3AsyncClient value = it.next().getValue();
            if (value.getState().isConnected()) {
                value.disconnect();
            }
        }
        clients.clear();
        latestMessages.clear();
    }
}
