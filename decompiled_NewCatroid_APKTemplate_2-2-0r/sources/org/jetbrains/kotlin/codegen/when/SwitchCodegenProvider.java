package org.jetbrains.kotlin.codegen.when;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtWhenCondition;
import org.jetbrains.kotlin.psi.KtWhenConditionWithExpression;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.IntegerValueConstant;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.UnsignedValueConstant;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: SwitchCodegenProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B!\b\u0002\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\fJ \u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000bJ&\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0016\u0010\u0014\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0016\u0012\u0004\u0012\u00020\u000b0\u0015J\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00160\u00182\u0006\u0010\u000f\u001a\u00020\u0010J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00160\u00182\u0006\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0018\u0010\u001f\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010 \u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010!\u001a\u00020\"*\u000e\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u00160#2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/codegen/when/SwitchCodegenProvider;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "shouldInlineConstVals", "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;ZLorg/jetbrains/kotlin/codegen/ExpressionCodegen;)V", "buildAppropriateSwitchCodegenIfPossible", "Lorg/jetbrains/kotlin/codegen/when/SwitchCodegen;", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "isStatement", "isExhaustive", "checkAllItemsAreConstantsSatisfying", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllConstants", "", "getConstantsFromEntry", "entry", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "isIntegralConstantsSwitch", "subjectType", "Lorg/jetbrains/org/objectweb/asm/Type;", "isStringConstantsSwitch", "isThereConstantEntriesButNulls", "addConstantsFromConditions", "", "Ljava/util/ArrayList;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SwitchCodegenProvider {
    private final BindingContext bindingContext;
    private final ExpressionCodegen codegen;
    private final boolean shouldInlineConstVals;

    private SwitchCodegenProvider(BindingContext bindingContext, boolean z, ExpressionCodegen expressionCodegen) {
        this.bindingContext = bindingContext;
        this.shouldInlineConstVals = z;
        this.codegen = expressionCodegen;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SwitchCodegenProvider(GenerationState state) {
        this(state.getBindingContext(), state.getConfig().getShouldInlineConstVals(), null);
        Intrinsics.checkNotNullParameter(state, "state");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SwitchCodegenProvider(ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this(bindingContext, codegen.getState().getConfig().getShouldInlineConstVals(), codegen);
    }

    public final boolean checkAllItemsAreConstantsSatisfying(KtWhenExpression expression, Function1<? super ConstantValue<?>, Boolean> predicate) {
        KtExpression expression2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        List<KtWhenEntry> entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        List<KtWhenEntry> list = entries;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            KtWhenCondition[] conditions = ((KtWhenEntry) it.next()).getConditions();
            Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
            for (KtWhenCondition ktWhenCondition : conditions) {
                if (!(ktWhenCondition instanceof KtWhenConditionWithExpression) || (expression2 = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression()) == null) {
                    return false;
                }
                Intrinsics.checkNotNull(expression2);
                ConstantValue<?> compileTimeConstant = ExpressionCodegen.getCompileTimeConstant(expression2, this.bindingContext, this.shouldInlineConstVals);
                if (compileTimeConstant == null) {
                    return false;
                }
                Intrinsics.checkNotNull(compileTimeConstant);
                if (!predicate.invoke(compileTimeConstant).booleanValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public final Iterable<ConstantValue<?>> getAllConstants(KtWhenExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ArrayList<ConstantValue<?>> arrayList = new ArrayList<>();
        for (KtWhenEntry ktWhenEntry : expression.getEntries()) {
            Intrinsics.checkNotNull(ktWhenEntry);
            addConstantsFromConditions(arrayList, ktWhenEntry);
        }
        return arrayList;
    }

    public final Iterable<ConstantValue<?>> getConstantsFromEntry(KtWhenEntry entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        ArrayList<ConstantValue<?>> arrayList = new ArrayList<>();
        addConstantsFromConditions(arrayList, entry);
        return arrayList;
    }

    private final void addConstantsFromConditions(ArrayList<ConstantValue<?>> arrayList, KtWhenEntry ktWhenEntry) {
        KtWhenCondition[] conditions = ktWhenEntry.getConditions();
        Intrinsics.checkNotNullExpressionValue(conditions, "getConditions(...)");
        for (KtWhenCondition ktWhenCondition : conditions) {
            if (ktWhenCondition instanceof KtWhenConditionWithExpression) {
                KtExpression expression = ((KtWhenConditionWithExpression) ktWhenCondition).getExpression();
                if (expression == null) {
                    throw new AssertionError("expression in when should not be null");
                }
                arrayList.add(ExpressionCodegen.getCompileTimeConstant(expression, this.bindingContext, this.shouldInlineConstVals));
            }
        }
    }

    public final SwitchCodegen buildAppropriateSwitchCodegenIfPossible(KtWhenExpression expression, boolean isStatement, boolean isExhaustive) {
        Type typeExpressionType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        ExpressionCodegen expressionCodegen = this.codegen;
        if (expressionCodegen == null) {
            throw new AssertionError("Can't create SwitchCodegen in this context");
        }
        if (!isThereConstantEntriesButNulls(expression)) {
            return null;
        }
        KtProperty subjectVariable = expression.getSubjectVariable();
        if (subjectVariable == null || (typeExpressionType = expressionCodegen.expressionType(subjectVariable.getInitializer())) == null) {
            typeExpressionType = expressionCodegen.expressionType(expression.getSubjectExpression());
        }
        Intrinsics.checkNotNull(typeExpressionType);
        WhenByEnumsMapping whenByEnumsMapping = (WhenByEnumsMapping) expressionCodegen.getBindingContext().get(CodegenBinding.MAPPING_FOR_WHEN_BY_ENUM, expression);
        if (whenByEnumsMapping != null) {
            return new EnumSwitchCodegen(expression, isStatement, isExhaustive, expressionCodegen, whenByEnumsMapping);
        }
        if (isIntegralConstantsSwitch(expression, typeExpressionType)) {
            return new IntegralConstantsSwitchCodegen(expression, isStatement, isExhaustive, expressionCodegen);
        }
        if (isStringConstantsSwitch(expression, typeExpressionType)) {
            return new StringSwitchCodegen(expression, isStatement, isExhaustive, expressionCodegen);
        }
        return null;
    }

    private final boolean isThereConstantEntriesButNulls(KtWhenExpression expression) {
        Iterable<ConstantValue<?>> allConstants = getAllConstants(expression);
        if ((allConstants instanceof Collection) && ((Collection) allConstants).isEmpty()) {
            return false;
        }
        for (ConstantValue<?> constantValue : allConstants) {
            if (constantValue != null && !(constantValue instanceof NullValue)) {
                return true;
            }
        }
        return false;
    }

    private final boolean isIntegralConstantsSwitch(KtWhenExpression expression, Type subjectType) {
        return AsmUtil.isIntPrimitive(subjectType) && checkAllItemsAreConstantsSatisfying(expression, new Function1<ConstantValue<?>, Boolean>() { // from class: org.jetbrains.kotlin.codegen.when.SwitchCodegenProvider.isIntegralConstantsSwitch.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConstantValue<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof IntegerValueConstant) || (it instanceof UnsignedValueConstant));
            }
        });
    }

    private final boolean isStringConstantsSwitch(KtWhenExpression expression, Type subjectType) {
        return Intrinsics.areEqual(subjectType.getClassName(), String.class.getName()) && checkAllItemsAreConstantsSatisfying(expression, new Function1<ConstantValue<?>, Boolean>() { // from class: org.jetbrains.kotlin.codegen.when.SwitchCodegenProvider.isStringConstantsSwitch.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConstantValue<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof StringValue) || (it instanceof NullValue));
            }
        });
    }
}
