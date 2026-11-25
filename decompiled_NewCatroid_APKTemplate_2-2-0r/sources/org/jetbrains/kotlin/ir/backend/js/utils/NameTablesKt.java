package org.jetbrains.kotlin.ir.backend.js.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsManglerIr;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.common.IdentifierPolicyKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: NameTables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0016\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\t\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u001b\u0010\n\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\u0005\u001a\u0002H\u000bH\u0002¢\u0006\u0002\u0010\f\u001a\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u001a\u0010\u0010\u0011\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00130\u0012\u001a\u001a\u0010\u0014\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a&\u0010\u0017\u001a\u00020\u0018*\u00020\u00182\u0017\u0010\u0019\u001a\u0013\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00100\u001a¢\u0006\u0002\b\u001bH\u0082\b\u001a\f\u0010\u001c\u001a\u0004\u0018\u00010\u0001*\u00020\u001d\u001a\n\u0010\u001e\u001a\u00020\u0001*\u00020\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"RESERVED_MEMBER_NAME_SUFFIX", "", "SYNTHETIC_BLOCK_LABEL", "SYNTHETIC_LOOP_LABEL", "calculateJsFunctionSignature", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "jsFunctionSignature", "mapToKey", ExifInterface.GPS_DIRECTION_TRUE, "(Ljava/lang/Object;)Ljava/lang/String;", "sanitizeName", "name", "withHash", "", ArchiveStreamFactory.DUMP, "Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "joinTypes", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "mangleIfNot", "", "predicate", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "nameIfPropertyAccessor", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "toJsIdentifier", "", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NameTablesKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> String mapToKey(T t) {
        JsManglerIr jsManglerIr = JsManglerIr.INSTANCE;
        if (t instanceof IrDeclaration) {
            try {
                return String.valueOf(jsManglerIr.hashedMangle(t, false));
            } catch (Throwable unused) {
                return "wrong_key";
            }
        }
        if (t instanceof String) {
            return String.valueOf(jsManglerIr.getHashMangle((String) t));
        }
        throw new IllegalStateException(("Key is not generated for " + (t != 0 ? Reflection.getOrCreateKotlinClass(t.getClass()).getSimpleName() : null)).toString());
    }

    public static final String dump(NameTable<IrDeclaration> nameTable) {
        Intrinsics.checkNotNullParameter(nameTable, "<this>");
        return "Names: \n" + CollectionsKt.joinToString$default(MapsKt.toList(nameTable.getNames()), "\n", null, null, 0, null, new Function1<Pair<? extends IrDeclaration, ? extends String>, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt.dump.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Pair<? extends IrDeclaration, String> pair) {
                Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                IrDeclaration irDeclarationComponent1 = pair.component1();
                String strComponent2 = pair.component2();
                Intrinsics.checkNotNull(irDeclarationComponent1, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName");
                FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable((IrDeclarationWithName) irDeclarationComponent1);
                if (fqNameWhenAvailable != null) {
                    irDeclarationComponent1 = fqNameWhenAvailable;
                }
                return "---  " + irDeclarationComponent1 + " => " + strComponent2;
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends IrDeclaration, ? extends String> pair) {
                return invoke2((Pair<? extends IrDeclaration, String>) pair);
            }
        }, 30, null);
    }

    public static final String toJsIdentifier(int i) {
        String strValueOf = String.valueOf((char) ((i % 26) + 97));
        int i2 = i / 26;
        if (i2 == 0) {
            return strValueOf;
        }
        StringBuilder sbAppend = new StringBuilder().append(strValueOf);
        String string = Integer.toString(i2, CharsKt.checkRadix(36));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(string).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String joinTypes(List<? extends IrType> list, final JsIrBackendContext jsIrBackendContext) {
        if (list.isEmpty()) {
            return "";
        }
        return CollectionsKt.joinToString$default(list, "$", "$", null, 0, null, new Function1<IrType, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt.joinTypes.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(IrType superType) {
                Intrinsics.checkNotNullParameter(superType, "superType");
                return IrTypeUtilsKt.asString(superType, jsIrBackendContext);
            }
        }, 28, null);
    }

    public static final String calculateJsFunctionSignature(IrFunction declaration, final JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        IrFunction irFunction = declaration;
        String strNameIfPropertyAccessor = nameIfPropertyAccessor(irFunction);
        if (strNameIfPropertyAccessor == null) {
            strNameIfPropertyAccessor = AnnotationUtilsKt.getJsNameOrKotlinName(irFunction).asString();
            Intrinsics.checkNotNullExpressionValue(strNameIfPropertyAccessor, "asString(...)");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strNameIfPropertyAccessor);
        List<IrTypeParameter> typeParameters = declaration.getTypeParameters();
        if (!typeParameters.isEmpty()) {
            sb.append("_$t");
            for (IrTypeParameter irTypeParameter : typeParameters) {
                sb.append("_").append(irTypeParameter.getName().asString()).append(joinTypes(irTypeParameter.getSuperTypes(), context));
            }
        }
        IrValueParameter extensionReceiverParameter = declaration.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            sb.append("_r$" + IrTypeUtilsKt.asString(extensionReceiverParameter.getType(), context) + joinTypes(org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.superTypes(extensionReceiverParameter.getType()), context));
        }
        List<IrValueParameter> valueParameters = declaration.getValueParameters();
        if (!valueParameters.isEmpty()) {
        }
        IrType returnType = declaration.getReturnType();
        if (context.getInlineClassesUtils().isTypeInlined(returnType) || IrTypePredicatesKt.isUnit(returnType)) {
            sb.append("_ret$" + IrTypeUtilsKt.asString(returnType, context));
        }
        String string = Integer.toString(Math.abs(sb.toString().hashCode()), CharsKt.checkRadix(36));
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return context.getGlobalInternationService().string(sanitizeName(strNameIfPropertyAccessor, false) + '_' + string + "_k$");
    }

    public static final String jsFunctionSignature(IrFunction declaration, JsIrBackendContext context) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        if (IrDeclarationsKt.isStaticMethodOfClass(declaration)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (declaration.getDispatchReceiverParameter() == null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (MiscKt.hasStableJsName(declaration, context)) {
            IrFunction irFunction = declaration;
            String strAsString = AnnotationUtilsKt.getJsNameOrKotlinName(irFunction).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (StringsKt.endsWith$default(strAsString, "_k$", false, 2, (Object) null)) {
                throw new IllegalArgumentException(("Function " + IrUtilsKt.getFqNameWhenAvailable(irFunction) + " uses reserved name suffix \"_k$\"").toString());
            }
            return strAsString;
        }
        IrSimpleFunction irSimpleFunction2 = declaration instanceof IrSimpleFunction ? (IrSimpleFunction) declaration : null;
        if (irSimpleFunction2 != null && (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction2, false, null, 3, null)) != null) {
            declaration = irSimpleFunction;
        }
        return calculateJsFunctionSignature(declaration, context);
    }

    public static /* synthetic */ String sanitizeName$default(String str, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return sanitizeName(str, z);
    }

    public static final String sanitizeName(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (IdentifierPolicyKt.isValidES5Identifier(name2)) {
            return name2;
        }
        String str = name2;
        if (str.length() == 0) {
            return "_";
        }
        StringBuilder sb = new StringBuilder(name2.length() + (z ? 7 : 0));
        char cFirst = StringsKt.first(str);
        if (!IdentifierPolicyKt.isES5IdentifierStart(cFirst)) {
            cFirst = '_';
        }
        sb.append(cFirst);
        int lastIndex = StringsKt.getLastIndex(str);
        int i = 1;
        if (1 <= lastIndex) {
            while (true) {
                char cCharAt = name2.charAt(i);
                if (!IdentifierPolicyKt.isES5IdentifierPart(cCharAt)) {
                    cCharAt = '_';
                }
                sb.append(cCharAt);
                if (i == lastIndex) {
                    break;
                }
                i++;
            }
        }
        if (z) {
            StringBuilder sbAppend = new StringBuilder().append((Object) sb).append('_');
            String string = Integer.toString(Math.abs(name2.hashCode()), CharsKt.checkRadix(36));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return sbAppend.append(string).toString();
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNull(string2);
        return string2;
    }

    public static final String nameIfPropertyAccessor(IrDeclarationWithName irDeclarationWithName) {
        String str;
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        if (!(irDeclarationWithName instanceof IrSimpleFunction)) {
            return null;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irDeclarationWithName;
        if (irSimpleFunction.getCorrespondingPropertySymbol() != null) {
            IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
            Intrinsics.checkNotNull(correspondingPropertySymbol);
            IrProperty owner = correspondingPropertySymbol.getOwner();
            String strAsString = AnnotationUtilsKt.getJsNameOrKotlinName(owner).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (Intrinsics.areEqual(irDeclarationWithName, owner.getGetter())) {
                str = "get_";
            } else {
                if (!Intrinsics.areEqual(irDeclarationWithName, owner.getSetter())) {
                    throw new IllegalStateException("".toString());
                }
                str = "set_";
            }
            return str + strAsString;
        }
        if (!Intrinsics.areEqual(irDeclarationWithName.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_PROPERTY_ACCESSOR.INSTANCE)) {
            return null;
        }
        String strAsString2 = AnnotationUtilsKt.getJsNameOrKotlinName(irDeclarationWithName).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        return StringsKt.replaceFirst$default(StringsKt.replaceFirst$default(StringsKt.removeSuffix(StringsKt.removePrefix(strAsString2, (CharSequence) "<"), (CharSequence) ">"), "get-", "get_", false, 4, (Object) null), "set-", "set_", false, 4, (Object) null);
    }
}
