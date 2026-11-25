package org.jetbrains.kotlin.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: ApproximatingStringTable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/serialization/ApproximatingStringTable;", "Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "()V", "isLocalClassIdReplacementKeptGeneric", "", "()Z", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ApproximatingStringTable extends StringTableImpl {
    @Override // org.jetbrains.kotlin.serialization.StringTableImpl, org.jetbrains.kotlin.serialization.DescriptorAwareStringTable
    public boolean isLocalClassIdReplacementKeptGeneric() {
        return false;
    }

    @Override // org.jetbrains.kotlin.serialization.DescriptorAwareStringTable
    public ClassId getLocalClassIdReplacement(ClassifierDescriptorWithTypeParameters descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (DescriptorUtils.isLocal(descriptor)) {
            return ClassId.topLevel(StandardNames.FqNames.any.toSafe());
        }
        return super.getLocalClassIdReplacement(descriptor);
    }
}
