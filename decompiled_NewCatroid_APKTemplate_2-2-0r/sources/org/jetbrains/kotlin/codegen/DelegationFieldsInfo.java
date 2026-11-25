package org.jetbrains.kotlin.codegen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class DelegationFieldsInfo {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BindingContext bindingContext;
    private final Type classAsmType;
    private final ClassDescriptor classDescriptor;
    private final Map<KtDelegatedSuperTypeEntry, Field> fields;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 5 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 5 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "descriptor";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i == 3) {
            objArr[0] = "context";
        } else if (i == 4) {
            objArr[0] = "delegationSpecifiers";
        } else if (i != 5) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = "org/jetbrains/kotlin/codegen/DelegationFieldsInfo";
        }
        if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/codegen/DelegationFieldsInfo";
        } else {
            objArr[1] = "getDelegationFieldsInfo";
        }
        if (i == 4) {
            objArr[2] = "getDelegationFieldsInfo";
        } else if (i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 5) {
            throw new IllegalStateException(str2);
        }
    }

    public DelegationFieldsInfo(Type type, ClassDescriptor classDescriptor, GenerationState generationState, BindingContext bindingContext) {
        if (type == null) {
            $$$reportNull$$$0(0);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (generationState == null) {
            $$$reportNull$$$0(2);
        }
        if (bindingContext == null) {
            $$$reportNull$$$0(3);
        }
        this.fields = new HashMap();
        this.classAsmType = type;
        this.classDescriptor = classDescriptor;
        this.state = generationState;
        this.typeMapper = generationState.getTypeMapper();
        this.bindingContext = bindingContext;
    }

    public class Field {
        public final boolean generateField;
        public final String genericSignature;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f696name;
        public final Type type;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/codegen/DelegationFieldsInfo$Field", "getStackValue"));
        }

        private Field(Type type, String str, boolean z, String str2) {
            this.type = type;
            this.f696name = str;
            this.generateField = z;
            this.genericSignature = str2;
        }

        private Field(DelegationFieldsInfo delegationFieldsInfo, Type type, String str, boolean z) {
            this(type, str, z, (String) null);
        }

        public StackValue getStackValue() {
            StackValue.Field field = StackValue.field(this.type, DelegationFieldsInfo.this.classAsmType, this.f696name, false, StackValue.none());
            if (field == null) {
                $$$reportNull$$$0(0);
            }
            return field;
        }
    }

    public Field getInfo(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry) {
        return this.fields.get(ktDelegatedSuperTypeEntry);
    }

    private void addField(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, PropertyDescriptor propertyDescriptor) {
        this.fields.put(ktDelegatedSuperTypeEntry, new Field(this.typeMapper.mapType(propertyDescriptor), propertyDescriptor.getShortName().asString(), false));
    }

    private void addField(KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry, Type type, String str, String str2) {
        this.fields.put(ktDelegatedSuperTypeEntry, new Field(type, str, true, str2));
    }

    public DelegationFieldsInfo getDelegationFieldsInfo(List<KtSuperTypeListEntry> list) {
        KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry;
        KtExpression delegateExpression;
        Type typeMapType;
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        DelegationFieldsInfo delegationFieldsInfo = new DelegationFieldsInfo(this.classAsmType, this.classDescriptor, this.state, this.bindingContext);
        int i = 0;
        for (KtSuperTypeListEntry ktSuperTypeListEntry : list) {
            if ((ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) && (delegateExpression = (ktDelegatedSuperTypeEntry = (KtDelegatedSuperTypeEntry) ktSuperTypeListEntry).getDelegateExpression()) != null) {
                PropertyDescriptor delegatePropertyIfAny = CodegenUtil.getDelegatePropertyIfAny(delegateExpression, this.classDescriptor, this.bindingContext);
                if (CodegenUtil.isFinalPropertyWithBackingField(delegatePropertyIfAny, this.bindingContext)) {
                    delegationFieldsInfo.addField(ktDelegatedSuperTypeEntry, delegatePropertyIfAny);
                } else {
                    KotlinType type = this.bindingContext.getType(delegateExpression);
                    ClassDescriptor superClass = JvmCodegenUtil.getSuperClass(ktSuperTypeListEntry, this.state, this.bindingContext);
                    BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.TYPE);
                    if (type != null) {
                        typeMapType = this.typeMapper.mapType(type, bothSignatureWriter);
                    } else {
                        typeMapType = superClass != null ? this.typeMapper.mapType(superClass.getDefaultType(), bothSignatureWriter) : null;
                    }
                    if (typeMapType != null) {
                        delegationFieldsInfo.addField(ktDelegatedSuperTypeEntry, typeMapType, JvmAbi.DELEGATE_SUPER_FIELD_PREFIX + i, bothSignatureWriter.makeJavaGenericSignature());
                    }
                }
                i++;
            }
        }
        return delegationFieldsInfo;
    }
}
