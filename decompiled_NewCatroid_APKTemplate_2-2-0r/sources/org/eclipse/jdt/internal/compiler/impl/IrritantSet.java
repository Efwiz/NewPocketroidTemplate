package org.eclipse.jdt.internal.compiler.impl;

/* JADX INFO: loaded from: classes14.dex */
public class IrritantSet {
    public static final IrritantSet ALL;
    public static final IrritantSet BOXING;
    public static final IrritantSet CAST;
    public static final IrritantSet COMPILER_DEFAULT_ERRORS;
    public static final IrritantSet COMPILER_DEFAULT_INFOS;
    public static final IrritantSet COMPILER_DEFAULT_WARNINGS;
    public static final IrritantSet DEPRECATION;
    public static final IrritantSet DEP_ANN;
    public static final IrritantSet FALLTHROUGH;
    public static final IrritantSet FINALLY;
    public static final int GROUP0 = 0;
    public static final int GROUP1 = 536870912;
    public static final int GROUP2 = 1073741824;
    public static final int GROUP_MASK = -536870912;
    public static final int GROUP_MAX = 3;
    public static final int GROUP_SHIFT = 29;
    public static final IrritantSet HIDING;
    public static final IrritantSet INCOMPLETE_SWITCH;
    public static final IrritantSet JAVADOC;
    public static final IrritantSet NLS;
    public static final IrritantSet NULL;
    public static final IrritantSet RAW;
    public static final IrritantSet RESOURCE;
    public static final IrritantSet RESTRICTION;
    public static final IrritantSet SERIAL;
    public static final IrritantSet STATIC_ACCESS;
    public static final IrritantSet STATIC_METHOD;
    public static final IrritantSet SUPER;
    public static final IrritantSet SYNCHRONIZED;
    public static final IrritantSet SYNTHETIC_ACCESS;
    public static final IrritantSet UNCHECKED;
    public static final IrritantSet UNQUALIFIED_FIELD_ACCESS;
    public static final IrritantSet UNUSED;
    private int[] bits;

    static {
        IrritantSet irritantSet = new IrritantSet(536870911);
        ALL = irritantSet;
        BOXING = new IrritantSet(CompilerOptions.AutoBoxing);
        CAST = new IrritantSet(67108864);
        DEPRECATION = new IrritantSet(4);
        DEP_ANN = new IrritantSet(CompilerOptions.MissingDeprecatedAnnotation);
        FALLTHROUGH = new IrritantSet(CompilerOptions.FallthroughCase);
        FINALLY = new IrritantSet(16777216);
        IrritantSet irritantSet2 = new IrritantSet(8);
        HIDING = irritantSet2;
        IrritantSet irritantSet3 = new IrritantSet(CompilerOptions.MissingEnumConstantCase);
        INCOMPLETE_SWITCH = irritantSet3;
        NLS = new IrritantSet(256);
        IrritantSet irritantSet4 = new IrritantSet(CompilerOptions.NullReference);
        NULL = irritantSet4;
        RAW = new IrritantSet(CompilerOptions.RawTypeReference);
        IrritantSet irritantSet5 = new IrritantSet(536870944);
        RESTRICTION = irritantSet5;
        SERIAL = new IrritantSet(CompilerOptions.MissingSerialVersion);
        IrritantSet irritantSet6 = new IrritantSet(268435456);
        STATIC_ACCESS = irritantSet6;
        IrritantSet irritantSet7 = new IrritantSet(CompilerOptions.MethodCanBeStatic);
        STATIC_METHOD = irritantSet7;
        SYNTHETIC_ACCESS = new IrritantSet(128);
        SYNCHRONIZED = new IrritantSet(805306368);
        SUPER = new IrritantSet(CompilerOptions.OverridingMethodWithoutSuperInvocation);
        IrritantSet irritantSet8 = new IrritantSet(16);
        UNUSED = irritantSet8;
        IrritantSet irritantSet9 = new IrritantSet(CompilerOptions.UncheckedTypeOperation);
        UNCHECKED = irritantSet9;
        UNQUALIFIED_FIELD_ACCESS = new IrritantSet(4194304);
        IrritantSet irritantSet10 = new IrritantSet(CompilerOptions.UnclosedCloseable);
        RESOURCE = irritantSet10;
        IrritantSet irritantSet11 = new IrritantSet(33554432);
        JAVADOC = irritantSet11;
        IrritantSet irritantSet12 = new IrritantSet(0);
        COMPILER_DEFAULT_ERRORS = irritantSet12;
        IrritantSet irritantSet13 = new IrritantSet(0);
        COMPILER_DEFAULT_WARNINGS = irritantSet13;
        COMPILER_DEFAULT_INFOS = new IrritantSet(0);
        irritantSet13.set(16838239).set(721671934).set(1075458182);
        irritantSet12.set(1073744896);
        irritantSet.setAll();
        irritantSet2.set(131072).set(65536).set(CompilerOptions.TypeHiding);
        irritantSet4.set(CompilerOptions.PotentialNullReference).set(CompilerOptions.RedundantNullCheck).set(CompilerOptions.NullSpecViolation).set(CompilerOptions.NullAnnotationInferenceConflict).set(CompilerOptions.NullUncheckedConversion).set(CompilerOptions.RedundantNullAnnotation).set(CompilerOptions.NonnullParameterAnnotationDropped).set(CompilerOptions.MissingNonNullByDefaultAnnotation).set(CompilerOptions.PessimisticNullAnalysisForFreeTypeVariables).set(CompilerOptions.NonNullTypeVariableFromLegacyInvocation);
        irritantSet5.set(CompilerOptions.DiscouragedReference);
        irritantSet6.set(2048);
        irritantSet8.set(32).set(CompilerOptions.UnusedExceptionParameter).set(32768).set(8388608).set(CompilerOptions.UnusedLabel).set(1024).set(CompilerOptions.UnusedTypeArguments).set(CompilerOptions.RedundantSuperinterface).set(CompilerOptions.DeadCode).set(CompilerOptions.UnusedObjectAllocation).set(CompilerOptions.UnusedTypeParameter).set(CompilerOptions.RedundantSpecificationOfTypeArguments);
        irritantSet7.set(CompilerOptions.MethodCanBePotentiallyStatic);
        irritantSet10.set(CompilerOptions.PotentiallyUnclosedCloseable).set(CompilerOptions.ExplicitlyClosedAutoCloseable);
        irritantSet3.set(CompilerOptions.MissingDefaultCase);
        String property = System.getProperty("suppressRawWhenUnchecked");
        if (property != null && "true".equalsIgnoreCase(property)) {
            irritantSet9.set(CompilerOptions.RawTypeReference);
        }
        irritantSet11.set(1048576).set(2097152);
    }

