package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.IrWhenUtils;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.when.SwitchCodegen;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: SwitchGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0006./0123B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u0016\u0010\u0010\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u0016\u0010\u0011\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002J\u0016\u0010\u0012\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0002JB\u0010\u0013\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\f0\u00152\u0016\u0010\u0017\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020\f0\u0015H\u0002J4\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000e2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u001f0\u001ej\b\u0012\u0004\u0012\u00020\u001f` H\u0002J\b\u0010!\u001a\u0004\u0018\u00010\"J^\u0010#\u001a\b\u0018\u00010$R\u00020\u00002\u0006\u0010%\u001a\u00020&2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001c0\u001ej\b\u0012\u0004\u0012\u00020\u001c` 2\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u001f0\u001ej\b\u0012\u0004\u0012\u00020\u001f` 2\b\u0010'\u001a\u0004\u0018\u00010&H\u0002J`\u0010(\u001a\b\u0018\u00010$R\u00020\u00002\b\u0010%\u001a\u0004\u0018\u00010)2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001c0\u001ej\b\u0012\u0004\u0012\u00020\u001c` 2\u0016\u0010\u001d\u001a\u0012\u0012\u0004\u0012\u00020\u001f0\u001ej\b\u0012\u0004\u0012\u00020\u001f` 2\b\u0010'\u001a\u0004\u0018\u00010&H\u0002J\n\u0010*\u001a\u00020\f*\u00020\u000fJ*\u0010+\u001a\u00020,*\u0012\u0012\u0004\u0012\u00020\u001f0\u001ej\b\u0012\u0004\u0012\u00020\u001f` 2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u001a0\u000eH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;)V", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "areConstIntComparisons", "", "conditions", "", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "areConstStringComparisons", "areConstUIntComparisons", "areConstantComparisons", "checkTypeSpecifics", "subjectTypePredicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "irConstPredicate", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "extractSwitchCasesAndFilterUnreachableLabels", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ValueToLabel;", "callToLabels", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$CallToLabel;", "expressionToLabels", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ExpressionToLabel;", "Lkotlin/collections/ArrayList;", CompilerOptions.GENERATE, "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "generatePrimitiveSwitch", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$Switch;", "subject", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "elseExpression", "generateUIntSwitch", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "isCoerceFromUIntToInt", "removeUnreachableLabels", "", "cases", "CallToLabel", "ExpressionToLabel", "IntSwitch", "StringSwitch", "Switch", "ValueToLabel", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SwitchGenerator {
    private final ExpressionCodegen codegen;
    private final JvmBackendContext context;
    private final BlockInfo data;
    private final IrWhen expression;

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ExpressionToLabel;", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ExpressionToLabel {
        private final IrExpression expression;
        private final Label label;

        public static /* synthetic */ ExpressionToLabel copy$default(ExpressionToLabel expressionToLabel, IrExpression irExpression, Label label, int i, Object obj) {
            if ((i & 1) != 0) {
                irExpression = expressionToLabel.expression;
            }
            if ((i & 2) != 0) {
                label = expressionToLabel.label;
            }
            return expressionToLabel.copy(irExpression, label);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrExpression getExpression() {
            return this.expression;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Label getLabel() {
            return this.label;
        }

        public final ExpressionToLabel copy(IrExpression expression, Label label) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(label, "label");
            return new ExpressionToLabel(expression, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ExpressionToLabel)) {
                return false;
            }
            ExpressionToLabel expressionToLabel = (ExpressionToLabel) other;
            return Intrinsics.areEqual(this.expression, expressionToLabel.expression) && Intrinsics.areEqual(this.label, expressionToLabel.label);
        }

        public int hashCode() {
            return (this.expression.hashCode() * 31) + this.label.hashCode();
        }

        public String toString() {
            return "ExpressionToLabel(expression=" + this.expression + ", label=" + this.label + ')';
        }

        public ExpressionToLabel(IrExpression expression, Label label) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(label, "label");
            this.expression = expression;
            this.label = label;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final Label getLabel() {
            return this.label;
        }
    }

    public SwitchGenerator(IrWhen expression, BlockInfo data, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        this.expression = expression;
        this.data = data;
        this.codegen = codegen;
        this.context = codegen.getContext();
    }

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$CallToLabel;", "", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class CallToLabel {
        private final IrCall call;
        private final Label label;

        public static /* synthetic */ CallToLabel copy$default(CallToLabel callToLabel, IrCall irCall, Label label, int i, Object obj) {
            if ((i & 1) != 0) {
                irCall = callToLabel.call;
            }
            if ((i & 2) != 0) {
                label = callToLabel.label;
            }
            return callToLabel.copy(irCall, label);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrCall getCall() {
            return this.call;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Label getLabel() {
            return this.label;
        }

        public final CallToLabel copy(IrCall call, Label label) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(label, "label");
            return new CallToLabel(call, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CallToLabel)) {
                return false;
            }
            CallToLabel callToLabel = (CallToLabel) other;
            return Intrinsics.areEqual(this.call, callToLabel.call) && Intrinsics.areEqual(this.label, callToLabel.label);
        }

        public int hashCode() {
            return (this.call.hashCode() * 31) + this.label.hashCode();
        }

        public String toString() {
            return "CallToLabel(call=" + this.call + ", label=" + this.label + ')';
        }

        public CallToLabel(IrCall call, Label label) {
            Intrinsics.checkNotNullParameter(call, "call");
            Intrinsics.checkNotNullParameter(label, "label");
            this.call = call;
            this.label = label;
        }

        public final IrCall getCall() {
            return this.call;
        }

        public final Label getLabel() {
            return this.label;
        }
    }

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0004HÆ\u0003J\u001f\u0010\f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ValueToLabel;", "", "value", CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/Label;", "(Ljava/lang/Object;Lorg/jetbrains/org/objectweb/asm/Label;)V", "getLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "getValue", "()Ljava/lang/Object;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ValueToLabel {
        private final Label label;
        private final Object value;

        public static /* synthetic */ ValueToLabel copy$default(ValueToLabel valueToLabel, Object obj, Label label, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = valueToLabel.value;
            }
            if ((i & 2) != 0) {
                label = valueToLabel.label;
            }
            return valueToLabel.copy(obj, label);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Object getValue() {
            return this.value;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Label getLabel() {
            return this.label;
        }

        public final ValueToLabel copy(Object value, Label label) {
            Intrinsics.checkNotNullParameter(label, "label");
            return new ValueToLabel(value, label);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ValueToLabel)) {
                return false;
            }
            ValueToLabel valueToLabel = (ValueToLabel) other;
            return Intrinsics.areEqual(this.value, valueToLabel.value) && Intrinsics.areEqual(this.label, valueToLabel.label);
        }

        public int hashCode() {
            Object obj = this.value;
            return ((obj == null ? 0 : obj.hashCode()) * 31) + this.label.hashCode();
        }

        public String toString() {
            return "ValueToLabel(value=" + this.value + ", label=" + this.label + ')';
        }

        public ValueToLabel(Object obj, Label label) {
            Intrinsics.checkNotNullParameter(label, "label");
            this.value = obj;
            this.label = label;
        }

        public final Label getLabel() {
            return this.label;
        }

        public final Object getValue() {
            return this.value;
        }
    }

    /* JADX WARN: Code duplicated, block: B:35:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:43:0x0117  */
    public final PromisedValue generate() {
        Switch switchGeneratePrimitiveSwitch;
        ArrayList<ExpressionToLabel> arrayList = new ArrayList<>();
        ArrayList<CallToLabel> arrayList2 = new ArrayList<>();
        IrExpression result = null;
        for (IrBranch irBranch : this.expression.getBranches()) {
            if (irBranch instanceof IrElseBranch) {
                result = irBranch.getResult();
            } else {
                ArrayList<IrCall> arrayListMatchConditions = IrWhenUtils.INSTANCE.matchConditions(this.context.getIrBuiltIns().getOrorSymbol(), irBranch.getCondition());
                if (arrayListMatchConditions == null) {
                    return null;
                }
                Label label = new Label();
                arrayList.add(new ExpressionToLabel(irBranch.getResult(), label));
                ArrayList<CallToLabel> arrayList3 = arrayList2;
                ArrayList<IrCall> arrayList4 = arrayListMatchConditions;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                Iterator<T> it = arrayList4.iterator();
                while (it.hasNext()) {
                    arrayList5.add(new CallToLabel((IrCall) it.next(), label));
                }
                CollectionsKt.addAll(arrayList3, arrayList5);
            }
        }
        if (arrayList2.size() == 0) {
            return null;
        }
        ArrayList<CallToLabel> arrayList6 = arrayList2;
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList6, 10));
        Iterator<T> it2 = arrayList6.iterator();
        while (it2.hasNext()) {
            arrayList7.add(((CallToLabel) it2.next()).getCall());
        }
        ArrayList arrayList8 = arrayList7;
        IrCall call = arrayList2.get(0).getCall();
        if (!Intrinsics.areEqual(call.getSymbol(), this.context.getIrBuiltIns().getEqeqSymbol())) {
            return null;
        }
        IrExpression valueArgument = call.getValueArgument(0);
        if (valueArgument instanceof IrCall) {
            IrCall irCall = (IrCall) valueArgument;
            if (isCoerceFromUIntToInt(irCall)) {
                IrExpression valueArgument2 = irCall.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument2);
                switchGeneratePrimitiveSwitch = generateUIntSwitch(valueArgument2 instanceof IrGetValue ? (IrGetValue) valueArgument2 : null, arrayList8, arrayList2, arrayList, result);
            } else if (!(valueArgument instanceof IrGetValue) || ((valueArgument instanceof IrConst) && IrTypePredicatesKt.isString(((IrConst) valueArgument).getType()))) {
                switchGeneratePrimitiveSwitch = generatePrimitiveSwitch(valueArgument, arrayList8, arrayList2, arrayList, result);
            } else {
                switchGeneratePrimitiveSwitch = null;
            }
        } else if (valueArgument instanceof IrGetValue) {
            switchGeneratePrimitiveSwitch = generatePrimitiveSwitch(valueArgument, arrayList8, arrayList2, arrayList, result);
        } else {
            switchGeneratePrimitiveSwitch = generatePrimitiveSwitch(valueArgument, arrayList8, arrayList2, arrayList, result);
        }
        if (switchGeneratePrimitiveSwitch != null) {
            return switchGeneratePrimitiveSwitch.genOptimizedIfEnoughCases();
        }
        return null;
    }

    public final boolean isCoerceFromUIntToInt(IrCall irCall) {
        IrType typeArgument;
        IrType typeArgument2;
        Intrinsics.checkNotNullParameter(irCall, "<this>");
        return Intrinsics.areEqual(irCall.getSymbol(), this.context.getIr().getSymbols().getUnsafeCoerceIntrinsic()) && (typeArgument = irCall.getTypeArgument(0)) != null && IrTypePredicatesKt.isUInt(typeArgument) && (typeArgument2 = irCall.getTypeArgument(1)) != null && IrTypePredicatesKt.isInt(typeArgument2);
    }

    private final Switch generateUIntSwitch(IrGetValue subject, List<? extends IrCall> conditions, ArrayList<CallToLabel> callToLabels, ArrayList<ExpressionToLabel> expressionToLabels, IrExpression elseExpression) {
        if (subject == null || !areConstUIntComparisons(conditions)) {
            return null;
        }
        ArrayList<CallToLabel> arrayList = callToLabels;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList, 10));
        for (CallToLabel callToLabel : arrayList) {
            IrExpression valueArgument = callToLabel.getCall().getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument);
            IrExpression valueArgument2 = ((IrCall) valueArgument).getValueArgument(0);
            Intrinsics.checkNotNull(valueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
            arrayList2.add(new ValueToLabel(((IrConst) valueArgument2).getValue(), callToLabel.getLabel()));
        }
        HashSet hashSet = new HashSet();
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (hashSet.add(((ValueToLabel) obj).getValue())) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        removeUnreachableLabels(expressionToLabels, arrayList4);
        return new IntSwitch(this, subject, elseExpression, expressionToLabels, arrayList4);
    }

    private final Switch generatePrimitiveSwitch(IrExpression subject, List<? extends IrCall> conditions, ArrayList<CallToLabel> callToLabels, ArrayList<ExpressionToLabel> expressionToLabels, IrExpression elseExpression) {
        if (!areConstantComparisons(conditions)) {
            return null;
        }
        if ((subject instanceof IrGetValue) && areConstIntComparisons(conditions)) {
            return new IntSwitch(this, (IrGetValue) subject, elseExpression, expressionToLabels, extractSwitchCasesAndFilterUnreachableLabels(callToLabels, expressionToLabels));
        }
        if (areConstStringComparisons(conditions)) {
            return new StringSwitch(this, subject, elseExpression, expressionToLabels, extractSwitchCasesAndFilterUnreachableLabels(callToLabels, expressionToLabels));
        }
        return null;
    }

    private final boolean areConstUIntComparisons(List<? extends IrCall> conditions) {
        List<? extends IrCall> list = conditions;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (true) {
            IrCall irCall = null;
            if (!it.hasNext()) {
                break;
            }
            IrCall irCall2 = (IrCall) it.next();
            if (!Intrinsics.areEqual(irCall2.getSymbol(), this.context.getIrBuiltIns().getEqeqSymbol())) {
                irCall2 = null;
            }
            IrExpression valueArgument = irCall2 != null ? irCall2.getValueArgument(0) : null;
            if (valueArgument instanceof IrCall) {
                irCall = (IrCall) valueArgument;
            }
            arrayList.add(irCall);
        }
        ArrayList<IrCall> arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            for (IrCall irCall3 : arrayList2) {
                if (irCall3 == null || !isCoerceFromUIntToInt(irCall3)) {
                    return false;
                }
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (IrCall irCall4 : arrayList2) {
            Intrinsics.checkNotNull(irCall4);
            IrExpression valueArgument2 = irCall4.getValueArgument(0);
            arrayList3.add(valueArgument2 instanceof IrGetValue ? (IrGetValue) valueArgument2 : null);
        }
        ArrayList arrayList4 = arrayList3;
        ArrayList<IrGetValue> arrayList5 = arrayList4;
        if (!(arrayList5 instanceof Collection) || !arrayList5.isEmpty()) {
            for (IrGetValue irGetValue : arrayList5) {
                if (irGetValue != null) {
                    IrValueSymbol symbol = irGetValue.getSymbol();
                    Object obj = arrayList4.get(0);
                    Intrinsics.checkNotNull(obj);
                    if (!Intrinsics.areEqual(symbol, ((IrGetValue) obj).getSymbol())) {
                    }
                }
                return false;
            }
        }
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            IrExpression valueArgument3 = ((IrCall) it2.next()).getValueArgument(1);
            arrayList6.add(valueArgument3 instanceof IrCall ? (IrCall) valueArgument3 : null);
        }
        ArrayList<IrCall> arrayList7 = arrayList6;
        if (!(arrayList7 instanceof Collection) || !arrayList7.isEmpty()) {
            for (IrCall irCall5 : arrayList7) {
                if (irCall5 == null || !isCoerceFromUIntToInt(irCall5) || !(irCall5.getValueArgument(0) instanceof IrConst)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final boolean areConstantComparisons$isValidIrGetValueTypeLHS(List<? extends IrCall> list, SwitchGenerator switchGenerator) {
        List<? extends IrCall> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (IrCall irCall : list2) {
            IrGetValue irGetValue = null;
            if (!Intrinsics.areEqual(irCall.getSymbol(), switchGenerator.context.getIrBuiltIns().getEqeqSymbol())) {
                irCall = null;
            }
            IrExpression valueArgument = irCall != null ? irCall.getValueArgument(0) : null;
            if (valueArgument instanceof IrGetValue) {
                irGetValue = (IrGetValue) valueArgument;
            }
            arrayList.add(irGetValue);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList<IrGetValue> arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            for (IrGetValue irGetValue2 : arrayList3) {
                if (irGetValue2 == null) {
                    return false;
                }
                IrValueSymbol symbol = irGetValue2.getSymbol();
                Object obj = arrayList2.get(0);
                Intrinsics.checkNotNull(obj);
                if (!Intrinsics.areEqual(symbol, ((IrGetValue) obj).getSymbol())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final boolean areConstantComparisons$isValidIrConstTypeLHS(List<? extends IrCall> list, SwitchGenerator switchGenerator) {
        List<? extends IrCall> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (IrCall irCall : list2) {
            IrConst irConst = null;
            if (!Intrinsics.areEqual(irCall.getSymbol(), switchGenerator.context.getIrBuiltIns().getEqeqSymbol())) {
                irCall = null;
            }
            IrExpression valueArgument = irCall != null ? irCall.getValueArgument(0) : null;
            if (valueArgument instanceof IrConst) {
                irConst = (IrConst) valueArgument;
            }
            arrayList.add(irConst);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList<IrConst> arrayList3 = arrayList2;
        if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
            for (IrConst irConst2 : arrayList3) {
                if (irConst2 == null) {
                    return false;
                }
                Object value = irConst2.getValue();
                Object obj = arrayList2.get(0);
                Intrinsics.checkNotNull(obj);
                if (!Intrinsics.areEqual(value, ((IrConst) obj).getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean areConstantComparisons(List<? extends IrCall> conditions) {
        if (!areConstantComparisons$isValidIrGetValueTypeLHS(conditions, this) && !areConstantComparisons$isValidIrConstTypeLHS(conditions, this)) {
            return false;
        }
        List<? extends IrCall> list = conditions;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!(((IrCall) it.next()).getValueArgument(1) instanceof IrConst)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean areConstIntComparisons(List<? extends IrCall> conditions) {
        return checkTypeSpecifics(conditions, new Function1<IrType, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.areConstIntComparisons.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrTypePredicatesKt.isInt(it));
            }
        }, new Function1<IrConst<?>, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.areConstIntComparisons.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrConst<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getKind(), IrConstKind.Int.INSTANCE));
            }
        });
    }

    private final boolean areConstStringComparisons(List<? extends IrCall> conditions) {
        return checkTypeSpecifics(conditions, new Function1<IrType, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.areConstStringComparisons.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(IrTypePredicatesKt.isString(it) || IrTypePredicatesKt.isNullableString(it));
            }
        }, new Function1<IrConst<?>, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.areConstStringComparisons.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrConst<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getKind(), IrConstKind.String.INSTANCE) || Intrinsics.areEqual(it.getKind(), IrConstKind.Null.INSTANCE));
            }
        });
    }

    private final boolean checkTypeSpecifics(List<? extends IrCall> conditions, Function1<? super IrType, Boolean> subjectTypePredicate, Function1<? super IrConst<?>, Boolean> irConstPredicate) {
        List<? extends IrCall> list = conditions;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (IrCall irCall : list) {
            IrExpression valueArgument = irCall.getValueArgument(0);
            IrExpression irExpression = valueArgument instanceof IrGetValue ? (IrGetValue) valueArgument : null;
            if (irExpression == null) {
                IrExpression valueArgument2 = irCall.getValueArgument(0);
                Intrinsics.checkNotNull(valueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
                irExpression = (IrConst) valueArgument2;
            }
            arrayList.add(irExpression);
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                if (!subjectTypePredicate.invoke(((IrExpression) it.next()).getType()).booleanValue()) {
                    return false;
                }
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            IrExpression valueArgument3 = ((IrCall) it2.next()).getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
            arrayList3.add((IrConst) valueArgument3);
        }
        ArrayList arrayList4 = arrayList3;
        if (!(arrayList4 instanceof Collection) || !arrayList4.isEmpty()) {
            Iterator it3 = arrayList4.iterator();
            while (it3.hasNext()) {
                if (!irConstPredicate.invoke((IrConst) it3.next()).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    private final List<ValueToLabel> extractSwitchCasesAndFilterUnreachableLabels(List<CallToLabel> callToLabels, ArrayList<ExpressionToLabel> expressionToLabels) {
        List<CallToLabel> list = callToLabels;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (CallToLabel callToLabel : list) {
            IrExpression valueArgument = callToLabel.getCall().getValueArgument(1);
            Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
            arrayList.add(new ValueToLabel(((IrConst) valueArgument).getValue(), callToLabel.getLabel()));
        }
        HashSet hashSet = new HashSet();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (hashSet.add(((ValueToLabel) obj).getValue())) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        removeUnreachableLabels(expressionToLabels, arrayList3);
        return arrayList3;
    }

    private final void removeUnreachableLabels(ArrayList<ExpressionToLabel> arrayList, List<ValueToLabel> list) {
        List<ValueToLabel> list2 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(((ValueToLabel) it.next()).getLabel());
        }
        final HashSet hashSet = new HashSet(arrayList2);
        final Function1<ExpressionToLabel, Boolean> function1 = new Function1<ExpressionToLabel, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.removeUnreachableLabels.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ExpressionToLabel it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(!hashSet.contains(it2.getLabel()));
            }
        };
        arrayList.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SwitchGenerator.removeUnreachableLabels$lambda$25(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean removeUnreachableLabels$lambda$25(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b¦\u0004\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\u0002\u0010\tJ\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0004J\n\u0010\u001a\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u001b\u001a\u00020\u0016H$J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$Switch;", "", "subject", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "elseExpression", "expressionToLabels", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ExpressionToLabel;", "Lkotlin/collections/ArrayList;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/ArrayList;)V", "defaultLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "getDefaultLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "getElseExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getExpressionToLabels", "()Ljava/util/ArrayList;", "getSubject", "genBranchTargets", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "genIntSwitch", "", "unsortedIntCases", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ValueToLabel;", "genOptimizedIfEnoughCases", "genSwitch", "shouldOptimize", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public abstract class Switch {
        private final Label defaultLabel;
        private final IrExpression elseExpression;
        private final ArrayList<ExpressionToLabel> expressionToLabels;
        private final IrExpression subject;
        final /* synthetic */ SwitchGenerator this$0;

        protected abstract void genSwitch();

        public boolean shouldOptimize() {
            return false;
        }

        public Switch(SwitchGenerator switchGenerator, IrExpression subject, IrExpression irExpression, ArrayList<ExpressionToLabel> expressionToLabels) {
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(expressionToLabels, "expressionToLabels");
            this.this$0 = switchGenerator;
            this.subject = subject;
            this.elseExpression = irExpression;
            this.expressionToLabels = expressionToLabels;
            this.defaultLabel = new Label();
        }

        public final IrExpression getSubject() {
            return this.subject;
        }

        public final IrExpression getElseExpression() {
            return this.elseExpression;
        }

        public final ArrayList<ExpressionToLabel> getExpressionToLabels() {
            return this.expressionToLabels;
        }

        protected final Label getDefaultLabel() {
            return this.defaultLabel;
        }

        public PromisedValue genOptimizedIfEnoughCases() {
            if (!shouldOptimize()) {
                return null;
            }
            genSwitch();
            return genBranchTargets();
        }

        protected final void genIntSwitch(List<ValueToLabel> unsortedIntCases) {
            Intrinsics.checkNotNullParameter(unsortedIntCases, "unsortedIntCases");
            List<ValueToLabel> listSortedWith = CollectionsKt.sortedWith(unsortedIntCases, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator$Switch$genIntSwitch$$inlined$sortedBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    Object value = ((SwitchGenerator.ValueToLabel) t).getValue();
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
                    Object value2 = ((SwitchGenerator.ValueToLabel) t2).getValue();
                    Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Int");
                    return ComparisonsKt.compareValues((Integer) value, (Integer) value2);
                }
            });
            Object value = ((ValueToLabel) CollectionsKt.first(listSortedWith)).getValue();
            Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) value).intValue();
            Object value2 = ((ValueToLabel) CollectionsKt.last(listSortedWith)).getValue();
            Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue2 = ((Integer) value2).intValue();
            long j = (((long) iIntValue2) - ((long) iIntValue)) + 1;
            ExpressionCodegen expressionCodegen = this.this$0.codegen;
            if (!SwitchCodegen.INSTANCE.preferLookupOverSwitch(listSortedWith.size(), j)) {
                int i = (int) j;
                Label[] labelArr = new Label[i];
                for (int i2 = 0; i2 < i; i2++) {
                    labelArr[i2] = this.defaultLabel;
                }
                for (ValueToLabel valueToLabel : listSortedWith) {
                    Object value3 = valueToLabel.getValue();
                    Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Int");
                    labelArr[((Integer) value3).intValue() - iIntValue] = valueToLabel.getLabel();
                }
                expressionCodegen.getMv().tableswitch(iIntValue, iIntValue2, this.defaultLabel, (Label[]) Arrays.copyOf(labelArr, i));
                return;
            }
            InstructionAdapter mv = expressionCodegen.getMv();
            Label label = this.defaultLabel;
            List list = listSortedWith;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object value4 = ((ValueToLabel) it.next()).getValue();
                Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Int");
                arrayList.add(Integer.valueOf(((Integer) value4).intValue()));
            }
            int[] intArray = CollectionsKt.toIntArray(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((ValueToLabel) it2.next()).getLabel());
            }
            mv.lookupswitch(label, intArray, (Label[]) arrayList2.toArray(new Label[0]));
        }

        private final PromisedValue genBranchTargets() {
            PromisedValue promisedValue;
            MaterialValue materialValueMaterializedAt;
            ExpressionCodegen expressionCodegen = this.this$0.codegen;
            SwitchGenerator switchGenerator = this.this$0;
            Label label = new Label();
            for (ExpressionToLabel expressionToLabel : this.expressionToLabels) {
                IrExpression expression = expressionToLabel.getExpression();
                expressionCodegen.getMv().visitLabel(expressionToLabel.getLabel());
                PromisedValue promisedValue2 = (PromisedValue) expression.accept(expressionCodegen, switchGenerator.data);
                if (this.elseExpression != null) {
                    PromisedValueKt.materializedAt(promisedValue2, switchGenerator.expression.getType());
                } else {
                    promisedValue2.discard();
                }
                expressionCodegen.getMv().goTo(label);
            }
            expressionCodegen.getMv().visitLabel(this.defaultLabel);
            IrExpression irExpression = this.elseExpression;
            MaterialValue unitValue = (irExpression == null || (promisedValue = (PromisedValue) irExpression.accept(expressionCodegen, switchGenerator.data)) == null || (materialValueMaterializedAt = PromisedValueKt.materializedAt(promisedValue, switchGenerator.expression.getType())) == null) ? PromisedValueKt.getUnitValue(expressionCodegen) : materialValueMaterializedAt;
            expressionCodegen.getMv().mark(label);
            return unitValue;
        }
    }

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$IntSwitch;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$Switch;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;", "subject", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "elseExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expressionToLabels", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ExpressionToLabel;", "Lkotlin/collections/ArrayList;", "cases", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ValueToLabel;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/ArrayList;Ljava/util/List;)V", "genSwitch", "", "shouldOptimize", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class IntSwitch extends Switch {
        private final List<ValueToLabel> cases;
        final /* synthetic */ SwitchGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IntSwitch(SwitchGenerator switchGenerator, IrGetValue subject, IrExpression irExpression, ArrayList<ExpressionToLabel> expressionToLabels, List<ValueToLabel> cases) {
            super(switchGenerator, subject, irExpression, expressionToLabels);
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(expressionToLabels, "expressionToLabels");
            Intrinsics.checkNotNullParameter(cases, "cases");
            this.this$0 = switchGenerator;
            this.cases = cases;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.Switch
        public boolean shouldOptimize() {
            return this.cases.size() > 1;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.Switch
        protected void genSwitch() {
            final ExpressionCodegen expressionCodegen = this.this$0.codegen;
            final SwitchGenerator switchGenerator = this.this$0;
            expressionCodegen.noLineNumberScope(new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator$IntSwitch$genSwitch$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    PromisedValue promisedValue = (PromisedValue) this.this$0.getSubject().accept(expressionCodegen, switchGenerator.data);
                    Type INT_TYPE = Type.INT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                    PromisedValueKt.materializeAt(promisedValue, INT_TYPE, promisedValue.getIrType());
                }
            });
            genIntSwitch(this.cases);
        }
    }

    /* JADX INFO: compiled from: SwitchGenerator.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\u00060\u0001R\u00020\u0002B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\u0002\u0010\rJ\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\f0\u0007j\b\u0012\u0004\u0012\u00020\f`\tX\u0082\u0004¢\u0006\u0002\n\u0000RJ\u0010\u000f\u001a>\u0012\u0004\u0012\u00020\u0011\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u0007j\b\u0012\u0004\u0012\u00020\f`\t0\u0010j\u001e\u0012\u0004\u0012\u00020\u0011\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\f0\u0007j\b\u0012\u0004\u0012\u00020\f`\t`\u0012X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$StringSwitch;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$Switch;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;", "subject", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "elseExpression", "expressionToLabels", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ExpressionToLabel;", "Lkotlin/collections/ArrayList;", "cases", "", "Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator$ValueToLabel;", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/SwitchGenerator;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/util/ArrayList;Ljava/util/List;)V", "hashAndSwitchLabels", "hashToStringAndExprLabels", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "genSwitch", "", "shouldOptimize", "", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class StringSwitch extends Switch {
        private final List<ValueToLabel> cases;
        private final ArrayList<ValueToLabel> hashAndSwitchLabels;
        private final HashMap<Integer, ArrayList<ValueToLabel>> hashToStringAndExprLabels;
        final /* synthetic */ SwitchGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StringSwitch(SwitchGenerator switchGenerator, IrExpression subject, IrExpression irExpression, ArrayList<ExpressionToLabel> expressionToLabels, List<ValueToLabel> cases) {
            super(switchGenerator, subject, irExpression, expressionToLabels);
            Intrinsics.checkNotNullParameter(subject, "subject");
            Intrinsics.checkNotNullParameter(expressionToLabels, "expressionToLabels");
            Intrinsics.checkNotNullParameter(cases, "cases");
            this.this$0 = switchGenerator;
            this.cases = cases;
            this.hashToStringAndExprLabels = new HashMap<>();
            this.hashAndSwitchLabels = new ArrayList<>();
            for (ValueToLabel valueToLabel : cases) {
                if (valueToLabel.getValue() != null) {
                    HashMap<Integer, ArrayList<ValueToLabel>> map = this.hashToStringAndExprLabels;
                    Integer numValueOf = Integer.valueOf(valueToLabel.getValue().hashCode());
                    ArrayList<ValueToLabel> arrayList = map.get(numValueOf);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                        map.put(numValueOf, arrayList);
                    }
                    arrayList.add(new ValueToLabel(valueToLabel.getValue(), valueToLabel.getLabel()));
                }
            }
            Iterator<Integer> it = this.hashToStringAndExprLabels.keySet().iterator();
            while (it.hasNext()) {
                this.hashAndSwitchLabels.add(new ValueToLabel(it.next(), new Label()));
            }
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.Switch
        public boolean shouldOptimize() {
            if (this.hashAndSwitchLabels.size() <= (getSubject() instanceof IrConst ? 0 : 2)) {
                IrType type = getSubject().getType();
                FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
                Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
                if (!IrUtilsKt.hasAnnotation(type, ENHANCED_NULLABILITY_ANNOTATION) || this.hashAndSwitchLabels.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator.Switch
        protected void genSwitch() {
            final ExpressionCodegen expressionCodegen = this.this$0.codegen;
            final SwitchGenerator switchGenerator = this.this$0;
            expressionCodegen.noLineNumberScope(new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator$StringSwitch$genSwitch$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Object next;
                    Label defaultLabel;
                    if (IrTypePredicatesKt.isNullableString(this.this$0.getSubject().getType())) {
                        PromisedValueKt.materialize((PromisedValue) this.this$0.getSubject().accept(expressionCodegen, switchGenerator.data));
                        InstructionAdapter mv = expressionCodegen.getMv();
                        Iterator it = this.this$0.cases.iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (((SwitchGenerator.ValueToLabel) next).getValue() != null);
                        SwitchGenerator.ValueToLabel valueToLabel = (SwitchGenerator.ValueToLabel) next;
                        if (valueToLabel == null || (defaultLabel = valueToLabel.getLabel()) == null) {
                            defaultLabel = this.this$0.getDefaultLabel();
                        }
                        mv.ifnull(defaultLabel);
                    }
                    PromisedValueKt.materialize((PromisedValue) this.this$0.getSubject().accept(expressionCodegen, switchGenerator.data));
                    expressionCodegen.getMv().invokevirtual("java/lang/String", "hashCode", "()I", false);
                }
            });
            genIntSwitch(this.hashAndSwitchLabels);
            for (ValueToLabel valueToLabel : this.hashAndSwitchLabels) {
                Object value = valueToLabel.getValue();
                expressionCodegen.getMv().visitLabel(valueToLabel.getLabel());
                ArrayList<ValueToLabel> arrayList = this.hashToStringAndExprLabels.get(value);
                Intrinsics.checkNotNull(arrayList);
                for (ValueToLabel valueToLabel2 : arrayList) {
                    Object value2 = valueToLabel2.getValue();
                    Label label = valueToLabel2.getLabel();
                    expressionCodegen.noLineNumberScope(new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.SwitchGenerator$StringSwitch$genSwitch$1$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            PromisedValueKt.materialize((PromisedValue) this.this$0.getSubject().accept(expressionCodegen, switchGenerator.data));
                        }
                    });
                    expressionCodegen.getMv().aconst(value2);
                    expressionCodegen.getMv().invokevirtual("java/lang/String", Namer.EQUALS_METHOD_NAME, "(Ljava/lang/Object;)Z", false);
                    expressionCodegen.getMv().ifne(label);
                }
                expressionCodegen.getMv().goTo(getDefaultLabel());
            }
        }
    }
}
