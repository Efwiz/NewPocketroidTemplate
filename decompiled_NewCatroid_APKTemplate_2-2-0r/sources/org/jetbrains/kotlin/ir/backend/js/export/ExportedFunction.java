package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f\u0012\b\b\u0002\u0010\u000e\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u0007HÆ\u0003J\t\u0010!\u001a\u00020\fHÆ\u0003J\t\u0010\"\u001a\u00020\fHÆ\u0003J\t\u0010#\u001a\u00020\fHÆ\u0003J\t\u0010$\u001a\u00020\fHÆ\u0003J\t\u0010%\u001a\u00020\u0011HÆ\u0003Jo\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\b\b\u0002\u0010\u000f\u001a\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011HÆ\u0001J\u0013\u0010'\u001a\u00020\f2\b\u0010(\u001a\u0004\u0018\u00010)HÖ\u0003J\t\u0010*\u001a\u00020+HÖ\u0001J\t\u0010,\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000e\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0015R\u0011\u0010\r\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "name", "", "returnType", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "parameters", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "typeParameters", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeParameter;", "isMember", "", "isStatic", "isAbstract", "isProtected", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Ljava/util/List;Ljava/util/List;ZZZZLorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "getIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "()Z", "getName", "()Ljava/lang/String;", "getParameters", "()Ljava/util/List;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getTypeParameters", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedFunction extends ExportedDeclaration {
    private final IrSimpleFunction ir;
    private final boolean isAbstract;
    private final boolean isMember;
    private final boolean isProtected;
    private final boolean isStatic;
    private final String name;
    private final List<ExportedParameter> parameters;
    private final ExportedType returnType;
    private final List<ExportedType.TypeParameter> typeParameters;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ExportedType getReturnType() {
        return this.returnType;
    }

    public final List<ExportedParameter> component3() {
        return this.parameters;
    }

    public final List<ExportedType.TypeParameter> component4() {
        return this.typeParameters;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsMember() {
        return this.isMember;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final boolean getIsStatic() {
        return this.isStatic;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getIsAbstract() {
        return this.isAbstract;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final boolean getIsProtected() {
        return this.isProtected;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final IrSimpleFunction getIr() {
        return this.ir;
    }

    public final ExportedFunction copy(String name2, ExportedType returnType, List<ExportedParameter> parameters, List<ExportedType.TypeParameter> typeParameters, boolean isMember, boolean isStatic, boolean isAbstract, boolean isProtected, IrSimpleFunction ir) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(ir, "ir");
        return new ExportedFunction(name2, returnType, parameters, typeParameters, isMember, isStatic, isAbstract, isProtected, ir);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedFunction)) {
            return false;
        }
        ExportedFunction exportedFunction = (ExportedFunction) other;
        return Intrinsics.areEqual(this.name, exportedFunction.name) && Intrinsics.areEqual(this.returnType, exportedFunction.returnType) && Intrinsics.areEqual(this.parameters, exportedFunction.parameters) && Intrinsics.areEqual(this.typeParameters, exportedFunction.typeParameters) && this.isMember == exportedFunction.isMember && this.isStatic == exportedFunction.isStatic && this.isAbstract == exportedFunction.isAbstract && this.isProtected == exportedFunction.isProtected && Intrinsics.areEqual(this.ir, exportedFunction.ir);
    }

    public int hashCode() {
        return (((((((((((((((this.name.hashCode() * 31) + this.returnType.hashCode()) * 31) + this.parameters.hashCode()) * 31) + this.typeParameters.hashCode()) * 31) + Boolean.hashCode(this.isMember)) * 31) + Boolean.hashCode(this.isStatic)) * 31) + Boolean.hashCode(this.isAbstract)) * 31) + Boolean.hashCode(this.isProtected)) * 31) + this.ir.hashCode();
    }

    public String toString() {
        return "ExportedFunction(name=" + this.name + ", returnType=" + this.returnType + ", parameters=" + this.parameters + ", typeParameters=" + this.typeParameters + ", isMember=" + this.isMember + ", isStatic=" + this.isStatic + ", isAbstract=" + this.isAbstract + ", isProtected=" + this.isProtected + ", ir=" + this.ir + ')';
    }

    public final String getName() {
        return this.name;
    }

    public final ExportedType getReturnType() {
        return this.returnType;
    }

    public final List<ExportedParameter> getParameters() {
        return this.parameters;
    }

    public /* synthetic */ ExportedFunction(String str, ExportedType exportedType, List list, List list2, boolean z, boolean z2, boolean z3, boolean z4, IrSimpleFunction irSimpleFunction, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, exportedType, list, (i & 8) != 0 ? CollectionsKt.emptyList() : list2, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2, (i & 64) != 0 ? false : z3, z4, irSimpleFunction);
    }

    public final List<ExportedType.TypeParameter> getTypeParameters() {
        return this.typeParameters;
    }

    public final boolean isMember() {
        return this.isMember;
    }

    public final boolean isStatic() {
        return this.isStatic;
    }

    public final boolean isAbstract() {
        return this.isAbstract;
    }

    public final boolean isProtected() {
        return this.isProtected;
    }

    public final IrSimpleFunction getIr() {
        return this.ir;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportedFunction(String name2, ExportedType returnType, List<ExportedParameter> parameters, List<ExportedType.TypeParameter> typeParameters, boolean z, boolean z2, boolean z3, boolean z4, IrSimpleFunction ir) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(ir, "ir");
        this.name = name2;
        this.returnType = returnType;
        this.parameters = parameters;
        this.typeParameters = typeParameters;
        this.isMember = z;
        this.isStatic = z2;
        this.isAbstract = z3;
        this.isProtected = z4;
        this.ir = ir;
    }
}
