package org.jetbrains.kotlin.codegen.when;

import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.psi.KtWhenEntry;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class EnumSwitchCodegen extends SwitchCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final WhenByEnumsMapping mapping;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "codegen";
        } else if (i == 2) {
            objArr[0] = "mapping";
        } else if (i == 3) {
            objArr[0] = "constant";
        } else if (i == 4) {
            objArr[0] = "entryLabel";
        } else if (i != 5) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "entry";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/when/EnumSwitchCodegen";
        if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "processConstant";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EnumSwitchCodegen(KtWhenExpression ktWhenExpression, boolean z, boolean z2, ExpressionCodegen expressionCodegen, WhenByEnumsMapping whenByEnumsMapping) {
        super(ktWhenExpression, z, z2, expressionCodegen, expressionCodegen.getState().getTypeMapper().mapType(whenByEnumsMapping.getEnumClassDescriptor()));
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(1);
        }
        if (whenByEnumsMapping == null) {
            $$$reportNull$$$0(2);
        }
        this.mapping = whenByEnumsMapping;
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void generateSubjectValueToIndex() {
        this.codegen.getState().getMappingsClassesForWhenByEnum().generateMappingsClassForExpression(this.expression);
        generateNullCheckIfNeeded();
        this.v.getstatic(this.mapping.getMappingsClassInternalName(), this.mapping.getFieldName(), MappingClassesForWhenByEnumCodegen.MAPPINGS_FIELD_DESCRIPTOR);
        this.v.swap();
        this.v.invokevirtual(this.codegen.getState().getTypeMapper().mapClass(this.mapping.getEnumClassDescriptor()).getInternalName(), "ordinal", Type.getMethodDescriptor(Type.INT_TYPE, new Type[0]), false);
        this.v.aload(Type.INT_TYPE);
    }

    @Override // org.jetbrains.kotlin.codegen.when.SwitchCodegen
    protected void processConstant(ConstantValue<?> constantValue, Label label, KtWhenEntry ktWhenEntry) {
        if (constantValue == null) {
            $$$reportNull$$$0(3);
        }
        if (label == null) {
            $$$reportNull$$$0(4);
        }
        if (ktWhenEntry == null) {
            $$$reportNull$$$0(5);
        }
        putTransitionOnce(this.mapping.getIndexByEntry((EnumValue) constantValue), label);
    }
}
