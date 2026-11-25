package org.jetbrains.kotlin.backend.common.serialization.proto;

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
public final class IrDeclaration extends GeneratedMessageLite implements IrDeclarationOrBuilder {
    public static final int IR_ANONYMOUS_INIT_FIELD_NUMBER = 1;
    public static final int IR_CLASS_FIELD_NUMBER = 2;
    public static final int IR_CONSTRUCTOR_FIELD_NUMBER = 3;
    public static final int IR_ENUM_ENTRY_FIELD_NUMBER = 4;
    public static final int IR_ERROR_DECLARATION_FIELD_NUMBER = 13;
    public static final int IR_FIELD_FIELD_NUMBER = 5;
    public static final int IR_FUNCTION_FIELD_NUMBER = 6;
    public static final int IR_LOCAL_DELEGATED_PROPERTY_FIELD_NUMBER = 11;
    public static final int IR_PROPERTY_FIELD_NUMBER = 7;
    public static final int IR_TYPE_ALIAS_FIELD_NUMBER = 12;
    public static final int IR_TYPE_PARAMETER_FIELD_NUMBER = 8;
    public static final int IR_VALUE_PARAMETER_FIELD_NUMBER = 10;
    public static final int IR_VARIABLE_FIELD_NUMBER = 9;
    public static Parser<IrDeclaration> PARSER = new AbstractParser<IrDeclaration>() { // from class: org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration.1
        @Override // org.jetbrains.kotlin.protobuf.Parser
        public IrDeclaration parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return new IrDeclaration(codedInputStream, extensionRegistryLite);
        }
    };
    private static final IrDeclaration defaultInstance;
    private static final long serialVersionUID = 0;
    private int bitField0_;
    private int declaratorCase_;
    private Object declarator_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private final ByteString unknownFields;

    private void initFields() {
    }

    private IrDeclaration(GeneratedMessageLite.Builder builder) {
        super(builder);
        this.declaratorCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.getUnknownFields();
    }

    private IrDeclaration(boolean z) {
        this.declaratorCase_ = 0;
        this.memoizedIsInitialized = (byte) -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = ByteString.EMPTY;
    }

    public static IrDeclaration getDefaultInstance() {
        return defaultInstance;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
    public IrDeclaration getDefaultInstanceForType() {
        return defaultInstance;
    }

    private IrDeclaration(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        boolean z = false;
        this.declaratorCase_ = 0;
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
                            IrAnonymousInit.Builder builder = this.declaratorCase_ == 1 ? ((IrAnonymousInit) this.declarator_).toBuilder() : null;
                            MessageLite message = codedInputStream.readMessage(IrAnonymousInit.PARSER, extensionRegistryLite);
                            this.declarator_ = message;
                            if (builder != null) {
                                builder.mergeFrom((IrAnonymousInit) message);
                                this.declarator_ = builder.buildPartial();
                            }
                            this.declaratorCase_ = 1;
                            continue;
                        case 18:
                            IrClass.Builder builder2 = this.declaratorCase_ == 2 ? ((IrClass) this.declarator_).toBuilder() : null;
                            MessageLite message2 = codedInputStream.readMessage(IrClass.PARSER, extensionRegistryLite);
                            this.declarator_ = message2;
                            if (builder2 != null) {
                                builder2.mergeFrom((IrClass) message2);
                                this.declarator_ = builder2.buildPartial();
                            }
                            this.declaratorCase_ = 2;
                            continue;
                        case 26:
                            IrConstructor.Builder builder3 = this.declaratorCase_ == 3 ? ((IrConstructor) this.declarator_).toBuilder() : null;
                            MessageLite message3 = codedInputStream.readMessage(IrConstructor.PARSER, extensionRegistryLite);
                            this.declarator_ = message3;
                            if (builder3 != null) {
                                builder3.mergeFrom((IrConstructor) message3);
                                this.declarator_ = builder3.buildPartial();
                            }
                            this.declaratorCase_ = 3;
                            continue;
                        case 34:
                            IrEnumEntry.Builder builder4 = this.declaratorCase_ == 4 ? ((IrEnumEntry) this.declarator_).toBuilder() : null;
                            MessageLite message4 = codedInputStream.readMessage(IrEnumEntry.PARSER, extensionRegistryLite);
                            this.declarator_ = message4;
                            if (builder4 != null) {
                                builder4.mergeFrom((IrEnumEntry) message4);
                                this.declarator_ = builder4.buildPartial();
                            }
                            this.declaratorCase_ = 4;
                            continue;
                        case 42:
                            IrField.Builder builder5 = this.declaratorCase_ == 5 ? ((IrField) this.declarator_).toBuilder() : null;
                            MessageLite message5 = codedInputStream.readMessage(IrField.PARSER, extensionRegistryLite);
                            this.declarator_ = message5;
                            if (builder5 != null) {
                                builder5.mergeFrom((IrField) message5);
                                this.declarator_ = builder5.buildPartial();
                            }
                            this.declaratorCase_ = 5;
                            continue;
                        case 50:
                            IrFunction.Builder builder6 = this.declaratorCase_ == 6 ? ((IrFunction) this.declarator_).toBuilder() : null;
                            MessageLite message6 = codedInputStream.readMessage(IrFunction.PARSER, extensionRegistryLite);
                            this.declarator_ = message6;
                            if (builder6 != null) {
                                builder6.mergeFrom((IrFunction) message6);
                                this.declarator_ = builder6.buildPartial();
                            }
                            this.declaratorCase_ = 6;
                            continue;
                        case 58:
                            IrProperty.Builder builder7 = this.declaratorCase_ == 7 ? ((IrProperty) this.declarator_).toBuilder() : null;
                            MessageLite message7 = codedInputStream.readMessage(IrProperty.PARSER, extensionRegistryLite);
                            this.declarator_ = message7;
                            if (builder7 != null) {
                                builder7.mergeFrom((IrProperty) message7);
                                this.declarator_ = builder7.buildPartial();
                            }
                            this.declaratorCase_ = 7;
                            continue;
                        case 66:
                            IrTypeParameter.Builder builder8 = this.declaratorCase_ == 8 ? ((IrTypeParameter) this.declarator_).toBuilder() : null;
                            MessageLite message8 = codedInputStream.readMessage(IrTypeParameter.PARSER, extensionRegistryLite);
                            this.declarator_ = message8;
                            if (builder8 != null) {
                                builder8.mergeFrom((IrTypeParameter) message8);
                                this.declarator_ = builder8.buildPartial();
                            }
                            this.declaratorCase_ = 8;
                            continue;
                        case 74:
                            IrVariable.Builder builder9 = this.declaratorCase_ == 9 ? ((IrVariable) this.declarator_).toBuilder() : null;
                            MessageLite message9 = codedInputStream.readMessage(IrVariable.PARSER, extensionRegistryLite);
                            this.declarator_ = message9;
                            if (builder9 != null) {
                                builder9.mergeFrom((IrVariable) message9);
                                this.declarator_ = builder9.buildPartial();
                            }
                            this.declaratorCase_ = 9;
                            continue;
                        case 82:
                            IrValueParameter.Builder builder10 = this.declaratorCase_ == 10 ? ((IrValueParameter) this.declarator_).toBuilder() : null;
                            MessageLite message10 = codedInputStream.readMessage(IrValueParameter.PARSER, extensionRegistryLite);
                            this.declarator_ = message10;
                            if (builder10 != null) {
                                builder10.mergeFrom((IrValueParameter) message10);
                                this.declarator_ = builder10.buildPartial();
                            }
                            this.declaratorCase_ = 10;
                            continue;
                        case 90:
                            IrLocalDelegatedProperty.Builder builder11 = this.declaratorCase_ == 11 ? ((IrLocalDelegatedProperty) this.declarator_).toBuilder() : null;
                            MessageLite message11 = codedInputStream.readMessage(IrLocalDelegatedProperty.PARSER, extensionRegistryLite);
                            this.declarator_ = message11;
                            if (builder11 != null) {
                                builder11.mergeFrom((IrLocalDelegatedProperty) message11);
                                this.declarator_ = builder11.buildPartial();
                            }
                            this.declaratorCase_ = 11;
                            continue;
                        case 98:
                            IrTypeAlias.Builder builder12 = this.declaratorCase_ == 12 ? ((IrTypeAlias) this.declarator_).toBuilder() : null;
                            MessageLite message12 = codedInputStream.readMessage(IrTypeAlias.PARSER, extensionRegistryLite);
                            this.declarator_ = message12;
                            if (builder12 != null) {
                                builder12.mergeFrom((IrTypeAlias) message12);
                                this.declarator_ = builder12.buildPartial();
                            }
                            this.declaratorCase_ = 12;
                            continue;
                        case 106:
                            IrErrorDeclaration.Builder builder13 = this.declaratorCase_ == 13 ? ((IrErrorDeclaration) this.declarator_).toBuilder() : null;
                            MessageLite message13 = codedInputStream.readMessage(IrErrorDeclaration.PARSER, extensionRegistryLite);
                            this.declarator_ = message13;
                            if (builder13 != null) {
                                builder13.mergeFrom((IrErrorDeclaration) message13);
                                this.declarator_ = builder13.buildPartial();
                            }
                            this.declaratorCase_ = 13;
                            continue;
                        default:
                            if (!parseUnknownField(codedInputStream, codedOutputStreamNewInstance, extensionRegistryLite, tag)) {
                                break;
                            }
                            break;
                    }
                    z = true;
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
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
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
        IrDeclaration irDeclaration = new IrDeclaration(true);
        defaultInstance = irDeclaration;
        irDeclaration.initFields();
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite, org.jetbrains.kotlin.protobuf.MessageLite
    public Parser<IrDeclaration> getParserForType() {
        return PARSER;
    }

    public enum DeclaratorCase implements Internal.EnumLite {
        IR_ANONYMOUS_INIT(1),
        IR_CLASS(2),
        IR_CONSTRUCTOR(3),
        IR_ENUM_ENTRY(4),
        IR_FIELD(5),
        IR_FUNCTION(6),
        IR_PROPERTY(7),
        IR_TYPE_PARAMETER(8),
        IR_VARIABLE(9),
        IR_VALUE_PARAMETER(10),
        IR_LOCAL_DELEGATED_PROPERTY(11),
        IR_TYPE_ALIAS(12),
        IR_ERROR_DECLARATION(13),
        DECLARATOR_NOT_SET(0);

        private int value;

        DeclaratorCase(int i) {
            this.value = i;
        }

        public static DeclaratorCase valueOf(int i) {
            switch (i) {
                case 0:
                    return DECLARATOR_NOT_SET;
                case 1:
                    return IR_ANONYMOUS_INIT;
                case 2:
                    return IR_CLASS;
                case 3:
                    return IR_CONSTRUCTOR;
                case 4:
                    return IR_ENUM_ENTRY;
                case 5:
                    return IR_FIELD;
                case 6:
                    return IR_FUNCTION;
                case 7:
                    return IR_PROPERTY;
                case 8:
                    return IR_TYPE_PARAMETER;
                case 9:
                    return IR_VARIABLE;
                case 10:
                    return IR_VALUE_PARAMETER;
                case 11:
                    return IR_LOCAL_DELEGATED_PROPERTY;
                case 12:
                    return IR_TYPE_ALIAS;
                case 13:
                    return IR_ERROR_DECLARATION;
                default:
                    throw new IllegalArgumentException("Value is undefined for this oneof enum.");
            }
        }

        @Override // org.jetbrains.kotlin.protobuf.Internal.EnumLite
        public int getNumber() {
            return this.value;
        }
    }

    public DeclaratorCase getDeclaratorCase() {
        return DeclaratorCase.valueOf(this.declaratorCase_);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrAnonymousInit() {
        return this.declaratorCase_ == 1;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrAnonymousInit getIrAnonymousInit() {
        if (this.declaratorCase_ == 1) {
            return (IrAnonymousInit) this.declarator_;
        }
        return IrAnonymousInit.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrClass() {
        return this.declaratorCase_ == 2;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrClass getIrClass() {
        if (this.declaratorCase_ == 2) {
            return (IrClass) this.declarator_;
        }
        return IrClass.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrConstructor() {
        return this.declaratorCase_ == 3;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrConstructor getIrConstructor() {
        if (this.declaratorCase_ == 3) {
            return (IrConstructor) this.declarator_;
        }
        return IrConstructor.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrEnumEntry() {
        return this.declaratorCase_ == 4;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrEnumEntry getIrEnumEntry() {
        if (this.declaratorCase_ == 4) {
            return (IrEnumEntry) this.declarator_;
        }
        return IrEnumEntry.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrField() {
        return this.declaratorCase_ == 5;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrField getIrField() {
        if (this.declaratorCase_ == 5) {
            return (IrField) this.declarator_;
        }
        return IrField.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrFunction() {
        return this.declaratorCase_ == 6;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrFunction getIrFunction() {
        if (this.declaratorCase_ == 6) {
            return (IrFunction) this.declarator_;
        }
        return IrFunction.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrProperty() {
        return this.declaratorCase_ == 7;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrProperty getIrProperty() {
        if (this.declaratorCase_ == 7) {
            return (IrProperty) this.declarator_;
        }
        return IrProperty.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrTypeParameter() {
        return this.declaratorCase_ == 8;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrTypeParameter getIrTypeParameter() {
        if (this.declaratorCase_ == 8) {
            return (IrTypeParameter) this.declarator_;
        }
        return IrTypeParameter.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrVariable() {
        return this.declaratorCase_ == 9;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrVariable getIrVariable() {
        if (this.declaratorCase_ == 9) {
            return (IrVariable) this.declarator_;
        }
        return IrVariable.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrValueParameter() {
        return this.declaratorCase_ == 10;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrValueParameter getIrValueParameter() {
        if (this.declaratorCase_ == 10) {
            return (IrValueParameter) this.declarator_;
        }
        return IrValueParameter.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrLocalDelegatedProperty() {
        return this.declaratorCase_ == 11;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrLocalDelegatedProperty getIrLocalDelegatedProperty() {
        if (this.declaratorCase_ == 11) {
            return (IrLocalDelegatedProperty) this.declarator_;
        }
        return IrLocalDelegatedProperty.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrTypeAlias() {
        return this.declaratorCase_ == 12;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrTypeAlias getIrTypeAlias() {
        if (this.declaratorCase_ == 12) {
            return (IrTypeAlias) this.declarator_;
        }
        return IrTypeAlias.getDefaultInstance();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public boolean hasIrErrorDeclaration() {
        return this.declaratorCase_ == 13;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
    public IrErrorDeclaration getIrErrorDeclaration() {
        if (this.declaratorCase_ == 13) {
            return (IrErrorDeclaration) this.declarator_;
        }
        return IrErrorDeclaration.getDefaultInstance();
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
        if (hasIrAnonymousInit() && !getIrAnonymousInit().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrClass() && !getIrClass().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrConstructor() && !getIrConstructor().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrEnumEntry() && !getIrEnumEntry().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrField() && !getIrField().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrFunction() && !getIrFunction().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrProperty() && !getIrProperty().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrTypeParameter() && !getIrTypeParameter().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrVariable() && !getIrVariable().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrValueParameter() && !getIrValueParameter().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrLocalDelegatedProperty() && !getIrLocalDelegatedProperty().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrTypeAlias() && !getIrTypeAlias().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        if (hasIrErrorDeclaration() && !getIrErrorDeclaration().isInitialized()) {
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }
        this.memoizedIsInitialized = (byte) 1;
        return true;
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        getSerializedSize();
        if (this.declaratorCase_ == 1) {
            codedOutputStream.writeMessage(1, (IrAnonymousInit) this.declarator_);
        }
        if (this.declaratorCase_ == 2) {
            codedOutputStream.writeMessage(2, (IrClass) this.declarator_);
        }
        if (this.declaratorCase_ == 3) {
            codedOutputStream.writeMessage(3, (IrConstructor) this.declarator_);
        }
        if (this.declaratorCase_ == 4) {
            codedOutputStream.writeMessage(4, (IrEnumEntry) this.declarator_);
        }
        if (this.declaratorCase_ == 5) {
            codedOutputStream.writeMessage(5, (IrField) this.declarator_);
        }
        if (this.declaratorCase_ == 6) {
            codedOutputStream.writeMessage(6, (IrFunction) this.declarator_);
        }
        if (this.declaratorCase_ == 7) {
            codedOutputStream.writeMessage(7, (IrProperty) this.declarator_);
        }
        if (this.declaratorCase_ == 8) {
            codedOutputStream.writeMessage(8, (IrTypeParameter) this.declarator_);
        }
        if (this.declaratorCase_ == 9) {
            codedOutputStream.writeMessage(9, (IrVariable) this.declarator_);
        }
        if (this.declaratorCase_ == 10) {
            codedOutputStream.writeMessage(10, (IrValueParameter) this.declarator_);
        }
        if (this.declaratorCase_ == 11) {
            codedOutputStream.writeMessage(11, (IrLocalDelegatedProperty) this.declarator_);
        }
        if (this.declaratorCase_ == 12) {
            codedOutputStream.writeMessage(12, (IrTypeAlias) this.declarator_);
        }
        if (this.declaratorCase_ == 13) {
            codedOutputStream.writeMessage(13, (IrErrorDeclaration) this.declarator_);
        }
        codedOutputStream.writeRawBytes(this.unknownFields);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public int getSerializedSize() {
        int i = this.memoizedSerializedSize;
        if (i != -1) {
            return i;
        }
        int iComputeMessageSize = this.declaratorCase_ == 1 ? CodedOutputStream.computeMessageSize(1, (IrAnonymousInit) this.declarator_) : 0;
        if (this.declaratorCase_ == 2) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(2, (IrClass) this.declarator_);
        }
        if (this.declaratorCase_ == 3) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(3, (IrConstructor) this.declarator_);
        }
        if (this.declaratorCase_ == 4) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(4, (IrEnumEntry) this.declarator_);
        }
        if (this.declaratorCase_ == 5) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(5, (IrField) this.declarator_);
        }
        if (this.declaratorCase_ == 6) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(6, (IrFunction) this.declarator_);
        }
        if (this.declaratorCase_ == 7) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(7, (IrProperty) this.declarator_);
        }
        if (this.declaratorCase_ == 8) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(8, (IrTypeParameter) this.declarator_);
        }
        if (this.declaratorCase_ == 9) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(9, (IrVariable) this.declarator_);
        }
        if (this.declaratorCase_ == 10) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(10, (IrValueParameter) this.declarator_);
        }
        if (this.declaratorCase_ == 11) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(11, (IrLocalDelegatedProperty) this.declarator_);
        }
        if (this.declaratorCase_ == 12) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(12, (IrTypeAlias) this.declarator_);
        }
        if (this.declaratorCase_ == 13) {
            iComputeMessageSize += CodedOutputStream.computeMessageSize(13, (IrErrorDeclaration) this.declarator_);
        }
        int size = iComputeMessageSize + this.unknownFields.size();
        this.memoizedSerializedSize = size;
        return size;
    }

    @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite
    protected Object writeReplace() throws ObjectStreamException {
        return super.writeReplace();
    }

    public static IrDeclaration parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString);
    }

    public static IrDeclaration parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(byteString, extensionRegistryLite);
    }

    public static IrDeclaration parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr);
    }

    public static IrDeclaration parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return PARSER.parseFrom(bArr, extensionRegistryLite);
    }

    public static IrDeclaration parseFrom(InputStream inputStream) throws IOException {
        return PARSER.parseFrom(inputStream);
    }

    public static IrDeclaration parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(inputStream, extensionRegistryLite);
    }

    public static IrDeclaration parseDelimitedFrom(InputStream inputStream) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream);
    }

    public static IrDeclaration parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseDelimitedFrom(inputStream, extensionRegistryLite);
    }

    public static IrDeclaration parseFrom(CodedInputStream codedInputStream) throws IOException {
        return PARSER.parseFrom(codedInputStream);
    }

    public static IrDeclaration parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return PARSER.parseFrom(codedInputStream, extensionRegistryLite);
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder newBuilderForType() {
        return newBuilder();
    }

    public static Builder newBuilder(IrDeclaration irDeclaration) {
        return newBuilder().mergeFrom(irDeclaration);
    }

    @Override // org.jetbrains.kotlin.protobuf.MessageLite
    public Builder toBuilder() {
        return newBuilder(this);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<IrDeclaration, Builder> implements IrDeclarationOrBuilder {
        private int bitField0_;
        private int declaratorCase_ = 0;
        private Object declarator_;

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
            this.declaratorCase_ = 0;
            this.declarator_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder clone() {
            return create().mergeFrom(buildPartial());
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public IrDeclaration getDefaultInstanceForType() {
            return IrDeclaration.getDefaultInstance();
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDeclaration build() {
            IrDeclaration irDeclarationBuildPartial = buildPartial();
            if (irDeclarationBuildPartial.isInitialized()) {
                return irDeclarationBuildPartial;
            }
            throw newUninitializedMessageException(irDeclarationBuildPartial);
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public IrDeclaration buildPartial() {
            IrDeclaration irDeclaration = new IrDeclaration(this);
            if (this.declaratorCase_ == 1) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 2) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 3) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 4) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 5) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 6) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 7) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 8) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 9) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 10) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 11) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 12) {
                irDeclaration.declarator_ = this.declarator_;
            }
            if (this.declaratorCase_ == 13) {
                irDeclaration.declarator_ = this.declarator_;
            }
            irDeclaration.bitField0_ = 0;
            irDeclaration.declaratorCase_ = this.declaratorCase_;
            return irDeclaration;
        }

        @Override // org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder
        public Builder mergeFrom(IrDeclaration irDeclaration) {
            if (irDeclaration == IrDeclaration.getDefaultInstance()) {
                return this;
            }
            switch (AnonymousClass2.$SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[irDeclaration.getDeclaratorCase().ordinal()]) {
                case 1:
                    mergeIrAnonymousInit(irDeclaration.getIrAnonymousInit());
                    break;
                case 2:
                    mergeIrClass(irDeclaration.getIrClass());
                    break;
                case 3:
                    mergeIrConstructor(irDeclaration.getIrConstructor());
                    break;
                case 4:
                    mergeIrEnumEntry(irDeclaration.getIrEnumEntry());
                    break;
                case 5:
                    mergeIrField(irDeclaration.getIrField());
                    break;
                case 6:
                    mergeIrFunction(irDeclaration.getIrFunction());
                    break;
                case 7:
                    mergeIrProperty(irDeclaration.getIrProperty());
                    break;
                case 8:
                    mergeIrTypeParameter(irDeclaration.getIrTypeParameter());
                    break;
                case 9:
                    mergeIrVariable(irDeclaration.getIrVariable());
                    break;
                case 10:
                    mergeIrValueParameter(irDeclaration.getIrValueParameter());
                    break;
                case 11:
                    mergeIrLocalDelegatedProperty(irDeclaration.getIrLocalDelegatedProperty());
                    break;
                case 12:
                    mergeIrTypeAlias(irDeclaration.getIrTypeAlias());
                    break;
                case 13:
                    mergeIrErrorDeclaration(irDeclaration.getIrErrorDeclaration());
                    break;
            }
            setUnknownFields(getUnknownFields().concat(irDeclaration.unknownFields));
            return this;
        }

        @Override // org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            if (hasIrAnonymousInit() && !getIrAnonymousInit().isInitialized()) {
                return false;
            }
            if (hasIrClass() && !getIrClass().isInitialized()) {
                return false;
            }
            if (hasIrConstructor() && !getIrConstructor().isInitialized()) {
                return false;
            }
            if (hasIrEnumEntry() && !getIrEnumEntry().isInitialized()) {
                return false;
            }
            if (hasIrField() && !getIrField().isInitialized()) {
                return false;
            }
            if (hasIrFunction() && !getIrFunction().isInitialized()) {
                return false;
            }
            if (hasIrProperty() && !getIrProperty().isInitialized()) {
                return false;
            }
            if (hasIrTypeParameter() && !getIrTypeParameter().isInitialized()) {
                return false;
            }
            if (hasIrVariable() && !getIrVariable().isInitialized()) {
                return false;
            }
            if (hasIrValueParameter() && !getIrValueParameter().isInitialized()) {
                return false;
            }
            if (hasIrLocalDelegatedProperty() && !getIrLocalDelegatedProperty().isInitialized()) {
                return false;
            }
            if (!hasIrTypeAlias() || getIrTypeAlias().isInitialized()) {
                return !hasIrErrorDeclaration() || getIrErrorDeclaration().isInitialized();
            }
            return false;
        }

        /* JADX WARN: Code duplicated, block: B:15:0x001d  */
        @Override // org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder, org.jetbrains.kotlin.protobuf.MessageLite.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws Throwable {
            IrDeclaration irDeclaration = null;
            try {
                try {
                    IrDeclaration partialFrom = IrDeclaration.PARSER.parsePartialFrom(codedInputStream, extensionRegistryLite);
                    if (partialFrom != null) {
                        mergeFrom(partialFrom);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    IrDeclaration irDeclaration2 = (IrDeclaration) e.getUnfinishedMessage();
                    try {
                        throw e;
                    } catch (Throwable th) {
                        th = th;
                        irDeclaration = irDeclaration2;
                        if (irDeclaration != null) {
                            mergeFrom(irDeclaration);
                        }
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (irDeclaration != null) {
                    mergeFrom(irDeclaration);
                }
                throw th;
            }
        }

        public DeclaratorCase getDeclaratorCase() {
            return DeclaratorCase.valueOf(this.declaratorCase_);
        }

        public Builder clearDeclarator() {
            this.declaratorCase_ = 0;
            this.declarator_ = null;
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrAnonymousInit() {
            return this.declaratorCase_ == 1;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrAnonymousInit getIrAnonymousInit() {
            if (this.declaratorCase_ == 1) {
                return (IrAnonymousInit) this.declarator_;
            }
            return IrAnonymousInit.getDefaultInstance();
        }

        public Builder setIrAnonymousInit(IrAnonymousInit irAnonymousInit) {
            irAnonymousInit.getClass();
            this.declarator_ = irAnonymousInit;
            this.declaratorCase_ = 1;
            return this;
        }

        public Builder setIrAnonymousInit(IrAnonymousInit.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 1;
            return this;
        }

        public Builder mergeIrAnonymousInit(IrAnonymousInit irAnonymousInit) {
            if (this.declaratorCase_ == 1 && this.declarator_ != IrAnonymousInit.getDefaultInstance()) {
                this.declarator_ = IrAnonymousInit.newBuilder((IrAnonymousInit) this.declarator_).mergeFrom(irAnonymousInit).buildPartial();
            } else {
                this.declarator_ = irAnonymousInit;
            }
            this.declaratorCase_ = 1;
            return this;
        }

        public Builder clearIrAnonymousInit() {
            if (this.declaratorCase_ == 1) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrClass() {
            return this.declaratorCase_ == 2;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrClass getIrClass() {
            if (this.declaratorCase_ == 2) {
                return (IrClass) this.declarator_;
            }
            return IrClass.getDefaultInstance();
        }

        public Builder setIrClass(IrClass irClass) {
            irClass.getClass();
            this.declarator_ = irClass;
            this.declaratorCase_ = 2;
            return this;
        }

        public Builder setIrClass(IrClass.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 2;
            return this;
        }

        public Builder mergeIrClass(IrClass irClass) {
            if (this.declaratorCase_ == 2 && this.declarator_ != IrClass.getDefaultInstance()) {
                this.declarator_ = IrClass.newBuilder((IrClass) this.declarator_).mergeFrom(irClass).buildPartial();
            } else {
                this.declarator_ = irClass;
            }
            this.declaratorCase_ = 2;
            return this;
        }

        public Builder clearIrClass() {
            if (this.declaratorCase_ == 2) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrConstructor() {
            return this.declaratorCase_ == 3;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrConstructor getIrConstructor() {
            if (this.declaratorCase_ == 3) {
                return (IrConstructor) this.declarator_;
            }
            return IrConstructor.getDefaultInstance();
        }

        public Builder setIrConstructor(IrConstructor irConstructor) {
            irConstructor.getClass();
            this.declarator_ = irConstructor;
            this.declaratorCase_ = 3;
            return this;
        }

        public Builder setIrConstructor(IrConstructor.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 3;
            return this;
        }

        public Builder mergeIrConstructor(IrConstructor irConstructor) {
            if (this.declaratorCase_ == 3 && this.declarator_ != IrConstructor.getDefaultInstance()) {
                this.declarator_ = IrConstructor.newBuilder((IrConstructor) this.declarator_).mergeFrom(irConstructor).buildPartial();
            } else {
                this.declarator_ = irConstructor;
            }
            this.declaratorCase_ = 3;
            return this;
        }

        public Builder clearIrConstructor() {
            if (this.declaratorCase_ == 3) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrEnumEntry() {
            return this.declaratorCase_ == 4;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrEnumEntry getIrEnumEntry() {
            if (this.declaratorCase_ == 4) {
                return (IrEnumEntry) this.declarator_;
            }
            return IrEnumEntry.getDefaultInstance();
        }

        public Builder setIrEnumEntry(IrEnumEntry irEnumEntry) {
            irEnumEntry.getClass();
            this.declarator_ = irEnumEntry;
            this.declaratorCase_ = 4;
            return this;
        }

        public Builder setIrEnumEntry(IrEnumEntry.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 4;
            return this;
        }

        public Builder mergeIrEnumEntry(IrEnumEntry irEnumEntry) {
            if (this.declaratorCase_ == 4 && this.declarator_ != IrEnumEntry.getDefaultInstance()) {
                this.declarator_ = IrEnumEntry.newBuilder((IrEnumEntry) this.declarator_).mergeFrom(irEnumEntry).buildPartial();
            } else {
                this.declarator_ = irEnumEntry;
            }
            this.declaratorCase_ = 4;
            return this;
        }

        public Builder clearIrEnumEntry() {
            if (this.declaratorCase_ == 4) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrField() {
            return this.declaratorCase_ == 5;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrField getIrField() {
            if (this.declaratorCase_ == 5) {
                return (IrField) this.declarator_;
            }
            return IrField.getDefaultInstance();
        }

        public Builder setIrField(IrField irField) {
            irField.getClass();
            this.declarator_ = irField;
            this.declaratorCase_ = 5;
            return this;
        }

        public Builder setIrField(IrField.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 5;
            return this;
        }

        public Builder mergeIrField(IrField irField) {
            if (this.declaratorCase_ == 5 && this.declarator_ != IrField.getDefaultInstance()) {
                this.declarator_ = IrField.newBuilder((IrField) this.declarator_).mergeFrom(irField).buildPartial();
            } else {
                this.declarator_ = irField;
            }
            this.declaratorCase_ = 5;
            return this;
        }

        public Builder clearIrField() {
            if (this.declaratorCase_ == 5) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrFunction() {
            return this.declaratorCase_ == 6;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrFunction getIrFunction() {
            if (this.declaratorCase_ == 6) {
                return (IrFunction) this.declarator_;
            }
            return IrFunction.getDefaultInstance();
        }

        public Builder setIrFunction(IrFunction irFunction) {
            irFunction.getClass();
            this.declarator_ = irFunction;
            this.declaratorCase_ = 6;
            return this;
        }

        public Builder setIrFunction(IrFunction.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 6;
            return this;
        }

        public Builder mergeIrFunction(IrFunction irFunction) {
            if (this.declaratorCase_ == 6 && this.declarator_ != IrFunction.getDefaultInstance()) {
                this.declarator_ = IrFunction.newBuilder((IrFunction) this.declarator_).mergeFrom(irFunction).buildPartial();
            } else {
                this.declarator_ = irFunction;
            }
            this.declaratorCase_ = 6;
            return this;
        }

        public Builder clearIrFunction() {
            if (this.declaratorCase_ == 6) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrProperty() {
            return this.declaratorCase_ == 7;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrProperty getIrProperty() {
            if (this.declaratorCase_ == 7) {
                return (IrProperty) this.declarator_;
            }
            return IrProperty.getDefaultInstance();
        }

        public Builder setIrProperty(IrProperty irProperty) {
            irProperty.getClass();
            this.declarator_ = irProperty;
            this.declaratorCase_ = 7;
            return this;
        }

        public Builder setIrProperty(IrProperty.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 7;
            return this;
        }

        public Builder mergeIrProperty(IrProperty irProperty) {
            if (this.declaratorCase_ == 7 && this.declarator_ != IrProperty.getDefaultInstance()) {
                this.declarator_ = IrProperty.newBuilder((IrProperty) this.declarator_).mergeFrom(irProperty).buildPartial();
            } else {
                this.declarator_ = irProperty;
            }
            this.declaratorCase_ = 7;
            return this;
        }

        public Builder clearIrProperty() {
            if (this.declaratorCase_ == 7) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrTypeParameter() {
            return this.declaratorCase_ == 8;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrTypeParameter getIrTypeParameter() {
            if (this.declaratorCase_ == 8) {
                return (IrTypeParameter) this.declarator_;
            }
            return IrTypeParameter.getDefaultInstance();
        }

        public Builder setIrTypeParameter(IrTypeParameter irTypeParameter) {
            irTypeParameter.getClass();
            this.declarator_ = irTypeParameter;
            this.declaratorCase_ = 8;
            return this;
        }

        public Builder setIrTypeParameter(IrTypeParameter.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 8;
            return this;
        }

        public Builder mergeIrTypeParameter(IrTypeParameter irTypeParameter) {
            if (this.declaratorCase_ == 8 && this.declarator_ != IrTypeParameter.getDefaultInstance()) {
                this.declarator_ = IrTypeParameter.newBuilder((IrTypeParameter) this.declarator_).mergeFrom(irTypeParameter).buildPartial();
            } else {
                this.declarator_ = irTypeParameter;
            }
            this.declaratorCase_ = 8;
            return this;
        }

        public Builder clearIrTypeParameter() {
            if (this.declaratorCase_ == 8) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrVariable() {
            return this.declaratorCase_ == 9;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrVariable getIrVariable() {
            if (this.declaratorCase_ == 9) {
                return (IrVariable) this.declarator_;
            }
            return IrVariable.getDefaultInstance();
        }

        public Builder setIrVariable(IrVariable irVariable) {
            irVariable.getClass();
            this.declarator_ = irVariable;
            this.declaratorCase_ = 9;
            return this;
        }

        public Builder setIrVariable(IrVariable.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 9;
            return this;
        }

        public Builder mergeIrVariable(IrVariable irVariable) {
            if (this.declaratorCase_ == 9 && this.declarator_ != IrVariable.getDefaultInstance()) {
                this.declarator_ = IrVariable.newBuilder((IrVariable) this.declarator_).mergeFrom(irVariable).buildPartial();
            } else {
                this.declarator_ = irVariable;
            }
            this.declaratorCase_ = 9;
            return this;
        }

        public Builder clearIrVariable() {
            if (this.declaratorCase_ == 9) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrValueParameter() {
            return this.declaratorCase_ == 10;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrValueParameter getIrValueParameter() {
            if (this.declaratorCase_ == 10) {
                return (IrValueParameter) this.declarator_;
            }
            return IrValueParameter.getDefaultInstance();
        }

        public Builder setIrValueParameter(IrValueParameter irValueParameter) {
            irValueParameter.getClass();
            this.declarator_ = irValueParameter;
            this.declaratorCase_ = 10;
            return this;
        }

        public Builder setIrValueParameter(IrValueParameter.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 10;
            return this;
        }

        public Builder mergeIrValueParameter(IrValueParameter irValueParameter) {
            if (this.declaratorCase_ == 10 && this.declarator_ != IrValueParameter.getDefaultInstance()) {
                this.declarator_ = IrValueParameter.newBuilder((IrValueParameter) this.declarator_).mergeFrom(irValueParameter).buildPartial();
            } else {
                this.declarator_ = irValueParameter;
            }
            this.declaratorCase_ = 10;
            return this;
        }

        public Builder clearIrValueParameter() {
            if (this.declaratorCase_ == 10) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrLocalDelegatedProperty() {
            return this.declaratorCase_ == 11;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrLocalDelegatedProperty getIrLocalDelegatedProperty() {
            if (this.declaratorCase_ == 11) {
                return (IrLocalDelegatedProperty) this.declarator_;
            }
            return IrLocalDelegatedProperty.getDefaultInstance();
        }

        public Builder setIrLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            irLocalDelegatedProperty.getClass();
            this.declarator_ = irLocalDelegatedProperty;
            this.declaratorCase_ = 11;
            return this;
        }

        public Builder setIrLocalDelegatedProperty(IrLocalDelegatedProperty.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 11;
            return this;
        }

        public Builder mergeIrLocalDelegatedProperty(IrLocalDelegatedProperty irLocalDelegatedProperty) {
            if (this.declaratorCase_ == 11 && this.declarator_ != IrLocalDelegatedProperty.getDefaultInstance()) {
                this.declarator_ = IrLocalDelegatedProperty.newBuilder((IrLocalDelegatedProperty) this.declarator_).mergeFrom(irLocalDelegatedProperty).buildPartial();
            } else {
                this.declarator_ = irLocalDelegatedProperty;
            }
            this.declaratorCase_ = 11;
            return this;
        }

        public Builder clearIrLocalDelegatedProperty() {
            if (this.declaratorCase_ == 11) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrTypeAlias() {
            return this.declaratorCase_ == 12;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrTypeAlias getIrTypeAlias() {
            if (this.declaratorCase_ == 12) {
                return (IrTypeAlias) this.declarator_;
            }
            return IrTypeAlias.getDefaultInstance();
        }

        public Builder setIrTypeAlias(IrTypeAlias irTypeAlias) {
            irTypeAlias.getClass();
            this.declarator_ = irTypeAlias;
            this.declaratorCase_ = 12;
            return this;
        }

        public Builder setIrTypeAlias(IrTypeAlias.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 12;
            return this;
        }

        public Builder mergeIrTypeAlias(IrTypeAlias irTypeAlias) {
            if (this.declaratorCase_ == 12 && this.declarator_ != IrTypeAlias.getDefaultInstance()) {
                this.declarator_ = IrTypeAlias.newBuilder((IrTypeAlias) this.declarator_).mergeFrom(irTypeAlias).buildPartial();
            } else {
                this.declarator_ = irTypeAlias;
            }
            this.declaratorCase_ = 12;
            return this;
        }

        public Builder clearIrTypeAlias() {
            if (this.declaratorCase_ == 12) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public boolean hasIrErrorDeclaration() {
            return this.declaratorCase_ == 13;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationOrBuilder
        public IrErrorDeclaration getIrErrorDeclaration() {
            if (this.declaratorCase_ == 13) {
                return (IrErrorDeclaration) this.declarator_;
            }
            return IrErrorDeclaration.getDefaultInstance();
        }

        public Builder setIrErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
            irErrorDeclaration.getClass();
            this.declarator_ = irErrorDeclaration;
            this.declaratorCase_ = 13;
            return this;
        }

        public Builder setIrErrorDeclaration(IrErrorDeclaration.Builder builder) {
            this.declarator_ = builder.build();
            this.declaratorCase_ = 13;
            return this;
        }

        public Builder mergeIrErrorDeclaration(IrErrorDeclaration irErrorDeclaration) {
            if (this.declaratorCase_ == 13 && this.declarator_ != IrErrorDeclaration.getDefaultInstance()) {
                this.declarator_ = IrErrorDeclaration.newBuilder((IrErrorDeclaration) this.declarator_).mergeFrom(irErrorDeclaration).buildPartial();
            } else {
                this.declarator_ = irErrorDeclaration;
            }
            this.declaratorCase_ = 13;
            return this;
        }

        public Builder clearIrErrorDeclaration() {
            if (this.declaratorCase_ == 13) {
                this.declaratorCase_ = 0;
                this.declarator_ = null;
            }
            return this;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase;

        static {
            int[] iArr = new int[DeclaratorCase.values().length];
            $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase = iArr;
            try {
                iArr[DeclaratorCase.IR_ANONYMOUS_INIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_CONSTRUCTOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_ENUM_ENTRY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_FIELD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_FUNCTION.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_PROPERTY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_TYPE_PARAMETER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_VARIABLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_VALUE_PARAMETER.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_LOCAL_DELEGATED_PROPERTY.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_TYPE_ALIAS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.IR_ERROR_DECLARATION.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$backend$common$serialization$proto$IrDeclaration$DeclaratorCase[DeclaratorCase.DECLARATOR_NOT_SET.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }
}
