package org.luaj.vm2.luajc;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import org.apache.bcel.generic.AASTORE;
import org.apache.bcel.generic.ALOAD;
import org.apache.bcel.generic.ANEWARRAY;
import org.apache.bcel.generic.ASTORE;
import org.apache.bcel.generic.ArrayInstruction;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.BranchInstruction;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.CompoundInstruction;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.GOTO;
import org.apache.bcel.generic.IFEQ;
import org.apache.bcel.generic.IFNE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.Type;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Prototype;

/* JADX INFO: loaded from: classes6.dex */
public class JavaBuilder {
    private static final String[][] ARG_NAMES_N;
    private static final Type[] ARG_TYPES_BUFFER;
    private static final Type[] ARG_TYPES_CHARARRAY;
    private static final Type[] ARG_TYPES_DOUBLE;
    private static final Type[] ARG_TYPES_INT;
    private static final Type[] ARG_TYPES_INT_INT;
    private static final Type[] ARG_TYPES_INT_LUAVALUE;
    private static final Type[] ARG_TYPES_INT_VARARGS;
    private static final Type[] ARG_TYPES_LUAVALUE;
    private static final Type[] ARG_TYPES_LUAVALUEARRAY;
    private static final Type[] ARG_TYPES_LUAVALUEARRAY_VARARGS;
    private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE;
    private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE;
    private static final Type[] ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS;
    private static final Type[] ARG_TYPES_LUAVALUE_STRINGARRAY;
    private static final Type[] ARG_TYPES_LUAVALUE_VARARGS;
    private static final Type[][] ARG_TYPES_N;
    private static final Type[] ARG_TYPES_NONE;
    private static final Type[] ARG_TYPES_STRING;
    private static final Type[] ARG_TYPES_STRINGARRAY;
    private static final Type[] ARG_TYPES_VARARGS;
    public static final int BRANCH_GOTO = 1;
    public static final int BRANCH_IFEQ = 3;
    public static final int BRANCH_IFNE = 2;
    private static final String[] METH_NAME_N;
    private static final String NAME_VARRESULT = "v";
    private static final String PREFIX_CONSTANT = "k";
    private static final String PREFIX_PLAIN_SLOT = "s";
    private static final String PREFIX_UPVALUE = "u";
    private static final String PREFIX_UPVALUE_SLOT = "a";
    private static final ObjectType[] RETURN_TYPE_N;
    private static final String STR_BUFFER;
    private static final String STR_FUNC0;
    private static final String STR_FUNC1;
    private static final String STR_FUNC2;
    private static final String STR_FUNC3;
    private static final String STR_FUNCV;
    private static final String STR_JSEPLATFORM = "org.luaj.vm2.lib.jse.JsePlatform";
    private static final String STR_LUABOOLEAN;
    private static final String STR_LUAINTEGER;
    private static final String STR_LUANUMBER;
    private static final String STR_LUASTRING;
    private static final String STR_LUATABLE;
    private static final String STR_LUAVALUE;
    private static final String STR_STRING;
    private static final String STR_VARARGS;
    private static int SUPERTYPE_VARARGS;
    private static final String[] SUPER_NAME_N;
    private static final ObjectType TYPE_BUFFER;
    private static final ArrayType TYPE_CHARARRAY;
    private static final ArrayType TYPE_LOCALUPVALUE;
    private static final ObjectType TYPE_LUABOOLEAN;
    private static final ObjectType TYPE_LUAINTEGER;
    private static final ObjectType TYPE_LUANUMBER;
    private static final ObjectType TYPE_LUASTRING;
    private static final ObjectType TYPE_LUATABLE;
    private static final ObjectType TYPE_LUAVALUE;
    private static final ObjectType TYPE_STRING;
    private static final ArrayType TYPE_STRINGARRAY;
    private static final ObjectType TYPE_VARARGS;
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$org$luaj$vm2$Buffer;
    static /* synthetic */ Class class$org$luaj$vm2$LuaBoolean;
    static /* synthetic */ Class class$org$luaj$vm2$LuaInteger;
    static /* synthetic */ Class class$org$luaj$vm2$LuaNumber;
    static /* synthetic */ Class class$org$luaj$vm2$LuaString;
    static /* synthetic */ Class class$org$luaj$vm2$LuaTable;
    static /* synthetic */ Class class$org$luaj$vm2$LuaValue;
    static /* synthetic */ Class class$org$luaj$vm2$Varargs;
    static /* synthetic */ Class class$org$luaj$vm2$lib$OneArgFunction;
    static /* synthetic */ Class class$org$luaj$vm2$lib$ThreeArgFunction;
    static /* synthetic */ Class class$org$luaj$vm2$lib$TwoArgFunction;
    static /* synthetic */ Class class$org$luaj$vm2$lib$VarArgFunction;
    static /* synthetic */ Class class$org$luaj$vm2$lib$ZeroArgFunction;
    private InstructionHandle beginningOfLuaInstruction;
    private final InstructionHandle[] branchDestHandles;
    private final BranchInstruction[] branches;
    private final ClassGen cg;
    private final String classname;
    private final ConstantPoolGen cp;
    private final InstructionFactory factory;
    private final InstructionList init;
    private final InstructionHandle[] lastInstrHandles;
    private final InstructionList main;
    private final MethodGen mg;
    private final Prototype p;
    private final ProtoInfo pi;
    private int superclassType;
    private final int[] targets;
    private LocalVariableGen varresult = null;
    private int prev_line = -1;
    private Map plainSlotVars = new HashMap();
    private Map upvalueSlotVars = new HashMap();
    private Map localVarGenBySlot = new HashMap();
    private Map constants = new HashMap();

