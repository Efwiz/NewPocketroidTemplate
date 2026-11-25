package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.PlatformUtilsKt;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: BinaryClassSignatureParser.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002 !B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001a\u0010\u0014\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u001a2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u001b\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bJ\u0018\u0010\u001c\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser;", "", "()V", "canonicalNameInterner", "Lorg/jetbrains/kotlin/com/intellij/util/containers/Interner;", "", "mapAsmType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "context", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/ClassifierResolutionContext;", "parseClassOrTypeVariableElement", "signature", "Ljava/text/CharacterIterator;", "parseClassifierRefSignature", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "parseDimensions", "", "parseParameterizedClassRefSignature", "parsePrimitiveType", "primitiveType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "parseTypeParameter", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "parseTypeParametersDeclaration", "", "parseTypeString", "parseTypeVariableRefSignature", "parseTypeWithoutVarianceAndArray", "parseVariance", "Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser$JavaSignatureVariance;", "ClsFormatException", "JavaSignatureVariance", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryClassSignatureParser {
    private final Interner<String> canonicalNameInterner = PlatformUtilsKt.createStringInterner();

    /* JADX INFO: compiled from: BinaryClassSignatureParser.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser$JavaSignatureVariance;", "", "(Ljava/lang/String;I)V", "PLUS", "MINUS", "STAR", "NO_VARIANCE", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private enum JavaSignatureVariance {
        PLUS,
        MINUS,
        STAR,
        NO_VARIANCE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<JavaSignatureVariance> getEntries() {
            return $ENTRIES;
        }
    }

    public final List<JavaTypeParameter> parseTypeParametersDeclaration(CharacterIterator signature, ClassifierResolutionContext context) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(context, "context");
        if (signature.current() != '<') {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        signature.next();
        while (signature.current() != '>') {
            arrayList.add(parseTypeParameter(signature, context));
        }
        signature.next();
        return org.jetbrains.kotlin.utils.CollectionsKt.compact(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JavaTypeParameter parseTypeParameter(CharacterIterator signature, ClassifierResolutionContext context) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        while (signature.current() != ':' && signature.current() != 65535) {
            sb.append(signature.current());
            signature.next();
        }
        if (signature.current() == 65535) {
            throw new ClsFormatException(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        SmartList smartList = new SmartList();
        boolean z = false;
        while (signature.current() == ':') {
            signature.next();
            if (signature.current() == ':') {
                z = true;
            } else {
                JavaClassifierType classifierRefSignature = parseClassifierRefSignature(signature, context);
                if (classifierRefSignature != null) {
                    smartList.add(classifierRefSignature);
                }
            }
        }
        Name nameIdentifier = Name.identifier(string);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return new BinaryJavaTypeParameter(nameIdentifier, smartList, z);
    }

    public final JavaClassifierType parseClassifierRefSignature(CharacterIterator signature, ClassifierResolutionContext context) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(context, "context");
        char cCurrent = signature.current();
        if (cCurrent == 'L') {
            return parseParameterizedClassRefSignature(signature, context);
        }
        if (cCurrent == 'T') {
            return parseTypeVariableRefSignature(signature, context);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JavaClassifierType parseTypeVariableRefSignature(CharacterIterator signature, final ClassifierResolutionContext context) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        signature.next();
        while (signature.current() != ';' && signature.current() != '>' && signature.current() != 65535) {
            sb.append(signature.current());
            signature.next();
        }
        if (signature.current() == 65535) {
            throw new ClsFormatException(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        if (signature.current() == ';') {
            signature.next();
        }
        String strIntern = this.canonicalNameInterner.intern(sb.toString());
        Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
        final String str = strIntern;
        return new PlainJavaClassifierType(new Function0<ClassifierResolutionContext.Result>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryClassSignatureParser.parseTypeVariableRefSignature.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierResolutionContext.Result invoke() {
                return context.resolveTypeParameter$resolution_common_jvm(str);
            }
        }, CollectionsKt.emptyList());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final JavaClassifierType parseParameterizedClassRefSignature(CharacterIterator signature, final ClassifierResolutionContext context) throws ClsFormatException {
        StringBuilder sb = new StringBuilder();
        SmartList smartList = new SmartList();
        signature.next();
        while (signature.current() != ';' && signature.current() != 65535) {
            char cCurrent = signature.current();
            if (cCurrent == '<') {
                ArrayList arrayList = new ArrayList();
                signature.next();
                do {
                    arrayList.add(parseClassOrTypeVariableElement(signature, context));
                } while (signature.current() != '>');
                smartList.add(arrayList);
            } else if (cCurrent != ' ') {
                sb.append(cCurrent);
            }
            signature.next();
        }
        if (signature.current() == 65535) {
            throw new ClsFormatException(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        signature.next();
        Interner<String> interner = this.canonicalNameInterner;
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        String strIntern = interner.intern(StringsKt.replace$default(string, '.', '$', false, 4, (Object) null));
        Intrinsics.checkNotNullExpressionValue(strIntern, "intern(...)");
        final String str = strIntern;
        return new PlainJavaClassifierType(new Function0<ClassifierResolutionContext.Result>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryClassSignatureParser.parseParameterizedClassRefSignature.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassifierResolutionContext.Result invoke() {
                return context.resolveByInternalName$resolution_common_jvm(str);
            }
        }, org.jetbrains.kotlin.utils.CollectionsKt.compact((ArrayList) AddToStdlibKt.flattenTo(CollectionsKt.reversed(smartList), new ArrayList())));
    }

    private final JavaType parseClassOrTypeVariableElement(CharacterIterator signature, ClassifierResolutionContext context) {
        JavaSignatureVariance variance = parseVariance(signature);
        if (variance == JavaSignatureVariance.STAR) {
            return new PlainJavaWildcardType(null, true);
        }
        JavaType typeString = parseTypeString(signature, context);
        if (variance == JavaSignatureVariance.NO_VARIANCE) {
            return typeString;
        }
        return new PlainJavaWildcardType(typeString, variance == JavaSignatureVariance.PLUS);
    }

    private final JavaSignatureVariance parseVariance(CharacterIterator signature) {
        JavaSignatureVariance javaSignatureVariance;
        char cCurrent = signature.current();
        boolean z = true;
        if (cCurrent == '+') {
            javaSignatureVariance = JavaSignatureVariance.PLUS;
        } else if (cCurrent == '-') {
            javaSignatureVariance = JavaSignatureVariance.MINUS;
        } else if (cCurrent == '*') {
            javaSignatureVariance = JavaSignatureVariance.STAR;
        } else if (cCurrent == '.' || cCurrent == '=') {
            javaSignatureVariance = JavaSignatureVariance.NO_VARIANCE;
        } else {
            javaSignatureVariance = JavaSignatureVariance.NO_VARIANCE;
            z = false;
        }
        if (z) {
            signature.next();
        }
        return javaSignatureVariance;
    }

    private final int parseDimensions(CharacterIterator signature) {
        int i = 0;
        while (signature.current() == '[') {
            i++;
            signature.next();
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final JavaType parseTypeString(CharacterIterator signature, ClassifierResolutionContext context) {
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(context, "context");
        int dimensions = parseDimensions(signature);
        PlainJavaArrayType typeWithoutVarianceAndArray = parseTypeWithoutVarianceAndArray(signature, context);
        if (typeWithoutVarianceAndArray == null) {
            throw new ClsFormatException(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }
        Iterator<Integer> it = new IntRange(1, dimensions).iterator();
        while (it.hasNext()) {
            ((IntIterator) it).nextInt();
            typeWithoutVarianceAndArray = new PlainJavaArrayType(typeWithoutVarianceAndArray);
        }
        return typeWithoutVarianceAndArray;
    }

    public final JavaType mapAsmType(Type type, ClassifierResolutionContext context) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(context, "context");
        return parseTypeString(new StringCharacterIterator(type.getDescriptor()), context);
    }

    private final JavaType parseTypeWithoutVarianceAndArray(CharacterIterator signature, ClassifierResolutionContext context) {
        char cCurrent = signature.current();
        if (cCurrent == 'L') {
            return parseParameterizedClassRefSignature(signature, context);
        }
        if (cCurrent == 'T') {
            return parseTypeVariableRefSignature(signature, context);
        }
        if (cCurrent == 'B') {
            return parsePrimitiveType(signature, PrimitiveType.BYTE);
        }
        if (cCurrent == 'C') {
            return parsePrimitiveType(signature, PrimitiveType.CHAR);
        }
        if (cCurrent == 'D') {
            return parsePrimitiveType(signature, PrimitiveType.DOUBLE);
        }
        if (cCurrent == 'F') {
            return parsePrimitiveType(signature, PrimitiveType.FLOAT);
        }
        if (cCurrent == 'I') {
            return parsePrimitiveType(signature, PrimitiveType.INT);
        }
        if (cCurrent == 'J') {
            return parsePrimitiveType(signature, PrimitiveType.LONG);
        }
        if (cCurrent == 'Z') {
            return parsePrimitiveType(signature, PrimitiveType.BOOLEAN);
        }
        if (cCurrent == 'S') {
            return parsePrimitiveType(signature, PrimitiveType.SHORT);
        }
        if (cCurrent == 'V') {
            return parsePrimitiveType(signature, null);
        }
        return null;
    }

    private final JavaType parsePrimitiveType(CharacterIterator signature, PrimitiveType primitiveType) {
        signature.next();
        return new PlainJavaPrimitiveType(primitiveType);
    }

    /* JADX INFO: compiled from: BinaryClassSignatureParser.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/classFiles/BinaryClassSignatureParser$ClsFormatException;", "", CrashHianalyticsData.MESSAGE, "", "cause", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClsFormatException extends Throwable {
        /* JADX WARN: Multi-variable type inference failed */
        public ClsFormatException() {
            this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
        }

        public ClsFormatException(String str, Throwable th) {
            super(str, th);
        }

        public /* synthetic */ ClsFormatException(String str, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : th);
        }
    }
}
