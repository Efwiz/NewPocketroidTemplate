package net.bytebuddy.dynamic.scaffold.subclass;

import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class SubclassImplementationTarget extends Implementation.Target.AbstractBase {
    protected final OriginTypeResolver originTypeResolver;

    public enum OriginTypeResolver {
        SUPER_CLASS { // from class: net.bytebuddy.dynamic.scaffold.subclass.SubclassImplementationTarget.OriginTypeResolver.1
            @Override // net.bytebuddy.dynamic.scaffold.subclass.SubclassImplementationTarget.OriginTypeResolver
            protected TypeDefinition identify(TypeDescription typeDescription) {
                return typeDescription.getSuperClass();
            }
        },
        LEVEL_TYPE { // from class: net.bytebuddy.dynamic.scaffold.subclass.SubclassImplementationTarget.OriginTypeResolver.2
            @Override // net.bytebuddy.dynamic.scaffold.subclass.SubclassImplementationTarget.OriginTypeResolver
            protected TypeDefinition identify(TypeDescription typeDescription) {
                return typeDescription;
            }
        };

        protected abstract TypeDefinition identify(TypeDescription typeDescription);
    }

    @Override // net.bytebuddy.implementation.Implementation.Target.AbstractBase
    public boolean equals(@MaybeNull Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.originTypeResolver.equals(((SubclassImplementationTarget) obj).originTypeResolver);
    }

    @Override // net.bytebuddy.implementation.Implementation.Target.AbstractBase
    public int hashCode() {
        return (super.hashCode() * 31) + this.originTypeResolver.hashCode();
    }

    protected SubclassImplementationTarget(TypeDescription typeDescription, MethodGraph.Linked linked, Implementation.Target.AbstractBase.DefaultMethodInvocation defaultMethodInvocation, OriginTypeResolver originTypeResolver) {
        super(typeDescription, linked, defaultMethodInvocation);
        this.originTypeResolver = originTypeResolver;
    }

    @Override // net.bytebuddy.implementation.Implementation.Target
    public Implementation.SpecialMethodInvocation invokeSuper(MethodDescription.SignatureToken signatureToken) {
        if (signatureToken.getName().equals("<init>")) {
            return invokeConstructor(signatureToken);
        }
        return invokeMethod(signatureToken);
    }

    private Implementation.SpecialMethodInvocation invokeConstructor(MethodDescription.SignatureToken signatureToken) {
        MethodList methodListFilter;
        TypeDescription.Generic superClass = this.instrumentedType.getSuperClass();
        if (superClass == null) {
            methodListFilter = new MethodList.Empty();
        } else {
            methodListFilter = superClass.getDeclaredMethods().filter(ElementMatchers.hasSignature(signatureToken).and(ElementMatchers.isVisibleTo(this.instrumentedType)));
        }
        return methodListFilter.size() == 1 ? Implementation.SpecialMethodInvocation.Simple.of((MethodDescription) methodListFilter.getOnly(), superClass.asErasure()) : Implementation.SpecialMethodInvocation.Illegal.INSTANCE;
    }

    private Implementation.SpecialMethodInvocation invokeMethod(MethodDescription.SignatureToken signatureToken) {
        MethodGraph.Node nodeLocate = this.methodGraph.getSuperClassGraph().locate(signatureToken);
        return nodeLocate.getSort().isUnique() ? Implementation.SpecialMethodInvocation.Simple.of(nodeLocate.getRepresentative(), this.instrumentedType.getSuperClass().asErasure()) : Implementation.SpecialMethodInvocation.Illegal.INSTANCE;
    }

    @Override // net.bytebuddy.implementation.Implementation.Target
    public TypeDefinition getOriginType() {
        return this.originTypeResolver.identify(this.instrumentedType);
    }

    public enum Factory implements Implementation.Target.Factory {
        SUPER_CLASS(OriginTypeResolver.SUPER_CLASS),
        LEVEL_TYPE(OriginTypeResolver.LEVEL_TYPE);

        private final OriginTypeResolver originTypeResolver;

        Factory(OriginTypeResolver originTypeResolver) {
            this.originTypeResolver = originTypeResolver;
        }

        @Override // net.bytebuddy.implementation.Implementation.Target.Factory
        public Implementation.Target make(TypeDescription typeDescription, MethodGraph.Linked linked, ClassFileVersion classFileVersion) {
            return new SubclassImplementationTarget(typeDescription, linked, Implementation.Target.AbstractBase.DefaultMethodInvocation.of(classFileVersion), this.originTypeResolver);
        }
    }
}
