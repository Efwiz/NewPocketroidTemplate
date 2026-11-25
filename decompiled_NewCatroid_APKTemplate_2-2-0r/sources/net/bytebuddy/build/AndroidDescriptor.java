package net.bytebuddy.build;

import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes12.dex */
public interface AndroidDescriptor {

    public enum TypeScope {
        LOCAL,
        EXTERNAL
    }

    TypeScope getTypeScope(TypeDescription typeDescription);

    public enum Trivial implements AndroidDescriptor {
        LOCAL(TypeScope.LOCAL),
        EXTERNAL(TypeScope.EXTERNAL);

        private final TypeScope typeScope;

        Trivial(TypeScope typeScope) {
            this.typeScope = typeScope;
        }

        @Override // net.bytebuddy.build.AndroidDescriptor
        public TypeScope getTypeScope(TypeDescription typeDescription) {
            return this.typeScope;
        }
    }
}
