package org.catrobat.catroid.formulaeditor;

import com.google.firebase.database.core.ServerValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorCustomEvent.class
  classes14.dex
 */
/* JADX INFO: compiled from: SensorCustomEvent.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0001HÆ\u0003J\u001d\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0004\u001a\u00020\u0001X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/SensorCustomEvent;", "", "sensor", "Lorg/catrobat/catroid/formulaeditor/Sensors;", "value", "(Lorg/catrobat/catroid/formulaeditor/Sensors;Ljava/lang/Object;)V", "getSensor", "()Lorg/catrobat/catroid/formulaeditor/Sensors;", "setSensor", "(Lorg/catrobat/catroid/formulaeditor/Sensors;)V", ServerValues.NAME_OP_TIMESTAMP, "", "getTimestamp", "()J", "setTimestamp", "(J)V", "getValue", "()Ljava/lang/Object;", "setValue", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SensorCustomEvent {
    private Sensors sensor;
    private long timestamp;
    private Object value;

    public static /* synthetic */ SensorCustomEvent copy$default(SensorCustomEvent sensorCustomEvent, Sensors sensors, Object obj, int i, Object obj2) {
        if ((i & 1) != 0) {
            sensors = sensorCustomEvent.sensor;
        }
        if ((i & 2) != 0) {
            obj = sensorCustomEvent.value;
        }
        return sensorCustomEvent.copy(sensors, obj);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Sensors getSensor() {
        return this.sensor;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Object getValue() {
        return this.value;
    }

    public final SensorCustomEvent copy(Sensors sensor, Object value) {
        Intrinsics.checkNotNullParameter(sensor, "sensor");
        Intrinsics.checkNotNullParameter(value, "value");
        return new SensorCustomEvent(sensor, value);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SensorCustomEvent)) {
            return false;
        }
        SensorCustomEvent sensorCustomEvent = (SensorCustomEvent) other;
        return this.sensor == sensorCustomEvent.sensor && Intrinsics.areEqual(this.value, sensorCustomEvent.value);
    }

    public int hashCode() {
        return (this.sensor.hashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        return "SensorCustomEvent(sensor=" + this.sensor + ", value=" + this.value + Constant.AFTER_QUTO;
    }

    public SensorCustomEvent(Sensors sensor, Object value) {
        Intrinsics.checkNotNullParameter(sensor, "sensor");
        Intrinsics.checkNotNullParameter(value, "value");
        this.sensor = sensor;
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public final Sensors getSensor() {
        return this.sensor;
    }

    public final void setSensor(Sensors sensors) {
        Intrinsics.checkNotNullParameter(sensors, "<set-?>");
        this.sensor = sensors;
    }

    public final Object getValue() {
        return this.value;
    }

    public final void setValue(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<set-?>");
        this.value = obj;
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final void setTimestamp(long j) {
        this.timestamp = j;
    }
}
