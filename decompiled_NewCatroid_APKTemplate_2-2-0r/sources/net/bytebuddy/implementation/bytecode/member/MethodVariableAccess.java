package net.bytebuddy.implementation.bytecode.member;

import java.util.ArrayList;
import java.util.Iterator;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum MethodVariableAccess {
    INTEGER(21, 54, StackSize.SINGLE),
    LONG(22, 55, StackSize.DOUBLE),
    FLOAT(23, 56, StackSize.SINGLE),
    DOUBLE(24, 57, StackSize.DOUBLE),
    REFERENCE(25, 58, StackSize.SINGLE);

    private static /* synthetic */ StackManipulation loadThis;
    private final int loadOpcode;
    private final StackSize size;
    private final int storeOpcode;

    MethodVariableAccess(int i, int i2, StackSize stackSize) {
        this.loadOpcode = i;
        this.size = stackSize;
        this.storeOpcode = i2;
    }

    public static MethodVariableAccess of(TypeDefinition typeDefinition) {
        if (typeDefinition.isPrimitive()) {
            if (typeDefinition.represents(Long.TYPE)) {
                return LONG;
            }
            if (typeDefinition.represents(Double.TYPE)) {
                return DOUBLE;
            }
            if (typeDefinition.represents(Float.TYPE)) {
                return FLOAT;
            }
            if (typeDefinition.represents(Void.TYPE)) {
                throw new IllegalArgumentException("Variable type cannot be void");
            }
            return INTEGER;
        }
        return REFERENCE;
    }

    public static MethodLoading allArgumentsOf(MethodDescription methodDescription) {
        return new MethodLoading(methodDescription, MethodLoading.TypeCastingHandler.NoOp.INSTANCE);
    }

    @CachedReturnPlugin.Enhance("loadThis")
    public static StackManipulation loadThis() {
        StackManipulation stackManipulationLoadFrom = loadThis != null ? null : REFERENCE.loadFrom(0);
        if (stackManipulationLoadFrom == null) {
            return loadThis;
        }
        loadThis = stackManipulationLoadFrom;
        return stackManipulationLoadFrom;
    }

    public StackManipulation loadFrom(int i) {
        return new OffsetLoading(i);
    }

    public StackManipulation storeAt(int i) {
        return new OffsetWriting(i);
    }

    public StackManipulation increment(int i, int i2) {
        if (this != INTEGER) {
            throw new IllegalStateException("Cannot increment type: " + this);
        }
        return new OffsetIncrementing(i, i2);
    }

    public static StackManipulation load(ParameterDescription parameterDescription) {
        return of(parameterDescription.getType()).loadFrom(parameterDescription.getOffset());
    }

    public static StackManipulation store(ParameterDescription parameterDescription) {
        return of(parameterDescription.getType()).storeAt(parameterDescription.getOffset());
    }

    public static StackManipulation increment(ParameterDescription parameterDescription, int i) {
        return of(parameterDescription.getType()).increment(parameterDescription.getOffset(), i);
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class MethodLoading extends StackManipulation.AbstractBase {
        private final MethodDescription methodDescription;
        private final TypeCastingHandler typeCastingHandler;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            MethodLoading methodLoading = (MethodLoading) obj;
            return this.methodDescription.equals(methodLoading.methodDescription) && this.typeCastingHandler.equals(methodLoading.typeCastingHandler);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.typeCastingHandler.hashCode();
        }

        protected MethodLoading(MethodDescription methodDescription, TypeCastingHandler typeCastingHandler) {
            this.methodDescription = methodDescription;
            this.typeCastingHandler = typeCastingHandler;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            ArrayList arrayList = new ArrayList();
            Iterator it = this.methodDescription.getParameters().iterator();
            while (it.hasNext()) {
                ParameterDescription parameterDescription = (ParameterDescription) it.next();
                TypeDescription typeDescriptionAsErasure = parameterDescription.getType().asErasure();
                arrayList.add(MethodVariableAccess.of(typeDescriptionAsErasure).loadFrom(parameterDescription.getOffset()));
                arrayList.add(this.typeCastingHandler.ofIndex(typeDescriptionAsErasure, parameterDescription.getIndex()));
            }
            return new StackManipulation.Compound(arrayList).apply(methodVisitor, context);
        }

        public StackManipulation prependThisReference() {
            return this.methodDescription.isStatic() ? this : new StackManipulation.Compound(MethodVariableAccess.loadThis(), this);
        }

        public MethodLoading asBridgeOf(MethodDescription methodDescription) {
            return new MethodLoading(this.methodDescription, new TypeCastingHandler.ForBridgeTarget(methodDescription));
        }

        protected interface TypeCastingHandler {
            StackManipulation ofIndex(TypeDescription typeDescription, int i);

            public enum NoOp implements TypeCastingHandler {
                INSTANCE;

                @Override // net.bytebuddy.implementation.bytecode.member.MethodVariableAccess.MethodLoading.TypeCastingHandler
                public StackManipulation ofIndex(TypeDescription typeDescription, int i) {
                    return StackManipulation.Trivial.INSTANCE;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForBridgeTarget implements TypeCastingHandler {
                private final MethodDescription bridgeTarget;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.bridgeTarget.equals(((ForBridgeTarget) obj).bridgeTarget);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.bridgeTarget.hashCode();
                }

                public ForBridgeTarget(MethodDescription methodDescription) {
                    this.bridgeTarget = methodDescription;
                }

                @Override // net.bytebuddy.implementation.bytecode.member.MethodVariableAccess.MethodLoading.TypeCastingHandler
                public StackManipulation ofIndex(TypeDescription typeDescription, int i) {
                    TypeDescription typeDescriptionAsErasure = ((ParameterDescription) this.bridgeTarget.getParameters().get(i)).getType().asErasure();
                    return typeDescription.equals(typeDescriptionAsErasure) ? StackManipulation.Trivial.INSTANCE : TypeCasting.to(typeDescriptionAsErasure);
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class OffsetLoading extends StackManipulation.AbstractBase {
        private final int offset;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            OffsetLoading offsetLoading = (OffsetLoading) obj;
            return this.offset == offsetLoading.offset && MethodVariableAccess.this.equals(MethodVariableAccess.this);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.offset) * 31) + MethodVariableAccess.this.hashCode();
        }

        protected OffsetLoading(int i) {
            this.offset = i;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitVarInsn(MethodVariableAccess.this.loadOpcode, this.offset);
            return MethodVariableAccess.this.size.toIncreasingSize();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class OffsetWriting extends StackManipulation.AbstractBase {
        private final int offset;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            OffsetWriting offsetWriting = (OffsetWriting) obj;
            return this.offset == offsetWriting.offset && MethodVariableAccess.this.equals(MethodVariableAccess.this);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.offset) * 31) + MethodVariableAccess.this.hashCode();
        }

        protected OffsetWriting(int i) {
            this.offset = i;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitVarInsn(MethodVariableAccess.this.storeOpcode, this.offset);
            return MethodVariableAccess.this.size.toDecreasingSize();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class OffsetIncrementing extends StackManipulation.AbstractBase {
        private final int offset;
        private final int value;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            OffsetIncrementing offsetIncrementing = (OffsetIncrementing) obj;
            return this.offset == offsetIncrementing.offset && this.value == offsetIncrementing.value;
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.offset) * 31) + this.value;
        }

        protected OffsetIncrementing(int i, int i2) {
            this.offset = i;
            this.value = i2;
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitIincInsn(this.offset, this.value);
            return StackManipulation.Size.ZERO;
        }
    }
}
