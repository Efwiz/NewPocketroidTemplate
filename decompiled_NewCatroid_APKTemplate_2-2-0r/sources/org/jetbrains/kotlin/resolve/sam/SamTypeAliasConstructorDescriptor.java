package org.jetbrains.kotlin.resolve.sam;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithNavigationSubstitute;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;

/* JADX INFO: compiled from: SamTypeAliasConstructorDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00020\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/sam/SamTypeAliasConstructorDescriptor;", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorWithNavigationSubstitute;", "expandedConstructorDescriptor", "getExpandedConstructorDescriptor", "()Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "substitute", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getSubstitute", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "typeAliasDescriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getTypeAliasDescriptor", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SamTypeAliasConstructorDescriptor extends DeclarationDescriptorWithNavigationSubstitute, SamConstructorDescriptor {
    SamConstructorDescriptor getExpandedConstructorDescriptor();

    @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithNavigationSubstitute
    DeclarationDescriptor getSubstitute();

    TypeAliasDescriptor getTypeAliasDescriptor();

    /* JADX INFO: compiled from: SamTypeAliasConstructorDescriptor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static DeclarationDescriptor getSubstitute(SamTypeAliasConstructorDescriptor samTypeAliasConstructorDescriptor) {
            return samTypeAliasConstructorDescriptor.getTypeAliasDescriptor();
        }
    }
}
