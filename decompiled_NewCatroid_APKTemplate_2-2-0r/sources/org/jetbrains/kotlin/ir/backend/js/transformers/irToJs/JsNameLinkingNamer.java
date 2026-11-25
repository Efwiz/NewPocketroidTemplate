package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrNamerBase;
import org.jetbrains.kotlin.ir.backend.js.utils.MinimizedNameGenerator;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: JsNameLinkingNamer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0018\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020!0 *\u00020\"H\u0002J$\u0010#\u001a\u00020\u0014*\u00020\u001e2\u0006\u0010$\u001a\u00020!2\u000e\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010&H\u0002J\u0014\u0010'\u001a\u00020\u0014*\u00020\u001e2\u0006\u0010(\u001a\u00020!H\u0002J\u001c\u0010)\u001a\u00020\u0014*\u00020\u001e2\u000e\u0010%\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010&H\u0002J\u0016\u0010*\u001a\u00020\u0014*\u00020\u001e2\b\b\u0002\u0010+\u001a\u00020!H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00140\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsNameLinkingNamer;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamerBase;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "minimizedMemberNames", "", "isEsModules", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;ZZ)V", "importedModules", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "getImportedModules", "()Ljava/util/List;", "imports", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getImports", "()Ljava/util/Map;", "nameMap", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getNameMap", "getNameForMemberField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getNameForMemberFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameForStaticDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "fieldData", "", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateImportForDeclarationInFileWithJsModule", "fileJsModule", "jsQualifier", "", "generateImportForDeclarationWithJsModule", "jsModule", "generateRegularQualifiedImport", "getName", "prefix", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsNameLinkingNamer extends IrNamerBase {
    private final JsIrBackendContext context;
    private final List<JsImportedModule> importedModules;
    private final Map<IrDeclaration, JsStatement> imports;
    private final boolean isEsModules;
    private final boolean minimizedMemberNames;
    private final Map<IrDeclaration, JsName> nameMap;

    public JsNameLinkingNamer(JsIrBackendContext context, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.minimizedMemberNames = z;
        this.isEsModules = z2;
        this.nameMap = new LinkedHashMap();
        this.importedModules = new ArrayList();
        this.imports = new LinkedHashMap();
    }

    public final Map<IrDeclaration, JsName> getNameMap() {
        return this.nameMap;
    }

    static /* synthetic */ JsName getName$default(JsNameLinkingNamer jsNameLinkingNamer, IrDeclarationWithName irDeclarationWithName, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "";
        }
        return jsNameLinkingNamer.getName(irDeclarationWithName, str);
    }

    private final JsName getName(IrDeclarationWithName irDeclarationWithName, String str) {
        String strNameIfPropertyAccessor;
        Map<IrDeclaration, JsName> map = this.nameMap;
        JsName jsName = map.get(irDeclarationWithName);
        if (jsName == null) {
            if (((irDeclarationWithName instanceof IrClass ? (IrClass) irDeclarationWithName : null) == null || (strNameIfPropertyAccessor = this.context.getLocalClassNames().get(irDeclarationWithName)) == null) && (strNameIfPropertyAccessor = NameTablesKt.nameIfPropertyAccessor(irDeclarationWithName)) == null) {
                strNameIfPropertyAccessor = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).asString();
                Intrinsics.checkNotNullExpressionValue(strNameIfPropertyAccessor, "asString(...)");
            }
            Intrinsics.checkNotNull(strNameIfPropertyAccessor);
            JsName jsName2 = new JsName(NameTablesKt.sanitizeName$default(str + strNameIfPropertyAccessor, false, 2, null), true);
            map.put(irDeclarationWithName, jsName2);
            jsName = jsName2;
        }
        return jsName;
    }

    public final List<JsImportedModule> getImportedModules() {
        return this.importedModules;
    }

    public final Map<IrDeclaration, JsStatement> getImports() {
        return this.imports;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamerBase, org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticDeclaration(IrDeclarationWithName declaration) {
        String jsQualifier;
        List listSplit$default;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        ArrayList arrayList = null;
        if (IrUtilsKt.isEffectivelyExternal(declaration)) {
            String jsModule = AnnotationUtilsKt.getJsModule(declaration);
            IrDeclarationParent parent = declaration.getParent();
            IrFile irFile = parent instanceof IrFile ? (IrFile) parent : null;
            String jsModule2 = irFile != null ? AnnotationUtilsKt.getJsModule(irFile) : null;
            if (irFile != null && (jsQualifier = AnnotationUtilsKt.getJsQualifier(irFile)) != null && (listSplit$default = StringsKt.split$default((CharSequence) jsQualifier, new char[]{'.'}, false, 0, 6, (Object) null)) != null) {
                List list = listSplit$default;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList2.add(new JsName((String) it.next(), false));
                }
                arrayList = arrayList2;
            }
            if (jsModule != null) {
                return generateImportForDeclarationWithJsModule(declaration, jsModule);
            }
            if (jsModule2 != null) {
                return generateImportForDeclarationInFileWithJsModule(declaration, jsModule2, arrayList);
            }
            return generateRegularQualifiedImport(declaration, arrayList);
        }
        return getName$default(this, declaration, null, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamerBase, org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (function.getDispatchReceiverParameter() == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrSimpleFunction irSimpleFunction = function;
        String strJsFunctionSignature = NameTablesKt.jsFunctionSignature(irSimpleFunction, this.context);
        IrSimpleFunction irSimpleFunction2 = function;
        if (this.context.getKeeper().shouldKeep(irSimpleFunction2)) {
            this.context.getMinimizedNameGenerator().keepName(strJsFunctionSignature);
        }
        if (this.minimizedMemberNames && !MiscKt.hasStableJsName(irSimpleFunction, this.context)) {
            fieldData(IrUtilsKt.getParentAsClass(irSimpleFunction2));
            strJsFunctionSignature = this.context.getMinimizedNameGenerator().nameBySignature(strJsFunctionSignature);
        }
        return IrNamerBase.toJsName$default(this, strJsFunctionSignature, false, 1, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamerBase, org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        if (field.getIsStatic()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        String str = fieldData(IrUtilsKt.getParentAsClass(field)).get(field);
        Intrinsics.checkNotNull(str);
        return new JsName(str, false);
    }

    private final JsName generateImportForDeclarationWithJsModule(IrDeclarationWithName irDeclarationWithName, String str) {
        String strAsString;
        if (AnnotationUtilsKt.isJsNonModule(irDeclarationWithName)) {
            strAsString = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).asString();
        } else {
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irDeclarationWithName);
            Intrinsics.checkNotNull(fqNameWhenAvailable);
            FqName fqNameParent = fqNameWhenAvailable.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            strAsString = fqNameParent.child(AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName)).asString();
        }
        Intrinsics.checkNotNull(strAsString);
        JsName jsName = new JsName(NameTablesKt.sanitizeName$default(strAsString, false, 2, null), true);
        JsNameRef jsNameRefMakeRef = jsName.makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        if (this.isEsModules) {
            this.imports.put(irDeclarationWithName, new JsImport(str, ((irDeclarationWithName instanceof IrClass) && IrUtilsKt.isObject((IrClass) irDeclarationWithName)) ? new JsImport.Target.All(jsNameRefMakeRef) : new JsImport.Target.Default(jsNameRefMakeRef)));
            this.nameMap.put(irDeclarationWithName, jsName);
        } else {
            this.importedModules.add(new JsImportedModule(str, jsName, jsNameRefMakeRef, null, 8, null));
        }
        return jsName;
    }

    private final JsName generateImportForDeclarationInFileWithJsModule(IrDeclarationWithName irDeclarationWithName, String str, List<? extends JsName> list) {
        JsNameRef jsNameRefMakeRef;
        JsName jsName;
        JsCompositeBlock jsCompositeBlock;
        if (this.nameMap.containsKey(irDeclarationWithName)) {
            return getName$default(this, irDeclarationWithName, null, 1, null);
        }
        String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (this.isEsModules) {
            if (list == null || (jsName = (JsName) CollectionsKt.firstOrNull((List) list)) == null) {
                jsName = toJsName(identifier, false);
            }
            JsImport jsImport = new JsImport(str, new JsImport.Element(jsName, null, 2, null));
            Map<IrDeclaration, JsStatement> map = this.imports;
            JsNameRef jsNameRefMakeRef2 = list != null ? JsNameLinkingNamerKt.makeRef(list) : null;
            if (jsNameRefMakeRef2 == null) {
                jsCompositeBlock = jsImport;
            } else {
                jsCompositeBlock = new JsCompositeBlock((List<? extends JsStatement>) CollectionsKt.listOf((Object[]) new JsNode[]{jsImport, JsAstUtilsKt.putIntoVariableWitName(JsAstUtilsKt.jsElementAccess(identifier, jsNameRefMakeRef2), IrNamerBase.toJsName$default(this, identifier, false, 1, null))}));
            }
            map.put(irDeclarationWithName, (JsStatement) jsCompositeBlock);
        } else {
            JsName jsName2 = new JsName(NameTablesKt.sanitizeName$default(Namer.LOCAL_MODULE_PREFIX + str, false, 2, null), true);
            this.importedModules.add(new JsImportedModule(str, jsName2, null, null, 8, null));
            if (list != null) {
                jsNameRefMakeRef = JsNameLinkingNamerKt.makeRef(CollectionsKt.plus((Collection) CollectionsKt.listOf(jsName2), (Iterable) list));
            } else {
                jsNameRefMakeRef = jsName2.makeRef();
            }
            Intrinsics.checkNotNull(jsNameRefMakeRef);
            this.imports.put(irDeclarationWithName, JsAstUtilsKt.putIntoVariableWitName(JsAstUtilsKt.jsElementAccess(identifier, jsNameRefMakeRef), IrNamerBase.toJsName$default(this, identifier, false, 1, null)));
        }
        return getName$default(this, irDeclarationWithName, null, 1, null);
    }

    private final JsName generateRegularQualifiedImport(IrDeclarationWithName irDeclarationWithName, List<? extends JsName> list) {
        String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (list != null) {
            this.imports.put(irDeclarationWithName, JsAstUtilsKt.putIntoVariableWitName(JsAstUtilsKt.jsElementAccess(identifier, JsNameLinkingNamerKt.makeRef(list)), IrNamerBase.toJsName$default(this, identifier, false, 1, null)));
            return getName$default(this, irDeclarationWithName, null, 1, null);
        }
        return toJsName(identifier, false);
    }

    private final Map<IrField, String> fieldData(IrClass irClass) {
        String strSafeName;
        IrPropertySymbol correspondingPropertySymbol;
        WeakHashMap<IrClass, Map<IrField, String>> fieldDataCache = this.context.getFieldDataCache();
        HashMap map = fieldDataCache.get(irClass);
        if (map == null) {
            HashMap map2 = new HashMap();
            List list = DFS.topologicalOrder(CollectionsKt.listOf(irClass), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsNameLinkingNamer$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
                public final Iterable getNeighbors(Object obj) {
                    return JsNameLinkingNamer.fieldData$lambda$10$lambda$5((IrClass) obj);
                }
            });
            HashMap map3 = new HashMap();
            if (this.minimizedMemberNames) {
                Intrinsics.checkNotNull(list);
                Iterator it = CollectionsKt.reversed(list).iterator();
                while (it.hasNext()) {
                    for (IrDeclaration irDeclaration : ((IrClass) it.next()).getDeclarations()) {
                        if (irDeclaration instanceof IrFunction) {
                            IrFunction irFunction = (IrFunction) irDeclaration;
                            if (irFunction.getDispatchReceiverParameter() != null) {
                                IrSimpleFunction irSimpleFunction = irDeclaration instanceof IrSimpleFunction ? (IrSimpleFunction) irDeclaration : null;
                                IrProperty owner = (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null) ? null : correspondingPropertySymbol.getOwner();
                                if ((owner != null && ExportModelGeneratorKt.isExported(owner, this.context)) || (owner != null && IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) owner))) {
                                    MinimizedNameGenerator minimizedNameGenerator = this.context.getMinimizedNameGenerator();
                                    String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(owner).getIdentifier();
                                    Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                                    minimizedNameGenerator.reserveName(identifier);
                                }
                                if (MiscKt.hasStableJsName(irFunction, this.context)) {
                                    this.context.getMinimizedNameGenerator().reserveName(NameTablesKt.jsFunctionSignature(irFunction, this.context));
                                }
                            }
                        }
                        if ((irDeclaration instanceof IrProperty) && ExportModelGeneratorKt.isExported(irDeclaration, this.context)) {
                            MinimizedNameGenerator minimizedNameGenerator2 = this.context.getMinimizedNameGenerator();
                            String identifier2 = AnnotationUtilsKt.getJsNameOrKotlinName((IrDeclarationWithName) irDeclaration).getIdentifier();
                            Intrinsics.checkNotNullExpressionValue(identifier2, "getIdentifier(...)");
                            minimizedNameGenerator2.reserveName(identifier2);
                        }
                    }
                }
            }
            Intrinsics.checkNotNull(list);
            Iterator it2 = CollectionsKt.reversed(list).iterator();
            while (it2.hasNext()) {
                for (IrDeclaration irDeclaration2 : ((IrClass) it2.next()).getDeclarations()) {
                    if (irDeclaration2 instanceof IrField) {
                        IrField irField = (IrField) irDeclaration2;
                        IrPropertySymbol correspondingPropertySymbol2 = irField.getCorrespondingPropertySymbol();
                        IrProperty owner2 = correspondingPropertySymbol2 != null ? correspondingPropertySymbol2.getOwner() : null;
                        boolean z = owner2 != null && owner2.getVisibility().isPublicAPI() && (ExportModelGeneratorKt.isExported(owner2, this.context) || AnnotationUtilsKt.getJsName(owner2) != null) && IrUtilsKt.isSimpleProperty(owner2);
                        if (z) {
                            strSafeName = AnnotationUtilsKt.getJsNameOrKotlinName((IrDeclarationWithName) (owner2 != null ? owner2 : irDeclaration2)).getIdentifier();
                        } else {
                            strSafeName = (!this.minimizedMemberNames || this.context.getKeeper().shouldKeep(irDeclaration2)) ? JsNameLinkingNamerKt.safeName(irField) : this.context.getMinimizedNameGenerator().generateNextName();
                        }
                        Intrinsics.checkNotNull(strSafeName);
                        if (!z) {
                            int iIntValue = ((Number) map2.getOrDefault(strSafeName, 0)).intValue() + 1;
                            map2.put(strSafeName, Integer.valueOf(iIntValue));
                            strSafeName = strSafeName + '_' + iIntValue;
                        }
                        map3.put(irDeclaration2, strSafeName);
                    } else if (irDeclaration2 instanceof IrFunction) {
                        IrFunction irFunction2 = (IrFunction) irDeclaration2;
                        if (irFunction2.getDispatchReceiverParameter() != null) {
                            map2.put(NameTablesKt.jsFunctionSignature(irFunction2, this.context), 1);
                        }
                    }
                }
            }
            map = map3;
            fieldDataCache.put(irClass, map);
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable fieldData$lambda$10$lambda$5(IrClass irClass) {
        List<IrType> superTypes = irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        for (IrType irType : superTypes) {
            if (!(irType instanceof IrSimpleType)) {
                irType = null;
            }
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            IrClassifierSymbol classifier = irSimpleType != null ? irSimpleType.getClassifier() : null;
            if (!(classifier instanceof IrClassSymbol)) {
                classifier = null;
            }
            IrClassSymbol irClassSymbol = (IrClassSymbol) classifier;
            IrClass owner = irClassSymbol != null ? irClassSymbol.getOwner() : null;
            if (owner != null) {
                arrayList.add(owner);
            }
        }
        return arrayList;
    }
}
