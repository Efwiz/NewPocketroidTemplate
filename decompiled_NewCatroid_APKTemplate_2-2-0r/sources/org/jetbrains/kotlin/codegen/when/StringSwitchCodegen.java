package org.jetbrains.kotlin.codegen.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class StringSwitchCodegen extends SwitchCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Map<Integer, List<Entry>> hashCodesToEntries;
    private int tempVarIndex;
    private static final String HASH_CODE_METHOD_DESC = Type.getMethodDescriptor(Type.INT_TYPE, new Type[0]);
    private static final String EQUALS_METHOD_DESC = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getType((Class<?>) Object.class));

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "codegen";
        } else if (i == 2) {
            objArr[0] = "constant";
        } else if (i == 3) {
            objArr[0] = "entryLabel";
        } else if (i != 4) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "entry";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/when/StringSwitchCodegen";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "processConstant";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StringSwitchCodegen(KtWhenExpression ktWhenExpression, boolean z, boolean z2, ExpressionCodegen expressionCodegen) {
        super(ktWhenExpression, z, z2, expressionCodegen, null);
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(1);
        }
        this.hashCodesToEntries = new HashMap();
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void processConstant(ConstantValue<?> constantValue, Label label, KtWhenEntry ktWhenEntry) {
        List<Entry> arrayList;
        if (constantValue == null) {
            $$$reportNull$$$0(2);
        }
        if (label == null) {
            $$$reportNull$$$0(3);
        }
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(4);
        }
        int iHashCode = constantValue.hashCode();
        if (!this.transitionsTable.containsKey(Integer.valueOf(iHashCode))) {
            this.transitionsTable.put(Integer.valueOf(iHashCode), new Label());
            arrayList = new ArrayList<>();
            this.hashCodesToEntries.put(Integer.valueOf(iHashCode), arrayList);
        } else {
            arrayList = this.hashCodesToEntries.get(Integer.valueOf(iHashCode));
        }
        arrayList.add(new Entry(((StringValue) constantValue).getValue(), label, ktWhenEntry));
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    public void generate() {
        super.generate();
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void generateSubjectValueToIndex() {
        generateNullCheckIfNeeded();
        this.tempVarIndex = this.codegen.myFrameMap.enterTemp(this.subjectType);
        this.v.store(this.tempVarIndex, this.subjectType);
        this.v.load(this.tempVarIndex, this.subjectType);
        this.v.invokevirtual(this.subjectType.getInternalName(), "hashCode", HASH_CODE_METHOD_DESC, false);
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void generateEntries() {
        Label label;
        Iterator<Integer> it = this.hashCodesToEntries.keySet().iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            this.v.visitLabel((Label) this.transitionsTable.get(Integer.valueOf(iIntValue)));
            List<Entry> list = this.hashCodesToEntries.get(Integer.valueOf(iIntValue));
            Label label2 = null;
            int i = 0;
            while (i < list.size()) {
                if (label2 != null) {
                    this.v.visitLabel(label2);
                }
                Entry entry = list.get(i);
                this.codegen.markLineNumber(entry.entry, false);
                this.v.load(this.tempVarIndex, this.subjectType);
                this.v.aconst(entry.value);
                this.v.invokevirtual(this.subjectType.getInternalName(), Namer.EQUALS_METHOD_NAME, EQUALS_METHOD_DESC, false);
                i++;
                if (i < list.size()) {
                    label = new Label();
                } else {
                    label = this.defaultLabel;
                }
                this.v.ifeq(label);
                this.v.goTo(entry.label);
                label2 = label;
            }
        }
        super.generateEntries();
    }

    private static class Entry {
        private final KtWhenEntry entry;
        private final Label label;
        private final String value;

        private Entry(String str, Label label, KtWhenEntry ktWhenEntry) {
            this.value = str;
            this.label = label;
            this.entry = ktWhenEntry;
        }
    }
}
