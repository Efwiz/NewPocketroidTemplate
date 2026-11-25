package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;

/* JADX INFO: compiled from: MoveBodilessDeclarationsToSeparatePlace.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u000e\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013\u001a\u0016\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0006\u001a\f\u0010\u0016\u001a\u00020\u0006*\u00020\bH\u0002\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"BODILESS_BUILTIN_CLASSES", "", "Lorg/jetbrains/kotlin/name/FqName;", "JsIntrinsicFqName", "JsPackage", "isBuiltInClass", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isIntrinsic", "isStdLibClass", "moveBodilessDeclarationsToSeparatePlace", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "validateIsExternal", "packageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "validateNestedExternalDeclarations", "isExternalTopLevel", "isPlacedInsideInternalPackage", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MoveBodilessDeclarationsToSeparatePlaceKt {
    private static final Set<FqName> BODILESS_BUILTIN_CLASSES;
    private static final FqName JsIntrinsicFqName;
    private static final FqName JsPackage;

    static {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"kotlin.String", "kotlin.Nothing", "kotlin.Array", "kotlin.Any", "kotlin.ByteArray", "kotlin.CharArray", "kotlin.ShortArray", "kotlin.IntArray", "kotlin.LongArray", "kotlin.FloatArray", "kotlin.DoubleArray", "kotlin.BooleanArray", "kotlin.Boolean", "kotlin.Byte", "kotlin.Short", "kotlin.Int", "kotlin.Float", "kotlin.Double", "kotlin.Function"});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(new FqName((String) it.next()));
        }
        BODILESS_BUILTIN_CLASSES = CollectionsKt.toSet(arrayList);
        JsPackage = new FqName("kotlin.js");
        JsIntrinsicFqName = new FqName("kotlin.js.JsIntrinsic");
    }

    public static final boolean isBuiltInClass(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (declaration instanceof IrClass) && CollectionsKt.contains(BODILESS_BUILTIN_CLASSES, IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) declaration));
    }

    public static final boolean isStdLibClass(IrDeclaration declaration) {
        FqName fqNameWhenAvailable;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (declaration instanceof IrClass) && ((fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) declaration)) == null || FqNamesUtilKt.isChildOf(fqNameWhenAvailable, JsPackage));
    }

    private static final boolean isPlacedInsideInternalPackage(IrDeclaration irDeclaration) {
        IrDeclarationParent parent = irDeclaration.getParent();
        IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
        return Intrinsics.areEqual(irPackageFragment != null ? irPackageFragment.getPackageFqName() : null, JsPackage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isIntrinsic(IrDeclaration irDeclaration) {
        if ((irDeclaration instanceof IrSimpleFunction) && isPlacedInsideInternalPackage(irDeclaration)) {
            List<IrConstructorCall> annotations = irDeclaration.getAnnotations();
            if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(IrUtilsKt.getFqNameWhenAvailable(AdditionalIrUtilsKt.getConstructedClass(((IrConstructorCall) it.next()).getSymbol().getOwner())), JsIntrinsicFqName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final void moveBodilessDeclarationsToSeparatePlace(JsIrBackendContext context, IrModuleFragment moduleFragment) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(moduleFragment, "moduleFragment");
        MoveBodilessDeclarationsToSeparatePlaceLowering moveBodilessDeclarationsToSeparatePlaceLowering = new MoveBodilessDeclarationsToSeparatePlaceLowering(context);
        for (IrFile irFile : moduleFragment.getFiles()) {
            validateIsExternal(irFile);
            moveBodilessDeclarationsToSeparatePlaceLowering.lower(irFile);
        }
    }

    public static final void validateIsExternal(IrPackageFragment packageFragment) {
        Intrinsics.checkNotNullParameter(packageFragment, "packageFragment");
        for (IrDeclaration irDeclaration : packageFragment.getDeclarations()) {
            IrPossiblyExternalDeclaration irPossiblyExternalDeclaration = irDeclaration instanceof IrPossiblyExternalDeclaration ? (IrPossiblyExternalDeclaration) irDeclaration : null;
            validateNestedExternalDeclarations(irDeclaration, irPossiblyExternalDeclaration != null ? irPossiblyExternalDeclaration.getIsExternal() : false);
        }
    }

    private static final void validateNestedExternalDeclarations$checkExternal(IrPossiblyExternalDeclaration irPossiblyExternalDeclaration, boolean z, IrDeclaration irDeclaration) {
        if (irPossiblyExternalDeclaration.getIsExternal() == z) {
            return;
        }
        CompilationExceptionKt.compilationException("isExternal validation failed for declaration", irDeclaration);
        throw null;
    }

    public static final void validateNestedExternalDeclarations(IrDeclaration declaration, boolean z) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrPossiblyExternalDeclaration) {
            validateNestedExternalDeclarations$checkExternal((IrPossiblyExternalDeclaration) declaration, z, declaration);
        }
        if (declaration instanceof IrProperty) {
            IrProperty irProperty = (IrProperty) declaration;
            IrSimpleFunction getter = irProperty.getGetter();
            if (getter != null) {
                validateNestedExternalDeclarations$checkExternal(getter, z, declaration);
            }
            IrSimpleFunction setter = irProperty.getSetter();
            if (setter != null) {
                validateNestedExternalDeclarations$checkExternal(setter, z, declaration);
            }
            IrField backingField = irProperty.getBackingField();
            if (backingField != null) {
                validateNestedExternalDeclarations$checkExternal(backingField, z, declaration);
            }
        }
        if (declaration instanceof IrClass) {
            Iterator<T> it = ((IrClass) declaration).getDeclarations().iterator();
            while (it.hasNext()) {
                validateNestedExternalDeclarations((IrDeclaration) it.next(), z);
            }
        }
    }
}
