package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.Name;
import org.slf4j.Logger;

/* JADX INFO: loaded from: classes3.dex */
public class RootContext extends CodegenContext<FakeDescriptor> {
    private final GenerationState state;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "state";
        } else {
            objArr[0] = "org/jetbrains/kotlin/codegen/context/RootContext";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/codegen/context/RootContext";
        } else {
            objArr[1] = "getState";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RootContext(GenerationState generationState) {
        super(new FakeDescriptor(), OwnerKind.PACKAGE, null, null, null, null);
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        this.state = generationState;
    }

    @Override // org.jetbrains.kotlin.codegen.context.CodegenContext
    public GenerationState getState() {
        GenerationState generationState = this.state;
        if (generationState == null) {
            $$$reportNull$$$0(1);
        }
        return generationState;
    }

    public String toString() {
        return Logger.ROOT_LOGGER_NAME;
    }

    static class FakeDescriptor implements DeclarationDescriptor {
        FakeDescriptor() {
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public DeclarationDescriptor getOriginal() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public DeclarationDescriptor getContainingDeclaration() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        public <R, D> R accept(DeclarationDescriptorVisitor<R, D> declarationDescriptorVisitor, D d) {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.descriptors.DeclarationDescriptor
        /* JADX INFO: renamed from: acceptVoid */
        public void mo7454acceptVoid(DeclarationDescriptorVisitor<Void, Void> declarationDescriptorVisitor) {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.descriptors.annotations.Annotated
        public Annotations getAnnotations() {
            throw new IllegalStateException();
        }

        @Override // org.jetbrains.kotlin.descriptors.Named
        /* JADX INFO: renamed from: getName */
        public Name getShortName() {
            throw new IllegalStateException();
        }
    }
}