    public IrritantSet(int i) {
        this.bits = new int[3];
        initialize(i);
    }

    public IrritantSet(IrritantSet irritantSet) {
        this.bits = new int[3];
        initialize(irritantSet);
    }

    public boolean areAllSet() {
        for (int i = 0; i < 3; i++) {
            if (this.bits[i] != 536870911) {
                return false;
            }
        }
        return true;
    }

    public IrritantSet clear(int i) {
        int i2 = ((-536870912) & i) >> 29;
        int[] iArr = this.bits;
        iArr[i2] = (~i) & iArr[i2];
        return this;
    }

    public IrritantSet clearAll() {
        for (int i = 0; i < 3; i++) {
            this.bits[i] = 0;
        }
        return this;
    }

    public void initialize(int i) {
        if (i == 0) {
            return;
        }
        this.bits[((-536870912) & i) >> 29] = i & 536870911;
    }

    public void initialize(IrritantSet irritantSet) {
        if (irritantSet == null) {
            return;
        }
        int[] iArr = irritantSet.bits;
        int[] iArr2 = new int[3];
        this.bits = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, 3);
    }

    public boolean isAnySet(IrritantSet irritantSet) {
        if (irritantSet == null) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if ((this.bits[i] & irritantSet.bits[i]) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSameIrritants(IrritantSet irritantSet) {
        if (irritantSet == null) {
            return false;
        }
        for (int i = 0; i < 3; i++) {
            if (this.bits[i] != irritantSet.bits[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isSet(int i) {
        return (i & this.bits[((-536870912) & i) >> 29]) != 0;
    }

    public IrritantSet set(int i) {
        int i2 = ((-536870912) & i) >> 29;
        int[] iArr = this.bits;
        iArr[i2] = (i & 536870911) | iArr[i2];
        return this;
    }

    public IrritantSet set(IrritantSet irritantSet) {
        if (irritantSet == null) {
            return this;
        }
        boolean z = true;
        for (int i = 0; i < 3; i++) {
            int i2 = irritantSet.bits[i] & 536870911;
            int[] iArr = this.bits;
            int i3 = iArr[i];
            if ((i3 & i2) != i2) {
                iArr[i] = i3 | i2;
                z = false;
            }
        }
        if (z) {
            return null;
        }
        return this;
    }

    public IrritantSet setAll() {
        for (int i = 0; i < 3; i++) {
            int[] iArr = this.bits;
            iArr[i] = iArr[i] | 536870911;
        }
        return this;
    }
}
