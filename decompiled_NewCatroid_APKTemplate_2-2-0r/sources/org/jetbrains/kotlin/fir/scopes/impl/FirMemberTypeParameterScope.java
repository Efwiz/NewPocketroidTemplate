package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.scopes.FirTypeParameterScope;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirMemberTypeParameterScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R&\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirMemberTypeParameterScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeParameterScope;", "callableMember", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)V", "typeParameters", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "getTypeParameters", "()Ljava/util/Map;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMemberTypeParameterScope extends FirTypeParameterScope {
    private final Map<Name, List<FirTypeParameter>> typeParameters;

    public FirMemberTypeParameterScope(FirMemberDeclaration callableMember) {
        Intrinsics.checkNotNullParameter(callableMember, "callableMember");
        List<FirTypeParameterRef> typeParameters = callableMember.getTypeParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (obj instanceof FirTypeParameter) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            Name name2 = ((FirTypeParameter) obj2).getName();
            Object obj3 = linkedHashMap.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        this.typeParameters = linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeParameterScope
    public Map<Name, List<FirTypeParameter>> getTypeParameters() {
        return this.typeParameters;
    }
}
