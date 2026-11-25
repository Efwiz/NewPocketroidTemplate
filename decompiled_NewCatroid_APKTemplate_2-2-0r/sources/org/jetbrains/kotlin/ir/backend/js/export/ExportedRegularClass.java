package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bs\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0005HÆ\u0003J\t\u0010 \u001a\u00020\u0005HÆ\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\bHÆ\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0\bHÆ\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\bHÆ\u0003J\t\u0010&\u001a\u00020\u0011HÆ\u0003J\u0081\u0001\u0010'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0011HÆ\u0001J\u0013\u0010(\u001a\u00020\u00052\b\u0010)\u001a\u0004\u0018\u00010*HÖ\u0003J\t\u0010+\u001a\u00020,HÖ\u0001J\t\u0010-\u001a\u00020\u0003HÖ\u0001R\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0015R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\b¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedRegularClass;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "name", "", "isInterface", "", "isAbstract", "superClasses", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "superInterfaces", "typeParameters", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeParameter;", "members", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "nestedClasses", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Ljava/lang/String;ZZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "getIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "()Z", "getMembers", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getNestedClasses", "getSuperClasses", "getSuperInterfaces", "getTypeParameters", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedRegularClass extends ExportedClass {
    private final IrClass ir;
    private final boolean isAbstract;
    private final boolean isInterface;
    private final List<ExportedDeclaration> members;
    private final String name;
    private final List<ExportedClass> nestedClasses;
    private final List<ExportedType> superClasses;
    private final List<ExportedType> superInterfaces;
    private final List<ExportedType.TypeParameter> typeParameters;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsInterface() {
        return this.isInterface;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsAbstract() {
        return this.isAbstract;
    }

    public final List<ExportedType> component4() {
        return this.superClasses;
    }

    public final List<ExportedType> component5() {
        return this.superInterfaces;
    }

    public final List<ExportedType.TypeParameter> component6() {
        return this.typeParameters;
    }

    public final List<ExportedDeclaration> component7() {
        return this.members;
    }

    public final List<ExportedClass> component8() {
        return this.nestedClasses;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final IrClass getIr() {
        return this.ir;
    }

    public final ExportedRegularClass copy(String name2, boolean isInterface, boolean isAbstract, List<? extends ExportedType> superClasses, List<? extends ExportedType> superInterfaces, List<ExportedType.TypeParameter> typeParameters, List<? extends ExportedDeclaration> members, List<? extends ExportedClass> nestedClasses, IrClass ir) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        Intrinsics.checkNotNullParameter(superInterfaces, "superInterfaces");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(members, "members");
        Intrinsics.checkNotNullParameter(nestedClasses, "nestedClasses");
        Intrinsics.checkNotNullParameter(ir, "ir");
        return new ExportedRegularClass(name2, isInterface, isAbstract, superClasses, superInterfaces, typeParameters, members, nestedClasses, ir);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedRegularClass)) {
            return false;
        }
        ExportedRegularClass exportedRegularClass = (ExportedRegularClass) other;
        return Intrinsics.areEqual(this.name, exportedRegularClass.name) && this.isInterface == exportedRegularClass.isInterface && this.isAbstract == exportedRegularClass.isAbstract && Intrinsics.areEqual(this.superClasses, exportedRegularClass.superClasses) && Intrinsics.areEqual(this.superInterfaces, exportedRegularClass.superInterfaces) && Intrinsics.areEqual(this.typeParameters, exportedRegularClass.typeParameters) && Intrinsics.areEqual(this.members, exportedRegularClass.members) && Intrinsics.areEqual(this.nestedClasses, exportedRegularClass.nestedClasses) && Intrinsics.areEqual(this.ir, exportedRegularClass.ir);
    }

    public int hashCode() {
        return (((((((((((((((this.name.hashCode() * 31) + Boolean.hashCode(this.isInterface)) * 31) + Boolean.hashCode(this.isAbstract)) * 31) + this.superClasses.hashCode()) * 31) + this.superInterfaces.hashCode()) * 31) + this.typeParameters.hashCode()) * 31) + this.members.hashCode()) * 31) + this.nestedClasses.hashCode()) * 31) + this.ir.hashCode();
    }

    public String toString() {
        return "ExportedRegularClass(name=" + this.name + ", isInterface=" + this.isInterface + ", isAbstract=" + this.isAbstract + ", superClasses=" + this.superClasses + ", superInterfaces=" + this.superInterfaces + ", typeParameters=" + this.typeParameters + ", members=" + this.members + ", nestedClasses=" + this.nestedClasses + ", ir=" + this.ir + ')';
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public String getName() {
        return this.name;
    }

    public final boolean isInterface() {
        return this.isInterface;
    }

    public final boolean isAbstract() {
        return this.isAbstract;
    }

    public /* synthetic */ ExportedRegularClass(String str, boolean z, boolean z2, List list, List list2, List list3, List list4, List list5, IrClass irClass, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? CollectionsKt.emptyList() : list, (i & 16) != 0 ? CollectionsKt.emptyList() : list2, list3, list4, list5, irClass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedType> getSuperClasses() {
        return this.superClasses;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedClass
    public List<ExportedType> getSuperInterfaces() {
        return this.superInterfaces;
    }

    public final List<ExportedType.TypeParameter> getTypeParameters() {
        return this.typeParameters;
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ExportedRegularClass(String name2, boolean z, boolean z2, List<? extends ExportedType> superClasses, List<? extends ExportedType> superInterfaces, List<ExportedType.TypeParameter> typeParameters, List<? extends ExportedDeclaration> members, List<? extends ExportedClass> nestedClasses, IrClass ir) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        Intrinsics.checkNotNullParameter(superInterfaces, "superInterfaces");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(members, "members");
        Intrinsics.checkNotNullParameter(nestedClasses, "nestedClasses");
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.name = name2;
        this.isInterface = z;
        this.isAbstract = z2;
        this.superClasses = superClasses;
        this.superInterfaces = superInterfaces;
        this.typeParameters = typeParameters;
        this.members = members;
        this.nestedClasses = nestedClasses;
        this.ir = ir;
    }
}
