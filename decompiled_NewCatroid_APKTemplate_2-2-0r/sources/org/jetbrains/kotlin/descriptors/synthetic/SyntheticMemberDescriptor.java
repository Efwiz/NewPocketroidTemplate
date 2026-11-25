package org.jetbrains.kotlin.descriptors.synthetic;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: SyntheticMemberDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/synthetic/SyntheticMemberDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "baseDescriptorForSynthetic", "getBaseDescriptorForSynthetic", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SyntheticMemberDescriptor<T extends DeclarationDescriptor> {
    T getBaseDescriptorForSynthetic();
}
