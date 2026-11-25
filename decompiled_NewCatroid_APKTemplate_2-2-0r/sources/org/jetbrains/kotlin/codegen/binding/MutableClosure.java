package org.jetbrains.kotlin.codegen.binding;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public final class MutableClosure implements CalculatedClosure {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean captureEnclosingReceiver;
    private KotlinType captureReceiverType;
    private boolean captureThis;
    private Map<DeclarationDescriptor, EnclosedValueDescriptor> captureVariables;
    private final ClassDescriptor closureClass;
    private final ClassDescriptor enclosingClass;
    private final CallableDescriptor enclosingFunWithReceiverDescriptor;
    private boolean isSuspend;
    private boolean isSuspendLambda;
    private Map<DeclarationDescriptor, Integer> parameterOffsetInConstructor;
    private List<Pair<String, Type>> recordedFields;

    /* JADX WARN: Code duplicated, block: B:24:0x0036  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5 || i == 6) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/codegen/binding/MutableClosure";
        } else if (i == 4) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else if (i == 5 || i == 6) {
            objArr[0] = "org/jetbrains/kotlin/codegen/binding/MutableClosure";
        } else {
            objArr[0] = "classDescriptor";
        }
        if (i == 2) {
            objArr[1] = "getClosureClass";
        } else if (i == 3) {
            objArr[1] = "getCapturedReceiverFieldName";
        } else if (i == 5) {
            objArr[1] = "getCaptureVariables";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/codegen/binding/MutableClosure";
        } else {
            objArr[1] = "getRecordedFields";
        }
        switch (i) {
            case 1:
                objArr[2] = "enclosingExtensionMemberForClass";
                break;
            case 2:
            case 3:
            case 5:
            case 6:
                break;
            case 4:
                objArr[2] = "setCustomCapturedReceiverType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    MutableClosure(ClassDescriptor classDescriptor, ClassDescriptor classDescriptor2) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        this.closureClass = classDescriptor;
        this.enclosingClass = classDescriptor2;
        this.enclosingFunWithReceiverDescriptor = enclosingExtensionMemberForClass(classDescriptor);
    }

    private static CallableDescriptor enclosingExtensionMemberForClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        DeclarationDescriptor containingModule = classDescriptor.getContainingDeclaration();
        if (!(containingModule instanceof CallableMemberDescriptor)) {
            return null;
        }
        CallableMemberDescriptor directMember = JvmCodegenUtil.getDirectMember((CallableMemberDescriptor) containingModule);
        if (directMember.getExtensionReceiverParameter() != null) {
            return directMember;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public ClassDescriptor getClosureClass() {
        ClassDescriptor classDescriptor = this.closureClass;
        if (classDescriptor == null) {
            $$$reportNull$$$0(2);
        }
        return classDescriptor;
    }

    public ClassDescriptor getEnclosingClass() {
        return this.enclosingClass;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public ClassDescriptor getCapturedOuterClassDescriptor() {
        if (this.captureThis) {
            return this.enclosingClass;
        }
        return null;
    }

    public void setNeedsCaptureOuterClass() {
        this.captureThis = true;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public KotlinType getCapturedReceiverFromOuterContext() {
        KotlinType kotlinType = this.captureReceiverType;
        if (kotlinType != null) {
            return kotlinType;
        }
        if (this.captureEnclosingReceiver) {
            return getEnclosingCallableDescriptorWithReceiver().getExtensionReceiverParameter().getType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public String getCapturedReceiverFieldName(BindingContext bindingContext, LanguageVersionSettings languageVersionSettings) {
        if (this.captureReceiverType != null) {
            return AsmUtil.CAPTURED_RECEIVER_FIELD;
        }
        if (this.enclosingFunWithReceiverDescriptor != null) {
            if (!languageVersionSettings.supportsFeature(LanguageFeature.NewCapturedReceiverFieldNamingConvention)) {
                return AsmUtil.CAPTURED_RECEIVER_FIELD;
            }
            String capturedFieldName = AsmUtil.getCapturedFieldName(DescriptorAsmUtil.getNameForCapturedReceiverField(this.enclosingFunWithReceiverDescriptor, bindingContext, languageVersionSettings));
            if (capturedFieldName == null) {
                $$$reportNull$$$0(3);
            }
            return capturedFieldName;
        }
        throw new IllegalStateException("Closure does not capture an outer receiver");
    }

    public void setNeedsCaptureReceiverFromOuterContext() {
        if (this.enclosingFunWithReceiverDescriptor == null) {
            throw new IllegalStateException("Extension receiver parameter should exist");
        }
        this.captureEnclosingReceiver = true;
    }

    public void setCustomCapturedReceiverType(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        this.captureReceiverType = kotlinType;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public Map<DeclarationDescriptor, EnclosedValueDescriptor> getCaptureVariables() {
        Map<DeclarationDescriptor, EnclosedValueDescriptor> mapEmptyMap = this.captureVariables;
        if (mapEmptyMap == null) {
            mapEmptyMap = Collections.emptyMap();
        }
        if (mapEmptyMap == null) {
            $$$reportNull$$$0(5);
        }
        return mapEmptyMap;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public List<Pair<String, Type>> getRecordedFields() {
        List<Pair<String, Type>> listEmptyList = this.recordedFields;
        if (listEmptyList == null) {
            listEmptyList = Collections.emptyList();
        }
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public boolean isSuspend() {
        return this.isSuspend;
    }

    public void setSuspend(boolean z) {
        this.isSuspend = z;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public boolean isSuspendLambda() {
        return this.isSuspendLambda;
    }

    public void setSuspendLambda() {
        this.isSuspendLambda = true;
    }

    private void recordField(String str, Type type) {
        if (this.recordedFields == null) {
            this.recordedFields = new LinkedList();
        }
        this.recordedFields.add(new Pair<>(str, type));
    }

    public void captureVariable(EnclosedValueDescriptor enclosedValueDescriptor) {
        recordField(enclosedValueDescriptor.getFieldName(), enclosedValueDescriptor.getType());
        if (this.captureVariables == null) {
            this.captureVariables = new LinkedHashMap();
        }
        this.captureVariables.put(enclosedValueDescriptor.getDescriptor(), enclosedValueDescriptor);
    }

    public void setCapturedParameterOffsetInConstructor(DeclarationDescriptor declarationDescriptor, int i) {
        if (this.parameterOffsetInConstructor == null) {
            this.parameterOffsetInConstructor = new LinkedHashMap();
        }
        this.parameterOffsetInConstructor.put(declarationDescriptor, Integer.valueOf(i));
    }

    public int getCapturedParameterOffsetInConstructor(DeclarationDescriptor declarationDescriptor) {
        Map<DeclarationDescriptor, Integer> map = this.parameterOffsetInConstructor;
        Integer num = map != null ? map.get(declarationDescriptor) : null;
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.codegen.binding.CalculatedClosure
    public CallableDescriptor getEnclosingCallableDescriptorWithReceiver() {
        return this.enclosingFunWithReceiverDescriptor;
    }
}
