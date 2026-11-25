package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public class BaseTypeBinding extends TypeBinding {
    public static final int[] CONVERSIONS = initializeConversions();
    public static final int IDENTITY = 1;
    public static final int MAX_CONVERSIONS = 256;
    public static final int NARROWING = 4;
    public static final int WIDENING = 2;
    private char[] constantPoolName;
    public char[] simpleName;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 132;
    }

    public static final int[] initializeConversions() {
        int[] iArr = new int[256];
        iArr[85] = 1;
        iArr[51] = 1;
        iArr[67] = 2;
        iArr[35] = 4;
        iArr[163] = 2;
        iArr[115] = 2;
        iArr[147] = 2;
        iArr[131] = 2;
        iArr[52] = 4;
        iArr[68] = 1;
        iArr[36] = 4;
        iArr[164] = 2;
        iArr[116] = 2;
        iArr[148] = 2;
        iArr[132] = 2;
        iArr[50] = 4;
        iArr[66] = 4;
        iArr[34] = 1;
        iArr[162] = 2;
        iArr[114] = 2;
        iArr[146] = 2;
        iArr[130] = 2;
        iArr[58] = 4;
        iArr[74] = 4;
        iArr[42] = 4;
        iArr[170] = 1;
        iArr[122] = 2;
        iArr[154] = 2;
        iArr[138] = 2;
        iArr[55] = 4;
        iArr[71] = 4;
        iArr[39] = 4;
        iArr[167] = 4;
        iArr[119] = 1;
        iArr[151] = 2;
        iArr[135] = 2;
        iArr[57] = 4;
        iArr[73] = 4;
        iArr[41] = 4;
        iArr[169] = 4;
        iArr[121] = 4;
        iArr[153] = 1;
        iArr[137] = 2;
        iArr[56] = 4;
        iArr[72] = 4;
        iArr[40] = 4;
        iArr[168] = 4;
        iArr[120] = 4;
        iArr[152] = 4;
        iArr[136] = 1;
        return iArr;
    }

    public static final boolean isNarrowing(int i, int i2) {
        int i3 = i2 + (i << 4);
        return i3 >= 0 && i3 < 256 && (CONVERSIONS[i3] & 5) != 0;
    }

    public static final boolean isWidening(int i, int i2) {
        int i3 = i2 + (i << 4);
        return i3 >= 0 && i3 < 256 && (CONVERSIONS[i3] & 3) != 0;
    }

    BaseTypeBinding(int i, char[] cArr, char[] cArr2) {
        this.tagBits |= 2;
        this.f626id = i;
        this.simpleName = cArr;
        this.constantPoolName = cArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        return constantPoolName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        return this.constantPoolName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new BaseTypeBinding(this.f626id, this.simpleName, this.constantPoolName);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public final boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        int i = this.f626id + (typeBinding.f626id << 4);
        if (i < 0 || i >= 256 || (CONVERSIONS[i] & 3) == 0) {
            return this == TypeBinding.NULL && !typeBinding.isBaseType();
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        super.setTypeAnnotations(annotationBindingArr, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        if (!hasTypeAnnotations()) {
            return this;
        }
        switch (this.f626id) {
            case 2:
                return TypeBinding.CHAR;
            case 3:
                return TypeBinding.BYTE;
            case 4:
                return TypeBinding.SHORT;
            case 5:
                return TypeBinding.BOOLEAN;
            case 6:
            default:
                throw new IllegalStateException();
            case 7:
                return TypeBinding.LONG;
            case 8:
                return TypeBinding.DOUBLE;
            case 9:
                return TypeBinding.FLOAT;
            case 10:
                return TypeBinding.INT;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isUncheckedException(boolean z) {
        return this == TypeBinding.NULL;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        return this.simpleName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return this.simpleName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        return this.simpleName;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        return this.simpleName;
    }

    public String toString() {
        return hasTypeAnnotations() ? annotatedDebugName() : new String(readableName());
    }
}
