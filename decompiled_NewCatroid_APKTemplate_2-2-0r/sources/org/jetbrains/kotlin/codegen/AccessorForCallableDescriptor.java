package org.jetbrains.kotlin.codegen;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;

/* JADX INFO: compiled from: AccessorForCallableDescriptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/AccessorForCallableDescriptor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "", "accessorKind", "Lorg/jetbrains/kotlin/codegen/AccessorKind;", "getAccessorKind", "()Lorg/jetbrains/kotlin/codegen/AccessorKind;", "calleeDescriptor", "getCalleeDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "superCallTarget", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getSuperCallTarget", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AccessorForCallableDescriptor<T extends CallableMemberDescriptor> {
    AccessorKind getAccessorKind();

    T getCalleeDescriptor();

    ClassDescriptor getSuperCallTarget();
}
