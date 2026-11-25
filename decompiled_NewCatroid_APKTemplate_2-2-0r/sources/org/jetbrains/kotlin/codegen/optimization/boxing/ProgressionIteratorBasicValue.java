package org.jetbrains.kotlin.codegen.optimization.boxing;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ProgressionIteratorBasicValue.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\u0013\u0010\u0018\u001a\u00020\u00142\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u001eR\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "Lorg/jetbrains/kotlin/codegen/optimization/common/StrictBasicValue;", "iteratorCallInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "nextMethodName", "", "iteratorType", "Lorg/jetbrains/org/objectweb/asm/Type;", "primitiveElementType", "boxedElementType", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Ljava/lang/String;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Type;)V", "getBoxedElementType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getIteratorCallInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "nextMethodDesc", "getNextMethodDesc", "()Ljava/lang/String;", "getNextMethodName", "<set-?>", "", "tainted", "getTainted", "()Z", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "taint", "", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProgressionIteratorBasicValue extends StrictBasicValue {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Type boxedElementType;
    private final AbstractInsnNode iteratorCallInsn;
    private final String nextMethodName;
    private final Type primitiveElementType;
    private boolean tainted;

    public /* synthetic */ ProgressionIteratorBasicValue(AbstractInsnNode abstractInsnNode, String str, Type type, Type type2, Type type3, DefaultConstructorMarker defaultConstructorMarker) {
        this(abstractInsnNode, str, type, type2, type3);
    }

    public final AbstractInsnNode getIteratorCallInsn() {
        return this.iteratorCallInsn;
    }

    public final String getNextMethodName() {
        return this.nextMethodName;
    }

    public final Type getBoxedElementType() {
        return this.boxedElementType;
    }

    private ProgressionIteratorBasicValue(AbstractInsnNode abstractInsnNode, String str, Type type, Type type2, Type type3) {
        super(type);
        this.iteratorCallInsn = abstractInsnNode;
        this.nextMethodName = str;
        this.primitiveElementType = type2;
        this.boxedElementType = type3;
    }

    public final boolean getTainted() {
        return this.tainted;
    }

    public final void taint() {
        this.tainted = true;
    }

    public final String getNextMethodDesc() {
        return "()" + this.primitiveElementType.getDescriptor();
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other != null && Intrinsics.areEqual(getClass(), other.getClass()) && super.equals(other)) {
            return Intrinsics.areEqual(this.primitiveElementType, ((ProgressionIteratorBasicValue) other).primitiveElementType);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue, org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue
    public int hashCode() {
        return (super.hashCode() * 31) + this.nextMethodName.hashCode();
    }

    /* JADX INFO: compiled from: ProgressionIteratorBasicValue.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ*\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\bH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue$Companion;", "", "()V", "byProgressionClassType", "Lorg/jetbrains/kotlin/codegen/optimization/boxing/ProgressionIteratorBasicValue;", "iteratorCallInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "progressionClassType", "Lorg/jetbrains/org/objectweb/asm/Type;", "progressionIteratorValue", "typeName", "", "valuesPrimitiveType", "valuesBoxedType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        static /* synthetic */ ProgressionIteratorBasicValue progressionIteratorValue$default(Companion companion, AbstractInsnNode abstractInsnNode, String str, Type type, Type type2, int i, Object obj) {
            if ((i & 8) != 0) {
                type2 = AsmUtil.boxType(type);
                Intrinsics.checkNotNullExpressionValue(type2, "boxType(...)");
            }
            return companion.progressionIteratorValue(abstractInsnNode, str, type, type2);
        }

        private final ProgressionIteratorBasicValue progressionIteratorValue(AbstractInsnNode iteratorCallInsn, String typeName, Type valuesPrimitiveType, Type valuesBoxedType) {
            String str = ES6Iterator.NEXT_METHOD + typeName;
            Type objectType = Type.getObjectType("kotlin/collections/" + typeName + "Iterator");
            Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
            return new ProgressionIteratorBasicValue(iteratorCallInsn, str, objectType, valuesPrimitiveType, valuesBoxedType, null);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:11:0x0029, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.CHAR_PROGRESSION_FQN) == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:14:0x0033, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.CHAR_RANGE_FQN) == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0036, code lost:
        
            r3 = org.jetbrains.org.objectweb.asm.Type.CHAR_TYPE;
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, "CHAR_TYPE");
         */
        /* JADX WARN: Code restructure failed: missing block: B:18:0x0050, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.LONG_RANGE_FQN) == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x0053, code lost:
        
            r3 = org.jetbrains.org.objectweb.asm.Type.LONG_TYPE;
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, "LONG_TYPE");
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x006d, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.INT_PROGRESSION_FQN) == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0076, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.INT_RANGE_FQN) == false) goto L28;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x0079, code lost:
        
            r3 = org.jetbrains.org.objectweb.asm.Type.INT_TYPE;
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, "INT_TYPE");
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:?, code lost:
        
            return progressionIteratorValue$default(r7, r8, "Char", r3, null, 8, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:?, code lost:
        
            return progressionIteratorValue$default(r7, r8, "Long", r3, null, 8, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        
            return progressionIteratorValue$default(r7, r8, "Int", r3, null, 8, null);
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x001f, code lost:
        
            if (r9.equals(org.jetbrains.kotlin.types.RangeUtilKt.LONG_PROGRESSION_FQN) == false) goto L28;
         */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue byProgressionClassType(org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode r8, org.jetbrains.org.objectweb.asm.Type r9) {
            /*
                r7 = this;
                java.lang.String r0 = "iteratorCallInsn"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
                java.lang.String r0 = "progressionClassType"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
                java.lang.String r9 = r9.getClassName()
                if (r9 == 0) goto L8d
                int r0 = r9.hashCode()
                switch(r0) {
                    case -1404642343: goto L70;
                    case -1316009897: goto L67;
                    case -421901258: goto L4a;
                    case 1012823964: goto L2d;
                    case 1168491994: goto L23;
                    case 1668431604: goto L19;
                    default: goto L17;
                }
            L17:
                goto L8d
            L19:
                java.lang.String r0 = "kotlin.ranges.LongProgression"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L53
                goto L8d
            L23:
                java.lang.String r0 = "kotlin.ranges.CharProgression"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L36
                goto L8d
            L2d:
                java.lang.String r0 = "kotlin.ranges.CharRange"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L36
                goto L8d
            L36:
                org.jetbrains.org.objectweb.asm.Type r3 = org.jetbrains.org.objectweb.asm.Type.CHAR_TYPE
                java.lang.String r9 = "CHAR_TYPE"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r9)
                r5 = 8
                r6 = 0
                java.lang.String r2 = "Char"
                r4 = 0
                r0 = r7
                r1 = r8
                org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue r8 = progressionIteratorValue$default(r0, r1, r2, r3, r4, r5, r6)
                goto L8e
            L4a:
                java.lang.String r0 = "kotlin.ranges.LongRange"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L53
                goto L8d
            L53:
                org.jetbrains.org.objectweb.asm.Type r3 = org.jetbrains.org.objectweb.asm.Type.LONG_TYPE
                java.lang.String r9 = "LONG_TYPE"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r9)
                r5 = 8
                r6 = 0
                java.lang.String r2 = "Long"
                r4 = 0
                r0 = r7
                r1 = r8
                org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue r8 = progressionIteratorValue$default(r0, r1, r2, r3, r4, r5, r6)
                goto L8e
            L67:
                java.lang.String r0 = "kotlin.ranges.IntProgression"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L79
                goto L8d
            L70:
                java.lang.String r0 = "kotlin.ranges.IntRange"
                boolean r9 = r9.equals(r0)
                if (r9 != 0) goto L79
                goto L8d
            L79:
                org.jetbrains.org.objectweb.asm.Type r3 = org.jetbrains.org.objectweb.asm.Type.INT_TYPE
                java.lang.String r9 = "INT_TYPE"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r9)
                r5 = 8
                r6 = 0
                java.lang.String r2 = "Int"
                r4 = 0
                r0 = r7
                r1 = r8
                org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue r8 = progressionIteratorValue$default(r0, r1, r2, r3, r4, r5, r6)
                goto L8e
            L8d:
                r8 = 0
            L8e:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue.Companion.byProgressionClassType(org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode, org.jetbrains.org.objectweb.asm.Type):org.jetbrains.kotlin.codegen.optimization.boxing.ProgressionIteratorBasicValue");
        }
    }
}
