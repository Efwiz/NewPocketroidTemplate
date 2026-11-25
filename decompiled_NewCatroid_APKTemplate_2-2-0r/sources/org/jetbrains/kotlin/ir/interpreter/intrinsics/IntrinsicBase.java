package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.interpreter.CustomInstruction;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment;
import org.jetbrains.kotlin.ir.interpreter.exceptions.ExceptionUtilsKt;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterException;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH&J\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u0082\u0001\f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "", "()V", "customEvaluateInstruction", "Lorg/jetbrains/kotlin/ir/interpreter/CustomInstruction;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "evaluate", "", "getListOfAcceptableFunctions", "", "", "unwind", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/ArrayConstructor;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/ArrayOf;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/ArrayOfNulls;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/AssertIntrinsic;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/DataClassArrayToString;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EmptyArray;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EnumIntrinsics;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EnumValueOf;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/EnumValues;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/Indent;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/JsPrimitives;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/SourceLocation;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IntrinsicBase {
    public /* synthetic */ IntrinsicBase(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void evaluate(IrFunction irFunction, IrInterpreterEnvironment environment);

    public abstract List<String> getListOfAcceptableFunctions();

    private IntrinsicBase() {
    }

    public List<Instruction> unwind(IrFunction irFunction, IrInterpreterEnvironment environment) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(environment, "environment");
        return CollectionsKt.listOf(customEvaluateInstruction(irFunction, environment));
    }

    private final CustomInstruction customEvaluateInstruction(final IrFunction irFunction, final IrInterpreterEnvironment environment) {
        return new CustomInstruction(new Function0<Unit>() { // from class: org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase.customEvaluateInstruction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws InterpreterException {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws InterpreterException {
                IrInterpreterEnvironment irInterpreterEnvironment = environment;
                try {
                    this.evaluate(irFunction, irInterpreterEnvironment);
                    irInterpreterEnvironment.getCallStack().dropFrameAndCopyResult();
                } catch (InterpreterException e) {
                    throw e;
                } catch (Throwable th) {
                    ExceptionUtilsKt.handleUserException(th, irInterpreterEnvironment);
                }
            }
        });
    }
}
