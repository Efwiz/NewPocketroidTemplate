package org.jetbrains.kotlin.resolve.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.OverloadFilter;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedCallableMemberDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;

/* JADX INFO: compiled from: JvmOverloadFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmOverloadFilter;", "Lorg/jetbrains/kotlin/resolve/OverloadFilter;", "()V", "filterPackageMemberOverloads", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "overloads", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmOverloadFilter implements OverloadFilter {
    public static final JvmOverloadFilter INSTANCE = new JvmOverloadFilter();

    private JvmOverloadFilter() {
    }

    @Override // org.jetbrains.kotlin.resolve.OverloadFilter
    public Collection<DeclarationDescriptorNonRoot> filterPackageMemberOverloads(Collection<? extends DeclarationDescriptorNonRoot> overloads) {
        Intrinsics.checkNotNullParameter(overloads, "overloads");
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot : overloads) {
            KtFile containingFile = DescriptorToSourceUtils.getContainingFile(declarationDescriptorNonRoot);
            if (containingFile != null) {
                arrayList.add(declarationDescriptorNonRoot);
                hashSet.add(JvmFileClassUtil.getFileClassInfoNoResolve(containingFile).getFileClassFqName());
            }
        }
        for (DeclarationDescriptorNonRoot declarationDescriptorNonRoot2 : overloads) {
            if (!(declarationDescriptorNonRoot2 instanceof ConstructorDescriptor) && (declarationDescriptorNonRoot2 instanceof DeserializedCallableMemberDescriptor) && !hashSet.contains(JvmFileClassUtil.INSTANCE.getPartFqNameForDeserialized((DeserializedMemberDescriptor) declarationDescriptorNonRoot2))) {
                arrayList.add(declarationDescriptorNonRoot2);
            }
        }
        return arrayList;
    }
}
