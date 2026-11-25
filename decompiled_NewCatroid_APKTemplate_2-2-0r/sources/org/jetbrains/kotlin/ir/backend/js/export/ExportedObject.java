package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u0005HÆ\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005HÆ\u0003J\t\u0010 \u001a\u00020\fHÆ\u0003J\t\u0010!\u001a\u00020\u000eHÆ\u0003Jg\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&HÖ\u0003J\t\u0010'\u001a\u00020(HÖ\u0001J\t\u0010)\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedObject;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "name", "", "superClasses", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "superInterfaces", "members", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "nestedClasses", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getMembers", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getNestedClasses", "getSuperClasses", "getSuperInterfaces", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedObject extends ExportedClass {
    private final IrClass ir;
    private final IrSimpleFunction irGetter;
    private final List<ExportedDeclaration> members;
    private final String name;
    private final List<ExportedClass> nestedClasses;
    private final List<ExportedType> superClasses;
    private final List<ExportedType> superInterfaces;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExportedObject copy$default(ExportedObject exportedObject, String str, List list, List list2, List list3, List list4, IrClass irClass, IrSimpleFunction irSimpleFunction, int i, Object obj) {
        if ((i & 1) != 0) {
            str = exportedObject.name;
        }
        if ((i & 2) != 0) {
            list = exportedObject.superClasses;
        }
        List list5 = list;
        if ((i & 4) != 0) {
            list2 = exportedObject.superInterfaces;
        }
        List list6 = list2;
        if ((i & 8) != 0) {
            list3 = exportedObject.members;
        }
        List list7 = list3;
        if ((i & 16) != 0) {
            list4 = exportedObject.nestedClasses;
        }
        List list8 = list4;
        if ((i & 32) != 0) {
            irClass = exportedObject.ir;
        }
        IrClass irClass2 = irClass;
        if ((i & 64) != 0) {
            irSimpleFunction = exportedObject.irGetter;
        }
        return exportedObject.copy(str, list5, list6, list7, list8, irClass2, irSimpleFunction);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final List<ExportedType> component2() {
        return this.superClasses;
    }

    public final List<ExportedType> component3() {
        return this.superInterfaces;
    }

    public final List<ExportedDeclaration> component4() {
        return this.members;
    }

    public final List<ExportedClass> component5() {
        return this.nestedClasses;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final IrClass getIr() {
        return this.ir;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final IrSimpleFunction getIrGetter() {
        return this.irGetter;
    }

    public final ExportedObject copy(String name2, List<? extends ExportedType> superClasses, List<? extends ExportedType> superInterfaces, List<? extends ExportedDeclaration> members, List<? extends ExportedClass> nestedClasses, IrClass ir, IrSimpleFunction irGetter) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        Intrinsics.checkNotNullParameter(superInterfaces, "superInterfaces");
        Intrinsics.checkNotNullParameter(members, "members");
        Intrinsics.checkNotNullParameter(nestedClasses, "nestedClasses");
        Intrinsics.checkNotNullParameter(ir, "ir");
        Intrinsics.checkNotNullParameter(irGetter, "irGetter");
        return new ExportedObject(name2, superClasses, superInterfaces, members, nestedClasses, ir, irGetter);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedObject)) {
            return false;
        }
        ExportedObject exportedObject = (ExportedObject) other;
        return Intrinsics.areEqual(this.name, exportedObject.name) && Intrinsics.areEqual(this.superClasses, exportedObject.superClasses) && Intrinsics.areEqual(this.superInterfaces, exportedObject.superInterfaces) && Intrinsics.areEqual(this.members, exportedObject.members) && Intrinsics.areEqual(this.nestedClasses, exportedObject.nestedClasses) && Intrinsics.areEqual(this.ir, exportedObject.ir) && Intrinsics.areEqual(this.irGetter, exportedObject.irGetter);
    }

    public int hashCode() {
        return (((((((((((this.name.hashCode() * 31) + this.superClasses.hashCode()) * 31) + this.superInterfaces.hashCode()) * 31) + this.members.hashCode()) * 31) + this.nestedClasses.hashCode()) * 31) + this.ir.hashCode()) * 31) + this.irGetter.hashCode();
    }

    public String toString() {
        return "ExportedObject(name=" + this.name + ", superClasses=" + this.superClasses + ", superInterfaces=" + this.superInterfaces + ", members=" + this.members + ", nestedClasses=" + this.nestedClasses + ", ir=" + this.ir + ", irGetter=" + this.irGetter + ')';
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public String getName() {
        return this.name;
    }

    public /* synthetic */ ExportedObject(String str, List list, List list2, List list3, List list4, IrClass irClass, IrSimpleFunction irSimpleFunction, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? CollectionsKt.emptyList() : list2, list3, list4, irClass, irSimpleFunction);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedType> getSuperClasses() {
        return this.superClasses;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedType> getSuperInterfaces() {
        return this.superInterfaces;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedDeclaration> getMembers() {
        return this.members;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedClass> getNestedClasses() {
        return this.nestedClasses;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public IrClass getIr() {
        return this.ir;
    }

    public final IrSimpleFunction getIrGetter() {
        return this.irGetter;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ExportedObject(String name2, List<? extends ExportedType> superClasses, List<? extends ExportedType> superInterfaces, List<? extends ExportedDeclaration> members, List<? extends ExportedClass> nestedClasses, IrClass ir, IrSimpleFunction irGetter) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        Intrinsics.checkNotNullParameter(superInterfaces, "superInterfaces");
        Intrinsics.checkNotNullParameter(members, "members");
        Intrinsics.checkNotNullParameter(nestedClasses, "nestedClasses");
        Intrinsics.checkNotNullParameter(ir, "ir");
        Intrinsics.checkNotNullParameter(irGetter, "irGetter");
        this.name = name2;
        this.superClasses = superClasses;
        this.superInterfaces = superInterfaces;
        this.members = members;
        this.nestedClasses = nestedClasses;
        this.ir = ir;
        this.irGetter = irGetter;
    }
}
