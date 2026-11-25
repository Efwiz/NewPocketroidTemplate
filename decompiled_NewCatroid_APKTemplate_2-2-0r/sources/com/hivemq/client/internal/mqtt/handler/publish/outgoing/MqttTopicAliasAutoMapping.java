package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.util.collections.Index;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicAliasAutoMapping implements MqttTopicAliasMapping {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Index.Spec<Entry, String> INDEX_SPEC = new Index.Spec<>(new Function() { // from class: com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasAutoMapping$$ExternalSyntheticLambda0
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return ((MqttTopicAliasAutoMapping.Entry) obj).topic;
        }
    });
    private static final byte OVERSIZE = 4;
    private static final byte OVERWRITE_COST_INC = 2;
    private static final byte OVERWRITE_COST_MAX = 126;
    private static final byte OVERWRITE_COST_MIN = 2;
    private static final byte RETAIN = 8;
    private long accessCounter;
    private byte fullOverwriteTries;
    private Entry lowest;
    private byte overwriteTries;
    private final int topicAliasMaximum;
    private final Index<Entry, String> map = new Index<>(INDEX_SPEC);
    private byte overwriteCost = 2;
    private byte fullOverwriteCost = 2;

    public MqttTopicAliasAutoMapping(int i) {
        this.topicAliasMaximum = i;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasMapping
    public int getTopicAliasMaximum() {
        return this.topicAliasMaximum;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasMapping
    public int onPublish(MqttTopicImpl mqttTopicImpl) {
        long j = this.accessCounter + 1;
        this.accessCounter = j;
        String string = mqttTopicImpl.toString();
        Entry entry = this.map.get(string);
        if (entry != null) {
            entry.access(j);
            if (entry.topicAlias != 0) {
                byte b = this.overwriteCost;
                if (b > 2) {
                    this.overwriteCost = (byte) (b - 1);
                }
                byte b2 = this.fullOverwriteCost;
                if (b2 > 2) {
                    this.fullOverwriteCost = (byte) (b2 - 1);
                }
                if (entry.lower != null) {
                    if (entry.lower.topicAlias == 0) {
                        this.overwriteTries = (byte) 0;
                    }
                } else {
                    this.fullOverwriteTries = (byte) 0;
                }
            }
            swapNewer(entry, j);
            return entry.topicAlias;
        }
        Entry entry2 = new Entry(string, j);
        if (this.map.size() < this.topicAliasMaximum + 4) {
            if (this.map.size() < this.topicAliasMaximum) {
                entry2.setNewTopicAlias(this.map.size() + 1);
            }
            this.map.put(entry2);
            Entry entry3 = this.lowest;
            if (entry3 != null) {
                entry2.higher = entry3;
                this.lowest.lower = entry2;
            }
        } else {
            Entry entry4 = this.lowest;
            if (entry2.priority(j) <= entry4.priority(j)) {
                return 0;
            }
            byte b3 = (byte) (this.fullOverwriteTries + 1);
            this.fullOverwriteTries = b3;
            byte b4 = this.fullOverwriteCost;
            if (b3 < b4) {
                return 0;
            }
            this.fullOverwriteTries = (byte) 0;
            if (b4 < 126) {
                this.fullOverwriteCost = (byte) (b4 + ((byte) Math.min(2, 126 - b4)));
            }
            if (entry4.topicAlias != 0) {
                entry2.setNewTopicAlias(entry4.topicAlias);
            }
            this.map.remove(entry4.topic);
            this.map.put(entry2);
            Entry entry5 = entry4.higher;
            entry2.higher = entry5;
            if (entry5 != null) {
                entry5.lower = entry2;
            }
        }
        this.lowest = entry2;
        swapNewer(entry2, j);
        return entry2.topicAlias;
    }

    private void swapNewer(Entry entry, long j) {
        Entry entry2 = entry.higher;
        if (entry.higher == null) {
            return;
        }
        Entry entry3 = entry.lower;
        long jPriority = entry.priority(j);
        while (entry2.priority(j) < jPriority) {
            if (entry.topicAlias == 0 && entry2.topicAlias != 0) {
                byte b = (byte) (this.overwriteTries + 1);
                this.overwriteTries = b;
                byte b2 = this.overwriteCost;
                if (b < b2) {
                    return;
                }
                this.overwriteTries = (byte) 0;
                if (b2 < 126) {
                    this.overwriteCost = (byte) (b2 + ((byte) Math.min(2, 126 - b2)));
                }
                entry.setNewTopicAlias(entry2.topicAlias);
                entry2.topicAlias = 0;
            }
            Entry entry4 = entry2.higher;
            entry2.higher = entry;
            entry.lower = entry2;
            if (entry3 == null) {
                entry2.lower = null;
                this.lowest = entry2;
            } else {
                entry3.higher = entry2;
                entry2.lower = entry3;
            }
            if (entry4 == null) {
                entry.higher = null;
                return;
            }
            entry.higher = entry4;
            entry4.lower = entry;
            entry3 = entry2;
            entry2 = entry4;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry entry = this.lowest; entry != null; entry = entry.higher) {
            sb.append("\n  ").append(entry);
        }
        return sb.append("\n}").toString();
    }

    static class Entry {
        private long access;
        Entry higher;
        Entry lower;
        final String topic;
        int topicAlias = 0;
        private long used = 1;

        Entry(String str, long j) {
            this.topic = str;
            this.access = j;
        }

        void setNewTopicAlias(int i) {
            this.topicAlias = i | 65536;
        }

        void access(long j) {
            this.topicAlias &= 65535;
            this.used = priority(j) + 1;
            this.access = j;
        }

        long priority(long j) {
            return Math.max(this.used - Math.max((j - this.access) - 8, 0L), 0L);
        }

        public String toString() {
            return "{topic='" + this.topic + '\'' + (this.topicAlias == 0 ? "" : ", alias=" + (this.topicAlias & 65535)) + ((this.topicAlias & 65536) != 0 ? ", new " : "") + ", used = " + this.used + ", access = " + this.access + '}';
        }
    }
}
