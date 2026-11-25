package net.bytebuddy.description;

import javax.annotation.Nonnull;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface DeclaredByType {

    public interface WithMandatoryDeclaration extends DeclaredByType {
        @Override // net.bytebuddy.description.DeclaredByType
        @Nonnull
        TypeDefinition getDeclaringType();
    }

    @MaybeNull
    TypeDefinition getDeclaringType();
}
