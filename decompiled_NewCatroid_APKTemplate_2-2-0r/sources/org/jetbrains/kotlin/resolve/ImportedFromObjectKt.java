package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;

/* JADX INFO: compiled from: importedFromObject.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0001\u001a\u0016\u0010\u0000\u001a\u00020\u0004*\u00020\u00052\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0006"}, d2 = {"asImportedFromObject", "Lorg/jetbrains/kotlin/resolve/FunctionImportedFromObject;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/resolve/PropertyImportedFromObject;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImportedFromObjectKt {
    public static /* synthetic */ FunctionImportedFromObject asImportedFromObject$default(FunctionDescriptor functionDescriptor, FunctionImportedFromObject functionImportedFromObject, int i, Object obj) {
        if ((i & 1) != 0) {
            functionImportedFromObject = null;
        }
        return asImportedFromObject(functionDescriptor, functionImportedFromObject);
    }

    public static final FunctionImportedFromObject asImportedFromObject(FunctionDescriptor functionDescriptor, FunctionImportedFromObject functionImportedFromObject) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        return new FunctionImportedFromObject(functionDescriptor, functionImportedFromObject != null ? functionImportedFromObject.getOriginal() : null);
    }

    public static /* synthetic */ PropertyImportedFromObject asImportedFromObject$default(PropertyDescriptor propertyDescriptor, PropertyImportedFromObject propertyImportedFromObject, int i, Object obj) {
        if ((i & 1) != 0) {
            propertyImportedFromObject = null;
        }
        return asImportedFromObject(propertyDescriptor, propertyImportedFromObject);
    }

    public static final PropertyImportedFromObject asImportedFromObject(PropertyDescriptor propertyDescriptor, PropertyImportedFromObject propertyImportedFromObject) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        return new PropertyImportedFromObject(propertyDescriptor, propertyImportedFromObject != null ? propertyImportedFromObject.getOriginal() : null);
    }
}
