package org.jetbrains.kotlin.incremental;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.java.JavaClassProtoBuf;
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.serialization.Interner;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: ProtoCompareGenerated.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0016\u0018\u00002\u00020\u0001:\u0002vwB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\bJ\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000eH\u0002J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020$2\u0006\u0010\"\u001a\u00020$H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020%2\u0006\u0010\"\u001a\u00020%H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020&2\u0006\u0010\"\u001a\u00020&H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020(2\u0006\u0010\"\u001a\u00020(H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020)2\u0006\u0010\"\u001a\u00020)H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020*2\u0006\u0010\"\u001a\u00020*H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020+2\u0006\u0010\"\u001a\u00020+H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020,2\u0006\u0010\"\u001a\u00020,H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020.2\u0006\u0010\"\u001a\u00020.H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u00020/2\u0006\u0010\"\u001a\u00020/H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002002\u0006\u0010\"\u001a\u000200H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002012\u0006\u0010\"\u001a\u000201H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002022\u0006\u0010\"\u001a\u000202H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002032\u0006\u0010\"\u001a\u000203H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002042\u0006\u0010\"\u001a\u000204H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002052\u0006\u0010\"\u001a\u000205H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002062\u0006\u0010\"\u001a\u000206H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002072\u0006\u0010\"\u001a\u000207H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002082\u0006\u0010\"\u001a\u000208H\u0016J\u0018\u0010#\u001a\u00020 2\u0006\u0010!\u001a\u0002092\u0006\u0010\"\u001a\u000209H\u0016J\u0018\u0010:\u001a\u00020 2\u0006\u0010!\u001a\u00020$2\u0006\u0010\"\u001a\u00020$H\u0016J\u0018\u0010;\u001a\u00020 2\u0006\u0010!\u001a\u00020&2\u0006\u0010\"\u001a\u00020&H\u0016J\u0018\u0010<\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010=\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010>\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010?\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010@\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010A\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010B\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010C\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010D\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010E\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010F\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010G\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010H\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010I\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010J\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010K\u001a\u00020 2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'H\u0016J\u0018\u0010L\u001a\u00020 2\u0006\u0010!\u001a\u00020(2\u0006\u0010\"\u001a\u00020(H\u0016J\u0018\u0010M\u001a\u00020 2\u0006\u0010!\u001a\u00020(2\u0006\u0010\"\u001a\u00020(H\u0016J\u0018\u0010N\u001a\u00020 2\u0006\u0010!\u001a\u00020)2\u0006\u0010\"\u001a\u00020)H\u0016J\u0018\u0010O\u001a\u00020 2\u0006\u0010!\u001a\u00020*2\u0006\u0010\"\u001a\u00020*H\u0016J\u0018\u0010P\u001a\u00020 2\u0006\u0010!\u001a\u00020,2\u0006\u0010\"\u001a\u00020,H\u0016J\u0018\u0010Q\u001a\u00020 2\u0006\u0010!\u001a\u00020,2\u0006\u0010\"\u001a\u00020,H\u0016J\u0018\u0010R\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010S\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010T\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010U\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010V\u001a\u00020 2\u0006\u0010!\u001a\u00020-2\u0006\u0010\"\u001a\u00020-H\u0016J\u0018\u0010W\u001a\u00020 2\u0006\u0010!\u001a\u00020.2\u0006\u0010\"\u001a\u00020.H\u0016J\u0018\u0010X\u001a\u00020 2\u0006\u0010!\u001a\u00020.2\u0006\u0010\"\u001a\u00020.H\u0016J\u0018\u0010Y\u001a\u00020 2\u0006\u0010!\u001a\u00020.2\u0006\u0010\"\u001a\u00020.H\u0016J\u0018\u0010Z\u001a\u00020 2\u0006\u0010!\u001a\u00020/2\u0006\u0010\"\u001a\u00020/H\u0016J\u0018\u0010[\u001a\u00020 2\u0006\u0010!\u001a\u00020/2\u0006\u0010\"\u001a\u00020/H\u0016J\u0018\u0010\\\u001a\u00020 2\u0006\u0010!\u001a\u00020/2\u0006\u0010\"\u001a\u00020/H\u0016J\u0018\u0010]\u001a\u00020 2\u0006\u0010!\u001a\u00020/2\u0006\u0010\"\u001a\u00020/H\u0016J\u0018\u0010^\u001a\u00020 2\u0006\u0010!\u001a\u0002022\u0006\u0010\"\u001a\u000202H\u0016J\u0018\u0010_\u001a\u00020 2\u0006\u0010!\u001a\u0002022\u0006\u0010\"\u001a\u000202H\u0016J\u0018\u0010`\u001a\u00020 2\u0006\u0010!\u001a\u0002022\u0006\u0010\"\u001a\u000202H\u0016J\u0018\u0010a\u001a\u00020 2\u0006\u0010!\u001a\u0002002\u0006\u0010\"\u001a\u000200H\u0016J\u0018\u0010b\u001a\u00020 2\u0006\u0010!\u001a\u0002032\u0006\u0010\"\u001a\u000203H\u0016J\u0018\u0010c\u001a\u00020 2\u0006\u0010!\u001a\u0002032\u0006\u0010\"\u001a\u000203H\u0016J\u0018\u0010d\u001a\u00020 2\u0006\u0010!\u001a\u0002062\u0006\u0010\"\u001a\u000206H\u0016J\u0018\u0010e\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u000eH\u0002J\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020h0g2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'J\u001c\u0010f\u001a\b\u0012\u0004\u0012\u00020i0g2\u0006\u0010!\u001a\u00020.2\u0006\u0010\"\u001a\u00020.J*\u0010j\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000e2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010m\u001a\u00020\u0003J*\u0010n\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000e2\u0012\u0010l\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010m\u001a\u00020\u0003J\u000e\u0010o\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000eJ\u000e\u0010p\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000eJ\u000e\u0010q\u001a\u0002002\u0006\u0010r\u001a\u00020\u000eJ\u000e\u0010s\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000eJ\u000e\u0010t\u001a\u00020\u000e2\u0006\u0010k\u001a\u00020\u000eJ\u000e\u0010u\u001a\u0002002\u0006\u0010r\u001a\u00020\u000eR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u001d\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006x"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "", "oldNameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "newNameResolver", "oldTypeTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "newTypeTable", "(Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;)V", "classIds", "Lorg/jetbrains/kotlin/metadata/serialization/Interner;", "Lorg/jetbrains/kotlin/name/ClassId;", "newClassIdIndexesMap", "", "", "getNewClassIdIndexesMap", "()Ljava/util/Map;", "getNewNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "newStringIndexesMap", "getNewStringIndexesMap", "getNewTypeTable", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "oldClassIdIndexesMap", "getOldClassIdIndexesMap", "getOldNameResolver", "oldStringIndexesMap", "getOldStringIndexesMap", "getOldTypeTable", "strings", "", "checkClassIdEquals", "", "old", PsiKeyword.NEW, "checkEquals", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation$Argument$Value;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Contract;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Effect;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$EnumEntry;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Expression;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Argument;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeAlias;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$ValueParameter;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirement;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$VersionRequirementTable;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmFieldSignature;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmMethodSignature;", "Lorg/jetbrains/kotlin/metadata/jvm/JvmProtoBuf$JvmPropertySignature;", "checkEqualsAnnotationArgument", "checkEqualsAnnotationArgumentValueArrayElement", "checkEqualsClassConstructor", "checkEqualsClassContextReceiverType", "checkEqualsClassContextReceiverTypeId", "checkEqualsClassEnumEntry", "checkEqualsClassFunction", "checkEqualsClassMultiFieldValueClassUnderlyingName", "checkEqualsClassMultiFieldValueClassUnderlyingType", "checkEqualsClassMultiFieldValueClassUnderlyingTypeId", "checkEqualsClassNestedClassName", "checkEqualsClassProperty", "checkEqualsClassSealedSubclassFqName", "checkEqualsClassSupertype", "checkEqualsClassSupertypeId", "checkEqualsClassTypeAlias", "checkEqualsClassTypeParameter", "checkEqualsClassVersionRequirement", "checkEqualsConstructorValueParameter", "checkEqualsConstructorVersionRequirement", "checkEqualsContractEffect", "checkEqualsEffectEffectConstructorArgument", "checkEqualsExpressionAndArgument", "checkEqualsExpressionOrArgument", "checkEqualsFunctionContextReceiverType", "checkEqualsFunctionContextReceiverTypeId", "checkEqualsFunctionTypeParameter", "checkEqualsFunctionValueParameter", "checkEqualsFunctionVersionRequirement", "checkEqualsPackageFunction", "checkEqualsPackageProperty", "checkEqualsPackageTypeAlias", "checkEqualsPropertyContextReceiverType", "checkEqualsPropertyContextReceiverTypeId", "checkEqualsPropertyTypeParameter", "checkEqualsPropertyVersionRequirement", "checkEqualsTypeAliasAnnotation", "checkEqualsTypeAliasTypeParameter", "checkEqualsTypeAliasVersionRequirement", "checkEqualsTypeArgument", "checkEqualsTypeParameterUpperBound", "checkEqualsTypeParameterUpperBoundId", "checkEqualsVersionRequirementTableRequirement", "checkStringEquals", "difference", "Ljava/util/EnumSet;", "Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated$ProtoBufClassKind;", "Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated$ProtoBufPackageKind;", "getIndexOfClassId", "index", "map", "nameResolver", "getIndexOfString", "newGetIndexOfClassId", "newGetIndexOfString", "newGetTypeById", "id", "oldGetIndexOfClassId", "oldGetIndexOfString", "oldGetTypeById", "ProtoBufClassKind", "ProtoBufPackageKind", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ProtoCompareGenerated {
    private final Interner<ClassId> classIds;
    private final Map<Integer, Integer> newClassIdIndexesMap;
    private final NameResolver newNameResolver;
    private final Map<Integer, Integer> newStringIndexesMap;
    private final ProtoBuf.TypeTable newTypeTable;
    private final Map<Integer, Integer> oldClassIdIndexesMap;
    private final NameResolver oldNameResolver;
    private final Map<Integer, Integer> oldStringIndexesMap;
    private final ProtoBuf.TypeTable oldTypeTable;
    private final Interner<String> strings;

    /* JADX INFO: compiled from: ProtoCompareGenerated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\"\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated$ProtoBufClassKind;", "", "(Ljava/lang/String;I)V", "FLAGS", "FQ_NAME", "COMPANION_OBJECT_NAME", "TYPE_PARAMETER_LIST", "SUPERTYPE_LIST", "SUPERTYPE_ID_LIST", "NESTED_CLASS_NAME_LIST", "CONTEXT_RECEIVER_TYPE_LIST", "CONTEXT_RECEIVER_TYPE_ID_LIST", "CONSTRUCTOR_LIST", "FUNCTION_LIST", "PROPERTY_LIST", "TYPE_ALIAS_LIST", "ENUM_ENTRY_LIST", "SEALED_SUBCLASS_FQ_NAME_LIST", "INLINE_CLASS_UNDERLYING_PROPERTY_NAME", "INLINE_CLASS_UNDERLYING_TYPE", "INLINE_CLASS_UNDERLYING_TYPE_ID", "MULTI_FIELD_VALUE_CLASS_UNDERLYING_NAME_LIST", "MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_LIST", "MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_ID_LIST", "VERSION_REQUIREMENT_LIST", "VERSION_REQUIREMENT_TABLE", "JVM_EXT_CLASS_MODULE_NAME", "JVM_EXT_CLASS_LOCAL_VARIABLE_LIST", "JVM_EXT_ANONYMOUS_OBJECT_ORIGIN_NAME", "JVM_EXT_JVM_CLASS_FLAGS", "JS_EXT_CLASS_ANNOTATION_LIST", "JS_EXT_CLASS_CONTAINING_FILE_ID", "JAVA_EXT_IS_PACKAGE_PRIVATE_CLASS", "BUILT_INS_EXT_CLASS_ANNOTATION_LIST", "KLIB_EXT_CLASS_ANNOTATION_LIST", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ProtoBufClassKind {
        FLAGS,
        FQ_NAME,
        COMPANION_OBJECT_NAME,
        TYPE_PARAMETER_LIST,
        SUPERTYPE_LIST,
        SUPERTYPE_ID_LIST,
        NESTED_CLASS_NAME_LIST,
        CONTEXT_RECEIVER_TYPE_LIST,
        CONTEXT_RECEIVER_TYPE_ID_LIST,
        CONSTRUCTOR_LIST,
        FUNCTION_LIST,
        PROPERTY_LIST,
        TYPE_ALIAS_LIST,
        ENUM_ENTRY_LIST,
        SEALED_SUBCLASS_FQ_NAME_LIST,
        INLINE_CLASS_UNDERLYING_PROPERTY_NAME,
        INLINE_CLASS_UNDERLYING_TYPE,
        INLINE_CLASS_UNDERLYING_TYPE_ID,
        MULTI_FIELD_VALUE_CLASS_UNDERLYING_NAME_LIST,
        MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_LIST,
        MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_ID_LIST,
        VERSION_REQUIREMENT_LIST,
        VERSION_REQUIREMENT_TABLE,
        JVM_EXT_CLASS_MODULE_NAME,
        JVM_EXT_CLASS_LOCAL_VARIABLE_LIST,
        JVM_EXT_ANONYMOUS_OBJECT_ORIGIN_NAME,
        JVM_EXT_JVM_CLASS_FLAGS,
        JS_EXT_CLASS_ANNOTATION_LIST,
        JS_EXT_CLASS_CONTAINING_FILE_ID,
        JAVA_EXT_IS_PACKAGE_PRIVATE_CLASS,
        BUILT_INS_EXT_CLASS_ANNOTATION_LIST,
        KLIB_EXT_CLASS_ANNOTATION_LIST;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ProtoBufClassKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: ProtoCompareGenerated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated$ProtoBufPackageKind;", "", "(Ljava/lang/String;I)V", "FUNCTION_LIST", "PROPERTY_LIST", "TYPE_ALIAS_LIST", "VERSION_REQUIREMENT_TABLE", "JVM_EXT_PACKAGE_MODULE_NAME", "JVM_EXT_PACKAGE_LOCAL_VARIABLE_LIST", "JS_EXT_PACKAGE_FQ_NAME", "BUILT_INS_EXT_PACKAGE_FQ_NAME", "KLIB_EXT_PACKAGE_FQ_NAME", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ProtoBufPackageKind {
        FUNCTION_LIST,
        PROPERTY_LIST,
        TYPE_ALIAS_LIST,
        VERSION_REQUIREMENT_TABLE,
        JVM_EXT_PACKAGE_MODULE_NAME,
        JVM_EXT_PACKAGE_LOCAL_VARIABLE_LIST,
        JS_EXT_PACKAGE_FQ_NAME,
        BUILT_INS_EXT_PACKAGE_FQ_NAME,
        KLIB_EXT_PACKAGE_FQ_NAME;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ProtoBufPackageKind> getEntries() {
            return $ENTRIES;
        }
    }

    public final ProtoBuf.Type newGetTypeById(int id2) {
        ProtoBuf.Type type = this.newTypeTable.getType(id2);
        if (type != null) {
            return type;
        }
        throw new IllegalStateException(("Unknown type id: " + id2).toString());
    }

    public final ProtoBuf.Type oldGetTypeById(int id2) {
        ProtoBuf.Type type = this.oldTypeTable.getType(id2);
        if (type != null) {
            return type;
        }
        throw new IllegalStateException(("Unknown type id: " + id2).toString());
    }

    public ProtoCompareGenerated(NameResolver oldNameResolver, NameResolver newNameResolver, ProtoBuf.TypeTable typeTable, ProtoBuf.TypeTable typeTable2) {
        Intrinsics.checkNotNullParameter(oldNameResolver, "oldNameResolver");
        Intrinsics.checkNotNullParameter(newNameResolver, "newNameResolver");
        this.oldNameResolver = oldNameResolver;
        this.newNameResolver = newNameResolver;
        this.strings = new Interner<>(null, 1, null);
        this.oldStringIndexesMap = new HashMap();
        this.newStringIndexesMap = new HashMap();
        this.oldClassIdIndexesMap = new HashMap();
        this.newClassIdIndexesMap = new HashMap();
        if (typeTable == null) {
            typeTable = ProtoBuf.TypeTable.getDefaultInstance();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getDefaultInstance(...)");
        }
        this.oldTypeTable = typeTable;
        if (typeTable2 == null) {
            typeTable2 = ProtoBuf.TypeTable.getDefaultInstance();
            Intrinsics.checkNotNullExpressionValue(typeTable2, "getDefaultInstance(...)");
        }
        this.newTypeTable = typeTable2;
        this.classIds = new Interner<>(null, 1, null);
    }

    public final NameResolver getOldNameResolver() {
        return this.oldNameResolver;
    }

    public final NameResolver getNewNameResolver() {
        return this.newNameResolver;
    }

    public final Map<Integer, Integer> getOldStringIndexesMap() {
        return this.oldStringIndexesMap;
    }

    public final Map<Integer, Integer> getNewStringIndexesMap() {
        return this.newStringIndexesMap;
    }

    public final Map<Integer, Integer> getOldClassIdIndexesMap() {
        return this.oldClassIdIndexesMap;
    }

    public final Map<Integer, Integer> getNewClassIdIndexesMap() {
        return this.newClassIdIndexesMap;
    }

    public final ProtoBuf.TypeTable getOldTypeTable() {
        return this.oldTypeTable;
    }

    public final ProtoBuf.TypeTable getNewTypeTable() {
        return this.newTypeTable;
    }

    public boolean checkEquals(ProtoBuf.Package old, ProtoBuf.Package r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (!checkEqualsPackageFunction(old, r9) || !checkEqualsPackageProperty(old, r9) || !checkEqualsPackageTypeAlias(old, r9) || old.hasVersionRequirementTable() != r9.hasVersionRequirementTable()) {
            return false;
        }
        if (old.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = old.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            ProtoBuf.VersionRequirementTable versionRequirementTable2 = r9.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable2, "getVersionRequirementTable(...)");
            if (!checkEquals(versionRequirementTable, versionRequirementTable2)) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.packageModuleName) != r9.hasExtension(JvmProtoBuf.packageModuleName)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.packageModuleName)) {
            Object extension = old.getExtension(JvmProtoBuf.packageModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            int iIntValue = ((Number) extension).intValue();
            Object extension2 = r9.getExtension(JvmProtoBuf.packageModuleName);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkStringEquals(iIntValue, ((Number) extension2).intValue())) {
                return false;
            }
        }
        if (old.getExtensionCount(JvmProtoBuf.packageLocalVariable) != r9.getExtensionCount(JvmProtoBuf.packageLocalVariable)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JvmProtoBuf.packageLocalVariable) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension3 = old.getExtension(JvmProtoBuf.packageLocalVariable, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = r9.getExtension(JvmProtoBuf.packageLocalVariable, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Property) extension3, (ProtoBuf.Property) extension4)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.hasExtension(JsProtoBuf.packageFqName) != r9.hasExtension(JsProtoBuf.packageFqName)) {
            return false;
        }
        if ((old.hasExtension(JsProtoBuf.packageFqName) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.packageFqName), r9.getExtension(JsProtoBuf.packageFqName))) || old.hasExtension(BuiltInsProtoBuf.packageFqName) != r9.hasExtension(BuiltInsProtoBuf.packageFqName)) {
            return false;
        }
        if ((!old.hasExtension(BuiltInsProtoBuf.packageFqName) || Intrinsics.areEqual(old.getExtension(BuiltInsProtoBuf.packageFqName), r9.getExtension(BuiltInsProtoBuf.packageFqName))) && old.hasExtension(KlibMetadataProtoBuf.packageFqName) == r9.hasExtension(KlibMetadataProtoBuf.packageFqName)) {
            return !old.hasExtension(KlibMetadataProtoBuf.packageFqName) || Intrinsics.areEqual(old.getExtension(KlibMetadataProtoBuf.packageFqName), r9.getExtension(KlibMetadataProtoBuf.packageFqName));
        }
        return false;
    }

    public final EnumSet<ProtoBufPackageKind> difference(ProtoBuf.Package old, ProtoBuf.Package r8) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r8, "new");
        EnumSet<ProtoBufPackageKind> enumSetNoneOf = EnumSet.noneOf(ProtoBufPackageKind.class);
        if (!checkEqualsPackageFunction(old, r8)) {
            enumSetNoneOf.add(ProtoBufPackageKind.FUNCTION_LIST);
        }
        if (!checkEqualsPackageProperty(old, r8)) {
            enumSetNoneOf.add(ProtoBufPackageKind.PROPERTY_LIST);
        }
        if (!checkEqualsPackageTypeAlias(old, r8)) {
            enumSetNoneOf.add(ProtoBufPackageKind.TYPE_ALIAS_LIST);
        }
        if (old.hasVersionRequirementTable() != r8.hasVersionRequirementTable()) {
            enumSetNoneOf.add(ProtoBufPackageKind.VERSION_REQUIREMENT_TABLE);
        }
        if (old.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = old.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            ProtoBuf.VersionRequirementTable versionRequirementTable2 = r8.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable2, "getVersionRequirementTable(...)");
            if (!checkEquals(versionRequirementTable, versionRequirementTable2)) {
                enumSetNoneOf.add(ProtoBufPackageKind.VERSION_REQUIREMENT_TABLE);
            }
        }
        if (old.hasExtension(JvmProtoBuf.packageModuleName) != r8.hasExtension(JvmProtoBuf.packageModuleName)) {
            enumSetNoneOf.add(ProtoBufPackageKind.JVM_EXT_PACKAGE_MODULE_NAME);
        }
        if (old.hasExtension(JvmProtoBuf.packageModuleName)) {
            Object extension = old.getExtension(JvmProtoBuf.packageModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            int iIntValue = ((Number) extension).intValue();
            Object extension2 = r8.getExtension(JvmProtoBuf.packageModuleName);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkStringEquals(iIntValue, ((Number) extension2).intValue())) {
                enumSetNoneOf.add(ProtoBufPackageKind.JVM_EXT_PACKAGE_MODULE_NAME);
            }
        }
        if (old.getExtensionCount(JvmProtoBuf.packageLocalVariable) != r8.getExtensionCount(JvmProtoBuf.packageLocalVariable)) {
            enumSetNoneOf.add(ProtoBufPackageKind.JVM_EXT_PACKAGE_LOCAL_VARIABLE_LIST);
        } else {
            int extensionCount = old.getExtensionCount(JvmProtoBuf.packageLocalVariable) - 1;
            if (extensionCount >= 0) {
                int i = 0;
                while (true) {
                    Object extension3 = old.getExtension(JvmProtoBuf.packageLocalVariable, i);
                    Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                    Object extension4 = r8.getExtension(JvmProtoBuf.packageLocalVariable, i);
                    Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                    if (!checkEquals((ProtoBuf.Property) extension3, (ProtoBuf.Property) extension4)) {
                        enumSetNoneOf.add(ProtoBufPackageKind.JVM_EXT_PACKAGE_LOCAL_VARIABLE_LIST);
                    }
                    if (i == extensionCount) {
                        break;
                    }
                    i++;
                }
            }
        }
        if (old.hasExtension(JsProtoBuf.packageFqName) != r8.hasExtension(JsProtoBuf.packageFqName)) {
            enumSetNoneOf.add(ProtoBufPackageKind.JS_EXT_PACKAGE_FQ_NAME);
        }
        if (old.hasExtension(JsProtoBuf.packageFqName) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.packageFqName), r8.getExtension(JsProtoBuf.packageFqName))) {
            enumSetNoneOf.add(ProtoBufPackageKind.JS_EXT_PACKAGE_FQ_NAME);
        }
        if (old.hasExtension(BuiltInsProtoBuf.packageFqName) != r8.hasExtension(BuiltInsProtoBuf.packageFqName)) {
            enumSetNoneOf.add(ProtoBufPackageKind.BUILT_INS_EXT_PACKAGE_FQ_NAME);
        }
        if (old.hasExtension(BuiltInsProtoBuf.packageFqName) && !Intrinsics.areEqual(old.getExtension(BuiltInsProtoBuf.packageFqName), r8.getExtension(BuiltInsProtoBuf.packageFqName))) {
            enumSetNoneOf.add(ProtoBufPackageKind.BUILT_INS_EXT_PACKAGE_FQ_NAME);
        }
        if (old.hasExtension(KlibMetadataProtoBuf.packageFqName) != r8.hasExtension(KlibMetadataProtoBuf.packageFqName)) {
            enumSetNoneOf.add(ProtoBufPackageKind.KLIB_EXT_PACKAGE_FQ_NAME);
        }
        if (old.hasExtension(KlibMetadataProtoBuf.packageFqName) && !Intrinsics.areEqual(old.getExtension(KlibMetadataProtoBuf.packageFqName), r8.getExtension(KlibMetadataProtoBuf.packageFqName))) {
            enumSetNoneOf.add(ProtoBufPackageKind.KLIB_EXT_PACKAGE_FQ_NAME);
        }
        Intrinsics.checkNotNull(enumSetNoneOf);
        return enumSetNoneOf;
    }

    public boolean checkEquals(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.hasFlags() != r9.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != r9.getFlags()) || !checkClassIdEquals(old.getFqName(), r9.getFqName()) || old.hasCompanionObjectName() != r9.hasCompanionObjectName()) {
            return false;
        }
        if ((old.hasCompanionObjectName() && !checkStringEquals(old.getCompanionObjectName(), r9.getCompanionObjectName())) || !checkEqualsClassTypeParameter(old, r9) || !checkEqualsClassSupertype(old, r9) || !checkEqualsClassSupertypeId(old, r9) || !checkEqualsClassNestedClassName(old, r9) || !checkEqualsClassContextReceiverType(old, r9) || !checkEqualsClassContextReceiverTypeId(old, r9) || !checkEqualsClassConstructor(old, r9) || !checkEqualsClassFunction(old, r9) || !checkEqualsClassProperty(old, r9) || !checkEqualsClassTypeAlias(old, r9) || !checkEqualsClassEnumEntry(old, r9) || !checkEqualsClassSealedSubclassFqName(old, r9) || old.hasInlineClassUnderlyingPropertyName() != r9.hasInlineClassUnderlyingPropertyName()) {
            return false;
        }
        if ((old.hasInlineClassUnderlyingPropertyName() && !checkStringEquals(old.getInlineClassUnderlyingPropertyName(), r9.getInlineClassUnderlyingPropertyName())) || old.hasInlineClassUnderlyingType() != r9.hasInlineClassUnderlyingType()) {
            return false;
        }
        if (old.hasInlineClassUnderlyingType()) {
            ProtoBuf.Type inlineClassUnderlyingType = old.getInlineClassUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(inlineClassUnderlyingType, "getInlineClassUnderlyingType(...)");
            ProtoBuf.Type inlineClassUnderlyingType2 = r9.getInlineClassUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(inlineClassUnderlyingType2, "getInlineClassUnderlyingType(...)");
            if (!checkEquals(inlineClassUnderlyingType, inlineClassUnderlyingType2)) {
                return false;
            }
        }
        if (old.hasInlineClassUnderlyingTypeId() != r9.hasInlineClassUnderlyingTypeId()) {
            return false;
        }
        if (old.hasInlineClassUnderlyingTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getInlineClassUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(r9.getInlineClassUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (!checkEqualsClassMultiFieldValueClassUnderlyingName(old, r9) || !checkEqualsClassMultiFieldValueClassUnderlyingType(old, r9) || !checkEqualsClassMultiFieldValueClassUnderlyingTypeId(old, r9) || !checkEqualsClassVersionRequirement(old, r9) || old.hasVersionRequirementTable() != r9.hasVersionRequirementTable()) {
            return false;
        }
        if (old.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = old.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            ProtoBuf.VersionRequirementTable versionRequirementTable2 = r9.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable2, "getVersionRequirementTable(...)");
            if (!checkEquals(versionRequirementTable, versionRequirementTable2)) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.classModuleName) != r9.hasExtension(JvmProtoBuf.classModuleName)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.classModuleName)) {
            Object extension = old.getExtension(JvmProtoBuf.classModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            int iIntValue = ((Number) extension).intValue();
            Object extension2 = r9.getExtension(JvmProtoBuf.classModuleName);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkStringEquals(iIntValue, ((Number) extension2).intValue())) {
                return false;
            }
        }
        if (old.getExtensionCount(JvmProtoBuf.classLocalVariable) != r9.getExtensionCount(JvmProtoBuf.classLocalVariable)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JvmProtoBuf.classLocalVariable) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension3 = old.getExtension(JvmProtoBuf.classLocalVariable, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = r9.getExtension(JvmProtoBuf.classLocalVariable, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Property) extension3, (ProtoBuf.Property) extension4)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.hasExtension(JvmProtoBuf.anonymousObjectOriginName) != r9.hasExtension(JvmProtoBuf.anonymousObjectOriginName)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.anonymousObjectOriginName)) {
            Object extension5 = old.getExtension(JvmProtoBuf.anonymousObjectOriginName);
            Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
            int iIntValue2 = ((Number) extension5).intValue();
            Object extension6 = r9.getExtension(JvmProtoBuf.anonymousObjectOriginName);
            Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
            if (!checkStringEquals(iIntValue2, ((Number) extension6).intValue())) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.jvmClassFlags) != r9.hasExtension(JvmProtoBuf.jvmClassFlags)) {
            return false;
        }
        if ((old.hasExtension(JvmProtoBuf.jvmClassFlags) && !Intrinsics.areEqual(old.getExtension(JvmProtoBuf.jvmClassFlags), r9.getExtension(JvmProtoBuf.jvmClassFlags))) || old.getExtensionCount(JsProtoBuf.classAnnotation) != r9.getExtensionCount(JsProtoBuf.classAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(JsProtoBuf.classAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension7 = old.getExtension(JsProtoBuf.classAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = r9.getExtension(JsProtoBuf.classAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.hasExtension(JsProtoBuf.classContainingFileId) != r9.hasExtension(JsProtoBuf.classContainingFileId)) {
            return false;
        }
        if ((old.hasExtension(JsProtoBuf.classContainingFileId) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.classContainingFileId), r9.getExtension(JsProtoBuf.classContainingFileId))) || old.hasExtension(JavaClassProtoBuf.isPackagePrivateClass) != r9.hasExtension(JavaClassProtoBuf.isPackagePrivateClass)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isPackagePrivateClass) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isPackagePrivateClass), r9.getExtension(JavaClassProtoBuf.isPackagePrivateClass))) || old.getExtensionCount(BuiltInsProtoBuf.classAnnotation) != r9.getExtensionCount(BuiltInsProtoBuf.classAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(BuiltInsProtoBuf.classAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension9 = old.getExtension(BuiltInsProtoBuf.classAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension9, "getExtension(...)");
                Object extension10 = r9.getExtension(BuiltInsProtoBuf.classAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension10, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension9, (ProtoBuf.Annotation) extension10)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.classAnnotation) != r9.getExtensionCount(KlibMetadataProtoBuf.classAnnotation)) {
            return false;
        }
        int extensionCount4 = old.getExtensionCount(KlibMetadataProtoBuf.classAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension11 = old.getExtension(KlibMetadataProtoBuf.classAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension11, "getExtension(...)");
                Object extension12 = r9.getExtension(KlibMetadataProtoBuf.classAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension12, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension11, (ProtoBuf.Annotation) extension12)) {
                    return false;
                }
                if (i4 != extensionCount4) {
                    i4++;
                }
            }
        }
        return true;
    }

    public final EnumSet<ProtoBufClassKind> difference(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        EnumSet<ProtoBufClassKind> enumSetNoneOf = EnumSet.noneOf(ProtoBufClassKind.class);
        if (old.hasFlags() != r9.hasFlags()) {
            enumSetNoneOf.add(ProtoBufClassKind.FLAGS);
        }
        if (old.hasFlags() && old.getFlags() != r9.getFlags()) {
            enumSetNoneOf.add(ProtoBufClassKind.FLAGS);
        }
        if (!checkClassIdEquals(old.getFqName(), r9.getFqName())) {
            enumSetNoneOf.add(ProtoBufClassKind.FQ_NAME);
        }
        if (old.hasCompanionObjectName() != r9.hasCompanionObjectName()) {
            enumSetNoneOf.add(ProtoBufClassKind.COMPANION_OBJECT_NAME);
        }
        if (old.hasCompanionObjectName() && !checkStringEquals(old.getCompanionObjectName(), r9.getCompanionObjectName())) {
            enumSetNoneOf.add(ProtoBufClassKind.COMPANION_OBJECT_NAME);
        }
        if (!checkEqualsClassTypeParameter(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.TYPE_PARAMETER_LIST);
        }
        if (!checkEqualsClassSupertype(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.SUPERTYPE_LIST);
        }
        if (!checkEqualsClassSupertypeId(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.SUPERTYPE_ID_LIST);
        }
        if (!checkEqualsClassNestedClassName(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.NESTED_CLASS_NAME_LIST);
        }
        if (!checkEqualsClassContextReceiverType(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.CONTEXT_RECEIVER_TYPE_LIST);
        }
        if (!checkEqualsClassContextReceiverTypeId(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.CONTEXT_RECEIVER_TYPE_ID_LIST);
        }
        if (!checkEqualsClassConstructor(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.CONSTRUCTOR_LIST);
        }
        if (!checkEqualsClassFunction(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.FUNCTION_LIST);
        }
        if (!checkEqualsClassProperty(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.PROPERTY_LIST);
        }
        if (!checkEqualsClassTypeAlias(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.TYPE_ALIAS_LIST);
        }
        if (!checkEqualsClassEnumEntry(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.ENUM_ENTRY_LIST);
        }
        if (!checkEqualsClassSealedSubclassFqName(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.SEALED_SUBCLASS_FQ_NAME_LIST);
        }
        if (old.hasInlineClassUnderlyingPropertyName() != r9.hasInlineClassUnderlyingPropertyName()) {
            enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_PROPERTY_NAME);
        }
        if (old.hasInlineClassUnderlyingPropertyName() && !checkStringEquals(old.getInlineClassUnderlyingPropertyName(), r9.getInlineClassUnderlyingPropertyName())) {
            enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_PROPERTY_NAME);
        }
        if (old.hasInlineClassUnderlyingType() != r9.hasInlineClassUnderlyingType()) {
            enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE);
        }
        if (old.hasInlineClassUnderlyingType()) {
            ProtoBuf.Type inlineClassUnderlyingType = old.getInlineClassUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(inlineClassUnderlyingType, "getInlineClassUnderlyingType(...)");
            ProtoBuf.Type inlineClassUnderlyingType2 = r9.getInlineClassUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(inlineClassUnderlyingType2, "getInlineClassUnderlyingType(...)");
            if (!checkEquals(inlineClassUnderlyingType, inlineClassUnderlyingType2)) {
                enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE);
            }
        }
        if (old.hasInlineClassUnderlyingTypeId() != r9.hasInlineClassUnderlyingTypeId()) {
            enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE_ID);
        }
        if (old.hasInlineClassUnderlyingTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getInlineClassUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(r9.getInlineClassUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                enumSetNoneOf.add(ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE_ID);
            }
        }
        if (!checkEqualsClassMultiFieldValueClassUnderlyingName(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_NAME_LIST);
        }
        if (!checkEqualsClassMultiFieldValueClassUnderlyingType(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_LIST);
        }
        if (!checkEqualsClassMultiFieldValueClassUnderlyingTypeId(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_ID_LIST);
        }
        if (!checkEqualsClassVersionRequirement(old, r9)) {
            enumSetNoneOf.add(ProtoBufClassKind.VERSION_REQUIREMENT_LIST);
        }
        if (old.hasVersionRequirementTable() != r9.hasVersionRequirementTable()) {
            enumSetNoneOf.add(ProtoBufClassKind.VERSION_REQUIREMENT_TABLE);
        }
        if (old.hasVersionRequirementTable()) {
            ProtoBuf.VersionRequirementTable versionRequirementTable = old.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "getVersionRequirementTable(...)");
            ProtoBuf.VersionRequirementTable versionRequirementTable2 = r9.getVersionRequirementTable();
            Intrinsics.checkNotNullExpressionValue(versionRequirementTable2, "getVersionRequirementTable(...)");
            if (!checkEquals(versionRequirementTable, versionRequirementTable2)) {
                enumSetNoneOf.add(ProtoBufClassKind.VERSION_REQUIREMENT_TABLE);
            }
        }
        if (old.hasExtension(JvmProtoBuf.classModuleName) != r9.hasExtension(JvmProtoBuf.classModuleName)) {
            enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_CLASS_MODULE_NAME);
        }
        if (old.hasExtension(JvmProtoBuf.classModuleName)) {
            Object extension = old.getExtension(JvmProtoBuf.classModuleName);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            int iIntValue = ((Number) extension).intValue();
            Object extension2 = r9.getExtension(JvmProtoBuf.classModuleName);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkStringEquals(iIntValue, ((Number) extension2).intValue())) {
                enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_CLASS_MODULE_NAME);
            }
        }
        int i = 0;
        if (old.getExtensionCount(JvmProtoBuf.classLocalVariable) != r9.getExtensionCount(JvmProtoBuf.classLocalVariable)) {
            enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_CLASS_LOCAL_VARIABLE_LIST);
        } else {
            int extensionCount = old.getExtensionCount(JvmProtoBuf.classLocalVariable) - 1;
            if (extensionCount >= 0) {
                int i2 = 0;
                while (true) {
                    Object extension3 = old.getExtension(JvmProtoBuf.classLocalVariable, i2);
                    Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                    Object extension4 = r9.getExtension(JvmProtoBuf.classLocalVariable, i2);
                    Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                    if (!checkEquals((ProtoBuf.Property) extension3, (ProtoBuf.Property) extension4)) {
                        enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_CLASS_LOCAL_VARIABLE_LIST);
                    }
                    if (i2 == extensionCount) {
                        break;
                    }
                    i2++;
                }
            }
        }
        if (old.hasExtension(JvmProtoBuf.anonymousObjectOriginName) != r9.hasExtension(JvmProtoBuf.anonymousObjectOriginName)) {
            enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_ANONYMOUS_OBJECT_ORIGIN_NAME);
        }
        if (old.hasExtension(JvmProtoBuf.anonymousObjectOriginName)) {
            Object extension5 = old.getExtension(JvmProtoBuf.anonymousObjectOriginName);
            Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
            int iIntValue2 = ((Number) extension5).intValue();
            Object extension6 = r9.getExtension(JvmProtoBuf.anonymousObjectOriginName);
            Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
            if (!checkStringEquals(iIntValue2, ((Number) extension6).intValue())) {
                enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_ANONYMOUS_OBJECT_ORIGIN_NAME);
            }
        }
        if (old.hasExtension(JvmProtoBuf.jvmClassFlags) != r9.hasExtension(JvmProtoBuf.jvmClassFlags)) {
            enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_JVM_CLASS_FLAGS);
        }
        if (old.hasExtension(JvmProtoBuf.jvmClassFlags) && !Intrinsics.areEqual(old.getExtension(JvmProtoBuf.jvmClassFlags), r9.getExtension(JvmProtoBuf.jvmClassFlags))) {
            enumSetNoneOf.add(ProtoBufClassKind.JVM_EXT_JVM_CLASS_FLAGS);
        }
        if (old.getExtensionCount(JsProtoBuf.classAnnotation) != r9.getExtensionCount(JsProtoBuf.classAnnotation)) {
            enumSetNoneOf.add(ProtoBufClassKind.JS_EXT_CLASS_ANNOTATION_LIST);
        } else {
            int extensionCount2 = old.getExtensionCount(JsProtoBuf.classAnnotation) - 1;
            if (extensionCount2 >= 0) {
                int i3 = 0;
                while (true) {
                    Object extension7 = old.getExtension(JsProtoBuf.classAnnotation, i3);
                    Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                    Object extension8 = r9.getExtension(JsProtoBuf.classAnnotation, i3);
                    Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                    if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                        enumSetNoneOf.add(ProtoBufClassKind.JS_EXT_CLASS_ANNOTATION_LIST);
                    }
                    if (i3 == extensionCount2) {
                        break;
                    }
                    i3++;
                }
            }
        }
        if (old.hasExtension(JsProtoBuf.classContainingFileId) != r9.hasExtension(JsProtoBuf.classContainingFileId)) {
            enumSetNoneOf.add(ProtoBufClassKind.JS_EXT_CLASS_CONTAINING_FILE_ID);
        }
        if (old.hasExtension(JsProtoBuf.classContainingFileId) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.classContainingFileId), r9.getExtension(JsProtoBuf.classContainingFileId))) {
            enumSetNoneOf.add(ProtoBufClassKind.JS_EXT_CLASS_CONTAINING_FILE_ID);
        }
        if (old.hasExtension(JavaClassProtoBuf.isPackagePrivateClass) != r9.hasExtension(JavaClassProtoBuf.isPackagePrivateClass)) {
            enumSetNoneOf.add(ProtoBufClassKind.JAVA_EXT_IS_PACKAGE_PRIVATE_CLASS);
        }
        if (old.hasExtension(JavaClassProtoBuf.isPackagePrivateClass) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isPackagePrivateClass), r9.getExtension(JavaClassProtoBuf.isPackagePrivateClass))) {
            enumSetNoneOf.add(ProtoBufClassKind.JAVA_EXT_IS_PACKAGE_PRIVATE_CLASS);
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.classAnnotation) != r9.getExtensionCount(BuiltInsProtoBuf.classAnnotation)) {
            enumSetNoneOf.add(ProtoBufClassKind.BUILT_INS_EXT_CLASS_ANNOTATION_LIST);
        } else {
            int extensionCount3 = old.getExtensionCount(BuiltInsProtoBuf.classAnnotation) - 1;
            if (extensionCount3 >= 0) {
                int i4 = 0;
                while (true) {
                    Object extension9 = old.getExtension(BuiltInsProtoBuf.classAnnotation, i4);
                    Intrinsics.checkNotNullExpressionValue(extension9, "getExtension(...)");
                    Object extension10 = r9.getExtension(BuiltInsProtoBuf.classAnnotation, i4);
                    Intrinsics.checkNotNullExpressionValue(extension10, "getExtension(...)");
                    if (!checkEquals((ProtoBuf.Annotation) extension9, (ProtoBuf.Annotation) extension10)) {
                        enumSetNoneOf.add(ProtoBufClassKind.BUILT_INS_EXT_CLASS_ANNOTATION_LIST);
                    }
                    if (i4 == extensionCount3) {
                        break;
                    }
                    i4++;
                }
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.classAnnotation) != r9.getExtensionCount(KlibMetadataProtoBuf.classAnnotation)) {
            enumSetNoneOf.add(ProtoBufClassKind.KLIB_EXT_CLASS_ANNOTATION_LIST);
        } else {
            int extensionCount4 = old.getExtensionCount(KlibMetadataProtoBuf.classAnnotation) - 1;
            if (extensionCount4 >= 0) {
                while (true) {
                    Object extension11 = old.getExtension(KlibMetadataProtoBuf.classAnnotation, i);
                    Intrinsics.checkNotNullExpressionValue(extension11, "getExtension(...)");
                    Object extension12 = r9.getExtension(KlibMetadataProtoBuf.classAnnotation, i);
                    Intrinsics.checkNotNullExpressionValue(extension12, "getExtension(...)");
                    if (!checkEquals((ProtoBuf.Annotation) extension11, (ProtoBuf.Annotation) extension12)) {
                        enumSetNoneOf.add(ProtoBufClassKind.KLIB_EXT_CLASS_ANNOTATION_LIST);
                    }
                    if (i == extensionCount4) {
                        break;
                    }
                    i++;
                }
            }
        }
        Intrinsics.checkNotNull(enumSetNoneOf);
        return enumSetNoneOf;
    }

    public boolean checkEquals(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.hasFlags() != function.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != function.getFlags()) || old.hasOldFlags() != function.hasOldFlags()) {
            return false;
        }
        if ((old.hasOldFlags() && old.getOldFlags() != function.getOldFlags()) || !checkStringEquals(old.getName(), function.getName()) || old.hasReturnType() != function.hasReturnType()) {
            return false;
        }
        if (old.hasReturnType()) {
            ProtoBuf.Type returnType = old.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            ProtoBuf.Type returnType2 = function.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType2, "getReturnType(...)");
            if (!checkEquals(returnType, returnType2)) {
                return false;
            }
        }
        if (old.hasReturnTypeId() != function.hasReturnTypeId()) {
            return false;
        }
        if (old.hasReturnTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getReturnTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(function.getReturnTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (!checkEqualsFunctionTypeParameter(old, function) || old.hasReceiverType() != function.hasReceiverType()) {
            return false;
        }
        if (old.hasReceiverType()) {
            ProtoBuf.Type receiverType = old.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType, "getReceiverType(...)");
            ProtoBuf.Type receiverType2 = function.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType2, "getReceiverType(...)");
            if (!checkEquals(receiverType, receiverType2)) {
                return false;
            }
        }
        if (old.hasReceiverTypeId() != function.hasReceiverTypeId()) {
            return false;
        }
        if (old.hasReceiverTypeId()) {
            ProtoBuf.Type type3 = this.oldTypeTable.getType(old.getReceiverTypeId());
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            ProtoBuf.Type type4 = this.newTypeTable.getType(function.getReceiverTypeId());
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            if (!checkEquals(type3, type4)) {
                return false;
            }
        }
        if (!checkEqualsFunctionContextReceiverType(old, function) || !checkEqualsFunctionContextReceiverTypeId(old, function) || !checkEqualsFunctionValueParameter(old, function) || !checkEqualsFunctionVersionRequirement(old, function) || old.hasContract() != function.hasContract()) {
            return false;
        }
        if (old.hasContract()) {
            ProtoBuf.Contract contract = old.getContract();
            Intrinsics.checkNotNullExpressionValue(contract, "getContract(...)");
            ProtoBuf.Contract contract2 = function.getContract();
            Intrinsics.checkNotNullExpressionValue(contract2, "getContract(...)");
            if (!checkEquals(contract, contract2)) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.methodSignature) != function.hasExtension(JvmProtoBuf.methodSignature)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.methodSignature)) {
            Object extension = old.getExtension(JvmProtoBuf.methodSignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            Object extension2 = function.getExtension(JvmProtoBuf.methodSignature);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkEquals((JvmProtoBuf.JvmMethodSignature) extension, (JvmProtoBuf.JvmMethodSignature) extension2)) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.lambdaClassOriginName) != function.hasExtension(JvmProtoBuf.lambdaClassOriginName)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.lambdaClassOriginName)) {
            Object extension3 = old.getExtension(JvmProtoBuf.lambdaClassOriginName);
            Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
            int iIntValue = ((Number) extension3).intValue();
            Object extension4 = function.getExtension(JvmProtoBuf.lambdaClassOriginName);
            Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
            if (!checkStringEquals(iIntValue, ((Number) extension4).intValue())) {
                return false;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.functionAnnotation) != function.getExtensionCount(JsProtoBuf.functionAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JsProtoBuf.functionAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension5 = old.getExtension(JsProtoBuf.functionAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = function.getExtension(JsProtoBuf.functionAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.hasExtension(JsProtoBuf.functionContainingFileId) != function.hasExtension(JsProtoBuf.functionContainingFileId)) {
            return false;
        }
        if ((old.hasExtension(JsProtoBuf.functionContainingFileId) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.functionContainingFileId), function.getExtension(JsProtoBuf.functionContainingFileId))) || old.hasExtension(JavaClassProtoBuf.isStaticMethod) != function.hasExtension(JavaClassProtoBuf.isStaticMethod)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isStaticMethod) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isStaticMethod), function.getExtension(JavaClassProtoBuf.isStaticMethod))) || old.hasExtension(JavaClassProtoBuf.isPackagePrivateMethod) != function.hasExtension(JavaClassProtoBuf.isPackagePrivateMethod)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isPackagePrivateMethod) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isPackagePrivateMethod), function.getExtension(JavaClassProtoBuf.isPackagePrivateMethod))) || old.getExtensionCount(BuiltInsProtoBuf.functionAnnotation) != function.getExtensionCount(BuiltInsProtoBuf.functionAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(BuiltInsProtoBuf.functionAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension7 = old.getExtension(BuiltInsProtoBuf.functionAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = function.getExtension(BuiltInsProtoBuf.functionAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.functionAnnotation) != function.getExtensionCount(KlibMetadataProtoBuf.functionAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(KlibMetadataProtoBuf.functionAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension9 = old.getExtension(KlibMetadataProtoBuf.functionAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension9, "getExtension(...)");
                Object extension10 = function.getExtension(KlibMetadataProtoBuf.functionAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension10, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension9, (ProtoBuf.Annotation) extension10)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation) != function.getExtensionCount(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation)) {
            return false;
        }
        int extensionCount4 = old.getExtensionCount(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension11 = old.getExtension(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension11, "getExtension(...)");
                Object extension12 = function.getExtension(KlibMetadataProtoBuf.functionExtensionReceiverAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension12, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension11, (ProtoBuf.Annotation) extension12)) {
                    return false;
                }
                if (i4 != extensionCount4) {
                    i4++;
                }
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.Property old, ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(property, "new");
        if (old.hasFlags() != property.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != property.getFlags()) || old.hasOldFlags() != property.hasOldFlags()) {
            return false;
        }
        if ((old.hasOldFlags() && old.getOldFlags() != property.getOldFlags()) || !checkStringEquals(old.getName(), property.getName()) || old.hasReturnType() != property.hasReturnType()) {
            return false;
        }
        if (old.hasReturnType()) {
            ProtoBuf.Type returnType = old.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
            ProtoBuf.Type returnType2 = property.getReturnType();
            Intrinsics.checkNotNullExpressionValue(returnType2, "getReturnType(...)");
            if (!checkEquals(returnType, returnType2)) {
                return false;
            }
        }
        if (old.hasReturnTypeId() != property.hasReturnTypeId()) {
            return false;
        }
        if (old.hasReturnTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getReturnTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(property.getReturnTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (!checkEqualsPropertyTypeParameter(old, property) || old.hasReceiverType() != property.hasReceiverType()) {
            return false;
        }
        if (old.hasReceiverType()) {
            ProtoBuf.Type receiverType = old.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType, "getReceiverType(...)");
            ProtoBuf.Type receiverType2 = property.getReceiverType();
            Intrinsics.checkNotNullExpressionValue(receiverType2, "getReceiverType(...)");
            if (!checkEquals(receiverType, receiverType2)) {
                return false;
            }
        }
        if (old.hasReceiverTypeId() != property.hasReceiverTypeId()) {
            return false;
        }
        if (old.hasReceiverTypeId()) {
            ProtoBuf.Type type3 = this.oldTypeTable.getType(old.getReceiverTypeId());
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            ProtoBuf.Type type4 = this.newTypeTable.getType(property.getReceiverTypeId());
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            if (!checkEquals(type3, type4)) {
                return false;
            }
        }
        if (!checkEqualsPropertyContextReceiverType(old, property) || !checkEqualsPropertyContextReceiverTypeId(old, property) || old.hasSetterValueParameter() != property.hasSetterValueParameter()) {
            return false;
        }
        if (old.hasSetterValueParameter()) {
            ProtoBuf.ValueParameter setterValueParameter = old.getSetterValueParameter();
            Intrinsics.checkNotNullExpressionValue(setterValueParameter, "getSetterValueParameter(...)");
            ProtoBuf.ValueParameter setterValueParameter2 = property.getSetterValueParameter();
            Intrinsics.checkNotNullExpressionValue(setterValueParameter2, "getSetterValueParameter(...)");
            if (!checkEquals(setterValueParameter, setterValueParameter2)) {
                return false;
            }
        }
        if (old.hasGetterFlags() != property.hasGetterFlags()) {
            return false;
        }
        if ((old.hasGetterFlags() && old.getGetterFlags() != property.getGetterFlags()) || old.hasSetterFlags() != property.hasSetterFlags()) {
            return false;
        }
        if ((old.hasSetterFlags() && old.getSetterFlags() != property.getSetterFlags()) || !checkEqualsPropertyVersionRequirement(old, property) || old.hasExtension(JvmProtoBuf.propertySignature) != property.hasExtension(JvmProtoBuf.propertySignature)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.propertySignature)) {
            Object extension = old.getExtension(JvmProtoBuf.propertySignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            Object extension2 = property.getExtension(JvmProtoBuf.propertySignature);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkEquals((JvmProtoBuf.JvmPropertySignature) extension, (JvmProtoBuf.JvmPropertySignature) extension2)) {
                return false;
            }
        }
        if (old.hasExtension(JvmProtoBuf.flags) != property.hasExtension(JvmProtoBuf.flags)) {
            return false;
        }
        if ((old.hasExtension(JvmProtoBuf.flags) && !Intrinsics.areEqual(old.getExtension(JvmProtoBuf.flags), property.getExtension(JvmProtoBuf.flags))) || old.getExtensionCount(JsProtoBuf.propertyAnnotation) != property.getExtensionCount(JsProtoBuf.propertyAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JsProtoBuf.propertyAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension3 = old.getExtension(JsProtoBuf.propertyAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = property.getExtension(JsProtoBuf.propertyAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.propertyGetterAnnotation) != property.getExtensionCount(JsProtoBuf.propertyGetterAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(JsProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension5 = old.getExtension(JsProtoBuf.propertyGetterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = property.getExtension(JsProtoBuf.propertyGetterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.propertySetterAnnotation) != property.getExtensionCount(JsProtoBuf.propertySetterAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(JsProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension7 = old.getExtension(JsProtoBuf.propertySetterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = property.getExtension(JsProtoBuf.propertySetterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.hasExtension(JsProtoBuf.compileTimeValue) != property.hasExtension(JsProtoBuf.compileTimeValue)) {
            return false;
        }
        if (old.hasExtension(JsProtoBuf.compileTimeValue)) {
            Object extension9 = old.getExtension(JsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension9, "getExtension(...)");
            Object extension10 = property.getExtension(JsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension10, "getExtension(...)");
            if (!checkEquals((ProtoBuf.Annotation.Argument.Value) extension9, (ProtoBuf.Annotation.Argument.Value) extension10)) {
                return false;
            }
        }
        if (old.hasExtension(JsProtoBuf.propertyContainingFileId) != property.hasExtension(JsProtoBuf.propertyContainingFileId)) {
            return false;
        }
        if ((old.hasExtension(JsProtoBuf.propertyContainingFileId) && !Intrinsics.areEqual(old.getExtension(JsProtoBuf.propertyContainingFileId), property.getExtension(JsProtoBuf.propertyContainingFileId))) || old.hasExtension(JavaClassProtoBuf.isStaticField) != property.hasExtension(JavaClassProtoBuf.isStaticField)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isStaticField) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isStaticField), property.getExtension(JavaClassProtoBuf.isStaticField))) || old.hasExtension(JavaClassProtoBuf.isPackagePrivateField) != property.hasExtension(JavaClassProtoBuf.isPackagePrivateField)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isPackagePrivateField) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isPackagePrivateField), property.getExtension(JavaClassProtoBuf.isPackagePrivateField))) || old.getExtensionCount(BuiltInsProtoBuf.propertyAnnotation) != property.getExtensionCount(BuiltInsProtoBuf.propertyAnnotation)) {
            return false;
        }
        int extensionCount4 = old.getExtensionCount(BuiltInsProtoBuf.propertyAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension11 = old.getExtension(BuiltInsProtoBuf.propertyAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension11, "getExtension(...)");
                Object extension12 = property.getExtension(BuiltInsProtoBuf.propertyAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension12, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension11, (ProtoBuf.Annotation) extension12)) {
                    return false;
                }
                if (i4 == extensionCount4) {
                    break;
                }
                i4++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.propertyGetterAnnotation) != property.getExtensionCount(BuiltInsProtoBuf.propertyGetterAnnotation)) {
            return false;
        }
        int extensionCount5 = old.getExtensionCount(BuiltInsProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount5 >= 0) {
            int i5 = 0;
            while (true) {
                Object extension13 = old.getExtension(BuiltInsProtoBuf.propertyGetterAnnotation, i5);
                Intrinsics.checkNotNullExpressionValue(extension13, "getExtension(...)");
                Object extension14 = property.getExtension(BuiltInsProtoBuf.propertyGetterAnnotation, i5);
                Intrinsics.checkNotNullExpressionValue(extension14, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension13, (ProtoBuf.Annotation) extension14)) {
                    return false;
                }
                if (i5 == extensionCount5) {
                    break;
                }
                i5++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.propertySetterAnnotation) != property.getExtensionCount(BuiltInsProtoBuf.propertySetterAnnotation)) {
            return false;
        }
        int extensionCount6 = old.getExtensionCount(BuiltInsProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount6 >= 0) {
            int i6 = 0;
            while (true) {
                Object extension15 = old.getExtension(BuiltInsProtoBuf.propertySetterAnnotation, i6);
                Intrinsics.checkNotNullExpressionValue(extension15, "getExtension(...)");
                Object extension16 = property.getExtension(BuiltInsProtoBuf.propertySetterAnnotation, i6);
                Intrinsics.checkNotNullExpressionValue(extension16, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension15, (ProtoBuf.Annotation) extension16)) {
                    return false;
                }
                if (i6 == extensionCount6) {
                    break;
                }
                i6++;
            }
        }
        if (old.hasExtension(BuiltInsProtoBuf.compileTimeValue) != property.hasExtension(BuiltInsProtoBuf.compileTimeValue)) {
            return false;
        }
        if (old.hasExtension(BuiltInsProtoBuf.compileTimeValue)) {
            Object extension17 = old.getExtension(BuiltInsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension17, "getExtension(...)");
            Object extension18 = property.getExtension(BuiltInsProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension18, "getExtension(...)");
            if (!checkEquals((ProtoBuf.Annotation.Argument.Value) extension17, (ProtoBuf.Annotation.Argument.Value) extension18)) {
                return false;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertyAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertyAnnotation)) {
            return false;
        }
        int extensionCount7 = old.getExtensionCount(KlibMetadataProtoBuf.propertyAnnotation) - 1;
        if (extensionCount7 >= 0) {
            int i7 = 0;
            while (true) {
                Object extension19 = old.getExtension(KlibMetadataProtoBuf.propertyAnnotation, i7);
                Intrinsics.checkNotNullExpressionValue(extension19, "getExtension(...)");
                Object extension20 = property.getExtension(KlibMetadataProtoBuf.propertyAnnotation, i7);
                Intrinsics.checkNotNullExpressionValue(extension20, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension19, (ProtoBuf.Annotation) extension20)) {
                    return false;
                }
                if (i7 == extensionCount7) {
                    break;
                }
                i7++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertyGetterAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertyGetterAnnotation)) {
            return false;
        }
        int extensionCount8 = old.getExtensionCount(KlibMetadataProtoBuf.propertyGetterAnnotation) - 1;
        if (extensionCount8 >= 0) {
            int i8 = 0;
            while (true) {
                Object extension21 = old.getExtension(KlibMetadataProtoBuf.propertyGetterAnnotation, i8);
                Intrinsics.checkNotNullExpressionValue(extension21, "getExtension(...)");
                Object extension22 = property.getExtension(KlibMetadataProtoBuf.propertyGetterAnnotation, i8);
                Intrinsics.checkNotNullExpressionValue(extension22, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension21, (ProtoBuf.Annotation) extension22)) {
                    return false;
                }
                if (i8 == extensionCount8) {
                    break;
                }
                i8++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertySetterAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertySetterAnnotation)) {
            return false;
        }
        int extensionCount9 = old.getExtensionCount(KlibMetadataProtoBuf.propertySetterAnnotation) - 1;
        if (extensionCount9 >= 0) {
            int i9 = 0;
            while (true) {
                Object extension23 = old.getExtension(KlibMetadataProtoBuf.propertySetterAnnotation, i9);
                Intrinsics.checkNotNullExpressionValue(extension23, "getExtension(...)");
                Object extension24 = property.getExtension(KlibMetadataProtoBuf.propertySetterAnnotation, i9);
                Intrinsics.checkNotNullExpressionValue(extension24, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension23, (ProtoBuf.Annotation) extension24)) {
                    return false;
                }
                if (i9 == extensionCount9) {
                    break;
                }
                i9++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertyBackingFieldAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertyBackingFieldAnnotation)) {
            return false;
        }
        int extensionCount10 = old.getExtensionCount(KlibMetadataProtoBuf.propertyBackingFieldAnnotation) - 1;
        if (extensionCount10 >= 0) {
            int i10 = 0;
            while (true) {
                Object extension25 = old.getExtension(KlibMetadataProtoBuf.propertyBackingFieldAnnotation, i10);
                Intrinsics.checkNotNullExpressionValue(extension25, "getExtension(...)");
                Object extension26 = property.getExtension(KlibMetadataProtoBuf.propertyBackingFieldAnnotation, i10);
                Intrinsics.checkNotNullExpressionValue(extension26, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension25, (ProtoBuf.Annotation) extension26)) {
                    return false;
                }
                if (i10 == extensionCount10) {
                    break;
                }
                i10++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation)) {
            return false;
        }
        int extensionCount11 = old.getExtensionCount(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation) - 1;
        if (extensionCount11 >= 0) {
            int i11 = 0;
            while (true) {
                Object extension27 = old.getExtension(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation, i11);
                Intrinsics.checkNotNullExpressionValue(extension27, "getExtension(...)");
                Object extension28 = property.getExtension(KlibMetadataProtoBuf.propertyDelegatedFieldAnnotation, i11);
                Intrinsics.checkNotNullExpressionValue(extension28, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension27, (ProtoBuf.Annotation) extension28)) {
                    return false;
                }
                if (i11 == extensionCount11) {
                    break;
                }
                i11++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation) != property.getExtensionCount(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation)) {
            return false;
        }
        int extensionCount12 = old.getExtensionCount(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation) - 1;
        if (extensionCount12 >= 0) {
            int i12 = 0;
            while (true) {
                Object extension29 = old.getExtension(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation, i12);
                Intrinsics.checkNotNullExpressionValue(extension29, "getExtension(...)");
                Object extension30 = property.getExtension(KlibMetadataProtoBuf.propertyExtensionReceiverAnnotation, i12);
                Intrinsics.checkNotNullExpressionValue(extension30, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension29, (ProtoBuf.Annotation) extension30)) {
                    return false;
                }
                if (i12 == extensionCount12) {
                    break;
                }
                i12++;
            }
        }
        if (old.hasExtension(KlibMetadataProtoBuf.compileTimeValue) != property.hasExtension(KlibMetadataProtoBuf.compileTimeValue)) {
            return false;
        }
        if (old.hasExtension(KlibMetadataProtoBuf.compileTimeValue)) {
            Object extension31 = old.getExtension(KlibMetadataProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension31, "getExtension(...)");
            Object extension32 = property.getExtension(KlibMetadataProtoBuf.compileTimeValue);
            Intrinsics.checkNotNullExpressionValue(extension32, "getExtension(...)");
            if (!checkEquals((ProtoBuf.Annotation.Argument.Value) extension31, (ProtoBuf.Annotation.Argument.Value) extension32)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.TypeAlias old, ProtoBuf.TypeAlias typeAlias) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeAlias, "new");
        if (old.hasFlags() != typeAlias.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != typeAlias.getFlags()) || !checkStringEquals(old.getName(), typeAlias.getName()) || !checkEqualsTypeAliasTypeParameter(old, typeAlias) || old.hasUnderlyingType() != typeAlias.hasUnderlyingType()) {
            return false;
        }
        if (old.hasUnderlyingType()) {
            ProtoBuf.Type underlyingType = old.getUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(underlyingType, "getUnderlyingType(...)");
            ProtoBuf.Type underlyingType2 = typeAlias.getUnderlyingType();
            Intrinsics.checkNotNullExpressionValue(underlyingType2, "getUnderlyingType(...)");
            if (!checkEquals(underlyingType, underlyingType2)) {
                return false;
            }
        }
        if (old.hasUnderlyingTypeId() != typeAlias.hasUnderlyingTypeId()) {
            return false;
        }
        if (old.hasUnderlyingTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(typeAlias.getUnderlyingTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (old.hasExpandedType() != typeAlias.hasExpandedType()) {
            return false;
        }
        if (old.hasExpandedType()) {
            ProtoBuf.Type expandedType = old.getExpandedType();
            Intrinsics.checkNotNullExpressionValue(expandedType, "getExpandedType(...)");
            ProtoBuf.Type expandedType2 = typeAlias.getExpandedType();
            Intrinsics.checkNotNullExpressionValue(expandedType2, "getExpandedType(...)");
            if (!checkEquals(expandedType, expandedType2)) {
                return false;
            }
        }
        if (old.hasExpandedTypeId() != typeAlias.hasExpandedTypeId()) {
            return false;
        }
        if (old.hasExpandedTypeId()) {
            ProtoBuf.Type type3 = this.oldTypeTable.getType(old.getExpandedTypeId());
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            ProtoBuf.Type type4 = this.newTypeTable.getType(typeAlias.getExpandedTypeId());
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            if (!checkEquals(type3, type4)) {
                return false;
            }
        }
        return checkEqualsTypeAliasAnnotation(old, typeAlias) && checkEqualsTypeAliasVersionRequirement(old, typeAlias);
    }

    public boolean checkEquals(ProtoBuf.VersionRequirementTable old, ProtoBuf.VersionRequirementTable versionRequirementTable) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "new");
        return checkEqualsVersionRequirementTableRequirement(old, versionRequirementTable);
    }

    public boolean checkEquals(ProtoBuf.TypeParameter old, ProtoBuf.TypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeParameter, "new");
        if (old.getId() != typeParameter.getId() || !checkStringEquals(old.getName(), typeParameter.getName()) || old.hasReified() != typeParameter.hasReified()) {
            return false;
        }
        if ((old.hasReified() && old.getReified() != typeParameter.getReified()) || old.hasVariance() != typeParameter.hasVariance()) {
            return false;
        }
        if ((old.hasVariance() && old.getVariance() != typeParameter.getVariance()) || !checkEqualsTypeParameterUpperBound(old, typeParameter) || !checkEqualsTypeParameterUpperBoundId(old, typeParameter) || old.getExtensionCount(JvmProtoBuf.typeParameterAnnotation) != typeParameter.getExtensionCount(JvmProtoBuf.typeParameterAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JvmProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension = old.getExtension(JvmProtoBuf.typeParameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                Object extension2 = typeParameter.getExtension(JvmProtoBuf.typeParameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension, (ProtoBuf.Annotation) extension2)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.typeParameterAnnotation) != typeParameter.getExtensionCount(JsProtoBuf.typeParameterAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(JsProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension3 = old.getExtension(JsProtoBuf.typeParameterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = typeParameter.getExtension(JsProtoBuf.typeParameterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.typeParameterAnnotation) != typeParameter.getExtensionCount(BuiltInsProtoBuf.typeParameterAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(BuiltInsProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension5 = old.getExtension(BuiltInsProtoBuf.typeParameterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = typeParameter.getExtension(BuiltInsProtoBuf.typeParameterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.typeParameterAnnotation) != typeParameter.getExtensionCount(KlibMetadataProtoBuf.typeParameterAnnotation)) {
            return false;
        }
        int extensionCount4 = old.getExtensionCount(KlibMetadataProtoBuf.typeParameterAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension7 = old.getExtension(KlibMetadataProtoBuf.typeParameterAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = typeParameter.getExtension(KlibMetadataProtoBuf.typeParameterAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i4 != extensionCount4) {
                    i4++;
                }
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.Type old, ProtoBuf.Type type) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(type, "new");
        if (!checkEqualsTypeArgument(old, type) || old.hasNullable() != type.hasNullable()) {
            return false;
        }
        if ((old.hasNullable() && old.getNullable() != type.getNullable()) || old.hasFlexibleTypeCapabilitiesId() != type.hasFlexibleTypeCapabilitiesId()) {
            return false;
        }
        if ((old.hasFlexibleTypeCapabilitiesId() && !checkStringEquals(old.getFlexibleTypeCapabilitiesId(), type.getFlexibleTypeCapabilitiesId())) || old.hasFlexibleUpperBound() != type.hasFlexibleUpperBound()) {
            return false;
        }
        if (old.hasFlexibleUpperBound()) {
            ProtoBuf.Type flexibleUpperBound = old.getFlexibleUpperBound();
            Intrinsics.checkNotNullExpressionValue(flexibleUpperBound, "getFlexibleUpperBound(...)");
            ProtoBuf.Type flexibleUpperBound2 = type.getFlexibleUpperBound();
            Intrinsics.checkNotNullExpressionValue(flexibleUpperBound2, "getFlexibleUpperBound(...)");
            if (!checkEquals(flexibleUpperBound, flexibleUpperBound2)) {
                return false;
            }
        }
        if (old.hasFlexibleUpperBoundId() != type.hasFlexibleUpperBoundId()) {
            return false;
        }
        if (old.hasFlexibleUpperBoundId()) {
            ProtoBuf.Type type2 = this.oldTypeTable.getType(old.getFlexibleUpperBoundId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            ProtoBuf.Type type3 = this.newTypeTable.getType(type.getFlexibleUpperBoundId());
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            if (!checkEquals(type2, type3)) {
                return false;
            }
        }
        if (old.hasClassName() != type.hasClassName()) {
            return false;
        }
        if ((old.hasClassName() && !checkClassIdEquals(old.getClassName(), type.getClassName())) || old.hasTypeParameter() != type.hasTypeParameter()) {
            return false;
        }
        if ((old.hasTypeParameter() && old.getTypeParameter() != type.getTypeParameter()) || old.hasTypeParameterName() != type.hasTypeParameterName()) {
            return false;
        }
        if ((old.hasTypeParameterName() && !checkStringEquals(old.getTypeParameterName(), type.getTypeParameterName())) || old.hasTypeAliasName() != type.hasTypeAliasName()) {
            return false;
        }
        if ((old.hasTypeAliasName() && !checkClassIdEquals(old.getTypeAliasName(), type.getTypeAliasName())) || old.hasOuterType() != type.hasOuterType()) {
            return false;
        }
        if (old.hasOuterType()) {
            ProtoBuf.Type outerType = old.getOuterType();
            Intrinsics.checkNotNullExpressionValue(outerType, "getOuterType(...)");
            ProtoBuf.Type outerType2 = type.getOuterType();
            Intrinsics.checkNotNullExpressionValue(outerType2, "getOuterType(...)");
            if (!checkEquals(outerType, outerType2)) {
                return false;
            }
        }
        if (old.hasOuterTypeId() != type.hasOuterTypeId()) {
            return false;
        }
        if (old.hasOuterTypeId()) {
            ProtoBuf.Type type4 = this.oldTypeTable.getType(old.getOuterTypeId());
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            ProtoBuf.Type type5 = this.newTypeTable.getType(type.getOuterTypeId());
            Intrinsics.checkNotNullExpressionValue(type5, "getType(...)");
            if (!checkEquals(type4, type5)) {
                return false;
            }
        }
        if (old.hasAbbreviatedType() != type.hasAbbreviatedType()) {
            return false;
        }
        if (old.hasAbbreviatedType()) {
            ProtoBuf.Type abbreviatedType = old.getAbbreviatedType();
            Intrinsics.checkNotNullExpressionValue(abbreviatedType, "getAbbreviatedType(...)");
            ProtoBuf.Type abbreviatedType2 = type.getAbbreviatedType();
            Intrinsics.checkNotNullExpressionValue(abbreviatedType2, "getAbbreviatedType(...)");
            if (!checkEquals(abbreviatedType, abbreviatedType2)) {
                return false;
            }
        }
        if (old.hasAbbreviatedTypeId() != type.hasAbbreviatedTypeId()) {
            return false;
        }
        if (old.hasAbbreviatedTypeId()) {
            ProtoBuf.Type type6 = this.oldTypeTable.getType(old.getAbbreviatedTypeId());
            Intrinsics.checkNotNullExpressionValue(type6, "getType(...)");
            ProtoBuf.Type type7 = this.newTypeTable.getType(type.getAbbreviatedTypeId());
            Intrinsics.checkNotNullExpressionValue(type7, "getType(...)");
            if (!checkEquals(type6, type7)) {
                return false;
            }
        }
        if (old.hasFlags() != type.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != type.getFlags()) || old.getExtensionCount(JvmProtoBuf.typeAnnotation) != type.getExtensionCount(JvmProtoBuf.typeAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JvmProtoBuf.typeAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension = old.getExtension(JvmProtoBuf.typeAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                Object extension2 = type.getExtension(JvmProtoBuf.typeAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension, (ProtoBuf.Annotation) extension2)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.hasExtension(JvmProtoBuf.isRaw) != type.hasExtension(JvmProtoBuf.isRaw)) {
            return false;
        }
        if ((old.hasExtension(JvmProtoBuf.isRaw) && !Intrinsics.areEqual(old.getExtension(JvmProtoBuf.isRaw), type.getExtension(JvmProtoBuf.isRaw))) || old.getExtensionCount(JsProtoBuf.typeAnnotation) != type.getExtensionCount(JsProtoBuf.typeAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(JsProtoBuf.typeAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension3 = old.getExtension(JsProtoBuf.typeAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = type.getExtension(JsProtoBuf.typeAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.typeAnnotation) != type.getExtensionCount(BuiltInsProtoBuf.typeAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(BuiltInsProtoBuf.typeAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension5 = old.getExtension(BuiltInsProtoBuf.typeAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = type.getExtension(BuiltInsProtoBuf.typeAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.typeAnnotation) != type.getExtensionCount(KlibMetadataProtoBuf.typeAnnotation)) {
            return false;
        }
        int extensionCount4 = old.getExtensionCount(KlibMetadataProtoBuf.typeAnnotation) - 1;
        if (extensionCount4 >= 0) {
            int i4 = 0;
            while (true) {
                Object extension7 = old.getExtension(KlibMetadataProtoBuf.typeAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = type.getExtension(KlibMetadataProtoBuf.typeAnnotation, i4);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i4 != extensionCount4) {
                    i4++;
                }
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.Constructor old, ProtoBuf.Constructor constructor) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(constructor, "new");
        if (old.hasFlags() != constructor.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != constructor.getFlags()) || !checkEqualsConstructorValueParameter(old, constructor) || !checkEqualsConstructorVersionRequirement(old, constructor) || old.hasExtension(JvmProtoBuf.constructorSignature) != constructor.hasExtension(JvmProtoBuf.constructorSignature)) {
            return false;
        }
        if (old.hasExtension(JvmProtoBuf.constructorSignature)) {
            Object extension = old.getExtension(JvmProtoBuf.constructorSignature);
            Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
            Object extension2 = constructor.getExtension(JvmProtoBuf.constructorSignature);
            Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
            if (!checkEquals((JvmProtoBuf.JvmMethodSignature) extension, (JvmProtoBuf.JvmMethodSignature) extension2)) {
                return false;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.constructorAnnotation) != constructor.getExtensionCount(JsProtoBuf.constructorAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JsProtoBuf.constructorAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension3 = old.getExtension(JsProtoBuf.constructorAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = constructor.getExtension(JsProtoBuf.constructorAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.hasExtension(JavaClassProtoBuf.isPackagePrivateConstructor) != constructor.hasExtension(JavaClassProtoBuf.isPackagePrivateConstructor)) {
            return false;
        }
        if ((old.hasExtension(JavaClassProtoBuf.isPackagePrivateConstructor) && !Intrinsics.areEqual(old.getExtension(JavaClassProtoBuf.isPackagePrivateConstructor), constructor.getExtension(JavaClassProtoBuf.isPackagePrivateConstructor))) || old.getExtensionCount(BuiltInsProtoBuf.constructorAnnotation) != constructor.getExtensionCount(BuiltInsProtoBuf.constructorAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(BuiltInsProtoBuf.constructorAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension5 = old.getExtension(BuiltInsProtoBuf.constructorAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = constructor.getExtension(BuiltInsProtoBuf.constructorAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.constructorAnnotation) != constructor.getExtensionCount(KlibMetadataProtoBuf.constructorAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(KlibMetadataProtoBuf.constructorAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension7 = old.getExtension(KlibMetadataProtoBuf.constructorAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension7, "getExtension(...)");
                Object extension8 = constructor.getExtension(KlibMetadataProtoBuf.constructorAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension8, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension7, (ProtoBuf.Annotation) extension8)) {
                    return false;
                }
                if (i3 != extensionCount3) {
                    i3++;
                }
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.EnumEntry old, ProtoBuf.EnumEntry enumEntry) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(enumEntry, "new");
        if (old.hasName() != enumEntry.hasName()) {
            return false;
        }
        if ((old.hasName() && !checkStringEquals(old.getName(), enumEntry.getName())) || old.getExtensionCount(JsProtoBuf.enumEntryAnnotation) != enumEntry.getExtensionCount(JsProtoBuf.enumEntryAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JsProtoBuf.enumEntryAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension = old.getExtension(JsProtoBuf.enumEntryAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                Object extension2 = enumEntry.getExtension(JsProtoBuf.enumEntryAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension, (ProtoBuf.Annotation) extension2)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.enumEntryAnnotation) != enumEntry.getExtensionCount(BuiltInsProtoBuf.enumEntryAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(BuiltInsProtoBuf.enumEntryAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension3 = old.getExtension(BuiltInsProtoBuf.enumEntryAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = enumEntry.getExtension(BuiltInsProtoBuf.enumEntryAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.enumEntryAnnotation) != enumEntry.getExtensionCount(KlibMetadataProtoBuf.enumEntryAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(KlibMetadataProtoBuf.enumEntryAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension5 = old.getExtension(KlibMetadataProtoBuf.enumEntryAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = enumEntry.getExtension(KlibMetadataProtoBuf.enumEntryAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i3 == extensionCount3) {
                    break;
                }
                i3++;
            }
        }
        if (old.hasExtension(KlibMetadataProtoBuf.enumEntryOrdinal) != enumEntry.hasExtension(KlibMetadataProtoBuf.enumEntryOrdinal)) {
            return false;
        }
        return !old.hasExtension(KlibMetadataProtoBuf.enumEntryOrdinal) || Intrinsics.areEqual(old.getExtension(KlibMetadataProtoBuf.enumEntryOrdinal), enumEntry.getExtension(KlibMetadataProtoBuf.enumEntryOrdinal));
    }

    public boolean checkEquals(ProtoBuf.Annotation old, ProtoBuf.Annotation annotation) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(annotation, "new");
        return checkClassIdEquals(old.getId(), annotation.getId()) && checkEqualsAnnotationArgument(old, annotation);
    }

    public boolean checkEquals(ProtoBuf.ValueParameter old, ProtoBuf.ValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(valueParameter, "new");
        if (old.hasFlags() != valueParameter.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != valueParameter.getFlags()) || !checkStringEquals(old.getName(), valueParameter.getName()) || old.hasType() != valueParameter.hasType()) {
            return false;
        }
        if (old.hasType()) {
            ProtoBuf.Type type = old.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = valueParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (old.hasTypeId() != valueParameter.hasTypeId()) {
            return false;
        }
        if (old.hasTypeId()) {
            ProtoBuf.Type type3 = this.oldTypeTable.getType(old.getTypeId());
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            ProtoBuf.Type type4 = this.newTypeTable.getType(valueParameter.getTypeId());
            Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
            if (!checkEquals(type3, type4)) {
                return false;
            }
        }
        if (old.hasVarargElementType() != valueParameter.hasVarargElementType()) {
            return false;
        }
        if (old.hasVarargElementType()) {
            ProtoBuf.Type varargElementType = old.getVarargElementType();
            Intrinsics.checkNotNullExpressionValue(varargElementType, "getVarargElementType(...)");
            ProtoBuf.Type varargElementType2 = valueParameter.getVarargElementType();
            Intrinsics.checkNotNullExpressionValue(varargElementType2, "getVarargElementType(...)");
            if (!checkEquals(varargElementType, varargElementType2)) {
                return false;
            }
        }
        if (old.hasVarargElementTypeId() != valueParameter.hasVarargElementTypeId()) {
            return false;
        }
        if (old.hasVarargElementTypeId()) {
            ProtoBuf.Type type5 = this.oldTypeTable.getType(old.getVarargElementTypeId());
            Intrinsics.checkNotNullExpressionValue(type5, "getType(...)");
            ProtoBuf.Type type6 = this.newTypeTable.getType(valueParameter.getVarargElementTypeId());
            Intrinsics.checkNotNullExpressionValue(type6, "getType(...)");
            if (!checkEquals(type5, type6)) {
                return false;
            }
        }
        if (old.getExtensionCount(JsProtoBuf.parameterAnnotation) != valueParameter.getExtensionCount(JsProtoBuf.parameterAnnotation)) {
            return false;
        }
        int extensionCount = old.getExtensionCount(JsProtoBuf.parameterAnnotation) - 1;
        if (extensionCount >= 0) {
            int i = 0;
            while (true) {
                Object extension = old.getExtension(JsProtoBuf.parameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                Object extension2 = valueParameter.getExtension(JsProtoBuf.parameterAnnotation, i);
                Intrinsics.checkNotNullExpressionValue(extension2, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension, (ProtoBuf.Annotation) extension2)) {
                    return false;
                }
                if (i == extensionCount) {
                    break;
                }
                i++;
            }
        }
        if (old.getExtensionCount(BuiltInsProtoBuf.parameterAnnotation) != valueParameter.getExtensionCount(BuiltInsProtoBuf.parameterAnnotation)) {
            return false;
        }
        int extensionCount2 = old.getExtensionCount(BuiltInsProtoBuf.parameterAnnotation) - 1;
        if (extensionCount2 >= 0) {
            int i2 = 0;
            while (true) {
                Object extension3 = old.getExtension(BuiltInsProtoBuf.parameterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension3, "getExtension(...)");
                Object extension4 = valueParameter.getExtension(BuiltInsProtoBuf.parameterAnnotation, i2);
                Intrinsics.checkNotNullExpressionValue(extension4, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension3, (ProtoBuf.Annotation) extension4)) {
                    return false;
                }
                if (i2 == extensionCount2) {
                    break;
                }
                i2++;
            }
        }
        if (old.getExtensionCount(KlibMetadataProtoBuf.parameterAnnotation) != valueParameter.getExtensionCount(KlibMetadataProtoBuf.parameterAnnotation)) {
            return false;
        }
        int extensionCount3 = old.getExtensionCount(KlibMetadataProtoBuf.parameterAnnotation) - 1;
        if (extensionCount3 >= 0) {
            int i3 = 0;
            while (true) {
                Object extension5 = old.getExtension(KlibMetadataProtoBuf.parameterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension5, "getExtension(...)");
                Object extension6 = valueParameter.getExtension(KlibMetadataProtoBuf.parameterAnnotation, i3);
                Intrinsics.checkNotNullExpressionValue(extension6, "getExtension(...)");
                if (!checkEquals((ProtoBuf.Annotation) extension5, (ProtoBuf.Annotation) extension6)) {
                    return false;
                }
                if (i3 != extensionCount3) {
                    i3++;
                }
            }
        }
        return true;
    }

    public boolean checkEquals(ProtoBuf.Contract old, ProtoBuf.Contract contract) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(contract, "new");
        return checkEqualsContractEffect(old, contract);
    }

    public boolean checkEquals(JvmProtoBuf.JvmMethodSignature old, JvmProtoBuf.JvmMethodSignature jvmMethodSignature) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(jvmMethodSignature, "new");
        if (old.hasName() != jvmMethodSignature.hasName()) {
            return false;
        }
        if ((!old.hasName() || checkStringEquals(old.getName(), jvmMethodSignature.getName())) && old.hasDesc() == jvmMethodSignature.hasDesc()) {
            return !old.hasDesc() || checkStringEquals(old.getDesc(), jvmMethodSignature.getDesc());
        }
        return false;
    }

    public boolean checkEquals(JvmProtoBuf.JvmPropertySignature old, JvmProtoBuf.JvmPropertySignature jvmPropertySignature) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(jvmPropertySignature, "new");
        if (old.hasField() != jvmPropertySignature.hasField()) {
            return false;
        }
        if (old.hasField()) {
            JvmProtoBuf.JvmFieldSignature field = old.getField();
            Intrinsics.checkNotNullExpressionValue(field, "getField(...)");
            JvmProtoBuf.JvmFieldSignature field2 = jvmPropertySignature.getField();
            Intrinsics.checkNotNullExpressionValue(field2, "getField(...)");
            if (!checkEquals(field, field2)) {
                return false;
            }
        }
        if (old.hasSyntheticMethod() != jvmPropertySignature.hasSyntheticMethod()) {
            return false;
        }
        if (old.hasSyntheticMethod()) {
            JvmProtoBuf.JvmMethodSignature syntheticMethod = old.getSyntheticMethod();
            Intrinsics.checkNotNullExpressionValue(syntheticMethod, "getSyntheticMethod(...)");
            JvmProtoBuf.JvmMethodSignature syntheticMethod2 = jvmPropertySignature.getSyntheticMethod();
            Intrinsics.checkNotNullExpressionValue(syntheticMethod2, "getSyntheticMethod(...)");
            if (!checkEquals(syntheticMethod, syntheticMethod2)) {
                return false;
            }
        }
        if (old.hasGetter() != jvmPropertySignature.hasGetter()) {
            return false;
        }
        if (old.hasGetter()) {
            JvmProtoBuf.JvmMethodSignature getter = old.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter, "getGetter(...)");
            JvmProtoBuf.JvmMethodSignature getter2 = jvmPropertySignature.getGetter();
            Intrinsics.checkNotNullExpressionValue(getter2, "getGetter(...)");
            if (!checkEquals(getter, getter2)) {
                return false;
            }
        }
        if (old.hasSetter() != jvmPropertySignature.hasSetter()) {
            return false;
        }
        if (old.hasSetter()) {
            JvmProtoBuf.JvmMethodSignature setter = old.getSetter();
            Intrinsics.checkNotNullExpressionValue(setter, "getSetter(...)");
            JvmProtoBuf.JvmMethodSignature setter2 = jvmPropertySignature.getSetter();
            Intrinsics.checkNotNullExpressionValue(setter2, "getSetter(...)");
            if (!checkEquals(setter, setter2)) {
                return false;
            }
        }
        if (old.hasDelegateMethod() != jvmPropertySignature.hasDelegateMethod()) {
            return false;
        }
        if (!old.hasDelegateMethod()) {
            return true;
        }
        JvmProtoBuf.JvmMethodSignature delegateMethod = old.getDelegateMethod();
        Intrinsics.checkNotNullExpressionValue(delegateMethod, "getDelegateMethod(...)");
        JvmProtoBuf.JvmMethodSignature delegateMethod2 = jvmPropertySignature.getDelegateMethod();
        Intrinsics.checkNotNullExpressionValue(delegateMethod2, "getDelegateMethod(...)");
        return checkEquals(delegateMethod, delegateMethod2);
    }

    public boolean checkEquals(ProtoBuf.Annotation.Argument.Value old, ProtoBuf.Annotation.Argument.Value value) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(value, "new");
        if (old.hasType() != value.hasType()) {
            return false;
        }
        if ((old.hasType() && old.getType() != value.getType()) || old.hasIntValue() != value.hasIntValue()) {
            return false;
        }
        if ((old.hasIntValue() && old.getIntValue() != value.getIntValue()) || old.hasFloatValue() != value.hasFloatValue()) {
            return false;
        }
        if ((old.hasFloatValue() && old.getFloatValue() != value.getFloatValue()) || old.hasDoubleValue() != value.hasDoubleValue()) {
            return false;
        }
        if ((old.hasDoubleValue() && old.getDoubleValue() != value.getDoubleValue()) || old.hasStringValue() != value.hasStringValue()) {
            return false;
        }
        if ((old.hasStringValue() && !checkStringEquals(old.getStringValue(), value.getStringValue())) || old.hasClassId() != value.hasClassId()) {
            return false;
        }
        if ((old.hasClassId() && !checkClassIdEquals(old.getClassId(), value.getClassId())) || old.hasEnumValueId() != value.hasEnumValueId()) {
            return false;
        }
        if ((old.hasEnumValueId() && !checkStringEquals(old.getEnumValueId(), value.getEnumValueId())) || old.hasAnnotation() != value.hasAnnotation()) {
            return false;
        }
        if (old.hasAnnotation()) {
            ProtoBuf.Annotation annotation = old.getAnnotation();
            Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
            ProtoBuf.Annotation annotation2 = value.getAnnotation();
            Intrinsics.checkNotNullExpressionValue(annotation2, "getAnnotation(...)");
            if (!checkEquals(annotation, annotation2)) {
                return false;
            }
        }
        if (!checkEqualsAnnotationArgumentValueArrayElement(old, value) || old.hasArrayDimensionCount() != value.hasArrayDimensionCount()) {
            return false;
        }
        if ((!old.hasArrayDimensionCount() || old.getArrayDimensionCount() == value.getArrayDimensionCount()) && old.hasFlags() == value.hasFlags()) {
            return !old.hasFlags() || old.getFlags() == value.getFlags();
        }
        return false;
    }

    public boolean checkEquals(ProtoBuf.VersionRequirement old, ProtoBuf.VersionRequirement versionRequirement) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(versionRequirement, "new");
        if (old.hasVersion() != versionRequirement.hasVersion()) {
            return false;
        }
        if ((old.hasVersion() && old.getVersion() != versionRequirement.getVersion()) || old.hasVersionFull() != versionRequirement.hasVersionFull()) {
            return false;
        }
        if ((old.hasVersionFull() && old.getVersionFull() != versionRequirement.getVersionFull()) || old.hasLevel() != versionRequirement.hasLevel()) {
            return false;
        }
        if ((old.hasLevel() && old.getLevel() != versionRequirement.getLevel()) || old.hasErrorCode() != versionRequirement.hasErrorCode()) {
            return false;
        }
        if ((old.hasErrorCode() && old.getErrorCode() != versionRequirement.getErrorCode()) || old.hasMessage() != versionRequirement.hasMessage()) {
            return false;
        }
        if ((!old.hasMessage() || checkStringEquals(old.getMessage(), versionRequirement.getMessage())) && old.hasVersionKind() == versionRequirement.hasVersionKind()) {
            return !old.hasVersionKind() || old.getVersionKind() == versionRequirement.getVersionKind();
        }
        return false;
    }

    public boolean checkEquals(ProtoBuf.Type.Argument old, ProtoBuf.Type.Argument argument) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(argument, "new");
        if (old.hasProjection() != argument.hasProjection()) {
            return false;
        }
        if ((old.hasProjection() && old.getProjection() != argument.getProjection()) || old.hasType() != argument.hasType()) {
            return false;
        }
        if (old.hasType()) {
            ProtoBuf.Type type = old.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = argument.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        if (old.hasTypeId() != argument.hasTypeId()) {
            return false;
        }
        if (!old.hasTypeId()) {
            return true;
        }
        ProtoBuf.Type type3 = this.oldTypeTable.getType(old.getTypeId());
        Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
        ProtoBuf.Type type4 = this.newTypeTable.getType(argument.getTypeId());
        Intrinsics.checkNotNullExpressionValue(type4, "getType(...)");
        return checkEquals(type3, type4);
    }

    public boolean checkEquals(ProtoBuf.Annotation.Argument old, ProtoBuf.Annotation.Argument argument) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(argument, "new");
        if (!checkStringEquals(old.getNameId(), argument.getNameId())) {
            return false;
        }
        ProtoBuf.Annotation.Argument.Value value = old.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        ProtoBuf.Annotation.Argument.Value value2 = argument.getValue();
        Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
        return checkEquals(value, value2);
    }

    public boolean checkEquals(ProtoBuf.Effect old, ProtoBuf.Effect effect) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(effect, "new");
        if (old.hasEffectType() != effect.hasEffectType()) {
            return false;
        }
        if ((old.hasEffectType() && old.getEffectType() != effect.getEffectType()) || !checkEqualsEffectEffectConstructorArgument(old, effect) || old.hasConclusionOfConditionalEffect() != effect.hasConclusionOfConditionalEffect()) {
            return false;
        }
        if (old.hasConclusionOfConditionalEffect()) {
            ProtoBuf.Expression conclusionOfConditionalEffect = old.getConclusionOfConditionalEffect();
            Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect, "getConclusionOfConditionalEffect(...)");
            ProtoBuf.Expression conclusionOfConditionalEffect2 = effect.getConclusionOfConditionalEffect();
            Intrinsics.checkNotNullExpressionValue(conclusionOfConditionalEffect2, "getConclusionOfConditionalEffect(...)");
            if (!checkEquals(conclusionOfConditionalEffect, conclusionOfConditionalEffect2)) {
                return false;
            }
        }
        if (old.hasKind() != effect.hasKind()) {
            return false;
        }
        return !old.hasKind() || old.getKind() == effect.getKind();
    }

    public boolean checkEquals(JvmProtoBuf.JvmFieldSignature old, JvmProtoBuf.JvmFieldSignature jvmFieldSignature) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(jvmFieldSignature, "new");
        if (old.hasName() != jvmFieldSignature.hasName()) {
            return false;
        }
        if ((!old.hasName() || checkStringEquals(old.getName(), jvmFieldSignature.getName())) && old.hasDesc() == jvmFieldSignature.hasDesc()) {
            return !old.hasDesc() || checkStringEquals(old.getDesc(), jvmFieldSignature.getDesc());
        }
        return false;
    }

    public boolean checkEquals(ProtoBuf.Expression old, ProtoBuf.Expression expression) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(expression, "new");
        if (old.hasFlags() != expression.hasFlags()) {
            return false;
        }
        if ((old.hasFlags() && old.getFlags() != expression.getFlags()) || old.hasValueParameterReference() != expression.hasValueParameterReference()) {
            return false;
        }
        if ((old.hasValueParameterReference() && old.getValueParameterReference() != expression.getValueParameterReference()) || old.hasConstantValue() != expression.hasConstantValue()) {
            return false;
        }
        if ((old.hasConstantValue() && old.getConstantValue() != expression.getConstantValue()) || old.hasIsInstanceType() != expression.hasIsInstanceType()) {
            return false;
        }
        if (old.hasIsInstanceType()) {
            ProtoBuf.Type isInstanceType = old.getIsInstanceType();
            Intrinsics.checkNotNullExpressionValue(isInstanceType, "getIsInstanceType(...)");
            ProtoBuf.Type isInstanceType2 = expression.getIsInstanceType();
            Intrinsics.checkNotNullExpressionValue(isInstanceType2, "getIsInstanceType(...)");
            if (!checkEquals(isInstanceType, isInstanceType2)) {
                return false;
            }
        }
        if (old.hasIsInstanceTypeId() != expression.hasIsInstanceTypeId()) {
            return false;
        }
        if (old.hasIsInstanceTypeId()) {
            ProtoBuf.Type type = this.oldTypeTable.getType(old.getIsInstanceTypeId());
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            ProtoBuf.Type type2 = this.newTypeTable.getType(expression.getIsInstanceTypeId());
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            if (!checkEquals(type, type2)) {
                return false;
            }
        }
        return checkEqualsExpressionAndArgument(old, expression) && checkEqualsExpressionOrArgument(old, expression);
    }

    public boolean checkEqualsPackageFunction(ProtoBuf.Package old, ProtoBuf.Package r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getFunctionCount() != r9.getFunctionCount()) {
            return false;
        }
        int functionCount = old.getFunctionCount() - 1;
        if (functionCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Function function = old.getFunction(i);
                Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                ProtoBuf.Function function2 = r9.getFunction(i);
                Intrinsics.checkNotNullExpressionValue(function2, "getFunction(...)");
                if (!checkEquals(function, function2)) {
                    return false;
                }
                if (i != functionCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsPackageProperty(ProtoBuf.Package old, ProtoBuf.Package r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getPropertyCount() != r9.getPropertyCount()) {
            return false;
        }
        int propertyCount = old.getPropertyCount() - 1;
        if (propertyCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Property property = old.getProperty(i);
                Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                ProtoBuf.Property property2 = r9.getProperty(i);
                Intrinsics.checkNotNullExpressionValue(property2, "getProperty(...)");
                if (!checkEquals(property, property2)) {
                    return false;
                }
                if (i != propertyCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsPackageTypeAlias(ProtoBuf.Package old, ProtoBuf.Package r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getTypeAliasCount() != r9.getTypeAliasCount()) {
            return false;
        }
        int typeAliasCount = old.getTypeAliasCount() - 1;
        if (typeAliasCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeAlias typeAlias = old.getTypeAlias(i);
                Intrinsics.checkNotNullExpressionValue(typeAlias, "getTypeAlias(...)");
                ProtoBuf.TypeAlias typeAlias2 = r9.getTypeAlias(i);
                Intrinsics.checkNotNullExpressionValue(typeAlias2, "getTypeAlias(...)");
                if (!checkEquals(typeAlias, typeAlias2)) {
                    return false;
                }
                if (i != typeAliasCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassTypeParameter(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getTypeParameterCount() != r9.getTypeParameterCount()) {
            return false;
        }
        int typeParameterCount = old.getTypeParameterCount() - 1;
        if (typeParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = old.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                ProtoBuf.TypeParameter typeParameter2 = r9.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter2, "getTypeParameter(...)");
                if (!checkEquals(typeParameter, typeParameter2)) {
                    return false;
                }
                if (i != typeParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassSupertype(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getSupertypeCount() != r9.getSupertypeCount()) {
            return false;
        }
        int supertypeCount = old.getSupertypeCount() - 1;
        if (supertypeCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type supertype = old.getSupertype(i);
                Intrinsics.checkNotNullExpressionValue(supertype, "getSupertype(...)");
                ProtoBuf.Type supertype2 = r9.getSupertype(i);
                Intrinsics.checkNotNullExpressionValue(supertype2, "getSupertype(...)");
                if (!checkEquals(supertype, supertype2)) {
                    return false;
                }
                if (i != supertypeCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassSupertypeId(ProtoBuf.Class old, ProtoBuf.Class r10) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r10, "new");
        if (old.getSupertypeIdCount() != r10.getSupertypeIdCount()) {
            return false;
        }
        int supertypeIdCount = old.getSupertypeIdCount() - 1;
        if (supertypeIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getSupertypeId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(r10.getSupertypeId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != supertypeIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassNestedClassName(ProtoBuf.Class old, ProtoBuf.Class r8) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r8, "new");
        if (old.getNestedClassNameCount() != r8.getNestedClassNameCount()) {
            return false;
        }
        int nestedClassNameCount = old.getNestedClassNameCount() - 1;
        if (nestedClassNameCount >= 0) {
            for (int i = 0; checkStringEquals(old.getNestedClassName(i), r8.getNestedClassName(i)); i++) {
                if (i != nestedClassNameCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsClassContextReceiverType(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getContextReceiverTypeCount() != r9.getContextReceiverTypeCount()) {
            return false;
        }
        int contextReceiverTypeCount = old.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = old.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                ProtoBuf.Type contextReceiverType2 = r9.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType2, "getContextReceiverType(...)");
                if (!checkEquals(contextReceiverType, contextReceiverType2)) {
                    return false;
                }
                if (i != contextReceiverTypeCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassContextReceiverTypeId(ProtoBuf.Class old, ProtoBuf.Class r10) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r10, "new");
        if (old.getContextReceiverTypeIdCount() != r10.getContextReceiverTypeIdCount()) {
            return false;
        }
        int contextReceiverTypeIdCount = old.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(r10.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != contextReceiverTypeIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassConstructor(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getConstructorCount() != r9.getConstructorCount()) {
            return false;
        }
        int constructorCount = old.getConstructorCount() - 1;
        if (constructorCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Constructor constructor = old.getConstructor(i);
                Intrinsics.checkNotNullExpressionValue(constructor, "getConstructor(...)");
                ProtoBuf.Constructor constructor2 = r9.getConstructor(i);
                Intrinsics.checkNotNullExpressionValue(constructor2, "getConstructor(...)");
                if (!checkEquals(constructor, constructor2)) {
                    return false;
                }
                if (i != constructorCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassFunction(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getFunctionCount() != r9.getFunctionCount()) {
            return false;
        }
        int functionCount = old.getFunctionCount() - 1;
        if (functionCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Function function = old.getFunction(i);
                Intrinsics.checkNotNullExpressionValue(function, "getFunction(...)");
                ProtoBuf.Function function2 = r9.getFunction(i);
                Intrinsics.checkNotNullExpressionValue(function2, "getFunction(...)");
                if (!checkEquals(function, function2)) {
                    return false;
                }
                if (i != functionCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassProperty(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getPropertyCount() != r9.getPropertyCount()) {
            return false;
        }
        int propertyCount = old.getPropertyCount() - 1;
        if (propertyCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Property property = old.getProperty(i);
                Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
                ProtoBuf.Property property2 = r9.getProperty(i);
                Intrinsics.checkNotNullExpressionValue(property2, "getProperty(...)");
                if (!checkEquals(property, property2)) {
                    return false;
                }
                if (i != propertyCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassTypeAlias(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getTypeAliasCount() != r9.getTypeAliasCount()) {
            return false;
        }
        int typeAliasCount = old.getTypeAliasCount() - 1;
        if (typeAliasCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeAlias typeAlias = old.getTypeAlias(i);
                Intrinsics.checkNotNullExpressionValue(typeAlias, "getTypeAlias(...)");
                ProtoBuf.TypeAlias typeAlias2 = r9.getTypeAlias(i);
                Intrinsics.checkNotNullExpressionValue(typeAlias2, "getTypeAlias(...)");
                if (!checkEquals(typeAlias, typeAlias2)) {
                    return false;
                }
                if (i != typeAliasCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassEnumEntry(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getEnumEntryCount() != r9.getEnumEntryCount()) {
            return false;
        }
        int enumEntryCount = old.getEnumEntryCount() - 1;
        if (enumEntryCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.EnumEntry enumEntry = old.getEnumEntry(i);
                Intrinsics.checkNotNullExpressionValue(enumEntry, "getEnumEntry(...)");
                ProtoBuf.EnumEntry enumEntry2 = r9.getEnumEntry(i);
                Intrinsics.checkNotNullExpressionValue(enumEntry2, "getEnumEntry(...)");
                if (!checkEquals(enumEntry, enumEntry2)) {
                    return false;
                }
                if (i != enumEntryCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassSealedSubclassFqName(ProtoBuf.Class old, ProtoBuf.Class r8) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r8, "new");
        if (old.getSealedSubclassFqNameCount() != r8.getSealedSubclassFqNameCount()) {
            return false;
        }
        int sealedSubclassFqNameCount = old.getSealedSubclassFqNameCount() - 1;
        if (sealedSubclassFqNameCount >= 0) {
            for (int i = 0; checkClassIdEquals(old.getSealedSubclassFqName(i), r8.getSealedSubclassFqName(i)); i++) {
                if (i != sealedSubclassFqNameCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsClassMultiFieldValueClassUnderlyingName(ProtoBuf.Class old, ProtoBuf.Class r8) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r8, "new");
        if (old.getMultiFieldValueClassUnderlyingNameCount() != r8.getMultiFieldValueClassUnderlyingNameCount()) {
            return false;
        }
        int multiFieldValueClassUnderlyingNameCount = old.getMultiFieldValueClassUnderlyingNameCount() - 1;
        if (multiFieldValueClassUnderlyingNameCount >= 0) {
            for (int i = 0; checkStringEquals(old.getMultiFieldValueClassUnderlyingName(i), r8.getMultiFieldValueClassUnderlyingName(i)); i++) {
                if (i != multiFieldValueClassUnderlyingNameCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsClassMultiFieldValueClassUnderlyingType(ProtoBuf.Class old, ProtoBuf.Class r9) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r9, "new");
        if (old.getMultiFieldValueClassUnderlyingTypeCount() != r9.getMultiFieldValueClassUnderlyingTypeCount()) {
            return false;
        }
        int multiFieldValueClassUnderlyingTypeCount = old.getMultiFieldValueClassUnderlyingTypeCount() - 1;
        if (multiFieldValueClassUnderlyingTypeCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type multiFieldValueClassUnderlyingType = old.getMultiFieldValueClassUnderlyingType(i);
                Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingType, "getMultiFieldValueClassUnderlyingType(...)");
                ProtoBuf.Type multiFieldValueClassUnderlyingType2 = r9.getMultiFieldValueClassUnderlyingType(i);
                Intrinsics.checkNotNullExpressionValue(multiFieldValueClassUnderlyingType2, "getMultiFieldValueClassUnderlyingType(...)");
                if (!checkEquals(multiFieldValueClassUnderlyingType, multiFieldValueClassUnderlyingType2)) {
                    return false;
                }
                if (i != multiFieldValueClassUnderlyingTypeCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassMultiFieldValueClassUnderlyingTypeId(ProtoBuf.Class old, ProtoBuf.Class r10) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r10, "new");
        if (old.getMultiFieldValueClassUnderlyingTypeIdCount() != r10.getMultiFieldValueClassUnderlyingTypeIdCount()) {
            return false;
        }
        int multiFieldValueClassUnderlyingTypeIdCount = old.getMultiFieldValueClassUnderlyingTypeIdCount() - 1;
        if (multiFieldValueClassUnderlyingTypeIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getMultiFieldValueClassUnderlyingTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(r10.getMultiFieldValueClassUnderlyingTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != multiFieldValueClassUnderlyingTypeIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsClassVersionRequirement(ProtoBuf.Class old, ProtoBuf.Class r8) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(r8, "new");
        if (old.getVersionRequirementCount() != r8.getVersionRequirementCount()) {
            return false;
        }
        int versionRequirementCount = old.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            for (int i = 0; old.getVersionRequirement(i) == r8.getVersionRequirement(i); i++) {
                if (i != versionRequirementCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsFunctionTypeParameter(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.getTypeParameterCount() != function.getTypeParameterCount()) {
            return false;
        }
        int typeParameterCount = old.getTypeParameterCount() - 1;
        if (typeParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = old.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                ProtoBuf.TypeParameter typeParameter2 = function.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter2, "getTypeParameter(...)");
                if (!checkEquals(typeParameter, typeParameter2)) {
                    return false;
                }
                if (i != typeParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsFunctionContextReceiverType(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.getContextReceiverTypeCount() != function.getContextReceiverTypeCount()) {
            return false;
        }
        int contextReceiverTypeCount = old.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = old.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                ProtoBuf.Type contextReceiverType2 = function.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType2, "getContextReceiverType(...)");
                if (!checkEquals(contextReceiverType, contextReceiverType2)) {
                    return false;
                }
                if (i != contextReceiverTypeCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsFunctionContextReceiverTypeId(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.getContextReceiverTypeIdCount() != function.getContextReceiverTypeIdCount()) {
            return false;
        }
        int contextReceiverTypeIdCount = old.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(function.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != contextReceiverTypeIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsFunctionValueParameter(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.getValueParameterCount() != function.getValueParameterCount()) {
            return false;
        }
        int valueParameterCount = old.getValueParameterCount() - 1;
        if (valueParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.ValueParameter valueParameter = old.getValueParameter(i);
                Intrinsics.checkNotNullExpressionValue(valueParameter, "getValueParameter(...)");
                ProtoBuf.ValueParameter valueParameter2 = function.getValueParameter(i);
                Intrinsics.checkNotNullExpressionValue(valueParameter2, "getValueParameter(...)");
                if (!checkEquals(valueParameter, valueParameter2)) {
                    return false;
                }
                if (i != valueParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsFunctionVersionRequirement(ProtoBuf.Function old, ProtoBuf.Function function) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(function, "new");
        if (old.getVersionRequirementCount() != function.getVersionRequirementCount()) {
            return false;
        }
        int versionRequirementCount = old.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            for (int i = 0; old.getVersionRequirement(i) == function.getVersionRequirement(i); i++) {
                if (i != versionRequirementCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsPropertyTypeParameter(ProtoBuf.Property old, ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(property, "new");
        if (old.getTypeParameterCount() != property.getTypeParameterCount()) {
            return false;
        }
        int typeParameterCount = old.getTypeParameterCount() - 1;
        if (typeParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = old.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                ProtoBuf.TypeParameter typeParameter2 = property.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter2, "getTypeParameter(...)");
                if (!checkEquals(typeParameter, typeParameter2)) {
                    return false;
                }
                if (i != typeParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsPropertyContextReceiverType(ProtoBuf.Property old, ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(property, "new");
        if (old.getContextReceiverTypeCount() != property.getContextReceiverTypeCount()) {
            return false;
        }
        int contextReceiverTypeCount = old.getContextReceiverTypeCount() - 1;
        if (contextReceiverTypeCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type contextReceiverType = old.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType, "getContextReceiverType(...)");
                ProtoBuf.Type contextReceiverType2 = property.getContextReceiverType(i);
                Intrinsics.checkNotNullExpressionValue(contextReceiverType2, "getContextReceiverType(...)");
                if (!checkEquals(contextReceiverType, contextReceiverType2)) {
                    return false;
                }
                if (i != contextReceiverTypeCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsPropertyContextReceiverTypeId(ProtoBuf.Property old, ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(property, "new");
        if (old.getContextReceiverTypeIdCount() != property.getContextReceiverTypeIdCount()) {
            return false;
        }
        int contextReceiverTypeIdCount = old.getContextReceiverTypeIdCount() - 1;
        if (contextReceiverTypeIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(property.getContextReceiverTypeId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != contextReceiverTypeIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsPropertyVersionRequirement(ProtoBuf.Property old, ProtoBuf.Property property) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(property, "new");
        if (old.getVersionRequirementCount() != property.getVersionRequirementCount()) {
            return false;
        }
        int versionRequirementCount = old.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            for (int i = 0; old.getVersionRequirement(i) == property.getVersionRequirement(i); i++) {
                if (i != versionRequirementCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsTypeAliasTypeParameter(ProtoBuf.TypeAlias old, ProtoBuf.TypeAlias typeAlias) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeAlias, "new");
        if (old.getTypeParameterCount() != typeAlias.getTypeParameterCount()) {
            return false;
        }
        int typeParameterCount = old.getTypeParameterCount() - 1;
        if (typeParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.TypeParameter typeParameter = old.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter, "getTypeParameter(...)");
                ProtoBuf.TypeParameter typeParameter2 = typeAlias.getTypeParameter(i);
                Intrinsics.checkNotNullExpressionValue(typeParameter2, "getTypeParameter(...)");
                if (!checkEquals(typeParameter, typeParameter2)) {
                    return false;
                }
                if (i != typeParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsTypeAliasAnnotation(ProtoBuf.TypeAlias old, ProtoBuf.TypeAlias typeAlias) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeAlias, "new");
        if (old.getAnnotationCount() != typeAlias.getAnnotationCount()) {
            return false;
        }
        int annotationCount = old.getAnnotationCount() - 1;
        if (annotationCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Annotation annotation = old.getAnnotation(i);
                Intrinsics.checkNotNullExpressionValue(annotation, "getAnnotation(...)");
                ProtoBuf.Annotation annotation2 = typeAlias.getAnnotation(i);
                Intrinsics.checkNotNullExpressionValue(annotation2, "getAnnotation(...)");
                if (!checkEquals(annotation, annotation2)) {
                    return false;
                }
                if (i != annotationCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsTypeAliasVersionRequirement(ProtoBuf.TypeAlias old, ProtoBuf.TypeAlias typeAlias) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeAlias, "new");
        if (old.getVersionRequirementCount() != typeAlias.getVersionRequirementCount()) {
            return false;
        }
        int versionRequirementCount = old.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            for (int i = 0; old.getVersionRequirement(i) == typeAlias.getVersionRequirement(i); i++) {
                if (i != versionRequirementCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsVersionRequirementTableRequirement(ProtoBuf.VersionRequirementTable old, ProtoBuf.VersionRequirementTable versionRequirementTable) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "new");
        if (old.getRequirementCount() != versionRequirementTable.getRequirementCount()) {
            return false;
        }
        int requirementCount = old.getRequirementCount() - 1;
        if (requirementCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.VersionRequirement requirement = old.getRequirement(i);
                Intrinsics.checkNotNullExpressionValue(requirement, "getRequirement(...)");
                ProtoBuf.VersionRequirement requirement2 = versionRequirementTable.getRequirement(i);
                Intrinsics.checkNotNullExpressionValue(requirement2, "getRequirement(...)");
                if (!checkEquals(requirement, requirement2)) {
                    return false;
                }
                if (i != requirementCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsTypeParameterUpperBound(ProtoBuf.TypeParameter old, ProtoBuf.TypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeParameter, "new");
        if (old.getUpperBoundCount() != typeParameter.getUpperBoundCount()) {
            return false;
        }
        int upperBoundCount = old.getUpperBoundCount() - 1;
        if (upperBoundCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type upperBound = old.getUpperBound(i);
                Intrinsics.checkNotNullExpressionValue(upperBound, "getUpperBound(...)");
                ProtoBuf.Type upperBound2 = typeParameter.getUpperBound(i);
                Intrinsics.checkNotNullExpressionValue(upperBound2, "getUpperBound(...)");
                if (!checkEquals(upperBound, upperBound2)) {
                    return false;
                }
                if (i != upperBoundCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsTypeParameterUpperBoundId(ProtoBuf.TypeParameter old, ProtoBuf.TypeParameter typeParameter) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(typeParameter, "new");
        if (old.getUpperBoundIdCount() != typeParameter.getUpperBoundIdCount()) {
            return false;
        }
        int upperBoundIdCount = old.getUpperBoundIdCount() - 1;
        if (upperBoundIdCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type type = this.oldTypeTable.getType(old.getUpperBoundId(i));
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                ProtoBuf.Type type2 = this.newTypeTable.getType(typeParameter.getUpperBoundId(i));
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                if (!checkEquals(type, type2)) {
                    return false;
                }
                if (i != upperBoundIdCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsTypeArgument(ProtoBuf.Type old, ProtoBuf.Type type) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(type, "new");
        if (old.getArgumentCount() != type.getArgumentCount()) {
            return false;
        }
        int argumentCount = old.getArgumentCount() - 1;
        if (argumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Type.Argument argument = old.getArgument(i);
                Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
                ProtoBuf.Type.Argument argument2 = type.getArgument(i);
                Intrinsics.checkNotNullExpressionValue(argument2, "getArgument(...)");
                if (!checkEquals(argument, argument2)) {
                    return false;
                }
                if (i != argumentCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsConstructorValueParameter(ProtoBuf.Constructor old, ProtoBuf.Constructor constructor) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(constructor, "new");
        if (old.getValueParameterCount() != constructor.getValueParameterCount()) {
            return false;
        }
        int valueParameterCount = old.getValueParameterCount() - 1;
        if (valueParameterCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.ValueParameter valueParameter = old.getValueParameter(i);
                Intrinsics.checkNotNullExpressionValue(valueParameter, "getValueParameter(...)");
                ProtoBuf.ValueParameter valueParameter2 = constructor.getValueParameter(i);
                Intrinsics.checkNotNullExpressionValue(valueParameter2, "getValueParameter(...)");
                if (!checkEquals(valueParameter, valueParameter2)) {
                    return false;
                }
                if (i != valueParameterCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsConstructorVersionRequirement(ProtoBuf.Constructor old, ProtoBuf.Constructor constructor) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(constructor, "new");
        if (old.getVersionRequirementCount() != constructor.getVersionRequirementCount()) {
            return false;
        }
        int versionRequirementCount = old.getVersionRequirementCount() - 1;
        if (versionRequirementCount >= 0) {
            for (int i = 0; old.getVersionRequirement(i) == constructor.getVersionRequirement(i); i++) {
                if (i != versionRequirementCount) {
                }
            }
            return false;
        }
        return true;
    }

    public boolean checkEqualsAnnotationArgument(ProtoBuf.Annotation old, ProtoBuf.Annotation annotation) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(annotation, "new");
        if (old.getArgumentCount() != annotation.getArgumentCount()) {
            return false;
        }
        int argumentCount = old.getArgumentCount() - 1;
        if (argumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Annotation.Argument argument = old.getArgument(i);
                Intrinsics.checkNotNullExpressionValue(argument, "getArgument(...)");
                ProtoBuf.Annotation.Argument argument2 = annotation.getArgument(i);
                Intrinsics.checkNotNullExpressionValue(argument2, "getArgument(...)");
                if (!checkEquals(argument, argument2)) {
                    return false;
                }
                if (i != argumentCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsContractEffect(ProtoBuf.Contract old, ProtoBuf.Contract contract) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(contract, "new");
        if (old.getEffectCount() != contract.getEffectCount()) {
            return false;
        }
        int effectCount = old.getEffectCount() - 1;
        if (effectCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Effect effect = old.getEffect(i);
                Intrinsics.checkNotNullExpressionValue(effect, "getEffect(...)");
                ProtoBuf.Effect effect2 = contract.getEffect(i);
                Intrinsics.checkNotNullExpressionValue(effect2, "getEffect(...)");
                if (!checkEquals(effect, effect2)) {
                    return false;
                }
                if (i != effectCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsAnnotationArgumentValueArrayElement(ProtoBuf.Annotation.Argument.Value old, ProtoBuf.Annotation.Argument.Value value) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(value, "new");
        if (old.getArrayElementCount() != value.getArrayElementCount()) {
            return false;
        }
        int arrayElementCount = old.getArrayElementCount() - 1;
        if (arrayElementCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Annotation.Argument.Value arrayElement = old.getArrayElement(i);
                Intrinsics.checkNotNullExpressionValue(arrayElement, "getArrayElement(...)");
                ProtoBuf.Annotation.Argument.Value arrayElement2 = value.getArrayElement(i);
                Intrinsics.checkNotNullExpressionValue(arrayElement2, "getArrayElement(...)");
                if (!checkEquals(arrayElement, arrayElement2)) {
                    return false;
                }
                if (i != arrayElementCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsEffectEffectConstructorArgument(ProtoBuf.Effect old, ProtoBuf.Effect effect) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(effect, "new");
        if (old.getEffectConstructorArgumentCount() != effect.getEffectConstructorArgumentCount()) {
            return false;
        }
        int effectConstructorArgumentCount = old.getEffectConstructorArgumentCount() - 1;
        if (effectConstructorArgumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Expression effectConstructorArgument = old.getEffectConstructorArgument(i);
                Intrinsics.checkNotNullExpressionValue(effectConstructorArgument, "getEffectConstructorArgument(...)");
                ProtoBuf.Expression effectConstructorArgument2 = effect.getEffectConstructorArgument(i);
                Intrinsics.checkNotNullExpressionValue(effectConstructorArgument2, "getEffectConstructorArgument(...)");
                if (!checkEquals(effectConstructorArgument, effectConstructorArgument2)) {
                    return false;
                }
                if (i != effectConstructorArgumentCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsExpressionAndArgument(ProtoBuf.Expression old, ProtoBuf.Expression expression) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(expression, "new");
        if (old.getAndArgumentCount() != expression.getAndArgumentCount()) {
            return false;
        }
        int andArgumentCount = old.getAndArgumentCount() - 1;
        if (andArgumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Expression andArgument = old.getAndArgument(i);
                Intrinsics.checkNotNullExpressionValue(andArgument, "getAndArgument(...)");
                ProtoBuf.Expression andArgument2 = expression.getAndArgument(i);
                Intrinsics.checkNotNullExpressionValue(andArgument2, "getAndArgument(...)");
                if (!checkEquals(andArgument, andArgument2)) {
                    return false;
                }
                if (i != andArgumentCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public boolean checkEqualsExpressionOrArgument(ProtoBuf.Expression old, ProtoBuf.Expression expression) {
        Intrinsics.checkNotNullParameter(old, "old");
        Intrinsics.checkNotNullParameter(expression, "new");
        if (old.getOrArgumentCount() != expression.getOrArgumentCount()) {
            return false;
        }
        int orArgumentCount = old.getOrArgumentCount() - 1;
        if (orArgumentCount >= 0) {
            int i = 0;
            while (true) {
                ProtoBuf.Expression orArgument = old.getOrArgument(i);
                Intrinsics.checkNotNullExpressionValue(orArgument, "getOrArgument(...)");
                ProtoBuf.Expression orArgument2 = expression.getOrArgument(i);
                Intrinsics.checkNotNullExpressionValue(orArgument2, "getOrArgument(...)");
                if (!checkEquals(orArgument, orArgument2)) {
                    return false;
                }
                if (i != orArgumentCount) {
                    i++;
                }
            }
        }
        return true;
    }

    public final int oldGetIndexOfString(int index) {
        return getIndexOfString(index, this.oldStringIndexesMap, this.oldNameResolver);
    }

    public final int newGetIndexOfString(int index) {
        return getIndexOfString(index, this.newStringIndexesMap, this.newNameResolver);
    }

    public final int getIndexOfString(int index, Map<Integer, Integer> map, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Integer num = map.get(Integer.valueOf(index));
        if (num != null) {
            return num.intValue();
        }
        int iIntern = this.strings.intern(nameResolver.getString(index));
        map.put(Integer.valueOf(index), Integer.valueOf(iIntern));
        return iIntern;
    }

    public final int oldGetIndexOfClassId(int index) {
        return getIndexOfClassId(index, this.oldClassIdIndexesMap, this.oldNameResolver);
    }

    public final int newGetIndexOfClassId(int index) {
        return getIndexOfClassId(index, this.newClassIdIndexesMap, this.newNameResolver);
    }

    public final int getIndexOfClassId(int index, Map<Integer, Integer> map, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(map, "map");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Integer num = map.get(Integer.valueOf(index));
        if (num != null) {
            return num.intValue();
        }
        int iIntern = this.classIds.intern(NameResolverUtilKt.getClassId(nameResolver, index));
        map.put(Integer.valueOf(index), Integer.valueOf(iIntern));
        return iIntern;
    }

    private final boolean checkStringEquals(int old, int i) {
        return oldGetIndexOfString(old) == newGetIndexOfString(i);
    }

    private final boolean checkClassIdEquals(int old, int i) {
        return oldGetIndexOfClassId(old) == newGetIndexOfClassId(i);
    }
}
