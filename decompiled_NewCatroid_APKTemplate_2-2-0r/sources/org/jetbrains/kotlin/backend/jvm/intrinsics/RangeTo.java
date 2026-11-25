package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: RangeTo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/RangeTo;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "mapRangeTypeToPrimitiveType", "Lorg/jetbrains/org/objectweb/asm/Type;", "rangeType", "toCallable", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicFunction;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RangeTo extends IntrinsicMethod {
    public static final RangeTo INSTANCE = new RangeTo();

    private RangeTo() {
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public IntrinsicFunction toCallable(IrFunctionAccessExpression expression, JvmMethodSignature signature, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        Type returnType = signature.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        Type typeMapRangeTypeToPrimitiveType = mapRangeTypeToPrimitiveType(returnType);
        List listListOf = CollectionsKt.listOf(typeMapRangeTypeToPrimitiveType);
        List<JvmMethodParameterSignature> valueParameters = signature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<JvmMethodParameterSignature> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (JvmMethodParameterSignature jvmMethodParameterSignature : list) {
            arrayList.add(typeMapRangeTypeToPrimitiveType);
        }
        return new IntrinsicFunction(expression, signature, classCodegen, typeMapRangeTypeToPrimitiveType, CollectionsKt.plus((Collection) listListOf, (Iterable) arrayList)) { // from class: org.jetbrains.kotlin.backend.jvm.intrinsics.RangeTo.toCallable.1
            final /* synthetic */ Type $argType;
            final /* synthetic */ JvmMethodSignature $signature;

            {
                this.$signature = signature;
                this.$argType = typeMapRangeTypeToPrimitiveType;
            }

            @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
            public void genInvokeInstruction(InstructionAdapter v) {
                Intrinsics.checkNotNullParameter(v, "v");
                String internalName = this.$signature.getReturnType().getInternalName();
                Type type = Type.VOID_TYPE;
                Type type2 = this.$argType;
                v.invokespecial(internalName, "<init>", Type.getMethodDescriptor(type, type2, type2), false);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicFunction
            public StackValue invoke(InstructionAdapter v, ExpressionCodegen codegen, BlockInfo data, IrFunctionAccessExpression expression2) {
                Intrinsics.checkNotNullParameter(v, "v");
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(data, "data");
                Intrinsics.checkNotNullParameter(expression2, "expression");
                codegen.markLineNumber(expression2, true);
                v.anew(this.$signature.getReturnType());
                v.dup();
                return super.invoke(v, codegen, data, expression2);
            }
        };
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final Type mapRangeTypeToPrimitiveType(Type rangeType) {
        String internalName = rangeType.getInternalName();
        Intrinsics.checkNotNull(internalName);
        String strSubstringBefore$default = StringsKt.substringBefore$default(StringsKt.substringAfter$default(internalName, "kotlin/ranges/", (String) null, 2, (Object) null), "Range", (String) null, 2, (Object) null);
        switch (strSubstringBefore$default.hashCode()) {
            case 73679:
                if (strSubstringBefore$default.equals("Int")) {
                    Type INT_TYPE = Type.INT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                    return INT_TYPE;
                }
                break;
            case 2086184:
                if (strSubstringBefore$default.equals("Byte")) {
                    Type BYTE_TYPE = Type.BYTE_TYPE;
                    Intrinsics.checkNotNullExpressionValue(BYTE_TYPE, "BYTE_TYPE");
                    return BYTE_TYPE;
                }
                break;
            case 2099062:
                if (strSubstringBefore$default.equals("Char")) {
                    Type CHAR_TYPE = Type.CHAR_TYPE;
                    Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
                    return CHAR_TYPE;
                }
                break;
            case 2374300:
                if (strSubstringBefore$default.equals("Long")) {
                    Type LONG_TYPE = Type.LONG_TYPE;
                    Intrinsics.checkNotNullExpressionValue(LONG_TYPE, "LONG_TYPE");
                    return LONG_TYPE;
                }
                break;
            case 67973692:
                if (strSubstringBefore$default.equals("Float")) {
                    Type FLOAT_TYPE = Type.FLOAT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
                    return FLOAT_TYPE;
                }
                break;
            case 79860828:
                if (strSubstringBefore$default.equals("Short")) {
                    Type SHORT_TYPE = Type.SHORT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(SHORT_TYPE, "SHORT_TYPE");
                    return SHORT_TYPE;
                }
                break;
            case 2052876273:
                if (strSubstringBefore$default.equals("Double")) {
                    Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
                    Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
                    return DOUBLE_TYPE;
                }
                break;
        }
        throw new IllegalStateException("RangeTo intrinsic can only work for primitive types: " + internalName);
    }
}
