package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.End;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilderBase.SharedBase;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopicFilterBuilderBase<C extends MqttTopicFilterBuilderBase<C, E, S, SC, SE>, E extends End, S extends SharedBase<S, SC, SE>, SC extends S, SE extends End> {

    public interface Complete<C extends MqttTopicFilterBuilderBase<C, E, S, SC, SE>, E extends End, S extends SharedBase<S, SC, SE>, SC extends S, SE extends End> extends MqttTopicFilterBuilderBase<C, E, S, SC, SE> {
        /* JADX WARN: Incorrect return type in method signature: (Ljava/lang/String;)TSC; */
        @Override // 
        SharedBase share(String str);
    }

    public interface End {
    }

    public interface SharedBase<S extends SharedBase<S, SC, SE>, SC extends S, SE extends End> extends MqttTopicFilterBuilderBase<SC, SE, S, SC, SE> {

        public interface Complete<S extends SharedBase<S, SC, SE>, SC extends S, SE extends End> extends Complete<SC, SE, S, SC, SE> {
        }
    }

    C addLevel(String str);

    E multiLevelWildcard();

    S share(String str);

    C singleLevelWildcard();
}
