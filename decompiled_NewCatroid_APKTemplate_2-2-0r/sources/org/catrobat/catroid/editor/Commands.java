package org.catrobat.catroid.editor;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.SceneManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Commands.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Commands {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Commands$TransformCommand.class
      classes14.dex
     */
    public static class TransformCommand implements UndoManager.EditorCommand {
        private final Vector3 newPos;
        private final Quaternion newRot;
        private final Vector3 newScale;
        private final String objectId;
        private final Vector3 oldPos;
        private final Quaternion oldRot;
        private final Vector3 oldScale;
        private final SceneManager sceneManager;

        public TransformCommand(SceneManager sm, GameObject go, Vector3 startPos, Quaternion startRot, Vector3 startScale) {
            Vector3 vector3 = new Vector3();
            this.oldPos = vector3;
            Vector3 vector4 = new Vector3();
            this.newPos = vector4;
            Quaternion quaternion = new Quaternion();
            this.oldRot = quaternion;
            Quaternion quaternion2 = new Quaternion();
            this.newRot = quaternion2;
            Vector3 vector5 = new Vector3();
            this.oldScale = vector5;
            Vector3 vector6 = new Vector3();
            this.newScale = vector6;
            this.sceneManager = sm;
            this.objectId = go.f591id;
            vector3.set(startPos);
            quaternion.set(startRot);
            vector5.set(startScale);
            vector4.set(go.transform.position);
            quaternion2.set(go.transform.rotation);
            vector6.set(go.transform.scale);
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void undo() {
            apply(this.oldPos, this.oldRot, this.oldScale);
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void redo() {
            apply(this.newPos, this.newRot, this.newScale);
        }

        private void apply(Vector3 p, Quaternion r, Vector3 s) {
            GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(this.objectId);
            if (gameObjectFindGameObject != null) {
                gameObjectFindGameObject.transform.position.set(p);
                gameObjectFindGameObject.transform.rotation.set(r);
                gameObjectFindGameObject.transform.scale.set(s);
                this.sceneManager.updateWorldTransforms();
                this.sceneManager.rebuildGameObject(gameObjectFindGameObject);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Commands$DeleteCommand.class
      classes14.dex
     */
    public static class DeleteCommand implements UndoManager.EditorCommand {
        private final String objectId;
        private final String objectJson;
        private final SceneManager sceneManager;

        public DeleteCommand(SceneManager sm, GameObject go) {
            this.sceneManager = sm;
            this.objectId = go.f591id;
            this.objectJson = sm.getJson().toJson(go);
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void undo() {
            GameObject gameObjectFindGameObject;
            GameObject gameObject = (GameObject) this.sceneManager.getJson().fromJson(GameObject.class, this.objectJson);
            this.sceneManager.getAllGameObjects().put(gameObject.f591id, gameObject);
            if (gameObject.parentId != null && (gameObjectFindGameObject = this.sceneManager.findGameObject(gameObject.parentId)) != null && !gameObjectFindGameObject.childrenIds.contains(gameObject.f591id)) {
                gameObjectFindGameObject.childrenIds.add(gameObject.f591id);
            }
            this.sceneManager.rebuildGameObject(gameObject);
            this.sceneManager.updateWorldTransforms();
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void redo() {
            GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(this.objectId);
            if (gameObjectFindGameObject != null) {
                this.sceneManager.removeGameObject(gameObjectFindGameObject);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Commands$CompositeCommand.class
      classes14.dex
     */
    public static class CompositeCommand implements UndoManager.EditorCommand {
        private final List<UndoManager.EditorCommand> commands = new ArrayList();

        public void addCommand(UndoManager.EditorCommand cmd) {
            this.commands.add(cmd);
        }

        public boolean isEmpty() {
            return this.commands.isEmpty();
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void undo() {
            for (int size = this.commands.size() - 1; size >= 0; size--) {
                this.commands.get(size).undo();
            }
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void redo() {
            Iterator<UndoManager.EditorCommand> it = this.commands.iterator();
            while (it.hasNext()) {
                it.next().redo();
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/Commands$AddCommand.class
      classes14.dex
     */
    public static class AddCommand implements UndoManager.EditorCommand {
        private final String objectId;
        private final String objectJson;
        private final SceneManager sceneManager;

        public AddCommand(SceneManager sm, GameObject go) {
            this.sceneManager = sm;
            this.objectId = go.f591id;
            this.objectJson = sm.getJson().toJson(go);
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void undo() {
            GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(this.objectId);
            if (gameObjectFindGameObject != null) {
                this.sceneManager.removeGameObject(gameObjectFindGameObject);
            }
        }

        @Override // org.catrobat.catroid.editor.UndoManager.EditorCommand
        public void redo() {
            GameObject gameObjectFindGameObject;
            GameObject gameObject = (GameObject) this.sceneManager.getJson().fromJson(GameObject.class, this.objectJson);
            this.sceneManager.getAllGameObjects().put(gameObject.f591id, gameObject);
            if (gameObject.parentId != null && (gameObjectFindGameObject = this.sceneManager.findGameObject(gameObject.parentId)) != null && !gameObjectFindGameObject.childrenIds.contains(gameObject.f591id)) {
                gameObjectFindGameObject.childrenIds.add(gameObject.f591id);
            }
            this.sceneManager.rebuildGameObject(gameObject);
            this.sceneManager.updateWorldTransforms();
        }
    }
}
