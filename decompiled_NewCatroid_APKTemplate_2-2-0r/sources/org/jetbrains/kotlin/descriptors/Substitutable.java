package org.jetbrains.kotlin.descriptors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: Substitutable.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003J\u0015\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/Substitutable;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Lorg/jetbrains/kotlin/types/TypeSubstitutor;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Substitutable<T extends DeclarationDescriptorNonRoot> {
    T substitute(TypeSubstitutor substitutor);
}
