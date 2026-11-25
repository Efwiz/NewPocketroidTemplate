package com.android.dx.cf.code;

import com.android.dx.cf.iface.Method;
import com.android.dx.cf.iface.MethodList;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.FillArrayDataInsn;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InvokePolymorphicInsn;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.SwitchInsn;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.code.ThrowingInsn;
import com.android.dx.rop.code.TranslationAdvice;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstCallSiteRef;
import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: loaded from: classes.dex */
final class RopperMachine extends ValueAwareMachine {
    private static final CstType ARRAY_REFLECT_TYPE;
    private static final CstMethodRef MULTIANEWARRAY_METHOD;
    private final TranslationAdvice advice;
    private boolean blockCanThrow;
    private TypeList catches;
    private boolean catchesUsed;
    private int extraBlockCount;
    private boolean hasJsr;
    private final ArrayList<Insn> insns;
    private final int maxLocals;
    private final ConcreteMethod method;
    private final MethodList methods;
    private int primarySuccessorIndex;
    private ReturnAddress returnAddress;
    private Rop returnOp;
    private SourcePosition returnPosition;
    private boolean returns;
    private final Ropper ropper;

    static {
        CstType cstType = new CstType(Type.internClassName("java/lang/reflect/Array"));
        ARRAY_REFLECT_TYPE = cstType;
        MULTIANEWARRAY_METHOD = new CstMethodRef(cstType, new CstNat(new CstString(TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME), new CstString("(Ljava/lang/Class;[I)Ljava/lang/Object;")));
    }

    public RopperMachine(Ropper ropper, ConcreteMethod concreteMethod, TranslationAdvice translationAdvice, MethodList methodList) {
        super(concreteMethod.getEffectiveDescriptor());
        if (methodList == null) {
            throw new NullPointerException("methods == null");
        }
        if (ropper == null) {
            throw new NullPointerException("ropper == null");
        }
        if (translationAdvice == null) {
            throw new NullPointerException("advice == null");
        }
        this.ropper = ropper;
        this.method = concreteMethod;
        this.methods = methodList;
        this.advice = translationAdvice;
        this.maxLocals = concreteMethod.getMaxLocals();
        this.insns = new ArrayList<>(25);
        this.catches = null;
        this.catchesUsed = false;
        this.returns = false;
        this.primarySuccessorIndex = -1;
        this.extraBlockCount = 0;
        this.blockCanThrow = false;
        this.returnOp = null;
        this.returnPosition = null;
    }

    public ArrayList<Insn> getInsns() {
        return this.insns;
    }

    public Rop getReturnOp() {
        return this.returnOp;
    }

    public SourcePosition getReturnPosition() {
        return this.returnPosition;
    }

    public void startBlock(TypeList typeList) {
        this.catches = typeList;
        this.insns.clear();
        this.catchesUsed = false;
        this.returns = false;
        this.primarySuccessorIndex = 0;
        this.extraBlockCount = 0;
        this.blockCanThrow = false;
        this.hasJsr = false;
        this.returnAddress = null;
    }

    public boolean wereCatchesUsed() {
        return this.catchesUsed;
    }

    public boolean returns() {
        return this.returns;
    }

    public int getPrimarySuccessorIndex() {
        return this.primarySuccessorIndex;
    }

    public int getExtraBlockCount() {
        return this.extraBlockCount;
    }

    public boolean canThrow() {
        return this.blockCanThrow;
    }

    public boolean hasJsr() {
        return this.hasJsr;
    }

    public boolean hasRet() {
        return this.returnAddress != null;
    }

