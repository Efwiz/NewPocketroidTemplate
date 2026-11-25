package org.jetbrains.kotlin.types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: loaded from: classes6.dex */
public class BoundsSubstitutor {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 5 || i == 7 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 5 || i == 7 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 7:
            case 10:
                objArr[0] = "org/jetbrains/kotlin/types/BoundsSubstitutor";
                break;
            case 3:
                objArr[0] = "callableDescriptor";
                break;
            case 4:
            case 6:
            case 9:
                objArr[0] = "typeParameters";
                break;
            case 8:
                objArr[0] = "current";
                break;
            default:
                objArr[0] = "functionDescriptor";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "substituteBounds";
        } else if (i == 5) {
            objArr[1] = "createUpperBoundsSubstitutor";
        } else if (i == 7) {
            objArr[1] = "topologicallySortTypeParameters";
        } else if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/types/BoundsSubstitutor";
        } else {
            objArr[1] = "getTypeParametersFromUpperBounds";
        }
        switch (i) {
            case 1:
            case 2:
            case 5:
            case 7:
            case 10:
                break;
            case 3:
            case 4:
                objArr[2] = "createUpperBoundsSubstitutor";
                break;
            case 6:
                objArr[2] = "topologicallySortTypeParameters";
                break;
            case 8:
            case 9:
                objArr[2] = "getTypeParametersFromUpperBounds";
                break;
            default:
                objArr[2] = "substituteBounds";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 5 && i != 7 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private BoundsSubstitutor() {
    }

    public static <D extends CallableDescriptor> D substituteBounds(D d) {
        if (d == null) {
            $$$reportNull$$$0(0);
        }
        List<TypeParameterDescriptor> typeParameters = d.getTypeParameters();
        if (typeParameters.isEmpty()) {
            if (d == null) {
                $$$reportNull$$$0(1);
            }
            return d;
        }
        D d2 = (D) d.substitute(createUpperBoundsSubstitutor(typeParameters));
        if (d2 == null) {
            $$$reportNull$$$0(2);
        }
        return d2;
    }

    public static <D extends CallableDescriptor> TypeSubstitutor createUpperBoundsSubstitutor(D d) {
        if (d == null) {
            $$$reportNull$$$0(3);
        }
        return createUpperBoundsSubstitutor(d.getTypeParameters());
    }

    private static TypeSubstitutor createUpperBoundsSubstitutor(List<TypeParameterDescriptor> list) {
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        HashMap map = new HashMap();
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(map);
        for (TypeParameterDescriptor typeParameterDescriptor : topologicallySortTypeParameters(list)) {
            map.put(typeParameterDescriptor.getTypeConstructor(), new TypeProjectionImpl(typeSubstitutorCreate.substitute(TypeIntersector.getUpperBoundsAsType(typeParameterDescriptor), Variance.INVARIANT)));
        }
        if (typeSubstitutorCreate == null) {
            $$$reportNull$$$0(5);
        }
        return typeSubstitutorCreate;
    }

    private static List<TypeParameterDescriptor> topologicallySortTypeParameters(final List<TypeParameterDescriptor> list) {
        if (list == null) {
            $$$reportNull$$$0(6);
        }
        List<TypeParameterDescriptor> list2 = DFS.topologicalOrder(list, new DFS.Neighbors() { // from class: org.jetbrains.kotlin.types.BoundsSubstitutor$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return BoundsSubstitutor.getTypeParametersFromUpperBounds((TypeParameterDescriptor) obj, list);
            }
        });
        Collections.reverse(list2);
        if (list2 == null) {
            $$$reportNull$$$0(7);
        }
        return list2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<TypeParameterDescriptor> getTypeParametersFromUpperBounds(TypeParameterDescriptor typeParameterDescriptor, final List<TypeParameterDescriptor> list) {
        if (typeParameterDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        if (list == null) {
            $$$reportNull$$$0(9);
        }
        List<TypeParameterDescriptor> list2 = (List) DFS.dfs(typeParameterDescriptor.getUpperBounds(), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.types.BoundsSubstitutor$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return CollectionsKt.map(((KotlinType) obj).getArguments(), new Function1() { // from class: org.jetbrains.kotlin.types.BoundsSubstitutor$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        return ((TypeProjection) obj2).getNullableAnyType();
                    }
                });
            }
        }, new DFS.NodeHandlerWithListResult<KotlinType, TypeParameterDescriptor>() { // from class: org.jetbrains.kotlin.types.BoundsSubstitutor.1
            @Override // org.jetbrains.kotlin.utils.DFS.AbstractNodeHandler, org.jetbrains.kotlin.utils.DFS.NodeHandler
            public boolean beforeChildren(KotlinType kotlinType) {
                ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
                if (!list.contains(this$0)) {
                    return true;
                }
                ((LinkedList) this.result).add((TypeParameterDescriptor) this$0);
                return true;
            }
        });
        if (list2 == null) {
            $$$reportNull$$$0(10);
        }
        return list2;
    }
}
