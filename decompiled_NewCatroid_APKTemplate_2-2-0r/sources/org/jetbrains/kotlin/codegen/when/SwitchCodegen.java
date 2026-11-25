package org.jetbrains.kotlin.codegen.when;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cfg.WhenChecker;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FrameMapBase;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: SwitchCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 K2\u00020\u0001:\u0001KB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010:\u001a\u00020)2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u0010;\u001a\u00020<H\u0016J\b\u0010=\u001a\u00020<H\u0014J\b\u0010>\u001a\u00020<H\u0004J\b\u0010?\u001a\u00020<H\u0002J\b\u0010@\u001a\u00020<H$J\b\u0010A\u001a\u00020<H\u0002J\b\u0010B\u001a\u00020<H\u0002J$\u0010C\u001a\u00020<2\n\u0010D\u001a\u0006\u0012\u0002\b\u00030E2\u0006\u0010F\u001a\u00020\u00112\u0006\u0010G\u001a\u00020HH$J\u0018\u0010I\u001a\u00020<2\u0006\u0010J\u001a\u00020)2\u0006\u0010F\u001a\u00020\u0011H\u0004R\u0014\u0010\f\u001a\u00020\rX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0007\u001a\u00020\b8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0014\u0010\u001f\u001a\u00020\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\u001cX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001eR\u001a\u0010(\u001a\u00020)X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0010\u0010\t\u001a\u00020\n8\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0016\u0010.\u001a\u0004\u0018\u00010/X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u000205X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u00106\u001a\u000e\u0012\u0004\u0012\u00020)\u0012\u0004\u0012\u00020\u0011078\u0004X\u0085\u0004¢\u0006\u0002\n\u0000R\u0010\u00108\u001a\u0002098\u0004X\u0085\u0004¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lorg/jetbrains/kotlin/codegen/when/SwitchCodegen;", "", "expression", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "isStatement", "", "isExhaustive", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "subjectType", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/kotlin/psi/KtWhenExpression;ZZLorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/org/objectweb/asm/Type;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "defaultLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "getDefaultLabel", "()Lorg/jetbrains/org/objectweb/asm/Label;", "setDefaultLabel", "(Lorg/jetbrains/org/objectweb/asm/Label;)V", "elseLabel", "endLabel", "entryLabels", "", "()Z", "resultKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getResultKotlinType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "resultType", "getResultType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "subjectExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getSubjectExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", "subjectKotlinType", "getSubjectKotlinType", "subjectLocal", "", "getSubjectLocal", "()I", "setSubjectLocal", "(I)V", "subjectVariable", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getSubjectVariable", "()Lorg/jetbrains/kotlin/psi/KtProperty;", "subjectVariableDescriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "switchCodegenProvider", "Lorg/jetbrains/kotlin/codegen/when/SwitchCodegenProvider;", "transitionsTable", "Ljava/util/NavigableMap;", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "findNullEntryIndex", CompilerOptions.GENERATE, "", "generateEntries", "generateNullCheckIfNeeded", "generateSubjectValue", "generateSubjectValueToIndex", "generateSwitchInstructionByTransitionsTable", "prepareConfiguration", "processConstant", "constant", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "entryLabel", "entry", "Lorg/jetbrains/kotlin/psi/KtWhenEntry;", "putTransitionOnce", "value", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SwitchCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BindingContext bindingContext;
    protected final ExpressionCodegen codegen;
    protected Label defaultLabel;
    private Label elseLabel;
    private Label endLabel;
    private final List<Label> entryLabels;
    protected final KtWhenExpression expression;
    private final boolean isExhaustive;
    private final boolean isStatement;
    private final KotlinType resultKotlinType;
    private final Type resultType;
    private final KtExpression subjectExpression;
    private final KotlinType subjectKotlinType;
    private int subjectLocal;
    protected final Type subjectType;
    private final KtProperty subjectVariable;
    private VariableDescriptor subjectVariableDescriptor;
    private final SwitchCodegenProvider switchCodegenProvider;
    protected final NavigableMap<Integer, Label> transitionsTable;
    protected final InstructionAdapter v;

    protected abstract void generateSubjectValueToIndex();

    protected abstract void processConstant(ConstantValue<?> constant, Label entryLabel, KtWhenEntry entry);

    public SwitchCodegen(KtWhenExpression expression, boolean z, boolean z2, ExpressionCodegen codegen, Type type) {
        Type typeExpressionType;
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        this.expression = expression;
        this.isStatement = z;
        this.isExhaustive = z2;
        this.codegen = codegen;
        BindingContext bindingContext = codegen.getBindingContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        this.bindingContext = bindingContext;
        this.subjectVariable = expression.getSubjectVariable();
        KtExpression subjectExpression = expression.getSubjectExpression();
        if (subjectExpression == null) {
            throw new AssertionError("No subject expression: " + expression.getText());
        }
        this.subjectExpression = subjectExpression;
        KotlinType kotlinTypeWhenSubjectTypeWithoutSmartCasts = WhenChecker.INSTANCE.whenSubjectTypeWithoutSmartCasts(expression, bindingContext);
        if (kotlinTypeWhenSubjectTypeWithoutSmartCasts == null) {
            throw new AssertionError("No subject type: " + expression);
        }
        this.subjectKotlinType = kotlinTypeWhenSubjectTypeWithoutSmartCasts;
        if (type == null) {
            type = codegen.asmType(kotlinTypeWhenSubjectTypeWithoutSmartCasts);
            Intrinsics.checkNotNullExpressionValue(type, "asmType(...)");
        }
        this.subjectType = type;
        this.subjectLocal = -1;
        this.resultKotlinType = !z ? codegen.kotlinType(expression) : null;
        if (z) {
            typeExpressionType = Type.VOID_TYPE;
            str = "VOID_TYPE";
        } else {
            typeExpressionType = codegen.expressionType(expression);
            str = "expressionType(...)";
        }
        Intrinsics.checkNotNullExpressionValue(typeExpressionType, str);
        this.resultType = typeExpressionType;
        InstructionAdapter v = codegen.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        this.v = v;
        this.transitionsTable = new TreeMap();
        this.entryLabels = new ArrayList();
        this.elseLabel = new Label();
        this.endLabel = new Label();
        this.switchCodegenProvider = new SwitchCodegenProvider(codegen);
    }

    /* JADX INFO: renamed from: isStatement, reason: from getter */
    protected final boolean getIsStatement() {
        return this.isStatement;
    }

    protected final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    protected final KtProperty getSubjectVariable() {
        return this.subjectVariable;
    }

    protected final KtExpression getSubjectExpression() {
        return this.subjectExpression;
    }

    protected final KotlinType getSubjectKotlinType() {
        return this.subjectKotlinType;
    }

    protected final int getSubjectLocal() {
        return this.subjectLocal;
    }

    protected final void setSubjectLocal(int i) {
        this.subjectLocal = i;
    }

    protected final KotlinType getResultKotlinType() {
        return this.resultKotlinType;
    }

    protected final Type getResultType() {
        return this.resultType;
    }

    protected final Label getDefaultLabel() {
        Label label = this.defaultLabel;
        if (label != null) {
            return label;
        }
        Intrinsics.throwUninitializedPropertyAccessException("defaultLabel");
        return null;
    }

    protected final void setDefaultLabel(Label label) {
        Intrinsics.checkNotNullParameter(label, "<set-?>");
        this.defaultLabel = label;
    }

    public void generate() {
        FrameMapBase<DeclarationDescriptor>.Mark mark = this.codegen.getFrameMap().mark();
        prepareConfiguration();
        boolean z = this.expression.getElseExpression() != null;
        setDefaultLabel((z || !this.isStatement || this.isExhaustive) ? this.elseLabel : this.endLabel);
        generateSubjectValue();
        generateSubjectValueToIndex();
        Label label = new Label();
        this.v.mark(label);
        generateSwitchInstructionByTransitionsTable();
        generateEntries();
        if (!z && (!this.isStatement || this.isExhaustive)) {
            this.v.visitLabel(this.elseLabel);
            this.codegen.putUnitInstanceOntoStackForNonExhaustiveWhen(this.expression, this.isStatement);
        }
        this.codegen.markLineNumber(this.expression, this.isStatement);
        this.v.mark(this.endLabel);
        mark.dropTo();
        VariableDescriptor variableDescriptor = this.subjectVariableDescriptor;
        if (variableDescriptor != null) {
            this.v.visitLocalVariable(variableDescriptor.getShortName().asString(), this.subjectType.getDescriptor(), null, label, this.endLabel, this.subjectLocal);
        }
    }

    private final void prepareConfiguration() {
        for (KtWhenEntry ktWhenEntry : this.expression.getEntries()) {
            Label label = new Label();
            SwitchCodegenProvider switchCodegenProvider = this.switchCodegenProvider;
            Intrinsics.checkNotNull(ktWhenEntry);
            for (ConstantValue<?> constantValue : switchCodegenProvider.getConstantsFromEntry(ktWhenEntry)) {
                if (!(constantValue instanceof NullValue) && constantValue != null) {
                    processConstant(constantValue, label, ktWhenEntry);
                }
            }
            if (ktWhenEntry.isElse()) {
                this.elseLabel = label;
            }
            this.entryLabels.add(label);
        }
    }

    protected final void putTransitionOnce(int value, Label entryLabel) {
        Intrinsics.checkNotNullParameter(entryLabel, "entryLabel");
        if (this.transitionsTable.containsKey(Integer.valueOf(value))) {
            return;
        }
        this.transitionsTable.put(Integer.valueOf(value), entryLabel);
    }

    private final void generateSubjectValue() {
        if (this.subjectVariable != null) {
            VariableDescriptor variableDescriptor = (VariableDescriptor) this.bindingContext.get(BindingContext.VARIABLE, this.subjectVariable);
            if (variableDescriptor == null) {
                throw new AssertionError("Unresolved subject variable: " + this.expression);
            }
            this.subjectLocal = this.codegen.getFrameMap().enter(variableDescriptor, this.subjectType);
            this.codegen.visitProperty(this.subjectVariable, (StackValue) null);
            StackValue.local(this.subjectLocal, this.subjectType, this.subjectKotlinType).put(this.subjectType, this.subjectKotlinType, this.codegen.v);
            this.subjectVariableDescriptor = variableDescriptor;
            return;
        }
        this.codegen.gen(this.subjectExpression, this.subjectType, this.subjectKotlinType);
        this.subjectVariableDescriptor = null;
    }

    protected final void generateNullCheckIfNeeded() {
        if (TypeUtils.isNullableType(this.subjectKotlinType)) {
            int iFindNullEntryIndex = findNullEntryIndex(this.expression);
            Label defaultLabel = iFindNullEntryIndex == -1 ? getDefaultLabel() : this.entryLabels.get(iFindNullEntryIndex);
            Label label = new Label();
            InstructionAdapter instructionAdapter = this.v;
            instructionAdapter.dup();
            instructionAdapter.ifnonnull(label);
            instructionAdapter.pop();
            instructionAdapter.goTo(defaultLabel);
            instructionAdapter.visitLabel(label);
        }
    }

    private final int findNullEntryIndex(KtWhenExpression expression) {
        Object next;
        List<KtWhenEntry> entries = expression.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        Iterator it = CollectionsKt.withIndex(entries).iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            KtWhenEntry ktWhenEntry = (KtWhenEntry) ((IndexedValue) next).component2();
            SwitchCodegenProvider switchCodegenProvider = this.switchCodegenProvider;
            Intrinsics.checkNotNull(ktWhenEntry);
            Iterable<ConstantValue<?>> constantsFromEntry = switchCodegenProvider.getConstantsFromEntry(ktWhenEntry);
            if (!(constantsFromEntry instanceof Collection) || !((Collection) constantsFromEntry).isEmpty()) {
                Iterator<ConstantValue<?>> it2 = constantsFromEntry.iterator();
                while (it2.hasNext()) {
                    if (it2.next() instanceof NullValue) {
                        break loop0;
                    }
                }
            }
        }
        IndexedValue indexedValue = (IndexedValue) next;
        if (indexedValue != null) {
            return indexedValue.getIndex();
        }
        return -1;
    }

    private final void generateSwitchInstructionByTransitionsTable() {
        Set<Integer> setKeySet = this.transitionsTable.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        int[] intArray = CollectionsKt.toIntArray(setKeySet);
        int length = intArray.length;
        int iLast = ArraysKt.last(intArray);
        int iFirst = ArraysKt.first(intArray);
        long j = (((long) iLast) - ((long) iFirst)) + 1;
        if (INSTANCE.preferLookupOverSwitch(length, j)) {
            Collection<Label> collectionValues = this.transitionsTable.values();
            Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
            this.v.lookupswitch(getDefaultLabel(), intArray, (Label[]) collectionValues.toArray(new Label[0]));
            return;
        }
        int i = (int) j;
        Label[] labelArr = new Label[i];
        for (int i2 = 0; i2 < i; i2++) {
            Label defaultLabel = (Label) this.transitionsTable.get(Integer.valueOf(i2 + iFirst));
            if (defaultLabel == null) {
                defaultLabel = getDefaultLabel();
            }
            labelArr[i2] = defaultLabel;
        }
        this.v.tableswitch(iFirst, iLast, getDefaultLabel(), (Label[]) Arrays.copyOf(labelArr, i));
    }

    protected void generateEntries() {
        Iterator<Label> it = this.entryLabels.iterator();
        for (KtWhenEntry ktWhenEntry : this.expression.getEntries()) {
            this.v.visitLabel(it.next());
            FrameMapBase<DeclarationDescriptor>.Mark mark = this.codegen.myFrameMap.mark();
            this.codegen.gen(ktWhenEntry.getExpression(), this.resultType, this.resultKotlinType);
            mark.dropTo();
            if (!ktWhenEntry.isElse()) {
                this.v.goTo(this.endLabel);
            }
        }
    }

    /* JADX INFO: compiled from: SwitchCodegen.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/when/SwitchCodegen$Companion;", "", "()V", "preferLookupOverSwitch", "", "labelsNumber", "", "rangeLength", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean preferLookupOverSwitch(int labelsNumber, long rangeLength) {
            return rangeLength > ((long) labelsNumber) * 2 || rangeLength > 2147483647L;
        }

        private Companion() {
        }
    }
}
