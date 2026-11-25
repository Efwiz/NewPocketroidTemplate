package org.jetbrains.kotlin.backend.common.serialization.proto;

import com.gaurav.avnc.vnc.XKeySym;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import org.jetbrains.kotlin.protobuf.AbstractParser;
import org.jetbrains.kotlin.protobuf.ByteString;
import org.jetbrains.kotlin.protobuf.CodedInputStream;
import org.jetbrains.kotlin.protobuf.CodedOutputStream;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.Internal;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.protobuf.Parser;

/* JADX INFO: loaded from: classes2.dex */
public final class IrOperation extends GeneratedMessageLite implements IrOperationOrBuilder {
    public static final int BLOCK_FIELD_NUMBER = 1;
    public static final int BREAK_FIELD_NUMBER = 2;
    public static final int CALL_FIELD_NUMBER = 3;
    public static final int CLASS_REFERENCE_FIELD_NUMBER = 4;
    public static final int COMPOSITE_FIELD_NUMBER = 5;
    public static final int CONSTRUCTOR_CALL_FIELD_NUMBER = 32;
    public static final int CONST_FIELD_NUMBER = 6;
    public static final int CONTINUE_FIELD_NUMBER = 7;
    public static final int DELEGATING_CONSTRUCTOR_CALL_FIELD_NUMBER = 8;
    public static final int DO_WHILE_FIELD_NUMBER = 9;
    public static final int DYNAMIC_MEMBER_FIELD_NUMBER = 29;
    public static final int DYNAMIC_OPERATOR_FIELD_NUMBER = 30;
    public static final int ENUM_CONSTRUCTOR_CALL_FIELD_NUMBER = 10;
    public static final int ERROR_CALL_EXPRESSION_FIELD_NUMBER = 35;
    public static final int ERROR_EXPRESSION_FIELD_NUMBER = 34;
    public static final int FUNCTION_EXPRESSION_FIELD_NUMBER = 33;
    public static final int FUNCTION_REFERENCE_FIELD_NUMBER = 11;
    public static final int GET_CLASS_FIELD_NUMBER = 12;
    public static final int GET_ENUM_VALUE_FIELD_NUMBER = 13;
    public static final int GET_FIELD_FIELD_NUMBER = 14;
    public static final int GET_OBJECT_FIELD_NUMBER = 15;
    public static final int GET_VALUE_FIELD_NUMBER = 16;
    public static final int INSTANCE_INITIALIZER_CALL_FIELD_NUMBER = 17;
    public static final int LOCAL_DELEGATED_PROPERTY_REFERENCE_FIELD_NUMBER = 31;
    public static Parser<IrOperation> PARSER = new AbstractParser<IrOperation>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrOperation.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrOperation parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrOperation(codedInputStream, extensionRegistryLite);
        }
    };
    public static final int PROPERTY_REFERENCE_FIELD_NUMBER = 18;
    public static final int RETURN_FIELD_NUMBER = 19;
    public static final int SET_FIELD_FIELD_NUMBER = 20;
    public static final int SET_VALUE_FIELD_NUMBER = 21;
    public static final int STRING_CONCAT_FIELD_NUMBER = 22;
    public static final int THROW_FIELD_NUMBER = 23;
    public static final int TRY_FIELD_NUMBER = 24;
    public static final int TYPE_OP_FIELD_NUMBER = 25;
    public static final int VARARG_FIELD_NUMBER = 26;
    public static final int WHEN_FIELD_NUMBER = 27;
    public static final int WHILE_FIELD_NUMBER = 28;
    private static final IrOperation defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int bitField1_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int operationCase_;
    private Object operation_;
    private final ByteString unknownFields;

    private void initFields() {
    }

    private IrOperation(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.operationCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrOperation(boolean z) {
        this.operationCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrOperation getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrOperation getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrOperation(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        boolean z = false;
        this.operationCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        initFields();
        ByteString.Output outputNewOutput = ByteString.newOutput();
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputNewOutput, 1);
        while (!z) {
            try {
                try {
                    int tag = codedInputStream.readTag();
                    switch (tag) {
                        case 0:
                            break;
                        case 10:
                            IrBlock.Builder builder = this.operationCase_ == 1 ? ((IrBlock) this.operation_).toBuilder() : null;
                            MessageLite message = codedInputStream.readMessage(IrBlock.PARSER, extensionRegistryLite);
                            this.operation_ = message;
                            if (builder != null) {
                                builder.mergeFrom((IrBlock) message);
                                this.operation_ = builder.buildPartial();
                            }
                            this.operationCase_ = 1;
                            continue;
                        case 18:
                            IrBreak.Builder builder2 = this.operationCase_ == 2 ? ((IrBreak) this.operation_).toBuilder() : null;
                            MessageLite message2 = codedInputStream.readMessage(IrBreak.PARSER, extensionRegistryLite);
                            this.operation_ = message2;
                            if (builder2 != null) {
                                builder2.mergeFrom((IrBreak) message2);
                                this.operation_ = builder2.buildPartial();
                            }
                            this.operationCase_ = 2;
                            continue;
                        case 26:
                            IrCall.Builder builder3 = this.operationCase_ == 3 ? ((IrCall) this.operation_).toBuilder() : null;
                            MessageLite message3 = codedInputStream.readMessage(IrCall.PARSER, extensionRegistryLite);
                            this.operation_ = message3;
                            if (builder3 != null) {
                                builder3.mergeFrom((IrCall) message3);
                                this.operation_ = builder3.buildPartial();
                            }
                            this.operationCase_ = 3;
                            continue;
                        case 34:
                            IrClassReference.Builder builder4 = this.operationCase_ == 4 ? ((IrClassReference) this.operation_).toBuilder() : null;
                            MessageLite message4 = codedInputStream.readMessage(IrClassReference.PARSER, extensionRegistryLite);
                            this.operation_ = message4;
                            if (builder4 != null) {
                                builder4.mergeFrom((IrClassReference) message4);
                                this.operation_ = builder4.buildPartial();
                            }
                            this.operationCase_ = 4;
                            continue;
                        case 42:
                            IrComposite.Builder builder5 = this.operationCase_ == 5 ? ((IrComposite) this.operation_).toBuilder() : null;
                            MessageLite message5 = codedInputStream.readMessage(IrComposite.PARSER, extensionRegistryLite);
                            this.operation_ = message5;
                            if (builder5 != null) {
                                builder5.mergeFrom((IrComposite) message5);
                                this.operation_ = builder5.buildPartial();
                            }
                            this.operationCase_ = 5;
                            continue;
                        case 50:
                            IrConst.Builder builder6 = this.operationCase_ == 6 ? ((IrConst) this.operation_).toBuilder() : null;
                            MessageLite message6 = codedInputStream.readMessage(IrConst.PARSER, extensionRegistryLite);
                            this.operation_ = message6;
                            if (builder6 != null) {
                                builder6.mergeFrom((IrConst) message6);
                                this.operation_ = builder6.buildPartial();
                            }
                            this.operationCase_ = 6;
                            continue;
                        case 58:
                            IrContinue.Builder builder7 = this.operationCase_ == 7 ? ((IrContinue) this.operation_).toBuilder() : null;
                            MessageLite message7 = codedInputStream.readMessage(IrContinue.PARSER, extensionRegistryLite);
                            this.operation_ = message7;
                            if (builder7 != null) {
                                builder7.mergeFrom((IrContinue) message7);
                                this.operation_ = builder7.buildPartial();
                            }
                            this.operationCase_ = 7;
                            continue;
                        case 66:
                            IrDelegatingConstructorCall.Builder builder8 = this.operationCase_ == 8 ? ((IrDelegatingConstructorCall) this.operation_).toBuilder() : null;
                            MessageLite message8 = codedInputStream.readMessage(IrDelegatingConstructorCall.PARSER, extensionRegistryLite);
                            this.operation_ = message8;
                            if (builder8 != null) {
                                builder8.mergeFrom((IrDelegatingConstructorCall) message8);
                                this.operation_ = builder8.buildPartial();
                            }
                            this.operationCase_ = 8;
                            continue;
                        case 74:
                            IrDoWhile.Builder builder9 = this.operationCase_ == 9 ? ((IrDoWhile) this.operation_).toBuilder() : null;
                            MessageLite message9 = codedInputStream.readMessage(IrDoWhile.PARSER, extensionRegistryLite);
                            this.operation_ = message9;
                            if (builder9 != null) {
                                builder9.mergeFrom((IrDoWhile) message9);
                                this.operation_ = builder9.buildPartial();
                            }
                            this.operationCase_ = 9;
                            continue;
                        case 82:
                            IrEnumConstructorCall.Builder builder10 = this.operationCase_ == 10 ? ((IrEnumConstructorCall) this.operation_).toBuilder() : null;
                            MessageLite message10 = codedInputStream.readMessage(IrEnumConstructorCall.PARSER, extensionRegistryLite);
                            this.operation_ = message10;
                            if (builder10 != null) {
                                builder10.mergeFrom((IrEnumConstructorCall) message10);
                                this.operation_ = builder10.buildPartial();
                            }
                            this.operationCase_ = 10;
                            continue;
                        case 90:
                            IrFunctionReference.Builder builder11 = this.operationCase_ == 11 ? ((IrFunctionReference) this.operation_).toBuilder() : null;
                            MessageLite message11 = codedInputStream.readMessage(IrFunctionReference.PARSER, extensionRegistryLite);
                            this.operation_ = message11;
                            if (builder11 != null) {
                                builder11.mergeFrom((IrFunctionReference) message11);
                                this.operation_ = builder11.buildPartial();
                            }
                            this.operationCase_ = 11;
                            continue;
                        case 98:
                            IrGetClass.Builder builder12 = this.operationCase_ == 12 ? ((IrGetClass) this.operation_).toBuilder() : null;
                            MessageLite message12 = codedInputStream.readMessage(IrGetClass.PARSER, extensionRegistryLite);
                            this.operation_ = message12;
                            if (builder12 != null) {
                                builder12.mergeFrom((IrGetClass) message12);
                                this.operation_ = builder12.buildPartial();
                            }
                            this.operationCase_ = 12;
                            continue;
                        case 106:
                            IrGetEnumValue.Builder builder13 = this.operationCase_ == 13 ? ((IrGetEnumValue) this.operation_).toBuilder() : null;
                            MessageLite message13 = codedInputStream.readMessage(IrGetEnumValue.PARSER, extensionRegistryLite);
                            this.operation_ = message13;
                            if (builder13 != null) {
                                builder13.mergeFrom((IrGetEnumValue) message13);
                                this.operation_ = builder13.buildPartial();
                            }
                            this.operationCase_ = 13;
                            continue;
                        case 114:
                            IrGetField.Builder builder14 = this.operationCase_ == 14 ? ((IrGetField) this.operation_).toBuilder() : null;
                            MessageLite message14 = codedInputStream.readMessage(IrGetField.PARSER, extensionRegistryLite);
                            this.operation_ = message14;
                            if (builder14 != null) {
                                builder14.mergeFrom((IrGetField) message14);
                                this.operation_ = builder14.buildPartial();
                            }
                            this.operationCase_ = 14;
                            continue;
                        case 122:
                            IrGetObject.Builder builder15 = this.operationCase_ == 15 ? ((IrGetObject) this.operation_).toBuilder() : null;
                            MessageLite message15 = codedInputStream.readMessage(IrGetObject.PARSER, extensionRegistryLite);
                            this.operation_ = message15;
                            if (builder15 != null) {
                                builder15.mergeFrom((IrGetObject) message15);
                                this.operation_ = builder15.buildPartial();
                            }
                            this.operationCase_ = 15;
                            continue;
                        case 130:
                            IrGetValue.Builder builder16 = this.operationCase_ == 16 ? ((IrGetValue) this.operation_).toBuilder() : null;
                            MessageLite message16 = codedInputStream.readMessage(IrGetValue.PARSER, extensionRegistryLite);
                            this.operation_ = message16;
                            if (builder16 != null) {
                                builder16.mergeFrom((IrGetValue) message16);
                                this.operation_ = builder16.buildPartial();
                            }
                            this.operationCase_ = 16;
                            continue;
                        case 138:
                            IrInstanceInitializerCall.Builder builder17 = this.operationCase_ == 17 ? ((IrInstanceInitializerCall) this.operation_).toBuilder() : null;
                            MessageLite message17 = codedInputStream.readMessage(IrInstanceInitializerCall.PARSER, extensionRegistryLite);
                            this.operation_ = message17;
                            if (builder17 != null) {
                                builder17.mergeFrom((IrInstanceInitializerCall) message17);
                                this.operation_ = builder17.buildPartial();
                            }
                            this.operationCase_ = 17;
                            continue;
                        case 146:
                            IrPropertyReference.Builder builder18 = this.operationCase_ == 18 ? ((IrPropertyReference) this.operation_).toBuilder() : null;
                            MessageLite message18 = codedInputStream.readMessage(IrPropertyReference.PARSER, extensionRegistryLite);
                            this.operation_ = message18;
                            if (builder18 != null) {
                                builder18.mergeFrom((IrPropertyReference) message18);
                                this.operation_ = builder18.buildPartial();
                            }
                            this.operationCase_ = 18;
                            continue;
                        case 154:
                            IrReturn.Builder builder19 = this.operationCase_ == 19 ? ((IrReturn) this.operation_).toBuilder() : null;
                            MessageLite message19 = codedInputStream.readMessage(IrReturn.PARSER, extensionRegistryLite);
                            this.operation_ = message19;
                            if (builder19 != null) {
                                builder19.mergeFrom((IrReturn) message19);
                                this.operation_ = builder19.buildPartial();
                            }
                            this.operationCase_ = 19;
                            continue;
                        case 162:
                            IrSetField.Builder builder20 = this.operationCase_ == 20 ? ((IrSetField) this.operation_).toBuilder() : null;
                            MessageLite message20 = codedInputStream.readMessage(IrSetField.PARSER, extensionRegistryLite);
                            this.operation_ = message20;
                            if (builder20 != null) {
                                builder20.mergeFrom((IrSetField) message20);
                                this.operation_ = builder20.buildPartial();
                            }
                            this.operationCase_ = 20;
                            continue;
                        case 170:
                            IrSetValue.Builder builder21 = this.operationCase_ == 21 ? ((IrSetValue) this.operation_).toBuilder() : null;
                            MessageLite message21 = codedInputStream.readMessage(IrSetValue.PARSER, extensionRegistryLite);
                            this.operation_ = message21;
                            if (builder21 != null) {
                                builder21.mergeFrom((IrSetValue) message21);
                                this.operation_ = builder21.buildPartial();
                            }
                            this.operationCase_ = 21;
                            continue;
                        case 178:
                            IrStringConcat.Builder builder22 = this.operationCase_ == 22 ? ((IrStringConcat) this.operation_).toBuilder() : null;
                            MessageLite message22 = codedInputStream.readMessage(IrStringConcat.PARSER, extensionRegistryLite);
                            this.operation_ = message22;
                            if (builder22 != null) {
                                builder22.mergeFrom((IrStringConcat) message22);
                                this.operation_ = builder22.buildPartial();
                            }
                            this.operationCase_ = 22;
                            continue;
                        case 186:
                            IrThrow.Builder builder23 = this.operationCase_ == 23 ? ((IrThrow) this.operation_).toBuilder() : null;
                            MessageLite message23 = codedInputStream.readMessage(IrThrow.PARSER, extensionRegistryLite);
                            this.operation_ = message23;
                            if (builder23 != null) {
                                builder23.mergeFrom((IrThrow) message23);
                                this.operation_ = builder23.buildPartial();
                            }
                            this.operationCase_ = 23;
                            continue;
                        case 194:
                            IrTry.Builder builder24 = this.operationCase_ == 24 ? ((IrTry) this.operation_).toBuilder() : null;
                            MessageLite message24 = codedInputStream.readMessage(IrTry.PARSER, extensionRegistryLite);
                            this.operation_ = message24;
                            if (builder24 != null) {
                                builder24.mergeFrom((IrTry) message24);
                                this.operation_ = builder24.buildPartial();
                            }
                            this.operationCase_ = 24;
                            continue;
                        case 202:
                            IrTypeOp.Builder builder25 = this.operationCase_ == 25 ? ((IrTypeOp) this.operation_).toBuilder() : null;
                            MessageLite message25 = codedInputStream.readMessage(IrTypeOp.PARSER, extensionRegistryLite);
                            this.operation_ = message25;
                            if (builder25 != null) {
                                builder25.mergeFrom((IrTypeOp) message25);
                                this.operation_ = builder25.buildPartial();
                            }
                            this.operationCase_ = 25;
                            continue;
                        case 210:
                            IrVararg.Builder builder26 = this.operationCase_ == 26 ? ((IrVararg) this.operation_).toBuilder() : null;
                            MessageLite message26 = codedInputStream.readMessage(IrVararg.PARSER, extensionRegistryLite);
                            this.operation_ = message26;
                            if (builder26 != null) {
                                builder26.mergeFrom((IrVararg) message26);
                                this.operation_ = builder26.buildPartial();
                            }
                            this.operationCase_ = 26;
                            continue;
                        case 218:
                            IrWhen.Builder builder27 = this.operationCase_ == 27 ? ((IrWhen) this.operation_).toBuilder() : null;
                            MessageLite message27 = codedInputStream.readMessage(IrWhen.PARSER, extensionRegistryLite);
                            this.operation_ = message27;
                            if (builder27 != null) {
                                builder27.mergeFrom((IrWhen) message27);
                                this.operation_ = builder27.buildPartial();
                            }
                            this.operationCase_ = 27;
                            continue;
                        case 226:
                            IrWhile.Builder builder28 = this.operationCase_ == 28 ? ((IrWhile) this.operation_).toBuilder() : null;
                            MessageLite message28 = codedInputStream.readMessage(IrWhile.PARSER, extensionRegistryLite);
                            this.operation_ = message28;
                            if (builder28 != null) {
                                builder28.mergeFrom((IrWhile) message28);
                                this.operation_ = builder28.buildPartial();
                            }
                            this.operationCase_ = 28;
                            continue;
                        case XKeySym.XK_ecircumflex /* 234 */:
                            IrDynamicMemberExpression.Builder builder29 = this.operationCase_ == 29 ? ((IrDynamicMemberExpression) this.operation_).toBuilder() : null;
                            MessageLite message29 = codedInputStream.readMessage(IrDynamicMemberExpression.PARSER, extensionRegistryLite);
                            this.operation_ = message29;
                            if (builder29 != null) {
                                builder29.mergeFrom((IrDynamicMemberExpression) message29);
                                this.operation_ = builder29.buildPartial();
                            }
                            this.operationCase_ = 29;
                            continue;
                        case 242:
                            IrDynamicOperatorExpression.Builder builder30 = this.operationCase_ == 30 ? ((IrDynamicOperatorExpression) this.operation_).toBuilder() : null;
                            MessageLite message30 = codedInputStream.readMessage(IrDynamicOperatorExpression.PARSER, extensionRegistryLite);
                            this.operation_ = message30;
                            if (builder30 != null) {
                                builder30.mergeFrom((IrDynamicOperatorExpression) message30);
                                this.operation_ = builder30.buildPartial();
                            }
                            this.operationCase_ = 30;
                            continue;
                        case 250:
                            IrLocalDelegatedPropertyReference.Builder builder31 = this.operationCase_ == 31 ? ((IrLocalDelegatedPropertyReference) this.operation_).toBuilder() : null;
                            MessageLite message31 = codedInputStream.readMessage(IrLocalDelegatedPropertyReference.PARSER, extensionRegistryLite);
                            this.operation_ = message31;
                            if (builder31 != null) {
                                builder31.mergeFrom((IrLocalDelegatedPropertyReference) message31);
                                this.operation_ = builder31.buildPartial();
                            }
                            this.operationCase_ = 31;
                            continue;
                        case 258:
                            IrConstructorCall.Builder builder32 = this.operationCase_ == 32 ? ((IrConstructorCall) this.operation_).toBuilder() : null;
                            MessageLite message32 = codedInputStream.readMessage(IrConstructorCall.PARSER, extensionRegistryLite);
                            this.operation_ = message32;
                            if (builder32 != null) {
                                builder32.mergeFrom((IrConstructorCall) message32);
                                this.operation_ = builder32.buildPartial();
                            }
                            this.operationCase_ = 32;
                            continue;
                        case 266:
                            IrFunctionExpression.Builder builder33 = this.operationCase_ == 33 ? ((IrFunctionExpression) this.operation_).toBuilder() : null;
                            MessageLite message33 = codedInputStream.readMessage(IrFunctionExpression.PARSER, extensionRegistryLite);
                            this.operation_ = message33;
                            if (builder33 != null) {
                                builder33.mergeFrom((IrFunctionExpression) message33);
                                this.operation_ = builder33.buildPartial();
                            }
                            this.operationCase_ = 33;
                            continue;
                        case 274:
                            IrErrorExpression.Builder builder34 = this.operationCase_ == 34 ? ((IrErrorExpression) this.operation_).toBuilder() : null;
                            MessageLite message34 = codedInputStream.readMessage(IrErrorExpression.PARSER, extensionRegistryLite);
                            this.operation_ = message34;
                            if (builder34 != null) {
                                builder34.mergeFrom((IrErrorExpression) message34);
                                this.operation_ = builder34.buildPartial();
                            }
                            this.operationCase_ = 34;
                            continue;
                        case 282:
                            IrErrorCallExpression.Builder builder35 = this.operationCase_ == 35 ? ((IrErrorCallExpression) this.operation_).toBuilder() : null;
                            MessageLite message35 = codedInputStream.readMessage(IrErrorCallExpression.PARSER, extensionRegistryLite);
                            this.operation_ = message35;
                            if (builder35 != null) {
                                builder35.mergeFrom((IrErrorCallExpression) message35);
                                this.operation_ = builder35.buildPartial();
                            }
                            this.operationCase_ = 35;
                            continue;
                        default:
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                break;
                            }
                            break;
                    }
                    z = true;
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                }
            } catch (Throwable th) {
                try {
                    codedOutputStreamNewInstance.flush();
                } catch (IOException unused) {
                } finally {
                    this.unknownFields = outputNewOutput.toByteString();
                }
                makeExtensionsImmutable();
                throw th;
            }
        }
        try {
            codedOutputStreamNewInstance.flush();
        } catch (IOException unused2) {
        } finally {
            this.unknownFields = outputNewOutput.toByteString();
        }
        makeExtensionsImmutable();
    }

    static {
        IrOperation irOperation = new IrOperation(true);
        defaultInstance = irOperation;
        irOperation.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrOperation> getParserForType() {
        return PARSER;
    }

    public enum OperationCase implements Internal.EnumLite {
        BLOCK(1),
        BREAK(2),
        CALL(3),
        CLASS_REFERENCE(4),
        COMPOSITE(5),
        CONST(6),
        CONTINUE(7),
        DELEGATING_CONSTRUCTOR_CALL(8),
        DO_WHILE(9),
        ENUM_CONSTRUCTOR_CALL(10),
        FUNCTION_REFERENCE(11),
        GET_CLASS(12),
        GET_ENUM_VALUE(13),
        GET_FIELD(14),
        GET_OBJECT(15),
        GET_VALUE(16),
        INSTANCE_INITIALIZER_CALL(17),
        PROPERTY_REFERENCE(18),
        RETURN(19),
        SET_FIELD(20),
        SET_VALUE(21),
        STRING_CONCAT(22),
        THROW(23),
        TRY(24),
        TYPE_OP(25),
        VARARG(26),
        WHEN(27),
        WHILE(28),
        DYNAMIC_MEMBER(29),
        DYNAMIC_OPERATOR(30),
        LOCAL_DELEGATED_PROPERTY_REFERENCE(31),
        CONSTRUCTOR_CALL(32),
        FUNCTION_EXPRESSION(33),
        ERROR_EXPRESSION(34),
        ERROR_CALL_EXPRESSION(35),
        OPERATION_NOT_SET(0);

        private int value;

        OperationCase(int i) {
            this.value = i;
        }

        public static OperationCase valueOf(int i) {
            switch (i) {
                case 0:
                    return OPERATION_NOT_SET;
                case 1:
                    return BLOCK;
                case 2:
                    return BREAK;
                case 3:
                    return CALL;
                case 4:
                    return CLASS_REFERENCE;
                case 5:
                    return COMPOSITE;
                case 6:
                    return CONST;
                case 7:
                    return CONTINUE;
                case 8:
                    return DELEGATING_CONSTRUCTOR_CALL;
                case 9:
                    return DO_WHILE;
                case 10:
                    return ENUM_CONSTRUCTOR_CALL;
                case 11:
                    return FUNCTION_REFERENCE;
                case 12:
                    return GET_CLASS;
                case 13:
                    return GET_ENUM_VALUE;
                case 14:
                    return GET_FIELD;
                case 15:
                    return GET_OBJECT;
                case 16:
                    return GET_VALUE;
                case 17:
                    return INSTANCE_INITIALIZER_CALL;
                case 18:
                    return PROPERTY_REFERENCE;
                case 19:
                    return RETURN;
                case 20:
                    return SET_FIELD;
                case 21:
                    return SET_VALUE;
                case 22:
                    return STRING_CONCAT;
                case 23:
                    return THROW;
                case 24:
                    return TRY;
                case 25:
                    return TYPE_OP;
                case 26:
                    return VARARG;
                case 27:
                    return WHEN;
                case 28:
                    return WHILE;
                case 29:
                    return DYNAMIC_MEMBER;
                case 30:
                    return DYNAMIC_OPERATOR;
                case 31:
                    return LOCAL_DELEGATED_PROPERTY_REFERENCE;
                case 32:
                    return CONSTRUCTOR_CALL;
                case 33:
                    return FUNCTION_EXPRESSION;
                case 34:
                    return ERROR_EXPRESSION;
                case 35:
                    return ERROR_CALL_EXPRESSION;
                default:
                    throw new IllegalArgumentException("Value is undefined for this oneof enum.");
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public OperationCase getOperationCase() {
        return OperationCase.valueOf(this.operationCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasBlock() {
        return this.operationCase_ == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrBlock getBlock() {
        if (this.operationCase_ == 1) {
            return (IrBlock) this.operation_;
        }
        return IrBlock.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasBreak() {
        return this.operationCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrBreak getBreak() {
        if (this.operationCase_ == 2) {
            return (IrBreak) this.operation_;
        }
        return IrBreak.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasCall() {
        return this.operationCase_ == 3;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrCall getCall() {
        if (this.operationCase_ == 3) {
            return (IrCall) this.operation_;
        }
        return IrCall.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasClassReference() {
        return this.operationCase_ == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrClassReference getClassReference() {
        if (this.operationCase_ == 4) {
            return (IrClassReference) this.operation_;
        }
        return IrClassReference.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasComposite() {
        return this.operationCase_ == 5;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrComposite getComposite() {
        if (this.operationCase_ == 5) {
            return (IrComposite) this.operation_;
        }
        return IrComposite.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasConst() {
        return this.operationCase_ == 6;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrConst getConst() {
        if (this.operationCase_ == 6) {
            return (IrConst) this.operation_;
        }
        return IrConst.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasContinue() {
        return this.operationCase_ == 7;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrContinue getContinue() {
        if (this.operationCase_ == 7) {
            return (IrContinue) this.operation_;
        }
        return IrContinue.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasDelegatingConstructorCall() {
        return this.operationCase_ == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrDelegatingConstructorCall getDelegatingConstructorCall() {
        if (this.operationCase_ == 8) {
            return (IrDelegatingConstructorCall) this.operation_;
        }
        return IrDelegatingConstructorCall.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasDoWhile() {
        return this.operationCase_ == 9;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrDoWhile getDoWhile() {
        if (this.operationCase_ == 9) {
            return (IrDoWhile) this.operation_;
        }
        return IrDoWhile.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasEnumConstructorCall() {
        return this.operationCase_ == 10;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrEnumConstructorCall getEnumConstructorCall() {
        if (this.operationCase_ == 10) {
            return (IrEnumConstructorCall) this.operation_;
        }
        return IrEnumConstructorCall.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasFunctionReference() {
        return this.operationCase_ == 11;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrFunctionReference getFunctionReference() {
        if (this.operationCase_ == 11) {
            return (IrFunctionReference) this.operation_;
        }
        return IrFunctionReference.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasGetClass() {
        return this.operationCase_ == 12;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrGetClass getGetClass() {
        if (this.operationCase_ == 12) {
            return (IrGetClass) this.operation_;
        }
        return IrGetClass.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasGetEnumValue() {
        return this.operationCase_ == 13;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrGetEnumValue getGetEnumValue() {
        if (this.operationCase_ == 13) {
            return (IrGetEnumValue) this.operation_;
        }
        return IrGetEnumValue.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasGetField() {
        return this.operationCase_ == 14;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrGetField getGetField() {
        if (this.operationCase_ == 14) {
            return (IrGetField) this.operation_;
        }
        return IrGetField.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasGetObject() {
        return this.operationCase_ == 15;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrGetObject getGetObject() {
        if (this.operationCase_ == 15) {
            return (IrGetObject) this.operation_;
        }
        return IrGetObject.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasGetValue() {
        return this.operationCase_ == 16;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrGetValue getGetValue() {
        if (this.operationCase_ == 16) {
            return (IrGetValue) this.operation_;
        }
        return IrGetValue.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasInstanceInitializerCall() {
        return this.operationCase_ == 17;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrInstanceInitializerCall getInstanceInitializerCall() {
        if (this.operationCase_ == 17) {
            return (IrInstanceInitializerCall) this.operation_;
        }
        return IrInstanceInitializerCall.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasPropertyReference() {
        return this.operationCase_ == 18;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrPropertyReference getPropertyReference() {
        if (this.operationCase_ == 18) {
            return (IrPropertyReference) this.operation_;
        }
        return IrPropertyReference.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasReturn() {
        return this.operationCase_ == 19;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrReturn getReturn() {
        if (this.operationCase_ == 19) {
            return (IrReturn) this.operation_;
        }
        return IrReturn.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasSetField() {
        return this.operationCase_ == 20;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrSetField getSetField() {
        if (this.operationCase_ == 20) {
            return (IrSetField) this.operation_;
        }
        return IrSetField.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasSetValue() {
        return this.operationCase_ == 21;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrSetValue getSetValue() {
        if (this.operationCase_ == 21) {
            return (IrSetValue) this.operation_;
        }
        return IrSetValue.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasStringConcat() {
        return this.operationCase_ == 22;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrStringConcat getStringConcat() {
        if (this.operationCase_ == 22) {
            return (IrStringConcat) this.operation_;
        }
        return IrStringConcat.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasThrow() {
        return this.operationCase_ == 23;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrThrow getThrow() {
        if (this.operationCase_ == 23) {
            return (IrThrow) this.operation_;
        }
        return IrThrow.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasTry() {
        return this.operationCase_ == 24;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrTry getTry() {
        if (this.operationCase_ == 24) {
            return (IrTry) this.operation_;
        }
        return IrTry.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasTypeOp() {
        return this.operationCase_ == 25;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrTypeOp getTypeOp() {
        if (this.operationCase_ == 25) {
            return (IrTypeOp) this.operation_;
        }
        return IrTypeOp.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasVararg() {
        return this.operationCase_ == 26;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrVararg getVararg() {
        if (this.operationCase_ == 26) {
            return (IrVararg) this.operation_;
        }
        return IrVararg.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasWhen() {
        return this.operationCase_ == 27;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrWhen getWhen() {
        if (this.operationCase_ == 27) {
            return (IrWhen) this.operation_;
        }
        return IrWhen.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasWhile() {
        return this.operationCase_ == 28;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrWhile getWhile() {
        if (this.operationCase_ == 28) {
            return (IrWhile) this.operation_;
        }
        return IrWhile.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasDynamicMember() {
        return this.operationCase_ == 29;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrDynamicMemberExpression getDynamicMember() {
        if (this.operationCase_ == 29) {
            return (IrDynamicMemberExpression) this.operation_;
        }
        return IrDynamicMemberExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasDynamicOperator() {
        return this.operationCase_ == 30;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrDynamicOperatorExpression getDynamicOperator() {
        if (this.operationCase_ == 30) {
            return (IrDynamicOperatorExpression) this.operation_;
        }
        return IrDynamicOperatorExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasLocalDelegatedPropertyReference() {
        return this.operationCase_ == 31;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrLocalDelegatedPropertyReference getLocalDelegatedPropertyReference() {
        if (this.operationCase_ == 31) {
            return (IrLocalDelegatedPropertyReference) this.operation_;
        }
        return IrLocalDelegatedPropertyReference.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasConstructorCall() {
        return this.operationCase_ == 32;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrConstructorCall getConstructorCall() {
        if (this.operationCase_ == 32) {
            return (IrConstructorCall) this.operation_;
        }
        return IrConstructorCall.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasFunctionExpression() {
        return this.operationCase_ == 33;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrFunctionExpression getFunctionExpression() {
        if (this.operationCase_ == 33) {
            return (IrFunctionExpression) this.operation_;
        }
        return IrFunctionExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasErrorExpression() {
        return this.operationCase_ == 34;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrErrorExpression getErrorExpression() {
        if (this.operationCase_ == 34) {
            return (IrErrorExpression) this.operation_;
        }
        return IrErrorExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public boolean hasErrorCallExpression() {
        return this.operationCase_ == 35;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
    public IrErrorCallExpression getErrorCallExpression() {
        if (this.operationCase_ == 35) {
            return (IrErrorCallExpression) this.operation_;
        }
        return IrErrorCallExpression.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        byte b = this.memoizedIsInitialized;
        if (b == 1) {
            return true;
        }
        if (b == 0) {
            return false;
        }
        if (hasBlock() && !getBlock().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasBreak() && !getBreak().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasCall() && !getCall().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasClassReference() && !getClassReference().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasComposite() && !getComposite().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasContinue() && !getContinue().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDelegatingConstructorCall() && !getDelegatingConstructorCall().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDoWhile() && !getDoWhile().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasEnumConstructorCall() && !getEnumConstructorCall().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasFunctionReference() && !getFunctionReference().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetClass() && !getGetClass().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetEnumValue() && !getGetEnumValue().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetField() && !getGetField().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetObject() && !getGetObject().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasGetValue() && !getGetValue().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasInstanceInitializerCall() && !getInstanceInitializerCall().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasPropertyReference() && !getPropertyReference().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasReturn() && !getReturn().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSetField() && !getSetField().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasSetValue() && !getSetValue().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasStringConcat() && !getStringConcat().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasThrow() && !getThrow().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasTry() && !getTry().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasTypeOp() && !getTypeOp().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasVararg() && !getVararg().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasWhen() && !getWhen().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasWhile() && !getWhile().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDynamicMember() && !getDynamicMember().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasDynamicOperator() && !getDynamicOperator().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasLocalDelegatedPropertyReference() && !getLocalDelegatedPropertyReference().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasConstructorCall() && !getConstructorCall().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasFunctionExpression() && !getFunctionExpression().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasErrorExpression() && !getErrorExpression().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasErrorCallExpression() && !getErrorCallExpression().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (this.operationCase_ == 1) {
            codedOutputStream.writeMessage(1, (IrBlock) this.operation_);
        }
        if (this.operationCase_ == 2) {
            codedOutputStream.writeMessage(2, (IrBreak) this.operation_);
        }
        if (this.operationCase_ == 3) {
            codedOutputStream.writeMessage(3, (IrCall) this.operation_);
        }
        if (this.operationCase_ == 4) {
            codedOutputStream.writeMessage(4, (IrClassReference) this.operation_);
        }
        if (this.operationCase_ == 5) {
            codedOutputStream.writeMessage(5, (IrComposite) this.operation_);
        }
        if (this.operationCase_ == 6) {
            codedOutputStream.writeMessage(6, (IrConst) this.operation_);
        }
        if (this.operationCase_ == 7) {
            codedOutputStream.writeMessage(7, (IrContinue) this.operation_);
        }
        if (this.operationCase_ == 8) {
            codedOutputStream.writeMessage(8, (IrDelegatingConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 9) {
            codedOutputStream.writeMessage(9, (IrDoWhile) this.operation_);
        }
        if (this.operationCase_ == 10) {
            codedOutputStream.writeMessage(10, (IrEnumConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 11) {
            codedOutputStream.writeMessage(11, (IrFunctionReference) this.operation_);
        }
        if (this.operationCase_ == 12) {
            codedOutputStream.writeMessage(12, (IrGetClass) this.operation_);
        }
        if (this.operationCase_ == 13) {
            codedOutputStream.writeMessage(13, (IrGetEnumValue) this.operation_);
        }
        if (this.operationCase_ == 14) {
            codedOutputStream.writeMessage(14, (IrGetField) this.operation_);
        }
        if (this.operationCase_ == 15) {
            codedOutputStream.writeMessage(15, (IrGetObject) this.operation_);
        }
        if (this.operationCase_ == 16) {
            codedOutputStream.writeMessage(16, (IrGetValue) this.operation_);
        }
        if (this.operationCase_ == 17) {
            codedOutputStream.writeMessage(17, (IrInstanceInitializerCall) this.operation_);
        }
        if (this.operationCase_ == 18) {
            codedOutputStream.writeMessage(18, (IrPropertyReference) this.operation_);
        }
        if (this.operationCase_ == 19) {
            codedOutputStream.writeMessage(19, (IrReturn) this.operation_);
        }
        if (this.operationCase_ == 20) {
            codedOutputStream.writeMessage(20, (IrSetField) this.operation_);
        }
        if (this.operationCase_ == 21) {
            codedOutputStream.writeMessage(21, (IrSetValue) this.operation_);
        }
        if (this.operationCase_ == 22) {
            codedOutputStream.writeMessage(22, (IrStringConcat) this.operation_);
        }
        if (this.operationCase_ == 23) {
            codedOutputStream.writeMessage(23, (IrThrow) this.operation_);
        }
        if (this.operationCase_ == 24) {
            codedOutputStream.writeMessage(24, (IrTry) this.operation_);
        }
        if (this.operationCase_ == 25) {
            codedOutputStream.writeMessage(25, (IrTypeOp) this.operation_);
        }
        if (this.operationCase_ == 26) {
            codedOutputStream.writeMessage(26, (IrVararg) this.operation_);
        }
        if (this.operationCase_ == 27) {
            codedOutputStream.writeMessage(27, (IrWhen) this.operation_);
        }
        if (this.operationCase_ == 28) {
            codedOutputStream.writeMessage(28, (IrWhile) this.operation_);
        }
        if (this.operationCase_ == 29) {
            codedOutputStream.writeMessage(29, (IrDynamicMemberExpression) this.operation_);
        }
        if (this.operationCase_ == 30) {
            codedOutputStream.writeMessage(30, (IrDynamicOperatorExpression) this.operation_);
        }
        if (this.operationCase_ == 31) {
            codedOutputStream.writeMessage(31, (IrLocalDelegatedPropertyReference) this.operation_);
        }
        if (this.operationCase_ == 32) {
            codedOutputStream.writeMessage(32, (IrConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 33) {
            codedOutputStream.writeMessage(33, (IrFunctionExpression) this.operation_);
        }
        if (this.operationCase_ == 34) {
            codedOutputStream.writeMessage(34, (IrErrorExpression) this.operation_);
        }
        if (this.operationCase_ == 35) {
            codedOutputStream.writeMessage(35, (IrErrorCallExpression) this.operation_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = this.operationCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (IrBlock) this.operation_) : 0;
        if (this.operationCase_ == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (IrBreak) this.operation_);
        }
        if (this.operationCase_ == 3) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, (IrCall) this.operation_);
        }
        if (this.operationCase_ == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, (IrClassReference) this.operation_);
        }
        if (this.operationCase_ == 5) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, (IrComposite) this.operation_);
        }
        if (this.operationCase_ == 6) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(6, (IrConst) this.operation_);
        }
        if (this.operationCase_ == 7) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(7, (IrContinue) this.operation_);
        }
        if (this.operationCase_ == 8) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(8, (IrDelegatingConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 9) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(9, (IrDoWhile) this.operation_);
        }
        if (this.operationCase_ == 10) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(10, (IrEnumConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 11) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(11, (IrFunctionReference) this.operation_);
        }
        if (this.operationCase_ == 12) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(12, (IrGetClass) this.operation_);
        }
        if (this.operationCase_ == 13) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(13, (IrGetEnumValue) this.operation_);
        }
        if (this.operationCase_ == 14) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(14, (IrGetField) this.operation_);
        }
        if (this.operationCase_ == 15) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(15, (IrGetObject) this.operation_);
        }
        if (this.operationCase_ == 16) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(16, (IrGetValue) this.operation_);
        }
        if (this.operationCase_ == 17) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(17, (IrInstanceInitializerCall) this.operation_);
        }
        if (this.operationCase_ == 18) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(18, (IrPropertyReference) this.operation_);
        }
        if (this.operationCase_ == 19) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(19, (IrReturn) this.operation_);
        }
        if (this.operationCase_ == 20) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(20, (IrSetField) this.operation_);
        }
        if (this.operationCase_ == 21) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(21, (IrSetValue) this.operation_);
        }
        if (this.operationCase_ == 22) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(22, (IrStringConcat) this.operation_);
        }
        if (this.operationCase_ == 23) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(23, (IrThrow) this.operation_);
        }
        if (this.operationCase_ == 24) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(24, (IrTry) this.operation_);
        }
        if (this.operationCase_ == 25) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(25, (IrTypeOp) this.operation_);
        }
        if (this.operationCase_ == 26) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(26, (IrVararg) this.operation_);
        }
        if (this.operationCase_ == 27) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(27, (IrWhen) this.operation_);
        }
        if (this.operationCase_ == 28) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(28, (IrWhile) this.operation_);
        }
        if (this.operationCase_ == 29) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(29, (IrDynamicMemberExpression) this.operation_);
        }
        if (this.operationCase_ == 30) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(30, (IrDynamicOperatorExpression) this.operation_);
        }
        if (this.operationCase_ == 31) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(31, (IrLocalDelegatedPropertyReference) this.operation_);
        }
        if (this.operationCase_ == 32) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(32, (IrConstructorCall) this.operation_);
        }
        if (this.operationCase_ == 33) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(33, (IrFunctionExpression) this.operation_);
        }
        if (this.operationCase_ == 34) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(34, (IrErrorExpression) this.operation_);
        }
        if (this.operationCase_ == 35) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(35, (IrErrorCallExpression) this.operation_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrOperation parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrOperation parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrOperation parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrOperation parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrOperation parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrOperation parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrOperation parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrOperation parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrOperation parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrOperation parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrOperation irOperation) {
        return newBuilder().mergeFrom(irOperation);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrOperation, Builder> implements IrOperationOrBuilder {
        private int bitField0_;
        private int bitField1_;
        private int operationCase_ = 0;
        private Object operation_;

        private void maybeForceBuilderInitialization() {
        }

        private Builder() {
            maybeForceBuilderInitialization();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Builder create() {
            return new Builder();
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clear() {
            super.clear();
            this.operationCase_ = 0;
            this.operation_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrOperation getDefaultInstanceForType() {
            return IrOperation.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrOperation build() {
            IrOperation irOperationBuildPartial = buildPartial();
            if (irOperationBuildPartial.isInitialized()) {
                return irOperationBuildPartial;
            }
            throw newUninitializedMessageException(irOperationBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrOperation buildPartial() {
            IrOperation irOperation = new IrOperation(this);
            if (this.operationCase_ == 1) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 2) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 3) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 4) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 5) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 6) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 7) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 8) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 9) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 10) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 11) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 12) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 13) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 14) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 15) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 16) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 17) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 18) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 19) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 20) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 21) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 22) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 23) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 24) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 25) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 26) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 27) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 28) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 29) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 30) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 31) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 32) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 33) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 34) {
                irOperation.operation_ = this.operation_;
            }
            if (this.operationCase_ == 35) {
                irOperation.operation_ = this.operation_;
            }
            irOperation.bitField0_ = 0;
            irOperation.bitField1_ = 0;
            irOperation.operationCase_ = this.operationCase_;
            return irOperation;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrOperation irOperation) {
            if (irOperation == IrOperation.getDefaultInstance()) {
                return this;
            }
            switch (AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[irOperation.getOperationCase().ordinal()]) {
                case 1:
                    mergeBlock(irOperation.getBlock());
                    break;
                case 2:
                    mergeBreak(irOperation.getBreak());
                    break;
                case 3:
                    mergeCall(irOperation.getCall());
                    break;
                case 4:
                    mergeClassReference(irOperation.getClassReference());
                    break;
                case 5:
                    mergeComposite(irOperation.getComposite());
                    break;
                case 6:
                    mergeConst(irOperation.getConst());
                    break;
                case 7:
                    mergeContinue(irOperation.getContinue());
                    break;
                case 8:
                    mergeDelegatingConstructorCall(irOperation.getDelegatingConstructorCall());
                    break;
                case 9:
                    mergeDoWhile(irOperation.getDoWhile());
                    break;
                case 10:
                    mergeEnumConstructorCall(irOperation.getEnumConstructorCall());
                    break;
                case 11:
                    mergeFunctionReference(irOperation.getFunctionReference());
                    break;
                case 12:
                    mergeGetClass(irOperation.getGetClass());
                    break;
                case 13:
                    mergeGetEnumValue(irOperation.getGetEnumValue());
                    break;
                case 14:
                    mergeGetField(irOperation.getGetField());
                    break;
                case 15:
                    mergeGetObject(irOperation.getGetObject());
                    break;
                case 16:
                    mergeGetValue(irOperation.getGetValue());
                    break;
                case 17:
                    mergeInstanceInitializerCall(irOperation.getInstanceInitializerCall());
                    break;
                case 18:
                    mergePropertyReference(irOperation.getPropertyReference());
                    break;
                case 19:
                    mergeReturn(irOperation.getReturn());
                    break;
                case 20:
                    mergeSetField(irOperation.getSetField());
                    break;
                case 21:
                    mergeSetValue(irOperation.getSetValue());
                    break;
                case 22:
                    mergeStringConcat(irOperation.getStringConcat());
                    break;
                case 23:
                    mergeThrow(irOperation.getThrow());
                    break;
                case 24:
                    mergeTry(irOperation.getTry());
                    break;
                case 25:
                    mergeTypeOp(irOperation.getTypeOp());
                    break;
                case 26:
                    mergeVararg(irOperation.getVararg());
                    break;
                case 27:
                    mergeWhen(irOperation.getWhen());
                    break;
                case 28:
                    mergeWhile(irOperation.getWhile());
                    break;
                case 29:
                    mergeDynamicMember(irOperation.getDynamicMember());
                    break;
                case 30:
                    mergeDynamicOperator(irOperation.getDynamicOperator());
                    break;
                case 31:
                    mergeLocalDelegatedPropertyReference(irOperation.getLocalDelegatedPropertyReference());
                    break;
                case 32:
                    mergeConstructorCall(irOperation.getConstructorCall());
                    break;
                case 33:
                    mergeFunctionExpression(irOperation.getFunctionExpression());
                    break;
                case 34:
                    mergeErrorExpression(irOperation.getErrorExpression());
                    break;
                case 35:
                    mergeErrorCallExpression(irOperation.getErrorCallExpression());
                    break;
            }
            setUnknownFields(getUnknownFields().concat(irOperation.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasBlock() && !getBlock().isInitialized()) {
                return false;
            }
            if (hasBreak() && !getBreak().isInitialized()) {
                return false;
            }
            if (hasCall() && !getCall().isInitialized()) {
                return false;
            }
            if (hasClassReference() && !getClassReference().isInitialized()) {
                return false;
            }
            if (hasComposite() && !getComposite().isInitialized()) {
                return false;
            }
            if (hasContinue() && !getContinue().isInitialized()) {
                return false;
            }
            if (hasDelegatingConstructorCall() && !getDelegatingConstructorCall().isInitialized()) {
                return false;
            }
            if (hasDoWhile() && !getDoWhile().isInitialized()) {
                return false;
            }
            if (hasEnumConstructorCall() && !getEnumConstructorCall().isInitialized()) {
                return false;
            }
            if (hasFunctionReference() && !getFunctionReference().isInitialized()) {
                return false;
            }
            if (hasGetClass() && !getGetClass().isInitialized()) {
                return false;
            }
            if (hasGetEnumValue() && !getGetEnumValue().isInitialized()) {
                return false;
            }
            if (hasGetField() && !getGetField().isInitialized()) {
                return false;
            }
            if (hasGetObject() && !getGetObject().isInitialized()) {
                return false;
            }
            if (hasGetValue() && !getGetValue().isInitialized()) {
                return false;
            }
            if (hasInstanceInitializerCall() && !getInstanceInitializerCall().isInitialized()) {
                return false;
            }
            if (hasPropertyReference() && !getPropertyReference().isInitialized()) {
                return false;
            }
            if (hasReturn() && !getReturn().isInitialized()) {
                return false;
            }
            if (hasSetField() && !getSetField().isInitialized()) {
                return false;
            }
            if (hasSetValue() && !getSetValue().isInitialized()) {
                return false;
            }
            if (hasStringConcat() && !getStringConcat().isInitialized()) {
                return false;
            }
            if (hasThrow() && !getThrow().isInitialized()) {
                return false;
            }
            if (hasTry() && !getTry().isInitialized()) {
                return false;
            }
            if (hasTypeOp() && !getTypeOp().isInitialized()) {
                return false;
            }
            if (hasVararg() && !getVararg().isInitialized()) {
                return false;
            }
            if (hasWhen() && !getWhen().isInitialized()) {
                return false;
            }
            if (hasWhile() && !getWhile().isInitialized()) {
                return false;
            }
            if (hasDynamicMember() && !getDynamicMember().isInitialized()) {
                return false;
            }
            if (hasDynamicOperator() && !getDynamicOperator().isInitialized()) {
                return false;
            }
            if (hasLocalDelegatedPropertyReference() && !getLocalDelegatedPropertyReference().isInitialized()) {
                return false;
            }
            if (hasConstructorCall() && !getConstructorCall().isInitialized()) {
                return false;
            }
            if (hasFunctionExpression() && !getFunctionExpression().isInitialized()) {
                return false;
            }
            if (!hasErrorExpression() || getErrorExpression().isInitialized()) {
                return !hasErrorCallExpression() || getErrorCallExpression().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrOperation irOperation = null;
            try {
                try {
                    IrOperation partialFrom = IrOperation.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrOperation irOperation2 = (IrOperation) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irOperation = irOperation2;
                        if (irOperation != null) {
                            mergeFrom(irOperation);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irOperation != null) {
                    mergeFrom(irOperation);
                }
                throw th;
            }
        }

        public OperationCase getOperationCase() {
            return OperationCase.valueOf(this.operationCase_);
        }

        public Builder clearOperation() {
            this.operationCase_ = 0;
            this.operation_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasBlock() {
            return this.operationCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrBlock getBlock() {
            if (this.operationCase_ == 1) {
                return (IrBlock) this.operation_;
            }
            return IrBlock.getDefaultInstance();
        }

        public Builder setBlock(IrBlock irBlock) {
            irBlock.getClass();
            this.operation_ = irBlock;
            this.operationCase_ = 1;
            return this;
        }

        public Builder setBlock(IrBlock.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 1;
            return this;
        }

        public Builder mergeBlock(IrBlock irBlock) {
            if (this.operationCase_ == 1 && this.operation_ != IrBlock.getDefaultInstance()) {
                this.operation_ = IrBlock.newBuilder((IrBlock) this.operation_).mergeFrom(irBlock).buildPartial();
            } else {
                this.operation_ = irBlock;
            }
            this.operationCase_ = 1;
            return this;
        }

        public Builder clearBlock() {
            if (this.operationCase_ == 1) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasBreak() {
            return this.operationCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrBreak getBreak() {
            if (this.operationCase_ == 2) {
                return (IrBreak) this.operation_;
            }
            return IrBreak.getDefaultInstance();
        }

        public Builder setBreak(IrBreak irBreak) {
            irBreak.getClass();
            this.operation_ = irBreak;
            this.operationCase_ = 2;
            return this;
        }

        public Builder setBreak(IrBreak.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 2;
            return this;
        }

        public Builder mergeBreak(IrBreak irBreak) {
            if (this.operationCase_ == 2 && this.operation_ != IrBreak.getDefaultInstance()) {
                this.operation_ = IrBreak.newBuilder((IrBreak) this.operation_).mergeFrom(irBreak).buildPartial();
            } else {
                this.operation_ = irBreak;
            }
            this.operationCase_ = 2;
            return this;
        }

        public Builder clearBreak() {
            if (this.operationCase_ == 2) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasCall() {
            return this.operationCase_ == 3;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrCall getCall() {
            if (this.operationCase_ == 3) {
                return (IrCall) this.operation_;
            }
            return IrCall.getDefaultInstance();
        }

        public Builder setCall(IrCall irCall) {
            irCall.getClass();
            this.operation_ = irCall;
            this.operationCase_ = 3;
            return this;
        }

        public Builder setCall(IrCall.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 3;
            return this;
        }

        public Builder mergeCall(IrCall irCall) {
            if (this.operationCase_ == 3 && this.operation_ != IrCall.getDefaultInstance()) {
                this.operation_ = IrCall.newBuilder((IrCall) this.operation_).mergeFrom(irCall).buildPartial();
            } else {
                this.operation_ = irCall;
            }
            this.operationCase_ = 3;
            return this;
        }

        public Builder clearCall() {
            if (this.operationCase_ == 3) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasClassReference() {
            return this.operationCase_ == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrClassReference getClassReference() {
            if (this.operationCase_ == 4) {
                return (IrClassReference) this.operation_;
            }
            return IrClassReference.getDefaultInstance();
        }

        public Builder setClassReference(IrClassReference irClassReference) {
            irClassReference.getClass();
            this.operation_ = irClassReference;
            this.operationCase_ = 4;
            return this;
        }

        public Builder setClassReference(IrClassReference.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 4;
            return this;
        }

        public Builder mergeClassReference(IrClassReference irClassReference) {
            if (this.operationCase_ == 4 && this.operation_ != IrClassReference.getDefaultInstance()) {
                this.operation_ = IrClassReference.newBuilder((IrClassReference) this.operation_).mergeFrom(irClassReference).buildPartial();
            } else {
                this.operation_ = irClassReference;
            }
            this.operationCase_ = 4;
            return this;
        }

        public Builder clearClassReference() {
            if (this.operationCase_ == 4) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasComposite() {
            return this.operationCase_ == 5;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrComposite getComposite() {
            if (this.operationCase_ == 5) {
                return (IrComposite) this.operation_;
            }
            return IrComposite.getDefaultInstance();
        }

        public Builder setComposite(IrComposite irComposite) {
            irComposite.getClass();
            this.operation_ = irComposite;
            this.operationCase_ = 5;
            return this;
        }

        public Builder setComposite(IrComposite.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 5;
            return this;
        }

        public Builder mergeComposite(IrComposite irComposite) {
            if (this.operationCase_ == 5 && this.operation_ != IrComposite.getDefaultInstance()) {
                this.operation_ = IrComposite.newBuilder((IrComposite) this.operation_).mergeFrom(irComposite).buildPartial();
            } else {
                this.operation_ = irComposite;
            }
            this.operationCase_ = 5;
            return this;
        }

        public Builder clearComposite() {
            if (this.operationCase_ == 5) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasConst() {
            return this.operationCase_ == 6;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrConst getConst() {
            if (this.operationCase_ == 6) {
                return (IrConst) this.operation_;
            }
            return IrConst.getDefaultInstance();
        }

        public Builder setConst(IrConst irConst) {
            irConst.getClass();
            this.operation_ = irConst;
            this.operationCase_ = 6;
            return this;
        }

        public Builder setConst(IrConst.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 6;
            return this;
        }

        public Builder mergeConst(IrConst irConst) {
            if (this.operationCase_ == 6 && this.operation_ != IrConst.getDefaultInstance()) {
                this.operation_ = IrConst.newBuilder((IrConst) this.operation_).mergeFrom(irConst).buildPartial();
            } else {
                this.operation_ = irConst;
            }
            this.operationCase_ = 6;
            return this;
        }

        public Builder clearConst() {
            if (this.operationCase_ == 6) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasContinue() {
            return this.operationCase_ == 7;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrContinue getContinue() {
            if (this.operationCase_ == 7) {
                return (IrContinue) this.operation_;
            }
            return IrContinue.getDefaultInstance();
        }

        public Builder setContinue(IrContinue irContinue) {
            irContinue.getClass();
            this.operation_ = irContinue;
            this.operationCase_ = 7;
            return this;
        }

        public Builder setContinue(IrContinue.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 7;
            return this;
        }

        public Builder mergeContinue(IrContinue irContinue) {
            if (this.operationCase_ == 7 && this.operation_ != IrContinue.getDefaultInstance()) {
                this.operation_ = IrContinue.newBuilder((IrContinue) this.operation_).mergeFrom(irContinue).buildPartial();
            } else {
                this.operation_ = irContinue;
            }
            this.operationCase_ = 7;
            return this;
        }

        public Builder clearContinue() {
            if (this.operationCase_ == 7) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasDelegatingConstructorCall() {
            return this.operationCase_ == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrDelegatingConstructorCall getDelegatingConstructorCall() {
            if (this.operationCase_ == 8) {
                return (IrDelegatingConstructorCall) this.operation_;
            }
            return IrDelegatingConstructorCall.getDefaultInstance();
        }

        public Builder setDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
            irDelegatingConstructorCall.getClass();
            this.operation_ = irDelegatingConstructorCall;
            this.operationCase_ = 8;
            return this;
        }

        public Builder setDelegatingConstructorCall(IrDelegatingConstructorCall.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 8;
            return this;
        }

        public Builder mergeDelegatingConstructorCall(IrDelegatingConstructorCall irDelegatingConstructorCall) {
            if (this.operationCase_ == 8 && this.operation_ != IrDelegatingConstructorCall.getDefaultInstance()) {
                this.operation_ = IrDelegatingConstructorCall.newBuilder((IrDelegatingConstructorCall) this.operation_).mergeFrom(irDelegatingConstructorCall).buildPartial();
            } else {
                this.operation_ = irDelegatingConstructorCall;
            }
            this.operationCase_ = 8;
            return this;
        }

        public Builder clearDelegatingConstructorCall() {
            if (this.operationCase_ == 8) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasDoWhile() {
            return this.operationCase_ == 9;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrDoWhile getDoWhile() {
            if (this.operationCase_ == 9) {
                return (IrDoWhile) this.operation_;
            }
            return IrDoWhile.getDefaultInstance();
        }

        public Builder setDoWhile(IrDoWhile irDoWhile) {
            irDoWhile.getClass();
            this.operation_ = irDoWhile;
            this.operationCase_ = 9;
            return this;
        }

        public Builder setDoWhile(IrDoWhile.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 9;
            return this;
        }

        public Builder mergeDoWhile(IrDoWhile irDoWhile) {
            if (this.operationCase_ == 9 && this.operation_ != IrDoWhile.getDefaultInstance()) {
                this.operation_ = IrDoWhile.newBuilder((IrDoWhile) this.operation_).mergeFrom(irDoWhile).buildPartial();
            } else {
                this.operation_ = irDoWhile;
            }
            this.operationCase_ = 9;
            return this;
        }

        public Builder clearDoWhile() {
            if (this.operationCase_ == 9) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasEnumConstructorCall() {
            return this.operationCase_ == 10;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrEnumConstructorCall getEnumConstructorCall() {
            if (this.operationCase_ == 10) {
                return (IrEnumConstructorCall) this.operation_;
            }
            return IrEnumConstructorCall.getDefaultInstance();
        }

        public Builder setEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
            irEnumConstructorCall.getClass();
            this.operation_ = irEnumConstructorCall;
            this.operationCase_ = 10;
            return this;
        }

        public Builder setEnumConstructorCall(IrEnumConstructorCall.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 10;
            return this;
        }

        public Builder mergeEnumConstructorCall(IrEnumConstructorCall irEnumConstructorCall) {
            if (this.operationCase_ == 10 && this.operation_ != IrEnumConstructorCall.getDefaultInstance()) {
                this.operation_ = IrEnumConstructorCall.newBuilder((IrEnumConstructorCall) this.operation_).mergeFrom(irEnumConstructorCall).buildPartial();
            } else {
                this.operation_ = irEnumConstructorCall;
            }
            this.operationCase_ = 10;
            return this;
        }

        public Builder clearEnumConstructorCall() {
            if (this.operationCase_ == 10) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasFunctionReference() {
            return this.operationCase_ == 11;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrFunctionReference getFunctionReference() {
            if (this.operationCase_ == 11) {
                return (IrFunctionReference) this.operation_;
            }
            return IrFunctionReference.getDefaultInstance();
        }

        public Builder setFunctionReference(IrFunctionReference irFunctionReference) {
            irFunctionReference.getClass();
            this.operation_ = irFunctionReference;
            this.operationCase_ = 11;
            return this;
        }

        public Builder setFunctionReference(IrFunctionReference.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 11;
            return this;
        }

        public Builder mergeFunctionReference(IrFunctionReference irFunctionReference) {
            if (this.operationCase_ == 11 && this.operation_ != IrFunctionReference.getDefaultInstance()) {
                this.operation_ = IrFunctionReference.newBuilder((IrFunctionReference) this.operation_).mergeFrom(irFunctionReference).buildPartial();
            } else {
                this.operation_ = irFunctionReference;
            }
            this.operationCase_ = 11;
            return this;
        }

        public Builder clearFunctionReference() {
            if (this.operationCase_ == 11) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasGetClass() {
            return this.operationCase_ == 12;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrGetClass getGetClass() {
            if (this.operationCase_ == 12) {
                return (IrGetClass) this.operation_;
            }
            return IrGetClass.getDefaultInstance();
        }

        public Builder setGetClass(IrGetClass irGetClass) {
            irGetClass.getClass();
            this.operation_ = irGetClass;
            this.operationCase_ = 12;
            return this;
        }

        public Builder setGetClass(IrGetClass.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 12;
            return this;
        }

        public Builder mergeGetClass(IrGetClass irGetClass) {
            if (this.operationCase_ == 12 && this.operation_ != IrGetClass.getDefaultInstance()) {
                this.operation_ = IrGetClass.newBuilder((IrGetClass) this.operation_).mergeFrom(irGetClass).buildPartial();
            } else {
                this.operation_ = irGetClass;
            }
            this.operationCase_ = 12;
            return this;
        }

        public Builder clearGetClass() {
            if (this.operationCase_ == 12) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasGetEnumValue() {
            return this.operationCase_ == 13;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrGetEnumValue getGetEnumValue() {
            if (this.operationCase_ == 13) {
                return (IrGetEnumValue) this.operation_;
            }
            return IrGetEnumValue.getDefaultInstance();
        }

        public Builder setGetEnumValue(IrGetEnumValue irGetEnumValue) {
            irGetEnumValue.getClass();
            this.operation_ = irGetEnumValue;
            this.operationCase_ = 13;
            return this;
        }

        public Builder setGetEnumValue(IrGetEnumValue.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 13;
            return this;
        }

        public Builder mergeGetEnumValue(IrGetEnumValue irGetEnumValue) {
            if (this.operationCase_ == 13 && this.operation_ != IrGetEnumValue.getDefaultInstance()) {
                this.operation_ = IrGetEnumValue.newBuilder((IrGetEnumValue) this.operation_).mergeFrom(irGetEnumValue).buildPartial();
            } else {
                this.operation_ = irGetEnumValue;
            }
            this.operationCase_ = 13;
            return this;
        }

        public Builder clearGetEnumValue() {
            if (this.operationCase_ == 13) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasGetField() {
            return this.operationCase_ == 14;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrGetField getGetField() {
            if (this.operationCase_ == 14) {
                return (IrGetField) this.operation_;
            }
            return IrGetField.getDefaultInstance();
        }

        public Builder setGetField(IrGetField irGetField) {
            irGetField.getClass();
            this.operation_ = irGetField;
            this.operationCase_ = 14;
            return this;
        }

        public Builder setGetField(IrGetField.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 14;
            return this;
        }

        public Builder mergeGetField(IrGetField irGetField) {
            if (this.operationCase_ == 14 && this.operation_ != IrGetField.getDefaultInstance()) {
                this.operation_ = IrGetField.newBuilder((IrGetField) this.operation_).mergeFrom(irGetField).buildPartial();
            } else {
                this.operation_ = irGetField;
            }
            this.operationCase_ = 14;
            return this;
        }

        public Builder clearGetField() {
            if (this.operationCase_ == 14) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasGetObject() {
            return this.operationCase_ == 15;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrGetObject getGetObject() {
            if (this.operationCase_ == 15) {
                return (IrGetObject) this.operation_;
            }
            return IrGetObject.getDefaultInstance();
        }

        public Builder setGetObject(IrGetObject irGetObject) {
            irGetObject.getClass();
            this.operation_ = irGetObject;
            this.operationCase_ = 15;
            return this;
        }

        public Builder setGetObject(IrGetObject.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 15;
            return this;
        }

        public Builder mergeGetObject(IrGetObject irGetObject) {
            if (this.operationCase_ == 15 && this.operation_ != IrGetObject.getDefaultInstance()) {
                this.operation_ = IrGetObject.newBuilder((IrGetObject) this.operation_).mergeFrom(irGetObject).buildPartial();
            } else {
                this.operation_ = irGetObject;
            }
            this.operationCase_ = 15;
            return this;
        }

        public Builder clearGetObject() {
            if (this.operationCase_ == 15) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasGetValue() {
            return this.operationCase_ == 16;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrGetValue getGetValue() {
            if (this.operationCase_ == 16) {
                return (IrGetValue) this.operation_;
            }
            return IrGetValue.getDefaultInstance();
        }

        public Builder setGetValue(IrGetValue irGetValue) {
            irGetValue.getClass();
            this.operation_ = irGetValue;
            this.operationCase_ = 16;
            return this;
        }

        public Builder setGetValue(IrGetValue.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 16;
            return this;
        }

        public Builder mergeGetValue(IrGetValue irGetValue) {
            if (this.operationCase_ == 16 && this.operation_ != IrGetValue.getDefaultInstance()) {
                this.operation_ = IrGetValue.newBuilder((IrGetValue) this.operation_).mergeFrom(irGetValue).buildPartial();
            } else {
                this.operation_ = irGetValue;
            }
            this.operationCase_ = 16;
            return this;
        }

        public Builder clearGetValue() {
            if (this.operationCase_ == 16) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasInstanceInitializerCall() {
            return this.operationCase_ == 17;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrInstanceInitializerCall getInstanceInitializerCall() {
            if (this.operationCase_ == 17) {
                return (IrInstanceInitializerCall) this.operation_;
            }
            return IrInstanceInitializerCall.getDefaultInstance();
        }

        public Builder setInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
            irInstanceInitializerCall.getClass();
            this.operation_ = irInstanceInitializerCall;
            this.operationCase_ = 17;
            return this;
        }

        public Builder setInstanceInitializerCall(IrInstanceInitializerCall.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 17;
            return this;
        }

        public Builder mergeInstanceInitializerCall(IrInstanceInitializerCall irInstanceInitializerCall) {
            if (this.operationCase_ == 17 && this.operation_ != IrInstanceInitializerCall.getDefaultInstance()) {
                this.operation_ = IrInstanceInitializerCall.newBuilder((IrInstanceInitializerCall) this.operation_).mergeFrom(irInstanceInitializerCall).buildPartial();
            } else {
                this.operation_ = irInstanceInitializerCall;
            }
            this.operationCase_ = 17;
            return this;
        }

        public Builder clearInstanceInitializerCall() {
            if (this.operationCase_ == 17) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasPropertyReference() {
            return this.operationCase_ == 18;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrPropertyReference getPropertyReference() {
            if (this.operationCase_ == 18) {
                return (IrPropertyReference) this.operation_;
            }
            return IrPropertyReference.getDefaultInstance();
        }

        public Builder setPropertyReference(IrPropertyReference irPropertyReference) {
            irPropertyReference.getClass();
            this.operation_ = irPropertyReference;
            this.operationCase_ = 18;
            return this;
        }

        public Builder setPropertyReference(IrPropertyReference.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 18;
            return this;
        }

        public Builder mergePropertyReference(IrPropertyReference irPropertyReference) {
            if (this.operationCase_ == 18 && this.operation_ != IrPropertyReference.getDefaultInstance()) {
                this.operation_ = IrPropertyReference.newBuilder((IrPropertyReference) this.operation_).mergeFrom(irPropertyReference).buildPartial();
            } else {
                this.operation_ = irPropertyReference;
            }
            this.operationCase_ = 18;
            return this;
        }

        public Builder clearPropertyReference() {
            if (this.operationCase_ == 18) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasReturn() {
            return this.operationCase_ == 19;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrReturn getReturn() {
            if (this.operationCase_ == 19) {
                return (IrReturn) this.operation_;
            }
            return IrReturn.getDefaultInstance();
        }

        public Builder setReturn(IrReturn irReturn) {
            irReturn.getClass();
            this.operation_ = irReturn;
            this.operationCase_ = 19;
            return this;
        }

        public Builder setReturn(IrReturn.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 19;
            return this;
        }

        public Builder mergeReturn(IrReturn irReturn) {
            if (this.operationCase_ == 19 && this.operation_ != IrReturn.getDefaultInstance()) {
                this.operation_ = IrReturn.newBuilder((IrReturn) this.operation_).mergeFrom(irReturn).buildPartial();
            } else {
                this.operation_ = irReturn;
            }
            this.operationCase_ = 19;
            return this;
        }

        public Builder clearReturn() {
            if (this.operationCase_ == 19) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasSetField() {
            return this.operationCase_ == 20;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrSetField getSetField() {
            if (this.operationCase_ == 20) {
                return (IrSetField) this.operation_;
            }
            return IrSetField.getDefaultInstance();
        }

        public Builder setSetField(IrSetField irSetField) {
            irSetField.getClass();
            this.operation_ = irSetField;
            this.operationCase_ = 20;
            return this;
        }

        public Builder setSetField(IrSetField.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 20;
            return this;
        }

        public Builder mergeSetField(IrSetField irSetField) {
            if (this.operationCase_ == 20 && this.operation_ != IrSetField.getDefaultInstance()) {
                this.operation_ = IrSetField.newBuilder((IrSetField) this.operation_).mergeFrom(irSetField).buildPartial();
            } else {
                this.operation_ = irSetField;
            }
            this.operationCase_ = 20;
            return this;
        }

        public Builder clearSetField() {
            if (this.operationCase_ == 20) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasSetValue() {
            return this.operationCase_ == 21;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrSetValue getSetValue() {
            if (this.operationCase_ == 21) {
                return (IrSetValue) this.operation_;
            }
            return IrSetValue.getDefaultInstance();
        }

        public Builder setSetValue(IrSetValue irSetValue) {
            irSetValue.getClass();
            this.operation_ = irSetValue;
            this.operationCase_ = 21;
            return this;
        }

        public Builder setSetValue(IrSetValue.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 21;
            return this;
        }

        public Builder mergeSetValue(IrSetValue irSetValue) {
            if (this.operationCase_ == 21 && this.operation_ != IrSetValue.getDefaultInstance()) {
                this.operation_ = IrSetValue.newBuilder((IrSetValue) this.operation_).mergeFrom(irSetValue).buildPartial();
            } else {
                this.operation_ = irSetValue;
            }
            this.operationCase_ = 21;
            return this;
        }

        public Builder clearSetValue() {
            if (this.operationCase_ == 21) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasStringConcat() {
            return this.operationCase_ == 22;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrStringConcat getStringConcat() {
            if (this.operationCase_ == 22) {
                return (IrStringConcat) this.operation_;
            }
            return IrStringConcat.getDefaultInstance();
        }

        public Builder setStringConcat(IrStringConcat irStringConcat) {
            irStringConcat.getClass();
            this.operation_ = irStringConcat;
            this.operationCase_ = 22;
            return this;
        }

        public Builder setStringConcat(IrStringConcat.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 22;
            return this;
        }

        public Builder mergeStringConcat(IrStringConcat irStringConcat) {
            if (this.operationCase_ == 22 && this.operation_ != IrStringConcat.getDefaultInstance()) {
                this.operation_ = IrStringConcat.newBuilder((IrStringConcat) this.operation_).mergeFrom(irStringConcat).buildPartial();
            } else {
                this.operation_ = irStringConcat;
            }
            this.operationCase_ = 22;
            return this;
        }

        public Builder clearStringConcat() {
            if (this.operationCase_ == 22) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasThrow() {
            return this.operationCase_ == 23;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrThrow getThrow() {
            if (this.operationCase_ == 23) {
                return (IrThrow) this.operation_;
            }
            return IrThrow.getDefaultInstance();
        }

        public Builder setThrow(IrThrow irThrow) {
            irThrow.getClass();
            this.operation_ = irThrow;
            this.operationCase_ = 23;
            return this;
        }

        public Builder setThrow(IrThrow.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 23;
            return this;
        }

        public Builder mergeThrow(IrThrow irThrow) {
            if (this.operationCase_ == 23 && this.operation_ != IrThrow.getDefaultInstance()) {
                this.operation_ = IrThrow.newBuilder((IrThrow) this.operation_).mergeFrom(irThrow).buildPartial();
            } else {
                this.operation_ = irThrow;
            }
            this.operationCase_ = 23;
            return this;
        }

        public Builder clearThrow() {
            if (this.operationCase_ == 23) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasTry() {
            return this.operationCase_ == 24;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrTry getTry() {
            if (this.operationCase_ == 24) {
                return (IrTry) this.operation_;
            }
            return IrTry.getDefaultInstance();
        }

        public Builder setTry(IrTry irTry) {
            irTry.getClass();
            this.operation_ = irTry;
            this.operationCase_ = 24;
            return this;
        }

        public Builder setTry(IrTry.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 24;
            return this;
        }

        public Builder mergeTry(IrTry irTry) {
            if (this.operationCase_ == 24 && this.operation_ != IrTry.getDefaultInstance()) {
                this.operation_ = IrTry.newBuilder((IrTry) this.operation_).mergeFrom(irTry).buildPartial();
            } else {
                this.operation_ = irTry;
            }
            this.operationCase_ = 24;
            return this;
        }

        public Builder clearTry() {
            if (this.operationCase_ == 24) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasTypeOp() {
            return this.operationCase_ == 25;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrTypeOp getTypeOp() {
            if (this.operationCase_ == 25) {
                return (IrTypeOp) this.operation_;
            }
            return IrTypeOp.getDefaultInstance();
        }

        public Builder setTypeOp(IrTypeOp irTypeOp) {
            irTypeOp.getClass();
            this.operation_ = irTypeOp;
            this.operationCase_ = 25;
            return this;
        }

        public Builder setTypeOp(IrTypeOp.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 25;
            return this;
        }

        public Builder mergeTypeOp(IrTypeOp irTypeOp) {
            if (this.operationCase_ == 25 && this.operation_ != IrTypeOp.getDefaultInstance()) {
                this.operation_ = IrTypeOp.newBuilder((IrTypeOp) this.operation_).mergeFrom(irTypeOp).buildPartial();
            } else {
                this.operation_ = irTypeOp;
            }
            this.operationCase_ = 25;
            return this;
        }

        public Builder clearTypeOp() {
            if (this.operationCase_ == 25) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasVararg() {
            return this.operationCase_ == 26;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrVararg getVararg() {
            if (this.operationCase_ == 26) {
                return (IrVararg) this.operation_;
            }
            return IrVararg.getDefaultInstance();
        }

        public Builder setVararg(IrVararg irVararg) {
            irVararg.getClass();
            this.operation_ = irVararg;
            this.operationCase_ = 26;
            return this;
        }

        public Builder setVararg(IrVararg.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 26;
            return this;
        }

        public Builder mergeVararg(IrVararg irVararg) {
            if (this.operationCase_ == 26 && this.operation_ != IrVararg.getDefaultInstance()) {
                this.operation_ = IrVararg.newBuilder((IrVararg) this.operation_).mergeFrom(irVararg).buildPartial();
            } else {
                this.operation_ = irVararg;
            }
            this.operationCase_ = 26;
            return this;
        }

        public Builder clearVararg() {
            if (this.operationCase_ == 26) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasWhen() {
            return this.operationCase_ == 27;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrWhen getWhen() {
            if (this.operationCase_ == 27) {
                return (IrWhen) this.operation_;
            }
            return IrWhen.getDefaultInstance();
        }

        public Builder setWhen(IrWhen irWhen) {
            irWhen.getClass();
            this.operation_ = irWhen;
            this.operationCase_ = 27;
            return this;
        }

        public Builder setWhen(IrWhen.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 27;
            return this;
        }

        public Builder mergeWhen(IrWhen irWhen) {
            if (this.operationCase_ == 27 && this.operation_ != IrWhen.getDefaultInstance()) {
                this.operation_ = IrWhen.newBuilder((IrWhen) this.operation_).mergeFrom(irWhen).buildPartial();
            } else {
                this.operation_ = irWhen;
            }
            this.operationCase_ = 27;
            return this;
        }

        public Builder clearWhen() {
            if (this.operationCase_ == 27) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasWhile() {
            return this.operationCase_ == 28;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrWhile getWhile() {
            if (this.operationCase_ == 28) {
                return (IrWhile) this.operation_;
            }
            return IrWhile.getDefaultInstance();
        }

        public Builder setWhile(IrWhile irWhile) {
            irWhile.getClass();
            this.operation_ = irWhile;
            this.operationCase_ = 28;
            return this;
        }

        public Builder setWhile(IrWhile.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 28;
            return this;
        }

        public Builder mergeWhile(IrWhile irWhile) {
            if (this.operationCase_ == 28 && this.operation_ != IrWhile.getDefaultInstance()) {
                this.operation_ = IrWhile.newBuilder((IrWhile) this.operation_).mergeFrom(irWhile).buildPartial();
            } else {
                this.operation_ = irWhile;
            }
            this.operationCase_ = 28;
            return this;
        }

        public Builder clearWhile() {
            if (this.operationCase_ == 28) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasDynamicMember() {
            return this.operationCase_ == 29;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrDynamicMemberExpression getDynamicMember() {
            if (this.operationCase_ == 29) {
                return (IrDynamicMemberExpression) this.operation_;
            }
            return IrDynamicMemberExpression.getDefaultInstance();
        }

        public Builder setDynamicMember(IrDynamicMemberExpression irDynamicMemberExpression) {
            irDynamicMemberExpression.getClass();
            this.operation_ = irDynamicMemberExpression;
            this.operationCase_ = 29;
            return this;
        }

        public Builder setDynamicMember(IrDynamicMemberExpression.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 29;
            return this;
        }

        public Builder mergeDynamicMember(IrDynamicMemberExpression irDynamicMemberExpression) {
            if (this.operationCase_ == 29 && this.operation_ != IrDynamicMemberExpression.getDefaultInstance()) {
                this.operation_ = IrDynamicMemberExpression.newBuilder((IrDynamicMemberExpression) this.operation_).mergeFrom(irDynamicMemberExpression).buildPartial();
            } else {
                this.operation_ = irDynamicMemberExpression;
            }
            this.operationCase_ = 29;
            return this;
        }

        public Builder clearDynamicMember() {
            if (this.operationCase_ == 29) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasDynamicOperator() {
            return this.operationCase_ == 30;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrDynamicOperatorExpression getDynamicOperator() {
            if (this.operationCase_ == 30) {
                return (IrDynamicOperatorExpression) this.operation_;
            }
            return IrDynamicOperatorExpression.getDefaultInstance();
        }

        public Builder setDynamicOperator(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            irDynamicOperatorExpression.getClass();
            this.operation_ = irDynamicOperatorExpression;
            this.operationCase_ = 30;
            return this;
        }

        public Builder setDynamicOperator(IrDynamicOperatorExpression.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 30;
            return this;
        }

        public Builder mergeDynamicOperator(IrDynamicOperatorExpression irDynamicOperatorExpression) {
            if (this.operationCase_ == 30 && this.operation_ != IrDynamicOperatorExpression.getDefaultInstance()) {
                this.operation_ = IrDynamicOperatorExpression.newBuilder((IrDynamicOperatorExpression) this.operation_).mergeFrom(irDynamicOperatorExpression).buildPartial();
            } else {
                this.operation_ = irDynamicOperatorExpression;
            }
            this.operationCase_ = 30;
            return this;
        }

        public Builder clearDynamicOperator() {
            if (this.operationCase_ == 30) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasLocalDelegatedPropertyReference() {
            return this.operationCase_ == 31;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrLocalDelegatedPropertyReference getLocalDelegatedPropertyReference() {
            if (this.operationCase_ == 31) {
                return (IrLocalDelegatedPropertyReference) this.operation_;
            }
            return IrLocalDelegatedPropertyReference.getDefaultInstance();
        }

        public Builder setLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
            irLocalDelegatedPropertyReference.getClass();
            this.operation_ = irLocalDelegatedPropertyReference;
            this.operationCase_ = 31;
            return this;
        }

        public Builder setLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 31;
            return this;
        }

        public Builder mergeLocalDelegatedPropertyReference(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
            if (this.operationCase_ == 31 && this.operation_ != IrLocalDelegatedPropertyReference.getDefaultInstance()) {
                this.operation_ = IrLocalDelegatedPropertyReference.newBuilder((IrLocalDelegatedPropertyReference) this.operation_).mergeFrom(irLocalDelegatedPropertyReference).buildPartial();
            } else {
                this.operation_ = irLocalDelegatedPropertyReference;
            }
            this.operationCase_ = 31;
            return this;
        }

        public Builder clearLocalDelegatedPropertyReference() {
            if (this.operationCase_ == 31) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasConstructorCall() {
            return this.operationCase_ == 32;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrConstructorCall getConstructorCall() {
            if (this.operationCase_ == 32) {
                return (IrConstructorCall) this.operation_;
            }
            return IrConstructorCall.getDefaultInstance();
        }

        public Builder setConstructorCall(IrConstructorCall irConstructorCall) {
            irConstructorCall.getClass();
            this.operation_ = irConstructorCall;
            this.operationCase_ = 32;
            return this;
        }

        public Builder setConstructorCall(IrConstructorCall.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 32;
            return this;
        }

        public Builder mergeConstructorCall(IrConstructorCall irConstructorCall) {
            if (this.operationCase_ == 32 && this.operation_ != IrConstructorCall.getDefaultInstance()) {
                this.operation_ = IrConstructorCall.newBuilder((IrConstructorCall) this.operation_).mergeFrom(irConstructorCall).buildPartial();
            } else {
                this.operation_ = irConstructorCall;
            }
            this.operationCase_ = 32;
            return this;
        }

        public Builder clearConstructorCall() {
            if (this.operationCase_ == 32) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasFunctionExpression() {
            return this.operationCase_ == 33;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrFunctionExpression getFunctionExpression() {
            if (this.operationCase_ == 33) {
                return (IrFunctionExpression) this.operation_;
            }
            return IrFunctionExpression.getDefaultInstance();
        }

        public Builder setFunctionExpression(IrFunctionExpression irFunctionExpression) {
            irFunctionExpression.getClass();
            this.operation_ = irFunctionExpression;
            this.operationCase_ = 33;
            return this;
        }

        public Builder setFunctionExpression(IrFunctionExpression.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 33;
            return this;
        }

        public Builder mergeFunctionExpression(IrFunctionExpression irFunctionExpression) {
            if (this.operationCase_ == 33 && this.operation_ != IrFunctionExpression.getDefaultInstance()) {
                this.operation_ = IrFunctionExpression.newBuilder((IrFunctionExpression) this.operation_).mergeFrom(irFunctionExpression).buildPartial();
            } else {
                this.operation_ = irFunctionExpression;
            }
            this.operationCase_ = 33;
            return this;
        }

        public Builder clearFunctionExpression() {
            if (this.operationCase_ == 33) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasErrorExpression() {
            return this.operationCase_ == 34;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrErrorExpression getErrorExpression() {
            if (this.operationCase_ == 34) {
                return (IrErrorExpression) this.operation_;
            }
            return IrErrorExpression.getDefaultInstance();
        }

        public Builder setErrorExpression(IrErrorExpression irErrorExpression) {
            irErrorExpression.getClass();
            this.operation_ = irErrorExpression;
            this.operationCase_ = 34;
            return this;
        }

        public Builder setErrorExpression(IrErrorExpression.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 34;
            return this;
        }

        public Builder mergeErrorExpression(IrErrorExpression irErrorExpression) {
            if (this.operationCase_ == 34 && this.operation_ != IrErrorExpression.getDefaultInstance()) {
                this.operation_ = IrErrorExpression.newBuilder((IrErrorExpression) this.operation_).mergeFrom(irErrorExpression).buildPartial();
            } else {
                this.operation_ = irErrorExpression;
            }
            this.operationCase_ = 34;
            return this;
        }

        public Builder clearErrorExpression() {
            if (this.operationCase_ == 34) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public boolean hasErrorCallExpression() {
            return this.operationCase_ == 35;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrOperationOrBuilder
        public IrErrorCallExpression getErrorCallExpression() {
            if (this.operationCase_ == 35) {
                return (IrErrorCallExpression) this.operation_;
            }
            return IrErrorCallExpression.getDefaultInstance();
        }

        public Builder setErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
            irErrorCallExpression.getClass();
            this.operation_ = irErrorCallExpression;
            this.operationCase_ = 35;
            return this;
        }

        public Builder setErrorCallExpression(IrErrorCallExpression.Builder builder) {
            this.operation_ = builder.build();
            this.operationCase_ = 35;
            return this;
        }

        public Builder mergeErrorCallExpression(IrErrorCallExpression irErrorCallExpression) {
            if (this.operationCase_ == 35 && this.operation_ != IrErrorCallExpression.getDefaultInstance()) {
                this.operation_ = IrErrorCallExpression.newBuilder((IrErrorCallExpression) this.operation_).mergeFrom(irErrorCallExpression).buildPartial();
            } else {
                this.operation_ = irErrorCallExpression;
            }
            this.operationCase_ = 35;
            return this;
        }

        public Builder clearErrorCallExpression() {
            if (this.operationCase_ == 35) {
                this.operationCase_ = 0;
                this.operation_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IrOperation$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase;

        static {
            int[] iArr = new int[OperationCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase = iArr;
            try {
                iArr[OperationCase.BLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.BREAK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.CALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.CLASS_REFERENCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.COMPOSITE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.CONST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.CONTINUE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.DELEGATING_CONSTRUCTOR_CALL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.DO_WHILE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.ENUM_CONSTRUCTOR_CALL.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.FUNCTION_REFERENCE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.GET_CLASS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.GET_ENUM_VALUE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.GET_FIELD.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.GET_OBJECT.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.GET_VALUE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.INSTANCE_INITIALIZER_CALL.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.PROPERTY_REFERENCE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.RETURN.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.SET_FIELD.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.SET_VALUE.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.STRING_CONCAT.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.THROW.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.TRY.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.TYPE_OP.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.VARARG.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.WHEN.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.WHILE.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.DYNAMIC_MEMBER.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.DYNAMIC_OPERATOR.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.LOCAL_DELEGATED_PROPERTY_REFERENCE.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.CONSTRUCTOR_CALL.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.FUNCTION_EXPRESSION.ordinal()] = 33;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.ERROR_EXPRESSION.ordinal()] = 34;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.ERROR_CALL_EXPRESSION.ordinal()] = 35;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrOperation$OperationCase[OperationCase.OPERATION_NOT_SET.ordinal()] = 36;
            } catch (NoSuchFieldError unused36) {
            }
        }
    }
}
