package net.bytebuddy.implementation.bytecode.assign.reference;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import net.bytebuddy.utility.QueueFactory;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum GenericTypeAwareAssigner implements Assigner {
    INSTANCE;

    @Override // net.bytebuddy.implementation.bytecode.assign.Assigner
    public StackManipulation assign(TypeDescription.Generic generic, TypeDescription.Generic generic2, Assigner.Typing typing) {
        if (generic.isPrimitive() || generic2.isPrimitive()) {
            return generic.equals(generic2) ? StackManipulation.Trivial.INSTANCE : StackManipulation.Illegal.INSTANCE;
        }
        if (((Boolean) generic.accept(new IsAssignableToVisitor(generic2))).booleanValue()) {
            return StackManipulation.Trivial.INSTANCE;
        }
        if (typing.isDynamic()) {
            return generic.asErasure().isAssignableTo(generic2.asErasure()) ? StackManipulation.Trivial.INSTANCE : TypeCasting.to(generic2);
        }
        return StackManipulation.Illegal.INSTANCE;
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class IsAssignableToVisitor implements TypeDescription.Generic.Visitor<Boolean> {
        private final boolean polymorphic;
        private final TypeDescription.Generic typeDescription;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            IsAssignableToVisitor isAssignableToVisitor = (IsAssignableToVisitor) obj;
            return this.polymorphic == isAssignableToVisitor.polymorphic && this.typeDescription.equals(isAssignableToVisitor.typeDescription);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.typeDescription.hashCode()) * 31) + (this.polymorphic ? 1 : 0);
        }

        public IsAssignableToVisitor(TypeDescription.Generic generic) {
            this(generic, true);
        }

        protected IsAssignableToVisitor(TypeDescription.Generic generic, boolean z) {
            this.typeDescription = generic;
            this.polymorphic = z;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
        public Boolean onGenericArray(TypeDescription.Generic generic) {
            return (Boolean) this.typeDescription.accept(new OfGenericArray(generic, this.polymorphic));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
        public Boolean onWildcard(TypeDescription.Generic generic) {
            return (Boolean) this.typeDescription.accept(new OfWildcard(generic));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
        public Boolean onParameterizedType(TypeDescription.Generic generic) {
            return (Boolean) this.typeDescription.accept(new OfParameterizedType(generic, this.polymorphic));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
        public Boolean onTypeVariable(TypeDescription.Generic generic) {
            if (generic.getTypeVariableSource().isInferrable()) {
                throw new UnsupportedOperationException("Assignability checks for type variables declared by methods are not currently supported");
            }
            if (generic.equals(this.typeDescription)) {
                return true;
            }
            if (this.polymorphic) {
                Queue queueMake = QueueFactory.make(generic.getUpperBounds());
                while (!queueMake.isEmpty()) {
                    TypeDescription.Generic generic2 = (TypeDescription.Generic) queueMake.remove();
                    if (((Boolean) generic2.accept(new IsAssignableToVisitor(this.typeDescription))).booleanValue()) {
                        return true;
                    }
                    if (generic2.getSort().isTypeVariable()) {
                        queueMake.addAll(generic2.getUpperBounds());
                    }
                }
                return false;
            }
            return false;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
        public Boolean onNonGenericType(TypeDescription.Generic generic) {
            return (Boolean) this.typeDescription.accept(new OfNonGenericType(generic, this.polymorphic));
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static abstract class OfManifestType implements TypeDescription.Generic.Visitor<Boolean> {
            protected final boolean polymorphic;
            protected final TypeDescription.Generic typeDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                OfManifestType ofManifestType = (OfManifestType) obj;
                return this.polymorphic == ofManifestType.polymorphic && this.typeDescription.equals(ofManifestType.typeDescription);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.typeDescription.hashCode()) * 31) + (this.polymorphic ? 1 : 0);
            }

            protected OfManifestType(TypeDescription.Generic generic, boolean z) {
                this.typeDescription = generic;
                this.polymorphic = z;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onWildcard(TypeDescription.Generic generic) {
                Iterator it = generic.getUpperBounds().iterator();
                while (it.hasNext()) {
                    if (!((Boolean) this.typeDescription.accept(new IsAssignableToVisitor((TypeDescription.Generic) it.next()))).booleanValue()) {
                        return false;
                    }
                }
                Iterator it2 = generic.getLowerBounds().iterator();
                while (it2.hasNext()) {
                    if (!((Boolean) ((TypeDescription.Generic) it2.next()).accept(new IsAssignableToVisitor(this.typeDescription))).booleanValue()) {
                        return false;
                    }
                }
                return true;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onTypeVariable(TypeDescription.Generic generic) {
                if (generic.getTypeVariableSource().isInferrable()) {
                    throw new UnsupportedOperationException("Assignability checks for type variables declared by methods arel not currently supported");
                }
                return false;
            }
        }

        protected static abstract class OfSimpleType extends OfManifestType {
            protected OfSimpleType(TypeDescription.Generic generic, boolean z) {
                super(generic, z);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onParameterizedType(TypeDescription.Generic generic) {
                Queue queueMake = QueueFactory.make(Collections.singleton(this.typeDescription));
                HashSet hashSet = new HashSet(Collections.singleton(this.typeDescription.asErasure()));
                do {
                    TypeDescription.Generic generic2 = (TypeDescription.Generic) queueMake.remove();
                    if (generic2.asErasure().equals(generic.asErasure())) {
                        if (generic2.getSort().isNonGeneric()) {
                            return true;
                        }
                        TypeList.Generic typeArguments = generic2.getTypeArguments();
                        TypeList.Generic typeArguments2 = generic.getTypeArguments();
                        int size = typeArguments2.size();
                        if (typeArguments.size() != size) {
                            return false;
                        }
                        for (int i = 0; i < size; i++) {
                            if (!((Boolean) ((TypeDescription.Generic) typeArguments.get(i)).accept(new IsAssignableToVisitor((TypeDescription.Generic) typeArguments2.get(i), false))).booleanValue()) {
                                return false;
                            }
                        }
                        TypeDescription.Generic ownerType = generic.getOwnerType();
                        return Boolean.valueOf(ownerType == null || ((Boolean) ownerType.accept(new IsAssignableToVisitor(ownerType))).booleanValue());
                    }
                    if (this.polymorphic) {
                        TypeDescription.Generic superClass = generic2.getSuperClass();
                        if (superClass != null && hashSet.add(superClass.asErasure())) {
                            queueMake.add(superClass);
                        }
                        for (TypeDescription.Generic generic3 : generic2.getInterfaces()) {
                            if (hashSet.add(generic3.asErasure())) {
                                queueMake.add(generic3);
                            }
                        }
                    }
                } while (!queueMake.isEmpty());
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onNonGenericType(TypeDescription.Generic generic) {
                boolean zEquals;
                if (this.polymorphic) {
                    zEquals = this.typeDescription.asErasure().isAssignableTo(generic.asErasure());
                } else {
                    zEquals = this.typeDescription.asErasure().equals(generic.asErasure());
                }
                return Boolean.valueOf(zEquals);
            }
        }

        protected static class OfGenericArray extends OfManifestType {
            protected OfGenericArray(TypeDescription.Generic generic, boolean z) {
                super(generic, z);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onGenericArray(TypeDescription.Generic generic) {
                TypeDescription.Generic componentType = this.typeDescription.getComponentType();
                TypeDescription.Generic componentType2 = generic.getComponentType();
                while (componentType.getSort().isGenericArray() && componentType2.getSort().isGenericArray()) {
                    componentType = componentType.getComponentType();
                    componentType2 = componentType2.getComponentType();
                }
                return Boolean.valueOf((componentType.getSort().isGenericArray() || componentType2.getSort().isGenericArray() || !((Boolean) componentType.accept(new IsAssignableToVisitor(componentType2))).booleanValue()) ? false : true);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onParameterizedType(TypeDescription.Generic generic) {
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onNonGenericType(TypeDescription.Generic generic) {
                boolean zEquals;
                if (this.polymorphic) {
                    zEquals = this.typeDescription.asErasure().isAssignableTo(generic.asErasure());
                } else {
                    zEquals = this.typeDescription.asErasure().equals(generic.asErasure());
                }
                return Boolean.valueOf(zEquals);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class OfWildcard implements TypeDescription.Generic.Visitor<Boolean> {
            private final TypeDescription.Generic wildcard;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.wildcard.equals(((OfWildcard) obj).wildcard);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.wildcard.hashCode();
            }

            protected OfWildcard(TypeDescription.Generic generic) {
                this.wildcard = generic;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onGenericArray(TypeDescription.Generic generic) {
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onWildcard(TypeDescription.Generic generic) {
                boolean z;
                Iterator it = generic.getUpperBounds().iterator();
                boolean z2 = false;
                while (true) {
                    while (true) {
                        if (it.hasNext()) {
                            TypeDescription.Generic generic2 = (TypeDescription.Generic) it.next();
                            Iterator it2 = this.wildcard.getUpperBounds().iterator();
                            while (it2.hasNext()) {
                                if (!((Boolean) ((TypeDescription.Generic) it2.next()).accept(new IsAssignableToVisitor(generic2))).booleanValue()) {
                                    return false;
                                }
                            }
                            z = z || !generic2.represents(Object.class);
                        } else {
                            boolean z3 = false;
                            for (TypeDescription.Generic generic3 : generic.getLowerBounds()) {
                                Iterator it3 = this.wildcard.getLowerBounds().iterator();
                                while (it3.hasNext()) {
                                    if (!((Boolean) generic3.accept(new IsAssignableToVisitor((TypeDescription.Generic) it3.next()))).booleanValue()) {
                                        return false;
                                    }
                                }
                                z3 = true;
                            }
                            if (z) {
                                return Boolean.valueOf(this.wildcard.getLowerBounds().isEmpty());
                            }
                            if (z3) {
                                TypeList.Generic upperBounds = this.wildcard.getUpperBounds();
                                if (upperBounds.size() == 0 || (upperBounds.size() == 1 && upperBounds.getOnly().represents(Object.class))) {
                                    z2 = true;
                                }
                                return Boolean.valueOf(z2);
                            }
                            return true;
                        }
                    }
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onParameterizedType(TypeDescription.Generic generic) {
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onTypeVariable(TypeDescription.Generic generic) {
                return false;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onNonGenericType(TypeDescription.Generic generic) {
                return false;
            }
        }

        protected static class OfParameterizedType extends OfSimpleType {
            protected OfParameterizedType(TypeDescription.Generic generic, boolean z) {
                super(generic, z);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onGenericArray(TypeDescription.Generic generic) {
                return false;
            }
        }

        protected static class OfNonGenericType extends OfSimpleType {
            protected OfNonGenericType(TypeDescription.Generic generic, boolean z) {
                super(generic, z);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // net.bytebuddy.description.type.TypeDescription.Generic.Visitor
            public Boolean onGenericArray(TypeDescription.Generic generic) {
                boolean zEquals;
                if (this.polymorphic) {
                    zEquals = this.typeDescription.asErasure().isAssignableTo(generic.asErasure());
                } else {
                    zEquals = this.typeDescription.asErasure().equals(generic.asErasure());
                }
                return Boolean.valueOf(zEquals);
            }
        }
    }
}
