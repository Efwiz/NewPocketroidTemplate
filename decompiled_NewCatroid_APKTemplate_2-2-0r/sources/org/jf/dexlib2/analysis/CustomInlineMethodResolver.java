package org.jf.dexlib2.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jf.dexlib2.HiddenApiRestriction;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodParameter;
import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
import org.jf.dexlib2.immutable.util.ParamUtil;
import org.jf.util.io.Files;

/* JADX INFO: loaded from: classes6.dex */
public class CustomInlineMethodResolver extends InlineMethodResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Pattern longMethodPattern = Pattern.compile("(L[^;]+;)->([^(]+)\\(([^)]*)\\)(.+)");
    private final ClassPath classPath;
    private final Method[] inlineMethods;

    public CustomInlineMethodResolver(ClassPath classPath, String str) {
        this.classPath = classPath;
        StringReader stringReader = new StringReader(str);
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        try {
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                if (line.length() > 0) {
                    arrayList.add(line);
                }
            }
            this.inlineMethods = new Method[arrayList.size()];
            int i = 0;
            while (true) {
                Method[] methodArr = this.inlineMethods;
                if (i >= methodArr.length) {
                    return;
                }
                methodArr[i] = parseAndResolveInlineMethod((String) arrayList.get(i));
                i++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while parsing inline table", e);
        }
    }

    public CustomInlineMethodResolver(ClassPath classPath, File file) throws IOException {
        this(classPath, Files.readContent(file));
    }

    @Override // org.jf.dexlib2.analysis.InlineMethodResolver
    public Method resolveExecuteInline(AnalyzedInstruction analyzedInstruction) {
        int inlineIndex = ((InlineIndexInstruction) analyzedInstruction.instruction).getInlineIndex();
        if (inlineIndex >= 0) {
            Method[] methodArr = this.inlineMethods;
            if (inlineIndex < methodArr.length) {
                return methodArr[inlineIndex];
            }
        }
        throw new RuntimeException("Invalid method index: " + inlineIndex);
    }

    private Method parseAndResolveInlineMethod(String str) {
        int accessFlags;
        Matcher matcher = longMethodPattern.matcher(str);
        if (!matcher.matches()) {
            throw new RuntimeException("Invalid method descriptor: " + str);
        }
        boolean z = true;
        String strGroup = matcher.group(1);
        String strGroup2 = matcher.group(2);
        Iterable<ImmutableMethodParameter> paramString = ParamUtil.parseParamString(matcher.group(3));
        String strGroup3 = matcher.group(4);
        ImmutableMethodReference immutableMethodReference = new ImmutableMethodReference(strGroup, strGroup2, paramString, strGroup3);
        TypeProto typeProto = this.classPath.getClass(strGroup);
        if (typeProto instanceof ClassProto) {
            for (Method method : ((ClassProto) typeProto).getClassDef().getMethods()) {
                if (method.equals(immutableMethodReference)) {
                    accessFlags = method.getAccessFlags();
                }
            }
            z = false;
            accessFlags = 0;
        } else {
            z = false;
            accessFlags = 0;
        }
        if (!z) {
            throw new RuntimeException("Cannot resolve inline method: " + str);
        }
        return new ImmutableMethod(strGroup, strGroup2, paramString, strGroup3, accessFlags, (Set<? extends Annotation>) null, (Set<HiddenApiRestriction>) null, (MethodImplementation) null);
    }
}