    static {
        Class clsClass$ = class$org$luaj$vm2$Varargs;
        if (clsClass$ == null) {
            clsClass$ = class$("org.luaj.vm2.Varargs");
            class$org$luaj$vm2$Varargs = clsClass$;
        }
        String name2 = clsClass$.getName();
        STR_VARARGS = name2;
        Class clsClass$2 = class$org$luaj$vm2$LuaValue;
        if (clsClass$2 == null) {
            clsClass$2 = class$("org.luaj.vm2.LuaValue");
            class$org$luaj$vm2$LuaValue = clsClass$2;
        }
        String name3 = clsClass$2.getName();
        STR_LUAVALUE = name3;
        Class clsClass$3 = class$org$luaj$vm2$LuaString;
        if (clsClass$3 == null) {
            clsClass$3 = class$("org.luaj.vm2.LuaString");
            class$org$luaj$vm2$LuaString = clsClass$3;
        }
        String name4 = clsClass$3.getName();
        STR_LUASTRING = name4;
        Class clsClass$4 = class$org$luaj$vm2$LuaInteger;
        if (clsClass$4 == null) {
            clsClass$4 = class$("org.luaj.vm2.LuaInteger");
            class$org$luaj$vm2$LuaInteger = clsClass$4;
        }
        String name5 = clsClass$4.getName();
        STR_LUAINTEGER = name5;
        Class clsClass$5 = class$org$luaj$vm2$LuaNumber;
        if (clsClass$5 == null) {
            clsClass$5 = class$("org.luaj.vm2.LuaNumber");
            class$org$luaj$vm2$LuaNumber = clsClass$5;
        }
        String name6 = clsClass$5.getName();
        STR_LUANUMBER = name6;
        Class clsClass$6 = class$org$luaj$vm2$LuaBoolean;
        if (clsClass$6 == null) {
            clsClass$6 = class$("org.luaj.vm2.LuaBoolean");
            class$org$luaj$vm2$LuaBoolean = clsClass$6;
        }
        String name7 = clsClass$6.getName();
        STR_LUABOOLEAN = name7;
        Class clsClass$7 = class$org$luaj$vm2$LuaTable;
        if (clsClass$7 == null) {
            clsClass$7 = class$("org.luaj.vm2.LuaTable");
            class$org$luaj$vm2$LuaTable = clsClass$7;
        }
        String name8 = clsClass$7.getName();
        STR_LUATABLE = name8;
        Class clsClass$8 = class$org$luaj$vm2$Buffer;
        if (clsClass$8 == null) {
            clsClass$8 = class$("org.luaj.vm2.Buffer");
            class$org$luaj$vm2$Buffer = clsClass$8;
        }
        String name9 = clsClass$8.getName();
        STR_BUFFER = name9;
        Class clsClass$9 = class$java$lang$String;
        if (clsClass$9 == null) {
            clsClass$9 = class$(CommonClassNames.JAVA_LANG_STRING);
            class$java$lang$String = clsClass$9;
        }
        String name10 = clsClass$9.getName();
        STR_STRING = name10;
        ObjectType objectType = new ObjectType(name2);
        TYPE_VARARGS = objectType;
        ObjectType objectType2 = new ObjectType(name3);
        TYPE_LUAVALUE = objectType2;
        TYPE_LUASTRING = new ObjectType(name4);
        TYPE_LUAINTEGER = new ObjectType(name5);
        TYPE_LUANUMBER = new ObjectType(name6);
        TYPE_LUABOOLEAN = new ObjectType(name7);
        TYPE_LUATABLE = new ObjectType(name8);
        Type objectType3 = new ObjectType(name9);
        TYPE_BUFFER = objectType3;
        ObjectType objectType4 = new ObjectType(name10);
        TYPE_STRING = objectType4;
        TYPE_LOCALUPVALUE = new ArrayType(objectType2, 1);
        Type arrayType = new ArrayType(Type.CHAR, 1);
        TYPE_CHARARRAY = arrayType;
        Type arrayType2 = new ArrayType(objectType4, 1);
        TYPE_STRINGARRAY = arrayType2;
        Class clsClass$10 = class$org$luaj$vm2$lib$VarArgFunction;
        if (clsClass$10 == null) {
            clsClass$10 = class$("org.luaj.vm2.lib.VarArgFunction");
            class$org$luaj$vm2$lib$VarArgFunction = clsClass$10;
        }
        String name11 = clsClass$10.getName();
        STR_FUNCV = name11;
        Class clsClass$11 = class$org$luaj$vm2$lib$ZeroArgFunction;
        if (clsClass$11 == null) {
            clsClass$11 = class$("org.luaj.vm2.lib.ZeroArgFunction");
            class$org$luaj$vm2$lib$ZeroArgFunction = clsClass$11;
        }
        String name12 = clsClass$11.getName();
        STR_FUNC0 = name12;
        Class clsClass$12 = class$org$luaj$vm2$lib$OneArgFunction;
        if (clsClass$12 == null) {
            clsClass$12 = class$("org.luaj.vm2.lib.OneArgFunction");
            class$org$luaj$vm2$lib$OneArgFunction = clsClass$12;
        }
        String name13 = clsClass$12.getName();
        STR_FUNC1 = name13;
        Class clsClass$13 = class$org$luaj$vm2$lib$TwoArgFunction;
        if (clsClass$13 == null) {
            clsClass$13 = class$("org.luaj.vm2.lib.TwoArgFunction");
            class$org$luaj$vm2$lib$TwoArgFunction = clsClass$13;
        }
        String name14 = clsClass$13.getName();
        STR_FUNC2 = name14;
        Class clsClass$14 = class$org$luaj$vm2$lib$ThreeArgFunction;
        if (clsClass$14 == null) {
            clsClass$14 = class$("org.luaj.vm2.lib.ThreeArgFunction");
            class$org$luaj$vm2$lib$ThreeArgFunction = clsClass$14;
        }
        String name15 = clsClass$14.getName();
        STR_FUNC3 = name15;
        Type[] typeArr = new Type[0];
        ARG_TYPES_NONE = typeArr;
        ARG_TYPES_INT = new Type[]{Type.INT};
        ARG_TYPES_DOUBLE = new Type[]{Type.DOUBLE};
        ARG_TYPES_STRING = new Type[]{Type.STRING};
        ARG_TYPES_CHARARRAY = new Type[]{arrayType};
        ARG_TYPES_INT_LUAVALUE = new Type[]{Type.INT, objectType2};
        ARG_TYPES_INT_VARARGS = new Type[]{Type.INT, objectType};
        ARG_TYPES_LUAVALUE_VARARGS = new Type[]{objectType2, objectType};
        ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS = new Type[]{objectType2, objectType2, objectType};
        ARG_TYPES_LUAVALUEARRAY = new Type[]{new ArrayType(objectType2, 1)};
        ARG_TYPES_LUAVALUEARRAY_VARARGS = new Type[]{new ArrayType(objectType2, 1), objectType};
        Type[] typeArr2 = {objectType2, objectType2, objectType2};
        ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE = typeArr2;
        Type[] typeArr3 = {objectType};
        ARG_TYPES_VARARGS = typeArr3;
        Type[] typeArr4 = {objectType2, objectType2};
        ARG_TYPES_LUAVALUE_LUAVALUE = typeArr4;
        ARG_TYPES_INT_INT = new Type[]{Type.INT, Type.INT};
        Type[] typeArr5 = {objectType2};
        ARG_TYPES_LUAVALUE = typeArr5;
        ARG_TYPES_BUFFER = new Type[]{objectType3};
        ARG_TYPES_STRINGARRAY = new Type[]{arrayType2};
        ARG_TYPES_LUAVALUE_STRINGARRAY = new Type[]{objectType2, arrayType2};
        SUPER_NAME_N = new String[]{name12, name13, name14, name15, name11};
        RETURN_TYPE_N = new ObjectType[]{objectType2, objectType2, objectType2, objectType2, objectType};
        ARG_TYPES_N = new Type[][]{typeArr, typeArr5, typeArr4, typeArr2, typeArr3};
        ARG_NAMES_N = new String[][]{new String[0], new String[]{ParameterDescription.NAME_PREFIX}, new String[]{"arg1", "arg2"}, new String[]{"arg1", "arg2", "arg3"}, new String[]{"args"}};
        METH_NAME_N = new String[]{"call", "call", "call", "call", "onInvoke"};
        SUPERTYPE_VARARGS = 4;
    }

