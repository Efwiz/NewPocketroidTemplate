package org.jetbrains.kotlin.resolve.calls.model;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u000f\u0010\u0004\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/TransformableToWarning;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "", "transformToWarning", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TransformableToWarning<T extends KotlinCallDiagnostic> {
    T transformToWarning();
}
