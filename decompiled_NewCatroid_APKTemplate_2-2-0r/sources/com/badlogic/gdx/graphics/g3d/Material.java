package com.badlogic.gdx.graphics.g3d;

import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/Material.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class Material extends Attributes {
    private static int counter;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    public String f100id;

    public Material() {
        StringBuilder sb = new StringBuilder("mtl");
        int i = counter + 1;
        counter = i;
        this(sb.append(i).toString());
    }

    public Material(String str) {
        this.f100id = str;
    }

    public Material(Attribute... attributeArr) {
        this();
        set(attributeArr);
    }

    public Material(String str, Attribute... attributeArr) {
        this(str);
        set(attributeArr);
    }

    public Material(Array<Attribute> array) {
        this();
        set(array);
    }

    public Material(String str, Array<Attribute> array) {
        this(str);
        set(array);
    }

    public Material(Material material) {
        this(material.f100id, material);
    }

    public Material(String str, Material material) {
        this(str);
        Iterator<Attribute> it = material.iterator();
        while (it.hasNext()) {
            set(it.next().copy());
        }
    }

    public Material copy() {
        return new Material(this);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attributes
    public int hashCode() {
        return super.hashCode() + (this.f100id.hashCode() * 3);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attributes, java.util.Comparator
    public boolean equals(Object obj) {
        return (obj instanceof Material) && (obj == this || (((Material) obj).f100id.equals(this.f100id) && super.equals(obj)));
    }
}
