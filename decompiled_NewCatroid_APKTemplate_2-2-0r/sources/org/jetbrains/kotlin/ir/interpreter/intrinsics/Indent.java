package org.jetbrains.kotlin.ir.interpreter.intrinsics;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: IntrinsicImplementations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/Indent;", "Lorg/jetbrains/kotlin/ir/interpreter/intrinsics/IntrinsicBase;", "()V", "evaluate", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "environment", "Lorg/jetbrains/kotlin/ir/interpreter/IrInterpreterEnvironment;", "getListOfAcceptableFunctions", "", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Indent extends IntrinsicBase {
    public static final Indent INSTANCE = new Indent();

    private Indent() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    public List<String> getListOfAcceptableFunctions() {
        return CollectionsKt.listOf((Object[]) new String[]{"kotlin.text.StringsKt.trimIndent", "kotlin.text.trimIndent", "kotlin.text.StringsKt.trimMargin", "kotlin.text.trimMargin", "kotlin.text.StringsKt.trimMargin$default", "kotlin.text.trimMargin$default"});
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0047, code lost:
    
        if (r1.equals("kotlin.text.trimMargin$default") != false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0050, code lost:
    
        if (r1.equals("kotlin.text.StringsKt.trimMargin") != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0052, code lost:
    
        r0 = kotlin.text.StringsKt.trimMargin(r0, org.jetbrains.kotlin.ir.interpreter.state.StateKt.asString(r5.getCallStack().loadState(((org.jetbrains.kotlin.ir.declarations.IrValueParameter) kotlin.collections.CollectionsKt.single((java.util.List) r4.getValueParameters())).getSymbol())));
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0079, code lost:
    
        if (r1.equals("kotlin.text.StringsKt.trimIndent") != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x007b, code lost:
    
        r0 = kotlin.text.StringsKt.trimIndent(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0086, code lost:
    
        if (r1.equals("kotlin.text.StringsKt.trimMargin$default") != false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0088, code lost:
    
        r0 = kotlin.text.StringsKt.trimMargin$default(r0, null, 1, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x008e, code lost:
    
        r5.getCallStack().pushState(r5.convertToState$ir_interpreter(r0, r4.getReturnType()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x009d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0035, code lost:
    
        if (r1.equals("kotlin.text.trimMargin") != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x003e, code lost:
    
        if (r1.equals("kotlin.text.trimIndent") != false) goto L19;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // org.jetbrains.kotlin.ir.interpreter.intrinsics.IntrinsicBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void evaluate(org.jetbrains.kotlin.ir.declarations.IrFunction r4, org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment r5) {
        /*
            r3 = this;
            java.lang.String r0 = "irFunction"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.lang.String r0 = "environment"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
            org.jetbrains.kotlin.ir.interpreter.stack.CallStack r0 = r5.getCallStack()
            org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol r1 = org.jetbrains.kotlin.ir.interpreter.UtilsKt.getExtensionReceiver(r4)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            org.jetbrains.kotlin.ir.symbols.IrSymbol r1 = (org.jetbrains.kotlin.ir.symbols.IrSymbol) r1
            org.jetbrains.kotlin.ir.interpreter.state.State r0 = r0.loadState(r1)
            java.lang.String r0 = org.jetbrains.kotlin.ir.interpreter.state.StateKt.asString(r0)
            r1 = r4
            org.jetbrains.kotlin.ir.IrElement r1 = (org.jetbrains.kotlin.ir.IrElement) r1
            java.lang.String r1 = org.jetbrains.kotlin.ir.interpreter.UtilsKt.getFqName(r1)
            int r2 = r1.hashCode()
            switch(r2) {
                case -1547797276: goto L80;
                case -1269761723: goto L73;
                case -1166832057: goto L4a;
                case 562521729: goto L41;
                case 1968689122: goto L38;
                case 2071618788: goto L2f;
                default: goto L2d;
            }
        L2d:
            goto L9e
        L2f:
            java.lang.String r2 = "kotlin.text.trimMargin"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
            goto L52
        L38:
            java.lang.String r2 = "kotlin.text.trimIndent"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
            goto L7b
        L41:
            java.lang.String r2 = "kotlin.text.trimMargin$default"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
            goto L88
        L4a:
            java.lang.String r2 = "kotlin.text.StringsKt.trimMargin"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
        L52:
            org.jetbrains.kotlin.ir.interpreter.stack.CallStack r1 = r5.getCallStack()
            java.util.List r2 = r4.getValueParameters()
            java.lang.Object r2 = kotlin.collections.CollectionsKt.single(r2)
            org.jetbrains.kotlin.ir.declarations.IrValueParameter r2 = (org.jetbrains.kotlin.ir.declarations.IrValueParameter) r2
            org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol r2 = r2.getSymbol()
            org.jetbrains.kotlin.ir.symbols.IrSymbol r2 = (org.jetbrains.kotlin.ir.symbols.IrSymbol) r2
            org.jetbrains.kotlin.ir.interpreter.state.State r1 = r1.loadState(r2)
            java.lang.String r1 = org.jetbrains.kotlin.ir.interpreter.state.StateKt.asString(r1)
            java.lang.String r0 = kotlin.text.StringsKt.trimMargin(r0, r1)
            goto L8e
        L73:
            java.lang.String r2 = "kotlin.text.StringsKt.trimIndent"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
        L7b:
            java.lang.String r0 = kotlin.text.StringsKt.trimIndent(r0)
            goto L8e
        L80:
            java.lang.String r2 = "kotlin.text.StringsKt.trimMargin$default"
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L9e
        L88:
            r1 = 1
            r2 = 0
            java.lang.String r0 = kotlin.text.StringsKt.trimMargin$default(r0, r2, r1, r2)
        L8e:
            org.jetbrains.kotlin.ir.interpreter.stack.CallStack r1 = r5.getCallStack()
            org.jetbrains.kotlin.ir.types.IrType r4 = r4.getReturnType()
            org.jetbrains.kotlin.ir.interpreter.state.State r4 = r5.convertToState$ir_interpreter(r0, r4)
            r1.pushState(r4)
            return
        L9e:
            kotlin.NotImplementedError r4 = new kotlin.NotImplementedError
            java.lang.String r5 = "An operation is not implemented: unknown trim function"
            r4.<init>(r5)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.ir.interpreter.intrinsics.Indent.evaluate(org.jetbrains.kotlin.ir.declarations.IrFunction, org.jetbrains.kotlin.ir.interpreter.IrInterpreterEnvironment):void");
    }
}
