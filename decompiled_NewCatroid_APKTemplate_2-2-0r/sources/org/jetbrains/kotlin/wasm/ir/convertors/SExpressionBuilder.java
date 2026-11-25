package org.jetbrains.kotlin.wasm.ir.convertors;

import com.huawei.hms.ml.language.common.utils.Constant;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WasmIrToText.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0004J\u001a\u0010\u0012\u001a\u00020\u000f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u0084\bø\u0001\u0000J\b\u0010\u0015\u001a\u00020\u000fH\u0004J\"\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u0084\bø\u0001\u0000J\"\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00112\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014H\u0084\bø\u0001\u0000J\b\u0010\u0019\u001a\u00020\u0011H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\u00060\nj\u0002`\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/SExpressionBuilder;", "", "()V", OutputKeys.INDENT, "", "getIndent", "()I", "setIndent", "(I)V", "stringBuilder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getStringBuilder", "()Ljava/lang/StringBuilder;", "appendElement", "", "value", "", "indented", "body", "Lkotlin/Function0;", "newLine", "newLineList", "name", "sameLineList", "toString", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SExpressionBuilder {
    private int indent;
    private final StringBuilder stringBuilder = new StringBuilder();

    protected final StringBuilder getStringBuilder() {
        return this.stringBuilder;
    }

    protected final int getIndent() {
        return this.indent;
    }

    protected final void setIndent(int i) {
        this.indent = i;
    }

    protected final void indented(Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        this.indent++;
        body.invoke();
        this.indent--;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void newLine() {
        Intrinsics.checkNotNullExpressionValue(this.stringBuilder.append('\n'), "append(...)");
        int i = this.indent;
        for (int i2 = 0; i2 < i; i2++) {
            this.stringBuilder.append("    ");
        }
    }

    protected final void newLineList(String name2, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(body, "body");
        newLine();
        this.stringBuilder.append("(" + name2);
        this.indent++;
        body.invoke();
        this.indent--;
        this.stringBuilder.append(Constant.AFTER_QUTO);
    }

    protected final void sameLineList(String name2, Function0<Unit> body) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(body, "body");
        this.stringBuilder.append(" (" + name2);
        body.invoke();
        this.stringBuilder.append(Constant.AFTER_QUTO);
    }

    protected final void appendElement(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        this.stringBuilder.append(" ");
        this.stringBuilder.append(value);
    }

    public String toString() {
        String string = this.stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
