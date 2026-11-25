package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassOrObjectInfo;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;

/* JADX INFO: compiled from: DeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\bH&J*\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00100\u000fH&J\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "", "getClassOrObjectDeclarations", "", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassOrObjectInfo;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getDeclarationNames", "", "getDeclarations", "", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "", "getDestructuringDeclarationsEntries", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry;", "getFunctionDeclarations", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getPropertyDeclarations", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getScriptDeclarations", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "getTypeAliasDeclarations", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DeclarationProvider {
    Collection<KtClassOrObjectInfo<?>> getClassOrObjectDeclarations(Name name2);

    Set<Name> getDeclarationNames();

    List<KtDeclaration> getDeclarations(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter);

    Collection<KtDestructuringDeclarationEntry> getDestructuringDeclarationsEntries(Name name2);

    Collection<KtNamedFunction> getFunctionDeclarations(Name name2);

    Collection<KtProperty> getPropertyDeclarations(Name name2);

    Collection<KtScriptInfo> getScriptDeclarations(Name name2);

    Collection<KtTypeAlias> getTypeAliasDeclarations(Name name2);
}
