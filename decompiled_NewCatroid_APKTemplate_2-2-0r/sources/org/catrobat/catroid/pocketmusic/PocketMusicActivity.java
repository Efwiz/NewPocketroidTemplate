package org.catrobat.catroid.pocketmusic;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.pocketmusic.fastscroller.FastScroller;
import org.catrobat.catroid.pocketmusic.mididriver.MidiNotePlayer;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;
import org.catrobat.catroid.pocketmusic.note.midi.MidiException;
import org.catrobat.catroid.pocketmusic.note.midi.MidiToProjectConverter;
import org.catrobat.catroid.pocketmusic.note.midi.ProjectToMidiConverter;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGridToTrackConverter;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/PocketMusicActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PocketMusicActivity extends BaseActivity {
    public static final String ABSOLUTE_FILE_PATH = "file";
    private static final String TAG = "PocketMusicActivity";
    public static final String TITLE = "title";
    private FastScroller fastScroller;
    private MidiNotePlayer midiDriver;
    private File midiFolder;
    private Project project;
    private TactScrollRecyclerView tactScroller;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.midiFolder = new File(getApplicationContext().getFilesDir().getPath(), Constants.SOUND_DIRECTORY_NAME);
        this.midiDriver = new MidiNotePlayer();
        if (getIntent().getExtras() != null && getIntent().getStringExtra("file") != null) {
            String string = getString(R.string.pocket_music_default_project_name);
            if (getIntent().getExtras() != null) {
                string = getIntent().getStringExtra("title");
            }
            MidiToProjectConverter midiToProjectConverter = new MidiToProjectConverter();
            File file = new File(getIntent().getStringExtra("file"));
            try {
                try {
                    Project projectConvertMidiFileToProject = midiToProjectConverter.convertMidiFileToProject(file);
                    this.project = projectConvertMidiFileToProject;
                    projectConvertMidiFileToProject.setName(string);
                    this.project.setFile(file);
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                    finish();
                }
            } catch (IOException | MidiException unused) {
                this.project = createEmptyProject();
            }
        } else {
            try {
                this.project = createEmptyProject();
            } catch (IOException e2) {
                Log.e(TAG, Log.getStackTraceString(e2));
                finish();
            }
        }
        setContentView(R.layout.activity_pocketmusic);
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setLogo(R.drawable.ic_pocketmusic);
        getSupportActionBar().setTitle(this.project.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TactScrollRecyclerView tactScrollRecyclerView = (TactScrollRecyclerView) findViewById(R.id.tact_scroller);
        this.tactScroller = tactScrollRecyclerView;
        tactScrollRecyclerView.setTrack(this.project.getTrack(getString(R.string.pocket_music_default_track_name)), this.project.getBeatsPerMinute());
        FastScroller fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        this.fastScroller = fastScroller;
        fastScroller.setRecyclerView(this.tactScroller);
        new ScrollController(viewGroup, this.tactScroller, this.project.getBeatsPerMinute());
    }

    private Project createEmptyProject() throws IOException {
        if (!this.midiFolder.exists() && !this.midiFolder.mkdir()) {
            throw new IOException("Cannot create dir MIDI folder at: " + this.midiFolder.getAbsolutePath());
        }
        Project project = new Project(getString(R.string.pocket_music_default_project_name), MusicalBeat.BEAT_4_4, 60);
        project.putTrack(getString(R.string.pocket_music_default_track_name), new Track(MusicalKey.VIOLIN, Project.DEFAULT_INSTRUMENT));
        project.setFile(new File(this.midiFolder, "MUS-" + Math.abs(new Random().nextInt()) + ProjectToMidiConverter.MIDI_FILE_EXTENSION));
        return project;
    }

    @Override // android.app.Activity
    public void finish() {
        Project project = this.project;
        if (project != null) {
            boolean zExists = project.getFile().exists();
            Track trackConvertTrackGridToTrack = TrackGridToTrackConverter.convertTrackGridToTrack(this.tactScroller.getTrackGrid(), 60);
            if (trackConvertTrackGridToTrack.isEmpty() && zExists) {
                SoundInfo soundInfo = new SoundInfo(this.project.getName(), this.project.getFile(), true);
                ProjectManager.getInstance().getCurrentSprite().getSoundList().remove(soundInfo);
                try {
                    new SoundController().delete(soundInfo);
                } catch (IOException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            } else if (!trackConvertTrackGridToTrack.isEmpty()) {
                Iterator<String> it = this.project.getTrackNames().iterator();
                while (it.hasNext()) {
                    this.project.putTrack(it.next(), trackConvertTrackGridToTrack);
                }
                SoundInfo soundInfo2 = new SoundInfo(this.project.getName(), this.project.getFile(), true);
                try {
                    new ProjectToMidiConverter(this.midiFolder).writeProjectAsMidi(this.project, soundInfo2.getFile());
                } catch (IOException | MidiException e2) {
                    Log.e(TAG, "Cannot save file:" + soundInfo2.getFile().getAbsolutePath() + ".", e2);
                }
                if (!zExists) {
                    ProjectManager.getInstance().getCurrentSprite().getSoundList().add(soundInfo2);
                }
            }
        }
        super.finish();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.midiDriver != null && !MidiNotePlayer.isInitialized()) {
            this.midiDriver.start();
        } else {
            this.midiDriver.setInstrument((byte) 0, Project.DEFAULT_INSTRUMENT);
        }
    }
}
