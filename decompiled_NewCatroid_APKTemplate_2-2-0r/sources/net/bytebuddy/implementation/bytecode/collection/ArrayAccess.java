package net.bytebuddy.implementation.bytecode.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum ArrayAccess {
    BYTE(51, 84, StackSize.SINGLE),
    SHORT(53, 86, StackSize.SINGLE),
    CHARACTER(52, 85, StackSize.SINGLE),
    INTEGER(46, 79, StackSize.SINGLE),
    LONG(47, 80, StackSize.DOUBLE),
    FLOAT(48, 81, StackSize.SINGLE),
    DOUBLE(49, 82, StackSize.DOUBLE),
    REFERENCE(50, 83, StackSize.SINGLE);

    private final int loadOpcode;
    private final StackSize stackSize;
    private final int storeOpcode;

    ArrayAccess(int i, int i2, StackSize stackSize) {
        this.loadOpcode = i;
        this.storeOpcode = i2;
        this.stackSize = stackSize;
    }

    public static ArrayAccess of(TypeDefinition typeDefinition) {
        if (!typeDefinition.isPrimitive()) {
            return REFERENCE;
        }
        if (typeDefinition.represents(Boolean.TYPE) || typeDefinition.represents(Byte.TYPE)) {
            return BYTE;
        }
        if (typeDefinition.represents(Short.TYPE)) {
            return SHORT;
        }
        if (typeDefinition.represents(Character.TYPE)) {
            return CHARACTER;
        }
        if (typeDefinition.represents(Integer.TYPE)) {
            return INTEGER;
        }
        if (typeDefinition.represents(Long.TYPE)) {
            return LONG;
        }
        if (typeDefinition.represents(Float.TYPE)) {
            return FLOAT;
        }
        if (typeDefinition.represents(Double.TYPE)) {
            return DOUBLE;
        }
        throw new IllegalArgumentException("Not a legal array type: " + typeDefinition);
    }

    public StackManipulation load() {
        return new Loader();
    }

    public StackManipulation store() {
        return new Putter();
    }

    public StackManipulation forEach(List<? extends StackManipulation> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<? extends StackManipulation> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            arrayList.add(new StackManipulation.Compound(Duplication.SINGLE, IntegerConstant.forValue(i), new Loader(), it.next()));
            i++;
        }
        return new StackManipulation.Compound(arrayList);
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class Loader extends StackManipulation.AbstractBase {
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && ArrayAccess.this.equals(ArrayAccess.this);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + ArrayAccess.this.hashCode();
        }

        protected Loader() {
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitInsn(ArrayAccess.this.loadOpcode);
            return ArrayAccess.this.stackSize.toIncreasingSize().aggregate(new StackManipulation.Size(-2, 0));
        }
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class Putter extends StackManipulation.AbstractBase {
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && ArrayAccess.this.equals(ArrayAccess.this);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + ArrayAccess.this.hashCode();
        }

        protected Putter() {
        }

        @Override // net.bytebuddy.implementation.bytecode.StackManipulation
        public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
            methodVisitor.visitInsn(ArrayAccess.this.storeOpcode);
            return ArrayAccess.this.stackSize.toDecreasingSize().aggregate(new StackManipulation.Size(-2, 0));
        }
    }
}