    public JavaBuilder(ProtoInfo protoInfo, String str, String str2) {
        this.pi = protoInfo;
        Prototype prototype = protoInfo.prototype;
        this.p = prototype;
        this.classname = str;
        this.superclassType = prototype.numparams;
        if (prototype.is_vararg != 0 || this.superclassType >= SUPERTYPE_VARARGS) {
            this.superclassType = SUPERTYPE_VARARGS;
        }
        int length = prototype.code.length;
        for (int i = 0; i < length; i++) {
            int i2 = this.p.code[i];
            int iGET_OPCODE = Lua.GET_OPCODE(i2);
            if (iGET_OPCODE == 30 || (iGET_OPCODE == 31 && (Lua.GETARG_B(i2) < 1 || Lua.GETARG_B(i2) > 2))) {
                this.superclassType = SUPERTYPE_VARARGS;
                break;
            }
        }
        ClassGen classGen = new ClassGen(str, SUPER_NAME_N[this.superclassType], str2, 33, (String[]) null);
        this.cg = classGen;
        this.cp = classGen.getConstantPool();
        this.factory = new InstructionFactory(classGen);
        this.init = new InstructionList();
        this.main = new InstructionList();
        for (int i3 = 0; i3 < this.p.upvalues.length; i3++) {
            this.cg.addField(new FieldGen(0, protoInfo.isReadWriteUpvalue(protoInfo.upvals[i3]) ? TYPE_LOCALUPVALUE : TYPE_LUAVALUE, upvalueName(i3), this.cp).getField());
        }
        Type[] typeArr = RETURN_TYPE_N;
        int i4 = this.superclassType;
        this.mg = new MethodGen(17, typeArr[i4], ARG_TYPES_N[i4], ARG_NAMES_N[i4], METH_NAME_N[i4], STR_LUAVALUE, this.main, this.cp);
        initializeSlots();
        int length2 = this.p.code.length;
        this.targets = new int[length2];
        this.branches = new BranchInstruction[length2];
        this.branchDestHandles = new InstructionHandle[length2];
        this.lastInstrHandles = new InstructionHandle[length2];
    }

