package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf;
import org.jetbrains.kotlin.metadata.java.JavaClassProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;

/* JADX INFO: compiled from: ProtoCompareGenerated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\b2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\t2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u000b2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\f2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u000e2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u000f2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00102\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00112\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00122\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00132\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00142\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00152\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00162\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00172\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00182\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00192\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u001a2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u001b2\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00070\u0004¨\u0006\u001c"}, d2 = {"hashCode", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "stringIndexes", "Lkotlin/Function1;", "fqNameIndexes", "typeById", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirementTable;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmFieldSignature;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmMethodSignature;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProtoCompareGeneratedKt {
    public static final int hashCode(ProtoBuf.Package r7, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(r7, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int functionCount = r7.getFunctionCount() - 1;
        int i = 0;
        int iIntValue = 1;
        if (functionCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.Function function = r7.getFunction(i2);
                Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                iIntValue = (iIntValue * 31) + hashCode(function, stringIndexes, fqNameIndexes, typeById);
                if (i2 == functionCount) {
                    break;
                }
                i2++;
            }
        }
        int propertyCount = r7.getPropertyCount() - 1;
        if (propertyCount >= 0) {
            int i3 = 0;
            while (true) {
                ProtoBuf.Property property = r7.getProperty(i3);
                Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                iIntValue = (iIntValue * 31) + hashCode(property, stringIndexes, fqNameIndexes, typeById);
                if (i3 == propertyCount) {
                    break;
                }
                i3++;
            }
        }
        int typeAliasCount = r7.getTypeAliasCount() - 1;
        if (typeAliasCount >= 0) {
            int i4 = 0;
            while (true) {
                ProtoBuf.TypeAlias typeAlias = r7.getTypeAlias(i4);
                Intrinsics.checkNotNullExpressionValue(typeAlias, "getTypeAlias(...)");
                iIntValue = (iIntValue * 31) + hashCode(typeAlias, stringIndexes, fqNameIndexes, typeById);
                if (i4 == typeAliasCount) {
                    break;
                }
                i4++;
            }
        }
        if (r7.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = r7.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            iIntValue = (iIntValue * 31) + hashCode(versionRequirementTable, stringIndexes, fqNameIndexes, typeById);
        }
        if (r7.hasExtension(JvmProtoBuf.packageModuleName)) {
            Object extension = r7.getExtension(JvmProtoBuf.packageModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            iIntValue = (iIntValue * 31) + stringIndexes.invoke(extension).intValue();
        }
        int extensionCount = r7.getExtensionCount(JvmProtoBuf.packageLocalVariable) - 1;
        if (extensionCount >= 0) {
            while (true) {
                Object extension2 = r7.getExtension(JvmProtoBuf.packageLocalVariable, i);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Property) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (r7.hasExtension(JsProtoBuf.packageFqName)) {
            Object extension3 = r7.getExtension(JsProtoBuf.packageFqName);
            Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
            iIntValue = (iIntValue * 31) + ((Number) extension3).intValue();
        }
        if (r7.hasExtension(BuiltInsProtoBuf.packageFqName)) {
            Object extension4 = r7.getExtension(BuiltInsProtoBuf.packageFqName);
            Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
            iIntValue = (iIntValue * 31) + ((Number) extension4).intValue();
        }
        if (!r7.hasExtension(KlibMetadataProtoBuf.packageFqName)) {
            return iIntValue;
        }
        Object extension5 = r7.getExtension(KlibMetadataProtoBuf.packageFqName);
        Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
        return (iIntValue * 31) + ((Number) extension5).intValue();
    }

    public static final int hashCode(ProtoBuf.Class r8, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(r8, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = ((r8.hasFlags() ? r8.getFlags() + 31 : 1) * 31) + fqNameIndexes.invoke(Integer.valueOf(r8.getFqName())).intValue();
        if (r8.hasCompanionObjectName()) {
            flags = (flags * 31) + stringIndexes.invoke(Integer.valueOf(r8.getCompanionObjectName())).intValue();
        }
        int typeParameterCount = r8.getTypeParameterCount() - 1;
        int i = 0;
        if (typeParameterCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = r8.getTypeParameter(i2);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                flags = (flags * 31) + hashCode(typeParameter, stringIndexes, fqNameIndexes, typeById);
                if (i2 == typeParameterCount) {
                    break;
                }
                i2++;
            }
        }
        int supertypeCount = r8.getSupertypeCount() - 1;
        if (supertypeCount >= 0) {
            int i3 = 0;
            while (true) {
                ProtoBuf.Type supertype = r8.getSupertype(i3);
                Intrinsics.checkNotNullExpressionValue(supertype, "getSupertype(...)");
                flags = (flags * 31) + hashCode(supertype, stringIndexes, fqNameIndexes, typeById);
                if (i3 == supertypeCount) {
                    break;
                }
                i3++;
            }
        }
        int supertypeIdCount = r8.getSupertypeIdCount() - 1;
        if (supertypeIdCount >= 0) {
            int i4 = 0;
            while (true) {
                flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(r8.getSupertypeId(i4))), stringIndexes, fqNameIndexes, typeById);
                if (i4 == supertypeIdCount) {
                    break;
                }
                i4++;
            }
        }
        int nestedClassNameCount = r8.getNestedClassNameCount() - 1;
        if (nestedClassNameCount >= 0) {
            int i5 = 0;
            while (true) {
                flags = (flags * 31) + stringIndexes.invoke(Integer.valueOf(r8.getNestedClassName(i5))).intValue();
                if (i5 == nestedClassNameCount) {
                    break;
                }
                i5++;
            }
        }
        int contextReceiverTypeCount = r8.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i6 = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = r8.getContextReceiverType(i6);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                flags = (flags * 31) + hashCode(contextReceiverType, stringIndexes, fqNameIndexes, typeById);
                if (i6 == contextReceiverTypeCount) {
                    break;
                }
                i6++;
            }
        }
        int contextReceiverTypeIdCount = r8.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i7 = 0;
            while (true) {
                flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(r8.getContextReceiverTypeId(i7))), stringIndexes, fqNameIndexes, typeById);
                if (i7 == contextReceiverTypeIdCount) {
                    break;
                }
                i7++;
            }
        }
        int constructorCount = r8.getConstructorCount() - 1;
        if (constructorCount >= 0) {
            int i8 = 0;
            while (true) {
                ProtoBuf.Constructor constructor = r8.getConstructor(i8);
                Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
                flags = (flags * 31) + hashCode(constructor, stringIndexes, fqNameIndexes, typeById);
                if (i8 == constructorCount) {
                    break;
                }
                i8++;
            }
        }
        int functionCount = r8.getFunctionCount() - 1;
        if (functionCount >= 0) {
            int i9 = 0;
            while (true) {
                ProtoBuf.Function function = r8.getFunction(i9);
                Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                flags = (flags * 31) + hashCode(function, stringIndexes, fqNameIndexes, typeById);
                if (i9 == functionCount) {
                    break;
                }
                i9++;
            }
        }
        int propertyCount = r8.getPropertyCount() - 1;
        if (propertyCount >= 0) {
            int i10 = 0;
            while (true) {
                ProtoBuf.Property property = r8.getProperty(i10);
                Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                flags = (flags * 31) + hashCode(property, stringIndexes, fqNameIndexes, typeById);
                if (i10 == propertyCount) {
                    break;
                }
                i10++;
            }
        }
        int typeAliasCount = r8.getTypeAliasCount() - 1;
        if (typeAliasCount >= 0) {
            int i11 = 0;
            while (true) {
                ProtoBuf.TypeAlias typeAlias = r8.getTypeAlias(i11);
                Intrinsics.checkNotNullExpressionValue(typeAlias, "getTypeAlias(...)");
                flags = (flags * 31) + hashCode(typeAlias, stringIndexes, fqNameIndexes, typeById);
                if (i11 == typeAliasCount) {
                    break;
                }
                i11++;
            }
        }
        int enumEntryCount = r8.getEnumEntryCount() - 1;
        if (enumEntryCount >= 0) {
            int i12 = 0;
            while (true) {
                ProtoBuf.EnumEntry enumEntry = r8.getEnumEntry(i12);
                Intrinsics.checkNotNullExpressionValue(enumEntry, "getEnumEntry(...)");
                flags = (flags * 31) + hashCode(enumEntry, stringIndexes, fqNameIndexes, typeById);
                if (i12 == enumEntryCount) {
                    break;
                }
                i12++;
            }
        }
        int sealedSubclassFqNameCount = r8.getSealedSubclassFqNameCount() - 1;
        if (sealedSubclassFqNameCount >= 0) {
            int i13 = 0;
            while (true) {
                flags = (flags * 31) + fqNameIndexes.invoke(Integer.valueOf(r8.getSealedSubclassFqName(i13))).intValue();
                if (i13 == sealedSubclassFqNameCount) {
                    break;
                }
                i13++;
            }
        }
        if (r8.hasInlineClassUnderlyingPropertyName()) {
            flags = (flags * 31) + stringIndexes.invoke(Integer.valueOf(r8.getInlineClassUnderlyingPropertyName())).intValue();
        }
        if (r8.hasInlineClassUnderlyingType()) {
            ProtoBuf.Type inlineClassUnderlyingType = r8.getInlineClassUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(inlineClassUnderlyingType, "getInlineClassUnderlyingType(...)");
            flags = (flags * 31) + hashCode(inlineClassUnderlyingType, stringIndexes, fqNameIndexes, typeById);
        }
        if (r8.hasInlineClassUnderlyingTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(r8.getInlineClassUnderlyingTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int multiFieldValueClassUnderlyingNameCount = r8.getMultiFieldValueClassUnderlyingNameCount() - 1;
        if (multiFieldValueClassUnderlyingNameCount >= 0) {
            int i14 = 0;
            while (true) {
                flags = (flags * 31) + stringIndexes.invoke(Integer.valueOf(r8.getMultiFieldValueClassUnderlyingName(i14))).intValue();
                if (i14 == multiFieldValueClassUnderlyingNameCount) {
                    break;
                }
                i14++;
            }
        }
        int multiFieldValueClassUnderlyingTypeCount = r8.getMultiFieldValueClassUnderlyingTypeCount() - 1;
        if (multiFieldValueClassUnderlyingTypeCount >= 0) {
            int i15 = 0;
            while (true) {
                ProtoBuf.Type multiFieldValueClassUnderlyingType = r8.getMultiFieldValueClassUnderlyingType(i15);
                Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingType, "getMultiFieldValueClassUnderlyingType(...)");
                flags = (flags * 31) + hashCode(multiFieldValueClassUnderlyingType, stringIndexes, fqNameIndexes, typeById);
                if (i15 == multiFieldValueClassUnderlyingTypeCount) {
                    break;
                }
                i15++;
            }
        }
        int multiFieldValueClassUnderlyingTypeIdCount = r8.getMultiFieldValueClassUnderlyingTypeIdCount() - 1;
        if (multiFieldValueClassUnderlyingTypeIdCount >= 0) {
            int i16 = 0;
            while (true) {
                flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(r8.getMultiFieldValueClassUnderlyingTypeId(i16))), stringIndexes, fqNameIndexes, typeById);
                if (i16 == multiFieldValueClassUnderlyingTypeIdCount) {
                    break;
                }
                i16++;
            }
        }
        int versionRequirementCount = r8.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            int i17 = 0;
            while (true) {
                flags = (flags * 31) + r8.getVersionRequirement(i17);
                if (i17 == versionRequirementCount) {
                    break;
                }
                i17++;
            }
        }
        if (r8.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = r8.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            flags = (flags * 31) + hashCode(versionRequirementTable, stringIndexes, fqNameIndexes, typeById);
        }
        if (r8.hasExtension(JvmProtoBuf.classModuleName)) {
            Object extension = r8.getExtension(JvmProtoBuf.classModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            flags = (flags * 31) + stringIndexes.invoke(extension).intValue();
        }
        int extensionCount = r8.getExtensionCount(JvmProtoBuf.classLocalVariable) - 1;
        if (extensionCount >= 0) {
            int i18 = 0;
            while (true) {
                Object extension2 = r8.getExtension(JvmProtoBuf.classLocalVariable, i18);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Property) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i18 == extensionCount) {
                    break;
                }
                i18++;
            }
        }
        if (r8.hasExtension(JvmProtoBuf.anonymousObjectOriginName)) {
            Object extension3 = r8.getExtension(JvmProtoBuf.anonymousObjectOriginName);
            Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
            flags = (flags * 31) + stringIndexes.invoke(extension3).intValue();
        }
        if (r8.hasExtension(JvmProtoBuf.jvmClassFlags)) {
            Object extension4 = r8.getExtension(JvmProtoBuf.jvmClassFlags);
            Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
            flags = (flags * 31) + ((Number) extension4).intValue();
        }
        int extensionCount2 = r8.getExtensionCount(JsProtoBuf.classAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i19 = 0;
            while (true) {
                Object extension5 = r8.getExtension(JsProtoBuf.classAnnotation, i19);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension5, stringIndexes, fqNameIndexes, typeById);
                if (i19 == extensionCount2) {
                    break;
                }
                i19++;
            }
        }
        if (r8.hasExtension(JsProtoBuf.classContainingFileId)) {
            Object extension6 = r8.getExtension(JsProtoBuf.classContainingFileId);
            Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
            flags = (flags * 31) + ((Number) extension6).intValue();
        }
        if (r8.hasExtension(JavaClassProtoBuf.isPackagePrivateClass)) {
            flags = (flags * 31) + ((Boolean) r8.getExtension(JavaClassProtoBuf.isPackagePrivateClass)).hashCode();
        }
        int extensionCount3 = r8.getExtensionCount(BuiltInsProtoBuf.classAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i20 = 0;
            while (true) {
                Object extension7 = r8.getExtension(BuiltInsProtoBuf.classAnnotation, i20);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension7, stringIndexes, fqNameIndexes, typeById);
                if (i20 == extensionCount3) {
                    break;
                }
                i20++;
            }
        }
        int extensionCount4 = r8.getExtensionCount(KlibMetadataProtoBuf.classAnnotation) - 1;
        if (extensionCount4 >= 0) {
            while (true) {
                Object extension8 = r8.getExtension(KlibMetadataProtoBuf.classAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension8, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount4) {
                    break;
                }
                i++;
            }
        }
        return flags;
    }

    public static final int hashCode(ProtoBuf.Function function, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(function, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = function.hasFlags() ? function.getFlags() + 31 : 1;
        if (function.hasOldFlags()) {
            flags = (flags * 31) + function.getOldFlags();
        }
        int iIntValue = (flags * 31) + stringIndexes.invoke(Integer.valueOf(function.getName())).intValue();
        if (function.hasReturnType()) {
            ProtoBuf.Type returnType = function.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            iIntValue = (iIntValue * 31) + hashCode(returnType, stringIndexes, fqNameIndexes, typeById);
        }
        if (function.hasReturnTypeId()) {
            iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(function.getReturnTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int typeParameterCount = function.getTypeParameterCount() - 1;
        int i = 0;
        if (typeParameterCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = function.getTypeParameter(i2);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                iIntValue = (iIntValue * 31) + hashCode(typeParameter, stringIndexes, fqNameIndexes, typeById);
                if (i2 == typeParameterCount) {
                    break;
                }
                i2++;
            }
        }
        if (function.hasReceiverType()) {
            ProtoBuf.Type receiverType = function.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType, "getReceiverType(...)");
            iIntValue = (iIntValue * 31) + hashCode(receiverType, stringIndexes, fqNameIndexes, typeById);
        }
        if (function.hasReceiverTypeId()) {
            iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(function.getReceiverTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int contextReceiverTypeCount = function.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i3 = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = function.getContextReceiverType(i3);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                iIntValue = (iIntValue * 31) + hashCode(contextReceiverType, stringIndexes, fqNameIndexes, typeById);
                if (i3 == contextReceiverTypeCount) {
                    break;
                }
                i3++;
            }
        }
        int contextReceiverTypeIdCount = function.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i4 = 0;
            while (true) {
                iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(function.getContextReceiverTypeId(i4))), stringIndexes, fqNameIndexes, typeById);
                if (i4 == contextReceiverTypeIdCount) {
                    break;
                }
                i4++;
            }
        }
        int valueParameterCount = function.getValueParameterCount() - 1;
        if (valueParameterCount >= 0) {
            int i5 = 0;
            while (true) {
                ProtoBuf.ValueParameter valueParameter = function.getValueParameter(i5);
                Intrinsics.checkNotNullExpressionValue(valueParameter, "getValueParameter(...)");
                iIntValue = (iIntValue * 31) + hashCode(valueParameter, stringIndexes, fqNameIndexes, typeById);
                if (i5 == valueParameterCount) {
                    break;
                }
                i5++;
            }
        }
        int versionRequirementCount = function.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            int i6 = 0;
            while (true) {
                iIntValue = (iIntValue * 31) + function.getVersionRequirement(i6);
                if (i6 == versionRequirementCount) {
                    break;
                }
                i6++;
            }
        }
        if (function.hasContract()) {
            ProtoBuf.Contract contract = function.getContract();
            Intrinsics.checkNotNullExpressionValue(contract, "getContract(...)");
            iIntValue = (iIntValue * 31) + hashCode(contract, stringIndexes, fqNameIndexes, typeById);
        }
        if (function.hasExtension(JvmProtoBuf.methodSignature)) {
            Object extension = function.getExtension(JvmProtoBuf.methodSignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            iIntValue = (iIntValue * 31) + hashCode((JvmProtoBuf.JvmMethodSignature) extension, stringIndexes, fqNameIndexes, typeById);
        }
        if (function.hasExtension(JvmProtoBuf.lambdaClassOriginName)) {
            Object extension2 = function.getExtension(JvmProtoBuf.lambdaClassOriginName);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            iIntValue = (iIntValue * 31) + stringIndexes.invoke(extension2).intValue();
        }
        int extensionCount = function.getExtensionCount(JsProtoBuf.functionAnnotation) - 1;
        if (extensionCount >= 0) {
            int i7 = 0;
            while (true) {
                Object extension3 = function.getExtension(JsProtoBuf.functionAnnotation, i7);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i7 == extensionCount) {
                    break;
                }
                i7++;
            }
        }
        if (function.hasExtension(JsProtoBuf.functionContainingFileId)) {
            Object extension4 = function.getExtension(JsProtoBuf.functionContainingFileId);
            Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
            iIntValue = (iIntValue * 31) + ((Number) extension4).intValue();
        }
        if (function.hasExtension(JavaClassProtoBuf.isStaticMethod)) {
            iIntValue = (iIntValue * 31) + ((Boolean) function.getExtension(JavaClassProtoBuf.isStaticMethod)).hashCode();
        }
        if (function.hasExtension(JavaClassProtoBuf.isPackagePrivateMethod)) {
            iIntValue = (iIntValue * 31) + ((Boolean) function.getExtension(JavaClassProtoBuf.isPackagePrivateMethod)).hashCode();
        }
        int extensionCount2 = function.getExtensionCount(BuiltInsProtoBuf.functionAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i8 = 0;
            while (true) {
                Object extension5 = function.getExtension(BuiltInsProtoBuf.functionAnnotation, i8);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension5, stringIndexes, fqNameIndexes, typeById);
                if (i8 == extensionCount2) {
                    break;
                }
                i8++;
            }
        }
        int extensionCount3 = function.getExtensionCount(KlibMetadataProtoBuf.functionAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i9 = 0;
            while (true) {
                Object extension6 = function.getExtension(KlibMetadataProtoBuf.functionAnnotation, i9);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension6, stringIndexes, fqNameIndexes, typeById);
                if (i9 == extensionCount3) {
                    break;
                }
                i9++;
            }
        }
        int extensionCount4 = function.getExtensionCount(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation) - 1;
        if (extensionCount4 >= 0) {
            while (true) {
                Object extension7 = function.getExtension(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension7, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount4) {
                    break;
                }
                i++;
            }
        }
        return iIntValue;
    }

    public static final int hashCode(ProtoBuf.Property property, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(property, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = property.hasFlags() ? property.getFlags() + 31 : 1;
        if (property.hasOldFlags()) {
            flags = (flags * 31) + property.getOldFlags();
        }
        int iIntValue = (flags * 31) + stringIndexes.invoke(Integer.valueOf(property.getName())).intValue();
        if (property.hasReturnType()) {
            ProtoBuf.Type returnType = property.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            iIntValue = (iIntValue * 31) + hashCode(returnType, stringIndexes, fqNameIndexes, typeById);
        }
        if (property.hasReturnTypeId()) {
            iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(property.getReturnTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int typeParameterCount = property.getTypeParameterCount() - 1;
        int i = 0;
        if (typeParameterCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = property.getTypeParameter(i2);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                iIntValue = (iIntValue * 31) + hashCode(typeParameter, stringIndexes, fqNameIndexes, typeById);
                if (i2 == typeParameterCount) {
                    break;
                }
                i2++;
            }
        }
        if (property.hasReceiverType()) {
            ProtoBuf.Type receiverType = property.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType, "getReceiverType(...)");
            iIntValue = (iIntValue * 31) + hashCode(receiverType, stringIndexes, fqNameIndexes, typeById);
        }
        if (property.hasReceiverTypeId()) {
            iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(property.getReceiverTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int contextReceiverTypeCount = property.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i3 = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = property.getContextReceiverType(i3);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                iIntValue = (iIntValue * 31) + hashCode(contextReceiverType, stringIndexes, fqNameIndexes, typeById);
                if (i3 == contextReceiverTypeCount) {
                    break;
                }
                i3++;
            }
        }
        int contextReceiverTypeIdCount = property.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i4 = 0;
            while (true) {
                iIntValue = (iIntValue * 31) + hashCode(typeById.invoke(Integer.valueOf(property.getContextReceiverTypeId(i4))), stringIndexes, fqNameIndexes, typeById);
                if (i4 == contextReceiverTypeIdCount) {
                    break;
                }
                i4++;
            }
        }
        if (property.hasSetterValueParameter()) {
            ProtoBuf.ValueParameter setterValueParameter = property.getSetterValueParameter();
            Intrinsics.checkNotNullExpressionValue(setterValueParameter, "getSetterValueParameter(...)");
            iIntValue = (iIntValue * 31) + hashCode(setterValueParameter, stringIndexes, fqNameIndexes, typeById);
        }
        if (property.hasGetterFlags()) {
            iIntValue = (iIntValue * 31) + property.getGetterFlags();
        }
        if (property.hasSetterFlags()) {
            iIntValue = (iIntValue * 31) + property.getSetterFlags();
        }
        int versionRequirementCount = property.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            int i5 = 0;
            while (true) {
                iIntValue = (iIntValue * 31) + property.getVersionRequirement(i5);
                if (i5 == versionRequirementCount) {
                    break;
                }
                i5++;
            }
        }
        if (property.hasExtension(JvmProtoBuf.propertySignature)) {
            Object extension = property.getExtension(JvmProtoBuf.propertySignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            iIntValue = (iIntValue * 31) + hashCode((JvmProtoBuf.JvmPropertySignature) extension, stringIndexes, fqNameIndexes, typeById);
        }
        if (property.hasExtension(JvmProtoBuf.flags)) {
            Object extension2 = property.getExtension(JvmProtoBuf.flags);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            iIntValue = (iIntValue * 31) + ((Number) extension2).intValue();
        }
        int extensionCount = property.getExtensionCount(JsProtoBuf.propertyAnnotation) - 1;
        if (extensionCount >= 0) {
            int i6 = 0;
            while (true) {
                Object extension3 = property.getExtension(JsProtoBuf.propertyAnnotation, i6);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i6 == extensionCount) {
                    break;
                }
                i6++;
            }
        }
        int extensionCount2 = property.getExtensionCount(JsProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i7 = 0;
            while (true) {
                Object extension4 = property.getExtension(JsProtoBuf.propertyGetterAnnotation, i7);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension4, stringIndexes, fqNameIndexes, typeById);
                if (i7 == extensionCount2) {
                    break;
                }
                i7++;
            }
        }
        int extensionCount3 = property.getExtensionCount(JsProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i8 = 0;
            while (true) {
                Object extension5 = property.getExtension(JsProtoBuf.propertySetterAnnotation, i8);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension5, stringIndexes, fqNameIndexes, typeById);
                if (i8 == extensionCount3) {
                    break;
                }
                i8++;
            }
        }
        if (property.hasExtension(JsProtoBuf.compileTimeValue)) {
            Object extension6 = property.getExtension(JsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
            iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation.Argument.Value) extension6, stringIndexes, fqNameIndexes, typeById);
        }
        if (property.hasExtension(JsProtoBuf.propertyContainingFileId)) {
            Object extension7 = property.getExtension(JsProtoBuf.propertyContainingFileId);
            Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
            iIntValue = (iIntValue * 31) + ((Number) extension7).intValue();
        }
        if (property.hasExtension(JavaClassProtoBuf.isStaticField)) {
            iIntValue = (iIntValue * 31) + ((Boolean) property.getExtension(JavaClassProtoBuf.isStaticField)).hashCode();
        }
        if (property.hasExtension(JavaClassProtoBuf.isPackagePrivateField)) {
            iIntValue = (iIntValue * 31) + ((Boolean) property.getExtension(JavaClassProtoBuf.isPackagePrivateField)).hashCode();
        }
        int extensionCount4 = property.getExtensionCount(BuiltInsProtoBuf.propertyAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i9 = 0;
            while (true) {
                Object extension8 = property.getExtension(BuiltInsProtoBuf.propertyAnnotation, i9);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension8, stringIndexes, fqNameIndexes, typeById);
                if (i9 == extensionCount4) {
                    break;
                }
                i9++;
            }
        }
        int extensionCount5 = property.getExtensionCount(BuiltInsProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount5 >= 0) {
            int i10 = 0;
            while (true) {
                Object extension9 = property.getExtension(BuiltInsProtoBuf.propertyGetterAnnotation, i10);
                Intrinsics.checkNotNullExpressionValue(extension9, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension9, stringIndexes, fqNameIndexes, typeById);
                if (i10 == extensionCount5) {
                    break;
                }
                i10++;
            }
        }
        int extensionCount6 = property.getExtensionCount(BuiltInsProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount6 >= 0) {
            int i11 = 0;
            while (true) {
                Object extension10 = property.getExtension(BuiltInsProtoBuf.propertySetterAnnotation, i11);
                Intrinsics.checkNotNullExpressionValue(extension10, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension10, stringIndexes, fqNameIndexes, typeById);
                if (i11 == extensionCount6) {
                    break;
                }
                i11++;
            }
        }
        if (property.hasExtension(BuiltInsProtoBuf.compileTimeValue)) {
            Object extension11 = property.getExtension(BuiltInsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension11, "getExtension(...)");
            iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation.Argument.Value) extension11, stringIndexes, fqNameIndexes, typeById);
        }
        int extensionCount7 = property.getExtensionCount(KlibMetadataProtoBuf.propertyAnnotation) - 1;
        if (extensionCount7 >= 0) {
            int i12 = 0;
            while (true) {
                Object extension12 = property.getExtension(KlibMetadataProtoBuf.propertyAnnotation, i12);
                Intrinsics.checkNotNullExpressionValue(extension12, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension12, stringIndexes, fqNameIndexes, typeById);
                if (i12 == extensionCount7) {
                    break;
                }
                i12++;
            }
        }
        int extensionCount8 = property.getExtensionCount(KlibMetadataProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount8 >= 0) {
            int i13 = 0;
            while (true) {
                Object extension13 = property.getExtension(KlibMetadataProtoBuf.propertyGetterAnnotation, i13);
                Intrinsics.checkNotNullExpressionValue(extension13, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension13, stringIndexes, fqNameIndexes, typeById);
                if (i13 == extensionCount8) {
                    break;
                }
                i13++;
            }
        }
        int extensionCount9 = property.getExtensionCount(KlibMetadataProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount9 >= 0) {
            int i14 = 0;
            while (true) {
                Object extension14 = property.getExtension(KlibMetadataProtoBuf.propertySetterAnnotation, i14);
                Intrinsics.checkNotNullExpressionValue(extension14, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension14, stringIndexes, fqNameIndexes, typeById);
                if (i14 == extensionCount9) {
                    break;
                }
                i14++;
            }
        }
        int extensionCount10 = property.getExtensionCount(KlibMetadataProtoBuf.propertyBackingFieldAnnotation) - 1;
        if (extensionCount10 >= 0) {
            int i15 = 0;
            while (true) {
                Object extension15 = property.getExtension(KlibMetadataProtoBuf.propertyBackingFieldAnnotation, i15);
                Intrinsics.checkNotNullExpressionValue(extension15, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension15, stringIndexes, fqNameIndexes, typeById);
                if (i15 == extensionCount10) {
                    break;
                }
                i15++;
            }
        }
        int extensionCount11 = property.getExtensionCount(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation) - 1;
        if (extensionCount11 >= 0) {
            int i16 = 0;
            while (true) {
                Object extension16 = property.getExtension(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation, i16);
                Intrinsics.checkNotNullExpressionValue(extension16, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension16, stringIndexes, fqNameIndexes, typeById);
                if (i16 == extensionCount11) {
                    break;
                }
                i16++;
            }
        }
        int extensionCount12 = property.getExtensionCount(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation) - 1;
        if (extensionCount12 >= 0) {
            while (true) {
                Object extension17 = property.getExtension(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension17, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension17, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount12) {
                    break;
                }
                i++;
            }
        }
        if (!property.hasExtension(KlibMetadataProtoBuf.compileTimeValue)) {
            return iIntValue;
        }
        Object extension18 = property.getExtension(KlibMetadataProtoBuf.compileTimeValue);
        Intrinsics.checkNotNullExpressionValue(extension18, "getExtension(...)");
        return (iIntValue * 31) + hashCode((ProtoBuf.Annotation.Argument.Value) extension18, stringIndexes, fqNameIndexes, typeById);
    }

    public static final int hashCode(ProtoBuf.TypeAlias typeAlias, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(typeAlias, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = ((typeAlias.hasFlags() ? typeAlias.getFlags() + 31 : 1) * 31) + stringIndexes.invoke(Integer.valueOf(typeAlias.getName())).intValue();
        int typeParameterCount = typeAlias.getTypeParameterCount() - 1;
        int i = 0;
        if (typeParameterCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = typeAlias.getTypeParameter(i2);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                flags = (flags * 31) + hashCode(typeParameter, stringIndexes, fqNameIndexes, typeById);
                if (i2 == typeParameterCount) {
                    break;
                }
                i2++;
            }
        }
        if (typeAlias.hasUnderlyingType()) {
            ProtoBuf.Type underlyingType = typeAlias.getUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(underlyingType, "getUnderlyingType(...)");
            flags = (flags * 31) + hashCode(underlyingType, stringIndexes, fqNameIndexes, typeById);
        }
        if (typeAlias.hasUnderlyingTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(typeAlias.getUnderlyingTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        if (typeAlias.hasExpandedType()) {
            ProtoBuf.Type expandedType = typeAlias.getExpandedType();
            Intrinsics.checkNotNullExpressionValue(expandedType, "getExpandedType(...)");
            flags = (flags * 31) + hashCode(expandedType, stringIndexes, fqNameIndexes, typeById);
        }
        if (typeAlias.hasExpandedTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(typeAlias.getExpandedTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int annotationCount = typeAlias.getAnnotationCount() - 1;
        if (annotationCount >= 0) {
            int i3 = 0;
            while (true) {
                ProtoBuf.Annotation annotation = typeAlias.getAnnotation(i3);
                Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
                flags = (flags * 31) + hashCode(annotation, stringIndexes, fqNameIndexes, typeById);
                if (i3 == annotationCount) {
                    break;
                }
                i3++;
            }
        }
        int versionRequirementCount = typeAlias.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            while (true) {
                flags = (flags * 31) + typeAlias.getVersionRequirement(i);
                if (i == versionRequirementCount) {
                    break;
                }
                i++;
            }
        }
        return flags;
    }

    public static final int hashCode(ProtoBuf.VersionRequirementTable versionRequirementTable, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(versionRequirementTable, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iHashCode = 1;
        int requirementCount = versionRequirementTable.getRequirementCount() - 1;
        if (requirementCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.VersionRequirement requirement = versionRequirementTable.getRequirement(i);
                Intrinsics.checkNotNullExpressionValue(requirement, "getRequirement(...)");
                iHashCode = (iHashCode * 31) + hashCode(requirement, stringIndexes, fqNameIndexes, typeById);
                if (i == requirementCount) {
                    break;
                }
                i++;
            }
        }
        return iHashCode;
    }

    public static final int hashCode(ProtoBuf.TypeParameter typeParameter, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(typeParameter, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int id2 = ((typeParameter.getId() + 31) * 31) + stringIndexes.invoke(Integer.valueOf(typeParameter.getName())).intValue();
        if (typeParameter.hasReified()) {
            id2 = (id2 * 31) + Boolean.hashCode(typeParameter.getReified());
        }
        if (typeParameter.hasVariance()) {
            id2 = (id2 * 31) + typeParameter.getVariance().hashCode();
        }
        int upperBoundCount = typeParameter.getUpperBoundCount() - 1;
        int i = 0;
        if (upperBoundCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.Type upperBound = typeParameter.getUpperBound(i2);
                Intrinsics.checkNotNullExpressionValue(upperBound, "getUpperBound(...)");
                id2 = (id2 * 31) + hashCode(upperBound, stringIndexes, fqNameIndexes, typeById);
                if (i2 == upperBoundCount) {
                    break;
                }
                i2++;
            }
        }
        int upperBoundIdCount = typeParameter.getUpperBoundIdCount() - 1;
        if (upperBoundIdCount >= 0) {
            int i3 = 0;
            while (true) {
                id2 = (id2 * 31) + hashCode(typeById.invoke(Integer.valueOf(typeParameter.getUpperBoundId(i3))), stringIndexes, fqNameIndexes, typeById);
                if (i3 == upperBoundIdCount) {
                    break;
                }
                i3++;
            }
        }
        int extensionCount = typeParameter.getExtensionCount(JvmProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount >= 0) {
            int i4 = 0;
            while (true) {
                Object extension = typeParameter.getExtension(JvmProtoBuf.typeParameterAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                id2 = (id2 * 31) + hashCode((ProtoBuf.Annotation) extension, stringIndexes, fqNameIndexes, typeById);
                if (i4 == extensionCount) {
                    break;
                }
                i4++;
            }
        }
        int extensionCount2 = typeParameter.getExtensionCount(JsProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i5 = 0;
            while (true) {
                Object extension2 = typeParameter.getExtension(JsProtoBuf.typeParameterAnnotation, i5);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                id2 = (id2 * 31) + hashCode((ProtoBuf.Annotation) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i5 == extensionCount2) {
                    break;
                }
                i5++;
            }
        }
        int extensionCount3 = typeParameter.getExtensionCount(BuiltInsProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i6 = 0;
            while (true) {
                Object extension3 = typeParameter.getExtension(BuiltInsProtoBuf.typeParameterAnnotation, i6);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                id2 = (id2 * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i6 == extensionCount3) {
                    break;
                }
                i6++;
            }
        }
        int extensionCount4 = typeParameter.getExtensionCount(KlibMetadataProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount4 >= 0) {
            while (true) {
                Object extension4 = typeParameter.getExtension(KlibMetadataProtoBuf.typeParameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                id2 = (id2 * 31) + hashCode((ProtoBuf.Annotation) extension4, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount4) {
                    break;
                }
                i++;
            }
        }
        return id2;
    }

    public static final int hashCode(ProtoBuf.Type type, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(type, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int argumentCount = type.getArgumentCount() - 1;
        int i = 0;
        int iHashCode = 1;
        if (argumentCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.Type.Argument argument = type.getArgument(i2);
                Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
                iHashCode = (iHashCode * 31) + hashCode(argument, stringIndexes, fqNameIndexes, typeById);
                if (i2 == argumentCount) {
                    break;
                }
                i2++;
            }
        }
        if (type.hasNullable()) {
            iHashCode = (iHashCode * 31) + Boolean.hashCode(type.getNullable());
        }
        if (type.hasFlexibleTypeCapabilitiesId()) {
            iHashCode = (iHashCode * 31) + stringIndexes.invoke(Integer.valueOf(type.getFlexibleTypeCapabilitiesId())).intValue();
        }
        if (type.hasFlexibleUpperBound()) {
            ProtoBuf.Type flexibleUpperBound = type.getFlexibleUpperBound();
            Intrinsics.checkNotNullExpressionValue(flexibleUpperBound, "getFlexibleUpperBound(...)");
            iHashCode = (iHashCode * 31) + hashCode(flexibleUpperBound, stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasFlexibleUpperBoundId()) {
            iHashCode = (iHashCode * 31) + hashCode(typeById.invoke(Integer.valueOf(type.getFlexibleUpperBoundId())), stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasClassName()) {
            iHashCode = (iHashCode * 31) + fqNameIndexes.invoke(Integer.valueOf(type.getClassName())).intValue();
        }
        if (type.hasTypeParameter()) {
            iHashCode = (iHashCode * 31) + type.getTypeParameter();
        }
        if (type.hasTypeParameterName()) {
            iHashCode = (iHashCode * 31) + stringIndexes.invoke(Integer.valueOf(type.getTypeParameterName())).intValue();
        }
        if (type.hasTypeAliasName()) {
            iHashCode = (iHashCode * 31) + fqNameIndexes.invoke(Integer.valueOf(type.getTypeAliasName())).intValue();
        }
        if (type.hasOuterType()) {
            ProtoBuf.Type outerType = type.getOuterType();
            Intrinsics.checkNotNullExpressionValue(outerType, "getOuterType(...)");
            iHashCode = (iHashCode * 31) + hashCode(outerType, stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasOuterTypeId()) {
            iHashCode = (iHashCode * 31) + hashCode(typeById.invoke(Integer.valueOf(type.getOuterTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasAbbreviatedType()) {
            ProtoBuf.Type abbreviatedType = type.getAbbreviatedType();
            Intrinsics.checkNotNullExpressionValue(abbreviatedType, "getAbbreviatedType(...)");
            iHashCode = (iHashCode * 31) + hashCode(abbreviatedType, stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasAbbreviatedTypeId()) {
            iHashCode = (iHashCode * 31) + hashCode(typeById.invoke(Integer.valueOf(type.getAbbreviatedTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        if (type.hasFlags()) {
            iHashCode = (iHashCode * 31) + type.getFlags();
        }
        int extensionCount = type.getExtensionCount(JvmProtoBuf.typeAnnotation) - 1;
        if (extensionCount >= 0) {
            int i3 = 0;
            while (true) {
                Object extension = type.getExtension(JvmProtoBuf.typeAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                iHashCode = (iHashCode * 31) + hashCode((ProtoBuf.Annotation) extension, stringIndexes, fqNameIndexes, typeById);
                if (i3 == extensionCount) {
                    break;
                }
                i3++;
            }
        }
        if (type.hasExtension(JvmProtoBuf.isRaw)) {
            iHashCode = (iHashCode * 31) + ((Boolean) type.getExtension(JvmProtoBuf.isRaw)).hashCode();
        }
        int extensionCount2 = type.getExtensionCount(JsProtoBuf.typeAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension2 = type.getExtension(JsProtoBuf.typeAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                iHashCode = (iHashCode * 31) + hashCode((ProtoBuf.Annotation) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i4 == extensionCount2) {
                    break;
                }
                i4++;
            }
        }
        int extensionCount3 = type.getExtensionCount(BuiltInsProtoBuf.typeAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i5 = 0;
            while (true) {
                Object extension3 = type.getExtension(BuiltInsProtoBuf.typeAnnotation, i5);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                iHashCode = (iHashCode * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i5 == extensionCount3) {
                    break;
                }
                i5++;
            }
        }
        int extensionCount4 = type.getExtensionCount(KlibMetadataProtoBuf.typeAnnotation) - 1;
        if (extensionCount4 >= 0) {
            while (true) {
                Object extension4 = type.getExtension(KlibMetadataProtoBuf.typeAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                iHashCode = (iHashCode * 31) + hashCode((ProtoBuf.Annotation) extension4, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount4) {
                    break;
                }
                i++;
            }
        }
        return iHashCode;
    }

    public static final int hashCode(ProtoBuf.Constructor constructor, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(constructor, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = constructor.hasFlags() ? constructor.getFlags() + 31 : 1;
        int valueParameterCount = constructor.getValueParameterCount() - 1;
        int i = 0;
        if (valueParameterCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.ValueParameter valueParameter = constructor.getValueParameter(i2);
                Intrinsics.checkNotNullExpressionValue(valueParameter, "getValueParameter(...)");
                flags = (flags * 31) + hashCode(valueParameter, stringIndexes, fqNameIndexes, typeById);
                if (i2 == valueParameterCount) {
                    break;
                }
                i2++;
            }
        }
        int versionRequirementCount = constructor.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            int i3 = 0;
            while (true) {
                flags = (flags * 31) + constructor.getVersionRequirement(i3);
                if (i3 == versionRequirementCount) {
                    break;
                }
                i3++;
            }
        }
        if (constructor.hasExtension(JvmProtoBuf.constructorSignature)) {
            Object extension = constructor.getExtension(JvmProtoBuf.constructorSignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            flags = (flags * 31) + hashCode((JvmProtoBuf.JvmMethodSignature) extension, stringIndexes, fqNameIndexes, typeById);
        }
        int extensionCount = constructor.getExtensionCount(JsProtoBuf.constructorAnnotation) - 1;
        if (extensionCount >= 0) {
            int i4 = 0;
            while (true) {
                Object extension2 = constructor.getExtension(JsProtoBuf.constructorAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i4 == extensionCount) {
                    break;
                }
                i4++;
            }
        }
        if (constructor.hasExtension(JavaClassProtoBuf.isPackagePrivateConstructor)) {
            flags = (flags * 31) + ((Boolean) constructor.getExtension(JavaClassProtoBuf.isPackagePrivateConstructor)).hashCode();
        }
        int extensionCount2 = constructor.getExtensionCount(BuiltInsProtoBuf.constructorAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i5 = 0;
            while (true) {
                Object extension3 = constructor.getExtension(BuiltInsProtoBuf.constructorAnnotation, i5);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i5 == extensionCount2) {
                    break;
                }
                i5++;
            }
        }
        int extensionCount3 = constructor.getExtensionCount(KlibMetadataProtoBuf.constructorAnnotation) - 1;
        if (extensionCount3 >= 0) {
            while (true) {
                Object extension4 = constructor.getExtension(KlibMetadataProtoBuf.constructorAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension4, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount3) {
                    break;
                }
                i++;
            }
        }
        return flags;
    }

    public static final int hashCode(ProtoBuf.EnumEntry enumEntry, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(enumEntry, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iIntValue = enumEntry.hasName() ? stringIndexes.invoke(Integer.valueOf(enumEntry.getName())).intValue() + 31 : 1;
        int extensionCount = enumEntry.getExtensionCount(JsProtoBuf.enumEntryAnnotation) - 1;
        int i = 0;
        if (extensionCount >= 0) {
            int i2 = 0;
            while (true) {
                Object extension = enumEntry.getExtension(JsProtoBuf.enumEntryAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension, stringIndexes, fqNameIndexes, typeById);
                if (i2 == extensionCount) {
                    break;
                }
                i2++;
            }
        }
        int extensionCount2 = enumEntry.getExtensionCount(BuiltInsProtoBuf.enumEntryAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension2 = enumEntry.getExtension(BuiltInsProtoBuf.enumEntryAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i3 == extensionCount2) {
                    break;
                }
                i3++;
            }
        }
        int extensionCount3 = enumEntry.getExtensionCount(KlibMetadataProtoBuf.enumEntryAnnotation) - 1;
        if (extensionCount3 >= 0) {
            while (true) {
                Object extension3 = enumEntry.getExtension(KlibMetadataProtoBuf.enumEntryAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                iIntValue = (iIntValue * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount3) {
                    break;
                }
                i++;
            }
        }
        if (!enumEntry.hasExtension(KlibMetadataProtoBuf.enumEntryOrdinal)) {
            return iIntValue;
        }
        Object extension4 = enumEntry.getExtension(KlibMetadataProtoBuf.enumEntryOrdinal);
        Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
        return (iIntValue * 31) + ((Number) extension4).intValue();
    }

    public static final int hashCode(ProtoBuf.Annotation annotation, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(annotation, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iIntValue = fqNameIndexes.invoke(Integer.valueOf(annotation.getId())).intValue() + 31;
        int argumentCount = annotation.getArgumentCount() - 1;
        if (argumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Annotation.Argument argument = annotation.getArgument(i);
                Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
                iIntValue = (iIntValue * 31) + hashCode(argument, stringIndexes, fqNameIndexes, typeById);
                if (i == argumentCount) {
                    break;
                }
                i++;
            }
        }
        return iIntValue;
    }

    public static final int hashCode(ProtoBuf.ValueParameter valueParameter, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(valueParameter, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = ((valueParameter.hasFlags() ? valueParameter.getFlags() + 31 : 1) * 31) + stringIndexes.invoke(Integer.valueOf(valueParameter.getName())).intValue();
        if (valueParameter.hasType()) {
            ProtoBuf.Type type = valueParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            flags = (flags * 31) + hashCode(type, stringIndexes, fqNameIndexes, typeById);
        }
        if (valueParameter.hasTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(valueParameter.getTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        if (valueParameter.hasVarargElementType()) {
            ProtoBuf.Type varargElementType = valueParameter.getVarargElementType();
            Intrinsics.checkNotNullExpressionValue(varargElementType, "getVarargElementType(...)");
            flags = (flags * 31) + hashCode(varargElementType, stringIndexes, fqNameIndexes, typeById);
        }
        if (valueParameter.hasVarargElementTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(valueParameter.getVarargElementTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int extensionCount = valueParameter.getExtensionCount(JsProtoBuf.parameterAnnotation) - 1;
        int i = 0;
        if (extensionCount >= 0) {
            int i2 = 0;
            while (true) {
                Object extension = valueParameter.getExtension(JsProtoBuf.parameterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension, stringIndexes, fqNameIndexes, typeById);
                if (i2 == extensionCount) {
                    break;
                }
                i2++;
            }
        }
        int extensionCount2 = valueParameter.getExtensionCount(BuiltInsProtoBuf.parameterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension2 = valueParameter.getExtension(BuiltInsProtoBuf.parameterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension2, stringIndexes, fqNameIndexes, typeById);
                if (i3 == extensionCount2) {
                    break;
                }
                i3++;
            }
        }
        int extensionCount3 = valueParameter.getExtensionCount(KlibMetadataProtoBuf.parameterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            while (true) {
                Object extension3 = valueParameter.getExtension(KlibMetadataProtoBuf.parameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                flags = (flags * 31) + hashCode((ProtoBuf.Annotation) extension3, stringIndexes, fqNameIndexes, typeById);
                if (i == extensionCount3) {
                    break;
                }
                i++;
            }
        }
        return flags;
    }

    public static final int hashCode(ProtoBuf.Contract contract, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(contract, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iHashCode = 1;
        int effectCount = contract.getEffectCount() - 1;
        if (effectCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Effect effect = contract.getEffect(i);
                Intrinsics.checkNotNullExpressionValue(effect, "getEffect(...)");
                iHashCode = (iHashCode * 31) + hashCode(effect, stringIndexes, fqNameIndexes, typeById);
                if (i == effectCount) {
                    break;
                }
                i++;
            }
        }
        return iHashCode;
    }

    public static final int hashCode(JvmProtoBuf.JvmMethodSignature jvmMethodSignature, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(jvmMethodSignature, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iIntValue = jvmMethodSignature.hasName() ? 31 + stringIndexes.invoke(Integer.valueOf(jvmMethodSignature.getName())).intValue() : 1;
        return jvmMethodSignature.hasDesc() ? (iIntValue * 31) + stringIndexes.invoke(Integer.valueOf(jvmMethodSignature.getDesc())).intValue() : iIntValue;
    }

    public static final int hashCode(JvmProtoBuf.JvmPropertySignature jvmPropertySignature, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        int iHashCode;
        Intrinsics.checkNotNullParameter(jvmPropertySignature, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        if (jvmPropertySignature.hasField()) {
            JvmProtoBuf.JvmFieldSignature field = jvmPropertySignature.getField();
            Intrinsics.checkNotNullExpressionValue(field, "getField(...)");
            iHashCode = 31 + hashCode(field, stringIndexes, fqNameIndexes, typeById);
        } else {
            iHashCode = 1;
        }
        if (jvmPropertySignature.hasSyntheticMethod()) {
            JvmProtoBuf.JvmMethodSignature syntheticMethod = jvmPropertySignature.getSyntheticMethod();
            Intrinsics.checkNotNullExpressionValue(syntheticMethod, "getSyntheticMethod(...)");
            iHashCode = (iHashCode * 31) + hashCode(syntheticMethod, stringIndexes, fqNameIndexes, typeById);
        }
        if (jvmPropertySignature.hasGetter()) {
            JvmProtoBuf.JvmMethodSignature getter = jvmPropertySignature.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
            iHashCode = (iHashCode * 31) + hashCode(getter, stringIndexes, fqNameIndexes, typeById);
        }
        if (jvmPropertySignature.hasSetter()) {
            JvmProtoBuf.JvmMethodSignature setter = jvmPropertySignature.getSetter();
            Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
            iHashCode = (iHashCode * 31) + hashCode(setter, stringIndexes, fqNameIndexes, typeById);
        }
        if (!jvmPropertySignature.hasDelegateMethod()) {
            return iHashCode;
        }
        JvmProtoBuf.JvmMethodSignature delegateMethod = jvmPropertySignature.getDelegateMethod();
        Intrinsics.checkNotNullExpressionValue(delegateMethod, "getDelegateMethod(...)");
        return (iHashCode * 31) + hashCode(delegateMethod, stringIndexes, fqNameIndexes, typeById);
    }

    public static final int hashCode(ProtoBuf.Annotation.Argument.Value value, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(value, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iHashCode = value.hasType() ? value.getType().hashCode() + 31 : 1;
        if (value.hasIntValue()) {
            iHashCode = (iHashCode * 31) + Long.hashCode(value.getIntValue());
        }
        if (value.hasFloatValue()) {
            iHashCode = (iHashCode * 31) + Float.hashCode(value.getFloatValue());
        }
        if (value.hasDoubleValue()) {
            iHashCode = (iHashCode * 31) + Double.hashCode(value.getDoubleValue());
        }
        if (value.hasStringValue()) {
            iHashCode = (iHashCode * 31) + stringIndexes.invoke(Integer.valueOf(value.getStringValue())).intValue();
        }
        if (value.hasClassId()) {
            iHashCode = (iHashCode * 31) + fqNameIndexes.invoke(Integer.valueOf(value.getClassId())).intValue();
        }
        if (value.hasEnumValueId()) {
            iHashCode = (iHashCode * 31) + stringIndexes.invoke(Integer.valueOf(value.getEnumValueId())).intValue();
        }
        if (value.hasAnnotation()) {
            ProtoBuf.Annotation annotation = value.getAnnotation();
            Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
            iHashCode = (iHashCode * 31) + hashCode(annotation, stringIndexes, fqNameIndexes, typeById);
        }
        int arrayElementCount = value.getArrayElementCount() - 1;
        if (arrayElementCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Annotation.Argument.Value arrayElement = value.getArrayElement(i);
                Intrinsics.checkNotNullExpressionValue(arrayElement, "getArrayElement(...)");
                iHashCode = (iHashCode * 31) + hashCode(arrayElement, stringIndexes, fqNameIndexes, typeById);
                if (i == arrayElementCount) {
                    break;
                }
                i++;
            }
        }
        if (value.hasArrayDimensionCount()) {
            iHashCode = (iHashCode * 31) + value.getArrayDimensionCount();
        }
        return value.hasFlags() ? (iHashCode * 31) + value.getFlags() : iHashCode;
    }

    public static final int hashCode(ProtoBuf.VersionRequirement versionRequirement, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(versionRequirement, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int version = versionRequirement.hasVersion() ? 31 + versionRequirement.getVersion() : 1;
        if (versionRequirement.hasVersionFull()) {
            version = (version * 31) + versionRequirement.getVersionFull();
        }
        if (versionRequirement.hasLevel()) {
            version = (version * 31) + versionRequirement.getLevel().hashCode();
        }
        if (versionRequirement.hasErrorCode()) {
            version = (version * 31) + versionRequirement.getErrorCode();
        }
        if (versionRequirement.hasMessage()) {
            version = (version * 31) + stringIndexes.invoke(Integer.valueOf(versionRequirement.getMessage())).intValue();
        }
        return versionRequirement.hasVersionKind() ? (version * 31) + versionRequirement.getVersionKind().hashCode() : version;
    }

    public static final int hashCode(ProtoBuf.Type.Argument argument, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(argument, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iHashCode = argument.hasProjection() ? 31 + argument.getProjection().hashCode() : 1;
        if (argument.hasType()) {
            ProtoBuf.Type type = argument.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            iHashCode = (iHashCode * 31) + hashCode(type, stringIndexes, fqNameIndexes, typeById);
        }
        return argument.hasTypeId() ? (iHashCode * 31) + hashCode(typeById.invoke(Integer.valueOf(argument.getTypeId())), stringIndexes, fqNameIndexes, typeById) : iHashCode;
    }

    public static final int hashCode(ProtoBuf.Annotation.Argument argument, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(argument, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iIntValue = (stringIndexes.invoke(Integer.valueOf(argument.getNameId())).intValue() + 31) * 31;
        ProtoBuf.Annotation.Argument.Value value = argument.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return iIntValue + hashCode(value, stringIndexes, fqNameIndexes, typeById);
    }

    public static final int hashCode(ProtoBuf.Effect effect, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(effect, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iHashCode = effect.hasEffectType() ? effect.getEffectType().hashCode() + 31 : 1;
        int effectConstructorArgumentCount = effect.getEffectConstructorArgumentCount() - 1;
        if (effectConstructorArgumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Expression effectConstructorArgument = effect.getEffectConstructorArgument(i);
                Intrinsics.checkNotNullExpressionValue(effectConstructorArgument, "getEffectConstructorArgument(...)");
                iHashCode = (iHashCode * 31) + hashCode(effectConstructorArgument, stringIndexes, fqNameIndexes, typeById);
                if (i == effectConstructorArgumentCount) {
                    break;
                }
                i++;
            }
        }
        if (effect.hasConclusionOfConditionalEffect()) {
            ProtoBuf.Expression conclusionOfConditionalEffect = effect.getConclusionOfConditionalEffect();
            Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect, "getConclusionOfConditionalEffect(...)");
            iHashCode = (iHashCode * 31) + hashCode(conclusionOfConditionalEffect, stringIndexes, fqNameIndexes, typeById);
        }
        return effect.hasKind() ? (iHashCode * 31) + effect.getKind().hashCode() : iHashCode;
    }

    public static final int hashCode(JvmProtoBuf.JvmFieldSignature jvmFieldSignature, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(jvmFieldSignature, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int iIntValue = jvmFieldSignature.hasName() ? 31 + stringIndexes.invoke(Integer.valueOf(jvmFieldSignature.getName())).intValue() : 1;
        return jvmFieldSignature.hasDesc() ? (iIntValue * 31) + stringIndexes.invoke(Integer.valueOf(jvmFieldSignature.getDesc())).intValue() : iIntValue;
    }

    public static final int hashCode(ProtoBuf.Expression expression, Function1<? super Integer, Integer> stringIndexes, Function1<? super Integer, Integer> fqNameIndexes, Function1<? super Integer, ProtoBuf.Type> typeById) {
        Intrinsics.checkNotNullParameter(expression, "<this>");
        Intrinsics.checkNotNullParameter(stringIndexes, "stringIndexes");
        Intrinsics.checkNotNullParameter(fqNameIndexes, "fqNameIndexes");
        Intrinsics.checkNotNullParameter(typeById, "typeById");
        int flags = expression.hasFlags() ? expression.getFlags() + 31 : 1;
        if (expression.hasValueParameterReference()) {
            flags = (flags * 31) + expression.getValueParameterReference();
        }
        if (expression.hasConstantValue()) {
            flags = (flags * 31) + expression.getConstantValue().hashCode();
        }
        if (expression.hasIsInstanceType()) {
            ProtoBuf.Type isInstanceType = expression.getIsInstanceType();
            Intrinsics.checkNotNullExpressionValue(isInstanceType, "getIsInstanceType(...)");
            flags = (flags * 31) + hashCode(isInstanceType, stringIndexes, fqNameIndexes, typeById);
        }
        if (expression.hasIsInstanceTypeId()) {
            flags = (flags * 31) + hashCode(typeById.invoke(Integer.valueOf(expression.getIsInstanceTypeId())), stringIndexes, fqNameIndexes, typeById);
        }
        int andArgumentCount = expression.getAndArgumentCount() - 1;
        int i = 0;
        if (andArgumentCount >= 0) {
            int i2 = 0;
            while (true) {
                ProtoBuf.Expression andArgument = expression.getAndArgument(i2);
                Intrinsics.checkNotNullExpressionValue(andArgument, "getAndArgument(...)");
                flags = (flags * 31) + hashCode(andArgument, stringIndexes, fqNameIndexes, typeById);
                if (i2 == andArgumentCount) {
                    break;
                }
                i2++;
            }
        }
        int orArgumentCount = expression.getOrArgumentCount() - 1;
        if (orArgumentCount >= 0) {
            while (true) {
                ProtoBuf.Expression orArgument = expression.getOrArgument(i);
                Intrinsics.checkNotNullExpressionValue(orArgument, "getOrArgument(...)");
                flags = (flags * 31) + hashCode(orArgument, stringIndexes, fqNameIndexes, typeById);
                if (i == orArgumentCount) {
                    break;
                }
                i++;
            }
        }
        return flags;
    }
}
