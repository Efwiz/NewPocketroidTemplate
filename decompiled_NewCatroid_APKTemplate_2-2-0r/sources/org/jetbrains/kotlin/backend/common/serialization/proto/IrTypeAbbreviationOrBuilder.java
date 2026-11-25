package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrTypeAbbreviationOrBuilder extends MessageLiteOrBuilder {
    IrConstructorCall getAnnotation(int i);

    int getAnnotationCount();

    List<IrConstructorCall> getAnnotationList();

    long getArgument(int i);

    int getArgumentCount();

    List<Long> getArgumentList();

    boolean getHasQuestionMark();

    long getTypeAlias();

    boolean hasHasQuestionMark();

    boolean hasTypeAlias();
}
