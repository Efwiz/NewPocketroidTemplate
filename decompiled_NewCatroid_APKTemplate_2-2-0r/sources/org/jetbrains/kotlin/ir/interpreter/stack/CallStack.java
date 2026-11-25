package org.jetbrains.kotlin.ir.interpreter.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.InstructionKt;
import org.jetbrains.kotlin.ir.interpreter.InstructionsUnfolderKt;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: CallStack.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\u0012J\u0006\u0010\u0016\u001a\u00020\u0012J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u001aJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u001cJ\u0006\u0010\u001d\u001a\u00020\u000eJ\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"J\u001a\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\b2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010&J\u000e\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020'J\u000e\u0010(\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\bJ\b\u0010)\u001a\u0004\u0018\u00010\u001fJ\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020\u001fJ\u000e\u0010-\u001a\u00020\u00122\u0006\u0010.\u001a\u00020+J\u000e\u0010/\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u001fJ\u000e\u00100\u001a\u00020\u00122\u0006\u00101\u001a\u000202J\u0016\u00103\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u00104\u001a\u00020\u001fJ\u0018\u00105\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u00106\u001a\u000207H\u0002J\u0018\u00105\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010!\u001a\u0004\u0018\u00010\u001fJ\u000e\u00108\u001a\u00020\u00122\u0006\u0010!\u001a\u00020\"J\u000e\u00109\u001a\u00020\u00122\u0006\u0010:\u001a\u00020;R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/stack/CallStack;", "", "()V", "currentFrame", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Frame;", "getCurrentFrame", "()Lorg/jetbrains/kotlin/ir/interpreter/stack/Frame;", "currentFrameOwner", "Lorg/jetbrains/kotlin/ir/IrElement;", "getCurrentFrameOwner$ir_interpreter", "()Lorg/jetbrains/kotlin/ir/IrElement;", "frames", "Lkotlin/collections/ArrayDeque;", "containsStateInMemory", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "copyUpValuesFromPreviousFrame", "", "dropFrame", "dropFrameAndCopyResult", "dropFramesUntilTryCatch", "dropSubFrame", "getFileAndPositionInfo", "", "getStackCount", "", "getStackTrace", "", "hasNoInstructions", "loadState", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "loadUpValues", "state", "Lorg/jetbrains/kotlin/ir/interpreter/state/StateWithClosure;", "newFrame", "frameOwner", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "newSubFrame", "peekState", "popInstruction", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "popState", "pushInstruction", "instruction", "pushState", "returnFromFrameWithResult", "irReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "rewriteState", "newState", "storeState", "variable", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "storeUpValues", "unrollInstructionsForBreakContinue", "breakOrContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrBreakContinue;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallStack {
    private final ArrayDeque<Frame> frames = new ArrayDeque<>();

    private final Frame getCurrentFrame() {
        return this.frames.last();
    }

    public final IrElement getCurrentFrameOwner$ir_interpreter() {
        return getCurrentFrame().getCurrentSubFrameOwner();
    }

    public static /* synthetic */ void newFrame$default(CallStack callStack, IrElement irElement, IrFile irFile, int i, Object obj) {
        if ((i & 2) != 0) {
            irFile = null;
        }
        callStack.newFrame(irElement, irFile);
    }

    public final void newFrame(IrElement frameOwner, IrFile irFile) {
        Intrinsics.checkNotNullParameter(frameOwner, "frameOwner");
        this.frames.add(new Frame(frameOwner, irFile));
    }

    public final void newFrame(IrFunction frameOwner) {
        Intrinsics.checkNotNullParameter(frameOwner, "frameOwner");
        this.frames.add(new Frame(frameOwner, IrUtilsKt.getFileOrNull(frameOwner)));
    }

    public final void newSubFrame(IrElement frameOwner) {
        Intrinsics.checkNotNullParameter(frameOwner, "frameOwner");
        getCurrentFrame().addSubFrame(frameOwner);
    }

    public final void dropFrame() {
        this.frames.removeLast();
    }

    public final void dropFrameAndCopyResult() {
        State statePeekState = peekState();
        if (statePeekState == null) {
            dropFrame();
            return;
        }
        popState();
        dropFrame();
        pushState(statePeekState);
    }

    public final void dropSubFrame() {
        getCurrentFrame().removeSubFrame();
    }

    public final void returnFromFrameWithResult(IrReturn irReturn) {
        Intrinsics.checkNotNullParameter(irReturn, "irReturn");
        State statePopState = popState();
        IrReturnTarget owner = irReturn.getReturnTargetSymbol().getOwner();
        IrElement currentFrameOwner$ir_interpreter = getCurrentFrameOwner$ir_interpreter();
        while (!Intrinsics.areEqual(currentFrameOwner$ir_interpreter, owner)) {
            if (currentFrameOwner$ir_interpreter instanceof IrTry) {
                dropSubFrame();
                pushState(statePopState);
                InstructionKt.pushSimpleInstruction(this, irReturn);
                IrExpression finallyExpression = ((IrTry) currentFrameOwner$ir_interpreter).getFinallyExpression();
                if (finallyExpression != null) {
                    InstructionsUnfolderKt.handleAndDropResult(finallyExpression, this);
                    return;
                }
                return;
            }
            if (currentFrameOwner$ir_interpreter instanceof IrCatch) {
                Instruction instructionDropInstructions = getCurrentFrame().dropInstructions();
                Intrinsics.checkNotNull(instructionDropInstructions);
                IrElement element = instructionDropInstructions.getElement();
                Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrTry");
                dropSubFrame();
                pushState(statePopState);
                InstructionKt.pushSimpleInstruction(this, irReturn);
                IrExpression finallyExpression2 = ((IrTry) element).getFinallyExpression();
                if (finallyExpression2 != null) {
                    InstructionsUnfolderKt.handleAndDropResult(finallyExpression2, this);
                    return;
                }
                return;
            }
            dropSubFrame();
            if (getCurrentFrame().hasNoSubFrames() && !Intrinsics.areEqual(currentFrameOwner$ir_interpreter, owner)) {
                dropFrame();
            }
            currentFrameOwner$ir_interpreter = getCurrentFrameOwner$ir_interpreter();
        }
        getCurrentFrame().dropInstructions();
        InstructionKt.pushSimpleInstruction(this, owner);
        if (owner instanceof IrConstructor) {
            return;
        }
        pushState(statePopState);
    }

    public final void unrollInstructionsForBreakContinue(IrBreakContinue breakOrContinue) {
        Intrinsics.checkNotNullParameter(breakOrContinue, "breakOrContinue");
        IrElement currentFrameOwner$ir_interpreter = getCurrentFrameOwner$ir_interpreter();
        while (!Intrinsics.areEqual(currentFrameOwner$ir_interpreter, breakOrContinue.getLoop())) {
            if (currentFrameOwner$ir_interpreter instanceof IrTry) {
                getCurrentFrame().removeSubFrameWithoutDataPropagation();
                InstructionKt.pushCompoundInstruction(this, breakOrContinue);
                newSubFrame(currentFrameOwner$ir_interpreter);
                InstructionKt.pushSimpleInstruction(this, currentFrameOwner$ir_interpreter);
                return;
            }
            if (currentFrameOwner$ir_interpreter instanceof IrCatch) {
                Instruction instructionDropInstructions = getCurrentFrame().dropInstructions();
                Intrinsics.checkNotNull(instructionDropInstructions);
                getCurrentFrame().removeSubFrameWithoutDataPropagation();
                InstructionKt.pushCompoundInstruction(this, breakOrContinue);
                IrElement element = instructionDropInstructions.getElement();
                Intrinsics.checkNotNull(element);
                newSubFrame(element);
                pushInstruction(instructionDropInstructions);
                return;
            }
            getCurrentFrame().removeSubFrameWithoutDataPropagation();
            currentFrameOwner$ir_interpreter = getCurrentFrameOwner$ir_interpreter();
        }
        if (breakOrContinue instanceof IrBreak) {
            getCurrentFrame().removeSubFrameWithoutDataPropagation();
        } else if (breakOrContinue.getLoop() instanceof IrDoWhileLoop) {
            InstructionKt.pushSimpleInstruction(this, breakOrContinue.getLoop());
            InstructionKt.pushCompoundInstruction(this, breakOrContinue.getLoop().getCondition());
        } else {
            InstructionKt.pushCompoundInstruction(this, breakOrContinue.getLoop());
        }
    }

    public final void dropFramesUntilTryCatch() {
        State statePopState = popState();
        IrElement currentFrameOwner$ir_interpreter = getCurrentFrameOwner$ir_interpreter();
        while (!this.frames.isEmpty()) {
            Frame currentFrame = getCurrentFrame();
            while (!currentFrame.hasNoSubFrames()) {
                currentFrameOwner$ir_interpreter = currentFrame.getCurrentSubFrameOwner();
                if (currentFrameOwner$ir_interpreter instanceof IrTry) {
                    dropSubFrame();
                    newSubFrame(currentFrameOwner$ir_interpreter);
                    InstructionKt.pushSimpleInstruction(this, currentFrameOwner$ir_interpreter);
                    Iterator it = CollectionsKt.reversed(((IrTry) currentFrameOwner$ir_interpreter).getCatches()).iterator();
                    while (it.hasNext()) {
                        InstructionKt.pushCompoundInstruction(this, (IrCatch) it.next());
                    }
                    pushState(statePopState);
                    return;
                }
                if (currentFrameOwner$ir_interpreter instanceof IrCatch) {
                    Instruction instructionDropInstructions = currentFrame.dropInstructions();
                    Intrinsics.checkNotNull(instructionDropInstructions);
                    pushInstruction(instructionDropInstructions);
                    pushState(statePopState);
                    return;
                }
                currentFrame.removeSubFrameWithoutDataPropagation();
            }
            dropFrame();
        }
        if (this.frames.size() == 0) {
            newFrame$default(this, currentFrameOwner$ir_interpreter, null, 2, null);
        }
        pushState(statePopState);
    }

    public final boolean hasNoInstructions() {
        if (this.frames.isEmpty()) {
            return true;
        }
        return this.frames.size() == 1 && this.frames.first().hasNoInstructions();
    }

    public final void pushInstruction(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        getCurrentFrame().pushInstruction(instruction);
    }

    public final Instruction popInstruction() {
        return getCurrentFrame().popInstruction();
    }

    public final void pushState(State state) {
        Intrinsics.checkNotNullParameter(state, "state");
        getCurrentFrame().pushState(state);
    }

    public final State popState() {
        return getCurrentFrame().popState();
    }

    public final State peekState() {
        return getCurrentFrame().peekState();
    }

    public final void storeState(IrSymbol symbol, State state) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        getCurrentFrame().storeState(symbol, state);
    }

    private final void storeState(IrSymbol symbol, Variable variable) {
        getCurrentFrame().storeState(symbol, variable);
    }

    public final boolean containsStateInMemory(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return getCurrentFrame().containsStateInMemory(symbol);
    }

    public final State loadState(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return getCurrentFrame().loadState(symbol);
    }

    public final void rewriteState(IrSymbol symbol, State newState) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(newState, "newState");
        getCurrentFrame().rewriteState(symbol, newState);
    }

    public final void storeUpValues(StateWithClosure state) {
        Intrinsics.checkNotNullParameter(state, "state");
        getCurrentFrame().copyMemoryInto(state);
    }

    public final void loadUpValues(StateWithClosure state) {
        Intrinsics.checkNotNullParameter(state, "state");
        for (Map.Entry<IrSymbol, Variable> entry : state.getUpValues().entrySet()) {
            storeState(entry.getKey(), entry.getValue());
        }
    }

    public final void copyUpValuesFromPreviousFrame() {
        ArrayDeque<Frame> arrayDeque = this.frames;
        arrayDeque.get(arrayDeque.size() - 2).copyMemoryInto(getCurrentFrame());
    }

    public final List<String> getStackTrace() {
        ArrayDeque<Frame> arrayDeque = this.frames;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayDeque, 10));
        Iterator<Frame> it = arrayDeque.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toString());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!Intrinsics.areEqual((String) obj, Frame.NOT_DEFINED)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public final String getFileAndPositionInfo() {
        ArrayDeque<Frame> arrayDeque = this.frames;
        return arrayDeque.get(arrayDeque.size() - 2).getFileAndPositionInfo();
    }

    public final int getStackCount() {
        return this.frames.size();
    }
}
