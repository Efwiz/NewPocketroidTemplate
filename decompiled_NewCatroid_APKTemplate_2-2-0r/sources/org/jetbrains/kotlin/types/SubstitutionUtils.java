package org.jetbrains.kotlin.types;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.LinkedHashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;

/* JADX INFO: loaded from: classes6.dex */
public class SubstitutionUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/types/SubstitutionUtils";
        } else if (i == 4) {
            objArr[0] = "context";
        } else if (i == 5) {
            objArr[0] = "substitutor";
        } else if (i != 6) {
            objArr[0] = ModuleXmlParser.TYPE;
        } else {
            objArr[0] = SchemaSymbols.ATTVAL_SUBSTITUTION;
        }
        if (i == 1) {
            objArr[1] = "buildDeepSubstitutor";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/types/SubstitutionUtils";
        } else {
            objArr[1] = "buildDeepSubstitutionMultimap";
        }
        switch (i) {
            case 1:
            case 3:
                break;
            case 2:
                objArr[2] = "buildDeepSubstitutionMultimap";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "fillInDeepSubstitutor";
                break;
            default:
                objArr[2] = "buildDeepSubstitutor";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private SubstitutionUtils() {
    }

    public static TypeSubstitutor buildDeepSubstitutor(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(0);
        }
        HashMap map = new HashMap();
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(map);
        fillInDeepSubstitutor(kotlinType, typeSubstitutorCreate, map, null);
        if (typeSubstitutorCreate == null) {
            $$$reportNull$$$0(1);
        }
        return typeSubstitutorCreate;
    }

    public static Multimap<TypeParameterDescriptor, TypeProjection> buildDeepSubstitutionMultimap(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(2);
        }
        LinkedHashMultimap linkedHashMultimapCreate = LinkedHashMultimap.create();
        HashMap map = new HashMap();
        fillInDeepSubstitutor(kotlinType, TypeSubstitutor.create(map), map, linkedHashMultimapCreate);
        if (linkedHashMultimapCreate == null) {
            $$$reportNull$$$0(3);
        }
        return linkedHashMultimapCreate;
    }

    private static void fillInDeepSubstitutor(KotlinType kotlinType, TypeSubstitutor typeSubstitutor, Map<TypeConstructor, TypeProjection> map, Multimap<TypeParameterDescriptor, TypeProjection> multimap) {
        if (kotlinType == null) {
            $$$reportNull$$$0(4);
        }
        if (typeSubstitutor == null) {
            $$$reportNull$$$0(5);
        }
        if (map == null) {
            $$$reportNull$$$0(6);
        }
        List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
        List<TypeProjection> arguments = kotlinType.getArguments();
        if (parameters.size() != arguments.size()) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < arguments.size(); i++) {
            TypeProjection typeProjection = arguments.get(i);
            TypeParameterDescriptor typeParameterDescriptor = parameters.get(i);
            TypeProjection typeProjectionSubstitute = typeSubstitutor.substitute(typeProjection);
            map.put(typeParameterDescriptor.getTypeConstructor(), typeProjectionSubstitute);
            if (multimap != null) {
                multimap.put(typeParameterDescriptor, typeProjectionSubstitute);
            }
        }
        if (KotlinBuiltIns.isNothingOrNullableNothing(kotlinType)) {
            return;
        }
        Iterator<KotlinType> it = kotlinType.getConstructor().mo7438getSupertypes().iterator();
        while (it.hasNext()) {
            fillInDeepSubstitutor(it.next(), typeSubstitutor, map, multimap);
        }
    }
}
