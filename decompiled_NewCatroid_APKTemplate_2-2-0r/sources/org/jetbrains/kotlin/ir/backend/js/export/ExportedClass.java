package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00000\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\nR\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\nR\u0018\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\n\u0082\u0001\u0002\u0016\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "()V", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "members", "", "getMembers", "()Ljava/util/List;", "name", "", "getName", "()Ljava/lang/String;", "nestedClasses", "getNestedClasses", "superClasses", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getSuperClasses", "superInterfaces", "getSuperInterfaces", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedObject;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedRegularClass;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExportedClass extends ExportedDeclaration {
    public /* synthetic */ ExportedClass(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract IrClass getIr();

    public abstract List<ExportedDeclaration> getMembers();

    public abstract String getName();

    public abstract List<ExportedClass> getNestedClasses();

    public abstract List<ExportedType> getSuperClasses();

    public abstract List<ExportedType> getSuperInterfaces();

    private ExportedClass() {
        super(null);
    }
}
