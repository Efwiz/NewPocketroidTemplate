package org.jetbrains.kotlin.types.error;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: ErrorClassDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/types/error/ErrorClassDescriptor;", "Lorg/jetbrains/kotlin/descriptors/impl/ClassDescriptorImpl;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getMemberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "typeArguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "typeSubstitution", "Lorg/jetbrains/kotlin/types/TypeSubstitution;", "substitute", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorClassDescriptor extends ClassDescriptorImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ErrorClassDescriptor(Name name2) {
        super(ErrorUtils.INSTANCE.getErrorModule(), name2, Modality.OPEN, ClassKind.CLASS, CollectionsKt.emptyList(), SourceElement.NO_SOURCE, false, LockBasedStorageManager.NO_LOCKS);
        Intrinsics.checkNotNullParameter(name2, "name");
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreate = ClassConstructorDescriptorImpl.create(this, Annotations.INSTANCE.getEMPTY(), true, SourceElement.NO_SOURCE);
        classConstructorDescriptorImplCreate.initialize(CollectionsKt.emptyList(), DescriptorVisibilities.INTERNAL);
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreate, "apply(...)");
        ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ERROR_CLASS;
        String string = classConstructorDescriptorImplCreate.getShortName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        ErrorScope errorScopeCreateErrorScope = ErrorUtils.createErrorScope(errorScopeKind, string, "");
        classConstructorDescriptorImplCreate.setReturnType(new ErrorType(ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.ERROR_CLASS, new String[0]), errorScopeCreateErrorScope, ErrorTypeKind.ERROR_CLASS, null, false, new String[0], 24, null));
        initialize(errorScopeCreateErrorScope, SetsKt.setOf(classConstructorDescriptorImplCreate), classConstructorDescriptorImplCreate);
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public ClassDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public MemberScope getMemberScope(List<? extends TypeProjection> typeArguments, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ERROR_CLASS;
        String string = getShortName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return ErrorUtils.createErrorScope(errorScopeKind, string, typeArguments.toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.impl.ModuleAwareClassDescriptor
    public MemberScope getMemberScope(TypeSubstitution typeSubstitution, KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(typeSubstitution, "typeSubstitution");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        ErrorScopeKind errorScopeKind = ErrorScopeKind.SCOPE_FOR_ERROR_CLASS;
        String string = getShortName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return ErrorUtils.createErrorScope(errorScopeKind, string, typeSubstitution.toString());
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl
    public String toString() {
        String strAsString = getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
