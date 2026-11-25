package org.catrobat.catroid.raptor;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/TransformComponent.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TransformComponent implements Component {
    public Vector3 position = new Vector3();
    public Quaternion rotation = new Quaternion();
    public Vector3 scale = new Vector3(1.0f, 1.0f, 1.0f);
    public final transient Matrix4 worldTransform = new Matrix4();

    public Matrix4 toMatrix() {
        return new Matrix4().set(this.position, this.rotation, this.scale);
    }
}
