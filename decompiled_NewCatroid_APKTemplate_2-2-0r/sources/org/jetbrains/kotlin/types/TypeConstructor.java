package org.jetbrains.kotlin.types;

import java.util.Collection;
import java.util.List;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeConstructor extends TypeConstructorMarker {
    KotlinBuiltIns getBuiltIns();

    /* JADX INFO: renamed from: getDeclarationDescriptor */
    ClassifierDescriptor mo7434getDeclarationDescriptor();

    List<TypeParameterDescriptor> getParameters();

    /* JADX INFO: renamed from: getSupertypes */
    Collection<KotlinType> mo7438getSupertypes();

    boolean isDenotable();

    boolean isFinal();

    TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner);
}
