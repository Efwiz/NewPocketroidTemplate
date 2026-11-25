package org.jetbrains.kotlin.backend.common.serialization.proto;

import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrOperationOrBuilder extends MessageLiteOrBuilder {
    IrBlock getBlock();

    IrBreak getBreak();

    IrCall getCall();

    IrClassReference getClassReference();

    IrComposite getComposite();

    IrConst getConst();

    IrConstructorCall getConstructorCall();

    IrContinue getContinue();

    IrDelegatingConstructorCall getDelegatingConstructorCall();

    IrDoWhile getDoWhile();

    IrDynamicMemberExpression getDynamicMember();

    IrDynamicOperatorExpression getDynamicOperator();

    IrEnumConstructorCall getEnumConstructorCall();

    IrErrorCallExpression getErrorCallExpression();

    IrErrorExpression getErrorExpression();

    IrFunctionExpression getFunctionExpression();

    IrFunctionReference getFunctionReference();

    IrGetClass getGetClass();

    IrGetEnumValue getGetEnumValue();

    IrGetField getGetField();

    IrGetObject getGetObject();

    IrGetValue getGetValue();

    IrInstanceInitializerCall getInstanceInitializerCall();

    IrLocalDelegatedPropertyReference getLocalDelegatedPropertyReference();

    IrPropertyReference getPropertyReference();

    IrReturn getReturn();

    IrSetField getSetField();

    IrSetValue getSetValue();

    IrStringConcat getStringConcat();

    IrThrow getThrow();

    IrTry getTry();

    IrTypeOp getTypeOp();

    IrVararg getVararg();

    IrWhen getWhen();

    IrWhile getWhile();

    boolean hasBlock();

    boolean hasBreak();

    boolean hasCall();

    boolean hasClassReference();

    boolean hasComposite();

    boolean hasConst();

    boolean hasConstructorCall();

    boolean hasContinue();

    boolean hasDelegatingConstructorCall();

    boolean hasDoWhile();

    boolean hasDynamicMember();

    boolean hasDynamicOperator();

    boolean hasEnumConstructorCall();

    boolean hasErrorCallExpression();

    boolean hasErrorExpression();

    boolean hasFunctionExpression();

    boolean hasFunctionReference();

    boolean hasGetClass();

    boolean hasGetEnumValue();

    boolean hasGetField();

    boolean hasGetObject();

    boolean hasGetValue();

    boolean hasInstanceInitializerCall();

    boolean hasLocalDelegatedPropertyReference();

    boolean hasPropertyReference();

    boolean hasReturn();

    boolean hasSetField();

    boolean hasSetValue();

    boolean hasStringConcat();

    boolean hasThrow();

    boolean hasTry();

    boolean hasTypeOp();

    boolean hasVararg();

    boolean hasWhen();

    boolean hasWhile();
}
