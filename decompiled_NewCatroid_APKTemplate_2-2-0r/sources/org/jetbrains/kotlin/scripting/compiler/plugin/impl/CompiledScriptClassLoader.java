package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.reandroid.arsc.chunk.TypeBlock;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.BytesUrlUtils;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: CompiledScriptClassLoader.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0001\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u0005H\u0014J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0014J\u001a\u0010\r\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000e2\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0014J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\n\u001a\u00020\u0005H\u0016R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/CompiledScriptClassLoader;", "Ljava/lang/ClassLoader;", "parent", TypeBlock.NAME_entries, "", "", "", "(Ljava/lang/ClassLoader;Ljava/util/Map;)V", "findClass", "Ljava/lang/Class;", "name", "findResource", "Ljava/net/URL;", "findResources", "Ljava/util/Enumeration;", "getResourceAsStream", "Ljava/io/InputStream;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompiledScriptClassLoader extends ClassLoader {
    private final Map<String, byte[]> entries;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompiledScriptClassLoader(ClassLoader classLoader, Map<String, byte[]> entries) {
        super(classLoader);
        Intrinsics.checkNotNullParameter(entries, "entries");
        this.entries = entries;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        byte[] bArr = this.entries.get(StringsKt.replace$default(name2, '.', '/', false, 4, (Object) null) + ".class");
        if (bArr == null) {
            return null;
        }
        return defineClass(name2, bArr, 0, bArr.length);
    }

    @Override // java.lang.ClassLoader
    public InputStream getResourceAsStream(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        byte[] bArr = this.entries.get(name2);
        return bArr != null ? new ByteArrayInputStream(bArr) : super.getResourceAsStream(name2);
    }

    @Override // java.lang.ClassLoader
    protected Enumeration<URL> findResources(String name2) throws IOException {
        URL urlCreateBytesUrl;
        Enumeration<URL> enumerationFindResources = super.findResources(name2);
        byte[] bArr = this.entries.get(name2);
        if (bArr == null || (urlCreateBytesUrl = BytesUrlUtils.createBytesUrl(bArr)) == null) {
            return enumerationFindResources;
        }
        List listListOf = CollectionsKt.listOf(urlCreateBytesUrl);
        Intrinsics.checkNotNull(enumerationFindResources);
        return Collections.enumeration(CollectionsKt.plus((Collection) listListOf, SequencesKt.asSequence(CollectionsKt.iterator(enumerationFindResources))));
    }

    @Override // java.lang.ClassLoader
    protected URL findResource(String name2) {
        URL urlCreateBytesUrl;
        byte[] bArr = this.entries.get(name2);
        return (bArr == null || (urlCreateBytesUrl = BytesUrlUtils.createBytesUrl(bArr)) == null) ? super.findResource(name2) : urlCreateBytesUrl;
    }
}