    private void append(BranchInstruction branchInstruction) {
        conditionalSetBeginningOfLua(this.main.append(branchInstruction));
    }

    private void append(CompoundInstruction compoundInstruction) {
        conditionalSetBeginningOfLua(this.main.append(compoundInstruction));
    }

    private void append(Instruction instruction) {
        conditionalSetBeginningOfLua(this.main.append(instruction));
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private void conditionalSetBeginningOfLua(InstructionHandle instructionHandle) {
        if (this.beginningOfLuaInstruction == null) {
            this.beginningOfLuaInstruction = instructionHandle;
        }
    }

    private String createLuaDoubleField(double d) {
        String string = new StringBuffer("k").append(this.constants.size()).toString();
        ObjectType objectType = TYPE_LUAVALUE;
        this.cg.addField(new FieldGen(24, objectType, string, this.cp).getField());
        this.init.append(new PUSH(this.cp, d));
        this.init.append(this.factory.createInvoke(STR_LUAVALUE, "valueOf", TYPE_LUANUMBER, ARG_TYPES_DOUBLE, (short) 184));
        this.init.append(this.factory.createPutStatic(this.classname, string, objectType));
        return string;
    }

    private String createLuaIntegerField(int i) {
        String string = new StringBuffer("k").append(this.constants.size()).toString();
        ObjectType objectType = TYPE_LUAVALUE;
        this.cg.addField(new FieldGen(24, objectType, string, this.cp).getField());
        this.init.append(new PUSH(this.cp, i));
        this.init.append(this.factory.createInvoke(STR_LUAVALUE, "valueOf", TYPE_LUAINTEGER, ARG_TYPES_INT, (short) 184));
        this.init.append(this.factory.createPutStatic(this.classname, string, objectType));
        return string;
    }

    private String createLuaStringField(LuaString luaString) {
        InstructionList instructionList;
        InvokeInstruction invokeInstructionCreateInvoke;
        String string = new StringBuffer("k").append(this.constants.size()).toString();
        this.cg.addField(new FieldGen(24, TYPE_LUAVALUE, string, this.cp).getField());
        LuaString luaStringCheckstring = luaString.checkstring();
        if (luaStringCheckstring.isValidUtf8()) {
            this.init.append(new PUSH(this.cp, luaString.tojstring()));
            instructionList = this.init;
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUASTRING, "valueOf", TYPE_LUASTRING, ARG_TYPES_STRING, (short) 184);
        } else {
            char[] cArr = new char[luaStringCheckstring.m_length];
            for (int i = 0; i < luaStringCheckstring.m_length; i++) {
                cArr[i] = (char) (luaStringCheckstring.m_bytes[luaStringCheckstring.m_offset + i] & 255);
            }
            this.init.append(new PUSH(this.cp, new String(cArr)));
            this.init.append(this.factory.createInvoke(STR_STRING, "toCharArray", TYPE_CHARARRAY, Type.NO_ARGS, (short) 182));
            instructionList = this.init;
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUASTRING, "valueOf", TYPE_LUASTRING, ARG_TYPES_CHARARRAY, (short) 184);
        }
        instructionList.append(invokeInstructionCreateInvoke);
        this.init.append(this.factory.createPutStatic(this.classname, string, TYPE_LUAVALUE));
        return string;
    }

    private int findSlot(int i, Map map, String str, Type type) {
        Integer numValueOf = Integer.valueOf(i);
        if (map.containsKey(numValueOf)) {
            return ((Integer) map.get(numValueOf)).intValue();
        }
        LocalVariableGen localVariableGenAddLocalVariable = this.mg.addLocalVariable(new StringBuffer().append(str).append(i).toString(), type, (InstructionHandle) null, (InstructionHandle) null);
        int index = localVariableGenAddLocalVariable.getIndex();
        map.put(numValueOf, Integer.valueOf(index));
        this.localVarGenBySlot.put(numValueOf, localVariableGenAddLocalVariable);
        return index;
    }

    private int findSlotIndex(int i, boolean z) {
        Map map;
        String str;
        ArrayType arrayType;
        if (z) {
            map = this.upvalueSlotVars;
            str = PREFIX_UPVALUE_SLOT;
            arrayType = TYPE_LOCALUPVALUE;
        } else {
            map = this.plainSlotVars;
            str = PREFIX_PLAIN_SLOT;
            arrayType = TYPE_LUAVALUE;
        }
        return findSlot(i, map, str, arrayType);
    }

    private int getVarresultIndex() {
        if (this.varresult == null) {
            this.varresult = this.mg.addLocalVariable("v", TYPE_VARARGS, (InstructionHandle) null, (InstructionHandle) null);
        }
        return this.varresult.getIndex();
    }

    private void resolveBranches() {
        InstructionHandle[] instructionHandleArr;
        int length = this.p.code.length;
        for (int i = 0; i < length; i++) {
            if (this.branches[i] != null) {
                int i2 = this.targets[i];
                while (true) {
                    instructionHandleArr = this.branchDestHandles;
                    if (i2 >= instructionHandleArr.length || instructionHandleArr[i2] != null) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (i2 >= instructionHandleArr.length) {
                    throw new IllegalArgumentException(new StringBuffer("no target at or after ").append(this.targets[i]).append(" op=").append(Lua.GET_OPCODE(this.p.code[this.targets[i]])).toString());
                }
                this.branches[i].setTarget(instructionHandleArr[i2]);
            }
        }
    }

    private static String upvalueName(int i) {
        return new StringBuffer(PREFIX_UPVALUE).append(i).toString();
    }

    public void addBranch(int i, int i2, int i3) {
        if (i2 == 2) {
            this.branches[i] = new IFNE((InstructionHandle) null);
        } else if (i2 != 3) {
            this.branches[i] = new GOTO((InstructionHandle) null);
        } else {
            this.branches[i] = new IFEQ((InstructionHandle) null);
        }
        this.targets[i] = i3;
        append(this.branches[i]);
    }

    public void areturn() {
        append((Instruction) InstructionConstants.ARETURN);
    }

    public void arg(int i) {
        InvokeInstruction invokeInstructionCreateInvoke;
        if (i == 1) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_VARARGS, "arg1", TYPE_LUAVALUE, ARG_TYPES_NONE, (short) 182);
        } else {
            append((CompoundInstruction) new PUSH(this.cp, i));
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_VARARGS, ParameterDescription.NAME_PREFIX, TYPE_LUAVALUE, ARG_TYPES_INT, (short) 182);
        }
        append((Instruction) invokeInstructionCreateInvoke);
    }

    public void binaryop(int i) {
        String str;
        switch (i) {
            case 14:
                str = "sub";
                break;
            case 15:
                str = "mul";
                break;
            case 16:
                str = "div";
                break;
            case 17:
                str = "mod";
                break;
            case 18:
                str = "pow";
                break;
            default:
                str = ProductAction.ACTION_ADD;
                break;
        }
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, str, TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short) 182));
    }

    public void call(int i) {
        InvokeInstruction invokeInstructionCreateInvoke;
        if (i == 0) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_NONE, (short) 182);
        } else if (i == 1) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short) 182);
        } else if (i == 2) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE, (short) 182);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(new StringBuffer("can't call with ").append(i).append(" args").toString());
            }
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "call", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE_LUAVALUE_LUAVALUE, (short) 182);
        }
        append((Instruction) invokeInstructionCreateInvoke);
    }

    public void closeUpvalue(int i, int i2) {
    }

    public void closureCreate(String str) {
        append((Instruction) this.factory.createNew(new ObjectType(str)));
        append((Instruction) InstructionConstants.DUP);
        append((Instruction) this.factory.createInvoke(str, "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
    }

    public void closureInitUpvalueFromLocal(String str, int i, int i2, int i3) {
        ProtoInfo protoInfo = this.pi;
        boolean zIsReadWriteUpvalue = protoInfo.isReadWriteUpvalue(protoInfo.vars[i3][i2].upvalue);
        ArrayType arrayType = zIsReadWriteUpvalue ? TYPE_LOCALUPVALUE : TYPE_LUAVALUE;
        String strUpvalueName = upvalueName(i);
        append((Instruction) new ALOAD(findSlotIndex(i3, zIsReadWriteUpvalue)));
        append((Instruction) this.factory.createFieldAccess(str, strUpvalueName, arrayType, (short) 181));
    }

    public void closureInitUpvalueFromUpvalue(String str, int i, int i2) {
        ProtoInfo protoInfo = this.pi;
        ArrayType arrayType = protoInfo.isReadWriteUpvalue(protoInfo.upvals[i2]) ? TYPE_LOCALUPVALUE : TYPE_LUAVALUE;
        String strUpvalueName = upvalueName(i2);
        String strUpvalueName2 = upvalueName(i);
        append((Instruction) InstructionConstants.THIS);
        append((Instruction) this.factory.createFieldAccess(this.classname, strUpvalueName, arrayType, (short) 180));
        append((Instruction) this.factory.createFieldAccess(str, strUpvalueName2, arrayType, (short) 181));
    }

    public void compareop(int i) {
        String str;
        if (i != 25) {
            str = i != 26 ? "eq_b" : "lteq_b";
        } else {
            str = "lt_b";
        }
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, str, Type.BOOLEAN, ARG_TYPES_LUAVALUE, (short) 182));
    }

    public byte[] completeClass(boolean z) {
        FieldInstruction fieldInstructionCreateFieldAccess;
        if (!this.init.isEmpty()) {
            MethodGen methodGen = new MethodGen(8, Type.VOID, ARG_TYPES_NONE, new String[0], MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, this.cg.getClassName(), this.init, this.cg.getConstantPool());
            this.init.append(InstructionConstants.RETURN);
            methodGen.setMaxStack();
            this.cg.addMethod(methodGen.getMethod());
            this.init.dispose();
        }
        this.cg.addEmptyConstructor(1);
        resolveBranches();
        this.mg.setMaxStack();
        this.cg.addMethod(this.mg.getMethod());
        this.main.dispose();
        if (this.p.upvalues.length == 1 && this.superclassType == SUPERTYPE_VARARGS) {
            BasicType basicType = Type.VOID;
            Type[] typeArr = ARG_TYPES_LUAVALUE;
            MethodGen methodGen2 = new MethodGen(17, basicType, typeArr, new String[]{"env"}, "initupvalue1", STR_LUAVALUE, this.main, this.cp);
            ProtoInfo protoInfo = this.pi;
            boolean zIsReadWriteUpvalue = protoInfo.isReadWriteUpvalue(protoInfo.upvals[0]);
            append((Instruction) InstructionConstants.THIS);
            append((Instruction) new ALOAD(1));
            if (zIsReadWriteUpvalue) {
                InstructionFactory instructionFactory = this.factory;
                String str = this.classname;
                ArrayType arrayType = TYPE_LOCALUPVALUE;
                append((Instruction) instructionFactory.createInvoke(str, "newupl", arrayType, typeArr, (short) 184));
                fieldInstructionCreateFieldAccess = this.factory.createFieldAccess(this.classname, upvalueName(0), arrayType, (short) 181);
            } else {
                fieldInstructionCreateFieldAccess = this.factory.createFieldAccess(this.classname, upvalueName(0), TYPE_LUAVALUE, (short) 181);
            }
            append((Instruction) fieldInstructionCreateFieldAccess);
            append((Instruction) InstructionConstants.RETURN);
            methodGen2.setMaxStack();
            this.cg.addMethod(methodGen2.getMethod());
            this.main.dispose();
        }
        if (z) {
            MethodGen methodGen3 = new MethodGen(9, Type.VOID, ARG_TYPES_STRINGARRAY, new String[]{ParameterDescription.NAME_PREFIX}, "main", this.classname, this.main, this.cp);
            append((Instruction) this.factory.createNew(this.classname));
            append((Instruction) InstructionConstants.DUP);
            append((Instruction) this.factory.createInvoke(this.classname, "<init>", Type.VOID, ARG_TYPES_NONE, (short) 183));
            append((Instruction) new ALOAD(0));
            append((Instruction) this.factory.createInvoke(STR_JSEPLATFORM, "luaMain", Type.VOID, ARG_TYPES_LUAVALUE_STRINGARRAY, (short) 184));
            append((Instruction) InstructionConstants.RETURN);
            methodGen3.setMaxStack();
            this.cg.addMethod(methodGen3.getMethod());
            this.main.dispose();
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.cg.getJavaClass().dump(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(new StringBuffer("JavaClass.dump() threw ").append(e).toString());
        }
    }

    public void concatbuffer() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "concat", TYPE_BUFFER, ARG_TYPES_BUFFER, (short) 182));
    }

    public void concatvalue() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "concat", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short) 182));
    }

    public void convertToUpvalue(int i, int i2) {
        if (this.pi.isUpvalueAssign(i, i2)) {
            append((Instruction) new ALOAD(findSlotIndex(i2, false)));
            append((Instruction) this.factory.createInvoke(this.classname, "newupl", TYPE_LOCALUPVALUE, ARG_TYPES_LUAVALUE, (short) 184));
            append((Instruction) new ASTORE(findSlotIndex(i2, true)));
        }
    }

    public void createUpvalues(int i, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i2 + i4;
            if (this.pi.isUpvalueCreate(i, i5)) {
                int iFindSlotIndex = findSlotIndex(i5, true);
                append((Instruction) this.factory.createInvoke(this.classname, "newupn", TYPE_LOCALUPVALUE, ARG_TYPES_NONE, (short) 184));
                append((Instruction) new ASTORE(iFindSlotIndex));
            }
        }
    }

    public void dup() {
        append((Instruction) InstructionConstants.DUP);
    }

    public void getTable() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "get", TYPE_LUAVALUE, ARG_TYPES_LUAVALUE, (short) 182));
    }

    public void initializeSlots() {
        int i = 0;
        createUpvalues(-1, 0, this.p.maxstacksize);
        if (this.superclassType == SUPERTYPE_VARARGS) {
            while (i < this.p.numparams) {
                if (this.pi.isInitialValueUsed(i)) {
                    append((Instruction) new ALOAD(1));
                    append((CompoundInstruction) new PUSH(this.cp, i + 1));
                    append((Instruction) this.factory.createInvoke(STR_VARARGS, ParameterDescription.NAME_PREFIX, TYPE_LUAVALUE, ARG_TYPES_INT, (short) 182));
                    storeLocal(-1, i);
                }
                i++;
            }
            append((Instruction) new ALOAD(1));
            append((CompoundInstruction) new PUSH(this.cp, this.p.numparams + 1));
            append((Instruction) this.factory.createInvoke(STR_VARARGS, "subargs", TYPE_VARARGS, ARG_TYPES_INT, (short) 182));
            append((Instruction) new ASTORE(1));
        } else {
            while (i < this.p.numparams) {
                int i2 = i + 1;
                this.plainSlotVars.put(Integer.valueOf(i), Integer.valueOf(i2));
                if (this.pi.isUpvalueCreate(-1, i)) {
                    append((Instruction) new ALOAD(i2));
                    storeLocal(-1, i);
                }
                i = i2;
            }
        }
        while (i < this.p.maxstacksize) {
            if (this.pi.isInitialValueUsed(i)) {
                loadNil();
                storeLocal(-1, i);
            }
            i++;
        }
    }

    public void invoke(int i) {
        InvokeInstruction invokeInstructionCreateInvoke;
        if (i == -1) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_VARARGS, (short) 182);
        } else if (i == 0) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_NONE, (short) 182);
        } else if (i == 1) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_VARARGS, (short) 182);
        } else if (i == 2) {
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short) 182);
        } else {
            if (i != 3) {
                throw new IllegalArgumentException(new StringBuffer("can't invoke with ").append(i).append(" args").toString());
            }
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "invoke", TYPE_VARARGS, ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS, (short) 182);
        }
        append((Instruction) invokeInstructionCreateInvoke);
    }

    public void isNil() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "isnil", Type.BOOLEAN, Type.NO_ARGS, (short) 182));
    }

    public void loadArrayArgs(int i, int i2, int i3) {
        append((CompoundInstruction) new PUSH(this.cp, i3));
        append((Instruction) new ANEWARRAY(this.cp.addClass(STR_LUAVALUE)));
        int i4 = 0;
        while (i4 < i3) {
            append((Instruction) InstructionConstants.DUP);
            append((CompoundInstruction) new PUSH(this.cp, i4));
            loadLocal(i, i2);
            append((Instruction) new AASTORE());
            i4++;
            i2++;
        }
    }

    public void loadBoolean(boolean z) {
        append((Instruction) this.factory.createFieldAccess(STR_LUAVALUE, z ? "TRUE" : "FALSE", TYPE_LUABOOLEAN, (short) 178));
    }

    public void loadConstant(LuaValue luaValue) {
        int iType = luaValue.type();
        if (iType == 0) {
            loadNil();
            return;
        }
        if (iType == 1) {
            loadBoolean(luaValue.toboolean());
            return;
        }
        if (iType != 3 && iType != 4) {
            throw new IllegalArgumentException(new StringBuffer("bad constant type: ").append(luaValue.type()).toString());
        }
        String strCreateLuaStringField = (String) this.constants.get(luaValue);
        if (strCreateLuaStringField == null) {
            if (luaValue.type() == 3) {
                strCreateLuaStringField = luaValue.isinttype() ? createLuaIntegerField(luaValue.checkint()) : createLuaDoubleField(luaValue.checkdouble());
            } else {
                strCreateLuaStringField = createLuaStringField(luaValue.checkstring());
            }
            this.constants.put(luaValue, strCreateLuaStringField);
        }
        append((Instruction) this.factory.createGetStatic(this.classname, strCreateLuaStringField, TYPE_LUAVALUE));
    }

    public void loadLocal(int i, int i2) {
        boolean zIsUpvalueRefer = this.pi.isUpvalueRefer(i, i2);
        append((Instruction) new ALOAD(findSlotIndex(i2, zIsUpvalueRefer)));
        if (zIsUpvalueRefer) {
            append((CompoundInstruction) new PUSH(this.cp, 0));
            append((Instruction) InstructionConstants.AALOAD);
        }
    }

    public void loadNil() {
        append((Instruction) this.factory.createFieldAccess(STR_LUAVALUE, "NIL", TYPE_LUAVALUE, (short) 178));
    }

    public void loadNone() {
        append((Instruction) this.factory.createFieldAccess(STR_LUAVALUE, "NONE", TYPE_LUAVALUE, (short) 178));
    }

    public void loadUpvalue(int i) {
        ArrayInstruction arrayInstructionCreateFieldAccess;
        ProtoInfo protoInfo = this.pi;
        boolean zIsReadWriteUpvalue = protoInfo.isReadWriteUpvalue(protoInfo.upvals[i]);
        append((Instruction) InstructionConstants.THIS);
        if (zIsReadWriteUpvalue) {
            append((Instruction) this.factory.createFieldAccess(this.classname, upvalueName(i), TYPE_LOCALUPVALUE, (short) 180));
            append((CompoundInstruction) new PUSH(this.cp, 0));
            arrayInstructionCreateFieldAccess = InstructionConstants.AALOAD;
        } else {
            arrayInstructionCreateFieldAccess = this.factory.createFieldAccess(this.classname, upvalueName(i), TYPE_LUAVALUE, (short) 180);
        }
        append((Instruction) arrayInstructionCreateFieldAccess);
    }

    public void loadVarargs() {
        append((Instruction) new ALOAD(1));
    }

    public void loadVarargs(int i) {
        loadVarargs();
        arg(i);
    }

    public void loadVarresult() {
        append((Instruction) new ALOAD(getVarresultIndex()));
    }

    public void newTable(int i, int i2) {
        append((CompoundInstruction) new PUSH(this.cp, i));
        append((CompoundInstruction) new PUSH(this.cp, i2));
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "tableOf", TYPE_LUATABLE, ARG_TYPES_INT_INT, (short) 184));
    }

    public void newTailcallVarargs() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "tailcallOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short) 184));
    }

    public void newVarargs(int i, int i2, int i3) {
        InvokeInstruction invokeInstructionCreateInvoke;
        if (i3 == 0) {
            loadNone();
            return;
        }
        if (i3 == 1) {
            loadLocal(i, i2);
            return;
        }
        if (i3 == 2) {
            loadLocal(i, i2);
            loadLocal(i, i2 + 1);
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_VARARGS, (short) 184);
        } else if (i3 != 3) {
            loadArrayArgs(i, i2, i3);
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUEARRAY, (short) 184);
        } else {
            loadLocal(i, i2);
            loadLocal(i, i2 + 1);
            loadLocal(i, i2 + 2);
            invokeInstructionCreateInvoke = this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUE_LUAVALUE_VARARGS, (short) 184);
        }
        append((Instruction) invokeInstructionCreateInvoke);
    }

    public void newVarargsVarresult(int i, int i2, int i3) {
        loadArrayArgs(i, i2, i3);
        loadVarresult();
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "varargsOf", TYPE_VARARGS, ARG_TYPES_LUAVALUEARRAY_VARARGS, (short) 184));
    }

    public void onEndOfLuaInstruction(int i, int i2) {
        this.branchDestHandles[i] = this.beginningOfLuaInstruction;
        this.lastInstrHandles[i] = this.main.getEnd();
        if (i2 != this.prev_line) {
            MethodGen methodGen = this.mg;
            InstructionHandle instructionHandle = this.beginningOfLuaInstruction;
            this.prev_line = i2;
            methodGen.addLineNumber(instructionHandle, i2);
        }
        this.beginningOfLuaInstruction = null;
    }

    public void pop() {
        append((Instruction) InstructionConstants.POP);
    }

    public void setTable() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, Type.VOID, ARG_TYPES_LUAVALUE_LUAVALUE, (short) 182));
    }

    public void setVarStartEnd(int i, int i2, int i3, String str) {
        Integer numValueOf = Integer.valueOf(i);
        if (this.localVarGenBySlot.containsKey(numValueOf)) {
            String strReplaceAll = str.replaceAll("[^a-zA-Z0-9]", "_");
            LocalVariableGen localVariableGen = (LocalVariableGen) this.localVarGenBySlot.get(numValueOf);
            localVariableGen.setEnd(this.lastInstrHandles[i3 - 1]);
            if (i2 > 1) {
                localVariableGen.setStart(this.lastInstrHandles[i2 - 2]);
            }
            localVariableGen.setName(strReplaceAll);
        }
    }

    public void setlistStack(int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < i4; i5++) {
            dup();
            append((CompoundInstruction) new PUSH(this.cp, i3 + i5));
            loadLocal(i, i2 + i5);
            append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "rawset", Type.VOID, ARG_TYPES_INT_LUAVALUE, (short) 182));
        }
    }

    public void setlistVarargs(int i, int i2) {
        append((CompoundInstruction) new PUSH(this.cp, i));
        loadVarresult();
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "rawsetlist", Type.VOID, ARG_TYPES_INT_VARARGS, (short) 182));
    }

    public void storeLocal(int i, int i2) {
        ArrayInstruction astore;
        ASTORE aload;
        boolean zIsUpvalueAssign = this.pi.isUpvalueAssign(i, i2);
        int iFindSlotIndex = findSlotIndex(i2, zIsUpvalueAssign);
        if (zIsUpvalueAssign) {
            if (this.pi.isUpvalueCreate(i, i2)) {
                append((Instruction) this.factory.createInvoke(this.classname, "newupe", TYPE_LOCALUPVALUE, ARG_TYPES_NONE, (short) 184));
                append((Instruction) InstructionConstants.DUP);
                aload = new ASTORE(iFindSlotIndex);
            } else {
                aload = new ALOAD(iFindSlotIndex);
            }
            append((Instruction) aload);
            append((Instruction) InstructionConstants.SWAP);
            append((CompoundInstruction) new PUSH(this.cp, 0));
            append((Instruction) InstructionConstants.SWAP);
            astore = InstructionConstants.AASTORE;
        } else {
            astore = new ASTORE(iFindSlotIndex);
        }
        append((Instruction) astore);
    }

    public void storeUpvalue(int i, int i2, int i3) {
        ArrayInstruction arrayInstructionCreateFieldAccess;
        ProtoInfo protoInfo = this.pi;
        boolean zIsReadWriteUpvalue = protoInfo.isReadWriteUpvalue(protoInfo.upvals[i2]);
        append((Instruction) InstructionConstants.THIS);
        if (zIsReadWriteUpvalue) {
            append((Instruction) this.factory.createFieldAccess(this.classname, upvalueName(i2), TYPE_LOCALUPVALUE, (short) 180));
            append((CompoundInstruction) new PUSH(this.cp, 0));
            loadLocal(i, i3);
            arrayInstructionCreateFieldAccess = InstructionConstants.AASTORE;
        } else {
            loadLocal(i, i3);
            arrayInstructionCreateFieldAccess = this.factory.createFieldAccess(this.classname, upvalueName(i2), TYPE_LUAVALUE, (short) 181);
        }
        append((Instruction) arrayInstructionCreateFieldAccess);
    }

    public void storeVarresult() {
        append((Instruction) new ASTORE(getVarresultIndex()));
    }

    public void subargs(int i) {
        append((CompoundInstruction) new PUSH(this.cp, i));
        append((Instruction) this.factory.createInvoke(STR_VARARGS, "subargs", TYPE_VARARGS, ARG_TYPES_INT, (short) 182));
    }

    public void testForLoop() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "testfor_b", Type.BOOLEAN, ARG_TYPES_LUAVALUE_LUAVALUE, (short) 182));
    }

    public void toBoolean() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "toboolean", Type.BOOLEAN, Type.NO_ARGS, (short) 182));
    }

    public void tobuffer() {
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, "buffer", TYPE_BUFFER, Type.NO_ARGS, (short) 182));
    }

    public void tostring() {
        append((Instruction) this.factory.createInvoke(STR_BUFFER, "tostring", TYPE_LUASTRING, Type.NO_ARGS, (short) 182));
    }

    public void tovalue() {
        append((Instruction) this.factory.createInvoke(STR_BUFFER, "value", TYPE_LUAVALUE, Type.NO_ARGS, (short) 182));
    }

    public void unaryop(int i) {
        String str;
        if (i != 20) {
            str = i != 21 ? "neg" : "len";
        } else {
            str = "not";
        }
        append((Instruction) this.factory.createInvoke(STR_LUAVALUE, str, TYPE_LUAVALUE, Type.NO_ARGS, (short) 182));
    }
}
