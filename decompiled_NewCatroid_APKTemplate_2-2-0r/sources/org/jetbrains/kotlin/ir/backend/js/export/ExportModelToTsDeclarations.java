package org.jetbrains.kotlin.ir.backend.js.export;

import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsAnnotations;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: ExportModelToTsDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u0002J$\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J)\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\u0007H\u0002J\u0010\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0007H\u0002J\f\u0010\u001b\u001a\u00020\u0007*\u00020\u001cH\u0002J\f\u0010\u001d\u001a\u00020\u001e*\u00020\u001fH\u0002J\u001a\u0010 \u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020!0\u00112\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\f\u0010 \u001a\u00020\u0007*\u00020\"H\u0002J\u0014\u0010 \u001a\u00020\u0007*\u00020#2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u0014\u0010 \u001a\u00020\u0007*\u00020$2\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001c\u0010 \u001a\u00020\u0007*\u00020%2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u0002J\u001c\u0010 \u001a\u00020\u0007*\u00020&2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u0002J&\u0010 \u001a\u00020\u0007*\u00020'2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010(\u001a\u00020\u001eH\u0002J&\u0010 \u001a\u00020\u0007*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010(\u001a\u00020\u001eH\u0002J\u001c\u0010 \u001a\u00020\u0007*\u00020)2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u0007H\u0002J\f\u0010*\u001a\u00020\u001e*\u00020)H\u0002J\u001a\u0010+\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020,0\u00112\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\"\u0010-\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020,0\u00112\u0006\u0010.\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\f\u0010/\u001a\u00020\u0005*\u00020\u001fH\u0002J\u001a\u00100\u001a\u00020\u0007*\b\u0012\u0004\u0012\u000202012\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001a\u00100\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00140\u00112\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001a\u00100\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00140\u00112\u0006\u0010\u000f\u001a\u00020\bH\u0002J\u0014\u00100\u001a\u00020\u0007*\u0002022\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J(\u00100\u001a\u00020\u0007*\u00020\u00142\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\b\b\u0002\u0010(\u001a\u00020\u001eH\u0002J\u001c\u00100\u001a\u00020\u0007*\u00020!2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u00103\u001a\u00020\u001eH\u0002J\u001e\u00100\u001a\u00020\u0007*\u00020,2\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u00104\u001a\u00020\u001eH\u0002J\f\u00105\u001a\u00020)*\u00020\u001fH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportModelToTsDeclarations;", "", "()V", "objectsSyntheticProperties", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedProperty;", OutputKeys.INDENT, "", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getIndent", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;)Ljava/lang/String;", "generateObjectsNamespaceIfNeeded", "prefix", "generateTypeScript", "name", "moduleKind", "declarations", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "generateTypeScriptFragment", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "generateTypeScriptFragment-2EUUaHo", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;)Ljava/lang/String;", "tsDeprecated", CrashHianalyticsData.MESSAGE, "tsIgnore", "reason", "asNestedClassAccess", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "couldBeProperty", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedClass;", "generateTypeScriptString", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ErrorDeclaration;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructor;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedNamespace;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedObject;", "esModules", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedRegularClass;", "hasSuperClassWithPrivateConstructor", "toExtendsClause", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "toImplementsClause", "superInterfacesKeyword", "toReadonlyProperty", "toTypeScript", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedAttribute;", "couldBeOptional", "isInCommentContext", "withProtectedConstructors", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExportModelToTsDeclarations {
    private final List<ExportedProperty> objectsSyntheticProperties = new ArrayList();

    /* JADX INFO: compiled from: ExportModelToTsDeclarations.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ModuleKind.values().length];
            try {
                iArr[ModuleKind.PLAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ModuleKind.AMD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ModuleKind.COMMON_JS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ModuleKind.ES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ModuleKind.UMD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String getIndent(ModuleKind moduleKind) {
        return moduleKind == ModuleKind.PLAIN ? "    " : "";
    }

    public final String generateTypeScript(String name2, ModuleKind moduleKind, List<TypeScriptFragment> declarations) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        String str = (StringsKt.prependIndent("type Nullable<T> = T | null | undefined", getIndent(moduleKind)) + '\n') + ExportModelToTsDeclarationsKt.joinTypeScriptFragments(declarations);
        String strSanitizeName = NameTablesKt.sanitizeName(name2, false);
        int i = WhenMappings.$EnumSwitchMapping$0[moduleKind.ordinal()];
        if (i == 1) {
            return "declare namespace " + strSanitizeName + " {\n" + str + "\n}\n";
        }
        if (i == 2 || i == 3 || i == 4) {
            return str;
        }
        if (i == 5) {
            return str + "\nexport as namespace " + strSanitizeName + ';';
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: renamed from: generateTypeScriptFragment-2EUUaHo, reason: not valid java name */
    public final String m7087generateTypeScriptFragment2EUUaHo(ModuleKind moduleKind, List<? extends ExportedDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        return TypeScriptFragment.m7090constructorimpl(toTypeScript(declarations, moduleKind));
    }

    private final String toTypeScript(List<? extends ExportedDeclaration> list, final ModuleKind moduleKind) {
        return CollectionsKt.joinToString$default(list, "\n", null, null, 0, null, new Function1<ExportedDeclaration, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ExportModelToTsDeclarations exportModelToTsDeclarations = ExportModelToTsDeclarations.this;
                return exportModelToTsDeclarations.toTypeScript(it, exportModelToTsDeclarations.getIndent(moduleKind), moduleKind == ModuleKind.PLAIN ? "" : "export declare ", moduleKind == ModuleKind.ES);
            }
        }, 30, null) + generateObjectsNamespaceIfNeeded(getIndent(moduleKind), moduleKind == ModuleKind.PLAIN ? "" : "declare ");
    }

    private final String generateObjectsNamespaceIfNeeded(String indent, String prefix) {
        if (this.objectsSyntheticProperties.isEmpty()) {
            return "";
        }
        return "\n" + toTypeScript$default(this, new ExportedNamespace("_objects_", this.objectsSyntheticProperties), indent, prefix, false, 4, null);
    }

    private final String toTypeScript(List<? extends ExportedDeclaration> list, final String str) {
        return CollectionsKt.joinToString$default(list, "", null, null, 0, null, new Function1<ExportedDeclaration, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ExportModelToTsDeclarations.toTypeScript$default(ExportModelToTsDeclarations.this, it, str, null, false, 6, null) + '\n';
            }
        }, 30, null);
    }

    static /* synthetic */ String toTypeScript$default(ExportModelToTsDeclarations exportModelToTsDeclarations, ExportedDeclaration exportedDeclaration, String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = "";
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return exportModelToTsDeclarations.toTypeScript(exportedDeclaration, str, str2, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toTypeScript(ExportedDeclaration exportedDeclaration, String str, String str2, boolean z) {
        String strGenerateTypeScriptString;
        StringBuilder sbAppend = new StringBuilder().append(toTypeScript((Iterable<? extends ExportedAttribute>) exportedDeclaration.getAttributes(), str)).append(str);
        if (exportedDeclaration instanceof ErrorDeclaration) {
            strGenerateTypeScriptString = generateTypeScriptString((ErrorDeclaration) exportedDeclaration);
        } else if (exportedDeclaration instanceof ExportedConstructor) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedConstructor) exportedDeclaration, str);
        } else if (exportedDeclaration instanceof ExportedConstructSignature) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedConstructSignature) exportedDeclaration, str);
        } else if (exportedDeclaration instanceof ExportedNamespace) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedNamespace) exportedDeclaration, str, str2);
        } else if (exportedDeclaration instanceof ExportedFunction) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedFunction) exportedDeclaration, str, str2);
        } else if (exportedDeclaration instanceof ExportedRegularClass) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedRegularClass) exportedDeclaration, str, str2);
        } else if (exportedDeclaration instanceof ExportedProperty) {
            strGenerateTypeScriptString = generateTypeScriptString((ExportedProperty) exportedDeclaration, str, str2, z);
        } else {
            if (!(exportedDeclaration instanceof ExportedObject)) {
                throw new NoWhenBranchMatchedException();
            }
            strGenerateTypeScriptString = generateTypeScriptString((ExportedObject) exportedDeclaration, str, str2, z);
        }
        return sbAppend.append(strGenerateTypeScriptString).toString();
    }

    private final String toTypeScript(Iterable<? extends ExportedAttribute> iterable, final String str) {
        String strJoinToString$default = CollectionsKt.joinToString$default(iterable, "\n", null, null, 0, null, new Function1<ExportedAttribute, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedAttribute it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ExportModelToTsDeclarations.this.toTypeScript(it, str);
            }
        }, 30, null);
        return strJoinToString$default.length() > 0 ? strJoinToString$default + '\n' : strJoinToString$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toTypeScript(ExportedAttribute exportedAttribute, String str) {
        if (exportedAttribute instanceof ExportedAttribute.DeprecatedAttribute) {
            return str + tsDeprecated(((ExportedAttribute.DeprecatedAttribute) exportedAttribute).getMessage());
        }
        throw new NoWhenBranchMatchedException();
    }

    private final String generateTypeScriptString(ErrorDeclaration errorDeclaration) {
        return "/* ErrorDeclaration: " + errorDeclaration.getMessage() + " */";
    }

    private final String generateTypeScriptString(ExportedNamespace exportedNamespace, String str, String str2) {
        return str2 + "namespace " + exportedNamespace.getName() + " {\n" + toTypeScript((List<? extends ExportedDeclaration>) exportedNamespace.getDeclarations(), str + "    ") + str + '}';
    }

    private final String generateTypeScriptString(ExportedConstructor exportedConstructor, String str) {
        return exportedConstructor.getVisibility().getKeyword() + "constructor(" + generateTypeScriptString(exportedConstructor.getParameters(), str) + ");";
    }

    private final String generateTypeScriptString(ExportedConstructSignature exportedConstructSignature, String str) {
        return "new(" + generateTypeScriptString(exportedConstructSignature.getParameters(), str) + "): " + toTypeScript$default(this, exportedConstructSignature.getReturnType(), str, false, 2, null) + ';';
    }

    private final String generateTypeScriptString(ExportedProperty exportedProperty, String str, String str2, boolean z) {
        String str3;
        String str4 = str + "    ";
        String str5 = exportedProperty.isOptional() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "";
        boolean zIsValidES5Identifier = IdentifierPolicyKt.isValidES5Identifier(exportedProperty.getName());
        String name2 = !zIsValidES5Identifier ? "\"" + exportedProperty.getName() + '\"' : exportedProperty.getName();
        IrFunction irGetter = exportedProperty.getIrGetter();
        boolean zAreEqual = Intrinsics.areEqual(irGetter != null ? irGetter.getOrigin() : null, JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE);
        String typeScript$default = toTypeScript$default(this, exportedProperty.getType(), (!exportedProperty.isMember() && z && zAreEqual) ? str4 : str, false, 2, null);
        if (exportedProperty.isMember()) {
            String str6 = exportedProperty.isStatic() ? "static " : "";
            String str7 = exportedProperty.isAbstract() ? "abstract " : "";
            String str8 = exportedProperty.isProtected() ? "protected " : "";
            if (exportedProperty.isField()) {
                return str2 + str8 + str6 + str7 + (exportedProperty.getMutable() ? "" : "readonly ") + name2 + str5 + ": " + typeScript$default + ';';
            }
            String str9 = str2 + str8 + str6 + str7 + "get " + name2 + "(): " + typeScript$default + ';';
            str3 = exportedProperty.getMutable() ? "\n" + str + str2 + str8 + str6 + str7 + "set " + name2 + "(value: " + typeScript$default + ");" : null;
            return str9 + (str3 != null ? str3 : "");
        }
        if (!zIsValidES5Identifier) {
            return "";
        }
        if (!z) {
            return str2 + (exportedProperty.getMutable() ? "let " : "const ") + name2 + str5 + ": " + typeScript$default + ';';
        }
        if (zAreEqual) {
            return str2 + "const " + exportedProperty.getName() + ": {\n" + str4 + "getInstance(): " + typeScript$default + ";\n};";
        }
        String str10 = "get(): " + typeScript$default + ';';
        str3 = exportedProperty.getMutable() ? " set(value: " + typeScript$default + "): void;" : null;
        return str2 + "const " + exportedProperty.getName() + ": { " + str10 + (str3 != null ? str3 : "") + " };";
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0042  */
    /* JADX WARN: Code duplicated, block: B:20:0x007b  */
    /* JADX WARN: Code duplicated, block: B:25:0x00b5  */
    private final String generateTypeScriptString(ExportedFunction exportedFunction, final String str, String str2) {
        String str3;
        String str4;
        String str5;
        String name2;
        String str6 = exportedFunction.isProtected() ? "protected " : "";
        if (exportedFunction.isMember()) {
            if (!exportedFunction.isStatic()) {
                if (exportedFunction.isAbstract()) {
                    str3 = "abstract ";
                } else {
                    str4 = "";
                }
                String strGenerateTypeScriptString = generateTypeScriptString(exportedFunction.getParameters(), str);
                if (exportedFunction.getTypeParameters().isEmpty()) {
                    str5 = "";
                } else {
                    str5 = "<" + CollectionsKt.joinToString$default(exportedFunction.getTypeParameters(), ", ", null, null, 0, null, new Function1<ExportedType.TypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$generateTypeScriptString$renderedTypeParameters$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(ExportedType.TypeParameter it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return ExportModelToTsDeclarations.toTypeScript$default(this.this$0, it, str, false, 2, null);
                        }
                    }, 30, null) + '>';
                }
                String typeScript$default = toTypeScript$default(this, exportedFunction.getReturnType(), str, false, 2, null);
                boolean zIsValidES5Identifier = IdentifierPolicyKt.isValidES5Identifier(exportedFunction.getName());
                if (exportedFunction.isMember() || zIsValidES5Identifier) {
                    name2 = exportedFunction.getName();
                } else {
                    name2 = "\"" + exportedFunction.getName() + '\"';
                }
                return (!exportedFunction.isMember() || zIsValidES5Identifier) ? str2 + str6 + str4 + name2 + str5 + '(' + strGenerateTypeScriptString + "): " + typeScript$default + ';' : "";
            }
            str3 = "static ";
        } else {
            str3 = "function ";
        }
        str4 = str3;
        String strGenerateTypeScriptString2 = generateTypeScriptString(exportedFunction.getParameters(), str);
        if (exportedFunction.getTypeParameters().isEmpty()) {
            str5 = "<" + CollectionsKt.joinToString$default(exportedFunction.getTypeParameters(), ", ", null, null, 0, null, new Function1<ExportedType.TypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$generateTypeScriptString$renderedTypeParameters$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ExportedType.TypeParameter it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ExportModelToTsDeclarations.toTypeScript$default(this.this$0, it, str, false, 2, null);
                }
            }, 30, null) + '>';
        } else {
            str5 = "";
        }
        String typeScript$default2 = toTypeScript$default(this, exportedFunction.getReturnType(), str, false, 2, null);
        boolean zIsValidES5Identifier2 = IdentifierPolicyKt.isValidES5Identifier(exportedFunction.getName());
        if (exportedFunction.isMember()) {
            name2 = exportedFunction.getName();
        } else {
            name2 = exportedFunction.getName();
        }
        if (exportedFunction.isMember()) {
        }
    }

    private final String generateTypeScriptString(ExportedObject exportedObject, String str, String str2, boolean z) {
        ArrayList arrayListEmptyList;
        String name2;
        FqName fqNameWithJsNameWhenAvailable;
        String strAsString;
        boolean zCouldBeProperty = couldBeProperty(exportedObject);
        List<ExportedClass> nestedClasses = exportedObject.getNestedClasses();
        if (!zCouldBeProperty) {
            nestedClasses = null;
        }
        if (nestedClasses != null) {
            List<ExportedClass> list = nestedClasses;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ExportedClass exportedClass : list) {
                Intrinsics.checkNotNull(exportedClass, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.backend.js.export.ExportedObject");
                arrayList.add((ExportedObject) exportedClass);
            }
            arrayListEmptyList = arrayList;
        } else {
            arrayListEmptyList = null;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        ExportedType.IntersectionType inlineInterfaceType = new ExportedType.InlineInterfaceType(CollectionsKt.plus((Collection) exportedObject.getMembers(), (Iterable) arrayListEmptyList));
        Iterator it = CollectionsKt.plus((Collection) exportedObject.getSuperClasses(), (Iterable) exportedObject.getSuperInterfaces()).iterator();
        while (it.hasNext()) {
            inlineInterfaceType = new ExportedType.IntersectionType(inlineInterfaceType, (ExportedType) it.next());
        }
        if (!zCouldBeProperty) {
            inlineInterfaceType = new ExportedType.IntersectionType(inlineInterfaceType, new ExportedType.InlineInterfaceType(CollectionsKt.listOf(new ExportedConstructSignature(CollectionsKt.emptyList(), ExportedType.Primitive.Any.INSTANCE))));
        }
        ExportedType exportedType = inlineInterfaceType;
        IrDeclarationParent parent = exportedObject.getIr().getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        IrClass ir = exportedObject.getIr();
        if (zCouldBeProperty) {
            ir = null;
        }
        if (ir == null || (fqNameWithJsNameWhenAvailable = IrJsUtilsKt.getFqNameWithJsNameWhenAvailable(ir, true)) == null || (strAsString = fqNameWithJsNameWhenAvailable.asString()) == null || (name2 = StringsKt.replace$default(strAsString, '.', '$', false, 4, (Object) null)) == null) {
            name2 = exportedObject.getName();
        }
        String str3 = name2;
        ExportedProperty exportedProperty = new ExportedProperty(str3, exportedType, false, irClass != null && zCouldBeProperty, (exportedObject.getIr().getIsInner() || irClass == null || IrUtilsKt.isObject(irClass)) ? false : true, false, Intrinsics.areEqual(exportedObject.getIr().getVisibility(), DescriptorVisibilities.PROTECTED), false, exportedObject.getIrGetter(), null, false, 1696, null);
        if (zCouldBeProperty) {
            return generateTypeScriptString(exportedProperty, str, str2, z);
        }
        StringBuilder sb = new StringBuilder();
        String str4 = z ? "__NonExistent" : null;
        if (str4 == null) {
            str4 = "";
        }
        String string = sb.append(str4).append(exportedObject.getName()).toString();
        String str5 = "_objects_." + str3;
        boolean z2 = (exportedObject.getMembers().isEmpty() && exportedObject.getSuperInterfaces().isEmpty() && exportedObject.getSuperClasses().isEmpty()) ? false : true;
        ExportedType.ClassType classType = new ExportedType.ClassType(str5, CollectionsKt.emptyList(), exportedObject.getIr());
        if (!z2) {
            classType = null;
        }
        ExportedRegularClass exportedRegularClass = new ExportedRegularClass(string, false, true, CollectionsKt.listOfNotNull(classType), exportedObject.getSuperInterfaces(), CollectionsKt.emptyList(), CollectionsKt.listOf(new ExportedConstructor(CollectionsKt.emptyList(), ExportedVisibility.PRIVATE)), exportedObject.getNestedClasses(), exportedObject.getIr());
        if (z2) {
            this.objectsSyntheticProperties.add(exportedProperty);
        }
        if (z && !exportedProperty.isMember()) {
            return generateTypeScriptString(exportedProperty.copy((2044 & 1) != 0 ? exportedProperty.name : exportedObject.getName(), (2044 & 2) != 0 ? exportedProperty.type : new ExportedType.TypeOf(string), (2044 & 4) != 0 ? exportedProperty.mutable : false, (2044 & 8) != 0 ? exportedProperty.isMember : false, (2044 & 16) != 0 ? exportedProperty.isStatic : false, (2044 & 32) != 0 ? exportedProperty.isAbstract : false, (2044 & 64) != 0 ? exportedProperty.isProtected : false, (2044 & 128) != 0 ? exportedProperty.isField : false, (2044 & 256) != 0 ? exportedProperty.irGetter : null, (2044 & 512) != 0 ? exportedProperty.irSetter : null, (2044 & 1024) != 0 ? exportedProperty.isOptional : false), str, str2, z) + '\n' + generateTypeScriptString(exportedRegularClass, str, "declare ");
        }
        return generateTypeScriptString(exportedRegularClass, str, str2);
    }

    private final String generateTypeScriptString(ExportedRegularClass exportedRegularClass, final String str, String str2) {
        String str3;
        String str4 = exportedRegularClass.isInterface() ? PsiKeyword.INTERFACE : "class";
        String str5 = exportedRegularClass.isInterface() ? PsiKeyword.EXTENDS : PsiKeyword.IMPLEMENTS;
        String extendsClause = toExtendsClause(exportedRegularClass.getSuperClasses(), str);
        String implementsClause = toImplementsClause(exportedRegularClass.getSuperInterfaces(), str5, str);
        List<ExportedClass> nestedClasses = exportedRegularClass.getNestedClasses();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : nestedClasses) {
            if (couldBeProperty((ExportedClass) obj)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        List<ExportedDeclaration> members = exportedRegularClass.getMembers();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(members, 10));
        for (ExportedFunction exportedFunctionCopy : members) {
            if (exportedRegularClass.getIr().getIsInner() && (exportedFunctionCopy instanceof ExportedFunction)) {
                ExportedFunction exportedFunction = (ExportedFunction) exportedFunctionCopy;
                if (exportedFunction.isStatic()) {
                    exportedFunctionCopy = exportedFunction.copy((507 & 1) != 0 ? exportedFunction.name : null, (507 & 2) != 0 ? exportedFunction.returnType : null, (507 & 4) != 0 ? exportedFunction.parameters : CollectionsKt.drop(exportedFunction.getParameters(), 1), (507 & 8) != 0 ? exportedFunction.typeParameters : null, (507 & 16) != 0 ? exportedFunction.isMember : false, (507 & 32) != 0 ? exportedFunction.isStatic : false, (507 & 64) != 0 ? exportedFunction.isAbstract : false, (507 & 128) != 0 ? exportedFunction.isProtected : false, (507 & 256) != 0 ? exportedFunction.ir : null);
                }
            }
            arrayList3.add(exportedFunctionCopy);
        }
        List listPlus = CollectionsKt.plus((Collection) arrayList3, (Iterable) list);
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : list2) {
            if (((ExportedClass) obj2).getIr().getIsInner()) {
                arrayList4.add(obj2);
            } else {
                arrayList5.add(obj2);
            }
        }
        Pair pair2 = new Pair(arrayList4, arrayList5);
        List list3 = (List) pair2.component1();
        List list4 = (List) pair2.component2();
        List list5 = list3;
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
        Iterator it = list5.iterator();
        while (it.hasNext()) {
            arrayList6.add(toReadonlyProperty((ExportedClass) it.next()));
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.plus((Collection) listPlus, (Iterable) arrayList6), "", null, null, 0, null, new Function1<ExportedDeclaration, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$generateTypeScriptString$membersString$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedDeclaration it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return ExportModelToTsDeclarations.toTypeScript$default(this.this$0, it2, str + "    ", null, false, 6, null) + '\n';
            }
        }, 30, null);
        if (exportedRegularClass.isInterface() || exportedRegularClass.isAbstract()) {
            str3 = "";
        } else {
            List list6 = listPlus;
            if (!(list6 instanceof Collection) || !list6.isEmpty()) {
                Iterator it2 = list6.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((ExportedDeclaration) it2.next()) instanceof ExportedConstructor) {
                            str3 = "";
                        }
                    }
                }
            }
            str3 = str + "    private constructor();\n";
        }
        String str6 = !exportedRegularClass.getTypeParameters().isEmpty() ? "<" + CollectionsKt.joinToString$default(exportedRegularClass.getTypeParameters(), ", ", null, null, 0, null, new Function1<ExportedType.TypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$generateTypeScriptString$renderedTypeParameters$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedType.TypeParameter it3) {
                Intrinsics.checkNotNullParameter(it3, "it");
                return ExportModelToTsDeclarations.toTypeScript$default(this.this$0, it3, str, false, 2, null);
            }
        }, 30, null) + '>' : "";
        String str7 = (!exportedRegularClass.isAbstract() || exportedRegularClass.isInterface()) ? "" : "abstract ";
        String str8 = str3 + strJoinToString$default + str;
        List list7 = list4;
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list5, 10));
        Iterator it3 = list5.iterator();
        while (it3.hasNext()) {
            arrayList7.add(withProtectedConstructors((ExportedClass) it3.next()));
        }
        List listPlus2 = CollectionsKt.plus((Collection) list7, (Iterable) arrayList7);
        return IdentifierPolicyKt.isValidES5Identifier(exportedRegularClass.getName()) ? (hasSuperClassWithPrivateConstructor(exportedRegularClass) ? tsIgnore("extends class with private primary constructor") + '\n' + str : "") + (str2 + str7 + str4 + StringUtil.SPACE + exportedRegularClass.getName() + str6 + extendsClause + implementsClause + " {\n" + str8 + '}') + (!listPlus2.isEmpty() ? "\n" + toTypeScript$default(this, new ExportedNamespace(exportedRegularClass.getName(), listPlus2), str, str2, false, 4, null) : "") : "";
    }

    private final boolean hasSuperClassWithPrivateConstructor(ExportedRegularClass exportedRegularClass) {
        Object next;
        IrClass ir;
        IrConstructor primaryConstructor;
        Iterator<T> it = exportedRegularClass.getSuperClasses().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof ExportedType.ClassType));
        ExportedType.ClassType classType = (ExportedType.ClassType) next;
        if (classType == null || (ir = classType.getIr()) == null) {
            return false;
        }
        IrClass irClass = IrUtilsKt.isObject(ir) ? null : ir;
        if (irClass == null || (primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass)) == null) {
            return false;
        }
        return Intrinsics.areEqual(primaryConstructor.getVisibility(), DescriptorVisibilities.PRIVATE) || IrUtilsKt.hasAnnotation(primaryConstructor, JsAnnotations.INSTANCE.getJsExportIgnoreFqn());
    }

    private final String toExtendsClause(List<? extends ExportedType> list, final String str) {
        if (list.isEmpty()) {
            return "";
        }
        List<? extends ExportedType> list2 = list;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (obj instanceof ExportedType.ImplicitlyExportedType) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList2, ", ", null, null, 0, null, new Function1<ExportedType.ImplicitlyExportedType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$toExtendsClause$implicitlyExportedClassesString$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedType.ImplicitlyExportedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.toTypeScript((ExportedType) it, str, true);
            }
        }, 30, null);
        if (arrayList2.size() == list.size()) {
            return " /* extends " + strJoinToString$default + " */";
        }
        Object obj2 = null;
        if (strJoinToString$default.length() <= 0) {
            strJoinToString$default = null;
        }
        String str2 = strJoinToString$default != null ? "/* " + strJoinToString$default + " */ " : null;
        String str3 = str2 != null ? str2 : "";
        boolean z = false;
        for (Object obj3 : list2) {
            if (!(((ExportedType) obj3) instanceof ExportedType.ImplicitlyExportedType)) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            return " extends " + str3 + toTypeScript((ExportedType) obj2, str, false);
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    private final String toImplementsClause(List<? extends ExportedType> list, String str, final String str2) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : list) {
            if (!(((ExportedType) obj) instanceof ExportedType.ImplicitlyExportedType)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list2 = (List) pair.component1();
        List list3 = (List) pair.component2();
        String strJoinToString$default = CollectionsKt.joinToString$default(list3, ", ", null, null, 0, null, new Function1<ExportedType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$toImplementsClause$listOfNonExportedInterfaces$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return this.this$0.toTypeScript(((ExportedType.ImplicitlyExportedType) it).getType(), str2, true);
            }
        }, 30, null);
        if (list2.isEmpty() && !list3.isEmpty()) {
            return " /* " + str + StringUtil.SPACE + strJoinToString$default + " */";
        }
        if (list2.isEmpty()) {
            return "";
        }
        return " " + str + StringUtil.SPACE + CollectionsKt.joinToString$default(list2, ", ", null, null, 0, null, new Function1<ExportedType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toImplementsClause.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(ExportedType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return ExportModelToTsDeclarations.toTypeScript$default(ExportModelToTsDeclarations.this, it, str2, false, 2, null);
            }
        }, 30, null) + (list3.isEmpty() ? "" : "/*, " + strJoinToString$default + " */");
    }

    private final ExportedRegularClass withProtectedConstructors(ExportedClass exportedClass) {
        Intrinsics.checkNotNull(exportedClass, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.backend.js.export.ExportedRegularClass");
        ExportedRegularClass exportedRegularClass = (ExportedRegularClass) exportedClass;
        List<ExportedDeclaration> members = exportedClass.getMembers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(members, 10));
        for (ExportedConstructor exportedConstructorCopy$default : members) {
            if (exportedConstructorCopy$default instanceof ExportedConstructor) {
                ExportedConstructor exportedConstructor = (ExportedConstructor) exportedConstructorCopy$default;
                if (!exportedConstructor.isProtected()) {
                    exportedConstructorCopy$default = ExportedConstructor.copy$default(exportedConstructor, null, ExportedVisibility.PROTECTED, 1, null);
                }
            }
            arrayList.add(exportedConstructorCopy$default);
        }
        return exportedRegularClass.copy((XKeySym.XK_zabovedot & 1) != 0 ? exportedRegularClass.name : null, (XKeySym.XK_zabovedot & 2) != 0 ? exportedRegularClass.isInterface : false, (XKeySym.XK_zabovedot & 4) != 0 ? exportedRegularClass.isAbstract : false, (XKeySym.XK_zabovedot & 8) != 0 ? exportedRegularClass.superClasses : null, (XKeySym.XK_zabovedot & 16) != 0 ? exportedRegularClass.superInterfaces : null, (XKeySym.XK_zabovedot & 32) != 0 ? exportedRegularClass.typeParameters : null, (XKeySym.XK_zabovedot & 64) != 0 ? exportedRegularClass.members : arrayList, (XKeySym.XK_zabovedot & 128) != 0 ? exportedRegularClass.nestedClasses : null, (XKeySym.XK_zabovedot & 256) != 0 ? exportedRegularClass.ir : null);
    }

    private final ExportedProperty toReadonlyProperty(ExportedClass exportedClass) {
        final String strAsNestedClassAccess = asNestedClassAccess(exportedClass.getIr());
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(exportedClass.getMembers()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$toReadonlyProperty$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof ExportedConstructor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return new ExportedProperty(exportedClass.getName(), new ExportedType.IntersectionType(new ExportedType.InlineInterfaceType(SequencesKt.toList(SequencesKt.map(SequencesKt.filterNot(sequenceFilter, new Function1<ExportedConstructor, Boolean>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$toReadonlyProperty$allPublicConstructors$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ExportedConstructor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.isProtected());
            }
        }), new Function1<ExportedConstructor, ExportedConstructSignature>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations$toReadonlyProperty$allPublicConstructors$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ExportedConstructSignature invoke(ExportedConstructor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ExportedConstructSignature(CollectionsKt.drop(it.getParameters(), 1), new ExportedType.TypeParameter(strAsNestedClassAccess, null, 2, null));
            }
        }))), new ExportedType.TypeOf(strAsNestedClassAccess)), false, true, false, false, false, false, null, null, false, XKeySym.XK_Greek_pi, null);
    }

    private final String generateTypeScriptString(List<ExportedParameter> list, String str) {
        ArrayList arrayList = new ArrayList();
        if (!list.isEmpty()) {
            ListIterator<ExportedParameter> listIterator = list.listIterator(list.size());
            boolean z = true;
            while (listIterator.hasPrevious()) {
                ExportedParameter exportedParameterPrevious = listIterator.previous();
                if (!exportedParameterPrevious.getHasDefaultValue()) {
                    z = false;
                }
                arrayList.add(0, toTypeScript(exportedParameterPrevious, str, z));
            }
        }
        return CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null);
    }

    private final String toTypeScript(ExportedParameter exportedParameter, String str, boolean z) {
        ExportedType.UnionType type;
        String strSanitizeName = NameTablesKt.sanitizeName(exportedParameter.getName(), false);
        if (exportedParameter.getHasDefaultValue() && !z) {
            type = new ExportedType.UnionType(exportedParameter.getType(), ExportedType.Primitive.Undefined.INSTANCE);
        } else {
            type = exportedParameter.getType();
        }
        return strSanitizeName + ((exportedParameter.getHasDefaultValue() && z) ? TypeDescription.Generic.OfWildcardType.SYMBOL : "") + ": " + toTypeScript$default(this, type, str, false, 2, null);
    }

    private final String asNestedClassAccess(IrClass irClass) {
        String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(irClass).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return !(irClass.getParent() instanceof IrClass) ? identifier : asNestedClassAccess(IrUtilsKt.getParentAsClass(irClass)) + '.' + identifier;
    }

    static /* synthetic */ String toTypeScript$default(ExportModelToTsDeclarations exportModelToTsDeclarations, ExportedType exportedType, String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return exportModelToTsDeclarations.toTypeScript(exportedType, str, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String toTypeScript(ExportedType exportedType, final String str, final boolean z) {
        if (exportedType instanceof ExportedType.Primitive) {
            return ((ExportedType.Primitive) exportedType).getTypescript();
        }
        if (exportedType instanceof ExportedType.Array) {
            return "Array<" + toTypeScript(((ExportedType.Array) exportedType).getElementType(), str, z) + '>';
        }
        if (exportedType instanceof ExportedType.Function) {
            ExportedType.Function function = (ExportedType.Function) exportedType;
            return "(" + CollectionsKt.joinToString$default(CollectionsKt.withIndex(function.getParameterTypes()), ", ", null, null, 0, null, new Function1<IndexedValue<? extends ExportedType>, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(IndexedValue<? extends ExportedType> indexedValue) {
                    Intrinsics.checkNotNullParameter(indexedValue, "<name for destructuring parameter 0>");
                    return "p" + indexedValue.getIndex() + ": " + ExportModelToTsDeclarations.this.toTypeScript(indexedValue.component2(), str, z);
                }
            }, 30, null) + ") => " + toTypeScript(function.getReturnType(), str, z);
        }
        if (exportedType instanceof ExportedType.ClassType) {
            ExportedType.ClassType classType = (ExportedType.ClassType) exportedType;
            return classType.getName() + (classType.getArguments().isEmpty() ? "" : "<" + CollectionsKt.joinToString$default(classType.getArguments(), ", ", null, null, 0, null, new Function1<ExportedType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.6
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ExportedType it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ExportModelToTsDeclarations.this.toTypeScript(it, str, z);
                }
            }, 30, null) + '>');
        }
        if (exportedType instanceof ExportedType.TypeOf) {
            return "typeof " + ((ExportedType.TypeOf) exportedType).getName();
        }
        if (exportedType instanceof ExportedType.ErrorType) {
            return z ? ((ExportedType.ErrorType) exportedType).getComment() : "any /*" + ((ExportedType.ErrorType) exportedType).getComment() + "*/";
        }
        if (exportedType instanceof ExportedType.Nullable) {
            return "Nullable<" + toTypeScript(((ExportedType.Nullable) exportedType).getBaseType(), str, z) + '>';
        }
        if (exportedType instanceof ExportedType.InlineInterfaceType) {
            return CollectionsKt.joinToString$default(((ExportedType.InlineInterfaceType) exportedType).getMembers(), "", "{\n", str + '}', 0, null, new Function1<ExportedDeclaration, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarations.toTypeScript.7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(ExportedDeclaration it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return ExportModelToTsDeclarations.toTypeScript$default(ExportModelToTsDeclarations.this, it, str + "    ", null, false, 6, null) + '\n';
                }
            }, 24, null);
        }
        if (exportedType instanceof ExportedType.IntersectionType) {
            ExportedType.IntersectionType intersectionType = (ExportedType.IntersectionType) exportedType;
            return toTypeScript$default(this, intersectionType.getLhs(), str, false, 2, null) + " & " + toTypeScript(intersectionType.getRhs(), str, z);
        }
        if (exportedType instanceof ExportedType.UnionType) {
            ExportedType.UnionType unionType = (ExportedType.UnionType) exportedType;
            return toTypeScript$default(this, unionType.getLhs(), str, false, 2, null) + " | " + toTypeScript(unionType.getRhs(), str, z);
        }
        if (exportedType instanceof ExportedType.LiteralType.StringLiteralType) {
            return "\"" + ((ExportedType.LiteralType.StringLiteralType) exportedType).getValue() + '\"';
        }
        if (exportedType instanceof ExportedType.LiteralType.NumberLiteralType) {
            return ((ExportedType.LiteralType.NumberLiteralType) exportedType).getValue().toString();
        }
        if (!(exportedType instanceof ExportedType.ImplicitlyExportedType)) {
            if (exportedType instanceof ExportedType.PropertyType) {
                ExportedType.PropertyType propertyType = (ExportedType.PropertyType) exportedType;
                return toTypeScript(propertyType.getContainer(), str, z) + '[' + toTypeScript(propertyType.getPropertyName(), str, z) + ']';
            }
            if (!(exportedType instanceof ExportedType.TypeParameter)) {
                throw new NoWhenBranchMatchedException();
            }
            ExportedType.TypeParameter typeParameter = (ExportedType.TypeParameter) exportedType;
            return typeParameter.getConstraint() == null ? typeParameter.getName() : typeParameter.getName() + " extends " + toTypeScript(typeParameter.getConstraint(), str, z);
        }
        ExportedType.ImplicitlyExportedType implicitlyExportedType = (ExportedType.ImplicitlyExportedType) exportedType;
        String typeScript = toTypeScript(implicitlyExportedType.getType(), "", true);
        if (z) {
            return typeScript;
        }
        String typeScript$default = toTypeScript$default(this, implicitlyExportedType.getExportedSupertype(), str, false, 2, null);
        StringBuilder sb = new StringBuilder();
        if (implicitlyExportedType.getExportedSupertype() instanceof ExportedType.IntersectionType) {
            typeScript$default = "(" + typeScript$default + ')';
        }
        return sb.append(typeScript$default).append("/* ").append(typeScript).append(" */").toString();
    }

    private final boolean couldBeProperty(ExportedClass exportedClass) {
        if (exportedClass instanceof ExportedObject) {
            List<ExportedClass> nestedClasses = exportedClass.getNestedClasses();
            if (!(nestedClasses instanceof Collection) || !nestedClasses.isEmpty()) {
                for (ExportedClass exportedClass2 : nestedClasses) {
                    if (!couldBeProperty(exportedClass2) || Intrinsics.areEqual(exportedClass2.getIr().getVisibility(), DescriptorVisibilities.PROTECTED)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    private final String tsIgnore(String reason) {
        return "/* @ts-ignore: " + reason + " */";
    }

    private final String tsDeprecated(String message) {
        return "/** @deprecated " + message + " */";
    }
}
