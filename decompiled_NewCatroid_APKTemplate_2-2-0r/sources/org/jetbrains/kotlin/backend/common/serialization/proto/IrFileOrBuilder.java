package org.jetbrains.kotlin.backend.common.serialization.proto;

import java.util.List;
import org.jetbrains.kotlin.protobuf.MessageLiteOrBuilder;

/* JADX INFO: loaded from: classes2.dex */
public interface IrFileOrBuilder extends MessageLiteOrBuilder {
    Actual getActual(int i);

    int getActualCount();

    List<Actual> getActualList();

    IrConstructorCall getAnnotation(int i);

    int getAnnotationCount();

    List<IrConstructorCall> getAnnotationList();

    int getDeclarationId(int i);

    int getDeclarationIdCount();

    List<Integer> getDeclarationIdList();

    long getExplicitlyExportedToCompiler(int i);

    int getExplicitlyExportedToCompilerCount();

    List<Long> getExplicitlyExportedToCompilerList();

    FileEntry getFileEntry();

    int getFqName(int i);

    int getFqNameCount();

    List<Integer> getFqNameList();

    boolean hasFileEntry();
}
