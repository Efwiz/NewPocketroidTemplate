package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: ClassifierBasedTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0004J\u0013\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\bH\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\bH$R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/types/ClassifierBasedTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "()V", "hashCode", "", "areFqNamesEqual", "", "first", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "second", Namer.EQUALS_METHOD_NAME, "other", "", "getDeclarationDescriptor", "hasMeaningfulFqName", "descriptor", "isSameClassifier", "classifier", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ClassifierBasedTypeConstructor implements TypeConstructor {
    private int hashCode;

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor */
    public abstract ClassifierDescriptor getThis$0();

    protected abstract boolean isSameClassifier(ClassifierDescriptor classifier);

    public int hashCode() {
        int iIdentityHashCode;
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        ClassifierDescriptor this$0 = getThis$0();
        if (hasMeaningfulFqName(this$0)) {
            iIdentityHashCode = DescriptorUtils.getFqName(this$0).hashCode();
        } else {
            iIdentityHashCode = System.identityHashCode(this);
        }
        this.hashCode = iIdentityHashCode;
        return iIdentityHashCode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TypeConstructor) || other.hashCode() != hashCode()) {
            return false;
        }
        TypeConstructor typeConstructor = (TypeConstructor) other;
        if (typeConstructor.getParameters().size() != getParameters().size()) {
            return false;
        }
        ClassifierDescriptor this$0 = getThis$0();
        ClassifierDescriptor this$1 = typeConstructor.getThis$0();
        if (this$1 != null && hasMeaningfulFqName(this$0) && hasMeaningfulFqName(this$1)) {
            return isSameClassifier(this$1);
        }
        return false;
    }

    protected final boolean areFqNamesEqual(ClassifierDescriptor first, ClassifierDescriptor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        if (!Intrinsics.areEqual(first.getShortName(), second.getShortName())) {
            return false;
        }
        DeclarationDescriptor containingDeclaration = first.getContainingDeclaration();
        for (DeclarationDescriptor containingDeclaration2 = second.getContainingDeclaration(); containingDeclaration != null && containingDeclaration2 != null; containingDeclaration2 = containingDeclaration2.getContainingDeclaration()) {
            if (containingDeclaration instanceof ModuleDescriptor) {
                return containingDeclaration2 instanceof ModuleDescriptor;
            }
            if (containingDeclaration2 instanceof ModuleDescriptor) {
                return false;
            }
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                return (containingDeclaration2 instanceof PackageFragmentDescriptor) && Intrinsics.areEqual(((PackageFragmentDescriptor) containingDeclaration).getFqName(), ((PackageFragmentDescriptor) containingDeclaration2).getFqName());
            }
            if ((containingDeclaration2 instanceof PackageFragmentDescriptor) || !Intrinsics.areEqual(containingDeclaration.getShortName(), containingDeclaration2.getShortName())) {
                return false;
            }
            containingDeclaration = containingDeclaration.getContainingDeclaration();
        }
        return true;
    }

    private final boolean hasMeaningfulFqName(ClassifierDescriptor descriptor) {
        ClassifierDescriptor classifierDescriptor = descriptor;
        return (org.jetbrains.kotlin.types.error.ErrorUtils.isError(classifierDescriptor) || DescriptorUtils.isLocal(classifierDescriptor)) ? false : true;
    }
}
