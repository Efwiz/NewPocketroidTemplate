package org.jetbrains.kotlin.resolve.calls.model;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: ArgumentMapping.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatch;", "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMapping;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", "getStatus", "()Lorg/jetbrains/kotlin/resolve/calls/model/ArgumentMatchStatus;", "valueParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getValueParameter", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "isError", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ArgumentMatch extends ArgumentMapping {
    ArgumentMatchStatus getStatus();

    ValueParameterDescriptor getValueParameter();

    @Override // org.jetbrains.kotlin.resolve.calls.model.ArgumentMapping
    default boolean isError() {
        return getStatus().getIsError();
    }
}
