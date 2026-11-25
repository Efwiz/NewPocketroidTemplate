package org.jetbrains.kotlin.backend.jvm;

import java.util.Map;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: JvmGeneratorExtensions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u001e\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "", "cachedFields", "Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "getCachedFields", "()Lorg/jetbrains/kotlin/backend/jvm/CachedFieldsForObjectInstances;", "classNameOverride", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassNameOverride", "()Ljava/util/Map;", "rawTypeAnnotationConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getRawTypeAnnotationConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmGeneratorExtensions {
    CachedFieldsForObjectInstances getCachedFields();

    Map<IrClass, JvmClassName> getClassNameOverride();

    IrConstructor getRawTypeAnnotationConstructor();
}
