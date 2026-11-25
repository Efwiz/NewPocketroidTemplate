package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: descriptorBasedTypeSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0011\u0010\u0005\u001a\r\u0012\t\u0012\u00070\u0004¢\u0006\u0002\b\u00070\u0006H&J\u0012\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000bH&J\u0017\u0010\r\u001a\u0004\u0018\u00018\u00002\u0006\u0010\n\u001a\u00020\u000bH&¢\u0006\u0002\u0010\u000eJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/TypeMappingConfiguration;", ExifInterface.GPS_DIRECTION_TRUE, "", "commonSupertype", "Lorg/jetbrains/kotlin/types/KotlinType;", SpecTypePair.NAME_types, "", "Lkotlin/jvm/JvmSuppressWildcards;", "getPredefinedFullInternalNameForClass", "", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getPredefinedInternalNameForClass", "getPredefinedTypeForClass", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/lang/Object;", "preprocessType", "kotlinType", "processErrorType", "", "descriptor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeMappingConfiguration<T> {
    KotlinType commonSupertype(Collection<KotlinType> types);

    default String getPredefinedFullInternalNameForClass(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        return null;
    }

    String getPredefinedInternalNameForClass(ClassDescriptor classDescriptor);

    T getPredefinedTypeForClass(ClassDescriptor classDescriptor);

    default KotlinType preprocessType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        return null;
    }

    void processErrorType(KotlinType kotlinType, ClassDescriptor descriptor);
}
