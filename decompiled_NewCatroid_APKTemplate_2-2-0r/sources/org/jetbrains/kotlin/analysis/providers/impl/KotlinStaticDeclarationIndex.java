package org.jetbrains.kotlin.analysis.providers.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;

/* JADX INFO: compiled from: KotlinStaticDeclarationIndex.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R&\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR&\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR&\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\tR&\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR&\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\tR&\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\tR&\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00060\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\t¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/impl/KotlinStaticDeclarationIndex;", "", "()V", "classMap", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getClassMap$analysis_api_providers", "()Ljava/util/Map;", "facadeFileMap", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFacadeFileMap$analysis_api_providers", "multiFileClassPartMap", "getMultiFileClassPartMap$analysis_api_providers", "scriptMap", "Lorg/jetbrains/kotlin/psi/KtScript;", "getScriptMap$analysis_api_providers", "topLevelFunctionMap", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getTopLevelFunctionMap$analysis_api_providers", "topLevelPropertyMap", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getTopLevelPropertyMap$analysis_api_providers", "typeAliasMap", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "getTypeAliasMap$analysis_api_providers", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStaticDeclarationIndex {
    private final Map<FqName, Set<KtFile>> facadeFileMap = new LinkedHashMap();
    private final Map<FqName, Set<KtFile>> multiFileClassPartMap = new LinkedHashMap();
    private final Map<FqName, Set<KtScript>> scriptMap = new LinkedHashMap();
    private final Map<FqName, Set<KtClassOrObject>> classMap = new LinkedHashMap();
    private final Map<FqName, Set<KtTypeAlias>> typeAliasMap = new LinkedHashMap();
    private final Map<FqName, Set<KtNamedFunction>> topLevelFunctionMap = new LinkedHashMap();
    private final Map<FqName, Set<KtProperty>> topLevelPropertyMap = new LinkedHashMap();

    public final Map<FqName, Set<KtFile>> getFacadeFileMap$analysis_api_providers() {
        return this.facadeFileMap;
    }

    public final Map<FqName, Set<KtFile>> getMultiFileClassPartMap$analysis_api_providers() {
        return this.multiFileClassPartMap;
    }

    public final Map<FqName, Set<KtScript>> getScriptMap$analysis_api_providers() {
        return this.scriptMap;
    }

    public final Map<FqName, Set<KtClassOrObject>> getClassMap$analysis_api_providers() {
        return this.classMap;
    }

    public final Map<FqName, Set<KtTypeAlias>> getTypeAliasMap$analysis_api_providers() {
        return this.typeAliasMap;
    }

    public final Map<FqName, Set<KtNamedFunction>> getTopLevelFunctionMap$analysis_api_providers() {
        return this.topLevelFunctionMap;
    }

    public final Map<FqName, Set<KtProperty>> getTopLevelPropertyMap$analysis_api_providers() {
        return this.topLevelPropertyMap;
    }
}
