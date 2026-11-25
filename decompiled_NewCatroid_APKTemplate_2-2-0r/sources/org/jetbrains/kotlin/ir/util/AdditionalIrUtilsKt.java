package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.PsiIrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassPublicSymbolImpl;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: AdditionalIrUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ð\u0001\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\"\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00040Z2\u0006\u0010[\u001a\u00020-2\f\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00040Z\u001a\f\u0010]\u001a\u0004\u0018\u00010\u0003*\u00020\u0003\u001a\u001a\u0010^\u001a\u0004\u0018\u00010\u0004*\b\u0012\u0004\u0012\u00020\u00040Z2\u0006\u0010[\u001a\u00020-\u001a\n\u0010_\u001a\u00020R*\u00020)\u001a\u0016\u0010`\u001a\u0004\u0018\u00010\u0010*\u00020\u00032\u0006\u0010a\u001a\u00020bH\u0002\u001a\u0014\u0010c\u001a\u0004\u0018\u00010d*\u00020\u00032\u0006\u0010a\u001a\u00020b\u001a\u0014\u0010c\u001a\u0004\u0018\u00010d*\u00020e2\u0006\u0010a\u001a\u00020b\u001a\u0014\u0010f\u001a\u0004\u0018\u00010d*\u00020\u00032\u0006\u0010a\u001a\u00020b\u001a\u0014\u0010f\u001a\u0004\u0018\u00010d*\u00020e2\u0006\u0010a\u001a\u00020b\u001a\u0014\u0010g\u001a\u0004\u0018\u00010d*\u00020\u00032\u0006\u0010a\u001a\u00020b\u001a\u0014\u0010g\u001a\u0004\u0018\u00010d*\u00020e2\u0006\u0010a\u001a\u00020b\u001a\u0018\u0010h\u001a\u000202*\b\u0012\u0004\u0012\u00020\u00040Z2\u0006\u0010[\u001a\u00020-\u001a\u0012\u0010i\u001a\u000202*\u00020\u00132\u0006\u0010[\u001a\u00020-\u001a\u0012\u0010i\u001a\u000202*\u00020j2\u0006\u0010[\u001a\u00020-\u001a\u0012\u0010k\u001a\u000202*\u00020\u00042\u0006\u0010[\u001a\u00020-\u001a\u0014\u0010l\u001a\u000202*\u0004\u0018\u00010m2\u0006\u0010n\u001a\u00020o\u001a\u0012\u0010p\u001a\u000202*\u00020\u000e2\u0006\u0010q\u001a\u00020o\u001a(\u0010r\u001a\u000202\"\b\b\u0000\u0010s*\u00020j*\b\u0012\u0004\u0012\u0002Hs0t2\f\u0010u\u001a\b\u0012\u0004\u0012\u0002Hs0t\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u0018\u0010\u0002\u001a\u00020\u0003*\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\n\u0010\r\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000f\"\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\n\u0010\u0011\"\u0018\u0010\u0012\u001a\u00020\b*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0017\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\"\u0017\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001b\"\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u0017*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\"\u0015\u0010\u001f\u001a\u00020\u0017*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b \u0010\u0019\"\u0015\u0010\u001f\u001a\u00020\u0017*\u00020\u001a8F¢\u0006\u0006\u001a\u0004\b \u0010\u001b\"\u0018\u0010!\u001a\u00020\u0017*\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u001e\"\u0015\u0010#\u001a\u00020\u0003*\u00020$8F¢\u0006\u0006\u001a\u0004\b%\u0010&\"\u0015\u0010'\u001a\u00020(*\u00020)8F¢\u0006\u0006\u001a\u0004\b*\u0010+\"\u0015\u0010,\u001a\u00020-*\u00020.8F¢\u0006\u0006\u001a\u0004\b/\u00100\"\u0015\u00101\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b1\u00103\"\u0015\u00104\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b4\u00103\"\u0015\u00105\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b5\u00103\"\u0015\u00106\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b6\u00103\"\u0015\u00107\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b7\u00103\"\u0015\u00108\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b8\u00103\"\u0015\u00109\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b9\u00103\"\u0015\u0010:\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b:\u00103\"\u0015\u0010;\u001a\u000202*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b;\u0010<\"\u0015\u0010=\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b=\u00103\"\u0015\u0010>\u001a\u000202*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b>\u0010<\"\u0015\u0010?\u001a\u000202*\u00020)8F¢\u0006\u0006\u001a\u0004\b?\u00103\"\u0015\u0010@\u001a\u000202*\u00020A8F¢\u0006\u0006\u001a\u0004\b@\u0010B\"\u0015\u0010C\u001a\u00020-*\u00020.8F¢\u0006\u0006\u001a\u0004\bD\u00100\"\u0015\u0010E\u001a\u00020F*\u00020G8F¢\u0006\u0006\u001a\u0004\bH\u0010I\"\u0015\u0010E\u001a\u00020F*\u00020(8F¢\u0006\u0006\u001a\u0004\bH\u0010J\"\u001e\u0010K\u001a\u00020L*\u00020)8FX\u0087\u0004¢\u0006\f\u0012\u0004\bM\u0010N\u001a\u0004\bO\u0010P\"\u001e\u0010Q\u001a\u00020R*\u00020)8FX\u0087\u0004¢\u0006\f\u0012\u0004\bS\u0010N\u001a\u0004\bT\u0010U\"\u0017\u0010V\u001a\u0004\u0018\u00010-*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bW\u0010X¨\u0006v"}, d2 = {"SYNTHETIC_OFFSET", "", "annotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotationClass", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getCallableId", "(Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;)Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "(Lorg/jetbrains/kotlin/ir/declarations/IrField;)Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/name/CallableId;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Lorg/jetbrains/kotlin/name/CallableId;", "callableIdImpl", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getCallableIdImpl", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;)Lorg/jetbrains/kotlin/name/CallableId;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;)Lorg/jetbrains/kotlin/name/ClassId;", "classIdImpl", "getClassIdImpl", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;)Lorg/jetbrains/kotlin/name/ClassId;", "classIdOrFail", "getClassIdOrFail", "classIdOrFailImpl", "getClassIdOrFailImpl", "constructedClass", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructedClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fileEntry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFileEntry", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/IrFileEntry;", "fqNameForIrSerialization", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getFqNameForIrSerialization", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)Lorg/jetbrains/kotlin/name/FqName;", "isAccessor", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isAnonymousFunction", "isAnonymousObject", "isGetter", "isJvmInlineClassConstructor", "isLocal", "isPropertyAccessor", "isPropertyField", "isReal", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "isSetter", "isSuspend", "isTopLevelDeclaration", "isVararg", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)Z", "kotlinFqName", "getKotlinFqName", "lineStartOffsets", "", "Ljava/io/File;", "getLineStartOffsets", "(Ljava/io/File;)[I", "(Lorg/jetbrains/kotlin/ir/IrFileEntry;)[I", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModule$annotations", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "getModule", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "nameForIrSerialization", "Lorg/jetbrains/kotlin/name/Name;", "getNameForIrSerialization$annotations", "getNameForIrSerialization", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/name/Name;", "packageFqName", "getPackageFqName", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/name/FqName;", "filterOutAnnotations", "", "fqName", "annotations", "companionObject", "findAnnotation", "getNameWithAssert", "getPropertyDeclaration", "name", "", "getPropertyGetter", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getPropertySetter", "getSimpleFunction", "hasAnnotation", "hasEqualFqName", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "isAnnotationWithEqualFqName", "isArrayOrPrimitiveArray", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "builtins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "isInlineArrayConstructor", "builtIns", "overrides", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "other", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AdditionalIrUtilsKt {
    public static final int SYNTHETIC_OFFSET = -2;

    private static final ClassId getClassIdOrFailImpl(IrDeclarationWithName irDeclarationWithName) {
        ClassId classIdImpl = getClassIdImpl(irDeclarationWithName);
        if (classIdImpl != null) {
            return classIdImpl;
        }
        throw new IllegalStateException(("No classId for " + irDeclarationWithName).toString());
    }

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getModule$annotations(IrDeclaration irDeclaration) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "This function is deprecated because it has confusing name and behavior. Please use IrDeclarationWithName.name or IrDeclaration.getNameWithAssert", replaceWith = @ReplaceWith(expression = "(this as? IrDeclarationWithName)?.name", imports = {"org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName"}))
    public static /* synthetic */ void getNameForIrSerialization$annotations(IrDeclaration irDeclaration) {
    }

    public static final Name getNameWithAssert(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrDeclarationWithName) {
            return ((IrDeclarationWithName) irDeclaration).getName();
        }
        throw new IllegalStateException(irDeclaration.toString());
    }

    public static final IrClass getConstructedClass(IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        IrDeclarationParent parent = irConstructor.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        return (IrClass) parent;
    }

    public static final boolean isArrayOrPrimitiveArray(IrClassifierSymbol irClassifierSymbol, IrBuiltIns builtins) {
        Intrinsics.checkNotNullParameter(builtins, "builtins");
        return Intrinsics.areEqual(irClassifierSymbol, builtins.getArrayClass()) || builtins.getPrimitiveArraysToPrimitiveTypes().containsKey(irClassifierSymbol);
    }

    public static final boolean isInlineArrayConstructor(IrFunction irFunction, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        return (irFunction instanceof IrConstructor) && irFunction.getValueParameters().size() == 2 && isArrayOrPrimitiveArray(getConstructedClass((IrConstructor) irFunction).getSymbol(), builtIns);
    }

    public static final FqName getFqNameForIrSerialization(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<this>");
        if (irDeclarationParent instanceof IrPackageFragment) {
            return ((IrPackageFragment) irDeclarationParent).getPackageFqName();
        }
        if (!(irDeclarationParent instanceof IrDeclarationWithName)) {
            throw new IllegalStateException(irDeclarationParent.toString());
        }
        IrDeclarationWithName irDeclarationWithName = (IrDeclarationWithName) irDeclarationParent;
        FqName fqNameChild = getFqNameForIrSerialization(irDeclarationWithName.getParent()).child(irDeclarationWithName.getName());
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return fqNameChild;
    }

    public static final FqName getKotlinFqName(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<this>");
        if (irDeclarationParent instanceof IrPackageFragment) {
            return ((IrPackageFragment) irDeclarationParent).getPackageFqName();
        }
        if (irDeclarationParent instanceof IrClass) {
            if (IrUtilsKt.isFileClass((IrDeclaration) irDeclarationParent)) {
                return getKotlinFqName(((IrClass) irDeclarationParent).getParent());
            }
            IrClass irClass = (IrClass) irDeclarationParent;
            FqName fqNameChild = getKotlinFqName(irClass.getParent()).child(irClass.getName());
            Intrinsics.checkNotNull(fqNameChild);
            return fqNameChild;
        }
        if (!(irDeclarationParent instanceof IrDeclarationWithName)) {
            throw new IllegalStateException(irDeclarationParent.toString());
        }
        IrDeclarationWithName irDeclarationWithName = (IrDeclarationWithName) irDeclarationParent;
        FqName fqNameChild2 = getKotlinFqName(irDeclarationWithName.getParent()).child(irDeclarationWithName.getName());
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        return fqNameChild2;
    }

    public static final ClassId getClassId(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return getClassIdImpl(irClass);
    }

    public static final ClassId getClassId(IrTypeAlias irTypeAlias) {
        Intrinsics.checkNotNullParameter(irTypeAlias, "<this>");
        return getClassIdImpl(irTypeAlias);
    }

    private static final ClassId getClassIdImpl(IrDeclarationWithName irDeclarationWithName) {
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        if (!(parent instanceof IrClass)) {
            if (parent instanceof IrPackageFragment) {
                return ClassId.topLevel(((IrPackageFragment) parent).getPackageFqName().child(irDeclarationWithName.getName()));
            }
            return null;
        }
        ClassId classId = getClassId((IrClass) parent);
        if (classId != null) {
            return classId.createNestedClassId(irDeclarationWithName.getName());
        }
        return null;
    }

    public static final ClassId getClassIdOrFail(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return getClassIdOrFailImpl(irClass);
    }

    public static final ClassId getClassIdOrFail(IrTypeAlias irTypeAlias) {
        Intrinsics.checkNotNullParameter(irTypeAlias, "<this>");
        return getClassIdOrFailImpl(irTypeAlias);
    }

    public static final CallableId getCallableId(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return getCallableIdImpl(irFunction);
    }

    public static final CallableId getCallableId(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        return getCallableIdImpl(irProperty);
    }

    public static final CallableId getCallableId(IrField irField) {
        Intrinsics.checkNotNullParameter(irField, "<this>");
        return getCallableIdImpl(irField);
    }

    public static final CallableId getCallableId(IrEnumEntry irEnumEntry) {
        Intrinsics.checkNotNullParameter(irEnumEntry, "<this>");
        return getCallableIdImpl(irEnumEntry);
    }

    private static final CallableId getCallableIdImpl(IrDeclarationWithName irDeclarationWithName) {
        if (irDeclarationWithName.getSymbol() instanceof IrClassifierSymbol) {
            throw new IllegalStateException(("Classifiers can not have callableId. Got " + irDeclarationWithName).toString());
        }
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        CallableId callableId = null;
        if (parent instanceof IrClass) {
            ClassId classId = getClassId((IrClass) parent);
            if (classId != null) {
                callableId = new CallableId(classId, irDeclarationWithName.getName());
            }
        } else if (parent instanceof IrPackageFragment) {
            callableId = new CallableId(((IrPackageFragment) parent).getPackageFqName(), irDeclarationWithName.getName());
        }
        if (callableId != null) {
            return callableId;
        }
        throw new IllegalStateException((irDeclarationWithName + " has no callableId").toString());
    }

    public static final Name getNameForIrSerialization(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrDeclarationWithName) {
            return ((IrDeclarationWithName) irDeclaration).getName();
        }
        if (irDeclaration instanceof IrConstructor) {
            return SpecialNames.INIT;
        }
        throw new IllegalStateException(irDeclaration.toString());
    }

    public static final boolean isVararg(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        return irValueParameter.getVarargElementType() != null;
    }

    public static final boolean isSuspend(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (irFunction instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction).getIsSuspend();
    }

    public static final boolean isReal(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return ((irFunction instanceof IrSimpleFunction) && ((IrSimpleFunction) irFunction).getIsFakeOverride()) ? false : true;
    }

    public static final <S extends IrSymbol> boolean overrides(IrOverridableDeclaration<S> irOverridableDeclaration, IrOverridableDeclaration<S> other) {
        Intrinsics.checkNotNullParameter(irOverridableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        if (Intrinsics.areEqual(irOverridableDeclaration, other)) {
            return true;
        }
        Iterator<T> it = irOverridableDeclaration.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration<S of org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt.overrides$lambda$1>");
            if (overrides((IrOverridableDeclaration) owner, other)) {
                return true;
            }
        }
        return false;
    }

    private static final IrClass getAnnotationClass(IrConstructorCall irConstructorCall) {
        return getConstructedClass(irConstructorCall.getSymbol().getOwner());
    }

    public static final boolean isAnnotationWithEqualFqName(IrConstructorCall irConstructorCall, FqName fqName) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return hasEqualFqName(getAnnotationClass(irConstructorCall), fqName);
    }

    public static final FqName getPackageFqName(IrClass irClass) {
        FqName fqName;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        IdSignature signature = irClass.getSymbol().getSignature();
        if (signature != null && (fqName = signature.getFqName()) != null) {
            return fqName;
        }
        IrPackageFragment packageFragment = IrUtilsKt.getPackageFragment(irClass.getParent());
        if (packageFragment != null) {
            return packageFragment.getPackageFqName();
        }
        return null;
    }

    public static final boolean hasEqualFqName(IrDeclarationWithName irDeclarationWithName, FqName fqName) {
        boolean zHasEqualFqName;
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (!hasEqualFqName(irDeclarationWithName.getSymbol(), fqName)) {
            if (!Intrinsics.areEqual(irDeclarationWithName.getName(), fqName.shortName())) {
                return false;
            }
            IrDeclarationParent parent = irDeclarationWithName.getParent();
            if (parent instanceof IrPackageFragment) {
                zHasEqualFqName = Intrinsics.areEqual(((IrPackageFragment) parent).getPackageFqName(), fqName.parent());
            } else if (parent instanceof IrDeclarationWithName) {
                FqName fqNameParent = fqName.parent();
                Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
                zHasEqualFqName = hasEqualFqName((IrDeclarationWithName) parent, fqNameParent);
            } else {
                zHasEqualFqName = false;
            }
            if (!zHasEqualFqName) {
                return false;
            }
        }
        return true;
    }

    public static final boolean hasEqualFqName(IrSymbol irSymbol, FqName fqName) {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        if (!(irSymbol instanceof IrClassPublicSymbolImpl)) {
            return false;
        }
        IdSignature signature = ((IrClassPublicSymbolImpl) irSymbol).getSignature();
        IdSignature.CommonSignature commonSignature = signature instanceof IdSignature.CommonSignature ? (IdSignature.CommonSignature) signature : null;
        return commonSignature != null && Intrinsics.areEqual(new FqName(new StringBuilder().append(commonSignature.getPackageFqName()).append('.').append(commonSignature.getDeclarationFqName()).toString()), fqName);
    }

    public static final boolean hasAnnotation(List<? extends IrConstructorCall> list, FqName fqName) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        List<? extends IrConstructorCall> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            if (hasEqualFqName(getAnnotationClass((IrConstructorCall) it.next()), fqName)) {
                return true;
            }
        }
        return false;
    }

    public static final IrConstructorCall findAnnotation(List<? extends IrConstructorCall> list, FqName fqName) {
        Object next;
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (hasEqualFqName(getAnnotationClass((IrConstructorCall) next), fqName)) {
                return (IrConstructorCall) next;
            }
        }
        next = null;
        return (IrConstructorCall) next;
    }

    public static final IrFileEntry getFileEntry(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        if (parent instanceof IrFile) {
            return ((IrFile) parent).getFileEntry();
        }
        if (parent instanceof IrPackageFragment) {
            throw new NotImplementedError("An operation is not implemented: Unknown file");
        }
        if (parent instanceof IrDeclaration) {
            return getFileEntry((IrDeclaration) parent);
        }
        throw new NotImplementedError("An operation is not implemented: Unexpected declaration parent");
    }

    public static final IrClass companionObject(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : irClass.getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj3;
            if ((irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getIsCompanion()) {
                if (z) {
                    return (IrClass) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        return (IrClass) obj;
    }

    public static final boolean isGetter(IrDeclaration irDeclaration) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol();
            if (Intrinsics.areEqual(irDeclaration, (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? null : owner.getGetter())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isSetter(IrDeclaration irDeclaration) {
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrSimpleFunction) {
            IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol();
            if (Intrinsics.areEqual(irDeclaration, (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? null : owner.getSetter())) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isAccessor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return isGetter(irDeclaration) || isSetter(irDeclaration);
    }

    public static final boolean isPropertyAccessor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrSimpleFunction) && ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol() != null;
    }

    public static final boolean isPropertyField(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrField) && ((IrField) irDeclaration).getCorrespondingPropertySymbol() != null;
    }

    public static final boolean isJvmInlineClassConstructor(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getName().asString(), "constructor-impl");
    }

    public static final boolean isTopLevelDeclaration(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return ((irDeclaration.getParent() instanceof IrDeclaration) || isPropertyAccessor(irDeclaration) || isPropertyField(irDeclaration)) ? false : true;
    }

    public static final boolean isAnonymousObject(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrClass) && Intrinsics.areEqual(((IrClass) irDeclaration).getName(), SpecialNames.NO_NAME_PROVIDED);
    }

    public static final boolean isAnonymousFunction(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getName(), SpecialNames.NO_NAME_PROVIDED);
    }

    public static final boolean isLocal(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrElement parent = irDeclaration;
        while (true) {
            IrElement irElement = parent;
            if (irElement instanceof IrPackageFragment) {
                return false;
            }
            if (!(irElement instanceof IrDeclaration)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if ((irElement instanceof IrDeclarationWithVisibility) && Intrinsics.areEqual(((IrDeclarationWithVisibility) irElement).getVisibility(), DescriptorVisibilities.LOCAL)) {
                return true;
            }
            IrDeclaration irDeclaration2 = (IrDeclaration) irElement;
            if (isAnonymousObject(irDeclaration2) || (irElement instanceof IrScript) || ((irElement instanceof IrClass) && Intrinsics.areEqual(irDeclaration2.getOrigin(), IrDeclarationOrigin.SCRIPT_CLASS.INSTANCE))) {
                return true;
            }
            parent = irDeclaration2.getParent();
        }
    }

    public static final ModuleDescriptor getModule(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return DescriptorUtilsKt.getModule(irDeclaration.getDescriptor());
    }

    public static final int[] getLineStartOffsets(File file) {
        Intrinsics.checkNotNullParameter(file, "<this>");
        final int length = System.lineSeparator().length();
        final ArrayList arrayList = new ArrayList();
        final Ref.IntRef intRef = new Ref.IntRef();
        FilesKt.forEachLine$default(file, null, new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt$lineStartOffsets$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String line) {
                Intrinsics.checkNotNullParameter(line, "line");
                arrayList.add(Integer.valueOf(intRef.element));
                intRef.element += line.length() + length;
            }
        }, 1, null);
        arrayList.add(Integer.valueOf(intRef.element));
        return CollectionsKt.toIntArray(arrayList);
    }

    public static final int[] getLineStartOffsets(IrFileEntry irFileEntry) {
        Intrinsics.checkNotNullParameter(irFileEntry, "<this>");
        if (irFileEntry instanceof PsiIrFileEntry) {
            return ((PsiIrFileEntry) irFileEntry).getLineOffsets();
        }
        File file = new File(irFileEntry.getName());
        return (file.exists() && file.isFile()) ? getLineStartOffsets(file) : new int[0];
    }

    private static final IrProperty getPropertyDeclaration(IrClass irClass, String str) {
        SmartList smartListEmptyList;
        List<IrDeclaration> declarations = irClass.getDeclarations();
        if (declarations.isEmpty()) {
            smartListEmptyList = CollectionsKt.emptyList();
        } else {
            SmartList smartList = new SmartList();
            for (Object obj : declarations) {
                if ((obj instanceof IrProperty) && Intrinsics.areEqual(((IrProperty) obj).getName().asString(), str)) {
                    smartList.add(obj);
                }
            }
            smartListEmptyList = smartList;
        }
        if (smartListEmptyList.size() > 1) {
            throw new IllegalStateException(("More than one property with name " + str + " in class " + IrUtilsKt.getFqNameWhenAvailable(irClass) + ":\n" + CollectionsKt.joinToString$default(smartListEmptyList, "\n", null, null, 0, null, AnonymousClass1.INSTANCE, 30, null)).toString());
        }
        return (IrProperty) CollectionsKt.firstOrNull(smartListEmptyList);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt$getPropertyDeclaration$1, reason: invalid class name */
    /* JADX INFO: compiled from: AdditionalIrUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrProperty, String> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getPropertyDeclaration$render(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Ljava/lang/String;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "render";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final String invoke(IrProperty p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return RenderIrElementKt.render$default(p0, (DumpIrTreeOptions) null, 1, (Object) null);
        }
    }

    public static final IrSimpleFunctionSymbol getSimpleFunction(IrClass irClass, String name2) {
        Object next;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = irClass.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if ((irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getName().asString(), name2)) {
                break;
            }
        }
        if (!(next instanceof IrSimpleFunction)) {
            next = null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) next;
        if (irSimpleFunction != null) {
            return irSimpleFunction.getSymbol();
        }
        return null;
    }

    public static final IrSimpleFunctionSymbol getPropertyGetter(IrClass irClass, String name2) {
        IrSimpleFunctionSymbol simpleFunction;
        IrSimpleFunction owner;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner2;
        Name name3;
        IrSimpleFunction getter;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrProperty propertyDeclaration = getPropertyDeclaration(irClass, name2);
        if (propertyDeclaration == null || (getter = propertyDeclaration.getGetter()) == null || (simpleFunction = getter.getSymbol()) == null) {
            simpleFunction = getSimpleFunction(irClass, "<get-" + name2 + '>');
            Intrinsics.areEqual((simpleFunction == null || (owner = simpleFunction.getOwner()) == null || (correspondingPropertySymbol = owner.getCorrespondingPropertySymbol()) == null || (owner2 = correspondingPropertySymbol.getOwner()) == null || (name3 = owner2.getName()) == null) ? null : name3.asString(), name2);
        }
        return simpleFunction;
    }

    public static final IrSimpleFunctionSymbol getPropertySetter(IrClass irClass, String name2) {
        IrSimpleFunctionSymbol simpleFunction;
        IrSimpleFunction owner;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner2;
        Name name3;
        IrSimpleFunction setter;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrProperty propertyDeclaration = getPropertyDeclaration(irClass, name2);
        if (propertyDeclaration == null || (setter = propertyDeclaration.getSetter()) == null || (simpleFunction = setter.getSymbol()) == null) {
            simpleFunction = getSimpleFunction(irClass, "<set-" + name2 + '>');
            Intrinsics.areEqual((simpleFunction == null || (owner = simpleFunction.getOwner()) == null || (correspondingPropertySymbol = owner.getCorrespondingPropertySymbol()) == null || (owner2 = correspondingPropertySymbol.getOwner()) == null || (name3 = owner2.getName()) == null) ? null : name3.asString(), name2);
        }
        return simpleFunction;
    }

    public static final IrSimpleFunctionSymbol getSimpleFunction(IrClassSymbol irClassSymbol, String name2) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return getSimpleFunction(irClassSymbol.getOwner(), name2);
    }

    public static final IrSimpleFunctionSymbol getPropertyGetter(IrClassSymbol irClassSymbol, String name2) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return getPropertyGetter(irClassSymbol.getOwner(), name2);
    }

    public static final IrSimpleFunctionSymbol getPropertySetter(IrClassSymbol irClassSymbol, String name2) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return getPropertySetter(irClassSymbol.getOwner(), name2);
    }

    public static final List<IrConstructorCall> filterOutAnnotations(FqName fqName, List<? extends IrConstructorCall> annotations) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        ArrayList arrayList = new ArrayList();
        for (Object obj : annotations) {
            if (!hasEqualFqName(getAnnotationClass((IrConstructorCall) obj), fqName)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }
}
