package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsnsKt;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.codegen.state.StaticTypeMapperForOldBackend;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.SyntheticFieldDescriptor;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.ImportedFromObjectCallableDescriptor;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StackValue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Local LOCAL_0 = local(0, AsmTypes.OBJECT_TYPE);
    private static final StackValue UNIT = operation(AsmTypes.UNIT_TYPE, new Function1() { // from class: org.jetbrains.kotlin.codegen.StackValue$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            return StackValue.lambda$static$0((InstructionAdapter) obj);
        }
    });
    private final boolean canHaveSideEffects;
    public final KotlinType kotlinType;
    public final Type type;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 42 || i == 47 || i == 49 || i == 76 || i == 77 || i == 162 || i == 163) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 42 || i == 47 || i == 49 || i == 76 || i == 77 || i == 162 || i == 163) ? 2 : 3];
        switch (i) {
            case 5:
            case 6:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 16:
            case 18:
            case 20:
            case 83:
            case 87:
            case 88:
            case 93:
            case 98:
            case 99:
            case 102:
            case 105:
            case 107:
            case 110:
            case 115:
            case 121:
            case 124:
            case 125:
            case 159:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 7:
            case 9:
            case 11:
            case 21:
            case 22:
            case 23:
            case 25:
            case 27:
            case 32:
            case 33:
            case 35:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 43:
            case 44:
            case 46:
            case 56:
            case 57:
            case 59:
            case 63:
            case 67:
            case 79:
            case 91:
            case 95:
            case 143:
            case 161:
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 15:
            case 17:
            case 137:
            case 139:
                objArr[0] = "value";
                break;
            case 19:
            case 106:
                objArr[0] = "topOfStackType";
                break;
            case 24:
            case 26:
            case 34:
            case 36:
            case 78:
            case 142:
            case 148:
            case 151:
            case 152:
            case 164:
                objArr[0] = "descriptor";
                break;
            case 28:
                objArr[0] = "delegateValue";
                break;
            case 29:
                objArr[0] = "metadataValue";
                break;
            case 30:
            case 134:
                objArr[0] = "variableDescriptor";
                break;
            case 31:
            case 81:
            case 141:
            case 145:
            case 147:
            case 149:
                objArr[0] = "codegen";
                break;
            case 42:
            case 47:
            case 49:
            case 76:
            case 77:
            case 162:
            case 163:
                objArr[0] = "org/jetbrains/kotlin/codegen/StackValue";
                break;
            case 45:
                objArr[0] = "opToken";
                break;
            case 48:
            case 75:
            case 144:
                objArr[0] = "stackValue";
                break;
            case 50:
            case 52:
                objArr[0] = "left";
                break;
            case 51:
            case 53:
                objArr[0] = "right";
                break;
            case 54:
            case 55:
                objArr[0] = "argument";
                break;
            case 58:
            case 62:
            case 66:
            case 70:
            case 74:
            case 80:
            case 129:
            case 146:
                objArr[0] = "receiver";
                break;
            case 60:
            case 64:
            case 68:
            case 100:
                objArr[0] = "owner";
                break;
            case 61:
            case 65:
            case 69:
            case 158:
            case 160:
                objArr[0] = "name";
                break;
            case 71:
            case 135:
                objArr[0] = "field";
                break;
            case 72:
            case 136:
                objArr[0] = "newReceiver";
                break;
            case 73:
                objArr[0] = CompilerOptions.INFO;
                break;
            case 82:
            case 117:
                objArr[0] = "kotlinType";
                break;
            case 84:
            case 94:
            case 116:
            case 153:
            case 155:
            case 157:
                objArr[0] = "typeMapper";
                break;
            case 85:
            case 97:
                objArr[0] = "unboxed";
                break;
            case 86:
            case 96:
                objArr[0] = "boxed";
                break;
            case 89:
                objArr[0] = "boxedType";
                break;
            case 90:
                objArr[0] = "underlyingType";
                break;
            case 92:
                objArr[0] = "targetInlineClassType";
                break;
            case 101:
                objArr[0] = "resultType";
                break;
            case 103:
                objArr[0] = "body";
                break;
            case 104:
            case 109:
            case 112:
            case 114:
            case 120:
            case 123:
                objArr[0] = "toType";
                break;
            case 108:
            case 111:
            case 113:
            case 119:
            case 122:
                objArr[0] = "fromType";
                break;
            case 118:
                objArr[0] = "actualType";
                break;
            case 126:
            case 130:
                objArr[0] = "localType";
                break;
            case 127:
            case 131:
                objArr[0] = "classType";
                break;
            case 128:
            case 132:
                objArr[0] = "fieldName";
                break;
            case 133:
                objArr[0] = "refWrapper";
                break;
            case 138:
            case 140:
                objArr[0] = "castType";
                break;
            case 150:
                objArr[0] = Constants.EXTRA_RESULT_RECEIVER;
                break;
            case 154:
            case 156:
                objArr[0] = "classDescriptor";
                break;
        }
        if (i == 42) {
            objArr[1] = "constant";
        } else if (i == 47) {
            objArr[1] = "cmp";
        } else if (i == 49) {
            objArr[1] = "not";
        } else if (i == 76 || i == 77) {
            objArr[1] = "changeReceiverForFieldAndSharedVar";
        } else if (i == 162 || i == 163) {
            objArr[1] = "sharedTypeForType";
        } else {
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue";
        }
        switch (i) {
            case 4:
            case 5:
                objArr[2] = "moveToTopOfStack";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "put";
                break;
            case 13:
                objArr[2] = "putReceiver";
                break;
            case 14:
                objArr[2] = "dup";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "store";
                break;
            case 19:
            case 20:
                objArr[2] = "storeSelector";
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = "local";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "localDelegate";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "shared";
                break;
            case 37:
            case 38:
                objArr[2] = "onStack";
                break;
            case 39:
                objArr[2] = "integerConstant";
                break;
            case 40:
            case 41:
                objArr[2] = "constant";
                break;
            case 42:
            case 47:
            case 49:
            case 76:
            case 77:
            case 162:
            case 163:
                break;
            case 43:
                objArr[2] = "createDefaultValue";
                break;
            case 44:
                objArr[2] = "createDefaultPrimitiveValue";
                break;
            case 45:
            case 46:
                objArr[2] = "cmp";
                break;
            case 48:
                objArr[2] = "not";
                break;
            case 50:
            case 51:
                objArr[2] = "or";
                break;
            case 52:
            case 53:
                objArr[2] = "and";
                break;
            case 54:
                objArr[2] = "compareIntWithZero";
                break;
            case 55:
                objArr[2] = "compareWithNull";
                break;
            case 56:
                objArr[2] = "arrayElement";
                break;
            case 57:
            case 58:
                objArr[2] = "underlyingValueOfInlineClass";
                break;
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
                objArr[2] = "field";
                break;
            case 75:
                objArr[2] = "changeReceiverForFieldAndSharedVar";
                break;
            case 78:
            case 79:
            case 80:
            case 81:
                objArr[2] = "property";
                break;
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
                objArr[2] = "boxInlineClass";
                break;
            case 88:
            case 89:
            case 90:
                objArr[2] = "invokeBoxMethod";
                break;
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
                objArr[2] = "unboxInlineClass";
                break;
            case 99:
            case 100:
            case 101:
                objArr[2] = "invokeUnboxMethod";
                break;
            case 102:
            case 103:
                objArr[2] = "boxOrUnboxWithNullCheck";
                break;
            case 104:
            case 105:
                objArr[2] = "coerceTo";
                break;
            case 106:
            case 107:
                objArr[2] = "coerceFrom";
                break;
            case 108:
            case 109:
            case 110:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
                objArr[2] = "coerce";
                break;
            case 111:
            case 112:
                objArr[2] = "requiresInlineClassBoxingOrUnboxing";
                break;
            case 113:
            case 114:
            case 115:
            case 116:
                objArr[2] = "coerceInlineClasses";
                break;
            case 117:
            case 118:
                objArr[2] = "isUnboxedInlineClass";
                break;
            case 125:
                objArr[2] = "putUnitInstance";
                break;
            case 126:
            case 127:
            case 128:
            case 129:
                objArr[2] = "receiverWithRefWrapper";
                break;
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
                objArr[2] = "fieldForSharedVar";
                break;
            case 137:
            case 138:
                objArr[2] = "coercion";
                break;
            case 139:
            case 140:
                objArr[2] = "coercionValueForArgumentOfInlineClassConstructor";
                break;
            case 141:
            case 142:
                objArr[2] = "thisOrOuter";
                break;
            case 143:
            case 144:
            case 145:
                objArr[2] = "preIncrement";
                break;
            case 146:
            case 147:
            case 148:
                objArr[2] = "genReceiver";
                break;
            case 149:
                objArr[2] = "putLocalSuspendFunctionOnStack";
                break;
            case 150:
            case 151:
                objArr[2] = "platformStaticCallIfPresent";
                break;
            case 152:
            case 153:
                objArr[2] = "enumEntry";
                break;
            case 154:
            case 155:
                objArr[2] = "singleton";
                break;
            case 156:
            case 157:
            case 158:
                objArr[2] = "createSingletonViaInstance";
                break;
            case 159:
            case 160:
                objArr[2] = "genNonNullAssertForLateinit";
                break;
            case 161:
                objArr[2] = "sharedTypeForType";
                break;
            case 164:
                objArr[2] = "throwUnsupportedComplexOperation";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 42 && i != 47 && i != 49 && i != 76 && i != 77 && i != 162 && i != 163) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public boolean isNonStaticAccess(boolean z) {
        return false;
    }

    public void putReceiver(InstructionAdapter instructionAdapter, boolean z) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(13);
        }
    }

    public abstract void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter);

    static /* synthetic */ Unit lambda$static$0(InstructionAdapter instructionAdapter) {
        instructionAdapter.visitFieldInsn(178, AsmTypes.UNIT_TYPE.getInternalName(), JvmAbi.INSTANCE_FIELD, AsmTypes.UNIT_TYPE.getDescriptor());
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected StackValue(Type type) {
        this(type, null, true);
        if (type == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected StackValue(Type type, boolean z) {
        this(type, null, z);
        if (type == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    protected StackValue(Type type, KotlinType kotlinType) {
        this(type, kotlinType, true);
        if (type == null) {
            $$$reportNull$$$0(2);
        }
    }

    protected StackValue(Type type, KotlinType kotlinType, boolean z) {
        if (type == null) {
            $$$reportNull$$$0(3);
        }
        this.type = type;
        this.kotlinType = kotlinType;
        this.canHaveSideEffects = z;
    }

    public void moveToTopOfStack(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter, int i) {
        if (type == null) {
            $$$reportNull$$$0(4);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(5);
        }
        put(type, kotlinType, instructionAdapter);
    }

    public void put(InstructionAdapter instructionAdapter) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(6);
        }
        put(this.type, null, instructionAdapter, false);
    }

    public void put(Type type, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(7);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(8);
        }
        put(type, null, instructionAdapter, false);
    }

    public void put(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(9);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(10);
        }
        put(type, kotlinType, instructionAdapter, false);
    }

    public void put(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter, boolean z) {
        if (type == null) {
            $$$reportNull$$$0(11);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(12);
        }
        if (!z) {
            putReceiver(instructionAdapter, true);
        }
        putSelector(type, kotlinType, instructionAdapter);
    }

    public void dup(InstructionAdapter instructionAdapter, boolean z) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(14);
        }
        if (Type.VOID_TYPE.equals(this.type)) {
            return;
        }
        AsmUtil.dup(instructionAdapter, this.type);
    }

    public void store(StackValue stackValue, InstructionAdapter instructionAdapter) {
        if (stackValue == null) {
            $$$reportNull$$$0(15);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(16);
        }
        store(stackValue, instructionAdapter, false);
    }

    public boolean canHaveSideEffects() {
        return this.canHaveSideEffects;
    }

    public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
        if (stackValue == null) {
            $$$reportNull$$$0(17);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(18);
        }
        if (!z) {
            putReceiver(instructionAdapter, false);
        }
        stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
        storeSelector(stackValue.type, stackValue.kotlinType, instructionAdapter);
    }

    protected void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(19);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(20);
        }
        throw new UnsupportedOperationException("Cannot store to value " + this);
    }

    public static Local local(int i, Type type) {
        if (type == null) {
            $$$reportNull$$$0(21);
        }
        return new Local(i, type);
    }

    public static Local local(int i, Type type, KotlinType kotlinType) {
        if (type == null) {
            $$$reportNull$$$0(22);
        }
        return new Local(i, type, kotlinType);
    }

    public static StackValue local(int i, Type type, VariableDescriptor variableDescriptor) {
        if (type == null) {
            $$$reportNull$$$0(23);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(24);
        }
        return local(i, type, variableDescriptor, null);
    }

    public static StackValue local(int i, Type type, VariableDescriptor variableDescriptor, KotlinType kotlinType) {
        if (type == null) {
            $$$reportNull$$$0(25);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(26);
        }
        if (variableDescriptor.isLateInit()) {
            return new LateinitLocal(i, type, variableDescriptor.getType(), variableDescriptor.getShortName());
        }
        if (kotlinType == null) {
            kotlinType = variableDescriptor.getType();
        }
        return new Local(i, type, kotlinType);
    }

    public static Delegate localDelegate(Type type, StackValue stackValue, StackValue stackValue2, VariableDescriptorWithAccessors variableDescriptorWithAccessors, ExpressionCodegen expressionCodegen) {
        if (type == null) {
            $$$reportNull$$$0(27);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(28);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(29);
        }
        if (variableDescriptorWithAccessors == null) {
            $$$reportNull$$$0(30);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(31);
        }
        return new Delegate(type, stackValue, stackValue2, variableDescriptorWithAccessors, expressionCodegen);
    }

    public static StackValue shared(int i, Type type) {
        if (type == null) {
            $$$reportNull$$$0(32);
        }
        return new Shared(i, type);
    }

    public static StackValue shared(int i, Type type, VariableDescriptor variableDescriptor) {
        if (type == null) {
            $$$reportNull$$$0(33);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(34);
        }
        return shared(i, type, variableDescriptor, null);
    }

    public static StackValue shared(int i, Type type, VariableDescriptor variableDescriptor, KotlinType kotlinType) {
        if (type == null) {
            $$$reportNull$$$0(35);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(36);
        }
        if (kotlinType == null) {
            kotlinType = variableDescriptor.getType();
        }
        return new Shared(i, type, kotlinType, variableDescriptor.isLateInit(), variableDescriptor.getShortName());
    }

    public static StackValue onStack(Type type) {
        if (type == null) {
            $$$reportNull$$$0(37);
        }
        return onStack(type, null);
    }

    public static StackValue onStack(Type type, KotlinType kotlinType) {
        if (type == null) {
            $$$reportNull$$$0(38);
        }
        return type == Type.VOID_TYPE ? none() : new OnStack(type, kotlinType);
    }

    public static StackValue integerConstant(int i, Type type) {
        if (type == null) {
            $$$reportNull$$$0(39);
        }
        if (type == Type.LONG_TYPE) {
            return constant(Long.valueOf(i), type);
        }
        if (type == Type.BYTE_TYPE || type == Type.SHORT_TYPE || type == Type.INT_TYPE) {
            return constant(Integer.valueOf(i), type);
        }
        if (type == Type.CHAR_TYPE) {
            return constant(Character.valueOf((char) i), type);
        }
        throw new AssertionError("Unexpected integer type: " + type);
    }

    public static StackValue constant(int i) {
        return constant(Integer.valueOf(i), Type.INT_TYPE);
    }

    public static StackValue constant(Object obj, Type type) {
        if (type == null) {
            $$$reportNull$$$0(40);
        }
        return constant(obj, type, null);
    }

    public static StackValue constant(Object obj, Type type, KotlinType kotlinType) {
        if (type == null) {
            $$$reportNull$$$0(41);
        }
        if (type == Type.BOOLEAN_TYPE) {
            BranchedValue branchedValueBooleanConstant = BranchedValue.INSTANCE.booleanConstant(((Boolean) obj).booleanValue());
            if (branchedValueBooleanConstant == null) {
                $$$reportNull$$$0(42);
            }
            return branchedValueBooleanConstant;
        }
        return new Constant(obj, type, kotlinType);
    }

    public static StackValue createDefaultValue(Type type) {
        if (type == null) {
            $$$reportNull$$$0(43);
        }
        if (type.getSort() == 10 || type.getSort() == 9) {
            return constant(null, type);
        }
        return createDefaultPrimitiveValue(type);
    }

    private static StackValue createDefaultPrimitiveValue(Type type) {
        if (type == null) {
            $$$reportNull$$$0(44);
        }
        Object l = 0;
        if (type.getSort() == 1) {
            l = Boolean.FALSE;
        } else if (type.getSort() == 6) {
            l = new Float(0.0d);
        } else if (type.getSort() == 8) {
            l = new Double(0.0d);
        } else if (type.getSort() == 7) {
            l = new Long(0L);
        }
        return constant(l, type);
    }

    public static StackValue cmp(IElementType iElementType, Type type, StackValue stackValue, StackValue stackValue2) {
        if (iElementType == null) {
            $$$reportNull$$$0(45);
        }
        if (type == null) {
            $$$reportNull$$$0(46);
        }
        BranchedValue branchedValueCmp = BranchedValue.INSTANCE.cmp(iElementType, type, stackValue, stackValue2);
        if (branchedValueCmp == null) {
            $$$reportNull$$$0(47);
        }
        return branchedValueCmp;
    }

    public static StackValue not(StackValue stackValue) {
        if (stackValue == null) {
            $$$reportNull$$$0(48);
        }
        StackValue stackValueCreateInvertValue = BranchedValue.INSTANCE.createInvertValue(stackValue);
        if (stackValueCreateInvertValue == null) {
            $$$reportNull$$$0(49);
        }
        return stackValueCreateInvertValue;
    }

    public static StackValue or(StackValue stackValue, StackValue stackValue2) {
        if (stackValue == null) {
            $$$reportNull$$$0(50);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(51);
        }
        return new Or(stackValue, stackValue2);
    }

    public static StackValue and(StackValue stackValue, StackValue stackValue2) {
        if (stackValue == null) {
            $$$reportNull$$$0(52);
        }
        if (stackValue2 == null) {
            $$$reportNull$$$0(53);
        }
        return new And(stackValue, stackValue2);
    }

    public static StackValue compareIntWithZero(StackValue stackValue, int i) {
        if (stackValue == null) {
            $$$reportNull$$$0(54);
        }
        return new BranchedValue(stackValue, null, Type.INT_TYPE, i);
    }

    public static StackValue compareWithNull(StackValue stackValue, int i) {
        if (stackValue == null) {
            $$$reportNull$$$0(55);
        }
        return new BranchedValue(stackValue, null, AsmTypes.OBJECT_TYPE, i);
    }

    public static StackValue arrayElement(Type type, KotlinType kotlinType, StackValue stackValue, StackValue stackValue2) {
        if (type == null) {
            $$$reportNull$$$0(56);
        }
        return new ArrayElement(type, kotlinType, stackValue, stackValue2);
    }

    public static StackValue collectionElement(CollectionElementReceiver collectionElementReceiver, Type type, KotlinType kotlinType, ResolvedCall<FunctionDescriptor> resolvedCall, ResolvedCall<FunctionDescriptor> resolvedCall2, ExpressionCodegen expressionCodegen) {
        return new CollectionElement(collectionElementReceiver, type, kotlinType, resolvedCall, resolvedCall2, expressionCodegen);
    }

    public static UnderlyingValueOfInlineClass underlyingValueOfInlineClass(Type type, KotlinType kotlinType, StackValue stackValue) {
        if (type == null) {
            $$$reportNull$$$0(57);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(58);
        }
        return new UnderlyingValueOfInlineClass(type, kotlinType, stackValue);
    }

    public static Field field(Type type, Type type2, String str, boolean z, StackValue stackValue) {
        if (type == null) {
            $$$reportNull$$$0(59);
        }
        if (type2 == null) {
            $$$reportNull$$$0(60);
        }
        if (str == null) {
            $$$reportNull$$$0(61);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(62);
        }
        return field(type, null, type2, str, z, stackValue);
    }

    public static Field field(Type type, KotlinType kotlinType, Type type2, String str, boolean z, StackValue stackValue) {
        if (type == null) {
            $$$reportNull$$$0(63);
        }
        if (type2 == null) {
            $$$reportNull$$$0(64);
        }
        if (str == null) {
            $$$reportNull$$$0(65);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(66);
        }
        return field(type, kotlinType, type2, str, z, stackValue, null);
    }

    public static Field field(Type type, KotlinType kotlinType, Type type2, String str, boolean z, StackValue stackValue, DeclarationDescriptor declarationDescriptor) {
        if (type == null) {
            $$$reportNull$$$0(67);
        }
        if (type2 == null) {
            $$$reportNull$$$0(68);
        }
        if (str == null) {
            $$$reportNull$$$0(69);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(70);
        }
        return new Field(type, kotlinType, type2, str, z, stackValue, declarationDescriptor);
    }

    public static Field field(Field field, StackValue stackValue) {
        if (field == null) {
            $$$reportNull$$$0(71);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(72);
        }
        return field(field.type, field.kotlinType, field.owner, field.f697name, field.isStaticPut, stackValue, field.descriptor);
    }

    public static Field field(FieldInfo fieldInfo, StackValue stackValue) {
        if (fieldInfo == null) {
            $$$reportNull$$$0(73);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(74);
        }
        return field(fieldInfo.getFieldType(), fieldInfo.getFieldKotlinType(), Type.getObjectType(fieldInfo.getOwnerInternalName()), fieldInfo.getFieldName(), fieldInfo.isStatic(), stackValue);
    }

    public static StackValue changeReceiverForFieldAndSharedVar(StackValueWithSimpleReceiver stackValueWithSimpleReceiver, StackValue stackValue) {
        if (stackValueWithSimpleReceiver == null) {
            $$$reportNull$$$0(75);
        }
        if (stackValue == null || stackValueWithSimpleReceiver.isStaticPut) {
            if (stackValueWithSimpleReceiver == null) {
                $$$reportNull$$$0(76);
            }
            return stackValueWithSimpleReceiver;
        }
        StackValueWithSimpleReceiver stackValueWithSimpleReceiverChangeReceiver = stackValueWithSimpleReceiver.changeReceiver(stackValue);
        if (stackValueWithSimpleReceiverChangeReceiver == null) {
            $$$reportNull$$$0(77);
        }
        return stackValueWithSimpleReceiverChangeReceiver;
    }

    public static Property property(PropertyDescriptor propertyDescriptor, Type type, Type type2, boolean z, String str, CallableMethod callableMethod, CallableMethod callableMethod2, StackValue stackValue, ExpressionCodegen expressionCodegen, ResolvedCall resolvedCall, boolean z2, KotlinType kotlinType) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(78);
        }
        if (type2 == null) {
            $$$reportNull$$$0(79);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(80);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(81);
        }
        return new Property(propertyDescriptor, type, callableMethod, callableMethod2, z, str, type2, stackValue, expressionCodegen, resolvedCall, z2, kotlinType);
    }

    public static StackValue expression(Type type, KtExpression ktExpression, ExpressionCodegen expressionCodegen) {
        return new Expression(type, ktExpression, expressionCodegen);
    }

    private static void box(Type type, Type type2, InstructionAdapter instructionAdapter) {
        if (type == Type.INT_TYPE) {
            if (type2.getInternalName().equals("java/lang/Byte")) {
                type = Type.BYTE_TYPE;
            } else if (type2.getInternalName().equals("java/lang/Short")) {
                type = Type.SHORT_TYPE;
            } else if (type2.getInternalName().equals("java/lang/Long")) {
                type = Type.LONG_TYPE;
            }
            instructionAdapter.cast(Type.INT_TYPE, type);
        }
        Type typeBoxType = AsmUtil.boxType(type);
        if (typeBoxType == type) {
            return;
        }
        instructionAdapter.invokestatic(typeBoxType.getInternalName(), "valueOf", Type.getMethodDescriptor(typeBoxType, type), false);
        coerce(typeBoxType, type2, instructionAdapter);
    }

    private static void unbox(Type type, Type type2, InstructionAdapter instructionAdapter) {
        instructionAdapter.invokevirtual(type.getInternalName(), type2.getClassName() + "Value", "()" + type2.getDescriptor(), false);
    }

    public static void boxInlineClass(KotlinTypeMarker kotlinTypeMarker, InstructionAdapter instructionAdapter, KotlinTypeMapperBase kotlinTypeMapperBase) {
        if (kotlinTypeMarker == null) {
            $$$reportNull$$$0(82);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(83);
        }
        if (kotlinTypeMapperBase == null) {
            $$$reportNull$$$0(84);
        }
        Type typeMapTypeCommon = kotlinTypeMapperBase.mapTypeCommon(kotlinTypeMarker, TypeMappingMode.CLASS_DECLARATION);
        Type typeMapUnderlyingTypeOfInlineClassType = KotlinTypeMapper.mapUnderlyingTypeOfInlineClassType(kotlinTypeMarker, kotlinTypeMapperBase);
        boxInlineClass(typeMapUnderlyingTypeOfInlineClassType, typeMapTypeCommon, kotlinTypeMapperBase.getTypeSystem().isNullableType(kotlinTypeMarker) && !AsmUtil.isPrimitive(typeMapUnderlyingTypeOfInlineClassType), instructionAdapter);
    }

    public static void boxInlineClass(final Type type, final Type type2, boolean z, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(85);
        }
        if (type2 == null) {
            $$$reportNull$$$0(86);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(87);
        }
        if (z) {
            boxOrUnboxWithNullCheck(instructionAdapter, new Consumer() { // from class: org.jetbrains.kotlin.codegen.StackValue$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StackValue.invokeBoxMethod((InstructionAdapter) obj, type2, type);
                }
            });
        } else {
            invokeBoxMethod(instructionAdapter, type2, type);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void invokeBoxMethod(InstructionAdapter instructionAdapter, Type type, Type type2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(88);
        }
        if (type == null) {
            $$$reportNull$$$0(89);
        }
        if (type2 == null) {
            $$$reportNull$$$0(90);
        }
        instructionAdapter.invokestatic(type.getInternalName(), KotlinTypeMapper.BOX_JVM_METHOD_NAME, Type.getMethodDescriptor(type, type2), false);
    }

    public static void unboxInlineClass(Type type, KotlinTypeMarker kotlinTypeMarker, InstructionAdapter instructionAdapter, KotlinTypeMapperBase kotlinTypeMapperBase) {
        if (type == null) {
            $$$reportNull$$$0(91);
        }
        if (kotlinTypeMarker == null) {
            $$$reportNull$$$0(92);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(93);
        }
        if (kotlinTypeMapperBase == null) {
            $$$reportNull$$$0(94);
        }
        Type typeMapTypeCommon = kotlinTypeMapperBase.mapTypeCommon(kotlinTypeMarker, TypeMappingMode.CLASS_DECLARATION);
        Type typeMapUnderlyingTypeOfInlineClassType = KotlinTypeMapper.mapUnderlyingTypeOfInlineClassType(kotlinTypeMarker, kotlinTypeMapperBase);
        unboxInlineClass(type, typeMapTypeCommon, typeMapUnderlyingTypeOfInlineClassType, kotlinTypeMapperBase.getTypeSystem().isNullableType(kotlinTypeMarker) && !AsmUtil.isPrimitive(typeMapUnderlyingTypeOfInlineClassType), instructionAdapter);
    }

    public static void unboxInlineClass(Type type, final Type type2, final Type type3, boolean z, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(95);
        }
        if (type2 == null) {
            $$$reportNull$$$0(96);
        }
        if (type3 == null) {
            $$$reportNull$$$0(97);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(98);
        }
        coerce(type, type2, instructionAdapter);
        if (z) {
            boxOrUnboxWithNullCheck(instructionAdapter, new Consumer() { // from class: org.jetbrains.kotlin.codegen.StackValue$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StackValue.invokeUnboxMethod((InstructionAdapter) obj, type2, type3);
                }
            });
        } else {
            invokeUnboxMethod(instructionAdapter, type2, type3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void invokeUnboxMethod(InstructionAdapter instructionAdapter, Type type, Type type2) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(99);
        }
        if (type == null) {
            $$$reportNull$$$0(100);
        }
        if (type2 == null) {
            $$$reportNull$$$0(101);
        }
        instructionAdapter.invokevirtual(type.getInternalName(), KotlinTypeMapper.UNBOX_JVM_METHOD_NAME, "()" + type2.getDescriptor(), false);
    }

    private static void boxOrUnboxWithNullCheck(InstructionAdapter instructionAdapter, Consumer<InstructionAdapter> consumer) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(102);
        }
        if (consumer == null) {
            $$$reportNull$$$0(103);
        }
        Label label = new Label();
        Label label2 = new Label();
        instructionAdapter.dup();
        instructionAdapter.ifnull(label);
        consumer.accept(instructionAdapter);
        instructionAdapter.goTo(label2);
        instructionAdapter.mark(label);
        instructionAdapter.pop();
        instructionAdapter.aconst(null);
        instructionAdapter.mark(label2);
    }

    protected void coerceTo(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(104);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(105);
        }
        coerce(this.type, this.kotlinType, type, kotlinType, instructionAdapter);
    }

    protected void coerceFrom(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(106);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(107);
        }
        coerce(type, kotlinType, this.type, this.kotlinType, instructionAdapter);
    }

    public static void coerce(Type type, KotlinType kotlinType, Type type2, KotlinType kotlinType2, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(108);
        }
        if (type2 == null) {
            $$$reportNull$$$0(109);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(110);
        }
        if (coerceInlineClasses(type, kotlinType, type2, kotlinType2, instructionAdapter, StaticTypeMapperForOldBackend.INSTANCE)) {
            return;
        }
        coerce(type, type2, instructionAdapter);
    }

    public static boolean requiresInlineClassBoxingOrUnboxing(Type type, KotlinType kotlinType, Type type2, KotlinType kotlinType2) {
        if (type == null) {
            $$$reportNull$$$0(111);
        }
        if (type2 == null) {
            $$$reportNull$$$0(112);
        }
        if (kotlinType == null || kotlinType2 == null) {
            return false;
        }
        boolean zIsInlineClassType = InlineClassesUtilsKt.isInlineClassType(kotlinType);
        boolean zIsInlineClassType2 = InlineClassesUtilsKt.isInlineClassType(kotlinType2);
        if (!zIsInlineClassType && !zIsInlineClassType2) {
            return false;
        }
        boolean z = zIsInlineClassType && isUnboxedInlineClass(kotlinType, type);
        boolean z2 = zIsInlineClassType2 && isUnboxedInlineClass(kotlinType2, type2);
        if (zIsInlineClassType && zIsInlineClassType2) {
            return z != z2;
        }
        return (zIsInlineClassType && z) || (zIsInlineClassType2 && z2);
    }

    private static boolean coerceInlineClasses(Type type, KotlinType kotlinType, Type type2, KotlinType kotlinType2, InstructionAdapter instructionAdapter, KotlinTypeMapperBase kotlinTypeMapperBase) {
        if (type == null) {
            $$$reportNull$$$0(113);
        }
        if (type2 == null) {
            $$$reportNull$$$0(114);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(115);
        }
        if (kotlinTypeMapperBase == null) {
            $$$reportNull$$$0(116);
        }
        if (kotlinType != null && kotlinType2 != null) {
            boolean zIsInlineClassType = InlineClassesUtilsKt.isInlineClassType(kotlinType);
            boolean zIsInlineClassType2 = InlineClassesUtilsKt.isInlineClassType(kotlinType2);
            if (!zIsInlineClassType && !zIsInlineClassType2) {
                return false;
            }
            if (kotlinType.equals(kotlinType2) && type.equals(type2)) {
                return true;
            }
            if (zIsInlineClassType && zIsInlineClassType2) {
                boolean zIsUnboxedInlineClass = isUnboxedInlineClass(kotlinType, type);
                boolean zIsUnboxedInlineClass2 = isUnboxedInlineClass(kotlinType2, type2);
                if (zIsUnboxedInlineClass && !zIsUnboxedInlineClass2) {
                    boxInlineClass(kotlinType, instructionAdapter, kotlinTypeMapperBase);
                    return true;
                }
                if (!zIsUnboxedInlineClass && zIsUnboxedInlineClass2) {
                    unboxInlineClass(type, kotlinType2, instructionAdapter, kotlinTypeMapperBase);
                    return true;
                }
            } else if (zIsInlineClassType) {
                if (isUnboxedInlineClass(kotlinType, type)) {
                    boxInlineClass(kotlinType, instructionAdapter, kotlinTypeMapperBase);
                    return true;
                }
            } else if (isUnboxedInlineClass(kotlinType2, type2)) {
                unboxInlineClass(type, kotlinType2, instructionAdapter, kotlinTypeMapperBase);
                return true;
            }
        }
        return false;
    }

    public static boolean isUnboxedInlineClass(KotlinType kotlinType, Type type) {
        if (kotlinType == null) {
            $$$reportNull$$$0(117);
        }
        if (type == null) {
            $$$reportNull$$$0(118);
        }
        return KotlinTypeMapper.mapUnderlyingTypeOfInlineClassType(kotlinType, StaticTypeMapperForOldBackend.INSTANCE).equals(type);
    }

    public static void coerce(Type type, Type type2, InstructionAdapter instructionAdapter) {
        if (type == null) {
            $$$reportNull$$$0(119);
        }
        if (type2 == null) {
            $$$reportNull$$$0(120);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(121);
        }
        coerce(type, type2, instructionAdapter, false);
    }

    public static void coerce(Type type, Type type2, InstructionAdapter instructionAdapter, boolean z) {
        if (type == null) {
            $$$reportNull$$$0(122);
        }
        if (type2 == null) {
            $$$reportNull$$$0(123);
        }
        if (instructionAdapter == null) {
            $$$reportNull$$$0(124);
        }
        if (!type2.equals(type) || z) {
            if (type2.getSort() == 0) {
                AsmUtil.pop(instructionAdapter, type);
                return;
            }
            if (type.getSort() == 0) {
                if (type2.equals(AsmTypes.UNIT_TYPE) || type2.equals(AsmTypes.OBJECT_TYPE)) {
                    putUnitInstance(instructionAdapter);
                    return;
                } else {
                    AsmUtil.pushDefaultValueOnStack(type2, instructionAdapter);
                    return;
                }
            }
            if (type2.equals(AsmTypes.UNIT_TYPE)) {
                if (type.equals(AsmTypes.getType(Object.class))) {
                    instructionAdapter.checkcast(AsmTypes.UNIT_TYPE);
                    return;
                } else {
                    if (type.equals(AsmTypes.getType(Void.class))) {
                        return;
                    }
                    AsmUtil.pop(instructionAdapter, type);
                    putUnitInstance(instructionAdapter);
                    return;
                }
            }
            if (type2.getSort() == 9) {
                if (type.getSort() != 9) {
                    instructionAdapter.checkcast(type2);
                    return;
                } else if (type2.getDimensions() != type.getDimensions()) {
                    instructionAdapter.checkcast(type2);
                    return;
                } else {
                    if (type2.getElementType().equals(AsmTypes.OBJECT_TYPE)) {
                        return;
                    }
                    instructionAdapter.checkcast(type2);
                    return;
                }
            }
            if (type2.getSort() == 10) {
                if (type.getSort() == 10 || type.getSort() == 9) {
                    if (type2.equals(AsmTypes.OBJECT_TYPE)) {
                        return;
                    }
                    instructionAdapter.checkcast(type2);
                    return;
                }
                box(type, type2, instructionAdapter);
                return;
            }
            if (type.getSort() == 10 || type.getSort() == 9) {
                Type typeUnboxPrimitiveTypeOrNull = AsmUtil.unboxPrimitiveTypeOrNull(type);
                if (typeUnboxPrimitiveTypeOrNull != null) {
                    unbox(type, typeUnboxPrimitiveTypeOrNull, instructionAdapter);
                    coerce(typeUnboxPrimitiveTypeOrNull, type2, instructionAdapter);
                    return;
                }
                if (type2.getSort() == 1) {
                    coerce(type, AsmTypes.BOOLEAN_WRAPPER_TYPE, instructionAdapter);
                    unbox(AsmTypes.BOOLEAN_WRAPPER_TYPE, Type.BOOLEAN_TYPE, instructionAdapter);
                    return;
                } else {
                    if (type2.getSort() == 2) {
                        if (type.equals(AsmTypes.NUMBER_TYPE)) {
                            unbox(AsmTypes.NUMBER_TYPE, Type.INT_TYPE, instructionAdapter);
                            instructionAdapter.visitInsn(146);
                            return;
                        } else {
                            coerce(type, AsmTypes.CHARACTER_WRAPPER_TYPE, instructionAdapter);
                            unbox(AsmTypes.CHARACTER_WRAPPER_TYPE, Type.CHAR_TYPE, instructionAdapter);
                            return;
                        }
                    }
                    coerce(type, AsmTypes.NUMBER_TYPE, instructionAdapter);
                    unbox(AsmTypes.NUMBER_TYPE, type2, instructionAdapter);
                    return;
                }
            }
            instructionAdapter.cast(type, type2);
        }
    }

    public static void putUnitInstance(InstructionAdapter instructionAdapter) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(125);
        }
        unit().put(AsmTypes.UNIT_TYPE, null, instructionAdapter);
    }

    public static StackValue unit() {
        return UNIT;
    }

    public static StackValue none() {
        return None.INSTANCE;
    }

    public static Field receiverWithRefWrapper(Type type, Type type2, String str, StackValue stackValue, DeclarationDescriptor declarationDescriptor) {
        if (type == null) {
            $$$reportNull$$$0(126);
        }
        if (type2 == null) {
            $$$reportNull$$$0(127);
        }
        if (str == null) {
            $$$reportNull$$$0(128);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(129);
        }
        return field(sharedTypeForType(type), null, type2, str, false, stackValue, declarationDescriptor);
    }

    public static FieldForSharedVar fieldForSharedVar(Type type, Type type2, String str, Field field, VariableDescriptor variableDescriptor) {
        if (type == null) {
            $$$reportNull$$$0(130);
        }
        if (type2 == null) {
            $$$reportNull$$$0(131);
        }
        if (str == null) {
            $$$reportNull$$$0(132);
        }
        if (field == null) {
            $$$reportNull$$$0(133);
        }
        if (variableDescriptor == null) {
            $$$reportNull$$$0(134);
        }
        return new FieldForSharedVar(type, variableDescriptor.getType(), type2, str, field, variableDescriptor.isLateInit(), variableDescriptor.getShortName());
    }

    public static FieldForSharedVar fieldForSharedVar(FieldForSharedVar fieldForSharedVar, StackValue stackValue) {
        if (fieldForSharedVar == null) {
            $$$reportNull$$$0(135);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(136);
        }
        return new FieldForSharedVar(fieldForSharedVar.type, fieldForSharedVar.kotlinType, fieldForSharedVar.owner, fieldForSharedVar.f698name, field((Field) fieldForSharedVar.receiver, stackValue), fieldForSharedVar.isLateinit, fieldForSharedVar.variableName);
    }

    public static StackValue coercion(StackValue stackValue, Type type, KotlinType kotlinType) {
        if (stackValue == null) {
            $$$reportNull$$$0(137);
        }
        if (type == null) {
            $$$reportNull$$$0(138);
        }
        return coercionValueForArgumentOfInlineClassConstructor(stackValue, type, kotlinType, null);
    }

    public static StackValue coercionValueForArgumentOfInlineClassConstructor(StackValue stackValue, Type type, KotlinType kotlinType, KotlinType kotlinType2) {
        if (stackValue == null) {
            $$$reportNull$$$0(139);
        }
        if (type == null) {
            $$$reportNull$$$0(140);
        }
        KotlinType kotlinType3 = stackValue.kotlinType;
        return (stackValue.type.equals(type) && ((kotlinType3 == null && kotlinType == null) || (kotlinType3 != null && kotlinType != null && kotlinType.equals(kotlinType3)))) ? stackValue : new CoercionValue(stackValue, type, kotlinType, kotlinType2);
    }

    public static StackValue thisOrOuter(ExpressionCodegen expressionCodegen, ClassDescriptor classDescriptor, boolean z, boolean z2) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(141);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(142);
        }
        return new ThisOuter(expressionCodegen, classDescriptor, z, classDescriptor.getKind() == ClassKind.INTERFACE || InlineClassesUtilsKt.isInlineClass(classDescriptor) || (z2 && !z));
    }

    public static StackValue postIncrement(int i, int i2) {
        return new PostIncrement(i, i2);
    }

    public static StackValue preIncrementForLocalVar(int i, int i2, KotlinType kotlinType) {
        return new PreIncrementForLocalVar(i, i2, kotlinType);
    }

    public static StackValue preIncrement(Type type, StackValue stackValue, int i, ResolvedCall resolvedCall, ExpressionCodegen expressionCodegen) {
        if (type == null) {
            $$$reportNull$$$0(143);
        }
        if (stackValue == null) {
            $$$reportNull$$$0(144);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(145);
        }
        KotlinType kotlinType = stackValue.kotlinType;
        if ((stackValue instanceof Local) && Type.INT_TYPE == stackValue.type && kotlinType != null && KotlinBuiltIns.isPrimitiveType(kotlinType)) {
            return preIncrementForLocalVar(((Local) stackValue).index, i, kotlinType);
        }
        return new PrefixIncrement(type, stackValue, resolvedCall, expressionCodegen);
    }

    public static StackValue receiver(ResolvedCall<?> resolvedCall, StackValue stackValue, ExpressionCodegen expressionCodegen, Callable callable) {
        boolean z;
        ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.mo7304getDispatchReceiver();
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        if (resultingDescriptor instanceof SyntheticFieldDescriptor) {
            receiverValueMo7304getDispatchReceiver = ((SyntheticFieldDescriptor) resultingDescriptor).getDispatchReceiverForBackend();
        }
        ReceiverValue receiverValue = receiverValueMo7304getDispatchReceiver;
        ReceiverValue receiverValueMo7305getExtensionReceiver = resolvedCall.mo7305getExtensionReceiver();
        if (resultingDescriptor instanceof ImportedFromObjectCallableDescriptor) {
            resultingDescriptor = ((ImportedFromObjectCallableDescriptor) resultingDescriptor).getCallableFromObject();
            z = true;
        } else {
            z = false;
        }
        CallableDescriptor callableDescriptor = resultingDescriptor;
        if (receiverValue == null && receiverValueMo7305getExtensionReceiver == null && !isLocalFunCall(callable) && !z) {
            return stackValue;
        }
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (callableDescriptor instanceof SyntheticFieldDescriptor) {
            receiverParameterDescriptorMo6753getDispatchReceiverParameter = ((SyntheticFieldDescriptor) callableDescriptor).getDispatchReceiverParameterForBackend();
        }
        return CallReceiver.generateCallReceiver(resolvedCall, expressionCodegen, callable, receiverParameterDescriptorMo6753getDispatchReceiverParameter, platformStaticCallIfPresent(genReceiver(receiverValueMo7305getExtensionReceiver != null ? none() : stackValue, expressionCodegen, callableDescriptor, callable, receiverValue, false), callableDescriptor), extensionReceiverParameter, genReceiver(stackValue, expressionCodegen, callableDescriptor, callable, receiverValueMo7305getExtensionReceiver, true));
    }

    private static StackValue genReceiver(StackValue stackValue, ExpressionCodegen expressionCodegen, CallableDescriptor callableDescriptor, Callable callable, ReceiverValue receiverValue, boolean z) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        if (stackValue == null) {
            $$$reportNull$$$0(146);
        }
        if (expressionCodegen == null) {
            $$$reportNull$$$0(147);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(148);
        }
        DeclarationDescriptor containingDeclaration = callableDescriptor.getPackageFragment();
        if (stackValue == none()) {
            if (receiverValue != null) {
                return expressionCodegen.generateReceiverValue(receiverValue, false);
            }
            if (isLocalFunCall(callable) && !z) {
                if ((callableDescriptor instanceof SimpleFunctionDescriptor) && (simpleFunctionDescriptor = (SimpleFunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction((SimpleFunctionDescriptor) callableDescriptor)) != null && simpleFunctionDescriptor.isSuspend()) {
                    return putLocalSuspendFunctionOnStack(expressionCodegen, simpleFunctionDescriptor.getOriginal());
                }
                return expressionCodegen.findLocalOrCapturedValue(callableDescriptor.getOriginal());
            }
            if (!z && DescriptorUtils.isObject(containingDeclaration)) {
                return singleton((ClassDescriptor) containingDeclaration, expressionCodegen.typeMapper);
            }
        } else if (receiverValue != null) {
            return stackValue;
        }
        return none();
    }

    private static StackValue putLocalSuspendFunctionOnStack(ExpressionCodegen expressionCodegen, SimpleFunctionDescriptor simpleFunctionDescriptor) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(149);
        }
        int iLookupLocalIndex = expressionCodegen.lookupLocalIndex(simpleFunctionDescriptor);
        if (iLookupLocalIndex >= 0) {
            return local(iLookupLocalIndex, AsmTypes.OBJECT_TYPE);
        }
        BindingContext bindingContext = expressionCodegen.getBindingContext();
        Type typeAsmTypeForAnonymousClass = CodegenBinding.asmTypeForAnonymousClass(bindingContext, simpleFunctionDescriptor);
        if (expressionCodegen.context.hasThisDescriptor()) {
            ClassDescriptor thisDescriptor = expressionCodegen.context.getThisDescriptor();
            ClassDescriptor classDescriptor = (ClassDescriptor) bindingContext.get(CodegenBinding.CLASS_FOR_CALLABLE, simpleFunctionDescriptor);
            if ((thisDescriptor instanceof SyntheticClassDescriptorForLambda) && ((SyntheticClassDescriptorForLambda) thisDescriptor).isCallableReference()) {
                Boolean bool = (Boolean) bindingContext.get(CodegenBinding.RECURSIVE_SUSPEND_CALLABLE_REFERENCE, thisDescriptor);
                if (bool != null && bool.booleanValue()) {
                    return thisOrOuter(expressionCodegen, classDescriptor, false, false);
                }
                return expressionCodegen.findCapturedValue(simpleFunctionDescriptor);
            }
            if (classDescriptor == thisDescriptor) {
                expressionCodegen.v.visitVarInsn(25, 0);
                return onStack(typeAsmTypeForAnonymousClass);
            }
        }
        return expressionCodegen.findCapturedValue(simpleFunctionDescriptor);
    }

    private static StackValue platformStaticCallIfPresent(StackValue stackValue, CallableDescriptor callableDescriptor) {
        if (stackValue == null) {
            $$$reportNull$$$0(150);
        }
        if (callableDescriptor == null) {
            $$$reportNull$$$0(151);
        }
        if (!CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface(callableDescriptor)) {
            return stackValue;
        }
        if (stackValue.canHaveSideEffects()) {
            return coercion(stackValue, Type.VOID_TYPE, null);
        }
        return none();
    }

    static boolean isLocalFunCall(Callable callable) {
        return (callable == null || callable.getGenerateCalleeType() == null) ? false : true;
    }

    public static StackValue receiverWithoutReceiverArgument(StackValue stackValue) {
        return stackValue instanceof CallReceiver ? ((CallReceiver) stackValue).withoutReceiverArgument() : stackValue;
    }

    public static Field enumEntry(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(152);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(153);
        }
        SimpleType defaultType = ((ClassDescriptor) classDescriptor.getPackageFragment()).getDefaultType();
        Type typeMapType = kotlinTypeMapper.mapType(defaultType);
        return field(typeMapType, defaultType, typeMapType, classDescriptor.getShortName().asString(), true, none(), classDescriptor);
    }

    public static Field singleton(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(154);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(155);
        }
        return field(FieldInfo.createForSingleton(classDescriptor, kotlinTypeMapper), none());
    }

    public static Field createSingletonViaInstance(ClassDescriptor classDescriptor, KotlinTypeMapper kotlinTypeMapper, String str) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(156);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(157);
        }
        if (str == null) {
            $$$reportNull$$$0(158);
        }
        return field(FieldInfo.createSingletonViaInstance(classDescriptor, kotlinTypeMapper, str), none());
    }

    public static StackValue operation(Type type, Function1<InstructionAdapter, Unit> function1) {
        return operation(type, null, function1);
    }

    public static StackValue operation(Type type, KotlinType kotlinType, Function1<InstructionAdapter, Unit> function1) {
        return new OperationStackValue(type, kotlinType, function1);
    }

    public static StackValue functionCall(Type type, KotlinType kotlinType, Function1<InstructionAdapter, Unit> function1) {
        return new FunctionCallStackValue(type, kotlinType, function1);
    }

    public static boolean couldSkipReceiverOnStaticCall(StackValue stackValue) {
        return (stackValue instanceof Local) || (stackValue instanceof Constant);
    }

    private static class None extends StackValue {
        public static final None INSTANCE = new None();

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$None";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private None() {
            super(Type.VOID_TYPE, false);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    public static class Local extends StackValue {
        public final int index;

        /* JADX WARN: Code duplicated, block: B:8:0x0016  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 2) {
                objArr[0] = "topOfStackType";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Local";
            if (i == 2 || i == 3) {
                objArr[2] = "storeSelector";
            } else {
                objArr[2] = "putSelector";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private Local(int i, Type type, KotlinType kotlinType) {
            super(type, kotlinType, false);
            if (i < 0) {
                throw new IllegalStateException("local variable index must be non-negative");
            }
            this.index = i;
        }

        private Local(int i, Type type) {
            this(i, type, (KotlinType) null);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            instructionAdapter.load(this.index, this.type);
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.store(this.index, this.type);
        }
    }

    public static class LateinitLocal extends StackValue {
        public final int index;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final Name f699name;

        /* JADX WARN: Code duplicated, block: B:8:0x0016  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 2) {
                objArr[0] = "topOfStackType";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$LateinitLocal";
            if (i == 2 || i == 3) {
                objArr[2] = "storeSelector";
            } else {
                objArr[2] = "putSelector";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private LateinitLocal(int i, Type type, KotlinType kotlinType, Name name2) {
            super(type, kotlinType, false);
            if (i < 0) {
                throw new IllegalStateException("local variable index must be non-negative");
            }
            if (name2 == null) {
                throw new IllegalArgumentException("Lateinit local variable should have name: #" + i + " " + type.getDescriptor());
            }
            this.index = i;
            this.f699name = name2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            instructionAdapter.load(this.index, this.type);
            StackValue.genNonNullAssertForLateinit(instructionAdapter, this.f699name.asString());
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.store(this.index, this.type);
            PseudoInsnsKt.storeNotNull(instructionAdapter);
        }
    }

    public static class Delegate extends StackValue {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final ExpressionCodegen codegen;
        private final StackValue delegateValue;
        private final StackValue metadataValue;
        private final VariableDescriptorWithAccessors variableDescriptor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "delegateValue";
                    break;
                case 2:
                    objArr[0] = "metadataValue";
                    break;
                case 3:
                    objArr[0] = "variableDescriptor";
                    break;
                case 4:
                    objArr[0] = "codegen";
                    break;
                case 5:
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 6:
                case 8:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 7:
                    objArr[0] = "rightSide";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Delegate";
            if (i == 5 || i == 6) {
                objArr[2] = "putSelector";
            } else if (i == 7 || i == 8) {
                objArr[2] = "store";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private Delegate(Type type, StackValue stackValue, StackValue stackValue2, VariableDescriptorWithAccessors variableDescriptorWithAccessors, ExpressionCodegen expressionCodegen) {
            super(type);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
            if (stackValue2 == null) {
                $$$reportNull$$$0(2);
            }
            if (variableDescriptorWithAccessors == null) {
                $$$reportNull$$$0(3);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(4);
            }
            this.delegateValue = stackValue;
            this.metadataValue = stackValue2;
            this.variableDescriptor = variableDescriptorWithAccessors;
            this.codegen = expressionCodegen;
        }

        private ResolvedCall<FunctionDescriptor> getResolvedCall(boolean z) {
            return (ResolvedCall) this.codegen.getState().getBindingContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, z ? this.variableDescriptor.getGetter() : this.variableDescriptor.getSetter());
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(5);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(6);
            }
            ResolvedCall<FunctionDescriptor> resolvedCall = getResolvedCall(true);
            List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
            this.codegen.tempVariables.put(valueArguments.get(0).getCallElement(), StackValue.constant(null, AsmTypes.OBJECT_TYPE));
            this.codegen.tempVariables.put(valueArguments.get(1).getCallElement(), this.metadataValue);
            this.codegen.invokeFunction(resolvedCall, this.delegateValue).put(type, kotlinType, instructionAdapter);
            this.codegen.tempVariables.remove(valueArguments.get(0).getCallElement());
            this.codegen.tempVariables.remove(valueArguments.get(1).getCallElement());
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
            if (stackValue == null) {
                $$$reportNull$$$0(7);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(8);
            }
            ResolvedCall<FunctionDescriptor> resolvedCall = getResolvedCall(false);
            List<? extends ValueArgument> valueArguments = resolvedCall.getCall().getValueArguments();
            this.codegen.tempVariables.put(valueArguments.get(0).getCallElement(), StackValue.constant(null, AsmTypes.OBJECT_TYPE));
            this.codegen.tempVariables.put(valueArguments.get(1).getCallElement(), this.metadataValue);
            this.codegen.tempVariables.put(valueArguments.get(2).getCallElement(), stackValue);
            this.codegen.invokeFunction(resolvedCall, this.delegateValue).put(Type.VOID_TYPE, null, instructionAdapter);
            this.codegen.tempVariables.remove(valueArguments.get(0).getCallElement());
            this.codegen.tempVariables.remove(valueArguments.get(1).getCallElement());
            this.codegen.tempVariables.remove(valueArguments.get(2).getCallElement());
        }
    }

    public static class OnStack extends StackValue {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 3) {
                objArr[0] = RegisterSpec.PREFIX;
            } else {
                objArr[0] = ModuleXmlParser.TYPE;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$OnStack";
            if (i == 2 || i == 3) {
                objArr[2] = "moveToTopOfStack";
            } else {
                objArr[2] = "putSelector";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public OnStack(Type type) {
            this(type, null);
        }

        public OnStack(Type type, KotlinType kotlinType) {
            super(type, kotlinType);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void moveToTopOfStack(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter, int i) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            if (i == 0) {
                put(type, kotlinType, instructionAdapter);
                return;
            }
            if (i == 1) {
                int size = this.type.getSize();
                if (size == 1) {
                    instructionAdapter.swap();
                } else if (size == 2) {
                    instructionAdapter.dupX2();
                    instructionAdapter.pop();
                } else {
                    throw new UnsupportedOperationException("don't know how to move type " + type + " to top of stack");
                }
                coerceTo(type, kotlinType, instructionAdapter);
                return;
            }
            if (i == 2) {
                int size2 = this.type.getSize();
                if (size2 == 1) {
                    instructionAdapter.dup2X1();
                    instructionAdapter.pop2();
                } else if (size2 == 2) {
                    instructionAdapter.dup2X2();
                    instructionAdapter.pop2();
                } else {
                    throw new UnsupportedOperationException("don't know how to move type " + type + " to top of stack");
                }
                coerceTo(type, kotlinType, instructionAdapter);
                return;
            }
            throw new UnsupportedOperationException("unsupported move-to-top depth " + i);
        }
    }

    public static class Constant extends StackValue {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public final Object value;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Constant";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public Constant(Object obj, Type type, KotlinType kotlinType) {
            super(type, kotlinType, false);
            this.value = obj;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            Object obj = this.value;
            if ((obj instanceof Integer) || (obj instanceof Byte) || (obj instanceof Short)) {
                instructionAdapter.iconst(((Number) obj).intValue());
            } else if (obj instanceof Character) {
                instructionAdapter.iconst(((Character) obj).charValue());
            } else if (obj instanceof Long) {
                instructionAdapter.lconst(((Long) obj).longValue());
            } else if (obj instanceof Float) {
                instructionAdapter.fconst(((Float) obj).floatValue());
            } else if (obj instanceof Double) {
                instructionAdapter.dconst(((Double) obj).doubleValue());
            } else {
                instructionAdapter.aconst(obj);
            }
            if (this.value != null || AsmUtil.isPrimitive(type)) {
                coerceTo(type, kotlinType, instructionAdapter);
            }
        }
    }

    private static class ArrayElement extends StackValueWithSimpleReceiver {
        private final Type type;

        /* JADX WARN: Code duplicated, block: B:8:0x0016  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 2) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else if (i != 3) {
                objArr[0] = "topOfStackType";
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$ArrayElement";
            if (i == 2 || i == 3) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "storeSelector";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver
        public int receiverSize() {
            return 2;
        }

        public ArrayElement(Type type, KotlinType kotlinType, StackValue stackValue, StackValue stackValue2) {
            super(type, kotlinType, false, false, new Receiver(Type.LONG_TYPE, stackValue, stackValue2), true);
            this.type = type;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.astore(this.type);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            instructionAdapter.aload(this.type);
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    public static class UnderlyingValueOfInlineClass extends StackValueWithSimpleReceiver {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "receiver";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$UnderlyingValueOfInlineClass";
            if (i == 2 || i == 3) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnderlyingValueOfInlineClass(Type type, KotlinType kotlinType, StackValue stackValue) {
            super(type, kotlinType, false, false, stackValue, true);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    public static class CollectionElementReceiver extends StackValue {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private CallGenerator callGenerator;
        private final Callable callable;
        private final ExpressionCodegen codegen;
        private DefaultCallArgs defaultArgs;
        private final FrameMap frame;
        boolean isComplexOperationWithDup;
        private final boolean isGetter;
        private final StackValue receiver;
        private final ResolvedCall<FunctionDescriptor> resolvedGetCall;
        private final ResolvedCall<FunctionDescriptor> resolvedSetCall;
        private final List<ResolvedValueArgument> valueArguments;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "receiver";
                    break;
                case 2:
                    objArr[0] = "codegen";
                    break;
                case 3:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 4:
                case 5:
                case 6:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                default:
                    objArr[0] = "callable";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$CollectionElementReceiver";
            if (i == 3 || i == 4) {
                objArr[2] = "putSelector";
            } else if (i == 5) {
                objArr[2] = "dup";
            } else if (i != 6) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "dupReceiver";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CollectionElementReceiver(Callable callable, StackValue stackValue, ResolvedCall<FunctionDescriptor> resolvedCall, ResolvedCall<FunctionDescriptor> resolvedCall2, boolean z, ExpressionCodegen expressionCodegen, List<ResolvedValueArgument> list) {
            super(AsmTypes.OBJECT_TYPE);
            if (callable == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            this.callable = callable;
            this.isGetter = z;
            this.receiver = stackValue;
            this.resolvedGetCall = resolvedCall;
            this.resolvedSetCall = resolvedCall2;
            this.valueArguments = list;
            this.codegen = expressionCodegen;
            this.frame = expressionCodegen.myFrameMap;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(4);
            }
            ResolvedCall<FunctionDescriptor> resolvedCall = this.isGetter ? this.resolvedGetCall : this.resolvedSetCall;
            StackValue stackValueReceiver = StackValue.receiver(resolvedCall, this.receiver, this.codegen, this.callable);
            ArgumentGenerator argumentGeneratorCreateArgumentGenerator = createArgumentGenerator();
            stackValueReceiver.put(stackValueReceiver.type, stackValueReceiver.kotlinType, instructionAdapter);
            this.callGenerator.processHiddenParameters();
            this.callGenerator.putHiddenParamsIntoLocals();
            List<ResolvedValueArgument> list = this.valueArguments;
            this.defaultArgs = argumentGeneratorCreateArgumentGenerator.generate(list, list, resolvedCall.getResultingDescriptor());
        }

        private ArgumentGenerator createArgumentGenerator() {
            ResolvedCall<FunctionDescriptor> resolvedCall = this.isGetter ? this.resolvedGetCall : this.resolvedSetCall;
            this.callGenerator = !this.isComplexOperationWithDup ? this.codegen.getOrCreateCallGenerator(resolvedCall) : this.codegen.defaultCallGenerator;
            return new CallBasedArgumentGenerator(this.codegen, this.callGenerator, ((FunctionDescriptor) resolvedCall.getResultingDescriptor()).getValueParameters(), this.callable.getValueParameterTypes());
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void dup(InstructionAdapter instructionAdapter, boolean z) {
            if (instructionAdapter == null) {
                $$$reportNull$$$0(5);
            }
            dupReceiver(instructionAdapter);
        }

        public void dupReceiver(InstructionAdapter instructionAdapter) {
            int iEnterTemp;
            int iEnterTemp2;
            Type typeMapType;
            int i;
            if (instructionAdapter == null) {
                $$$reportNull$$$0(6);
            }
            if (CollectionElement.isStandardStack(this.codegen.typeMapper, this.resolvedGetCall, 1) && CollectionElement.isStandardStack(this.codegen.typeMapper, this.resolvedSetCall, 2)) {
                instructionAdapter.dup2();
                return;
            }
            FrameMapBase<DeclarationDescriptor>.Mark mark = this.frame.mark();
            List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) this.resolvedGetCall.getResultingDescriptor()).getValueParameters();
            int size = valueParameters.size() - 1;
            int size2 = -1;
            while (size >= 0) {
                Type typeMapType2 = this.codegen.typeMapper.mapType(valueParameters.get(size).getType());
                int iEnterTemp3 = this.frame.enterTemp(typeMapType2);
                instructionAdapter.store(iEnterTemp3, typeMapType2);
                size--;
                size2 = iEnterTemp3;
            }
            ReceiverValue receiverValueMo7305getExtensionReceiver = this.resolvedGetCall.mo7305getExtensionReceiver();
            if (receiverValueMo7305getExtensionReceiver != null) {
                Type typeMapType3 = this.codegen.typeMapper.mapType(receiverValueMo7305getExtensionReceiver.getType());
                iEnterTemp = this.frame.enterTemp(typeMapType3);
                instructionAdapter.store(iEnterTemp, typeMapType3);
            } else {
                iEnterTemp = -1;
            }
            if (this.resolvedGetCall.mo7304getDispatchReceiver() != null) {
                iEnterTemp2 = this.frame.enterTemp(AsmTypes.OBJECT_TYPE);
                instructionAdapter.store(iEnterTemp2, AsmTypes.OBJECT_TYPE);
            } else {
                iEnterTemp2 = -1;
            }
            if (iEnterTemp != -1) {
                typeMapType = this.codegen.typeMapper.mapType(receiverValueMo7305getExtensionReceiver.getType());
                i = iEnterTemp;
            } else if (iEnterTemp2 != -1) {
                typeMapType = AsmTypes.OBJECT_TYPE;
                i = iEnterTemp2;
            } else {
                throw new UnsupportedOperationException();
            }
            if (this.resolvedSetCall.mo7304getDispatchReceiver() != null) {
                if (this.resolvedSetCall.mo7305getExtensionReceiver() != null) {
                    this.codegen.generateReceiverValue(this.resolvedSetCall.mo7304getDispatchReceiver(), false).put(AsmTypes.OBJECT_TYPE, null, instructionAdapter);
                }
                instructionAdapter.load(i, typeMapType);
            } else if (this.resolvedSetCall.mo7305getExtensionReceiver() != null) {
                instructionAdapter.load(i, typeMapType);
            } else {
                throw new UnsupportedOperationException();
            }
            Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
            int size3 = size2;
            while (it.hasNext()) {
                Type typeMapType4 = this.codegen.typeMapper.mapType(it.next().getType());
                instructionAdapter.load(size3, typeMapType4);
                size3 -= typeMapType4.getSize();
            }
            if (iEnterTemp2 != -1) {
                instructionAdapter.load(iEnterTemp2, AsmTypes.OBJECT_TYPE);
            }
            if (iEnterTemp != -1) {
                instructionAdapter.load(iEnterTemp, typeMapType);
            }
            Iterator<ValueParameterDescriptor> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                Type typeMapType5 = this.codegen.typeMapper.mapType(it2.next().getType());
                instructionAdapter.load(size2, typeMapType5);
                size2 -= typeMapType5.getSize();
            }
            mark.dropTo();
        }
    }

    public static class CollectionElement extends StackValueWithSimpleReceiver {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final ExpressionCodegen codegen;
        private final Callable getter;
        private final ResolvedCall<FunctionDescriptor> resolvedGetCall;
        private final ResolvedCall<FunctionDescriptor> resolvedSetCall;
        private final Callable setter;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 3:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 2:
                    objArr[0] = "codegen";
                    break;
                case 4:
                case 7:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 5:
                    objArr[0] = "typeMapper";
                    break;
                case 6:
                    objArr[0] = "topOfStackType";
                    break;
                default:
                    objArr[0] = "collectionElementReceiver";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$CollectionElement";
            if (i == 3 || i == 4) {
                objArr[2] = "putSelector";
            } else if (i == 5) {
                objArr[2] = "isStandardStack";
            } else if (i == 6 || i == 7) {
                objArr[2] = "storeSelector";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CollectionElement(CollectionElementReceiver collectionElementReceiver, Type type, KotlinType kotlinType, ResolvedCall<FunctionDescriptor> resolvedCall, ResolvedCall<FunctionDescriptor> resolvedCall2, ExpressionCodegen expressionCodegen) {
            super(type, kotlinType, false, false, collectionElementReceiver, true);
            if (collectionElementReceiver == null) {
                $$$reportNull$$$0(0);
            }
            if (type == null) {
                $$$reportNull$$$0(1);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            this.resolvedGetCall = resolvedCall;
            this.resolvedSetCall = resolvedCall2;
            this.setter = resolvedCall2 == null ? null : expressionCodegen.resolveToCallable(expressionCodegen.accessibleFunctionDescriptor(resolvedCall2), false, resolvedCall2);
            this.getter = resolvedCall != null ? expressionCodegen.resolveToCallable(expressionCodegen.accessibleFunctionDescriptor(resolvedCall), false, resolvedCall) : null;
            this.codegen = expressionCodegen;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(4);
            }
            if (this.getter == null) {
                throw new UnsupportedOperationException("no getter specified");
            }
            CallGenerator callGenerator = getCallGenerator();
            callGenerator.genCall(this.getter, this.resolvedGetCall, genDefaultMaskIfPresent(callGenerator), this.codegen);
            coerceTo(type, kotlinType, instructionAdapter);
        }

        private boolean genDefaultMaskIfPresent(CallGenerator callGenerator) {
            return ((CollectionElementReceiver) this.receiver).defaultArgs.generateOnStackIfNeeded(callGenerator, true);
        }

        private CallGenerator getCallGenerator() {
            return ((CollectionElementReceiver) this.receiver).callGenerator;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver
        public int receiverSize() {
            return (isStandardStack(this.codegen.typeMapper, this.resolvedGetCall, 1) && isStandardStack(this.codegen.typeMapper, this.resolvedSetCall, 2)) ? 2 : -1;
        }

        public static boolean isStandardStack(KotlinTypeMapper kotlinTypeMapper, ResolvedCall<?> resolvedCall, int i) {
            if (kotlinTypeMapper == null) {
                $$$reportNull$$$0(5);
            }
            if (resolvedCall == null) {
                return true;
            }
            List<ValueParameterDescriptor> valueParameters = resolvedCall.getResultingDescriptor().getValueParameters();
            if (valueParameters.size() != i) {
                return false;
            }
            Iterator<ValueParameterDescriptor> it = valueParameters.iterator();
            while (it.hasNext()) {
                if (kotlinTypeMapper.mapType(it.next().getType()).getSize() != 1) {
                    return false;
                }
            }
            if (resolvedCall.mo7304getDispatchReceiver() != null) {
                if (resolvedCall.mo7305getExtensionReceiver() != null) {
                    return false;
                }
            } else if (kotlinTypeMapper.mapType(resolvedCall.getResultingDescriptor().getExtensionReceiverParameter().getType()).getSize() != 1) {
                return false;
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(6);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(7);
            }
            Callable callable = this.setter;
            if (callable == null) {
                throw new UnsupportedOperationException("no setter specified");
            }
            Type type2 = (Type) ArraysKt.last(callable.getParameterTypes());
            KotlinType type3 = ((ValueParameterDescriptor) CollectionsKt.last((List) ((FunctionDescriptor) this.resolvedSetCall.getResultingDescriptor()).getOriginal().getValueParameters())).getType();
            coerce(type, kotlinType, type2, type3, instructionAdapter);
            CallGenerator callGenerator = getCallGenerator();
            callGenerator.putValueIfNeeded(new JvmKotlinType(type2, type3), StackValue.onStack(type2, type3));
            CollectionElementReceiver collectionElementReceiver = (CollectionElementReceiver) this.receiver;
            boolean zSupportsFeature = this.codegen.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperArrayConventionSetterWithDefaultCalls);
            boolean z = false;
            boolean z2 = true;
            if (collectionElementReceiver.isGetter) {
                List listTakeLastWhile = CollectionsKt.takeLastWhile(new ArrayList(collectionElementReceiver.valueArguments), new Function1() { // from class: org.jetbrains.kotlin.codegen.StackValue$CollectionElement$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(((ResolvedValueArgument) obj) instanceof DefaultValueArgument);
                    }
                });
                List<ResolvedValueArgument> valueArgumentsByIndex = this.resolvedSetCall.getValueArgumentsByIndex();
                List<ResolvedValueArgument> listTakeLastWhile2 = CollectionsKt.takeLastWhile(CollectionsKt.dropLast(valueArgumentsByIndex, 1), new Function1() { // from class: org.jetbrains.kotlin.codegen.StackValue$CollectionElement$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(((ResolvedValueArgument) obj) instanceof DefaultValueArgument);
                    }
                });
                if (!listTakeLastWhile.isEmpty() || !listTakeLastWhile2.isEmpty()) {
                    Local local = StackValue.local(this.codegen.myFrameMap.enterTemp(type2), type2);
                    local.store(StackValue.onStack(this.type), instructionAdapter);
                    List<Type> valueParameterTypes = this.getter.getValueParameterTypes();
                    for (int size = collectionElementReceiver.valueArguments.size() - 1; size >= 0; size--) {
                        if (((ResolvedValueArgument) collectionElementReceiver.valueArguments.get(size)) instanceof DefaultValueArgument) {
                            AsmUtil.pop(instructionAdapter, valueParameterTypes.get(size));
                        }
                    }
                    if (zSupportsFeature) {
                        DefaultCallArgs defaultCallArgs = new DefaultCallArgs(((FunctionDescriptor) CodegenUtilKt.unwrapFrontendVersion(this.resolvedSetCall.getResultingDescriptor())).getValueParameters().size());
                        if (listTakeLastWhile2.isEmpty()) {
                            z2 = false;
                        } else {
                            CallBasedArgumentGenerator callBasedArgumentGenerator = new CallBasedArgumentGenerator(this.codegen, callGenerator, ((FunctionDescriptor) this.resolvedSetCall.getResultingDescriptor()).getValueParameters(), this.setter.getValueParameterTypes());
                            int lastIndex = (CollectionsKt.getLastIndex(valueArgumentsByIndex) - 1) - listTakeLastWhile2.size();
                            for (ResolvedValueArgument resolvedValueArgument : listTakeLastWhile2) {
                                lastIndex++;
                                defaultCallArgs.mark(lastIndex);
                                callBasedArgumentGenerator.generateDefault(lastIndex, (DefaultValueArgument) resolvedValueArgument);
                            }
                        }
                        local.put(instructionAdapter);
                        this.codegen.myFrameMap.leaveTemp(type2);
                        defaultCallArgs.generateOnStackIfNeeded(callGenerator, false);
                        z = z2;
                    } else {
                        local.put(instructionAdapter);
                        this.codegen.myFrameMap.leaveTemp(type2);
                    }
                }
            } else if (zSupportsFeature && genDefaultMaskIfPresent(callGenerator)) {
                z = z2;
            }
            callGenerator.genCall(this.setter, this.resolvedSetCall, z, this.codegen);
            Type returnType = this.setter.getReturnType();
            if (returnType != Type.VOID_TYPE) {
                AsmUtil.pop(instructionAdapter, returnType);
            }
        }
    }

    public static class Field extends StackValueWithSimpleReceiver {
        public final DeclarationDescriptor descriptor;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        public final String f697name;
        public final Type owner;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "owner";
                    break;
                case 2:
                    objArr[0] = "name";
                    break;
                case 3:
                    objArr[0] = "receiver";
                    break;
                case 4:
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 5:
                case 7:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 6:
                    objArr[0] = "topOfStackType";
                    break;
                case 8:
                    objArr[0] = "newReceiver";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Field";
            switch (i) {
                case 4:
                case 5:
                    objArr[2] = "putSelector";
                    break;
                case 6:
                case 7:
                    objArr[2] = "storeSelector";
                    break;
                case 8:
                    objArr[2] = "changeReceiver";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Field(Type type, KotlinType kotlinType, Type type2, String str, boolean z, StackValue stackValue, DeclarationDescriptor declarationDescriptor) {
            super(type, kotlinType, z, z, stackValue, stackValue.canHaveSideEffects());
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (type2 == null) {
                $$$reportNull$$$0(1);
            }
            if (str == null) {
                $$$reportNull$$$0(2);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(3);
            }
            this.owner = type2;
            this.f697name = str;
            this.descriptor = declarationDescriptor;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(4);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(5);
            }
            instructionAdapter.visitFieldInsn(this.isStaticPut ? 178 : 180, this.owner.getInternalName(), this.f697name, this.type.getDescriptor());
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(6);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(7);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.visitFieldInsn(this.isStaticStore ? 179 : 181, this.owner.getInternalName(), this.f697name, this.type.getDescriptor());
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver
        protected StackValueWithSimpleReceiver changeReceiver(StackValue stackValue) {
            if (stackValue == null) {
                $$$reportNull$$$0(8);
            }
            return field(this, stackValue);
        }
    }

    public static class Property extends StackValueWithSimpleReceiver {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final Type backingFieldOwner;
        private final ExpressionCodegen codegen;
        private final KotlinType delegateKotlinType;
        private final PropertyDescriptor descriptor;
        private final String fieldName;
        private final CallableMethod getter;
        private final ResolvedCall resolvedCall;
        private final CallableMethod setter;
        private final boolean skipLateinitAssertion;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                case 6:
                case 8:
                case 10:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 2:
                    objArr[0] = "receiver";
                    break;
                case 3:
                    objArr[0] = "codegen";
                    break;
                case 4:
                    objArr[0] = "delegating";
                    break;
                case 5:
                    objArr[0] = "marker";
                    break;
                case 7:
                case 9:
                case 11:
                case 13:
                case 15:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 12:
                    objArr[0] = "rightSide";
                    break;
                case 14:
                    objArr[0] = "topOfStackType";
                    break;
                default:
                    objArr[0] = "descriptor";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Property";
            switch (i) {
                case 6:
                case 7:
                    objArr[2] = "putSelector";
                    break;
                case 8:
                case 9:
                    objArr[2] = "inlineConstantIfNeeded";
                    break;
                case 10:
                case 11:
                    objArr[2] = "inlineConstant";
                    break;
                case 12:
                case 13:
                    objArr[2] = "store";
                    break;
                case 14:
                case 15:
                    objArr[2] = "storeSelector";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Property(PropertyDescriptor propertyDescriptor, Type type, CallableMethod callableMethod, CallableMethod callableMethod2, boolean z, String str, Type type2, StackValue stackValue, ExpressionCodegen expressionCodegen, ResolvedCall resolvedCall, boolean z2, KotlinType kotlinType) {
            super(type2, propertyDescriptor.getType(), isStatic(z, callableMethod), isStatic(z, callableMethod2), stackValue, true);
            if (propertyDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (type2 == null) {
                $$$reportNull$$$0(1);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(2);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(3);
            }
            this.backingFieldOwner = type;
            this.getter = callableMethod;
            this.setter = callableMethod2;
            this.descriptor = propertyDescriptor;
            this.fieldName = str;
            this.codegen = expressionCodegen;
            this.resolvedCall = resolvedCall;
            this.skipLateinitAssertion = z2;
            this.delegateKotlinType = kotlinType;
        }

        private static class DelegatePropertyConstructorMarker {
            public static final DelegatePropertyConstructorMarker MARKER = new DelegatePropertyConstructorMarker();

            private DelegatePropertyConstructorMarker() {
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private Property(Property property, DelegatePropertyConstructorMarker delegatePropertyConstructorMarker) {
            super(property.type, property.delegateKotlinType, property.isStaticPut, property.isStaticStore, property.receiver, true);
            if (property == null) {
                $$$reportNull$$$0(4);
            }
            if (delegatePropertyConstructorMarker == null) {
                $$$reportNull$$$0(5);
            }
            this.backingFieldOwner = property.backingFieldOwner;
            this.getter = property.getter;
            this.setter = property.setter;
            this.descriptor = property.descriptor;
            this.fieldName = property.fieldName;
            this.codegen = property.codegen;
            this.resolvedCall = property.resolvedCall;
            this.skipLateinitAssertion = property.skipLateinitAssertion;
            this.delegateKotlinType = null;
        }

        public Property getDelegateOrNull() {
            if (this.delegateKotlinType == null) {
                return null;
            }
            return new Property(this, DelegatePropertyConstructorMarker.MARKER);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(6);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(7);
            }
            if (this.getter == null) {
                if (inlineConstantIfNeeded(type, kotlinType, instructionAdapter)) {
                    return;
                }
                instructionAdapter.visitFieldInsn(this.isStaticPut ? 178 : 180, this.backingFieldOwner.getInternalName(), this.fieldName, this.type.getDescriptor());
                if (!this.skipLateinitAssertion && this.descriptor.isLateInit()) {
                    CallableMemberDescriptor contextDescriptor = this.codegen.context.getContextDescriptor();
                    if (!(contextDescriptor instanceof AccessorForPropertyBackingField) || ((AccessorForPropertyBackingField) contextDescriptor).getAccessorKind() != AccessorKind.IN_CLASS_COMPANION) {
                        StackValue.genNonNullAssertForLateinit(instructionAdapter, this.descriptor.getShortName().asString());
                    }
                }
                coerceTo(type, kotlinType, instructionAdapter);
                return;
            }
            PropertyGetterDescriptor getter = this.descriptor.getGetter();
            if (this.resolvedCall != null && getter.isInline()) {
                ExpressionCodegen expressionCodegen = this.codegen;
                ResolvedCall<?> resolvedCall = this.resolvedCall;
                CallGenerator orCreateCallGenerator = expressionCodegen.getOrCreateCallGenerator(resolvedCall, ((PropertyDescriptor) resolvedCall.getResultingDescriptor()).getGetter());
                orCreateCallGenerator.processHiddenParameters();
                orCreateCallGenerator.putHiddenParamsIntoLocals();
                orCreateCallGenerator.genCall(this.getter, this.resolvedCall, false, this.codegen);
            } else {
                this.getter.genInvokeInstruction(instructionAdapter);
            }
            Type returnType = this.getter.getReturnType();
            KotlinType returnType2 = getter.getReturnType();
            if (DescriptorUtils.isAnnotationClass(this.descriptor.getPackageFragment())) {
                if (this.type.equals(AsmTypes.K_CLASS_TYPE)) {
                    AsmUtil.wrapJavaClassIntoKClass(instructionAdapter);
                    returnType = AsmTypes.K_CLASS_TYPE;
                } else if (this.type.equals(AsmTypes.K_CLASS_ARRAY_TYPE)) {
                    AsmUtil.wrapJavaClassesIntoKClasses(instructionAdapter);
                    returnType = AsmTypes.K_CLASS_ARRAY_TYPE;
                }
                returnType2 = null;
            }
            coerce(returnType, returnType2, type, kotlinType, instructionAdapter);
            PropertyDescriptor propertyDescriptor = this.descriptor;
            if (propertyDescriptor instanceof AccessorForPropertyBackingField) {
                PropertyDescriptor calleeDescriptor = ((AccessorForPropertyBackingField) propertyDescriptor).getCalleeDescriptor();
                if (!this.skipLateinitAssertion && calleeDescriptor.isLateInit() && DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(calleeDescriptor) && !JvmCodegenUtil.couldUseDirectAccessToProperty(calleeDescriptor, true, false, this.codegen.context, false)) {
                    StackValue.genNonNullAssertForLateinit(instructionAdapter, calleeDescriptor.getShortName().asString());
                }
            }
            KotlinType returnType3 = this.descriptor.getReturnType();
            if (returnType3 == null || !KotlinBuiltIns.isNothing(returnType3)) {
                return;
            }
            instructionAdapter.aconst(null);
            instructionAdapter.athrow();
        }

        private boolean inlineConstantIfNeeded(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(8);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(9);
            }
            if (JvmCodegenUtil.isInlinedJavaConstProperty(this.descriptor)) {
                return inlineConstant(type, kotlinType, instructionAdapter);
            }
            if (this.descriptor.isConst() && this.codegen.getState().getConfig().getShouldInlineConstVals()) {
                return inlineConstant(type, kotlinType, instructionAdapter);
            }
            return false;
        }

        private boolean inlineConstant(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(10);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(11);
            }
            ConstantValue<?> compileTimeInitializer = this.descriptor.mo6754getCompileTimeInitializer();
            if (compileTimeInitializer == null) {
                return false;
            }
            Object value = compileTimeInitializer.getValue();
            if (this.type == Type.FLOAT_TYPE && (value instanceof Double)) {
                value = Float.valueOf(((Double) value).floatValue());
            }
            StackValue.constant(value, this.type, this.kotlinType).putSelector(type, kotlinType, instructionAdapter);
            return true;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver, org.jetbrains.kotlin.codegen.StackValue
        public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
            if (stackValue == null) {
                $$$reportNull$$$0(12);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(13);
            }
            PropertySetterDescriptor setter = this.descriptor.getSetter();
            if (this.resolvedCall != null && setter != null && setter.isInline()) {
                ExpressionCodegen expressionCodegen = this.codegen;
                ResolvedCall<?> resolvedCall = this.resolvedCall;
                CallGenerator orCreateCallGenerator = expressionCodegen.getOrCreateCallGenerator(resolvedCall, ((PropertyDescriptor) resolvedCall.getResultingDescriptor()).getSetter());
                if (!z) {
                    putReceiver(instructionAdapter, false);
                }
                orCreateCallGenerator.processHiddenParameters();
                orCreateCallGenerator.putValueIfNeeded(new JvmKotlinType(((JvmMethodParameterSignature) CollectionsKt.last((List) this.setter.getValueParameters())).getAsmType(), ((ValueParameterDescriptor) CollectionsKt.last((List) setter.getValueParameters())).getType()), stackValue);
                orCreateCallGenerator.putHiddenParamsIntoLocals();
                orCreateCallGenerator.genCall(this.setter, this.resolvedCall, false, this.codegen);
                return;
            }
            super.store(stackValue, instructionAdapter, z);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(14);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(15);
            }
            if (this.setter == null) {
                coerceFrom(type, kotlinType, instructionAdapter);
                instructionAdapter.visitFieldInsn(this.isStaticStore ? 179 : 181, this.backingFieldOwner.getInternalName(), this.fieldName, this.type.getDescriptor());
                return;
            }
            PropertySetterDescriptor setter = this.descriptor.getSetter();
            coerce(type, kotlinType, (Type) ArraysKt.last(this.setter.getParameterTypes()), setter != null ? ((ValueParameterDescriptor) CollectionsKt.last((List) setter.getValueParameters())).getReturnType() : null, instructionAdapter);
            this.setter.genInvokeInstruction(instructionAdapter);
            Type returnType = this.setter.getReturnType();
            if (returnType != Type.VOID_TYPE) {
                AsmUtil.pop(instructionAdapter, returnType);
            }
        }

        private static boolean isStatic(boolean z, CallableMethod callableMethod) {
            JvmMethodParameterKind kind;
            if (z && callableMethod == null) {
                return true;
            }
            if (callableMethod == null || !callableMethod.isStaticCall()) {
                return false;
            }
            Iterator<JvmMethodParameterSignature> it = callableMethod.getValueParameters().iterator();
            while (it.hasNext() && (kind = it.next().getKind()) != JvmMethodParameterKind.VALUE) {
                if (kind == JvmMethodParameterKind.CONTEXT_RECEIVER || kind == JvmMethodParameterKind.RECEIVER || kind == JvmMethodParameterKind.THIS) {
                    return false;
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void genNonNullAssertForLateinit(InstructionAdapter instructionAdapter, String str) {
        if (instructionAdapter == null) {
            $$$reportNull$$$0(159);
        }
        if (str == null) {
            $$$reportNull$$$0(160);
        }
        instructionAdapter.dup();
        Label label = new Label();
        instructionAdapter.ifnonnull(label);
        instructionAdapter.visitLdcInsn(str);
        instructionAdapter.invokestatic("kotlin/jvm/internal/Intrinsics", "throwUninitializedPropertyAccessException", "(Ljava/lang/String;)V", false);
        instructionAdapter.mark(label);
    }

    private static class Expression extends StackValue {
        private final KtExpression expression;
        private final ExpressionCodegen generator;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Expression";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public Expression(Type type, KtExpression ktExpression, ExpressionCodegen expressionCodegen) {
            super(type, expressionCodegen.kotlinType(ktExpression));
            this.expression = ktExpression;
            this.generator = expressionCodegen;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            this.generator.gen(this.expression, type, kotlinType);
        }
    }

    public static class Shared extends StackValueWithSimpleReceiver {
        private final int index;
        private final boolean isLateinit;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final Name f700name;

        /* JADX WARN: Code duplicated, block: B:8:0x0016  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 2) {
                objArr[0] = "topOfStackType";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Shared";
            if (i == 2 || i == 3) {
                objArr[2] = "storeSelector";
            } else {
                objArr[2] = "putSelector";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public Shared(int i, Type type, KotlinType kotlinType, boolean z, Name name2) {
            super(type, kotlinType, false, false, local(i, AsmTypes.OBJECT_TYPE), false);
            this.index = i;
            if (z && name2 == null) {
                throw new IllegalArgumentException("Lateinit shared local variable should have name: #" + i + " " + type.getDescriptor());
            }
            this.isLateinit = z;
            this.f700name = name2;
        }

        public Shared(int i, Type type) {
            this(i, type, null, false, null);
        }

        public int getIndex() {
            return this.index;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            Type typeRefType = refType(this.type);
            instructionAdapter.visitFieldInsn(180, sharedTypeForType(this.type).getInternalName(), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, typeRefType.getDescriptor());
            if (this.isLateinit) {
                StackValue.genNonNullAssertForLateinit(instructionAdapter, this.f700name.asString());
            }
            coerceFrom(typeRefType, null, instructionAdapter);
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.visitFieldInsn(181, sharedTypeForType(this.type).getInternalName(), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, refType(this.type).getDescriptor());
        }
    }

    public static Type sharedTypeForType(Type type) {
        if (type == null) {
            $$$reportNull$$$0(161);
        }
        int sort = type.getSort();
        if (sort == 9 || sort == 10) {
            Type type2 = AsmTypes.OBJECT_REF_TYPE;
            if (type2 == null) {
                $$$reportNull$$$0(162);
            }
            return type2;
        }
        PrimitiveType primitiveTypeAsmPrimitiveTypeToLangPrimitiveType = AsmUtil.asmPrimitiveTypeToLangPrimitiveType(type);
        if (primitiveTypeAsmPrimitiveTypeToLangPrimitiveType == null) {
            throw new UnsupportedOperationException();
        }
        Type typeSharedTypeForPrimitive = AsmTypes.sharedTypeForPrimitive(primitiveTypeAsmPrimitiveTypeToLangPrimitiveType);
        if (typeSharedTypeForPrimitive == null) {
            $$$reportNull$$$0(163);
        }
        return typeSharedTypeForPrimitive;
    }

    public static Type refType(Type type) {
        return (type.getSort() == 10 || type.getSort() == 9) ? AsmTypes.OBJECT_TYPE : type;
    }

    public static class FieldForSharedVar extends StackValueWithSimpleReceiver {
        final boolean isLateinit;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        final String f698name;
        final Type owner;
        final Name variableName;

        /* JADX WARN: Code duplicated, block: B:10:0x001e  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 2) {
                objArr[0] = "topOfStackType";
            } else if (i == 3) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i != 4) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = "newReceiver";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$FieldForSharedVar";
            if (i == 2 || i == 3) {
                objArr[2] = "storeSelector";
            } else if (i != 4) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "changeReceiver";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public FieldForSharedVar(Type type, KotlinType kotlinType, Type type2, String str, Field field, boolean z, Name name2) {
            super(type, kotlinType, false, false, field, field.canHaveSideEffects());
            if (z && name2 == null) {
                throw new IllegalArgumentException("variableName should be non-null for captured lateinit variable " + str);
            }
            this.owner = type2;
            this.f698name = str;
            this.isLateinit = z;
            this.variableName = name2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            Type typeSharedTypeForType = sharedTypeForType(this.type);
            Type typeRefType = refType(this.type);
            instructionAdapter.visitFieldInsn(180, typeSharedTypeForType.getInternalName(), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, typeRefType.getDescriptor());
            if (this.isLateinit) {
                StackValue.genNonNullAssertForLateinit(instructionAdapter, this.variableName.asString());
            }
            coerceFrom(typeRefType, null, instructionAdapter);
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            coerceFrom(type, kotlinType, instructionAdapter);
            instructionAdapter.visitFieldInsn(181, sharedTypeForType(this.type).getInternalName(), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, refType(this.type).getDescriptor());
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver
        protected StackValueWithSimpleReceiver changeReceiver(StackValue stackValue) {
            if (stackValue == null) {
                $$$reportNull$$$0(4);
            }
            return fieldForSharedVar(this, stackValue);
        }
    }

    private static class ThisOuter extends StackValue {
        private final ExpressionCodegen codegen;
        private final boolean coerceType;
        private final ClassDescriptor descriptor;
        private final boolean isSuper;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$ThisOuter";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public ThisOuter(ExpressionCodegen expressionCodegen, ClassDescriptor classDescriptor, boolean z, boolean z2) {
            super(AsmTypes.OBJECT_TYPE, false);
            this.codegen = expressionCodegen;
            this.descriptor = classDescriptor;
            this.isSuper = z;
            this.coerceType = z2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            StackValue stackValueGenerateThisOrOuter = this.codegen.generateThisOrOuter(this.descriptor, this.isSuper);
            if (!this.coerceType) {
                type = stackValueGenerateThisOrOuter.type;
            }
            if (!this.coerceType) {
                kotlinType = stackValueGenerateThisOrOuter.kotlinType;
            }
            stackValueGenerateThisOrOuter.put(type, kotlinType, instructionAdapter);
        }
    }

    private static class PostIncrement extends StackValue {
        private final int increment;
        private final int index;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$PostIncrement";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public PostIncrement(int i, int i2) {
            super(Type.INT_TYPE);
            this.index = i;
            this.increment = i2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            if (!type.equals(Type.VOID_TYPE)) {
                instructionAdapter.load(this.index, Type.INT_TYPE);
                coerceTo(type, kotlinType, instructionAdapter);
            }
            instructionAdapter.iinc(this.index, this.increment);
        }
    }

    private static class PreIncrementForLocalVar extends StackValue {
        private final int increment;
        private final int index;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$PreIncrementForLocalVar";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public PreIncrementForLocalVar(int i, int i2, KotlinType kotlinType) {
            super(Type.INT_TYPE, kotlinType);
            this.index = i;
            this.increment = i2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            instructionAdapter.iinc(this.index, this.increment);
            if (type.equals(Type.VOID_TYPE)) {
                return;
            }
            instructionAdapter.load(this.index, Type.INT_TYPE);
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    private static class PrefixIncrement extends StackValue {
        private final ExpressionCodegen codegen;
        private final ResolvedCall resolvedCall;
        private StackValue value;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "value";
            } else if (i == 2) {
                objArr[0] = "codegen";
            } else if (i != 4) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$PrefixIncrement";
            if (i == 3 || i == 4) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PrefixIncrement(Type type, StackValue stackValue, ResolvedCall resolvedCall, ExpressionCodegen expressionCodegen) {
            super(type, stackValue.kotlinType);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
            if (expressionCodegen == null) {
                $$$reportNull$$$0(2);
            }
            this.value = stackValue;
            this.resolvedCall = resolvedCall;
            this.codegen = expressionCodegen;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(4);
            }
            StackValue stackValueComplexReceiver = StackValue.complexReceiver(this.value, true, false, true);
            this.value = stackValueComplexReceiver;
            stackValueComplexReceiver.put(this.type, this.kotlinType, instructionAdapter);
            this.value.store(this.codegen.invokeFunction(this.resolvedCall, StackValue.onStack(this.type, this.kotlinType)), instructionAdapter, true);
            this.value.put(this.type, this.kotlinType, instructionAdapter, true);
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    public static abstract class StackValueWithSimpleReceiver extends StackValue {
        public final boolean isStaticPut;
        public final boolean isStaticStore;
        public final StackValue receiver;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "receiver";
                    break;
                case 2:
                case 3:
                case 5:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 4:
                    objArr[0] = "rightSide";
                    break;
                case 6:
                    objArr[0] = "newReceiver";
                    break;
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$StackValueWithSimpleReceiver";
            if (i == 2) {
                objArr[2] = "putReceiver";
            } else if (i == 3) {
                objArr[2] = "dup";
            } else if (i == 4 || i == 5) {
                objArr[2] = "store";
            } else if (i != 6) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "changeReceiver";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        protected StackValueWithSimpleReceiver changeReceiver(StackValue stackValue) {
            if (stackValue == null) {
                $$$reportNull$$$0(6);
            }
            return this;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StackValueWithSimpleReceiver(Type type, KotlinType kotlinType, boolean z, boolean z2, StackValue stackValue, boolean z3) {
            super(type, kotlinType, z3);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
            this.receiver = stackValue;
            this.isStaticPut = z;
            this.isStaticStore = z2;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putReceiver(InstructionAdapter instructionAdapter, boolean z) {
            if (instructionAdapter == null) {
                $$$reportNull$$$0(2);
            }
            boolean zIsNonStaticAccess = isNonStaticAccess(z);
            if (zIsNonStaticAccess || this.receiver.canHaveSideEffects()) {
                StackValue stackValue = this.receiver;
                stackValue.put(zIsNonStaticAccess ? stackValue.type : Type.VOID_TYPE, zIsNonStaticAccess ? this.receiver.kotlinType : null, instructionAdapter);
            }
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public boolean isNonStaticAccess(boolean z) {
            if (z) {
                if (!this.isStaticPut) {
                    return true;
                }
            } else if (!this.isStaticStore) {
                return true;
            }
            return false;
        }

        public int receiverSize() {
            return this.receiver.type.getSize();
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void dup(InstructionAdapter instructionAdapter, boolean z) {
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            if (!z) {
                super.dup(instructionAdapter, false);
                return;
            }
            int iReceiverSize = isNonStaticAccess(false) ? receiverSize() : 0;
            if (iReceiverSize == -1) {
                throw new UnsupportedOperationException();
            }
            if (iReceiverSize == 0) {
                AsmUtil.dup(instructionAdapter, this.type);
                return;
            }
            if (iReceiverSize == 1) {
                if (this.type.getSize() == 2) {
                    instructionAdapter.dup2X1();
                    return;
                } else {
                    instructionAdapter.dupX1();
                    return;
                }
            }
            if (iReceiverSize != 2) {
                return;
            }
            if (this.type.getSize() == 2) {
                instructionAdapter.dup2X2();
            } else {
                instructionAdapter.dupX2();
            }
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
            if (stackValue == null) {
                $$$reportNull$$$0(4);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(5);
            }
            if (!z) {
                putReceiver(instructionAdapter, false);
            }
            stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
            storeSelector(stackValue.type, stackValue.kotlinType, instructionAdapter);
        }
    }

    private static class ComplexReceiver extends StackValue {
        private final boolean[] isReadOperations;
        private final StackValueWithSimpleReceiver originalValueWithReceiver;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$ComplexReceiver";
            objArr[2] = "putSelector";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public ComplexReceiver(StackValueWithSimpleReceiver stackValueWithSimpleReceiver, boolean[] zArr) {
            super(stackValueWithSimpleReceiver.type, stackValueWithSimpleReceiver.receiver.canHaveSideEffects());
            this.originalValueWithReceiver = stackValueWithSimpleReceiver;
            this.isReadOperations = zArr;
            if ((stackValueWithSimpleReceiver instanceof CollectionElement) && (stackValueWithSimpleReceiver.receiver instanceof CollectionElementReceiver)) {
                ((CollectionElementReceiver) stackValueWithSimpleReceiver.receiver).isComplexOperationWithDup = true;
            }
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(1);
            }
            StackValue stackValue = this.originalValueWithReceiver.receiver;
            boolean z = false;
            for (boolean z2 : this.isReadOperations) {
                if (this.originalValueWithReceiver.isNonStaticAccess(z2)) {
                    if (!z) {
                        stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
                        z = true;
                    } else {
                        stackValue.dup(instructionAdapter, false);
                    }
                }
            }
            if (z || !stackValue.canHaveSideEffects()) {
                return;
            }
            stackValue.put(Type.VOID_TYPE, null, instructionAdapter);
        }
    }

    public static class Receiver extends StackValue {
        private final StackValue[] instructions;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 2) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$Receiver";
            if (i == 1 || i == 2) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected Receiver(Type type, StackValue... stackValueArr) {
            super(type);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            this.instructions = stackValueArr;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(1);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(2);
            }
            for (StackValue stackValue : this.instructions) {
                stackValue.put(stackValue.type, stackValue.kotlinType, instructionAdapter);
            }
        }
    }

    public static class DelegatedForComplexReceiver extends StackValueWithSimpleReceiver {
        public final StackValueWithSimpleReceiver originalValue;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "originalValue";
                    break;
                case 2:
                    objArr[0] = "receiver";
                    break;
                case 3:
                default:
                    objArr[0] = ModuleXmlParser.TYPE;
                    break;
                case 4:
                case 6:
                case 8:
                case 9:
                    objArr[0] = RegisterSpec.PREFIX;
                    break;
                case 5:
                    objArr[0] = "rightSide";
                    break;
                case 7:
                    objArr[0] = "topOfStackType";
                    break;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$DelegatedForComplexReceiver";
            switch (i) {
                case 3:
                case 4:
                    objArr[2] = "putSelector";
                    break;
                case 5:
                case 6:
                    objArr[2] = "store";
                    break;
                case 7:
                case 8:
                    objArr[2] = "storeSelector";
                    break;
                case 9:
                    objArr[2] = "dup";
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DelegatedForComplexReceiver(Type type, StackValueWithSimpleReceiver stackValueWithSimpleReceiver, ComplexReceiver complexReceiver) {
            super(type, null, bothReceiverStatic(stackValueWithSimpleReceiver), bothReceiverStatic(stackValueWithSimpleReceiver), complexReceiver, stackValueWithSimpleReceiver.canHaveSideEffects());
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValueWithSimpleReceiver == null) {
                $$$reportNull$$$0(1);
            }
            if (complexReceiver == null) {
                $$$reportNull$$$0(2);
            }
            this.originalValue = stackValueWithSimpleReceiver;
        }

        private static boolean bothReceiverStatic(StackValueWithSimpleReceiver stackValueWithSimpleReceiver) {
            return (stackValueWithSimpleReceiver.isNonStaticAccess(true) || stackValueWithSimpleReceiver.isNonStaticAccess(false)) ? false : true;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(4);
            }
            this.originalValue.putSelector(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver, org.jetbrains.kotlin.codegen.StackValue
        public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
            if (stackValue == null) {
                $$$reportNull$$$0(5);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(6);
            }
            if (!z) {
                putReceiver(instructionAdapter, false);
            }
            this.originalValue.store(stackValue, instructionAdapter, true);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void storeSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(7);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(8);
            }
            this.originalValue.storeSelector(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver, org.jetbrains.kotlin.codegen.StackValue
        public void dup(InstructionAdapter instructionAdapter, boolean z) {
            if (instructionAdapter == null) {
                $$$reportNull$$$0(9);
            }
            this.originalValue.dup(instructionAdapter, z);
        }
    }

    public static StackValue complexWriteReadReceiver(StackValue stackValue) {
        return complexReceiver(stackValue, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static StackValue complexReceiver(StackValue stackValue, boolean... zArr) {
        if (stackValue instanceof Delegate) {
            throwUnsupportedComplexOperation(((Delegate) stackValue).variableDescriptor);
        }
        if (!(stackValue instanceof StackValueWithSimpleReceiver)) {
            return stackValue;
        }
        Type type = stackValue.type;
        StackValueWithSimpleReceiver stackValueWithSimpleReceiver = (StackValueWithSimpleReceiver) stackValue;
        return new DelegatedForComplexReceiver(type, stackValueWithSimpleReceiver, new ComplexReceiver(stackValueWithSimpleReceiver, zArr));
    }

    static class SafeCall extends StackValue {
        private final Label ifNull;
        private final StackValue receiver;
        private final Type type;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "value";
            } else if (i != 3) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$SafeCall";
            if (i == 2 || i == 3) {
                objArr[2] = "putSelector";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SafeCall(Type type, KotlinType kotlinType, StackValue stackValue, Label label) {
            super(type, kotlinType);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            if (stackValue == null) {
                $$$reportNull$$$0(1);
            }
            this.type = type;
            this.receiver = stackValue;
            this.ifNull = label;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(2);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(3);
            }
            this.receiver.put(this.type, this.kotlinType, instructionAdapter);
            if (this.ifNull != null) {
                instructionAdapter.dup();
                instructionAdapter.ifnull(this.ifNull);
            }
            coerceTo(type, kotlinType, instructionAdapter);
        }
    }

    static class SafeFallback extends StackValueWithSimpleReceiver {
        private final Label ifNull;

        /* JADX WARN: Code duplicated, block: B:8:0x0016  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 2) {
                objArr[0] = RegisterSpec.PREFIX;
            } else if (i == 3) {
                objArr[0] = "rightSide";
            } else if (i != 4) {
                objArr[0] = ModuleXmlParser.TYPE;
            } else {
                objArr[0] = RegisterSpec.PREFIX;
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/StackValue$SafeFallback";
            if (i == 1 || i == 2) {
                objArr[2] = "putSelector";
            } else if (i == 3 || i == 4) {
                objArr[2] = "store";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SafeFallback(Type type, KotlinType kotlinType, Label label, StackValue stackValue) {
            super(type, kotlinType, false, false, stackValue, true);
            if (type == null) {
                $$$reportNull$$$0(0);
            }
            this.ifNull = label;
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue
        public void putSelector(Type type, KotlinType kotlinType, InstructionAdapter instructionAdapter) {
            if (type == null) {
                $$$reportNull$$$0(1);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(2);
            }
            Label label = new Label();
            instructionAdapter.goTo(label);
            instructionAdapter.mark(this.ifNull);
            instructionAdapter.pop();
            if (!this.type.equals(Type.VOID_TYPE)) {
                instructionAdapter.aconst(null);
            }
            instructionAdapter.mark(label);
            coerceTo(type, kotlinType, instructionAdapter);
        }

        @Override // org.jetbrains.kotlin.codegen.StackValue.StackValueWithSimpleReceiver, org.jetbrains.kotlin.codegen.StackValue
        public void store(StackValue stackValue, InstructionAdapter instructionAdapter, boolean z) {
            if (stackValue == null) {
                $$$reportNull$$$0(3);
            }
            if (instructionAdapter == null) {
                $$$reportNull$$$0(4);
            }
            this.receiver.store(stackValue, instructionAdapter, z);
            Label label = new Label();
            instructionAdapter.goTo(label);
            instructionAdapter.mark(this.ifNull);
            instructionAdapter.pop();
            instructionAdapter.mark(label);
        }
    }

    private static void throwUnsupportedComplexOperation(CallableDescriptor callableDescriptor) {
        if (callableDescriptor == null) {
            $$$reportNull$$$0(164);
        }
        throw new RuntimeException("Augmented assignment and increment are not supported for local delegated properties and inline properties: " + callableDescriptor);
    }
}
