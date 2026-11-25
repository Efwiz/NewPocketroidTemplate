package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ParameterInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0016\u0018\u00002\u00020\u0001B/\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nB9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003¢\u0006\u0002\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0010R\u0011\u0010\u001e\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001fR\u0011\u0010 \u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b \u0010\u001fR\u001c\u0010\b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b'\u0010&¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "skipped", "", "index", "", "remapValue", "declarationIndex", "(Lorg/jetbrains/org/objectweb/asm/Type;ZIII)V", "isSkipped", "Lorg/jetbrains/kotlin/codegen/StackValue;", "typeOnStack", "(Lorg/jetbrains/org/objectweb/asm/Type;ZILorg/jetbrains/kotlin/codegen/StackValue;ILorg/jetbrains/org/objectweb/asm/Type;)V", "getDeclarationIndex", "()I", "fieldEquivalent", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "getFieldEquivalent", "()Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "setFieldEquivalent", "(Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;)V", "functionalArgument", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "getFunctionalArgument", "()Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "setFunctionalArgument", "(Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;)V", "getIndex", "isRemapped", "()Z", "isSkippedOrRemapped", "getRemapValue", "()Lorg/jetbrains/kotlin/codegen/StackValue;", "setRemapValue", "(Lorg/jetbrains/kotlin/codegen/StackValue;)V", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "getTypeOnStack", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ParameterInfo {
    private final int declarationIndex;
    private CapturedParamInfo fieldEquivalent;
    private FunctionalArgument functionalArgument;
    private final int index;
    private final boolean isSkipped;
    private StackValue remapValue;
    private final Type type;
    private final Type typeOnStack;

    public ParameterInfo(Type type, boolean z, int i, StackValue stackValue, int i2, Type typeOnStack) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeOnStack, "typeOnStack");
        this.type = type;
        this.isSkipped = z;
        this.index = i;
        this.remapValue = stackValue;
        this.declarationIndex = i2;
        this.typeOnStack = typeOnStack;
    }

    public /* synthetic */ ParameterInfo(Type type, boolean z, int i, StackValue stackValue, int i2, Type type2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(type, z, i, stackValue, i2, (i3 & 32) != 0 ? type : type2);
    }

    public final Type getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isSkipped, reason: from getter */
    public final boolean getIsSkipped() {
        return this.isSkipped;
    }

    public final int getIndex() {
        return this.index;
    }

    public final StackValue getRemapValue() {
        return this.remapValue;
    }

    public final void setRemapValue(StackValue stackValue) {
        this.remapValue = stackValue;
    }

    public final int getDeclarationIndex() {
        return this.declarationIndex;
    }

    public final Type getTypeOnStack() {
        return this.typeOnStack;
    }

    public final CapturedParamInfo getFieldEquivalent() {
        return this.fieldEquivalent;
    }

    public final void setFieldEquivalent(CapturedParamInfo capturedParamInfo) {
        this.fieldEquivalent = capturedParamInfo;
    }

    public final FunctionalArgument getFunctionalArgument() {
        return this.functionalArgument;
    }

    public final void setFunctionalArgument(FunctionalArgument functionalArgument) {
        this.functionalArgument = functionalArgument;
    }

    public final boolean isSkippedOrRemapped() {
        return this.isSkipped || isRemapped();
    }

    public final boolean isRemapped() {
        return this.remapValue != null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ParameterInfo(Type type, boolean z, int i, int i2, int i3) {
        this(type, z, i, i2 == -1 ? null : StackValue.local(i2, type), i3, null, 32, null);
        Intrinsics.checkNotNullParameter(type, "type");
    }
}
