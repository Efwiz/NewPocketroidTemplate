package org.jetbrains.kotlin.codegen.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MappingsClassesForWhenByEnum {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Set<String> generatedMappingClasses;
    private final MappingClassesForWhenByEnumCodegen mappingsCodegen;
    private final GenerationState state;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "state";
        } else {
            objArr[0] = "expression";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/when/MappingsClassesForWhenByEnum";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "generateMappingsClassForExpression";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public MappingsClassesForWhenByEnum(GenerationState generationState) {
        if (generationState == null) {
            $$$reportNull$$$0(0);
        }
        this.generatedMappingClasses = new HashSet();
        this.state = generationState;
        this.mappingsCodegen = new MappingClassesForWhenByEnumCodegen(generationState);
    }

    public void generateMappingsClassForExpression(KtWhenExpression ktWhenExpression) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(1);
        }
        WhenByEnumsMapping whenByEnumsMapping = (WhenByEnumsMapping) this.state.getBindingContext().get(CodegenBinding.MAPPING_FOR_WHEN_BY_ENUM, ktWhenExpression);
        if (this.generatedMappingClasses.contains(whenByEnumsMapping.getMappingsClassInternalName())) {
            return;
        }
        this.mappingsCodegen.generate((List) this.state.getBindingContext().get(CodegenBinding.MAPPINGS_FOR_WHENS_BY_ENUM_IN_CLASS_FILE, whenByEnumsMapping.getOuterClassInternalNameForExpression()), Type.getObjectType(whenByEnumsMapping.getMappingsClassInternalName()), ktWhenExpression.getContainingKtFile());
        this.generatedMappingClasses.add(whenByEnumsMapping.getMappingsClassInternalName());
    }
}
