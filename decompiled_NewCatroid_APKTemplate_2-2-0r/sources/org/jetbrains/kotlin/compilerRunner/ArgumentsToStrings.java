package org.jetbrains.kotlin.compilerRunner;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.value.CompoundEntry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KClassifier;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty1;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.ReflectJvmMapping;
import org.jetbrains.kotlin.cli.common.arguments.Argument;
import org.jetbrains.kotlin.cli.common.arguments.CommonToolArguments;
import org.jetbrains.kotlin.cli.common.arguments.InternalArgument;
import org.jetbrains.kotlin.cli.common.arguments.ParseCommandLineArgumentsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: argumentsToStrings.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a1\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¢\u0006\u0002\u0010\t\u001aC\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u0002H\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0001¢\u0006\u0002\u0010\u0011\u001a!\u0010\u0012\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\u000fH\u0002¢\u0006\u0002\u0010\u0013\u001a&\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\f2\b\b\u0002\u0010\u0010\u001a\u00020\b2\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\u0014"}, d2 = {"getArgumentStringValue", "", "", "argumentAnnotation", "Lorg/jetbrains/kotlin/cli/common/arguments/Argument;", CompoundEntry.NAME_values, "", "compactArgumentValues", "", "(Lorg/jetbrains/kotlin/cli/common/arguments/Argument;[Ljava/lang/Object;Z)Ljava/util/List;", "toArgumentStrings", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "thisArguments", ModuleXmlParser.TYPE, "Lkotlin/reflect/KClass;", "shortArgumentKeys", "(Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;Lkotlin/reflect/KClass;ZZ)Ljava/util/List;", "newArgumentsInstance", "(Lkotlin/reflect/KClass;)Lorg/jetbrains/kotlin/cli/common/arguments/CommonToolArguments;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsToStrings {
    public static final List<String> toArgumentStrings(CommonToolArguments commonToolArguments) {
        Intrinsics.checkNotNullParameter(commonToolArguments, "<this>");
        return toArgumentStrings$default(commonToolArguments, false, false, 3, null);
    }

    public static final List<String> toArgumentStrings(CommonToolArguments commonToolArguments, boolean z) {
        Intrinsics.checkNotNullParameter(commonToolArguments, "<this>");
        return toArgumentStrings$default(commonToolArguments, z, false, 2, null);
    }

    public static /* synthetic */ List toArgumentStrings$default(CommonToolArguments commonToolArguments, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return toArgumentStrings(commonToolArguments, z, z2);
    }

    public static final List<String> toArgumentStrings(CommonToolArguments commonToolArguments, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(commonToolArguments, "<this>");
        return toArgumentStrings(commonToolArguments, Reflection.getOrCreateKotlinClass(commonToolArguments.getClass()), z, z2);
    }

    public static final <T extends CommonToolArguments> List<String> toArgumentStrings(T thisArguments, KClass<T> type, boolean z, boolean z2) {
        List<String> listListOf;
        Class javaClass;
        String string;
        Intrinsics.checkNotNullParameter(thisArguments, "thisArguments");
        Intrinsics.checkNotNullParameter(type, "type");
        ArrayList arrayList = new ArrayList();
        CommonToolArguments commonToolArgumentsNewArgumentsInstance = newArgumentsInstance(type);
        for (KProperty1 kProperty1 : KClasses.getMemberProperties(type)) {
            Field javaField = ReflectJvmMapping.getJavaField(kProperty1);
            Argument argument = javaField != null ? (Argument) javaField.getAnnotation(Argument.class) : null;
            if (argument != null) {
                Intrinsics.checkNotNull(argument);
                Object obj = kProperty1.get(thisArguments);
                if (!Intrinsics.areEqual(obj, kProperty1.get(commonToolArgumentsNewArgumentsInstance))) {
                    if (Intrinsics.areEqual(kProperty1.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                        if (obj == null || (string = obj.toString()) == null) {
                            string = "false";
                        }
                        listListOf = CollectionsKt.listOf(string);
                    } else {
                        KClassifier classifier = kProperty1.getReturnType().getClassifier();
                        KClass kClass = classifier instanceof KClass ? (KClass) classifier : null;
                        if (kClass != null && (javaClass = JvmClassMappingKt.getJavaClass(kClass)) != null && javaClass.isArray()) {
                            listListOf = getArgumentStringValue(argument, (Object[]) obj, z2);
                        } else if (Intrinsics.areEqual(kProperty1.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(List.class))) {
                            List list = (List) obj;
                            listListOf = getArgumentStringValue(argument, list != null ? list.toArray(new Object[0]) : null, z2);
                        } else {
                            listListOf = CollectionsKt.listOf(String.valueOf(obj));
                        }
                    }
                    String strValue = (!z || argument.shortName().length() <= 0) ? argument.value() : argument.shortName();
                    for (String str : listListOf) {
                        if ((obj instanceof Boolean) && ((Boolean) obj).booleanValue()) {
                            arrayList.add(strValue);
                        } else if (ParseCommandLineArgumentsKt.isAdvanced(argument) || Intrinsics.areEqual(kProperty1.getReturnType().getClassifier(), Reflection.getOrCreateKotlinClass(Boolean.TYPE))) {
                            arrayList.add(strValue + '=' + str);
                        } else {
                            arrayList.add(strValue);
                            arrayList.add(str);
                        }
                    }
                }
            }
        }
        arrayList.addAll(thisArguments.getFreeArgs());
        List<InternalArgument> internalArguments = thisArguments.getInternalArguments();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(internalArguments, 10));
        Iterator<T> it = internalArguments.iterator();
        while (it.hasNext()) {
            arrayList2.add(((InternalArgument) it.next()).getStringRepresentation());
        }
        arrayList.addAll(arrayList2);
        return arrayList;
    }

    private static final List<String> getArgumentStringValue(Argument argument, Object[] objArr, boolean z) {
        if (objArr == null || objArr.length == 0) {
            return CollectionsKt.emptyList();
        }
        String resolvedDelimiter = ParseCommandLineArgumentsKt.getResolvedDelimiter(argument);
        if (resolvedDelimiter != null && resolvedDelimiter.length() != 0 && z) {
            return CollectionsKt.listOf(ArraysKt.joinToString$default(objArr, resolvedDelimiter, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null));
        }
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(String.valueOf(obj));
        }
        return arrayList;
    }

    private static final <T extends CommonToolArguments> T newArgumentsInstance(KClass<T> kClass) {
        Object next;
        Iterator<T> it = kClass.getConstructors().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((KFunction) next).getParameters().isEmpty());
        KFunction kFunction = (KFunction) next;
        if (kFunction == null) {
            throw new IllegalArgumentException(kClass.getQualifiedName() + " has no empty constructor");
        }
        return (T) kFunction.call(new Object[0]);
    }
}