    public ReturnAddress getReturnAddress() {
        return this.returnAddress;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x02f5  */
    /* JADX WARN: Code duplicated, block: B:104:0x0306  */
    /* JADX WARN: Code duplicated, block: B:106:0x030b  */
    /* JADX WARN: Code duplicated, block: B:108:0x0318  */
    /* JADX WARN: Code duplicated, block: B:109:0x031c  */
    /* JADX WARN: Code duplicated, block: B:110:0x0325  */
    /* JADX WARN: Code duplicated, block: B:113:0x0331  */
    /* JADX WARN: Code duplicated, block: B:115:0x0338  */
    /* JADX WARN: Code duplicated, block: B:130:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:59:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:60:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:79:0x024c  */
    /* JADX WARN: Code duplicated, block: B:81:0x0252  */
    /* JADX WARN: Code duplicated, block: B:82:0x0260  */
    /* JADX WARN: Code duplicated, block: B:85:0x027b  */
    /* JADX WARN: Code duplicated, block: B:87:0x0280  */
    /* JADX WARN: Code duplicated, block: B:89:0x0286  */
    /* JADX WARN: Code duplicated, block: B:91:0x0295  */
    /* JADX WARN: Code duplicated, block: B:92:0x02a8  */
    /* JADX WARN: Code duplicated, block: B:94:0x02ba A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:95:0x02bc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:96:0x02be  */
    /* JADX WARN: Code duplicated, block: B:98:0x02c6  */
    /* JADX WARN: Code duplicated, block: B:99:0x02d8  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v30, types: [int] */
    /* JADX WARN: Type inference failed for: r12v9, types: [com.android.dx.rop.type.TypeBearer] */
    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.dx.rop.type.TypeBearer] */
    /* JADX WARN: Type inference failed for: r3v19, types: [int] */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v24 */
    /* JADX WARN: Type inference failed for: r3v25 */
    @Override // com.android.dx.cf.code.ValueAwareMachine, com.android.dx.cf.code.Machine
    public void run(Frame frame, int i, int i2) {
        Constant constantIntern;
        RegisterSpec registerSpec;
        PlainInsn plainInsn;
        PlainInsn plainInsn2;
        Constant constantMake;
        RegisterSpecList registerSpecListWithoutFirst;
        Rop ropRopFor;
        SwitchList auxCases;
        ArrayList<Constant> initValues;
        boolean zCanThrow;
        boolean z;
        SourcePosition sourcePosition;
        Insn plainInsn3;
        Insn throwingInsn;
        boolean z2;
        boolean z3;
        SourcePosition sourcePosition2;
        boolean z4;
        int i3;
        Insn plainInsn4;
        RegisterSpec registerSpec2;
        TypeBearer typeBearer;
        ?? r3;
        Type returnType;
        ThrowingCstInsn throwingCstInsn;
        int i4 = i2;
        int size = this.maxLocals + frame.getStack().size();
        RegisterSpecList sources = getSources(i4, size);
        int size2 = sources.size();
        super.run(frame, i, i2);
        SourcePosition sourcePositionMakeSourcePosistion = this.method.makeSourcePosistion(i);
        RegisterSpec localTarget = getLocalTarget(i4 == 54);
        int iResultCount = resultCount();
        if (iResultCount == 0) {
            if (i4 == 87 || i4 == 88) {
                return;
            } else {
                localTarget = null;
            }
        } else if (localTarget == null) {
            if (iResultCount == 1) {
                localTarget = RegisterSpec.make(size, result(0));
            } else {
                int firstTempStackReg = this.ropper.getFirstTempStackReg();
                RegisterSpec[] registerSpecArr = new RegisterSpec[size2];
                for (int i5 = 0; i5 < size2; i5++) {
                    RegisterSpec registerSpec3 = sources.get(i5);
                    TypeBearer typeBearer2 = registerSpec3.getTypeBearer();
                    RegisterSpec registerSpecWithReg = registerSpec3.withReg(firstTempStackReg);
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer2), sourcePositionMakeSourcePosistion, registerSpecWithReg, registerSpec3));
                    registerSpecArr[i5] = registerSpecWithReg;
                    firstTempStackReg += registerSpec3.getCategory();
                }
                for (int auxInt = getAuxInt(); auxInt != 0; auxInt >>= 4) {
                    RegisterSpec registerSpec4 = registerSpecArr[(auxInt & 15) - 1];
                    TypeBearer typeBearer3 = registerSpec4.getTypeBearer();
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer3), sourcePositionMakeSourcePosistion, registerSpec4.withReg(size), registerSpec4));
                    size += typeBearer3.getType().getCategory();
                }
                return;
            }
        }
        TypeBearer typeBearer4 = localTarget != null ? localTarget : Type.VOID;
        Constant auxCst = getAuxCst();
        if (i4 == 197) {
            this.blockCanThrow = true;
            this.extraBlockCount = 6;
            RegisterSpec registerSpecMake = RegisterSpec.make(localTarget.getNextReg(), Type.INT_ARRAY);
            this.insns.add(new ThrowingCstInsn(Rops.opFilledNewArray(Type.INT_ARRAY, size2), sourcePositionMakeSourcePosistion, sources, this.catches, CstType.INT_ARRAY));
            this.insns.add(new PlainInsn(Rops.opMoveResult(Type.INT_ARRAY), sourcePositionMakeSourcePosistion, registerSpecMake, RegisterSpecList.EMPTY));
            Type classType = ((CstType) auxCst).getClassType();
            for (int i6 = 0; i6 < size2; i6++) {
                classType = classType.getComponentType();
            }
            RegisterSpec registerSpecMake2 = RegisterSpec.make(localTarget.getReg(), Type.CLASS);
            if (classType.isPrimitive()) {
                throwingCstInsn = new ThrowingCstInsn(Rops.GET_STATIC_OBJECT, sourcePositionMakeSourcePosistion, RegisterSpecList.EMPTY, this.catches, CstFieldRef.forPrimitiveType(classType));
            } else {
                throwingCstInsn = new ThrowingCstInsn(Rops.CONST_OBJECT, sourcePositionMakeSourcePosistion, RegisterSpecList.EMPTY, this.catches, new CstType(classType));
            }
            this.insns.add(throwingCstInsn);
            this.insns.add(new PlainInsn(Rops.opMoveResultPseudo(registerSpecMake2.getType()), sourcePositionMakeSourcePosistion, registerSpecMake2, RegisterSpecList.EMPTY));
            RegisterSpec registerSpecMake3 = RegisterSpec.make(localTarget.getReg(), Type.OBJECT);
            CstMethodRef cstMethodRef = MULTIANEWARRAY_METHOD;
            this.insns.add(new ThrowingCstInsn(Rops.opInvokeStatic(cstMethodRef.getPrototype()), sourcePositionMakeSourcePosistion, RegisterSpecList.make(registerSpecMake2, registerSpecMake), this.catches, cstMethodRef));
            this.insns.add(new PlainInsn(Rops.opMoveResult(cstMethodRef.getPrototype().getReturnType()), sourcePositionMakeSourcePosistion, registerSpecMake3, RegisterSpecList.EMPTY));
            sources = RegisterSpecList.make(registerSpecMake3);
            i4 = 192;
            constantIntern = auxCst;
        } else {
            constantIntern = auxCst;
            if (i4 == 168) {
                this.hasJsr = true;
                return;
            } else if (i4 == 169) {
                try {
                    this.returnAddress = (ReturnAddress) arg(0);
                    return;
                } catch (ClassCastException e) {
                    throw new RuntimeException("Argument to RET was not a ReturnAddress", e);
                }
            }
        }
        int iJopToRopOpcode = jopToRopOpcode(i4, constantIntern);
        Rop ropRopFor2 = Rops.ropFor(iJopToRopOpcode, typeBearer4, sources, constantIntern);
        if (localTarget != null && ropRopFor2.isCallLike()) {
            this.extraBlockCount++;
            if (ropRopFor2.getOpcode() == 59) {
                returnType = ((CstCallSiteRef) constantIntern).getReturnType();
            } else {
                returnType = ((CstMethodRef) constantIntern).getPrototype().getReturnType();
            }
            plainInsn2 = new PlainInsn(Rops.opMoveResult(returnType), sourcePositionMakeSourcePosistion, localTarget, RegisterSpecList.EMPTY);
        } else {
            if (localTarget == null || !ropRopFor2.canThrow()) {
                registerSpec = localTarget;
                plainInsn = null;
            } else {
                this.extraBlockCount++;
                plainInsn2 = new PlainInsn(Rops.opMoveResultPseudo(localTarget.getTypeBearer()), sourcePositionMakeSourcePosistion, localTarget, RegisterSpecList.EMPTY);
            }
            if (iJopToRopOpcode == 41) {
                constantIntern = CstType.intern(ropRopFor2.getResult());
            } else {
                if (constantIntern != null && size2 == 2) {
                    ?? typeBearer5 = sources.get(0).getTypeBearer();
                    ?? typeBearer6 = sources.get(1).getTypeBearer();
                    if ((typeBearer6.isConstant() || typeBearer5.isConstant()) && this.advice.hasConstantOperation(ropRopFor2, sources.get(0), sources.get(1))) {
                        if (typeBearer6.isConstant()) {
                            constantMake = (Constant) typeBearer6;
                            registerSpecListWithoutFirst = sources.withoutLast();
                            if (ropRopFor2.getOpcode() == 15) {
                                constantMake = CstInteger.make(-((CstInteger) typeBearer6).getValue());
                                iJopToRopOpcode = 14;
                            }
                        } else {
                            constantMake = (Constant) typeBearer5;
                            registerSpecListWithoutFirst = sources.withoutFirst();
                        }
                        ropRopFor = Rops.ropFor(iJopToRopOpcode, typeBearer4, registerSpecListWithoutFirst, constantMake);
                        constantIntern = constantMake;
                        sources = registerSpecListWithoutFirst;
                    }
                }
                auxCases = getAuxCases();
                initValues = getInitValues();
                zCanThrow = ropRopFor.canThrow();
                this.blockCanThrow |= zCanThrow;
                if (auxCases != null) {
                    if (auxCases.size() == 0) {
                        plainInsn3 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                        this.primarySuccessorIndex = 0;
                    } else {
                        IntList values = auxCases.getValues();
                        plainInsn4 = new SwitchInsn(ropRopFor, sourcePositionMakeSourcePosistion, registerSpec, sources, values);
                        this.primarySuccessorIndex = values.size();
                    }
                    plainInsn = plainInsn;
                    r3 = 1;
                    sourcePosition = sourcePositionMakeSourcePosistion;
                    this.insns.add(plainInsn3);
                    if (plainInsn != null) {
                        this.insns.add(plainInsn);
                    }
                    if (initValues != null) {
                        this.extraBlockCount += r3;
                        this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
                    }
                }
                if (iJopToRopOpcode == 33) {
                    if (sources.size() != 0) {
                        i3 = 0;
                        registerSpec2 = sources.get(0);
                        typeBearer = registerSpec2.getTypeBearer();
                        if (registerSpec2.getReg() != 0) {
                            this.insns.add(new PlainInsn(Rops.opMove(typeBearer), sourcePositionMakeSourcePosistion, RegisterSpec.make(0, typeBearer), registerSpec2));
                        }
                    } else {
                        i3 = 0;
                    }
                    plainInsn4 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                    this.primarySuccessorIndex = i3;
                    updateReturnOp(ropRopFor, sourcePositionMakeSourcePosistion);
                    this.returns = true;
                } else {
                    if (constantIntern != null) {
                        if (zCanThrow) {
                            if (ropRopFor.getOpcode() == 58) {
                                sourcePosition2 = sourcePositionMakeSourcePosistion;
                                throwingInsn = makeInvokePolymorphicInsn(ropRopFor, sourcePositionMakeSourcePosistion, sources, this.catches, constantIntern);
                                z4 = true;
                            } else {
                                sourcePosition2 = sourcePositionMakeSourcePosistion;
                                z4 = true;
                                throwingInsn = new ThrowingCstInsn(ropRopFor, sourcePosition2, sources, this.catches, constantIntern);
                            }
                            this.catchesUsed = z4;
                            this.primarySuccessorIndex = this.catches.size();
                            z3 = z4;
                        } else {
                            plainInsn = plainInsn;
                            z3 = true;
                            sourcePosition2 = sourcePositionMakeSourcePosistion;
                            throwingInsn = new PlainCstInsn(ropRopFor, sourcePosition2, registerSpec, sources, constantIntern);
                        }
                        sourcePosition = sourcePosition2;
                        z2 = z3;
                    } else {
                        plainInsn = plainInsn;
                        z = true;
                        sourcePosition = sourcePositionMakeSourcePosistion;
                        if (zCanThrow) {
                            throwingInsn = new ThrowingInsn(ropRopFor, sourcePosition, sources, this.catches);
                            this.catchesUsed = true;
                            if (i4 == 191) {
                                this.primarySuccessorIndex = -1;
                                z2 = z;
                            } else {
                                this.primarySuccessorIndex = this.catches.size();
                                z2 = z;
                            }
                        } else {
                            plainInsn3 = new PlainInsn(ropRopFor, sourcePosition, registerSpec, sources);
                            r3 = z;
                        }
                    }
                    plainInsn3 = throwingInsn;
                    r3 = z2;
                }
                this.insns.add(plainInsn3);
                if (plainInsn != null) {
                    this.insns.add(plainInsn);
                }
                if (initValues != null) {
                    this.extraBlockCount += r3;
                    this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
                }
                plainInsn3 = plainInsn4;
                plainInsn = plainInsn;
                r3 = 1;
                sourcePosition = sourcePositionMakeSourcePosistion;
                this.insns.add(plainInsn3);
                if (plainInsn != null) {
                    this.insns.add(plainInsn);
                }
                if (initValues != null) {
                    this.extraBlockCount += r3;
                    this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
                }
            }
            ropRopFor = ropRopFor2;
            auxCases = getAuxCases();
            initValues = getInitValues();
            zCanThrow = ropRopFor.canThrow();
            this.blockCanThrow |= zCanThrow;
            if (auxCases != null) {
                if (auxCases.size() == 0) {
                    plainInsn3 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                    this.primarySuccessorIndex = 0;
                } else {
                    IntList values2 = auxCases.getValues();
                    plainInsn4 = new SwitchInsn(ropRopFor, sourcePositionMakeSourcePosistion, registerSpec, sources, values2);
                    this.primarySuccessorIndex = values2.size();
                }
                plainInsn = plainInsn;
                r3 = 1;
                sourcePosition = sourcePositionMakeSourcePosistion;
                this.insns.add(plainInsn3);
                if (plainInsn != null) {
                    this.insns.add(plainInsn);
                }
                if (initValues != null) {
                    this.extraBlockCount += r3;
                    this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
                }
            }
            if (iJopToRopOpcode == 33) {
                if (sources.size() != 0) {
                    i3 = 0;
                    registerSpec2 = sources.get(0);
                    typeBearer = registerSpec2.getTypeBearer();
                    if (registerSpec2.getReg() != 0) {
                        this.insns.add(new PlainInsn(Rops.opMove(typeBearer), sourcePositionMakeSourcePosistion, RegisterSpec.make(0, typeBearer), registerSpec2));
                    }
                } else {
                    i3 = 0;
                }
                plainInsn4 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                this.primarySuccessorIndex = i3;
                updateReturnOp(ropRopFor, sourcePositionMakeSourcePosistion);
                this.returns = true;
            } else {
                if (constantIntern != null) {
                    if (zCanThrow) {
                        if (ropRopFor.getOpcode() == 58) {
                            sourcePosition2 = sourcePositionMakeSourcePosistion;
                            throwingInsn = makeInvokePolymorphicInsn(ropRopFor, sourcePositionMakeSourcePosistion, sources, this.catches, constantIntern);
                            z4 = true;
                        } else {
                            sourcePosition2 = sourcePositionMakeSourcePosistion;
                            z4 = true;
                            throwingInsn = new ThrowingCstInsn(ropRopFor, sourcePosition2, sources, this.catches, constantIntern);
                        }
                        this.catchesUsed = z4;
                        this.primarySuccessorIndex = this.catches.size();
                        z3 = z4;
                    } else {
                        plainInsn = plainInsn;
                        z3 = true;
                        sourcePosition2 = sourcePositionMakeSourcePosistion;
                        throwingInsn = new PlainCstInsn(ropRopFor, sourcePosition2, registerSpec, sources, constantIntern);
                    }
                    sourcePosition = sourcePosition2;
                    z2 = z3;
                } else {
                    plainInsn = plainInsn;
                    z = true;
                    sourcePosition = sourcePositionMakeSourcePosistion;
                    if (zCanThrow) {
                        throwingInsn = new ThrowingInsn(ropRopFor, sourcePosition, sources, this.catches);
                        this.catchesUsed = true;
                        if (i4 == 191) {
                            this.primarySuccessorIndex = -1;
                            z2 = z;
                        } else {
                            this.primarySuccessorIndex = this.catches.size();
                            z2 = z;
                        }
                    } else {
                        plainInsn3 = new PlainInsn(ropRopFor, sourcePosition, registerSpec, sources);
                        r3 = z;
                    }
                }
                plainInsn3 = throwingInsn;
                r3 = z2;
            }
            this.insns.add(plainInsn3);
            if (plainInsn != null) {
                this.insns.add(plainInsn);
            }
            if (initValues != null) {
                this.extraBlockCount += r3;
                this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
            }
            plainInsn3 = plainInsn4;
            plainInsn = plainInsn;
            r3 = 1;
            sourcePosition = sourcePositionMakeSourcePosistion;
            this.insns.add(plainInsn3);
            if (plainInsn != null) {
                this.insns.add(plainInsn);
            }
            if (initValues != null) {
                this.extraBlockCount += r3;
                this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
            }
        }
        plainInsn = plainInsn2;
        registerSpec = null;
        if (iJopToRopOpcode == 41) {
            constantIntern = CstType.intern(ropRopFor2.getResult());
        } else {
            if (constantIntern != null) {
            }
            auxCases = getAuxCases();
            initValues = getInitValues();
            zCanThrow = ropRopFor.canThrow();
            this.blockCanThrow |= zCanThrow;
            if (auxCases != null) {
                if (auxCases.size() == 0) {
                    plainInsn3 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                    this.primarySuccessorIndex = 0;
                } else {
                    IntList values3 = auxCases.getValues();
                    plainInsn4 = new SwitchInsn(ropRopFor, sourcePositionMakeSourcePosistion, registerSpec, sources, values3);
                    this.primarySuccessorIndex = values3.size();
                }
                plainInsn = plainInsn;
                r3 = 1;
                sourcePosition = sourcePositionMakeSourcePosistion;
                this.insns.add(plainInsn3);
                if (plainInsn != null) {
                    this.insns.add(plainInsn);
                }
                if (initValues != null) {
                    this.extraBlockCount += r3;
                    this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
                }
            }
            if (iJopToRopOpcode == 33) {
                if (sources.size() != 0) {
                    i3 = 0;
                    registerSpec2 = sources.get(0);
                    typeBearer = registerSpec2.getTypeBearer();
                    if (registerSpec2.getReg() != 0) {
                        this.insns.add(new PlainInsn(Rops.opMove(typeBearer), sourcePositionMakeSourcePosistion, RegisterSpec.make(0, typeBearer), registerSpec2));
                    }
                } else {
                    i3 = 0;
                }
                plainInsn4 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                this.primarySuccessorIndex = i3;
                updateReturnOp(ropRopFor, sourcePositionMakeSourcePosistion);
                this.returns = true;
            } else {
                if (constantIntern != null) {
                    if (zCanThrow) {
                        if (ropRopFor.getOpcode() == 58) {
                            sourcePosition2 = sourcePositionMakeSourcePosistion;
                            throwingInsn = makeInvokePolymorphicInsn(ropRopFor, sourcePositionMakeSourcePosistion, sources, this.catches, constantIntern);
                            z4 = true;
                        } else {
                            sourcePosition2 = sourcePositionMakeSourcePosistion;
                            z4 = true;
                            throwingInsn = new ThrowingCstInsn(ropRopFor, sourcePosition2, sources, this.catches, constantIntern);
                        }
                        this.catchesUsed = z4;
                        this.primarySuccessorIndex = this.catches.size();
                        z3 = z4;
                    } else {
                        plainInsn = plainInsn;
                        z3 = true;
                        sourcePosition2 = sourcePositionMakeSourcePosistion;
                        throwingInsn = new PlainCstInsn(ropRopFor, sourcePosition2, registerSpec, sources, constantIntern);
                    }
                    sourcePosition = sourcePosition2;
                    z2 = z3;
                } else {
                    plainInsn = plainInsn;
                    z = true;
                    sourcePosition = sourcePositionMakeSourcePosistion;
                    if (zCanThrow) {
                        throwingInsn = new ThrowingInsn(ropRopFor, sourcePosition, sources, this.catches);
                        this.catchesUsed = true;
                        if (i4 == 191) {
                            this.primarySuccessorIndex = -1;
                            z2 = z;
                        } else {
                            this.primarySuccessorIndex = this.catches.size();
                            z2 = z;
                        }
                    } else {
                        plainInsn3 = new PlainInsn(ropRopFor, sourcePosition, registerSpec, sources);
                        r3 = z;
                    }
                }
                plainInsn3 = throwingInsn;
                r3 = z2;
            }
            this.insns.add(plainInsn3);
            if (plainInsn != null) {
                this.insns.add(plainInsn);
            }
            if (initValues != null) {
                this.extraBlockCount += r3;
                this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
            }
            plainInsn3 = plainInsn4;
            plainInsn = plainInsn;
            r3 = 1;
            sourcePosition = sourcePositionMakeSourcePosistion;
            this.insns.add(plainInsn3);
            if (plainInsn != null) {
                this.insns.add(plainInsn);
            }
            if (initValues != null) {
                this.extraBlockCount += r3;
                this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
            }
        }
        ropRopFor = ropRopFor2;
        auxCases = getAuxCases();
        initValues = getInitValues();
        zCanThrow = ropRopFor.canThrow();
        this.blockCanThrow |= zCanThrow;
        if (auxCases != null) {
            if (auxCases.size() == 0) {
                plainInsn3 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
                this.primarySuccessorIndex = 0;
            } else {
                IntList values4 = auxCases.getValues();
                plainInsn4 = new SwitchInsn(ropRopFor, sourcePositionMakeSourcePosistion, registerSpec, sources, values4);
                this.primarySuccessorIndex = values4.size();
            }
            plainInsn = plainInsn;
            r3 = 1;
            sourcePosition = sourcePositionMakeSourcePosistion;
            this.insns.add(plainInsn3);
            if (plainInsn != null) {
                this.insns.add(plainInsn);
            }
            if (initValues != null) {
                this.extraBlockCount += r3;
                this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
            }
        }
        if (iJopToRopOpcode == 33) {
            if (sources.size() != 0) {
                i3 = 0;
                registerSpec2 = sources.get(0);
                typeBearer = registerSpec2.getTypeBearer();
                if (registerSpec2.getReg() != 0) {
                    this.insns.add(new PlainInsn(Rops.opMove(typeBearer), sourcePositionMakeSourcePosistion, RegisterSpec.make(0, typeBearer), registerSpec2));
                }
            } else {
                i3 = 0;
            }
            plainInsn4 = new PlainInsn(Rops.GOTO, sourcePositionMakeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY);
            this.primarySuccessorIndex = i3;
            updateReturnOp(ropRopFor, sourcePositionMakeSourcePosistion);
            this.returns = true;
        } else {
            if (constantIntern != null) {
                if (zCanThrow) {
                    if (ropRopFor.getOpcode() == 58) {
                        sourcePosition2 = sourcePositionMakeSourcePosistion;
                        throwingInsn = makeInvokePolymorphicInsn(ropRopFor, sourcePositionMakeSourcePosistion, sources, this.catches, constantIntern);
                        z4 = true;
                    } else {
                        sourcePosition2 = sourcePositionMakeSourcePosistion;
                        z4 = true;
                        throwingInsn = new ThrowingCstInsn(ropRopFor, sourcePosition2, sources, this.catches, constantIntern);
                    }
                    this.catchesUsed = z4;
                    this.primarySuccessorIndex = this.catches.size();
                    z3 = z4;
                } else {
                    plainInsn = plainInsn;
                    z3 = true;
                    sourcePosition2 = sourcePositionMakeSourcePosistion;
                    throwingInsn = new PlainCstInsn(ropRopFor, sourcePosition2, registerSpec, sources, constantIntern);
                }
                sourcePosition = sourcePosition2;
                z2 = z3;
            } else {
                plainInsn = plainInsn;
                z = true;
                sourcePosition = sourcePositionMakeSourcePosistion;
                if (zCanThrow) {
                    throwingInsn = new ThrowingInsn(ropRopFor, sourcePosition, sources, this.catches);
                    this.catchesUsed = true;
                    if (i4 == 191) {
                        this.primarySuccessorIndex = -1;
                        z2 = z;
                    } else {
                        this.primarySuccessorIndex = this.catches.size();
                        z2 = z;
                    }
                } else {
                    plainInsn3 = new PlainInsn(ropRopFor, sourcePosition, registerSpec, sources);
                    r3 = z;
                }
            }
            plainInsn3 = throwingInsn;
            r3 = z2;
        }
        this.insns.add(plainInsn3);
        if (plainInsn != null) {
            this.insns.add(plainInsn);
        }
        if (initValues != null) {
            this.extraBlockCount += r3;
            this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
        }
        plainInsn3 = plainInsn4;
        plainInsn = plainInsn;
        r3 = 1;
        sourcePosition = sourcePositionMakeSourcePosistion;
        this.insns.add(plainInsn3);
        if (plainInsn != null) {
            this.insns.add(plainInsn);
        }
        if (initValues != null) {
            this.extraBlockCount += r3;
            this.insns.add(new FillArrayDataInsn(Rops.FILL_ARRAY_DATA, sourcePosition, RegisterSpecList.make(plainInsn.getResult()), initValues, constantIntern));
        }
    }

    private RegisterSpecList getSources(int i, int i2) {
        RegisterSpecList registerSpecList;
        int iArgCount = argCount();
        if (iArgCount == 0) {
            return RegisterSpecList.EMPTY;
        }
        int localIndex = getLocalIndex();
        if (localIndex >= 0) {
            registerSpecList = new RegisterSpecList(1);
            registerSpecList.set(0, RegisterSpec.make(localIndex, arg(0)));
        } else {
            RegisterSpecList registerSpecList2 = new RegisterSpecList(iArgCount);
            for (int i3 = 0; i3 < iArgCount; i3++) {
                RegisterSpec registerSpecMake = RegisterSpec.make(i2, arg(i3));
                registerSpecList2.set(i3, registerSpecMake);
                i2 += registerSpecMake.getCategory();
            }
            if (i == 79) {
                if (iArgCount != 3) {
                    throw new RuntimeException("shouldn't happen");
                }
                RegisterSpec registerSpec = registerSpecList2.get(0);
                RegisterSpec registerSpec2 = registerSpecList2.get(1);
                registerSpecList2.set(0, registerSpecList2.get(2));
                registerSpecList2.set(1, registerSpec);
                registerSpecList2.set(2, registerSpec2);
            } else if (i == 181) {
                if (iArgCount != 2) {
                    throw new RuntimeException("shouldn't happen");
                }
                RegisterSpec registerSpec3 = registerSpecList2.get(0);
                registerSpecList2.set(0, registerSpecList2.get(1));
                registerSpecList2.set(1, registerSpec3);
            }
            registerSpecList = registerSpecList2;
        }
        registerSpecList.setImmutable();
        return registerSpecList;
    }

    private void updateReturnOp(Rop rop, SourcePosition sourcePosition) {
        if (rop == null) {
            throw new NullPointerException("op == null");
        }
        if (sourcePosition == null) {
            throw new NullPointerException("pos == null");
        }
        Rop rop2 = this.returnOp;
        if (rop2 == null) {
            this.returnOp = rop;
            this.returnPosition = sourcePosition;
        } else {
            if (rop2 != rop) {
                throw new SimException("return op mismatch: " + rop + ", " + this.returnOp);
            }
            if (sourcePosition.getLine() > this.returnPosition.getLine()) {
                this.returnPosition = sourcePosition;
            }
        }
    }

    private int jopToRopOpcode(int i, Constant constant) {
        if (i == 0) {
            return 1;
        }
        if (i == 20) {
            return 5;
        }
        if (i == 21) {
            return 2;
        }
        if (i == 171) {
            return 13;
        }
        if (i == 172) {
            return 33;
        }
        if (i == 198) {
            return 7;
        }
        if (i == 199) {
            return 8;
        }
        switch (i) {
            case 0:
                return 1;
            case 18:
                return 5;
            case 46:
                return 38;
            case 54:
                return 2;
            case 79:
                return 39;
            case 96:
                return 14;
            case 100:
                return 15;
            case 104:
                return 16;
            case 108:
                return 17;
            case 112:
                return 18;
            case 116:
                return 19;
            case 120:
                return 23;
            case 122:
                return 24;
            case 124:
                return 25;
            case 126:
                return 20;
            case 128:
                return 21;
            case 130:
                return 22;
            default:
                switch (i) {
                    case 132:
                        return 14;
                    case 133:
                    case 134:
                    case 135:
                    case 136:
                    case 137:
                    case 138:
                    case 139:
                    case 140:
                    case 141:
                    case 142:
                    case 143:
                    case 144:
                        return 29;
                    case 145:
                        return 30;
                    case 146:
                        return 31;
                    case 147:
                        return 32;
                    case 148:
                    case 149:
                    case 151:
                        return 27;
                    case 150:
                    case 152:
                        return 28;
                    case 153:
                    case 159:
                    case 165:
                        return 7;
                    case 154:
                    case 160:
                    case 166:
                        return 8;
                    case 155:
                    case 161:
                        return 9;
                    case 156:
                    case 162:
                        return 10;
                    case 157:
                    case 163:
                        return 12;
                    case 158:
                    case 164:
                        return 11;
                    case 167:
                        return 6;
                    default:
                        switch (i) {
                            case 177:
                                return 33;
                            case 178:
                                return 46;
                            case 179:
                                return 48;
                            case 180:
                                return 45;
                            case 181:
                                return 47;
                            case 182:
                                CstMethodRef cstMethodRef = (CstMethodRef) constant;
                                if (cstMethodRef.getDefiningClass().equals(this.method.getDefiningClass())) {
                                    for (int i2 = 0; i2 < this.methods.size(); i2++) {
                                        Method method = this.methods.get(i2);
                                        if (AccessFlags.isPrivate(method.getAccessFlags()) && cstMethodRef.getNat().equals(method.getNat())) {
                                            return 52;
                                        }
                                    }
                                }
                                return cstMethodRef.isSignaturePolymorphic() ? 58 : 50;
                            case 183:
                                CstMethodRef cstMethodRef2 = (CstMethodRef) constant;
                                return (cstMethodRef2.isInstanceInit() || cstMethodRef2.getDefiningClass().equals(this.method.getDefiningClass())) ? 52 : 51;
                            case 184:
                                return 49;
                            case 185:
                                return 53;
                            case 186:
                                return 59;
                            case 187:
                                return 40;
                            case 188:
                            case 189:
                                return 41;
                            case 190:
                                return 34;
                            case 191:
                                return 35;
                            case 192:
                                return 43;
                            case 193:
                                return 44;
                            case 194:
                                return 36;
                            case 195:
                                return 37;
                            default:
                                throw new RuntimeException("shouldn't happen");
                        }
                }
        }
    }

    private Insn makeInvokePolymorphicInsn(Rop rop, SourcePosition sourcePosition, RegisterSpecList registerSpecList, TypeList typeList, Constant constant) {
        return new InvokePolymorphicInsn(rop, sourcePosition, registerSpecList, typeList, (CstMethodRef) constant);
    }
}
