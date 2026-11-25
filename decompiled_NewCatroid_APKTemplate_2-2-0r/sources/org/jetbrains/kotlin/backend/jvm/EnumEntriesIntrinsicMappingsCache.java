package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;

/* JADX INFO: compiled from: EnumEntriesIntrinsicMappingsCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/EnumEntriesIntrinsicMappingsCache;", "", "()V", "generateMappingsClasses", "", "getEnumEntriesIntrinsicMappings", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "containingClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "enumClass", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class EnumEntriesIntrinsicMappingsCache {
    public abstract void generateMappingsClasses();

    public abstract IrField getEnumEntriesIntrinsicMappings(IrClass containingClass, IrClass enumClass);
}
