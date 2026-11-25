package org.jetbrains.kotlin.asJava;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;

/* JADX INFO: compiled from: KotlinAsJavaSupportBase.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0017\u0012\b\u0010\u0004\u001a\u0004\u0018\u00018\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0004\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/asJava/LightClassCachedValue;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "", "value", "tracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "(Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lcom/intellij/openapi/util/ModificationTracker;)V", "getTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", "getValue", "()Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightClassCachedValue<T extends KtLightClass> {
    private final ModificationTracker tracker;
    private final T value;

    public LightClassCachedValue(T t, ModificationTracker tracker) {
        Intrinsics.checkNotNullParameter(tracker, "tracker");
        this.value = t;
        this.tracker = tracker;
    }

    public final ModificationTracker getTracker() {
        return this.tracker;
    }

    public final T getValue() {
        return this.value;
    }
}
