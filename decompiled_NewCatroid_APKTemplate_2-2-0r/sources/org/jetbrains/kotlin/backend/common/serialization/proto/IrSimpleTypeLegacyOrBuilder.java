package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrSimpleTypeLegacyOrBuilder extends MessageLiteOrBuilder {
    IrTypeAbbreviation getAbbreviation();

    IrConstructorCall getAnnotation(int i);

    int getAnnotationCount();

    List<IrConstructorCall> getAnnotationList();

    long getArgument(int i);

    int getArgumentCount();

    List<Long> getArgumentList();

    long getClassifier();

    boolean getHasQuestionMark();

    boolean hasAbbreviation();

    boolean hasClassifier();

    boolean hasHasQuestionMark();
}
