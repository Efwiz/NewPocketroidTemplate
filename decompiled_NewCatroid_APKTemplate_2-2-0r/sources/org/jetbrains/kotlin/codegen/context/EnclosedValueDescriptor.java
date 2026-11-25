package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public final class EnclosedValueDescriptor {
    private final DeclarationDescriptor descriptor;
    private final String fieldName;
    private final StackValue.StackValueWithSimpleReceiver innerValue;
    private final StackValue instanceValue;
    private final KotlinType kotlinType;
    private final Type type;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "innerValue";
                break;
            case 2:
            case 6:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
                objArr[0] = "name";
                break;
            case 5:
                objArr[0] = "instanceValue";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/codegen/context/EnclosedValueDescriptor";
                break;
            default:
                objArr[0] = "fieldName";
                break;
        }
        switch (i) {
            case 7:
                objArr[1] = "getFieldName";
                break;
            case 8:
                objArr[1] = "getInnerValue";
                break;
            case 9:
                objArr[1] = "getInstanceValue";
                break;
            case 10:
                objArr[1] = "getType";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/codegen/context/EnclosedValueDescriptor";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 9:
            case 10:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public EnclosedValueDescriptor(String str, DeclarationDescriptor declarationDescriptor, StackValue.StackValueWithSimpleReceiver stackValueWithSimpleReceiver, Type type, KotlinType kotlinType) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (stackValueWithSimpleReceiver == null) {
            $$$reportNull$$$0(1);
        }
        if (type == null) {
            $$$reportNull$$$0(2);
        }
        this.fieldName = str;
        this.descriptor = declarationDescriptor;
        this.innerValue = stackValueWithSimpleReceiver;
        this.instanceValue = stackValueWithSimpleReceiver;
        this.type = type;
        this.kotlinType = kotlinType;
    }

    public EnclosedValueDescriptor(String str, DeclarationDescriptor declarationDescriptor, StackValue.StackValueWithSimpleReceiver stackValueWithSimpleReceiver, StackValue.Field field, Type type, KotlinType kotlinType) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (stackValueWithSimpleReceiver == null) {
            $$$reportNull$$$0(4);
        }
        if (field == null) {
            $$$reportNull$$$0(5);
        }
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        this.fieldName = str;
        this.descriptor = declarationDescriptor;
        this.innerValue = stackValueWithSimpleReceiver;
        this.instanceValue = field;
        this.type = type;
        this.kotlinType = kotlinType;
    }

    public String getFieldName() {
        String str = this.fieldName;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    public DeclarationDescriptor getDescriptor() {
        return this.descriptor;
    }

    public StackValue.StackValueWithSimpleReceiver getInnerValue() {
        StackValue.StackValueWithSimpleReceiver stackValueWithSimpleReceiver = this.innerValue;
        if (stackValueWithSimpleReceiver == null) {
            $$$reportNull$$$0(8);
        }
        return stackValueWithSimpleReceiver;
    }

    public StackValue getInstanceValue() {
        StackValue stackValue = this.instanceValue;
        if (stackValue == null) {
            $$$reportNull$$$0(9);
        }
        return stackValue;
    }

    public Type getType() {
        Type type = this.type;
        if (type == null) {
            $$$reportNull$$$0(10);
        }
        return type;
    }

    public KotlinType getKotlinType() {
        return this.kotlinType;
    }

    public String toString() {
        return this.fieldName + " " + this.type + " -> " + this.descriptor;
    }
}
