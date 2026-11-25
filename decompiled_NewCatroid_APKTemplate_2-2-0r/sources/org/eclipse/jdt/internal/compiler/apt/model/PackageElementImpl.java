package org.eclipse.jdt.internal.compiler.apt.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.batch.FileSystem;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;

/* JADX INFO: loaded from: classes14.dex */
public class PackageElementImpl extends ElementImpl implements PackageElement {
    public Element getEnclosingElement() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    PackageElement getPackage() {
        return this;
    }

    PackageElementImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, PackageBinding packageBinding) {
        super(baseProcessingEnvImpl, packageBinding);
    }

    public <R, P> R accept(ElementVisitor<R, P> elementVisitor, P p) {
        return (R) elementVisitor.visitPackage(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    protected AnnotationBinding[] getAnnotationBindings() {
        ReferenceBinding type = this._env.getLookupEnvironment().getType(CharOperation.arrayConcat(((PackageBinding) this._binding).compoundName, TypeConstants.PACKAGE_INFO_NAME));
        if (type == null || !type.isValidBinding()) {
            return null;
        }
        return type.getAnnotations();
    }

    public List<? extends Element> getEnclosedElements() {
        PackageBinding packageBinding = (PackageBinding) this._binding;
        LookupEnvironment lookupEnvironment = packageBinding.environment;
        INameEnvironment iNameEnvironment = packageBinding.environment.nameEnvironment;
        char[][][] cArrFindTypeNames = iNameEnvironment instanceof FileSystem ? ((FileSystem) iNameEnvironment).findTypeNames(packageBinding.compoundName) : null;
        HashSet hashSet = new HashSet();
        if (cArrFindTypeNames != null) {
            for (char[][] cArr : cArrFindTypeNames) {
                ReferenceBinding type = lookupEnvironment.getType(cArr);
                if (type != null && type.isValidBinding()) {
                    hashSet.add(this._env.getFactory().newElement(type));
                }
            }
        }
        ArrayList arrayList = new ArrayList(hashSet.size());
        arrayList.addAll(hashSet);
        return Collections.unmodifiableList(arrayList);
    }

    public ElementKind getKind() {
        return ElementKind.PACKAGE;
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.ElementImpl
    public Name getSimpleName() {
        char[][] cArr = ((PackageBinding) this._binding).compoundName;
        int length = cArr.length;
        if (length == 0) {
            return new NameImpl(CharOperation.NO_CHAR);
        }
        return new NameImpl(cArr[length - 1]);
    }

    public Name getQualifiedName() {
        return new NameImpl(CharOperation.concatWith(((PackageBinding) this._binding).compoundName, '.'));
    }

    public boolean isUnnamed() {
        return ((PackageBinding) this._binding).compoundName == CharOperation.NO_CHAR_CHAR;
    }
}
