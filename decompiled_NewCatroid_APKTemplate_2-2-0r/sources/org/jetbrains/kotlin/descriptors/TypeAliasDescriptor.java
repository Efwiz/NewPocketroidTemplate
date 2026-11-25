package org.jetbrains.kotlin.descriptors;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.mpp.TypeAliasSymbolMarker;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: TypeAliasDescriptor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u00012\u00020\u0002J\b\u0010\u0012\u001a\u00020\u0000H&R\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "Lorg/jetbrains/kotlin/mpp/TypeAliasSymbolMarker;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "constructors", "", "Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;", "getConstructors", "()Ljava/util/Collection;", "expandedType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getExpandedType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "underlyingType", "getUnderlyingType", "getOriginal", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeAliasDescriptor extends ClassifierDescriptorWithTypeParameters, TypeAliasSymbolMarker {
    ClassDescriptor getClassDescriptor();

    Collection<TypeAliasConstructorDescriptor> getConstructors();

    SimpleType getExpandedType();

    @Override // org.jetbrains.kotlin.descriptors.ClassifierDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    TypeAliasDescriptor getOriginal();

    SimpleType getUnderlyingType();
}
