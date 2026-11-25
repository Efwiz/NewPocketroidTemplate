package org.jetbrains.kotlin.serialization.deserialization.descriptors;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.serialization.deserialization.IncompatibleVersionErrorData;

/* JADX INFO: compiled from: DeserializedContainerSource.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0018\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0012\u0010\r\u001a\u00020\u000eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "abiStability", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "getAbiStability", "()Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerAbiStability;", "incompatibility", "Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "getIncompatibility", "()Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", "isPreReleaseInvisible", "", "()Z", "presentableString", "", "getPresentableString", "()Ljava/lang/String;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeserializedContainerSource extends SourceElement {
    DeserializedContainerAbiStability getAbiStability();

    /* JADX INFO: renamed from: getIncompatibility */
    IncompatibleVersionErrorData<?> mo6784getIncompatibility();

    String getPresentableString();

    boolean isPreReleaseInvisible();
}
