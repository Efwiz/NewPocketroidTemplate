package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: loaded from: classes4.dex */
public interface ClassDescriptorWithResolutionScopes extends ClassDescriptor {
    @Override // org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getCompanionObjectDescriptor */
    ClassDescriptorWithResolutionScopes mo6346getCompanionObjectDescriptor();

    Collection<CallableMemberDescriptor> getDeclaredCallableMembers();

    LexicalScope getScopeForClassHeaderResolution();

    LexicalScope getScopeForCompanionObjectHeaderResolution();

    LexicalScope getScopeForConstructorHeaderResolution();

    LexicalScope getScopeForInitializerResolution();

    LexicalScope getScopeForMemberDeclarationResolution();

    LexicalScope getScopeForStaticMemberDeclarationResolution();
}
