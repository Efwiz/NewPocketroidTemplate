package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ExpressionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\r\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/VariableInfo;", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "index", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "startLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;ILorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getDeclaration", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "explicitEndLabel", "getExplicitEndLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "setExplicitEndLabel", "(Lorg/jetbrains/org/objectweb/asm/Label;)V", "gaps", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/Gap;", "getGaps", "()Ljava/util/List;", "getIndex", "()I", "getStartLabel", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableInfo {
    private final IrVariable declaration;
    private Label explicitEndLabel;
    private final List<Gap> gaps;
    private final int index;
    private final Label startLabel;
    private final Type type;

    public VariableInfo(IrVariable declaration, int i, Type type, Label startLabel) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(startLabel, "startLabel");
        this.declaration = declaration;
        this.index = i;
        this.type = type;
        this.startLabel = startLabel;
        this.gaps = new ArrayList();
    }

    public final IrVariable getDeclaration() {
        return this.declaration;
    }

    public final int getIndex() {
        return this.index;
    }

    public final Label getStartLabel() {
        return this.startLabel;
    }

    public final Type getType() {
        return this.type;
    }

    public final List<Gap> getGaps() {
        return this.gaps;
    }

    public final Label getExplicitEndLabel() {
        return this.explicitEndLabel;
    }

    public final void setExplicitEndLabel(Label label) {
        this.explicitEndLabel = label;
    }
}
