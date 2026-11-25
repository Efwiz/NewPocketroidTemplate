package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.PackagePartRegistry;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fileClasses.JvmFileClassInfo;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.inline.InlineOnlyKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FileClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FileClassLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "createFileClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileClassMembers", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "lower", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FileClassLowering implements FileLoweringPass {
    private final JvmBackendContext context;

    public FileClassLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (IrDeclaration irDeclaration : irFile.getDeclarations()) {
            if (!(irDeclaration instanceof IrScript)) {
                if (irDeclaration instanceof IrClass) {
                    arrayList.add(irDeclaration);
                } else {
                    arrayList2.add(irDeclaration);
                }
            }
        }
        if (arrayList2.isEmpty()) {
            MetadataSource metadata = irFile.getMetadata();
            DescriptorMetadataSource.File file = metadata instanceof DescriptorMetadataSource.File ? (DescriptorMetadataSource.File) metadata : null;
            List<DeclarationDescriptor> descriptors = file != null ? file.getDescriptors() : null;
            if (descriptors == null || descriptors.isEmpty()) {
                return;
            }
        }
        arrayList.add(createFileClass(irFile, arrayList2));
        irFile.getDeclarations().clear();
        irFile.getDeclarations().addAll(arrayList);
    }

    private final IrClass createFileClass(IrFile irFile, List<? extends IrDeclaration> fileClassMembers) {
        IrDeclarationOrigin.FILE_CLASS file_class;
        DescriptorVisibility descriptorVisibility;
        ArrayList annotations;
        IrFileEntry fileEntry = irFile.getFileEntry();
        JvmFileClassInfo fileClassInfo = FileClassLoweringKt.getFileClassInfo(irFile);
        boolean withJvmMultifileClass = fileClassInfo.getWithJvmMultifileClass();
        boolean z = false;
        if (this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.PackagePrivateFileClassesWithAllPrivateMembers)) {
            List<? extends IrDeclaration> list = fileClassMembers;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = true;
                        break;
                    }
                    IrDeclaration irDeclaration = (IrDeclaration) it.next();
                    boolean z2 = (irDeclaration instanceof IrDeclarationWithVisibility) && DescriptorVisibilities.isPrivate(((IrDeclarationWithVisibility) irDeclaration).getVisibility());
                    boolean zHasAnnotation = IrUtilsKt.hasAnnotation(irDeclaration, InlineOnlyKt.getINLINE_ONLY_ANNOTATION_FQ_NAME());
                    if (!z2 && !zHasAnnotation) {
                        break;
                    }
                }
            } else {
                z = true;
                break;
            }
        }
        if (!withJvmMultifileClass || ((Boolean) this.context.getState().getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getInheritMultifileParts())).booleanValue()) {
            file_class = IrDeclarationOrigin.FILE_CLASS.INSTANCE;
        } else {
            file_class = IrDeclarationOrigin.SYNTHETIC_FILE_CLASS.INSTANCE;
        }
        int maxOffset = fileEntry.getMaxOffset();
        IrDeclarationOrigin irDeclarationOrigin = file_class;
        IrClassSymbolImpl irClassSymbolImpl = new IrClassSymbolImpl(null, 1, null);
        Name nameShortName = fileClassInfo.getFileClassFqName().shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        ClassKind classKind = ClassKind.CLASS;
        if (withJvmMultifileClass || z) {
            descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        } else {
            descriptorVisibility = DescriptorVisibilities.PUBLIC;
        }
        Intrinsics.checkNotNull(descriptorVisibility);
        IrClassImpl irClassImpl = new IrClassImpl(0, maxOffset, irDeclarationOrigin, irClassSymbolImpl, nameShortName, classKind, descriptorVisibility, Modality.FINAL, false, false, false, false, false, false, false, null, null, 130816, null);
        irClassImpl.setSuperTypes(CollectionsKt.listOf(this.context.getIrBuiltIns().getAnyType()));
        irClassImpl.setParent(irFile);
        irClassImpl.getDeclarations().addAll(fileClassMembers);
        IrClassImpl irClassImpl2 = irClassImpl;
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassImpl2);
        for (IrDeclaration irDeclaration2 : fileClassMembers) {
            IrClassImpl irClassImpl3 = irClassImpl;
            irDeclaration2.setParent(irClassImpl3);
            if (irDeclaration2 instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration2;
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    getter.setParent(irClassImpl3);
                }
                IrSimpleFunction setter = irProperty.getSetter();
                if (setter != null) {
                    setter.setParent(irClassImpl3);
                }
                IrField backingField = irProperty.getBackingField();
                if (backingField != null) {
                    backingField.setParent(irClassImpl3);
                }
            }
        }
        if (withJvmMultifileClass) {
            List<IrConstructorCall> annotations2 = irFile.getAnnotations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : annotations2) {
                if (!AdditionalIrUtilsKt.hasEqualFqName(IrUtilsKt.getParentAsClass(((IrConstructorCall) obj).getSymbol().getOwner()), JvmFileClassUtil.INSTANCE.getJVM_NAME())) {
                    arrayList.add(obj);
                }
            }
            annotations = arrayList;
        } else {
            annotations = irFile.getAnnotations();
        }
        irClassImpl.setAnnotations(annotations);
        irClassImpl.setMetadata(irFile.getMetadata());
        Type typeAsmTypeByFqNameWithoutInnerClasses = AsmUtil.asmTypeByFqNameWithoutInnerClasses(fileClassInfo.getFileClassFqName());
        Intrinsics.checkNotNullExpressionValue(typeAsmTypeByFqNameWithoutInnerClasses, "asmTypeByFqNameWithoutInnerClasses(...)");
        Type typeAsmTypeByFqNameWithoutInnerClasses2 = withJvmMultifileClass ? AsmUtil.asmTypeByFqNameWithoutInnerClasses(fileClassInfo.getFacadeClassFqName()) : null;
        PackagePartRegistry packagePartRegistry = this.context.getState().getFactory().getPackagePartRegistry();
        FqName packageFqName = irFile.getPackageFqName();
        String internalName = typeAsmTypeByFqNameWithoutInnerClasses.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        packagePartRegistry.addPart(packageFqName, internalName, typeAsmTypeByFqNameWithoutInnerClasses2 != null ? typeAsmTypeByFqNameWithoutInnerClasses2.getInternalName() : null);
        if (!Intrinsics.areEqual(fileClassInfo.getFileClassFqName(), IrUtilsKt.getFqNameWhenAvailable(irClassImpl))) {
            Map<IrClass, JvmClassName> classNameOverride = this.context.getClassNameOverride();
            JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(typeAsmTypeByFqNameWithoutInnerClasses.getInternalName());
            Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
            classNameOverride.put(irClassImpl, jvmClassNameByInternalName);
        }
        if (typeAsmTypeByFqNameWithoutInnerClasses2 != null) {
            JvmClassName jvmClassNameByInternalName2 = JvmClassName.byInternalName(typeAsmTypeByFqNameWithoutInnerClasses2.getInternalName());
            Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName2, "byInternalName(...)");
            Map<JvmClassName, List<IrClass>> multifileFacadesToAdd = this.context.getMultifileFacadesToAdd();
            ArrayList arrayList2 = multifileFacadesToAdd.get(jvmClassNameByInternalName2);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                multifileFacadesToAdd.put(jvmClassNameByInternalName2, arrayList2);
            }
            arrayList2.add(irClassImpl);
        }
        return irClassImpl2;
    }
}
