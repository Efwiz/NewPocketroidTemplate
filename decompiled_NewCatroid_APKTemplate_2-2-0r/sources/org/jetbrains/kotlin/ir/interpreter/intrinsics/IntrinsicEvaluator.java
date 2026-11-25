package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;

/* JADX INFO: compiled from: IntrinsicEvaluator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicEvaluator;", "", "()V", "fqNameToHandler", "", "", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "unwindInstructions", "", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IntrinsicEvaluator {
    public static final IntrinsicEvaluator INSTANCE = new IntrinsicEvaluator();
    private static final Map<String, IntrinsicBase> fqNameToHandler;

    private IntrinsicEvaluator() {
    }

    static {
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (IntrinsicBase intrinsicBase : CollectionsKt.listOf((Object[]) new IntrinsicBase[]{EmptyArray.INSTANCE, ArrayOf.INSTANCE, ArrayOfNulls.INSTANCE, ArrayConstructor.INSTANCE, EnumValues.INSTANCE, EnumValueOf.INSTANCE, JsPrimitives.INSTANCE, SourceLocation.INSTANCE, AssertIntrinsic.INSTANCE, DataClassArrayToString.INSTANCE, Indent.INSTANCE})) {
            Iterator<T> it = intrinsicBase.getListOfAcceptableFunctions().iterator();
            while (it.hasNext()) {
                mapCreateMapBuilder.put((String) it.next(), intrinsicBase);
            }
        }
        fqNameToHandler = MapsKt.build(mapCreateMapBuilder);
    }

    public final List<Instruction> unwindInstructions(IrFunction irFunction, IrInterpreterEnvironment environment) {
        List<Instruction> listUnwind;
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        String fqName = UtilsKt.getFqName(irFunction);
        IntrinsicBase intrinsicBase = fqNameToHandler.get(fqName);
        if (intrinsicBase != null && (listUnwind = intrinsicBase.unwind(irFunction, environment)) != null) {
            return listUnwind;
        }
        if (EnumIntrinsics.INSTANCE.canHandleFunctionWithName(fqName, irFunction.getOrigin())) {
            return EnumIntrinsics.INSTANCE.unwind(irFunction, environment);
        }
        return null;
    }
}
