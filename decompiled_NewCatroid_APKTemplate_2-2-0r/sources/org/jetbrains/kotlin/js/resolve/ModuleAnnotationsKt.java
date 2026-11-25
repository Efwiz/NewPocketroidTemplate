package org.jetbrains.kotlin.js.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.kotlin.serialization.js.KotlinJavascriptPackageFragment;

/* JADX INFO: compiled from: moduleAnnotations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"getAnnotationsOnContainingJsModule", "", "Lorg/jetbrains/kotlin/name/ClassId;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ModuleAnnotationsKt {
    public static final List<ClassId> getAnnotationsOnContainingJsModule(DeclarationDescriptor descriptor) {
        DeserializedContainerSource containerSource;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        ClassOrPackageFragmentDescriptor classOrPackageFragmentDescriptor = (ClassOrPackageFragmentDescriptor) DescriptorUtils.getParentOfType(descriptor, ClassOrPackageFragmentDescriptor.class, false);
        if (classOrPackageFragmentDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        DeserializedMemberDescriptor deserializedMemberDescriptor = descriptor instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) descriptor : null;
        DeserializedContainerSource source = (deserializedMemberDescriptor == null || (containerSource = deserializedMemberDescriptor.getContainerSource()) == null) ? classOrPackageFragmentDescriptor.getSource() : containerSource;
        Intrinsics.checkNotNull(source);
        KotlinJavascriptPackageFragment.JsContainerSource jsContainerSource = source instanceof KotlinJavascriptPackageFragment.JsContainerSource ? (KotlinJavascriptPackageFragment.JsContainerSource) source : null;
        if (jsContainerSource != null) {
            return jsContainerSource.getAnnotations();
        }
        return null;
    }
}
