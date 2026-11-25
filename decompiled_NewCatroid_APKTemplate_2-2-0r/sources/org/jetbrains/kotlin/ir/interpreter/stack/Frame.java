package org.jetbrains.kotlin.ir.interpreter.stack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.interpreter.Instruction;
import org.jetbrains.kotlin.ir.interpreter.exceptions.InterpreterError;
import org.jetbrains.kotlin.ir.interpreter.state.State;
import org.jetbrains.kotlin.ir.interpreter.state.StateWithClosure;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Frame.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0000\u0018\u0000 ;2\u00020\u0001:\u0001;B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0002\u001a\u00020\u0003J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u0000J\u000e\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dJ\b\u0010\u001e\u001a\u0004\u0018\u00010\fJ\u001d\u0010\u001f\u001a\u00020\u00152\u0012\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00150!H\u0082\bJ\u001a\u0010\"\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020%0$0#H\u0002J\u0006\u0010&\u001a\u00020'J\b\u0010(\u001a\u00020'H\u0002J\u0006\u0010)\u001a\u00020\u0017J\u0006\u0010*\u001a\u00020\u0017J\u000e\u0010+\u001a\u00020,2\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010-\u001a\u0004\u0018\u00010,J\u0006\u0010.\u001a\u00020\fJ\u0006\u0010/\u001a\u00020,J\u000e\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u00020\fJ\u000e\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u00020,J\u0006\u00104\u001a\u00020\u0015J\u0006\u00105\u001a\u00020\u0015J\u0016\u00106\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00107\u001a\u00020,J\u0016\u00108\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00109\u001a\u00020%J\u0018\u00108\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\b\u00103\u001a\u0004\u0018\u00010,J\b\u0010:\u001a\u00020'H\u0016R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/stack/Frame;", "", "subFrameOwner", "Lorg/jetbrains/kotlin/ir/IrElement;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "currentFrame", "Lorg/jetbrains/kotlin/ir/interpreter/stack/SubFrame;", "getCurrentFrame", "()Lorg/jetbrains/kotlin/ir/interpreter/stack/SubFrame;", "currentInstruction", "Lorg/jetbrains/kotlin/ir/interpreter/Instruction;", "currentSubFrameOwner", "getCurrentSubFrameOwner", "()Lorg/jetbrains/kotlin/ir/IrElement;", "innerStack", "Lkotlin/collections/ArrayDeque;", "getIrFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "addSubFrame", "", "containsStateInMemory", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "copyMemoryInto", "newFrame", "closure", "Lorg/jetbrains/kotlin/ir/interpreter/state/StateWithClosure;", "dropInstructions", "forEachSubFrame", "block", "Lkotlin/Function1;", "getAll", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/interpreter/stack/Variable;", "getFileAndPositionInfo", "", "getLineNumberForCurrentInstruction", "hasNoInstructions", "hasNoSubFrames", "loadState", "Lorg/jetbrains/kotlin/ir/interpreter/state/State;", "peekState", "popInstruction", "popState", "pushInstruction", "instruction", "pushState", "state", "removeSubFrame", "removeSubFrameWithoutDataPropagation", "rewriteState", "newState", "storeState", "variable", "toString", "Companion", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Frame {
    public static final String NOT_DEFINED = "Not defined";
    private Instruction currentInstruction;
    private final ArrayDeque<SubFrame> innerStack;
    private final IrFile irFile;

    public Frame(IrElement subFrameOwner, IrFile irFile) {
        Intrinsics.checkNotNullParameter(subFrameOwner, "subFrameOwner");
        this.irFile = irFile;
        ArrayDeque<SubFrame> arrayDeque = new ArrayDeque<>();
        arrayDeque.add(new SubFrame(subFrameOwner));
        this.innerStack = arrayDeque;
    }

    public /* synthetic */ Frame(IrElement irElement, IrFile irFile, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irElement, (i & 2) != 0 ? null : irFile);
    }

    public final IrFile getIrFile() {
        return this.irFile;
    }

    private final SubFrame getCurrentFrame() {
        return this.innerStack.last();
    }

    public final IrElement getCurrentSubFrameOwner() {
        return getCurrentFrame().getOwner();
    }

    public final void addSubFrame(IrElement subFrameOwner) {
        Intrinsics.checkNotNullParameter(subFrameOwner, "subFrameOwner");
        this.innerStack.add(new SubFrame(subFrameOwner));
    }

    public final void removeSubFrame() {
        State statePeekState = getCurrentFrame().peekState();
        removeSubFrameWithoutDataPropagation();
        if (hasNoSubFrames() || statePeekState == null) {
            return;
        }
        getCurrentFrame().pushState(statePeekState);
    }

    public final void removeSubFrameWithoutDataPropagation() {
        this.innerStack.removeLast();
    }

    public final boolean hasNoSubFrames() {
        return this.innerStack.isEmpty();
    }

    public final boolean hasNoInstructions() {
        if (hasNoSubFrames()) {
            return true;
        }
        return this.innerStack.size() == 1 && this.innerStack.first().isEmpty();
    }

    public final void pushInstruction(Instruction instruction) {
        Intrinsics.checkNotNullParameter(instruction, "instruction");
        getCurrentFrame().pushInstruction(instruction);
    }

    public final Instruction popInstruction() {
        Instruction instructionPopInstruction = getCurrentFrame().popInstruction();
        this.currentInstruction = instructionPopInstruction;
        return instructionPopInstruction;
    }

    public final Instruction dropInstructions() {
        return getCurrentFrame().dropInstructions();
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

    public final void storeState(IrSymbol symbol, Variable variable) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(variable, "variable");
        getCurrentFrame().storeState(symbol, variable);
    }

    public final void copyMemoryInto(Frame newFrame) {
        Intrinsics.checkNotNullParameter(newFrame, "newFrame");
        Iterator<T> it = getAll().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            IrSymbol irSymbol = (IrSymbol) pair.component1();
            Variable variable = (Variable) pair.component2();
            if (!newFrame.containsStateInMemory(irSymbol)) {
                newFrame.storeState(irSymbol, variable);
            }
        }
    }

    public final void copyMemoryInto(StateWithClosure closure) {
        Intrinsics.checkNotNullParameter(closure, "closure");
        for (Pair pair : CollectionsKt.reversed(getAll())) {
            closure.getUpValues().put((IrSymbol) pair.component1(), (Variable) pair.component2());
        }
    }

    private final List<Pair<IrSymbol, Variable>> getAll() {
        ArrayDeque<SubFrame> arrayDeque = this.innerStack;
        ArrayList arrayList = new ArrayList();
        Iterator<SubFrame> it = arrayDeque.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getAll());
        }
        return arrayList;
    }

    private final String getLineNumberForCurrentInstruction() {
        if (this.irFile == null) {
            return "";
        }
        Instruction instruction = this.currentInstruction;
        IrElement element = instruction != null ? instruction.getElement() : null;
        return ((element instanceof IrExpression) || ((element instanceof IrDeclaration) && Intrinsics.areEqual(((IrDeclaration) element).getOrigin(), IrDeclarationOrigin.DEFINED.INSTANCE))) ? ":" + (this.irFile.getFileEntry().getLineNumber(element.getStartOffset()) + 1) : "";
    }

    public final String getFileAndPositionInfo() {
        if (this.irFile == null) {
            return NOT_DEFINED;
        }
        return IrDeclarationsKt.getName(this.irFile) + getLineNumberForCurrentInstruction();
    }

    public String toString() {
        SubFrame next;
        Object fqNameWhenAvailable;
        IrFile irFile = this.irFile;
        if (irFile == null) {
            return NOT_DEFINED;
        }
        String strCapitalizeAsciiOnly = CapitalizeDecapitalizeKt.capitalizeAsciiOnly(StringsKt.replace$default(IrDeclarationsKt.getName(irFile), KotlinFileType.DOT_DEFAULT_EXTENSION, "Kt", false, 4, (Object) null));
        Iterator<SubFrame> it = this.innerStack.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next.getOwner() instanceof IrFunction));
        SubFrame subFrame = next;
        IrElement owner = subFrame != null ? subFrame.getOwner() : null;
        IrFunction irFunction = owner instanceof IrFunction ? (IrFunction) owner : null;
        String lineNumberForCurrentInstruction = getLineNumberForCurrentInstruction();
        StringBuilder sbAppend = new StringBuilder("at ").append(strCapitalizeAsciiOnly).append('.');
        if (irFunction == null || (fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irFunction)) == null) {
            fqNameWhenAvailable = MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME;
        }
        return sbAppend.append(fqNameWhenAvailable).append('(').append(IrDeclarationsKt.getName(this.irFile)).append(lineNumberForCurrentInstruction).append(')').toString();
    }

    public final State loadState(IrSymbol symbol) throws InterpreterError {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Iterator<Integer> it = RangesKt.downTo(CollectionsKt.getLastIndex(this.innerStack), 0).iterator();
        while (it.hasNext()) {
            State stateLoadState = this.innerStack.get(((IntIterator) it).nextInt()).loadState(symbol);
            if (stateLoadState != null) {
                return stateLoadState;
            }
        }
        throw new InterpreterError(symbol + " not found");
    }

    public final void rewriteState(IrSymbol symbol, State newState) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(newState, "newState");
        Iterator<Integer> it = RangesKt.downTo(CollectionsKt.getLastIndex(this.innerStack), 0).iterator();
        while (it.hasNext()) {
            SubFrame subFrame = this.innerStack.get(((IntIterator) it).nextInt());
            if (subFrame.containsStateInMemory(symbol)) {
                subFrame.rewriteState(symbol, newState);
                return;
            }
        }
    }

    public final boolean containsStateInMemory(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Iterator<Integer> it = RangesKt.downTo(CollectionsKt.getLastIndex(this.innerStack), 0).iterator();
        while (it.hasNext()) {
            if (this.innerStack.get(((IntIterator) it).nextInt()).containsStateInMemory(symbol)) {
                return true;
            }
        }
        return false;
    }
}
